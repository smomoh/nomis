/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import ImportExport.DataImportFileUploadManager;
import com.fhi.kidmap.business.Partners;
import com.fhi.kidmap.business.User;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.ZipHandler;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.nomis.logs.NomisLogManager;
import com.fhi.nomis.nomisutils.DatabaseUtilities;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author smomoh
 */
public class DatabaseUploadAction extends org.apache.struts.action.Action {

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
        DatabaseUploadForm duf=(DatabaseUploadForm)form;
        ZipHandler zipHandler=new ZipHandler();
        DaoUtil util=new DaoUtil();
        AppUtility appUtil=new AppUtility();
        HttpSession session=request.getSession();
        String requiredAction=duf.getActionName();
        FormFile uploadedFile=duf.getUploadedFile();
        
        String actionName=request.getParameter("actionName");
        String currentUser=appUtil.getCurrentUser(session);
        String currentDate=appUtil.getCurrentDate();
        String partnerCode=duf.getPartnerCode();
        String syncRecords=duf.getSyncRecord();
        String hivBirthRegUpdate="off";//duf.getHivBirthRegUpdate();
        String importHivOnly=duf.getImportHivOnly();
        String[] selectedTables=duf.getTableNames();
        int loadFromImport=duf.getLoadFromImport();

        if(currentUser==null)
        return mapping.findForward("login");
        
        User user=util.getUserDaoInstance().getUser(currentUser);
        setPartnerList(user,session);
        DatabaseUtilities dbUtils=new DatabaseUtilities();
        session.setAttribute("tableList", dbUtils.getTableList());
        //check to be sure that the jsp is the same jsp specified as input in the struts config.
        if(requiredAction==null && actionName !=null)
        {
            requiredAction=actionName;
            System.err.println("actionName is "+actionName);
        }

        if(requiredAction==null)
        {
            duf.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("upload"))
        {
            String uploadMsg="Unable to upload file";
            String filePath = appUtil.getImportFilePath();
            if(loadFromImport==1)
            {
                saveAllFilesInImportFolder(filePath,selectedTables,currentUser,importHivOnly,syncRecords,loadFromImport);
            }
            if(uploadedFile !=null)
            {
               appUtil.createExportImportDirectories();
               //String filePath = appUtil.getImportFilePath();
                File folder = new File(filePath);
                if(!folder.exists())
                {
                    folder.mkdir();
                }
                InputStream is=uploadedFile.getInputStream();
                String fileName=uploadedFile.getFileName();
                String addedName="_"+currentUser+"_"+currentDate+".zip";
                
                
                if(fileName !=null && fileName.endsWith(".zip"))
                {
                    fileName=fileName.replace(".zip", addedName);
                    //System.err.println("file name is "+fileName);
                    File targetFile = new File(appUtil.getImportFilePath()+"\\"+fileName);
                    OutputStream outStream = new FileOutputStream(targetFile);

                    byte[] buffer = new byte[8 * 1024];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1)
                    {
                        outStream.write(buffer, 0, bytesRead);
                    }
                    IOUtils.closeQuietly(is);
                    IOUtils.closeQuietly(outStream);
                    File file = new File(appUtil.getImportFilePath()+appUtil.getFileSeperator()+fileName);
                    
                    if(file.exists())
                    {
                        DataImportFileUploadManager difum=new DataImportFileUploadManager();
                        difum.setDateImportCompleted(DateManager.getDateInstance("1900-01-01"));
                        difum.setDateOfUpload(DateManager.getDateInstance(currentDate));
                        difum.setImportFileName(fileName);
                        difum.setImportStatus(0);
                        difum.setPartnerCode(partnerCode);
                        difum.setTimeImportCompleted(DateManager.getDefaultCurrentDateAndTime());
                        difum.setUserName(currentUser);
                        difum.setImportFilePath(filePath);
                        difum.setSelectedTableCodes(appUtil.concatStr(selectedTables, null));
                        difum.setImportOptions(importHivOnly+","+syncRecords);
                        util.getDataImportUploadManagerDaoInstance().saveDataImportFileUploadManager(difum);
                        uploadMsg="Your file has been uploaded. You will be notified as soon as it is processed. Thank you";
                    }
                    if(importHivOnly !=null && importHivOnly.equalsIgnoreCase("on"))
                    AppUtility.importHivOnly=true;
                                        
                    //saveData(request,syncRecords,hivBirthRegUpdate);
                    request.setAttribute("dbImportMsg",uploadMsg);
                    session.setAttribute("syncRecords",syncRecords);
                }  
                //Return to jsp page which will then call an ajax function to commence the import process
                return mapping.findForward("dbImportSuccessMsg");
            }
        }
        return mapping.findForward(SUCCESS);
    }
    private void setPartnerList(User user,HttpSession session)
    {
        String partnerCodes=null;
        DaoUtil util=new DaoUtil();
        try
        {
            if(user !=null)
            {
                List partnerList=new ArrayList();
                if(user.isSuperUser())
                {
                   partnerList=util.getPartnerDaoInstance().getAllPartners(); 
                }
                else
                {
                    partnerCodes=user.getPartnerCodes();
                    if(partnerCodes !=null)
                    {
                        String[] partnerCodeArray=partnerCodes.split(",");
                        if(partnerCodeArray !=null)
                        {
                            //List list=new ArrayList();
                            Partners partner=null;
                            for(int i=0; i<partnerCodeArray.length; i++)
                            {
                                partner=util.getPartnerDaoInstance().getPartner(partnerCodeArray[i]);
                                partnerList.add(partner);
                            }
                            //session.setAttribute("userAssignedPartners", list);
                        }
                    }
                }
                session.setAttribute("userAssignedPartners", partnerList);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
public void saveAllFilesInImportFolder(String filePath,String[] selectedTables,String currentUser,String importHivOnly,String syncRecords,int loadFromImport)
{
    try
    {
    AppUtility appUtil=new AppUtility();
    DaoUtil util=new DaoUtil();
    if(loadFromImport==1)
    {
        String[] uploadedFileList=appUtil.listFiles(appUtil.getImportFilePath());
        if(uploadedFileList !=null && uploadedFileList.length>0)
        {
            String fileName=null;
            String currentDate=appUtil.getCurrentDate();
            for(int i=0; i<uploadedFileList.length; i++)
            {
                fileName=uploadedFileList[i];
                if(fileName !=null && fileName.indexOf(".zip") !=-1)
                {
                    //fileName=fileName.replace(".zip", "rpt.zip");
                    DataImportFileUploadManager difum=new DataImportFileUploadManager();
                    difum.setDateImportCompleted(DateManager.getDateInstance("1900-01-01"));
                    difum.setDateOfUpload(DateManager.getDateInstance(currentDate));
                    difum.setImportFileName(fileName);
                    difum.setImportStatus(0);
                    difum.setPartnerCode(NomisConstant.DEAULT_PARTNER_CODE);
                    difum.setTimeImportCompleted(DateManager.getDefaultCurrentDateAndTime());
                    difum.setUserName(currentUser);
                    difum.setImportFilePath(filePath);
                    difum.setSelectedTableCodes(appUtil.concatStr(selectedTables, null));
                    difum.setImportOptions(importHivOnly+","+syncRecords);
                    util.getDataImportUploadManagerDaoInstance().saveDataImportFileUploadManager(difum);
                }
            }
        }
    }
    }
    catch(Exception ex)
    {
        NomisLogManager.logStackTrace(ex);
    }
}
}
