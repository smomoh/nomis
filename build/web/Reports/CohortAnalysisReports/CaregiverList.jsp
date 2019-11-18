<%-- 
    Document   : CaregiverList
    Created on : Mar 28, 2016, 12:09:40 PM
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
        <title>List of caregivers</title>
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
    <div align="center">
<div style=" font-family: arial" align="center">${cohortlisttitle} </div>
<table border=0>
    <tr><td style="border: none; text-align: left;">
            <table align="center" border="0" style="border: none;">
                <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${cohortorgparam[0]}</label></td>
                <td class="orgNamestd"><label class="lab">LGA:</label></td> <td style=" border: none;" width="25%"><label class="orgNames"> ${cohortorgparam[1]}</label></td>
                <td class="orgNamestd"><label class="lab">Organization:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${cohortorgparam[2]}</label></td>
                <td class="orgNamestd"><label class="lab"><jsp:include page="/includes/WardName.jsp"/></label></td> <td style=" border: none;" width="25%"><label class="orgNames">${cohortorgparam[3]}</label></td></tr>

                </table>
        </td></tr>

    <tr><td style="border: none; text-align: left">
                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th>Household unique id</th>
                                <th>Caregiver unique id</th>
                                <th >Caregiver name </th>
                                <th >Address</th>
                                <th >Age at enrollment</th>
                                <%--<th >Current age</th>--%>
                                <th >Sex(M/F)</th>
                                <th >Phone</th>
                                <th >Marital status</th>
                                <th >Occupation</th>
                                <th >HIV status</th>
                                <th >Date of enrollment (mm/dd/yyyy)</th>
                                <th >Date of entry (mm/dd/yyyy)</th>
                            </tr>
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <logic:iterate id="cgiver" name="cgiverList">

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
                                       ${cgiver.hhUniqueId}
                                    </td>
                                    <td width="20%">
                                       ${cgiver.caregiverId}
                                    </td>
                                    <td width="10%">
                                        ${cgiver.caregiverFullName}
                                    </td>
                                    
                                    <td width="20%">
                                        ${cgiver.caregiverAddress}
                                    </td>
                                    <td width="3%">
                                        ${cgiver.caregiverAge}
                                    </td>
                                    <%--<td width="3%">
                                        ${cgiver.currentAge}
                                    </td>--%>
                                    <td width="3%">
                                        ${cgiver.caregiverGender}
                                    </td>
                                    <td width="20%">
                                        ${cgiver.caregiverPhone}
                                    </td>
                                    <td width="3%">
                                        ${cgiver.caregiverMaritalStatus}
                                    </td>
                                    <td width="3%">
                                        ${cgiver.caregiverOccupation}
                                    </td>
                                    <td width="3%">
                                        ${cgiver.cgiverHivStatus}
                                    </td>
                                    <td width="3%">
                                        ${cgiver.dateOfEnrollment}
                                    </td>
                                    
                                    <td width="10%">
                                        ${cgiver.dateModified}
                                    </td>
                                    
                                </tr>
                            </logic:iterate>
                            </table>
        </td></tr></table>
        </div>
    </body>


</html>
