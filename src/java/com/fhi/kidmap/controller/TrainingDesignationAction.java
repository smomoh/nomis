/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.TrainingDesignation;
import com.fhi.kidmap.dao.TrainingCategoryDao;
import com.fhi.kidmap.dao.TrainingCategoryDaoImpl;
import com.fhi.kidmap.dao.TrainingDesignationDao;
import com.fhi.kidmap.dao.TrainingDesignationDaoImpl;
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
public class TrainingDesignationAction extends org.apache.struts.action.Action {

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
        TrainingDesignationForm designationForm=(TrainingDesignationForm)form;
        TrainingDesignation td=new TrainingDesignation();
        TrainingDesignationDao tdDao=new TrainingDesignationDaoImpl();
        TrainingCategoryDao dao=new TrainingCategoryDaoImpl();
        HttpSession session=request.getSession();
        String designationId=designationForm.getDesignationId();
        String hiddenId=designationForm.getHiddenId();
        td.setCategoryId(designationForm.getCategoryId());
        td.setDesignationName(designationForm.getDesignationName());
        String requiredAction=designationForm.getActionName();
        List categoryList=dao.getAllTrainingCategories();
        designationForm.setCategoryList(categoryList);
        List designationList=tdDao.getAllTrainingDesignations();
        designationForm.setDesignationList(designationList);
        
        session.setAttribute("trnDesignationSavedisabled", "false");
        session.setAttribute("trnDesignationModifyDisabled", "true");
        if(requiredAction==null)
        {
           designationForm.reset(mapping, request); 
           return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("details"))
        {
            designationForm.reset(mapping, request);
            td=tdDao.getTrainingDesignation(designationId);
            if(td !=null)
            {
                designationForm.setCategoryId(td.getCategoryId());
                designationForm.setHiddenId(designationId);
                designationForm.setDesignationName(td.getDesignationName());
                session.setAttribute("trnDesignationSavedisabled", "true");
                session.setAttribute("trnDesignationModifyDisabled", "false");
            }
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            tdDao.saveTrainingDesignation(td);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            td.setDesignationId(hiddenId);
            tdDao.updateTrainingDesignation(td);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            td.setDesignationId(hiddenId);
            tdDao.deleteTrainingDesignation(td);
        }
        designationForm.reset(mapping, request);
        designationList=tdDao.getAllTrainingDesignations();
        designationForm.setDesignationList(designationList);
        return mapping.findForward(SUCCESS);
    }
}
