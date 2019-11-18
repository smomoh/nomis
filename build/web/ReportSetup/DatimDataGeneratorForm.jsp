<%-- 
    Document   : DatimReportGeneratorForm
    Created on : Dec 19, 2018, 9:25:06 PM
    Author     : smomoh
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
<title>DATIM data generator </title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
        font-size: 10px;
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
<link type="text/css" href="css/ui-darkness/jquery-ui-1.8.custom.css" rel="Stylesheet" />
<link type="text/css" href="css/ui-darkness/jquery-ui-1.8.custom.css" rel="Stylesheet" />
<link href="kidmap-default.css" rel="stylesheet" type="text/css" />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
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
.right
{
    font-size: 14px;
}
-->
</style>
<!--<link href="sdmenu/sdmenu.css" rel="stylesheet" type="text/css" />-->


<script language="javascript">

function submitForm(requiredAction,formId)
{
       setActionName(requiredAction)
       document.getElementById(formId).submit()
       return true
}
function setBtnName(name)
{
     if(confirm("Are you sure you want to "+name+"?"))
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




      <tr>
        <td height="3"></td>
        <td></td>
        <td></td>
        <td rowspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="95" height="65"><a href="Nomis.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('OVC','','images/OVC_down.jpg',1)"><img src="images/OVC_up.jpg" alt="OVC" name="OVC" width="95" height="65" border="0" id="OVC" /></a></td>
              </tr>
          </table></td>
          <td></td>
        <td rowspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="95" height="65"><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Care and Support','','images/care_down.jpg',1)"><img src="images/care_up.jpg" alt="Care and Support" name="Care and Support" width="95" height="65" border="0" id="Care and Support" /></a></td>
              </tr>
          </table></td>
          <td></td>
        <td rowspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="95" height="65"><a href="RapidAssessment.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Rapid Assesment','','images/Rapid_down.jpg',1)"><img src="images/rapid_up.jpg" alt="Rapid Assesment" name="Rapid Assesment" width="95" height="65" border="0" id="Rapid Assesment" /></a></td>
              </tr>
          </table></td>
          <td></td>
        <td rowspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="95" height="65"><a href="Administration.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('NOMIS Admin','','images/Admin_down.jpg',1)"><img src="images/admin_up.jpg" alt="NOMIS Admin" name="NOMIS Admin" width="95" height="65" border="0" id="NOMIS Admin" /></a></td>
              </tr>
          </table></td>
          <td></td>
          <td rowspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="95" height="65"><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('About NOMIS','','images/About_down.jpg',1)"><img src="images/about_up.jpg" alt="About NOMIS" name="About NOMIS" width="95" height="65" border="0" id="About NOMIS" /></a></td>
              </tr>
          </table></td>
          <td></td>
        </tr>

      <tr>
        <td height="62"></td>
          <td rowspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="268" height="92"><img src="images/logo.jpg" width="268" height="92" /></td>
              </tr>
          </table></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
        </tr>





      <tr>
        <td height="30"></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td width="20"> </td>
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
                    <div><jsp:include page="../Navigation/AnalyticsLink.jsp"/></div>
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
      <td width="639" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td height="19" class="homepagestyle" colspan="2" align="center"> </td>
    </tr>
    <tr><td colspan="2" align="center"><jsp:include page="../PartnerView.jsp" /></td></tr>
    </table>

         
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!--DWLayoutTable-->
                <tr>
                  <td width="762" height="28" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="27">
                          <table width="100%">
                              <tr><td class="homepagestyle" colspan="2" align="center">DATIM report data generation</td></tr>

                         </table></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" valign="top">
			  
                        <table width="100%" >

                        <%--class="regsitertable"--%>
                            <tr>
                                <td colspan="6">
                                    <table>
                                        <tr>
                      <td width="752" height="92">

                      
                        <table width="100%" class="regsitertable" bgcolor="#F0F0F0">
                            
                            <tr><td colspan="2" align="center">${msg}</td></tr>
                            <tr>
                                <td colspan="2" align="center">
                                    <html:form action="/datimdatagenerator" method="post">
                <html:hidden property="actionName" styleId="actionName"/>
               
        <span>
        <center>
            <html:errors/>
        <table class="paramPage">
                  
            <tr><td class="orglabel"> </td><td colspan="3" class="title" align="center">
                    <fieldset>
                        <legend class="fieldset"><label style="color:white">Select States</label></legend>
                        <div style="width:500px; min-height: 50px; max-height: 150px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td>
                          <table width="480" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:iterate name="datimReportStateList" id="state">
                                  <tr> <td style="width:30px"><html:multibox property='states' styleId="${state.state_code}" value="${state.state_code}" styleClass='smallfieldcellselect'/> </td>
                                  <td>${state.name} </td></tr>
                              </logic:iterate>
                              
                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
                  </fieldset>
                </td>
            </tr>
            <tr><td class="orglabel" align="right"><label style="color:white">Period</label></td><td align="left" colspan="3">
                    <html:select property="startMth" styleId="startMth" style="width: 70px;">
                        <logic:present name="generatedMonths">
                            <logic:iterate id="months" name="generatedMonths">
                                <html:option value="${months}">${months}</html:option>
                            </logic:iterate>
                    </logic:present>
                    </html:select> <html:select property="startYear" styleId="startYear" style="width: 60px;">
                        <logic:present name="generatedYears">
                            <logic:iterate id="year" name="generatedYears" >
                                <html:option value="${year}">${year}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select> <label class="orglabel" style=" margin-left: 2px; margin-right: 2px"> to</label>
                    <html:select property="endMth" styleId="endMth" style="width: 70px;" >
                        <logic:present name="generatedMonths">
                            <logic:iterate id="month" name="generatedMonths">
                                <html:option value="${month}">${month}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select> <html:select property="endYear" style="width: 60px;" styleId="endYear" >
                        <logic:present name="generatedYears">
                            <logic:iterate id="year" name="generatedYears">
                                <html:option value="${year}">${year}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select> </td>
            </tr>
            
            <tr><td colspan="3" align="center"><html:submit value="Generate report" onclick="return setBtnName('generateData')" style="width: 100px; margin-left: 50px;" disabled="${cirbGenButtonDisabled}" />
                <html:submit value="Delete data" onclick="return setBtnName('deleteData'); " style="width: 100px; margin-left: 10px" disabled="${cirbGenButtonDisabled}" /> </td></tr>
        </table>
        </center>
        </span>
   <!-- </div>-->

            </html:form>
                                </td>
                            </tr>
    
                        </table>

                      </td>
                      </tr>
                                    </table>

                                </td>
                            </tr>
			</table>
                     


					  </td>
                      </tr>

                  </table></td>
                </tr>
                
            </table>
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
