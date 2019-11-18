<%-- 
    Document   : HhMultipleReportQueryParamPage
    Created on : Oct 26, 2017, 10:12:30 AM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%--<%@page import="com.fhi.kidmap.business.*" %>--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Household multiple report form </title>
<style type="text/css">
<!--
body 
{
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
function MM_swapImgRestore() 
{ //v3.0
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

<script language="javascript">
var msg=" "
			$(function() {
                $("#serviceDate").datepicker();
            });


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
</script>
<link href="kidmap-default.css" rel="stylesheet" type="text/css" />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
</head>

<body onload="MM_preloadImages('images/About_down.jpg','images/Admin_down.jpg','images/Rapid_down.jpg','images/care_down.jpg','images/OVC_down.jpg'); checkCurrentAge()">
    <br/><br/>
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
    <td width="931" height="351" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <!--DWLayoutTable-->
      <tr>
        <td> </td>
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
            <td height="85" valign="top"><div style="float: left" id="my_menu" class="sdmenu">
                    
            </div></td>
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
            <td height="157" valign="top"><div style="float: left" id="my_menu2" class="sdmenu" >
                    
            </div></td>
          </tr>


      </table></td>
    <td width="13">&nbsp;</td>
      <td width="639" >

     <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
	 <td width="30%" height="39" class="homepagestyle">Household Rapid Service form </td>
	 <td width="70%"><html:errors/></td>
    </tr>

  </table>

         <center>
            
            <html:form action="/householdmultiplequeryreport" method="post">
                <html:hidden property="actionName" styleId="actionName"/>
        <!--<div id="serviceReportDiv" class="paramPage" style="height: 210px; width: 360px; margin-top: 100px;">-->
        <span>
        <center>
        <table class="paramPage">
            <tr><td colspan="4"><html:errors/>  </td></tr> <!--onchange="getLga(this.value,'lgaForReports')"-->
            <tr><td class="orglabel" align="left">State </td><td colspan="3">
                    <html:select property="state" styleId="state" style="width: 290px;" onchange="setActionName('lga'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="stateListForReports">
                            <logic:iterate id="stateObj" name="stateListForReports">
                                <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                            </logic:iterate>
                        </logic:present>
                        <%--<html:optionsCollection property="stateList" label="name" value="state_code" />--%>
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">LGA </td><td colspan="3">
                    <html:select property="lga" styleId="lga" style="width: 290px;" onchange="setActionName('cbo'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <html:optionsCollection property="lgaList" label="lga_name" value="lga_code" />
                    </html:select>
                </td></tr>
            <tr><td class="orglabel" align="left">CBO </td><td colspan="3">
                    <html:select property="cbo" styleId="cbo" style="width: 290px;" onchange="setActionName('ward'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <html:optionsCollection property="cboList" label="orgName" value="orgCode" />
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left"><jsp:include page="../includes/WardName.jsp" /> </td><td colspan="3">
                    <html:select property="ward" styleId="ward" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                        <html:optionsCollection property="wardList" label="ward_name" value="ward_code" />
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">Partner </td><td colspan="3">
                    <html:select property="partnerCode" styleId="partnerCode" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                    <logic:iterate id="partner" name="partnerList">
                      <html:option value="${partner.partnerCode}">${partner.partnerName}</html:option>
                        </logic:iterate>
                    </html:select> </td>
            </tr>
            <tr><td>&nbsp;</td> 
                <td class="title" colspan="2">
                    <html:select property="criteria1">
                        <html:option value="All">All</html:option>
                        <html:option value="served">Served</html:option>
                        <html:option value="notserved">Not served</html:option>
                    </html:select>
                </td>
            </tr>
            <tr><td class="title">
                    &nbsp;
                </td>
                <td align="left">
                    <html:select property="serviceMonth" styleId="serviceMonth" style="width: 70px;">
                        <logic:iterate name="mrmonthList" id="month">
                            <html:option value="${month.value}">${month.name}</html:option>
                        </logic:iterate>
                        
                    </html:select>
                        <html:select property="serviceYear" styleId="serviceYear" style="width: 70px;">
                        <logic:iterate name="mryearList" id="year">
                            <html:option value="${year}">${year}</html:option>
                        </logic:iterate>
                    </html:select>
                        <html:select property="serviceMonth2" styleId="serviceMonth2" style="width: 70px;">
                        <logic:iterate name="mrmonthList" id="month">
                            <html:option value="${month.value}">${month.name}</html:option>
                        </logic:iterate>
                        
                    </html:select>
                        <html:select property="serviceYear2" styleId="serviceYear2" style="width: 70px;">
                          <logic:iterate name="mryearList" id="year">
                            <html:option value="${year}">${year}</html:option>
                          </logic:iterate>
                    </html:select>
                  </td>
                <td>&nbsp; </td></tr>
            <tr><td>&nbsp;</td> 
                <td class="title" colspan="2">
                    <html:select property="criteria2">
                        <html:option value="All">All</html:option>
                        <html:option value="served">Served</html:option>
                        <html:option value="notserved">Not served</html:option>
                    </html:select>
                </td>
            </tr>
            <tr><td class="title">
                    &nbsp;
                </td>
                <td align="left">
                    <html:select property="cr2serviceMonth" styleId="cr2serviceMonth" style="width: 70px;">
                        <logic:iterate name="mrmonthList" id="month">
                            <html:option value="${month.value}">${month.name}</html:option>
                        </logic:iterate>
                        
                    </html:select>
                        <html:select property="cr2serviceYear" styleId="cr2serviceYear" style="width: 70px;">
                        <logic:iterate name="mryearList" id="year">
                            <html:option value="${year}">${year}</html:option>
                        </logic:iterate>
                    </html:select>
                        <html:select property="cr2serviceMonth2" styleId="cr2serviceMonth2" style="width: 70px;">
                        <logic:iterate name="mrmonthList" id="month">
                            <html:option value="${month.value}">${month.name}</html:option>
                        </logic:iterate>
                        
                    </html:select>
                        <html:select property="cr2serviceYear2" styleId="cr2serviceYear2" style="width: 70px;">
                          <logic:iterate name="mryearList" id="year">
                            <html:option value="${year}">${year}</html:option>
                          </logic:iterate>
                    </html:select>
                  </td>
                <td>&nbsp; </td></tr>
            <tr><td>&nbsp;</td> 
                
                <td class="title" colspan="2">
                    <html:select property="criteria3">
                        <html:option value="All">All</html:option>
                        <html:option value="curenr">Currently enrolled</html:option>
                        <html:option value="everenr">Ever enrolled</html:option>
                    </html:select>
                    
                    <html:select property="criteria4">
                        <html:option value="All">All</html:option>
                        <html:option value="hivpos">HIV positive only</html:option>
                        <html:option value="hivneg">HIV negative only</html:option>
                        <html:option value="hivunk">HIV unknown only</html:option>
                    </html:select>
                </td>
            </tr>
            
            <tr><td class="title"><label style=" margin-right: 5px; color:white">Age</label></td>
                <td colspan="2" align="left">
                    <label style="color:white; width: 30px; margin-right: 21px;">between</label>
                    <html:select property="startAge" styleId="startAge" style="width: 55px;">
                            <html:option value="0">0 </html:option>
                            <html:option value="1">1 </html:option>
                            <html:option value="2">2 </html:option>
                            <html:option value="3">3 </html:option>
                            <html:option value="4">4 </html:option>
                            <html:option value="5">5 </html:option>
                            <html:option value="6">6 </html:option>
                            <html:option value="7">7 </html:option>
                            <html:option value="8">8 </html:option>
                            <html:option value="9">9</html:option>
                            <html:option value="10">10</html:option>
                            <html:option value="11">11 </html:option>
                            <html:option value="12">12 </html:option>
                            <html:option value="13">13</html:option>
                            <html:option value="14">14 </html:option>
                            <html:option value="15">15 </html:option>
                            <html:option value="16">16</html:option>
                            <html:option value="17">17</html:option>
                            <html:option value="18">18</html:option>
                            <html:option value="19">19</html:option>
                            <html:option value="20">20 </html:option>
                            <html:option value="21">21 </html:option>
                            <html:option value="22">22</html:option>
                            <html:option value="23">23</html:option>
                            <html:option value="24">24</html:option>
                   </html:select>
                <label style=" color: white">and</label>  <html:select property="endAge" styleId="endAge" style="width: 55px;">
                        <html:option value="0">0 </html:option>
                            <html:option value="1">1 </html:option>
                            <html:option value="2">2 </html:option>
                            <html:option value="3">3 </html:option>
                            <html:option value="4">4 </html:option>
                            <html:option value="5">5 </html:option>
                            <html:option value="6">6 </html:option>
                            <html:option value="7">7 </html:option>
                            <html:option value="8">8 </html:option>
                            <html:option value="9">9</html:option>
                            <html:option value="10">10 </html:option>
                            <html:option value="11">11 </html:option>
                            <html:option value="12">12 </html:option>
                            <html:option value="13">13</html:option>
                            <html:option value="14">14 </html:option>
                            <html:option value="15">15 </html:option>
                            <html:option value="16">16</html:option>
                            <html:option value="17">17</html:option>
                            <html:option value="18">18</html:option>
                            
                            <html:option value="19">19</html:option>
                            <html:option value="20">20 </html:option>
                            <html:option value="21">21 </html:option>
                            <html:option value="22">22</html:option>
                            <html:option value="23">23</html:option>
                            <html:option value="24">24</html:option>
                    </html:select>
                    </td></tr>
            <tr><td colspan="4" align="center">
                    <html:submit property="serviceReportBtn" value="Submit" onclick="setActionName('MthlyServiceList')" style="width: 70px;" />
                </td></tr>
        </table>
        </center>
        </span>
    <!--</div>-->
            </html:form>

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