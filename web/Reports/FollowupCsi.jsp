<%-- 
    Document   : FollowupCsi
    Created on : Aug 18, 2011, 10:52:02 AM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Follow up report</title>

        <style>
.verticaltext
{
	writing-mode: tb-rl;
	filter: flipV flipH;
	border-right: solid black 1px;
}
.tdLine
{
	border-right: solid black 2px;
}
tr
{
	height:20px;
}
td {
	padding-left: 11px;
	padding-right: 11px;
	border-left: 1px solid black;
	border-bottom: 1px solid black;
	font-size: 11px;
	color: black;
}
th {
	padding-left: 11px;
	padding-right: 11px;
	border-left: 1px solid black;
	border-bottom: 1px solid black;
        border-top: 1px solid black;
	font-size: 11px;
	color: black;
}
table {
	border-collapse: collapse;
	margin: 10px;
}
.lab
{
    font-family:  arial, sans-serif;
    font-size: 14px;
    /*font-weight: bold;*/
    letter-spacing: 3px;
}
.orgNames
{
    font-family:  arial, sans-serif;
    font-size: 12px;
    border: none; text-align: left; width: 40px;
}
.orgNamestd
{
    border: none; text-align: left; font-size:16px;
}
</style>
    </head>
    <body>
        
        <br/><br/><br/>
        <center>
        <div align="center" style=" width: 900px; background-color: white;"><br/>

            <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:none">
                <tr style="border:none"><td class="orgNamestd"><strong>State:</strong>&nbsp; ${ovc.state}</td><td width="40" class="orgNamestd"> &nbsp;</td><td class="orgNamestd"><strong>Lga:</strong> &nbsp; ${ovc.lga} </td></tr>
                    <tr style="border:none"><td class="orgNamestd">CBO:&nbsp; ${ovc.completedbyCbo}</td><td class="orgNamestd">&nbsp; </td><td class="orgNamestd"><strong><jsp:include page="../includes/WardName.jsp" /></strong>&nbsp; ${ovc.ward}</td></tr>
                    <tr style="border:none"><td class="orgNamestd">&nbsp;</td><td class="orgNamestd">&nbsp;</td><td class="orgNamestd"></td></tr>
                    <tr style="border:none"><td class="orgNamestd"><strong>Name: </strong>&nbsp; ${ovc.firstName}&nbsp;${ovc.lastName}</td><td class="orgNamestd">&nbsp;</td><td class="orgNamestd"><strong>Ovc Id:</strong>&nbsp;${ovc.ovcId}</td></tr>
                </table>

                            <logic:iterate name="csihistory" id="csi">
                                                                   
                <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                    <tr style="background-color: #DDDDDD">
                        <td colspan="10" style="text-align: center;"> Date:&nbsp;${csi.csiDate}</td>
                              </tr>
                              
                            <tr style="border:none; background-color: #FFFFFF">
                         
                              <td width="27%" ><strong>Domain</strong></td>
                              <td colspan="4"><strong>Score</strong></td>
                              <td width="27%"><strong>Domain</strong></td>
                              <td colspan="4"><strong>Score<br /></strong></td>
                              </tr>
                             <tr>
                              <td colspan="5" bgcolor="#DDDDDD"><strong>Psychosocial</strong></td>
                              <td colspan="5" bgcolor="#DDDDDD"><strong>Education &amp; Work </strong></td>
                              </tr>
                              <tr >
                                  <td style="text-align:left">Emotional Health </td>
                              <td colspan="4">&nbsp; ${csi.csiFactor1}</td>
                              <td>Performance</td>
                              <td colspan="4">&nbsp;${csi.csiFactor7} </td>

                            </tr>
                            <tr >
                              <td style="text-align:left">Social Behaviour </td>
                              <td colspan="4">&nbsp;${csi.csiFactor2} </td>
                              <td style="text-align:left">School work </td>
                              <td colspan="4">&nbsp; ${csi.csiFactor8}</td>
                            </tr>
                            <tr>
                              <td colspan="5" bgcolor="#DDDDDD"><strong>Food &amp; Nutrition </strong></td>
                              <td colspan="5" bgcolor="#DDDDDD"><strong>Protection</strong></td>
                              </tr>
                            <tr >
                              <td style="text-align:left">Food Security </td>
                              <td colspan="4">&nbsp;${csi.csiFactor3} </td>
                              <td style="text-align:left">Abuse and Exploitation </td>
                              <td colspan="4">&nbsp;${csi.csiFactor9} </td>
                            </tr>
                            <tr >
                              <td style="text-align:left">Nutrition and Growth </td>
                              <td colspan="4">&nbsp;${csi.csiFactor4} </td>
                              <td style="text-align:left">Legal Protection </td>
                              <td colspan="4">&nbsp;${csi.csiFactor10}</td>
                            </tr>
                            <tr>
                              <td colspan="5" bgcolor="#DDDDDD"><strong>Health</strong></td>
                              <td colspan="5" bgcolor="#DDDDDD"><strong>Shelter &amp; Care </strong></td>
                              </tr>
                            <tr >
                                <td style="text-align:left">Wellness</td>
                              <td colspan="4">&nbsp; ${csi.csiFactor5}</td>
                              <td style="text-align:left">Shelter</td>
                              <td colspan="4">&nbsp;${csi.csiFactor11} </td>
                            </tr>
                            <tr >
                              <td style="text-align:left">Health care services </td>
                              <td colspan="4">&nbsp;${csi.csiFactor6} </td>
                              <td style="text-align:left">Care</td>
                              <td colspan="4">&nbsp;${csi.csiFactor12} </td>
                            </tr>
                          </table>
                     </logic:iterate>
            </div>
            </center>
        
    </body>
</html>
