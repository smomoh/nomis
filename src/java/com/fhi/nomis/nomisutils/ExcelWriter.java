/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.nomisutils;

import com.fhi.kidmap.business.BirthRegistration;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.kidmap.business.HivRiskAssessmentChecklist;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.HouseholdService;
import com.fhi.kidmap.business.HouseholdVulnerabilityAssessment;
import com.fhi.kidmap.business.Indicators;
import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.OrganizationUnitGroup;
import com.fhi.kidmap.business.OrganizationUnitGroupAssignment;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcReferral;
import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.business.ReferralDirectory;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.ChildStatusIndexDao;
import com.fhi.kidmap.dao.ChildStatusIndexDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdServiceDao;
import com.fhi.kidmap.dao.HouseholdServiceDaoImpl;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDao;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDaoImpl;
import com.fhi.kidmap.dao.LgaDao;
import com.fhi.kidmap.dao.LgaDaoImpl;
import com.fhi.kidmap.dao.OrganizationRecordsDao;
import com.fhi.kidmap.dao.OrganizationRecordsDaoImpl;
import com.fhi.kidmap.dao.OrganizationUnitGroupAssignmentDao;
import com.fhi.kidmap.dao.OrganizationUnitGroupAssignmentDaoImpl;
import com.fhi.kidmap.dao.OrganizationUnitGroupDao;
import com.fhi.kidmap.dao.OrganizationUnitGroupDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcServiceDao;
import com.fhi.kidmap.dao.OvcServiceDaoImpl;
import com.fhi.kidmap.report.IndicatorWarehouse;
import com.fhi.kidmap.report.SummaryStatisticsBean;
import com.nomis.business.ReportTemplate;
import jxl.write.Label;
import java.io.Serializable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author smomoh
 */
public class ExcelWriter implements Serializable {

