<%-- 
    Document   : OptionsManager
    Created on : Jun 19, 2018, 7:02:29 AM
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
<title>Options manager</title>
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
.labelColor
{
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
        color: white
}
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
                $("#curagedate").datepicker();
                $("#startDate").datepicker();
                $("#endDate").datepicker();
            });
            
function submitForm(requiredAction,formId)
{
       setActionName(requiredAction)
       document.getElementById(formId).submit()
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
                        <jsp:include page="../Navigation/AdministrationLink.jsp"/>
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
            <html:form action="/options">
    <html:errors/>
    <html:hidden property="actionName" styleId="actionName" />
    <center><br/>
        <fieldset style="margin: 10px;">
<table  style=" font-size: 12px;">
    <tr><td colspan="3"> &nbsp;</td><td> </td></tr>
    <tr><td align="right" class="labelColor" style="margin-left:30px">Current report period</td>
        <td align="left" colspan="2"><html:text property="startDate" styleId="startDate" styleClass="textField" readonly="true" />
            <label class="labelColor">To:</label>&nbsp;<html:text property="endDate" styleId="endDate" styleClass="textField" readonly="true" />  </td>
    </tr>
    <tr><td align="right" style="margin-left:30px" class="labelColor">HHVA version</td>
        <td align="left"> 
            <html:select property="hhvaversion" styleId="hhvaversion" styleClass="textField">
                <html:option value="1">Old Household Vulnerability Assessment form</html:option>
                <html:option value="2">Revised Household Vulnerability Assessment form</html:option>
            </html:select>
        </td><td>  </td>
    </tr>
    <tr><td align="right" style="margin-left:30px" class="labelColor">HIV Risk assessment checklist</td>
        <td align="left"> 
            <html:select property="hracversion" styleId="hracversion" styleClass="textField">
                <html:option value="1">Old HIV Risk assessment checklist</html:option>
                <html:option value="2">National HIV Risk assessment checklist</html:option>
            </html:select>
        </td><td>  </td>
    </tr>
    <tr><td align="right" style="margin-left:30px" class="labelColor">Date to calculate current age</td>
        <td align="left"> <html:text property="curagedate" styleId="curagedate" styleClass="textField" readonly="true"/>
        </td><td>  </td>
    </tr>
    <tr><td align="right" style="margin-left:30px" class="labelColor">Data import</td>
        <td align="left"> 
            <html:select property="xmlDbImport" styleId="xmlDbImport" styleClass="textField">
                <html:option value="1">Enable Data import</html:option>
                <html:option value="2">Disable data import</html:option>
            </html:select>
        </td><td>  </td>
    </tr>
    
    <%--<tr><td align="right" style="margin-left:30px" class="labelColor">Beneficiary information display</td>
        <td align="left"> 
            <html:select property="displayClientRecordInHtml" styleId="displayClientRecordInHtml" styleClass="textField">
                <html:option value="2">Do not allow display of beneficiary details in browser</html:option>
                <html:option value="1">Allow display beneficiary details in browser</html:option>
            </html:select>
        </td><td>  </td>
    </tr>
    <tr><td align="right" style="margin-left:30px" class="labelColor">Beneficiary information download</td>
        <td align="left"> 
            <html:select property="downloadClientRecordInExcel" styleId="downloadClientRecordInExcel" styleClass="textField">
                <html:option value="2">Do not allow download of beneficiary details in excel</html:option>
                <html:option value="1">Allow download of beneficiary details in excel</html:option>
            </html:select>
        </td><td>  </td>
    </tr>--%>
<tr> <td colspan="3" align="center">
    <html:submit style="width:75px; height: 30px;" styleId="save" value="Save" disabled="${saveDisabled}" onclick="return setBtnName('save')"/>
    <%--<html:submit style="width:75px; height: 30px;" styleId="modify" value="Modify..." disabled="${modifyDisabled}" onclick="return setBtnName('modify')" />
    <html:submit style="width:75px; height: 30px;" styleId="deleteBtn" value="Delete..." disabled="${modifyDisabled}" onclick="return setBtnName('delete')" />--%>

</td></tr>
</table>
    </fieldset>
    </center>
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
