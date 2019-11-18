/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import ImportExport.MetaDataExportHandler;
import ImportExport.MetaDataImportHandler;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.ZipHandler;
import ImportExport.TrainingExportImportManager;
import com.fhi.kidmap.business.EligibilityCriteria;
import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.OvcSchool;
import com.fhi.kidmap.business.Partners;
import com.fhi.kidmap.business.ReferralDirectory;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.business.Wards;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.nomis.nomisutils.DateManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
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
 * @author Siaka
 */
public class MetadataMgtAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
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
        MetadataMgtForm mdmgtForm=(MetadataMgtForm)form;
        HttpSession session=request.getSession();
        DaoUtil util=new DaoUtil();
        String[] selectedMetadata=mdmgtForm.getMetadata();
        ZipHandler zipHandler=new ZipHandler();
        FormFile uploadedFile=mdmgtForm.getUpload();
        AppUtility appUtil=new AppUtility();
        InputStream inputStream=null;
        
        String requiredAction=mdmgtForm.getActionName();
        String param=request.getParameter("name");
        if(param !=null)
        requiredAction=param;
        System.err.println("requiredAction is "+requiredAction);
        String[] selectedStates=mdmgtForm.getSelectedStates();
        String[] selectedLgas=mdmgtForm.getSelectedLgas();
        String[] selectedCbos=mdmgtForm.getSelectedCBOs();
        String[] selectedWards=mdmgtForm.getSelectedWards();
        String[] selectedPartners=mdmgtForm.getSelectedPartners();
        String[] selectedVulnerabilityStatus=mdmgtForm.getSelectedVulnerabilityStatus();
        String[] selectedSchools=mdmgtForm.getSelectedSchools();
        String[] selectedFacilities=mdmgtForm.getSelectedFacilities();
        /*String stateCode=mdmgtForm.getState();
        String lgaCode=mdmgtForm.getLga();
        String cboCode=mdmgtForm.getCbo();
        String community=mdmgtForm.getCommunity();
        mdmgtForm.setStateList(util.getStateListFromHhe());*/
        
        loadMetaData(session);
        
        
        List emptyList=new ArrayList();
        //String[] params={stateCode,lgaCode,cboCode,community};
        if(requiredAction==null)
        {
            mdmgtForm.reset(mapping, request);
            //etOrganizationUnitLists(mdmgtForm);
            return mapping.findForward(SUCCESS); 
        }
        else if(requiredAction.equalsIgnoreCase("export"))
        {
            appUtil.deleteFiles(appUtil.getMetadataDirectoryPath());
            appUtil.createMetadataExportImportDirectories();
            
            MetaDataExportHandler mdeh=new MetaDataExportHandler();
            String msg=" ";
            String directoryPath=" ";
            String currentDateAndTime=DateManager.getDefaultCurrentDateAndTime();
            if(currentDateAndTime !=null)
            currentDateAndTime=currentDateAndTime.replaceAll(":", "_");
            String fileName="metadata_"+currentDateAndTime;
            if(selectedStates !=null && selectedStates.length>0)
            {
                List list=new ArrayList();
                for(int i=0; i<selectedStates.length; i++)
                {
                    States state=util.getStateDaoInstance().getStateByStateCode(selectedStates[i]);
                    list.add(state);
                }
                directoryPath=appUtil.getStateDirectoryPath();
                mdeh.createStateExportInXml(directoryPath, list);
            }
            if(selectedLgas !=null && selectedLgas.length>0)
            {
                List list=new ArrayList();
                for(int i=0; i<selectedLgas.length; i++)
                {
                    Lgas lga=util.getLgaDaoInstance().getLgaByCode(selectedLgas[i]);
                    list.add(lga);
                }
                directoryPath=appUtil.getLgaDirectoryPath();
                mdeh.createLgaExportInXml(directoryPath, list);
            }
            if(selectedCbos !=null && selectedCbos.length>0)
            {
                List list=new ArrayList();
                for(int i=0; i<selectedCbos.length; i++)
                {
                    OrganizationRecords cbo=util.getOrganizationRecordsDaoInstance().getOrganizationRecord(selectedCbos[i]);
                    list.add(cbo);
                }
                directoryPath=appUtil.getOrganizationRecordsDirectoryPath();
                mdeh.createCBOExportInXml(directoryPath, list);
            }
            if(selectedWards !=null && selectedWards.length>0)
            {
                List list=new ArrayList();
                for(int i=0; i<selectedWards.length; i++)
                {
                    Wards ward=util.getWardDaoInstance().getWards(selectedWards[i]);
                    list.add(ward);
                }
                directoryPath=appUtil.getWardsDirectoryPath();
                mdeh.createWardExportInXml(directoryPath, list);
            }
            if(selectedPartners !=null && selectedPartners.length>0)
            {
               List list=new ArrayList();
                for(int i=0; i<selectedPartners.length; i++)
                {
                    Partners partner=util.getPartnerDaoInstance().getPartner(selectedPartners[i]);
                    list.add(partner);
                } 
                directoryPath=appUtil.getPartnerDirectoryPath();
                mdeh.createPartnerExportInXml(directoryPath, list);
            }
            if(selectedVulnerabilityStatus !=null && selectedVulnerabilityStatus.length>0)
            {
                List list=new ArrayList();
                for(int i=0; i<selectedVulnerabilityStatus.length; i++)
                {
                    EligibilityCriteria elibilityStatus=util.getEligibilityCriteriaDaoInstance().getEligibilityCriteria(Integer.parseInt(selectedVulnerabilityStatus[i]));
                    list.add(elibilityStatus);
                }
                directoryPath=appUtil.getEligibilityCriteriaDirectoryPath();
                mdeh.createEligibilityCriteriaExportInXml(directoryPath, list);
            }
            if(selectedSchools !=null && selectedSchools.length>0)
            {
                List list=new ArrayList();
                for(int i=0; i<selectedSchools.length; i++)
                {
                    OvcSchool school=util.getOvcSchoolDaoInstance().getSchoolDetails(Integer.parseInt(selectedSchools[i]));
                    list.add(school);
                }
                directoryPath=appUtil.getSchoolDirectoryPath();
                mdeh.createSchoolExportInXml(directoryPath, list);
            }
            if(selectedFacilities !=null && selectedFacilities.length>0)
            {
                List list=new ArrayList();
                for(int i=0; i<selectedFacilities.length; i++)
                {
                    ReferralDirectory facility=util.getReferralDirectoryDaoInstance().getReferralDirectory(selectedFacilities[i]);
                    list.add(facility);
                }
                directoryPath=appUtil.getReferralDirectoryDirectoryPath();
                mdeh.createReferralDirectoryExportInXml(directoryPath, list);
            }
            List listOfFilesToBeZipped=appUtil.copyFilePathsIntoList(appUtil.getMetadataDirectoryPath());
            zipHandler.zipFile(listOfFilesToBeZipped, appUtil.getDbExportZipDirectory(), fileName);
                msg="Export file named "+fileName+" saved in "+appUtil.getDbExportZipDirectory();
                request.setAttribute("mtmsg", msg);
            
            mdmgtForm.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("fuPage"))
        {
          mdmgtForm.reset(mapping, request);
          return mapping.findForward("fileUploadPage");
        }
        else if(requiredAction.equalsIgnoreCase("import"))
        {
            appUtil.deleteFiles(appUtil.getMetadataDirectoryPath());
            if(uploadedFile !=null)
            {
                String destination=appUtil.getMetadataDirectoryPath();
                String filePath = appUtil.getImportFilePath();
                File folder = new File(filePath);
                if(!folder.exists())
                {
                    folder.mkdir();
                }
                String fileName = uploadedFile.getFileName();
                if(fileName !=null && fileName.endsWith(".zip") && fileName.indexOf("metadata") !=-1)
                {
                   uploadFile(uploadedFile, filePath, fileName);
                   zipHandler.unZipFile(appUtil.getImportFilePath()+"\\"+uploadedFile.getFileName(),destination);
                   MetaDataImportHandler mdih=new MetaDataImportHandler();
                   mdih.importCBORecordsFromXml();
                   mdih.importEligibilityCriteriaRecordsFromXml();
                   mdih.importLgaRecordsFromXml();
                   mdih.importParnerRecordsFromXml();
                   mdih.importReferralDirectoryRecordsFromXml();
                   mdih.importSchoolRecordsFromXml();
                   mdih.importStateRecordsFromXml();
                   mdih.importWardRecordsFromXml();
                }
                mdmgtForm.reset(mapping, request);
                return mapping.findForward("importExportPage");
            }
        }
        /*else if(requiredAction.equalsIgnoreCase("import"))
        {
            appUtil.deleteFiles(appUtil.getExportFilePath());
            if(uploadedFile !=null)
            {
                String destination=appUtil.getExportFilePath();
                String filePath = appUtil.getImportFilePath();
                File folder = new File(filePath);
                if(!folder.exists())
                {
                    folder.mkdir();
                }
                String fileName = uploadedFile.getFileName();

                if(fileName !=null && fileName.equals("Trainingmetadata.zip"))
                {
                    uploadFile(uploadedFile, filePath, fileName);
                    
                    zipHandler.unZipFile(appUtil.getImportFilePath()+"\\"+uploadedFile.getFileName(),destination);
                    TrainingExportImportManager teim=new TrainingExportImportManager();
                    //appUtil.createDirectories(teim.getTrainingXmlFolderPathFile());
                    teim.importTrainingCategory();
                    teim.importTrainingDesignation();
                    teim.importTrainingInformationSetup();
                    teim.importTrainingStatusSetup();
                }
                if(fileName !=null && fileName.endsWith(".zip") && fileName.indexOf("metadata") !=-1)
                {
                   uploadFile(uploadedFile, filePath, fileName);
                   zipHandler.unZipFile(appUtil.getImportFilePath()+"\\"+uploadedFile.getFileName(),destination);
                   MergeDatabase mdb=new MergeDatabase(null,fileName);
                   mdb.importReferralDirectory();
                   mdb.readOrganizationRecordsFromXml(null, session);
                   mdb.readWardsFromXml(null, session);
                }
                mdmgtForm.reset(mapping, request);
                return mapping.findForward("importExportPage");
            }
        }*/
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
    
    private void loadMetaData(HttpSession session)
    {
        try
        {
            DaoUtil util=new DaoUtil();
            List stateList=util.getStateDaoInstance().getStates();
            List lgaList=util.getLgaDaoInstance().getAllLgas();
            List cboList=util.getOrganizationRecordsDaoInstance().getOrganizationList();
            List wardList=util.getWardDaoInstance().getAllWards();
            List partnerList=util.getPartnerDaoInstance().getAllPartners();
            List schoolList=util.getOvcSchoolDaoInstance().getOvcSchools();;
            List facilityList=util.getReferralDirectoryDaoInstance().getAllReferralDirectories();
            List vulnerabilityStatusList=util.getEligibilityCriteriaDaoInstance().getEligibilityCriteria();
            session.setAttribute("metadataStateList", stateList);
            session.setAttribute("metadataLgaList", lgaList);
            session.setAttribute("metadataCBOList", cboList);
            session.setAttribute("metadataWardList", wardList);
            session.setAttribute("metadataPartnerList", partnerList);
            session.setAttribute("metadataSchoolList", schoolList);
            session.setAttribute("metadataFacilityList", facilityList);
            session.setAttribute("metadataVulnerabilityStatusList", vulnerabilityStatusList);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
