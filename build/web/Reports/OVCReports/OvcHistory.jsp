<%-- 
    Document   : OvcHistory
    Created on : Mar 8, 2018, 10:04:44 PM
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
        <title>OVC history</title>
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
<div style=" font-family: arial" align="center">OVC history </div>
<logic:present name="ovcHistoryList">
    <table width="80%" border=0 style="border-collapse: collapse; border: #0066FF 1px solid; width: 500px;">
        <tr><td width="80%">
                <table border=0 style="border-collapse: collapse; border: #0066FF 1px solid; width: 500px;">
    <tr style="background-color: lightblue"><td colspan="18">Baseline bio data</td></tr>
    <tr><td>State</td><td>LGA</td><td>CBO</td><td>Ward/Community</td><td>Unique Id</td><td>Ovc unique Id</td><td>Name</td><td>Gender</td><td>Age at baseline</td><td>Current age</td><td>Baseline eligibility status</td><td>Current HIV status</td><td>Enrolled on treatment</td>
        <td>Has birth certificate</td><td>In school</td><td>Caregiver Id</td><td>Enrollment status</td><td>Date of status</td></tr>
        <logic:iterate collection="${ovcHistoryList[0]}" id="ovc">
            <tr>
                <td>${ovc.hhe.stateName}</td><td>${ovc.hhe.lgaName}</td><td>${ovc.hhe.orgName}</td><td>${ovc.hhe.communityName}</td>
        <td>${ovc.hhUniqueId}</td><td>${ovc.ovcId}</td><td>${ovc.fullName}</td><td>${ovc.gender}</td><td>${ovc.age} ${ovc.ageUnit}(s)</td><td>${ovc.currentAge} ${ovc.currentAgeUnit}(s)</td><td>${ovc.eligibility}</td><td>${ovc.activeHivStatus.hivStatus}</td>
                <td>${ovc.enrolledOnTreatment}</td>
                <td>${ovc.birthRegistration.birthRegistrationStatus}</td><td>${ovc.activeSchoolStatus}</td><td>${ovc.caregiverId}</td>
                <td>${ovc.reasonForWithdrawal}</td><td>${ovc.dateOfWithdrawal}</td>
            </tr> 
        </logic:iterate>
    
</table>
            </td></tr>
<tr><td>
<table border=0 style="border-collapse: collapse; border: #0066FF 1px solid;">
    <tr style="background-color: lightblue"><td colspan="14">Baseline assessment</td></tr>
    <tr><td>Date of Assessment</td><td>Emotional health</td><td>Social behavior</td><td>Food security</td><td>Nutrition and growth</td><td>Wellness</td><td>Health care services</td>
        <td>Development and performance</td><td>Education and work</td><td>Abuse and exploitation</td><td>Legal protection</td><td>Shelter</td><td>Care</td><td>Total score</td></tr>
<logic:iterate collection="${ovcHistoryList[1]}" id="csi">
    <tr>
        <td>${csi.csiDate}</td><td>${csi.csiFactor1}</td><td>${csi.csiFactor2}</td>
        <td>${csi.csiFactor3}</td><td>${csi.csiFactor4}</td><td>${csi.csiFactor5}</td>
        <td>${csi.csiFactor6}</td><td>${csi.csiFactor7}</td><td>${csi.csiFactor8}</td>
        <td>${csi.csiFactor9}</td><td>${csi.csiFactor10}</td><td>${csi.csiFactor11}</td>
        <td>${csi.csiFactor12}</td><td>${csi.totalCsiScore}</td>
    </tr> 
</logic:iterate>
</table></td></tr>
<tr><td>
<table border=0 style="border-collapse: collapse; border: #0066FF 1px solid;">
    <tr style="background-color: lightblue"><td colspan="14">Follow up assessment</td></tr>
     <tr><td>Date of Assessment</td><td>Emotional health</td><td>Social behavior</td><td>Food security</td><td>Nutrition and growth</td><td>Wellness</td><td>Health care services</td>
        <td>Development and performance</td><td>Education and work</td><td>Abuse and exploitation</td><td>Legal protection</td><td>Shelter</td><td>Care</td><td>Total score</td></tr>
