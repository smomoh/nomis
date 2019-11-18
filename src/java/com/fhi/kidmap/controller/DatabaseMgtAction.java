/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

/*import com.fhi.kidmap.business.AjaxProcessor;
import com.fhi.kidmap.business.AppUtility;
import com.fhi.kidmap.business.DataElementImporter;
import com.fhi.kidmap.business.DataExport;
import ImportExport.DatabaseExport;
import com.fhi.kidmap.business.LoadUpInfo;*/
import ImportExport.MergeDatabase;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.nomis.nomisutils.AccessManager;
import ImportExport.NutritionAssessmentExportImportManager;
import com.fhi.nomis.nomisutils.AjaxProcessor;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DataElementImporter;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author COMPAQ USER
 */
public class DatabaseMgtAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String DATASIZE = "dataSize";
    private static final String ZIPFILE = "zipfile";
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
            throws Exception {

        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        
        //if(!appUtil.hasPriviledgeToAccessPage(session))
        //return mapping.findForward("Nouserpriviledge");

        DatabaseMgtBean dbmb=(DatabaseMgtBean)form;
        String requiredAction=dbmb.getDbActionName();
        AjaxProcessor aproc=new AjaxProcessor();
        

        String stateCode=dbmb.getState();
        String lgaCode=dbmb.getLga();
        String cboCode=dbmb.getCbo();
        String partnerCode=dbmb.getPartnerCode();
        boolean isEntirePeriod=dbmb.isEntirePeriod();
        String month1=dbmb.getMonth1();
        String month2=dbmb.getMonth2();
        String year1=dbmb.getYear1();
        String year2=dbmb.getYear2();
        FormFile uploadedFile=dbmb.getUploadedFile();
        DaoUtil util=new DaoUtil();

        
        //System.err.println("isEntirePeriod is "+isEntirePeriod);
        //session.removeAttribute("dataElementFileUploaded");
        String fileUploadedAttribute=(String)session.getAttribute("fileUploaded");
        if(fileUploadedAttribute !=null)
        requiredAction=fileUploadedAttribute;
        else
        {
            fileUploadedAttribute=(String)session.getAttribute("dataElementFileUploaded");
            if(fileUploadedAttribute !=null)
            requiredAction=fileUploadedAttribute;
        }


        if(requiredAction==null)
        {
            //appUtil.createExportImportDirectories();
            if(dbmb.getActionName() !=null && dbmb.getActionName().equalsIgnoreCase("selectionPage"))
            {
                ActionErrors errors = new ActionErrors();
                errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.fileTransferActivity.required"));
                saveErrors(request, errors);
                //return mapping.findForward(FAILURE);
            }
            return mapping.findForward("selectionPage");
        }
        
        else if(requiredAction.equalsIgnoreCase("dbexportSelected"))
        {
            AccessManager acm=new AccessManager();
            boolean userInRole=acm.isUserInRole("005",appUtil.getCurrentUser(session));
            if(!userInRole)
            {
                request.setAttribute("errorMsg", appUtil.getAccessDeniedMessage());
                return mapping.findForward("accessDenied");
            }
            LoadUpInfo loadup=new LoadUpInfo();
            loadup.getAllStatesForExport(session);
            //loadup.getAllStates(session);
            PartnersDao pdao=new PartnersDaoImpl();
            List partnerList=pdao.getAllPartners();
            session.setAttribute("dbExportLgaList", new ArrayList());
            session.setAttribute("dbExportOrgList",new ArrayList());
            session.setAttribute("partnerList", partnerList);
            return mapping.findForward("dbexportSelected");
        }
        else if(requiredAction.equalsIgnoreCase("lga"))
        {
            List lgaList =util.getOrganizationalUnits(requiredAction,stateCode,lgaCode,cboCode,null);
            session.setAttribute("dbExportLgaList", lgaList);
            session.setAttribute("dbExportOrgList",new ArrayList());
            return mapping.findForward("dbexportSelected");
        }
        else if(requiredAction.equalsIgnoreCase("cbo"))
        {
            OrganizationRecords orgRecords=null;
            List orgList=new ArrayList();
            List cboList =util.getOrganizationalUnits(requiredAction,stateCode,lgaCode,cboCode,null);
            for(Object org:cboList)
            {
                orgRecords=util.getOrganizationRecords(org.toString());
                orgList.add(orgRecords);
            }
            session.setAttribute("dbExportOrgList", orgList);
            return mapping.findForward("dbexportSelected");
        }
        else if(requiredAction.equalsIgnoreCase("fileUploadSelected"))
        {
            AccessManager acm=new AccessManager();
            boolean userInRole=acm.isUserInRole("006",appUtil.getCurrentUser(session));
            if(!userInRole)
            {
                request.setAttribute("errorMsg", appUtil.getAccessDeniedMessage());
                return mapping.findForward("accessDenied");
            }
            return mapping.findForward("fileUploadSelected");
        }

        else if(requiredAction.equalsIgnoreCase("dataElementImportSelected"))
        {
            return mapping.findForward("dataElementImportSelected");
        }
        else if(requiredAction.equalsIgnoreCase("deFileUploaded"))
        {
            session.removeAttribute("dataElementFileUploaded");
            //request.setAttribute("noOfDataElements", "No of data elements imported");
            return mapping.findForward("deFileUploaded");
        }
        else if(requiredAction.equalsIgnoreCase("importDataElements"))
        {
            String fileName=(String)session.getAttribute("fileName");
            session.removeAttribute("fileName");
            DataElementImporter deImporter=new DataElementImporter();
            boolean deimported=deImporter.importDhisDataElements(fileName);
            if(!deimported)
            {
                request.setAttribute("errorMsg", "Failed to import data elements");
                return mapping.findForward("dataElementImportFailed");
            }
            request.setAttribute("successMsg", "Data elements imported");
            return mapping.findForward("dataElementImported");
        }
        else if(requiredAction.equalsIgnoreCase("dbImportSelected"))
        {
            AccessManager acm=new AccessManager();
            boolean userInRole=acm.isUserInRole("006",appUtil.getCurrentUser(session));
            if(!userInRole)
            {
                request.setAttribute("errorMsg", appUtil.getAccessDeniedMessage());
                return mapping.findForward("accessDenied");
            }
            String[] files=appUtil.listFiles(appUtil.getImportFilePath());
            List list=new ArrayList();
            List fileList=null;
            //appUtil.createExportImportDirectories();
            if(files !=null)
            {
                for(int i=0; i<files.length; i++)
                {
                    if(files[i].endsWith(".zip"))
                    {
                        fileList=new ArrayList();
                        fileList.add(files[i]);
                        fileList.add(files[i].substring(0, files[i].indexOf(".zip")));
                        list.add(fileList);
                    }
                }
            }
            request.setAttribute("availableFileList", list);
            request.getSession().removeAttribute("fileUploaded");
            return mapping.findForward("dbImportSelected");
        }
        
        else if(requiredAction.equalsIgnoreCase("dbexport"))
        {
            if(isEntirePeriod)
            {
                month1="All";
                year1="All";
                month2="All";
                year2="All";
            }
            String partnerName=util.getPartnerName(partnerCode);
            String[] params={month1,year1,month2,year2,stateCode,lgaCode,cboCode,partnerName,partnerCode};
            String[] orgParams={params[4],params[5],params[6],null,null,null,null,null,null,null,null,null,null,params[7],params[8]};
            String additionalOrgQuery=util.getOrgQueryCriteria(orgParams);
            
            /*int[] values={aproc.getEnrollmentSize(session,params),aproc.getCsiDataSize(session,params),aproc.getServiceDataSize(session,params),aproc.getOrganizationRecordsDataSize(session,params),aproc.getWardRecordsDataSize(session,params),aproc.getOrganizationalAssessmentDataSize(session,params),aproc.getFollowupSurveyRecordsDataSize(session,params),aproc.getReferralRecordsDataSize(session, params),aproc.getSchoolRecordsDataSize(session,params),aproc.getHouseholdEnrollmentDataSize(session,params),aproc.getHouseholdServiceDataSize(session, params),aproc.getHouseholdVulnerabilityAssessmentDataSize(session, params),aproc.getCaregiverDataSize(session, params),aproc.getNutritionAssessmentDataSize(session, params)};
            appUtil.deleteFiles(appUtil.getExportFilePath());
            request.setAttribute("datasize", values);
            session.setAttribute("exportPeriod", params);*/
            return mapping.findForward(DATASIZE);
        }
        else if(requiredAction.equalsIgnoreCase("dbimport"))
        {
            String fileName=dbmb.getImportFileName();
            appUtil.deleteFiles(appUtil.getExportFilePath());
            MergeDatabase mdb=new MergeDatabase(null,fileName);
            String btnDisabled="disabled=disabled";
                List savableCountList=new ArrayList();
                List savableList=new ArrayList();
                List backUpCount=new ArrayList();

                List savableServiceList=new ArrayList();
                List backUpServiceCount=new ArrayList();

                mdb.readOvcFromXml(session,true,true);
                mdb.readHouseholdVulnebilityIndexFromXml(session);
                //mdb.readHouseholdEnrollmentFromXml(session);
                mdb.readCaregiverFromXml(session);
                mdb.readHouseholdVulnebilityAssessmentFromXml(session);
                //mdb.readCsiScoreFromXml(session);
                mdb.readServiceRecordsFromXml(session);
                //mdb.readFollowupSurveyRecordsFromXml(session);
                mdb.readOrganizationRecordsFromXml(null,session);
                mdb.readWardsFromXml(null,session);
                mdb.readOrganizationalAssessmentFromXml(session);
                //mdb.readSchoolRecordsFromXml(session);
                mdb.readReferralRecordsFromXml(session);
                
                mdb.readHouseholdServiceFromXml(session);
                NutritionAssessmentExportImportManager.readNutritionAssessmentFromXml(session);
                savableList=(List)session.getAttribute("savableOvcList");
                backUpCount=(List)session.getAttribute("backUpCountList");
                savableServiceList=(List)session.getAttribute("serviceSavableList");
                backUpServiceCount=(List)session.getAttribute("backUpServiceCountList");

                savableCountList.add("Number of enrollment records ready to be saved");
                savableCountList.add("Number of service records ready to be saved");
                
                btnDisabled=" ";
                session.setAttribute("savableCountList", savableCountList);
                session.setAttribute("backUpCount", backUpCount);
                session.setAttribute("backUpServiceCount", backUpServiceCount);
                session.setAttribute("btnDisabled", btnDisabled);
        }
        return mapping.findForward(SUCCESS);
    }
}
