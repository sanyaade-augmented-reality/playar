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

import org.imajie.server.web.imajiematch.matchsServers.openwig.Engine;


/**
 *
 * @author Carl Tremblay <carl_tremblay at imajie.tv>
 */
public class MainServerPlayerServerClientProtocol {

    /* **********************************************
     *************************************************
     *            Commands Definition
     *********************************************** */
    private static final int START = 0;
    private static final int PLAYER_MESSAGE = 3;
    private static final int CALL_FINNISH = 6;
    private static final int CALL_FINNISH_KILL_GAME = 7;
    private static final int FINNISH = 9;
    private static final int KILL_THREAD = 10;
    private static final int KILL_GAME = 11;
    private boolean killGame = false;

    private int state = START;
    public  int stateAfterStart = START;
    
    
    public static String port = "777";
    public static String username = "Alfred";
    public static String lat = "0";
    public static String lon = "0";
    public static String alt = "0";
    public static String accuracy = "10";
    public static String match = MainServerPlayerServerClient.matchToPlay;


    MainServerPlayerServerClientProtocol( int Requeststate) {
        
        stateAfterStart = Requeststate;
      
        
        
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    public String processInput(String theInput) {
        String theOutput = null;

        if (state == START) {

            if (theInput != null) {

                if (theInput.equals("Ready?")) {

                    state = stateAfterStart;

                    
                    if (stateAfterStart == PLAYER_MESSAGE) {
                        theOutput = "PLAYER_MESSAGE";
                    }                
                    if (stateAfterStart == KILL_THREAD) {
                        theOutput = "KILL_THREAD";
                    }
                    if (stateAfterStart == KILL_GAME) {
                        theOutput = "KILL_THREAD";
                    }

                } 
//                else {
//
//
//                    state = KILL_THREAD;
//                    theOutput = "KILL_THREAD";
//
//                }
            }


        }  /* **********************************************
         *************************************************
         *        PLAYER_MESSAGE
         *********************************************** */ //
        else if (state == PLAYER_MESSAGE) {
            if (theInput != null) {
                if (theInput.equalsIgnoreCase("PLAYER_MESSAGE?")) {

                    // TODO IMPLEMENT SEND MESSAGE TO OTHER PLAYER

                    theOutput = "";
                    state = KILL_THREAD;
                    //waiting(300);

                }
            }
        } else if (state == KILL_THREAD) {
            if (theInput != null) {
                if (theInput.equalsIgnoreCase("Process?")) {

                    theOutput = "Connection";
                    state = CALL_FINNISH;


                } else {

                    theOutput = "KILL_THREAD";
                    state = KILL_THREAD;

                }
            }
        } else if (state == KILL_GAME) {
            if (theInput != null) {
                if (theInput.equalsIgnoreCase("Process?")) {

                    theOutput = "GAME||||||" + Engine.Username;
                    state = CALL_FINNISH_KILL_GAME;

                    killGame = true;
                    
                   

                } 
//                else {
//
//                    theOutput = "KILL_THREAD";
//                    state = KILL_THREAD;
//
//
//                }
            }
        } /* **********************************************
         *************************************************
         *     Finnish process Method
         *********************************************** */ //
        else if (state == CALL_FINNISH) {
            
            if (killGame) {
            
            killGame=false;
            
            //waiting(2000);
            //System.exit(1);
            
            
            }
            
        }
        else if (state == CALL_FINNISH_KILL_GAME) {
            
           if (theInput.equalsIgnoreCase("GAME_KILLED")) {

                    theOutput = "KILL_THREAD";
                    state = KILL_THREAD;

                
                    
                   

                } 
            
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
