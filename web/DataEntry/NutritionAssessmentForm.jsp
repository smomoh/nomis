<%-- 
    Document   : NutritionAssessmentForm
    Created on : Dec 18, 2014, 10:16:38 PM
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
<title>Nutrition assessment form </title>
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

<script language="javascript">
var msg=" "
			$(function() {
                $("#dateOfAssessment").datepicker();
                $("#dateOfLastWeight").datepicker();
                
            });


function setChangeInWeight()
{
    var weight=document.getElementById("weight").value
    var lastWeight=document.getElementById("lastWeight").value
    if((parseInt(weight) < parseInt(lastWeight)))
    {
        document.getElementById("changeInWeight").value="Loss"
    }
    else if((parseInt(weight) == parseInt(lastWeight)))
    {
        document.getElementById("changeInWeight").value="Stationary"
    }
    else if((parseInt(weight) > parseInt(lastWeight)))
    {
        document.getElementById("changeInWeight").value="Gain"
    }
    else
    {
        document.getElementById("changeInWeight").value=""
    }
}
function activateBMIAndNutritionalStatus()
{
    var age=document.getElementById("ovcAge").value
    var ageUnit=document.getElementById("ageUnit").value
    if((ageUnit=="Year" && parseInt(age) >4))
    {
        enableControl("bmi")
        enableControl("nutritionalStatus")
    }
    else
    {
        disableControl("bmi")
        disableControl("nutritionalStatus")
    }
}
function activateOedemaAndMUAC()
{
    var age=document.getElementById("ovcAge").value
    var ageUnit=document.getElementById("ageUnit").value
    if(((ageUnit=="month" || ageUnit=="Month") && parseInt(age) >5)|| (ageUnit=="Year" && parseInt(age) <5))
    {
        enableControl("oedma")
        enableControl("muac")
    }
    else
    {
        disableControl("oedema")
        disableControl("muac")
    }
}
function activateFoodSecurityAndDietQ4ToQ7()
{
    var age=document.getElementById("ovcAge").value
    var ageUnit=document.getElementById("ageUnit").value
    if(((ageUnit=="month" || ageUnit=="Month") && parseInt(age) >5)|| (ageUnit=="Year"))
    {
        enableControl("foodSecurityAndDietQ4")
        enableControl("foodSecurityAndDietQ5")
        enableControl("foodSecurityAndDietQ6")
        enableControl("foodSecurityAndDietQ7")
    }
    else
    {
        disableControl("foodSecurityAndDietQ4")
        disableControl("foodSecurityAndDietQ5")
        disableControl("foodSecurityAndDietQ6")
        disableControl("foodSecurityAndDietQ7")
    }
}
function activateFoodSecurityAndDietQ8AndQ9()
{
    var age=document.getElementById("ovcAge").value
    var ageUnit=document.getElementById("ageUnit").value
    if(((ageUnit=="month" || ageUnit=="Month") && parseInt(age) <6))
    {
        enableControl("foodSecurityAndDietQ8")
        enableControl("foodSecurityAndDietQ9")
    }
    else
    {
        disableControl("foodSecurityAndDietQ8")
        disableControl("foodSecurityAndDietQ9")
    }
}
function tokenizeStr(str){

    var tokenArray = str.split(", ");
    return tokenArray;
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



function setActionName(val)
{
    document.getElementById("actionName").value=val
}
function makeSno(sNo)
{
        if(sNo=="" || sNo==" ")
        {
            sNo=document.getElementById("ovcSno").value
            document.getElementById("ovcId").value=" "
            if(sNo=="" || sNo==" ")
            {
                document.getElementById("ovcId").value=" "
                return false
            }
        }
        else if(isNaN(sNo))
        {
            alert("Serial number must be numeric")
            return false
        }
        else if(sNo.length>5)
        {
            alert("Serial number cannot be greater than 5 digits")
            document.getElementById("ovcId").value=" "
            return false
        }
        if(sNo.length==1)
            sNo="0000"+sNo
        else if(sNo.length==2)
            sNo="000"+sNo
        else if(sNo.length==3)
            sNo="00"+sNo
        else if(sNo.length==4)
            sNo="0"+sNo
        else if(sNo.length==5)
            sNo=sNo
        return sNo
    }
   function getDropDownValue(id)
    {
      var dropDownValue=document.getElementById(id).value
      return dropDownValue;
    }
    function trim(stringToTrim)
    {
           return stringToTrim.replace(/^\s+|\s+$/g,"");
    }
    function enableControl(id)
    {
        document.getElementById(id).disabled=false
    }
    function disableControl(id)
    {
        document.getElementById(id).disabled="disabled"
    }

    function checkCurrentAge()
    {
        var ageStatus="${currentAge}"

        var ageStatusArray=ageStatus.split(";")
        if(ageStatusArray[0]=="overaged")
        alert("This Ovc is above 17 years old. Current age is "+ageStatusArray[1])
    }

 function constructUniqueId(orgId,SNo,maxDigit)
{
    var orgCode=document.getElementById(orgId).value
    if(orgCode==null || orgCode.indexOf("/")==-1)
    {
       clearUniqueSNoField("hhSerialNo")
       alert("Select organization/CSO")
       return false
    }
    else
    orgCode=orgCode.substring(orgCode.lastIndexOf("/")+1)
    var paddedSNo=padSerialNo(SNo,maxDigit,"hhSerialNo")
    if(paddedSNo==0)
    {
        paddedSNo=" "
    }
    var uniqueId=document.getElementById("stateId").value+"/"+document.getElementById("lgaId").value+"/"+orgCode+"/"+paddedSNo
    document.getElementById("hhUniqueId").value=uniqueId
    document.getElementById("uniqueIdLabel").innerHTML=uniqueId
    //alert(uniqueId)
    return true
}
function padSerialNo(SNo,maxDigit,id)
{
    var numberLength=SNo.length
    var num=validateSNo(SNo,maxDigit)
    if(num==0)
    {
        clearUniqueSNoField(id)
        SNo=0
        showMsg(msg)
    }
    else
    {
        while(numberLength<maxDigit)
        {
            SNo="0"+SNo
            numberLength++
        }
    }
    return SNo
}
function validateSNo(SNo,maxDigit)
{
    var numberLength=SNo.length

    if(SNo=="" || SNo==" ")
    {
        msg=""
        return 0
    }
    else if(isNaN(SNo))
    {
        msg="Serial number must be numeric"
        return 0
    }
    else if(SNo <1)
    {
        msg="Serial number must be greater than 0"
        return 0
    }
    else if(numberLength > maxDigit)
    {
        msg="Serial number must not be more than "+maxDigit+" digits"
        return 0
    }
    return 1
}
function clearUniqueSNoField(id)
{
    document.getElementById(id).value=""
}
function submitForm(val,callerValue,maxDigit)
{
    document.getElementById("actionName").value=val
    document.getElementById("dataForm").submit()
}
function setStartUpParameters()
{
    checkCurrentAge()
    setChangeInWeight()
}
function calculateBMI()
{
    var height=(parseInt(document.getElementById("height").value)/100);
    var weight=parseInt(document.getElementById("weight").value)
    var bmi=(weight/(height*height))
    
    //alert(height+" "+weight)
    document.getElementById("bmi").value=bmi
}
function activateFacilityList(value) 
{
    if(value == "red") 
    {
        document.getElementById("muacFacility").disabled = false;
    }
    else 
    {
        document.getElementById("muacFacility").value="select"
        document.getElementById("muacFacility").disabled = true;
    }
}
</script>
<link href="kidmap-default.css" rel="stylesheet" type="text/css" />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
</head>

<body onload="MM_preloadImages('images/About_down.jpg','images/Admin_down.jpg','images/Rapid_down.jpg','images/care_down.jpg','images/OVC_down.jpg'); setStartUpParameters()">
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
                    <div><jsp:include page="../DataEntryLinkPage.jsp"/><%--<a href="householdVulnerabilityIndexAction.do">Household Vulnerability Assessment Form</a><a href="Add.do">Enrolment card </a> <a href="householdServiceAction.do">Household service form</a><a href="OvcService.do">Ovc Service form</a><a href="ovcreferral.do">Referral form</a><a href="followupsurvey.do">Follow up CSI form</a>--%></div>
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
                    <div><jsp:include page="../ServiceReportLinkPage.jsp"/> <a href="#"> &nbsp; </a><a href="#"> &nbsp; </a><a href="#"> &nbsp; </a></div>
            </div></td>
          </tr>


      </table></td>
    <td width="13">&nbsp;</td>
      <td width="639" >

     <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
	 <td width="30%" height="39" class="homepagestyle">Nutrition assessment form </td>
	 <td width="70%"><html:errors/></td>
    </tr>

  </table>

         <html:form styleId="dataForm" action="/nutritionAssessmentAction" method="post">
<html:hidden property="actionName" styleId="actionName"/>
<html:hidden property="id" styleId="id"/>
<html:hidden property="assessmentNo" styleId="assessmentNo"/>
<html:hidden property="hhUniqueId" styleId="hhUniqueId" />
<input type="hidden" name="stateId" id="stateId" value="${state.state_code}" />



  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!--DWLayoutTable-->
                <tr>
                  <td width="762" height="28" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="27">
                          <table width="100%">

                        		<label style="color:red">${ovcWithdrawn}</label>
                                        <tr><td colspan="4" align="center"><jsp:include page="../PartnerView.jsp" /></td></tr>

							<tr>
                            <td width="5%"><span style="color:#333">State:</span></td>
                          <td width="30%">
                              <html:text styleId="stateList" property="stateCode" styleClass="bigfieldcellinput" readonly="true" value="${state.name}" />
                          </td>
                              <td width="5%"><span style="color:#333">LGA:</span></td>
                              <td width="30%">
                                  <html:select property="lgaCode" styleClass="bigfieldcellinput" styleId="lgaId" onchange="setActionName('cboList');forms[0].submit()">
                                          <logic:iterate name="lgaListInOrgRecords" id="lga">
                                              <html:option value="${lga.lga_code}" >${lga.lga_name}</html:option>
                                          </logic:iterate>
                                      </html:select>
                                  </td>
                              </tr>
                              <tr>
                              <td width="5%"><span style="color:#333">CBO:</span></td>
                              <td width="31%">

                              <html:select styleId="orgCode" property="orgCode" styleClass="bigfieldcellinput" onchange="setActionName('cbo'); forms[0].submit()" >
                              <html:option value=""> </html:option>
                                      <logic:iterate id="cbos" name="cboListInOrgRecords" >
                                              <html:option value="${cbos.orgCode}">${cbos.orgName} </html:option>
                                          </logic:iterate>
               
                              </html:select>
                              </td>
                              <td width="5%"><span style="color:#333"><jsp:include page="../includes/WardName.jsp" /></span></td>
                              <td width="21%">
                                  <html:text property="communityCode" styleClass="bigfieldcellinput" styleId="communityCode" readonly="true"/>
                                  </td>
                        </tr>


                        </table></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="120" valign="top">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" valign="top">

					  <fieldset>
                        <legend class="fieldset">Personal Information  </legend>
                        <table width="100%" class="regsitertable">
                        <tr><td width="15%" valign="top" class="right">HH Unique SNo</td>
                            <td><html:text property="hhSerialNo" styleId="hhSerialNo" styleClass="smallfieldcellinput" style="width:50px;" onkeyup="constructUniqueId('orgCode',this.value,5)" onblur="submitForm('hhDetails',this.value,4)"/>

                                <label id="uniqueIdLabel" >${vcHhUniqueId} </label></td><td width="15%" valign="top" class="right">VC Unique Id: </td><td colspan="3"><label id="uniqueIdLabel" style="color:green;" >${vcUniqueId} </label></td>

                        </tr>
                        <tr><td width="15%" valign="top" class="right">VC Name</td>
                        <td>
                                <html:select property="ovcId" styleClass="fieldcellinput" styleId="ovcId" onchange="submitForm('baselineDetails',this.value,5)" >
                                    <html:option value=""> </html:option>
                                    <logic:present name="ovcListInNutritionAssessment">
                                    <logic:iterate name="ovcListInNutritionAssessment" id="ovc">
                                        <html:option value="${ovc.ovcId}">${ovc.firstName} ${ovc.lastName}</html:option>
                                    </logic:iterate>
                                    </logic:present>
                                </html:select>

                        </td><td>Enrollment date:</td><td colspan="2"><html:text property="dateOfEnrollment" styleId="dateEnrollment" styleClass="smallfieldcellinput" readonly="true" />&nbsp;(mm/dd/yyyy)</td><td></td>
                        </tr>
                        
                        <tr>
                          <td valign="top" class="right">Sex: </td>
                              <td>
                                  <html:text property="ovcGender" styleClass="fieldcellinput" styleId="ovcGender" readonly="true" style="width:70px;" />
                     <span style=" margin-left: 3px;">Current age:</span>
                     <html:text property="ovcAge" styleClass="shortfieldcellinput" styleId="ovcAge" readonly="true" onchange="activateOedemaAndMUAC()"  />


                              </td><td>Age unit:</td><td><html:text styleClass="fieldcellinput" property="ageUnit" style="width:82px;" styleId="ageUnit" onchange="activateOedemaAndMUAC()"  />
                     </td><td> </td><td> </td>
                            </tr>

                            
                        </table>
                      </fieldset>


					  </td>
                      </tr>
                      
                      <tr>
                          <td width="762" height="130">

                              <fieldset><legend>Anthropometric</legend>
                              <table width="100%" border="1" bordercolor="#D7E5F2" class="regsitertable">
                            <tr>
                            <td valign="top" class="right" >Date of assessment </td>
                            <td colspan="3"><html:text property="dateOfAssessment" styleId="dateOfAssessment" styleClass="smallfieldcellinput" onchange="setActionName('assessmentDetails'); forms[0].submit()" /> &nbsp;(mm/dd/yyyy)
                                  </td>

                                
                            </tr>
                           <tr>
                            <td valign="top" class="right" >Weight(Kg): </td>
                            <td><html:text property="weight" styleClass="shortfieldcellinput" styleId="weight" style="margin-right:28px;" onblur="calculateBMI();setChangeInWeight()"/>
                                Height(cm): <html:text property="height" styleClass="shortfieldcellinput" styleId="height" onblur="calculateBMI()" /></td>

                                <td align="right">Last Weight</td>
                                <td ><html:text styleClass="smallfieldcellinput" property="lastWeight" styleId="lastWeight"  /></td>
                            </tr>
                           <tr>
                            <td valign="top" class="right" >Change in weight</td>
                              <td><html:select property="changeInWeight" styleClass="fieldcellinput" styleId="changeInWeight">
                                    <html:option value=""> </html:option><html:option value="Gain">Gain</html:option><html:option value="Stationary">Stationary</html:option>
                                    <html:option value="Loss">Loss</html:option>
                                  </html:select></td>
                              <td align="right">Date of Last weight </td><td ><html:text styleClass="fieldcellinput" property="dateOfLastWeight"  styleId="dateOfLastWeight"/> </td>

                                
                            </tr>
                            <tr>
                            <td valign="top" class="right" >Oedema </td>
                            <td><html:select styleClass="fieldcellinput" property="oedema"  styleId="oedema" disabled="${disableOedemaAndMUAC}" >
                                      <html:option value=""> </html:option><html:option value="no">No</html:option><html:option value="yes">Yes</html:option>
                                  </html:select>
                                  </td>
                                  <td align="right">Muac (6mths to 4 yrs)</td><td><html:select styleClass="fieldcellinput" property="muac" styleId="muac" onchange="activateFacilityList(this.value)" disabled="${disableOedemaAndMUAC}" >
                                      <html:option value=""> </html:option><html:option value="red">Red</html:option><html:option value="yellow">Yellow</html:option>
                                      <html:option value="green">Green</html:option>
                                  </html:select> </td>

                              
                            </tr>
                            <tr><%--${disableBMIAndNutritionalStatus}calculateBMI()--%>
                            <td valign="top" class="right" >BMI (Above 4 yrs)</td>
                            <td><html:text styleClass="fieldcellinput" property="bmi" styleId="bmi" disabled="true" />

                                  </td>
                                  <td align="right">Nutritional Status </td><td ><html:select styleClass="fieldcellinput" property="nutritionalStatus" styleId="nutritionalStatus" disabled="${disableBMIAndNutritionalStatus}" >
                                      <html:option value=""> </html:option><html:option value="Obesse">Obesse</html:option><html:option value="Over Weight">Over Weight</html:option>
                                      <html:option value="Normal">Normal</html:option>
                                      <html:option value="Mild malnutrition">Mild malnutrition</html:option><html:option value="Moderate malnutrition">Moderate malnutrition</html:option>
                                      <html:option value="Severe malnutrition">Severe malnutrition</html:option>
                                  </html:select> </td>

                              
                            </tr>

                              </table>
                              </fieldset>
                          </td>
                      </tr>
                  </table></td>
                </tr>

                <tr>
                  <td height="123" valign="top"><fieldset>
                        <legend class="fieldset"> </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="17">
                          
                          <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                              <td class="right" colspan="2">In the last 30 days, was there ever no food to eat in the household? </td>
                              <td >
                                  <html:select styleClass="fieldcellinput" property="foodSecurityAndDietQ1" style="width:82px;" styleId="foodSecurityAndDietQ1" >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Rarely">Rarely</html:option>
                                      <html:option value="Sometimes">Sometimes</html:option>
                                      <html:option value="Often">Often</html:option>
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">In the last 30 days did any household member go to sleep hungry because there wasn’t any food?</td>
                              <td >
                                  <html:select styleClass="fieldcellinput" property="foodSecurityAndDietQ2" style="width:82px;" styleId="foodSecurityAndDietQ2" >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Rarely">Rarely</html:option>
                                      <html:option value="Sometimes">Sometimes</html:option>
                                      <html:option value="Often">Often</html:option>
                                  </html:select> </td>
                          </tr>
			<tr>
                              <td class="right" colspan="2">In the last 30 days, did any household member go a whole day and night without eating anything because there was not enough food?</td>
                              <td >
                                  <html:select styleClass="fieldcellinput" property="foodSecurityAndDietQ3" style="width:82px;" styleId="foodSecurityAndDietQ3" >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Rarely">Rarely</html:option>
                                      <html:option value="Sometimes">Sometimes</html:option>
                                      <html:option value="Often">Often</html:option>
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">Did the child receive any food or liquid besides breast milk in the last 24 hours?</td><td >
                                  <html:select styleClass="fieldcellinput" property="foodSecurityAndDietQ8" style="width:82px;" styleId="foodSecurityAndDietQ8" disabled="${disableFoodSecurityAndDietQ8AndQ9}">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">Is the mother experiencing any difficulties with breastfeeding?</td><td >
                                  <html:select styleClass="fieldcellinput" property="foodSecurityAndDietQ9" style="width:82px;" styleId="foodSecurityAndDietQ9" disabled="${disableFoodSecurityAndDietQ8AndQ9}">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">How many times did the child eat yesterday?</td>
                              <td >
                                  <html:select styleClass="fieldcellinput" property="foodSecurityAndDietQ4" style="width:82px;" styleId="foodSecurityAndDietQ4" disabled="${disableFoodSecurityAndDietQ4ToQ7}" >
                                      <html:option value=""> </html:option><html:option value="0">0</html:option>
                                      <html:option value="1">1</html:option>
                                      <html:option value="2">2</html:option>
                                      <html:option value="3">3</html:option>
                                      <html:option value="4 or more">4 or more</html:option>
                                    
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">Yesterday, did the child eat any vitamin A rich foods (for example: mango, carrots, papaya, red palm oil, zogali, ugu, cassava, liver, or kidney)?</td>
                              <td>
                                  <html:select styleClass="fieldcellinput" property="foodSecurityAndDietQ5" style="width:82px;" styleId="foodSecurityAndDietQ5" disabled="${disableFoodSecurityAndDietQ4ToQ7}">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          
                          <tr>
                              <td class="right" colspan="2">Yesterday, did the child eat any Iron-rich foods (for example: liver, kidney, beans, groundnut, or dark green leaves such as spinach, zogali, ugu, cassava)?</td><td >
                                  <html:select styleClass="fieldcellinput" property="foodSecurityAndDietQ6" style="width:82px;" styleId="foodSecurityAndDietQ6" disabled="${disableFoodSecurityAndDietQ4ToQ7}">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">Yesterday, did the child eat any protein foods (for example: meat, eggs, fish, beans, groundnut, milk, cheese, soya, or Tom Brown)?</td>
                              <td >
                                  <html:select styleClass="fieldcellinput" property="foodSecurityAndDietQ7" style="width:82px;" styleId="foodSecurityAndDietQ7" disabled="${disableFoodSecurityAndDietQ4ToQ7}">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          
                          <tr>
                              <td class="right" colspan="2">Does the household have soap and water to wash dishes and utensils?</td><td >
                                  <html:select  property="hygieneQ1" styleClass="fieldcellinput" style="width:82px;" styleId="hygieneQ1" >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>

                          <tr>
                              <td class="right" colspan="2">Does the household have soap or ash for hand washing?</td><td >
                                  <html:select styleClass="fieldcellinput" property="hygieneQ2" style="width:82px;" styleId="hygieneQ2" >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">Do you normally wash your hands with soap/ash before cooking/eating?</td><td >
                                  <html:select styleClass="fieldcellinput" property="hygieneQ3" style="width:82px;" styleId="hygieneQ3" >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>

                          <tr>
                              <td class="right" colspan="2">Do you normally wash your hands with soap/ash after the toilet?</td><td >
                                  <html:select styleClass="fieldcellinput" property="hygieneQ4" style="width:82px;" styleId="hygieneQ4" >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                        </table>

                        
                       </td>
                      </tr>
                      <tr>
                          <td>
                            <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                            <td width="21%" class="right">Referral: </td>
                            <td colspan="3"> </td>

                          </tr>
                          <tr>
                              <td colspan="4" ><b>If MUAC is red, refer to facility</b></td>
                            
                          </tr>
                          <tr>
                            <td >Facility </td>
                            <td colspan="3"><html:select property="muacFacility" styleClass="fieldcellinput" styleId="muacFacility" style="width:510px;" disabled="true">
                                      <html:option value="select">Select</html:option>
                                      <html:optionsCollection property="referralDirectoryList" label="facilityName" value="facilityId"/>
                                  </html:select></td>
                            
                          </tr>
                          <tr>
                              <td colspan="4" ><b>If MUAC is yellow, provide...</b></td>
                            
                          </tr>
                          <tr>
                              <td align="bottom">Nutrition education<html:multibox property="yellowMuacServices" styleId="nut_edu" style="border-align: bottom;" value="nut_edu"/></td>
                            <td>
                                Nutrition counselling <html:multibox property="yellowMuacServices" styleId="nut_counsel" style="border-align: bottom"  value="nut_counsel"/>
                            </td>
                            <td >Nutrition support <html:multibox property="yellowMuacServices" styleId="nut_support" value="nut_support"/></td>
                            <td>
                                HIV Risk assessment <html:multibox property="yellowMuacServices" styleId="hivRiskAss" value="hivRiskAss"/>
                            </td>
                          </tr>
                                                                           
                        </table>
                          </td>
                      </tr>
                  </table>
                  </fieldset>

                  </td>
                </tr>

                
                <tr>
				<td height="40" align="center">
                            <html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${nutritionSaveDisabled}"/>
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${nutritionModifyDisabled}"/><html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${nutritionModifyDisabled}"/>

                        </td>
				</tr>

                <tr>
				<td height="30">&nbsp;</td>
				</tr>



            </table>


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
  <div id="pop" class="search" style="width:210px;">
    <table><tr><td style="width:208px;"><label id="title" style="color:#FFFFFF; width:198px;">Browse</label></td><td><img name="popClose" src="images/close.jpg" style="width:10px; height:10px;" onClick="hideComponent('pop')"/></td></tr>
        <tr><td colspan="2" align="left"><span><input type="text" name="selectedName" style="width:195px;" style="margin-top:0px;" onkeyup="filterNames(this.value)"/></span></td></tr>
        <tr><td colspan="2"><span id="ovcNames"> </span></td></tr>
    </table>
  </div>
</body>
</html>
