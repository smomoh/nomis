/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.SiteTransition;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.dao.SiteTransitionDao;
import com.fhi.kidmap.dao.SiteTransitionDaoImpl;
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.LoadUpInfo;
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
public class SiteTransitionAction extends org.apache.struts.action.Action {

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
        SiteTransitionForm stform=(SiteTransitionForm)form;
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        //DaoUtil util=new DaoUtil();
        PartnersDao pdao=new PartnersDaoImpl();
        
        String stateCode=stform.getStateCode();
        String lgaCode=stform.getLgaCode();
        String orgCode=stform.getOrgCode();
        String communityCode=stform.getCommunityCode();
        String organizationSiteTransitionedTo=stform.getOrganizationSiteTransitionedTo();
        //String partnerCode=stform.getPartnerCode();
        
        AccessManager acm=new AccessManager();
        boolean userInRole=acm.isUserInRole("bdelete",appUtil.getCurrentUser(session));
        if(!userInRole)
        {
            request.setAttribute("errorMsg", appUtil.getAccessDeniedMessage());
            return mapping.findForward("accessDenied");
        }
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getAllStates(session);
        loadup.setParamAttributes(request);
        List partnerList=pdao.getAllPartners();
       session.setAttribute("partnerList", partnerList);
       if(session.getAttribute("stwardList")==null)
       session.setAttribute("stwardList", new ArrayList());
       String msg=" ";
       request.setAttribute("stmsg", msg);
        String requiredAction=stform.getActionName();
        if(requiredAction==null)
        {
            session.setAttribute("stlgaList", new ArrayList());
            session.setAttribute("orgList", new ArrayList());
            session.setAttribute("stwardList", new ArrayList());
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("lga"))
        {
            List lgaList=generateOrgUnitParametersList(requiredAction,stateCode,lgaCode,orgCode,request);
            session.setAttribute("stlgaList", lgaList);
            session.setAttribute("orgList", new ArrayList());
            session.setAttribute("stwardList", new ArrayList());
            //generateLgaList(stateCode,request);
            
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList=generateOrgUnitParametersList(requiredAction,stateCode,lgaCode,orgCode,request);
            
            session.setAttribute("orgList", orgList);
            session.setAttribute("stwardList", new ArrayList());
        }
        else if(requiredAction.equals("ward"))
        {
            List wardList =generateOrgUnitParametersList(requiredAction,stateCode,lgaCode,orgCode,request);
            
            session.setAttribute("stwardList", wardList);
        }
        else if(requiredAction.equals("transitionSite"))
        {
            msg=" ";
            //System.err.println("Inside transitionSite block; Community code is "+communityCode);
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            
            //AppUtility appUtil=new AppUtility();
            String dateOfTransition=appUtil.processMthDayYearToMysqlFormat(stform.getDateOfTransition());
            List hheList=hhedao.getListOfHouseholdEnrollmentByCommunityCode(communityCode);
            if(hheList !=null)
            {
                try
                {
                    HouseholdEnrollment hhe=null;
                    OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
                    for(int i=0; i<hheList.size(); i++)
                    {

                        hhe=(HouseholdEnrollment)hheList.get(i);
                        wdao.withdrawHousehold(hhe.getHhUniqueId(), dateOfTransition,"transfer", "household", 1);
                        msg=i+" Households transitioned ";
                    }
                    
                    SiteTransition st=new SiteTransition();
                    SiteTransitionDao stdao=new SiteTransitionDaoImpl();
                    st.setDateModified(appUtil.getCurrentDate());
                    st.setDateOfTransition(dateOfTransition);
                    st.setOrganizationSiteTransitionedTo(organizationSiteTransitionedTo);
                    st.setRecordedBy(appUtil.getCurrentUser(session));
                    st.setSiteCode(communityCode);
                    stdao.saveSiteTransition(st);
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                request.setAttribute("stmsg", msg);
            }
            
        }
        //
        return mapping.findForward(PARAMPAGE);
    }
    private List generateOrgUnitParametersList(String requiredAction,String stateCode,String lgaCode, String cboCode,HttpServletRequest request)
    {
        DaoUtil util=new DaoUtil();
        List list=new ArrayList();
        try
        {
            list=util.getRealOrganizationalUnitsFromData(requiredAction, stateCode, lgaCode, cboCode, cboCode);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
    private void generateLgaList(String stateCode,HttpServletRequest request)
    {
        ReportUtility rutil=new ReportUtility();
        
        HttpSession session=request.getSession();
        List lgaList=rutil.generateLgaListForReports(stateCode);
        session.setAttribute("stlgaList", lgaList);
        session.setAttribute("orgList", new ArrayList());
    }
}
