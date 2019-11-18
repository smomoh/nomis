/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.nomis.nomisutils.AppUtility;
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

public class OvcWithdrawalDaoImpl implements OvcWithdrawalDao {

    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
public List getListOfBeneficiariesByReasonWithdrawn(String reasonWithdrawal) throws Exception
{
    List list = new ArrayList();
    if(reasonWithdrawal !=null)
    reasonWithdrawal=reasonWithdrawal.toUpperCase();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from OvcWithdrawal withdrawal where UPPER(withdrawal.reasonWithdrawal) ='"+reasonWithdrawal+"'").list();
        tx.commit();
        session.close();
    } 
    catch (Exception ex)
    {
        ex.printStackTrace();
    }
    return list;
}
public List getListOfBeneficiariesByTypeAndReasonWithdrawn(String reasonWithdrawal,String type) throws Exception
{
    List list = new ArrayList();
    if(reasonWithdrawal !=null)
    reasonWithdrawal=reasonWithdrawal.toUpperCase();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from OvcWithdrawal withdrawal where UPPER(withdrawal.reasonWithdrawal) ='"+reasonWithdrawal+"' and withdrawal.type='"+type+"'").list();
        tx.commit();
        session.close();
    } 
    catch (Exception ex)
    {
        ex.printStackTrace();
    }
    return list;
}
public int resetReasonWithdrawalForHouseholds() throws Exception
{
    List list = new ArrayList();
    int count=0;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from OvcWithdrawal withdrawal where withdrawal.reasonWithdrawal ='"+NomisConstant.DIED+"' and withdrawal.type ='"+NomisConstant.HOUSEHOLD_TYPE+"'").list();
        tx.commit();
        session.close();
    } catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    if(list !=null && !list.isEmpty())
    {
        //Ovc ovc=null;
        OvcWithdrawal withdrawal=null;
        for(int i=0; i<list.size(); i++)
        {
            withdrawal=(OvcWithdrawal)list.get(i);
            withdrawal.setType("caregiver");
            //withdrawal.setReasonWithdrawal("Loss to follow-up");
            updateOvcWithdrawal(withdrawal);
            count++;
        }
        
    }
    return count;
}
public int resetAgeAbove18ToGraduation() throws Exception
{
    List list = new ArrayList();
    int count=0;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from Ovc ovc, OvcWithdrawal withdrawal where ovc.ovcId=withdrawal.ovcId and (withdrawal.reasonWithdrawal ='ageabove17' or withdrawal.reasonWithdrawal ='ageabove18' or UPPER(withdrawal.reasonWithdrawal) like '%AGE >%') and ovc.currentAge <18 ").list();
        tx.commit();
        session.close();
    } catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    if(list !=null && !list.isEmpty())
    {
        Ovc ovc=null;
        OvcWithdrawal withdrawal=null;
        for(int i=0; i<list.size(); i++)
        {
            Object[] obj=(Object[])list.get(i);
            ovc=(Ovc)obj[0];
            if(util.getCurrentAge(ovc) < 18)
            {
                withdrawal=(OvcWithdrawal)obj[1];
                withdrawal.setReasonWithdrawal(NomisConstant.GRADUATED);
                updateOvcWithdrawal(withdrawal);
                count++;
            }
        }
        
    }
    resetReasonWithdrawalForHouseholds();
    return count;
}
public int resetHouseholdAgeAbove17ToGraduation() throws Exception
{
    List list = new ArrayList();
    int count=0;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from HouseholdEnrollment hhe, OvcWithdrawal withdrawal where hhe.hhUniqueId=withdrawal.ovcId and (withdrawal.reasonWithdrawal ='ageabove17' or withdrawal.reasonWithdrawal ='ageabove18')").list();
        tx.commit();
        session.close();
    } 
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    if(list !=null && !list.isEmpty())
    {
        Ovc ovc=null;
        OvcWithdrawal withdrawal=null;
        for(int i=0; i<list.size(); i++)
        {
            Object[] obj=(Object[])list.get(i);
            
                withdrawal=(OvcWithdrawal)obj[1];
                withdrawal.setReasonWithdrawal(NomisConstant.GRADUATED);
                updateOvcWithdrawal(withdrawal);
                count++;
        }
        
    }
    //resetReasonWithdrawalForHouseholds();
    return count;
}
public int resetGraduationValue() throws Exception
{
    List list = new ArrayList();
    int count=0;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from OvcWithdrawal withdrawal where withdrawal.reasonWithdrawal ='Graduated from program' or withdrawal.reasonWithdrawal ='Graduated' or withdrawal.reasonWithdrawal is null or withdrawal.reasonWithdrawal ='' or withdrawal.reasonWithdrawal =' ' or withdrawal.reasonWithdrawal ='  ' or withdrawal.reasonWithdrawal ='   ' or withdrawal.reasonWithdrawal ='    '").list();
        tx.commit();
        session.close();
    } catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    if(list !=null && !list.isEmpty())
    {
        OvcWithdrawal withdrawal=null;
        for(int i=0; i<list.size(); i++)
        {
            withdrawal=(OvcWithdrawal)list.get(i);
            withdrawal.setReasonWithdrawal(NomisConstant.GRADUATED);
            updateOvcWithdrawal(withdrawal);
            count++;
        }
    }
    return count;
}
public int resetAgeAbove18Value() throws Exception
{
    List list = new ArrayList();
    int count=0;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from OvcWithdrawal withdrawal where (withdrawal.reasonWithdrawal ='Age > 18' or withdrawal.reasonWithdrawal ='Age &gt; 18' or withdrawal.reasonWithdrawal ='ageabove18') ").list();
        tx.commit();
        session.close();
    } catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    if(list !=null && !list.isEmpty())
    {
        
        OvcWithdrawal withdrawal=null;
        for(int i=0; i<list.size(); i++)
        {
            withdrawal=(OvcWithdrawal)list.get(i);
            withdrawal.setReasonWithdrawal(NomisConstant.AGED_OUT);
            updateOvcWithdrawal(withdrawal);
            count++;
        }
    }
    return count;
}
public List getListOfWithrawalWithWrongType() throws Exception
{
    List list = new ArrayList();
    
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from OvcWithdrawal withdrawal where ((withdrawal.type !='ovc' and withdrawal.type !='household' and withdrawal.type !='caregiver') or withdrawal.type is null)").list();
        tx.commit();
        session.close();
    } catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
         return list;
}
public OvcWithdrawal cleanupWithdrawal(OvcWithdrawal withdrawal) throws Exception
{
    try
    {
        if(withdrawal !=null)
        {
            
        }
    }
    catch(Exception ex)
    {
        
    }
    return withdrawal;
}

