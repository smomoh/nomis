<%-- 
    Document   : DbMgtSelectionPage
    Created on : Aug 16, 2011, 11:37:25 AM
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
        <title>Data Transfer</title>
        <link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
        <!--<script language="javascript">
            function setActionName(val)
            {
                document.getElementById("actionName").value=val
            }

        </script>-->
    </head>
    <body>
        <html:form action="/databasemgt">
            <html:hidden property="actionName" value="selectionPage"/>
            <br/><br/><br/><br/><br/>
                     <center>
                         <table>
                             <tr><td><html:errors/></td></tr>
                             <tr><td>&nbsp;</td></tr>
                         </table>
                         
        <div class="paramPage" style="width: 500px; height: 160px;">
               <table class="paramPage">
                   <tr><td colspan="5">&nbsp;</td></tr>
                   <tr><td><html:radio property="dbActionName" value="dbexportSelected"/><label style=" color: white">Export database</label>  </td>
                       <td colspan="2" ><html:radio property="dbActionName" value="fileUploadSelected"/><label style=" color: white">Load file</label> </td>
                       <td colspan="2"><html:radio property="dbActionName" value="dbImportSelected"/><label style=" color: white;">Import database</label> </td></tr>
                       <td><html:radio property="dbActionName" value="dataElementImportSelected"/><label style=" color: white;">Import data elements</label> </td>
                    <tr><td colspan="5">&nbsp;</td></tr>
                    <tr><td colspan="5" align="center"><html:submit  property="restore" value=" Next >> " style="width: 120px; height: 25px;"/></td></tr>
               </table>

          </html:form>
         </div>
        </center>
    </body>
</html>
