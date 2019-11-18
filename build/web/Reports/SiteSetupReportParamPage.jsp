<%-- 
    Document   : SiteSetupReportParamPage
    Created on : Oct 18, 2012, 4:17:24 PM
    Author     : smomoh
--%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
        <title>Site setup List</title>
        <script language="javascript">
            function setActionName(val)
            {
                document.getElementById("actionName").value=val
            }

        </script>
    </head>
    <body>
        <br/><br/>
        <center>
            <html:form action="/sitesetupreport" method="post">
                <html:hidden property="actionName" styleId="actionName"/>

        <span>
        <center>
        <table class="paramPage">
            <tr><td colspan="4" class="title" align="center">Site setup list</td></tr>
            <tr><td class="title" align="left">State </td><td colspan="3">
                    <html:select property="state" styleId="state" style="width: 290px;" onchange="setActionName('lga'); forms[0].submit()"><html:option value="All">All</html:option>
                        <logic:iterate id="stateObj" name="siteSetupStateList">
                        <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                        </logic:iterate>
                    </html:select> </td></tr>
            <tr><td class="title" align="left">LGA </td><td colspan="3"><html:select property="lga" styleId="lga" style="width: 290px;">
                        <html:option value="All">All</html:option>
                        <logic:iterate id="lgaObj" name="siteSetupLgaList">
                        <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                        </logic:iterate>
                        </html:select> </td></tr>
            <tr><td colspan="3" align="center"><html:submit property="orgBtn" value="Submit" onclick="setActionName('setupList')" style="width: 70px; margin-left: 70px;" /></td></tr>
        </table>
        </center>
        </span>
    <!--</div>-->

            </html:form>
        </center>
    </body>
</html>
