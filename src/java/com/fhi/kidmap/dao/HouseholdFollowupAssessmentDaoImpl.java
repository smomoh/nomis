/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.HivStatus;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.HouseholdFollowupAssessment;
import com.fhi.kidmap.business.HouseholdVulnerabilityAssessment;
import com.fhi.nomis.OperationsManagement.HivRecordsManager;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class HouseholdFollowupAssessmentDaoImpl implements HouseholdFollowupAssessmentDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
    AppUtility appUtil=new AppUtility();
    String hheAndFollowupQuery=util.getHouseholdQueryPart()+"HouseholdFollowupAssessment hhfa where hhe.hhUniqueId=hhfa.hhUniqueId";
    

public HouseholdFollowupAssessment getMostRecentHouseholdFollowupAssessment(String hhUniqueId) throws Exception 
    {
        HouseholdFollowupAssessment hhfa=null;
         try 
         {
            List hvaList=getFollowupAssessmentsDescOrder(hhUniqueId);
            
            if(hvaList !=null && !hvaList.isEmpty())
            {
                //The list comes in descending order of date 
                hhfa=(HouseholdFollowupAssessment)hvaList.get(0);   
            }
        } 
         
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return hhfa;
    }
public int getNumberOfHouseholdsWithFollowupAssessement(String additionalQuery,boolean currentlyEnrolled) throws Exception
{
    String currentlyEnrolledQuery="";
    if(currentlyEnrolled)
    currentlyEnrolledQuery=util.getHouseholdWithdrawnFromProgramQuery("No");
    List list = new ArrayList();
    int numberOfHouseholds=0;
        try
        {
            String query="select count(distinct hhe.hhUniqueId) from HouseholdEnrollment hhe,HouseholdFollowupAssessment hhfa where hhe.hhUniqueId=hhfa.hhUniqueId and hhfa.vulnerabilityScore >"+NomisConstant.NOTVULNERABLE_ENDVALUE+additionalQuery+currentlyEnrolledQuery+")";
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        if(list !=null && !list.isEmpty())
        {
            numberOfHouseholds=Integer.parseInt(list.get(0).toString());
        }
        return numberOfHouseholds;
}
public List getListOfHouseholdsWithFollowupAssessement(String additionalQuery,boolean currentlyEnrolled) throws Exception
{
    String currentlyEnrolledQuery="";
    if(currentlyEnrolled)
    currentlyEnrolledQuery=util.getHouseholdWithdrawnFromProgramQuery("No");
    List mainList = new ArrayList();
    
        try
        {
            String query="from HouseholdEnrollment hhe,HouseholdFollowupAssessment hhfa where hhe.hhUniqueId=hhfa.hhUniqueId and hhfa.vulnerabilityScore >"+NomisConstant.NOTVULNERABLE_ENDVALUE+additionalQuery+currentlyEnrolledQuery+")";
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(query).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                List idList = new ArrayList();
                HouseholdEnrollment hhe=null;
                
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    hhe=(HouseholdEnrollment)objArray[0];
                    if(!idList.contains(hhe.getHhUniqueId()))
                    {
                        mainList.add(hhe);
                        idList.add(hhe.getHhUniqueId());
                    }
                    
                }
            }
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        
       return mainList;
}
public HouseholdFollowupAssessment getHouseholdFollowupAssessmentByUniqueIdAndHnvaId(String hhUniqueId,int hhvaId) throws Exception
{
        List list = null;
        HouseholdFollowupAssessment hhfa = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdFollowupAssessment hhfa where hhfa.hhUniqueId=:uid and hhfa.hhvaId=:hId").setString("uid", hhUniqueId).setInteger("hId", hhvaId).list();
            tx.commit();
            session.close();
        }
         catch (Exception ex)
         {
             session.close();
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            hhfa=((HouseholdFollowupAssessment)list.get(0));
        }
        return hhfa;
}
public List getFollowupAssessmentsDescOrder(String hhUniqueId) throws Exception
{
    List mainList = new ArrayList();

    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(util.getHouseholdAndFollowupAssessmentQueryPart()+" and hhfa.hhUniqueId=:id order by hhfa.dateOfAssessment desc").setString("id", hhUniqueId).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                HouseholdFollowupAssessment hhfa=(HouseholdFollowupAssessment)objArray[1];
                mainList.add(hhfa); 
            }
        }
    }
     catch (Exception ex)
     {
         session.close();
         ex.printStackTrace();
        //throw new Exception(he);
     }
    return mainList;
}
    public List getFollowupAssessmentsAscOrder(String hhUniqueId) throws Exception
    {
        List mainList = new ArrayList();
        
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdAndFollowupAssessmentQueryPart()+" and hhfa.hhUniqueId=:id order by hhfa.dateOfAssessment asc").setString("id", hhUniqueId).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    HouseholdFollowupAssessment hhfa=(HouseholdFollowupAssessment)objArray[1];
                    //HouseholdVulnerabilityAssessment hva=(HouseholdVulnerabilityAssessment)objArray[2];
                    //hhfa.setHva(hva);
                    mainList.add(hhfa);
                }    
            }
        }
         catch (Exception ex)
         {
             session.close();
             ex.printStackTrace();
            //throw new Exception(he);
         }
        return mainList;
    }
    public List getFollowupAssessmentsWithoutScoresByCommunityCode(String communityCode) throws Exception
    {
        List mainList = new ArrayList();
        
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdQueryPart()+"HouseholdFollowupAssessment hhfa where hhe.hhUniqueId=hhfa.hhUniqueId and hhe.communityCode=:ccode and hhfa.vulnerabilityScore <"+NomisConstant.VULNERABLE_STARTVALUE).setString("ccode", communityCode).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    HouseholdFollowupAssessment hhfa=(HouseholdFollowupAssessment)objArray[1];
                    mainList.add(hhfa);
                }    
            }
        }
         catch (Exception ex)
         {
             session.close();
             ex.printStackTrace();
            //throw new Exception(he);
         }
        return mainList;
    }
    public List getFollowupAssessments(String hhUniqueId) throws Exception
    {
        List mainList = new ArrayList();
        
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdFollowupAssessmentQueryPart()+" and hhfa.dateOfAssessment=hva.dateOfAssessment and hva.hhUniqueId=:id").setString("id", hhUniqueId).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    HouseholdFollowupAssessment hhfa=(HouseholdFollowupAssessment)objArray[1];
                    HouseholdVulnerabilityAssessment hva=(HouseholdVulnerabilityAssessment)objArray[2];
                    hhfa.setHva(hva);
                    mainList.add(hhfa);
                }    
            }
        }
         catch (Exception ex)
         {
             session.close();
             ex.printStackTrace();
            //throw new Exception(he);
         }
        return mainList;
    }
    public int getNumberOfHouseholdFollowupAssessmentsPerCohort(String additionalCriteria) throws Exception
    {
        int numberFollowedUp=0;
        List list=null;
        try 
        {
            //System.err.println("select count(distinct hhfa.hhUniqueId) "+hheAndFollowupQuery+additionalCriteria);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count(distinct hhfa.hhUniqueId) "+hheAndFollowupQuery+additionalCriteria).list();
            tx.commit();
            session.close();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            session.close();
        }
        if(list !=null && !list.isEmpty())
        numberFollowedUp=Integer.parseInt(list.get(0).toString());
        return numberFollowedUp;
    }
    public void deleteAllAssessmentPerHousehold(String hhUniqueId) throws Exception
   {
       List list=getListOfAssessmentsPerHousehold(hhUniqueId);
        if(list !=null)
        {
            for(int i=0; i<list.size(); i++)
            {
                HouseholdFollowupAssessment hhfa=(HouseholdFollowupAssessment)list.get(i);
                deleteHouseholdFollowupAssessment(hhfa);
            }
        }
   }
    public int getNumberOfHouseholdWithoutFollowupAssessmentsPerCohort(String additionalCriteria) throws Exception
    {
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        int numberOfHouseholds=hhedao.getNumberOfHouseholdsEnrolled(additionalCriteria);
        int numberFollowedUp=getNumberOfHouseholdFollowupAssessmentsPerCohort(additionalCriteria);
        int numberNotFollowedUp=0;
        
        numberNotFollowedUp=numberOfHouseholds-numberFollowedUp;
        if(numberNotFollowedUp <0)
        numberNotFollowedUp=0;
        return numberNotFollowedUp;
    }
    public List getListOfHouseholdFollowupAssessmentsPerCohort(String additionalCriteria) throws Exception
    {
        List hheList=new ArrayList();
        List list=null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(hheAndFollowupQuery+additionalCriteria).list();
            tx.commit();
            session.close();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            session.close();
        }
        if(list !=null && !list.isEmpty())
        {
            List idList=new ArrayList();
            HouseholdEnrollment hhe=null;
            for(int i=0; i<list.size(); i++)
            {
                Object[] obj=(Object[])list.get(i);
                hhe=(HouseholdEnrollment)obj[0];
                if(idList.contains(hhe.getHhUniqueId()))
                continue;
                hheList.add(hhe);
                idList.add(hhe.getHhUniqueId());
            }
        }
        return hheList;
    }
    public List getListOfHouseholdIdsInFollowupAssessmentsPerCohort(String additionalCriteria) throws Exception
    {
        List hheList=new ArrayList();
        List list=null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct hhfa.hhUniqueId "+hheAndFollowupQuery+additionalCriteria).list();
            tx.commit();
            session.close();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            session.close();
        }
        
        return list;
    }
    public List getListOfHouseholdWithoutFollowupAssessmentsPerCohort(String additionalCriteria) throws Exception
    {
        List listOfHouseholdsNotFollowedup=new ArrayList();
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        List ListOfHouseholds=hhedao.getListOfHouseholdEnrollment(additionalCriteria,null);
        List listOfHouseholdIdsFollowedUp=getListOfHouseholdIdsInFollowupAssessmentsPerCohort(additionalCriteria);
        if(ListOfHouseholds !=null)
        {
            HouseholdEnrollment hhe=null;
            for(int i=0; i<ListOfHouseholds.size(); i++)
            {
                hhe=(HouseholdEnrollment)ListOfHouseholds.get(i);
                if(listOfHouseholdIdsFollowedUp.contains(hhe.getHhUniqueId()))
                continue;
                listOfHouseholdsNotFollowedup.add(hhe);
            }
        }
        
        return listOfHouseholdsNotFollowedup;
    }
    public List getListOfAssessments(String additionalCriteria) throws Exception
    {
        List list=null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //System.err.println(hheAndFollowupQuery+additionalCriteria+" order by hhfa.dateOfAssessment desc");
            list = session.createQuery(hheAndFollowupQuery+additionalCriteria+" order by hhfa.dateOfAssessment desc").list();
            
        tx.commit();
        session.close();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            session.close();
        }
        return list;
    }
    public HouseholdFollowupAssessment getHouseholdFollowupAssessment(String recordId) throws Exception
    {
        HouseholdFollowupAssessment hhfa=null;
        List list=null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdFollowupAssessment hhfa where hhfa.recordId =:id").setString("id", recordId).list();

        tx.commit();
        session.close();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            session.close();
        }
        if(list !=null && !list.isEmpty())
        {
            hhfa=(HouseholdFollowupAssessment)list.get(0);
        }
        return hhfa;
    }
    public HouseholdFollowupAssessment getHouseholdFollowupAssessmentByIdAndDate(String hhUniqueId,String dateOfAssessment) throws Exception
    {
        HouseholdFollowupAssessment hhfa=null;
        List list=null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdFollowupAssessment hhfa where hhfa.hhUniqueId=:id and hhfa.dateOfAssessment =:assmtdate").setString("id", hhUniqueId).setString("assmtdate", dateOfAssessment).list();

        tx.commit();
        session.close();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            session.close();
        }
        if(list !=null && !list.isEmpty())
        {
            hhfa=(HouseholdFollowupAssessment)list.get(0);
        }
        return hhfa;
    }
    public List getListOfAssessmentsPerHousehold(String hhUniqueId) throws Exception
    {
        List list=null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdFollowupAssessment hhfa where hhfa.hhUniqueId =:id order by hhfa.dateOfAssessment desc").setString("id", hhUniqueId).list();

        tx.commit();
        session.close();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            session.close();
        }
        return list;
    }

    public List getAllHouseholdFollowupAssessments() throws Exception
    {
        List list=null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdFollowupAssessment hhfa order by hhfa.dateOfAssessment desc").list();

        tx.commit();
        session.close();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            session.close();
        }
        return list;
    }
    public String generateUniqueId() throws Exception
    {
        String recordId=appUtil.generateUniqueId();
        if(getHouseholdFollowupAssessment(recordId) !=null)
        generateUniqueId();
         return recordId;
    }
    public void saveHouseholdFollowupAssessment(HouseholdFollowupAssessment hhfa) throws Exception
    {
        try
        {
            if(hhfa ==null)
            return;
            if(hhfa.getRecordId()==null || hhfa.getRecordId().equalsIgnoreCase("") || hhfa.getRecordId().equalsIgnoreCase(" "))
            hhfa.setRecordId(generateUniqueId());
            hhfa.setVulnerabilityScore(getFollowupVulnerabilityScore(hhfa));
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            HivStatusUpdate defaultHivStatus=null;
            if(hhfa.getHivStatus()==null)
            {
                //If no HIV status is recorded, get the active one or create a new unknown HIV status
                defaultHivStatus=getDefaultHivStatus(hhfa.getHhUniqueId(),hhfa.getDateOfAssessment(),hhfa.getPointOfService());
                hhfa.setHivStatus(defaultHivStatus.getHivStatus());
            }
            session.save(hhfa);
            tx.commit();
            session.close();
            //If an existing active HIV status is used, set the date before you update
            if(defaultHivStatus !=null)
            hhfa.setDateOfAssessment(defaultHivStatus.getDateOfCurrentStatus());
            saveHouseholdHeadHIVStatus(hhfa);   
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public void updateHouseholdFollowupAssessment(HouseholdFollowupAssessment hhfa) throws Exception
    {
        try
        {
            if(hhfa !=null)
            {
                if(getHouseholdFollowupAssessment(hhfa.getRecordId())==null)
                {
                    HouseholdFollowupAssessment existingHhfa=getHouseholdFollowupAssessmentByIdAndDate(hhfa.getHhUniqueId(), hhfa.getDateOfAssessment());
                    if(existingHhfa !=null)
                    {
                        hhfa.setRecordId(existingHhfa.getRecordId());
                        hhfa=updateHouseholdEnrollmentWithRevisedData(hhfa,existingHhfa);
                    }
                    else
                    return;
                }
                hhfa.setVulnerabilityScore(getFollowupVulnerabilityScore(hhfa));
                HivStatusUpdate defaultHivStatus=null;
                if(hhfa.getHivStatus()==null)
                {
                    //If no HIV status is recorded, get the active one or create a new unknown HIV status
                    defaultHivStatus=getDefaultHivStatus(hhfa.getHhUniqueId(),hhfa.getDateOfAssessment(),hhfa.getPointOfService());
                    hhfa.setHivStatus(defaultHivStatus.getHivStatus());
                }
                
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(hhfa);
                tx.commit();
                session.close();
                //If an existing active HIV status is used, set the date before you update
                if(defaultHivStatus !=null)
                hhfa.setDateOfAssessment(defaultHivStatus.getDateOfCurrentStatus());
                saveHouseholdHeadHIVStatus(hhfa);
            }
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    private HouseholdFollowupAssessment updateHouseholdEnrollmentWithRevisedData(HouseholdFollowupAssessment hhfa,HouseholdFollowupAssessment existingHhfa) throws Exception
    {
        try
        {
            if(hhfa !=null)
            {
                //HouseholdFollowupAssessment existingHhfa=getHouseholdFollowupAssessmentByIdAndDate(hhfa.getHhUniqueId(), hhfa.getDateOfAssessment());
                if(hhfa.getChildEducationLevel()==0)
                hhfa.setChildEducationLevel(existingHhfa.getChildEducationLevel());
                if(hhfa.getHhEducationLevel()==0)
                hhfa.setHhEducationLevel(existingHhfa.getHhEducationLevel());
                if(hhfa.getHhEducationLevel()==0)
                hhfa.setHhEducationLevel(existingHhfa.getEducationLevel()); 
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return hhfa;
    }
    public void markedForDelete(HouseholdFollowupAssessment hhfa) throws Exception
    {
        try
        {
            hhfa.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateHouseholdFollowupAssessment(hhfa);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteHouseholdFollowupAssessment(HouseholdFollowupAssessment hhfa) throws Exception
    {
        try
        {
            if(hhfa==null || getHouseholdFollowupAssessment(hhfa.getRecordId())==null)
            return;
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(hhfa);
            tx.commit();
            session.close();
            util.saveDeletedRecord(hhfa.getHhUniqueId(),null, "hhFollowupAssessment", hhfa.getDateOfAssessment());
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public void saveHouseholdHeadHIVStatus(HouseholdFollowupAssessment hhfa) throws Exception
    {
        System.err.println("hhfa.getHivStatus() is "+hhfa.getHivStatus());
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        HivStatusUpdate hsu=new HivStatusUpdate();
        String dateOfCurrentHivStatus=hhfa.getDateOfAssessment();
        hsu.setClientEnrolledInCare(hhfa.getEnrolledInCare());
        hsu.setEnrolledOnART(hhfa.getEnrolledOnART());
        hsu.setOrganizationClientIsReferred(hhfa.getFacilityId());
        hsu.setClientId(hhfa.getHhUniqueId());
        hsu.setClientType(NomisConstant.HOUSEHOLD_TYPE);
        hsu.setDateModified(DateManager.getCurrentDate());
        hsu.setDateOfCurrentStatus(dateOfCurrentHivStatus);
        hsu.setHivStatus(HivRecordsManager.getHivStatusScode(hhfa.getHivStatus()));
        hsu.setRecordStatus("active");
        hsu.setPointOfUpdate(hhfa.getPointOfService());
        hsudao.saveHivStatusUpdate(hsu);
    }
    public static HivStatusUpdate getDefaultHivStatus(String clientId,String enrollmentDate,String pointOfService)
    {
        HivStatus hivStatus=new HivStatus();
        HivStatusUpdate defaultHivStatus=hivStatus.getDefaultHivStatus(clientId,enrollmentDate,pointOfService);
        
        return defaultHivStatus;
    }
    public int getFollowupVulnerabilityScore(HouseholdFollowupAssessment hhfa) throws Exception
    {
        int vulnerabilityScore=0;
        if(hhfa !=null)
        {
           vulnerabilityScore+=hhfa.getEducationLevel();
           vulnerabilityScore+=hhfa.getFoodSecurityAndNutrition();
           vulnerabilityScore+=hhfa.getHealth();
           vulnerabilityScore+=hhfa.getHhHeadship();
           vulnerabilityScore+=hhfa.getHhIncome();
           vulnerabilityScore+=hhfa.getMeansOfLivelihood();
           vulnerabilityScore+=hhfa.getShelterAndHousing();
           vulnerabilityScore+=hhfa.getProtection();
        }
        return vulnerabilityScore;
    }
}