    AppUtility appUtil = new AppUtility();
    DaoUtil util = new DaoUtil();
    int numberOfServices = 0;
    public WritableWorkbook writeRevisedQuarterlyReportTemplateToExcel(OutputStream os, List mainList) 
    {
        WritableWorkbook wworkbook = null;
        //HSSFWorkbook wb = new HSSFWorkbook();
        WritableSheet wsheet = null;
        Label label = null;
        Number number = null;
        String state=null;
        String lga=null;
        String cbo=null;
        String merCode=null;
        String partnerName=null;
        Indicators ind=null;
        String indicatorId=null;
        String indicatorName=null;
        String sex=null;
        String ageCategory=null;
        try 
        {
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet("Report Sheet", 0);

            int t = 0;
            int row = 0;
            String[] columnHeadings = {"State","Lga","CBO","Partner","Indicator","Sex","Age","Other Disagg","Value","Period"};
            String[] maleAgeDisaggregations = {"<1", "1-4", "5-9", "10-14","15-17","18-24","25+"};
            String[] sexDisaggregations = {"male", "female"};
            String period=null;
            ReportTemplate rt = null;
            for (int i = 0; i < columnHeadings.length; i++) {
                label = new Label(i, 0, columnHeadings[i]);
                wsheet.addCell(label);
            }
            if (mainList != null) 
            {
                List stateValueList=null;
                int cell = 0;
                for (int j = 0; j < mainList.size(); j++) 
                {  
                                       
                    stateValueList=(List)mainList.get(j);
                    for(int k=0; k<stateValueList.size(); k++)
                    {
                        rt = (ReportTemplate) stateValueList.get(k);
                        state=rt.getState();
                        lga=rt.getLga();
                        cbo=rt.getCbo();
                        partnerName=util.getPartnerName(rt.getPartnerCode());
                        merCode=rt.getMerCode();
                        ind=rt.getIndicator();
                        indicatorName=rt.getIndicatorName();
                        period=rt.getPeriod();//.getStartMth()+""+rt.getStartYr()+"-"+rt.getEndMth()+""+rt.getEndYr();
                        if(ind !=null && (ind.getIndicatorType() !=null && ind.getIndicatorType().equalsIgnoreCase(NomisConstant.HOUSEHOLD_TYPE)))
                        {
                            cell = 0;
                            row++;
                            sex="NIL";
                            label = new Label(cell, row, state);
                            wsheet.addCell(label);
                            label = new Label(++cell, row, lga);
                            wsheet.addCell(label);
                            label = new Label(++cell, row, cbo);
                            wsheet.addCell(label);
                            label = new Label(++cell, row, partnerName);
                            wsheet.addCell(label);
                            label = new Label(++cell, row, merCode);
                            wsheet.addCell(label);
                            label = new Label(++cell, row, sex);
                            wsheet.addCell(label);
                            label = new Label(++cell, row, ageCategory);
                            wsheet.addCell(label);
                            label = new Label(++cell, row, indicatorName);
                            wsheet.addCell(label);
                            number = new Number(++cell, row, rt.getGrandTotal());
                            wsheet.addCell(number);
                            label = new Label(++cell, row, period);
                            wsheet.addCell(label);
                        }
                        else
                        {
                            for(int l=0; l<maleAgeDisaggregations.length; l++)
                            {
                                ageCategory=maleAgeDisaggregations[l];
                                for(int m=0; m<sexDisaggregations.length; m++)
                                {

                                    sex=sexDisaggregations[m];
                                    if(sex.equalsIgnoreCase("male"))
                                    {
                                        cell = 0;
                                        row++;
                                        label = new Label(cell, row, state);
                                        wsheet.addCell(label);
                                        label = new Label(++cell, row, lga);
                                        wsheet.addCell(label);
                                        label = new Label(++cell, row, cbo);
                                        wsheet.addCell(label);
                                        label = new Label(++cell, row, partnerName);
                                        wsheet.addCell(label);
                                        label = new Label(++cell, row, merCode);
                                        wsheet.addCell(label);
                                        label = new Label(++cell, row, sex);
                                        wsheet.addCell(label);
                                        label = new Label(++cell, row, ageCategory);
                                        wsheet.addCell(label);
                                        label = new Label(++cell, row, indicatorName);
                                        wsheet.addCell(label);
                                        

                                        if(ageCategory.equalsIgnoreCase("<1"))
                                        number = new Number(++cell, row, rt.getMaleLessThan1());
                                        //wsheet.addCell(number);
                                        else if(ageCategory.equalsIgnoreCase("1-4"))
                                        number = new Number(++cell, row, rt.getMale1to4());
                                        //wsheet.addCell(number);
                                        else if(ageCategory.equalsIgnoreCase("5-9"))
                                        number = new Number(++cell, row, rt.getMale5to9());
                                        //wsheet.addCell(number);
                                        else if(ageCategory.equalsIgnoreCase("10-14"))
                                        number = new Number(++cell, row, rt.getMale10to14());
                                        //wsheet.addCell(number);
                                        else if(ageCategory.equalsIgnoreCase("15-17"))
                                        number = new Number(++cell, row, rt.getMale15to17());
                                        //wsheet.addCell(number);
                                        else if(ageCategory.equalsIgnoreCase("18-24"))
                                        number = new Number(++cell, row, rt.getMale18to24());
                                        //wsheet.addCell(number);
                                        else if(ageCategory.equalsIgnoreCase("25+"))
                                        number = new Number(++cell, row, rt.getMale25AndAbove());
                                        wsheet.addCell(number);
                                        
                                        label = new Label(++cell, row, period);
                                        wsheet.addCell(label);
                                    }
                                    else if(sex.equalsIgnoreCase("female"))
                                    {
                                        row++;
                                        cell = 0;
                                        //get for females too
                                        label = new Label(cell, row, state);
                                        wsheet.addCell(label);
                                        label = new Label(++cell, row, lga);
                                        wsheet.addCell(label);
                                        label = new Label(++cell, row, cbo);
                                        wsheet.addCell(label);
                                        label = new Label(++cell, row, partnerName);
                                        wsheet.addCell(label);
                                        label = new Label(++cell, row, merCode);
                                        wsheet.addCell(label);
                                        label = new Label(++cell, row, sex);
                                        wsheet.addCell(label);
                                        label = new Label(++cell, row, ageCategory);
                                        wsheet.addCell(label);
                                        label = new Label(++cell, row, indicatorName);
                                        wsheet.addCell(label);
                                        

                                        if(ageCategory.equalsIgnoreCase("<1"))
                                        number = new Number(++cell, row, rt.getFemaleLessThan1());
                                        //wsheet.addCell(number);
                                        else if(ageCategory.equalsIgnoreCase("1-4"))
                                        number = new Number(++cell, row, rt.getFemale1to4());
                                        //wsheet.addCell(number);
                                        else if(ageCategory.equalsIgnoreCase("5-9"))
                                        number = new Number(++cell, row, rt.getFemale5to9());
                                        //wsheet.addCell(number);
                                        else if(ageCategory.equalsIgnoreCase("10-14"))
                                        number = new Number(++cell, row, rt.getFemale10to14());
                                        //wsheet.addCell(number);
                                        else if(ageCategory.equalsIgnoreCase("15-17"))
                                        number = new Number(++cell, row, rt.getFemale15to17());
                                        //wsheet.addCell(number);
                                        else if(ageCategory.equalsIgnoreCase("18-24"))
                                        number = new Number(++cell, row, rt.getFemale18to24());
                                        //wsheet.addCell(number);
                                        else if(ageCategory.equalsIgnoreCase("25+"))
                                        number = new Number(++cell, row, rt.getFemale25AndAbove());
                                        wsheet.addCell(number);
                                        
                                        label = new Label(++cell, row, period);
                                        wsheet.addCell(label);
                                    }
                                    //row++;
                                }
                            }
                        }
                    }
                }
                t++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }
    public WritableWorkbook writeQuarterlyReportToExcel(OutputStream os, List mainList) 
    {
        WritableWorkbook wworkbook = null;
        //HSSFWorkbook wb = new HSSFWorkbook();
        WritableSheet wsheet = null;
        Label label = null;
        Number number = null;

        try 
        {
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet("Report Sheet", 0);

            int t = 0;
            int row = 1;
            String[] columnHeadings = {"Indicator","MER Code","State","Lga","CBO","Partner","Period", "<1", "1-4", "5-9", "10-14","15-17","18-24","25+","Male total","<1", "1-4", "5-9", "10-14","15-17","18-24","25+","Female total","Total"};
            ReportTemplate rt = null;
            String partnerName=null;
            for (int i = 0; i < columnHeadings.length; i++) {
                label = new Label(i, 0, columnHeadings[i]);
                wsheet.addCell(label);
            }
            if (mainList != null) 
            {
                List stateValueList=null;
                int cell = 0;
                for (int j = 0; j < mainList.size(); j++) 
                {  
                                       
                    stateValueList=(List)mainList.get(j);
                    for(int k=0; k<stateValueList.size(); k++)
                    {
                        cell = 0;
                        rt = (ReportTemplate) stateValueList.get(k);
                        partnerName=util.getPartnerName(rt.getPartnerCode());
                        label = new Label(cell, row, rt.getIndicatorName());
                        wsheet.addCell(label);
                        label = new Label(++cell, row, rt.getMerCode());
                        wsheet.addCell(label);
                        label = new Label(++cell, row, rt.getState());
                        wsheet.addCell(label);
                        label = new Label(++cell, row, rt.getLga());
                        wsheet.addCell(label);
                        label = new Label(++cell, row, rt.getCbo());
                        wsheet.addCell(label);
                        label = new Label(++cell, row, partnerName);
                        wsheet.addCell(label);
                        label = new Label(++cell, row, rt.getPeriod());
                        wsheet.addCell(label);
                        number = new Number(++cell, row, rt.getMaleLessThan1());
                        wsheet.addCell(number);
                        number = new Number(++cell, row, rt.getMale1to4());
                        wsheet.addCell(number);
                        number = new Number(++cell, row, rt.getMale5to9());
                        wsheet.addCell(number);
                        number = new Number(++cell, row, rt.getMale10to14());
                        wsheet.addCell(number);
                        number = new Number(++cell, row, rt.getMale15to17());
                        wsheet.addCell(number);
                        number = new Number(++cell, row, rt.getMale18to24());
                        wsheet.addCell(number);
                        number = new Number(++cell, row, rt.getMale25AndAbove());
                        wsheet.addCell(number);
                        number = new Number(++cell, row, rt.getMaleTotal());
                        wsheet.addCell(number);

                        //get for females too
                        number = new Number(++cell, row, rt.getFemaleLessThan1());
                        wsheet.addCell(number);
                        number = new Number(++cell, row, rt.getFemale1to4());
                        wsheet.addCell(number);
                        number = new Number(++cell, row, rt.getFemale5to9());
                        wsheet.addCell(number);
                        number = new Number(++cell, row, rt.getFemale10to14());
                        wsheet.addCell(number);
                        number = new Number(++cell, row, rt.getFemale15to17());
                        wsheet.addCell(number);
                        number = new Number(++cell, row, rt.getFemale18to24());
                        wsheet.addCell(number);
                        number = new Number(++cell, row, rt.getFemale25AndAbove());
                        wsheet.addCell(number);

                        number = new Number(++cell, row, rt.getFemaleTotal());
                        wsheet.addCell(number);
                        number = new Number(++cell, row, rt.getGrandTotal());
                        wsheet.addCell(number);

                        row++;
                    }
                }
                t++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }
    public WritableWorkbook writeDatim2017ReportToExcel(OutputStream os, List datimFormList) 
    {
        WritableWorkbook wworkbook = null;
        //HSSFWorkbook wb = new HSSFWorkbook();
        WritableSheet wsheet = null;
        Label label = null;
        Number number = null;

        try {
            //appUtil.createReportDirectory();
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet("Report Sheet", 0);

            int t = 0;
            int row = 1;
            String[] columnHeadings = {"State", "Lga", "Organization unit group", "Category", "Indicator", "Period", "No of OVC"};
            SummaryStatisticsBean stb = null;
            for (int i = 0; i < columnHeadings.length; i++) {
                label = new Label(i, 0, columnHeadings[i]);
                wsheet.addCell(label);
            }
            if (datimFormList != null) {
                List stbList = null;
                for (int j = 0; j < datimFormList.size(); j++) {
                    int cell = 0;
                    stbList = (List) datimFormList.get(j);
                    for (int k = 0; k < stbList.size(); k++) {
                        cell = 0;
                        stb = (SummaryStatisticsBean) stbList.get(k);

                        label = new Label(cell, row, stb.getState());
                        wsheet.addCell(label);
                        label = new Label(++cell, row, stb.getLga());
                        wsheet.addCell(label);
                        label = new Label(++cell, row, stb.getCbo());
                        wsheet.addCell(label);
                        label = new Label(++cell, row, stb.getCategoryOptionCombo());
                        wsheet.addCell(label);
                        label = new Label(++cell, row, stb.getIndicatorName());
                        wsheet.addCell(label);
                        label = new Label(++cell, row, stb.getPeriod());
                        wsheet.addCell(label);
                        number = new Number(++cell, row, stb.getTotNoOfOvc());
                        wsheet.addCell(number);

                        row++;
                    }
                }
                t++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }

    public WritableWorkbook writeDatimReportToExcel(OutputStream os, List excelList) {
        WritableWorkbook wworkbook = null;
        //HSSFWorkbook wb = new HSSFWorkbook();
        WritableSheet wsheet = null;
        Label label = null;
        Number number = null;

        try {
            //appUtil.createReportDirectory();
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet("Report Sheet", 0);
            int t = 0;
            int row = 1;
            String[] columnHeadings = {"State", "Lga", "Organization unit group", "Organization", "Indicator", "Month", "Year", "No of OVC"};
            for (int k = 0; k < columnHeadings.length; k++) {
                label = new Label(k, 0, columnHeadings[k]);
                wsheet.addCell(label);
            }
            LgaDao lgadao = new LgaDaoImpl();
            OrganizationRecordsDao orgdao = new OrganizationRecordsDaoImpl();
            OrganizationRecords orgRecord = null;
            OrganizationUnitGroupDao ougdao = new OrganizationUnitGroupDaoImpl();
            OrganizationUnitGroup oug = null;
            OrganizationUnitGroupAssignmentDao ougadao = new OrganizationUnitGroupAssignmentDaoImpl();
            OrganizationUnitGroupAssignment ouga = null;
            String orgUnitGroupName = null;
            String lgaName = null;
            String lgaCode = null;
            String orgCode = null;
            Lgas lga = null;
            for (int i = 0; i < excelList.size(); i++) {
                //Every second item in the list is a cbo code
                if (t == 1) {
                    orgCode = orgdao.getOrganizationCode(excelList.get(i).toString());
                    orgRecord = orgdao.getOrganizationRecord(orgCode);
                    lgaCode = orgRecord.getLga();
                    lgaName = lgaCode;
                    lga = lgadao.getLgaByCode(lgaCode);
                    if (lga != null) {
                        lgaName = lga.getLga_name();
                    }
                    orgUnitGroupName = util.getOrganizationName(orgCode);
                    ouga = ougadao.getOrganizationUnitGroupAssignmentByOrgUnitIdAndParentOrgUnitGroupId(excelList.get(i).toString(), excelList.get(i).toString());
                    if (ouga != null) {
                        oug = ougdao.getOrganizationUnitGroup(ouga.getOrgUnitGroupId());
                        if (oug != null) {
                            orgUnitGroupName = oug.getOrgUnitGroupName();
                        }
                    }
                    label = new Label(++t, row, lgaName);
                    wsheet.addCell(label);
                    label = new Label(++t, row, orgUnitGroupName);
                    wsheet.addCell(label);
                }
                //Every fifth item in the list is a number
                if (t == 7) {
                    number = new Number(t, row, Integer.parseInt(excelList.get(i).toString()));
                    wsheet.addCell(number);
                    t = 0;
                    row++;
                    continue;
                } else {
                    label = new Label(t, row, excelList.get(i).toString());
                    wsheet.addCell(label);
                }
                t++;
            }
            //wworkbook.write();
            //wworkbook.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }

    public List organizeCsiData(List list) {
        //The list contains objects per communities arranged in sublists, so iterate over it and create sublists per OVC
        String csiId = null;
        List mainObjList = new ArrayList();
        List objList = new ArrayList();
        List subList = null;
        ChildStatusIndex csi = null;
        for (int i = 0; i < list.size(); i++) {
            subList = (List) list.get(i);
            for (int j = 0; j < subList.size(); j++) {
                Object[] obj = (Object[]) subList.get(j);
                csi = (ChildStatusIndex) obj[2];
                if (csiId == null) {
                    csiId = csi.getOvcId();
                }
                if (csi.getOvcId().equals(csiId)) {
                    objList.add(obj);
                } else {
                    mainObjList.add(objList);
                    objList = new ArrayList();
                    objList.add(obj);
                    csiId = csi.getOvcId();
                }
            }
        }
        return mainObjList;
    }

    public WritableSheet getSheetWithColumnHeadings(String[] columnHeadings, String[] subColumnHeadings, WritableSheet wsheet, int maxNumberOfAssessments) {
        Label label = null;
        try {
            for (int k = 0; k < columnHeadings.length; k++) {
                label = new Label(k, 0, columnHeadings[k]);
                wsheet.addCell(label);
            }
            int m = columnHeadings.length;
            for (int l = 1; l < maxNumberOfAssessments; l++) {
                for (int k = 0; k < subColumnHeadings.length; k++) {
                    label = new Label(m, 0, subColumnHeadings[k]);
                    wsheet.addCell(label);
                    m++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wsheet;
    }

    public WritableSheet addHouseholdServices(String uniqueId, String lastFollowupDate, WritableSheet wsheet, int columnSize, int row) {
        Label label = null;
        int cell = columnSize;
        HouseholdServiceDao hhsdao = new HouseholdServiceDaoImpl();

        try {
            List serviceList = hhsdao.getOvcServicesByOvcIdAndEndDate(uniqueId, lastFollowupDate);
            if (serviceList != null) {
                HouseholdService hhs = null;
                for (int i = 0; i < serviceList.size(); i++) {
                    hhs = (HouseholdService) serviceList.get(i);
                    label = new Label(++cell, row, hhs.getServiceDate());
                    wsheet.addCell(label);
                    label = new Label(++cell, row, hhs.getPsychosocialSupportServices());
                    wsheet.addCell(label);
                    label = new Label(++cell, row, hhs.getNutritionalServices());
                    wsheet.addCell(label);
                    label = new Label(++cell, row, hhs.getHealthServices());
                    wsheet.addCell(label);
                    label = new Label(++cell, row, hhs.getEducationalServices());
                    wsheet.addCell(label);
                    label = new Label(++cell, row, hhs.getProtectionServices());
                    wsheet.addCell(label);
                    label = new Label(++cell, row, hhs.getShelterAndCareServices());
                    wsheet.addCell(label);
                    label = new Label(++cell, row, hhs.getEconomicStrengtheningServices());
                    wsheet.addCell(label);
                }
                if (serviceList.size() > numberOfServices) {
                    numberOfServices = serviceList.size();
                    setHouseholdServiceFieldHeadings(wsheet, columnSize, numberOfServices);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wsheet;
    }

    public WritableSheet addServices(String uniqueId, String lastFollowupDate, WritableSheet wsheet, int columnSize, int row) {
        Label label = null;
        int cell = columnSize;
        OvcServiceDao servicedao = new OvcServiceDaoImpl();

        try {
            List serviceList = servicedao.getOvcServicesByOvcIdAndEndDate(uniqueId, lastFollowupDate);
            if (serviceList != null) {
                OvcService service = null;
                for (int i = 0; i < serviceList.size(); i++) {
                    service = (OvcService) serviceList.get(i);
                    label = new Label(++cell, row, service.getServicedate());
                    wsheet.addCell(label);
                    label = new Label(++cell, row, service.getServiceAccessed1());
                    wsheet.addCell(label);
                    label = new Label(++cell, row, service.getServiceAccessed2());
                    wsheet.addCell(label);
                    label = new Label(++cell, row, service.getServiceAccessed3());
                    wsheet.addCell(label);
                    label = new Label(++cell, row, service.getServiceAccessed4());
                    wsheet.addCell(label);
                    label = new Label(++cell, row, service.getServiceAccessed5());
                    wsheet.addCell(label);
                    label = new Label(++cell, row, service.getServiceAccessed6());
                    wsheet.addCell(label);
                    label = new Label(++cell, row, service.getServiceAccessed7());
                    wsheet.addCell(label);
                    label = new Label(++cell, row, service.getServicesReferred());
                    wsheet.addCell(label);
                }
                if (serviceList.size() > numberOfServices) {
                    numberOfServices = serviceList.size();
                    setServiceFieldHeadings(wsheet, columnSize, numberOfServices);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wsheet;
    }

    public WritableSheet setServiceFieldHeadings(WritableSheet wsheet, int columnSize, int numberOfServices) {
        Label label = null;
        int cell = columnSize;
        String[] serviceColumns = {"Service date", "Psychosocial services", "Nutritional services", "Health services", "Educational services", "Protection services", "Shelter and care", "Economic strengthening services", "Referral"};
        try {
            for (int i = 0; i < numberOfServices; i++) {
                for (int k = 0; k < serviceColumns.length; k++) {
                    label = new Label(++cell, 0, serviceColumns[k]);
                    wsheet.addCell(label);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wsheet;
    }

    public WritableSheet setHouseholdServiceFieldHeadings(WritableSheet wsheet, int columnSize, int numberOfServices) {
        Label label = null;
        int cell = columnSize;
        String[] serviceColumns = {"Service date", "Psychosocial services", "Nutritional services", "Health services", "Educational services", "Protection services", "Shelter and care", "Economic strengthening services"};
        try {
            for (int i = 0; i < numberOfServices; i++) {
                for (int k = 0; k < serviceColumns.length; k++) {
                    label = new Label(++cell, 0, serviceColumns[k]);
                    wsheet.addCell(label);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wsheet;
    }

    public WritableWorkbook writeOvcNutritionAssessmentValuesToExcel(OutputStream os, List list) {
        WritableWorkbook wworkbook = null;
        WritableSheet wsheet = null;
        String sheetName = "Report ";
        int sheetCount = 0;
        Label label = null;
        Number number = null;
        numberOfServices = 0;

        try {
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"State", "Lga", "CBO", "Ward/Community", "OVC Id", "Gender","Eligibility criteria", "HIV Status", "Current age", "OVC has birth certificate", "OVC in school", "Ovc withdrawn from program", "Month of enrollment", "Year of enrollment", "BMI", "Nutrional status", "MUAC", "Oedema", "Month of assessment", "Year of assessment", "Assessment No"};
            //String[] subColumnHeadings={"Date of assessment","Emotional Health","Social behaviour","Food security","Nutrition and growth","Wellness","Health care services","Development and performance","Education and work","Abuse and exploitation","Legal protection","Shelter","Care","Total CSI score"};
            int t = 0;
            int row = 1;
            int cellCount = 0;


            for (int k = 0; k < columnHeadings.length; k++) {
                label = new Label(k, 0, columnHeadings[k]);
                wsheet.addCell(label);
            }

            for (int i = 0; i < list.size(); i++) 
            {
                cellCount = 0;
                Ovc ovc = (Ovc) list.get(i);

                String ovcId = ovc.getOvcId();
                //Ovc ovc=ovcdao.getOvc(ovcId);
                String fullName = " ";
                String gender = " ";
                String hivStatus = " ";
                int currentAge = 0;
                String birthCertificate = " ";
                String ovcInSchool = " ";
                String nutritionStatus = " ";
                String ovcWithdrawn = " ";
                String bmi = "";

                fullName = ovc.getFullName();
                gender = ovc.getGender();
                hivStatus = ovc.getActiveHivStatus().getHivStatus();
                currentAge = ovc.getCurrentAge();
                birthCertificate = ovc.getActiveBirthRegistrationStatus();//getBirthCertificate();
                ovcInSchool = ovc.getActiveSchoolStatus();
                nutritionStatus = ovc.getNutritionAssessmentAsAtReport().getNutritionalStatus();
                bmi = ovc.getNutritionAssessmentAsAtReport().getBmi();
                nutritionStatus = ovc.getCurrentNutritionAssessment().getNutritionalStatus();
                ovcWithdrawn = ovc.getWithdrawnFromProgram();

                label = new Label(cellCount, row, ovc.getState());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getLastName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getCompletedbyCbo());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getWard());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovcId);
                wsheet.addCell(label);

                //label = new Label(++cellCount, row, fullName);
                //wsheet.addCell(label);
                label = new Label(++cellCount, row, gender);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getEligibility());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hivStatus);
                wsheet.addCell(label);
                number = new Number(++cellCount, row, currentAge);
                wsheet.addCell(number);
                label = new Label(++cellCount, row, birthCertificate);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovcInSchool);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovcWithdrawn);
                wsheet.addCell(label);
                number = new Number(++cellCount, row, ovc.getMonthOfEnrollment());
                wsheet.addCell(number);
                number = new Number(++cellCount, row, ovc.getYearOfEnrollment());
                wsheet.addCell(number);
                label = new Label(++cellCount, row, bmi);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, nutritionStatus);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getCurrentNutritionAssessment().getMuac());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getCurrentNutritionAssessment().getOedema());
                wsheet.addCell(label);
                number = new Number(++cellCount, row, ovc.getCurrentNutritionAssessment().getMonthOfAssessment());
                wsheet.addCell(number);
                number = new Number(++cellCount, row, ovc.getCurrentNutritionAssessment().getYearOfAssessment());
                wsheet.addCell(number);
                number = new Number(++cellCount, row, ovc.getCurrentNutritionAssessment().getAssessmentNo());
                wsheet.addCell(number);

                ///If record gets to 50000, create new sheet
                if (t == (49900 * (sheetCount + 1))) {
                    sheetCount++;
                    wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
                    row = 0;
                }
                row++;
                t++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }

    public WritableWorkbook writeOVCListToExcel(OutputStream os, List list) {
        WritableWorkbook wworkbook = null;
        WritableSheet wsheet = null;
        String sheetName = "Report ";
        int sheetCount = 0;
        Label label = null;
        Number number = null;
        //util.getOvcServiceDaoInstance().
        try {
            //appUtil.createReportDirectory();
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"State", "Lga","Organization unit group" ,"CBO", "Ward/Community","Volunteer name", "HH unique Id","Caregiver Id","OVC Id","Year of Enrollment", "Age at enrollment", "Age unit", "Current age", "Current age unit","Sex","Eligibility criteria", "Current HIV Status","Enrolled in Care","Enrolled on ART","Facility enrolled", "OVC Status", "Birth registration", "Point of birth registration", "Date of birth registration", "Previous day of service", "Previous month of service","Previous year of service","Previous service quarter", "Date of Last service","Last day of service","Last month of service", "Last year of service","Last service quarter","Last services provided","HH Withdrawn?","Reason dor withdrawal","Date of withdrawal","Number of OVC"};
            //String[] columnHeadings = {"State", "Lga","Organization unit group" ,"CBO", "Ward/Community","Volunteer name", "HH unique Id","Name of Household head", "Address","Caregiver Id","Caregiver name","Caregiver phone","OVC Id", "First name","Last name","Year of Enrollment", "Age at enrollment", "Age unit", "Current age", "Current age unit","Sex","Eligibility criteria", "Current HIV Status","Enrolled in Care","Enrolled on ART","Facility enrolled", "OVC Status", "Birth registration", "Point of birth registration", "Date of birth registration", "Previous day of service", "Previous month of service","Previous year of service","Previous service quarter", "Date of Last service","Last day of service","Last month of service", "Last year of service","Last service quarter","Last services provided","HH Withdrawn?","Reason dor withdrawal","Date of withdrawal","Number of OVC"};

            int t = 0;
            int row = 1;
            int cellCount = 0;
            //String yearOfEnrollment=null;          
            for (int k = 0; k < columnHeadings.length; k++) {
                label = new Label(k, 0, columnHeadings[k]);
                wsheet.addCell(label);
            }
            HouseholdEnrollment hhe = null;
            BirthRegistration birthRegistration;
            OvcService service=null;
            String lastServiceDate = null;
            String lastServiceDay=null;
            String lastServiceMth=null;
            String lastServiceYear=null;
            String previousServiceDate = null;
            String previousServiceDay=null;
            String previousServiceMth=null;
            String previousServiceYear=null;
            String previousServiceQuarter=null;
            String lastServiceQuarter=null;
            ReferralDirectory facility=null;
            String facilityCode=null;
            String facilityName=null;
            String services=null;
            
            OrganizationUnitGroupDao ougdao = new OrganizationUnitGroupDaoImpl();
            OrganizationUnitGroup oug = null;
            OrganizationUnitGroupAssignmentDao ougadao = new OrganizationUnitGroupAssignmentDaoImpl();
            OrganizationUnitGroupAssignment ouga = null;
            String orgUnitGroupName = null;
            
            Ovc ovc = null;
            for (int i = 0; i < list.size(); i++) 
            {
                previousServiceDate = null;
                lastServiceDate = null;
                lastServiceDay=null;
                lastServiceMth=null;
                lastServiceYear=null;
                previousServiceDay=null;
                previousServiceMth=null;
                previousServiceYear=null;
                previousServiceQuarter=null;
                lastServiceQuarter=null;
                facilityCode=null;
                facilityName=null;
                services=null;
                cellCount = 0;
                service=null;
                ovc = (Ovc) list.get(i);
                if (ovc == null) 
                {
                    continue;
                }
                birthRegistration = ovc.getBirthRegistration();
                //String[] dateArray=ovc.getDateEnrollment().split("-");
                //yearOfEnrollment=dateArray[0];
                hhe = ovc.getHhe();
                orgUnitGroupName = util.getOrganizationName(hhe.getOrgCode());
                    ouga = ougadao.getOrganizationUnitGroupAssignmentByOrgUnitIdAndParentOrgUnitGroupId(hhe.getCommunityCode(), hhe.getStateCode());
                    if (ouga != null) 
                    {
                        oug = ougdao.getOrganizationUnitGroup(ouga.getOrgUnitGroupId());
                        if (oug != null) 
                        {
                            orgUnitGroupName = oug.getOrgUnitGroupName();
                        }
                    }
                
                //System.err.println("ovc.getOvcId() is " + ovc.getOvcId());
                List serviceDateList = util.getOvcServiceDaoInstance().getDistinctServiceDatesPerOvc(ovc.getOvcId());
                if (serviceDateList != null && !serviceDateList.isEmpty()) 
                {
                    lastServiceDate = (String) serviceDateList.get(0);
                    if(lastServiceDate !=null && lastServiceDate.indexOf("-") !=-1)
                    {
                        service=util.getOvcServiceDaoInstance().getOvcServiceForTheMth(ovc.getOvcId(), lastServiceDate);
                        services=getServicesProvidedToOvc(service);
                        String[] lastServiceDateArray=lastServiceDate.split("-");
                        if(lastServiceDateArray !=null && lastServiceDateArray.length>2)
                        {
                            lastServiceDay=lastServiceDateArray[2];
                            lastServiceMth=lastServiceDateArray[1];
                            lastServiceYear=lastServiceDateArray[0];
                            lastServiceQuarter=getBusinessQuarter(Integer.parseInt(lastServiceMth));
                        }
                    }
                    if (serviceDateList.size() > 1) 
                    {
                        previousServiceDate = (String) serviceDateList.get(1);
                        if(previousServiceDate !=null && previousServiceDate.indexOf("-") !=-1)
                        {
                            String[] previousServiceArray=previousServiceDate.split("-");
                            if(previousServiceArray !=null && previousServiceArray.length>2)
                            {
                                previousServiceDay=previousServiceArray[2];
                                previousServiceMth=previousServiceArray[1];
                                previousServiceYear=previousServiceArray[0]; 
                                previousServiceQuarter=getBusinessQuarter(Integer.parseInt(previousServiceMth));
                            }
                            
                            if(services !=null && services.trim().equalsIgnoreCase("graduated"))
                            {
                                service=util.getOvcServiceDaoInstance().getOvcServiceForTheMth(ovc.getOvcId(), previousServiceDate);
                                services+=","+getServicesProvidedToOvc(service);
                                System.err.println("services is "+services);
                                lastServiceDate=previousServiceDate;
                                lastServiceDay=previousServiceArray[2];
                                lastServiceMth=previousServiceArray[1];
                                lastServiceYear=previousServiceArray[0];
                                lastServiceQuarter=getBusinessQuarter(Integer.parseInt(lastServiceMth));
                            }
                        }
                    }
                }
                facilityCode=ovc.getActiveHivStatus().getOrganizationClientIsReferred();
                if(facilityCode !=null)
                {
                    facility=util.getReferralDirectoryDaoInstance().getReferralDirectory(facilityCode);
                    if(facility !=null)
                    facilityName=facility.getFacilityName();
                }
                label = new Label(cellCount, row, hhe.getStateName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getLgaName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, orgUnitGroupName);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getOrgName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getCommunityName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getCompletedbyName());
                wsheet.addCell(label);
                
                label = new Label(++cellCount, row, ovc.getHhUniqueId());
                wsheet.addCell(label);
                
                //label = new Label(++cellCount, row, ovc.getHhe().getHhSurname()+" "+ovc.getHhe().getHhFirstname());
                //wsheet.addCell(label);
                //label = new Label(++cellCount, row, ovc.getHhe().getAddress());
                //wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getCaregiverId());
                wsheet.addCell(label);
                //label = new Label(++cellCount, row, ovc.getCaregiverName());
                //wsheet.addCell(label);
                //label = new Label(++cellCount, row, ovc.getCaregiverPhone());
                //wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getOvcId());
                wsheet.addCell(label);
                //label = new Label(++cellCount, row, ovc.getLastName());
                //wsheet.addCell(label);
                //label = new Label(++cellCount, row, ovc.getFirstName());
                //wsheet.addCell(label);
                
                label = new Label(++cellCount, row, ovc.getYearOfEnrollment() + "");
                wsheet.addCell(label);
                number = new Number(++cellCount, row, ovc.getAge());
                wsheet.addCell(number);
                label = new Label(++cellCount, row, ovc.getAgeUnit());
                wsheet.addCell(label);
                number = new Number(++cellCount, row, ovc.getCurrentAge());
                wsheet.addCell(number);
                label = new Label(++cellCount, row, ovc.getCurrentAgeUnit());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getGender());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getEligibility());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getActiveHivStatus().getHivStatus());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getActiveHivStatus().getClientEnrolledInCare());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getActiveHivStatus().getEnrolledOnART());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, facilityName);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getReasonForWithdrawal());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, birthRegistration.getBirthRegistrationStatus());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, birthRegistration.getPointOfUpdate());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, birthRegistration.getDateOfRegistration());
                wsheet.addCell(label);

                label = new Label(++cellCount, row, previousServiceDay);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, previousServiceMth);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, previousServiceYear);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, previousServiceQuarter);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, lastServiceDate);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, lastServiceDay);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, lastServiceMth);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, lastServiceYear);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, lastServiceQuarter);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, services);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getHhe().getWithdrawnFromProgram());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getHhe().getReasonForWithdrawal());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getHhe().getDateOfWithdrawal());
                wsheet.addCell(label);
                number = new Number(++cellCount, row, 1);
                wsheet.addCell(number);

                /*If record gets to 50000, create new sheet*/
                if (t == (49900 * (sheetCount + 1))) {
                    sheetCount++;
                    wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
                    row = 0;
                    for (int k = 0; k < columnHeadings.length; k++) {
                        label = new Label(k, 0, columnHeadings[k]);
                        wsheet.addCell(label);
                    }
                }
                row++;
                t++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }
