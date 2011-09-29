package org.imajie.server.web.imajiematch.matchsServers.openwig;

import org.imajie.server.web.imajiematch.matchsServers.kahlua.vm.*;
import java.io.*;
import org.imajie.server.web.imajiematch.matchsServers.format.CartridgeFile;
import org.imajie.server.web.imajiematch.matchsServers.format.Savegame;
import org.imajie.server.web.imajiematch.matchsServers.platform.*;
import org.imajie.server.web.imajiematch.matchsServers.util.BackgroundRunner;
import java.io.Serializable;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.imajie.server.web.Constants;
import org.imajie.server.web.log.LoggingOutputStream;
import org.imajie.server.web.log.StdOutErrLevel;
import org.imajie.server.web.imajiematch.matchsServers.sockets.PlayerServer;

/** The OpenWIG Engine
 * <p>
 * This is the heart of OpenWIG. It instantiates the Lua machine and acts
 * as an interface between GPS position source, GUI and the Lua Wherigo script.
 * <p>
 * Engine is a partial singleton - although its singleness is not guarded, it
 * doesn't make sense to run more than one Engine at once, because most components
 * access Engine.instance statically (this is more a convenience than a purposeful
 * decision - it would be massively impractical to have reference to Engine in
 * every last component that might somehow use it).
 * <p>
 * To create a new Engine, you need a CartridgeFile, a reference to UI and LocationService.
 * Optionally, you can provide an OutputStream that will be used for logging.
 * When you get an instance, you can start it via start() for new game, or resume() for
 * continuing a saved game. Note that resume() will fail if there is no saved game.
 * <p>
 * Engine runs in a separate thread, and creates one more utility thread for itself,
 * whose sole purpose is to do everything related to Lua state - calling events, callbacks,
 * saving game.
 * Engine's own main loop consists of relaying position information from LocationService
 * to the Lua properties and evaluating position of player against zones.
 */  //implements Runnable
public class Engine implements Serializable, Runnable {

    public static final String VERSION = "386";
    /** the main instance */
    public static Engine instance;
    /** Lua state - don't touch this if you don't have to */
    public static LuaState state;
    /** reference to UI implementation */
    public static UI ui;
    /** reference to LocationService */
    //public static LocationService gps;
    /** reference to source file */
    public CartridgeFile gwcfile;
    /** reference to save file */
    public Savegame savegame = null;
    /** reference to log stream */
    private Logger log;
    private LogManager logManager;
    private LoggingOutputStream los;
    /** event runner taking care of Lua state calls */
    private BackgroundRunner eventRunner;
    private PlayerServer listeningServer;
    /** Cartridge (a global Lua object) */
    public Cartridge cartridge;
    /** global Player Lua object */
    public Player player = new Player();
    private boolean doRestore = false;
    public static boolean end = false;
    public static final int LOG_PROP = 0;
    public static final int LOG_CALL = 1;
    public static final int LOG_WARN = 2;
    public static final int LOG_ERROR = 3;
    private int loglevel = LOG_WARN;
    public static boolean prepareStateFinish = false;
    private Thread thread = null;
    //public static HttpSession tempSession;
    public static String Username;
    public String gameStarted;
    public boolean luaStateReset = false;
    public Double lat = Double.parseDouble("0");
    public Double lon = Double.parseDouble("0");
    public Double alt = Double.parseDouble("0");
    public Double accuracy = Double.parseDouble("10");
    public int port;
    private boolean debug = false;

    /** creates a new global Engine instance */
    public static Engine newInstance(CartridgeFile cf, LoggingOutputStream los, UI ui, String RequestUsername, String Requestlat, String Requestlon, String Requestalt, String Requestaccuracy, int Requestport) throws IOException {

        Username = RequestUsername;

        System.out.println("Creating engine...\n");

        Engine.ui = ui;

        //Engine.gps = service;
        instance = new Engine(cf, los, Requestlat, Requestlon, Requestalt, Requestaccuracy, Requestport);

        return instance;
    }

    public String username() {

        return Username;
    }

