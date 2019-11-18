<%-- 
    Document   : HivRiskAssessmentReport
    Created on : Jun 12, 2017, 9:16:10 AM
    Author     : smomoh
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
        <title>HIV Risk assessment report</title>
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
                <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td style=" border: none;" width="20%"><label class="orgNames">${hracparams[8]}</label></td>
                <td class="orgNamestd"><label class="lab">LGA:</label></td> <td style=" border: none;" width="22%"><label class="orgNames">${hracparams[9]}</label></td>
                <td class="orgNamestd"><label class="lab">CBO:</label></td> <td style=" border: none;" width="35%"><label class="orgNames">${hracparams[10]}</label></td>
                <td class="orgNamestd"><label class="lab"><jsp:include page="../includes/WardName.jsp" /></label></td> <td style=" border: none;" width="15%"><label class="orgNames">${hracparams[11]}</label></td></tr>
                <tr><td class="orgNamestd" colspan="9">&nbsp; </td></tr>
                <tr><td class="orgNamestd" colspan="9"> <div style=" font-family: arial" align="center">List of OVC</div></td></tr>

                
                </table>
        </td></tr>
    <tr><td style="border: none; text-align: left;">
                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo.</th>
                                <th >OVC Unique ID No.</th>
                                <th >Full name</th>
                                <th >Current age</th>
                                <th >Age unit</th>
                                <th >Gender</th>
                                <th >Date of assessment</th>
                                <th >Does the child have an HIV-positive parent or sibling?</th>
                                <th >Comment</th>
                                <th >Has the child experience any sexual abuse?</th>
                                <th >Comment</th>
                                <%--Has the child been tested for HIV in the past 6 months?--%>
                                <th >Has the child tested negative for HIV more than 6 months ago?</th>
                                <th >Comment</th>
                                <th >Is the child, parent or any family member chronically ill?</th>
                                <th >Comment</th>
                                <th >Is child sexually active?</th>
                                <th >Comment</th>
                                <th >Has the child been sick or admitted to hospital in the past 6 months?</th>
                                <th >Comment</th>
                                <th >Has the child ever received blood transfusion or had any medical procedure in the last 6 months?</th>
                                <th >Comment</th>
                                <th >Does this child (under 5 years) have a MUAC value of <11.5 cm/ <13.5 BMI or are there physical signs of wasting or failure to thrive?</th>
                                <th >Comment</th>
                                
                                <logic:present name="showOldFields">
                                    <th >Is the child older than 2 years of age?</th>
                                    <th >Comment</th>
                                    <th >Does the child have recurring skin problems?</th>
                                    <th >Comment</th>
                                    <th >Are one or both parents of child deceased?</th>
                                    <th >Comment</th>
                                    <th >Is the child below his/her expected school grade relative to his/her peers?</th>
                                    <th >Comment</th>
                                    <th >Has child reported genital discharge, sores or pains in the last 6 months?</th>
                                    <th >Comment</th>
                                </logic:present>
                                <th >Child at risk</th>
                                <th >Name of service provider</th>
                                <th >Designation</th>
                                <th >Phone</th>
                                <th >Date of entry</th>
                            </tr>
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <!?? iterate over the results of the query ??>
                            <logic:iterate id="hrac" name="hracList">

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
                                    <td width="25%">${hrac.ovcId}</td>
                                    <td width="25%">${hrac.ovc.fullName}</td>
                                    <td width="25%">${hrac.ovc.currentAge}</td>
                                    <td width="25%">${hrac.ovc.currentAgeUnit}</td>
                                    <td width="25%">${hrac.ovc.gender}</td>                                  
                                    
                                    
                                    <td width="25%">${hrac.dateOfAssessment}</td>
                                    
                                    <td width="25%">${hrac.hivParentQuestion}</td>
                                    <td width="25%">${hrac.hivParentComment}</td>
                                    <td width="25%">${hrac.sexualAbuseQuestion}</td>
                                    <td width="25%">${hrac.sexualAbuseComment}</td>
                                    <td width="25%">${hrac.childTestedQuestion}</td>
                                    <td width="25%">${hrac.childTestedComment}</td>
                                    <td width="25%">${hrac.chronicallyIllQuestion}</td>
                                    <td width="25%">${hrac.chronicallyIllComment}</td>
                                    <td width="25%">${hrac.sexuallyActiveQuestion}</td>
                                    <td width="25%">${hrac.sexuallyActiveComment}</td>
                                    <td width="25%">${hrac.childSickQuestion}</td>
                                    <td width="25%">${hrac.childSickComment}</td>
                                    <td width="25%">${hrac.bloodTransfusionQuestion}</td>
                                    <td width="25%">${hrac.bloodTransfusionComment}</td>
                                    <td width="25%">${hrac.muacbmiQuestion}</td>
                                    <td width="25%">${hrac.muacbmiComment}</td>
                                    
                                    <logic:present name="showOldFields">
                                        <td width="25%">${hrac.childAgeQuestion}</td>
                                        <td width="25%">${hrac.childAgeComment}</td>
                                        <td width="25%">${hrac.skinProblemQuestion}</td>
                                        <td width="25%">${hrac.skinProblemComment}</td>
                                        <td width="25%">${hrac.parentDeceasedQuestion}</td>
                                        <td width="25%">${hrac.parentDeceasedComment}</td>
                                        <td width="25%">${hrac.schoolGradeQuestion}</td>
                                        <td width="25%">${hrac.schoolGradeComment}</td>                               
                                        <td width="25%">${hrac.genitalDischargeQuestion}</td>
                                        <td width="25%">${hrac.genitalDischargeComment}</td>
                                    </logic:present>
                                        
                                    <td width="25%">${hrac.childAtRiskQuestion}</td>
                                    <td width="25%">${hrac.serviceProviderName}</td>
                                    <td width="25%">${hrac.designation}</td>
                                    <td width="25%">${hrac.serviceProviderPhone}</td>
                                    <td width="25%">${hrac.lastModifiedDate}</td>
                                </tr>
                            </logic:iterate>
                            </table>
        </td></tr></table>
         </div>
    </body>
</html>