<%--
    Document   : ovcdashboard
    Created on : 24-Aug-2010, 19:45:22
    Author     : HP USER
--%>


<%@page buffer="7000kb" autoFlush="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.fhi.kidmap.chart.DashboardChartGenerator" %>
<%@page import="com.fhi.kidmap.chart.dashboard.OvcSummary" %>
<%@page import="com.fhi.nomis.nomisutils.AppUtility" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>



<%
OvcSummary summary = new OvcSummary();
AppUtility util=new AppUtility();
summary.setSummaryList(request);

%>


<logic:notPresent name="dashboardDatasetList">
    <logic:forward name="login" />
</logic:notPresent>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OVC Dashboard</title>

        <link href="kidmap-default.css" rel="stylesheet" type="text/css" />
        <link href="images/kidmap2.css" rel="stylesheet" type="text/css" />

    </head>
    <div align="center">
        <body bgcolor="#666666">
        <p>
        <table width="98%" cellpadding="0" cellspacing="0" border="0" bgcolor="#FFFFFF">

            <tr>
                <td height="50" width="1%"></td><td colspan="2" style="font-family:Arial, Helvetica, sans-serif; font-size:16px;">LGA-WIDE COMMUNITY OVC PROGRAMMING DASHBOARD</td><td width="1%"></td>
            </tr>

            <tr>
                <td height="60" width="1%"></td><td colspan="2" style="font-size:14px; font-weight:bold; font-family: Arial" valign="top">Report as at <%=autil.getCurrentMthAndDate()%></td><td width="1%"></td>
            </tr>
            <tr>
                <td width="1%"></td><td colspan="2" align="right"><%--<IMG SRC="ServletDemo1" BORDER=0 WIDTH=500 HEIGHT=375/>--%>

                    <table border="0" cellpadding="0" cellspacing="0" class="regsitertable" width="500" align="center">
                        <tr><th colspan="4" align="center" height="30">
                        OVC enrolled in LGA community OVC program
                        </th></tr>
                        <tr align="center"><th height="30"></th><th>Male</th><th>Female</th><th>Total</th></tr>

                        <logic:iterate id="ovcSummaryBean" name="dashboardDatasetList">

                            <tr align="left" style="font-family: Arial, Helvetica, sans-serif;"><td style=" font-weight:bold" height="30"> <bean:write name="ovcSummaryBean" property="lga" /></td><td align="center"> <bean:write name="ovcSummaryBean" property="maleCount" /></td><td align="center"> <bean:write name="ovcSummaryBean" property="femaleCount" /></td><td align="center"> <bean:write name="ovcSummaryBean" property="total" /></td></tr>
                       </logic:iterate>

                    </table>

                </td><td width="1%"></td>
            </tr>
            <tr>
                <td width="1%" height="80"></td><td>&nbsp;</td>
                <td>&nbsp;</td><td width="1%"></td>
            </tr>
            <tr>
                <td width="1%"></td><td><IMG SRC="OvcWithThreeServices?type=ovcWithThreeServices" BORDER=0 WIDTH=500 HEIGHT=375/></td>
                <td><IMG SRC="OvcHivStatus?type=ovcHivStatus" BORDER=0 WIDTH=500 HEIGHT=375/></td><td width="1%"></td>
            </tr>
            <tr>
                <td width="1%" height="80"></td><td>&nbsp;</td>
                <td>&nbsp;</td><td width="1%"></td>
            </tr>
            <tr>
                <td width="1%"></td><td><IMG SRC="OvcOutOfSchool?type=ovcOutOfSchool" BORDER=0 WIDTH=500 HEIGHT=375/></td>
                <td><IMG SRC="OvcWithoutBirthCert?type=ovcWithoutBirthCert" BORDER=0 WIDTH=500 HEIGHT=375/></td><td width="1%"></td>
            </tr>
            <tr>
                <td width="1%" height="80"></td><td>&nbsp;</td>
                <td>&nbsp;</td><td width="1%"></td>
            </tr>
            <tr>
                <td width="1%"></td><td><IMG SRC="OvcCsiScore?type=ovcCsiScore" BORDER=0 WIDTH=500 HEIGHT=375/></td>
                <td>

                </td>
                <td width="1%"></td>
            </tr>
            <tr>
                <td width="1%" height="80"></td><td></td><td></td><td width="1%"></td>
            </tr>

        </table>

    </body>
    </div>
</html>
