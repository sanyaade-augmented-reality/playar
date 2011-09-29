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
package org.imajie.server.web.imajiematch.matchsServers.mixare;



import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.imajie.server.web.Constants;
import org.imajie.server.web.Utils;

/**
 *
 * @author Pierre Levy
 */
public class MixareServlet extends HttpServlet
{
    private static Logger logger = Logger.getLogger("MixareServlet");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        double latitude = Utils.getDouble(request, "latitude", 48.8);
        double longitude = Utils.getDouble(request, "longitude", 2.3);
        double radius = Utils.getDouble(request, "radius", 20);
        int max = Utils.getInt(request, "max", 20);

        Writer out = response.getWriter();

        response.setContentType(Constants.CONTENT_TYPE_JSON);

        JSONObject data = new JSONObject();
        JSONArray results = new JSONArray();
        int count = 0;
//        for (Tag tag : TagService.getNearestTags(latitude, longitude, max))
//        {
//            count++;
//            JSONObject poi = new JSONObject();
//            poi.accumulate("id", "" + tag.getId());
//            poi.accumulate("lat", "" + tag.getLat());
//            poi.accumulate("lng", "" + tag.getLon());
//            poi.accumulate("elevation", "0");
//            poi.accumulate("title", StringEscapeUtils.escapeJavaScript(tag.getName()));
//            poi.accumulate("has_detail_page", "1");
//            String webpageUrl = Constants.URL_SERVER + "/imajiematch/mixare.jsp?id=" + tag.getId();
//            poi.accumulate("webpage", URLEncoder.encode( webpageUrl , "UTF-8"));
//
//            results.add(poi);
//        }
        data.accumulate("status", "OK");
        data.accumulate("num_results", count);
        data.accumulate("results", results);

        out.write(data.toString(2));
        out.close();
    }
}
