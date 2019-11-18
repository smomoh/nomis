<%-- 
    Document   : FileUpload
    Created on : May 5, 2012, 5:00:02 PM
    Author     : smomoh
--%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD>
        <TITLE>Load files for import</TITLE>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
    </HEAD>
    <% //  for uploading the file we used Encrypt type of multipart/form-data and input of file type to browse and submit the file %>
    <BODY> <FORM  ENCTYPE="multipart/form-data" ACTION="single_upload_page.jsp" METHOD=POST>
            <br><br><br>
            <center>
                <table border="0" class="paramPage" style="border-collapse: collapse" >
                    <tr><td colspan="2"> &nbsp;</td></tr>
                    <tr><center>
                        <td colspan="2"><p align="center" style=" color: white"><B>Load files to import folder</B></td></center></tr>

                        <tr><td colspan="2"> &nbsp;</td></tr>
                        <tr><td style=" color: white;"><label style="margin-left: 20px"><b>Choose file:&nbsp;</b></label></td><td><INPUT NAME="F1" TYPE="file" style=" margin-right: 20px;"></td></tr>
                        <tr><td colspan="2"> &nbsp;</td></tr>
                            
                        <tr><td colspan="2" align="center"><INPUT TYPE="submit" VALUE="Load file" ></td></tr>
                        <tr><td colspan="2"> &nbsp;</td></tr>
                        </table>
            </center>
     </FORM>
</BODY></HTML>
