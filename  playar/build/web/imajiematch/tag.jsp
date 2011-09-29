<%--
    Document   : tag.jsp
    Created on : 16 juil. 2010, 15:24:00
    Author     : pierre
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ImajieMatch</title>
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
        <div class="box">
            <form name="formRating" action="rated.jsp" >
           
                <br/>
                <h1>Rate this Match</h1>
                <br/>
                <div align="center">
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
