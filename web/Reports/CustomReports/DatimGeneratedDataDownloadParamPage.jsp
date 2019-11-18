<%-- 
    Document   : DataGeneratedDataDownloadForm
    Created on : Dec 20, 2018, 6:13:36 AM
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
            <html:form action="/datimgenerateddatadownload" method="post">
                <html:hidden property="actionName" styleId="actionName"/>
                <!--<div id="mthlySummFormDiv" class="paramPage" style="height: 210px; width: 370px;">-->
        <span>
        <center>
        <table class="paramPage">
            <tr><td colspan="4" class="title" align="center"> <logic:present name="datimpdfmsg">
                        ${datimpdfmsg}
                    </logic:present></td></tr>
            <tr><td colspan="4" class="title" align="center"> DATIM report download</td></tr>
            <tr><td class="orglabel">State </td><td colspan="3">
                    <html:select property="state" styleId="state" style="width: 290px;" onchange="setActionName('lga');forms[0].submit()">
                        <logic:present name="datimReportStates">
                            <html:option value="All">All</html:option>
                            <logic:iterate name="datimReportStates" id="stateName">
                                <html:option value="${stateName}">${stateName}</html:option>
                            </logic:iterate>
                        </logic:present>
                       
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel">Lga </td><td colspan="3">
                    <html:select property="lgaCode" styleId="lgaCode" style="width: 290px;" onchange="setActionName('cbo');forms[0].submit()">
                        <logic:present name="datimReportLgaList">
                            <html:option value="All">All</html:option>
                            <logic:iterate name="datimReportLgaList" id="lgaName">
                                <html:option value="${lgaName}">${lgaName}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select>

                </td>
            </tr>
            <%--<tr><td class="orglabel">CBO </td><td colspan="3">
                    <html:select property="cbo" styleId="cbo" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                        <html:optionsCollection name="DatimReportForm" property="organizationList" label="orgName" value="orgCode" />
                    </html:select>

                </td>
            </tr>--%>
            
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
           <tr><td>Period</td> <td colspan="2">
                    <html:select property="period" styleId="period" onchange="setActionName('stateList'); forms[0].submit()">
                        <html:option value="select">Select...</html:option>
                        <logic:present name="datgenperiodList">
                            <logic:iterate name="datgenperiodList" id="period">
                                <html:option value="${period}">${period}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select> </td>
            </tr> 
            
            
 <tr><td colspan="4" align="center"><html:submit property="mthlySummBtn" value="Download report" onclick="setActionName('report')" style="width: 120px;"/>
    
        </table>
        </center>
        </span>
    <!--</div>-->

            </html:form>
        </center>
    </body>
</html>
