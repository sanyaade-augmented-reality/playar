/*
 * Copyright (C) 2011 Carl Tremblay <carl_tremblay at imajie.tv>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.imajie.server.web.imajiematch.matchsServers.sockets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.imajie.server.web.imajiematch.matchsServers.DiskIO;
import org.imajie.server.web.imajiematch.matchsServers.openwig.ZonePoint;
import org.imajie.server.web.Constants;

/**
 *
 * @author Carl Tremblay <carl_tremblay at imajie.tv>
 */
public class MainServerProtocol {

    /* **********************************************
     *************************************************
     *            Commands Definition
     *********************************************** */
    private static final int WAITING = 0;
    private static final int COMMAND = 1;
    private static final int NEW_GAME = 2;
    private static final int RESTORE_GAME = 3;
    private static final int PLAYER_MESSAGE = 4;
    private static final int KILL_THREAD = 5;
    private static final int GET_MATCHS = 6;
    private static final int MATCH_DETAILS = 7;
    private static final int GET_LAYAR_REFRESH = 8;
    private static final int KILL_GAME_WAITING = 9;
    private int state = WAITING;
    private static final String[] wordsNS = {"", "north", "south"};
    private static final String[] wordsEW = {"", "east", "west"};
    public static Process[] processContainer = new Process[100];
    public static String[] currentMatchsContainer = new String[100];
    public static ArrayList list = new ArrayList();
    public String port = "777";
    public String gameToKill;

