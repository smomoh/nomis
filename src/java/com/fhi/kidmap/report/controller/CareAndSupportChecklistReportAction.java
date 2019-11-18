/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.CareAndSupportDao;
import com.fhi.kidmap.dao.CareAndSupportDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
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
public class CareAndSupportChecklistReportAction extends org.apache.struts.action.Action {

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
            throws Exception 
    {
        CareAndSupportReportForm candsform=(CareAndSupportReportForm)form;
        PartnersDao pdao=new PartnersDaoImpl();
        
        AppUtility appUtil=new AppUtility();
        HttpSession session=request.getSession();
        User user=appUtil.getUser(session);
        AccessManager acm=new AccessManager();
        acm.setStateListForReports(session);
        List lgaList=new ArrayList();
        List wardList=new ArrayList();
        List emptyList=new ArrayList();
        
        String requiredAction=candsform.getActionName();
        String stateCode=candsform.getStateCode();
        String lgaCode=candsform.getLgaCode();
        String cboCode=candsform.getCboCode();
        String communityCode=candsform.getCommunityCode();
        String partnerCode=candsform.getPartnerCode();
        String startMth=candsform.getStartMth();
        String startYear=candsform.getStartYear();
        String endMth=candsform.getEndMth();
        String endYear=candsform.getEndYear();
        List partnerList=pdao.getAllPartners();
        session.setAttribute("partnerList", partnerList);
        if(requiredAction==null)
        {
            session.setAttribute("candslgaList", emptyList);
            session.setAttribute("candsorgList", emptyList);
            session.setAttribute("candswardList", emptyList);
            candsform.reset(mapping, request);
            return mapping.findForward(PARAMPAGE);
        }
        if(requiredAction.equals("lga"))
        {
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
            session.setAttribute("candslgaList", lgaList);
            session.setAttribute("candsorgList", emptyList);
            session.setAttribute("candswardList", emptyList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =new ArrayList();
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("candsorgList", orgList);
            session.setAttribute("candswardList", emptyList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("ward"))
        {
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,cboCode);
            session.setAttribute("candswardList", wardList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("report"))
        {
            CareAndSupportDao candsdao=new CareAndSupportDaoImpl();
            
            DateManager dm=new DateManager();
            DaoUtil util=new DaoUtil();
            String[] params={stateCode,lgaCode,cboCode,communityCode,util.getStateName(stateCode),util.getLgaName(lgaCode),util.getOrganizationName(cboCode),util.getWardName(communityCode)};
            String orgUnitQuery=util.getOrgQueryCriteria(params);
            String startDate=dm.getStartDate(Integer.parseInt(startMth), Integer.parseInt(startYear));
            String endDate=dm.getEndDate(Integer.parseInt(endMth), Integer.parseInt(endYear));
            
            List list=candsdao.getCareAndSupportChecklistByPeriod(orgUnitQuery, startDate, endDate,"asc");
            System.err.println("candsList size is "+list.size());
            session.setAttribute("candsList", list);
            session.setAttribute("candsparams", params);
            candsform.reset(mapping, request);
            return mapping.findForward(SUCCESS);
            
        }
        return mapping.findForward(SUCCESS);
    }
}
