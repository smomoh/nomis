<%-- 
    Document   : DataImportTrackerReport
    Created on : Sep 4, 2015, 11:16:32 AM
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
        <title>Data import tracker</title>
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

        <body style="background-image:url('images/ReportBg.jpg');">

            <html:errors/>


            <center>

            <div align="center" style=" height: 1000px; background-color: white;"><br/>

                <table border=0 width="100%">
                    <tr><td align="center" colspan="2" style=" border: none;"><label class="lab">Data import tracker</label></td> </tr>


                        <tr>
                            <td style=" border: none;">


                                <table width="70%" align="center"
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid;">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th>User Name</th>
                                <th >Date of import</th>
                                <th > File name</th>
                                <th > Response sent</th>
                                <th > Details</th>
                            </tr>


                            <!?? iterate over the results of the query ??>
                            <logic:iterate id="dit" name="ditreportList">

                                <tr align="left" bgcolor="#DDDDDD">

                                    <td width="10%">
                                        ${dit.snumber}
                                    </td>
                                    <td width="30%">
                                        ${dit.userName}
                                    </td>
                                    <td width="30%">
                                        ${dit.dateAndTime}
                                    </td>
                                    <td width="30%">
                                        ${dit.fileName}
                                    </td>
                                    <td width="30%">
                                        ${dit.responseSent}
                                    </td>
                                    <td width="30%">
                                        <a href="databaseImportTrackerReportAction.do?id=${dit.recordId}:${ditperiodParams[0]}:${ditperiodParams[1]}" target="_blank">View details</a>
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
