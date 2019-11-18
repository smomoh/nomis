<%-- 
    Document   : CaregiverExpenditureAndSchAttendanceForm
    Created on : Mar 18, 2018, 6:22:28 AM
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
<title>Caregiver emergency fund and child school attendance form </title>
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
function changeHowMoneyIsRaised(value)
{
   if(value=="Yes")
   {
        document.getElementById("loan").disabled=false
       document.getElementById("personalSavings").disabled=false
       document.getElementById("incomeFromTrade").disabled=false
       document.getElementById("salary").disabled=false
       document.getElementById("soldItems").disabled=false
       document.getElementById("borrowed").disabled=false
       document.getElementById("lackofmotivation").disabled=false
   }
   else
   {
       document.getElementById("loan").disabled=true
       document.getElementById("personalSavings").disabled=true
       document.getElementById("incomeFromTrade").disabled=true
       document.getElementById("salary").disabled=true
       document.getElementById("soldItems").disabled=true
       document.getElementById("borrowed").disabled=true
       document.getElementById("lackofmotivation").disabled=true
       unselectChkBoxes("sourceOfMoney")
   }
}
function disableEnableUrgentHhNeedsControls(value)
{
   if(value=="Yes")
   {
       document.getElementById("investment").disabled=false
       document.getElementById("transport").disabled=false
       document.getElementById("gift").disabled=false
       document.getElementById("water").disabled=false
       document.getElementById("firewood").disabled=false
       document.getElementById("livestock").disabled=false
       document.getElementById("medical").disabled=false
       document.getElementById("agricInputs").disabled=false
       document.getElementById("clothsandshoes").disabled=false
       document.getElementById("schoolFees").disabled=false
       document.getElementById("savingsorsilc").disabled=false
       
       document.getElementById("clothsandshoes").disabled=false
       document.getElementById("debtRepayment").disabled=false
       document.getElementById("householdItems").disabled=false
       document.getElementById("food").disabled=false
       document.getElementById("rentOrShelterMaintenance").disabled=false
       
       
   }
   else
   {
       document.getElementById("investment").disabled=true
       document.getElementById("transport").disabled=true
       document.getElementById("gift").disabled=true
       document.getElementById("water").disabled=true
       document.getElementById("firewood").disabled=true
       document.getElementById("livestock").disabled=true
       document.getElementById("medical").disabled=true
       document.getElementById("agricInputs").disabled=true
       document.getElementById("clothsandshoes").disabled=true
       document.getElementById("schoolFees").disabled=true
       document.getElementById("savingsorsilc").disabled=true
       
       document.getElementById("clothsandshoes").disabled=true
       document.getElementById("debtRepayment").disabled=true
       document.getElementById("householdItems").disabled=true
       document.getElementById("food").disabled=true
       document.getElementById("rentOrShelterMaintenance").disabled=true
       unselectChkBoxes("urgentHhNeeds")
   }
}
function disableEnableOtherControls(value)
{
    changeHowMoneyIsRaised(value)
    disableEnableUrgentHhNeedsControls(value)
    if(value=="Yes")
    {
        document.getElementById("accessMoney").disabled=false
    }
    else
    {
        document.getElementById("accessMoney").disabled=true
    }
}
function unselectChkBoxes(chkname)
{
   var elements=document.getElementsByName(chkname)
    for(var i=0; i<elements.length; i++)
    {
        elements[i].checked=false
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
    document.getElementById("hhSerialNo").value=""
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
            <td height="157" valign="top">
                <div style="float: left" id="my_menu2" class="sdmenu" >
              <div>
                    <a href="caregiverexpreport.do" target="_blank">Caregiver Expenditure and school attendance report </a>
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
	 <td width="30%" height="39" class="homepagestyle">Caregiver emergency fund and child school attendance form </td>
	 <td width="70%"><html:errors/></td>
    </tr>
    <tr>
        <td class="homepagestyle" colspan="2" align="center"><logic:present name="msg"><label style="color:red; font-size: 15px;">${msg}</label></logic:present> </td>
	 
    </tr>

  </table>

         <html:form action="/cgexpandschattend" method="post" styleId="hhFormId">
<html:hidden property="actionName" styleId="actionName"/>
<html:hidden property="stateCode" styleId="stateCode" value="${state.state_code}" />
<%--<html:hidden property="lgaCode" styleId="lgaCode" value="${lga.lga_code}" />--%>
<html:hidden property="id" styleId="id" />
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
                                <html:text property="hhSerialNo"  styleClass="smallfieldcellinput" styleId="hhSerialNo"  onblur="submitForm('caregiverList','hhFormId')" onkeyup="constructUniqueId(this.value)"/> </td>
                                <td colspan="4"><label id="uniqueIdLabel"> ${hhUniqueIdInEcons}</label></td>
                        </tr>
                        
                        <tr>
                          <td valign="top" class="right">Caregiver name: </td>
                              <td>
                                  <html:select property="caregiverId" styleClass="fieldcellinput"  styleId="caregiverId" onchange="submitForm('caregiverDetails','hhFormId')">
                                      <html:option value=""> </html:option>
                                      <logic:present name="caregiverListInEcons">
                                      <logic:iterate name="caregiverListInEcons" id="cgiver">
                                          <html:option value="${cgiver.caregiverId}">${cgiver.caregiverFirstname} ${cgiver.caregiverLastName} </html:option>
                                      </logic:iterate>
                                      </logic:present>
                                  </html:select>  </td>
                              <td>Sex</td><td><html:text property="careiverGender" styleClass="fieldcellinput" styleId="careiverGender" readonly="true" style="width:70px;" /> </td>
                              <td>Age</td><td><html:text property="caregiverAge" styleClass="shortfieldcellinput" styleId="caregiverAge" readonly="true"  /></td>
                            </tr>
                       
			</table>
                      </fieldset>


					  </td>
                      </tr>

                  </table></td>
                </tr>

                <tr>
                  <td valign="top"><fieldset>
                        <legend class="fieldset">Caregiver access to emergency fund </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="17">
                          <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">

			<tr>
                          <td width="20%">Date of assessment: </td>
                          <td width="80%" colspan="2">
                              <html:text property="dateOfAssessment" styleId="dateOfAssessment" styleClass="smallfieldcellinput" onchange="setActionName('assessmentDetails'); forms[0].submit()" readonly="true"/>&nbsp;(mm/dd/yyyy)
                          </td>

                        </tr>
                        </table>
                          <table>
                              <tr>
                              <td class="right" colspan="2">Did you make any unexpected expenditure in the past six (6) months?</td><td colspan="2">
                                  <html:select styleClass="fieldcellinput" property="unexpectedExpenditure" styleId="unexpectedExpenditure" style="width:82px;" onchange="disableEnableOtherControls(this.value)" >
                                     <html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select>
                                                                        
                                   </td>
                          </tr>
                          <tr><td colspan="4">If No go to section B (Child School attendance)</td></tr>
                          <tr>
                              <td class="right" colspan="2">If Yes to the question above, were you able to access money to pay for this unexpected expenditure?</td>
                              <td colspan="2">
                                  <html:select styleClass="fieldcellinput" property="accessMoney" styleId="accessMoney" style="width:82px;" onchange="changeHowMoneyIsRaised(this.value)" >
                                     <html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select>
                                                                        
                                   </td>
                          </tr>
                          <tr>
                              <td class="right" colspan="4">If No go to (iii), if Yes to the question above, how were you able to raise the money? (tick as appropriate)</td>
                          </tr>
                          <tr>
                      <td width="752" height="102" colspan="4">
                          <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              
                              <tr>
                                  <td>Income from trade </td> <td><html:multibox property='sourceOfMoney' styleId="incomeFromTrade" styleClass='smallfieldcellselect' value="Income from trade" disabled="true"/> </td>
                                  <td>Through my salary </td> <td><html:multibox property='sourceOfMoney' styleId="salary" styleClass='smallfieldcellselect' value="salary" disabled="true"/> </td>    
                                  </tr>
                                  <tr>
                                      <td>I sold some items in the house </td> <td><html:multibox property='sourceOfMoney' styleId="soldItems" styleClass='smallfieldcellselect' value="Sold items in the house" disabled="true"/> </td>
                                      <td>Borrowed from a friend </td> <td><html:multibox property='sourceOfMoney' styleId="borrowed" styleClass='smallfieldcellselect' value="Borrowed from a friend" disabled="true"/> </td>
                                  </tr>
                                  <tr>
                                      <td>Took a loan, received amount saved or social fund from a SILC group </td> <td><html:multibox property='sourceOfMoney' styleId="loan" styleClass='smallfieldcellselect' value="Took a loan received amount saved or social fund from a SILC group" disabled="true"/> </td>
                                      <td>From my personal savings </td> <td><html:multibox property='sourceOfMoney' styleId="personalSavings" styleClass='smallfieldcellselect' value="Personal savings" disabled="true"/> </td>
                                  </tr>
                                  
                        </table>

                      </td>
                      </tr>
                      
                      <tr>
                      <td width="752" height="102" colspan="4">
                          <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <tr><td colspan="4">What are the household (HH) needs requiring routine and/or emergency cash to address? (tick as appropriate) </td> 
                                  </tr>
                                  
                              <tr><td>Food</td> <td><html:multibox property='urgentHhNeeds' styleId="food" styleClass='smallfieldcellselect' value="Food" /> </td>
                                      <td>Business investment</td> <td><html:multibox property='urgentHhNeeds' styleId="investment" styleClass='smallfieldcellselect' value="Business investment" /> </td>
                                  </tr>
                                  <tr>
                                      <td>Transport </td> <td><html:multibox property='urgentHhNeeds' styleId="transport" styleClass='smallfieldcellselect' value="Transport" /> </td>
                                      <td>Household items </td> <td><html:multibox property='urgentHhNeeds' styleId="householdItems" styleClass='smallfieldcellselect' value="Household items" /> </td>
                                  </tr>
                                  <tr>
                                      <td>Gift </td> <td><html:multibox property='urgentHhNeeds' styleId="gift" styleClass='smallfieldcellselect' value="gift" /> </td>
                                      <td>Water </td> 
                                      <td><html:multibox property='urgentHhNeeds' styleId="water" styleClass='smallfieldcellselect' value="water" /> </td>
                                  </tr>
                                  <tr>
                                      <td>House rent or shelter materials </td> <td><html:multibox property='urgentHhNeeds' styleId="rentOrShelterMaintenance" styleClass='smallfieldcellselect' value="House rent or shelter materials" /> </td>
                                      <td>Fire wood </td> <td><html:multibox property='urgentHhNeeds' styleId="firewood" styleClass='smallfieldcellselect' value="Fire wood" /> </td>
                                  </tr>
                                  
                                  <tr>
                                      <td>Livestock </td> <td><html:multibox property='urgentHhNeeds' styleId="livestock" styleClass='smallfieldcellselect' value="livestock" /> </td>
                                      <td>Medical </td> <td><html:multibox property='urgentHhNeeds' styleId="medical" styleClass='smallfieldcellselect' value="Medical" /> </td>
                                  </tr>
                                  <tr>
                                      <td>Agricultural inputs </td> <td><html:multibox property='urgentHhNeeds' styleId="agricInputs" styleClass='smallfieldcellselect' value="Agricultural inputs" /> </td>
                                      <td>Cloths/shoes </td> <td><html:multibox property='urgentHhNeeds' styleId="clothsandshoes" styleClass='smallfieldcellselect' value="Cloths and shoes" /> </td>
                                  </tr>
                                  <tr>
                                      <td>School fees </td> <td><html:multibox property='urgentHhNeeds' styleId="schoolFees" styleClass='smallfieldcellselect' value="School fees" /> </td>
                                      <td>Debt repayment </td> <td><html:multibox property='urgentHhNeeds' styleId="debtRepayment" styleClass='smallfieldcellselect' value="Debt repayment" /> </td>
                                  </tr>
                                  <tr>
                                      <td>Savings/in hand/SILC </td> <td><html:multibox property='urgentHhNeeds' styleId="savingsorsilc" styleClass='smallfieldcellselect' value="Savings/in hand/SILC" /> </td>
                                      <td> </td> <td> </td>
                                  </tr>
                        </table>

                      </td>
                      </tr>
                       
                      <%--<tr><td class="right" colspan="4" align="right"><label style=" font-size: 16px; font-weight: bold">School attendance</label></td></tr>   
                      <tr>
                              <td class="right" colspan="3">Did your child/children miss school/vocational training for more than 5 days in the last one month (4 weeks) of full academic/vocational training session?</td><td colspan="2">
                                  <html:select styleClass="fieldcellinput" property="schAttendance" styleId="schAttendance" style="width:82px;" >
                                     <html:option value="No">No</html:option><html:option value="Yes">Yes</html:option>
                                  </html:select>
                                                                        
                               </td>
                          </tr>
                          
                        </tr>
                          <tr><td class="right" colspan="4">
                                  <fieldset>
                        <legend class="fieldset">Select OVC </legend>
                        <div style="width:720px; height:80px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="80">
                          <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              
                                  <logic:present name="ovcListInEcons">
                                    <logic:iterate name="ovcListInEcons" id="ovc">
                                        
                                        <tr><td border="0"><html:multibox property="ovcId" value="${ovc.ovcId}"/>${ovc.firstName} ${ovc.lastName}
                                                &nbsp;Sex:${ovc.gender} &nbsp;Age:${ovc.age}</td>
           
                                        </tr>
                                    </logic:iterate>
                                        
                                    </logic:present>
                             
                        </table>

                      </td>
                      </tr>
                      
                  </table>
                </div>
                  </fieldset>
                    </td>
                        </tr>
                        <tr><td colspan="4">Reasons for missing school: <html:textarea property="reasonsForMissingSch" styleId="reasonsForMissingSch" style="width:600px" /> </td></tr>
                        <tr><td colspan="2">Name of Volunteer: <html:text property="volunteerName" styleClass="fieldcellinput" styleId="volunteerName"/></td>
                            <td >Phone</td><td ><html:text property="volunteerPhone" styleClass="fieldcellinput" styleId="volunteerPhone"/></td>
                          </tr>
                          </table>
                        
                      </td>
                      </tr>
                  </table>
                  </fieldset>

                  </td>
                </tr>--%>
                 <tr><td colspan="2">Name of Volunteer: <html:text property="volunteerName" styleClass="fieldcellinput" styleId="volunteerName"/></td>
                            <td >Phone</td><td ><html:text property="volunteerPhone" styleClass="fieldcellinput" styleId="volunteerPhone"/></td>
                          </tr>            
                
                <tr>
				<td height="40" align="center">
                            
                            <html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${ovcEconsSaveBtnDisabled}"/>
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${ovcEconsModifyBtnDisabled}"/><html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${ovcEconsModifyBtnDisabled}"/>

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

