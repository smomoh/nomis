<%-- 
    Document   : ChartParamPage
    Created on : Aug 11, 2011, 9:32:58 AM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%
request.setAttribute("chartno", request.getParameter("chartNo"));
request.setAttribute("charttitle", request.getParameter("title"));
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../images/kidmap2.css" rel="stylesheet" type="text/css" />
        <title>Chart selection page</title>
    </head>
    <body>
        <br/><br/>
        <center><label style="color: blue; font-size: 16px; font-family: Arial">${charttitle}</label></center><br/><br/>
        <br/><br/>
        <html:form action="/chartaction">
            <html:hidden property="chartType" value="${chartno}" />
            <html:hidden property="chartTitle" value="${charttitle}" />
            <center>
                
        <div class="paramPage" style="height: 160px; width: 400px;">
            <span>
                <center>
                    <table class="paramPage">

                        <tr><td align="center" class="title" colspan="4">&nbsp; <html:errors/> </td></tr>
                        <tr><td align="center" class="title" colspan="4">&nbsp; </td></tr>
                        <tr><td class="orglabel" align="left">Period </td><td align="left">
                                <html:select property="month1" styleId="month1" styleClass="smallfieldcellinput" style="width:60px;">
                                    <html:option value=""> </html:option>
                                    <html:option value="01">Jan</html:option>
                                    <html:option value="02">Feb</html:option>
                                    <html:option value="03">Mar</html:option>
                                    <html:option value="04">Apr</html:option>
                                    <html:option value="05">May</html:option>
                                    <html:option value="06">Jun</html:option>
                                    <html:option value="07">Jul</html:option>
                                    <html:option value="08">Aug</html:option>
                                    <html:option value="09">Sep</html:option>
                                    <html:option value="10">Oct</html:option>
                                    <html:option value="11">Nov</html:option>
                                    <html:option value="12">Dec</html:option>
                                </html:select>

                                <html:select property="year1" styleId="year1" styleClass="smallfieldcellinput" style="width:60px;">
                                    <html:option value=""> </html:option>
                                    <html:option value="2000">2000 </html:option>
                                    <html:option value="2001">2001 </html:option>
                                    <html:option value="2002">2002 </html:option>
                                    <html:option value="2003">2003 </html:option>
                                    <html:option value="2004">2004 </html:option>
                                    <html:option value="2005">2005 </html:option>
                                    <html:option value="2006">2006 </html:option>
                                    <html:option value="2007">2007 </html:option>
                                    <html:option value="2008">2008 </html:option>
                                    <html:option value="2009">2009 </html:option>
                                    <html:option value="2010">2010 </html:option>
                                    <html:option value="2011">2011 </html:option>
                                    <html:option value="2012">2012 </html:option>
                                    <html:option value="2013">2013 </html:option>
                                    <html:option value="2014">2014</html:option>
                                    <html:option value="2015">2015</html:option>
                                    <html:option value="2016">2016</html:option>
                                    <html:option value="2017">2017</html:option>
                                    <html:option value="2018">2018</html:option>
                                    <html:option value="2019">2019</html:option>
                                    <html:option value="2020">2020</html:option>
                                </html:select>



                            </td><td>to</td><td>

                                <html:select property="month2" styleId="month2" styleClass="smallfieldcellinput" style="width:60px;">
                                    <html:option value=""> </html:option>
                                    <html:option value="01">Jan</html:option>
                                    <html:option value="02">Feb</html:option>
                                    <html:option value="03">Mar</html:option>
                                    <html:option value="04">Apr</html:option>
                                    <html:option value="05">May</html:option>
                                    <html:option value="06">Jun</html:option>
                                    <html:option value="07">Jul</html:option>
                                    <html:option value="08">Aug</html:option>
                                    <html:option value="09">Sep</html:option>
                                    <html:option value="10">Oct</html:option>
                                    <html:option value="11">Nov</html:option>
                                    <html:option value="12">Dec</html:option>
                                </html:select>

                                <html:select property="year2" styleId="year2" styleClass="smallfieldcellinput" style="width:60px;">
                                    <html:option value=""> </html:option>
                                    <html:option value="2000">2000 </html:option>
                                    <html:option value="2001">2001 </html:option>
                                    <html:option value="2002">2002 </html:option>
                                    <html:option value="2003">2003 </html:option>
                                    <html:option value="2004">2004 </html:option>
                                    <html:option value="2005">2005 </html:option>
                                    <html:option value="2006">2006 </html:option>
                                    <html:option value="2007">2007 </html:option>
                                    <html:option value="2008">2008 </html:option>
                                    <html:option value="2009">2009 </html:option>
                                    <html:option value="2010">2010 </html:option>
                                    <html:option value="2011">2011 </html:option>
                                    <html:option value="2012">2012 </html:option>
                                    <html:option value="2013">2013 </html:option>
                                    <html:option value="2014">2014</html:option>
                                    <html:option value="2015">2015</html:option>
                                    <html:option value="2016">2016</html:option>
                                    <html:option value="2017">2017</html:option>
                                    <html:option value="2018">2018</html:option>
                                    <html:option value="2019">2019</html:option>
                                    <html:option value="2020">2020</html:option>
                                </html:select>

                            </td></tr>


                        <tr><td class="orglabel" align="left">&nbsp;</td><td> </td><td> </td><td> </td></tr>
                        <tr><td colspan="4" align="center"><html:submit value="OK" style="width: 70px;" />&nbsp; <input type="button" id="chart1CancelBtn" value="Cancel" onclick="hideComponent()" style="width: 70px;" /> </td></tr>
                    </table>
                </center>
            </span>
        </div>
       </center>
        </html:form>
    </body>
</html>
