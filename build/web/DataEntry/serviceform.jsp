<%-- 
    Document   : Serviceform
    Created on : Mar 9, 2011, 5:28:04 PM
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
<title>VC monthly service form </title>
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
                $("#serviceDate").datepicker();
            });


function activateEnrolledInCare(value)
{
    if(value == "positive") 
    {
        document.getElementById("enrolledInCare").disabled = false;
        document.getElementById("enrolledOnART").disabled = false;
    }
    else 
    {
       document.getElementById("enrolledInCare").value="No"
       document.getElementById("enrolledOnART").value="No"
       document.getElementById("enrolledInCare").disabled = true;
       document.getElementById("enrolledOnART").disabled = true;
       document.getElementById("organizationClientIsReferred").value="select"
       document.getElementById("organizationClientIsReferred").disabled = true;
    }
}
function activateReferralList(value) {
    if(value == "Yes") 
    {
        document.getElementById("organizationClientIsReferred").disabled = false;
    }
    else 
    {
        if(document.getElementById("enrolledOnART").value=="No" && document.getElementById("enrolledInCare").value=="No")
        {
            document.getElementById("organizationClientIsReferred").value="select"
            document.getElementById("organizationClientIsReferred").disabled = true;
        }
    }
}
   function enableControl(value)
{
    if(value==" ")
    {
        document.getElementById("ovcSno").disabled=true;
        document.getElementById("ovcSno").value=" ";
        document.getElementById("characters").value=" ";

    }
    else
    {
        document.getElementById("ovcSno").disabled=false;

        document.getElementById("characters").value = " ";
        document.getElementById("ovcSno").value = " ";


        document.getElementById("wardId").value = value;

    }
}


function childWithdrawnAction(value) {
    if(value == "Yes") 
    {
        document.getElementById("reasonWithdrawal").disabled = false;
    }
    else 
    {
        document.getElementById("reasonWithdrawal").value=""
        document.getElementById("reasonWithdrawal").disabled = true;
    }
}
function tokenizeStr(str){

    var tokenArray = str.split(", ");
    return tokenArray;
}
function enableButton(id) {
    document.getElementById(id).disabled = false;
}


