/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.CboSetup;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.OvcSchool;
import com.fhi.kidmap.dao.CboSetupDao;
import com.fhi.kidmap.dao.CboSetupDaoImpl;
import com.fhi.kidmap.dao.OvcSchoolDao;
import com.fhi.kidmap.dao.OvcSchoolDaoImpl;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.TaskManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author HP USER
 */
public class AddOvcSchoolAction_1 extends Action{

    final static String SUCCESS="success";
    final static String FAILURE="failure";
    protected void insertSchool(OvcSchoolForm schForm)
    throws Exception {

        OvcSchool ovcSchool = new OvcSchool();

        ovcSchool.setId(schForm.getId());
        ovcSchool.setName(schForm.getName());
        ovcSchool.setType(schForm.getType());
        ovcSchool.setAddress(schForm.getAddress());
        ovcSchool.setLga(schForm.getLga());
        ovcSchool.setState(schForm.getState());
        String requiredAction=schForm.getActionName();

         try {
                OvcSchoolDao dao=new OvcSchoolDaoImpl();

                if (requiredAction.equals("save"))
                {
                    dao.saveOvcSchool(ovcSchool);
                }
                else if (requiredAction.equals("modify"))
                {
                    dao.updateOvcSchool(ovcSchool);
                }
                else if (requiredAction.equals("delete"))
                {
                    dao.deleteOvcSchool(ovcSchool);
                }
            /*OvcSchoolDao dao = new OvcSchoolDaoImpl();
            dao.saveOvcSchool(ovcSchool);*/

        } catch (Exception e) {
           
            throw new Exception(e);
        }



    }




     @Override
public ActionForward execute(ActionMapping mapping,
ActionForm form,
HttpServletRequest request,
HttpServletResponse response)
throws IOException, ServletException
     {
         HttpSession session=request.getSession();
         AppUtility appUtil=new AppUtility();
         
         OvcSchool ovcSchool = new OvcSchool();
         OvcSchoolForm schForm = (OvcSchoolForm) form;
         OvcSchoolDao schDao=new OvcSchoolDaoImpl();
         
        ovcSchool.setId(schForm.getId());
        ovcSchool.setName(schForm.getName());
        ovcSchool.setType(schForm.getType());
        ovcSchool.setAddress(schForm.getAddress());
        ovcSchool.setLga(schForm.getLga());
        ovcSchool.setState(schForm.getState());
        String requiredAction=schForm.getActionName();
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getLgasPerState(session);
        getSchools(schDao, session);
        AccessManager acm=new AccessManager();
        boolean userInRole=acm.isUserInRole("007",appUtil.getCurrentUser(session));
        if(!userInRole)
        {
            session.setAttribute("schBtnSaveDisabled", "true");
            session.setAttribute("schBtnModifyDisabled", "true");
            return (mapping.findForward(SUCCESS));
            
        }
        try {
            String msg=" ";
            /*if(TaskManager.isDatabaseLocked())
            {
                msg="System busy. Kindly try again later";
                request.setAttribute("msg", msg);
                return mapping.findForward(SUCCESS);
            }*/
                
                OvcSchoolDao dao=new OvcSchoolDaoImpl();
                if(requiredAction==null)
                {
                    
                    session.setAttribute("schBtnSaveDisabled", "false");
                    session.setAttribute("schBtnModifyDisabled", "true");
                    
                    return (mapping.findForward(SUCCESS));
                }
                else if (requiredAction.equals("schoolDetails"))
                {
                    int id=schForm.getSchoolList();
                    OvcSchool school=dao.getSchoolDetails(id);
                    if(school !=null)
                    {
                        schForm.setId(school.getId());
                        schForm.setState(school.getState());
                        schForm.setLga(school.getLga());
                        schForm.setName(school.getName());
                        schForm.setAddress(school.getAddress());                   
                        String type=school.getType();
                        if(type !=null)
                        type=type.trim();
                        schForm.setType(type);
                        session.setAttribute("schBtnSaveDisabled", "true");
                        session.setAttribute("schBtnModifyDisabled", "false");
                        getSchools(schDao, session);
                        return (mapping.findForward(SUCCESS));
                    }
                }
                else if (requiredAction.equals("save"))
                {
                    OvcSchool school=dao.getSchool(ovcSchool.getName());
                    if(school !=null)
                    {
                        ActionErrors errors = new ActionErrors();
                        errors.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionMessage("errors.database.error", "This School has already been registered"));
                        saveErrors(request, errors);
                        if (!errors.isEmpty())
                        {
                            saveErrors(request, errors);
                        }
                        return mapping.findForward(SUCCESS);
                    }
                    dao.saveOvcSchool(ovcSchool);
                    getSchools(schDao, session);
                    session.setAttribute("schBtnSaveDisabled", "false");
                    session.setAttribute("schBtnModifyDisabled", "true");
                }
                else if (requiredAction.equals("modify"))
                {
                    dao.updateOvcSchool(ovcSchool);
                    getSchools(schDao, session);
                    session.setAttribute("schBtnSaveDisabled", "false");
                    session.setAttribute("schBtnModifyDisabled", "true");
                }
                else if (requiredAction.equals("delete"))
                {
                    dao.deleteOvcSchool(ovcSchool);
                    getSchools(schDao, session);
                    session.setAttribute("schBtnSaveDisabled", "false");
                    session.setAttribute("schBtnModifyDisabled", "true");
                }
                getSchools(schDao, session);
            
        } catch (Exception e) {

            e.printStackTrace();
        }

form.reset(mapping, request);
return (mapping.findForward(SUCCESS));
}
public void getSchools(OvcSchoolDao schDao, HttpSession session)
{
    List schoolList=new ArrayList();
         try
         {
             AppUtility appUtil=new AppUtility();
             CboSetupDao csDao=new CboSetupDaoImpl();
             CboSetup setup=csDao.getCboSetup(appUtil.getCurrentUser(session));
             if(setup !=null)
             {
                 String stateCode=setup.getState_code();
                 schoolList=schDao.getSchoolList(stateCode);
             }
            session.setAttribute("schools", schoolList);
         }
         catch(Exception ex)
         {
             ex.printStackTrace();
         }
}
}
