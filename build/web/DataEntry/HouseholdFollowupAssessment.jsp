<%-- 
    Document   : HouseholdFollowupAssessment
    Created on : Mar 25, 2015, 2:32:41 PM
    Author     : smomoh
--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>NOMIS - National VC Management Information System </title>
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
<link type="text/css" href="css/ui-darkness/jquery-ui-1.8.custom.css" rel="Stylesheet" />
<link href="kidmap-default.css" rel="stylesheet" type="text/css" />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="kidmap.js" type="text/JavaScript"></script>
 <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
 <script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
 <script type="text/javascript" src="js/CRSjsfile.js"></script>
<script type="text/javascript">
function withdrawnAction(value) 
{
    if(value == "Yes") {
        document.getElementById("migrated").disabled = false;
        document.getElementById("losttofollowup").disabled = false;
        document.getElementById("graduated").disabled = false;
        document.getElementById("transfered").disabled = false;
    }
    else 
    {
        document.getElementById("migrated").disabled = true;
        document.getElementById("losttofollowup").disabled = true;
        document.getElementById("graduated").disabled = true;
        document.getElementById("transfered").disabled = true;
    }
}
function constructUniqueId(SNo)
{
    var orgCode=document.getElementById("orgCode").value
    if(orgCode==null || orgCode.indexOf("/")==-1)
    {
       clearUniqueSNoField()
       alert("Select organization/CSO")
       return false
    }
    else
    orgCode=orgCode.substring(orgCode.lastIndexOf("/")+1)
    var paddedSNo=padSerialNo(SNo)
    if(paddedSNo==0)
    {
        paddedSNo=" "
    }
    var uniqueId=document.getElementById("stateCode").value+"/"+document.getElementById("lgaCode").value+"/"+orgCode+"/"+paddedSNo
    document.getElementById("hhUniqueId").value=uniqueId
    document.getElementById("uniqueIdLabel").innerHTML=uniqueId
    
    return true
}
function padSerialNo(SNo)
{
    var numberLength=SNo.length
    if(SNo=="" || SNo==" ")
    {
        return 0
    }
    else if(isNaN(SNo))
    {
        clearUniqueSNoField()
        alert("Serial number must be numeric")
        return 0
    }
    else if(SNo <1)
    {
        clearUniqueSNoField()
        alert("Serial number must be greater than 0")
        return 0
    }
    else if(numberLength>5)
    {
        clearUniqueSNoField()
        alert("Serial number must not be more than 4 digits")
        return 0
    }

    while(numberLength<5)
    {
        SNo="0"+SNo
        numberLength++
    }
    return SNo
}
function clearUniqueSNoField()
{
    document.getElementById("serialNo").value=""
}
function submitForm(name)
{
    setActionName(name)
    document.getElementById("hhviForm").submit()
    return true
}
function calculateScore()
{
    var health=0;
    var hhHeadship=0
    var educationLevel=0
    var shelterAndHousing=0
    var foodSecurityAndNutrition=0
    var meansOfLivelihood=0
    var hhIncome=0
    if(document.getElementById("hhHeadship1").checked==true)
    hhHeadship=document.getElementById("hhHeadship1").value
    else if(document.getElementById("hhHeadship2").checked==true)
    hhHeadship=document.getElementById("hhHeadship2").value
    else if(document.getElementById("hhHeadship3").checked==true)
    hhHeadship=document.getElementById("hhHeadship3").value
    else if(document.getElementById("hhHeadship4").checked==true)
    hhHeadship=document.getElementById("hhHeadship4").value

    if(document.getElementById("health1").checked==true)
    health=document.getElementById("health1").value
    else if(document.getElementById("health2").checked==true)
    health=document.getElementById("health2").value
    else if(document.getElementById("health3").checked==true)
    health=document.getElementById("health3").value
    else if(document.getElementById("health4").checked==true)
    health=document.getElementById("health4").value

    if(document.getElementById("educationLevel1").checked==true)
    educationLevel=document.getElementById("educationLevel1").value
    else if(document.getElementById("educationLevel2").checked==true)
    educationLevel=document.getElementById("educationLevel2").value
    else if(document.getElementById("educationLevel3").checked==true)
    educationLevel=document.getElementById("educationLevel3").value
    else if(document.getElementById("educationLevel4").checked==true)
    educationLevel=document.getElementById("educationLevel4").value

    if(document.getElementById("shelterAndHousing1").checked==true)
    shelterAndHousing=document.getElementById("shelterAndHousing1").value
    else if(document.getElementById("shelterAndHousing2").checked==true)
    shelterAndHousing=document.getElementById("shelterAndHousing2").value
    else if(document.getElementById("shelterAndHousing3").checked==true)
    shelterAndHousing=document.getElementById("shelterAndHousing3").value
    else if(document.getElementById("shelterAndHousing4").checked==true)
    shelterAndHousing=document.getElementById("shelterAndHousing4").value

    if(document.getElementById("foodSecurityAndNutrition1").checked==true)
    foodSecurityAndNutrition=document.getElementById("foodSecurityAndNutrition1").value
    else if(document.getElementById("foodSecurityAndNutrition2").checked==true)
    foodSecurityAndNutrition=document.getElementById("foodSecurityAndNutrition2").value
    else if(document.getElementById("foodSecurityAndNutrition3").checked==true)
    foodSecurityAndNutrition=document.getElementById("foodSecurityAndNutrition3").value
    else if(document.getElementById("foodSecurityAndNutrition4").checked==true)
    foodSecurityAndNutrition=document.getElementById("foodSecurityAndNutrition4").value

    if(document.getElementById("meansOfLivelihood1").checked==true)
    meansOfLivelihood=document.getElementById("meansOfLivelihood1").value
    else if(document.getElementById("meansOfLivelihood2").checked==true)
    meansOfLivelihood=document.getElementById("meansOfLivelihood2").value
    else if(document.getElementById("meansOfLivelihood3").checked==true)
    meansOfLivelihood=document.getElementById("meansOfLivelihood3").value
    else if(document.getElementById("meansOfLivelihood4").checked==true)
    meansOfLivelihood=document.getElementById("meansOfLivelihood4").value

    if(document.getElementById("hhIncome1").checked==true)
    hhIncome=document.getElementById("hhIncome1").value
    else if(document.getElementById("hhIncome2").checked==true)
    hhIncome=document.getElementById("hhIncome2").value
    else if(document.getElementById("hhIncome3").checked==true)
    hhIncome=document.getElementById("hhIncome3").value
    else if(document.getElementById("hhIncome4").checked==true)
    hhIncome=document.getElementById("hhIncome4").value

    var totalScore=parseInt(hhHeadship)+parseInt(health)+parseInt(educationLevel)+parseInt(shelterAndHousing)+parseInt(protection)+parseInt(foodSecurityAndNutrition)+parseInt(meansOfLivelihood)+parseInt(hhIncome)
    document.getElementById("grade").innerHTML=totalScore
var vulStatus=""
if(totalScore >6 && totalScore <14)
vulStatus="Vunerable"
else if(totalScore >13 && totalScore <21)
vulStatus="More vunerable"
else if(totalScore >20)
vulStatus="Most vulnerable"
document.getElementById("vstatus").innerHTML=vulStatus
}
function setBtnName(name)
{
     if(name=="save")
     {
            setActionName(name)
            return true
     }
     else if(name=="delete")
     {
            if(confirm(" Are you sure you want to "+name+" these records?"))
            {
                    setActionName(name)
                    return true
            }
     }
     else(confirm("Are you sure you want to "+name+" this record?"))
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
</head>

<body onload="MM_preloadImages('images/About_down.jpg','images/Admin_down.jpg','images/Rapid_down.jpg','images/care_down.jpg','images/OVC_down.jpg');">
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
                    <!--onmouseover="setToolTipText('Click this link to get to the enrollment data entry page','30%','30%')" onmouseout="hideComponent('tooltip')"-->
                    <div><jsp:include page="../DataEntryLinkPage.jsp"/></div>
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
              <div><jsp:include page="../ReportLinkPage.jsp"/></div>
            </div></td>
          </tr>


      </table></td>
    <td width="13">&nbsp;</td>
      <td width="739" >

          <div align="center">
			<div style="width:800px; background-color:#FFFFFF">
			<div align="left">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
     <td width="30%" height="20" class="homepagestyle">Household Follow up Assessment form </td>
     </tr>
     <tr><td height="10" colspan="2" align="center"><label style=" font-size: 15px; color: green"><%--${partnername}--%></label> </td></tr>
     
  </table>
    <html:form styleId="hhviForm"  action="/householdFollowupAssessmentAction" method="POST">
    <html:hidden property="stateCode" styleId="stateCode" value="${state.state_code}" />
    <html:hidden property="partnerCode" styleId="partnerCode"/>
    <html:hidden property="hhUniqueId"  styleId="hhUniqueId"/>
    <html:hidden property="recordId"  styleId="recordId"/>
    <html:hidden property="assessmentId"  styleId="assessmentId"/>
            <html:hidden property="actionName" styleId="actionName"/>
            <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
        <!--DWLayoutTable-->
        <tr>
          <td width="8" height="700">&nbsp;</td>
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
                              <td colspan="4" align="center" ><label style=" font-size: 15px; color: red"><logic:present name="withdrawalMsg">${withdrawalMsg}</logic:present></label></td>

                            </tr>
                            <tr>
                              <td colspan="4" align="center"><jsp:include page="../PartnerView.jsp" /></td>

                            </tr>

                              <tr>
                                  <td width="4%"><label>State:</label></td>
                          <td width="30%">
                              <input type="text" id="stateList" name="state" readonly="true" class="bigfieldcellinput" value="${state.name}" />

                          </td>
                              <td width="4%"><label>LGA:</label></td>
                              <td width="30%">


                                       <html:select property="lgaCode" styleClass="bigfieldcellinput" styleId="lgaCode" onchange="submitForm('cboList');">
                                          <logic:iterate name="lgaListInOrgRecords" id="lga">
                                              <html:option value="${lga.lga_code}" >${lga.lga_name}</html:option>
                                          </logic:iterate>
                                      </html:select>


                                      <%--<span id="selected"><input type="text" class="bigfieldcellinput" readonly="readonly" id="LgasSelect" value="${lga.lga_name}"/></span>--%></td>

                            </tr>
                        <tr>
                            <td width="4%" ><label>CBO:</label></td>
                              <td width="30%">
                                  <span id="selected2">
                                      <html:select property="orgCode" styleId="orgCode" styleClass="bigfieldcellinput" style="width:250px;">
                                      <html:option value=""> </html:option>
                                      <logic:iterate id="cbos" name="cboListInOrgRecords">
                                              <html:option value="${cbos.orgCode}">${cbos.orgName} </html:option>
                                          </logic:iterate>
                                      </html:select>


                                  </span></td>
                            <td width="4%"><jsp:include page="../includes/WardName.jsp" /></td>

                            <td width="30%">
                                <html:text property="communityCode" styleId="communityCode" readonly="true" styleClass="bigfieldcellinput"/>
                                    
                              
                             </td>
                            </tr>

                        </table>
                      </td>
                      </tr>
                  </table>



                  </td>
                </tr>


                <tr>
                  <td height="180" valign="top">
                      <br/>
                      <table width="100%" border="0" cellpadding="0" cellspacing="0">
                          <tr><td><html:errors/></td></tr>
                          <tr>
                              <td>
                                 <fieldset>
                        <legend class="fieldset">Household information  </legend>
                          <table width="100%" class="regsitertable"><%--uniqueIdLabel--%>

                              <tr><td align="left" width="110">Serial No. </td><td><html:text property="serialNo"  styleId="serialNo" styleClass="smallfieldcellinput" onkeyup="return constructUniqueId(this.value)" onblur="return submitForm('hhdetails')"/> <label id="uniqueIdLabel" >${hhUniqueId}</label></td>
                                  <td align="right">Date of assessment </td><td><html:text property="dateOfAssessment" styleId="dateOfAssessment" styleClass="smallfieldcellinput" onchange="return submitForm('hhFollowupDetails')" readonly="true"/>(mm/dd/yyyy)</td></tr>
                            <tr><td align="left">Surname </td><td><html:text property="hhSurname" styleId="hhSurname" styleClass="fieldcellinput" onkeyup="capitalizeAll('hhSurname', this.value)" readonly="true"/> </td><td align="right">First name </td><td><html:text property="hhFirstname" styleId="hhFirstname" styleClass="smallfieldcellinput" readonly="true"/> </td></tr>
                            <tr><td align="left">Age </td><td><html:text property="hhAge" styleId="hhAge" styleClass="smallfieldcellinput" style="margin-right: 5px;width:68px;" readonly="true"/> Sex <html:text property="hhGender" styleId="hhGender" styleClass="smallfieldcellinput"/>
                                        </td><td align="right">Number of children (0-17) in household</td><td><html:text property="noOfChildrenInhh" styleId="noOfChildrenInhh" styleClass="smallfieldcellinput"/> </td></tr>
                            <tr><td align="left">Phone No. </td><td><html:text property="phone" styleId="phone" styleClass="fieldcellinput"/></td>
                                <td align="right">Number of people in household </td>
                                <td align="left"><html:text property="noOfPeopleInhh" styleId="noOfPeopleInhh" styleClass="smallfieldcellinput" /> </td></tr>
                            <tr><td align="left">Address </td><td><html:textarea property="address" styleId="address" rows="3" cols="80" styleClass="textareacellinput"></html:textarea></td><td align="right">Occupation</td><td><html:text property="occupation" styleId="occupation" styleClass="fieldcellinput"/></td></tr>
                            
                            

                          </table>
                          </fieldset>
                              </td>
                          </tr>
                    <!--DWLayoutTable-->
                   
                  </table></td>
                </tr>

                <tr>
                  <td height="553" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->

                     <tr>
                      <td height="172" valign="top">



                          <fieldset>
                      <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->



                    <tr>
                      <td valign="top">

                          <fieldset>


                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!--DWLayoutTable-->
                        <tr>
                          <td width="752" > <!--bordercolor="#D7E5F2"-->
                              <table width="100%" border="1" class="regsitertable">

                              <tr>
                                <td width="21%" > Thematic area</td>
                                <td width="72%" align="center" colspan="4">Rating</td>

                              <td width="7%" > Score</td>
                            </tr>

                          </table></td>
                        </tr>
                        <tr>
                          <td width="752" >
                              <table width="100%" border="1"  class="regsitertable">

                              <tr>
                                <td width="21%" > </td>
                                <td width="18%" align="center">1</td>
                              <td width="18%" align="center">2</td>
                              <td width="18%" align="center">3 </td>
                              <td width="18%" align="center">4</td>
                              <td width="7%" > &nbsp;</td>
                            </tr>

                          </table></td>
                        </tr>
                        <tr>
                          <td width="752" >
                              <table width="100%" border="1"  class="regsitertable">

                            <tr>
                                <td width="21%" > Household headship</td>
                                <td width="18%" align="center"><html:radio property="hhHeadship" styleId="hhHeadship1" value="1" onclick="calculateScore()"/><br/>Both parents headed household </td>
                              <td width="18%" align="center"><html:radio property="hhHeadship" styleId="hhHeadship2" value="2" onclick="calculateScore()"/><br/>Single parent headed household</td>
                              <td width="18%" align="center"><html:radio property="hhHeadship" styleId="hhHeadship3" value="3" onclick="calculateScore()"/><br/>Grandparent headed household</td>
                              <td width="18%" align="center"><html:radio property="hhHeadship" styleId="hhHeadship4" value="4" onclick="calculateScore()"/><br/>Child headed household</td>
                              <td width="11%" > &nbsp;</td>
                            </tr>

                          </table></td>
                        </tr>
                        <tr>
                          <td width="752" >
                              <table width="100%" border="1"  class="regsitertable">

                            <tr>
                                <td width="21%" > Health</td>
                                <td width="18%" align="center"><html:radio property="health" styleId="health1" value="1" onclick="calculateScore()"/><br/>No health constraint in household</td>
                              <td width="18%" align="center"><html:radio property="health" styleId="health2" value="2" onclick="calculateScore()"/><br/>Members are occasionally sick with access to healthcare</td>
                              <td width="18%" align="center"><html:radio property="health" styleId="health3" value="3" onclick="calculateScore()"/><br/>One or more members are frequently sick without access to healthcare</td>
                              <td width="18%" align="center"><html:radio property="health" styleId="health4" value="4" onclick="calculateScore()"/><br/>Member(s) of HH is chronically ill and /or living with AIDS/HIV positive</td>
                              <td width="11%" > &nbsp;</td>
                            </tr>

                          </table></td>
                        </tr>
                        <tr>
                          <td width="752" >
                              <table width="100%" border="1"  class="regsitertable">

                            <tr>
                                <td width="21%" > Education Level (Household head)</td>
                                <td width="18%" align="center"><html:radio property="educationLevel" styleId="educationLevel1" value="1" onclick="calculateScore()"/><br/>Tertiary (degree/diploma) </td>
                              <td width="18%" align="center"><html:radio property="educationLevel" styleId="educationLevel2" value="2" onclick="calculateScore()"/><br/>Secondary/trade test/artisan </td>
                              <td width="18%" align="center"><html:radio property="educationLevel" styleId="educationLevel3" value="3" onclick="calculateScore()"/><br/>Primary/vocational skill</td>
                              <td width="18%" align="center"><html:radio property="educationLevel" styleId="educationLevel4" value="4" onclick="calculateScore()"/><br/>No education attained </td>
                              <td width="11%" > &nbsp;</td>
                            </tr>

                          </table></td>
                        </tr>

                        <tr>
                          <td width="752" >
                              <table width="100%" border="1" class="regsitertable">

                            <tr>
                                <td width="21%" > Shelter & Housing</td>
                                <td width="18%" align="center"><html:radio property="shelterAndHousing" styleId="shelterAndHousing1" value="1" onclick="calculateScore()"/><br/>HH has good shelter, not overcrowded </td>
                              <td width="18%" align="center"><html:radio property="shelterAndHousing" styleId="shelterAndHousing2" value="2" onclick="calculateScore()"/><br/>HH has good shelter but overcrowded</td>
                              <td width="18%" align="center"><html:radio property="shelterAndHousing" styleId="shelterAndHousing3" value="3" onclick="calculateScore()"/><br/>HH has structurally defective shelter </td>
                              <td width="18%" align="center"><html:radio property="shelterAndHousing" styleId="shelterAndHousing4" value="4" onclick="calculateScore()"/><br/>HH has no shelter  </td>
                              <td width="11%" > &nbsp;</td>
                            </tr>

                          </table></td>
                        </tr>

                        <tr>
                          <td width="752" >
                              <table width="100%" border="1"  class="regsitertable">

                            <tr>
                                <td width="21%" >Food Security and Nutrition</td>
                                <td width="18%" align="center"><html:radio property="foodSecurityAndNutrition" styleId="foodSecurityAndNutrition1" value="1" onclick="calculateScore()"/><br/>HH has sufficient and regular food all through the year</td>
                              <td width="18%" align="center"><html:radio property="foodSecurityAndNutrition" styleId="foodSecurityAndNutrition2" value="2" onclick="calculateScore()"/><br/>HH has sufficient food but this is not regular</td>
                              <td width="18%" align="center"><html:radio property="foodSecurityAndNutrition" styleId="foodSecurityAndNutrition3" value="3" onclick="calculateScore()"/><br/>HH occasionally has insufficient and/or not regular food</td>
                              <td width="18%" align="center"><html:radio property="foodSecurityAndNutrition" styleId="foodSecurityAndNutrition4" value="4" onclick="calculateScore()"/><br/>HH has no sufficient regular food for most times of the year </td>
                              <td width="11%" > &nbsp;</td>
                            </tr>

                          </table></td>
                        </tr>

                        <tr>
                          <td width="752" >
                              <table width="100%" border="1"  class="regsitertable">

                            <tr>
                                <td width="21%" > Means of livelihood</td>
                                <td width="18%" align="center"><html:radio property="meansOfLivelihood" styleId="meansOfLivelihood1" value="1" onclick="calculateScore()"/><br/>More than one member of HH is employed /or HH owns at least one business or farming livestock</td>
                              <td width="18%" align="center"><html:radio property="meansOfLivelihood" styleId="meansOfLivelihood2" value="2" onclick="calculateScore()"/><br/>At least one member of HH is  employed or has business or farming/livestock</td>
                              <td width="18%" align="center"><html:radio property="meansOfLivelihood" styleId="meansOfLivelihood3" value="3" onclick="calculateScore()"/><br/>No member is employed but HH has business or farming/livestock</td>
                              <td width="18%" align="center"><html:radio property="meansOfLivelihood" styleId="meansOfLivelihood4" value="4" onclick="calculateScore()"/><br/>No member of HH is employed nor have any business nor own farming assets for livelihood </td>
                              <td width="11%" > &nbsp;</td>
                            </tr>

                          </table></td>
                        </tr>

                        <tr>
                          <td width="752" >
                              <table width="100%" border="1"  class="regsitertable">

                            <tr>
                                <td width="21%" > Household income</td>
                                <td width="18%" align="center"><html:radio property="hhIncome" styleId="hhIncome1" value="1" onclick="calculateScore()"/><br/>HH income above N25,000 a month</td>
                              <td width="18%" align="center"><html:radio property="hhIncome" styleId="hhIncome2" value="2" onclick="calculateScore()"/><br/>HH income between N18,000-N25,000 a month</td>
                              <td width="18%" align="center"><html:radio property="hhIncome" styleId="hhIncome3" value="3" onclick="calculateScore()"/><br/>HH income below N18,000 a month</td>
                              <td width="18%" align="center"><html:radio property="hhIncome" styleId="hhIncome4" value="4" onclick="calculateScore()"/><br/>HH has no monthly income earned </td>
                              <td width="11%" > &nbsp;</td>
                            </tr>

                          </table></td>
                        </tr>



                        <tr>
                          <td width="752" >
                              <table width="100%" border="1"  class="regsitertable">
                              <tr>
                                  <td width="100%" colspan="2" align="center">Total score </td>


                            </tr>
                              <tr>
                                <td width="50%"> Vulnerability status </td>
                                <td width="50%" align="center">Grade</td>

                            </tr>
                            <tr>
                                <td width="50%"><label id="vstatus" style=" font-weight: bold">  </label></td>
                                <td width="50%" align="center">  <label id="grade" style=" font-weight: bold">0 </label></td>

                            </tr>

                          </table></td>
                        </tr>
                        
                        <tr>
                                <td height="47" valign="top">
                      <table width="727" border="0" cellpadding="0" cellspacing="0">
                          <tr><td><fieldset>
                        
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="727" height="56" valign="top">
					  <table width="100%" class="regsitertable">
                                              <tr><td width="57%">Household withdrawn from the program?</td><td width="43%"><select class="shortfieldcellinput" id="childWithdrawn" onchange="withdrawnAction(this.value);">
					    <option value=" "></option><option value="No">No</option><option value="Yes">Yes</option></select> </td></tr>
					  </table>
					  <table width="100%" class="regsitertable">
                          <tr>
                            <td width="25%">Tick reason household is withdrawn</td>
                            <td width="3%"><html:radio property="reasonWithdrawal" value="Migrated" styleClass="smallfieldcellselect" styleId="migrated" disabled="true"/></td>
                            <td width="8%">Migrated</td>
                            <td width="3%"><html:radio property="reasonWithdrawal" value="Loss to follow-up" styleClass="smallfieldcellselect" styleId="losttofollowup" disabled="true"/></td>
                            <td width="15%">Loss to follow-up </td>
                            <td width="3%"><html:radio property="reasonWithdrawal" value="Graduated" styleClass="smallfieldcellselect" styleId="graduated" disabled="true"/></td>
                            <td width="23%">Graduated from program</td>
                            <td width="3%"><html:radio property="reasonWithdrawal" value="transfer" styleClass="smallfieldcellselect" styleId="transfered" disabled="true"/></td>
                            <td width="23%">Transfer to other program</td>
                            
                          </tr>
                                            </table></td>
                    </tr>
                  </table>
                  </fieldset></td></tr></table></td>
                </tr>
                       <tr>
                      <td valign="top">
                          <table>
                              <tr><td>&nbsp;&nbsp;Name of Assessor </td><td><html:text property="nameOfAssessor" styleClass="fieldcellinput" styleId="nameOfAssessor" /> </td><td>&nbsp;&nbsp;Designation </td><td><html:text property="designation" styleClass="smallfieldcellinput" styleId="designation" /> </td><td> </td><td> </td></tr>
                          </table>
                      </td>
                      </tr>
                      </table></fieldset>                    </td>
                      </tr>

                      <tr>
                      <td valign="top">

                      </td>
                      </tr>




                  </table>


                 </fieldset>


                      </td>
                      </tr>
                      <script language="javascript">
                              calculateScore()
                      </script>

                    <tr>
                        <td height="40" align="center">
                           <html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${hhfuSaveBtnDisabled}" />
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${hhfuModifyBtnDisabled}"/>
                           <html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${hhfuModifyBtnDisabled}"/>
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
          <td colspan="2" valign="top">
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
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
          <td width="949" height="25" class="copyright" bgcolor="#038233" style=" margin-left: 5px;"><jsp:include page="../includes/Version.jsp"/></td>
        </tr>
    </table></td>
  </tr>
</table>
</body>
</html>

