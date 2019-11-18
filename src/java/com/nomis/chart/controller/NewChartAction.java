/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.chart.controller;

import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.ChartDao;
import com.fhi.kidmap.dao.ChartDaoImpl;
import com.fhi.kidmap.report.IndicatorDictionary;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DateManager;
import com.nomis.chart.Chart;
import com.nomis.chart.ChartContentGenerator;
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
public class NewChartAction extends org.apache.struts.action.Action {

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
        NewChartForm cform=(NewChartForm)form;
        
        ChartContentGenerator ccg=new ChartContentGenerator();
        AppUtility appUtil=new AppUtility();
        
        HttpSession session=request.getSession();
        AccessManager acm=appUtil.getAccessManager();
        acm.setStateListForReports(session);
        User user=appUtil.getUser(session);
        String requiredAction=cform.getActionName();
        String stateCode=cform.getState();
        String lgaCode=cform.getLga();
        String cboCode=cform.getCboCode();
        String communityCode=cform.getCommunityCode();
           
        
        String chartType=cform.getChartType();
        String periodType=cform.getPeriodType();
               
        boolean labelVisible=cform.isLabelVisible();
        String selectedSeriesOption=cform.getSeries();
        String selectedCategoryOption=cform.getCategory();
        String selectedFilterOption=cform.getFilter();
        String selectedIndicatorId=cform.getSingleIndicatorId();
        String singleOrgUnitId=cform.getSingleOrgUnitId();
        String[] selectedOrganizationUnit=cform.getSelectedOrganizationUnit();
        String[] indicatorIds=cform.getIndicatorIds();
        String[] selectedPeriods=cform.getSelectedMonths();
        String selectedSinglePeriod=cform.getSelectedSinglePeriod();
        if(selectedFilterOption !=null && selectedFilterOption.equalsIgnoreCase("Organization unit"))
        {
            selectedOrganizationUnit=new String[1];
            selectedOrganizationUnit[0]=singleOrgUnitId;
        }
        if(selectedFilterOption !=null && selectedFilterOption.equalsIgnoreCase("Indicators"))
        {
            indicatorIds=new String[1];
            indicatorIds[0]=selectedIndicatorId;
        }
        if(selectedFilterOption !=null && selectedFilterOption.equalsIgnoreCase("Period"))
        {
            selectedPeriods=new String[1];
            selectedPeriods[0]=selectedSinglePeriod;
        }
        
        String msg="";
        
        List list=getChartIndicators();
        request.setAttribute("chartIndicatorList", list);
        
        List emptyList=new ArrayList();
        List orgunitList=new ArrayList();
        String sessionname="all";
        
