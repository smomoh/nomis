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
 * @author COMPAQ USER
 */
public class DqaReportAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String PARAMPAGE="paramPage";
    
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
        DqaReportForm reportForm=(DqaReportForm)form;

        OvcRecords records=new OvcRecords();

        DaoUtil util=new DaoUtil();
        PartnersDao pdao=new PartnersDaoImpl();
        
        List paramList=new ArrayList();
        String requiredAction;
        String stateCode=reportForm.getDqa_state();
        String lgaCode=reportForm.getLga();
        String cboCode=reportForm.getCbo();
        String partnerCode=reportForm.getPartnerCode();
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        User user=appUtil.getUser(session);
        AccessManager acm=new AccessManager();

        requiredAction=reportForm.getActionName();
        List partnerList=pdao.getAllPartners();
        session.setAttribute("partnerList", partnerList);
        session.removeAttribute("ovcCount");
        List lgaList=new ArrayList();
        
        if(requiredAction==null)
        {
            LoadUpInfo loadup=new LoadUpInfo();
            loadup.getAllStates(session);
            session.setAttribute("dqaLgaList", new ArrayList());
            session.setAttribute("dqaOrgList", new ArrayList());
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("lga"))
        {
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
            session.setAttribute("dqaLgaList", lgaList);
            session.setAttribute("dqaOrgList", new ArrayList());
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =new ArrayList();
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("dqaOrgList", orgList);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equals("dqareport"))
        {
            paramList.add(stateCode);
            paramList.add(lgaCode);
            paramList.add(cboCode);
            paramList.add(partnerCode);
            records.doDqa(session,paramList);
        }
        
        return mapping.findForward(SUCCESS);
    }
}
