<%-- 
    Document   : CohortAnalysisReportCriteria
    Created on : Mar 19, 2016, 2:36:24 PM
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
        <title>Cohort analysis report</title>
        <link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
        <style type="text/css">

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
.selectBoxtWidth
{
    width: 160px;
}


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
function enableComponents(id)
{
    document.getElementById(id).disabled=false
}
function disableComponents(id)
{
    document.getElementById(id).disabled=true
}
        </script>
    </head>
    <body>
        <br/><br/>
        <center>
            <html:form action="/cohortAnalysisReportAction" method="post">
                <html:hidden property="actionName" styleId="actionName"/>
                
                <!--<div styleId="sumstatDivPerMth" class="paramPage" style="height: 240px; width: 450px; margin-top: 100px;">-->
        <span>
        <center>
            <html:errors/>
        <table class="paramPage">
            <tr><td colspan="4" class="title" align="center">Cohort analysis</td></tr>
            <tr><td class="orglabel" align="left">State </td><td align="left">
                    <html:select property="stateCode" styleId="stateCode" style="width: 290px;" onchange="setActionName('lga'); forms[0].submit()"><html:option value="All">All</html:option>
                        <logic:present name="stateListForReports">
                            <logic:iterate id="stateObj" name="stateListForReports">
                                <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select> </td>
                <td class="orglabel" align="left">Group </td><td>
                    <html:select property="orgUnitGroupId" styleId="orgUnitGroupId" style="width: 290px;" onchange="setActionName('allLga'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="ssorgUnitGroupList">
                            <logic:iterate id="oug" name="ssorgUnitGroupList">
                            <html:option value="${oug.orgUnitGroupId}">${oug.orgUnitGroupName}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select> </td>
            </tr>
            <tr>
                             
            <td class="orglabel" align="left">LGA </td><td align="left">
                <html:select property="lgaCode" styleId="lgaCode" style="width: 290px;" onchange="setActionName('cbo'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="cohortlgaList">
                        <logic:iterate id="lgaObj" name="cohortlgaList">
                        <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                        </logic:iterate>
                        </logic:present>
                        </html:select> </td>
            <td class="orglabel" align="left">CBO </td><td align="left">
                    <html:select property="cboCode" styleId="cboCode" style="width: 290px;" onchange="setActionName('ward'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="cohortcboList">
                    <logic:iterate id="cboObj" name="cohortcboList">
                      <html:option value="${cboObj.orgCode}">${cboObj.orgName}</html:option>
                        </logic:iterate>
                    </logic:present>
                    </html:select> </td>
            </tr>
            <tr>
            
            <td class="orglabel" align="left"><jsp:include page="../includes/WardName.jsp"/> </td><td align="left">
                    <html:select property="wardCode" styleId="ward" style="width: 290px;" ><html:option value="All"> </html:option>
                        <logic:present name="cohortwardList">
                        <logic:iterate id="wardObj" name="cohortwardList">
                        <html:option value="${wardObj.ward_code}">${wardObj.ward_name}</html:option>
                        </logic:iterate>
                        </logic:present>
                    </html:select> </td>
            <td class="orglabel" align="left">Partner </td><td align="left">
                    <html:select property="partnerCode" styleId="partnerCode" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                    <logic:present name="partnerList">
                        <logic:iterate id="partner" name="partnerList">
                      <html:option value="${partner.partnerCode}">${partner.partnerName}</html:option>
                        </logic:iterate>
                    </logic:present>
                    </html:select> </td>
            </tr>
            <tr>
            
            <td class="orglabel" align="left">Cohort period</td>
                <td align="left" colspan="3">
                    <html:select property="startMth" styleId="startMth" style="width: 70px;">
                        <html:option value="All">All</html:option>
                    <logic:iterate id="months" name="cohortStartMonths">
                        <html:option value="${months}">${months}</html:option>
                        </logic:iterate>
                    </html:select> <html:select property="startYear" styleId="startYear" style="width: 55px;" onchange="setActionName('enrollmentEndDates'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                    <logic:iterate id="year" name="cohortStartDates" >
                        <html:option value="${year}">${year}</html:option>
                        </logic:iterate>
                    </html:select> <label class="orglabel" style=" margin-left: 6px; margin-right: 6px"> to</label>
                    <html:select property="endMth" styleId="endMth" style="width: 70px;" >
                        <%--<html:option value="All">All</html:option>--%>
                    <logic:iterate id="month" name="cohortEndMonths">
                        <html:option value="${month}">${month}</html:option>
                        </logic:iterate>
                    </html:select> <html:select property="endYear" style="width: 55px;" styleId="endYear" >
                        <html:option value="All"></html:option>
                    <logic:iterate id="year" name="cohortEndYears">
                        <html:option value="${year}">${year}</html:option>
                        </logic:iterate>
                    </html:select> </td>
            </tr>
                       
            <tr><td>&nbsp;</td>
                <td valign="top" colspan="3">
                      <fieldset>
                        <legend class="fieldset">Indicators </legend>
                        <div style="width:620px; height:250px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    
                    <tr>
                      <td>
                          <table width="620" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:iterate name="cohortAnalysisIndictors" id="ind">
                                  <tr><td><html:multibox property='selectedIndicatorIds' styleId="${ind.indicatorId}" value="${ind.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${ind.indicatorName} </td> </tr>
                              </logic:iterate>
                              
                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
                  </fieldset></td>
            </tr>
            <tr><td></td><td colspan="2" >
            <input type="button" value="Select all" onclick="selectChkBoxes('selectedIndicatorIds')" />
                    <input type="button" value="Unselect all" onclick="unselectChkBoxes('selectedIndicatorIds')" />
                    </td></tr>
            <tr><td colspan="3" align="center"><html:submit property="reportBtn" value="Submit" onclick="setActionName('summstatreport')" style="width: 70px; margin-left: 350px;" /></td></tr>
        </table>
        </center>
        </span>
   <!-- </div>-->

            </html:form>
        </center>
        
    </body>
</html>

