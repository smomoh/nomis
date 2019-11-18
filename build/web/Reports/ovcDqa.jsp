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


--%>

<%@page import="com.fhi.kidmap.report.OvcRecords;" %>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>Data quality assessment</title>
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
<%
    //OvcRecords record=new OvcRecords();
    //record.doDqa(session);
%>

            <html:errors/>


            <center>

            <div align="center" style=" width: 800px; height: 1000px; background-color: white;"><br/>

                <table border=0 style=" width: 800px;">
                    <tr><td align="centre" colspan="2" style=" border: none;"><label class="lab">Data quality assessment </label></td> </tr>
                    <tr><td align="left" style=" border: none;">
                <table align="left" border="0" style="border: none;">
                    <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td style=" border: none;"><label class="orgNames">${dqaOrgParams[0]} </label></td>
                        <td class="orgNamestd"><label class="lab">LGA:</label></td> <td  style=" border: none;"><label class="orgNames">${dqaOrgParams[1]} </label></td>
                        <td class="orgNamestd"><label class="lab">Organization:</label></td> <td style=" border: none;"><label class="orgNames">${dqaOrgParams[2]} </label>
                        
                    </tr>
                    <tr><td class="orgNamestd"><label class="lab">Partner:</label></td> <td style=" border: none;" colspan="3"><label class="orgNames">${dqaOrgParams[3]} </label></td></td></tr>

                    </table>
                            </td>
                        </tr>

                        <tr>
                            <td style=" border: none;">


                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th>Indicator Name</th>
                                <th >Value</th>
                            </tr>

                            <%
                                        int cnt = 0;
                                        int row =0;
                                        int factor=0;
                            %>
                            <!?? iterate over the results of the query ??>
                            <logic:iterate id="dqaList" name="dqaCount">

                                <%
                                    request.setAttribute("factor", ++factor);
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
                                    <td width="30%">
                                        ${dqaList[0]}
                                    </td>
                                    <td width="10%">
                                        <a href="Reports/DqaList.jsp?dqaIndex=${factor}" target="_blank">${dqaList[1]}</a>
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

