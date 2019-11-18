<%-- 
    Document   : EnrollmentRecord
    Created on : Oct 5, 2010, 12:35:14 PM
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
        <title>Enrollent Records</title>
        <link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
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
            <html:form action="/enrollmentReport" method="post">
                <html:hidden property="actionName" styleId="actionName"/>
        <!--<div id="enrlRecQueryDiv" class="paramPage" style="height: 220px; width: 400px;  margin-top: 100px;">-->
        <span>
        <center>
        <table class="paramPage">
            <tr><td>  </td><td> </td><td>&nbsp; </td><td> </td></tr> <!--onchange="getLga(this.value,'lgaForReports')"-->
            <tr><td class="orglabel" align="left">State </td><td colspan="3">
                    <html:select property="state" styleId="state" style="width: 290px;" onchange="setActionName('lga'); forms[0].submit();" ><html:option value="All">All</html:option>
                        <logic:iterate id="stateObj" name="stateListForReports">
                        <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                        </logic:iterate>
                    </html:select>
        </td></tr>
            <tr><td class="orglabel" align="left">LGA </td><td colspan="3">
                    <html:select property="lga" styleId="lga" style="width: 290px;" onchange="setActionName('cbo');forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:iterate id="lgaObj" name="lgaList">
                        <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                        </logic:iterate>
                        </html:select> </td></tr>
            <tr><td class="orglabel" align="left">CBO </td><td colspan="3">
                    <html:select property="cbo" styleId="cbo" style="width: 290px;" onchange="setActionName('ward'); forms[0].submit()" ><html:option value="All">
                        </html:option>
                        <logic:iterate id="cboObj" name="orgList">
                      <html:option value="${cboObj.orgCode}">${cboObj.orgName}</html:option>
                        </logic:iterate>
                    </html:select> </td></tr>
            <tr><td class="orglabel" align="left"><jsp:include page="../includes/WardName.jsp" /> </td><td colspan="3">
                    <html:select property="ward" styleId="ward" style="width: 290px;" ><html:option value="All"> </html:option>
                        <logic:iterate id="wardObj" name="wardList">
                        <html:option value="${wardObj.ward_code}">${wardObj.ward_name}</html:option>
                        </logic:iterate>
                    </html:select> </td></tr>
            <tr><td class="orglabel" align="left">Partner </td><td colspan="3">
                    <html:select property="partnerCode" styleId="partnerCode" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                    <logic:iterate id="partner" name="partnerList">
                      <html:option value="${partner.partnerCode}">${partner.partnerName}</html:option>
                        </logic:iterate>
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
                            <logic:iterate id="year" name="yearList" >
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
                <td><label style=" margin-right: 5px; color:white">To </label> <html:select property="enroll_month2" styleId="enroll_month2" style="width: 70px;">
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
                            <logic:iterate id="year" name="yearList" >
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
            <tr><td class="title" align="left"><label style=" margin-right: 5px; color:white">Age</label></td>
                <td colspan="2" align="left">
                    
                    <html:select property="enroll_startAge" styleId="enroll_startAge" style="width: 55px;">
                            <html:option value="0">0 </html:option>
                            <html:option value="1">1 </html:option>
                            <html:option value="2">2 </html:option>
                            <html:option value="3">3 </html:option>
                            <html:option value="4">4 </html:option>
                            <html:option value="5">5 </html:option>
                            <html:option value="6">6 </html:option>
                            <html:option value="7">7 </html:option>
                            <html:option value="8">8 </html:option>
                            <html:option value="9">9</html:option>
                            <html:option value="10">10 </html:option>
                            <html:option value="11">11 </html:option>
                            <html:option value="12">12 </html:option>
                            <html:option value="13">13</html:option>
                            <html:option value="14">14 </html:option>
                            <html:option value="15">15 </html:option>
                            <html:option value="16">16</html:option>
                            <html:option value="17">17</html:option>
                            <html:option value="18">18</html:option>
                   </html:select>
                <label style=" color: white">to</label>  <html:select property="enroll_endAge" styleId="enroll_endAge" style="width: 55px;">
                        <html:option value="0">0 </html:option>
                            <html:option value="1">1 </html:option>
                            <html:option value="2">2 </html:option>
                            <html:option value="3">3 </html:option>
                            <html:option value="4">4 </html:option>
                            <html:option value="5">5 </html:option>
                            <html:option value="6">6 </html:option>
                            <html:option value="7">7 </html:option>
                            <html:option value="8">8 </html:option>
                            <html:option value="9">9</html:option>
                            <html:option value="10">10 </html:option>
                            <html:option value="11">11 </html:option>
                            <html:option value="12">12 </html:option>
                            <html:option value="13">13</html:option>
                            <html:option value="14">14 </html:option>
                            <html:option value="15">15 </html:option>
                            <html:option value="16">16</html:option>
                            <html:option value="17">17</html:option>
                            <html:option value="18">18</html:option>
                            <html:option value="18+">18+</html:option>
                    </html:select>
                    </td></tr>
            <tr><td colspan="4" align="center"><html:submit property="enrldateQueryBtn" value="Submit" onclick="setActionName('ovcList')" style="width: 70px;" />&nbsp; <!--<input type="button" property="enrlQuerycancelBtn" value="Cancel" onclick="hideComponent('enrlRecQueryDiv')" style="width: 70px;" />--> </td></tr>
        </table>
        </center>
        </span>
    <!--</div>-->
            </html:form>
        </center>
    </body>
</html>
