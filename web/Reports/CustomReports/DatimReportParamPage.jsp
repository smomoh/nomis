<%--
    Document   : CustomReports
    Created on : Apr 6, 2015, 2:40:59 PM
    Author     : Siaka
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
        <title>Custom report</title>
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
    if(document.getElementById("periodChkBox").checked==true)
    {
        enableComponents("activeMonth2")
        enableComponents("activeYear2")
    }
    else
    {
        disableComponents("activeMonth2")
        disableComponents("activeYear2")
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
            <html:form action="/datimreport" method="post">
                <html:hidden property="actionName" styleId="actionName"/>
                <!--<div id="mthlySummFormDiv" class="paramPage" style="height: 210px; width: 370px;">-->
        <span>
        <center>
        <table class="paramPage">
            <tr><td colspan="4" class="title" align="center"> <logic:present name="datimpdfmsg">
                        ${datimpdfmsg}
                    </logic:present></td></tr>
            <tr><td colspan="4" class="title" align="center"> DATIM reports</td></tr>
            <tr><td class="orglabel">State </td><td colspan="3">
                    <html:select property="state" styleId="state" style="width: 290px;" onchange="setActionName('lga');forms[0].submit()">
                       <html:option value="All">All</html:option>
                       <html:optionsCollection name="DatimReportForm" property="stateList" label="name" value="state_code" />
                       
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel">Lga </td><td colspan="3">
                    <html:select property="lgaCode" styleId="lgaCode" style="width: 290px;" onchange="setActionName('cbo');forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <html:optionsCollection name="DatimReportForm" property="lgaList" label="lga_name" value="lga_code" />
                    </html:select>

                </td>
            </tr>
            <tr><td class="orglabel">CBO </td><td colspan="3">
                    <html:select property="cbo" styleId="cbo" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                        <html:optionsCollection name="DatimReportForm" property="organizationList" label="orgName" value="orgCode" />
                    </html:select>

                </td>
            </tr>
            <tr><td class="orglabel" align="left">Group </td><td colspan="3">
                    <html:select property="orgUnitGroup" styleId="orgUnitGroupId" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                        <html:optionsCollection name="DatimReportForm" property="organizationUnitGroupList" label="orgUnitGroupName" value="orgUnitGroupId" />
                        </html:select>
                </td>
            </tr>
            <tr><td class="orglabel" align="left">Partner </td><td colspan="3">
                    <html:select property="partnerCode" styleId="partnerCode" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                        <html:optionsCollection name="DatimReportForm" property="partnerList" label="partnerName" value="partnerCode" />
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">Type </td>
                <td colspan="3">
                    <html:select property="reportType" styleId="reportType" style="width: 290px;" >
                        <%--<html:option value="mer">MER report form</html:option>
                        <html:option value="datim">Datim report form</html:option>--%>
                        <html:option value="datim2017">Datim report form</html:option>
                        <html:option value="writedatim2017ToPDF">Write Datim report to PDF</html:option>
                        <html:option value="datim2017ExcelDownload">Download Datim report in Excel</html:option>
                        <%--<html:option value="mer2018">New MER report form</html:option>--%>
                    </html:select>
                </td>
            </tr>
           <%--<tr><td class="title">&nbsp;</td><td colspan="3" class="title" align="left"> <html:checkbox property="periodChkBox" styleId="periodChkBox" value="on" onclick="enableDateFields()"/>Generate periodic report  </td></tr>--%>
           
            <tr>
                <td class="title"><label style=" margin-right: 5px; color:white">From</label></td>
                <td colspan="2" align="left">
                    <html:select property="activeMonth1" styleId="activeMonth1">
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
                    <html:select property="activeYear1" styleId="activeYear1">
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
                        </html:select> 
                <!--</td>
            </tr>

            <tr><td class="title"><label style=" margin-right: 5px; color:white">To</label></td>
                <td colspan="2" align="left">-->
                    To <html:select property="activeMonth2" styleId="activeMonth2">
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
                    <html:select property="activeYear2" styleId="activeYear2">
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
                        </html:select> </td>
            </tr>
            
            
 <tr><td colspan="4" align="center"><html:submit property="mthlySummBtn" value="Submit" onclick="setActionName('report')" style="width: 70px;" />
    <%--html:submit property="mthlySummBtn" value="Write to file" onclick="setActionName('reportToFile')" style="width: 100px;" /></td></tr>--%>
        </table>
        </center>
        </span>
    <!--</div>-->

            </html:form>
        </center>
    </body>
</html>


