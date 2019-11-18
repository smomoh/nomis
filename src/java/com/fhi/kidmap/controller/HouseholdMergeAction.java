/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.LgaDao;
import com.fhi.kidmap.dao.LgaDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.dao.StatesDao;
import com.fhi.kidmap.dao.StatesDaoImpl;
import com.fhi.kidmap.report.HouseholdReports;
import com.fhi.nomis.nomisutils.AccessManager;
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
public class HouseholdMergeAction extends org.apache.struts.action.Action {

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
            throws Exception 
    {
        HouseholdMergeForm hhmForm=(HouseholdMergeForm)form;
        AppUtility appUtil=new AppUtility();
        HttpSession session=request.getSession();
        String requiredAction=hhmForm.getActionName();
        String stateCode=hhmForm.getState();
        String lgaCode=hhmForm.getLga();
        String orgCode=hhmForm.getOrganization();
        String wardCode=hhmForm.getWard();
        String hhUniqueId=hhmForm.getHhUniqueId();
        String recordToLoad=hhmForm.getRecordToLoad();
        String sortOrder=hhmForm.getSortOrder();
        request.setAttribute("hhmBtnDisabled", "false");
        AccessManager acm=new AccessManager();
        boolean userInRole=acm.isUserInRole("hhmerge",appUtil.getCurrentUser(session));
        if(!userInRole)
        {
            request.setAttribute("errorMsg", appUtil.getAccessDeniedMessage());
            return mapping.findForward("accessDenied");
        }
        if(wardCode==null || wardCode.equalsIgnoreCase("All"))
        {
            request.setAttribute("hhmBtnDisabled", "true");
        }
        String partnerCode=hhmForm.getPartnerCode();
        //HttpSession session=request.getSession();

        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        StatesDao sDao=new StatesDaoImpl();
        LgaDao lDao=new LgaDaoImpl();
        DaoUtil util=new DaoUtil();
        PartnersDao pdao=new PartnersDaoImpl();
        
        List lgaList=new ArrayList();
        List cboList=new ArrayList();
        List wardList=new ArrayList();
        List list=new ArrayList();
        List stateList=new ArrayList();
        List partnerList=new ArrayList();
        partnerList=pdao.getAllPartners();
        
        String id=request.getParameter("id");
        if(id !=null)
        requiredAction="householdMemberList";
        String ovcId=request.getParameter("uid");
        if(ovcId !=null)
        requiredAction="deleteOvc";
        request.setAttribute("btnName", "Merge household records");
        session.setAttribute("partnerList", partnerList);
        session.removeAttribute("hviRecords");
        if(requiredAction==null)
        {
            list=hhedao.getHVIStateList();
            for(Object obj:list)
            {
                States state=sDao.getStateByStateCode(obj.toString());
                if(state !=null)
                stateList.add(state);
            }
            
            session.setAttribute("hhmStateList",stateList);
            session.setAttribute("hhmLgaList",lgaList);
            session.setAttribute("hhmOrgList", cboList);
            session.setAttribute("hhmwardList", wardList);
            hhmForm.reset(mapping, request);
            return mapping.findForward("paramPage");
        }

        else if(requiredAction.equals("lga"))
        {
            list=hhedao.getHVILgaList(stateCode);
            for(Object obj:list)
            {
                Lgas lga=lDao.getLgaByCode(obj.toString());
                if(lga !=null)
                lgaList.add(lga);
            }
            session.setAttribute("hhmLgaList", lgaList);
            session.setAttribute("hhmOrgList", cboList);
            hhmForm.reset(mapping, request);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("cbo"))
        {
            OrganizationRecords orgRecords=null;
            if(lgaCode !=null)
            lgaCode=lgaCode.trim();
            List orgList=new ArrayList();
            cboList =hhedao.getHVIOrgList(lgaCode);
            for(Object org:cboList)
            {
                orgRecords=util.getOrganizationRecords(org.toString());
                orgList.add(orgRecords);
            }
            session.setAttribute("hhmOrgList", orgList);
            hhmForm.reset(mapping, request);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("wardList"))
        {
            wardList=util.getHheWardListByStateLgaAndCbo(stateCode, lgaCode,orgCode);
            
            session.setAttribute("hhmwardList", wardList);
            hhmForm.reset(mapping, request);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("hhmReportList"))
        {
            generateReport(stateCode,lgaCode,orgCode, wardCode,partnerCode,request,recordToLoad,sortOrder);
            hhmForm.reset(mapping, request);
        }
        else if(requiredAction.equals("merge"))
        {
            String orgUnitToKeep=hhmForm.getKeep();
            String[] orgUnitsToMerge=hhmForm.getMerge();
            if(recordToLoad !=null && recordToLoad.equalsIgnoreCase("caregiverrecords"))
            {
                mergeCaregivers(orgUnitToKeep, orgUnitsToMerge);
            }
            else
            {
                if(orgUnitToKeep !=null)
                {
                    if(orgUnitsToMerge !=null)
                    {
                        for(int i=0; i<orgUnitsToMerge.length; i++)
                        {
                            if(!orgUnitsToMerge[i].equalsIgnoreCase(orgUnitToKeep))
                            util.mergeHouseholds(orgUnitsToMerge[i], orgUnitToKeep);
                            System.err.println("Keep "+orgUnitToKeep+", Merge "+orgUnitsToMerge[i]);
                        }
                    }
                }
            }
            generateReport(stateCode,lgaCode,orgCode, wardCode,partnerCode,request,recordToLoad,sortOrder);
        }
        else if(requiredAction.equals("mergecgiverRecords"))
        {
            String orgUnitToKeep=hhmForm.getKeep();
            String[] orgUnitsToMerge=hhmForm.getMerge();
            mergeCaregivers(orgUnitToKeep, orgUnitsToMerge);
            
            List hhMemberList=new ArrayList();
            List reportList=hhedao.getHouseholdMembers(hhUniqueId);
            if(reportList !=null)
            hhMemberList =reportList;
            request.setAttribute("hhMemberList", hhMemberList);
            return mapping.findForward("hhMemberListPage");
        }
        else if(requiredAction.equalsIgnoreCase("deleteOvc"))
        {
            OvcDao ovcdao=new OvcDaoImpl();
            Ovc ovc=new Ovc();
            ovc.setOvcId(ovcId);
            ovcdao.deleteOvc(ovc);
            //ovcdao.deleteAllOvcRecords(ovcId);
            List hhMemberList=new ArrayList();
            if(ovcId !=null && ovcId.indexOf("/") !=-1)
            hhUniqueId=ovcId.substring(0, ovcId.lastIndexOf("/"));
            System.err.println("hhUniqueId is "+hhUniqueId);
            List reportList=hhedao.getHouseholdMembers(hhUniqueId);
            if(reportList !=null)
            hhMemberList =reportList;
            request.setAttribute("hhMemberList", hhMemberList);
            return mapping.findForward("hhMemberListPage");
        }
        else if(requiredAction.equalsIgnoreCase("householdMemberList"))
        {
            if(recordToLoad !=null && recordToLoad.equalsIgnoreCase("caregiverrecords"))
            {
                CaregiverDao cgiverdao=new CaregiverDaoImpl();
                List hhMemberList=new ArrayList();
                List reportList=cgiverdao.getHouseholdMembers(id);
                if(reportList !=null)
                hhMemberList =reportList;
                request.setAttribute("hhMemberList", hhMemberList);
            }
            else
            {
                List hhMemberList=new ArrayList();
                List reportList=hhedao.getHouseholdMembers(id);
                if(reportList !=null)
                hhMemberList =reportList;
                request.setAttribute("hhMemberList", hhMemberList);
            }
            return mapping.findForward("hhMemberListPage");
        }
        hhmForm.reset(mapping, request);
        return mapping.findForward("paramPage");
    }
    public void mergeCaregivers(String orgUnitToKeep, String[] orgUnitsToMerge)
    {
        DaoUtil util=new DaoUtil();
        if(orgUnitToKeep !=null)
        {
            if(orgUnitsToMerge !=null)
            {
                for(int i=0; i<orgUnitsToMerge.length; i++)
                {
                    if(!orgUnitsToMerge[i].equalsIgnoreCase(orgUnitToKeep))
                    util.mergeCaregivers(orgUnitsToMerge[i], orgUnitToKeep);
                    System.err.println("Keep "+orgUnitToKeep+", Merge "+orgUnitsToMerge[i]);
                }
            }
        }
    }
    private void generateReport(String stateCode,String lgaCode, String orgCode, String wardCode,String partnerCode,HttpServletRequest request,String recordToLoad,String sortOrder)
    {
        
        DaoUtil util=new DaoUtil();
        String stateName=util.getStateName(stateCode);
        String lgaName=util.getLgaName(lgaCode);
        String cboName=util.getOrganizationName(orgCode);
        String wardName=util.getWardName(wardCode);
        String partnerName=util.getPartnerName(partnerCode);
        String param[]={stateCode,lgaCode,orgCode,wardCode,stateName,lgaName,cboName,wardName,partnerName,"All",partnerCode};
        HouseholdReports hhr=new HouseholdReports();
        if(recordToLoad.equalsIgnoreCase("householdrecords"))
        {
            hhr.getHhRecords(request,param,sortOrder);
            request.setAttribute("btnName", "Merge household records");
        }
        else if(recordToLoad.equalsIgnoreCase("caregiverrecords"))
        {
            String additionalQueryCriteria=util.getHVIOrgUnitQuery(param);
            List caregiverList=hhr.getCaregiverList(additionalQueryCriteria, request.getSession());
            //System.err.println("caregiverList size is "+caregiverList.size());
            request.setAttribute("caregiverList", caregiverList);
            request.setAttribute("btnName", "Merge caregiver records");
        }
    }
}
