/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcSchool;
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
public class OvcSchoolDaoImpl implements OvcSchoolDao {

    private OvcSchool ovcSchool = null;
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();

  public List getOvcSchools() throws Exception {
        List list = new ArrayList();
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from OvcSchool").list();
        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
  public void cleanSchoolNamesInOvcRecords() throws Exception
  {
      List list = new ArrayList();
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Ovc ovc where ovc.schoolName like '%&%'").list();
        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
        Ovc ovc=null;
        String schoolName=null;
        OvcDao ovcDao=new OvcDaoImpl();
        System.err.println("list size in cleanSchoolNames() is "+list.size());
        for(Object s:list)
        {
            ovc=(Ovc)s;
            schoolName=ovc.getSchoolName().replace("&", "and");
            ovc.setSchoolName(schoolName);
            ovcDao.updateOvc(ovc,false,false);
        }
  }
  public void cleanSchoolNamesInSchoolRecords() throws Exception
  {
      List list = new ArrayList();
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OvcSchool school where school.name like '%&%'").list();
        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
        OvcSchool school=null;
        String schoolName=null;
        System.err.println("list size in cleanSchoolNames() is "+list.size());
        for(Object s:list)
        {
            school=(OvcSchool)s;
            schoolName=school.getName().replace("&", "and");
            school.setName(schoolName);
            this.updateOvcSchool(school);
        }
  }
    public List getSchoolAttendanceList(String stateName,String schoolName,String[] param) throws Exception
    {
        List list = new ArrayList();
        List ovcList = new ArrayList();
         try
         {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String additionalQuery=util.getQueryCriteria(param);
            //System.err.println(util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and ovc.schoolName=:school_name "+additionalQuery+" order by ovc.ovcId ");
            list = session.createQuery(util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and ovc.schoolName=:school_name "+additionalQuery+" order by ovc.ovcId ")
                    .setString("school_name", schoolName).list();
        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }
        for(Object s:list)
        {
            Object[] obj=(Object[])s;
            ovcList.add((Ovc)obj[1]);
        }
        return ovcList;
    }
    public List getOvcCountPerSchool(String[] params) throws Exception {
        List list = new ArrayList();
        int totalInSchool=0;
        String typeQuery=" ";
        String stateCode=params[0];
        String lgaCode=params[1];
        String cboCode=params[2];
        String wardCode=params[3];
        String school_type=params[4];
        String partnerCode=params[10];
        if(!school_type.equalsIgnoreCase("All"))
        typeQuery=util.getSchoolType(school_type);
        String param[]={stateCode,lgaCode,cboCode,wardCode,null,null,null,null,"0","17",partnerCode,null,null,null,null};
        String additionalQuery=util.getQueryCriteria(param);
        List ovcCountList=new ArrayList();
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct ovc.schoolName "+util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and (ovc.schoolName !=null and ovc.schoolName !='' and ovc.schoolName !=' ' and  ovc.schoolName !='  ') "+typeQuery+additionalQuery+" order by ovc.schoolName").list();
            tx.commit();
            session.close();
        } 
        catch (HibernateException he) 
        {
            session.close();
            throw new Exception(he);
        }
        String address=" ";
        for(Object s:list)
        {
            String schoolName=(String)s;
            address=" ";
            if(schoolName.length()<3)
                continue;
            int ovcCount=getNoOfOvcInSchool(stateCode,schoolName,partnerCode,additionalQuery);
            OvcSchool school=getOvcSchool(stateCode,lgaCode,schoolName);
            
            if(school !=null)
            address=school.getAddress();
                List nameAndCountList=new ArrayList();
                nameAndCountList.add(schoolName);
                nameAndCountList.add(address);
                nameAndCountList.add(ovcCount);
                ovcCountList.add(nameAndCountList);
                totalInSchool+=ovcCount;
        }
        List totalCountList=new ArrayList();
            //totalCountList.add("Total OVC in school");
            //totalCountList.add(totalInSchool);
            ovcCountList.add(totalCountList);
        return ovcCountList;
    }
    private int getNoOfOvcInSchool(String stateName,String schName,String partnerCode,String additionalQuery) throws Exception
    {
        List list = new ArrayList();
        int ovcCount=0;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            if(!partnerCode.equalsIgnoreCase("All"))
            list = session.createQuery("select ovc.ovcId "+util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and  ovc.schoolName = :school_name and hhe.partnerCode=:partner_code "+additionalQuery)
            .setString("school_name", schName).setString("partner_code", partnerCode).list();
            else
            list = session.createQuery("select ovc.ovcId "+util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and ovc.schoolName = :school_name "+additionalQuery)
            .setString("school_name", schName).list();
            tx.commit();
            session.close();
        } 
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        if(list!=null)
        ovcCount=list.size();
        return ovcCount;
    }
    public List getSchoolListPerState(String state_id) throws Exception
    {
        List list = new ArrayList();
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from OvcSchool school where school.state=:stateId order by school.name").setString("stateId", state_id).list();
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
    public List getSchoolList(String state_id) throws Exception
    {
        List list = new ArrayList();
        String queryCriteria=" ";
        if(state_id !=null && state_id !="" && state_id !=" " && !state_id.equalsIgnoreCase("All"))
        queryCriteria="where school.state like '%"+state_id+"%'";
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OvcSchool school "+queryCriteria+" order by school.name").list();
        
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
    public List getSchoolListForExport(String state_id,String lga_id) throws Exception
    {
        List list = new ArrayList();
        String queryCriteria=" ";
        if(state_id !=null && !state_id.equals("") && !state_id.equals(" ") && !state_id.equalsIgnoreCase("All"))
        {
            queryCriteria="where school.state='"+state_id+"'";
            if(lga_id !=null && !lga_id.equals("") && !lga_id.equals(" ") && !lga_id.equalsIgnoreCase("All"))
            {
                lga_id=lga_id.trim();
                queryCriteria=queryCriteria+" and school.lga like'%"+lga_id+"%'";
            }
        }
        
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from OvcSchool school "+queryCriteria+" order by school.name").list();
        
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
    public OvcSchool getOvcSchool(String schoolName,String additionalQuery) throws Exception
    {
        List list = new ArrayList();
        OvcSchool school=null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from OvcSchool school where school.name=:school_name "+additionalQuery).setString("school_name", schoolName).list();
        tx.commit();
        session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        if(list !=null && list.size()>0)
        {
            school=(OvcSchool)list.get(0);
        }
        return school;
    }
    public OvcSchool getOvcSchool(String state,String lga,String schoolName) throws Exception
    {
        List list = new ArrayList();
        OvcSchool school=null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from OvcSchool school where school.state=:state_id and school.name=:school_name").setString("state_id", state).setString("school_name", schoolName).list();
        tx.commit();
        session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        if(list !=null && list.size()>0)
        {
            school=(OvcSchool)list.get(0);
        }
        return school;
    }
    public OvcSchool getSchool(String schoolName) throws Exception
    {
        List list = new ArrayList();
        OvcSchool school=null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from OvcSchool school where school.name=:school_name").setString("school_name", schoolName).list();
        tx.commit();
        session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        if(list !=null && list.size()>0)
        {
            school=(OvcSchool)list.get(0);
        }
        return school;
    }
    public OvcSchool getSchoolDetails(int school_id) throws Exception
    {
        List list = new ArrayList();
        OvcSchool school=null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from OvcSchool school where school.id=:schoolId").setInteger("schoolId", school_id).list();
        tx.commit();
        session.close();
        }
        catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        if(list !=null && list.size()>0)
        {
            school=(OvcSchool)list.get(0);
        }
        return school;
    }
    public List getSchoolListFromEnrollment(String stateName) throws Exception
    {
        List list = new ArrayList();
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select distinct ovc.schoolName"+util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and hhe.stateCode like '%"+stateName+"%' order by ovc.schoolName").list();
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

    /*public OvcSchool getOvcSchool(String name) throws Exception {

        return ovcSchool;
    }*/
    public void saveOvcSchool(OvcSchool ovcSchool) throws Exception {

         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

        session.save(ovcSchool); 

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            session.close();
            throw new Exception(he);
        }

    }
    public void updateOvcSchool(OvcSchool ovcSchool) throws Exception
    {
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.update(ovcSchool);
            tx.commit();
            session.close();
        } 
        catch (HibernateException he) 
        {
            session.close();
            throw new Exception(he);
        }
    }
    public void markedForDelete(OvcSchool ovcSchool) throws Exception
    {
        try
        {
            ovcSchool.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateOvcSchool(ovcSchool);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void deleteOvcSchool(OvcSchool ovcSchool) throws Exception
    {
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        session.delete(ovcSchool);
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
    }

}
