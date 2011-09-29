/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imajie.server.web.imajiematch.matchsServers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import org.imajie.server.web.imajiematch.matchsServers.layar.LayarServlet;
import org.imajie.server.web.imajiematch.matchsServers.sockets.ImajieMatchClientProtocol;

/**
 *
 * @author Carl Tremblay
 */
public class RefreshMatchsJspBean implements Serializable {

    private static Logger logger = Logger.getLogger(RefreshMatchsJspBean.class.getName());
    public static boolean processFinish = false;
    public static boolean saveFinish;

    public static String refresh(String gameStarted, String username, HttpServletRequest request) {

        HttpSession session = request.getSession(true);

        username = session.getAttribute("username").toString();

        session.setAttribute("luaStateReset", "true");
        String msg = refreshSelected(gameStarted, username, request);
        System.out.println("Start cartridge Process finish...\n");

        return msg;
    }

    public static void layarRefresh(String game, String user, HttpServletRequest request) {

        HttpSession session = request.getSession(true);

        session.setAttribute("luaStateReset", "true");
        layarRefreshSelected(game, user, request);
    }

    public String refresh2(HttpServletRequest request) {

        HttpSession session = request.getSession(true);

        String text = "";
        String media = "";

        if (session.getAttribute("dialogTexts") != null) {

            text = session.getAttribute("dialogTexts").toString();

        }

        if (session.getAttribute("dialogMedia") != null) {

            media = session.getAttribute("dialogMedia").toString();

        }
        if (session.getAttribute("button1") != null) {
            // text = session.getAttribute("button1").toString();
        }
        if (session.getAttribute("button2") != null) {
            // text = session.getAttribute("button2").toString();
        }
        if (session.getAttribute("callback") != null) {
            // text = session.getAttribute("callback").toString();
        }


        String task = media;
        String delimiter2 = "\\|\\|";
        String[] temp2 = task.split(delimiter2);

        String media1 = "";
        String media2 = "";
        String media3 = "";
        String media4 = "";
        String media5 = "";

        for (int ii = 0; ii < temp2.length; ii++) {
            if (ii == 0) {
                media1 = temp2[ii];
            }
            if (ii == 1) {
                media2 = temp2[ii];
            }
            if (ii == 2) {
                media3 = temp2[ii];
            }
            if (ii == 3) {
                media4 = temp2[ii];
            }
            if (ii == 4) {
                media5 = temp2[ii];
            }

        }

        String msg = "";


        return msg;
    }

    public static String refreshSelected(String gameStarted, String username, HttpServletRequest request) {

        HttpSession session = request.getSession(true);

        gameStarted = "none";

        if (session.getAttribute("gameStarted") != null) {

            gameStarted = session.getAttribute("gameStarted").toString();

        }




//        try {
//
//            int startState = 0;
//            PlayerClient.newInstance(request, startState).start();
//
//        } catch (IOException ex) {
//            Logger.getLogger(RefreshMatchsJspBean.class.getName()).log(Level.SEVERE, null, ex);
//        }


        String host = "localhost";

        try {
            Socket imajieMatchSocket = null;
            PrintWriter out = null;
            BufferedReader in = null;

            try {
                imajieMatchSocket = new Socket(host, Integer.parseInt(session.getAttribute("PlayerServerSocketPort").toString()));
                out = new PrintWriter(imajieMatchSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(imajieMatchSocket.getInputStream()));
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host: " + host + ":::::Port:." + Integer.parseInt(session.getAttribute("PlayerServerSocketPort").toString()));

            } catch (IOException e) {

                if (out != null){
                 out.close();   
                }
                if (in != null){
                 in.close();   
                }
                if (imajieMatchSocket != null){
                 imajieMatchSocket.close();   
                }
                
              
                System.err.println("Couldn't get I/O for the connection to: " + host + ":::::Port:." + Integer.parseInt(session.getAttribute("PlayerServerSocketPort").toString()));

            }


            String fromServer;
            String fromUser;
            ImajieMatchClientProtocol imp = new ImajieMatchClientProtocol();

            fromUser = imp.processInput(null, session, request);
            out.println(fromUser);

            while ((fromServer = in.readLine()) != null) {
                System.out.println(fromServer);

                fromUser = imp.processInput(fromServer, session, request);
                if (fromUser != null) {
                    System.out.println(fromUser);
                    out.println(fromUser);
                }



                if (fromServer.equals("Bye.")) {


                    break;
                }
                if (fromServer.equals("Me?")) {

                    break;
                }


            }

            out.close();
            in.close();

            imajieMatchSocket.close();

        } catch (IOException ex) {
            Logger.getLogger(RefreshMatchsJspBean.class.getName()).log(Level.SEVERE, null, ex);

        }





















        return "";

    }

    public static void layarRefreshSelected(String game, String user, HttpServletRequest request) {

        processFinish = false;
        saveFinish = false;


        HttpSession session = request.getSession(true);


//        try {
//
//            int startState = 0;
//            PlayerClient.newInstance(request, startState).start();
//
//        } catch (IOException ex) {
//            Logger.getLogger(RefreshMatchsJspBean.class.getName()).log(Level.SEVERE, null, ex);
//        }



        String host = "localhost";

        try {
            Socket imajieMatchSocket = null;
            PrintWriter out = null;
            BufferedReader in = null;

            try {
                imajieMatchSocket = new Socket(host, Integer.parseInt(session.getAttribute("PlayerServerSocketPort").toString()));
                out = new PrintWriter(imajieMatchSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(imajieMatchSocket.getInputStream()));
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host: " + host + ":::::Port:." + Integer.parseInt(session.getAttribute("PlayerServerSocketPort").toString()));

            } catch (IOException e) {

                out.close();
                in.close();


                imajieMatchSocket.close();
                System.err.println("Couldn't get I/O for the connection to: " + host + ":::::Port:." + Integer.parseInt(session.getAttribute("PlayerServerSocketPort").toString()));

            }


            String fromServer;
            String fromUser;
            ImajieMatchClientProtocol imp = new ImajieMatchClientProtocol();

            fromUser = imp.processInput(null, session, request);
            out.println(fromUser);

            while ((fromServer = in.readLine()) != null) {
                System.out.println(fromServer);

                fromUser = imp.processInput(fromServer, session, request);
                if (fromUser != null) {
                    System.out.println(fromUser);
                    out.println(fromUser);
                }



                if (fromServer.equals("Bye.")) {


                    break;
                }
                if (fromServer.equals("Me?")) {

                    break;
                }


            }

            out.close();
            in.close();

            imajieMatchSocket.close();

        } catch (IOException ex) {
            Logger.getLogger(RefreshMatchsJspBean.class.getName()).log(Level.SEVERE, null, ex);

        }



        LayarServlet.processFinish = true;

    }

    public static void waiting(int n) {

        long t0, t1;

        t0 = System.currentTimeMillis();

        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < (n * 1000));
    }
}
