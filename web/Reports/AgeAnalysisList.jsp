<%-- 
    Document   : AgeAnalysisList
    Created on : Jul 3, 2015, 5:00:30 PM
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
	/*margin: 10px;*/
}
.lab
{
    font-family:  arial, sans-serif;
    font-size: 12px;
    font-weight: bold;
    letter-spacing: 3px;
}
.orgNames
{
    font-family:  arial, sans-serif;
    font-size: 12px;
    letter-spacing: 1px;
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
            <div align="center" style=" width: 950px; height: 2000px; background-color: white;">
                <br/>
                <div>
                    <label class="orgNames">Age Analysis</label><br/>&nbsp;
                <table border=0 style=" width: 950px;">
                <tr><td align="left" style=" border: none;">

                </td>
                        </tr>
                        <tr>
                            <td style=" border: none;">

                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th>OVC Id</th>
                                <th>Surname</th>
                                <th >Other Names</th>
                                <th >Date of enrollment</th>
                                <th >Age at enrollment</th>
                                <th >Age unit</th>
                                <th >Current age</th>
                                <th >Gender</th>
                                <th >Address</th>
                                <th >State</th>
                                <th >Phone</th>

                            </tr>

                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <!?? iterate over the results of the query ??>
                            <logic:iterate id="ovc" name="ovcAgeAnalysisList">

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

                                    <td width="15%">
                                        ${ovc.serialNo}
                                    </td>
                                    <td width="15%">
                                        ${ovc.ovcId}
                                    </td>
                                    <td width="10%">
                                     ${ovc.lastName}
                                    </td>
                                    <td width="8%">
                                        ${ovc.firstName}
                                    </td>
                                    <td width="8%">
                                        ${ovc.dateEnrollment}
                                    </td>
                                    <td width="3%">
                                    ${ovc.age}
                                    </td>
                                    <td width="3%">
                                    ${ovc.ageUnit}
                                    </td>
                                    <td width="3%">
                                    ${ovc.currentAge}
                                    </td>
                                    <td width="4%">
                                    ${ovc.gender}
                                    </td>
                                    <td width="25%">
                                    ${ovc.address}
                                    </td>
                                    <td width="14%">
                                    ${ovc.state}
                                    </td>
                                    <td width="5%">
                                    ${ovc.phone}
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


