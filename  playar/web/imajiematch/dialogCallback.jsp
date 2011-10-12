<%-- 
    Document   : dialogCallback
    Created on : Oct 5, 2011, 10:58:19 PM
    Author     : Carl Tremblay <carl_tremblay at imajie.tv>
--%>
<jsp:useBean id="match" scope="request" class="org.imajie.server.web.imajiematch.matchsServers.RefreshMatchsJspBean" />

<%
    //String tagId = request.getParameter("id");
    //String thumbnailId = request.getParameter("id_thumbnail");
    String userId = request.getParameter("userId");
    String username = "";
    String radius = "10000";
    String practice = "true";
    String gameType = "stand-alone";
    String gameStarted = "none";
    String showControlPanel = request.getParameter("showControlPanel");

    if (request.getParameter("showControlPanel") != null) {
        if (request.getParameter("showControlPanel").contains("true")) {
            session.setAttribute("showControlPanel", "true");
        }
    } else {

        session.setAttribute("showControlPanel", "false");

    }

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
    if (session.getAttribute("gameStarted") != null) {

        gameStarted = session.getAttribute("gameStarted").toString();

    }

    if (!gameStarted.equals("none")) {

        session.setAttribute("sendCallback", true);


    }
%> 
<html>
    <head>
        <%= match.refresh(gameStarted, session.getAttribute("username").toString(), request)%>

        <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
        <title>Wait</title>
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
        <div data-role="page"  style="min-height:100%" data-theme='a'>
            <div data-role="header"  data-theme="b">

                <h1>Waiting ...</h1>


            </div><!-- /header -->

            <div data-role="content"  data-theme="a">

                Mission request being transfered ...


            </div><!-- /Content -->

            <div data-role="footer"  data-theme="b">

                <h4>Powered by Playar</h4>        


            </div><!-- /Footer -->




        </div> 
    </body>




    <script language='javascript'>
 
 
        window.location.replace("layar://imajiematch");
 
    </script>
</html>