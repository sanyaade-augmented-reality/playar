/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imajie.server.web.imajiematch.matchsServers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.imajie.server.web.imajiematch.matchsServers.sockets.MainServerClientProtocol;

/**
 *
 * @author Carl Tremblay
 */
public class GetMatchsJspBean implements Serializable {

    private static final String[] wordsNS = {"", "north", "south"};
    private static final String[] wordsEW = {"", "east", "west"};
    private static Logger logger = Logger.getLogger(GetMatchsJspBean.class.getName());
    public static boolean processFinish = false;

    public String getCount(HttpServletRequest request) {

        HttpSession session = request.getSession(true);

        String radiusSession = "10000";
        String latSession = "0";
        String lonSession = "0";

        if (session.getAttribute("radius") != null) {

            radiusSession = session.getAttribute("radius").toString();

        } else {
            radiusSession = "10000";
        }

        if (session.getAttribute("lat") != null) {

            latSession = session.getAttribute("lat").toString();

        }

        if (session.getAttribute("lon") != null) {

            lonSession = session.getAttribute("lon").toString();

        }




        int port = 4000;
        String host = "localhost";

        try {
            Socket imajieMatchSocket = null;
            PrintWriter out2 = null;
            BufferedReader in = null;

            try {
                imajieMatchSocket = new Socket(host, port);
                out2 = new PrintWriter(imajieMatchSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(imajieMatchSocket.getInputStream()));
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host: " + host + ".");

            } catch (IOException e) {


                System.err.println("Couldn't get I/O for the connection to: " + host + ".");

            }



            String fromServer;
            String fromUser;
            MainServerClientProtocol imp = new MainServerClientProtocol(session, 4);

            fromUser = imp.processInput(null, session, request);
            out2.println(fromUser);


            while ((fromServer = in.readLine()) != null) {
                System.out.println(fromServer);

                fromUser = imp.processInput(fromServer, session, request);
                if (fromUser != null) {
                    System.out.println(fromUser);
                    out2.println(fromUser);
                }

                if (fromServer.equals("Bye.")) {

                    break;
                }
                if (fromServer.equals("Me?")) {

                    break;
                }


            }


            System.out.println("Process finish");
            out2.close();
            in.close();

            imajieMatchSocket.close();


            System.out.println("Process finish");

        } catch (IOException ex) {

            Logger.getLogger(GetMatchsJspBean.class.getName()).log(Level.SEVERE, null, ex);
        }


        String msg = "";

        if (session.getAttribute("MatchsList") != null) {

            msg = session.getAttribute("MatchsList").toString();



        }

        if (msg.length() < 4) {

            msg = "<li>No mission available at this location. Please try to adjust the Radar Range in the parameter menu below.</li>";

        }

        return msg;
    }

    public String getMatchDetails(HttpServletRequest request) {

        HttpSession session = request.getSession(true);



        String match = request.getParameter("match");

        String msg = "";

        

        
            int port = 4000;
            String host = "localhost";
            
            try {
            Socket imajieMatchSocket = null;
            PrintWriter out2 = null;
            BufferedReader in = null;

            try {
                imajieMatchSocket = new Socket(host, port);
                out2 = new PrintWriter(imajieMatchSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(imajieMatchSocket.getInputStream()));
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host: " + host + ".");
                
            } catch (IOException e) {

             
                System.err.println("Couldn't get I/O for the connection to: " + host + ".");
               
            }

            
           
            String fromServer;
            String fromUser;
            MainServerClientProtocol imp = new MainServerClientProtocol(session, 5);

            fromUser = imp.processInput(null,session,request);
            out2.println(fromUser);


            while ((fromServer = in.readLine()) != null) {
                System.out.println(fromServer);

                fromUser = imp.processInput(fromServer, session,request);
                if (fromUser != null) {
                    System.out.println(fromUser);
                    out2.println(fromUser);
                }

                if (fromServer.equals("Bye.")) {

                    break;
                }
                if (fromServer.equals("Me?")) {

                    break;
                }


            }


            System.out.println("Process finish");
            out2.close();
            in.close();

            imajieMatchSocket.close();


            System.out.println("Process finish");

        } catch (IOException ex) {
            
            Logger.getLogger(GetMatchsJspBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        


        msg = session.getAttribute("MatchDetails").toString();

        return msg;
    }
}
