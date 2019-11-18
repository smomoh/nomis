<%-- 
    Document   : OvcCurrentAgeAnalysis
    Created on : Jun 24, 2015, 1:03:08 PM
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
        <title>Current age analysis</title>
    </head>
    <body>
        <html:form action="/ageAnalysisReportAction" method="post">
            <html:hidden property="actionName" styleId="actionName" value="report"/>
        <span>
        <center>
        <table class="paramPage">
            <tr><td colspan="4" class="title" align="center"> Age analysis report  </td></tr>
            <tr><td class="orglabel" align="left">Current age </td>
                <td colspan="2">
                    <html:select property="logic" styleId="logic" style="width: 290px;">
                        <html:option value="<">Less than</html:option>
                        <html:option value="=">Equal to</html:option>
                        <html:option value=">">Greater than</html:option>
                    </html:select> 
                </td>
                <td>
                    <html:select property="currentAge" styleId="currentAge">
                        <logic:iterate name="currentAgeList" id="age">
                            <html:option value="${age}">${age}</html:option>
                        </logic:iterate>
                    </html:select>
                </td>
            </tr>
                               
            <tr><td>&nbsp; </td><td> </td></tr>
            <tr><td colspan="4" align="center"><html:submit value="Submit" style="width: 70px;" /></td></tr>
        </table>
        </center>
        </span>
    <!--</div>-->

            </html:form>
    </body>
</html>
