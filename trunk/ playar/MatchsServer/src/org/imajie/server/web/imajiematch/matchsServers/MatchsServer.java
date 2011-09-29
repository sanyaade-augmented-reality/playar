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

import org.imajie.server.web.imajiematch.matchsServers.sockets.MainServer;

/**
 *
 * @author Carl Tremblay <carl_tremblay at imajie.tv>
 */
public class MatchsServer {

    /**
     * @param args the command line arguments
     */
    
    
   // private static MainServer listeningServer;
    
    
    public static void main(String[] args) {
        
        
        // Start the socket on port 4000
        
        
        MainServer.main(args);
        //listeningServer.start();
        
        
    }
}
