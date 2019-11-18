<%-- 
    Document   : HouseholdMembers
    Created on : Jun 2, 2016, 4:36:23 PM
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
        <title>List of Household members</title>

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
    border: none; text-align: left; font-size:16px;
}
</style>
<script language="javascript">
function setActionName(val)
{
    document.getElementById("actionName").value=val
}
function disableControl(id)
{
    document.getElementById(id).disabled=true;
}
function confirmAction(action,val)
{
    if(confirm("Are you  sure you want to "+action+" this record"))
    {
        setActionName(val)
        return true
    }
    else
    return false
}
function confirmDelete(action)
{
    if(confirm("Are you  sure you want to "+action+" this record"))
    return true
    else
    return false
}

</script>
    </head>
    <body>
        
        <br/><br/><br/>
        <center>
            <html:form action="/householdMergeAction" method="post">
                <html:hidden property="actionName" styleId="actionName"/>
        <div align="center" style=" width: 900px; background-color: white;"><br/>

            <table align="center" border="0" style="border: none;">
                <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${hviParam[4]}   </label></td>
                <td class="orgNamestd"><label class="lab">LGA:</label></td> <td style=" border: none;" width="25%"><label class="orgNames"> ${hviParam[5]} </label></td>
                <td class="orgNamestd"><label class="lab">Organization:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${hviParam[6]}   </label></td>
                <td class="orgNamestd"><label class="lab"><jsp:include page="../includes/WardName.jsp"/></label></td> <td style=" border: none;" width="25%"><label class="orgNames">${hviParam[7]}   </label></td></tr>
             
                </table>
                                             
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                        <tr align="left" bgcolor="#D7E5F2">
                                <th>Unique id</th>
                                <th >Household head name </th>
                                <th >Address</th>
                                <th >Age</th>
                                <th >Sex(M/F)</th>
                                <th >Phone</th>
                        </tr>
                        <logic:iterate collection="${hhMemberList[0]}" id="hhe">
                            <html:hidden property="hhUniqueId" value="${hhe.hhUniqueId}" />
                        <tr>
                            <td width="10%">
                                ${hhe.hhUniqueId}
                            </td>
                            <td width="15%">
                                ${hhe.hhSurname} ${hhe.hhFirstname}
                            </td>

                            <td width="23%">
                                ${hhe.address}
                            </td>
                            <td width="3%">
                                ${hhe.hhAge}
                            </td>
                            <td width="3%">
                                ${hhe.hhGender}
                            </td>
                            <td width="8%">
                                ${hhe.phone}
                            </td>
                        </tr>
                         </logic:iterate>
                    </table>
               
                
                                                                   
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                        <tr align="left" bgcolor="#D7E5F2">
                                <th>Household unique id</th>
                                <th>Caregiver id</th>
                                <th >Caregiver name </th>
                                <th >Age</th>
                                <th >Sex(M/F)</th>
                                <th >Phone</th>
                                <th >Keep</th>
                                <th >Delete</th>
                        </tr>
                        <logic:iterate collection="${hhMemberList[1]}" id="cgiver">
                        <tr>
                            <td width="10%">
                                ${cgiver.hhUniqueId}
                            </td>
                            <td width="10%">
                                ${cgiver.caregiverId}
                            </td>
                            <td width="10%">
                                ${cgiver.caregiverFullName}
                            </td>
                            <td width="10%">
                                ${cgiver.caregiverAge}
                            </td>
                            <td width="10%">
                                ${cgiver.caregiverGender}
                            </td>
                            <td width="10%">
                                ${cgiver.caregiverPhone}
                            </td>
                            <td width="7%">
                               <html:radio property="keep" styleId="keep_${cgiver.caregiverId}" value="${cgiver.caregiverId}" onchange="disableControl(${cgiver.caregiverId})"/>Keep
                            </td>
                                    <td width="8%"><html:checkbox property="merge" styleId="${cgiver.caregiverId}" value="${cgiver.caregiverId}" />delete</td>
                        </tr>
                        </logic:iterate>
                    </table>
                
                
                                                                   
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                        <tr align="left" bgcolor="#D7E5F2">
                                <th>Household unique id</th>
                                <th>Ovc id</th>
                                <th >Ovc name </th>
                                <th >Caregiver Id </th>
                                <th >Caregiver name </th>
                                <th >Age</th>
                                <th >Sex(M/F)</th>
                                <th >Phone</th>
                                <th >Action</th>
                        </tr>
                        <logic:iterate collection="${hhMemberList[2]}" id="ovc">
                        <tr>
                            <td width="10%">
                                ${ovc.hhUniqueId}
                            </td>
                            <td width="10%">
                                ${ovc.ovcId}
                            </td>
                            <td width="10%">
                                ${ovc.fullName}
                            </td>
                            <td width="10%">
                                ${ovc.caregiverId}
                            </td>
                            <td width="10%">
                                ${ovc.caregiverName}
                            </td>
                            <td width="10%">
                                ${ovc.age}
                            </td>
                            <td width="10%">
                                ${ovc.gender}
                            </td>
                            <td width="10%">
                                ${ovc.phone}
                            </td>
                            <td width="10%">
                                <a href="householdMergeAction.do?uid=${ovc.ovcId}" onclick="return confirmDelete('delete')">delete</a>
                            </td>
                        </tr>
                        </logic:iterate>
                        
                    </table>
                
            </div>
                <table border="0" cellspacing="0" cellpadding="0" style="border:none">
                    <tr><td align="center" style="border:none"><html:submit property="caregiverMergeBtn" value="Merge caregivers" onclick="return confirmAction('merge','mergecgiverRecords')" style="width: 150px; margin-left: 50px;"/></td></tr>
                </table>
                    </html:form>
            </center>
        
    </body>
</html>