        if(stateCode ==null || stateCode.equalsIgnoreCase("All"))
        {
            sessionname="mstateListForReports";
            orgunitList=acm.getStateListForReports(session);
            request.setAttribute(sessionname, orgunitList);
            
        }
        else if(stateCode !=null && !stateCode.equalsIgnoreCase("All"))
        {
            if(lgaCode !=null && !lgaCode.equalsIgnoreCase("All"))
            {
                if(cboCode !=null && !cboCode.equalsIgnoreCase("All"))
                {
                    if(user !=null)
                    orgunitList=acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,cboCode);
                    sessionname="mchartCommunityList";
                    request.setAttribute(sessionname, orgunitList);
                }
                else
                {
                    if(user !=null)
                    orgunitList=acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);//eiddao.getDistinctFacilityListFromEiddata(lgaCode);
                    sessionname="mchartcboList";
                    request.setAttribute(sessionname, orgunitList);
                }
            }
            else
            {
                if(user !=null)
                orgunitList=acm.getListOfLgasAssignedToUser(user,stateCode);
                sessionname="mchartLgaList";
                request.setAttribute(sessionname, orgunitList);       
            }
        }
        session.setAttribute("organizationUnitList", orgunitList);
        request.setAttribute("msg", msg);
        setChartAttributes(session,selectedSeriesOption,selectedCategoryOption);
        generatePeriod(session,periodType);
        if(requiredAction==null || requiredAction.equalsIgnoreCase("chartcriteriaPage"))
        {            
            session.setAttribute("chartLgaList", emptyList);
            session.setAttribute("chartcboList", emptyList);
            session.setAttribute("chartCommunityList", emptyList);
            cform.reset(mapping, request);
            return mapping.findForward("chartcriteriaPage");
        }
        else if(requiredAction.equalsIgnoreCase("lga"))
        {
            if(user !=null)
            orgunitList =acm.getListOfLgasAssignedToUser(user,stateCode);
            session.setAttribute("chartLgaList", orgunitList);
            session.setAttribute("chartcboList", emptyList);
            session.setAttribute("chartCommunityList", emptyList);
            return mapping.findForward("chartcriteriaPage");
        }//chartcboList
        else if(requiredAction.equalsIgnoreCase("cbo"))
        {
            List lgaList=new ArrayList();
            if(user !=null)
            lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
            session.setAttribute("chartLgaList", lgaList);
            session.setAttribute("chartCommunityList", emptyList);
            if(user !=null)
            orgunitList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("chartcboList", orgunitList);
            //if no lgaCode is selected, then do not change the list of organizations displayed
            
            return mapping.findForward("chartcriteriaPage");
        }
        else if(requiredAction.equalsIgnoreCase("communityList"))
        {
            if(user !=null)
            orgunitList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,cboCode);
            session.setAttribute("chartCommunityList", orgunitList);
            
            return mapping.findForward("chartcriteriaPage");
        }
        else if(requiredAction.equalsIgnoreCase("categoryList"))
        {
            setChartAttributes(session,selectedSeriesOption,selectedCategoryOption);
            return mapping.findForward("chartcriteriaPage");
        }
        else if(requiredAction.equalsIgnoreCase("filterList"))
        {
            setChartAttributes(session,selectedSeriesOption,selectedCategoryOption);
            return mapping.findForward("chartcriteriaPage");
        }
        else if(requiredAction.equalsIgnoreCase("chartType"))
        {
            return mapping.findForward("chartcriteriaPage");
        }
        else if(requiredAction.equalsIgnoreCase("chartView"))
        {
            return mapping.findForward("chartpage");
        }
        else if(requiredAction.equalsIgnoreCase("periodList"))
        {
            return mapping.findForward("chartcriteriaPage");
        }
        else if(requiredAction.equalsIgnoreCase("chart"))
        {
            
            //setChartTitle(session,selectedOrganizationUnit,indicatorIds,selectedSeriesOption);
            //startDate=startYr+"-"+startMth+"-"+startDay;
            //endDate=endYr+"-"+endMth+"-"+endDay;
            String parentOrgUnit="All";
            int orgUnitLevel=2;
            if(!stateCode.equalsIgnoreCase("All"))
            {
                parentOrgUnit=stateCode;
                orgUnitLevel=3;
                if(!lgaCode.equalsIgnoreCase("All"))
                {
                    parentOrgUnit=lgaCode;
                    orgUnitLevel=4;
                    if(!cboCode.equalsIgnoreCase("All"))
                    {
                        parentOrgUnit=cboCode;
                        orgUnitLevel=5;
                    }
                }
            }
            
            
            if(chartType ==null)
            chartType="barchart";
            Chart chart=new Chart();
            chart.setStateCode(stateCode);
            chart.setLgaCode(lgaCode);
            chart.setCboCode(cboCode);
            chart.setCommunityCode(communityCode);
            chart.setChartId(null);
            chart.setChartName(null);
            chart.setChartType(chartType);
            chart.setCategoryOption(selectedCategoryOption);
            chart.setDateCreated(appUtil.getCurrentDate());
            chart.setFilter(selectedFilterOption);
            chart.setLabelVisible(labelVisible+"");
            chart.setLastModifiedDate(appUtil.getCurrentDate());
            chart.setOrgUnitCode(parentOrgUnit);
            chart.setOrgUnitLevel(orgUnitLevel);
            chart.setPeriodType(periodType);
            chart.setSelectedIndicators(appUtil.concatStr(indicatorIds, null));
            chart.setSelectedOrgUnits(appUtil.concatStr(selectedOrganizationUnit, null));
            chart.setSelectedPeriods(appUtil.concatStr(selectedPeriods,null));
            chart.setSeries(selectedSeriesOption);
            chart.setStartDate(appUtil.getCurrentDate());
            chart.setEndDate(appUtil.getCurrentDate());
            ccg.getChartDataset(chart, session);
            //ccg.getChartDataset(indicatorIds,selectedOrganizationUnit,selectedPeriods,periodType,selectedSeriesOption,stateCode,lgaCode,cboCode,session);
            //ccg.getChartDataset(indicatorIds,selectedOrganizationUnit,selectedPeriods,periodType,selectedSeriesOption,selectedCategoryOption,selectedFilterOption,session);
            String verticalLabel="Percentage";
            String chartTitle=ccg.setChartTitle(chart);
            session.setAttribute("chartTitle", chartTitle);
            session.setAttribute("chartType", chartType);
            String reasonTitle=" ";
            String resultTitle=" ";
            
            resultTitle+="Result: ";//+result;
            request.setAttribute("reasonTitle", reasonTitle);
            request.setAttribute("resultTitle", resultTitle);
            session.setAttribute("labelVisible", labelVisible);
            session.setAttribute("verticalLabel",verticalLabel);
            
            
            
            session.setAttribute("chart", chart);
            cform.reset(mapping, request);
            return mapping.findForward("chartpage");
        }
        else if(requiredAction.equalsIgnoreCase("saveChart"))
        {
            Chart chart=(Chart)session.getAttribute("chart");
            if(chart !=null)
            {
                String chartName=cform.getChartName();
                if(chartName !=null)
                {
                    //System.err.println("About to save Chart with name "+chartName);
                    chart.setChartName(chartName);
                    ChartDao cdao=new ChartDaoImpl();      
                    cdao.saveChart(chart);
                }
            }
            return mapping.findForward("chartpage");
        }
        return mapping.findForward("chartcriteriaPage");
    }
    public List getChartIndicators()
    {
        IndicatorDictionary ind=new IndicatorDictionary();
        List indicatorList=new ArrayList();
        indicatorList.add(ind.getIndicatorForProportionOfHivPosOvcCurrentlyEnrolled());
        indicatorList.add(ind.getIndicatorForProportionOfHivNegOvcCurrentlyEnrolled());
        indicatorList.add(ind.getIndicatorForProportionOfHivUnkOvcCurrentlyEnrolled());
        //indicatorList.add(ind.getIndicatorForNumberOfNewOvcEnrolled());
        //indicatorList.add(ind.getIndicatorForNumberOfOvcCurrentlyEnrolled());
        //indicatorList.add(ind.getIndicatorForNumberOfOvcEverEnrolled());
        return indicatorList;
    }
    public void generatePeriod(HttpSession session,String periodType)
    {
        if(periodType==null)
        periodType="monthly";
        DateManager ndate=new DateManager();
        List periodList=ndate.getPeriodList(periodType);
        session.setAttribute("chartPeriodList", periodList);
    }
    private void setChartAttributes(HttpSession session,String selectedSeriesOption,String selectedCategoryOption)
    {
        ChartContentGenerator ccg=new ChartContentGenerator();
        List seriesList=ccg.getSeriesFilterAndCategoryOptions();
        List categoryList=ccg.getCategoryOptions(selectedSeriesOption);
        List filterList=ccg.getFilterOptions(selectedSeriesOption,selectedCategoryOption);
        session.setAttribute("seriesList", seriesList);
        session.setAttribute("filterList", filterList);
        session.setAttribute("chartCategoryList", categoryList);
        if(filterList !=null && !filterList.isEmpty())
        {
            String filter=filterList.get(0).toString();
            
            System.err.println("filter is "+filter);
            if(filter !=null)
            {
                if(filter.equalsIgnoreCase("Organization unit"))
                {
                    session.removeAttribute("multipleSelectionChartOrgUnitList");
                    session.removeAttribute("singleSelectionChartIndicatorList");
                    session.removeAttribute("singleSelectionChartPeriodList");

                    session.setAttribute("singleSelectionChartOrgUnitList","");
                    session.setAttribute("multipleSelectionChartIndicatorList","");
                    session.setAttribute("multipleSelectionChartPeriodList","");
                }
                else if(filter.equalsIgnoreCase("Indicators"))
                {
                    session.removeAttribute("multipleSelectionChartIndicatorList");
                    session.removeAttribute("singleSelectionChartPeriodList");
                    session.removeAttribute("singleSelectionChartOrgUnitList");

                    session.setAttribute("singleSelectionChartIndicatorList","");
                    session.setAttribute("multipleSelectionChartOrgUnitList","");
                    session.setAttribute("multipleSelectionChartPeriodList","");
                }
                else if(filter.equalsIgnoreCase("Period"))
                {
                    session.removeAttribute("multipleSelectionChartPeriodList");
                    session.removeAttribute("singleSelectionChartIndicatorList");
                    session.removeAttribute("singleSelectionChartOrgUnitList");

                    session.setAttribute("singleSelectionChartPeriodList","");
                    session.setAttribute("multipleSelectionChartIndicatorList","");
                    session.setAttribute("multipleSelectionChartOrgUnitList","");  
                }
            }
        }
    }
}
