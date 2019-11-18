<%-- 
    Document   : UserAccountSetup
    Created on : Aug 8, 2015, 11:15:12 PM
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
<title>User account setup</title>
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
                        <jsp:include page="../Navigation/UserAccountMgtLink.jsp"/>
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
          <br/>
            <html:form action="/userSetup">
    <html:errors />
    <html:hidden property="actionName" styleId="actionName" />
    <html:hidden property="userId" styleId="userId" />
    <fieldset style="margin: 10px;">
    <table style="margin-left:20px;">
        
        <tr><td colspan="4" align="center"><label class="labels" style=" margin-left: 30px; font-size: 14px; color: white">User setup  </label></td></tr>
        <tr><td colspan="4" >&nbsp; </td></tr>

        
        <tr><td style="color:#FFFFFF">Surname</td><td> <html:text property="surname" styleId="surname" styleClass="textField" style="margin-left: 10px; width:130px;" /> </td>
            <td style="color:#FFFFFF">Other names</td><td> <html:text property="other_names" styleId="other_names" styleClass="textField" style="margin-left: 10px; width:155px;"/> </td></tr>
        <tr><td style="color:#FFFFFF">User name</td><td> <html:text property="user_name" styleId="user_name" styleClass="textField" style="margin-left: 10px; width:130px;"/> </td>
            <td style="color:#FFFFFF">User role    </td>
            <td> <html:select property="user_role" styleId="user_role" styleClass="textField" style="margin-left: 10px; width:157px;">
                    <html:option value=" "> </html:option>
                    <logic:iterate id="ur" name="urList">
                        <html:option value="${ur.roleId}">${ur.roleName}</html:option>
                    </logic:iterate>
                    
                </html:select>  </td></tr>
    <tr><td style="color:#FFFFFF">Password  </td><td><html:password property="pwd" styleId="pwd" styleClass="textField" style="margin-left: 10px; width:130px;"/>  </td>
        <td style="color:#FFFFFF">Confirm password</td><td> <html:password property="confirm_pwd" styleId="confirm_pwd" styleClass="textField" style="margin-left: 10px; width:155px;"/> </td>
    </tr>
    <tr><td style="color:#FFFFFF">Phone  </td><td><html:text property="phone" styleId="phone" styleClass="textField" style="margin-left:10px; width:130px;"/>  </td>
        <td style="color:#FFFFFF">Email</td><td> <html:text property="email" styleId="email" styleClass="textField" style="margin-left: 10px; width:155px;"/> </td>
    </tr>
    <tr>
               <td ><label class="labels" align="left" style="color:white">Address</label></td>
              <td colspan="3">
                  <html:textarea property="address" styleId="address" style="width:440px; margin-left: 10px" rows="2"></html:textarea>
              </td>
        </tr>
        <tr><td>  </td><td style="color:#FFFFFF" colspan="3">&nbsp;&nbsp;User can change password<html:checkbox property="changePwd" styleId="changePwd" value="yes" style="vertical-align:bottom"/> 
                   
                User can view client details<html:checkbox property="viewClientDetails" styleId="viewClientDetails" value="yes" style="vertical-align:bottom" />
                &nbsp;Status<html:select property="accountStatus" styleId="accountStatus" styleClass="textField" style="margin-left: 5px;">
                <html:option value="disabled">Disable</html:option>
                <html:option value="enabled">Enable</html:option>
            </html:select></td>
                
        
        </tr>
        
    <%--<tr><td style="color:#FFFFFF">Status  </td><td><html:select property="accountStatus" styleId="accountStatus" styleClass="textField" style="margin-left: 10px; width:130px;">
                <html:option value="disabled">Disable</html:option>
                <html:option value="enabled">Enable</html:option>
            </html:select></td>
        <td style="color:#FFFFFF"> </td><td>  </td>
    </tr>--%>
    <tr><td style="color:#FFFFFF">User group</td>
        <td colspan="3">
            <html:select property="userGroupId" style="width:440px; margin-left: 10px">
                <html:option value="defaultgpId">Default user group</html:option> 
                <logic:present name="userGroupList">
                  <logic:iterate id="userGroup" name="userGroupList">
                      <html:option value="${userGroup.groupId}">${userGroup.groupName} </html:option> 
                  </logic:iterate>
                </logic:present>
            </html:select>
        </td>
    </tr>
    <tr> <td> </td><td style="color:#FFFFFF" colspan="3" ><label style="color:#FFFFFF; margin-left: 10px;">Organization unit groups</label></td>
    
    <tr><td style="color:#FFFFFF"></td><td colspan="3">
            <div style="width:450px; min-height: 100px; max-height: 160px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF; margin-left: 10px">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable  bordercolor="#D7E5F2"-->
                    <tr>
                      <td >
                          <table width="700" border="1" style="border-collapse:collapse" bordercolor="green" cellpadding="0" cellspacing="0">
                              <logic:present name="orgUnitGroupList" >
                                  <logic:iterate id="oug" name="orgUnitGroupList">
                                      <tr><td><html:multibox property='orgUnitGroupId' styleId="${oug.orgUnitGroupId}" value="${oug.orgUnitGroupId}" styleClass='smallfieldcellselect'/> </td><td>${oug.orgUnitGroupName} </td> </tr>
                                  </logic:iterate>
                              </logic:present>
                        </table>

                      </td>
                      </tr>
                      
                  </table>
                </div>
        </td>
    </tr>
    <tr><td style="color:#FFFFFF">Partners</td><td colspan="3">
            <div style="width:450px; min-height: 100px; max-height: 160px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF; margin-left: 10px">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable  bordercolor="#D7E5F2"-->
                    <tr>
                      <td >
                          <table width="700" border="1" style="border-collapse:collapse" bordercolor="green" cellpadding="0" cellspacing="0">
                              <logic:present name="allPartneLlist" >
                                  <logic:iterate name="allPartneLlist" id="partner">
                                      <tr><td><html:multibox property="selectedPartners" styleId="${partner.partnerCode}" value="${partner.partnerCode}" styleClass='smallfieldcellselect'/> </td><td>${partner.partnerName} </td> </tr>
                                  </logic:iterate>
                              </logic:present>
                        </table>

                      </td>
                      </tr>
                      
                  </table>
                </div>
        </td>
    </tr>
    <tr><td> </td><td colspan="3">
            <input type="button" value="Select all" onclick="selectChkBoxes('orgUnitGroupId')" style="margin-left: 10px; width: 100px; height: 25px;" />
                    <input type="button" value="Unselect all" onclick="unselectChkBoxes('orgUnitGroupId')" style="margin-left: 10px; width: 100px; height: 25px;"/>
                    </td></tr>
    <tr><td>  </td><td colspan="3">
            <html:select property="userList" styleId="userList" style=" min-height: 70px; max-height: 100px; width: 450px; margin-left: 10px; font-size:9pt;" multiple="true" ondblclick="setActionName('userdetails');forms[0].submit()">
                <logic:iterate id="user" name="userLists">
                <html:option value="${user.username}">${user.firstName} ${user.lastName}</html:option>
                </logic:iterate></html:select></td>
    </tr>
    
    <tr><td colspan="4" align="center" style="margin-left: 350px;">
            <center>
<fieldset style="width:250px;">
    <html:submit style="width:75px; height:30px;" value="Save" styleId="save" disabled="${userAccountSaveDisabled}" onclick="return setBtnName('save')"/>
    <html:submit style="width:75px; height:30px;" value="Modify..." styleId="modify" disabled="${userAccountModifyDisabled}" onclick="return setBtnName('modify')" />
    <html:submit style="width:75px; height:30px;" value="Delete..." styleId="delete" disabled="${userAccountModifyDisabled}" onclick="return setBtnName('delete')" />

</fieldset>
            </center>
 </td></tr>
</table>
    </fieldset>
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