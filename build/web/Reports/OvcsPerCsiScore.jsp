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

<%--<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>
--%>

<%@page import="com.fhi.kidmap.report.OvcRecords;" %>

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
                    <label class="orgNames">List of OVC with ${indicatorName} <br> ${agetitle}</label><br/>&nbsp;
                <table border=0 style=" width: 950px;">
                <tr><td align="left" style=" border: none;">
                <table align="left" border="0" style="border: none;">
                <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td width="21%" style=" border: none;"><label class="orgNames">${params[11]}</label></td>
                <td class="orgNamestd"><label class="lab">LGA:</label></td> <td width="25%" style=" border: none;"><label class="orgNames">${params[12]}</label></td>
                <td class="orgNamestd"><label class="lab">CBO:</label></td> <td width="50%" style=" border: none;"><label class="orgNames">${params[13]}</label></td>
                <td class="orgNamestd"><label class="lab"><jsp:include page="../includes/WardName.jsp"/></label></td> <td width="50%" style=" border: none;"><label class="orgNames">${params[15]}</label></td></tr>
                </table>
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
                                <th >Age</th>
                                <th >Gender</th>
                                <th >Address</th>
                                <th >Community</th>
                                <th >Phone</th>

                            </tr>
                            
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <!?? iterate over the results of the query ??>
                            <logic:iterate id="csiOvcs" name="csiOvcList">

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

                                    <td width="1%"><%= ++row %></td>
                                    <td width="15%">
                                        ${csiOvcs.ovcId}
                                    </td>
                                    <td width="10%">
                                     ${csiOvcs.lastName}
                                    </td>
                                    <td width="8%">
                                        ${csiOvcs.firstName}
                                    </td>
                                    <td width="3%">
                                    ${csiOvcs.currentAge}
                                    </td>
                                    <td width="4%">
                                    ${csiOvcs.gender}
                                    </td>
                                    <td width="25%">
                                    ${csiOvcs.address}
                                    </td>
                                    <td width="14%">
                                    ${csiOvcs.ward}
                                    </td>
                                    <td width="5%">
                                    ${csiOvcs.phone}
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

