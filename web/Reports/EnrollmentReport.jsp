<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>

<logic:notPresent name="ovcRecords">
    <jsp:forward page="EnrollmentRecord.jsp" />
    <%--<logic:forward name="EnrollmentRecord.jsp" />--%>
</logic:notPresent>


<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>Enroment record</title>
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

    <body>
<div align="center">
<div style=" font-family: arial" align="center">VC enrolment record</div>
<table border=0>
    <tr><td style="border: none; text-align: left;">
            <table align="left" border="0" style="border: none;">
                <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td style=" border: none;" width="20%"><label class="orgNames">${orgparams[14]}</label></td>
                <td class="orgNamestd"><label class="lab">LGA:</label></td> <td style=" border: none;" width="22%"><label class="orgNames">${orgparams[11]}</label></td>
                <td class="orgNamestd"><label class="lab">CBO:</label></td> <td style=" border: none;" width="35%"><label class="orgNames">${orgparams[12]}</label></td>
                <td class="orgNamestd"><label class="lab"><jsp:include page="../includes/WardName.jsp" /></label></td> <td style=" border: none;" width="15%"><label class="orgNames">${orgparams[13]}</label></td></tr>
                <tr><td class="orgNamestd" colspan="9">&nbsp; </td></tr>
                <tr><td class="orgNamestd" colspan="9"> <div style=" font-family: arial" align="center">List of OVC enrolled ${ovcrecordstitle} &nbsp;&nbsp;&nbsp; ${enrollagetitle}</div></td></tr>

                <tr><td class="orgNamestd" style=" font-size: 12px; width: 300px;" colspan="8">
                        <a href="enrollmentpdf.do" style="margin-left: 20px;" target="_blank">Export to PDF</a>
                        <a href="enrollmentReport.do?id=exceldownload" style="margin-left: 20px;" target="_blank">Download in excel</a></td>
                    <td style=" border: none;" width="15%">
                    </td></tr>
                </table>
        </td></tr>
    <tr><td style="border: none; text-align: left;">
                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo.</th>
                                <th >OVC Unique ID No.</th>
                                <th >Date of Enrollment (yyyy-mm-dd)</th>
                                <th >Surname</th>
                                <th >Other Names</th>
                                <th >Age at baseline</th>
                                <th>Age Unit at baseline</th> 
                                <th >Current age(yrs)</th>
                                <th >Sex</th>
                                <th >Phone</th>
                                <th >Eligibility criteria</th>
                                <th>Baseline Hiv status</th>
                                <th>Current Hiv status</th>
                                <th>Date of current Hiv status (yyyy-mm-dd)</th>
                                <th>Enrolled in Care</th>
                                <th >Enrolled on ART</th>
                                <th >point of current Hiv status update</th>
                                <!--<th >State</th>
                                <th >LGA</th>
                                <th >CBO</th>
                                <th >Ward</th>                 
                                <th style=" width: 300px;">Eligibility</th>
                                <th >Hiv status</th>
                                <th >Birth certificate</th>
                                <th >School status</th>
                                <th >Type of school</th>
                                <th >School name</th>
                                <th >Class</th>
                                <th >Child in orphanage</th>
                                <th >Name of orphanage</th>-->
                                <th >Caregiver name</th>
                                <th >Caregiver address</th>
                                <th >Caregiver phone</th>
                                <th >Withdrawn from program</th>
                               <th >Date of withdrawal (yyyy-mm-dd)</th>
                               <th >Reason for withdrawal</th>
                               <th >Date of entry (yyyy-mm-dd)</th>
                                <!--<th >Caregiver occupation</th>
                                <th >Relationship to child</th>-->
                                <th >Completed by</th>
                                <th >Designation</th>
                            </tr>
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <!?? iterate over the results of the query ??>
                            <logic:iterate id="ovcData" name="ovcRecords">

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

                                    <%--<tr align="left" bgcolor="">--%>

                                    <td width="3%"><%= ++row %></td>
                                    <td width="25%">
                                       <bean:write name="ovcData" property="ovcId" />
                                    </td>
                                    
                                    <td width="10%">
                                        <bean:write name="ovcData" property="dateEnrollment" />
                                    </td>
                                    <td width="16%">
                                        <bean:write name="ovcData" property="lastName" />
                                    </td>
                                    <td width="20%">
                                        <bean:write name="ovcData" property="firstName" />
                                    </td>
                                    <td width="3%">
                                        <bean:write name="ovcData" property="age" />
                                    </td>
                                    <td width="3%">
                                        <bean:write name="ovcData" property="ageUnit" />
                                    </td>
                                    <td width="3%">
                                        <bean:write name="ovcData" property="currentAge" />
                                    </td>
                                    <td width="6%"><bean:write name="ovcData" property="gender" /></td>
                                    <td width="4%"><bean:write name="ovcData" property="phone" /></td>
                                    <td width="4%"><bean:write name="ovcData" property="eligibility" /></td>
                                    <td width="4%"><bean:write name="ovcData" property="hivStatus" /></td>
                                    <td width="4%"><bean:write name="ovcData" property="activeHivStatus.hivStatus" /></td>
                                    <td width="4%"><bean:write name="ovcData" property="activeHivStatus.dateOfCurrentStatus" /></td>
                                    <td width="4%"><bean:write name="ovcData" property="activeHivStatus.clientEnrolledInCare" /></td>
                                    <td width="4%"><bean:write name="ovcData" property="activeHivStatus.enrolledOnART" /></td>
                                    <td width="4%"><bean:write name="ovcData" property="activeHivStatus.pointOfUpdate" /></td>
                                    
                                    
                                    <td width="6%"><bean:write name="ovcData" property="caregiverName" /></td>
                                    <%--<td width="6%"><bean:write name="ovcData" property="caregiverGender" /></td>
                                    <td width="6%"><bean:write name="ovcData" property="caregiverAge" /></td>--%>
                                    <td width="6%"><bean:write name="ovcData" property="caregiverAddress" /></td>
                                    <td width="4%"><bean:write name="ovcData" property="caregiverPhone" /></td>
                                    <td width="6%"><bean:write name="ovcData" property="withdrawnFromProgram" /></td>
                                    <td width="4%"><bean:write name="ovcData" property="dateOfWithdrawal" /></td>
                                    <td width="6%"><bean:write name="ovcData" property="reasonForWithdrawal" /></td>
                                    <td width="6%"><bean:write name="ovcData" property="dateOfEntry" /></td>
                                    <%--<td width="6%"><bean:write name="ovcData" property="caregiverOccupation" /></td>
                                    <td width="6%"><bean:write name="ovcData" property="caregiverRelationships" /></td>--%>
                                    <td width="6%"><bean:write name="ovcData" property="completedbyName" /></td>
                                    <td width="6%"><bean:write name="ovcData" property="completedbyDesignation" /></td>
                                </tr>
                            </logic:iterate>
                            </table>
        </td></tr></table>
         </div>
    </body>

    

</html>
