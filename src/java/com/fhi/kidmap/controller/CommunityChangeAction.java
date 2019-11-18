/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.LgaDao;
import com.fhi.kidmap.dao.LgaDaoImpl;
import com.fhi.kidmap.dao.OrganizationRecordsDao;
import com.fhi.kidmap.dao.OrganizationRecordsDaoImpl;
import com.fhi.kidmap.dao.WardDao;
import com.fhi.kidmap.dao.WardDaoImpl;
import com.fhi.kidmap.report.HouseholdReports;
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
public class CommunityChangeAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String CURRENTPARAMPAGE = "currentparamPage";
    private static final String NEWPARAMPAGE = "newparamPage";
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
        CommunityChangeForm ccform=(CommunityChangeForm)form;
        String requiredAction=ccform.getActionName();
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        User user=appUtil.getUser(session);
        AccessManager acm=appUtil.getAccessManager();
        acm.setStateListForReports(session);
        String currentStateCode=ccform.getCurrentState();
        String currentLgaCode=ccform.getCurrentLga();
        String currentCboCode=ccform.getCurrentCbo();
        String currentCommunityCode=ccform.getCurrentCommunity();
        String sortOrder=ccform.getSortOrder();
        String newStateCode=ccform.getNewState();
        String newLgaCode=ccform.getNewLga();
        String newCboCode=ccform.getNewCbo();
        String newCommunityCode=ccform.getNewCommunity();
        String[] householdsToChange=ccform.getHouseholdsToChange();
        
        List lgaList=new ArrayList();
        List cboList=new ArrayList();
        List wardList=new ArrayList();
        List emptyList=new ArrayList();
        
        boolean userInRole=acm.isUserInRole("mergeou",appUtil.getCurrentUser(session));
        if(!userInRole)
        {
            request.setAttribute("errorMsg", appUtil.getAccessDeniedMessage());
            return mapping.findForward("accessDenied");
        }
        if(requiredAction==null)
        {
            ccform.reset(mapping, request);
            return mapping.findForward(NEWPARAMPAGE);
        }
        else if(requiredAction.equals("currentlga"))
        {
            lgaList=generateLgaList(currentStateCode,request); 
            session.setAttribute("curlgaList", lgaList);
            session.setAttribute("curcboList", emptyList);
            session.setAttribute("curcommunityList", emptyList);
            ccform.reset(mapping, request);
            return mapping.findForward(NEWPARAMPAGE);
        }
        else if(requiredAction.equals("currentcbo"))
        {
            if(user !=null)
            cboList =acm.getListOfCBOsAssignedToUser(user,currentStateCode,currentLgaCode);
            session.setAttribute("curcboList", cboList);
            session.setAttribute("curcommunityList", emptyList); 
            ccform.reset(mapping, request);
            return mapping.findForward(NEWPARAMPAGE);
        }
        else if(requiredAction.equals("currentcommunity"))
        {
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,currentStateCode,currentLgaCode,currentCboCode);
            session.setAttribute("curcommunityList", wardList);
            ccform.reset(mapping, request);
            return mapping.findForward(NEWPARAMPAGE);
        }
        else if(requiredAction.equals("newlga"))
        {
            LgaDao ldao=new LgaDaoImpl();
            lgaList=ldao.getLgaList(newStateCode);//generateLgaList(newStateCode,request); 
            session.setAttribute("newlgaList", lgaList);
            session.setAttribute("newcboList", emptyList);
            session.setAttribute("newcommunityList", emptyList);
            ccform.reset(mapping, request);
            return mapping.findForward(NEWPARAMPAGE);
        }
        else if(requiredAction.equals("newcbo"))
        {
            OrganizationRecordsDao ordao=new OrganizationRecordsDaoImpl();
            //if(user !=null)
            cboList =ordao.getListOfOrganizationsAssignedToLga(newLgaCode);//acm.getListOfCBOsAssignedToUser(user,newStateCode,newLgaCode);
            session.setAttribute("newcboList", cboList);
            session.setAttribute("newcommunityList", emptyList);
            ccform.reset(mapping, request);
            return mapping.findForward(NEWPARAMPAGE);
        }
        else if(requiredAction.equals("newcommunity"))
        {
            WardDao wdao=new WardDaoImpl();
            //if(user !=null)
            wardList =wdao.getWardByLgaCodeAndCboId(newLgaCode, newCboCode);
            session.setAttribute("newcommunityList", wardList);
            ccform.reset(mapping, request);
            return mapping.findForward(NEWPARAMPAGE);
        }
        
        else if(requiredAction.equals("generateList"))
        {
            generateReport(currentStateCode,currentLgaCode,currentCboCode,currentCommunityCode,"All",request,sortOrder);
            ccform.reset(mapping, request);
            return mapping.findForward(NEWPARAMPAGE);
        }
        else if(requiredAction.equals("changeCommunity"))
        {
            DaoUtil util=new DaoUtil();
            if(householdsToChange !=null)
            {
                for(int i=0; i<householdsToChange.length; i++)
                {
                    System.err.println("householdsToChange is "+householdsToChange[i]);
                    util.changeCommunity(householdsToChange[i],newCommunityCode);
                }
            }
            ccform.reset(mapping, request);
            return mapping.findForward(NEWPARAMPAGE);
        }
        //householdsToChange
        return mapping.findForward(NEWPARAMPAGE);
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
    private void generateReport(String stateCode,String lgaCode, String cboCode, String communityCode,String partnerCode,HttpServletRequest request,String sortOrder)
    {
        DaoUtil util=new DaoUtil();
        String stateName=util.getStateName(stateCode);
        String lgaName=util.getLgaName(lgaCode);
        String cboName=util.getOrganizationName(cboCode);
        String wardName=util.getWardName(communityCode);
        String partnerName=util.getPartnerName(partnerCode);
        String param[]={stateCode,lgaCode,cboCode,communityCode,stateName,lgaName,cboName,wardName,partnerName,"All",partnerCode};
        HouseholdReports hhr=new HouseholdReports();
        hhr.getHhRecords(request, param,sortOrder);
    }
}
