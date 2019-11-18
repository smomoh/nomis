<%-- 
    Document   : CaregiverList
    Created on : Apr 28, 2011, 10:10:44 AM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<logic:notPresent name="caregiverlist">
    <jsp:forward page="CaregiverListParamPage.jsp" />
</logic:notPresent>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>List of caregivers</title>
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

            <!--<div align="center" style=" width: 800px; height: 1000px; background-color: white;"><br/>-->
                <label class="lab">List of caregivers and number of VC <br/> </label><br/>

                <table border=0 style=" width: 800px;  background-color: white;">
                    <tr><td align="left" style=" border: none;">
                <table align="left" border="0" style="border: none;">
                    <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td width="25%" style=" border: none;"><label class="orgNames">${caregiverlistparam[14]} </label></td>
                    <td class="orgNamestd"><label class="lab">LGA:</label></td> <td width="30%" style=" border: none;"><label class="orgNames">${caregiverlistparam[11]} </label></td>
                    <td class="orgNamestd"><label class="lab">CBO:</label></td> <td width="35%" style=" border: none;"><label class="orgNames">${caregiverlistparam[12]} </label></td>
                    </tr>
                    <tr><td class="orgNamestd"><jsp:include page="../includes/WardName.jsp" /></td> <td width="35%" style=" border: none;"><label class="orgNames">${caregiverlistparam[15]} </label></td><td class="orgNamestd"><label class="lab">Partner:</label></td> <td width="35%" colspan="3" style=" border: none;"><label class="orgNames">${caregiverlistparam[13]} </label></td></tr>
                </table>
                            </td>
                        </tr>
                        <tr>
                            <td style=" border: none;">


                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th >Caregiver Id</th>
                                <th >Caregiver name</th>
                                <th >Caregiver address</th>
                                <th >Baseline HIV status</th>
                                <th >Current HIV status</th>
                                <th >Date of current status</th>
                                <th >Point of update of current status</th>
                                <th >Enrolled in care</th>
                                <th >Enrolled on ART</th>
                                
                                <th >No. of OVC</th>
                            </tr>

                            <%
                                        int cnt = 0;
                                        int row =0;
                                        int indicatorIndex=0;
                             %>
                             <logic:iterate id="listofcaregiver" name="caregiverlist">
                                <%
                                            cnt++;
                                            request.setAttribute("index", ++indicatorIndex);
                                            if (cnt % 2 != 0) {
                                %>
                                <tr align="left" bgcolor="#FFFFFF">
                                    <%} else {
                                    %>
                                <tr align="left" bgcolor="#DDDDDD">
                                    <%            }
                                    %>
                                    
                                    <td width="5%">${listofcaregiver[0]}</td>
                                    <td width="5%">${listofcaregiver[1]}</td>
                                    <td width="10%">${listofcaregiver[2]}</td>
                                    <td width="10%">${listofcaregiver[3]}</td>
                                    
                                    <td width="5%">${listofcaregiver[4]}</td>
                                    <td width="5%">${listofcaregiver[5]}</td>
                                    <td width="5%">${listofcaregiver[6]}</td>
                                    <td width="5%">${listofcaregiver[7]}</td>
                                    <td width="5%">${listofcaregiver[8]}</td>
                                    <td width="5%">${listofcaregiver[9]}</td>
                                    
                                    <%--<td width="10%">
                                        <a >${listofcaregiver[2]}</a>
                                    </td>
                                    <td width="10%">
                                        <a>${listofcaregiver[3]}</a>
                                    </td>--%>
                                    <td width="10%">
                                        <a href="listofcaregiver.do?id=${listofcaregiver[1]}&name=ovc" target="_blank">${listofcaregiver[10]}</a>
                                    </td>

                                </tr>
                           </logic:iterate>
                            </table>
                            </td></tr>
                    </table>
            <!--</div>-->

            </center>
        </body>



</html>

