<%-- 
    Document   : Enrollment
    Created on : Mar 8, 2011, 3:00:40 PM
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
<script language="JavaScript" src="kidmap.js" type="text/JavaScript"></script>
<link type="text/css" href="css/ui-darkness/jquery-ui-1.8.custom.css" rel="Stylesheet" />
 <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
 <script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
 <script type="text/javascript" src="js/Enrollmentjsfile.js"></script>
 <script language="javascript">
function activateEnrolledInCare(value)
{
    if(value == "positive") 
    {
        document.getElementById("childEnrolledInCare").disabled = false;
        document.getElementById("childEnrolledOnART").disabled = false;
    }
    else 
    {
       document.getElementById("childEnrolledOnART").value="No"
       document.getElementById("childEnrolledInCare").value="No"
       document.getElementById("childEnrolledOnART").disabled = true;
       document.getElementById("childEnrolledInCare").disabled = true;
       document.getElementById("organizationChildIsReferred").value="select"
       document.getElementById("organizationChildIsReferred").disabled = true;
    }
}
function activateCaregiverEnrolledInCare(value)
{
    if(value == "positive") 
    {
        document.getElementById("cgiverEnrolledInCare").disabled = false;
        document.getElementById("cgiverEnrolledOnART").disabled = false;
    }
    else 
    {
       document.getElementById("cgiverEnrolledInCare").value="No"
       document.getElementById("cgiverEnrolledOnART").value="No"
       document.getElementById("cgiverEnrolledInCare").disabled = true;
       document.getElementById("cgiverEnrolledOnART").disabled = true;
       document.getElementById("organizationCaregiverIsReferred").value="select"
       document.getElementById("organizationCaregiverIsReferred").disabled = true;
    }
}
function activateReferralList(value) {
    if(value == "Yes") 
    {
        document.getElementById("organizationChildIsReferred").disabled = false;
    }
    else 
    {
        if(document.getElementById("childEnrolledOnART").value=="No" && document.getElementById("childEnrolledInCare").value=="No")
        {
            document.getElementById("organizationChildIsReferred").value="select"
            document.getElementById("organizationChildIsReferred").disabled = true;
        }
    }
}
function activateCaregiverReferralList(value) 
{
    if(value == "Yes") 
    {
        document.getElementById("organizationCaregiverIsReferred").disabled = false;
    }
    else 
    {
        if(document.getElementById("cgiverEnrolledOnART").value=="No" && document.getElementById("cgiverEnrolledInCare").value=="No")
        {
            document.getElementById("organizationCaregiverIsReferred").value="select"
            document.getElementById("organizationCaregiverIsReferred").disabled = true;
        }
    }
}
function setWithdrawalStatus()
{
   req="withdrawalStatus;"+"withdrawalStatus"
   getValuesFromDb('addcbo.do',req,'withdrawalStatus')
}

