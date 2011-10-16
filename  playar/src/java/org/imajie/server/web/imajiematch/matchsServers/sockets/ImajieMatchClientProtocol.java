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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Carl Tremblay <carl_tremblay at imajie.tv>
 */
public class ImajieMatchClientProtocol {

    /* **********************************************
     *************************************************
     *            Commands Definition
     *********************************************** */
    private static final int START = 0;
    private static final int COMMAND = 1;
    private static final int CONFIRM = 2;
    private static final int CALLBACK = 3;
    private static final int REFRESH_LOCATION = 4;
    private static final int PLAYER_CALLBACK = 5;
    private static final int STATE = 6;
    private static final int SAVE = 7;
    private static final int CALL_FINNISH = 8;
    private static final int FINNISH = 9;
    private static final int KILL_THREAD = 10;
    private static final int GET_LAYAR_REFRESH_POI = 11;
    private int state = START;
    private String playerState = "";

    public String processInput(String theInput, HttpSession session, HttpServletRequest request) {
        String theOutput = null;

        if (state == START) {
            System.out.println("START");

            if (theInput != null) {

                if (theInput.equals(session.getAttribute("username").toString())) {



                    theOutput = "REFRESH_LOCATION";
                    System.out.println("REFRESH_LOCATION");
                    state = REFRESH_LOCATION;
                    // waiting(300);

                } else {



                    theOutput = "KILL_THREAD";
                    System.out.println("KILL_THREAD");
                    state = KILL_THREAD;
                    // waiting(300);


                }


            } else {

                state = START;

            }


        } /* **********************************************
         *************************************************
         *        Kill Process and Threads Commands
         *********************************************** */ //
        else if (state == KILL_THREAD) {
            if (theInput != null) {
                if (theInput.equalsIgnoreCase("Process?")) {

                    theOutput = "Connection";

                    //waiting(300);
                    System.out.println("CALL_FINNISH");
                    state = CALL_FINNISH;
                }
            } else {

                state = KILL_THREAD;

            }
        } /* **********************************************
         *************************************************
         *        Refresh layar player state
         *********************************************** */ //
        else if (state == GET_LAYAR_REFRESH_POI) {
            if (theInput != null) {
                if (theInput.equalsIgnoreCase("GET_LAYAR_REFRESH_POI?")) {

                    // TODO IMPLEMENTS GET_LAYAR_REFRESH_POI

                    theOutput = "";

                    //waiting(300);
                    System.out.println("KILL_THREAD");
                    state = KILL_THREAD;
                }
            } else {

                state = GET_LAYAR_REFRESH_POI;

            }
        } /* **********************************************
         *************************************************
         *          Refresh Location Command Method
         *********************************************** */ //
        else if (state == REFRESH_LOCATION) {
            if (theInput != null) {
                if (theInput.equalsIgnoreCase("GeoPosition?")) {


                    theOutput = "GEOPOSITION||" + session.getAttribute("lat").toString() + "||" + session.getAttribute("lon").toString() + "||" + session.getAttribute("alt").toString() + "||" + session.getAttribute("accuracy").toString();

                    System.out.println("STATE");
                    state = STATE;
                    //waiting(300);


                }
            } else {

                state = REFRESH_LOCATION;

            }


        } /* **********************************************
         *************************************************
         *     Player State Command Method
         *********************************************** */ //
        else if (state == STATE) {
            playerState = playerState + theInput.toString();
            if (theInput != null) {
                if (theInput.contains("PLAYER_STATE||||||")) {
                    //PLAYER_STATE||||||
                    //[ZONE]zoneList[]
                    //[TASK]tasksList[]
                    //[INVENTORY]inventoryList[]
                    //[YOUSEE]youseeList[]
                    //[BUTTON1]Button1[]
                    //[BUTTON2]Button2[]
                    //[DIALOGTEXT]dialogTexts[]
                    //[DIALOGMEDIA]dialogMedia 
                    //MEDIAALTTEXT altText |!!!|
                    //MEDIADESCRIPTION description |!!!|  
                    //MEDIARAWNAME rawName |!!!| 
                    //  ZONECOUNT zoneCount
                    //  TASKCOUNT tasksCount
                    //  INVENTORYCOUNT inventoryCount
                    //  YOUSEECOUNT youseeCount
                    //waiting(300);
                }
                if (theInput.contains("|!!|PLAYER_STATE")) {

                    playerState = playerState.replace("|!!|PLAYER_STATE", "");

                    String resultArrays = playerState.replace("PLAYER_STATE||||||", "");

                    String delimiter = "\\|!!!\\|";
                    String[] temp = resultArrays.split(delimiter);

                    for (int ii = 0; ii < temp.length; ii++) {

                        if (ii == 0) {

                            session.setAttribute("zoneList", temp[ii]);

                        }
                        if (ii == 1) {

                            session.setAttribute("tasksList", temp[ii]);
                        }
                        if (ii == 2) {
                            session.setAttribute("inventoryList", temp[ii]);
                        }
                        if (ii == 3) {
                            session.setAttribute("youseeList", temp[ii]);
                        }
                        if (ii == 4) {
                            session.setAttribute("Button1", temp[ii].replace("BUTTON1", ""));
                        }
                        if (ii == 5) {
                            session.setAttribute("Button2", temp[ii].replace("BUTTON2", ""));
                        }
                        if (ii == 6) {
                            session.setAttribute("dialogTexts", temp[ii].replace("DIALOGTEXT", ""));
                        }
                        if (ii == 7) {
                            session.setAttribute("dialogMedia", temp[ii]);
                        }
                        if (ii == 8) {
                            String MEDIAALTTEXT = temp[ii].replace("MEDIAALTTEXT", "");
                            MEDIAALTTEXT = MEDIAALTTEXT.replace("|!!!|", "");
                            session.setAttribute("MEDIAALTTEXT", MEDIAALTTEXT);
                        }
                        if (ii == 9) {
                            String MEDIADESCRIPTION = temp[ii].replace("MEDIADESCRIPTION", "");
                            MEDIADESCRIPTION = MEDIADESCRIPTION.replace("|!!!|", "");
                            session.setAttribute("MEDIADESCRIPTION", MEDIADESCRIPTION);
                        }
                        if (ii == 10) {
                            String MEDIARAWNAME = temp[ii].replace("MEDIARAWNAME", "");
                            MEDIARAWNAME = MEDIARAWNAME.replace("|!!!|", "");
                            session.setAttribute("MEDIARAWNAME", MEDIARAWNAME);
                        }
                        if (ii == 11) {
                            String zoneCount = temp[ii].replace("ZONECOUNT", "");
                            zoneCount = zoneCount.replace("|!!!|", "");
                            session.setAttribute("zoneCount", zoneCount);
                        }
                        if (ii == 12) {
                            String tasksCount = temp[ii].replace("TASKCOUNT", "");
                            tasksCount = tasksCount.replace("|!!!|", "");
                            session.setAttribute("tasksCount", tasksCount);
                        }
                        if (ii == 13) {
                            String inventoryCount = temp[ii].replace("INVENTORYCOUNT", "");
                            inventoryCount = inventoryCount.replace("|!!!|", "");
                            session.setAttribute("inventoryCount", inventoryCount);
                        }
                        if (ii == 14) {
                            String youseeCount = temp[ii].replace("YOUSEECOUNT", "");
                            youseeCount = youseeCount.replace("|!!!|", "");
                            session.setAttribute("youseeCount", youseeCount);
                        }
                        if (ii == 15) {
                            String PLAYMEDIA_CALL = temp[ii].replace("PLAYMEDIA_CALL", "");
                            PLAYMEDIA_CALL = PLAYMEDIA_CALL.replace("|!!!|", "");
                            session.setAttribute("PLAYMEDIA_CALL", PLAYMEDIA_CALL);
                        }
                        if (ii == 16) {
                            String DIALOG = temp[ii].replace("DIALOG", "");
                            DIALOG = DIALOG.replace("|!!!|", "");
                            session.setAttribute("DIALOG", DIALOG);
                        }
                        if (ii == 17) {
                        
                            String SHOWDIALOG = temp[ii].replace("SHOWDIALOG", "");
                            SHOWDIALOG = SHOWDIALOG.replace("|!!!|", "");

                            String delimiter2 = "\\|\\|";
                            String[] temp2 = SHOWDIALOG.split(delimiter2);

                            for (int iii = 0; iii < temp2.length; iii++) {

                                if (temp2[0].equals("null") || temp2[0].equals("") || temp2[0].equals(null)) {

                                    session.setAttribute("showDialog", "null");

                                } else {

                                    session.setAttribute("showDialog", temp2[0]);
                                    session.setAttribute("dialogMedia", temp2[1]);


                                }


                            }


                        }
                        if (ii == 18) {
                            //"PLAYMEDIA_CALL" + GameWindow.youseeCount+ "";
                            String CURRENTEVENT = temp[ii].replace("CURRENTEVENT", "");
                            CURRENTEVENT = CURRENTEVENT.replace("|!!!|", "");
                            session.setAttribute("CURRENTEVENT", CURRENTEVENT);
                        }

                    }



                    theOutput = "CALLBACK";

                    System.out.println("CALLBACK");
                    state = CALLBACK;
                }
            } else {

                state = STATE;

            }
        } /* **********************************************
         *************************************************
         *     Player Input CallBack Command Method
         *********************************************** */ //
        else if (state == CALLBACK) {

            if (theInput != null) {
                if (theInput.equalsIgnoreCase("Input?")) {
                    if (session.getAttribute("sendCallback") != null) {

                        if (session.getAttribute("sendCallback").toString().equals("true")) {

                            if (request.getParameter("button1") != null) {

                                theOutput = "PLAYER_CALLBACK" + "Button1";
                                state = PLAYER_CALLBACK;

                            } else if (request.getParameter("button2") != null) {

                                theOutput = "PLAYER_CALLBACK" + "Button2";
                                state = PLAYER_CALLBACK;
                            }
                            else if (request.getParameter("thingButton") != null) {

                                theOutput = "PLAYER_CALLBACK" + "thingButton"+request.getParameter("thingButton");
                                state = PLAYER_CALLBACK;
                            }else if (request.getParameter("input") != null) {

                                theOutput = "PLAYER_CALLBACK" + "input"+request.getParameter("answer");
                                state = PLAYER_CALLBACK;
                            } 
                            
                            else {
                                
                                theOutput = "PLAYER_CALLBACK";
                            state = PLAYER_CALLBACK;
                                
                            }



                        } else {

                            theOutput = "PLAYER_CALLBACK";
                            state = PLAYER_CALLBACK;
                        }


                    } else {

                        theOutput = "PLAYER_CALLBACK";
                        state = PLAYER_CALLBACK;
                    }




                    System.out.println("CALLBACK");

                    state = PLAYER_CALLBACK;
                    //waiting(300);
                } else {

                    state = CALLBACK;

                }
            } else {

                state = CALLBACK;

            }
        }/* **********************************************
         *************************************************
         *     Player Input CallBack Command Method
         *********************************************** */ //
        else if (state == PLAYER_CALLBACK) {

            if (theInput != null) {
                if (theInput.contains("PLAYER_CALLBACK_STATE||")) {

                    String resultRequest = theInput.replace("PLAYER_CALLBACK_STATE||", "");

                    if (resultRequest.equals("REFRESH")) {

                        session.setAttribute("REFRESH", "REFRESH");

                    } else {
                    }



                    theOutput = "SAVE";
                    state = SAVE;


                    // waiting(300);
                }
            } else {

                state = PLAYER_CALLBACK;

            }
        } /* **********************************************
         *************************************************
         *     Player CallBack State Method
         *********************************************** */ //
        else if (state == SAVE) {
            if (theInput != null) {
                if (theInput.equalsIgnoreCase("OK")) {



                    theOutput = "KILL_THREAD";
                    state = KILL_THREAD;


                    // waiting(300);
                }
            } else {

                state = SAVE;

            }
        } /* **********************************************
         *************************************************
         *     Finnish process Method
         *********************************************** */ //
        else if (state == CALL_FINNISH) {
        }


        return theOutput;
    }

    public static void waiting(int n) {

        long t0, t1;

        t0 = System.currentTimeMillis();

        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < (n));
    }
}
