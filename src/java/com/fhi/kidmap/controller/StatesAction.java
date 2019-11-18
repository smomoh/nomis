/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AjaxProcessor;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.dao.StatesDao;
import com.fhi.kidmap.dao.StatesDaoImpl;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author COMPAQ USER
 */
public class StatesAction extends org.apache.struts.action.Action {
    
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
            throws Exception
    {
        HttpSession session=request.getSession();
        //AppUtility appUtil=new AppUtility();
        //LoadUpInfo loadup=new LoadUpInfo();
        StatesDao sdao=new StatesDaoImpl();
        List stateList=sdao.getStates();
        session.setAttribute("stateSetupList", stateList);
        /*if(!appUtil.hasPriviledgeToAccessPage(session))
        return mapping.findForward("Nouserpriviledge");*/

        //loadup.getAllStatesAsObjects(session);
        //loadup.setParamAttributes(request);

        StatesActionForm stateForm=(StatesActionForm)form;
        States state=new States();
        //AjaxProcessor aproc=new AjaxProcessor();
        String requiredAction=stateForm.getActionName();
        String selectedStateCode=stateForm.getStateList();
        String stateName=stateForm.getName();
        if(stateName !=null)
        stateName=stateName.trim();
        state.setName(stateName);

        String stateCode=stateForm.getState_code();
        if(stateCode !=null)
        stateCode=stateCode.trim().toUpperCase();
        state.setState_code(stateCode);
        String modifyDisabled="true";
        String saveDisabled="false";
        session.removeAttribute("stateDetails");
        try
        {
            StatesDao dao=new StatesDaoImpl();
            if(requiredAction==null)
            {
                return mapping.findForward(SUCCESS);
            }
            else if(requiredAction.equals("stateDetails"))
            {
                state=sdao.getStateByStateCode(selectedStateCode);
                stateForm.setState_code(state.getState_code());
                stateForm.setName(state.getName());
                return mapping.findForward(SUCCESS);
                
            }
            else if(requiredAction.equals("save"))
            dao.saveState(state);
            else if(requiredAction.equals("modify"))
            {
                state.setState_code(stateForm.getState_code());
                dao.updateState(state);
            }
            else if(requiredAction.equals("delete"))
            {
                state.setState_code(stateForm.getState_code());
                dao.deleteState(state);
            }
            session.setAttribute("action",requiredAction);
            session.setAttribute("stateModifyDisabled", modifyDisabled);
            session.setAttribute("statesavedisabled", saveDisabled);
        }
        catch(Exception ex)
        {
            //ex.printStackTrace();
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
