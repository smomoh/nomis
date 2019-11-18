<%-- 
    Document   : DatabaseUpload
    Created on : Aug 20, 2015, 4:01:29 PM
    Author     : smomoh
--%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>XML data import</title>
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
}
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
function selectChkBoxes(chkname)
{
   var elements=document.getElementsByName(chkname)
    for(var i=0; i < elements.length; i++)
    {
        elements[i].checked=true
    }
}
function unselectChkBoxes(chkname)
{
   var elements=document.getElementsByName(chkname)
    for(var i=0; i <elements.length; i++)
    {
        elements[i].checked=false
    }
}
function checkDataImportStatus()
{
   req="checkImportStatus;"+"checkImportStatus"
   //alert("req is "+req)
   getValuesFromDb('addcbo.do',req,'checkImportStatus')
}
function submitForm(requiredAction,formId)
{
       setActionName(requiredAction)
       document.getElementById(formId).submit()
       return true
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
function showImportOptions()
{
    //alert("Inside showImportOptions()")
    document.getElementById("optionsDiv").style.visibility="visible";
    document.getElementById("showAnchor").style.visibility="hidden";
}
function hideImportOptions()
{
    document.getElementById("optionsDiv").style.visibility="hidden";
    document.getElementById("showAnchor").style.visibility="visible";
}
function setActionName(val)
{
    document.getElementById("actionName").value=val
}
</script>
<link href="kidmap-default.css" rel="stylesheet" type="text/css" />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
</head>

<body onload="MM_preloadImages('images/About_down.jpg','images/Admin_down.jpg','images/Rapid_down.jpg','images/care_down.jpg','images/OVC_down.jpg');InitializeTimer()">

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
        <td> </td>
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
                        <jsp:include page="../Navigation/ImportExportReportLink.jsp"/>
                    </div>
            </div></td>
          </tr>


      </table></td>
    <td width="13"></td>
    <td width="660" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
        <tr>
            <td height="19" class="homepagestyle" colspan="2" align="center"> </td>

    </tr>
    

  </table>

         <html:form enctype="multipart/form-data" action="/databaseUploadAction" method="post">
            <html:hidden property="actionName" value="upload"/>
            
            <center>
                
                <div style="width: auto; height: 480px;" class="paramPage">
                    <br/><br/><br/><br/>
            <table>
                <tr><td align="center" colspan="3" style="color: white;">XML data import </td></tr>
                <tr><td align="center" colspan="3"><html:errors/> </td></tr>
                <tr><td style="color: white;" align="right">Partner </td><td colspan="2">
                        <html:select property="partnerCode">
                            <html:option value="select">select...</html:option>
                            <logic:present name="userAssignedPartners">
                                <logic:iterate name="userAssignedPartners" id="partner">
                                    <html:option value="${partner.partnerCode}">${partner.partnerName}</html:option>
                                </logic:iterate>
                            </logic:present>
                        </html:select></td>
                </tr>
                <tr><td colspan="3"><label id="msgId" style="font-size:medium; font-weight: bold; color: white"> </label></td></tr>
                <tr><td colspan="3"><label id="importStatusMsg" style="color:white"> </label></td></tr>
                
                <tr><td style="color: white;" align="right">Choose file to upload </td><td><html:file property="uploadedFile" /></td><td> </td></tr>
                <tr><td colspan="3" align="center"><html:submit value="Upload" styleId="uploadBtn" style="width:75px; height:20px; " disabled="false"/></td></tr>
                                          
                
                <%--<tr><td>Time spent: <label id="timeSpentMsg" style="color:white"> </label> mins</td></tr>--%>
               
                <tr>
                    <td colspan="3"><a href="#" onclick="showImportOptions()" id="showAnchor" style="color:white; margin-left: 50px;">Show options</a>
                        <div id="optionsDiv" style="visibility:hidden"><a href="#" onclick="hideImportOptions()" id="hideAnchor" style="color:white">Hide options</a>
                        <table width="100%">
                            <tr><td> </td><td><label style="color:yellow; font-size: 14px; font-weight: bold">If you want to import just specific data, select from the list below</label> </td></tr>
                            <tr>
                                            <td class="orglabel" align="left" style="color: white;">Tables </td>
                                <td >
                                    <div style="width: 500px; height: 150px;overflow: scroll; background-color: white;">
                                        <table width="500" border="1" bordercolor="#D7E5F2" class="regsitertable" >
                                            <logic:present name="tableList">
                                              <logic:iterate  name="tableList" id="table">
                                                  <tr>
                                                      <td width="20"><html:multibox property="tableNames" styleId="${table.code}" value="${table.code}" styleClass='smallfieldcellselect'/> </td>
                                                      <td align="left" style=" font-size: 13px;">${table.name} </td>
                                                      </tr>
                                              </logic:iterate>
                                            </logic:present>
                                        </table>
                                     </div>
                                </td>
                            </tr>  
                            <tr><td > </td><td colspan="2">
            <input type="button" value="Select all " style="width:75px; height:30px; " onclick="selectChkBoxes('tableNames')" />
                    <input type="button" value="Unselect all " style="width:75px; height:30px; " onclick="unselectChkBoxes('tableNames')" />
                    </td></tr>
                    <tr><td colspan="3"><html:checkbox property="syncRecord" value="on" /> <label style=" color: white;">Remove records deleted on source computer</label></td></tr>
                    <tr><td colspan="3"><html:checkbox property="loadFromImport" value="1" /> <label style=" color: white;">Load files from import folder</label></td></tr>
                    </div>
                        </table>
                        </div>
                    </td>
                </tr>
                
                
                
                <%--<tr><td colspan="3"><html:checkbox property="importHivOnly" value="on" /><label style=" color: white;">Import only HIV records</label></td></tr>--%>
                
               
            </table>
                </div>
            </center>
        </html:form>
			<!--</div>
			</div>
			</div>-->

         </td>
      </tr>

    </table>    </td>
    <td width="18"> </td>
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