    protected Engine(CartridgeFile cf, LoggingOutputStream loss, String Requestlat, String Requestlon, String Requestalt, String Requestaccuracy, int Requestport) throws IOException {

        System.out.println("Protected engine...\n");
        instance = this;
        gwcfile = cf;
        savegame = cf.getSavegame();
        lat = Double.parseDouble(Requestlat);
        lon = Double.parseDouble(Requestlon);
        alt = Double.parseDouble(Requestalt);
        accuracy = Double.parseDouble(Requestaccuracy);
        port = Requestport;
        logManager = LogManager.getLogManager();
        logManager.reset();
        los = loss;
        setLogger();
    }

    protected Engine() {
        /* for test mockups */
    }

    /** starts Engine's thread */
    public void start() throws IOException {
        System.out.println("Start thread...\n");
        thread = new Thread(this);
        thread.start();
        //run();
    }

    public void setLogger() {
        try {


            // log file max size 500K, 10 rolling files, append-on-open
            Handler fileHandler = new FileHandler("PlayerServer-LOG-" + Username, 300000, 10, true);


            fileHandler.setFormatter(new SimpleFormatter());
            Logger.getLogger("").addHandler(fileHandler);

            // preserve old stdout/stderr streams in case they might be useful
            PrintStream stdout = System.out;
            PrintStream stderr = System.err;

            // now rebind stdout/stderr to logger
            //Logger logger;
            LoggingOutputStream los;

            log = Logger.getLogger("stdout");

            if (debug == false) {
                los = new LoggingOutputStream(log, StdOutErrLevel.STDOUT);
                System.setOut(new PrintStream(los, true));
            }

            log = Logger.getLogger("stderr");
            if (debug == false) {
                los = new LoggingOutputStream(log, StdOutErrLevel.STDERR);
                System.setErr(new PrintStream(los, true));
            }
            // now log a message using a normal logger
            log = Logger.getLogger("test");
            log.info("");
            log.info("");
            log.info("------------------------------------------------------------------");
            log.info("------------------------------------------------------------------");
            log.info("ImajieMatch Player Server | The ultimate tool for GEO MMORPG Game");
            log.info("Copyright Carl Tremblay and imajie.tv 1990-2011");
            log.info("------------------------------------------------------------------");
            log.info("");
        } catch (IOException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        } catch (SecurityException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }


    }

    /** marks game for resuming and starts thread */
    public void restore() throws IOException {


        doRestore = true;
        start();
    }

    /** prepares Lua state and some bookkeeping */
    private void prepareState() throws IOException {


        prepareStateFinish = false;

        System.out.println("Creating state...\n");
        state = null;
        state = new LuaState(System.out);

        /*write("Registering base libs...\n");
        BaseLib.register(state);
        MathLib.register(state);
        StringLib.register(state);
        CoroutineLib.register(state);
        OsLib.register(state);*/

        //ui.debugMsg("Building javafunc map...\n");
        System.out.println("Building javafunc map...\n");


        savegame.buildJavafuncMap(state.getEnvironment());

        System.out.println("Loading stdlib...\n");

        InputStream stdlib = getClass().getResourceAsStream(Constants.STDLIB);
        LuaClosure closure = LuaPrototype.loadByteCode(stdlib, state.getEnvironment());

        System.out.println("calling...\n");


        state.call(closure, null, null, null);
        stdlib.close();
        stdlib = null;


        System.out.println("Registering WIG libs...\n");


        System.out.println("State...:" + state.toString() + "\n");
        WherigoLib.register(state);

        System.out.println("Building event queue...\n");

        eventRunner = new BackgroundRunner(true);
        eventRunner.setQueueListener(new Runnable() {

            public void run() {
                ui.refresh();
            }
        });

        playerSocket.run();
        luaStateReset = false;

    }
    private Runnable playerSocket = new Runnable() {

        public void run() {
            listeningServer = new PlayerServer();
            listeningServer.setPort(port);
            listeningServer.setUsername(Username);
            listeningServer.start();
        }
    };

