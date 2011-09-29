<%-- 
    Document   : controlpanel
    Created on : Aug 22, 2011, 10:15:59 PM
    Author     : Carl Tremblay
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="locationsCount" scope="request" class="org.imajie.server.web.imajiematch.matchsServers.GetLocationsCountJspBean" />
<jsp:useBean id="inventoryCount" scope="request" class="org.imajie.server.web.imajiematch.matchsServers.GetInventoryCountJspBean" />
<jsp:useBean id="tasksCount" scope="request" class="org.imajie.server.web.imajiematch.matchsServers.GetTasksCountJspBean" />
<jsp:useBean id="youseeCount" scope="request" class="org.imajie.server.web.imajiematch.matchsServers.GetYouseeCountJspBean" />


<%
    //String tagId = request.getParameter("id");
    //String thumbnailId = request.getParameter("id_thumbnail");
    String userId = request.getParameter("userId");
    String username = "";
    String radius = "";
    String practice = "";
    String gameType = "";


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



%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
        <title>Mission Control</title>

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

<h1>Mission Control</h1>

	

                

            </div><!-- /header -->

            <div data-role="content">

                <ul data-role="listview" data-theme="a">

                    <li><a href="inventory.jsp" data-transition="slide"><IMG SRC= "images/inventory.png">Inventory (<%= inventoryCount.getCount(request)%>)</a></li>
                    <li><a href="tasks.jsp" data-transition="slide"><IMG SRC= "images/tasks.png">Tasks (<%= tasksCount.getCount(request)%>)</a></li>
                    <li><a href="locations.jsp" data-transition="slide"><IMG SRC= "images/locations.png">Locations (<%= locationsCount.getCount(request)%>)</a></li>
                    <li><a href="yousee.jsp" data-transition="slide"><IMG SRC= "images/yousee.png">You See (<%= youseeCount.getCount(request)%>)</a></li>
                    <li><a href="parameters.jsp" data-transition="slide"><IMG SRC= "images/cartridge.png">Parameters</a></li>


                </ul>


            </div><!-- /Content -->

            <div data-role="footer"  data-theme="b">

                <h4>&copy; imajie.tv 2011</h4>        


            </div><!-- /Footer -->




        </div> 
    </body>
</html>
