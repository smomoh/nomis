/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.CategoryOption;
import com.fhi.kidmap.dao.CategoryOptionDao;
import com.fhi.kidmap.dao.CategoryOptionDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.nomis.nomisutils.TaskManager;
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
public class CategoryOptionAction extends org.apache.struts.action.Action {
    
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
        CategoryOptionForm coForm=(CategoryOptionForm)form;
        CategoryOption coption=new CategoryOption();
        CategoryOptionDao coDao=new CategoryOptionDaoImpl();
        DaoUtil util=new DaoUtil();
        
        String coUniqueId=coForm.getCategoryOptionId();
        String hiddenId=coForm.getHiddenId();
        coption.setCategoryOptionDescription(coForm.getCategoryOptionDescription());
        coption.setCategoryOptionId(coUniqueId);
        coption.setCategoryOptionName(coForm.getCategoryOptionName());
        coption.setDisaggregation(coForm.getDisaggregation());
        
        //String disaggregation=coForm.getDisaggregation();
        String logic=coForm.getLogic();
        String ageUnit=coForm.getAgeUnit();
        int startAge=coForm.getStartAge();
        int endAge=coForm.getEndAge();
        String categoryDefinition=null;
        
        if(ageUnit!=null)
        {
            categoryDefinition=ageUnit+";"+logic+";"+startAge;
            if(logic !=null && logic.equalsIgnoreCase("between"))
            categoryDefinition=ageUnit+";"+logic+";"+startAge+";"+endAge;
        }
        coption.setCategoryDefinition(categoryDefinition);
        
        HttpSession session=request.getSession();
        List categoryOptionList=new ArrayList();
        
        String requiredAction=coForm.getActionName();
        session.setAttribute("visibility", "hidden");
        session.setAttribute("coptionSaveBtnDisabled", "false");
        session.setAttribute("coptionModifyBtnDisabled", "true");
        String msg=" ";
        /*if(TaskManager.isDatabaseLocked())
        {
            msg="System busy. Kindly try again later";
            request.setAttribute("msg", msg);
            return mapping.findForward(SUCCESS);
        }*/
        if(requiredAction==null)
        {
            categoryOptionList=coDao.getCategoryOptions();
            session.setAttribute("categoryOptionList", categoryOptionList);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("details"))
        {
            coption=coDao.getCategoryOption(coUniqueId);
            coForm.reset(mapping, request);
            if(coption !=null)
            {
                coForm.setCategoryOptionDescription(coption.getCategoryOptionDescription());
                coForm.setHiddenId(coption.getCategoryOptionId());
                coForm.setCategoryOptionId(coption.getCategoryOptionId());
                coForm.setCategoryOptionName(coption.getCategoryOptionName());
                categoryDefinition=coption.getCategoryDefinition();
                if(categoryDefinition !=null)
                {
                    String[] categoryDefinitionArray=categoryDefinition.split(";");
                    coForm.setAgeUnit(categoryDefinitionArray[0]);
                    coForm.setLogic(categoryDefinitionArray[1]);
                    if(categoryDefinitionArray[1].equalsIgnoreCase("between"))
                    session.setAttribute("visibility", "visible");
                    coForm.setStartAge(Integer.parseInt(categoryDefinitionArray[2]));
                    if(categoryDefinitionArray.length >3)
                    coForm.setEndAge(Integer.parseInt(categoryDefinitionArray[3]));
                }
                session.setAttribute("coptionSaveBtnDisabled", "true");
                session.setAttribute("coptionModifyBtnDisabled", "false");
            }
            categoryOptionList=coDao.getCategoryOptions();
            session.setAttribute("categoryOptionList", categoryOptionList);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            ActionErrors errors=getActionErrors(request,coption.getCategoryOptionName());
            if(!errors.isEmpty())
            {
                saveErrors(request,errors);
                return mapping.findForward(SUCCESS);
            }
            coDao.saveCategoryOption(coption);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            CategoryOption duplicateCatOption=coDao.getCategoryOption(coForm.getHiddenId());
             if(duplicateCatOption !=null)
             {
                 if(!coption.getCategoryOptionName().equalsIgnoreCase(duplicateCatOption.getCategoryOptionName()))
                 {
                    ActionErrors errors=getActionErrors(request,coption.getCategoryOptionName());
                    if(!errors.isEmpty())
                    {
                        saveErrors(request,errors);
                        session.setAttribute("coptionSaveBtnDisabled", "true");
                        session.setAttribute("coptionModifyBtnDisabled", "false");
                        return mapping.findForward(SUCCESS);
                    }
                 }
             }
            coption.setCategoryOptionId(hiddenId);
            coDao.updateCategoryOption(coption);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            coption.setCategoryOptionId(hiddenId);
            coDao.deleteCategoryOption(coption);
        }
        categoryOptionList=coDao.getCategoryOptions();
        session.setAttribute("categoryOptionList", categoryOptionList);
        coForm.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
    public ActionErrors getActionErrors(HttpServletRequest request,String objectName)
    {
        ActionErrors errors=new ActionErrors();
        CategoryOptionDao coDao=new CategoryOptionDaoImpl();
        try
        {
            CategoryOption duplicateCatOption=coDao.getCategoryOptionByName(objectName);
            if(duplicateCatOption !=null)
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("categoryOptionName.exist"));
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return errors;
    }
}
