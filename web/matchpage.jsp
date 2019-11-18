<%--
    Document   : ovclist_1
    Created on : 05-Apr-2010, 17:22:10
    Author     : HP USER
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


<logic:notPresent name="ovcMatches">
    <logic:forward name="login" />
</logic:notPresent>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>List of OVC Matches</title>
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


        <script language="javascript">

            function doReset() {
            
                var parentWindow = window.opener;
                parentWindow.resetFieldValues()
                //resetAllFields(var1);
                window.close();
            }
        </script>
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
</style>



    </head>

    <div align="center">

        <body>
          

            <html:errors/>
            
                        <table width="95%"
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#FFFFFF">
                                <th width="6%">SNo.</th>
                                <th width="20%">OVC Unique ID No.</th>
                                <th width="20%">Date of Enrollment</th>
                                <th width="16%">Surname</th>
                                <th width="20%">Other Names</th>
                                <th width="6%">Age</th>
                                <th width="6%">Age Unit</th>
                                <th width="6%">Gender</th>

                            </tr>
                            <tr bgcolor="#FFFFFF">
                                <td height="10"></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <!?? iterate over the results of the query ??>
                            <logic:iterate id="ovcObj" name="ovcMatches">

                                <%
                                            cnt++;
                                            if (cnt % 2 != 0) {
                                %>
                                <tr align="left" bgcolor="#D7E5F2">
                                    <%} else {
                                    %>
                                <tr align="left" bgcolor="#FFFFFF">
                                    <%            }
                                    %>

                                    <%--<tr align="left" bgcolor="">--%>

                                    <td width="6%"><%= ++row %></td>
                                    <td width="20%">
                                       <bean:write name="ovcObj" property="ovcId" />
                                    </td>
                                    
                                    <td width="20%">
                                        <bean:write name="ovcObj" property="dateEnrollment" />
                                    </td>
                                    <td width="16%">
                                        <bean:write name="ovcObj" property="lastName" />
                                    </td>
                                    <td width="20%">
                                        <bean:write name="ovcObj" property="firstName" />
                                    </td>
                                    <td width="6%">
                                        <bean:write name="ovcObj" property="age" />
                                    </td>
                                    <td width="6%">
                                        <bean:write name="ovcObj" property="ageUnit" />
                                    </td>
                                    <td width="6%"><bean:write name="ovcObj" property="gender" /></td>

                                </tr>
                            </logic:iterate>
                            <tr>
                                <td colspan="8">
                                    &nbsp;
                                </td>
                            </tr>

                            <tr bgcolor="#FFFFFF">
                                <td height="10"></td>
                                <td><a href="#" style="color:green;" onclick="window.close();">IGNORE</a> | <a href="#" style="color:red;" onclick="doReset();">RESET FORM</a></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>

                        </table>
           

                       
    </body>

    </div>

</html>
