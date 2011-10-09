<%-- 
    Document   : start
    Created on : Aug 28, 2011, 10:19:16 PM
    Author     : Carl Tremblay <carl_tremblay at imajie.tv>
--%>




<%@page import="org.imajie.server.web.imajiematch.matchsServers.RefreshMatchsJspBean"%>
<jsp:useBean id="match" scope="request" class="org.imajie.server.web.imajiematch.matchsServers.StartMatchsJspBean" />

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
    String authorized = "";
    String matchStarted = "null";
    String matchToStart = request.getParameter("match");

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

    if (session.getAttribute("authorized") != null) {

        authorized = session.getAttribute("authorized").toString();

    }
    if (session.getAttribute("gameStarted") != null) {

        matchStarted = session.getAttribute("gameStarted").toString();

    }

    if (authorized.equals("yes") && matchStarted.equals("null")) {


        match.start(request);

    } else {

        String notAutorized = "";



    }

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
        <title>Start</title>

        <!--      <link rel="stylesheet" href="http://code.jquery.com/mobile/1.0a1/jquery.mobile-1.0a1.min.css" />
        
               <script src="http://code.jquery.com/jquery-1.4.3.min.js"></script>
        
                <script src="http://code.jquery.com/mobile/1.0a1/jquery.mobile-1.0a1.min.js"></script>-->


        <!----><link rel="stylesheet" href="js/jquery.mobile-1.0b2/jquery.mobile-1.0b2.min.css" />
        <script src="js/jquery/jquery-1.6.2.min.js"></script>

        <script src="js/jquery.mobile-1.0b2/jquery.mobile-1.0b2.min.js"></script>



        <script type="text/javascript" language="javascript">
            $(document).ready(function(){
        
                var    dheight = $('html').height(),
                cbody = $('#contentbody').height(),
                wheight = $(window).height(),
                cheight = wheight - dheight + cbody;
            
                if (wheight > dheight){
                    $('#contentbody').height(cheight);
                }
        
                $(window).resize(function(){
                    wheight = $(window).height();
                    noscroll();
                    changepush();
                });

                function noscroll(){
                    if (wheight > dheight) {
                        $('html').addClass('noscroll');
                    }

                    else if (wheight <= dheight) {
                        $('html').removeClass('noscroll');
                    }
            
                    else {}

                }

                function changepush(){
                    if (wheight > dheight) {
                        $('#contentbody').height(wheight-dheight+cbody);
                    }
            
                }

            });

        </script>
        
        
  
                
                
                 </head>

                <%= session.getAttribute("DIALOG").toString()%>


</html>