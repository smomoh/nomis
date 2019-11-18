<%-- 
    Document   : ReportDownload
    Created on : Oct 29, 2015, 4:37:45 PM
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
<%@ page language="java" %>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Report downloads</title>
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
            <html:form action="/reportDownloadAction" method="post">
                <html:hidden property="actionName" styleId="actionName"/>
               
        <span>
        <center>
            <html:errors/>
        <table class="paramPage">
            <tr><td colspan="4" class="title" align="center">Download report in excel</td></tr>
            <tr><td class="orglabel">Report</td><td colspan="3" class="title" align="left">
                    <html:select property="reportType" style="width:290px;" onchange="setActionName('loadReportType');forms[0].submit()">
                        <%--<html:option value="vcradet"> OVC RADET report</html:option>
                        <html:option value="cgradet"> Caregiver RADET report</html:option>
                        <html:option value="quarterlyReport"> New Custom indicators report</html:option>--%>
                        <html:option value="indicatorvalues"> OVC Indicators </html:option>
                        <html:option value="hivRiskAssessmentList"> HIV Risk Assessment data</html:option>
                        <html:option value="vcnassesmnt"> OVC Nutritional assessment data</html:option>
                        <html:option value="vclist">List of OVC</html:option>
                        <html:option value="csi"> Child status index data</html:option>
                        <html:option value="ovcVulnerabilityScoreByCsi"> OVC vulnerability status by CSI</html:option>
                        <html:option value="cgindicators"> Caregiver indicators </html:option>
                        <html:option value="hhindicators"> Household indicators </html:option>
                        <html:option value="hva"> Household vulnerability assessment</html:option>
                        
                    </html:select></td>
            </tr>
            
            <tr><td class="orglabel"> </td><td colspan="3" class="title" align="center">
                    <fieldset>
                        <legend class="fieldset">Select States</legend>
                        <div style="width:750px; min-height: 50px; max-height: 150px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="730">
                          <table width="720" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:iterate name="stateListForReports" id="state">
                                  <tr> <td style="width:30px"><html:multibox property='states' styleId="${state.state_code}" value="${state.state_code}" styleClass='smallfieldcellselect'/> </td>
                                  <td>${state.name} </td></tr>
                              </logic:iterate>
                              
                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
                  </fieldset>
                </td>
            </tr>
            <tr><td class="orglabel"> </td><td colspan="3" class="title" align="center">
                    <logic:present name="downloadIndicatorList">
                    <fieldset>
                        <legend class="fieldset">Select indicators</legend>
                        <div style="width:730px; height:250px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="730" height="102">
                          <table width="720" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:iterate name="downloadIndicatorList" id="indicator">
                                  <tr> <td><html:multibox property='indicators' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td>
                                  <td>${indicator.indicatorName} </td></tr>
                              </logic:iterate>
                              
                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
                  </fieldset>
                    </logic:present>
                </td>
            </tr>
            <tr><td class="orglabel" align="left">Period</td><td align="left" colspan="3">
                    <html:select property="startMth" styleId="startMth" style="width: 70px;">
                        <logic:iterate id="months" name="generatedMonths">
                            <html:option value="${months}">${months}</html:option>
                        </logic:iterate>
                    </html:select> <html:select property="startYear" styleId="startYear" style="width: 60px;">
                        <logic:iterate id="year" name="generatedYears" >
                            <html:option value="${year}">${year}</html:option>
                        </logic:iterate>
                    </html:select> <label class="orglabel" style=" margin-left: 2px; margin-right: 2px"> to</label>
                    <html:select property="endMth" styleId="endMth" style="width: 70px;" >

                    <logic:iterate id="month" name="generatedMonths">
                        <html:option value="${month}">${month}</html:option>
                        </logic:iterate>
                    </html:select> <html:select property="endYear" style="width: 60px;" styleId="endYear" >
                       <logic:iterate id="year" name="generatedYears">
                            <html:option value="${year}">${year}</html:option>
                        </logic:iterate>
                    </html:select> </td>
            </tr>
            <%--<tr><td>&nbsp;</td><td><html:checkbox property="sumbyorgunitgroup" value="on"/> Generate report by organization unit group</td></tr>--%>

            <tr><td colspan="3" align="center"><html:submit value="Submit" onclick="setActionName('download')" style="width: 70px; margin-left: 50px;" /></td></tr>
        </table>
        </center>
        </span>
   <!-- </div>-->

            </html:form>
        </center>

    </body>
</html>
