<%-- 
    Document   : GraduationCheckList
    Created on : Oct 2, 2016, 2:07:04 PM
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
<title>Household graduation checklist </title>
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
function checkForGraduation()
{
    if(document.getElementById("graduated").checked)
    {
        if(confirm("This household will be graduated. Do you want to proceed?"))
        return true
        else
        return false
    }
    return true
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
                    <a href="graduationCheckListAction.do">Graduation checklist </a>
                    <a href="householdMergeAction.do" target="_blank">Beneficiary records management </a>
                    <a href="#"> </a>
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
                    <a href="#" > </a>
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
	 <td width="30%" height="39" class="homepagestyle">Household graduation checklist</td>
	 <td width="70%"><html:errors/></td>
    </tr>
    <tr>
        <td class="homepagestyle" colspan="2" align="center"><logic:present name="msg"><label style="color:red; font-size: 15px;">${msg}</label></logic:present> </td>
	 
    </tr>

  </table>

         <html:form action="/graduationCheckListAction" method="post" styleId="hhFormId">
<html:hidden property="actionName" styleId="actionName"/>
<html:hidden property="stateCode" styleId="stateCode" value="${state.state_code}" />
<html:hidden property="id" styleId="id" />
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
                        		
							<tr>
                            <td width="5%"><span style="color:#333">State:</span></td>
                          <td width="30%">
                              <html:text styleId="stateList" property="state" styleClass="bigfieldcellinput" disabled="true" value="${state.name}" />
                          </td>
                              <td width="5%"><span style="color:#333">LGA:</span></td>
                              <td width="30%"> 
                                  
                                  <html:select property="lgaCode" styleClass="bigfieldcellinput" styleId="lgaCode" onchange="setActionName('cboList');forms[0].submit()">
                                      <logic:present name="lgaListInOrgRecords">    
                                      <logic:iterate name="lgaListInOrgRecords" id="lga">
                                              <html:option value="${lga.lga_code}" >${lga.lga_name}</html:option>
                                          </logic:iterate>
                                      </logic:present>
                                      </html:select>
                                  
                                  <%--<input type="text" Class="bigfieldcellinput" id="LgasSelect" disabled="true" value="${lga.lga_name}"/>--%>
                              </td>
                                                        </tr>
                              <tr>
                              <td width="5%"><span style="color:#333">CBO:</span></td>
                              <td width="31%">
                                  
                                  <html:select property="orgCode" styleId="orgCode" styleClass="bigfieldcellinput">
                              <html:option value=""> </html:option>
                              <logic:present name="cboListInOrgRecords">
                                      <logic:iterate id="cbos" name="cboListInOrgRecords" >
                                              <html:option value="${cbos.orgCode}">${cbos.orgName} </html:option>
                                          </logic:iterate>
                                      </logic:present>
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
                      <td width="762" valign="top">

					  <fieldset>
                        <legend class="fieldset">Personal Information  </legend>
                        <table width="100%" class="regsitertable"><%--uniqueIdLabel--%>
                              
                              <tr><td align="left" width="110">Serial No. </td><td><html:text property="hhSerialNo"  styleId="hhSerialNo" styleClass="smallfieldcellinput" onkeyup="return constructUniqueId(this.value)" onblur="return submitForm('hhDetails');"/> <label id="uniqueIdLabel" >${hhUniqueId}</label></td>
                                  <td align="right">Date of enrollment </td><td><html:text property="dateOfEnrollment" styleId="dateOfEnrollment" styleClass="smallfieldcellinput"/>(mm/dd/yyyy)</td></tr>
                            <tr><td align="left">Surname </td><td><html:text property="hhSurname" styleId="hhSurname" styleClass="fieldcellinput" onkeyup="capitalizeAll('hhSurname', this.value)" /> </td>
                                <td align="right">First name </td><td><html:text property="hhFirstname" styleId="hhFirstname" styleClass="smallfieldcellinput" /> </td></tr>
                            <tr><td align="left">Age </td><td><html:text property="hhAge" styleId="hhAge" styleClass="smallfieldcellinput" style="margin-right: 5px;width:68px;" /> Sex <html:text property="hhGender" styleId="hhGender" styleClass="smallfieldcellinput"/>
                                        </td>
                                <td align="right">Phone No.</td><td><html:text property="phone" styleId="phone" styleClass="fieldcellinput"/> </td></tr>
                            
                            <tr><td align="left">Address </td><td><html:textarea property="address" styleId="address" rows="3" cols="80" styleClass="textareacellinput"></html:textarea></td><td align="right">Occupation</td><td><html:text property="occupation" styleId="occupation" styleClass="fieldcellinput"/></td></tr>
                            <!--hhgclUniqueId-->
                          </table>
                       
                      </fieldset>


					  </td>
                      </tr>

                  </table></td>
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
                              <html:text property="dateOfAssessment" styleId="dateOfAssessment" styleClass="smallfieldcellinput" readonly="true"/>&nbsp;(mm/dd/yyyy)
                          </td>

                        </tr>
                        </table>
                          <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                              <td class="right" colspan="2">Caregiver knows the HIV status of all the children in the household</td>
                              <td >
                                  <html:select styleClass="fieldcellinput" property="health" style="width:82px;" styleId="health" >
                                     <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">All children in the household have birth certificates</td>
                              <td >
                                  <html:select styleClass="fieldcellinput" property="safety" style="width:82px;" styleId="safety"  >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">Caregiver has accessed either agricultural input support, vocational skills training, participation in SILC or cash transfer within the past year</td>
                              <td>
                                  <html:select styleClass="fieldcellinput" property="stability" style="width:82px;" styleId="stability" >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          
                          <tr>
                              <td class="right" colspan="2">All children aged 5 years and above in the household enrolled in, and attending school</td><td >
                                  <html:select styleClass="fieldcellinput" property="school" style="width:82px;" styleId="school">
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">Household has achieved demonstrable reduction in vulnerability (as evidenced by follow-up assessment)</td><td >
                                  <html:select styleClass="fieldcellinput" property="vulnerability" style="width:82px;" styleId="vulnerability" >
                                      <html:option value=""> </html:option><html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select> </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="2">Household graduated from the program </td><td >
                                  <html:checkbox styleClass="fieldcellinput" property="graduated" style="width:82px;" styleId="graduated" value="Graduated" />
                                      
                                   </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="3">Volunteer name
                                  <html:text styleClass="fieldcellinput" property="volunteerId" styleId="volunteerId" style="margin-left: 20px;"/>
                                  <%--Designation
                                  <html:text styleClass="fieldcellinput" property="designation" styleId="designation" style="margin-left: 20px;"/>--%>
                                      
                                   </td>
                          </tr>
                          <tr>
				<td height="40" align="center">
                           <html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save');return checkForGraduation()" disabled="${gclSaveBtnDisabled}"/>
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${gclModifyBtnDisabled}"/>
                           <html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${gclModifyBtnDisabled}"/>

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
