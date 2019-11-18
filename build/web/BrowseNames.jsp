<%-- 
    Document   : BrowseNames
    Created on : Feb 16, 2011, 1:39:52 PM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">-->

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search by names</title>
        <style>
.popup
{
	position:absolute; left:250; top:10; width:250;height:150;
	border-style:solid;
	border-width:1;
	/*border-color: #3366CC;*/
	border-color:#3333FF;
	border-top-width:0px;
	background-color: #0066FF;/*#3333FF;*/
	padding:1px;
	color:red;
	font-family:Arial;
	font-weight:bold;
	font-size:10pt;
	/*z-index:2;
	visibility:hidden;*/
}
</style>
<script language="javascript">
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
            var values=xmlhttp.responseText;
            if(callerId=="partofnamestring")
                document.getElementById("ovcNames").innerHTML=values
        }
}
function filterNames(namepart)
{
    if(namepart=="" || namepart==" " || namepart==null)
    return
    req="partofnamestring;"+namepart
    getValuesFromDb('addcbo.do',req,'partofnamestring')
}
        </script>
    </head>
    <div id="pop" class="popup">
<label id="title" style="color:#FFFFFF; width:208px;">Browse</label>
<input type="text" name="selectedName" style="width:245px;" style="margin-top:0px;" onkeyup="filterNames(this.value)"><br/>
<span id="ovcNames"><select name="patNames" id="patNameId" size="14" style="width:245px; font-family:Arial, Helvetica, sans-serif; font-size:9pt;" multiple onChange="showSelected()" ondblclick="return setPatDetails(patDetails())"><option>  </option></select></span>
</div>
</html>
