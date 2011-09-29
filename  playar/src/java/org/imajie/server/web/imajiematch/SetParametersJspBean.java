/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imajie.server.web.imajiematch;


import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author Carl Tremblay
 */
public class SetParametersJspBean implements Serializable {

    private static Logger logger = Logger.getLogger(SetParametersJspBean.class.getName());

    public String doSaveParameters(HttpServletRequest request) {

        long radius = Long.parseLong(request.getParameter("radius"));
        
        String practice = request.getParameter("practice");
        String gameType = request.getParameter("gameType");

       

        String msg = "Your new parameters has been succesfully saved ! <div data-role='fieldcontain'  data-theme='a'><a href='controlpanel.jsp' data-transition='slide' data-role='button'>Mission Control</a> <a href='getmatchs.jsp' data-transition='slide' data-role='button'>Choose Mission</a> <a href='parameters.jsp' data-role='button'>Parameters</a><a href='http://m.layar.com/open/imajiematch' data-transition='slide' data-role='button'>Return to mission</a></div>";




        try {




            /*int rating = Integer.parseInt( request.getParameter("rating"));
            long id = Long.parseLong( request.getParameter("id"));
            
            TagDAO dao = new TagDAO();
            Tag tag = dao.findById( id );
            int ratingSum = tag.getRatingSum();
            int ratingCount = tag.getRatingCount();
            tag.setRatingSum( ratingSum + rating );
            tag.setRatingCount( ratingCount + 1 );
            dao.update(tag);*/
        } catch (NumberFormatException e) {
            msg = "Sorry, You haven't select any star !";
        } catch (Exception e) {
            msg = "Sorry, an error occured. Your rating hasn't been correctly saved !";
            logger.log(Level.SEVERE, "Error saving rating", e);
        }
        return msg;
    }

    public String doFlag(HttpServletRequest request) {
        String msg = "Your notification has been successfully registered";
        try {
            int flag = Integer.parseInt(request.getParameter("flag"));

            long radius = Long.parseLong(request.getParameter("radius"));
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String practice = request.getParameter("practice");
            String gameType = request.getParameter("gameType");


            //TagDAO dao = new TagDAO();
            //Tag tag = dao.findById( id );
            //tag.setInappropriate(flag);
            //dao.update(tag);
        } catch (Exception e) {
            msg = "Sorry, an error occured. Your notification hasn't been correctly saved !";
            logger.log(Level.SEVERE, "Error saving flag", e);

        }
        return msg;
    }
}
