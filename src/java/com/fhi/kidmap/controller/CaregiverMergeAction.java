/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.business.Wards;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.LgaDao;
import com.fhi.kidmap.dao.LgaDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.dao.StatesDao;
import com.fhi.kidmap.dao.StatesDaoImpl;
import com.fhi.kidmap.report.HouseholdReports;
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
public class CaregiverMergeAction extends org.apache.struts.action.Action {

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
        CaregiverMergeForm cgmForm=(CaregiverMergeForm)form;
        AppUtility appUtil=new AppUtility();
        String requiredAction=cgmForm.getActionName();
        String stateCode=cgmForm.getState();
        String lgaCode=cgmForm.getLga();
        String orgCode=cgmForm.getOrganization();
        String wardCode=cgmForm.getWard();
        String partnerCode=cgmForm.getPartnerCode();
        HttpSession session=request.getSession();

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
        
        session.setAttribute("partnerList", partnerList);
        
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
            cgmForm.reset(mapping, request);
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
            cgmForm.reset(mapping, request);
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
            cgmForm.reset(mapping, request);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("wardList"))
        {
            Wards ward=null;
            if(wardCode !=null)
            wardCode=wardCode.trim();
            List wardCodeList =hhedao.getHVIWardList(orgCode);
            if(wardCodeList !=null)
            {
                for(Object org:wardCodeList)
                {
                    ward=util.getWard(org.toString());
                    wardList.add(ward);
                }
            }
            session.setAttribute("hhmwardList", wardList);
            cgmForm.reset(mapping, request);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("cgmReportList"))
        {
            generateReport(stateCode,lgaCode,orgCode, wardCode,partnerCode,request);
            cgmForm.reset(mapping, request);
        }
        else if(requiredAction.equals("merge"))
        {
            util.correctHhIdInHouseholdEnrollmentData(stateCode, stateCode, orgCode, wardCode);
            String orgUnitToKeep=cgmForm.getKeep();
            String[] orgUnitsToMerge=cgmForm.getMerge();
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
            generateReport(stateCode,lgaCode,orgCode, wardCode,partnerCode,request);
        }
        else if(requiredAction.equalsIgnoreCase("caregiverMemberList"))
        {
            List hhMemberList=new ArrayList();
            hhMemberList=hhedao.getHouseholdMembers(id);
            
            request.setAttribute("cgiverMemberList", hhMemberList);
            return mapping.findForward("cgiverMemberListPage");
        }
        cgmForm.reset(mapping, request);
        return mapping.findForward("paramPage");
    }
    private void generateReport(String stateCode,String lgaCode, String orgCode, String wardCode,String partnerCode,HttpServletRequest request)
    {
        HttpSession session=request.getSession();
        DaoUtil util=new DaoUtil();
        String stateName=util.getStateName(stateCode);
        String lgaName=util.getLgaName(lgaCode);
        String cboName=util.getOrganizationName(orgCode);
        String wardName=util.getWardName(wardCode);
        String partnerName=util.getPartnerName(partnerCode);
        String param[]={stateCode,lgaCode,orgCode,wardCode,stateName,lgaName,cboName,wardName,partnerName,"All",partnerCode};
        String additionalQueryCriteria=util.getHVIReportCriteria(param);
        HouseholdReports hhr=new HouseholdReports();
        List encryptedCgiverList=hhr.getCaregiverList(additionalQueryCriteria,session);
        request.setAttribute("cgmRecords", encryptedCgiverList);
        request.setAttribute("cgmParam", param);
    }
      
}
