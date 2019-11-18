<%--
    Document   : AgeAnalysisReport
    Created on : Jun 24, 2015, 5:05:01 PM
    Author     : smomoh
--%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Age analysis report</title>
    </head>
    <body>
        <br/><br/><br/>
        <center>
        <div align="center" style=" width: 900px; background-color: white;"><br/>



                <table border="1" cellspacing="0" cellpadding="0" style="border:1px black solid; width: 800px; border-collapse: collapse">

                            <tr style="background-color: #FFFFFF">
                              <td ><strong>State</strong></td><td ><strong>Lga</strong></td><td ><strong>CBO</strong></td>
                              <td><strong>Total number of OVC</strong></td><td><strong>Number of OVC</strong></td>
                              <td><strong>Number of OVC out of school</strong></td><td><strong>Number of OVC &gt; 15 yrs</strong></td>
                            </tr>
                            <logic:iterate name="ssbreportList" id="ssb">
                             <tr>

                                  <td> ${ssb.state}</td><td> ${ssb.lga}</td><td> ${ssb.cbo}</td>
                                  <td> ${ssb.totNoOfOvc}</td>
                                  <td><a href="ageAnalysisReportAction.do?id=${ssb.stateCode}&&name=list" target="_blank" >${ssb.noOfOvc}</a></td>
                                  <td> ${ssb.value3}</td><td> ${ssb.value4}</td>
                              </tr>
                              </logic:iterate>
                          </table>
            </div>
            </center>
    </body>
</html>
