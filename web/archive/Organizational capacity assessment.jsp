<%-- 
    Document   : Organizational capacity assessment
    Created on : Nov 22, 2011, 3:17:05 PM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<!--<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">-->
<%--<%@page import="com.fhi.kidmap.business.*" %>--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>

<%--<%
    LoadUpInfo loadup=new LoadUpInfo();
    loadup.setRequestParameters(request);
    loadup.getOrganizationList(session);
%>--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>NOMIS - National OVC Management Information System </title>
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
<script language="JavaScript" src="kidmap.js" type="text/JavaScript"></script>
<link type="text/css" href="css/ui-darkness/jquery-ui-1.8.custom.css" rel="Stylesheet" />
 <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
 <script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>

 <script language="javascript">

$(function() {
                $("#dateofcapacityassessment").datepicker();
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

<body onload="MM_preloadImages('images/About_down.jpg','images/Admin_down.jpg','images/Rapid_down.jpg','images/care_down.jpg','images/OVC_down.jpg')">
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




      <tr>
        <td height="3"></td>
        <td></td>
        <td></td>
        <td rowspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="95" height="65"><a href="Nomis.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('OVC','','images/OVC_down.jpg',1)"><img src="images/OVC_up.jpg" alt="OVC" name="OVC" width="95" height="65" border="0" id="OVC" /></a></td>
              </tr>
          </table></td>
          <td></td>
        <td rowspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="95" height="65"><a href="CareAndSupport.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Care and Support','','images/care_down.jpg',1)"><img src="images/care_up.jpg" alt="Care and Support" name="Care and Support" width="95" height="65" border="0" id="Care and Support" /></a></td>
              </tr>
          </table></td>
          <td></td>
        <td rowspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="95" height="65"><a href="RapidAssessment.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Rapid Assesment','','images/Rapid_down.jpg',1)"><img src="images/rapid_up.jpg" alt="Rapid Assesment" name="Rapid Assesment" width="95" height="65" border="0" id="Rapid Assesment" /></a></td>
              </tr>
          </table></td>
          <td></td>
        <td rowspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="95" height="65"><a href="Administration.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('NOMIS Admin','','images/Admin_down.jpg',1)"><img src="images/admin_up.jpg" alt="NOMIS Admin" name="NOMIS Admin" width="95" height="65" border="0" id="NOMIS Admin" /></a></td>
              </tr>
          </table></td>
          <td></td>
          <td rowspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="95" height="65"><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('About NOMIS','','images/About_down.jpg',1)"><img src="images/about_up.jpg" alt="About NOMIS" name="About NOMIS" width="95" height="65" border="0" id="About NOMIS" /></a></td>
              </tr>
          </table></td>
          <td></td>
        </tr>

      <tr>
        <td height="62"></td>
          <td rowspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="268" height="92"><img src="images/logo.jpg" width="268" height="92" /></td>
              </tr>
          </table></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
        </tr>





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
          <td><jsp:include page="Logout.jsp" /></td>
          <td></td>
        </tr>
      <tr>
        <td height="3" colspan="13" valign="top">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#038233">
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
        <td width="180" rowspan="2" valign="top"  bgcolor="#038233">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#038233">
          <!--DWLayoutTable-->
          <tr>
            <td width="180" height="28" valign="top">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="180" height="28"><img src="images/dataentry.jpg" width="231" height="28" /></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td height="85" valign="top"><div style="float: left" id="my_menu" class="sdmenu">
                    <!--onmouseover="setToolTipText('Click this link to get to the enrollment data entry page','30%','30%')" onmouseout="hideComponent('tooltip')"-->
                    <div><!--<a href="orgrecords.do">Organizational Setup </a>--><a href="orgcapacityassessment.do">Organizational capacity assessment </a> <!--<a href="CVDataEntry.jsp">CV</a>--><a href="#">&nbsp;</a></div>
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
              <div><a  > </a><a > </a><a > </a><a > </a><a> </a><a > </a><a > </a><a > </a></div>
            </div></td>
          </tr>


      </table></td>
    <td width="13">&nbsp;</td>
      <td width="639" >

          <!--<div align="center">
			<div style="width:75+7px; height:1200px; background-color:#FFFFFF">
			<div align="left">-->
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
     <td width="30%" height="20" class="homepagestyle"> </td>
     <td width="70%"> <html:errors/> </td>
    </tr>
     <!--<tr><td height="10" colspan="2" align="center"><label style=" font-size: 15px; color: green"> </label> </td></tr>-->
  </table><%--<${partnername}--%>
    <html:form styleId="dataForm"  method="post" action="/orgcapacityassessment">
        <html:hidden property="actionName" styleId="actionName" />
        <html:hidden property="recordId" styleId="recordId" />
        <html:hidden property="noofassessment" styleId="noofassessment" />
        <html:hidden property="dateoffirstcapassessment" styleId="dateoffirstcapassessment" />
            
            <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
        <!--DWLayoutTable-->
        <tr>
          <td width="8" height="900">&nbsp;</td>
            <td colspan="2" valign="top">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!--DWLayoutTable-->
                <tr>
                  <td width="752" height="28" valign="top">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="17">

                          <table width="100%">
                          <tr>
                              <td colspan="4" align="center"> </td>

                            </tr>

                              
                        </table>
                      </td>
                      </tr>
                  </table></td>
                </tr>


                <tr>
                  <td height="210" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="122" valign="top">


                          <fieldset>
                        <legend class="fieldset">Organizational information  </legend>
                        <br/>
                        <table width="100%" class="regsitertable">
                        <tr><td width="40%" valign="top" class="right">Name of organization</td>

                              <td>
                                  <html:select property="orgCode" styleId="orgCode" styleClass="fieldcellinput" onchange="setActionName('noOfAssessmentAndDateOfFirstAssessment'); forms[0].submit()">
                                      <html:option value=""> </html:option>
                                  <logic:iterate name="organizationList" id="organization">
                                        <html:option value="${organization.orgCode}">${organization.orgName}</html:option>
                                  </logic:iterate>
                              </html:select>
                                  </td>
                            </tr>


                            <tr><td width="30%" valign="top" class="right">Number of assessments to date</td>

                              <td>
                                  <input type="text" name="disabledNoofassessment" id="disabledNoofassessment" class="smallfieldcellinput" disabled="true" value="${noofassessment}"/>

                                  </td>
                            </tr>



                        <tr>
                          <td valign="top" class="right">Type of organization </td>
                          <td>
                              <html:text property="typeoforganization" styleId="typeoforganization" styleClass="fieldcellinput" disabled="true" />
                              <%--<html:option value=""> </html:option>
                              <html:option value="Government">Government</html:option>
                              <html:option value="CSO">CSO</html:option>
                              <html:option value="others">Others</html:option>
                              </html:select>--%>
                              
                          </td>
                            </tr>

                            <tr>
                          <td valign="top" class="right">Is the organization a sub-grantee? </td>
                          <td>
                                  No <html:radio property="isorganizationasubgrantee" value="No"  styleId="subgrantee" />
                                  Yes <html:radio property="isorganizationasubgrantee" value="Yes" styleId="subgrantee" />
                          </td>
                            </tr>
                        
                        <tr>
                          <td valign="top" class="right">Date of capacity assessment </td>
                              <td>
                                  <html:text property="dateofcapacityassessment" styleId="dateofcapacityassessment" styleClass="fieldcellinput" onchange="setActionName('assessmentDetails'); forms[0].submit()" />
                               </td>
                            </tr>


                            <tr>
                          <td valign="top" class="right">Has the organization been assessed before? </td>
                              <td>
                                  No <html:radio property="assessedbefore" value="No"  styleId="assessedbefore" disabled="true" />
                                  Yes <html:radio property="assessedbefore" value="Yes" styleId="assessedbefore" disabled="true" />
                     </td>
                            </tr>
		 

                        <tr>
                            <td valign="top" class="right" style=" height: 20px;">Date of first capacity assessment </td>
                              <td>
                                  <input type="text" name="disableddateoffirstcapassessment" id="disableddateoffirstcapassessment"  class="fieldcellinput" disabled="true" value="${dateOfFirstAssessment}"/>
                              </td>
                            </tr>

                            <tr>
                            <td valign="top" class="right" style=" height: 20px;">Name of lead assessor </td>
                              <td>
                                    <html:text property="leadassessorname" styleId="leadassessorname"  styleClass="fieldcellinput"/>
                              </td>
                            </tr>
                            <tr>
                            <td valign="top" class="right" style=" height: 20px;">Number of assessors in the OC assessment </td>
                              <td>
                                    <html:text property="noofassessors" styleId="noofassessors"  styleClass="fieldcellinput"/>
                              </td>
                            </tr>
                        </table>
                      </fieldset>

                      </td>
                      </tr>
                  </table></td>
                </tr>


                







                <tr>
                  <td height="553" valign="top">
                      <br/>
                      <fieldset>
                      <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->



                    <tr>
                      <td valign="top">
                          <br/>



                              <!--<table> <tr><td>-->
                          <table width="100%" border="1" bordercolor="#D7E5F2" class="regsitertable">
                            <tr>
                                <td width="25%" align="center"><div align="center">Domain</div></td>
                                <td width="7%" align="center">Not assessed<div align="center">0</div></td>
                              <td width="7%" align="center">Seeding<br /><div align="center">1</div></td>
                              <td width="7%" align="center">Vegetation<br /><div align="center">2</div></td>
                              <td width="7%" align="center">Flowering<br /><div align="center">3</div> </td>
                              <td width="7%" align="center">Fruit/Ripening<br /><div align="center">4</div></td>                            
                            </tr>
                        </table>
                         <br/>
                          <fieldset>

                        <legend class="fieldset">Organizational purpose and planning </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!--DWLayoutTable-->
                        <tr>
                          <td width="752" >
                              <table width="100%" border="1" bordercolor="#D7E5F2" class="regsitertable">
                            
                            <tr>
                                <td width="25%" ><div >Organizational vision and mission</div></td>
                                <td width="7%" align="center"><html:radio property="visionandmission" styleId="visionandmission1" value="0"/></td>
                              <td width="7%" align="center"><html:radio property="visionandmission" styleId="visionandmission2" value="1" /></td>
                              <td width="7%" align="center"><html:radio property="visionandmission" styleId="visionandmission3" value="2" /></td>
                              <td width="7%" align="center"><html:radio property="visionandmission" styleId="visionandmission4" value="3" /> </td>
                              <td width="7%" align="center"><html:radio property="visionandmission" styleId="visionandmission5" value="4" /></td>
                            </tr>
                            <tr>
                                <td width="25%" ><div >Organizational goals and objectives</div></td>
                                <td width="7%" align="center"><html:radio property="goalsandobjectives" styleId="goalsandobjectives1" value="0"/></td>
                              <td width="7%" align="center"><html:radio property="goalsandobjectives" styleId="goalsandobjectives2" value="1" /></td>
                              <td width="7%" align="center"><html:radio property="goalsandobjectives" styleId="goalsandobjectives3" value="2" /></td>
                              <td width="7%" align="center"><html:radio property="goalsandobjectives" styleId="goalsandobjectives" value="3" /> </td>
                              <td width="7%" align="center"><html:radio property="goalsandobjectives" styleId="goalsandobjectives" value="4" /></td>
                            </tr>
                            <tr>
                                <td width="25%" ><div >Action planning</div></td>
                                <td width="7%" align="center"><html:radio property="actionplanning" styleId="actionplanning1" value="0"/></td>
                              <td width="7%" align="center"><html:radio property="actionplanning" styleId="actionplanning2" value="1" /></td>
                              <td width="7%" align="center"><html:radio property="actionplanning" styleId="actionplanning3" value="2" /></td>
                              <td width="7%" align="center"><html:radio property="actionplanning" styleId="actionplanning4" value="3" /> </td>
                              <td width="7%" align="center"><html:radio property="actionplanning" styleId="actionplanning5" value="4" /></td>
                            </tr>
                               
                          </table></td>
                        </tr>
                      </table></fieldset>                      </td>
                      </tr>
                  
                      <tr>
                      <td valign="top"><fieldset>
                        <legend class="fieldset">Organizational structure and procedures </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!--DWLayoutTable-->
                        <tr>
                          <td width="752" >
                              <table width="100%" border="1" bordercolor="#D7E5F2" class="regsitertable">
                            
                            <tr>
                                <td width="25%" ><div >Structure, roles and resposibilities</div></td>
                                <td width="7%" align="center"><html:radio property="roleandresponsibilities" styleId="roleandresponsibilities1" value="0"/></td>
                              <td width="7%" align="center"><html:radio property="roleandresponsibilities" styleId="roleandresponsibilities2" value="1" /></td>
                              <td width="7%" align="center"><html:radio property="roleandresponsibilities" styleId="roleandresponsibilities3" value="2" /></td>
                              <td width="7%" align="center"><html:radio property="roleandresponsibilities" styleId="roleandresponsibilities4" value="3" /> </td>
                              <td width="7%" align="center"><html:radio property="roleandresponsibilities" styleId="roleandresponsibilities5" value="4" /></td>
                            </tr>
                            <tr>
                                <td width="25%" ><div >Internal rules</div></td>
                                <td width="7%" align="center"><html:radio property="internalrules" styleId="internalrules1" value="0"/></td>
                              <td width="7%" align="center"><html:radio property="internalrules" styleId="internalrules2" value="1" /></td>
                              <td width="7%" align="center"><html:radio property="internalrules" styleId="internalrules3" value="2" /></td>
                              <td width="7%" align="center"><html:radio property="internalrules" styleId="internalrules4" value="3" /> </td>
                              <td width="7%" align="center"><html:radio property="internalrules" styleId="internalrules5" value="4" /></td>
                            </tr>
                            <tr>
                                <td width="25%" ><div >Meetings</div></td>
                                <td width="7%" align="center"><html:radio property="meetings" styleId="meetings1" value="0"/></td>
                              <td width="7%" align="center"><html:radio property="meetings" styleId="meetings2" value="1" /></td>
                              <td width="7%" align="center"><html:radio property="meetings" styleId="meetings3" value="2" /></td>
                              <td width="7%" align="center"><html:radio property="meetings" styleId="meetings4" value="3" /> </td>
                              <td width="7%" align="center"><html:radio property="meetings" styleId="meetings5" value="4" /></td>
                            </tr>

                          </table></td>
                        </tr>
                      </table></fieldset>                      </td>
                      </tr>


                      <tr>
                      <td valign="top"><fieldset>
                        <legend class="fieldset">Group dynamics </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!--DWLayoutTable-->
                        <tr>
                          <td width="752" >
                              <table width="100%" border="1" bordercolor="#D7E5F2" class="regsitertable">

                            <tr>
                                <td width="25%" ><div >Leadership</div></td>
                                <td width="7%" align="center"><html:radio property="leadership" styleId="leadership1" value="0"/></td>
                              <td width="7%" align="center"><html:radio property="leadership" styleId="leadership2" value="1" /></td>
                              <td width="7%" align="center"><html:radio property="leadership" styleId="leadership3" value="2" /></td>
                              <td width="7%" align="center"><html:radio property="leadership" styleId="leadership4" value="3" /> </td>
                              <td width="7%" align="center"><html:radio property="leadership" styleId="leadership5" value="4" /></td>
                            </tr>
                            <tr>
                                <td width="25%" ><div >Team building</div></td>
                                <td width="7%" align="center"><html:radio property="teambuilding" styleId="teambuilding1" value="0"/></td>
                              <td width="7%" align="center"><html:radio property="teambuilding" styleId="teambuilding2" value="1" /></td>
                              <td width="7%" align="center"><html:radio property="teambuilding" styleId="teambuilding3" value="2" /></td>
                              <td width="7%" align="center"><html:radio property="teambuilding" styleId="teambuilding4" value="3" /> </td>
                              <td width="7%" align="center"><html:radio property="teambuilding" styleId="teambuilding5" value="4" /></td>
                            </tr>

                          </table></td>
                        </tr>
                      </table></fieldset>                      </td>
                      </tr>


                      <tr>
                      <td valign="top"><fieldset>
                        <legend class="fieldset">Monitoring and evaluation </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!--DWLayoutTable-->
                        <tr>
                          <td width="752" >
                              <table width="100%" border="1" bordercolor="#D7E5F2" class="regsitertable">

                            <tr>
                                <td width="25%" ><div >Monitoring</div></td>
                                <td width="7%" align="center"><html:radio property="monitoring" styleId="monitoring1" value="0"/></td>
                              <td width="7%" align="center"><html:radio property="monitoring" styleId="monitoring2" value="1" /></td>
                              <td width="7%" align="center"><html:radio property="monitoring" styleId="monitoring3" value="2" /></td>
                              <td width="7%" align="center"><html:radio property="monitoring" styleId="monitoring4" value="3" /> </td>
                              <td width="7%" align="center"><html:radio property="monitoring" styleId="monitoring5" value="4" /></td>
                            </tr>
                            <tr>
                                <td width="25%" ><div >Reporting and record keeping</div></td>
                                <td width="7%" align="center"><html:radio property="reportingandrecordkeeping" styleId="reportingandrecordkeeping1" value="0"/></td>
                              <td width="7%" align="center"><html:radio property="reportingandrecordkeeping" styleId="reportingandrecordkeeping2" value="1" /></td>
                              <td width="7%" align="center"><html:radio property="reportingandrecordkeeping" styleId="reportingandrecordkeeping3" value="2" /></td>
                              <td width="7%" align="center"><html:radio property="reportingandrecordkeeping" styleId="reportingandrecordkeeping4" value="3" /> </td>
                              <td width="7%" align="center"><html:radio property="reportingandrecordkeeping" styleId="reportingandrecordkeeping5" value="4" /></td>
                            </tr>
                            
                          </table></td>
                        </tr>
                      </table></fieldset>                      </td>
                      </tr>


                      <tr>
                      <td valign="top"><fieldset>
                        <legend class="fieldset">Financial management </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!--DWLayoutTable-->
                        <tr>
                          <td width="752" >
                              <table width="100%" border="1" bordercolor="#D7E5F2" class="regsitertable">

                            <tr>
                                <td width="25%" ><div >Budgeting</div></td>
                                <td width="7%" align="center"><html:radio property="budgeting" styleId="budgeting1" value="0"/></td>
                              <td width="7%" align="center"><html:radio property="budgeting" styleId="budgeting2" value="1" /></td>
                              <td width="7%" align="center"><html:radio property="budgeting" styleId="budgeting3" value="2" /></td>
                              <td width="7%" align="center"><html:radio property="budgeting" styleId="budgeting4" value="3" /> </td>
                              <td width="7%" align="center"><html:radio property="budgeting" styleId="budgeting5" value="4" /></td>
                            </tr>
                            <tr>
                                <td width="25%" ><div >Book keeping</div></td>
                                <td width="7%" align="center"><html:radio property="bookkeeping" styleId="bookkeeping1" value="0"/></td>
                              <td width="7%" align="center"><html:radio property="bookkeeping" styleId="bookkeeping2" value="1" /></td>
                              <td width="7%" align="center"><html:radio property="bookkeeping" styleId="bookkeeping3" value="2" /></td>
                              <td width="7%" align="center"><html:radio property="bookkeeping" styleId="bookkeeping4" value="3" /> </td>
                              <td width="7%" align="center"><html:radio property="bookkeeping" styleId="bookkeeping5" value="4" /></td>
                            </tr>
                            <tr>
                                <td width="25%" ><div >Banking</div></td>
                                <td width="7%" align="center"><html:radio property="banking" styleId="banking1" value="0"/></td>
                              <td width="7%" align="center"><html:radio property="banking" styleId="banking2" value="1" /></td>
                              <td width="7%" align="center"><html:radio property="banking" styleId="banking3" value="2" /></td>
                              <td width="7%" align="center"><html:radio property="banking" styleId="banking4" value="3" /> </td>
                              <td width="7%" align="center"><html:radio property="banking" styleId="banking5" value="4" /></td>
                            </tr>

                          </table></td>
                        </tr>
                      </table></fieldset>                      </td>
                      </tr>

                      <tr>
                      <td valign="top"><fieldset>
                        <legend class="fieldset">Resource mobilization </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!--DWLayoutTable-->
                        <tr>
                          <td width="752" >
                              <table width="100%" border="1" bordercolor="#D7E5F2" class="regsitertable">

                            <tr>
                                <td width="25%"><div >Local resource mobilization</div></td>
                                <td width="7%" align="center"><html:radio property="localresourcemobilization" styleId="localresourcemobilization1" value="0"/></td>
                              <td width="7%" align="center"><html:radio property="localresourcemobilization" styleId="localresourcemobilization2" value="1" /></td>
                              <td width="7%" align="center"><html:radio property="localresourcemobilization" styleId="localresourcemobilization3" value="2" /></td>
                              <td width="7%" align="center"><html:radio property="localresourcemobilization" styleId="localresourcemobilization4" value="3" /> </td>
                              <td width="7%" align="center"><html:radio property="localresourcemobilization" styleId="localresourcemobilization5" value="4" /></td>
                            </tr>
                            <tr>
                                <td width="25%"><div >Proposal writing</div></td>
                                <td width="7%" align="center"><html:radio property="proposalwriting" styleId="proposalwriting1" value="0"/></td>
                              <td width="7%" align="center"><html:radio property="proposalwriting" styleId="proposalwriting2" value="1" /></td>
                              <td width="7%" align="center"><html:radio property="proposalwriting" styleId="proposalwriting3" value="2" /></td>
                              <td width="7%" align="center"><html:radio property="proposalwriting" styleId="proposalwriting4" value="3" /> </td>
                              <td width="7%" align="center"><html:radio property="proposalwriting" styleId="proposalwriting5" value="4" /></td>
                            </tr>

                          </table></td>
                        </tr>
                      </table></fieldset>                      </td>
                      </tr>


                      <tr>
                      <td valign="top"><fieldset>
                        <legend class="fieldset">External relations </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!--DWLayoutTable-->
                        <tr>
                          <td width="752" >
                              <table width="100%" border="1" bordercolor="#D7E5F2" class="regsitertable">

                            <tr>
                                <td width="25%" ><div >Networking</div></td>
                                <td width="7%" align="center"><html:radio property="networking" styleId="networking1" value="0"/></td>
                              <td width="7%" align="center"><html:radio property="networking" styleId="networking2" value="1" /></td>
                              <td width="7%" align="center"><html:radio property="networking" styleId="networking3" value="2" /></td>
                              <td width="7%" align="center"><html:radio property="networking" styleId="networking4" value="3" /> </td>
                              <td width="7%" align="center"><html:radio property="networking" styleId="networking5" value="4" /></td>
                            </tr>
                            <tr>
                                <td width="25%" ><div >Advocacy</div></td>
                                <td width="7%" align="center"><html:radio property="advocacy" styleId="advocacy1" value="0"/></td>
                              <td width="7%" align="center"><html:radio property="advocacy" styleId="advocacy2" value="1" /></td>
                              <td width="7%" align="center"><html:radio property="advocacy" styleId="advocacy3" value="2" /></td>
                              <td width="7%" align="center"><html:radio property="advocacy" styleId="advocacy4" value="3" /> </td>
                              <td width="7%" align="center"><html:radio property="advocacy" styleId="advocacy5" value="4" /></td>
                            </tr>
                            
                          </table></td>
                        </tr>
                      </table></fieldset>
                      </td>
                      </tr>


                    

                  </table>


                 </fieldset>
                  </td>
                </tr>
                <tr>
                        <td height="40" align="center">
                            <br/>
                            <html:reset value="New" styleId="newBtn" style="width:70px; height:25px;  margin-right:5px;" disabled="true"/><html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${enableOrgCapSave}"/>
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${enableOrgCapModify}"/><html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${enableOrgCapModify}"/>
                        </td>
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
          
    </html:form>

<!--</div>
</div>
</div>-->

          &nbsp;</td>
      </tr>

    </table>    </td>
    <td width="18">&nbsp;</td>
  </tr>
  <tr>
    <td height="25" colspan="2" valign="top">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <!--DWLayoutTable-->
      <tr>
          <td width="949" height="25" class="copyright" bgcolor="#038233" style=" margin-left: 5px;"><jsp:include page="includes/Version.jsp"/></td>
        </tr>
    </table></td>
  </tr>
</table>
  
</body>
</html>


