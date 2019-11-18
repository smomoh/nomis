/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
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
public class HouseholdChangeOperationAction extends org.apache.struts.action.Action {

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
        HouseholdChangeOperationForm hcoform=(HouseholdChangeOperationForm)form;
        HouseholdEnrollment hhe=new HouseholdEnrollment();
        //HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        AppUtility appUtil=new AppUtility();
        String requiredAction=hcoform.getActionName();
        String uid=request.getParameter("uid");
        
        String id=request.getParameter("id");
        if(id !=null)
        requiredAction=id;
        String curHhUniqueId=hcoform.getCurhhUniqueId();
        String newHhUniqueId=hcoform.getNewHhUniqueId();
        //System.err.println("curHhUniqueId is "+curHhUniqueId);
        //System.err.println("newHhUniqueId is "+newHhUniqueId);
        String newhhSerialNo=hcoform.getSerialNo();
        //String ovcserialNo=hcoform.getOvcSerialNo();
        String curhhSerialNo=hcoform.getCurhhSerialNo();
        //String stateCode=hcoform.getStateCode();
        String lgaCode=hcoform.getLgaCode();
        String orgCode=hcoform.getOrgCode();
        String curlgaCode=hcoform.getCurlgaCode();
        String curorgCode=hcoform.getCurorgCode();
        HttpSession session=request.getSession();
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getAllStates(session);
        List emptyList=new ArrayList();
        
        if(requiredAction==null)
        {
            loadup.getStartupInfo(session);
            
            session.removeAttribute("curHhMemberList");
            session.removeAttribute("newHhMemberList");
            session.setAttribute("hhOrgList", emptyList);
            session.setAttribute("curhhOrgList", emptyList);
            return mapping.findForward("paramPage");
        }
                
        else if(requiredAction.equalsIgnoreCase("cbo"))
        {
            DaoUtil util=new DaoUtil();
            List orgList=util.getOrganizationRecordsFromHhe(lgaCode);
            session.setAttribute("hhOrgList", orgList);
            hcoform.reset(mapping, request);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equalsIgnoreCase("curcbo"))
        {
            DaoUtil util=new DaoUtil();
            List orgList=util.getOrganizationRecordsFromHhe(curlgaCode);
            session.setAttribute("curhhOrgList", orgList);
            hcoform.reset(mapping, request);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equalsIgnoreCase("newhhedetails"))
        {
            newHhUniqueId=orgCode+"/"+appUtil.padNumberWithZeros(newhhSerialNo, 5);
            List hhMembersList=getBenefiaciaryList(newHhUniqueId);
            session.setAttribute("newHhMemberList", hhMembersList);
            hcoform.reset(mapping, request);
            hcoform.setNewHhUniqueId(newHhUniqueId);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equalsIgnoreCase("curhhdetails"))
        {
            curHhUniqueId=curorgCode+"/"+appUtil.padNumberWithZeros(curhhSerialNo, 5);
            List hhMembersList=getBenefiaciaryList(curHhUniqueId);
            session.setAttribute("curHhMemberList", hhMembersList);
            hcoform.reset(mapping, request);
            hcoform.setCurhhUniqueId(curHhUniqueId);
            return mapping.findForward("paramPage");
            
        }
        else if(requiredAction.equalsIgnoreCase("cg"))
        {
            if(uid !=null)
            {
                CaregiverDao cgiverdao=new CaregiverDaoImpl();
                String curCaregiverId=uid;
                String newCaregiverId=uid.replace(curHhUniqueId, newHhUniqueId);
                cgiverdao.changeCaregiverId(curCaregiverId, newCaregiverId);
                List hhMembersList=getBenefiaciaryList(curHhUniqueId);
                session.setAttribute("curHhMemberList", hhMembersList);
                hhMembersList=getBenefiaciaryList(newHhUniqueId);
                session.setAttribute("newHhMemberList", hhMembersList);
            }
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equalsIgnoreCase("ov"))
        {
            if(uid !=null)
            {
                OvcDao dao=new OvcDaoImpl();
                String curOvcId=uid;
                
                System.err.println("curHhUniqueId is "+curHhUniqueId+" and newHhUniqueId is "+newHhUniqueId+" uid is "+uid);
                String newOvcId=uid.replace(curHhUniqueId, newHhUniqueId);
                System.err.println("curOvcId is "+curOvcId+" and newOvcId is "+newOvcId);
                dao.changeOvcId(curOvcId, newOvcId);
                List hhMembersList=getBenefiaciaryList(curHhUniqueId);
                session.setAttribute("curHhMemberList", hhMembersList);
                hhMembersList=getBenefiaciaryList(newHhUniqueId);
                session.setAttribute("newHhMemberList", hhMembersList);
            }
        }
        hcoform.reset(mapping, request);
        return mapping.findForward("paramPage");
    }
    public List getBenefiaciaryList(String hhUniqueId)
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        List hhMembersList=null;
        try
        {
            hhMembersList=hhedao.getHouseholdMembers(hhUniqueId);
            if(hhMembersList==null)
            hhMembersList=new ArrayList();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return hhMembersList;
    }
}
