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
package org.imajie.server.web.imajiematch.matchsServers.main;

import org.imajie.server.web.imajiematch.matchsServers.openwig.Engine;
import java.util.*;

import org.imajie.server.web.imajiematch.matchsServers.events.EventTableDetails;
import org.imajie.server.web.imajiematch.matchsServers.events.EventTableList;
import org.imajie.server.web.imajiematch.matchsServers.openwig.EventTable;
import org.imajie.server.web.imajiematch.matchsServers.openwig.Media;
import org.imajie.server.web.imajiematch.matchsServers.openwig.Task;
import org.imajie.server.web.imajiematch.matchsServers.openwig.Thing;
import org.imajie.server.web.imajiematch.matchsServers.openwig.Zone;
//import org.imajie.server.web.imajiematch.matchsServers.common.FrameTimer;
import org.imajie.server.web.imajiematch.matchsServers.common.Navigator;
import org.imajie.server.web.imajiematch.matchsServers.platform.UI;
import org.imajie.server.web.imajiematch.matchsServers.kahlua.vm.LuaClosure;
import org.imajie.server.web.imajiematch.matchsServers.kahlua.vm.LuaTable;
import org.imajie.server.web.imajiematch.matchsServers.openwig.ZonePoint;
import org.imajie.server.web.imajiematch.matchsServers.openwig.Action;

public class GameWindow implements UI {

