<%-- 
    Document   : Dashboard
    Created on : Aug 15, 2017, 5:47:28 PM
    Author     : smomoh
--%>
<%-- 
    Document   : ChartView
    Created on : Feb 15, 2016, 12:19:22 AM
    Author     : smomoh
--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
        <link href="css/sdmenu/sdmenu.css" rel="stylesheet" type="text/css" />
        <link type="text/css" href="css/kidmap2.css" rel="Stylesheet" />
        <link type="text/css" href="css/ui-darkness/jquery-ui-1.8.custom.css" rel="Stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script language="javascript">
$(function() {
                $("#startDate").datepicker();
            });
$(function() {
                $("#endDate").datepicker();
            });

function setActionName(val)
{
    document.getElementById("actionName").value=val
}
</script>
    </head>
    <body>
        <center>
            <table border="1" style="vertical-align: top; width: 950px; height: 650px; border: 1px green solid; border-collapse: collapse;">
            <tr>
                <td style="width: 200px; "><img src="images/logo.jpg" width="200" height="92" /> </td>

                <td style="background-image: url(images/topbar.jpg); width: 750px; height: 92px; text-align: center;"><label style="color:white; font-size: 30px; font-weight: bolder;">Sample Tracker</label> </td>
            </tr>
            <tr>
<!--#FFEBCD-->

                <td style="background-color: #028232" colspan="2">
                    <table align="center">
                        <tr><td height="500">
                              <table>
                                  <tr><td colspan="2">&nbsp;&nbsp;&nbsp;<a href="homeaction.do?p=hid" style="color: white">Home</a> </td> </tr>
                                  <tr><td colspan="2">
                                  <html:form action="/dashboard" method="post">
                <html:hidden property="actionName" styleId="actionName" value="dashboard"/>
                
                                      </td>
                    </html:form>
                </tr>
                <logic:present name="mainObjectIdList">
                    <logic:iterate name="mainObjectIdList" id="objectIdList">
                        <tr style="background-color:white">
                            <td><IMG SRC="dashboard.do?q=dbchart&&id=${objectIdList[0]}" BORDER=0 WIDTH=450 HEIGHT=400/></td>
                            <td><IMG SRC="dashboard.do?q=dbchart&&id=${objectIdList[1]}" BORDER=0 WIDTH=450 HEIGHT=400/> </td>
                        </tr>
                    </logic:iterate>
                </logic:present>
            </table>
                              </td></tr>

                        <tr><td style="width: 600px; font-weight: 800; color: green; text-align: right; vertical-align: bottom;">&nbsp;</td></tr>
                    </table>

                </td>
            </tr>

        </table>
        </center>
    </body>
</html>