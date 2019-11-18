 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.nomis.nomisutils;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.CboSetup;
import com.fhi.kidmap.business.Cbos;
import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcSchool;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.business.Wards;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import java.util.List;
import java.util.ArrayList;
import com.fhi.kidmap.dao.WardDao;
import com.fhi.kidmap.dao.WardDaoImpl;
import com.fhi.kidmap.dao.CboDao;
import com.fhi.kidmap.dao.CboDaoImpl;
import com.fhi.kidmap.dao.CboSetupDao;
import com.fhi.kidmap.dao.CboSetupDaoImpl;
import com.fhi.kidmap.dao.ChildStatusIndexDao;
import com.fhi.kidmap.dao.ChildStatusIndexDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.FollowupDao;
import com.fhi.kidmap.dao.FollowupDaoImpl;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdFollowupAssessmentDao;
import com.fhi.kidmap.dao.HouseholdFollowupAssessmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdServiceDao;
import com.fhi.kidmap.dao.HouseholdServiceDaoImpl;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDao;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDaoImpl;
import com.fhi.kidmap.dao.LgaDao;
import com.fhi.kidmap.dao.LgaDaoImpl;
import com.fhi.kidmap.dao.NutritionAssessmentDao;
import com.fhi.kidmap.dao.NutritionAssessmentDaoImpl;
import com.fhi.kidmap.dao.OrganizationRecordsDao;
import com.fhi.kidmap.dao.OrganizationRecordsDaoImpl;
import com.fhi.kidmap.dao.OrganizationalCapacityAssessmentDao;
import com.fhi.kidmap.dao.OrganizationalCapacityAssessmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcReferralDao;
import com.fhi.kidmap.dao.OvcReferralDaoImpl;
import com.fhi.kidmap.dao.OvcSchoolDao;
import com.fhi.kidmap.dao.OvcSchoolDaoImpl;
import com.fhi.kidmap.dao.OvcServiceDao;
import com.fhi.kidmap.dao.OvcServiceDaoImpl;
import com.fhi.kidmap.dao.StatesDao;
import com.fhi.kidmap.dao.StatesDaoImpl;
import com.fhi.kidmap.dao.UserDao;
import com.fhi.kidmap.dao.UserDaoImpl;
import com.fhi.kidmap.report.OvcRecords;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 *
 * @author COMPAQ USER
 */
