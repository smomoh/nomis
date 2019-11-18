<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%--<%@page import="com.fhi.kidmap.business.*;" %>
<%
    LoadUpInfo loadup=new LoadUpInfo();
    loadup.getAllStatesAsObjects(session);
    loadup.setParamAttributes(request);
%>--%>
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
function setActionName(val)
{
     document.getElementById("actionName").value=val
}
</SCRIPT>
</head>
<body class="setuppagebg">

<html:form action="/statesAction">
    <html:errors/>
    <html:hidden property="actionName" styleId="actionName" />

<table >
    <tr><td colspan="3" align="center"><label style=" color: white">State setup  </label></td><td> </td></tr>
    <tr><td><label class="label" style="margin-left:50px;">State code</label></td><td> <html:text property="state_code" styleId="code" styleClass="textField" style="margin-left: 10px; width:100px;" value="${stateDetails[0]}"/> </td><td>  </td></tr>
    <tr><td><label style="margin-left:50px;">State name</label></td><td> <html:text property="name" styleId="states" styleClass="textField" style="margin-left: 10px; width:300px;" value="${stateDetails[1]}" /> </td><td>  </td></tr>
<tr><td>  </td><td>
        <html:select property="stateList" styleId="stateList" style="height: 290px; width: 300px; margin-left: 10px; margin-right:60px; font-size:9pt;" multiple="true" ondblclick="setActionName('stateDetails');forms[0].submit()">
        <logic:iterate id="stat" name="states">
            <html:option value="${stat.state_code}"> ${stat.name}</html:option>
        </logic:iterate>
       </html:select>
        </td><td> <%--disabled="${statesavedisabled}"  disabled="${stateModifyDisabled}"--%>
<fieldset style="margin-left:0px; width:70px;">
    <html:submit style="width:75px" value="Save" onclick="return setBtnName('save')" styleId="save" disabled="true"/><br><br>
    <html:submit style="width:75px" styleId="modify" value="Modify..."  onclick="return setBtnName('modify')" disabled="true"/><br><br>
    <html:submit style="width:75px" value="Delete..." styleId="deleteBtn" disabled="true" onclick="return setBtnName('delete')" /><br><br>

</fieldset>
 </td></tr>

</table>
</html:form>
    <%
       session.removeAttribute("stateModifyDisabled");
       session.removeAttribute("statesavedisabled");
    %>
</body>
</html>