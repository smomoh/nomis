/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import ImportExport.DataExportSummary;
import ImportExport.DataTransferSummaryGenerator;
import com.fhi.nomis.nomisutils.AjaxProcessor;
import com.fhi.nomis.nomisutils.AppUtility;
import ImportExport.DatabaseExport;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.Wards;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.DateManager;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class DatabaseExportAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String SUMMARY = "summary";

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        DatabaseExportForm dbef=(DatabaseExportForm)form;
        String requiredAction=dbef.getActionName();
        AjaxProcessor aproc=new AjaxProcessor();
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        String actionName=request.getParameter("actionName");
        if(requiredAction ==null && actionName !=null)
        {
            requiredAction=actionName;
        }
        //System.err.println("actionName is "+actionName);
        String stateCode=dbef.getState();
        String lgaCode=dbef.getLga();
        String cboCode=dbef.getCbo();
        String communityCode=dbef.getWard();
        String partnerCode=dbef.getPartnerCode();
        String beneficiaryStatus=dbef.getBeneficiaryStatus();
        boolean isEntirePeriod=dbef.isEntirePeriod();
        String month1=dbef.getMonth1();
        String month2=dbef.getMonth2();
        String year1=dbef.getYear1();
        String year2=dbef.getYear2();

        DaoUtil util=new DaoUtil();
        appUtil.deleteFiles(appUtil.getExportFilePath());
        appUtil.createExportImportDirectories();
        System.err.println("requiredAction is "+requiredAction);
        if(requiredAction==null)
        {
           return mapping.findForward("mainPage");
        }
        else if(requiredAction.equalsIgnoreCase("criteriaPage"))
        {
            AccessManager acm=new AccessManager();
            boolean userInRole=acm.isUserInRole("005",appUtil.getCurrentUser(session));
            if(!userInRole)
            {
                request.setAttribute("errorMsg", appUtil.getAccessDeniedMessage());
                return mapping.findForward("accessDenied");
            }
            LoadUpInfo loadup=new LoadUpInfo();
            loadup.getAllStatesForExport(session);
            PartnersDao pdao=new PartnersDaoImpl();
            List partnerList=pdao.getAllPartners();
            session.setAttribute("dbExportLgaList", new ArrayList());
            session.setAttribute("dbExportOrgList",new ArrayList());
            session.setAttribute("dbExportWardList",new ArrayList());
            session.setAttribute("partnerList", partnerList);
            
            return mapping.findForward("criteriaPage");
        }
        else if(requiredAction.equalsIgnoreCase("lga"))
        {
            List lgaList =util.getOrganizationalUnits(requiredAction,stateCode,lgaCode,cboCode,null);
            session.setAttribute("dbExportLgaList", lgaList);
            session.setAttribute("dbExportOrgList",new ArrayList());
            session.setAttribute("dbExportWardList",new ArrayList());
            return mapping.findForward("criteriaPage");
        }
        else if(requiredAction.equalsIgnoreCase("cbo"))
        {
            OrganizationRecords orgRecords=null;
            List orgList=new ArrayList();
            List cboList =util.getOrganizationalUnits(requiredAction,stateCode,lgaCode,cboCode,null);
            for(Object org:cboList)
            {
                orgRecords=util.getOrganizationRecords(org.toString());
                orgList.add(orgRecords);
            }
            session.setAttribute("dbExportOrgList", orgList);
            session.setAttribute("dbExportWardList",new ArrayList());
            return mapping.findForward("criteriaPage");
        }
        else if(requiredAction.equalsIgnoreCase("ward"))
        {
            Wards ward=null;
            List wardList=new ArrayList();
            List list =util.getOrganizationalUnits(requiredAction,stateCode,lgaCode,cboCode,null);
            for(Object wardCode:list)
            {
                ward=util.getWard(wardCode.toString());
                wardList.add(ward);
            }
            session.setAttribute("dbExportWardList",wardList);
            return mapping.findForward("criteriaPage");
        }
        else if(requiredAction.equalsIgnoreCase("dbexport"))
        {
            String startDate=null;
            String endDate=null;
            String hsuDateQuery=" ";
            String tbhivDateQuery=" ";
            String gclDateQuery=" ";
            boolean entirePeriod=false;
            if(isEntirePeriod)
            {
                month1="All";
                year1="All";
                month2="All";
                year2="All";
                startDate="All";
                endDate="All";
                entirePeriod=true;
            }
            else
            {
              gclDateQuery=util.getGraduationCheckListPeriodQuery(Integer.parseInt(month1), Integer.parseInt(year1), Integer.parseInt(month2), Integer.parseInt(year2));  
            }
            appUtil.deleteFiles(appUtil.getExportFilePath());
            String partnerName=util.getPartnerName(partnerCode);
            String[] params={month1,year1,month2,year2,stateCode,lgaCode,cboCode,partnerName,partnerCode,communityCode};
            String[] orgParams={params[4],params[5],params[6],params[9],null,null,null,null,null,null,null,null,null,params[7],params[8]};
            
            String stateName=util.getStateName(stateCode);
            String lgaName=util.getLgaName(lgaCode);
            String cboName=util.getOrganizationName(cboCode);
            String wardName=util.getWardName(communityCode);
            
            String[] ssrgParams={stateCode,lgaCode,cboCode,month1,year1,stateName,lgaName,month1,year1,month2,year2,"All","All","All","All","0","24+",cboName,partnerCode,communityCode,wardName};
            
            //param={params[4],params[5],params[6],month,year,stateName,lgaName,enrollmentStartMth,enrollmentStartYear,enrollmentEndMth,enrollmentEndYear,"All","All","All","All",startAge,endAge,cboName,partnerCode,wardCode,wardName};
            
            String additionalOrgQuery=util.getOrgQueryCriteria(orgParams);
            
            if(startDate ==null)
            {
                startDate=util.getStartDate(params);
                endDate=util.getEndDate(params);
                hsuDateQuery=" and hsu.dateModified between '"+startDate+"' and '"+endDate+"'";
                tbhivDateQuery=" and tbhivChecklist.dateModified between '"+startDate+"' and '"+endDate+"'";
            }
            String cpaExportDateQuery=util.getCareplanAchievementExportDateQuery(startDate,endDate);
            String ovcWithdrawalQuery="";
            String cgiverWithdrawalQuery="";
            String hhWithdrawalQuery="";
            if(beneficiaryStatus!=null)
            {
                if(beneficiaryStatus.equalsIgnoreCase("active"))
                {
                    ovcWithdrawalQuery=util.getOvcWithdrawnFromProgramQuery("No");
                    cgiverWithdrawalQuery=util.getCaregiverWithdrawnFromProgramQuery("No");
                    hhWithdrawalQuery=util.getHouseholdWithdrawnFromProgramQuery("No");
                }
                else if(beneficiaryStatus.equalsIgnoreCase("exited"))
                {
                    ovcWithdrawalQuery=util.getOvcWithdrawnFromProgramQuery("Yes");
                    cgiverWithdrawalQuery=util.getCaregiverWithdrawnFromProgramQuery("Yes");
                    hhWithdrawalQuery=util.getHouseholdWithdrawnFromProgramQuery("Yes");
                }
            }
            DatabaseExport dbe=new DatabaseExport();
            if(beneficiaryStatus !=null && !beneficiaryStatus.equalsIgnoreCase("active"))
            {
                dbe.exportOvcWithdrwalRecordsInXml(additionalOrgQuery);
            }
            DateManager dm=new DateManager();
            String startMthName=params[0];
            String endMthName=params[2];
            if((params[0] !=null && !params[0].equalsIgnoreCase("All")) && (params[2] !=null && !params[2].equalsIgnoreCase("All")) )
            {
                startMthName=dm.getMonthAsString(Integer.parseInt(params[0]));
                endMthName=dm.getMonthAsString(Integer.parseInt(params[2]));
            }
            
            DataExportSummary des=DataTransferSummaryGenerator.getDataExportSummaryWithGeneralInformation(ssrgParams);//new DataExportSummary();
            if(entirePeriod)
            des.setExportPeriod("All");
            else
            des.setExportPeriod(startMthName+"-"+params[1]+"  to  "+endMthName+"-"+params[3]);
            des.setLevel2Ou(params[4]);
            des.setLevel3Ou(params[5]);
            des.setCbo(params[6]);
            des.setLevel2OuName(stateName);
            des.setLevel3OuName(lgaName);
            des.setLevel4OuName(wardName);
            des.setCboName(cboName);
            dbe.exportHivStatusRecordsInXml(additionalOrgQuery+hsuDateQuery);
            dbe.exportDeletedRecordsInXml(startDate, endDate);
            des.setNoOfCgiverTBHIVRecordsExported(dbe.exportCaregiverTBHIVScreeningChecklistInXml(additionalOrgQuery+tbhivDateQuery));
            des.setNoOfVCTBHIVRecordsExported(dbe.exportOvcTBHIVScreeningChecklistInXml(additionalOrgQuery+tbhivDateQuery));
            dbe.exportGraduationCheckListInXml(additionalOrgQuery+gclDateQuery);
            des.setCareplanAchievementRecordsExported(dbe.exportCareplanAchievementInXml(additionalOrgQuery+cpaExportDateQuery));
            dbe.exportReferralDirectoryInXml(null,additionalOrgQuery);
            des.setNoOfHIVRiskAssessmentRecordsExported(dbe.exportHivRiskAssessmentChecklistInXml(null, additionalOrgQuery));
            des.setCareAndSupportRecordsExported(dbe.exportCareAndSupportChecklistInXml(null, additionalOrgQuery));
            des.setCaregiverExpenditureRecordsExported(dbe.exportCaregiverExpenditureAndSchoolAttendanceInXml(null,additionalOrgQuery));
            
            int[] values={aproc.getEnrollmentSize(session,params,ovcWithdrawalQuery),aproc.getCsiDataSize(session,params,ovcWithdrawalQuery),aproc.getServiceDataSize(session,params,ovcWithdrawalQuery),aproc.getOrganizationRecordsDataSize(session,params),aproc.getWardRecordsDataSize(session,params),aproc.getOrganizationalAssessmentDataSize(session,params),aproc.getFollowupSurveyRecordsDataSize(session,params,ovcWithdrawalQuery),aproc.getReferralRecordsDataSize(session, params),aproc.getSchoolRecordsDataSize(session,params),aproc.getHouseholdEnrollmentDataSize(session,params,hhWithdrawalQuery),aproc.getHouseholdServiceDataSize(session, params,hhWithdrawalQuery),aproc.getHouseholdVulnerabilityAssessmentDataSize(session, params,hhWithdrawalQuery),aproc.getCaregiverDataSize(session, params,cgiverWithdrawalQuery),aproc.getNutritionAssessmentDataSize(session, params,ovcWithdrawalQuery),aproc.getHouseholdFollowupAssessmentDataSize(session, params,hhWithdrawalQuery)};

            List resultList=new ArrayList();
            List subList=new ArrayList();
            if(values !=null)
            {
                subList.add("Number of enrollment records ");
                subList.add(values[0]);
                resultList.add(subList);
                des.setNoOfVCEnrollmentRecordsExported(values[0]);
                des.setNoOfCSIRecordsExported(values[1]);
                
                subList=new ArrayList();
                subList.add("Number of service records");
                subList.add(values[2]);
                resultList.add(subList);
                des.setNoOfVCServiceRecordsExported(values[2]);
                
                System.err.println("values[2] is "+values[2]);
                subList=new ArrayList();
                subList.add("Number of organization/CSO records");
                subList.add(values[3]);
                resultList.add(subList);
                                
                subList=new ArrayList();
                subList.add("Number of followup records");
                subList.add(values[6]);
                resultList.add(subList);
                des.setNoOfVCFollowupAssessmentRecordsExported(values[6]);
                
                subList=new ArrayList();
                subList.add("Number of referral records");
                subList.add(values[7]);
                resultList.add(subList);
                des.setNoOfReferralRecordsExported(values[7]);
                
                subList=new ArrayList();
                subList.add("Number of school records");
                subList.add(values[8]);
                resultList.add(subList);
                
                subList=new ArrayList();
                subList.add("Number of household vulnerability assessment records");
                subList.add(values[9]);
                resultList.add(subList);
                des.setNoOfHHEnrollmentRecordsExported(values[9]);
                
                subList=new ArrayList();
                subList.add("Number of household service records");
                subList.add(values[10]);
                resultList.add(subList);
                des.setNoOfHHServiceRecordsExported(values[10]);
                des.setNoOfCaregiverEnrollmentRecordsExported(values[12]);
                des.setNutritionalAssessmentRecordsExported(values[13]);
                subList=new ArrayList();
                subList.add("Number of household followup records");
                subList.add(values[14]);
                resultList.add(subList);
                des.setNoOfHHFollowupAssessmentRecordsExported(values[14]);
            }
            des.setExportId(appUtil.generateUniqueId(11));
            des.setSystemTime(DateManager.getCurrentDateAndTime("dd/MM/yyyy HH:mm:ss"));
            dbe.createDataExportSummaryInXml(null, des);
            DataTransferSummaryGenerator.writeDataExportSummaryToPDF(des);
            request.setAttribute("dbExportSummaryList", resultList);
            request.setAttribute("datasize", values);
            session.setAttribute("exportPeriod", params);
            return mapping.findForward(SUMMARY);
        }
        return mapping.findForward(SUCCESS);
    }
}
