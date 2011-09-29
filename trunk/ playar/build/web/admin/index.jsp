<%@page import="org.imajie.server.web.Security" %>
<%@include file="header.jsp" %>

<div class="box">
    <h2>ImajieMatch Server - Administration Home</h2>
<ul>
    <li>
        <a href="listTags.jsp">Matchs list</a>
    </li>
    <li>
        <a href="viewTags.jsp">View matchs on the map</a>
    </li>
    <li>
        <a href="createTag.jsp">Create a match from this web interface</a>
    </li>
    <li>
        <a href="/tags">Get matchs (XML format)</a>
    </li>
    <li>
        <a href="/layar?developerId=<%=Security.LAYAR_DEVELOPER_ID%>">Layar </a>
    </li>
    <li>
        <a href="layarParams.jsp">Layar params</a>
    </li>
    <li>
        <a href="http://publishing.layar.com/publishing/test/imajie" target="Layar">Test at Layar</a>
    </li>
    <li>
        <a href="/arml">ARML (Wikitude)</a>
    </li>
    <li>
        <a href="/junaio/pois/search/?adminKey=<%=Security.ADMIN_KEY%>">Junaio </a>
    </li>
    <li>
        <a href="http://www.junaio.com/publisher/mychannels" target="Junaio">Test at Junaio</a>
    </li>
    <li>
        <a href="/mixare?latitude=45.499782660312334&longitude=-73.56020453948975&altitude=106.0&radius=20.75" target="Mixare">Mixare</a>
    </li>
    <li>
        <a href="kml">KML Export</a>
    </li>
</ul>
</div>

<%@include file="footer.jsp" %>
