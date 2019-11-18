<%-- 
    Document   : OrganizationRecords
    Created on : Aug 8, 2015, 1:40:44 PM
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
<title>Organization setup</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
        font-size: 12px;
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
			
function submitForm(requiredAction,formId)
{
       setActionName(requiredAction)
       document.getElementById(formId).submit()
       return true
}

function setBtnName(name)
{
     if(name=="save" || name=="generateForms")
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
<link href="images/untitled.css" rel="stylesheet" type="text/css" />
<link href="images/sdmenu/sdmenu.css" rel="stylesheet" type="text/css" />
</head>

<body onload="MM_preloadImages('images/About_down.jpg','images/Admin_down.jpg','images/Rapid_down.jpg','images/care_down.jpg','images/OVC_down.jpg');">

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
    <td width="931" height="251" valign="top">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <!--DWLayoutTable-->
      <tr>
        
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
                    <div>
                        <jsp:include page="../Navigation/EnvironmentSetupLinkPage.jsp"/>
                    </div>
              </div>

            </td>
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
          
      </table></td>
    <td width="10">&nbsp;</td>
      <td width="659" class="paramPage">
            <html:form action="/orgrecords" method="post" styleId="orgform">
                    <html:errors/>
                    <html:hidden property="actionName" styleId="actionName" />
                    <html:hidden property="stateCode" styleId="stateCode" />
                    <%--<html:hidden property="lgaCode" styleId="lgaCode" />--%>
                    <html:hidden property="hiddenOrgCode" styleId="hiddenOrgCode" />
                    <br/>
                    <fieldset class="fieldset" style="margin: 10px;"><legend>Organizational information</legend>
            <table align="center" >
                <tr><td>State </td><td>
                        <html:text property="state" styleId="state" styleClass="fieldcellinput" readonly="true" onclick="setActionName('refresh');forms[0].submit()" style="margin-right: 58px"/>
                        </td>
                        <td align="right">LGA</td><td>
                            <html:select property="lgaCode" styleId="lgaCode" styleClass="fieldcellinput">
                                    <logic:iterate name="lgalistperstate" id="lga" >
                                    <html:option value="${lga.lga_code}">${lga.lga_name}</html:option>
                                </logic:iterate>
                        </html:select>
                            <%--<html:text property="lga" styleId="lga" styleClass="fieldcellinput" readonly="true" onclick="setActionName('refresh');forms[0].submit()"/>--%>
                            </td> </tr>
                <tr><td>Type </td><td><html:select property="orgType" styleId="orgType" styleClass="fieldcellinput"> 
                            <html:option value="Government" >Government</html:option><html:option value="CSO" >CSO</html:option></html:select></td>
                    <td align="right">Subtype</td><td><html:select property="orgSubtype" styleId="orgSubtype" styleClass="fieldcellinput"> 
                        <html:option value="dataentry">Data entry</html:option><html:option value="referral">Referral</html:option>
                        <html:option value="training">Training</html:option>
                    </html:select>
                    </td> </tr>
                <tr><td>Name </td><td><html:text property="orgName" styleId="orgName" styleClass="fieldcellinput"/></td><td align="right">Code</td><td><html:text property="orgCode" styleId="orgCode" styleClass="fieldcellinput"/></td></tr>
                <%--<tr><td>Dhis id</td><td><html:text property="dhisId" styleId="dhisId" styleClass="fieldcellinput"/> </td><td><html:checkbox property="dataEntryAllowed" />Enable data entry </td></tr>--%>
                <tr><td>Address</td><td colspan="3"><html:textarea property="address" styleClass="contactslongcell" style="width: 520px;"/>  </td></tr>
                <tr><td colspan="4">
                        <fieldset><legend>Contact persons</legend>
                        <table>
                            <tr><td>Name </td><td><html:text property="contactName1" styleId="contactName1" styleClass="fieldcellinput"/> </td><td>Phone </td><td><html:text property="contactPhone1" styleId="contactPhone1" styleClass="contactscellinput"/> </td><td>Email </td><td><html:text property="contactEmail1" styleId="contactEmail1" styleClass="contactscellinput"/> </td></tr>
                            <tr><td>Name </td><td><html:text property="contactName2" styleId="contactName2" styleClass="fieldcellinput"/> </td><td>Phone </td><td><html:text property="contactPhone2" styleId="contactPhone2" styleClass="contactscellinput"/> </td><td>Email </td><td><html:text property="contactEmail2" styleId="contactEmail2" styleClass="contactscellinput"/> </td></tr>
                            <%--<tr><td>Contact 3 </td><td>Name </td><td><html:text property="contactName3" styleId="contactName3" styleClass="contactscellinput"/> </td><td>Phone </td><td><html:text property="contactPhone3" styleId="contactPhone3" styleClass="contactscellinput"/> </td><td>Email </td><td><html:text property="contactEmail3" styleId="contactEmail3" styleClass="contactscellinput"/> </td></tr>--%>
                        </table>
                        </fieldset>
                    </td>
                </tr>
                <tr><td>Latitude</td><td colspan="2"><html:text property="latitude" styleClass="contactslongcell" styleId="latitude" style="width: 50px; margin-left:20px; margin-right:30px;"/>
                        Longitude<html:text property="longitude" styleClass="contactslongcell" styleId="longitude" style="width: 50px; margin-left:20px;"/>&nbsp;</td>
                    <td>Dhis Id: &nbsp;<html:text property="dhisId" styleId="dhisId" styleClass="fieldcellinput"/></td>
                </tr>
                <tr><td colspan="4">LGA Assignment </td></tr>
                <tr>
                    <td colspan="4">
                        <div style="width:570px; height: 100px; border: 1px black solid;background-color: #D7E5F2; overflow: scroll;">
                        <table>
                    <logic:iterate name="lgalistperstate" id="lga">
                        <tr>
                            <td><html:multibox property="assignedLgas" value="${lga.lga_code}"/>${lga.lga_name} </td>
                    </tr>
                    </logic:iterate>
                    </table>
                        </div>
                        </td>
                </tr>
                <tr><td colspan="4">Services </td></tr>
                <tr>
                    <td colspan="4">
                        <div style="width:570px; height: 100px; border: 1px black solid;background-color: #D7E5F2; overflow: scroll;">
                        <table>
                    <logic:iterate name="serviveList" id="service">
                        <tr>
                            <td><html:multibox property="services" value="${service.serviceName}"/>${service.serviceName}</td>
                    </tr>
                    </logic:iterate>
                    </table>
                        </div>
                        </td>
                </tr>

                <tr>
                    <td colspan="4">
                        <html:select property="orgList" multiple="true" style="width:570px; height:100px;" ondblclick="setActionName('orgDetails');forms[0].submit()">
                            <logic:iterate name="organizationList" id="organization">
                                <html:option value="${organization.orgCode}">${organization.orgName}</html:option>
                            </logic:iterate>
                        </html:select>
                    </td>
                </tr>
            </table>
           </fieldset>
                        <table align="center">
                            <tr>
                        <td align="center">
                            <logic:present name="enableEnvironmentSetup">
                                <html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${orgSaveDiabled}"/>
                                <html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${orgModifyDiabled}"/>
                            </logic:present>
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${orgModifyDiabled}"/>
                           
                        </td>
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
  </body>
</html>
