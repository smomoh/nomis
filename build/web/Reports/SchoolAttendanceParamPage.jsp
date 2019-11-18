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
<%@page import="com.fhi.kidmap.business.*;" %>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>School attendance</title>
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
            <html:form action="/schoolAttendance" method="post">
                <html:hidden property="actionName" styleId="actionName"/>
                <!--<div class="paramPage" id="schoolAttendanceDiv" style="height: 180px; width: 380px; margin-top: 100px;">-->
        <span style="height: 170px; width: 380px;">
        <center>
        <table class="paramPage">
            <tr><td colspan="4" class="title" align="center">School attendance  </td></tr>
            <tr><td colspan="4">&nbsp; </td></tr>
            <tr><td class="orglabel" align="left">State </td><td colspan="3"><html:select property="schAttend_state" styleId="schAttend_state" style="width: 290px;" onchange="setActionName('lga'); forms[0].submit()">
                        <html:option value="All">Select state.... </html:option>
                        <logic:present name="stateListForReports">
                            <logic:iterate id="stateObj" name="stateListForReports">
                                <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select> </td></tr>
            <tr><td class="orglabel" align="left">LGA </td><td colspan="3">
                    <html:select property="schAttend_lga" styleId="schAttend_lga" style="width: 290px;" onchange="setActionName('cbo');forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="lgaList">
                            <logic:iterate id="lgaObj" name="lgaList">
                                <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                            </logic:iterate>
                        </logic:present>
                        </html:select> </td>
            </tr>
            
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
            <tr><td class="orglabel" align="left">School type</td><td colspan="3"><html:select property="school_type" styleId="school_type" style="width: 290px;">
                        <html:option value="All">All</html:option>
                        <%--<html:option value="Public">Public</html:option>
                        <html:option value="Private">Private</html:option>--%>
                        </html:select> </td>
            </tr>

                 <tr><td>&nbsp; </td><td> </td></tr>
                 <tr><td colspan="4" align="center"><html:submit property="sch_Btn" value="Submit" onclick="setActionName('schoolAttendance')" style="width: 70px;" /></td></tr>
        </table>
        </center>
        </span>
    <!--</div>-->

            </html:form>
        </center>
    </body>
</html>
