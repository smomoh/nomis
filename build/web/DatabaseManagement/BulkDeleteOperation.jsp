<%-- 
    Document   : BulkDeleteOperation
    Created on : Jun 25, 2018, 3:43:29 PM
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
        <title><jsp:include page="../includes/WardName.jsp"/> Bulk Delete Operation </title>
        <style type="text/css">
<!--
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

-->
</style>
<script language="javascript">
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
function setActionName(val)
{
    document.getElementById("actionName").value=val
}
function disableControl(id)
{
    document.getElementById(id).disabled=true;
}
function confirmAction(val)
{
    if(confirm("This action will permanently change this record. Are you  sure you want to proceed?"))
    {
        setActionName(val)
        return true
    }
    else
    return false
}


</script>
    </head>
    <body>
        <br/><br/>
        <center>
            <html:form action="/bulkdeleteoperation" method="post">
                <html:hidden property="actionName" styleId="actionName"/>

        <span>
        <center>
                    
        <table class="paramPage">
            <tr>
                <td>
                    <table class="paramPage">
            <tr><td colspan="4" class="title" align="center">Select current <jsp:include page="../includes/WardName.jsp"/></td></tr>
            <tr><td class="orglabel" align="left">State </td><td colspan="3">
                    <html:select property="stateCode" styleId="stateCode" style="width: 290px;" onchange="setActionName('lga'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="stateListForReports">
                            <logic:iterate id="stateObj" name="stateListForReports">
                                <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select> </td></tr>
            <tr><td class="orglabel" align="left">LGA </td><td colspan="3">
                    <html:select property="lgaCode" styleId="lgaCode" style="width: 290px;" onchange="setActionName('cbo'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="bdlgaList">
                            <logic:iterate id="lgaObj" name="bdlgaList">
                                <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                            </logic:iterate>
                        </logic:present>
                        </html:select> </td></tr>
            <tr><td class="orglabel" align="left">CBO </td><td colspan="3">
                    <html:select property="cboCode" styleId="cboCode" style="width: 290px;" onchange="setActionName('community'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="bdcboList">
                            <logic:iterate id="cboObj" name="bdcboList">
                                <html:option value="${cboObj.orgCode}">${cboObj.orgName}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select> </td></tr>
            <tr><td class="orglabel" align="left"><jsp:include page="../includes/WardName.jsp"/> </td><td colspan="3">
                    <html:select property="communityCode" styleId="communityCode" style="width: 290px;" >
                        <html:option value="All"> </html:option>
                        <logic:present name="bdcommunityList">
                            <logic:iterate id="wardObj" name="bdcommunityList">
                                <html:option value="${wardObj.ward_code}">${wardObj.ward_name}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">Sort by </td><td colspan="3">
                    <html:select property="sortOrder" styleId="sortOrder" style="width: 290px;" >
                      <html:option value="hhUniqueId">Unique Id</html:option>
                      <html:option value="hhFirstname">Surname</html:option>  
                    </html:select> </td>
            </tr> 
            <tr><td class="orglabel" align="left">Beneficiary </td><td colspan="3">
                    <html:select property="beneficiaryType" styleId="beneficiaryType" style="width: 290px;" >
                        <%--<html:option value="ovc">Ovc</html:option>
                        <html:option value="caregiver">Caregiver</html:option>--%> 
                        <html:option value="household">Household</html:option>
                     </html:select> </td>
            </tr> 
            <tr><td colspan="3" align="center"><html:submit property="hhmReportBtn" value="Generate list" onclick="setActionName('generateList');" style="width: 150px; margin-left: 50px;" /></td></tr>
                    </table></td> <td colspan="2">&nbsp;</td>
                    <td style="vertical-align:top">
            
            
            </td> 
             
        </table>
                        
                    <table border=0>
                        <tr><td align="center" height="40">&nbsp;</td></tr>
                                
    
    <logic:present name="bdhheList">
    <tr><td style="border: none; text-align: left">
            
                        <table border="1" cellspacing="0" cellpadding="0" style="border:1px black solid; border-collapse: collapse; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th>Unique id</th>
                                <th >Household head name </th>
                                <th >Address</th>
                                <th >Age</th>
                                <th >Sex(M/F)</th>
                                <th >Phone</th>
                                
                                <th >No. of children in Household</th>
                                <th >No. of people in household</th>
                                <th >Date of assessment (mm/dd/yyyy)</th>
                                <th >Vulnerability score</th>
                                <th >View Household members</th>
                                <th >Change <jsp:include page="../includes/WardName.jsp"/></th>
                                
                            </tr>
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <logic:iterate id="hviData" name="bdhheList">

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
                                        ${hviData.hhUniqueId}
                                    </td>

                                    <td width="15%">
                                        ${hviData.hhSurname} ${hviData.hhFirstname}
                                    </td>
                                    
                                    <td width="23%">
                                        ${hviData.address}
                                    </td>
                                    <td width="3%">
                                        ${hviData.hhAge}
                                    </td>
                                    <td width="3%">
                                        ${hviData.hhGender}
                                    </td>
                                    <td width="8%">
                                        ${hviData.phone}
                                    </td>
                                    
                                    <td width="3%">
                                        ${hviData.noOfChildrenInhh}
                                    </td>
                                    <td width="3%">
                                        ${hviData.noOfPeopleInhh}
                                    </td>
                                    
                                    <td width="10%">
                                        ${hviData.dateOfAssessment}
                                    </td>
                                    <td width="4%">${hviData.currentVulnerabilityScore}</td>
                                    <td width="10%">
                                        <a href="householdMergeAction.do?id=${hviData.hhUniqueId}" target="_blank"> View members</a>
                                    </td>
                                    <td width="8%"><html:checkbox property="householdsToDelete" styleId="${hviData.hhUniqueId}" value="${hviData.hhUniqueId}"/></td>
                                     
                                </tr>
                            </logic:iterate>
                                
                        </table>
                            </td></tr>
    
                            <tr><td >
                                <input type="button" value="Select all" onclick="selectChkBoxes('householdsToDelete')" />
                                <input type="button" value="Unselect all" onclick="unselectChkBoxes('householdsToDelete')" />
                            </td></tr>
                           </logic:present> 
                    
        
        <%--return confirmAction('change the ward/community for ','changeCommunity')--%>
        <tr><td align="center"><html:submit value="Delete selected records" onclick="return confirmAction('delete')" style="width: 150px; margin-left: 50px;" disabled="${hhmBtnDisabled}"/></td></tr>
     </table>                 
        </center>
        </span>
    <!--</div>-->

            </html:form>
        </center>
    </body>
</html>
