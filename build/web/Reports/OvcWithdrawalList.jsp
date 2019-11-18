<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>

<logic:notPresent name="withdrwalList">
    <jsp:forward page="OvcWithdrawalDatePage.jsp" />
</logic:notPresent>


<%@page import="com.fhi.kidmap.report.ServiceRecords" %>

<%
    ServiceRecords records=new ServiceRecords();
    records.removeFromWithdrawalList(session,request.getParameter("ovcId"));
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>Withdrawal list</title>
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

    <div align="center">

        <body>
<div style=" font-family: arial" align="center">List of beneficiaries withdrawn from the program between ${withdrawalDate[0]} and ${withdrawalDate[1]}</div>
<table border=0>
    <tr><td style="border: none; text-align: left;">
            <table align="left" border="0" style="border: none;">
                <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td style=" border: none;" width="20%"><label class="orgNames">${withdrawalOrgParam[0]} </label></td>
                <td class="orgNamestd"><label class="lab">LGA:</label></td> <td style=" border: none;" width="22%"><label class="orgNames">${withdrawalOrgParam[1]}</label></td>
                <td class="orgNamestd"><label class="lab">CBO:</label></td> <td style=" border: none;" width="22%"><label class="orgNames">${withdrawalOrgParam[2]}</label></td>
                <td class="orgNamestd"><label class="lab">Partner:</label></td> <td style=" border: none;" width="22%"><label class="orgNames">${withdrawalOrgParam[8]}</label></td>
                </table>
        </td></tr>
    <tr><td style="border: none; text-align: left;">
                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo.</th>
                                <th >Unique ID No.</th>
                                <th >Surname</th>
                                <th >Other Names</th>
                                <th >Age</th>
                                <th >Gender</th>
                                <th >Date of withdrawal</th>
                                <th >Reason for withdrawal</th>
                                <th > </th>
                            </tr>
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <!?? iterate over the results of the query ??>
                            <logic:iterate id="withdrwallist" name="withdrwalList">

                                <%
                                            cnt++;
                                            if (cnt % 2 != 0) {
                                %>
                                <tr align="left" bgcolor="#FFFFFF">
                                    <%} else {
                                    %>
                                <tr align="left" bgcolor="#D7E5F2">
                                    <%            }
                                    %>

                                    <%--<tr align="left" bgcolor="">--%>

                                    <td width="1%"><%= ++row %></td>
                                    <td width="10%">
                                        ${withdrwallist.ovcId}
                                    </td>
                                    <td width="6%"> ${withdrwallist.lastName}</td>
                                    <td width="8%">${withdrwallist.firstName} </td>
                                    <td width="6%"><bean:write name="withdrwallist" property="age" /> ${withdrwallist.ageUnit}</td>
                                    <td width="8%"><bean:write name="withdrwallist" property="gender" /> </td>

                                    <td width="4%"> <bean:write name="withdrwallist" property="dateOfWithdrawal" /></td>
                                    <td width="3%"><bean:write name="withdrwallist" property="reasonWithdrawal" /> </td>
                                    <td width="3%"><a href="Reports/ConfirmDeletePage.jsp?ovcId=${withdrwallist.ovcId}">Remove</a> </td>
                                </tr>
                            </logic:iterate>
                            </table>
        </td></tr></table>

    </body>

    </div>

</html>