    public static void playerRefresh() {
        // TODO verify why this is commented out
        //playerRefresh1();
    }

    public void playerRefresh1() {

        player.refreshLocation();

    }

    /** invokes game restore */
    private void restoreGame()
            throws IOException {
    
        System.out.println("Restoring saved state...\n");

        cartridge = new Cartridge();
        savegame.restore(state.getEnvironment());
    }

    /** invokes creation of clean new game environment */
    private void newGame()
            throws IOException {
        // starting game normally

        System.out.println("Loading gwc...\n");

        if (gwcfile == null) {
            throw new IOException("invalid cartridge file");
        }
    
        System.out.println("Loading code...\n");

        byte[] lbc = gwcfile.getBytecode();

 
        System.out.println("parsing lua...\n");

        LuaClosure closure = LuaPrototype.loadByteCode(new ByteArrayInputStream(lbc), state.getEnvironment());

        System.out.println("calling lua...\n");

        state.call(closure, null, null, null);
        lbc = null;
        closure = null;


        System.out.println("Setting remaining properties...\n");

 
        player.rawset("CompletionCode", gwcfile.code);
        player.rawset("Name", gwcfile.member);
    }

    /** main loop - periodically copy location data into Lua and evaluate zone positions */
    private void mainloop() {
        int i = 0;
        try {

            if (doRestore) {


                while (!Savegame.restoreFinish && !prepareStateFinish) {
                }



            } else {

                while (!prepareStateFinish) {
                }
            }




            while (!end) {
                try {



                    if (lat != player.position.latitude
                            || lon != player.position.longitude
                            || alt != player.position.altitude.value) {
                        player.refreshLocation();
                    }


                    cartridge.tick();
                } catch (Exception e) {
                    stacktrace(e);
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }

        } catch (Throwable t) {
            ui.end();
            stacktrace(t);
            System.out.println("UI Closed");
        } finally {
            instance = null;
            state = null;
            if (eventRunner != null) {
                eventRunner.kill();
                System.out.println("Event runner killed");

            }
            eventRunner = null;
            System.out.println("Engine completely closed");

            System.exit(0);
        }


    }

    /** thread's run() method that does all the work in the right order */
    public void run() {
        PlayerServer.processFinish = false;

        try {



            prepareState();

            if (doRestore) {
                restoreGame();

            } else {
                newGame();

            }


  
            System.out.println("Starting game...\n");
            ui.start();

            player.refreshLocation();


            cartridge.callEvent(doRestore ? "OnRestore" : "OnStart", null);

            gameStarted = Engine.instance.cartridge.name;
            ui.refresh();
            eventRunner.unpause();

            mainloop();
        } catch (IOException e) {
            ui.showError("Could not load cartridge: " + e.getMessage());
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, e);
            System.exit(1);
        } catch (Throwable t) {
            stacktrace(t);
        } finally {
            ui.end();
            System.exit(0);
        }
    }

    /** utility function to dump stack trace and show a semi-meaningful error */
    public static void stacktrace(Throwable e) {
        e.printStackTrace();
        String msg;
        if (state != null) {
            System.out.println(state.currentThread.stackTrace);
            msg = e.toString() + "\n\nstack trace: " + state.currentThread.stackTrace;
        } else {
            msg = e.toString();
        }

        ui.showError("you hit a bug! please report at playar.googlecode.com and i'll fix it for you!\n" + msg);
        System.out.println("you hit a bug! please report at playar.googlecode.com and i'll fix it for you...\n");
        end = true;
        System.exit(0);
    }

    /** stops Engine */
    public static void kill() {
        if (instance == null) {
            System.out.println("Engine.kill Instance was null...\n");
            return;
        }
        Timer.kill();
        instance.end = true;
        System.out.println("Engine killed succesfully...\n");

    }

