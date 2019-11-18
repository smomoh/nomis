<%-- 
    Document   : DatabaseExportSummaryPage
    Created on : Aug 25, 2015, 11:16:28 AM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Data export</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
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
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
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

<script language="javascript">
var param=""
var callerId=""
var t=0
var k=0
var j=0;
var secs
var msgSecs=0
var counter=0
var timerID = null
var timerRunning = false
var delay = 4000
var dataSize=[]
var loopSize=0
var totalCount=0;
var msg=" "
var dbIndicator=" "
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
            var result=xmlhttp.responseText;
            if(result==" " || result=="" || result==";")
            return false;
            var values=result.split(";")
            if(callerId=="sizeOfData")
            {
                dataSize=values
            }
            else if(callerId=="restoreDb")
            {
                  dbIndicator="endOfImport"
                  msgSecs=0
                  msg=result
            }
	}
}
function InitializeTimer()
{
    secs = secCount
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
    var count=1

    if(loopSize==0 && t==0)
    {
        var data=parseInt(dataSize[0])
        if(data>0)
        {
          count=getLoopCount(dataSize[0],2000)
        }
        else
        {
          count=1
        }
        totalCount=totalCount+count
     }

    if(k==totalCount)
    {
        loopSize++
        var data=parseInt(dataSize[loopSize])
        if(data>0)
        count=getLoopCount(dataSize[loopSize],2000)
        else count=1
        totalCount=totalCount+count
        j++
        t=0
    }

        if (secs==0)
        {
            StopTheClock()
            if(dbIndicator=="endOfImport")
            msg="Database import completed"
            else
            {
                    msg="Click the Finish button to finish export"
                    showMsg("bcMsg",msg)
                    document.getElementById("backup").disabled="disabled"
                    document.getElementById("finisBbtn").disabled=false
                    resetGlobalVariables()

            }
        }
        else
        {
            exportDb(t,j)
            self.status = secs
            secs = secs - 1
            if(secs%2==0)
            msg=" "
            else
            msg="Database export in progress, please wait..."
            showMsg("bcMsg",msg)
            timerRunning = true
            timerID = self.setTimeout("StartTheTimer()", delay)
            k++
            t++
        }
}
function showMsg(id,msg)
{
    document.getElementById(id).innerHTML=msg
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
function getDataSize()
{
    dataSize=["${datasize[0]}","${datasize[1]}","${datasize[2]}","${datasize[3]}","${datasize[4]}","${datasize[5]}","${datasize[6]}","${datasize[7]}","${datasize[8]}","${datasize[9]}","${datasize[10]}","${datasize[11]}","${datasize[12]}","${datasize[13]}","${datasize[14]}"]
}
function exportDatabase()
{
   secCount=0
   var noOfLoops=0
   for(var g=0; g<dataSize.length; g++)
    {
        noOfLoops=getLoopCount(dataSize[g],2000)
        secCount=secCount+noOfLoops
    }
   InitializeTimer()
}
function getLoopCount(numerator,denominator)
{
    var result=1
    if(numerator > 0)
    result=Math.ceil((parseInt(numerator)/denominator))
  return result
}
function exportDb(i,j)
{
        var req=i+":"+j+":"+0
        getValuesFromDb('dataexport.do',req,'databaseExport')

}
function setActionName(val)
{
     document.getElementById("dbActionName").value=val
}
function finishExport()
{
    getValuesFromDb('dataexport.do',"zip",'databaseExport2')
     msg="Database exported to ${zipFileLocation}"
     showMsg("bcMsg",msg)
     alert(msg)
}
</script>
<link href="kidmap-default.css" rel="stylesheet" type="text/css" />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
</head>

<body onload="MM_preloadImages('images/About_down.jpg','images/Admin_down.jpg','images/Rapid_down.jpg','images/care_down.jpg','images/OVC_down.jpg');getDataSize()">

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
      <!--DWLayoutTable-->
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
            <td height="127" valign="top">
                <div style="float: left" id="my_menu2" class="sdmenu" >
              <div>
                <a href="#">&nbsp; </a>
                <a href="#">&nbsp; </a>
                <a href="#">&nbsp; </a>
                <a href="#">&nbsp; </a>
                <a href="#">&nbsp; </a>
                <a href="#">&nbsp; </a>
              </div>
            </div></td>
          </tr>


      </table></td>
    <td width="13">&nbsp;</td>
    <td width="660" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td height="19" class="homepagestyle" colspan="2" align="center"> </td>

    </tr>


  </table>
        <center>
          <html:form action="/databaseExportAction">
        <div id="dbBackupDiv" class="paramPage" style="width: 650px; height: 450px;">
            <table><tr><td height="20"> </td> </tr></table>

            <table class="paramPage" width="560" style=" color: white; font-size: 20px; ">
                <logic:iterate name="dbExportSummaryList" id="summary">
                    <tr class="title"  ><td colspan="3" >${summary[0]} </td><td  align="left"> ${summary[1]}</td></tr>
                </logic:iterate>
                <%--<tr><td colspan="4" class="title" style=" color: white; font-size: 20px">Number of enrollment records ready for export: ${datasize[0]}</td></tr>
            <tr><td class="orglabel" height="20" colspan="4" style=" color: white; font-size: 20px">&nbsp;Number of service records ready for export: ${datasize[2]}</td></tr>--%>
            <tr><td>&nbsp;</td><td> </td></tr>
            <tr><td colspan="4" height="30" > &nbsp;<label id="bcMsg" style="font-size: 20px; color: yellow;"> </label> </td></tr>

            <tr><td >&nbsp;</td><td > </td></tr>

            <html:hidden property="actionName" styleId="actionName"/>
            <tr><td colspan="2" align="center" style="border: 0px;">
                             <input type="button" id="backup" name="backup" value="Next >>" onclick="exportDatabase()" style="width: 120px; height: 25px" />
                             <input type="button" name="finisBbtn" id="finisBbtn" value="Finish" onclick="finishExport()" style="width: 120px; height: 25px" disabled="true"/>                            
                     </td></tr>

        </table>

    </div>
    </html:form>
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
        <td width="945" height="25" class="copyright"><jsp:include page="../includes/Version.jsp"/> </td>
        </tr>
    </table></td>
  </tr>
</table>
  </body>
</html>

