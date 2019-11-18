<%-- 
    Document   : RestoreDbPage
    Created on : Feb 7, 2011, 1:59:22 PM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Database import</title>
<script language="javascript">
function setBtnName(name)
{
     if(confirm("Are you sure you want to "+name+" this record?"))
     {
            document.getElementById("buttonId").value = name
            return true
     }
       return false
}
</script>
    </head>
    <body>
        <br/><br/><br/><br/>
        <center>
            <div style=" background-color: teal; border: 1px black solid; width: 700px;">
                <table style=" width: 700px; background-color: teal; text-align: left; color: white" >
                    <tr><td><h2>No of enrollment records imported = ${backUpCount[0]}</h2></td></tr>
                    <tr><td><h2>No of duplicates detected = ${backUpCount[1]}</h2> </td></tr>
                    <%--<tr><td><h2>No of enrollment records ready to be saved = ${savableCountList[1]} </h2></td></tr>
                    <tr><td><h2>No of service records imported = ${backUpServiceCount[0]}</h2></td></tr>
                    <tr><td><h2>No of service duplicates detected = ${backUpServiceCount[1]}</h2></td></tr>
                    <tr><td><h2>No of service records ready to be saved = ${savableCountList[3]} </h2></td></tr>--%>
                    <tr><td align=" center"> &nbsp;</td></tr>
                    <form action="Reports/SaveImportedRecords.jsp" method="post">
                        <tr><td><center><input type="submit" name="saveDb" value="Save" style=" width: 90px" ${btnDisabled} onclick=" return setBtnName('save')"/></center></td></tr>
                    </form>
                </table>
            </div>
        </center>
                   
    </body>
</html>
