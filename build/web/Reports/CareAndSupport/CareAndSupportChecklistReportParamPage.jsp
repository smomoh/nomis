<%-- 
    Document   : CareAndSupportChecklistReportParamPage
    Created on : Mar 5, 2018, 2:18:33 PM
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
        <title>Care and support checklist report</title>
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
        </script>
    </head>
    <body>
        <br/><br/>
        <center>
            <html:form action="/candsreport" method="post">
                <html:hidden property="actionName" styleId="actionName"/>
        <!--<div id="enrlRecQueryDiv" class="paramPage" style="height: 220px; width: 400px;  margin-top: 100px;">-->
        <span>
        <center>
        <table class="paramPage">
            <tr><td>  </td><td> </td><td>&nbsp; </td><td> </td></tr> <!--onchange="getLga(this.value,'lgaForReports')"-->
            <tr><td class="orglabel" align="left">State </td><td colspan="3">
                    <html:select property="stateCode" styleId="stateCode" style="width: 290px;" onchange="setActionName('lga'); forms[0].submit();" ><html:option value="All">All</html:option>
                        <logic:iterate id="stateObj" name="stateListForReports">
                        <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                        </logic:iterate>
                    </html:select>
        </td></tr>
            <tr><td class="orglabel" align="left">LGA </td><td colspan="3">
                    <html:select property="lgaCode" styleId="lgaCode" style="width: 290px;" onchange="setActionName('cbo');forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:iterate id="lgaObj" name="candslgaList">
                        <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                        </logic:iterate>
                        </html:select> </td></tr>
            <tr><td class="orglabel" align="left">CBO </td><td colspan="3">
                    <html:select property="cboCode" styleId="cboCode" style="width: 290px;" onchange="setActionName('ward'); forms[0].submit()" ><html:option value="All">
                        </html:option>
                        <logic:iterate id="cboObj" name="candsorgList">
                      <html:option value="${cboObj.orgCode}">${cboObj.orgName}</html:option>
                        </logic:iterate>
                    </html:select> </td></tr> <%--<jsp:include page="..includes/WardName.jsp" />--%>
            <tr><td class="orglabel" align="left">Ward </td><td colspan="3">
                    <html:select property="communityCode" styleId="communityCode" style="width: 290px;" ><html:option value="All"> </html:option>
                        <logic:iterate id="wardObj" name="candswardList">
                        <html:option value="${wardObj.ward_code}">${wardObj.ward_name}</html:option>
                        </logic:iterate>
                    </html:select> </td></tr>
            <tr><td class="orglabel" align="left">Partner </td><td colspan="3">
                    <html:select property="partnerCode" styleId="partnerCode" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                    <logic:iterate id="partner" name="partnerList">
                      <html:option value="${partner.partnerCode}">${partner.partnerName}</html:option>
                        </logic:iterate>
                    </html:select> </td>
            </tr>
            <tr><td class="title" align="left"><label style=" margin-right: 5px; color:white">From</label></td><td>
                    <html:select property="startMth" styleId="startMth" style="width: 70px;">
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
                    <html:select property="startYear" styleId="startYear" style="width: 55px;">
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
                <td><label style=" margin-right: 5px; color:white">To </label> <html:select property="endMth" styleId="endMth" style="width: 70px;">
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
                    <html:select property="endYear" styleId="endYear" style="width: 65px;">
                            <html:option value="2008">2008 </html:option>
                            <html:option value="2009">2009 </html:option>
                            <html:option value="2010" filter="2010">2010 </html:option>
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
                        </html:select></td></tr>
            
            <tr><td colspan="4" align="center"><html:submit value="Submit" onclick="setActionName('report')" style="width: 70px;" />&nbsp;  </td></tr>
        </table>
        </center>
        </span>
    <!--</div>-->
            </html:form>
        </center>
    </body>
</html>
