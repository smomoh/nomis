<%-- 
    Document   : HouseholdHistory
    Created on : Mar 7, 2018, 5:58:26 PM
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
        <title>Household history</title>
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
<div style=" font-family: arial" align="center">Household history </div>
<logic:present name="householdHistoryList">

<table border=0 style="border-collapse: collapse; border: #0066FF 1px solid;">
    <tr style="background-color: lightblue"><td colspan="15">Baseline bio data</td></tr>
    <tr><td>State</td><td>LGA</td><td>CBO</td><td>Ward/Community</td><td>Unique Id</td><td>Date of Assessment</td><td>Household head name</td><td>Address</td><td>Phone</td><td>Marital status</td><td>Sex</td><td>Occupation</td><td>Number of children in household</td><td>Number of people in household</td><td>Enrollment status</td></tr>
<logic:iterate collection="${householdHistoryList[0]}" id="hhe">
    <tr>
        <td>${hhe.stateName}</td><td>${hhe.lgaName}</td><td>${hhe.orgName}</td><td>${hhe.communityName}</td>
        <td>${hhe.hhUniqueId}</td><td>${hhe.dateOfAssessment}</td><td>${hhe.hhFirstname} ${hhe.hhSurname}</td>
        <td>${hhe.address}</td><td>${hhe.phone}</td><td>${hhe.maritalStatus}</td>
        <td>${hhe.hhGender}</td><td>${hhe.occupation}</td><td>${hhe.noOfChildrenInhh}</td><td>${hhe.noOfPeopleInhh}</td>
        <td>${hhe.reasonForWithdrawal}</td>
    </tr> 
</logic:iterate>
</table>
<table border=0 style="border-collapse: collapse; border: #0066FF 1px solid;">
    <tr style="background-color: lightblue"><td colspan="11">Baseline assessment</td></tr>
    <tr><td>Date of Assessment</td><td>Household headship</td><td>Health</td><td>Education level</td><td>Protection</td><td>Shelter and Housing</td><td>Food security and Nutrition</td><td>Means of livelihood</td><td>Household income</td><td>Total score</td><td>Vulnerability status</td></tr>
<logic:iterate collection="${householdHistoryList[0]}" id="baselineAssessment">
    <tr>
        <td>${hhe.dateOfAssessment}</td><td>${hhe.hhHeadship}</td><td>${hhe.health}</td>
        <td>${hhe.educationLevel}</td><td>${hhe.protection}</td><td>${hhe.shelterAndHousing}</td><td>${hhe.foodSecurityAndNutrition}</td>
        <td>${hhe.meansOfLivelihood}</td><td>${hhe.hhIncome}</td><td>${hhe.baselineAssessmentScore}</td><td> </td>
    </tr> 
</logic:iterate>
</table>
<%--<table border=0 style="border-collapse: collapse; border: #0066FF 1px solid;">
    <tr style="background-color: lightblue"><td colspan="10">Baseline assessment</td></tr>
    <tr><td>Date of Assessment</td><td>Household headship</td><td>Health</td><td>Education level</td><td>Shelter and Housing</td><td>Food security and Nutrition</td><td>Means of livelihood</td><td>Household income</td><td>Total score</td><td>Vulnerability status</td></tr>
<logic:iterate collection="${householdHistoryList[1]}" id="baselineAssessment">
    <tr>
        <td>${baselineAssessment.dateOfAssessment}</td><td>${baselineAssessment.hhHeadship}</td><td>${baselineAssessment.health}</td>
        <td>${baselineAssessment.educationLevel}</td><td>${baselineAssessment.shelterAndHousing}</td><td>${baselineAssessment.foodSecurityAndNutrition}</td>
        <td>${baselineAssessment.meansOfLivelihood}</td><td>${baselineAssessment.hhIncome}</td><td>${baselineAssessment.vulnerabilityScore}</td><td> </td>
    </tr> 
</logic:iterate>
</table>--%>
<table border=0 style="border-collapse: collapse; border: #0066FF 1px solid;">
    <tr style="background-color: lightblue"><td colspan="11">Follow up assessment</td></tr>
    <tr><td>Date of Assessment</td><td>Household headship</td><td>Health</td><td>Education level</td><td>Protection</td><td>Shelter and Housing</td><td>Food security and Nutrition</td><td>Means of livelihood</td><td>Household income</td><td>Total score</td><td>Vulnerability status</td></tr>
