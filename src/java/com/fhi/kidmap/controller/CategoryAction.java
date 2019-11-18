/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Category;
import com.fhi.kidmap.business.CategoryOption;
import com.fhi.kidmap.dao.CategoryDao;
import com.fhi.kidmap.dao.CategoryDaoImpl;
import com.fhi.kidmap.dao.CategoryOptionDao;
import com.fhi.kidmap.dao.CategoryOptionDaoImpl;
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
 * @author smomoh
 */
public class CategoryAction extends org.apache.struts.action.Action {
    
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
        CategoryForm cform=(CategoryForm)form;
        AppUtility appUtil=new AppUtility();
        Category category=new Category();
        CategoryDao cdao=new CategoryDaoImpl();
        CategoryOptionDao codao=new CategoryOptionDaoImpl();
        String categoryId=cform.getCategoryId();
        String hiddenId=cform.getHiddenId();
        String categoryName=cform.getCategoryName();
        String catOptions=null;
        String[] categoryOptions=cform.getCategoryOptionId();
        if(categoryOptions !=null)
        {
            catOptions=appUtil.concatStr(categoryOptions, null);
        }
        category.setCategoryId(categoryId);
        category.setCategoryName(categoryName);
        category.setCategoryOption(catOptions);
        
        List categoryOptionList=new ArrayList();
        List categoryList=new ArrayList();
        String requiredAction=cform.getActionName();
        HttpSession session=request.getSession();
        session.setAttribute("categorySaveBtnDisabled", "false");
        session.setAttribute("categoryModifyBtnDisabled", "true");
        System.err.println("requiredAction is "+requiredAction);
        if(requiredAction==null)
        {
            cform.reset(mapping, request);
            categoryOptionList=codao.getCategoryOptions();
            categoryList=cdao.getCategories();
            session.setAttribute("categoryOptionList", categoryOptionList);
            session.setAttribute("categoryList", categoryList);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("details"))
        {
            cform.reset(mapping, request);
            category=cdao.getCategory(categoryId);
            if(category !=null)
            {
                cform.setHiddenId(categoryId);
                cform.setCategoryName(category.getCategoryName());
                String[] catOptionArray=appUtil.splitString(category.getCategoryOption(), ",");
                cform.setCategoryOptionId(catOptionArray);
            }
            session.setAttribute("categorySaveBtnDisabled", "true");
            session.setAttribute("categoryModifyBtnDisabled", "false");
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            ActionErrors errors=getActionErrors(request,category.getCategoryName());
            if(!errors.isEmpty())
            {
                saveErrors(request,errors);
                return mapping.findForward(SUCCESS);
            }
            cdao.saveCategory(category);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            Category duplicateCategory=cdao.getCategory(cform.getHiddenId());
             if(duplicateCategory !=null)
             {
                 if(!category.getCategoryName().equalsIgnoreCase(duplicateCategory.getCategoryName()))
                 {
                    ActionErrors errors=getActionErrors(request,category.getCategoryName());
                    if(!errors.isEmpty())
                    {
                        saveErrors(request,errors);
                        session.setAttribute("categorySaveBtnDisabled", "true");
                        session.setAttribute("categoryModifyBtnDisabled", "false");
                        return mapping.findForward(SUCCESS);
                    }
                 }
             }
            category.setCategoryId(hiddenId);
            cdao.updateCategory(category);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            category.setCategoryId(hiddenId);
            cdao.deleteCategory(category);
        }
        cform.reset(mapping, request);
        categoryOptionList=codao.getCategoryOptions();
        categoryList=cdao.getCategories();
        session.setAttribute("categoryOptionsList", categoryOptionList);
        session.setAttribute("categoryList", categoryList);
        return mapping.findForward(SUCCESS);
    }
    public ActionErrors getActionErrors(HttpServletRequest request,String objectName)
    {
        ActionErrors errors=new ActionErrors();
        CategoryDao cdao=new CategoryDaoImpl();
        try
        {
            Category duplicateCategory=cdao.getCategoryName(objectName);
            if(duplicateCategory !=null)
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("categoryName.exist"));
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return errors;
    }
}
