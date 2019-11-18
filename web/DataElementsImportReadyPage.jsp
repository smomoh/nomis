<%-- 
    Document   : DataElementsImportReadyPage
    Created on : Jul 3, 2013, 9:25:51 AM
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
        <title>Data Elements Import</title>
    </head>
    <html:form action="/databasemgt">
            <html:hidden property="dbActionName" value="importDataElements"/>
            <br/><br/><br/><br/><br/>
                     <center>
                         <table>
                             <tr><td><html:errors/></td></tr>
                             <tr><td>&nbsp;</td></tr>
                         </table>

        <div class="paramPage" style="width: 500px; height: 160px;">
               <table class="paramPage">
                   <tr><td colspan="5">&nbsp;</td></tr>

                    <tr><td colspan="5" align="center"><html:submit  property="deExportReadyBtn" value=" Next >> " style="width: 120px; height: 25px;"/></td></tr>
               </table>

          </html:form>
            </div>
        </center>
</html>
