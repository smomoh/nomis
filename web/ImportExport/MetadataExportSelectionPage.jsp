<%-- 
    Document   : MetadataExportSelectionPage
    Created on : Jun 1, 2015, 10:09:16 PM
    Author     : Siaka
--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Metadata export </title>
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
</head>

<body onload="MM_preloadImages('images/About_down.jpg','images/Admin_down.jpg','images/Rapid_down.jpg','images/care_down.jpg','images/OVC_down.jpg'); checkCurrentAge()">
    
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
        <td> </td>
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
                    <div><jsp:include page="../Navigation/ImportExportLinks.jsp"/></div>
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
          <tr>
            <td height="127" valign="top"><div style="float: left" id="my_menu2" class="sdmenu" >
              <div><a href="#"> &nbsp; </a><a href="#"> &nbsp; </a><a href="#"> &nbsp; </a></div>
            </div></td>
          </tr>


      </table></td>
    <td width="13">&nbsp;</td>
      <td width="660" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td height="19" class="homepagestyle" colspan="2" align="center">Meta data export</td>
	 
    </tr>
    <tr>
	 <td height="19" class="homepagestyle" colspan="2" align="center"><html:errors/> </td>
    </tr>

  </table>

         <html:form action="/metadataMgtAction">
        <html:hidden property="actionName" styleId="actionName" />
    
<table >
    
    <%--<tr><td class="orglabel" align="left">State</td> <td >
            <html:select property="state" styleId="state" style="width: 320px;" onchange="setActionName('lga'); forms[0].submit()">
                <html:option value="All">All</html:option>        
                <html:optionsCollection property="stateList" label="name" value="state_code" />
                        
                    </html:select> </td><td class="orglabel" align="left" >LGA </td><td>
                    <html:select property="lga" styleId="lga" style="width: 320px;" onchange="setActionName('cbo'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <html:optionsCollection property="lgaList" label="lga_name" value="lga_code" />
                        
                        </html:select> </td></tr>
            <tr><td class="orglabel" align="left" >CBO </td><td >
                    <html:select property="cbo" styleId="cbo" style="width: 320px;" onchange="setActionName('community'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <html:optionsCollection property="cboList" label="orgName" value="orgCode" />
                    
                    </html:select> </td>
            <td class="orglabel" align="left" ><jsp:include page="../includes/WardName.jsp" /> </td><td>
                    <html:select property="community" styleId="community" style="width: 320px;" >
                        <html:option value="All"> </html:option>
                        <html:optionsCollection property="communityList" label="ward_name" value="ward_code" />
                        
                    </html:select> </td>
            </tr>--%>
    <tr><td>States  </td><td colspan="3">
           <div style="width:685px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="102">
                          <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:present name="metadataStateList">
                                  <logic:iterate name="metadataStateList" id="state">
                                  <tr>
                                      <td> <html:checkbox property="selectedStates" style="${state.state_code}" value="${state.state_code}" />${state.name}</td></tr>                                        
                                  </logic:iterate>
                              </logic:present>
                                                           
                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
           
        </td>
</tr>
   <tr><td>LGA  </td><td colspan="3">
           <div style="width:685px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="102">
                          <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:present name="metadataLgaList">
                                  <logic:iterate name="metadataLgaList" id="lga">
                                  <tr>
                                      <td> <html:checkbox property="selectedLgas" style="${lga.lga_code}" value="${lga.lga_code}" />${lga.lga_name}</td></tr>                                        
                                  </logic:iterate>
                              </logic:present>
                                                           
                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
           
        </td>
</tr>
<tr><td>CBO  </td><td colspan="3">
           <div style="width:685px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="102">
                          <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:present name="metadataCBOList">
                                  <logic:iterate name="metadataCBOList" id="cbo">
                                  <tr>
                                      <td> <html:checkbox property="selectedCBOs" style="${cbo.orgCode}" value="${cbo.orgCode}" />${cbo.orgName}</td></tr>                                        
                                  </logic:iterate>
                              </logic:present>
                                                           
                        </table>

                      </td>
                      </tr>
                  </table>
           </div>
        </td>
</tr>
<tr><td>Ward  </td><td colspan="3">
           <div style="width:685px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="102">
                          <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:present name="metadataWardList">
                                  <logic:iterate name="metadataWardList" id="ward">
                                  <tr>
                                      <td> <html:checkbox property="selectedWards" style="${ward.ward_code}" value="${ward.ward_code}" />${ward.ward_name}</td></tr>                                        
                                  </logic:iterate>
                              </logic:present>
                                                           
                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
        </td>
</tr>
<tr><td>Partner  </td><td colspan="3">
           <div style="width:685px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="102">
                          <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:present name="metadataPartnerList">
                                  <logic:iterate name="metadataPartnerList" id="partner">
                                  <tr>
                                      <td> <html:checkbox property="selectedPartners" style="${partner.partnerCode}" value="${partner.partnerCode}" />${partner.partnerName}</td></tr>                                        
                                  </logic:iterate>
                              </logic:present>
                                                           
                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
        </td>
</tr>
<tr><td>Vulnerability status  </td><td colspan="3">
           <div style="width:685px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="102">
                          <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:present name="metadataVulnerabilityStatusList">
                                  <logic:iterate name="metadataVulnerabilityStatusList" id="status">
                                  <tr>
                                      <td> <html:checkbox property="selectedVulnerabilityStatus" style="${status.id}" value="${status.id}" />${status.eligibilityName}</td></tr>                                        
                                  </logic:iterate>
                              </logic:present>
                                                           
                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
        </td>
</tr>
<tr><td>Schools  </td><td colspan="3">
           <div style="width:685px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="102">
                          <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:present name="metadataSchoolList">
                                  <logic:iterate name="metadataSchoolList" id="school">
                                  <tr>
                                      <td> <html:checkbox property="selectedSchools" style="${school.id}" value="${school.id}" />${school.name}</td></tr>                                        
                                  </logic:iterate>
                              </logic:present>
                                                           
                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
        </td>
</tr>
<tr><td>Facilities  </td><td colspan="3">
           <div style="width:685px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="102">
                          <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:present name="metadataFacilityList">
                                  <logic:iterate name="metadataFacilityList" id="facility">
                                  <tr>
                                      <td> <html:checkbox property="selectedFacilities" style="${facility.facilityId}" value="${facility.facilityId}" />${facility.facilityName}</td></tr>                                        
                                  </logic:iterate>
                              </logic:present>
                                                           
                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
        </td>
</tr>
   <%--<tr><td>  </td><td colspan="3">
           <div style="width:685px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="102">
                          <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <tr><td> 
                              <html:checkbox property="metadata" style="cbo" value="cbo" />CBO
                              </td></tr>
                              <tr><td> 
                              <html:checkbox property="metadata" style="community" value="community" /><jsp:include page="../includes/WardName.jsp" />
                              </td></tr>
                              <tr><td> 
                              <html:checkbox property="metadata" style="referralDirectory" value="referralDirectory" />Referral Directory
                              </td></tr>
                              <%--<tr><td>
                              <html:checkbox property="metadata" style="training" value="training" />Training
                              </td></tr>
                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
           
        </td>
</tr>--%>
<tr><td colspan="4" align="center" height="40">
        <html:submit style="width:75px; height:20px; margin-left:40px;" value="Export" onclick="setActionName('export')"/>
     </td></tr>
<logic:present name="mtmsg">
        <tr><td colspan="4" align="center" style="color:#006600">${mtmsg}</td></tr>
    </logic:present>

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
