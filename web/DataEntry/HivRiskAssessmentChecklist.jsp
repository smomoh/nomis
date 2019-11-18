<%-- 
    Document   : HivRiskAssessmentChecklist
    Created on : Aug 20, 2017, 10:31:55 PM
    Author     : smomoh
--%>
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
<title>HIV Risk assessment checklist </title>
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
			$(function() {
                $("#dateOfAssessment").datepicker();
            });
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
    document.getElementById("hhSerialNo").value=""
}
function submitForm(requiredAction)
{
    var SNumber=document.getElementById("hhSerialNo").value
    if(SNumber==null || SNumber=="" || SNumber==" " || SNumber=="  " || SNumber=="   " || SNumber.length < 1)
    return false
    else
    {
        setActionName(requiredAction)
        document.getElementById("hhFormId").submit()
    } 
    return true
}
function setBtnName(name)
{
     if(name=="save")
     {
            setActionName(name)
            return checkForGraduation()
     }
     if(confirm("Are you sure you want to "+name+" this record?"))
     {
            setActionName(name)
            if(name=='modify')
            return checkForGraduation()
            else
            return true
     }
       return false
}
function setControlsForChildTestedWithinSixMonths(value)
{
    if(value =="Yes")
    {
        disableControls()
        setEnable("childTestedQuestion")
    }
    else
    {
        enableControls() 
    }
}
function enableSingleControl(id,value)
{
    if(value =="Yes")
    {
        document.getElementById(id).disabled=false
    }
    else
    {
        document.getElementById(id).value=""
        document.getElementById(id).disabled=true
    }
}
function setControlsForHivParentQuestion(value)
{
    if(value =="Yes")
    {
        enableControls()
    }
    else
    {
        disableControls()
        setEnable("hivParentQuestion")
    }
}
function enableControls()
{
    setEnable("childAgeQuestion")
    setEnable("childSickQuestion")
    setEnable("skinProblemQuestion")
    setEnable("parentDeceasedQuestion")
    setEnable("chronicallyIllQuestion")
    setEnable("schoolGradeQuestion")
    setEnable("sexualAbuseQuestion")
    //setEnable("sexuallyActiveQuestion")
    //setEnable("genitalDischargeQuestion")
    //setEnable("childAtRiskQuestion")
    setEnable("childTestedQuestion")
    setEnable("hivParentQuestion")
    setEnable("childTestedQuestion")
    if(document.getElementById("modifyBtn").disabled==true)
    setEnable("saveBtn")
}
function disableControls()
{
    setDisable("childAgeQuestion")
    setDisable("childSickQuestion")
    setDisable("skinProblemQuestion")
    setDisable("parentDeceasedQuestion")
    setDisable("chronicallyIllQuestion")
    setDisable("schoolGradeQuestion")
    setDisable("sexualAbuseQuestion")
    //setDisable("sexuallyActiveQuestion")
    //setDisable("genitalDischargeQuestion")
    //setDisable("childAtRiskQuestion")
    setDisable("hivParentQuestion")
    setDisable("childTestedQuestion")
    setDisable("saveBtn")
}
function setEnable(id)
{
    document.getElementById(id).disabled=false
}
function setDisable(id)
{
    document.getElementById(id).disabled=true
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
                <div>
                    <a href="hivriskassessmentchecklist.do">HIV Risk assessment checklist </a>
                    <a href="graduationCheckListAction.do">Graduation checklist </a>
                    <a href="#"> </a>
                    <a href="#"> </a>
                    <a href="#"> </a>
                    <a href="#"> </a>
                    <a href="#"> </a>
                </div>
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
              <div>
                  <a href="graduationCheckListReportAction.do" target="_blank">Graduation checklist report </a>
                    <a href="hivriskassessmentreport.do" target="_blank">HIV Risk assessment report </a>
                    <a href="#"> </a>
                    <a href="#"> </a>
                    <a href="#"> </a>
                    <a href="#"> </a>
                    <a href="#"> </a>
                    <a href="#"> </a>
              </div>
            </div></td>
          </tr>


      </table></td>
    <td width="13">&nbsp;</td>
      <td width="639" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
	 <td width="30%" height="39" class="homepagestyle">HIV Risk assessment checklist</td>
	 <td width="70%"> </td>
    </tr>
    <tr>
        <td class="homepagestyle" colspan="2" align="center"><logic:present name="msg"><label style="color:red; font-size: 15px;">${msg}</label></logic:present> </td>
	 
    </tr>

  </table>

         <html:form action="/hivriskassessmentchecklist" method="post" styleId="hhFormId">
<html:hidden property="actionName" styleId="actionName"/>
<html:hidden property="stateCode" styleId="stateCode"/>
<html:hidden property="hhUniqueId" styleId="hhUniqueId" />

  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!--DWLayoutTable-->
                <tr>
                  <td width="762" height="28" valign="top">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="27">
                          <table width="100%">
                              <tr><td colspan="4" align="center"><jsp:include page="../PartnerView.jsp" /></td></tr>
                              <tr><td colspan="4" align="center"><html:errors/> <logic:present name="ovcWithdrawn"><label style="color:red"> ${ovcWithdrawn} </label></logic:present></td></tr>
							<tr>
                            <td width="5%"><span style="color:#333">State:</span></td>
                          <td width="30%">
                              <html:text property="stateName" styleId="stateName" styleClass="bigfieldcellinput" disabled="true" />
                          </td>
                              <td width="5%"><span style="color:#333">LGA:</span></td>
                              <td width="30%"> 
                                  <html:select property="lgaCode" styleClass="bigfieldcellinput" styleId="lgaCode" onchange="submitForm('cboList');">
                                          <logic:iterate name="lgaListInOrgRecords" id="lga">
                                              <html:option value="${lga.lga_code}" >${lga.lga_name}</html:option>
                                          </logic:iterate>
                                      </html:select>
                                  <%--<html:select property="lgaCode" styleClass="bigfieldcellinput" styleId="lgaCode" onchange="setActionName('cboList');forms[0].submit()">
                                      <html:optionsCollection property="lgaList" label="lga_name" value="lga_code" />
                                  </html:select>
                                  
                                  <%--<input type="text" Class="bigfieldcellinput" id="LgasSelect" disabled="true" value="${lga.lga_name}"/>--%>
                              </td>
                                                        </tr>
                              <tr>
                              <td width="5%"><span style="color:#333">CBO:</span></td>
                              <td width="31%">
                                  <html:select property="orgCode" styleId="orgCode" styleClass="bigfieldcellinput" style="width:250px;">
                                      <html:option value=""> </html:option>
                                      <logic:iterate id="cbos" name="cboListInOrgRecords">
                                              <html:option value="${cbos.orgCode}">${cbos.orgName} </html:option>
                                          </logic:iterate>
                                      </html:select>
                                  
                              </td>
                              <td width="5%"><span style="color:#333"><jsp:include page="../includes/WardName.jsp" /></span></td>
                              <td width="21%">
                                  <html:text property="community" styleId="community" styleClass="bigfieldcellinput" disabled="true" />
                                  </td>
                        </tr>


                        </table></td>
                      </tr>
                  </table></td>
                </tr>
                
                
                
                <tr>
                  <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="120" valign="top">

					  <fieldset>
                        <legend class="fieldset">Personal Information  </legend>
                        <table width="100%" class="regsitertable">
                        <tr><td width="15%" valign="top" class="right">HH Unique SNo</td>
                            <td><html:text property="hhSerialNo" styleId="hhSerialNo" styleClass="smallfieldcellinput" style="width:50px;" onkeyup="constructUniqueId(this.value)" onchange="submitForm('hhDetails',this.value,4)"/>
                               
                                <label id="uniqueIdLabel" >${vcHhUniqueId} </label></td><td width="15%" valign="top" class="right">VC Unique Id: </td><td colspan="3"><label id="uniqueIdLabel" style="color:green;" >${vcUniqueId} </label></td>
                                
                        </tr>
                        <tr><td width="15%" valign="top" class="right">VC Name</td>
                        <td>
                                <html:select property="ovcId" styleClass="fieldcellinput" styleId="ovcId" onchange="submitForm('childDetails',this.value,5)" >
                                    <html:option value=""> </html:option>
                                    <html:optionsCollection property="ovcList" label="fullName" value="ovcId" />
                                    
                                </html:select>

                            
                        </tr>
                        
                        <tr>
                          <td valign="top" class="right">Sex: </td>
                              <td>
                                  <html:text property="gender" styleClass="fieldcellinput" styleId="gender" readonly="true" style="width:70px;" />
                     <span style=" margin-left: 32px;">Age:</span>
                                    <html:text property="age" styleClass="shortfieldcellinput" styleId="age" readonly="true"  />


                     </td><td>Age unit:</td><td colspan="3"><html:text styleClass="fieldcellinput" property="ageUnit" style="width:82px;" styleId="ageUnit"  />
                     </td>
                            </tr>

                            <tr>
                            <td valign="top" class="right" style=" height: 25px;"><br/>Address: </td>
                              <td>
                                  <html:textarea property="address" rows="3" cols="80" styleClass="fieldcellinput" styleId="address" readonly="true" ></html:textarea>
                                </td><td>Telephone:</td><td colspan="3"><html:text property="phone" styleClass="smallfieldcellinput" styleId="phone" readonly="true" /></td>
                                
                            </tr>

                        </table>
                      </fieldset>
                        </td>
                      </tr>

                  </table></td>
                </tr>
                
                
                <tr>
                    <td>
                        <fieldset><legend>HIV status disclosure</legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                            <tr>
                              <td class="right" colspan="2">Do you (Caregiver) know the HIV status of your child/ward?</td>
                              <td >
                                  <html:select styleClass="fieldcellinput" property="hivStatusQuestion" style="width:82px;" styleId="hivStatusQuestion" onchange="enableSingleControl('hivStatus',this.value)" >
                                     <html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                              <td><html:select property="hivStatus" styleId="hivStatus" disabled="true"> 
                                      <html:option value=""></html:option>
                                      <html:option value="positive">Positive</html:option>
                                      <html:option value="negative">Negative</html:option>
                                      <html:option value="rnd">Refused to disclose</html:option>
                                  </html:select>
                                  </td>
                          </tr>
                        </table>
                            </fieldset>
                    </td>
                </tr>
                
                
                <tr>
                  <td valign="top"><fieldset>
                        <legend class="fieldset"> </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="17">
                          <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">

			<tr>
                          <td width="20%">Date of assessment </td>
                          <td width="80%" colspan="2"><!--onchange="setActionName('gcfdetails'); forms[0].submit()" -->
                              <html:text property="dateOfAssessment" styleId="dateOfAssessment" styleClass="smallfieldcellinput" onchange="setActionName('assessmentDetails'); forms[0].submit()" readonly="true"/>&nbsp;(mm/dd/yyyy)
                          </td>

                        </tr>
                        </table>
                          <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                              <td class="right" colspan="3"><label style="color:red">${hraEligibilityMsg}</label> </td>
                              <td>Comment </td>
                          </tr>
                          
                          <%--<tr>
                              <td class="right" colspan="2">Is the child older than 2 years of age?</td>
                              <td >
                                  <html:select styleClass="fieldcellinput" property="childAgeQuestion" style="width:82px;" styleId="childAgeQuestion" disabled="true">
                                     <html:option value=""></html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td><td><html:textarea property="childAgeComment" styleId="childAgeComment" /> </td>
                          </tr>--%>
                          <tr>
                              <td class="right" colspan="2">Has the child tested negative for HIV more than 6 months ago?</td>
                              <td ><!--onchange="setControlsForChildTestedWithinSixMonths(this.value)"-->
                                  <html:select styleClass="fieldcellinput" property="childTestedQuestion" style="width:82px;" styleId="childTestedQuestion" >
                                      <html:option value=""></html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td><td><html:textarea property="childTestedComment" styleId="childTestedComment" /> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">Does the child have an HIV-positive parent/sibling/member of household that is an index patient? 
                                        <!--onchange="setControlsForHivParentQuestion(this.value)" -->
                                </td>
                              <td>
                                  <html:select styleClass="fieldcellinput" property="hivParentQuestion" style="width:82px;" styleId="hivParentQuestion" >
                                      <html:option value=""></html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td><td><html:textarea property="hivParentComment" styleId="hivParentComment" /> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">Has the child ever experience any form of sexual violence? </td><td >
                                  <html:select styleClass="fieldcellinput" property="sexualAbuseQuestion" style="width:82px;" styleId="sexualAbuseQuestion" >
                                      <html:option value=""></html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td><td><html:textarea property="sexualAbuseComment" styleId="sexualAbuseComment" /> </td>
                          </tr>
                          
                          <tr>
                              <td class="right" colspan="2">Has the child, caregiver or any member of household within the last 3 months been chronically ill?</td><td >
                                  <html:select styleClass="fieldcellinput" property="chronicallyIllQuestion" style="width:82px;" styleId="chronicallyIllQuestion" >
                                      <html:option value=""></html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td><td><html:textarea property="chronicallyIllComment" styleId="chronicallyIllComment" /> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">Is child sexually active/pregnant recently? </td><td >
                                  <html:select styleClass="fieldcellinput" property="sexuallyActiveQuestion" style="width:82px;" styleId="sexuallyActiveQuestion" >
                                      <html:option value="N/A">N/A </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td><td><html:textarea property="sexuallyActiveComment" styleId="sexuallyActiveComment" /> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">Has the child been hospitalized more than once in the last 3 months for recurrent and persistent fever/upper respiratory track infections over a period of at least 3 months? 
                                </td><td >
                                  <html:select styleClass="fieldcellinput" property="childSickQuestion" style="width:82px;" styleId="childSickQuestion" >
                                      <html:option value=""></html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td><td><html:textarea property="childSickComment" styleId="childSickComment" /> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">Has the child ever received blood transfusion or had any medical procedure in the last 6 months?</td><td >
                                  <html:select styleClass="fieldcellinput" property="bloodTransfusionQuestion" style="width:82px;" styleId="bloodTransfusionQuestion" >
                                      <html:option value=""></html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td><td><html:textarea property="bloodTransfusionComment" styleId="bloodTransfusionComment" /> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">Does this child (under 5 years) have a MUAC value of &lt;11.5 cm/ &lt;13.5 BMI or are there physical signs of wasting or failure to thrive?</td><td >
                                  <html:select styleClass="fieldcellinput" property="muacbmiQuestion" style="width:82px;" styleId="muacbmiQuestion" disabled="${hramuacfielddisabled}" >
                                      <html:option value="N/A">N/A</html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td><td><html:textarea property="muacbmiComment" styleId="muacbmiComment" /> </td>
                          </tr>
                          <%--<tr>
                              <td class="right" colspan="2">Does the child have recurring skin problems?</td><td >
                                  <html:select styleClass="fieldcellinput" property="skinProblemQuestion" style="width:82px;" styleId="skinProblemQuestion" disabled="${hrafiledsdisabled}">
                                      <html:option value=""></html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td><td><html:textarea property="skinProblemComment" styleId="skinProblemComment" /> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">Are one or both parents of child deceased?</td><td >
                                  <html:select styleClass="fieldcellinput" property="parentDeceasedQuestion" style="width:82px;" styleId="parentDeceasedQuestion" disabled="${hrafiledsdisabled}">
                                      <html:option value=""></html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td><td><html:textarea property="parentDeceasedComment" styleId="parentDeceasedComment" /> </td>
                          </tr>
                          
                          <tr>
                              <td class="right" colspan="2">Is the child below his/her expected school grade relative to his/her peers?</td><td >
                                  <html:select styleClass="fieldcellinput" property="schoolGradeQuestion" style="width:82px;" styleId="schoolGradeQuestion" disabled="${hrafiledsdisabled}">
                                      <html:option value=""></html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td><td><html:textarea property="schoolGradeComment" styleId="schoolGradeComment" /> </td>
                          </tr>
                          
                          <tr>
                              <td class="right" colspan="4" style="background-color:grey; color: white;"> Adolescent only (10 - 17 years of age)</td>
                          </tr>
                          
                          <tr>
                              <td class="right" colspan="2">Has child reported genital discharge, sores or pains in the last 6 months? </td><td >
                                  <html:select styleClass="fieldcellinput" property="genitalDischargeQuestion" style="width:82px;" styleId="genitalDischargeQuestion" disabled="${hraadolfiledsdisabled}">
                                      <html:option value="N/A">N/A </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td><td><html:textarea property="genitalDischargeComment" styleId="genitalDischargeComment" /> </td>
                          </tr>--%>
                          <tr>
                              <td class="right" colspan="2">Child at risk? </td><td colspan="2">
                                  <html:select styleClass="fieldcellinput" property="childAtRiskQuestion" style="width:82px;" styleId="childAtRiskQuestion" disabled="${hraadolfiledsdisabled}">
                                      <html:option value="">select...</html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select>
                                   </td>
                          </tr>
                          <tr><td colspan="4" style="width:100px; height: 20px;"></td></tr>
                          <tr>
                              <td class="right">Service provider name
                                  <html:text styleClass="fieldcellinput" property="serviceProviderName" styleId="serviceProviderName" />
                                    </td><td> Designation<html:text styleClass="fieldcellinput" property="designation" styleId="designation"/>
                                    </td><td colspan="2">Phone<html:text styleClass="fieldcellinput" property="serviceProviderPhone" style="width:120px;" styleId="serviceProviderPhone" /></td>
                                    
                          </tr>
                          
                          <tr>
				<td height="40" align="center" colspan="4">
                           <html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save');" disabled="${hraSaveBtnDisabled}"/>
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${hraModifyBtnDisabled}"/>
                           <html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${hraModifyBtnDisabled}"/>

                        </td>
				</tr>
                          
                        </table>
                  
                   </table></td>
                    </tr>
                    
                  </table>
                 
        	  </td>
                </tr>
                
            </table>


			</html:form>
		</td>
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
    </table>    </td>
    <td width="18">&nbsp;</td>
  </tr>
  
  </body>
</html>
