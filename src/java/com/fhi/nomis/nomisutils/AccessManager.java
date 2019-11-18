/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.nomisutils;

import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.OrganizationUnitGroupAssignment;
import com.fhi.kidmap.business.Partners;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.business.UserGroup;
import com.fhi.kidmap.business.UserRole;
import com.fhi.kidmap.business.Wards;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.LgaDao;
import com.fhi.kidmap.dao.LgaDaoImpl;
import com.fhi.kidmap.dao.OrganizationRecordsDao;
import com.fhi.kidmap.dao.OrganizationRecordsDaoImpl;
import com.fhi.kidmap.dao.OrganizationUnitGroupAssignmentDao;
import com.fhi.kidmap.dao.OrganizationUnitGroupAssignmentDaoImpl;
import com.fhi.kidmap.dao.OrganizationUnitGroupDao;
import com.fhi.kidmap.dao.OrganizationUnitGroupDaoImpl;
import com.fhi.kidmap.dao.StatesDao;
import com.fhi.kidmap.dao.StatesDaoImpl;
import com.fhi.kidmap.dao.UserDao;
import com.fhi.kidmap.dao.UserDaoImpl;
import com.fhi.kidmap.dao.UserGroupDao;
import com.fhi.kidmap.dao.UserGroupDaoImpl;
import com.fhi.kidmap.dao.UserRoleDao;
import com.fhi.kidmap.dao.UserRoleDaoImpl;
import com.fhi.kidmap.dao.WardDao;
import com.fhi.kidmap.dao.WardDaoImpl;
import com.fhi.nomis.OperationsManagement.HouseholdEnrollmentManager;
import com.fhi.nomis.OperationsManagement.OrganizationUnitGroupAssignmentManager;
import com.fhi.nomis.OperationsManagement.PartnerManager;
import com.fhi.nomis.OperationsManagement.StateManager;
import com.fhi.nomis.OperationsManagement.UserGroupManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Siaka
 */
public class AccessManager implements Serializable
{
    AppUtility appUtil=new AppUtility();
    UserDao userDao=new UserDaoImpl();
    //public static final String SUPERUSERID="superuser";
    //public static final String DEFAULTGRP="defaultgrp";
    
