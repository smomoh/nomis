/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.CategoryCombination;
import com.fhi.kidmap.dao.CategoryCombinationDao;
import com.fhi.kidmap.dao.CategoryCombinationDaoImpl;
import com.fhi.kidmap.dao.CategoryOptionDao;
import com.fhi.kidmap.dao.CategoryOptionDaoImpl;
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
 * @author smomoh
 */
public class CategoryCombinationAction extends org.apache.struts.action.Action {
    
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
        CategoryCombinationForm ccf=(CategoryCombinationForm)form;
        CategoryOptionDao codao=new CategoryOptionDaoImpl();
        CategoryCombinationDao ccdao=new CategoryCombinationDaoImpl();
        CategoryCombination catCombo=new CategoryCombination();
        AppUtility appUtil=new AppUtility();

        HttpSession session=request.getSession();
        List categoryComboList=ccdao.getCategoryCombinationList();
        List categoryOptionList=codao.getCategoryOptions();

        catCombo.setCategoryComboName(ccf.getCatComboName());
        catCombo.setDescription(ccf.getDescription());
        String[] categories=ccf.getCategoryId();
        catCombo.setCategoryId(appUtil.concatStr(categories, null));
        String categoryComboId=ccf.getCategoryComboId();
        String hiddenId=ccf.getHiddenId();
        catCombo.setProgramArea(ccf.getProgramArea());
        catCombo.setQuery(ccf.getQuery());
        catCombo.setType(ccf.getType());

        session.setAttribute("catComboSaveBtnDisabled", "false");
        session.setAttribute("catComboModifyBtnDisabled", "true");
        String msg=" ";
        /*if(TaskManager.isDatabaseLocked())
        {
            msg="System busy. Kindly try again later";
            request.setAttribute("msg", msg);
            return mapping.findForward(SUCCESS);
        }*/
        String requiredAction=ccf.getActionName();
        if(requiredAction==null)
        {
            ccf.reset(mapping, request);
            session.setAttribute("categoryComboList", categoryComboList);
            session.setAttribute("categoryOptionList", categoryOptionList);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("details"))
        {
            catCombo=ccdao.getCategoryCombination(categoryComboId);
            if(catCombo !=null)
            {
                ccf.reset(mapping, request);
                ccf.setDescription(catCombo.getDescription());
                ccf.setCatComboName(catCombo.getCategoryComboName());
                categories=appUtil.splitString(catCombo.getCategoryId(), ",");
                ccf.setCategoryId(categories);
                ccf.setHiddenId(catCombo.getCategoryComboId());
                ccf.setProgramArea(catCombo.getProgramArea());
                ccf.setQuery(catCombo.getQuery());
                ccf.setType(catCombo.getType());
            }
            session.setAttribute("catComboSaveBtnDisabled", "true");
            session.setAttribute("catComboModifyBtnDisabled", "false");
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            ActionErrors errors=getActionErrors(request,catCombo.getCategoryComboName());
            if(!errors.isEmpty())
            {
                saveErrors(request,errors);
                return mapping.findForward(SUCCESS);
            }
            ccdao.saveCategoryCombination(catCombo);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            CategoryCombination duplicateCatCombo=ccdao.getCategoryCombination(ccf.getHiddenId());
             if(duplicateCatCombo !=null)
             {
                 if(!catCombo.getCategoryComboName().equalsIgnoreCase(duplicateCatCombo.getCategoryComboName()))
                 {
                    ActionErrors errors=getActionErrors(request,catCombo.getCategoryComboName());
                    if(!errors.isEmpty())
                    {
                        saveErrors(request,errors);
                        session.setAttribute("catComboSaveBtnDisabled", "true");
                        session.setAttribute("catComboModifyBtnDisabled", "false");
                        return mapping.findForward(SUCCESS);
                    }
                 }
             }
            catCombo.setCategoryComboId(hiddenId);
            ccdao.updateCategoryCombination(catCombo);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            catCombo.setCategoryComboId(hiddenId);
            ccdao.deleteCategoryCombination(catCombo);
        }
        ccf.reset(mapping, request);
        categoryComboList=ccdao.getCategoryCombinationList();
        categoryOptionList=codao.getCategoryOptions();
        session.setAttribute("categoryComboList", categoryComboList);
        session.setAttribute("categoryOptionList", categoryOptionList);
        return mapping.findForward(SUCCESS);
    }
    public ActionErrors getActionErrors(HttpServletRequest request,String objectName)
    {
        ActionErrors errors=new ActionErrors();
        CategoryCombinationDao ccdao=new CategoryCombinationDaoImpl();
        try
        {
            CategoryCombination duplicateCatCombo=ccdao.getCategoryCombinationByName(objectName);
            if(duplicateCatCombo !=null)
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("categoryComboName.exist"));
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return errors;
    }
}
