/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Ovc;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author COMPAQ USER
 */
public class DqaDaoImpl implements DqaDao {

    DaoUtil util=new DaoUtil();
    
    Session session;
    Transaction tx;
    String queryPart="select count (distinct ovc.ovcId) "+util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and ";
    String csiQueryOart="select count(distinct csi.ovcId)"+util.getHouseholdQueryPart()+"Ovc ovc,ChildStatusIndex csi where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId";
    String ovcListQueryPart=util.getHouseholdQueryPart()+"Ovc ovc where hhe.hhUniqueId=ovc.hhUniqueId and ";
    String csiListQueryPart=util.getHouseholdQueryPart()+"Ovc ovc,ChildStatusIndex csi where hhe.hhUniqueId=ovc.hhUniqueId and ovc.ovcId=csi.ovcId and ";
    public DqaDaoImpl()
    {

    }
    public List execSqlQuery(String sql)
    {
        List list=new ArrayList();
        session=HibernateUtil.getSession();
        tx=session.beginTransaction();
        list=session.createQuery(sql).list();
        tx.commit();
        session.close();
        return list;
    }
    public List getDqaOvcList(String[] params,int index) throws Exception
    {
        List list=new ArrayList();
        List listOfOvcWithCaregiverInfo=new ArrayList();
        String additionalQueryCriteria=util.getQueryCriteria(params);
        
        switch(index)
        {
            case 1:
            list=getListOfOVCWithNoDocEligibilityCriteria(additionalQueryCriteria);
            break;
            case 2:
            list=getListOfOVCWithNoRecordOfHivStatus(additionalQueryCriteria);
            break;
            case 3:
            list=getListOfOVCWithNoBirthCertAtEnrolmt(additionalQueryCriteria);
            break;
            case 4:
            list=getListOfOVCWithNoEducationalDetails(additionalQueryCriteria);
            break;
            case 5:
            list=getListOfOVCInSchoolWithNoSchoolName(additionalQueryCriteria);
            break;
            /*case 6:
            list=getListOfOVCWithNoCareGiverInfo(additionalQueryCriteria);
            break;*/
            case 6:
            list=getListOfOVCWithNoBaselinePsychoScore(additionalQueryCriteria);
            break;
            case 7:
            list=getListOfOVCWithNoBaselineFoodAndNutritionScore(additionalQueryCriteria);
            break;
            case 8:
            list=getListOfOVCWithNoBaselineHealthScore(additionalQueryCriteria);
            break;
            case 9:
            list=getListOfOVCWithNoBaselineEduAndWorkScore(additionalQueryCriteria);
            break;
            case 10:
            list=getListOfOVCWithNoBaselineProtectionScore(additionalQueryCriteria);
            break;
            case 11:
            list=getListOfOVCWithNoBaselineShelterAndCare(additionalQueryCriteria);
            break;
            default:
            break;
        }
        if(list !=null && !list.isEmpty())
        {
            listOfOvcWithCaregiverInfo.add(list.get(0));
            list.remove(0);
            //util.getOvcWithCaregiverInfo(list)
            listOfOvcWithCaregiverInfo.addAll(list);
        }
        return listOfOvcWithCaregiverInfo;
    }
    public List getDqaCount(String[] params) throws Exception
    {
        String additionalQueryCriteria=util.getQueryCriteria(params);//.getStateCriteria(state);
        List list=new ArrayList();
        list.add(getNoOfOVCWithNoDocEligibilityCriteria(additionalQueryCriteria));
        list.add(getNoOfOVCWithNoRecordOfHivStatus(additionalQueryCriteria));
        list.add(getNoOfOVCWithNoBirthCertAtEnrolmt(additionalQueryCriteria));
        list.add(getNoOfOVCWithNoEducationalDetails(additionalQueryCriteria));
        list.add(getNoOfOVCInSchoolWithNoSchoolName(additionalQueryCriteria));         
        //list.add(getNoOfOVCInOrphanageWithNoOrphanageName(additionalQueryCriteria));
        //list.add(getNoOfOVCWithNoCareGiverInfo(additionalQueryCriteria));
        list.add(getNoOfOVCWithNoBaselinePsychoScore(additionalQueryCriteria));
        list.add(getNoOfOVCWithNoBaselineFoodAndNutritionScore(additionalQueryCriteria));
        list.add(getNoOfOVCWithNoBaselineHealthScore(additionalQueryCriteria));
        list.add(getNoOfOVCWithNoBaselineEduAndWorkScore(additionalQueryCriteria));
        list.add(getNoOfOVCWithNoBaselineProtectionScore(additionalQueryCriteria));
        list.add(getNoOfOVCWithNoBaselineShelterAndCare(additionalQueryCriteria));
        return list;
    }
    private int getCount(List list)
    {
        int count=0;
        if(list !=null)
        count=Integer.parseInt(list.get(0).toString());
        return count;
    }
    private List getNoOfOVCWithNoDocEligibilityCriteria(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        mainlist.add("VC records with no documented <b>vulnerability</b> status");
        String sql=queryPart+"(ovc.eligibility=' ' or ovc.eligibility is null) "+additionalCriteria;
        list=util.execReportQuery(sql);
        mainlist.add(getCount(list));
        return mainlist;
    }
    private List getNoOfOVCWithNoRecordOfHivStatus(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        mainlist.add("VC records with no documented <b>HIV status</b> at enrollment");
        String sql=queryPart+"(ovc.hivStatus=' ' or ovc.hivStatus is null) "+additionalCriteria;
        list=util.execReportQuery(sql);
        mainlist.add(getCount(list));
        return mainlist;
    }
    private List getNoOfOVCWithNoBirthCertAtEnrolmt(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        mainlist.add("VC records with no <b>birth registration</b> details");
        String sql=queryPart+"(ovc.birthCertificate=' ' or ovc.birthCertificate is null) "+additionalCriteria;
        list=util.execReportQuery(sql);
        mainlist.add(getCount(list));
        return mainlist;
    }
    private List getNoOfOVCWithNoEducationalDetails(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        mainlist.add("VC records with no <b>educational</b> details");
        String sql=queryPart+" (ovc.schoolStatus=' ' or ovc.schoolStatus is null) and (ovc.schoolName=' ' or ovc.schoolName is null) "+additionalCriteria;
        mainlist.add(getCount(util.execReportQuery(sql)));
        return mainlist;
    }
    private List getNoOfOVCInSchoolWithNoSchoolName(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        mainlist.add("VC in school with no record of <b>name of school</b>");
        String sql=queryPart+" (ovc.schoolStatus !=' ' and ovc.schoolStatus !='No') and (ovc.schoolName=' ' or ovc.schoolName is null) "+additionalCriteria;
        mainlist.add(getCount(util.execReportQuery(sql)));
        return mainlist;
    }
    /*private List getNoOfOVCInOrphanageWithNoOrphanageName(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        mainlist.add("VC in ophanage with no record of <b>name of orphanage</b>");
        String sql="From Ovc ovc where ovc.orphanage ='Yes' and (ovc.orphanageName=' ' or ovc.orphanageName is null) "+additionalCriteria;
        mainlist.add(util.execReportQuery(sql).size());
        return mainlist;
    }
    private List getNoOfOVCWithNoCareGiverInfo(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        mainlist.add("VC records with no <b>caregiver information</b>");
        String sql="From Ovc ovc where ovc.caregiverName=' ' "+additionalCriteria;
        mainlist.add(util.execReportQuery(sql).size());
        return mainlist;
    }*/
    private List getNoOfOVCWithNoBaselinePsychoScore(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        mainlist.add("VC records with no baseline <b>Psychosocial</b> CSI score");
        String sql=csiQueryOart+" and ovc.dateEnrollment=csi.csiDate and (csi.csiFactor1=0 and csi.csiFactor2=0) "+additionalCriteria;
        mainlist.add(getCount(util.execReportQuery(sql)));
        return mainlist;
    }
    private List getNoOfOVCWithNoBaselineFoodAndNutritionScore(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        mainlist.add("VC records with no baseline <b>food and Nutrition</b> CSI score");
        String sql=csiQueryOart+" and ovc.dateEnrollment=csi.csiDate and (csi.csiFactor3=0 and csi.csiFactor4=0) "+additionalCriteria;
        mainlist.add(getCount(util.execReportQuery(sql)));
        return mainlist;
    }
    private List getNoOfOVCWithNoBaselineHealthScore(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        mainlist.add("VC records with no baseline <b>health</b> CSI score");
        String sql=csiQueryOart+" and ovc.dateEnrollment=csi.csiDate and (csi.csiFactor5=0 and csi.csiFactor6=0) "+additionalCriteria;
        mainlist.add(getCount(util.execReportQuery(sql)));
        return mainlist;
    }
    private List getNoOfOVCWithNoBaselineEduAndWorkScore(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        mainlist.add("VC records with no baseline <b>education and work</b> CSI score");
        String sql=csiQueryOart+" and ovc.dateEnrollment=csi.csiDate and (csi.csiFactor7=0 and csi.csiFactor8=0) "+additionalCriteria;
        mainlist.add(getCount(util.execReportQuery(sql)));
        return mainlist;
    }
    private List getNoOfOVCWithNoBaselineProtectionScore(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        mainlist.add("VC records with no baseline <b>protection</b> CSI score");
        String sql=csiQueryOart+" and ovc.dateEnrollment=csi.csiDate and (csi.csiFactor9=0 and csi.csiFactor10=0) "+additionalCriteria;
        mainlist.add(getCount(util.execReportQuery(sql)));
        return mainlist;
    }
    private List getNoOfOVCWithNoBaselineShelterAndCare(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        mainlist.add("VC records with no baseline <b>shelter and Care</b> CSI score");
        String sql=csiQueryOart+" and ovc.dateEnrollment=csi.csiDate and (csi.csiFactor11=0 and csi.csiFactor12=0) "+additionalCriteria;
        mainlist.add(getCount(util.execReportQuery(sql)));
        return mainlist;
    }
    
