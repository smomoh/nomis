<%-- 
    Document   : HVIServiceReport
    Created on : Jan 31, 2014, 9:31:10 PM
    Author     : Siaka
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
        <title>Household service report</title>
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
<div style=" font-family: arial" align="center">Household Service Register </div>
<table border=0>
    <tr><td style="border: none; text-align: left;">
            <table align="left" border="0" style="border: none;">
                <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${hhsParam[12]}   </label></td>
                <td class="orgNamestd"><label class="lab">LGA:</label></td> <td style=" border: none;" width="25%"><label class="orgNames"> ${hhsParam[4]} </label></td>
                <td class="orgNamestd"><label class="lab">Organization:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${hhsParam[5]}   </label></td></tr>
                <td class="orgNamestd"><label class="lab"><jsp:include page="../includes/WardName.jsp" /></label></td> <td style=" border: none;" width="25%"><label class="orgNames">${hhsParam[11]}   </label></td>
                <tr><td class="orgNamestd" style=" font-size: 14px" colspan="4" align="center" width="50%"> ${hhsAgeParam}</td><td class="orgNamestd" style=" font-size: 14px" colspan="4" align="center" width="50%"> ${hhsReportPartner}</td> </tr>
            </table>
        </td></tr>

    <tr><td style="border: none; text-align: left">
                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th>State</th>
                                <th>LGA</th>
                                <th >CBO</th>
                                <th >Ward/Community</th>
                                
                                <th>HH unique Id</th>
                                <th>Caregiver Id</th>
                                <th >Care giver name</th>
                                <th >Address</th>
                                <th >Date of enrollment</th>
                                <th >Age</th>
                                <th >Sex</th>
                                <th >HIV status</th>
                                <th >Enrolled in Care?</th>
                                <th >Enrolled on ART?</th>
                                <th >Withdrawn from program</th>
                                <th >Date of withdrawal</th>
                                <th >Service date (mm/dd/yyyy)</th>
                                <th >Health</th>
                                <th >Nutrition</th>
                                <th >Psychosocial support </th>
                                <th >Protection services</th>
                                <th >Shelter and care</th>
                                <th >Educational services</th>
                                <th >Economic strengthening</th>
                                <th >Name of volunteer</th>
                                <th >Recorded by</th>
                                <th >Last modified</th>
                            </tr>
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <logic:iterate id="hhsData" name="hhsRecords">

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
                                    <td width="5%">
                                       ${hhsData.cgiver.hhe.stateName}
                                    </td>
                                    <td width="5%">
                                       ${hhsData.cgiver.hhe.lgaName}
                                    </td>
                                    <td width="5%">
                                       ${hhsData.cgiver.hhe.orgName}
                                    </td>
                                    <td width="7%">
                                       ${hhsData.cgiver.hhe.communityName}
                                    </td>
                                    <td width="5%">
                                       ${hhsData.cgiver.hhUniqueId}
                                    </td>
                                    <td width="5%">
                                       ${hhsData.caregiverId}
                                    </td>
                                    <td width="7%">
                                       ${hhsData.cgiver.caregiverFirstname} ${hhsData.cgiver.caregiverLastName}
                                    </td>
                                    <td width="7%">
                                        ${hhsData.cgiver.caregiverAddress}
                                    </td>
                                    <td width="7%">
                                        ${hhsData.cgiver.dateOfEnrollment}
                                    </td>
                                    <td width="7%">
                                        ${hhsData.cgiver.caregiverAge}
                                    </td>
                                    <td width="7%">
                                       ${hhsData.cgiver.caregiverGender}
                                    </td>
                                    <td width="7%">
                                       ${hhsData.cgiver.activeHivStatus.hivStatus}
                                    </td>
                                    <td width="7%">
                                       ${hhsData.cgiver.enrolledInCare}
                                    </td>
                                    <td width="7%">
                                       ${hhsData.cgiver.enrolledOnART}
                                    </td>
                                    <td width="7%">
                                       ${hhsData.cgiver.reasonForWithdrawal}
                                    </td>
                                    <td width="7%">
                                       ${hhsData.cgiver.dateOfWithdrawal}
                                    </td>
                                    <td width="4%">
                                        ${hhsData.serviceDate}
                                    </td>
                                    <td width="10%">${hhsData.healthServicesByName}</td>
                                    <td width="10%">${hhsData.nutritionalServicesByName}</td>
                                    <td width="10%">${hhsData.psychosocialServicesByName}</td>
                                    <td width="10%">${hhsData.protectionServicesByName}</td>
                                   <td width="10%">${hhsData.shelterAndCareServicesByName}</td>
                                   <td width="10%">${hhsData.educationalServicesByName}</td>
                                   <td width="10%">${hhsData.economicStrenghteningServicesByName}</td>
                                   <td width="5%">
                                        ${hhsData.volunteerName}
                                    </td>
                                    <td width="4%">${hhsData.recordedBy}</td>
                                   <td width="4%">${hhsData.dateOfEntry}</td>
                            </tr>
                            </logic:iterate>
                            </table>
        </td></tr></table>
        <!--</div>-->
    </body>
</html>