<logic:iterate collection="${ovcHistoryList[2]}" id="followup">
    <tr>
        
        <td>${followup.childStatusIndex.csiDate}</td><td>${followup.childStatusIndex.csiFactor1}</td><td>${followup.childStatusIndex.csiFactor2}</td>
        <td>${followup.childStatusIndex.csiFactor3}</td><td>${followup.childStatusIndex.csiFactor4}</td><td>${followup.childStatusIndex.csiFactor5}</td>
        <td>${followup.childStatusIndex.csiFactor6}</td><td>${followup.childStatusIndex.csiFactor7}</td><td>${followup.childStatusIndex.csiFactor8}</td>
        <td>${followup.childStatusIndex.csiFactor9}</td><td>${csi.csiFactor10}</td><td>${csi.csiFactor11}</td>
        <td>${followup.childStatusIndex.csiFactor12}</td><td>${followup.childStatusIndex.totalCsiScore}</td>
    
    </tr> 
</logic:iterate>
</table></td></tr>
<tr><td>
<table border=0 style="border-collapse: collapse; border: #0066FF 1px solid;">
    <tr style="background-color: lightblue"><td colspan="9">Service provided to OVC</td></tr>
    <tr><td>Date of service</td><td>Psychosocial</td><td>Nutritional</td><td>Health</td><td>Education</td><td>Protection</td><td>Shelter and Care</td><td>Economic strengthening</td><td>Volunteer name</td></tr>
<logic:iterate collection="${ovcHistoryList[3]}" id="service">
    <tr>
        <td>${service.servicedate}</td><td>${service.serviceAccessed1}</td><td>${service.serviceAccessed2}</td>
        <td>${service.serviceAccessed3}</td><td>${service.serviceAccessed4}</td><td>${service.serviceAccessed5}</td>
        <td>${service.serviceAccessed6}</td><td>${service.serviceAccessed7}</td><td>${service.providerName}</td>
    </tr> 
</logic:iterate>
</table></td></tr>
<tr><td>
<table border=0 style="border-collapse: collapse; border: #0066FF 1px solid;">
    <tr style="background-color: lightblue"><td colspan="17">HIV Risk assessment</td></tr>
    <tr><td>Do you (Caregiver) know the HIV status of your child/ward?</td><td>Date of assessment</td>
        <td>Does the child have an HIV-positive parent/sibling/member of household that is an index patient?</td>
        <td>Has the child ever experience any form of sexual violence?</td>
        <td>Has the child tested negative for HIV within the past 6 months?</td>
        <td>Has the child, caregiver or any member of household within the last 3 months been chronically ill?</td>
        <td>Are one or both parents of child deceased?</td>
        <td>Is child sexually active/pregnant recently?</td>
        <td>Has the child been hospitalized more than once in the last 3 months for recurrent and persistent fever/upper respiratory track infections over a period of at least 3 months?</td>
    
    <td>Has the child ever received blood transfusion or had any medical procedure in the last 6 months?</td>
    <td>Does this child (under 5 years) have a MUAC value of <11.5 cm/ <13.5 BMI or are there physical signs of wasting or failure to thrive?</td>
    <td>Has child reported genital discharge, sores or pains in the last 6 months?</td>
    <td>Child at risk</td><td>Name of Service provider</td></tr>
<logic:iterate collection="${ovcHistoryList[4]}" id="hrac">
    <tr>
        <td>${hrac.hivStatusQuestion}</td><td>${hrac.dateOfAssessment}</td><td>${hrac.hivParentQuestion}</td>
        <td>${hrac.sexualAbuseQuestion}</td><td>${hrac.childTestedQuestion}</td><td>${hrac.chronicallyIllQuestion}</td>
        <td>${hrac.parentDeceasedQuestion}</td><td>${hrac.sexuallyActiveQuestion}</td><td>${hrac.childSickQuestion}</td><td>${hrac.bloodTransfusionQuestion}</td>
        <td>${hrac.muacbmiQuestion}</td><td>${hrac.genitalDischargeQuestion}</td>
        <td>${hrac.childAtRiskQuestion}</td><td>${hrac.serviceProviderName}</td>
        
    </tr> 
</logic:iterate>
</table></td></tr>
</table>  
</logic:present>
        </div>
    </body>
</html>

