<%-- 
    Document   : SiteTransition
    Created on : Nov 20, 2016, 10:44:37 AM
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
        <link type="text/css" href="css/ui-darkness/jquery-ui-1.8.custom.css" rel="Stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
        <title>Site transition</title>
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
                $("#dateOfTransition").datepicker();
            });
            function setActionName(val)
            {
                if(val=="transitionSite")
                {
                    if(confirm("This operation will transition all records in this organization unit. Do you want to proceed?"))
                    {
                        document.getElementById("actionName").value=val
                        return true
                    }
                    else 
                    return false
                }
                document.getElementById("actionName").value=val
            }

        </script>
    </head>
    <body>
        <br/><br/>
        <center>
            <html:form action="/sitetransitionaction" method="post">
                <html:hidden property="actionName" styleId="actionName"/>

                
        <span>
        <center>
        <table class="paramPage">
            <%--<tr><td colspan="4" class="title" align="center">${dbsource}----</td></tr>--%>
            <tr><td colspan="4" class="title" align="center">Site transition</td></tr>
            <tr><td colspan="4" class="title" align="center"><label>${stmsg}</label></td></tr>
            <tr><td class="orglabel" align="left">State </td><td colspan="3">
                    <html:select property="stateCode" styleId="state" style="width: 290px;" onchange="setActionName('lga'); forms[0].submit()"><html:option value="All">All</html:option>
                        <logic:iterate id="stateObj" name="states">
                        <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                        </logic:iterate>
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">LGA </td><td colspan="3"><html:select property="lgaCode" styleId="lga" style="width: 290px;" onchange="setActionName('cbo'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:iterate id="lgaObj" name="stlgaList">
                        <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                        </logic:iterate>
                        </html:select> </td></tr>
            <tr><td class="orglabel" align="left">CBO </td><td colspan="3">
                    <html:select property="orgCode" styleId="cbo" style="width: 290px;" onchange="setActionName('ward'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                    <logic:iterate id="cboObj" name="orgList">
                      <html:option value="${cboObj.orgCode}">${cboObj.orgName}</html:option>
                        </logic:iterate>
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left"><jsp:include page="../includes/WardName.jsp" /> </td><td colspan="3">
                    <html:select property="communityCode" styleId="ward" style="width: 290px;" ><html:option value="All"> </html:option>
                        <logic:iterate id="wardObj" name="stwardList">
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
            <tr><td class="orglabel" align="left">Date of site transition </td><td colspan="3">
                    <html:text property="dateOfTransition" styleId="dateOfTransition" style="width: 290px;" readonly="true"/>
                        </td>
            </tr>
            <tr><td class="orglabel" align="left">Organization site is transition to </td><td colspan="3">
                    <html:text property="organizationSiteTransitionedTo" styleId="organizationSiteTransitionedTo" style="width: 290px;" />
                        </td>
            </tr>
<tr><td colspan="4" align="center"><html:submit property="submitbtn" value="Submit" onclick="setActionName('transitionSite')" style="width: 70px; margin-left: 70px;" /></td></tr>
        </table>
        </center>
        </span>
    <!--</div>-->

            </html:form>
        </center>
    </body>
</html>