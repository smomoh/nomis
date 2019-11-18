/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AjaxProcessor;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Lgas;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.Wards;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.LgaDao;
import com.fhi.kidmap.dao.LgaDaoImpl;
import com.fhi.kidmap.dao.OrganizationRecordsDao;
import com.fhi.kidmap.dao.OrganizationRecordsDaoImpl;
import com.fhi.kidmap.dao.WardDao;
import com.fhi.kidmap.dao.WardDaoImpl;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.NomisConstant;
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
 * @author COMPAQ USER
 */
public class WardAction extends org.apache.struts.action.Action {
    
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
        WardActionForm wardForm=(WardActionForm)form;
        Wards ward=new Wards();

        AjaxProcessor aproc=new AjaxProcessor();
        AppUtility appUtil=new AppUtility();
        HttpSession session=request.getSession();
        AccessManager acm=new AccessManager();
        boolean userInRole=acm.isUserInRole("008",appUtil.getCurrentUser(session));
        if(!userInRole)
        {
            session.setAttribute("wardModifyDisabled", "true");
            session.setAttribute("wardsavedisabled", "true");
            return mapping.findForward(SUCCESS);
            //request.setAttribute("errorMsg", appUtil.getAccessDeniedMessage());
            //return mapping.findForward("accessDenied");
        }
                
        String lgaId=wardForm.getLga_code();
        String cboCode=wardForm.getCbo_code();
        String wardListCode=wardForm.getWardList();
        String wardCode=wardForm.getWard_code();
        //System.err.println("cboCode is "+cboCode);
        String requiredAction=wardForm.getActionName();
        if(wardCode !=null && cboCode !=null && requiredAction !=null && (requiredAction.equals("save") || requiredAction.equals("modify")))
        {
            String[] codeArray=cboCode.split("/");
            wardCode=(codeArray[0]+"/"+lgaId+"/"+codeArray[2]+"/"+wardCode.trim()).toUpperCase();
            wardCode=appUtil.removeSpacesFromString(wardCode);
        }
        //wardCode=cboCode+"/"+wardCode.trim().toUpperCase();
        String wardName=wardForm.getWard_name();
        if(wardName !=null)
        wardName=wardName.trim();

        ward.setCbo_code(cboCode);
        ward.setWard_code(wardCode);
        ward.setWard_name(wardName);

        String modifyDisabled="true";
        String saveDisabled="false";
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getLgasPerState(session);

        
        
