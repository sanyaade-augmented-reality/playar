<%-- 
    Document   : xmlrcpclient
    Created on : Sep 1, 2011, 1:09:54 PM
    Author     : Carl Tremblay <carl_tremblay at imajie.tv>
--%>

<%@ page import="java.net.*"%> 
<%@ page import="java.util.*"%> 
<%@ page import="org.apache.xmlrpc.client.*"%> 
<%@ page import="org.apache.xmlrpc.client.XmlRpcClientConfigImpl.*"%> 
<%@ page import="org.apache.xmlrpc.XmlRpcException"%> 

<% 

String address = "http://www.imajie.tv/index.php/component/imajiematchconnect?task=imajiematch.connect&format=xmlrpc"; 
String message= "test"; 

Properties systemSettings = System.getProperties(); 
systemSettings.put("http.proxyHost","localhost") ; //your proxy server 
systemSettings.put("http.proxyPort", "8080") ; 

XmlRpcClientConfigImpl rpcconfig = new XmlRpcClientConfigImpl(); 
rpcconfig.setServerURL(new URL(address)); 
XmlRpcClient client = new XmlRpcClient();       
client.setConfig(rpcconfig);     

Vector params = new Vector ();   
params.addElement( "admin" ); 
params.addElement( "doudou--07" ); 
    
String methodName = "imajiematch.connect";   

    
    try { 
            //  client.execute(methodName, params); 
            Object result=client.execute(methodName, params);   
           if (result instanceof Boolean) { 
           Boolean b=(Boolean)result; 
           System.out.println (result.toString()); 
           message=result.toString(); 
           } 
           /* for (int i=0; i < result.size(); i++) { 
                System.out.println (result.elementAt(i)); 
            }*/ 

        } catch (XmlRpcException e) { 
            //XmlRpcException ex =(XmlRpcException)e; 
            System.err.println ("client.excecute failed - " +e.toString()); 
            message=e.toString()+" "+ e.getMessage()+ "Code="+e.code; 
        } 
        catch(Exception ex) {                 
               message += ex.getMessage(); 
                } 
        

%> 
<html> 
<body bgcolor="white"> 


<font size=4> 
<br> 
Message:<br> 
<%= message%> 
</font> 

</body> 
</html> 

