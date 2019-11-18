<%-- 
    Document   : HouseholdChangeOperation
    Created on : Feb 1, 2017, 6:58:10 AM
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
        <link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="js/Enrollmentjsfile.js"></script>
        <title>Change Household </title>
        <style type="text/css">
<%--
a {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #333333;
	font-weight: bold;
}
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
	color: #000000;
}
a:hover {
	text-decoration: underline;
	color: #0075B6;
}
a:active {
	text-decoration: none;
	color: #000000;
}
.title
{
  color: #FFFFFF;
  font-weight: bold;
}
.orglabel
{
    color: #FFFFFF;
}

--%>
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
        border-top: 1px solid black;
	border-left: 1px solid black;
        border-right: 1px solid black;
	border-bottom: 1px solid black;
	font-size: 12px;
	color: black;
}
th {
	padding-left: 11px;
	padding-right: 11px;
        border-top: 1px solid black;
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
function stateChanged()
{
	if (xmlhttp.readyState==4)
	{
		var lgas=xmlhttp.responseText;
                alert(lgas)
                if(lgas==" " || lgas=="" || lgas==";" || lgas==null)
                return false;
                //alert(callerId)
            var values=lgas.split(";")
            
            if(callerId=="partofnamestring")
                document.getElementById("ovcNames").innerHTML=lgas
            else if(callerId=="searchCaregiverInHousehold")
            {
                document.getElementById("caregiverSurnameNameSpan").innerHTML=lgas
            }
            else if(callerId=="searchCaregiverById")
            {
                populateCaregiverInfo(values)
            }
            
	}
        else
        {
            //alert("error "+xmlhttp.responseText)
        }
}
function filterHouseholdRecords(serialNo)
{
    if(serialNo=="" || serialNo==" " || serialNo==null)
    return
    req="partofnamestring;"+serialNo
    getValuesFromDb('addcbo.do',req,'households')
}
function setActionName(val)
{
    document.getElementById("actionName").value=val
}
function confirmChange(action)
{
    if(confirm("Are you  sure you want to "+action+" this record"))
    return true
    else
    return false
}
        </script>
    </head>
    <body style="background-color: #EBF0F4">
        <br/><br/>
        <center>
            <html:form action="/hhChangeOperationAction" method="post">
                <html:hidden property="actionName" styleId="actionName"/>
                
        <span>
        <center>
        <table style="background-color: #eeeeee">
            
            <tr><td  align="left">State: </td><td colspan="3">
                    <html:hidden property="stateCode" styleId="stateCode" value="${state.state_code}" />
                    <label>${state.name}</label>
                    <%--<html:select property="stateCode" styleId="stateCode" style="width: 290px;" onchange="setActionName('lga'); forms[0].submit()">
                       <html:option value="${state.state_code}">${state.name}</html:option> 
                    </html:select>--%> </td></tr>
            
            
                        </table>
                        
            
             <br/><br/>
             
                 
             <div align="center" style=" width: auto; background-color: #EBF0F4">
                 <fieldset ><legend>Details of new Household beneficiary is to migrate</legend>
                 <table><tr><td align="left">LGA </td><td colspan="3">
                    <html:select property="lgaCode" styleId="lgaCode" style="width: 290px;" onchange="setActionName('cbo'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:iterate id="lgaObj" name="lgaListInOrgRecords">
                        <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                        </logic:iterate>
                        </html:select> </td></tr>
            <tr><td  align="left">CBO </td><td colspan="3">
                    <html:select property="orgCode" styleId="orgCode" style="width: 290px;">
                        <html:option value="All">All</html:option>
                    <logic:iterate id="cboObj" name="hhOrgList">
                      <html:option value="${cboObj.orgCode}">${cboObj.orgName}</html:option>
                        </logic:iterate>
                    </html:select> </td>
            </tr>
            
                
                            <tr><td align="left">New Household Serial number </td><td colspan="2">
                    <html:text property="serialNo" styleId="serialNo" style="width: 40px;" />
                    <html:submit value="Get new Household" onclick="setActionName('newhhedetails');" />
                        </td>
            </tr></table>
                 <logic:present name="newHhMemberList">
                
             <table width="80%" border="1" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:20px; border-collapse: collapse">
                        <tr align="left" bgcolor="#D7E5F2">
                                <th>Unique id</th>
                                <th >Household head name </th>
                                <th >Address</th>
                                <th >Age</th>
                                <th >Sex(M/F)</th>
                                <th >Phone</th>
                                <th >Household members</th>
                                                                
                        </tr>
                        <logic:iterate collection="${newHhMemberList[0]}" id="hhe">
                            <html:hidden property="newHhUniqueId"  />
                        <tr>
                            <td width="10%">
                                ${hhe.hhUniqueId}
                            </td>
                            <td width="25%">
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
                            <td width="10%">
                                ${hhe.phone}
                            </td>
                            <td width="16%" colspan="3">
                                <a href="householdMergeAction.do?id=${hhe.hhUniqueId}" target="_blank"> View members</a>
                            </td>
                            
                            
                        </tr>
                         </logic:iterate>
                    </table>
               
                
                    </fieldset>
             
             </logic:present>
             <br/>
             
                 <fieldset ><legend>Details of Current Household of beneficiary</legend>
                 <table >
                     <tr><td align="left">LGA </td><td colspan="3">
                    <html:select property="curlgaCode" styleId="curlgaCode" style="width: 290px;" onchange="setActionName('curcbo'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:iterate id="lgaObj" name="lgaListInOrgRecords">
                        <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                        </logic:iterate>
                        </html:select> </td></tr>
            <tr><td align="left">CBO </td><td colspan="3">
                    <html:select property="curorgCode" styleId="curorgCode" style="width: 290px;">
                        <html:option value="All">All</html:option>
                    <logic:iterate id="cboObj" name="curhhOrgList">
                      <html:option value="${cboObj.orgCode}">${cboObj.orgName}</html:option>
                        </logic:iterate>
                    </html:select> </td>
            </tr>
            <tr><td align="left">Current Household Serial number </td><td colspan="2">
                                    <html:text property="curhhSerialNo" styleId="curhhSerialNo" style="width: 40px;" />
                                    <html:submit value="Get Current Household" onclick="setActionName('curhhdetails');" />
                        </td>
                        
                     </tr>
                 </table>
                        <br/>
                 <logic:present name="curHhMemberList">
                 
                     
             <table width="80%" border="1" cellspacing="0" cellpadding="0" style="border:1px black solid; border-collapse: collapse; margin-bottom:20px;">
                        <tr align="left" bgcolor="#D7E5F2">
                                <th>Unique id</th>
                                <th >Household head name </th>
                                <th >Address</th>
                                <th >Age</th>
                                <th >Sex(M/F)</th>
                                <th >Phone</th>
                        </tr>
                        <logic:iterate collection="${curHhMemberList[0]}" id="hhe">
                            <html:hidden property="curhhUniqueId" />
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
                                                         
                    <table  border="1" cellspacing="0" cellpadding="0" style="border:1px black solid; border-collapse: collapse; margin-bottom:20px">
                        <tr align="left" bgcolor="#D7E5F2">
                                <th>Household unique id</th>
                                <th>Caregiver id</th>
                                <th >Caregiver name </th>
                                <th >Age</th>
                                <th >Sex(M/F)</th>
                                <th >Phone</th>
                                <th>Action</th>
                        </tr>
                        <logic:iterate collection="${curHhMemberList[1]}" id="cgiver">
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
                            <td width="5%">
                                ${cgiver.caregiverAge}
                            </td>
                            <td width="8%">
                                ${cgiver.caregiverGender}
                            </td>
                            <td width="8%">
                                ${cgiver.caregiverPhone}
                            </td>
                            <td width="10%">
                                <a href="hhChangeOperationAction.do?uid=${cgiver.caregiverId}&&id=cg" onclick="return confirmChange('migrate')">Migrate to new Household</a>
                            </td>
                        </tr>
                        </logic:iterate>
                    </table>
                                                          
                    <table  border="1" cellspacing="0" cellpadding="0" style="border:1px black solid;border-collapse: collapse; margin-bottom:20px">
                        <tr align="left" bgcolor="#D7E5F2">
                                <th>Household unique id</th>
                                <th>Ovc id</th>
                                <th >Ovc name </th>
                                <th >Caregiver Id </th>
                                <th >Caregiver name </th>
                                <th >Age</th>
                                <th >Sex(M/F)</th>
                                <th >Phone</th>
                                <th>Action</th>
                        </tr>
                        <logic:iterate collection="${curHhMemberList[2]}" id="ovc">
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
                            <td width="5%">
                                ${ovc.age}
                            </td>
                            <td width="8%">
                                ${ovc.gender}
                            </td>
                            <td width="8%">
                                ${ovc.phone}
                            </td>
                            <td width="10%">
                                <a href="hhChangeOperationAction.do?uid=${ovc.ovcId}&&id=ov" onclick="return confirmChange('migrate')">Migrate to new Household</a>
                            </td>
                        </tr>
                        </logic:iterate>
                        
                    </table>
                 
             
             </logic:present>
                        </fieldset>
             </div>
             
             
             
        
        </center>
        </span>
    <!--</div>-->

            </html:form>
        </center>
        <div id="pop" class="search" style="width:210px;">
    <table><tr><td style="width:208px;"><label id="title" style="color:#FFFFFF; width:198px;">Browse</label></td><td><img name="popClose" src="images/close.jpg" style="width:10px; height:10px;" onClick="hideComponent('pop')"/></td></tr>
        <tr><td colspan="2" align="left"><span><input type="text" name="selectedName" style="width:195px;" style="margin-top:0px;" onkeyup="filterNames(this.value)"/></span></td></tr>
        <tr><td colspan="2"><span id="ovcNames"> </span></td></tr>
    </table>
  </div>
    </body>
</html>


