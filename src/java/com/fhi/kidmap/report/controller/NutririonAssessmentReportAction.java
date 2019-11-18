/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.NutritionAssessment;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.NutritionAssessmentDao;
import com.fhi.kidmap.dao.NutritionAssessmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.OvcRecords;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.DateManager;
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
 * @author smomoh
 */
public class NutririonAssessmentReportAction extends org.apache.struts.action.Action {
    
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
            throws Exception {
        NutritionAssessmentReportForm reportForm=(NutritionAssessmentReportForm)form;
        NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
        OvcRecords records=new OvcRecords();
        //LgaDao dao = new LgaDaoImpl();
        PartnersDao pdao=new PartnersDaoImpl();
        HttpSession session=request.getSession();
        DaoUtil util=new DaoUtil();
        AppUtility appUtil=new AppUtility();
        AccessManager acm=appUtil.getAccessManager();
        acm.setStateListForReports(session);
        User user=appUtil.getUser(session);
        List lgaList=new ArrayList();
        
        List wardList=new ArrayList();
        List paramList=new ArrayList();
        String requiredAction;

        String stateCode=reportForm.getState();
        String lgaCode=reportForm.getLga();
        String cboCode=reportForm.getCbo();
        String wardCode=reportForm.getWard();
        String partnerCode=reportForm.getPartnerCode();
        int fmonth=reportForm.getEnroll_month();
        int fyear=reportForm.getEnroll_year();
        int smonth=reportForm.getEnroll_month2();
        int syear=reportForm.getEnroll_year2();
        int startAge=reportForm.getEnroll_startAge();
        int endAge=reportForm.getEnroll_endAge();
        int assessmentNo=reportForm.getAssessmentNo();
        requiredAction=reportForm.getActionName();
        String requestName=request.getParameter("name");
        if(requestName !=null)
        requiredAction=requestName;
        
        //session.removeAttribute("ovcRecords");
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getAllStates(session);
        loadup.setParamAttributes(request);
        List emptyList=new ArrayList();
        List partnerList=pdao.getAllPartners();
        session.setAttribute("partnerList", partnerList);
        List assessmentNoList=nadao.getDistinctAssessmentNo();
        request.setAttribute("nassessmentNoList", assessmentNoList);
        List yearList=DateManager.generateYears();
        session.setAttribute("yearList", yearList);
        //System.err.println("requiredAction is "+requiredAction);
        if(requiredAction==null)
        {
            session.setAttribute("lgaList", emptyList);
            session.setAttribute("orgList", emptyList);
            session.setAttribute("wardList", emptyList);
            return mapping.findForward(SUCCESS);
        }
        if(requiredAction.equals("lga"))
        {
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
            session.setAttribute("lgaList", lgaList);
            session.setAttribute("orgList", emptyList);
            session.setAttribute("wardList", emptyList);
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =new ArrayList();
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("orgList", orgList);
            session.setAttribute("wardList", emptyList);
        }
        else if(requiredAction.equals("ward"))
        {
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,cboCode);
            session.setAttribute("wardList", wardList);
        }
        else if(requiredAction.equals("ovcList"))
        {
            paramList.add(stateCode);
            paramList.add(lgaCode);
            paramList.add(cboCode);
            paramList.add(wardCode);
            paramList.add(fmonth);
            paramList.add(fyear);
            paramList.add(smonth);
            paramList.add(syear);
            paramList.add(startAge);
            paramList.add(endAge);
            paramList.add(partnerCode);
            paramList.add(assessmentNo);
            records.getNutritionalAssessmentRecords(session, paramList);
            reportForm.reset(mapping, request);
            return mapping.findForward("naReport");
        }
        else if(requiredAction.equals("details"))
        {
            List ovcList=new ArrayList();
            String requestId=request.getParameter("id");
            //System.err.println("requestId is "+requestId);
            int id=Integer.parseInt(requestId);
            OvcDao ovcDao=new OvcDaoImpl();
            NutritionAssessment na=nadao.getNutritionAssessment(id);
            Ovc ovc=ovcDao.getOvc(na.getOvcId());
            ovcList.add(ovc);
            //List ovcListWithCaregiverInfo=util.getOvcWithCaregiverInfo(ovcList);
            
            List encryptedOvcList=appUtil.scrambleIdentifiers(ovcList,session);
            if(encryptedOvcList !=null && !encryptedOvcList.isEmpty())
            {
                Ovc ovc2=(Ovc)encryptedOvcList.get(0);
                na.setAgeUnit(ovc2.getAgeUnit());
                na.setOvcAge(ovc2.getCurrentAge());
                na.setOvcFname(ovc2.getFirstName());
                na.setOvcSurname(ovc2.getLastName());
                na.setOvcGender(ovc2.getGender());
            }
            session.setAttribute("nutritionAssessmentRecord", na);
            return mapping.findForward("nutritionAssessmentDetails");
        }
        
        return mapping.findForward(SUCCESS);
    }
}
