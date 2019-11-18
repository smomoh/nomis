<%-- 
    Document   : CVDataEntry
    Created on : Jul 7, 2011, 10:29:43 AM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<!--<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">-->
<%@page import="com.fhi.nomis.nomisutils.*" %>

<%@page import="com.fhi.kidmap.dao.StateDaoImpl" %>
<%@page import="java.util.List" %>
<%@page import="com.fhi.kidmap.dao.StateDao" %>
<%@page import="java.util.ArrayList" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>

<%
    LoadUpInfo loadup=new LoadUpInfo();
    loadup.processCboSetup(session);
    loadup.getSchoolsPerState(session);
    loadup.setEnrollmentAttributes(session);
    loadup.getAllStates(session);
    loadup.displayEligibilityCriteria(session);
%>

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
                $("#dateEnrollment").datepicker();
            });


 var req;
 var which;
 var callerId=""
 var resetId=0
 var searchIndex=0


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
                <td width="95" height="65"><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Care and Support','','images/care_down.jpg',1)"><img src="images/care_up.jpg" alt="Care and Support" name="Care and Support" width="95" height="65" border="0" id="Care and Support" /></a></td>
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
                    <div><a href="Enrollment.jsp" onmouseover="setToolTipText('Click this link to get to the enrollment data entry page','30%','30%')" onmouseout="hideComponent('tooltip')">Enrolment card </a> <a href="serviceform.jsp">Service form</a><a href="followupsurvey.jsp">Follow up survey form</a></div>
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
              <div><a href="Reports/CsiParamPage.jsp" target="_blank" >CSI analysis</a><a href="Reports/EnrollmentRecord.jsp" target="_blank" >Enrollment records</a><a href="Reports/SchoolAttendanceParamPage.jsp" target="_blank" >School attendance</a><a href="Reports/SummStatParamPage.jsp" target="_blank" >Summary statistics</a><a href="Reports/CaregiverListParamPage.jsp" target="_blank" >List of caregivers</a><a href="Reports/DqaParamPage.jsp" target="_blank" >Data quality assessment</a><a href="Reports/OvcMthlySummaryFormParamPage.jsp" target="_blank" >OVC Monthly summary form</a><a href="ChartPage.jsp">Charts </a></div>
            </div></td>
          </tr>


      </table></td>
    <td width="13">&nbsp;</td>
      <td width="639" >

          <div align="center">
			<div style="width:75+7px; height:1370px; background-color:#FFFFFF">
			<div align="left">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
     <td width="30%" height="20" class="homepagestyle">OVC Enrollment card </td>
     <td width="70%"> <html:errors/> </td>
    </tr>
     <tr><td height="10" colspan="2" align="center"><label style=" font-size: 15px; color: green">${partnername}</label> </td></tr>
  </table>
    <html:form styleId="dataForm"  method="post" action="/Add">
            <input type="hidden" name="stateId2" />
            <input type="hidden" name="lgaId2" />
            <html:hidden property="stateCode" styleId="stateCode" value="${state.state_code}" />
            <html:hidden property="lga" styleId="lgaId" value="${lga.lga_code}" />
            <input type="hidden" name="lgaCode" id="lgaCode" />
            <input type="hidden" name="stateId" id="stateId" value="${state.state_code}" />
            <html:hidden property="actionName" styleId="buttonId"/>
            <html:hidden property="childIndexId" styleId="childIndexId" />
            <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
        <!--DWLayoutTable-->
        <tr>
          <td width="8" height="1258">&nbsp;</td>
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

                              <tr>
                                  <td width="4%"><label>State:</label></td>
                          <td width="30%">
                              <html:text styleId="stateList" property="state" readonly="true" styleClass="bigfieldcellinput" value="${state.name}" ></html:text>

                          </td>
                              <td width="4%"><label>LGA:</label></td>
                              <td width="30%"><span id="selected">
                                      <%--<label class="bigfieldcellinput">${lga.name}</label>--%>
                                      <input type="text" class="bigfieldcellinput" readonly="readonly" id="LgasSelect" value="${lga.lga_name}"/></span></td>

                            </tr>
                        <tr>
                            <td width="4%" ><label>CBO:</label></td>
                              <td width="30%">
                                  <span id="selected2">
                                      <html:select property="completedbyCbo" styleId="CbosSelect" styleClass="bigfieldcellinput" style="width:250px;" onchange="getwards(this.value);">
                                      <html:option value=""> </html:option>
                                      <logic:iterate id="cbos" name="cbolist">
                                              <html:option value="${cbos.cbo_code}">${cbos.cbo_name} </html:option>
                                          </logic:iterate>
                                      </html:select>


                                  </span></td>
                            <td width="4%"><label >Ward:</label></td>

                              <td width="30%">
                                  <html:select property="ward" styleId="WardsSelect" styleClass="bigfieldcellinput">
                                  <html:option value=""> </html:option>
                                  <logic:iterate id="lastward" name="lastwardlist">
                                        <html:option value="${lastward.ward_name}">${lastward.ward_name} </html:option>
                                  </logic:iterate>


                              </html:select>
                                  </td>
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
                        <legend class="fieldset">Personal information  </legend>
                        <table width="100%" class="regsitertable">
                        <tr><td width="15%" valign="top" class="right">Date of engagement:</td>

                              <td>
                                  <html:text property="dateEnrollment" styleId="dateEnrollment" styleClass="smallfieldcellinput"/>

                                  &nbsp;&nbsp;<%--<span style="margin-left:110px;">OVC serial no.</span>
                                  <html:text property="ovcSno"  styleClass="smallfieldcellinput" styleId="ovcSno" onkeyup="makeId()" onblur="checkDupId()"/>&nbsp;<input type="button" name="search" value="Search by name" onclick="showSearchDiv()" onmouseover="setToolTipText('This button enables you to search an OVC by name','52%','60%')" onmouseout="hideComponent('tooltip')"/>--%></td>
                            </tr>

                        
                        <tr>
                          <td valign="top" class="right">Surname: </td>
                              <td>

                                  <html:text property="surname" styleId="surname" styleClass="fieldcellinput" onkeyup="capitalizeAll('surname', this.value)" onblur="checkDuplicateByName()"/>
                                  <span style="margin-left:17px; margin-right: 15px;">First name:</span> <html:text property="otherNames1" styleId="otherNames1" styleClass="smallfieldcellinput" onkeyup="capitalize('otherNames1', this.value)" onblur="checkDuplicateByName()" />
                                  <span style="margin-left:10px; margin-right: 5px;">Middle name:</span><html:text property="otherNames2" styleId="otherNames2" styleClass="smallfieldcellinput" onkeyup="capitalize('otherNames2', this.value)" onchange="checkDuplicateByName()"/></td>
                            </tr>
                        <tr>
                          <td valign="top" class="right">Sex: </td>
                              <td>
                                    <html:select property="gender" styleId="gender" styleClass="fieldcellinput" style="width:70px;">
                    <html:option value=""> </html:option><html:option value="Male">Male</html:option><html:option value="Female">Female</html:option>
                                    </html:select> <span style=" margin-left: 52px;">Age:</span>
                    <html:text property="age" styleId="age" styleClass="shortfieldcellinput" onchange="checkDuplicateByName()"/>

                    
                    
                <%--<html:select styleId="ageUnit" styleClass="fieldcellinput" property="ageUnit" style="width:82px;" onchange="checkDuplicateByName()">
                    <html:option value=""></html:option>
                    <html:option value="Year">Year</html:option>
                    <html:option value="Month">Month</html:option>
                </html:select>--%></td>
                            </tr>


							 <tr>
                          <td valign="top" class="right">&nbsp;</td>
                              <td><span id="characters3" style="color: red"></span>  </td>
                            </tr>


                        <tr>
                            <td valign="top" class="right" style=" height: 20px;"><br/>Address: </td>
                              <td>
                                    <html:textarea property="address" styleId="address" rows="4" cols="80" styleClass="textareacellinput"></html:textarea>
                                    <span style=" margin-left: 16px; margin-right: 18px;">Telephone:</span> <html:text property="phone" styleId="phone" styleClass="smallfieldcellinput" /></td>
                            </tr>
                        </table>
                      </fieldset>




                      </td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="123" valign="top">
                      <fieldset>
                        <legend class="fieldset">Vulnurability status<!--Eligibility criteria--> </legend>
                        <div style="width:720px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="102">
                          <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:iterate name="finalList" id="propertyList">
                                  <tr><td>${propertyList[0].name} </td> <td><html:multibox property='eligibility' styleId="${propertyList[0].id}" value="${propertyList[0].name}" styleClass='smallfieldcellselect'/> </td><td>${propertyList[1].name} </td> <td><html:multibox property='eligibility' styleId="${propertyList[1].id}" value="${propertyList[1].name}" styleClass='smallfieldcellselect'/> </td></tr>
                              </logic:iterate>

                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
                  </fieldset></td>
                </tr>
                <tr>
                  <td height="53" valign="top">

                      <fieldset>
                        <legend class="fieldset">Child's HIV status </legend><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="34"><table width="100%" class="regsitertable">
                        <tr>
                          <td width="16%">HIV status unknown</td>
                          <td width="10%"><html:radio property="hivStatus" styleClass="smallfieldcellselect" styleId="hivStatus1" value="HIV status unknown" /></td>
                          <td width="10%">HIV negative </td>
                          <td width="10%"><html:radio property="hivStatus" styleClass="smallfieldcellselect" styleId="hivStatus2" value="HIV negative" /></td>
                          <td width="10%">HIV positive </td>
                          <td width="58%"><html:radio property="hivStatus" styleClass="smallfieldcellselect" styleId="hivStatus3" value="HIV positive" /></td>
                        </tr>
                      </table></td>
                    </tr>
                  </table>
                  </fieldset></td>
                </tr>
                <tr>
                  <td height="127" valign="top"><fieldset>
                        <legend class="fieldset">Birth registration and education </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="48"><table width="100%" class="regsitertable">
                        <tr>
                          <td width="40%" class="right">Does the child have birth registration certificate? </td>
                              <td width="60%"><label>
                                      <html:select property="birthCertificate" styleId="birthCertificate" styleClass="shortfieldcellinput">
                    <html:option value=" "> </html:option>
                    <html:option value="Yes">Yes</html:option>
                    <html:option value="No">No</html:option>
                </html:select>
                                  </label> <span style=" margin-left: 10px; margin-right: 10px;">Is the child in school?</span> <html:select property="schoolStatus" styleId="schoolStatus" onchange="childInSchool(this.value)" styleClass="shortfieldcellinput">
                                   <html:option value=" "> </html:option>
                                   <html:option value="Yes">Yes</html:option>
                                   <html:option value="No">No</html:option>
                                   </html:select>
                              </td>
                            </tr>
                         <tr>
                          <td class="right">Is the school the child is enrolled Public or Private: <br /></td>
                              <td><label>
                                      <!--changeSchoolType('schCombo') changeSchoolType('schText')-->
                                      Public school &nbsp;<html:radio property="schoolType" styleId="schoolType1" disabled="true" value="Public" styleClass="smallfieldcellselect" onclick="populateSchools('public')" />
                                                                                <span style=" margin-left: 13px;">Private school</span>
                                                                                &nbsp;<html:radio property="schoolType" styleId="schoolType2" disabled="true" styleClass="smallfieldcellselect" value="Private" onclick="populateSchools('private')" />
                         </label></td>
                            </tr>
                        <tr>
                          <td class="right">Name of school:</td>
                          <td><span id="schoolNameSpan">
                                  <html:select styleClass="fieldcellinput" property="schoolName" styleId="schoolName" disabled="true" >
                                      <html:option value=" "> </html:option>
                                      <logic:iterate id="school" name="schools">
                                      <html:option value="${school.name}">${school.name}</html:option>
                                      </logic:iterate>
                                  </html:select>
                                  <!-- onclick="populateSchools()" <input type="text" id="schoolName1" class="fieldcellinput" disabled="true" />--></span> Class: <html:text property="currentClass" styleId="currentClass" styleClass="smallfieldcellinput" disabled="true" /></td>
                        </tr>

                        <tr>
                          <td class="right">Does the child live in orphange: </td>
                              <td>
                                  <html:select property="orphanage" styleClass="shortfieldcellinput" styleId="orphanage" onchange="childInOrphanage(this.value)">
                                  <html:option value=" "> </html:option>
                                <html:option value="Yes">Yes</html:option>
                    <html:option value="No">No</html:option>
                </html:select>
                                  </td>
                            </tr>
                        <tr>
                          <td class="right">Name of orphanage:</td>
                          <td><html:text property="orphanageName" styleId="orphanageName" styleClass="fieldcellinput" disabled="true" /></td>
                        </tr>
                        </table></td>
                      </tr>
                  </table>
                  </fieldset></td>
                </tr>
                <tr>
                  <td height="553" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->

                    <tr>
                      <td width="752" height="92"><fieldset>
                        <legend class="fieldset">Household information </legend>
                        <table width="100%" class="regsitertable">
                            <tr>
                          <td width="20%" bgcolor="#F0F0F0"><div align="left">Surname: </div></td>
                          <td width="85%" bgcolor="#F0F0F0"><html:text property="hhSurname" styleId="hhSurname" styleClass="fieldcellinput" />
                              <span style=" margin-left:10px; margin-right: 15px;">First name:</span> <html:text property="hhFirstname" styleId="hhFirstname" styleClass="fieldcellinput" /></td>
                            </tr>
                          <tr>
                           <td bgcolor="#F0F0F0"><div align="left">Sex:</div></td>
                          <td bgcolor="#F0F0F0">
                              <html:select property="hhGender" styleId="hhGender" styleClass="smallfieldcellinput"><html:option value=""> </html:option><html:option value="Male">Male</html:option>
                                  <html:option value="Female">Female</html:option></html:select>
                                  <span style=" margin-left: 112px; margin-right:56px;">Age:</span><html:text property="hhAge" styleId="hhAge" styleClass="shortfieldcellinput"/></td>
                            </tr>
                           <tr>
                            <td class="right">Number of Children 0-17 years in the household</td>
                            <td colspan="5"><html:text property="numOfChildrenInHh" styleId="numOfChildrenInHh"  styleClass="shortfieldcellinput"></html:text> <span style="margin-left:48px; margin-right: 5px;">Number of OVC in the household: </span>
                                <html:text property="numOfOVCInHh" styleId="numOfOVCInHh" styleClass="shortfieldcellinput" />
                            </td>
                            </tr>
                          <%--<tr>
                            <td class="right">Number of OVC in the household</td>
                            <td colspan="5"><html:text property="caregiverPhone" styleId="caregiverPhone" styleClass="smallfieldcellinput" />
                                <span style="margin-left:110px; margin-right: 15px;">Occupation: </span><html:text property="caregiverOccupation" styleId="caregiverOccupation" styleClass="fieldcellinput" /></td>
                            </tr>--%>
                          <tr>
                            <td class="right"> </td>
                            <td colspan="5"></td>
                            </tr>
                          </table></fieldset></td>
                      </tr>


                    <tr>
                      <td width="752" height="92"><fieldset>
                        <legend class="fieldset">Caregiver information </legend>
                        <table width="100%" class="regsitertable">
                            <tr>
                          <td width="20%" bgcolor="#F0F0F0"><div align="left">Surname: </div></td>
                          <td width="85%" bgcolor="#F0F0F0"><html:text property="caregiverName1" styleId="caregiverName1" styleClass="fieldcellinput" onblur="checkDuplicateByCareGiver()" />
                              <span style=" margin-left:10px; margin-right: 13px;">First name:</span> <html:text property="caregiverName2" styleId="caregiverName2" styleClass="fieldcellinput" onblur="checkDuplicateByCareGiver()" /><span id="characters4" style="margin-left:20px;color: red"></span></td>
                            </tr>
                          <tr>
                           <td bgcolor="#F0F0F0"><div align="left">Sex:</div></td>
                          <td bgcolor="#F0F0F0">
                              <html:select property="caregiverGender" styleId="caregiverGender" styleClass="smallfieldcellinput"><html:option value=""> </html:option><html:option value="Male">Male</html:option>
                                  <html:option value="Female">Female</html:option></html:select>
                                  <span style=" margin-left: 112px; margin-right:56px;">Age:</span><html:text property="caregiverAge" styleId="caregiverAge" styleClass="shortfieldcellinput"/></td>
                            </tr>
                           <tr>
                            <td class="right">Address:</td>
                            <td colspan="5"><html:textarea property="caregiverAddress" styleId="caregiverAddress" rows="5" cols="40" styleClass="fieldcellinput"></html:textarea></td>
                            </tr>
                          <tr>
                            <td class="right">Telephone:</td>
                            <td colspan="5"><html:text property="caregiverPhone" styleId="caregiverPhone" styleClass="smallfieldcellinput" />
                                <span style="margin-left:110px; margin-right: 74px;">Occupation: </span><html:text property="caregiverOccupation" styleId="caregiverOccupation" styleClass="fieldcellinput" /></td>
                            </tr>
                          <tr>
                            <td class="right"> </td>
                            <td colspan="5"></td>
                            </tr>
                          <tr>
                              <td class="right" ><div>Relationship to child:
                                 </div></td>
                                 <td width="60%" bordercolor="#FFFFFF" bgcolor="#F0F0F0"><html:select property="caregiverRelationships" styleId="caregiverRelationships" styleClass="fieldcellinput">
                                      <html:option value=" "> </html:option>
                                      <html:option value="Nuclear family member">Nuclear family member</html:option>
                    <html:option value="Neighbour/Friend">Neighbour/Friend</html:option>
                    <html:option value="Social worker">Social worker</html:option>

                                  </html:select> <span style=" margin-left: 12px;">Extended family member:</span>  <html:text property="caregiverRelationships2" styleId="caregiverRelationships2" styleClass="fieldcellinput"/>
                              </td>
                              <%--<td width="60%" bordercolor="#FFFFFF" bgcolor="#F0F0F0"><html:select property="caregiverRelationships" styleId="caregiverRelationships" styleClass="fieldcellinput">
                                      <html:option value=" "> </html:option>
                                      <html:option value="Mother/Father">Mother/Father</html:option>
                    <html:option value="Aunt/Uncle">Aunt/Uncle</html:option>
                    <html:option value="Social worker/Orphanage caregiver">Social worker/Orphanage caregiver</html:option>
					<html:option value="Grandparents">Grandparents</html:option>
					<html:option value="Brother/Sister">Brother/Sister</html:option>
                                  </html:select> <span style=" margin-left: 12px;">Other Relative:</span>  <html:text property="caregiverRelationships2" styleId="caregiverRelationships2" styleClass="fieldcellinput"/>
                              </td>--%>

                          </tr>


                        </table></fieldset></td>
                      </tr>




                    <tr>
                      <td height="172" valign="top"><fieldset>
                        <legend class="fieldset">Baseline / Initial child status index assessment </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!--DWLayoutTable-->
                        <tr>
                          <td width="752" height="172">
                              <table width="100%" border="1" bordercolor="#D7E5F2" class="regsitertable">
                            <tr>
                              <td width="17%" rowspan="2"><strong>Domain</strong></td>
                              <td colspan="4"><strong>Score<br />
                                (Mark as appropriate)</strong></td>
                              <td width="21%" rowspan="2"><strong>Domain</strong></td>
                              <td colspan="4"><strong>Score<br />
