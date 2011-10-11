/* Copyright (c) 2011 Imajie project owners (see http://www.imajie.org)
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
package org.imajie.server.web.imajiematch.matchsServers.layar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.Socket;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.imajie.server.web.Constants;
import org.imajie.server.web.Security;
import org.imajie.server.web.Utils;
import org.imajie.server.web.imajiematch.matchsServers.RefreshMatchsJspBean;
import org.imajie.server.web.imajiematch.matchsServers.sockets.MainServerClientProtocol;

/**
 *
 * @author Carl Tremblay
 */
public class LayarServlet extends HttpServlet {

    public static boolean processFinish = false;
    public static String playMediaCall = "null";
    public static String dialog = "null";
    public static boolean DoplayMediaCall = false;
    public static boolean Dodialog = false;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        LayarParamsService params = LayarParamsService.instance();

        String layerName = request.getParameter("layerName");
        String developerId = request.getParameter("developerId");
        double latitude = Utils.getDouble(request, "lat", 45.49);
        double longitude = Utils.getDouble(request, "lon", -73.56);
        double altitude = Utils.getDouble(request, "alt", 0);

        HttpSession session = null;

        session = request.getSession(true);

        session.setAttribute("lat", latitude);
        session.setAttribute("lon", longitude);
        session.setAttribute("alt", altitude);
        session.setAttribute("accuracy", Utils.getDouble(request, "accuracy", 1));

        String username = "";
        String radiusSession = "10000";
        // TODO implements practice and team-playing in layar
        String practice = "true";
        String gameType = "stand-alone";
        String gameStarted = "none";


        if (session.getAttribute("username") != null) {

            username = session.getAttribute("username").toString();

        }

        if (session.getAttribute("radius") != null) {

            radiusSession = session.getAttribute("radius").toString();

        }

        if (session.getAttribute("practice") != null) {

            practice = session.getAttribute("practice").toString();

        }
        if (session.getAttribute("gameType") != null) {

            gameType = session.getAttribute("gameType").toString();

        }

        if (session.getAttribute("gameStarted") != null) {

            gameStarted = session.getAttribute("gameStarted").toString();

        }



        String radiusParam = request.getParameter("radius");
        long radius = (radiusSession != null) ? Long.parseLong(radiusSession) : 10000L;



        String userId = request.getParameter("userId");

        Writer out = response.getWriter();
        if (developerId == null || !developerId.equals(Security.LAYAR_DEVELOPER_ID)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(Constants.CONTENT_TYPE_TEXT);
            out.write("Unauthorized 401");
            out.close();
            return;
        }

        response.setContentType(Constants.CONTENT_TYPE_JSON);

        JSONObject layer = new JSONObject();

        // TODO "deletedHotspots":["spot0001", "spot0002"]
        JSONArray hotspots = new JSONArray();

        int count = 0;



