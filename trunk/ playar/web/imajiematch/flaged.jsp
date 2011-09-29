<%-- 
    Document   : flaged
    Created on : 17 juil. 2010, 02:26:18
    Author     : pierre
--%>

<jsp:useBean id="flag" scope="request" class="org.imajie.server.web.RatingJspBean" />

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thanks for helping us!</title>
         <!--      <link rel="stylesheet" href="http://code.jquery.com/mobile/1.0a1/jquery.mobile-1.0a1.min.css" />

       <script src="http://code.jquery.com/jquery-1.4.3.min.js"></script>

        <script src="http://code.jquery.com/mobile/1.0a1/jquery.mobile-1.0a1.min.js"></script>-->


        <!----><link rel="stylesheet" href="js/jquery.mobile-1.0b2/jquery.mobile-1.0b2.min.css" />
        <script src="js/jquery/jquery-1.6.2.min.js"></script>

        <script src="js/jquery.mobile-1.0b2/jquery.mobile-1.0b2.min.js"></script>

        <link href="css/client.css" type="text/css" rel="stylesheet"/>
    </head>
    <body>
       <div class="box">
            <h1>Thanks for helping us!</h1>
            <p>
                <%= flag.doFlag(request) %>
            </p>
            <p>
                Press the Back button to go back to Layar.
            </p>
        </div>
    </body>
</html>
