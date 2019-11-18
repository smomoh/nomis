/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.kidmap.business.User;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.dao.ChildStatusIndexDao;
import com.fhi.kidmap.dao.ChildStatusIndexDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.OvcRecords;
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
public class CsiReportAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";

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
        CsiReportForm reportForm=(CsiReportForm)form;
        OvcDao ovcdao=new OvcDaoImpl();
        OvcRecords records=new OvcRecords();
        //LgaDao dao = new LgaDaoImpl();
        PartnersDao pdao=new PartnersDaoImpl();
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();
        AccessManager acm=appUtil.getAccessManager();
        HttpSession session=request.getSession();
        List lgaList=new ArrayList();
        List cboList=new ArrayList();
        List wardList=new ArrayList();
        List paramList=new ArrayList();
        String requiredAction;
        String stateCode=reportForm.getState();
        String lgaCode=reportForm.getLga();
        String cboCode=reportForm.getCbo();
        String wardCode=reportForm.getWard();
        String partnerCode=reportForm.getPartnerCode();
        int fmonth=reportForm.getEnroll_month();
        int fyear=reportForm.getEnroll_year();
        int smonth=reportForm.getEnroll_month2();
        int syear=reportForm.getEnroll_year2();
        String startAge=reportForm.getEnroll_startAge();
        String endAge=reportForm.getEnroll_endAge();
        int csiNo=reportForm.getCsiNo();
        requiredAction=reportForm.getActionName();
        session.removeAttribute("ovcCount");
        ChildStatusIndexDao csiDao=new ChildStatusIndexDaoImpl();
        acm.setStateListForReports(session);
        //LoadUpInfo loadup=new LoadUpInfo();
        //loadup.getAllStates(session);
        //loadup.setParamAttributes(request);
        acm.setStateListForReports(session);
        List partnerList=pdao.getAllPartners();
        List emptyList=new ArrayList();
        session.setAttribute("partnerList", partnerList);
        session.setAttribute("csiwardList", emptyList);
        String csiParam=request.getParameter("csiIndex");
        User user=appUtil.getUser(session);
        if(csiParam !=null)
        {
            requiredAction="csiList";
        }
        if(requiredAction==null)
        {
            //List list=ovcdao.getUniqueOvcId();
            //csiDao.reorderAssessmentNumber(list);
            List csiList=csiDao.getCsiWithSurveyNo(0);
            if(csiList !=null && csiList.size()>0)
            {
                for(Object csiListObj:csiList)
                {
                    ChildStatusIndex csi=(ChildStatusIndex)csiListObj;
                    csi.setSurveyNumber(1);
                    csiDao.updateChildStatusIndex(csi);
                }
            }
            //return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equals("lga"))
        {
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
            session.setAttribute("csilgaList", lgaList);
            session.setAttribute("csiorgList",new ArrayList());
            session.setAttribute("csiwardList", new ArrayList());
        }
        else if(requiredAction.equals("cbo"))
        {
            if(user !=null)
            cboList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("csiorgList", cboList);
            session.setAttribute("csiwardList", new ArrayList());
            //List orgList =util.getUserAssignedCBOs(stateCode,lgaCode,session);
            
        }
        else if(requiredAction.equals("ward"))
        {
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,cboCode);
            session.setAttribute("csiwardList", wardList);
        }
        else if(requiredAction.equals("csireport"))
        {
            paramList.add(stateCode);
            paramList.add(lgaCode);
            paramList.add(cboCode);
            paramList.add(fmonth);
            paramList.add(fyear);
            paramList.add(smonth);
            paramList.add(syear);
            paramList.add(startAge);
            paramList.add(endAge);
            paramList.add(csiNo);
            paramList.add(partnerCode);
            paramList.add(wardCode);
            records.getCsiValues(session, paramList);
            reportForm.reset(mapping, request);
        }
        else if(requiredAction.equals("csiList"))
        {
            request.setAttribute("csi", csiParam);
            OvcRecords record=new OvcRecords();
            record.getCsiOvcs(session, csiParam);
            return mapping.findForward("csiList");
        }
        List list=csiDao.getSurveyNumbers();
        request.setAttribute("surveyNumbers", list);
        return mapping.findForward(SUCCESS);
    }
}
