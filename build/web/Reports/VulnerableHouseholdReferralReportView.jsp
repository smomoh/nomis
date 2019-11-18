<%-- 
    Document   : VulnerableHouseholdReferralReportView
    Created on : Apr 3, 2014, 10:07:17 PM
    Author     : Siaka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>


<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>Referral List</title>
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
    border: none; text-align: left; width: 40px;
}
</style>

    </head>

    <div align="center">

        <body>
<div style=" font-family: arial" align="center">List of OVC and caregivers referred for servicees</div>
<div style=" font-family: arial" align="center">Period:${referralPeriod} </div>
<table border=0>
    <tr><td style="border: none; text-align: left;">
            <table align="left" border="0" style="border: none;">
                <%--<tr> <td colspan="8" >Period:${referralPeriod} </td></tr>--%>
                <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td style=" border: none;" width="20%"><label class="orgNames">${referralParams[8]}</label></td>
                <td class="orgNamestd"><label class="lab">LGA:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${referralParams[9]}</label></td>
                <td class="orgNamestd"><label class="lab">CBO:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${referralParams[10]}</label></td>
                <td class="orgNamestd"><label class="lab"><jsp:include page="../includes/WardName.jsp" /></label></td> <td style=" border: none;" width="25%"><label class="orgNames">${referralParams[10]}</label></td>
                <td class="orgNamestd"><label class="lab">Partner:</label></td> <td style=" border: none;" width="30%"><label class="orgNames">${referralParams[11]}</label></td></tr>
                </table>
        </td></tr>
    <tr><td style="border: none; text-align: left;">
                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo.</th>
                                <th >Unique ID No.</th>
                                <th >Surname</th>
                                <th >Other Names</th>
                                <th >Age</th>
                                <th >Gender</th>
                                <th >Date of referral</th>
                               
                                <th >Psychosocial</th>
                                <th >Nutritional</th>
                                <th >Health</th>
                                <th >Educational</th>
                                <th >Protection</th>
                                <th >Shelter</th>
                                <th >Economic Strengthening</th>
                                <th >Organization Household/Child is referred</th>
                                <th >Type</th>
                                <th >Referral Complete</th>
                            </tr>
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <!?? iterate over the results of the query ??>
                            <logic:iterate id="referral" name="referralList">

                                <%
                                            cnt++;
                                            if (cnt % 2 != 0) {
                                %>
                                <tr align="left" bgcolor="#FFFFFF">
                                    <%}
                                            else {
                                    %>
                                <tr align="left" bgcolor="#D7E5F2">
                                    <%            }
                                    %>

                                    <%--<tr align="left" bgcolor="">--%>

                                    <td width="1%"><%= ++row %></td>
                                    <td width="3%"><bean:write name="referral" property="ovcId" /></td>
                                    <td width="4%"><bean:write name="referral" property="surname" /> </td>
                                    <td width="4%"><bean:write name="referral" property="firstName" /> </td>
                                    <td width="4%"><bean:write name="referral" property="age" /> </td>
                                    <td width="3%"><bean:write name="referral" property="gender" /> </td>
                                    <td width="4%"><bean:write name="referral" property="dateOfReferral"/></td>
                                    
                                    <td width="4%"><bean:write name="referral" property="psychoServices" /></td>
                                    <td width="4%"><bean:write name="referral" property="nutritionalServices" /></td>
                                    <td width="4%"><bean:write name="referral" property="healthServices" /></td>
                                    <td width="3%"><bean:write name="referral" property="educationalServices" /></td>
                                    <td width="4%"><bean:write name="referral" property="protectionServices" /></td>
                                    <td width="4%"><bean:write name="referral" property="shelterServices" /></td>
                                    <td width="4%"><bean:write name="referral" property="economicServices" /></td>
                                    <td width="4%"><bean:write name="referral" property="nameOfOrganizationChildIsreferred" /></td>
                                    <td width="4%"><bean:write name="referral" property="type" /></td>
                                    <td width="4%"><bean:write name="referral" property="referralCompleted" /></td>
                                </tr>
                            </logic:iterate>
                                    <%--<logic:iterate name="hhrList" id="hhr">
                                        <tr>
                                        <td width="1%"><%= ++row %></td>
                                    <td width="3%"><bean:write name="hhr" property="hhUniqueId" /></td>
                                    <td width="4%"><bean:write name="hhr" property="surname" /> </td>
                                    <td width="4%"><bean:write name="hhr" property="firstName" /> </td>
                                    <td width="4%"><bean:write name="hhr" property="age" /> </td>
                                    <td width="3%"><bean:write name="hhr" property="gender" /> </td>
                                    <td width="4%"><bean:write name="hhr" property="dateOfReferral"/></td>
                                    
                                    <td width="4%"><bean:write name="hhr" property="psychosocialServices" /></td>
                                    <td width="4%"><bean:write name="hhr" property="nutritionalServices" /></td>
                                    <td width="4%"><bean:write name="hhr" property="healthServices" /></td>
                                    <td width="3%"><bean:write name="hhr" property="educationalServices" /></td>
                                    <td width="4%"><bean:write name="hhr" property="protectionServices" /></td>
                                    <td width="4%"><bean:write name="hhr" property="shelterServices" /></td>
                                    <td width="4%"><bean:write name="hhr" property="economicServices" /></td>
                                    <td width="4%"><bean:write name="hhr" property="nameOfOrganizationChildIsReferred" /></td>
                                    <td width="4%"><bean:write name="hhr" property="type" /></td>
                                    <td width="4%"><bean:write name="hhr" property="referralCompleted" /></td>
                                </tr>
                            </logic:iterate>--%>
                            </table>
        </td></tr></table>

    </body>

    </div>

</html>

