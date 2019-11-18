/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report;

import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.HouseholdService;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdServiceDao;
import com.fhi.kidmap.dao.HouseholdServiceDaoImpl;
import com.fhi.kidmap.dao.HouseholdUniqueServiceReportDao;
import com.fhi.kidmap.dao.HouseholdUniqueServiceReportDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcServiceDao;
import com.fhi.kidmap.dao.OvcServiceDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.kidmap.dao.ServiceReportDao;
import com.fhi.kidmap.dao.ServiceReportDaoImpl;
import com.fhi.kidmap.dao.SpecificServiceReportDao;
import com.fhi.kidmap.dao.SpecificServiceReportDaoImpl;
import com.fhi.nomis.nomisutils.NomisConstant;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author smomoh
 */
public class ServiceRecords
{
    DaoUtil util=new DaoUtil();

    public ServiceRecords()
    {

    }
    public String generateServiceReportData(String orgCode,String startDate,String endDate)
    {
        String msg="Datamart populated";
        OvcServiceDao sdao=new OvcServiceDaoImpl();
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();

        OvcService os=null;
        try
        {
            int savedRecordsCount=0;
            int totalRecords=0;
            List list=null;
            List orgCodeList=hhedao.getDistinctOrgCodes();
            for(int j=0; j<orgCodeList.size(); j++)
            {
                orgCode=(String)orgCodeList.get(j);
                System.err.println("orgCode is "+orgCode);
                if(orgCode==null || orgCode.equalsIgnoreCase("All"))
                list=sdao.getServiceByPeriod(startDate, endDate);
                else
                list=sdao.getServiceByCBOAndPeriod(orgCode, startDate, endDate);
                if(list==null || list.isEmpty())
                System.err.println("list is empty "+list.size());
                if(list !=null && !list.isEmpty())
                {
                    System.err.println("list size in ServiceRecords.generateServiceReportData is "+list.size());
                    ServiceReportDao srdao=new ServiceReportDaoImpl();
                    ServiceReport sr=null;
                    ServiceReport sr2=null;
                    List serviceList=null;
                    int size=list.size();
                    totalRecords+=size;
                    String ovcId=null;
                    for(int i=0; i<list.size(); i++)
                    {
                        os=(OvcService)list.get(i);
                        sr=srdao.getServiceReportByIdAndPeriod(os.getOvcId(),startDate, endDate);
                        if(sr==null)
                        {
                            sr2=new ServiceReport();
                            sr2.setDateOfEntry(os.getDateOfEntry());
                            sr2.setHeight(os.getHeight());
                            sr2.setId(os.getId());
                            sr2.setOvcId(os.getOvcId());
                            sr2.setProviderName(os.getProviderName());
                            sr2.setServiceAccessed1(os.getServiceAccessed1());
                            sr2.setServiceAccessed2(os.getServiceAccessed2());
                            sr2.setServiceAccessed3(os.getServiceAccessed3());
                            sr2.setServiceAccessed4(os.getServiceAccessed4());
                            sr2.setServiceAccessed5(os.getServiceAccessed5());
                            sr2.setServiceAccessed6(os.getServiceAccessed6());
                            sr2.setServiceAccessed7(os.getServiceAccessed7());
                            sr2.setServicedate(os.getServicedate());
                            sr2.setServicesReferred(os.getServicesReferred());
                            sr2.setSurveyNumber(os.getSurveyNumber());
                            sr2.setNumberOfServices(sdao.getNumberOfServicesPerServiceRecord(os));
                            savedRecordsCount++;
                            System.err.println("Service record "+savedRecordsCount+" of "+totalRecords+" saved for "+orgCode);
                           srdao.saveServiceReport(sr2);
                        }
                    }
                    msg=savedRecordsCount+" unique service records saved out of "+totalRecords;
                }
                else
                msg=savedRecordsCount+" unique service records saved out of "+totalRecords;
            }
        }
        catch(Exception ex)
        {
            msg="Error: "+ex.getMessage();
            //ex.printStackTrace();
        }
        return msg;
    }
    public String generateCaregiverServiceReportData(String orgCode,String startDate,String endDate)
    {
        String msg="Datamart populated";
        HouseholdServiceDao hhsdao=new HouseholdServiceDaoImpl();
        HouseholdUniqueServiceReportDao uhhsdao=new HouseholdUniqueServiceReportDaoImpl();
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();

        HouseholdService hhs=null;
        try
        {
            int savedRecordsCount=0;
            int totalRecords=0;
            List list=null;
            List orgCodeList=hhedao.getDistinctOrgCodes();
            for(int j=0; j<orgCodeList.size(); j++)
            {
                orgCode=(String)orgCodeList.get(j);
                System.err.println("orgCode is "+orgCode);
                if(orgCode==null || orgCode.equalsIgnoreCase("All"))
                list=hhsdao.getServiceByPeriod(startDate, endDate);
                else
                list=hhsdao.getServiceByCBOAndPeriod(orgCode, startDate, endDate);
                if(list==null || list.isEmpty())
                System.err.println("list is empty "+list.size());
                if(list !=null && !list.isEmpty())
                {
                    System.err.println("list size in ServiceRecords.generateCaregiverServiceReportData is "+list.size());
                    
                    HouseholdUniqueServiceReport sr=null;
                    HouseholdUniqueServiceReport sr2=null;
                    
                    int size=list.size();
                    totalRecords+=size;
                    
                    for(int i=0; i<list.size(); i++)
                    {
                        hhs=(HouseholdService)list.get(i);
                        sr=uhhsdao.getServiceReportByIdAndPeriod(hhs.getCaregiverId(),startDate, endDate);
                        if(sr==null)
                        {
                            sr2=new HouseholdUniqueServiceReport();
                            sr2.setDateOfEntry(hhs.getDateOfEntry());
                            
                            sr2.setId(hhs.getId());
                            sr2.setHhUniqueId(hhs.getHhUniqueId());
                            sr2.setCaregiverId(hhs.getCaregiverId());
                            sr2.setDateOfEntry(hhs.getDateOfEntry());
                            sr2.setEconomicStrengtheningServices(hhs.getEconomicStrengtheningServices());
                            sr2.setEducationalServices(hhs.getEducationalServices());
                            sr2.setHealthServices(hhs.getHealthServices());
                            //sr2.setNumberOfServices(hhs.getNumberOfServices());
                            sr2.setNutritionalServices(hhs.getNutritionalServices());
                            sr2.setProtectionServices(hhs.getProtectionServices());
                            sr2.setPsychosocialSupportServices(hhs.getPsychosocialSupportServices());
                            sr2.setRecordedBy(hhs.getRecordedBy());
                            sr2.setServiceDate(hhs.getServiceDate());
                            sr2.setNumberOfServices(hhsdao.getNumberOfServicesPerServiceRecord(hhs));
                            sr2.setServiceNo(hhs.getServiceNo());
                            sr2.setServiceRecipientType(hhs.getServiceRecipientType());
                            sr2.setShelterAndCareServices(hhs.getShelterAndCareServices());
                            sr2.setVolunteerDesignation(hhs.getVolunteerDesignation());
                            sr2.setVolunteerName(hhs.getVolunteerName());
                            savedRecordsCount++;
                            System.err.println("Household Service record "+savedRecordsCount+" of "+totalRecords+" saved for "+orgCode);
                            uhhsdao.saveServiceReport(sr2);
                        }
                    }
                    msg=savedRecordsCount+" unique Household service records saved out of "+totalRecords;
                }
                else
                msg=savedRecordsCount+" unique Household service records saved out of "+totalRecords;
            }
        }
        catch(Exception ex)
        {
            msg="Error: "+ex.getMessage();
            //ex.printStackTrace();
        }
        return msg;
    }
    public String generateHivServicesSupportData(String orgUnitCode,String startDate,String endDate)
    {
        String msg="Datamart populated";
        OvcServiceDao sdao=new OvcServiceDaoImpl();
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();

        OvcService os=null;
        try
        {
            int savedRecordsCount=0;
            int totalRecords=0;
            List list=null;
            List orgUnitList=hhedao.getDistinctCommunityCodes();
            SpecificServiceReportDao srdao=new SpecificServiceReportDaoImpl();
            for(int j=0; j<orgUnitList.size(); j++)
            {
                orgUnitCode=(String)orgUnitList.get(j);
                System.err.println("communityCode is "+orgUnitCode);
                list=sdao.getHivServicesByOrgUnitCodeAndPeriod(orgUnitCode, startDate, endDate);
                

                if(list !=null && !list.isEmpty())
                {
                    System.err.println("list size in ServiceRecords.generateHivServicesSupportData is "+list.size());
                    
                    SpecificServiceReport sr=null;
                    SpecificServiceReport sr2=null;
                    //List serviceList=null;
                    int size=list.size();
                    totalRecords+=size;
                    String ovcId=null;
                    for(int i=0; i<list.size(); i++)
                    {
                        os=(OvcService)list.get(i);
                        sr=srdao.getHivServiceReport(os.getOvcId());
                        if(sr==null)
                        {
                            sr2=new SpecificServiceReport();
                            sr2.setDateOfEntry(os.getDateOfEntry());
                            sr2.setHeight(os.getHeight());
                            sr2.setId(os.getId());
                            sr2.setOvcId(os.getOvcId());
                            sr2.setProviderName(os.getProviderName());
                            sr2.setServiceAccessed1(os.getServiceAccessed1());
                            sr2.setServiceAccessed2(os.getServiceAccessed2());
                            sr2.setServiceAccessed3(os.getServiceAccessed3());
                            sr2.setServiceAccessed4(os.getServiceAccessed4());
                            sr2.setServiceAccessed5(os.getServiceAccessed5());
                            sr2.setServiceAccessed6(os.getServiceAccessed6());
                            sr2.setServiceAccessed7(os.getServiceAccessed7());
                            sr2.setServicedate(os.getServicedate());
                            sr2.setServicesReferred(os.getServicesReferred());
                            sr2.setSurveyNumber(os.getSurveyNumber());
                            sr2.setNumberOfServices(sdao.getNumberOfServicesPerServiceRecord(os));
                            savedRecordsCount++;
                            System.err.println("HIV Service record "+savedRecordsCount+" of "+totalRecords+" saved for "+orgUnitCode);
                           srdao.saveSpecificServiceReport(sr2);
                        }
                    }
                    //msg=savedRecordsCount+" unique hiv service records saved out of "+totalRecords;
                }
                //else
                    //msg="No records for the period selected";
            }
            msg=savedRecordsCount+" unique hiv service records saved out of "+totalRecords;
        }
        catch(Exception ex)
        {
            msg="Error: "+ex.getMessage();
            //ex.printStackTrace();
        }
        return msg;
    }
    public void removeFromWithdrawalList(HttpSession session,String ovcId)
    {
        OvcWithdrawalDao dao=new OvcWithdrawalDaoImpl();
        List list=new ArrayList();
        List paramList=new ArrayList();
        paramList=(List)session.getAttribute("withdrawalParamList");
        if(ovcId !=null)
        {
            try
            {
                list=dao.getWithdrawnOvc(ovcId);
                if(!list.isEmpty())
                {
                    OvcWithdrawal ovcWithdrawal=(OvcWithdrawal)list.get(0);
                    dao.deleteOvcWithdrawal(ovcWithdrawal);
                    generateWithdrawalList(session, paramList);
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        
    }
    public void generateWithdrawalList(HttpSession session,List paramList)
    {
        List withdrawalList=new ArrayList();

        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
        HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
        OvcDao ovcDao=new OvcDaoImpl();
        CaregiverDao cgiverdao=new CaregiverDaoImpl();
        String type=(String)paramList.get(10);
        List beneficiaryList=new ArrayList();
        try
        {
            withdrawalList=wdao.getWithdrawalList(paramList);
            for(Object s: withdrawalList)
            {
                OvcWithdrawal withdrawal=(OvcWithdrawal)s;
                if(withdrawal.getType().equalsIgnoreCase(NomisConstant.OVC_TYPE))
                {
                    Ovc ovc=(Ovc)ovcDao.getOvc(withdrawal.getOvcId());
                    beneficiaryList.add(getOvcWithdrawalReportBean(ovc,withdrawal));
                }
                if(withdrawal.getType().equalsIgnoreCase(NomisConstant.Caregiver_TYPE))
                {
                    if(withdrawal.getOvcId() !=null)
                    {
                        if(withdrawal.getOvcId().trim().length()==NomisConstant.HHUNIQUEID_LENGTH)
                        {
                            withdrawal.setType(NomisConstant.HOUSEHOLD_TYPE);
                            wdao.updateOvcWithdrawal(withdrawal);
                            HouseholdEnrollment hhe=(HouseholdEnrollment)hhedao.getHouseholdEnrollment(withdrawal.getOvcId());
                            beneficiaryList.add(getOvcWithdrawalReportBean(hhe,withdrawal));
                        }
                        else
                        {
                            Caregiver cgiver=(Caregiver)cgiverdao.getCaregiverByCaregiverId(withdrawal.getOvcId());
                            beneficiaryList.add(getOvcWithdrawalReportBean(cgiver,withdrawal));
                            
                            /*else
                            {//This is likely a household head also serving as caregiver
                                withdrawal.setType(NomisConstant.HOUSEHOLD_TYPE);
                                wdao.updateOvcWithdrawal(withdrawal);
                                HouseholdEnrollment hhe=(HouseholdEnrollment)hhedao.getHouseholdEnrollment(withdrawal.getOvcId());
                                beneficiaryList.add(getOvcWithdrawalReportBean(hhe,withdrawal));
                            }*/
                        }
                    }
                }
                if(withdrawal.getType().equalsIgnoreCase(NomisConstant.HOUSEHOLD_TYPE))
                {
                    HouseholdEnrollment hhe=(HouseholdEnrollment)hhedao.getHouseholdEnrollment(withdrawal.getOvcId());
                    beneficiaryList.add(getOvcWithdrawalReportBean(hhe,withdrawal));
                }
            }
            /*if(type !=null && type.equalsIgnoreCase("ovc"))
            {
                for(Object s: withdrawalList)
                {
                    OvcWithdrawal withdrawal=(OvcWithdrawal)s;
                    Ovc ovc=(Ovc)ovcDao.getOvc(withdrawal.getOvcId());
                    beneficiaryList.add(getOvcWithdrawalReportBean(ovc,withdrawal));
                }
            }
            if(type !=null && type.equalsIgnoreCase("caregiver"))
            {
                System.err.println("withdrawalList size is "+withdrawalList.size());
                for(Object s: withdrawalList)
                {
                    OvcWithdrawal withdrawal=(OvcWithdrawal)s;
                    Caregiver cgiver=(Caregiver)cgiverdao.getCaregiverByCaregiverId(withdrawal.getOvcId());
                    beneficiaryList.add(getOvcWithdrawalReportBean(cgiver,withdrawal));
                }
            }
            else if(type !=null && type.equalsIgnoreCase("household"))
            {
                for(Object s: withdrawalList)
                {
                    OvcWithdrawal withdrawal=(OvcWithdrawal)s;
                    HouseholdEnrollment hhe=(HouseholdEnrollment)hhedao.getHouseholdEnrollment(withdrawal.getOvcId());
                    beneficiaryList.add(getOvcWithdrawalReportBean(hhe,withdrawal));
                }
            }*/
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //System.err.println("paramList.get(2).toString() is "+paramList.get(2).toString());
        String cboName=util.getOrganizationName(paramList.get(2).toString());
        paramList.set(2,cboName);
        String serviceMth=paramList.get(4).toString();
        String serviceYear=paramList.get(5).toString();
        String serviceMth2=paramList.get(6).toString();
        String serviceYear2=paramList.get(7).toString();
        String[] dateParams={serviceMth,serviceYear,serviceMth2,serviceYear2};
        String[] dateArray={util.getStartDate(dateParams),util.getEndDate(dateParams)};
        List dateLabels=util.getDateLabels(dateArray);
        session.setAttribute("withdrawalDate", dateLabels);
        session.setAttribute("withdrawalOrgParam", paramList);
        session.setAttribute("withdrwalList", beneficiaryList);
    }
    public List getListOfOvcServed(String additionalQuery)
    {
        OvcServiceDao dao=new OvcServiceDaoImpl();
        OvcDao ovcDao=new OvcDaoImpl();
        List ovcServiceList=new ArrayList();
        List ovcList=new ArrayList();
        try
        {                  
            ovcServiceList=dao.getListOfOvcServed(additionalQuery);
            for(Object s: ovcServiceList)
            {
                OvcService service=(OvcService)s;
                Ovc ovc=(Ovc)ovcDao.getOvc(service.getOvcId());
                ovcList.add(getOvcServiceReportBean(ovc,service)); 
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return ovcList;
    }
    
    public void setMthlyServiceList(HttpSession session, List paramList)
    {
        OvcServiceDao dao=new OvcServiceDaoImpl();
        OvcDao ovcDao=new OvcDaoImpl();
        List ovcServiceList=new ArrayList();
        List ovcList=new ArrayList();
        try
        {                  
            ovcServiceList=dao.getOvcServicePerMth(paramList);
            for(Object s: ovcServiceList)
            {
                OvcService service=(OvcService)s;
                Ovc ovc=(Ovc)ovcDao.getOvc(service.getOvcId());
                ovcList.add(getOvcServiceReportBean(ovc,service)); 
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        String cboName=util.getOrganizationName(paramList.get(2).toString());
        paramList.set(2,cboName);
        String serviceMth=paramList.get(4).toString();
        String serviceYear=paramList.get(5).toString();
        String serviceMth2=paramList.get(6).toString();
        String serviceYear2=paramList.get(7).toString();
        String[] dateParams={serviceMth,serviceYear,serviceMth2,serviceYear2};
        String[] dateArray={util.getStartDate(dateParams),util.getEndDate(dateParams)};
        List dateLabels=util.getDateLabels(dateArray);
        String serviceAgePeriod=paramList.get(8)+" and "+paramList.get(9);
        session.removeAttribute("serviceList");
        session.setAttribute("serviceAgePeriod", serviceAgePeriod);
        session.setAttribute("serviceDate", dateLabels);
        session.setAttribute("serviceOrgParam", paramList);
        session.setAttribute("serviceList", ovcList);
    }
    private OvcServiceReportBean getOvcServiceReportBean(Ovc ovc,OvcService service)
    {
        AppUtility appUtil=new AppUtility();
        OvcServiceReportBean serviceBean=new OvcServiceReportBean();
        serviceBean.setOvcId(ovc.getOvcId());
        serviceBean.setSurname(ovc.getLastName());
        serviceBean.setOtherNames1(ovc.getFirstName());
        serviceBean.setAddress(ovc.getAddress());
        serviceBean.setPhone(ovc.getPhone());
        serviceBean.setAge(ovc.getAge());
        serviceBean.setAgeUnit(ovc.getAgeUnit());
        serviceBean.setGender(ovc.getGender());
        serviceBean.setState(ovc.getHhe().getStateName());
        serviceBean.setLga(ovc.getHhe().getLgaName());
        serviceBean.setCompletedbyCbo(ovc.getHhe().getOrgName());
        serviceBean.setWard(ovc.getHhe().getCommunityName());
        serviceBean.setWithdrawnFromProgram(ovc.getWithdrawnFromProgram());
        serviceBean.setDateOfWithdrawal(appUtil.monthDayYear(ovc.getDateOfWithdrawal()));
        serviceBean.setReasonForWithdrawal(ovc.getReasonForWithdrawal());
        serviceBean.setOvc(ovc);
        serviceBean.setServiceDate(appUtil.monthDayYear(service.getServicedate()));
        
        serviceBean.setServiceAccessed1(service.getProtectionServicesByName());
        serviceBean.setServiceAccessed2(service.getNutritionalServicesByName());
        serviceBean.setServiceAccessed3(service.getHealthServicesByName());
        serviceBean.setServiceAccessed4(service.getEducationalServicesByName());
        serviceBean.setServiceAccessed5(service.getProtectionServicesByName());
        serviceBean.setServiceAccessed6(service.getShelterAndCareServicesByName());
        serviceBean.setServiceAccessed7(service.getEconomicStrenghteningServicesByName());
        /*serviceBean.setServiceAccessed1(service.getServiceAccessed1());
        serviceBean.setServiceAccessed2(service.getServiceAccessed2());
        serviceBean.setServiceAccessed3(service.getServiceAccessed3());
        serviceBean.setServiceAccessed4(service.getServiceAccessed4());
        serviceBean.setServiceAccessed5(service.getServiceAccessed5());
        serviceBean.setServiceAccessed6(service.getServiceAccessed6());
        serviceBean.setServiceAccessed7(service.getServiceAccessed7());*/
        serviceBean.setDateOfEntry(appUtil.monthDayYear(service.getDateOfEntry()));
        serviceBean.setProviderName(service.getProviderName());
        serviceBean.setChildAbusedAnswer(service.getChildAbusedAnswer());
        serviceBean.setChildLinkedToGovtAnswer(service.getChildLinkedToGovtAnswer());
        serviceBean.setChildMissedSchool(service.getChildMissedSchool());
        return serviceBean;
    }
    private OvcWithdrawalReportBean getOvcWithdrawalReportBean(Ovc ovc,OvcWithdrawal withdrawal)
    {
        OvcWithdrawalReportBean withdrawalBean=new OvcWithdrawalReportBean();
        withdrawalBean.setAge(util.getCurrentAge(ovc));
        withdrawalBean.setAgeUnit(ovc.getAgeUnit());
        withdrawalBean.setFirstName(ovc.getFirstName());
        withdrawalBean.setLastName(ovc.getLastName());
        withdrawalBean.setGender(ovc.getGender());
        withdrawalBean.setDateOfWithdrawal(util.getServiceDate(withdrawal.getDateOfWithdrawal()));
        withdrawalBean.setReasonWithdrawal(withdrawal.getReasonWithdrawal());
        withdrawalBean.setOvcId(withdrawal.getOvcId());
        return withdrawalBean;
    }
    private OvcWithdrawalReportBean getOvcWithdrawalReportBean(HouseholdEnrollment hhe,OvcWithdrawal withdrawal)
    {
        OvcWithdrawalReportBean withdrawalBean=new OvcWithdrawalReportBean();
        withdrawalBean.setAge(hhe.getHhAge());
        withdrawalBean.setAgeUnit("years");
        withdrawalBean.setFirstName(hhe.getHhFirstname());
        withdrawalBean.setLastName(hhe.getHhSurname());
        withdrawalBean.setGender(hhe.getHhGender());
        withdrawalBean.setDateOfWithdrawal(util.getServiceDate(withdrawal.getDateOfWithdrawal()));
        withdrawalBean.setReasonWithdrawal(withdrawal.getReasonWithdrawal());
        withdrawalBean.setOvcId(withdrawal.getOvcId());
        return withdrawalBean;
    }
    private OvcWithdrawalReportBean getOvcWithdrawalReportBean(Caregiver cgiver,OvcWithdrawal withdrawal)
    {
        OvcWithdrawalReportBean withdrawalBean=new OvcWithdrawalReportBean();
        withdrawalBean.setAge(cgiver.getCaregiverAge());
        withdrawalBean.setAgeUnit("years");
        withdrawalBean.setFirstName(cgiver.getCaregiverFirstname());
        withdrawalBean.setLastName(cgiver.getCaregiverLastName());
        withdrawalBean.setGender(cgiver.getCaregiverGender());
        withdrawalBean.setDateOfWithdrawal(util.getServiceDate(withdrawal.getDateOfWithdrawal()));
        withdrawalBean.setReasonWithdrawal(withdrawal.getReasonWithdrawal());
        withdrawalBean.setOvcId(withdrawal.getOvcId());
        return withdrawalBean;
    }
    public void recordGraduatedOvcAsServed()
    {
        try
        {
            int count=0;
            AppUtility appUtil=new AppUtility();
            OvcDao dao=new OvcDaoImpl();
            OvcServiceDao sdao=new OvcServiceDaoImpl();
            OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
            List graduatedOvcList=wdao.getListOfBeneficiariesByTypeAndReasonWithdrawn(NomisConstant.GRADUATED, NomisConstant.OVC_TYPE);
            for(Object s: graduatedOvcList)
            {
                OvcWithdrawal withdrawal=(OvcWithdrawal)s;
                OvcService service=sdao.getOvcServiceForTheMth(withdrawal.getOvcId(), withdrawal.getDateOfWithdrawal());
                if(service==null)
                {
                    Ovc ovc=dao.getOvc(withdrawal.getOvcId());
                    if(ovc !=null)
                    {
                        service=new OvcService();
                        service.setOvcId(withdrawal.getOvcId());
                        service.setServicedate(withdrawal.getDateOfWithdrawal());
                        service.setDateOfEntry(appUtil.getCurrentDate());
                        service.setserviceAccessed7(withdrawal.getReasonWithdrawal());
                        //service.setPointOfService(service.getPointOfService());
                        HivStatusUpdate hsu=ovc.getActiveHivStatus();
                        if(hsu==null)
                        {
                            service.setCurrentHivStatus(hsu.getHivStatus());
                            service.setEnrolledInCare(hsu.getClientEnrolledInCare());
                            service.setEnrolledOnART(hsu.getEnrolledOnART());
                            service.setFacilityId(hsu.getOrganizationClientIsReferred());
                        }
                        sdao.saveOvcService(service, false, false);
                        count++;
                        System.err.println("Graduated OVC at "+count+" served in ServiceRecords");
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

