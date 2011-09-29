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
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainServerPlayerServerClient extends Thread{
    
    public static MainServerPlayerServerClient instance;
     public int port = 4000;
     public String host = "localhost";
  
     private Thread thread = null;
     public static int initialState = 0;
     public static String matchToPlay = "";
     //public  HttpServletRequest sessionRequest;
     public  boolean processFinish = false;
     
  //   public static class run extends PlayerClient {

//        public run() {
//            start();
//        }
   // }
     
    
    @Override
     public void start() {
        System.out.println("Start Socket MainServerClient...\n");
        thread = new Thread(this);
        thread.start();
        //run();
    }
     
     public static MainServerPlayerServerClient newInstance( int state, String match) throws IOException {
          
         
         initialState = state;
        matchToPlay = match;
        
        instance = new MainServerPlayerServerClient(initialState);

        return instance;
    }
     
     protected MainServerPlayerServerClient( int state) throws IOException {
        
         processFinish = false;
         //sessionRequest = request;
        
        System.out.println("Protected MainServerClient...\n");
        instance = this;
        
        
     }  
     
    
    @Override
  public void run() {
        try {
            Socket imajieMatchSocket = null;
            PrintWriter out = null;
            BufferedReader in = null;

            try {
              imajieMatchSocket = new Socket(host, port);
              out = new PrintWriter(imajieMatchSocket.getOutputStream(), true);
              in = new BufferedReader(new InputStreamReader(imajieMatchSocket
                  .getInputStream()));
            } catch (UnknownHostException e) {
              System.err.println("Don't know about host: "+host+".");
              //System.exit(1);
            } catch (IOException e) {
              System.err
                  .println("Couldn't get I/O for the connection to: "+host+".");
              System.exit(1);
            }

            //BufferedReader stdIn = new BufferedReader(new InputStreamReader(
               // System.in));
            String fromServer;
            String fromUser;
            MainServerPlayerServerClientProtocol imp = new MainServerPlayerServerClientProtocol(initialState);

            fromUser = imp.processInput(null);
            out.println(fromUser);
            
            
            
            

            while ((fromServer = in.readLine()) != null) {
              System.out.println(fromServer);
              
              fromUser = imp.processInput(fromServer);
              if (fromUser != null) {
                System.out.println(fromUser);
                out.println(fromUser);
              }
              
              
              
              if (fromServer.equals("Bye."))
              {
                  processFinish = true;
                  
                  break;
              }
              if (fromServer.equals("Me?"))
              {
                  processFinish = true;
                
                  break;
              }

              
            }

            out.close();
            in.close();
            //stdIn.close();
            imajieMatchSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(MainServerPlayerServerClient.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
  }
    
    
    
}
