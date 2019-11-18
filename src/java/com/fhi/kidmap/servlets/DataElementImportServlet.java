/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.servlets;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author smomoh
 */
public class DataElementImportServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
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
       HttpSession session=request.getSession();
       String fileName="C:\\Nomis\\Transfer\\dhis\\import\\"+saveFile;
        FileOutputStream fileOut = new FileOutputStream(fileName);
        fileOut.write(dataBytes, startPos, (endPos - startPos));
        fileOut.flush();                
        fileOut.close();
        session.setAttribute("fileName", fileName);

        session.setAttribute("dataElementFileUploaded", "deFileUploaded");
        response.sendRedirect("databasemgt.do");
   }
   catch(FileNotFoundException fnfe)
   {
       response.sendRedirect("DataElementsUploadPage.jsp");
   }
   catch(Exception ex)
   {
       response.sendRedirect("DataElementsUploadPage.jsp");
   }
}
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
