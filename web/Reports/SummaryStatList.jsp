<%-- 
    Document   : Summary statistics List
    Created on : May 29, 2010, 2:53:29 PM
    Author     : COMPAQ USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>List of indicators</title>
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

    <body style="background-image:url('images/ReportBg.jpg');">
<logic:notPresent name="summaryStatisticsList">
    <jsp:forward page="HouseholdVulnerabilityReport.jsp" />
</logic:notPresent>
         
            <center>
                <div align="center" style=" width:1070px; height: 10000px; background-color: white;">
                    <br/>
                    
                    <div align="center">
                    <table border=0 style=" width: 920px; margin: 10px;">
                        <tr><td border="0" style="border: none;" align="center"><label class="lab" style="margin-right:13px;">${summListTitle}</label> </td></tr>
                    <tr><td border="0" style="border: none;"><label class="lab" style="margin-right:13px;">&nbsp;</label> </td></tr>
                    <tr><td align="left" style=" border: none;">
                <table align="left" border="0" style="border: none;">
                    <tr><td class="orgNamestd" ><label class="lab">State:</label></td> <td width="25%" style=" border: none;"><label class="orgNames">${summaryStatparams[5]}</label></td>
                    <td class="orgNamestd" ><label class="lab">LGA:</label></td> <td width="30%" style=" border: none;"><label class="orgNames">${summaryStatparams[6]}</label></td>
                    <td class="orgNamestd" ><label class="lab">CBO:</label></td> <td width="35%" style=" border: none;"><label class="orgNames">${summaryStatparams[17]}</label></td></tr>
                    <tr><td class="orgNamestd" colspan="4" align="center" ><label class="lab">${period}</label> </td><td class="orgNamestd" colspan="2" align="center"><label class="lab"> ${agegroup}</label></td></tr>
               </table>
                </td>
                        </tr>
                        
                        <tr>
                            <td style=" border: none;">
                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th>State</th>
                                <th>LGA</th>
                                <th>CBO</th>
                                <th >Community/Ward</th>
                                <th>HH Unique Id</th>
                                <th >Caregiver Id</th>
                                <th>OVC Id</th>
                                <th >Surname</th>
                                <th>Other Names</th>
                                <th >Date of enrollment</th>
                                <th >Age at baseline</th>
                                <th >Current age</th>
                                <th >Age unit</th>
                                <th >Sex</th>
                                <th>Address</th>
                                <th>Eligibility criteria</th>
                                <th>Baseline Hiv status</th>
                                <th>Current Hiv status</th>
                                <th>Date of current Hiv status (yyyy-mm-dd)</th>
                                <th>Enrolled in Care</th>
                                <th >Enrolled on ART</th>
                                <th >Facility enrolled</th>
                                <th >point of current Hiv status update</th>
                                <th >Child in school?</th>
                                <th >Name of school</th>
                                <th >Class</th>
                                <th >Care giver Name</th>
                                <th >Care giver Phone</th>
                                <th >Baseline CSI score</th>
                                <th >Baseline vulnerability status</th>
                                <th >Current CSI score</th>
                                <th >Current vulnerability status</th>
                                <%--<th >Withdrawn from program</th>--%>
                                <th >Current enrollment status</th>
                                <th >Date of current status</th>
                                <th >Completed by</th>
                                <th >Designation</th>
                               
                            </tr>
                            
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <!?? iterate over the results of the query ??>
                           <logic:iterate id="summList" name="summaryStatisticsList">

                                <%
                                            cnt++;
                                            if (cnt % 2 != 0) {
                                %>
                                <tr align="left" bgcolor="#FFFFFF">
                                    <%} else {
                                    %>
                                <tr align="left" bgcolor="#DDDDDD">
                                    <%            }
                                    %>

                                    <td width="1%"><%= ++row %></td>
                                    <td width="12%">
                                        ${summList.state}
                                    </td>
                                    <td width="12%">
                                        ${summList.lga}
                                    </td>
                                    <td width="12%">
                                        ${summList.completedbyCbo}
                                    </td>
                                    <td width="7%"> 
                                        ${summList.ward}
                                    </td>
                                    <td width="12%">
                                        ${summList.hhUniqueId}
                                    </td>
                                    <td width="15%">
                                        ${summList.caregiverId}
                                    </td>
                                    <td width="12%">
                                        ${summList.ovcId}
                                    </td>
                                    <td width="5%">
                                     ${summList.lastName}
                                    </td>
                                    <td width="9%">
                                        ${summList.firstName}
                                    </td>
                                    <td width="2%">
                                        ${summList.dateEnrollment} <%--${summList.ageUnit}(s)--%>
                                    </td>
                                    <td width="2%">
                                        ${summList.age} <%--${summList.ageUnit}(s)--%>
                                    </td>
                                    <td width="2%">
                                    ${summList.currentAge} <%--${summList.ageUnit}(s)--%>
                                    </td>
                                    <td width="2%">
                                    ${summList.currentAgeUnit} 
                                    </td>
                                    <td width="3%">
                                    ${summList.gender}
                                    </td>
                                    <td width="15%">
                                    ${summList.address}
                                    </td>
                                    <td width="15%">
                                    ${summList.eligibility}
                                    </td>
                                    <td width="15%">
                                        ${summList.hivStatus}
                                    </td>
                                    <td width="15%">
                                        ${summList.activeHivStatus.hivStatus}
                                    </td>
                                    <td width="15%">
                                        ${summList.activeHivStatus.dateOfCurrentStatus}
                                    </td>
                                    <td width="15%">
                                        ${summList.activeHivStatus.clientEnrolledInCare}
                                    </td>
                                    <td width="15%">
                                        ${summList.activeHivStatus.enrolledOnART}
                                    </td>
                                    <td width="15%">
                                        ${summList.activeHivStatus.facility.facilityName}
                                    </td>
                                    <td width="15%">
                                        ${summList.activeHivStatus.pointOfUpdate}
                                    </td>
                                    
                                    <td width="15%">
                                        ${summList.currentSchoolStatusObj.ovcInSchool}
                                    </td>
                                    <td width="15%">
                                        ${summList.currentSchoolStatusObj.currentSchoolName}
                                    </td>
                                    <td width="15%">
                                        ${summList.currentSchoolStatusObj.currentClass}
                                    </td>
                                    
                                    <td width="5%">
                                        ${summList.caregiverName}
                                    </td>
                                    <td width="7%"> 
                                    ${summList.caregiverPhone}
                                    </td>
                                    <td width="7%"> 
                                    ${summList.baselineVulnerabilityScore}
                                    </td>
                                    <td width="7%"> 
                                    ${summList.baselineVulnerabilityStatus}
                                    </td>
                                    <td width="7%"> 
                                    ${summList.currentVulnerabilityScore}
                                    </td>
                                    <td width="7%"> 
                                    ${summList.currentVulnerabilityStatus}
                                    </td>
                                    <%--<td width="5%">
                                    ${summList.withdrawnFromProgram}
                                    </td>--%>
                                                                        
                                    <td width="7%"> 
                                    ${summList.reasonForWithdrawal}
                                    </td>
                                    <td width="5%">
                                    ${summList.dateOfWithdrawal}
                                    </td>
                                    <td width="5%">
                                    ${summList.completedbyName}
                                    </td>
                                    
                                    <td width="7%"> 
                                    ${summList.completedbyDesignation}
                                    </td>
                                  </tr>
                           </logic:iterate>
                                  </table>
                            </td></tr>
                            </table>

                            </div>
                </div>
                            </center>
        </body>

    

</html>

