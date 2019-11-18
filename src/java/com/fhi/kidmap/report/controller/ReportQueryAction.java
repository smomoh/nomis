/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.ReportQuery;
import com.fhi.kidmap.dao.ReportQueryDao;
import com.fhi.kidmap.dao.ReportQueryDaoImpl;
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
 * @author Siaka
 */
public class ReportQueryAction extends org.apache.struts.action.Action {

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
        ReportQueryForm rqForm=(ReportQueryForm)form;
        ReportQueryDao rqDao=new ReportQueryDaoImpl();
        ReportQuery rq=new ReportQuery();
        HttpSession session=request.getSession();

        String queryId=rqForm.getQueryId();
        String hiddenId=rqForm.getHiddenId();
        rq.setQueryId(queryId);
        rq.setQueryCriteria(rqForm.getQueryCriteria());
        rq.setQueryDescription(rqForm.getQueryDescription());
        rq.setQueryName(rqForm.getQueryName());
        rq.setQueryString(rqForm.getQueryString());
        rq.setQueryType(rqForm.getQueryType());
        List queryList=rqDao.getReportQueries();
        if(queryList==null)
        queryList=new ArrayList();
        session.setAttribute("queryList", queryList);
        String requiredAction=rqForm.getActionName();
        session.setAttribute("reportQuerySaveBtnDisabled", "false");
        session.setAttribute("reportQueryModifyBtnDisabled", "true");
        if(requiredAction==null)
        {
            rqForm.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("details"))
        {
            rq=rqDao.getReportQuery(queryId);
            rqForm.reset(mapping, request);
            if(rq !=null)
            {
                rqForm.setHiddenId(queryId);
                rqForm.setQueryCriteria(rq.getQueryCriteria());
                rqForm.setQueryDescription(rq.getQueryDescription());
                rqForm.setQueryName(rq.getQueryName());
                rqForm.setQueryString(rq.getQueryString());
                rqForm.setQueryType(rq.getQueryType());
                session.setAttribute("reportQuerySaveBtnDisabled", "true");
                session.setAttribute("reportQueryModifyBtnDisabled", "false");
            }
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            ReportQuery rq2=rqDao.getReportQueriesByQueryName(rq.getQueryName());
            if(rq2 !=null)
            {
                ActionErrors errors = new ActionErrors();
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                new ActionMessage("errors.queryName.exist"));
                if (!errors.isEmpty())
                saveErrors(request, errors);
            }
            else
            rqDao.saveReportQuery(rq);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            rq.setQueryId(hiddenId);
            rqDao.updateReportQuery(rq);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            rq.setQueryId(hiddenId);
            rqDao.deleteReportQuery(rq);
        }
        queryList=rqDao.getReportQueries();
        if(queryList==null)
        queryList=new ArrayList();
        session.setAttribute("queryList", queryList);
        rqForm.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
}
