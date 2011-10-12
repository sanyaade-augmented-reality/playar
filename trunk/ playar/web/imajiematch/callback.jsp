<%-- 
    Document   : start
    Created on : Aug 28, 2011, 10:19:16 PM
    Author     : Carl Tremblay <carl_tremblay at imajie.tv>
--%>



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

    if (authorized.equals("yes")) {


        //match.start(request);

    } else {

        String notAutorized = "";



    }

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
        <title>Coming Soon</title>

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
    <body>


        <div data-role="page" style="min-height:100%">
            <div data-role="header"  data-theme="b">

                <h1>Coming Soon</h1>


            </div><!-- /header -->

            <div data-role="content"  data-theme="a" >        
                
                
                <div data-role='fieldcontain'  data-theme='a'>
                    <h3  align="center" >This Feature is Coming Soon</h3>
                    <a href="imajieste/login.jsp" data-transition='slide' data-role='button' data-theme="e">Login</a>
                    <a href="http://imajie.tv/index.php?option=com_comprofiler&task=registers"  data-transition='slide' data-role='button' data-theme="b">Create an account</a>
                    <a href='controlpanel.jsp' data-transition='slide' data-role='button'>Mission Control</a>
                    <a href='parameters.jsp' data-role='button'>Parameters</a>
                    <a href='http://m.layar.com/open/imajiematch' data-transition='slide' data-role='button'>Return to Camera</a>
                    
                </div>

               




            </div><!-- /Content -->

            <div data-role="footer"  data-theme="b">

                <h4>Powered by Playar</h4>        


            </div><!-- /Footer -->




        </div> 


    </body>
</html>