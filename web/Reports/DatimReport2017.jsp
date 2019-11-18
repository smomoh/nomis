<%-- 
    Document   : DatimReport2017
    Created on : Apr 13, 2017, 12:16:58 PM
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
        <title>DATIM report</title>
    </head>
    <body>
        <table align="center" style="border-collapse: collapse;width: 800px;">
            <tr><td colspan="4" align="center">DATIM report form </td></tr>
            <tr><td width="20">State: </td><td style="margin-right: 10px;">${datim2017Form.state} </td><td width="20" >Lga: </td><td >${datim2017Form.lga} </td></tr>
            <tr><td>Organization: </td><td>${datim2017Form.cbo} </td><td >Partner: </td><td >${datim2017Form.partnerName} </td></tr>
            <tr><td colspan="3">Reporting period: ${datimReportPeriod} </td><td> </td></tr>
            <%--<tr><td colspan="3">Reporting period: ${datim2017Form.startMth} ${datim2017Form.startYr} to ${datim2017Form.endMth} ${datim2017Form.endYr} </td><td> </td></tr>
            <tr><td >State: </td><td style="margin-right: 10px;">${merReportParam[7]} </td><td >Lga: </td><td >${merReportParam[8]} </td></tr>
            <tr><td>Organization: </td><td>${merReportParam[9]} </td><td >Partner: </td><td >${merReportParam[13]} </td></tr>
            <tr><td >Reporting period: </td><td >${merReportParam[3]} ${merReportParam[4]} to ${merReportParam[5]} ${merReportParam[6]} </td><td> </td><td> </td></tr>
            <tr><td colspan="4"><a href="usgDataEntryFormInPdf.do" target="_blank" >Download as PDF</a> </td></tr>--%>
        </table>
            
        <table border="1" style="border-collapse: collapse; width: 800px;" align="center">
            <tr >
                <td class="tdLine" style="font-size:16px; background-color: #F39814" colspan="2"><div >OVC_HIVSTAT</div></td>
                
             </tr>
             <tr >
                <td class="tdLine" style="font-size:16px; background-color: aquamarine;" colspan="2"><div >Number of OVC with HIV status reported to implementing partner (including status not reported)</div></td>
             </tr>
             <tr >
                <td class="tdLine" style="font-size:16px;"><div >Numerator</div></td>
                <td class="tdLine" style="font-size:16px;" ><div >${datim2017Form.hiv_statNumerator} </div></td>
             </tr>
            <tr >
                <td class="tdLine" style="font-size:16px; background-color: lightgrey" colspan="2" ><div >Disaggregated by status type</div></td>
             </tr>
            <tr >
                <td class="tdLine" style="font-size:16px; "><div >Reported HIV positive to IP (includes tested in the reporting period and known positive) </div></td>
                <td class="tdLine" style="font-size:16px;"><div > ${datim2017Form.totalPositive}</div></td>
            </tr>
             <tr >
                <td class="tdLine" style="font-size:16px; "><div >Of those positive: Currently receiving ART </div></td>
                <td class="tdLine" style="font-size:16px;"><div > ${datim2017Form.enrolledOnART}</div></td>
            </tr>
            <tr >
                <td class="tdLine" style="font-size:16px; "><div >Of those positive: Not currently receiving ART </div></td>
                <td class="tdLine" style="font-size:16px;"><div > ${datim2017Form.notEnrolledOnART}</div></td>
            </tr>
            <tr>
                <td class="tdLine" style="font-size:16px; "><div >Reported HIV negative to IP </div></td>
                <td class="tdLine" style="font-size:16px;"><div > ${datim2017Form.totalNegative}</div></td>
            </tr>
            <tr >
                <td class="tdLine" style="font-size:16px; "><div >No HIV status reported to Implementing Partner</div></td>
                <td class="tdLine" style="font-size:16px;"><div > ${datim2017Form.totalUnknown}</div></td>
            </tr>
            <tr >
                <td class="tdLine" style="font-size:16px; "><div >Of those not reported: test not indicated</div></td>
                <td class="tdLine" style="font-size:16px;"><div >${datim2017Form.testNotIndicated}</div></td>
            </tr>
            <tr >
                <td class="tdLine" style="font-size:16px; "><div >Of those not reported: Other reasons</div></td>
                <td class="tdLine" style="font-size:16px;"><div >${datim2017Form.otherReasons} </div></td>
            </tr>
             <tr >
                <td class="tdLine" style="font-size:16px; height: 20px;" colspan="5"> </td>
             </tr>
            
                 
        </table>
             
             
        <table border="1" style="border-collapse: collapse; width: 800px;" align="center">
            <tr>
                <td class="tdLine" style="font-size:16px; background-color: #F39814" colspan="2"><div >OVC_SERV</div></td>
                
             </tr>
             <tr >
                <td class="tdLine" style="font-size:16px; background-color: aqua" colspan="2"><div >Number of beneficiaries served by PEPFAR OVC programs for children and families affected by HIV/AIDS</div></td>
                
             </tr>
             <tr >
                <td class="tdLine" style="font-size:16px;"><div >Numerator</div></td>
                <td class="tdLine" style="font-size:16px;" ><div >${datim2017Form.ovc_servNumerator} </div></td>
             </tr>
             <tr >
                <td class="tdLine" style="font-size:16px;" colspan="2"><div >Disaggregated by program status</div></td>
                
             </tr>
            <tr >
                <td class="tdLine" style="font-size:16px; "><div >Active</div></td>
                <td class="tdLine" style="font-size:16px;  "><div >${datim2017Form.ovc_servActive}</div></td>
             </tr>
            <tr >
                <td class="tdLine" style="font-size:16px; "><div >graduated</div></td>
                <td class="tdLine" style="font-size:16px;"><div >${datim2017Form.ovc_servGraduated}</div></td>
             </tr>
             <tr >
                <td class="tdLine" style="font-size:16px; "><div>Transfered</div></td>
                <td class="tdLine" style="font-size:16px; "><div >${datim2017Form.ovc_servTransfered}</div></td>
             </tr>
             <tr >
                <td class="tdLine" style="font-size:16px; "><div >Exited without graduation</div></td>
                <td class="tdLine" style="font-size:16px; "><div >${datim2017Form.ovc_servExitedWithoutGraduation}</div></td>
             </tr>
                
                <tr >
                    <td  colspan="4" style="height: 20px;"> </td>
                </tr>
                 
        </table>
        <table border="1" style="border-collapse: collapse; width: 800px;" align="center">
            
            <tr >
                <td class="tdLine" style="font-size:16px; "><div >Required</div></td>
                <td class="tdLine" style="font-size:16px;" colspan="4"><div >Disaggregated by age and sex (Fine disaggregation)</div></td>
             </tr>
            <tr >
                <td class="tdLine" style="font-size:16px; "><div >Under 10 </div></td>
                <td class="tdLine" style="font-size:16px;"><div > &lt;1</div></td>
                <td class="tdLine" style="font-size:16px;"><div >1-9</div></td>
                <td class="tdLine" style="font-size:16px;"><div > </div></td>
                <td class="tdLine" style="font-size:16px;"><div > </div></td>
             </tr>
             <tr >
                <td class="tdLine" style="font-size:16px;"></td>
                <td class="tdLine" style="font-size:16px;"><div>${datim2017Form.ovc_servLessThan1} </div></td>
                <td class="tdLine" style="font-size:16px;"><div>${datim2017Form.ovc_serv1To9} </div></td>
                <td class="tdLine" style="font-size:16px;"><div> </div></td>
                <td class="tdLine" style="font-size:16px;"><div> </div></td>
             </tr>
             <tr >
                <td class="tdLine" style="font-size:16px; height: 20px;" colspan="5"> </td>
             </tr>
            <tr >
                <td class="tdLine" style="font-size:16px; "><div > </div></td>
                <td class="tdLine" style="font-size:16px;"><div >10-14</div></td>
                <td class="tdLine" style="font-size:16px;"><div >15-17</div></td>
                <td class="tdLine" style="font-size:16px;"><div >18-24</div></td>
                <td class="tdLine" style="font-size:16px;"><div >25+</div></td>
             </tr>
            <tr >
                <td class="tdLine" style="font-size:16px; "><div >Female</div></td>
                <td class="tdLine" style="font-size:16px;"><div >${datim2017Form.ovc_servFemale10To14}</div></td>
                <td class="tdLine" style="font-size:16px;"><div >${datim2017Form.ovc_servFemale15To17}</div></td>
                <td class="tdLine" style="font-size:16px;"><div >${datim2017Form.ovc_servFemale18To24}</div></td>
                <td class="tdLine" style="font-size:16px;"><div >${datim2017Form.ovc_servFemale25AndAbove}</div></td>
             </tr>
             <tr >
                <td class="tdLine" style="font-size:16px; "><div >Male</div></td>
                <td class="tdLine" style="font-size:16px;"><div >${datim2017Form.ovc_servMale10To14}</div></td>
                <td class="tdLine" style="font-size:16px;"><div >${datim2017Form.ovc_servMale15To17}</div></td>
                <td class="tdLine" style="font-size:16px;"><div >${datim2017Form.ovc_servMale18To24}</div></td>
                <td class="tdLine" style="font-size:16px;"><div >${datim2017Form.ovc_servMale25AndAbove}</div></td>
             </tr>
             
                
                <tr >
                    <td  colspan="5" style="height: 20px;"> </td>
                </tr>
                 
        </table>
        
    </body>
</html>