    public String processInput(String theInput) {
        String theOutput = null;

        if (state == WAITING) {
            theOutput = "Ready?";
            state = COMMAND;
        } /* **********************************************
         *************************************************
         *                Match Commands
         *********************************************** */ //
        else if (state == COMMAND) {



            //*****New game Call ************
            if (theInput.equalsIgnoreCase("NEW_GAME")) {

                theOutput = "NEW_GAME?";
                state = NEW_GAME;
            } //***** Restore game Call*****
            else if (theInput.equalsIgnoreCase("RESTORE_GAME")) {

                theOutput = "RESTORE_GAME?";
                state = RESTORE_GAME;
            } //***** Get nearest matchs call for Layar*****
            else if (theInput.equalsIgnoreCase("GET_LAYAR_REFRESH")) {

                theOutput = "Location?";
                state = GET_LAYAR_REFRESH;

            } //*****  Send message Player to Player      *****
            else if (theInput.equalsIgnoreCase("PLAYER_MESSAGE")) {

                state = PLAYER_MESSAGE;
                theOutput = "PLAYER_MESSAGE?";


            } //*****  Get the list of matchs near you      *****
            else if (theInput.equalsIgnoreCase("GET_MATCHS")) {


                theOutput = "LOCATION?";
                state = GET_MATCHS;

            } //***** Procces and thread killing Call*****
            else if (theInput.equalsIgnoreCase("KILL_THREAD")) {


                theOutput = "Process?";
                state = KILL_THREAD;

            } //***** Get match details Call*****
            else if (theInput.equalsIgnoreCase("MATCH_DETAILS")) {


                theOutput = "MATCH_DETAILS?";
                state = MATCH_DETAILS;

            } //***** Get all the matchs  played Call*****
            else if (theInput.equalsIgnoreCase("GET_PLAYED_MATCHS")) {



                for (int i = 0; i < list.size(); i++) {

                    theOutput = theOutput + "Process: " + list.get(i);
                }

                state = COMMAND;

            } //***** Return to the initial state if UNKNOWN  *****
            else {

                theOutput = "Ready?";
                state = COMMAND;
            }

        } /* **********************************************
         *************************************************
         *      Create a new Virtual Player Server
         *********************************************** */ //
        else if (state == NEW_GAME) {
            if (theInput.contains("START||||||")) {

                // String port = "";
                String username = "";
                String lat = "";
                String lon = "";
                String alt = "";
                String accuracy = "";
                String match = "";

                String position = theInput.replace("START||||||", "");
                String delimiter = "\\|\\|";
                String[] temp = position.split(delimiter);




                for (int ii = 0; ii < temp.length; ii++) {

                    if (ii == 0) {
                        username = temp[ii];
                    }
                    if (ii == 1) {
                        lat = temp[ii];
                    }
                    if (ii == 2) {
                        lon = temp[ii];
                    }
                    if (ii == 3) {
                        alt = temp[ii];
                    }
                    if (ii == 4) {
                        accuracy = temp[ii];
                    }
                    if (ii == 5) {
                        match = temp[ii];
                    }

                }

                try {

                    boolean alreadyPlaying = false;
                    int freePortCount = 0;
                    boolean insertDone = false;

                    for (int i = 0; i < MainServer.ports.size(); i++) {

                        Ports p = (Ports) MainServer.ports.get(i);
                        if (p.getUsername().equals("")) {
                            freePortCount++;
                        }
                        if (p.getUsername() == username) {



                            alreadyPlaying = true;

                        }

                    }

                    while (!insertDone) {

                        if (!alreadyPlaying && freePortCount > 0) {
                            for (int i = 0; i < MainServer.ports.size(); i++) {


                                Ports p = (Ports) MainServer.ports.get(i);


                                if (p.getUsername().equals("")) {


                                    p.setUsername(username);


                                    port = String.valueOf(p.getPort());


                                    Processes pp = new Processes(username, Runtime.getRuntime().exec("java -jar " + Constants.PLAYER_SERVER_LAUNCH_SCRIPT + " " + port + " " + username + " " + lat + " " + lon + " " + alt + " " + accuracy + " " + match + ""), port, lat, lon, match);
                                    list.add(pp);


                                    System.out.println("User set to port:" + p.toString());

                                    System.out.print("java -jar " + Constants.PLAYER_SERVER_LAUNCH_SCRIPT + " \"" + port + "\" \"" + username + "\" \"" + lat + "\" \"" + lon + "\" \"" + alt + "\" \"" + accuracy + "\" \"" + match + "\"");


                                    insertDone = true;

                                    MainServer.timeout(username);
                                    i = MainServer.ports.size();

                                }

                            }

                        }
                        insertDone = true;

                    }


                    if (!alreadyPlaying) {
                        if (freePortCount == 0) {

                            theOutput = "SERVER_FULL";

                        } else {

                            theOutput = "HAVE_FUN::PORT::" + port;

                        }
                    } else {

                        theOutput = "ALREADY_PLAYING";
                        alreadyPlaying = false;

                    }


                    state = COMMAND;
                } catch (IOException ex) {
                    Logger.getLogger(MainServerProtocol.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {

                theOutput = "Ready?";
                state = COMMAND;

            }
        } /* **********************************************
         *************************************************
         *          Restore game Method
         *********************************************** */ //
        else if (state == RESTORE_GAME) {
            if (theInput.contains("RESTORE||||||")) {

                //String port = "";
                String username = "";
                String lat = "";
                String lon = "";
                String alt = "";
                String accuracy = "";
                String match = "";

                String position = theInput.replace("RESTORE||||||", "");
                String delimiter = "\\|\\|";
                String[] temp = position.split(delimiter);

                for (int ii = 0; ii < temp.length; ii++) {

                    if (ii == 0) {
                        username = temp[ii];
                    }
                    if (ii == 1) {
                        lat = temp[ii];
                    }
                    if (ii == 2) {
                        lon = temp[ii];
                    }
                    if (ii == 3) {
                        alt = temp[ii];
                    }
                    if (ii == 4) {
                        accuracy = temp[ii];
                    }
                    if (ii == 5) {
                        match = temp[ii];
                    }

                }

                try {

                    boolean alreadyPlaying = false;
                    int freePortCount = 0;
                    boolean insertDone = false;

                    for (int i = 1; i < MainServer.ports.size(); i++) {

                        Ports p = (Ports) MainServer.ports.get(i);
                        if (p.getUsername().length() > 1) {
                            freePortCount++;
                        }
                        if (p.getUsername() == username) {



                            alreadyPlaying = true;

                        }

                    }



                    while (!insertDone) {

                        if (!alreadyPlaying && freePortCount > 0) {
                            for (int i = 1; i < MainServer.ports.size(); i++) {


                                Ports p = (Ports) MainServer.ports.get(i);


                                if (p.getUsername().equals("")) {


                                    p.setUsername(username);


                                    port = String.valueOf(p.getPort());


                                    Processes pp = new Processes(username, Runtime.getRuntime().exec("java -jar " + Constants.CARTRIDGE_BASE_DIR + "PlayerMatchServer.jar " + port + " " + username + " " + lat + " " + lon + " " + alt + " " + accuracy + " " + match + ""), port, lat, lon, match);
                                    list.add(pp);


                                    insertDone = true;

                                    MainServer.timeout(username);

                                }

                            }
                        }
                        insertDone = true;

                    }

                    // FOR MOCK TEST (START A TEST GAME WITH ALFRED USER)
                    //Process exec = Runtime.getRuntime().exec("java -jar "+Constants.CARTRIDGE_BASE_DIR+Constants.PLAYER_SERVER_LAUNCH_SCRIPT);
//                    InputStream is = processContainer[0].getInputStream();
//                    InputStreamReader isr = new InputStreamReader(is);
//                    BufferedReader br = new BufferedReader(isr);
//                    String line;
//                    while ((line = br.readLine()) != null) {
//                        System.out.println(line);
//                    }


                    if (!alreadyPlaying) {
                        if (freePortCount == 0) {

                            theOutput = "SERVER_FULL";

                        } else {

                            theOutput = "HAVE_FUN::PORT::" + port;

                        }
                    } else {

                        theOutput = "ALREADY_PLAYING";
                        alreadyPlaying = false;

                    }


                    state = COMMAND;


                } catch (IOException ex) {
                    Logger.getLogger(MainServerProtocol.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } /* **********************************************
         *************************************************
         *     Get match details Command Method
         *********************************************** */ //
        else if (state == MATCH_DETAILS) {
            if (theInput.contains("MATCH||")) {


                String position = theInput.replace("MATCH||", "");
                String delimiter = "\\|\\|";
                String[] temp = position.split(delimiter);

                Double lat = null;
                Double lon = null;
                Double radius = null;
                String match = null;


                for (int ii = 0; ii < temp.length; ii++) {
                    if (ii == 0) {
                        lat = Double.parseDouble(temp[ii]);
                    }
                    if (ii == 1) {
                        lon = Double.parseDouble(temp[ii]);
                    }
                    if (ii == 2) {
                        match = temp[ii];
                    }


                }

                String[] cartridgeDetails = new String[5];
                cartridgeDetails = DiskIO.matchDetails(match);
                ZonePoint zp = new ZonePoint(Double.parseDouble(cartridgeDetails[2].toString()), Double.parseDouble(cartridgeDetails[3].toString()), 0);
                String distance = "";
                distance = "" + getDistanceAndDirection(zp, lat, lon);


                //TODO RETURN THE DETAILS AND PROCESS THE RESULT ON WEB SERVER SIDE


                String rabbitHole = "<div data-role='fieldcontain'  data-theme='a'>";

                //  + "Find this rabbit hole to enter the game: " + cartridgeDetails[4] + "</div>"
                //         + "<div data-role='fieldcontain'  data-theme='a'>"

                String formHeader = "<form action='start.jsp?match=" + match + "' data-transition='slide' method='post'> ";
                String formFooter = "</form>";
                if (cartridgeDetails[4].contains("VISION")) {

                    formHeader = "";
                    formFooter = "";
                    String mediaName = cartridgeDetails[4].replace("***VISION", "");
                    mediaName = mediaName.replace("***", "");


                    rabbitHole = rabbitHole + "Find this rabbit hole !<br> <IMG SRC= '../icon?matchtitle=" + cartridgeDetails[0] + "&icon=" + mediaName + "'>"
                            + "<br>";

                }

                
//                String cartridgeDescription = "";
//                        
//                        String[] description = cartridgeDetails[1].split("<BR>");
//                        
//                        for (int iii = 0; iii< description.length; iii++) {
//                        
//                        cartridgeDescription = cartridgeDescription + "(!!BR!!)";
//                        
//                        }
                
                String msg = formHeader
                        + "<div data-role='fieldcontain'  data-theme='a'>"
                        + "<h2>" + cartridgeDetails[0] + "</h2></div>"
                        + "<div data-role='fieldcontain'  data-theme='a'>"
                        + "<IMG SRC= '../icon?matchtitle=" + cartridgeDetails[0] + "&icon=splashid.jpg'></div>"
                        + "<div data-role='fieldcontain'  data-theme='a'>" + cartridgeDetails[1] + "</div>"
                        + rabbitHole
                        + "Distance: " + distance + "</div>";


                if (rabbitHole.equals("<div data-role='fieldcontain'  data-theme='a'>")) {

                    msg = msg + "<input type='submit' data-role='button' data-theme='a' value='Get Mission'/>";


                } else {


                    msg = msg + "<div data-role='fieldcontain'  data-theme='a'>"
                            + "<a href='http://m.layar.com/open/imajiematch' data-transition='slide' data-role='button'>"
                            + "Find the rabbit hole</a>"
                            + "</div>";


                }



                msg = msg + formFooter;

                theOutput = "MATCH_DETAILS||" + msg + "MATCH_DETAILS|!!|";
                state = COMMAND;

            }
        } /* **********************************************
         *************************************************
         *     Player to Player messages Method
         *********************************************** */ //
        else if (state == PLAYER_MESSAGE) {
            if (theInput.contains("PLAYER_MESSAGE_CONTAINS")) {


                // TODO IMPLEMENTS MESSAGES ROUTING BETWEEN PLAYERS
                //      -APPLICATION INTERNAL MESSAGES: BETWEEN PLAYERS' PRIVATE PLAYER_SERVERS SOCKETS PORT, FOR CURENTLY PLAYING PLAYER - (ALWAYS SAME TEAM PLAYERS)
                //      -BY SMS OR TELEPHONE CALLS: BETWEEN A PLAYER CURENTLY PLAYING AND A PLAYER NOT PLAYING - (ALWAYS SAME TEAM PLAYERS)

                theOutput = "MESSAGE_SENDED";
                state = COMMAND;

            }
        } /* **********************************************
         *************************************************
         *        Get the matchs list Commands
         *********************************************** */ //
        else if (state == GET_MATCHS) {

            if (theInput.contains("LOCATION||||||")) {

                String position = theInput.replace("LOCATION||||||", "");
                String delimiter = "\\|\\|";
                String[] temp = position.split(delimiter);

                String latSession = null;
                String lonSession = null;
                String radiusSession = null;


                for (int ii = 0; ii < temp.length; ii++) {
                    if (ii == 0) {
                        latSession = temp[ii];
                    }
                    if (ii == 1) {
                        lonSession = temp[ii];
                    }
                    if (ii == 2) {
                        radiusSession = temp[ii];
                    }

                }

                String[] gameList = DiskIO.refreshNearestList(latSession, lonSession, radiusSession);


                String msg = "";

                for (int i = 0; i < gameList.length; i++) {
                    if (gameList[i] != null) {
                        msg = msg + "<li><a href='startmatch.jsp?match=" + gameList[i] + "' data-transition='slide'><IMG SRC= '../icon?matchtitle=" + gameList[i] + "&icon=splashid.jpg'>" + gameList[i] + "</a></li>";
                    }
                }

                theOutput = "MATCHS_LIST||||||" + msg;
                state = COMMAND;


            } else {

                theOutput = "Ready?";
                state = COMMAND;

            }
        } /* **********************************************
         *************************************************
         *        GET_LAYAR_REFRESH Commands
         *********************************************** */ //
        else if (state == GET_LAYAR_REFRESH) {
            if (theInput.contains("LOCATION||||||")) {

                String position = theInput.replace("LOCATION||||||", "");
                String delimiter = "\\|\\|";
                String[] temp = position.split(delimiter);

                String latSession = null;
                String lonSession = null;
                String radiusSession = null;


                for (int ii = 0; ii < temp.length; ii++) {
                    if (ii == 0) {
                        latSession = temp[ii];
                    }
                    if (ii == 1) {
                        lonSession = temp[ii];
                    }
                    if (ii == 2) {
                        radiusSession = temp[ii];
                    }

                }

                String[] gameList = DiskIO.refreshNearestList(latSession, lonSession, radiusSession);


                String msg = "";
                int count = 0;

                for (int i = 0; i < gameList.length; i++) {
                    if (gameList[i] != null) {
                        count++;

                        String[] cartridgeDetails = new String[5];

                        cartridgeDetails = DiskIO.matchDetails(gameList[i]);

//                        String cartridgeDescription = "";
//                        
//                        String[] description = cartridgeDetails[1].split("<BR>");
//                        
//                        for (int iii = 0; iii< description.length; iii++) {
//                        
//                        cartridgeDescription = cartridgeDescription + "(!!BR!!)";
//                        
//                        }


                        ZonePoint zp = new ZonePoint(Double.parseDouble(cartridgeDetails[2].toString()), Double.parseDouble(cartridgeDetails[3].toString()), 0);
                        String distance = "";
                        distance = "" + getDistanceAndDirection(zp, Double.parseDouble(latSession), Double.parseDouble(lonSession));

                        msg = msg + "||||" + gameList[i] + "||" + cartridgeDetails[1] +"||" + cartridgeDetails[2] + "||" + cartridgeDetails[3] + "||" + cartridgeDetails[4];
                    }
                }

                theOutput = "LAYAR_REFRESH||||||" + count + msg +"|!!!|LAYAR_REFRESH";
                state = COMMAND;
            }
        } /* **********************************************
         *************************************************
         *        Kill Process and Threads Commands
         *********************************************** */ //
        else if (state == KILL_THREAD) {
            if (theInput.equalsIgnoreCase("Connection")) {

                theOutput = "Bye.";
                state = WAITING;

            } else if (theInput.equalsIgnoreCase("you")) {

                theOutput = "Me?";
                state = WAITING;

            } else if (theInput.contains("GAME||||||")) {


                gameToKill = theInput.replace("GAME||||||", "");

                theOutput = gameToKill + " GAME_KILLED";
                timeout.run();
                state = WAITING;


            } else if (state == KILL_GAME_WAITING) {








                state = WAITING;

            } else {

                theOutput = "";
                state = COMMAND;

            }
        }

        return theOutput;
    }
    private Runnable timeout = new Runnable() {

        @Override
        public void run() {
            int numberOfMillisecondsInTheFuture = 15000;
            Date timeToRun = new Date(System.currentTimeMillis() + numberOfMillisecondsInTheFuture);



            Timer timeout = new Timer();

            timeout.schedule(new TimerTask() {

                @Override
                public void run() {






                    for (int i = 0; i < list.size(); i++) {


                        Processes p = (Processes) list.get(i);

                        if (p.getUsername().equals(gameToKill)) {

                            for (int ii = 0; ii < MainServer.ports.size(); ii++) {


                                Ports pp = (Ports) MainServer.ports.get(ii);

                                if (pp.getUsername().equals(p.getUsername())) {

                                    pp.setUsername("");

                                }

                            }
                            MainServer.out.close();
                            p.kill();
                            list.remove(p);
                            System.out.println("Timeout " + gameToKill + " game killed at: " + new Date());

                        }
                    }



                }
            }, timeToRun);

        }
    };

    public static String getDistanceAndDirection(ZonePoint zp, double lat, double lon) {


        String dist = zp.friendlyDistance(lat, lon);

        double dx = zp.latitude - lat;
        double dy = zp.longitude - lon;
        double adx = Math.abs(dx), ady = Math.abs(dy);
        int north = 0, east = 0;
        if (adx >= ady / 2 && dx != 0) {
            north = dx > 0 ? 1 : 2;
        }
        if (ady >= adx / 2 && dy != 0) {
            east = dy > 0 ? 1 : 2;
        }


        return dist + " " + wordsNS[north] + wordsEW[east];

    }

    public int getPort(String username) {

        int port = 0;

        return port;
    }

    public static void waiting(int n) {

        long t0, t1;

        t0 = System.currentTimeMillis();

        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < (n));
    }
    private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>"); 
 
public static String removeTags(String string) { 
    if (string == null || string.length() == 0) { 
        return string; 
    } 
 
    Matcher m = REMOVE_TAGS.matcher(string); 
    return m.replaceAll(""); 
} 

}

class Processes {

    private String username;
    private Process process;
    private String port;
    private String lat;
    private String lon;
    private String match;

    public Processes() {
    }

    public Processes(String username, Process process, String port, String lat, String lon, String match) {

        this.username = username;
        this.process = process;
        this.port = port;
        this.lat = lat;
        this.lon = lon;
        this.match = match;
    }

    /**
     * @return Returns the played match.
     */
    public String getMatch() {
        return match;
    }

    /**
     * @param match The Match played to set.
     */
    public void setMatch(String match) {
        this.match = match;
    }

    /**
     * @return Returns the longitude.
     */
    public String getLon() {
        return lon;
    }

    /**
     * @param lon The longitude to set.
     */
    public void setLon(String lon) {
        this.lon = lon;
    }

    /**
     * @return Returns the latitude.
     */
    public String getLat() {
        return lat;
    }

    /**
     * @param lat The latitude to set.
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * @return Returns the socket port.
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port The socket port to set.
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return Returns the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return Returns the Virtual Player Server process.
     */
    public Process getProcess() {
        return process;
    }

    public void kill() {
        this.process.destroy();
    }

    /**
     * @param set the Virtual player server process.
     */
    public void setProcess(Process process) {
        this.process = process;
    }

    public String toString() {
        return "## Username : " + this.username + ", PlayerServer : " + this.process.toString() + ", Port : " + this.port + ", Match : " + this.match + ", Latitude : " + this.lat + ", Longitude : " + this.lon + "\n";
    }
}
