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
package org.imajie.server.web;

/**
 *
 * @author Carl Tremblay <carl_tremblay at imajie.tv>
 */

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;


public class SessionListener  implements HttpSessionListener {
    
    public void sessionCreated(HttpSessionEvent se)    {    
        
//        HttpSession session = se.getSession();     
//        
//        System.out.print(getTime() + " (session) Created:");      
//        
//        System.out.println("ID=" + session.getId() + " MaxInactiveInterval="
// + session.getMaxInactiveInterval());   
    
    }   
    
    public void sessionDestroyed(HttpSessionEvent se)    {     
        
//        HttpSession session = se.getSession();      
        
//        // session has been invalidated and all session data 
////(except Id)is no longer available       
//        
//        System.out.println(getTime() + " (session) Destroyed:ID=" 
//+ session.getId());    
    
    }    
    
    private String getTime()    
    
    {       
        
        return new Date(System.currentTimeMillis()).toString();  
    
    }
    
    
    
    
}
