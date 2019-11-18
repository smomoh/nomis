<%-- 
    Document   : GraduationCheckListReport
    Created on : Oct 4, 2016, 5:36:00 PM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>Graduation checklist report</title>
        <link href="kidmap.css" rel="stylesheet" type="text/css" />

        <style type="text/css">
            <!--
            #Layer1 {
                position:absolute;
                width:200px;
                height:115px;
                z-index:1;
            }
            -->
        </style>
        <style>
.verticaltext
{
	writing-mode: tb-rl;
	filter: flipV flipH;
	border-right: solid black 1px;
}
.tdLine
{
	border-right: solid black 2px;
}
tr
{
	height:20px;
}
td {
	padding-left: 11px;
	padding-right: 11px;
	border-left: 1px solid black;
	border-bottom: 1px solid black;
	font-size: 11px;
	color: black;
}
th {
	padding-left: 11px;
	padding-right: 11px;
	border-left: 1px solid black;
	border-bottom: 1px solid black;
        border-top: 1px solid black;
	font-size: 11px;
	color: black;
}
table {
	border-collapse: collapse;
	margin: 10px;
}
.lab
{
    font-family:  arial, sans-serif;
    font-size: 14px;
    /*font-weight: bold;*/
    letter-spacing: 3px;
}
.orgNames
{
    font-family:  arial, sans-serif;
    font-size: 12px;
    border: none; text-align: left; width: 40px;
}
.orgNamestd
{
    border: none; text-align: left;
}
</style>

    </head>

    <body>
<div align="center">
<div style=" font-family: arial" align="center">Graduation checklist</div>
<table border=0>
    <tr><td style="border: none; text-align: left;">
            <table align="left" border="0" style="border: none;">
                <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td style=" border: none;" width="20%"><label class="orgNames">${gclorgparams[12]}</label></td>
                <td class="orgNamestd"><label class="lab">LGA:</label></td> <td style=" border: none;" width="22%"><label class="orgNames">${gclorgparams[4]}</label></td>
                <td class="orgNamestd"><label class="lab">CBO:</label></td> <td style=" border: none;" width="35%"><label class="orgNames">${gclorgparams[5]}</label></td>
                <td class="orgNamestd"><label class="lab"><jsp:include page="/includes/WardName.jsp" /></label></td> <td style=" border: none;" width="15%"><label class="orgNames">${gclorgparams[11]}</label></td></tr>
                <tr><td class="orgNamestd"><label class="lab">Partner</label></td> <td style=" border: none;" width="15%" colspan="3"><label class="orgNames">${gclorgparams[13]}</label></td>
                <td class="orgNamestd"><label class="lab">Period</label></td> <td style=" border: none;" width="15%" colspan="3"><label class="orgNames">${gclperiod}</label></td></tr>
               
                </table>
        </td></tr>
    <tr><td style="border: none; text-align: left;">
                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo.</th>
                                <th >Household Unique ID</th>
                                <th >Date of Assessment</th>
                                <th >Surname</th>
                                <th >Other Names</th>
                                <th >Current age</th>
                                <th >Gender</th>
                                <th >address</th>
                                <th >Phone</th>  
                                <th >Health</th>
                                <th >Safety</th> 
                                <th >School</th>
                                <th >Stability</th>
                                <th >Vulnerability</th>
                                <th >graduated</th>
                                <th >score</th>
                                <th >Name of volunteer</th>
                                <th >Recorded by</th>
                            </tr>
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <!?? iterate over the results of the query ??>
                            <logic:iterate id="gcl" name="gclList">

                                <%
                                            cnt++;
                                            if (cnt % 2 != 0) {
                                %>
                                <tr align="left" bgcolor="#FFFFFF">
                                    <%} else {
                                    %>
                                <tr align="left" bgcolor="#D7E5F2">
                                    <%            }
                                    %>

                                    <%--<tr align="left" bgcolor="">--%>

                                    <td width="3%"><%= ++row %></td>
                                    <td width="25%">
                                       <bean:write name="gcl" property="clientId" />
                                    </td>
                                    
                                    <td width="10%">
                                        <bean:write name="gcl" property="dateOfAssessment" />
                                    </td>
                                    <td width="16%">
                                        <bean:write name="gcl" property="surname" />
                                    </td>
                                    <td width="20%">
                                        <bean:write name="gcl" property="firstName" />
                                    </td>
                                    <td width="3%">
                                        <bean:write name="gcl" property="age" />
                                    </td>
                                    <td width="6%"><bean:write name="gcl" property="gender" /></td>
                                    <td width="3%">
                                        <bean:write name="gcl" property="address" />
                                    </td>
                                    <td width="4%"><bean:write name="gcl" property="phone" /></td>
                                    <td width="6%"><bean:write name="gcl" property="health" /></td>
                                    <td width="4%"><bean:write name="gcl" property="safety" /></td>
                                    <td width="6%"><bean:write name="gcl" property="school" /></td>
                                    <td width="6%"><bean:write name="gcl" property="stability" /></td>
                                    <td width="6%"><bean:write name="gcl" property="vulnerability" /></td>
                                    <td width="6%"><bean:write name="gcl" property="graduated" /></td>
                                    <td width="6%"><bean:write name="gcl" property="gclscore" /></td>
                                    <td width="6%"><bean:write name="gcl" property="volunteerId" /></td>
                                    <td width="6%"><bean:write name="gcl" property="recordedby" /></td>
                                </tr>
                            </logic:iterate>
                            </table>
        </td></tr></table>
         </div>
    </body>

    

</html>

