/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.CustomDataElement;
import com.fhi.kidmap.business.ReportQuery;
import com.fhi.kidmap.dao.CategoryCombinationDao;
import com.fhi.kidmap.dao.CategoryCombinationDaoImpl;
import com.fhi.kidmap.dao.CustomDataElementsDao;
import com.fhi.kidmap.dao.CustomDataElementsDaoImpl;
import com.fhi.kidmap.dao.ReportQueryDao;
import com.fhi.kidmap.dao.ReportQueryDaoImpl;
import com.fhi.nomis.nomisutils.TaskManager;
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
 * @author Siaka
 */
public class CustomDataElementsAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
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
        CustomDataElementsForm cdef=(CustomDataElementsForm)form;
        CustomDataElementsDao cdeDao=new CustomDataElementsDaoImpl();
        CustomDataElement cde=new CustomDataElement();
        CategoryCombinationDao catDao=new CategoryCombinationDaoImpl();
        HttpSession session=request.getSession();
        ReportQueryDao rqdao=new ReportQueryDaoImpl();
        
        String customDeId=cdef.getCustomDeId();
        cde.setCategoryComboId(cdef.getCategoryComboId());
        cde.setCustomDeDescription(cdef.getCustomDeDescription());
        cde.setCustomDeName(cdef.getCustomDeName());
        cde.setStdDeId(cdef.getStdDeId());
        
        String requiredAction=cdef.getActionName();
        
        session.setAttribute("customDeSaveBtnDisabled", "false");
        session.setAttribute("customDeModifyBtnDisabled", "true");
        String msg=" ";
        /*if(TaskManager.isDatabaseLocked())
        {
            msg="System busy";
            request.setAttribute("msg", msg);
            return mapping.findForward(SUCCESS);
        }*/
        if(requiredAction==null)
        {
            setSessions(session);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("details"))
        {
            cdef.reset(mapping, request);
            cde=cdeDao.getCustomDataElement(customDeId);
            if(cde !=null)
            {
                cdef.setCategoryComboId(cde.getCategoryComboId());
                cdef.setCustomDeDescription(cde.getCustomDeDescription());
                cdef.setHiddenId(cde.getCustomDeId());
                cdef.setCustomDeName(cde.getCustomDeName());
                cdef.setStdDeId(cde.getStdDeId());
                ReportQuery rq=rqdao.getReportQuery(cde.getStdDeId());
                if(rq !=null)
                cdef.setStdDeDescription(rq.getQueryDescription());
                session.setAttribute("customDeSaveBtnDisabled", "true");
                session.setAttribute("customDeModifyBtnDisabled", "false");
            }
            setSessions(session);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            setSessions(session);
            ActionErrors errors=getActionErrors(request,cde.getCustomDeName());
            if(!errors.isEmpty())
            {
                saveErrors(request,errors);
                return mapping.findForward(SUCCESS);
            }
            cdeDao.saveCustomDataElement(cde);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            CustomDataElement duplicateCde=cdeDao.getCustomDataElement(cdef.getHiddenId());
             if(duplicateCde !=null)
             {
                 if(!cde.getCustomDeName().equalsIgnoreCase(duplicateCde.getCustomDeName()))
                 {
                    ActionErrors errors=getActionErrors(request,cde.getCustomDeName());
                    if(!errors.isEmpty())
                    {
                        saveErrors(request,errors);
                        session.setAttribute("customDeSaveBtnDisabled", "true");
                        session.setAttribute("customDeModifyBtnDisabled", "false");
                        return mapping.findForward(SUCCESS);
                    }
                 }
             }       
            cde.setCustomDeId(cdef.getHiddenId());         
            cdeDao.updateCustomDataElement(cde);
            setSessions(session);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            cde.setCustomDeId(cdef.getHiddenId());
            cdeDao.deleteCustomDataElement(cde);
        }
        cdef.reset(mapping, request);
        setSessions(session);
        return mapping.findForward(SUCCESS);
    }
    public void setSessions(HttpSession session)
    {
        CustomDataElementsDao cdeDao=new CustomDataElementsDaoImpl();
        CategoryCombinationDao catDao=new CategoryCombinationDaoImpl();
        ReportQueryDao rqdao=new ReportQueryDaoImpl();
        try
        {
            List rqList=rqdao.getReportQueries();
            List list=catDao.getCategoryCombinationList();
            List cdeList=cdeDao.getCustomDataElementList();
            session.setAttribute("catComboList", list);
            session.setAttribute("custDeList", cdeList);
            session.setAttribute("rqList", rqList);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public ActionErrors getActionErrors(HttpServletRequest request,String objectName)
    {
        ActionErrors errors=new ActionErrors();
        CustomDataElementsDao cdeDao=new CustomDataElementsDaoImpl();
        try
        {
            CustomDataElement duplicateCde=cdeDao.getCustomDataElemenByName(objectName);
            if(duplicateCde !=null)
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("customDeName.exist"));                
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return errors;
    }
}
