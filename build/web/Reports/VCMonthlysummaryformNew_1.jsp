<%-- 
    Document   : VCMonthlysummaryformNew
    Created on : Jan 24, 2015, 11:07:52 AM
    Author     : Siaka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">-->
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>
<logic:notPresent name="mthSummarySessionObj">
    <jsp:forward page="OvcMthlySummaryFormParamPage.jsp" />
</logic:notPresent>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${reportParam} VC Monthly summary form </title>
<style>
.verticaltext
{
writing-mode: tb-rl;
filter: flipV flipH;
}
.fcell
{
	border-right: solid black 2px;
        width: 50px;
}
.tdLine
{
	border-right: solid black 3px;
}
tr
{
	height:30px;
}
td{
	padding-left: 5px;
	padding-right: 5px;
	border-left: 2px solid black;
	border-right: dashed black 2px;
	border-bottom: 1px solid black;
	font-size: 14px;
	color: black;
        /*width: 30px;*/
}
th {
	padding-left: 5px;
	padding-right: 5px;
	border-left: 1px solid black;
	border-right: dashed black 2px;
	border-bottom: 1px solid black;
	font-size: 11px;
	color: black;
        width: 30px;
}
table {
	border-collapse: collapse;
	margin: 10px;
}
.borderdraw
{
    border-style:solid;
    height:0;
    line-height:0;
    width:0;
}
.orglabel
{
    border:none;
    font-family: 'Courier New', Courier, monospace;
    font-size:12pt;
}
</style>

</head>
<body style="background-color:#2A3F55" >
<center>
    <table border="0" width="90%" style="border:none; background-color:#FFFFFF">
        <tr><td style="border:none;" >
<table  border="0" style="border:none;">
  <tr>
      <td style="border:none;"><img src="images/CoatOfArm.jpg"  /></td><td style="border:none; font-family:'Courier New', Courier, monospace; font-size:14pt" align="center" colspan="2"><label style="margin-left:100px"><b>${reportParam} VC MONTHLY SUMMARY FORM </b></label></td>
  </tr>
  <tr>
      <td style="border:none;">
          <label style="margin-left:100px; font-size:12pt;"><b>Reporting Period:</b> &nbsp;</label><label id="mth" style="font-size:12pt;"> 01 ${mthlysummparams[3]} ${mthlysummparams[4]} to   end of ${mthlysummparams[5]}  ${mthlysummparams[6]}</label>
      ${reportLevel[3]}</td>
  </tr>
 
  </table>
            </td></tr>
        <tr><td style="border:none;" align="center">
  <logic:iterate id="orglist" name="orgReportList">
      <table width="90%"  border="0" cellpadding="0" cellspacing="0" style="border:none;">
  <tr style="border:none">
      ${reportLevel[0]}  ${reportLevel[1]}  ${reportLevel[2]} 
  </tr>
</table>
</logic:iterate>
            </td></tr>
        <tr><td style="border:none;" align="center">
