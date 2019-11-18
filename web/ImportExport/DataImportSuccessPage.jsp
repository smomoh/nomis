<%-- 
    Document   : DataImportSuccessPage
    Created on : Aug 24, 2015, 2:55:16 PM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Xml data import</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
        font-size: 12px;
	background-image: url(images/bg.jpg);
	background-repeat: repeat-x;
}
-->
</style>
<script type="text/javascript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<link href="images/untitled.css" rel="stylesheet" type="text/css" />
<link href="images/sdmenu/sdmenu.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
a {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
}
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
}
a:hover {
	text-decoration: underline;
}
a:active {
	text-decoration: none;
}
-->
</style>
<!--<link href="sdmenu/sdmenu.css" rel="stylesheet" type="text/css" />-->
<link type="text/css" href="css/ui-darkness/jquery-ui-1.8.custom.css" rel="Stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/Enrollmentjsfile.js"></script>
<script language="javascript">
var param=""
var callerId=""
var t=0
var k=0
var j=0;
var secs=0
var secCount=10
var methodCallCount=secCount-1
var processMsg=null
var msgSecs=0
var counter=0
var timerID = null
var timerRunning = false
var delay = 4000
var totalCount=0;
var timeSpentInSecs=0;
var timeSpentInMins=0;
var totalCount=0;
var msg=" "
var dbIndicator=" "

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
                if(dbIndicator=="upload")
                {
                    dbIndicator=" "
                    InitializeTimer();
                    return
                }
             	var value=xmlhttp.responseText;
                if(value !=null)
                value=value.trim()
                if(value==null || value=="" || value==" " || value=="-" || value==";")
                {
                    //alert("value is"+value+"*")
                    if(counter>0)
                    {
                        msg="Data import complete"
                        counter=0
                    }
                    else
                    msg=" "
                    secs=0
                    showMsg("msgId",msg)
                    return false;
                }
                else if(callerId=="checkImportStatus")
                {
                    //if(secs==0)
                    secs=secCount
                    counter++
                    processMsg=value
                    //showMsg("msgId",value)
                    StartTheTimer()
                    //methodCallCount--
                }                 
	}
        else
        {
            //alert("error "+xmlhttp.responseText)
        }
}
function InitializeTimer()
{
    showMsg("msgId","Checking for import....")
    checkDataImportStatus()
    //secs = secCount
    StopTheClock()
    StartTheTimer()
}
function StopTheClock()
{
    if(timerRunning)
        clearTimeout(timerID)
    timerRunning = false
}
function StartTheTimer()
{
    if(secs>0)
    {
        
        //alert("methodCallCount in StartTheTimer() "+methodCallCount)
        if(methodCallCount==0)
        {
            methodCallCount=secCount-1
            checkDataImportStatus()
        }
        methodCallCount--
        //msg="Database import in progress, please wait..."
        self.status = secs
        secs = secs - 1
        if(secs%2==0)
        msg=" "
        else
        //msg=processMsg
        msg="Database import in progress, please wait..."//+secs+"--"+methodCallCount
    
        timeSpentInSecs=timeSpentInSecs+delay
        timeSpentInMins=(timeSpentInSecs/60000)
        showMsg("msgId",msg)
        
        timerRunning = true
        timerID = self.setTimeout("StartTheTimer()", delay)
        
        //if(secs ==1)
        //secs=3
    }
    else
    {
        StopTheClock()
    }
}//importStatusMsg
function showMsg(id,msg)
{
    document.getElementById(id).innerHTML=msg
    document.getElementById("importStatusMsg").innerHTML=processMsg
    //document.getElementById("timeSpentMsg").innerHTML=timeSpentInMins
}
function resetGlobalVariables()
{
    j=0
    k=0
    t=0
    secs=0
    timerID = null
    timerRunning = false
    delay = 4000
}
function processUploadedFiles()
{
   req="processImportFiles;"+"processImportFiles"
   dbIndicator="upload"
   getValuesFromDb('addcbo.do',req,'processImportFiles')
}
function checkDataImportStatus()
{
   req="checkImportStatus;"+"checkImportStatus"
   //alert("req is "+req)
   getValuesFromDb('addcbo.do',req,'checkImportStatus')
}
/*function submitForm(requiredAction,formId)
{
       setActionName(requiredAction)
       document.getElementById(formId).submit()
       return true
}


function checkDataImportStatus()
{
   req="checkImportStatus;"+"checkImportStatus"
   getValuesFromDb('addcbo.do',req,'checkImportStatus')
}*/
/*function stateChanged()
{
	if (xmlhttp.readyState==4)
	{
		var value=xmlhttp.responseText;
                if(value==" " || value=="" || value==";" || value==null)
                return false;
                            
            if(callerId=="checkImportStatus")
            document.getElementById("importStatusMsg").innerHTML=value
	}
        else
        {
            //alert("error "+xmlhttp.responseText)
        }
}*/
function loadStartupProcesses()
{
    processUploadedFiles();
    InitializeTimer();
    //checkDataImportStatus();
    //checkProcessedImportFiles();
    
    //
    //
}
</script>
<link href="kidmap-default.css" rel="stylesheet" type="text/css" />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
<link href="images/untitled.css" rel="stylesheet" type="text/css" />
<link href="images/sdmenu/sdmenu.css" rel="stylesheet" type="text/css" />
</head>

