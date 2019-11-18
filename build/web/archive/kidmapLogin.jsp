
<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Welcome to KidMAP Orphan &amp; Vulnerable Children Database</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #FFFFFF;
}
-->
</style>
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
<link href="images/kidmap.css" rel="stylesheet" type="text/css" />
<link href="sdmenu/sdmenu.css" rel="stylesheet" type="text/css" />
<link href="kidmap-default.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.style1 {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 10px;
}
-->
</style>
</head>

<body>
<table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
  <!--DWLayoutTable-->
  <tr>
    <td width="800" height="80">&nbsp;</td>
  </tr>
  <tr>
    <td height="175" valign="top"><div align="center"><img src="images/kidmap_logo.jpg" width="156" height="73" />
    </div>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <!--DWLayoutTable-->
      <tr>
        <td height="175"><table width="367" height="175" align="center" bgcolor="#C1C9F9">
          <!--DWLayoutTable-->
          <tr>
            <td width="354"><html:form action="/Login" method="post">
              <table width="367" height="92">
                <tr>
                  <td width="156" height="28" class="userlogin">Username:</td>
                      <td><html:text property="username" styleClass="smallfieldcellinput" /></td>
                    </tr>
                <tr>
                  <td height="28" class="userlogin">Password:</td>
                      <td><html:password property="password" styleClass="smallfieldcellinput" /></td>
                    </tr>
                <tr>
                  <td height="32" colspan="2"><label>
                    <div align="center">
                     
					  <html:image src="images/ok_button.jpg" value="Submit" alt="Click to submit form" styleClass="btnsubmit" />
                       
		              <html:image src="images/cancel.jpg" value="" alt="Click to submit form" styleClass="btnsubmit" onclick="window.close()"/> 			   
                        </div>
                      </label></td>
                      </tr>
                </table>
                            </html:form>              </td>
              </tr>
        </table>
          <p align="center"><span class="style1">KidMAP Orphans and Vulnerable Children Information System</span></p>
          <%--<p>&nbsp;</p>--%>
          <p align="center"><table cellpadding="0" cellspacing="0" border="0"><tr><td width="30%"></td><td width="40%"><html:errors /></td><td width="30%"></td></tr></table></p>
          <p>&nbsp;</p>          <p>&nbsp;</p></td>
        </tr>
    </table></td></tr>
  <tr>
    <td height="113">&nbsp;</td>
  </tr>
</table>
</body>
</html>