    public static String dialogTexts = "";
    public static String dialogMedia = "";
    public static String Button1;
    public static String Button2;
    public static int zoneCount;
    public static String zoneList;
    public static int youseeCount;
    public static String youseeList;
    public static int inventoryCount;
    public static String inventoryList;
    public static int tasksCount;
    public static String tasksList;
    public static String altText;
    public static String description;
    public static String rawName;
    public static String mediaName;
    public static String mediaType;
    public static String mediaOverride;
    public static String triviaInput;
    public static String triviaInputPlayed;
    public static String dialog;
    public static String currentEvent = "";
    public static EventTable currentInput;
    public static TargetPickerWindow picker;
    private ArrayList<Action> actions = new ArrayList<Action>();
    private ArrayList<String> buttons = new ArrayList<String>();
    /** List of zones.
     * Its source reads data from <code>Engine.instance.cartridge.zones</code>
     */
    protected EventTableList zones = new EventTableList(this, new EventTableList.Source() {

        public List<EventTable> newSet() {

            List<EventTable> ret = new ArrayList<EventTable>();

            String list = "";
            Vector v = Engine.instance.cartridge.zones;
            int Count = 0;
            for (Object o : v) {
                Zone z = (Zone) o;


                if (z.isVisible()) {

                    //*********************************************************
                    //Start


                    Count++;

                    String name = z.name;
                    String description = z.description;
                    String altitude = "";
                    String longitude = Double.toString(z.position.longitude);
                    String latitude = Double.toString(z.position.latitude);
                    String distance = Double.toString(z.distance);

                    ZonePoint zp = new ZonePoint(z.position.latitude, z.position.longitude, 0);

                    String friendlyDistance = Navigator.getDistanceAndDirection(zp);

                    String zonemedia = "";
                    if (z.media != null) {

                        zonemedia = z.media.name + "." + z.media.type;
                    }


                    String content = name + "||" + description + "||" + altitude + "||" + latitude + "||" + longitude + "||" + distance + "||" + friendlyDistance + "||" + zonemedia;
                    list = list + "|!|" + content;

                    ret.add(z);
                    //END
                    //*******************************************************************                   

                }


            }

            zoneCount = Count;
            zoneList = list;
            //System.out.println("Zone Refresh");
            return ret;
        }
    });
    /** List of visible items in active zones.
     * A.k.a. "you see". Its source reads data from <code>Engine.instance.cartridge.currentThings()</code>
     */
    protected EventTableList yousee = new EventTableList(this, new EventTableList.Source() {

        public List<EventTable> newSet() {
            int Count = 0;
            String list = "";
            LuaTable container = Engine.instance.cartridge.currentThings();
            List<EventTable> ret = new ArrayList<EventTable>(container.len());
            Object key = null;
            while ((key = container.next(key)) != null) {
                Thing t = (Thing) container.rawget(key);
                if (t.isVisible()) {
                    Count++;
                    String name = "";
                    String description = "";
                    String altitude = "";
                    String longitude = "";
                    String latitude = "";

                    ret.add(t);

                    if (t.name.toString() != null) {
                        name = t.name.toString();
                    } else {
                        name = "";
                    }
                    if (t.description.toString() != null) {
                        description = t.description.toString();
                    } else {
                        description = "";
                    }
                    String friendlyDistance = "";

                    String youseemedia = "";
                    if (t.media != null) {

                        youseemedia = t.media.name + "." + t.media.type;

                    }

                    String youseeButtonsArray = "";


                    Vector action = t.actions;
                    int actionsCount = 0;
                    String label;
                    for (Object o : action) {
                        Action a = (Action) o;
                        //System.out.println("Actor visible actions: " + a.getActor().visibleActions());


                        if (a.getActor() == t && a.isVisible()) {
//			if (a.hasParameter()) {
//				picker.showPicker(a);
//			} else {
//                            System.out.println("Event Name :" + a.getName());
//				//Engine.callEvent(t, "On"+a.getName(), null);
//			}
                        } else {

                            // System.out.println("Event Name :" + a.getName() + "Actor : " + a.getActor() + "Event text " + a.text);
                            youseeButtonsArray = youseeButtonsArray + a.text + "**";
                            //Engine.callEvent(a.getActor(), "On"+a.getName(), t);
                        }


                    }



                    String content = name + "||" + description + "||" + altitude + "||" + latitude + "||" + longitude + "||" + friendlyDistance + "||" + youseemedia + "||" + youseeButtonsArray;
                    list = list + "|!|" + content;
                }

            }

            youseeCount = Count;
            youseeList = list;
             System.out.println("Yousee list : " + list);
            return ret;
        }
    });
    /** List of visible items in inventory.
     * Its source reads data from <code>Engine.instance.player.inventory</code>
     */
    protected EventTableList inventory = new EventTableList(this, new EventTableList.Source() {

        public List<EventTable> newSet() {
            int Count = 0;

            String list = "";
            LuaTable container = Engine.instance.player.inventory;
            List<EventTable> ret = new ArrayList<EventTable>(container.len());
            Object key = null;
            while ((key = container.next(key)) != null) {
                Thing t = (Thing) container.rawget(key);
                if (t.isVisible()) {
                    Count++;
                    String name = "";
                    String description = "";
                    String altitude = "";
                    String longitude = "";
                    String latitude = "";

                    ret.add(t);

                    if (t.name.toString() != null) {
                        name = t.name.toString();
                    } else {
                        name = "";
                    }
                    if (t.description.toString() != null) {
                        description = t.description.toString();
                    } else {
                        description = "";
                    }

                    String inventorymedia = "";
                    if (t.media != null) {

                        inventorymedia = t.media.name + "." + t.media.type;

                    }

                    String content = name + "||" + description + "||" + altitude + "||" + latitude + "||" + longitude + "||" + inventorymedia;
                    list = list + "|!|" + content;

                }

            }

            inventoryCount = Count;
            inventoryList = list;
            // System.out.println("Inventory Refresh");
            return ret;
        }
    });
    /** List of tasks.
     * Its source reads data from <code>Engine.instance.cartridge.tasks</code>
     */
    protected EventTableList tasks = new EventTableList(this, new EventTableList.Source() {

        public List<EventTable> newSet() {
            int Count = 0;
            List<EventTable> ret = new ArrayList<EventTable>(Engine.instance.cartridge.tasks.size());

            String list = "";
            for (Object o : Engine.instance.cartridge.tasks) {
                Task t = (Task) o;
                if (t.isVisible()) {
                    Count++;
                    String name = "";
                    String description = "";
                    String altitude = "";
                    String longitude = "";
                    String latitude = "";

                    ret.add(t);

                    if (t.name.toString() != null) {
                        name = t.name.toString();
                    } else {
                        name = "";
                    }
                    if (t.description.toString() != null) {
                        description = t.description.toString();
                    } else {
                        description = "";
                    }

                    String taskmedia = "";
                    if (t.media != null) {

                        taskmedia = t.media.name + "." + t.media.type;
                    }
                    String content = name + "||" + description + "||" + altitude + "||" + latitude + "||" + longitude + "||" + taskmedia;
                    list = list + "|!|" + content;
                }

            }

            tasksCount = Count;
            tasksList = list;
            //System.out.println("Task Refresh");
            return ret;
        }
    });
//    public static String eventDetails() {
//    
//    
//    return details.;
//    
//    }
    public EventTableDetails details = new EventTableDetails(this);
    private boolean buttonsVisible = true;