<body onload="MM_preloadImages('images/About_down.jpg','images/Admin_down.jpg','images/Rapid_down.jpg','images/care_down.jpg','images/OVC_down.jpg');loadStartupProcesses()">

<table width="949" border="0" align="center" cellpadding="0" cellspacing="0" class="boarder">
  <!--DWLayoutTable-->
  <tr>
    <td height="117" colspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <!--DWLayoutTable-->
      <tr>
        <td width="7" height="2"></td>
          <td width="271"></td>
          <td width="137"></td>
          <td width="95"></td>
          <td width="8"></td>
          <td width="95"></td>
          <td width="8"></td>
          <td width="95"></td>
          <td width="8"></td>
          <td width="95"></td>
          <td width="8"></td>
          <td width="95"></td>
          <td width="23"></td>
        </tr>


<jsp:include page="../includes/Pagetabs.jsp" />

      <tr>
        <td height="30"></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td>&nbsp;</td>
        <td></td>
        <td></td>
        <td></td>
        </tr>

      <tr>
        <td height="17"></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td><jsp:include page="../Logout.jsp" /></td>
          <td></td>
        </tr>
      <tr>
        <td height="3" colspan="13" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#038233">
          <!--DWLayoutTable-->
          <tr>
            <td width="945" height="2"></td>
            </tr>
          <!--DWLayoutTable-->
          <tr>
            <td height="1"></td>
              </tr>
        </table></td>
        </tr>

    </table></td>
  </tr>
  <tr>
    <td width="931" height="251" valign="top">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">

      <tr>
        
        <td width="231" rowspan="2" valign="top"  bgcolor="#038233">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#038233">
          <!--DWLayoutTable-->
          <tr>
            <td width="231" height="28" valign="top">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="231" height="28"><img src="images/dataentry.jpg" width="231" height="28" /></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td height="85" valign="top">
                <div style="float: left" id="my_menu" class="sdmenu">
                    <div>
                        <jsp:include page="../Navigation/ImportExportLinks.jsp"/>
                    </div>
              </div>

            </td>
          </tr>
          <tr>
            <td height="30" valign="top">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="180" height="30"><img src="images/reports.jpg" width="231" height="30" /></td>
                    </tr>
            </table></td>
          </tr>
          <tr>
            <td height="75" valign="top">
                <div style="float: left" id="my_menu" class="sdmenu">
                    <div>
                        <a href="#">&nbsp; </a>
                        <a href="#">&nbsp; </a>
                        <a href="#">&nbsp; </a>
                        <a href="#">&nbsp; </a>
                        <a href="#">&nbsp; </a>
                        <a href="#">&nbsp; </a>
                    </div>
              </div>

            </td>
          </tr>
      </table></td>
    <td width="10">&nbsp;</td>
      <td width="659" >
          <br/>
            <center>
            <div style="width: 700px; height: 450px; overflow: scroll; border: 1px black solid;" class="paramPage" >
                <table style=" width: 700px; text-align: center; color: white; min-height: 450px;" >
                    <%--<tr><td> <h3>${dbImportMsg}</h3></td><td> &nbsp;</td></tr>--%>
                    <tr><td><label id="msgId" style="font-size:medium; font-weight: bold; color: white"> </label></td></tr>
                    
                    <tr><td><label id="importStatusMsg" style="color:white;"> </label></td></tr>
                    <%--<tr><td>Time spent: <label id="timeSpentMsg" style="color:white"> </label> mins</td></tr>--%>
                    <logic:present name="resultList">
                        <logic:iterate name="resultList" id="result">
                            <tr><td align="left">&nbsp;&nbsp; ${result[0]}</td><td align="left"> ${result[1]}</td></tr>
                        </logic:iterate>
                    </logic:present> 
                            
                            <tr><td><html:button property="msgbtn" value="Show Progress" style="height: 25px;" onclick="loadStartupProcesses()"/></td></tr>
                </table>
            </div>
        </center>
          &nbsp;</td>
      </tr>

    </table>    </td>
    <td width="18">&nbsp;</td>
  </tr>
  <tr>
    <td height="25" colspan="2" valign="top">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#038233">
      <!--DWLayoutTable-->
      <tr>
        <td width="945" height="25" class="copyright"><jsp:include page="../includes/Version.jsp"/></td>
        </tr>
    </table></td>
  </tr>
</table>
  </body>
</html>