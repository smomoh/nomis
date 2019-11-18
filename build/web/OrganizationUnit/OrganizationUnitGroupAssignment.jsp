<%-- 
    Document   : OrganizationUnitGroupAssignment
    Created on : Aug 2, 2015, 9:28:11 PM
    Author     : Siaka
--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Organization group assignment </title>
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
function selectChkBoxes(chkname)
{
   var elements=document.getElementsByName(chkname)
    for(var i=0; i<elements.length; i++)
    {
        elements[i].checked=true
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
                    <div><jsp:include page="../Navigation/OrganizationUnitLinks.jsp"/></div>
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
        
    <tr>
	 <td class="homepagestyle" colspan="2" align="center"><html:errors/> </td>
    </tr>

  </table>

         <html:form action="/organizationUnitGroupAssignmentAction" method="post" styleId="formId">
<html:hidden property="actionName" styleId="actionName"/>
<html:hidden property="hiddenId" styleId="hiddenId"/>

  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!--DWLayoutTable-->
                <tr>
                  <td width="762" height="28" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="762" height="27">
                          <table width="100%">
                              <tr><td class="homepagestyle" colspan="2" align="center">Organization unit group assignment</td></tr>
                              
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
                        <legend class="fieldset">  </legend>
                        <table width="100%" class="regsitertable">


                            <tr>
                                <td colspan="6">
                                    <table>
                                        <tr>
                      <td width="752" height="92">


                        <table width="100%" class="regsitertable" bgcolor="#F0F0F0">
                            <tr>
                          <td width="25%" bgcolor="#F0F0F0" class="right"><div align="left">State </div></td>
                          <td bgcolor="#F0F0F0">
                              <html:select property="stateCode" styleId="stateCode" style="width: 470px; font-size:9pt;" onchange="setActionName('orgList');forms[0].submit()">
                                    <html:optionsCollection property="stateList" name="OrganizationUnitGroupAssignmentForm" value="state_code" label="name" />
                                </html:select>
                              </td>
                            </tr>
                            
                            <tr bgcolor="#F0F0F0">
                            <td class="right">Group </td>
                            <td >
                                <html:select property="orgUnitGroupId" styleId="orgUnitGroupId" style="width: 470px; font-size:9pt;" onchange="setActionName('details');forms[0].submit()">
                                    <html:option value=""> </html:option>
                                    <html:optionsCollection property="orgUnitGroupList" name="OrganizationUnitGroupAssignmentForm" value="orgUnitGroupId" label="orgUnitGroupName" />
                                </html:select>
                                </td>
                            </tr>

                          <tr bgcolor="#F0F0F0">
                            <td class="orglabel" align="left">Ward/Community </td>
                <td >
                    <div style="width: 470px; height: 150px;overflow: scroll; background-color: white;">
                        <table width="470" border="1" bordercolor="#D7E5F2" class="regsitertable" >
                              <logic:iterate  name="orgListInOugaAction" id="organization">
                                  <tr>
                                      <td width="20"><html:multibox property="orgUnitIds" styleId="${organization.ward_code}" value="${organization.ward_code}" styleClass='smallfieldcellselect'/> </td>
                                      <td align="left" style=" font-size: 13px;">${organization.ward_name} </td>
                                      </tr>
                              </logic:iterate>
                              
                        </table>
                     </div>
                </td>
            </tr>                           
                            
            <tr bgcolor="#F0F0F0"><td> </td><td>
            <input type="button" value="Select all " onclick="selectChkBoxes('orgUnitIds')" />
                    <input type="button" value="Unselect all " onclick="unselectChkBoxes('orgUnitIds')" />
                    </td></tr>
                        </table></td>
                      </tr>
                                    </table>

                                </td>
                            </tr>
			</table>
                      </fieldset>


					  </td>
                      </tr>

                  </table></td>
                </tr>
                <tr>
				<td height="40" align="center">

                        </td>
				</tr>
               <tr>
				<td height="40" align="center">

                            <html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${ougaSavedisabled}"/>
                            <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${ougaModifyDisabled}"/>
                            <html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${ougaModifyDisabled}"/>


                        </td>
				</tr>

                <tr>
				<td height="60">&nbsp;</td>
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


