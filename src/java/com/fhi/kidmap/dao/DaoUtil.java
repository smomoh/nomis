/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.CboSetup;
import com.fhi.kidmap.business.Cbos;
import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.kidmap.business.EligibilityCriteria;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.HouseholdVulnerabilityAssessment;
import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.OrganizationUnitGroup;
import com.fhi.kidmap.business.OrganizationUnitGroupAssignment;
import com.fhi.kidmap.business.OrganizationUnitHirachy;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.kidmap.business.Partners;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.business.Wards;
import com.fhi.nomis.OperationsManagement.PartnerManager;
import com.fhi.nomis.logs.NomisLogManager;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import com.fhi.nomis.nomisutils.OptionsManager;
import com.fhi.nomis.nomisutils.OvcServiceManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author COMPAQ USER
 */
public class DaoUtil implements Serializable{

    Session session;
    Transaction tx;

    public DaoUtil()
    {

    }
    public static int getGraduationScore(String value)
    {
       int score=0;
       if(value !=null)
       {
           if(value.equalsIgnoreCase("yes"))
           score=1;
           else if(value.equalsIgnoreCase("NA") || value.equalsIgnoreCase("N/A"))
           score=1;
       }
       return score;
    }
    public String getVulnerabilityStatus(int score)
    {
        String vulnerabilityStatus=" ";
        if(score>=NomisConstant.VULNERABLE_STARTVALUE && score <=NomisConstant.VULNERABLE_ENDVALUE)
        vulnerabilityStatus="Vulnerable";
        if(score>=NomisConstant.MOREVULNERABLE_STARTVALUE && score <=NomisConstant.MOREVULNERABLE_ENDVALUE)
        vulnerabilityStatus="More vulnerable";
        if(score>=NomisConstant.MOSTVULNERABLE_STARTVALUE && score <=NomisConstant.MOSTVULNERABLE_ENDVALUE)
        vulnerabilityStatus="Most vulnerable";
        return vulnerabilityStatus;
    }
    public String getWithdrawalStatus(String uniqueId)
    {
        String withdrawalStatus="No";
        try
        {
            if(getOvcWithdrawalDaoInstance().getOvcWithdrawal(uniqueId) !=null)
            withdrawalStatus="Yes";
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return withdrawalStatus;
    }
    public String getDateOfLastActivity(Object person)
    {
        String dateOfLastActivity=null;
        try
        {
            if(person !=null)
            {
                HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
                HouseholdFollowupAssessmentDao hhfadao=new HouseholdFollowupAssessmentDaoImpl();
                OvcServiceDao sdao=new OvcServiceDaoImpl();
                NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
                FollowupDao fudao=new FollowupDaoImpl();
                HivRiskAssessmentChecklistDao hradao=new HivRiskAssessmentChecklistDaoImpl();
                if(person instanceof HouseholdEnrollment)
                {

                }
                else if(person instanceof Caregiver)
                {

                }
                else if(person instanceof Ovc)
                {
                    Ovc ovc=(Ovc)person;
                    List list=sdao.getOvcServices(ovc.getOvcId());
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return dateOfLastActivity;
    }
    public void processExitedWithoutGraduation(String startDate,String endDate)
    {
        try
        {
            List list=getOvcDaoInstance().getListOfOvcEnrolledBeforeTheStartOfReportPeriod(null, startDate, true);
            declareOvcInactive(list,startDate,endDate);
            
            List newlyEnrolledList=getOvcDaoInstance().getListOfOvcNewlyEnrolled(null, startDate,endDate, true);
            startDate="2018-01-01";
            endDate="2018-03-31";
            //declareOvcInactive(newlyEnrolledList,startDate,endDate);
            
            //NomisUpgrade nu=new NomisUpgrade();
            //String values=nu.updateWithdrawalStatus(true);
            //System.err.println("values is "+values);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
public OvcWithdrawal getWithdrawalWithCleanedValues(OvcWithdrawal withdrawal)
 {
     try
     {
         String reasonWithdrawal=withdrawal.getReasonWithdrawal();
         if(withdrawal !=null && reasonWithdrawal !=null)
         {
            if(withdrawal.getType() !=null && withdrawal.getType().equalsIgnoreCase(NomisConstant.HOUSEHOLD_TYPE))
            { 
                if(reasonWithdrawal.equalsIgnoreCase(NomisConstant.DIED))
                withdrawal.setType("caregiver");
            }
            else if(reasonWithdrawal.equalsIgnoreCase("ageabove18") || reasonWithdrawal.equalsIgnoreCase("Age > 18") || reasonWithdrawal.equalsIgnoreCase("Age &gt; 18"))
            withdrawal.setReasonWithdrawal(NomisConstant.AGED_OUT);//
            else if(withdrawal.getReasonWithdrawal().equalsIgnoreCase("Graduated") || withdrawal.getReasonWithdrawal().equalsIgnoreCase("Graduated from program")  || withdrawal.getReasonWithdrawal().equalsIgnoreCase("") || withdrawal.getReasonWithdrawal().equalsIgnoreCase(" ") || withdrawal.getReasonWithdrawal().equalsIgnoreCase("  ") || withdrawal.getReasonWithdrawal().equalsIgnoreCase("   "))
            withdrawal.setReasonWithdrawal(NomisConstant.GRADUATED);
            else if(withdrawal.getReasonWithdrawal().equalsIgnoreCase("ageabove17") || withdrawal.getReasonWithdrawal().equalsIgnoreCase("ageabove18")  || withdrawal.getReasonWithdrawal().indexOf("AGE") !=-1)
            {
                Ovc ovc=this.getOvcDaoInstance().getOvc(withdrawal.getOvcId());
                if(ovc !=null && ovc.getCurrentAge()<18)
                withdrawal.setReasonWithdrawal(NomisConstant.GRADUATED);
            }
            if(withdrawal.getType()==null || (!withdrawal.getType().equalsIgnoreCase(NomisConstant.OVC_TYPE) && !withdrawal.getType().equalsIgnoreCase(NomisConstant.Caregiver_TYPE) && !withdrawal.getType().equalsIgnoreCase(NomisConstant.HOUSEHOLD_TYPE)))
            {
                withdrawal=getWithdrawal(withdrawal);
            }
         }
     }
     catch(Exception ex)
     {
         ex.printStackTrace();
     }
     return withdrawal;
 }
public OvcWithdrawal getWithdrawal(OvcWithdrawal withdrawal)
{
    String uniqueId=withdrawal.getOvcId();
    if(uniqueId !=null)
    {
        if(uniqueId.trim().length() == 17)
        withdrawal.setType(NomisConstant.HOUSEHOLD_TYPE);
        else if(uniqueId.trim().length() > 17 && (uniqueId.trim().length() < 20))
        withdrawal.setType(NomisConstant.Caregiver_TYPE);
        else if(uniqueId.trim().length() > 19)
        withdrawal.setType(NomisConstant.OVC_TYPE);

        //System.err.println("withdrawal with id "+withdrawal.getOvcId()+" updated with type: "+withdrawal.getType());
    }
    return withdrawal;
}
public OvcWithdrawal correctWithdrawalType(OvcWithdrawal withdrawal) throws Exception
{
    try
    {
        if(withdrawal !=null)
        {
            if(withdrawal.getType()==null || (withdrawal.getType() !="ovc" && withdrawal.getType() !="household" && withdrawal.getType() !="caregiver"))
            withdrawal=getWithdrawal(withdrawal);
        }
    }
    catch(Exception ex)
    {
        
    }
    return withdrawal;
}
    public void removeInactiveClientsFromWithdrawalList()
    {
        try
        {
            List list=getOvcWithdrawalDaoInstance().getListOfBeneficiariesByReasonWithdrawn(NomisConstant.INACTIVE);
            if(list !=null)
            {
                int count=0;
                for(Object obj:list)
                {
                    OvcWithdrawal withdrwal=(OvcWithdrawal)obj;
                    getOvcWithdrawalDaoInstance().deleteOvcWithdrawal(withdrwal);
                    System.err.println("withdrwal with status "+withdrwal.getReasonWithdrawal()+" removed "+(++count));
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void declareOvcInactive(List list,String startDate,String endDate)
    {
        try
        {
            //AppUtility appUtil=new AppUtility();
            if(list !=null && !list.isEmpty())
            {
                for(int i=0;i<list.size(); i++)
                {
                    Ovc ovc=(Ovc)list.get(i);
                    List serviceList=getOvcServiceDaoInstance().getOvcServicesByOvcIdAndPeriod(ovc.getOvcId(), startDate, endDate);
                    if(serviceList==null || serviceList.isEmpty())
                    {
                        getOvcWithdrawalDaoInstance().withdrawClient(ovc.getOvcId(), endDate, NomisConstant.INACTIVE,NomisConstant.OVC_TYPE, 0);
                        System.err.println("ovc.getOvcId() at "+i+" exited without graduation");
                    }
                }
                
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public String getParentOrgUnit(int oulevel,String orgUnitCode)
    {
        String parentOrgUnitCode="All";
        if(oulevel==3)
        {
            Lgas lga=getLgaByLgaCode(orgUnitCode);
            if(lga !=null)
            parentOrgUnitCode=lga.getState_code();
        }
        else if(oulevel==4)
        {
            OrganizationRecords orgRecord=this.getOrganizationRecords(orgUnitCode);
            if(orgRecord !=null)
            parentOrgUnitCode=orgRecord.getLga();
        }
        return parentOrgUnitCode;
    }
    public List execReportQuery(String sql)
    {
        List list=new ArrayList();
        //System.err.println("sql in execReportQuery is "+sql);
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            list=session.createQuery(sql).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
        //System.err.println("list size in execReportQuery is "+list.size());
        return list;
    }
    public List getAllUserGroups()
    {
        UserGroupDao usgdao=new UserGroupDaoImpl();
        
        List usgList=null;
        try
        {
            usgList=usgdao.getAllUserGroups();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return usgList;
    }
    public List getCommunityListByOrganizationUnitHierachy()
    {
        List communityList=new ArrayList();
        WardDao wdao=new WardDaoImpl();
        
        try
        {
            List list=wdao.getAllWards();
            if(list !=null)
            {
                String stateCode=null;
                String lgaCode=null;
                String cboCode=null;
                String communityCode=null;
                String stateName=null;
                States state=null;
                Lgas lga=null;
                OrganizationRecords or=null;
                Wards community=null;
                for(Object obj:list)
                {
                    if(obj !=null)
                    {
                        community=(Wards)obj;
                        //community=getWard(communityCode);
                        if(community !=null)
                        {
                            communityCode=community.getWard_code();
                            if(community !=null && communityCode.indexOf("/") !=-1)
                            {
                                String[] orgUnitArray=communityCode.split("/");
                                if(orgUnitArray !=null && orgUnitArray.length > 3)
                                {
                                    stateCode=orgUnitArray[0];
                                    lgaCode=orgUnitArray[1];
                                    cboCode=community.getCbo_code();
                                    if(cboCode !=null)
                                    {
                                        or=getOrganizationRecordsByorgCode(cboCode);
                                        if(or !=null)
                                        {
                                            lga=getLgaByLgaCode(lgaCode);
                                            if(lga !=null)
                                            {
                                                //stateCode=lga.getState_code();
                                                stateName=stateCode;
                                                state=getStateByStateCode(stateCode);
                                                if(state !=null)
                                                stateName=state.getName();
                                                community.setWard_name(stateName+"/"+lga.getLga_name()+"/"+or.getOrgName()+"/"+community.getWard_name());
                                                communityList.add(community);
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
        
        return communityList;
    }
    public OvcWithdrawal getBeneficiaryWithdrawn(String uniqueId)
    {
        OvcWithdrawal withdrawal=null;
        try
        {
            OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
            withdrawal=wdao.getOvcWithdrawal(uniqueId);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
       return withdrawal; 
    }
    public List getCBOListByLgasForOrganizationUnitGrpAssignment(String stateCode)
    {
        List cboList=new ArrayList();
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        
        try
        {
            List list=hhedao.getDistinctLgaAndCboAndCommunityCodesFromHouseholdEnrollmentRecords(stateCode);
            if(list !=null)
            {
                String lgaCode=null;
                String cboCode=null;
                String communityCode=null;
                OrganizationRecords or=null;
                Lgas lga=null;
                Wards community=null;
                for(Object obj:list)
                {
                    Object[] objarray=(Object[])obj;
                    lgaCode=(String)objarray[0];
                    cboCode=(String)objarray[1];
                    communityCode=(String)objarray[2];
                    lga=getLgaByLgaCode(lgaCode);
                    or=getOrganizationRecordsByorgCode(cboCode);
                    community=getWard(communityCode);
                    or.setOrgName(or.getOrgName()+"("+community.getWard_name()+"-"+lga.getLga_name()+")");
                    cboList.add(or);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return cboList;
    }
    public HouseholdVulnerabilityAssessment getHouseholdVulnerabilityAssessment(String hhUniqueId,String dateOfAssessment)
    {
        HouseholdVulnerabilityAssessment hva=null;
        try
        {
            HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
            hva=hvadao.getHouseholdVulnerabilityAssessment(hhUniqueId, dateOfAssessment);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return hva;
    }
    public List getCommunityListByLgasForOrganizationUnitGrpAssignment(String stateCode)
    {
        List communityList=new ArrayList();
        //HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        WardDao wdao=new WardDaoImpl();
        try
        {
            //wdao
            List list=wdao.getWardsByStateCode(stateCode);//hhedao.getDistinctLgaAndCboAndCommunityCodesFromHouseholdEnrollmentRecords(stateCode);
            if(list !=null)
            {
                String lgaCode=null;
                String cboCode=null;
                String communityCode=null;
                OrganizationRecords or=null;
                Lgas lga=null;
                Wards community=null;
                for(Object obj:list)
                {
                    community=(Wards)obj;
                    communityCode=community.getWard_code();
                    if(communityCode !=null && communityCode.indexOf("/") !=-1)
                    {
                        String[] objarray=communityCode.split("/");
                        if(objarray.length>3)
                        {
                            lgaCode=objarray[1];
                            cboCode=community.getCbo_code();
                            lga=getLgaByLgaCode(lgaCode);
                            or=getOrganizationRecordsByorgCode(cboCode);
                            //community=getWard(communityCode);
                            community.setWard_code(community.getWard_code().trim());
                            community.setWard_name(community.getWard_name()+"("+or.getOrgName()+"/"+lga.getLga_name()+")");
                            communityList.add(community);
                        }
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return communityList;
    }
    public List getCommunityListByLgasForOrganizationUnitGrpAssignmentFromExistingData(String stateCode)
    {
        List communityList=new ArrayList();
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        
        try
        {
            List list=hhedao.getDistinctLgaAndCboAndCommunityCodesFromHouseholdEnrollmentRecords(stateCode);
            if(list !=null)
            {
                String lgaCode=null;
                String cboCode=null;
                String communityCode=null;
                OrganizationRecords or=null;
                Lgas lga=null;
                Wards community=null;
                for(Object obj:list)
                {
                    Object[] objarray=(Object[])obj;
                    lgaCode=(String)objarray[0];
                    cboCode=(String)objarray[1];
                    communityCode=(String)objarray[2];
                    lga=getLgaByLgaCode(lgaCode);
                    or=getOrganizationRecordsByorgCode(cboCode);
                    community=getWard(communityCode);
                    community.setWard_code(community.getWard_code().trim());
                    community.setWard_name(community.getWard_name()+"("+or.getOrgName()+"/"+lga.getLga_name()+")");
                    communityList.add(community);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return communityList;
    }
    public List getReferralDirectoriesByState(String stateCode)
    {
        List referralList=new ArrayList();
        ReferralDirectoryDao rdao=new ReferralDirectoryDaoImpl();
        try
        {
            //System.err.println("About to fetch lgaList in getReferralDirectoriesByState. statecode is "+stateCode);
            List lgaList=getLgaListFromLgaTableByStateCode(stateCode);
            //System.err.println("lgaList size is "+lgaList.size());
            if(lgaList !=null && !lgaList.isEmpty())
            {
                //System.err.println("lgaList1 size is "+lgaList.size());
                String lgaCode=null;
                String communityCode=null;
                for(int i=0; i<lgaList.size(); i++)
                {
                    Lgas lga=(Lgas)lgaList.get(i);
                    lgaCode=lga.getLga_code();
                    List wardList=getWardListByStateAndLgaCodes(stateCode,lgaCode);
                    //System.err.println("wardList size is "+wardList.size());
                    if(wardList !=null && !wardList.isEmpty())
                    {
                        for(int j=0; j<wardList.size(); j++)
                        {
                            Wards ward=(Wards)wardList.get(j);
                            communityCode=ward.getWard_code();
                            List list=rdao.getReferralDirectories(communityCode);
                            if(list !=null)
                            {
                                //System.err.println("referralList size is "+list.size());
                                referralList.addAll(list);
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
        return referralList;
    }
    public void saveDeletedRecord(String recordId,String newId,String typeOfRecord,String dateRecordCreated)
    {
        DeletedRecordDao drdao=new DeletedRecordDaoImpl();
        try
        {
            drdao.createDeletedRecord(recordId, newId,typeOfRecord, dateRecordCreated);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public List getHheStateList()
    {
        HouseholdEnrollmentDao hheDao=new HouseholdEnrollmentDaoImpl();
        List list=new ArrayList();
        try
        {
            list=hheDao.getHVIStateList();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
    public List getHheLgaList(String stateCode)
    {
        List list=new ArrayList();
        try
        {
            list=getHouseholdEnrollmentDaoInstance().getDistinctLgaCodesByStateCode(stateCode);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
    public List getHheOrgList(String lgaCode)
    {
        HouseholdEnrollmentDao hheDao=new HouseholdEnrollmentDaoImpl();
        List list=new ArrayList();
        try
        {
            list=hheDao.getHVIOrgList(lgaCode);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
    public List getHheOrgListByStateCode(String stateCode)
    {
        HouseholdEnrollmentDao hheDao=new HouseholdEnrollmentDaoImpl();
        List list=new ArrayList();
        try
        {
            list=hheDao.getHVIOrgListByStateCode(stateCode);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
    public List getHheWardList(String orgCode)
    {
        HouseholdEnrollmentDao hheDao=new HouseholdEnrollmentDaoImpl();
        List list=new ArrayList();
        try
        {
            list=hheDao.getHVIWardList(orgCode);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
    public List getHheWardListByStateLgaAndCbo(String stateCode,String lgaCode,String orgCode)
    {
        HouseholdEnrollmentDao hheDao=new HouseholdEnrollmentDaoImpl();
        List communityList=new ArrayList();
        try
        {
            List list=hheDao.getDistinctCommunityCodes(stateCode,lgaCode,orgCode);
            if(list !=null)
            {
                for(int i=0; i<list.size(); i++)
                {
                    communityList.add(getWardByorgCode((String)list.get(i)));
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return communityList;
    }
    public List getWardListByStateAndLgaCodes(String stateCode,String lgaCode)
    {
        WardDao wdao=new WardDaoImpl();
        List list=new ArrayList();
        try
        {
            list=wdao.getWardsByStateAndLgaCodes(stateCode, lgaCode);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
    public Partners getPartnerByPartnerCode(String partnerCode)
    {
        PartnersDao pdao=new PartnersDaoImpl();
        Partners partner=null;
        try
        {
            partner=pdao.getPartner(partnerCode);
            if(partner==null)
            {
                partner=new Partners();
                partner.setPartnerCode(partnerCode);
                partner.setPartnerName(partnerCode);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return partner;
    }
    public States getStateByStateCode(String stateCode)
    {
        StatesDao sdao=new StatesDaoImpl();
        States state=null;
        try
        {
            state=sdao.getStateByStateCode(stateCode);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return state;
    }
    public List getLgaListFromLgaTableByStateCode(String stateCode)
    {
        LgaDao ldao=new LgaDaoImpl();
        List list=new ArrayList();
        try
        {
            List lgaList=ldao.getLgaList(stateCode);
            if(lgaList !=null && !lgaList.isEmpty())
            list=lgaList;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return list;
    }
    public Lgas getLgaByStateAndLgaCode(String stateCode,String lgaCode)
    {
        LgaDao lgdao=new LgaDaoImpl();
        Lgas lga=null;
        try
        {
            lga=lgdao.getLgaByStateAndLgaCode(stateCode, lgaCode);
            if(lga ==null)
            {
                String lgaName=getLgaNameByAlternateLgaCode(lgaCode);
                lga=new Lgas();
                lga.setLga_code(lgaCode);
                lga.setLga_name(lgaCode);
                lga.setState_code(stateCode);
                if(lgaName !=null)
                lga.setLga_name(lgaName);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return lga;
    }
    public Lgas getLgaByLgaCode(String lgaCode)
    {
        LgaDao lgdao=new LgaDaoImpl();
        Lgas lga=null;
        try
        {
            String stateCode="";
            List stateCodeList=this.getHouseholdEnrollmentDaoInstance().getDistinctStateCodesFromHouseholdEnrollmentRecords(lgaCode);
            if(stateCodeList !=null && !stateCodeList.isEmpty() && stateCodeList.get(0) !=null)
            {
                stateCode=stateCodeList.get(0).toString();
                lga=lgdao.getLgaByStateAndLgaCode(stateCode, lgaCode);
            }
            else
            lga=lgdao.getLgaByCode(lgaCode);
            if(lga ==null)
            {
                String lgaName=getLgaNameByAlternateLgaCode(lgaCode);
                lga=new Lgas();
                lga.setLga_code(lgaCode);
                lga.setLga_name(lgaCode);
                lga.setState_code(stateCode);
                lga.setAutogenerated("true");
                if(lgaName !=null)
                lga.setLga_name(lgaName);
                if(getLgaDaoInstance().getLgaByCode(lgaCode) ==null)
                getLgaDaoInstance().saveLga(lga);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return lga;
    }
    private String getLgaNameByAlternateLgaCode(String lgaCode)
    {
        String lgaName=null;
        if(lgaCode !=null)
        {
            try
            {
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            List list=hhedao.getDistinctStateCodesFromHouseholdEnrollmentRecords(lgaCode);
            if(list !=null && !list.isEmpty())
            {
                if(list.get(0) !=null)
                {
                    String stateCode=list.get(0).toString();
                    String stateName=getStateName(stateCode);
                    lgaName=getSupposedLgaName(lgaCode,stateCode);
                    if(lgaName==null)
                    lgaName=lgaCode+" ("+stateName+")";
                    
                    Lgas lga=new Lgas();
                    lga.setLga_code(lgaCode);
                    lga.setLga_name(lgaName);
                    lga.setState_code(stateCode);
                    lga.setAutogenerated("true");
                    if(getLgaDaoInstance().getLgaByCode(lgaCode)==null)
                    getLgaDaoInstance().saveLga(lga); 
                }
            }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return lgaName;
    }
    private String getSupposedLgaName(String lgaCode,String stateCode)
    {
        String lgaName=null;
        if(stateCode.equalsIgnoreCase("AKW"))
        {
            if(lgaCode.equalsIgnoreCase("IKY") || lgaCode.equalsIgnoreCase("IK"))
            lgaName="Ikono";
            else if(lgaCode.equalsIgnoreCase("EPN"))
            lgaName="Ikot Ekpene";
            else if(lgaCode.equalsIgnoreCase("EKK"))
            lgaName="Eket";
            /*else if(lgaCode.equalsIgnoreCase("IIN"))
            lgaName="Unknown LGA (IIN-WOCLIF)";
            else if(lgaCode.equalsIgnoreCase("IIT"))
            lgaName="Unknown LGA (IIT-WOCLIF)";
            else if(lgaCode.equalsIgnoreCase("IQK"))
            lgaName="Unknown LGA (IQK-WOCLIF)";*/
        }
        else if(stateCode.equalsIgnoreCase("ANA"))
        {
            if(lgaCode.equalsIgnoreCase("IDE"))
            lgaName="Idemili North";
        }
        else if(stateCode.equalsIgnoreCase("BOR"))
        {
            if(lgaCode.equalsIgnoreCase("MMC"))
            lgaName="Maiduguri";
        }
        else if(stateCode.equalsIgnoreCase("EDO"))
        {
            if(lgaCode.equalsIgnoreCase("EEW"))
            lgaName="Etsako west";
        }
        else if(stateCode.equalsIgnoreCase("KAN"))
        {
            if(lgaCode.equalsIgnoreCase("SNN"))
            lgaName="Shanono";
        }
        else if(stateCode.equalsIgnoreCase("JIG"))
        {
            if(lgaCode.equalsIgnoreCase("BKD"))
            lgaName="Birnin Kudu";  
        }
        else if(stateCode.equalsIgnoreCase("LAG"))
        {
            if(lgaCode.equalsIgnoreCase("LAG"))
            lgaName="Lagos Mainland";
        }
        else if(stateCode.equalsIgnoreCase("RIV"))
        {
            if(lgaCode.equalsIgnoreCase("020") || lgaCode.equalsIgnoreCase("PHG"))
            lgaName="Port Harcourt";
            else if(lgaCode.equalsIgnoreCase("ELE"))
            lgaName="Eleme";
        }
        return lgaName;
    }
    /*private String getLgaNameByAlternateLgaCode(String lgaCode)
    {
        String lgaName=null;
        if(lgaCode !=null)
        {
            try
            {
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            List list=hhedao.getDistinctStateCodesFromHouseholdEnrollmentRecords(lgaCode);
            if(list !=null && !list.isEmpty())
            {
                if(list.get(0) !=null)
                {
                    String stateCode=list.get(0).toString();
                    if(stateCode.equalsIgnoreCase("AKW"))
                    {
                        if(lgaCode.equalsIgnoreCase("IKY") || lgaCode.equalsIgnoreCase("IK"))
                        lgaName="Ikono";
                        else if(lgaCode.equalsIgnoreCase("EPN"))
                        lgaName="Ikot Ekpene";
                        else if(lgaCode.equalsIgnoreCase("EKK"))
                        lgaName="Eket";
                        else if(lgaCode.equalsIgnoreCase("IIN"))
                        lgaName="Unknown LGA (IIN-WOCLIF)";
                        else if(lgaCode.equalsIgnoreCase("IIT"))
                        lgaName="Unknown LGA (IIT-WOCLIF)";
                        else if(lgaCode.equalsIgnoreCase("IQK"))
                        lgaName="Unknown LGA (IQK-WOCLIF)";
                    }
                    else if(stateCode.equalsIgnoreCase("ANA"))
                    {
                        if(lgaCode.equalsIgnoreCase("IDE"))
                        lgaName="Idemili North";
                    }
                    else if(stateCode.equalsIgnoreCase("BOR"))
                    {
                        if(lgaCode.equalsIgnoreCase("MMC"))
                        lgaName="Maiduguri";
                    }
                    else if(stateCode.equalsIgnoreCase("EDO"))
                    {
                        if(lgaCode.equalsIgnoreCase("EEW"))
                        lgaName="Etsako west";
                    }
                    else if(stateCode.equalsIgnoreCase("KAN"))
                    {
                        if(lgaCode.equalsIgnoreCase("SNN"))
                        lgaName="Shanono";
                    }
                    else if(stateCode.equalsIgnoreCase("JIG"))
                    {
                        if(lgaCode.equalsIgnoreCase("BKD"))
                        lgaName="Birnin Kudu";  
                    }
                    else if(stateCode.equalsIgnoreCase("LAG"))
                    {
                        if(lgaCode.equalsIgnoreCase("LAG"))
                        lgaName="Lagos Mainland";
                    }
                    else if(stateCode.equalsIgnoreCase("RIV"))
                    {
                        if(lgaCode.equalsIgnoreCase("020") || lgaCode.equalsIgnoreCase("PHG"))
                        lgaName="Port Harcourt";
                        else if(lgaCode.equalsIgnoreCase("ELE"))
                        lgaName="Eleme";
                    }
                }
            }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return lgaName;
    }*/
    public String getWardQueryForMetadataExport(String[] params)
    {
        String orgQueryCriteria=" ";
        List list=new ArrayList();
        String stateCode=params[0];
        String lgaCode=params[1];
        String orgCode=params[2];
        String communityCode=params[3];
        //String orgQueryCriteria=" ";
        String stateLgaCbo=null;
        if(stateCode !=null && !stateCode.equalsIgnoreCase("All"))
        {
            stateLgaCbo=stateCode;
            if(lgaCode !=null && !lgaCode.equalsIgnoreCase("All"))
            {
                stateLgaCbo=stateCode+"/"+lgaCode;
                orgQueryCriteria="where ward.ward_code like '%"+stateLgaCbo+"%'";
                if(orgCode !=null && !orgCode.equalsIgnoreCase("All"))
                {
                    stateLgaCbo=orgCode;
                    orgQueryCriteria="where ward.cbo_code='"+orgCode+"'";
                    if(communityCode !=null && !communityCode.equalsIgnoreCase("All"))
                    {
                        communityCode=communityCode.trim();
                        orgQueryCriteria=orgQueryCriteria+" and ward.ward_code='"+communityCode+"'";
                    }
                }
            }
        
        
        }
        return orgQueryCriteria;
    }
    public OrganizationRecords getOrganizationRecordsByorgCode(String orgCode)
    {
        OrganizationRecordsDao orgdao=new OrganizationRecordsDaoImpl();
        OrganizationRecords orgRecord=null;
        try
        {
            orgRecord=orgdao.getOrganizationRecord(orgCode);
            if(orgRecord==null)
            {
                orgRecord=new OrganizationRecords();
                orgRecord.setOrgCode(orgCode);
                orgRecord.setOrgName(orgCode);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return orgRecord;
    }
    public Wards getWardByorgCode(String wardCode)
    {
        WardDao dao=new WardDaoImpl();
        Wards ward=null;
        try
        {
            ward=dao.getWards(wardCode);
            if(ward==null)
            {
                ward=new Wards();
                ward.setWard_code(wardCode);
                ward.setWard_name(wardCode);
                if(wardCode.indexOf("/") !=-1)
                ward.setCbo_code(wardCode.substring(0, wardCode.lastIndexOf("/")));
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return ward;
    }
    public List getUserAssignedStates(HttpSession session)
    {
        AccessManager acm=new AccessManager();
        List list=acm.getStateListForReports(session);
        return list;
    }
    public List getUserAssignedLgas(String stateCode,HttpSession session)
    {
        AppUtility appUtil=new AppUtility();
        List lgaList =new ArrayList();
        User user=appUtil.getUser(session);
        if(user !=null)//
        lgaList =appUtil.getAccessManager().getLgaByStateUserGroupIdAndLevel(stateCode, user.getAssignedGroupId());
        //getLgaListForStartup(stateCode, user.getAssignedGroupId());
        //
        return lgaList;
    }
    public List getUserAssignedCBOs(String stateCode,String lgaCode,HttpSession session)
    {
        AppUtility appUtil=new AppUtility();
        List orgList =new ArrayList();
        User user=appUtil.getUser(session);
        if(user !=null)
        orgList =appUtil.getAccessManager().getCBOByStateUserGroupIdAndLevel(stateCode,lgaCode, user.getAssignedGroupId());
        return orgList;
    }
    public List getUserAssignedCommunities(String stateCode,String lgaCode,String cboCode,HttpSession session)
    {
        AppUtility appUtil=new AppUtility();
        List communityList =new ArrayList();
        User user=appUtil.getUser(session);
        if(user !=null)
        communityList =appUtil.getAccessManager().getCommunityByStateUserGroupIdAndLevel(stateCode,lgaCode, cboCode,user.getAssignedGroupId());
        return communityList;
    }
    public List getListOfYearsFromHhe()
    {
        List list=new ArrayList();
        AppUtility appUtil=new AppUtility();
        try
        {
            //NomisDate date=new NomisDate();
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            List yearList=hhedao.getDistinctYearOfAssessment();
            
                if(yearList !=null)
                {
                    int startCount=0;
                    int endCount=yearList.size();
                    int startYear=Integer.parseInt(yearList.get(0).toString());
                    int incrementedYear=startYear;
                    int endYear=Integer.parseInt(yearList.get(yearList.size()-1).toString());
                    int currentYear=0;
                    String currentDate=appUtil.getCurrentDate();
                    if(currentDate !=null && currentDate.indexOf("-") !=-1)
                    {
                        String[] currentDateArray=currentDate.split("-");
                        currentYear=Integer.parseInt(currentDateArray[0]);
                    }
                    if(endYear <currentYear)
                    endYear=currentYear;
                    endYear++;
                    for(int i=startCount; i<endCount; i++)
                    {
                        list.add(incrementedYear++);
                        if(i==endCount-1 && (endYear <currentYear))
                        {
                            startCount=endYear;
                            startCount=currentYear+1;
                        }
                        //System.err.println("year is "+incrementedYear);
                    }
                }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return list;
    }
    public List getStateListFromHheByPartner(String partnerCode)
    {
        List stateList=new ArrayList();
        try
        {
            List list=this.getHouseholdEnrollmentDaoInstance().getDistinctStateCodesByPartner(partnerCode);
            if(list !=null)
            {
                for(int i=0; i<list.size(); i++)
                {
                   stateList.add(getStateByStateCode((String)list.get(i)));
                }
            }
        }
        catch(Exception ex)
        {
            NomisLogManager.logStackTrace(ex);
        }
        return stateList;
    }
    public List getStateListFromHhe()
    {
        List list=getHheStateList();
        List stateList=new ArrayList();
        if(list !=null)
        {
            for(int i=0; i<list.size(); i++)
            {
               stateList.add(getStateByStateCode((String)list.get(i)));
            }
        }
        
        return stateList;
    }
    public List getLgaListFromHheByStateAndPartnerCodes(String stateCode,String partnerCode)
    {
        List lgaList=new ArrayList();
        try
        {
            List list=this.getHouseholdEnrollmentDaoInstance().getDistinctLgaCodesByStateAndPartnerCodes(stateCode, partnerCode);
            Lgas lga=null;
            String lgaCode=null;
            if(list !=null)
            {
                for(int i=0; i<list.size(); i++)
                {
                    lgaCode=(String)list.get(i);
                    lga=getLgaByLgaCode(lgaCode);
                    if(lga !=null)
                    {
                        if(!lga.getState_code().equalsIgnoreCase(stateCode))
                        {
                            lga.setLga_name(lgaCode+" ("+this.getStateName(stateCode)+")");
                        }
                        lgaList.add(lga);
                    }
                }
            }
        }
        catch(Exception ex)
        {
            NomisLogManager.logStackTrace(ex);
            
        }
        return lgaList;
    }
    public List getLgaListFromHhe(String stateCode)
    {
        List list=getHheLgaList(stateCode);
        List lgaList=new ArrayList();
        Lgas lga=null;
        String lgaCode=null;
        if(list !=null)
        {
            for(int i=0; i<list.size(); i++)
            {
                lgaCode=(String)list.get(i);
                lga=getLgaByLgaCode(lgaCode);
                if(lga !=null)
                {
                    if(!lga.getState_code().equalsIgnoreCase(stateCode))
                    {
                        lga.setLga_name(lgaCode+" ("+this.getStateName(stateCode)+")");
                    }
                    lgaList.add(lga);
                }
            }
        }
        return lgaList;
    }
    public List getOrganizationRecordsByStateCodeFromHhe(String stateCode)
    {
        List list=getHheOrgListByStateCode(stateCode);
        List orgList=new ArrayList();
        if(list !=null)
        {
            for(int i=0; i<list.size(); i++)
            {
               orgList.add(getOrganizationRecordsByorgCode((String)list.get(i)));
            }
        }
        return orgList;
    }
    public List getOrganizationRecordsFromHhe(String lgaCode)
    {
        List list=getHheOrgList(lgaCode);
        List orgList=new ArrayList();
        
        if(list !=null)
        {
            for(int i=0; i<list.size(); i++)
            {
               OrganizationRecords orgRecord=getOrganizationRecordsByorgCode((String)list.get(i));
               orgList.add(orgRecord);
            }
        }
        return orgList;
    }
    public List getWardsFromHhe(String orgCode)
    {
        List list=getHheWardList(orgCode);
        List wardList=new ArrayList();
        if(list !=null)
        {
            for(int i=0; i<list.size(); i++)
            {
               wardList.add(getWardByorgCode((String)list.get(i)));
            }
        }
        return wardList;
    }
    public String generateNewIdFromOldId(String oldOvcId)
    {
        String oldId=null;
        String newId=null;
        if(oldOvcId !=null && oldOvcId.indexOf("/") !=-1)
        {
             oldId=oldOvcId.replace(" ", "");
             oldId=oldId.trim();
             String[] oldOvcIdArray=oldId.split("/");
             if(oldOvcIdArray.length > 4)
             {
                return oldOvcId;
             }
             newId=oldOvcIdArray[0].trim()+"/"+oldOvcIdArray[1].trim()+"/"+oldOvcIdArray[2].trim()+"/"+oldOvcIdArray[3].trim()+"/"+oldOvcIdArray[3].trim();
             //System.err.println("oldId is "+oldId+" and newId is "+newId);
        }
        else
        {
            return oldOvcId;
        }
        return newId;
    }
    public void correctWardCodesInEnrollmentData()
    {
        String query="from Ovc ovc where ovc.ward like '%//%'";
        List list=execReportQuery(query);
        String wardCode=null;


        if(list !=null)
        {
            OvcDao ovcDao=new OvcDaoImpl();

            Ovc ovc=null;
            for(int i=0; i<list.size(); i++)
            {
                ovc=(Ovc)list.get(i);
                wardCode=ovc.getWard();
                System.err.println("Old ward code is "+wardCode);
                wardCode=wardCode.replace("//", "/");
                
                System.err.println("New ward code is "+wardCode);
                try
                {
                    ovcDao.deleteOvc(ovc);
                    ovc.setWard(wardCode);
                    ovcDao.saveOvc(ovc,false,false);
                }
                catch(Exception ex)
                {
                   ex.printStackTrace();
                }

            }
        }
    }
   public void changeCommunity(String oldHhUniqueId,String newCommunityCode)
    {
        try
        {
            
            if(newCommunityCode !=null)
            {
                HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
                HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
                HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
                AppUtility appUtil=new AppUtility();
                HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(oldHhUniqueId);
                if(hhe !=null)
                {
                    System.err.println("Old hhUniqueId: "+hhe.getHhUniqueId()+" LGA: "+hhe.getLgaCode()+" CBO: "+hhe.getOrgCode()+" Community: "+hhe.getCommunityCode());
                    System.err.println("newCommunityCode: "+newCommunityCode);
                    if(newCommunityCode.indexOf("/") !=-1)
                    {
                        WardDao wdao=new WardDaoImpl();
                         Wards ward=wdao.getWards(newCommunityCode);
                                                
                           if(ward !=null)
                           {
                                String[] codeArray=newCommunityCode.split("/");
                                String lgaCode=codeArray[1];
                                String newCboCode=ward.getCbo_code();
                                String communityCodePart=newCommunityCode.substring(0, newCommunityCode.lastIndexOf("/"));
                                String hhUniqueIdPart=oldHhUniqueId.substring(0, oldHhUniqueId.lastIndexOf("/"));
                                String newHhUniqueId=oldHhUniqueId.replace(hhUniqueIdPart, communityCodePart);
                                //hhe.setHhUniqueId(newHhUniqueId);

                                hhe.setLgaCode(lgaCode);
                                hhe.setCommunityCode(newCommunityCode);
                                hhe.setOrgCode(newCboCode);
                                
                                hhe.setDateOfEntry(appUtil.getCurrentDate());
                                newHhUniqueId=hhedao.generateNewHhUniqueId(newHhUniqueId);
                                hhe.setHhEnrollmentId(newHhUniqueId);
                                //update the household with new Id and community
                                hhedao.updateHouseholdEnrollment(hhe);
                                System.err.println("New hhUniqueId: "+hhe.getHhUniqueId()+" LGA: "+hhe.getLgaCode()+" CBO: "+hhe.getOrgCode()+" Community: "+hhe.getCommunityCode());                                
                           }
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    /*public void changeCommunity(String oldHhUniqueId,String newCommunityCode)
    {
        try
        {
            
            if(newCommunityCode !=null)
            {
                HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
                HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
                HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
                AppUtility appUtil=new AppUtility();
                HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(oldHhUniqueId);
                if(hhe !=null)
                {
                    System.err.println("Old hhUniqueId: "+hhe.getHhUniqueId()+" LGA: "+hhe.getLgaCode()+" CBO: "+hhe.getOrgCode()+" Community: "+hhe.getCommunityCode());
                    System.err.println("newCommunityCode: "+newCommunityCode);
                    if(newCommunityCode.indexOf("/") !=-1)
                    {
                        WardDao wdao=new WardDaoImpl();
                         Wards ward=wdao.getWards(newCommunityCode);
                                                
                           if(ward !=null)
                           {
                                String[] codeArray=newCommunityCode.split("/");
                                String lgaCode=codeArray[1];
                                String newCboCode=ward.getCbo_code();
                                String communityCodePart=newCommunityCode.substring(0, newCommunityCode.lastIndexOf("/"));
                                String hhUniqueIdPart=oldHhUniqueId.substring(0, oldHhUniqueId.lastIndexOf("/"));
                                String newHhUniqueId=oldHhUniqueId.replace(hhUniqueIdPart, communityCodePart);
                                hhe.setHhUniqueId(newHhUniqueId);

                                hhe.setLgaCode(lgaCode);
                                hhe.setCommunityCode(newCommunityCode);
                                hhe.setOrgCode(newCboCode);
                                
                                hhe.setDateOfEntry(appUtil.getCurrentDate());
                                if(!oldHhUniqueId.equalsIgnoreCase(newHhUniqueId))
                                {
                                    newHhUniqueId=hhedao.generateNewHhUniqueId(newHhUniqueId);
                                    hhe.setHhUniqueId(newHhUniqueId);
                                    //Save the household with new Id and community and delete the old record
                                    hhedao.saveHouseholdEnrollment(hhe);
                                    hhe.setHhUniqueId(oldHhUniqueId);
                                    hhe.setNewHhUniqueId(newHhUniqueId);
                                    hhedao.deleteHouseholdEnrollmentOnly(hhe);
                                    
                                    hvadao.changeHhUniqueIdInHouseholdVulnerabilityAssessmentData(oldHhUniqueId,newHhUniqueId);
                                    correctCaregiverId(oldHhUniqueId,newHhUniqueId);
                                    hhsdao.changeHhUniqueIdInHouseholdService(oldHhUniqueId,newHhUniqueId);
                                    correctOvcIdInEnrollmentData(oldHhUniqueId,newHhUniqueId);
                                }
                                else
                                {
                                    hhedao.updateHouseholdEnrollment(hhe);
                                }
                                System.err.println("New hhUniqueId: "+hhe.getHhUniqueId()+" LGA: "+hhe.getLgaCode()+" CBO: "+hhe.getOrgCode()+" Community: "+hhe.getCommunityCode());
                                
                           }
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }*/
    public void mergeHouseholds(String oldHhUniqueId,String newHhUniqueId)
    {
        HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
        try
        {
            hvadao.changeHhUniqueIdInHouseholdVulnerabilityAssessmentData(oldHhUniqueId,newHhUniqueId);
            correctCaregiverId(oldHhUniqueId,newHhUniqueId);
            hhsdao.changeHhUniqueIdInHouseholdService(oldHhUniqueId,newHhUniqueId);
            correctOvcIdInEnrollmentData(oldHhUniqueId,newHhUniqueId);
        
            HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(oldHhUniqueId);
            if(hhe !=null)
            {
                hhe.setNewHhUniqueId(newHhUniqueId);
                hhedao.deleteHouseholdEnrollmentOnly(hhe);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void mergeCaregivers(String oldCaregiverId,String newCaregiverId)
    {
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
        OvcDao ovcdao=new OvcDaoImpl();
        FollowupDao fdao=new FollowupDaoImpl();
        try
        {
            String hhUniqueId=null;
            Caregiver cgiver=cgiverdao.getCaregiverByCaregiverId(newCaregiverId);
            if(cgiver !=null)
            hhUniqueId=cgiver.getHhUniqueId();
            else
            cgiver=cgiverdao.getCaregiverByCaregiverId(oldCaregiverId);
            if(cgiver !=null)
            hhUniqueId=cgiver.getHhUniqueId();
            ovcdao.changeCaregiverId(oldCaregiverId, newCaregiverId);
            hhsdao.changeCaregiverId(oldCaregiverId, hhUniqueId,newCaregiverId);
            fdao.changeCaregiverId(oldCaregiverId, newCaregiverId);
            Caregiver cgiver2=cgiverdao.getCaregiverByCaregiverId(oldCaregiverId);
            if(cgiver2 !=null)
            {
                cgiver2.setNewCaregiverId(newCaregiverId);
                cgiverdao.deleteCaregiver(cgiver2);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void correctHhIdInHouseholdEnrollmentData(String oldStateLgaCboCode, String newStateLgaCboCode,String newCommunityCode)
    {
        AppUtility appUtil=new AppUtility();
        WardDao wdao=new WardDaoImpl();
        
        String[] codeArray=newStateLgaCboCode.split("/");
        String lgaCode=codeArray[1];
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        
        String oldHhUniqueId=null;
        String newHhUniqueId=null;
        
           try
           {
               Wards ward=wdao.getWards(newCommunityCode);
               if(ward !=null)
               {
                   String newCboCode=ward.getCbo_code();
                   //hhedao.correctLgaCodesInHouseholdEnrollmentRecords();
                    
                    HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(newHhUniqueId);
                    
                        if(oldHhUniqueId !=null)
                        newHhUniqueId=oldHhUniqueId.replace(oldStateLgaCboCode, newStateLgaCboCode);
                        
                        System.err.println("New ward code is "+newCommunityCode);
                            hhe.setNewHhUniqueId(newHhUniqueId);
                            hhedao.deleteHouseholdEnrollmentOnly(hhe);
                            hhe.setLgaCode(lgaCode);
                            hhe.setCommunityCode(newCommunityCode);
                            hhe.setOrgCode(newCboCode);
                            newHhUniqueId=hhedao.generateNewHhUniqueId(newHhUniqueId);
                            hhe.setHhUniqueId(newHhUniqueId);
                            hhe.setDateOfEntry(appUtil.getCurrentDate());
                            hhedao.saveHouseholdEnrollment(hhe);

                            System.err.println("Household with uniqueid "+hhe.getHhUniqueId()+" saved");
                            
                            HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
                            HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
                            hvadao.changeHhUniqueIdInHouseholdVulnerabilityAssessmentData(oldHhUniqueId,newHhUniqueId);
                            correctCaregiverId(oldHhUniqueId,newHhUniqueId);
                            hhsdao.changeHhUniqueIdInHouseholdService(oldHhUniqueId,newHhUniqueId);
                            correctOvcIdInEnrollmentData(oldHhUniqueId,newHhUniqueId);
               }
                
            }
            catch(Exception ex)
            {
               ex.printStackTrace();
            }
        
    }
    public void correctHhIdInHouseholdEnrollmentData(String stateLgaCode, String newStateLgaCode,String oldCboCode,String newCboCode)
    {
        AppUtility appUtil=new AppUtility();
        String oldStateLgaCboCode=stateLgaCode+"/"+oldCboCode;
        String newStateLgaCboCode=newStateLgaCode+"/"+newCboCode;
        String[] codeArray=newStateLgaCode.split("/");
        String lgaCode=codeArray[1];
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        //String query="from HouseholdEnrollment hhe where hhe.hhUniqueId like '%"+oldStateLgaCboCode+"%'";
        //execReportQuery(query);
        String oldHhUniqueId=null;
        String newHhUniqueId=null;
        
        String oldWardCode=null;
        String newWardCode=null;
        
        
        
           try
           {
               //hhedao.correctLgaCodesInHouseholdEnrollmentRecords();
               List list=hhedao.getListOfHouseholdsByHhuniqueIdPart(oldStateLgaCboCode);
               if(list !=null)
                {
            
                    HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
                    HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
                    HouseholdEnrollment hhe=null;
                    for(int i=0; i<list.size(); i++)
                    {
                        hhe=(HouseholdEnrollment)list.get(i);
                        oldHhUniqueId=hhe.getHhUniqueId();
                        System.err.println("Old hhuniqueid is "+oldHhUniqueId);

                        if(oldHhUniqueId !=null)
                        newHhUniqueId=oldHhUniqueId.replace(oldStateLgaCboCode, newStateLgaCboCode);
                        oldWardCode=hhe.getCommunityCode();

                        if(oldWardCode !=null)
                        newWardCode=oldWardCode.replace(oldStateLgaCboCode, newStateLgaCboCode);
                        System.err.println("New ward code is "+newWardCode);
                            hhe.setNewHhUniqueId(newHhUniqueId);
                            hhedao.deleteHouseholdEnrollmentOnly(hhe);
                            hhe.setLgaCode(lgaCode);
                            hhe.setCommunityCode(newWardCode);
                            hhe.setOrgCode(newStateLgaCboCode);
                            newHhUniqueId=hhedao.generateNewHhUniqueId(newHhUniqueId);
                            hhe.setHhUniqueId(newHhUniqueId);
                            hhe.setDateOfEntry(appUtil.getCurrentDate());
                            hhedao.saveHouseholdEnrollment(hhe);

                            System.err.println("Household with uniqueid "+hhe.getHhUniqueId()+" saved");
                            hvadao.changeHhUniqueIdInHouseholdVulnerabilityAssessmentData(oldHhUniqueId,newHhUniqueId);
                            correctCaregiverId(oldHhUniqueId,newHhUniqueId);
                            hhsdao.changeHhUniqueIdInHouseholdService(oldHhUniqueId,newHhUniqueId);
                            correctOvcIdInEnrollmentData(oldHhUniqueId,newHhUniqueId);
                    }
                }
            }
            catch(Exception ex)
            {
               ex.printStackTrace();
            }
        
    }
    public void correctOvcIdInEnrollmentData(String oldHhUniqueId, String newHhUniqueId)
    {
        try
        {
            OvcDao ovcDao=new OvcDaoImpl();
            List list=ovcDao.getOvcListPerHousehold(oldHhUniqueId);
            String oldOvcId=null;
            String newOvcId=null;

            if(list !=null)
            {
                Ovc ovc=null;
                for(int i=0; i<list.size(); i++)
                {
                    ovc=(Ovc)list.get(i);
                    oldOvcId=ovc.getOvcId();
                    newOvcId=oldOvcId.replace(oldHhUniqueId, newHhUniqueId);
                    ovcDao.changeOvcId(oldOvcId, newOvcId);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    
    public void correctCaregiverId(String oldHhUniqueId,String newHhUniqueId)
    {
        AppUtility appUtil=new AppUtility();
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        OvcDao ovcdao=new OvcDaoImpl();
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        try
        {
            List list=cgiverdao.getListOfCaregiversFromSameHousehold(oldHhUniqueId);
            String oldCaregiverId=null;
            String newCaregiverId=null;
            if(list !=null)
            {
                Caregiver cgiver=null;
                Caregiver duplicateCgiver=null;
                for(int i=0; i<list.size(); i++)
                {
                    cgiver=(Caregiver)list.get(i);
                    oldCaregiverId=cgiver.getCaregiverId();
                    newCaregiverId=oldCaregiverId.replace(oldHhUniqueId, newHhUniqueId);
                    cgiver.setNewCaregiverId(newCaregiverId);
                    cgiverdao.deleteCaregiver(cgiver);
                    
                    cgiver.setHhUniqueId(newHhUniqueId);
                    String caregiverId=newCaregiverId;
                    cgiver.setCaregiverId(caregiverId);
                    
                    cgiver.setDateModified(appUtil.getCurrentDate());
                    duplicateCgiver=cgiverdao.getCaregiverByCaregiverId(caregiverId);
                    if(duplicateCgiver==null)
                    cgiverdao.saveCaregiver(cgiver);
                    else if(duplicateCgiver.getCaregiverFullName().equalsIgnoreCase(cgiver.getCaregiverFullName()))
                    {
                        cgiverdao.updateCaregiver(cgiver);
                    }
                    else
                    {
                        caregiverId=cgiverdao.generateCaregiverId(cgiver.getHhUniqueId());
                        cgiver.setCaregiverId(caregiverId);
                        cgiverdao.saveCaregiver(cgiver);
                    }
                    List ovcList=ovcdao.getListOfOvcsPerCaregiver(" ", oldCaregiverId);
                    if(ovcList !=null)
                    {
                        Ovc ovc=null;
                        for(int j=0; j<ovcList.size(); j++)
                        {
                            ovc=(Ovc)ovcList.get(j);
                            ovc.setDateOfEntry(appUtil.getCurrentDate());
                            ovc.setCaregiverId(caregiverId);
                            ovcdao.updateOvc(ovc,true,true);
                        }
                    }
                    hsudao.changeClientId(oldCaregiverId, newCaregiverId);
                    System.err.println("Caregiver with "+cgiver.getCaregiverId()+" processed");
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void correctOvcIdInCsiData(String oldOvcId, String newOvcId)
    {
        ChildStatusIndexDao csiDao=new ChildStatusIndexDaoImpl();
            ChildStatusIndex csi=null;
            try
            {
                List list=csiDao.getCsiListOrderedByDateAsc(oldOvcId);
                for(Object obj:list)
                {
                    csi=(ChildStatusIndex)obj;
                    csiDao.deleteChildStatusIndex(csi);
                    if(csiDao.getChildStatusIndex(newOvcId, csi.getCsiDate())==null)
                    {
                        csi.setOvcId(newOvcId);
                        csiDao.saveChildStatusIndex(csi);
                    }
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
    }
    public void correctOvcIdInServiceData(String oldOvcId, String newOvcId)
    {
        OvcServiceDao serviceDao=new OvcServiceDaoImpl();
        OvcService service=null;
        try
            {
                List list=serviceDao.getOvcServices(oldOvcId);
                for(Object obj:list)
                {
                    service=(OvcService)obj;
                    serviceDao.deleteService(service);
                    if(serviceDao.getOvcServiceForTheMth(newOvcId, service.getServicedate())==null)
                    {
                        service.setOvcId(newOvcId);
                        serviceDao.saveOvcService(service,true,true);
                    }
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
    }
    public void correctOrgCodes(String oldCode, String newCode)
    {
        OrganizationRecordsDao orgdao=new OrganizationRecordsDaoImpl();
            OrganizationRecords orgRecords=null;
            String[] codeParts=newCode.split("/");
            try
            {
                orgRecords=orgdao.getOrganizationRecord(oldCode);
                if(orgRecords !=null)
                {
                    orgdao.deleteOrgRecords(orgRecords);
                    orgRecords.setState(codeParts[0]);
                    orgRecords.setLga(codeParts[1]);
                    orgRecords.setOrgCode(newCode);
                    OrganizationRecords duplicateOrgRecords=orgdao.getOrganizationRecord(newCode);
                   if(duplicateOrgRecords ==null)
                   {
                       orgdao.saveOrgRecords(orgRecords);
                   }
                   else
                   orgdao.updateOrgRecords(orgRecords);
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
    }
    public void correctWardCodes(String oldWardCode, String newWardCode,String newCboCode)
    {
            WardDao wdao=new WardDaoImpl();
            Wards ward=null;
            try
            {
                ward=wdao.getWards(oldWardCode);
                if(ward !=null)
                {
                    wdao.deleteWard(ward);
                    ward.setCbo_code(newCboCode);
                    ward.setWard_code(newWardCode);
                   Wards duplicateWard=wdao.getWards(newWardCode);

                   if(duplicateWard ==null)
                   {
                       wdao.saveWard(ward);
                   }
                   else
                   wdao.updateWard(ward);
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
    }
    public String getOvcWithdrawnFromProgramQuery(String withdrawnFromProgram)
    {
        String withdrawnFromProgramQuery=" ";
        if(withdrawnFromProgram !=null && (withdrawnFromProgram.equalsIgnoreCase("Yes") || withdrawnFromProgram.equalsIgnoreCase("No")))
        {
            withdrawnFromProgram=withdrawnFromProgram.toUpperCase().trim();
            withdrawnFromProgramQuery=" and UPPER(ovc.withdrawnFromProgram)='"+withdrawnFromProgram+"'";
        }
        return withdrawnFromProgramQuery;
    }
    public String getCaregiverWithdrawnFromProgramQuery(String withdrawnFromProgram)
    {
        String withdrawnFromProgramQuery=" ";
        if(withdrawnFromProgram !=null && (withdrawnFromProgram.equalsIgnoreCase("Yes") || withdrawnFromProgram.equalsIgnoreCase("No")))
        {
            withdrawnFromProgram=withdrawnFromProgram.toUpperCase().trim();
            withdrawnFromProgramQuery=" and UPPER(cgiver.withdrawnFromProgram)='"+withdrawnFromProgram+"' ";
        }
        return withdrawnFromProgramQuery;
    }
    public String getHouseholdWithdrawnFromProgramQuery(String withdrawnFromProgram)
    {
        String withdrawnFromProgramQuery=" ";
        if(withdrawnFromProgram !=null && (withdrawnFromProgram.equalsIgnoreCase("Yes") || withdrawnFromProgram.equalsIgnoreCase("No")))
        {
            withdrawnFromProgram=withdrawnFromProgram.toUpperCase().trim();
            withdrawnFromProgramQuery=" and UPPER(hhe.withdrawnFromProgram)='"+withdrawnFromProgram+"'";
        }
        return withdrawnFromProgramQuery;
    }
    public String getAdditionalEnrollmentQuery(String additionalEnrollmentQuery)
    {
        AppUtility appUtil=new AppUtility();
        if(additionalEnrollmentQuery !=null && !additionalEnrollmentQuery.equals(" ") && appUtil.isString(additionalEnrollmentQuery))
        {
            if(!additionalEnrollmentQuery.startsWith("and"))
            additionalEnrollmentQuery=" and "+additionalEnrollmentQuery;
            
            if(additionalEnrollmentQuery !=null)
            additionalEnrollmentQuery=additionalEnrollmentQuery.replace("and  and", "and");
            //additionalEnrollmentQuery=additionalEnrollmentQuery.replaceAll("and and", "and");
        }
        return additionalEnrollmentQuery;
    }
    public String getAdditionalServiceQuery(String additionalServiceQuery)
    {
        if(!additionalServiceQuery.equals(" "))
            additionalServiceQuery=" and "+additionalServiceQuery;
        return additionalServiceQuery;
    }
    public List createSqlQuery(String query)
    {
        List list=null;
        try
        {
            //Desktop.getDesktop().browse(new URI("hhh"));
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createSQLQuery(query).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
            //throw new Exception();
        }
        return list;
    }
    public void updateDatabase(String query) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.createSQLQuery(query).executeUpdate();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            //ex.printStackTrace();
            throw new Exception(ex);
        }
    }
    
    public String getEligibilityCriteriaId()
    {
        EligibilityCriteriaDao eldao=new EligibilityCriteriaDaoImpl();
        String eligibilityId=" ";
        List elList=new ArrayList();
        try
        {
            elList=eldao.getEligibilityCriteria();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        int j=0;
        for(Object el:elList)
        {

            EligibilityCriteria elc=(EligibilityCriteria)el;

            if(j<elList.size()-1)
            eligibilityId+=elc.getEligibilityName()+", ";
            else
            eligibilityId+=elc.getEligibilityName();
            j++;
        }
        return eligibilityId;
    }
    public String capitalizeFirstLetter(String str)
    {
        String firstLetter=null;
        if(str.length()>1)
        {
            firstLetter=str.substring(0, 1).toUpperCase();
            str=firstLetter+str.substring(1);
        }
        else
        str=str.toUpperCase();
        return str;
    }
    public int compareTotalScore(List list)
    {
        int j=0;
        try
        {
            int baseLineCsiScore=Integer.parseInt(list.get(0).toString());
            for(int i=1; i<list.size(); i++)
            {
                int otherScore=Integer.parseInt(list.get(i).toString());
                if(baseLineCsiScore < otherScore)
                {
                   j=1;
                   break;
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return j;
    }
    /*public String getHivStatusCriteria(String status)
    {
        String hivStatusUnknownCriteria="";
        if(status.equalsIgnoreCase("unknown"))
        hivStatusUnknownCriteria="(ovc.hivStatus is null or ovc.hivStatus=' ' or ovc.hivStatus='  ' or ovc.hivStatus='HIV status unknown' or ovc.hivStatus='') ";
        else if(status.equalsIgnoreCase("positive"))
        hivStatusUnknownCriteria="(ovc.hivStatus ='HIV positive') ";
        else if(status.equalsIgnoreCase("negative"))
        hivStatusUnknownCriteria="(ovc.hivStatus ='HIV negative') ";
        return hivStatusUnknownCriteria;
    }*/
    /*public String getHivStatusCriteria(String status)
    {
        String hivStatusUnknownCriteria="";
        if(status.equalsIgnoreCase("unknown"))
        hivStatusUnknownCriteria="(ovc.activeHivStatus='unknown') ";
        else if(status.equalsIgnoreCase("negative"))
        hivStatusUnknownCriteria="(ovc.activeHivStatus='negative') ";
        else if(status.equalsIgnoreCase("positive"))
        hivStatusUnknownCriteria="(ovc.activeHivStatus='positive') ";
        return hivStatusUnknownCriteria;
    }*/
    public String getHivStatusUpdateQueryPart()
    {
        return "HivStatusUpdate hsu ";
    }
    public String getEnrolledInCareQueryCriteria(String beneficiaryType)
    {
        String hivStatusUnknownCriteria="and (hsu.hivStatus like '%"+NomisConstant.HIV_POSITIVE+"%' and hsu.recordStatus='active' and hsu.clientType='ovc' and UPPER(hsu.clientEnrolledInCare='YES')) ";
        return hivStatusUnknownCriteria;
    }
    public String getEnrolledOnARTQueryCriteria(String beneficiaryType)
    {
        String hivStatusUnknownCriteria=" and (hsu.hivStatus like '%"+NomisConstant.HIV_POSITIVE+"%' and hsu.recordStatus='active' and hsu.clientType='"+beneficiaryType+"' and UPPER(hsu.enrolledOnART)='YES') ";
        return hivStatusUnknownCriteria;
    }
    public String getActivePositiveOrNegativeHivStatusCriteria(String beneficiaryType)
    {
        return "((hsu.hivStatus like '%"+NomisConstant.HIV_NEGATIVE+"%' or hsu.hivStatus like '%"+NomisConstant.HIV_POSITIVE+"%') and hsu.recordStatus='active' and hsu.clientType='"+beneficiaryType+"') ";
    }
    public String getOvcBaselineHivStatusCriteria(String status)
    {
        String hivStatusCriteria="";
        if(status.equalsIgnoreCase(NomisConstant.HIV_UNKNOWN))
        hivStatusCriteria=" and (ovc.hivStatus='"+status+"' or ovc.hivStatus='"+NomisConstant.HIV_TEST_NOT_INDICATED+"' or ovc.hivStatus='"+NomisConstant.HIV_TEST_NOT_DONE+"' or ovc.hivStatus='"+NomisConstant.HIV_RESULT_NOT_DISCLOSED+"')";
        else 
        hivStatusCriteria=" and (ovc.hivStatus='"+status+"') ";
        return hivStatusCriteria;
    }
    /*public String getActiveHivStatusCriteria(String status,String beneficiaryType)
    {
        String hivStatusCriteria="";
        String beneficiaryTypeQuery=" and hsu.clientType='"+beneficiaryType+"'";
        if(status.equalsIgnoreCase(NomisConstant.HIV_UNKNOWN))
        hivStatusCriteria=" and ((hsu.hivStatus='"+status+"' or hsu.hivStatus='"+NomisConstant.HIV_TEST_NOT_INDICATED+"' or hsu.hivStatus='"+NomisConstant.HIV_TEST_NOT_DONE+"' or hsu.hivStatus='"+NomisConstant.HIV_RESULT_NOT_DISCLOSED+"') and hsu.recordStatus='active' and hsu.clientType='"+beneficiaryType+"') ";
        else 
        hivStatusCriteria=" and (hsu.hivStatus like '%"+status+"%' and hsu.recordStatus='active' and hsu.clientType='"+beneficiaryType+"') ";
        return hivStatusCriteria;
    }*/
    public String getActiveHivStatusCriteria(String status,String beneficiaryType)
    {
        String hivStatusCriteria="";
        String beneficiaryTypeQuery=" and hsu.clientType='"+beneficiaryType+"'";
        if(beneficiaryType !=null && beneficiaryType.equalsIgnoreCase(NomisConstant.Caregiver_TYPE))
        {
            beneficiaryTypeQuery=" and (hsu.clientType='"+beneficiaryType+"' or hsu.clientType='"+NomisConstant.HOUSEHOLD_TYPE+"')";
        }
        if(status.equalsIgnoreCase(NomisConstant.HIV_UNKNOWN))
        hivStatusCriteria=" and ((hsu.hivStatus='"+status+"' or hsu.hivStatus='"+NomisConstant.HIV_TEST_NOT_INDICATED+"' or hsu.hivStatus='"+NomisConstant.HIV_TEST_NOT_DONE+"' or hsu.hivStatus='"+NomisConstant.HIV_RESULT_NOT_DISCLOSED+"') and hsu.recordStatus='active' "+beneficiaryTypeQuery+")";
        else 
        hivStatusCriteria=" and (hsu.hivStatus like '%"+status+"%' and hsu.recordStatus='active'"+beneficiaryTypeQuery+")";
        return hivStatusCriteria;
    }
    public String getActiveHivStatusCriteriaForHivRiskAssessment(String status,String beneficiaryType)
    {
        String hivStatusUnknownCriteria="";
        if(status.equalsIgnoreCase(NomisConstant.HIV_UNKNOWN))
        hivStatusUnknownCriteria=" and ((hsu.hivStatus ='"+status+"' or hsu.hivStatus='"+NomisConstant.HIV_TEST_NOT_INDICATED+"') and hsu.recordStatus='active' and hsu.clientType='"+beneficiaryType+"') ";
        return hivStatusUnknownCriteria;
    }
    /*public String getActiveHivStatusCriteria(String status)
    {
        String hivStatusUnknownCriteria="";
        if(status.equalsIgnoreCase("unknown"))
        hivStatusUnknownCriteria="((hsu.hivStatus='unknown' or hsu.hivStatus='unk_tni' or hsu.hivStatus='unk_tnd' or hsu.hivStatus='unk_rnd') and hsu.recordStatus='active' and hsu.clientType='ovc') ";
        else if(status.equalsIgnoreCase("positive"))
        hivStatusUnknownCriteria="(hsu.hivStatus='positive' and hsu.recordStatus='active' and hsu.clientType='ovc') ";
        else if(status.equalsIgnoreCase("negative"))
        hivStatusUnknownCriteria="(hsu.hivStatus='negative' and hsu.recordStatus='active' and hsu.clientType='ovc') ";
        return hivStatusUnknownCriteria;
    }*/
    public String getBaselineHivStatusCriteria(String status)
    {
        String hivStatusUnknownCriteria="";
        if(status.equalsIgnoreCase(NomisConstant.HIV_UNKNOWN))
        hivStatusUnknownCriteria="((hsu.hivStatus='"+status+"' or hsu.hivStatus='"+NomisConstant.HIV_TEST_NOT_INDICATED+"' or hsu.hivStatus='"+NomisConstant.HIV_TEST_NOT_DONE+"' or hsu.hivStatus='"+NomisConstant.HIV_RESULT_NOT_DISCLOSED+"') and hsu.pointOfUpdate='enrollment' and hsu.clientType='ovc') ";
        else //if(status.equalsIgnoreCase("positive"))
        hivStatusUnknownCriteria="(hsu.hivStatus like '%"+status+"%' and hsu.pointOfUpdate='enrollment' and hsu.clientType='ovc') ";
        //else if(status.equalsIgnoreCase("negative"))
        //hivStatusUnknownCriteria="(hsu.hivStatus='negative' and hsu.pointOfUpdate='enrollment' and hsu.clientType='ovc') ";
        return hivStatusUnknownCriteria;
    }
    public String getHivPositiveEnrolledInCareStatusCriteria(String enrolledInCareValue)
    {
        return " hsu.clientEnrolledInCare='"+enrolledInCareValue+"'";
    }
    public String getHivPositiveEnrolledOnARTStatusCriteria(String enrolledOnARTValue)
    {
        return " and ( hsu.enrolledOnART='"+enrolledOnARTValue+"' or hsu.clientEnrolledInCare ='"+enrolledOnARTValue+"')";
    }
    public String getGenderCriteria(String gender)
    {
        String genderCriteria="";
        if(gender==null)
        return genderCriteria;
        gender=gender.toUpperCase();
        if(gender.equalsIgnoreCase("MALE"))
        genderCriteria=" UPPER(ovc.gender)='"+gender+"'";
        else if(gender.equalsIgnoreCase("FEMALE"))
        genderCriteria=" UPPER(ovc.gender) !='MALE' ";

        return genderCriteria;
    }
    public String getCaregiverGenderCriteria(String gender)
    {
        String genderCriteria="";
        if(gender !=null)
        {
            //gender=gender.toUpperCase();
            if(gender.equalsIgnoreCase("male"))
             genderCriteria=" UPPER(cgiver.caregiverGender)='MALE' ";//caregiverGender
            else if(gender.equalsIgnoreCase("female"))
             genderCriteria=" UPPER(cgiver.caregiverGender) !='MALE' ";
        }
        return genderCriteria;
    }
    public void updateHivStatusOfOvcNotAtRisk()
    {
        try
        {
            AppUtility appUtil=new AppUtility();
            List list=getHivRiskAssessmentChecklistDaoInstance().getListOfOvcNotAtRiskWithUnknownHivStatus();
            if(list !=null && !list.isEmpty())
            {
                HivStatusUpdate hsu=null;
                String ovcId=null;
                for(int i=0; i<list.size(); i++)
                {
                    ovcId=(String)list.get(i);
                    if(ovcId !=null)
                    hsu=getHivStatusUpdateDaoInstance().getActiveHivStatusUpdatesByClientId(ovcId);
                    if(hsu !=null)
                    {
                        if(hsu.getHivStatus() !=null && (hsu.getHivStatus().equalsIgnoreCase(NomisConstant.HIV_UNKNOWN) || hsu.getHivStatus().equalsIgnoreCase(NomisConstant.HIV_TEST_NOT_INDICATED)))
                        {
                           hsu.setHivStatus(NomisConstant.HIV_TEST_NOT_INDICATED);
                           hsu.setPointOfUpdate(NomisConstant.HIVRISK_POINTOFUPDATE);
                           hsu.setDateModified(appUtil.getCurrentDate());
                           getHivStatusUpdateDaoInstance().saveHivStatusUpdate(hsu); 
                           System.err.println("Ovc Id "+ovcId+" at "+i+" updated in DaoUtil.updateHivStatusOfOvcNotAtRisk()");
                        }
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public String getPartnerCode(String user)
    {
        List list=new ArrayList();
        String partnerCode=null;

        try
        {
            CboSetupDao dao = new CboSetupDaoImpl();
            CboSetup setup=dao.getCboSetup(user);
            if(setup !=null)
            partnerCode=setup.getPartner();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return partnerCode;
    }
    public String getPartnerName(String partnerCode)
    {
        
        String partnerName=partnerCode;
        try
        {
            Partners partner=PartnerManager.getPartner(partnerCode);
            /*List list=pdao.getPartner(partnerCode);
            if(list !=null && list.size()>0)*/
            partnerName=partner.getPartnerName();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return partnerName;
    }
    public CboSetup getSetupInfo()
    {
        CboSetup setup=null;
        try
        {
            List list=new ArrayList();
            CboSetupDao dao = new CboSetupDaoImpl();
            list = dao.getCboSetupInfo();
            if(!list.isEmpty())
            {
                setup=(CboSetup)list.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return setup;
    }
    public String getStateName(String state_code)
    {
        if(state_code==null)
        return null;
        else if(state_code.equalsIgnoreCase("All"))
        return "All";
        StatesDao dao=new StatesDaoImpl();
        List list=new ArrayList();
        String stateName=null;
        try
        {
         list=dao.getState(state_code);
         for(Object s: list)
         {
             States state=(States)s;
             stateName=state.getName();
         }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return stateName;
    }
    public String getLgaName(String lga_code)
    {
        if(lga_code==null)
        return null;
        else if(lga_code.equalsIgnoreCase("All"))
        return "All";
        //LgaDao dao=new LgaDaoImpl();
        //List list=new ArrayList();
        String lgaName=null;
        try
        {
            Lgas lga=getLgaByLgaCode(lga_code);
            lgaName=lga.getLga_name();
         /*list=dao.getLgaByLgaCode(lga_code);
         for(Object s: list)
         {
             Lgas lga=(Lgas)s;
             lgaName=lga.getLga_name();
         }*/
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return lgaName;
    }
    public String getCboName(String cbo_code)
    {
        CboDao dao=new CboDaoImpl();
        List list=new ArrayList();
        String cboName=null;
        try
        {
         list=dao.getCbos(cbo_code);
         for(Object s: list)
         {
             Cbos cbo=(Cbos)s;
             cboName=cbo.getCbo_name();
         }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return cboName;
    }
    public String getOrganizationName(String orgCode)
    {
        String orgName="All";
        if(orgCode !=null && !orgCode.equalsIgnoreCase("All"))
        {
            OrganizationRecordsDao orDao=new OrganizationRecordsDaoImpl();
            try
            {
                orgName=orDao.getOrganizationName(orgCode);
                if(orgName==null || orgName.equals("") || orgName.equals(" ") || orgName.equals("  "))
                orgName=getOrganizationUnitGroupName(orgCode);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return orgName;
    }
    public String getOrganizationUnitGroupName(String orgCode)
    {
        String orgName="All";
        if(!orgCode.equalsIgnoreCase("All"))
        {
            OrganizationUnitGroupDao ougDao=new OrganizationUnitGroupDaoImpl();
            OrganizationUnitGroup oug=null;
            try
            {
                oug=ougDao.getOrganizationUnitGroup(orgCode);
                if(oug !=null)
                orgName=oug.getOrgUnitGroupName();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return orgName;
    }
    public OrganizationRecords getOrganizationRecords(String cbo_code)
    {
        OrganizationRecordsDao orDao=new OrganizationRecordsDaoImpl();
        OrganizationRecords orgRecords=null;
        try
        {
         orgRecords=(OrganizationRecords)orDao.getOrganizationRecord(cbo_code);
         if(orgRecords==null)
         {
            orgRecords=new OrganizationRecords();
            orgRecords.setOrgCode(cbo_code);
            orgRecords.setOrgName(cbo_code);
         }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return orgRecords;
    }
    public String getCboCode(String cbo_name)
    {
        CboDao dao=new CboDaoImpl();
        List list=new ArrayList();
        String cboCode=null;
        try
        {
         list=dao.getCboCode(cbo_name);
         for(Object s: list)
         {
             Cbos cbo=(Cbos)s;
             cboCode=cbo.getCbo_code();
         }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return cboCode;
    }
    public String getWardName(String ward_code)
    {
        WardDao dao=new WardDaoImpl();
        List list=new ArrayList();
        String wardName=null;
        if(ward_code !=null && ward_code.equals("All"))
        wardName="All";
        try
        {
         list=dao.getWardByWardCode(ward_code);
         for(Object s: list)
         {
             Wards ward=(Wards)s;
             wardName=ward.getWard_name();
         }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return wardName;
    }
    public Wards getWard(String ward_code)
    {
        WardDao dao=new WardDaoImpl();
        String cboCode=ward_code;
        if(ward_code !=null && ward_code.indexOf("/") !=-1)
        cboCode=ward_code.substring(0,ward_code.lastIndexOf("/"));
         Wards ward=null;
        try
        {
         ward=(Wards)dao.getWards(ward_code);
         if(ward==null)
         {
            ward=new Wards();
            ward.setWard_code(ward_code);
            ward.setWard_name(ward_code);
            ward.setCbo_code(cboCode);
         }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return ward;
    }
    public String tokenize(String s)
    {
        String token=" ";
        StringTokenizer st = new StringTokenizer(s);
        while (st.hasMoreTokens())
        {
            token+=st.nextToken();
        }
        return token;
    }
    public int getMonthDifference(int baselineMth,int baselineYear)
    {
        int mthDiff=0;
        try
        {
        int currentMonth=0;
        int currentYear=0;
        OptionsManager opm=getOptionsManagerDaoInstance().getOptionsManager("curagedate");
            if(opm !=null)
            {
                if(opm.getValue() !=null && opm.getValue().indexOf("-") !=-1)
                {
                    String[] curdateArray=opm.getValue().split("-");
                    currentMonth=Integer.parseInt(curdateArray[1]);
                    currentYear=Integer.parseInt(curdateArray[0]);
                }
            }
            mthDiff=DateManager.getDateDifference(baselineMth,baselineYear,currentMonth,currentYear);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return mthDiff;
    }
    public int getCurrentAge(String dateAtBaseline, int ageAtBaseline,String ageUnit)
    {
        int age=ageAtBaseline;
        int currentAge=age;
        if(dateAtBaseline ==null || dateAtBaseline.indexOf("-") == -1)
        return ageAtBaseline;
        String[] dateArray=dateAtBaseline.split("-");
        int yearOfEnrollment=Integer.parseInt(dateArray[0]);
        int monthOfEnrollment=Integer.parseInt(dateArray[1]);
        
        try
        {
            int mthDiff=getMonthDifference(monthOfEnrollment, yearOfEnrollment);
            if(ageUnit !=null && ageUnit.equalsIgnoreCase("Month"))
            {
                age=age+mthDiff;
                double year=Math.floor(age/12);
                if(year >0)
                currentAge=(int)year;
            }
            else
            {
                currentAge=((int)Math.floor(mthDiff/12))+age;
                if(currentAge<ageAtBaseline)
                currentAge=ageAtBaseline;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return currentAge;
    }
    public int getCurrentAge(Ovc ovc)
    {
        int currentAge=getCurrentAge(ovc.getDateEnrollment(), ovc.getAge(),ovc.getAgeUnit());
        return currentAge;
    }
    public Ovc getOvcWithCurrentAge(Ovc ovc)
    {
        String ageUnit=ovc.getAgeUnit();
        int currentAge=getCurrentAge(ovc.getDateEnrollment(), ovc.getAge(),ageUnit);
        ovc.setCurrentAge(currentAge);
        ovc.setCurrentAgeUnit(getCurrentAgeUnit(ovc));
        //System.err.println("ovc.getCurrentAgeUnit() is "+ovc.getCurrentAgeUnit());
        return ovc;
    }
    public String getCurrentAgeUnit(Ovc ovc)
    {
        String currentAgeUnit="";
        //System.err.println("ovc.getAgeUnit() is "+ovc.getAgeUnit());
        if(ovc !=null)
        {
            if(ovc.getAgeUnit()==null || (!ovc.getAgeUnit().equalsIgnoreCase("Month") && !ovc.getAgeUnit().equalsIgnoreCase("Year")))
            ovc.setAgeUnit("Year");
            currentAgeUnit=ovc.getAgeUnit();
            String[] dateArray=ovc.getDateEnrollment().split("-");
            int yearOfEnrollment=Integer.parseInt(dateArray[0]);
            int monthOfEnrollment=Integer.parseInt(dateArray[1]);
            int ageAtBaseline=ovc.getAge();
            int age=ageAtBaseline;
            int mthDiff=getMonthDifference(monthOfEnrollment, yearOfEnrollment);
            if(ovc.getAgeUnit() !=null && ovc.getAgeUnit().equalsIgnoreCase("Month"))
            {
                age=age+mthDiff;
                if(age>11)
                currentAgeUnit="Year";
                
            }
        }
        return currentAgeUnit;
    }
    
    public List execSqlQuery(String sql)
    {
        List list=new ArrayList();
        session=HibernateUtil.getSession();
        tx=session.beginTransaction();
        list=session.createSQLQuery(sql).list();
        tx.commit();
        session.close();
        //System.err.println("About to print list.....");
        //System.err.println(list);
        return list;
        
    }
    public String getServiceDate(String mysqlDate)
    {
        String[] dateParts=mysqlDate.split("-");
        String month=getMonthAsString(Integer.parseInt(dateParts[1]));
        String displayDate=month+" "+dateParts[0];
        return displayDate;
    }
    public String getMySqlDate(String day,String month,String year)
    {
        String mysqlDate=year+"-"+month+"-"+day;
        return mysqlDate;
    }
    public String getDisplayDate(String mysqlDate)
    {
        String[] dateParts=mysqlDate.split("-");
        String displayDate=dateParts[2]+"/"+dateParts[1]+"/"+dateParts[0];
        return displayDate;
    }
    public String getFullMonthAsString(int mth)
    {
        String month=null;
        if(mth==1 || mth==01)
        month="January";
        else if(mth==2 || mth==02)
        month="February";
        else if(mth==3 || mth==03)
        month="March";
        else if(mth==4 || mth==04)
        month="April";
        else if(mth==5 || mth==05)
        month="May";
        else if(mth==6 || mth==06)
        month="June";
        else if(mth==7 || mth==07)
        month="July";
        else if(mth==8)
        month="August";
        else if(mth==9)
        month="September";
        else if(mth==10)
        month="October";
        else if(mth==11)
        month="November";
        else if(mth==12)
        month="December";
        return month;
    }
    public String getMonthAsString(int mth)
    {
        String month=null;
        if(mth==1 || mth==01)
        month="Jan";
        else if(mth==2 || mth==02)
        month="Feb";
        else if(mth==3 || mth==03)
        month="Mar";
        else if(mth==4 || mth==04)
        month="Apr";
        else if(mth==5 || mth==05)
        month="May";
        else if(mth==6 || mth==06)
        month="Jun";
        else if(mth==7 || mth==07)
        month="Jul";
        else if(mth==8)
        month="Aug";
        else if(mth==9)
        month="Sep";
        else if(mth==10)
        month="Oct";
        else if(mth==11)
        month="Nov";
        else if(mth==12)
        month="Dec";
        return month;
    }
    public String getDatePeriodForServices(List dateList)
    {
        int startMth=(Integer)dateList.get(0);
        int endMth=(Integer)dateList.get(1);
        int startYr=(Integer)dateList.get(2);
        int endYr=(Integer)dateList.get(3);
        String[] dateParams={startMth+"",startYr+"",endMth+"",endYr+""};
        String startDate=getStartDate(dateParams);
        String endDate=getEndDate(dateParams);
        String queryPart=" os.servicedate between '"+startDate+"' and '"+endDate+"'";
        
        if(endMth < startMth && (endYr == startYr || endYr < startYr))
        queryPart=" os.ovcId !=' ' ";
        else if((endMth == startMth) && ( endYr < startYr))
        queryPart=" os.ovcId !=' ' ";
        else if((endMth > startMth) && ( endYr < startYr))
        queryPart=" os.ovcId !=' ' ";
        return queryPart;
    }
    public List getOrganizationalUnits(String requiredAction,String stateCode,String lgaCode,String cboCode,String wardCode)throws Exception
    {
        List list=new ArrayList();
        if(requiredAction==null || requiredAction.equals("All"))
        {
            OrganizationRecordsDao orgDao = new OrganizationRecordsDaoImpl();
            list = orgDao.getOrganizationList();
        }
        else if(requiredAction.equals("lga"))
        {
            list=getLgaListFromHhe(stateCode);
        }
        else if(requiredAction.equals("cbo"))
        {
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            list = hhedao.getDistinctOrgCodesPerLga(stateCode, lgaCode);
        }
        else if(requiredAction.equals("cboPerState"))
        {
            OrganizationRecordsDao orgDao = new OrganizationRecordsDaoImpl();
            list = orgDao.getStateOrganizationList(stateCode);
        }
        else if(requiredAction.equals("ward"))
        {
            HouseholdEnrollmentDao hheDao = new HouseholdEnrollmentDaoImpl();
            list = hheDao.getDistinctCommunityCodes(stateCode, lgaCode,cboCode);
        }
        return list;
    }
    public List getRealOrganizationalUnitsFromData(String requiredAction,String stateCode,String lgaCode,String cboCode,String wardCode)throws Exception
    {
        List list=new ArrayList();
        if(requiredAction==null)
        return list;
        else if(requiredAction.equalsIgnoreCase("state"))
        {
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            List stateCodeList = hhedao.getDistinctStateCodes();
            for(Object obj:stateCodeList)
            {
                States state=this.getStateByStateCode(obj.toString());
                if(state !=null)
                list.add(state);
            }
        }
        else if(requiredAction.equals("lga"))
        {
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            List lgaCodeList = hhedao.getHVILgaList(stateCode);
            for(Object obj:lgaCodeList)
            {
                Lgas lga=getLgaByLgaCode(obj.toString());
                if(lga !=null)
                list.add(lga);
            }
        }
        else if(requiredAction.equals("cbo"))
        {
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            List orgCodeList = hhedao.getDistinctOrgCodesPerLga(stateCode, lgaCode);
            OrganizationRecords orgRecords=null;
                        
            for(Object org:orgCodeList)
            {
                orgRecords=getOrganizationRecords(org.toString());
                list.add(orgRecords);
            }
        }
        else if(requiredAction.equals("cboPerState"))
        {
            OrganizationRecordsDao orgDao = new OrganizationRecordsDaoImpl();
            List orgCodeList = orgDao.getStateOrganizationList(stateCode);
            OrganizationRecords orgRecords=null;        
            for(Object org:orgCodeList)
            {
                orgRecords=getOrganizationRecords(org.toString());
                list.add(orgRecords);
            }
        }
        else if(requiredAction.equals("ward"))
        {
            HouseholdEnrollmentDao hheDao = new HouseholdEnrollmentDaoImpl();
            List wardCodeList = hheDao.getDistinctCommunityCodes(stateCode, lgaCode,cboCode);
            Wards ward=null;
            for(Object org:wardCodeList)
            {
                if(org==null || org.toString().indexOf("/")==-1)
                continue;
                ward=getWard(org.toString());
                list.add(ward);
            }
        }
        return list;
    }
    public List getDateLabels(String[] dateParams)
    {
        List dateList=new ArrayList();
        Calendar cal=Calendar.getInstance();
        for(int i=0; i<dateParams.length; i++)
        {
           String[] dateArray=dateParams[i].split("-");
           cal.set(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1])-1, Integer.parseInt(dateArray[2]));
           dateList.add(cal.getDisplayName(cal.MONTH, cal.SHORT, Locale.ENGLISH)+" "+dateArray[0]);
        }
        return dateList;
    }
    public String getStartDate(int startMth,int startYear)
    {
        String startDate=startYear+"-"+startMth+"-"+"01";
        return startDate;
    }
    public String getEndDate(int endMth,int endYear)
    {
        Calendar cal=Calendar.getInstance();
        cal.set(endYear, endMth-1,1);
        int lastDayOfMth=cal.getActualMaximum(Calendar.DATE);
        String endDate=endYear+"-"+endMth+"-"+lastDayOfMth;
        return endDate;
    }
    public String getStartDate(String[] params)
    {
        String startDate=null;
        if(params !=null && params.length >1)
        {
            if(!validateDateParamenters(params))
            return null;
            startDate=params[1]+"-"+params[0]+"-"+"01";
        }
        return startDate;
    }
    public String getEndDate(String[] params)
    {
        String endDate=null;
        if(params !=null && params.length >3)
        {
            if(!validateDateParamenters(params))
            return null;
            Calendar cal=Calendar.getInstance();
            cal.set(Integer.parseInt(params[3]), Integer.parseInt(params[2])-1,1);
            int lastDayOfMth=cal.getActualMaximum(Calendar.DATE);
            endDate=params[3]+"-"+params[2]+"-"+lastDayOfMth;
        }
        return endDate;
    }
    public boolean validateDateParamenters(String[] params)
    {
        boolean gooddateParams=true;
        for(int i=0; i<4; i++)
        {
            if(params[i]==null || params[i].equalsIgnoreCase("All"))
            gooddateParams=false;
        }
        return gooddateParams;
    }
    public String getCareAndSupportSkippedARVQuery(boolean skippedARV)
    {
        String skippedARVQuery=" ";
        if(skippedARV)
        {
            skippedARVQuery=" and csc.skippedARV ='Yes'";
        }
        else
        {
            skippedARVQuery=" and csc.skippedARV ='No'";
        }
          return skippedARVQuery;
    }
    public String getSchoolAttendanceTrackerDateOfAssessmentQuery(String startDate, String endDate)
    {
        String dateQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            dateQuery=" and sat.dateOfAssessment between '"+startDate+"' and '"+endDate+"'";
        }
          return dateQuery;
    }
    public String getSchoolAttendanceDateOfAssessmentQuery(String startDate, String endDate)
    {
        String dateQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            dateQuery=" and sa.dateOfAssessment between '"+startDate+"' and '"+endDate+"'";
        }
          return dateQuery;
    }
    public String getCareAndSupportLastModifiedDateQuery(String startDate, String endDate)
    {
        String dateQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            dateQuery=" and csc.lastModifiedDate between '"+startDate+"' and '"+endDate+"'";
        }
          return dateQuery;
    }
    public String getCareAndSupportDateOfAssessmentQuery(String startDate, String endDate)
    {
        String dateQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            dateQuery=" and csc.dateOfAssessment between '"+startDate+"' and '"+endDate+"'";
        }
          return dateQuery;
    }
    public String getCaregiverExpenditureAndSchoolAttendanceDateOfAssessmentQuery(String startDate, String endDate)
    {
        String dateQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            dateQuery=" and ceasa.dateOfAssessment between '"+startDate+"' and '"+endDate+"'";
        }
          return dateQuery;
    }
    public String getCareAndSupportDateOfHivTestQuery(String startDate, String endDate)
    {
        String dateQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All") && startDate.indexOf("-") !=-1) && (endDate !=null && !endDate.equalsIgnoreCase("All") && endDate.indexOf("-") !=-1))
        {
            dateQuery=" and csc.dateOfHivTest between '"+startDate+"' and '"+endDate+"'";
        }
          return dateQuery;
    }
    public String getCareAndSupportDateEnrolledOnARTQuery(String startDate, String endDate)
    {
        String dateQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            dateQuery=" and csc.dateEnrolledOnART between '"+startDate+"' and '"+endDate+"'";
        }
          return dateQuery;
    }
    public String getNutritionalAssessmentDateQuery(String startDate, String endDate)
    {
        String ovcDateQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            ovcDateQuery=" and na.dateOfAssessment between '"+startDate+"' and '"+endDate+"'";
        }
          return ovcDateQuery;
    }
    public String getEnrollmentEndDateQuery(String endDate)
    {
        String ovcDateQuery=" ";
        if(endDate !=null && endDate.indexOf("-") !=-1)
        {
            ovcDateQuery=" and ovc.dateEnrollment <='"+endDate+"'";
        }
        return ovcDateQuery;
    }
    public String getEnrollmentEndDateQuery(String[] dateParams)
    {
        String endDate=null;
        String ovcDateQuery=" ";
        if(dateParams !=null && dateParams.length==4)
        {
            if((dateParams[2] !=null && !dateParams[2].equalsIgnoreCase("All")) && (dateParams[3] !=null && !dateParams[3].equalsIgnoreCase("All")))
            {
                endDate=getEndDate(dateParams);
                ovcDateQuery=" ovc.dateEnrollment <='"+endDate+"'";
            }
        }

        return ovcDateQuery;
    }
    public String getEnrollmentDateQuery(String startDate, String endDate)
    {
        String ovcDateQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            ovcDateQuery=" and ovc.dateEnrollment between '"+startDate+"' and '"+endDate+"'";
        }
          return ovcDateQuery;
    }
    public String getCsiDateQuery(String startDate, String endDate)
    {
        String csiDateQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            csiDateQuery=" and csi.csiDate between '"+startDate+"' and '"+endDate+"'";
        }
          return csiDateQuery;
    }
    public String getFollowupDateQuery(String startDate, String endDate)
    {
        String ovcDateQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            ovcDateQuery=" and fu.dateOfSurvey between '"+startDate+"' and '"+endDate+"'";
        }
          return ovcDateQuery;
    }
    public String getDateQueryForWithdrawalWithinReportPeriod(String startDate, String endDate)
    {
        String ovcDateQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            ovcDateQuery=" and withdrawal.dateOfWithdrawal between '"+startDate+"' and '"+endDate+"'";
        }
          return ovcDateQuery;
    }
    public String getWithdrawalServicePeriodCriteria(String startDate)
    {
        String periodQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")))
        periodQuery= " and withdrawal.dateOfWithdrawal >= '"+startDate+"'";
        return periodQuery;
    }
    /*public String getWithdrawalDateQueryByStartDate(String startDate)
    {
        String ovcDateQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")))
        {
            ovcDateQuery=" and withdrawal.dateOfWithdrawal >= '"+startDate+"'";
        }
          return ovcDateQuery;
    }*/
    public String getOvcServiceDateQuery(String startDate, String endDate)
    {
        String ovcDateQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            ovcDateQuery=" and service.servicedate between '"+startDate+"' and '"+endDate+"'";
        }
          return ovcDateQuery;
    }
    public String getCareplanAchievementExportDateQuery(String startDate, String endDate)
    {
        String ovcDateQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            ovcDateQuery=" and cpa.lastModifiedDate between '"+startDate+"' and '"+endDate+"'";
        }
          return ovcDateQuery;
    }
    public String getCareplanAchievementReportDateQuery(String startDate, String endDate)
    {
        String ovcDateQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            ovcDateQuery=" and cpa.dateOfAssessment between '"+startDate+"' and '"+endDate+"'";
        }
          return ovcDateQuery;
    }
    public String getHouseholdServiceDateQuery(String startDate, String endDate)
    {
        String ovcDateQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            ovcDateQuery=" and hhs.serviceDate between '"+startDate+"' and '"+endDate+"'";
        }
          return ovcDateQuery;
    }
    public String getHIVStatusDateQuery(String startDate, String endDate)
    {
        String ovcDateQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            ovcDateQuery=" and hsu.dateOfCurrentStatus between '"+startDate+"' and '"+endDate+"'";
        }
          return ovcDateQuery;
    }
    public String getLastHIVStatusDateQuery(String dateOfStatus,String operand)
    {
        String dateQuery=" ";
        if((dateOfStatus !=null && !dateOfStatus.equalsIgnoreCase("All")))
        {
            dateQuery=" and hsu.dateOfCurrentStatus <='"+dateOfStatus+"'";
            if(operand !=null)
            {
                if(operand.equalsIgnoreCase("<"))
                dateQuery=" and hsu.dateOfCurrentStatus <'"+dateOfStatus+"'";
                else if(operand.equalsIgnoreCase("="))
                dateQuery=" and hsu.dateOfCurrentStatus ='"+dateOfStatus+"'";
                else if(operand.equalsIgnoreCase(">"))
                dateQuery=" and hsu.dateOfCurrentStatus >'"+dateOfStatus+"'";
                else if(operand.equalsIgnoreCase(">="))
                dateQuery=" and hsu.dateOfCurrentStatus >='"+dateOfStatus+"'";
            }
            
        }
          return dateQuery;
    }
    public String getQueryPeriod(String[] params,String queryPart)
    {
        String startDate=getStartDate(params);
        String endDate=getEndDate(params);
        String dateQuery=queryPart +" between '"+startDate+"' and '"+endDate+"'";
        return dateQuery;
    }
    public int setCurrentAgeUnit()
    {
        int count=0;
        if(!isCurrentAgeUnitCorrectlySet())
        {
            try
            {
                List communityCodeList=getOvcDaoInstance().getDistinctCommunityCodesWithIncorrectCurrentAgeUnit();
                if(communityCodeList !=null)
                {
                    String communityCode=null;
                    for(int i=0; i<communityCodeList.size(); i++)
                    {
                        if(communityCodeList.get(i) !=null)
                        communityCode=communityCodeList.get(i).toString();
                        else
                        communityCode=(String)communityCodeList.get(i);
                        count+=getOvcDaoInstance().setCurrentAgeUnitByCommunity(communityCode);
                    }
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        System.err.println(count+" currentAgeUnits correctly set");
        return count;
    }
    public boolean isCurrentAgeUnitCorrectlySet()
    {
        boolean currentAgeUnitSet=true;
        try
        {
            int numberOfRecordsWithIncorrectCurrentAgeUnit=getOvcDaoInstance().getNumberOfRecordsWithIncorrectCurrentAgeUnit();
            if(numberOfRecordsWithIncorrectCurrentAgeUnit >0)
            currentAgeUnitSet=false;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return currentAgeUnitSet;
    }
    public String getAgeCriteria(String firstAge,String secondAge,String ageUnit,String logic)
    {
        String ageQuery=" ";
        try
        {
            if(firstAge ==null || firstAge.equalsIgnoreCase("All") || secondAge ==null || secondAge.equalsIgnoreCase("All"))
            return ageQuery;
            int startAge=Integer.parseInt(firstAge);
            int endAge=Integer.parseInt(secondAge);
            if(logic.equalsIgnoreCase("lessthan"))
            {
                if(startAge==0)
                ageQuery=" ((UPPER(ovc.ageUnit) like '%MONTH%' and ovc.currentAge=0) or (ovc.age=0 and ovc.currentAge=0))";
                //ageQuery=" ((UPPER(ovc.ageUnit) like '%MONTH%' and UPPER(ovc.currentAgeUnit != 'YEAR')) or ((UPPER(ovc.ageUnit) like '%MONTH%' and ovc.currentAge=0 and UPPER(ovc.currentAgeUnit) like '%YEAR%')) or (( (UPPER(ovc.ageUnit) like '%YEAR%' and ovc.age=0) and ovc.currentAge=0 and UPPER(ovc.currentAgeUnit) like '%YEAR%')))";
                //ageQuery=" (UPPER(ovc.ageUnit) like '%MONTH%' or (ovc.currentAge=0 and UPPER(ovc.ageUnit) like '%YEAR%'))";
                else if(startAge > 0)
                ageQuery=" ovc.currentAge < "+startAge+" and (UPPER(ovc.ageUnit) like '%YEAR%' or UPPER(ovc.ageUnit) like '%MONTH%')";
            }
            if(logic.equalsIgnoreCase("equalsto"))
            {
                if(startAge==0)
                ageQuery=" (UPPER(ovc.ageUnit) like '%MONTH%' or (ovc.currentAge=0 and UPPER(ovc.ageUnit) like '%YEAR%'))";
                else if(startAge > 0)
                ageQuery=" ovc.currentAge = "+startAge;
            }
            if(logic.equalsIgnoreCase("between"))
            {
                if(startAge==0 && startAge < endAge)
                 ageQuery=" (ovc.currentAge between "+startAge+" and "+endAge+" or UPPER(ovc.ageUnit) like '%MONTH%')";
                else if(startAge>0 && startAge < endAge)
                ageQuery=" (ovc.currentAge between "+startAge+" and "+endAge+" and UPPER(ovc.ageUnit) like '%YEAR%')";
            }
            if(logic.equalsIgnoreCase("greaterthan"))
            {
                if(startAge > 0)
                ageQuery=" ovc.currentAge > "+startAge+" and UPPER(ovc.ageUnit) like '%YEAR%'";
            }
        }
        catch(NumberFormatException nex)
        {
            ageQuery=" ";
            nex.printStackTrace();
        }
        catch(Exception ex)
        {
           ageQuery=" ";
           ex.printStackTrace();
        }
        return ageQuery;
    }
    public String getAgeCriteria(String firstAge,String secondAge)
    {
        String ageQuery=" ";
        try
        {
            if(firstAge ==null || firstAge.equalsIgnoreCase("All") || secondAge ==null || secondAge.equalsIgnoreCase("All"))
            return ageQuery;
            int startAge=Integer.parseInt(firstAge);
            int endAge=18;
            if(secondAge.indexOf("+")!=-1)
            {
               return " and (ovc.currentAge >= "+startAge+")";
            }
            endAge=Integer.parseInt(secondAge);
            if((startAge==endAge) && endAge==0)
            //ageQuery=" and ((UPPER(ovc.currentAgeUnit) like '%MONTH%') or (UPPER(ovc.ageUnit) like '%MONTH%' and (ovc.currentAgeUnit is null or (UPPER(ovc.currentAgeUnit) !='MONTH' and UPPER(ovc.currentAgeUnit) !='YEAR')))";
            ageQuery=" and ( (ovc.age=0 and ovc.currentAge=0) or (UPPER(ovc.currentAgeUnit) like '%MONTH%' and ovc.currentAge <12) or (UPPER(ovc.ageUnit) like '%MONTH%' and ovc.currentAgeUnit is null and ovc.currentAge <12))";
            else if((startAge==endAge) && endAge > 0)
            ageQuery=" and (ovc.currentAge= "+startAge+" and (UPPER(ovc.currentAgeUnit) like '%YEAR%' or UPPER(ovc.ageUnit) like '%YEAR%'))";
            else if(startAge==0 && startAge < endAge)
                 ageQuery=" and ((ovc.currentAge >="+startAge+" and ovc.currentAge <="+endAge+") or UPPER(ovc.currentAgeUnit) like '%MONTH%')";
            else if(startAge>0 && startAge < endAge)
                ageQuery=" and (ovc.currentAge >= "+startAge+" and ovc.currentAge <="+endAge+" and (UPPER(ovc.currentAgeUnit) like '%YEAR%' or UPPER(ovc.ageUnit) like '%YEAR%'))";
            else if(startAge > endAge)
            ageQuery=" ";
            else
            ageQuery=" and ovc.currentAge >= "+startAge+" and ovc.currentAge <= "+endAge;
        }
        catch(NumberFormatException nex)
        {
            ageQuery=" ";
            nex.printStackTrace();
        }
        catch(Exception ex)
        {
           ageQuery=" ";
           ex.printStackTrace();
        }
        return ageQuery;
    }
    /*public String getAgeCriteria(String firstAge,String secondAge)
    {
        String ageQuery=" ";
        if(firstAge ==null || firstAge.equalsIgnoreCase("All") || secondAge ==null || secondAge.equalsIgnoreCase("All"))
        return ageQuery;
        int startAge=Integer.parseInt(firstAge);
        int endAge=18;
        if(secondAge.indexOf("+")!=-1)
        {
           return " and (ovc.currentAge >= "+startAge+")";
        }
        endAge=Integer.parseInt(secondAge);
        if((startAge==endAge) && endAge==0)
        ageQuery=" and ((UPPER(ovc.currentAgeUnit) like '%MONTH%' and ovc.currentAge <12) or (UPPER(ovc.ageUnit) like '%MONTH%' and ovc.currentAgeUnit is null and ovc.currentAge <12))";
        else if((startAge==endAge) && endAge > 0)
        ageQuery=" and (ovc.currentAge= "+startAge+" and UPPER(ovc.ageUnit) like '%YEAR%')";
        else if(startAge==0 && startAge < endAge)
             ageQuery=" and (ovc.currentAge between "+startAge+" and "+endAge+" or UPPER(ovc.ageUnit) like '%MONTH%')";
        else if(startAge>0 && startAge < endAge)
            ageQuery=" and (ovc.currentAge between "+startAge+" and "+endAge+" and UPPER(ovc.ageUnit) like '%YEAR%')";
        else if(startAge > endAge)
        ageQuery=" ";
        else
        ageQuery=" and ovc.currentAge between "+startAge+" and "+endAge;
        return ageQuery;
    }*/
    /*public String getAgeCriteria(String firstAge,String secondAge)
    {
        String ageQuery=" ";
        if(firstAge ==null || firstAge.equalsIgnoreCase("All") || secondAge ==null || secondAge.equalsIgnoreCase("All"))
        return ageQuery;
        int startAge=Integer.parseInt(firstAge);
        int endAge=18;
        if(secondAge.indexOf("+")!=-1)
        {
           return " and (ovc.currentAge >= "+startAge+")";
        }
        endAge=Integer.parseInt(secondAge);
        if((startAge==endAge) && endAge==0)
        ageQuery=" and (UPPER(ovc.ageUnit) like '%MONTH%' or (ovc.currentAge=0 and UPPER(ovc.ageUnit) like '%YEAR%'))";
        else if((startAge==endAge) && endAge > 0)
        ageQuery=" and (ovc.currentAge= "+startAge+" and UPPER(ovc.ageUnit) like '%YEAR%')";
        else if(startAge==0 && startAge < endAge)
             ageQuery=" and (ovc.currentAge between "+startAge+" and "+endAge+" or UPPER(ovc.ageUnit) like '%MONTH%')";
        else if(startAge>0 && startAge < endAge)
            ageQuery=" and (ovc.currentAge between "+startAge+" and "+endAge+" and UPPER(ovc.ageUnit) like '%YEAR%')";
        else if(startAge > endAge)
        ageQuery=" ";
        else
        ageQuery=" and ovc.currentAge between "+startAge+" and "+endAge;
        return ageQuery;
    }*/
    
    public String getAgeForTitle(String firstAge,String secondAge)
    {
        int startAge=Integer.parseInt(firstAge);
        int endAge=0;
        //System.err.println("firstAge is "+firstAge+" and secondAge is "+secondAge);
        if(secondAge !=null && secondAge.indexOf("+") !=-1)
        return " Age: "+startAge+" and above";
        endAge=Integer.parseInt(secondAge);
        if(endAge>24)
        return " Age: "+startAge+" to 25+";
        String ageTitle=" ";
        if(startAge < endAge)
         ageTitle=" Age: between "+startAge+" and "+endAge;
        else if(startAge > endAge)
        ageTitle=" ";
        else if(startAge==endAge && endAge>0)
        ageTitle=" Age: "+endAge;
        else if(startAge==endAge && endAge==0)
        ageTitle=" Age: less than one year";
          return ageTitle;
    }
    public String getSchoolType(String school_type)
    {
        String schTypeQuery=" ";
        if(school_type.equals("All"))
        schTypeQuery=" ";
        else
        schTypeQuery=" and ovc.schoolType like '%"+school_type+"%' ";
        return schTypeQuery;
    }
    public String getChildHivRiskAssessmentHivStatusQueryPart(String hivStatus)
    {
        String query="";
        if(hivStatus !=null)
        {
            if(hivStatus.equalsIgnoreCase("All"))
            return query="";
            else if(hivStatus.equalsIgnoreCase(NomisConstant.HIV_UNKNOWN))
            query=" and (hrac.hivStatus='"+hivStatus+"' or hrac.hivStatus='refusedtodisclose' or hrac.hivStatus='rtd' or hrac.hivStatus='rnd'  or LENGTH(TRIM(hrac.hivStatus))=0 or hrac.hivStatus is null)";  
            else if(hivStatus.equalsIgnoreCase(NomisConstant.HIV_RESULT_NOT_DISCLOSED))
            query=" and (hrac.hivStatus='"+hivStatus+"' or hrac.hivStatus='refusedtodisclose' or hrac.hivStatus='rtd')";  
            else
            query=" and hrac.hivStatus='"+hivStatus+"'";
        }
        return query;
    }
    public String getCaregiverKnowHivStatusQueryPart(String hivStatusAnswer)
    {
        String query=" ";
        if(hivStatusAnswer !=null)
        query=" and hrac.hivStatusQuestion='"+hivStatusAnswer+"'";
        return query;
    }
    /*public String getOvcEnrollmentPeriodQuery(String startDate, String endDate)
    {
        String periodQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All") && startDate.indexOf("-") !=-1) && (endDate !=null && !endDate.equalsIgnoreCase("All")  && endDate.indexOf("-") !=-1))
        {
            periodQuery=" and ovc.dateEnrollment between '"+startDate+"' and '"+endDate+"'";
        }
        return periodQuery;
    }*/
    public String getHivRiskAssessmentPeriodQueryPart(String startDate, String endDate)
    {
        String periodQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All") && startDate.indexOf("-") !=-1) && (endDate !=null && !endDate.equalsIgnoreCase("All")  && endDate.indexOf("-") !=-1))
        {
            periodQuery=" and hrac.dateOfAssessment between '"+startDate+"' and '"+endDate+"'";
        }
        return periodQuery;
    }
    public String getHTCReferralQuery()
    {
        return "and (UPPER(service.serviceAccessed3) like '%HTC/PMTCT%' or UPPER(service.serviceAccessed3) like  '%HIV TESTING%' or UPPER(service.serviceAccessed3) like '%H.C.T%' or UPPER(service.serviceAccessed3) like '%HTC%' or UPPER(service.serviceAccessed3) like '%ART%')";
    }// 
    public String getReferralPeriodQueryPart(String startDate, String endDate)
    {
        String periodQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        {
            periodQuery=" and ovcRef.dateOfReferral between '"+startDate+"' and '"+endDate+"'";
        }
        return periodQuery;
    }
    public String getHouseholdOvcHivRiskAssessmentReferralQueryPart()
    {
        return "from HouseholdEnrollment hhe, Ovc ovc, HivRiskAssessmentChecklist hrac, OvcReferral ovcRef where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=hrac.ovcId and ovc.ovcId=ovcRef.ovcId";
    }
    public String getChildAtRiskQuery(boolean atRisk)
    {
        String atRiskQuery=atRiskQuery=" and hrac.childAtRiskQuestion='No'";
        if(atRisk)
        atRiskQuery=" and hrac.childAtRiskQuestion='Yes'";
        return atRiskQuery;
    }
    public String getHouseholdOvcHivRiskAssessmentHivStatusUpdateQueryPart()
    {
        return "from HouseholdEnrollment hhe, Ovc ovc, HivRiskAssessmentChecklist hrac,HivStatusUpdate hsu where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=hrac.ovcId and ovc.ovcId=hsu.clientId";
    }
    public String getHouseholdOvcHivRiskAssessmentQueryPart()
    {
        return "from HouseholdEnrollment hhe, Ovc ovc, HivRiskAssessmentChecklist hrac where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=hrac.ovcId ";
    }
    public String getHouseholdOvcHivRiskAssessmentServiceQueryPart()
    {
        return "from HouseholdEnrollment hhe, Ovc ovc, HivRiskAssessmentChecklist hrac, OvcService service where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=hrac.ovcId and hrac.ovcId=service.ovcId";
    }
    /*public String getHivRiskAssessmentHivStatusCriteria(String hivStatus)
    {
        return "from HouseholdEnrollment hhe, Ovc ovc, HivRiskAssessmentChecklist hrac, OvcService service where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=hrac.ovcId and hrac.ovcId=service.ovcId";
    }*/
    /*public String getHouseholdOvcHivRiskAssessmentHivStatusServiceQueryPart()
    {
        return "from HouseholdEnrollment hhe, Ovc ovc, HivRiskAssessmentChecklist hrac, HivStatusUpdate hsu,OvcService service where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=hrac.ovcId and ovc.ovcId=hsu.clientId and hrac.ovcId=service.ovcId";
    }*/
    public String getWithdrawalStatusQuery(int status)
    {
        String query=" ";
        String reasonWithdrawal=null;
        if(status>1)
        {
            if(status==NomisConstant.GRADUATEDCODE)
            reasonWithdrawal=NomisConstant.GRADUATED;
            if(status==NomisConstant.MIGRATEDCODE)
            reasonWithdrawal=NomisConstant.MIGRATED;
            if(status==NomisConstant.LOST_TO_FOLLOWUPCODE)
            reasonWithdrawal=NomisConstant.LOST_TO_FOLLOWUP;
            if(status==NomisConstant.KNOWN_DEATHCODE)
            reasonWithdrawal=NomisConstant.DIED;
            if(status==NomisConstant.TRANSFEREDCODE)
            reasonWithdrawal=NomisConstant.TRANSFERED;
            if(status==NomisConstant.AGED_OUTCODE)
            reasonWithdrawal=NomisConstant.AGED_OUT;
            if(status==NomisConstant.VOLUNTARILY_WITHDRAWNCODE)
            reasonWithdrawal=NomisConstant.VOLUNTARILY_WITHDRAWN;
            if(reasonWithdrawal !=null)
            query=" and withdrawal.reasonWithdrawal='"+reasonWithdrawal+"'";
        }
        return query;
    }
    public String getHouseholdOvcHivRiskAssessmentServiceWithdrawalQueryPart()
    {
        return "from HouseholdEnrollment hhe, Ovc ovc, HivRiskAssessmentChecklist hrac, OvcService service,OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=hrac.ovcId and hrac.ovcId=service.ovcId and ovc.ovcId=withdrawal.ovcId";
    }
    public String getHouseholdOvcHivRiskAssessmentHivStatusServiceWithdrawalQueryPart()
    {
        return "from HouseholdEnrollment hhe, Ovc ovc, HivRiskAssessmentChecklist hrac, HivStatusUpdate hsu,OvcService service,OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=hrac.ovcId and ovc.ovcId=hsu.clientId and hrac.ovcId=service.ovcId and ovc.ovcId=withdrawal.ovcId";
    }
    public String getHouseholdOvcQueryPart()
    {
        return "from HouseholdEnrollment hhe, Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId ";
    }
    public String getHouseholdServiceTestedForHivQueryPart()
    {
        return " and hhs.healthServices like '%HTC/PMTCT%' ";
    }
    public String getDomainQuery(String domainName,String subServiceType)
    {
        String domainQuery=" ";
        if(domainName !=null)
        {
            if(domainName.equalsIgnoreCase(NomisConstant.DOMAIN_PSYCHOSOCIAL))
            domainQuery=getOvcPsychosocialSupportQuery(subServiceType);
            if(domainName.equalsIgnoreCase(NomisConstant.DOMAIN_NUTRITION))
            domainQuery=getOvcNutritionalSupportQuery(subServiceType);
            if(domainName.equalsIgnoreCase(NomisConstant.DOMAIN_HEALTH))
            domainQuery=getOvcHealthSupportQuery(subServiceType);
            if(domainName.equalsIgnoreCase(NomisConstant.DOMAIN_EDUCATION))
            domainQuery=getOvcEducationalSupportQuery(subServiceType);
            if(domainName.equalsIgnoreCase(NomisConstant.DOMAIN_PROTECTION))
            domainQuery=getOvcProtectionSupportQuery(subServiceType);
            if(domainName.equalsIgnoreCase(NomisConstant.DOMAIN_SHELTER))
            domainQuery=getOvcShelterSupportQuery(subServiceType);
            if(domainName.equalsIgnoreCase(NomisConstant.DOMAIN_ECONOMICSTRENTHENINIG))
            domainQuery=getOvcEconomicServicesQuery(subServiceType);
        }
        
        return domainQuery;
    }
    public String getOvcPsychosocialSupportQuery(String serviceType)
    {
        String serviceTypeQuery=" and (service.serviceAccessed1 is not null and service.serviceAccessed1 !='' and service.serviceAccessed1 !=' ' and service.serviceAccessed1 !='  ')";
        if(serviceType !=null)
        {
            if(serviceType.equalsIgnoreCase(NomisConstant.RECREATION_ACTIVITIES))
            serviceTypeQuery=" and (service.serviceAccessed1 like '%"+OvcServiceManager.getRecreationalService().getServiceCode()+"%' or service.serviceAccessed1 like '%"+OvcServiceManager.getRecreationalService().getServiceCode()+"%')";
            
        }
        return serviceTypeQuery;
    }
    public String getOvcNutritionalSupportQuery(String serviceType)
    {
        String serviceTypeQuery=" and (service.serviceAccessed2 is not null and service.serviceAccessed2 !='' and service.serviceAccessed2 !=' ' and service.serviceAccessed2 !='  ')";
        if(serviceType !=null)
        {
            if(serviceType.equalsIgnoreCase(NomisConstant.RECREATION_ACTIVITIES))
            serviceTypeQuery=" and service.serviceAccessed2 like '%Recreational activity%'";
            
        }
        return serviceTypeQuery;
    }
    public String getOvcHealthSupportQuery(String serviceType)
    {
        String serviceTypeQuery=" and (service.serviceAccessed3 is not null and service.serviceAccessed3 !='' and service.serviceAccessed3 !=' ' and service.serviceAccessed3 !='  ')";
        if(serviceType !=null)
        {
            if(serviceType.equalsIgnoreCase(NomisConstant.RECREATION_ACTIVITIES))
            serviceTypeQuery=" and service.serviceAccessed3 like '%Recreational activity%'";
            
        }
        return serviceTypeQuery;
    }
    public String getOvcEducationalSupportQuery(String serviceType)
    {
        String serviceTypeQuery=" and (service.serviceAccessed4 is not null and service.serviceAccessed4 !='' and service.serviceAccessed4 !=' ' and service.serviceAccessed4 !='  ')";
        if(serviceType !=null)
        {
            if(serviceType.equalsIgnoreCase(NomisConstant.RECREATION_ACTIVITIES))
            serviceTypeQuery=" and service.serviceAccessed4 like '%Recreational activity%'";
            
        }
        return serviceTypeQuery;
    }
    public String getChildAbusedQuery(int childAbused)
    {
        String serviceTypeQuery=" and service.childAbused="+childAbused;
        
        return serviceTypeQuery;
    }
    public String getChildAbusedAndLinkedToGovtQuery(int childLinkedToGovt)
    {
        String serviceTypeQuery=" and service.childAbused=2 and service.childAbused="+childLinkedToGovt;
        
        return serviceTypeQuery;
    }
    public String getOvcProtectionSupportQuery(String serviceType)
    {
        String serviceTypeQuery=" and (service.serviceAccessed5 is not null and service.serviceAccessed5 !='' and service.serviceAccessed5 !=' ' and service.serviceAccessed5 !='  ')";
        if(serviceType !=null)
        {
            if(serviceType.equalsIgnoreCase(NomisConstant.LINKED_TO_GOVT_POSTVIOLENCE))
            serviceTypeQuery=" and service.serviceAccessed5 like '%"+NomisConstant.LINKED_TO_GOVT_POSTVIOLENCE+"%'";
            
        }
        return serviceTypeQuery;
    }
    public String getAdolescentHivPreventionQuery(String serviceType)
    {
        String serviceTypeQuery=" and (service.serviceAccessed3 is not null and service.serviceAccessed3 !='' and service.serviceAccessed3 !=' ' and service.serviceAccessed3 !='  ')";
        if(serviceType !=null)
        {
            if(serviceType.equalsIgnoreCase(NomisConstant.OVC_HIVPREV))
            serviceTypeQuery=" and (service.serviceAccessed3 like '%"+NomisConstant.OVC_HIVPREV+"%' or service.serviceAccessed3 like '%"+OvcServiceManager.getAdolescentHivPreventionService().getServiceCode()+"%')";
            
        }
        return serviceTypeQuery;
    }
    public String getOvcShelterSupportQuery(String serviceType)
    {
        String serviceTypeQuery=" and (service.serviceAccessed6 is not null and service.serviceAccessed6 !='' and service.serviceAccessed6 !=' ' and service.serviceAccessed6 !='  ')";
        if(serviceType !=null)
        {
            if(serviceType.equalsIgnoreCase(NomisConstant.RECREATION_ACTIVITIES))
            serviceTypeQuery=" and (service.serviceAccessed6 like '%Recreational activity%' or service.serviceAccessed6 like '"+OvcServiceManager.getRecreationalService()+"%')";
            
        }
        return serviceTypeQuery;
    }
    public String getOvcEconomicServicesQuery(String serviceType)
    {
        String serviceTypeQuery=" and (service.serviceAccessed7 is not null and service.serviceAccessed7 !='' and service.serviceAccessed7 !=' ' and service.serviceAccessed7 !='  ')";
        if(serviceType !=null)
        {
            if(serviceType.equalsIgnoreCase(NomisConstant.DOMAIN_ECONOMICSTRENTHENINIG))
            serviceTypeQuery=" and service.serviceAccessed7 like '%"+NomisConstant.DOMAIN_ECONOMICSTRENTHENINIG+"%'";
            
        }
        return serviceTypeQuery;
    }
    public String getHESTypeQuery(String hesType)
    {
        String hesTypeQuery=getHouseholdEconomicStrentheningServiceQueryPart();
        if(hesType !=null)
        {
            if(hesType.equalsIgnoreCase("hes"))
            hesTypeQuery=getHouseholdEconomicStrentheningServiceQueryPart();
            else if(hesType.equalsIgnoreCase("microfinance"))
            hesTypeQuery=getHouseholdMicrofinanceSupportQueryPart();
            else if(hesType.equalsIgnoreCase("SILC"))
            hesTypeQuery=getHouseholdSILCQueryPart();
            else if(hesType.equalsIgnoreCase("cgforum"))
            hesTypeQuery=getHouseholdCaregiverForumQueryPart();
        }
        return hesTypeQuery;
    }
    public String getHouseholdSILCQueryPart()
    {
        return " and hhs.economicStrengtheningServices is not null and (UPPER(hhs.economicStrengtheningServices) like '%SILC%' or hhs.economicStrengtheningServices like '%"+OvcServiceManager.getSavingsAndInternalLendingCommunity().getServiceCode()+"%' )";
    }
    public String getHouseholdMicrofinanceSupportQueryPart()
    {
        return " and hhs.economicStrengtheningServices is not null and UPPER(hhs.economicStrengtheningServices) like '%MICRO FINANCE SUPPORT%' ";
    }
    public String getHouseholdCaregiverForumQueryPart()
    {
        return " and hhs.economicStrengtheningServices is not null and (UPPER(hhs.economicStrengtheningServices) like '%CAREGIVERS FORUM%' or UPPER(hhs.economicStrengtheningServices) like '%CGFORUM%' or hhs.economicStrengtheningServices like '%"+OvcServiceManager.getCaregiverForum().getServiceCode()+"%' )";
    }
    public String getHouseholdEconomicStrentheningServiceQueryPart()
    {
        return " and hhs.economicStrengtheningServices is not null and (hhs.economicStrengtheningServices !='' and hhs.economicStrengtheningServices !=' ' and hhs.economicStrengtheningServices !='  ' and hhs.economicStrengtheningServices !='   ') ";
    }
    public String getHouseholdOvcHouseholdServiceQueryPart()
    {
        return "from HouseholdEnrollment hhe, Ovc ovc,HouseholdService hhs where hhe.hhUniqueId=hhs.hhUniqueId and hhe.hhUniqueId=ovc.hhUniqueId ";
    }
    public String getHouseholdAndHouseholdServiceQueryPart()
    {
        return "from HouseholdEnrollment hhe, HouseholdService hhs where hhe.hhUniqueId=hhs.hhUniqueId ";
    }
    public String getHouseholdOvcHIVQueryPart()
    {
        return "from HouseholdEnrollment hhe, Ovc ovc,HivStatusUpdate hsu where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=hsu.clientId ";
    }
    public String getHouseholdHIVOvcServiceReportQueryPart(String tableName)
    {//ServiceReport
        return "from HouseholdEnrollment hhe, Ovc ovc, "+tableName+" service, HivStatusUpdate hsu where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId and ovc.ovcId=hsu.clientId";
    }
    public String getHouseholdOvcWithdrawalReportQueryPart()
    {
        return "from HouseholdEnrollment hhe, Ovc ovc, OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=withdrawal.ovcId ";
    }
    public String getHouseholdOvcServiceHivStatusWithdrawalReportQueryPart(String tableName)
    {
        return "from HouseholdEnrollment hhe, Ovc ovc, "+tableName+" service, OvcWithdrawal withdrawal,HivStatusUpdate hsu where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId and ovc.ovcId=withdrawal.ovcId and ovc.ovcId=hsu.clientId";
    }
    public String getHouseholdOvcServiceWithdrawalReportQueryPart(String tableName)
    {
        return "from HouseholdEnrollment hhe, Ovc ovc, "+tableName+" service, OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId and ovc.ovcId=withdrawal.ovcId ";
    }
    public String getHouseholdOvcServiceReportQueryPart(String tableName)
    {
        return "from HouseholdEnrollment hhe, Ovc ovc, "+tableName+" service where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId ";
    }
    public String getHouseholdOvcServiceHivReportQueryPart(String tableName)
    {
        return "from HouseholdEnrollment hhe, Ovc ovc, "+tableName+" service,HivStatusUpdate hsu where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId and ovc.ovcId=hsu.clientId ";
    }
    public String getHouseholdOvcBirthRegistrationServiceReportQueryPart(String tableName)
    {//ServiceReport
        return "from HouseholdEnrollment hhe, Ovc ovc, "+tableName+" service,BirthRegistration br where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=service.ovcId and ovc.ovcId=br.clientId ";
    }
    public String getHouseholdOvcFollowupQueryPart()
    {
        return "from HouseholdEnrollment hhe, Ovc ovc,FollowupSurvey fu where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=fu.ovcId";
    }
    public String getHouseholdOvcFollowupBirthRegistrationQueryPart()
    {
        return "from HouseholdEnrollment hhe, Ovc ovc,FollowupSurvey fu,BirthRegistration br where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=fu.ovcId and ovc.ovcId=br.clientId ";
    }
    public String getHouseholdCaregiverServiceReportQueryPart(String tableName)
    {//ServiceReport
        return "from HouseholdEnrollment hhe, Caregiver cgiver, "+tableName+" hhs where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=hhs.caregiverId ";
    }
    public String getHouseholdFollowupAssessmentQueryPart()
    {
        return getHouseholdQueryPart()+"HouseholdFollowupAssessment hhfa, HouseholdVulnerabilityAssessment hva where hhe.hhUniqueId=hhfa.hhUniqueId and hhfa.hhUniqueId=hva.hhUniqueId";
    }
    public String getHouseholdAndFollowupAssessmentQueryPart()
    {
        return getHouseholdQueryPart()+"HouseholdFollowupAssessment hhfa where hhe.hhUniqueId=hhfa.hhUniqueId ";
    }
    public String getHouseholdQueryPart()
    {
        return "from HouseholdEnrollment hhe, ";
    }
    public String getHouseholdObjectName()
    {
        return "HouseholdEnrollment hhe ";
    }
    public String getGraduationCheckListPeriodQuery(int startMth,int startYear,int endMth,int endYear)
    {
        return " and gcl.dateOfAssessment between '"+getStartDate(startMth, startYear)+"' and '"+getEndDate(endMth, endYear)+"'";
    }
    
    /*public String getHVIOrgUnitQuery(String[] params)
    {
        String stateName=params[0];
        String lgaName=params[1];
        String cboName=params[2];
        String wardCode=params[3];
        String partnerCode=params[10];

        if(wardCode !=null)
        wardCode=wardCode.trim();
        String additionalQueryCriteria=" ";
        String partnerQuery=" ";
        AccessManager acm=new AccessManager();
        stateCodeList=acm.getStateIdsFromUserGroupId(userGroupId);
        if(stateCodeList !=null && !stateCodeList.isEmpty())
        {
            for(int i=0; i<stateCodeList.size(); i++)
            {
                stateName=stateCodeList.get(i).toString();
                if(i==0)
                additionalQueryCriteria=" and hhe.stateCode = '"+stateName+"' ";
                else
                additionalQueryCriteria=" or hhe.stateCode = '"+stateName+"' ";
            }
        }&& (stateCodeList ==null || stateCodeList.isEmpty())
         
        if(stateName.equals("All"))
        {
           additionalQueryCriteria=" ";
        }
        else
        {
            additionalQueryCriteria=" and hhe.stateCode = '"+stateName+"' ";
            if(lgaName.equals("All"))
            {
                additionalQueryCriteria=" and hhe.stateCode = '"+stateName+"' ";
            }
            else
            {
                if(lgaName.equalsIgnoreCase("orgUnitGroup"))
                additionalQueryCriteria=" and hhe.stateCode = '"+stateName+"' and (hhe.communityCode = '"+cboName+"'"+getUmbrellaOrganizationQueryForHVI(cboName)+")";
                //additionalQueryCriteria=" and hhe.stateCode = '"+stateName+"' and (hhe.orgCode = '"+cboName+"'"+getUmbrellaOrganizationQueryForHVI(cboName)+")";
                else
                {
                    additionalQueryCriteria=" and hhe.stateCode = '"+stateName+"' and hhe.lgaCode = '"+lgaName+"'";
                    if(cboName !=null)
                    {
                        if(cboName.equals("All"))
                        additionalQueryCriteria=additionalQueryCriteria;
                        else
                        {
                            additionalQueryCriteria=" and hhe.stateCode = '"+stateName+"' and hhe.lgaCode = '"+lgaName+"' and hhe.orgCode = '"+cboName+"'";
                            if(wardCode !=null && !wardCode.equalsIgnoreCase("All"))
                            {
                                additionalQueryCriteria=" and hhe.stateCode = '"+stateName+"' and hhe.lgaCode = '"+lgaName+"' and hhe.orgCode = '"+cboName+"' and hhe.communityCode = '"+wardCode+"'";
                            }
                        }
                    }
                }
            }
        }
        if(partnerCode !=null && !partnerCode.equalsIgnoreCase("All"))
        partnerQuery=" and hhe.partnerCode='"+partnerCode+"'";
        //System.err.println("additionalQueryCriteria+partnerQuery is "+additionalQueryCriteria+partnerQuery);
        return additionalQueryCriteria+partnerQuery;
    }*/
    public String getHVIOrgUnitQuery(String[] params)
    {
        AppUtility appUtil=new AppUtility();
        String stateCode=params[0];
        String lgaCode=params[1];
        String cboCode=params[2];
        String wardCode=params[3];
        String partnerCode=params[10];
        if(wardCode !=null)
        wardCode=wardCode.trim();
        String additionalQueryCriteria=" ";
        String partnerQuery=" ";
        
        User user=appUtil.getUser();
        AccessManager acm=new AccessManager();
        
        if(stateCode.equalsIgnoreCase("All"))
        {
           additionalQueryCriteria=" ";
           List stateCodeList=acm.getStateCodeListByUserGroup(user);//getStateIdsFromUserGroupId(userGroupId);
            if(stateCodeList !=null && !stateCodeList.isEmpty())
            {
                for(int i=0; i<stateCodeList.size(); i++)
                {
                    stateCode=stateCodeList.get(i).toString();
                    if(i==0)
                    additionalQueryCriteria=" and hhe.stateCode = '"+stateCode+"' ";
                    else
                    additionalQueryCriteria=" or hhe.stateCode = '"+stateCode+"' ";
                }
            }
        }
        else
        {
            additionalQueryCriteria=" and hhe.stateCode = '"+stateCode+"' ";
            if(lgaCode.equals("All"))
            {
                additionalQueryCriteria=" and hhe.stateCode = '"+stateCode+"' ";
            }
            else
            {
                if(lgaCode.equalsIgnoreCase("orgUnitGroup"))
                additionalQueryCriteria=" and hhe.stateCode = '"+stateCode+"' and (hhe.communityCode = '"+cboCode+"'"+getUmbrellaOrganizationQueryForHVI(cboCode)+")";
                //additionalQueryCriteria=" and hhe.stateCode = '"+stateName+"' and (hhe.orgCode = '"+cboName+"'"+getUmbrellaOrganizationQueryForHVI(cboName)+")";
                else
                {
                    additionalQueryCriteria=" and hhe.stateCode = '"+stateCode+"' and hhe.lgaCode = '"+lgaCode+"'";
                    if(cboCode !=null)
                    {
                        if(cboCode.equals("All"))
                        additionalQueryCriteria=additionalQueryCriteria;
                        else
                        {
                            additionalQueryCriteria=" and hhe.stateCode = '"+stateCode+"' and hhe.lgaCode = '"+lgaCode+"' and hhe.orgCode = '"+cboCode+"'";
                            if(wardCode !=null && !wardCode.equalsIgnoreCase("All"))
                            {
                                additionalQueryCriteria=" and hhe.stateCode = '"+stateCode+"' and hhe.lgaCode = '"+lgaCode+"' and hhe.orgCode = '"+cboCode+"' and hhe.communityCode = '"+wardCode+"'";
                            }
                        }
                    }
                }
            }
        }
        if(partnerCode !=null && !partnerCode.equalsIgnoreCase("All"))
        partnerQuery=" and hhe.partnerCode='"+partnerCode+"'";
        //System.err.println("additionalQueryCriteria+partnerQuery is "+additionalQueryCriteria+partnerQuery);
        return additionalQueryCriteria+partnerQuery;
    }
    public String getOrganizationRecordsReportCriteria(String stateCode,String lgaCode,String orgCode)
    {
        String orgQueryCriteria=" ";
        if(stateCode !=null && !stateCode.equalsIgnoreCase("All"))
        {
            orgQueryCriteria="where orgRecords.state='"+stateCode+"'";
            if(lgaCode !=null && !lgaCode.equalsIgnoreCase("All"))
            {
                orgQueryCriteria=orgQueryCriteria+" and orgRecords.lga like '%"+lgaCode+"%'";
                if(orgCode !=null && !orgCode.equalsIgnoreCase("All"))
                orgQueryCriteria=orgQueryCriteria+" and orgRecords.orgCode='"+orgCode+"'";
            }
        }
        return orgQueryCriteria;
    }
    public String getUmbrellaOrganizationQueryForHVI(String cboCode)
    {
        List subOrganizationList=getCommunityListFromOrgUnitGroupAssignment(cboCode);
        String umbrellaOrganizationQuery=" ";
        for(int j=0; j<subOrganizationList.size(); j++)
        {
            umbrellaOrganizationQuery+="or hhe.communityCode = '"+(String)subOrganizationList.get(j)+"'";
        }

        return umbrellaOrganizationQuery;
    }
    public String getHVIReportCriteria(String[] params)
    {
        String startMth=params[6];
        String startYear=params[7];
        String endMth=params[8];
        String endYear=params[9];
        String periodQuery=" ";
        String assessmentNoQuery=" ";
        if(params.length>14)
        {
            int assessmentNo=Integer.parseInt(params[14].toString());
            assessmentNoQuery="and hva.assessmentNo="+assessmentNo;
        }
        String[] dateParams={startMth,startYear,endMth,endYear};
       
        String additionalQueryCriteria=getHVIOrgUnitQuery(params);
        
        if(startMth !=null && !startMth.equalsIgnoreCase("All") && startYear !=null && !startYear.equalsIgnoreCase("All") && endMth !=null && !endMth.equalsIgnoreCase("All") && endYear !=null && !endYear.equalsIgnoreCase("All"))
        periodQuery=getHhePeriodCriteria(getStartDate(dateParams),getEndDate(dateParams));
        //periodQuery=" and hva.dateOfAssessment between '"+getStartDate(dateParams)+"' and '"+getEndDate(dateParams)+"'";
        //+assessmentNoQuery
        return additionalQueryCriteria+periodQuery;
    }
    public String getCaregiverReportCriteria(String[] params)
    {
        String startMth=params[0];
        String startYear=params[1];
        String endMth=params[2];
        String endYear=params[3];
        String periodQuery=" ";    
        String[] dateParams={startMth,startYear,endMth,endYear};
        
        if(startMth !=null && !startMth.equalsIgnoreCase("All") && startYear !=null && !startYear.equalsIgnoreCase("All") && endMth !=null && !endMth.equalsIgnoreCase("All") && endYear !=null && !endYear.equalsIgnoreCase("All"))
        periodQuery=getCaregiverPeriodCriteria(getStartDate(dateParams),getEndDate(dateParams));
        //periodQuery=" and hva.dateOfAssessment between '"+getStartDate(dateParams)+"' and '"+getEndDate(dateParams)+"'";
        
        return periodQuery;
    }
    public String getHhFollowupAssessmentReportCriteria(String[] params)
    {
        String startMth=params[6];
        String startYear=params[7];
        String endMth=params[8];
        String endYear=params[9];
        String periodQuery=" ";
        String[] dateParams={startMth,startYear,endMth,endYear};
       
        String additionalQueryCriteria=getHVIOrgUnitQuery(params);
        
        if(startMth !=null && !startMth.equalsIgnoreCase("All") && startYear !=null && !startYear.equalsIgnoreCase("All") && endMth !=null && !endMth.equalsIgnoreCase("All") && endYear !=null && !endYear.equalsIgnoreCase("All"))
        periodQuery=getHhFollowupAssessmentPeriodCriteria(getStartDate(dateParams),getEndDate(dateParams));
                
        return additionalQueryCriteria+periodQuery;
    }
    public String getCaregiverAgeCriteria(int startAge,int endAge)
    {
        String ageCriteria=" cgiver.caregiverAge between "+startAge+" and "+endAge;    
        return ageCriteria;
    }
    public String getCaregiverAgeCriteria(String firstAge,String secondAge,String logic)
    {
        String ageQuery=" ";
        if(firstAge ==null || firstAge.equalsIgnoreCase("All") || secondAge ==null || secondAge.equalsIgnoreCase("All"))
        return ageQuery;
        int startAge=Integer.parseInt(firstAge);
        int endAge=Integer.parseInt(secondAge);
        if(startAge==endAge)
        ageQuery=" cgiver.caregiverAge= "+startAge;
        else if(startAge < endAge)
            ageQuery=" cgiver.caregiverAge between "+startAge+" and "+endAge;
        else if(startAge > endAge)
        ageQuery=" ";
        else
        ageQuery=" cgiver.caregiverAge between "+startAge+" and "+endAge;
        return ageQuery;
    }
    public String getOvcFollowupReportDateCriteria(String[] params)
    {
        String startMth=params[4];
        String startYear=params[5];
        String endMth=params[6];
        String endYear=params[7];
        String periodQuery=" ";
        String[] dateParams={startMth,startYear,endMth,endYear};

        if(startMth !=null && !startMth.equalsIgnoreCase("All") && startYear !=null && !startYear.equalsIgnoreCase("All") && endMth !=null && !endMth.equalsIgnoreCase("All") && endYear !=null && !endYear.equalsIgnoreCase("All"))
        periodQuery=" and fu.dateOfSurvey between '"+getStartDate(dateParams)+"' and '"+getEndDate(dateParams)+"' ";
        return periodQuery;
    }
    public String getHhAgeCriteria(String firstAge,String secondAge,String logic)
    {
        String ageQuery=" ";
        if(firstAge ==null || firstAge.equalsIgnoreCase("All") || secondAge ==null || secondAge.equalsIgnoreCase("All"))
        return ageQuery;
        int startAge=Integer.parseInt(firstAge);
        int endAge=Integer.parseInt(secondAge);
        if(startAge==endAge)
        ageQuery=" hhe.currentAge= "+startAge;
        else if(startAge < endAge)
            ageQuery=" hhe.currentAge between "+startAge+" and "+endAge;
        else if(startAge > endAge)
        ageQuery=" ";
        else
        ageQuery=" and hhe.currentAge between "+startAge+" and "+endAge;
        return ageQuery;
    }
    public String getHhAgeCriteria(int startAge,int endAge)
    {
        return " and hhe.hhAge between "+startAge+" and "+endAge;
    }
    public String getHhGenderCriteria(String gender)
    {
        if(gender !=null)
        {
            gender=gender.toUpperCase();
        }
        return " (UPPER(hhe.hhGender) ='"+gender+"')";
    }
    public String getHhePeriodCriteria(String startDate,String endDate)
    {
        String periodQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        periodQuery=" and hhe.dateOfAssessment between '"+startDate+"' and '"+endDate+"'";
        return periodQuery;
    }
    public String getCaregiverEndPeriodQuery(String endDate)
    {
        String enrollmentPeriodQuery="";
        if(endDate !=null && !endDate.equalsIgnoreCase("All"))
        enrollmentPeriodQuery=" and cgiver.dateOfEnrollment <='"+endDate+"'";
        return enrollmentPeriodQuery;
    }
    public String getCaregiverPeriodCriteria(String startDate,String endDate)
    {
        String periodQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        periodQuery=" and cgiver.dateOfEnrollment between '"+startDate+"' and '"+endDate+"'";
        return periodQuery;
    }
    public String getHvaPeriodCriteria(String startDate,String endDate)
    {
        String periodQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        periodQuery=" and hva.dateOfAssessment between '"+startDate+"' and '"+endDate+"'";
        return periodQuery;
    }
    public String getHhFollowupAssessmentPeriodCriteria(String startDate,String endDate)
    {
        String periodQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        periodQuery=" and hhfa.dateOfAssessment between '"+startDate+"' and '"+endDate+"'";
        return periodQuery;
    }
    
    public String getWithdrawalPeriodCriteria(String startDate,String endDate)
    {
        String periodQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        periodQuery= " and withdrawal.dateOfWithdrawal between '"+startDate+"' and '"+endDate+"'";
        return periodQuery;
    }
    public String getHouseholdOvcGenderNormQueryCriteria()
    {
        return this.getHouseholdQueryPart()+"Ovc ovc,GenderNormCohortAttendance gnca where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=gnca.clientId";
    }
    public String getHouseholdCaregiverGenderNormQueryCriteria()
    {
        return this.getHouseholdQueryPart()+"Caregiver cgiver,GenderNormCohortAttendance gnca where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=gnca.clientId";
    }
    public String getGenderNormAttendancePeriodCriteria(String startDate,String endDate)
    {
        String periodQuery=" ";
        if((startDate !=null && !startDate.equalsIgnoreCase("All")) && (endDate !=null && !endDate.equalsIgnoreCase("All")))
        periodQuery= " and gnca.dateOfAttendance between '"+startDate+"' and '"+endDate+"'";
        return periodQuery;
    }
    public String getHVIDateQueryByDateOfEntry(String[] dateParams)
    {
        String periodQuery=" ";
        if(dateParams !=null && dateParams.length>3)
        periodQuery=" and hhe.dateOfEntry between '"+getStartDate(dateParams)+"' and '"+getEndDate(dateParams)+"'";
        return periodQuery;
    }
    public String getHhsDateQueryByDateOfEntry(String[] dateParams)
    {
        String periodQuery=" ";
        if(dateParams !=null && dateParams.length>3)
        periodQuery=" and hhs.dateOfEntry between '"+getStartDate(dateParams)+"' and '"+getEndDate(dateParams)+"'";
        return periodQuery;
    }
    public String getHhsPeriodQuery(String startDate,String endDate)
    {
        String periodQuery=" ";
        if(startDate !=null && startDate.indexOf("-") !=-1 && endDate !=null && endDate.indexOf("-") !=-1)
        periodQuery=" and hhs.serviceDate between '"+startDate+"' and '"+endDate+"'";
        return periodQuery;
    }
    public String getCareplanAchievementPeriodQuery(String startDate,String endDate)
    {
        String periodQuery=" ";
        if(startDate !=null && startDate.indexOf("-") !=-1 && endDate !=null && endDate.indexOf("-") !=-1)
        periodQuery=" and cpa.dateOfAssessment between '"+startDate+"' and '"+endDate+"'";
        return periodQuery;
    }
    public String getHhServicePeriodCriteria(String[] params)
    {
        String startMth=params[0];
        String startYear=params[1];
        String endMth=params[2];
        String endYear=params[3];
              
        String[] dateParams={startMth,startYear,endMth,endYear};
        String periodQuery=" ";
        
        if(startMth !=null && !startMth.equalsIgnoreCase("All") && startYear !=null && !startYear.equalsIgnoreCase("All") && endMth !=null && !endMth.equalsIgnoreCase("All") && endYear !=null && !endYear.equalsIgnoreCase("All"))
        periodQuery=" and hhs.serviceDate between '"+getStartDate(dateParams)+"' and '"+getEndDate(dateParams)+"'";
        return periodQuery;
    }
    public String getHVIServiceReportCriteria(String[] params)
    {
        String startMth=params[6];
        String startYear=params[7];
        String endMth=params[8];
        String endYear=params[9];
              
        String[] dateParams={startMth,startYear,endMth,endYear};
       
        String additionalQueryCriteria=getHVIOrgUnitQuery(params);
        String periodQuery=" ";
        
        if(startMth !=null && !startMth.equalsIgnoreCase("All") && startYear !=null && !startYear.equalsIgnoreCase("All") && endMth !=null && !endMth.equalsIgnoreCase("All") && endYear !=null && !endYear.equalsIgnoreCase("All"))
        periodQuery=" and hhs.serviceDate between '"+getStartDate(dateParams)+"' and '"+getEndDate(dateParams)+"'";
        return additionalQueryCriteria+periodQuery;
    }
    public String getHVIServiceReportCriteriaByDateOfEntry(String[] params)
    {
        String startMth=params[6];
        String startYear=params[7];
        String endMth=params[8];
        String endYear=params[9];
        String[] dateParams={startMth,startYear,endMth,endYear};
        String additionalQueryCriteria=getHVIOrgUnitQuery(params);
        String periodQuery=" ";
        if(startMth !=null && !startMth.equalsIgnoreCase("All") && startYear !=null && !startYear.equalsIgnoreCase("All") && endMth !=null && !endMth.equalsIgnoreCase("All") && endYear !=null && !endYear.equalsIgnoreCase("All"))
        periodQuery=getHhsDateQueryByDateOfEntry(dateParams);
        
        return additionalQueryCriteria+periodQuery;
    }
    public String getHVIReportCriteriaForExport(String[] params)
    {
        String startMth=params[6];
        String startYear=params[7];
        String endMth=params[8];
        String endYear=params[9];
        String[] dateParams={startMth,startYear,endMth,endYear};
        String additionalQueryCriteria=getHVIOrgUnitQuery(params);
        String periodQuery=" ";
        if(startMth !=null && !startMth.equalsIgnoreCase("All") && startYear !=null && !startYear.equalsIgnoreCase("All") && endMth !=null && !endMth.equalsIgnoreCase("All") && endYear !=null && !endYear.equalsIgnoreCase("All"))
        periodQuery=getHVIDateQueryByDateOfEntry(dateParams);  
        return additionalQueryCriteria+periodQuery;
    }
    public String getQueryCriteria(String[] params)
    {
        String ageQuery=" ";
        ageQuery=getAgeCriteria(params[8],params[9]);

        String additionalQueryCriteria=getHVIOrgUnitQuery(params)+" "+ageQuery;
        return additionalQueryCriteria;        
    }
    public String getWithdrawalQueryCriteria(String[] params)
    {
        String additionalQueryCriteria=getHVIOrgUnitQuery(params);
        return additionalQueryCriteria;
    }
    public String getWithdrawalPeriodQuery(String startMth,String startYear,String endMth, String endYear)
    {
        String additionalQuery=" ";
        String[] params={startMth,startYear,endMth,endYear};
        if((startMth !=null && !startMth.equalsIgnoreCase("All")) && (startYear !=null && !startYear.equalsIgnoreCase("All")) && (endMth !=null && !endMth.equalsIgnoreCase("All")) && (endYear !=null && !endYear.equalsIgnoreCase("All")))
        {
            additionalQuery="withdrawal.dateOfWithdrawal between '"+getStartDate(params)+"' and '"+getEndDate(params)+"'";
        }
        return additionalQuery;
    }
    public String getAdditionalEnrollmentQuery(String startMth,String startYear,String endMth,String endYear)
    {
        String additionalQuery=" ";
        String[] params={startMth,startYear,endMth,endYear};
        if((startMth !=null && !startMth.equalsIgnoreCase("All")) && (startYear !=null && !startYear.equalsIgnoreCase("All")))
            additionalQuery=" ovc.dateEnrollment between '"+getStartDate(params)+"' and '"+getEndDate(params)+"'";
        return additionalQuery;
    }
    public String getAdditionalServiceQuery(String startMth,String startYear,String endMth,String endYear)
    {
        String additionalQuery=" ";
        String[] params={startMth,startYear,endMth,endYear};
        if((startMth !=null && !startMth.equalsIgnoreCase("All")) && (startYear !=null && !startYear.equalsIgnoreCase("All")))
        {
            String startDate=getStartDate(params);
            String endDate=getEndDate(params);
            additionalQuery=" service.servicedate between '"+startDate+"' and '"+endDate+"'";
        }
         return additionalQuery;
    }
    public String getSummStatQueryCriteria(String[] params)
    {
        String stateCode=params[0];
        String lgaCode=params[1];
        String cboCode=params[2];
        String wardCode=null;
        String partnerCode=null;
        if(params.length>17)
        partnerCode=params[18];
        if(params.length>18)
        wardCode=params[19];
        
        System.err.println("Partner code is "+partnerCode);
        String[] hheParams={stateCode,lgaCode,cboCode,wardCode,null,null,null,null,null,null,partnerCode};
        String additionalQueryCriteria=getHVIOrgUnitQuery(hheParams);
        return additionalQueryCriteria;
    }
    public String getOrgQueryCriteria(String[] params)
    {
        String stateName=params[0];
        String lgaName=params[1];
        String cboName=params[2];
        String wardCode=params[3];
        String partnerCode="All";
        if(params.length>14)
        partnerCode=params[14];
        String additionalQueryCriteria=" ";
        String partnerQuery=" ";
        if(partnerCode !=null && !partnerCode.equalsIgnoreCase("All"))
            partnerQuery=" and hhe.partnerCode='"+partnerCode+"'";
        if(stateName.equalsIgnoreCase("All"))
        additionalQueryCriteria=" ";
        else
        {
            if(lgaName.equalsIgnoreCase("All"))
            additionalQueryCriteria=" and hhe.stateCode = '"+stateName+"' ";
            else
            {
                if(cboName.equalsIgnoreCase("All"))
                additionalQueryCriteria=" and hhe.stateCode = '"+stateName+"' and hhe.lgaCode = '"+lgaName+"'";
                else
                {
                    if(wardCode.equalsIgnoreCase("All"))
                    additionalQueryCriteria=" and hhe.stateCode = '"+stateName+"' and hhe.orgCode = '"+cboName+"'";
                    else
                    {
                        additionalQueryCriteria=" and hhe.stateCode = '"+stateName+"' and hhe.orgCode = '"+cboName+"' and hhe.communityCode='"+wardCode+"'";
                    }
                }
            }
        }

        additionalQueryCriteria=additionalQueryCriteria+partnerQuery;
        return additionalQueryCriteria;
    }
    public String getUmbrellaOrganizationQuery(String cboCode)
    {
        String umbrellaOrganizationQuery=" ";
        try
        {
            List subOrganizationList=getCommunityListFromOrgUnitGroupAssignment(cboCode);
            for(int j=0; j<subOrganizationList.size(); j++)
            {
                umbrellaOrganizationQuery+=" or hhe.communityCode = '"+(String)subOrganizationList.get(j)+"'";
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return umbrellaOrganizationQuery;
    }
    public List getCommunityListFromOrgUnitGroupAssignment(String orgUnitGrpId)
    {
        OrganizationUnitGroupAssignmentDao ougadao=new OrganizationUnitGroupAssignmentDaoImpl();
        List subOrganizationList=new ArrayList();
        try
        {
            List list=ougadao.getOrganizationUnitGroupAssignmentByGroupId(orgUnitGrpId);
            OrganizationUnitGroupAssignment ouga=null;
            if(list !=null)
            {
                for(int i=0; i<list.size(); i++)
                {
                    ouga=(OrganizationUnitGroupAssignment)list.get(i);
                    subOrganizationList.add(ouga.getOrgunitid());
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return subOrganizationList;
    }
    public String getOrgLevelId(String stateCode,String lgaCode,String cboCode)
    {
        String orgLevelId=null;
        if(stateCode !=null && !stateCode.equalsIgnoreCase("All"))
        {
            orgLevelId=stateCode.trim();
            if(lgaCode !=null && !lgaCode.equalsIgnoreCase("All"))
            {
                orgLevelId=stateCode.trim()+"/"+lgaCode.trim();
                if(cboCode!=null && !cboCode.equalsIgnoreCase("All"))
                orgLevelId=cboCode.trim();
            }
        }
        return orgLevelId;
    }
    public String getFollowupSurveyIdQueryCriteria(String stateCode,String lgaCode,String cboCode,String partnerCode)
    {
        String ovcIdPart="";
        String partnerQuery=" ";

        if(partnerCode !=null && !partnerCode.equalsIgnoreCase("All"))
        partnerQuery=" and ovc.partner='"+partnerCode+"'";
        String additionalQueryCriteria=" ";
        if(stateCode !=null && !stateCode.equalsIgnoreCase("All"))
        {
            ovcIdPart=stateCode.trim();
            if(lgaCode !=null && !lgaCode.equalsIgnoreCase("All"))
            {
                ovcIdPart=stateCode.trim()+"/"+lgaCode.trim();
                if(cboCode!=null && !cboCode.equalsIgnoreCase("All"))
                ovcIdPart=cboCode.trim();
            }
            additionalQueryCriteria=" and followup.ovcId like '%"+ovcIdPart+"%'";
        }
        return additionalQueryCriteria+partnerQuery;
    }
    public String getReferralIdQueryCriteria(String[] params)
    {
        String stateCode=params[0];
        String lgaCode=params[1];
        String cboCode=params[2];
        String partnerCode="All";
        if(params.length>14)
        partnerCode=params[14];
        String ovcIdPart="";
        String additionalQueryCriteria=" ";
        String partnerQuery=" ";
        if(stateCode==null || stateCode.equals("All"))
        additionalQueryCriteria=" ";
        if(partnerCode !=null && !partnerCode.equalsIgnoreCase("All"))
        partnerQuery=" and ovc.partner='"+partnerCode+"'";
        
        if(stateCode==null || stateCode.equals("All"))
        additionalQueryCriteria=" ";
        else
        {
            if(lgaCode==null || lgaCode.equals("All"))
            additionalQueryCriteria=" and ovcRef.ovcId like '%"+stateCode.trim()+"%' ";
            else
            {
                ovcIdPart=stateCode.trim()+"/"+lgaCode.trim();
                if(cboCode==null || cboCode.equals("All"))
                additionalQueryCriteria=" and ovcRef.ovcId like '%"+ovcIdPart+"%'";
                else
                {
                    ovcIdPart=cboCode.trim();
                    additionalQueryCriteria=" and ovcRef.ovcId like '%"+ovcIdPart+"%'";
                }
            }
        }
        return additionalQueryCriteria+partnerQuery;
    }
    public String getCsiIdQueryCriteria(String[] params)
    {
        String stateCode=params[0];
        String lgaCode=params[1];
        String cboCode=params[2];
        String wardCode=params[14];
        String partnerCode="All";
        if(params.length>14)
        partnerCode=params[14];
        String ovcIdPart="";
        String additionalQueryCriteria=" ";
        String partnerQuery=" ";
        if(stateCode==null || stateCode.equals("All"))
        additionalQueryCriteria=" ";
        if(partnerCode !=null && !partnerCode.equalsIgnoreCase("All"))
        partnerQuery=" and hhe.partner='"+partnerCode+"'";
        
        if(stateCode==null || stateCode.equals("All"))
        additionalQueryCriteria=" ";
        else
        {
            if(lgaCode==null || lgaCode.equals("All"))
            additionalQueryCriteria=" and csi.ovcId like '%"+stateCode.trim()+"%' ";
            else
            {
                ovcIdPart=stateCode.trim()+"/"+lgaCode.trim();
                if(cboCode==null || cboCode.equals("All"))
                additionalQueryCriteria=" and csi.ovcId like '%"+ovcIdPart+"%'";// and ovc.ovcId = '"+lgaCode+"'";
                else
                {
                    ovcIdPart=cboCode.trim();
                    additionalQueryCriteria=" and csi.ovcId like '%"+ovcIdPart+"%'";// and ovc.ovcId like '%"+cboCode+"%'";
                    if(wardCode !=null && !wardCode.equalsIgnoreCase("All"))
                    {
                        wardCode=wardCode.trim();
                        additionalQueryCriteria=additionalQueryCriteria+" and ovc.ward='"+wardCode+"' "; 
                    }
                }
            }
        }
        return additionalQueryCriteria+partnerQuery;
    }
    
    public OrganizationUnitHirachy getOrganizationUnitHirachyByName(String hirachyName)
    {
        OrganizationUnitHirachyDao ouhdao=new OrganizationUnitHirachyDaoImpl();
        OrganizationUnitHirachy ouh=null;
        try
        {
            ouh=ouhdao.getOrganizationUnitHirachyByName(hirachyName);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return ouh;
    }
    public CareAndSupportDao getCareAndSupportDaoInstance()
    {
        return new CareAndSupportDaoImpl();
    }
    public OvcDao getOvcDaoInstance()
    {
        return new OvcDaoImpl();
    }
    public ChildStatusIndexDao getChildStatusIndexDaoInstance()
    {
        return new ChildStatusIndexDaoImpl();
    }
    public OvcServiceDao getOvcServiceDaoInstance()
    {
        return new OvcServiceDaoImpl();
    }
    public OvcReferralDao getOvcReferralDaoInstance()
    {
        return new OvcReferralDaoImpl();
    }
    public FollowupDao getFollowupDaoInstance()
    {
        return new FollowupDaoImpl();
    }
    public CaregiverDao getCaregiverDaoInstance()
    {
        return new CaregiverDaoImpl();
    }
    public HouseholdEnrollmentDao getHouseholdEnrollmentDaoInstance()
    {
        return new HouseholdEnrollmentDaoImpl();
    }
    public HouseholdVulnerabilityAssessmentDao getHouseholdVulnerabilityAssessmentDaoInstance()
    {
        return new HouseholdVulnerabilityAssessmentDaoImpl();
    }
    public HouseholdFollowupAssessmentDao getHouseholdFollowupAssessmentDaoInstance()
    {
        return new HouseholdFollowupAssessmentDaoImpl();
    }
    public HouseholdServiceDao getHouseholdServiceDaoInstance()
    {
        return new HouseholdServiceDaoImpl();
    }
    public OvcWithdrawalDao getOvcWithdrawalDaoInstance()
    {
        return new OvcWithdrawalDaoImpl();
    }
    public CareplanAchievementDao getCareplanAchievementDaoInstance()
    {
        return new CareplanAchievementDaoImpl();
    }
    public HivRiskAssessmentChecklistDao getHivRiskAssessmentChecklistDaoInstance()
    {
        return new HivRiskAssessmentChecklistDaoImpl();
    }
    public HivStatusUpdateDao getHivStatusUpdateDaoInstance()
    {
        return new HivStatusUpdateDaoImpl();
    }
    public ReferralDirectoryDao getReferralDirectoryDaoInstance()
    {
        return new ReferralDirectoryDaoImpl();
    }
    public OrganizationRecordsDao getOrganizationRecordsDaoInstance()
    {
        return new OrganizationRecordsDaoImpl();
    }
    public WardDao getWardDaoInstance()
    {
        return new WardDaoImpl();
    }
    public PartnersDao getPartnerDaoInstance()
    {
        return new PartnersDaoImpl();
    }//
    public OvcSchoolDao getOvcSchoolDaoInstance()
    {
        return new OvcSchoolDaoImpl();
    }
    public EligibilityCriteriaDao getEligibilityCriteriaDaoInstance()
    {
        return new EligibilityCriteriaDaoImpl();
    }
    public CaregiverExpenditureAndSchoolAttendanceDao getCaregiverExpenditureAndSchoolAttendanceDaoInstance()
    {
        return new CaregiverExpenditureAndSchoolAttendanceDaoImpl();
    }
    public SchoolAttendanceDao getSchoolAttendanceDaoInstance()
    {
        return new SchoolAttendanceDaoImpl();
    }
    public SchoolAttendanceTrackerDao getSchoolAttendanceTrackerDaoInstance()
    {
        return new SchoolAttendanceTrackerDaoImpl();
    }
    public OvcSchoolAttendanceDao getOvcSchoolAttendanceDaoInstance()
    {
        return new OvcSchoolAttendanceDaoImpl();
    }
    public ChildSchoolStatusDao getChildSchoolStatusDaoInstance()
    {
        return new ChildSchoolStatusDaoImpl();
    }
    public StatesDao getStateDaoInstance()
    {
        return new StatesDaoImpl();
    }
    public LgaDao getLgaDaoInstance()
    {
        return new LgaDaoImpl();
    }
    public OptionsManagerDao getOptionsManagerDaoInstance()
    {
        return new OptionsManagerDaoImpl();
    }//
    public UserDao getUserDaoInstance()
    {
        return new UserDaoImpl();
    }
    public DataImportUploadManagerDao getDataImportUploadManagerDaoInstance()
    {
        return new DataImportUploadManagerDaoImpl();
    }
    public DatimReportDao getDatimReportDaoInstance()
    {
        return new DatimReportDaoImpl();
    }
    public BirthRegistrationDao getBirthRegistrationDaoInstance()
    {
        return new BirthRegistrationDaoImpl();
    }
}
