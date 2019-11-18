<%--
    Document   : EnrollmentRecord
    Created on : Oct 5, 2010, 12:35:14 PM
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
        <title>Monthly service records</title>
        <link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
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
 function enableDateFields()
{
    if(document.getElementById("chkReportType").checked==true)
    {
        disableComponents("serviceMonth2")
        disableComponents("serviceYear2")
        //document.getElementById("reportType").value="on"
    }
    else
    {
        enableComponents("serviceMonth2")
        enableComponents("serviceYear2")
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
            
            <html:form action="/mthlyServiceReport" method="post">
                <html:hidden property="actionName" styleId="actionName"/>
        <!--<div id="serviceReportDiv" class="paramPage" style="height: 210px; width: 360px; margin-top: 100px;">-->
        <span>
        <center>
        <table class="paramPage">
            <tr><td colspan="4"><html:errors/>  </td></tr> <!--onchange="getLga(this.value,'lgaForReports')"-->
            <tr><td class="orglabel" align="left">State </td><td colspan="3">
                    <html:select property="state" styleId="state" style="width: 290px;" onchange="setActionName('lga'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="stateListForReports">
                            <logic:iterate id="stateObj" name="stateListForReports">
                                <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                            </logic:iterate>
                        </logic:present>
                        <%--<html:optionsCollection property="stateList" label="name" value="state_code" />--%>
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">LGA </td><td colspan="3">
                    <html:select property="lga" styleId="lga" style="width: 290px;" onchange="setActionName('cbo'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <html:optionsCollection property="lgaList" label="lga_name" value="lga_code" />
                    </html:select>
                </td></tr>
            <tr><td class="orglabel" align="left">CBO </td><td colspan="3">
                    <html:select property="cbo" styleId="cbo" style="width: 290px;" onchange="setActionName('ward'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <html:optionsCollection property="cboList" label="orgName" value="orgCode" />
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left"><jsp:include page="../includes/WardName.jsp" /> </td><td colspan="3">
                    <html:select property="ward" styleId="ward" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                        <html:optionsCollection property="wardList" label="ward_name" value="ward_code" />
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">Partner </td><td colspan="3">
                    <html:select property="partnerCode" styleId="partnerCode" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                    <logic:iterate id="partner" name="partnerList">
                      <html:option value="${partner.partnerCode}">${partner.partnerName}</html:option>
                        </logic:iterate>
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">Filter </td><td colspan="3">
                    <html:select property="reportFilter" styleId="reportFilter" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                        <%--<html:option value="auto">Auto service</html:option>
                        <html:option value="specialService">Special service</html:option>--%>
                    </html:select>
                     </td>
            </tr>
            <tr> <td colspan="4"> <html:checkbox property="chkReportType" styleId="chkReportType" onclick="enableDateFields()" /> <label style=" margin-right: 5px; color:white">Generate Service report per month</label> </td></tr>
            <%--<tr><td class="orglabel" align="left">Ward </td><td colspan="3">
                    <html:select property="ward" styleId="ward" style="width: 290px;" ><html:option value="All"> </html:option>
                        <logic:iterate id="wardObj" name="wardList">
                        <html:option value="${wardObj}">${wardObj}</html:option>
                        </logic:iterate>
                    </html:select> </td></tr>--%>
            <tr><td class="title"><label style=" margin-right: 5px; color:white">Period</label></td>
                <td align="left">
                    <html:select property="serviceMonth" styleId="serviceMonth" style="width: 70px;">
                        <html:option value="1">January </html:option>
                        <html:option value="2">February </html:option>
                        <html:option value="3">March </html:option>
                        <html:option value="4">April </html:option>
                        <html:option value="5">May</html:option>
                        <html:option value="6">June</html:option>
                        <html:option value="7">July </html:option>
                        <html:option value="8">August </html:option>
                        <html:option value="9">September </html:option>
                        <html:option value="10">October </html:option>
                        <html:option value="11">November </html:option>
                        <html:option value="12">December</html:option>
                    </html:select>
                    <html:select property="serviceYear" styleId="serviceYear" style="width: 55px;">
                        <logic:present name="yearList">
                            <logic:iterate name="yearList" id="year">
                                <html:option value="${year}">${year}</html:option>
                            </logic:iterate>
                        </logic:present>
                            <%--<html:option value="2008">2008 </html:option>
                            <html:option value="2009">2009 </html:option>
                            <html:option value="2010" dir="2010">2010 </html:option>
                            <html:option value="2011">2011 </html:option>
                            <html:option value="2012">2012 </html:option>
                            <html:option value="2013">2013 </html:option>
                            <html:option value="2014">2014 </html:option>
                            <html:option value="2015">2015 </html:option>
                            <html:option value="2016">2016 </html:option>
                            <html:option value="2017">2017 </html:option>
                            <html:option value="2018">2018 </html:option>
                            <html:option value="2019">2019 </html:option>
                            <html:option value="2020">2020 </html:option>--%>
                        </html:select> </td>
                <td><label style=" margin-right: 5px; color:white">To </label> <html:select property="serviceMonth2" styleId="serviceMonth2" style="width: 70px;">
                        <html:option value="1">January </html:option>
                        <html:option value="2">February </html:option>
                        <html:option value="3">March </html:option>
                        <html:option value="4">April </html:option>
                        <html:option value="5">May</html:option>
                        <html:option value="6">June</html:option>
                        <html:option value="7">July </html:option>
                        <html:option value="8">August </html:option>
                        <html:option value="9">September </html:option>
                        <html:option value="10">October </html:option>
                        <html:option value="11">November </html:option>
                        <html:option value="12">December</html:option>
                    </html:select>
                    <html:select property="serviceYear2" styleId="serviceYear2" style="width: 55px;">
                        <logic:present name="yearList">
                            <logic:iterate name="yearList" id="year">
                                <html:option value="${year}">${year}</html:option>
                            </logic:iterate>
                        </logic:present>
                            <%--<html:option value="2008">2008 </html:option>
                            <html:option value="2009">2009 </html:option>
                            <html:option value="2010">2010 </html:option>
                            <html:option value="2011">2011 </html:option>
                            <html:option value="2012">2012 </html:option>
                            <html:option value="2013">2013 </html:option>
                            <html:option value="2014">2014 </html:option>
                            <html:option value="2015">2015 </html:option>
                            <html:option value="2016">2016 </html:option>
                            <html:option value="2017">2017 </html:option>
                            <html:option value="2018">2018 </html:option>
                            <html:option value="2019">2019 </html:option>
                            <html:option value="2020">2020 </html:option>--%>
                        </html:select></td>
                <td>&nbsp; </td>
</tr>
            <tr><td class="title"><label style=" margin-right: 5px; color:white">Age</label></td>
                <td colspan="2" align="left">
                    <label style="color:white; width: 30px; margin-right: 21px;">between</label>
                    <html:select property="startAge" styleId="startAge" style="width: 55px;">
                            <html:option value="0">0 </html:option>
                            <html:option value="1">1 </html:option>
                            <html:option value="2">2 </html:option>
                            <html:option value="3">3 </html:option>
                            <html:option value="4">4 </html:option>
                            <html:option value="5">5 </html:option>
                            <html:option value="6">6 </html:option>
                            <html:option value="7">7 </html:option>
                            <html:option value="8">8 </html:option>
                            <html:option value="9">9</html:option>
                            <html:option value="10">10</html:option>
                            <html:option value="11">11 </html:option>
                            <html:option value="12">12 </html:option>
                            <html:option value="13">13</html:option>
                            <html:option value="14">14 </html:option>
                            <html:option value="15">15 </html:option>
                            <html:option value="16">16</html:option>
                            <html:option value="17">17</html:option>
                            <html:option value="18">18</html:option>
                            <html:option value="19">19</html:option>
                            <html:option value="20">20 </html:option>
                            <html:option value="21">21 </html:option>
                            <html:option value="22">22</html:option>
                            <html:option value="23">23</html:option>
                            <html:option value="24">24</html:option>
                   </html:select>
                <label style=" color: white">and</label>  <html:select property="endAge" styleId="endAge" style="width: 55px;">
                        <html:option value="0">0 </html:option>
                            <html:option value="1">1 </html:option>
                            <html:option value="2">2 </html:option>
                            <html:option value="3">3 </html:option>
                            <html:option value="4">4 </html:option>
                            <html:option value="5">5 </html:option>
                            <html:option value="6">6 </html:option>
                            <html:option value="7">7 </html:option>
                            <html:option value="8">8 </html:option>
                            <html:option value="9">9</html:option>
                            <html:option value="10">10 </html:option>
                            <html:option value="11">11 </html:option>
                            <html:option value="12">12 </html:option>
                            <html:option value="13">13</html:option>
                            <html:option value="14">14 </html:option>
                            <html:option value="15">15 </html:option>
                            <html:option value="16">16</html:option>
                            <html:option value="17">17</html:option>
                            <html:option value="18">18</html:option>
                            <html:option value="19">19</html:option>
                            <html:option value="20">20 </html:option>
                            <html:option value="21">21 </html:option>
                            <html:option value="22">22</html:option>
                            <html:option value="23">23</html:option>
                            <html:option value="24">24</html:option>
                    </html:select>
                    </td></tr>
            <tr><td colspan="4" align="center">
                    <html:submit property="serviceReportBtn" value="Submit" onclick="setActionName('MthlyServiceList')" style="width: 70px;" />
                </td></tr>
        </table>
        </center>
        </span>
    <!--</div>-->
            </html:form>
        </center>
    </body>
</html>
