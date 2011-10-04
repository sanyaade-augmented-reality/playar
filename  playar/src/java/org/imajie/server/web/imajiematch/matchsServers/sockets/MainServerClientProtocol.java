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
public class MainServerClientProtocol {

    /* **********************************************
     *************************************************
     *            Commands Definition
     *********************************************** */
    private static final int START = 0;
    private static final int NEW_GAME = 1;
    private static final int RESTORE_GAME = 2;
    private static final int PLAYER_MESSAGE = 3;
    private static final int GET_MATCHS = 4;
    private static final int MATCH_DETAILS = 5;
    private static final int CALL_FINNISH = 6;
    private static final int GET_LAYAR_REFRESH = 7;
    private static final int GET_PORT = 8;
    private static final int FINNISH = 9;
    private static final int KILL_THREAD = 10;
    private static final int KILL_GAME = 11;
    private static final int MATCHS = 12;
    private static final int SESSION_MATCH_DETAILS = 13;
    private static final int LAYAR_REFRESH = 14;
    private int state = START;
    public  int stateAfterStart = START;
    
    
    public static String port = "777";
    public static String username = "Alfred";
    public static String lat = "0";
    public static String lon = "0";
    public static String alt = "0";
    public static String accuracy = "10";
    //public static String match = MainServerClient.matchToPlay;
 

