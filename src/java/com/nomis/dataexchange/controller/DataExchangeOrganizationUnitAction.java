/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.dataexchange.controller;

import com.fhi.kidmap.business.ZipHandler;
import com.fhi.nomis.nomisutils.AppUtility;
import com.nomis.dataexchange.dao.DhisInstanceDao;
import com.nomis.dataexchange.dao.DhisInstanceDaoImpl;
import com.nomis.dataexchange.pojo.MetadataManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author smomoh
 */
public class DataExchangeOrganizationUnitAction extends org.apache.struts.action.Action {

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
        DataExchangeOrganizationUnitForm dxouForm=(DataExchangeOrganizationUnitForm)form;
        ZipHandler zipHandler=new ZipHandler();
        DhisInstanceDao dao=new DhisInstanceDaoImpl();
        
        String softwareInstance=dxouForm.getSoftwareInstance();
        String metadataType=dxouForm.getMetadataType();
        FormFile uploadedFile=dxouForm.getUpload();
        AppUtility appUtil=new AppUtility();
        InputStream inputStream=null;
        String requiredAction=dxouForm.getActionName();
        HttpSession session=request.getSession();
        
        List instanceList=dao.getAllDhisInstances();
        session.setAttribute("instanceList", instanceList);
        //System.err.println("requiredAction is "+requiredAction+" Metadata type is "+metadataType+" and softwareInstance is "+softwareInstance);
        if(requiredAction==null)
        {
            dxouForm.reset(mapping, request);
            //etOrganizationUnitLists(mdmgtForm);
            return mapping.findForward(SUCCESS); 
        }
        else if(requiredAction.equalsIgnoreCase("import"))
        {
            MetadataManager mdmgr=new MetadataManager();
            if(metadataType !=null)
            {
            appUtil.deleteFiles(appUtil.getDxExportFilePath());
            //System.err.println("Metadata type is "+metadataType+" and softwareInstance is "+softwareInstance);
            if(uploadedFile !=null)
            {
                String uploadDirPath = appUtil.getDxImportFilePath();
                String filePath=null;
                File folder = new File(uploadDirPath);
                if(!folder.exists())
                {
                    folder.mkdir();
                }
                String fileName = uploadedFile.getFileName();
                
                if(fileName !=null && fileName.endsWith(".zip"))
                {
                   filePath=uploadDirPath+"\\"+fileName;
                   String destination=appUtil.getDxExportFilePath();
                   uploadFile(uploadedFile, uploadDirPath, fileName);
                   zipHandler.unZipFile(filePath,destination);
                   //zipHandler.unZipFile(appUtil.getDxImportFilePath()+"\\"+uploadedFile.getFileName());
                   File xmlFileDir=new File(destination);
                   String[] xmlFiles=xmlFileDir.list();
                   for(int i=0; i<xmlFiles.length; i++)
                   {
                       filePath=destination+"\\"+xmlFiles[i];
                       if(metadataType.equalsIgnoreCase("orgunit"))
                       mdmgr.processOrgUnit(filePath, softwareInstance);
                       else if(metadataType.equalsIgnoreCase("dataelement"))
                       mdmgr.processDataElement(filePath, softwareInstance);
                       if(metadataType.equalsIgnoreCase("catcombo"))
                       mdmgr.processCatCombo(filePath, softwareInstance);
                       System.err.println("filePath is "+filePath);
                   }    
                }
                else if(fileName !=null && fileName.endsWith(".xml"))
                {
                   filePath=uploadDirPath+"\\"+fileName;
                   uploadFile(uploadedFile, uploadDirPath, fileName);
                         
                   if(metadataType.equalsIgnoreCase("orgunit"))
                   mdmgr.processOrgUnit(filePath, softwareInstance);
                   else if(metadataType.equalsIgnoreCase("dataelement"))
                   mdmgr.processDataElement(filePath, softwareInstance);
                   if(metadataType.equalsIgnoreCase("catcombo"))
                   mdmgr.processCatCombo(filePath, softwareInstance);
                   System.err.println("metadataType tpe name is "+metadataType);
                       
                }
            }
            }
        }
        return mapping.findForward(SUCCESS);
    }
    private void uploadFile(FormFile uploadedFile,String filePath, String fileName)
    {
        try
        {
        File newFile = new File(filePath, fileName);
        if(!newFile.exists())
        {
          FileOutputStream fos = new FileOutputStream(newFile);
          fos.write(uploadedFile.getFileData());
          fos.flush();
          fos.close();
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
