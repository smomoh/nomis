<%-- 
    Document   : DatabaseImportTrackerReportParamPage
    Created on : Sep 3, 2015, 11:14:48 PM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="js/Enrollmentjsfile.js"></script>
        <title>Data import tracker report</title>
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
function checkDataImportStatus()
{
   
   req="checkImportStatus;"+"checkImportStatus"
   getValuesFromDb('addcbo.do',req,'checkImportStatus')
}
function stateChanged()
{
        if (xmlhttp.readyState==4)
	{
            
		var value=xmlhttp.responseText;
                //alert("inside stateChanged()--"+value)
                if(value==" " || value=="" || value==";" || value==null)
                return false;
                            
            if(callerId=="checkImportStatus")
            {
                //alert("inside stateChanged()--checkImportStatus")
                document.getElementById("importStatusMsg").innerHTML=value
            }
            
	}
        else
        {
            //alert("error "+xmlhttp.responseText)
        }
}
function setActionName(val)
{
    document.getElementById("actionName").value=val
}

        </script>
    </head>
    <body onload="checkDataImportStatus()">
        <br/><br/>
        <center>
            <html:form action="/databaseImportTrackerReportAction" method="post">
                <html:hidden property="actionName" styleId="actionName"/>

                <!--<div styleId="sumstatDivPerMth" class="paramPage" style="height: 160px; width: 400px; margin-top: 100px;">-->
        <span>
        <center>
            <h2><label id="importStatusMsg" style="color:green; font-weight: bold"> </label> </h2>
        <table class="paramPage">
            <tr><td colspan="5" class="title" align="center">Data import tracker report</td></tr>
            <tr><td class="orglabel" align="left">User names </td><td colspan="4">
                    <html:select property="userName" styleId="userName" style="width: 303px;">
                        <html:option value="All">All</html:option>
                        <logic:iterate id="userName" name="userNameList">
                        <html:option value="${userName}">${userName}</html:option>
                        </logic:iterate>
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">Period </td>
                <td > <html:select property="startMonth">
                        <logic:iterate name="monthList" id="month">
                            <html:option value="${month.value}">${month.name}</html:option>
                        </logic:iterate>
                    </html:select></td>
                <td>
                    <html:select property="startYear">
                        <logic:iterate name="yearList" id="year">
                            <html:option value="${year}">${year}</html:option>
                        </logic:iterate>
                    </html:select></td>
                <td > <html:select property="endMonth">
                        <logic:iterate name="monthList" id="month">
                            <html:option value="${month.value}">${month.name}</html:option>
                        </logic:iterate>
                    </html:select>
                </td>
                <td>
                    <html:select property="endYear">
                        <logic:iterate name="yearList" id="year">
                            <html:option value="${year}">${year}</html:option>
                        </logic:iterate>
                    </html:select>
                </td>


            </tr>
             <tr><td colspan="4" align="center"><html:submit value="Submit" onclick="setActionName('report')" style="width: 70px; margin-left: 70px;" /></td></tr>
        </table>
        </center>
        </span>
    <!--</div>-->

            </html:form>
        </center>
    </body>
</html>

