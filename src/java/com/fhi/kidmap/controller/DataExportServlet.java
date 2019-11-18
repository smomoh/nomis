/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
/**
 *
 * @author COMPAQ USER
 */
public class DataExportServlet extends HttpServlet {
   //private int count=0;
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/xml");
        response.setHeader("Content-Disposition", "attachment; filename=\"C:/KidmapBackup/OvcRecords.zip\"");

        PrintWriter pw = response.getWriter();
        ServletOutputStream out=response.getOutputStream();
        
        try {
                //String count=(String)request.getAttribute("counter");
                //out.print(count);
                pw.println("<b>Database export complete</b> ");

        } 
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally {
            out.close();
        }
        

    }


}
