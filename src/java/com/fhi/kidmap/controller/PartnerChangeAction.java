/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.Partners;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.business.Wards;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.PartnerReportBean;
import com.fhi.nomis.OperationsManagement.HouseholdEnrollmentManager;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.AppUtility;
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
public class PartnerChangeAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String PARAMPAGE="paramPage";
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
        PartnerChangeForm pcform=(PartnerChangeForm)form;
        String requiredAction=pcform.getActionName();
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        User user=appUtil.getUser(session);
        AccessManager acm=appUtil.getAccessManager();
        List partnerList=acm.getPartnerListForReports(session);
        acm.setStateListForReportsByPartnerCode(session,partnerList);
        String stateCode=pcform.getStateCode();
        String lgaCode=pcform.getLgaCode();
        String cboCode=pcform.getCboCode();
        String communityCode=pcform.getCommunityCode();
        String currentPartner=pcform.getCurrentPartner();
        String newPartner=pcform.getNewPartner();
        
        boolean userInRole=acm.isUserInRole("chptner",appUtil.getCurrentUser(session));
        if(!userInRole)
        {
            request.setAttribute("errorMsg", appUtil.getAccessDeniedMessage());
            return mapping.findForward("accessDenied");
        }
        
        List stateList=new ArrayList();
        if(requiredAction !=null && !requiredAction.equalsIgnoreCase("stateList"))        
        getListOfStatesByPartnerCode(request,currentPartner);
        
        
        /*String sortOrder=pcform;
        String newStateCode=pcform.getNewState();
        String newLgaCode=pcform.getNewLga();
        String newCboCode=pcform.getNewCbo();
        String newCommunityCode=pcform.getNewCommunity();
        String[] householdsToChange=pcform.getHouseholdsToChange();*/
        
        List lgaList=new ArrayList();
        List cboList=new ArrayList();
        List wardList=new ArrayList();
        List emptyList=new ArrayList();
        
        List currentPartnerList=acm.getPartnerListForReports(session);
        request.setAttribute("currentPartnerList", currentPartnerList);
        
        PartnersDao pdao=new PartnersDaoImpl();
        List newPartnerList=pdao.getAllPartners();
        request.setAttribute("newPartnerList", newPartnerList);
        
        if(requiredAction==null)
        {
            pcform.reset(mapping, request);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("stateList"))
        {
            stateList=getListOfStatesByPartnerCode(request,currentPartner);
            request.setAttribute("stateListForPartners", stateList);
            System.err.println("stateList.size() is "+stateList.size());
            session.setAttribute("curlgaList", new ArrayList());
            session.setAttribute("curcboList", new ArrayList());
            session.setAttribute("curcommunityList", new ArrayList());
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("currentlga"))
        {
            lgaList=generateLgaList(stateCode,request); 
            session.setAttribute("curlgaList", lgaList);
            session.setAttribute("curcboList", emptyList);
            session.setAttribute("curcommunityList", emptyList);
            pcform.reset(mapping, request);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("currentcbo"))
        {
            if(user !=null)
            cboList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("curcboList", cboList);
            session.setAttribute("curcommunityList", emptyList); 
            pcform.reset(mapping, request);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("currentcommunity"))
        {
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,cboCode);
            session.setAttribute("curcommunityList", wardList);
            pcform.reset(mapping, request);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("changePartner"))
        {
            DaoUtil util=new DaoUtil();
            String stateName=util.getStateName(stateCode);
            String lgaName=util.getLgaName(lgaCode);
            String cboName=util.getOrganizationName(cboCode);
            String wardName=util.getWardName(communityCode);
            String partnerName=util.getPartnerName(currentPartner);
            String param[]={stateCode,lgaCode,cboCode,communityCode,stateName,lgaName,cboName,wardName,partnerName,"All",currentPartner};
            String additionalQuery=util.getHVIOrgUnitQuery(param);
            List reportList=HouseholdEnrollmentManager.getHouseholdEnrollmentDaoInstance().getListOfHouseholdEnrollment(additionalQuery, "asc");
            if(reportList !=null)
            {
                HouseholdEnrollment hhe=null;
                for(int i=0; i<reportList.size(); i++)
                {
                    hhe=(HouseholdEnrollment)reportList.get(i);
                    if(hhe !=null)
                    {
                        hhe.setPartnerCode(newPartner);
                        HouseholdEnrollmentManager.getHouseholdEnrollmentDaoInstance().updateHouseholdEnrollment(hhe);
                        System.err.println("hhe.getHhUniqueId() is "+hhe.getHhUniqueId());
                    }
                }

            }
        }
        return mapping.findForward(PARAMPAGE);
    }
    private List getListOfStatesByPartnerCode(HttpServletRequest request, String partnerCode)
    {
        List stateList=new ArrayList();
        List idList=new ArrayList();
        List list=getListOfPartnerReportBeanByPartnerCode(partnerCode);
        if(list !=null)
        {
            PartnerReportBean prb=null;
            for(int i=0; i<list.size(); i++)
            {
                prb=(PartnerReportBean)list.get(i);
                if(!idList.contains(prb.getState().getState_code()))
                {
                    stateList.add(prb.getState());
                    idList.add(prb.getState().getState_code());
                }
            }
        }
        request.setAttribute("stateListForPartners", stateList);
        return stateList;
    }
    private List getListOfPartnerReportBeanByPartnerCode(String partnerCode)
    {
        DaoUtil util=new DaoUtil();
        List reportList=new ArrayList();
        try
        {
            List list=HouseholdEnrollmentManager.getHouseholdEnrollmentDaoInstance().getDistinctRecordsByPartner(partnerCode);
            if(list !=null)
            {
                
                PartnerReportBean prb=null;
                String stateCode;
                String lgaCode;
                String cboCode;
                String communityCode;
                States state=null;
                Lgas lga=null;
                OrganizationRecords cbo=null;
                Wards community=null;
                Partners partner=null;
                for(int i=0; i<list.size(); i++)
                {
                    Object[] objArray=(Object[])list.get(i);
                    stateCode=(String)objArray[0];
                    lgaCode=(String)objArray[1];
                    cboCode=(String)objArray[2];
                    communityCode=(String)objArray[3];
                    state=util.getStateByStateCode(stateCode);
                    lga=util.getLgaByLgaCode(lgaCode);
                    cbo=util.getOrganizationRecordsByorgCode(cboCode);
                    community=util.getWardByorgCode(communityCode);
                    partner=util.getPartnerByPartnerCode(partnerCode);
                    prb=new PartnerReportBean();
                    prb.setCommunity(community);
                    prb.setLga(lga);
                    prb.setOrganization(cbo);
                    prb.setPartner(partner);
                    prb.setState(state);
                    reportList.add(prb);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return reportList;
    }
    private void generateReport(String partnerCode,HttpServletRequest request)
    {
        DaoUtil util=new DaoUtil();
        List reportList=new ArrayList();
        try
        {
            List list=HouseholdEnrollmentManager.getHouseholdEnrollmentDaoInstance().getDistinctRecordsByPartner(partnerCode);
            if(list !=null)
            {
                
                PartnerReportBean prb=null;
                String stateCode;
                String lgaCode;
                String cboCode;
                String communityCode;
                States state=null;
                Lgas lga=null;
                OrganizationRecords cbo=null;
                Wards community=null;
                Partners partner=null;
                for(int i=0; i<list.size(); i++)
                {
                    Object[] objArray=(Object[])list.get(i);
                    stateCode=(String)objArray[0];
                    lgaCode=(String)objArray[1];
                    cboCode=(String)objArray[2];
                    communityCode=(String)objArray[3];
                    state=util.getStateByStateCode(stateCode);
                    lga=util.getLgaByLgaCode(lgaCode);
                    cbo=util.getOrganizationRecordsByorgCode(cboCode);
                    community=util.getWardByorgCode(communityCode);
                    partner=util.getPartnerByPartnerCode(partnerCode);
                    prb=new PartnerReportBean();
                    prb.setCommunity(community);
                    prb.setLga(lga);
                    prb.setOrganization(cbo);
                    prb.setPartner(partner);
                    prb.setState(state);
                    reportList.add(prb);
                }
            }
            
            /*String stateName=util.getStateName(stateCode);
            String lgaName=util.getLgaName(lgaCode);
            String cboName=util.getOrganizationName(cboCode);
            String wardName=util.getWardName(communityCode);
            String partnerName=util.getPartnerName(partnerCode);
            String param[]={stateCode,lgaCode,cboCode,communityCode,stateName,lgaName,cboName,wardName,partnerName,"All",partnerCode};
            HouseholdReports hhr=new HouseholdReports();
            hhr.getHhRecords(request, param,sortOrder);*/
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        request.setAttribute("partnerRecordList", reportList);
    }
    private List generateLgaList(String stateCode,HttpServletRequest request)
    {
        List lgaList=new ArrayList();
        AppUtility appUtil=new AppUtility();
        User user=appUtil.getUser(request.getSession());
        AccessManager acm=new AccessManager();
        try
        {
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return lgaList;
    }
}