function disableButton(id) {
    document.getElementById(id).disabled = true;
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
    function makeId()
    {
        var state=document.getElementById("stateId").value
        var lga=document.getElementById("lgaId").value
        var cbo=getDropDownValue("cboList")
        var sNo=trim(document.getElementById("ovcSno").value)
        if(sNo=="" || sNo==" ")
        {
            document.getElementById("characters").value=" "
            return
        }
        if(state=="" || state==" " || lga=="" || lga==" " || cbo=="" || cbo==" ")
        {
          alert("State,Lga and CBO must not be empty")
          document.getElementById("ovcSno").value=" "
          return
        }

        var sNo=trim(document.getElementById("ovcSno").value)
        sNo=makeSno(sNo)
        if(sNo==false)
        {
            document.getElementById("ovcSno").focus()
            return false
        }
        var cboArray=cbo.split("/")
        var uniqueId=state+"/"+lga+"/"+cboArray[2]+"/"+sNo
        //var uniqueId=cbo+"/"+sNo
        document.getElementById("characters").value=uniqueId
        document.getElementById("lgaId").value=lga;
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

    function monitorGrowth()
    {
        if(document.getElementById("serviceAccessed23").checked==true)
        {
            enableControl("currentWeight")
            enableControl("currentHeight")
        }
        else
        {
            document.getElementById("currentWeight").value="0.0"
            document.getElementById("currentHeight").value="0.0"
            disableControl("currentWeight")
            disableControl("currentHeight")
        }
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
    return true
}
function makeOvcId(SNo,maxDigit,callerId)
{
    var hhId=document.getElementById("hhUniqueId").value
    if(hhId==null || hhId=="" || hhId==" " || hhId=="  ")
    {
      clearUniqueSNoField(callerId)
      alert("Please, enter House hold Id")
      return false
    }
    var num=padSerialNo(SNo,maxDigit,callerId)//validateSNo(SNo,maxDigit)
    if(num==0)
    {
        clearUniqueSNoField(callerId)
        showMsg(msg)
        return false    
    }
    else
    document.getElementById("ovcId").value=hhId+"/"+num
}
function showMsg(msg)
{
    if(msg=="" || msg==" " || msg=="  ")
    return
    else
    alert(msg)
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
    /*var validationResult=validateSNo(callerValue,maxDigit)
    if(validationResult==0)
    return false*/
    document.getElementById("actionName").value=val
    document.getElementById("dataForm").submit()
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
                    <div><jsp:include page="../DataEntryLinkPage.jsp"/>
                        <%--<a href="householdVulnerabilityIndexAction.do">Household Vulnerability Assessment Form</a><a href="Add.do">Enrolment card </a> <a href="householdServiceAction.do">Household service form</a><a href="OvcService.do">Ovc Service form</a><a href="ovcreferral.do">Referral form</a><a href="followupsurvey.do">Follow up CSI form</a>--%></div>
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
	 <td width="30%" height="39" class="homepagestyle">VC Service form </td>
	 <td width="70%"><html:errors/></td>
    </tr>

  </table>

         <html:form styleId="dataForm" action="/OvcService" method="post">
<html:hidden property="actionName" styleId="actionName"/>
<input type="hidden" name="curAge" id="curAge" value="${currentAge}"/>
<html:hidden property="ovcServiceId" styleId="ovcServiceId" />
<%--<html:hidden property="lga" styleId="lgaId" value="${lga.lga_code}" />--%>
<input type="hidden" name="lgaCode" id="lgaCode" />
<input type="hidden" name="stateId" id="stateId" value="${state.state_code}" />
<html:hidden property="hhUniqueId" styleId="hhUniqueId" />
      

  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!--DWLayoutTable-->
                <tr>
                  <td width="762" height="28" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="27">
                          <table width="100%"> 
                              <tr><td colspan="4"><label style="color:red"> ${ovcWithdrawn}</label></td></tr>
                                        <tr><td colspan="4" align="center"><jsp:include page="../PartnerView.jsp" /></td></tr>

							<tr>
                            <td width="5%"><span style="color:#333">State:</span></td>
                          <td width="30%">
                              <html:text styleId="stateList" property="state" styleClass="bigfieldcellinput" readonly="true" value="${state.name}" />
                          </td>
                              <td width="5%"><span style="color:#333">LGA:</span></td>
                              <td width="30%">
                                  <html:select property="lga" styleClass="bigfieldcellinput" styleId="lgaId" onchange="setActionName('cboList');forms[0].submit()">
                                          <logic:iterate name="lgaListInOrgRecords" id="lga">
                                              <html:option value="${lga.lga_code}" >${lga.lga_name}</html:option>
                                          </logic:iterate>
                                      </html:select>
                                  <%--<input type="text" Class="bigfieldcellinput" id="LgasSelect" readonly="true" value="${lga.lga_name}"/>--%>
                              </td>
                                                        </tr>
                              <tr>
                              <td width="5%"><span style="color:#333">CBO:</span></td>
                              <td width="31%">
                                  
                              <html:select styleId="cboList" property="completedbyCbo" styleClass="bigfieldcellinput" onchange="setActionName('cbo'); forms[0].submit()" >
                              <html:option value=""> </html:option>
                                      <logic:iterate id="cbos" name="cboListInOrgRecords" >
                                              <html:option value="${cbos.orgCode}">${cbos.orgName} </html:option>
                                          </logic:iterate>
               
                              </html:select>
                              </td>
                              <td width="5%"><span style="color:#333"><jsp:include page="../includes/WardName.jsp" /></span></td>
                              <td width="21%">
                                  <html:text property="ward" styleClass="bigfieldcellinput" styleId="ward" readonly="true"/>
                                  </td>
                        </tr>


                        </table></td>
                      </tr>
                  </table></td>
                </tr>
                
                <tr>
                  <td height="140" valign="top">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="120" valign="top">

					  <fieldset>
                        <legend class="fieldset">Personal Information  </legend>
                        <table width="100%" class="regsitertable">
                        <tr><td width="15%" valign="top" class="right">HH Unique SNo</td>
                            <td><html:text property="hhSerialNo" styleId="hhSerialNo" styleClass="smallfieldcellinput" style="width:50px;" onkeyup="constructUniqueId('cboList',this.value,5)" onchange="submitForm('hhDetails',this.value,4)"/>
                               
                                <label id="uniqueIdLabel" >${vcHhUniqueId} </label></td><td width="15%" valign="top" class="right">VC Unique Id: </td><td colspan="3"><label id="uniqueIdLabel" style="color:green;" >${vcUniqueId} </label></td>
                                
                        </tr>
                        <tr><td width="15%" valign="top" class="right">VC Name</td>
                        <td>
                                <html:select property="ovcId" styleClass="fieldcellinput" styleId="ovcId" onchange="submitForm('baselineDetails',this.value,5)" >
                                    <html:option value=""> </html:option>
                                    <logic:present name="ovcListInService">
                                    <logic:iterate name="ovcListInService" id="ovc">
                                        <html:option value="${ovc.ovcId}">${ovc.firstName} ${ovc.lastName}</html:option>
                                    </logic:iterate>
                                    </logic:present>
                                </html:select>

                            </td><td>Enrollment date:</td><td><html:text property="dateEnrollment" styleId="dateEnrollment" styleClass="smallfieldcellinput" readonly="true" /></td><td>(mm/dd/yyyy)</td><td></td>
                        </tr>
                        
                        <tr>
                          <td valign="top" class="right">Sex: </td>
                              <td>
                                  <html:text property="gender" styleClass="fieldcellinput" styleId="gender" readonly="true" style="width:70px;" />
                     <span style=" margin-left: 4px;">Current age:</span>
                                    <html:text property="age" styleClass="shortfieldcellinput" styleId="age" readonly="true"  />


                     </td><td>Age unit:</td><td><html:text styleClass="fieldcellinput" property="ageUnit" style="width:82px;" styleId="ageUnit"  />
                     </td><td>Weight(Kg):</td><td><html:text property="weight" styleClass="shortfieldcellinput" styleId="weight" readonly="true" /></td>
                            </tr>

                            <tr>
                            <td valign="top" class="right" style=" height: 25px;"><br/>Address: </td>
                              <td>
                                  <html:textarea property="address" rows="3" cols="80" styleClass="fieldcellinput" styleId="address" readonly="true" ></html:textarea>
                                </td><td>Telephone:</td><td><html:text property="phone" styleClass="smallfieldcellinput" styleId="phone" readonly="true" /></td>
                                <td>Height(cm):</td>
                                <td><html:text property="height" styleClass="shortfieldcellinput" styleId="height" readonly="true" /></td>
                                 
                            </tr>
                            <tr>
                            <td>Current HIV status </td>
                              <td>
                                  <html:text property="currentHivStatus" styleId="currentHivStatus" styleClass="fieldcellinput" disabled="true"/>
                                </td>
                                <td> </td><td> </td>
                                <td></td>
                                <td> </td>
                                
                            </tr>
                        </table>
                      </fieldset>


					  </td>
                      </tr>
                      
                      

                  </table></td>
                </tr>

                <tr>
                  <td height="123" valign="top"><fieldset>
                        <legend class="fieldset">Services </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="17">
                          <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">

			<tr>
                          <td width="20%">Date services provided: </td>
                          <td width="80%" colspan="2">
                              <html:text property="serviceDate" styleId="serviceDate" styleClass="smallfieldcellinput" disabled="${servicedatedisabled}" onchange="setActionName('serviceDetails'); forms[0].submit()" readonly="true"/> &nbsp;(mm/dd/yyyy)
                          </td>

                        </tr>
                        </table>
                          <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                              <td width="21%" bgcolor="#CCCCCC" class="right">Health: </td>
                              <td bgcolor="#CCCCCC" colspan="2"></td>
                            
                          </tr>
                          <tr>
                            <td rowspan="16">&nbsp;</td>
                              <td><html:multibox property="healthServices" value="Health education" styleClass="smallfieldcellselect" styleId="serviceAccessed35"/></td>
                            <td>Health education</td>
                          </tr>
                          <tr>
                            <td><html:multibox property="healthServices" value="Treatment of minor illnesses" styleClass="smallfieldcellselect" styleId="treatmentofminorillnesses"/></td>
                            <td>Treatment of minor illnesses</td>
                          </tr>
			
                          <tr>
                              <td><html:multibox property="healthServices" value="De-worming" styleClass="smallfieldcellselect" styleId="serviceAccessed38"/></td>
                              <td>Deworming </td>
                          </tr>
                          <tr>
                            <td><html:multibox property="healthServices" value="Insecticides treated bed net" styleClass="smallfieldcellselect" styleId="insecticide"/></td>
                            <td>Insecticides treated bed nets </td>
                          </tr>  
                          <tr>
                            <td><html:multibox property="healthServices" value="Water treatment" styleClass="smallfieldcellselect" styleId="serviceAccessed32"/></td>
                            <td>Water treatment </td>
                          </tr>
                          <tr>
                            <td><html:multibox property="healthServices" value="Wash" styleClass="smallfieldcellselect" styleId="wash"/></td>
                            <td>Water, Sanitation and Hygiene (WASH)</td>
                          </tr>
                          <tr>
                            <td><html:multibox property="healthServices" value="Community HIV services (HTC/PMTCT)" styleClass="smallfieldcellselect" styleId="communityhivservices"/></td>
                            <td>Community HIV services – HTC/PMTCT</td>
                          </tr>
                          <tr>
                            <td><html:multibox property="healthServices" value="HIV services referral (HTC/EID)" styleClass="smallfieldcellselect" styleId="healthreferralHCTEID"/></td>
                            <td>HIV services referral – HTC/EID</td>
                          </tr>
                          <tr>
                            <td><html:multibox property="healthServices" value="HIV services referral-ART" styleClass="smallfieldcellselect" styleId="healthreferralART"/></td>
                            <td>HIV services referral – ART</td>
                          </tr>
                          <tr>
                           <td><html:multibox property="healthServices" value="Access for HIV care" styleClass="smallfieldcellselect" styleId="accessforHIV"/></td>
                            <td>HIV care and support</td>
                          </tr>
                          <tr>
                           <td width="4%"><html:multibox property="healthServices" value="Community TB symptom screening" styleClass="smallfieldcellselect" styleId="communityTBsymptomscreening"/></td>
                            <td>TB symptom screening </td>
                          </tr>
                          <tr>
                              <td width="4%"><html:multibox property="healthServices" value="TB services referral (Diagnosis-DOTS)" styleClass="smallfieldcellselect" styleId="tbservicesreferral"/></td>
                            <td>TB services referral (Diagnosis, DOTS) </td>
                          </tr>
                          <tr><%-- --%>
                              <td width="4%"><html:multibox property="healthServices" value="Adolescent HIV prevention and sexual reproductive health services" styleClass="smallfieldcellselect" styleId="hivprev" disabled="${hivprevdisabled}"/></td>
                            <td>Adolescent HIV prevention and sexual reproductive health services </td>
                          </tr>
                          <tr>
                            <td><html:multibox property="healthServices" value="Health referral" styleClass="smallfieldcellselect" styleId="healthreferral"/></td>
                            <td>Health referral</td>
                          </tr>
                          
                          <tr>
                              <td><input type="checkbox" name="chkHealthOther" class="smallfieldcellselect" id="growthOther"/>
                                  </td>
                            <td>Other (specify) <html:text property="healthOther" styleClass="fieldcellinput" styleId="serviceAccessed37"/></td>
                          </tr>
                          
                        </table>

                        <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                            <td width="21%" bgcolor="#CCCCCC" class="right">Nutrition: </td>
                            <td bgcolor="#CCCCCC" colspan="2"> </td>
                            
                          </tr>
                          <tr>
                            <td rowspan="8">&nbsp;</td>
                            <td><html:multibox property="nutritionalServices" value="Nutrition education and counselling" styleClass="smallfieldcellselect" styleId="serviceAccessed21"/></td>
                            <td>Nutrition education</td>
                          </tr>
                          <tr>
                            
                            <td><html:multibox property="nutritionalServices" value="Food & nutritional supplements" styleClass="smallfieldcellselect" styleId="serviceAccessed22"/></td>
                            <td>Food and Nutritional supplements </td>
                          </tr>
                          <tr>
                           <tr>
                            <td><html:multibox property="healthServices" value="Vitamin A-Zinc and Iron suplement" styleClass="smallfieldcellselect" styleId="serviceAccessed36"/></td>
                            <td>Vitamin A, Zinc and Iron suplement</td>
                          </tr>
                            <td><html:multibox property="nutritionalServices" value="NACS" styleClass="smallfieldcellselect" styleId="nacs"/></td>
                            <td>Nutrition assessment, counselling and support (NACS)</td>
                          </tr>
                          <tr>
                              <td><html:multibox property="nutritionalServices" value="Growth monitoring" styleClass="smallfieldcellselect" styleId="serviceAccessed23" onclick="monitorGrowth()"/></td>
                              <td>Growth monitoring   <label style="margin-left:20px;">Weight(kg) <html:text property="currentWeight" styleClass="shortfieldcellinput" styleId="currentWeight" disabled="${weightDisabled}" /></label>
                                  <label style="margin-left:20px;">Height(cm) </label><html:text property="currentHeight"  styleClass="shortfieldcellinput" styleId="currentHeight" disabled="${heightDisabled}" /></td>
                          </tr>
                          <%--<tr>
                            <td><html:multibox property="nutritionalServices" value="Water-Sanitation and Hygiene (WASH)" styleClass="smallfieldcellselect" styleId="wash"/></td>
                            <td>Water, Sanitation and Hygiene (WASH) </td>
                          </tr>--%>
                          <tr>
                            <td><html:multibox property="nutritionalServices" value="Nutrition referral" styleClass="smallfieldcellselect" styleId="trainingonhousehold"/></td>
                            <td>Nutrition referral – malnutrition</td>
                          </tr>
                          <tr>
                              <td><input type="checkbox" name="chkNutritionOther" class="smallfieldcellselect" id="nutritionOther"/></td>
                            <td>Others (specify)  <html:text property="nutritionOther" styleClass="fieldcellinput" styleId="serviceAccessed24"/></td>
                          </tr>
                        </table>

                         <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                            <td width="21%" bgcolor="#CCCCCC" class="right">Shelter and care: </td>
                            <td bgcolor="#CCCCCC" colspan="2"></td>
                          </tr>
                          <tr>
                            <td rowspan="6">&nbsp;</td>
                            <td width="4%"><html:multibox property="shelterServices" value="Re-integration into Family" styleClass="smallfieldcellselect" styleId="serviceAccessed62"/></td>
                            <td>Re-integration into family </td>
                          </tr>
                          <tr>
                              <td><html:multibox property="shelterServices" value="Provision/repair of accommodation" styleClass="smallfieldcellselect" styleId="repareofaccommodation"/></td>
                            <td>Provision/repair of accommodation </td>
                          </tr>
                          <tr>
                              <td><html:multibox property="shelterServices" value="Foster parenting" styleClass="smallfieldcellselect" styleId="fosterparent"/></td>
                            <td>Foster parenting </td>
                          </tr>
                          <tr>
                              <td><html:multibox property="shelterServices" value="Clothing support" styleClass="smallfieldcellselect" styleId="serviceAccessed63"/></td>
                            <td>Clothing support </td>
                          </tr>
                          <tr>
                              <td><html:multibox property="shelterServices" value="Referral for shelter and care services" styleClass="smallfieldcellselect" styleId="serviceAccessed63"/></td>
                            <td>Referral </td>
                          </tr>
                          <tr>
                              <td>
                                  <input type="checkbox" name="chkShelterOther" class="smallfieldcellselect" id="shelterOther"/>
                                  </td>
                            <td>Other (specify) <html:text property="shelterOther" styleClass="fieldcellinput" styleId="serviceAccessed65"/></td>
                          </tr>
                        </table>




                        <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                            <td width="21%" bgcolor="#CCCCCC" class="right">Education: </td>
                            <td bgcolor="#CCCCCC" colspan="2"></td>
                          </tr>
                          <tr>
                            <td rowspan="10">&nbsp;</td>
                            <td><html:multibox property="educationalServices" value="Advocacy for school enrolment" styleClass="smallfieldcellselect" styleId="holisticscholarship"/></td>
                            <td>School enrolment/re-enrolment </td>
                          </tr>
                          <tr>
                            
                            <td width="4%"><html:multibox property="educationalServices" value="Advocacy for waiver of school fees" styleClass="smallfieldcellselect" styleId="serviceAccessed41"/></td>
                            <td width="75%">Waiver of school fees </td>
                          </tr>
                          <tr>
                            <td><html:multibox property="educationalServices" value="Provision of school materials" styleClass="smallfieldcellselect" styleId="serviceAccessed42"/></td>
                            <td>Provision of school materials/uniform</td>
                          </tr>
                          <tr>
                              <td><html:multibox property="educationalServices" value="School visit" styleClass="smallfieldcellselect" styleId="serviceAccessed44"/></td>
                            <td>School visit </td>
                          </tr>
                          <tr>
                              <td><html:multibox property="educationalServices" value="School performance assessment" styleClass="smallfieldcellselect" styleId="serviceAccessed45"/></td>
                            <td>School performance assessment </td>
                          </tr>
                          <tr>
                          <tr>
                              <td><html:multibox property="educationalServices" value="Holistic scholarship" styleClass="smallfieldcellselect" styleId="holisticscholarship"/></td>
                            <td>Holistic scholarship </td>
                          </tr>
                          <tr> 
                              <td> </td>
                            <td>Did the child miss school for more than 5 days in the last 1 month?
                            
                                <html:select property="childMissedSchool" styleClass="smallfieldcellselect" styleId="childMissedSchool" disabled="${childMissSchoolDisabled}">
                                    <html:option value="N/A">N/A</html:option>
                                    <html:option value="No">No</html:option>
                                    <html:option value="Yes">Yes</html:option>
                                </html:select>
                                </td>
                            
                          </tr>
                          <tr>
                              <td><html:multibox property="educationalServices" value="Referral for educational services" styleClass="smallfieldcellselect" styleId="educationalReferral"/></td>
                            <td>Referral</td>
                          </tr>
                          <tr>
                              <td><input type="checkbox" name="chkEducationOther" class="smallfieldcellselect" id="educationOther"/></td>
                            <td>Other (specify) <html:text property="educationOther" styleClass="fieldcellinput" styleId="serviceAccessed46"/></td>
                          </tr>
                        </table>

                        <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                        <tr>
                          <td width="21%" bgcolor="#CCCCCC" class="right">Psychosocial Support: </td>
                          <td bgcolor="#CCCCCC" colspan="2"></td>
                        </tr>
                        <tr>
                          <td rowspan="4">&nbsp;</td>
                          <td width="4%"><html:multibox property="psychoServices" value="Counselling support" styleClass="smallfieldcellselect" styleId="serviceAccessed11"/></td>
                          <td width="75%">Counselling support </td>
                        </tr>
                        <tr>
                          
                          <td><html:multibox property="psychoServices" value="Recreational activity" styleClass="smallfieldcellselect" styleId="serviceAccessed12"/></td>
                          <td>Recreational activity (e.g kids club) </td>
                        </tr>
                        <tr>
                            <td><html:multibox property="psychoServices" value="Life skill support" styleClass="smallfieldcellselect" styleId="serviceAccessed13"/></td>
                          <td>Life skill support </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="checkbox" name="chkPsychosocialOther" class="smallfieldcellselect" id="psychosocialOther"/>
                            </td>
                          <td>Others (specify) <html:text property="psychosocialOther" styleClass="fieldcellinput" styleId="serviceAccessed14"/></td>
                        </tr>
                      </table>

                        <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                            <td width="21%" bgcolor="#CCCCCC" class="right">Child protection: </td>
                            <td bgcolor="#CCCCCC" colspan="2"></td>
                          </tr>
                          <tr>
                            <td rowspan="7">&nbsp;</td>
                            <td width="4%"><html:multibox property="protectionServices" value="Legal services" styleClass="smallfieldcellselect" styleId="serviceAccessed51"/></td>
                            <td width="75%">Legal services </td>
                          </tr>
                          <tr>
                              
                              <td><html:multibox property="protectionServices" value="Succession planning" styleClass="smallfieldcellselect" styleId="serviceAccessed53"/></td>
                            <td>Succession planning </td>
                          </tr>
                          <tr>
                            
                            <td><html:multibox property="protectionServices" value="Birth registration" styleClass="smallfieldcellselect" styleId="serviceAccessed52"/></td>
                            <td>Birth registration </td>
                          </tr>
                          <tr>  
                              <td></td>
                            <td >Did Child experience any form of abuse, exploitation or neglect in the last 1 month?
                            
                                <html:select property="childAbused" styleClass="smallfieldcellselect" styleId="childAbused">
                                    <html:option value="0">N/A</html:option>
                                    <html:option value="1">No</html:option>
                                    <html:option value="2">Yes</html:option>
                                </html:select>
                                </td>
                            
                          </tr>
                          <tr>                            
                            <td></td>
                            <td> If yes, was the child Linked to Government for post-violence services? 
                            <html:select property="childLinkedToGovt" styleClass="smallfieldcellselect" styleId="childLinkedToGovt">
                                    <html:option value="0">N/A</html:option>
                                    <html:option value="1">No</html:option>
                                    <html:option value="2">Yes</html:option>
                                </html:select>
                            </td>
                          </tr>
                          <tr>
                            <td><html:multibox property="protectionServices" value="Referral for protection services" styleClass="smallfieldcellselect" styleId="protectionReferral"/></td>
                            <td>Referral </td>
                          </tr>
                          <tr>
                              <td>
                                  <input type="checkbox" name="chkProtectionOther" class="smallfieldcellselect" id="protectionOther"/>
                                  </td>
                            <td>Other (specify) <html:text property="protectionOther" styleClass="fieldcellinput" styleId="serviceAccessed54"/></td>
                          </tr>
                        </table>




                        <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                            <td width="21%" bgcolor="#CCCCCC" class="right">Economic Strengthening: </td>
                            <td bgcolor="#CCCCCC" colspan="2"></td>
                          </tr>
                          <tr>
                            <td rowspan="6">&nbsp;</td>
                            <td width="4%"><html:multibox property="economicServices" value="Financial Education" styleClass="smallfieldcellselect" styleId="serviceAccessed71"/></td>
                            <td width="75%">Financial Education </td>
                          </tr>
                          <tr>
                            
                            <td><html:multibox property="economicServices" value="Micro-finance (savings and loans)" styleClass="smallfieldcellselect" styleId="microcreditsupport"/></td>
                            <td>Micro-finance (savings and loans) </td>
                          </tr>
                          <tr>
                            <td><html:multibox property="economicServices" value="Vocational/apprenticeship training" styleClass="smallfieldcellselect" styleId="Vocationalapprenticeshiptraining"/></td>
                            <td>Vocational/apprenticeship training </td>
                          </tr>

                          <tr>
                              <td><html:multibox property="economicServices" value="Livelihood opportunity" styleClass="smallfieldcellselect" styleId="serviceAccessed73"/></td>
                            <td>Livelihood opportunity</td>
                          </tr>
                          <tr>
                              <td><html:multibox property="economicServices" value="Business grant" styleClass="smallfieldcellselect" styleId="businessgrant"/></td>
                            <td>Business grant</td>
                          </tr>
                          <tr>
                              <td>
                                  <input type="checkbox" name="chkEconomicOther" class="smallfieldcellselect" id="economicOther"/>
                                  </td>
                            <td>Other (specify) <html:text property="economicOther" styleClass="fieldcellinput" styleId="serviceAccessed75"/></td>
                          </tr>




                        </table>                        </td>
                      </tr>
                  </table>
                  </fieldset>






                  </td>
                </tr>
                <%--<tr>
                  <td height="53" valign="top">





				  <fieldset>
                        <legend class="fieldset">Referral <span class="style1"> </span>  </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="727" height="46" valign="top">
					                                            
					  <table width="100%" class="regsitertable">
                         <tr>
                             <td width="3%"><html:multibox property="referralServices" value="Nutritional support" styleClass="smallfieldcellselect" styleId="nutriSupportReferral" /></td>
                            <td width="16%">Health services </td>
                              <td width="3%"><html:multibox property="referralServices" value="Health referral" styleClass="smallfieldcellselect" styleId="healthSupportReferral" /></td>
                            <td width="16%">Nutritional services </td>
                            <td width="3%"><html:multibox property="referralServices" value="Shelter and care" styleClass="smallfieldcellselect" styleId="shelterSupportReferral" /></td>
                            <td width="15%">Shelter and care services</td>
                            <td width="3%"><html:multibox property="referralServices" value="Educational support" styleClass="smallfieldcellselect" styleId="eduSupportReferral" /></td>
                            <td width="20%">Educational services</td>
                            
                          </tr>
                          <tr>
                              <td width="3%"><html:multibox property="referralServices" value="Psychosocial support" styleClass="smallfieldcellselect" styleId="psychoSupportReferral" /></td>
                            <td width="25%">Psychosocial support services</td>
                              <td width="3%"><html:multibox property="referralServices" value="Protection service" styleClass="smallfieldcellselect" styleId="protServiceReferral" /></td>
                            <td width="20%">Protection services </td>
                            <td width="3%"><html:multibox property="referralServices" value="Economic strengthening" styleClass="smallfieldcellselect" styleId="econStrengtheningReferral" /></td>
                            <td width="43%" colspan="3">Economic strengthening services</td>

                            <!--<td width="20%"> </td>
                            <td width="20%"> </td>-->
                          </tr>
                          
                                            </table></td>
                    </tr>
                    
                  </table>
                  </fieldset>


				  </td>
                </tr>--%>
                <%--<tr>
                  <td height="53" valign="top">





				  <fieldset>
                        <legend class="fieldset">Child HIV status update <span class="style1"> </span>  </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="727" height="46" valign="top">
					                                            
		<table width="100%" class="regsitertable">
                        <tr>
                            
                             <td>Last known HIV status &nbsp;
                                 <html:text property="currentHivStatus" styleId="currentHivStatus" styleClass="fieldcellinput" disabled="true"/>
                            </td>
                            <td> &nbsp;New HIV status <html:select property="newHivStatus" styleClass="fieldcellinput" styleId="newHivStatus" style="margin-left:20px" onchange="activateEnrolledInCare(this.value)" >
                                      <html:option value="select">select...</html:option>
                                      <html:optionsCollection property="hivStatusList" label="hivStatusName" value="hivStatusCode" />
                                  </html:select>
                            </td>
                          </tr>
                         <tr>
                            <td>
                                Child enrolled in care?<html:select property="enrolledInCare" styleClass="smallfieldcellinput" styleId="enrolledInCare" style="margin-left:10px" onchange="activateReferralList(this.value)" disabled="true">
                                      <html:option value="No">No</html:option>
                                      <html:option value="Yes">Yes</html:option>
                                </html:select>
                            </td>
                            <td>&nbsp;Enrolled on ART?<html:select property="enrolledOnART" styleClass="smallfieldcellinput" styleId="enrolledOnART" style="margin-left:12px" onchange="activateReferralList(this.value)" disabled="true">
                                      <html:option value="No">No</html:option>
                                      <html:option value="Yes">Yes</html:option>
                                  </html:select>
                            </td>
                          </tr>
                          <tr>
                                <td class="right" colspan="2">Facility child is enrolled
                                    &nbsp;<html:select property="organizationClientIsReferred" styleClass="fieldcellinput" styleId="organizationClientIsReferred" style="width:500px;" disabled="true">
                                      <html:option value="select">Select</html:option>
                                      <html:optionsCollection property="referralDirectoryList" label="facilityName" value="facilityId"/>
                                  </html:select></td>
                                
                            </tr>
                   </table></td>
                    </tr>
                    
                  </table>
                  </fieldset>
			  </td>
                </tr>--%>
                
                
                <tr><td>
                <fieldset>
                        <legend class="fieldset">Child followup information <!--<span class="style1">(Tick reason child is withdrawn)</span>-->  </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="727" height="30" valign="top">
					  <table width="100%" class="regsitertable">
                                              <tr><td width="28%">Child withdrawn from program</td>
                                                  <td ><select class="shortfieldcellinput" id="childWithdrawn" onChange="childWithdrawnAction(this.value);">
					    <option value=" "></option><option value="No">No</option><option value="Yes">Yes</option></select>
                                                  </td>
                                              <td width="28%">&nbsp;&nbsp; Select reason child is withdrawn</td>
                                                    <td >
                                                        <html:select property="reasonWithdrawal" styleId="reasonWithdrawal" disabled="true">
                                                            <html:option value=""> </html:option>
                                                            <html:option value="ageabove17">Age 18 and above</html:option>
                                                            <html:option value="Graduated">Graduated from program</html:option>
                                                            <html:option value="transfer">Transfer to other program</html:option>
                                                            <html:option value="Migrated">Migrated</html:option>
                                                            <html:option value="Loss to follow-up">Loss to follow-up (after 3 months)</html:option>
                                                            <html:option value="Known death">Known death</html:option>
                                                            <html:option value="voluntaryWithdrawal">Voluntary withdrawal</html:option>
                                                        </html:select>
                                                    </td>
                                              </tr>
					  </table>
					  <table width="100%" class="regsitertable">
                         
                                            </table></td>
                    </tr>
                  </table>
                  </fieldset>
                    </td></tr>
                <tr>
                    <td><table><tr><td >Name of Volunteer/Service provider</td><td ><html:text property="providerName" styleClass="fieldcellinput" styleId="providerName"/></td></tr></table></td>

                </tr>
                <!--<tr>
                    <td width="745" height="27">&nbsp;</td>
                </tr>-->
                <tr>
				<td height="40" align="center">
                            <%--<html:reset value="New" styleId="newBtn" style="width:70px; height:25px;  margin-right:5px;" disabled="true"/>--%>
                            <html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${serviceSaveDisabled}"/>
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${serviceModifyDisabled}"/><html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${serviceModifyDisabled}"/>

                        </td>
				</tr>

                <tr>
				<td height="30">&nbsp;</td>
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
<%--
    //session.removeAttribute("baselineInfo");
    session.removeAttribute("ovcWithdrawn");
    //session.removeAttribute("serviceModifyDisabled");
    //session.removeAttribute("serviceSaveDisabled");
    session.removeAttribute("serviceId");
--%>
</html>


