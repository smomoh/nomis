/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.User;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.SchoolRecords;
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
 * @author COMPAQ USER
 */
public class SchoolAttendanceAction extends org.apache.struts.action.Action {
    
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
        SchoolAttendanceForm reportForm=(SchoolAttendanceForm)form;

        //OvcRecords records=new OvcRecords();

        DaoUtil util=new DaoUtil();
        PartnersDao pdao=new PartnersDaoImpl();
        AppUtility appUtil=new AppUtility();
        HttpSession session=request.getSession();
        User user=appUtil.getUser(session);
        AccessManager acm=new AccessManager();
        acm.setStateListForReports(session);

        List paramList=new ArrayList();
        List lgaList=new ArrayList();
        List cboList=new ArrayList();
        List wardList=new ArrayList();
        String requiredAction;
        String stateCode=reportForm.getSchAttend_state();
        String lgaCode=reportForm.getSchAttend_lga();
        String cboCode=reportForm.getCbo();
        String wardCode=reportForm.getWard();
        String school_type=reportForm.getSchool_type();
        String partnerCode=reportForm.getPartnerCode();

        requiredAction=reportForm.getActionName();
        List partnerList=pdao.getAllPartners();
        session.setAttribute("partnerList", partnerList);
        session.removeAttribute("schoolAttendanceCount");//
        
        session.setAttribute("schoolwardList", wardList);
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getAllStates(session);
        loadup.setParamAttributes(request);
        if(requiredAction==null)
        {
            lgaList.clear();
            session.setAttribute("lgaList", lgaList);
            session.setAttribute("schoolorgList", cboList);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equals("lga"))
        {
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
            session.setAttribute("lgaList", lgaList);
            session.setAttribute("schoolorgList", cboList);
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =new ArrayList();
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("schoolorgList", orgList);
        }
        else if(requiredAction.equals("ward"))
        {
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,cboCode);
            session.setAttribute("schoolwardList", wardList);
        }
        else if(requiredAction.equals("schoolAttendance"))
        {
            paramList.add(stateCode);
            paramList.add(lgaCode);
            paramList.add(school_type);
            paramList.add(partnerCode);
            paramList.add(cboCode);
            paramList.add(wardCode);
            SchoolRecords record=new SchoolRecords();
            record.getSchoolAttendaceCount(session,paramList);
        }
        reportForm.reset(mapping,request);
        return mapping.findForward(SUCCESS);
    }
}
