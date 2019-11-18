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
  // code for IE7+, Firefox, Chrome, Opera, Safari
     return new XMLHttpRequest();
    }
    if (window.ActiveXObject)
    {
        // code for IE6, IE5
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
    return null;
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
function getLga(stateId)
{
    var req=stateId+";"+"lga"
    getValuesFromDb('addcbo.do',req,'lga')
}
function getCbo(lgaId)
{
    var req=lgaId+";"+"cbo"
    getValuesFromDb('addcbo.do',req,'cbo')
}
function getwards(cboId)
{
    var req=cboId+";"+"ward"
    getValuesFromDb('addcbo.do',req,'ward')
}
function setModify(id,id2)
{
	var btnVar=document.patForm;
	var dropIndex=document.getElementById(id).selectedIndex;
	var dropValue=document.getElementById(id).options[dropIndex].text;
	document.getElementById(id2).value=dropValue;
	btnVar.modify.disabled=false;
	btnVar.deleteBtn.disabled=false;
}
function enableSaveBtn()
{
	var btnVar=document.patForm;
	if(btnVar.states.value!="")
	{
		btnVar.save.disabled=false;
	}
	else
	{
		btnVar.save.disabled="disabled";
		btnVar.modify.disabled="disabled";
		btnVar.deleteBtn.disabled="disabled";	
	}
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