/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.OrganizationUnitHirachy;
import com.fhi.kidmap.dao.OrganizationUnitHirachyDao;
import com.fhi.kidmap.dao.OrganizationUnitHirachyDaoImpl;
import com.fhi.nomis.nomisutils.AppUtility;
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
public class OrganizationUnitHirachyAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "newhirachypage";

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
        OrganizationUnitHirachyForm hirachyform=(OrganizationUnitHirachyForm)form;
        AppUtility appUtil=new AppUtility();
        OrganizationUnitHirachyDao ouhdao=new OrganizationUnitHirachyDaoImpl();
        HttpSession session=request.getSession();
        String requiredAction=hirachyform.getActionName();
        String hirachyId=hirachyform.getHirachyId();
        String hirachyName=hirachyform.getHirachyName();
        int level=hirachyform.getLevel();
        String dateModified=appUtil.getCurrentDate();
        String id=request.getParameter("id");
        if(id !=null)
        requiredAction=id;
        OrganizationUnitHirachy ouh=new OrganizationUnitHirachy();
        ouh.setDateModified(dateModified);
        ouh.setHirachyId(hirachyId);
        ouh.setHirachyName(hirachyName);
        ouh.setLevel(level);
        List hirachyList=ouhdao.getAllOrganizationUnitHirachy();
        session.setAttribute("hirachyList", hirachyList);
        session.setAttribute("ouhSavedisabled", "false");
        session.setAttribute("ouhModifyDisabled", "true");
        if(requiredAction==null)
        {
            hirachyform.reset(mapping, request);
            hirachyform.setLevel(hirachyList.size()+1);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            ouhdao.saveOrganizationUnitHirachy(ouh);
        }
        else if(requiredAction.equalsIgnoreCase("ed"))
        {
            hirachyName=request.getParameter("nid");
            if(hirachyName !=null)
            {
                hirachyName=hirachyName.trim();
                ouh=ouhdao.getOrganizationUnitHirachyByName(hirachyName);
                if(ouh !=null)
                {
                    hirachyform.setHirachyId(ouh.getHirachyId());
                    hirachyform.setHirachyName(hirachyName);
                    hirachyform.setLevel(ouh.getLevel());
                    session.setAttribute("ouhSavedisabled", "true");
                    session.setAttribute("ouhModifyDisabled", "false");
                }
                return mapping.findForward(SUCCESS);
            }
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            //System.err.println("new ouh.getHirachyId() and ouh.getHirachyName() are "+ouh.getHirachyId()+" and "+ouh.getHirachyName()+" and level is "+ouh.getLevel());
            ouhdao.updateOrganizationUnitHirachy(ouh);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            ouhdao.deleteOrganizationUnitHirachy(ouh);
        }
        hirachyList=ouhdao.getAllOrganizationUnitHirachy();
        session.setAttribute("hirachyList", hirachyList);
        hirachyform.reset(mapping, request);
        hirachyform.setLevel(hirachyList.size()+1);
        return mapping.findForward(SUCCESS);
    }
}
