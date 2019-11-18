<%-- 
    Document   : CaregiverView
    Created on : Jan 14, 2015, 9:32:59 PM
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
<title>Caregiver form </title>
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
            <td height="60" valign="top"><div style="float: left" id="my_menu" class="sdmenu">
                <div><jsp:include page="../DataEntryLinkPage.jsp"/></div>
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
              <div>
                  <%--<a href="householdVulnerabilityIndexReportAction.do" target="_blank">Household vulnerability Assessment register </a><a href="hVIServiceReportAction.do" target="_blank">Household service register </a><a href="Reports/MthlyServiceReportDatePage.jsp" target="_blank"> Service report </a> <a href="Reports/OvcWithdrawalDatePage.jsp" target="_blank"> VC withdrawal list </a> <a href="#"> &nbsp; </a><a href="#"> &nbsp; </a><a href="#"> &nbsp; </a>--%>
              </div>
            </div></td>
          </tr>


      </table></td>
    <td>&nbsp;</td>
      <td width="639" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0">
     <tr>
        <td height="39" class="homepagestyle" align="center" >Caregiver form </td>

    </tr>
    <tr>
        <td ><html:errors/></td>
    </tr>

  </table>

         <html:form action="/caregiverAction" method="post" styleId="hhFormId">
<html:hidden property="actionName" styleId="actionName"/>
<html:hidden property="stateCode" styleId="stateCode" value="${state.state_code}" />
<html:hidden property="hhUniqueId" styleClass="fieldcellinput" styleId="hhUniqueId" />
     

  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!--DWLayoutTable-->
                <tr>
                  <td width="762" height="28" valign="top">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="27">
                          <table width="100%" class="regsitertable">
                              <tr><td colspan="4" align="center"><jsp:include page="../PartnerView.jsp" /></td></tr>
                        		<tr><td colspan="4"> &nbsp;</td></tr>
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
                                  <%--<html:select property="lgaCode" styleClass="bigfieldcellinput" styleId="lgaCode" onchange="setActionName('cboList');forms[0].submit()">
                                      <html:option value=""></html:option>
                                      <html:optionsCollection name="CaregiverForm" property="lgaList" label="lga_name" value="lga_code" />
                                      
                                      </html:select>
                                  --%>
                                  
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
                                  <%--<html:select property="orgCode" styleId="orgCode" styleClass="bigfieldcellinput" onchange="setActionName('refresh');forms[0].submit()">
                              <html:option value=""> </html:option>
                              <html:optionsCollection name="CaregiverForm" property="cboList" label="orgName" value="orgCode" />
                                      
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
                  <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" valign="top">





					  
                        <table width="100%" class="regsitertable" style="height:250px">
                            <tr> 
                                <td>
                                    <table>
                            <tr><td class="right">Household S/No.</td>
                   
                            <td>
                                <html:text property="serialNo"  styleClass="smallfieldcellinput" styleId="serialNo"  onblur="submitForm('caregiverList','hhFormId')" onkeyup="constructUniqueId(this.value)"/> </td>
                            <td><label id="uniqueIdLabel">${hhServiceUniqueId}</label></td><td colspan="3">&nbsp;</td>
                        </tr>
                        
                        <tr>
                          <td class="right">Existing caregivers </td>
                          <td colspan="5" align="left">
                                  <html:select property="caregiverId" styleClass="fieldcellinput"  styleId="caregiverId" onchange="submitForm('caregiverDetails','hhFormId')">
                                      <html:option value=""> </html:option>
                                      <html:optionsCollection name="CaregiverForm" property="caregiverList" label="caregiverFullName" value="caregiverId" />
                                      
                                  </html:select>  </td>
                        </tr>
                        </table>
                        </td>
                        </tr>
                        <tr>
                            
                            <td colspan="6">
                                <fieldset>
                        <legend class="fieldset">New Caregiver information </legend>
                        <table width="100%" class="regsitertable">
                            <tr>
                          <td width="17%" bgcolor="#F0F0F0"><div align="left">Surname: </div></td>
                          <td width="80%" bgcolor="#F0F0F0"><html:text property="caregiverLastName" styleId="caregiverLastName" styleClass="fieldcellinput" onkeyup="searchCaregiverInHousehold(this.value)" />
                              <span style=" margin-left:10px; margin-right: 11px;">First name:</span> <html:text property="caregiverFirstname" styleId="caregiverFirstname" styleClass="fieldcellinput" />
                          </td>
                            </tr>
                            <tr>
                           <td bgcolor="#F0F0F0"><div align="left">Sex:</div></td>
                          <td bgcolor="#F0F0F0">
                              <html:select property="caregiverGender" styleId="caregiverGender" styleClass="smallfieldcellinput"><html:option value=""> </html:option><html:option value="Male">Male</html:option>
                                  <html:option value="Female">Female</html:option></html:select>
                                  <span style=" margin-left: 112px; margin-right:50px;">Age:</span><html:text property="caregiverAge" styleId="caregiverAge" styleClass="shortfieldcellinput"/></td>
                            </tr>
                            
                           <tr>
                            <td class="right">Address:</td>
                            <td ><html:textarea property="caregiverAddress" styleId="caregiverAddress" rows="3" cols="40" styleClass="fieldcellinput" style="margin-right:10px;"></html:textarea>
                                <span style="margin-right:18px;">Telephone:</span><html:text property="caregiverPhone" styleId="caregiverPhone" styleClass="smallfieldcellinput"/></td>
                          </tr>
                          <tr>
                            <td class="right">Occupation:</td>
                            <td colspan="5"><html:text property="caregiverOccupation" styleId="caregiverOccupation" styleClass="fieldcellinput" />
                                <span style="margin-left:10px; ">Marital status:</span>
                                <html:select styleId="caregiverMaritalStatus" styleClass="fieldcellinput" property="caregiverMaritalStatus" >
                    <html:option value=""></html:option>
                    <html:option value="Married">Married</html:option>
                    <html:option value="Single">Single</html:option>
                    <html:option value="Divorced">Divorced</html:option>
                    <html:option value="Seperated">Separated</html:option>
                    <html:option value="Widow(er)">Widow(er)</html:option>
                                </html:select></td>
                            </tr>
                            <tr>
                            <td >HIV status:</td>
                            <td > 
                                <html:text property="currentHivStatus" styleClass="smallfieldcellselect" styleId="cgiverHivStatus1"/>
                            
                                &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;New HIV status
                                <html:select property="newHivStatus" styleClass="fieldcellinput" styleId="newHivStatus" onchange="activateEnrolledInCare(this.value)">
                                      <html:option value="select">select...</html:option>
                                      <html:optionsCollection name="CaregiverForm" property="hivStatusList" label="hivStatusName" value="hivStatusCode" />
                                  </html:select>
                            </td>
                          </tr>
                          <tr>
                            <td >Enrolled in care? </td>
                            <td > 
                                <html:select property="enrolledInCare" styleClass="smallfieldcellinput" styleId="enrolledInCare" onchange="activateReferralList(this.value)" disabled="true">
                                      <html:option value="No">No</html:option>
                                      <html:option value="Yes">Yes</html:option>
                                  </html:select>
                                      <span style=" margin-left: 100px"> Enrolled on ART? </span>
                             
                                <html:select property="enrolledOnART" styleClass="smallfieldcellinput" styleId="enrolledOnART" onchange="activateReferralList(this.value)" disabled="true">
                                      <html:option value="No">No</html:option>
                                      <html:option value="Yes">Yes</html:option>
                                  </html:select>
                            </td>
                          </tr>
                          <tr>
                            <td >Facility enrolled </td>
                            <td > 
                                <html:select property="organizationClientIsReferred" styleClass="fieldcellinput" styleId="organizationClientIsReferred" style="width:490px;" disabled="true">
                                      <html:option value="select">Select</html:option>
                                      <html:optionsCollection property="referralDirectoryList" label="facilityName" value="facilityId"/>
                                  </html:select>
                            </td>
                          </tr>
                        </table></fieldset>
                            </td>
                            </tr>
                            <tr> <td style="height:30px" colspan="6">&nbsp; </td></tr>
			</table>
                     


					  </td>
                      </tr>

                  </table></td>
                </tr>
                
                
                <tr>
				<td height="40" align="center">
                            
                            <html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${caregiverSaveBtnDisabled}"/>
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
