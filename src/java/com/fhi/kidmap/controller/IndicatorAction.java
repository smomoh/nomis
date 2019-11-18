/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.kidmap.dao.IndicatorDao;
import com.fhi.kidmap.dao.IndicatorDaoImpl;
import com.nomis.business.Indicator;
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
public class IndicatorAction extends org.apache.struts.action.Action {
    
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
        IndicatorForm indForm=(IndicatorForm)form;
        IndicatorDao idao=new IndicatorDaoImpl();

        String hiddenId=indForm.getHiddenId();
        String indicatorId=indForm.getIndicatorId();
        String indicatorName=indForm.getIndicatorName();


        Indicator indicator=new Indicator();
        indicator.setIndicatorName(indicatorName);
        indicator.setIndicatorId(indicatorId);
        indicator.setDescription(indForm.getDescription());
        indicator.setQuery(indForm.getQuery());
        indicator.setProgramArea(indForm.getProgramArea());
        indicator.setType(indForm.getType());
        
        String requiredAction=indForm.getActionName();
        HttpSession session=request.getSession();
        List indicatorList=idao.getAllIndicators();
        session.setAttribute("indicatorList", indicatorList);
        session.setAttribute("indicatorSavedisabled", "false");
        session.setAttribute("indicatorModifyDisabled", "true");
        if(requiredAction==null)
        {
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("details"))
        {
            indicator=idao.getIndicator(indicatorId);
            if(indicator !=null)
            {
                indForm.setHiddenId(indicatorId);
                indForm.setIndicatorName(indicator.getIndicatorName());
                indForm.setDescription(indicator.getDescription());
                indForm.setQuery(indicator.getQuery());
                indForm.setProgramArea(indicator.getProgramArea());
                indForm.setType(indicator.getType());
                session.setAttribute("indicatorSavedisabled", "true");
                session.setAttribute("indicatorModifyDisabled", "false");
            }
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            if(idao.getIndicator(indicatorName) ==null)
            idao.saveIndicator(indicator);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            indicator.setIndicatorId(hiddenId);
            Indicator duplicateIndicator=idao.getIndicator(indicatorName);
            if(duplicateIndicator !=null)
            {
               if(!duplicateIndicator.getIndicatorId().equalsIgnoreCase(hiddenId))
               return mapping.findForward(SUCCESS);
            }
            idao.updateIndicator(indicator);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            indicator.setIndicatorId(hiddenId);
            idao.deleteIndicator(indicator);
        }
        indForm.reset(mapping, request);
        indicatorList=idao.getAllIndicators();
        session.setAttribute("indicatorList", indicatorList);
        return mapping.findForward(SUCCESS);
    }
}
