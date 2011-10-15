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

        String locale = request.getParameter("lang");
        String layerName = request.getParameter("layerName");
        String developerId = request.getParameter("developerId");
        double latitude = Utils.getDouble(request, "lat", 45.49);
        double longitude = Utils.getDouble(request, "lon", -73.56);
        double altitude = Utils.getDouble(request, "alt", 0);

        //**************************************
        //
        //     Setting the locales
        //*************************************
        String footnote;
        String missionLabel;
        String inventoryTitle;
        String inventoryDescription;
        String inventoryLabel;
        String youseeTitle;
        String youseeDescription;
        String youseeLabel;
        String locationsTitle;
        String locationsDescription;
        String getLocationsLabel;
        String tasksTitle;
        String taskDescription;
        String getTaskLabel;
        String parametersTitle;
        String parametersDescription;
        String getParametersLabel;
        String messageLabel;
        String dialogTitle;
        String detailsLabel;
        String missionControlLabel;
        String chooseMissionLabel;
        String loginLabel;
        String createAccountLabel;
        String errorString1;
        String errorString2;
        String showMessage;

        if ("fr".equals(locale) || "fr-ca".equals(locale) || "fr-br".equals(locale) || "fr-ca".equals(locale)) {
            footnote = org.imajie.server.locales.fr.locales.footnote;
            missionLabel = org.imajie.server.locales.fr.locales.missionLabel;
            inventoryTitle = org.imajie.server.locales.fr.locales.inventoryTitle;
            inventoryDescription = org.imajie.server.locales.fr.locales.inventoryDescription;
            inventoryLabel = org.imajie.server.locales.fr.locales.inventoryLabel;
            youseeTitle = org.imajie.server.locales.fr.locales.youseeTitle;
            youseeDescription = org.imajie.server.locales.fr.locales.youseeDescription;
            youseeLabel = org.imajie.server.locales.fr.locales.youseeLabel;
            locationsTitle = org.imajie.server.locales.fr.locales.locationsTitle;
            locationsDescription = org.imajie.server.locales.fr.locales.locationsDescription;
            getLocationsLabel = org.imajie.server.locales.fr.locales.getLocationsLabel;
            tasksTitle = org.imajie.server.locales.fr.locales.tasksTitle;
            taskDescription = org.imajie.server.locales.fr.locales.taskDescription;
            getTaskLabel = org.imajie.server.locales.fr.locales.getTaskLabel;
            parametersTitle = org.imajie.server.locales.fr.locales.parametersTitle;
            parametersDescription = org.imajie.server.locales.fr.locales.parametersDescription;
            getParametersLabel = org.imajie.server.locales.fr.locales.getParametersLabel;
            messageLabel = org.imajie.server.locales.fr.locales.messageLabel;
            dialogTitle = org.imajie.server.locales.fr.locales.dialogTitle;
            detailsLabel = org.imajie.server.locales.fr.locales.detailsLabel;
            missionControlLabel = org.imajie.server.locales.fr.locales.missionControlLabel;
            chooseMissionLabel = org.imajie.server.locales.fr.locales.chooseMissionLabel;
            loginLabel = org.imajie.server.locales.fr.locales.loginLabel;
            createAccountLabel = org.imajie.server.locales.fr.locales.createAccountLabel;
            errorString1 = org.imajie.server.locales.fr.locales.errorString1;
            errorString2 = org.imajie.server.locales.fr.locales.errorString2;
            showMessage = org.imajie.server.locales.fr.locales.showMessage;

        } else if ("en".equals(locale)) {

            footnote = org.imajie.server.locales.en.locales.footnote;
            missionLabel = org.imajie.server.locales.en.locales.missionLabel;
            inventoryTitle = org.imajie.server.locales.en.locales.inventoryTitle;
            inventoryDescription = org.imajie.server.locales.en.locales.inventoryDescription;
            inventoryLabel = org.imajie.server.locales.en.locales.inventoryLabel;
            youseeTitle = org.imajie.server.locales.en.locales.youseeTitle;
            youseeDescription = org.imajie.server.locales.en.locales.youseeDescription;
            youseeLabel = org.imajie.server.locales.en.locales.youseeLabel;
            locationsTitle = org.imajie.server.locales.en.locales.locationsTitle;
            locationsDescription = org.imajie.server.locales.en.locales.locationsDescription;
            getLocationsLabel = org.imajie.server.locales.en.locales.getLocationsLabel;
            tasksTitle = org.imajie.server.locales.en.locales.tasksTitle;
            taskDescription = org.imajie.server.locales.en.locales.taskDescription;
            getTaskLabel = org.imajie.server.locales.en.locales.getTaskLabel;
            parametersTitle = org.imajie.server.locales.en.locales.parametersTitle;
            parametersDescription = org.imajie.server.locales.en.locales.parametersDescription;
            getParametersLabel = org.imajie.server.locales.en.locales.getParametersLabel;
            messageLabel = org.imajie.server.locales.en.locales.messageLabel;
            dialogTitle = org.imajie.server.locales.en.locales.dialogTitle;
            detailsLabel = org.imajie.server.locales.en.locales.detailsLabel;
            missionControlLabel = org.imajie.server.locales.en.locales.missionControlLabel;
            chooseMissionLabel = org.imajie.server.locales.en.locales.chooseMissionLabel;
            loginLabel = org.imajie.server.locales.en.locales.loginLabel;
            createAccountLabel = org.imajie.server.locales.en.locales.createAccountLabel;
            errorString1 = org.imajie.server.locales.en.locales.errorString1;
            errorString2 = org.imajie.server.locales.en.locales.errorString2;
            showMessage = org.imajie.server.locales.en.locales.showMessage;


        } else {

            // Default language (EN)
            footnote = org.imajie.server.locales.en.locales.footnote;
            missionLabel = org.imajie.server.locales.en.locales.missionLabel;
            inventoryTitle = org.imajie.server.locales.en.locales.inventoryTitle;
            inventoryDescription = org.imajie.server.locales.en.locales.inventoryDescription;
            inventoryLabel = org.imajie.server.locales.en.locales.inventoryLabel;
            youseeTitle = org.imajie.server.locales.en.locales.youseeTitle;
            youseeDescription = org.imajie.server.locales.en.locales.youseeDescription;
            youseeLabel = org.imajie.server.locales.en.locales.youseeLabel;
            locationsTitle = org.imajie.server.locales.en.locales.locationsTitle;
            locationsDescription = org.imajie.server.locales.en.locales.locationsDescription;
            getLocationsLabel = org.imajie.server.locales.en.locales.getLocationsLabel;
            tasksTitle = org.imajie.server.locales.en.locales.tasksTitle;
            taskDescription = org.imajie.server.locales.en.locales.taskDescription;
            getTaskLabel = org.imajie.server.locales.en.locales.getTaskLabel;
            parametersTitle = org.imajie.server.locales.en.locales.parametersTitle;
            parametersDescription = org.imajie.server.locales.en.locales.parametersDescription;
            getParametersLabel = org.imajie.server.locales.en.locales.getParametersLabel;
            messageLabel = org.imajie.server.locales.en.locales.messageLabel;
            dialogTitle = org.imajie.server.locales.en.locales.dialogTitle;
            detailsLabel = org.imajie.server.locales.en.locales.detailsLabel;
            missionControlLabel = org.imajie.server.locales.en.locales.missionControlLabel;
            chooseMissionLabel = org.imajie.server.locales.en.locales.chooseMissionLabel;
            loginLabel = org.imajie.server.locales.en.locales.loginLabel;
            createAccountLabel = org.imajie.server.locales.en.locales.createAccountLabel;
            errorString1 = org.imajie.server.locales.en.locales.errorString1;
            errorString2 = org.imajie.server.locales.en.locales.errorString2;
            showMessage = org.imajie.server.locales.en.locales.showMessage;
        }
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

        String showControlPanel = "false";

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
                            text.accumulate("footnote", footnote);


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



                        poi.accumulate("showBiwOnClick", true);



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
                        action1.accumulate("label", missionLabel);

                        // contenType
                        //"text/html", "text/plain", "audio/mpeg", "audio/mp4",
                        //"video/3gpp", "video/mp4", "application/vnd.layar.internal", "application/vnd.layar.async".
                        action1.accumulate("contentType", "text/html");
                        action1.accumulate("method", "GET");
                        action1.accumulate("activityType", 1);
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
                        transform.accumulate("scale", "1.0");
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

                        //object.accumulate("size", "1.00");
                        poi.accumulate("object", object);

                        hotspots.add(poi);

                    }

                }
            }

        } else {



            if (session.getAttribute("showControlPanel") != null) {

                showControlPanel = session.getAttribute("showControlPanel").toString();


            }
            //*******************************************************************************
            //
            //            Determine if need to show control panel instead of regular pois
            //
            //*******************************************************************************

            if ("true".equals(showControlPanel)) {



                //***************************************
                // inventory icon poi
                //***************************************
                double inventoryAngle = 0;
                String inventoryLatitude = "" + (latitude + 0.001 * Math.cos(inventoryAngle));
                String inventoryLongitude = "" + (longitude + 0.001 * Math.sin(inventoryAngle));

                JSONObject inventoryPoi = new JSONObject();
                count++;

                inventoryPoi.accumulate("id", "Inventory");
                inventoryPoi.accumulate("anchor", "geo:" + inventoryLatitude + "," + inventoryLongitude + "");

                JSONObject inventoryText = new JSONObject();
                String InventoryCount = "0";
                if (session.getAttribute("inventoryList") != null) {

                    String delimiter = "\\|!\\|";
                    String[] temp = session.getAttribute("inventoryList").toString().split(delimiter);

                    if (temp.length == 0) {
                        InventoryCount = "0";
                    } else {
                        InventoryCount = InventoryCount + (temp.length - 1);
                    }

                } else {

                    InventoryCount = "0";

                }

                inventoryText.accumulate("title", inventoryTitle + " (" + InventoryCount + ")");

                String inventoryList = "";
                if (session.getAttribute("inventoryList") != null) {

                    String list = (String) session.getAttribute("inventoryList");
                    count = list.length();
                    if (count > 0) {


                        String delimiter = "\\|!\\|";
                        String[] temp = list.split(delimiter);
                        for (int i = 0; i < temp.length; i++) {
                            if (i == 0) {
                            } else {


                                String title = "";
                                String description = "";
                                String hotspotAltitude = "";
                                String hotspotLatitude = "";
                                String hotspotLongitude = "";
                                String hotspotDistance = "";
                                String media = "";

                                String hotspot = temp[i];
                                String delimiter2 = "\\|\\|";
                                String[] temp2 = hotspot.split(delimiter2);

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
                                        media = temp2[ii];
                                    }

                                }


                                inventoryList = inventoryList + title + " :" + description + "<br>";
                            }

                        }

                        if ("".equals(inventoryList)) {

                            inventoryList = "Nothing in your inventory";


                        }


                    }
                } else {

                    inventoryList = "Nothing in your inventory";

                }

                inventoryText.accumulate("description", inventoryList);
                inventoryText.accumulate("footnote", footnote);
                inventoryPoi.accumulate("text", inventoryText);
                inventoryPoi.accumulate("imageUrl", Constants.URL_SERVER + "/imajiematch/images/backpack.png");
                inventoryPoi.accumulate("doNotIndex", true);
                inventoryPoi.accumulate("inFocus", false);
                inventoryPoi.accumulate("showSmallBiw", true);
                inventoryPoi.accumulate("showBiwOnClick", true);

                JSONObject inventoryIcon = new JSONObject();
                inventoryIcon.accumulate("url", Constants.URL_SERVER + "/imajiematch/images/backpack.png");
                inventoryIcon.accumulate("type", "0");
                inventoryPoi.accumulate("icon", inventoryIcon);

                inventoryPoi.accumulate("biwStyle", "classic");

                JSONArray inventoryActions = new JSONArray();
                JSONObject inventoryAction1 = new JSONObject();
                inventoryAction1.accumulate("uri", Constants.URL_SERVER + "/imajiematch/inventory.jsp");
                inventoryAction1.accumulate("label", inventoryLabel);
                inventoryAction1.accumulate("contentType", "text/html");
                inventoryAction1.accumulate("method", "GET");
                inventoryAction1.accumulate("activityType", 6);

                inventoryAction1.accumulate("params", null);
                inventoryAction1.accumulate("closeBiw", true);
                inventoryAction1.accumulate("showActivity", false);
                inventoryAction1.accumulate("activityMessage", "");
                inventoryAction1.accumulate("autoTriggerOnly ", false);
                inventoryAction1.accumulate("autoTrigger ", false); // Autotrigger indicator for Vision enabled POIs. 

                inventoryActions.add(inventoryAction1);
                
                JSONObject inventoryAction2 = new JSONObject();
                inventoryAction2.accumulate("uri", Constants.URL_SERVER + "layar://");
                inventoryAction2.accumulate("label", missionLabel);
                inventoryAction2.accumulate("contentType", "application/vnd.layar.async");
                inventoryAction2.accumulate("method", "GET");
                inventoryAction2.accumulate("activityType", 6);

                inventoryAction2.accumulate("params", null);
                inventoryAction2.accumulate("closeBiw", true);
                inventoryAction2.accumulate("showActivity", false);
                inventoryAction2.accumulate("activityMessage", "");
                inventoryAction2.accumulate("autoTriggerOnly ", false);
                inventoryAction2.accumulate("autoTrigger ", false); // Autotrigger indicator for Vision enabled POIs. 

                inventoryActions.add(inventoryAction2);

                inventoryPoi.accumulate("actions", inventoryActions);

                // Transform values
                JSONObject inventoryTransform = new JSONObject();
                inventoryTransform.accumulate("scale", "1.0");
                inventoryPoi.accumulate("transform", inventoryTransform);

                // Transform values
                JSONObject inventoryObject = new JSONObject();

                inventoryObject.accumulate("contentType", "image/png");
                inventoryObject.accumulate("url", Constants.URL_SERVER + "/imajiematch/inventory.jsp");
                inventoryObject.accumulate("size", "1.00");
                inventoryPoi.accumulate("object", inventoryObject);

                hotspots.add(inventoryPoi);




                //***************************************
                // yousee icon poi
                //***************************************
                double youseeAngle = 30;
                String youseeLatitude = "" + (latitude + 0.001 * Math.cos(youseeAngle));
                String youseeLongitude = "" + (longitude + 0.001 * Math.sin(youseeAngle));

                JSONObject youseePoi = new JSONObject();
                count++;

                youseePoi.accumulate("id", "yousee");
                youseePoi.accumulate("anchor", "geo:" + youseeLatitude + "," + youseeLongitude + "");

                JSONObject youseeText = new JSONObject();    
                
                String YouseeCount = "0";
                if (session.getAttribute("youseeList") != null) {

                    String delimiter = "\\|!\\|";
                    String[] temp = session.getAttribute("youseeList").toString().split(delimiter);

                    if (temp.length == 0) {
                        YouseeCount = "0";
                    } else {
                        YouseeCount = YouseeCount + (temp.length - 1);
                    }

                } else {

                    YouseeCount = "0";

                }

                youseeText.accumulate("title", youseeTitle + " (" + YouseeCount + ")");

                String youseeList = "";
                if (session.getAttribute("youseeList") != null) {

                    String list = (String) session.getAttribute("youseeList");
                    count = list.length();
                    if (count > 0) {


                        String delimiter = "\\|!\\|";
                        String[] temp = list.split(delimiter);
                        for (int i = 0; i < temp.length; i++) {
                            if (i == 0) {
                            } else {


                                String title = "";
                                String description = "";
                                String hotspotAltitude = "";
                                String hotspotLatitude = "";
                                String hotspotLongitude = "";
                                String hotspotDistance = "";
                                String media = "";

                                String hotspot = temp[i];
                                String delimiter2 = "\\|\\|";
                                String[] temp2 = hotspot.split(delimiter2);

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
                                        media = temp2[ii];
                                    }

                                }


                                youseeList = youseeList + title + " :" + description + "<br>";
                            }

                        }

                        if ("".equals(youseeList)) {

                            youseeList = "Nothing to see";


                        }


                    }
                } else {

                    youseeList = "Nothing to see";

                }

                youseeText.accumulate("description", youseeList);
                youseeText.accumulate("footnote", footnote);
                youseePoi.accumulate("text", youseeText);
                youseePoi.accumulate("imageUrl", Constants.URL_SERVER + "/imajiematch/images/binoculars.png");
                youseePoi.accumulate("doNotIndex", true);
                youseePoi.accumulate("inFocus", false);
                youseePoi.accumulate("showSmallBiw", true);
                youseePoi.accumulate("showBiwOnClick", true);

                JSONObject youseeIcon = new JSONObject();
                youseeIcon.accumulate("url", Constants.URL_SERVER + "/imajiematch/images/binoculars.png");
                youseeIcon.accumulate("type", "0");
                youseePoi.accumulate("icon", youseeIcon);

                youseePoi.accumulate("biwStyle", "classic");

                JSONArray youseeActions = new JSONArray();
                JSONObject youseeAction1 = new JSONObject();
                youseeAction1.accumulate("uri", Constants.URL_SERVER + "/imajiematch/yousee.jsp");
                youseeAction1.accumulate("label", youseeLabel);
                youseeAction1.accumulate("contentType", "text/html");
                youseeAction1.accumulate("method", "GET");
                youseeAction1.accumulate("activityType", 6);

                youseeAction1.accumulate("params", null);
                youseeAction1.accumulate("closeBiw", true);
                youseeAction1.accumulate("showActivity", false);
                youseeAction1.accumulate("activityMessage", "");
                youseeAction1.accumulate("autoTriggerOnly ", false);
                youseeAction1.accumulate("autoTrigger ", false); // Autotrigger indicator for Vision enabled POIs. 

                youseeActions.add(youseeAction1);

                JSONObject youseeAction2 = new JSONObject();
                youseeAction2.accumulate("uri", Constants.URL_SERVER + "layar://");
                youseeAction2.accumulate("label", missionLabel);
                youseeAction2.accumulate("contentType", "application/vnd.layar.async");
                youseeAction2.accumulate("method", "GET");
                youseeAction2.accumulate("activityType", 6);

                youseeAction2.accumulate("params", null);
                youseeAction2.accumulate("closeBiw", true);
                youseeAction2.accumulate("showActivity", false);
                youseeAction2.accumulate("activityMessage", "");
                youseeAction2.accumulate("autoTriggerOnly ", false);
                youseeAction2.accumulate("autoTrigger ", false); // Autotrigger indicator for Vision enabled POIs. 

                youseeActions.add(youseeAction2);
                
                youseePoi.accumulate("actions", youseeActions);

                // Transform values
                JSONObject youseeTransform = new JSONObject();
                youseeTransform.accumulate("scale", "1.0");
                youseePoi.accumulate("transform", youseeTransform);

                // Transform values
                JSONObject youseeObject = new JSONObject();

                youseeObject.accumulate("contentType", "image/png");
                youseeObject.accumulate("url", Constants.URL_SERVER + "/imajiematch/yousee.jsp");
                youseeObject.accumulate("size", "1.00");
                youseePoi.accumulate("object", youseeObject);

                hotspots.add(youseePoi);



                //***************************************
                // locations icon poi
                //***************************************
                double locationsAngle = 60;
                String locationsLatitude = "" + (latitude + 0.001 * Math.cos(locationsAngle));
                String locationsLongitude = "" + (longitude + 0.001 * Math.sin(locationsAngle));

                JSONObject locationsPoi = new JSONObject();
                count++;

                locationsPoi.accumulate("id", "locations");
                locationsPoi.accumulate("anchor", "geo:" + locationsLatitude + "," + locationsLongitude + "");

                JSONObject locationsText = new JSONObject();
    
                
                String LocationsCount = "0";
                if (session.getAttribute("locationsList") != null) {

                    String delimiter = "\\|!\\|";
                    String[] temp = session.getAttribute("locationsList").toString().split(delimiter);

                    if (temp.length == 0) {
                        LocationsCount = "0";
                    } else {
                        LocationsCount = LocationsCount + (temp.length - 1);
                    }

                } else {

                    LocationsCount = "0";

                }

               
                locationsText.accumulate("title", locationsTitle+ " (" + LocationsCount + ")");
                
                String locationsList = "";
                if (session.getAttribute("locationsList") != null) {

                    String list = (String) session.getAttribute("locationsList");
                    count = list.length();
                    if (count > 0) {


                        String delimiter = "\\|!\\|";
                        String[] temp = list.split(delimiter);
                        for (int i = 0; i < temp.length; i++) {
                            if (i == 0) {
                            } else {


                                String title = "";
                                String description = "";
                                String hotspotAltitude = "";
                                String hotspotLatitude = "";
                                String hotspotLongitude = "";
                                String hotspotDistance = "";
                                String media = "";

                                String hotspot = temp[i];
                                String delimiter2 = "\\|\\|";
                                String[] temp2 = hotspot.split(delimiter2);

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
                                        media = temp2[ii];
                                    }

                                }


                                locationsList = locationsList + title + " :" + description + "<br>";
                            }

                        }

                        if ("".equals(locationsList)) {

                            locationsList = "No place to go";


                        }


                    }
                } else {

                    locationsList = "No place to go";

                }

                locationsText.accumulate("description", locationsList);
    
                locationsText.accumulate("footnote", footnote);
                locationsPoi.accumulate("text", locationsText);
                locationsPoi.accumulate("imageUrl", Constants.URL_SERVER + "/imajiematch/images/compass.png");
                locationsPoi.accumulate("doNotIndex", true);
                locationsPoi.accumulate("inFocus", false);
                locationsPoi.accumulate("showSmallBiw", true);
                locationsPoi.accumulate("showBiwOnClick", true);

                JSONObject locationsIcon = new JSONObject();
                locationsIcon.accumulate("url", Constants.URL_SERVER + "/imajiematch/images/compass.png");
                locationsIcon.accumulate("type", "0");
                locationsPoi.accumulate("icon", locationsIcon);

                locationsPoi.accumulate("biwStyle", "classic");

                JSONArray locationsActions = new JSONArray();
                JSONObject locationsAction1 = new JSONObject();
                locationsAction1.accumulate("uri", Constants.URL_SERVER + "/imajiematch/locations.jsp");
                locationsAction1.accumulate("label", getLocationsLabel);
                locationsAction1.accumulate("contentType", "text/html");
                locationsAction1.accumulate("method", "GET");
                locationsAction1.accumulate("activityType", 6);

                locationsAction1.accumulate("params", null);
                locationsAction1.accumulate("closeBiw", true);
                locationsAction1.accumulate("showActivity", false);
                locationsAction1.accumulate("activityMessage", "");
                locationsAction1.accumulate("autoTriggerOnly ", false);
                locationsAction1.accumulate("autoTrigger ", false); // Autotrigger indicator for Vision enabled POIs. 

                locationsActions.add(locationsAction1);

                JSONObject locationsAction2 = new JSONObject();
                locationsAction2.accumulate("uri", Constants.URL_SERVER + "layar://");
                locationsAction2.accumulate("label", missionLabel);
                locationsAction2.accumulate("contentType", "application/vnd.layar.async");
                locationsAction2.accumulate("method", "GET");
                locationsAction2.accumulate("activityType", 6);

                locationsAction2.accumulate("params", null);
                locationsAction2.accumulate("closeBiw", true);
                locationsAction2.accumulate("showActivity", false);
                locationsAction2.accumulate("activityMessage", "");
                locationsAction2.accumulate("autoTriggerOnly ", false);
                locationsAction2.accumulate("autoTrigger ", false); // Autotrigger indicator for Vision enabled POIs. 

                locationsActions.add(locationsAction2);
                
                locationsPoi.accumulate("actions", locationsActions);

                // Transform values
                JSONObject locationsTransform = new JSONObject();
                locationsTransform.accumulate("scale", "1.0");
                locationsPoi.accumulate("transform", locationsTransform);

                // Transform values
                JSONObject locationsObject = new JSONObject();

                locationsObject.accumulate("contentType", "image/png");
                locationsObject.accumulate("url", Constants.URL_SERVER + "/imajiematch/locations.jsp");
                locationsObject.accumulate("size", "1.00");
                locationsPoi.accumulate("object", locationsObject);

                hotspots.add(locationsPoi);


                //***************************************
                // tasks icon poi
                //***************************************
                double tasksAngle = 90;
                String tasksLatitude = "" + (latitude + 0.001 * Math.cos(tasksAngle));
                String tasksLongitude = "" + (longitude + 0.001 * Math.sin(tasksAngle));

                JSONObject tasksPoi = new JSONObject();
                count++;

                tasksPoi.accumulate("id", "tasks");
                tasksPoi.accumulate("anchor", "geo:" + tasksLatitude + "," + tasksLongitude + "");


                JSONObject tasksText = new JSONObject();
                
                
                
                
               
                
                
                
                
                
                String TasksCount = "0";
                if (session.getAttribute("tasksList") != null) {

                    String delimiter = "\\|!\\|";
                    String[] temp = session.getAttribute("tasksList").toString().split(delimiter);

                    if (temp.length == 0) {
                        TasksCount = "0";
                    } else {
                        TasksCount = TasksCount + (temp.length - 1);
                    }

                } else {

                    TasksCount = "0";

                }

                tasksText.accumulate("title", tasksTitle+ " (" + TasksCount + ")");
             
                
                String tasksList = "";
                if (session.getAttribute("tasksList") != null) {

                    String list = (String) session.getAttribute("tasksList");
                    count = list.length();
                    if (count > 0) {


                        String delimiter = "\\|!\\|";
                        String[] temp = list.split(delimiter);
                        for (int i = 0; i < temp.length; i++) {
                            if (i == 0) {
                            } else {


                                String title = "";
                                String description = "";
                                String hotspotAltitude = "";
                                String hotspotLatitude = "";
                                String hotspotLongitude = "";
                                String hotspotDistance = "";
                                String media = "";

                                String hotspot = temp[i];
                                String delimiter2 = "\\|\\|";
                                String[] temp2 = hotspot.split(delimiter2);

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
                                        media = temp2[ii];
                                    }

                                }


                                tasksList = tasksList + title + " :" + description + "<br>";
                            }

                        }

                        if ("".equals(tasksList)) {

                            tasksList = "Nothing to do";


                        }


                    }
                } else {

                    tasksList = "Nothing to do";

                }

             
                
                tasksText.accumulate("description", tasksList);
                tasksText.accumulate("footnote", footnote);
                tasksPoi.accumulate("text", tasksText);
                tasksPoi.accumulate("imageUrl", Constants.URL_SERVER + "/imajiematch/images/tasks_folder.png");
                tasksPoi.accumulate("doNotIndex", true);
                tasksPoi.accumulate("inFocus", false);
                tasksPoi.accumulate("showSmallBiw", true);
                tasksPoi.accumulate("showBiwOnClick", true);

                JSONObject tasksIcon = new JSONObject();
                tasksIcon.accumulate("url", Constants.URL_SERVER + "/imajiematch/images/tasks_folder.png");
                tasksIcon.accumulate("type", "0");
                tasksPoi.accumulate("icon", tasksIcon);

                tasksPoi.accumulate("biwStyle", "classic");

                JSONArray tasksActions = new JSONArray();
                JSONObject tasksAction1 = new JSONObject();
                tasksAction1.accumulate("uri", Constants.URL_SERVER + "/imajiematch/tasks.jsp");
                tasksAction1.accumulate("label", getTaskLabel);
                tasksAction1.accumulate("contentType", "text/html");
                tasksAction1.accumulate("method", "GET");
                tasksAction1.accumulate("activityType", 6);

                tasksAction1.accumulate("params", null);
                tasksAction1.accumulate("closeBiw", true);
                tasksAction1.accumulate("showActivity", false);
                tasksAction1.accumulate("activityMessage", "");
                tasksAction1.accumulate("autoTriggerOnly ", false);
                tasksAction1.accumulate("autoTrigger ", false); // Autotrigger indicator for Vision enabled POIs. 

                tasksActions.add(tasksAction1);

                JSONObject tasksAction2 = new JSONObject();
                tasksAction2.accumulate("uri", Constants.URL_SERVER + "layar://");
                tasksAction2.accumulate("label", missionLabel);
                tasksAction2.accumulate("contentType", "application/vnd.layar.async");
                tasksAction2.accumulate("method", "GET");
                tasksAction2.accumulate("activityType", 6);

                tasksAction2.accumulate("params", null);
                tasksAction2.accumulate("closeBiw", true);
                tasksAction2.accumulate("showActivity", false);
                tasksAction2.accumulate("activityMessage", "");
                tasksAction2.accumulate("autoTriggerOnly ", false);
                tasksAction2.accumulate("autoTrigger ", false); // Autotrigger indicator for Vision enabled POIs. 

                tasksActions.add(tasksAction2);
                
                tasksPoi.accumulate("actions", tasksActions);

                // Transform values
                JSONObject tasksTransform = new JSONObject();
                tasksTransform.accumulate("scale", "1.0");
                tasksPoi.accumulate("transform", tasksTransform);

                // Transform values
                JSONObject tasksObject = new JSONObject();

                tasksObject.accumulate("contentType", "image/png");
                tasksObject.accumulate("url", Constants.URL_SERVER + "/imajiematch/tasks.jsp");
                tasksObject.accumulate("size", "1.00");
                tasksPoi.accumulate("object", tasksObject);

                hotspots.add(tasksPoi);

                //***************************************
                // parameters icon poi
                //***************************************
                double parametersAngle = 120;
                String parametersLatitude = "" + (latitude + 0.001 * Math.cos(parametersAngle));
                String parametersLongitude = "" + (longitude + 0.001 * Math.sin(parametersAngle));

                JSONObject parametersPoi = new JSONObject();
                count++;

                parametersPoi.accumulate("id", "parameters");
                parametersPoi.accumulate("anchor", "geo:" + parametersLatitude + "," + parametersLongitude + "");


                JSONObject parametersText = new JSONObject();
                parametersText.accumulate("title", parametersTitle);
                parametersText.accumulate("description", parametersDescription);
                parametersText.accumulate("footnote", footnote);
                parametersPoi.accumulate("text", parametersText);
                parametersPoi.accumulate("imageUrl", Constants.URL_SERVER + "/imajiematch/images/control_panel.png");
                parametersPoi.accumulate("doNotIndex", true);
                parametersPoi.accumulate("inFocus", false);
                parametersPoi.accumulate("showSmallBiw", true);
                parametersPoi.accumulate("showBiwOnClick", true);

                JSONObject parametersIcon = new JSONObject();
                parametersIcon.accumulate("url", Constants.URL_SERVER + "/imajiematch/images/control_panel.png");
                parametersIcon.accumulate("type", "0");
                parametersPoi.accumulate("icon", parametersIcon);

                parametersPoi.accumulate("biwStyle", "classic");

                JSONArray parametersActions = new JSONArray();
                JSONObject parametersAction1 = new JSONObject();
                parametersAction1.accumulate("uri", Constants.URL_SERVER + "/imajiematch/parameters.jsp");
                parametersAction1.accumulate("label", getParametersLabel);
                parametersAction1.accumulate("contentType", "text/html");
                parametersAction1.accumulate("method", "GET");
                parametersAction1.accumulate("activityType", 6);

                parametersAction1.accumulate("params", null);
                parametersAction1.accumulate("closeBiw", true);
                parametersAction1.accumulate("showActivity", false);
                parametersAction1.accumulate("activityMessage", "");
                parametersAction1.accumulate("autoTriggerOnly ", false);
                parametersAction1.accumulate("autoTrigger ", false); // Autotrigger indicator for Vision enabled POIs. 

                parametersActions.add(parametersAction1);

                JSONObject parametersAction2 = new JSONObject();
                parametersAction2.accumulate("uri", Constants.URL_SERVER + "layar://");
                parametersAction2.accumulate("label", missionLabel);
                parametersAction2.accumulate("contentType", "application/vnd.layar.async");
                parametersAction2.accumulate("method", "GET");
                parametersAction2.accumulate("activityType", 6);

                parametersAction2.accumulate("params", null);
                parametersAction2.accumulate("closeBiw", true);
                parametersAction2.accumulate("showActivity", false);
                parametersAction2.accumulate("activityMessage", "");
                parametersAction2.accumulate("autoTriggerOnly ", false);
                parametersAction2.accumulate("autoTrigger ", false); // Autotrigger indicator for Vision enabled POIs. 

                parametersActions.add(parametersAction2);
                
                parametersPoi.accumulate("actions", parametersActions);

                // Transform values
                JSONObject parametersTransform = new JSONObject();
                parametersTransform.accumulate("scale", "1.0");
                parametersPoi.accumulate("transform", parametersTransform);

                // Transform values
                JSONObject parametersObject = new JSONObject();

                parametersObject.accumulate("contentType", "image/png");
                parametersObject.accumulate("url", Constants.URL_SERVER + "/imajiematch/parameters.jsp");
                parametersObject.accumulate("size", "1.00");
                parametersPoi.accumulate("object", parametersObject);

                hotspots.add(parametersPoi);



            } else {

                String[] controlPanel = new String[]{"parameters", "inventory", "yousee", "locations", "tasks"};

                layer.accumulate("deletedHotspots", controlPanel);
                //"deletedHotspots":["spot0001", "spot0002"]

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
                    text.accumulate("title", StringEscapeUtils.escapeJavaScript(messageLabel));


                    text.accumulate("footnote", footnote);
                    poi.accumulate("text", text);

                    JSONArray actions = new JSONArray();

                    if (playMediaCall.contains("||***VIDEO***")) {

                        String media = playMediaCall.replace("||***VIDEO***", "");
                        media = media.replace(".png", ".mp4");
                        JSONObject layarActionMedias = new JSONObject();
                        layarActionMedias.accumulate("contentType", "video/mp4");
                        layarActionMedias.accumulate("method", "GET");
                        layarActionMedias.accumulate("uri", "video://" + Constants.URL_SERVER.replace("http://", "") + "/icon?matchtitle=" + gameStarted + "&icon=" + media + "");
                        layarActionMedias.accumulate("label", messageLabel);
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
                        layarActionMedias.accumulate("label", messageLabel);
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
                    text.accumulate("title", StringEscapeUtils.escapeJavaScript(dialogTitle));


                    text.accumulate("footnote", footnote);
                    poi.accumulate("text", text);

                    JSONArray actions = new JSONArray();


                    JSONObject layarActionMedias = new JSONObject();
                    layarActionMedias.accumulate("contentType", "text/html");
                    layarActionMedias.accumulate("method", "GET");
                    layarActionMedias.accumulate("uri", Constants.URL_SERVER + "/imajiematch/start2.jsp");
                    layarActionMedias.accumulate("label", messageLabel);
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
                                    text.accumulate("footnote", footnote);
                                    poi.accumulate("text", text);


                                    poi.accumulate("imageUrl", Constants.URL_SERVER + "/icon?matchtitle=" + gameStarted + "&icon=" + media + "");

                                    if (gameStarted.equals("none") || gameStarted.equals("SERVER_FULL") || gameStarted.equals("ALREADY_PLAYING")) {

                                        poi.accumulate("doNotIndex", false);
                                    } else {

                                        poi.accumulate("doNotIndex", true);
                                    }
                                    poi.accumulate("inFocus", false);

                                    poi.accumulate("showSmallBiw", true);

                                    poi.accumulate("showBiwOnClick", true);



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
                                    action1.accumulate("label", title + detailsLabel);

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
                                    transform.accumulate("scale", "1.0");
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
                                    object.accumulate("size", "1.00");
                                    poi.accumulate("object", object);

                                    hotspots.add(poi);
                                }

                            }
                        }

                    }
                    //**********************************************************************************
                    //
                    //         Set Yousee pois and place it around the player if no position specified
                    //
                    //**********************************************************************************
                    String youseeList = "";

                    if (session.getAttribute("youseeList") != null) {

                        youseeList = session.getAttribute("youseeList").toString();


                    }
                    String delimiter3 = "\\|!\\|";
                    String[] temp3 = youseeList.split(delimiter3);

                    if (temp3.length > 0) {

                        double angle = 0;

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
                                    String buttonsArray = "";


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
                                        if (ii == 7) {
                                            buttonsArray = temp2[ii];
                                        }
                                    }



                                    String[] buttonsray = buttonsArray.split("\\*\\*");

