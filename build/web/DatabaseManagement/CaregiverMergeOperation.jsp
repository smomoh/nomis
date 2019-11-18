<%-- 
    Document   : CaregiverMergeOperation
    Created on : Jun 29, 2016, 11:26:07 AM
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
        <title>Household Merge operation </title>
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
</script>
    </head>
    <body>
        <br/><br/>
        <center>
            <html:form action="/householdMergeAction" method="post">
                <html:hidden property="actionName" styleId="actionName" value="caregiveroperations"/>

        <span>
        <center>
        <table class="paramPage">
            <tr><td colspan="4" class="title" align="center">Select organization units</td></tr>
            <tr><td class="orglabel" align="left">State </td><td colspan="3">
                    <html:select property="state" styleId="state" style="width: 290px;" onchange="setActionName('lga'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="hhmStateList">
                        <logic:iterate id="stateObj" name="hhmStateList">
                        <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                        </logic:iterate>
                        </logic:present>
                    </html:select> </td></tr>
            <tr><td class="orglabel" align="left">LGA </td><td colspan="3"><html:select property="lga" styleId="lga" style="width: 290px;" onchange="setActionName('cbo'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="hhmLgaList">
                        <logic:iterate id="lgaObj" name="hhmLgaList">
                        <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                        </logic:iterate>
                        </logic:present>
                        </html:select> </td></tr>
            <tr><td class="orglabel" align="left">CBO </td><td colspan="3">
                    <html:select property="organization" styleId="organization" style="width: 290px;" onchange="setActionName('wardList'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="hhmOrgList">
                    <logic:iterate id="cboObj" name="hhmOrgList">
                      <html:option value="${cboObj.orgCode}">${cboObj.orgName}</html:option>
                        </logic:iterate>
                        </logic:present>
                    </html:select> </td></tr>
            <tr><td class="orglabel" align="left"><jsp:include page="../includes/WardName.jsp"/> </td><td colspan="3">
                    <html:select property="ward" styleId="ward" style="width: 290px;" >
                        <html:option value="All"> </html:option>
                        <logic:present name="hhmwardList">
                        <logic:iterate id="wardObj" name="hhmwardList">
                        <html:option value="${wardObj.ward_code}">${wardObj.ward_name}</html:option>
                        </logic:iterate>
                        </logic:present>
                    </html:select> </td></tr>
            <tr><td class="orglabel" align="left">Partner </td><td colspan="3">
                    <html:select property="partnerCode" styleId="partnerCode" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                        <logic:present name="partnerList">
                    <logic:iterate id="partner" name="partnerList">
                      <html:option value="${partner.partnerCode}">${partner.partnerName}</html:option>
                        </logic:iterate>
                    </logic:present>
                    </html:select> </td>
            </tr>
            

             <tr><td colspan="3" align="center"><html:submit property="hhmReportBtn" value="Generate list" onclick="setActionName('hhmReportList')" style="width: 150px; margin-left: 50px;" /></td></tr>
        </table>
                        <logic:present name="hviRecords">
                            <table border=0>
                                <tr><td align="center" height="40">&nbsp;</td></tr>
                                
    <tr><td style="border: none; text-align: left;">
            <table align="center" border="0" style="border: none;">
                <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${hviParam[4]}   </label></td>
                <td class="orgNamestd"><label class="lab">LGA:</label></td> <td style=" border: none;" width="25%"><label class="orgNames"> ${hviParam[5]} </label></td>
                <td class="orgNamestd"><label class="lab">Organization:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${hviParam[6]}   </label></td>
                <td class="orgNamestd"><label class="lab"><jsp:include page="../includes/WardName.jsp"/></label></td> <td style=" border: none;" width="25%"><label class="orgNames">${hviParam[7]}   </label></td></tr>

                
                </table>
        </td></tr>

    <tr><td style="border: none; text-align: left">
                        <table
                               border="1" cellspacing="0" cellpadding="0" style="border:1px black solid; border-collapse: collapse; margin-bottom:40px">
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
                                <th >Keep</th>
                                <th >Delete</th>
                                
                                
                                <%--<th >Marital status</th>
                                <th >Occupation</th>
                                <th >Partner</th>
                                <th >Recorded by</th>--%>
                               <%-- <th >Household headship</th>
                                <th >Health </th>
                                <th >Education level</th>
                                <th >Shelter and housing</th>
                                <th >Food security and nutrition</th>
                                <th >Means of livelihood</th>
                                <th >Household income</th>
                                
                                <th >Vulnerability status</th>
                               <th >Date of entry (mm/dd/yyyy)</th>--%>
                                
                                
                            </tr>
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <logic:iterate id="hviData" name="hviRecords">

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
                                    <td width="4%">${hviData.vulnerabilityScore}</td>
                                    <td width="10%">
                                        <a href="householdMergeAction.do?id=${hviData.hhUniqueId}" target="_blank"> View members</a>
                                    </td>
                                    <td width="7%">
                                        <html:radio property="keep" styleId="keep_${hviData.hhUniqueId}" value="${hviData.hhUniqueId}" onchange="disableControl(${hviData.hhUniqueId})"/>Keep
                                    </td>
                                    <td width="8%"><html:checkbox property="merge" styleId="${hviData.hhUniqueId}" value="${hviData.hhUniqueId}"/>delete</td>
                                    
                                </tr>
                            </logic:iterate>
                            </table>
        </td></tr>
    <tr><td align="center"><html:submit property="hhmReportBtn" value="Merge household" onclick="confirmAction('merge','merge')" style="width: 150px; margin-left: 50px;" disabled="${hhmBtnDisabled}"/></td></tr>
                            </table>
                        </logic:present>
        </center>
        </span>
    <!--</div>-->

            </html:form>
        </center>
    </body>
</html>

