/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.HouseholdReferral;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Siaka
 */
public class HhReferralDaoImpl implements HhReferralDao
{
    Session session;
    Transaction tx;
    DaoUtil util=new DaoUtil();
    public List getHouseholdReferralList(String[] params) throws Exception
    {
        String startDate=null; 
        String endDate=null;
        String[] hviParam={params[0],params[1],params[2],null,null,null,"All","All","All","All","All"};
        String additionalOrgQuery=util.getHVIReportCriteria(hviParam);
        String sql="from HouseholdReferral hhr,HouseholdVulnerabilityIndex hvi where hhr.hhUniqueId=hvi.hhUniqueId "+additionalOrgQuery+" order by hhr.hhUniqueId ";
        if((params[6]!=null && !params[6].equalsIgnoreCase("All")) && (params[9]!=null && !params[9].equalsIgnoreCase("All")))
        {
            String[] dateParams={params[6],params[7],params[8],params[9]};
            startDate=util.getStartDate(dateParams);
            endDate=util.getEndDate(dateParams);
            sql="from HouseholdReferral hhr,HouseholdVulnerabilityIndex hvi where hhr.hhUniqueId=hvi.hhUniqueId and hhr.dateOfReferral between '"+startDate+"' and '"+endDate+"' "+additionalOrgQuery+" order by hhr.hhUniqueId ";
        }
        System.err.println("hhrDao sql is "+sql);
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
            HouseholdReferral hhr=null;
            HouseholdEnrollment hhe=null;
            for(int i=0; i<list.size(); i++)
            {
                    Object[] obj=(Object[])list.get(i);
                    hhr=(HouseholdReferral)obj[0];
                    hhe=(HouseholdEnrollment)obj[1];
                    hhr.setAge(hhe.getHhAge());
                    hhr.setSurname(hhe.getHhSurname());
                    hhr.setFirstName(hhe.getHhFirstname());
                    hhr.setGender(hhe.getHhGender());
                    referralList.add(hhr);
            }
        }
        return referralList;
    }
    public HouseholdReferral getHouseholdReferral(String uniqueId,String refDate) throws Exception
    {
        HouseholdReferral hhr=null;
        List list=new ArrayList();
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            list = session.createQuery("from HouseholdReferral hhr where hhr.hhUniqueId=:uid and hhr.dateOfReferral=:rdate").setString("uid", uniqueId).setString("rdate", refDate).list();
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            session.close();
            throw new Exception(hbe);
        }
        if(list !=null && !list.isEmpty())
        {
            hhr=(HouseholdReferral)list.get(0);
        }
        return hhr;
    }
    public HouseholdReferral getHouseholdReferral(int id) throws Exception
    {
        HouseholdReferral hhr=null;
        List list=new ArrayList();
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            list = session.createQuery("from HouseholdReferral hhr where hhr.recordId=:rid").setInteger("rid", id).list();
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            session.close();
            throw new Exception(hbe);
        }
        if(list !=null && !list.isEmpty())
        {
            hhr=(HouseholdReferral)list.get(0);
        }
        return hhr;
    }
    public void saveHhReferral(HouseholdReferral hhr) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(hhr);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void updateHhReferral(HouseholdReferral hhr) throws Exception
    {
        try
        {
            if(hhr !=null)
            {
                //HouseholdReferral hhr2=this.getHouseholdReferral(hhr.getHhUniqueId(), hhr.getDateOfReferral());
                
                session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                session.update(hhr);
                tx.commit();
                session.close();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void markedForDelete(HouseholdReferral hhr) throws Exception
    {
        try
        {
            hhr.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateHhReferral(hhr);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteHhReferral(HouseholdReferral hhr) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(hhr);
            tx.commit();
            session.close();
            util.saveDeletedRecord(hhr.getHhUniqueId(),null, "hhreferral", hhr.getDateOfReferral());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
