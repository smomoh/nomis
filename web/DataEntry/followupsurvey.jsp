<%-- 
    Document   : followupsurvey
    Created on : Apr 13, 2011, 2:40:16 PM
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
<title>VC follow up CSI </title>
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
.caregiverSearch2
{
	/*position: absolute; left: 30%; top: 80%;*/
        border-style:solid;
	border-width:1px;
	border-color:#3333FF;
	border-top-width:0px;
	background-color: #0066FF;
	padding:1px;
	font-family:Arial;
	font-weight:bold;
	font-size:10pt;
	visibility:visible;
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
<script type="text/javascript" src="js/Enrollmentjsfile.js"></script>
<script language="javascript">
			$(function() {
                $("#dateOfSurvey").datepicker();
            });

function stateChanged()
{
	if (xmlhttp.readyState==4)
	{
		var lgas=xmlhttp.responseText;
                if(lgas==" " || lgas=="" || lgas==";" || lgas==null)
                return false;
                //alert(callerId)
            var values=lgas.split(";")

            if(callerId=="partofnamestring")
                document.getElementById("ovcNames").innerHTML=lgas
            else if(callerId=="searchCaregiverInHousehold")
            {
                document.getElementById("caregiverSurnameNameSpan").innerHTML=lgas
            }
            else if(callerId=="searchCaregiverById")
            {
                populateCaregiverInfo(values)
            }
            else if(callerId=="cbo")
            {
                setSelectBoxValues(values, "CbosSelect")
            }

            else if(callerId=="nameAndAge" || callerId=="nameAndCaregiver")
            {
                if(values[0]=="no duplicate")
                return
                getConfirmation()
            }
            else if(callerId=="schoolList")
            {
                populateSchoolDropdownBox(values, "schoolName")
            }
	}
        else
        {
            //alert("error "+xmlhttp.responseText)
        }
}
function activateEnrolledInCare(value)
{
    if(value == "positive") 
    {
        document.getElementById("enrolledInCare").disabled = false;
        document.getElementById("enrolledOnArt").disabled = false;
    }
    else 
    {
       document.getElementById("enrolledInCare").value="No"
       document.getElementById("enrolledOnArt").value="No"
       document.getElementById("enrolledInCare").disabled = true;
       document.getElementById("enrolledOnArt").disabled = true;
       document.getElementById("vcFacilityId").value="select"
       document.getElementById("vcFacilityId").disabled = true;
    }
}
function activateCaregiverEnrolledInCare(value)
{
    if(value == "positive") 
    {
        document.getElementById("cgiverEnrolledInCare").disabled = false;
        document.getElementById("cgiverEnrolledOnArt").disabled = false;
    }
    else 
    {
       document.getElementById("cgiverEnrolledInCare").value="No"
       document.getElementById("cgiverEnrolledOnArt").value="No"
       document.getElementById("cgiverEnrolledInCare").disabled = true;
       document.getElementById("cgiverEnrolledOnArt").disabled = true;
       document.getElementById("cgiverFacilityId").value="select"
       document.getElementById("cgiverFacilityId").disabled = true;
    }
}
function activateReferralList(value) {
    if(value == "Yes") 
    {
        document.getElementById("vcFacilityId").disabled = false;
    }
    else 
    {
        document.getElementById("vcFacilityId").value="select"
        document.getElementById("vcFacilityId").disabled = true;
    }
}
function activateCaregiverReferralList(value) {
    if(value == "Yes") 
    {
        document.getElementById("cgiverFacilityId").disabled = false;
    }
    else 
    {
        document.getElementById("cgiverFacilityId").value="select"
        document.getElementById("cgiverFacilityId").disabled = true;
    }
}
function childWithdrawnAction(value) 
{
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

function enableButton(id) {
    document.getElementById(id).disabled = false;
}


function disableButton(id) {
    document.getElementById(id).disabled = true;
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
    var num=padSerialNo(SNo,maxDigit,callerId)
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
    var validationResult=validateSNo(callerValue,maxDigit)
    if(validationResult==0)
    return false
    document.getElementById("actionName").value=val
    document.getElementById("dataForm").submit()
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
        var ageStatus=document.getElementById("curAge").value
        var ageStatusArray=ageStatus.split(";")
        if(ageStatusArray[0]=="overaged")
        alert("This Ovc is above 17 years old. Current age is "+ageStatusArray[1])
    }
    function enableChkBtn(id,component)
    {
        if(id=="true")
            enableControl2(component)
        else
            disableControl2(component)
    }
    function childInSchool(boolValue)
    {
        id="updatedSchoolName"
        if (boolValue=="Yes")
        {
            enableControl2(id)
        }
        else
        {
            disableControl2(id)
            document.getElementById(id).value=" "
        }
    }
    function enableControl2(id)
    {
        document.getElementById(id).disabled=false
    }
    function disableControl2(id)
    {
        document.getElementById(id).disabled=true
    }
    function populateAge(value)
    {
             if(value=="Month")
             i=11;
             else if(value=="Year")
             i=17;

             document.getElementById("updatedAge").options.length=0
             for(var j=1; j<=i; j++)
             {
                 document.getElementById("updatedAge").options[j-1]=new Option(j,j)
             }
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
              <div><jsp:include page="../ServiceReportLinkPage.jsp"/> <a href="#"> &nbsp; </a><a href="#"> &nbsp; </a><a href="#"> &nbsp; </a></div>
            </div></td>
          </tr>


      </table></td>
    <td width="13">&nbsp;</td>
      <td width="639" >

			<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
	 <td width="42%" height="39" class="homepagestyle">Follow-Up CSI Form </td>
	 <td width="58%"><html:errors/></td>
    </tr>

  </table>

                            <%--<span style="color:black"><html:errors/></span>--%>
                            <html:form method="post" styleId="dataForm" action="/followupsurvey">
            <html:hidden property="actionName" styleId="actionName"/>
            <html:hidden property="followupId"/>
            <html:hidden property="followupCsiId"/>
            <%--<html:hidden property="lga" styleId="lgaId" value="${lga.lga_code}" />--%>
            <input type="hidden" name="lgaCode" id="lgaCode" />
            <input type="hidden" name="stateId" id="stateId" value="${state.state_code}" />
            <html:hidden property="hhUniqueId" styleId="hhUniqueId" />



            
      <table width="727" border="0" cellpadding="0" cellspacing="0">
                <!--DWLayoutTable-->
                <tr>
                  <td width="727" height="28" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="727" height="27">
					  
                        <table width="100%">
                            <tr><td colspan="4" align="center"><jsp:include page="../PartnerView.jsp" /></td> </tr>
                            <tr><td colspan="4" align="center"><label style="color:red;">${ovcWithdrawn}</label></td></tr>


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
                                  <!--<span id="selected3"><select Class="bigfieldcellinput" ><option value=" "> </option></select></span>--></td>
                        </tr>
                        </table>
			</td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="150" valign="top"><table width="727" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="727" height="132" valign="top">


					  <fieldset>
                        <legend class="fieldset">Baseline Information  </legend>
                        <table width="100%" class="regsitertable">
                        <tr><td width="15%" valign="top" class="right">HH Unique SNo</td>
                            <td><html:text property="hhSerialNo" styleId="hhSerialNo" styleClass="smallfieldcellinput" style="width:50px;" onkeyup="constructUniqueId('cboList',this.value,5)" onchange="submitForm('hhDetails',this.value,4)"/>

                                <span style="width:250px"><label id="uniqueIdLabel" >&nbsp; ${vcHhUniqueId} </label></span><label style="margin-left:100px">VC unique Id:&nbsp;${vcUniqueId}</label> </td><td valign="top"></td><td colspan="3"> </td>

                        </tr>
                        <tr><td width="15%" valign="top" class="right">VC Name</td> <!--onchange="submitForm('baselineDetails',this.value,5)" >-->
                        <td>
                                <html:select property="ovcId" styleClass="fieldcellinput" styleId="ovcId" onchange="setActionName('baselineDetails'); forms[0].submit()" >
                                    <html:option value=""> </html:option>
                                    <logic:present name="ovcListInFollowup">
                                    <logic:iterate name="ovcListInFollowup" id="ovc">
                                        <html:option value="${ovc.ovcId}">${ovc.firstName} ${ovc.lastName}</html:option>
                                    </logic:iterate>
                                    </logic:present>
                                </html:select> 
                            <label style="margin-left:32px">Current age</label>&nbsp;<html:text property="age" styleId="age" styleClass="smallfieldcellinput" style="width:70px;" readonly="true"  />
                            </td><td>
                                
                            </td>
                            <td> </td><td></td>
                        </tr>

                       
                            <tr>
                            <td valign="top" class="right" style=" height: 25px;"><br/>Address: </td>
                              <td>
                                  <html:textarea property="address" rows="3" cols="80" styleClass="fieldcellinput" styleId="address" readonly="true" ></html:textarea>
                              <label style="margin-left:72px">Sex</label>&nbsp;<html:text property="gender" styleClass="smallfieldcellinput" styleId="gender" readonly="true" /></td><td> &nbsp; </td><td> </td>
                                <td> </td>
                                <td> </td>
                            </tr>
                            <tr>
                                <td valign="top" class="right">

                                    Caregiver Name</td>
                                <td>
                                    <html:text property="combinedCaregiverName" styleClass="fieldcellinput" styleId="combinedCaregiverName" readonly="true" />
                                    <label >Caregiver phone:</label>&nbsp;<html:text property="caregiverPhone" styleClass="smallfieldcellinput" styleId="baselineCaregiverPhone" readonly="true" /></td>

                                
                                <td> </td>
                              
                                <td>
                              </td>
                              <td></td><td></td>
                            </tr>                           
                        </table>
                      </fieldset>


					  </td>
                      </tr>
                      
                  </table></td>
                </tr>

                <tr><td colspan="2">&nbsp; </td></tr>

                <tr><td>
                        <fieldset class="fieldset" style="width:710px;">
                            <legend >Child information Update <span class="style1">(Update any dynamic information of the child which has changed )</span></legend>
                    <table >
                <tr>
                  <td height="46" valign="top">
                      <table  border="0" cellpadding="0" cellspacing="0">
                          
                          <tr><td colspan="2">
                                  <fieldset>
                        <legend class="fieldset"> <span class="style1">Personal information</span>  </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td style="width:690px;" height="46">
                          <table width="100%" class="regsitertable">
                              <tr><td>Date of survey </td><td><html:text property="dateOfSurvey" styleId="dateOfSurvey" styleClass="smallfieldcellinput" onchange="setActionName('followupDetails'); forms[0].submit()" readonly="true"/>&nbsp;(mm/dd/yyyy)</td>
                                  <td colspan="2"> </td></tr>
                         <%-- <tr>
                            <td >Current Address:</td>
                          <td><html:textarea property="updatedAddress" rows="3" cols="40" styleClass="fieldcellinput" styleId="updatedAddress"></html:textarea></td>
                          <td >Current age: </td><td ><html:text property="updatedAge" styleId="updatedAge" styleClass="smallfieldcellinput" style="width:50px"/> 
                          <label style=" margin-left: 5px">Age unit:</label> <html:text property="updatedAgeUnit" styleId="updatedAgeUnit" styleClass="smallfieldcellinput"/> </td>
                          </tr>--%>
                                                
                         <tr>
                            <td >Last known HIV status</td>
                            <td> 
                                <html:text property="childCurrentHivStatus" styleId="childCurrentHivStatus" styleClass="fieldcellinput"/>
                                
                            </td><td>&nbsp;&nbsp;&nbsp;&nbsp;New HIV status </td><td><html:select property="childUpdatedHivStatus" styleClass="fieldcellinput" styleId="childUpdatedHivStatus" onchange="activateEnrolledInCare(this.value)" >
                                      <html:option value="select">select...</html:option>
                                      <html:optionsCollection property="hivStatusList" label="hivStatusName" value="hivStatusCode" />
                                  </html:select>  </td>
                          </tr>
                          <tr>
                            <td >Child enrolled in care?</td>
                            <td> 
                                <html:select property="enrolledInCare" styleClass="smallfieldcellinput" styleId="enrolledInCare" style="width:50px" onchange="activateReferralList(this.value)" disabled="true">
                                      <html:option value="No">No</html:option>
                                      <html:option value="Yes">Yes</html:option>
                                </html:select>
                                
                            </td><td >Child enrolled on ART?</td>
                            <td>   
                                <html:select property="enrolledOnArt" styleClass="smallfieldcellinput" styleId="enrolledOnArt" style="width:50px" onchange="activateReferralList(this.value)" disabled="true">
                                      <html:option value="No">No</html:option>
                                      <html:option value="Yes">Yes</html:option>
                                </html:select>
                            </td>
                          </tr>
                          <tr>
                            <td >Facility child is enrolled</td>
                            <td colspan="3"> 
                                <html:select property="vcFacilityId" styleClass="fieldcellinput" styleId="vcFacilityId" style="width:524px;" disabled="true">
                                      <html:option value="select">Select</html:option>
                                      <html:optionsCollection property="referralDirectoryList" label="facilityName" value="facilityId"/>
                                  </html:select>
                                
                            </td>
                          </tr>
                          
                      </table></td>
                   
</table></fieldset></td></tr></table>                  </td>
                </tr>


                <tr>
                  <td valign="top"><table style="width:690px;" border="0" cellpadding="0" cellspacing="0"><tr><td>
                                  <fieldset>
                        <legend class="fieldset">Child Registration and Education </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td style="width:690px;" height="48">
                          <table width="100%" border="1" bordercolor="#D7E5F2" class="regsitertable">
                        
                        <tr>
                          <td class="right">Does Child have birth certificate currently ? </td>
                              <td >
                                  <html:select property="updatedBirthCertStatus" styleId="updatedBirthCertStatus" styleClass="shortfieldcellinput">
                                        <html:option value=" "> </html:option>
                                        <html:option value="Yes">Yes</html:option>
                                        <html:option value="No">No</html:option>
                                   </html:select>
                     </td>
                                                  <td class="right">is child currently in school? </td>
                                                  <td>
                                                      <html:select property="updatedSchoolStatus" styleId="updatedSchoolStatus" onchange="childInSchool(this.value)" styleClass="shortfieldcellinput">
                                   <html:option value=" "> </html:option>
                                   <html:option value="Yes">Yes</html:option>
                                   <html:option value="No">No</html:option>
                                   </html:select>
                                  
                              </td>
                            </tr>
                        
                        <tr>
                          <td class="right">School Name:</td>
                          <td><html:select styleClass="fieldcellinput" property="updatedSchoolName" styleId="updatedSchoolName" disabled="${schoolDisabled}" >
                                      <html:option value=" "> </html:option>
                                      <html:optionsCollection property="schoolList" label="name" value="name" />
                                      <%--<logic:iterate id="school" name="schools">
                                      <html:option value="${school.name}">${school.name}</html:option>
                                      </logic:iterate>--%>

                                  </html:select>
                              </td>
                              <td>Class </td>
                              <td>
                                  <html:text property="updatedClass" styleClass="shortfieldcellinput" styleId="updatedClass"/>
                              </td>
                        </tr>
                       </table></td>
                      </tr>
                  </table>
                  </fieldset></td></tr></table></td>
                </tr>
                    </table>
                </fieldset>
                    </td></tr>

                <tr>
                    <td height="104" valign="top" colspan="2"><table width="727" border="0" cellpadding="0" cellspacing="0"><tr><td><fieldset>
                        <legend class="fieldset">Caregiver Information Update  </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="727" height="104"><table width="100%" class="regsitertable">
                        <tr>
                          <td width="22%" valign="top" class="right">Caregiver name: </td>
                          <td colspan="2">
                              <html:select property="caregiverId" styleClass="fieldcellinput"  styleId="caregiverId" onchange="setActionName('caregiverDetails'); forms[0].submit()">
                                      <html:option value=""> </html:option>
                                      <html:optionsCollection property="caregiverList" label="caregiverFullName" value="caregiverId" />

                                  </html:select>
                              
                          </td>
                        </tr>
                        <tr><td > </td> <td colspan="5"><span id="caregiverSurnameNameSpan" style="position: absolute"> </span> </td></tr>
						
                        <tr>
                          <td class="right">Sex:</td>
                          <td colspan="2">
						  <html:select property="updatedCaregiverGender" styleClass="smallfieldcellinput" styleId="caregiverGender">
                           <html:option value=" "></html:option>
                           <html:option value="Male">Male</html:option>
                            <html:option value="Female">Female</html:option>
                          </html:select>						  
                          </td>
                          <td class="right">&nbsp;&nbsp;Age in years: </td>
                          <td colspan="2"><html:text property="updatedCaregiverAge" styleClass="smallfieldcellinput" styleId="caregiverAge"/></td>
                        </tr>
                        <%--<tr>
                          <td class="right">Address:</td>
                          <td colspan="5">
                              <html:textarea property="updatedCaregiverAddress" rows="3" cols="40" styleClass="fieldcellinput" styleId="caregiverAddress" readonly="false"></html:textarea></td>
                        </tr>--%>
                        <tr>
                          <td class="right">Phone:</td>
                          <td colspan="2"><html:text property="updatedCaregiverPhone" styleClass="fieldcellinput" styleId="caregiverPhone"/></td>
                          <td class="right">&nbsp;&nbsp;Occupation:</td>
                          <td colspan="2"><html:text property="updatedCaregiverOccupation" styleClass="smallfieldcellinput" styleId="caregiverOccupation"/></td>
                        </tr>
                        <tr>
                            <td >Last known HIV status</td>
                            <td colspan="2">
                                <html:text property="cgiverCurrentHivStatus" styleClass="fieldcellinput" styleId="cgiverCurrentHivStatus" readonly="true"/>
                            </td>
                            <td>New HIV status</td>
                            <td colspan="2">
                                
                                 <html:select property="cgiverHivStatus" styleClass="fieldcellinput" styleId="cgiverUpdatedHivStatus" onchange="activateCaregiverEnrolledInCare(this.value)" >
                                      <html:option value="select">select...</html:option>
                                      <html:optionsCollection property="caregiverHivStatusList" label="hivStatusName" value="hivStatusCode" />
                                  </html:select> 
                            </td>
                        </tr>
                        <tr>
                            <td >Enrolled in care?</td><td colspan="2"><html:select property="cgiverEnrolledInCare" styleClass="smallfieldcellinput" styleId="cgiverEnrolledInCare" onchange="activateCaregiverReferralList(this.value)" disabled="true">
                                      <html:option value="No">No</html:option>
                                      <html:option value="Yes">Yes</html:option>
                                  </html:select>
                            </td>
                            <td >Enrolled on ART?</td>
                            <td>   
                                <html:select property="cgiverEnrolledOnArt" styleClass="smallfieldcellinput" styleId="cgiverEnrolledOnArt" style="width:50px" onchange="activateCaregiverReferralList(this.value)" disabled="true">
                                      <html:option value="No">No</html:option>
                                      <html:option value="Yes">Yes</html:option>
                                </html:select>
                            </td>
                          </tr>
                              
                            <tr>
                                <td class="right">Facility enrolled</td>
                                <td colspan="5"> <html:select property="cgiverFacilityId" styleClass="fieldcellinput" styleId="cgiverFacilityId" style="width:524px;" disabled="true">
                                      <html:option value="select">Select</html:option>
                                      <html:optionsCollection property="referralDirectoryList" label="facilityName" value="facilityId"/>
                                  </html:select></td>
                                
                            </tr>                      
                   
                        <tr>
                            <td class="right">Relationship to child</td>
                            <td colspan="2"><html:select property="updatedCaregiverRelationship" styleId="updatedCaregiverRelationship" styleClass="fieldcellinput">
                                      <html:option value=" "> </html:option>
                                      <html:option value="Father">Father</html:option>
                                      <html:option value="Mother">Mother</html:option>
                                      <html:option value="Aunt/Uncle">Aunt/Uncle</html:option>
                                      <html:option value="Sister/Brother">Sister/Brother</html:option>
                                      <html:option value="Grand parent">Grand parent</html:option>
                                      <html:option value="Guardian">Guardian</html:option>
                                      <html:option value="Neighbour">Neighbour </html:option>
                                      <html:option value="Friend">Friend </html:option>
                                      <html:option value="Social worker">Social worker</html:option>
                                      <html:option value="Nuclear family member">Nuclear family member</html:option>

                                  </html:select> </td>
                          <td>Other relative </td>
                          <td colspan="2"><html:text property="updatedOtherCaregiverRelationship" styleClass="fieldcellinput" styleId="updatedOtherCaregiverRelationship" disabled="true"/></td>
                          
                        </tr>
                        </table></td>
                    </tr>
                  </table></fieldset></td></tr></table>                  </td>
                </tr>


                <tr>
                  <td height="83" valign="top"><table width="727" border="0" cellpadding="0" cellspacing="0"><tr><td>


				  <fieldset>
                        <legend class="fieldset">Child Status Index Scores </legend>
                        
                        
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
                              <td colspan="5" bgcolor="#F0F0F0"><strong>Education and skills </strong></td>
                              </tr>
                            <tr>
                              <td>Abuse and exploitation </td>
                              <td align="center"><html:radio property="csiFactor9" styleId="csiFactor9_4" value="4" /></td><td align="center"><html:radio property="csiFactor9" styleId="csiFactor9_3" value="3" /></td><td align="center"><html:radio property="csiFactor9" styleId="csiFactor9_2" value="2" /></td><td align="center"><html:radio property="csiFactor9" styleId="csiFactor9_1" value="1" /></td>
                              <td>Development and performance</td>
                              <td align="center"><html:radio property="csiFactor7" styleId="csiFactor7_4" value="4" /></td>
                              <td align="center"><html:radio property="csiFactor7" styleId="csiFactor7_3" value="3" /></td>
                              <td align="center"><html:radio property="csiFactor7" styleId="csiFactor7_2" value="2" /></td>
                              <td align="center"><html:radio property="csiFactor7" styleId="csiFactor7_1" value="1" /></td>

                            </tr>
                            <tr>
                              <td>Legal protection </td>
                              <td align="center"><html:radio property="csiFactor10" styleId="csiFactor10_4" value="4" /></td><td align="center"><html:radio property="csiFactor10" styleId="csiFactor10_3" value="3" /></td><td align="center"><html:radio property="csiFactor10" styleId="csiFactor10_2" value="2" /></td><td align="center"><html:radio property="csiFactor10" styleId="csiFactor10_1" value="1" /></td>
                              <td>Education and work </td>
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
                      </table>

                        </fieldset>

				  </td></tr></table></td>
                </tr>

                <tr>
                  <td height="47" valign="top"><table width="727" border="0" cellpadding="0" cellspacing="0"><tr><td><fieldset>
                        <legend class="fieldset">Child withdrawn from the Program </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="727" valign="top">
					  <table width="100%" class="regsitertable">
                                              <tr><td width="30%">Child withdrawn from the program?</td><td ><select class="shortfieldcellinput" id="childWithdrawn" onchange="childWithdrawnAction(this.value);">
					    <option value=" "></option><option value="No">No</option><option value="Yes">Yes</option></select></td>
                                              <td >&nbsp;&nbsp;If Yes, select reason child is withdrawn</td>
                              <td >
                                <html:select property="reasonWithdrawal" styleId="reasonWithdrawal" disabled="true">
                                    <html:option value=""> </html:option>
                                    <html:option value="ageabove17">Age 18 and above</html:option>
                                    <html:option value="Graduated">Graduated from program</html:option>
                                    <html:option value="transfer">Transfer to other program</html:option>
                                    <html:option value="Migrated">Migrated</html:option>
                                    <html:option value="Loss to follow-up">Loss to follow-up (after 3 months)</html:option>
                                    <html:option value="Known death">Known death</html:option>
                                </html:select>
                              </td>
                                              </tr>
					  </table>
					  <table width="100%" class="regsitertable">
                          
                                            </table></td>
                    </tr>
                  </table>
                  </fieldset></td></tr></table></td>
                </tr>


                <tr>
                            <td width="727" height="27">
                                <table><tr><td width="150"><span style="width:150px; margin-right: 30px;">Completed by:</span>    Name </td><td><html:text property="completedbyName" styleClass="smallfieldcellinput"/> </td><td><label style="margin-left:30px;">Designation</label> </td><td><html:text property="completedbyDesignation" styleClass="smallfieldcellinput"/> </td></tr></table>
                            </td>
                        </tr>
                <tr>
                  <td height="40" align="center" width="727">

				  <%--<html:reset value="New" styleId="newBtn" style="width:70px; height:25px;  margin-right:5px;" />--%>
                                  <html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${followupSaveDisabled}" />
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${followupModifyDisabled}"/><html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${followupModifyDisabled}"/>

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
