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

<logic:notPresent name="schoolAttendanceCount">
    <jsp:forward page="SchoolAttendanceParamPage.jsp" />
</logic:notPresent>


<%@page import="com.fhi.kidmap.report.SchoolRecords;" %>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>School Attendace Summary</title>
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
    border: none; text-align: left; width: 80px;
}
</style>
    </head>

    

        <body style="background-image:url('images/ReportBg.jpg');">
<%
    //SchoolRecords record=new SchoolRecords();
    //record.getSchoolAttendaceCount(session);
%>

<html:errors/>

            <center>
            <div align="center" style=" width: 800px; background-color: white;">
                <div>
                    <br/>
                <label class="lab" style="margin-right:13px;">School Attendance Summary</label><br/>

          <table border=0 style=" width: 800px;">
                    <tr><td align="left" style=" border: none;">
                <table align="left" border="0" style="border: none;">
                     <tr><td class="orgNamestd" ><label class="lab">State:</label></td> <td width="25%" style=" border: none;"><label class="orgNames">${schAttenparams[11]}</label></td>
                         <td class="orgNamestd"><label class="lab">LGA:</label></td> <td width="25%" style=" border: none;"><label class="orgNames">${schAttenparams[12]}</label></td>
                         <td class="orgNamestd" ><label class="lab">Partner:</label></td> <td width="30%" style=" border: none;"><label class="orgNames">${schAttenparams[13]}</label></td>
                     </tr>
                     
                     <tr><td class="orgNamestd" colspan="6" > &nbsp;</td> </tr>

                     <tr><td class="orgNamestd" colspan="6" style="width: 300px;" ><a href="schAttendanceExcelData.do" style="font-size: 15px;" target="_blank">Export to Excel</a><a href="schattendancepdfreport.do" style="margin-left: 20px; font-size: 15px;" target="_blank">Export to PDF</a></td></tr>
                </table>
                </td>
                        </tr>
                        <tr>
                            <td style=" border: none;">
                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th>School name</th>
                                <th>Address</th>
                                <th>No. of OVC</th>
                                                                
                            </tr>
                            
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <!?? iterate over the results of the query ??>
                            <logic:iterate id="ovcSchAttendCount" name="schoolAttendanceCount">

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

                                    <td width="3%"><%= ++row %></td>
                                    <td width="15%">
                                        ${ovcSchAttendCount[0]}
                                    </td>
                                    <td width="15%">
                                        ${ovcSchAttendCount[1]}
                                    </td>
                                    <td width="3%">
                                       <a href="Reports/SchoolAttendanceReport.jsp?schoolName=${ovcSchAttendCount[0]}" target="_blank"> ${ovcSchAttendCount[2]}</a>
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

