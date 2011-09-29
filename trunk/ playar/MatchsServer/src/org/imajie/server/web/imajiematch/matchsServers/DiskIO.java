/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imajie.server.web.imajiematch.matchsServers;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.imajie.server.web.imajiematch.matchsServers.format.CartridgeFile;
import org.imajie.server.web.imajiematch.matchsServers.j2se.J2SEFileHandle;
import org.imajie.server.web.imajiematch.matchsServers.j2se.J2SESeekableFile;
import org.imajie.server.web.imajiematch.matchsServers.platform.FileHandle;
import org.imajie.server.web.imajiematch.matchsServers.platform.SeekableFile;
import java.io.RandomAccessFile;
import org.imajie.server.web.Constants;



public final class DiskIO {

    private static final Logger LOG = Logger.getLogger("org.imajie.tv");

    private DiskIO() {
        // don't instantiate this
    }

    public static String[] matchDetails(String matchTitle) {

        matchToplay = matchTitle;


        return matchDetail();
    }

    public static CartridgeFile start(String matchTitle, String username) {


        matchToplay = matchTitle;



        return getMatchCartridge(username);
    }

    public static File getFile(String matchTitle, String username) {

        matchToplay = matchTitle;
        System.out.println("Match to play...:" + matchToplay + "\n");

        return getFile(username);
    }

