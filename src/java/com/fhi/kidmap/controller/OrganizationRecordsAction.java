/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AjaxProcessor;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.DhisOrgUnitLink;
import com.fhi.kidmap.business.Lgas;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.dao.DhisOrgUnitLinkDao;
import com.fhi.kidmap.dao.DhisOrgUnitLinkDaoImpl;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.OrganizationRecordsDao;
import com.fhi.kidmap.dao.OrganizationRecordsDaoImpl;
import com.fhi.nomis.nomisutils.AccessManager;
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
public class OrganizationRecordsAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String FAILURE="failure";
    
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


        OrganizationRecords orgRecords=new OrganizationRecords();
        DhisOrgUnitLink dhisOrgUnitLink=new DhisOrgUnitLink();
        OrganizationRecordsForm orgForm=(OrganizationRecordsForm)form;
        OrganizationRecordsDao orgDao=new OrganizationRecordsDaoImpl();
        DhisOrgUnitLinkDao dhisLinkDao=new DhisOrgUnitLinkDaoImpl();


        AppUtility appUtil=new AppUtility();
        HttpSession session=request.getSession();
        AccessManager acm=new AccessManager();
        boolean userInRole=acm.isUserInRole("008",appUtil.getCurrentUser(session));
        if(!userInRole)
        {
            request.setAttribute("errorMsg", appUtil.getAccessDeniedMessage());
            return mapping.findForward("accessDenied");
        }
        setInitialParameters(orgForm,request);
        
        orgRecords.setOrgType(orgForm.getOrgType());
        orgRecords.setOrgSubtype(orgForm.getOrgSubtype());
        String stateCode=orgForm.getStateCode();
        getLgaListPerState(request,stateCode);
        if(stateCode !=null)
        {
            stateCode=stateCode.trim();
            stateCode=stateCode.toUpperCase();
        }
        orgRecords.setState(stateCode);
        
        String lgaCode=orgForm.getLgaCode();
        if(lgaCode !=null)
        {
            lgaCode=lgaCode.trim();
            lgaCode=lgaCode.toUpperCase();
        }
        orgRecords.setLga(lgaCode);
        
        String code=orgForm.getOrgCode();
        if(code !=null)
        {
            code=code.trim();
            code=code.toUpperCase();
        }
        
        String orgCode=stateCode+"/"+lgaCode+"/"+code;
        orgCode=appUtil.removeSpacesFromString(orgCode);
        orgRecords.setOrgCode(orgCode);
        String orgName=orgForm.getOrgName();
        if(orgName !=null)
        orgName=orgName.trim();
        orgRecords.setOrgName(orgName);

        orgRecords.setAddress(orgForm.getAddress());
        orgRecords.setContactEmail1(orgForm.getContactEmail1());
        orgRecords.setContactEmail2(orgForm.getContactEmail2());
        orgRecords.setContactEmail2(orgForm.getContactEmail2());
        orgRecords.setContactName1(orgForm.getContactName1());
        orgRecords.setContactName2(orgForm.getContactName2());
        orgRecords.setContactName3(orgForm.getContactPhone3());
        orgRecords.setContactPhone1(orgForm.getContactPhone1());
        orgRecords.setContactPhone2(orgForm.getContactPhone2());
        orgRecords.setContactPhone3(orgForm.getContactPhone3());
        //System.err.println("orgForm.isDataEntryAllowed() is "+orgForm.isDataEntryAllowed());
        dhisOrgUnitLink.setOrgCode(orgCode);
        dhisOrgUnitLink.setDhisId(orgForm.getDhisId());
        double latitude=orgForm.getLatitude();
        double longitude=orgForm.getLongitude();
        orgRecords.setLatitude(latitude);
        orgRecords.setLongitude(longitude);

        //int id=orgForm.getOrgList();
        String[] services=orgForm.getServices();
        String[] assignedLgaArray=orgForm.getAssignedLgas();
        String allServices=concatArrayValues(services);

        String concatenatedLgas=concatArrayValues(assignedLgaArray);
        orgRecords.setLga(concatenatedLgas);
        orgRecords.setServices(allServices);
             
        String requiredAction=orgForm.getActionName();

        //String ajaxRequest=request.getParameter("qparam");
           
        if(requiredAction==null)
        {
            form.reset(mapping, request);
            OrganizationRecords orgRecords2;
            
            List orgUnitsNotInDhisLinkTableList=orgDao.getOrgUnitsNotInDhisLinkTable();
            for(int i=0; i<orgUnitsNotInDhisLinkTableList.size(); i++)
            {
                orgRecords2=(OrganizationRecords)orgUnitsNotInDhisLinkTableList.get(i);
                dhisOrgUnitLink.setOrgCode(orgRecords2.getOrgCode());
                dhisLinkDao.saveDhisOrgUnitLink(dhisOrgUnitLink);
            }
            setInitialParameters(orgForm,request);
            session.setAttribute("orgSaveDiabled", "false");
            session.setAttribute("orgModifyDiabled", "true");
            return mapping.findForward(SUCCESS);
        }
        try
        {
            if (requiredAction.equals("orgDetails"))
            {
                String selectedOrgCode=orgForm.getOrgList();
                form.reset(mapping, request);
                setInitialParameters(orgForm,request);
                
                orgRecords=orgDao.getOrganizationRecord(selectedOrgCode);
                if(orgRecords !=null)
                {
                    orgForm.setAddress(orgRecords.getAddress());
                    orgForm.setOrgName(orgRecords.getOrgName());
                    orgForm.setOrgType(orgRecords.getOrgType());
                    orgForm.setOrgSubtype(orgRecords.getOrgSubtype());
                    orgForm.setState(orgRecords.getState());
                    String[] orgCodeArray=orgRecords.getOrgCode().split("/");
                    orgForm.setLgaCode(orgCodeArray[1]);
                    
                    String lgas=orgRecords.getLga();
                    String[] assignedLgaArray2=null;
                    if(lgas !=null)
                    {
                        assignedLgaArray2=lgas.split(",");
                    }
                    orgForm.setAssignedLgas(assignedLgaArray2);
                    String service=orgRecords.getServices();
                    String[] serviceArray={};
                    if(service !=null)
                    serviceArray=service.split(",");
                    orgForm.setServices(serviceArray);
                    orgForm.setContactEmail1(orgRecords.getContactEmail1());
                    orgForm.setContactEmail2(orgRecords.getContactEmail2());
                    orgForm.setContactEmail3(orgRecords.getContactEmail3());
                    orgForm.setContactName1(orgRecords.getContactName1());
                    orgForm.setContactName2(orgRecords.getContactName2());
                    orgForm.setDhisId(((DhisOrgUnitLink)(dhisLinkDao.getDhisOrgUnitLink(orgRecords.getOrgCode()))).getDhisId());
                    
                    orgForm.setContactPhone1(orgRecords.getContactPhone1());
                    orgForm.setContactPhone2(orgRecords.getContactPhone2());
                    orgForm.setContactPhone3(orgRecords.getContactPhone3());
                    orgForm.setHiddenOrgCode(orgRecords.getOrgCode());
                    
                    orgForm.setOrgCode(orgCodeArray[orgCodeArray.length-1].trim());
                    orgForm.setLatitude(orgRecords.getLatitude());
                    orgForm.setLongitude(orgRecords.getLongitude());
                    session.setAttribute("orgSaveDiabled", "true");
                    session.setAttribute("orgModifyDiabled", "false");
                }
                return mapping.findForward(SUCCESS);
            }
            else if(requiredAction.equalsIgnoreCase("save"))
            {
                OrganizationRecords orgRecords2=null;
                orgRecords2=orgDao.getOrganizationRecord(orgCode);
                if(orgRecords2 == null)
                {
                    String availableCode=orgDao.getOrganizationCode(orgName);
                    if(availableCode == null || (!(availableCode.substring(0,availableCode.lastIndexOf("/"))).equalsIgnoreCase(orgCode.substring(0,orgCode.lastIndexOf("/")))))
                    {
                        orgDao.saveOrgRecords(orgRecords);
                        dhisLinkDao.saveDhisOrgUnitLink(dhisOrgUnitLink);
                    }
                    else
                    {
                        ActionErrors errors = new ActionErrors();
                        errors.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionMessage("errors.database.error", "This Organization Name is already in use"));
                        saveErrors(request, errors);
                        if (!errors.isEmpty())
                        {
                            saveErrors(request, errors);
                        }
                        return mapping.findForward(SUCCESS);
                    }
                    session.setAttribute("orgSaveDiabled", "false");
                    session.setAttribute("orgModifyDiabled", "true");
                }
                else
                {
                    ActionErrors errors = new ActionErrors();
                    errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("errors.database.error", "This Organization Code is already in use"));
                    saveErrors(request, errors);
                    if (!errors.isEmpty())
                    {
                        saveErrors(request, errors);
                    }
                    return mapping.findForward(SUCCESS);
                }
            }
            
            else if(requiredAction.equalsIgnoreCase("modify"))
            {
                try
                {
                    orgRecords.setOrgCode(orgForm.getHiddenOrgCode());
                    dhisOrgUnitLink.setOrgCode(orgForm.getHiddenOrgCode());
                    orgDao.updateOrgRecords(orgRecords);
                    dhisLinkDao.updateDhisOrgUnitLink(dhisOrgUnitLink);
                    session.setAttribute("orgSaveDiabled", "false");
                    session.setAttribute("orgModifyDiabled", "true");
                }
                catch(Exception ex)
                {
                    ActionErrors errors = new ActionErrors();
                    errors.add(ActionErrors.GLOBAL_MESSAGE,
                    new ActionMessage("errors.database.error", "Could not update database. Ensure all entry fields are correctly filled"));
                    if (!errors.isEmpty())
                    {
                        saveErrors(request, errors);
                    }
                    setInitialParameters(orgForm,request);
                    return mapping.findForward(FAILURE);
                }
            }
            else if(requiredAction.equalsIgnoreCase("delete"))
            {
                HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
                List hheList=hhedao.getListOfHouseholdEnrollmentByOrgCode(orgForm.getHiddenOrgCode());
                if(hheList ==null || hheList.isEmpty())
                {
                    orgRecords.setOrgCode(orgForm.getHiddenOrgCode());
                    dhisOrgUnitLink.setOrgCode(orgForm.getHiddenOrgCode());
                    orgDao.deleteOrgRecords(orgRecords);
                    dhisLinkDao.deleteDhisOrgUnitLink(dhisOrgUnitLink);
                    session.setAttribute("orgSaveDiabled", "false");
                    session.setAttribute("orgModifyDiabled", "true");
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
            else if(requiredAction.equalsIgnoreCase("refresh"))
            {
                setInitialParameters(orgForm,request);
                return mapping.findForward(SUCCESS);
            }
        }
        catch(Exception ex)
        {
            ActionErrors errors = new ActionErrors();
            errors.add(ActionErrors.GLOBAL_MESSAGE,
            new ActionMessage("errors.database.error", "Could not update database. Ensure all entry fields are correctly filled"));
            if (!errors.isEmpty())
            {
                saveErrors(request, errors);
            }
            System.err.println(ex.getMessage());
            setInitialParameters(orgForm,request);
            return mapping.findForward(FAILURE);
        }
        form.reset(mapping, request);
        setInitialParameters(orgForm,request);
        return mapping.findForward(SUCCESS);
    }
    public void setInitialParameters(OrganizationRecordsForm orgForm,HttpServletRequest request)
    {
            HttpSession session=request.getSession();
            LoadUpInfo loadUpInfo=new LoadUpInfo();
            loadUpInfo.processCboSetup(session);
            loadUpInfo.getServiceList(session);
            loadUpInfo.getOrganizationList(session);
            States state=(States)session.getAttribute("state");
            orgForm.setStateCode(state.getState_code());
            orgForm.setState(state.getName());

            /*Lgas lga=(Lgas)session.getAttribute("lga");
            orgForm.setLgaCode(lga.getLga_code());
            orgForm.setLga(lga.getLga_name());*/
    }
    private String concatArrayValues(String[] values)
    {
        String allValues="";
        if(values !=null)
        {
            for(int i=0; i<values.length; i++)
            {
                allValues+=values[i];
                if(i<values.length-1)
                allValues+=",";
            }
        }
        return allValues;
    }
    private void getLgaListPerState(HttpServletRequest request,String stateCode)
    {
        AjaxProcessor aproc=new AjaxProcessor();
        List lgaList=aproc.getLgasPerState(stateCode);
        request.setAttribute("lgalistperstate", lgaList);
    }
}
