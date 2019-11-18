/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nomis.webservice.pmis;

import com.fhi.kidmap.business.CategoryCombination;

import com.fhi.kidmap.business.Lgas;
import com.fhi.nomis.nomisutils.Message;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.dao.CategoryCombinationDao;
import com.fhi.kidmap.dao.CategoryCombinationDaoImpl;
import com.fhi.kidmap.dao.DatavalueDao;
import com.fhi.kidmap.dao.DatavalueDaoImpl;
import com.fhi.kidmap.dao.IndicatorDao;
import com.fhi.kidmap.dao.IndicatorDaoImpl;
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
import com.nomis.business.OrganizationUnit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class PMISOperation implements Serializable
{
    public List getPMISServiceData(String metadataRequired,String startDate,String endDate)
    {
        List mainList=new ArrayList();
        List orgUnitList=new ArrayList();
        List orgUnitGroupList=new ArrayList();
        List orgUnitGroupAssignmentList=new ArrayList();
        List indicatorList=new ArrayList();
        List catComboList=new ArrayList();
        List dataValueList=new ArrayList();
        List loginList=new ArrayList();
        Message msg=new Message();
        if(metadataRequired !=null && metadataRequired.equalsIgnoreCase("yes"))
        {
            orgUnitList=getOrganizationUnitList();
            if(orgUnitList==null)
            orgUnitList=new ArrayList();
            orgUnitGroupList=getOrganizationUnitGroupList();
            if(orgUnitGroupList==null)
            orgUnitGroupList=new ArrayList();
            orgUnitGroupAssignmentList=getOrganizationUnitGroupAssignmentList();
            if(orgUnitGroupAssignmentList==null)
            orgUnitGroupAssignmentList=new ArrayList();
            indicatorList=getIndicatorList();
            if(indicatorList==null)
            indicatorList=new ArrayList();
            catComboList=getCategoryCombinations();
            if(catComboList==null)
            catComboList=new ArrayList();
        }

        dataValueList=getDatavalue(startDate,endDate);
        if(dataValueList==null)
        dataValueList=new ArrayList();

        mainList.add(orgUnitList);
        mainList.add(orgUnitGroupList);
        mainList.add(orgUnitGroupAssignmentList);
        mainList.add(indicatorList);
        mainList.add(catComboList);
        mainList.add(dataValueList);
        msg=getMessage("Login","Login successful");
        loginList.add(msg);
        mainList.add(loginList);
        return mainList;
    }
    public List getOrganizationUnitList()
    {
        List list=new ArrayList();
        try
        {
            StatesDao sdao=new StatesDaoImpl();
            LgaDao ldao=new LgaDaoImpl();
            OrganizationRecordsDao ordao=new OrganizationRecordsDaoImpl();
            List stateList=sdao.getStates();
            List lgaList=ldao.getAllLgas();
            List orgList=ordao.getOrganizationList();
            OrganizationRecords orgRecord=null;
            Lgas lga=null;
            States state=null;
            OrganizationUnit orgUnit=null;
            for(int i=0; i<stateList.size(); i++)
            {
                state=(States)stateList.get(i);
                orgUnit=new OrganizationUnit();
                orgUnit.setOrgunitid(state.getState_code());
                orgUnit.setOrgunitname(state.getName());
                orgUnit.setParentid("NIG");
                list.add(orgUnit);
            }
            for(int i=0; i<lgaList.size(); i++)
            {
                lga=(Lgas)lgaList.get(i);
                orgUnit=new OrganizationUnit();
                orgUnit.setOrgunitid(lga.getLga_code());
                orgUnit.setOrgunitname(lga.getLga_name());
                orgUnit.setParentid(lga.getState_code());
                list.add(orgUnit);
            }
            for(int i=0; i<orgList.size(); i++)
            {
                orgRecord=(OrganizationRecords)orgList.get(i);
                orgUnit=new OrganizationUnit();
                orgUnit.setOrgunitid(orgRecord.getOrgCode());
                orgUnit.setOrgunitname(orgRecord.getOrgName());
                orgUnit.setParentid(orgRecord.getLga());
                list.add(orgUnit);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
    public List getOrganizationUnitGroupList()
    {
        List list=new ArrayList();
        List pmisOugList=new ArrayList();
        OrganizationUnitGroupDao ougdao=new OrganizationUnitGroupDaoImpl();
        try
        {
            com.fhi.kidmap.business.OrganizationUnitGroup oug=null;
            com.nomis.webservice.pmis.OrganizationUnitGroup pmisOug=null;
            list=ougdao.getAllOrganizationUnitGroups();
            for(int i=0; i<list.size(); i++)
            {
                oug=(com.fhi.kidmap.business.OrganizationUnitGroup)list.get(i);
                pmisOug=new com.nomis.webservice.pmis.OrganizationUnitGroup();
                pmisOug.setOrgUnitGroupId(oug.getOrgUnitGroupId());
                pmisOug.setOrgUnitGroupName(oug.getOrgUnitGroupName());
                pmisOugList.add(pmisOug);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
    public List getOrganizationUnitGroupAssignmentList()
    {
        List list=null;
        OrganizationUnitGroupAssignmentDao ougadao=new OrganizationUnitGroupAssignmentDaoImpl();
        try
        {
            list=ougadao.getAllOrganizationUnitGroupAssignments();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
    public List getIndicatorList()
    {
        List list=new ArrayList();
        com.nomis.business.Indicator indicator=null;
        com.nomis.webservice.pmis.Indicator pmisIndicator=null;
        try
        {
            IndicatorDao idao=new IndicatorDaoImpl();
            List indicatorList=idao.getAllIndicators();
            for(int i=0; i<indicatorList.size(); i++)
            {
                indicator=(com.nomis.business.Indicator)indicatorList.get(i);
                pmisIndicator=new com.nomis.webservice.pmis.Indicator();
                pmisIndicator.setIndicatorId(indicator.getIndicatorId());
                pmisIndicator.setIndicatorName(indicator.getIndicatorName());
                list.add(pmisIndicator);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
    public List getCategoryCombinations()
    {
        List list=null;
        List catComboList=new ArrayList();
        CategoryCombination catCombo=null;
        com.nomis.webservice.pmis.CategoryCombination pmiscc=null;
        CategoryCombinationDao ccdao=new CategoryCombinationDaoImpl();
        try
        {
            list=ccdao.getCategoryCombinationList();
            if(list !=null)
            {
                for(int i=0; i<list.size(); i++)
                {
                    catCombo=(CategoryCombination)list.get(i);
                    pmiscc=new com.nomis.webservice.pmis.CategoryCombination();
                    pmiscc.setCategoryComboId(catCombo.getCategoryComboId());
                    pmiscc.setCategoryComboName(catCombo.getCategoryComboName());
                    catComboList.add(pmiscc);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return catComboList;
    }
    public List getDatavalue(String startDate,String endDate)
    {
        List list=null;
        DatavalueDao dvdao=new DatavalueDaoImpl();
        try
        {
            list=dvdao.getDatavalues(startDate, endDate);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
    private Message getMessage(String type,String message)
    {
        Message msg=new Message();
        msg.setMessageType(type);
        msg.setMessage(message);
        return msg;
    }
}
