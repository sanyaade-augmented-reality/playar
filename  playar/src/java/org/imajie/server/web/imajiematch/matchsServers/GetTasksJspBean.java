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

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Carl Tremblay <carl_tremblay at imajie.tv>
 */
public class GetTasksJspBean implements Serializable {
    
    private static Logger logger = Logger.getLogger(GetTasksJspBean.class.getName());
    
    public String getTasks(HttpServletRequest request) {
        
        String msg = "";
        int count = 0;
        
        HttpSession session = request.getSession(true);
        
        String gameStarted = "none";
        
        if (session.getAttribute("gameStarted") != null) {
            
            gameStarted = session.getAttribute("gameStarted").toString();
            
        }
        if (!gameStarted.equals("none")) {
            
            
            
            RefreshMatchsJspBean.refresh(gameStarted, session.getAttribute("username").toString(), request);
           
            
            
            
        }
        
        if (session.getAttribute("tasksCount") != null) {
            if (!session.getAttribute("tasksCount").toString().equals("")) {
                
                count = (int) Long.parseLong(session.getAttribute("tasksCount").toString());
                
                msg = msg + "<ul data-role='listview' data-theme='a'>";
                
                String list = (String) session.getAttribute("tasksList");
                String delimiter = "\\|!\\|";
                String[] temp = list.split(delimiter);
                for (int i = 0; i < temp.length; i++) {
                    if (i == 0) {
                    } else {

                        //content = name + "||" + description + "||" + altitude + "||" + latitude + "||" + longitude;

                        String title = "";
                        String description = "";
                        //String taskAltitude = "";
                        //String taskLatitude = "";
                        //String taskLongitude = "";

                        String task = temp[i];
                        String delimiter2 = "\\|\\|";
                        String[] temp2 = task.split(delimiter2);
                        
                        
                        
                        for (int ii = 0; ii < temp2.length; ii++) {
                            if (ii == 0) {
                                title = temp2[ii];
                            }
                            if (ii == 1) {
                                description = temp2[ii];
                            }
                            
                            
                        }
                        
                        
                        
                        msg = msg + "<li><IMG SRC= 'images/task-pending.png'>" + title + description + "</a></li>";
                        
                        
                    }
                    
                }
                
                
                
                msg = msg + "</ul></br><div data-role='fieldcontain'  data-theme='a'><a href='controlpanel.jsp' data-transition='slide' data-role='button'>Mission Control</a> <a href='parameters.jsp' data-role='button'>Parameters</a><a href='http://m.layar.com/open/imajiematch' data-transition='slide' data-role='button'>Return to mission</a></div>";
                
                
                if (msg == "") {
                    
                    msg = "You got no task to do</br><div data-role='fieldcontain'  data-theme='a'><a href='controlpanel.jsp' data-transition='slide' data-role='button'>Mission Control</a> <a href='parameters.jsp' data-role='button'>Parameters</a><a href='http://m.layar.com/open/imajiematch' data-transition='slide' data-role='button'>Return to mission</a></div>";
                    
                }
                
            }
        } else {
            
            msg = "You got no task to do</br><div data-role='fieldcontain'  data-theme='a'><a href='controlpanel.jsp' data-transition='slide' data-role='button'>Mission Control</a> <a href='parameters.jsp' data-role='button'>Parameters</a><a href='http://m.layar.com/open/imajiematch' data-transition='slide' data-role='button'>Return to mission</a></div>";
            
        }
        
        return msg;
        
    }
    
    public static String arrayToString(String[] a, String separator) {
        if (a == null || separator == null) {
            return null;
        }
        StringBuffer result = new StringBuffer();
        if (a.length > 0) {
            result.append(a[0]);
            for (int i = 1; i < a.length; i++) {
                result.append(separator);
                result.append(a[i]);
            }
        }
        return result.toString();
    }
}
