/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.Training;
import com.fhi.kidmap.business.TrainingCategory;
import com.fhi.kidmap.business.TrainingDesignation;
import com.fhi.kidmap.business.TrainingInformationSetup;
import com.fhi.kidmap.business.TrainingParticipant;
import com.fhi.kidmap.business.TrainingStatusSetup;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.OrganizationRecordsDao;
import com.fhi.kidmap.dao.OrganizationRecordsDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.dao.TrainingCategoryDao;
import com.fhi.kidmap.dao.TrainingCategoryDaoImpl;
import com.fhi.kidmap.dao.TrainingDao;
import com.fhi.kidmap.dao.TrainingDaoImpl;
import com.fhi.kidmap.dao.TrainingDesignationDao;
import com.fhi.kidmap.dao.TrainingDesignationDaoImpl;
import com.fhi.kidmap.dao.TrainingInformationSetupDao;
import com.fhi.kidmap.dao.TrainingInformationSetupDaoImpl;
import com.fhi.kidmap.dao.TrainingParticipantDao;
import com.fhi.kidmap.dao.TrainingParticipantDaoImpl;
import com.fhi.kidmap.dao.TrainingStatusSetupDao;
import com.fhi.kidmap.dao.TrainingStatusSetupDaoImpl;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Siaka
 */
public class TrainingReportAction extends org.apache.struts.action.Action {

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
        TrainingReportForm trf=(TrainingReportForm)form;
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();

        TrainingCategoryDao tcdao=new TrainingCategoryDaoImpl();
        TrainingParticipantDao tpdao=new TrainingParticipantDaoImpl();
        TrainingDao trndao=new TrainingDaoImpl();
        TrainingDesignationDao tddao=new TrainingDesignationDaoImpl();
        TrainingInformationSetupDao tisdao=new TrainingInformationSetupDaoImpl();
        TrainingStatusSetupDao tssdao=new TrainingStatusSetupDaoImpl();
        OrganizationRecordsDao ordao=new OrganizationRecordsDaoImpl();
        PartnersDao patdao=new PartnersDaoImpl();

        String organizationCode=trf.getOrganizationCode();
        String categoryId=trf.getCategory();
        String partnerCode=trf.getPartnerCode();
        String designation=trf.getDesignation();
        String trainingName=trf.getNameOfTraining();
        String startDate=appUtil.processMthDayYearToMysqlFormat(trf.getStartDate());
        String endDate=appUtil.processMthDayYearToMysqlFormat(trf.getEndDate());
        String requiredAction=trf.getActionName();



        List trainingCategoryList=tcdao.getAllTrainingCategories();
        List orgCodeList=tpdao.getDistinctOrganizationRecordsFromTraining();
        List organizationList=new ArrayList();
        List trainingDesignationList=new ArrayList();
        List trainingInfoList=tisdao.getAllTrainingInformationSetups();
        trainingDesignationList=tddao.getTrainingDesignations(categoryId);
        trf.setTrainingDesignationList(trainingDesignationList);

        String orgCode=null;
        OrganizationRecords orgRecords=null;
        if(orgCodeList !=null)
        {
            for(int i=0; i<orgCodeList.size(); i++)
            {
                orgCode=(String)orgCodeList.get(i);
                orgRecords=ordao.getOrganizationRecord(orgCode);
                if(orgRecords !=null)
                {
                    organizationList.add(orgRecords);
                }
            }
        }
        List partnerList=patdao.getAllPartners();

        trf.setOrganizationList(organizationList);
        trf.setPartnerList(partnerList);
        trf.setTrainingCategoryList(trainingCategoryList);
        trf.setTrainingInfoList(trainingInfoList);
        
        if(requiredAction==null)
        {
            trf.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("designation"))
        {
            trf.setActionName(null);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("report"))
        {
            HttpSession session=request.getSession();
            TrainingParticipant tp=null;
            Training trn=null;
            
            
            String[] params={organizationCode,categoryId,designation,trainingName,startDate,endDate,partnerCode};
            List list=trndao.getAllTrainingReports(params);//trndao.getTrainingReport(params);
            List trainingList=new ArrayList();
            if(list !=null && !list.isEmpty())
            {
                for(int i=0; i<list.size(); i++)
                {
                    trn=(Training)list.get(i);
                    tp=tpdao.getTrainingParticipant(trn.getParticipantId());
                    TrainingStatusSetup ts=tssdao.getTrainingStatusSetup(trn.getTrainingStatus());
                    TrainingInformationSetup tis=tisdao.getTrainingInformationSetup(trn.getTrainingId());
                    TrainingCategory tc=tcdao.getTrainingCategory(trn.getCategory());
                    if(tp !=null)
                    {
                        trn.setParticipantName(tp.getParticipantName());
                        trn.setOrganizationCode(util.getOrganizationName(tp.getOrganizationCode()));
                        TrainingDesignation td=tddao.getTrainingDesignation(tp.getDesignation());
                        if(td !=null)
                        trn.setDesignation(td.getDesignationName());
                    }
                    if(tis !=null)
                    {
                        trn.setTrainingName(tis.getTrainingName());
                    }
                    if(ts !=null)
                    trn.setTrainingStatus(ts.getTrainingStatusName());
                    
                    if(tc !=null)
                    trn.setCategory(tc.getCategoryName());
                    trainingList.add(trn);
                }
            }
            String title="Organization: "+util.getOrganizationName(organizationCode)+"    ";
            if((startDate !=null && !startDate.equalsIgnoreCase("All") && !startDate.equalsIgnoreCase("") && !startDate.equalsIgnoreCase(" ")) && (endDate !=null && !endDate.equalsIgnoreCase("All") && !endDate.equalsIgnoreCase("") && !endDate.equalsIgnoreCase(" ")))
            title+="              Period: "+appUtil.getDisplayDate(startDate)+" to "+appUtil.getDisplayDate(endDate);
            session.setAttribute("trainingTitle",title);
            session.setAttribute("trainingList", trainingList);
            System.err.println("trainingList "+trainingList.size());
            //trf.setTrainingList(trainingList);
            return mapping.findForward("report");
        }
        trf.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
}
