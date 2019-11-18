<%-- 
    Document   : CaregiverListParamPage
    Created on : Apr 28, 2011, 9:58:30 AM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
        <title>List of caregivers</title>
        <style type="text/css">
<!--
a {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #333333;
	font-weight: bold;
}
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
	color: #000000;
}
a:hover {
	text-decoration: underline;
	color: #0075B6;
}
a:active {
	text-decoration: none;
	color: #000000;
}
.title
{
  color: #FFFFFF;
  font-weight: bold;
}
.orglabel
{
    color: #FFFFFF;
}

-->
</style>
        <script language="javascript">
            function setActionName(val)
            {
                document.getElementById("actionName").value=val
            }

        </script>
    </head>
    <body>
        <br/><br/>
        <center>
            <html:form action="/listofcaregiver" method="post">
                <html:hidden property="actionName" styleId="actionName"/>

                <!--<div styleId="sumstatDivPerMth" class="paramPage" style="height: 160px; width: 400px; margin-top: 100px;">-->
        <span>
        <center>
        <table class="paramPage">
            <%--<tr><td colspan="4" class="title" align="center">${dbsource}----</td></tr>--%>
            <tr><td colspan="4" class="title" align="center">List of caregivers</td></tr>
            <tr><td class="orglabel" align="left">State </td><td colspan="3">
                    <html:select property="state" styleId="state" style="width: 290px;" onchange="setActionName('lga'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="stateListForReports">
                            <logic:iterate id="stateObj" name="stateListForReports">
                                <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">Group </td><td colspan="3">
                    <html:select property="orgUnitGroupId" styleId="orgUnitGroupId" style="width: 290px;" onchange="setActionName('allLga'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="ssorgUnitGroupList">
                            <logic:iterate id="oug" name="ssorgUnitGroupList">
                            <html:option value="${oug.orgUnitGroupId}">${oug.orgUnitGroupName}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">LGA </td><td colspan="3"><html:select property="lga" styleId="lga" style="width: 290px;" onchange="setActionName('cbo'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="caregiverlgaList">
                            <logic:iterate id="lgaObj" name="caregiverlgaList">
                                <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                            </logic:iterate>
                        </logic:present>
                        </html:select> </td></tr>
            <tr><td class="orglabel" align="left">CBO </td><td colspan="3">
                    <html:select property="cbo" styleId="cbo" style="width: 290px;" onchange="setActionName('ward'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="orgList">
                            <logic:iterate id="cboObj" name="orgList">
                              <html:option value="${cboObj.orgCode}">${cboObj.orgName}</html:option>
                            </logic:iterate>
                    </logic:present>
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left"><jsp:include page="../includes/WardName.jsp" /> </td><td colspan="3">
                    <html:select property="ward" styleId="ward" style="width: 290px;" >
                        <html:option value="All"> </html:option>
                        <logic:present name="caregiverwardList">
                            <logic:iterate id="wardObj" name="caregiverwardList">
                                <html:option value="${wardObj.ward_code}">${wardObj.ward_name}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select> </td></tr>
            <tr><td class="orglabel" align="left">Partner </td><td colspan="3">
                    <html:select property="partnerCode" styleId="partnerCode" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                        <logic:present name="partnerList">
                            <logic:iterate id="partner" name="partnerList">
                                <html:option value="${partner.partnerCode}">${partner.partnerName}</html:option>
                            </logic:iterate>
                    </logic:present>
                    </html:select> </td>
            </tr>
            <tr><td class="title" align="left"><label style=" margin-right: 5px; color:white">From</label></td><td>
                    <html:select property="enroll_month" styleId="enroll_month" style="width: 70px;">
                        <html:option value="01">January </html:option>
                        <html:option value="02">February </html:option>
                        <html:option value="03">March </html:option>
                        <html:option value="04">April </html:option>
                        <html:option value="05">May</html:option>
                        <html:option value="06">June</html:option>
                        <html:option value="07">July </html:option>
                        <html:option value="08">August </html:option>
                        <html:option value="09">September </html:option>
                        <html:option value="10">October </html:option>
                        <html:option value="11">November </html:option>
                        <html:option value="12">December</html:option>
                    </html:select>
                    <html:select property="enroll_year" styleId="enroll_year" style="width: 55px;">
                        <logic:present name="yearList">
                            <logic:iterate name="yearList" id="year">
                                <html:option value="${year}">${year}</html:option>
                            </logic:iterate>
                        </logic:present>
                            <%--<html:option value="2008">2008 </html:option>
                            <html:option value="2009">2009 </html:option>
                            <html:option value="2010" dir="2010">2010 </html:option>
                            <html:option value="2011">2011 </html:option>
                            <html:option value="2012">2012 </html:option>
                            <html:option value="2013">2013 </html:option>
                            <html:option value="2014">2014 </html:option>
                            <html:option value="2015">2015 </html:option>
                            <html:option value="2016">2016 </html:option>
                            <html:option value="2017">2017 </html:option>
                            <html:option value="2018">2018 </html:option>
                            <html:option value="2019">2019 </html:option>
                            <html:option value="2020">2020 </html:option>--%>
                        </html:select> </td>
                <td><label style=" margin-right: 5px; color:white">To </label> 
                    <html:select property="enroll_month2" styleId="enroll_month2" style="width: 70px;">
                        <html:option value="01">January </html:option>
                        <html:option value="02">February </html:option>
                        <html:option value="03">March </html:option>
                        <html:option value="04">April </html:option>
                        <html:option value="05">May</html:option>
                        <html:option value="06">June</html:option>
                        <html:option value="07">July </html:option>
                        <html:option value="08">August </html:option>
                        <html:option value="09">September </html:option>
                        <html:option value="10">October </html:option>
                        <html:option value="11">November </html:option>
                        <html:option value="12">December</html:option>
                    </html:select>
                    <html:select property="enroll_year2" styleId="enroll_year2" style="width: 65px;">
                        <logic:present name="yearList">
                            <logic:iterate name="yearList" id="year">
                                <html:option value="${year}">${year}</html:option>
                            </logic:iterate>
                        </logic:present>
                            <%--<html:option value="2008">2008 </html:option>
                            <html:option value="2009">2009 </html:option>
                            <html:option value="2010" filter="2010">2010 </html:option>
                            <html:option value="2011">2011 </html:option>
                            <html:option value="2012">2012 </html:option>
                            <html:option value="2013">2013 </html:option>
                            <html:option value="2014">2014 </html:option>
                            <html:option value="2015">2015 </html:option>
                            <html:option value="2016">2016 </html:option>
                            <html:option value="2017">2017 </html:option>
                            <html:option value="2018">2018 </html:option>
                            <html:option value="2019">2019 </html:option>
                            <html:option value="2020">2020 </html:option>--%>
                        </html:select></td></tr>
             <tr><td colspan="4" align="center"><html:submit property="sumstatPerMth_dateBtn" value="Submit" onclick="setActionName('caregiverList')" style="width: 70px; margin-left: 70px;" /></td></tr>
        </table>
        </center>
        </span>
    <!--</div>-->

            </html:form>
        </center>
    </body>
</html>


