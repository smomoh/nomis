<%-- 
    Document   : single_upload_page
    Created on : May 5, 2012, 5:11:36 PM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.io.*" %>
<%@ page import="com.fhi.kidmap.business.*" %>
<%
AppUtility appUtil=new AppUtility();
String contentType = request.getContentType();
if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
{
    DataInputStream in = new DataInputStream(request.getInputStream());
    int formDataLength = request.getContentLength();
    byte dataBytes[] = new byte[formDataLength];
    int byteRead = 0;
    int totalBytesRead = 0;
    
    while (totalBytesRead < formDataLength)
    {
        byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
        totalBytesRead += byteRead;
    }
    String file = new String(dataBytes);
    String saveFile = file.substring(file.indexOf("filename=\"") + 10);
    saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
    saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,saveFile.indexOf("\""));
   int lastIndex = contentType.lastIndexOf("=");
   String boundary = contentType.substring(lastIndex + 1,contentType.length());
   int pos;
   
   pos = file.indexOf("filename=\"");
   pos = file.indexOf("\n", pos) + 1;
   pos = file.indexOf("\n", pos) + 1;
   pos = file.indexOf("\n", pos) + 1;
   int boundaryLocation = file.indexOf(boundary, pos) - 4;
   int startPos = ((file.substring(0, pos)).getBytes()).length;
   int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
   try
   {
        FileOutputStream fileOut = new FileOutputStream(appUtil.getImportFilePath()+"\\"+saveFile);
        fileOut.write(dataBytes, startPos, (endPos - startPos));
        fileOut.flush();                fileOut.close();
        request.getSession().setAttribute("fileUploaded", "dbImportSelected");
        response.sendRedirect("databasemgt.do");
   }
   catch(FileNotFoundException fnfe)
   {
       response.sendRedirect("FileUpload.jsp");
   }
   catch(Exception ex)
   {
       response.sendRedirect("FileUpload.jsp");
   }
}
   %>

  