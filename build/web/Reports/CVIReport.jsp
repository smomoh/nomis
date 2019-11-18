<%-- 
    Document   : CVIReport
    Created on : May 7, 2012, 11:18:36 AM
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
        <title>Child Vulnerability Index report</title>
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
<body>
<div style=" font-family: arial" align="center">Child Vulnerability Pre Enrolment Register </div>
<table border=0>
    <tr><td style="border: none; text-align: left;">
            <table align="left" border="0" style="border: none;">
                <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${cviParam[3]}   </label></td>
                <td class="orgNamestd"><label class="lab">LGA:</label></td> <td style=" border: none;" width="30%"><label class="orgNames"> ${cviParam[4]} </label></td>
                <td class="orgNamestd"><label class="lab">Organization:</label></td> <td style=" border: none;" width="35%"><label class="orgNames">${cviParam[5]}   </label></td>

                <tr><td class="orgNamestd" style=" font-size: 12px;" colspan="8"> &nbsp;</td> <td style=" border: none;" width="15%"> </td></tr>
                </table>
        </td></tr>

    <tr><td style="border: none; text-align: left;">
                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th>Pre-enrolment No.</th>
                                <th >Surname </th>
                                <th >First name</th>
                                <th >Address</th>
                                <th >Age</th>
                                <th >Sex(M/F)</th>
                                <th >Date of assessment</th>
                                <th >Health </th>
                                <th >Education</th>
                                <th >Shelter</th>
                                <th >Protection</th>
                                <th >Nutrition</th>
                                <th >Economic Strengthening</th>
                                <th >Vulnerability score</th>
                                <th >Vulnerability status</th>
                            </tr>
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <logic:iterate id="cviData" name="cviRecords">

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

                                    <td width="3%"><%= ++row %></td>
                                    <td width="20%">
                                       ${cviData[0].preEnrollmentNo}
                                    </td>

                                    <td width="10%">
                                        ${cviData[0].surName}
                                    </td>
                                    <td width="10%">
                                        ${cviData[0].firstName}
                                    </td>
                                    
                                    <td width="20%">
                                        ${cviData[0].address}
                                    </td>
                                    <td width="3%">
                                        ${cviData[0].age} ${cviData[0].ageUnit}
                                    </td>
                                    <td width="3%">
                                        ${cviData[0].gender}
                                    </td>
                                    <td width="10%">
                                        ${cviData[0].dateOfAssessment}
                                    </td>
                                    <td width="4%">${cviData[0].health}</td>
                                    <td width="4%">${cviData[0].education}</td>
                                    <td width="4%">${cviData[0].shelter}</td>
                                    <td width="4%">${cviData[0].protection}</td>
                                    <td width="4%">${cviData[0].nutrition}</td>
                                   <td width="4%">${cviData[0].economicStrengthening}</td>
                                   <td width="4%">${cviData[1]}</td>
                                   <td width="4%">${cviData[2]}</td>
                                </tr>
                            </logic:iterate>
                            </table>
        </td></tr></table>
        <!--</div>-->
    </body>


</html>
