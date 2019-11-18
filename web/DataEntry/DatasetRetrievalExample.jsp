<%-- 
    Document   : DatasetRetrievalExample
    Created on : Mar 22, 2018, 6:34:15 AM
    Author     : smomoh
--%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <title>
      Chart
    </title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script src="http://code.jquery.com/jquery-1.9.1.js" type="text/javascript"></script>
    <script src="http://code.highcharts.com/highcharts.js" type="text/javascript"></script>
    <script src="http://code.highcharts.com/modules/exporting.js" type="text/javascript"></script>
    <script type="text/javascript">
function getValuesFromDb(url)
{
    xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
    {
        alert ("Browser does not support HTTP Request");
        return;
    }
    url=url//+"?qparam="+str;
    url=url+"?sid="+Math.random();
    //alert(url+"---"+str)
    xmlhttp.onreadystatechange=stateChanged;
    xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function stateChanged()
{
	if (xmlhttp.readyState==4)
	{
		var value=xmlhttp.responseText;
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
function getDataValues(url)
{
    getValuesFromDb(url)
}
    </script>
  </head>
  <body>
    <div id="container" style="min-width: 400px; height: 400px; margin: 0 auto"></div>
  <input type="button" value="Get Dataset values" onclick="getValuesFromDb('https://dhis-backup.sidhas.org/fhi360/api/dataValueSets.json?dataSet=yyO78xvlnc0&startDate=2017-01-01&endDate=2017-03-31&dataElement=Pz6EWjhdGSM&orgUnit=W4QQK1UMCbp')" />
  </body>
</html>

