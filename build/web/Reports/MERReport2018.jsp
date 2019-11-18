<%-- 
    Document   : MERReport2018
    Created on : Feb 22, 2018, 11:23:37 PM
    Author     : smomoh
--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>New Indicator report</title>
    </head>
    <body>
        <table align="center" style="border-collapse: collapse;width: 800px;">
            <tr><td colspan="4" align="center">New Indicator report form </td></tr>
            <tr><td width="20">State: </td><td style="margin-right: 10px;">${mer2018rht.state} </td><td width="20" >Lga: </td><td >${mer2018rht.lga} </td></tr>
            <tr><td>Organization: </td><td>${mer2018rht.cbo} </td><td >Partner: </td><td >${mer2018rht.partnerName} </td></tr>
            <tr><td colspan="3">Reporting period:  </td><td>${mer2018rht.period} </td></tr>
            
        </table>
            
        <table border="1" style="border-collapse: collapse; width: 800px;" align="center">
            <tr style="background-color: lightgray">
                <td class="tdLine" style="font-size:16px;">Indicator </td>
                <td class="tdLine" style="font-size:16px;">Sex </td>
                <td class="tdLine" style="font-size:16px;">&lt;1 </td>
                <%--<td class="tdLine" style="font-size:16px;">1-4 </td>
                <td class="tdLine" style="font-size:16px;">5-9 </td>--%>
                <td class="tdLine" style="font-size:16px;">1-9 </td>
                <td class="tdLine" style="font-size:16px;">10-14 </td>
                <td class="tdLine" style="font-size:16px;">15-17 </td>
                <td class="tdLine" style="font-size:16px;">18-24 </td>
                <td class="tdLine" style="font-size:16px;">25+ </td>
                <td class="tdLine" style="font-size:16px;">Total </td>
                <td class="tdLine" style="font-size:16px;">Denominator </td>
                <td class="tdLine" style="font-size:16px;">Proportion </td>
             </tr>
             <logic:iterate name="mer2018ReportList" id="reportTemplate">
            <tr >
                <td class="tdLine" style="font-size:16px;" rowspan="2">${reportTemplate.indicatorName}</td>
                <td class="tdLine" style="font-size:16px;">Male</td>
                <td class="tdLine" style="font-size:16px;">${reportTemplate.maleLessThan1} </td>
                <%--<td class="tdLine" style="font-size:16px;">${reportTemplate.male1to4}</td>
                <td class="tdLine" style="font-size:16px;">${reportTemplate.male5to9} </td>--%>
                <td class="tdLine" style="font-size:16px;">${reportTemplate.male1to4+reportTemplate.male5to9} </td>
                <td class="tdLine" style="font-size:16px;">${reportTemplate.male10to14} </td>
                <td class="tdLine" style="font-size:16px;">${reportTemplate.male15to17} </td>
                <td class="tdLine" style="font-size:16px;">${reportTemplate.male18to24} </td>
                <td class="tdLine" style="font-size:16px;">${reportTemplate.male25AndAbove} </td>
                <td class="tdLine" style="font-size:16px;">${reportTemplate.maleTotal} </td>
                <td class="tdLine" style="font-size:16px;" rowspan="2">${reportTemplate.ovc_serv} (${reportTemplate.ovc_servAgeCategory})</td>
                <td class="tdLine" style="font-size:16px;" rowspan="2">${reportTemplate.percentage}</td>
             </tr>
             <tr >
                <td class="tdLine" style="font-size:16px;">Female</td>
                <td class="tdLine" style="font-size:16px;">${reportTemplate.femaleLessThan1} </td>
                <%--<td class="tdLine" style="font-size:16px;">${reportTemplate.female1to4}</td>
                <td class="tdLine" style="font-size:16px;">${reportTemplate.female5to9} </td>--%>
                <td class="tdLine" style="font-size:16px;">${reportTemplate.female1to4+reportTemplate.female5to9} </td>
                <td class="tdLine" style="font-size:16px;">${reportTemplate.female10to14} </td>
                <td class="tdLine" style="font-size:16px;">${reportTemplate.female15to17} </td>
                <td class="tdLine" style="font-size:16px;">${reportTemplate.female18to24} </td>
                <td class="tdLine" style="font-size:16px;">${reportTemplate.female25AndAbove} </td>
                <td class="tdLine" style="font-size:16px;">${reportTemplate.femaleTotal} </td>
             </tr>
             </logic:iterate>
        </table>
        
        
    </body>
</html>
