/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.HivStatus;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.nomis.OperationsManagement.HivRecordsManager;
import com.fhi.nomis.logs.NomisLogManager;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Siaka
 */
public class HouseholdEnrollmentDaoImpl implements HouseholdEnrollmentDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
public List getDistinctCBOCodesByLga(String lgaCode) throws Exception
{
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct hhe.orgCode from HouseholdEnrollment hhe where hhe.lgaCode=:lgcode").setString("lgcode", lgaCode).list();
            tx.commit();
            session.close();
        }
         catch (Exception ex)
         {
             session.close();
            ex.printStackTrace();
         }
        
        return list;
}
public List getDistinctRecordsByPartner(String partnerCode) throws Exception
{
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode from HouseholdEnrollment hhe where hhe.partnerCode=:pcode").setString("pcode", partnerCode).list();
            tx.commit();
            session.close();
        }
         catch (Exception ex)
         {
             session.close();
            ex.printStackTrace();
         }
        
        return list;
}
public List getDistinctStateCodesByPartner(String partnerCode) throws Exception
{
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct hhe.stateCode from HouseholdEnrollment hhe where hhe.partnerCode=:pcode").setString("pcode", partnerCode).list();
            tx.commit();
            session.close();
        }
         catch (Exception ex)
         {
             session.close();
            ex.printStackTrace();
         }
        
        return list;
}
public List getDistinctPartnerCodesByState(String stateCode) throws Exception
{
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct hhe.partnerCode from HouseholdEnrollment hhe where hhe.stateCode=:stc").setString("stc", stateCode).list();
            tx.commit();
            session.close();
        }
         catch (Exception ex)
         {
             session.close();
            ex.printStackTrace();
         }
        
        return list;
}
public List getDistinctPartnerCodesByCommunity(String communityCode) throws Exception
{
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct hhe.partnerCode from HouseholdEnrollment hhe where hhe.communityCode=:cc").setString("cc", communityCode).list();
            tx.commit();
            session.close();
        }
         catch (Exception ex)
         {
             session.close();
            ex.printStackTrace();
         }
        
        return list;
}
public List getDistinctPartnerCodes() throws Exception
{
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct hhe.partnerCode from HouseholdEnrollment hhe").list();
            tx.commit();
            session.close();
        }
         catch (Exception ex)
         {
             session.close();
            ex.printStackTrace();
         }
        
        return list;
}
public HouseholdEnrollment getHouseholdEnrollmentByUniqueIdAndHnvaId(String hhUniqueId,int hhvaId) throws Exception
{
        List list = null;
        HouseholdEnrollment hhe = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdEnrollment hhe where hhe.hhUniqueId=:uid and hhe.hhvaId=:hId").setString("uid", hhUniqueId).setInteger("hId", hhvaId).list();
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
            hhe=((HouseholdEnrollment)list.get(0));
        }
        return hhe;
}
public int getHVAThematicAreaCount(String hvaProperty,int score,String queryCriteria) throws Exception
{
    List list = new ArrayList();
    int count=0;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select count(distinct hhe.hhUniqueId) from HouseholdEnrollment hhe where hhe."+hvaProperty+"= "+score+" "+queryCriteria).list();
        tx.commit();
        session.close();
        //System.err.println("select count(distinct hvi.hhUniqueId) from HouseholdVulnerabilityIndex hvi  where hvi."+hvaProperty+"= "+score+" "+queryCriteria);
    }
     catch (HibernateException he)
     {
         session.close();
        throw new Exception(he);
     }
    if(list !=null && !list.isEmpty())
    count=Integer.parseInt(list.get(0).toString());
     return count;
}
public int getNumberOfHouseholdsByBaselineVulnerabilityStatus(String additionalQuery,boolean currentlyEnrolled,int startValue, int endValue) throws Exception
{
    String currentlyEnrolledQuery="";
    if(currentlyEnrolled)
    currentlyEnrolledQuery=util.getHouseholdWithdrawnFromProgramQuery("No");
    List list = new ArrayList();
    int numberOfHouseholds=0;
        try
        {
            String query="select count(distinct hhe.hhUniqueId) from HouseholdEnrollment hhe where hhe.baselineAssessmentScore >="+startValue+" and hhe.baselineAssessmentScore<="+endValue+additionalQuery+currentlyEnrolledQuery+")";
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        if(list !=null && !list.isEmpty())
        {
            numberOfHouseholds=Integer.parseInt(list.get(0).toString());
        }
        return numberOfHouseholds;
}
public List getListOfHouseholdsByBaselineVulnerabilityStatus(String additionalQuery,boolean currentlyEnrolled,int startValue, int endValue) throws Exception
{
    String currentlyEnrolledQuery="";
    if(currentlyEnrolled)
    currentlyEnrolledQuery=util.getHouseholdWithdrawnFromProgramQuery("No");
    List list = new ArrayList();
    
        try
        {
            String query="from HouseholdEnrollment hhe where hhe.baselineAssessmentScore >="+startValue+" and hhe.baselineAssessmentScore<="+endValue+additionalQuery+currentlyEnrolledQuery+")";
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
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
public int getNumberOfHouseholdsWithBaselineAssessement(String additionalQuery,boolean currentlyEnrolled) throws Exception
{
    String currentlyEnrolledQuery="";
    if(currentlyEnrolled)
    currentlyEnrolledQuery=util.getHouseholdWithdrawnFromProgramQuery("No");
    List list = new ArrayList();
    int numberOfHouseholds=0;
        try
        {
            String query="select count(distinct hhe.hhUniqueId) from HouseholdEnrollment hhe where hhe.baselineAssessmentScore >"+NomisConstant.NOTVULNERABLE_ENDVALUE+additionalQuery+currentlyEnrolledQuery+")";
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            session.close();
        }
        catch(Exception ex)
        {
            session.close();
            ex.printStackTrace();
        }
        if(list !=null && !list.isEmpty())
        {
            numberOfHouseholds=Integer.parseInt(list.get(0).toString());
        }
        return numberOfHouseholds;
}
public List getListOfHouseholdsWithBaselineAssessement(String additionalQuery,boolean currentlyEnrolled) throws Exception
{
    String currentlyEnrolledQuery="";
    if(currentlyEnrolled)
    currentlyEnrolledQuery=util.getHouseholdWithdrawnFromProgramQuery("No");
    List list = new ArrayList();
    
        try
        {
            String query="from HouseholdEnrollment hhe where hhe.baselineAssessmentScore >"+NomisConstant.NOTVULNERABLE_ENDVALUE+additionalQuery+currentlyEnrolledQuery+")";
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
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
//This method is used for DQA
public List getHouseholdsWithIncorrectDateOfAssessment() throws Exception
{
    List list=null;
    
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query=util.getHouseholdOvcQueryPart()+" and hhe.dateOfAssessment > ovc.dateEnrollment order by ovc.dateEnrollment asc ";
        //System.err.println(query);
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
public List getDistinctYearOfAssessment() throws Exception
{
    List list=null;
    
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select distinct YEAR(hhe.dateOfAssessment) from HouseholdEnrollment hhe order by YEAR(hhe.dateOfAssessment) asc";
        //System.err.println(query);
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
public List getNumberOfHouseholdsNewlyEnrolledPerMonthByCBO(String indicatorName,String stateCode, String startDate, String endDate,boolean currentlyEnrolled) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String currentlyEnrolledQuery="";
    if(currentlyEnrolled)
    currentlyEnrolledQuery=util.getHouseholdWithdrawnFromProgramQuery("No");
    String additionalQuery=util.getHhePeriodCriteria(startDate, endDate);
    mainList.add(indicatorName);
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,hhe.hhGender,hhe.currentAge, count(distinct hhe.hhUniqueId) from HouseholdEnrollment hhe where hhe.stateCode=:state"+additionalQuery+currentlyEnrolledQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,hhe.hhGender,hhe.currentAge";
        //System.err.println(query);
        list = session.createQuery(query).setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public List getNumberOfHouseholdsEnrolledPerMonthByCBO(String indicatorName,String stateCode, boolean currentlyEnrolled) throws Exception
{
    List list=null;
    List mainList=new ArrayList();
    String currentlyEnrolledQuery="";
    if(currentlyEnrolled)
    currentlyEnrolledQuery=util.getHouseholdWithdrawnFromProgramQuery("No");
    //String additionalQuery=util.getEnrollmentDateQuery(startDate, endDate);
    mainList.add(indicatorName);
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        String query="select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,hhe.hhGender,hhe.currentAge, count(distinct hhe.hhUniqueId) from HouseholdEnrollment hhe where hhe.stateCode=:state"+currentlyEnrolledQuery+" group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,hhe.hhGender,hhe.currentAge";
        System.err.println(query);
        list = session.createQuery(query).setString("state", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (HibernateException he)
    {
        session.close();
        throw new Exception(he);
    }
    mainList.addAll(list);
    return mainList;
}
public List getListOfHouseholdsByHhuniqueIdPart(String stateLgaCboCode) throws Exception
{
    List list=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from HouseholdEnrollment hhe where hhe.hhUniqueId like '%"+stateLgaCboCode+"%'").list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return list;
}
public List correctLgaCodesInHouseholdEnrollmentRecords() throws Exception
{
    List list=null;
    try
    {
        String communityCode=null;
        List communityCodes=this.getDistinctCommunityCodes();
        if(communityCodes !=null)
        {
            HouseholdEnrollment hhe=null;
            for(int i=0; i<communityCodes.size(); i++)
            {
                if(communityCodes.get(i) !=null)
                communityCode=communityCodes.get(i).toString();
                else
                communityCode=(String)communityCodes.get(i);
                List households=getListOfHouseholdEnrollmentByCommunityCode(communityCode);
                if(households !=null)
                {
                    for(int j=0; j<households.size(); j++)
                    {
                        hhe=(HouseholdEnrollment)households.get(j);
                        System.err.println("hhe old lga code is "+hhe.getLgaCode());
                        String hhUniqueId=hhe.getHhUniqueId();
                        if(hhUniqueId !=null && hhUniqueId.indexOf("/") !=-1)
                        {
                            String[] idarray=hhUniqueId.split("/");
                            if(idarray !=null && idarray.length > 1)
                            {
                                hhe.setLgaCode(idarray[1]);
                                updateHouseholdEnrollment(hhe);
                                System.err.println("hhe new lga code is "+hhe.getLgaCode());
                            }
                        }
                    }
                }
            }
        }
    }
    catch (Exception ex)
    {
        ex.printStackTrace();
    }
    return list;
}
public List getDistinctLgaCodesByStateCode(String stateCode) throws Exception
{
    List list=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select distinct hhe.lgaCode from HouseholdEnrollment hhe where hhe.stateCode=:stc").setString("stc", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return list;
}
public List getDistinctLgaCodesByStateAndPartnerCodes(String stateCode,String partnerCode) throws Exception
{
    List list=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select distinct hhe.lgaCode from HouseholdEnrollment hhe where hhe.stateCode=:stc and hhe.partnerCode=:pc").setString("stc", stateCode).setString("pc", partnerCode).list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return list;
}
public List getDistinctLgaAndCboAndCommunityCodesFromHouseholdEnrollmentRecords(String stateCode) throws Exception
{
    List list=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select distinct hhe.lgaCode, hhe.orgCode,hhe.communityCode from HouseholdEnrollment hhe where hhe.stateCode=:stc").setString("stc", stateCode).list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return list;
}
public List getDistinctStateCodesFromHouseholdEnrollmentRecords(String lgaCode) throws Exception
{
    List list=null;
    if(lgaCode !=null)
    lgaCode=lgaCode.trim();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("select distinct hhe.stateCode from HouseholdEnrollment hhe where TRIM(hhe.lgaCode)=:lga").setString("lga", lgaCode).list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    return list;
}
public List searchHouseholdsBySerialNumber(String serialNo) throws Exception
{
    List list=null;
    List listOfHouseholds=new ArrayList();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from HouseholdEnrollment hhe where hhe.hhUniqueId like '/"+serialNo+"%'").list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    if(list !=null)
    {
        for(Object s:list)
        {
            Object[] obj=(Object[])s;
            listOfHouseholds.add(obj[0]);
        }
    }
    return listOfHouseholds;
}
public List getHouseholdsWithYesWithdrawalStatus() throws Exception
{
    List list=null;
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from HouseholdEnrollment hhe where hhe.withdrawnFromProgram='Yes'").list();
        tx.commit();
        session.close(); 
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
   return list;
}
public List getActiveHouseholdsFromWithdrawalRecords() throws Exception
{
    List list=null;
    List listOfHouseholds=new ArrayList();
    try
    {
        session = HibernateUtil.getSession();
        tx = session.beginTransaction();
        list = session.createQuery("from HouseholdEnrollment hhe, OvcWithdrawal withdrawal where hhe.hhUniqueId=withdrawal.ovcId and hhe.withdrawnFromProgram='No'").list();
        tx.commit();
        session.close();
    }
    catch (Exception ex)
    {
        session.close();
        ex.printStackTrace();
    }
    if(list !=null)
    {
        for(Object s:list)
        {
            Object[] obj=(Object[])s;
            listOfHouseholds.add(obj[0]);
        }
    }
    return listOfHouseholds;
}
    public int updateHouseholdHeadGender() throws Exception
    {
        int count=0;
        List list = new ArrayList();
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdEnrollment hhe where hhe.hhGender is null or UPPER(hhe.hhGender)='NONE'").list();
            tx.commit();
            session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
        if(list !=null)
        {
            for(int i=0; i<list.size(); i++)
            {
                HouseholdEnrollment hhe=(HouseholdEnrollment)list.get(i);
                hhe.setHhGender("Male");
                updateHouseholdEnrollment(hhe);
                count++;
                System.err.println(count+" household records updated with gender");
            }
        }
         return count;
    }
    public int updateHouseholdHeadNameWithCaregiverName() throws Exception
    {
        int count=0;
        List list = new ArrayList();
        try 
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdEnrollment hhe where (hhe.hhSurname is null or UPPER(hhe.hhSurname)='NONE' or hhe.hhSurname='' or hhe.hhSurname=' ' or hhe.hhSurname='  ' or hhe.hhSurname='   ') or (hhe.hhFirstname is null or UPPER(hhe.hhFirstname)='NONE' or hhe.hhSurname='' or hhe.hhSurname=' ' or hhe.hhSurname='  ' or hhe.hhSurname='   ')").list();
            tx.commit();
            session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
        if(list !=null)
        {
            List cgiverList=null;//new ArrayList();
            CaregiverDao cdao=new CaregiverDaoImpl();
            AppUtility appUtil=new AppUtility();
            for(int i=0; i<list.size(); i++)
            {
                HouseholdEnrollment hhe=(HouseholdEnrollment)list.get(i);
                cgiverList=cdao.getListOfCaregiversFromSameHousehold(hhe.getHhUniqueId());
                if(cgiverList !=null && !cgiverList.isEmpty())
                {
                    Caregiver cgiver=(Caregiver)cgiverList.get(0);
                    if(cgiver.getCaregiverFirstname() !=null && !cgiver.getCaregiverFirstname().equalsIgnoreCase("Unknown"))
                    {
                        if(hhe.getHhFirstname()==null || hhe.getHhFirstname().equalsIgnoreCase("none") || !appUtil.isString(hhe.getHhFirstname()))
                        hhe.setHhFirstname(cgiver.getCaregiverFirstname());
                        //System.err.println(" hhe.getHhFirstname after update is "+hhe.getHhFirstname());
                    }
                    if(cgiver.getCaregiverLastName() !=null &&  !cgiver.getCaregiverLastName().equalsIgnoreCase("Unknown"))
                    {
                        if(hhe.getHhSurname()==null || hhe.getHhSurname().equalsIgnoreCase("none") || !appUtil.isString(hhe.getHhSurname()))
                        hhe.setHhSurname(cgiver.getCaregiverLastName());
                        //System.err.println("hhe.getHhSurname after update is "+hhe.getHhSurname());
                    }
                    if(hhe.getHhGender()==null || hhe.getHhGender().equalsIgnoreCase("none"))
                    {
                        hhe.setHhGender("Male");
                    }
                    if((hhe.getHhFirstname() !=null && !hhe.getHhFirstname().equalsIgnoreCase("none")) || (hhe.getHhSurname() !=null && !hhe.getHhSurname().equalsIgnoreCase("none")))
                    {
                        hhe.setHhAge(cgiver.getCaregiverAge());
                        hhe.setHhGender(cgiver.getCaregiverGender());
                        
                    }
                    updateHouseholdEnrollment(hhe);
                    count++;
                    System.err.println(count+" household records updated");
                }
            }
            updateHouseholdHeadGender();
        }
         return count;
    }
    public List getDistinctCommunityCodesPerState(String stateCode) throws Exception
    {
        List list = new ArrayList();
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select distinct hhe.communityCode from HouseholdEnrollment hhe where hhe.stateCode=:st").setString("st", stateCode).list();
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
    public List getDistinctCommunityCodes() throws Exception
    {
        List list = new ArrayList();
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select distinct hhe.communityCode from HouseholdEnrollment hhe").list();
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
    public List getDistinctCommunityCodes(String stateCode,String lgaCode,String orgCode) throws Exception
    {
        List list = new ArrayList();
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select distinct hhe.communityCode from HouseholdEnrollment hhe where hhe.stateCode=:st and hhe.lgaCode=:lg and hhe.orgCode=:oc").setString("st", stateCode).setString("lg", lgaCode).setString("oc", orgCode).list();
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
    public List getDistinctOrgCodesPerLga(String stateCode,String lgaCode) throws Exception
    {
        List list = new ArrayList();
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select distinct hhe.orgCode from HouseholdEnrollment hhe where hhe.stateCode=:st and hhe.lgaCode=:lg").setString("st", stateCode).setString("lg", lgaCode).list();
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
    public List getDistinctOrgCodes() throws Exception
    {
        List list = new ArrayList();
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select distinct hhe.orgCode from HouseholdEnrollment hhe").list();
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
    public List getDistinctLgaCodes() throws Exception
    {
        List list = new ArrayList();
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select distinct hhe.lgaCode from HouseholdEnrollment hhe").list();
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
    public List getDistinctStateCodes() throws Exception
    {
        List list = new ArrayList();
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select distinct hhe.stateCode from HouseholdEnrollment hhe").list();
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
    public String updateHheCurrentAge() throws Exception
    {
        String msg=" ";
        DaoUtil util=new DaoUtil();
        try
        {
                List orgList=getDistinctOrgCodes();
                HouseholdEnrollment hvi=null;
                if(orgList !=null && !orgList.isEmpty())
                {
                    String orgCode=null;
                    List list=null;
                    for(Object s:orgList)
                    {
                        orgCode=(String)s;
                        if(orgCode !=null)
                        {
                            list=getListOfHouseholdEnrollmentByOrgCode(orgCode);
                            for(Object obj:list)
                            {
                                hvi=(HouseholdEnrollment)obj;
                                hvi.setCurrentAge(util.getCurrentAge(hvi.getDateOfAssessment(), hvi.getHhAge(),"Year"));
                                updateHouseholdEnrollment(hvi);
                            }
                        }
                    }
                    msg="Caregiver age updated successfully";
                }
                else
                {
                    msg="Could not update caregiver current age, No record found";
                }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return msg;
    }
    public List getListOfHouseholdsWithoutPartner(String additionalOrgQuery) throws Exception
    {
        List list = new ArrayList();
        String sql="from HouseholdEnrollment hhe where hhe.partnerCode is null "+additionalOrgQuery+" order by hhe.hhUniqueId asc";

         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        Query query = session.createQuery(sql);

        list = query.list();
        tx.commit();
        session.close();
        } catch (HibernateException he)
        {
            session.close();
            throw new Exception(he);
        }
        return list;
    }
    public List getListOfHouseholdEnrollmentWithIncompleteVulnerabilityScoreByCommunityCode(String communityCode,int baselineAssessmentScore) throws Exception
    {
        List list = null;
        List hheList = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query="from HouseholdEnrollment hhe where hhe.communityCode=:comm_code and hhe.baselineAssessmentScore <"+baselineAssessmentScore+" order by hhe.hhUniqueId";
        list = session.createQuery(query).setString("comm_code", communityCode).list();
        tx.commit();
        session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
        for(Object s:list)
        {
            hheList.add((HouseholdEnrollment)s);
        }
        return hheList;
    }
    public List getListOfHouseholdEnrollmentByCommunityCode(String communityCode) throws Exception
    {
        List list = null;
        List hheList = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query="from HouseholdEnrollment hhe where hhe.communityCode=:comm_code order by hhe.hhUniqueId";
        list = session.createQuery(query).setString("comm_code", communityCode).list();
        tx.commit();
        session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
        for(Object s:list)
        {
            hheList.add((HouseholdEnrollment)s);
        }
        return hheList;
    }
    public List getDistinctHouseholdUniqueIdByCommunityCode(String communityCode) throws Exception
    {
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query="select distinct hhe.hhUniqueId from HouseholdEnrollment hhe where hhe.communityCode=:comm_code order by hhe.hhUniqueId";
            list = session.createQuery(query).setString("comm_code", communityCode).list();
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
    public List getListOfHouseholdEnrollmentByOrgCode(String orgCode) throws Exception
    {
        List list = null;
        List hheList = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query="from HouseholdEnrollment hhe where hhe.orgCode=:org_code order by hhe.hhUniqueId";
        list = session.createQuery(query).setString("org_code", orgCode).list();
        tx.commit();
        session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
        for(Object s:list)
        {
            hheList.add((HouseholdEnrollment)s);
        }
        return hheList;
    }
    public List getListOfHouseholdEnrollment(String additionalQuery,String sortOrder) throws Exception
    {
        String sortQuery=" order by hhe.hhUniqueId, hhe.hhSurname,hhe.hhFirstname";
        if(sortOrder !=null)
        {
            if(sortOrder.equalsIgnoreCase("hhFirstname"))
            sortQuery="order by hhe.hhSurname, hhe.hhFirstname, hhe.hhUniqueId";
            else if(sortOrder.equalsIgnoreCase("active"))
            sortQuery="order by hhe.withdrawnFromProgram,hhe.hhSurname, hhe.hhFirstname, hhe.hhUniqueId";
        }
        List list = null;
        List hheList = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            String query="from HouseholdEnrollment hhe where hhe.orgCode is not null "+additionalQuery+sortQuery;
        list = session.createQuery(query).list();
        tx.commit();
        session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
        for(Object s:list)
        {
            hheList.add((HouseholdEnrollment)s);
        }
        return hheList;
    }
    public List getListOfHouseholdsEnrolledWithoutPartner(String additionalQuery) throws Exception
    {
        List list = new ArrayList();
        List hviList = new ArrayList();
         try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("from HouseholdEnrollment hhe where hhe.partnerCode is null "+additionalQuery+" order by hvi.hhUniqueId").list();
        tx.commit();
        session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
        for(Object s:list)
        {
            hviList.add((HouseholdEnrollment)s);
        }
        return hviList;
    }
    public int getNoOfHouseholdsEnrolled() throws Exception
    {
        List list = null;
        int count=0;
        try
        {
            
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count(distinct hhe.hhUniqueId) from HouseholdEnrollment hhe").list();
        tx.commit();
        session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
        if(list !=null && !list.isEmpty())
        {
            count=(Integer.parseInt(list.get(0).toString()));
        }
        return count;

    }
    public int getNumberOfHouseholdsEnrolled(String additionalQuery) throws Exception
    {
        List list = null;
        int count=0;
        try
        {
            System.err.println("select count(distinct hhe.hhUniqueId) from HouseholdEnrollment hhe where hhe.hhUniqueId is not null "+additionalQuery);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count(distinct hhe.hhUniqueId) from HouseholdEnrollment hhe where hhe.hhUniqueId is not null "+additionalQuery).list();
            tx.commit();
            session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
        if(list !=null && !list.isEmpty())
        {
            count=(Integer.parseInt(list.get(0).toString()));
        }
        return count;
    }
    public int getNumberOfHouseholdsCurrentlyEnrolled(String additionalQuery) throws Exception
    {
        List list = null;
        int count=0;
        try
        {
            System.err.println("select distinct hhe.hhUniqueId from HouseholdEnrollment hhe where hhe.hhUniqueId is not null "+additionalQuery);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct hhe.hhUniqueId from HouseholdEnrollment hhe where hhe.hhUniqueId is not null "+additionalQuery).list();
            tx.commit();
            session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
        if(list !=null && !list.isEmpty())
        {
            OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
            List listOfHhNotWithdrawn=wdao.getListOfIdsNotWithdrawn(list);
            if(listOfHhNotWithdrawn !=null)
            count=listOfHhNotWithdrawn.size();
        }
        return count;
    }
    public List getListOfHouseholdsCurrentlyEnrolled(String additionalQuery) throws Exception
    {
        List list = null;
        try
        {
            String query="from HouseholdEnrollment hhe where hhe.hhUniqueId is not null "+additionalQuery+util.getHouseholdWithdrawnFromProgramQuery("No");
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
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
    public List getListOfHouseholdIdsCurrentlyEnrolled(String additionalQuery) throws Exception
    {
        List list = null;
        List listOfHouseholds = new ArrayList();
        try
        {
            System.err.println("select distinct hhe.hhUniqueId from HouseholdEnrollment hhe where hhe.hhUniqueId is not null "+additionalQuery);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct hhe.hhUniqueId from HouseholdEnrollment hhe where hhe.hhUniqueId is not null "+additionalQuery).list();
            tx.commit();
            session.close();
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
        if(list !=null && !list.isEmpty())
        {
            OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
            List listOfHhNotWithdrawn=wdao.getListOfIdsNotWithdrawn(list);
            if(listOfHhNotWithdrawn !=null)
            {
                for(int i=0; i<listOfHhNotWithdrawn.size(); i++)
                {
                    listOfHouseholds.add(getHouseholdEnrollment(listOfHhNotWithdrawn.get(i).toString()));
                }
            }
            
        }
        return listOfHouseholds;
    }
    public HouseholdEnrollment getHouseholdEnrollment(String hhUniqueId) throws Exception
    {
        HouseholdEnrollment hhe = getHouseholdEnrollmentByEnrollmentId(hhUniqueId);
        try
        {
            if(hhe==null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                List list = session.createQuery("from HouseholdEnrollment hhe where hhe.hhUniqueId=:uid").setString("uid", hhUniqueId).list();
                tx.commit();
                session.close();
                if(list !=null && !list.isEmpty())
                {
                    hhe=((HouseholdEnrollment)list.get(0));
                }
            }
        }
         catch (Exception ex)
         {
             session.close();
            throw new Exception(ex);
         }
        
        return hhe;
    }
    public HouseholdEnrollment getHouseholdEnrollmentByEnrollmentId(String hhEnrollmentId) throws Exception
    {
        List list = null;
        HouseholdEnrollment hhe = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from HouseholdEnrollment hhe where hhe.hhEnrollmentId=:heId").setString("heId", hhEnrollmentId).list();
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
            hhe=((HouseholdEnrollment)list.get(0));
        }
        return hhe;
    }
    public HouseholdEnrollment getHouseholdEnrollmentByNameAndUniqueId(String hhUniqueId,String hhFirstname,String hhSurname) throws Exception
    {
        HouseholdEnrollment hhe=null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            List list = session.createQuery("from HouseholdEnrollment hhe where hhe.hhUniqueId=:uid and hhe.hhFirstname=:fname and hhe.hhSurname=:sname")
                .setString("uid", hhUniqueId).setString("fname", hhFirstname).setString("sname", hhSurname).list();
            tx.commit();
            session.close();
            if(list !=null && !list.isEmpty())
            {
                hhe=((HouseholdEnrollment)list.get(0));
            }
        }
         catch (HibernateException he)
         {
             session.close();
            throw new Exception(he);
         }
        
        return hhe;
    }
    public List getHVILgaList(String stateId) throws Exception
    {
        List list = new ArrayList();
        if(stateId ==null || stateId.equalsIgnoreCase("All"))
        return list;
        String additionalQuery=" where hhe.stateCode ='"+stateId+"'";
        //if(stateId ==null || stateId.equalsIgnoreCase("All"))
        //additionalQuery=" ";
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select distinct hhe.lgaCode from HouseholdEnrollment hhe"+additionalQuery+"order by hhe.lgaCode").list();
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
    public List getHVIOrgListByStateCode(String stateCode) throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct hhe.orgCode from HouseholdEnrollment hhe where hhe.stateCode =:stcode order by hhe.orgCode").setString("stcode", stateCode).list();
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
    public List getHVIOrgList(String lgaId) throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct hhe.orgCode from HouseholdEnrollment hhe where hhe.lgaCode =:lga_code order by hhe.orgCode").setString("lga_code", lgaId).list();
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
    public List getHVIStateList() throws Exception
    {
        List list = new ArrayList();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct hhe.stateCode from HouseholdEnrollment hhe  order by hhe.stateCode").list();
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
    public List getHVIWardList(String cboId) throws Exception
    {
        List list = new ArrayList();
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
        list = session.createQuery("select distinct hhe.communityCode from HouseholdEnrollment hhe  where hhe.orgCode =:org_code order by hhe.communityCode").setString("org_code", cboId).list();
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
    public void saveHouseholdEnrollment(HouseholdEnrollment hhe) throws Exception
    {
        try
        {
            if(hhe.getHhFirstname() !=null && hhe.getHhFirstname().length()>24)
            hhe.setHhFirstname(hhe.getHhFirstname().substring(0,23));
            if(hhe.getHhSurname() !=null && hhe.getHhSurname().length()>24)
            hhe.setHhSurname(hhe.getHhSurname().substring(0,23));
            if(hhe.getHhUniqueId() !=null && hhe.getHhUniqueId().indexOf("/")!=-1 && this.getHouseholdEnrollment(hhe.getHhUniqueId())==null)
            {
                AppUtility appUtil=new AppUtility();
                String serialNumber=hhe.getHhUniqueId().substring(hhe.getHhUniqueId().lastIndexOf("/")+1, hhe.getHhUniqueId().length());

                if(appUtil.isNumberGreaterThanZero(serialNumber))
                {
                    if(hhe.getHhEnrollmentId()==null)
                    hhe.setHhEnrollmentId(hhe.getHhUniqueId());
                    hhe.setBaselineAssessmentScore(getHouseholdVulnerabilityScore(hhe));
                    System.err.println("hhe.getBaselineAssessmentScore() is "+hhe.getBaselineAssessmentScore()+" and hhe.getBaselineVulnerabilityScore() is "+hhe.getBaselineVulnerabilityScore());
                    System.err.println("hhe.getHhAge() is "+hhe.getHhAge()+" and hhe.getHhvaId() is "+hhe.getHhvaId());
                    if(hhe.getBaselineHivStatus()==null)
                    hhe.setBaselineHivStatus(NomisConstant.HIV_UNKNOWN);
                    hhe=getHouseholdWithUpdatedWithdrawalStatus(hhe);
                    session = HibernateUtil.getSession(); 
                    tx=session.beginTransaction();
                    session.save(hhe);
                    tx.commit();
                    session.close();
                    saveHouseholdHeadHIVStatus(hhe);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
           //System.err.println("Exception message: "+ex.getMessage());
        }
    }
    public void updateHouseholdEnrollment(HouseholdEnrollment hhe) throws Exception
    {
        try
        {
            if(hhe !=null)
            {
                HouseholdEnrollment hhe2=getHouseholdEnrollment(hhe.getHhUniqueId());
                if(hhe2 !=null)
                {
                   if(DateManager.compareDates(hhe2.getDateOfEntry(), hhe.getDateOfEntry())) 
                   {
                       if(hhe.getHhEnrollmentId()==null)
                       {
                           if(hhe2.getHhEnrollmentId() !=null)
                           hhe.setHhEnrollmentId(hhe2.getHhEnrollmentId());
                           else
                           hhe.setHhEnrollmentId(hhe.getHhUniqueId());
                       }
                        hhe.setBaselineAssessmentScore(getHouseholdVulnerabilityScore(hhe));
                        HivStatusUpdate defaultHivStatus=null;
                        if(hhe.getBaselineHivStatus()==null)
                        {
                            //If no HIV status is recorded, get the active one or create a new unknown HIV status
                            defaultHivStatus=getDefaultHivStatus(hhe.getHhUniqueId(),hhe.getDateOfAssessment(),hhe.getPointOfService());
                            hhe.setBaselineHivStatus(defaultHivStatus.getHivStatus());
                        }
                        hhe=updateHouseholdEnrollmentWithRevisedData(hhe,hhe2);
                        hhe=getHouseholdWithUpdatedWithdrawalStatus(hhe);
                        session = HibernateUtil.getSession();
                        tx=session.beginTransaction();
                        session.update(hhe);
                        tx.commit();
                        session.close();
                        //If an existing active HIV status is used, set the date before you update
                        if(defaultHivStatus !=null)
                        hhe.setDateOfAssessment(defaultHivStatus.getDateOfCurrentStatus());
                        saveHouseholdHeadHIVStatus(hhe);
                   }
                }
            }
        }
        catch(Exception ex)
        {
            //System.err.println("Exception message: "+ex.getMessage());
            ex.printStackTrace();
        }
    }
    public void markedForDelete(HouseholdEnrollment hhe) throws Exception
    {
        try
        {
            hhe.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
            updateHouseholdEnrollment(hhe);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private HouseholdEnrollment updateHouseholdEnrollmentWithRevisedData(HouseholdEnrollment hhe,HouseholdEnrollment existingHhe) throws Exception
    {
        try
        {
            if(hhe !=null)
            {
                HouseholdEnrollment hhe2=getHouseholdEnrollment(hhe.getHhUniqueId());
                if(hhe.getChildEducationLevel()==0)
                    hhe.setChildEducationLevel(hhe2.getChildEducationLevel());
                if(hhe.getHhEducationLevel()==0)
                hhe.setHhEducationLevel(hhe2.getHhEducationLevel());
                if(hhe.getHhEducationLevel()==0)
                hhe.setHhEducationLevel(hhe2.getEducationLevel()); 
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return hhe;
    }
    public void deleteHouseholdEnrollmentOnly(HouseholdEnrollment hhe) throws Exception
    {
        session = HibernateUtil.getSession();
        tx=session.beginTransaction();
        session.delete(hhe);
        tx.commit();
        session.close();
        util.saveDeletedRecord(hhe.getHhUniqueId(),hhe.getNewHhUniqueId(), "householdEnrollment", hhe.getDateOfAssessment());
        //deleteHouseholdMembers(hhe.getHhUniqueId());
    }
    public void deleteHouseholdEnrollment(HouseholdEnrollment hhe) throws Exception
    {
        session = HibernateUtil.getSession();
        tx=session.beginTransaction();
        session.delete(hhe);
        tx.commit();
        session.close();
        util.saveDeletedRecord(hhe.getHhUniqueId(),hhe.getNewHhUniqueId(), "householdEnrollment", hhe.getDateOfAssessment());
        deleteHouseholdMembers(hhe.getHhUniqueId());
    }
    public void deleteHouseholdMembers(String hhUniqueId) throws Exception
    {
        HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
        hvadao.deleteAllAssessmentPerHousehold(hhUniqueId);
        HouseholdFollowupAssessmentDao hhfdao=new HouseholdFollowupAssessmentDaoImpl();
        hhfdao.deleteAllAssessmentPerHousehold(hhUniqueId);
        OvcDao ovcdao=new OvcDaoImpl();
        ovcdao.deleteAllOvcInHousehold(hhUniqueId);
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        cgiverdao.deleteAllCaregiversInHousehold(hhUniqueId);
        HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
        hhsdao.deleteAllHouseholdServices(hhUniqueId);
    }
    public void saveHouseholdHeadHIVStatus(HouseholdEnrollment hhe) throws Exception
    {
        System.err.println("hhe.getBaselineHivStatus() is "+hhe.getBaselineHivStatus());
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        HivStatusUpdate hsu=new HivStatusUpdate();
        String dateOfCurrentHivStatus=hhe.getDateOfAssessment();
        //if(dateOfCurrentHivStatus==null)
        //dateOfCurrentHivStatus=hhe.getDateOfAssessment();
        hsu.setClientEnrolledInCare(hhe.getEnrolledInCare());
        hsu.setEnrolledOnART(hhe.getEnrolledOnART());
        hsu.setOrganizationClientIsReferred(hhe.getFacilityId());
        hsu.setClientId(hhe.getHhUniqueId());
        hsu.setClientType(NomisConstant.HOUSEHOLD_TYPE);
        hsu.setDateModified(DateManager.getCurrentDate());
        hsu.setDateOfCurrentStatus(dateOfCurrentHivStatus);
        hsu.setHivStatus(HivRecordsManager.getHivStatusScode(hhe.getBaselineHivStatus()));
        hsu.setRecordStatus("active");
        hsu.setPointOfUpdate(hhe.getPointOfService());
        hsudao.saveHivStatusUpdate(hsu);
    }
    public static HivStatusUpdate getDefaultHivStatus(String clientId,String enrollmentDate,String pointOfService)
    {
        HivStatus hivStatus=new HivStatus();
        HivStatusUpdate defaultHivStatus=hivStatus.getDefaultHivStatus(clientId,enrollmentDate,pointOfService);
        return defaultHivStatus;
    }
    public String generateNewHhUniqueId(String existingHhUniqueId) throws Exception
    {
        int snumber=0;
        String newId=existingHhUniqueId;
        String paddedSnumber=null;
        try
        {
            if(existingHhUniqueId !=null)
            {
                String[] hhIdParts=existingHhUniqueId.split("/");
                if(hhIdParts !=null && hhIdParts.length >2)
                {
                    snumber=Integer.parseInt(hhIdParts[3]);
                    snumber++;
                    paddedSnumber=padSnumber(snumber+"");
                    newId=hhIdParts[0]+"/"+hhIdParts[1]+"/"+hhIdParts[2]+"/"+paddedSnumber;
                    HouseholdEnrollment hhe=getHouseholdEnrollment(newId);
                    if(hhe !=null)
                    newId=generateNewHhUniqueId(newId);

                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return newId;
    }
    public String padSnumber(String snumber)
    {
        if(snumber==null || snumber.length()==5)
        return snumber;
        snumber=snumber.trim();
        if(snumber.length()==1)
        snumber="0000"+snumber;
        else if(snumber.length()==2)
        snumber="000"+snumber;
        else if(snumber.length()==3)
        snumber="00"+snumber;
        else if(snumber.length()==4)
        snumber="0"+snumber;
        return snumber;
    }
    public List getHouseholdMembers(String hhUniqueId) throws Exception
    {
        List mainList=new ArrayList();
        List hheList=new ArrayList();
        List cgiverMainList=new ArrayList();
        List ovcMainList=new ArrayList();
        HouseholdEnrollment hhe=getHouseholdEnrollment(hhUniqueId);
        if(hhe !=null)
        {
            hheList.add(hhe);
            CaregiverDao cgiverdao=new CaregiverDaoImpl();
            OvcDao ovcdao=new OvcDaoImpl();
            List cgiverList=cgiverdao.getListOfCaregiversFromSameHousehold(hhUniqueId);
            if(cgiverList !=null)
            cgiverMainList.addAll(cgiverList);
            List ovcList=ovcdao.getOvcListPerHousehold(hhUniqueId);
            if(ovcList !=null)
            ovcMainList.addAll(ovcList);
        }
        mainList.add(hheList);
        mainList.add(cgiverMainList);
        mainList.add(ovcMainList);
        return mainList;
    }
    public HouseholdEnrollment getHouseholdEnrollmentInstance(String hhUniqueId)
    {
        HouseholdEnrollment hhe=null;
        try
        {
            hhe=getHouseholdEnrollment(hhUniqueId);
        }
        catch(Exception ex)
        {
            NomisLogManager.logStackTrace(ex);//ex.printStackTrace();
        }
        return hhe;
    }
    public void deleteHouseholdsByStateLgaCBO(String stateLgaCboCode) throws Exception
    {
        System.err.println("stateLgaCboCode in deleteHouseholdsByStateLgaCBO is "+stateLgaCboCode);
        List list=getListOfHouseholdsByHhuniqueIdPart(stateLgaCboCode);
        if(list !=null && !list.isEmpty())
        {
            for(Object obj:list)
            {
                HouseholdEnrollment hhe=(HouseholdEnrollment)obj;
                deleteHouseholdEnrollment(hhe);
                System.err.println("Household with Id "+hhe.getHhUniqueId()+" deleted in deleteHouseholdsByStateLgaCBO ");
            }
        }
    }
    public int getHouseholdVulnerabilityScore(HouseholdEnrollment hhe) throws Exception
    {
        int vulnerabilityScore=0;
        if(hhe !=null)
        {
           vulnerabilityScore+=hhe.getEducationLevel();
           vulnerabilityScore+=hhe.getFoodSecurityAndNutrition();
           vulnerabilityScore+=hhe.getHealth();
           vulnerabilityScore+=hhe.getHhHeadship();
           vulnerabilityScore+=hhe.getHhIncome();
           vulnerabilityScore+=hhe.getMeansOfLivelihood();
           vulnerabilityScore+=hhe.getShelterAndHousing();
           vulnerabilityScore+=hhe.getProtection();
        }
        if(vulnerabilityScore>999)
        vulnerabilityScore=0;
        return vulnerabilityScore;
    }
    public HouseholdEnrollment getHouseholdWithUpdatedWithdrawalStatus(HouseholdEnrollment hhe)
    {
       try
       {
             if(hhe !=null)
             {
                 OvcWithdrawal withdrawal=util.getOvcWithdrawalDaoInstance().getOvcWithdrawal(hhe.getHhUniqueId());
                 if(withdrawal !=null)
                 hhe.setWithdrawnFromProgram("Yes");
                 else
                 hhe.setWithdrawnFromProgram("No");                 
             } 
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       return hhe;
    }
    public void testLoggingWithException()
    {
        try
        {
            session = HibernateUtil.getSession();
            session.close();
            session.close();
        }
        catch(Exception ex)
        {
            //NomisLogManager.write(ex.getMessage());
            NomisLogManager.logStackTrace(ex);
        }
    }
}
