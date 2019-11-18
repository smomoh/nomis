<%-- 
    Document   : CreateAdminAccount
    Created on : Apr 28, 2011, 10:57:12 PM
    Author     : COMPAQ USER
--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@page import="com.fhi.nomis.nomisutils.*;" %>

<%
    LoadUpInfo loadup=new LoadUpInfo();
    loadup.getAllUsers(session);
    loadup.getAllStatesAsObjects(session);
    loadup.setParamAttributes(request);
    loadup.getPartners(session);
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>User accounts</title>
<link rel="stylesheet" href="SpryAssets/LookupTableStyle.css"  />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" >
var callerId=""
function getValuesFromDb(url,str,id)
{
	callerId=id;
	xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
    {
        alert ("Browser does not support HTTP Request");
        return;
    }
    url=url+"?qparam="+str;
    url=url+"&sid="+Math.random();
    xmlhttp.onreadystatechange=stateChanged;
    xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function GetXmlHttpObject()
{
    if (window.XMLHttpRequest)
    {
        return new XMLHttpRequest();
    }
    if (window.ActiveXObject)
    {
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
    return null;
}
function stateChanged()
{
	if (xmlhttp.readyState==4)
	{
		var lgas=xmlhttp.responseText;
                if(lgas==" ")
                return false;
            var values=lgas.split(";")
            if(callerId=="state")
            {
               setSelectBoxValues(values,"state")
            }
            if(callerId=="userDetails")
            {
                document.getElementById("other_names").value=values[0]
                document.getElementById("surname").value=values[1]
                document.getElementById("user_name").value=values[2]
                document.getElementById("state").value=values[3]
                document.getElementById("user_role").value=values[4]
                document.getElementById("save").disabled=true
                document.getElementById("modify").disabled=false
                document.getElementById("delete").disabled=false
            }
	}
}
function setSelectBoxValues2(value, id)
{
    document.getElementById(id).options.length=0;
    var j=0;
    for(var i=0; i<value.length; i+=2)
    {
        document.getElementById(id).options[j]=new Option(value[i],value[i+1])
        j++
    }
}
function setSelectBoxValues(value, id)
{
    document.getElementById(id).options.length=0;
    document.getElementById(id).options[0]=new Option(" ")
    for(var i=0; i<value.length; i+=2)
    {
        document.getElementById(id).options[i+1]=new Option(value[i],value[i+1])
    }
    cleanUp(id)
}
function setSelectBoxText(value, id)
{
    document.getElementById(id).options.length=0;
    document.getElementById(id).options[0]=new Option(" ")
    for(var i=0; i<value.length; i++)
    {
        document.getElementById(id).options[i+1]=new Option(value[i],value[i])
    }
}
function getState()
{
    if(document.getElementById("state").options.length>1)
        return
    var req="state;state"
    getValuesFromDb('addcbo.do',req,'state')
}
function cleanUp(id)
{
    for(i=0; i<document.getElementById(id).options.length; i++ )
    {
        if(document.getElementById(id).options[i].text=="")
        {
            document.getElementById(id).options[i]=null;
        }
    }
}
function setModify()
{
	var userName=document.getElementById("userList").value
        var req=userName+";"+"userDetails"
        getValuesFromDb('addcbo.do',req,'userDetails')
}
function setBtnName(name)
{
     if(name=="save")
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
<style>
.labels
{
  font-family: arial,sans-serif;
  font-size: 12px;
}
</style>
</head>

<body style=" border: 1px blue solid">

<html:form action="/userSetup">
    <html:errors />
    <html:hidden property="actionName" styleId="buttonId" />
    <center>
        <div  >
    <table class="setuppagebg" style="border: ipx black solid">
        <tr><td colspan="4" align="center"><label class="labels" style=" margin-left: 30px; font-size: 14px; color: blue">User setup  </label></td></tr>
        <tr><td colspan="4" >&nbsp; </td></tr>

        <tr><td><label class="labels">State</label></td><td colspan="3">
                <html:select property="state_code" styleId="state" styleClass="textField" style="margin-left: 10px; width:430px;">
                <html:option value=""> </html:option>
                <logic:iterate id="stat" name="states">
                <html:option value="${stat.state_code}">${stat.name}</html:option>
                </logic:iterate>
            </html:select></td>
        </tr>
    <tr><td><label class="labels">Surname</label></td><td> <html:text property="surname" styleId="surname" styleClass="textField" style="margin-left: 10px; width:130px;"/> </td><td><label class="labels" >Other names</label></td><td> <html:text property="other_names" styleId="other_names" styleClass="textField" style="margin-left: 10px; width:155px;"/> </td></tr>
    <tr><td><label class="labels">User name</label></td><td> <html:text property="user_name" styleId="user_name" styleClass="textField" style="margin-left: 10px; width:130px;"/> </td><td><label>User role</label>    </td><td> <html:text property="user_role" styleId="user_role" styleClass="textField" style="margin-left: 10px; width:155px;" value="Administrator" readonly="true" /> </td></tr>
    <tr><td><label class="labels">Password </label>  </td><td><html:password property="pwd" styleId="pwd" styleClass="textField" style="margin-left: 10px; width:130px;"/>  </td><td><label class="labels">Confirm password </label></td><td> <html:password property="confirm_pwd" styleId="confirm_pwd" styleClass="textField" style="margin-left: 10px; width:155px;"/> </td></tr>

    <tr><td>  </td><td colspan="3"><select name="userList" id="userList" style="height: 200px; width: 430px; margin-left: 10px; font-size:9pt;" multiple onDblClick="setModify()">
                <logic:iterate id="user" name="userLists">
                <option value="${user.username}">${user.firstName} ${user.lastName}</option>
                </logic:iterate></select></td>
    </tr>
    <tr><td colspan="4" align="center" style="margin-left: 350px;">
            <center>
<fieldset style="width:250px;">
    <html:submit style="width:75px" value="Save" styleId="save" onclick="return setBtnName('save')"/>
    <html:submit style="width:75px" value="Modify..." styleId="modify" disabled="true" onclick="return setBtnName('modify')" />
    <html:submit style="width:75px" value="Delete..." styleId="delete" disabled="true" onclick="return setBtnName('delete')" />

</fieldset>
            </center>
 </td></tr>
</table>
        </div>
    </center>
</html:form>
</body>
</html>