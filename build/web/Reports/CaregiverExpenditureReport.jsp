<%-- 
    Document   : CaregiverExpenditureReport
    Created on : Mar 20, 2018, 1:42:44 AM
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
        <title>Caregiver Expenditure and school attendance report</title>
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
<div style=" font-family: arial" align="center">Caregiver Expenditure and school attendance report </div>
<table border=0>
    <tr><td style="border: none; text-align: left;">
            <table align="left" border="0" style="border: none;" width="100%">
                <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${ceasaParam[12]}   </label></td>
                <td class="orgNamestd"><label class="lab">LGA:</label></td> <td style=" border: none;" width="25%"><label class="orgNames"> ${ceasaParam[4]} </label></td>
                <td class="orgNamestd"><label class="lab">Organization:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${ceasaParam[5]}   </label></td>
                <td class="orgNamestd"><label class="lab"><jsp:include page="../includes/WardName.jsp" /></label></td> <td style=" border: none;" width="25%"><label class="orgNames">${ceasaParam[11]}   </label></td></tr>
                <tr><td class="orgNamestd" style=" font-size: 14px" colspan="4" align="center" width="50%"> Partner</td><td class="orgNamestd" style=" font-size: 14px" colspan="4" align="center" width="50%"> ${ceasaParam[13]}</td> </tr>
                </table>
        </td></tr>

    <tr><td style="border: none; text-align: left">
                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th >Date of assessment (mm/dd/yyyy)</th>
                                <th>Unique id</th>
                                <th >Caregiver name </th>
                                <th >Sex(M/F)</th>
                                <th >Age</th>
                                <th >Marital status</th>
                                <th >Address</th>
                                
                                <th >Phone</th>
                                
                                <th >Did you make any unexpected expenditure in the past six (6) months? </th>
                                <th >were you able to access money to pay for this unexpected expenditure? </th>
                                <th >How were you able to raise the money?</th>
                                <th >What are the household (HH) needs requiring routine and/or emergency cash to address?</th>
                                <th >Did your child/children miss school/vocational training for more than 5 days in the last one month (4 weeks) of full academic/vocational training session?</th>
                                <th >Child’s/children information</th>
                                <th >Name of volunteer </th>
                                <th >Phone number </th>
                                <th >Recorded by</th>
                               <th >Last modified date (mm/dd/yyyy)</th>
                               
                            </tr>
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <logic:iterate id="ceasa" name="ceasareportList">

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
                                        ${ceasa.dateOfAssessment}
                                    </td>
                                    <td width="20%">
                                       ${ceasa.caregiverId}
                                    </td>
                                    
                                    <td width="10%">
                                        ${ceasa.cgiver.caregiverFullName}
                                    </td>
                                    <td width="3%">
                                        ${ceasa.cgiver.caregiverGender}
                                    </td>
                                    <td width="3%">
                                        ${ceasa.cgiver.caregiverAge}
                                    </td>
                                    <td width="3%">
                                        ${ceasa.cgiver.caregiverMaritalStatus}
                                    </td>
                                    <td width="20%">
                                        ${ceasa.cgiver.caregiverAddress}
                                    </td>
                                    
                                    
                                    <td width="20%">
                                       ${ceasa.cgiver.caregiverPhone}
                                    </td>
                                    
                                    <td width="3%">
                                        ${ceasa.unexpectedExpenditure}
                                    </td>
                                    <td width="3%">
                                        ${ceasa.accessMoney}
                                    </td>
                                    <td width="3%">
                                        ${ceasa.sourceOfMoney}
                                    </td>
                                    
                                    <td width="3%">
                                        ${ceasa.urgentHhNeeds}
                                    </td>
                                    <td width="3%">
                                        ${ceasa.schAttendance}
                                    </td>
                                    <td width="3%">
                                        ${ceasa.ovcNames}
                                    </td>
                                    <td width="3%">
                                        ${ceasa.reasonsForMissingSch}
                                    </td>
                                                                       
                                    <td width="3%">
                                        ${ceasa.volunteerName}
                                    </td>
                                    <td width="3%">
                                        ${ceasa.volunteerPhone}
                                    </td>
                                    <td width="3%">
                                        ${ceasa.lastModifiedDate}
                                    </td>
                                    
                                    
                                    
                                   
                                </tr>
                            </logic:iterate>
                            </table>
        </td></tr></table>
        </div>
    </body>


</html>
