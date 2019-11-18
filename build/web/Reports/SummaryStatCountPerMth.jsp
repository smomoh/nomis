<%--
    Document   : CsiAnalysis
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

<logic:notPresent name="summaryStatisticsPerMth">
    <jsp:forward page="OvcSummaryStatistics.jsp" />
</logic:notPresent>
--%>

<%@page import="com.fhi.kidmap.report.OvcRecords;" %>

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
        font-family:  arial, sans-serif;
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
    font-family:  arial, sans-serif;
}
</style>
    </head>

        <body style="background-image:url('images/ReportBg.jpg');">
<%
    
%>

            <html:errors/>

            <center>
            
            <div align="center" style=" width: 1050px; height: 1000px; background-color: white;"><br/>
                <table border=0 style=" width: 1050px;">
                    <tr><td border="0" style="border: none;"><label class="lab">List of indicators<span style="font-family:  arial, sans-serif; font-size: 12px; letter-spacing: normal"> (${dateLabels[0]} - ${dateLabels[5]})</span></label> </td></tr>
                    <tr><td align="left" style=" border: none;">
                <table align="left" border="0" style="border: none;">
                    <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td width="30%" style=" border: none;"><label class="orgNames">${summaryStatparams[5]}</label></td>
                    <td class="orgNamestd"><label class="lab">LGA:</label></td> <td width="30%" style=" border: none;"><label class="orgNames">${summaryStatparams[6]}</label></td>
                    <td class="orgNamestd"><label class="lab">CBO:</label></td> <td width="30%" style=" border: none;"><label class="orgNames">${summaryStatparams[2]}</label></td></tr>
                </table>
                            </td>
                        </tr>
                        <tr><td colspan="2" class="orgNamestd" align="center" style="width: 300px;"><a href="mthlysummstatpdfreport.do" style="margin-left: 20px; font-size: 15px;" target="_blank">Export to PDF</a></td></tr>
                        
                        <tr>
                            <td style=" border: none;">


                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th>Indicator Name</th>
                                <th colspan="2">${dateLabels[0]}</th>
                                <th colspan="2">${dateLabels[1]}</th>
                                <th colspan="2">${dateLabels[2]}</th>
                                <th colspan="2">${dateLabels[3]}</th>
                                <th colspan="2">${dateLabels[4]}</th>
                                <th colspan="2">${dateLabels[5]}</th>
                                <th colspan="2">Total</th>
                                <th >Grand Total</th>
                            </tr>
                            <tr align="left" bgcolor="#D7E5F2">
                                <th > </th>
                                <th > </th>
                                <th >Male</th>
                                <th >Female</th>
                                <th >Male</th>
                                <th >Female</th>
                                <th >Male</th>
                                <th >Female</th>
                                <th >Male</th>
                                <th >Female</th>
                                <th >Male</th>
                                <th >Female</th>
                                <th >Male</th>
                                <th >Female</th>
                                <th >Male</th>
                                <th >Female</th>
                                <th > </th>
                            </tr>

                            <%
                                        int cnt = 0;
                                        int row =0;
                                        int indicatorIndex=0;
                             %>
                             <logic:iterate id="summList" name="summaryStatisticsPerMth">
                                <%
                                            cnt++;
                                            request.setAttribute("index", ++indicatorIndex);
                                            if (cnt % 2 != 0) {
                                %>
                                <tr align="left" bgcolor="#FFFFFF">
                                    <%} else {
                                    %>
                                <tr align="left" bgcolor="#DDDDDD">
                                    <%            }
                                    %>

                                    <td width="2%"><%= ++row %></td>
                                    <td width="35%">
                                        ${summList[0]}
                                    </td>
                                    <td width="7%">
                                        <a href="Reports/SummaryStatList.jsp?reqParam=${dateParams[0]}:${dateParams[1]}:${index}:Male:${summList[0]}:${dateLabels[0]}" target="_blank">${summList[1]}</a>
                                    </td>
                                    <td width="7%">
                                        <a href="Reports/SummaryStatList.jsp?reqParam=${dateParams[0]}:${dateParams[1]}:${index}:Female:${summList[0]}:${dateLabels[0]}" target="_blank">${summList[2]}</a>
                                    </td>
                                    <td width="7%">
                                        <a href="Reports/SummaryStatList.jsp?reqParam=${dateParams[2]}:${dateParams[3]}:${index}:Male:${summList[0]}:${dateLabels[1]}" target="_blank">${summList[3]}</a>
                                    </td>
                                    <td width="7%">
                                        <a href="Reports/SummaryStatList.jsp?reqParam=${dateParams[2]}:${dateParams[3]}:${index}:Female:${summList[0]}:${dateLabels[1]}" target="_blank">${summList[4]}</a>
                                    </td>
                                    <td width="7%">
                                        <a href="Reports/SummaryStatList.jsp?reqParam=${dateParams[4]}:${dateParams[5]}:${index}:Male:${summList[0]}:${dateLabels[2]}" target="_blank">${summList[5]}</a>
                                    </td>
                                    <td width="7%">
                                        <a href="Reports/SummaryStatList.jsp?reqParam=${dateParams[4]}:${dateParams[5]}:${index}:Female:${summList[0]}:${dateLabels[2]}" target="_blank">${summList[6]}</a>
                                    </td>
                                    <td width="7%">
                                        <a href="Reports/SummaryStatList.jsp?reqParam=${dateParams[6]}:${dateParams[7]}:${index}:Male:${summList[0]}:${dateLabels[3]}" target="_blank">${summList[7]}</a>
                                    </td>
                                    <td width="7%">
                                        <a href="Reports/SummaryStatList.jsp?reqParam=${dateParams[6]}:${dateParams[7]}:${index}:Female:${summList[0]}:${dateLabels[3]}" target="_blank">${summList[8]}</a>
                                    </td>
                                    <td width="7%">
                                        <a href="Reports/SummaryStatList.jsp?reqParam=${dateParams[8]}:${dateParams[9]}:${index}:Male:${summList[0]}:${dateLabels[4]}" target="_blank">${summList[9]}</a>
                                    </td>
                                    <td width="7%">
                                        <a href="Reports/SummaryStatList.jsp?reqParam=${dateParams[8]}:${dateParams[9]}:${index}:Female:${summList[0]}:${dateLabels[4]}" target="_blank">${summList[10]}</a>
                                    </td>
                                    <td width="5%">
                                        <a href="Reports/SummaryStatList.jsp?reqParam=${dateParams[10]}:${dateParams[11]}:${index}:Male:${summList[0]}:${dateLabels[5]}" target="_blank">${summList[11]}</a>
                                    </td>
                                    <td width="5%">
                                        <a href="Reports/SummaryStatList.jsp?reqParam=${dateParams[10]}:${dateParams[11]}:${index}:Female:${summList[0]}:${dateLabels[5]}" target="_blank">${summList[12]}</a>
                                    </td>
                                    <td width="5%">
                                        ${summList[1] + summList[3]+ summList[5]+ summList[7]+ summList[9]+ summList[11]}
                                    </td>
                                    <td width="5%">
                                        ${summList[2]+summList[4]+ summList[6]+ summList[8]+ summList[10]+ summList[12]}
                                    </td>
                                    <td width="5%">
                                        ${summList[1] + summList[3]+ summList[5]+ summList[7]+ summList[9]+ summList[11]+summList[2]+summList[4]+ summList[6]+ summList[8]+ summList[10]+ summList[12]}
                                    </td>
                                </tr>
                           </logic:iterate>
                            </table>
                            </td></tr>
                    </table>
            </div>

            </center>
        </body>



</html>

