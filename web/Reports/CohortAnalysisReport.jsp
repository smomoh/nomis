<%-- 
    Document   : CohortAnalysisReport
    Created on : Mar 21, 2016, 9:58:35 PM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>Cohort analysis report</title>
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

        <body>

            <html:errors/>


            <center>

            <div align="center" style=" width: 800px; height: 1000px; background-color: white;"><br/>
                <label class="lab">Cohort analysis report</label><br/>

                <table border=0 style=" width: 800px;">
                    <tr><td align="left" style=" border: none;">
                <table align="left" border="0" style="border: none;">
                    <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td width="25%" style=" border: none;"><label class="orgNames">${cohortorgparam[9]}</label></td>
                    <td class="orgNamestd"><label class="lab">LGA:</label></td> <td width="30%" style=" border: none;"><label class="orgNames">${cohortorgparam[10]}</label></td>
                    <td class="orgNamestd"><label class="lab">CBO:</label></td> <td width="35%" style=" border: none;"><label class="orgNames">${cohortorgparam[11]}</label></td>
                    <td class="orgNamestd"><label class="lab">Community:</label></td> <td width="35%" style=" border: none;"><label class="orgNames">${cohortorgparam[12]}</label></td>
                    </tr>
                    <tr><td class="orgNamestd" colspan="4" align="center" ><label class="lab">${cohortperiod}</label> </td>
                        <td class="orgNamestd" colspan="2" align="center"><label class="lab"> ${cohortorgparam[4]}</label></td></tr>
                    
                </table>
                            </td>
                        </tr>
                        <%--<tr><td class="orgNamestd" style="font-size:15px; width: 300px;" align="center"><a href="summstatexcelaction.do" target="_blank">Export to Excel</a><a href="summstatpdfreport.do" style="margin-left: 20px;" target="_blank">Export to pdf</a></td></tr>--%>
                        <tr>
                            <td style=" border: none;">


                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th>Indicator Name</th>
                                <th >Male</th>
                                <th >Female</th>
                                <th >Total</th>
                            </tr>

                            <%
                                        int cnt = 0;
                                        int row =0;
                                        //int indicatorIndex=0;
                             %>
                             <logic:iterate id="stb" name="cohortreport">
                                <%
                                            cnt++;
                                            //request.setAttribute("index", ++indicatorIndex);
                                            if (cnt % 2 != 0) {
                                %>
                                <tr align="left" bgcolor="#FFFFFF">
                                    <%} else {
                                    %>
                                <tr align="left" bgcolor="#DDDDDD">
                                    <%            }
                                    %>

                                    <td width="5%"><%= ++row %></td>
                                    <td width="65%">
                                        ${stb.indicatorName}
                                    </td>
                                    <td width="10%">
                                        <a href="cohortAnalysisReportAction.do?q=${stb.indicatorId}:Male:list:${stb.categoryOptionCombo}" target="_blank">${stb.value3}</a>
                                    </td>
                                    <td width="10%">
                                        <a href="cohortAnalysisReportAction.do?q=${stb.indicatorId}:Female:list:${stb.categoryOptionCombo}" target="_blank">${stb.value4}</a>
                                    </td>
                                    <td width="10%">
                                        <a href="cohortAnalysisReportAction.do?q=${stb.indicatorId}:Both gender:list:${stb.categoryOptionCombo}" target="_blank">${stb.totNoOfOvc}</a>
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
