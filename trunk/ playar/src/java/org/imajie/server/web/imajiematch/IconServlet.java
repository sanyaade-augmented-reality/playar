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
package org.imajie.server.web.imajiematch;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.imajie.server.web.Constants;

/**
 *
 * @author Carl Tremblay
 */
public class IconServlet extends HttpServlet {

    private static final long EXPIRES = 36000000L * 24L; // one day

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sId = req.getParameter("matchtitle");
        String icon = req.getParameter("icon");

        String imageId = req.getParameter("icon");

        String cartridgedir = req.getParameter("matchtitle").replaceAll(",", "");

        
        if (!imageId.contains(".mp4") && !imageId.contains(".mp3")) {
        
        
        File file = new File(Constants.CARTRIDGE_BASE_DIR + cartridgedir + "/" + req.getParameter("icon"));

        if (!file.exists()) {
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            byte[] bytes = new byte[bis.available()];
            if (req.getParameter("icon").contains(".jpg")) {
            resp.setContentType("image/jpeg");
            
            } else {
                
                resp.setContentType("image/png");
                
            }
            OutputStream os = resp.getOutputStream();
            bis.read(bytes);
            os.write(bytes);
        } catch (IOException e) {
        }
    } else if (imageId.contains(".mp4")) {
    
    
        File file = new File(Constants.CARTRIDGE_BASE_DIR + cartridgedir + "/" + req.getParameter("icon"));

        if (!file.exists()) {
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            byte[] bytes = new byte[bis.available()];
            resp.setContentType("video/mp4");
            OutputStream os = resp.getOutputStream();
            bis.read(bytes);
            os.write(bytes);
        } catch (IOException e) {
        } 
    
    }
      else if (imageId.contains(".mp3")) {
    
    
        File file = new File(Constants.CARTRIDGE_BASE_DIR + cartridgedir + "/" + req.getParameter("icon"));

        if (!file.exists()) {
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            byte[] bytes = new byte[bis.available()];
            resp.setContentType("audio/mp3");
            OutputStream os = resp.getOutputStream();
            bis.read(bytes);
            os.write(bytes);
        } catch (IOException e) {
        } 
    
    }  

    }

    public static Object[] refreshIconList() {

        Object[] returnMessage = null;
        return returnMessage;
    }
}
