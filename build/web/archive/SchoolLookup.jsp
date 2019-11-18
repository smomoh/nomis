<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

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
            document.getElementById("actionName").value = name
            return true
     }
     if(confirm("Are you sure you want to "+name+" this record?"))
     {
            document.getElementById("actionName").value = name
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
<body class="setuppagebg" style=" border: 1px blue solid">

<html:form action="/AddOvcSchool">
    <html:errors/>
    <html:hidden property="actionName" styleId="actionName" />
    <html:hidden property="id" styleId="code" />

    <table width="635">
        <tr><td colspan="3" height="30" align="center"><label style="font-size: 14px; font-weight: bold; color: blue">School setup</label></td><td> </td></tr>
    <tr><td width="70"><label >State</label></td>
        <td colspan="3">
            <html:select property="state" styleClass="textField" styleId="state" style="width:418px;" onblur="enableSaveBtn()" onchange="getLga(this.value)">        
                <html:option value="${state.state_code}">${state.name}</html:option>
            </html:select>
        </td></tr>
    <tr><td>LGA</td>
        <td colspan="3">
            <html:select property="lga" styleClass="textField" styleId="lga" style="width:418px;">
                <logic:iterate id="lgaObj" name="lgas">
                <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name} </html:option>
                </logic:iterate>
            </html:select></tr>
    <tr><td ><label>School type</label></td><td colspan="2">
            <html:select property="type" styleId="type" styleClass="textField" style="width:125px;" onblur="enableSaveBtn()">
                <html:option value=""> </html:option><html:option value="public">Public</html:option><html:option value="private">Private</html:option>
            </html:select>
                <label style=" margin-left: 10px;">School name</label>
                <html:text property="name" styleClass="textField" styleId="name" style="margin-left: 1px; width:200px;"/>
        </td></tr>
    <tr><td colspan="3" height="15"><label>Address</label><html:textarea property="address" styleId="address" styleClass="textField" style="margin-left: 26px; width:418px;" onblur="enableSaveBtn()"> </html:textarea></td></tr>
    <tr><td>  </td><td><html:select property="schoolList" styleId="schoolList" style="height: 200px; width: 418px; font-size:9pt;" multiple="true" ondblclick="setActionName('schoolDetails'); forms[0].submit()">
                <logic:iterate id="schoolObj" name="schools">
                    <html:option value="${schoolObj.id}">${schoolObj.name}</html:option>
                </logic:iterate>
                </html:select></td>
    <td>
 </td></tr>
    <tr><td> </td><td colspan="3" align="center"><fieldset style=" width:250px;">
                <html:submit style="width:75px" value="Save" onclick="return setBtnName('save');" styleId="save" disabled="${schBtnSaveDisabled}"/>
    <html:submit style="width:75px" value="Modify..." styleId="modify" onclick="return setBtnName('modify');" disabled="${schBtnModifyDisabled}"/>
    <html:submit style="width:75px" value="Delete..." styleId="delete" onclick="return setBtnName('delete');" disabled="${schBtnModifyDisabled}"/>

</fieldset></td><td> </td></tr>
</table>
</html:form>
</body>
</html>