/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.nomisutils;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;

/**
 *
 * @author Siaka
 */
public class FileUploader implements Serializable
{
    AppUtility appUtil=new AppUtility();
    public void uploadFile(String contentType, InputStream is,int formDataLength)
    {
        try
        {
            if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
            {
                System.err.println("is.getClass().getCanonicalName() is "+formDataLength);
                DataInputStream in = new DataInputStream(is);
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
                if(saveFile.indexOf("\n") !=-1)
                saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
                if(saveFile.lastIndexOf("\\") !=-1 && saveFile.indexOf("\"") !=-1)
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
               int endPos=0;
               if(boundaryLocation > 0)
               {
                    endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
               }
               else
               endPos = (file.getBytes()).length;    
               appUtil.createDirectories(appUtil.getImportFilePath());
                FileOutputStream fileOut = new FileOutputStream(appUtil.getImportFilePath()+"\\"+saveFile);
                fileOut.write(dataBytes, startPos, (endPos - startPos));
                fileOut.flush();                
                fileOut.close();
            }
       }
       catch(FileNotFoundException fnfe)
       {
           fnfe.printStackTrace();
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
    }
} 
