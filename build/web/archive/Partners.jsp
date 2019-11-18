<%-- 
    Document   : Partners
    Created on : Feb 14, 2011, 2:28:41 PM
    Author     : smomoh
--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>States</title>
<link rel="stylesheet" href="SpryAssets/LookupTableStyle.css"  />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" >
function setBtnName(name)
{
     if(name=="save" || name=="partnerDetails")
     {
            document.getElementById("buttonId").value = name
            return true
     }
     if(confirm("Are you sure you want to "+name+" this record?"))
     {
            document.getElementById("buttonId").value = name
            return true
     }
       return false
}
</SCRIPT>
</head>
<body class="setuppagebg">

<html:form action="/addPartners">
    <html:errors/>
    <html:hidden property="actionName" styleId="buttonId" />

<table >
    <tr><td colspan="3"><label style="margin-left:50px;">  </label></td><td> </td></tr>
    <tr><td><label style="margin-left:50px;">Partner code</label></td>
        <%--onblur="enableSaveBtn()" value="${partnerList[0].partnerCode}"  value="${partnerList[0].partnerName}"--%>
        <td> <html:text property="partnerCode" styleId="partnerCode" styleClass="textField" style="margin-left: 10px; width:60px;" maxlength="3"/>

                      </td><td>  </td></tr>
    <tr><td><label style="margin-left:50px;">Partner name</label></td>
        <td colspan="2"> <html:text property="partnerName" styleId="partnerName" styleClass="textField" style="margin-left: 10px; width:300px;" maxlength="100" /> </td></tr>

<tr><td>  </td><td>
        <html:select property="partnerList"  styleId="partnerList" style="height: 290px; width: 300px; margin-left: 10px; margin-right:60px; font-size:9pt;" multiple="true" ondblclick="setBtnName('partnerDetails'); forms[0].submit()">
                    <logic:iterate name="listofpartners" id="partner">
                    <html:option value="${partner.partnerCode}">${partner.partnerName} </html:option>
                    </logic:iterate>
                    </html:select></td><td>
<fieldset style="margin-left:0px; width:70px;">
    <html:submit style="width:75px" styleId="save" value="Save" disabled="${saveDisabled}" onclick="return setBtnName('save')"/><br><br>
    <html:submit style="width:75px" styleId="modify" value="Modify..." disabled="${modifyDisabled}" onclick="return setBtnName('modify')" /><br><br>
    <html:submit style="width:75px" styleId="deleteBtn" value="Delete..." disabled="${modifyDisabled}" onclick="return setBtnName('delete')" /><br><br>

</fieldset>
 </td></tr>
</table>
</html:form>

</body>
</html>