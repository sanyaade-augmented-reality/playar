<%-- 
    Document   : setParameters
    Created on : 21 aout. 2011
    Author     : Carl Tremblay
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="parameters" scope="request" class="org.imajie.server.web.imajiematch.SetParametersJspBean" />

<%
    long radius = Long.parseLong(request.getParameter("radius"));

    
    String practice = request.getParameter("practice");
    String gameType = request.getParameter("gameType");
    
    session.setAttribute("radius", radius);
    session.setAttribute("practice", practice);
    session.setAttribute("gameType", gameType);

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>New parameters saved</title>
        
        
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

                <h1>New Parameters</h1


            </div><!-- /header -->

            <div data-role="content"  data-theme="a">

                <p>
                    <%= parameters.doSaveParameters(request)%>
                </p>
                

            </div><!-- /Content -->

            <div data-role="footer"  data-theme="b">

                <h4>Powered by Playar</h4>        


            </div><!-- /Footer -->




        </div>



    </body>
</html>
