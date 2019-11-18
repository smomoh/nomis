<%-- 
    Document   : CaregiverTBHIVScreeningChecklist
    Created on : Jun 27, 2016, 9:37:11 PM
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
<title>Caregiver TB/HIV Care and Support screening checklist </title>
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
    setChangeInWeight()
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
                    <div> <a href="#"> &nbsp; </a><a href="#"> &nbsp; </a><a href="#"> &nbsp; </a><a href="#"> &nbsp; </a><a href="#"> &nbsp; </a><a href="#"> &nbsp; </a></div>
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
	 <td height="30" class="homepagestyle" align="center">Caregiver TB/HIV CARE & SUPPORT SCREENING CHECKLIST </td>
	 
    </tr>
    <tr>
	 <td ><html:errors/></td>
    </tr>

  </table>

         <html:form styleId="dataForm" action="/caregivertbhivScreeningAction" method="post">
<html:hidden property="actionName" styleId="actionName"/>
<html:hidden property="id" styleId="id"/>
<html:hidden property="assessmentNo" styleId="assessmentNo"/>
<html:hidden property="hhUniqueId" styleId="hhUniqueId" />
<input type="hidden" name="stateId" id="stateId" value="${state.state_code}" />



  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!--DWLayoutTable-->
                <tr>
                  <td width="762" height="28" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    
                    <tr>
                      <td width="762" height="27">
                          <table width="100%">

                        		<label style="color:red;">${ovcWithdrawn}</label>
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

                              <html:select styleId="orgCode" property="orgCode" styleClass="bigfieldcellinput" onchange="setActionName('refresh'); forms[0].submit()" >
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
                            <td><html:text property="hhSerialNo" styleId="hhSerialNo" styleClass="smallfieldcellinput" style="width:50px;" onkeyup="constructUniqueId('orgCode',this.value,5)" onblur="submitForm('hhDetails')"/>

                                <label id="uniqueIdLabel" >${vcHhUniqueId} </label></td><td width="15%" valign="top" class="right">Caregiver Id: </td><td colspan="3"><label id="uniqueIdLabel" style="color:green;" >${vcUniqueId} </label></td>

                        </tr>
                        <tr><td width="15%" valign="top" class="right">Caregiver Name</td>
                        <td>
                                <html:select property="caregiverId" styleClass="fieldcellinput" styleId="caregiverId" onchange="submitForm('baselineDetails')" >
                                    <html:option value=""> </html:option>
                                    <logic:iterate name="caregiverListInTbHivForm" id="cgiver">
                                        <html:option value="${cgiver.caregiverId}">${cgiver.caregiverFullName}</html:option>
                                    </logic:iterate>
                                </html:select>

                        </td><td> </td><td colspan="2"></td><td></td>
                        </tr>
                        
                        <tr>
                          <td valign="top" class="right">Sex: </td>
                              <td>
                                  <html:text property="gender" styleClass="fieldcellinput" styleId="gender" readonly="true" style="width:70px;" />
                     <span style=" margin-left: 32px;">Age:</span>
                     <html:text property="age" styleClass="shortfieldcellinput" styleId="age" readonly="true" onchange="activateOedemaAndMUAC()"  />


                              </td><td> </td><td>
                     </td><td> </td><td> </td>
                            </tr>

                            <tr>
                            <td valign="top" class="right" >Date of assessment </td>
                            <td colspan="3"><html:text property="dateOfAssessment" styleId="dateOfAssessment" styleClass="smallfieldcellinput" onchange="setActionName('assessmentDetails'); forms[0].submit()" readonly="true"/> &nbsp;(mm/dd/yyyy)
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
                          <legend class="fieldset"><b>SECTION A: TB SYMPTOMS ASSESSMENT</b> </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="17">
                          <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">

			
                        </table>
                          <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                              <td class="right" colspan="2">Has the caregiver been coughing for up to two weeks or more? </td>
                              <td >
                                  <html:select styleClass="fieldcellinput" property="caregiverCoughing" style="width:82px;" styleId="caregiverCoughing" >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option> 
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">Has the caregiver been losing weight recently or is not growing properly?</td>
                              <td >
                                  <html:select styleClass="fieldcellinput" property="caregiverLossindWeight" style="width:82px;" styleId="caregiverLossindWeight" >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
			<tr>
                              <td class="right" colspan="2">Has the caregiver been having fever for a long period of time?</td>
                              <td >
                                  <html:select styleClass="fieldcellinput" property="caregiverHavingFever" style="width:82px;" styleId="caregiverHavingFever" >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option> 
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">Has the caregiver been having night sweats?</td>
                              <td >
                                  <html:select styleClass="fieldcellinput" property="caregiverHavingNightSweat" style="width:82px;" styleId="caregiverHavingNightSweat" disabled="${disableFoodSecurityAndDietQ4ToQ7}" >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">Does the caregiver have swellings on the neck?</td>
                              <td>
                                  <html:select styleClass="fieldcellinput" property="caregiverSwellingOnNeck" style="width:82px;" styleId="foodSecurityAndDietQ5" disabled="${disableFoodSecurityAndDietQ4ToQ7}">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          
                          <tr>
                              <td class="right" colspan="2">Is there any adult member of the household or family that has any of the above symptoms or has been treated for TB in the past two years?</td><td >
                                  <html:select styleClass="fieldcellinput" property="familyMemberHadTB" style="width:82px;" styleId="familyMemberHadTB" disabled="${disableFoodSecurityAndDietQ4ToQ7}">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="3"><b>SECTION B: POSITIVE HEALTH DIGNITY & PREVENTION (PHDP) SUPPORT 
                                (Fill this section for HIV positive beneficiaries ONLY)</b></td>
                          </tr>
                          </table>
                          <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                            <td class="right" colspan="4"><b>Prevent HIV Transmission:</b> </td>
                            

                          </tr>
                          <tr>
                              <td class="right" colspan="3">How many doses of ARV has the caregiver missed in the past one week?</td>
                              <td align="right">
                                  <html:select styleClass="fieldcellinput" property="dosesOfARVMissedByCaregiver" style="width:82px;" styleId="dosesOfARVMissedByCaregiver" disabled="${cgiverhivStatusAttribute}">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="3">If the caregiver is HIV-positive and pregnant, is she enrolled into PMTCT?</td>
                              <td align="right">
                                  <html:select styleClass="fieldcellinput" property="caregiverEnrolledInPMTCT" style="width:82px;" styleId="caregiverEnrolledInPMTCT" disabled="${cgiverhivStatusAttribute}">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>

                          <tr>
                              <td class="right" colspan="3">Has HIV-exposed infant been provided with EID testing?</td>
                              <td align="right">
                                  <html:select styleClass="fieldcellinput" property="eidTestingDoneForChild" style="width:82px;" styleId="eidTestingDoneForChild" disabled="${cgiverhivStatusAttribute}">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="3">Is the HIV-exposed infant on ‘Septrin’?</td>
                              <td align="right">
                                  <html:select styleClass="fieldcellinput" property="caregiverOnSeptrin" style="width:82px;" styleId="caregiverOnSeptrin" disabled="${cgiverhivStatusAttribute}">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>

                          <tr>
                              <td class="right" colspan="3">If sexually active, has the beneficiary disclosed his/her HIV status to the sexual partner?</td>
                              <td align="right">
                                  <html:select styleClass="fieldcellinput" property="hivStatusDisclosedToPartner" style="width:82px;" styleId="hivStatusDisclosedToPartner" disabled="${cgiverhivStatusAttribute}">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="3">If sexually active, does the beneficiary know the HIV status of the sexual partner?</td>
                              <td align="right">
                                  <html:select styleClass="fieldcellinput" property="hivStatusOfPartnerKnown" style="width:82px;" styleId="hivStatusOfPartnerKnown" disabled="${cgiverhivStatusAttribute}">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                        </table>

                        <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                            <td  colspan="4" class="right"><b>Prevent Diseases: </b></td>
                          </tr>
                          <tr>
                            <td colspan="3">How many doses of ‘Septrin’ has the caregiver missed in the past one week?</td>
                            <td align="right"><html:select styleClass="fieldcellinput" property="dosesOfSeptrinMissedByCaregiver" style="width:82px;" styleId="dosesOfSeptrinMissedByCaregiver" >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select></td>
                            
                          </tr>
                          <tr>
                            <td colspan="3">If HIV-exposed infant is >6 weeks, has he/she been started on ‘Septrin’?</td>
                            <td align="right"><html:select styleClass="fieldcellinput" property="childAboveSixWksAndStartedOnSeptrin" style="width:82px;" styleId="caregiverAboveSixWksAndStartedOnSeptrin" >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select>
                            </td>
                            
                          </tr>
                          <tr>
                            <td colspan="3">Does the caregiver sleep under insecticide-treated nets every night?</td>
                            <td align="right"><html:select styleClass="fieldcellinput" property="caregiverSleepInTreatedNet" style="width:82px;" styleId="caregiverSleepInTreatedNet" >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select></td>
                            
                          </tr>
                          <tr>
                              <td colspan="3" >Does the beneficiary have sores/rash/pain/discharge/bleeding from the vagina or penis?</td>
                            <td align="right"><html:select styleClass="fieldcellinput" property="beneficiariesHasSoresOrBleeding" style="width:82px;" styleId="beneficiariesHasSoresOrBleeding" >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select></td>
                            
                          </tr>
                          <tr>
                              <td colspan="4" ><b>Promote Healthy Living</b></td>
                            
                          </tr>
                          <tr>
                              <td colspan="3" >How many PLHIV support group meetings has the caregiver attended in recent 3 months?</td>
                            <td align="right"><html:text styleClass="fieldcellinput" property="numberOfClubsAttndedInThreeMths" style="width:82px;" styleId="numberOfClubsAttndedInThreeMths"/>
                                      </td>
                            
                          </tr>
                          <tr>
                    <td><table><tr><td >Name of Volunteer/Service provider</td><td ><html:text property="volunteerName" styleClass="fieldcellinput" styleId="volunteerName"/></td>
                            <td >Designation</td><td ><html:text property="designation" styleClass="fieldcellinput" styleId="designation"/></td></tr></table></td>

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
                            <html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${caregivertbhivSaveDisabled}"/>
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${caregivertbhivModifyDisabled}"/><html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${caregivertbhivModifyDisabled}"/>

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
