/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.nomis.nomisutils.DataEncryption;
import com.fhi.kidmap.business.FollowupSurvey;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.Indicators;
import com.fhi.kidmap.business.NutritionAssessment;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.ChildStatusIndexDao;
import com.fhi.kidmap.dao.ChildStatusIndexDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.DqaDao;
import com.fhi.kidmap.dao.DqaDaoImpl;
import com.fhi.kidmap.dao.FollowupDao;
import com.fhi.kidmap.dao.FollowupDaoImpl;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.NutritionAssessmentDao;
import com.fhi.kidmap.dao.NutritionAssessmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author COMPAQ USER
 */
public class OvcRecords implements Serializable
{
    List list=null;
    DataEncryption de=new DataEncryption();
    DaoUtil util=new DaoUtil();
    AppUtility appUtil=new AppUtility();
    public OvcRecords()
    {

    }
    public List getDateLabelsForTitle(String[] dateParams)
    {
        List dateLabel=new ArrayList();
        String[] dateList=new String[2];
        dateList[0]=util.getStartDate(dateParams);
        dateList[1]=util.getEndDate(dateParams);
        dateLabel=util.getDateLabels(dateList);
        return dateLabel;
    }
    public void getFollowupRecords(String[] orgParam,HttpServletRequest request)
    {
        List list=new ArrayList();
        List followupList=new ArrayList();
        String additionalQueryCriteria=util.getQueryCriteria(orgParam)+util.getOvcFollowupReportDateCriteria(orgParam);
        FollowupDao fdao=new FollowupDaoImpl();
        try
        {
            //System.err.println("additionalQueryCriteria is "+additionalQueryCriteria);
            followupList=fdao.getDistinctFollowup(additionalQueryCriteria);
            HouseholdEnrollment hhe=null;
            CaregiverDao cgiverdao=new CaregiverDaoImpl();
            Caregiver cgiver=null;
            Ovc ovc=null;
            String caregiverId=null;
            for(Object o:followupList)
            {
                Object[] objArray=(Object[])o;
                hhe=(HouseholdEnrollment)objArray[0];
                ovc=(Ovc)objArray[1];
                FollowupSurvey survey=(FollowupSurvey)objArray[2];
                caregiverId=survey.getCaregiverId();
                if(caregiverId !=null)
                {
                    cgiver=cgiverdao.getCaregiverByCaregiverId(caregiverId);
                    if(cgiver !=null)
                    {
                        survey.setCgiverHivStatus(cgiver.getCgiverHivStatus());
                        survey.setUpdatedCaregiverAddress(cgiver.getCaregiverAddress());
                        survey.setUpdatedCaregiverAge(cgiver.getCaregiverAge());
                        survey.setUpdatedCaregiverGender(cgiver.getCaregiverGender());
                        survey.setUpdatedCaregiverName(cgiver.getCaregiverFullName());
                        survey.setUpdatedCaregiverOccupation(cgiver.getCaregiverOccupation());
                        survey.setUpdatedCaregiverPhone(cgiver.getCaregiverPhone());
                    }
                }
                
                survey.setDateOfSurvey(appUtil.convertMysqlDateTomonthDayYear(survey.getDateOfSurvey()));
                list.add(survey);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        request.setAttribute("followup", list);
    }
    public void getFollowupCsi(HttpServletRequest request,String ovcId)
    {
        List csiList=new ArrayList();
        List list=new ArrayList();
        List ovcList=new ArrayList();
        Ovc ovc=new Ovc();
        HouseholdEnrollment hhe=null;
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        ChildStatusIndexDao csidao=new ChildStatusIndexDaoImpl();
        try
        {
            csiList=csidao.getCsiListOrderedByDateAsc(ovcId);
            if(csiList !=null && !csiList.isEmpty())
            {
                ChildStatusIndex csi=null;
                for(int i=0; i<csiList.size(); i++)
                {
                    csi=(ChildStatusIndex)csiList.get(i);
                    csi.setCsiDate(appUtil.convertMysqlDateTomonthDayYear(csi.getCsiDate()));
                    list.add(csi);
                    if(i==0)
                    {
                        OvcDao odao=new OvcDaoImpl();
                        ovcList = odao.getOVC(csi.getOvcId());
                        if(ovcList !=null && !ovcList.isEmpty())
                        {
                            ovc=(Ovc)ovcList.get(0);
                            hhe=hhedao.getHouseholdEnrollment(ovc.getHhUniqueId());
                            if(hhe !=null)
                            {
                                ovc.setState(util.getStateName(hhe.getStateCode()));
                                ovc.setLga(util.getLgaName(hhe.getLgaCode()));
                                ovc.setCompletedbyCbo(util.getOrganizationName(hhe.getOrgCode()));
                                ovc.setWard(util.getWardName(hhe.getCommunityCode()));
                            }
                        }
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        ovc.setDateEnrollment(appUtil.convertMysqlDateTomonthDayYear(ovc.getDateEnrollment()));
        request.setAttribute("ovc", ovc);
        request.setAttribute("csihistory", list);
    }
    public void getOvcsPerCaregiver(HttpSession session,String caregiverId)
    {
        OvcDao dao=new OvcDaoImpl();
        String[] params=(String[])session.getAttribute("caregiverlistparam");
        String additionalQuery=util.getQueryCriteria(params);
        int SNo=0;
        List ovcList=new ArrayList();
        List subList=new ArrayList();
        List mainList=new ArrayList();
        try
        {
            String rowColour="#FFFFFF";
            Ovc ovc=null;
            ovcList=dao.getListOfOvcsPerCaregiver(additionalQuery, caregiverId);
            List encryptedList=de.encryptOvcInfo(ovcList, session);
            for(Object obj:encryptedList)
            {
                SNo++;
                if(SNo%2==0)
                rowColour="#DDDDDD";
                else
                rowColour="#FFFFFF";
                ovc=(Ovc)obj;
                ovc.setWard(util.getWardName(ovc.getWard()));
                subList=new ArrayList();
                subList.add(rowColour);
                subList.add(SNo);
                subList.add(ovc);
                mainList.add(subList);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //session.setAttribute("caregivername", caregiverName);
        session.setAttribute("listofovcspercaregiver", mainList);
    }
    public void getOvcMthlySummaryData(HttpSession session,List paramList,String ageSegType)
    {
        List summaryList=new ArrayList();
        List econStrengthList=new ArrayList();
        List nationalMthlySummaryList=new ArrayList();
        List stateMthlySummaryList=new ArrayList();
        List lgaMthlySummaryList=new ArrayList();
        List cboMthlySummaryList=new ArrayList();
        List allList=new ArrayList();
        List orgList=new ArrayList();
        ReportUtility rutil=new ReportUtility();

        String stateCode=(String)paramList.get(0);
        String lgaCode=(String)paramList.get(1);
        String cboCode=(String)paramList.get(2);
        String wardCode=(String)paramList.get(3);
        String startMonth=paramList.get(4).toString();
        String startYear=paramList.get(5).toString();
        String endMonth=paramList.get(6).toString();
        String endYear=paramList.get(7).toString();
        String partnerCode=paramList.get(8).toString();
        
        /*String stateName=util.getStateName((String)paramList.get(0));
        String lgaName=util.getLgaName((String)paramList.get(1));
        String cboName=util.getOrganizationName((String)paramList.get(2));
        String partnerName=util.getPartnerName((String)paramList.get(7));*/
        String[] queryParam=rutil.getQueryParam(paramList);//{stateCode,lgaCode,cboCode,startMonth,startYear,endMonth,endYear,null,null,null,null,null,null,partnerName,partnerCode};
        String[] param=rutil.getLabelParam(paramList);
                //{stateCode,lgaCode,cboCode,startMonth,startYear,endMonth,endYear,stateName,lgaName,cboName,null,null,null,partnerName,partnerCode};
        String[] reportLevel={"<td class='orglabel' width='30'><b>State:</b></td><td class='orglabel' >"+param[7]+"</td>","<td class='orglabel' width='20' ><b>LGA: </b></td><td class='orglabel' >"+param[8]+"</td>","<td class='orglabel' ><b>Organization name:</b></td><td class='orglabel' >"+param[9]+"</td>","<td class='orglabel' ><b>Partner:</b></td><td class='orglabel' >"+param[13]+"</td>"};
        String reportParam=null;
        
        //EnrollmentReportDao dao=new EnrollmentReportDaoImpl();
        MonthlySummaryFormReport dao=new MonthlySummaryFormReport();
        try
        {
            if(stateCode.equals("All"))
            {
                reportParam="National";
                allList.addAll(dao.getNoOfStateReportingOVCServicesForTheMth(queryParam));
                allList.addAll(dao.getNoOfLGAReportingOVCServicesForTheMth(queryParam));
                allList.addAll(dao.getNoOfCSOReportingOVCServicesForTheMth(queryParam));
                nationalMthlySummaryList.add(allList);
                reportLevel[0]=" ";
                reportLevel[1]=" ";
                reportLevel[2]=" ";
                //call the method that gives the national headings
            }
            else if(lgaCode.equals("All"))
            {
                reportParam="State";
                allList.addAll(dao.getNoOfLGAReportingOVCServicesForTheMth(queryParam));
                allList.addAll(dao.getNoOfCSOReportingOVCServicesForTheMth(queryParam));
                stateMthlySummaryList.add(allList);
                reportLevel[1]=" ";
                reportLevel[2]=" ";
                orgList.add(1);

            }
            else if(cboCode.equals("All"))
            {
                //call the method that gives the LGA headings
                reportParam="LGA";
                lgaMthlySummaryList.add(dao.getNoOfCSOReportingOVCServicesForTheMth(queryParam));
                reportLevel[2]=" ";
                orgList.add(2);
            }
            else
            {
                reportParam="CSO";
                orgList.add(3);
                //call the method that gives the CBO headings
            }
            //System.err.println("inside OvcRecords.getOvcMthlySummaryData(HttpSession session,List paramList)");
            //if(ageSegType.equalsIgnoreCase("new"))
            summaryList=dao.getOvcMthlySummaryData(queryParam,ageSegType);
            econStrengthList=dao.getOvcMthlyEconStrengtheningData(queryParam,ageSegType);
        }
        catch(Exception e)
        {
             e.printStackTrace();
        }
        String startMonthInLetter=util.getFullMonthAsString(Integer.parseInt(startMonth));
        String endMonthInLetter=util.getFullMonthAsString(Integer.parseInt(endMonth));
        param[3]=startMonthInLetter;
        param[5]=endMonthInLetter;
        session.setAttribute("mthSummarySessionObj", summaryList);
        session.setAttribute("econStrengthList", econStrengthList);
        session.setAttribute("mthlysummparams", param);
        session.setAttribute("reportParam", reportParam);
        session.setAttribute("reportLevel", reportLevel);
        session.setAttribute("orgReportList", orgList);

        session.setAttribute("nationalMthlySummaryList", nationalMthlySummaryList);
        session.setAttribute("stateMthlySummaryList", stateMthlySummaryList);
        session.setAttribute("lgaMthlySummaryList", lgaMthlySummaryList);
        session.setAttribute("cboMthlySummaryList", cboMthlySummaryList);
    }
    public void doDqa(HttpSession session,List paramList)
    {
        List list=new ArrayList();
        DqaDao dao=new DqaDaoImpl();
        String stateCode=(String)paramList.get(0);
        String lgaCode=(String)paramList.get(1);
        String cboCode=(String)paramList.get(2);
        String partnerCode=(String)paramList.get(3);
        String stateName=util.getStateName(stateCode);
        String lgaName=util.getLgaName(lgaCode);
        String cboName=util.getOrganizationName(cboCode);
        String partnerName=util.getPartnerName(partnerCode);
        String[] dqaParams={stateCode,lgaCode,cboCode,"All",null,null,null,null,null,null,null,null,null,partnerName,partnerCode};
        String[] dqaOrgParams={stateName,lgaName,cboName,partnerName};
        try
        {
            list=dao.getDqaCount(dqaParams);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        session.setAttribute("dqaparams", dqaParams);
        session.setAttribute("dqaOrgParams", dqaOrgParams);
        session.setAttribute("dqaState", stateName);
        session.setAttribute("dqaCount", list);
    }
    public void getDqaList(HttpSession session,int index)
    {
        List list=new ArrayList();
        String[] dqaParams=(String[])session.getAttribute("dqaparams");
        DqaDao dao=new DqaDaoImpl();
        try
        {
            list=dao.getDqaOvcList(dqaParams, index);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("dqaListTitle", list.get(0));
        list.remove(0);
        session.setAttribute("dqaList", list);
    }
    public String[] getParams(List paramList)
    {
        String stateCode=(String)paramList.get(0);
        String lgaCode=(String)paramList.get(1);
        String cboCode=(String)paramList.get(2);
        String wardCode=(String)paramList.get(16);
        
        String month=paramList.get(3).toString();
        String year=paramList.get(4).toString();

        String enrollmentStartMth=paramList.get(5).toString();
        String enrollmentStartYear=paramList.get(6).toString();
        String enrollmentEndMth=paramList.get(7).toString();
        String enrollmentEndYear=paramList.get(8).toString();
        
        String startAge=paramList.get(13).toString();
        String endAge=paramList.get(14).toString();
        String partnerCode=(String)paramList.get(15);

        String stateName=util.getStateName(stateCode);
        String lgaName=util.getLgaName(lgaCode);
        String cboName=util.getOrganizationName(cboCode);
        String wardName=util.getWardName(wardCode);
        String[] param={stateCode,lgaCode,cboCode,month,year,stateName,lgaName,enrollmentStartMth,enrollmentStartYear,enrollmentEndMth,enrollmentEndYear,"All","All","All","All",startAge,endAge,cboName,partnerCode,wardCode,wardName};
        return param;
    }
    public void getOvcCount(HttpSession session,String sex,List paramList,String[] multipleLgas,String[] selectedIndicators,String userGroupId)
    {
        List list=new ArrayList();
        String[] param=getParams(paramList);
        SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
        //EnrollmentReportDao dao=new EnrollmentReportDaoImpl();
        try
        {
            //This is call for OVC indicators only
            if(multipleLgas !=null && multipleLgas.length>0)
            {
                for(int i=0; i<multipleLgas.length; i++)
                {
                    paramList.set(1,multipleLgas[i]);
                    param=getParams(paramList);
                    list.addAll(ssrg.getOvcEnrolledSummStatistics(sex,param,selectedIndicators));
                }
            }
            else
            list.addAll(ssrg.getOvcEnrolledSummStatistics(sex,param,selectedIndicators));
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        String agegroup=util.getAgeForTitle(param[15], param[16]);
        //"Age: "+ param[15]+" to "+param[16];
        String period="Period: All";
        if(!param[7].equalsIgnoreCase("all") && !param[8].equalsIgnoreCase("all") && !param[9].equalsIgnoreCase("all") && !param[10].equalsIgnoreCase("all"))
        period="Period: "+appUtil.getFullMonthAsString( Integer.parseInt(param[7]))+" "+param[8]+" to "+appUtil.getFullMonthAsString( Integer.parseInt(param[9]))+" "+ param[10];
        
        //This is call for household indicators only
        List assessmentList=ssrg.getSummaryStaticsCountWithoutGenderDisaggregation(param,selectedIndicators);
        
        session.setAttribute("orgAssessmentList", assessmentList);
        session.setAttribute("summaryStatistics", list);
        session.setAttribute("summaryStatparams", param);
        session.setAttribute("multipleLgas", multipleLgas);
        session.setAttribute("period", period);
        session.setAttribute("agegroup", agegroup);
    }
    public String getSummStatisticsList(HttpSession session,String[] reqparams,String type)
    {
        String reportType="summstatOvcList";
        List list=new ArrayList();
        String indicatorCode=reqparams[1];
        //System.err.println("indicatorCode in getSummStatisticsList is "+indicatorCode);
        //int index=Integer.parseInt(reqparams[1]);
        String param[]=(String[])session.getAttribute("summaryStatparams");
        String multipleLgaParams[]=(String[])session.getAttribute("multipleLgas");
        String[] caregiverParams={param[0],param[1],param[2],"All",null,null,null,null,param[15],param[16]};
        //EnrollmentReportDao dao=new EnrollmentReportDaoImpl();
        SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
        try
        {
            if(type.equals("all"))
            {
                if(multipleLgaParams !=null && multipleLgaParams.length>0)
                {
                    for(int i=0; i<multipleLgaParams.length; i++)
                    {
                        param[1]=multipleLgaParams[i];
                        list.addAll(ssrg.getSummaryStatisticList(param, reqparams));
                    }
                }
                else
                list=(ssrg.getSummaryStatisticList(param, reqparams));
            }
            IndicatorWarehouse indw=new IndicatorWarehouse();
            Indicators ind=indw.getIndicatorById(indicatorCode);
            session.removeAttribute("summaryStatisticsList");
            session.removeAttribute("hhsRecords");
            session.removeAttribute("caregiverList");
            session.removeAttribute("hviRecords");
            //System.err.println("list size in getSummStatisticsList is "+list.size());
            if(indicatorCode.equalsIgnoreCase("cgservedhes") || indicatorCode.equalsIgnoreCase("cgtesforhiv") || indicatorCode.equalsIgnoreCase("cgiverserve") || indicatorCode.equalsIgnoreCase("cgivehivacc") || indicatorCode.equalsIgnoreCase("cgnenrserrp") || indicatorCode.equalsIgnoreCase("cgactiserrp") || indicatorCode.equalsIgnoreCase("cgsergradrp") || indicatorCode.equalsIgnoreCase("cgserltfurp") || indicatorCode.equalsIgnoreCase("cgserdiedrp") || indicatorCode.equalsIgnoreCase("cgsermigrrp") || indicatorCode.equalsIgnoreCase("cgsertranrp") || indicatorCode.equalsIgnoreCase("cgenrsilcrp") || indicatorCode.equalsIgnoreCase("cginforumrp"))
            {
                HouseholdReports hhr=new HouseholdReports();
                List hviList=new ArrayList();
                if(list !=null && list.size()>0)
                { 
                    hviList=hhr.processCaregiverList((List)list.get(0), session);
                    session.setAttribute("hhsRecords", hviList);
                }
                session.setAttribute("hhsParam", caregiverParams);
                session.setAttribute("hviParam", caregiverParams);
                session.removeAttribute("summaryStatisticsList");
                reportType="caregiverList";
                return reportType;
            }
            else if(indicatorCode.equalsIgnoreCase("cgnenrolled") || indicatorCode.equalsIgnoreCase("cgcenrolled") || indicatorCode.equalsIgnoreCase("cgeenrolled") || indicatorCode.equalsIgnoreCase("cgknowndeat") || indicatorCode.equalsIgnoreCase("cggraduated") || indicatorCode.equalsIgnoreCase("cgmigrated1")||indicatorCode.equalsIgnoreCase("cglosttofup")||indicatorCode.equalsIgnoreCase("cgtransferd") || indicatorCode.equalsIgnoreCase("cginactivrp"))
            {
                session.removeAttribute("summaryStatisticsList");
                HouseholdReports hhr=new HouseholdReports();
                List caregiverList=new ArrayList();
                if(list !=null && list.size()>0)
                { 
                    caregiverList=hhr.encryptCaregiverInformation((List)list.get(0), session);
                    session.setAttribute("caregiverList", caregiverList);
                }
                session.setAttribute("hviParam", caregiverParams);
                reportType="caregiverRegister";
                return reportType;
            }
            else if(indicatorCode.equalsIgnoreCase("cgposcevulb") || indicatorCode.equalsIgnoreCase("cgposcemrvb") || indicatorCode.equalsIgnoreCase("cgposcemovb"))
            {
                //System.err.println("inside getSummStatisticsList is where indicatorCode is "+indicatorCode);
                session.removeAttribute("summaryStatisticsList");
                HouseholdReports hhr=new HouseholdReports();
                List caregiverList=new ArrayList();
                if(list !=null && list.size()>0)
                { 
                    caregiverList=hhr.encryptCaregiverInformation((List)list.get(0), session);
                    session.setAttribute("caregiverList", caregiverList);
                }
                session.setAttribute("hviParam", caregiverParams);
                reportType="caregiverRegister";
                //System.err.println("reportType in getSummStatisticsList is "+reportType);
                return reportType;
            }
            else if(indicatorCode.equalsIgnoreCase("cghivunkcen") || indicatorCode.equalsIgnoreCase("cghivnegcen") || indicatorCode.equalsIgnoreCase("cghivposcen")  || indicatorCode.equalsIgnoreCase("cghivunkeen") || indicatorCode.equalsIgnoreCase("cghivnegeen") || indicatorCode.equalsIgnoreCase("cghivposeen") || indicatorCode.equalsIgnoreCase("cghivcencar") || indicatorCode.equalsIgnoreCase("cghivcenart"))
            {
                //System.err.println("inside getSummStatisticsList is where indicatorCode is "+indicatorCode);
                session.removeAttribute("summaryStatisticsList");
                HouseholdReports hhr=new HouseholdReports();
                List caregiverList=new ArrayList();
                if(list !=null && list.size()>0)
                { 
                    caregiverList=hhr.encryptCaregiverInformation((List)list.get(0), session);
                    session.setAttribute("caregiverList", caregiverList);
                }
                session.setAttribute("hviParam", caregiverParams);
                reportType="caregiverRegister";
                System.err.println("reportType in getSummStatisticsList is "+reportType);
                return reportType;
            }
            else if(indicatorCode.equalsIgnoreCase("cgscrnfortb") || indicatorCode.equalsIgnoreCase("cgnotserved"))
            {
               // System.err.println("indicatorCode is "+indicatorCode);
                DataEncryption de=new DataEncryption();
                List hviList=new ArrayList();
                if(list !=null && list.size()>0)
                { 
                    hviList=de.encryptCaregiverInfo(list, session);
                    session.setAttribute("caregiverList", hviList);
                }
                session.setAttribute("hviParam", caregiverParams);
                session.removeAttribute("summaryStatisticsList");
                reportType="caregiverRegister";
                return reportType;
            }
            else if(indicatorCode.equalsIgnoreCase("hhemergneed") || indicatorCode.equalsIgnoreCase("hhheadserve") || indicatorCode.equalsIgnoreCase("hhdservedrp") || indicatorCode.equalsIgnoreCase("hhdserhesrp"))
            {
                HouseholdReports hhr=new HouseholdReports();
                List hviList=new ArrayList();
                if(list !=null && list.size()>0)
                {
                   hviList=hhr.processHheList((List)list.get(0), session);
                   session.setAttribute("hviRecords", hviList);
                }
                //System.err.println("hviList size is "+hviList.size());
                session.setAttribute("hviParam", caregiverParams);
                session.removeAttribute("summaryStatisticsList");
                reportType="householdList";
                return reportType;
            }
            else if(indicatorCode.equalsIgnoreCase("hhdEnrolled") || indicatorCode.equalsIgnoreCase("hhnenrolled")||indicatorCode.equalsIgnoreCase("hhcenrolled")||indicatorCode.equalsIgnoreCase("hheenrolled") || indicatorCode.equalsIgnoreCase("hhwithdrawn") || indicatorCode.equalsIgnoreCase("hhgraduated")||indicatorCode.equalsIgnoreCase("hhmigrated1")||indicatorCode.equalsIgnoreCase("hhlosttofup")||indicatorCode.equalsIgnoreCase("hhtransferd")|| indicatorCode.equalsIgnoreCase("hhinactivrp") || indicatorCode.equalsIgnoreCase("hhdbasasses")||indicatorCode.equalsIgnoreCase("hhdcebasass")|| indicatorCode.equalsIgnoreCase("hheefupasse") || indicatorCode.equalsIgnoreCase("hhcefupasse") ||indicatorCode.equalsIgnoreCase("hhbassesper")||indicatorCode.equalsIgnoreCase("hhcebassper"))
            {
                HouseholdReports hhr=new HouseholdReports();
                List hviList=new ArrayList();
                if(list !=null && list.size()>0)
                {
                   hviList=hhr.processHheList((List)list.get(0), session);
                   session.setAttribute("hviRecords", hviList);
                }
                session.setAttribute("hviParam", caregiverParams);
                session.removeAttribute("summaryStatisticsList");
                reportType="householdList";
                return reportType;
            }
            else if(ind !=null && ind.getIndicatorType().equalsIgnoreCase(NomisConstant.HOUSEHOLD_TYPE))
            {
                HouseholdReports hhr=new HouseholdReports();
                List hviList=new ArrayList();
                if(list !=null && list.size()>0)
                {
                   hviList=hhr.processHheList((List)list.get(0), session);
                   session.setAttribute("hviRecords", hviList);
                }
                session.setAttribute("hviParam", caregiverParams);
                session.removeAttribute("summaryStatisticsList");
                reportType="householdList";
                return reportType;
            }
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        List encryptedOvcList=new ArrayList();
        encryptedOvcList=de.encryptOvcInfo(list,session);

        session.setAttribute("summaryStatisticsList", encryptedOvcList);
        return reportType;
    }
    public List getSummStatisticsListForExcelDownload(HttpSession session,String[] reqparams,String type)
    {
        String reportType="summstatOvcList";
        List list=new ArrayList();
        String indicatorCode=reqparams[1];
        //System.err.println("indicatorCode in getSummStatisticsList is "+indicatorCode);
        //int index=Integer.parseInt(reqparams[1]);
        String param[]=(String[])session.getAttribute("summaryStatparams");
        String[] caregiverParams={param[0],param[1],param[2],"All",null,null,null,null,param[15],param[16]};
        //EnrollmentReportDao dao=new EnrollmentReportDaoImpl();
        SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
        String multipleLgaParams[]=(String[])session.getAttribute("multipleLgas");
        try
        {
            if(type.equals("all"))
            {
                if(multipleLgaParams !=null && multipleLgaParams.length>0)
                {
                    for(int i=0; i<multipleLgaParams.length; i++)
                    {
                        param[1]=multipleLgaParams[i];
                        list.addAll(ssrg.getSummaryStatisticList(param, reqparams));
                    }
                }
                else
                list=(ssrg.getSummaryStatisticList(param, reqparams));
            }
            
            
            session.removeAttribute("summaryStatisticsList");
            session.removeAttribute("hhsRecords");
            session.removeAttribute("caregiverList");
            session.removeAttribute("hviRecords");
            //System.err.println("list size in getSummStatisticsList is "+list.size());
            if(indicatorCode.equalsIgnoreCase("cgnenrserrp")|| indicatorCode.equalsIgnoreCase("cgservedhes") || indicatorCode.equalsIgnoreCase("cgtesforhiv") || indicatorCode.equalsIgnoreCase("cgiverserve") || indicatorCode.equalsIgnoreCase("cgivehivacc") || indicatorCode.equalsIgnoreCase("cgactiserrp") || indicatorCode.equalsIgnoreCase("cgsergradrp") || indicatorCode.equalsIgnoreCase("cgserltfurp") || indicatorCode.equalsIgnoreCase("cgserdiedrp") || indicatorCode.equalsIgnoreCase("cgsermigrrp") || indicatorCode.equalsIgnoreCase("cgsertranrp") || indicatorCode.equalsIgnoreCase("cgenrsilcrp") || indicatorCode.equalsIgnoreCase("cginforumrp"))
            {
                HouseholdReports hhr=new HouseholdReports();
                List hviList=new ArrayList();
                if(list !=null && list.size()>0)
                { 
                    hviList=hhr.processCaregiverList((List)list.get(0), session);
                    session.setAttribute("hhsRecords", hviList);
                }
                session.setAttribute("hhsParam", caregiverParams);
                session.setAttribute("hviParam", caregiverParams);
                session.removeAttribute("summaryStatisticsList");
                reportType="caregiverList";
                return hviList;
            }
            else if(indicatorCode.equalsIgnoreCase("cgnenrolled") || indicatorCode.equalsIgnoreCase("cgcenrolled") || indicatorCode.equalsIgnoreCase("cgeenrolled") || indicatorCode.equalsIgnoreCase("cgknowndeat") || indicatorCode.equalsIgnoreCase("cggraduated") || indicatorCode.equalsIgnoreCase("cgmigrated1")||indicatorCode.equalsIgnoreCase("cglosttofup")||indicatorCode.equalsIgnoreCase("cgtransferd"))
            {
                session.removeAttribute("summaryStatisticsList");
                HouseholdReports hhr=new HouseholdReports();
                List caregiverList=new ArrayList();
                if(list !=null && list.size()>0)
                { 
                    caregiverList=hhr.encryptCaregiverInformation((List)list.get(0), session);
                    session.setAttribute("caregiverList", caregiverList);
                }
                session.setAttribute("hviParam", caregiverParams);
                reportType="caregiverRegister";
                return caregiverList;
            }
            else if(indicatorCode.equalsIgnoreCase("cgposcevulb") || indicatorCode.equalsIgnoreCase("cgposcemrvb") || indicatorCode.equalsIgnoreCase("cgposcemovb"))
            {
                //System.err.println("inside getSummStatisticsList is where indicatorCode is "+indicatorCode);
                session.removeAttribute("summaryStatisticsList");
                HouseholdReports hhr=new HouseholdReports();
                List caregiverList=new ArrayList();
                if(list !=null && list.size()>0)
                { 
                    caregiverList=hhr.encryptCaregiverInformation((List)list.get(0), session);
                    session.setAttribute("caregiverList", caregiverList);
                }
                session.setAttribute("hviParam", caregiverParams);
                reportType="caregiverRegister";
                //System.err.println("reportType in getSummStatisticsList is "+reportType);
                return caregiverList;
            }
            else if(indicatorCode.equalsIgnoreCase("cghivunkcen") || indicatorCode.equalsIgnoreCase("cghivnegcen") || indicatorCode.equalsIgnoreCase("cghivposcen")  || indicatorCode.equalsIgnoreCase("cghivunkeen") || indicatorCode.equalsIgnoreCase("cghivnegeen") || indicatorCode.equalsIgnoreCase("cghivposeen") || indicatorCode.equalsIgnoreCase("cghivcencar") || indicatorCode.equalsIgnoreCase("cghivcenart"))
            {
                //System.err.println("inside getSummStatisticsList is where indicatorCode is "+indicatorCode);
                session.removeAttribute("summaryStatisticsList");
                HouseholdReports hhr=new HouseholdReports();
                List caregiverList=new ArrayList();
                if(list !=null && list.size()>0)
                { 
                    caregiverList=hhr.encryptCaregiverInformation((List)list.get(0), session);
                    session.setAttribute("caregiverList", caregiverList);
                }
                session.setAttribute("hviParam", caregiverParams);
                reportType="caregiverRegister";
                System.err.println("reportType in getSummStatisticsList is "+reportType);
                return caregiverList;
            }
            else if(indicatorCode.equalsIgnoreCase("cgscrnfortb") || indicatorCode.equalsIgnoreCase("cgnotserved"))
            {
               // System.err.println("indicatorCode is "+indicatorCode);
                DataEncryption de=new DataEncryption();
                List hviList=new ArrayList();
                if(list !=null && list.size()>0)
                { 
                    hviList=de.encryptCaregiverInfo(list, session);
                    session.setAttribute("caregiverList", hviList);
                }
                session.setAttribute("hviParam", caregiverParams);
                session.removeAttribute("summaryStatisticsList");
                reportType="caregiverRegister";
                return hviList;
            }
            else if(indicatorCode.equalsIgnoreCase("hhheadserve") || indicatorCode.equalsIgnoreCase("hhdservedrp") || indicatorCode.equalsIgnoreCase("hhdserhesrp"))
            {
                HouseholdReports hhr=new HouseholdReports();
                List hviList=new ArrayList();
                if(list !=null && list.size()>0)
                {
                   hviList=hhr.processHheList((List)list.get(0), session);
                   session.setAttribute("hviRecords", hviList);
                }
                //System.err.println("hviList size is "+hviList.size());
                session.setAttribute("hviParam", caregiverParams);
                session.removeAttribute("summaryStatisticsList");
                reportType="householdList";
                return hviList;
            }
            else if(indicatorCode.equalsIgnoreCase("hhdEnrolled") || indicatorCode.equalsIgnoreCase("hhnenrolled")||indicatorCode.equalsIgnoreCase("hhcenrolled")||indicatorCode.equalsIgnoreCase("hheenrolled") || indicatorCode.equalsIgnoreCase("hhwithdrawn") || indicatorCode.equalsIgnoreCase("hhgraduated")||indicatorCode.equalsIgnoreCase("hhmigrated1")||indicatorCode.equalsIgnoreCase("hhlosttofup")||indicatorCode.equalsIgnoreCase("hhtransferd")||indicatorCode.equalsIgnoreCase("hhdbasasses")||indicatorCode.equalsIgnoreCase("hhdcebasass")|| indicatorCode.equalsIgnoreCase("hheefupasse") || indicatorCode.equalsIgnoreCase("hhcefupasse") ||indicatorCode.equalsIgnoreCase("hhbassesper")||indicatorCode.equalsIgnoreCase("hhcebassper"))
            {
                HouseholdReports hhr=new HouseholdReports();
                List hviList=new ArrayList();
                if(list !=null && list.size()>0)
                {
                   hviList=hhr.processHheList((List)list.get(0), session);
                   session.setAttribute("hviRecords", hviList);
                }
                session.setAttribute("hviParam", caregiverParams);
                session.removeAttribute("summaryStatisticsList");
                reportType="householdList";
                return hviList;
            }
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        List encryptedOvcList=new ArrayList();
        encryptedOvcList=de.encryptOvcInfo(list,session);

        session.setAttribute("summaryStatisticsList", encryptedOvcList);
        return encryptedOvcList;
    }
    public void getCsiValues(HttpSession session,List paramList)
    {
        List csiList=new ArrayList();
        List dateLabel=new ArrayList();
        String stateCode=(String)paramList.get(0);
        String lgaCode=(String)paramList.get(1);
        String cboCode=(String)paramList.get(2);
        String wardCode=(String)paramList.get(11);
        String partnerCode=(String)paramList.get(10);
        String fmonth=paramList.get(3).toString();
        String fyear=paramList.get(4).toString();
        String smonth=paramList.get(5).toString();
        String syear=paramList.get(6).toString();
        String startAge=paramList.get(7).toString();
        String endAge=paramList.get(8).toString();
        String csiNo=paramList.get(9).toString();
        String stateName=util.getStateName(stateCode);
        String lgaName=util.getLgaName(lgaCode);
        String cboName=util.getOrganizationName(cboCode);
        String wardName=util.getWardName(wardCode);
        String[] param={stateCode,lgaCode,cboCode,wardCode,fmonth,fyear,smonth,syear,startAge,endAge,partnerCode,stateName,lgaName,cboName,csiNo,wardName};
        String[] dateParams={fmonth,fyear,smonth,syear};

         dateLabel=getDateLabelsForTitle(dateParams);
         String ageTitle=util.getAgeForTitle(startAge,endAge)+"  CSI number="+csiNo;
         String[] indicatorNames=getIndicatorNames();
         ChildStatusIndexDao dao=new ChildStatusIndexDaoImpl();
         
        String subTitle=" between 1st of "+dateLabel.get(0)+" and end of "+dateLabel.get(1);
        for(int i=1; i<13; i++)
        {
           try
           {
                list=dao.getCsiIndicatorCount(i,param);
                list.add(indicatorNames[i-1]);
                csiList.add(list);
           }
           catch(Exception e)
           {
                e.printStackTrace();
           }
        }
        session.setAttribute("ovcCount", csiList);
        session.setAttribute("subtitle", subTitle);
        session.setAttribute("agetitle", ageTitle);
        session.setAttribute("params", param);
    }
    public void getCsiOvcs(HttpSession session,String csiParams)
    {
        List ovcList=new ArrayList();
        List dateLabel=new ArrayList();
        String[] csiValues=csiParams.split(":");
        int factorIndex=Integer.parseInt(csiValues[0]);
        int csiScore=Integer.parseInt(csiValues[1]);
        String param[]=(String[])session.getAttribute("params");
        ChildStatusIndexDao dao=new ChildStatusIndexDaoImpl();
        String[] indicatorNames=getIndicatorNames();
        String[] csiScoreName={"very bad","bad","fair","good",};
        String[] dateParams={param[4],param[5],param[6],param[7]};
        String csiNo=param[14];
        dateLabel=getDateLabelsForTitle(dateParams);
        /*Create a title from the indicator name and csi score and set it as an attribute*/
        String title= csiScoreName[csiScore-1]+" "+indicatorNames[factorIndex-1]+" between 1st of "+dateLabel.get(0)+" and end of "+dateLabel.get(1);
        session.setAttribute("indicatorName", title);
        ovcList.clear();
           try
           {
               list=dao.getCsiOvcList(param,factorIndex,csiScore);
               System.err.println("list size is getCsiOvcs "+list.size());
           }
           catch(Exception e)
           {
                e.printStackTrace();
           }
        List encryptedOvcList=de.encryptOvcInfo(list,session);
            for (Object s: encryptedOvcList)
            {
                Ovc ovc = (Ovc)s;
                ovc.setWard(util.getWardName(ovc.getWard()));
                ovcList.add(ovc);
            }
            session.setAttribute("csiOvcList", ovcList);
    }
    public String[] getIndicatorNames()
    {
        String[] indicatorNames={"Psychosocial score (emotional health)","Psychosocial score (social behavior)","Food and nutrition score (food security)","Food and nutrition score (nutrition and growth)","Health score (wellness)","Health score (health care services)","Education and work score (performance)","Education and work score (school work)","Protection score (abuse and exploitation)","Protection score (legal protection)","Shelter and care score (shelter)","Shelter and care score (care)"};
        return indicatorNames;
    }
    public String[] getMthlySummaryIndicatorNames()
    {
        String[] indicatorNames={"Psychosocial score (emotional health)","Psychosocial score (social behavior)","Food and nutrition score (food security)","Food and nutrition score (nutrition and growth)","Health score (wellness)","Health score (health care services)","Education and work score (performance)","Education and work score (school work)","Protection score (abuse and exploitation)","Protection score (legal protection)","Shelter and care score (shelter)","Shelter and care score (care)"};
        return indicatorNames;
    }
    public void getAllOvcs(HttpSession session)
    {
           List ovcList = new ArrayList();
           try
           {
                OvcDao dao = new OvcDaoImpl();
                list = dao.getOvcList();
           }
           catch(Exception e)
           {
                e.printStackTrace();
           }
           List encryptedOvcList=de.encryptOvcInfo(list,session);
            for (Object s: encryptedOvcList)
            {
                Ovc ovc = (Ovc)s;
                ovcList.add(ovc);
                session.setAttribute("ovcRecords", ovcList);
            }
            session.setAttribute("ovcRecords", ovcList);
    }
    public void updateCurrentAge()
    {
            List ovcList = new ArrayList();
           try
           {
                OvcDao dao = new OvcDaoImpl();
                Ovc ovc =null;
                ovcList = dao.getOvcList();
                if(ovcList !=null)
                {
                    for(Object obj: ovcList)
                    {
                        ovc = (Ovc)obj;
                        ovc=util.getOvcWithCurrentAge(ovc);
                        //int currentAge=util.getCurrentAge(ovc);
                        //ovc.setCurrentAge(currentAge);
                        dao.updateOvc(ovc,false,false);
                    }
                }
           }
           catch(Exception e)
           {
                e.printStackTrace();
           }
    }
    
    public void getOvcRecords(HttpSession session,List paramList)
    {
        List ovcList = new ArrayList();
        List ovcListWithCaregiverInfo = new ArrayList();
        List dateLabel=new ArrayList();
        String stateCode=(String)paramList.get(0);
        String lgaCode=(String)paramList.get(1);
        String cboCode=(String)paramList.get(2);
        String cboName=util.getOrganizationName(cboCode);
        String wardCode=(String)paramList.get(3);
        String wardName=util.getWardName(wardCode);
        String partnerCode=(String)paramList.get(10);
        String fmonth=paramList.get(4).toString();
        String fyear=paramList.get(5).toString();
        String smonth=paramList.get(6).toString();
        String syear=paramList.get(7).toString();
        String startAge=paramList.get(8).toString();
        String endAge=paramList.get(9).toString();
        String stateName=util.getStateName(stateCode);
        String lgaName=util.getLgaName(lgaCode);
        String[] params={stateCode,lgaCode,cboCode,wardCode,fmonth,fyear,smonth,syear,startAge,endAge,partnerCode,lgaName,cboName,wardName,stateName};
        String[] dateParams={fmonth,fyear,smonth,syear};
        
         dateLabel=getDateLabelsForTitle(dateParams);
         String ageTitle=util.getAgeForTitle(startAge,endAge);
            try
           {
                OvcDao dao = new OvcDaoImpl();
                list = dao.getOvcRecords(params);
                //ovcListWithCaregiverInfo=util.getOvcWithCaregiverInfo(list);
           }
           catch(Exception e)
           {
                e.printStackTrace();
           }
        List encryptedOvcList=de.encryptOvcInfo(list,session);
        for (Object s: encryptedOvcList)
        {
            Ovc ovc = (Ovc)s;
            //ovc.setDateEnrollment(appUtil.getDisplayDate(ovc.getDateEnrollment()));
            //ovc.setDateOfEntry(appUtil.getDisplayDate(ovc.getDateOfEntry()));
            //ovc.setDateOfWithdrawal(appUtil.getDisplayDate(ovc.getDateOfWithdrawal()));
            //appUtil.convertMysqlDateTomonthDayYear(ovc.getDateEnrollment())
            ovc.setDateEnrollment(ovc.getDateEnrollment());
            ovcList.add(ovc);
        }
         String subTitle=" between 1st of "+dateLabel.get(0)+" and end of "+dateLabel.get(1);
         session.setAttribute("ovcrecordstitle", subTitle);
         session.setAttribute("ovcRecords", ovcList);
         session.setAttribute("enrollagetitle", ageTitle);
         session.setAttribute("orgparams", params);
    }
    public void getNutritionalAssessmentRecords(HttpSession session,List paramList)
    {
        List ovcList = new ArrayList();
        List naList=new ArrayList();
        List mainList=new ArrayList();
        List ovcListWithCaregiverInfo = new ArrayList();
        List dateLabel=new ArrayList();
        String stateCode=(String)paramList.get(0);
        String lgaCode=(String)paramList.get(1);
        String cboCode=(String)paramList.get(2);
        String cboName=util.getOrganizationName(cboCode);
        String wardCode=(String)paramList.get(3);
        String wardName=util.getWardName(wardCode);
        String partnerCode=(String)paramList.get(10);
        String fmonth=paramList.get(4).toString();
        String fyear=paramList.get(5).toString();
        String smonth=paramList.get(6).toString();
        String syear=paramList.get(7).toString();
        String startAge=paramList.get(8).toString();
        String endAge=paramList.get(9).toString();
        int assessmentNo=Integer.parseInt(paramList.get(11).toString());
        String stateName=util.getStateName(stateCode);
        String lgaName=util.getLgaName(lgaCode);
        String[] params={stateCode,lgaCode,cboCode,wardCode,fmonth,fyear,smonth,syear,startAge,endAge,partnerCode,lgaName,cboName,wardName,stateName};
        String[] dateParams={fmonth,fyear,smonth,syear};

         dateLabel=getDateLabelsForTitle(dateParams);
         String ageTitle=util.getAgeForTitle(startAge,endAge);
         Ovc ovc=null;
                    NutritionAssessment na=null;
                    NutritionAssessment mainNa=null;
            try
           {
               String assessmentNoQuery=" and na.assessmentNo="+assessmentNo;
                NutritionAssessmentDao dao = new NutritionAssessmentDaoImpl();
                String[] dateParts={params[4],params[5],params[6],params[7]};
                String dateQueryPart=util.getQueryPeriod(dateParts, " and na.dateOfAssessment ");
                String additionalQueryCriteria=util.getQueryCriteria(params)+dateQueryPart;
                list = dao.getNutritionAssessmentsWithOvcDetails(additionalQueryCriteria+assessmentNoQuery);
                if(list !=null && !list.isEmpty())
                { 
                    for(Object s:list)
                    {
                        Object[] obj=(Object[])s;
                        ovc=(Ovc)obj[1];
                        na=(NutritionAssessment)obj[2];
                        ovcList.add(ovc);
                        naList.add(na);
                     
                    }
                }
                //ovcListWithCaregiverInfo=util.getOvcWithCaregiverInfo(ovcList);
           }
           catch(Exception e)
           {
                e.printStackTrace();
           }
        List encryptedOvcList=de.encryptOvcInfo(ovcList,session);
        Ovc ovc2=null;
            for(int i=0; i< encryptedOvcList.size(); i++)
            {
                ovc2 = (Ovc)encryptedOvcList.get(i);
                mainNa=(NutritionAssessment)naList.get(i);
                mainNa.setOvcFname(ovc2.getFirstName());
                mainNa.setOvcSurname(ovc2.getLastName());
                mainNa.setOvcAge(ovc2.getCurrentAge());
                mainNa.setAgeUnit(ovc2.getAgeUnit());
                mainNa.setOvcGender(ovc2.getGender());

                mainList.add(mainNa);
            }
         String subTitle=" between 1st of "+dateLabel.get(0)+" and end of "+dateLabel.get(1);
         session.setAttribute("narecordstitle", subTitle);
         session.setAttribute("naRecords", mainList);
         session.setAttribute("naenrollagetitle",ageTitle);
         session.setAttribute("naorgparams", params);
    }
}

