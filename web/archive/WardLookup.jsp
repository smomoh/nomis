<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>States</title>
<link rel="stylesheet" href="SpryAssets/LookupTableStyle.css"  />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js2/ExternalScript.js" > </script>
<SCRIPT LANGUAGE="JavaScript" >
function loadValues()
{
    setActionName('refresh')
    document.getElementById("wardForm").submit()
}
</SCRIPT>
</head>
<body class="setuppagebg">

    <html:form action="/addWard" styleId="wardForm">
    <html:errors/>
    <html:hidden property="actionName" styleId="actionName" />
    <html:hidden property="hiddenWardCode" styleId="hiddenWardCode" />
    <input type="hidden" id="hiddencbo_code" />

<table >
    <tr><td colspan="3"><label style="margin-left:50px;">  </label></td><td> </td></tr>
    <tr><td><label style="margin-left:50px;">LGA</label></td><td colspan="2">
            <%--
            <html:select property="state_code" styleClass="textField" styleId="state" style="margin-left: 10px; width:125px;" onblur="enableSaveBtn()" onchange="getLga(this.value)">
                
                <html:option value="${sessionScope.state.state_code}">${sessionScope.state.name}</html:option></html:select>

            <label style="margin-left:7px;">LGA</label>--%> 
            <html:select property="lga_code" styleClass="textField" styleId="lgaCode" style="margin-left: 10px;  width:300px;" onchange="setActionName('cboList'); forms[0].submit()">
                    <html:option value=""> </html:option>
                    <logic:iterate id="lgaObj" name="lgas">
                    <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                    </logic:iterate>
                </html:select></td></tr>
    <tr><td><label style="margin-left:50px;">CBO</label></td><td colspan="2"> 
    <html:select property="cbo_code" styleId="cbo_code" styleClass="textField" style="margin-left: 10px; width:300px;" onchange="setActionName('wardList');forms[0].submit()">
        <html:option value=""> </html:option>
        <logic:iterate id="cbo" name="cbolistperlga">
            <html:option value="${cbo.orgCode}">${cbo.orgName} </html:option>
        </logic:iterate>
    </html:select></td></tr> <%--value="${wardDetails[1]}"  value="${wardDetails[0]}" --%>
    <tr><td><label style="margin-left:50px;">Code</label></td><td colspan="2"> <html:text property="ward_code" styleId="ward" styleClass="textField" style="margin-left: 10px; width:75px;" /><label style="margin-left:20px;">Name</label> <html:text property="ward_name" styleClass="textField" styleId="wardName" style="margin-left: 10px; width:155px;" /></td></tr>
    <tr><td>  </td><td><html:select property="wardList" styleId="wardList" style="height: 280px; width: 300px; margin-left: 10px; margin-right:60px; font-size:9pt;" multiple="true" ondblclick="setActionName('wardDetails');forms[0].submit()">
            <logic:iterate id="wardObj" name="wardlistpercbo">
                <html:option value="${wardObj.ward_code}">${wardObj.ward_name}</html:option>
            </logic:iterate>
            
            </html:select></td><td>
<fieldset style="margin-left:0px; width:70px;">
    <html:submit style="width:75px" value="Save" onclick="return setBtnName('save')" styleId="save" disabled="${wardsavedisabled}"/><br><br>
    <html:submit style="width:75px" value="Modify..." disabled="${wardModifyDisabled}" onclick="return setBtnName('modify')" styleId="modify" /><br><br>
    <html:submit style="width:75px" value="Delete..." disabled="${wardModifyDisabled}" onclick="return setBtnName('delete')" styleId="delete" /><br><br>
    

</fieldset>
 </td></tr>

</table>
</html:form>
   
</body>
</html>