public class AjaxProcessor implements Serializable
{
    String cboId;
    String param="";
    List list;
    DaoUtil util=new DaoUtil();
    OvcRecords records=new OvcRecords();
    public AjaxProcessor()
    {
        list = new ArrayList();
    }
    public String processOvcByNameAndAge(String reqParam,HttpServletRequest request)
    {
        Ovc ovcObj = null;
        List ovcMatches = new ArrayList();
        String ageUnit=" ";
        String[] params=reqParam.split(":");
        int ageParam=Integer.parseInt(params[2]);
        if(params !=null && params.length>3)
            ageUnit=params[3];
           try
           {
                OvcDao dao = new OvcDaoImpl();
                list = dao.getOvcByNameAndAge(params[0],params[1],ageParam,ageUnit);
                //System.err.println("list.size() is "+list.size());
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                Ovc ovc = (Ovc)s;

                ovcObj = new Ovc();
                ovcObj.setOvcId(ovc.getOvcId());
                ovcObj.setDateEnrollment(ovc.getDateEnrollment());
                ovcObj.setState(ovc.getState());
                ovcObj.setLga(ovc.getLga());
                ovcObj.setWard(ovc.getWard());
                ovcObj.setLastName(ovc.getLastName());
                ovcObj.setFirstName(ovc.getFirstName());
                ovcObj.setGender(ovc.getGender());
                ovcObj.setAge(ovc.getAge());
                ovcObj.setAgeUnit(ovc.getAgeUnit());

                
                ovcMatches.add(ovcObj);
                request.setAttribute("ovcMatches", ovcMatches);
                request.getSession().setAttribute("ovcMatches", ovcMatches);
                //param+="duplicate;";
            }
            if(ovcMatches!=null && !ovcMatches.isEmpty())
            param="duplicate;";
            else
            param="no duplicate;";
            //System.err.println("param is "+param);
            return param;
    }
    public String getOvcByPartOfNames(String partOfName)
    {
        List list=new ArrayList();
        int selectSize=0;
        OvcDao dao=new OvcDaoImpl();
        try
        {
            list=dao.getOVCByPartOfNames(partOfName);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        String ovcOptionList="";
        if(list !=null && list.size()>0)
        {
            if(list.size()<2)
            selectSize=2;
            else if(list.size()>12)
            selectSize=12;
            else
            selectSize=list.size();

            ovcOptionList="<select name='patNames' id='patNameId' size='"+selectSize+"' style='width:199px; font-family:Arial, Helvetica, sans-serif; font-size:9pt;' multiple onchange='showSelected(this.value)' ondblclick='setPatDetails(this.value)'>";
            for(Object s:list)
            {
                Ovc ovc=(Ovc)s;
                ovcOptionList+="<option value='"+ovc.getOvcId()+"'>"+ovc.getLastName()+"       "+ovc.getFirstName()+"</option>";
            }
            if(list.isEmpty())
            ovcOptionList+="<option> </option>";
            ovcOptionList+="</select>";
        }
        return ovcOptionList;
    }
    public String getCaregiversByPartOfNames(String hhUniqueIdAndPartOfName)
    {
        List list=new ArrayList();
        int selectSize=0;
        CaregiverDao dao=new CaregiverDaoImpl();
        String[] hhUniqueIdAndPartOfNameArray=null;
        if(hhUniqueIdAndPartOfName !=null && hhUniqueIdAndPartOfName.indexOf("-") !=-1)
        {
            hhUniqueIdAndPartOfNameArray=hhUniqueIdAndPartOfName.split("-");
            if(hhUniqueIdAndPartOfNameArray.length <2)
            return null;
        }
        else
        return null;
        String hhUniqueId=hhUniqueIdAndPartOfNameArray[0];
        String partOfName=hhUniqueIdAndPartOfNameArray[1];
        try
        {
            list=dao.searchCaregiversFromTheSameHouseholdByPartsOfName(hhUniqueId,partOfName);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        String ovcOptionList="";
        if(list !=null && list.size()>0)
        {
            if(list.size()<2)
            selectSize=2;
            else if(list.size()>3)
            selectSize=6;
            else
            selectSize=list.size();
            ovcOptionList="<div id='caregiverPop' class='caregiverSearch2' style='width:200px;'>";
            ovcOptionList+="<table cellspacing='0'><tr><td style='width:208px;'><label id='cgiverIdSpace' style='color:#FFFFFF; width:198px;'>Select Caregiver</label></td><td><img name='caregiverPopClose' src='images/close.jpg' style='width:10px; height:10px;' onClick='hideComponent(\"caregiverPop\")'/></td></tr>";
            ovcOptionList+="<tr><td colspan=2><select name='caregiverNames' id='caregiverNames' size='"+selectSize+"' style='width:199px; font-family:Arial, Helvetica, sans-serif; font-size:9pt;' multiple onchange='showCaregiverId(this.value)' ondblclick='setCaregiverDetails(this.value)'>";
            for(Object s:list)
            {
                Caregiver cgiver=(Caregiver)s;
                ovcOptionList+="<option value='"+cgiver.getCaregiverId()+"'>"+cgiver.getCaregiverFirstname()+"  "+cgiver.getCaregiverLastName()+"</option>";
            }
            if(list.isEmpty())
            ovcOptionList+="<option> </option>";
            ovcOptionList+="</select>";
            ovcOptionList+="</td></tr></table></div>";
        }
        return ovcOptionList;
    }
    public String getCaregiverInfoById(String cgiverId)
    {
        CaregiverDao dao=new CaregiverDaoImpl();
        String caregiverInfo="";
        try
        {
            Caregiver cgiver=dao.getCaregiverByCaregiverId(cgiverId);
            if(cgiver !=null)
            {
                caregiverInfo=cgiver.getCaregiverLastName()+";"+cgiver.getCaregiverFirstname()+";"+cgiver.getCaregiverAddress();
                caregiverInfo+=";"+cgiver.getCaregiverGender()+";"+cgiver.getCaregiverOccupation()+";"+cgiver.getCaregiverAge()+";"+cgiver.getCaregiverPhone()+";"+cgiver.getCgiverHivStatus();
            }
        }
        catch(Exception ex)
        {

        }
        return caregiverInfo;
     }
    public int getEnrollmentSize(HttpSession session,String[] params,String withdrawalQuery)
    {
        String startDate="All";
        String endDate="All";

        if(params[0] !=null && !params[0].equalsIgnoreCase("All"))
        {
            startDate=util.getStartDate(params);
            endDate=util.getEndDate(params);
        }
        
        String[] orgParams={params[4],params[5],params[6],params[9],null,null,null,null,null,null,null,null,null,params[7],params[8]};
        String additionalOrgQuery=util.getOrgQueryCriteria(orgParams);
        List list=new ArrayList();
        OvcDao dao=new OvcDaoImpl();
        try
        {       
            list=dao.getOvcList(startDate,endDate,additionalOrgQuery+withdrawalQuery);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("reportPeriod", startDate+";"+endDate);
        session.setAttribute("enrollmentRecList", list);
        return list.size();
    }
    public int getCsiDataSize(HttpSession session,String[] params,String withdrawalQuery)
    {
        String startDate="All";
        String endDate="All";

        if(params[0] !=null && !params[0].equalsIgnoreCase("All"))
        {
            startDate=util.getStartDate(params);
            endDate=util.getEndDate(params);
        }
        String[] orgParams={params[4],params[5],params[6],params[9],null,null,null,null,null,null,null,null,null,params[7],params[8]};
        String additionalOrgQuery=util.getOrgQueryCriteria(orgParams);
        List list=new ArrayList();
        ChildStatusIndexDao dao=new ChildStatusIndexDaoImpl();
        try
        {
            list=dao.getCsiForExport(startDate,endDate,additionalOrgQuery+withdrawalQuery);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("csiRecList", list);
        int csiListSize=list.size();
        return csiListSize;
    }
    public int getServiceDataSize(HttpSession session,String[] params,String withdrawalQuery)
    {
        String startDate="All";
        String endDate="All";

        if(params[0] !=null && !params[0].equalsIgnoreCase("All"))
        {
            startDate=util.getStartDate(params);
            endDate=util.getEndDate(params);
        }
        String[] orgParams={params[4],params[5],params[6],params[9],null,null,null,null,null,null,null,null,null,params[7],params[8]};
        String additionalOrgQuery=util.getOrgQueryCriteria(orgParams);
        List list=new ArrayList();
        OvcServiceDao dao=new OvcServiceDaoImpl();
        try
        {
            list=dao.getServiceRecords(startDate,endDate,additionalOrgQuery+withdrawalQuery);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("servicesRecList", list);
        int serviceListSize=list.size();
        return serviceListSize;
    }
    public int getNutritionAssessmentDataSize(HttpSession session,String[] params,String withdrawalQuery)
    {
        String startDate="All";
        String endDate="All";
        String dateQuery=" ";

        if(params[0] !=null && !params[0].equalsIgnoreCase("All"))
        {
            startDate=util.getStartDate(params);
            endDate=util.getEndDate(params);
            dateQuery=" and na.dateModified between '"+startDate+"' and '"+endDate+"'";
        }
        String[] orgParams={params[4],params[5],params[6],params[9],null,null,null,null,null,null,null,null,null,params[7],params[8]};
        String additionalOrgQuery=util.getOrgQueryCriteria(orgParams);
        List list=new ArrayList();
        NutritionAssessmentDao dao=new NutritionAssessmentDaoImpl();
        try
        {
            list=dao.getNutritionAssessmentsForExport(additionalOrgQuery+withdrawalQuery+dateQuery);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("nutritionAssessmentRecList", list);
        int naListSize=list.size();
        return naListSize;
    }
    public int getHouseholdFollowupAssessmentDataSize(HttpSession session,String[] params,String withdrawalQuery)
    {
        String startDate="All";
        String endDate="All";
        String dateQuery=" ";

        if(params[0] !=null && !params[0].equalsIgnoreCase("All"))
        {
            startDate=util.getStartDate(params);
            endDate=util.getEndDate(params);
            dateQuery=" and hhfa.dateModified between '"+startDate+"' and '"+endDate+"'";
        }
        String[] orgParams={params[4],params[5],params[6],params[9],null,null,null,null,null,null,null,null,null,params[7],params[8]};
        String additionalOrgQuery=util.getOrgQueryCriteria(orgParams);
        List list=new ArrayList();
        List hhfaList=new ArrayList();
        HouseholdFollowupAssessmentDao dao=new HouseholdFollowupAssessmentDaoImpl();
        try
        {
            list=dao.getListOfAssessments(additionalOrgQuery+withdrawalQuery+dateQuery);
            if(list !=null)
            {
                for(Object obj:list)
                {
                    Object[] objArray=(Object[])obj;
                    hhfaList.add(objArray[1]);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("householdFollowupAssessmentRecList", hhfaList);
        int naListSize=hhfaList.size();
        return naListSize;
    }
    public int getOrganizationRecordsDataSize(HttpSession session,String[] params)
    {
        List list=new ArrayList();
        String stateCode=params[4];
        String lgaCode=params[5];
        String orgCode=params[6];
        String orgQueryCriteria=util.getOrganizationRecordsReportCriteria(stateCode,lgaCode,orgCode);
        
        OrganizationRecordsDao dao=new OrganizationRecordsDaoImpl();
        try
        {
            System.err.println("orgQueryCriteria in getOrganizationRecordsDataSize is "+orgQueryCriteria);
            list=dao.getOrganizationListForExport(orgQueryCriteria); 
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("organizationRecList", list);
        return list.size();
    }
    public int getWardRecordsDataSize(HttpSession session,String[] params)
    {
        String orgQueryCriteria=util.getWardQueryForMetadataExport(params);
        WardDao dao=new WardDaoImpl();
        try
        {
            System.err.println("orgQueryCriteria in getWardRecordsDataSize is "+orgQueryCriteria);
           list=dao.getWardListByOrgUnits(orgQueryCriteria);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("WardRecList", list);
        return list.size();
    }
    
    public int getSchoolRecordsDataSize(HttpSession session,String[] params)
    {
        List list=new ArrayList();
        String stateCode=null;
        String lgaCode=null;
        
        stateCode=params[4];
        lgaCode=params[5];
        OvcSchoolDao dao=new OvcSchoolDaoImpl();
        try
        {
            list=dao.getSchoolListForExport(stateCode, lgaCode);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("schoolRecList", list);
        return list.size();
    }
    public int getHouseholdEnrollmentDataSize(HttpSession session,String[] params,String withdrawalQuery)
    {
        List list=new ArrayList();
        String[] orgParams={params[4],params[5],params[6],params[9],null,null,params[0],params[1],params[2],params[3],null,null,null,params[7],params[8]};
        String additionalOrgQuery=util.getHVIReportCriteriaForExport(orgParams);

        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        try
        {
            list=hhedao.getListOfHouseholdEnrollment(additionalOrgQuery+withdrawalQuery,null);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //System.err.println("list size in aproc.getHouseholdEnrollmentDataSize(HttpSession session,String[] params) is "+list.size());
        session.setAttribute("hheRecList", list);
        return list.size();
    }
    public int getHouseholdVulnerabilityAssessmentDataSize(HttpSession session,String[] params,String withdrawalQuery)
    {
        List list=new ArrayList();
        String[] orgParams={params[4],params[5],params[6],params[9],null,null,params[0],params[1],params[2],params[3],null,null,null,params[7],params[8]};
        String additionalOrgQuery=util.getHVIReportCriteriaForExport(orgParams);

        HouseholdVulnerabilityAssessmentDao hvadao=new HouseholdVulnerabilityAssessmentDaoImpl();
        try
        {
            list=hvadao.getListOfHouseholdVulnerabilityAssessments(additionalOrgQuery);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("hvaRecList", list);
        return list.size();
    }
    public int getCaregiverDataSize(HttpSession session,String[] params,String withdrawalQuery)
    {
        List list=new ArrayList();
        String[] orgParams={params[4],params[5],params[6],params[9],null,null,params[0],params[1],params[2],params[3],null,null,null,params[7],params[8]};
        String additionalOrgQuery=util.getHVIReportCriteriaForExport(orgParams);

        CaregiverDao cgiverDao=new CaregiverDaoImpl();
        try
        {
            list=cgiverDao.getListOfCaregivers(additionalOrgQuery+withdrawalQuery);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("caregiverRecList", list);
        return list.size();
    }
    
    public int getHouseholdServiceDataSize(HttpSession session,String[] params,String withdrawalQuery)
    {
        List list=new ArrayList();
        String[] orgParams={params[4],params[5],params[6],params[9],null,null,params[0],params[1],params[2],params[3],null,null,null,params[7],params[8]};
        String additionalOrgQuery=util.getHVIServiceReportCriteriaByDateOfEntry(orgParams);
                
        HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
        try
        {
            list=hhsdao.getHouseholdServiceList(additionalOrgQuery+withdrawalQuery);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("hhsRecList", list);
        return list.size();
    }
    public int getOrganizationalAssessmentDataSize(HttpSession session,String[] params)
    {
        List list=new ArrayList();
        OrganizationalCapacityAssessmentDao dao=new OrganizationalCapacityAssessmentDaoImpl();
        String orgCode=util.getOrgLevelId(params[4], params[5], params[6]);
        try
        {
           list=dao.getOrganizationalCapacityAssessmentRecords(orgCode);//.getOrganizationalCapacityAssessment();
           if(list==null)
           {
               list=new ArrayList();
           }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("listOfOrganizationalAssessment", list);
        return list.size();
    }
    public int getFollowupSurveyRecordsDataSize(HttpSession session,String[] params,String withdrawalQuery)
    {
        String startDate="All";
        String endDate="All";

        if(params[0] !=null && !params[0].equalsIgnoreCase("All"))
        {
            startDate=util.getStartDate(params);
            endDate=util.getEndDate(params);
        }
        String stateCode=params[4];
        String lgaCode=params[5];
        String orgCode=params[6];
        String partnerName=params[7];
        String partnerCode=params[8];
        String orgQueryCriteria=util.getFollowupSurveyIdQueryCriteria(stateCode, lgaCode, orgCode,partnerCode);
        
        List list=new ArrayList();
        FollowupDao dao=new FollowupDaoImpl();
        try
        {
           list=dao.getFollowupRecords(orgQueryCriteria+withdrawalQuery, startDate, endDate);
           if(list==null)
           {
               list=new ArrayList();
           }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("listOffollowupSurveyRecords", list);
        return list.size();
    }
    public int getReferralRecordsDataSize(HttpSession session, String[] params)
    {
        String startDate="All";
        String endDate="All";

        if(params[0] !=null && !params[0].equalsIgnoreCase("All"))
        {
            startDate=util.getStartDate(params);
            endDate=util.getEndDate(params);
        }
        String[] orgParams={params[4],params[5],params[6],params[9],params[0],params[1],params[2],params[3],null,null,params[7],params[8],"All"};
        //orgParams={params[4],params[5],params[6]};
        //String additionalOrgQuery=util.getReferralIdQueryCriteria(orgParams);
        List list=new ArrayList();
        OvcReferralDao dao=new OvcReferralDaoImpl();
        try
        {
            list=dao.getVulnerableHouseholdReferralList(orgParams);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("referralRecordsList", list);
        return list.size();
    }
    public String processOvcByNameAndCaregiver(String reqParam,HttpServletRequest request)
    {
        Ovc ovcObj = null;
        String[] params=reqParam.split(":");
           try
           {
                OvcDao dao = new OvcDaoImpl();
                list = dao.getOvcByNameAndCaregiver(params[0],params[1],params[2]);
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                Ovc ovc = (Ovc)s;

                ovcObj = new Ovc();
                ovcObj.setOvcId(ovc.getOvcId());
                ovcObj.setDateEnrollment(ovc.getDateEnrollment());
                ovcObj.setState(ovc.getState());
                ovcObj.setLga(ovc.getLga());
                ovcObj.setWard(ovc.getWard());
                ovcObj.setLastName(ovc.getLastName());
                ovcObj.setFirstName(ovc.getFirstName());
                ovcObj.setGender(ovc.getGender());
                ovcObj.setAge(ovc.getAge());
                ovcObj.setAgeUnit(ovc.getAgeUnit());

                List ovcMatches = new ArrayList();
                ovcMatches.add(ovcObj);
                request.setAttribute("ovcMatches", ovcMatches);
                request.getSession().setAttribute("ovcMatches", ovcMatches);
                param+="duplicate;";
            }
            return param+="no duplicate;";
    }
    public String processCboSetup()
    {
            String lgaCode = "";
            String stateCode ="";
            String cboCode ="";
           try
           {
                CboSetupDao dao = new CboSetupDaoImpl();
                list = dao.getCboSetupInfo();
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                CboSetup setup = (CboSetup)s;
                lgaCode = setup.getLga_code();
                stateCode = setup.getState_code();
                cboCode = setup.getUserName();
                param+=stateCode+";"+lgaCode+";"+cboCode+";";
            }
            try
            {
                StatesDao dao = new StatesDaoImpl();
                list = dao.getState(stateCode);
                for (Object s: list)
                {
                    States state = (States)s;
                    String name = state.getName();
                    param+=name+";";
                }

                LgaDao ldao=new LgaDaoImpl();
                list = ldao.getLgaByLgaCode(lgaCode);
                for (Object s: list)
                {
                    Lgas lga = (Lgas)s;
                    String name = lga.getLga_name();
                    param+=name+";";
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        return param;
    }
    public String getLgaFromEnrollment(String stateCode)
    {
           try
           {
                list = util.getHouseholdEnrollmentDaoInstance().getDistinctLgaCodesByStateCode(stateCode);
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                param+=((String)s)+";";
            }
        return param;
    }
    public String getCboFromEnrollment(String stateName,String lgaName)
    {
           try
           {
                CboDao dao = new CboDaoImpl();
                list = dao.getCboFromEnrollment(stateName, lgaName);
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                param+=((String)s)+";";
            }
        return param;
    }
    public String getWardsFromEnrollment(String stateName,String wardName)
    {
           try
           {
                WardDao dao = new WardDaoImpl();
                list = dao.getWardsFromEnrollment(stateName, wardName);
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                param+=((String)s)+";";
            }
        return param;
    }
    public List getLgasPerState(String stateId)
    {
        List lgaList=new ArrayList();
           try
           {
                LgaDao dao = new LgaDaoImpl();
                list = dao.getLgaList(stateId);
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                Lgas lga = (Lgas)s;
                lgaList.add(lga);
            }
        return lgaList;
    }
    public String processLga(String stateId)
    {
           try
           {
                LgaDao dao = new LgaDaoImpl();
                list = dao.getLgaList(stateId);
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                Lgas lga = (Lgas)s;
                String name = lga.getLga_name().trim();
                String code = lga.getLga_code();
                param+=name+";"+code+";";
            }
        return param;
    }
    public String processSchools(String stateId,String schoolType)
    {
           try
           {
               //System.err.println("stateId in aproc is"+stateId+"schoolType is "+schoolType);
                OvcSchoolDao dao = new OvcSchoolDaoImpl();
                list = dao.getSchoolList(stateId);
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                OvcSchool school = (OvcSchool)s;
                String name = school.getName().trim();
                param+=name+";";
            }
        return param;
    }
    public String getSchoolListFromEnrollment(String stateName)
    {
           try
           {
                OvcSchoolDao dao = new OvcSchoolDaoImpl();
                list = dao.getSchoolListFromEnrollment(stateName);
           }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                param+=((String)s).trim()+";";
            }
        return param;
    }
    public String getSchoolDetails(String school_id)
    {
        OvcSchool school=null;
           try
           {
                OvcSchoolDao dao = new OvcSchoolDaoImpl();
                school = dao.getSchoolDetails(Integer.parseInt(school_id));
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            if(school !=null)
            {
                //OvcSchool school = (OvcSchool)s;
                String name = school.getName();
                if(name !=null)
                name=name.trim();
                int code = school.getId();
                String lga=school.getLga();
                String state=school.getState();
                String address=school.getAddress();
                String schType=school.getType();
                param+=code+";"+name+";"+lga+";"+state+";"+schType+";"+address+";";
            }
        return param;
    }
    public User getUser(String user_name)
    {
        User user=null;
           try
           {
                UserDao dao = new UserDaoImpl();
                user=dao.getUser(user_name);
                
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            
        return user;
    }
    public String getUserDetails(String user_name)
    {
        //AppUtility appUtil=new AppUtility();
           try
           {
                UserDao dao = new UserDaoImpl();
                list = dao.getUserInfo(user_name);
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                User user = (User)s;
                String fname = user.getFirstName().trim();
                String lname = user.getLastName();
                String userGrpId=user.getUserGroupId();
                String orgUnitGrpIds=user.getOrgUnitGroupId();
                String username=user.getUsername();
                String userrole=user.getUserroleId().trim();
                String userCanChangePwd=user.getChangePwd();
                String userCanViewClientDetails=user.getViewClientDetails();
                String accountStatus=user.getAccountStatus();
                String partnerCodes=user.getPartnerCodes();
                param+=fname+";"+lname+";"+username+";"+orgUnitGrpIds+";"+userrole+";"+userCanChangePwd+";"+userCanViewClientDetails+";"+user.getAddress()+";"+user.getPhone()+";"+user.getEmail()+";"+accountStatus+";"+userGrpId+";"+partnerCodes;
            }
        return param;
    }
    public String processAllStates()
    {
           try
           {
                StatesDao dao = new StatesDaoImpl();
                list = dao.getStates();
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                States state = (States)s;
                String name = state.getName().trim();
                String code = state.getState_code();
                param+=name+";"+code+";";
            }
        return param;
    }
    public String processAllLgas()
    {
           try
           {
                LgaDao dao = new LgaDaoImpl();
                list = dao.getAllLgas();
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                Lgas lga = (Lgas)s;
                String name = lga.getLga_name().trim();
                String code = lga.getLga_code();
                param+=name+";"+code+";";
            }
        return param;
    }
    public String processAllCbos()
    {
           try
           {
                CboDao dao = new CboDaoImpl();
                list = dao.getAllCbos();
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                Cbos cbo = (Cbos)s;
                String name = cbo.getCbo_name().trim();
                String code = cbo.getCbo_code();
                param+=name+";"+code+";";
            }
        return param;
    }
    public String processAllWards()
    {
           try
           {
                WardDao dao = new WardDaoImpl();
                list = dao.getAllWards();
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                Wards ward = (Wards)s;
                String name = ward.getWard_name().trim();
                String code=ward.getWard_code();
                param+=name+";"+code+";";
            }
        return param;
    }
    public List getCboListPerLga(String lgaId)
    {
        List cboList=new ArrayList();
           try
           {
                CboDao dao = new CboDaoImpl();
                list = dao.getCboList(lgaId);
           }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                Cbos cbo = (Cbos)s;
                cboList.add(cbo);
            }
           return cboList;
    }
    public String processCbo(String lgaId)
    {
           try
           {
                CboDao dao = new CboDaoImpl();
                list = dao.getCboList(lgaId);
           }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                Cbos cbo = (Cbos)s;
                String name = cbo.getCbo_name().trim();
                String code = cbo.getCbo_code();
                param+=name+";"+code+";";
            }
           return param;
    }
    public String getWardDetails(String wardId)
    {
           try
           {
                WardDao dao = new WardDaoImpl();
                list = dao.getWardByWardCode(wardId);
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                Wards ward = (Wards)s;
                String ward_name = ward.getWard_name().trim();
                String ward_code=ward.getWard_code();
                //String lga_code=ward.getLga_code();
                //String cbo_code=ward.getCbo_code();
                param+=ward_code+";"+ward_name+";";
            }
        return param;
    }
    public String processWard(HttpSession session,String stateLgacboId)
    {
           try
           {
               String[] codeArray=stateLgacboId.split("-");
               //System.err.println("stateLgacboId is "+stateLgacboId);
                WardDao dao = new WardDaoImpl();
                list=dao.getWardByLgaCodeAndCboId(codeArray[1], codeArray[0]);
                //list = dao.getWardByCboId(cboId);
                session.setAttribute("lastwardlist", list);
           }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                Wards ward = (Wards)s;
                String name = ward.getWard_name();
                String code=ward.getWard_code();
                param+=name+";"+code+";";
            }
        return param;
    }
    /*public String getStateDetails(String state_code)
    {
           try
           {
                StatesDao dao = new StatesDaoImpl();
                list = dao.getState(state_code);
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                States state = (States)s;
                String name = state.getName().trim();
                String code = state.getState_code();
                param+=code+";"+name+";";
            }
        return param;
    }*/
    public String getLgaDetails(String lga_code)
    {
           try
           {
                LgaDao dao = new LgaDaoImpl();
                list = dao.getLgaByLgaCode(lga_code);
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                Lgas lga = (Lgas)s;
                String name = lga.getLga_name().trim();
                String code = lga.getLga_code();
                String stateCode=lga.getState_code();
                param+=code+";"+name+";"+stateCode+";";
            }
        return param;
    }
    public String getCboDetails(String cbo_code)
    {
           try
           {
                CboDao dao = new CboDaoImpl();
                list = dao.getCbos(cbo_code);
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                Cbos cbo = (Cbos)s;
                String code = cbo.getCbo_code();
                String name = cbo.getCbo_name().trim();
                String lgaCode=cbo.getLga_code();
                String phone=cbo.getPhone();
                String email=cbo.getEmail();
                String address=cbo.getAddress();
                param+=code+";"+name+";"+lgaCode+";"+phone+";"+email+";"+address+";";
            }
        return param;
    }
    public String processOvcInfo(String ovcId)
    {
            Ovc ovc =new Ovc();
           try
           {
                OvcDao dao = new OvcDaoImpl();
               ovc =(Ovc)dao.getOvc(ovcId);
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
           String id=ovc.getOvcId();
           param+=id+";";
        return param;
    }
}