private String getServicesProvidedToOvc(OvcService service)
{
    String services=" ";
    int serviceCount=0;
    if(service !=null)
    {
        if(service.getServiceAccessed1() !=null && appUtil.isString(service.getServiceAccessed1()))
        {
            services+=service.getServiceAccessed1();
            serviceCount++;
        }
        if(service.getServiceAccessed2() !=null && appUtil.isString(service.getServiceAccessed2()))
        {
            if(serviceCount>0)
            services+=",";
            services+=service.getServiceAccessed2();
            serviceCount++;
        }
        if(service.getServiceAccessed3() !=null && appUtil.isString(service.getServiceAccessed3()))
        {
            if(serviceCount>0)
            services+=",";
            services+=service.getServiceAccessed3();
            serviceCount++;
        }
        if(service.getServiceAccessed4() !=null && appUtil.isString(service.getServiceAccessed4()))
        {
            if(serviceCount>0)
            services+=",";
            services+=service.getServiceAccessed4();
            serviceCount++;
        }
        if(service.getServiceAccessed5() !=null && appUtil.isString(service.getServiceAccessed5()))
        {
            if(serviceCount>0)
            services+=",";
            services+=service.getServiceAccessed5();
            serviceCount++;
        }
        if(service.getServiceAccessed6() !=null && appUtil.isString(service.getServiceAccessed6()))
        {
            if(serviceCount>0)
            services+=",";
            services+=service.getServiceAccessed6();
            serviceCount++;
        }
        if(service.getServiceAccessed7() !=null && appUtil.isString(service.getServiceAccessed7()))
        {
            if(serviceCount>0)
            services+=",";
            services+=service.getServiceAccessed7();
            serviceCount++;
        }
        
    }
    return services;
}
public WritableWorkbook writeHivRiskAssessmentDataToExcel(OutputStream os, List hhOvcHivRiskAssList) 
{
        WritableWorkbook wworkbook = null;
        WritableSheet wsheet = null;
        String sheetName = "Report ";
        int sheetCount = 0;
        Label label = null;
        Number number = null;

        try 
        {
            //appUtil.createReportDirectory();
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"State", "Lga", "CBO", "Ward/Community", "HH unique Id", "Caregiver Id","OVC Id", "Surname","Other names","Day of Enrollment","Month of Enrollment","Year of Enrollment", "Age at baseline", "Age unit", "Current age", "Current age unit","Sex" ,"Address","Eligibility criteria","Baseline Hiv status","Current HIV Status","Date of current Hiv status (yyyy-mm-dd)","In Care","On ART","Day of assessment","Month of assessment","Year of assessment","Do you (Caregiver) know the HIV status of your child/ward?","Has the child tested negative for HIV more than 6 months ago?","Does the child have an HIV-positive parent/sibling/member of household that is an index patient?","Has the child ever experience any form of sexual violence?","Has the child, caregiver or any member of household within the last 3 months been chronically ill?","Is child sexually active/pregnant recently?","Has the child been hospitalized more than once in the last 3 months for recurrent and persistent fever/upper respiratory track infections over a period of at least 3 months?","Has the child ever received blood transfusion or had any medical procedure in the last 6 months?","Does this child (under 5 years) have a MUAC value of <11.5 cm/ <13.5 BMI or are there physical signs of wasting or failure to thrive?","Child at risk?","Referred for HIV Test","Date of Referral", "OVC Status","Date of current status","No. Of OVC"};

            int t = 0;
            int row = 1;
            int cellCount = 0;
            String dayOfEnrollment=null;
            String monthOfEnrollment=null;
            String yearOfEnrollment=null;  
            String dayOfAssessment=null;
            String monthOfAssessment=null;
            String yearOfAssessment=null;
            String referredForHIV="No";
            String dateReferredForHIV=" ";
            for (int k = 0; k < columnHeadings.length; k++) {
                label = new Label(k, 0, columnHeadings[k]);
                wsheet.addCell(label);
            }
            HouseholdEnrollment hhe = null;
            Caregiver cgiver=null;
            Ovc ovc = null;
            HivRiskAssessmentChecklist hra=null;
            String cgiverHivStatus=null;
            String cgiverCurrentStatus=null;
            for (int i = 0; i < hhOvcHivRiskAssList.size(); i++) 
            {
                cellCount = 0;
                cgiverHivStatus=null;
                cgiverCurrentStatus=null;
                dayOfEnrollment=null;
                monthOfEnrollment=null;
                yearOfEnrollment=null; 
                dayOfAssessment=null;
                monthOfAssessment=null;
                yearOfAssessment=null; 
                referredForHIV="No";
                dateReferredForHIV=" ";
                Object[] objArray=(Object[])hhOvcHivRiskAssList.get(i);
                hhe = (HouseholdEnrollment) objArray[0];
                ovc = (Ovc) objArray[1];
                hra = (HivRiskAssessmentChecklist) objArray[2];
                if (hhe==null || ovc == null || hra==null) 
                {
                    continue;
                }
                if(ovc.getDateEnrollment() !=null && ovc.getDateEnrollment().indexOf("-") !=-1)
                {
                    String[] dateArray=ovc.getDateEnrollment().split("-");
                    if(dateArray !=null && dateArray.length==3)
                    {
                        yearOfEnrollment=dateArray[0];
                        monthOfEnrollment=dateArray[1];
                        dayOfEnrollment=dateArray[2]; 
                    }
                }
                if(hra.getDateOfAssessment() !=null && hra.getDateOfAssessment().indexOf("-") !=-1)
                {
                    String[] dateArray=hra.getDateOfAssessment().split("-");
                    if(dateArray !=null && dateArray.length==3)
                    {
                        yearOfAssessment=dateArray[0];
                        monthOfAssessment=dateArray[1];
                        dayOfAssessment=dateArray[2]; 
                    }
                }
                OvcService service=util.getOvcServiceDaoInstance().getHTCReferralServiceRecordById(ovc.getOvcId(), hra.getDateOfAssessment());
                OvcReferral referral=util.getOvcReferralDaoInstance().getHTCReferralServiceRecordById(ovc.getOvcId(), hra.getDateOfAssessment());
                if(service !=null || referral !=null)
                {
                    referredForHIV="Yes";
                    if(service !=null && referral !=null)
                    {                        
                        if(DateManager.compareDates(service.getServicedate(), referral.getDateOfReferral()))
                        dateReferredForHIV=service.getServicedate();
                        else
                        dateReferredForHIV=referral.getDateOfReferral();
                    }
                    else if(service !=null)
                    dateReferredForHIV=service.getServicedate();
                    else
                    dateReferredForHIV=referral.getDateOfReferral();
                }
                hhe = ovc.getHhe();
                cgiver=util.getCaregiverDaoInstance().getCaregiverByCaregiverId(ovc.getCaregiverId());
                if(cgiver !=null)
                {
                    cgiverHivStatus=cgiver.getActiveHivStatus().getHivStatus();
                    cgiverCurrentStatus=cgiver.getReasonForWithdrawal();
                }
                //System.err.println("ovc.getOvcId() is " + ovc.getOvcId()+" "+i);
                util.getHouseholdVulnerabilityAssessmentDaoInstance().getMostRecentHvaScore(hhe.getHhUniqueId());
                label = new Label(cellCount, row, hhe.getStateName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getLgaName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getOrgName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getCommunityName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getHhUniqueId());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getCaregiverId());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getOvcId());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getLastName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getFirstName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, dayOfEnrollment);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, monthOfEnrollment);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, yearOfEnrollment);
                wsheet.addCell(label);
                number = new Number(++cellCount, row, ovc.getAge());
                wsheet.addCell(number);
                label = new Label(++cellCount, row, ovc.getAgeUnit());
                wsheet.addCell(label);
                number = new Number(++cellCount, row, ovc.getCurrentAge());
                wsheet.addCell(number);
                label = new Label(++cellCount, row, ovc.getCurrentAgeUnit());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getGender());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getAddress());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getEligibility());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getHivStatus());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getActiveHivStatus().getHivStatus());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getActiveHivStatus().getDateOfCurrentStatus());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getActiveHivStatus().getClientEnrolledInCare());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getActiveHivStatus().getEnrolledOnART());
                wsheet.addCell(label);              
                label = new Label(++cellCount, row, dayOfAssessment);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, monthOfAssessment);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, yearOfAssessment);
                wsheet.addCell(label);
                
                label = new Label(++cellCount, row, hra.getHivStatusQuestion());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hra.getChildTestedQuestion());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hra.getHivParentQuestion());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hra.getSexualAbuseQuestion());
                wsheet.addCell(label);
                
                label = new Label(++cellCount, row, hra.getChronicallyIllQuestion());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hra.getSexuallyActiveQuestion());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hra.getChildSickQuestion());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hra.getBloodTransfusionQuestion());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hra.getMuacbmiQuestion());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hra.getChildAtRiskQuestion());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, referredForHIV);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, dateReferredForHIV);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getReasonForWithdrawal());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getDateOfWithdrawal());
                wsheet.addCell(label);
                number = new Number(++cellCount, row, 1);
                wsheet.addCell(number);
                /*If record gets to 50000, create new sheet*/
                if (t == (49900 * (sheetCount + 1))) {
                    sheetCount++;
                    wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
                    row = 0;
                    for (int k = 0; k < columnHeadings.length; k++) {
                        label = new Label(k, 0, columnHeadings[k]);
                        wsheet.addCell(label);
                    }
                }
                row++;
                t++;
            }

        } 
        catch (ClassCastException ex) 
        {
            System.err.println("Error: "+ex.getMessage());
        }
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
        return wworkbook;
    }

public WritableWorkbook writeListOfIndicatorsTableToExcel(OutputStream os, List mainList,List metadataInfoList) 
{
        WritableWorkbook wworkbook = null;
        WritableSheet wsheet = null;
        String sheetName = "Report ";
        int sheetCount = 0;
        Label label = null;
        Number number = null;
        IndicatorWarehouse indw=new IndicatorWarehouse();
        try 
        {
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"Indicator", "Male", "Female", "Total"};

            int t = 0;
            int row = 2;
            int cellCount = 0;
            String indicatorName=null;
            String indicatorCode=null;
            int maleCount=0;
            int femaleCount=0; 
            int total=0;
            //
            for (int k = 0; k < metadataInfoList.size(); k++) 
            {
                label = new Label(k, 0, metadataInfoList.get(k).toString());
                wsheet.addCell(label);
            }
            for (int k = 0; k < columnHeadings.length; k++) 
            {
                label = new Label(k, 1, columnHeadings[k]);
                wsheet.addCell(label);
            }
            
            List subList=null;
            
            for (int i = 0; i < mainList.size(); i++) 
            {
                subList=(List)mainList.get(i);
                if(subList !=null && !subList.isEmpty())
                {
                    indicatorName=(String)subList.get(0);
                    //The size of the household result list is not more than 3 because it has no sex disaggregation while that of OVC or Caregivers has sex disaggregation 
                    if(subList.size()>3)
                    indicatorCode=subList.get(4).toString();
                    else
                    indicatorCode=subList.get(2).toString();
                    Indicators ind=indw.getIndicatorById(indicatorCode);
                    
                    cellCount = 0;
                       // System.err.println("indicatorName is " + indicatorName+" "+maleCount+" "+femaleCount+" "+total);
                        //util.getHouseholdVulnerabilityAssessmentDaoInstance().getMostRecentHvaScore(hhe.getHhUniqueId());
                        label = new Label(cellCount, row, indicatorName);
                        wsheet.addCell(label);
                        if(ind !=null && ind.getIndicatorType() !=null && ind.getIndicatorType().equalsIgnoreCase(NomisConstant.HOUSEHOLD_TYPE))
                        {
                            //This is a household indicator
                            total=Integer.parseInt(subList.get(1).toString());
                            label = new Label(++cellCount, row, "");
                            wsheet.addCell(label);
                            label = new Label(++cellCount, row, "");
                            wsheet.addCell(label);
                           
                        }
                        else
                        {
                            //this is an OVC or caregiver indicayor
                            maleCount=Integer.parseInt(subList.get(1).toString());
                            femaleCount=Integer.parseInt(subList.get(2).toString()); 
                            total=Integer.parseInt(subList.get(3).toString());
                            number = new Number(++cellCount, row, maleCount);
                            wsheet.addCell(number);
                            number = new Number(++cellCount, row, femaleCount);
                            wsheet.addCell(number);
                        }
                        number = new Number(++cellCount, row, total);
                        wsheet.addCell(number);

                        /*If record gets to 50000, create new sheet*/
                        if (t == (49900 * (sheetCount + 1))) {
                            sheetCount++;
                            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
                            row = 0;
                            for (int k = 0; k < columnHeadings.length; k++) {
                                label = new Label(k, 0, columnHeadings[k]);
                                wsheet.addCell(label);
                            }
                        }
                        row++;
                        t++;
                }
            }

        } 
        catch (ClassCastException ex) 
        {
            System.err.println("Error: "+ex.getMessage());
        }
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
        return wworkbook;
    }
