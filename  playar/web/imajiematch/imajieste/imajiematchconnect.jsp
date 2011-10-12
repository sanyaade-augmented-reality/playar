<%-- 
    Document   : imajiematchconnect
    Created on : 2011-08-31, 20:35:18
    Author     : Carl Tremblay <carl_tremblay at imajie.tv>
--%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Date"%>
<jsp:useBean id="loginImajiematch" scope="request" class="org.imajie.server.web.imajiematch.ImajiematchconnectJspBean" />


<%
 String login = request.getParameter("username");
 String password = request.getParameter("password");
 String loginResult = loginImajiematch.doLogin(request);
 
 String sessionid = null;
   
 
 if ( loginResult.equals("BAD_LOGIN") || !loginResult.equals("OK")) {
         // Invalid login
         session.setAttribute("authorized", "no");
         session.setAttribute("username", "Anonymous");
 } else {
         // Valid login
         
         
         session.setAttribute("authorized", "yes");
         session.setAttribute("username", login);
 }
 
 %>
 
 <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
        <title>Login Confirmation</title>

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
        <div data-role="page" style="min-height:100%"  data-theme="a">
            <div data-role="header" data-theme="b">

<h1>Login Confirmation</h1>

	

                

            </div><!-- /header -->

            <div data-role="content"  data-theme="a">

                <h4 align="center">You logged in with <%= session.getAttribute("username") %></h4>
          <div data-role="fieldcontain"  data-theme="a">
                <a href="../controlpanel.jsp" data-transition="slide" data-role="button">Mission Control</a> 
                <a href="../getmatchs.jsp" data-transition="slide" data-role="button">Choose Mission</a> 
                <a href="../parameters.jsp" data-transition="slide" data-role="button">Parameters</a>
                <a href="http://m.layar.com/open/imajiematch" data-role="button">Return to mission</a>
                
          </div>
            </div><!-- /Content -->

            <div data-role="footer"  data-theme="b">

                <h4>Powered by Playar</h4>        


            </div><!-- /Footer -->




        </div> 
    </body>
</html>

 
