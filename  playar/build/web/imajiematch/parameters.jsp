<%-- 
    Document   : parameters
    Created on : Aug 21, 2011, 12:00:37 PM
    Author     : Carl Tremblay
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
        <title>Parameters</title>



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

        <script type="text/javascript">

            $(document).ready(function() {

                $('#gameType').hide();

            });

        </script>  
        <script>
            
            
            
            
        </script>
        
    </head>
    <body>         

        <div data-role="page" style="min-height:100%">










            <%


                if (session.getAttribute("authorized") != null) {



                    String authorized = (String) session.getAttribute("authorized");



                    if (authorized.equals("no")) {
            %>
            <div data-role="header" data-position="inline"  data-theme="e">
                <a href="imajieste/login.jsp" data-icon="save">Login</a>
                <h1>Parameters</h1>
                <h6>You must login to get full featured mission. Click on Imajie to create an account</h6><a href="http://imajie.tv/index.php?option=com_comprofiler&task=registers"   >Imajie</a></label></br>

                <%     } else {

                %>
                <div data-role="header" data-position="inline"  data-theme="b">

                    <h1>Parameters</h1>
                    <h6>Welcome Agent <%= session.getAttribute("username")%></h6>
                    <a href="imajieste/logout.jsp" data-transition="slide" data-icon="delete">Logout</a>
                    <%

                        }

                    } else {

                    %>
                    <div data-role="header" data-position="inline"  data-theme="e">
                        <a href="imajieste/login.jsp" data-transition="slide" data-icon="save">Login</a>
                        <h1>Parameters</h1>
                         <h6>You must login to get full featured mission. Click on Imajie to create an account</h6><a href="http://imajie.tv/index.php?option=com_comprofiler&task=registers" >Imajie</a></label></br>

                        <%     }
                        %>
                    </div> <!-- /header --> 
                    <div data-role="content"  data-theme="a">
                        <form action="setParameters.jsp" data-transition="slide" method="post">

                            <div data-role="fieldcontain"  data-theme="a">

                                <label data-theme="a" for="radius">Radar Range (in meter)</label>

                                <input type="range" data-theme="a" name="radius" id="radius" value="<%= Long.parseLong(radius)%>" min="10" max="10000"  />



                            </div>  

                            <div data-role="fieldcontain"  data-theme="a">

                                <fieldset  data-role="controlgroup" data-type="horizontal">

                                    <input type="radio" name="practice" id="practice" value="true" checked="checked" />

                                    <label for="practice">Practice</label>

                                    <input type="radio" name="practice" id="fullFeaturedGame" value="false"  />

                                    <label for="fullFeaturedGame">Full Game</label>

                                </fieldset>

                            </div>
                                
                                <div data-role="fieldcontain"  data-theme="a">

                                <fieldset  data-role="controlgroup" data-type="horizontal">

                                    <input type="radio" name="gameType" id="gameType" value="true" checked="checked" />

                                    <label for="gameType">Stand-Alone</label>

                                    <input type="radio" name="gameType" id="Team-Play" value="false"  />

                                    <label for="Team-Play">Team-Play</label>

                                </fieldset>

                            </div>



                            <input type="submit" data-role="button" data-theme="a" value="Save Parameters"/>
                        </form>
                    </div><!-- /content -->

                    <div data-role="footer"  data-theme="b">

                        <h4>&copy; imajie.tv 2011</h4>


                    </div> <!-- /footer -->          	

                </div>

               
                </body>


                </html>
