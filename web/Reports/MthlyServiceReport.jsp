<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>

<logic:notPresent name="serviceList">
    <jsp:forward page="MthlyServiceReportDatePage.jsp" />
</logic:notPresent>


<%--@page import="com.fhi.kidmap.report.OvcRecords;" --%>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>List of OVC</title>
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
    border: none; text-align: left; width: 40px;
}
</style>

    </head>

    <div align="center">

        <body>
<div style=" font-family: arial" align="center">List of OVC provided services between ${serviceDate[0]} and ${serviceDate[1]} <br/>age between ${serviceAgePeriod}</div>
<table border=0>
    <tr><td style="border: none; text-align: left;">
            <table align="left" border="0" style="border: none;">
                <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td style=" border: none;" width="20%"><label class="orgNames">${serviceOrgParam[10]}</label></td>
                <td class="orgNamestd"><label class="lab">LGA:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${serviceOrgParam[11]}</label></td>
                <td class="orgNamestd"><label class="lab">CBO:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${serviceOrgParam[2]}</label></td>
                <td class="orgNamestd"><label class="lab"><jsp:include page="../includes/WardName.jsp" /></label></td> <td style=" border: none;" width="25%"><label class="orgNames">${serviceOrgParam[14]}</label></td>
                <td class="orgNamestd"><label class="lab">Partner:</label></td> <td style=" border: none;" width="30%"><label class="orgNames">${serviceOrgParam[12]}</label></td>
                </table>
        </td></tr>
    <tr><td style="border: none; text-align: left;">
                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo.</th>
                                <th>State</th>
                                <th>LGA</th>
                                <th>CBO</th>
                                <th >Ward</th>
                                <th >Community</th>
                                <th >Service date (mm/dd/yyyy)</th>
                                <th >HH Unique ID</th>
                                <th >OVC Unique ID</th>
                                <th >Surname</th>
                                <th >Other Names</th>
                                <th >Sex</th>
                                <th >Age at baseline</th>
                                <th >Current age</th>
                                <th >Eligibility criteria</th>
                                <th >HIV status</th>
                                <th >Enrolled in Care?</th>
                                <th >Enrolled on ART?</th>
                                <th >Caregiver name</th>
                                <th >Psychosocial support</th>
                                <th >Nutrtion</th>
                                <th >Health</th>
                                <th >Education</th>
                                <th >Child protection</th>
                                <th >Shelter and care</th>
                                <th >Economic strengthening</th>
                                                                
                                <th >Child missed school</th>
                                <th >Child abused</th>
                                <th >Child linked to Government agency?</th>
                                
                                <th >Last modified date (mm/dd/yyyy)</th>
                                <th >Withdrawn from program</th>
                                <th >Date withdrawn</th>
                                <th >Reason for withdrawal</th>
                                <th >Volunteer name</th>
                            </tr>
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <!?? iterate over the results of the query ??>
                            <logic:iterate id="serviceInfo" name="serviceList">

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

                                    <td width="1%"><%= ++row %></td>
                                    <td width="10%">
                                       <bean:write name="serviceInfo" property="state" />
                                    </td>
                                    <td width="10%">
                                       <bean:write name="serviceInfo" property="lga" />
                                    </td>  
                                    <td width="10%">
                                       <bean:write name="serviceInfo" property="completedbyCbo" />
                                    </td>  
                                    <td width="3%"><bean:write name="serviceInfo" property="ward"/> </td>
                                    <td width="3%"> </td>
                                    <td width="3%"><bean:write name="serviceInfo" property="serviceDate" /></td>
                                    <td width="10%">
                                       <bean:write name="serviceInfo" property="ovc.hhUniqueId" />
                                    </td> 
                                    <td width="10%">
                                       <bean:write name="serviceInfo" property="ovcId" />
                                    </td>                                
                                    <td width="6%">
                                        <bean:write name="serviceInfo" property="surname" />
                                    </td>
                                    <td width="8%">
                                        <bean:write name="serviceInfo" property="otherNames1" />
                                    </td>
                                    <td width="3%"><bean:write name="serviceInfo" property="gender" /></td>
                                    <td width="4%">
                                        <bean:write name="serviceInfo" property="age" />
                                        <bean:write name="serviceInfo" property="ageUnit" />(s)
                                    </td>
                                    <td width="4%">
                                        <bean:write name="serviceInfo" property="ovc.currentAge" />
                                        <bean:write name="serviceInfo" property="ovc.currentAgeUnit" />(s)
                                    </td>
                                    
                                    <td width="3%"><bean:write name="serviceInfo" property="ovc.eligibility" /></td>
                                    <td width="3%"><bean:write name="serviceInfo" property="ovc.activeHivStatus.hivStatus"/></td>
                                    <td width="3%"><bean:write name="serviceInfo" property="ovc.activeHivStatus.clientEnrolledInCare"/></td>
                                    <td width="3%"><bean:write name="serviceInfo" property="ovc.activeHivStatus.enrolledOnART"/></td>
                                    <td width="3%"><bean:write name="serviceInfo" property="ovc.caregiverName"/></td>
                                    <%--<td width="4%"><bean:write name="serviceInfo" property="psychosocialServicesByName" /></td>
                                    <td width="4%"><bean:write name="serviceInfo" property="nutritionalServicesByName" /></td>
                                    <td width="4%"><bean:write name="serviceInfo" property="healthServicesByName" /></td>
                                    <td width="4%"><bean:write name="serviceInfo" property="educationalServicesByName" /></td>
                                    <td width="4%"><bean:write name="serviceInfo" property="protectionServicesByName" /></td>
                                    <td width="4%"><bean:write name="serviceInfo" property="shelterAndCareServicesByName" /></td>
                                    <td width="4%"><bean:write name="serviceInfo" property="serviceAccessed7" /></td>
                                    --%> 
                                    <td width="4%"><bean:write name="serviceInfo" property="serviceAccessed1" /></td>
                                    <td width="4%"><bean:write name="serviceInfo" property="serviceAccessed2" /></td>
                                    <td width="4%"><bean:write name="serviceInfo" property="serviceAccessed3" /></td>
                                    <td width="4%"><bean:write name="serviceInfo" property="serviceAccessed4" /></td>
                                    <td width="4%"><bean:write name="serviceInfo" property="serviceAccessed5" /></td>
                                    <td width="4%"><bean:write name="serviceInfo" property="serviceAccessed6" /></td>
                                    <td width="4%"><bean:write name="serviceInfo" property="serviceAccessed7" /></td>
                                    
                                    
                                    <td width="4%"><bean:write name="serviceInfo" property="childMissedSchool" /></td>
                                    <td width="4%"><bean:write name="serviceInfo" property="childAbusedAnswer" /></td>
                                    <td width="4%"><bean:write name="serviceInfo" property="childLinkedToGovtAnswer" /></td>
                                    
                                    
                                    <td width="4%"><bean:write name="serviceInfo" property="dateOfEntry" /></td>
                                    <td width="4%"><bean:write name="serviceInfo" property="withdrawnFromProgram" /></td>
                                    <td width="4%"><bean:write name="serviceInfo" property="dateOfWithdrawal" /></td>
                                    <td width="4%"><bean:write name="serviceInfo" property="reasonForWithdrawal" /></td>
                                    <td width="4%"><bean:write name="serviceInfo" property="providerName" /></td>
                                    
                                </tr>
                            </logic:iterate>
                            </table>
        </td></tr></table>

    </body>

    </div>

</html>
