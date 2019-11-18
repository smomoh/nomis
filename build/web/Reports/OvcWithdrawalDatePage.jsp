<%-- 
    Document   : OvcWithdrawalDatePage
    Created on : Nov 21, 2010, 9:02:58 PM
    Author     : COMPAQ USER
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
        <title>Beneficiaries withdrawn from the program</title>
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
            <html:form action="/ovcWithdrawal" method="post">
                <html:hidden property="actionName" styleId="actionName"/>
        <!--<div id="serviceReportDiv" class="paramPage" style="height: 180px; width: 360px; margin-top: 100px;">-->
        <span>
        <center><!--style="background-color: #0075B5;"-->
        <table class="paramPage">
            <tr><td>  </td><td> </td><td>&nbsp; </td><td> </td></tr> 
            <tr><td class="orglabel" align="left">State </td><td colspan="3">
                    <html:select property="state" styleId="state" style="width: 290px;" onchange="setActionName('lga'); forms[0].submit();" ><html:option value="All">All</html:option>
                        <logic:present name="stateListForReports">
                            <logic:iterate id="stateObj" name="stateListForReports">
                            <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select>
        </td></tr>
            <tr><td class="orglabel" align="left">Group </td><td colspan="3">
                    <html:select property="orgUnitGroupId" styleId="orgUnitGroupId" style="width: 290px;" onchange="setActionName('allLga'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="ssorgUnitGroupList">
                            <logic:iterate id="oug" name="ssorgUnitGroupList">
                            <html:option value="${oug.orgUnitGroupId}">${oug.orgUnitGroupName}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">LGA </td><td colspan="3"><html:select property="lga" styleId="lga" style="width: 290px;" onchange="setActionName('cbo');forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:iterate id="lgaObj" name="lgaList">
                        <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                        </logic:iterate>
                        </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">CBO </td><td colspan="3">
                    <html:select property="cbo" styleId="cbo" style="width: 290px;" onchange="setActionName('ward'); forms[0].submit()" ><html:option value="All">
                        </html:option>
                        <logic:iterate id="cboObj" name="orgList">
                      <html:option value="${cboObj.orgCode}">${cboObj.orgName}</html:option>
                        </logic:iterate>
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
            <tr><td class="orglabel" align="left">Type </td><td colspan="3">
                    <html:select property="type" styleId="type" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                        <logic:present name="withdrawalTypeList">
                            <logic:iterate name="withdrawalTypeList" id="withdrawalType">
                                <html:option value="${withdrawalType}">${withdrawalType}</html:option>
                            </logic:iterate>
                        </logic:present>
                        <%--<html:option value="ovc">OVC</html:option>
                        <html:option value="household">Household</html:option>--%>
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">Reason for withdrawal </td><td colspan="3">
                    <html:select property="reasonWithdrawn" styleId="type" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                        <logic:present name="withdrawalReasonList">
                            <logic:iterate name="withdrawalReasonList" id="reason">
                                <html:option value="${reason}">${reason}</html:option>
                            </logic:iterate>
                        </logic:present>
                        <%--<html:option value="ovc">OVC</html:option>
                        <html:option value="household">Household</html:option>--%>
                    </html:select> </td>
            </tr>
            <tr><td class="title"><label style=" margin-right: 5px; color:white">Period</label></td>
                <td align="left">
                    <html:select property="serviceMonth" styleId="serviceMonth" style="width: 70px;">
                        <html:option value="01">January </html:option>
                        <html:option value="02">February </html:option>
                        <html:option value="03">March </html:option>
                        <html:option value="04">April </html:option>
                        <html:option value="05">May</html:option>
                        <html:option value="06">June</html:option>
                        <html:option value="07">July </html:option>
                        <html:option value="08">August </html:option>
                        <html:option value="09">September </html:option>
                        <html:option value="10">October </html:option>
                        <html:option value="11">November </html:option>
                        <html:option value="12">December</html:option>
                    </html:select>
                    <html:select property="serviceYear" styleId="serviceYear" style="width: 55px;">
                            <html:option value="2008">2008 </html:option>
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
                            <html:option value="2020">2020 </html:option>
                        </html:select> </td>
                <td><label style=" margin-right: 5px; color:white">To </label> <html:select property="serviceMonth2" styleId="serviceMonth2" style="width: 70px;">
                        <html:option value="01">January </html:option>
                        <html:option value="02">February </html:option>
                        <html:option value="03">March </html:option>
                        <html:option value="04">April </html:option>
                        <html:option value="05">May</html:option>
                        <html:option value="06">June</html:option>
                        <html:option value="07">July </html:option>
                        <html:option value="08">August </html:option>
                        <html:option value="09">September </html:option>
                        <html:option value="10">October </html:option>
                        <html:option value="11">November </html:option>
                        <html:option value="12">December</html:option>
                    </html:select>
                    <html:select property="serviceYear2" styleId="serviceYear2" style="width: 55px;">
                            <html:option value="2008">2008 </html:option>
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
                            <html:option value="2020">2020 </html:option>
                        </html:select></td>
                <td>&nbsp; </td>
</tr>
            
            <tr><td colspan="4" align="center">
                    <html:submit property="withdrawalReportBtn" value="Submit" onclick="setActionName('withdrawalList')" style="width: 70px;" />&nbsp; <!--<input type="button" property="enrlQuerycancelBtn" value="Cancel" onclick="hideComponent('enrlRecQueryDiv')" style="width: 70px;" />--> </td></tr>
        </table>
        </center>
        </span>
    <!--</div>-->
            </html:form>
        </center>
    </body>
</html>

