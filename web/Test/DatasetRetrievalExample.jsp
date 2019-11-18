<%-- 
    Document   : DatasetRetrievalExample
    Created on : Mar 22, 2018, 9:16:23 AM
    Author     : smomoh
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <title>
      Dataset Retrieval
    </title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script src="http://code.jquery.com/jquery-1.9.1.js" type="text/javascript"></script>
    <script src="http://code.highcharts.com/highcharts.js" type="text/javascript"></script>
    <script src="http://code.highcharts.com/modules/exporting.js" type="text/javascript"></script>
    <script type="text/javascript">
function getValuesFromDb(url,str)
{

    xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
    {
        alert ("Browser does not support HTTP Request");
        return;
    }
	//alert(url)
    url=url+str;
    //url=url+"?sid="+Math.random();
    alert(url)
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
	alert("Inside stateChanged "+xmlhttp.readyState)
	if (xmlhttp.readyState==4)
	{
		var value=xmlhttp.responseText;
		alert("value is "+value)
                //if(lgas==" " || lgas=="" || lgas==";" || lgas==null)
                //return false;
                //alert(callerId)
            
            document.getElementById("container").innerHTML=value
            
	}
        else
        {
           alert("error "+xmlhttp.responseText)
        }
}
function getDataValues(url,str)
{
    getValuesFromDb(url,str)
}
    </script>
  </head>
  <body>
    <div id="container" style="min-width: 400px; height: 400px; margin: 0 auto"></div>
	<a href="https://dhis-backup.sidhas.org/fhi360/api/dataValueSets?dataSet=yyO78xvlnc0&startDate=2017-01-01&endDate=2017-01-31&orgUnit=W4QQK1UMCbp" target="_blank">Open DHIS2</a>
  <input type="button" value="Get Dataset values" onclick="getValuesFromDb('https://dhis-backup.sidhas.org/fhi360/api/dataValueSets','?dataSet=yyO78xvlnc0&startDate=2017-01-01&endDate=2017-01-31&orgUnit=W4QQK1UMCbp')" />
  </body>
</html>

