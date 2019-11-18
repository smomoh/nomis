<%-- 
    Document   : FollowupSurveyList
    Created on : Aug 18, 2011, 3:27:58 PM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Follow up assessment</title>

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
    <body>
       
        <center>

            <div align="center" style=" width: 900px; background-color: white;"><br/>

                <table border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
           <tr align="left" bgcolor="#D7E5F2">
               <td>S/No. </td><td>OVC Id </td><td >Date of survey </td><td >VC Address </td><td >VC Age</td><td>HIV status</td><td >Has birth certificate</td><td >Name of school</td><td>Caregiver name</td><td >Caregiver address</td><td >Caregiver phone</td><td ></td>
         </tr>
                            <%
                                        int row =0;
                            %>

                            <logic:iterate name="followup" id="record">
                               <c:choose>
                                    <c:when test="${color=='#DDDDDD'}">
                                        <c:set var="color" scope="session" value="#FFFFFF"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="color" scope="session" value="#DDDDDD"/>
                                    </c:otherwise>
                                </c:choose>
                                    <tr align="left" bgcolor="${color}">
                                      <td width="3%"><%= ++row %></td><td>${record.ovcId} </td><td >${record.dateOfSurvey} </td><td >${record.updatedAddress} </td><td >${record.updatedAge} ${record.updatedAgeUnit}</td><td>${record.updatedHivStatus}</td><td >${record.updatedBirthCertStatus}</td><td >${record.updatedSchoolName}</td><td>${record.updatedCaregiverName}</td><td >${record.updatedCaregiverAddress}</td><td >${record.updatedCaregiverPhone}</td>
                                      <td ><a href="ovcFollowupSurveyReportAction.do?req=${record.ovcId}" target="_blank">Compare CSI values</a></td>
                                    </tr>
                                    </logic:iterate>

                           </table>

            </div>
            </center>
        
    </body>
</html>
