<%-- 
    Document   : CareplanGraduationList
    Created on : Sep 26, 2017, 9:25:27 AM
    Author     : smomoh
--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Care plan graduation list</title>
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
<script language="javascript">
function setActionName(val)
{
    document.getElementById("actionName").value=val
}
function selectChkBoxes(chkname)
{
   var elements=document.getElementsByName(chkname)
    for(var i=0; i<elements.length; i++)
    {
        elements[i].checked=true
    }
}
function unselectChkBoxes(chkname)
{
   var elements=document.getElementsByName(chkname)
    for(var i=0; i<elements.length; i++)
    {
        elements[i].checked=false
    }
}
        </script>
    </head>
<body>
    <html:form action="/careplanachievementreport" method="post">
                <html:hidden property="actionName" styleId="actionName" />
    <div align="center">
<div style=" font-family: arial" align="center">Care plan achievement register </div>

<table border=0>
    
    <tr><td style="border: none; text-align: left;">
            <table align="left" border="0" style="border: none;" width="100%">
                <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${cpaParam[12]}   </label></td>
                <td class="orgNamestd"><label class="lab">LGA:</label></td> <td style=" border: none;" width="25%"><label class="orgNames"> ${cpaParam[4]} </label></td>
                <td class="orgNamestd"><label class="lab">Organization:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${cpaParam[5]}   </label></td>
                <td class="orgNamestd"><label class="lab"><jsp:include page="../includes/WardName.jsp" /></label></td> <td style=" border: none;" width="25%"><label class="orgNames">${cpaParam[11]}   </label></td></tr>
                <tr><td class="orgNamestd" style=" font-size: 14px" colspan="4" align="center" width="50%"> Partner</td><td class="orgNamestd" style=" font-size: 14px" colspan="4" align="center" width="50%"> ${cpaParam[13]}</td> </tr>
                </table>
        </td></tr>

    <tr><td style="border: none; text-align: left">
            
                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th >Date of assessment (mm/dd/yyyy)</th>
                                <th>Unique id</th>
                                <th><jsp:include page="../includes/WardName.jsp" /></th>
                                <th >Household head name </th>
                                <th >Sex(M/F)</th>
                                <th >Age</th>
                                <th >Marital status</th>
                                <th >Address</th>
                                
                                <th >Phone</th>
                                
                                
                                <th >Total score </th>
                                <th >Marked for graduation?</th>
                                
                                <th >Partner</th>
                               <th >Recorded by</th>
                               <th >Date of entry (mm/dd/yyyy)</th>
                               <th >Mark for graduation</th>
                            </tr>
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <logic:iterate id="hviData" name="cpareportList">

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

                                    <td width="3%"><%= ++row %></td>
                                    <td width="10%">
                                        ${hviData.dateOfAssessment}
                                    </td>
                                    <td width="20%">
                                       ${hviData.clientId}
                                    </td>
                                    <td width="20%">
                                       ${hviData.hhe.communityName}
                                    </td>
                                    <td width="10%">
                                        ${hviData.hhe.hhSurname} ${hviData.hhe.hhFirstname}
                                    </td>
                                    <td width="3%">
                                        ${hviData.hhe.hhGender}
                                    </td>
                                    <td width="3%">
                                        ${hviData.hhe.hhAge}
                                    </td>
                                    <td width="3%">
                                        ${hviData.hhe.maritalStatus}
                                    </td>
                                    <td width="20%">
                                        ${hviData.hhe.address}
                                    </td>
                                    
                                    
                                    <td width="20%">
                                        ${hviData.hhe.phone}
                                    </td>
                                    
                                    
                                    <td width="3%">
                                        ${hviData.score}
                                    </td>
                                    <td width="3%">
                                        ${hviData.graduated}
                                    </td>
                                    
                                    
                                    <td width="10%">
                                        ${hviData.hhe.partnerCode}
                                    </td>
                                    
                                   <td width="4%">${hviData.recordedBy}</td>
                                   <td width="4%">${hviData.lastModifiedDate}</td>
                                   <td width="8%"><html:checkbox property="householdsToGraduate" styleId="${hviData.clientId}" value="${hviData.clientId}"/></td>
                                   
                                </tr>
                            </logic:iterate>
                                
                            </table>
        </td></tr>
    <tr><td style="border: none;">
                                <input type="button" value="Select all" onclick="selectChkBoxes('householdsToGraduate')" />
                                <input type="button" value="Unselect all" onclick="unselectChkBoxes('householdsToGraduate')" />
                            </td></tr>
    
</table>
    
        </div>
   <center>
    <table border="0" align="center">
        
        <tr><td  style="border: none;"><html:submit value="Graduate selected households" onclick="setActionName('graduate')" style="width: 210px; margin-left: 70px;"/> </td></tr>
    </table>
    </center>
    </html:form>
</body>
</html>
