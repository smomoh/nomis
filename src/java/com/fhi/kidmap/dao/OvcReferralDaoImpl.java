/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcReferral;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class OvcReferralDaoImpl implements OvcReferralDao
{
    Session session;
    Transaction tx;
    DaoUtil util=new DaoUtil();
public List getHTCReferralServiceRecordById(String ovcId) throws Exception
{
    OvcReferral ovcRef=null;
        
    List refList=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="from Ovc ovc, OvcReferral ovcRef where ovc.ovcId=ovcRef.ovcId and ovcRef.ovcId=:id and (UPPER(ovcRef.healthServices) like '%HIV%' or UPPER(ovcRef.healthServices) like '%HTC%') order by ovcRef.dateOfReferral desc";
        System.err.println(query);
        List list = session.createQuery(query).setString("id", ovcId).list();
        tx.commit();
        session.close();
        if(list !=null && !list.isEmpty())
        {
            for(Object obj:list)
            {
                Object[] objarray=(Object[])obj;
                ovcRef=(OvcReferral)objarray[1]; 
                refList.add(ovcRef);
            }
        }
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return refList;
}
public OvcReferral getHTCReferralServiceRecordById(String ovcId,String startDate) throws Exception
{
    OvcReferral ovcRef=null;
    String dateQuery="";
    if(startDate !=null && !startDate.equalsIgnoreCase("All"))
    dateQuery=" and ovcRef.dateOfReferral is not null and ovcRef.dateOfReferral >='"+startDate+"'";
    List list=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="from Ovc ovc, OvcReferral ovcRef where ovc.ovcId=ovcRef.ovcId and ovcRef.ovcId=:id and (UPPER(ovcRef.healthServices) like '%HIV%' or UPPER(ovcRef.healthServices) like '%HTC%')"+dateQuery+" order by ovcRef.dateOfReferral desc";
        System.err.println(query);
        list = session.createQuery(query).setString("id", ovcId).list();
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
        Object[] objarray=(Object[])list.get(0);
        ovcRef=(OvcReferral)objarray[1];    
    }
    return ovcRef;
}
    public String getReferralReportCriteria(String[] params) throws Exception
    {
        
        String stateCode=params[0],lgaCode=params[1],orgCode=params[2],wardCode=params[3],startMth=params[4],startYear=params[5],endMth=params[6],endYear=params[7];
        String queryCriteria=" ",orgUnit=" ",periodQuery="";
        try
        {
            String[] refParams={stateCode,lgaCode,orgCode,null,null,null,null,null,null,null,null,null,null,null,params[10]};
            queryCriteria=util.getQueryCriteria(refParams);
            
            if((startMth !=null && !startMth.equalsIgnoreCase("All")) && (endMth !=null && !endMth.equalsIgnoreCase("All")) &&(startYear !=null && !startYear.equalsIgnoreCase("All")) && (endYear !=null && !endYear.equalsIgnoreCase("All")))
            {
                String[] dateParams={startMth,startYear,endMth,endYear};
                periodQuery=" and ovcRef.dateOfReferral between '"+util.getStartDate(dateParams)+"' and '"+util.getEndDate(dateParams)+"'";
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return queryCriteria+periodQuery;
    }
    public void changeOvcId(String oldId,String newId) throws Exception
{
    AppUtility appUtil=new AppUtility();
    List list=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from OvcReferral ovcRef where ovcRef.ovcId =:id").setString("id", oldId).list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
    if(list !=null && !list.isEmpty())
    {
        OvcReferral ovcRef=null;
        for(Object obj:list)
        {
            ovcRef=(OvcReferral)obj;
            deleteOvcReferral(ovcRef);
            if(this.getOvcReferral(newId, ovcRef.getDateOfReferral())==null)
            {
                ovcRef.setOvcId(newId);
                saveOvcReferral(ovcRef);
            }
        }
    }
}
    public OvcReferral getOvcReferral(String ovcId,String refDate) throws Exception
    {
        List list=new ArrayList();
        OvcReferral ovcRef=null;
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            list = session.createQuery("from OvcReferral ovcRef where ovcRef.ovcId =:id and ovcRef.dateOfReferral=:date").setString("id",ovcId).setString("date", refDate).list();
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            session.close();
            throw new Exception(hbe);
        }
        if(list.size()>0)
        {
            ovcRef=(OvcReferral)list.get(0);
        }
        return ovcRef;
    }
    public List getVulnerableHouseholdReferralList(String[] params) throws Exception
    {
        List referralList=new ArrayList();
        referralList.addAll(getOvcReferralList(params));
        String[] hviParam={params[0],params[1],params[2],params[3],params[4],params[5],params[6],params[7],params[11]};
        referralList.addAll(getVulnerableHouseholdReferralListForHh(hviParam));
        return referralList;
    }
    public List getOvcReferralList(String[] params) throws Exception
    {
        String additionalQuery=getReferralReportCriteria(params);
        List list=new ArrayList();
        List referralList=new ArrayList();
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            list = session.createQuery(util.getHouseholdQueryPart()+"OvcReferral ovcRef,Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=ovcRef.ovcId) "+additionalQuery+" order by ovcRef.type,ovcRef.dateOfReferral").list();
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            session.close();
            throw new Exception(hbe);
        }

        if(list !=null)
        {
            OvcReferral ovcRef=null;
            Ovc ovc=null;
            for(Object s:list)
            {
               Object[] obj=(Object[])s;
               ovcRef=(OvcReferral)obj[1];
               ovc=(Ovc)obj[2];
               if(ovc==null)
               ovc=new Ovc();
               ovcRef.setAge(ovc.getAge());
               ovcRef.setSurname(ovc.getLastName());
               ovcRef.setFirstName(ovc.getFirstName());
               ovcRef.setGender(ovc.getGender());
               if(ovcRef.getType()==null)
               ovcRef.setType("VC");
               referralList.add(ovcRef);
            }
        }
             return referralList;
        }
        public List getVulnerableHouseholdReferralListForHh(String[] params) throws Exception
        {
            //String additionalQuery=getReferralReportCriteria(params);
            List list=new ArrayList();
            List referralList=new ArrayList();
            String startDate=null;
            String endDate=null;
            String[] hviParam={params[0],params[1],params[2],params[3],null,null,"All","All","All","All",params[8]};

            String additionalOrgQuery=util.getHVIReportCriteria(hviParam);
            String queryPart=util.getHouseholdQueryPart()+"OvcReferral ovcRef, Caregiver cgiver where (hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=ovcRef.ovcId) "+additionalOrgQuery;
            String sql=util.getHouseholdQueryPart()+"OvcReferral ovcRef, Caregiver cgiver where (hhe.hhUniqueId=cgiver.hhUniqueId and cgiver.caregiverId=ovcRef.ovcId) "+additionalOrgQuery+" order by ovcRef.type,ovcRef.dateOfReferral";

            if((params[4]!=null && !params[4].equalsIgnoreCase("All")) && (params[7]!=null && !params[7].equalsIgnoreCase("All")))
            {
                String[] dateParams={params[4],params[5],params[6],params[7]};
                startDate=util.getStartDate(dateParams);
                endDate=util.getEndDate(dateParams);
                sql=queryPart+" and ovcRef.dateOfReferral between '"+startDate+"' and '"+endDate+"' order by ovcRef.type,ovcRef.dateOfReferral ";
            }
            try
            {
                //System.err.println("sql is "+sql);
                session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                list = session.createQuery(sql).list();
                tx.commit();
                session.close();
            }
            catch(HibernateException hbe)
            {
            session.close();
            throw new Exception(hbe);
            }

            if(list !=null)
            {
                OvcReferral ovcRef=null;
                HouseholdEnrollment hhe=null;
                CaregiverDao cgiverdao=new CaregiverDaoImpl();
                Caregiver cgiver=null;
                 for(Object s:list)
                {
                    Object[] obj=(Object[])s;
                    hhe=(HouseholdEnrollment)obj[0];
                    ovcRef=(OvcReferral)obj[1];
                    cgiver=cgiverdao.getCaregiverByCaregiverId(ovcRef.getOvcId());
                    if(cgiver !=null)
                    {
                        ovcRef.setAge(cgiver.getCaregiverAge());
                        ovcRef.setSurname(cgiver.getCaregiverLastName());
                        ovcRef.setFirstName(cgiver.getCaregiverFirstname());
                    }
                    else
                    {
                        ovcRef.setAge(hhe.getHhAge());
                        ovcRef.setSurname(hhe.getHhSurname());
                        ovcRef.setFirstName(hhe.getHhFirstname());
                    }
                    //ovcRef.setGender(hhe.get);
                    if(ovcRef.getType()==null)
                    ovcRef.setType(NomisConstant.HOUSEHOLD_TYPE);
                    referralList.add(ovcRef);
                }
            }
        return referralList;
    }
    public List getOvcReferralList(String startDate, String endDate,String additionalOrgQuery) throws Exception
    {
        String sql=util.getHouseholdQueryPart()+"OvcReferral ovcRef,Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=ovcRef.ovcId) and ovcRef.dateOfReferral between '"+startDate+"' and '"+endDate+"' "+additionalOrgQuery+" order by ovcRef.ovcId ";
        if(startDate.equalsIgnoreCase("All") || endDate.equalsIgnoreCase("All"))
        sql=util.getHouseholdQueryPart()+"OvcReferral ovcRef,Ovc ovc where (hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=ovcRef.ovcId) "+additionalOrgQuery+" order by ovcRef.ovcId ";
        List list=new ArrayList();
        List referralList=new ArrayList();
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            list = session.createQuery(sql).list();
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            session.close();
            throw new Exception(hbe);
        }
        if(list !=null)
        {
            OvcReferral ovcRef=null;
            for(int i=0; i<list.size(); i++)
            {
                    Object[] obj=(Object[])list.get(i);
                    ovcRef=(OvcReferral)obj[1];
                    referralList.add(ovcRef);
            }
        }
        return referralList;
    }
    public List getOvcReferralListByOvcId(String ovcId) throws Exception
    {
        List list=new ArrayList();

        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            list = session.createQuery("from OvcReferral ovcRef where ovcRef.ovcId=:id").setString("id", ovcId).list();
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            session.close();
            throw new Exception(hbe);
        }
        return list;
    }
    public void saveOvcReferral(OvcReferral ovcRef) throws Exception
    {
        try
        {
            if(ovcRef !=null && ovcRef.getDateOfReferral() !=null)
            {
                session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.save(ovcRef);
                tx.commit();
                session.close();
            }
        }
        catch(HibernateException hbe)
        {
            session.close();
            throw new Exception(hbe);
        }
    }
    public void updateOvcReferral(OvcReferral ovcRef) throws Exception
    {
        try
        {
            if(ovcRef !=null && ovcRef.getDateOfReferral() !=null)
            {
                OvcReferral ovcRef2=this.getOvcReferral(ovcRef.getOvcId(), ovcRef.getDateOfReferral());
                if(ovcRef2 !=null)
                {
                    ovcRef.setRecordId(ovcRef2.getRecordId());
                    session=HibernateUtil.getSession();
                    tx=session.beginTransaction();
                    session.update(ovcRef);
                    tx.commit();
                    session.close(); 
                }
            }
        }
        catch(HibernateException hbe)
        {
            session.close();
            throw new Exception(hbe);
        }
    }
    public void markedForDelete(OvcReferral ovcRef) throws Exception
    {
        try
        {
            ovcRef.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateOvcReferral(ovcRef);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteOvcReferral(OvcReferral ovcRef) throws Exception
    {
        try
        {
            if(ovcRef !=null && ovcRef.getDateOfReferral() !=null)
            {
                OvcReferral ovcRef2=this.getOvcReferral(ovcRef.getOvcId(), ovcRef.getDateOfReferral());
                if(ovcRef2 !=null)
                {
                    session=HibernateUtil.getSession();
                    tx=session.beginTransaction();
                    session.delete(ovcRef);
                    tx.commit();
                    session.close();
                    util.saveDeletedRecord(ovcRef.getOvcId(), null,"ovcReferal", ovcRef.getDateOfReferral());
                }
            }
        }
        catch(HibernateException hbe)
        {
            session.close();
            throw new Exception(hbe);
        }
    }
    public void deleteOvcReferrals(String ovcId) throws Exception
    {
        List list=getOvcReferralListByOvcId(ovcId);
        if(list !=null)
        {
            for(int i=0; i<list.size(); i++)
            {
                OvcReferral ovcRef=(OvcReferral)list.get(i);
                deleteOvcReferral(ovcRef);
                
            }
        }
    }
public List getOvcIdsFromReferral() throws Exception
{
    List list=new ArrayList();
    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select distinct ovcRef.ovcId from OvcReferral ovcRef").list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
    return list;
}
}