<logic:iterate collection="${householdHistoryList[2]}" id="followupAssessment">
    <tr>
        <td>${followupAssessment.dateOfAssessment}</td><td>${followupAssessment.hhHeadship}</td><td>${followupAssessment.health}</td>
        <td>${followupAssessment.educationLevel}</td><td>${followupAssessment.protection}</td><td>${followupAssessment.shelterAndHousing}</td><td>${followupAssessment.foodSecurityAndNutrition}</td>
        <td>${followupAssessment.meansOfLivelihood}</td><td>${followupAssessment.hhIncome}</td><td>${followupAssessment.vulnerabilityScore}</td><td> </td>
    </tr> 
</logic:iterate>
</table>
    
<table border=0 style="border-collapse: collapse; border: #0066FF 1px solid;">
    <tr style="background-color: lightblue"><td colspan="9">Household service provided to Household head</td></tr>
    <tr><td>Date of service</td><td>Psychosocial</td><td>Nutritional</td><td>Health</td><td>Education</td><td>Protection</td><td>Shelter and Care</td><td>Economic strengthening</td><td>Volunteer name</td></tr>
<logic:iterate collection="${householdHistoryList[3]}" id="hhs">
    <tr>
        <td>${hhs.serviceDate}</td><td>${hhs.psychosocialSupportServices}</td><td>${hhs.nutritionalServices}</td>
        <td>${hhs.healthServices}</td><td>${hhs.educationalServices}</td><td>${hhs.protectionServices}</td>
        <td>${hhs.shelterAndCareServices}</td><td>${hhs.economicStrengtheningServices}</td><td>${hhs.volunteerName}</td>
    </tr> 
</logic:iterate>
</table>
    
<table border=0 style="border-collapse: collapse; border: #0066FF 1px solid;">
    <tr style="background-color: lightblue"><td colspan="10">Caregivers in the household</td></tr>
    <tr><td>Caregiver Id</td><td>Caregiver name</td><td>Gender</td><td>Occupation</td><td>Marital status</td><td>Current HIV status</td><td>Enrollment status</td><td>Date of status</td><td>Last modified date</td><td>Caregiver History</td></tr>
        <logic:iterate collection="${householdHistoryList[4]}" id="cgiver">
            <tr>
                <td>${cgiver.caregiverId}</td><td>${cgiver.caregiverFullName}</td><td>${cgiver.caregiverGender}</td>
                <td>${cgiver.caregiverOccupation}</td><td>${cgiver.caregiverMaritalStatus}</td><td>${cgiver.activeHivStatus.hivStatus}</td>
                <td>${cgiver.reasonForWithdrawal}</td><td>${cgiver.dateOfWithdrawal}</td><td>${cgiver.dateModified}</td><td><a href="householdVulnerabilityIndexReportAction.do?id=cgHistory:${cgiver.caregiverId}" target="_blank">Open Caregiver file</a></td>
            </tr> 
        </logic:iterate>
</table>
    
<table border=0 style="border-collapse: collapse; border: #0066FF 1px solid;">
    <tr style="background-color: lightblue"><td colspan="13">OVC in the household</td></tr>
    <tr><td>Ovc unique Id</td><td>Name</td><td>Gender</td><td>Age at baseline</td><td>Current age</td><td>Baseline eligibility status</td><td>Current HIV status</td><td>Enrolled on treatment</td>
        <td>Has birth certificate</td><td>In school</td><td>Caregiver Id</td><td>Enrollment status</td><td>Date of status</td><td>OVC History Details</td></tr>
        <logic:iterate collection="${householdHistoryList[5]}" id="ovc">
            <tr>
                <td>${ovc.ovcId}</td><td>${ovc.fullName}</td><td>${ovc.gender}</td><td>${ovc.age} ${ovc.ageUnit}(s)</td><td>${ovc.currentAge} ${ovc.currentAgeUnit}(s)</td><td>${ovc.eligibility}</td><td>${ovc.activeHivStatus.hivStatus}</td>
                <td>${ovc.enrolledOnTreatment}</td>
                <td>${ovc.birthRegistration.birthRegistrationStatus}</td><td>${ovc.activeSchoolStatus}</td><td>${ovc.caregiverId}</td>
                <td>${ovc.reasonForWithdrawal}</td><td>${ovc.dateOfWithdrawal}</td><td><a href="householdVulnerabilityIndexReportAction.do?id=ovcHistory:${ovc.ovcId}" target="_blank">Open OVC file</a></td>
            </tr> 
        </logic:iterate>
</table>
</logic:present>
        </div>
    </body>


</html>
