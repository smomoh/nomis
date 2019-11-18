/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.TrainingCategory;
import com.fhi.kidmap.dao.TrainingCategoryDao;
import com.fhi.kidmap.dao.TrainingCategoryDaoImpl;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Siaka
 */
public class TrainingCategoryAction extends org.apache.struts.action.Action {

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
        TrainingCategoryForm trnCategoryForm=(TrainingCategoryForm)form;
        TrainingCategory trnCat=new TrainingCategory();
        TrainingCategoryDao dao=new TrainingCategoryDaoImpl();
        String categoryId=trnCategoryForm.getCategoryId();
        String hiddenId=trnCategoryForm.getHiddenId();
        trnCat.setCategoryName(trnCategoryForm.getCategoryName());
        List trainingCategoryList=dao.getAllTrainingCategories();
        trnCategoryForm.setTrainingCategoryList(trainingCategoryList);
        String requiredAction=trnCategoryForm.getActionName();
        HttpSession session=request.getSession();
        session.setAttribute("trncategorySavedisabled", "false");
        session.setAttribute("trncategoryModifyDisabled", "true");
        if(requiredAction==null)
        {
           trnCategoryForm.reset(mapping, request); 
           return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("details"))
        {
            trnCategoryForm.reset(mapping, request);
            trnCat=dao.getTrainingCategory(categoryId);
            if(trnCat !=null)
            {
                trnCategoryForm.setCategoryName(trnCat.getCategoryName());
                trnCategoryForm.setHiddenId(categoryId);
                session.setAttribute("trncategorySavedisabled", "true");
                session.setAttribute("trncategoryModifyDisabled", "false");
            }
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            dao.saveTrainingCategory(trnCat);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            trnCat.setCategoryId(hiddenId);
            dao.updateTrainingCategory(trnCat);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            trnCat.setCategoryId(hiddenId);
            dao.deleteTrainingCategory(trnCat);
        }
        trnCategoryForm.reset(mapping, request);
        trainingCategoryList=dao.getAllTrainingCategories();
        trnCategoryForm.setTrainingCategoryList(trainingCategoryList);
        return mapping.findForward(SUCCESS);
    }
}
