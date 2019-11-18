<%-- 
    Document   : HivStatusUpdate
    Created on : Apr 12, 2016, 9:05:32 PM
    Author     : smomoh
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>HIV Status update </title>
<style type="text/css">
<!--
body {
argin-left: 0px;
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
function MM_swapImgRestore() 
{ //v3.0
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
                $("#dateOfNewStatus").datepicker();
                $("#dateEnrolledOnTreatment").datepicker();
            });	
function activateEnrolledInCare(value)
{
    if(value == "positive") 
    {
        document.getElementById("enrolledInCare").disabled = false;
    }
    else 
    {
       document.getElementById("enrolledInCare").value="No"
       document.getElementById("enrolledInCare").disabled = true;
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
        document.getElementById("organizationClientIsReferred").value="select"
        document.getElementById("organizationClientIsReferred").disabled = true;
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
    var SNumber=document.getElementById("hhSerialNo").value
    if(SNumber==null || SNumber=="" || SNumber==" " || SNumber=="  " || SNumber=="   " || SNumber.length < 1)
    return false
    else
    {
        setActionName(requiredAction)
        document.getElementById(formId).submit()
    } 
    return true
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
                <div><jsp:include page="../Navigation/HivInformationLink.jsp"/></div>
            </div></td>
          </tr>
          <tr>
            <td height="30" valign="top">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <%--<tr>
                <td width="180" height="30"><img src="images/reports.jpg" width="231" height="30" /></td>
                    </tr>--%>
            </table></td>
          </tr>
          <tr>
            <td height="60" valign="top"><div style="float: left" id="my_menu2" class="sdmenu" >
              
            </div></td>
          </tr>


      </table></td>
    <td >&nbsp;</td>
      <td width="660" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0">
     <tr>
        <td height="19" class="homepagestyle" align="center" >HIV status update </td>

    </tr>
    <tr>
        <td ><html:errors/></td>
    </tr>

  </table>

         <html:form action="/hivStatusUpdateAction" method="post" styleId="hhFormId">
<html:hidden property="actionName" styleId="actionName"/>
<html:hidden property="stateCode" styleId="stateCode" value="${state.state_code}" />
<html:hidden property="hhUniqueId" styleClass="fieldcellinput" styleId="hhUniqueId" />
     

  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!--DWLayoutTable-->
                <tr>
                  <td width="650" height="28" valign="top">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="650" height="27">
                          <table width="100%">
                              <tr><td colspan="4" align="center"><jsp:include page="../PartnerView.jsp" /></td></tr>
                        		<tr><td colspan="4"> &nbsp;</td></tr>
			<tr>
                            <td width="5%"><span style="color:#333">State:</span></td>
                          <td width="30%">
                              <html:text styleId="stateName" property="stateName" styleClass="bigfieldcellinput" disabled="true" value="${state.name}" />
                          </td>
                              <td width="5%"><span style="color:#333">LGA:</span></td>
                              <td width="30%"> 
                                  <html:select property="lgaCode" styleClass="bigfieldcellinput" styleId="lgaCode" onchange="setActionName('cboList');forms[0].submit()">
                                      <logic:iterate name="lgaListInOrgRecords" id="lga">
                                          <html:option value="${lga.lga_code}" >${lga.lga_name}</html:option>
                                      </logic:iterate>
                                  </html:select>
                                  <%--<html:select property="lgaCode" styleClass="bigfieldcellinput" styleId="lgaCode" onchange="setActionName('cboList');forms[0].submit()">
                                      <html:option value=""></html:option>
                                      <html:optionsCollection name="HivStatusUpdateForm" property="lgaList" label="lga_name" value="lga_code" />
                                      
                                      </html:select>--%>
                                  
                                  
                              </td>
                            </tr>
                              <tr>
                              <td width="5%"><span style="color:#333">CBO:</span></td>
                              <td width="31%">
                                  <html:select property="orgCode" styleId="orgCode" styleClass="bigfieldcellinput" style="width:250px;" onchange="submitForm('wardList');">
                                      <html:option value=""> </html:option>
                                      <logic:iterate id="cbos" name="cboListInOrgRecords">
                                              <html:option value="${cbos.orgCode}">${cbos.orgName} </html:option>
                                          </logic:iterate>
                                      </html:select>
                                  <%--<html:select property="orgCode" styleId="orgCode" styleClass="bigfieldcellinput" onchange="setActionName('cboList');forms[0].submit()">
                              <html:option value=""> </html:option>
                              <html:optionsCollection name="HivStatusUpdateForm" property="cboList" label="orgName" value="orgCode" />
                                      
                                            </html:select>--%>
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
                  <td height="140" valign="top">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="120" valign="top">

					  <fieldset>
                        <legend class="fieldset">Personal Information  </legend>
                        <table width="100%" class="regsitertable">
                        <tr><td width="23%" class="right">HH Serial number</td>
                            <td><html:text property="hhSerialNo" styleId="hhSerialNo" styleClass="smallfieldcellinput" style="width:50px;" onkeyup="constructUniqueId(this.value)" onblur="submitForm('beneficiaryList','hhFormId')"/>
                               
                                <label id="uniqueIdLabel" >${hsuHhUniqueId} </label></td>
                            <td>Beneficiary type</td>
                            <td><html:select property="beneficiaryType" styleId="beneficiaryType" styleClass="smallfieldcellinput" onchange="submitForm('beneficiaryList','hhFormId')">
                                    <html:option value="ovc" >OVC</html:option>
                                    <html:option value="caregiver" >Caregiver</html:option>
                                </html:select></td>
                            <%--<td class="right">VC Unique Id: </td><td><label id="uniqueIdLabel" style="color:green;" >${vcUniqueId} </label></td>--%>
                                
                        </tr>
                        <tr><td width="15%" class="right"><logic:present name="beneficiaryType">${beneficiaryType}</logic:present> Name</td>
                        <td>
                                <html:select property="beneficiaryId" styleClass="fieldcellinput" styleId="beneficiaryId" onchange="submitForm('baselineDetails','hhFormId')" >
                                    <html:option value=""> </html:option>
                                    <logic:present name="ovcBeneficiaryList">
                                        <html:optionsCollection name="HivStatusUpdateForm" property="beneficiaryList" label="fullName" value="ovcId" />
                                    </logic:present>
                                    <logic:present name="caregiverBeneficiaryList">
                                        <html:optionsCollection name="HivStatusUpdateForm" property="beneficiaryList" label="caregiverFullName" value="caregiverId" />
                                    </logic:present>
                                </html:select>

                            </td><td> Unique Id</td><td><label id="uniqueIdLabel" style="color:green;" >${beneficiaryId} </label></td>
                        </tr>
                        
                        <tr>
                          <td valign="top" class="right">Age: </td>
                              <td>
                                  
                                    <html:text property="age" styleClass="shortfieldcellinput" styleId="age" readonly="true"  />
                                    &nbsp;&nbsp;Age unit:&nbsp;<html:text styleClass="smallfieldcellinput" property="ageUnit" styleId="ageUnit" style="width:88px;" readonly="true" />

                     </td><td>Sex </td>
                     <td><html:text property="gender" styleClass="smallfieldcellinput" styleId="gender" readonly="true" style="width:120px;" />
                     </td>
                            </tr>

                            <tr>
                            <td valign="top" class="right" style=" height: 25px;"><br/>Address: </td>
                              <td>
                                  <html:textarea property="address" rows="3" cols="80" styleClass="fieldcellinput" styleId="address" readonly="true" ></html:textarea>
                                </td><td>Last known HIV status</td><td><html:text property="currentHivStatus" styleClass="smallfieldcellinput" styleId="currentHivStatus" style="width:120px;" readonly="true" /></td>
                            </tr>
                            <tr><td>New HIV status</td>
                                <td><html:select property="newHivStatus" styleClass="fieldcellinput" styleId="newHivStatus" onchange="activateEnrolledInCare(this.value)" >
                                      <html:option value="select">select...</html:option>
                                      <html:optionsCollection name="HivStatusUpdateForm" property="hivStatusList" label="hivStatusName" value="hivStatusCode" />
                                  </html:select></td>
                                <td class="right" >Date of new status </td>
                                <td>
                                  <html:text property="dateOfNewStatus" styleClass="smallfieldcellinput" styleId="dateOfNewStatus" style="width:120px;" readonly="true"/>
                                </td>
                            </tr>
                            <tr>
                            <td class="right" >Client in care? </td>
                              <td>
                              
                                <html:select property="enrolledInCare" styleClass="fieldcellinput" styleId="enrolledInCare" style="width:60px" onchange="activateReferralList(this.value)">
                                      <html:option value="No">No</html:option>
                                      <html:option value="Yes">Yes</html:option>
                                  </html:select>
                              </td>
                              <td class="right" >Enrolled on ART? </td>
                              <td>
                              
                                  <html:select property="enrolledOnART" styleClass="fieldcellinput" styleId="enrolledOnART" style="width:60px" onchange="activateReferralList(this.value)">
                                      <html:option value="No">No</html:option>
                                      <html:option value="Yes">Yes</html:option>
                                  </html:select>
                              </td>
                            </tr>
                            <tr>
                            <td class="right" > </td>
                            
                                <td colspan="3">If client is enrolled in care, select name of facility/organization from the box below</td>
                                
                            </tr>
                            <tr>
                            <td class="right" > </td>
                            
                                <td colspan="3"><html:select property="organizationClientIsReferred" styleClass="fieldcellinput" styleId="organizationClientIsReferred" style="width:470px;" disabled="true">
                                      <html:option value="select">Select</html:option>
                                      <html:optionsCollection property="referralDirectoryList" label="facilityName" value="facilityId"/>
                                  </html:select></td>
                                
                            </tr>
                            <tr>
                            <td class="right" >Treatment Id </td>
                            <td ><html:text property="treatmentId" styleClass="shortfieldcellinput" styleId="treatmentId" style="width:180px;" maxlength="25"/>
                                      </td>
                                      <td>Date enrolled on treatment</td>
                                      <td><html:text property="dateEnrolledOnTreatment" styleId="dateEnrolledOnTreatment" styleClass="shortfieldcellinput" style="width:109px;" readonly="true"/></td>
                            </tr>
                        </table>
                      </fieldset>
		  </td>
                      </tr>

                  </table></td>
                </tr>
                
                
                
                
                
                
                <tr>
				<td height="40" align="center">
                            
                            <%--<html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${caregiverSaveBtnDisabled}"/>--%>
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${caregiverModifyBtnDisabled}"/>
                           <html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${caregiverModifyBtnDisabled}"/>

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

