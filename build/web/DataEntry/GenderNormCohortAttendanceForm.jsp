<%-- 
    Document   : GenderNormCohortAttendanceForm
    Created on : Sep 24, 2017, 3:12:05 PM
    Author     : smomoh
--%>

<%-- 
    Document   : OvcReferralForm
    Created on : Dec 9, 2011, 3:04:09 PM
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
<title>Gender norm attendance form </title>
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
                
                $("#dateOfAttendance").datepicker();
            });



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
    if(value == "Yes") {
        document.getElementById("reasonWithdrawal1").disabled = false;
        document.getElementById("reasonWithdrawal2").disabled = false;
        document.getElementById("reasonWithdrawal3").disabled = false;
        document.getElementById("reasonWithdrawal4").disabled = false;
    }
    else {
        document.getElementById("reasonWithdrawal1").disabled = true;
        document.getElementById("reasonWithdrawal2").disabled = true;
        document.getElementById("reasonWithdrawal3").disabled = true;
        document.getElementById("reasonWithdrawal4").disabled = true;
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
              <div> <a href="gendernormcohortattendancereport.do" target="_blank"> Gender norm cohort attendance report </a><a href="#"> &nbsp; </a><a href="#"> &nbsp; </a></div>
            </div></td>
          </tr>


      </table></td>
    <td width="13">&nbsp;</td>
      <td width="639" >

        
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
	 <td width="30%" height="39" class="homepagestyle">Gender norm attendance form </td>
	 <td width="70%"><html:errors/></td>
    </tr>

  </table>

         <html:form styleId="dataForm" action="/gendernormcohortattendance" method="post">
<html:hidden property="actionName" styleId="actionName"/>
<html:hidden property="recordId" styleId="recordId" />
<%--<html:hidden property="lga" styleId="lgaId" value="${lga.lga_code}" />--%>
<input type="hidden" name="lgaCode" id="lgaCode" />
<input type="hidden" name="stateId" id="stateId" value="${state.state_code}" />
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

                        		<tr><td colspan="4" align="center"><label style="color:red;">${ovcWithdrawn}</label></td></tr>

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

                                  <html:select styleId="cboList" property="completedbyCbo" styleClass="bigfieldcellinput" onchange="setActionName('cbo'); forms[0].submit()">
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
                  <td height="150" valign="top">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="100" valign="top">
		  <fieldset>
                        <legend class="fieldset">Personal Information  </legend>
                        <table width="100%" class="regsitertable">
                        <tr><td width="15%" valign="top" class="right">HH Unique SNo</td>
                            <td><html:text property="hhSerialNo" styleId="hhSerialNo" style="width:50px;" styleClass="smallfieldcellinput" onkeyup="constructUniqueId('cboList',this.value,5)" onchange="submitForm('hhDetails',this.value,5)"/>
                            <label id="uniqueIdLabel" >${vcHhUniqueId} </label>
                            </td><td>Target group</td>
                            <td><html:select property="targetGroup" styleId="targetGroup" styleClass="fieldcellinput" onchange="submitForm('hhDetails',this.value,5)">
                                    <html:option value="OVC" >Child</html:option>
                                    <html:option value="Caregiver" >Caregiver</html:option>
                                </html:select></td><td></td><td> </td>
                        </tr>
                        <tr><td width="15%" valign="top" class="right">${gncafName}</td>

                            <td>
                                <html:select property="clientId"  styleClass="fieldcellinput" styleId="clientId"  onchange="submitForm('baselineDetails',this.value,5)" >
                                    <html:option value=""></html:option>
                                    <logic:present name="gncafOvcList">
                                    <logic:iterate name="gncafOvcList" id="ovc">
                                        <html:option value="${ovc.ovcId}">${ovc.firstName} ${ovc.lastName}</html:option>
                                    </logic:iterate>
                                    </logic:present>
                                    <logic:present name="gncafCaregiverList">
                                    <logic:iterate name="gncafCaregiverList" id="cgiver">
                                        <html:option value="${cgiver.caregiverId}">${cgiver.caregiverFirstname} ${cgiver.caregiverLastName}</html:option>
                                    </logic:iterate>
                                    </logic:present>
                                </html:select>
                            </td><td>${gncafId}</td><td colspan="2">${gncafIdValue} </td><td></td>
                              
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
                            <td valign="top" class="right" style=" height: 30px;" rowspan="2"><br/>Address: </td>
                            <td rowspan="2">
                                  <html:textarea property="address" rows="3" cols="80" styleClass="fieldcellinput" styleId="address" readonly="true" ></html:textarea>
                              </td><td>Telephone:</td><td colspan="3"><html:text property="phone" styleClass="smallfieldcellinput" styleId="phone" readonly="true" /></td>
                                
                            </tr>
                            <tr> <td>Caregiver name:</td><td colspan="3"><html:text property="caregiverName" styleClass="fieldcellinput" styleId="caregiverName" readonly="true" /></td></tr>
                            
                            <tr><td style="height: 40px;" colspan="4"><label>Session Number: ${sessionNumber}</label> <label style="margin-left:40px;">Number of sessions: ${sessionCount}</label></td></tr>
                        </table>
                      </fieldset>
        	  </td>
                      </tr>

                  </table></td>
                </tr>

                <tr>
                  <td height="100" valign="top"><fieldset>
                        
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="17">
                          <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">

			<tr>
                            <td width="25.5%"><label style=" margin-right: 20px;">Date of attendance:</label> </td>
                          <td width="74.5%" colspan="2">
                              <html:text property="dateOfAttendance" styleId="dateOfAttendance" styleClass="smallfieldcellinput" style="margin-left: 4px;" onchange="setActionName('gncadetails'); forms[0].submit()" readonly="true"/>&nbsp;(mm/dd/yyyy)
                          </td>

                        </tr>
                        <tr>
                            <td width="25.5%"><label style=" margin-right: 20px;">Name of PCV/PO</label> </td>
                          <td width="74.5%" colspan="2">
                              <html:text property="pcvName" styleId="pcvName" styleClass="fieldcellinput" style="margin-left: 4px;" />
                          </td>

                        </tr>
                        
                        </table>
                          
                      </td></tr>
                          
                              </table>

                        
                           </td>
                      </tr>
                      <tr>
				<td height="20" align="center">
                            
                            <html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${gncafSaveDisabled}"/>
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${gncafModifyDisabled}"/><html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${gncafModifyDisabled}"/>

                        </td>
				</tr>
                                
                  </table>
                  <%--</fieldset>
                            
               </td>
                </tr>
                
                <tr><td height="20">
                        &nbsp;
                    </td></tr>
                
                
                

                <tr>
				<td height="30">&nbsp;</td>
				</tr>



            </table>--%>


			</html:form>
			

          &nbsp;</td>
      </tr>

    </table>    
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