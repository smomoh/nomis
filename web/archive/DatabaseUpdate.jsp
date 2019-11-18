<%-- 
    Document   : DatabaseUpdate
    Created on : Aug 1, 2012, 12:08:48 PM
    Author     : smomoh
--%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Database update</title>
    </head>
    <body>
        <br/><br/><br/>
        <html:errors/>
        <html:form action="/databaseupdate">
            <html:hidden property="actionName"/>
            <table>
                <tr><td><html:submit value="Run dababase update" style="margin-left: 100px;"/></td></tr>
            </table>
        </html:form>
    </body>
</html>
