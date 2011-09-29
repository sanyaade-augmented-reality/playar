<%-- 
    Document   : rated
    Created on : 17 juil. 2010, 01:49:19
    Author     : pierre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thanks for rating this tag!</title>
        <link href="css/client.css" type="text/css" rel="stylesheet"/>
        
         <!--      <link rel="stylesheet" href="http://code.jquery.com/mobile/1.0a1/jquery.mobile-1.0a1.min.css" />
       
              <script src="http://code.jquery.com/jquery-1.4.3.min.js"></script>
       
               <script src="http://code.jquery.com/mobile/1.0a1/jquery.mobile-1.0a1.min.js"></script>-->


        <!----><link rel="stylesheet" href="js/jquery.mobile-1.0b2/jquery.mobile-1.0b2.min.css" />
        <script src="js/jquery/jquery-1.6.2.min.js"></script>

        <script src="js/jquery.mobile-1.0b2/jquery.mobile-1.0b2.min.js"></script>

    </head>
    <body>
        
        <div data-role="page">
            <div data-role="header" data-position="inline" data-theme="b">
<a href="index.html" data-icon="delete">Cancel</a>
<h1>Rating</h1>

	<a href="index.html" data-icon="check">Save</a>

                

            </div><!-- /header -->

            <div data-role="content" data-theme="a">
        
        
       
            <h1>Thanks for rating this Match!</h1>
            <p>
                <%= rating.doRating(request) %>
            </p>
            <p>
                Press the Back button to go back to your Match.
            </p>
       
            
             </div>
    </body>
</html>
