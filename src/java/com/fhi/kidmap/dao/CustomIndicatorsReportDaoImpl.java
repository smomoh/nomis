/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.nomis.logs.NomisLogManager;
import com.nomis.business.ReportTemplate;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class CustomIndicatorsReportDaoImpl implements CustomIndicatorsReportDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
    
    public void saveReportTemplate(ReportTemplate rt) throws Exception
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
    public void saveOrUpdateReportTemplate(ReportTemplate rt) throws Exception
    {
        try
        {
            if(rt !=null)
            {
                if(getReportTemplate(rt)==null)
                {
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.save(rt);
                    tx.commit();
                    closeSession(session);
                }
                else
                updateReportTemplate(rt);
            }
        }
        catch(Exception ex)
        {
            NomisLogManager.logStackTrace(ex);
        }
    }
    public void updateReportTemplate(ReportTemplate rt) throws Exception
    {
        try
        {
            if(rt !=null)
            {
                ReportTemplate cirb2=getReportTemplate(rt);
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
    public void deleteReportTemplate(ReportTemplate rt) throws Exception
    {
        try
        {
            if(rt !=null)
            {
                ReportTemplate cirb2=getReportTemplate(rt);
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
    public int deleteAllReportTemplates(String state) throws Exception
    {
        int count=0;
        try
        {
            List list=this.getAllReportTemplateByState(state);
            if(list !=null)
            {
                for(int i=0; i<list.size(); i++)
                {
                    ReportTemplate rt=(ReportTemplate)list.get(i);
                    deleteReportTemplate(rt);
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
            list = session.createQuery("select distinct rt.period from ReportTemplate rt").list();
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
            list = session.createQuery("select distinct rt.state from ReportTemplate rt").list();
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
            list = session.createQuery("select distinct rt.state from ReportTemplate rt where rt.partnerCode=:pc").setString("pc", partnerCode).list();
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
    public ReportTemplate getReportTemplate(String lga,String cbo,String partnerCode,String indicatorName, String merCode,String otherDisaggregation,String period) throws Exception
    {
        ReportTemplate rt = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from ReportTemplate rt where rt.lga=:lg and rt.cbo=:cb and rt.partnerCode=:pc and rt.indicatorName=:indn and rt.merCode=:mer and rt.otherDisaggregation=:od and rt.period=:prd")
                    .setString("lg", lga).setString("cb", cbo).setString("pc", partnerCode).setString("indn", indicatorName).setString("mer", merCode).setString("od", otherDisaggregation).setString("prd", period).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                rt=(ReportTemplate)list.get(0);
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
            list = session.createQuery("select distinct rt.state from ReportTemplate rt where rt.period=:prd").setString("prd", period).list();
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
            list = session.createQuery("select distinct rt.state from ReportTemplate rt where rt.partnerCode=:pc and rt.period=:prd").setString("pc", partnerCode).setString("prd", period).list();
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
    public List getAllReportTemplates() throws Exception
    {
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ReportTemplate rt").list();
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
    public List getAllReportTemplateByState(String state) throws Exception
    {
        List list = null;
        if(state !=null)
        state=state.trim();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ReportTemplate rt where rt.state=:st").setString("st", state).list();
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
    public ReportTemplate getReportTemplate(ReportTemplate rt) throws Exception
    {
        ReportTemplate cirb2=null;
        try
        {
            if(rt !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                List list = session.createQuery("from ReportTemplate rt where rt.state=:st and rt.lga=:lg and rt.cbo=:cb and rt.partnerCode=:pt and rt.indicatorName=:ind and rt.merCode=:mer and rt.otherDisaggregation=:disag and rt.period=:prd").setString("st", rt.getState()).setString("lg", rt.getLga()).setString("cb", rt.getCbo()).setString("pt", rt.getPartnerCode()).setString("ind", rt.getIndicatorName()).setString("mer", rt.getMerCode()).setString("disag", rt.getOtherDisaggregation()).setString("prd", rt.getPeriod()).list();
                tx.commit();
                closeSession(session);
                if(list !=null && !list.isEmpty())
                {
                    cirb2=(ReportTemplate)list.get(0);
                }
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            NomisLogManager.logStackTrace(ex);
         }
        return cirb2;
    }
    public ReportTemplate getReportTemplate(String state,String lga,String cbo,String indicator, String otherDisaggregation,String period) throws Exception
    {
        ReportTemplate rt=null;
        if(state !=null)
        state=state.trim();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from ReportTemplate rt where rt.state=:st and rt.lga=:lg and rt.cbo=:cb and rt.indicator=:ind and rt.sex=:sx and rt.ageCategory=:ac and rt.otherDisaggregation=:disag and rt.period=:prd").setString("st", state).setString("lg", lga).setString("cb", cbo).setString("ind", indicator).setString("disag", otherDisaggregation).setString("prd", period).list();
            tx.commit();
            closeSession(session);
            if(list !=null && !list.isEmpty())
            {
                rt=(ReportTemplate)list.get(0);
            }
        }
         catch (Exception ex)
         {
             closeSession(session);
            NomisLogManager.logStackTrace(ex);
         }
        return rt;
    }
    public List getReportTemplatesByStatePartnerAndPeriod(String state,String partnerCode,String period) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            if(partnerCode !=null && !partnerCode.equalsIgnoreCase("All"))
            list = session.createQuery("from ReportTemplate rt where rt.state=:st and rt.partnerCode=:ptc and rt.period=:prd").setString("st", state).setString("ptc", partnerCode).setString("prd", period).list();
            else
            list = session.createQuery("from ReportTemplate rt where rt.state=:st and rt.period=:prd").setString("st", state).setString("prd", period).list();
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
    public List getAllReportTemplateByLga(String state,String lga) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ReportTemplate rt where rt.state=:st and rt.lga=:lg").setString("st", state).setString("lg", lga).list();
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
    public List getAllReportTemplateByCbo(String state,String lga,String cbo) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from ReportTemplate rt where rt.state=:st and rt.lga=:lg and and rt.cbo=:cb").setString("st", state).setString("lg", lga).setString("cb", cbo).list();
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
