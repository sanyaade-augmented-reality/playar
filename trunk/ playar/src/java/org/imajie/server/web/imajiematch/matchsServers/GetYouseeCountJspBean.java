/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imajie.server.web.imajiematch.matchsServers;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Carl Tremblay
 */
public class GetYouseeCountJspBean implements Serializable {

    private static Logger logger = Logger.getLogger(GetYouseeCountJspBean.class.getName());

    public String getCount(HttpServletRequest request) {

         String msg ="";
        
        HttpSession session = request.getSession(true);
        
        if (session.getAttribute("youseeCount") != null) {
        
        msg = session.getAttribute("youseeCount").toString();
        
        
        } else {
        
        msg = "0";
        
        }

        return msg;
    }

   
}