    public boolean isUserExist(String userName,String pwd)
    {
        boolean userExist=false;
        try
        {
            User user=userDao.getUser(userName, pwd);
            if(user !=null)
            userExist=true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return userExist;
    }
    public boolean isUserInRole(String rightId,String userName)
    {
        boolean userInRole=false;
        UserRoleDao urDao=new UserRoleDaoImpl();

        try
        {
            User user=userDao.getUser(userName);
            if(user==null)
            return false;
            String userRoleId=user.getUserroleId();
            if(userRoleId.equalsIgnoreCase(NomisConstant.SUPERUSER_ROLEID))
            return true;
            UserRole ur=urDao.getUserRole(userRoleId);
            String[] rightsArray=null;
            if(ur !=null)
            rightsArray=appUtil.splitString(ur.getAssignedRights(), ",");
            if(rightsArray !=null)
            {
                for(int i=0; i<rightsArray.length; i++)
                {
                    if(rightId.equalsIgnoreCase(rightsArray[i]))
                    userInRole=true;
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return userInRole;
    }
    public boolean userCanChangePassword(String currentUser)
    {
        User user=getUser(currentUser);
        if(user==null)
        return false;
        if((user !=null && user.getChangePwd()!=null && (user.getChangePwd().equalsIgnoreCase("yes")) || user.getChangePwd().equalsIgnoreCase("on")))
        return true;
        return false;
    }
    public User getUser(String userName)
    {
        User user=null;
        try
        {
            user=userDao.getUser(userName);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return user;
    }
    public List getListOfStatesAssignedToUser(String userName)
    {
        DaoUtil util=new DaoUtil();
        List stateList=new ArrayList();
        List stateCodeList=getStateCodesPerUser(userName);
        if(stateCodeList !=null)
        {
            for(int i=0; i<stateCodeList.size(); i++)
            {
                stateList.add(util.getStateByStateCode(stateCodeList.get(i).toString()));
            }
        }
        return stateList;
    }
    public List getListOfLgasAssignedToUser(User user,String stateCode)
    {
        DaoUtil util=new DaoUtil();
        List lgaList=new ArrayList();
        if(user !=null)
        {
            if(user.getUserroleId().equalsIgnoreCase(NomisConstant.SUPERUSER_ROLEID))
            lgaList=util.getLgaListFromHhe(stateCode);
            else if(user.getUserGroupId().equalsIgnoreCase(NomisConstant.DEFAULTGRPID))
            {
                if(user.getPartnerCodes() !=null)
                {
                    String concatPartnerCodes=user.getPartnerCodes();
                    String[] partnerCodeArray=concatPartnerCodes.split(",");
                    if(partnerCodeArray !=null)
                    {
                        List list=null;
                        for(int i=0; i<partnerCodeArray.length; i++)
                        {
                            list=util.getLgaListFromHheByStateAndPartnerCodes(stateCode, partnerCodeArray[i]);
                            if(list !=null)
                            lgaList.addAll(list);
                        }
                    }
                }
            }
            else
            {
                List lgaCodeList=getLgaCodesPerUser(user.getUsername(), stateCode);
                if(lgaCodeList !=null)
                {
                    for(int i=0; i<lgaCodeList.size(); i++)
                    {
                        lgaList.add(util.getLgaByLgaCode(lgaCodeList.get(i).toString()));
                    }
                }
            }
        }
        return lgaList;
    }
    private String[] getPartnerCodes(String concatPartnerCodes)
    {
        String[] partnerCodesArray=null;
        if(concatPartnerCodes !=null)
        {
            partnerCodesArray=concatPartnerCodes.split(",");
        }
        return partnerCodesArray;
    }
    public List getListOfCBOsAssignedToUser(User user,String stateCode,String lgaCode)
    {
        DaoUtil util=new DaoUtil();
        List cboIdList=new ArrayList();
        List cboList=new ArrayList();
        if(user !=null)
        {
            if(user.getUserroleId().equalsIgnoreCase(NomisConstant.SUPERUSER_ROLEID) || user.getUserGroupId().equalsIgnoreCase(NomisConstant.DEFAULTGRPID))
            cboList=util.getOrganizationRecordsFromHhe(lgaCode);
            else
            {
                List communityList=getListOfCommunitiesAssignedToUser(user,stateCode,lgaCode);
                if(communityList !=null)
                {
                    String cboCode=null;
                    Wards community=null;
                    for(int i=0; i<communityList.size(); i++)
                    {
                        community=(Wards)communityList.get(i);

                        if(community !=null)
                        {
                            cboCode=community.getCbo_code();
                            if(!cboIdList.contains(cboCode))
                            {
                                cboList.add(util.getOrganizationRecords(cboCode));
                                cboIdList.add(cboCode);
                            }
                        }
                    }
                }
            }
        }
        return cboList;
    }
    public List getListOfCommunitiesAssignedToUser(User user,String stateCode,String lgaCode)
    {
        String stateLgaCode=stateCode+"/"+lgaCode;
        List communityIdList=new ArrayList();
        DaoUtil util=new DaoUtil();
        List communityList=new ArrayList();
        if(user !=null)
        {
            List communityCodeList=getCommunityCodePerUser(user.getUsername());
            if(communityCodeList !=null)
            {
                String communityCode=null;
                for(int i=0; i<communityCodeList.size(); i++)
                {
                    communityCode=communityCodeList.get(i).toString();
                    if(communityCode !=null)
                    {
                        if(communityCode.startsWith(stateLgaCode))
                        {
                            if(!communityIdList.contains(communityCode))
                            {
                                communityList.add(util.getWard(communityCode));
                                communityIdList.add(communityCode);
                            }
                        }
                    }
                }
            }
        }
        return communityList;
    }
    public List getListOfCommunitiesAssignedToUserByCBO(User user,String stateCode,String lgaCode,String cboCode)
    {
        String stateLgaCode=stateCode+"/"+lgaCode;
        List communityIdList=new ArrayList();
        DaoUtil util=new DaoUtil();
        Wards ward=null;
        List communityList=new ArrayList();
        if(user !=null)
        {
            if(user.getUserroleId().equalsIgnoreCase(NomisConstant.SUPERUSER_ROLEID) || user.getUserGroupId().equalsIgnoreCase(NomisConstant.DEFAULTGRPID))
            communityList=util.getHheWardListByStateLgaAndCbo(stateCode,lgaCode,cboCode);
            else
            {
                List communityCodeList=getCommunityCodePerUser(user.getUsername());
                if(communityCodeList !=null)
                {
                    String communityCode=null;
                    for(int i=0; i<communityCodeList.size(); i++)
                    {
                        communityCode=communityCodeList.get(i).toString();
                        if(communityCode !=null)
                        {
                            if(communityCode.startsWith(stateLgaCode))
                            {
                                if(!communityIdList.contains(communityCode))
                                {
                                    ward=util.getWard(communityCode);
                                    if(ward !=null && ward.getCbo_code().trim().equalsIgnoreCase(cboCode.trim()))
                                    {
                                        communityList.add(util.getWard(communityCode));
                                    }
                                    communityIdList.add(communityCode);
                                }
                            }
                        }
                    }
                }
            }
        }
        return communityList;
    }
    public List getStateCodesPerUser(String userName)
    {
        List stateCodeList=new ArrayList();//getUserAssignedOrganizationUnitGroup(userName);
        List communityList=getCommunityCodePerUser(userName);
        if(communityList !=null)
        {
            String communityCode=null;
            for(int i=0; i<communityList.size(); i++)
            {
                communityCode=communityList.get(i).toString();
                if(communityCode.indexOf("/") !=-1)
                {
                    String[] codeArray=communityCode.split("/");
                    if(!stateCodeList.contains(codeArray[0]))
                    stateCodeList.add(codeArray[0]);
                }
                //System.err.println(communityList.get(i).toString());
            }
        }
        return stateCodeList;
    }
    public List getLgaCodesPerUser(String userName, String stateCode)
    {
        List lgaCodeList=new ArrayList();//getUserAssignedOrganizationUnitGroup(userName);
        List communityList=getCommunityCodePerUser(userName);
        if(communityList !=null)
        {
            String communityCode=null;
            for(int i=0; i<communityList.size(); i++)
            {
                communityCode=communityList.get(i).toString();
                if(communityCode.indexOf("/") !=-1)
                {
                    String[] codeArray=communityCode.split("/");
                    if(stateCode.trim().equalsIgnoreCase(codeArray[0].trim()))
                    {
                        if(!lgaCodeList.contains(codeArray[1]))
                        lgaCodeList.add(codeArray[1]);
                    }
                }
                //System.err.println(communityList.get(i).toString());
            }
        }
        return lgaCodeList;
    }
    public List getCommunityCodePerUser(String userName)
    {
        List orgUnitGroupIdList=getUserAssignedOrganizationUnitGroup(userName);
        List communityList=getDistinctListOfCommunityCodes(orgUnitGroupIdList);
        return communityList;
    }
    public List getUserAssignedOrganizationUnitGroup(String userName)
    {
        List orgUnitGroupList=new ArrayList();
        User user=getUser(userName);
        if(user !=null)
        {
            String concatOuGroupIds=user.getOrgUnitGroupId();
            if(concatOuGroupIds !=null)
            {
                if(concatOuGroupIds.indexOf(",") !=-1)
                {
                    String[] ouGroupIdArray=concatOuGroupIds.split(",");
                    for(int i=0; i<ouGroupIdArray.length; i++)
                    {
                        orgUnitGroupList.add(ouGroupIdArray[i].trim());
                    }
                }
                else
                {
                    orgUnitGroupList.add(concatOuGroupIds.trim());
                }
            }
        }
        return orgUnitGroupList;
    }
    public List getDistinctListOfCommunityCodes(List listOfOrgUnitGrpId)
    {
        List communityCodeList=new ArrayList();
        if(listOfOrgUnitGrpId !=null)
        {
            String communityCode=null;
            for(int i=0; i<listOfOrgUnitGrpId.size(); i++)
            {
                List list=getCommunityCodesFromOrgUnitGrpAssignment(listOfOrgUnitGrpId.get(i).toString());
                if(list !=null)
                {
                    for(int j=0; j<list.size(); j++)
                    {
                        communityCode=list.get(j).toString();
                        if(!communityCodeList.contains(communityCode))
                        communityCodeList.add(communityCode);
                    }
                }
            }
        }
        return communityCodeList;
    }
    /*This method returns the list of communities assigned to organization unit groups*/
    public List getCommunityCodesFromOrgUnitGrpAssignment(String orgUnitGrpId)
    {
        DaoUtil util=new DaoUtil();
        List communityList=util.getCommunityListFromOrgUnitGroupAssignment(orgUnitGrpId);
        return communityList;
    }
    public List getAllOrgUnitGroup()
    {
        List list=null;
        OrganizationUnitGroupDao ougdao=new OrganizationUnitGroupDaoImpl();
        try
        {
            list=ougdao.getAllOrganizationUnitGroups();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
    public List getLgaListForStartup(String stateCode,String userGroupIds)
    {
        //System.err.println("assignedUserGroupIds in getLgaListForStartup is "+assignedUserGroupIds);
        LoadUpInfo loadup=new LoadUpInfo();
        List lgaList=new ArrayList();
        try
        {
            lgaList=loadup.getLgasPerStateFromOrgRecords(stateCode);
            if(lgaList==null)
            lgaList=new ArrayList();
            //System.err.println("lgaList size in getLgaListForStartup is "+lgaList.size());
            for(int i=0; i<lgaList.size(); i++)
            {
                Lgas lga=(Lgas)lgaList.get(i);
                //System.err.println("lga name in getLgaListForStartup is "+lga.getLga_name());
            }

            if(userGroupIds.equalsIgnoreCase(NomisConstant.DEFAULTGRP) || userGroupIds.equalsIgnoreCase(NomisConstant.DEFAULTGRPID))
            {
                return lgaList;
            }
            else
            {

            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return lgaList;
    }
    public List getStateListForStartupByUserName(String userName)
    {
        getUser(userName);
        User user=getUser(userName);
        List stateList=new ArrayList();
        if(user !=null)
        stateList=getStateListForStartup(user);
        return stateList;
    }
    public List getStateListForStartup(HttpSession session)
    {
        User user=appUtil.getUser(session);
        List stateList=new ArrayList();
        if(user !=null)
        stateList=getStateListForStartup(user);
        
        return stateList;
    }
    public List getStateListForStartup(User user)
    {
        List stateList=new ArrayList();
        try
        {
            if(user !=null)
            {
                if(user.getUserroleId() !=null && user.getUserroleId().equalsIgnoreCase(NomisConstant.SUPERUSER_ROLEID) || (user.getUserGroupId() !=null && user.getUserGroupId().equalsIgnoreCase(NomisConstant.DEFAULTGRPID)))
                {
                    StatesDao sdao=new StatesDaoImpl();
                     stateList=sdao.getStates();
                }
                else
                stateList=getListOfStatesAssignedToUser(user.getUsername());
            }
        }
        catch(Exception ex)
        {
           ex.printStackTrace(); 
        }
        return stateList;
    }
    public List getPartnerListForReports(HttpSession session)
    {
        List list=new ArrayList();
        List partnerList=new ArrayList();
        
        User user=appUtil.getUser(session);
        try
        {
        if(user !=null)
        {
            String userGroupId=user.getUserGroupId();
            System.err.println("userGroupId is "+userGroupId);
            if(userGroupId !=null)
            {
                if(userGroupId.equalsIgnoreCase(NomisConstant.DEFAULTGRP) || userGroupId.equalsIgnoreCase(NomisConstant.DEFAULTGRPID))
                 {
                     HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
                     list=hhedao.getDistinctPartnerCodes();
                     if(list !=null)
                     {
                         for(int i=0; i<list.size(); i++)
                         {
                             if(list.get(i) !=null)
                             partnerList.add(PartnerManager.getPartner(list.get(i).toString()));
                         }
                     }
                 }
                else
                {
                    System.err.println("userGroup is not default "+userGroupId);
                   UserGroupDao usgdao=new UserGroupDaoImpl(); 
                   UserGroup usg=usgdao.getUserGroup(userGroupId);
                   if(usg !=null)
                   {
                       System.err.println("usg is not null "+userGroupId);
                       if(UserGroupManager.isDonor(usg))
                       {
                           String orgUnitGroup=usg.getOrgUnitGroup();
                           if(orgUnitGroup !=null)
                           {
                               System.err.println("orgUnitGroup is not default "+userGroupId);
                               if(orgUnitGroup.indexOf(",") !=-1)
                               {
                                   String[] orgUnitGroupArray=orgUnitGroup.split(",");
                                   if(orgUnitGroupArray !=null)
                                   {
                                       for(int i=0; i<orgUnitGroupArray.length; i++)
                                       {
                                           partnerList.add(PartnerManager.getPartner(orgUnitGroupArray[i]));
                                       }
                                   }
                               }
                               else
                               {
                                   partnerList.add(PartnerManager.getPartner(orgUnitGroup));
                               }
                           }
                       }
                       else
                       {
                           partnerList=getParnerListFromOrganizationUnitGroup(usg);
                       }
                   }
                }
            }
            
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return partnerList;
    }
    public List getParnerListFromOrganizationUnitGroup(UserGroup usg)
    {
        List list=new ArrayList();
        String orgUnitGroup=usg.getOrgUnitGroup();
        //System.err.println("orgUnitGroup is "+orgUnitGroup);
       if(orgUnitGroup !=null)
       {
           //System.err.println("orgUnitGroup is not null "+orgUnitGroup);
           try
           {
               if(orgUnitGroup.indexOf(",") !=-1)
               {
                   //System.err.println("orgUnitGroup has , seperator "+orgUnitGroup);
                   String[] orgUnitGroupArray=orgUnitGroup.split(",");
                   if(orgUnitGroupArray !=null)
                   {
                       List ougaList=null;
                       List idList=new ArrayList();
                       List partnerList=new ArrayList();
                       Partners partner=null;
                       for(int i=0; i<orgUnitGroupArray.length; i++)
                       {
                           //this returns list of partners by orgunitid, i.e community or ward code
                           //System.err.println("orgUnitGroupArray[i] is "+orgUnitGroupArray[i]);
                           partnerList=getPartnerListByOrgUnitGroupId(orgUnitGroupArray[i]);
                           if(partnerList !=null)
                           {
                               for(int k=0; k<partnerList.size(); k++)
                               {
                                   partner=(Partners)partnerList.get(k);
                                   if(partner !=null)
                                   {
                                       //Filter to remove duplicate partners
                                       if(!idList.contains(partner.getPartnerCode()))
                                       {
                                            list.add(partner);
                                            idList.add(partner.getPartnerCode());
                                       }
                                   }
                               }
                           }
                           
                       }
                   }
               }
               else
               {
                   list.addAll(getPartnerListByOrgUnitGroupId(orgUnitGroup));
                   System.err.println("orgUnitGroup has no , seperator "+orgUnitGroup);
               }
           }
           catch(Exception ex)
           {
               ex.printStackTrace();
           }
       }
        return list;
    }
    public List getPartnerListByOrgUnitGroupId(String orgUnitGroup)
    {
        List ougaList=OrganizationUnitGroupAssignmentManager.getOrganizationUnitGroupAssignments(orgUnitGroup);
        List idList=new ArrayList();  
        List list=new ArrayList();
        try
        {
           for(int j=0; j<ougaList.size(); j++)
           {
               OrganizationUnitGroupAssignment ouga=(OrganizationUnitGroupAssignment)ougaList.get(j);
               System.err.println("ouga.getOrgunitid() is "+ouga.getOrgunitid());
               List pcodeList=HouseholdEnrollmentManager.getHouseholdEnrollmentDaoInstance().getDistinctPartnerCodesByCommunity(ouga.getOrgunitid());
                if(pcodeList !=null)
                {
                    System.err.println("pcodeList is not null ");
                    for(int k=0; k<pcodeList.size(); k++)
                    {
                        System.err.println("pcodeList is "+pcodeList.get(k));
                        if(pcodeList.get(k) !=null && !idList.contains(pcodeList.get(k)))
                        {
                            list.add(PartnerManager.getPartner(pcodeList.get(k).toString()));
                            idList.add(pcodeList.get(k).toString());
                            System.err.println("pcodeList partner added to list ");
                        }
                    }
                }
           }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
         return list;              
    }
    public List getStateListByPartnersForReports(User user,List partnerList)
    {
        List list=new ArrayList();
        try
        {
            if(partnerList !=null)
            {
                List ougaParentList=getStateCodeListByUserGroup(user);
                List idList=new ArrayList();
                for(int i=0; i<partnerList.size(); i++)
                {
                    Partners partner=(Partners)partnerList.get(i);
                    List stateCodeList=HouseholdEnrollmentManager.getHouseholdEnrollmentDaoInstance().getDistinctStateCodesByPartner(partner.getPartnerCode());
                    if(stateCodeList !=null)
                    {
                        for(int j=0; j<stateCodeList.size(); j++)
                        {
                            
                            if(stateCodeList.get(j) !=null && !idList.contains(stateCodeList.get(j).toString()))
                            {
                                if(ougaParentList !=null && ougaParentList.contains(stateCodeList.get(j).toString()))
                                {
                                    System.err.println("ougaParentList.size() is "+ougaParentList.size());
                                    list.add(StateManager.getStateDaoInstance().getStateByStateCode(stateCodeList.get(j).toString()));
                                    idList.add(stateCodeList.get(j).toString());
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
    public List getStateCodeListByUserGroup(User user)
    {
        List stateCodeList=new ArrayList();
        try
        {
            if(user !=null)
            {
                System.err.println("user is not null ");
                String userGroupId=user.getUserGroupId();
                
                /*if(userGroupId.equalsIgnoreCase(NomisConstant.DEFAULTGRP) || userGroupId.equalsIgnoreCase(NomisConstant.DEFAULTGRPID))
                {
                     stateCodeList=HouseholdEnrollmentManager.getHouseholdEnrollmentDaoInstance().getDistinctStateCodes();
                     return stateCodeList;
                }*/
                if(user.isSuperUser())
                {
                    stateCodeList=HouseholdEnrollmentManager.getHouseholdEnrollmentDaoInstance().getDistinctStateCodes();
                     return stateCodeList;
                }
                if(userGroupId.equalsIgnoreCase(NomisConstant.DEFAULTGRP) || userGroupId.equalsIgnoreCase(NomisConstant.DEFAULTGRPID))
                {
                    if(user.getPartnerCodes() !=null)
                    {
                        String concatPartnerCodes=user.getPartnerCodes();
                        String[] partnerCodeArray=concatPartnerCodes.split(",");
                        if(partnerCodeArray !=null)
                        {
                            List list=null;
                            for(int i=0; i<partnerCodeArray.length; i++)
                            {
                                list=HouseholdEnrollmentManager.getHouseholdEnrollmentDaoInstance().getDistinctStateCodesByPartner(partnerCodeArray[i]);
                                if(list !=null)
                                stateCodeList.addAll(list);
                            }
                        }
                    }
                    return stateCodeList;
                }
                else
                {
                    UserGroup usg=UserGroupManager.getUserGroupDaoInstance().getUserGroup(userGroupId);
                    if(usg !=null)
                    {
                        System.err.println("usg is not null ");
                        System.err.println("usg.getOrgUnitGroup() "+usg.getOrgUnitGroup());
                        if(usg.getOrgUnitGroup() !=null)
                        {
                            String[] ougroupIdArray=usg.getOrgUnitGroup().split(",");
                            if(ougroupIdArray !=null)
                            {
                                for(int h=0; h<ougroupIdArray.length; h++)
                                {
                                    System.err.println("ougroupIdArray[h] is "+ougroupIdArray[h]);
                                    List list=OrganizationUnitGroupAssignmentManager.getOrganizationUnitGroupAssignmentDaoInstance().getOrganizationUnitGroupAssignmentByGroupId(ougroupIdArray[h]);
                                    if(list !=null)
                                    {
                                        System.err.println("list size is "+list.size());
                                        List idList=new ArrayList();
                                        for(int i=0; i<list.size(); i++)
                                        {
                                            OrganizationUnitGroupAssignment ouga=(OrganizationUnitGroupAssignment)list.get(i);
                                            System.err.println("ouga.getParentOrgUnitGroupId() is "+ouga.getParentOrgUnitGroupId());
                                            if(ouga !=null && ouga.getParentOrgUnitGroupId() !=null && !idList.contains(ouga.getParentOrgUnitGroupId()))
                                            {
                                                stateCodeList.add(ouga.getParentOrgUnitGroupId().toString());
                                                idList.add(ouga.getParentOrgUnitGroupId());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return stateCodeList;
    }
    public List getStateListForReports(HttpSession session)
    {
        List list=new ArrayList();
        DaoUtil util=new DaoUtil();
        List stateList=new ArrayList();
        List stateCodeList=util.getHheStateList();
        User user=appUtil.getUser(session);
        if(user !=null)
        {
            System.err.println("user.getAssignedGroupId() is "+user.getUserGroupId());
            stateList=getStateIdsFromUserGroupId(user.getUserGroupId());
            System.err.println("stateList is "+stateList.size());
            System.err.println("stateCodeList is "+stateCodeList.size());
        }
        
        if(stateList !=null && stateCodeList !=null)
        {
            States state=null;
            for(int i=0; i<stateList.size(); i++)
            {
                state=(States)stateList.get(i);
                for(int j=0; j<stateCodeList.size(); j++)
                {
                    String stateCode=(String)stateCodeList.get(j);
                    if(stateCode !=null && stateCode.equalsIgnoreCase(state.getState_code()))
                    {
                        list.add(state);
                        break;
                    }
                }
            }
        }
        return list;
    }
    public void setStateListForReportsByPartnerCode(HttpSession session,List partnerList)
    {
        User user=appUtil.getUser(session);
        List stateList=getStateListByPartnersForReports(user, partnerList);
        session.setAttribute("stateListForReports", stateList);   
    }
    public void setStateListForReports(HttpSession session)
    {
        List stateList=getStateListForReports(session);//getStateListForStartup(session);//new ArrayList();
        //System.err.println("stateList size in setStateListForReports is "+stateList.size());
        session.setAttribute("stateListForReports", stateList);   
    }
    public List getStateIdsFromUserGroupId(String userGroupId)
    {
        List stateList=new ArrayList();
        AppUtility appUtil=new AppUtility();
        try
        {
            UserGroupDao ugdao=new UserGroupDaoImpl();
            if(userGroupId !=null) 
            {
                //System.err.println("userGroupId in getStateIdsFromUserGroupId is "+userGroupId);
                 if(userGroupId.equalsIgnoreCase(NomisConstant.DEFAULTGRP) || userGroupId.equalsIgnoreCase(NomisConstant.DEFAULTGRPID))
                 {
                     StatesDao sdao=new StatesDaoImpl();
                     stateList=sdao.getStates();
                 }
                 else
                 {
                    String[] userGroupIdArray=appUtil.splitString(userGroupId, ",");
                    if(userGroupIdArray !=null && userGroupIdArray.length>0)
                    {
                        UserGroup usg=null;
                        for(int i=0; i<userGroupIdArray.length; i++)
                        {
                            usg=(UserGroup)ugdao.getUserGroup(userGroupIdArray[i]);
                            if(usg !=null)
                            {
                                //get organization unit groups assigned to these user groups
                                String orgunitGrpId=usg.getOrgUnitGroup();
                                if(orgunitGrpId !=null)
                                {
                                    String[] orgunitGrpIdArray=orgunitGrpId.split(",");
                                    if(orgunitGrpIdArray !=null && orgunitGrpIdArray.length>0)
                                    {
                                        //get the orgunitgroup assignment and retrieve the parent orgunit for that assignment, that is the state
                                        stateList.addAll(getParentsFromOrganizationUnitGroupAssignmentId(orgunitGrpIdArray));
                                    }
                                }
                            }
                        }//
                    }
                 }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();;
        }
        return stateList;
    }
    public List getLgaByStateUserGroupIdAndLevel(String stateCode,String userGroupId)
    {
        DaoUtil util=new DaoUtil();
        List lgaList=new ArrayList();
        List availableCodeList=new ArrayList();
        System.err.println("userGroupId in util.getLgaByStateUserGroupIdAndLevel(String stateCode,String userGroupId) is "+userGroupId);
        if(userGroupId !=null)
        {
            if(userGroupId.equalsIgnoreCase(NomisConstant.DEFAULTGRP) || userGroupId.equalsIgnoreCase(NomisConstant.DEFAULTGRPID))
            {
                lgaList=util.getLgaListFromHhe(stateCode);//ldao.getLgaList(stateCode);
            }
            else
            {
                List list=getOrgUnitsByStateUserGroupIdAndLevel(stateCode,userGroupId,3);
                if(list !=null)
                {
                    for(int i=0; i<list.size(); i++)
                    {
                       Lgas lga=(Lgas)list.get(i);
                        if(lga !=null && !availableCodeList.contains(lga.getLga_code()))
                        {
                            lgaList.add(lga);
                            availableCodeList.add(lga.getLga_code());
                        }
                    }
                }
            }
        }
        return lgaList;
    }
    public List getCBOByStateUserGroupIdAndLevel(String stateCode,String lgaCode,String userGroupId)
    {
        DaoUtil util=new DaoUtil();
        List orgList=new ArrayList();
        List availableCodeList=new ArrayList();
        System.err.println("userGroupId in util.getCBOByStateUserGroupIdAndLevel(String stateCode,String lgaCode,String userGroupId) is "+userGroupId);
        if(userGroupId !=null)
        {
            if(userGroupId.equalsIgnoreCase(NomisConstant.DEFAULTGRP) || userGroupId.equalsIgnoreCase(NomisConstant.DEFAULTGRPID))
            {
                orgList=util.getOrganizationRecordsFromHhe(lgaCode);//ldao.getLgaList(stateCode);
            }
            else
            {
                List list=getOrgUnitsByStateUserGroupIdAndLevel(stateCode,userGroupId,4);
                System.err.println("list.size() in getCBOByStateUserGroupIdAndLevel is "+list.size());
                if(list !=null)
                {
                    for(int i=0; i<list.size(); i++)
                    {            
                       OrganizationRecords or=(OrganizationRecords)list.get(i);
                       System.err.println("or in getCBOByStateUserGroupIdAndLevel is "+or.getOrgName()+" "+or.getOrgName()+" "+or.getLga());
                        if(or !=null && !availableCodeList.contains(or.getOrgCode()))
                        {
                            orgList.add(or);
                            availableCodeList.add(or.getOrgCode());
                            System.err.println("availableCodeList.size() in getCBOByStateUserGroupIdAndLevel is "+availableCodeList.size());
                        }
                    }
                }
            }
        }
        return orgList;
    }
    public List getCommunityByStateUserGroupIdAndLevel(String stateCode,String lgaCode,String cboCode,String userGroupId)
    {
        DaoUtil util=new DaoUtil();
        List communityList=new ArrayList();
        List availableCodeList=new ArrayList();
        System.err.println("userGroupId in util.getCommunityByStateUserGroupIdAndLevel(String stateCode,String lgaCode,String cboCode,String userGroupId) is "+userGroupId);
        if(userGroupId !=null)
        {
            if(userGroupId.equalsIgnoreCase(NomisConstant.DEFAULTGRP) || userGroupId.equalsIgnoreCase(NomisConstant.DEFAULTGRPID))
            {
                communityList=util.getWardListByStateAndLgaCodes(stateCode,lgaCode);//ldao.getLgaList(stateCode);
            }
            else
            {
                List list=getOrgUnitsByStateUserGroupIdAndLevel(stateCode,userGroupId,5);
                if(list !=null)
                {
                    String communityCode=null;
                    for(int i=0; i<list.size(); i++)
                    {
                       Wards ward=(Wards)list.get(i);
                        if(ward !=null && !availableCodeList.contains(ward.getWard_code()))
                        {
                            communityCode=ward.getWard_code();
                            if(communityCode.indexOf("/") !=-1)
                            {
                                String[] communityCodeArray=communityCode.split("/");
                                if(communityCodeArray !=null)
                                {
                                    if(communityCodeArray.length==3)
                                    {

                                    }
                                    else if(communityCodeArray !=null && communityCodeArray.length>3)
                                    {
                                        if(!communityCodeArray[1].trim().equalsIgnoreCase(lgaCode.trim()))
                                        continue;
                                    }
                                    communityList.add(ward);
                                    availableCodeList.add(ward.getWard_code());
                                }
                            }
                        }
                    }
                }
            }
        }
        return communityList;
    }
    public List getOrgUnitsByStateUserGroupIdAndLevel(String stateCode,String userGroupId,int level)
    {
        List list=new ArrayList();
        System.err.println("userGroupId is "+userGroupId+" and stateCode is "+stateCode);
        //AppUtility appUtil=new AppUtility();
        try
        {
            UserGroupDao ugdao=new UserGroupDaoImpl();
            if(userGroupId !=null) 
            {
                    String[] userGrpArray=appUtil.splitString(userGroupId, ",");
                    if(userGrpArray !=null && userGrpArray.length>0)
                    {
                        //get the organization unit group assignments with these ids and parent id
                        
                        OrganizationUnitGroupAssignmentDao ougadao=new OrganizationUnitGroupAssignmentDaoImpl();
                        OrganizationUnitGroupAssignment ouga=null;
                        String communityCode=null;
                        
                        for(int i=0; i<userGrpArray.length; i++)
                        {
                            UserGroup usg=ugdao.getUserGroup(userGrpArray[i]);
                            String assignedOrgUnitGrpId=usg.getOrgUnitGroup();
                            //System.err.println("assignedOrgUnitGrpId, stateCode is "+assignedOrgUnitGrpId+" and state is "+stateCode);
                            if(assignedOrgUnitGrpId !=null)
                            {
                                String[] assignedOrgUnitGrpIdArray=assignedOrgUnitGrpId.split(",");
                                if(assignedOrgUnitGrpIdArray !=null)
                                {
                                    for(int k=0; k<assignedOrgUnitGrpIdArray.length; k++)
                                    {
                                        List ougaList=ougadao.getOrganizationUnitGroupAssignmentByGroupId(assignedOrgUnitGrpIdArray[k]);
                                        if(ougaList !=null)
                                        {
                                            for(int j=0; j<ougaList.size(); j++)
                                            {
                                                ouga=(OrganizationUnitGroupAssignment)ougaList.get(j);
                                                //System.err.println("ouga.getParentOrgUnitGroupId() is "+ouga.getParentOrgUnitGroupId());
                                                if(ouga.getParentOrgUnitGroupId().equalsIgnoreCase(stateCode))
                                                {
                                                    communityCode=ouga.getOrgunitid();
                                                    if(communityCode !=null && communityCode.indexOf("/") !=-1)
                                                    {
                                                        String[] communityCodeParts=communityCode.split("/");
                                                        if(communityCodeParts.length==3 && level==5)
                                                        {
                                                            //This may be an old setup with cbocode instead of communityCodes
                                                            list.addAll(getCommunitiesByCboCode(communityCode));
                                                        }
                                                        else
                                                        list.add(getOrganizationUnitByCommunityCode(communityCode,level));
                                                        
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
            }        
        }
        catch(Exception ex)
        {
            ex.printStackTrace();;
        }
        return list;
    }
    public List getCommunitiesByCboCode(String cboCode)
    {
        List communityList=null;
        try
        {
            WardDao wdao=new WardDaoImpl();
            OrganizationRecordsDao ordao=new OrganizationRecordsDaoImpl();
            OrganizationRecords orgRecord=ordao.getOrganizationRecord(cboCode);
            communityList=wdao.getWardByCboId(cboCode);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return communityList;
    }
    public Object getOrganizationUnitByCommunityCode(String communityCode,int level)
    {
        Object obj=null;
        System.err.println("communityCode in getOrganizationUnitByCommunityCode is "+communityCode+" level is "+level);
        if(communityCode !=null)
        {
            try
            {
                if(communityCode.indexOf("/") !=-1)
                {
                    String[] communityCodeParts=communityCode.split("/");
                    if(level==2)
                    {
                        StatesDao sdao=new StatesDaoImpl();
                        obj=(States)sdao.getState(communityCodeParts[0]);
                    }
                    else if(level==3)
                    {
                        LgaDao ldao=new LgaDaoImpl();
                        obj=(Lgas)ldao.getLgaByCode(communityCodeParts[1]);
                    }
                    else if(level==4)
                    {
                        String cboCode=communityCode;
                        WardDao wdao=new WardDaoImpl();
                        
                        if(communityCodeParts.length >3)
                        {
                            Wards ward=wdao.getWards(communityCode);
                            if(ward !=null)
                            {
                                cboCode=ward.getCbo_code();
                            }
                        }
                        
                        OrganizationRecordsDao ordao=new OrganizationRecordsDaoImpl();
                        obj=(OrganizationRecords)ordao.getOrganizationRecord(cboCode);
                    }
                    else if(level==5)
                    {
                        WardDao wdao=new WardDaoImpl();
                        obj=(Wards)wdao.getWards(communityCode);
                    }
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }   
        }
        return obj;
    }
    public List getOrganizationUnitListByCommunityCode(String communityCode,int level)
    {
        Object obj=null;
        List list=new ArrayList();
        //System.err.println("communityCode in getOrganizationUnitByCommunityCode is "+communityCode);
        if(communityCode !=null)
        {
            try
            {
                WardDao wdao=new WardDaoImpl();
                OrganizationRecordsDao orgdao=new OrganizationRecordsDaoImpl();
                OrganizationRecords orgRecord=new OrganizationRecords();
                    Wards ward=wdao.getWards(communityCode);
                    if(ward !=null)
                    {
                        //String 
                        String cboCode=ward.getCbo_code();
                        orgRecord=orgdao.getOrganizationRecord(cboCode);
                        if(level==2)
                        {
                            StatesDao sdao=new StatesDaoImpl();
                            //obj=(States)sdao.getState(communityCodeParts[0]);
                        }
                        else if(level==3)
                        {
                            if(orgRecord !=null)
                            {
                                String lgaCode=orgRecord.getLga();
                                LgaDao ldao=new LgaDaoImpl();
                                //obj=(Lgas)ldao.getLgaByCode(communityCodeParts[1]);
                            }
                        }
                        else if(level==4)
                        {
                            if(ward !=null)
                            {
                                OrganizationRecordsDao ordao=new OrganizationRecordsDaoImpl();
                                obj=(OrganizationRecords)ordao.getOrganizationRecord(ward.getCbo_code());
                            }
                        }
                        else if(level==5)
                        {
                            //WardDao wdao=new WardDaoImpl();
                            obj=(Wards)wdao.getWards(communityCode);
                        }
                    }
                
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }   
        }
        return list;
    }
    public List getParentsFromOrganizationUnitGroupAssignmentId(String[] orgunitGrpIdArray)
    {
        DaoUtil util=new DaoUtil();
        OrganizationUnitGroupAssignmentDao ougadao=new OrganizationUnitGroupAssignmentDaoImpl();
        //UserGroupDao ugdao=new UserGroupDaoImpl();
        OrganizationUnitGroupAssignment ouga=null;
        String lastStateCode=" ";
        List stateList=new ArrayList();
        try
        {
            for(int j=0; j<orgunitGrpIdArray.length; j++)
            {
                List ougaList=(List)ougadao.getOrganizationUnitGroupAssignmentByGroupId(orgunitGrpIdArray[j]);
                if(ougaList !=null)
                {
                    for(int k=0; k<ougaList.size(); k++)
                    {
                        ouga=(OrganizationUnitGroupAssignment)ougaList.get(k);
                        if(!lastStateCode.equalsIgnoreCase(ouga.getParentOrgUnitGroupId()))
                        {
                            lastStateCode=ouga.getParentOrgUnitGroupId();
                            stateList.add(util.getStateByStateCode(lastStateCode));
                        }
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return stateList;
    }
    public List getStateListFromUser(String userName)
    {
        UserDao udao=new UserDaoImpl();
        List stateList=new ArrayList();
        try
        {
            User user=udao.getUser(userName);
            String assignedGrpIds=user.getAssignedGroupId();
            if(assignedGrpIds !=null)
            {
                //UserGroupDao ugdao=new UserGroupDaoImpl();
                String[] assignedGrpArray=assignedGrpIds.split(",");
                if(assignedGrpArray !=null && assignedGrpArray.length>0)
                { 
                    String groupId=null;
                    for(int i=0; i<assignedGrpArray.length; i++)
                    {
                        groupId=assignedGrpArray[i];
                        stateList.addAll(getStateIdsFromUserGroupId(groupId));                        
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return stateList;
    }
    public static void enableMetaDataAccess(HttpSession session)
    {
        session.setAttribute("enableEnvironmentSetup", "enableEnvironmentSetup");
    }
    public static void disableMetaDataAccess(HttpSession session)
    {
        session.removeAttribute("enableEnvironmentSetup");
    }
    public static AccessManager getInstance()
    {
        return new AccessManager();
    }
}
