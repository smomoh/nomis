<%-- 
    Document   : HouseholdServiceView
    Created on : Jan 12, 2014, 10:13:19 AM
    Author     : Siaka
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
<title>Household service form </title>
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
       document.getElementById("enrolledInCare").disabled = true;
       document.getElementById("enrolledOnART").value="No"
       document.getElementById("enrolledOnART").disabled = false;
       document.getElementById("organizationClientIsReferred").value="select"
       document.getElementById("organizationClientIsReferred").disabled = true;
    }
}
function activateReferralList(value) 
{
    if(value == "Yes") 
    {
        //if(document.getElementById("enrolledOnART").value=="Yes" || document.getElementById("enrolledInCare").value=="Yes")
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
function submitForm(requiredAction,formId)
{
    var SNumber=document.getElementById("serialNo").value
    if(SNumber==null || SNumber=="" || SNumber==" " || SNumber=="  " || SNumber=="   " || SNumber.length < 1)
    return false
    else
    {
        setActionName(requiredAction)
        document.getElementById(formId).submit()
    } 
    return true
}
function childWithdrawnAction(value) {
    if(value == "Yes") 
    {
        document.getElementById("reasonWithdrawal").disabled = false;
        /*document.getElementById("reasonWithdrawal2").disabled = false;
        document.getElementById("reasonWithdrawal3").disabled = false;
        document.getElementById("reasonWithdrawal4").disabled = false;*/
    }
    else 
    {
        /*document.getElementById("reasonWithdrawal").checked = true;
        document.getElementById("reasonWithdrawal2").checked = false;
        document.getElementById("reasonWithdrawal3").checked = false;
        document.getElementById("reasonWithdrawal4").checked = false;*/
        document.getElementById("reasonWithdrawal").value=""
        document.getElementById("reasonWithdrawal").disabled = true;
        /*document.getElementById("reasonWithdrawal2").disabled = true;
        document.getElementById("reasonWithdrawal3").disabled = true;
        document.getElementById("reasonWithdrawal4").disabled = true;*/
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
              <div><jsp:include page="../ServiceReportLinkPage.jsp"/></div>
            </div></td>
          </tr>


      </table></td>
    <td width="13">&nbsp;</td>
      <td width="639" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
	 <td width="30%" height="39" class="homepagestyle">Household Service form </td>
	 <td width="70%"><html:errors/></td>
    </tr>
    <tr>
        <td class="homepagestyle" colspan="2" align="center"><logic:present name="msg"><label style="color:red; font-size: 15px;">${msg}</label></logic:present> </td>
	 
    </tr>

  </table>

         <html:form action="/householdServiceAction" method="post" styleId="hhFormId">
<html:hidden property="actionName" styleId="actionName"/>
<html:hidden property="stateCode" styleId="stateCode" value="${state.state_code}" />
<%--<html:hidden property="lgaCode" styleId="lgaCode" value="${lga.lga_code}" />--%>
<html:hidden property="id" styleId="id" />
<html:hidden property="serviceNo" styleId="serviceNo" />
<html:hidden property="hhUniqueId" styleClass="fieldcellinput" styleId="hhUniqueId" />
     

  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!--DWLayoutTable-->
                <tr>
                  <td width="762" height="28" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="27">
                          <table width="100%">
                              <tr><td colspan="4" align="center"><jsp:include page="../PartnerView.jsp" /></td></tr>
                        		
							<tr>
                            <td width="5%"><span style="color:#333">State:</span></td>
                          <td width="30%">
                              <html:text styleId="stateList" property="state" styleClass="bigfieldcellinput" disabled="true" value="${state.name}" />
                          </td>
                              <td width="5%"><span style="color:#333">LGA:</span></td>
                              <td width="30%"> 
                                  
                                  <html:select property="lgaCode" styleClass="bigfieldcellinput" styleId="lgaCode" onchange="setActionName('cboList');forms[0].submit()">
                                          <logic:iterate name="lgaListInOrgRecords" id="lga">
                                              <html:option value="${lga.lga_code}" >${lga.lga_name}</html:option>
                                          </logic:iterate>
                                      </html:select>
                                  
                                  <%--<input type="text" Class="bigfieldcellinput" id="LgasSelect" disabled="true" value="${lga.lga_name}"/>--%>
                              </td>
                                                        </tr>
                              <tr>
                              <td width="5%"><span style="color:#333">CBO:</span></td>
                              <td width="31%">
                                  
                                  <html:select property="orgCode" styleId="orgCode" styleClass="bigfieldcellinput">
                              <html:option value=""> </html:option>
                                      <logic:iterate id="cbos" name="cboListInOrgRecords" >
                                              <html:option value="${cbos.orgCode}">${cbos.orgName} </html:option>
                                          </logic:iterate>
                                            </html:select>
                              </td>
                              <td width="5%"><span style="color:#333"><jsp:include page="../includes/WardName.jsp" /></span></td>
                              <td width="21%">
                                  <html:text property="ward" styleId="ward" styleClass="bigfieldcellinput" disabled="true" />
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
                      <td width="762" valign="top">





					  <fieldset>
                        <legend class="fieldset">Personal Information  </legend>
                        <table width="100%" class="regsitertable">
                            <tr><td colspan="3"> &nbsp;</td></tr>
                        <tr><td width="15%" valign="top" class="right">Household S/No.</td>
                   
                            <td>
                                <html:text property="serialNo"  styleClass="smallfieldcellinput" styleId="serialNo"  onblur="submitForm('caregiverList','hhFormId')" onkeyup="constructUniqueId(this.value)"/> </td>
                                <td colspan="4"><label id="uniqueIdLabel"> ${hhServiceUniqueId}</label></td>
                        </tr>
                        
                        <tr>
                          <td valign="top" class="right">Caregiver name: </td>
                              <td>
                                  <html:select property="caregiverName" styleClass="fieldcellinput"  styleId="caregiverName" onchange="submitForm('caregiverDetails','hhFormId')">
                                      <html:option value=""> </html:option>
                                      <logic:present name="caregiverList">
                                      <logic:iterate name="caregiverList" id="cgiver">
                                          <html:option value="${cgiver.caregiverId}">${cgiver.caregiverFirstname} ${cgiver.caregiverLastName} </html:option>
                                      </logic:iterate>
                                      </logic:present>
                                  </html:select>  </td>
                              <td>Sex</td><td><html:text property="careiverGender" styleClass="fieldcellinput" styleId="careiverGender" readonly="true" style="width:70px;" /> </td>
                              <td>Age</td><td><html:text property="caregiverAge" styleClass="shortfieldcellinput" styleId="caregiverAge" readonly="true"  /></td>
                            </tr>
                            
                            <tr>
                          <td class="right">Current HIV status </td>
                              <td>
                                  <html:text property="currentHivStatus" styleId="currentHivStatus" styleClass="fieldcellinput" disabled="true"/>
                              </td>
                              <td> </td><td></td>
                              <td> </td><td> </td>
                            </tr>
                       
			</table>
                      </fieldset>


					  </td>
                      </tr>

                  </table></td>
                </tr>


				


                <tr>
                  <td valign="top"><fieldset>
                        <legend class="fieldset">Services </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="17">
                          <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">

			<tr>
                          <td width="20%">Date services provided: </td>
                          <td width="80%" colspan="2">
                              <html:text property="serviceDate" styleId="serviceDate" styleClass="smallfieldcellinput" onchange="setActionName('hhserviceDetails'); forms[0].submit()" readonly="true"/>&nbsp;(mm/dd/yyyy)
                          </td>

                        </tr>
                        </table>
                          <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                              <td width="21%" bgcolor="#CCCCCC" class="right">Health </td>
                              <td colspan="2" bgcolor="#CCCCCC">&nbsp;</td>
                              
                          </tr>
                          <tr>
                              <td rowspan="12">&nbsp;</td>
                            <td width="4%"><html:multibox property="healthServices" value="Health education" styleClass="smallfieldcellselect" styleId="serviceAccessed33"/></td>
                            <td>Health education </td>
                          </tr>
                          <tr>
                            <td width="4%"><html:multibox property="healthServices" value="Treatmen_of_minor_illness" styleClass="smallfieldcellselect" styleId="insecticide"/></td>
                            <td>Treatment of minor illness </td>
                          </tr>
                          <tr>
                            <td width="4%"><html:multibox property="healthServices" value="Insecticides treated bed net" styleClass="smallfieldcellselect" styleId="insecticide"/></td>
                            <td>Insecticides treated bed nets </td>
                          </tr>
			   <tr>
                            <td width="4%"><html:multibox property="healthServices" value="Water treatment" styleClass="smallfieldcellselect" styleId="serviceAccessed32"/></td>
                            <td>Water treatment </td>
                          </tr>
                          <tr>
                            <td><html:multibox property="nutritionalServices" value="Water-Sanitation and Hygiene (WASH)" styleClass="smallfieldcellselect" styleId="wash"/></td>
                            <td>Water, Sanitation and Hygiene (WASH) </td>
                          </tr>
                          <tr>

                              <td width="4%"><html:multibox property="healthServices" value="Community HIV services (HTC/PMTCT)" styleClass="smallfieldcellselect" styleId="communityhivservices"/></td>
                            <td>Community HIV services (HTS/PMTCT)</td>
                          </tr>
                          <tr>
                          <tr>
                              
                              <td width="4%"><html:multibox property="healthServices" value="HIV services referral (HTC/PMTCT)" styleClass="smallfieldcellselect" styleId="hivHealthreferral"/></td>
                            <td>HIV services referral (HTS/PMTCT/ART)</td>
                          </tr>                         
                          <tr>

                              <td width="4%"><html:multibox property="healthServices" value="HIV care and support" styleClass="smallfieldcellselect" styleId="accessforHIV"/></td>
                            <td>HIV care and support </td>
                          </tr>
                          <tr>

                              <td width="4%"><html:multibox property="healthServices" value="Community TB symptom screening" styleClass="smallfieldcellselect" styleId="communityTBsymptomscreening"/></td>
                            <td>Community TB symptom screening </td>
                          </tr>
                          <tr>

                              <td width="4%"><html:multibox property="healthServices" value="TB services referral (Diagnosis-DOTS)" styleClass="smallfieldcellselect" styleId="tbservicesreferral"/></td>
                            <td>TB services referral (Diagnosis, DOTS) </td>
                          </tr>
                          <tr>
                              
                              <td><html:multibox property="healthServices" value="Health referral" styleClass="smallfieldcellselect" styleId="healthreferral"/></td>
                            <td>Health referral</td>
                          </tr>
                          <%--<tr>
                              <td><input type="checkbox" name="chkHealthOther" class="smallfieldcellselect" id="growthOther"/>
                                  </td>
                            <td>Other (specify) <html:text property="healthOther" styleClass="fieldcellinput" styleId="serviceAccessed37"/></td>
                          </tr>--%>
                        </table>

                        <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                            <td width="21%" bgcolor="#CCCCCC" class="right">Nutrition </td>
                            <td width="4%" colspan="2" bgcolor="#CCCCCC">&nbsp;</td>
                            
                          </tr>
                          <tr>
                            <td rowspan="11">&nbsp;</td>
                            <td width="4%"><html:multibox property="nutritionalServices" value="Nutrition education and counselling" styleClass="smallfieldcellselect" styleId="serviceAccessed21"/></td>
                            <td width="75%">Nutrition education and counselling</td>
                          </tr>
                          <tr>
                            
                            <td><html:multibox property="nutritionalServices" value="Vitamin A-Zinc and Iron suplement" styleClass="smallfieldcellselect" styleId="serviceAccessed22"/></td>
                            <td>Vitamin A, Zinc, Iron & supplement </td>
                          </tr>
                          <tr>
                            
                            <td><html:multibox property="nutritionalServices" value="Food & nutritional supplements" styleClass="smallfieldcellselect" styleId="serviceAccessed22"/></td>
                            <td>Food and Nutritional supplements </td>
                          </tr>
                          
                          <tr>
                            <td><html:multibox property="nutritionalServices" value="HHnutritionsssessmentCounsellingandsupport(NACS)services" styleClass="smallfieldcellselect" styleId="nacs"/></td>
                            <td>HH nutrition assessment, counselling and support (NACS) services</td>
                          </tr>
                          <tr>
                            <td><html:multibox property="nutritionalServices" value="Infant and young child feeding services" styleClass="smallfieldcellselect" styleId="infantFeeding"/></td>
                            <td>Support group (IYCF)</td>
                          </tr>
                          <tr>
                            <td><html:multibox property="nutritionalServices" value="Home garden" styleClass="smallfieldcellselect" styleId="homegarden"/></td>
                            <td>Home gardening support/training</td>
                          </tr>
                          <tr>
                            <td><html:multibox property="nutritionalServices" value="Food_preservation" styleClass="smallfieldcellselect" styleId="fooddemonstration"/></td>
                            <td>Food preservation and storage</td>
                          </tr>
                          <tr>
                            <td><html:multibox property="nutritionalServices" value="Food demonstration" styleClass="smallfieldcellselect" styleId="fooddemonstration"/></td>
                            <td>Food demonstration/preparation</td>
                          </tr>
                          <tr>
                            <td><html:multibox property="nutritionalServices" value="Household food security training" styleClass="smallfieldcellselect" styleId="trainingonhousehold"/></td>
                            <td>Household food security training</td>
                          </tr>
                          <tr>
                            <td><html:multibox property="nutritionalServices" value="Food and Nutrition training" styleClass="smallfieldcellselect" styleId="foodandnutritiontraining"/></td>
                            <td>Food and Nutrition training</td>
                          </tr>
                          <tr>
                              <td><html:multibox property="nutritionalServices" value="Nutrition referral" styleClass="smallfieldcellselect" styleId="serviceAccessed36"/></td>
                            <td>Referral </td>
                          </tr>
                          </table>

                         <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                            <td width="21%" bgcolor="#CCCCCC" class="right">Shelter and care</td>
                            <td width="4%" colspan="2" bgcolor="#CCCCCC"></td>
                            
                          </tr>
                          <tr>
                            <td rowspan="3">&nbsp;</td>
                            <td width="4%"><html:multibox property="shelterAndCareServices" value="Provision/repair of accommodation" styleClass="smallfieldcellselect" styleId="serviceAccessed61"/></td>
                            <td width="75%">Provision/repair of accommodation </td>
                          </tr>
                          <tr>
                              <td><html:multibox property="shelterAndCareServices" value="Clothing support" styleClass="smallfieldcellselect" styleId="serviceAccessed63"/></td>
                            <td>Clothing support </td>
                          </tr>
                          <tr>
                              <td><html:multibox property="shelterAndCareServices" value="Shelter referral" styleClass="smallfieldcellselect" styleId="serviceAccessed63"/></td>
                            <td>Referral </td>
                          </tr>                    
                        </table>




                        <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                            <td width="21%" bgcolor="#CCCCCC" class="right">Education</td>
                            <td colspan="2" bgcolor="#CCCCCC"></td>
                            
                          </tr>
                          <tr>
                            <td rowspan="3">&nbsp;</td>
                            <td><html:multibox property="educationalServices" value="Education awareness and engagement" styleClass="smallfieldcellselect" styleId="holisticscholarship"/></td>
                            <td>Child education awareness and sensitization</td>
                          </tr>
                          <%--<tr>
                            <td><html:multibox property="educationalServices" value="Sensitization for child school enrolment/re-enrolment " styleClass="smallfieldcellselect" styleId="schoolenrolmentsensitization"/></td>
                            <td>Sensitization for child school enrolment/re-enrolment </td>
                          </tr>--%>
                          <tr>
                            <td width="4%"><html:multibox property="educationalServices" value="Education referral" styleClass="smallfieldcellselect" styleId="serviceAccessed41"/></td>
                            <td width="75%">Referral </td>
                          </tr>
                                                    
                        </table>

                        <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                        <tr>
                          <td width="21%" bgcolor="#CCCCCC" class="right">Psychosocial Support </td>
                          <td width="75%" colspan="2" bgcolor="#CCCCCC"> </td>
                        </tr>
                        <tr>
                          <td rowspan="4">&nbsp;</td>
                          <td width="4%"><html:multibox property="psychosocialSupportServices" value="Counselling support" styleClass="smallfieldcellselect" styleId="serviceAccessed11"/></td>
                          <td width="75%">Counselling support </td>
                        </tr>
                        <tr>
                          
                          <td><html:multibox property="psychosocialSupportServices" value="Parenting skills" styleClass="smallfieldcellselect" styleId="serviceAccessed12"/></td>
                          <td>Parenting skills </td>
                        </tr>
                        <tr>
                            <td><html:multibox property="psychosocialSupportServices" value="Caregivers forum" styleClass="smallfieldcellselect" styleId="serviceAccessed13"/></td>
                          <td>Caregivers forum </td>
                        </tr>
                        <tr>
                            <td><html:multibox property="psychosocialSupportServices" value="Psychosocial referral" styleClass="smallfieldcellselect" styleId="serviceAccessed13"/></td>
                          <td>Referral </td>
                        </tr>
                      </table>

                        <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                            <td width="21%" bgcolor="#CCCCCC" class="right">Protection </td>
                            <td width="75%" colspan="2" bgcolor="#CCCCCC"></td>
                            
                          </tr>
                          <tr>
                            <td rowspan="5" >&nbsp;</td>
                            <td width="4%"><html:multibox property="protectionServices" value="Legal services" styleClass="smallfieldcellselect" styleId="serviceAccessed51"/></td>
                            <td width="75%">Access to legal services </td>
                          </tr>
                          <tr>
                              
                              <td><html:multibox property="protectionServices" value="Succession planning" styleClass="smallfieldcellselect" styleId="serviceAccessed53"/></td>
                            <td>Succession planning </td>
                          </tr>
                          <tr>
                            
                            <td><html:multibox property="protectionServices" value="Birth registration" styleClass="smallfieldcellselect" styleId="serviceAccessed52"/></td>
                            <td>Birth registration awareness</td>
                          </tr>
                          <tr>
                            
                            <td><html:multibox property="protectionServices" value="Awareness of gender issues" styleClass="smallfieldcellselect" styleId="serviceAccessed52"/></td>
                            <td>Awareness of gender issues/norms </td>
                          </tr>
                          <tr>
                            
                            <td><html:multibox property="protectionServices" value="Protection referral" styleClass="smallfieldcellselect" styleId="serviceAccessed52"/></td>
                            <td>Referral </td>
                          </tr>
                        </table>




                        <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                            <td width="20%" bgcolor="#CCCCCC" class="right">Economic Strengthening </td>
                            <td width="75%" colspan="2" bgcolor="#CCCCCC"> </td>
                          </tr>
                          <tr>
                            <td rowspan="10"></td>
                            <td width="4%"><html:multibox property="economicStrengtheningServices" value="micro finance support" styleClass="smallfieldcellselect" styleId="microfinancesupport"/></td>
                            <td>Micro finance support(Grants, savings and loans) </td>
                          </tr>
                          <tr>
                                                       
                          </tr>
                          <tr>
                            <td width="4%"><html:multibox property="economicStrengtheningServices" value="Vocational/apprenticeship training" styleClass="smallfieldcellselect" styleId="Vocationalapprenticeshiptraining"/></td>
                            <td>Vocational/apprenticeship training </td>
                          </tr>

                          <tr>
                              <td width="4%"><html:multibox property="economicStrengtheningServices" value="Livelihood opportunity" styleClass="smallfieldcellselect" styleId="livelihoodopportunity"/></td>
                            <td>Livelihood support/opportunity</td>
                          </tr>
                          <tr>
                              <td width="4%"><html:multibox property="economicStrengtheningServices" value="Financial Education" styleClass="smallfieldcellselect" styleId="Financial Education"/></td>
                            <td>Financial Education</td>
                          </tr>
                          <tr>
                              <td width="4%"><html:multibox property="economicStrengtheningServices" value="Linkage to public sector scheme(s)" styleClass="smallfieldcellselect" styleId="serviceAccessed73"/></td>
                            <td>Linkage to public sector scheme(s)</td>
                          </tr>
                          <tr>
                              <td width="4%"><html:multibox property="economicStrengtheningServices" value="Linkage to cash transfer scheme" styleClass="smallfieldcellselect" styleId="serviceAccessed73"/></td>
                            <td>Linkage to cash transfer scheme</td>
                          </tr>
                          <tr>
                              <td width="4%"><html:multibox property="economicStrengtheningServices" value="Private sector linkage(s)" styleClass="smallfieldcellselect" styleId="Privatesectorlinkage(s)"/></td>
                            <td>Private sector linkage(s)</td>
                          </tr>
                          <tr><!--value="Savings and Internal Lending Community (SILC)"-->
                              <td width="4%"><html:multibox property="economicStrengtheningServices" value="SILC" styleClass="smallfieldcellselect" styleId="silc"/></td>
                            <td>Savings and Internal Lending Community (SILC)</td>
                          </tr>
                          <tr>
                              <td width="4%"><html:multibox property="economicStrengtheningServices" value="Economic strenghtening referral" styleClass="smallfieldcellselect" styleId="Economicstrenghteningreferral"/></td>
                            <td>Referral</td>
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
                        <legend class="fieldset">Caregiver HIV status update <span class="style1"> </span>  </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="727" height="46" valign="top">
					                                            
		<table width="100%" class="regsitertable">
                        <tr>
                            
                             <td>Last known HIV status
                                 <html:text property="currentHivStatus" styleId="currentHivStatus" styleClass="fieldcellinput" style="margin-left: 25px" disabled="true"/>
                            </td>
                             <td>New HIV status
                                 <html:select property="newHivStatus" styleClass="fieldcellinput" styleId="newHivStatus" style="margin-left:20px" onchange="activateEnrolledInCare(this.value)" >
                                      <html:option value="select">select...</html:option>
                                      <html:optionsCollection property="hivStatusList" label="hivStatusName" value="hivStatusCode" />
                                  </html:select> 
                            </td>
                          </tr>
                         <tr>
                            <td>
                                 Caregiver enrolled in care?<html:select property="enrolledInCare" styleClass="smallfieldcellinput" styleId="enrolledInCare" onchange="activateReferralList(this.value)" disabled="true">
                                      <html:option value="No">No</html:option>
                                      <html:option value="Yes">Yes</html:option>
                                </html:select>
                            </td>
                            <td>Enrolled on ART?<html:select property="enrolledOnART" styleClass="smallfieldcellinput" styleId="enrolledOnART" style="margin-left:9px" onchange="activateReferralList(this.value)" disabled="true">
                                      <html:option value="No">No</html:option>
                                      <html:option value="Yes">Yes</html:option>
                                  </html:select>
                            </td>
                         </tr>
                            
                            <tr>
                                <td class="right" colspan="2">Facility caregiver enrolled
                                    <html:select property="organizationClientIsReferred" styleClass="fieldcellinput" styleId="organizationClientIsReferred"  style="width:500px; margin-left:7px;" disabled="true">
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
                        <legend class="fieldset">Caregiver follow-up information <!--<span class="style1">(Tick reason child is withdrawn)</span>-->  </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="727" height="30" valign="top">
					  <table width="100%" class="regsitertable">
                                              <tr><td width="30%">Caregiver withdrawn from program</td><td ><select class="shortfieldcellinput" id="childWithdrawn" onChange="childWithdrawnAction(this.value);">
					    <option value="No">No</option><option value="Yes">Yes</option></select></td>
                                            <td width="28%">&nbsp;&nbsp;Reason caregiver is withdrawn</td><td>
                                                <html:select property="reasonWithdrawal" styleId="reasonWithdrawal" disabled="true" style="width:200px">
                                                    <html:option value=""> </html:option>
                                                    <html:option value="Known death">Known death</html:option>
                                                    <html:option value="Migrated">Migrated</html:option>
                                                    <html:option value="Loss to follow-up">Loss to follow-up (after 3 months)</html:option>
                                                    <html:option value="Graduated">Graduated from program</html:option>
                                                    <html:option value="transfer">Transfer to other program</html:option>
                                                    <html:option value="voluntaryWithdrawal">Voluntary withdrawal</html:option>
                                                </html:select></td>
                                              </tr>
					  </table>
					 </td>
                    </tr>
                  </table>
                  </fieldset>
                    </td></tr>
                
                
                <tr>
                    <td><table><tr><td >Name of Volunteer/Service provider</td><td ><html:text property="volunteerName" styleClass="fieldcellinput" styleId="volunteerName"/></td><td >Designation</td><td ><html:text property="volunteerDesignation" styleClass="fieldcellinput" styleId="volunteerDesignation"/></td>
                            </tr></table></td>

                </tr>
                <tr>
				<td height="40" align="center">
                            
                            <html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${hhServiceSaveBtnDisabled}"/>
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${hhServiceModifyBtnDisabled}"/><html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${hhServiceModifyBtnDisabled}"/>

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
  </body>
</html>
