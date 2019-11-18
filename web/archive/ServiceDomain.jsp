<%-- 
    Document   : ServiceDomain
    Created on : Jan 25, 2012, 2:32:52 PM
    Author     : smomoh
--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@page import="com.fhi.nomis.nomisutils.*;" %>
<%
    LoadUpInfo loadup=new LoadUpInfo();
    loadup.setParamAttributes(request);
    loadup.getServiceDomainList(request);

%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>States</title>
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
function setActionName(name)
{
    document.getElementById("actionName").value = name
}
</SCRIPT>
</head>
<body class="setuppagebg">

<html:form action="/addPartners">
    <html:errors/>
    <html:hidden property="actionName" styleId="actionName" />

<table >
    <tr><td colspan="3"><label style="margin-left:50px;">  </label></td><td> </td></tr>
    <tr><td><label style="margin-left:50px;">Domain</label></td>
        <td colspan="2"> <html:select property="partnerName" styleId="partnerName" styleClass="textField" style="margin-left: 10px; width:300px;">
                <html:option value=" "></html:option>
                <logic:iterate name="listofservicedomains" id="domain">
                    <html:option value="${domain.domainCode}">${domain.domainName} </html:option>
                </logic:iterate>
            </html:select></td></tr>
    <tr><td><label style="margin-left:50px;">Service name</label></td>
        <td> <html:text property="partnerCode" styleId="partnerCode" styleClass="textField" style="margin-left: 10px; width:300px;" maxlength="3" onblur="enableSaveBtn()" value="${partnerList[0].partnerCode}"/>

                      </td><td>  </td></tr>
    

<tr><td>  </td><td>
        <html:select property="partnerList"  styleId="partnerList" style="height: 290px; width: 300px; margin-left: 10px; margin-right:60px; font-size:9pt;" multiple="true" ondblclick="setActionName('partnerDetails'); forms[0].submit()">
                    <logic:iterate name="listofservicedomains" id="domain">
                    <html:option value="${domain.domainCode}">${domain.domainName} </html:option>
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
<%
session.removeAttribute("partnerList");
%>
</body>
</html>