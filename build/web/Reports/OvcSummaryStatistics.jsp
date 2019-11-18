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
<logic:notPresent name="summaryStatistics">
    <jsp:forward page="SummStatParamPage.jsp" />
</logic:notPresent>


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

        <body >

            <html:errors/>


            <center>
                
            <div align="center" style=" width: 800px; height: 1000px; background-color: white;"><br/>
                <a href="summstatreport.do">Back</a>
                <label class="lab">List of indicators</label><br/>

                <table border=0 style=" width: 800px;">
                    
                    <tr><td align="left" style=" border: none;">
                <table align="left" border="0" style="border: none;">
                    <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td width="25%" style=" border: none;"><label class="orgNames">${summaryStatparams[5]}</label></td>
                    <td class="orgNamestd"><label class="lab">LGA:</label></td> <td width="30%" style=" border: none;"><label class="orgNames">${summaryStatparams[6]}</label></td>
                    <td class="orgNamestd"><label class="lab">CBO:</label></td> <td width="35%" style=" border: none;"><label class="orgNames">${summaryStatparams[17]}</label></td>
                    <td class="orgNamestd"><label class="lab"><jsp:include page="../includes/WardName.jsp" />:</label></td> <td width="35%" style=" border: none;"><label class="orgNames">${summaryStatparams[20]}</label></td>
                    </tr>
                    <tr><td class="orgNamestd" colspan="4" align="center" ><label class="lab">${period}</label> </td><td class="orgNamestd" colspan="2" align="center"><label class="lab"> ${agegroup}</label></td></tr>
                    
                    
                </table>
                            </td>
                        </tr>
                        <tr><td class="orgNamestd" style="font-size:15px; width: 300px;" align="center">
                                <a href="summstatreport.do?id=excelReportTable" target="_blank">Export table to Excel</a><a href="summstatpdfreport.do" style="margin-left: 20px;" target="_blank">Export to PDF</a>
                            </td></tr>
                        <tr>
                            <td style=" border: none;">
                        <table border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th>Indicator Name</th>
                                <th >Male</th>
                                <th >Female</th>
                                <th >Total</th>
                                <th >Download list to Excel </th>
                            </tr>

                            <%
                                        int cnt = 0;
                                        int row =0;
                                        //int indicatorIndex=0;
                             %>
                             <logic:iterate id="summList" name="summaryStatistics">
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
                                    <td width="59%">
                                        ${summList[0]}
                                    </td>
                                    <td width="7%">
                                        <a href="summstatreport.do?reqParam=${summList[0]}:${summList[4]}:Male:summstatList" target="_blank">${summList[1]}</a>
                                    </td>
                                    <td width="7%">
                                        <a href="summstatreport.do?reqParam=${summList[0]}:${summList[4]}:Female:summstatList" target="_blank">${summList[2]}</a>
                                    </td>
                                    <td width="7%">
                                        <a href="summstatreport.do?reqParam=${summList[0]}:${summList[4]}:Both gender:summstatList" target="_blank">${summList[3]} </a>
                                    </td>
                                    <td width="15%">
                                        <a href="summstatreport.do?reqParam=${summList[0]}:${summList[4]}:Both gender:dowloadInExcel" target="_blank">Download</a>
                                    </td>
                                   
                                </tr>
                                
                           </logic:iterate>
                           <logic:iterate name="orgAssessmentList" id="assessmentList">
                                <tr align="left" bgcolor="#DDDDDD">
                                    <td width="5%"><%= ++row %></td>
                                    <td width="65%" colspan="3">
                                        ${assessmentList[0]}
                                    </td>

                                    <td width="10%">
                                        <a href="summstatreport.do?reqParam=${assessmentList[0]}:${assessmentList[2]}:Both gender:summstatList" target="_blank">${assessmentList[1]} </a>
                                        
                                    </td>
                                    <td width="20%">
                                        <a href="summstatreport.do?reqParam=${assessmentList[0]}:${assessmentList[2]}:Both gender:dowloadInExcel" target="_blank">Download</a>
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

