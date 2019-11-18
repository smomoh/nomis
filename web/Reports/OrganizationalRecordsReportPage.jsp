<%-- 
    Document   : OrganizationalRecordsReportPage
    Created on : Oct 18, 2012, 1:49:31 PM
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
        <title>List of organizations</title>
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
        <center>
        <table border=0>
            <tr><td align="center" style=" border:none; font-size: 16px; font-weight: bold"><label> List of organizations</label> </td></tr>
            <tr><td align="left" style="border:none; font-size: 16px; font-weight: bold"><label style=" margin-left: 7px;"> Number of organizations: ${noOfOrganizations}</label> </td></tr>
    <tr><td style="border: none; text-align: left;">
                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th>State</th>
                                <th >Assigned LGAs </th>
                                <th >Organization code</th>
                                <th >Organization Name</th>
                                <th >Dhis id</th>
                                <th >Address</th>
                                <th >First Contact</th>
                                <th >Second Contact</th>
                                
                                <th >Services</th>

                            </tr>
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <logic:iterate id="organization" name="orgRecordsList">

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
                                    <td width="10%">
                                       ${organization.state}
                                    </td>

                                    <td width="10%">
                                        ${organization.lga}
                                    </td>
                                    <td width="15%">
                                        ${organization.orgCode}
                                    </td>
                                    <td width="15%">
                                        ${organization.orgName}
                                    </td>
                                    <td width="7%">
                                        ${organization.contactName3}
                                    </td>
                                    <td width="15%">
                                        ${organization.address}
                                    </td>
                                    <td width="7%">
                                        ${organization.contactName1}, ${organization.contactPhone1},${organization.contactEmail1}
                                    </td>
                                    <td width="7%">
                                        ${organization.contactName2}, ${organization.contactPhone2},${organization.contactEmail2}
                                    </td>
                                    
                                    
                                    <td width="25%">
                                        ${organization.services}
                                    </td>

                                </tr>
                            </logic:iterate>

                            </table>
        </td></tr></table></center>
    </body>
    </body>
</html>
