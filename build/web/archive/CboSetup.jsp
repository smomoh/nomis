<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="com.fhi.kidmap.business.*" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Environment Setup</title>
<link rel="stylesheet" href="SpryAssets/LookupTableStyle.css"  />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" >

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
function setActionName(name)
{
    document.getElementById("actionName").value = name
}
</SCRIPT>
</head>
<!--onLoad="getState();"-->
<body class="setuppagebg"  style=" border: 1px black solid">

<html:form action="/cboSetup">
    <html:errors/>
    <html:hidden property="actionName" styleId="actionName" />
    <html:hidden property="cbo_code" value=" " styleId="buttonId" />
    <table >
    <tr><td colspan="3" align="center" height="30"><label style="margin-left:50px; color: blue">Environment setup  </label></td><td> </td></tr> <!--;getLga(this.value)-->
    <tr><td><label style="margin-left:50px;">State</label></td><td> <html:select property="state_code" styleId="state" styleClass="textField" style="margin-left: 10px; width:300px;" onchange="setActionName('lgas'); forms[0].submit()">
                <html:option value=""> </html:option>
                <logic:iterate id="stat" name="statesWithRights">
                <html:option value="${stat.state_code}">${stat.name}</html:option>
            </logic:iterate>
            
            </html:select></td><td>  </td></tr>
    <%--<tr><td><label style="margin-left:50px;">LGA</label></td>
        <td> <html:select property="lga_code" styleId="lgaName" styleClass="textField" style="margin-left: 10px; width:300px;">
              <html:option value=" "> </html:option>
                <logic:iterate name="siteSetupLgaList" id="lga">
                <html:option value="${lga.lga_code}">${lga.lga_name}</html:option>
              </logic:iterate>
            </html:select>
        </td><td>  </td></tr>--%>
    <tr><td><label style="margin-left:50px;">Partner</label></td>
        <td> <html:select property="partner" styleId="partner" styleClass="textField" style="margin-left: 10px; width:300px;"  > <html:option value=""> </html:option>
          <logic:iterate name="listofpartners" id="partner">
            <html:option value="${partner.partnerCode}">${partner.partnerName} </html:option>
          </logic:iterate>
            </html:select></td><td>  </td></tr>
   

<tr><td>  </td><td><select name="setupList" id="setupList" style="height: 220px; width: 300px; margin-left: 10px; margin-right:60px; font-size:9pt;" multiple onDblClick="setModify('stateList','states')"><option> </option></select></td><td>
<fieldset style="margin-left:0px; width:70px;">  <%--disabled="${siteSetupSaveDisabled}"--%>
    <html:submit style="width:75px" value="Save" onclick="return setBtnName('save')" /><br><br>
<html:submit style="width:75px" value="Modify..." disabled="true" onclick="return setBtnName('modify')" /><br><br>
<html:submit style="width:75px" value="Delete..." disabled="true" onclick="return setBtnName('delete')" /><br><br>

</fieldset>
 </td></tr>
</table>
</html:form>
</body>
</html>