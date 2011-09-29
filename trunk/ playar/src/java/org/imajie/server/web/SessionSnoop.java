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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 *
 * @author Carl Tremblay <carl_tremblay at imajie.tv>
 */
public class SessionSnoop extends HttpServlet
 	  {
   
    @Override
    public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
  	      {
      res.setContentType("text/plain");
        PrintWriter out=res.getWriter();
        HttpSession session=req.getSession();
       Integer count=(Integer)session.getAttribute("snoop.count");
       if(count==null)
         count=new Integer(1);
        else
        count=new Integer(count.intValue()+1);
              session.setAttribute("snoop.count",count);
            
           out.println("<html><head><title>SessionSnoop</title></head>");
             out.println("<body><h1>Session Snoop</h1>");
           // Display the hit count for this page
            out.println("You've visited this page " + count +
            ((count.intValue() == 1) ? " time." : " times."));
            
           out.println("<p>");
            out.println("<h3>here is your saved session data </h3>");
//           Enumeration enum=session.getAttributeNames();
//             while(enum.hasMoreElements())
// 	              {
//               String name=(String)enum.nextElement();
//                 out.println(name+": "+session.getAttribute(name)+"<br>");
//           }
            
     out.println("<H3>Here are some vital stats on your session:</H3>");
    out.println("Session id: " + session.getId() + "<BR>");
      out.println("New session: " + session.isNew() + "<BR>");
     out.println("Creation time: " + session.getCreationTime());
      out.println("<I>(" + new Date(session.getCreationTime()) + ")</I><BR>");
      out.println("Last access time: " + session.getLastAccessedTime());
      out.println("<I>(" + new Date(session.getLastAccessedTime()) +
                ")</I><BR>");

    out.println("Requested session ID from cookie: " +
                 req.isRequestedSessionIdFromCookie() + "<BR>");

      out.println("Requested session ID valid: " +
                   req.isRequestedSessionIdValid() + "<BR>");
 
    out.println("<H3>Here are all the current session IDs");
     out.println("and the times they've hit this page:</H3>");
      HttpSessionContext context = session.getSessionContext();
    Enumeration ids = context.getIds();
      while (ids.hasMoreElements()) 
 	      {
      String id = (String)ids.nextElement();
        out.println(id + ": ");
      HttpSession foreignSession = context.getSession(id);
       Integer foreignCount =
         (Integer)foreignSession.getValue("snoop.count");
        if (foreignCount == null)
        out.println(0);
       else
        out.println(foreignCount.toString());
      out.println("<BR>");
    }
 
     out.println("<H3>Test URL Rewriting</H3>");
     out.println("Click <A HREF=\"" +
               res.encodeUrl(req.getRequestURI()) + "\">here</A>");
     out.println("to test that session tracking works via URL");
    out.println("rewriting even when cookies aren't supported.");

    out.println("</BODY></HTML>");
   }
}


