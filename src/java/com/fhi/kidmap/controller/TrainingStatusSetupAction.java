/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.TrainingStatusSetup;
import com.fhi.kidmap.dao.TrainingStatusSetupDao;
import com.fhi.kidmap.dao.TrainingStatusSetupDaoImpl;
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
public class TrainingStatusSetupAction extends org.apache.struts.action.Action {
    
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
        TrainingStatusSetupForm tssf=(TrainingStatusSetupForm)form;
        TrainingStatusSetupDao tssdao=new TrainingStatusSetupDaoImpl();
        TrainingStatusSetup tss=new TrainingStatusSetup();
        HttpSession session=request.getSession();
        String hiddenId=tssf.getHiddenId();
        String statusId=tssf.getStatusId();
        tss.setTrainingStatusName(tssf.getTrainingStatusName());
        List list=tssdao.getAllTrainingStatusSetups();
        tssf.setTrainingStatusList(list);
        session.setAttribute("trainingStatuSavedisabled", "false");
        session.setAttribute("trainingStatusModifyDisabled", "true");
        String requiredAction=tssf.getActionName();
        if(requiredAction==null)
        {
           tssf.reset(mapping, request);
            list=tssdao.getAllTrainingStatusSetups();
            tssf.setTrainingStatusList(list);
           return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("details"))
        {
            tssf.reset(mapping, request);
            tss=tssdao.getTrainingStatusSetup(statusId);
            if(tss !=null)
            {
                tssf.setTrainingStatusName(tss.getTrainingStatusName());
                tssf.setHiddenId(statusId);
                session.setAttribute("trainingStatuSavedisabled", "true");
                session.setAttribute("trainingStatusModifyDisabled", "false");
            }
            list=tssdao.getAllTrainingStatusSetups();
            tssf.setTrainingStatusList(list);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            tssdao.saveTrainingStatusSetup(tss);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            tss.setStatusId(hiddenId);
            tssdao.updateTrainingStatusSetup(tss);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            tss.setStatusId(hiddenId);
            tssdao.deleteTrainingStatusSetup(tss);
        }
        tssf.reset(mapping, request);
        list=tssdao.getAllTrainingStatusSetups();
            tssf.setTrainingStatusList(list);
        return mapping.findForward(SUCCESS);
    }
}
