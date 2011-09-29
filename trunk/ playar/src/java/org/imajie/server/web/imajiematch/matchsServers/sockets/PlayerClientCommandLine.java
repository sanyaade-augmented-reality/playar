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

public class PlayerClientCommandLine {
  public static void main(String args[]) throws IOException {

    Socket imajieMatchSocket = null;
    PrintWriter out = null;
    BufferedReader in = null;

    try {
      imajieMatchSocket = new Socket("localhost", 4001);
      out = new PrintWriter(imajieMatchSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(imajieMatchSocket
          .getInputStream()));
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host: "+"localhost"+".");
      System.exit(1);
    } catch (IOException e) {
      System.err
          .println("Couldn't get I/O for the connection to: "+"localhost"+".");
      System.exit(1);
    }

    BufferedReader stdIn = new BufferedReader(new InputStreamReader(
        System.in));
    String fromServer;
    String fromUser;

    while ((fromServer = in.readLine()) != null) {
      System.out.println(fromServer);
      if (fromServer.equals("Bye."))
      {
          break;
      }
      if (fromServer.equals("Me?"))
      {
          break;
      }
      fromUser = stdIn.readLine();
      if (fromUser != null) {
        System.out.println(fromUser);
        out.println(fromUser);
      }
    }

    out.close();
    in.close();
    stdIn.close();
    imajieMatchSocket.close();
  }
}
