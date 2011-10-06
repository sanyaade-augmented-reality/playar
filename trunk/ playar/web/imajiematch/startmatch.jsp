<%-- 
    Document   : startmatch
    Created on : Aug 26, 2011, 11:25:34 AM
      Author     : Carl Tremblay
--%>




<jsp:useBean id="matches" scope="request" class="org.imajie.server.web.imajiematch.matchsServers.GetMatchsJspBean" />

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%
    //String tagId = request.getParameter("id");
    //String thumbnailId = request.getParameter("id_thumbnail");
    String userId = request.getParameter("userId");
    String username = "";
    String radius = "";
    String practice = "";
    String gameType = "";
    String match = "";


    if (session.getAttribute("username") != null) {

        username = session.getAttribute("username").toString();

    }
//session.setAttribute("password", password);
    if (session.getAttribute("radius") != null) {

        radius = session.getAttribute("radius").toString();

    }

    if (session.getAttribute("practice") != null) {

        practice = session.getAttribute("practice").toString();

    }
    if (session.getAttribute("gameType") != null) {

        gameType = session.getAttribute("gameType").toString();

    }
    
    if (session.getAttribute("match") != null) {

        match = session.getAttribute("match").toString();

    }



%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
        <title>Mission Details</title>

         <!--      <link rel="stylesheet" href="http://code.jquery.com/mobile/1.0a1/jquery.mobile-1.0a1.min.css" />
       
              <script src="http://code.jquery.com/jquery-1.4.3.min.js"></script>
       
               <script src="http://code.jquery.com/mobile/1.0a1/jquery.mobile-1.0a1.min.js"></script>-->


        <!----><link rel="stylesheet" href="js/jquery.mobile-1.0b2/jquery.mobile-1.0b2.min.css" />
        <script src="js/jquery/jquery-1.6.2.min.js"></script>

        <script src="js/jquery.mobile-1.0b2/jquery.mobile-1.0b2.min.js"></script>

        
    </head>
    <body>
        
        
        <div data-role="page" style="min-height:100%" data-theme='a'>
            <div data-role="header"  data-theme="b">

                <h1>Mission Details</h1>


            </div><!-- /header -->

            <div data-role="content"  data-theme="a" align="center">        
                
              
      
                    
                    <%= matches.getMatchDetails(request)%>
                    
                    

            </div><!-- /Content -->

            <div data-role="footer"  data-theme="b">

                <h4>&copy; imajie.tv 2011</h4>        


            </div><!-- /Footer -->




        </div> 


    </body>
</html>
