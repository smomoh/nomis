/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.kidmap.business.FollowupSurvey;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.report.IndicatorDictionary;
import com.fhi.nomis.logs.NomisLogManager;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


/**
 *
 * @author HP USER
 */
public class ChildStatusIndexDaoImpl implements ChildStatusIndexDao {

    private ChildStatusIndex childStatusIndex = null;
    private ChildStatusIndex[] childStatusIndexObjects = null;
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
    int ovcCount=0;
public ChildStatusIndex getBaselineChildStatusIndex(String ovcId) throws Exception
{
    ChildStatusIndex csi=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        List list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc, ChildStatusIndex csi where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId and csi.csiDate=ovc.dateEnrollment and csi.ovcId=:uid").setString("uid", ovcId).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            for(Object obj:list)
            {
                Object[] objArray=(Object[])obj;
                csi=(ChildStatusIndex)objArray[2];
            }
        }
    }
    catch(Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    
    return csi;
}
public List getNumberOfOvcSickWithLimitedAccessToHealthCareAtBaseline(String stateCode,String startDate,String endDate) throws Exception
{
    String additionalQuery=util.getCsiDateQuery(startDate, endDate);
    List mainList=new ArrayList();
    IndicatorDictionary ind=new IndicatorDictionary();
    mainList.add(ind.getIndicatorForNumberOfOvcSickWithLimitedAccessToHealthCare().getIndicatorName());
    List list = new ArrayList();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(csi.csiDate),YEAR(csi.csiDate),count(distinct csi.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc, ChildStatusIndex csi where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId and hhe.stateCode=:state and csi.csiFactor6=3 and csi.surveyNumber=1"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(csi.csiDate),YEAR(csi.csiDate) ").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch(Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    mainList.addAll(list);
    return mainList;
}
public List getNumberOfOvcSickWithNoAccessToHealthCareAtBaseline(String stateCode,String startDate,String endDate) throws Exception
{
    String additionalQuery=util.getCsiDateQuery(startDate, endDate);
    List list = new ArrayList();
    IndicatorDictionary ind=new IndicatorDictionary();
    List mainList=new ArrayList();
    mainList.add(ind.getIndicatorForNumberOfOvcSickWithNoAccessToHealthCare().getIndicatorName());
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(csi.csiDate),YEAR(csi.csiDate),count(distinct csi.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc, ChildStatusIndex csi where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId and hhe.stateCode=:state and csi.csiFactor6=1  and csi.surveyNumber=1"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(csi.csiDate),YEAR(csi.csiDate) ").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch(Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    mainList.addAll(list);
    return mainList;
}
public List getNumberOfOvcWhoAreMostVulnerable(String indicatorName,String stateCode,String startDate,String endDate,int surveyNumber) throws Exception
{
    List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        IndicatorDictionary ind =new IndicatorDictionary();
        if(indicatorName==null)
        indicatorName=ind.getIndicatorForNumberOfOvcWhoAreMostVulnerableAtBaseline().getIndicatorName();
        mainList.add(indicatorName);
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc,ChildStatusIndex csi where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId) and hhe.stateCode=:state and csi.totalCsiScore <13 and csi.surveyNumber="+surveyNumber+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        mainList.addAll(list);
        return mainList;
}
public List getNumberOfOvcWhoAreMoreVulnerable(String indicatorName,String stateCode,String startDate,String endDate,int surveyNumber) throws Exception
{
    List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        IndicatorDictionary ind =new IndicatorDictionary();
        if(indicatorName==null)
        indicatorName=ind.getIndicatorForNumberOfOvcWhoAreMoreVulnerableAtBaseline().getIndicatorName();
        mainList.add(indicatorName);
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc,ChildStatusIndex csi where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId) and hhe.stateCode=:state and (csi.totalCsiScore between 13 and 24 and csi.surveyNumber="+surveyNumber+")"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        mainList.addAll(list);
        return mainList;
}
public List getNumberOfOvcWhoAreVulnerable(String indicatorName,String stateCode,String startDate,String endDate,int surveyNumber) throws Exception
{
    List list=null;
        List mainList=new ArrayList();
        String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
        IndicatorDictionary ind =new IndicatorDictionary();
        if(indicatorName==null)
        indicatorName=ind.getIndicatorForNumberOfOvcWhoAreVulnerableAtBaseline().getIndicatorName();
        mainList.add(indicatorName);
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct ovc.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc,ChildStatusIndex csi where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId) and hhe.stateCode=:state and (csi.totalCsiScore >24 and csi.surveyNumber="+surveyNumber+")"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)").setString("state", stateCode).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        mainList.addAll(list);
        return mainList;
}
public List getTotalCsiScoreForAllOvc(String stateCode) throws Exception
{
    String additionalQuery=" ";
    if(stateCode !=null)
        additionalQuery=" and hhe.stateCode='"+stateCode+"' ";
    List list = new ArrayList();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select hhe.stateCode, hhe.lgaCode,hhe.orgCode,hhe.communityCode,csi.totalCsiScore,csi.surveyNumber,count(distinct csi.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc, ChildStatusIndex csi where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId"+additionalQuery+" group by hhe.stateCode, hhe.lgaCode,hhe.orgCode,hhe.communityCode,csi.totalCsiScore,csi.surveyNumber ").list();
        tx.commit();
        session.close();
    }
    catch(Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return list;
}   
public void updateCsiDateOfEntry() throws Exception
{
    AppUtility appUtil=new AppUtility();
    List list = new ArrayList();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from ChildStatusIndex as csi where csi.dateOfEntry is null").list();
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
        ChildStatusIndex csi=null;
        for(Object obj:list)
        {
            csi=(ChildStatusIndex)obj;
            csi.setDateOfEntry(appUtil.getCurrentDate());
            updateChildStatusIndex(csi);
            //System.err.println("csi with ovcid "+csi.getOvcId()+" and id "+csi.getId()+" and dateofentry "+csi.getDateOfEntry()+" updated");
        }
    }
}
public List getChildStatusIndexRecordForDownload(String stateCode,String startDate,String endDate) throws Exception
{
    HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
   List mainList = new ArrayList();
    String additionalQuery=null;
    List communityList=hhedao.getDistinctCommunityCodesPerState(stateCode);
    //List ovcIdList=getDistinctOvcIdsFromCsiByAdditionalQuery(additionalQuery);
    if(communityList !=null)
    {
        String communityCode=null;
        for(int i=0; i<communityList.size(); i++)
        {
            if(communityList.get(i) !=null)
            communityCode=communityList.get(i).toString();
            else
            communityCode=(String)communityList.get(i);
            additionalQuery=" and hhe.communityCode='"+communityCode+"' and csi.csiDate >='"+startDate+"'";// and '"+endDate+"'";
            //additionalQuery=" and hhe.communityCode='"+communityCode+"' and csi.csiDate between '"+startDate+"' and '"+endDate+"'";
            //System.err.println(additionalQuery+" "+i);
            mainList.add(getChildStatusIndexRecords(additionalQuery));
        }
    }
    return mainList;
}
/*public List getChildStatusIndexRecordForDownload(String stateCode,String startDate,String endDate) throws Exception
{
   List mainList = new ArrayList();
    String additionalQuery=" and hhe.stateCode='"+stateCode+"' and csi.csiDate between '"+startDate+"' and '"+endDate+"'";
    List ovcIdList=getDistinctOvcIdsFromCsiByAdditionalQuery(additionalQuery);
    if(ovcIdList !=null)
    {
        for(int i=0; i<ovcIdList.size(); i++)
        {
            System.err.println(additionalQuery+" and csi.ovcId='"+ovcIdList.get(i).toString()+"' "+i);
            mainList.add(getChildStatusIndexRecords(additionalQuery+" and csi.ovcId='"+ovcIdList.get(i).toString()+"'"));
        }
    }
    return mainList;
}*/
public List getChildStatusIndexRecords(String additionalQuery) throws Exception
{
    List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query=util.getHouseholdQueryPart()+"Ovc ovc,ChildStatusIndex csi where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId "+additionalQuery+" order by csi.ovcId,csi.csiDate asc";
            //System.err.println(query);
            list = session.createQuery(query).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            NomisLogManager.logStackTrace(ex);
            //ex.printStackTrace();
        }
        return list;
}
    public int getTotalCsiScore(ChildStatusIndex childStatusIndex) throws Exception
    {
        int totalCsiScore=0;
        if(childStatusIndex !=null)
        {
            try
            {
                totalCsiScore+=childStatusIndex.getCsiFactor1();
                totalCsiScore+=childStatusIndex.getCsiFactor2();
                totalCsiScore+=childStatusIndex.getCsiFactor3();
                totalCsiScore+=childStatusIndex.getCsiFactor4();
                totalCsiScore+=childStatusIndex.getCsiFactor5();
                totalCsiScore+=childStatusIndex.getCsiFactor6();
                totalCsiScore+=childStatusIndex.getCsiFactor7();
                totalCsiScore+=childStatusIndex.getCsiFactor8();
                totalCsiScore+=childStatusIndex.getCsiFactor9();
                totalCsiScore+=childStatusIndex.getCsiFactor10();
                totalCsiScore+=childStatusIndex.getCsiFactor11();
                totalCsiScore+=childStatusIndex.getCsiFactor12();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return totalCsiScore;
    }
    public void changeOvcId(String oldId,String newId) throws Exception
    {
        AppUtility appUtil=new AppUtility();
        List list = new ArrayList();
        if(oldId==null || newId==null)
        return;
        oldId=oldId.trim();
        newId=newId.trim();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ChildStatusIndex as csi where csi.ovcId like'%"+oldId+"%'").list();
            tx.commit();
            session.close();
        
         
            if(list !=null && !list.isEmpty())
            {
                //System.err.println("list size in changecsi is "+list.size());
                ChildStatusIndex csi=null;
                for(int i=0; i<list.size(); i++)
                {
                    
                    /*This is the csi record with the old ovc id*/
                    csi=(ChildStatusIndex)list.get(i);
                    //System.err.println("csi.getOvcId() at "+i+" is "+csi.getOvcId());
                    /*check if a csi record with the new id already exist*/
                    if(getChildStatusIndex(newId, csi.getCsiDate())==null)
                    {
                        csi.setDateOfEntry(appUtil.getCurrentDate());
                        /*It does not exist, so just update this csi with new id and save*/
                        csi.setOvcId(newId);
                        //System.err.println("about to update "+csi.getOvcId());
                        //saveChildStatusIndex(csi);
                        updateChildStatusIndexForChangeCsi(csi);
                        //register the old id as deleted so it can be removed on other computer
                        util.saveDeletedRecord(oldId,null, "csi",csi.getCsiDate());
                        //System.err.println("csi.getOvcId() "+csi.getOvcId()+" updated");
                    }
                    else
                    {
                        /*Delete the csi record with the old id because another with the new id already exist*/
                        //System.err.println("about to delete "+csi.getOvcId());
                        deleteChildStatusIndex(csi);
                        //System.err.println("csi.getOvcId() "+csi.getOvcId()+" deleted");
                    }
                }
            }
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public int getNoOfCsi(String ovcId) throws Exception
    {
        List list = new ArrayList();
        int count=0;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select count(csi.ovcId) from ChildStatusIndex as csi where csi.ovcId=:ovc_id").setString("ovc_id", ovcId).list();
        tx.commit();
        session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            session.close();
        }
        if(list !=null && !list.isEmpty())
            count=(Integer.parseInt(list.get(0).toString()));
        return count;
    }
    public List getCSIByCommunityCode(String communityCode) throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc,ChildStatusIndex csi where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId and hhe.communityCode=:ccode").setString("ccode", communityCode).list();
            tx.commit();
            session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
        return list;
    }
    public List getCsiForExport() throws Exception {
        List list = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from ChildStatusIndex as csi").list();
        tx.commit();
        session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            session.close();
        }

         return list;
    }
    public List getCsiForExport(String startDate,String endDate,String additionalOrgQuery) throws Exception {
        List list = new ArrayList();
        List csiList = new ArrayList();
        String householdQueryPart=util.getHouseholdQueryPart();
        String sql=householdQueryPart+"ChildStatusIndex as csi, Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId and csi.ovcId=ovc.ovcId) and csi.dateOfEntry between '"+ startDate+"' and '"+endDate+"' "+additionalOrgQuery;
        if(startDate.equalsIgnoreCase("All") || endDate.equalsIgnoreCase("All"))
        sql=householdQueryPart+"ChildStatusIndex as csi, Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId and csi.ovcId=ovc.ovcId) "+additionalOrgQuery;
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery(sql).list();
        tx.commit();
        session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            session.close();
        }
        if(list !=null)
        {
            ChildStatusIndex csi=null;
            for(int i=0; i<list.size(); i++)
            {
                Object[] obj=(Object[])list.get(i);
                csi=(ChildStatusIndex)obj[1];
                csiList.add(csi);
            }
        }
         return csiList;
    }
    public List getDistinctOvcIdsFromCsiByAdditionalQuery(String additionalQuery) throws Exception
    {
        List list = new ArrayList();
         try 
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //System.err.println("select distinct csi.ovcId "+util.getHouseholdQueryPart()+" Ovc ovc, ChildStatusIndex csi where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId "+additionalQuery);
            list = session.createQuery("select distinct csi.ovcId "+util.getHouseholdQueryPart()+" Ovc ovc, ChildStatusIndex csi where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId "+additionalQuery).list();
            tx.commit();
            session.close();
        } catch (HibernateException he) 
        {
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
    public List getDistinctOvcIdsFromCsi() throws Exception
    {
        List list = new ArrayList();
         try 
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct csi.ovcId from ChildStatusIndex csi").list();
            tx.commit();
            session.close();
        } 
         catch (HibernateException he) 
        {
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
    public List getDistinctOvcIdsFromCsi(String orgCode) throws Exception
    {
        List list = new ArrayList();
        //List csiList = new ArrayList();
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //System.err.println("select distinct csi.ovcId "+util.getHouseholdQueryPart()+" Ovc ovc, ChildStatusIndex csi where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId and hhe.orgCode=:oc");
            list = session.createQuery("select distinct csi.ovcId "+util.getHouseholdQueryPart()+" Ovc ovc, ChildStatusIndex csi where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId and hhe.orgCode=:oc").setString("oc", orgCode).list();
            tx.commit();
            session.close();
        
        } catch (HibernateException he)
        {
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
    public List getDistinctOvcIdsFromCsiByCommunityCode(String communityCode) throws Exception
    {
        List list = new ArrayList();
        //List csiList = new ArrayList();
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //System.err.println("select distinct csi.ovcId "+util.getHouseholdQueryPart()+" Ovc ovc, ChildStatusIndex csi where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId and hhe.communityCode='"+communityCode+"'");
            list = session.createQuery("select distinct csi.ovcId "+util.getHouseholdQueryPart()+" Ovc ovc, ChildStatusIndex csi where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId and hhe.communityCode=:cc").setString("cc", communityCode).list();
            tx.commit();
            session.close();
        
        } 
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
        return list;
    }
    public List getCsiListOrderedByDateAsc(String ovcId) throws Exception 
    {
        List list = new ArrayList();
         try 
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ChildStatusIndex as csi where csi.ovcId = :id order by csi.csiDate").setString("id", ovcId).list();
            tx.commit();
            session.close();
        } 
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public List getCsiListOrderedByDateDesc(String ovcId) throws Exception 
    {
        List list = new ArrayList();
         try 
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ChildStatusIndex as csi where csi.ovcId = :id order by csi.csiDate desc").setString("id", ovcId).list();
            tx.commit();
            session.close();
        } 
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        return list;
    }
    public ChildStatusIndex getMostRecentCsiScore(String ovcId) throws Exception 
    {
        ChildStatusIndex csi=null;
         try 
         {
            List csiList=getCsiListOrderedByDateDesc(ovcId);
            if(csiList !=null && !csiList.isEmpty())
            {
                int count=csiList.size()-1;
                for(int i=0; i<csiList.size(); i++)
                {
                    csi=(ChildStatusIndex)csiList.get(i);
                    //System.err.println("---------"+count+" "+ovcId+" "+csi.getCsiDate());
                   if(i<count)
                   {
                       FollowupSurvey fu=util.getFollowupDaoInstance().getFollowup(ovcId, csi.getCsiDate());
                       if(fu !=null)
                       {
                           //System.err.println("Ovc "+ovcId+" followed up on "+csi.getCsiDate()+" "+i);
                            break;
                       }
                   }
                }
                
            }
        } 
         
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return csi;
    }
    public List getSurveyNumbers() throws Exception {
        List list = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select distinct csi.surveyNumber from ChildStatusIndex as csi").list();
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
    public List getCsiWithSurveyNo(int surveyNo) throws Exception {
        List list = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from ChildStatusIndex as csi where csi.surveyNumber = :id").setInteger("id", surveyNo).list();
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
    public List getDistinctOvcIdAndCsiDateAsList(String ordCode) throws Exception
    {
        List list = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select distinct csi.ovcId, csi.csiDate "+util.getHouseholdQueryPart()+" Ovc ovc, ChildStatusIndex csi where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId and hhe.orgCode =:oc").setString("oc", ordCode).list();
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
    public List getCsiAsList(String ovcId, String csiDate) throws Exception {
        List list = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from ChildStatusIndex as csi where csi.ovcId = :id and csi.csiDate = :dt")
                           .setString("id", ovcId)
                           .setString("dt", csiDate)
                           .list();
        tx.commit();
        session.close();
        } catch (HibernateException he) 
        {
            session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
    public ChildStatusIndex[] getChildStatusIndex(String ovcId) throws Exception
    {
        return childStatusIndexObjects;
    }
    public ChildStatusIndex getChildStatusIndex(String ovcId, String csiDate) throws Exception {
        List list = new ArrayList();
        ChildStatusIndex childStatusIndex=null;
         try 
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ChildStatusIndex as csi where csi.ovcId =:id and csi.csiDate =:dt").setString("id", ovcId).setString("dt", csiDate).list();
            tx.commit();
            session.close();
        } 
        catch(Exception ex)
        {
            if(session !=null)
            session.close();
            ex.printStackTrace();
        }
         if(list !=null && list.size()>0) 
         {
           childStatusIndex = (ChildStatusIndex)list.get(0);
         }
        return childStatusIndex;
    }
    public List getChildStatusIndexListByOvcIdAndDate(String ovcId, String csiDate) throws Exception
    {
        List list = new ArrayList();
        ChildStatusIndex childStatusIndex=null;
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from ChildStatusIndex as csi where csi.ovcId = :id and csi.csiDate = :dt")
                           .setString("id", ovcId)
                           .setString("dt", csiDate)
                           .list();
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            session.close();
        }

        return list;
    }
    public ChildStatusIndex getChildStatusIndex(int id) throws Exception
    {
        List list = new ArrayList();
        ChildStatusIndex csi=null;
         try 
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ChildStatusIndex as csi where csi.id =:uid").setInteger("uid", id).list();              
            tx.commit();
            session.close();
        } 
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
        if(list !=null && !list.isEmpty())
        {
            csi=(ChildStatusIndex)list.get(0);
        }
        return csi;
    }
    public void saveChildStatusIndex(ChildStatusIndex childStatusIndex) throws Exception {
        try 
        {
            //System.err.println("inside saveChildStatusIndex: OvcId is "+childStatusIndex.getOvcId());
            ChildStatusIndex duplicateChildStatusIndex = getChildStatusIndex(childStatusIndex.getOvcId(),childStatusIndex.getCsiDate());
            if(duplicateChildStatusIndex==null)
            {
                saveChildStatusIndexOnly(childStatusIndex);
                
            }
        }
        catch(Exception ex)
        {
            if(session !=null)
            session.close();
            ex.printStackTrace();
        }

    }
    public int saveChildStatusIndexOnly(ChildStatusIndex childStatusIndex) throws Exception 
    {
        int num=0;
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            childStatusIndex.setTotalCsiScore(getTotalCsiScore(childStatusIndex));
            session.save(childStatusIndex);
            tx.commit();
            session.close();
            num=1;
            //System.err.println("inside saveChildStatusIndexOnly "+childStatusIndex.getOvcId()+" saved");            
        }
        catch(Exception ex)
        {
            num=0;
            if(session !=null)
            session.close();
            ex.printStackTrace();
        }
        return num;
    }
    public void saveChildStatusIndexAndReorderAssessmentNumber(ChildStatusIndex childStatusIndex) throws Exception 
    {
        try 
        {
            saveChildStatusIndex(childStatusIndex);
            reorderAssessmentNumber(childStatusIndex.getOvcId());
        }
        catch(Exception ex)
        {
            if(session !=null)
            session.close();
            ex.printStackTrace();
        }

    }
    
    public void updateChildStatusIndexSurveyNumber(ChildStatusIndex csi) throws Exception
    {
        if(getChildStatusIndex(csi.getId()) !=null)
        {
            try
            {
                csi.setTotalCsiScore(getTotalCsiScore(csi));
                session = HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.update(csi);
                tx.commit();
                session.close();
                System.err.println("survey number in updateChildStatusIndexSurveyNumber is "+csi.getSurveyNumber());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    public void updateChildStatusIndexForChangeCsi(ChildStatusIndex childStatusIndex) throws Exception
    {
        try
        {
            System.err.println("Inside updateChildStatusIndexForChangeCsi");
            if(childStatusIndex !=null)
            {
                if(getChildStatusIndex(childStatusIndex.getId()) !=null)
                {
                    updateChildStatusIndexOnly(childStatusIndex);
                    //reorderAssessmentNumber(childStatusIndex.getOvcId());
                }
            }
        }
        catch(Exception ex)
        {
            //session.close();
            ex.printStackTrace();
        }
     }
    public int saveOrUpdateChildStatusIndex(ChildStatusIndex childStatusIndex) throws Exception
     {
        int num=0;
        try
        {
            if(childStatusIndex !=null)
            {
                ChildStatusIndex childStatusIndex2=getChildStatusIndex(childStatusIndex.getOvcId(), childStatusIndex.getCsiDate());
                
                if(childStatusIndex2 !=null)
                {
                   childStatusIndex.setTotalCsiScore(getTotalCsiScore(childStatusIndex));
                   AppUtility appUtil=new AppUtility();
                   if(DateManager.compareDates(childStatusIndex2.getDateOfEntry(), childStatusIndex.getDateOfEntry())) 
                   {
                        childStatusIndex.setId(childStatusIndex2.getId());
                        num=updateChildStatusIndexOnly(childStatusIndex);
                   }
                }
                else
                {
                    num=saveChildStatusIndexOnly(childStatusIndex);
                }
            }
        }
        catch(Exception ex)
        {
            if(session !=null)
            session.close();
            ex.printStackTrace();
        }
        return num;
     }
     public void updateChildStatusIndex(ChildStatusIndex childStatusIndex) throws Exception
     {
        try
        {
            if(childStatusIndex !=null)
            {
                ChildStatusIndex childStatusIndex2=getChildStatusIndex(childStatusIndex.getOvcId(), childStatusIndex.getCsiDate());
                
                if(childStatusIndex2 !=null)
                {
                   System.err.println("childStatusIndex2.getDateOfEntry() is "+childStatusIndex2.getDateOfEntry()+" and childStatusIndex.getDateOfEntry() is "+childStatusIndex.getDateOfEntry());
                   if(DateManager.compareDates(childStatusIndex2.getDateOfEntry(), childStatusIndex.getDateOfEntry())) 
                   {
                        childStatusIndex.setId(childStatusIndex2.getId());
                        updateChildStatusIndexOnly(childStatusIndex);
                   }
                    //reorderAssessmentNumber(childStatusIndex.getOvcId());
                }
                else
                {
                    saveChildStatusIndex(childStatusIndex);
                }
            }
        }
        catch(Exception ex)
        {
            if(session !=null)
            session.close();
            ex.printStackTrace();
        }
     }
     public int updateChildStatusIndexOnly(ChildStatusIndex childStatusIndex) throws Exception
     {
         int num=0;
        try
        {
            if(childStatusIndex !=null)
            {
                childStatusIndex.setTotalCsiScore(getTotalCsiScore(childStatusIndex));
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(childStatusIndex);
                tx.commit();
                session.close();
                num=2;
            }
        }
        catch(Exception ex)
        {
            if(session !=null)
            session.close();
            ex.printStackTrace();
        }
        return num;
     }
     public void updateChildStatusIndexAndReorderAssessmentNumber(ChildStatusIndex childStatusIndex) throws Exception
     {
        try
        {
            updateChildStatusIndex(childStatusIndex);
            reorderAssessmentNumber(childStatusIndex.getOvcId());
        }
        catch(Exception ex)
        {
            //session.close();
            ex.printStackTrace();
        }
     }
    public void markedForDelete(ChildStatusIndex childStatusIndex) throws Exception
    {
        try
        {
            childStatusIndex.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateChildStatusIndex(childStatusIndex);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
     public void deleteChildStatusIndex(String ovcId, String csiDate) throws Exception
     {
         ChildStatusIndex childStatusIndex=getChildStatusIndex(ovcId,csiDate);
         if(childStatusIndex !=null)
         deleteChildStatusIndex(childStatusIndex);
     }
     public void deleteChildStatusIndex(ChildStatusIndex childStatusIndex) throws Exception
     {
         //List list=new ArrayList();
        try
        {
            if(childStatusIndex !=null && getChildStatusIndex(childStatusIndex.getId()) !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(childStatusIndex);
                tx.commit();
                session.close();
                reorderAssessmentNumber(childStatusIndex.getOvcId());
                util.saveDeletedRecord(childStatusIndex.getOvcId(), null,"csi", childStatusIndex.getCsiDate());
            }
        } 
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
     }
     public void deleteChildStatusIndexWithoutReordering(ChildStatusIndex childStatusIndex) throws Exception
     {
         //List list=new ArrayList();
        try
        {
            if(childStatusIndex !=null && getChildStatusIndex(childStatusIndex.getId()) !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(childStatusIndex);
                tx.commit();
                session.close();
                util.saveDeletedRecord(childStatusIndex.getOvcId(), null,"csi", childStatusIndex.getCsiDate());
            }
        } 
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
     }
     public void deleteAllCsiRecordsPerOvc(String ovcId) throws Exception
     {
         List list=getCsiListOrderedByDateAsc(ovcId);
        try
        {
            for(Object obj:list)
            {
                ChildStatusIndex csi=(ChildStatusIndex)obj;
                deleteChildStatusIndex(csi);
            }
        } catch (HibernateException he) 
        {
            session.close();
            //throw new Exception(he);
        }
         catch(Exception ex)
        {
            session.close();
        }
     }
     /*public void reorderAssessmentNumber(List list) throws Exception
     {
         List csiList=null;
         if(list !=null)
         {
             System.err.println("list.size() is "+list.size());
             for(int i=0; i<list.size(); i++)
             {
                 csiList=getCSIRecordsByovcIdFromCSI((String)list.get(i));
                 saveCSIWithReorderedAssessmentNumber(csiList);
                 System.err.println("Record number "+i+" processed");
             }
         }
     }*/
    public void reorderAssessmentNumber(String ovcId) throws Exception
    {
        try
        {
        int assessmentNo=1;
        List list=getCSIRecordsByovcIdFromCSI(ovcId);
        if(list !=null)
        {
            ChildStatusIndex csi=null;
            for(int i=0; i<list.size(); i++)
            {
                csi=(ChildStatusIndex)list.get(i);
                csi.setSurveyNumber(assessmentNo);
                updateChildStatusIndexSurveyNumber(csi);
                assessmentNo++;
                ovcCount++;
                //System.err.println("CSI "+i+" with uniqueId "+csi.getOvcId()+", survey number "+csi.getSurveyNumber()+" and csi date "+csi.getCsiDate()+" and count "+ovcCount+" reordered");
            }
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
     
     public List getCSIRecordsByovcIdFromCSI(String ovcId) throws Exception
     {
         List list = new ArrayList();
        childStatusIndex=null;
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from ChildStatusIndex as csi where csi.ovcId=:id order by csi.csiDate asc").setString("id", ovcId).list();
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            //throw new Exception(he);
        }
        catch(Exception ex)
        {
            session.close();
        }
         return list;
     }
     
     public List getCsiIndicatorCount(int factorIndex,String[] param) throws Exception
     {
         List<Integer> size=new ArrayList<Integer>();
          DaoUtil util=new DaoUtil();
          String[] dateParams={param[4],param[5],param[6],param[7]};
          String dateSubQuery=" and csi.csiDate ";
         String dateQuery=util.getQueryPeriod(dateParams, dateSubQuery);
         int csiNo=Integer.parseInt(param[14]);
         List list=new ArrayList();
        
         String additionalQuery=util.getQueryCriteria(param);
         String sql=null;
         for(int i=1; i<5; i++)
         {
            sql=util.getHouseholdQueryPart()+"ChildStatusIndex as csi, Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId) and csi.csiFactor"+factorIndex+"="+i+" and csi.surveyNumber="+csiNo+additionalQuery+dateQuery;
            list=executeQuery(sql);
            size.add(list.size());
            //System.err.println("sql in CSI INDICATOR COUNT IS "+sql);
         }
         return size;
     }
     public List getCsiOvcList(String[] param,int factorIndex,int csiScore) throws Exception
     {
         List list=new ArrayList();
         List ovcList=new ArrayList();
         String sql="";
         String[] params={param[4],param[5],param[6],param[7]};
         int csiNo=Integer.parseInt(param[14]);
         DaoUtil util=new DaoUtil();
         String additionalQuery=util.getQueryCriteria(param);
         String dateSubQuery=" and csi.csiDate ";
         String dateQuery=util.getQueryPeriod(params, dateSubQuery);
         //sql=util.getHouseholdQueryPart()+"ChildStatusIndex as csi, Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId)"+additionalQuery+" and (csi.csiFactor"+factorIndex+"="+csiScore+" and csi.surveyNumber="+csiNo+dateQuery+")";
         sql=util.getHouseholdQueryPart()+"ChildStatusIndex as csi, Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId)"+additionalQuery+" and (csi.csiFactor"+factorIndex+"="+csiScore+" and csi.surveyNumber="+csiNo+dateQuery+")";
         //System.err.println("sql in getCsiOvcList IS "+sql);
         list=executeQuery(sql);
         if(list !=null && !list.isEmpty())
         {
             for(Object s:list)
             {
                Object[] obj=(Object[])s;

                ovcList.add((Ovc)obj[2]);
             }
         }
         return ovcList;
     }
     private List executeQuery(String sql) throws Exception
     {
         List list=new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery(sql).list();
        tx.commit();
        session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            //throw new Exception(he);
        }
         catch(Exception ex)
        {
            session.close();
        }
         return list;
     }
}
