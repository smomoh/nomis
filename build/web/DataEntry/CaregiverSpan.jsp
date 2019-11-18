<%-- 
    Document   : CaregiverSpan
    Created on : Sep 28, 2015, 11:06:55 PM
    Author     : smomoh
--%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Caregiver form </title>
<script language="JavaScript" src="kidmap.js" type="text/JavaScript"></script>
<link type="text/css" href="css/ui-darkness/jquery-ui-1.8.custom.css" rel="Stylesheet" />
 <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
 <script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
 <script type="text/javascript" src="js/Enrollmentjsfile.js"></script>
 <script language="javascript">
function constructUniqueId2(SNo)
{
    var orgCode=document.getElementById("cgiverorgCode").value
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
    var uniqueId=document.getElementById("stateCode").value+"/"+document.getElementById("cgiverLgaCode").value+"/"+orgCode+"/"+paddedSNo
    document.getElementById("cgiverhhUniqueId").value=uniqueId
    document.getElementById("cgiveruniqueIdLabel").innerHTML=uniqueId
    
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
function submitCaregiverForm(requiredAction,formId)
{
    setCaregiverActionName(requiredAction)
    document.getElementById(formId).submit()
}
function setBtnName(name)
{
     if(name=="save")
     {
            setCaregiverActionName(name)
            return true
     }
     if(confirm("Are you sure you want to "+name+" this record?"))
     {
            setCaregiverActionName(name)
            return true
     }
       return false
}
function setCaregiverActionName(val)
{
    document.getElementById("actionName").value=val
}
function testAjaxPost()
{
    var req="searchCaregiverInHousehold;"
    alert(req)
     getValuesFromDb('caregiverAction.do',req,'searchCaregiverInHousehold')
}
</script>
</head>
<body>
<span>
    <html:form action="/caregiverAction" method="post" styleId="cgiverFormId">
<html:hidden property="actionName" styleId="actionName"/>
<html:hidden property="formType" styleId="formType" value="span"/>
<html:hidden property="stateCode" styleId="stateCode" value="${state.state_code}" />
<html:hidden property="hhUniqueId" styleClass="fieldcellinput" styleId="cgiverhhUniqueId" />


  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!--DWLayoutTable-->
                <tr>
                  <td width="762" height="28" valign="top">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="27">
                          <table width="100%" class="regsitertable">
                              <tr><td colspan="4" align="center">Caregiver form</td></tr>
                        		<tr><td colspan="4"> &nbsp;</td></tr>
			<tr>
                            <td width="5%"><span style="color:#333">State:</span></td>
                          <td width="30%">
                              <html:text styleId="stateList" property="state" styleClass="bigfieldcellinput" disabled="true" value="${state.name}" />
                          </td>
                              <td width="5%"><span style="color:#333">LGA:</span></td>
                              <td width="30%">

                                  <html:select property="lgaCode" styleClass="bigfieldcellinput" styleId="cgiverLgaCode" onchange="submitCaregiverForm('cboList','cgiverFormId')">
                                      <html:option value=""></html:option>
                                      <html:optionsCollection name="CaregiverForm" property="lgaList" label="lga_name" value="lga_code" />

                                      </html:select>


                              </td>
                            </tr>
                              <tr>
                              <td width="5%"><span style="color:#333">CBO:</span></td>
                              <td width="31%">

                                  <html:select property="orgCode" styleId="cgiverorgCode" styleClass="bigfieldcellinput" onchange="submitCaregiverForm('refresh','cgiverFormId')">
                              <html:option value=""> </html:option>
                              <html:optionsCollection name="CaregiverForm" property="cboList" label="orgName" value="orgCode" />

                                            </html:select>
                              </td>
                              <td width="5%"><span style="color:#333">Community</span></td>
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

                            <tr><td class="right" width="17%">Household S/No.</td>

                            <td>
                                <html:text property="serialNo"  styleClass="smallfieldcellinput" styleId="serialNo"  onblur="submitCaregiverForm('caregiverList','cgiverFormId')" onkeyup="constructUniqueId2(this.value)"/> </td>
                            <td><label id="cgiveruniqueIdLabel">${hhServiceUniqueId}</label></td><td colspan="3"></td>
                        </tr>

                        <tr>
                          <td class="right">Existing caregivers </td>
                          <td colspan="5">
                                  <html:select property="caregiverId" styleClass="fieldcellinput"  styleId="caregiverId" onchange="submitCaregiverForm('caregiverDetails','cgiverFormId')">
                                      <html:option value=""> </html:option>
                                      <html:optionsCollection name="CaregiverForm" property="caregiverList" label="caregiverFullName" value="caregiverId" />

                                  </html:select>  </td>
                        </tr>
                        <tr>

                            <td colspan="6">
                                <fieldset>
                        <legend class="fieldset">New Caregiver information </legend>
                        <table width="100%" class="regsitertable">
                            <tr>
                          <td width="17%" bgcolor="#F0F0F0"><div align="left">Surname: </div></td>
                          <td width="80%" bgcolor="#F0F0F0"><html:text property="caregiverLastName" styleId="caregiverLastName" styleClass="fieldcellinput" />
                              <span style=" margin-left:10px; margin-right: 11px;">First name:</span> <html:text property="caregiverFirstname" styleId="caregiverFirstname" styleClass="fieldcellinput" /></td>
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
                            <td ><strong>HIV status: </strong></td>
                            <td colspan="5">                              HIV Unknown
                              <html:radio property="cgiverHivStatus" value="HIV Unknown" styleClass="smallfieldcellselect" styleId="cgiverHivStatus1"/>
                            &nbsp;&nbsp; HIV negative
                            <html:radio property="cgiverHivStatus" value="HIV negative" styleClass="smallfieldcellselect" styleId="cgiverHivStatus2"/>
                            &nbsp; HIV Positive
                            <html:radio property="cgiverHivStatus" value="HIV Positive" styleClass="smallfieldcellselect" styleId="cgiverHivStatus3"/></td>
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

                                    <html:submit value="Save" property="savebtn" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${caregiverSaveBtnDisabled}"/>
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${caregiverModifyBtnDisabled}"/>
                           <html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${caregiverModifyBtnDisabled}"/>
                           <input type="button" value="Close" id="closeBtn"  onclick="return hideComponent('cgiverdiv')" style="width:70px; height:25px; margin-right:5px;" />

                        </td>
				</tr>

                <tr>
				<td height="30">&nbsp;</td>
				</tr>
            </table>
			</html:form>
</span>
</body>
</html>