<table border="0" cellpadding="0" cellspacing="0" style="border:1px black solid; margin-bottom:70px">
    <logic:iterate id="countOfStates" name="nationalMthlySummaryList">
    <tr><td class="tdLine" colspan="15" >${countOfStates[0]} </td><td>${countOfStates[1]} </td></tr>
    <tr><td class="tdLine" colspan="15" >${countOfStates[2]} </td><td>${countOfStates[3]} </td></tr>
    <tr><td class="tdLine" colspan="15" >${countOfStates[4]} </td><td>${countOfStates[5]} </td></tr>
    </logic:iterate>
    <logic:iterate id="countOfLGA" name="stateMthlySummaryList">
        <tr><td class="tdLine" colspan="15" align="center" ><b>State level Data element</b> </td><td><b>Total</b> </td></tr>
    <tr><td class="tdLine" colspan="15" >${countOfLGA[0]} </td><td>${countOfLGA[1]} </td></tr>
    <tr><td class="tdLine" colspan="15" >${countOfLGA[2]} </td><td>${countOfLGA[3]} </td></tr>
    </logic:iterate>

    <logic:iterate id="countOfCSO" name="lgaMthlySummaryList">
        <tr><td class="tdLine" colspan="15" align="center" ><b>LGA level Data element</b> </td><td><b>Total</b> </td></tr>
    <tr><td class="tdLine" colspan="15" >${countOfCSO[0]} </td><td>${countOfCSO[1]} </td></tr>
    </logic:iterate>
  <tr >
    <th width="10%" rowspan="3" class="tdLine" style="font-size:16px; width: 170px;"><div align="center">Community VC Services Data Elements</div></th>
    <th width="4%" colspan="7" class="tdLine" style="font-size:16px"><div align="center">Male</div></th>
    <th width="2%" colspan="7" class="tdLine" style="font-size:16px"><div align="center">Female</div></th>
    <th width="2%" rowspan="3" class="tdLine" style="font-size:16px"><div align="center">Total</div></th>
  </tr>
  <tr >
    <th width="1%" rowspan="2"><div align="center"> &lt;1</div></th>
    <th width="1%" rowspan="2"><div align="center">1-4</div></th>
    <th width="2%" rowspan="2" class="tdLine"><div align="center">5-9</div></th>
    <th width="2%" rowspan="2" class="tdLine"><div align="center">10-14</div></th>
    <th width="2%" rowspan="2" class="tdLine"><div align="center">15-17</div></th>
    <th width="2%" rowspan="2" class="tdLine"><div align="center">18-24</div></th>
    <th width="2%" rowspan="2" class="tdLine"><div align="center">25+</div></th>
    <th width="2%" rowspan="2" class="tdLine"><div align="center">Male total</div></th>
    
  </tr>
  <tr >
    <th width="1%" ><div align="center"> &lt;1</div></th>
    <th width="1%" ><div align="center">1-4</div></th>
    <th width="2%" class="tdLine"><div align="center">5-9</div></th>
    <th width="2%" class="tdLine"><div align="center">10-14</div></th>
    <th width="2%" class="tdLine"><div align="center">15-17</div></th>
    <th width="2%" rowspan="2" class="tdLine"><div align="center">18-24</div></th>
    <th width="2%" rowspan="2" class="tdLine"><div align="center">25+</div></th>
    <th width="2%" class="tdLine"><div align="center">Female Total</div></th>
  </tr>


       <logic:iterate id="monthlySummCount" collection="${mthSummarySessionObj[0]}">
            <tr><td class="tdLine">${monthlySummCount[0]} </td>
                <td>${monthlySummCount[1]}</td><td>${monthlySummCount[2]} </td><td class="tdLine">${monthlySummCount[3]}</td><td>${monthlySummCount[4]}</td><td>${monthlySummCount[5]} </td><td class="tdLine">${monthlySummCount[6]}</td><td class="tdLine">${monthlySummCount[7]} </td>
                <td>${monthlySummCount[8]} </td><td>${monthlySummCount[9]} </td><td class="tdLine">${monthlySummCount[10]} </td><td>${monthlySummCount[11]} </td><td>${monthlySummCount[12]} </td><td class="tdLine">${monthlySummCount[13]} </td><td class="tdLine">${monthlySummCount[14]}</td>
                <td>${monthlySummCount[15]}</td> </tr>
       </logic:iterate>
   <logic:iterate id="monthlySummCount" collection="${mthSummarySessionObj[1]}">
            <tr><td class="tdLine">${monthlySummCount[0]} </td><td style="background-color:#808080"> </td><td style="background-color:#808080"> </td><td class="tdLine" style="background-color:#808080"> </td>
                <td style="background-color:#808080"> </td><td style="background-color:#808080"> </td><td class="tdLine">${monthlySummCount[2]} </td>
                <td class="tdLine">${monthlySummCount[2]} </td>
                <td style="background-color:#808080"> </td><td style="background-color:#808080"> </td><td class="tdLine" style="background-color:#808080"> </td>
                <td style="background-color:#808080"> </td><td style="background-color:#808080"> </td><td class="tdLine">${monthlySummCount[4]} </td>
                <td class="tdLine">${monthlySummCount[4]}</td><td>${monthlySummCount[2]+monthlySummCount[4]}</td> </tr>
   </logic:iterate>
   <logic:iterate id="monthlySummCount" collection="${mthSummarySessionObj[2]}">
            <tr><td class="tdLine">${monthlySummCount[0]} </td>
                <td>${monthlySummCount[1]}</td><td>${monthlySummCount[2]} </td><td class="tdLine">${monthlySummCount[3]}</td><td>${monthlySummCount[4]}</td><td>${monthlySummCount[5]} </td><td class="tdLine">${monthlySummCount[6]}</td><td class="tdLine">${monthlySummCount[7]} </td>
                <td>${monthlySummCount[8]} </td><td>${monthlySummCount[9]} </td><td class="tdLine">${monthlySummCount[10]} </td><td>${monthlySummCount[11]} </td><td>${monthlySummCount[12]} </td><td class="tdLine">${monthlySummCount[13]} </td><td class="tdLine">${monthlySummCount[14]}</td>
                <td>${monthlySummCount[15]}</td> 
            </tr>
       </logic:iterate>
   <logic:iterate id="econonomicStrenghtList" collection="${econStrengthList}">
            <tr><td class="tdLine">${econonomicStrenghtList[0]}</td>
                <td style="background-color:#808080"> </td><td style="background-color:#808080"> </td><td class="tdLine" style="background-color:#808080"> </td><td style="background-color:#808080"> </td><td style="background-color:#808080"> </td><td class="tdLine" style="background-color:#808080"> </td><td class="tdLine">${econonomicStrenghtList[7]} </td>
                <td style="background-color:#808080"> </td><td style="background-color:#808080"> </td><td class="tdLine" style="background-color:#808080"> </td><td style="background-color:#808080"> </td><td style="background-color:#808080"> </td><td class="tdLine" style="background-color:#808080"> </td><td class="tdLine">${econonomicStrenghtList[14]}</td>
                <td>${econonomicStrenghtList[15]}</td> 
            </tr>
       </logic:iterate>
  <%--<logic:iterate id="econonomicStrenghtList" collection="${econStrengthList}">
      <tr><td class="tdLine">${econonomicStrenghtList[0]} </td><td>${econonomicStrenghtList[1]}</td><td>${econonomicStrenghtList[2]} </td><td class="tdLine">${econonomicStrenghtList[3]}</td><td class="tdLine">${econonomicStrenghtList[4]}</td><td>${econonomicStrenghtList[5]} </td><td>${econonomicStrenghtList[6]} </td><td class="tdLine">${econonomicStrenghtList[7]} </td><td class="tdLine">${econonomicStrenghtList[8]}</td><td>${econonomicStrenghtList[9]}</td> </tr>  
  </logic:iterate>--%>
        
</table>
            </td></tr>
<tr><td style="border:none;"> </td></tr>
  <tr><td style="border:none;" align="center"> Completed by: Name_______________________ Designation:________________Sign/Date:________________</td></tr>
        <tr><td style="border:none;"> </td></tr>
    </table>
</center>
</body>
</html>

