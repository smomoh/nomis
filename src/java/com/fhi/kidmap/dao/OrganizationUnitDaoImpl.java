/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.OrganizationUnit;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class OrganizationUnitDaoImpl implements OrganizationUnitDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    public void saveOrganizationalUnit(OrganizationUnit ou) throws Exception
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        session.save(ou);
        tx.commit();
        session.close();
    }
    public void updateOrganizationalUnit(OrganizationUnit ou) throws Exception
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        session.update(ou);
        tx.commit();
        session.close();
    }
    public void deleteOrganizationalUnit(OrganizationUnit ou) throws Exception
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        session.delete(ou);
        tx.commit();
        session.close();
    }
    public OrganizationUnit getOrganizationUnit(String orgunitId) throws Exception
    {
        return null;
    }
    public OrganizationUnit getOrganizationUnitByName(String orgunitName) throws Exception
    {
        return null;
    }
    public OrganizationUnit getOrganizationUnitByShortCode(String orgunitShortCode) throws Exception
    {
        return null;
    }
    public List getAllOrganizationUnit() throws Exception
    {
        return null;
    }
    public List getOrganizationUnitByLevel(int level) throws Exception
    {
        return null;
    }
}
