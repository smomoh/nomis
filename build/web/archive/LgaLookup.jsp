<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>States</title>
<link rel="stylesheet" href="SpryAssets/LookupTableStyle.css"  />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
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
<body class="setuppagebg">

<html:form action="/addLga">
    <html:errors/>
    <html:hidden property="actionName" styleId="actionName" />

<table >
    <tr><td colspan="3" align="center"><label style=" color: white">LGA setup  </label></td><td> </td></tr>
    <tr><td><label style="margin-left:50px;">State</label></td>
        <td>
            <html:select property="state_code" styleId="state" styleClass="textField" style="margin-left: 10px; width:300px;" onchange="setActionName('lgaList');forms[0].submit()" >
                <html:option value=""> </html:option>
                <logic:iterate id="stat" name="states">
            <html:option value="${stat.state_code}"> ${stat.name}</html:option>
        </logic:iterate>
            
                </html:select></td><td>  </td></tr><%--value="${lgaDetails[0]}"  value="${lgaDetails[1]}"--%>
    <tr><td><label style="margin-left:50px;">LGA code</label></td>
        <td colspan="2"> <html:text property="lga_code" styleId="lgaCode" styleClass="textField" style="margin-left: 10px; width:60px;"  /> <label style="margin-left:7px;">LGA name</label><html:text property="lga_name" styleId="lgaName" styleClass="textField" style="margin-left: 10px; width:155px;" /></td></tr>

<tr><td>  </td><td>
        <html:select property="lgaList" styleId="lgaList" style="height: 290px; width: 300px; margin-left: 10px; margin-right:60px; font-size:9pt;" multiple="true" ondblclick="setActionName('lgaDetails');forms[0].submit()">
            <logic:iterate id="lga" name="lgalistperstate">
            <html:option value="${lga.lga_code}">${lga.lga_name} </html:option>
        </logic:iterate>
        </html:select></td><td>
<fieldset style="margin-left:0px; width:70px;">
    <html:submit style="width:75px" styleId="save" value="Save" onclick="return setBtnName('save')" disabled="${lgaSavedisabled}"/><br><br>
    <html:submit style="width:75px" styleId="modify" value="Modify..." disabled="${lgaModifyDisabled}" onclick="return setBtnName('modify')" /><br><br>
    <html:submit style="width:75px" styleId="deleteBtn" value="Delete..." disabled="${lgaModifyDisabled}" onclick="return setBtnName('delete')" /><br><br>

</fieldset>
 </td></tr>
</table>
    
</html:form>
</body>
</html>