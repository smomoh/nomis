<%-- 
    Document   : CareplanAchievementReport
    Created on : Sep 11, 2017, 5:04:26 PM
    Author     : smomoh
--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Care plan achievement register</title>
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
    border: none; text-align: left; width: 40px;
}
</style>
    </head>
<body>
    <div align="center">
<div style=" font-family: arial" align="center">Careplan achievement register </div>
<table border=0>
    <tr><td style="border: none; text-align: left;">
            <table align="left" border="0" style="border: none;" width="100%">
                <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${cpaParam[12]}   </label></td>
                <td class="orgNamestd"><label class="lab">LGA:</label></td> <td style=" border: none;" width="25%"><label class="orgNames"> ${cpaParam[4]} </label></td>
                <td class="orgNamestd"><label class="lab">Organization:</label></td> <td style=" border: none;" width="25%"><label class="orgNames">${cpaParam[5]}   </label></td>
                <td class="orgNamestd"><label class="lab"><jsp:include page="../includes/WardName.jsp" /></label></td> <td style=" border: none;" width="25%"><label class="orgNames">${cpaParam[11]}   </label></td></tr>
                <tr><td class="orgNamestd" style=" font-size: 14px" colspan="4" align="center" width="50%"> Partner</td><td class="orgNamestd" style=" font-size: 14px" colspan="4" align="center" width="50%"> ${cpaParam[13]}</td> </tr>
                </table>
        </td></tr>

    <tr><td style="border: none; text-align: left">
                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th >Date of assessment (mm/dd/yyyy)</th>
                                <th>Unique id</th>
                                <th><jsp:include page="../includes/WardName.jsp" /></th>
                                <th >Household head name </th>
                                <th >Sex(M/F)</th>
                                <th >Age</th>
                                <th >Marital status</th>
                                <th >Address</th>
                                
                                <th >Phone</th>
                                
                                <th >Do Caregivers knows the HIV status of the children in their care?</th>
                                <th >Have all the children enrolled in this household who at risk of HIV and TB been referred to HIV testing services? </th>
                                <th >Have all the children enrolled in this household who were referred for HIV testing services, received the service?</th>
                                <th >In the last six months, are all children enrolled in the household who are HIV+, on treatment (Anti-retroviral treatment)?</th>
                                <th >Has the caregiver disclosed HIV status to children > 10 years in the household?</th>
                                <th >Are all the children in the household in need of other health services, been referred and receiving the necessary treatment?</th>
                                <th >In the past four weeks, has any member of this household gone a whole day and night without eating anything at all because there was not enough food?</th>
                                
                                <th >In the last year, has the caregiver been enrolled and participating in economic strengthening activities tailored to their level of economic vulnerability to help meet the critical needs of the children? </th>
                                <th >Does the caregiver belong to or know of any group that provides social and emotional support? </th>
                                <th >Has the household demonstrated abilities to achieve to achieve set goals to improve its resiliency?</th>
                                <th >In the last month, is there any child in this household who is withdrawn or consistently sad, and not able to participate in daily activities including playing with family and friends? </th>
                                <th >Have there been any child referred for child protection services because of concerns related to abuse, violence, exploitation or neglect? </th>
                                <th >Are the children in this household safe from abuse, neglect or exploitation? </th>
                                <th >Do all the children in this households have birth certificates? </th>
                                <th >Have caregivers completed training on a minimum of the two modules on "understanding parenting"? </th>
                                
                                <th >Have the child-headed household been linked to alternative care services provided by the Government of Nigeria (LGA,State and Federal) and/or other service providers?</th>
                                <th >In the last six months, are all the children 6-17 years old in this household enrolled in school attending regularly (not missing more than two days per month)?</th>
                                <th >In this household, did all the children who completed primary school, transition to secondary school in the last one year?</th>
                                <th >Are there any adolescents (15 years and above) enrolled in vocational training program who need time to complete the vocational training? </th>
                                <th >Are all children aged 3-5 years in this household participating in any early child care and development activities? </th>
                                <th >Total score </th>
                                <th >Marked for graduation?</th>
                                
                                <th >Partner</th>
                               <th >Recorded by</th>
                               <th >Date of entry (mm/dd/yyyy)</th>
                               
                            </tr>
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <logic:iterate id="hviData" name="cpareportList">

                                <%
                                            cnt++;
                                            if (cnt % 2 != 0) {
                                %>
                                <tr align="left" bgcolor="#FFFFFF">
                                    <%} else {
                                    %>
                                <tr align="left" bgcolor="#D7E5F2">
                                    <%            }
                                    %>

                                    <td width="3%"><%= ++row %></td>
                                    <td width="10%">
                                        ${hviData.dateOfAssessment}
                                    </td>
                                    <td width="20%">
                                       ${hviData.clientId}
                                    </td>
                                    <td width="20%">
                                       ${hviData.hhe.communityName}
                                    </td>
                                    <td width="10%">
                                        ${hviData.hhe.hhSurname} ${hviData.hhe.hhFirstname}
                                    </td>
                                    <td width="3%">
                                        ${hviData.hhe.hhGender}
                                    </td>
                                    <td width="3%">
                                        ${hviData.hhe.hhAge}
                                    </td>
                                    <td width="3%">
                                        ${hviData.hhe.maritalStatus}
                                    </td>
                                    <td width="20%">
                                        ${hviData.hhe.address}
                                    </td>
                                    
                                    
                                    <td width="20%">
                                        ${hviData.hhe.phone}
                                    </td>
                                    
                                    <td width="3%">
                                        ${hviData.hth_hivknowledge}
                                    </td>
                                    <td width="3%">
                                        ${hviData.hth_vchivrisk}
                                    </td>
                                    <td width="3%">
                                        ${hviData.hth_vcreftested}
                                    </td>
                                    
                                    <td width="3%">
                                        ${hviData.hth_hivOnArt}
                                    </td>
                                    <td width="3%">
                                        ${hviData.hth_hivdisclosed}
                                    </td>
                                    <td width="3%">
                                        ${hviData.hth_vcInneedOfHthServices}
                                    </td>
                                    <td width="3%">
                                        ${hviData.stb_hungryNoFood}
                                    </td>
                                    
                                    
                                    <td width="3%">
                                        ${hviData.stb_resiliency}
                                    </td>
                                    <td width="3%">
                                        ${hviData.sft_vcsad}
                                    </td>
                                    <td width="3%">
                                        ${hviData.stb_cgPartEconServ}
                                    </td>
                                    
                                    <td width="3%">
                                        ${hviData.stb_socEmotSupport}
                                    </td>
                                    <td width="3%">
                                        ${hviData.sft_vcreferredForCps}
                                    </td>
                                    <td width="3%">
                                        ${hviData.sft_vcSafeFromAbuse}
                                    </td>
                                    <td width="3%">
                                        ${hviData.sft_birthCert}
                                    </td>
                                    <td width="3%">
                                        ${hviData.sft_cgcompletedTwoModules}
                                    </td>
                                    <td width="3%">
                                        ${hviData.sft_childHeadedLinkedToServices}
                                    </td>
                                    <td width="3%">
                                        ${hviData.sch_schAttendance}
                                    </td>
                                    
                                    <td width="3%">
                                        ${hviData.sch_vcEnrolledInSecondarySch}
                                    </td>
                                    <td width="3%">
                                        ${hviData.sch_adolInVoctraining}
                                    </td>
                                    <td width="3%">
                                        ${hviData.sch_earlyChildCare}
                                    </td>
                                    <td width="3%">
                                        ${hviData.score}
                                    </td>
                                    <td width="3%">
                                        ${hviData.graduated}
                                    </td>
                                    
                                    
                                    <td width="10%">
                                        ${hviData.hhe.partnerCode}
                                    </td>
                                    
                                   <td width="4%">${hviData.recordedBy}</td>
                                   <td width="4%">${hviData.lastModifiedDate}</td>
                                   
                                   
                                </tr>
                            </logic:iterate>
                            </table>
        </td></tr></table>
        </div>
    </body>


</html>

