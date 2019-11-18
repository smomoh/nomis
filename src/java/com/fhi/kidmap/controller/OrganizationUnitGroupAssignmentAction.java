/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.OrganizationUnitGroupAssignment;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.business.Wards;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.OrganizationRecordsDao;
import com.fhi.kidmap.dao.OrganizationRecordsDaoImpl;
import com.fhi.kidmap.dao.OrganizationUnitGroupAssignmentDao;
import com.fhi.kidmap.dao.OrganizationUnitGroupAssignmentDaoImpl;
import com.fhi.kidmap.dao.OrganizationUnitGroupDao;
import com.fhi.kidmap.dao.OrganizationUnitGroupDaoImpl;
import com.fhi.kidmap.dao.StatesDao;
import com.fhi.kidmap.dao.StatesDaoImpl;
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
public class OrganizationUnitGroupAssignmentAction extends org.apache.struts.action.Action {

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
        OrganizationUnitGroupAssignmentForm ougaf=(OrganizationUnitGroupAssignmentForm)form;
        OrganizationUnitGroupDao ougdao=new OrganizationUnitGroupDaoImpl();
        OrganizationUnitGroupAssignmentDao ougadao=new OrganizationUnitGroupAssignmentDaoImpl();
        OrganizationRecordsDao orgdao=new OrganizationRecordsDaoImpl();
        StatesDao sdao=new StatesDaoImpl();
        DaoUtil util=new DaoUtil();
        String requiredAction=ougaf.getActionName();
        String[] orgUnitIds=ougaf.getOrgUnitIds();
        String orgUnitGroupId=ougaf.getOrgUnitGroupId();
        String hiddenId=ougaf.getHiddenId();
        String stateCode=ougaf.getStateCode();
        int recordId=ougaf.getRecordId();
        List communityList=getCommunityList(stateCode);//util.getCommunityListByLgasForOrganizationUnitGrpAssignmentFromExistingData(stateCode);//orgdao.getStateOrganizationList(stateCode);
        HttpSession session=request.getSession();
        session.setAttribute("orgListInOugaAction", communityList);
        
        OrganizationUnitGroupAssignment ouga=null;
//System.err.println("stateCode is "+stateCode);
        List stateList=sdao.getStates();
        ougaf.setStateList(stateList);
        ougaf.setOrgUnitGroupList(ougdao.getAllOrganizationUnitGroups());
        session.setAttribute("ougaSavedisabled", "false");
        session.setAttribute("ougaModifyDisabled", "true");
        if(requiredAction==null)
        {
            ougaf.reset(mapping, request);
            if(stateList !=null && !stateList.isEmpty())
            {
                States state=(States)stateList.get(0);
                communityList=getCommunityList(state.getState_code());//util.getCommunityListByLgasForOrganizationUnitGrpAssignmentFromExistingData(state.getState_code());//orgdao.getStateOrganizationList(state.getState_code());
                session.setAttribute("orgListInOugaAction", communityList);
            }
            
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("orgList"))
        {
            ougaf.reset(mapping, request);
            ougaf.setStateCode(stateCode);
            communityList=getCommunityList(stateCode);//util.getCommunityListByLgasForOrganizationUnitGrpAssignmentFromExistingData(stateCode);
            //orgList=util.getCBOListByLgasForOrganizationUnitGrpAssignment(stateCode);//.getOrganizationRecordsByStateCodeFromHhe(stateCode);
            
            session.setAttribute("orgListInOugaAction", communityList);
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("details"))
        {
            //DaoUtil util=new DaoUtil();
            List ougaList=ougadao.getOrganizationUnitGroupAssignmentByGroupId(orgUnitGroupId);
            ougaf.reset(mapping, request);
            ougaf.setOrgUnitGroupId(orgUnitGroupId);
            ougaf.setStateCode(stateCode);
            communityList=getCommunityList(stateCode);//util.getCommunityListByLgasForOrganizationUnitGrpAssignmentFromExistingData(stateCode);
            //orgList=util.getCBOListByLgasForOrganizationUnitGrpAssignment(stateCode);//util.getOrganizationRecordsByStateCodeFromHhe(stateCode);
            List filteredOrgList=ougadao.filterCommunityList(communityList, orgUnitGroupId);
            session.setAttribute("orgListInOugaAction", filteredOrgList);
            
            if(ougaList !=null && !ougaList.isEmpty())
            {
                String[] ougaArray=new String[ougaList.size()];
                for(int i=0; i<ougaArray.length; i++)
                {
                    ouga=(OrganizationUnitGroupAssignment)ougaList.get(i);
                    ougaArray[i]=ouga.getOrgunitid();
                    //System.err.println("ougaArray[i] is "+ougaArray[i]);
                }
                
                ougaf.setOrgUnitIds(ougaArray);
                session.setAttribute("ougaSavedisabled", "true");
                session.setAttribute("ougaModifyDisabled", "false");
            }
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            ougadao.saveOrganizationUnitGroupAssignment(orgUnitIds, orgUnitGroupId,stateCode);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            ougadao.deleteOrganizationUnitGroupAssignmentByGroupId(orgUnitGroupId);
            ougadao.saveOrganizationUnitGroupAssignment(orgUnitIds, orgUnitGroupId,stateCode);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            ougadao.deleteOrganizationUnitGroupAssignmentByGroupId(orgUnitGroupId);
        }
        ougaf.reset(mapping, request);
        ougaf.setStateCode(stateCode);
        //ougaf.setOrgUnitGroupList(ougdao.getAllOrganizationUnitGroups());
        return mapping.findForward(SUCCESS);
    }
    private List getCommunityList(String stateCode)
    {
        List idList=new ArrayList();
        DaoUtil util=new DaoUtil();
        List communityList=util.getCommunityListByLgasForOrganizationUnitGrpAssignmentFromExistingData(stateCode);
        List newCommunityList=util.getCommunityListByLgasForOrganizationUnitGrpAssignment(stateCode);
        Wards community=null;
        if(communityList !=null)
        {
            for(int i=0; i<communityList.size(); i++)
            {
                community=(Wards)communityList.get(i);
                if(community !=null)
                idList.add(community.getWard_code());
            }
        }
        if(newCommunityList !=null)
        {
            for(int i=0; i<newCommunityList.size(); i++)
            {
                community=(Wards)newCommunityList.get(i);
                if(community !=null && !idList.contains(community.getWard_code()))
                communityList.add(community);
            }
        }
        return communityList;
    }
}
