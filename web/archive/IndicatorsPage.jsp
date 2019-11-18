<%-- 
    Document   : IndicatorsPage
    Created on : Apr 21, 2013, 9:19:25 PM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Indicator setup</title>
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

<html:form action="/indicators">
    <html:errors/>
    <html:hidden property="actionName" styleId="actionName" />
    <html:hidden property="dataElementHiddenId" styleId="dataElementHiddenId" />
    <br/><br/>
    <center>
        <label style="color: white; font-weight: bold">Data element setup</label>
        <br/><br/>
        <table >
            <tr><td align="left"><label style="margin-right: 10px; color: white">Data element name</label></td><td align="left"> <html:text property="indicatorName" styleId="indicatorName" styleClass="textField" style="width:398px;"/> </td></tr>
            <tr><td align="left"><label style="margin-right: 10px; color: white">Data element Id</label> </td><td align="left"><html:text property="dataElementId" styleId="dataElementId" styleClass="textField" style="width:150px;"/><label style="margin-left:13px; color: white">Category option</label><html:select property="categoryOptionCombo" styleId="categoryOptionCombo" styleClass="textField" style="width:134px;"><html:option value="enk4BgHWG7x">default</html:option> </html:select></td></tr>
    <tr><td align="left"><label style="margin-right: 10px; color: white">Program area</label></td><td align="left"><html:select property="indicatorType" styleId="indicatorType" styleClass="textField" style="width:150px;"><html:option value="ovc">OVC</html:option> </html:select><label style="margin-left: 15px; color: white">Type</label> <html:text property="indicatorSubtype" styleId="indicatorSubtype" styleClass="textField" style="margin-left:60px; width:134px;"/></td></tr>
    <tr><td align="left"><label style="margin-right: 10px; color: white">Query</label></td><td align="left"> <html:textarea property="query" styleId="query" styleClass="textField" style="width:395px;"/> </td></tr>
<tr><td>  </td><td>
       <html:select property="indicatorList" styleId="indicatorList" style="height: 290px; width: 400px; margin-right:10px; font-size:9pt;" multiple="true" ondblclick="setActionName('indicatorDetails');forms[0].submit()">
        <logic:iterate id="indicator" name="indicators">
            <html:option value="${indicator.indicatorId}">${indicator.indicatorName} </html:option>
        </logic:iterate>
       </html:select>
        </td></tr>
<tr><td> </td><td align="center" >
        <fieldset style="margin-left:0px; width:250px;">
    <html:submit style="width:75px" styleId="save" value="Save" disabled="${indicatorSaveDisabled}" onclick="return setBtnName('save')" />
    <html:submit style="width:75px" styleId="modify" value="Modify..."  disabled="${indicatorModifyDisabled}" onclick="return setBtnName('modify')"/>
    <html:submit style="width:75px" value="Delete..." styleId="deleteBtn" disabled="${indicatorModifyDisabled}" onclick="return setBtnName('delete')" />

</fieldset></td></tr>

</table>
    </center>
</html:form>

</body>
</html>