    /*Get the list of OVC per indicator*/
    private List getListOfOVCWithNoDocEligibilityCriteria(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        mainlist.add("List of VC with no documented <b>vulnerability</b> status");
        String sql=ovcListQueryPart+" (ovc.eligibility=' ' or ovc.eligibility is null) "+additionalCriteria;
        list=util.execReportQuery(sql);
        mainlist.addAll(getOvc(list));
        return mainlist;
    }
    private List getListOfOVCWithNoRecordOfHivStatus(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        mainlist.add("List of VC with no documented <b>HIV status</b> at enrollment");
        String sql=ovcListQueryPart+" (ovc.hivStatus=' ' or ovc.hivStatus is null) "+additionalCriteria;
        list=util.execReportQuery(sql);
        mainlist.addAll(getOvc(list));
        return mainlist;
    }
    private List getListOfOVCWithNoBirthCertAtEnrolmt(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        mainlist.add("List of VC with no <b>birth registration</b> details");
        String sql=ovcListQueryPart+" (ovc.birthCertificate=' ' or ovc.birthCertificate is null) "+additionalCriteria;
        list=util.execReportQuery(sql);
        mainlist.addAll(getOvc(list));
        return mainlist;
    }
    private List getListOfOVCWithNoEducationalDetails(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        mainlist.add("List of VC with no <b>educational</b> details");
        String sql=ovcListQueryPart+" (ovc.schoolStatus=' ' or ovc.schoolStatus is null) and (ovc.schoolName=' ' or ovc.schoolName is null) "+additionalCriteria;
        list=util.execReportQuery(sql);
        mainlist.addAll(getOvc(list));
        return mainlist;
    }
    private List getListOfOVCInSchoolWithNoSchoolName(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        mainlist.add("List of VC in school with no record of <b>name of school</b>");
        String sql=ovcListQueryPart+" (ovc.schoolStatus !=' ' and ovc.schoolStatus !='No') and (ovc.schoolName=' ' or ovc.schoolName is null) "+additionalCriteria;
        list=util.execReportQuery(sql);
        mainlist.addAll(getOvc(list));
        return mainlist;
    }
    /*private List getListOfOVCInOrphanageWithNoOrphanageName(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        mainlist.add("List of VC in ophanage with no record of <b>name of orphanage</b>");
        String sql="From Ovc ovc where ovc.orphanage ='Yes' and (ovc.orphanageName=' ' or ovc.orphanageName is null) "+additionalCriteria;
        list=util.execReportQuery(sql);
        mainlist.addAll(list);
        return mainlist;
    }
    private List getListOfOVCWithNoCareGiverInfo(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        mainlist.add("List of VC with no <b>caregiver information</b>");
        String sql="From Ovc ovc where ovc.caregiverName=' ' "+additionalCriteria;
        list=util.execReportQuery(sql);
        mainlist.addAll(list);
        return mainlist;
    }*/
    private List getListOfOVCWithNoBaselinePsychoScore(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        mainlist.add("List of VC with no baseline <b>Psychosocial</b> CSI score");
        String distinctIdQuery=csiListQueryPart+" ovc.dateEnrollment=csi.csiDate and (csi.csiFactor1=0 and csi.csiFactor2=0) "+additionalCriteria;
        System.err.println("distinctIdQuery in getListOfOVCWithNoBaselinePsychoScore(String additionalCriteria) is "+distinctIdQuery);
        list=util.execReportQuery(distinctIdQuery);
        mainlist.addAll(getOvc(list));
        return mainlist;
    }
    private List getListOfOVCWithNoBaselineFoodAndNutritionScore(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        mainlist.add("List of VC with no baseline <b>food and Nutrition</b> CSI score");
        //String sql="From Ovc ovc where ovc.ovcId in (select ovc.ovcId From Ovc ovc,ChildStatusIndex csi where ovc.ovcId=csi.ovcId and ovc.dateEnrollment=csi.csiDate and (csi.csiFactor3=0 and csi.csiFactor4=0)) "+additionalCriteria;
        String distinctIdQuery=csiListQueryPart+" ovc.dateEnrollment=csi.csiDate and (csi.csiFactor3=0 and csi.csiFactor4=0) "+additionalCriteria;
        list=util.execReportQuery(distinctIdQuery);
        mainlist.addAll(getOvc(list));
        return mainlist;
    }
    private List getListOfOVCWithNoBaselineHealthScore(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        mainlist.add("List of VC with no baseline <b>health</b> CSI score");
        //String sql="From Ovc ovc where ovc.ovcId in (select ovc.ovcId From Ovc ovc,ChildStatusIndex csi where ovc.ovcId=csi.ovcId and ovc.dateEnrollment=csi.csiDate and (csi.csiFactor5=0 and csi.csiFactor6=0)) "+additionalCriteria;
        String distinctIdQuery=csiListQueryPart+" ovc.dateEnrollment=csi.csiDate and (csi.csiFactor5=0 and csi.csiFactor6=0) "+additionalCriteria;
        list=util.execReportQuery(distinctIdQuery);
        mainlist.addAll(getOvc(list));
        return mainlist;
    }
    private List getListOfOVCWithNoBaselineEduAndWorkScore(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        mainlist.add("List of VC with no baseline <b>education and work</b> CSI score");
        //String sql="From Ovc ovc where ovc.ovcId in (select ovc.ovcId from Ovc ovc, ChildStatusIndex csi where ovc.ovcId=csi.ovcId and ovc.dateEnrollment=csi.csiDate and (csi.csiFactor7=0 and csi.csiFactor8=0)) "+additionalCriteria;
        String distinctIdQuery=csiListQueryPart+" ovc.dateEnrollment=csi.csiDate and (csi.csiFactor7=0 and csi.csiFactor8=0) "+additionalCriteria;
        list=util.execReportQuery(distinctIdQuery);
        mainlist.addAll(getOvc(list));
        return mainlist;
    }
    private List getListOfOVCWithNoBaselineProtectionScore(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        mainlist.add("List of VC with no baseline <b>protection</b> CSI score");
        //String sql="From Ovc ovc where ovc.ovcId in (select ovc.ovcId From Ovc ovc,ChildStatusIndex csi where ovc.ovcId=csi.ovcId and ovc.dateEnrollment=csi.csiDate and (csi.csiFactor9=0 and csi.csiFactor10=0)) "+additionalCriteria;
        String distinctIdQuery=csiListQueryPart+" ovc.dateEnrollment=csi.csiDate and (csi.csiFactor9=0 and csi.csiFactor10=0) "+additionalCriteria;
        list=util.execReportQuery(distinctIdQuery);
        mainlist.addAll(getOvc(list));
        return mainlist;
    }
    private List getListOfOVCWithNoBaselineShelterAndCare(String additionalCriteria)
    {
        List mainlist=new ArrayList();
        List list=new ArrayList();
        mainlist.add("List of VC with no baseline <b>shelter and Care</b> CSI score");
        //String sql="From Ovc ovc where ovc.ovcId in (select ovc.ovcId From Ovc ovc,ChildStatusIndex csi where ovc.ovcId=csi.ovcId and ovc.dateEnrollment=csi.csiDate and (csi.csiFactor11=0 and csi.csiFactor12=0)) "+additionalCriteria;
        String distinctIdQuery=csiListQueryPart+" ovc.dateEnrollment=csi.csiDate and (csi.csiFactor11=0 and csi.csiFactor12=0) "+additionalCriteria;
        list=util.execReportQuery(distinctIdQuery);
        mainlist.addAll(getOvc(list));
        
        return mainlist;
    }
    private List getOvc(List list)
    {
        List ovcList=new ArrayList();
        if(list !=null)
        {
            Ovc ovc=null;
            try
            {
                for(Object s:list)
                {
                    Object[] obj=(Object[])s;
                    ovc=(Ovc)obj[1];
                    System.err.println("ovc id is "+ovc.getOvcId());
                    ovcList.add(ovc);
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return ovcList;
    }
    /*private List getOvc(List list)
    {
        List ovcList=new ArrayList();
        if(list !=null)
        {
            OvcDao dao=new OvcDaoImpl();
            try
            {
                for(int i=0; i<list.size(); i++)
                {
                    ovcList.add(dao.getOvc((String)list.get(i)));
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return ovcList;
    }*/
}