    /** Timer for refreshing navigation displays. */
//	private FrameTimer refresher = new FrameTimer(this, new ActionListener() {
//		public void actionPerformed (ActionEvent e) {
//			zones.updateNavigation();
//			tasks.updateNavigation();
//			yousee.updateNavigation();
//			details.updateNavigation();
//		}
//	}, 500);
    public GameWindow() {
    }
    boolean closeAfterSave = false;

    public static void confirmExit() {
        // int ret = JOptionPane.showConfirmDialog(this, "Save game before exiting?");
        //Engine.requestSync();
    }

    private void kill() {
        Engine.kill();
    }

    /** Displays the detail pane and updates it with info from <code>details</code> */
    public void showDetails(EventTable details) {
        //detailDisplay.show("details");
        //this.details.showDetails(details, request);
    }

    public void hideDetails() {
        //detailDisplay.show("empty");
    }

    public void showSubmenu(String key) {
        //submenus.show(key);
    }

    public void thingsButton(String call) {

        String requestCall = call.replace("thingButton", "");

        String delimiter2 = "\\.";
        String[] temp2 = requestCall.split(delimiter2);

        LuaTable container = Engine.instance.cartridge.currentThings();

        Object key = null;
        while ((key = container.next(key)) != null) {
            Thing t = (Thing) container.rawget(key);
            if (t.isVisible()) {

                String name = "";


                //ret.add(t);

                if (t.name.toString() != null) {

                    if (t.name.toString().equals(temp2[0])) {

                        Vector action = t.actions;
                        int actionsCount = 0;
                        String label;
                        for (Object o : action) {
                            Action a = (Action) o;

                            if (a.getActor().toString().equals(t.name.toString()) && a.isVisible()) {
//			if (a.hasParameter()) {
//				picker.showPicker(a);
//			} else {
//                            System.out.println("Event Name :" + a.getName());
//				Engine.callEvent(t, "On"+a.getName(), null);
//			}
                            } else if (a.getName().equals(temp2[1].replace("On", ""))) {


                                Engine.callEvent(a.getActor(), "On" + a.getName(), t);
                            }


                        }


                    }

                }

            }

        }


    }

    public void refresh() {
        zones.updateNavigation();
        tasks.updateNavigation();
        yousee.updateNavigation();
        details.updateNavigation();
        zones.refresh();
        yousee.refresh();
        inventory.refresh();
        tasks.refresh();
        // the lists must refresh first, mainMenu refreshes based on what they see
        //mainMenu.refresh();
        details.refresh();
        Engine.instance.player.refreshLocation();
        zones.updateNavigation();
        tasks.updateNavigation();
        yousee.updateNavigation();
        details.updateNavigation();
    }

    public void refreshPlayer() {

        Engine.instance.player.refreshLocation();
    }

    public void start() {
    }

    public void end() {
    }

    public void showError(String msg) {
    }

    public void debugMsg(String msg) {
        System.err.print(msg);
    }

    /** not implemented, because nobody uses it anyway and it would only clutter the UI */
    public void setStatusText(String text) {
    }

