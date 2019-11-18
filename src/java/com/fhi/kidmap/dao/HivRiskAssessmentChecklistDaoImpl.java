/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.HivRiskAssessmentChecklist;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.business.Ovc;
import com.fhi.nomis.nomisutils.AppUtility;
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
public class HivRiskAssessmentChecklistDaoImpl implements HivRiskAssessmentChecklistDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    AppUtility appUtil=new AppUtility();
    DaoUtil util=new DaoUtil();
public List getAllHivRiskAssessmentByCommunity(String communityCode) throws Exception
{
    List hracList=new ArrayList();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query=util.getHouseholdOvcHivRiskAssessmentQueryPart()+" and hhe.communityCode=:cc";
        System.err.println(query);
        List list = session.createQuery(query).setString("cc", communityCode).list();
        tx.commit();
        session.close();
        if(list !=null)
        {
            //HivRiskAssessmentChecklist 
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                hracList.add(objArray[2]);
            }
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return hracList;
}
public List getListOfHivRiskAssessmentWithBlankHivStatus(String communityCode) throws Exception
{
    List hracList=new ArrayList();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query=util.getHouseholdOvcHivRiskAssessmentQueryPart()+" and hhe.communityCode=:cc and (hrac.hivStatus is null or hrac.hivStatus='' or hrac.hivStatus=' ' or hrac.hivStatus='  ' or LENGTH(TRIM(hrac.hivStatus))=0)";
        System.err.println(query);
        List list = session.createQuery(query).setString("cc", communityCode).list();
        tx.commit();
        session.close();
        if(list !=null)
        {
            //HivRiskAssessmentChecklist 
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                hracList.add(objArray[2]);
            }
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return hracList;
}
public int getNumberOfOvcAssessedForHivRiskAndReferredForTesting(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
{
    int numberOfAdolescents=0;
    try
    {
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count (distinct ovc.ovcId)"+util.getHouseholdOvcHivRiskAssessmentReferralQueryPart()+additionalQuery+currentlyEnrolledQuery+" and ovcRef.dateOfReferral>=hrac.dateOfAssessment "+util.getReferralPeriodQueryPart(startDate, endDate);
        System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            numberOfAdolescents=Integer.parseInt(list.get(0).toString());
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return numberOfAdolescents;
}
public List getListOfOvcAssessedForHivRiskAndReferredForTesting(String additionalQuery,String startDate,String endDate,boolean currentlyEnrolled) throws Exception
{
    List listOfOvc=new ArrayList();
    try
    {
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query=util.getHouseholdOvcHivRiskAssessmentReferralQueryPart()+additionalQuery+currentlyEnrolledQuery+" and ovcRef.dateOfReferral>=hrac.dateOfAssessment "+util.getReferralPeriodQueryPart(startDate, endDate);
        System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null)
        {
            List ovcIdList=new ArrayList();
            Ovc ovc=null;
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                ovc=(Ovc)objArray[1];
                if(!ovcIdList.contains(ovc.getOvcId()))
                {
                    listOfOvc.add(ovc);
                    ovcIdList.add(ovc.getOvcId());
                }
            }
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return listOfOvc;
}
public List getListOfOvcNotAtRiskWithUnknownHivStatus() throws Exception
{
    List list=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select distinct hrac.ovcId from HivRiskAssessmentChecklist hrac, HivStatusUpdate hsu where hrac.ovcId=hsu.clientId and hrac.childAtRiskQuestion='No' "+util.getActiveHivStatusCriteriaForHivRiskAssessment(NomisConstant.HIV_UNKNOWN,NomisConstant.OVC_TYPE);
        System.err.println(query);
        list = session.createQuery(query).list();
        tx.commit();
        session.close();  
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return list;
}
public void setHivStatusOfUnknownOvcNotAtRiskToTestNotIndicated(HivRiskAssessmentChecklist hrac)
{
    try
    {
    if(hrac !=null)
    {
        if(hrac.getChildAtRiskQuestion()==null || hrac.getChildAtRiskQuestion().equalsIgnoreCase("No"))
        {
            HivStatusUpdate hsu=util.getHivStatusUpdateDaoInstance().getActiveHivStatusUpdatesByClientId(hrac.getOvcId());
            if(hsu !=null && (hsu.getHivStatus().equalsIgnoreCase(NomisConstant.HIV_UNKNOWN)))
            hsu.setHivStatus(NomisConstant.HIV_TEST_NOT_INDICATED);
        }
    }
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
}
public HivRiskAssessmentChecklist getHivRiskAssessmentChecklistWithCleanedHivStatus(HivRiskAssessmentChecklist hrac)
{
    if(hrac.getHivStatusQuestion() !=null && hrac.getHivStatusQuestion().equalsIgnoreCase("Yes"))
    {
        if(hrac.getHivStatus()==null || hrac.getHivStatus().trim().length()==0)
        hrac.setHivStatus(NomisConstant.HIV_NEGATIVE);
    }
    else
    {
        hrac.setHivStatusQuestion("No");
        hrac.setHivStatus(NomisConstant.HIV_UNKNOWN);
    }
    return hrac;
}
public HivRiskAssessmentChecklist getLastHivRiskAssessmentForOvc(String ovcId) throws Exception
{
    HivRiskAssessmentChecklist hrac=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select distinct hrac.dateOfAssessment from HivRiskAssessmentChecklist hrac where hrac.ovcId=:id order by hrac.dateOfAssessment desc";
        //System.err.println(query);
        List list = session.createQuery(query).setString("id", ovcId).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            String lastDateOfAssessment=(String)list.get(0);
            hrac=getHivRiskAssessmentChecklist(ovcId,lastDateOfAssessment);
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return hrac;
}
public int getNoOfOvcAssessedForHivRiskByHivStatus(String additionalQuery,String startDate,String endDate,String hivStatus,boolean currentlyEnrolled) throws Exception
{
    int numberOfAdolescents=0;
    try
    {
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count (distinct ovc.ovcId) "+util.getHouseholdOvcHivRiskAssessmentQueryPart()+additionalQuery+currentlyEnrolledQuery+util.getHivRiskAssessmentPeriodQueryPart(startDate,endDate)+util.getOvcBaselineHivStatusCriteria(hivStatus);
        System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            numberOfAdolescents=Integer.parseInt(list.get(0).toString());
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return numberOfAdolescents;
}
public int getNumberOfOvcThatHasBeenAssessedOnHivRiskAssessment(String additionalQuery,String startDate,String endDate,boolean atRisk,boolean currentlyEnrolled) throws Exception
{
    int numberOfAdolescents=0;
    try
    {
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=" and ovc.withdrawnFromProgram='No'";
        String atRiskQuery=util.getChildAtRiskQuery(atRisk);
        
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count (distinct ovc.ovcId)"+util.getHouseholdOvcHivRiskAssessmentQueryPart()+additionalQuery+currentlyEnrolledQuery+util.getHivRiskAssessmentPeriodQueryPart(startDate,endDate);//+atRiskQuery;
        System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            numberOfAdolescents=Integer.parseInt(list.get(0).toString());
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return numberOfAdolescents;
}
public int getNoOfOvcAssessedForHIVRiskServedAndWithdrawn(String additionalQuery,String startDate,String endDate,String hivStatus) throws Exception
{
    int numberOfAdolescents=0;
    try
    {
        String baseQuery=util.getHouseholdOvcHivRiskAssessmentServiceWithdrawalQueryPart()+util.getChildHivRiskAssessmentHivStatusQueryPart(hivStatus);//util.getActiveHivStatusCriteria(hivStatus, NomisConstant.OVC_TYPE);
        if(hivStatus==null || hivStatus.equalsIgnoreCase("All"))
        baseQuery=util.getHouseholdOvcHivRiskAssessmentServiceWithdrawalQueryPart();
             
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count (distinct ovc.ovcId)"+baseQuery+additionalQuery+util.getHivRiskAssessmentPeriodQueryPart(startDate,endDate)+util.getOvcServiceDateQuery(startDate, endDate)+util.getWithdrawalServicePeriodCriteria(startDate) ;
        System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            numberOfAdolescents=Integer.parseInt(list.get(0).toString());
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return numberOfAdolescents;
}
public int getNoOfActiveOvcAssessedForHIVRiskAndServed(String additionalQuery,String startDate,String endDate,String hivStatus) throws Exception
{
    int numberOfOvc=0;
    try
    {
        String baseQuery=util.getHouseholdOvcHivRiskAssessmentServiceQueryPart()+util.getChildHivRiskAssessmentHivStatusQueryPart(hivStatus)+util.getOvcWithdrawnFromProgramQuery("No");
        if(hivStatus==null || hivStatus.equalsIgnoreCase("All"))
        baseQuery=util.getHouseholdOvcHivRiskAssessmentServiceQueryPart();
        //if(hivStatus !=null || !hivStatus.equalsIgnoreCase("All"))
        
                
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count (distinct ovc.ovcId)"+baseQuery+additionalQuery+util.getHivRiskAssessmentPeriodQueryPart(startDate,endDate)+util.getOvcServiceDateQuery(startDate, endDate);
        System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            numberOfOvc=Integer.parseInt(list.get(0).toString());
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return numberOfOvc;
}
public int getNoOfOvcAssessedForHIVRiskAndServed(String additionalQuery,String startDate,String endDate,String hivStatus,boolean atRisk,boolean currentlyEnrolled) throws Exception
{
    int numberOfOvc=0;
    try
    {
        int hivUnknownRiskAssessed=getNoOfHivUnknownOvcAssessedForHIVRiskAndServed(additionalQuery,startDate,endDate,atRisk,currentlyEnrolled);
        int hivNegativeRiskAssessed=getNoOfHivNegativeOvcAssessedForHIVRiskAndServed(additionalQuery,startDate,endDate,atRisk,currentlyEnrolled);
        numberOfOvc=hivUnknownRiskAssessed+hivNegativeRiskAssessed;
        /*int activeAndServed=getNoOfActiveOvcAssessedForHIVRiskAndServed(additionalQuery, startDate,endDate,hivStatus);
        int withdrawnAndServed=getNoOfOvcAssessedForHIVRiskServedAndWithdrawn(additionalQuery, startDate,endDate,hivStatus);
        numberOfOvc=activeAndServed+withdrawnAndServed;*/
        
        
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return numberOfOvc;
}
public List getListOfOvcAssessedForHIVRiskAndServed(String additionalQuery,String startDate,String endDate,String hivStatus,boolean atRisk,boolean currentlyEnrolled) throws Exception
{
    List ovcList=new ArrayList();
    try
    {
        
        String baseQuery=util.getHouseholdOvcHivRiskAssessmentServiceQueryPart()+util.getChildHivRiskAssessmentHivStatusQueryPart(hivStatus);
        //util.getHouseholdOvcHivRiskAssessmentHivStatusServiceQueryPart()+util.getActiveHivStatusCriteria(hivStatus, NomisConstant.OVC_TYPE);
        if(hivStatus==null || hivStatus.equalsIgnoreCase("All"))
        baseQuery=util.getHouseholdOvcHivRiskAssessmentServiceQueryPart();
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query=baseQuery+additionalQuery+currentlyEnrolledQuery+util.getHivRiskAssessmentPeriodQueryPart(startDate,endDate)+util.getOvcServiceDateQuery(startDate, endDate);
        System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            List idList=new ArrayList();
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                Ovc ovc=(Ovc)objArray[1];
                if(!idList.contains(ovc.getOvcId()))
                {
                    idList.add(ovc.getOvcId());
                    ovcList.add(ovc);
                }
            }
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return ovcList;
}
public int getNoOfHivUnknownOvcAssessedForHIVRiskAndServed(String additionalQuery,String startDate,String endDate,boolean atRisk,boolean currentlyEnrolled) throws Exception
{
    int numberOfOvc=0;
    try
    {
        int activeAndServed=getNoOfActiveOvcAssessedForHIVRiskAndServed(additionalQuery, startDate,endDate,NomisConstant.HIV_UNKNOWN);
        int withdrawnAndServed=getNoOfOvcAssessedForHIVRiskServedAndWithdrawn(additionalQuery, startDate,endDate,NomisConstant.HIV_UNKNOWN);
        numberOfOvc=activeAndServed+withdrawnAndServed;      
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return numberOfOvc;
}
public int getNoOfHivNegativeOvcAssessedForHIVRiskAndServed(String additionalQuery,String startDate,String endDate,boolean atRisk,boolean currentlyEnrolled) throws Exception
{
    int numberOfOvc=0;
    try
    {
        int activeAndServed=getNoOfActiveOvcAssessedForHIVRiskAndServed(additionalQuery, startDate,endDate,NomisConstant.HIV_NEGATIVE);
        int withdrawnAndServed=getNoOfOvcAssessedForHIVRiskServedAndWithdrawn(additionalQuery, startDate,endDate,NomisConstant.HIV_NEGATIVE);
        numberOfOvc=activeAndServed+withdrawnAndServed;
        
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return numberOfOvc;
}
/*public List getListOfOvcAssessedForHIVRiskAndServedByHivStatus(String additionalQuery,String startDate,String endDate,String hivStatus,boolean atRisk,boolean currentlyEnrolled) throws Exception
{
    List ovcList=new ArrayList();
    try
    {
        
        String baseQuery=util.getHouseholdOvcHivRiskAssessmentServiceQueryPart()+util.getChildHivRiskAssessmentHivStatusQueryPart(hivStatus);
        //util.getHouseholdOvcHivRiskAssessmentHivStatusServiceQueryPart()+util.getActiveHivStatusCriteria(hivStatus, NomisConstant.OVC_TYPE);
        if(hivStatus==null || hivStatus.equalsIgnoreCase("All"))
        baseQuery=util.getHouseholdOvcHivRiskAssessmentServiceQueryPart();
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query=baseQuery+additionalQuery+currentlyEnrolledQuery+util.getHivRiskAssessmentPeriodQueryPart(startDate,endDate)+util.getOvcServiceDateQuery(startDate, endDate);
        System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            List idList=new ArrayList();
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                Ovc ovc=(Ovc)objArray[1];
                if(!idList.contains(ovc.getOvcId()))
                {
                    idList.add(ovc.getOvcId());
                    ovcList.add(ovc);
                }
            }
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return ovcList;
}*/
public List getHivRiskAssessmentList(String additionalQuery,String startDate,String endDate,boolean atRisk,boolean currentlyEnrolled) throws Exception
{
    List list=new ArrayList();
    try
    {
        String atRiskQuery=util.getChildAtRiskQuery(atRisk);
        String currentlyEnrolledQuery=" ";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        String query=util.getHouseholdOvcHivRiskAssessmentQueryPart()+additionalQuery+currentlyEnrolledQuery+util.getHivRiskAssessmentPeriodQueryPart(startDate,endDate)+atRiskQuery;
        System.err.println(query);
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
        
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return list;
}
public List getListOfOvcThatHasBeenAssessedOnHivRiskAssessment(String additionalQuery,String startDate,String endDate,boolean atRisk,boolean currentlyEnrolled) throws Exception
{
    List listOfOvc=new ArrayList();
    try
    {
        List list=getHivRiskAssessmentList(additionalQuery,startDate,endDate,atRisk,currentlyEnrolled);
        if(list !=null)
        {
            List ovcIdList=new ArrayList();
            Ovc ovc=null;
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                ovc=(Ovc)objArray[1];
                if(!ovcIdList.contains(ovc.getOvcId()))
                {
                    listOfOvc.add(ovc);
                    ovcIdList.add(ovc.getOvcId());
                }
            }
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return listOfOvc;
}
public int getNumberOfOvcWhoseCaregiversKnowTheirHivStatus(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery,String hivStatusAnswer) throws Exception
{
    int numberOfAdolescents=0;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count (distinct hrac.ovcId) "+util.getHouseholdOvcHivRiskAssessmentQueryPart()+additionalQuery+currentlyEnrolledQuery+util.getCaregiverKnowHivStatusQueryPart(hivStatusAnswer);
        System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            numberOfAdolescents=Integer.parseInt(list.get(0).toString());
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return numberOfAdolescents;
}
public List getListOfOvcWhoseCaregiversKnowTheirHivStatus(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery,String hivStatusAnswer) throws Exception
{
    List listOfOvc=new ArrayList();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query=util.getHouseholdOvcHivRiskAssessmentQueryPart()+additionalQuery+currentlyEnrolledQuery+util.getCaregiverKnowHivStatusQueryPart(hivStatusAnswer);
        System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null)
        {
            List ovcIdList=new ArrayList();
            Ovc ovc=null;
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                ovc=(Ovc)objArray[1];
                if(!ovcIdList.contains(ovc.getOvcId()))
                {
                    listOfOvc.add(ovc);
                    ovcIdList.add(ovc.getOvcId());
                }
            }
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return listOfOvc;
}
public int getNumberOfOvcByHivStatusFromHivRiskAssessment(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery,String hivStatus) throws Exception
{
    int numberOfOvc=0;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count (distinct hrac.ovcId)"+util.getHouseholdOvcHivRiskAssessmentQueryPart()+additionalQuery+currentlyEnrolledQuery+util.getChildHivRiskAssessmentHivStatusQueryPart(hivStatus);
        System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            numberOfOvc=Integer.parseInt(list.get(0).toString());
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return numberOfOvc;
}
public List getListOfOvcByHivStatusFromHivRiskAssessment(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery,String hivStatus) throws Exception
{
    List listOfOvc=new ArrayList();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query=util.getHouseholdOvcHivRiskAssessmentQueryPart()+additionalQuery+currentlyEnrolledQuery+util.getChildHivRiskAssessmentHivStatusQueryPart(hivStatus);
        System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null)
        {
            List ovcIdList=new ArrayList();
            Ovc ovc=null;
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                ovc=(Ovc)objArray[1];
                if(!ovcIdList.contains(ovc.getOvcId()))
                {
                    listOfOvc.add(ovc);
                    ovcIdList.add(ovc.getOvcId());
                }
            }
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return listOfOvc;
}
public int getNumberOfOvcNeverAssessedForHivRiskAssessment(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery) throws Exception
{
    int numberOfAdolescents=0;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select count (distinct ovc.ovcId)"+util.getHouseholdOvcQueryPart()+additionalQuery+currentlyEnrolledQuery+" and ovc.ovcId not in (select distinct hrac.ovcId from HivRiskAssessmentChecklist hrac)";
        System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            numberOfAdolescents=Integer.parseInt(list.get(0).toString());
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return numberOfAdolescents;
}
public List getListOfOvcNeverAssessedForHivRiskAssessment(String additionalQuery,String startDate,String endDate,String currentlyEnrolledQuery) throws Exception
{
    List listOfOvc=new ArrayList();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query=util.getHouseholdOvcQueryPart()+additionalQuery+currentlyEnrolledQuery+" and ovc.ovcId not in (select distinct hrac.ovcId from HivRiskAssessmentChecklist hrac)";
        System.err.println(query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        if(list !=null)
        {
            List ovcIdList=new ArrayList();
            Ovc ovc=null;
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                ovc=(Ovc)objArray[1];
                if(!ovcIdList.contains(ovc.getOvcId()))
                {
                    listOfOvc.add(ovc);
                    ovcIdList.add(ovc.getOvcId());
                }
            }
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }   
    return listOfOvc;
}
    public List getHivRiskAssessmentChecklist(String additionalQuery) throws Exception
    {
        List hracList=new ArrayList();
        
        try 
        {
            //System.err.println(util.getHouseholdOvcHivRiskAssessmentQueryPart()+additionalQuery);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery(util.getHouseholdOvcHivRiskAssessmentQueryPart()+additionalQuery).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    hracList.add(objArray[2]);
                }
            }
         }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
        
        return hracList;
    }
    public void saveHivRiskAssessmentChecklist(HivRiskAssessmentChecklist hrac) throws Exception
    {
        if(hrac !=null)
        {
            try
            {
                HivRiskAssessmentChecklist duplicateHrac=getHivRiskAssessmentChecklist(hrac.getOvcId(), hrac.getDateOfAssessment());
                if(duplicateHrac ==null)
                {
                    hrac=getHivRiskAssessmentChecklistWithCleanedHivStatus(hrac);
                    session = HibernateUtil.getSession();
                    tx=session.beginTransaction();
                    session.save(hrac);
                    tx.commit();
                    closeSession(session); 
                    setHivStatusOfUnknownOvcNotAtRiskToTestNotIndicated(hrac);
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    public void updateHivRiskAssessmentChecklist(HivRiskAssessmentChecklist hrac) throws Exception
    {
        if(hrac !=null)
        {
            try
            {
                HivRiskAssessmentChecklist duplicateHrac=getHivRiskAssessmentChecklist(hrac.getOvcId(), hrac.getDateOfAssessment());
                if(duplicateHrac !=null)
                {
                    hrac=getHivRiskAssessmentChecklistWithCleanedHivStatus(hrac);
                    hrac.setRecordId(duplicateHrac.getRecordId());
                    session = HibernateUtil.getSession();
                    tx=session.beginTransaction();
                    session.update(hrac);
                    tx.commit();
                    closeSession(session);
                    setHivStatusOfUnknownOvcNotAtRiskToTestNotIndicated(hrac);
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    public void markedForDelete(HivRiskAssessmentChecklist hrac) throws Exception
    {
        try
        {
            hrac.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateHivRiskAssessmentChecklist(hrac);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteHivRiskAssessmentChecklist(HivRiskAssessmentChecklist hrac) throws Exception
    {
        if(hrac !=null)
        {
            try
            {
                HivRiskAssessmentChecklist duplicateHrac=getHivRiskAssessmentChecklist(hrac.getOvcId(), hrac.getDateOfAssessment());
                if(duplicateHrac !=null)
                {
                    session = HibernateUtil.getSession();
                    tx=session.beginTransaction();
                    session.delete(duplicateHrac);
                    tx.commit();
                    closeSession(session); 
                    util.saveDeletedRecord(hrac.getOvcId(),null, "hivriskassessment", hrac.getDateOfAssessment());
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    public HivRiskAssessmentChecklist getHivRiskAssessmentChecklist(int recordId) throws Exception
    {
        HivRiskAssessmentChecklist hrac=null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from HivRiskAssessmentChecklist hrac where hrac.recordId=:id").setInteger("id", recordId).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                hrac=(HivRiskAssessmentChecklist)list.get(0);
            }
         }
         catch(Exception ex)
         {
            closeSession(session);
            ex.printStackTrace();
         }
        
        return hrac;
    }
    public HivRiskAssessmentChecklist getHivRiskAssessmentChecklist(String ovcId,String dateOfAssessment) throws Exception
    {
        HivRiskAssessmentChecklist hrac=null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from HivRiskAssessmentChecklist hrac where hrac.ovcId=:id and hrac.dateOfAssessment=:dt").setString("id", ovcId).setString("dt", dateOfAssessment).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                hrac=(HivRiskAssessmentChecklist)list.get(0);
            }
         }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
        
        return hrac;
    }
    public List getHivRiskAssessmentChecklistByOvcId(String ovcId) throws Exception
    {
        List list=null;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HivRiskAssessmentChecklist hrac where hrac.ovcId=:id").setString("id", ovcId).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
             ex.printStackTrace();
         }
        
        return list;
    }
    public void changeOvcId(String oldId,String newId) throws Exception
    {
        //AppUtility appUtil=new AppUtility();
        List list=getHivRiskAssessmentChecklistByOvcId(oldId);
        if(list !=null)
        {
            for(int i=0; i<list.size(); i++)
            {
                HivRiskAssessmentChecklist hrac=(HivRiskAssessmentChecklist)list.get(i);
                hrac.setOvcId(newId);
                hrac.setLastModifiedDate(appUtil.getCurrentDate());
                updateHivRiskAssessmentChecklist(hrac);
                util.saveDeletedRecord(oldId, null,"hivriskassessment", hrac.getDateOfAssessment());
                //deleteHivRiskAssessmentChecklist(hrac);   
            }
        }
    }
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
}
