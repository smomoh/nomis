/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author COMPAQ USER
 */
public class FileUploadAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
         //to get the content type information from JSP Request Header
        String contentType = request.getContentType();
        //here we are checking the content type is not equal to Null and as well as the passed data from mulitpart/form-data is greater than or equal to 0
    if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
    {
        DataInputStream in = new DataInputStream(request.getInputStream());
    //we are taking the length of Content type data
        int formDataLength = request.getContentLength();
        if(formDataLength < 1)
        return mapping.findForward(FAILURE);
        byte dataBytes[] = new byte[formDataLength];
        int byteRead = 0;
        int totalBytesRead = 0;
    //this loop converting the uploaded file into byte code
        while (totalBytesRead < formDataLength)
        {
            byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
            totalBytesRead += byteRead;
        }
        String file = new String(dataBytes);
    //for saving the file name
        String saveFile = file.substring(file.indexOf("filename=\"") + 10);
        saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
        saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,saveFile.indexOf("\""));
        int lastIndex = contentType.lastIndexOf("=");
        String boundary = contentType.substring(lastIndex + 1,contentType.length());
        int pos;
        //extracting the index of file
        pos = file.indexOf("filename=\"");
        pos = file.indexOf("\n", pos) + 1;
        pos = file.indexOf("\n", pos) + 1;
        pos = file.indexOf("\n", pos) + 1;
        int boundaryLocation = file.indexOf(boundary, pos) - 4;
        int startPos = ((file.substring(0, pos)).getBytes()).length;
        int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
        // creating a new file with the same name and writing the content in new file
        //FileOutputStream fileOut = new FileOutputStream(saveFile);
        FileOutputStream fileOut = new FileOutputStream("C:\\Nomis\\Transfer\\Import\\"+saveFile);
        fileOut.write(dataBytes, startPos, (endPos - startPos));
        fileOut.flush();                fileOut.close();
    }
        
        return mapping.findForward(SUCCESS);
    }
}
