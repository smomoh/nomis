<%-- 
    Document   : DbExportPage
    Created on : May 4, 2012, 12:04:00 PM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%--
    Document   : DbMgtSelectionPage
    Created on : Aug 16, 2011, 11:37:25 AM
    Author     : smomoh
--%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data Transfer</title>
        <link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
   <style>
        .orglabel
        {
            color: #FFFFFF;
        }
    </style>
    <script language="javascript">
            function setActionName(val)
            {
                document.getElementById("actionName").value=val
            }

        </script>
    </head>
    <body>
        <html:form action="/databasemgt">
            <%--<html:hidden property="restoreDb"/>--%>
            <br/><br/><br/>
                     <center>
                         <table>
                             <tr><td><html:errors/></td></tr>
                             <tr><td>&nbsp;</td></tr>
                         </table>

       <!-- <div class="paramPage" style="width: 460px; height: 160px;">-->
               <table class="paramPage">
                   <tr><td colspan="5">&nbsp;</td></tr>
                   <tr><td colspan="5" class="orglabel" align="center">Data export</td></tr>
                   <tr><td>&nbsp; </td><td colspan="2" >
                           <html:hidden property="dbActionName" styleId="actionName" value="dbexport"/></td>
                   </tr>
                   <tr><td class="orglabel" align="left">State</td> <td colspan="2"><html:select property="state" styleId="state" style="width: 320px;" onchange="setActionName('lga'); forms[0].submit()">
                               
                        <logic:iterate id="stateObj" name="states">
                        <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                        </logic:iterate>
                    </html:select> </td></tr>
            <tr><td class="orglabel" align="left">LGA </td><td colspan="2">
                    <html:select property="lga" styleId="lga" style="width: 320px;" onchange="setActionName('cbo'); forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:iterate id="lgaObj" name="dbExportLgaList">
                        <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                        </logic:iterate>
                        </html:select> </td></tr>
            <tr><td class="orglabel" align="left">CBO </td><td colspan="2">
                    <html:select property="cbo" styleId="cbo" style="width: 320px;" >
                        <html:option value="All">All</html:option>
                    <logic:iterate id="cboObj" name="dbExportOrgList">
                      <html:option value="${cboObj.orgCode}">${cboObj.orgName}</html:option>
                        </logic:iterate>
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">Partner </td><td colspan="2">
                    <html:select property="partnerCode" styleId="partnerCode" style="width: 320px;" >
                        <html:option value="All">All</html:option>
                    <logic:iterate id="partner" name="partnerList">
                      <html:option value="${partner.partnerCode}">${partner.partnerName}</html:option>
                        </logic:iterate>
                    </html:select> </td>
            </tr>
            <tr><td class="orglabel" align="left">&nbsp; </td><td colspan="2" class="orglabel">
                    <html:checkbox property="entirePeriod" value="true">
                        Export for entire period
                    </html:checkbox> </td>
            </tr>
                   <tr><td><label style="color: white">Date of entry:</label></td>
                       <td style="color: white">
                       <html:select property="month1" styleId="month1" style="width: 73px;" disabled="false" >
                        <html:option value="1">January </html:option>
                        <html:option value="2">February </html:option>
                        <html:option value="3">March </html:option>
                        <html:option value="4">April </html:option>
                        <html:option value="5">May</html:option>
                        <html:option value="6">June</html:option>
                        <html:option value="7">July </html:option>
                        <html:option value="8">August </html:option>
                        <html:option value="9">September </html:option>
                        <html:option value="10">October </html:option>
                        <html:option value="11">November </html:option>
                        <html:option value="12">December</html:option>
                    </html:select>
                        <html:select property="year1"  style="width: 72px;" disabled="false" >
                            <html:option value="2008">2008 </html:option>
                            <html:option value="2009">2009 </html:option>
                            <html:option value="2010">2010 </html:option>
                            <html:option value="2011">2011 </html:option>
                            <html:option value="2012">2012 </html:option>
                            <html:option value="2013">2013 </html:option>
                            <html:option value="2014">2014 </html:option>
                            <html:option value="2015">2015 </html:option>
                            <html:option value="2016">2016 </html:option>
                            <html:option value="2017">2017 </html:option>
                            <html:option value="2018">2018 </html:option>
                            <html:option value="2019">2019 </html:option>
                            <html:option value="2020">2020 </html:option>
                        </html:select>
                       </td>
                       <td style="color: white"><label style=" color: white">To</label>
                       <html:select property="month2" styleId="month2" style="width: 72px;" disabled="false" >
                        <html:option value="1">January </html:option>
                        <html:option value="2">February </html:option>
                        <html:option value="3">March </html:option>
                        <html:option value="4">April </html:option>
                        <html:option value="5">May</html:option>
                        <html:option value="6">June</html:option>
                        <html:option value="7">July </html:option>
                        <html:option value="8">August </html:option>
                        <html:option value="9">September </html:option>
                        <html:option value="10">October </html:option>
                        <html:option value="11">November </html:option>
                        <html:option value="12">December</html:option>
                    </html:select>
                        <html:select property="year2" styleId="year2" style="width: 70px;" disabled="false" >
                            <html:option value="2008">2008 </html:option>
                            <html:option value="2009">2009 </html:option>
                            <html:option value="2010">2010 </html:option>
                            <html:option value="2011">2011 </html:option>
                            <html:option value="2012">2012 </html:option>
                            <html:option value="2013">2013 </html:option>
                            <html:option value="2014">2014 </html:option>
                            <html:option value="2015">2015 </html:option>
                            <html:option value="2016">2016 </html:option>
                            <html:option value="2017">2017 </html:option>
                            <html:option value="2018">2018 </html:option>
                            <html:option value="2019">2019 </html:option>
                            <html:option value="2020">2020 </html:option>
                        </html:select>
                       </td></tr>
                   <tr><td colspan="5">&nbsp;</td></tr>
                   <tr><td colspan="5" align="center"><html:submit  property="restore" value="Next >>" style="width: 120px; height: 25px;"/></td></tr>
               </table>

          </html:form>
         <!--</div>-->
        </center>
    </body>
</html>

