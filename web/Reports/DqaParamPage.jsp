<%--
    Document   : EnrollmentRecord
    Created on : Oct 5, 2010, 12:35:14 PM
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
<%@ page language="java" %>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <!--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">-->
        <title>Data quality assessment</title>
        <link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
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
            <html:form action="/dqareport" method="post">
                <html:hidden property="actionName" styleId="actionName"/>
                <!--<div id="dqaDiv" class="paramPage" style="height: 160px; width: 300px; margin-top: 100px;">-->
        <span  class="paramPage" style="height: 160px; width: 400px; ">
        <center>
        <table class="paramPage">
            <tr><td colspan="4" class="title" align="center">Data quality assessment </td></tr>
            <tr><td>&nbsp; </td><td> </td></tr>
            <tr><td class="orglabel">State </td><td>
                    <html:select property="dqa_state" styleId="dqa_state" style="width: 290px;" onchange="setActionName('lga'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="stateListForReports">
                            <logic:iterate id="stateObj" name="stateListForReports">
                                <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select> </td><td> </td><td> </td></tr>
            <tr><td class="orglabel" align="left">LGA </td><td ><html:select property="lga" styleId="lga" style="width: 290px;" onchange="setActionName('cbo'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="dqaLgaList">
                        <logic:iterate id="lgaObj" name="dqaLgaList">
                        <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                        </logic:iterate>
                        </logic:present>
                        </html:select> </td><td> </td><td> </td></tr>
            <tr><td class="orglabel" align="left">CBO </td><td >
                    <html:select property="cbo" styleId="cbo" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                        <logic:present name="dqaOrgList">
                    <logic:iterate id="cboObj" name="dqaOrgList">
                      <html:option value="${cboObj.orgCode}">${cboObj.orgName}</html:option>
                        </logic:iterate>
                    </logic:present>
                    </html:select> </td><td> </td><td> </td></tr>
            <tr><td class="orglabel" align="left">Partner </td><td colspan="3">
                    <html:select property="partnerCode" styleId="partnerCode" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                        <logic:present name="partnerList">
                            <logic:iterate id="partner" name="partnerList">
                                    <html:option value="${partner.partnerCode}">${partner.partnerName}</html:option>
                            </logic:iterate>
                    </logic:present>
                    </html:select> </td>
            </tr>
                 <tr><td>&nbsp; </td><td> </td><td> </td><td> </td></tr>
                 <tr><td colspan="4" align="center"><html:submit property="dqa_Btn" value="Submit" onclick="setActionName('dqareport')" style="width: 70px;" /></td></tr>
        </table>
        </center>
        </span>
    <!--</div>-->

            </html:form>
        </center>
    </body>
</html>