    public static Object[] showDetails(CartridgeFile cf) {


        Object[] cartridgeDetails = new Object[6];


        cartridgeDetails[0] = cf.name;
        cartridgeDetails[1] = cf.description;
        cartridgeDetails[2] = cf.latitude;
        cartridgeDetails[3] = cf.longitude;
        cartridgeDetails[4] = cf.version;
        try {
            
            cartridgeDetails[5] = cf.getSavegame().exists();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cartridgeDetails;

    }

    public static byte[] refreshIcon() {
//		model.clear();
        File currentDirectory = new File(Constants.CARTRIDGE_BASE_DIR);


        byte[] returnMessage = null;

        for (File file : currentDirectory.listFiles()) {
            try {
                if (!file.isFile() || !file.getName().endsWith(".gwc")) {
                    continue;
                }
                SeekableFile sf = new J2SESeekableFile(new RandomAccessFile(file, "r"));
                String path = file.getPath();
                String savefile = path.substring(0, path.length() - 4) + ".ows";
                FileHandle save = new J2SEFileHandle(new File(savefile));
                CartridgeFile cf = CartridgeFile.read(sf, save);
//				model.add(new CartridgeListItem(file, cf));

                if (matchToplay == showDetails(cf)[0]) {

                    returnMessage = new byte[cf.getFile(cf.splashId).length];
                    returnMessage = cf.getFile(cf.splashId);

                }

            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

        }

        return returnMessage;
    }

    public static byte[] refreshIconList(String matchTitle) {


        matchToplay = matchTitle;
        return refreshIcon();
    }
    public static String requestLat = "";
    public static String requestLon = "";
    public static String requestRadius = "";

    public static String[] refreshNearestList(String lat, String lon, String radius) {

        requestLat = lat;
        requestLon = lon;
        requestRadius = radius;

        return refreshList();
    }

    public static String[] refreshList() {
//		model.clear();
        File currentDirectory = new File(Constants.CARTRIDGE_BASE_DIR);

        int fileCount = 0;

        System.out.println(Constants.CARTRIDGE_BASE_DIR);

        for (File file : currentDirectory.listFiles()) {


            if (file.getName().endsWith(".gwc")) {
                fileCount++;
                System.out.println(file.getName());
            }




        }




        String[] returnMessage = new String[fileCount];
        int i = 0;
        for (File file : currentDirectory.listFiles()) {
            try {
                if (!file.isFile() || !file.getName().endsWith(".gwc")) {
                    continue;
                }
                SeekableFile sf = new J2SESeekableFile(new RandomAccessFile(file, "r"));
                String path = file.getPath();
                String savefile = path.substring(0, path.length() - 4) + ".ows";
                FileHandle save = new J2SEFileHandle(new File(savefile));
                CartridgeFile cf = CartridgeFile.read(sf, save);


                long lat10e6 = 0;
                long lon10e6 = 0;

               // if (requestLat != null && requestLon != null && !requestLat.equals("") && !requestLat.equals("")) {
                    lat10e6 = (long) (Double.parseDouble(requestLat) * 1000000.0);
                    lon10e6 = (long) (Double.parseDouble(requestLon) * 1000000.0);
                //}

                long matchLat10e6 = (long) (Double.parseDouble(showDetails(cf)[2].toString()) * 1000000.0);
                long matchLon10e6 = (long) (Double.parseDouble(showDetails(cf)[3].toString()) * 1000000.0);


                if (requestRadius == null || requestLat == null || requestLon == null || requestRadius == "" || requestLat == "" || requestLon == "") {


                    returnMessage[i] = showDetails(cf)[0].toString();
                    i++;


                } else if (getDistance(matchLon10e6, matchLat10e6, lat10e6, lon10e6) <= Long.parseLong(requestRadius)) {

                    returnMessage[i] = showDetails(cf)[0].toString();
                    i++;

                }



            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

        }


        requestLat = "";
        requestLon = "";
        requestRadius = "";

        return returnMessage;
    }
    public static String matchToplay;

    public static CartridgeFile getMatchCartridge(String username) {
//		model.clear();


        File currentDirectory = new File(Constants.CARTRIDGE_BASE_DIR);



        int fileCount = 0;

        for (File file : currentDirectory.listFiles()) {

            fileCount++;

        }

        CartridgeFile cf;
        int i = 0;
        for (File file : currentDirectory.listFiles()) {
            try {
                if (!file.isFile() || !file.getName().endsWith(".gwc")) {
                    continue;
                }
                SeekableFile sf = new J2SESeekableFile(new RandomAccessFile(file, "r"));
                String path = file.getPath();



                String savefile = path.substring(0, path.length() - 4) + username + ".ows";
                FileHandle save = new J2SEFileHandle(new File(savefile));
                cf = CartridgeFile.read(sf, save);
//				model.add(new CartridgeListItem(file, cf));

                if (showDetails(cf)[0].toString().equals(matchToplay)) {

                    return cf;

                }


                i++;

            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

        }

        return null;
    }

    public static File getFile(String username) {
//		model.clear();


        File currentDirectory = new File(Constants.CARTRIDGE_BASE_DIR);

        int fileCount = 0;

        for (File file : currentDirectory.listFiles()) {

            fileCount++;

        }

        int i = 0;
        for (File file : currentDirectory.listFiles()) {
            try {
                if (!file.isFile() || !file.getName().endsWith(".gwc")) {
                    continue;
                }
                SeekableFile sf = new J2SESeekableFile(new RandomAccessFile(file, "r"));
                String path = file.getPath();
                String savefile = path.substring(0, path.length() - 4) + username + ".ows";
                
                FileHandle save = new J2SEFileHandle(new File(savefile));
                CartridgeFile cf = CartridgeFile.read(sf, save);
//				model.add(new CartridgeListItem(file, cf));

                if (showDetails(cf)[0].toString().equals(matchToplay)) {

                    System.out.println("File to play...:" + file.getName() + "\n");
                    System.out.println("File to Save...:" + savefile + "\n");
                    System.out.println("Username...:" + username + "\n");
                    
                    return file;

                }

                i++;

            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

        }


        return null;
    }

    public static String[] matchDetail() {
//		model.clear();


        File currentDirectory = new File(Constants.CARTRIDGE_BASE_DIR);

        int fileCount = 0;

        for (File file : currentDirectory.listFiles()) {

            fileCount++;

        }


        String[] returnMessage = new String[5];
        int i = 0;
        for (File file : currentDirectory.listFiles()) {
            try {
                if (!file.isFile() || !file.getName().endsWith(".gwc")) {
                    continue;
                }
                SeekableFile sf = new J2SESeekableFile(new RandomAccessFile(file, "r"));
                String path = file.getPath();
                String savefile = path.substring(0, path.length() - 4) + ".ows";
                FileHandle save = new J2SEFileHandle(new File(savefile));
                CartridgeFile cf = CartridgeFile.read(sf, save);
//				model.add(new CartridgeListItem(file, cf));

                if (showDetails(cf)[0].toString().equals(matchToplay)) {

                    returnMessage[0] = showDetails(cf)[0].toString();
                    returnMessage[1] = showDetails(cf)[1].toString();
                    returnMessage[2] = showDetails(cf)[2].toString();
                    returnMessage[3] = showDetails(cf)[3].toString();
                    returnMessage[4] = showDetails(cf)[4].toString();

                }

                i++;

            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

        }


        return returnMessage;
    }

    public static long getDistance(long matchLon10e6, long matchLat10e6, long lat10e6, long lon10e6) {
        return (long) (0.08 * Math.sqrt((double) ((matchLat10e6 - lat10e6) * (matchLat10e6 - lat10e6) + (matchLon10e6 - lon10e6) * (matchLon10e6 - lon10e6))));
    }
}