        if (gameStarted.equals("none") || gameStarted.equals("SERVER_FULL") || gameStarted.equals("ALREADY_PLAYING")) {


            //   **************************************************************************
            //****************************************************************************
            //
            //       Set  Cartridge available POIS
            //
            //****************************************************************************

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
                MainServerClientProtocol imp = new MainServerClientProtocol(session, 7);

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

                Logger.getLogger(LayarServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            // End the solution to the problem

            String result = "";
            if (session.getAttribute("LayarMatchsList") != null) {
                result = session.getAttribute("LayarMatchsList").toString();
            }
            if (!result.equals("")) {
                String[] layarMatchsList;

                // LAYAR_REFRESH||||||poiCount||||title||description||lat||lon||||title||description||lat||lon||Vision image||||


                String delimiter = "\\|\\|\\|\\|";
                layarMatchsList = result.split(delimiter);


                int layarPoiCount = Integer.parseInt(layarMatchsList[0]);
                String[] layarPoi = new String[layarPoiCount];

                for (int ii = 0; ii < layarPoiCount; ii++) {

                    if (layarMatchsList[ii + 1] != null) {
                        layarPoi[ii] = layarMatchsList[ii + 1];
                    }

                }

                for (int i = 0; i < layarPoiCount; i++) {
                    if (layarPoi[i] != null) {
                        count++;

                        String[] cartridgeDetails = new String[5];
                        String delimiter2 = "\\|\\|";
                        cartridgeDetails = layarPoi[i].split(delimiter2);

                        JSONObject poi = new JSONObject();

                        poi.accumulate("id", StringEscapeUtils.escapeJavaScript(cartridgeDetails[0]));


                        //Placement of the POI. Can either be a geolocation or the key of a reference image 
                        //in Layar Vision. For geolocation, alt is optional but lat and lon are mandatory. 

                        //NOTE: lat and lon are now decimal degrees instead of integer millionths of degrees.
                        //  Layar also supports the geo: URI scheme for specifying geolocations.

                        //"anchor": { "referenceImage": "myFirstImage" }

                        //"anchor": { "geolocation": { "lat": 52.3, "lon": 4.5 } }

                        //"anchor": "geo:52.3,4.5"



                        if (cartridgeDetails[4].contains("***VISION")) {

                            JSONObject anchor = new JSONObject();


                            String layarVision = cartridgeDetails[4].replace("***VISION", "");
                            layarVision = layarVision.replace("***", "");
                            String referenceimage = "\"referenceImage:\"";
                            String visionImage = layarVision.replace(".png", "");

                            anchor.accumulate("referenceImage", visionImage);

                            poi.accumulate("anchor", anchor);

                        } else {

                            poi.accumulate("anchor", "geo:" + cartridgeDetails[2] + "," + cartridgeDetails[3] + "");

                        }

                        JSONObject text = new JSONObject();
                        text.accumulate("title", StringEscapeUtils.escapeJavaScript(cartridgeDetails[0]));

                        if (cartridgeDetails[4].contains("***VISION")) {
                        } else {
                            text.accumulate("description", cartridgeDetails[1]);
                            text.accumulate("footnote", "Powered by ImajieMatch");


                        }

                        poi.accumulate("text", text);

                        if (cartridgeDetails[4].contains("***VISION")) {
                        } else {

                            poi.accumulate("imageUrl", Constants.URL_SERVER + "/icon?matchtitle=" + cartridgeDetails[0] + "&icon=splashid.png");

                        }


                        poi.accumulate("doNotIndex", false);

                        poi.accumulate("inFocus", false);

                        if (cartridgeDetails[4].contains("***VISION")) {
                            poi.accumulate("showSmallBiw", false);
                        } else {

                            poi.accumulate("showSmallBiw", true);
                        }



                        poi.accumulate("showBiwOnClick", false);



                        if (cartridgeDetails[4].contains("***VISION")) {
                        } else {

                            JSONObject icon = new JSONObject();
                            icon.accumulate("url", Constants.URL_SERVER + "/icon?matchtitle=" + cartridgeDetails[0] + "&icon=splashid.png");
                            icon.accumulate("type", "0");
                            poi.accumulate("icon", icon);

                        }
                        // Forces the POI's BIW style to "classic" or "collapsed". 
                        //Default is "classic" if the POI is a geolocated POI and "collapsed" 
                        //if the POI is a Vision enabled POI.
                        poi.accumulate("biwStyle", "collapsed");



                        // Actions
                        JSONArray actions = new JSONArray();
                        JSONObject action1 = new JSONObject();
                        if (cartridgeDetails[4].contains("***VISION")) {
                            action1.accumulate("uri", Constants.URL_SERVER + "/imajiematch/start.jsp?match=" + cartridgeDetails[0]);
                        } else {
                            action1.accumulate("uri", Constants.URL_SERVER + "/imajiematch/startmatch.jsp?match=" + cartridgeDetails[0]);
                        }
                        action1.accumulate("label", "View Mission");

                        // contenType
                        //"text/html", "text/plain", "audio/mpeg", "audio/mp4",
                        //"video/3gpp", "video/mp4", "application/vnd.layar.internal", "application/vnd.layar.async".
                        action1.accumulate("contentType", "text/html");
                        action1.accumulate("method", "GET");
                        action1.accumulate("activityType", 3);
                        action1.accumulate("params", null);
                        action1.accumulate("closeBiw", true);
                        action1.accumulate("showActivity", false);
                        action1.accumulate("activityMessage", "");



                        if (cartridgeDetails[4].contains("***VISION")) {

                            action1.accumulate("autoTriggerOnly ", true);
                            action1.accumulate("autoTrigger ", true);

                        } else {

                            action1.accumulate("autoTriggerOnly ", false);
                            action1.accumulate("autoTriggerRange", "10");

                        }

                        // Autotrigger indicator for Vision enabled POIs. 

                        actions.add(action1);

                        poi.accumulate("actions", actions);

                        // Transform values
                        JSONObject transform = new JSONObject();
                        //transform.accumulate("rotate", "");
                        //transform.accumulate("translate", "");
                        transform.accumulate("scale", params.getScale());
                        poi.accumulate("transform", transform);




                        // Transform values
                        JSONObject object = new JSONObject();
                        // Content type of the object. Can be one of the following:

                        //   image/vnd.layar.generic for any supported image type (PNG, GIF, JPEG)
                        //   model/vnd.layar.l3d for 3D models
                        //   image/jpeg, image/gif, image/png for images




                        if (cartridgeDetails[4].contains("***VISION")) {
                            object.accumulate("contentType", "image/png");
                            object.accumulate("url", Constants.URL_SERVER + "/icon?matchtitle=" + cartridgeDetails[0] + "&icon=getMission.png");
                            object.accumulate("size", "0.25");

                        } else {

                            object.accumulate("contentType", "image/png");
                            object.accumulate("url", Constants.URL_SERVER + "/icon?matchtitle=" + cartridgeDetails[0] + "&icon=splashid.png");
                            // object.accumulate("reducedURL", Constants.URL_SERVER + "/icon?matchtitle=" + cartridgeDetails[0] + "&icon=splashidsmall.jpg");
                            object.accumulate("size", "1.0");
                        }

                        //object.accumulate("size", params.getSize());
                        poi.accumulate("object", object);

                        hotspots.add(poi);

                    }

                }
            }

        } else {

            playMediaCall = "null";
            dialog = "null";
            // TODO DELETE ALL POI



            RefreshMatchsJspBean.layarRefresh(gameStarted, username, request);

            if (session.getAttribute("playMediaCallPlayed") == null) {

                session.setAttribute("playMediaCallPlayed", "");
            }

            if (session.getAttribute("PLAYMEDIA_CALL") != null) {


                if (!session.getAttribute("PLAYMEDIA_CALL").toString().equals(session.getAttribute("playMediaCallPlayed").toString())) {


                    playMediaCall = session.getAttribute("PLAYMEDIA_CALL").toString();

                    session.setAttribute("playMediaCallPlayed", playMediaCall);
                    DoplayMediaCall = true;

                } else {

                    DoplayMediaCall = false;

                }
            }

            if (session.getAttribute("DIALOG") != null) {



                if (session.getAttribute("dialogPlayed") == null) {

                    session.setAttribute("dialogPlayed", "");
                }

                if (!session.getAttribute("DIALOG").toString().equals(session.getAttribute("dialogPlayed").toString()) && !DoplayMediaCall && session.getAttribute("showDialog").toString().contains("null")) {

                    dialog = session.getAttribute("DIALOG").toString();
                    session.setAttribute("dialogPlayed", dialog);
                    Dodialog = true;

                } else {
                    Dodialog = false;
                }

            }

            String currentEvent = "";
            if (session.getAttribute("CURRENTEVENT") != null) {

                currentEvent = session.getAttribute("CURRENTEVENT").toString();


            }



            if (DoplayMediaCall) {

                //   **************************************************************************
                //****************************************************************************
                //
                //       Set  Media POIS
                //
                //****************************************************************************
                count++;


                JSONObject poi = new JSONObject();

                poi.accumulate("id", playMediaCall);


                poi.accumulate("anchor", "geo:" + latitude + "," + longitude + "");

                JSONObject text = new JSONObject();
                text.accumulate("title", StringEscapeUtils.escapeJavaScript("Message"));


                text.accumulate("footnote", "Powered by ImajieMatch");
                poi.accumulate("text", text);

                JSONArray actions = new JSONArray();

                if (playMediaCall.contains("||***VIDEO***")) {

                    String media = playMediaCall.replace("||***VIDEO***", "");
                    media = media.replace(".png", ".mp4");
                    JSONObject layarActionMedias = new JSONObject();
                    layarActionMedias.accumulate("contentType", "video/mp4");
                    layarActionMedias.accumulate("method", "GET");
                    layarActionMedias.accumulate("uri", "video://" + Constants.URL_SERVER.replace("http://", "") + "/icon?matchtitle=" + gameStarted + "&icon=" + media + "");
                    layarActionMedias.accumulate("label", "Message");
                    layarActionMedias.accumulate("activityType", 3);
                    layarActionMedias.accumulate("autoTrigger", true);
                    layarActionMedias.accumulate("autoTriggerRange", 20);
                    layarActionMedias.accumulate("autoTriggerOnly", true);
                    actions.add(layarActionMedias);
                    poi.accumulate("actions", actions);
                    hotspots.add(poi);

                }

                if (playMediaCall.contains("||***AUDIO***")) {

                    JSONObject layarActionMedias = new JSONObject();
                    String media = playMediaCall.replace("||***AUDIO***", "");
                    media = media.replace(".png", ".mp4");
                    layarActionMedias.accumulate("contentTyp", "audio/mp3");
                    layarActionMedias.accumulate("method", "GET");
                    layarActionMedias.accumulate("uri", "audio://" + Constants.URL_SERVER.replace("http://", "") + "/icon?matchtitle=" + gameStarted + "&icon=" + media + "");
                    layarActionMedias.accumulate("label", "Message");
                    layarActionMedias.accumulate("activityType", 2);
                    layarActionMedias.accumulate("autoTrigger", true);
                    layarActionMedias.accumulate("autoTriggerRange", 20);
                    layarActionMedias.accumulate("autoTriggerOnly", true);
                    actions.add(layarActionMedias);
                    poi.accumulate("actions", actions);
                    hotspots.add(poi);

                }


                // TODO FINISH THE IMPLEMENTS OF TELEPHONE CALLS BETWEEN PLAYERS OF SAME TEAM --- LAYAR 
                if (playMediaCall.contains("||***TEL***")) {
//                JSONObject layarActionMedias = new JSONObject();
//                layarActionMedias.accumulate("contentType", "application/vnd.layar.internal");
//                layarActionMedias.accumulate("method", "GET");
//                layarActionMedias.accumulate("uri", "tel://"");
//                layarActionMedias.accumulate("label", "Message");
//                layarActionMedias.accumulate("activityType", 4);
//                layarActionMedias.accumulate("autoTrigger", true);
//                layarActionMedias.accumulate("autoTriggerRange", 20);
//                layarActionMedias.accumulate("autoTriggerOnly", true);
//                layarActions.add(layarActionMedias);
                }

                // TODO FINISH THE IMPLEMENTS OF SMS CALLS BETWEEN PLAYERS OF SAME TEAM --- LAYAR 
                if (playMediaCall.contains("||***SMS***")) {
//                JSONObject layarActionMedias = new JSONObject();
//                layarActionMedias.accumulate("contentType", "application/vnd.layar.internal");
//                layarActionMedias.accumulate("method", "GET");
//                layarActionMedias.accumulate("uri", "sms://"");
//                layarActionMedias.accumulate("label", "Message");
//                layarActionMedias.accumulate("activityType", 5);
//                layarActionMedias.accumulate("autoTrigger", true);
//                layarActionMedias.accumulate("autoTriggerRange", 20);
//                layarActionMedias.accumulate("autoTriggerOnly", true);
//                layarActions.add(layarActionMedias);
                }



            }
            if (Dodialog && !DoplayMediaCall) {


                //   **************************************************************************
                //****************************************************************************
                //
                //       Set  Dialog POIS
                //
                //****************************************************************************

                count++;

                JSONObject poi = new JSONObject();

                poi.accumulate("id", session.getAttribute("dialogTexts"));



                poi.accumulate("anchor", "geo:" + latitude + "," + longitude + "");

                JSONObject text = new JSONObject();
                text.accumulate("title", StringEscapeUtils.escapeJavaScript("Dialog"));


                text.accumulate("footnote", "Powered by ImajieMatch");
                poi.accumulate("text", text);

                JSONArray actions = new JSONArray();


                JSONObject layarActionMedias = new JSONObject();
                layarActionMedias.accumulate("contentType", "text/html");
                layarActionMedias.accumulate("method", "GET");
                layarActionMedias.accumulate("uri", Constants.URL_SERVER + "/imajiematch/start2.jsp");
                layarActionMedias.accumulate("label", "Message");
                layarActionMedias.accumulate("activityType", 36);
                layarActionMedias.accumulate("autoTrigger", true);
                layarActionMedias.accumulate("autoTriggerRange", 20);
                layarActionMedias.accumulate("autoTriggerOnly", true);
                actions.add(layarActionMedias);
                poi.accumulate("actions", actions);


                hotspots.add(poi);


            }



            if (!Dodialog
                    && !DoplayMediaCall) {

                //   **************************************************************************
                //****************************************************************************
                //
                //       Set  Zone POIS
                //
                //****************************************************************************


                String zoneList = session.getAttribute("zoneList").toString();

                String list = (String) session.getAttribute("tasksList");



                String delimiter = "\\|!\\|";
                String[] temp = zoneList.split(delimiter);
                for (int i = 0; i < temp.length; i++) {
                    if (i == 0) {
                    } else {

                        if (temp[i] != null) {


                            String title = "";
                            String description = "";
                            String hotspotAltitude = "";
                            String hotspotLatitude = "";
                            String hotspotLongitude = "";
                            String hotspotDistance = "";
                            String frienddlyDistance = "";

                            String hotspot = temp[i];
                            String delimiter2 = "\\|\\|";
                            String[] temp2 = hotspot.split(delimiter2);
                            String media = "";







                            for (int ii = 0; ii < temp2.length; ii++) {
                                if (ii == 0) {
                                    title = temp2[ii];
                                }
                                if (ii == 1) {
                                    description = temp2[ii];
                                }
                                if (ii == 2) {
                                    hotspotAltitude = temp2[ii];
                                }
                                if (ii == 3) {
                                    hotspotLatitude = temp2[ii];
                                }
                                if (ii == 4) {
                                    hotspotLongitude = temp2[ii];
                                }
                                if (ii == 5) {
                                    hotspotDistance = temp2[ii];
                                }
                                if (ii == 6) {
                                    frienddlyDistance = temp2[ii];
                                }
                                if (ii == 7) {
                                    media = temp2[ii];
                                }
                            }


                            if (!currentEvent.contentEquals(title + ".OnEnter")) {


                                JSONObject poi = new JSONObject();
                                count++;

                                //String[] matchPois = new String[4];


                                //Placement of the POI. Can either be a geolocation or the key of a reference image 
                                //in Layar Vision. For geolocation, alt is optional but lat and lon are mandatory. 

                                //NOTE: lat and lon are now decimal degrees instead of integer millionths of degrees.
                                //  Layar also supports the geo: URI scheme for specifying geolocations.

                                //"anchor": { "referenceImage": "myFirstImage" }

                                //"anchor": { "geolocation": { "lat": 52.3, "lon": 4.5 } }

                                //"anchor": "geo:52.3,4.5"


                                poi.accumulate("id", StringEscapeUtils.escapeJavaScript(title));
                                poi.accumulate("anchor", "geo:" + hotspotLatitude + "," + hotspotLongitude + "");

                                JSONObject text = new JSONObject();
                                text.accumulate("title", StringEscapeUtils.escapeJavaScript(title));
                                text.accumulate("description", description);
                                text.accumulate("footnote", "Powered by ImajieMatch");
                                poi.accumulate("text", text);



//                            if (session.getAttribute("dialogMedia") != null) {
//
//                                media = session.getAttribute("dialogMedia").toString().replace("DIALOGMEDIA", "");
//
//                            }

                                poi.accumulate("imageUrl", Constants.URL_SERVER + "/icon?matchtitle=" + gameStarted + "&icon=" + media + "");

                                if (gameStarted.equals("none") || gameStarted.equals("SERVER_FULL") || gameStarted.equals("ALREADY_PLAYING")) {

                                    poi.accumulate("doNotIndex", false);
                                } else {

                                    poi.accumulate("doNotIndex", true);
                                }
                                poi.accumulate("inFocus", false);

                                poi.accumulate("showSmallBiw", true);

                                poi.accumulate("showBiwOnClick", false);



                                JSONObject icon = new JSONObject();
                                icon.accumulate("url", Constants.URL_SERVER + "/icon?matchtitle=" + gameStarted + "&icon=" + media + "");
                                icon.accumulate("type", "0");
                                poi.accumulate("icon", icon);


                                // Forces the POI's BIW style to "classic" or "collapsed". 
                                //Default is "classic" if the POI is a geolocated POI and "collapsed" 
                                //if the POI is a Vision enabled POI.
                                poi.accumulate("biwStyle", "classic");



                                // Actions
                                JSONArray actions = new JSONArray();
                                JSONObject action1 = new JSONObject();
                                action1.accumulate("uri", Constants.URL_SERVER + "/imajiematch/locations.jsp?zonePoint=" + title);
                                action1.accumulate("label", title + " Details");

                                // contenType
                                //"text/html", "text/plain", "audio/mpeg", "audio/mp4",
                                //"video/3gpp", "video/mp4", "application/vnd.layar.internal", "application/vnd.layar.async".
                                action1.accumulate("contentType", "text/html");
                                action1.accumulate("method", "GET");
                                action1.accumulate("activityType", 6);

                                action1.accumulate("params", null);
                                action1.accumulate("closeBiw", true);
                                action1.accumulate("showActivity", false);
                                action1.accumulate("activityMessage", "");
                                //action1.accumulate("autoTriggerRange", "10");
                                action1.accumulate("autoTriggerOnly ", false);
                                action1.accumulate("autoTrigger ", false); // Autotrigger indicator for Vision enabled POIs. 

                                actions.add(action1);


                                poi.accumulate("actions", actions);

                                // Transform values
                                JSONObject transform = new JSONObject();
                                //transform.accumulate("rotate", "");
                                //transform.accumulate("translate", "");
                                transform.accumulate("scale", params.getScale());
                                poi.accumulate("transform", transform);

                                // Transform values
                                JSONObject object = new JSONObject();
                                // Content type of the object. Can be one of the following:

                                //   image/vnd.layar.generic for any supported image type (PNG, GIF, JPEG)
                                //   model/vnd.layar.l3d for 3D models
                                //   image/jpeg, image/gif, image/png for images

                                if (media.contains(".jpg")) {
                                    object.accumulate("contentType", "image/jpeg");

                                } else {
                                    object.accumulate("contentType", "image/png");

                                }
                                object.accumulate("url", Constants.URL_SERVER + "/icon?matchtitle=" + gameStarted + "&icon=" + media + "");
                                //object.accumulate("reducedURL", Constants.URL_SERVER + "/icon?matchtitle=" + gameStarted + "&icon=" + media + "");
                                object.accumulate("size", params.getSize());
                                poi.accumulate("object", object);

                                hotspots.add(poi);
                            }

                        }
                    }

                }
                //********************************************
                //
                //          Add You see pois
                //
                //********************************************
                String youseeList = "";

                if (session.getAttribute("youseeList") != null) {

                    youseeList = session.getAttribute("youseeList").toString();


                }
                String delimiter3 = "\\|!\\|";
                String[] temp3 = youseeList.split(delimiter3);

                if (temp3.length > 0) {


                    //String list = (String) session.getAttribute("tasksList");




                    double angle = 0 ;
                    
                    for (int i = 0; i < temp3.length; i++) {
                        if (i == 0) {
                        } else {

                            if (temp3[i] != null) {


                                
                                
                                
                                String title = "";
                                String description = "";
                                String hotspotAltitude = "";
                                String hotspotLatitude = "";
                                String hotspotLongitude = "";
                                String hotspotDistance = "";
                                String frienddlyDistance = "";

                                String hotspot = temp3[i];
                                String delimiter2 = "\\|\\|";
                                String[] temp2 = hotspot.split(delimiter2);
                                String media = "";


                                for (int ii = 0; ii < temp2.length; ii++) {
                                    if (ii == 0) {
                                        title = temp2[ii];
                                    }
                                    if (ii == 1) {
                                        description = temp2[ii];
                                    }
                                    if (ii == 2) {
                                        hotspotAltitude = temp2[ii];
                                    }
                                    if (ii == 3) {
                                        hotspotLatitude = temp2[ii];
                                    }
                                    if (ii == 4) {
                                        hotspotLongitude = temp2[ii];
                                    }
                                    if (ii == 5) {
                                        hotspotDistance = temp2[ii];
                                    }
                                    if (ii == 6) {
                                        media = temp2[ii];
                                    }
                                }


//                                x = cx + r * cos(a)
//                                y = cy + r * sin(a)
// float x = (float)(radius * Math.Cos(angleInDegrees * Math.PI / 180F)) + origin.X;
//        float y = (float)(radius * Math.Sin(angleInDegrees * Math.PI / 180F)) + origin.Y;



                                if ("".equals(hotspotLatitude) && "".equals(hotspotLongitude))  {
                                    
                                    hotspotLatitude = ""+(latitude + 3 * Math.cos(angle));
                                    hotspotLongitude = ""+(latitude + 3 * Math.sin(angle));
                                    hotspotAltitude = "0.0";
                                }

                                JSONObject poi = new JSONObject();
                                count++;

                                //String[] matchPois = new String[4];


                                //Placement of the POI. Can either be a geolocation or the key of a reference image 
                                //in Layar Vision. For geolocation, alt is optional but lat and lon are mandatory. 

                                //NOTE: lat and lon are now decimal degrees instead of integer millionths of degrees.
                                //  Layar also supports the geo: URI scheme for specifying geolocations.

                                //"anchor": { "referenceImage": "myFirstImage" }

                                //"anchor": { "geolocation": { "lat": 52.3, "lon": 4.5 } }

                                //"anchor": "geo:52.3,4.5"


                                poi.accumulate("id", StringEscapeUtils.escapeJavaScript(title));
                                poi.accumulate("anchor", "geo:" + hotspotLatitude + "," + hotspotLongitude + "");

                                JSONObject text = new JSONObject();
                                text.accumulate("title", StringEscapeUtils.escapeJavaScript(title));
                                text.accumulate("description", description);
                                text.accumulate("footnote", "Powered by ImajieMatch");
                                poi.accumulate("text", text);



//                            if (session.getAttribute("dialogMedia") != null) {
//
//                                media = session.getAttribute("dialogMedia").toString().replace("DIALOGMEDIA", "");
//
//                            }

                                poi.accumulate("imageUrl", Constants.URL_SERVER + "/icon?matchtitle=" + gameStarted + "&icon=" + media + "");

                                if (gameStarted.equals("none") || gameStarted.equals("SERVER_FULL") || gameStarted.equals("ALREADY_PLAYING")) {

                                    poi.accumulate("doNotIndex", false);
                                } else {

                                    poi.accumulate("doNotIndex", true);
                                }
                                poi.accumulate("inFocus", false);

                                poi.accumulate("showSmallBiw", true);

                                poi.accumulate("showBiwOnClick", false);



                                JSONObject icon = new JSONObject();
                                icon.accumulate("url", Constants.URL_SERVER + "/icon?matchtitle=" + gameStarted + "&icon=" + media + "");
                                icon.accumulate("type", "0");
                                poi.accumulate("icon", icon);


                                // Forces the POI's BIW style to "classic" or "collapsed". 
                                //Default is "classic" if the POI is a geolocated POI and "collapsed" 
                                //if the POI is a Vision enabled POI.
                                poi.accumulate("biwStyle", "classic");



                                // Actions
                                JSONArray actions = new JSONArray();
                                JSONObject action1 = new JSONObject();
                                action1.accumulate("uri", Constants.URL_SERVER + "/imajiematch/locations.jsp?zonePoint=" + title);
                                action1.accumulate("label", title + " Details");

                                // contenType
                                //"text/html", "text/plain", "audio/mpeg", "audio/mp4",
                                //"video/3gpp", "video/mp4", "application/vnd.layar.internal", "application/vnd.layar.async".
                                action1.accumulate("contentType", "text/html");
                                action1.accumulate("method", "GET");
                                action1.accumulate("activityType", 6);

                                action1.accumulate("params", null);
                                action1.accumulate("closeBiw", true);
                                action1.accumulate("showActivity", false);
                                action1.accumulate("activityMessage", "");
                                //action1.accumulate("autoTriggerRange", "10");
                                action1.accumulate("autoTriggerOnly ", false);
                                action1.accumulate("autoTrigger ", false); // Autotrigger indicator for Vision enabled POIs. 

                                actions.add(action1);


                                poi.accumulate("actions", actions);

                                // Transform values
                                JSONObject transform = new JSONObject();
                                //transform.accumulate("rotate", "");
                                //transform.accumulate("translate", "");
                                transform.accumulate("scale", params.getScale());
                                poi.accumulate("transform", transform);

                                // Transform values
                                JSONObject object = new JSONObject();
                                // Content type of the object. Can be one of the following:

                                //   image/vnd.layar.generic for any supported image type (PNG, GIF, JPEG)
                                //   model/vnd.layar.l3d for 3D models
                                //   image/jpeg, image/gif, image/png for images

                                if (media.contains(".jpg")) {
                                    object.accumulate("contentType", "image/jpeg");

                                } else {
                                    object.accumulate("contentType", "image/png");

                                }
                                object.accumulate("url", Constants.URL_SERVER + "/icon?matchtitle=" + gameStarted + "&icon=" + media + "");
                                //object.accumulate("reducedURL", Constants.URL_SERVER + "/icon?matchtitle=" + gameStarted + "&icon=" + media + "");
                                object.accumulate("size", params.getSize());
                                poi.accumulate("object", object);

                                hotspots.add(poi);


                                angle = angle + 30;
                            }
                        }

                    }
                }


            }


        }

        JSONArray layarActions = new JSONArray();


        //   **************************************************************************
        //****************************************************************************
        //
        //       Set  the default layer buttons
        //
        //****************************************************************************

        String authorized = "no";
        if (session.getAttribute("authorized") != null) {

            authorized = session.getAttribute("authorized").toString();

        }

        if ("yes".equals(authorized)) {

            JSONObject layarAction1 = new JSONObject();
            layarAction1.accumulate("contentType", "text/html");
            layarAction1.accumulate("method", "GET");
            layarAction1.accumulate("uri", Constants.URL_SERVER + "/imajiematch/controlpanel.jsp");
            layarAction1.accumulate("label", "Mission Control");
            layarAction1.accumulate("activityType", 3);
            layarActions.add(layarAction1);

            if (gameStarted.equals("none") || gameStarted.equals("SERVER_FULL") || gameStarted.equals("ALREADY_PLAYING")) {
                JSONObject layarAction1a = new JSONObject();
                layarAction1a.accumulate("contentType", "text/html");
                layarAction1a.accumulate("method", "GET");
                layarAction1a.accumulate("uri", Constants.URL_SERVER + "/imajiematch/getmatchs.jsp");
                layarAction1a.accumulate("label", "Choose Mission");
                layarAction1a.accumulate("activityType", 3);
                layarActions.add(layarAction1a);
            }


            if (!gameStarted.equals("none") && !gameStarted.equals("SERVER_FULL") && !gameStarted.equals("ALREADY_PLAYING")) {
                JSONObject layarAction2 = new JSONObject();
                layarAction2.accumulate("contentType", "text/html");
                layarAction2.accumulate("method", "GET");
                layarAction2.accumulate("uri", Constants.URL_SERVER + "/imajiematch/tasks.jsp");
                layarAction2.accumulate("label", "Tasks");
                layarAction2.accumulate("activityType", 3);
                layarActions.add(layarAction2);
            }


            if (!gameStarted.equals("none") && !gameStarted.equals("SERVER_FULL") && !gameStarted.equals("ALREADY_PLAYING")) {
                JSONObject layarAction3 = new JSONObject();
                layarAction3.accumulate("contentType", "text/html");
                layarAction3.accumulate("method", "GET");
                layarAction3.accumulate("uri", Constants.URL_SERVER + "/imajiematch/inventory.jsp");
                layarAction3.accumulate("label", "Inventory");
                layarAction3.accumulate("activityType", 3);
                layarActions.add(layarAction3);
            }

            if (!gameStarted.equals("none") && !gameStarted.equals("SERVER_FULL") && !gameStarted.equals("ALREADY_PLAYING")) {
                JSONObject layarAction4 = new JSONObject();
                layarAction4.accumulate("contentType", "text/html");
                layarAction4.accumulate("method", "GET");
                layarAction4.accumulate("uri", Constants.URL_SERVER + "/imajiematch/locations.jsp");
                layarAction4.accumulate("label", "Locations");
                layarAction4.accumulate("activityType", 3);
                layarActions.add(layarAction4);
            }
            if (!gameStarted.equals("none") && !gameStarted.equals("SERVER_FULL") && !gameStarted.equals("ALREADY_PLAYING")) {
                JSONObject layarAction5 = new JSONObject();
                layarAction5.accumulate("contentType", "text/html");
                layarAction5.accumulate("method", "GET");
                layarAction5.accumulate("uri", Constants.URL_SERVER + "/imajiematch/yousee.jsp");
                layarAction5.accumulate("label", "You See");
                layarAction5.accumulate("activityType", 3);
                layarActions.add(layarAction5);
            }
        } else {

            // If user is not loged in
            JSONObject layarAction1 = new JSONObject();
            layarAction1.accumulate("contentType", "text/html");
            layarAction1.accumulate("method", "GET");
            layarAction1.accumulate("uri", Constants.URL_SERVER + "/imajiematch/imajieste/login.jsp");
            layarAction1.accumulate("label", "Login");
            layarAction1.accumulate("activityType", 16);
            layarAction1.accumulate("autoTriggerRange", "10000");
            layarAction1.accumulate("autoTriggerOnly ", true);
            layarAction1.accumulate("autoTrigger ", true);
            layarActions.add(layarAction1);

            JSONObject layarAction2 = new JSONObject();
            layarAction2.accumulate("contentType", "text/html");
            layarAction2.accumulate("method", "GET");
            layarAction2.accumulate("uri", "http://imajie.tv/index.php?option=com_comprofiler&task=registers");
            layarAction2.accumulate("label", "Create an Account");
            layarAction2.accumulate("activityType", 1);
            layarAction2.accumulate("autoTriggerOnly ", false);
            layarAction2.accumulate("autoTrigger ", false);
            layarActions.add(layarAction2);
        }
        layer.accumulate("hotspots", hotspots);
        layer.accumulate("layer", layerName);
        if (count > 0) {
            layer.accumulate("errorString", "OK");
            layer.accumulate("errorCode", 0);

        } else {


            if (gameStarted.equals("none") || gameStarted.equals("SERVER_FULL") || gameStarted.equals("ALREADY_PLAYING")) {

                layer.accumulate("errorString", "No mission available at this location. Adjust the Radar Range in the parameter menu below.");


            } else {

                layer.accumulate("errorString", "Nothing available at this location. Adjust the Radar Range in the parameter menu or go to mission control for further details.");



            }

            layer.accumulate("errorCode", 21);
        }
        if (Dodialog || DoplayMediaCall) {

            layer.accumulate("refreshInterval", 30);

        } else {

            layer.accumulate("refreshInterval", 180);

        }

        layer.accumulate("refreshDistance", 15);



        if (gameStarted.equals("none") || gameStarted.equals("SERVER_FULL") || gameStarted.equals("ALREADY_PLAYING")) {
            layer.accumulate("fullRefresh", true);

        } else {

            layer.accumulate("fullRefresh", true);

        }

        layer.accumulate("morePages", false);
        layer.accumulate("nextPageKey", null);


        layer.accumulate("actions", layarActions);

        if (gameStarted.equals("none") || gameStarted.equals("SERVER_FULL") || gameStarted.equals("ALREADY_PLAYING")) {


            layer.accumulate("showMessage", "Go to layer actions menu for setting up your game.");


        } else {

            // TODO IMPLEMENTS SHOWDIALOG SESSION ATTRIBUTES        
            if (session.getAttribute("showDialog") != null) {
                if (session.getAttribute("showDialog").toString() != "null" || session.getAttribute("showDialog").toString().length() > 5) {

                    JSONObject showDialog = new JSONObject();

                    showDialog.accumulate("title", "Mission : " + gameStarted + "");



                    String msg = "";


                    String dialogTexts = "";
                    String dialogMedia = "";

                    if (session.getAttribute("dialogTexts") != null) {

                        dialogTexts = session.getAttribute("dialogTexts").toString();


                    }


                    if (session.getAttribute("dialogMedia") != null) {

                        dialogMedia = session.getAttribute("dialogMedia").toString();


                    }

                    msg = dialogTexts;

                    showDialog.accumulate("iconURL ", Constants.URL_SERVER + "/icon?matchtitle=" + gameStarted + "&icon=" + dialogMedia + "");

                    showDialog.accumulate("description", msg);

                    JSONArray messageActions = new JSONArray();

                    JSONObject messageAction1 = new JSONObject();


                    messageAction1.accumulate("contentType", "text/html");
                    messageAction1.accumulate("method", "GET");
                    messageAction1.accumulate("activityType", "1");
                    messageAction1.accumulate("uri", Constants.URL_SERVER + "/imajiematch/dialogCallback.jsp?button1=" + session.getAttribute("Button1"));
                    messageAction1.accumulate("label", session.getAttribute("Button1"));
                    //messageAction1.accumulate("label", "Tasks (" + counts + ")");
                    messageActions.add(messageAction1);


                    if (!session.getAttribute("Button2").toString().contains("null")) {
                        JSONObject messageAction2 = new JSONObject();


                        messageAction2.accumulate("contentType", "text/html");
                        messageAction2.accumulate("method", "GET");
                        messageAction2.accumulate("activityType", "1");
                        messageAction2.accumulate("uri", Constants.URL_SERVER + "/imajiematch/dialogCallback.jsp?button2=" + session.getAttribute("Button2"));
                        messageAction2.accumulate("label", session.getAttribute("Button2"));
                        //messageAction1.accumulate("label", "Tasks (" + counts + ")");
                        messageActions.add(messageAction2);
                    }


                    showDialog.accumulate("actions", messageActions);


                    layer.accumulate("showDialog", showDialog);
                }
            }
        }
        //layer.accumulate("deletedHotspots", "");

        //layer.accumulate("animations", "");


        //Forces the BIW style for all POIs to a certain form (verbose "classic" or lean "collapsed").
        //Set to null or do not send for default behavior, which is "classic" for geolocated POIs and "collapsed" 
        //for feature tracked POIs.

        layer.accumulate("biwStyle", "collapsed");


        layer.accumulate("disableClueMenu", false);


        out.write(layer.toString(2));



        Dodialog = false;

        DoplayMediaCall = false;

        out.close();


    }

    private static String generateSessionId() throws UnsupportedEncodingException {
        String uid = new java.rmi.server.UID().toString(); // guaranteed unique
        return URLEncoder.encode(uid, "UTF-8"); // encode any special chars
    }
}
