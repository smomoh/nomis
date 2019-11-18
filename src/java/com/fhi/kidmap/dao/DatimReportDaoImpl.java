/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.report.DatimReportTemplate;
import com.fhi.nomis.logs.NomisLogManager;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DatimReportDaoImpl implements DatimReportDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
    public void saveDatimReportTemplate(DatimReportTemplate rt) throws Exception
    {
        try
        {
            if(rt !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.save(rt);
                tx.commit();
                closeSession(session);
            }
        }
        catch(Exception ex)
        {
            NomisLogManager.logStackTrace(ex);
        }
    }
    public void saveOrUpdateDatimReportTemplate(DatimReportTemplate rt) throws Exception
    {
        try
        {
            if(rt !=null)
            {
                if(getDatimReportTemplate(rt)==null)
                {
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.save(rt);
                    tx.commit();
                    closeSession(session);
                }
                else
                updateDatimReportTemplate(rt);
            }
        }
        catch(Exception ex)
        {
            NomisLogManager.logStackTrace(ex);
        }
    }
    public void updateDatimReportTemplate(DatimReportTemplate rt) throws Exception
    {
        try
        {
            if(rt !=null)
            {
                DatimReportTemplate cirb2=getDatimReportTemplate(rt);
                if(cirb2 !=null)
                {
                    rt.setRecordId(cirb2.getRecordId());
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.update(rt);
                    tx.commit();
                    closeSession(session);
                }
            }
        }
        catch(Exception ex)
        {
            NomisLogManager.logStackTrace(ex);
        }
    }
    public void deleteDatimReportTemplate(DatimReportTemplate rt) throws Exception
    {
        try
        {
            if(rt !=null)
            {
                DatimReportTemplate cirb2=getDatimReportTemplate(rt);
                if(cirb2 !=null)
                {
                    rt.setRecordId(cirb2.getRecordId());
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.delete(rt);
                    tx.commit();
                    closeSession(session);
                }
            }
        }
        catch(Exception ex)
        {
            NomisLogManager.logStackTrace(ex);
        }
    }
    public int deleteAllDatimReportTemplates(String state) throws Exception
    {
        int count=0;
        try
        {
            List list=this.getAllDatimReportTemplateByState(state);
            if(list !=null)
            {
                for(int i=0; i<list.size(); i++)
                {
                    DatimReportTemplate rt=(DatimReportTemplate)list.get(i);
                    deleteDatimReportTemplate(rt);
                    count++;
                }
            }
        }
        catch(Exception ex)
        {
            NomisLogManager.logStackTrace(ex);
        }
        return count;
    }
    public List getDistinctPeriods() throws Exception
    {
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct rt.period from DatimReportTemplate rt").list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            NomisLogManager.logStackTrace(ex);
         }
        return list;
    }
    public List getDistinctStates() throws Exception
    {
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct rt.state from DatimReportTemplate rt").list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            NomisLogManager.logStackTrace(ex);
         }
        return list;
    }
    public List getDistinctLgas(String stateName) throws Exception
    {
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct rt.lga from DatimReportTemplate rt  where rt.state=:st").setString("st", stateName).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            NomisLogManager.logStackTrace(ex);
         }
        return list;
    }
    public List getDistinctStatesByPartner(String partnerCode) throws Exception
    {
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct rt.state from DatimReportTemplate rt where rt.partnerCode=:pc").setString("pc", partnerCode).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            NomisLogManager.logStackTrace(ex);
         }
        return list;
    }
    public DatimReportTemplate getDatimReportTemplate(String lga,String cbo,String partnerCode,String period) throws Exception
    {
        DatimReportTemplate rt = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from DatimReportTemplate rt where rt.lga=:lg and rt.cbo=:cb and rt.partnerCode=:pc and rt.period=:prd")
                    .setString("lg", lga).setString("cb", cbo).setString("pc", partnerCode).setString("prd", period).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                rt=(DatimReportTemplate)list.get(0);
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            NomisLogManager.logStackTrace(ex);
         }
        return rt;
    }
    public List getDistinctStatesByPeriod(String period) throws Exception
    {
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct rt.state from DatimReportTemplate rt where rt.period=:prd").setString("prd", period).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            NomisLogManager.logStackTrace(ex);
         }
        return list;
    }
    public List getDistinctStatesByPeriodAndPartner(String partnerCode,String period) throws Exception
    {
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct rt.state from DatimReportTemplate rt where rt.partnerCode=:pc and rt.period=:prd").setString("pc", partnerCode).setString("prd", period).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            NomisLogManager.logStackTrace(ex);
         }
        return list;
    }
    public List getAllDatimReportTemplates() throws Exception
    {
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DatimReportTemplate rt").list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            NomisLogManager.logStackTrace(ex);
         }
        return list;
    }
    public List getAllDatimReportTemplateByState(String state) throws Exception
    {
        List list = null;
        if(state !=null)
        state=state.trim();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DatimReportTemplate rt where rt.state=:st").setString("st", state).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            NomisLogManager.logStackTrace(ex);//throw new Exception(ex);
         }
        return list;
    }
    public DatimReportTemplate getDatimReportTemplate(DatimReportTemplate rt) throws Exception
    {
        DatimReportTemplate drt2=null;
        try
        {
            if(rt !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                List list = session.createQuery("from DatimReportTemplate rt where rt.state=:st and rt.lga=:lg and rt.cbo=:cb and rt.partnerCode=:ptc and rt.period=:prd").setString("st", rt.getState()).setString("lg", rt.getLga()).setString("cb", rt.getCbo()).setString("ptc", rt.getPartnerName()).setString("prd", rt.getPeriod()).list();
                tx.commit();
                closeSession(session);
                if(list !=null && !list.isEmpty())
                {
                    drt2=(DatimReportTemplate)list.get(0);
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            NomisLogManager.logStackTrace(ex);
         }
        return drt2;
    }
    public DatimReportTemplate getDatimReportTemplate(String state,String lga,String period) throws Exception
    {
        DatimReportTemplate rt=null;
        if(state !=null)
        state=state.trim();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from DatimReportTemplate rt where rt.state=:st and rt.lga=:lg  and rt.period=:prd").setString("st", state).setString("lg", lga).setString("prd", period).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                rt=(DatimReportTemplate)list.get(0);
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            NomisLogManager.logStackTrace(ex);
         }
        return rt;
    }
    public DatimReportTemplate getDatimReportTemplate(String state,String lga,String cbo, String partnerCode,String period) throws Exception
    {
        DatimReportTemplate rt=null;
        if(state !=null)
        state=state.trim();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from DatimReportTemplate rt where rt.state=:st and rt.lga=:lg and rt.cbo=:cb and rt.partnerCode=:ptc and rt.period=:prd").setString("st", state).setString("lg", lga).setString("cb", cbo).setString("ptc", partnerCode).setString("prd", period).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                rt=(DatimReportTemplate)list.get(0);
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            NomisLogManager.logStackTrace(ex);
         }
        return rt;
    }
    public List getAllDatimReportTemplateByLga(String state,String lga) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DatimReportTemplate rt where rt.state=:st and rt.lga=:lg").setString("st", state).setString("lg", lga).list();
            tx.commit();
            closeSession(session);   
        }
         catch (Exception ex)
         {
            closeSession(session);
            NomisLogManager.logStackTrace(ex);
         }
        return list;
    }
    public List getAllDatimReportTemplateByCbo(String state,String lga,String cbo) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DatimReportTemplate rt where rt.state=:st and rt.lga=:lg and and rt.cbo=:cb").setString("st", state).setString("lg", lga).setString("cb", cbo).list();
            tx.commit();
            closeSession(session);   
        }
         catch (Exception ex)
         {
            closeSession(session);
            NomisLogManager.logStackTrace(ex);
         }
        return list;
    }
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
    
}
