/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.Indicators;
import com.fhi.kidmap.dao.IndicatorsDao;
import com.fhi.kidmap.dao.IndicatorsDaoImpl;
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
public class IndicatorsAction extends org.apache.struts.action.Action {

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
            throws Exception {
        IndicatorsForm indicatorsForm=(IndicatorsForm)form;
        Indicators indicator=new Indicators();
        HttpSession session=request.getSession();
        String indicatorId=indicatorsForm.getDataElementId();
        int hiddenId=indicatorsForm.getDataElementHiddenId();
        String indicatorName=indicatorsForm.getIndicatorName();
        String indicatorType=indicatorsForm.getIndicatorType();
        String indicatorSubtype=indicatorsForm.getIndicatorSubtype();
        String query=indicatorsForm.getQuery();
        String queryCriteria=indicatorsForm.getQueryCriteria();
        String categoryOptionCombo=indicatorsForm.getCategoryOptionCombo();
        String requiredAction=indicatorsForm.getActionName();

        indicator.setIndicatorId(indicatorId);
        indicator.setIndicatorName(indicatorName);
        indicator.setIndicatorType(indicatorType);
        indicator.setIndicatorSubtype(indicatorSubtype);
        indicator.setQuery(query);
        indicator.setQueryCriteria(queryCriteria);
        indicator.setCategoryOptionCombo(categoryOptionCombo);
        IndicatorsDao idao=new IndicatorsDaoImpl();

        try
        {
            if(requiredAction==null)
            {
                indicatorsForm.reset(mapping, request);
                session.setAttribute("indicatorSaveDisabled", "false");
                session.setAttribute("indicatorModifyDisabled", "true");
                //return mapping.findForward(SUCCESS);
            }
            else if(requiredAction.equals("indicatorDetails"))
            {
                int id=Integer.parseInt(indicatorsForm.getIndicatorList());
                indicatorsForm.reset(mapping, request);
                indicator=idao.getIndicator(id);
                if(indicator == null)
                indicator=new Indicators();
                indicatorsForm.setDataElementHiddenId(indicator.getId());
                indicatorsForm.setDataElementId(indicator.getIndicatorId());
                indicatorsForm.setIndicatorName(indicator.getIndicatorName());
                indicatorsForm.setIndicatorType(indicator.getIndicatorType());
                indicatorsForm.setIndicatorSubtype(indicator.getIndicatorSubtype());
                indicatorsForm.setQuery(indicator.getQuery());
                indicatorsForm.setCategoryOptionCombo(indicator.getCategoryOptionCombo());
                session.setAttribute("indicatorSaveDisabled", "true");
                session.setAttribute("indicatorModifyDisabled", "false");
            }
            else if(requiredAction.equals("save"))
            {
                idao.saveIndicators(indicator);
                session.setAttribute("indicatorSaveDisabled", "false");
                session.setAttribute("indicatorModifyDisabled", "true");
                indicatorsForm.reset(mapping, request);
            }
            else if(requiredAction.equals("modify"))
            {
                indicator.setId(hiddenId);
                idao.updateIndicators(indicator);
                session.setAttribute("indicatorSaveDisabled", "false");
                session.setAttribute("indicatorModifyDisabled", "true");
                indicatorsForm.reset(mapping, request);
            }
            else if(requiredAction.equals("delete"))
            {
                indicator.setId(hiddenId);
                idao.deleteIndicators(indicator);
                session.setAttribute("indicatorSaveDisabled", "false");
                session.setAttribute("indicatorModifyDisabled", "true");
                indicatorsForm.reset(mapping, request);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        List indicatorList=idao.getIndicators();
        session.setAttribute("indicators", indicatorList);
        return mapping.findForward(SUCCESS);
    }
}
