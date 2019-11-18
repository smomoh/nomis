<%-- 
    Document   : CareAndSupportReport
    Created on : Mar 5, 2018, 4:27:42 PM
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
        <title>Care and support checklist report</title>
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
            <table align="left" border="0" style="border: none;"> <%--<jsp:include page=".../includes/WardName.jsp" />--%>
                <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td style=" border: none;" width="20%"><label class="orgNames">${candsparams[4]}</label></td>
                <td class="orgNamestd"><label class="lab">LGA:</label></td> <td style=" border: none;" width="22%"><label class="orgNames">${candsparams[5]}</label></td>
                <td class="orgNamestd"><label class="lab">CBO:</label></td> <td style=" border: none;" width="35%"><label class="orgNames">${candsparams[6]}</label></td>
                <td class="orgNamestd"><label class="lab">Ward</label></td> <td style=" border: none;" width="15%"><label class="orgNames">${candsparams[7]}</label></td></tr>
                <tr><td class="orgNamestd" colspan="9">&nbsp; </td></tr>
                <tr><td class="orgNamestd" colspan="9"> <div style=" font-family: arial" align="center">List of OVC enrolled ${ovcrecordstitle} &nbsp;&nbsp;&nbsp; ${enrollagetitle}</div></td></tr>

                
                </table>
        </td></tr>
    <tr><td style="border: none; text-align: left;">
                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo.</th>
                                <th >Unique ID No.</th>
                                <th >Name</th>
                                <th >Current age</th>
                                <th >Date of assessment</th>
                                <th >Date of HIV test</th>
                                <th >Beneficiary Received test result</th>
                                <th >HIV status</th>
                                <th >Beneficiary enrolled on treatment</th>
                                <th >Date enrolled on ART</th>
                                
                                <th >Name of facility enrolled</th>
                                <th >Viral load test done</th>
                            </tr>
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <!?? iterate over the results of the query ??>
                            <logic:iterate id="cands" name="candsList">

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
                                       <bean:write name="cands" property="clientId"/>
                                    </td>
                                    <td width="10%">
                                       <bean:write name="cands" property="ovc.fullName"/>
                                    </td>
                                     <td width="10%">
                                       <bean:write name="cands" property="ovc.currentAge"/>
                                       <bean:write name="cands" property="ovc.currentAgeUnit"/>
                                    </td>
                                    <td width="25%">
                                       <bean:write name="cands" property="dateOfAssessment"/>
                                    </td>
                                    <td width="25%">
                                       <bean:write name="cands" property="dateOfHivTest" />
                                    </td>
                                    
                                    <td width="10%">
                                        <bean:write name="cands" property="receivedTestResult" />
                                    </td>
                                    <td width="16%">
                                        <bean:write name="cands" property="hivStatus" />
                                    </td>
                                    <td width="20%">
                                        <bean:write name="cands" property="enrolledOnTreatment" />
                                    </td>
                                    <td width="3%">
                                        <bean:write name="cands" property="dateEnrolledOnART" />
                                    </td>
                                    <td width="3%">
                                        <bean:write name="cands" property="facility.facilityName" />
                                    </td>
                                    <td width="3%">
                                        <bean:write name="cands" property="viralLoadTestDone" />
                                    </td>
                                    
                                </tr>
                            </logic:iterate>
                            </table>
        </td></tr></table>
         </div>
    </body>
</html>
