/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DatabaseUtilities;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.TaskManager;
import java.util.ArrayList;
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
public class DiskDefragmenterAction extends org.apache.struts.action.Action {

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
        DiskDefragmenterForm ddf=(DiskDefragmenterForm)form;
        String requiredAction=ddf.getActionName();
        HttpSession session=request.getSession();
        AccessManager acm=new AccessManager();
        AppUtility appUtil=new AppUtility();
        
        String[] tableCodes=ddf.getTableNames();
        
        int sequence=ddf.getSequence();
        DatabaseUtilities dbUtils=new DatabaseUtilities();
        request.setAttribute("tableList", dbUtils.getTableList());    
        String msg=" ";
        request.setAttribute("dbdefragmentDisabled", "false");
        request.setAttribute("dbdefragmentmsg", msg);
        if(!acm.isUserInRole("superuser", appUtil.getCurrentUser(session)))
        {
            request.setAttribute("dbdefragmentDisabled", "true");
            return mapping.findForward(SUCCESS);
        }
        if(TaskManager.isDatabaseLocked())
        {
            msg="System busy. Kindly try again later";
            return mapping.findForward(SUCCESS);
        }
        if(requiredAction==null)
        {
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("defragment"))
        {
            msg="<label style='color: red'>No table processed</label>";
            TaskManager.setDatabaseLocked(true);
            List tableList=new ArrayList();
            if(tableCodes !=null)
            {
                for(int i=0; i<tableCodes.length; i++)
                {
                    tableList.add(tableCodes[i]);
                }
                tableList.add("dlr");
                tableList.add("brg");
            }
            msg=dbUtils.defragmentTable(tableList, sequence);  
            
            TaskManager.setDatabaseLocked(false);
            request.setAttribute("dbdefragmentmsg", msg);
        }
        TaskManager.setDatabaseLocked(false);
        ddf.reset(mapping, request);
        request.setAttribute("dbdefragmentmsg", msg);
        return mapping.findForward(SUCCESS);
    }
}
