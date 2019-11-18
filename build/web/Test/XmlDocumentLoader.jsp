<%-- 
    Document   : XmlDocumentLoader
    Created on : Mar 22, 2018, 10:38:01 AM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>XML Loader</title>
        <script src="http://code.jquery.com/jquery-1.9.1.js" type="text/javascript"></script>
        <script src="http://code.highcharts.com/highcharts.js" type="text/javascript"></script>
        <script src="http://code.highcharts.com/modules/exporting.js" type="text/javascript"></script>
        <script type="text/javascript">
  $(function(){
    //left out irrelevant code which creates the var "parameters"
    loadXMLDoc(function(results) {
      // this function will be called if the xmlhttprequest received a result
      document.getElementById("xmlresults").innerHTML = results;
    }, function() {
      // this function will be called if xhr failed
      document.getElementById("xmlresults").innerHTML = "No results.";
    });
  });
  function loadXMLDoc() 
  {
    alert("loadXMLDoc has been called.");
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange=function() 
    {
        alert("xmlhttp.readyState is "+xmlhttp.readyState);
      if (xmlhttp.readyState==4) 
      {
          alert("xmlhttp.status is "+xmlhttp.status);
          document.getElementById("xmlresults").innerHTML = xmlhttp.responseText;
        if(xmlhttp.status==200) {
          
          alert("Got the response!");
          //onComplete(xmlhttp.responseText);
        } 
        else 
        {
            alert("Error! "+xmlhttp.status);
          //onError();
        }
      }
    };

    var url =  "https://www.google.com";
    xmlhttp.open("GET",url,true);
    xmlhttp.send();
  }
  
</script>

    </head>
    <body>
        <div id="xmlresults" > </div>
    </body>
</html>