        try
        {
            WardDao dao=new WardDaoImpl();

            if(requiredAction==null || requiredAction.equalsIgnoreCase("refresh"))
            {
                List list=new ArrayList();
                wardForm.reset(mapping, request);
                wardForm.setCbo_code(cboCode);
                session.setAttribute("cbolistperlga", list);
                request.setAttribute("wardlistpercbo", list);
                session.setAttribute("wardModifyDisabled", modifyDisabled);
                session.setAttribute("wardsavedisabled", saveDisabled);
                return mapping.findForward(SUCCESS);
            }
            else if(requiredAction.equals("cboList"))
            {
                form.reset(mapping, request);
                OrganizationRecordsDao orDao=new OrganizationRecordsDaoImpl();
                if(lgaId !=null)
                lgaId=lgaId.trim();
                List cboList=orDao.getListOfOrganizationsAssignedToLga(lgaId);//.getOrganizationList(lgaId);
                session.setAttribute("cbolistperlga", cboList);
                setDefaultValues(wardForm,request,lgaId,cboCode);
                session.setAttribute("wardlistpercbo", new ArrayList());
                return mapping.findForward(SUCCESS);
            }
            else if(requiredAction.equals("wardList"))
            {
                form.reset(mapping, request);
                setDefaultValues(wardForm,request,lgaId,cboCode);
                return mapping.findForward(SUCCESS);
            }
            else if(requiredAction.equals("wardDetails"))
            {
                String wardDetail=aproc.getWardDetails(wardListCode);
                try
                {
                    if(wardDetail !=null)
                    {
                        String[] values=wardDetail.split(";");
                        String[] wardCodes=null;
                        if(values[0].indexOf("/") != -1)
                        {
                            wardCodes=values[0].split("/");
                            wardForm.setWard_code(wardCodes[wardCodes.length-1].trim());
                            wardForm.setHiddenWardCode(values[0]);
                        }
                        wardForm.setWard_name(values[1].trim());
                    }
                    setDefaultValues(wardForm,request,lgaId,cboCode);
                    session.setAttribute("wardModifyDisabled", "false");
                    session.setAttribute("wardsavedisabled", "true");
                }
                catch(Exception ex)
                {
                    ActionErrors errors = new ActionErrors();
                    errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionMessage("errors.database.error","Error retrieving ward details"));
                    if (!errors.isEmpty())
                    {
                        saveErrors(request, errors);
                    }
                }
                wardForm.setCbo_code(cboCode);
                return mapping.findForward(SUCCESS);
            }
            else if(requiredAction.equals("save"))
            {
                //System.err.println("ward.getWard_code() is "+ward.getWard_code()+" NomisConstant.COMMUNITYCODE_LENGTH: "+NomisConstant.COMMUNITYCODE_LENGTH);
                if(ward.getWard_code() !=null && ward.getWard_code().length()==NomisConstant.COMMUNITYCODE_LENGTH)
                {
                    dao.saveWard(ward);
                }
                else
                {
                    ActionErrors errors = new ActionErrors();
                    errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionMessage("errors.database.error", "Invalid Community/Ward code. Unable to save record"));
                    if (!errors.isEmpty())
                    {
                        saveErrors(request, errors);
                    }
                    return mapping.findForward(FAILURE);
                }
                session.setAttribute("wardModifyDisabled", "true");
                session.setAttribute("wardsavedisabled", "false");
            }
            else if(requiredAction.equals("modify"))
            {
                ward.setWard_code(wardForm.getHiddenWardCode());
                dao.updateWard(ward);
                session.setAttribute("wardModifyDisabled", "true");
                session.setAttribute("wardsavedisabled", "false");
            }
            else if(requiredAction.equals("delete"))
            {
                
                HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
                List hheList=hhedao.getListOfHouseholdEnrollmentByCommunityCode(wardForm.getHiddenWardCode());
                if(hheList ==null || hheList.isEmpty())
                {
                    ward.setWard_code(wardForm.getHiddenWardCode());
                    dao.deleteWard(ward);
                    session.setAttribute("wardModifyDisabled", "true");
                    session.setAttribute("wardsavedisabled", "false");
                    form.reset(mapping, request);  
                }
                else
                {
                    ActionErrors errors = new ActionErrors();
                    errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionMessage("errors.database.error", "This object is associated with data value. Unable to delete."));
                    if (!errors.isEmpty())
                    {
                        saveErrors(request, errors);
                    }
                    return mapping.findForward(FAILURE);
                }
            }
        }
        catch(Exception ex)
        {
            ActionErrors errors = new ActionErrors();
            errors.add(ActionErrors.GLOBAL_MESSAGE,
            new ActionMessage("errors.database.error",
            "Could not update database. Ensure the Ward code is not already in use"));
            if (!errors.isEmpty())
            {
                saveErrors(request, errors);
            }
            return mapping.findForward(FAILURE);
        }
        form.reset(mapping, request);
        setDefaultValues(wardForm,request,lgaId,cboCode);
        return mapping.findForward(SUCCESS);
    }
    private void setDefaultValues(WardActionForm wardForm,HttpServletRequest request,String lgaCode,String cboCode)
    {
        wardForm.setLga_code(lgaCode);
        wardForm.setCbo_code(cboCode);
        getWardList(lgaCode,cboCode,request);
    }
    private void getWardList(String lgaCode,String cboCode,HttpServletRequest request)
    {
        WardDao wdao = new WardDaoImpl();
        LgaDao lgaDao=new LgaDaoImpl();

        try
        {
                Lgas lga=(Lgas)lgaDao.getLgaByCode(lgaCode);
                String stateAndLgaCodes=lga.getState_code()+"/"+lgaCode;
                List wardList=wdao.getWardByLgaCodeAndCboId(stateAndLgaCodes, cboCode);//.getWardByCboId(cboCode);
                request.getSession().setAttribute("wardlistpercbo", wardList);
        }
        catch(Exception ex)
        {
            System.err.println("Error retrieving ward list");
        }
    }
}
