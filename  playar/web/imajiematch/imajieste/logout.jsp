<%-- 
    Document   : logout.jsp
    Created on : Sep 2, 2011, 9:27:31 AM
    Author     : Carl Tremblay <carl_tremblay at imajie.tv>
--%>

<%@page contentType="text/html" pageEncoding="windows-1250"%>
<!DOCTYPE html>
<% session.invalidate(); %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
        <title>You have logout</title>
        
        <link rel="stylesheet" href="js/jquery.mobile-1.0b2/jquery.mobile-1.0b2.min.css" />
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
        <div data-role="page"  style="min-height:100%">
            <div data-role="header"  data-theme="b">

<h1>You have logout</h1>


	

                

            </div><!-- /header -->

            <div data-role="content"  style="min-height:100%" data-theme="a">

                <div data-role="fieldcontain"  data-theme="a">
                    <a href="login.jsp" data-role="button">Login</a>
                <a href="../controlpanel.jsp" data-role="button">Mission Control</a> 
                <a href="../getmatchs.jsp" data-role="button">Choose Mission</a> 
                <a href="../parameters.jsp" data-role="button">Parameters</a>
                <a href="http://m.layar.com/open/imajiematch" data-role="button">Return to mission</a>
                
          </div>


            </div><!-- /Content -->

            <div data-role="footer"  data-theme="b">

                <h4>&copy; imajie.tv 2011</h4>        


            </div><!-- /Footer -->




        </div> 
    </body>
</html>
