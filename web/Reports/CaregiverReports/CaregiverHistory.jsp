<%-- 
    Document   : CaregiverHistory
    Created on : Mar 10, 2018, 7:10:59 AM
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
        <title>Caregiver history</title>
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
<div style=" font-family: arial" align="center">Caregiver history </div>
<logic:present name="cgiverHistoryList">
    <table width="80%" border=0 style="border-collapse: collapse; border: #0066FF 1px solid; width: 500px;">
        <tr><td width="80%">
                <table border=0 style="border-collapse: collapse; border: #0066FF 1px solid; width: 500px;">
    <tr style="background-color: lightblue"><td colspan="18">Baseline bio data</td></tr>
    <tr><td>State</td><td>LGA</td><td>CBO</td><td>Ward/Community</td><td>Household Id</td><td>Caregiver Id</td><td>Name</td><td>Sex</td><td>Current age</td><td>Phone</td>
        <td>Current HIV status</td><td>Enrolled on treatment</td>
        <td>Facility enrolled</td><td>Occupation</td><td>Marital status</td><td>Current status</td><td>Date of status</td></tr>
        <logic:iterate collection="${cgiverHistoryList[0]}" id="cgiver">
            <tr>
                <td>${cgiver.hhe.stateName}</td><td>${cgiver.hhe.lgaName}</td><td>${cgiver.hhe.orgName}</td><td>${cgiver.hhe.communityName}</td>
        <td>${cgiver.hhUniqueId}</td> <td>${cgiver.caregiverId}</td><td>${cgiver.caregiverFullName}</td><td>${cgiver.caregiverGender}</td><td>${cgiver.caregiverAge}</td><td>${cgiver.caregiverPhone}</td>
        <td>${cgiver.activeHivStatus.hivStatus}</td><td>${cgiver.enrolledOnTreatment}</td><td>${cgiver.facilityId}</td><td>${cgiver.caregiverOccupation}</td>
                <td>${cgiver.caregiverMaritalStatus}</td>
                <td>${cgiver.reasonForWithdrawal}</td><td>${cgiver.dateOfWithdrawal}</td>
            </tr> 
        </logic:iterate>
    
</table>
            </td></tr>
<tr><td>
<table border=0 style="border-collapse: collapse; border: #0066FF 1px solid;">
    <tr style="background-color: lightblue"><td colspan="9">Household service provided to Caregiver</td></tr>
    <tr><td>Date of service</td><td>Psychosocial</td><td>Nutritional</td><td>Health</td><td>Education</td><td>Protection</td><td>Shelter and Care</td><td>Economic strengthening</td><td>Volunteer name</td></tr>
<logic:iterate collection="${cgiverHistoryList[1]}" id="hhs">
    <tr>
        <td>${hhs.serviceDate}</td><td>${hhs.psychosocialSupportServices}</td><td>${hhs.nutritionalServices}</td>
        <td>${hhs.healthServices}</td><td>${hhs.educationalServices}</td><td>${hhs.protectionServices}</td>
        <td>${hhs.shelterAndCareServices}</td><td>${hhs.economicStrengtheningServices}</td><td>${hhs.volunteerName}</td>
    </tr> 
</logic:iterate>
</table>
    </td></tr>

</table>  
</logic:present>
        </div>
    </body>
</html>
