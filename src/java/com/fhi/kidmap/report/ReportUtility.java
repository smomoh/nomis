/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report;

import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.OrganizationUnitGroup;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.LgaDao;
import com.fhi.kidmap.dao.LgaDaoImpl;
import com.fhi.kidmap.dao.OrganizationUnitGroupAssignmentDao;
import com.fhi.kidmap.dao.OrganizationUnitGroupAssignmentDaoImpl;
import com.fhi.kidmap.dao.OrganizationUnitGroupDao;
import com.fhi.kidmap.dao.OrganizationUnitGroupDaoImpl;
import com.fhi.kidmap.dao.OvcServiceDao;
import com.fhi.kidmap.dao.OvcServiceDaoImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class ReportUtility implements Serializable
{
    public static final String GRADUATED="graduated";
    public static final String MIGRATED="migrated";
    public static final String LOST_TO_FOLLOWUP="Loss to follow-up";
    public static final String DIED="Known death";
    public static final String TRANSFERED="transfer";
    public static final String AGED_OUT="ageabove17";
    public static final String VOLUNTARILY_WITHDRAWN="voluntary_withdrawal";
    
    DaoUtil util=new DaoUtil();
    String householdQueryPart="from HouseholdEnrollment hhe, ";
    
    public ReportUtility()
    {

    }
    public String getHouseholdQueryPart()
    {
        return householdQueryPart;
    }
    /*public String getHIVServicesReferralQuery()
    {
        String hivServicesQuery=" (service.serviceAccessed3 like '%HIV services referral%') ";
        return hivServicesQuery;
    }*/
    public String getHIVServicesReportQuery()
    {
        String hivServicesQuery=" (UPPER(service.serviceAccessed3) like '%HIV%' or UPPER(service.serviceAccessed3) like '%HTC%' or UPPER(service.serviceAccessed3) like '%HCT%') ";
        return hivServicesQuery;
    }
    public String getHhOvcQueryPart()
    {
        return householdQueryPart+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and ";
    }
    public String getOvcCountQueryPart()
    {
        return "select count(distinct ovc.ovcId)"+householdQueryPart+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and ";
    }
    public String getServiceCountQueryPart()
    {
        return "select count(distinct service.ovcId)"+householdQueryPart+"Ovc ovc, OvcService service where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId) and " ;
    }
    public String getHhOvcAndServiceQueryPart()
    {
        return householdQueryPart+"Ovc ovc,OvcService service where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId) ";
    }
    public String getHhOvcAndServiceByOvcIdQueryPart()
    {
        return "select distinct service.ovcId "+householdQueryPart+"Ovc ovc,OvcService service where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId) ";
    }
    public String getHheCaregiverHhsQuery()
    {
        return "select count(distinct hhs.caregiverId) from HouseholdService hhs, HouseholdEnrollment hhe,Caregiver cgiver where (hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.hhUniqueId=hhs.hhUniqueId)";
    }
    public String getHheServiceCountQuery()
    {
        return "select count(distinct hhs.hhUniqueId) from HouseholdService hhs, HouseholdEnrollment hhe where (hhe.hhUniqueId=hhs.hhUniqueId)";
    }
    public String getServiceReportName()
    {
        return " ServiceReport";
    }
    public String getOvcCurrentlyEnrolledQuery()
    {
        return "ovc.withdrawnFromProgram='No'";
    }
    public String getHhCurrentlyEnrolledQuery()
    {
        return "hhe.withdrawnFromProgram='No'";
    }
    public String getCgiverCurrentlyEnrolledQuery()
    {
        return "cgiver.withdrawnFromProgram='No'";
    }
    public String getOvcWithdrawnQuery(String reasonWithdrawal)
    {
        if(reasonWithdrawal==null)
        return "ovc.withdrawnFromProgram='Yes'";
        else
        return "ovc.withdrawnFromProgram='Yes' and UPPER(withdrawal.reasonWithdrawal)='"+reasonWithdrawal.toUpperCase()+"'";
    }
    public String getHhWithdrawnQuery(String reasonWithdrawal)
    {
        if(reasonWithdrawal==null)
        return "hhe.withdrawnFromProgram='Yes'";
        else
        return "hhe.withdrawnFromProgram='Yes' and UPPER(withdrawal.reasonWithdrawal)='"+reasonWithdrawal.toUpperCase()+"'";
    }
    public String getCgiverWithdrawnQuery(String reasonWithdrawal)
    {
        if(reasonWithdrawal==null)
        return "cgiver.withdrawnFromProgram='Yes'";
        else
        return "cgiver.withdrawnFromProgram='Yes' and UPPER(withdrawal.reasonWithdrawal)='"+reasonWithdrawal.toUpperCase()+"'";
    }
    public String getGraduatedParameter()
    {
        return "";
    }
    public static List getOrgUnitGroup(String parentOrgUnitGroupId)
    {
        OrganizationUnitGroupAssignmentDao ougadao=new OrganizationUnitGroupAssignmentDaoImpl();
        OrganizationUnitGroupDao ougdao=new OrganizationUnitGroupDaoImpl();
        OrganizationUnitGroup oug=null;
        List ougList=new ArrayList();
        try
        {
            List ougaList=ougadao.getDistinctOrgUnitGroupIdByParentId(parentOrgUnitGroupId);
            if(ougaList !=null)
            {
                for(int i=0; i<ougaList.size(); i++)
                {
                    oug=(OrganizationUnitGroup)ougdao.getOrganizationUnitGroup((String)ougaList.get(i));
                    if(oug !=null)
                    ougList.add(oug);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return ougList;
    }
    public List generateLgaListForReports(String stateCode)
    {
        DaoUtil util=new DaoUtil();
        LgaDao lgadao=new LgaDaoImpl();
        
        List lgaList=new ArrayList();
        List list=util.getHheLgaList(stateCode);
        try
        {
            for(Object obj:list)
            {
                Lgas lga=lgadao.getLgaByCode(obj.toString());
                if(lga !=null)
                lgaList.add(lga);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return lgaList;
    }
    public int[] get0_17AgeSegregation()
    {
        int[] ageSegregation={0,0,1,4,5,9,10,14,15,17};
        return ageSegregation;
    }
    public int[] getNewAgeSegregation()
    {
        int[] ageSegregation={0,0,1,4,5,9,10,14,15,17,18,100};
        return ageSegregation;
    }
    public int[] getNewFinerAgeSegregation()
    {
        int[] ageSegregation={0,0,1,4,5,9,10,14,15,17,18,24,25,100};
        return ageSegregation;
    }
    public int[] getAgeSegregation()
    {
        int[] ageSegregation={0,5,6,12,13,17};
        return ageSegregation;
    }
    public int[] getHouseholdAgeSegregation()
    {
        int[] householdAgeSegregation={0,17,18,59,60,200};
        return householdAgeSegregation;
    }
    public int[] getHouseholdAgeSegregation(int[] ageSegregation)
    {
        return ageSegregation;
    }
    public int[] getSecondHouseholdAgeSegregation()
    {
        int[] ageSegregation={0,17,18,24,25,200};
        return ageSegregation;
    }
    public String[] getQueryParam(List paramList)
    {
        String stateCode=(String)paramList.get(0);
        String lgaCode=(String)paramList.get(1);
        String cboCode=(String)paramList.get(2);
        String wardCode=(String)paramList.get(3);
        String startMonth=paramList.get(4).toString();
        String startYear=paramList.get(5).toString();
        String endMonth=paramList.get(6).toString();
        String endYear=paramList.get(7).toString();
        String partnerCode="All";
        if(paramList.size()>7)
        partnerCode=(String)paramList.get(8);
        
        System.err.println("partnerCode in rutil.getQueryParam(List paramList) is "+partnerCode);
        String partnerName=util.getPartnerName(partnerCode);
        String[] queryParam={stateCode,lgaCode,cboCode,wardCode,startMonth,startYear,endMonth,endYear,null,null,null,null,null,null,partnerName,partnerCode,null,null,partnerCode};
        return queryParam;
    }
    public String[] getLabelParam(List paramList)
    {
        String stateCode=(String)paramList.get(0);
        String lgaCode=(String)paramList.get(1);
        String cboCode=(String)paramList.get(2);
        String wardCode=(String)paramList.get(3);
        String startMonth=paramList.get(4).toString();
        String startYear=paramList.get(5).toString();
        String endMonth=paramList.get(6).toString();
        String endYear=paramList.get(7).toString();
        String partnerCode=paramList.get(8).toString();
        String stateName=util.getStateName(stateCode);
        String lgaName=util.getLgaName(lgaCode);
        String cboName=util.getOrganizationName(cboCode);
        String partnerName=util.getPartnerName(partnerCode);
        
        String[] labelParam={stateCode,lgaCode,cboCode,startMonth,startYear,endMonth,endYear,stateName,lgaName,cboName,null,null,null,partnerName,partnerCode};
        return labelParam;
    }
    public String getTrainingReportCriteria(String[] params)
    {
        String orgCode=params[0];
        String categoryId=params[1];
        String designation=params[2];
        String trainingName=params[3];
        String startDate=params[4];
        String endDate=params[5];
        String partnerCode=params[6];
        String queryPart=" ";
        if(orgCode !=null && !orgCode.equalsIgnoreCase("All"))
        queryPart=" and tp.organizationCode='"+orgCode+"'";
        if(categoryId !=null && !categoryId.equalsIgnoreCase("All"))
        queryPart=queryPart+" and trn.category='"+categoryId+"'";
        if(designation !=null && !designation.equalsIgnoreCase("All"))
        queryPart=queryPart+" and trn.designation='"+designation+"'";
        if(trainingName !=null && !trainingName.equalsIgnoreCase("All"))
        queryPart=queryPart+" and trn.trainingId='"+trainingName+"'";
        if(partnerCode !=null && !partnerCode.equalsIgnoreCase("All"))
        queryPart=queryPart+" and trn.partnerCode='"+partnerCode+"'";
        if((startDate !=null && !startDate.equalsIgnoreCase("All") && !startDate.equalsIgnoreCase("") && !startDate.equalsIgnoreCase(" ")) && (endDate !=null && !endDate.equalsIgnoreCase("All") && !endDate.equalsIgnoreCase("") && !endDate.equalsIgnoreCase(" ")))
        queryPart=queryPart+" and trn.startDate between '"+startDate+"' and '"+endDate+"'";
        return queryPart;
    }
    
    public void saveHivStatusOfOvcUnknownProvidedHTC()
    {
       
        OvcServiceDao sdao=new OvcServiceDaoImpl();
        
        try
        {
            sdao.saveHivStatusOfOvcUnknownProvidedHTC();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    public List getUniqueListOfHouseholds(List list)
    {
        List uniqueHhList=new ArrayList();
        
        if(list !=null && !list.isEmpty())
        {
            List uniqueIdList=new ArrayList();
            for(Object obj:list)
            {
                HouseholdEnrollment hhe=(HouseholdEnrollment)obj;
                if(!uniqueIdList.contains(hhe.getHhUniqueId()))
                {
                    uniqueHhList.add(hhe);
                    uniqueIdList.add(hhe.getHhUniqueId());
                }
            }
        }
        return uniqueHhList;
    }
}
