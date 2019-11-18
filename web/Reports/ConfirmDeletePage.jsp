<%-- 
    Document   : ConfirmDeletePage
    Created on : Nov 21, 2010, 9:54:11 AM
    Author     : COMPAQ USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%
    String id=request.getParameter("ovcId");
    session.setAttribute("id", id);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Remove beneficiary </title>
    </head>
    <body>
        <br/><br/><br/>
        <center><!--OvcWithdrawalList.jsp-->
        <h1>Are you sure you want to remove this beneficiary from the withdrawal list?<br/>
        Click <a href="../ovcWithdrawal.do?ovcId=${id}&an=remove" >Yes </a> to remove. Click <a href="../ovcWithdrawal.do">No</a> to go back</h1>
        </center>
    </body>
</html>
