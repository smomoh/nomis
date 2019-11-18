/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.nomis.chart.DashboardManager;
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
public class HomeAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String HOMEPAGE = "homePage";
    private static final String DASHBOARDPAGE = "dashboardPage";
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
        HomeForm hf=(HomeForm)form;
        
        String nextPage=DASHBOARDPAGE;
        String homePage=request.getParameter("p");
        if(homePage !=null)
        nextPage=HOMEPAGE;
        String requiredAction=hf.getActionName();
        HttpSession session=request.getSession();
        if(requiredAction==null)
        {
            DashboardManager dm=new DashboardManager();
            //dm.loadDashboardItems(session);
            //return mapping.findForward(nextPage);
            return mapping.findForward(HOMEPAGE);
        }
        return mapping.findForward(HOMEPAGE);
    }
}
