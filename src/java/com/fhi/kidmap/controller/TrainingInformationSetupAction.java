/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.TrainingInformationSetup;
import com.fhi.kidmap.dao.TrainingInformationSetupDao;
import com.fhi.kidmap.dao.TrainingInformationSetupDaoImpl;
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
public class TrainingInformationSetupAction extends org.apache.struts.action.Action {
    
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
        TrainingInformationSetupForm tisf=(TrainingInformationSetupForm)form;
        TrainingInformationSetup tis=new TrainingInformationSetup();
        TrainingInformationSetupDao tisdao=new TrainingInformationSetupDaoImpl();
        HttpSession session=request.getSession();
        String hiddenId=tisf.getHiddenId();
        String recordId=tisf.getRecordId();
        tis.setTrainingName(tisf.getTrainingName());
        String requiredAction=tisf.getActionName();
        List list=tisdao.getAllTrainingInformationSetups();
        tisf.setTrainingInfoList(list);
        session.setAttribute("trainingInfoSavedisabled", "false");
        session.setAttribute("trainingInfoModifyDisabled", "true");
        if(requiredAction==null)
        {
           tisf.reset(mapping, request);
           list=tisdao.getAllTrainingInformationSetups();
           tisf.setTrainingInfoList(list);
           return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("details"))
        {
            tisf.reset(mapping, request);
            tis=tisdao.getTrainingInformationSetup(recordId);
            if(tis !=null)
            {
                tisf.setTrainingName(tis.getTrainingName());
                tisf.setHiddenId(recordId);
                session.setAttribute("trainingInfoSavedisabled", "true");
                session.setAttribute("trainingInfoModifyDisabled", "false");
            }
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            tisdao.saveTrainingInformationSetup(tis);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            tis.setRecordId(hiddenId);
            tisdao.updateTrainingInformationSetup(tis);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            tis.setRecordId(hiddenId);
            tisdao.deleteTrainingInformationSetup(tis);
        }
        tisf.reset(mapping, request);
        list=tisdao.getAllTrainingInformationSetups();
        tisf.setTrainingInfoList(list);
        return mapping.findForward(SUCCESS);
    }
}
