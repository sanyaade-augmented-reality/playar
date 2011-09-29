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


public class PlayerServerCommandLine {
    //public static KnockKnockServer instance;
    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private static PrintWriter out = null;
    private static BufferedReader in = null;
    
  public static void main(String args[]) throws IOException {

    
    try {
      serverSocket = new ServerSocket(4444);
    } catch (IOException e) {
      System.err.println("Could not listen on port: 4444.");
      //System.exit(1);
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
      if (outputLine.equals("Bye."))
        break;
    }
    out.close();
    in.close();
    clientSocket.close();
    serverSocket.close();
  }
  
  public static void kill() throws IOException {
  out.close();
    in.close();
    clientSocket.close();
    serverSocket.close();
   // this.
  
  
  
  } 
}
