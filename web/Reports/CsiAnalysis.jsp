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
<logic:notPresent name="ovcCount">
    <jsp:forward page="CsiParamPage.jsp" />
</logic:notPresent>

<%--<%@page import="com.fhi.kidmap.report.OvcRecords;" %>--%>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>CSI Analysis</title>
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

            <div align="center" style=" width: 800px; height: 1000px; background-color: white;"><br/>
                    
                <table border=0 style=" width: 800px;">
                    <tr><td align="center" colspan="2" style=" border: none;"><label class="lab">CSI analysis ${subtitle} <br> ${agetitle}</label></td> </tr>
                    <tr><td align="left" style=" border: none;">
                <table align="left" border="0" style="border: none;">
                    <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td width="21%" style=" border: none;"><label class="orgNames">${params[11]}</label></td>
                    <td class="orgNamestd"><label class="lab">LGA:</label></td> <td width="25%" style=" border: none;"><label class="orgNames">${params[12]}</label></td>
                    <td class="orgNamestd"><label class="lab">CBO:</label></td> <td width="25%" style="border: none;"><label class="orgNames">${params[13]}</label></td>
                    <td class="orgNamestd"><label class="lab"><jsp:include page="../includes/WardName.jsp" /></label></td> <td width="25%" style="border: none;"><label class="orgNames">${params[15]}</label></td></tr>
                    <tr><td colspan="4" class="orgNamestd">&nbsp;</td></tr>
                    <tr><td colspan="4" class="orgNamestd" align="center" style="width: 300px;"><a href="csiExcelData.do" target="_blank">Export to Excel</a><a href="csianalysispdf.do" style="margin-left: 20px;" target="_blank">Export to pdf</a></td></tr>
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
                                <th >Good</th>
                                <th >Fair</th>
                                <th >Bad</th>
                                <th >Very bad</th>
                                <th >Total</th>
                            </tr>
                            
                            <%
                                        int cnt = 0;
                                        int row =0;
                                        int factor=0;
                                        int[] csiScore={1,2,3,4};
                                        request.setAttribute("score", csiScore);
                            %>
                            <!?? iterate over the results of the query ??>
                            <logic:iterate id="csiScores" name="ovcCount">
                                

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
                                        ${csiScores[4]} 
                                    </td>
                                    <td width="10%">
                                        <a href="csireport.do?csiIndex=${factor}:${score[3]}" target="_blank">${csiScores[3]}</a>
                                    </td>

                                    <td width="10%">
                                    <a href="csireport.do?csiIndex=${factor}:${score[2]}" target="_blank">${csiScores[2]}</a>
                                    </td>
                                    <td width="7%">
                                    <a href="csireport.do?csiIndex=${factor}:${score[1]}" target="_blank">${csiScores[1]}</a>
                                    </td>
                                    <td width="7%">
                                    <a href="csireport.do?csiIndex=${factor}:${score[0]}" target="_blank">${csiScores[0]}</a>
                                    </td>
                                    <td width="7%">
                                    ${csiScores[0]+csiScores[1]+csiScores[2]+csiScores[3]}
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

