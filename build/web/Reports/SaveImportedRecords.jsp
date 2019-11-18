<%-- 
    Document   : saveImportedRecords
    Created on : Feb 7, 2011, 10:01:51 PM
    Author     : COMPAQ USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.fhi.kidmap.business.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Database import</title>
    </head>
    <body>
        <%
            MergeDatabase mdb=new MergeDatabase(" ");
            mdb.saveImportedRecords(session);
        %>
        <br/><br/><br/><br/>
        <center>
            <div style=" background-color: teal; border: 1px black solid; width: 700px;">
                <table style=" width: 700px; background-color: teal; text-align: center; color: white; height: 200px;" >
                    <tr><td> &nbsp;<h1>All records saved successfully</h1></td></tr>
                </table>
            </div>
        </center>
        
    </body>
</html>
