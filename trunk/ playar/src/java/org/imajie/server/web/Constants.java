/* Copyright (c) 2010 Imajie project owners (see http://www.imajie.org)
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

// TODO Transfert all of this in a property file

package org.imajie.server.web;

/**
 *
 * @author Carl Tremblay
 */
public final class Constants
{
    public static final String PARAMATER_ID = "id";
    public static final String PARAMATER_NAME = "name";
    public static final String PARAMATER_LAT = "lat";
    public static final String PARAMATER_LON = "lon";
    public static final String PARAMATER_LANDSCAPE = "landscape";

    public static final String CONTENT_TYPE_TEXT = "text/plain";
    public static final String CONTENT_TYPE_XML = "application/xml";
    public static final String CONTENT_TYPE_JPEG = "image/jpeg";
    public static final String CONTENT_TYPE_JSON = "application/javascript";
    public static final String CONTENT_TYPE_KML = "application/vnd.google-earth.kml+xml";
    
    
    //     This will use a port range localy of the max player number 
    //     starting from the main server port. 
    //     Example: Server port 4000 - max player 100 - will use the port range 4000 - 4100 
    public static final int MAIN_SERVER_PORT = 4000;
    public static final int MAX_PLAYER_NUMBER = 100;
    
    // Fix the duration witch player server remain on without player trigger
    public static final int PLAYER_SERVER_TIMEOUT = 30000;
  
    
    
     public static final String URL_SERVER = "http://localhost:8080/IMAJIEMATCH";
    
     
     public static final String URL_SAFE_SERVER = "http:\\/\\/imajiematch.imajie.tv:8080\\/IMAJIEMATCH";

    
   
    public static final String CARTRIDGE_BASE_DIR = "/opt/tomcat7/playar/cartridges/";

    
    public static final String BASE_DIR = "/opt/tomcat7/webapps/IMAJIEMATCH/";
   

   public static final String IMAJIE_SERVER = "http://community.server.host/index.php/component/imajiematchconnect?task=imajiematch.connect&format=xmlrpc";  
   

}

