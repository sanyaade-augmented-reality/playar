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

<head>
   <%= match.refresh(gameStarted, session.getAttribute("username").toString(), request) %>
<script language='javascript'>
 
 
        window.location.replace("layar://imajiematch");
 
   </script>
</head>


<body>
    <h1>Hello World!</h1>
</body>