public int getNumberOfOvcWithdrawnPerCohort(String additionalQueryCriteria,String reasonWithdrawn) throws Exception
{
    int numberWithdrawn=0;
    List list = new ArrayList();
    String reasonWithrawnQuery=" ";
    if(reasonWithdrawn!=null)
    {
        reasonWithdrawn=reasonWithdrawn.toUpperCase();
        
        reasonWithrawnQuery=" and UPPER(withdrawal.reasonWithdrawal)='"+reasonWithdrawn+"'";
    }
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count(distinct withdrawal.ovcId)"+ util.getHouseholdQueryPart()+" Ovc ovc, OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=withdrawal.ovcId and withdrawal.type='ovc' "+additionalQueryCriteria+reasonWithrawnQuery).list();
            tx.commit();
            session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
         if(list !=null && !list.isEmpty())
        {
            numberWithdrawn=Integer.parseInt(list.get(0).toString());
        }
        return numberWithdrawn;
}
public List getListOfOvcWithdrawnPerCohort(String additionalQueryCriteria,String reasonWithrawn) throws Exception
{
    List list = new ArrayList();
    List listOfOvc = new ArrayList();
    String reasonWithrawnQuery=" ";
    if(reasonWithrawn!=null)
    {
        reasonWithrawn=reasonWithrawn.toUpperCase();
        reasonWithrawnQuery=" and UPPER(withdrawal.reasonWithdrawal)='"+reasonWithrawn+"'";
    }
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query=util.getHouseholdQueryPart()+" Ovc ovc, OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=withdrawal.ovcId and withdrawal.type='ovc' "+additionalQueryCriteria+reasonWithrawnQuery;
            //System.err.println("query in getListOfOvcWithdrawnPerCohort is "+query);
            list = session.createQuery(query).list();
            tx.commit();
            session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
         if(list !=null && !list.isEmpty())
        {
            for(Object obj: list)
            {
                Object[] objArray=(Object[])obj;
                listOfOvc.add(objArray[1]);
            }
        }
        return listOfOvc;
}
public int getNumberOfHouseholdsWithdrawnPerCohort(String additionalQueryCriteria,String reasonWithrawn) throws Exception
{
    int numberWithdrawn=0;
    List list = new ArrayList();
    String reasonWithrawnQuery=" ";
    if(reasonWithrawn!=null)
    {
        reasonWithrawn=reasonWithrawn.toUpperCase();
        reasonWithrawnQuery=" and UPPER(withdrawal.reasonWithdrawal)='"+reasonWithrawn+"'";
    }
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();//and withdrawal.type='household'
           //System.err.println("query in ovcwithdrawaldao is "+"select count (distinct withdrawal.ovcId) "+util.getHouseholdQueryPart()+" OvcWithdrawal withdrawal where (hhe.hhUniqueId=withdrawal.ovcId and withdrawal.type='household') "+additionalQueryCriteria+reasonWithrawnQuery);
            list = session.createQuery("select count (distinct withdrawal.ovcId) "+util.getHouseholdQueryPart()+" OvcWithdrawal withdrawal where (hhe.hhUniqueId=withdrawal.ovcId) "+additionalQueryCriteria+reasonWithrawnQuery).list();
            tx.commit();
            session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
         if(list !=null && !list.isEmpty())
        {
            numberWithdrawn=Integer.parseInt(list.get(0).toString());
        }
        return numberWithdrawn;
}
public List getListOfHouseholdsWithdrawnPerCohort(String additionalQueryCriteria,String reasonWithrawn) throws Exception
{
    List list = new ArrayList();
    List listOfHouseholds = new ArrayList();
    String reasonWithrawnQuery=" ";
    if(reasonWithrawn!=null)
    {
        reasonWithrawn=reasonWithrawn.toUpperCase();
        reasonWithrawnQuery=" and UPPER(withdrawal.reasonWithdrawal)='"+reasonWithrawn+"'";
    }
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //System.err.println("query in ovcwithdrawaldao is "+util.getHouseholdQueryPart()+" Ovc ovc, OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=withdrawal.ovcId "+additionalQueryCriteria);
            list = session.createQuery(util.getHouseholdQueryPart()+" OvcWithdrawal withdrawal where (hhe.hhUniqueId=withdrawal.ovcId and withdrawal.type='household') "+additionalQueryCriteria+reasonWithrawnQuery).list();
            tx.commit();
            session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        if(list !=null && !list.isEmpty())
        {
            for(Object obj: list)
            {
                Object[] objArray=(Object[])obj;
                listOfHouseholds.add(objArray[0]);
            }
        }
        return listOfHouseholds;
}
public int getNumberOfCaregiversWithdrawnPerCohort(String additionalQueryCriteria,String reasonWithrawn) throws Exception
{
    int numberWithdrawn=0;
    List list = new ArrayList();
    String reasonWithrawnQuery=" ";
    if(reasonWithrawn!=null)
    {
        reasonWithrawn=reasonWithrawn.toUpperCase();
        reasonWithrawnQuery=" and UPPER(withdrawal.reasonWithdrawal)='"+reasonWithrawn+"'";
    }
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
           //System.err.println("query in ovcwithdrawaldao is "+"select count (distinct withdrawal.ovcId) "+util.getHouseholdQueryPart()+" OvcWithdrawal withdrawal where (hhe.hhUniqueId=withdrawal.ovcId and withdrawal.type='household') "+additionalQueryCriteria+reasonWithrawnQuery);
            list = session.createQuery("select count (distinct withdrawal.ovcId) "+util.getHouseholdQueryPart()+" Caregiver cgiver, OvcWithdrawal withdrawal where (hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=withdrawal.ovcId and withdrawal.type='caregiver') "+additionalQueryCriteria+reasonWithrawnQuery).list();
            tx.commit();
            session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
         if(list !=null && !list.isEmpty())
        {
            numberWithdrawn=Integer.parseInt(list.get(0).toString());
        }
        return numberWithdrawn;
}
public List getListOfCaregiversWithdrawnPerCohort(String additionalQueryCriteria,String reasonWithrawn) throws Exception
{
    List list = new ArrayList();
    List listOfCaregivers = new ArrayList();
    String reasonWithrawnQuery=" ";
    if(reasonWithrawn!=null)
    {
        reasonWithrawn=reasonWithrawn.toUpperCase();
        reasonWithrawnQuery=" and UPPER(withdrawal.reasonWithdrawal)='"+reasonWithrawn+"'";
    }
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //System.err.println("query in ovcwithdrawaldao is "+util.getHouseholdQueryPart()+" Ovc ovc, OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=withdrawal.ovcId "+additionalQueryCriteria);
            list = session.createQuery(util.getHouseholdQueryPart()+"Caregiver cgiver, OvcWithdrawal withdrawal where (hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=withdrawal.ovcId and withdrawal.type='caregiver') "+additionalQueryCriteria+reasonWithrawnQuery).list();
            tx.commit();
            session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        if(list !=null && !list.isEmpty())
        {
            for(Object obj: list)
            {
                Object[] objArray=(Object[])obj;
                listOfCaregivers.add(objArray[1]);
            }
        }
        return listOfCaregivers;
}
public List getDistinctWithdrawalTypes() throws Exception
{
    List list = new ArrayList();
        
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct withdrawal.type from OvcWithdrawal withdrawal ").list();
            tx.commit();
            session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
         return list;
}
public List getDistinctReasonForWithdrwal() throws Exception
{
    List list = new ArrayList();
        
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct withdrawal.reasonWithdrawal from OvcWithdrawal withdrawal ").list();
            tx.commit();
            session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
         return list;
}
public List getListOfOvcWithdrawn(String additionalQueryCriteria) throws Exception
{
    List list = new ArrayList();
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            System.err.println("query in ovcwithdrawaldao is "+util.getHouseholdQueryPart()+" Ovc ovc, OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=withdrawal.ovcId "+additionalQueryCriteria);
            list = session.createQuery(util.getHouseholdQueryPart()+" Ovc ovc, OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=withdrawal.ovcId "+additionalQueryCriteria).list();
            tx.commit();
            session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
}
public List getListOfOvcWithdrawnForExport(String additionalQueryCriteria) throws Exception
{
    List list = new ArrayList();
    List ovcWithdrawalList = new ArrayList();
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //System.err.println("query in ovcwithdrawaldao is "+util.getHouseholdQueryPart()+" Ovc ovc, OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=withdrawal.ovcId "+additionalQueryCriteria);
            list = session.createQuery(util.getHouseholdQueryPart()+" Ovc ovc, OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=withdrawal.ovcId "+additionalQueryCriteria).list();
            tx.commit();
            session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
    if(list !=null)
    {
        for(Object obj:list)
        {
            Object[] objArray=(Object[])obj;
            ovcWithdrawalList.add(objArray[2]);
        }
    }

        return ovcWithdrawalList;
}
public List getListOfHouseholdsWithdrawnForExport(String additionalQueryCriteria) throws Exception
{
    List list = new ArrayList();
    List ovcWithdrawalList = new ArrayList();
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //System.err.println("query in ovcwithdrawaldao is "+util.getHouseholdQueryPart()+" Ovc ovc, OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=withdrawal.ovcId "+additionalQueryCriteria);
            list = session.createQuery(util.getHouseholdQueryPart()+" OvcWithdrawal withdrawal where hhe.hhUniqueId=withdrawal.ovcId "+additionalQueryCriteria).list();
            tx.commit();
            session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
    if(list !=null)
    {
        for(Object obj:list)
        {
            Object[] objArray=(Object[])obj;
            ovcWithdrawalList.add(objArray[1]);
        }
    }

        return ovcWithdrawalList;
}
public List getListOfCaregiversWithdrawnForExport(String additionalQueryCriteria) throws Exception
{
    List list = new ArrayList();
    List ovcWithdrawalList = new ArrayList();
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //System.err.println("query in ovcwithdrawaldao is "+util.getHouseholdQueryPart()+" Ovc ovc, OvcWithdrawal withdrawal where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=withdrawal.ovcId "+additionalQueryCriteria);
            list = session.createQuery(util.getHouseholdQueryPart()+"Caregiver cgiver, OvcWithdrawal withdrawal where hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=withdrawal.ovcId "+additionalQueryCriteria).list();
            tx.commit();
            session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
    if(list !=null)
    {
        for(Object obj:list)
        {
            Object[] objArray=(Object[])obj;
            ovcWithdrawalList.add(objArray[2]);
        }
    }

        return ovcWithdrawalList;
}
public void changeOvcId(String oldId,String newId) throws Exception
{
    if(oldId.equalsIgnoreCase(newId))
    return;
    AppUtility appUtil=new AppUtility();
    List list=new ArrayList();
    try
    {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from OvcWithdrawal withdrawal where withdrawal.ovcId = :id").setString("id", oldId).list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
    if(list !=null && !list.isEmpty())
    {
        OvcWithdrawal withdrawal=null;
        for(Object obj:list)
        {
            withdrawal=(OvcWithdrawal)obj;
            //withdrawal.set
            withdrawal.setOvcId(newId);
            saveOvcWithdrawal(withdrawal);
            withdrawal.setOvcId(oldId);
            deleteOvcWithdrawal(withdrawal);
        }
    }
}
public void saveOvcWithdrawal(OvcWithdrawal ovcWithdrawal) throws Exception {
  try 
  {
      if(ovcWithdrawal !=null && ovcWithdrawal.getOvcId() !=null)
      {
            ovcWithdrawal=getOvcWithdrawalWithCorrectWithrawalType(ovcWithdrawal);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(ovcWithdrawal);
            tx.commit();
            session.close();
            if(ovcWithdrawal.getType().equalsIgnoreCase(NomisConstant.HOUSEHOLD_TYPE))
            withdrawHouseholdMembers(ovcWithdrawal);
      }
  } 
  catch (Exception ex) 
  {
    session.close();
    ex.printStackTrace();
  }
}
public void updateOvcWithdrawal(OvcWithdrawal ovcWithdrawal) throws Exception {
  try {
          if(ovcWithdrawal !=null && ovcWithdrawal.getOvcId() !=null)
          {
            ovcWithdrawal=getOvcWithdrawalWithCorrectWithrawalType(ovcWithdrawal);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.update(ovcWithdrawal);
            tx.commit();
            session.close();

            if(ovcWithdrawal.getType().equalsIgnoreCase(NomisConstant.HOUSEHOLD_TYPE))
            withdrawHouseholdMembers(ovcWithdrawal);
          }
        } 
    catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
}
public OvcWithdrawal getOvcWithdrawalWithCorrectWithrawalType(OvcWithdrawal ovcWithdrawal)
{
    try
    {
        if(ovcWithdrawal !=null && ovcWithdrawal.getOvcId() !=null)
        {
            if(ovcWithdrawal.getOvcId().trim().length()==NomisConstant.HHUNIQUEID_LENGTH)
            {
                ovcWithdrawal.setType(NomisConstant.HOUSEHOLD_TYPE);
                if(ovcWithdrawal.getReasonWithdrawal() !=null && ovcWithdrawal.getReasonWithdrawal().equalsIgnoreCase(NomisConstant.AGED_OUT))
                ovcWithdrawal.setReasonWithdrawal(NomisConstant.GRADUATED);
            }
            else if(ovcWithdrawal.getOvcId().trim().length()==NomisConstant.CAREGIVERID_LENGTH)
            {
                ovcWithdrawal.setType(NomisConstant.Caregiver_TYPE);
                if(ovcWithdrawal.getReasonWithdrawal() !=null && ovcWithdrawal.getReasonWithdrawal().equalsIgnoreCase(NomisConstant.AGED_OUT))
                ovcWithdrawal.setReasonWithdrawal(NomisConstant.GRADUATED);
            }
            else if(ovcWithdrawal.getOvcId().trim().length()==NomisConstant.OVCID_LENGTH)
            {
                ovcWithdrawal.setType(NomisConstant.OVC_TYPE);
            }
        }
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
    return ovcWithdrawal;
}
public List getWithdrawalList(List paramList) throws Exception
{
    List list = new ArrayList();
    List withdrawalList = new ArrayList();
        String state=(String)paramList.get(0);
        String lga=(String)paramList.get(1);
        String cbo=(String)paramList.get(2);
        String ward=(String)paramList.get(3);
        int serviceMth=(Integer)paramList.get(4);
        int serviceYear=(Integer)paramList.get(5);
        int serviceMth2=(Integer)paramList.get(6);
        int serviceYear2=(Integer)paramList.get(7);
        String partnerCode=(String)paramList.get(9);
        String type=(String)paramList.get(10);
        String reasonWithdrawn=(String)paramList.get(11);
        
        List dateList=new ArrayList();
        dateList.add(serviceMth);
        dateList.add(serviceMth2);
        dateList.add(serviceYear);
        dateList.add(serviceYear2);
        String[] dateParams={serviceMth+"",serviceYear+"",serviceMth2+"",serviceYear2+""};
        String[] params={state,lga,cbo,ward,serviceMth+"",serviceYear+"",serviceMth2+"",serviceYear2+"",null,null,null,null,null,null,partnerCode};
        String additionalQuery=util.getWithdrawalQueryCriteria(params);
        String queryPart=" withdrawal.dateOfWithdrawal ";
        String dateQuery=util.getQueryPeriod(dateParams, queryPart);
        String reasonWithdrawnQuery="";
        if(reasonWithdrawn !=null && !reasonWithdrawn.equalsIgnoreCase("All"))
        {
            reasonWithdrawnQuery=" and UPPER(withdrawal.reasonWithdrawal)='"+reasonWithdrawn.toUpperCase()+"'"; 
        }
        additionalQuery=additionalQuery+reasonWithdrawnQuery;
  
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query=util.getHouseholdQueryPart()+"OvcWithdrawal withdrawal,Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=withdrawal.ovcId) "+additionalQuery+" and "+dateQuery+" and (withdrawal.type='"+type+"' or withdrawal.type is null) order by withdrawal.ovcId";
            if(type ==null || type.equalsIgnoreCase("All"))
            query=util.getHouseholdQueryPart()+"OvcWithdrawal withdrawal where (hhe.hhUniqueId=withdrawal.ovcId) "+additionalQuery+" and "+dateQuery+" order by withdrawal.ovcId";
            else if(type !=null && type.equalsIgnoreCase("household"))
            query=util.getHouseholdQueryPart()+"OvcWithdrawal withdrawal where (hhe.hhUniqueId=withdrawal.ovcId) "+additionalQuery+" and "+dateQuery+" and withdrawal.type='"+type+"' order by withdrawal.ovcId";
            else if(type !=null && type.equalsIgnoreCase("caregiver"))
            query=util.getHouseholdQueryPart()+" OvcWithdrawal withdrawal,Caregiver cgiver where (hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=withdrawal.ovcId) "+additionalQuery+" and "+dateQuery+" and withdrawal.type='"+type+"' order by withdrawal.ovcId";
            
            System.err.println("query in getWithdrawalList is "+query);
            list = session.createQuery(query).list();

            //list = session.createQuery(util.getHouseholdQueryPart()+"OvcWithdrawal withdrawal,Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=withdrawal.ovcId) "+additionalQuery+" and "+dateQuery+" order by withdrawal.ovcId").list();
        tx.commit();
        session.close();
        //System.err.println("list.size() in getWithdrawalList is "+list.size());
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
        for(int i=0; i<list.size(); i++)
        {
            Object[] obj=(Object[])list.get(i);
            withdrawalList.add((OvcWithdrawal)obj[1]);
        }
        return withdrawalList;
}
public List getWithdrawnOvc(String ovcId) throws Exception
{
    List list = new ArrayList();
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OvcWithdrawal withdrawal where withdrawal.ovcId = :id")
            .setString("id", ovcId).list();
        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
        return list;
}

    public OvcWithdrawal getOvcWithdrawal(String ovcId) throws Exception 
    {
        List list=null;
        OvcWithdrawal ovcWithdrawal=null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OvcWithdrawal withdrawal where withdrawal.ovcId = :id")
            .setString("id", ovcId).list();
        //ovcWithdrawal = (OvcWithdrawal) session.load(OvcWithdrawal.class, ovcId, LockMode.UPGRADE);
        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
        if(list !=null && !list.isEmpty())
        {
            ovcWithdrawal=(OvcWithdrawal)list.get(0);
        }
        return ovcWithdrawal;
    }
    public OvcWithdrawal getWithdrawal(String uniqueId) throws Exception 
    {
        OvcWithdrawal ovcWithdrawal=getOvcWithdrawal(uniqueId);
        if(ovcWithdrawal !=null && ovcWithdrawal.getReasonWithdrawal() !=null && ovcWithdrawal.getReasonWithdrawal().equalsIgnoreCase(NomisConstant.INACTIVE))
        ovcWithdrawal=null;
        return ovcWithdrawal;
        //return null;
    }
    public String getWithdrawalMessage(String uniqueId, boolean checkWithdrawalRecord) throws Exception 
    {
        String msg=null;
        if(checkWithdrawalRecord)
        {
            OvcWithdrawal ovcWithdrawal=getOvcWithdrawal(uniqueId);

            if(ovcWithdrawal !=null)
            {
                msg="This beneficiary has been withdrawn";
                String beneficiaryType=ovcWithdrawal.getType();
                if(beneficiaryType !=null)
                {
                    if(beneficiaryType.equalsIgnoreCase(NomisConstant.OVC_TYPE))
                    msg="This OVC has been withdrawn";
                    else if(beneficiaryType.equalsIgnoreCase(NomisConstant.Caregiver_TYPE))
                    msg="This Caregiver has been withdrawn";
                    else if(beneficiaryType.equalsIgnoreCase(NomisConstant.HOUSEHOLD_TYPE))
                    msg="This household has been withdrawn";
                }
            }
        }
       return msg;
    }

public void saveOrUpdateOvcWithdrawal(OvcWithdrawal ovcWithdrawal) throws Exception 
{
      try 
      {
          if(ovcWithdrawal !=null && ovcWithdrawal.getOvcId() !=null)
          {
            ovcWithdrawal=getOvcWithdrawalWithCorrectWithrawalType(ovcWithdrawal);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(ovcWithdrawal);
            tx.commit();
            session.close();
            if(ovcWithdrawal.getType().equalsIgnoreCase(NomisConstant.HOUSEHOLD_TYPE))
            withdrawHouseholdMembers(ovcWithdrawal);
          }
      } 
      catch (Exception ex) 
      {
        session.close();
        ex.printStackTrace();
      }
}
public void markedForDelete(OvcWithdrawal ovcWithdrawal) throws Exception
{
    try
    {
        ovcWithdrawal.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
        updateOvcWithdrawal(ovcWithdrawal);
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
}
    public void deleteOvcWithdrawal(OvcWithdrawal ovcWithdrawal) throws Exception 
    {
       try 
       {
            if(ovcWithdrawal !=null)
            {
                deleteOvcWithdrawal(ovcWithdrawal.getOvcId());
            }
       } 
       catch (Exception ex) 
       {
            ex.printStackTrace();
       }
   }
public void deleteOvcWithdrawal(String clientId) throws Exception 
{
   try 
   {
       OvcWithdrawal withdrawal=getOvcWithdrawal(clientId);
       if(withdrawal !=null)
       {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(withdrawal);
            tx.commit();
            session.close();
            updateClientWithdrawalStatus(withdrawal,"restored");
            util.saveDeletedRecord(withdrawal.getOvcId(), null, "ovcWithdrawal", withdrawal.getDateOfWithdrawal());
            removeHouseholdFromWithdrawalRecord(withdrawal.getOvcId(),withdrawal.getType());
       }
   } 
   catch (Exception ex) 
   {
        ex.printStackTrace();
   }
}
private void removeHouseholdFromWithdrawalRecord(String uniqueId,String clientType)
{
    if(clientType !=null)
     {
         try
         {
             if(clientType.equalsIgnoreCase("ovc"))
             {
               OvcDao ovcdao=new OvcDaoImpl();
               Ovc ovc=ovcdao.getOvc(uniqueId);
               if(ovc !=null)
               {
                   deleteOvcWithdrawal(ovc.getHhUniqueId());
               }
             }
             else if(clientType.equalsIgnoreCase("caregiver"))
             {
                 CaregiverDao cgiverdao=new CaregiverDaoImpl();
                 Caregiver cgiver=cgiverdao.getCaregiverByCaregiverId(uniqueId);
                 if(cgiver !=null)
                 {
                     deleteOvcWithdrawal(cgiver.getHhUniqueId());
                 }
             }
         }
         catch(Exception ex)
         {
             ex.printStackTrace();
         }
     }
}
public void withdrawClient(String uniqueId,String dateOfWithdrawal, String reasonForWithdrawal,String type,int surveyNo)
{
    OvcWithdrawal withdrawal=new OvcWithdrawal();
    withdrawal.setDateOfWithdrawal(dateOfWithdrawal);
    withdrawal.setOvcId(uniqueId);
    withdrawal.setReasonWithdrawal(reasonForWithdrawal);
    withdrawal.setType(type);
    withdrawal.setSurveyNumber(surveyNo);
    try
    {
        if(getOvcWithdrawal(uniqueId) ==null)
        {
            saveOvcWithdrawal(withdrawal);
            updateClientWithdrawalStatus(withdrawal,"withdrawn");
            System.err.println("Client with id "+uniqueId+" and type "+type+" withdrwawn");
        }
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
 }
 public void withdrawHousehold(String hhUniqueId,String dateOfWithdrawal,String reasonForWithdrawal,String type,int surveyNo) throws Exception
 {
     CaregiverDao cgiverdao=new CaregiverDaoImpl();
     OvcDao ovcdao=new OvcDaoImpl();
    withdrawClient(hhUniqueId,dateOfWithdrawal, reasonForWithdrawal,type,surveyNo);
    List caregiverList=cgiverdao.getListOfCaregiversFromSameHousehold(hhUniqueId);
    if(caregiverList !=null && !caregiverList.isEmpty())
    {
        for(int i=0; i<caregiverList.size(); i++)
        {
            Caregiver cgiver=(Caregiver)caregiverList.get(i);
            withdrawClient(cgiver.getCaregiverId(),dateOfWithdrawal, reasonForWithdrawal,"caregiver",surveyNo);
        }
    }
    List ovcList=ovcdao.getOvcListPerHousehold(hhUniqueId);
    for(int i=0; i<ovcList.size(); i++)
    {
        Ovc ovc=(Ovc)ovcList.get(i);
        withdrawClient(ovc.getOvcId(),dateOfWithdrawal, reasonForWithdrawal,"ovc",surveyNo);
    }
 }
 public void withdrawHouseholdMembers(OvcWithdrawal withdrawal) throws Exception
 {
     CaregiverDao cgiverdao=new CaregiverDaoImpl();
     OvcDao ovcdao=new OvcDaoImpl();
    
    List caregiverList=cgiverdao.getListOfCaregiversFromSameHousehold(withdrawal.getOvcId());
    if(caregiverList !=null && !caregiverList.isEmpty())
    {
        for(int i=0; i<caregiverList.size(); i++)
        {
            Caregiver cgiver=(Caregiver)caregiverList.get(i);
            withdrawClient(cgiver.getCaregiverId(),withdrawal.getDateOfWithdrawal(), withdrawal.getReasonWithdrawal(),NomisConstant.Caregiver_TYPE,withdrawal.getSurveyNumber());
        }
    }
    List ovcList=ovcdao.getOvcListPerHousehold(withdrawal.getOvcId());
    for(int i=0; i<ovcList.size(); i++)
    {
        Ovc ovc=(Ovc)ovcList.get(i);
        withdrawClient(ovc.getOvcId(),withdrawal.getDateOfWithdrawal(), withdrawal.getReasonWithdrawal(),NomisConstant.OVC_TYPE,withdrawal.getSurveyNumber());
    }
 }
public List getWithdrawalListByTypeOfWithdrawal(String typeOfWithdrawal) throws Exception
{
    List list = new ArrayList();
     try
     {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="from OvcWithdrawal withdrawal where withdrawal.type='"+typeOfWithdrawal+"' order by withdrawal.ovcId";       
        System.err.println("query in getWithdrawalList is "+query);
        list = session.createQuery(query).list();
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
public List getListOfIdsNotWithdrawn(List listOfIds) throws Exception
{
    List listOfIdsNotWithdrawn=new ArrayList();
     try
     {
        if(listOfIds !=null && !listOfIds.isEmpty())
        {
            String uniqueId=null;
            for(int i=0; i<listOfIds.size(); i++)
            {
                uniqueId=listOfIds.get(i).toString();
                if(getOvcWithdrawal(uniqueId)==null)
                listOfIdsNotWithdrawn.add(uniqueId);
            } 
        }
    } 
     catch (HibernateException he) 
     {
        session.close();
        throw new Exception(he);
    }
    return listOfIdsNotWithdrawn;
}
public void updateHouseholdsWithIncorrectType() throws Exception
{
     try
     {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="from OvcWithdrawal withdrawal where LENGTH(TRIM(withdrawal.ovcId))="+NomisConstant.HHUNIQUEID_LENGTH+" and withdrawal.type !='"+NomisConstant.HOUSEHOLD_TYPE+"' order by withdrawal.ovcId";       
        System.err.println("query in updateHouseholdsWithIncorrectType() is "+query);
        List list = session.createQuery(query).list();
        tx.commit();
        session.close();
        
        if(list !=null && !list.isEmpty())
        {
            for(Object obj:list)
            {
                OvcWithdrawal withdrawal=(OvcWithdrawal)obj;
                withdrawal.setType(NomisConstant.HOUSEHOLD_TYPE);
                updateOvcWithdrawal(withdrawal);
            }
        }
    } 
     catch (HibernateException he) 
     {
        session.close();
        throw new Exception(he);
    }
}
 public void withdrawHouseholdMembers() throws Exception
 {
     updateHouseholdsWithIncorrectType();
     CaregiverDao cgiverdao=new CaregiverDaoImpl();
     OvcDao ovcdao=new OvcDaoImpl();
     List list=getWithdrawalListByTypeOfWithdrawal(NomisConstant.HOUSEHOLD_TYPE);
     if(list !=null)
     {
         OvcWithdrawal withdrawal=null;
         String hhUniqueId=null;
         String dateOfWithdrawal=null;
         String reasonForWithdrawal=null;
         int surveyNo=0;
         for(int j=0; j<list.size(); j++)
         {
             withdrawal=(OvcWithdrawal)list.get(j);
             hhUniqueId=withdrawal.getOvcId();
             dateOfWithdrawal=withdrawal.getDateOfWithdrawal();
             reasonForWithdrawal=withdrawal.getReasonWithdrawal();
             surveyNo=withdrawal.getSurveyNumber();
            //withdrawClient(hhUniqueId,dateOfWithdrawal, reasonForWithdrawal,type,surveyNo);
            List caregiverList=cgiverdao.getListOfCaregiversFromSameHousehold(hhUniqueId);
            if(caregiverList !=null && !caregiverList.isEmpty())
            {
                for(int i=0; i<caregiverList.size(); i++)
                {
                    Caregiver cgiver=(Caregiver)caregiverList.get(i);
                    withdrawClient(cgiver.getCaregiverId(),dateOfWithdrawal, reasonForWithdrawal,"caregiver",surveyNo);
                }
            }
            List ovcList=ovcdao.getOvcListPerHousehold(hhUniqueId);
            for(int i=0; i<ovcList.size(); i++)
            {
                Ovc ovc=(Ovc)ovcList.get(i);
                withdrawClient(ovc.getOvcId(),dateOfWithdrawal, reasonForWithdrawal,"ovc",surveyNo);
            }
         }
     }
 }
 public void updateClientWithdrawalStatus(OvcWithdrawal withdrawal,String action) throws Exception
 {
     if(withdrawal !=null)
     {
         String withdrawn="Yes";
         if(action.equalsIgnoreCase("restored"))
         withdrawn="No";
         String uniqueId=withdrawal.getOvcId();
         String clientType=withdrawal.getType();
         if(clientType !=null)
         {
             if(clientType.equalsIgnoreCase("ovc"))
             {
               OvcDao ovcdao=new OvcDaoImpl();
               Ovc ovc=ovcdao.getOvc(uniqueId);
               if(ovc !=null)
               {
                   ovc.setWithdrawnFromProgram(withdrawn);
                   ovcdao.updateOvc(ovc,false,false);
               }
             }
             else if(clientType.equalsIgnoreCase("caregiver"))
             {
                 CaregiverDao cgiverdao=new CaregiverDaoImpl();
                 Caregiver cgiver=cgiverdao.getCaregiverByCaregiverId(uniqueId);
                 if(cgiver !=null)
                 {
                     cgiver.setWithdrawnFromProgram(withdrawn);
                     cgiverdao.updateCaregiver(cgiver);
                 }
             }
             else if(clientType.equalsIgnoreCase("household"))
             {
                 HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
                 HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(uniqueId);
                 if(hhe !=null)
                 {
                     hhe.setWithdrawnFromProgram(withdrawn);
                     hhedao.updateHouseholdEnrollment(hhe);
                 }
             }
         }
     }
 }
 
}
