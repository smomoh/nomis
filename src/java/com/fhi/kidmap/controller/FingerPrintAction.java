/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.nomis.business.FingerPrint;
import com.fhi.kidmap.dao.FingerPrintDao;
import com.fhi.kidmap.dao.FingerPrintDaoImpl;
import com.fhi.nomis.nomisutils.FileLister;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class FingerPrintAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    
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
        File file=null;
        int sno=1234;
        FileLister fl=new FileLister();
        List fileList=fl.getFiles("C:\\images");
        String parentName=null;
        String fileName=null;
        for(int i=0; i<fileList.size(); i++)
        {
            file=(File)fileList.get(i);
            byte[] bfile=new byte[(int)file.length()];
            try
            {
                FileInputStream fileInputStream=new FileInputStream(file);
                fileInputStream.read(bfile);
                fileInputStream.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            parentName=file.getParent().substring(file.getParent().lastIndexOf("\\")+1);
            fileName=file.getName().substring(0, file.getName().indexOf("."));
            String id=parentName+"-"+fileName;
            sno++;
            FingerPrintDao fdao=new FingerPrintDaoImpl();
            FingerPrint fingerPrint=new FingerPrint();
            fingerPrint.setId(id);
            fingerPrint.setUid(file.length());
            fingerPrint.setOvcId("FCT/AMC/GDF/"+sno);
            fingerPrint.setImage(bfile);
            
            FingerPrint fingerPrint2=fdao.getFingerPrint(bfile.length);
            
           
            System.err.println("length of "+file.getName()+" is "+file.length());
                if(fingerPrint2 !=null)
                {
                    if(fingerPrint2.getId().equalsIgnoreCase(fingerPrint.getId()))
                    System.err.println("Record already exist");
                    else
                    System.err.println("Finger print id has changed");
                }
                else
                fdao.saveFingerPrint(fingerPrint);
        }

        /*FingerPrint fingerPrint2=fdao.getFingerPrint("123456");
        byte[] outputByte=fingerPrint2.getImage();
        try
        {
            FileOutputStream fos=new FileOutputStream("C:\\images\\output\\outputImage.jpg");
            fos.write(outputByte);
            fos.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }*/
        return mapping.findForward(SUCCESS);
    }
}
