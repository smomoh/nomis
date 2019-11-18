<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@page import="com.fhi.nomis.nomisutils.*;" %>
<%
    LoadUpInfo loadup=new LoadUpInfo();
    loadup.getLgasPerState(session);
    loadup.setParamAttributes(request);
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>States</title>
<link rel="stylesheet" href="SpryAssets/LookupTableStyle.css"  />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="ExternalScript.js" > </script>
<SCRIPT LANGUAGE="JavaScript" >
    var callerId=""

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
<body class="setuppagebg" >

<html:form action="/addCbo">
    <html:errors/>
    <html:hidden property="actionName" styleId="buttonId" />
    <html:hidden property="fax" styleId="fax" styleClass="textField" style="margin-left: 55px; width:125px;"/>
    <html:hidden property="website" styleId="website" styleClass="textField" style="margin-left: 28px; width:125px;"/>

<table >
    <tr><td colspan="3"><label style="margin-left:50px;">  </label></td><td> </td></tr>
    <%--<tr><td><label style="margin-left:50px;">State</label></td>
        <td colspan="2">
            <html:select property="state_code" styleId="state" styleClass="textField" style="margin-left: 10px; width:300px;" onblur="enableSaveBtn()" onchange="getLga(this.value)" onclick="getState()">
                <html:option value=""> </html:option>
                <logic:iterate id="stat" name="states">
            <html:option value="${stat.state_code}"> ${stat.name}</html:option>
        </logic:iterate>
            </html:select>
            </td></tr>--%>

    <tr><td><label style="margin-left:50px;">LGA</label></td>
        <td colspan="2">
             <html:select property="lga_code" styleId="lgaCode" styleClass="textField" style="margin-left: 10px; width:300px;" onchange="setActionName('cboList');forms[0].submit()" >
                 <html:option value=""></html:option>
                 <logic:iterate id="lga" name="lgas">
                 <html:option value="${lga.lga_code}"> ${lga.lga_name}</html:option>
                 </logic:iterate>
             </html:select>
        </td></tr>

    <tr><td><label style="margin-left:50px;">CBO code</label></td><td colspan="2"> <html:text property="cbo_code" styleId="cbo_code" styleClass="textField" style="margin-left: 10px; width:75px;" value="${cboDetails[0]}"/></td></tr>
    <tr><td><label style="margin-left:50px;">CBO name</label></td><td colspan="2"> <html:text property="cbo_name" styleId="cbo" styleClass="textField" style="margin-left: 10px; width:300px;" value="${cboDetails[1]}"/></td></tr>
    <tr><td><label style="margin-left:50px;">Telephone</label></td><td colspan="2"> <html:text property="phone" styleId="phone"  styleClass="textField" style="margin-left: 10px; width:125px;" value="${cboDetails[3]}"/> <label style="margin-left:3px;">Email</label> <html:text property="email" styleId="email" styleClass="textField" style="width:131px;" value="${cboDetails[4]}"/></td></tr>
    <tr><td><label style="margin-left:50px;">Address</label></td><td colspan="2"> <html:text property="address" styleId="address" styleClass="textField" style="margin-left: 10px; width:300px;" value="${cboDetails[5]}"/></td></tr>

    <tr><td></td></tr>
<tr><td>  </td><td>
        <!--<select name="cboList" id="cboList" style="height: 240px; width: 300px; margin-left: 10px; margin-right:60px; font-size:9pt;" multiple onDblClick="setModify()"><option> </option></select>-->
    <html:select property="cboList" styleId="cboList" style="height: 270px; width: 300px; margin-left: 10px; margin-right:60px; font-size:9pt;" multiple="true" ondblclick="setActionName('cboDetails');forms[0].submit()">
            <logic:iterate id="cbo" name="cbolistperlga">
            <html:option value="${cbo.orgCode}">${cbo.orgName} </html:option>
        </logic:iterate>
        </html:select>
    </td><td>
<fieldset style="margin-left:0px; width:70px;">
    <html:submit style="width:75px" value="Save" onclick="return setBtnName('save')" disabled="${cbosavedisabled}"/><br><br>
<html:submit style="width:75px" value="Modify..." disabled="${cboModifyDisabled}" onclick="return setBtnName('modify')" /><br><br>
<html:submit style="width:75px" value="Delete..." disabled="${cboModifyDisabled}" onclick="return setBtnName('delete')" /><br><br>

</fieldset>
 </td></tr>

</table>
</html:form>
    <%
        session.removeAttribute("cbolistperlga");
        session.removeAttribute("cboDetails");
        session.removeAttribute("cboModifyDisabled");
        session.removeAttribute("cbosavedisabled");
    %>
</body>
</html>