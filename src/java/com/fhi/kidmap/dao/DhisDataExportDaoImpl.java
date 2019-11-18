/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.DhisDataExport;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class DhisDataExportDaoImpl implements DhisDataExportDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public List changeOrgUnitNames(String nameParts,String instance) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DhisDataExport dde where dde.orgUnitName like '%"+nameParts+"%' and dde.dhisInstance=:inst")
            .setString("inst", instance).list();
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
    public List getNonDuplicateRecordsFromDhisDataExportForOVC(String orgUnitId,String instance) throws Exception
    {
        List list=null;
        try
        {
            //DhisDataExport dde=new DhisDataExport();dde.gets;
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct dde.orgUnitId,dde.dataElementId,dde.categoryOptionComboId,dde.startDate, sum(dde.value) from DhisDataExport dde where dde.dataElementId is not null and dde.categoryOptionComboId is not null and dde.orgUnitId=:ouId and  dde.dhisInstance=:inst group by dde.orgUnitId,dde.dataElementId,dde.categoryOptionComboId,dde.startDate")
            .setString("ouId", orgUnitId).setString("inst", instance).list();
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
    public List getNonDuplicateRecordsFromDhisDataExport(String orgUnitId,String instance) throws Exception
    {
        List list=null;
        try
        {
            //DhisDataExport dde=new DhisDataExport();dde.gets;
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct dde.orgUnitId,dde.orgUnitName, dde.dataElementId,dde.dataElementName,dde.categoryOptionComboId,dde.startDate, sum(dde.value) from DhisDataExport dde where dde.dataElementId is not null and dde.categoryOptionComboId is not null and dde.orgUnitId=:ouId and  dde.dhisInstance=:inst group by dde.orgUnitId,dde.orgUnitName, dde.dataElementId,dde.dataElementName,dde.categoryOptionComboId,dde.startDate")
            .setString("ouId", orgUnitId).setString("inst", instance).list();
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
    public List ExportToDhisFromDhisDataExport(String orgUnitId,String instance) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DhisDataExport dde where dde.dataElementId is not null and dde.categoryOptionComboId is not null and dde.orgUnitId=:ouId and  dde.dhisInstance=:inst")
            .setString("ouId", orgUnitId).setString("inst", instance).list();
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
    public DhisDataExport getDhisDataExportByDataElementNameAndCategoryComboOptionName(String dataElementName,String categoryComboOptionName) throws Exception
    {
        DhisDataExport dde=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DhisDataExport dde where dde.dataElementName =:deName and dde.categoryComboOptionName=:cocName and dde.usgDataElementAndCategory is null")
            .setString("deName", dataElementName).setString("cocName", categoryComboOptionName).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        if(list !=null && list.size()>0)
        {
            dde=(DhisDataExport)list.get(0);//dde.getCategoryComboOptionName()
        }
        return dde;
    }
    public List getDistinctDataElementNames() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct dde.dataElementName from DhisDataExport dde").list();
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
    public List getDistinctCategoryComboOption() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct dde.categoryComboOptionName from DhisDataExport dde").list();
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
    public List getDistinctDataElementAndCategoryComboOption() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct dde.dataElementName, dde.categoryComboOptionName from DhisDataExport dde").list();
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
    public List getDistinctRecordParametersFromDhisDataExport(String orgUnitName, String instance) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct dde.orgUnitName, dde.dataElementName, dde.startDate from DhisDataExport dde where dde.orgUnitName=:orgName and dde.dhisInstance=:inst")
            .setString("orgName", orgUnitName).setString("inst", instance).list();
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
    public List getDistinctOrgUnitIdsFromDhisDataExport(String instance) throws Exception
    {
        List list=null;
        try
        {//select distinct dde.orgUnitId
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct dde.orgUnitId from DhisDataExport dde where dde.orgUnitId is not null and dde.dataElementId is not null and dde.categoryOptionComboId is not null and dde.dhisInstance=:inst order by dde.orgUnitId asc")
            .setString("inst", instance).list();
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
    public List getAllDistinctOrgUnitNamesFromDhisDataExport(String instance) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct dde.orgUnitName from DhisDataExport dde where dde.dhisInstance=:inst")
            .setString("inst", instance).list();
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

    public List getDistinctOrgUnitNamesFromDhisDataExport(String instance) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct dde.orgUnitName from DhisDataExport dde where dde.dhisInstance=:inst and dde.orgUnitId is null")
            .setString("inst", instance).list();
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
    public List getDhisDataExportByOrgUnitId(String orgUnitId,String instance) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DhisDataExport dde where dde.orgUnitId =:orgUnit and dde.dhisInstance=:inst")
                    .setString("orgUnit", orgUnitId).setString("inst", instance).list();
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
    public List getDhisDataExportByOrgUnitName(String orgUnitName,String instance) throws Exception
    {
        List list=null;
        try
        {

            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DhisDataExport dde where dde.orgUnitName =:orgUnit and dde.dhisInstance=:inst")
                    .setString("orgUnit", orgUnitName).setString("inst", instance).list();
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
    public List getDistinctDataElementNamesFromDhisDataExport(String instance) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct dde.dataElementName from DhisDataExport dde where dde.dhisInstance=:inst")
            .setString("inst", instance).list();
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
    public List getDhisDataExportByDataElementName(String dataElementName,String instance) throws Exception
    {
        List list=null;
        try
        {

            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DhisDataExport dde where dde.dataElementName =:deName and dde.dhisInstance=:inst")
                    .setString("deName", dataElementName).setString("inst", instance).list();
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
    public List getDhisDataExportDataByCategoryComboOptionName(String cocName,String instance) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DhisDataExport dde where dde.categoryComboOptionName =:cocName and dde.dhisInstance=:inst")
                    .setString("cocName", cocName).setString("inst", instance).list();
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
    public List getDhisDataExportList(String orgUnitName,String dataElementName,String startDate) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DhisDataExport dde where dde.orgUnitName=:orgName and dde.dataElementName =:deName and dde.startDate=:sdate")
                    .setString("orgName", orgUnitName).setString("deName", dataElementName).setString("sdate", startDate).list();
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
    public List getDhisDataExportList(String orgUnitName,String instance,String dataElementName,String startDate) throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DhisDataExport dde where dde.orgUnitName=:orgName and dde.dhisInstance=:inst and dde.usgDataElementAndCategory =:deName and dde.startDate=:sdate")
                    .setString("orgName", orgUnitName).setString("inst", instance).setString("deName", dataElementName).setString("sdate", startDate).list();
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
    public DhisDataExport getDhisDataExport(String orgUnitName,String instance,String dataElementName,String startDate) throws Exception
    {
        DhisDataExport dde=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DhisDataExport dde where dde.orgUnitName=:orgName and dde.dhisInstance=:inst and dde.dataElementName =:deName and dde.startDate=:sdate")
                    .setString("orgName", orgUnitName).setString("inst", instance).setString("deName", dataElementName).setString("sdate", startDate).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        if(list !=null && list.size()>0)
        {
            dde=(DhisDataExport)list.get(0);
        }
        return dde;
    }
    public List getDhisDataExports() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from DhisDataExport dde ")
                    .list();
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
    public void saveDhisDataExport(DhisDataExport dde) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(dde);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public void updateDhisDataExport(DhisDataExport dde) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.update(dde);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
    public void deleteDhisDataExport(DhisDataExport dde) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(dde);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
    }
}