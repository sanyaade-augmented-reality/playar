<%-- 
    Document   : rate.jsp
    Created on : 16 juil. 2010, 15:24:00
    Author     : pierre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%
    String tagId = request.getParameter("id");
    String thumbnailId = request.getParameter("id_thumbnail");
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Rate this Match</title>



        <!--      <link rel="stylesheet" href="http://code.jquery.com/mobile/1.0a1/jquery.mobile-1.0a1.min.css" />
       
              <script src="http://code.jquery.com/jquery-1.4.3.min.js"></script>
       
               <script src="http://code.jquery.com/mobile/1.0a1/jquery.mobile-1.0a1.min.js"></script>-->


        <!----><link rel="stylesheet" href="js/jquery.mobile-1.0b2/jquery.mobile-1.0b2.min.css" />
        <script src="js/jquery/jquery-1.6.2.min.js"></script>

        <script src="js/jquery.mobile-1.0b2/jquery.mobile-1.0b2.min.js"></script>

        <script src="js/jquery/rating/jquery.rating.pack.js" type="text/javascript" language="javascript"></script>
        <link href="js/jquery/rating/jquery.rating.css" type="text/css" rel="stylesheet"/>
        <link href="css/client.css" type="text/css" rel="stylesheet"/>

        <script type="text/javascript" language="javascript">
        </script>
    </head>
    <body>

        <div data-role="page">
            <div data-role="header" data-position="inline" data-theme="b">
                <a href="index.html" data-icon="delete">Cancel</a>
                <h1>Rating</h1>

                <a href="index.html" data-icon="check">Save</a>



            </div><!-- /header -->

            <div data-role="content" data-theme="a" align="center">




                <form name="formRating" action="rated.jsp" >
                    <input name="id" type="hidden" value="<%= tagId%>" />
                    <h1>Rate this Match</h1>
                    <img src="/thumbnail?id=<%= thumbnailId%>" alt="thumbnail" />
                    <br/>
                    <br/>
                    <div align="center" >
                        <input name="rating" type="radio" class="star" value="1"/>
                        <input name="rating" type="radio" class="star" value="2"/>
                        <input name="rating" type="radio" class="star" value="3"/>
                        <input name="rating" type="radio" class="star" value="4"/>
                        <input name="rating" type="radio" class="star" value="5"/>
                    </div>

                    <br/>
                    <br/>
                    <input type="submit" value="Send" />
                </form>



            </div>
    </body>
</html>
