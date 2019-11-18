<%-- 
    Document   : UserSetup2
    Created on : Apr 29, 2011, 11:21:18 AM
    Author     : smomoh
--%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>NOMIS - National OVC Management Information System </title>
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
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
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
<script language="javascript">

</script>

</head>

<body onload="MM_preloadImages('images/About_down.jpg','images/Admin_down.jpg','images/Rapid_down.jpg','images/care_down.jpg','images/OVC_down.jpg')">
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




      <tr>
        <td height="3"></td>
        <td></td>
        <td></td>
        <td rowspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="95" height="65"><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('OVC','','images/OVC_down.jpg',1)"><img src="images/OVC_up.jpg" alt="OVC" name="OVC" width="95" height="65" border="0" id="OVC" /></a></td>
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
                <td width="95" height="65"><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Rapid Assesment','','images/Rapid_down.jpg',1)"><img src="images/rapid_up.jpg" alt="Rapid Assesment" name="Rapid Assesment" width="95" height="65" border="0" id="Rapid Assesment" /></a></td>
              </tr>
          </table></td>
          <td></td>
        <td rowspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="95" height="65"><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('NOMIS Admin','','images/Admin_down.jpg',1)"><img src="images/admin_up.jpg" alt="NOMIS Admin" name="NOMIS Admin" width="95" height="65" border="0" id="NOMIS Admin" /></a></td>
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
          <td rowspan="2" valign="top">
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
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
          <td></td>
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
    <td width="931" height="351" valign="top">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <!--DWLayoutTable-->
      <tr><td colspan="3">&nbsp; </td></tr>
     
      <tr>
          <td height="276" width="150">&nbsp;</td>
        <td>&nbsp;</td>
        <td rowspan="2" valign="top" >
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <!--DWLayoutTable-->
          <tr>
              <td width="639" height="301">
              <html:form action="/adminusersetup">
    <html:errors />
    <!--class="setuppagebg"-->
    <html:hidden property="actionName" styleId="buttonId" value="save" />
    <%--<html:hidden property="changePwd" styleId="buttonId" value="yes" />
    <html:hidden property="viewClientDetails" styleId="buttonId" value="yes" />--%>
    <html:hidden property="user_role" styleId="buttonId" value="superuser" />
    
    <div id="userSetupDiv" style="height: 300px; width: 640px; border: 1px blue solid; background-color: #4E9258">
        
        <table style="margin-left: 10px">
        <tr><td colspan="4" align="center"><label class="labels" style=" margin-left: 30px; font-size: 16pt; color: white">Create user account </label></td></tr>
        <tr><td colspan="4" > </td></tr>

    <tr><td><label class="labels" style="color:#FFFFFF">Surname</label></td><td> <html:text property="surname" styleId="surname" styleClass="textField" style="margin-left: 10px; width:130px;"/> </td><td><label class="labels" style="color:#FFFFFF">Other names</label></td><td> <html:text property="other_names" styleId="other_names" styleClass="textField" style="margin-left: 10px; width:155px;"/> </td></tr>
    <tr><td><label class="labels" style="color:#FFFFFF">User name</label></td><td> <html:text property="user_name" styleId="user_name" styleClass="textField" style="margin-left: 10px; width:130px;"/> </td><td><label style="color:#FFFFFF">User role</label>    </td><td> <html:text property="userRoleName" styleId="userRoleName" styleClass="textField" style="margin-left: 10px; width:154px;" value="Super user" readonly="true"/>   </td></tr>
    <tr><td><label class="labels" style="color:#FFFFFF">Password </label>  </td><td><html:password property="pwd" styleId="pwd" styleClass="textField" style="margin-left: 10px; width:130px;"/>  </td><td><label class="labels" style="color:#FFFFFF">Confirm password </label></td><td> <html:password property="confirm_pwd" styleId="confirm_pwd" styleClass="textField" style="margin-left: 10px; width:155px;"/> </td></tr>
    <tr><td style="color:#FFFFFF">Phone  </td><td><html:text property="phone" styleId="phone" styleClass="textField" style="margin-left: 10px; width:130px;"/>  </td>
        <td style="color:#FFFFFF">Email</td><td> <html:text property="email" styleId="email" styleClass="textField" style="margin-left: 10px; width:155px;"/> </td>
    </tr>
    <tr>
               <td ><label class="labels" align="left" style="color:white">Address</label></td>
              <td colspan="3">
                  <html:textarea property="address" styleId="address" style="width:435px; margin-left: 10px" rows="2"></html:textarea>
              </td>
        </tr>
                  <tr><td style="color:white">Resource location</td><td><html:text property="resourceLocation" style="margin-left: 10px" value="${dbroot}" /></td></tr>
    <%--<tr><td><label class="labels">States </label></td><td colspan="3">
            <div style="width:454px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF; margin-left: 10px">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td >
                          <table width="430" border="1" bordercolor="green" cellpadding="0" cellspacing="0">
                              <logic:iterate id="stat" name="states">
                                  <tr><td><html:multibox property='state_code' styleId="${stat.state_code}" value="${stat.state_code}" styleClass='smallfieldcellselect'/> </td><td>${stat.name} </td> </tr>
                              </logic:iterate>

                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
        </td>
    </tr>
    
    <tr><td>  </td><td colspan="3">
            <html:select property="userList" styleId="userList" style="height: 100px; width: 454px; margin-left: 10px; font-size:9pt;" multiple="true">
                <logic:iterate id="user" name="userLists">
                <option value="${user.username}">${user.firstName} ${user.lastName}</option>
                </logic:iterate>
            </html:select></td>
    </tr>--%>



    <tr><td colspan="4" align="center" >
            <center>
<fieldset style="width:85px; margin-left: 30px;">
    <html:submit style="width:75px" value="Save" styleId="save"/>
 </fieldset>
            </center>
 </td></tr>
</table>
        
    </div>
</html:form>
                  
    </td>
        </tr>
            </table></td>
    </tr>
      <tr>
        <td height="25">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="26">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>    </td>
    <td width="18">&nbsp;</td>
  </tr>
  <tr>
    <td height="25" colspan="2" valign="top">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#038233">
      <!--DWLayoutTable-->
      <tr>
        <td width="949" height="25" class="copyright"><jsp:include page="includes/Version.jsp"/></td>
        </tr>
    </table></td>
  </tr>
</table>
    
</body>
</html>

