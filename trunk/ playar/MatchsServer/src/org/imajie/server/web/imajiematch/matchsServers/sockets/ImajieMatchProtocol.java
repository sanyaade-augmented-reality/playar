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

import org.imajie.server.web.imajiematch.matchsServers.main.GameWindow;
import org.imajie.server.web.imajiematch.matchsServers.openwig.Engine;

/**
 *
 * @author Carl Tremblay <carl_tremblay at imajie.tv>
 */
public class ImajieMatchProtocol {

    /* **********************************************
     *************************************************
     *            Commands Definition
     *********************************************** */
    private static final int WAITING = 0;
    private static final int COMMAND = 1;
    private static final int CONFIRM = 2;
    private static final int KILL_THREAD = 10;
    private static final int REFRESH_LOCATION = 4;
    private static final int CALLBACK = 3;
    private int state = WAITING;
    private boolean firstStart = true;

    public String processInput(String theInput) {
        String theOutput = null;

        if (state == WAITING) {
            theOutput = Engine.instance.username();
            state = COMMAND;
        } /* **********************************************
         *************************************************
         *                Match Commands
         *********************************************** */ //
        else if (state == COMMAND) {

            while (!Engine.prepareStateFinish == true) {
            }




            //***** Player Refresh Location ************
            if (theInput.equalsIgnoreCase("REFRESH_LOCATION")) {

                theOutput = "GeoPosition?";
                state = REFRESH_LOCATION;
            } //***** Procces and thread killing Calls*****
            else if (theInput.equalsIgnoreCase("KILL_THREAD")) {

                theOutput = "Process?";

                state = KILL_THREAD;
            } //*****      Procces SAVE game      *****
            else if (theInput.equalsIgnoreCase("SAVE")) {

                Engine.requestSync();
                PlayerServer.resetTimer();
                theOutput = "OK";

                state = COMMAND;
            } //***** Procces Call for a actual player state  *****
            else if (theInput.equalsIgnoreCase("STATE")) {


                //************ Build the result player state and return it
                String result = "PLAYER_STATE||||||";
                result = result + "ZONE" + GameWindow.zoneList + "|!!!|";
                result = result + "TASK" + GameWindow.tasksList + "|!!!|";
                result = result + "INVENTORY" + GameWindow.inventoryList + "|!!!|";
                result = result + "YOUSEE" + GameWindow.youseeList + "|!!!|";
                result = result + "BUTTON1" + GameWindow.Button1 + "|!!!|";
                result = result + "BUTTON2" + GameWindow.Button2 + "|!!!|";
                result = result + "DIALOGTEXT" + GameWindow.dialogTexts + "|!!!|";
                result = result + "DIALOGMEDIA" + GameWindow.dialogMedia + "|!!!|";
                result = result + "MEDIAALTTEXT" + GameWindow.altText + "|!!!|";
                result = result + "MEDIADESCRIPTION" + GameWindow.description + "|!!!|";
                result = result + "MEDIARAWNAME" + GameWindow.rawName + "|!!!|";
                result = result + "ZONECOUNT" + GameWindow.zoneCount + "|!!!|";
                result = result + "TASKCOUNT" + GameWindow.tasksCount + "|!!!|";
                result = result + "INVENTORYCOUNT" + GameWindow.inventoryCount + "|!!!|";
                result = result + "YOUSEECOUNT" + GameWindow.youseeCount + "|!!!|";
                result = result + "PLAYMEDIA_CALL" + GameWindow.mediaName + "||" + GameWindow.mediaType + "||" + GameWindow.mediaOverride + "|!!!|";
                if (GameWindow.triviaInput != null) {
                    if (GameWindow.triviaInput != "") {
                        
                        result = result + "DIALOG" + GameWindow.triviaInput + "|!!!|";
                        
                        
                        
                    } else {
                        result = result + "DIALOG" + GameWindow.dialog + "|!!!|";
                    }
                } else {

                    result = result + "DIALOG" + GameWindow.dialog + "|!!!|";
                }


                GameWindow.triviaInput = "";


                if (!GameWindow.dialogTexts.equals(GameWindow.currentEvent)) {

                    result = result + "SHOWDIALOG" + GameWindow.dialogTexts + "||" + GameWindow.dialogMedia + "|!!!|";
                } else if (GameWindow.dialogTexts.equals(GameWindow.currentEvent)) {

                    result = result + "SHOWDIALOGnull|||!!!|";
                }

                result = result + "CURRENTEVENT" + GameWindow.currentEvent + "|!!!|";

                //

                theOutput = result + "|!!|PLAYER_STATE";

                //theOutput = result;
               // System.out.println("State Refresh");
                state = COMMAND;
            } //***** Return input From Player  *****
            else if (theInput.equalsIgnoreCase("CALLBACK")) {

                state = CALLBACK;
                theOutput = "Input?";


            } //***** Return to the initial state if UNKNOWN  *****
            else {
                theOutput = "";
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

            } else {

                theOutput = "";
                state = COMMAND;


            }
        } /* **********************************************
         *************************************************
         *          Refresh Location Command Method
         *********************************************** */ //
        else if (state == REFRESH_LOCATION) {
            if (theInput.contains("GEOPOSITION||")) {

                //    Array to parse     lat||lon||alt||accuracy
                String position = theInput.replace("GEOPOSITION||", "");
                String delimiter = "\\|\\|";
                String[] temp = position.split(delimiter);

                Double lat = null;
                Double lon = null;
                Double alt = null;
                Double accuracy = null;

                for (int ii = 0; ii < temp.length; ii++) {
                    if (ii == 0) {
                        lat = Double.parseDouble(temp[ii]);;
                    }
                    if (ii == 1) {
                        lon = Double.parseDouble(temp[ii]);;
                    }
                    if (ii == 2) {
                        alt = Double.parseDouble(temp[ii]);;
                    }
                    if (ii == 3) {
                        accuracy = Double.parseDouble(temp[ii]);;
                    }


                }

                Engine.instance.lat = lat;
                Engine.instance.lon = lon;
                Engine.instance.alt = alt;
                
                Engine.instance.accuracy = accuracy;
                 
                Engine.instance.player.refreshLocation();
                //Engine.playerRefresh();
//                Engine.ui.refreshPlayer();
//                Engine.ui.refresh();
//      
//                Engine.instance.cartridge.tick();
                Engine.requestSync();
                



                //************ Build the result player state and return it
                String result = "PLAYER_STATE||||||";
                result = result + "ZONE" + GameWindow.zoneList + "|!!!|";                  // 0
                result = result + "TASK" + GameWindow.tasksList + "|!!!|";                 // 1
                result = result + "INVENTORY" + GameWindow.inventoryList + "|!!!|";        // 2
                result = result + "YOUSEE" + GameWindow.youseeList + "|!!!|";              // 3
                result = result + "BUTTON1" + GameWindow.Button1 + "|!!!|";                // 4
                result = result + "BUTTON2" + GameWindow.Button2 + "|!!!|";                // 5
                result = result + "DIALOGTEXT" + GameWindow.dialogTexts + "|!!!|";         // 6
                result = result + "DIALOGMEDIA" + GameWindow.dialogMedia + "|!!!|";        // 7
                result = result + "MEDIAALTTEXT" + GameWindow.altText + "|!!!|";           // 8
                result = result + "MEDIADESCRIPTION" + GameWindow.description + "|!!!|";   // 9
                result = result + "MEDIARAWNAME" + GameWindow.rawName + "|!!!|";           // 10
                result = result + "ZONECOUNT" + GameWindow.zoneCount + "|!!!|";            // 11
                result = result + "TASKCOUNT" + GameWindow.tasksCount + "|!!!|";           // 12
                result = result + "INVENTORYCOUNT" + GameWindow.inventoryCount + "|!!!|";  // 13
                result = result + "YOUSEECOUNT" + GameWindow.youseeCount + "|!!!|";        // 14
                result = result + "PLAYMEDIA_CALL" + GameWindow.mediaName + "." + GameWindow.mediaType + "||" + GameWindow.mediaOverride + "|!!!|"; // 15
                if (GameWindow.triviaInput != null) {
                    if (GameWindow.triviaInput != "") {
                        
                        result = result + "DIALOG" + GameWindow.triviaInput + "|!!!|";
                        
                        
                        
                    } else {
                        result = result + "DIALOG" + GameWindow.dialog + "|!!!|";
                    }
                } else {

                    result = result + "DIALOG" + GameWindow.dialog + "|!!!|";
                }


                GameWindow.triviaInput = "";



                if (!GameWindow.dialogTexts.equals(GameWindow.currentEvent)) {

                    result = result + "SHOWDIALOG" + GameWindow.dialogTexts + "||" + GameWindow.dialogMedia + "|!!!|";      // 17
                } else if (GameWindow.dialogTexts.equals(GameWindow.currentEvent)) {

                    result = result + "SHOWDIALOGnull|||!!!|";
                }
                result = result + "CURRENTEVENT" + GameWindow.currentEvent + "|!!!|";      // 18
                theOutput = result + "|!!|PLAYER_STATE";

                //System.out.println("Geolocation Refresh");
                state = COMMAND;

            }

        } /* **********************************************
         *************************************************
         *     Player Input CallBack Command Method
         *********************************************** */ //
        else if (state == CALLBACK) {
            if (theInput.contains("PLAYER_CALLBACK")) {

                String resultRequest = theInput.replace("PLAYER_CALLBACK", "");

                if (resultRequest.contains("Button") && !resultRequest.contains("thing")) {


                    theOutput = "PLAYER_CALLBACK_STATE||REFRESH";
                    Engine.ui.callAndClose(resultRequest);

                } else if (resultRequest.equals("CANCEL")) {
                    Engine.ui.cancel();
                    theOutput = "PLAYER_CALLBACK_STATE||REFRESH";


                } else if (resultRequest.contains("thingButton")) {
                    // TODO IMPLEMENTS THINGS BUTTON CALLBACK FOR LAYAR POIS

                    Engine.ui.thingsButton(resultRequest);

                    theOutput = "PLAYER_CALLBACK_STATE||REFRESH";


                }else if (resultRequest.contains("input")) {
                    // TODO IMPLEMENTS THINGS BUTTON CALLBACK FOR LAYAR POIS

                    String inputRequest = resultRequest.replace("input", "");

                    Engine.callEvent(GameWindow.currentInput, "OnGetInput", inputRequest);
                            
                    theOutput = "PLAYER_CALLBACK_STATE||REFRESH";


                }
                
                else {

                    theOutput = "PLAYER_CALLBACK_STATE||";

                }
               
//                Engine.instance.player.refreshLocation();
//    
//                Engine.ui.refresh();
//                Engine.playerRefresh();
//                Engine.refreshUI();
//                Engine.instance.cartridge.tick();
//
//                
               // System.out.println("Callback Refresh");
                state = COMMAND;

            }
        }

        return theOutput;
    }
}
