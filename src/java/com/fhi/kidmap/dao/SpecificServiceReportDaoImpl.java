/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.report.ReportUtility;
import com.fhi.kidmap.report.SpecificServiceReport;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class SpecificServiceReportDaoImpl implements SpecificServiceReportDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
    ReportUtility rutil=new ReportUtility();
    public void saveSpecificServiceReport(SpecificServiceReport sr) throws Exception
    {
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(sr);
            tx.commit();
            session.close();
        } 
        catch(Exception ex) 
        {
            session.close();
            throw new Exception(ex);
        }
    }
    public List getNumberOfOvcSupportedToAccessHivServicesPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    ReportUtility rutil=new ReportUtility();
    String additionalQuery=util.getOvcServiceDateQuery(startDate, endDate)+" and "+rutil.getHIVServicesReportQuery();
    mainList.add(indicatorName);
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        System.err.println("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(service.servicedate),YEAR(service.servicedate), count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("SpecificServiceReport")+" and hhe.stateCode=:state"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(service.servicedate),YEAR(service.servicedate)");
        list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(service.servicedate),YEAR(service.servicedate), count(distinct service.ovcId) "+util.getHouseholdOvcServiceReportQueryPart("SpecificServiceReport")+" and hhe.stateCode=:state"+additionalQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(service.servicedate),YEAR(service.servicedate)").setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        throw new Exception(ex);
    }
    mainList.addAll(list);
    return mainList;
}
    public SpecificServiceReport getHivServiceReport(String ovc_Id) throws Exception
    {
        SpecificServiceReport sr=null;
        List list=new ArrayList();
       try 
       {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from SpecificServiceReport service where service.ovcId=:id and "+rutil.getHIVServicesReportQuery()).setString("id", ovc_Id).list();

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
            sr=(SpecificServiceReport)list.get(0);
        }
        return sr;
    }
    public SpecificServiceReport getSpecificServiceReport(String ovc_Id) throws Exception
    {
        SpecificServiceReport sr=null;
        List list=new ArrayList();
       try 
       {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from SpecificServiceReport sr where sr.ovcId=:id").setString("id", ovc_Id).list();

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
            sr=(SpecificServiceReport)list.get(0);
        }
        return sr;
    }
    public int getNumberOfServicesPerServiceRecord(SpecificServiceReport service) throws Exception
{
    int numberOfServices=0;
    if(service !=null)
    {
        if(service.getServiceAccessed1() !=null && !service.getServiceAccessed1().equals("") && !service.getServiceAccessed1().equals(" "))
        numberOfServices++;
        if(service.getServiceAccessed2() !=null && !service.getServiceAccessed2().equals("") && !service.getServiceAccessed2().equals(" "))
        numberOfServices++;
        if(service.getServiceAccessed3() !=null && !service.getServiceAccessed3().equals("") && !service.getServiceAccessed3().equals(" "))
        numberOfServices++;
        if(service.getServiceAccessed4() !=null && !service.getServiceAccessed4().equals("") && !service.getServiceAccessed4().equals(" "))
        numberOfServices++;
        if(service.getServiceAccessed5() !=null && !service.getServiceAccessed5().equals("") && !service.getServiceAccessed5().equals(" "))
        numberOfServices++;
        if(service.getServiceAccessed6() !=null && !service.getServiceAccessed6().equals("") && !service.getServiceAccessed6().equals(" "))
        numberOfServices++;
        if(service.getServiceAccessed7() !=null && !service.getServiceAccessed7().equals("") && !service.getServiceAccessed7().equals(" "))
        numberOfServices++;
        if(service.getServicesReferred() !=null && !service.getServicesReferred().equals("") && !service.getServicesReferred().equals(" "))
        numberOfServices++;
    }
    return numberOfServices;
}
public List getOvcServicesByOvcIdAndAdditionalServiceQueryByNumberOfServices(String ovcId,String additionalServiceQuery) throws Exception
{
    List list=new ArrayList();

    try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from SpecificServiceReport service where service.ovcId=:id "+additionalServiceQuery+" order by service.numberOfServices desc").setString("id", ovcId).list();

        tx.commit();
        session.close();
        } catch (Exception ex) {
            session.close();
            throw new Exception(ex);
        }
    return list;
}
}