    public void showDialog(String[] dialogtexts, Media[] dialogmedia, String btn1, String btn2, LuaClosure luacallback) {
        callback = luacallback;
        texts = dialogtexts;
        media = dialogmedia;
        Button1 = (btn1 == null ? "OK" : btn1);
        Button2 = (btn2);
        //button2.setVisible(btn2 != null);
        page = -1;
        flipPage();

        System.out.println("Details event tables" + details.toString());
        details.buttonClicked("Button1");
        details.refresh();
    }

    private void flipPage() {

        // unused function

        page++;
        if (page >= texts.length) {
            callAndClose("Button1");
        } else {
            //setDescription(texts[page]);
            //setMedia(media[page]);
        }
    }

    protected void buttonClicked(String button) {
        // TODO implements this behavior in layar

        if (button == Button1) {
            flipPage();
        } else if (button == Button2) {
            callAndClose("Button2");
        }
    }

    public void callAndClose(String what) {
        System.out.println("Call and Close method what value : " + what);
        if (callback != null) {
            System.out.println("Callback Value : " + callback.toString());
            Engine.invokeCallback(callback, what);
        }
        //parent.close();
    }
    private int page;
    /** texts of individual pages */
    private String[] texts;
    /** pictures for individual pages */
    private Media[] media;

    /** Called by parent window to indicate that the Dialog was cancelled.
     * If the callback is present, it must be called with null parameter.
     */
    public void cancel() {
        if (callback != null) {
            Engine.invokeCallback(callback, null);
        }
    }
    private LuaClosure callback;

    public void pushDialog(final String[] texts, final Media[] media, final String button1, final String button2, final LuaClosure callback, final String alttext, String descriptions, String rawname) {

        dialog = "<body>";

        this.callback = callback;

        showDialog(texts, media, button1, button2, callback);
        if (texts.length > 1) {
            for (int i = 0; i < texts.length; i++) {


                description = description + texts[i] + "\n";



                dialog = dialog + "<div data-role='page' id='page" + i + "' style='min-height:100%' data-theme='a'>"
                        + "<div data-role='header'  data-theme='b'>"
                        + "<h6>Dialog</h6>"
                        + "</div>"
                        + " <div data-role='fieldcontain'  data-theme='a' align='center'>"
                        + "<p>"
                        + "<IMG SRC= '../icon?matchtitle=" + Engine.instance.cartridge.name + "&icon=" + media[i].name + "." + media[i].type + "' align='center'>"
                        + "<h4 align='center'>" + texts[i] + "</h4>"
                        + "</p>"
                        + "<p>";

                if (i < texts.length - 1) {

                    dialog = dialog + "<a href='#page" + (i + 1) + "' data-transition='slide' data-role='button'>" + Button1 + "</a>";
                } else {

                    dialog = dialog + "<a href='./dialogCallback.jsp?button1=Button1' data-role='button' target='_top'>" + Button1 + "</a>";


                }


                if (Button2 != null) {

                    if (!Button2.equals("null")) {
                        dialog = dialog + "<a href='#/callBack.jsp?button2=Button2' data-role='button' target='_top'>" + Button2 + "</a>";


                    }

                }

                dialog = dialog + "</p>"
                        + "</div>"
                        + "<div data-role='footer' data-theme='b'>"
                        + "<h4>Powered by Playar</h4>"
                        + "</div>"
                        + "</div>";


            }


            dialog = dialog + "</div><body>";



            altText = alttext;
            // description = descriptions;
            rawName = rawname;
            dialogTexts = currentEvent;


            //showDialog(texts, media, button1, button2, callback);
            // TODO IMPLEMENTS THE MEDIA ARRAY GESTION FOR LAYAR

            dialogMedia = "null";

        } else {



            dialogTexts = texts[0];
            if (media[0] != null) {
                dialogMedia = media[0].name + "." + media[0].type;
            }

        }


    }

    public static String arrayToString(String[] a, String separator) {
        if (a == null || separator == null) {
            return null;
        }
        StringBuffer result = new StringBuffer();
        if (a.length > 0) {
            result.append(a[0]);
            for (int i = 1; i < a.length; i++) {
                result.append(separator);
                result.append(a[i]);
            }
        }
        return result.toString();
    }
    private ArrayList<String> options = new ArrayList<String>();

