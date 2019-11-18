<%-- 
    Document   : HVAAnalysisReportView
    Created on : Jul 17, 2014, 9:47:50 PM
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
        <title>Household vulnerability assessment analysis report</title>
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
<div style=" font-family: arial" align="center">HVA analysis </div>
<center>
<table border=0>
    <tr><td style="border: none; text-align: left;">
            <table align="left" border="0" style="border: none;" width="100%">
                <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${hvaAParam[12]}   </label></td>
                <td class="orgNamestd"><label class="lab">LGA:</label></td> <td style=" border: none;" width="25%"><label class="orgNames"> ${hvaAParam[4]} </label></td>
                <td class="orgNamestd"><label class="lab">Organization:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${hvaAParam[5]}   </label></td>
                <td class="orgNamestd"><label class="lab"><jsp:include page="../includes/WardName.jsp" /></label></td> <td style=" border: none;" width="25%"><label class="orgNames">${hvaAParam[11]}   </label></td></tr>
                <tr><td class="orgNamestd" style=" font-size: 14px" colspan="4" align="center" width="50%"> Partner</td><td class="orgNamestd" style=" font-size: 14px" colspan="4" align="center" width="50%"> ${hvaAParam[13]}</td> </tr>
                </table>
        </td></tr>

    <tr><td style="border: none; text-align: left">
                        <table width="100%"
                            border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px" >
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>Thematic area</th>
                                <%--<th >0</th>--%>
                                 <th >1</th>
                                <th >2</th>
                                <th >3</th>
                                <th >4</th>
                            </tr>
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <logic:iterate id="scoreList" name="analysisList">

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

                                    <td width="27%">${scoreList[0]}</td>
                                    <td width="17%">
                                        <a href="hvaAnalysisReportAction.do?TA=${scoreList[0]}:${scoreList[1]}:1" target="_blank"> ${scoreList[2]}</a>
                                    </td>
                                    <td width="14%">
                                        <a href="hvaAnalysisReportAction.do?TA=${scoreList[0]}:${scoreList[1]}:2" target="_blank">${scoreList[3]}</a>
                                    </td>
                                    <td width="19%">
                                        <a href="hvaAnalysisReportAction.do?TA=${scoreList[0]}:${scoreList[1]}:3" target="_blank">${scoreList[4]}</a>
                                    </td>
                                    <td width="20%"><a href="hvaAnalysisReportAction.do?TA=${scoreList[0]}:${scoreList[1]}:4" target="_blank">${scoreList[5]}</a></td>
                                    <%--<td width="10%">${scoreList[5]}</td>--%>
                            </tr>
                            </logic:iterate>
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>Vulnerability status</th>
                                <th >Not assessed</th>
                                <th >Vunerable</th>
                                <th >More vulnerable</th>
                                <th >Most vulnerable</th>
                            </tr>
                            <logic:iterate id="statusList" name="statusScoreList">
                                <tr>
                                    <td >${statusList[0]}</td>
                                    <td ><a href="hvaAnalysisReportAction.do?TA=vulnerabilityScore:${statusList[2]}:0" target="_blank">${statusList[3]}</a></td>
                                    <td ><a href="hvaAnalysisReportAction.do?TA=vulnerabilityScore:${statusList[4]}:1" target="_blank">${statusList[5]}</a></td>
                                    <td ><a href="hvaAnalysisReportAction.do?TA=vulnerabilityScore:${statusList[6]}:2" target="_blank">${statusList[7]}</a></td>
                                    <td ><a href="hvaAnalysisReportAction.do?TA=vulnerabilityScore:${statusList[8]}:3" target="_blank">${statusList[9]}</a></td>
                                </tr>
                            </logic:iterate>
                            </table>
        </td></tr></table>
</center>
    </body>


</html>