//                                x = cx + r * cos(a)
//                                y = cy + r * sin(a)
// float x = (float)(radius * Math.Cos(angleInDegrees * Math.PI / 180F)) + origin.X;
//        float y = (float)(radius * Math.Sin(angleInDegrees * Math.PI / 180F)) + origin.Y;



                                    if ("".equals(hotspotLatitude) && "".equals(hotspotLongitude)) {

                                        hotspotLatitude = "" + (latitude + 0.001 * Math.cos(angle));
                                        hotspotLongitude = "" + (longitude + 0.001 * Math.sin(angle));
                                        hotspotAltitude = "0.0";
                                    }

                                    JSONObject poi = new JSONObject();
                                    count++;



                                    poi.accumulate("id", StringEscapeUtils.escapeJavaScript(title));
                                    poi.accumulate("anchor", "geo:" + hotspotLatitude + "," + hotspotLongitude + "");

                                    JSONObject text = new JSONObject();
                                    text.accumulate("title", StringEscapeUtils.escapeJavaScript(title));
                                    text.accumulate("description", description);
                                    text.accumulate("footnote", footnote);
                                    poi.accumulate("text", text);

                                    poi.accumulate("imageUrl", Constants.URL_SERVER + "/icon?matchtitle=" + gameStarted + "&icon=" + media + "");

                                    if (gameStarted.equals("none") || gameStarted.equals("SERVER_FULL") || gameStarted.equals("ALREADY_PLAYING")) {

                                        poi.accumulate("doNotIndex", false);
                                    } else {

                                        poi.accumulate("doNotIndex", true);
                                    }
                                    poi.accumulate("inFocus", false);

                                    poi.accumulate("showSmallBiw", true);

                                    poi.accumulate("showBiwOnClick", true);



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


                                    for (int iiii = 0; iiii < buttonsray.length; iiii++) {


                                        JSONObject action1 = new JSONObject();
                                        action1.accumulate("uri", Constants.URL_SERVER + "/imajiematch/dialogCallback.jsp?thingButton=" + title + ".On" + buttonsray[iiii]);
                                        action1.accumulate("label", buttonsray[iiii]);

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


                                    }








                                    poi.accumulate("actions", actions);

                                    // Transform values
                                    JSONObject transform = new JSONObject();
                                    //transform.accumulate("rotate", "");
                                    //transform.accumulate("translate", "");
                                    transform.accumulate("scale", "1.0");
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
                                    object.accumulate("size", "1.00");
                                    poi.accumulate("object", object);

                                    hotspots.add(poi);


                                    angle = angle + 30;
                                }
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
            layarAction1.accumulate("uri", Constants.URL_SERVER + "/imajiematch/dialogCallback.jsp?showControlPanel=true");
            layarAction1.accumulate("label", missionControlLabel);
            layarAction1.accumulate("activityType", 32);
            layarActions.add(layarAction1);

            if (gameStarted.equals("none") || gameStarted.equals("SERVER_FULL") || gameStarted.equals("ALREADY_PLAYING")) {
                JSONObject layarAction1a = new JSONObject();
                layarAction1a.accumulate("contentType", "text/html");
                layarAction1a.accumulate("method", "GET");
                layarAction1a.accumulate("uri", Constants.URL_SERVER + "/imajiematch/getmatchs.jsp");
                layarAction1a.accumulate("label", chooseMissionLabel);
                layarAction1a.accumulate("activityType", 33);
                layarActions.add(layarAction1a);
            }



        } else {

            // If user is not loged in
            JSONObject layarAction1 = new JSONObject();
            layarAction1.accumulate("contentType", "text/html");
            layarAction1.accumulate("method", "GET");
            layarAction1.accumulate("uri", Constants.URL_SERVER + "/imajiematch/imajieste/login.jsp");
            layarAction1.accumulate("label", loginLabel);
            layarAction1.accumulate("activityType", 16);
            layarAction1.accumulate("autoTriggerRange", "10000");
            layarAction1.accumulate("autoTriggerOnly ", true);
            layarAction1.accumulate("autoTrigger ", true);
            layarActions.add(layarAction1);

            JSONObject layarAction2 = new JSONObject();
            layarAction2.accumulate("contentType", "text/html");
            layarAction2.accumulate("method", "GET");
            layarAction2.accumulate("uri", "http://imajie.tv/index.php?option=com_comprofiler&task=registers");
            layarAction2.accumulate("label", createAccountLabel);
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

                layer.accumulate("errorString", errorString1);


            } else {

                layer.accumulate("errorString", errorString2);



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


            layer.accumulate("showMessage", showMessage);


        } else {

            String showDialogPlayed = "null";
            if (session.getAttribute("showDialogPlayed") != null) {
                showDialogPlayed = session.getAttribute("showDialogPlayed").toString();
            } else {

                session.setAttribute("showDialogPlayed", "null");
            }



            if (session.getAttribute("showDialog") != null) {
                if (showControlPanel.contains("false") && !showDialogPlayed.equals(session.getAttribute("showDialog").toString())) {

                    if (!"null".equals(session.getAttribute("showDialog").toString()) || session.getAttribute("showDialog").toString().length() > 5) {

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
                        messageAction1.accumulate("uri", Constants.URL_SERVER + "/imajiematch/dialogCallback.jsp?button1=Button1");
                        messageAction1.accumulate("label", session.getAttribute("Button1"));
                        //messageAction1.accumulate("label", "Tasks (" + counts + ")");
                        messageActions.add(messageAction1);


                        if (!session.getAttribute("Button2").toString().contains("null")) {
                            JSONObject messageAction2 = new JSONObject();


                            messageAction2.accumulate("contentType", "text/html");
                            messageAction2.accumulate("method", "GET");
                            messageAction2.accumulate("activityType", "1");
                            messageAction2.accumulate("uri", Constants.URL_SERVER + "/imajiematch/dialogCallback.jsp?button2=Button2");
                            messageAction2.accumulate("label", session.getAttribute("Button2"));
                            //messageAction1.accumulate("label", "Tasks (" + counts + ")");
                            messageActions.add(messageAction2);
                        }


                        showDialog.accumulate("actions", messageActions);


                        layer.accumulate("showDialog", showDialog);
                        session.setAttribute("showDialogPlayed", dialogTexts);
                    }
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
        if (session.getAttribute("showControlPanel") != null) {

            if (!session.getAttribute("showControlPanel").toString().equals("false")) {

                session.setAttribute("showDialogPlayed", "null");
                session.setAttribute("showControlPanel", "false");


            }


        }

        Dodialog = false;

        DoplayMediaCall = false;

        out.close();


    }

    private static String generateSessionId() throws UnsupportedEncodingException {
        String uid = new java.rmi.server.UID().toString(); // guaranteed unique
        return URLEncoder.encode(uid, "UTF-8"); // encode any special chars
    }
}
