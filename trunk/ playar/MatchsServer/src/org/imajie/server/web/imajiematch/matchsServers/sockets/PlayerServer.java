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

/**
 *
 * @author Carl Tremblay <carl_tremblay at imajie.tv>
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.imajie.server.web.Constants;
import org.imajie.server.web.imajiematch.matchsServers.openwig.Engine;

public class PlayerServer extends Thread {

    public static int port = 4444;
    public static String username;
    public static String lat;
    public static String lon;
    public static String alt;
    public static String accuracy;
    private static boolean kill = false;
    public static boolean processFinish = false;
    public static Timer timeout = new Timer();
    private static boolean firstStart = true;

    public static class run extends PlayerServer {

        public run() {
            String[] args = null;

            start();
        }
    }

    public void setPort(int requestPort) {

        port = requestPort;

    }

    public void setUsername(String requestUsername) {

        username = requestUsername;

    }
    private static Runnable mainSocket = new Runnable() {

        @Override
        public void run() {
            int numberOfMillisecondsInTheFuture = Constants.PLAYER_SERVER_TIMEOUT; // 6 sec
            Date timeToRun = new Date(System.currentTimeMillis() + numberOfMillisecondsInTheFuture);


            timeout.schedule(new TimerTask() {

                @Override
                public void run() {

                    Engine.requestSync();

                    waiting(1000);

                    int state = 11;
                    try {
                        MainServerPlayerServerClient.newInstance(state, Engine.instance.cartridge.name).start();
                    } catch (IOException ex) {
                        Logger.getLogger(PlayerServer.class.getName()).log(Level.SEVERE, null, ex);
                        System.exit(1);
                    }



                    System.out.println("Timeout " + Engine.Username + " game killed at: " + new Date());
                }
            }, timeToRun);
        }
    };

    @Override
    public void run() {
        try {
            mainSocket.run();
//
//            int numberOfMillisecondsInTheFuture = Constants.PLAYER_SERVER_TIMEOUT; // 6 sec
//            Date timeToRun = new Date(System.currentTimeMillis() + numberOfMillisecondsInTheFuture);
//
//
//
//            
//            
//            timeout.schedule(new TimerTask() {
//
//                @Override
//                public void run() {
//                    Engine.requestSync();
//
//                    waiting(1000);
//
//                    int state = 11;
//                    try {
//                        MainServerPlayerServerClient.newInstance(state, Engine.instance.cartridge.name).start();
//                    } catch (IOException ex) {
//                        Logger.getLogger(PlayerServer.class.getName()).log(Level.SEVERE, null, ex);
//                        System.exit(1);
//                    }
//
//
//
//                    System.out.println("Timeout " + Engine.Username + " game killed at: " + new Date());
//                }
//            }, timeToRun);

            ServerSocket serverSocket = null;
            Socket clientSocket = null;
            PrintWriter out = null;
            BufferedReader in = null;
            //serverSocket.setReuseAddress(true);
            try {
                serverSocket = new ServerSocket(port);

            } catch (IOException e) {
                System.err.println("Could not listen on port: " + port + ".");
                //System.exit(1);
            }

            if (firstStart) {
                System.out.println(username + " Player Server is Listening");
                firstStart = false;
            }
            try {
                clientSocket = serverSocket.accept();

            } catch (IOException e) {
                System.err.println("Accept failed.");
                //System.exit(1);
            }

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
            String inputLine, outputLine;
            ImajieMatchProtocol imp = new ImajieMatchProtocol();

            outputLine = imp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = imp.processInput(inputLine);
                out.println(outputLine);

                if (!outputLine.isEmpty()) {
                    if (outputLine.equals("Me?")) {

                        kill = true;
                        break;
                    }
                }

                if (outputLine.equals("Bye.")) {

                    break;
                }
            }
            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();


        } catch (IOException ex) {
            Logger.getLogger(PlayerServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!kill) {

            this.run();
        }
    }

    public static void main(String[] args) {
        Thread thread = new PlayerServer();
        thread.start();

    }

    public static void waiting(int n) {

        long t0, t1;

        t0 = System.currentTimeMillis();

        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < (n));
    }

    public static void resetTimer() {
        // mainSocket.
    }
}
