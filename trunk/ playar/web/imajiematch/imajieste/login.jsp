<%-- 
    Document   : login
    Created on : 2011-08-31, 20:14:44
    Author     : Carl Tremblay <carl_tremblay at imajie.tv>
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%
    //String tagId = request.getParameter("id");
    //String thumbnailId = request.getParameter("id_thumbnail");
    String userId = request.getParameter("userId");
    String username = "";
    String radius = "10000";
    String practice = "true";
    String gameType = "stand-alone";


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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log on Imajieste !</title>



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

         
         
        

        <div data-role="page" style="min-height:100%" id="callAjaxPage"  data-theme="a">

            <div data-role="header"  data-theme="b">

                <h1>Log on Imajieste !</h1>

            </div> <!-- /header --> 

            <div data-role="content"  data-theme="a">

                <form method="POST" action="imajiematchconnect.jsp" >
                    <div data-role="fieldcontain"   data-theme="a">

                        <label  data-theme="a" for="username">Username</label>

                        <input type="text" name="username" id="username" value="<%= username%>"  />

                    </div>

                    <div data-role="fieldcontain"  data-theme="a">

                        <label  data-theme="a" for="password">Password</label>

                        <input type="password" name="password" id="password" value=""  />

                    </div>        
                    
                    <input type="submit"  id="login_submit" data-theme="a" value="Login"/>
                    </form>
                <h3 id="notification"></h3>

            </div><!-- /content -->

            <div data-role="footer"  data-theme="b">

                <h4>&copy; imajie.tv 2011</h4>


            </div> <!-- /footer -->          	

        </div>
 <!--<script> 
              $(function() {    
                  $("#login_submit").click(function() {   
                      var theName = $.trim($("#username").val()); 
                      var password = $.trim($("#password").val());
                      if(theName.length > 0)           
                      {              
                          $.ajax({      
                              type: "POST",       
                              url: "http://www.imajie.tv/index.php/component/imajiematchconnect?task=imajiematch.connect&format=xmlrpc",   
                              //url: "http://localhost/joomla/index.php/component/imajiematchconnect?task=imajiematch.connect&format=xmlrpc",   
                              data: ({username: theName,password: password}),               
                              cache: false,                
                              dataType: "utf8",             
                              success: onSuccess            
                          });          
                      }           
                  });          
                  
                $("#notification").ajaxError(function(event, request, settings, exception) {   
                    
                  $("#notification").html("Error Calling: " + settings.url + "<br />HTPP Code: " + request.status);   
              });             
            function onSuccess(data)      
            {           
                $("#notification").html("Result: " + data);   
            }       
        });    
          
        </script>-->
    </body>
  


</html>
