/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.CategoryCombination;
import com.fhi.kidmap.dao.CategoryCombinationDao;
import com.fhi.kidmap.dao.CategoryCombinationDaoImpl;
import com.fhi.kidmap.dao.IndicatorDao;
import com.fhi.kidmap.dao.IndicatorDaoImpl;
import com.fhi.kidmap.dao.ReportQueryManagerDao;
import com.fhi.kidmap.dao.ReportQueryManagerDaoImpl;
import com.nomis.business.Indicator;
import com.nomis.business.ReportQueryManager;
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
public class ReportQueryManagerAction extends org.apache.struts.action.Action {

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
        ReportQueryManagerForm rqmf=(ReportQueryManagerForm)form;
        IndicatorDao idao=new IndicatorDaoImpl();
        CategoryCombinationDao ccdao=new CategoryCombinationDaoImpl();
        ReportQueryManagerDao rqmdao=new ReportQueryManagerDaoImpl();
        rqmf.setIndicatorList(idao.getAllIndicators());
        rqmf.setCategoryCombinationList(ccdao.getCategoryCombinationList());
        
        ReportQueryManager rqm=new ReportQueryManager();
        String indicatorName=null;
        String categoryCombinationName=null;
        String indicatorQuery=null;
        String categoryCombinationQuery=null;
        String queryPart=" group by hhe.cboCode, MONTH(s.serviceDate), YEAR(s.serviceDate)";
        String indicatorId=rqmf.getIndicatorId();
        String catComboId=rqmf.getCategoryComboId();
        rqm.setCategoryComboId(rqmf.getCategoryComboId());
        rqm.setDescription(rqmf.getDescription());
        
        rqm.setIndicatorId(rqmf.getIndicatorId());
        Indicator indicator=idao.getIndicator(indicatorId);
        CategoryCombination catCombo=ccdao.getCategoryCombination(catComboId);
        if(indicator !=null)
        {
            indicatorName=indicator.getIndicatorName();
            indicatorQuery=indicator.getQuery();
        }
        if(catCombo !=null)
        {
            categoryCombinationName=catCombo.getCategoryComboName();
            categoryCombinationQuery=catCombo.getQuery();
        }
        rqm.setName(indicatorName+categoryCombinationName);
        rqm.setProgramArea(rqmf.getProgramArea());
        rqm.setQuery(indicatorQuery+" and "+categoryCombinationQuery+queryPart);
        rqm.setType(rqmf.getType());
        
        HttpSession session=request.getSession();
        session.setAttribute("rqmSaveDisabled", "false");
        session.setAttribute("rqmModifyDisabled", "true");
        String requiredAction=rqmf.getActionName();
        List rqmList=rqmdao.getAllReportQueryManagers();
                
        
        if(requiredAction==null)
        {
            rqmf.reset(mapping, request);
            rqmf.setReportQueryList(rqmList);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("details"))
        {
            rqm=rqmdao.getReportQueryManager(rqmf.getUniqueId());
            if(rqm !=null)
            {
                rqmf.setCategoryComboId(rqm.getCategoryComboId());
                rqmf.setDescription(rqm.getDescription());
                rqmf.setIndicatorId(rqm.getIndicatorId());
                rqmf.setName(rqm.getName());
                rqmf.setProgramArea(rqm.getProgramArea());
                rqmf.setQuery(rqm.getQuery());
                rqmf.setType(rqm.getType());
                rqmf.setHiddenId(rqm.getUniqueId());
                session.setAttribute("rqmSaveDisabled", "true");
                session.setAttribute("rqmModifyDisabled", "false");
                return mapping.findForward(SUCCESS);
            }
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            rqmdao.saveReportQueryManager(rqm);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            rqm.setUniqueId(rqmf.getHiddenId());
            rqmdao.updateReportQueryManager(rqm);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            rqm.setUniqueId(rqmf.getHiddenId());
            rqmdao.deleteReportQueryManager(rqm);
        }
        rqmf.reset(mapping, request);
        rqmList=rqmdao.getAllReportQueryManagers();
        rqmf.setReportQueryList(rqmList);
        return mapping.findForward(SUCCESS);
    }
}
