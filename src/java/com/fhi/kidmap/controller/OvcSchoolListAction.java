/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.OvcSchool;
import com.fhi.kidmap.dao.OvcSchoolDao;
import com.fhi.kidmap.dao.OvcSchoolDaoImpl;
import com.fhi.nomis.nomisutils.AccessManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 *
 * @author HP USER
 */
public class OvcSchoolListAction extends Action
{
    final static String SUCCESS="success";
    final static String FAILURE="failure";


    @Override
public ActionForward execute(ActionMapping mapping,
ActionForm form,
HttpServletRequest request,
HttpServletResponse response)
throws IOException, ServletException
{
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        AccessManager acm=new AccessManager();
        boolean userInRole=acm.isUserInRole("007",appUtil.getCurrentUser(session));
        System.err.println("Inside schoolist before userInRole() .......");
        if(!userInRole)
        {
            request.setAttribute("errorMsg", appUtil.getAccessDeniedMessage());
            return mapping.findForward("accessDenied");
        }
        /*if(!appUtil.hasPriviledgeToAccessPage(session))
        return mapping.findForward("Nouserpriviledge");*/
OvcSchoolForm schForm=(OvcSchoolForm)form;
OvcSchool school=new OvcSchool();
school.setId(schForm.getId());
school.setAddress(schForm.getAddress());
school.setLga(schForm.getLga());
school.setName(schForm.getName());
school.setState(schForm.getState());
school.setType(schForm.getType());

String requiredAction=schForm.getActionName();

try{
    OvcSchoolDao dao=new OvcSchoolDaoImpl();
    LoadUpInfo loadup=new LoadUpInfo();
    if(requiredAction==null)
    {
        loadup.getSchoolsPerState(session);
        loadup.getLgasPerState(session);
        //loadup.getCbosPerState(session);
         session.setAttribute("schBtnSaveDisabled", "false");
         session.setAttribute("schBtnModifyDisabled", "true");
         
         return (mapping.findForward(SUCCESS));
    }
    else if (requiredAction.equals("schoolDetails"))
    {
        int id=schForm.getId();
        OvcSchool ovcSchool=dao.getSchoolDetails(id);
        if(ovcSchool !=null)
        {
            schForm.setAddress(ovcSchool.getAddress());
            schForm.setName(ovcSchool.getName());
            schForm.setLga(ovcSchool.getLga());
            schForm.setState(ovcSchool.getState());
            schForm.setType(ovcSchool.getType());
            return (mapping.findForward(SUCCESS));
        }
    }
    else if (requiredAction.equals("save"))
    {
         dao.saveOvcSchool(school);
         session.setAttribute("schBtnSaveDisabled", "true");
         session.setAttribute("schBtnModifyDisabled", "false");
         loadup.getSchoolsPerState(session);
    }
    else if (requiredAction.equals("modify"))
    {
        session.setAttribute("schBtnSaveDisabled", "true");
        session.setAttribute("schBtnModifyDisabled", "false");
        dao.updateOvcSchool(school);
    } 
    else if (requiredAction.equals("delete"))
    {
        session.setAttribute("schBtnSaveDisabled", "true");
        session.setAttribute("schBtnModifyDisabled", "false");
        dao.deleteOvcSchool(school);
    }
    loadup.getSchoolsPerState(session);
}
catch(Exception ex)
{
    System.err.println("Inside school catch block.......");
    ex.printStackTrace();
}
String target = null;

target = "success";

 form.reset(mapping, request);
return (mapping.findForward(target));
}

}
