/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.dataexchange.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.nomis.dataexchange.business.DhisInstance;
import com.nomis.dataexchange.dao.DhisInstanceDao;
import com.nomis.dataexchange.dao.DhisInstanceDaoImpl;
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
public class DhisInstanceAction extends org.apache.struts.action.Action {

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
        AppUtility appUtil=new AppUtility();
        DhisInstanceForm dhisForm=(DhisInstanceForm)form;
        DhisInstanceDao dao=new DhisInstanceDaoImpl();
        String dhisInstanceName=dhisForm.getInstanceName();
        String availableInstance=dhisForm.getAvailableInstance();
        String hiddenId=dhisForm.getHiddenId();
        if(availableInstance !=null)
        hiddenId=availableInstance;
        String requiredAction=dhisForm.getActionName();
        HttpSession session=request.getSession();
        session.setAttribute("dhisInstanceSaveDisabled", "false");
        session.setAttribute("dhisInstanceModifyDisabled", "true");
        
        DhisInstance instance=new DhisInstance();
        instance.setId(hiddenId);
        instance.setInstanceName(dhisInstanceName);
        instance.setLastModified(appUtil.getCurrentDate());
        List instanceList=dao.getAllDhisInstances();
        session.setAttribute("availableInstanceList", instanceList);
        if(requiredAction==null)
        {
            dhisForm.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        if(requiredAction.equalsIgnoreCase("details"))
        {
            instance=dao.getDhisInstanceById(hiddenId);
            if(instance !=null)
            {
                dhisForm.setHiddenId(instance.getId());
                dhisForm.setAvailableInstance(instance.getId());
                dhisForm.setInstanceName(instance.getInstanceName());
                session.setAttribute("dhisInstanceSaveDisabled", "true");
                session.setAttribute("dhisInstanceModifyDisabled", "false");
            }
            return mapping.findForward(SUCCESS);
        }
        if(requiredAction.equalsIgnoreCase("save"))
        {
            instance.setId(null);
            dao.saveDhisInstance(instance);
            //return mapping.findForward(SUCCESS);
        }
        if(requiredAction.equalsIgnoreCase("modify"))
        {
            dao.updateDhisInstance(instance);
            //return mapping.findForward(SUCCESS);
        }
        if(requiredAction.equalsIgnoreCase("delete"))
        {
            dao.deleteDhisInstance(instance);
            //return mapping.findForward(SUCCESS);
        }
        instanceList=dao.getAllDhisInstances();
        session.setAttribute("availableInstanceList", instanceList);
        dhisForm.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
}
