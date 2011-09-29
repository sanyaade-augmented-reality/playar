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
package org.imajie.server.web.imajiematch.matchsServers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.imajie.server.web.imajiematch.matchsServers.main.GameWindow;
import org.imajie.server.web.imajiematch.matchsServers.openwig.Engine;
import org.imajie.server.web.imajiematch.matchsServers.sockets.PlayerServer;
import org.imajie.server.web.log.LoggingOutputStream;

/**
 *
 * @author Carl Tremblay <carl_tremblay at imajie.tv>
 */
public class PlayerMatchServer {

    /**
     * @param args the command line arguments
     */
    public static int port = 777;
    public static String username = "Alfred";
    public static String lat = "45.2562947";
    public static String lon = "-72.92003";
    public static String alt = "0";
    public static String match = "Zooventure";
    public static String accuracy = "10";
    private static PlayerServer listeningServer;
    public static GameWindow gui = new GameWindow();
   
    private static LoggingOutputStream los;

    public static void main(String[] args) {
     
        if (args != null) {
            for (String s : args) {



                port = Integer.parseInt(args[0]);

                username = args[1];
                lat = args[2];
                lon = args[3];
                alt = args[4];
                accuracy = args[5];
                match = args[6];




                System.out.println(s);
            }
        }
        try {

          

            if (match.contains("::RESTORE")) {

                match = match.replace("::RESTORE", "");
                System.out.println("Match Restored :"+match);
                Engine.newInstance(DiskIO.start(match, username),los, gui, username, lat, lon, alt, accuracy, port).restore();

            } else {
                Engine.newInstance(DiskIO.start(match, username),los, gui, username, lat, lon, alt, accuracy, port).start();
            }



            

            //        listeningServer = new PlayerServer();
            //        listeningServer.setPort(port);
            //        listeningServer.start();
        } catch (IOException ex) {
            Logger.getLogger(PlayerMatchServer.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    
   
}
