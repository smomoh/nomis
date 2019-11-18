/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AjaxProcessor;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.Wards;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
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
public class PartnerUpdateAction extends org.apache.struts.action.Action {

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
        PartnerUpdateForm partnerForm=(PartnerUpdateForm)form;
        AjaxProcessor aproc=new AjaxProcessor();
        PartnersDao pdao=new PartnersDaoImpl();

        DaoUtil util=new DaoUtil();
        HttpSession session=request.getSession();
        List partnerLgaList=new ArrayList();
        List cboList=new ArrayList();
        List partnerWardList=new ArrayList();
        //List paramList=new ArrayList();
        String requiredAction;

        String stateName=partnerForm.getState();
        String lgaName=partnerForm.getLga();
        String cboName=partnerForm.getCbo();
        String wardName=partnerForm.getWard();
        String partnerCode=partnerForm.getPartnerCode();
        requiredAction=partnerForm.getActionName();
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getAllStates(session);
        loadup.setParamAttributes(request);
        List emptyList=new ArrayList();
        List partnerList=pdao.getAllPartners();
        session.setAttribute("partnerList", partnerList);
        if(requiredAction==null)
        {
            session.setAttribute("partnerLgaList", emptyList);
            session.setAttribute("partnerOrgList", emptyList);
            session.setAttribute("partnerWardList", emptyList);
            return mapping.findForward(SUCCESS);
        }
        if(requiredAction.equals("lga"))
        {
            partnerLgaList =util.getOrganizationalUnits(requiredAction,stateName,lgaName,cboName,wardName);
            session.setAttribute("partnerLgaList", partnerLgaList);
            session.setAttribute("partnerOrgList", emptyList);
            session.setAttribute("partnerWardList", emptyList);
            
        }
        else if(requiredAction.equals("cbo"))
        {
            OrganizationRecords orgRecords=null;
            List partnerOrgList=new ArrayList();
            cboList =util.getOrganizationalUnits(requiredAction,stateName,lgaName,cboName,wardName);
            for(Object org:cboList)
            {
                orgRecords=util.getOrganizationRecords(org.toString());
                partnerOrgList.add(orgRecords);
            }
            session.setAttribute("partnerOrgList", partnerOrgList);
            session.setAttribute("partnerWardList", emptyList);
        }
        else if(requiredAction.equals("ward"))
        {
            partnerWardList =util.getOrganizationalUnits(requiredAction,stateName,lgaName,cboName,wardName);
            Wards ward=null;
            List partnerOrgList=new ArrayList();
            for(Object org:partnerWardList)
            {
                if(org==null || org.toString().indexOf("/")==-1)
                continue;
                ward=util.getWard(org.toString());
                partnerOrgList.add(ward);
            }
            session.setAttribute("partnerWardList", partnerOrgList);
        }
        else if(requiredAction.equals("updatePartner"))
        {
            String[] params={stateName,lgaName,cboName,null,null,null,null,null,null,null,null,wardName};
            String queryCriteria=util.getHVIReportCriteria(params);
            HouseholdEnrollment hhe=null;
            HouseholdEnrollmentDao hheDao=new HouseholdEnrollmentDaoImpl();
            List hheList=hheDao.getListOfHouseholdsWithoutPartner(queryCriteria);//hvaDao.getListOfHouseholdVulnerabilityIndexWithoutPartner(queryCriteria);
            if(hheList !=null && !hheList.isEmpty())
            {
                for(int i=0; i<hheList.size(); i++)
                {
                    hhe=(HouseholdEnrollment)hheList.get(i);
                    hhe.setPartnerCode(partnerCode);
                    hheDao.updateHouseholdEnrollment(hhe);
                    System.err.println("hhe.getPartnerCode() is "+hhe.getPartnerCode());
                }
            }
            partnerForm.reset(mapping, request);
        }
        return mapping.findForward(SUCCESS);
    }
}
