/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.chart.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.business.Wards;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.kidmap.report.SummaryStatisticsReportGenerator;
import com.fhi.kidmap.report.SummaryStatisticsReportManager;
import com.fhi.nomis.nomisutils.DateManager;
import com.nomis.business.OrganizationUnit;
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
public class ChartGeneratorAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    
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
        ChartGeneratorForm cgf=(ChartGeneratorForm)form;
        SummaryStatisticsReportManager srm=new SummaryStatisticsReportManager();
        ReportUtility rutil=new ReportUtility();
        AppUtility appUtil=new AppUtility();
        DaoUtil util=new DaoUtil();
        DateManager dm=new DateManager();
        PartnersDao pdao=new PartnersDaoImpl();
        HttpSession session=request.getSession();

        String stateCode=cgf.getStateCode();
        String lgaCode=cgf.getLgaCode();
        String orgCode=cgf.getOrgCode();
        String wardCode=cgf.getWardCode();
        String partnerCode=cgf.getPartnerCode();
        String chartTitle=cgf.getChartTitle();
        int startAge=cgf.getStartAge();
        int endAge=cgf.getEndAge();
        int startMth=cgf.getStartMth();
        int startYear=cgf.getStartYear();
        int endMth=cgf.getEndMth();
        int endYear=cgf.getEndYear();
        String[] selectedIndicators=cgf.getIndicatorIndexes();
        String[] selectedOrgUnits=cgf.getSelectedOrganizationUnits();
        List organizationList=new ArrayList();
        List stateList=util.getStateListFromHhe();
        OrganizationUnit ou=null;
        cgf.setStateList(stateList);
        List emptyList=new ArrayList();
        List ageList=new ArrayList();
        List list=srm.getSummaryStatisticsIndicators();
        List list2=null;
        List sumstatList=new ArrayList();
        List partnerList=pdao.getAllPartners();
        session.setAttribute("partnerList", partnerList);
        for(int i=0; i<list.size(); i++)
        {
            list2=new ArrayList();
            list2.add(i);
            list2.add(list.get(i));
            sumstatList.add(list2);
        }
        request.setAttribute("sunstatIndictors", sumstatList);

        String requiredAction=cgf.getActionName();
        String id=request.getParameter("id");
        if(id !=null)
        requiredAction=id;
        System.err.println("requiredAction is "+requiredAction);
        if(requiredAction==null)
        {
            cgf.reset(mapping, request);
            ageList.clear();
            organizationList=getStateOrganizationList();
            ageList=appUtil.getAgeList(0,100);
            List listOfMonths=dm.generateMonths();//(1);
            List listOfYears=dm.generatDate(2004);
            session.setAttribute("organizationList", organizationList);
            session.setAttribute("generatedStartDates", listOfYears);
            session.setAttribute("generatedMonths", listOfMonths);
            session.setAttribute("endMonths", listOfMonths);
            session.setAttribute("endYears", listOfYears);
            session.setAttribute("ageList", ageList);
            List endAgeList=new ArrayList();
            endAgeList.addAll(ageList);
            //endAgeList.add("18+");
            session.setAttribute("endAgeList", endAgeList);
            cgf.setOrgList(emptyList);
            cgf.setWardList(emptyList);
        }
        else if(requiredAction.equals("endAge"))
        {
             List endAgeList=appUtil.getAgeList(startAge, 100);
             session.setAttribute("endAgeList", endAgeList);
        }
        else if(requiredAction.equals("endDates"))
        {
            startMth=1;
            List listOfMonths=dm.generateMonths();//(appUtil.getMonthAsNumber(startMth+""));
            List listOfYears=dm.generatDate(Integer.parseInt(startYear+""));
            session.setAttribute("endMonths", listOfMonths);
            session.setAttribute("endYears", listOfYears);
        }
        else if(requiredAction.equalsIgnoreCase("lgaList"))
        {
            List lgaList=util.getLgaListFromHhe(stateCode);
            cgf.setLgaList(lgaList);
            Lgas lga=null;
            if(stateCode==null || stateCode.equalsIgnoreCase("All"))
            organizationList=getStateOrganizationList();
            else
            organizationList=getLgaOrganizationList(stateCode);
            session.setAttribute("organizationList", organizationList);
            cgf.setOrgList(emptyList);
            cgf.setWardList(emptyList);
        }
        else if(requiredAction.equalsIgnoreCase("orgList"))
        {
            List orgList=util.getOrganizationRecordsFromHhe(lgaCode);
            OrganizationRecords or=null;
            cgf.setOrgList(orgList);
            cgf.setWardList(emptyList);
            if(lgaCode==null || lgaCode.equalsIgnoreCase("All"))
            organizationList=getLgaOrganizationList(stateCode);
            else
            organizationList=getCBOOrganizationList(lgaCode);
            session.setAttribute("organizationList", organizationList);
        }
        else if(requiredAction.equalsIgnoreCase("wardList"))
        {
            List wardList=util.getWardsFromHhe(orgCode);
            cgf.setWardList(wardList);
            if(orgCode==null || orgCode.equalsIgnoreCase("All"))
            organizationList=getCBOOrganizationList(lgaCode);
            else
            organizationList=getWardOrganizationList(orgCode);
            
            session.setAttribute("organizationList", organizationList);
        }
        else if(requiredAction.equalsIgnoreCase("generateChart"))
        {
            List mainTotalList=new ArrayList();
            List totalList=new ArrayList();
            double largestValue=0;
            OvcDao dao=new OvcDaoImpl();
            String[] indictorArray={"1"};

            if(selectedOrgUnits !=null)
            {
                String selectedState=stateCode;
                String selectedLga=lgaCode;
                String selectedOrgCode=orgCode;
                String selectedWardCode=wardCode;
                List valueList=new ArrayList();
                String selectedOrgUnit=null;

                //System.err.println("selectedOrgUnits is "+selectedOrgUnits.length);
                for(int j=0; j<selectedOrgUnits.length; j++)
                {
                    if(stateCode.equalsIgnoreCase("All"))
                    {
                        selectedState=selectedOrgUnits[j];
                        selectedOrgUnit=util.getStateName(selectedState);
                    }
                    else if(lgaCode.equalsIgnoreCase("All"))
                    {
                        selectedLga=selectedOrgUnits[j];
                        selectedOrgUnit=util.getLgaName(selectedLga);
                    }
                    else if(orgCode.equalsIgnoreCase("All"))
                    {
                        selectedOrgCode=selectedOrgUnits[j];
                        selectedOrgUnit=util.getOrganizationName(selectedOrgCode);
                    }
                    else
                    {
                        selectedWardCode=selectedOrgUnits[j];
                        selectedOrgUnit=util.getWardName(selectedWardCode);
                    }
                    //System.err.println("org unit is "+selectedState+" "+selectedLga+" "+selectedOrgCode+" "+selectedWardCode);
                    String[] param={selectedState,selectedLga,selectedOrgCode,"All","All","stateName","lgaName",startMth+"",startYear+"",endMth+"",endYear+"","All","All","All","All",startAge+"",endAge+"","cboName",partnerCode,selectedWardCode,"wardName"};
                    SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
                    try
                    {
                        List resultList=new ArrayList();
                        list=new ArrayList();
                        list=ssrg.getOvcEnrolledSummStatistics("All",param,selectedIndicators);
                        mainTotalList=ssrg.getOvcEnrolledSummStatistics("All",param,indictorArray);
                        if(mainTotalList !=null && !mainTotalList.isEmpty())
                        {
                            totalList=(List)mainTotalList.get(0);
                            //System.err.println("totalList is "+totalList);
                            double totEnrolled=Double.parseDouble(totalList.get(totalList.size()-2).toString());
                            if(totEnrolled > largestValue)
                            largestValue=totEnrolled;
                        }
                        double indicatorTotal=dao.getNumberOfOvcEnrolledByOrgUnit(selectedOrgUnits[j]);
                        String indicator=null;
                        for(int i=0; i<list.size(); i++)
                        {
                            resultList=(List)list.get(i);
                            //System.err.println("resultList is "+resultList);
                            double value=Double.parseDouble(resultList.get(resultList.size()-2).toString());
                            if(indicatorTotal==0)
                            indicatorTotal=indicatorTotal+1;
                            //double dvalue=(value/indicatorTotal);
                            //System.err.println(selectedOrgUnit+": value is "+value+" and indicatorTotal is "+indicatorTotal+" and dvalue is "+dvalue);
                            indicator=resultList.get(0).toString();
                            valueList.add(value);
                            valueList.add(appUtil.removeTags(indicator));
                            valueList.add(selectedOrgUnit);
                        }
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
                if(chartTitle==null)
                chartTitle="Chart title";
                session.setAttribute("largestValue", largestValue);
                session.setAttribute("chartValueList", valueList);
                session.setAttribute("chartTitle", chartTitle);
                return mapping.findForward("chartview");
            }
        }
        /*else if(requiredAction.equalsIgnoreCase("generateChart"))
        {
            List mainList=new ArrayList();
            List mainTotalList=new ArrayList();
            List subTotalList=new ArrayList();
            OvcDao dao=new OvcDaoImpl();
            
            if(selectedOrgUnits !=null)
            {
                String selectedState=stateCode;
                String selectedLga=lgaCode;
                String selectedOrgCode=orgCode;
                String selectedWardCode=wardCode;
                List valueList=new ArrayList();
                String selectedOrgUnit=null;
                
                //System.err.println("selectedOrgUnits is "+selectedOrgUnits.length);
                for(int j=0; j<selectedOrgUnits.length; j++)
                {
                    if(stateCode.equalsIgnoreCase("All"))
                    {
                        selectedState=selectedOrgUnits[j];
                        selectedOrgUnit=util.getStateName(selectedState);
                    }
                    else if(lgaCode.equalsIgnoreCase("All"))
                    {
                        selectedLga=selectedOrgUnits[j];
                        selectedOrgUnit=util.getLgaName(selectedLga);
                    }
                    else if(orgCode.equalsIgnoreCase("All"))
                    {
                        selectedOrgCode=selectedOrgUnits[j];
                        selectedOrgUnit=util.getOrganizationName(selectedOrgCode);
                    }
                    else
                    {
                        selectedWardCode=selectedOrgUnits[j];
                        selectedOrgUnit=util.getWardName(selectedWardCode);
                    }
                    //System.err.println("org unit is "+selectedState+" "+selectedLga+" "+selectedOrgCode+" "+selectedWardCode);
                    String[] param={selectedState,selectedLga,selectedOrgCode,"All","All","stateName","lgaName",startMth+"",startYear+"",endMth+"",endYear+"","All","All","All","All",startAge+"",endAge+"","cboName",partnerCode,selectedWardCode,"wardName"};
                    SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
                    try
                    {
                        List resultList=new ArrayList();
                        list=new ArrayList();
                        list=ssrg.getOvcEnrolledSummStatistics("All",param,selectedIndicators);
                        subTotalList.addAll(getIndicatorSubtotalValueList(list));
                        //orgUnitList.add(selectedOrgUnit);
                        
                        //list.add(selectedOrgUnit);
                        //mainList.add(list);
                        //int value=0;
                        //int t=0;
                        //String indicator="Indicator name";
                        for(int i=0; i<list.size(); i++)
                        {
                            resultList=(List)list.get(i);
                            resultList.add(selectedOrgUnit);
                            mainList.add(resultList);
                        } 
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
                List resultList=null;
                int count=0;
                String indicator=null;
                
                mainTotalList=getIndicatorTotalValues(subTotalList, selectedOrgUnits.length,selectedIndicators.length);
                for(int i=0; i<mainList.size(); i++)
                {
                    resultList=(List)mainList.get(i);
                    selectedOrgUnit=resultList.get(resultList.size()-1).toString();
                    double indicatorTotal=Double.parseDouble(mainTotalList.get(count).toString());
                    double value=Double.parseDouble(resultList.get(resultList.size()-3).toString());
                    if(indicatorTotal==0)
                    indicatorTotal=indicatorTotal+1;
                    double dvalue=(value/indicatorTotal);
                    System.err.println(selectedOrgUnit+": value is "+value+" and indicatorTotal is "+indicatorTotal+" and dvalue is "+dvalue);
                    indicator=resultList.get(0).toString();
                    
                    valueList.add(dvalue);
                    valueList.add(appUtil.removeTags(indicator));
                    valueList.add(selectedOrgUnit);
                    count++;
                    if(count==selectedIndicators.length)
                    count=0;    
                    //System.err.println("indicator at "+i+" is "+indicator);
                }
                if(chartTitle==null)
                chartTitle="Chart title";
                session.setAttribute("chartValueList", valueList);
                session.setAttribute("chartTitle", chartTitle);
                return mapping.findForward("chartview");
            }
        }*/
        return mapping.findForward(SUCCESS);
    }
    /*The results for the query is returned in a list for all the indicators per orgunit. This method iterates 
     * over the main list to retrieve the sub lists for each indicator result*/
    private List getIndicatorSubtotalValueList(List list)
    {
        List valueList=new ArrayList();
        List resultList=null;
        for(int i=0; i<list.size(); i++)
        {
            resultList=(List)list.get(i);
            /*The sub list has four elements - the indicator, the male value, the female value, the total value 
             * and the indicator index. Only the total value is retrieved here*/
            valueList.add(resultList.get(resultList.size()-2).toString());
        }
        return valueList;
    }
    /*This method adds up the values for each indicator and return the total values for each indicator packaged in a list object*/
    private List getIndicatorTotalValues(List subTotalList, int numberOfOrgUnits,int numberOfIndicators)
    {
        /*The total number of items in the list is (numberOfOrgUnits * numberOfIndicators)*/
        List valueList=new ArrayList();
        int indicatorTotal=0;
        System.err.println("list.size() is "+subTotalList.size());
        /*iterate over the list numberOfIndicators times*/
        for(int i=0; i<numberOfIndicators; i++)
        {
            int t=i;
            indicatorTotal=0;
            /*calculate the total value per each indicator contained in the list*/
            for(int j=0; j<numberOfOrgUnits; j++)
            {
                System.err.println("t is "+t);
                indicatorTotal+=Integer.parseInt(subTotalList.get(t).toString());
                t+=numberOfIndicators;
            }
            System.err.println("indicatorTotal is "+indicatorTotal);
            valueList.add(indicatorTotal);
        }
        return valueList;
    }
    private List getStateOrganizationList()
    {
        DaoUtil util=new DaoUtil();
        List stateList=util.getStateListFromHhe();
        List organizationList=new ArrayList();
        States state=null;
        OrganizationUnit ou=null;
        for(int i=0; i<stateList.size(); i++)
        {
            state=(States)stateList.get(i);
            ou=new OrganizationUnit();
            ou.setOrgunitid(state.getState_code());
            ou.setOrgunitname(state.getName());
            organizationList.add(ou);
        }
        return organizationList;
    }
    private List getLgaOrganizationList(String stateCode)
    {
        DaoUtil util=new DaoUtil();
        List lgaList=util.getLgaListFromHhe(stateCode);
        List organizationList=new ArrayList();
        OrganizationUnit ou=null;
        Lgas lga=null;
        for(int i=0; i<lgaList.size(); i++)
        {
            lga=(Lgas)lgaList.get(i);
            ou=new OrganizationUnit();
            ou.setOrgunitid(lga.getLga_code());
            ou.setOrgunitname(lga.getLga_name());
            organizationList.add(ou);
        }
        return organizationList;
    }
    private List getCBOOrganizationList(String lgaCode)
    {
        DaoUtil util=new DaoUtil();
        List orgList=util.getOrganizationRecordsFromHhe(lgaCode);
        OrganizationRecords or=null;
        OrganizationUnit ou=null;
        List organizationList=new ArrayList();
        for(int i=0; i<orgList.size(); i++)
        {
            or=(OrganizationRecords)orgList.get(i);
            ou=new OrganizationUnit();
            ou.setOrgunitid(or.getOrgCode());
            ou.setOrgunitname(or.getOrgName());
            organizationList.add(ou);
        }
        return organizationList;
    }
    private List getWardOrganizationList(String orgCode)
    {
        DaoUtil util=new DaoUtil();
        List wardList=util.getWardsFromHhe(orgCode);
        List organizationList=new ArrayList();
        Wards ward=null;
        OrganizationUnit ou=null;
        for(int i=0; i<wardList.size(); i++)
        {
            ward=(Wards)wardList.get(i);
            ou=new OrganizationUnit();
            ou.setOrgunitid(ward.getWard_code());
            ou.setOrgunitname(ward.getWard_name());
            organizationList.add(ou);
        }
        return organizationList;
    }
    private void saveChartToFavorite()
    {

    }
}
