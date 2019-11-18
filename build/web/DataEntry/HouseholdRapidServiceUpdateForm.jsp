<%-- 
    Document   : HouseholdRapidServiceUpdateForm
    Created on : Oct 26, 2017, 12:36:04 PM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%--<%@page import="com.fhi.kidmap.business.*" %>--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>VC monthly service form </title>
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
var msg=" "
			$(function() {
                $("#serviceDate").datepicker();
            });


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

function submitForm(val,callerValue,maxDigit)
{
    
    document.getElementById("actionName").value=val
    document.getElementById("dataForm").submit()
}
function validateRecords()
{
   var dateValue=document.getElementById("serviceDate").value
   var providerName=document.getElementById("providerName").value
   if(dateValue==null || dateValue.indexOf("/")==-1)
   {
        alert("Please, enter date service provided")
        return false
   }
   else if(providerName==null)
   {
        alert("Please, enter name of service provider")
        return false
   }
    if(validateServices())
    {
        setBtnName('save')
        return true
    }
    else
    return false
}
function validateServices()
{
    var count=0;
    count+=validateSelectedRecords("healthServices")
    count+=validateSelectedRecords("nutritionalServices")
    count+=validateSelectedRecords("shelterServices")
    count+=validateSelectedRecords("educationServices")
    count+=validateSelectedRecords("psychosocialServices")
    count+=validateSelectedRecords("protectionServices")
    count+=validateSelectedRecords("economicServices")
    if(count >0)
    return true
    else
    {
        alert("You must select a service")    
        return false
    }
}
function validateSelectedRecords(chkname)
{
   var elements=document.getElementsByName(chkname)
   var count=0
    for(var i=0; i<elements.length; i++)
    {
        if(elements[i].checked==true)
        count++
    }
    return count
}
function childWithdrawnAction(value) {
    if(value == "Yes") 
    {
        document.getElementById("reasonWithdrawal").disabled = false;
    }
    else 
    {
        document.getElementById("reasonWithdrawal").value=""
        document.getElementById("reasonWithdrawal").disabled = true;
    }
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
                    <div><jsp:include page="../DataEntryLinkPage.jsp"/>
                        <%--<a href="householdVulnerabilityIndexAction.do">Household Vulnerability Assessment Form</a><a href="Add.do">Enrolment card </a> <a href="householdServiceAction.do">Household service form</a><a href="OvcService.do">Ovc Service form</a><a href="ovcreferral.do">Referral form</a><a href="followupsurvey.do">Follow up CSI form</a>--%></div>
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
                    <div><jsp:include page="../ServiceReportLinkPage.jsp"/> <a href="#"> &nbsp; </a><a href="#"> &nbsp; </a><a href="#"> &nbsp; </a></div>
            </div></td>
          </tr>


      </table></td>
    <td width="13">&nbsp;</td>
      <td width="639" >

     <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
	 <td width="30%" height="39" class="homepagestyle">Household/Caregiver Rapid Service form </td>
	 <td width="70%"><html:errors/></td>
    </tr>

  </table>

         <html:form styleId="dataForm" action="/householdmultiplequeryreport" method="post">
<html:hidden property="actionName" styleId="actionName"/>
      

  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!--DWLayoutTable-->
                <tr>
                  <td width="762" height="28" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    
                  </table></td>
                </tr>
                

                <tr>
                  <td height="123" valign="top"><fieldset>
                        <legend class="fieldset">Services </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="17">
                          <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">

			<tr>
                          <td width="20%">Date services provided: </td>
                          <td width="80%" colspan="2">
                              <html:text property="serviceDate" styleId="serviceDate" styleClass="smallfieldcellinput" disabled="${servicedatedisabled}" readonly="true"/> &nbsp;(mm/dd/yyyy)
                          </td>

                        </tr>
                        </table>
                          <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                              <td width="21%" bgcolor="#CCCCCC" class="right">Health: </td>
                              <td bgcolor="#CCCCCC" colspan="2"></td>
                          </tr>
                          <tr>
                              <td>
                                <table border="0" cellspacing="0" cellpadding="0" style="border:1px black solid;">
                                    <td>
                                       <div style="width:720px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                                        <!--DWLayoutTable-->
                                        <tr>
                                          <td width="752" height="102">
                                              <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                                                  <logic:iterate name="healthServices" id="service">
                                                      <tr>
                                                          <td>${service.serviceName} </td> <td><html:multibox property='healthServices' styleId="" value="${service.serviceValue}" styleClass='smallfieldcellselect'/> </td> 
                                                      </tr>
                                                  </logic:iterate>  
                                            </table>

                                          </td>
                                          </tr>
                                      </table>
                                    </div> 
                                    </td>
                                </table>
                            </td></tr>
                          
                        </table>

                        <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                            <td width="21%" bgcolor="#CCCCCC" class="right">Nutrition: </td>
                            <td bgcolor="#CCCCCC" colspan="2"> </td>
                            
                          </tr>
                          <tr>
                              <td>
                                <table border="0" cellspacing="0" cellpadding="0" style="border:1px black solid;">
                                    <td>
                                       <div style="width:720px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                                        <!--DWLayoutTable-->
                                        <tr>
                                          <td width="752" height="102">
                                              <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                                                  <logic:iterate name="nutritionalServices" id="service">
                                                      <tr>
                                                          <td>${service.serviceName} </td> <td><html:multibox property='nutritionalServices' styleId="" value="${service.serviceValue}" styleClass='smallfieldcellselect'/> </td> 
                                                      </tr>
                                                  </logic:iterate>  
                                            </table>

                                          </td>
                                          </tr>
                                      </table>
                                    </div> 
                                    </td>
                                </table>
                            </td></tr>
                          
                        </table>

                         <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                            <td width="21%" bgcolor="#CCCCCC" class="right">Shelter and care: </td>
                            <td bgcolor="#CCCCCC" colspan="2"></td>
                          </tr>
                          <tr>
                              <td>
                                <table border="0" cellspacing="0" cellpadding="0" style="border:1px black solid;">
                                    <td>
                                       <div style="width:720px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                                        <!--DWLayoutTable-->
                                        <tr>
                                          <td width="752" height="102">
                                              <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                                                  <logic:iterate name="shelterServices" id="service">
                                                      <tr>
                                                          <td>${service.serviceName} </td> <td><html:multibox property='shelterServices' styleId="" value="${service.serviceValue}" styleClass='smallfieldcellselect'/> </td> 
                                                      </tr>
                                                  </logic:iterate>  
                                            </table>

                                          </td>
                                          </tr>
                                      </table>
                                    </div> 
                                    </td>
                                </table>
                            </td></tr>
                        </table>

                        <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                            <td width="21%" bgcolor="#CCCCCC" class="right">Education: </td>
                            <td bgcolor="#CCCCCC" colspan="2"></td>
                          </tr>
                          <tr>
                              <td>
                                <table border="0" cellspacing="0" cellpadding="0" style="border:1px black solid;">
                                    <td>
                                       <div style="width:720px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                                        <!--DWLayoutTable-->
                                        <tr>
                                          <td width="752" height="102">
                                              <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                                                  <logic:iterate name="educationServices" id="service">
                                                      <tr>
                                                          <td>${service.serviceName} </td> <td><html:multibox property='educationServices' styleId="" value="${service.serviceValue}" styleClass='smallfieldcellselect'/> </td> 
                                                      </tr>
                                                  </logic:iterate>  
                                            </table>

                                          </td>
                                          </tr>
                                      </table>
                                    </div> 
                                    </td>
                                </table>
                            </td></tr>
                        </table>

                        <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                        <tr>
                          <td width="21%" bgcolor="#CCCCCC" class="right">Psychosocial Support: </td>
                          <td bgcolor="#CCCCCC" colspan="2"></td>
                        </tr>
                        <tr>
                              <td>
                                <table border="0" cellspacing="0" cellpadding="0" style="border:1px black solid;">
                                    <td>
                                       <div style="width:720px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                                        <!--DWLayoutTable-->
                                        <tr>
                                          <td width="752" height="102">
                                              <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                                                  <logic:iterate name="psychosocialServices" id="service">
                                                      <tr>
                                                          <td>${service.serviceName} </td> <td><html:multibox property='psychosocialServices' styleId="" value="${service.serviceValue}" styleClass='smallfieldcellselect'/> </td> 
                                                      </tr>
                                                  </logic:iterate>  
                                            </table>

                                          </td>
                                          </tr>
                                      </table>
                                    </div> 
                                    </td>
                                </table>
                            </td></tr>
                      </table>

                        <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                            <td width="21%" bgcolor="#CCCCCC" class="right">Child protection: </td>
                            <td bgcolor="#CCCCCC" colspan="2"></td>
                          </tr>
                          <tr><td>
            <table border="0" cellspacing="0" cellpadding="0" style="border:1px black solid;">
                <td>
                   <div style="width:720px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="102">
                          <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:iterate name="protectionServices" id="service">
                                  <tr>
                                      <td>${service.serviceName} </td> <td><html:multibox property='protectionServices' styleId="" value="${service.serviceValue}" styleClass='smallfieldcellselect'/> </td> 
                                  </tr>
                              </logic:iterate>  
                        </table>

                      </td>
                      </tr>
                  </table>
                </div> 
                </td>
            </table>
        </td></tr>
                        </table>

                        <table width="686" border="1" bordercolor="#D7E5F2" class="regsitertable">
                          <tr>
                            <td width="21%" bgcolor="#CCCCCC" class="right">Economic Strengthening: </td>
                            <td bgcolor="#CCCCCC" colspan="2"></td>
                          </tr>
                          
                          <tr>
                              <td>
            <table border="0" cellspacing="0" cellpadding="0" style="border:1px black solid;">
                <td>
                   <div style="width:720px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="102">
                          <table width="670" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:iterate name="householdEconomicServices" id="service">
                                  <tr>
                                      <td>${service.serviceName} </td> <td><html:multibox property='economicServices' styleId="" value="${service.serviceValue}" styleClass='smallfieldcellselect'/> </td> 
                                      
                                  </tr>
                              </logic:iterate>  
                        </table>

                      </td>
                      </tr>
                  </table>
                </div> 
                </td>
            </table>
        </td></tr>
                          <tr><td>
                   <table width="100%" class="regsitertable">
                                         <tr>
                                            <td width="727" height="30" valign="top">
					  <table width="100%" class="regsitertable">
                                              <tr><td width="30%">Caregiver withdrawn from program</td><td ><select class="shortfieldcellinput" id="childWithdrawn" onChange="childWithdrawnAction(this.value);">
					    <option value="No">No</option><option value="Yes">Yes</option></select></td>
                                            <td width="28%">&nbsp;&nbsp;Reason caregiver is withdrawn</td><td>
                                                <html:select property="reasonWithdrawal" styleId="reasonWithdrawal" disabled="true" style="width:200px">
                                                    <html:option value=""> </html:option>
                                                    <html:option value="Known death">Known death</html:option>
                                                    <html:option value="Migrated">Migrated</html:option>
                                                    <html:option value="Loss to follow-up">Loss to follow-up (after 3 months)</html:option>
                                                    <html:option value="Graduated">Graduated from program</html:option>
                                                    <html:option value="transfer">Transfer to other program</html:option>
                                                    <html:option value="voluntaryWithdrawal">Voluntary withdrawal</html:option>
                                                </html:select></td>
                                              </tr>
					  </table>
					 </td>
                    </tr>
                   </table></td>
                          </tr>

                        </table>                        </td>
                      </tr>
                  </table>
                  </fieldset>

                  </td>
                </tr>
                <tr>
                  
                </tr>
                <tr>
                  
                </tr>
                
                
                <tr>
                    <td>
                        <table><tr>
                                <td >Name of Volunteer/Service provider</td><td ><html:text property="providerName" styleClass="fieldcellinput" styleId="providerName"/></td>
                                <td ><html:text property="volunteerDesignation" styleClass="fieldcellinput" styleId="volunteerDesignation"/></td>
                            </tr></table></td>

                </tr>
                <!--<tr>
                    <td width="745" height="27">&nbsp;</td>
                </tr>-->
                <tr>
				<td height="40" align="center">
                            <%--<html:reset value="New" styleId="newBtn" style="width:70px; height:25px;  margin-right:5px;" disabled="true"/>--%>
                            <html:submit value="Save selected services " styleId="saveBtn" style="height:25px; margin-right:5px;" onclick="return validateRecords()"/>
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