    public MainServerClientProtocol(HttpSession session, int Requeststate) {
        
        stateAfterStart = Requeststate;
        
        
        
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    public String processInput(String theInput,HttpSession session, HttpServletRequest request ) {
        String theOutput = null;

        if (state == START) {

            if (theInput != null) {

                if (theInput.equals("Ready?")) {

                    state = stateAfterStart;

                    if (stateAfterStart == NEW_GAME) {
                        theOutput = "NEW_GAME";
                    }
                    if (stateAfterStart == RESTORE_GAME) {
                        theOutput = "RESTORE_GAME";
                    }
                    if (stateAfterStart == PLAYER_MESSAGE) {
                        theOutput = "PLAYER_MESSAGE";
                    }
                    if (stateAfterStart == GET_MATCHS) {
                        theOutput = "GET_MATCHS";
                    }
                    if (stateAfterStart == MATCH_DETAILS) {
                        theOutput = "MATCH_DETAILS";
                    }
                    if (stateAfterStart == GET_LAYAR_REFRESH) {
                        theOutput = "GET_LAYAR_REFRESH";
                    }
                    if (stateAfterStart == KILL_THREAD) {
                        theOutput = "KILL_THREAD";
                    }
                    if (stateAfterStart == KILL_GAME) {
                        theOutput = "KILL_THREAD";
                    }

                } else {


                    state = KILL_THREAD;
                    theOutput = "KILL_THREAD";

                }
            }


        } /* **********************************************
         *************************************************
         *        Start a new Game
         *********************************************** */ //
        else if (state == NEW_GAME) {
            if (theInput != null) {
                if (theInput.equalsIgnoreCase("NEW_GAME?")) {

                    theOutput = "START||||||" + session.getAttribute("username") + "||" + session.getAttribute("lat") + "||" + session.getAttribute("lon") + "||" + session.getAttribute("alt") + "||" + session.getAttribute("accuracy") + "||" + request.getParameter("match") + "";
                    state = GET_PORT;

                }
            }
        } /* **********************************************
         *************************************************
         *  Get the socket port of the Virtual Player Server
         *********************************************** */ //
        else if (state == GET_PORT) {
            if (theInput != null) {
                
                // TODO IMPLEMENTS THE HANDLE OF THIS ON THE RETURNED DISPLAY TO THE PLAYER
                //FOR NOW it only handle playing or not playing without warnings
                
                if (theInput.contains("HAVE_FUN::PORT::")) {

                    session.setAttribute("PlayerServerSocketPort", theInput.replace("HAVE_FUN::PORT::", ""));
                    session.setAttribute("gameStarted", request.getParameter("match"));
                    theOutput = "KILL_THREAD";
                    state = KILL_THREAD;

                }
                if (theInput.contains("SERVER_FULL")) {

                    
                    session.setAttribute("gameStarted", "SERVER_FULL");
                    
                    theOutput = "KILL_THREAD";
                    state = KILL_THREAD;

                }
                if (theInput.contains("ALREADY_PLAYING")) {

                   
                    session.setAttribute("gameStarted", "ALREADY_PLAYING");
                    
                    theOutput = "KILL_THREAD";
                    state = KILL_THREAD;

                }
            }
        } /* **********************************************
         *************************************************
         *        Refresh layar player state
         *********************************************** */ //
        else if (state == GET_LAYAR_REFRESH) {
            if (theInput != null) {
                if (theInput.equalsIgnoreCase("Location?")) {
//"|!!!|LAYAR_REFRESH"
                   
                    String latitude = "0";
                    String longitude = "0";
                    String radius = "10000";
                    
                    if (session.getAttribute("lat") != null) {
                    
                        latitude = session.getAttribute("lat").toString();
                    
                    }
                    if (session.getAttribute("lon") != null) {
                    
                        longitude = session.getAttribute("lon").toString();
                    
                    }
                     if (session.getAttribute("radius") != null) {
                    
                        radius = session.getAttribute("radius").toString();
                    
                    }
                    

                    theOutput = "LOCATION||||||" 
                            + latitude + "||" 
                            + longitude + "||" 
                            + radius;
                    
                    state = LAYAR_REFRESH;


                }
            }
        } 
        
         /* **********************************************
         *************************************************
         *        Refresh layar player state to session
         *********************************************** */ //
        else if (state == LAYAR_REFRESH) {
            if (theInput != null) {
                
                
                  matchDetails = matchDetails + theInput.toString();


//                }
                
                if (theInput.contains("|!!!|LAYAR_REFRESH")) {
                        
                        matchDetails = matchDetails.replace("LAYAR_REFRESH||||||", "");
                        matchDetails = matchDetails.replace("|!!!|LAYAR_REFRESH", "");
                    session.setAttribute("LayarMatchsList", matchDetails);
                    theOutput = "KILL_THREAD";
                    state = KILL_THREAD;
                    
                }
                
                
                
                
            } else { 
                
                state = LAYAR_REFRESH;
            
            }
        }
        /* **********************************************
         *************************************************
         *        Restore a Game
         *********************************************** */ //
        else if (state == RESTORE_GAME) {
            if (theInput != null) {
                if (theInput.equalsIgnoreCase("RESTORE_GAME?")) {

                    theOutput = "RESTORE||||||" + session.getAttribute("username") + "||" + session.getAttribute("lat") + "||" + session.getAttribute("lon") + "||" + session.getAttribute("alt") + "||" + session.getAttribute("accuracy") + "||" + request.getParameter("match") + "";
                    state = GET_PORT;


                }
            }
        } /* **********************************************
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
        } /* **********************************************
         *************************************************
         *        GEGet the list of the nearest matchs
         *********************************************** */ //
        else if (state == GET_MATCHS) {
            if (theInput != null) {
                if (theInput.equalsIgnoreCase("LOCATION?")) {

                    String latitude = "0";
                    String longitude = "0";
                    String rad = "10000";
                    
                    
                    
                    if (session.getAttribute("lat") != null)  {
                    
                        latitude = session.getAttribute("lat").toString();
                    
                    }  
                    
                    if (session.getAttribute("lon") != null)  {
                    
                        longitude = session.getAttribute("lon").toString();
                    
                    } 
                    
                    if (session.getAttribute("radius") != null)  {
                    
                        rad = session.getAttribute("radius").toString();
                    
                    } 
                    
                    

                    theOutput = "LOCATION||||||" 
                            + latitude + "||" 
                            + longitude + "||" 
                            + rad;
                    
                    state = MATCHS;


                }
            }
        } /* **********************************************
         *************************************************
         *        Get match details
         *********************************************** */ //
        else if (state == MATCH_DETAILS) {
            if (theInput != null) {
                if (theInput.equalsIgnoreCase("MATCH_DETAILS?")) {

                    
                    // TODO Probleme ici avec la variable match. Qui l'assigne et la declare ?
                    theOutput = "MATCH||" + session.getAttribute("lat") + "||" + session.getAttribute("lon") + "||" + request.getParameter("match");
                    state = SESSION_MATCH_DETAILS;


                }
            }
        } /* **********************************************
         *************************************************
         *        Set the matchs available list to session
         *********************************************** */ //
        else if (state == MATCHS) {
            if (theInput != null) {
                if (theInput.contains("MATCHS_LIST||||||")) {

                    session.setAttribute("MatchsList", theInput.replace("MATCHS_LIST||||||", ""));

                    theOutput = "KILL_THREAD";
                    state = KILL_THREAD;

                }
            }
        } else if (state == SESSION_MATCH_DETAILS) {
            if (theInput != null) {
                
//                if (theInput.contains("MATCH_DETAILS||")) {

                    matchDetails = matchDetails + theInput.toString();


//                }
                
                if (theInput.contains("MATCH_DETAILS|!!|")) {
                        
                        matchDetails = matchDetails.replace("MATCH_DETAILS||", "");
                        matchDetails = matchDetails.replace("MATCH_DETAILS|!!|", "");
                    session.setAttribute("MatchDetails", matchDetails);
                    theOutput = "KILL_THREAD";
                    state = KILL_THREAD;
                    
                }
            } else { state = SESSION_MATCH_DETAILS;
            
            }
        } else if (state == KILL_THREAD) {
            if (theInput != null) {
                if (theInput.equalsIgnoreCase("Process?")) {

                    theOutput = "Connection";
                    state = FINNISH;


                } else {

                    theOutput = "KILL_THREAD";
                    state = KILL_THREAD;

                }
            }
        } else if (state == KILL_GAME) {
            if (theInput != null) {
                if (theInput.equalsIgnoreCase("Process?")) {

                    theOutput = "GAME||||||" + request.getParameter("match");
                    state = FINNISH;

                    session.setAttribute("PlayerServerSocketPort", 0);
                    session.setAttribute("gameStarted", "none");

                } else {

                    theOutput = "KILL_THREAD";
                    state = KILL_THREAD;


                }
            }
        } /* **********************************************
         *************************************************
         *     Finnish process Method
         *********************************************** */ //
        else if (state == CALL_FINNISH) {
        }
        
        else if (state == FINNISH) {
            
            
            
            
        }
        


        return theOutput;
    }

    private String matchDetails = "";
    public static void waiting(int n) {

        long t0, t1;

        t0 = System.currentTimeMillis();

        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < (n));
    }
}
