/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.GraduationCheckList;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.GraduationCheckListDao;
import com.fhi.kidmap.dao.GraduationCheckListDaoImpl;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.LoadUpInfo;
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
public class GraduationCheckListAction extends org.apache.struts.action.Action {

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
        GraduationCheckListForm gcf=(GraduationCheckListForm)form;
        GraduationCheckList gcl=new GraduationCheckList();
        GraduationCheckListDao gcldao=new GraduationCheckListDaoImpl();
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();
        HttpSession session=request.getSession();
        String currentUser=appUtil.getCurrentUser(session);
        String requiredAction=gcf.getActionName();
        int hhSerialNo=gcf.getHhSerialNo();
        String hhUniqueId=gcf.getHhUniqueId();
        String dateOfAssessment=gcf.getDateOfAssessment();
        String stateCode=gcf.getStateCode();
        String lgaCode=gcf.getLgaCode();
        String orgCode=gcf.getOrgCode();
        String community=gcf.getCommunity();
        int id=gcf.getId();
        String health=gcf.getHealth();
        String safety=gcf.getSafety();
        String school=gcf.getSchool();
        String stability=gcf.getStability();
        String vulnerability=gcf.getVulnerability();
        String graduated=gcf.getGraduated();
        String dateModified=appUtil.getCurrentDate();
        String volunteerId=gcf.getVolunteerId();
        
        gcl.setId(id);
        gcl.setClientId(hhUniqueId);
        gcl.setDateOfAssessment(appUtil.processMthDayYearToMysqlFormat(dateOfAssessment));
        gcl.setHealth(health);
        gcl.setClientType("hh");
        gcl.setSafety(safety);
        gcl.setSchool(school);
        gcl.setStability(stability);
        gcl.setVulnerability(vulnerability);
        gcl.setGraduated(graduated);
        gcl.setDateModified(dateModified);
        gcl.setRecordedby(currentUser);
        gcl.setVolunteerId(volunteerId);
        String msg=" ";
        session.setAttribute("gclModifyBtnDisabled", "true");
        session.setAttribute("gclSaveBtnDisabled", "false");
        if(requiredAction==null)
        {
            gcf.reset(mapping, request);
            loadStartupInfo(session);
            String partnerCode=util.getPartnerCode(currentUser);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("cboList"))
        {
            LoadUpInfo loadup=new LoadUpInfo();
            loadup.getCbosPerLgaFromSetup(session,gcf.getLgaCode());
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("hhDetails"))
        {
            gcf.reset(mapping, request);
            OvcWithdrawalDao withdrawaldao=new OvcWithdrawalDaoImpl();
            
            if(withdrawaldao.getOvcWithdrawal(hhUniqueId) !=null)
            {
                msg="This household has been withdrawn ";
                request.setAttribute("msg", msg);
                return mapping.findForward(SUCCESS);
            }
            gcf.setLgaCode(lgaCode);
            gcf.setHhUniqueId(hhUniqueId);
            gcf.setHhSerialNo(hhSerialNo);
            gcf.setOrgCode(orgCode);
            gcf.setCommunity(community);
            gcf.setHhUniqueId(hhUniqueId);
            session.setAttribute("hhUniqueId", hhUniqueId);
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
            if(hhe !=null)
            {
                gcf.setAddress(hhe.getAddress());
                gcf.setHhAge(hhe.getHhAge());
                gcf.setDateOfEnrollment(appUtil.monthDayYear(hhe.getDateOfAssessment()));
                gcf.setHhFirstname(hhe.getHhFirstname());
                gcf.setHhSurname(hhe.getHhSurname());
                gcf.setCommunity(util.getWardName(hhe.getCommunityCode()));
                //gcf.setMaritalStatus(hhe.getMaritalStatus());
                gcf.setOccupation(hhe.getOccupation());
                gcf.setPhone(hhe.getPhone());
                gcf.setHhGender(hhe.getHhGender());
                
                gcl=gcldao.getGraduationCheckList(hhUniqueId);
                if(gcl !=null)
                {
                    gcf.setDateOfAssessment(appUtil.monthDayYear(gcl.getDateOfAssessment()));
                    gcf.setHealth(gcl.getHealth());
                    gcf.setSafety(gcl.getSafety());
                    gcf.setSchool(gcl.getSchool());
                    gcf.setStability(gcl.getStability());
                    gcf.setVulnerability(gcl.getVulnerability());
                    gcf.setId(gcl.getId());
                    gcf.setVolunteerId(gcl.getVolunteerId());
                    gcf.setGraduated(gcl.getGraduated());
                    session.setAttribute("gclModifyBtnDisabled", "false");
                    session.setAttribute("gclSaveBtnDisabled", "true");
                }
             }
                session.setAttribute("hhUniqueId", hhUniqueId);
                return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            gcldao.saveGraduationCheckList(gcl);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            gcldao.updateGraduationCheckList(gcl);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            gcldao.deleteGraduationCheckList(gcl);
        }
        gcf.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
    private void loadStartupInfo(HttpSession session)
    {
        session.setAttribute("hhUniqueId", " ");
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getStartupInfo(session);
    }
}
