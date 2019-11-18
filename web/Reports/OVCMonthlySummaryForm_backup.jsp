<%-- 
    Document   : OVCMonthlySummaryForm_backup
    Created on : Jan 24, 2015, 3:07:45 PM
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
	padding-left: 11px;
	padding-right: 11px;
	border-left: 2px solid black;
	border-right: dashed black 2px;
	border-bottom: 1px solid black;
	font-size: 14px;
	color: black;
        /*width: 30px;*/
}
th {
	padding-left: 11px;
	padding-right: 11px;
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
<!--<div style="margin-top:20px; margin-bottom:30px; width:1000px; height:1050px; background-color:#FFFFFF">
<span style="margin-top:20px; margin-bottom:100px">-->
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
  <tr>
      <td style="border:none;">
          <a href="monthlySummaryPdfAction.do" style="margin-left: 50px;" target="_blank">Export to pdf</a>
      </td>
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
    <tr><td class="tdLine" colspan="9" >${countOfStates[0]} </td><td>${countOfStates[1]} </td></tr>
    <tr><td class="tdLine" colspan="9" >${countOfStates[2]} </td><td>${countOfStates[3]} </td></tr>
    <tr><td class="tdLine" colspan="9" >${countOfStates[4]} </td><td>${countOfStates[5]} </td></tr>
    </logic:iterate>
    <logic:iterate id="countOfLGA" name="stateMthlySummaryList">
        <tr><td class="tdLine" colspan="9" align="center" ><b>State level Data element</b> </td><td><b>Total</b> </td></tr>
    <tr><td class="tdLine" colspan="9" >${countOfLGA[0]} </td><td>${countOfLGA[1]} </td></tr>
    <tr><td class="tdLine" colspan="9" >${countOfLGA[2]} </td><td>${countOfLGA[3]} </td></tr>
    </logic:iterate>

    <logic:iterate id="countOfCSO" name="lgaMthlySummaryList">
        <tr><td class="tdLine" colspan="9" align="center" ><b>LGA level Data element</b> </td><td><b>Total</b> </td></tr>
    <tr><td class="tdLine" colspan="9" >${countOfCSO[0]} </td><td>${countOfCSO[1]} </td></tr>
    </logic:iterate>
  <tr >
    <th width="10%" rowspan="3" class="tdLine" style="font-size:16px; width: 170px;"><div align="center">Community VC Services Data Elements</div></th>
    <th width="4%" colspan="4" class="tdLine" style="font-size:16px"><div align="center">Male</div></th>
    <th width="2%" colspan="4" class="tdLine" style="font-size:16px"><div align="center">Female</div></th>
    <th width="2%" rowspan="3" class="tdLine" style="font-size:16px"><div align="center">Total</div></th>
  </tr>
  <tr >
    <th width="2%" rowspan="2"><div align="center">0-5 years</div></th>
    <th width="2%" rowspan="2"><div align="center">6-12 years</div></th>
    <th width="3%" rowspan="2" class="tdLine"><div align="center">13-17 years</div></th>
    <th width="2%" rowspan="2" class="tdLine"><div align="center">Male total</div></th>
    <!--<th width="3%" rowspan="2"><div align="center">Pregnant</div></th>-->
  </tr>
  <tr >
  	<th width="2%"><div align="center">0-5 years</div></th>
    <th width="2%"><div align="center">6-12 years</div></th>
    <th width="3%" class="tdLine"><div align="center">13-17 years</div></th>
    <th width="2%" class="tdLine"><div align="center">Female Total</div></th>
  </tr>


       <logic:iterate id="monthlySummCount" collection="${mthSummarySessionObj[0]}">
            <tr><td class="tdLine">${monthlySummCount[0]} </td><td>${monthlySummCount[1]}</td><td>${monthlySummCount[2]} </td><td class="tdLine">${monthlySummCount[3]}</td><td class="tdLine">${monthlySummCount[1]+monthlySummCount[2]+monthlySummCount[3]} </td><td>${monthlySummCount[4]} </td><td>${monthlySummCount[5]} </td><td class="tdLine">${monthlySummCount[6]} </td><td class="tdLine">${monthlySummCount[4]+monthlySummCount[5]+monthlySummCount[6]}</td><td>${monthlySummCount[1]+monthlySummCount[2]+monthlySummCount[3]+monthlySummCount[4]+monthlySummCount[5]+monthlySummCount[6]}</td> </tr>
       </logic:iterate>
   <logic:iterate id="monthlySummCount" collection="${mthSummarySessionObj[1]}">
            <tr><td class="tdLine">${monthlySummCount[0]} </td><td style="background-color:#808080"> </td><td style="background-color:#808080"> </td><td class="tdLine" style="background-color:#808080"> </td><td class="tdLine">${monthlySummCount[1]} </td><td style="background-color:#808080"> </td><td style="background-color:#808080"> </td><td class="tdLine" style="background-color:#808080"> </td><td class="tdLine">${monthlySummCount[2]}</td><td>${monthlySummCount[1]+monthlySummCount[2]}</td> </tr>
   </logic:iterate>
   <logic:iterate id="monthlySummCount" collection="${mthSummarySessionObj[2]}">
            <tr><td class="tdLine">${monthlySummCount[0]} </td><td>${monthlySummCount[1]}</td><td>${monthlySummCount[2]} </td><td class="tdLine">${monthlySummCount[3]}</td><td class="tdLine">${monthlySummCount[1]+monthlySummCount[2]+monthlySummCount[3]} </td><td>${monthlySummCount[4]} </td><td>${monthlySummCount[5]} </td><td class="tdLine">${monthlySummCount[6]} </td><td class="tdLine">${monthlySummCount[4]+monthlySummCount[5]+monthlySummCount[6]}</td><td>${monthlySummCount[1]+monthlySummCount[2]+monthlySummCount[3]+monthlySummCount[4]+monthlySummCount[5]+monthlySummCount[6]}</td> </tr>
       </logic:iterate>
   
  <logic:iterate id="econonomicStrenghtList" collection="${econStrengthList}">
      <tr><td class="tdLine">${econonomicStrenghtList[0]} </td><td>${econonomicStrenghtList[1]}</td><td>${econonomicStrenghtList[2]} </td><td class="tdLine">${econonomicStrenghtList[3]}</td><td class="tdLine">${econonomicStrenghtList[4]}</td><td>${econonomicStrenghtList[5]} </td><td>${econonomicStrenghtList[6]} </td><td class="tdLine">${econonomicStrenghtList[7]} </td><td class="tdLine">${econonomicStrenghtList[8]}</td><td>${econonomicStrenghtList[9]}</td> </tr>  
  </logic:iterate>
        <%--
        <tr><td> </td> <td > </td> <td class="tdLine"> </td> <td > </td><td > </td><td class="tdLine"> </td> </tr>
        <tr><td class="tdLine"rowspan="2">No. of <b>household heads</b> receiving <b>economic strengthening</b></td><td > &lt;18yr</td><td >18-59yr </td><td class="tdLine">&gt;60yr </td><td class="tdLine" rowspan="2"> </td><td >&lt;18 </td><td >18-59yr </td><td class="tdLine"> &gt;60</td><td class="tdLine" rowspan="2"> </td><td rowspan="2"> </td> </tr>
        <tr><td class="tdLine">${econStrengthList[0]}</td><td style="background-color:#808080"> </td><td style="background-color:#808080"> </td><td class="tdLine">${econStrengthList[1]} </td><td class="tdLine">${econStrengthList[1]} </td><td style="background-color:#808080"> </td><td style="background-color:#808080"> </td><td class="tdLine"> ${econStrengthList[2]}</td><td class="tdLine"> ${econStrengthList[2]}</td><td>${econStrengthList[1]+econStrengthList[2]} </td> </tr>
        <tr><td class="tdLine">No. of households with VC receiving support on <b>economic strengthening</b></td><td colspan="4" class="tdLine"><img src="images/Ximage.JPG"  /> </td><td colspan="4" class="tdLine"> <img src="images/Ximage.JPG"  /> </td><td> </td> </tr>
        <tr><td class="tdLine">${monthlySummCount[0]} </td><td>${monthlySummCount[1]}</td><td>${monthlySummCount[2]} </td><td class="tdLine">${monthlySummCount[3]}</td><td class="tdLine">${monthlySummCount[1]+monthlySummCount[2]+monthlySummCount[3]} </td><td>${monthlySummCount[4]} </td><td>${monthlySummCount[5]} </td><td class="tdLine">${monthlySummCount[6]} </td><td class="tdLine">${monthlySummCount[4]+monthlySummCount[5]+monthlySummCount[6]}</td><td>${monthlySummCount[1]+monthlySummCount[2]+monthlySummCount[3]+monthlySummCount[4]+monthlySummCount[5]+monthlySummCount[6]}</td> </tr>
        --%>
  
  
  
  
</table>
            </td></tr>
<tr><td style="border:none;"> </td></tr>
  <tr><td style="border:none;" align="center"> Completed by: Name_______________________ Designation:________________Sign/Date:________________</td></tr>
        <tr><td style="border:none;"> </td></tr>
    </table>
<!--</span>
</div>-->
</center>
</body>
</html>

