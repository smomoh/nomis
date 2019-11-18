<%-- 
    Document   : CVIReportParamPage
    Created on : May 7, 2012, 3:11:26 PM
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
        <title>Child Vulnerability Pre Enrolment Register </title>
        <style type="text/css">
<!--
a {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #333333;
	font-weight: bold;
}
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
	color: #000000;
}
a:hover {
	text-decoration: underline;
	color: #0075B6;
}
a:active {
	text-decoration: none;
	color: #000000;
}
.title
{
  color: #FFFFFF;
  font-weight: bold;
}
.orglabel
{
    color: #FFFFFF;
}

-->
</style>
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
            <html:form action="/cvireport" method="post">
                <html:hidden property="actionName" styleId="actionName"/>

                <!--<div styleId="sumstatDivPerMth" class="paramPage" style="height: 160px; width: 400px; margin-top: 100px;">-->
        <span>
        <center>
        <table class="paramPage">
            <tr><td colspan="4" class="title" align="center">Select CVI report criteria</td></tr>
            <tr><td class="orglabel" align="left">State </td><td colspan="3">
                    <html:select property="state" styleId="state" style="width: 290px;" onchange="setActionName('lga'); forms[0].submit()"><html:option value="All">All</html:option>
                        <logic:iterate id="stateObj" name="cviStateList">
                        <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                        </logic:iterate>
                    </html:select> </td></tr>
            <tr><td class="orglabel" align="left">LGA </td><td colspan="3"><html:select property="lga" styleId="lga" style="width: 290px;" onchange="setActionName('cbo'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:iterate id="lgaObj" name="cviLgaList">
                        <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                        </logic:iterate>
                        </html:select> </td></tr>
            <tr><td class="orglabel" align="left">CBO </td><td colspan="3">
                    <html:select property="organization" styleId="organization" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                    <logic:iterate id="cboObj" name="cviOrgList">
                      <html:option value="${cboObj.orgCode}">${cboObj.orgName}</html:option>
                        </logic:iterate>
                    </html:select> </td></tr>

             <tr><td colspan="3" align="center"><html:submit property="cviReportListBtn" value="Submit" onclick="setActionName('cviReportList')" style="width: 70px; margin-left: 70px;" /></td></tr>
        </table>
        </center>
        </span>
    <!--</div>-->

            </html:form>
        </center>
    </body>
</html>