function stateChanged()
{
	if (xmlhttp.readyState==4)
	{
		var lgas=xmlhttp.responseText;
                if(lgas==" " || lgas=="" || lgas==";" || lgas==null)
                return false;
                //alert(callerId)
            var values=lgas.split(";")
            if(callerId=="withdrawalStatus")
            document.getElementById("msg").innerHTML=lgas
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
                //alert(values[0])
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
function constructUniqueId(SNo,maxDigit)
{
    var orgCode=document.getElementById("CbosSelect").value
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
function padSerialNo(SNo,maxDigit,id)
{
    var numberLength=SNo.length
    var num=validateSNo(SNo,maxDigit)
    if(num==0)
    clearUniqueSNoField(id)
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
    var msg=null
    if(SNo=="" || SNo==" ")
    {
        return 0
    }
    else if(isNaN(SNo))
    {
        alert("Serial number must be numeric")
        return 0
    }
    else if(SNo <1)
    {
        alert("Serial number must be greater than 0")
        return 0    
    }
    else if(numberLength > maxDigit)
    {
        msg="Serial number must not be more than "+maxDigit+" digits"
        alert(msg)
        return 0
    }
    return 1
}
function clearUniqueSNoField(id)
{
    document.getElementById(id).value=""
}
function getOvcDetails(ovcUniqueId)
{
     if(ovcUniqueId==" " || ovcUniqueId=="")
     return flase
 document.getElementById("ovcId").value=ovcUniqueId
 submitForm('ovcDetails')
 return true
}
function submitForm(val)
{
    document.getElementById("buttonId").value=val
    document.getElementById("dataForm").submit()
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
    var num=validateSNo(SNo,maxDigit)
    if(num==0)
    {
        return false    
    }
    else
    {
        var numberLength=SNo.length
        while(numberLength<parseInt(maxDigit))
        {
            SNo="0"+SNo
            numberLength++
        }
        document.getElementById("ovcId").value=hhId+"/"+SNo
    } 
}
/*function showChangeDocumentationDiv()
{
    showComponent("changeDiv")
    document.getElementById(id).style.top="76%"
}*/
function openCaregiverForm()
{
    window.open("DataEntry/CaregiverSpan.jsp","_blank","toolbar=no, scrollbars=no, resizable=yes, top=300, left=300, width=700, height=600");
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
    <td height="117" colspan="2" valign="top">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
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
    <td width="931" height="351" valign="top">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
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
            <td height="85" valign="top">
                <div style="float: left" id="my_menu" class="sdmenu">
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
      <td width="639" >

  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
     <td width="30%" height="20" class="homepagestyle">VC Enrollment form </td>
     <td width="70%"> <html:errors/> </td>
    </tr>
    
     <tr><td height="10" colspan="2" align="center"><label style=" font-size: 15px; color: green"><%--${partnername}--%></label> </td></tr>
  </table>
    <html:form styleId="dataForm"  method="post" action="/Add">
        <html:hidden property="hhUniqueId" styleId="hhUniqueId" />
            <input type="hidden" name="stateId2" />
            <input type="hidden" name="lgaId2" />
            <html:hidden property="stateCode" styleId="stateCode" value="${state.state_code}" />
            <html:hidden property="partnerCode" styleId="partnerCode"/>
            <input type="hidden" name="lgaCode" id="lgaCode" />
            <input type="hidden" name="stateId" id="stateId" value="${state.state_code}" />
            <html:hidden property="ward" styleId="WardsSelect"/>
            <html:hidden property="actionName" styleId="buttonId"/>
            <html:hidden property="childIndexId" styleId="childIndexId" />
            <%--<html:hidden property="caregiverId" styleId="caregiverId"/>--%>
            <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
        <!--DWLayoutTable-->
        <tr>
          <td width="8" height="1258">&nbsp;</td>
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
                              <td colspan="4" align="center"><jsp:include page="../PartnerView.jsp" /></td>

                            </tr>

                              <tr>
                                  <td width="4%"><label>State:</label></td>
                          <td width="30%">
                              <html:text styleId="stateList" property="state" readonly="true" styleClass="bigfieldcellinput" value="${state.name}" ></html:text>

                          </td>
                              <td width="4%"><label>LGA:</label></td>
                              <td width="30%">
                                      <html:select property="lga" styleClass="bigfieldcellinput" styleId="lgaId" onchange="submitForm('cboList');">
                                          <logic:iterate name="lgaListInOrgRecords" id="lga">
                                              <html:option value="${lga.lga_code}" >${lga.lga_name}</html:option>
                                          </logic:iterate>
                                      </html:select>
                                      
                              </td>

                            </tr>
                        <tr>
                            <td width="4%" ><label>CBO:</label></td>
                              <td width="30%"><%--getwards(this.value);--%>
                                  <span id="selected2">
                                      <html:select property="completedbyCbo" styleId="CbosSelect" styleClass="bigfieldcellinput" style="width:250px;">
                                      <html:option value=""> </html:option>
                                      <logic:iterate id="cbos" name="cboListInOrgRecords">
                                              <html:option value="${cbos.orgCode}">${cbos.orgName} </html:option>
                                          </logic:iterate>
                                      </html:select>


                                  </span></td>
                            <td width="4%"><label ><jsp:include page="../includes/WardName.jsp" /></label></td>

                              <td width="30%">
                                  <html:text property="wardName" styleId="wardName" styleClass="bigfieldcellinput"/>
                                  </td>
                            </tr>

                        </table>
                      </td>
                      </tr>
                  </table></td>
                </tr>


                <tr>
                  <td height="210" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="122" valign="top">


                          <fieldset>
                        <legend class="fieldset">Personal information  </legend>
                        <table width="100%" class="regsitertable">
                        <tr><td width="15%" valign="top" class="right">HH Unique SNo</td>
                            <td><html:text property="hhSerialNo" styleClass="smallfieldcellinput" styleId="hhSerialNo" onkeyup="constructUniqueId(this.value,5)" onblur="submitForm('hhDetails')"/> 
                               <label id="uniqueIdLabel" >${vcHhUniqueId} </label>
                               </td>
                            </tr>
                        <tr><td width="15%" valign="top" class="right">VC serial no.</td>
                            <%--<html:text property="ovcSno"  styleClass="smallfieldcellinput" styleId="serialNo" onkeyup="constructUniqueId(this.value)"/>onblur="checkDupId()"--%>
                            <td><html:text property="ovcSno"  styleClass="smallfieldcellinput" styleId="ovcSno" onkeyup="makeOvcId(this.value,5,'ovcSno')" onblur="submitForm('ovcDetails')" />&nbsp;<input type="button" name="search" value="Search by name" onclick="showSearchDiv()" />
                                  
                                  &nbsp;&nbsp;<span style="margin-left:80px;">Enrollment date:</span>
                                  
                                  <html:text property="dateEnrollment" styleId="dateEnrollment" styleClass="smallfieldcellinput" readonly="true"/>(mm/dd/yyyy)</td>
                            </tr>

                        <tr>
                          <td valign="top" class="right">VC unique Id:</td> <%--makeId()--%>
                          <td><html:text property="ovcId" readonly="true" styleClass="fieldcellinput" styleId="ovcId" /><br />
                              <span class="textunder" style="font-size:9px;">(State code/LGA code/Org. code/VC serial no) </span>
                          
                                  </td>
                            </tr>
                        <tr>
                          <td valign="top" class="right">Surname: </td>
                              <td>

                                  <html:text property="surname" styleId="surname" styleClass="fieldcellinput" onkeyup="capitalizeAll('surname', this.value)" onblur="checkDuplicateByName()"/>
                                  <span style="margin-left:17px; margin-right: 15px;">First name:</span> <html:text property="otherNames1" styleId="otherNames1" styleClass="smallfieldcellinput" onkeyup="capitalize('otherNames1', this.value)" onblur="checkDuplicateByName()" />
                                  <span style="margin-left:10px; margin-right: 5px;">Middle name:</span><html:text property="otherNames2" styleId="otherNames2" styleClass="smallfieldcellinput" onkeyup="capitalize('otherNames2', this.value)" onchange="checkDuplicateByName()"/></td>
                            </tr>
                        <tr>
                          <td valign="top" class="right">Sex: </td>
                              <td>
                                    <html:select property="gender" styleId="gender" styleClass="fieldcellinput" style="width:70px;">
                    <html:option value=""> </html:option><html:option value="Male">Male</html:option><html:option value="Female">Female</html:option> <!--populateAge(this.value); -->
                                    </html:select>
                    <span style=" margin-left: 35px; margin-right: 15px;">Age:</span>
                    <html:select property="age" styleId="age" styleClass="shortfieldcellinput" onchange="checkDuplicateByName()">

                    <html:option value="0">0</html:option>
                    <html:option value="1">1</html:option>
                    <html:option value="2">2</html:option>
                    <html:option value="3">3</html:option>
                    <html:option value="4">4</html:option>
                    <html:option value="5">5</html:option>
                    <html:option value="6">6</html:option>
                    <html:option value="7">7</html:option>
                    <html:option value="8">8</html:option>
                    <html:option value="9">9</html:option>
                    <html:option value="10">10</html:option>
                    <html:option value="11">11</html:option>
                    <html:option value="12">12</html:option>
                    <html:option value="13">13</html:option>
                    <html:option value="14">14</html:option>
                    <html:option value="15">15</html:option>
                    <html:option value="16">16</html:option>
                    <html:option value="17">17</html:option>
                </html:select>
                    <span style=" margin-left: 18px; margin-right: 34px;">Age unit</span><html:select styleId="ageUnit" styleClass="fieldcellinput" property="ageUnit" style="width:82px;" onchange="checkDuplicateByName()">
                    <html:option value=""></html:option>
                    <html:option value="Year">Year</html:option>
                    <html:option value="Month">Month</html:option>
                </html:select>
                    <span style="margin-left:20px; margin-right: 5px;">Weight(kg)</span><html:text property="weight" styleId="weight" styleClass="shortfieldcellinput" /></td>
                            </tr>


							 
                        <tr>
                            <td valign="top" class="right" style=" height: 20px;"><br/>Address: </td>
                              <td>
                                    <html:textarea property="address" styleId="address" rows="3" cols="80" styleClass="textareacellinput"></html:textarea>
                                    <span style=" margin-left: 16px; margin-right: 18px;">Telephone:</span> <html:text property="phone" styleId="phone" styleClass="smallfieldcellinput" /><span style="margin-left:12px; margin-right: 18px;">Height(cm):</span><html:text property="height" styleId="height" styleClass="shortfieldcellinput" />                               </td>
                            </tr>
                        </table>
                      </fieldset>




                      </td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="123" valign="top">
                      <fieldset>
                        <legend class="fieldset">Vulnerability status </legend>
                        <div style="width:720px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="102">
                          <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:iterate name="finalList" id="propertyList">
                                  <tr><td>${propertyList[0].name} </td> <td><html:multibox property='eligibility' styleId="${propertyList[0].id}" value="${propertyList[0].name}" styleClass='smallfieldcellselect'/> </td>
                                      <td>${propertyList[1].name} </td> <td><html:multibox property='eligibility' styleId="${propertyList[1].id}" value="${propertyList[1].name}" styleClass='smallfieldcellselect'/> </td>
                                  </tr>
                              </logic:iterate>
                              
                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
                  </fieldset></td>
                </tr>
                <tr>
                  <td height="53" valign="top">

                      <fieldset>
                        <legend class="fieldset">Child's HIV status </legend><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="34">
                       <table width="100%" class="regsitertable">
                        <%--<tr>
                          <td width="16%">HIV status unknown</td>
                          <td width="10%"><html:radio property="hivStatus" styleClass="smallfieldcellselect" styleId="hivStatus1" value="HIV status unknown" /></td>
                          <td width="10%">HIV negative </td>
                          <td width="10%"><html:radio property="hivStatus" styleClass="smallfieldcellselect" styleId="hivStatus2" value="HIV negative" /></td>
                          <td width="10%">HIV positive </td>
                          <td width="58%"><html:radio property="hivStatus" styleClass="smallfieldcellselect" styleId="hivStatus3" value="HIV positive" /></td>
                        </tr>--%>
                        <tr>
                            <td >Child's HIV status </td>
                            <td >                         
                                <html:select property="hivStatus" styleClass="fieldcellinput" styleId="hivStatus" onchange="activateEnrolledInCare(this.value)">
                                    <html:option value="select">select </html:option>  
                                    <logic:present name="enrhivStatusList">
                                        <logic:iterate name="enrhivStatusList" id="hivStatus">
                                           <html:option value="${hivStatus.hivStatusCode}">${hivStatus.hivStatusName} </html:option>  
                                        </logic:iterate>
                                    </logic:present>
                                    <%--<html:optionsCollection property="hivStatusList" label="hivStatusName" value="hivStatusCode" />--%>
                              
                                  </html:select>
                                      <span style=" margin-left: 4px;">Enrolled in care?</span>  
                                      <html:select property="childEnrolledInCare" styleClass="fieldcellinput" styleId="childEnrolledInCare" style="width:50px;" onchange="activateReferralList(this.value)" disabled="${enrhivDisabled}">
                                      <html:option value="No">No</html:option>
                                      <html:option value="Yes">Yes</html:option>
                                  </html:select>
                                   <span style=" margin-left: 4px;">Enrolled on ART?</span>  
                                      <html:select property="childEnrolledOnART" styleClass="fieldcellinput" styleId="childEnrolledOnART" style="width:50px;" onchange="activateReferralList(this.value)" disabled="${enrhivDisabled}">
                                      <html:option value="No">No</html:option>
                                      <html:option value="Yes">Yes</html:option>
                                  </html:select>
                            </td>
                          </tr>
                          <tr>
                            <td >Facility enrolled </td>
                            <td > 
                                <html:select property="organizationChildIsReferred" styleClass="fieldcellinput" styleId="organizationChildIsReferred" style="width:510px;" disabled="${enrOvcFachivDisabled}">
                                      <html:option value="select">Select</html:option>
                                      <html:optionsCollection property="referralDirectoryList" label="facilityName" value="facilityId" />
                                  </html:select>
                            </td>
                          </tr>
                      </table></td>
                    </tr>
                  </table>
                  </fieldset></td>
                </tr>
                <tr>
                  <td height="60" valign="top"><fieldset>
                        <legend class="fieldset">Birth registration and education </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="38"><table width="100%" class="regsitertable">
                        <tr>
                          <td width="40%" class="right">Does the child have birth registration certificate? </td>
                              <td width="60%"><label>
                                      <html:select property="birthCertificate" styleId="birthCertificate" styleClass="shortfieldcellinput">
                    <html:option value=" "> </html:option>
                    <html:option value="Yes">Yes</html:option>
                    <html:option value="No">No</html:option>
                </html:select>
                                  </label> <span style=" margin-left: 23px; margin-right: 2px;">Is the child in school/Vocational Centre?</span>
                                  <html:select property="schoolStatus" styleId="schoolStatus" onchange="childInSchool(this.value)" styleClass="shortfieldcellinput">
                                   <html:option value=" "> </html:option>
                                   <html:option value="Yes">Yes</html:option>
                                   <html:option value="No">No</html:option>
                                   </html:select>
                              </td>
                            </tr>
                         
                        <tr>
                          <td class="right">Name of school/Vocational Centre:</td>
                          <td><span id="schoolNameSpan">
                                  <html:select styleClass="bigfieldcellinput" property="schoolName" styleId="schoolName" disabled="${schoolDisabled}" >
                                      <html:option value=" "> </html:option>
                                      <logic:iterate id="school" name="schools">
                                      <html:option value="${school.name}">${school.name}</html:option>
                                      </logic:iterate>
                                  </html:select>
                                  <!-- onclick="populateSchools()" <input type="text" id="schoolName1" class="fieldcellinput" disabled="true" />--></span>
                              Class: <html:text property="currentClass" styleId="currentClass" styleClass="smallfieldcellinput" disabled="true" /></td>
                        </tr>

                        </table></td>
                      </tr>
                  </table>
                  </fieldset></td>
                </tr>
                <tr>
                  <td height="553" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->

                    <tr>
                      <td width="752" height="92"><fieldset>
                        <legend class="fieldset">Household head information </legend>
                        <table width="100%" class="regsitertable">
                            <tr>
                          <td width="20%" bgcolor="#F0F0F0"><div align="left">Surname: </div></td>
                          <td width="85%" bgcolor="#F0F0F0"><html:text property="hhSurname" styleId="hhSurname" styleClass="fieldcellinput" readonly="true"/>
                              <span style=" margin-left:10px; margin-right: 15px;">First name:</span> <html:text property="hhFirstname" styleId="hhFirstname" styleClass="fieldcellinput" readonly="true"/></td>
                            </tr>
                          <tr>
                           <td bgcolor="#F0F0F0"><div align="left">Sex:</div></td>
                          <td bgcolor="#F0F0F0">
                              <html:text property="hhGender" styleId="hhGender" styleClass="smallfieldcellinput" readonly="true"/>
                              <%--<html:option value=""> </html:option><html:option value="Male">Male</html:option>
                                  <html:option value="Female">Female</html:option></html:select>--%>
                              <span style=" margin-left: 112px; margin-right:48px;">Age:</span>
                              <html:text property="hhAge" styleId="hhAge" styleClass="shortfieldcellinput" readonly="true"/></td>
                            </tr>
                           <tr>
                                <td class="right">Number of Children 0-17 years in the household</td>
                                    <td colspan="5"><html:text property="numOfChildrenInHh" styleId="numOfChildrenInHh"  styleClass="shortfieldcellinput" readonly="true"></html:text> 
                                        <span style="margin-left:35px; margin-right: 5px;">Number of people in the household: </span>
                                    <html:text property="numOfOVCInHh" styleId="numOfOVCInHh" styleClass="shortfieldcellinput" readonly="true"/>
                            </td>
                            </tr>
                          <tr>
                            <td class="right"> </td>
                            <td colspan="5"></td>
                            </tr>
                          </table></fieldset></td>
                      </tr>


                    <tr>
                      <td width="752" height="92"><fieldset>
                        <legend class="fieldset">Caregiver information </legend>
                        <table width="100%" class="regsitertable">
                            <tr>
                          <td class="right">Select Caregiver </td>
                          <td colspan="5" align="left">
                                  <html:select property="caregiverId" styleClass="fieldcellinput"  styleId="caregiverId">
                                      <html:option value=""> </html:option>
                                      <logic:present name="ovcBaselineCaregiverList">
                                          <logic:iterate name="ovcBaselineCaregiverList" id="cgiver">
                                              <html:option value="${cgiver.caregiverId}">${cgiver.caregiverFullName}</html:option>
                                          </logic:iterate>
                                      </logic:present>
                                      <%--<html:optionsCollection property="caregiverList" label="caregiverFullName" value="caregiverId" />--%>
                                      
                                  </html:select>  </td>
                        </tr>
                            <%--<tr><td colspan="2">
                                  <input type="button" value="Add or update caregiver" onclick="openCaregiverForm()" style="height: 20px; width: 170px;" /> </td></tr>--%>
                            
                            <%--<tr>
                          <td width="20%" bgcolor="#F0F0F0"><div align="left">Surname: </div></td>
                          <td width="85%" bgcolor="#F0F0F0"><html:text property="caregiverName1" styleId="caregiverName1" styleClass="fieldcellinput" onkeyup="searchCaregiverInHousehold(this.value)" />
                              <span style=" margin-left:10px; margin-right: 13px;">First name:</span> <html:text property="caregiverName2" styleId="caregiverName2" styleClass="fieldcellinput" onkeyup="searchCaregiverInHousehold(this.value)" /></td>
                            </tr>
                            <tr><td > </td> <td ><span id="caregiverSurnameNameSpan" style="position: absolute"> </span> </td></tr>
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
                                <span style="margin-right:27px;">Telephone:</span><html:text property="caregiverPhone" styleId="caregiverPhone" styleClass="smallfieldcellinput"/></td>
                          </tr>
                          <tr>
                            <td class="right">Occupation:</td>
                            <td colspan="5"><html:text property="caregiverOccupation" styleId="caregiverOccupation" styleClass="fieldcellinput" />
                                <span style="margin-left:10px; margin-right: 10px;">Marital status:</span>
                                <html:select styleId="caregiverMaritalStatus" styleClass="fieldcellinput" property="caregiverMaritalStatus" >
                    <html:option value=""></html:option>
                    <html:option value="Married">Married</html:option>
                    <html:option value="Single">Single</html:option>
                    <html:option value="Divorced">Divorced</html:option>
                    <html:option value="Seperated">Separated</html:option>
                    <html:option value="Widow(er)">Widow(er)</html:option>
                                </html:select></td>
</tr>--%>
                            <tr>
                              <td class="right" >Relationship to child:</td>
                                 
                                 <td bordercolor="#FFFFFF" bgcolor="#F0F0F0">
                                     <html:select property="caregiverRelationships" styleId="caregiverRelationships" styleClass="fieldcellinput">
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
                                      
                                  </html:select> 
                                      <span style=" margin-left: 12px;">Others(specify):</span>  <html:text property="caregiverRelationships2" styleId="caregiverRelationships2" styleClass="fieldcellinput"/>
                              </td>
                              
                          </tr>
                          <%--<tr>
                            <td >Caregiver HIV status </td>
                            <td >                         
                                <html:select property="cgiverHivStatus" styleClass="fieldcellinput" styleId="cgiverHivStatus" onchange="activateCaregiverEnrolledInCare(this.value)">
                                    <html:option value="select">select </html:option> 
                                    <logic:present name="enrhivStatusList">
                                        <logic:iterate name="enrhivStatusList" id="hivStatus">
                                           <html:option value="${hivStatus.hivStatusCode}">${hivStatus.hivStatusName} </html:option>  
                                        </logic:iterate>
                                    </logic:present>
                                    
                                  </html:select>
                                      <span style=" margin-left: 4px;">Enrolled in care?</span>  
                                      <html:select property="cgiverEnrolledInCare" styleClass="fieldcellinput" styleId="cgiverEnrolledInCare" style="width:50px;" onchange="activateCaregiverReferralList(this.value)" disabled="${enrcghivDisabled}">
                                      <html:option value="No">No</html:option>
                                      <html:option value="Yes">Yes</html:option>
                                  </html:select>
                                      <span style=" margin-left: 4px;">Enrolled on ART?</span>  
                                      <html:select property="cgiverEnrolledOnART" styleClass="fieldcellinput" styleId="cgiverEnrolledOnART" style="width:50px;" onchange="activateCaregiverReferralList(this.value)" disabled="${enrcghivDisabled}">
                                      <html:option value="No">No</html:option>
                                      <html:option value="Yes">Yes</html:option>
                                  </html:select>
                            </td>
                          </tr>
                          <tr>
                            <td >Facility enrolled </td>
                            <td > 
                                <html:select property="organizationCaregiverIsReferred" styleClass="fieldcellinput" styleId="organizationCaregiverIsReferred" style="width:510px;" disabled="${enrcgFachivDisabled}">
                                      <html:option value="select">Select</html:option>
                                      <html:optionsCollection property="referralDirectoryList" label="facilityName" value="facilityId"/>
                                  </html:select>
                            </td>
                          </tr>--%>
                          
                        </table></fieldset></td>
                      </tr>




                    <tr>
                      <td height="172" valign="top"><fieldset>
                        <legend class="fieldset">Baseline / Initial child status index assessment </legend>
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
                              <td align="center"><html:radio property="foodSecurity" styleId="csiFactor3_4" value="4" /></td><td align="center"><html:radio property="foodSecurity" styleId="csiFactor3_3" value="3" /></td><td align="center"><html:radio property="foodSecurity" styleId="csiFactor3_2" value="2" /></td><td align="center"><html:radio property="foodSecurity" styleId="csiFactor3_1" value="1" /></td>
                              <td>Wellness</td>
                              <td align="center"><html:radio property="wellness" styleId="csiFactor5_4" value="4" /></td><td align="center"><html:radio property="wellness" styleId="csiFactor5_3" value="3" /></td><td align="center"><html:radio property="wellness" styleId="csiFactor5_2" value="2" /></td><td align="center"><html:radio property="wellness" styleId="csiFactor5_1" value="1" /></td>
                             
                            </tr>
                            <tr>
                                <td>Nutrition and growth </td>
                              <td align="center"><html:radio property="nutritionAndGrowth" styleId="csiFactor4_4" value="4" /></td><td align="center"><html:radio property="nutritionAndGrowth" styleId="csiFactor4_3" value="3" /></td><td align="center"><html:radio property="nutritionAndGrowth" styleId="csiFactor4_2" value="2" /></td><td align="center"><html:radio property="nutritionAndGrowth" styleId="csiFactor4_1" value="1" /></td>
                              <td>Health care services </td>
                              <td align="center"><html:radio property="healthCareServices" styleId="csiFactor6_4" value="4" /></td><td align="center"><html:radio property="healthCareServices" styleId="csiFactor6_3" value="3" /></td><td align="center"><html:radio property="healthCareServices" styleId="csiFactor6_2" value="2" /></td><td align="center"><html:radio property="healthCareServices" styleId="csiFactor6_1" value="1" /></td>
                             
                            </tr>
                            <tr>
                              <td colspan="5" bgcolor="#F0F0F0"><strong>Shelter and care</strong></td>
                              <td colspan="5" bgcolor="#F0F0F0"><strong>Psychosocial</strong></td>
                              </tr>
                            <tr>
                              <td>Shelter</td>
                              <td align="center"><html:radio property="shelter" styleId="csiFactor11_4" value="4" /></td><td align="center"><html:radio property="shelter" styleId="csiFactor11_3" value="3" /></td><td align="center"><html:radio property="shelter" styleId="csiFactor11_2" value="2" /></td><td align="center"><html:radio property="shelter" styleId="csiFactor11_1" value="1" /></td>

                              <td>Emotional health </td>
                           <td align="center"><html:radio property="emotionalHealth" styleId="csiFactor1_4" value="4" /></td>
                            <td align="center"><html:radio property="emotionalHealth" styleId="csiFactor1_3" value="3" /></td>
                             <td align="center"><html:radio property="emotionalHealth" styleId="csiFactor1_2" value="2" /></td>
                            <td align="center"><html:radio property="emotionalHealth" styleId="csiFactor1_1" value="1" /></td>

                            </tr>
                            <tr>
                              <td>Care</td>
                              <td align="center"><html:radio property="care" styleId="csiFactor12_4" value="4" /></td><td align="center"><html:radio property="care" styleId="csiFactor12_3" value="3" /></td><td align="center"><html:radio property="care" styleId="csiFactor12_2" value="2" /></td><td align="center"><html:radio property="care" styleId="csiFactor12_1" value="1" /></td>
                              <td>Social behaviour </td>
                              <td align="center"><html:radio property="socialBehaviour" styleId="csiFactor2_4" value="4" /></td><td align="center"><html:radio property="socialBehaviour" styleId="csiFactor2_3" value="3" /></td><td align="center"><html:radio property="socialBehaviour" styleId="csiFactor2_2" value="2" /></td><td align="center"><html:radio property="socialBehaviour" styleId="csiFactor2_1" value="1" /></td>

                            </tr>
                            <tr>
                              <td colspan="5" bgcolor="#F0F0F0"><strong>Protection</strong></td>
                              <td colspan="5" bgcolor="#F0F0F0"><strong>Education and skills </strong></td>
                              </tr>
                            <tr>
                              <td>Abuse and exploitation </td>
                              <td align="center"><html:radio property="abuseAndExploitation" styleId="csiFactor9_4" value="4" /></td><td align="center"><html:radio property="abuseAndExploitation" styleId="csiFactor9_3" value="3" /></td><td align="center"><html:radio property="abuseAndExploitation" styleId="csiFactor9_2" value="2" /></td><td align="center"><html:radio property="abuseAndExploitation" styleId="csiFactor9_1" value="1" /></td>
                              <td>Development and performance</td>
                              <td align="center"><html:radio property="developmentAndPerformance" styleId="csiFactor7_4" value="4" /></td>
                              <td align="center"><html:radio property="developmentAndPerformance" styleId="csiFactor7_3" value="3" /></td>
                              <td align="center"><html:radio property="developmentAndPerformance" styleId="csiFactor7_2" value="2" /></td>
                              <td align="center"><html:radio property="developmentAndPerformance" styleId="csiFactor7_1" value="1" /></td>

                            </tr>
                            <tr>
                              <td>Legal protection </td>
                              <td align="center"><html:radio property="legalProtection" styleId="csiFactor10_4" value="4" /></td><td align="center"><html:radio property="legalProtection" styleId="csiFactor10_3" value="3" /></td><td align="center"><html:radio property="legalProtection" styleId="csiFactor10_2" value="2" /></td><td align="center"><html:radio property="legalProtection" styleId="csiFactor10_1" value="1" /></td>
                              <td>Education and work </td>
                              <td align="center"><html:radio property="educationAndWork" styleId="csiFactor8_4" value="4" /></td><td align="center"><html:radio property="educationAndWork" styleId="csiFactor8_3" value="3" /></td><td align="center"><html:radio property="educationAndWork" styleId="csiFactor8_2" value="2" /></td><td align="center"><html:radio property="educationAndWork" styleId="csiFactor8_1" value="1" /></td>
                            </tr>

                            
                          </table></td>
                        </tr>
                      </table></fieldset>                      </td>
                      </tr>
                      
                      <tr>
                      <td height="52" valign="top">
                     <fieldset>
                        <legend class="fieldset">Source of information</legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                          <td width="752" height="33">

                            <table border="1" bordercolor="#D7E5F2" class="regsitertable">
                            <tr>
                                <td width="3%"><label>Child </label><html:multibox property="sourceOfInfo" styleId="child" style="margin-left:17px"  value="Child"/></td>
                                <td width="3%"><label>Parent/Guardian</label> <html:multibox property="sourceOfInfo" styleId="parent" value="Parent/Guardian"/></td>
                                <td width="3%"><label>Relative </label><html:multibox property="sourceOfInfo" styleId="relative" value="Relative"/></td>
                                <td width="3%"><label>Neighbour</label> <html:multibox property="sourceOfInfo" styleId="neighbour" value="Neighbour"/></td>
                            </tr>
                            <tr>
                                <td width="3%"><label>Teacher</label> <html:multibox property="sourceOfInfo" styleId="teacher" value="Teacher"/></td>
                                <td width="3%"><label>Family friend</label> <html:multibox property="sourceOfInfo" styleId="family" style="margin-left:17px" value="Family friend"/></td>
                                <td width="3%"><label>Community/Social worker</label> <html:multibox property="sourceOfInfo" styleId="community" value="Community/Social worker"/></td>
                                <td width="3%"><label>Others:</label> <html:text property="otherSourceOfInfo" styleId="otherSourceOfInfo"/></td>
                            </tr>
                          </table></td>
                        </tr>
                      </table>
                      </fieldset></td>
                    </tr>
                    <tr>
                      <td height="52" valign="top">
                     <fieldset>
                        <legend class="fieldset">Completed by <!--Services child recieved at intake <span class="opt style1"><em>( Tick if child received services at this encounter ) </em></span>--></legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!--DWLayoutTable-->
                        <tr>
                          <td width="752" height="33">
                              
                            <table>
                            <!--<tr>
                          <td width="752" height="28" colspan="6">
                              ----- <span class="homepagestyle">Completed by :-------------------------------------------------------------------------------------------------------------</span></td>
                      </tr>-->
                            <tr>
                              <td width="3%"><label>Surname</label> </td>
                              <td width="4%"><html:text property="completedbyName1" styleId="completedbyName1" styleClass="smallfieldcellinput" /></td>
                              <td width="7%"><label>First name</label> </td>
                              <td width="4%"><html:text property="completedbyName2" styleId="completedbyName2" styleClass="smallfieldcellinput" /></td>
                              <td width="4%"><label >Designation</label></td>
                              <td width="32%"><html:text property="completedbyDesignation" styleId="completedbyDesignation" styleClass="smallfieldcellinput" /> <label style="margin-left: 5px;">Organization</label><html:text property="verifiedBy" styleId="verifiedBy" styleClass="smallfieldcellinput"/></td>
                            </tr>
                          </table></td>
                        </tr>
                      </table>
                      </fieldset></td>
                    </tr>
                    
                    <tr>
                        <td height="40" align="center">
                                                        
                            <html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${enrollmentSaveDisable}"/>
                            <%--<html:button property="modify" value="Mark for Delete" styleId="modifyBtn" onclick="return showChangeDocumentationDiv()" style="width:120px; height:25px; margin-right:5px;" disabled="${enrollmentModifyDisable}"/>--%>
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify');" style="width:70px; height:25px; margin-right:5px;" disabled="${enrollmentModifyDisable}"/>
                           <html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete');" style="width:70px; height:25px; margin-right:5px;" disabled="${enrollmentModifyDisable}"/>
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
          <td colspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
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
                        
          <div id="schText" style=" visibility: hidden; position: absolute"><span><input type="text" id="privateSchool" class="fieldcellinput" onblur="setSchoolName(this.value)" /></span>
          </div>
          <div id="changeDiv" class="search" style="width:210px;">
    <table><tr><td style="width:208px;"><label id="title" style="color:#FFFFFF; width:198px;">Reason for change</label></td><td><img name="popClose" src="images/close.jpg" style="width:10px; height:10px;" onClick="hideComponent('pop')"/></td></tr>
        <tr><td colspan="2" align="left"><span><input type="textarea" name="changeArea" style="width:195px;" style="margin-top:0px;" /></span></td></tr>
        <tr><td colspan="2"><html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${enrollmentModifyDisable}"/></td></tr>
    </table>
  </div>
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
        <table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <!--DWLayoutTable-->
      <tr>
          <td width="949" height="25" class="copyright" bgcolor="#038233" style=" margin-left: 5px;"><jsp:include page="../includes/Version.jsp"/></td>
        </tr>
    </table></td>
  </tr>
</table>
  <div id="tooltip" class="tooltip" style="width:210px;">
      <span id="tooltipspan"> </span>
  </div>
  <div id="pop" class="search" style="width:210px;">
    <table><tr><td style="width:208px;"><label id="title" style="color:#FFFFFF; width:198px;">Browse</label></td><td><img name="popClose" src="images/close.jpg" style="width:10px; height:10px;" onClick="hideComponent('pop')"/></td></tr>
        <tr><td colspan="2" align="left"><span><input type="text" name="selectedName" style="width:195px;" style="margin-top:0px;" onkeyup="filterNames(this.value)"/></span></td></tr>
        <tr><td colspan="2"><span id="ovcNames"> </span></td></tr>
    </table>
  </div>
  
</body>
<%--<div class="regsitertable" id="cgiverdiv" style="border: 5px blue solid; visibility: ${visible}; position: absolute; left: 400px; top: 250px; height:600px; width:900px;">
                            <iframe name="partners" width="100%" height="100%" src="caregiverAction.do?formType=span" frameborder="0" scrolling="no" ></iframe><%--<jsp:include page="CaregiverSpan.jsp" />
                        </div>--%>
</html>

