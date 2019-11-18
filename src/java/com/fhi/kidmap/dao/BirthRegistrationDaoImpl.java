/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.BirthRegistration;
import com.fhi.kidmap.business.Ovc;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class BirthRegistrationDaoImpl implements BirthRegistrationDao
{
    Session session;
    Transaction tx;
    SessionFactory sessions;
    DaoUtil util=new DaoUtil();
    AppUtility appUtil=new AppUtility();
    public void cleanUpBirthRegistrationRecords()
    {
        
    }
    public List getAllBirthRegistrationRecordsWithANoAfterEnrollment(String communityCode) throws Exception
    {
        List list = null;
        if(communityCode !=null)
        communityCode=communityCode.trim();
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(util.getHouseholdQueryPart()+" Ovc ovc, BirthRegistration br where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=br.clientId and TRIM(hhe.communityCod)e=:cc and br.birthRegistrationStatus='No')").setString("cc", communityCode).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return list;
    }
    public List getNumberOfOvcCurrentlyEnrolledWithBirthRegistration(String indicatorName,String stateCode) throws Exception
    {
        List mainList = new ArrayList();
        mainList.add(indicatorName);
        List list = null;
        try
        {
            //System.err.println("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment), count(distinct br.clientId) "+util.getHouseholdQueryPart()+" Ovc ovc, BirthRegistration br where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=br.clientId and br.recordStatus='active' and br.birthRegistrationStatus='Yes' and hhe.stateCode=:state group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(ovc.dateEnrollment),YEAR(ovc.dateEnrollment)");
            
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(br.dateOfRegistration),YEAR(br.dateOfRegistration), count(distinct br.clientId) "+util.getHouseholdQueryPart()+" Ovc ovc, BirthRegistration br where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=br.clientId and ovc.withdrawnFromProgram='No' and br.recordStatus='active' and br.birthRegistrationStatus='Yes' and hhe.stateCode=:state group by hhe.stateCode,hhe.lgaCode,hhe.orgCode,hhe.communityCode,ovc.gender,ovc.currentAge,MONTH(br.dateOfRegistration),YEAR(br.dateOfRegistration)").setString("state", stateCode).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        mainList.addAll(list);
        return mainList;
    }
    /*public boolean isUpdatable(BirthRegistration br)
    {
        boolean updatable=false;
        try
        {
            BirthRegistration activeBr=getActiveBirthRegistration(br.getClientId());
            if(activeBr==null)
            {
                updatable=true;
                return updatable;
            }
            else
            {
                if(activeBr.getBirthRegistrationStatus().equalsIgnoreCase("Yes"))
                {
                    if((br.getPointOfUpdate().equalsIgnoreCase(activeBr.getPointOfUpdate())) && (br.getDateOfRegistration().equalsIgnoreCase(activeBr.getDateOfRegistration())))
                    updatable=true;
                    else
                    updatable=false;
                }
                else
                updatable=true;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return updatable;
    }*/
    public void saveBaselineBirthRegistration(String clientId) throws Exception
    {
        Ovc ovc=util.getOvcDaoInstance().getOvc(clientId);
        if(ovc !=null)
        {
            BirthRegistration br=new BirthRegistration();
            br.setBirthRegistrationStatus(ovc.getBirthCertificate());
            br.setClientId(clientId);
            br.setClientType(NomisConstant.OVC_TYPE);
            br.setDateModified(appUtil.getCurrentDate());
            br.setDateOfRegistration(ovc.getDateEnrollment());
            br.setPointOfUpdate(NomisConstant.ENROLLMENT_POINTOFUPDATE);
            br.setRecordStatus("active");
            saveBirthRegistration(br);
        }
    }
    public void cleanBirthRegistrationRecords(String clientId) throws Exception
    {
        try
        {
            List list=this.getBirthRegistrationRecordsByCliedId(clientId);
            if(list !=null && !list.isEmpty())
            {
                for(Object obj:list)
                {
                    BirthRegistration br=(BirthRegistration)obj;
                    if(br.getBirthRegistrationStatus().equalsIgnoreCase("No") && !br.getPointOfUpdate().equalsIgnoreCase(NomisConstant.ENROLLMENT_POINTOFUPDATE))
                    deleteBirthRegistration(br);
                }
                setActiveBirthRegistration(clientId);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public boolean isSavable(BirthRegistration br) throws Exception
    {
        boolean updatable=false;
        cleanBirthRegistrationRecords(br.getClientId());
        try
        {
            if(br==null || br.getPointOfUpdate()==null)
            {
                updatable=false;
                return updatable;
            }
            else
            {
                if(br.getPointOfUpdate().equalsIgnoreCase(NomisConstant.ENROLLMENT_POINTOFUPDATE))
                {
                    BirthRegistration enrollBr=null;
                    List brList=this.getBirthRegistrationRecordsByCliedId(br.getClientId());
                    if(brList !=null && !brList.isEmpty())
                    {
                        //getBirthRegistrationByIdAndPointOfUpdate(br.getClientId(), br.getPointOfUpdate());
                        for(Object obj:brList)
                        {
                            BirthRegistration existingBr=(BirthRegistration)obj;
                            if(existingBr.getPointOfUpdate().equalsIgnoreCase(NomisConstant.ENROLLMENT_POINTOFUPDATE))
                            enrollBr=existingBr;
                        }
                        if(enrollBr !=null)
                        {
                            if(enrollBr.getBirthRegistrationStatus().equalsIgnoreCase(br.getBirthRegistrationStatus()))
                            updatable=false;
                            else if(DateManager.compareDates(enrollBr.getDateModified(), br.getDateModified()))
                            updatable=true;
                        }
                        else
                        {
                            //enter new baseline birth registration record
                            updatable=true;
                            System.err.println("About to save new enrollment birth reg");
                        }
                    }
                    else
                    {
                        //enter new baseline birth registration record
                        updatable=true;
                    }
                }
                else
                {
                    if(br.getBirthRegistrationStatus()==null || br.getBirthRegistrationStatus().equalsIgnoreCase("No"))
                    {
                        //The birth registration is a No and should not be updated
                        updatable=false;
                    }
                    else
                    {
                        //The birth registration is a yes but at Service or follow up
                        BirthRegistration activeBr=getActiveBirthRegistration(br.getClientId());
                        if(activeBr==null)
                        {
                            saveBaselineBirthRegistration(br.getClientId());
                            updatable=true;
                        }
                        else if(activeBr.getBirthRegistrationStatus().equalsIgnoreCase("No"))
                        {
                            //Active is No, so update
                            updatable=true;
                        }
                        else
                        {
                            //No need to update, there is already an active Yes
                            updatable=false;
                        }
                    }
                }
                    
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return updatable;
    }
    public void saveBirthRegistration(BirthRegistration br) throws Exception
    {
        try
        {
            //System.err.println("BirthRegistration in saveBirthRegistration is "+br.getBirthRegistrationStatus());
            if(br !=null && br.getBirthRegistrationStatus() !=null)
            {
                if(isSavable(br))
                {
                    if(br.getRecordId()==null)
                    br.setRecordId(getUniqueRecordId());
                    else
                    {
                        if(getBirthRegistration(br.getRecordId()) !=null)
                        updateBirthRegistration(br);
                        return;
                    }
                    br.setRecordStatus("active");
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.save(br);
                    tx.commit();
                    closeSession(session);
                }
              //System.err.println("BirthRegistration in saveBirthRegistration just before the botton is "+br.getBirthRegistrationStatus());  
            }
            //System.err.println("The last BirthRegistration in saveBirthRegistration at the botton is "+br.getBirthRegistrationStatus());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
    }
    /*public void saveBirthRegistration(BirthRegistration br) throws Exception
    {
        try
        {
            //System.err.println("BirthRegistration in saveBirthRegistration is "+br.getBirthRegistrationStatus());
            if(br !=null && br.getBirthRegistrationStatus() !=null)
            {
                BirthRegistration existingbr=null;
                List brList=getBirthRegistrationRecordsByCliedId(br.getClientId());
                if(brList !=null && !brList.isEmpty())
                {
                    //earlier versions may have more than one records of the same client, so look for the active record
                    if(brList.size()>1)
                    {
                        for(int i=0; i<brList.size(); i++)
                        {
                            existingbr=(BirthRegistration)brList.get(i);
                            if(existingbr !=null && existingbr.getRecordStatus().equalsIgnoreCase("active"))
                            {
                                break;
                            }
                        }
                    }
                    else
                    {
                        existingbr=(BirthRegistration)brList.get(0);
                        
                    }//!br.getPointOfUpdate().equalsIgnoreCase(existingbr.getPointOfUpdate()) || 
                    if(br.getBirthRegistrationStatus() !=null && br.getBirthRegistrationStatus().equalsIgnoreCase("Yes"))
                    {
                        if(appUtil.compareDates(br.getDateOfRegistration(), existingbr.getDateOfRegistration())) 
                        return;
                       if(br.getPointOfUpdate().equalsIgnoreCase(existingbr.getPointOfUpdate()))
                       {
                           
                       }
                    }
                    if(appUtil.compareDates(br.getDateOfRegistration(), existingbr.getDateOfRegistration())) 
                    return;
                    
                    br.setRecordStatus("active");
                    br.setRecordId(existingbr.getRecordId());
                    updateBirthRegistration(br);
                }
                else
                {
                    if(br.getRecordId()==null)
                    br.setRecordId(getUniqueRecordId());
                    br.setRecordStatus("active");
                    session = HibernateUtil.getSession();
                    tx = session.beginTransaction();
                    session.save(br);
                    tx.commit();
                    closeSession(session);
                }
              //System.err.println("BirthRegistration in saveBirthRegistration just before the botton is "+br.getBirthRegistrationStatus());  
            }
            //System.err.println("The last BirthRegistration in saveBirthRegistration at the botton is "+br.getBirthRegistrationStatus());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            closeSession(session);
        }
    }*/
    
    public void updateBirthRegistration(BirthRegistration br) throws Exception
    {
        try
        {
            //System.err.println("BirthRegistration in updateBirthRegistration is "+br.getBirthRegistrationStatus());
            if(br !=null && br.getRecordId() !=null)
            {
                if(isSavable(br))
                {
                    //if this record already exist, update
                    BirthRegistration existingbr=getBirthRegistration(br.getRecordId());
                    if(existingbr==null)
                    existingbr=getBirthRegistrationByIdDateAndPointOfService(br.getClientId(), br.getDateOfRegistration(),br.getPointOfUpdate());
                    if(existingbr !=null)
                    {
                        br.setRecordId(existingbr.getRecordId());
                        //if the status is not null, update else delete existing record
                        if(br.getBirthRegistrationStatus() !=null)
                        {
                            session = HibernateUtil.getSession();
                            tx = session.beginTransaction();
                            session.update(br);
                            tx.commit();
                            closeSession(session);
                            System.err.println("Birth Reg updated....."+br.getPointOfUpdate()+" "+br.getBirthRegistrationStatus());
                            setActiveBirthRegistration(br.getClientId());
                        }
                    }
                }
            }
            
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void updateActiveBirthRegistration(BirthRegistration br) throws Exception
    {
        try
        {
            if(br !=null && br.getRecordId() !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.update(br);
                tx.commit();
                closeSession(session);
            }
            
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void markedForDelete(BirthRegistration br) throws Exception
    {
        try
        {
            if(br !=null && br.getRecordId() !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                br.setMarkedForDelete(NomisConstant.MARKEDFORDELETE);
                session.update(br);
                tx.commit();
                closeSession(session);
            }
            
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public void deleteBirthRegistration(BirthRegistration br) throws Exception
    {
        try
        {
            if(br !=null && br.getRecordId() !=null)
            {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                session.delete(br);
                tx.commit();
                closeSession(session);
                setActiveBirthRegistration(br.getClientId());
                util.saveDeletedRecord(br.getClientId(), null, "birthRegistration", br.getDateOfRegistration());
            }
            
        }
        catch(Exception ex)
        {
            closeSession(session);
            ex.printStackTrace();
        }
    }
    public List getBirthRegistrationRecords(String recordId) throws Exception
    {
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from BirthRegistration br where br.recordId=:id order by br.dateOfRegistration desc").setString("id", recordId).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return list;
    }
    public List getBirthRegistrationRecordsByCliedId(String clientId) throws Exception
    {
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from BirthRegistration br where br.clientId=:id order by br.dateOfRegistration desc").setString("id", clientId).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return list;
    }
    public int getCountOfBirthRegistration(String pointOfUpdate) throws Exception
    {
        BirthRegistration br=null;
        int count=0;
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count (distinct br.clientId) from BirthRegistration br where br.pointOfUpdate=:pou").setString("pou", pointOfUpdate).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            count=Integer.parseInt(list.get(0).toString());
        }
        return count;
    }
    public BirthRegistration getBirthRegistration(String recordId) throws Exception
    {
        BirthRegistration br=null;
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from BirthRegistration br where br.recordId=:id").setString("id", recordId).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            br=(BirthRegistration)list.get(0);
        }
        return br;
    }
    public BirthRegistration getActiveBirthRegistration(String clientId) throws Exception
    {
        BirthRegistration br=null;
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from BirthRegistration br where br.clientId=:id and br.recordStatus='active'").setString("id", clientId).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            br=(BirthRegistration)list.get(0);
        }
        return br;
    }
    public BirthRegistration getBirthRegistration(String clientId,String birthRegistrationStatus) throws Exception
    {
        BirthRegistration br=null;
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from BirthRegistration br where br.clientId=:id and br.birthRegistrationStatus=:bst").setString("id", clientId).setString("bst", birthRegistrationStatus).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            br=(BirthRegistration)list.get(0);
        }
        return br;
    }
    public BirthRegistration getBirthRegistrationByIdAndDate(String clientId,String dateOfRegistration) throws Exception
    {
        BirthRegistration br=null;
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from BirthRegistration br where br.clientId=:id and br.dateOfRegistration=:dt").setString("id", clientId).setString("dt", dateOfRegistration).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            br=(BirthRegistration)list.get(0);
        }
        return br;
    }
    public BirthRegistration getBirthRegistrationByIdAndPointOfUpdate(String clientId,String pointOfUpdate) throws Exception
    {
        BirthRegistration br=null;
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from BirthRegistration br where br.clientId=:id and br.pointOfUpdate=:pou").setString("id", clientId).setString("pou", pointOfUpdate).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            br=(BirthRegistration)list.get(0);
        }
        return br;
    }
    public BirthRegistration getBirthRegistrationByIdDateAndPointOfService(String clientId,String dateOfRegistration,String pointOfUpdate) throws Exception
    {
        BirthRegistration br=null;
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("from BirthRegistration br where br.clientId=:id and br.dateOfRegistration=:dt and br.pointOfUpdate=:pos").setString("id", clientId).setString("dt", dateOfRegistration).setString("pos", pointOfUpdate).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            br=(BirthRegistration)list.get(0);
        }
        return br;
    }
    public List getDistinctClientIdFromBirthRegistration(String communityCode) throws Exception
    {
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct br.clientId "+util.getHouseholdQueryPart()+" Ovc ovc, BirthRegistration br where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=br.clientId and hhe.communityCode=:cod ").setString("cod", communityCode).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return list;
    }
    public int getNumberOfOvcWithBirthRegistrationAtBaseline(String additionalQuery,boolean currentlyEnrolled) throws Exception
    {
        int count=0;
        List list = null;
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        try
        {
            String query="select count(distinct br.clientId) "+util.getHouseholdQueryPart()+" Ovc ovc, BirthRegistration br where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=br.clientId and br.recordStatus='active' and br.birthRegistrationStatus='Yes' and br.pointOfUpdate='enrollment'"+additionalQuery+currentlyEnrolledQuery;
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            count=Integer.parseInt(list.get(0).toString());
        }
        return count;
    }
    public List getListOfOvcWithBirthRegistrationAtBaseline(String additionalQuery,boolean currentlyEnrolled) throws Exception
    {
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        List ovcList=new ArrayList();
        List list = null;
        try
        {
            String query=util.getHouseholdQueryPart()+" Ovc ovc, BirthRegistration br where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=br.clientId and br.recordStatus='active' and br.birthRegistrationStatus='Yes' and br.pointOfUpdate='enrollment'"+additionalQuery+currentlyEnrolledQuery;
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            for(int i=0; i<list.size(); i++)
            {
                Object[] obj=(Object[])list.get(i);
                ovcList.add(obj[1]);
            }
        }
        return ovcList;
    }
    public int getNumberOfOvcWithBirthRegistrationByPointOfUpdate(String additionalQuery,String pointOfUpdate,boolean currentlyEnrolled) throws Exception
    {
        int count=0;
        List list = null;
        String pointOfUpdateQuery="";
        if(pointOfUpdate !=null)
        pointOfUpdateQuery=" and br.pointOfUpdate='"+pointOfUpdate+"'";
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        try
        {
            String query="select count(distinct br.clientId) "+util.getHouseholdQueryPart()+" Ovc ovc, BirthRegistration br where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=br.clientId and br.recordStatus='active' and br.birthRegistrationStatus='Yes'"+pointOfUpdateQuery+additionalQuery+currentlyEnrolledQuery;
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            count=Integer.parseInt(list.get(0).toString());
        }
        return count;
    }
    public List getListOfOvcWithBirthRegistrationByPointOfUpdate(String additionalQuery,String pointOfUpdate,boolean currentlyEnrolled) throws Exception
    {
        String pointOfUpdateQuery="";
        if(pointOfUpdate !=null)
        pointOfUpdateQuery=" and br.pointOfUpdate='"+pointOfUpdate+"'";
        String currentlyEnrolledQuery="";
        if(currentlyEnrolled)
        currentlyEnrolledQuery=util.getOvcWithdrawnFromProgramQuery("No");
        List ovcList=new ArrayList();
        List list = null;
        try
        {
            String query=util.getHouseholdQueryPart()+" Ovc ovc, BirthRegistration br where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=br.clientId and br.recordStatus='active' and br.birthRegistrationStatus='Yes'"+pointOfUpdateQuery+additionalQuery+currentlyEnrolledQuery;
            System.err.println(query);
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(query).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            for(int i=0; i<list.size(); i++)
            {
                Object[] obj=(Object[])list.get(i);
                ovcList.add(obj[1]);
            }
        }
        return ovcList;
    }
    public int getNumberOfOvcWithBirthRegistration(String additionalQuery) throws Exception
    {
        int count=0;
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select count(distinct br.clientId) "+util.getHouseholdQueryPart()+" Ovc ovc, BirthRegistration br where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=br.clientId and br.recordStatus='active' and br.birthRegistrationStatus='Yes'"+additionalQuery).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            count=Integer.parseInt(list.get(0).toString());
        }
        return count;
    }
    public List getListOfOvcWithBirthRegistration(String additionalQuery) throws Exception
    {
        List ovcList=new ArrayList();
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery(util.getHouseholdQueryPart()+" Ovc ovc, BirthRegistration br where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=br.clientId and br.recordStatus='active' and br.birthRegistrationStatus='Yes'"+additionalQuery).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            for(int i=0; i<list.size(); i++)
            {
                Object[] obj=(Object[])list.get(i);
                ovcList.add(obj[1]);
            }
        }
        return ovcList;
    }
    public int getNumberOfOvcWithoutBirthRegistration(String additionalQuery) throws Exception
    {
        int count=0;
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct br.clientId "+util.getHouseholdQueryPart()+" Ovc ovc, BirthRegistration br where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=br.clientId and br.recordStatus='active' and br.birthRegistrationStatus !='Yes'"+additionalQuery).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
            List idList=wdao.getListOfIdsNotWithdrawn(list);
            count=idList.size();
        }
        return count;
    }
    public List getListOfOvcIdsWithoutBirthRegistration(String additionalQuery) throws Exception
    {
        List idList=new ArrayList();
        List list = null;
        try
        {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            list = session.createQuery("select distinct br.clientId "+util.getHouseholdQueryPart()+" Ovc ovc, BirthRegistration br where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=br.clientId and br.recordStatus='active' and br.birthRegistrationStatus!='Yes'"+additionalQuery).list();
            tx.commit();
            closeSession(session);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        if(list !=null && !list.isEmpty())
        {
            OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
            idList=wdao.getListOfIdsNotWithdrawn(list);  
        }
        return idList;
    }
    /*public List getListOfOvcWithBirthRegistration(String additionalQuery) throws Exception
    {
        List idList=new ArrayList();
        List ovcList = null;
        try
        {
            idList=getListOfOvcIdsWithoutBirthRegistration(additionalQuery);
            OvcDao ovcdao=new OvcDaoImpl();
            ovcList=ovcdao.getListOfOvcByOvcId(idList);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return ovcList;
    }*/
    public List getListOfOvcWithoutBirthRegistration(String additionalQuery) throws Exception
    {
        List idList=new ArrayList();
        List ovcList = null;
        try
        {
            idList=getListOfOvcIdsWithoutBirthRegistration(additionalQuery);
            OvcDao ovcdao=new OvcDaoImpl();
            ovcList=ovcdao.getListOfOvcByOvcId(idList);
        }
         catch (Exception ex)
         {
             closeSession(session);
            throw new Exception(ex);
         }
        return ovcList;
    }
    public String getUniqueRecordId()
    {
        String recordId=appUtil.generateUniqueId();
        try
        {
            if(getBirthRegistration(recordId) !=null)
            getUniqueRecordId();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return recordId;
    }
    public void setActiveBirthRegistration(String clientId) throws Exception
    {
        try
        {
            //int count=0;
            List list=getBirthRegistrationRecordsByCliedId(clientId);
            if(list !=null && list.size()>1)
            {
                BirthRegistration br=null;
                for(int i=0; i<list.size(); i++)
                {
                    br=(BirthRegistration)list.get(i);
                    if(i==0)
                    br.setRecordStatus("active");
                    else
                    br.setRecordStatus("passive");
                    updateActiveBirthRegistration(br);
                    //count++;
                    //System.err.println("br.clientId "+br.getClientId()+" at "+count+" reordered");
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void closeSession(Session session)
    {
        if(session !=null && session.isOpen())
        {
            session.close();
        }
    }
}
