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
function populateDropdownBox(value, id)
{
    var theDropDown = document.getElementById(id)
    var numberOfOptions = theDropDown.options.length
    var j=1
    for (i=0; i<numberOfOptions; i++)
    {
        theDropDown.remove(0)
    }
    for(var i=0; i<value.length; i+=2)
    {
        document.getElementById(id).options[j]=new Option(value[i],value[i+1])

        j++
    }
}