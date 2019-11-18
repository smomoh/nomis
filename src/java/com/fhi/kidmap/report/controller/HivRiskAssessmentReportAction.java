/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HivRiskAssessmentChecklistDao;
import com.fhi.kidmap.dao.HivRiskAssessmentChecklistDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DateManager;
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
public class HivRiskAssessmentReportAction extends org.apache.struts.action.Action {

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
        HivRiskAssessmentReportForm hrarform=(HivRiskAssessmentReportForm)form;
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();
        PartnersDao pdao=new PartnersDaoImpl();
        HttpSession session=request.getSession();
        
        String requiredAction=hrarform.getActionName();
        String stateCode=hrarform.getStateCode();
        String lgaCode=hrarform.getLgaCode();
        String cboCode=hrarform.getCboCode();
        String orgUnitGroupId=hrarform.getOrgUnitGroupId();
        String wardCode=hrarform.getWardCode();
        String partnerCode=hrarform.getPartnerCode();
        String startMth=hrarform.getStartMth();
        String startYear=hrarform.getStartYear();
        String endMth=hrarform.getEndMth();
        String endYear=hrarform.getEndYear();
        String showOldFields=hrarform.getShowOldFields();
        
        List stateList=appUtil.getAccessManager().getStateListForReports(session);
        if(stateList !=null)
        hrarform.setStateList(stateList);
        List partnerList=pdao.getAllPartners();
        if(partnerList !=null)
        hrarform.setPartnerList(partnerList);
        List yearList=DateManager.generateYears();
        session.setAttribute("yearList", yearList);
        if(lgaCode ==null || lgaCode.equalsIgnoreCase("all"))
        {
            List orgUnitGroupList=ReportUtility.getOrgUnitGroup(stateCode);
            if(orgUnitGroupList !=null)
            hrarform.setOrgUnitGroupList(orgUnitGroupList);
        }
        
            List lgaList =util.getUserAssignedLgas(stateCode,session);
            if(lgaList !=null)
            hrarform.setLgaList(lgaList);
        
        List cboList=util.getUserAssignedCBOs(stateCode,lgaCode,session);
        if(cboList !=null)
        hrarform.setCboList(cboList);
        List wardList=util.getUserAssignedCommunities(stateCode,lgaCode,cboCode,session);
        if(wardList !=null)
        hrarform.setWardList(wardList);
        
        if(requiredAction==null)
        {
            hrarform.reset(mapping, request);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("report"))
        {
            String stateName=util.getStateName(stateCode);
            String lgaName=util.getLgaName(lgaCode);
            String cboName=util.getOrganizationName(cboCode);
            String wardName=util.getWardName(wardCode);
            String[] params={stateCode,lgaCode,cboCode,wardCode,startMth,endMth,startYear,endYear,stateName,lgaName,cboName,wardName,null,null,partnerCode};
            String[] dateParams={startMth,startYear,endMth,endYear};
            HivRiskAssessmentChecklistDao hracdao=new HivRiskAssessmentChecklistDaoImpl();
            String additionalQuery=util.getOrgQueryCriteria(params);
            String periodQuery=util.getHivRiskAssessmentPeriodQueryPart(util.getStartDate(dateParams),util.getEndDate(dateParams));
            additionalQuery=additionalQuery+periodQuery;
            List hracList=hracdao.getHivRiskAssessmentChecklist(additionalQuery);
            session.setAttribute("hracList", hracList);
            session.setAttribute("hracparams", params);
            hrarform.reset(mapping, request);
            if(showOldFields !=null && showOldFields.equalsIgnoreCase("on"))
            request.setAttribute("showOldFields", showOldFields);
            //if(hracList !=null)
            //System.err.println("hracList size is "+hracList.size());
            return mapping.findForward("reportPage");
        }
        return mapping.findForward(PARAMPAGE);
    }
}
