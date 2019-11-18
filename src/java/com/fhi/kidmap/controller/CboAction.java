/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AjaxProcessor;
import com.fhi.kidmap.business.Cbos;
import com.fhi.kidmap.dao.CboDao;
import com.fhi.kidmap.dao.CboDaoImpl;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author COMPAQ USER
 */
public class CboAction extends org.apache.struts.action.Action {
    
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
            throws Exception {
        //This action manages CBO records, adds new and updates existing CBO
        CbosActionForm cboForm=(CbosActionForm)form;
        Cbos cbo=new Cbos();
        HttpSession session=request.getSession();
        
        String lgaCode=cboForm.getLga_code().trim();
        String cboCode=cboForm.getCbo_code().trim().toUpperCase();
        String cboListCode=cboForm.getCboList();
        String cboName=cboForm.getCbo_name().trim();

        //cbo.setState_code(cboForm.getState_code());
        cbo.setLga_code(lgaCode);
        cbo.setCbo_code(cboCode);
        cbo.setCbo_name(cboName);
        cbo.setAddress(cboForm.getAddress());
        cbo.setEmail(cboForm.getEmail());
        cbo.setFax(cboForm.getFax());
        cbo.setPhone(cboForm.getPhone());
        cbo.setWebsite(cboForm.getWebsite());

        String modifyDisabled="true";
        String saveDisabled="false";

        AjaxProcessor aproc=new AjaxProcessor();
        List cboList;

        String requiredAction=cboForm.getActionName();
        try
        {
        CboDao dao=new CboDaoImpl();
        if (requiredAction.equals("cboList"))
        {
             cboList=aproc.getCboListPerLga(lgaCode);
             session.setAttribute("cbolistperlga", cboList);
             return mapping.findForward(SUCCESS);
        }
        else if (requiredAction.equals("cboDetails"))
        {
                String[] values = aproc.getCboDetails(cboListCode).split(";");
                cboForm.setLga_code(values[2]);
                session.setAttribute("cboDetails", values);
                modifyDisabled="false";
                saveDisabled="true";
                session.setAttribute("cboModifyDisabled", modifyDisabled);
                session.setAttribute("cbosavedisabled", saveDisabled);
                return mapping.findForward(SUCCESS);
        }
            else if(requiredAction.equals("save"))
                dao.saveCbo(cbo);
            else if(requiredAction.equals("modify"))
            {
                cbo.setCbo_code(cboForm.getCbo_code());
                dao.updateCbo(cbo);
            }
            else if(requiredAction.equals("delete"))
            {
                cbo.setCbo_code(cboForm.getCbo_code());
                dao.deleteCbo(cbo);
            }
        }
        catch(Exception ex)
        {
            ActionErrors errors = new ActionErrors();
            errors.add(ActionErrors.GLOBAL_MESSAGE,
            new ActionMessage("errors.database.error",
            ex.getMessage()));
            if (!errors.isEmpty())
            {
                saveErrors(request, errors);
            }
            return mapping.findForward(FAILURE);
        }
        form.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
}
