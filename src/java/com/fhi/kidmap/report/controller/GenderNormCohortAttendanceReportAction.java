/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.GenderNormCohortAttendanceDao;
import com.fhi.kidmap.dao.GenderNormCohortAttendanceDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.AppUtility;
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
public class GenderNormCohortAttendanceReportAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String PARAMPAGE = "paramPage";

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
        GenderNormCohortAttendanceReportForm gncrf=(GenderNormCohortAttendanceReportForm)form;
        PartnersDao pdao=new PartnersDaoImpl();
        
        AppUtility appUtil=new AppUtility();
        HttpSession session=request.getSession();
        User user=appUtil.getUser(session);
        AccessManager acm=new AccessManager();
        acm.setStateListForReports(session);
        List lgaList=new ArrayList();
        List wardList=new ArrayList();
        List emptyList=new ArrayList();
        
        String requiredAction=gncrf.getActionName();
        String stateCode=gncrf.getStateCode();
        String lgaCode=gncrf.getLgaCode();
        String cboCode=gncrf.getCboCode();
        String communityCode=gncrf.getCommunityCode();
        String partnerCode=gncrf.getPartnerCode();
        String startMth=gncrf.getStartMth();
        String startYear=gncrf.getStartYear();
        String endMth=gncrf.getEndMth();
        String endYear=gncrf.getEndYear();
        List partnerList=pdao.getAllPartners();
        session.setAttribute("partnerList", partnerList);
        if(requiredAction==null)
        {
            session.setAttribute("gncalgaList", emptyList);
            session.setAttribute("gncaorgList", emptyList);
            session.setAttribute("gncawardList", emptyList);
            gncrf.reset(mapping, request);
            return mapping.findForward(PARAMPAGE);
        }
        if(requiredAction.equals("lga"))
        {
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
            session.setAttribute("gncalgaList", lgaList);
            session.setAttribute("gncaorgList", emptyList);
            session.setAttribute("gncawardList", emptyList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =new ArrayList();
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("gncaorgList", orgList);
            session.setAttribute("gncawardList", emptyList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("ward"))
        {
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,cboCode);
            session.setAttribute("gncawardList", wardList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("report"))
        {
            GenderNormCohortAttendanceDao gncadao=new GenderNormCohortAttendanceDaoImpl();
            
            DateManager dm=new DateManager();
            DaoUtil util=new DaoUtil();
            String[] params={stateCode,lgaCode,cboCode,communityCode,util.getStateName(stateCode),util.getLgaName(lgaCode),util.getOrganizationName(cboCode),util.getWardName(communityCode)};
            String orgUnitQuery=util.getOrgQueryCriteria(params);
            String startDate=dm.getStartDate(Integer.parseInt(startMth), Integer.parseInt(startYear));
            String endDate=dm.getEndDate(Integer.parseInt(endMth), Integer.parseInt(endYear));
            
            List list=gncadao.getListOfGenderNormCohortAttendance(orgUnitQuery, startDate, endDate,"asc");
            session.setAttribute("gncaList", list);
            session.setAttribute("gncaparams", params);
            return mapping.findForward(SUCCESS);
            
        }
        return mapping.findForward(PARAMPAGE);
    }
}