(Mark as appropriate)</strong></td>
                              </tr>
                            <tr>
                                <td width="7%" align="center">Good<br /><div align="center">4</div></td>
                              <td width="7%" align="center">Fair<br /><div align="center">3</div></td>
                              <td width="7%" align="center">Bad<br /><div align="center">2</div></td>
                              <td width="7%" align="center">Very Bad<br /><div align="center">1</div> </td>

                              <td width="7%" align="center">Good<br /><div align="center">4</div></td>
                              <td width="7%" align="center">Fair<br /><div align="center">3</div></td>
                              <td width="7%" align="center">Bad<br /><div align="center">2</div></td>
                              <td width="7%" align="center">Very Bad<br /><div align="center">1</div> </td>
                            </tr>
                            <tr>
                              <td colspan="5" bgcolor="#F0F0F0"><strong>Food and nutrition </strong></td>
                              <td colspan="5" bgcolor="#F0F0F0"><strong>Health </strong></td>
                              </tr>
                            <tr>
                                <td>Food security </td>
                              <td align="center"><html:radio property="csiFactor3" styleId="csiFactor3_4" value="4" /></td><td align="center"><html:radio property="csiFactor3" styleId="csiFactor3_3" value="3" /></td><td align="center"><html:radio property="csiFactor3" styleId="csiFactor3_2" value="2" /></td><td align="center"><html:radio property="csiFactor3" styleId="csiFactor3_1" value="1" /></td>
                              <td>Wellness</td>
                              <td align="center"><html:radio property="csiFactor5" styleId="csiFactor5_4" value="4" /></td><td align="center"><html:radio property="csiFactor5" styleId="csiFactor5_3" value="3" /></td><td align="center"><html:radio property="csiFactor5" styleId="csiFactor5_2" value="2" /></td><td align="center"><html:radio property="csiFactor5" styleId="csiFactor5_1" value="1" /></td>

                            </tr>
                            <tr>
                                <td>Nutrition and growth </td>
                              <td align="center"><html:radio property="csiFactor4" styleId="csiFactor4_4" value="4" /></td><td align="center"><html:radio property="csiFactor4" styleId="csiFactor4_3" value="3" /></td><td align="center"><html:radio property="csiFactor4" styleId="csiFactor4_2" value="2" /></td><td align="center"><html:radio property="csiFactor4" styleId="csiFactor4_1" value="1" /></td>
                              <td>Health care services </td>
                              <td align="center"><html:radio property="csiFactor6" styleId="csiFactor6_4" value="4" /></td><td align="center"><html:radio property="csiFactor6" styleId="csiFactor6_3" value="3" /></td><td align="center"><html:radio property="csiFactor6" styleId="csiFactor6_2" value="2" /></td><td align="center"><html:radio property="csiFactor6" styleId="csiFactor6_1" value="1" /></td>

                            </tr>
                            <tr>
                              <td colspan="5" bgcolor="#F0F0F0"><strong>Shelter and care</strong></td>
                              <td colspan="5" bgcolor="#F0F0F0"><strong>Psychosocial</strong></td>
                              </tr>
                            <tr>
                              <td>Shelter</td>
                              <td align="center"><html:radio property="csiFactor11" styleId="csiFactor11_4" value="4" /></td><td align="center"><html:radio property="csiFactor11" styleId="csiFactor11_3" value="3" /></td><td align="center"><html:radio property="csiFactor11" styleId="csiFactor11_2" value="2" /></td><td align="center"><html:radio property="csiFactor11" styleId="csiFactor11_1" value="1" /></td>

                              <td>Emotional health </td>
                           <td align="center"><html:radio property="csiFactor1" styleId="csiFactor1_4" value="4" /></td>
                            <td align="center"><html:radio property="csiFactor1" styleId="csiFactor1_3" value="3" /></td>
                             <td align="center"><html:radio property="csiFactor1" styleId="csiFactor1_2" value="2" /></td>
                            <td align="center"><html:radio property="csiFactor1" styleId="csiFactor1_1" value="1" /></td>

                            </tr>
                            <tr>
                              <td>Care</td>
                              <td align="center"><html:radio property="csiFactor12" styleId="csiFactor12_4" value="4" /></td><td align="center"><html:radio property="csiFactor12" styleId="csiFactor12_3" value="3" /></td><td align="center"><html:radio property="csiFactor12" styleId="csiFactor12_2" value="2" /></td><td align="center"><html:radio property="csiFactor12" styleId="csiFactor12_1" value="1" /></td>
                              <td>Social behaviour </td>
                              <td align="center"><html:radio property="csiFactor2" styleId="csiFactor2_4" value="4" /></td><td align="center"><html:radio property="csiFactor2" styleId="csiFactor2_3" value="3" /></td><td align="center"><html:radio property="csiFactor2" styleId="csiFactor2_2" value="2" /></td><td align="center"><html:radio property="csiFactor2" styleId="csiFactor2_1" value="1" /></td>

                            </tr>
                            <tr>
                              <td colspan="5" bgcolor="#F0F0F0"><strong>Protection</strong></td>
                              <td colspan="5" bgcolor="#F0F0F0"><strong>Education and work </strong></td>
                              </tr>
                            <tr>
                              <td>Abuse and exploitation </td>
                              <td align="center"><html:radio property="csiFactor9" styleId="csiFactor9_4" value="4" /></td><td align="center"><html:radio property="csiFactor9" styleId="csiFactor9_3" value="3" /></td><td align="center"><html:radio property="csiFactor9" styleId="csiFactor9_2" value="2" /></td><td align="center"><html:radio property="csiFactor9" styleId="csiFactor9_1" value="1" /></td>
                              <td>Performance</td>
                              <td align="center"><html:radio property="csiFactor7" styleId="csiFactor7_4" value="4" /></td>
                              <td align="center"><html:radio property="csiFactor7" styleId="csiFactor7_3" value="3" /></td>
                              <td align="center"><html:radio property="csiFactor7" styleId="csiFactor7_2" value="2" /></td>
                              <td align="center"><html:radio property="csiFactor7" styleId="csiFactor7_1" value="1" /></td>

                            </tr>
                            <tr>
                              <td>Legal protection </td>
                              <td align="center"><html:radio property="csiFactor10" styleId="csiFactor10_4" value="4" /></td><td align="center"><html:radio property="csiFactor10" styleId="csiFactor10_3" value="3" /></td><td align="center"><html:radio property="csiFactor10" styleId="csiFactor10_2" value="2" /></td><td align="center"><html:radio property="csiFactor10" styleId="csiFactor10_1" value="1" /></td>
                              <td>School work </td>
                              <td align="center"><html:radio property="csiFactor8" styleId="csiFactor8_4" value="4" /></td><td align="center"><html:radio property="csiFactor8" styleId="csiFactor8_3" value="3" /></td><td align="center"><html:radio property="csiFactor8" styleId="csiFactor8_2" value="2" /></td><td align="center"><html:radio property="csiFactor8" styleId="csiFactor8_1" value="1" /></td>
                            </tr>
                            <!--<tr>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                            </tr>-->

                          </table></td>
                        </tr>
                      </table></fieldset>                      </td>
                      </tr>
                    <tr>
                      <td height="52" valign="top"><fieldset>
                        <legend class="fieldset">Completed by <!--Services child recieved at intake <span class="opt style1"><em>( Tick if child received services at this encounter ) </em></span>--></legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!--DWLayoutTable-->
                        <tr>
                          <td width="752" height="33">

                            <table>
                            <!--<tr>
                          <td width="752" height="28" colspan="6">
                              ----- <span class="homepagestyle">Completed by :-------------------------------------------------------------------------------------------------------------</span></td>
                      </tr>-->
                            <tr>
                              <td width="3%"><label>Surname</label> </td>
                              <td width="4%"><html:text property="completedbyName1" styleId="completedbyName1" styleClass="smallfieldcellinput" /></td>
                              <td width="7%"><label>First name</label> </td>
                              <td width="4%"><html:text property="completedbyName2" styleId="completedbyName2" styleClass="smallfieldcellinput" /></td>
                              <td width="4%"><label >Designation</label></td>
                              <td width="32%"><html:text property="completedbyDesignation" styleId="completedbyDesignation" styleClass="smallfieldcellinput" /> <label style="margin-left: 5px;">Organization</label><html:text property="verifiedBy" styleId="verifiedBy" styleClass="smallfieldcellinput"/></td>
                            </tr>
                          </table></td>
                        </tr>
                      </table>
                      </fieldset></td>
                    </tr>

                    <tr>
                        <td height="40" align="center">
                            <html:reset value="New" styleId="newBtn" style="width:70px; height:25px;  margin-right:5px;" disabled="true"/><html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${enableSave}"/>
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${enableModify}"/><html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${enableModify}"/>
                        </td>
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
          <div id="schText" style=" visibility: hidden; position: absolute"><span><input type="text" id="privateSchool" class="fieldcellinput" onblur="setSchoolName(this.value)" /></span>
          </div>
    </html:form>

</div>
</div>
</div>

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
          <td width="949" height="25" class="copyright" bgcolor="#038233" style=" margin-left: 5px;">Copyright &copy; National OVC Management Information System</td>
        </tr>
    </table></td>
  </tr>
</table>
  <div id="tooltip" class="tooltip" style="width:210px;">
      <span id="tooltipspan"> </span>
  </div>
  <div id="pop" class="search" style="width:210px;">
    <table><tr><td style="width:208px;"><label id="title" style="color:#FFFFFF; width:198px;">Browse</label></td><td><img name="popClose" src="images/close.jpg" style="width:10px; height:10px;" onClick="hideComponent('pop')"/></td></tr>
        <tr><td colspan="2" align="left"><span><input type="text" name="selectedName" style="width:195px;" style="margin-top:0px;" onkeyup="filterNames(this.value)"/></span></td></tr>
        <tr><td colspan="2"><span id="ovcNames"> </span></td></tr>
    </table>
  </div>
</body>
</html>
