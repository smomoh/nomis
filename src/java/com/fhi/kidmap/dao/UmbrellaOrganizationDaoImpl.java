/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.UmbrellaOrganization;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class UmbrellaOrganizationDaoImpl implements UmbrellaOrganizationDao
{
    Session session;
    Transaction tx;
    AppUtility appUtil=new AppUtility();
    public void saveUmbrellaOrganization(UmbrellaOrganization uo) throws Exception
    {
        try
        {
            if(uo.getRecordId()==null)
            uo.setRecordId(generateUniqueId());
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.save(uo);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public void updateUmbrellaOrganization(UmbrellaOrganization uo) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.update(uo);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public void deleteUmbrellaOrganization(UmbrellaOrganization uo) throws Exception
    {
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            session.delete(uo);
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
    }
    public UmbrellaOrganization getSubOrganizationByRecordId(String recordId) throws Exception
    {
        UmbrellaOrganization uo=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from UmbrellaOrganization uo where uo.recordId =:id").setString("id", recordId).list();

        tx.commit();
        session.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
        if(list !=null && !list.isEmpty())
        {
            uo=(UmbrellaOrganization)list.get(0);
        }
        return uo;
    }
    public UmbrellaOrganization getUmbrellaOrganization(String umbrellaOrganizationCode) throws Exception
    {
        UmbrellaOrganization uo=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from UmbrellaOrganization uo where uo.umbrellaOrganizationCode =:id").setString("id", umbrellaOrganizationCode).list();

        tx.commit();
        session.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
        if(list !=null && !list.isEmpty())
        {
            uo=(UmbrellaOrganization)list.get(0);
        }
        return uo;
    }
    public UmbrellaOrganization getUmbrellaOrganization(String umbrellaOrganizationCode,String subOrganizationCode) throws Exception
    {
        UmbrellaOrganization uo=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from UmbrellaOrganization uo where uo.umbrellaOrganizationCode =:id and uo.subOrganizationCodes like '%"+subOrganizationCode+"%'").setString("id", umbrellaOrganizationCode).list();

        tx.commit();
        session.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
        if(list !=null && !list.isEmpty())
        {
            uo=(UmbrellaOrganization)list.get(0);
        }
        return uo;
    }
    public UmbrellaOrganization getUmbrellaOrganizationByUmbrellaOrganizationCodeOrSubOrganization(String orgCode) throws Exception
    {
        UmbrellaOrganization uo=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from UmbrellaOrganization uo where uo.umbrellaOrganizationCode =:id or uo.subOrganizationCodes like '%"+orgCode+"%'").setString("id", orgCode).list();

        tx.commit();
        session.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
        if(list !=null && !list.isEmpty())
        {
            uo=(UmbrellaOrganization)list.get(0);
        }
        return uo;
    }
    public UmbrellaOrganization getUmbrellaOrganizationBySubOrganizationCode(String subOrganizationCode) throws Exception
    {
        UmbrellaOrganization uo=null;
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from UmbrellaOrganization uo where uo.subOrganizationCodes like '%"+subOrganizationCode+"%'").list();

        tx.commit();
        session.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
        if(list !=null && !list.isEmpty())
        {
            uo=(UmbrellaOrganization)list.get(0);
        }
        return uo;
    }
    public List getListOfUmbrellaOrganization() throws Exception
    {
        List list=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from UmbrellaOrganization uo").list();

        tx.commit();
        session.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            session.close();
        }
        return list;
    }
    public String generateUniqueId() throws Exception
    {
        String uniqueId=appUtil.generateUniqueId();
        if(getSubOrganizationByRecordId(uniqueId) !=null)
        generateUniqueId();
        return uniqueId;
    }
    public List filterOrganizationRecords(List allOrgList) throws Exception
    {
        List orgList=new ArrayList();
        if(allOrgList !=null && allOrgList.size()>0)
        {
            String orgCode=null;
            OrganizationRecords orgRecords=null;
            for(int i=0; i<allOrgList.size(); i++)
            {
                orgRecords=(OrganizationRecords)allOrgList.get(i);
                
                if(i==0 && allOrgList.size()==1)
                {
                    orgList.add(orgRecords);
                    break;
                }
                orgCode=orgRecords.getOrgCode().trim();
                if(getUmbrellaOrganizationBySubOrganizationCode(orgCode)==null)
                {
                    orgList.add(orgRecords);
                }
            }
        }
        return orgList;
    }
    /*This method generate the list to populate the sub organization list on the view when an umbrella organization is selected*/
    public List filterOrganizationRecordsForSubOrganizationList(String umbrellaOrganizationCode,List allOrgList) throws Exception
    {
        List umbrellaOrganizationList=new ArrayList();
        if(allOrgList !=null && allOrgList.size()>0)
        {
                umbrellaOrganizationList.clear();
                String orgCode=null;
                for(int i=0; i<allOrgList.size(); i++)
                {
                    OrganizationRecords orgRecords=(OrganizationRecords)allOrgList.get(i);
                    umbrellaOrganizationCode=umbrellaOrganizationCode.trim();
                    orgCode=orgRecords.getOrgCode().trim();
                    
                    /*Remove the organization selected as the umbrella organization from the list of sub-organizations and any umbrella organization in the list*/
                    if(orgCode.indexOf(umbrellaOrganizationCode)!=-1)
                    {
                        //System.err.println("orgCode 1 is "+orgCode);
                        continue;
                    }
                    else if(getUmbrellaOrganization(orgCode) !=null)
                    {
                        //System.err.println("orgCode 2 is "+orgCode);
                        continue;
                    }
                    else if(getUmbrellaOrganizationByUmbrellaOrganizationCodeOrSubOrganization(orgCode) !=null && getUmbrellaOrganization(umbrellaOrganizationCode,orgCode) ==null)
                    {
                        //System.err.println("orgCode 3 is "+orgCode);
                        continue;
                    }
                        umbrellaOrganizationList.add(orgRecords);
                 }

            }
        System.err.println("umbrellaOrganizationList.size() is "+umbrellaOrganizationList.size());
        return umbrellaOrganizationList;
    }
}
