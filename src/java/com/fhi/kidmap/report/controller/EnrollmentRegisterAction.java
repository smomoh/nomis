/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.dao.DaoUtil;
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
public class EnrollmentRegisterAction extends org.apache.struts.action.Action {
    
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
        EnrollmentRegisterForm regForm=(EnrollmentRegisterForm)form;
        DaoUtil util=new DaoUtil();
        HttpSession session=request.getSession();
        List lgaList=new ArrayList();
        List cboList=new ArrayList();
        
       if(session.getAttribute("selectionArray") == null) {
            String[] selectionArray = new String[3];
            //selectionArray = new String[3];

            session.setAttribute("selectionArray", selectionArray);

        }
        String state=regForm.getState();
        String lga=regForm.getLga();
        String cbo=regForm.getCbo();
        String requiredAction=regForm.getActionName();
        String wardName=null;

        if(requiredAction.equals("lga"))
        {
            lgaList =util.getUserAssignedLgas(state,session);
            //lgaList =util.getOrganizationalUnits(requiredAction,state,lga,cbo,wardName);
            session.setAttribute("lgaList", lgaList);
            session.removeAttribute("cboList");
            session.removeAttribute("wardList");
            ((String[])session.getAttribute("selectionArray"))[2] = state;
        }
        else if(requiredAction.equals("cbo"))
        {
            cboList =util.getUserAssignedCBOs(state,lga,session);
            //cboList =util.getOrganizationalUnits(requiredAction,state,lga,cbo,wardName);
            session.setAttribute("cboList", cboList);
            session.removeAttribute("wardList");
            ((String[])session.getAttribute("selectionArray"))[0] = lga;
        }
        
        return mapping.findForward(SUCCESS);
    }
}
