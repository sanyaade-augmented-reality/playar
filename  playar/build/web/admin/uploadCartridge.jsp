<%-- 
    Document   : uploadCartridge
    Created on : Sep 8, 2011, 9:32:55 PM
    Author     : Carl Tremblay <carl_tremblay at imajie.tv>
--%>

<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>  


<%     BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();%>  

<html>     
    <head>     
        <title>Upload Cartridge</title>   
    
    </head>   
    
    <body>       
        
        
        <form action="<%= blobstoreService.createUploadUrl("/upload")%>" method="post" enctype="multipart/form-data">       
            
            <input type="text" name="foo">          
            
            <input type="file" name="myFile">       
            
            <input type="submit" value="Submit">    
        
        </form>  
    
    </body> 

</html> 