    public void pushInput(final EventTable input) {

        currentInput = input;
        String[] event = currentEvent.split("\\.");

        String inputMedia = "";

        LuaTable container = Engine.instance.cartridge.currentThings();

        Object key = null;
        while ((key = container.next(key)) != null) {
            Thing t = (Thing) container.rawget(key);
            if (t.isVisible()) {


                if (t.name.toString() != null) {

                    if (t.name.toString().equals(event[0])) {

                        if (t.media != null) {

                            inputMedia = t.media.name + "." + t.media.type;

                        }


                    }

                }

            }

        }


        String text = Engine.removeHtml((String) input.rawget("Text"));
        String inputText = (text);

        String type = (String) input.rawget("InputType");
        if ("Text".equals(type)) {
            // hide buttons
            buttonsVisible = false;

            triviaInput = "<body><div data-role='page' style='min-height:100%' data-theme='a'>"
                    + "<div data-role='header'  data-theme='b'>"
                    + "<h6>Trivia</h6>"
                    + "</div>"
                    + "<div data-role='content'  data-theme='a'>"
                    + "<form method='POST' action='dialogCallback.jsp?input=" + currentInput.name + "' target='_top' >"
                    + "<div data-role='fieldcontain'  data-theme='a' align='center'>"
                    + "<p><IMG SRC= '../icon?matchtitle=" + Engine.instance.cartridge.name + "&icon=" + inputMedia + "' align='center'>"
                    + "<h4 align='center'>" + text + "</h4></p>"
                    + " </div>"
                    + "<div data-role='fieldcontain'   data-theme='a'>"
                    + "<input type='text' name='answer' id='answer' value=''  />"
                    + "</div>"
                    + "<input type='submit'  id='submit' data-theme='a' value='OK'/>"
                    + "</form>"
                    + "</div>"
                    + "<div data-role='footer' data-theme='b'>"
                    + "<h4>Powered by Playar</h4>"
                    + "</div>"
                    + "</div>"
                    + "</body>";



        } else if ("MultipleChoice".equals(type)) {

            LuaTable choices = (LuaTable) input.rawget("Choices");
            int n = choices.len();
            // make sure we have enough buttons
            for (int i = options.size(); i < n; i++) {
                String jb = new String();
                options.add(jb);
                //addButton(jb);
            }
            // set up choices
            for (int i = 0; i < n; i++) {
                String choice = (String) choices.rawget(new Double(i + 1));
                String jb = options.get(i);
                jb = choice;
                //jb.setVisible(true);
            }
            // hide the rest
//			for (int i = n; i < options.size(); i++) {
//				options.get(i).setVisible(false);
//			}
        }
//		rightPanel.revalidate();
        //session.setAttribute("input", input.toString());
    }

    /** Actually perform the {@link #showScreen()} action.
     * Display the specified submenu, and in case of Details,
     * figure out which submenu should be visible.
     */
    protected void doShowScreen(int screenId, EventTable details) {
        ////dialog.close();
        switch (screenId) {
            case UI.MAINSCREEN:
                // nop
                break;
            case UI.LOCATIONSCREEN:
                ////showSubmenu("zones");
                break;
            case UI.ITEMSCREEN:
                ////showSubmenu("yousee");
                break;
            case UI.INVENTORYSCREEN:
                ////showSubmenu("inventory");
                break;
            case UI.TASKSCREEN:
                ////showSubmenu("tasks");
                break;
            case UI.DETAILSCREEN:
                // figure out which submenu
                showDetails(details);
                break;

        }
    }

    public void showScreen(final int screenId, final EventTable details) {
    }

    public void playSound(Media media) {

        mediaName = media.name;
        mediaType = media.type;
        mediaOverride = media.altText;


        System.out.println("Play media fired !!!!!!!!!!!" + mediaName + "." + mediaType + "LAYAR_OVERRIDE...:" + mediaOverride);
    }

    public void blockForSaving() {
    }

    /** Hides the blocking dialog. If <code>closeAfterSave</code> is true, kills the window. */
    public void unblock() {
    }
}
