<%-- 
    Document   : EligibilitySetup
    Created on : Apr 4, 2011, 11:06:51 AM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%--<%@page import="com.fhi.kidmap.business.*;" %>
<%
    LoadUpInfo loadup=new LoadUpInfo();
    loadup.getAllEligibilityCriteria(session);
    loadup.setParamAttributes(request);
%>--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Eligibily Criteria setup</title>
<link rel="stylesheet" href="SpryAssets/LookupTableStyle.css"  />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" >
function setBtnName(name)
{
     if(name=="save")
     {
            setActionName(name)
            return true
     }
     if(confirm("Are you sure you want to "+name+" this record?"))
     {
            setActionName(name)
            return true
     }
       return false
}
function setActionName(val)
{
     document.getElementById("actionName").value=val
}
</SCRIPT>
</head>
<body class="setuppagebg">

<html:form action="/eligibility">
    <html:errors/>
    <html:hidden property="actionName" styleId="actionName" />

<table >
   <%-- <tr><td><label class="label" style="margin-left:50px;">State code</label></td><td> <html:text property="state_code" styleId="code" styleClass="textField" style="margin-left: 10px; width:100px;" value="${stateDetails[0]}"/> </td><td>  </td></tr>--%>
    <tr><td><label style="margin-left:40px;">Criteria name</label></td><td> <html:text property="eligibilityName" styleId="eligibilityName" styleClass="textField" style="margin-left: 10px; width:300px;"/> </td><td>  </td></tr>
<tr><td>  </td><td>
       <html:select property="eligibilityList" styleId="eligibilityList" style="height: 290px; width: 300px; margin-left: 10px; margin-right:60px; font-size:9pt;" multiple="true" ondblclick="setActionName('eligibilityDetails');forms[0].submit()">
        <logic:iterate id="eligibilityCriteria" name="listofeligibilitycriteria">
            <html:option value="${eligibilityCriteria.id}"> ${eligibilityCriteria.eligibilityName}</html:option>
        </logic:iterate>
       </html:select>
        </td><td>
<fieldset style="margin-left:0px; width:70px;">
    <html:submit style="width:75px" value="Save" onclick="return setBtnName('save')" styleId="save" disabled="${eligibilitysavedisabled}"/><br><br>
    <html:submit style="width:75px" styleId="modify" value="Modify..."  onclick="return setBtnName('modify')" disabled="${eligibilityModifyDisabled}"/><br><br>
    <html:submit style="width:75px" value="Delete..." styleId="deleteBtn" disabled="${eligibilityModifyDisabled}" onclick="return setBtnName('delete')" /><br><br>

</fieldset>
 </td></tr>

</table>
</html:form>
    
</body>
</html>
