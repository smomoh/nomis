/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.UmbrellaOrganization;
import com.fhi.kidmap.dao.OrganizationRecordsDao;
import com.fhi.kidmap.dao.OrganizationRecordsDaoImpl;
import com.fhi.kidmap.dao.UmbrellaOrganizationDao;
import com.fhi.kidmap.dao.UmbrellaOrganizationDaoImpl;
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
 * @author smomoh
 */
public class UmbrellaOrganizationAction extends org.apache.struts.action.Action {

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
        UmbrellaOrganizationForm uoform=(UmbrellaOrganizationForm)form;
        OrganizationRecordsDao orgDao=new OrganizationRecordsDaoImpl();
        UmbrellaOrganizationDao uodao=new UmbrellaOrganizationDaoImpl();
        AppUtility appUtil=new AppUtility();
        HttpSession session=request.getSession();
        OrganizationRecords orgRecords=null;
        String recordId=uoform.getRecordId();
        String umbrellaOrganizationCode=uoform.getUmbrellaOrganizationCode();
        String[] subOrganizationCode=uoform.getSubOrganizationCodes();
        String subOrganizations="";
        if(subOrganizationCode !=null)
        {
            for(int i=0; i<subOrganizationCode.length; i++)
            {
                if(umbrellaOrganizationCode.equalsIgnoreCase(subOrganizationCode[i]))
                continue;
                if(i==0)
                subOrganizations=subOrganizationCode[i];
                else
                subOrganizations+=","+subOrganizationCode[i];
            }
        }
        UmbrellaOrganization uo=new UmbrellaOrganization();

        uo.setUmbrellaOrganizationCode(umbrellaOrganizationCode);
        uo.setSubOrganizationCodes(subOrganizations);
        uo.setDateModified(appUtil.getCurrentDate());

        String stateCode=uoform.getStateCode();


        String requiredAction=uoform.getActionName();
        List allOrgList=new ArrayList();
        List orgList=new ArrayList();
        List stateCodeList=orgDao.getOrganizationRecordsStateList();
        uoform.setStateList(appUtil.getStates(stateCodeList));
        if(stateCode==null)
        {
            stateCode="All";
            if(stateCodeList !=null && !stateCodeList.isEmpty())
            {
                stateCode=(String)stateCodeList.get(0);
                allOrgList=orgDao.getStateOrganizationList(stateCode);
            }
        }
        else
        {
            allOrgList=orgDao.getStateOrganizationList(stateCode);
        }
        orgList=uodao.filterOrganizationRecords(allOrgList);
        List subOrganizationList=new ArrayList();
       /* if(orgList !=null && orgList.size()>0)
        {
            orgRecords=(OrganizationRecords)orgList.get(0);
            umbrellaOrganizationCode=orgRecords.getOrgCode();
            subOrganizationList=uodao.filterOrganizationRecordsForSubOrganizationList(umbrellaOrganizationCode, orgList);
        }*/
        
        
        if(orgList !=null && orgList.size()>1)
        {
            for(int i=1; i<orgList.size(); i++)
            {
                subOrganizationList.add(orgList.get(i));
            }
            uoform.setOrganizationList(orgList);
        }

        session.setAttribute("ucboSaveBtnDisabled", "false");
        session.setAttribute("ucboModifyBtnDisabled", "true");

        session.setAttribute("subOrgList", subOrganizationList);

        if(requiredAction==null)
        {
            uoform.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("cbosperstate"))
        {
            allOrgList=orgDao.getStateOrganizationList(stateCode);
            orgList=uodao.filterOrganizationRecords(allOrgList);
            /*if(orgList !=null && orgList.size()>0)
            {
                orgRecords=(OrganizationRecords)orgList.get(0);
                umbrellaOrganizationCode=orgRecords.getOrgCode();
                subOrganizationList=uodao.filterOrganizationRecordsForSubOrganizationList(umbrellaOrganizationCode, orgList);
                session.setAttribute("subOrgList", subOrganizationList);
            }*/
            uoform.setOrganizationList(orgList);
            uoform.reset(mapping, request);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("subcbos"))
        {
            uoform.reset(mapping, request);

            /*Check if this selected umbrella organization already has child organization*/
            uo=uodao.getUmbrellaOrganization(umbrellaOrganizationCode);
            subOrganizationList=uodao.filterOrganizationRecordsForSubOrganizationList(umbrellaOrganizationCode, allOrgList);
            uoform.setOrganizationList(orgList);
            if(uo !=null)
            {
                //subOrganizationList=uodao.filterOrganizationRecordsForSubOrganizationList(umbrellaOrganizationCode, allOrgList);
                //uoform.setOrganizationList(orgList);
                /*Get the child organizations, split it and set them on the view*/
                subOrganizations=uo.getSubOrganizationCodes();
                if(subOrganizations !=null)
                {
                    subOrganizationCode=subOrganizations.split(",");
                    uoform.setSubOrganizationCodes(subOrganizationCode);
                }
                uoform.setRecordId(uo.getRecordId());
                session.setAttribute("ucboSaveBtnDisabled", "true");
                session.setAttribute("ucboModifyBtnDisabled", "false");

            }
            session.setAttribute("subOrgList", subOrganizationList);

            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            uodao.saveUmbrellaOrganization(uo);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            uo.setRecordId(recordId);
            uodao.updateUmbrellaOrganization(uo);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            uo.setRecordId(recordId);
            uodao.deleteUmbrellaOrganization(uo);
        }
        uoform.reset(mapping, request);

        return mapping.findForward(SUCCESS);
    }
}
