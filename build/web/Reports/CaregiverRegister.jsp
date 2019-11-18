<%-- 
    Document   : CaregiverRegister
    Created on : Aug 21, 2016, 3:44:19 PM
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

<logic:notPresent name="caregiverList">
    <jsp:forward page="CaregiverListParamPage.jsp" />
</logic:notPresent>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>Caregiver register</title>
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
                <label class="lab">List of caregivers <br/> </label><br/>

                <table border=0 style=" width: 800px;  background-color: white;">
                    <tr><td align="left" style=" border: none;">
                <table align="left" border="0" style="border: none;">
                    <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td width="25%" style=" border: none;"><label class="orgNames">${hviParam[0]} </label></td>
                    <td class="orgNamestd"><label class="lab">LGA:</label></td> <td width="30%" style=" border: none;"><label class="orgNames">${hviParam[1]} </label></td>
                    <td class="orgNamestd"><label class="lab">CBO:</label></td> <td width="35%" style=" border: none;"><label class="orgNames">${hviParam[2]} </label></td>
                    </tr>
                    <tr><td class="orgNamestd"><jsp:include page="../includes/WardName.jsp" /></td> <td width="35%" style=" border: none;"><label class="orgNames">${hviParam[3]} </label></td><td class="orgNamestd"><label class="lab">Partner:</label></td> <td width="35%" colspan="3" style=" border: none;"><label class="orgNames">${hviParam[4]} </label></td></tr>
                    <tr><td class="orgNamestd" style="border: none" colspan="4">${period}</td><td class="orgNamestd" style="border: none" colspan="2">${agegroup}</td></tr>
                </table>
                            </td>
                        </tr>
                        
                        
                        <tr>
                            <td style=" border: none;">


                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th >Household Id</th>
                                <th >Caregiver Id</th>
                                <th >Caregiver name</th>
                                <th >Caregiver address</th>
                                <th >Phone</th>
                                <th >Gender</th>
                                <th >Age </th>
                                <th >Occupation</th>
                                <th >HIV status</th>
                                <th >Facility enrolled</th>
                                <th >Ward</th>
                                <th >Withdrawn from program</th>
                               <th >Date of withdrawal (mm/dd/yyyy)</th>
                               <th >Reason for withdrawal</th>
                            </tr>

                            <%
                                        int cnt = 0;
                                        int row =0;
                                        int indicatorIndex=0;
                             %>
                             <logic:iterate id="cgiver" name="caregiverList">
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
                                    <td width="3%"><%= ++row %></td>
                                    <td width="5%">${cgiver.hhUniqueId}</td>
                                    <td width="5%">${cgiver.caregiverId}</td>
                                    <td width="5%">${cgiver.caregiverFullName}</td>
                                    <td width="25%">${cgiver.caregiverAddress}</td>
                                    <td width="5%">${cgiver.caregiverPhone}</td>
                                    <td width="5%">${cgiver.caregiverGender}</td>
                                    <td width="5%">${cgiver.caregiverAge}</td>
                                    <td width="5%">${cgiver.caregiverOccupation}</td>
                                    <td width="5%">${cgiver.activeHivStatus.hivStatus}</td>
                                    <td width="5%">
                                        ${cgiver.activeHivStatus.facility.facilityName}
                                    </td>
                                    <td width="5%">${cgiver.hhe.communityName}</td>
                                    
                                    <td width="5%">${cgiver.withdrawnFromProgram}</td>
                                    <td width="5%">${cgiver.dateOfWithdrawal}</td>
                                    <td width="5%">${cgiver.reasonForWithdrawal}</td>
                                    
                                </tr>
                           </logic:iterate>
                            </table>
                            </td></tr>
                    </table>
            <!--</div>-->
            </center>
        </body>
</html>