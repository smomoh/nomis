<%--
    Document   : EnrollmentRecord
    Created on : Oct 5, 2010, 12:35:14 PM
    Author     : smomoh
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

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List of indicators</title>
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
            function setActionName(val)
            {
                document.getElementById("actionName").value=val
            }
function enableDateFields()
{
    if(document.getElementById("chkReportType").checked==true)
    {
        enableComponents("sumstat_month")
        enableComponents("sumstat_year")
        document.getElementById("reportType").value="on"
    }
    else
    {
        disableComponents("sumstat_month")
        disableComponents("sumstat_year")
        document.getElementById("reportType").value="off"
    }
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
            <html:form action="/summstatreport" method="post">
                <html:hidden property="actionName" styleId="actionName"/>
                <html:hidden property="reportType" styleId="reportType"/>
                <!--<div styleId="sumstatDivPerMth" class="paramPage" style="height: 240px; width: 450px; margin-top: 100px;">-->
        <span>
        <center>
            <html:errors/>
        <table class="paramPage">
            <logic:present name="sumstatmsg">
                <tr><td colspan="4" class="title" style="color:red; font-size: 16px;" align="center">${sumstatmsg}</td></tr>
            </logic:present>
            <tr><td colspan="4" class="title" align="center">List of indicators</td></tr>
            <tr><td class="orglabel" align="left">Partner </td><td align="left" colspan="3">
                    <html:select property="partnerCode" styleId="partnerCode" style="width: 685px;" onchange="setActionName('stateList'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="partnerList">
                            <logic:iterate id="partner" name="partnerList">
                                <html:option value="${partner.partnerCode}">${partner.partnerName}</html:option>
                            </logic:iterate>
                    </logic:present>
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">State </td><td align="left">
                    <html:select property="sumstatPerMth_state" styleId="sumstatPerMth_state" style="width: 290px;" onchange="setActionName('lga'); forms[0].submit()"><html:option value="All">All</html:option>
                        <logic:present name="stateListForReports">
                            <logic:iterate id="stateObj" name="stateListForReports">
                                <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select> </td>
                <td class="orglabel" align="left">Group </td><td align="left">
                    <html:select property="orgUnitGroupId" styleId="orgUnitGroupId" style="width: 290px;" onchange="setActionName('allLga'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="ssorgUnitGroupList">
                            <logic:iterate id="oug" name="ssorgUnitGroupList">
                            <html:option value="${oug.orgUnitGroupId}">${oug.orgUnitGroupName}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select>
            </td>
            
            </tr>
            
            <tr>
                <td class="orglabel" align="left">LGA </td><td align="left"><html:select property="sumstatPerMth_lga" styleId="sumstatPerMth_lga" style="width: 290px;" onchange="setActionName('cbo'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="summstatlgaList">
                        <logic:iterate id="lgaObj" name="summstatlgaList">
                        <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                        </logic:iterate>
                        </logic:present>
                        </html:select> </td>
                <td class="orglabel" align="left">CBO </td>
                <td align="left">
                    <html:select property="sumstatPerMth_cbo" styleId="sumstatPerMth_cbo" style="width: 290px;" onchange="setActionName('ward'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                    <logic:iterate id="cboObj" name="summstatcboList">
                      <html:option value="${cboObj.orgCode}">${cboObj.orgName}</html:option>
                        </logic:iterate>
                    </html:select> 
                </td>
            
            </tr>
            <tr>
                <td height="123" valign="top" > </td>
                <td colspan="3">
                      <fieldset>
                        <legend class="fieldset">LGA </legend>
                        <div style="width:700px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="700" height="102">
                          <table width="690" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:present name="summstatlgaList">
                                  <logic:iterate name="summstatlgaList" id="lga">
                                      <tr>
                                          <td><html:multibox property='lgas' styleId="${lga.lga_code}" value="${lga.lga_code}" styleClass='smallfieldcellselect'/> &nbsp;${lga.lga_name} </td> 
                                      </tr>
                                  </logic:iterate>
                              </logic:present>
                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
                  </fieldset></td>
                            
            </tr>
            <tr>
                <td class="orglabel" align="left"><jsp:include page="../includes/WardName.jsp"/> </td><td align="left">
                    <html:select property="ward" styleId="ward" style="width: 290px;" ><html:option value="All"> </html:option>
                        <logic:present name="sumstatwardList">
                            <logic:iterate id="wardObj" name="sumstatwardList">
                            <html:option value="${wardObj.ward_code}">${wardObj.ward_name}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select> </td><td> </td>
                            
            </tr>
            
            <tr>
                <td class="orglabel" align="left">Period</td>
                <td align="left">
                    <html:select property="enrollmentStartMth" styleId="enrollmentStartMth" style="width: 70px;">
                        <html:option value="All">All</html:option>
                        <logic:present name="generatedMonths">
                    <logic:iterate id="months" name="generatedMonths">
                        <html:option value="${months}">${months}</html:option>
                        </logic:iterate>
                    </logic:present>
                    </html:select> <html:select property="enrollmentStartYear" styleId="enrollmentStartYear" style="width: 50px;" onchange="setActionName('enrollmentEndDates'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="generatedStartDates">
                            <logic:iterate id="year" name="generatedStartDates" >
                                <html:option value="${year}">${year}</html:option>
                            </logic:iterate>
                    </logic:present>
                    </html:select> <label class="orglabel" style=" margin-left: 6px; margin-right: 6px"> to</label>
                    <html:select property="enrollmentEndMth" styleId="enrollmentEndMth" style="width: 70px;" >
                        <%--<html:option value="All">All</html:option>--%>
                        <logic:present name="enrollmentEndMonths">
                    <logic:iterate id="month" name="enrollmentEndMonths">
                        <html:option value="${month}">${month}</html:option>
                        </logic:iterate>
                    </logic:present>
                    </html:select> <html:select property="enrollmentEndYear" style="width: 50px;" styleId="enrollmentEndYear" >
                        <html:option value="All"></html:option>
                        <logic:present name="enrollmentEndYears">
                            <logic:iterate id="year" name="enrollmentEndYears">
                                <html:option value="${year}">${year}</html:option>
                            </logic:iterate>
                    </logic:present>
                    </html:select> </td>
                <td class="orglabel" align="left">Age</td>
                <td align="left">
                    <html:select property="ovcStartAge" styleId="ovcStartAge" style="width: 124px;" onchange="setActionName('endAge'); forms[0].submit()">
                        <logic:present name="ageList">
                            <logic:iterate id="age" name="ageList">
                                <html:option value="${age}">${age}</html:option>
                            </logic:iterate>
                    </logic:present>
                    </html:select>  <label class="orglabel" style=" margin-left: 6px; margin-right: 6px"> to</label>
                    <html:select property="ovcEndAge" styleId="ovcEndAge" style="width: 124px;" >
                        <logic:present name="endAgeList">
                            <logic:iterate id="age" name="endAgeList">
                                <html:option value="${age}">${age}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select></td>
            </tr>
            
            <tr><td>&nbsp;</td>
                <td valign="top" colspan="3">
                      <fieldset>
                        <legend class="fieldset">Indicators </legend>
                        <div style="width:700px; height:450px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <tr>
                      <td>
                          
                          <fieldset>
                            <table width="720" border="1" bordercolor="#D7E5F2" class="regsitertable">
                                <tr><td colspan="2" align="center" style="background-color:#4E9258; color: white">OVC indicators</td></tr>
                              <logic:iterate collection="${sunstatIndictors[0]}" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate>
                                  
                              <tr><td colspan="2" align="center" style="background-color:#4E9258; color: white">Birth registration indicators</td></tr>
                              <logic:iterate collection="${sunstatIndictors[1]}" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate>
                              
                             <tr><td colspan="2" align="center" style="background-color:#4E9258; color: white" >OVC HIV indicators</td></tr>
                              <logic:iterate collection="${sunstatIndictors[2]}" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate>
                                  
                             <tr><td colspan="2" align="center" style="background-color:#4E9258; color: white" >OVC newly enrolled HIV indicators</td></tr>
                              <logic:iterate collection="${sunstatIndictors[3]}" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate>
                                  
                              <tr><td colspan="2" align="center" style="background-color:#4E9258; color: white" >OVC HIV test and linkage to care indicators</td></tr>
                              <logic:iterate collection="${sunstatIndictors[4]}" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate>
                                  
                              <tr><td colspan="2" align="center" style="background-color:#4E9258; color: white" >HIV risk assessment indicators</td></tr>
                              <logic:iterate collection="${sunstatIndictors[5]}" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate>
                                  
                              <tr><td colspan="2" align="center" style="background-color:#4E9258; color: white" >Nutritional assessment indicators</td></tr>
                              <logic:iterate collection="${sunstatIndictors[6]}" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate>
                                  
                              <tr><td colspan="2" align="center" style="background-color:#4E9258; color: white" >OVC-Household linkage indicators</td></tr>
                              <logic:iterate collection="${sunstatIndictors[7]}" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate>
                                  
                                  <tr><td colspan="2" align="center" style="background-color:#4E9258; color: white">OVC Service indicators</td></tr>
                              <logic:iterate collection="${sunstatIndictors[8]}" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate>
                                  
                                  <tr><td colspan="2" align="center" style="background-color:#4E9258; color: white">OVC Service indicators by service domains</td></tr>
                              <logic:iterate collection="${sunstatIndictors[9]}" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate>
                                
                                  <tr><td colspan="2" align="center" style="background-color:#4E9258; color: white">OVC Service indicators by HIV status</td></tr>
                              <logic:iterate collection="${sunstatIndictors[10]}" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate>
                              
                              <tr><td colspan="2" align="center" style="background-color:#4E9258; color: white">OVC in Households Served HES</td></tr>
                              <logic:iterate collection="${sunstatIndictors[11]}" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate>
                                  
                                  <tr><td colspan="2" align="center" style="background-color:#4E9258; color: white">Caregiver indicators</td></tr>
                              <logic:iterate collection="${sunstatIndictors[12]}" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate>
                              
                                  <tr><td colspan="2" align="center" style="background-color:#4E9258; color: white">Caregiver HIV indicators</td></tr>
                              <logic:iterate collection="${sunstatIndictors[13]}" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate>
                                 <tr><td colspan="2" align="center" style="background-color:#4E9258; color: white">Caregiver service indicators</td></tr>
                              <logic:iterate collection="${sunstatIndictors[14]}" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate> 
                                  <tr><td colspan="2" align="center" style="background-color:#4E9258; color: white">Household indicators</td></tr>
                              <logic:iterate collection="${sunstatIndictors[15]}" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate>
                                  
                                  <tr><td colspan="2" align="center" style="background-color:#4E9258; color: white">Household indicators by vulnerability status</td></tr>
                              <logic:iterate collection="${sunstatIndictors[16]}" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate>
                                  
                                  <tr><td colspan="2" align="center" style="background-color:#4E9258; color: white">Special indicators</td></tr>
                              <logic:iterate collection="${sunstatIndictors[17]}" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate>
                            </table>
                          </fieldset>
                         
                          
                      </td>
                      </tr>
                   <%--<tr>
                      <td>
                          
                          <table width="720" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:iterate name="sunstatIndictors" id="indicator">
                                  <tr><td><html:multibox property='indicatorIndexes' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                              </logic:iterate>
                              
                          </table>

                      </td>
                      </tr>--%>
                  </table>
                </div>
                  </fieldset></td>
            </tr>
            <tr><td colspan="3" align="center"><html:submit property="sumstatPerMth_dateBtn" value="Submit" onclick="setActionName('summstatreport')" style="width: 70px; margin-left: 350px;" disabled="${sumstatButtonDisabled}" /></td></tr>
        </table>
        </center>
        </span>
   <!-- </div>-->

            </html:form>
        </center>
        
    </body>
</html>
