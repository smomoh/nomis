<%-- 
    Document   : CareAndSupportChecklist
    Created on : Feb 27, 2018, 5:20:36 PM
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
<title>Care and Support checklist </title>
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
                $("#dateOfHivTest").datepicker();
                $("#dateEnrolledOnART").datepicker();
                $("#dateOfViralLoadTest").datepicker();
            });

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
function submitForm(val)
{
    document.getElementById("actionName").value=val
    document.getElementById("dataForm").submit()
}
function setStartUpParameters()
{
    checkCurrentAge()
}
function unselectChkBoxes(chkname)
{
   var elements=document.getElementsByName(chkname)
    for(var i=0; i<elements.length; i++)
    {
        elements[i].checked=false
    }
}
function disableARVSkippingOptions(value)
{
    if(value=='Yes')
    {
        document.getElementById("drugsideeffect").disabled=false
        document.getElementById("stigma").disabled=false
        document.getElementById("dfdailylife").disabled=false
        document.getElementById("hopelessness").disabled=false
        document.getElementById("feelingwell").disabled=false
        document.getElementById("lackoffood").disabled=false
        document.getElementById("relbelief").disabled=false
        document.getElementById("lackofmotivation").disabled=false
    }
    else
    {
        unselectChkBoxes("reasonsPeopleSkipARV")
        document.getElementById("drugsideeffect").disabled="disabled"
        document.getElementById("stigma").disabled="disabled"
        document.getElementById("dfdailylife").disabled="disabled"
        document.getElementById("hopelessness").disabled="disabled"
        document.getElementById("feelingwell").disabled="disabled"
        document.getElementById("lackoffood").disabled="disabled"
        document.getElementById("relbelief").disabled="disabled"
        document.getElementById("lackofmotivation").disabled="disabled"
        document.getElementById("dateEnrolledOnART").disabled="disabled"        
    }
}function disableTestresultControl(value)
{
    if(value !=null && value !="" && value !=" " && value !="  ")
    {
        document.getElementById("receivedTestResult").disabled=false        
    }
    else
    {
        document.getElementById("receivedTestResult").value="" 
        document.getElementById("receivedTestResult").disabled="disabled"        
    }
}
function disableDateEnrolledInCare(value)
{
    if(value=='Yes')
    {
        document.getElementById("dateEnrolledOnART").disabled=false        
    }
    else
    {
        document.getElementById("dateEnrolledOnART").value="" 
        document.getElementById("dateEnrolledOnART").disabled="disabled"        
    }
}
function disableDateOfViralLoad(value)
{
    if(value=='Yes')
    {
        document.getElementById("dateOfViralLoadTest").disabled=false        
    }
    else
    {
        document.getElementById("dateOfViralLoadTest").value="" 
        document.getElementById("dateOfViralLoadTest").disabled="disabled"        
    }
}
function disableAllControls(value)
{
    if(value=='positive' || value=='Yes')
    {
        document.getElementById("hivStatus").disabled=false
        document.getElementById("enrolledOnTreatment").disabled=false
        document.getElementById("dateEnrolledOnART").disabled=false
        document.getElementById("treatmentFacility").disabled=false
        document.getElementById("viralLoadTest").disabled=false
        document.getElementById("dateOfViralLoadTest").disabled=false
        document.getElementById("medicationPickedUp").disabled=false
        document.getElementById("skippedARV").disabled=false
        document.getElementById("transportationSupport").disabled=false
        document.getElementById("saveBtn").disabled=false
        
    }
    else
    {
        if(value=='No')
        document.getElementById("hivStatus").disabled="disabled"
        document.getElementById("enrolledOnTreatment").disabled="disabled"
        document.getElementById("dateEnrolledOnART").disabled="disabled"
        document.getElementById("treatmentFacility").disabled="disabled"
        document.getElementById("viralLoadTest").disabled="disabled"
        document.getElementById("dateOfViralLoadTest").disabled="disabled"
        document.getElementById("medicationPickedUp").disabled="disabled"
        document.getElementById("skippedARV").disabled="disabled"
        document.getElementById("transportationSupport").disabled="disabled"
        document.getElementById("saveBtn").disabled="disabled"
        
    }
}
function activateReferralList(value) 
{
    disableDateEnrolledInCare(value)
    if(value == "Yes") 
    {
        document.getElementById("treatmentFacility").disabled = false;
    }
    else 
    {
            document.getElementById("treatmentFacility").value="select"
            document.getElementById("treatmentFacility").disabled = true;
    }
    
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
                    <div><jsp:include page="../Navigation/CareAndSupportDataEntryLink.jsp"/></div>
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
                    <div><a href="candsreport.do" target="_blank">Care and Support Checklist report </a><a href="gendernormcohortattendancereport.do" target="_blank"> Gender norm cohort attendance report </a><a href="#"> &nbsp; </a><a href="#"> &nbsp; </a><a href="#"> &nbsp; </a><a href="#"> &nbsp; </a><a href="#"> &nbsp; </a></div>
            </div></td>
          </tr>


      </table></td>
    <td width="13">&nbsp;</td>
      <td width="639" >

     <table width="100%" border="0" cellpadding="0" cellspacing="0">
         <tr>
             <td >&nbsp;</td>
    </tr>
    <tr>
	 <td height="30" class="homepagestyle" align="center">CARE & SUPPORT CHECKLIST </td>
	 
    </tr>
    <tr>
	 <td ><html:errors/></td>
    </tr>

  </table>

         <html:form styleId="dataForm" action="/careandsupport" method="post">
<html:hidden property="actionName" styleId="actionName"/>
<html:hidden property="hhUniqueId" styleId="hhUniqueId" />
<html:hidden property="recordId" styleId="recordId"/>
<input type="hidden" name="stateId" id="stateId" value="${state.state_code}" />



  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!--DWLayoutTable-->
                <tr>
                  <td width="762" height="28" valign="top">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    
                    <tr>
                      <td width="762" height="27">
                          <table width="100%">

                              <tr><td width="500"><label id="msg" style="color:red;"> ${ovcWithdrawn}</label></td></tr>
                                        <tr><td colspan="4" align="center"><jsp:include page="../PartnerView.jsp" /></td></tr>

							<tr>
                            <td width="5%"><span style="color:#333">State:</span></td>
                          <td width="30%">
                              <html:text styleId="stateList" property="stateCode" styleClass="bigfieldcellinput" readonly="true" value="${state.name}" />
                          </td>
                              <td width="5%"><span style="color:#333">LGA:</span></td>
                              <td width="30%">
                                  <html:select property="lgaCode" styleClass="bigfieldcellinput" styleId="lgaId" onchange="setActionName('cboList');forms[0].submit()">
                                      <logic:present name="lgaListInOrgRecords">    
                                          <logic:iterate name="lgaListInOrgRecords" id="lga">
                                              <html:option value="${lga.lga_code}" >${lga.lga_name}</html:option>
                                          </logic:iterate>
                                      </logic:present>
                                      </html:select>
                                  </td>
                              </tr>
                              <tr>
                              <td width="5%"><span style="color:#333">CBO:</span></td>
                              <td width="31%">

                              <html:select styleId="orgCode" property="orgCode" styleClass="bigfieldcellinput" >
                              <html:option value=""> </html:option>
                                    <logic:present name="cboListInOrgRecords" >
                                      <logic:iterate id="cbos" name="cboListInOrgRecords" >
                                              <html:option value="${cbos.orgCode}">${cbos.orgName} </html:option>
                                          </logic:iterate>
                                    </logic:present>
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
                            <td><html:text property="hhSerialNo" styleId="hhSerialNo" styleClass="smallfieldcellinput" style="width:50px;" onkeyup="constructUniqueId('orgCode',this.value,5)" onblur="submitForm('hhDetails')"/>

                                <label id="uniqueIdLabel" >${vcHhUniqueId} </label></td><td width="15%" valign="top" class="right">VC Unique Id: </td><td colspan="3"><label id="uniqueIdLabel" style="color:green;" >${vcUniqueId} </label></td>

                        </tr>
                        <tr><td width="15%" valign="top" class="right">OVC Name</td>
                        <td>
                                <html:select property="ovcId" styleClass="fieldcellinput" styleId="ovcId" onchange="submitForm('baselineDetails')" >
                                    <html:option value=""> </html:option>
                                    <logic:present name="ovcListInCsc">
                                        <logic:iterate name="ovcListInCsc" id="ovc">
                                            <html:option value="${ovc.ovcId}">${ovc.firstName} ${ovc.lastName}</html:option>
                                        </logic:iterate>
                                    </logic:present>
                                </html:select>

                        </td><td> </td><td colspan="2"></td><td></td>
                        </tr>
                        
                        <tr>
                          <td valign="top" class="right">Sex: </td>
                              <td>
                                  <html:text property="ovcGender" styleClass="fieldcellinput" styleId="ovcGender" readonly="true" style="width:70px;" />
                     <span style=" margin-left: 32px;">Age:</span>
                     <html:text property="age" styleClass="shortfieldcellinput" styleId="age" readonly="true"  />


                              </td><td>Age unit:</td><td><html:text styleClass="fieldcellinput" property="ageUnit" style="width:82px;" styleId="ageUnit" onchange="activateOedemaAndMUAC()"  />
                     </td><td> </td><td> </td>
                            </tr>

                            <tr>
                            <td valign="top" class="right" >Date of assessment </td>
                            <td colspan="3"><html:text property="dateOfAssessment" styleId="dateOfAssessment" styleClass="smallfieldcellinput" onchange="setActionName('assessmentDetails'); forms[0].submit()" disabled="${doadisabled}"/> &nbsp;(mm/dd/yyyy)
                                  </td>

                                
                            </tr>
                        </table>
                      </fieldset>


					  </td>
                      </tr>
                      
                      
                  </table></td>
                </tr>

                <tr>
                  <td height="123" valign="top"><fieldset>
                          <legend class="fieldset"><b>Tested for HIV and Enrolled in Care</b> </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="17">
                          
                          <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                              <td class="right" colspan="3">When was the beneficiary tested for HIV? </td>
                              <td >
                                  <html:text property="dateOfHivTest" styleId="dateOfHivTest" styleClass="smallfieldcellinput" onchange="disableTestresultControl(this.value)" /> &nbsp;(mm/dd/yyyy) </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="3">Has the beneficiary received his/her HIV result?</td>
                              <td >
                                  <html:select styleClass="fieldcellinput" property="receivedTestResult" style="width:82px;" styleId="receivedTestResult" onchange="disableAllControls(this.value)" disabled="true">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
			<tr>
                              <td class="right" colspan="3">What is the beneficiary HIV status?</td>
                              <td >
                                  <html:select styleClass="fieldcellinput" property="hivStatus" style="width:82px;" styleId="hivStatus" onchange="disableAllControls(this.value)">
                                      <html:option value=""> </html:option><html:option value="Negative">Negative</html:option><html:option value="positive">Positive</html:option> 
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="3">Is the beneficiary currently enrolled in Care and treatment?</td>
                              <td >
                                  <html:select styleClass="fieldcellinput" property="enrolledOnTreatment" style="width:82px;" styleId="enrolledOnTreatment" onchange="activateReferralList(this.value)">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="3">When did this beneficiary commence Anti-retroviral therapy?</td>
                              <td>
                                  <html:text property="dateEnrolledOnART" styleId="dateEnrolledOnART" styleClass="smallfieldcellinput" /> &nbsp;(mm/dd/yyyy)</td>
                          </tr>
                          
                          <tr>
                              <td class="right" colspan="3">Which health facility is beneficiary receiving treatment?</td>
                              <td >
                                  
                                  <html:select property="treatmentFacility" styleClass="fieldcellinput" styleId="treatmentFacility" style="width:300px;" disabled="true">
                                      <html:option value="select">Select</html:option>
                                      <html:optionsCollection property="referralDirectoryList" label="facilityName" value="facilityId"/>
                                  </html:select>
                             </td>
                          </tr>
                          
                          </table>
                          <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                              <td class="right" colspan="3">Has the beneficiary carried out viral load test in the last one year?</td>
                              <td>
                                  <html:select styleClass="fieldcellinput" property="viralLoadTest" style="width:82px;" styleId="viralLoadTest" onchange="disableDateOfViralLoad(this.value)" >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> 
                              </td>
                          </tr>
                          <tr>
                              <td colspan="3">If yes to Q7, when was the viral load test done?&nbsp;(mm/dd/yyyy)</td>
                              <td>
                                  <html:text property="dateOfViralLoadTest" styleId="dateOfViralLoadTest" styleClass="smallfieldcellinput" disabled="true"/>  </td>
                          </tr>

                          <tr>
                              <td class="right" colspan="3">Has beneficiary picked up their medication?</td>
                              <td>
                                  <html:select styleClass="fieldcellinput" property="medicationPickedUp" style="width:82px;" styleId="medicationPickedUp" disabled="${hivStatusAttribute}">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="3">Has beneficiary skipped their ARVs more than six doses in a month in the last 3 months?</td>
                              <td>
                                  <html:select styleClass="fieldcellinput" property="skippedARV" style="width:82px;" styleId="skippedARV" onchange="disableARVSkippingOptions(this.value)" disabled="${hivStatusAttribute}">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>

                          <tr>
                              <td class="right" colspan="4">If yes to Q9, check for reasons why people skip ARV?</td>
                              
                          </tr>
                          
                          
                          
                          <tr>
                  <td height="123" valign="top" colspan="4">
                      <fieldset>
                        <legend class="fieldset">Reasons people skip ARVs </legend>
                        <div style="width:720px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="102">
                          <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              
                              <tr><td>Drug side effect </td> <td><html:multibox property='reasonsPeopleSkipARV' styleId="drugsideeffect" styleClass='smallfieldcellselect' value="Drug side effect" disabled="true"/> </td>
                                      <td>Stigma </td> <td><html:multibox property='reasonsPeopleSkipARV' styleId="stigma" styleClass='smallfieldcellselect' value="Stigma" disabled="true"/> </td>
                                  </tr>
                                  <tr>
                                      <td>Unceasing demand for daily life </td> <td><html:multibox property='reasonsPeopleSkipARV' styleId="dfdailylife" styleClass='smallfieldcellselect' value="Demand for daily life" disabled="true"/> </td>
                                      <td>Feeling of hopelessness </td> <td><html:multibox property='reasonsPeopleSkipARV' styleId="hopelessness" styleClass='smallfieldcellselect' value="Feeling of hopelessness" disabled="true"/> </td>
                                  </tr>
                                  <tr>
                                      <td>Feeling well </td> <td><html:multibox property='reasonsPeopleSkipARV' styleId="feelingwell" styleClass='smallfieldcellselect' value="Feeling well" disabled="true"/> </td>
                                      <td>Lack of food </td> <td><html:multibox property='reasonsPeopleSkipARV' styleId="lackoffood" styleClass='smallfieldcellselect' value="Lack of food" disabled="true"/> </td>
                                  </tr>
                                  <tr>
                                      <td>Religious belief </td> <td><html:multibox property='reasonsPeopleSkipARV' styleId="relbelief" styleClass='smallfieldcellselect' value="Religious belief" disabled="true"/> </td>
                                      <td>Lack of motivation </td> <td><html:multibox property='reasonsPeopleSkipARV' styleId="lackofmotivation" styleClass='smallfieldcellselect' value="Lack of motivation" disabled="true"/> </td>
                                  </tr>
                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
                  </fieldset></td>
                </tr>
                
                
                
                
                          <tr>
                              <td class="right" colspan="3">Has beneficiary received transportation support to access ARVs in the last six months?</td>
                              <td>
                                  <html:select styleClass="fieldcellinput" property="transportationSupport" style="width:82px;" styleId="transportationSupport">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="3">Has beneficiary experienced sores/rash/pain/discharge/bleeding from the vagina or penis?</td>
                              <td>
                                  <html:select styleClass="fieldcellinput" property="childExperiencedSoresOrRash" style="width:82px;" styleId="childExperiencedSoresOrRash">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                        </table>

                        
                    <table><tr><td >Name of Volunteer/Service provider</td><td ><html:text property="volunteerName" styleClass="fieldcellinput" styleId="volunteerName"/></td>
                            <td >Designation</td><td ><html:text property="designation" styleClass="smallfieldcellinput" styleId="designation"/></td>
                        <td >Phone</td><td ><html:text property="providerPhone" styleClass="smallfieldcellinput" styleId="providerPhone"/></td></tr>
                        </table>

                
                       </td>
                      </tr>
                  </table>
                  </fieldset>

                  </td>
                </tr>

                
                <tr>
				<td height="40" align="center">
                            <html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${candsSaveDisabled}"/>
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${candsModifyDisabled}"/>
                           <html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${candsModifyDisabled}"/>

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