/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;


import com.fhi.kidmap.business.Ward;
import com.fhi.kidmap.business.Wards;
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
public class WardDaoImpl implements WardDao {

    Session session;
    Transaction tx;
    SessionFactory sessions;


    private Ward ward = null;
    private Ward wardById=null;

    public Ward getWardById()
    {
        return wardById;
    }

    public void setWardById(Ward wardById) {
        this.wardById = wardById;
    }
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
 public List getWardsWithSpacesInCode() throws Exception
 {
    List list = new ArrayList();
    
     try 
     {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from Wards ward where LENGTH(ward.ward_code)>"+NomisConstant.COMMUNITYCODE_LENGTH).list();
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
  public List getWardsByStateCode(String stateCode) throws Exception
 {
    List list = new ArrayList();
    
     try 
     {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from Wards ward where ward.ward_code like '"+stateCode+"/%'").list();
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
public List getWardsByStateAndLgaCodes(String stateCode,String lgaCode) throws Exception
 {
    List list = new ArrayList();
    String stateLgaCode=stateCode+"/"+lgaCode;
     try 
     {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from Wards ward where ward.ward_code like '%"+stateLgaCode+"%'").list();
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
public List getWardByWardCode(String wardCode) throws Exception
 {
    List list = new ArrayList();

     try {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from Wards wards where wards.ward_code = :wardId").setString("wardId",wardCode).list();
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
     public List getWardByLgaCodeAndCboId(String stateAndLgaCodes,String cboId) throws Exception
    {
        List list = new ArrayList();
        List wardList = new ArrayList();
         try 
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Wards wards where wards.ward_code like '%"+stateAndLgaCodes+"%' and wards.cbo_code = :cboCode").setString("cboCode",cboId).list();
            tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        for(Object s:list)
        {
            Wards ward=(Wards)s;
            wardList.add(ward);
        }
        return wardList;
    }
    public List getWardByCboId(String cboId) throws Exception
    {
        List list = new ArrayList();
        List wardList = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Wards wards where wards.cbo_code = :cboCode").setString("cboCode",cboId).list();
            tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        for(Object s:list)
        {
            Wards ward=(Wards)s;
            wardList.add(ward);
        }
        return wardList;
    }
    public Wards getWards(String wardCode) throws Exception
    {
        List list = new ArrayList();
        Wards ward=null;
        if(wardCode !=null)
        wardCode=wardCode.trim();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Wards wards where TRIM(wards.ward_code) = :wCode").setString("wCode",wardCode).list();
            tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        if(list !=null && list.size()>0)
        ward=(Wards)list.get(0);
        return ward;
    }
    public Wards getWardsByCboCode(String cboCode) throws Exception
    {
        List list = new ArrayList();
        Wards ward=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Wards wards where wards.ward_code like '%"+cboCode+"%'").list();
            tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        if(list !=null && list.size()>0)
        ward=(Wards)list.get(0);
        return ward;
    }
    public List getWardsFromEnrollment(String stateName,String cboName) throws Exception
    {
        List list = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String sql="select distinct (TRIM(hhe.communityCode)) from HouseholdEnrollment hhe where hhe.stateCode like '%"+stateName+"%' and hhe.orgCode like '%"+cboName+"%'";
            list = session.createQuery(sql).list();
            tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
     public List getWards() throws Exception
     {
        List list = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from Ward ward order by ward.name asc").list();
        
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
     public List getAllWards() throws Exception
     {
        List list = new ArrayList();
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Wards ward order by ward.ward_name asc").list();
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
    public List getWardListByOrgUnits(String orgCriteria) throws Exception
     {
        List list = new ArrayList();
        //String queryCriteria=" ";
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query="from Wards ward "+orgCriteria+" order by ward.ward_name asc";
            System.err.println("query in getWardListByOrgUnits is "+query);
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

    public Ward getWard(String name) throws Exception {

        return ward;
    }
    public void saveWard(Wards ward) throws Exception 
    {
        try
        {
            if(ward !=null)
            {
                if(ward.getWard_code() !=null)
                {
                    AppUtility appUtil=new AppUtility();
                    String cleanedWardCode=appUtil.removeSpacesFromString(ward.getWard_code());
                    if( cleanedWardCode !=null)
                    {// && cleanedWardCode.length() ==NomisConstant.COMMUNITYCODE_LENGTH
                        ward.setWard_code(cleanedWardCode);
                        session = HibernateUtil.getSession();
                        tx = session.beginTransaction();
                        session.save(ward);
                        tx.commit();
                        session.close();
                    }
                 }
            }
        }
        catch(HibernateException hbe)
        {
            session.close();
            throw new Exception(hbe);
        }
    }
    public void updateWard(Wards ward) throws Exception
    {
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.update(ward);
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            session.close();
            throw new Exception(hbe);
        }
    }
    public void deleteWard(Wards ward) throws Exception
    {
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(ward);
            tx.commit();
            session.close();
        }
        catch(HibernateException hbe)
        {
            session.close();
            throw new Exception(hbe);
        }
    }

}
