<%-- 
    Document   : ImportFilesSelectionPage
    Created on : May 4, 2012, 10:47:05 AM
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
        <title>Data import</title>
        <link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <html:form action="/databasemgt">
            <%--<html:hidden property="restoreDb"/>--%>
            <br/><br/><br/>
                     <center>
                         <table>
                             <tr><td><html:errors/></td></tr>
                             <tr><td>&nbsp;</td></tr>
                         </table>

        <!--<div class="paramPage" style="width: 600px; height: 140px;">-->
               <table class="paramPage">
                   <tr><td>&nbsp; </td>  <td colspan="4"><html:hidden property="dbActionName" value="dbimport"/></td></tr>
                   <tr><td colspan=""><label style="color: white">Select file to import</label></td></tr>
                   <tr><td colspan="5">&nbsp;</td></tr>
                   
                   <tr><td colspan="5" style="color: white"> &nbsp;
                           <html:select property="importFileName" styleId="importFileName" style="margin-left: 30px; margin-right: 30px" >
                           <html:option value=""> </html:option>
                           <logic:iterate id="fileList" name="availableFileList">
                        <html:option value="${fileList[0]}">${fileList[1]}</html:option>
                        </logic:iterate>
                    </html:select>

                       </td>
                       </tr>
                   <tr><td colspan="5">&nbsp;</td></tr>
                   <tr><td colspan="5" align="center"><html:submit  property="restore" value="Submit" style="width: 120px; height: 25px;"/></td></tr>
               </table>

          </html:form>
         <!--</div>-->
        </center>
    </body>
</html>
