<%-- 
    Document   : DataImportTrackerDetails
    Created on : Sep 4, 2015, 1:02:09 PM
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

            <br/><br/>

                <table border=0 width="70%" align="center">
                    <tr><td align="center" colspan="2" style=" border: none;">
                            <label class="lab">Data import tracker</label></td> </tr>

                    <tr><td style=" border: none;"> <label style="margin-left: 10px;"> ${dituserInfo}</label> </td></tr>
                        <tr>
                            <td style=" border: none;">


                            <!?? iterate over the results of the query ??>
                            <logic:iterate id="summaryArray" name="ditsummaryList">
                                <table border="1">
                                <logic:iterate id="data" name="summaryArray">
                                <tr align="left" >
                                    <td width="10%">
                                        ${data}
                                    </td>
                                                                   
                                </tr>
                                </logic:iterate>
                             </table>
                           </logic:iterate>
                             <%--</td> </tr>
                            </table>--%>

                         </td></tr>
                      </table>
            <%--</div>--%>
            </center>
        </body>
</html>