public WritableWorkbook writeOvcDataFromListOfIndicatorsToExcel(String indicatorName,OutputStream os, List ovcList) 
{
        WritableWorkbook wworkbook = null;
        WritableSheet wsheet = null;
        String sheetName = "Report ";
        int sheetCount = 0;
        Label label = null;
        Number number = null;

        try 
        {
            //appUtil.createReportDirectory();
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"State", "Lga", "CBO", "Ward/Community", "Indicator","HH unique Id", "Caregiver Id","Caregiver HIV status","Caregiver current status","OVC Id", "Day of Enrollment","Month of Enrollment","Year of Enrollment", "Age at baseline", "Age unit", "Current age", "Current age unit","Sex" ,"Eligibility criteria","Baseline Hiv status","Current HIV Status","Date of current Hiv status (yyyy-mm-dd)","In Care","On ART","Facility enrolled","Point of update","Birth Registration status","Risk assessed","At Risk of HIV","Date of last assessment","Baseline school status","Current school status","Household headship","Baseline Household vulnerability status","Current Household vulnerability status","OVC Baseline CSI","OVC Baseline Vulnerability status","OVC Current CSI score","OVC Current CSI status", "OVC Status","Date of current status","HH withdrawn from Program","Reason HH withdrawn","Date of withdrawal","Age category","No. Of OVC"};

            int t = 0;
            int row = 1;
            int cellCount = 0;
            String dayOfEnrollment=null;
            String monthOfEnrollment=null;
            String yearOfEnrollment=null; 
            String riskAssessed="No";
            String atRiskOfHiv=" ";
            String dateOfRiskAssessed=" ";
            for (int k = 0; k < columnHeadings.length; k++) 
            {
                label = new Label(k, 0, columnHeadings[k]);
                wsheet.addCell(label);
            }
            HouseholdEnrollment hhe = null;
            HivRiskAssessmentChecklist hrac=null;
            Caregiver cgiver=null;
            Ovc ovc = null;
            String ageCategory="";
            String cgiverHivStatus=null;       
            String cgiverCurrentStatus=null;
            for (int i = 0; i < ovcList.size(); i++) 
            {
                cellCount = 0;
                cgiverHivStatus=null;
                cgiverCurrentStatus=null;
                dayOfEnrollment=null;
                monthOfEnrollment=null;
                yearOfEnrollment=null; 
                riskAssessed="No";
                dateOfRiskAssessed=" ";
                atRiskOfHiv=" ";
                ovc = (Ovc) ovcList.get(i);
                if (ovc == null) {
                    continue;
                }
                ageCategory = appUtil.getOvcAgeCategory(ovc);
                if(ovc.getDateEnrollment() !=null && ovc.getDateEnrollment().indexOf("-") !=-1)
                {
                    String[] dateArray=ovc.getDateEnrollment().split("-");
                    if(dateArray !=null && dateArray.length==3)
                    {
                        yearOfEnrollment=dateArray[0];
                        monthOfEnrollment=dateArray[1];
                        dayOfEnrollment=dateArray[2]; 
                    }
                }
                hhe = ovc.getHhe();
                cgiver=util.getCaregiverDaoInstance().getCaregiverByCaregiverId(ovc.getCaregiverId());
                hrac=util.getHivRiskAssessmentChecklistDaoInstance().getLastHivRiskAssessmentForOvc(ovc.getOvcId());
                if(hrac !=null)
                {
                    riskAssessed="Yes";  
                    atRiskOfHiv=hrac.getChildAtRiskQuestion();
                    dateOfRiskAssessed=hrac.getDateOfAssessment();
                }
                if(cgiver !=null)
                {
                    cgiverHivStatus=cgiver.getActiveHivStatus().getHivStatus();
                    cgiverCurrentStatus=cgiver.getReasonForWithdrawal();
                }
                System.err.println("ovc.getOvcId() is " + ovc.getOvcId()+" "+i);
                //util.getHouseholdVulnerabilityAssessmentDaoInstance().getMostRecentHvaScore(hhe.getHhUniqueId());
                
                label = new Label(cellCount, row, hhe.getStateName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getLgaName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getOrgName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getCommunityName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, indicatorName);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getHhUniqueId());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getCaregiverId());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiverHivStatus);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiverCurrentStatus);
                wsheet.addCell(label);
                
                label = new Label(++cellCount, row, ovc.getOvcId());
                wsheet.addCell(label);
                //label = new Label(++cellCount, row, ovc.getLastName());
                //wsheet.addCell(label);
                //label = new Label(++cellCount, row, ovc.getFirstName());
                //wsheet.addCell(label);
                label = new Label(++cellCount, row, dayOfEnrollment);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, monthOfEnrollment);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, yearOfEnrollment);
                wsheet.addCell(label);
                number = new Number(++cellCount, row, ovc.getAge());
                wsheet.addCell(number);
                label = new Label(++cellCount, row, ovc.getAgeUnit());
                wsheet.addCell(label);
                number = new Number(++cellCount, row, ovc.getCurrentAge());
                wsheet.addCell(number);
                label = new Label(++cellCount, row, ovc.getCurrentAgeUnit());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getGender());
                wsheet.addCell(label);
                //label = new Label(++cellCount, row, ovc.getAddress());
                //wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getEligibility());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getHivStatus());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getActiveHivStatus().getHivStatus());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getActiveHivStatus().getDateOfCurrentStatus());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getActiveHivStatus().getClientEnrolledInCare());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getEnrolledOnTreatment());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getFacility().getFacilityName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getActiveHivStatus().getPointOfUpdate());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getActiveBirthRegistrationStatus());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, riskAssessed);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, atRiskOfHiv);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, dateOfRiskAssessed);
                wsheet.addCell(label);
                
                label = new Label(++cellCount, row, ovc.getSchoolStatus());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getCurrentSchoolStatus());
                wsheet.addCell(label);
                //label = new Label(++cellCount, row, ovc.getCaregiverName());
                //wsheet.addCell(label);
                //label = new Label(++cellCount, row, ovc.getCaregiverPhone());
                //wsheet.addCell(label);
                
                number =new Number(++cellCount, row, hhe.getHhHeadship());
                wsheet.addCell(number);
                label = new Label(++cellCount, row, ovc.getHhe().getBaselineVulnerabilityStatus());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getHhe().getCurrentVulnerabilityStatus());
                wsheet.addCell(label);
                number =new Number(++cellCount, row, ovc.getBaselineVulnerabilityScore());
                wsheet.addCell(number);
                label = new Label(++cellCount, row, ovc.getBaselineVulnerabilityStatus());
                wsheet.addCell(label);
                number =new Number(++cellCount, row, ovc.getCurrentVulnerabilityScore());
                wsheet.addCell(number);
                label = new Label(++cellCount, row, ovc.getCurrentVulnerabilityStatus());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getReasonForWithdrawal());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getDateOfWithdrawal());
                wsheet.addCell(label);
                //label = new Label(++cellCount, row, ovc.getCompletedbyName());
                //wsheet.addCell(label);
                //label = new Label(++cellCount, row, ovc.getCompletedbyDesignation());
                //wsheet.addCell(label);
                
                label = new Label(++cellCount, row, ovc.getHhe().getWithdrawnFromProgram());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getHhe().getReasonForWithdrawal());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getHhe().getDateOfWithdrawal());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ageCategory);
                wsheet.addCell(label);
                number = new Number(++cellCount, row, 1);
                wsheet.addCell(number);
                
                /*If record gets to 50000, create new sheet*/
                if (t == (49900 * (sheetCount + 1))) {
                    sheetCount++;
                    wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
                    row = 0;
                    for (int k = 0; k < columnHeadings.length; k++) {
                        label = new Label(k, 0, columnHeadings[k]);
                        wsheet.addCell(label);
                    }
                }
                row++;
                t++;
            }

        } 
        catch (ClassCastException ex) 
        {
            System.err.println("Error: "+ex.getMessage());
        }
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
        return wworkbook;
    }
    public WritableWorkbook writeOVCRadetToExcel(OutputStream os, List list) {
        WritableWorkbook wworkbook = null;
        WritableSheet wsheet = null;
        String sheetName = "Report ";
        int sheetCount = 0;
        Label label = null;
        Number number = null;

        try {
            //appUtil.createReportDirectory();
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"State", "Lga", "CBO", "Ward/Community", "HH unique Id", "Address","Caregiver Id", "OVC Id", "Year of Enrollment", "Sex","Age at enrollment", "Age unit", "Current age", "Current age unit","Eligibility criteria","Age category","Major age category", "Current HIV Status","On ART", "OVC Status","Datim Status","No. of OVC"};

            int t = 0;
            int row = 1;
            int cellCount = 0;
            //String yearOfEnrollment=null;          
            for (int k = 0; k < columnHeadings.length; k++) {
                label = new Label(k, 0, columnHeadings[k]);
                wsheet.addCell(label);
            }
            HouseholdEnrollment hhe = null;
            String ageCategory="";
            String majorAgeCategory="";
            String datimStatus="";
            Ovc ovc = null;
            for (int i = 0; i < list.size(); i++) {
                cellCount = 0;
                ovc = (Ovc) list.get(i);
                if (ovc == null) {
                    continue;
                }
                ageCategory = appUtil.getOvcAgeCategory(ovc);
                datimStatus=appUtil.getDatimStatus(ovc.getReasonForWithdrawal());
                majorAgeCategory=appUtil.getMajorAgeCategory(util.getCurrentAge(ovc));
                //String[] dateArray=ovc.getDateEnrollment().split("-");
                //yearOfEnrollment=dateArray[0];
                hhe = ovc.getHhe();
                //System.err.println("ovc.getOvcId() is " + ovc.getOvcId());
                label = new Label(cellCount, row, hhe.getStateName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getLgaName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getOrgName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getCommunityName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getHhUniqueId());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getAddress());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getCaregiverId());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getOvcId());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getYearOfEnrollment() + "");
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getGender());
                wsheet.addCell(label);
                number = new Number(++cellCount, row, ovc.getAge());
                wsheet.addCell(number);
                label = new Label(++cellCount, row, ovc.getAgeUnit());
                wsheet.addCell(label);
                number = new Number(++cellCount, row, ovc.getCurrentAge());
                wsheet.addCell(number);
                label = new Label(++cellCount, row, ovc.getCurrentAgeUnit());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getEligibility());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ageCategory);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, majorAgeCategory);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getActiveHivStatus().getHivStatus());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getEnrolledOnTreatment());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, ovc.getReasonForWithdrawal());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, datimStatus);
                wsheet.addCell(label);
                number = new Number(++cellCount, row, 1);
                wsheet.addCell(number);

                /*If record gets to 50000, create new sheet*/
                if (t == (49900 * (sheetCount + 1))) {
                    sheetCount++;
                    wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
                    row = 0;
                    for (int k = 0; k < columnHeadings.length; k++) {
                        label = new Label(k, 0, columnHeadings[k]);
                        wsheet.addCell(label);
                    }
                }
                row++;
                t++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }
    public WritableWorkbook writeCaregiverListToExcel(String indicatorName,OutputStream os, List list) {
        WritableWorkbook wworkbook = null;
        WritableSheet wsheet = null;
        String sheetName = "Report ";
        int sheetCount = 0;
        Label label = null;
        Number number = null;

        try {
            //appUtil.createReportDirectory();
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"State", "Lga", "CBO", "Ward/Community","Indicator" ,"HH unique Id", "Caregiver Id","Year of Enrollment", "Age at enrollment","Current age","Sex", "Current HIV Status","Facility enrolled", "Caregiver Status","Date of current status","No of Caregivers"};

            int t = 0;
            int row = 1;
            int cellCount = 0;
            String yearOfEnrollment = null;
            for (int k = 0; k < columnHeadings.length; k++) {
                label = new Label(k, 0, columnHeadings[k]);
                wsheet.addCell(label);
            }
            //HouseholdService hhs = null;
            Caregiver cgiver = null;
            String dateOfEnrollment = null;
            for (int i = 0; i < list.size(); i++) {
                cellCount = 0;
                yearOfEnrollment = null;
                cgiver = (Caregiver) list.get(i);
                
                if (cgiver == null) {
                    continue;
                }
                dateOfEnrollment = cgiver.getDateOfEnrollment();
                if (dateOfEnrollment != null && dateOfEnrollment.indexOf("/") != -1) {
                    String[] dateArray = cgiver.getDateOfEnrollment().split("/");
                    if (dateArray != null && dateArray.length > 2) {
                        yearOfEnrollment = dateArray[2];
                    }
                }
                System.err.println("cgiver.getCaregiverId() is " + cgiver.getCaregiverId());
                label = new Label(cellCount, row, cgiver.getHhe().getStateName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getHhe().getLgaName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getHhe().getOrgName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getHhe().getCommunityName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, indicatorName);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getHhUniqueId());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getCaregiverId());
                wsheet.addCell(label);
                //label = new Label(++cellCount, row, cgiver.getCaregiverFullName());
                //wsheet.addCell(label);
                //label = new Label(++cellCount, row, cgiver.getCaregiverAddress());
                //wsheet.addCell(label);
                label = new Label(++cellCount, row, yearOfEnrollment);
                wsheet.addCell(label);
                number = new Number(++cellCount, row, cgiver.getCaregiverAge());
                wsheet.addCell(number);
                number = new Number(++cellCount, row, cgiver.getCurrentAge());
                wsheet.addCell(number);
                label = new Label(++cellCount, row, cgiver.getCaregiverGender());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getActiveHivStatus().getHivStatus());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getFacility().getFacilityName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getReasonForWithdrawal());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getDateOfWithdrawal());
                wsheet.addCell(label);
                number = new Number(++cellCount, row, 1);
                wsheet.addCell(number);
                /*If record gets to 50000, create new sheet*/
                if (t == (49900 * (sheetCount + 1))) {
                    sheetCount++;
                    wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
                    row = 0;
                    for (int k = 0; k < columnHeadings.length; k++) {
                        label = new Label(k, 0, columnHeadings[k]);
                        wsheet.addCell(label);
                    }
                }
                row++;
                t++;
            }

        }
        catch (ClassCastException ex) 
        {
            System.err.println("Error: "+ex.getMessage());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }
    public WritableWorkbook writeCaregiverRadetToExcel(OutputStream os, List list) {
        WritableWorkbook wworkbook = null;
        WritableSheet wsheet = null;
        String sheetName = "Report ";
        int sheetCount = 0;
        Label label = null;
        Number number = null;

        try {
            //appUtil.createReportDirectory();
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"State", "Lga", "CBO", "Ward/Community", "HH unique Id","Address", "Caregiver Id", "Year of Enrollment", "Age at enrollment", "Current age","Sex", "Current HIV Status","Date of Current HIV Status","On ART", "Caregiver Status","Datim Status","Date of current status","No. of OVC"};

            int t = 0;
            int row = 1;
            int cellCount = 0;
            String yearOfEnrollment = null;
            for (int k = 0; k < columnHeadings.length; k++) {
                label = new Label(k, 0, columnHeadings[k]);
                wsheet.addCell(label);
            }
            HouseholdService hhs = null;
            Caregiver cgiver = null;
            String datimStatus=null;
            String dateOfEnrollment = null;
            for (int i = 0; i < list.size(); i++) {
                cellCount = 0;
                yearOfEnrollment = null;
                hhs = (HouseholdService) list.get(i);
                if (hhs == null) {
                    continue;
                }
                cgiver = hhs.getCgiver();
                if (cgiver == null) {
                    continue;
                }
                datimStatus=appUtil.getDatimStatus(cgiver.getReasonForWithdrawal());
                dateOfEnrollment = cgiver.getDateOfEnrollment();
                if (dateOfEnrollment != null && dateOfEnrollment.indexOf("/") != -1) {
                    String[] dateArray = cgiver.getDateOfEnrollment().split("/");
                    if (dateArray != null && dateArray.length > 2) {
                        yearOfEnrollment = dateArray[2];
                    }
                }
                System.err.println("cgiver.getCaregiverId() is " + cgiver.getCaregiverId());
                label = new Label(cellCount, row, cgiver.getHhe().getStateName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getHhe().getLgaName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getHhe().getOrgName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getHhe().getCommunityName());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getHhUniqueId());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getCaregiverAddress());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getCaregiverId());
                wsheet.addCell(label);

                label = new Label(++cellCount, row, yearOfEnrollment);
                wsheet.addCell(label);
                number = new Number(++cellCount, row, cgiver.getCaregiverAge());
                wsheet.addCell(number);
                number = new Number(++cellCount, row, cgiver.getCurrentAge());
                wsheet.addCell(number);
                label = new Label(++cellCount, row, cgiver.getCaregiverGender());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getActiveHivStatus().getHivStatus());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getActiveHivStatus().getDateOfCurrentStatus());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getEnrolledOnTreatment());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getReasonForWithdrawal());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, datimStatus);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, cgiver.getDateOfWithdrawal());
                wsheet.addCell(label);
                number = new Number(++cellCount, row, 1);
                wsheet.addCell(number);

                /*If record gets to 50000, create new sheet*/
                if (t == (49900 * (sheetCount + 1))) {
                    sheetCount++;
                    wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
                    row = 0;
                    for (int k = 0; k < columnHeadings.length; k++) {
                        label = new Label(k, 0, columnHeadings[k]);
                        wsheet.addCell(label);
                    }
                }
                row++;
                t++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }

    public WritableWorkbook writeCSIValuesToExcel(OutputStream os, List list) {
        WritableWorkbook wworkbook = null;
        WritableSheet wsheet = null;
        String sheetName = "Report ";
        int sheetCount = 0;
        Label label = null;
        Number number = null;
        numberOfServices = 0;

        try {
            //appUtil.createReportDirectory();
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            //String[] columnHeadings = {"State", "Lga", "Organization unit group", "CBO", "Ward/Community", "OVC Id", "Name", "Gender", "HIV Status", "Current age", "OVC has birth certificate", "OVC in school", "Ovc withdrawn from program", "Date of assessment", "Emotional Health", "Social behaviour", "Food security", "Nutrition and growth", "Wellness", "Health care services", "Development and performance", "Education and work", "Abuse and exploitation", "Legal protection", "Shelter", "Care", "Total CSI score"};
            String[] columnHeadings = {"State", "Lga", "CBO", "Ward/Community", "OVC Id","Sex", "HIV Status", "Current age", "OVC has birth certificate", "OVC in school","Ovc withdrawn from program",  "Date of assessment", "Emotional Health", "Social behaviour", "Food security", "Nutrition and growth", "Wellness", "Health care services", "Development and performance", "Education and work", "Abuse and exploitation", "Legal protection", "Shelter", "Care","Total CSI score"};
            String[] subColumnHeadings = {"Date of assessment", "Emotional Health", "Social behaviour", "Food security", "Nutrition and growth", "Wellness", "Health care services", "Development and performance", "Education and work", "Abuse and exploitation", "Legal protection", "Shelter", "Care","Total CSI score"};
            int t = 0;
            int row = 1;
            int cellCount = 0;
            int columnSize = 0;
            int numberOfAssessments = 0;
            int maxNumberOfAssessments = 0;
            List subList = null;

            /*for(int k=0; k<columnHeadings.length; k++)
            {
            label = new Label(k, 0, columnHeadings[k]);
            wsheet.addCell(label);
            }*/

            OrganizationUnitGroupDao ougdao = new OrganizationUnitGroupDaoImpl();
            OrganizationUnitGroup oug = null;
            OrganizationUnitGroupAssignmentDao ougadao = new OrganizationUnitGroupAssignmentDaoImpl();
            OrganizationUnitGroupAssignment ouga = null;
            String orgUnitGroupName = null;
            HouseholdEnrollment hhe = null;
            ChildStatusIndex csi = null;
            ChildStatusIndexDao csidao = new ChildStatusIndexDaoImpl();
            OvcDao ovcdao = new OvcDaoImpl();
            List mainObjList = organizeCsiData(list);
            //get the list with the largest record and use that to get the length of the column
            for (int i = 0; i < mainObjList.size(); i++) {
                cellCount = 0;
                subList = (List) mainObjList.get(i);
                if (subList.size() > maxNumberOfAssessments) {
                    maxNumberOfAssessments = subList.size();
                }
            }
            //baseline assessment is already added to initial columnHeadings array, so remove it from the column size calculation.
            if (maxNumberOfAssessments > 0) {
                numberOfAssessments = maxNumberOfAssessments - 1;
            }
            //get the size of the column
            columnSize = (numberOfAssessments * (subColumnHeadings.length)) + columnHeadings.length - 1;
            wsheet = getSheetWithColumnHeadings(columnHeadings, subColumnHeadings, wsheet, maxNumberOfAssessments);
            for (int i = 0; i < mainObjList.size(); i++) {
                cellCount = 0;
                subList = (List) mainObjList.get(i);
                for (int j = 0; j < subList.size(); j++) {
                    /*if(subList.size()>columnSize)
                    columnSize=subList.size();*/
                    Object[] obj = (Object[]) subList.get(j);
                    hhe = (HouseholdEnrollment) obj[0];
                    csi = (ChildStatusIndex) obj[2];

                    //orgUnitGroupName = util.getOrganizationName(hhe.getOrgCode());
                    /*ouga = ougadao.getOrganizationUnitGroupAssignmentByOrgUnitIdAndParentOrgUnitGroupId(hhe.getCommunityCode(), hhe.getStateCode());
                    if (ouga != null) 
                    {
                        oug = ougdao.getOrganizationUnitGroup(ouga.getOrgUnitGroupId());
                        if (oug != null) {
                            orgUnitGroupName = oug.getOrgUnitGroupName();
                        }
                    }*/
                    if (j == 0) {
                        String ovcId = csi.getOvcId();
                        Ovc ovc = ovcdao.getOvc(ovcId);
                        String fullName = " ";
                        String gender = " ";
                        String hivStatus = " ";
                        int currentAge = 0;
                        String birthCertificate = " ";
                        String ovcInSchool = " ";
                        String ovcWithdrawn = " ";
                        label = new Label(cellCount, row, hhe.getStateName());
                        wsheet.addCell(label);
                        label = new Label(++cellCount, row, hhe.getLgaName());
                        wsheet.addCell(label);
                        //label = new Label(++cellCount, row, orgUnitGroupName);
                        //wsheet.addCell(label);
                        label = new Label(++cellCount, row,hhe.getOrgName());
                        wsheet.addCell(label);
                        label = new Label(++cellCount, row, hhe.getCommunityName());
                        wsheet.addCell(label);
                        label = new Label(++cellCount, row, csi.getOvcId());
                        wsheet.addCell(label);
                        
                        if (ovc != null) {
                            fullName = ovc.getFullName();
                            gender = ovc.getGender();
                            hivStatus = ovc.getActiveHivStatus().getHivStatus();
                            currentAge = ovc.getCurrentAge();
                            birthCertificate = ovc.getActiveBirthRegistrationStatus();//getBirthCertificate();
                            ovcInSchool = ovc.getActiveSchoolStatus();
                            ovcWithdrawn = ovc.getWithdrawnFromProgram();
                        }
                        
                        label = new Label(++cellCount, row, gender);
                        wsheet.addCell(label);
                        label = new Label(++cellCount, row, hivStatus);
                        wsheet.addCell(label);
                        number = new Number(++cellCount, row, currentAge);
                        wsheet.addCell(number);
                        label = new Label(++cellCount, row, birthCertificate);
                        wsheet.addCell(label);
                        label = new Label(++cellCount, row, ovcInSchool);
                        wsheet.addCell(label);
                        label = new Label(++cellCount, row, ovcWithdrawn);
                        wsheet.addCell(label);
                    }
                    label = new Label(++cellCount, row, csi.getCsiDate());
                    wsheet.addCell(label);
                    number = new Number(++cellCount, row, csi.getCsiFactor1());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, csi.getCsiFactor2());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, csi.getCsiFactor3());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, csi.getCsiFactor4());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, csi.getCsiFactor5());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, csi.getCsiFactor6());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, csi.getCsiFactor7());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, csi.getCsiFactor8());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, csi.getCsiFactor9());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, csi.getCsiFactor10());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, csi.getCsiFactor11());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, csi.getCsiFactor12());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, csidao.getTotalCsiScore(csi));
                    wsheet.addCell(number);
                    System.err.println(csi.getOvcId()+" written to excel");
                }
                //System.err.println("columnSize is "+columnSize);
                //wsheet = addServices(csi.getOvcId(), csi.getCsiDate(), wsheet, columnSize, row);
                ///If record gets to 50000, create new sheet
                if (t == (49900 * (sheetCount + 1))) {
                    sheetCount++;
                    wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
                    row = 0;
                    wsheet = getSheetWithColumnHeadings(columnHeadings, subColumnHeadings, wsheet, maxNumberOfAssessments);
                }
                row++;
                t++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }

    public WritableWorkbook writeCSITotalScoresForVulnerabilityStatusToExcel(OutputStream os, List list) {
        WritableWorkbook wworkbook = null;
        WritableSheet wsheet = null;
        String sheetName = "Report ";
        int sheetCount = 0;
        Label label = null;
        Number number = null;

        try {
            //appUtil.createReportDirectory();
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"State", "Lga", "Ward/Community", "Organization unit group", "Organization", "surveyNumber", "CSI total score", "Vulnerability status", "Number of OVC"};

            int t = 0;
            int row = 1;
            int cellCount = 0;

            for (int k = 0; k < columnHeadings.length; k++) {
                label = new Label(k, 0, columnHeadings[k]);
                wsheet.addCell(label);
            }
            OrganizationUnitGroupDao ougdao = new OrganizationUnitGroupDaoImpl();
            OrganizationUnitGroup oug = null;
            OrganizationUnitGroupAssignmentDao ougadao = new OrganizationUnitGroupAssignmentDaoImpl();
            OrganizationUnitGroupAssignment ouga = null;
            String orgUnitGroupName = null;
            String stateCode = null;
            String lgaName = null;
            String cboCode = null;
            String communityCode = null;
            String vulnerabilityStatus = null;
            int totalCsiScore = 0;
            int assessmentNumber = 0;
            int numberOfOvc = 0;
            HouseholdEnrollment hhe = null;
            ChildStatusIndex csi = null;
            OvcDao ovcdao = new OvcDaoImpl();
            for (int i = 0; i < list.size(); i++) {
                cellCount = 0;
                Object[] obj = (Object[]) list.get(i);
                stateCode = (String) obj[0];
                lgaName = (String) obj[1];
                cboCode = (String) obj[2];
                communityCode = (String) obj[3];
                totalCsiScore = Integer.parseInt(obj[4].toString());
                vulnerabilityStatus = appUtil.getOvcVulnerabilityStatusByCSI(totalCsiScore);
                assessmentNumber = Integer.parseInt(obj[5].toString());
                numberOfOvc = Integer.parseInt(obj[6].toString());
                orgUnitGroupName = util.getOrganizationName(cboCode);
                ouga = ougadao.getOrganizationUnitGroupAssignmentByOrgUnitIdAndParentOrgUnitGroupId(communityCode, stateCode);
                if (ouga != null) {
                    oug = ougdao.getOrganizationUnitGroup(ouga.getOrgUnitGroupId());
                    if (oug != null) {
                        orgUnitGroupName = oug.getOrgUnitGroupName();
                    }
                }

                label = new Label(cellCount, row, util.getStateName(stateCode));
                wsheet.addCell(label);
                label = new Label(++cellCount, row, util.getLgaName(lgaName));
                wsheet.addCell(label);
                label = new Label(++cellCount, row, util.getWardName(communityCode));
                wsheet.addCell(label);
                label = new Label(++cellCount, row, orgUnitGroupName);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, util.getOrganizationName(cboCode));
                wsheet.addCell(label);
                number = new Number(++cellCount, row, assessmentNumber);
                wsheet.addCell(number);
                number = new Number(++cellCount, row, totalCsiScore);
                wsheet.addCell(number);
                label = new Label(++cellCount, row, vulnerabilityStatus);
                wsheet.addCell(label);
                number = new Number(++cellCount, row, numberOfOvc);
                wsheet.addCell(number);

                /*If record gets to 50000, create new sheet*/
                if (t == (49900 * (sheetCount + 1))) {
                    sheetCount++;
                    wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
                    row = 0;
                    for (int k = 0; k < columnHeadings.length; k++) {
                        label = new Label(k, 0, columnHeadings[k]);
                        wsheet.addCell(label);
                    }
                }
                row++;
                t++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }

    public WritableWorkbook writeHVAValuesToExcel(OutputStream os, List list) {
        WritableWorkbook wworkbook = null;
        WritableSheet wsheet = null;
        String sheetName = "Report ";
        int sheetCount = 0;
        int t = 0;
        int row = 1;
        int cellCount = 0;
        int numberOfAssessments = 0;
        int maxNumberOfAssessments = 0;
        Label label = null;
        Number number = null;
        numberOfServices = 0;

        try {
            //appUtil.createReportDirectory();
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"State", "Lga", "Organization unit group", "CBO", "Ward/Community", "Latitude", "Longitude", "HH unique Id", "Gender", "Age", "Marital status", "Occupation", "Number of Children", "Number of people", "Household withdrawn from program", "Caregiver Id", "Caregiver age", "Caregiver gender", "Caregiver Marital status", "Caregiver Occupation", "Caregiver withdrawn from program", "Number of Caregivers in HH", "Date of assessment", "Health", "Education Level", "Food security and nutrition", "HH headship", "HH income", "Means of livelihood", "Shelter and housing", "Total score"};
            String[] subColumnHeadings = {"Date of assessment", "Health", "Education Level", "Food security and nutrition", "HH headship", "HH income", "Means of livelihood", "Shelter and housing", "Total score"};

            int columnSize = 0;
            int numberOfCaregivers = 0;
            List subList = null;
            List cgiverList = null;

            OrganizationUnitGroupDao ougdao = new OrganizationUnitGroupDaoImpl();
            OrganizationUnitGroup oug = null;
            OrganizationUnitGroupAssignmentDao ougadao = new OrganizationUnitGroupAssignmentDaoImpl();
            OrganizationUnitGroupAssignment ouga = null;
            String orgUnitGroupName = null;
            CaregiverDao cgiverdao = new CaregiverDaoImpl();
            Caregiver cgiver = null;
            HouseholdEnrollment hhe = null;
            HouseholdVulnerabilityAssessment hva = null;
            HouseholdVulnerabilityAssessmentDao hvadao = new HouseholdVulnerabilityAssessmentDaoImpl();

            List mainObjList = new ArrayList();//organizeHVAData(list);
            //get the list with the largest record and use that to get the length of the column
            for (int i = 0; i < list.size(); i++) {
                cellCount = 0;
                subList = (List) list.get(i);
                mainObjList.add(subList);
                if (subList.size() > maxNumberOfAssessments) {
                    maxNumberOfAssessments = subList.size();
                }
            }
            if (maxNumberOfAssessments > 0) {
                numberOfAssessments = maxNumberOfAssessments - 1;
            }
            columnSize = (numberOfAssessments * (subColumnHeadings.length)) + columnHeadings.length - 1;
            wsheet = getSheetWithColumnHeadings(columnHeadings, subColumnHeadings, wsheet, maxNumberOfAssessments);
            for (int i = 0; i < mainObjList.size(); i++) {
                cellCount = 0;
                subList = (List) mainObjList.get(i);
                for (int j = 0; j < subList.size(); j++) {
                    Object[] obj = (Object[]) subList.get(j);
                    hhe = (HouseholdEnrollment) obj[0];
                    hva = (HouseholdVulnerabilityAssessment) obj[1];
                    numberOfCaregivers = 0;
                    if (hhe != null) {
                        cgiverList = cgiverdao.getListOfCaregiversFromSameHousehold(hhe.getHhUniqueId());
                    }
                    if (cgiverList != null && !cgiverList.isEmpty()) {
                        cgiver = (Caregiver) cgiverList.get(0);
                        numberOfCaregivers = cgiverList.size();
                    }
                    if (cgiver == null) {
                        cgiver = new Caregiver();
                    }
                    orgUnitGroupName = util.getOrganizationName(hhe.getOrgCode());
                    ouga = ougadao.getOrganizationUnitGroupAssignmentByOrgUnitIdAndParentOrgUnitGroupId(hhe.getCommunityCode(), hhe.getStateCode());
                    if (ouga != null) {
                        oug = ougdao.getOrganizationUnitGroup(ouga.getOrgUnitGroupId());
                        if (oug != null) {
                            orgUnitGroupName = oug.getOrgUnitGroupName();
                        }
                    }
                    if (j == 0) {
                        label = new Label(cellCount, row, util.getStateName(hhe.getStateCode()));
                        wsheet.addCell(label);
                        label = new Label(++cellCount, row, util.getLgaName(hhe.getLgaCode()));
                        wsheet.addCell(label);
                        label = new Label(++cellCount, row, orgUnitGroupName);
                        wsheet.addCell(label);
                        label = new Label(++cellCount, row, util.getOrganizationName(hhe.getOrgCode()));
                        wsheet.addCell(label);
                        label = new Label(++cellCount, row, util.getWardName(hhe.getCommunityCode()));
                        wsheet.addCell(label);

                        label = new Label(++cellCount, row, ((Double) hhe.getLatitude()).toString());
                        wsheet.addCell(label);
                        label = new Label(++cellCount, row, ((Double) hhe.getLongitude()).toString());
                        wsheet.addCell(label);

                        label = new Label(++cellCount, row, hva.getHhUniqueId());
                        wsheet.addCell(label);
                        //label = new Label(++cellCount, row, hhe.getHhFirstname() + " " + hhe.getHhSurname());
                        //wsheet.addCell(label);
                        label = new Label(++cellCount, row, hhe.getHhGender());
                        wsheet.addCell(label);
                        number = new Number(++cellCount, row, hhe.getCurrentAge());
                        wsheet.addCell(number);
                        label = new Label(++cellCount, row, hhe.getMaritalStatus());
                        wsheet.addCell(label);
                        label = new Label(++cellCount, row, hhe.getOccupation());
                        wsheet.addCell(label);
                        number = new Number(++cellCount, row, hhe.getNoOfChildrenInhh());
                        wsheet.addCell(number);
                        number = new Number(++cellCount, row, hhe.getNoOfPeopleInhh());
                        wsheet.addCell(number);
                        label = new Label(++cellCount, row, hhe.getWithdrawnFromProgram());
                        wsheet.addCell(label);

                        label = new Label(++cellCount, row, cgiver.getCaregiverId());
                        wsheet.addCell(label);
                        number = new Number(++cellCount, row, cgiver.getCaregiverAge());
                        wsheet.addCell(number);
                        label = new Label(++cellCount, row, cgiver.getCaregiverGender());
                        wsheet.addCell(label);
                        label = new Label(++cellCount, row, cgiver.getCaregiverMaritalStatus());
                        wsheet.addCell(label);
                        label = new Label(++cellCount, row, cgiver.getCaregiverOccupation());
                        wsheet.addCell(label);
                        label = new Label(++cellCount, row, cgiver.getWithdrawnFromProgram());
                        wsheet.addCell(label);
                        number = new Number(++cellCount, row, numberOfCaregivers);
                        wsheet.addCell(number);
                    }
                    label = new Label(++cellCount, row, hva.getDateOfAssessment());
                    wsheet.addCell(label);
                    number = new Number(++cellCount, row, hva.getHealth());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, hva.getEducationLevel());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, hva.getFoodSecurityAndNutrition());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, hva.getHhHeadship());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, hva.getHhIncome());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, hva.getMeansOfLivelihood());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, hva.getShelterAndHousing());
                    wsheet.addCell(number);
                    number = new Number(++cellCount, row, hvadao.getHouseholdVulnerabilityScore(hva));
                    wsheet.addCell(number);

                }
                wsheet = addHouseholdServices(hva.getHhUniqueId(), hva.getDateOfAssessment(), wsheet, columnSize, row);
                /*If record gets to 50000, create new sheet*/
                if (t == (49900 * (sheetCount + 1))) {
                    sheetCount++;
                    wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
                    wsheet = getSheetWithColumnHeadings(columnHeadings, subColumnHeadings, wsheet, maxNumberOfAssessments);
                    row = 0;

                }
                row++;
                t++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }

    public WritableWorkbook writeHVAAnalysisResultToExcel(OutputStream os, List list) {
        WritableWorkbook wworkbook = null;
        WritableSheet wsheet = null;
        String sheetName = "Report ";
        int sheetCount = 0;
        int t = 0;
        int row = 1;
        int cellCount = 0;
        int numberOfAssessments = 0;
        int maxNumberOfAssessments = 0;
        Label label = null;
        Number number = null;
        numberOfServices = 0;

        try {
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"State", "Not vulnerable(Score < 7)", "Vulnerable", "More vulnerable", "Most vulnerable", "Total assessed"};
            for (int k = 0; k < columnHeadings.length; k++) {
                label = new Label(k, 0, columnHeadings[k]);
                wsheet.addCell(label);
            }
            for (int i = 0; i < list.size(); i += 4) {
                label = new Label(++cellCount, row, list.get(i).toString());
                wsheet.addCell(label);
                number = new Number(++cellCount, row, Integer.parseInt(list.get(i + 1).toString()));
                wsheet.addCell(number);
                number = new Number(++cellCount, row, Integer.parseInt(list.get(i + 2).toString()));
                wsheet.addCell(number);
                number = new Number(++cellCount, row, Integer.parseInt(list.get(i + 3).toString()));
                wsheet.addCell(number);
                number = new Number(++cellCount, row, Integer.parseInt(list.get(i + 4).toString()));
                wsheet.addCell(number);
            }
            /*If record gets to 50000, create new sheet*/
            if (t == (49900 * (sheetCount + 1))) {
                sheetCount++;
                wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);

                row = 0;

            }
            row++;
            t++;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }

    public WritableWorkbook writeCaregiverIndicatorValuesToExcel(OutputStream os, List list, String reportPeriod) {
        WritableWorkbook wworkbook = null;
        WritableSheet wsheet = null;
        String sheetName = "Report ";
        int sheetCount = 0;
        Label label = null;
        Number number = null;

        try {
            //appUtil.createReportDirectory();
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"State", "Lga", "Organization unit group", "CBO", "Ward/Community", "Indicator", "Gender", "Current age", "Period", "No of Caregivers"};
            int t = 0;
            int row = 1;
            List subList = null;
            String indicator = null;
            for (int k = 0; k < columnHeadings.length; k++) {
                label = new Label(k, 0, columnHeadings[k]);
                wsheet.addCell(label);
            }
            OrganizationUnitGroupDao ougdao = new OrganizationUnitGroupDaoImpl();
            OrganizationUnitGroup oug = null;
            OrganizationUnitGroupAssignmentDao ougadao = new OrganizationUnitGroupAssignmentDaoImpl();
            OrganizationUnitGroupAssignment ouga = null;
            String orgUnitGroupName = null;

            for (int i = 0; i < list.size(); i++) {
                subList = (List) list.get(i);
                if (subList.isEmpty()) {
                    continue;
                }
                indicator = (String) subList.get(0);
                for (int j = 1; j < subList.size(); j++) 
                {
                    Object[] obj = (Object[]) subList.get(j);
                    orgUnitGroupName = util.getOrganizationName(obj[2].toString());
                    ouga = ougadao.getOrganizationUnitGroupAssignmentByOrgUnitIdAndParentOrgUnitGroupId(obj[3].toString(), obj[0].toString());
                    if (ouga != null) {
                        oug = ougdao.getOrganizationUnitGroup(ouga.getOrgUnitGroupId());
                        if (oug != null) {
                            orgUnitGroupName = oug.getOrgUnitGroupName();
                        }
                        //else
                        //orgUnitGroupName=util.getOrganizationName(obj[2].toString());
                    }
                    label = new Label(0, row, util.getStateName(obj[0].toString()));
                    wsheet.addCell(label);
                    label = new Label(1, row, util.getLgaByLgaCode(obj[1].toString()).getLga_name());
                    wsheet.addCell(label);
                    label = new Label(2, row, orgUnitGroupName);
                    wsheet.addCell(label);
                    label = new Label(3, row, util.getOrganizationRecordsByorgCode(obj[2].toString()).getOrgName());
                    wsheet.addCell(label);
                    label = new Label(4, row, util.getWardByorgCode(obj[3].toString()).getWard_name());
                    wsheet.addCell(label);
                    label = new Label(5, row, indicator);
                    wsheet.addCell(label);
                    label = new Label(6, row, obj[4].toString());
                    wsheet.addCell(label);
                    number = new Number(7, row, Integer.parseInt(obj[5].toString()));
                    wsheet.addCell(number);
                    label = new Label(8, row, reportPeriod);
                    wsheet.addCell(label);//
                  /*number = new Number(8, row,Integer.parseInt(obj[6].toString()));
                    wsheet.addCell(number);
                    number = new Number(9, row,Integer.parseInt(obj[7].toString()));
                    wsheet.addCell(number);*/
                    number = new Number(9, row, Integer.parseInt(obj[6].toString()));
                    wsheet.addCell(number);

                    /*If record gets to 50000, create new sheet*/
                    if (t == (49900 * (sheetCount + 1))) {
                        sheetCount++;
                        wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
                        row = 0;
                        for (int k = 0; k < columnHeadings.length; k++) {
                            label = new Label(k, 0, columnHeadings[k]);
                            wsheet.addCell(label);
                        }
                    }
                    row++;
                    t++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }

    public WritableWorkbook writeHouseholdIndicatorValuesToExcel(OutputStream os, List list) {
        WritableWorkbook wworkbook = null;
        WritableSheet wsheet = null;
        String sheetName = "Report ";
        int sheetCount = 0;
        Label label = null;
        Number number = null;

        try {
            //appUtil.createReportDirectory();
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"State", "Lga", "Organization unit group", "CBO", "Ward/Community", "Indicator", "Gender", "Current age", "No of Households"};
            int t = 0;
            int row = 1;
            List subList = null;
            String indicator = null;
            for (int k = 0; k < columnHeadings.length; k++) {
                label = new Label(k, 0, columnHeadings[k]);
                wsheet.addCell(label);
            }
            OrganizationUnitGroupDao ougdao = new OrganizationUnitGroupDaoImpl();
            OrganizationUnitGroup oug = null;
            OrganizationUnitGroupAssignmentDao ougadao = new OrganizationUnitGroupAssignmentDaoImpl();
            OrganizationUnitGroupAssignment ouga = null;
            String orgUnitGroupName = null;
            
            for (int i = 0; i < list.size(); i++) {
                subList = (List) list.get(i);
                if (subList.isEmpty()) {
                    continue;
                }
                indicator = (String) subList.get(0);
                for (int j = 1; j < subList.size(); j++) {
                    Object[] obj = (Object[]) subList.get(j);
                    orgUnitGroupName = util.getOrganizationName(obj[2].toString());
                    ouga = ougadao.getOrganizationUnitGroupAssignmentByOrgUnitIdAndParentOrgUnitGroupId(obj[3].toString(), obj[0].toString());
                    if (ouga != null) {
                        oug = ougdao.getOrganizationUnitGroup(ouga.getOrgUnitGroupId());
                        if (oug != null) {
                            orgUnitGroupName = oug.getOrgUnitGroupName();
                        }

                    }
                    label = new Label(0, row, util.getStateName(obj[0].toString()));
                    wsheet.addCell(label);
                    label = new Label(1, row, util.getLgaByLgaCode(obj[1].toString()).getLga_name());
                    wsheet.addCell(label);
                    label = new Label(2, row, orgUnitGroupName);
                    wsheet.addCell(label);
                    label = new Label(3, row, util.getOrganizationRecordsByorgCode(obj[2].toString()).getOrgName());
                    wsheet.addCell(label);
                    label = new Label(4, row, util.getWardByorgCode(obj[3].toString()).getWard_name());
                    wsheet.addCell(label);
                    label = new Label(5, row, indicator);
                    wsheet.addCell(label);
                    label = new Label(6, row, obj[4].toString());
                    wsheet.addCell(label);
                    number = new Number(7, row, Integer.parseInt(obj[5].toString()));
                    wsheet.addCell(number);
                    number = new Number(8, row, Integer.parseInt(obj[6].toString()));
                    wsheet.addCell(number);

                    /*If record gets to 50000, create new sheet*/
                    if (t == (49900 * (sheetCount + 1))) {
                        sheetCount++;
                        wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
                        row = 0;
                        for (int k = 0; k < columnHeadings.length; k++) {
                            label = new Label(k, 0, columnHeadings[k]);
                            wsheet.addCell(label);
                        }
                    }
                    row++;
                    t++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }
    /*public WritableWorkbook writeHouseholdIndicatorValuesToExcel(OutputStream os,List list)
    {
    WritableWorkbook wworkbook=null;
    WritableSheet wsheet =null;
    String sheetName="Report ";
    int sheetCount=0;
    Label label=null;
    Number number=null;
    
    try
    {
    //appUtil.createReportDirectory();
    wworkbook = Workbook.createWorkbook(os);
    wsheet = wworkbook.createSheet(sheetName+(sheetCount+1), sheetCount);
    String[] columnHeadings={"State","Lga","Organization unit group","CBO","Ward/Community","Indicator","Gender","Current age","Month","Year","Vulnerability score","Vulnerability status","No of Households"};
    int t=0;
    int row=1;
    List subList=null;
    String indicator=null;
    for(int k=0; k<columnHeadings.length; k++)
    {
    label = new Label(k, 0, columnHeadings[k]);
    wsheet.addCell(label);
    }
    OrganizationUnitGroupDao ougdao=new OrganizationUnitGroupDaoImpl();
    OrganizationUnitGroup oug=null;
    OrganizationUnitGroupAssignmentDao ougadao=new OrganizationUnitGroupAssignmentDaoImpl();
    OrganizationUnitGroupAssignment ouga=null;
    String orgUnitGroupName=null;
    String vulnerabilityStatus=null;
    int vulnerabilityScore=0;
    for(int i=0; i<list.size(); i++)
    {
    subList=(List)list.get(i);
    if(subList.isEmpty())
    continue;
    indicator=(String)subList.get(0);
    for(int j=1; j<subList.size(); j++)
    {
    Object[] obj=(Object[])subList.get(j);
    orgUnitGroupName=util.getOrganizationName(obj[2].toString());
    ouga=ougadao.getOrganizationUnitGroupAssignmentByOrgUnitIdAndParentOrgUnitGroupId(obj[2].toString(), obj[0].toString());
    if(ouga !=null)
    {
    oug=ougdao.getOrganizationUnitGroup(ouga.getOrgUnitGroupId());
    if(oug !=null)
    {
    orgUnitGroupName=oug.getOrgUnitGroupName();
    }
    //else
    //orgUnitGroupName=util.getOrganizationName(obj[2].toString());
    }
    label = new Label(0, row, util.getStateName(obj[0].toString()));
    wsheet.addCell(label);
    label = new Label(1, row, util.getLgaByLgaCode(obj[1].toString()).getLga_name());
    wsheet.addCell(label);
    label = new Label(2, row, orgUnitGroupName);
    wsheet.addCell(label);
    label = new Label(3, row, util.getOrganizationRecordsByorgCode(obj[2].toString()).getOrgName());
    wsheet.addCell(label);
    label = new Label(4, row, util.getWardByorgCode(obj[3].toString()).getWard_name());
    wsheet.addCell(label);
    label = new Label(5, row, indicator);
    wsheet.addCell(label);
    label = new Label(6, row, obj[4].toString());
    wsheet.addCell(label);
    number = new Number(7, row,Integer.parseInt(obj[5].toString()));
    wsheet.addCell(number);
    number = new Number(8, row,Integer.parseInt(obj[6].toString()));
    wsheet.addCell(number);
    number = new Number(9, row,Integer.parseInt(obj[7].toString()));
    wsheet.addCell(number);
    vulnerabilityScore=Integer.parseInt(obj[8].toString());
    number = new Number(10, row,vulnerabilityScore);
    wsheet.addCell(number);
    vulnerabilityStatus=appUtil.getVulnerabilityStatus(vulnerabilityScore);
    label = new Label(11, row, vulnerabilityStatus);
    wsheet.addCell(label);
    number = new Number(12, row,Integer.parseInt(obj[9].toString()));
    wsheet.addCell(number);
    
    //If record gets to 50000, create new sheet
    if(t==(49900*(sheetCount+1)))
    {
    sheetCount++;
    wsheet=wworkbook.createSheet(sheetName+(sheetCount+1), sheetCount);
    row=0;
    for(int k=0; k<columnHeadings.length; k++)
    {
    label = new Label(k, 0, columnHeadings[k]);
    wsheet.addCell(label);
    }
    }
    row++;
    t++;
    }
    }
    }
    catch(Exception ex)
    {
    ex.printStackTrace();
    }
    return wworkbook;
    }*/

    public WritableWorkbook writeIndicatorValuesToExcel(OutputStream os, List list, String reportPeriod) {
        WritableWorkbook wworkbook = null;
        WritableSheet wsheet = null;
        String sheetName = "Report ";
        int sheetCount = 0;
        Label label = null;
        Number number = null;
        //System.err.println("list size is "+list.size());
        try {
            //appUtil.createReportDirectory();
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            //String[] columnHeadings={"State","Lga","Organization unit group","CBO","Ward/Community","Indicator","Gender","Current age","Month","Year","No of OVC"};
            String[] columnHeadings = {"State", "Lga", "Organization unit group", "CBO", "Ward/Community", "Indicator", "Gender", "Current age", "Current age unit", "Period", "No of OVC", "Age category"};
            int t = 0;
            int row = 1;
            List subList = null;
            String indicator = null;
            for (int k = 0; k < columnHeadings.length; k++) {
                label = new Label(k, 0, columnHeadings[k]);
                wsheet.addCell(label);
            }
            OrganizationUnitGroupDao ougdao = new OrganizationUnitGroupDaoImpl();
            OrganizationUnitGroup oug = null;
            OrganizationUnitGroupAssignmentDao ougadao = new OrganizationUnitGroupAssignmentDaoImpl();
            OrganizationUnitGroupAssignment ouga = null;
            String orgUnitGroupName = null;
            String ageCategory = null;
            String currentAgeUnit = null;
            int numberOfOvc = 0;
            int currentAge = 0;
            for (int i = 0; i < list.size(); i++) {
                subList = (List) list.get(i);
                if (subList.isEmpty()) {
                    continue;
                }
                indicator = (String) subList.get(0);
                for (int j = 1; j < subList.size(); j++) {
                    currentAgeUnit = null;
                    Object[] obj = (Object[]) subList.get(j);
                    if (obj.length > 7) {
                        currentAgeUnit = (String) obj[6];
                        numberOfOvc = Integer.parseInt(obj[7].toString());
                    } else {
                        numberOfOvc = Integer.parseInt(obj[6].toString());
                    }
                    orgUnitGroupName = util.getOrganizationName(obj[2].toString());
                    ouga = ougadao.getOrganizationUnitGroupAssignmentByOrgUnitIdAndParentOrgUnitGroupId(obj[3].toString(), obj[0].toString());
                    if (ouga != null) {
                        oug = ougdao.getOrganizationUnitGroup(ouga.getOrgUnitGroupId());
                        if (oug != null) {
                            orgUnitGroupName = oug.getOrgUnitGroupName();
                        }
                        //else
                        //orgUnitGroupName=util.getOrganizationName(obj[2].toString());
                    }
                    label = new Label(0, row, util.getStateName(obj[0].toString()));
                    wsheet.addCell(label);
                    label = new Label(1, row, util.getLgaByLgaCode(obj[1].toString()).getLga_name());
                    wsheet.addCell(label);
                    label = new Label(2, row, orgUnitGroupName);
                    wsheet.addCell(label);
                    label = new Label(3, row, util.getOrganizationRecordsByorgCode(obj[2].toString()).getOrgName());
                    wsheet.addCell(label);
                    label = new Label(4, row, util.getWardByorgCode(obj[3].toString()).getWard_name());
                    wsheet.addCell(label);
                    label = new Label(5, row, indicator);
                    wsheet.addCell(label);
                    label = new Label(6, row, obj[4].toString());
                    wsheet.addCell(label);
                    currentAge = Integer.parseInt(obj[5].toString());
                    number = new Number(7, row, currentAge);
                    wsheet.addCell(number);

                    label = new Label(8, row, currentAgeUnit);
                    wsheet.addCell(label);

                    label = new Label(9, row, reportPeriod);
                    wsheet.addCell(label);
                    number = new Number(10, row, numberOfOvc);
                    wsheet.addCell(number);

                    ageCategory = appUtil.getOvcAgeCategory(currentAge,currentAgeUnit);
                    label = new Label(11, row, ageCategory);
                    wsheet.addCell(label);
                    //System.err.println("Number of Ovc is "+obj[6].toString());
                  /*If record gets to 50000, create new sheet*/
                    if (t == (49900 * (sheetCount + 1))) {
                        sheetCount++;
                        wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
                        row = 0;
                        for (int k = 0; k < columnHeadings.length; k++) {
                            label = new Label(k, 0, columnHeadings[k]);
                            wsheet.addCell(label);
                        }
                    }
                    row++;
                    t++;
                }
            }
            //wworkbook.write();
            //wworkbook.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }

    public WritableWorkbook writeAgeAnalysisValuesToExcel(OutputStream os, List list, String ageCriteriaLabel) {
        WritableWorkbook wworkbook = null;
        WritableSheet wsheet = null;
        String sheetName = "Report ";
        int sheetCount = 0;
        Label label = null;
        Number number = null;

        try {
            //appUtil.createReportDirectory();
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"State", "Lga", "CBO", "Total OVC enrolled", "No of OVC " + ageCriteriaLabel + " yrs", "No of OVC out of school", "No. of OVC " + ageCriteriaLabel + " yrs out of School"};
            int t = 0;
            int row = 1;
            List subList = null;
            SummaryStatisticsBean ssb = null;
            for (int k = 0; k < columnHeadings.length; k++) {
                label = new Label(k, 0, columnHeadings[k]);
                wsheet.addCell(label);
            }
            for (int i = 0; i < list.size(); i++) {
                ssb = (SummaryStatisticsBean) list.get(i);
                label = new Label(0, row, ssb.getState());
                wsheet.addCell(label);
                label = new Label(1, row, ssb.getLga());
                wsheet.addCell(label);
                label = new Label(2, row, ssb.getCbo());
                wsheet.addCell(label);
                number = new Number(3, row, ssb.getTotNoOfOvc());
                wsheet.addCell(number);
                number = new Number(4, row, ssb.getNoOfOvc());
                wsheet.addCell(number);
                number = new Number(5, row, ssb.getValue3());
                wsheet.addCell(number);
                number = new Number(6, row, ssb.getValue4());
                wsheet.addCell(number);

                /*If record gets to 50000, create new sheet*/
                if (t == (49900 * (sheetCount + 1))) {
                    sheetCount++;
                    wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
                    row = 0;
                    for (int k = 0; k < columnHeadings.length; k++) {
                        label = new Label(k, 0, columnHeadings[k]);
                        wsheet.addCell(label);
                    }
                }
                row++;
                t++;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }

    public void writeDatimReportToExcel(List excelList) {
        WritableWorkbook wworkbook;
        //HSSFWorkbook wb = new HSSFWorkbook();
        WritableSheet wsheet = null;
        Label label = null;
        Number number = null;

        try {
            appUtil.createReportDirectory();
            wworkbook = Workbook.createWorkbook(new File(appUtil.getReportDirectory() + appUtil.getFileSeperator() + "datim.xls"));
            wsheet = wworkbook.createSheet("First Sheet", 0);
            int t = 0;
            int row = 1;
            for (int i = 0; i < excelList.size(); i++) {
                if (t == 3) {
                    number = new Number(t, row, Integer.parseInt(excelList.get(i).toString()));
                    wsheet.addCell(number);
                    t = 0;
                    row++;
                    continue;
                } else {
                    label = new Label(t, row, excelList.get(i).toString());
                    wsheet.addCell(label);
                }
                t++;
            }
            wworkbook.write();
            wworkbook.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void writeEnrollmentRecordsToExcel(List list) {
        WritableWorkbook wworkbook;
        WritableSheet wsheet = null;
        String sheetName = "Sheet";
        int sheetCount = 0;
        Label label = null;
        Number number = null;
        Ovc ovc = null;
        HouseholdEnrollmentDao hhedao = new HouseholdEnrollmentDaoImpl();
        ChildStatusIndexDao csidao = new ChildStatusIndexDaoImpl();
        ChildStatusIndex csi = null;
        HouseholdEnrollment hhe = null;

        try {
            appUtil.createReportDirectory();
            //System.err.println(appUtil.getReportDirectory()+appUtil.getFileSeperator()+"EnrollmentRecords.xls");
            wworkbook = Workbook.createWorkbook(new File(appUtil.getReportDirectory() + appUtil.getFileSeperator() + "EnrollmentRecords.xls"));
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"State", "Lga", "CBO", "Ward/Community", "HH unique Id", "OVC Id", "Date of enrollment", "Gender", "Age", "Current Age", "Age unit", "Phone", "Eligibility", "HIV status", "Birth reg. certificate", "School status", "School name", "Current class", "Caregiver Id", "Partner", "Date modified", "Emotional health", "Social behaviour", "Food security", "Nutrition and growth", "Wellness", "Health care services", "Development and performance", "Education and work", "Abuse and exploitation", "Legal protection", "Shelter", "Care"};
            int t = 0;
            int row = 1;
            for (int i = 0; i < columnHeadings.length; i++) {
                label = new Label(i, 0, columnHeadings[i]);
                wsheet.addCell(label);
            }
            for (int i = 0; i < list.size(); i++) {
                ovc = (Ovc) list.get(i);
                hhe = hhedao.getHouseholdEnrollment(ovc.getHhUniqueId());
                if (hhe == null) {
                    hhe = new HouseholdEnrollment();
                }
                csi = csidao.getChildStatusIndex(ovc.getOvcId(), ovc.getDateEnrollment());
                if (csi == null) {
                    csi = new ChildStatusIndex();
                }
                label = new Label(0, row, util.getStateName(hhe.getStateCode()));
                wsheet.addCell(label);
                label = new Label(1, row, util.getLgaName(hhe.getLgaCode()));
                wsheet.addCell(label);
                label = new Label(2, row, util.getOrganizationName(hhe.getOrgCode()));
                wsheet.addCell(label);
                label = new Label(3, row, util.getWardName(hhe.getCommunityCode()));
                wsheet.addCell(label);
                label = new Label(4, row, ovc.getHhUniqueId());
                wsheet.addCell(label);
                label = new Label(5, row, ovc.getOvcId());
                wsheet.addCell(label);
                label = new Label(6, row, ovc.getDateEnrollment());
                wsheet.addCell(label);
                label = new Label(7, row, ovc.getGender());
                wsheet.addCell(label);
                number = new Number(8, row, ovc.getAge());
                wsheet.addCell(number);
                number = new Number(9, row, ovc.getCurrentAge());
                wsheet.addCell(number);
                label = new Label(10, row, ovc.getAgeUnit());
                wsheet.addCell(label);
                label = new Label(11, row, ovc.getPhone());
                wsheet.addCell(label);
                label = new Label(12, row, ovc.getEligibility());
                wsheet.addCell(label);
                label = new Label(13, row, ovc.getHivStatus());
                wsheet.addCell(label);
                label = new Label(14, row, ovc.getBirthCertificate());
                wsheet.addCell(label);
                label = new Label(15, row, ovc.getSchoolStatus());
                wsheet.addCell(label);
                label = new Label(16, row, ovc.getSchoolName());
                wsheet.addCell(label);
                label = new Label(17, row, ovc.getCurrentClass());
                wsheet.addCell(label);
                label = new Label(18, row, ovc.getCaregiverId());
                wsheet.addCell(label);
                label = new Label(19, row, ovc.getPartner());
                wsheet.addCell(label);
                label = new Label(20, row, ovc.getDateOfEntry());
                wsheet.addCell(label);
                number = new Number(21, row, csi.getCsiFactor1());
                wsheet.addCell(number);
                number = new Number(22, row, csi.getCsiFactor2());
                wsheet.addCell(number);
                number = new Number(23, row, csi.getCsiFactor3());
                wsheet.addCell(number);
                number = new Number(24, row, csi.getCsiFactor4());
                wsheet.addCell(number);
                number = new Number(25, row, csi.getCsiFactor5());
                wsheet.addCell(number);
                number = new Number(26, row, csi.getCsiFactor6());
                wsheet.addCell(number);
                number = new Number(27, row, csi.getCsiFactor7());
                wsheet.addCell(number);
                number = new Number(28, row, csi.getCsiFactor8());
                wsheet.addCell(number);
                number = new Number(29, row, csi.getCsiFactor9());
                wsheet.addCell(number);
                number = new Number(30, row, csi.getCsiFactor10());
                wsheet.addCell(number);
                number = new Number(31, row, csi.getCsiFactor11());
                wsheet.addCell(number);
                number = new Number(32, row, csi.getCsiFactor12());
                wsheet.addCell(number);

                /*If record gets to 50000, create new sheet*/
                if (t == (49998 * (sheetCount + 1))) {
                    sheetCount++;
                    wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);;
                }
                row++;
                t++;
            }
            wworkbook.write();
            wworkbook.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /*public void writeHouseholdEnrollmentRecordsToExcel(List list) 
    {
        WritableWorkbook wworkbook=null;
        WritableSheet wsheet = null;
        String sheetName = "Sheet";
        int sheetCount = 0;
        Label label = null;
        Number number = null;
        Ovc ovc = null;
        //HouseholdEnrollmentDao hhedao = new HouseholdEnrollmentDaoImpl();
        HouseholdVulnerabilityAssessmentDao hhvadao = new HouseholdVulnerabilityAssessmentDaoImpl();
        HouseholdEnrollment hhe = null;
        HouseholdVulnerabilityAssessment hhva = null;
        Caregiver cgiver = null;
        try {
            appUtil.createReportDirectory();
            //System.err.println(appUtil.getReportDirectory()+appUtil.getFileSeperator()+"EnrollmentRecords.xls");
            wworkbook = Workbook.createWorkbook(new File(appUtil.getReportDirectory() + appUtil.getFileSeperator() + "HouseholdEnrollmentRegister.xls"));
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"State", "Lga", "CBO", "Ward/Community", "HH unique Id","Household head name","Address", "Date of assessment", "Date of entry", "Gender", "Marital status", "Occupation", "Age", "No. of children in HH", "No. of people in HH", "partner", "Recorded by", "Assessment No.", "Education level", "Food security and nutrition", "Health", "HH headship", "HH income", "Means of livelihood", "Shelter and housing", "Baseline Vulnerability score","Baseline status","Date of current status"};
            int t = 0;
            int row = 1;
            for (int i = 0; i < columnHeadings.length; i++) {
                label = new Label(i, 0, columnHeadings[i]);
                wsheet.addCell(label);
            }
            for (int i = 0; i < list.size(); i++) {
                hhe = (HouseholdEnrollment) list.get(i);
                hhva = hhvadao.getHouseholdVulnerabilityAssessment(hhe.getHhUniqueId(), hhe.getDateOfAssessment());
                if (hhva == null) {
                    hhva = new HouseholdVulnerabilityAssessment();
                }
                label = new Label(0, row, util.getStateName(hhe.getStateCode()));
                wsheet.addCell(label);
                label = new Label(1, row, util.getLgaName(hhe.getLgaCode()));
                wsheet.addCell(label);
                label = new Label(2, row, util.getOrganizationName(hhe.getOrgCode()));
                wsheet.addCell(label);
                label = new Label(3, row, util.getWardName(hhe.getCommunityCode()));
                wsheet.addCell(label);
                label = new Label(4, row, hhe.getHhUniqueId());
                wsheet.addCell(label);
                label = new Label(5, row, hhe.getHhFirstname()+" "+hhe.getHhSurname());
                wsheet.addCell(label);
                label = new Label(6, row, hhe.getAddress());
                wsheet.addCell(label);
                label = new Label(7, row, hhe.getDateOfAssessment());
                wsheet.addCell(label);
                label = new Label(8, row, hhe.getDateOfEntry());
                wsheet.addCell(label);
                label = new Label(9, row, hhe.getHhGender());
                wsheet.addCell(label);
                label = new Label(10, row, hhe.getMaritalStatus());
                wsheet.addCell(label);
                label = new Label(11, row, hhe.getOccupation());
                wsheet.addCell(label);
                number = new Number(12, row, hhe.getHhAge());
                wsheet.addCell(number);
                number = new Number(13, row, hhe.getNoOfChildrenInhh());
                wsheet.addCell(number);
                number = new Number(14, row, hhe.getNoOfPeopleInhh());
                wsheet.addCell(number);
                label = new Label(15, row, hhe.getPartnerCode());
                wsheet.addCell(label);
                label = new Label(16, row, hhe.getRecordedBy());
                wsheet.addCell(label);
                number = new Number(17, row, hhva.getAssessmentNo());
                wsheet.addCell(number);
                number = new Number(18, row, hhe.getEducationLevel());
                wsheet.addCell(number);
                number = new Number(19, row, hhe.getFoodSecurityAndNutrition());
                wsheet.addCell(number);
                number = new Number(20, row, hhe.getHealth());
                wsheet.addCell(number);
                number = new Number(21, row, hhe.getHhHeadship());
                wsheet.addCell(number);
                number = new Number(22, row, hhe.getHhIncome());
                wsheet.addCell(number);
                number = new Number(23, row, hhe.getMeansOfLivelihood());
                wsheet.addCell(number);
                number = new Number(24, row, hhe.getShelterAndHousing());
                wsheet.addCell(number);
                number = new Number(25, row, hhe.getBaselineAssessmentScore());
                wsheet.addCell(number);
               label = new Label(26, row, hhe.getBaselineVulnerabilityStatus());
                wsheet.addCell(label);
               label = new Label(27, row, hhe.getReasonForWithdrawal());
                wsheet.addCell(label);
                label = new Label(28, row, hhe.getDateOfWithdrawal());
                wsheet.addCell(label);
                /*If record gets to 50000, create new sheet
                if (t == (49998 * (sheetCount + 1))) {
                    sheetCount++;
                    wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);;
                }
                row++;
                t++;
            }
            wworkbook.write();
            wworkbook.close();
        }
        catch (ClassCastException ex) 
        {
            System.err.println("Error: "+ex.getMessage());
        }
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
    }*/
    public WritableWorkbook writeHouseholdEnrollmentRecordsToExcel(String indicatorName,OutputStream os,List list) 
    {
        WritableWorkbook wworkbook=null;
        WritableSheet wsheet = null;
        String sheetName = "Sheet";
        int sheetCount = 0;
        int cellCount = 0;
        Label label = null;
        Number number = null;
        //Ovc ovc = null;
        //HouseholdEnrollmentDao hhedao = new HouseholdEnrollmentDaoImpl();
        HouseholdVulnerabilityAssessmentDao hhvadao = new HouseholdVulnerabilityAssessmentDaoImpl();
        HouseholdEnrollment hhe = null;
        HouseholdVulnerabilityAssessment hhva = null;
        //Caregiver cgiver = null;
        try {
            //appUtil.createReportDirectory();
            //System.err.println(appUtil.getReportDirectory()+appUtil.getFileSeperator()+"EnrollmentRecords.xls");
            wworkbook = Workbook.createWorkbook(os);
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"State", "Lga", "CBO", "Ward/Community","Indicator", "HH unique Id", "Date of assessment", "Date of entry", "Gender", "Marital status", "Occupation", "Age", "No. of children in HH", "No. of people in HH", "partner", "Recorded by", "Assessment No.", "Education level", "Food security and nutrition", "Health", "HH headship", "HH income", "Means of livelihood", "Shelter and housing", "Baseline vulnerability score","Baseline vulnerability status","Current vulnerability score","Current vulnerability status","Household followed up","Current status","Date of current status","No. of Households"};
            int t = 0;
            int row = 1;
            for (int i = 0; i < columnHeadings.length; i++) {
                label = new Label(i, 0, columnHeadings[i]);
                wsheet.addCell(label);
            }
            for (int i = 0; i < list.size(); i++) 
            {
                cellCount = 0;
                hhe = (HouseholdEnrollment) list.get(i);
                /*hhva = hhvadao.getHouseholdVulnerabilityAssessment(hhe.getHhUniqueId(), hhe.getDateOfAssessment());
                if (hhva == null) {
                    hhva = new HouseholdVulnerabilityAssessment();
                }*/
                
                //System.err.println("hhe.getBaselineVulnerabilityStatus() is "+hhe.getBaselineVulnerabilityStatus());
                label = new Label(cellCount, row, util.getStateName(hhe.getStateCode()));
                wsheet.addCell(label);
                label = new Label(++cellCount, row, util.getLgaName(hhe.getLgaCode()));
                wsheet.addCell(label);
                label = new Label(++cellCount, row, util.getOrganizationName(hhe.getOrgCode()));
                wsheet.addCell(label);
                label = new Label(++cellCount, row, util.getWardName(hhe.getCommunityCode()));
                wsheet.addCell(label);
                label = new Label(++cellCount, row, indicatorName);
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getHhUniqueId());
                wsheet.addCell(label);
                //label = new Label(++cellCount, row, hhe.getHhFirstname()+" "+hhe.getHhSurname());
                //wsheet.addCell(label);
                //label = new Label(++cellCount, row, hhe.getAddress());
                //wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getDateOfAssessment());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getDateOfEntry());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getHhGender());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getMaritalStatus());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getOccupation());
                wsheet.addCell(label);
                number = new Number(++cellCount, row, hhe.getHhAge());
                wsheet.addCell(number);
                number = new Number(++cellCount, row, hhe.getNoOfChildrenInhh());
                wsheet.addCell(number);
                number = new Number(++cellCount, row, hhe.getNoOfPeopleInhh());
                wsheet.addCell(number);
                label = new Label(++cellCount, row, hhe.getPartnerCode());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getRecordedBy());
                wsheet.addCell(label);
                /*This is to be looked into*/
                number = new Number(++cellCount, row, 1);
                wsheet.addCell(number);
                number = new Number(++cellCount, row, hhe.getEducationLevel());
                wsheet.addCell(number);
                number = new Number(++cellCount, row, hhe.getFoodSecurityAndNutrition());
                wsheet.addCell(number);
                number = new Number(++cellCount, row, hhe.getHealth());
                wsheet.addCell(number);
                number = new Number(++cellCount, row, hhe.getHhHeadship());
                wsheet.addCell(number);
                number = new Number(++cellCount, row, hhe.getHhIncome());
                wsheet.addCell(number);
                number = new Number(++cellCount, row, hhe.getMeansOfLivelihood());
                wsheet.addCell(number);
                number = new Number(++cellCount, row, hhe.getShelterAndHousing());
                wsheet.addCell(number);
                number = new Number(++cellCount, row, hhe.getBaselineAssessmentScore());
                wsheet.addCell(number);
                label = new Label(++cellCount, row, hhe.getBaselineVulnerabilityStatus());
                wsheet.addCell(label);
                number = new Number(++cellCount, row, hhe.getCurrentVulnerabilityScore());
                wsheet.addCell(number);
                label = new Label(++cellCount, row, hhe.getCurrentVulnerabilityStatus());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getFollowedUp());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getReasonForWithdrawal());
                wsheet.addCell(label);
                label = new Label(++cellCount, row, hhe.getDateOfWithdrawal());
                wsheet.addCell(label);
                number = new Number(++cellCount, row, 1);
                wsheet.addCell(number);
                
                System.err.println("Household number "+i+" added to excel");
                /*If record gets to 50000, create new sheet*/
                if (t == (49998 * (sheetCount + 1))) 
                {
                    sheetCount++;
                    wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);;
                }
                row++;
                t++;
            }
            //wworkbook.write();
            //wworkbook.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }
    /*public WritableWorkbook writeHouseholdEnrollmentRecordsToExcel(OutputStream os,List list) 
    {
        WritableWorkbook wworkbook=null;
        WritableSheet wsheet = null;
        String sheetName = "Sheet";
        int sheetCount = 0;
        Label label = null;
        Number number = null;
        Ovc ovc = null;
        HouseholdEnrollmentDao hhedao = new HouseholdEnrollmentDaoImpl();
        HouseholdVulnerabilityAssessmentDao hhvadao = new HouseholdVulnerabilityAssessmentDaoImpl();
        HouseholdEnrollment hhe = null;
        HouseholdVulnerabilityAssessment hhva = null;
        Caregiver cgiver = null;
        try {
            appUtil.createReportDirectory();
            //System.err.println(appUtil.getReportDirectory()+appUtil.getFileSeperator()+"EnrollmentRecords.xls");
            wworkbook = Workbook.createWorkbook(new File(appUtil.getReportDirectory() + appUtil.getFileSeperator() + "HouseholdEnrollmentRegister.xls"));
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"State", "Lga", "CBO", "Ward/Community", "HH unique Id", "Date of assessment", "Date of entry", "Gender", "Marital status", "Occupation", "Age", "No. of children in HH", "No. of people in HH", "partner", "Recorded by", "Assessment No.", "Education level", "Food security and nutrition", "Health", "HH headship", "HH income", "Means of livelihood", "Shelter and housing", "Vulnerability score"};
            int t = 0;
            int row = 1;
            for (int i = 0; i < columnHeadings.length; i++) {
                label = new Label(i, 0, columnHeadings[i]);
                wsheet.addCell(label);
            }
            for (int i = 0; i < list.size(); i++) {
                hhe = (HouseholdEnrollment) list.get(i);
                hhva = hhvadao.getHouseholdVulnerabilityAssessment(hhe.getHhUniqueId(), hhe.getDateOfAssessment());
                if (hhva == null) {
                    hhva = new HouseholdVulnerabilityAssessment();
                }
                label = new Label(0, row, util.getStateName(hhe.getStateCode()));
                wsheet.addCell(label);
                label = new Label(1, row, util.getLgaName(hhe.getLgaCode()));
                wsheet.addCell(label);
                label = new Label(2, row, util.getOrganizationName(hhe.getOrgCode()));
                wsheet.addCell(label);
                label = new Label(3, row, util.getWardName(hhe.getCommunityCode()));
                wsheet.addCell(label);
                label = new Label(4, row, hhe.getHhUniqueId());
                wsheet.addCell(label);
                label = new Label(5, row, hhe.getDateOfAssessment());
                wsheet.addCell(label);
                label = new Label(6, row, hhe.getDateOfEntry());
                wsheet.addCell(label);
                label = new Label(7, row, hhe.getHhGender());
                wsheet.addCell(label);
                label = new Label(8, row, hhe.getMaritalStatus());
                wsheet.addCell(label);
                label = new Label(9, row, hhe.getOccupation());
                wsheet.addCell(label);
                number = new Number(10, row, hhe.getHhAge());
                wsheet.addCell(number);
                number = new Number(11, row, hhe.getNoOfChildrenInhh());
                wsheet.addCell(number);
                number = new Number(12, row, hhe.getNoOfPeopleInhh());
                wsheet.addCell(number);
                label = new Label(13, row, hhe.getPartnerCode());
                wsheet.addCell(label);
                label = new Label(14, row, hhe.getRecordedBy());
                wsheet.addCell(label);
                number = new Number(15, row, hhva.getAssessmentNo());
                wsheet.addCell(number);
                number = new Number(16, row, hhva.getEducationLevel());
                wsheet.addCell(number);
                number = new Number(17, row, hhva.getFoodSecurityAndNutrition());
                wsheet.addCell(number);
                number = new Number(18, row, hhva.getHealth());
                wsheet.addCell(number);
                number = new Number(19, row, hhva.getHhHeadship());
                wsheet.addCell(number);
                number = new Number(20, row, hhva.getHhIncome());
                wsheet.addCell(number);
                number = new Number(21, row, hhva.getMeansOfLivelihood());
                wsheet.addCell(number);
                number = new Number(22, row, hhva.getShelterAndHousing());
                wsheet.addCell(number);
                number = new Number(23, row, hhva.getVulnerabilityScore());
                wsheet.addCell(number);
                //If record gets to 50000, create new sheet
                if (t == (49998 * (sheetCount + 1))) {
                    sheetCount++;
                    wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);;
                }
                row++;
                t++;
            }
            //wworkbook.write();
            //wworkbook.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wworkbook;
    }*/

    public void readServiceRecordsFromExcelAndSaveToDb(InputStream inputStream) throws IOException {

        Workbook w;
        try {
            AppUtility appUtil = new AppUtility();
            w = Workbook.getWorkbook(inputStream);
            //System.err.println("reading inputStream ");
            // Get the first sheet
            int count = w.getNumberOfSheets();
            String date = null;
            String cellContent = null;

            for (int a = 0; a < count; a++) {
                Sheet sheet = w.getSheet(a);
                OvcService service = null;
                OvcServiceDao serviceDao = new OvcServiceDaoImpl();

                // Loop over first 10 column and lines
                System.err.println("Sheet name is " + sheet.getName());
                for (int j = 1; j < sheet.getRows(); j++) {
                    service = new OvcService();
                    for (int i = 0; i < sheet.getColumns(); i++) {
                        Cell cell = sheet.getCell(i, j);
                        if (i == 0) {
                            cellContent = cell.getContents();
                            if (!isEmpty(cellContent)) {
                                cellContent = cellContent.trim();
                                service.setOvcId(cellContent);
                                cellContent = null;
                            } else {
                                continue;
                            }
                        } else if (i == 1) {
                            cellContent = cell.getContents();
                            if (!isEmpty(cellContent)) {
                                cellContent = cellContent.trim();
                                String[] dateArray = cellContent.split("/");
                                if (dateArray.length != 3) {
                                    continue;
                                }
                                date = "20" + dateArray[2] + "-" + dateArray[0] + "-" + dateArray[1];
                                service.setServicedate(date);
                                cellContent = null;
                                System.err.println("date is " + date);
                            } else {
                                continue;
                            }
                        } else if (i == 2) {
                            cellContent = cell.getContents();
                            if (!isEmpty(cellContent)) {

                                cellContent = cellContent.trim();
                                String[] dateArray = cellContent.split("/");
                                if (dateArray.length != 3) {
                                    continue;
                                }
                                date = "20" + dateArray[2] + "-" + dateArray[0] + "-" + dateArray[1];
                                service.setDateOfEntry(date);
                                cellContent = null;
                                System.err.println("date2 is " + date);
                            } else {
                                continue;
                            }
                        } else if (i == 3) {
                            cellContent = cell.getContents();
                            if (!isEmpty(cellContent)) {
                                cellContent = cellContent.trim();
                            }
                            service.setserviceAccessed1(cellContent);
                            cellContent = null;
                        } else if (i == 4) {
                            cellContent = cell.getContents();
                            if (!isEmpty(cellContent)) {
                                cellContent = cellContent.trim();
                            }
                            service.setserviceAccessed2(cellContent);
                            cellContent = null;
                        } else if (i == 5) {
                            cellContent = cell.getContents();
                            if (!isEmpty(cellContent)) {
                                cellContent = cellContent.trim();
                            }
                            service.setserviceAccessed3(cellContent);
                            cellContent = null;
                        } else if (i == 6) {
                            cellContent = cell.getContents();
                            if (!isEmpty(cellContent)) {
                                cellContent = cellContent.trim();
                            }
                            service.setserviceAccessed4(cellContent);
                            cellContent = null;
                        } else if (i == 7) {
                            cellContent = cell.getContents();
                            if (!isEmpty(cellContent)) {
                                cellContent = cellContent.trim();
                            }
                            service.setserviceAccessed5(cellContent);
                            cellContent = null;
                        } else if (i == 8) {
                            cellContent = cell.getContents();
                            if (!isEmpty(cellContent)) {
                                cellContent = cellContent.trim();
                            }
                            service.setserviceAccessed6(cellContent);
                            cellContent = null;
                        } else if (i == 9) {
                            cellContent = cell.getContents();
                            if (!isEmpty(cellContent)) {
                                cellContent = cellContent.trim();
                            }
                            service.setserviceAccessed7(cellContent);
                            cellContent = null;
                        } else if (i == 10) {
                            cellContent = cell.getContents();
                            if (!isEmpty(cellContent)) {
                                cellContent = cellContent.trim();
                            }
                            service.setServicesReferred(cellContent);
                            cellContent = null;
                        } else if (i == 11) {
                            cellContent = cell.getContents();
                            if (!isEmpty(cellContent)) {
                                cellContent = cellContent.trim();
                                service.setWeight(Double.parseDouble(cellContent));
                            }
                            cellContent = null;
                        } else if (i == 12) {
                            cellContent = cell.getContents();
                            if (!isEmpty(cellContent)) {
                                cellContent = cellContent.trim();
                                service.setHeight(Double.parseDouble(cellContent));
                            }
                            cellContent = null;
                        } else if (i == 13) {
                            cellContent = cell.getContents();
                            if (!isEmpty(cellContent)) {
                                cellContent = cellContent.trim();
                                service.setSurveyNumber(Integer.parseInt(cellContent));
                            }
                            cellContent = null;
                        } else if (i == 14) {
                            cellContent = cell.getContents();
                            if (!isEmpty(cellContent)) {
                                cellContent = cellContent.trim();
                            }
                            service.setProviderName(cellContent);
                            cellContent = null;
                        } else if (i == 15) {
                            cellContent = cell.getContents();
                            if (!isEmpty(cellContent)) {
                                cellContent = cellContent.trim();
                                service.setNumberOfServices(Integer.parseInt(cellContent));
                            }
                            cellContent = null;
                        }
                    }
                    try {
                        serviceDao.saveOvcService(service, false, false);
                        System.err.println("service record with ovcId " + service.getOvcId() + " saved");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (BiffException be) {
            be.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void writeIndicatorValuesToExcel(List list) {
        WritableWorkbook wworkbook;
        WritableSheet wsheet = null;
        String sheetName = "Sheet";
        int sheetCount = 0;
        Label label = null;
        Number number = null;

        try {
            appUtil.createReportDirectory();
            wworkbook = Workbook.createWorkbook(new File(appUtil.getReportDirectory() + appUtil.getFileSeperator() + "IndicatorValues.xls"));
            wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
            String[] columnHeadings = {"State", "Lga", "CBO", "Ward/Community", "Indicator", "Gender", "Current age", "Month", "Year", "No of OVC"};
            int t = 0;
            int row = 1;
            List subList = null;
            String indicator = null;
            for (int k = 0; k < columnHeadings.length; k++) {
                label = new Label(k, 0, columnHeadings[k]);
                wsheet.addCell(label);
            }
            for (int i = 0; i < list.size(); i++) {
                subList = (List) list.get(i);
                indicator = (String) subList.get(0);
                for (int j = 1; j < subList.size(); j++) {
                    Object[] obj = (Object[]) subList.get(j);
                    label = new Label(0, row, util.getStateName(obj[0].toString()));
                    wsheet.addCell(label);
                    label = new Label(1, row, util.getLgaName(obj[1].toString()));
                    wsheet.addCell(label);
                    label = new Label(2, row, util.getOrganizationName(obj[2].toString()));
                    wsheet.addCell(label);
                    label = new Label(3, row, util.getWardName(obj[3].toString()));
                    wsheet.addCell(label);
                    label = new Label(4, row, indicator);
                    wsheet.addCell(label);
                    label = new Label(5, row, obj[4].toString());
                    wsheet.addCell(label);
                    number = new Number(6, row, Integer.parseInt(obj[5].toString()));
                    wsheet.addCell(number);
                    number = new Number(7, row, Integer.parseInt(obj[6].toString()));
                    wsheet.addCell(number);
                    number = new Number(8, row, Integer.parseInt(obj[7].toString()));
                    wsheet.addCell(number);
                    number = new Number(9, row, Integer.parseInt(obj[8].toString()));
                    wsheet.addCell(number);

                    /*If record gets to 50000, create new sheet*/
                    if (t == (49900 * (sheetCount + 1))) {
                        sheetCount++;
                        wsheet = wworkbook.createSheet(sheetName + (sheetCount + 1), sheetCount);
                        row = 0;
                        for (int k = 0; k < columnHeadings.length; k++) {
                            label = new Label(k, 0, columnHeadings[k]);
                            wsheet.addCell(label);
                        }
                    }
                    row++;
                    t++;
                }
            }
            wworkbook.write();
            wworkbook.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean isEmpty(String value) {
        if (value != null && !value.equalsIgnoreCase("") && !value.equalsIgnoreCase(" ") && !value.equalsIgnoreCase("  ")) {
            return false;
        }
        return true;
    }
    private String getBusinessQuarter(int mth)
    {
        String quarter=null;
        if(mth>9 && mth <13)
        quarter="Quarter1";
        else if(mth>0 && mth <4)
        quarter="Quarter2";
        else if(mth>3 && mth <7)
        quarter="Quarter3";
        else if(mth>6 && mth <10)
        quarter="Quarter4";
        
        return quarter;
    }
}
