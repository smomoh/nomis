<%-- 
    Document   : ReferralDirectory
    Created on : Mar 5, 2017, 8:57:55 PM
    Author     : smomoh
--%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
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
<link type="text/css" href="css/ui-darkness/jquery-ui-1.8.custom.css" rel="Stylesheet" />
<link href="kidmap-default.css" rel="stylesheet" type="text/css" />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="kidmap.js" type="text/JavaScript"></script>
 <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
 <script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
 
<script type="text/javascript">
function setBtnName(name)
{
     if(name=="save")
     {
            setActionName(name)
            return true
     }
     else if(name=="delete")
     {
            if(confirm("This action will permanently delete this record\n"+" Are you sure you want to "+name+" these records?"))
            {
                if(confirm("Kindly click the OK button to continue delete or click Cancel button to cancel this operation"))
                {
                    setActionName(name)
                    return true
                }
            }
     }
     else(confirm("Are you sure you want to "+name+" this record?"))
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
</head>

<body onload="MM_preloadImages('images/About_down.jpg','images/Admin_down.jpg','images/Rapid_down.jpg','images/care_down.jpg','images/OVC_down.jpg');">
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
                    <!--onmouseover="setToolTipText('Click this link to get to the enrollment data entry page','30%','30%')" onmouseout="hideComponent('tooltip')"-->
                    <div><jsp:include page="../Navigation/OrganizationUnitLinks.jsp"/></div>
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
              <div> </div>
            </div></td>
          </tr>


      </table></td>
    <td width="13">&nbsp;</td>
      <td width="739" >

          <div align="center" style="border:1px black solid">
			
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td width="30%" height="20" align="center" class="homepagestyle">Facility setup </td>
     </tr>
     <tr><td height="10" colspan="2" align="center"><label style=" font-size: 15px; color: green"><%--${partnername}--%></label> </td></tr>
  </table>
    <html:form  action="/referralaction" method="POST">
    <html:hidden property="actionName" styleId="actionName"/>
    <html:hidden property="facilityId" styleId="facilityId"/>
    <table>
        <tr><td align="center" colspan="4"> <html:errors /> </td></tr>
        <tr><td align="center" colspan="4"> &nbsp; </td></tr>
    </table>
        <table class="paramPage">
            
            <tr><td class="orglabel" align="left">State </td>
                <td align="left">
            <html:select property="state" styleId="state" style="width: 200px;" onchange="setActionName('lga'); forms[0].submit()">
                <logic:present name="stateListInDirectory">
                    <logic:iterate id="state" name="stateListInDirectory">
                        <html:option value="${state.state_code}">${state.name}</html:option>
                    </logic:iterate>
                </logic:present>
             </html:select> 
                </td>
                <td>LGA</td><td><html:select property="lga" styleId="lga" style="width: 200px;" onchange="setActionName('ward'); forms[0].submit()">
                    <logic:present name="lgaListInDirectory">
                        <logic:iterate name="lgaListInDirectory" id="lga">
                            <html:option value="${lga.lga_code}">${lga.lga_name}</html:option>
                        </logic:iterate>
                    </logic:present>
                </html:select>
                </td>
            </tr>
            
            <tr><td class="orglabel" align="left"><jsp:include page="../includes/WardName.jsp" /> </td>
                <td align="left">
                 <html:select property="community" styleId="community" style="width: 200px;" >
                    <logic:present name="wardListInDirectory">
                         <logic:iterate id="ward" name="wardListInDirectory">
                            <html:option value="${ward.ward_code}">${ward.ward_name}</html:option>
                         </logic:iterate>
                     </logic:present>
                   </html:select> 
                </td>
                <td> </td>
                <td><%--<html:select property="community" styleId="community" style="width: 200px;">
                    <html:option value=""> </html:option>
                </html:select>--%>
            </td>
            </tr>
        </table>
        <table>
            <tr>
                <td>Facility name</td><td><html:text property="facilityName" styleId="facilityName" styleClass="fieldcellinput" /></td>
                <td>Facility type</td><td>
                    <html:select property="typeOfOrganization" styleId="typeOfOrganization" styleClass="fieldcellinput" >
                        <html:option value=""> </html:option>
                        <html:option value="PHC">Primary Health Center </html:option>
                        <html:option value="GHP">General Hospital </html:option>
                        <html:option value="FMC">Federal Medical Center </html:option>
                        <html:option value="UTH">Teaching Hospital </html:option>
                        <html:option value="SPC">Specialist Hospital </html:option>
                        <html:option value="PSC">Psychiatric Hospital </html:option>
                        <html:option value="CSO">Civil Society Organization </html:option>
                        <html:option value="CBO">Community Based Organization </html:option>
                        <html:option value="FBO">Faith Based Organization </html:option>
                    </html:select></td>
            </tr>
            <tr>
                <td>Facility address</td><td colspan="3"><html:textarea property="address" styleId="address" styleClass="fieldcellinput" style="width:490px" /></td>
            </tr>
            <tr>
                <td>Latitude</td><td><html:text property="latitude" styleId="latitude" styleClass="fieldcellinput" /></td>
                <td>Longitude</td><td><html:text property="longitude" styleId="longitude" styleClass="fieldcellinput" /></td>
            </tr>
            <tr>
                <td>Name of contact person</td><td><html:text property="nameOfContact" styleId="nameOfContact" styleClass="fieldcellinput" /></td>
                <td>Phone number</td><td><html:text property="contactNumber" styleId="contactNumber" styleClass="fieldcellinput" /></td>
            </tr>
            <tr>
                <td>Email</td><td><html:text property="contactEmail" styleId="contactEmail" styleClass="fieldcellinput" /></td>
                <td> </td><td> </td>
            </tr>
            <tr>
                <td colspan="4"> </td>
            </tr>
            <logic:present name="enableEnvironmentSetup">
            <tr>
                <td colspan="4" align="center"> 
                    <html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${rdSaveBtnDisabled}" />
                   <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${rdModifyBtnDisabled}"/>
                   <html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${rdModifyBtnDisabled}"/>
                </td>
            </tr>
            </logic:present>
            <tr>
                <td colspan="4"> </td>
            </tr>
            <tr>
          <td align="center" colspan="4">
              <logic:present name="facilityList">
                  <div align="center" style="width:720px; max-height:250px; overflow:scroll">
              <table width="700" border="1" bordercolor="#D7E5F2" class="regsitertable">
                  <logic:iterate name="facilityList" id="facility">
                      <tr><td>${facility.facilityName} </td><td><a href="referralaction.do?id=${facility.facilityId}&&p=ed">edit</a> </td> </tr>
                  </logic:iterate>

              </table>
            </div>
            </logic:present>
          </td>
      </tr>
        </table>
    </html:form>
</div>

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
</body>
</html>
