<%-- 
    Document   : TrainingReportView
    Created on : Mar 19, 2015, 8:01:10 PM
    Author     : Siaka
--%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Training report</title>
 </head>
    <center>
        <br/>
    <table border="0" cellspacing="0" cellpadding="0">
        <tr><td colspan="4" align="center">Training report</td></tr>
        <tr><td colspan="4" align="center"> </td></tr>
        <tr><td colspan="4">${trainingTitle}</td></tr>
    </table>
    <br/>
    <table border="1" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th>Training name</th>
                                <th >Organization</th>
                                <th >Participant name</th>
                                <th >Category</th>
                                <th >Designation</th>
                                <th >Training Status</th>
                                <th >Start date</th>
                                <th >End date</th>
                            </tr>
                            
                            <%
                                        int cnt = 0;
                                        int row =0;
                            %>
                            <!-- iterate over the results of the query -->
                           <logic:iterate id="training" name="trainingList">

                                <%
                                            cnt++;
                                            if (cnt % 2 != 0) {
                                %>
                                <tr align="left" bgcolor="#FFFFFF">
                                    <%} else {
                                    %>
                                <tr align="left" bgcolor="#DDDDDD">
                                    <%            }
                                    %>

                                    <td width="1%"><%= ++row %></td>
                                    <td width="5%">
                                     ${training.trainingName}
                                    </td>
                                    <td width="9%">
                                        ${training.organizationCode}
                                    </td>
                                    <td width="9%">
                                        ${training.participantName}
                                    </td>
                                    <td width="3%">
                                        ${training.category}
                                    </td>
                                    <td width="8%">
                                        ${training.designation}
                                    </td>
                                    <td width="5%">
                                        ${training.trainingStatus}
                                    </td>
                                    <td width="5%"> 
                                        ${training.startDate}
                                    </td>
                                    <td width="5%">
                                        ${training.endDate}
                                    </td>
                                    
                                  </tr>
                           </logic:iterate>
                                  </table>
                            </center>
</html>
