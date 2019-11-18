<%-- 
    Document   : OvcBaselineDetails
    Created on : Feb 4, 2011, 8:56:44 PM
    Author     : COMPAQ USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <center>
        <div style=" border: 1px black solid; width: 700px;">
            <table style=" width: 680px; text-align: left;">
            <tr><td>State</td><td><label> state name</label> </td><td>LGA </td><td><label> lga name</label>  </td> </tr>
            <tr><td>CBO</td><td><label> cbo name</label>  </td><td><jsp:include page="includes/WardName.jsp"/> </td><td><label> ward name</label>  </td> </tr>
            <fieldset>
                <legend>Personal information </legend>
                <tr><td>Ovc unique id</td><td><label> id</label> </td><td>Date of enrollment</td><td><label> date of enrollment</label>  </td> </tr>
            <tr><td>First name</td><td><label> first name</label>  </td><td>Other names </td><td><label> other names</label>  </td> </tr>
            </fieldset>
        </table>
        </div>
        </center>
    </body>
</html>
