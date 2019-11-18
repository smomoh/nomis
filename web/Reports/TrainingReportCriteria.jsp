<%-- 
    Document   : TrainingReport
    Created on : Mar 17, 2015, 9:34:55 PM
    Author     : Siaka
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

<%--<%@page import="com.fhi.kidmap.business.*;" %>


    LoadUpInfo loadup=new LoadUpInfo();
    loadup.getAllStates(session);
    loadup.setParamAttributes(request);
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
        <link type="text/css" href="css/ui-darkness/jquery-ui-1.8.custom.css" rel="Stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
        <title>Training report</title>
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
$(function() {
                $("#startDate").datepicker();
                $("#endDate").datepicker();
            });
    function setActionName(val)
    {
        document.getElementById("actionName").value=val
    }

</script>
    </head>
    <body>
        <br/><br/>
        <center>
            <html:form action="/trainingReportAction" method="post">
                <html:hidden property="actionName" styleId="actionName"/>

                <!--<div styleId="sumstatDivPerMth" class="paramPage" style="height: 160px; width: 400px; margin-top: 100px;">-->
        <span>
        <center>
        <table class="paramPage">
            <tr><td colspan="4" class="title" align="center">Report criteria</td></tr>
            
            <tr><td class="orglabel" align="left">Organization </td><td colspan="3">
                    <html:select property="organizationCode" styleId="organizationCode" styleClass="fieldcellinput" style="width:470px">
                                  <html:option value="All"> </html:option>
                                  <html:optionsCollection name="TrainingReportForm" label="orgName" property="organizationList" value="orgCode"/>
                              </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">Training type </td><td colspan="3">
                    <html:select property="nameOfTraining" styleId="nameOfTraining" styleClass="fieldcellinput" style="width:470px">
                        <html:option value="All"> </html:option>
                        <html:optionsCollection name="TrainingForm" label="trainingName" property="trainingInfoList" value="recordId"/>
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">Category </td><td colspan="3">
                    <html:select property="category" styleId="category" styleClass="fieldcellinput" style="width:470px" onchange="setActionName('designation'); forms[0].submit()">
                                <html:option value="All"> </html:option>
                                <html:optionsCollection name="TrainingReportForm" label="categoryName" property="trainingCategoryList" value="categoryId"/>
                    </html:select>
                </td>
            </tr>
            <tr><td class="orglabel" align="left">Designation </td><td colspan="3">
                    <html:select property="designation" styleId="designation" styleClass="smallfieldcellinput" style="width:150px">
                        <html:option value="All"> </html:option>
                        <html:optionsCollection name="TrainingReportForm" label="designationName" property="trainingDesignationList" value="designationId"/>
                    </html:select>
                </td>
            </tr>
            <tr><td class="orglabel" align="left">Partner </td><td colspan="3">
                    <html:select property="partnerCode" styleId="partnerCode" styleClass="fieldcellinput" style="width:470px">
                                  <html:optionsCollection name="TrainingReportForm" label="partnerName" property="partnerList" value="partnerCode"/>
                              </html:select>
                     </td>
            </tr>
            <tr>
                            <td class="orglabel">Training start date</td>
                            <td><html:text property="startDate" styleId="startDate" styleClass="fieldcellinput" style="width:150px;"/>
                                </td>
                            <td class="orglabel">Training end date</td>
                            <td ><html:text property="endDate" styleId="endDate" styleClass="fieldcellinput" style="width:155px;"/>
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
