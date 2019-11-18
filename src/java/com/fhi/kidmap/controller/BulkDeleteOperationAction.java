/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.DaoUtil;
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
public class BulkDeleteOperationAction extends org.apache.struts.action.Action {

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
        //This action manages bulk delete of beneficiary records
        BulkDeleteOperationForm bdform=(BulkDeleteOperationForm)form;
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        User user=appUtil.getUser(session);
        AccessManager acm=appUtil.getAccessManager();
        acm.setStateListForReports(session);
        
        String requiredAction=bdform.getActionName();
        String stateCode=bdform.getStateCode();
        String lgaCode=bdform.getLgaCode();
        String cboCode=bdform.getCboCode();
        String communityCode=bdform.getCommunityCode();
        String sortOrder=bdform.getSortOrder();
        String beneficiaryType=bdform.getBeneficiaryType();
        
        boolean userInRole=acm.isUserInRole("bdelete",appUtil.getCurrentUser(session));
        if(!userInRole)
        {
            request.setAttribute("errorMsg", appUtil.getAccessDeniedMessage());
            return mapping.findForward("accessDenied");
        }
        
        List lgaList=new ArrayList();
        List cboList=new ArrayList();
        List wardList=new ArrayList();
        List emptyList=new ArrayList();
        session.removeAttribute("bdhheList");
        if(requiredAction==null)
        {
            bdform.reset(mapping, request);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("lga"))
        {
            lgaList=generateLgaList(stateCode,request); 
            session.setAttribute("bdlgaList", lgaList);
            session.setAttribute("bdcboList", emptyList);
            session.setAttribute("bdcommunityList", emptyList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("cbo"))
        {
            if(user !=null)
            cboList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("bdcboList", cboList);
            session.setAttribute("bdcommunityList", emptyList); 
            bdform.reset(mapping, request);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("community"))
        {
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,cboCode);
            session.setAttribute("bdcommunityList", wardList);
            bdform.reset(mapping, request);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("generateList"))
        {
            generateReport(stateCode,lgaCode,cboCode,communityCode,"All",request,sortOrder,beneficiaryType);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("delete"))
        {
            String[] selectedRecords=bdform.getHouseholdsToDelete();
            if(selectedRecords !=null && selectedRecords.length>0)
            {
                DaoUtil util=new DaoUtil();
                String uniqueId=null;
                for(int i=0; i<selectedRecords.length; i++)
                {
                    uniqueId=selectedRecords[i];
                    HouseholdEnrollment hhe=util.getHouseholdEnrollmentDaoInstance().getHouseholdEnrollment(uniqueId);
                    if(hhe !=null)
                    {
                        //util.getHouseholdEnrollmentDaoInstance().deleteHouseholdMembers(uniqueId);
                        util.getHouseholdEnrollmentDaoInstance().deleteHouseholdEnrollment(hhe);
                    }
                }
            }
        }
        return mapping.findForward(PARAMPAGE);
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
    private void generateReport(String stateCode,String lgaCode, String cboCode, String communityCode,String partnerCode,HttpServletRequest request,String sortOrder,String beneficiaryType)
    {
        try
        {
            HttpSession session=request.getSession();
            DaoUtil util=new DaoUtil();
            String stateName=util.getStateName(stateCode);
            String lgaName=util.getLgaName(lgaCode);
            String cboName=util.getOrganizationName(cboCode);
            String wardName=util.getWardName(communityCode);
            String partnerName=util.getPartnerName(partnerCode);
            String param[]={stateCode,lgaCode,cboCode,communityCode,stateName,lgaName,cboName,wardName,partnerName,"All",partnerCode};
            String additionalQuery=util.getOrgQueryCriteria(param);
            session.removeAttribute("bdovcList");
            session.removeAttribute("bdcgiverList");
            session.removeAttribute("bdhheList");
            if(beneficiaryType !=null)
            {
                //The list of beneficiaries to be pulled is dependent on the beneficiary type that comes with the request
                if(beneficiaryType.equalsIgnoreCase("ovc"))
                {
                    List ovcList=util.getOvcDaoInstance().getOvcList(additionalQuery);
                    if(ovcList==null)
                    ovcList=new ArrayList();
                    session.setAttribute("bdovcList", ovcList);
                }
                else if(beneficiaryType.equalsIgnoreCase("caregiver"))
                {
                    List cgiverList=util.getCaregiverDaoInstance().getListOfCaregivers(additionalQuery);
                    if(cgiverList==null)
                    cgiverList=new ArrayList();
                    session.setAttribute("bdcgiverList",cgiverList);
                }
                else if(beneficiaryType.equalsIgnoreCase("household"))
                {
                    List hheList=util.getHouseholdEnrollmentDaoInstance().getListOfHouseholdEnrollment(additionalQuery, sortOrder);
                    if(hheList==null)
                    hheList=new ArrayList();
                    session.setAttribute("bdhheList",hheList);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
        
}