    /** builds and calls a dialog from a Message table */
    public static void message(LuaTable message, String altText, String description, String rawName) {
        String[] texts = {removeHtml((String) message.rawget("Text"))};
        System.out.println("CALL: MessageBox - " + texts[0].substring(0, Math.min(100, texts[0].length())));

        Media[] media = {(Media) message.rawget("Media")};
        String button1 = null, button2 = null;
        LuaTable buttons = (LuaTable) message.rawget("Buttons");
        if (buttons != null) {
            button1 = (String) buttons.rawget(new Double(1));
            button2 = (String) buttons.rawget(new Double(2));
        }
        LuaClosure callback = (LuaClosure) message.rawget("Callback");

        ui.pushDialog(texts, media, button1, button2, callback, altText, description, rawName);
    }

    /** builds and calls a dialog from a Dialog table */
    public static void dialog(String[] texts, Media[] media, String altText, String description, String rawName) {
        if (texts.length > 0) {
            System.out.println("CALL: Dialog - " + texts[0].substring(0, Math.min(100, texts[0].length())));
        }
        ui.pushDialog(texts, media, null, null, null, altText, description, rawName);
    }

    /** calls input to UI */
    public static void input(EventTable input) {
        System.out.println("CALL: GetInput - " + input.name);
        ui.pushInput(input);
    }

    /** fires the specified event on the specified object in the event thread */
    public static void callEvent(final EventTable subject, final String name, final Object param) {
        if (!subject.hasEvent(name)) {

            return;

        }
        instance.eventRunner.perform(new Runnable() {

            public void run() {
                subject.callEvent(name, param);
                // callEvent handles its failures, so no catch here
            }
        });
    }

    /** invokes a Lua callback in the event thread */
    public static void invokeCallback(final LuaClosure callback, final Object value) {
        instance.eventRunner.perform(new Runnable() {

            public void run() {
                try {
                    System.out.println("BTTN: " + (value == null ? "(cancel)" : value.toString()) + " pressed");
                    Engine.state.call(callback, value, null, null);
                    System.out.println("BTTN END");
                } catch (Throwable t) {
                    stacktrace(t);
                    System.out.println("BTTN FAIL");

                }
            }
        });
    }

    /** extracts media file data from cartridge */
    public static byte[] mediaFile(Media media) throws IOException {

        return instance.gwcfile.getFile(media.id);
    }

    private static void replace(String source, String pattern, String replace, StringBuffer builder) {
        int pos = 0;
        int pl = pattern.length();
        builder.delete(0, builder.length());
        while (pos < source.length()) {
            int np = source.indexOf(pattern, pos);
            if (np == -1) {
                break;
            }
            builder.append(source.substring(pos, np));
            builder.append(replace);
            pos = np + pl;
        }
        builder.append(source.substring(pos));
    }

    /** strips a subset of HTML that tends to appear in descriptions generated
     * by Groundspeak Builder
     */
    public static String removeHtml(String s) {
        if (s == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer(s.length());
        replace(s, "<BR>", "\n", sb);
        replace(sb.toString(), "&nbsp;", " ", sb);
        replace(sb.toString(), "&lt;", "<", sb);
        replace(sb.toString(), "&gt;", ">", sb);
        return sb.toString();
    }
    private Runnable refresh = new Runnable() {

        public void run() {
            synchronized (instance) {
                ui.refresh();
                refreshScheduled = false;
            }
        }
    };
    private boolean refreshScheduled = false;

    public static void refreshUI() {
        synchronized (instance) {
            if (!instance.refreshScheduled) {
                instance.refreshScheduled = true;
                instance.eventRunner.perform(instance.refresh);
            }
        }
    }
    private Runnable store = new Runnable() {

        public void run() {
            // perform the actual sync
            try {
                ui.blockForSaving();
                savegame.store(state.getEnvironment());
            } catch (IOException e) {
                System.out.println("STOR: save failed: " + e.toString());
                ui.showError("Sync failed.\n" + e.getMessage());
                System.out.println("Sync failed.\n" + e.getMessage());
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, e);
                System.exit(1);
            } finally {
                ui.unblock();

            }
        }
    };

    /** stores current game state */
    public void store() {
        store.run();
    }

    /** requests save in event thread */
    public static void requestSync() {
        instance.eventRunner.perform(instance.store);
    }
}
