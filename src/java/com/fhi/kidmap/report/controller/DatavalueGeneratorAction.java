/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.report.MonthlySummaryFormReport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class DatavalueGeneratorAction extends org.apache.struts.action.Action {
    
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
        DatavalueGeneratorForm dvgf=(DatavalueGeneratorForm)form;

        String requiredAction=dvgf.getActionName();
        request.setAttribute("msg", " ");
        request.setAttribute("dvbtnDisabled", "false");
        if(requiredAction==null)
        {
            dvgf.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("generateDatavalues"))
        {
            MonthlySummaryFormReport msfr=new MonthlySummaryFormReport();
            String msg=" ";
           // msg=msfr.generateDataValue();
            dvgf.reset(mapping, request);
            request.setAttribute("msg", msg);
        }
        return mapping.findForward(SUCCESS);
    }
}
