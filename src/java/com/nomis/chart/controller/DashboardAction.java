/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.chart.controller;

import com.fhi.kidmap.dao.ChartDao;
import com.fhi.kidmap.dao.ChartDaoImpl;
import com.fhi.kidmap.dao.DashboardDao;
import com.fhi.kidmap.dao.DashboardDaoImpl;
import com.fhi.nomis.nomisutils.AppUtility;
import com.nomis.chart.Chart;
import com.nomis.chart.ChartContentGenerator;
import com.nomis.chart.Dashboard;
import com.nomis.chart.DashboardManager;
import com.nomis.chart.NewChartGenerator;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author smomoh
 */
public class DashboardAction extends org.apache.struts.action.Action 
{

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String PARAMPAGE ="paramPage";

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
        DashboardForm dbf=(DashboardForm)form;
        HttpSession session=request.getSession();
        ChartDao cdao=new ChartDaoImpl();
        AppUtility appUtil=new AppUtility();
        session.setAttribute("dashboardSaveDisabled", "false");
        session.setAttribute("dashboardModifyDisabled", "true");
        String dashboardType=dbf.getSystemdashboard();
        if(dashboardType==null)
        dashboardType="user";
        
        String[] chartIds=dbf.getChartIds();
        DashboardDao dbdao=new DashboardDaoImpl();
        getDashboardList(session);
        List chartList=cdao.getAllCharts();
        session.setAttribute("chartList", chartList);
        
        String requiredAction=dbf.getActionName();
        String param=request.getParameter("q");
        if(param !=null)
        requiredAction=param;
        if(requiredAction==null)
        {
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("details"))
        {
            
            String selectedId=dbf.getDashboardListId();
            
            Dashboard dboard=dbdao.getDashboard(selectedId);
            if(dboard !=null)
            {
                dbf.setDashboardId(selectedId);
                dbf.setDashboardName(dboard.getDashboardName());
                dbf.setDashboardType(dboard.getDashboardType());
                session.setAttribute("dashboardSaveDisabled", "true");
                session.setAttribute("dashboardModifyDisabled", "false");
            }
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            Dashboard dboard=new Dashboard();
            dboard.setCreatedby(appUtil.getCurrentUser(session));
            dboard.setDashboardName(dbf.getDashboardName());
            dboard.setDashboardType(dashboardType);
            dboard.setDateCreated(appUtil.getCurrentDate());
            dboard.setLastModifiedDate(appUtil.getCurrentDate());
            dboard.setObjectIds(chartIds);
            dbdao.saveDashboard(dboard);
            
            getDashboardList(session);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            Dashboard dboard=new Dashboard();
            dboard.setDashboardId(dbf.getDashboardId());
            dboard.setCreatedby(appUtil.getCurrentUser(session));
            dboard.setDashboardName(dbf.getDashboardName());
            dboard.setDashboardType(dashboardType);
            dboard.setObjectIds(chartIds);
            dboard.setDateCreated(appUtil.getCurrentDate());
            dboard.setLastModifiedDate(appUtil.getCurrentDate());
            dbdao.updateDashboard(dboard);
            getDashboardList(session);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            Dashboard dboard=new Dashboard();
            dboard.setDashboardId(dbf.getDashboardId());
            dboard.setCreatedby(appUtil.getCurrentUser(session));
            dboard.setDashboardName(dbf.getDashboardName());
            dboard.setDashboardType(dashboardType);
            dboard.setDateCreated(appUtil.getCurrentDate());
            dboard.setLastModifiedDate(appUtil.getCurrentDate());
            dbdao.deleteDashboard(dboard);
            getDashboardList(session);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("dashboard"))
        {
            loadDashboardItems(session);
            return mapping.findForward("dashboard");
        }
        else if(requiredAction.equalsIgnoreCase("dbchart"))
        {
            OutputStream out = response.getOutputStream();
            String objectId=request.getParameter("id");
            System.err.println("objectId is "+objectId);
            JFreeChart chart=getChart(session,objectId);
            
            response.setContentType("image/png");
            if(chart !=null)
            ChartUtilities.writeChartAsPNG(out, chart, 500, 400);
            return null;
        }
        return mapping.findForward(PARAMPAGE);
    }
    public void getDashboardList(HttpSession session)
    {
        DashboardDao dbdao=new DashboardDaoImpl();
        try
        {
            List dashboardList=dbdao.getAllDashboards();
            session.setAttribute("dashboardList", dashboardList);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void loadDashboardItems(HttpSession session)
    {
        DashboardManager dm=new DashboardManager();
        dm.loadDashboardItems(session);
        
    }
    public JFreeChart getChart(HttpSession session,String recordId)
    {
        ChartDao cdao=new ChartDaoImpl();
        
        
        String selectedSeriesOption;
        String selectedCategoryOption;
        String selectedFilterOption;
        String selectedYear;
        String stateCode="All";
        String lgaCode="All";
        String cboCode="All";
        //String chartType=null;
        JFreeChart jfchart=null;
        double largestValue=0.0;
        try
        {
            Chart chart=cdao.getChart(recordId);
            if(chart !=null)
            {
                //DaoUtil util=new DaoUtil();
                if(chart.getSelectedIndicators() !=null && chart.getSelectedOrgUnits() !=null && chart.getSelectedPeriods() !=null)
                {
                    ChartContentGenerator ccg=new ChartContentGenerator();
                    NewChartGenerator cgen=new NewChartGenerator();
                    
                    selectedCategoryOption=chart.getCategoryOption();
                    selectedFilterOption=chart.getFilter();
                    List chartValueList=ccg.getChartDataset(chart, session);
                    chart.setChartValueList(chartValueList);
                    jfchart=cgen.getChart(chart,session);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return jfchart;
    }
}
