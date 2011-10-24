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
package org.imajie.server.web.imajiematch;

/**
 *
 * @author Carl Tremblay <carl_tremblay at imajie.tv>
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import org.imajie.server.web.Constants;

public class ImajiematchconnectJspBean implements Serializable {

    
    public String doLogin(HttpServletRequest request) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        
        String result = "";

        try {
            // Connect to the server
            URL u = new URL(Constants.IMAJIE_SERVER);
            URLConnection uc = u.openConnection();
            HttpURLConnection connection = (HttpURLConnection) uc;
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            OutputStream out = connection.getOutputStream();
            Writer wout = new OutputStreamWriter(out);

            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

            wout.write(data);
//      // Write the request
//      wout.write("<?xml version=\"1.0\"?>\r\n");
//      wout.write("<methodCall>\r\n");
//      wout.write(
//       "  <methodName>imajiematch.connect</methodName>\r\n");
//      wout.write("  <params>\r\n");
//      wout.write("    <param>\r\n");
//      wout.write("      <value><string>" + username
//       + "</int></value>\r\n");
//      wout.write("    </param>\r\n");
//      wout.write("    <param>\r\n");
//      wout.write("      <value><string>" + password
//       + "</int></value>\r\n");
//      wout.write("    </param>\r\n");
//      wout.write("  </params>\r\n");
//      wout.write("</methodCall>\r\n");

            wout.flush();
            wout.close();

            // Read the response
            InputStream in = connection.getInputStream();
            //result = readFibonacciXMLRPCResponse(in);
            //System.out.println(result);

            StringBuffer sb = new StringBuffer();
            Reader reader = new InputStreamReader(in, "UTF-8");
            int c;
            while ((c = in.read()) != -1) {
                sb.append((char) c);
            }

            String document = sb.toString();
            String startTag = "<string>";
            String endTag = "</string>";
            int start = document.indexOf(startTag) + startTag.length();
            int end = document.indexOf(endTag);
            result = document.substring(start, end);

            in.close();
            connection.disconnect();
        } catch (IOException e) {
            System.err.println(e);
        }

        return result;
    }

}