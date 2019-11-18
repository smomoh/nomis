/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.CboSetup;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author COMPAQ USER
 */
public class CboSetupDaoImpl implements CboSetupDao
{
    //private CboSetup cboSetup=null;
    private Session session=null;
    private Transaction tx=null;
    public void saveCboSetupInfo(CboSetup cboSetup) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(cboSetup);
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            session.close();
            throw new Exception(hbe);
        }
        catch(Exception ex)
        {
            session.close();
        }
    }
    public CboSetup getCboSetup(String userLoginName) throws Exception
    {
        CboSetup cboSetup=null;
        List list = new ArrayList();
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from CboSetup cboSetup where cboSetup.userName =:user").setString("user", userLoginName).list();
            tx.commit();
            if(!list.isEmpty())
            {
                cboSetup=(CboSetup)list.get(0);
            }
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
        return cboSetup;
    }
    public void updateCboSetupInfo(CboSetup cboSetup) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.update(cboSetup);
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
    }
    public void deleteCboSetupInfo(CboSetup cboSetup) throws Exception
    {
        try
        {
        session=HibernateUtil.getSession();
        tx=session.beginTransaction();
        session.delete(cboSetup);
        tx.commit();
        session.close();
        }
        catch(HibernateException hbe)
        {
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
    }
    public List getStateListFromCboSetup() throws Exception
    {
        List list = new ArrayList();
        try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct siteSetup.state_code from CboSetup siteSetup").list();
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
    public List getLgaListFromCboSetup(String stateCode) throws Exception
    {
        List list = new ArrayList();
        
        try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct siteSetup.lga_code from CboSetup siteSetup where siteSetup.state_code =:statecode").setString("statecode", stateCode).list();
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
    public List getListOfSiteSetup(String stateCode, String lgaCode) throws Exception
    {
        List list=new ArrayList();
        String queryCriteria=" ";
        if(stateCode !=null && !stateCode.equals("") && !stateCode.equals(" ") && !stateCode.equalsIgnoreCase("All"))
        {
            queryCriteria="where siteSetup.state_code='"+stateCode+"'";
            if(lgaCode !=null && !lgaCode.equals("") && !lgaCode.equals(" ") && !lgaCode.equalsIgnoreCase("All"))
            queryCriteria += " and siteSetup.lga_code like '%"+lgaCode+"%'";
        }
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list=session.createQuery("from CboSetup siteSetup "+queryCriteria+" order by siteSetup.state_code, siteSetup.lga_code asc").list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
        }
        return list;
    }
    public List getCboSetupInfo() throws Exception
    {
        List list = new ArrayList();
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from CboSetup").list();
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
