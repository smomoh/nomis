<%-- 
    Document   : ChildTBChecklistParamList
    Created on : Aug 21, 2016, 11:35:00 AM
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
        <title>Child TB checklist</title>
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
        <span>
        <center>
            <html:errors/>
        <table class="paramPage">
            <tr><td colspan="4" class="title" align="center">Child TB checklist</td></tr>
            <tr><td class="orglabel" align="left">State </td><td align="left"><html:select property="stateCode" styleId="stateCode" style="width: 290px;" onchange="setActionName('lga'); forms[0].submit()"><html:option value="All">All</html:option>
                        <logic:iterate id="stateObj" name="states">
                        <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                        </logic:iterate>
                    </html:select> </td>
            <td class="orglabel" align="left">LGA </td><td align="left"><html:select property="lgaCode" styleId="lgaCode" style="width: 290px;" onchange="setActionName('cbo'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:iterate id="lgaObj" name="summstatlgaList">
                        <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                        </logic:iterate>
                        </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">CBO </td><td align="left">
                    <html:select property="cboCode" styleId="cboCode" style="width: 290px;" onchange="setActionName('ward'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                    <logic:iterate id="cboObj" name="summstatcboList">
                      <html:option value="${cboObj.orgCode}">${cboObj.orgName}</html:option>
                        </logic:iterate>
                    </html:select> </td>
            
            <td class="orglabel" align="left"><jsp:include page="../includes/WardName.jsp"/> </td><td align="left">
                    <html:select property="wardCode" styleId="wardCode" style="width: 290px;" ><html:option value="All"> </html:option>
                        <logic:iterate id="wardObj" name="sumstatwardList">
                        <html:option value="${wardObj.ward_code}">${wardObj.ward_name}</html:option>
                        </logic:iterate>
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">Partner </td><td align="left">
                    <html:select property="partnerCode" styleId="partnerCode" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                    <logic:iterate id="partner" name="partnerList">
                      <html:option value="${partner.partnerCode}">${partner.partnerName}</html:option>
                        </logic:iterate>
                    </html:select> </td>
            
            <td class="orglabel" align="left">Period</td>
                <td align="left">
                    <html:select property="startMth" styleId="startMth" style="width: 70px;">
                        <html:option value="All">All</html:option>
                    <logic:iterate id="months" name="generatedMonths">
                        <html:option value="${months}">${months}</html:option>
                        </logic:iterate>
                    </html:select> <html:select property="startYear" styleId="startYear" style="width: 50px;" onchange="setActionName('enrollmentEndDates'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                    <logic:iterate id="year" name="generatedStartDates" >
                        <html:option value="${year}">${year}</html:option>
                        </logic:iterate>
                    </html:select> <label class="orglabel" style=" margin-left: 6px; margin-right: 6px"> to</label>
                    <html:select property="endMth" styleId="endMth" style="width: 70px;" >
                        <%--<html:option value="All">All</html:option>--%>
                    <logic:iterate id="month" name="enrollmentEndMonths">
                        <html:option value="${month}">${month}</html:option>
                        </logic:iterate>
                    </html:select> <html:select property="endYear" style="width: 50px;" styleId="endYear" >
                        <html:option value="All"></html:option>
                    <logic:iterate id="year" name="enrollmentEndYears">
                        <html:option value="${year}">${year}</html:option>
                        </logic:iterate>
                    </html:select> </td>
            </tr>
            
            <tr><td class="orglabel" align="left">Age</td>
                <td align="left">
                    <html:select property="startAge" styleId="startAge" style="width: 124px;" onchange="setActionName('endAge'); forms[0].submit()">
                       <%--<html:option value="All"></html:option>--%>
                    <logic:iterate id="age" name="ageList">
                        <html:option value="${age}">${age}</html:option>
                        </logic:iterate>
                    </html:select>  <label class="orglabel" style=" margin-left: 6px; margin-right: 6px"> to</label>
                    <html:select property="endAge" styleId="endAge" style="width: 124px;" >
                        <logic:iterate id="age" name="endAgeList">
                        <html:option value="${age}">${age}</html:option>
                        </logic:iterate>
                    </html:select></td>
            </tr>
            <tr><td colspan="3" align="center"><html:submit property="submitBtn" value="Submit" onclick="setActionName('summstatreport')" style="width: 70px; margin-left: 350px;" /></td></tr>
        </table>
        </center>
        </span>
   <!-- </div>-->

            </html:form>
        </center>
        
    </body>
</html>

