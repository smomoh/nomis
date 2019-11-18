<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@ page language="java" %>
<%@page import="com.fhi.nomis.nomisutils.*;" %>

<%
    LoadUpInfo loadup=new LoadUpInfo();
    loadup.getAllStates(session);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Welcome to KidMAP Orphan &amp; Vulnerable Children Database</title>
<style type="text/css">  
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #0075B5;
	background-image: url(images/back.jpg);
	background-repeat: repeat-x;
}
-->
</style>
<link href="images/kidmap.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
a {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #333333;
	font-weight: bold;
}
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
	color: #000000;
}
a:hover {
	text-decoration: underline;
	color: #0075B6;
}
a:active {
	text-decoration: none;
	color: #000000;
}
.title
{
  color: #FFFFFF;
  font-weight: bold;
}
.orglabel
{
    color: #FFFFFF;
}

-->
</style>
<script   type="text/JavaScript" src="kidmap.js"> </script>
<script language="javascript">

var param=""
var callerId=""
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
function validateDatePeriod(mth1Id,yr1Id,mth2Id,yr2Id)
{
    
    var mth1=getDropDownValue(mth1Id)
    var yr1=getDropDownValue(yr1Id)
    var mth2=getDropDownValue(mth2Id)
    var yr2=getDropDownValue(yr2Id)
    mth1=parseInt(mth1)
    yr1=parseInt(yr1)
    mth2=parseInt(mth2)
    yr2=parseInt(yr2)
    /*if(yr1>yr2)
    {
        alert("Wrong date period: first year greater than second year")
        return false
    }
    else if(yr1==yr2 && mth1>mth2)
    {
        alert("Wrong date period: first month greater than second month for the same year")
        return false
    }*/
    return true
}
function stateChanged()
{
    	if (xmlhttp.readyState==4)
	{
            var lgas=xmlhttp.responseText;
            if(lgas==" " || lgas=="" || lgas==";")
                return false;
            var values=lgas.split(";")
            if(callerId=="cboForReports")
            {
                populateSelectBox(values, "cbo")
            }
            else if(callerId=="csi_cbo")
            {
                populateSelectBox(values, "csi_cbo")
            }
            else if(callerId=="lgaForReports")
            {
                setSelectBoxValues(values, "lga")
            }
            else if(callerId=="csi_lga")
            {
                setSelectBoxValues(values, "csi_lga")
            }
            else if(callerId=="sumstat_lga")
            {
                setSelectBoxValues(values, "sumstat_lga")
            }
            else if(callerId=="sumstatPerMth_lga")
            {
                setSelectBoxValues(values, "sumstatPerMth_lga")
            }
            else if(callerId=="sumstat_cbo")
            {
                setSelectBoxValues(values, "sumstat_cbo")
            }
            else if(callerId=="sumstatPerMth_cbo")
            {
                setSelectBoxValues(values, "sumstatPerMth_cbo")
            }
            else if(callerId=="wardForReports")
            {
                setSelectBoxValues(values, "ward")
            }
            else if(callerId=="schools")
            {
                setSelectBoxValues(values, "schools")
            }
	}
}
function trim(stringToTrim)
{
        return stringToTrim.replace(/^\s+|\s+$/g,"");
}
function populateSelectBox(value, id)
{
    document.getElementById(id).options.length=0;
    document.getElementById(id).options[0]=new Option("All","All")
    for(var i=0; i<value.length; i++)
    {
        var selectBoxValue=trim(value[i])
        document.getElementById(id).options[i+1]=new Option(selectBoxValue,value[i])
    }
    cleanUp(id)
}
function setSelectBoxValues(value, id)
{
    document.getElementById(id).options.length=0;
    document.getElementById(id).options[0]=new Option("All","All")
    for(var i=0; i<value.length; i++)
    {
        var selectBoxValue=trim(value[i])
        document.getElementById(id).options[i+1]=new Option(selectBoxValue,value[i])
    }
    cleanUp(id)
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
function resetSelectBoxes(id)
{
    document.getElementById(id).options.length=0;
    document.getElementById(id).options[0]=new Option("All","All");
}
function showComponent(id,id2)
{
    document.getElementById("component1").innerHTML=document.getElementById(id).innerHTML
}
function hideComponent(id)
{
    document.getElementById(id).style.visibility="hidden"
}
function getDropDownValue(id)
{
    var dropDownValue=document.getElementById(id).value
    return dropDownValue;
}
function openOvcList()
{
    if(!validateDatePeriod('enroll_month','enroll_year','enroll_month2','enroll_year2'))
        return false
    var state=getDropDownValue("state")
    var lga=getDropDownValue("lga")
    var cbo=getDropDownValue("cbo")
    var ward=getDropDownValue("ward")
    var startMth=getDropDownValue("enroll_month")
    var startYr=getDropDownValue("enroll_year")
    var endMth=getDropDownValue("enroll_month2")
    var endYr=getDropDownValue("enroll_year2")
    var startAge=getDropDownValue("enroll_startAge")
    var endAge=getDropDownValue("enroll_endAge")
    var req="EnrollmentReports"+";"+state+":"+lga+":"+cbo+":"+ward+":"+startMth+"_"+startYr+"_"+endMth+"_"+endYr+":"+startAge+"-"+endAge
    getValuesFromDb('addcbo.do',req,'Reports')
    var w=window.open("Reports/EnrollmentReport.jsp","enrollmentList","width=1300,height=900,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,statusbar=yes,location=yes,left=0,top=0")
}
function openCsiAnalysis()
{
    if(!validateDatePeriod('csi_month','csi_year','csi_month2','csi_year2'))
        return false
    var state=getDropDownValue("csi_state")
    var lga=getDropDownValue("csi_lga")
    var cbo=getDropDownValue("csi_cbo")
    var startMth=getDropDownValue("csi_month")
    var startYr=getDropDownValue("csi_year")
    var endMth=getDropDownValue("csi_month2")
    var endYr=getDropDownValue("csi_year2")
    var startAge=getDropDownValue("csi_startAge")
    var endAge=getDropDownValue("csi_endAge")
    var req="csiReports"+";"+state+":"+lga+":"+cbo+":"+startMth+"_"+startYr+"_"+endMth+"_"+endYr+":"+startAge+"-"+endAge
    getValuesFromDb('addcbo.do',req,'Reports')
    var w=window.open("Reports/CsiAnalysis.jsp","csiAnalysis","width=1300,height=900,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,statusbar=yes,location=yes,left=0,top=0")
}
function openSchoolAttendance()
{
    var state=getDropDownValue("schAttend_state")
    var req="Reports"+";"+state+":"+"schoolAttendanceList"
    getValuesFromDb('addcbo.do',req,'schoolAttendanceList')
    var w=window.open("Reports/SchoolAttendanceCount.jsp","SchoolAttendance","width=1300,height=900,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,statusbar=yes,location=yes,left=0,top=0")
}
function doDqa()
{
    var state=getDropDownValue("dqa_state")
    var req="Reports"+";"+state+":"+"dqaReport"
    getValuesFromDb('addcbo.do',req,'dqaReport')
    var w=window.open("Reports/ovcDqa.jsp","dqa","width=1300,height=900,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,statusbar=yes,location=yes,left=0,top=0")
}
function openOvcSummaryStatistics()
{
    var state=getDropDownValue("sumstat_state")
    var lga=getDropDownValue("sumstat_lga")
    var cbo=getDropDownValue("sumstat_cbo")
    var req="Reports"+";"+state+":"+lga+":"+cbo+":"+"summaryStatistics"
    getValuesFromDb('addcbo.do',req,'summaryStatistics')
    var w=window.open("Reports/OvcSummaryStatistics.jsp","summaryStatistics","width=1300,height=900,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,statusbar=yes,location=yes,left=0,top=0")
    
}
function openOvcSummaryStatisticsPerMth()
{
    var state=getDropDownValue("sumstatPerMth_state")
    var lga=getDropDownValue("sumstatPerMth_lga")
    var cbo=getDropDownValue("sumstatPerMth_cbo")
    var month=getDropDownValue("sumstat_month")
    var year=getDropDownValue("sumstat_year")
    //var csi_period=getDropDownValue("period")
    var csi_period=6

    var req="Reports"+";"+state+":"+lga+":"+cbo+":"+month+":"+year+":"+csi_period+":"+"summaryStatistics"
    getValuesFromDb('addcbo.do',req,'summaryStatistics')
    if(document.getElementById("chkReportType").checked==true)
    {
      var w=window.open("Reports/SummaryStatCountPerMth.jsp","summaryStatisticsPerMth","width=1300,height=900,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,statusbar=yes,location=yes,left=0,top=0")
    }
    else
    {     
        var w=window.open("Reports/OvcSummaryStatistics.jsp","summaryStatistics","width=1300,height=900,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,statusbar=yes,location=yes,left=0,top=0")
    }
}
function enableDateFields()
{
    if(document.getElementById("chkReportType").checked==true)
    {
        enableComponents("sumstat_month")
        enableComponents("sumstat_year")
    }
    else
    {
        disableComponents("sumstat_month")
        //disableComponents("sumstat_month2")
        disableComponents("sumstat_year")
        //disableComponents("sumstat_year2")
    }

}
function enableComponents(id)
{
    document.getElementById(id).disabled=false
}
function disableComponents(id)
{
    document.getElementById(id).disabled=true
}
function getSchoolList(state,type)
{
    var req="Reports"+";"+state+":"+type
    getValuesFromDb('addcbo.do',req,type)
}
function getLga(state,type)
{
    if(state=="All")
    {
        if(type=='lgaForReports')
        {
            resetSelectBoxes("lga")
            resetSelectBoxes("cbo")
            resetSelectBoxes("ward")
        }
        else if(type=='csi_lga')
        {
            resetSelectBoxes("csi_lga")
            resetSelectBoxes("csi_cbo")
        }
        else if(type=='sumstat_lga')
        {
            resetSelectBoxes("sumstat_lga")
            resetSelectBoxes("sumstat_cbo")
        }
        else if(type=='sumstatPerMth_lga')
        {
            resetSelectBoxes("sumstatPerMth_lga")
            resetSelectBoxes("sumstatPerMth_cbo")
        }
        return
    }
    var req="Reports"+";"+state+":"+type
    getValuesFromDb('addcbo.do',req,type)
    resetSelectBoxes("cbo")
    resetSelectBoxes("ward")
}
function getCbo(lgaValue,type)
{
    var state=""
    var lga=""
    if(type=='cboForReports')
    {
        if(lgaValue=="All")
        {
            resetSelectBoxes("cbo")
            resetSelectBoxes("ward")
            return
        }
        state=getDropDownValue("state")
        lga=getDropDownValue("lga")
    }
    else if(type=='csi_cbo')
    {
        if(lgaValue=="All")
        {
            resetSelectBoxes("csi_cbo")
            return
        }
        state=getDropDownValue("csi_state")
        lga=getDropDownValue("csi_lga")
    }
    else if(type=='sumstat_cbo')
    {
        if(lgaValue=="All")
        {
            resetSelectBoxes("sumstat_cbo")
            return
        }
        state=getDropDownValue("sumstat_state")
        lga=getDropDownValue("sumstat_lga")
    }
    else if(type=='sumstatPerMth_cbo')
    {
        if(lgaValue=="All")
        {
            resetSelectBoxes("sumstatPerMth_cbo")
            return
        }
        state=getDropDownValue("sumstatPerMth_state")
        lga=getDropDownValue("sumstatPerMth_lga")
    }
    var req="Reports"+";"+state+":"+lga+":"+type
    getValuesFromDb('addcbo.do',req,type)
    resetSelectBoxes("ward")
}
function getWard(cboValue,type)
{
    var state=""
    if(type=='wardForReports')
    {
        if(cboValue=="All")
        {
            resetSelectBoxes("ward")
            return
        }
        state=getDropDownValue("state")
    }
    else if(type=='csi_ward')
    {
        if(cboValue=="All")
        {
            //resetSelectBoxes("ward_cbo")
            return
        }
        state=getDropDownValue("ward_state")
    }
    var req="Reports"+";"+state+":"+cboValue+":"+type
    getValuesFromDb('addcbo.do',req,type)
}
</script>
<link href="kidmap-default.css" rel="stylesheet" type="text/css" />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.style1 {
	font-size: 10px;
	color: #000000;
}
-->
</style>
</head>

<body onload="MM_preloadImages('images/dataentry_down.jpg','images/reports_down.jpg','images/admin_down.jpg','images/help_down.jpg')">
<table width="985" border="0" align="center" cellpadding="0" cellspacing="0">
  <!--DWLayoutTable-->
  <tr>
    <td height="125" colspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <!--DWLayoutTable-->
      <tr>
        <td width="204" height="29"></td>
        <td width="17"></td>
        <td width="122"></td>
        <td width="12"></td>
        <td width="122"></td>
        <td width="13"></td>
        <td width="122"></td>
        <td width="15"></td>
        <td width="122"></td>
        <td width="122"></td>
        <td width="114" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" background="images/logout.jpg">
          <!--DWLayoutTable-->
          <tr>
            <td width="114" height="29"><div align="center"><a href="login.jsp">Logout</a></div></td>
              </tr>
        </table></td>
        </tr>
      <tr>
        <td height="5"></td>
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
      </tr>

      <tr>
        <td rowspan="3" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <!--DWLayoutTable-->
          <tr>
            <td width="204" height="91"><img src="images/kidmap_logo.jpg" width="204" height="91" /></td>
          </tr>
            </table></td>
    <td height="15"></td>
      <td></td>
      <td></td>
      <td></td>
      <td></td>
      <td></td>
      <td></td>
      <td></td>
      <td></td>
      <td></td>
      </tr>
      <tr>
        <td height="37"></td>
        <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <!--DWLayoutTable-->
          <tr>
            <td width="122" height="37"><a href="dataentry.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Data Entry','','images/dataentry_down.jpg',1)"><img src="images/dataentry_up.jpg" alt="Data Entry" name="Data Entry" width="122" height="37" border="0" id="Data Entry" /></a></td>
              </tr>
        </table></td>
        <td>&nbsp;</td>
        <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <!--DWLayoutTable-->
          <tr>
            <td width="122" height="37"><a href="MainReportPage.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Reports','','images/reports_down.jpg',1)"><img src="images/reports_down.jpg" alt="Reports" name="Reports" width="122" height="37" border="0" id="Reports" /></a></td>
            </tr>
        </table></td>
        <td>&nbsp;</td>
        <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <!--DWLayoutTable-->
          <tr>
            <td width="122" height="37"><a href="AdminPage.html" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Admin','','images/admin_down.jpg',1)"><img src="images/admin_up.jpg" alt="Admin" name="Admin" width="122" height="37" border="0" id="Admin" /></a></td>
              </tr>
        </table></td>
        <td>&nbsp;</td>
        <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <!--DWLayoutTable-->
          <tr>
            <td width="122" height="37"><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Help','','images/help_down.jpg',1)"><img src="images/help_up.jpg" alt="Help" name="Help" width="122" height="37" border="0" id="Help" /></a></td>
            </tr>
        </table></td>
        <td>&nbsp;</td>
        <td></td>
      </tr>
      
      <tr>
        <td height="39" colspan="10" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
          <!--DWLayoutTable-->
          <tr>
              <td width="740" height="39" class="homepagestyle"><label>REPORTS</label> </td>
            <td width="41" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="41" height="39" valign="top"><img src="images/page_top_right.jpg" width="41" height="32" /></td>
                    </tr>
              
            </table></td>
          </tr>
          
          
            </table></td>
  </tr>
    </table>    </td>
  </tr>
  <tr>
    <td width="204" height="17"></td>
    <td width="781" rowspan="3" valign="top"><form id="form1" name="form1" method="post" action="">
      <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
        <!--DWLayoutTable-->
        <tr>
          <td width="9" height="1258">&nbsp;</td>
            <td colspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!--DWLayoutTable-->
                <tr>
                  <td width="762" height="28" valign="top">
                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr height="20"><td> </td></tr>
                     
                  </table></td>
                </tr>
                <tr>
                  <td height="10" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="12" valign="top">
                        <table width="100%" class="regsitertable">
                        <!--DWLayoutTable-->
                        
                        
                        
                        <tr><td></td></tr>
              
                        </table>
                      </td>
                      </tr>
                    
                  </table></td>
                </tr>
                <tr>
                  <td height="23" valign="top">
     <!--class="regsitertable"-->   
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" >
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="20">
                      <table width="686"  >
                        
                       <tr><td> </td></tr>
                        
                        
                        </table></td>
                      </tr>
                  </table>
                  </td>
                </tr>
                <tr>
                  <td height="13" valign="top">
                        
                  </td>
                </tr>
                <tr>
                  <td height="167" valign="top">
                        
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="48"><table width="100%" class="regsitertable">
                        
                              <tr><td> </td></tr>
                                  
                        </table></td>
                      </tr><!--#0075B5-->
                    <tr><td><span id="component1" style="background-color:#0075B5">  </span></td></tr>
                  </table>
                  </td>
                </tr>
                <tr>
                  <td height="553" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    
                                     
                    <tr>
                      <td height="27" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!--DWLayoutTable-->
                        <tr>
                          <td width="762" height="27">&nbsp; </td>
                        </tr>
                      </table>
                      </td>
                    </tr>
                    <tr>
                      <td height="65">&nbsp;</td>
                    </tr>
                    
                  </table></td>
                </tr>
                
                

            </table></td>
            <td width="10">&nbsp;</td>
        </tr>
        
        <tr>
          <td height="30">&nbsp;</td>
          <td width="741">&nbsp;</td>
          <td colspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
            <!--DWLayoutTable-->
            <tr>
              <td width="31" height="30">&nbsp;</td>
                </tr>
          </table></td>
          </tr>
        <tr>
          <td height="1"></td>
          <td></td>
          <td width="21"></td>
          <td></td>
        </tr>
        </table>
    </form>      <script language="JavaScript" type="text/javascript" src="kidmap.js"></script></td>
  </tr>
  <tr>
    <td height="192" valign="top"><table width="203" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
      <!--DWLayoutTable-->
      <tr>
        <td width="19" height="19" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <!--DWLayoutTable-->
          <tr>
            <td width="19" height="19"><img src="images/links_top_right.jpg" width="19" height="19" /></td>
              </tr>
          </table></td>
          <td width="185">&nbsp;</td>
        </tr>
      <tr>
        <td height="25" colspan="2" valign="top">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#000000">
          <!--DWLayoutTable-->
          <tr>
            <td width="204" height="20" class="quicklinks"> Reports </td>
            </tr>
           <tr height="25" style="background-color: white"><td><a href="Reports/CsiParamPage.jsp" style="margin-left:20px;" target="_blank" >CSI analysis</a> </td></tr>
           <tr height="25" style="background-color: white"><td><a href="Reports/EnrollmentRecord.jsp" target="_blank" style="margin-left:20px;" >Enrollment records</a> </td></tr>
           <tr height="25" style="background-color: white"><td><a href="Reports/SchoolAttendanceParamPage.jsp" target="_blank" style="margin-left:20px;" >School attendance</a> </td></tr>
           <tr height="25" style="background-color: white"><td><a href="Reports/SummStatParamPage.jsp" target="_blank" style="margin-left:20px;" >Summary statistics</a> </td></tr>
           <tr height="25" style="background-color: white"><td><a href="Reports/DqaParamPage.jsp" target="_blank" style="margin-left:20px;" >Data quality assessment</a> </td></tr>
           <!--<tr height="25" style="background-color: white"><td><a href="javascript:showComponent('sumstatDivPerMth')" style="margin-left:20px;" >Summary statistics</a> </td></tr>-->
           <!--<tr height="25" style="background-color: white"><td><a href="javascript:showComponent('csiAnalysisDiv')" style="margin-left:20px;" >CSI analysis</a> </td></tr>-->
                    
                    <!--<tr height="25" style="background-color: white"><td><a href="javascript:showComponent('enrlRecQueryDiv')" style="margin-left:20px;" >Enrollment records</a> </td></tr>-->
                    <!--<tr height="25" style="background-color: white"><td><a href="javascript:showComponent('schoolAttendanceDiv')" style="margin-left:20px;" >School attendance</a> </td></tr>-->
                    
                   <!--<tr height="25" style="background-color: white"><td><a href="javascript:showComponent('dqaDiv')" style="margin-left:20px;" >Data quality assessment</a> </td></tr>-->

                  
        </table></td>
        </tr>
      <tr>
        <td height="80">&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      
      
      <tr>
        <td height="18" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <!--DWLayoutTable-->
          <tr>
            <td width="19" height="17"><img src="images/links_bottom_left.jpg" width="19" height="18" /></td>
              </tr>
          </table></td>
          <td></td>
        </tr>

    </table></td>
  </tr>
  <tr>
    <td height="271">&nbsp;</td>
  </tr>

</table>

    <div id="mthlySummDate" style="height: 120px; width: 300px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
        <span>
        <center>
        <table>
            <tr><td>&nbsp; </td><td> </td></tr>
            <tr><td>Month: </td><td><select name="month" style="width: 90px;" ><option value="January">January </option><option value="February">February </option><option value="March">March </option><option value="April">April</option><option value="May">May</option><option value="June">June</option><option value="July">July</option><option value="August">August</option><option value="September">September</option><option value="October">October</option><option value="November">November</option><option value="December">December</option></select> </td><td>Year </td><td><select name="years" style="width: 90px;" ><option value="2009">2009 </option><option value="2010">2010 </option><option value="2011">2011 </option><option value="2012">2012 </option><option value="2013">2013 </option><option value="2014">2014</option><option value="2015">2015</option><option value="2016">2016</option><option value="2017">2017</option><option value="2018">2018</option><option value="2019">2019</option><option value="2010">2020</option></select></td></tr>
            <tr><td>&nbsp; </td><td> </td></tr>
           <tr><td colspan="4" align="center"> <input type="button" name="dateBtn" value="Submit" onclick="openMthlySummForm()" style="width: 70px;" />&nbsp; <input type="button" name="cancelBtn" value="Cancel" onclick="hideComponent('mthlySummDate')" style="width: 70px;" /></td></tr>
        </table>
            
        </center>
        </span>
    </div>
    
    <div id="enrlRecQueryDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
        <span>
        <center>
        <table style="background-color: #0075B5;">
            <tr><td>  </td><td> </td><td>&nbsp; </td><td> </td></tr>
            <tr><td class="orglabel" align="left">State </td><td colspan="3"><select name="state" id="state" style="width: 290px;" onchange="getLga(this.value,'lgaForReports')"><option value="All">All</option>
                        <logic:iterate id="stateObj" name="states">
                        <option value="${stateObj}">${stateObj}</option>
                        </logic:iterate>
                    </select> </td></tr>
            <tr><td class="orglabel" align="left">LGA </td><td colspan="3"><select name="lga" id="lga" style="width: 290px;" onchange="getCbo(this.value,'cboForReports')">
                        <option value="All">All</option>
                        </select> </td></tr>
            <tr><td class="orglabel" align="left">CBO </td><td colspan="3"><select name="cbo" id="cbo" style="width: 290px;" onchange="getWard(this.value,'wardForReports')" ><option value="All"> </option></select> </td></tr>
            <tr><td class="orglabel" align="left">Ward </td><td colspan="3"><select name="ward" id="ward" style="width: 290px;" ><option value="All"> </option></select> </td></tr>
            <tr><td class="title"><label style=" margin-right: 5px; color:white">From</label></td><td>
                    <select name="enroll_month" id="enroll_month" style="width: 70px;">
                        <option value="01">January </option>
                        <option value="02">February </option>
                        <option value="03">March </option>
                        <option value="04">April </option>
                        <option value="05">May</option>
                        <option value="06">June</option>
                        <option value="07">July </option>
                        <option value="08">August </option>
                        <option value="09">September </option>
                        <option value="10">October </option>
                        <option value="11">November </option>
                        <option value="12">December</option>
                    </select>
                    <select name="enroll_year" id="enroll_year" style="width: 55px;">
                            <option value="2008">2008 </option>
                            <option value="2009">2009 </option>
                            <option value="2010" selected="selected">2010 </option>
                            <option value="2011">2011 </option>
                            <option value="2012">2012 </option>
                            <option value="2013">2013 </option>
                            <option value="2014">2014 </option>
                            <option value="2015">2015 </option>
                            <option value="2016">2016 </option>
                            <option value="2017">2017 </option>
                            <option value="2018">2018 </option>
                            <option value="2019">2019 </option>
                            <option value="2020">2020 </option>
                        </select> </td>
                <td><label style=" margin-right: 5px; color:white">To </label> <select name="enroll_month2" id="enroll_month2" style="width: 70px;">
                        <option value="01">January </option>
                        <option value="02">February </option>
                        <option value="03">March </option>
                        <option value="04">April </option>
                        <option value="05">May</option>
                        <option value="06">June</option>
                        <option value="07">July </option>
                        <option value="08">August </option>
                        <option value="09">September </option>
                        <option value="10">October </option>
                        <option value="11">November </option>
                        <option value="12">December</option>
                    </select>
                    <select name="enroll_year2" id="enroll_year2" style="width: 65px;">
                            <option value="2008">2008 </option>
                            <option value="2009">2009 </option>
                            <option value="2010" selected="selected">2010 </option>
                            <option value="2011">2011 </option>
                            <option value="2012">2012 </option>
                            <option value="2013">2013 </option>
                            <option value="2014">2014 </option>
                            <option value="2015">2015 </option>
                            <option value="2016">2016 </option>
                            <option value="2017">2017 </option>
                            <option value="2018">2018 </option>
                            <option value="2019">2019 </option>
                            <option value="2020">2020 </option>
                        </select></td></tr>
            <tr><td class="title"><label style=" margin-right: 5px; color:white">Age</label></td>
                <td colspan="2" align="left">
                    <label style="color:white; width: 30px; margin-right: 21px;">between</label>
                    <select name="enroll_startAge" id="enroll_startAge" style="width: 55px;">
                            <option value="0">0 </option>
                            <option value="1">1 </option>
                            <option value="2">2 </option>
                            <option value="3">3 </option>
                            <option value="4">4 </option>
                            <option value="5">5 </option>
                            <option value="6">6 </option>
                            <option value="7">7 </option>
                            <option value="8">8 </option>
                            <option value="9">9</option>
                            <option value="10">10 </option>
                            <option value="11">11 </option>
                            <option value="12">12 </option>
                            <option value="13">13</option>
                            <option value="14">14 </option>
                            <option value="15">15 </option>
                            <option value="16">16</option>
                   </select>
                <label style=" color: white">and</label>  <select name="enroll_endAge" id="enroll_endAge" style="width: 55px;">
                        <option value="0">0 </option>
                            <option value="1">1 </option>
                            <option value="2">2 </option>
                            <option value="3">3 </option>
                            <option value="4">4 </option>
                            <option value="5">5 </option>
                            <option value="6">6 </option>
                            <option value="7">7 </option>
                            <option value="8">8 </option>
                            <option value="9">9</option>
                            <option value="10">10 </option>
                            <option value="11">11 </option>
                            <option value="12">12 </option>
                            <option value="13">13</option>
                            <option value="14">14 </option>
                            <option value="15">15 </option>
                            <option value="16">16</option>
                            <option value="17">17</option>
                    </select>
                    </td></tr>
            <tr><td colspan="4" align="center"><input type="button" name="enrldateQueryBtn" value="Submit" onclick="openOvcList()" style="width: 70px;" />&nbsp; <!--<input type="button" name="enrlQuerycancelBtn" value="Cancel" onclick="hideComponent('enrlRecQueryDiv')" style="width: 70px;" />--> </td></tr>
        </table>   
        </center>
        </span>
    </div>
    <div id="csiAnalysisDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
        <span>
        <center>
        <table style="background-color: #0075B5;">
            <tr><td colspan="4" class="title"> CSI analysis  </td></tr>
            <tr><td class="orglabel">State </td><td colspan="3"><select name="csi_state" id="csi_state" style="width: 290px;" onchange="getLga(this.value,'csi_lga')"><option value="All">All</option>
                        <logic:iterate id="stateObj" name="states">
                        <option value="${stateObj}">${stateObj}</option>
                        </logic:iterate>
                    </select> </td></tr>
            <tr><td class="orglabel">LGA </td><td colspan="3"><select name="csi_lga" id="csi_lga" style="width: 290px;" onchange="getCbo(this.value,'csi_cbo')">
                        <option value="All:All">All</option>
                        </select> </td></tr>
            <tr><td class="orglabel">CBO </td><td colspan="3"><select name="csi_cbo" id="csi_cbo" style="width: 290px;" ><option value="All"> </option></select> </td></tr>

            <tr><td class="title"><label style=" margin-right: 5px; color:white">From</label></td><td>
                    <select name="csi_month" id="csi_month" style="width: 70px;">
                        <option value="01">January </option>
                        <option value="02">February </option>
                        <option value="03">March </option>
                        <option value="04">April </option>
                        <option value="05">May</option>
                        <option value="06">June</option>
                        <option value="07">July </option>
                        <option value="08">August </option>
                        <option value="09">September </option>
                        <option value="10">October </option>
                        <option value="11">November </option>
                        <option value="12">December</option>
                    </select>
                    <select name="csi_year" id="csi_year" style="width: 55px;">
                            <option value="2008">2008 </option>
                            <option value="2009">2009 </option>
                            <option value="2010" selected="selected">2010 </option>
                            <option value="2011">2011 </option>
                            <option value="2012">2012 </option>
                            <option value="2013">2013 </option>
                            <option value="2014">2014 </option>
                            <option value="2015">2015 </option>
                            <option value="2016">2016 </option>
                            <option value="2017">2017 </option>
                            <option value="2018">2018 </option>
                            <option value="2019">2019 </option>
                            <option value="2020">2020 </option>
                        </select> </td>
                <td><label style=" color: white">To</label>  <select name="csi_month2" id="csi_month2" style="width: 70px;">
                        <option value="01">January </option>
                        <option value="02">February </option>
                        <option value="03">March </option>
                        <option value="04">April </option>
                        <option value="05">May</option>
                        <option value="06">June</option>
                        <option value="07">July </option>
                        <option value="08">August </option>
                        <option value="09">September </option>
                        <option value="10">October </option>
                        <option value="11">November </option>
                        <option value="12">December</option>
                    </select>
                    <select name="csi_year2" id="csi_year2" style="width: 65px;">
                            <option value="2008">2008 </option>
                            <option value="2009">2009 </option>
                            <option value="2010" selected="selected">2010 </option>
                            <option value="2011">2011 </option>
                            <option value="2012">2012 </option>
                            <option value="2013">2013 </option>
                            <option value="2014">2014 </option>
                            <option value="2015">2015 </option>
                            <option value="2016">2016 </option>
                            <option value="2017">2017 </option>
                            <option value="2018">2018 </option>
                            <option value="2019">2019 </option>
                            <option value="2020">2020 </option>
                        </select></td></tr>


            <tr><td class="title"><label style=" margin-right: 5px; color:white">Age</label></td>
                <td colspan="2" align="left">
                    <label style="color:white; width: 30px; margin-right: 24px;">between</label>
                    <select name="csi_startAge" id="csi_startAge" style="width: 55px;">
                            <option value="0">0 </option>
                            <option value="1">1 </option>
                            <option value="2">2 </option>
                            <option value="3">3 </option>
                            <option value="4">4 </option>
                            <option value="5">5 </option>
                            <option value="6">6 </option>
                            <option value="7">7 </option>
                            <option value="8">8 </option>
                            <option value="9">9</option>
                            <option value="10">10 </option>
                            <option value="11">11 </option>
                            <option value="12">12 </option>
                            <option value="13">13</option>
                            <option value="14">14 </option>
                            <option value="15">15 </option>
                            <option value="16">16</option>
                   </select> 
                <label style=" color: white">and</label>  <select name="csi_endAge" id="csi_endAge" style="width: 55px;">
                        <option value="0">0 </option>
                            <option value="1">1 </option>
                            <option value="2">2 </option>
                            <option value="3">3 </option>
                            <option value="4">4 </option>
                            <option value="5">5 </option>
                            <option value="6">6 </option>
                            <option value="7">7 </option>
                            <option value="8">8 </option>
                            <option value="9">9</option>
                            <option value="10">10 </option>
                            <option value="11">11 </option>
                            <option value="12">12 </option>
                            <option value="13">13</option>
                            <option value="14">14 </option>
                            <option value="15">15 </option>
                            <option value="16">16</option>
                            <option value="17">17</option>
                    </select>
                    </td></tr>
            
            <tr><td>&nbsp; </td><td> </td></tr>
            <tr><td colspan="4" align="center"><input type="button" name="csi_dateBtn" value="Submit" onclick="openCsiAnalysis()" style="width: 70px;" />&nbsp; <!--<input type="button" name="csi_cancelBtn" value="Cancel" onclick="hideComponent('enrlRecQueryDiv')" style="width: 70px;" />--></td></tr>
        </table>
        </center>
        </span>
    </div>

    <div id="schoolAttendanceDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
        <span style="height: 160px; width: 400px; background-color: #0075B5;">
        <center>
        <table style="background-color: #0075B5;">
            <tr><td colspan="4" class="title">School attendance  </td></tr>
            <tr><td>&nbsp; </td><td> </td></tr>
            <tr><td class="orglabel">State </td><td><select name="schAttend_state" id="schAttend_state" style="width: 290px;">
                        <logic:iterate id="stateObj" name="states">
                        <option value="${stateObj}">${stateObj}</option>
                        </logic:iterate>
                    </select> </td><td> </td><td> </td></tr>
                 <tr><td>&nbsp; </td><td> </td></tr>
            <tr><td colspan="2" align="center"><input type="button" name="sch_Btn" value="Submit" onclick="openSchoolAttendance()" style="width: 70px;" />&nbsp; <!--<input type="button" name="csi_cancelBtn" value="Cancel" onclick="hideComponent('enrlRecQueryDiv')" style="width: 70px;" />--> </td></tr>
        </table>     
        </center>
        </span>
    </div>

    <div id="dqaDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
        <span style="height: 160px; width: 400px; background-color: #0075B5;">
        <center>
        <table style="background-color: #0075B5;">
            <tr><td colspan="4" class="title">Data quality assessment</td></tr>
            <tr><td>&nbsp; </td><td> </td></tr>
            <tr><td class="orglabel">State </td><td><select name="dqa_state" id="dqa_state" style="width: 290px;">
                        <logic:iterate id="stateObj" name="states">
                        <option value="${stateObj}">${stateObj}</option>
                        </logic:iterate>
                    </select> </td><td> </td><td> </td></tr>
                 <tr><td>&nbsp; </td><td> </td></tr>
            <tr><td colspan="2" align="center"><input type="button" name="dqa_Btn" value="Submit" onclick="doDqa()" style="width: 70px;" />&nbsp;</td></tr>
        </table>
        </center>
        </span>
    </div>

    <div id="sumstatDivPerMth" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
        <span>
        <center>
        <table style="background-color: #0075B5;">
            <tr><td colspan="4" class="title">Summary statistics</td></tr>
            <tr><td class="orglabel" align="left">State </td><td colspan="3"><select name="sumstatPerMth_state" id="sumstatPerMth_state" style="width: 290px;" onchange="getLga(this.value,'sumstatPerMth_lga')"><option value="All">All</option>
                        <logic:iterate id="stateObj" name="states">
                        <option value="${stateObj}">${stateObj}</option>
                        </logic:iterate>
                    </select> </td></tr>
            <tr><td class="orglabel" align="left">LGA </td><td colspan="3"><select name="sumstatPerMth_lga" id="sumstatPerMth_lga" style="width: 290px;" onchange="getCbo(this.value,'sumstatPerMth_cbo')">
                        <option value="All:All">All</option>
                        </select> </td></tr>
            <tr><td class="orglabel" align="left">CBO </td><td colspan="3"><select name="sumstatPerMth_cbo" id="sumstatPerMth_cbo" style="width: 290px;" ><option value="All">All</option></select> </td></tr>

            <tr><td>&nbsp;</td><td colspan="3" align="left" class="title"><input type="checkbox" name="chkReportType" id="chkReportType" onclick="enableDateFields()" />Generate monthly statistics  </td></tr>
            <tr><td class="orglabel" align="left">From &nbsp;</td>
                <td>
                    <select name="sumstat_month" id="sumstat_month" style="width: 70px;" disabled="true" >
                        <option value="01">January </option>
                        <option value="02">February </option>
                        <option value="03">March </option>
                        <option value="04">April </option>
                        <option value="05">May</option>
                        <option value="06">June</option>
                        <option value="07">July </option>
                        <option value="08">August </option>
                        <option value="09">September </option>
                        <option value="10">October </option>
                        <option value="11">November </option>
                        <option value="12">December</option>
                    </select>
                    <select name="sumstat_year" id="sumstat_year" style="width: 75px;" disabled="true" >
                            <option value="2008">2008 </option>
                            <option value="2009">2009 </option>
                            <option value="2010" selected="selected">2010 </option>
                            <option value="2011">2011 </option>
                            <option value="2012">2012 </option>
                            <option value="2013">2013 </option>
                            <option value="2014">2014 </option>
                            <option value="2015">2015 </option>
                            <option value="2016">2016 </option>
                            <option value="2017">2017 </option>
                            <option value="2018">2018 </option>
                            <option value="2019">2019 </option>
                            <option value="2020">2020 </option>
                        </select>
                </td><td class="orglabel" width="65"><!--Period--></td><td width="70"><!--<select name="period" id="period" style=" width: 50px;"><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option></select><label style="color:white;"> months </label>--></td></tr>
                    
            <tr><td colspan="3" align="center"><input type="button" name="sumstatPerMth_dateBtn" value="Submit" onclick="openOvcSummaryStatisticsPerMth()" style="width: 70px; margin-left: 70px;" /></td></tr>
        </table>
        </center>
        </span>
    </div>
</body>
</html>
