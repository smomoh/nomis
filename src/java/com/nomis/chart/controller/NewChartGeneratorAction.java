/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.chart.controller;

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
public class NewChartGeneratorAction extends org.apache.struts.action.Action {

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
        String chartType=request.getParameter("type");
        //System.err.println("chartType is "+chartType);
        JFreeChart chart=null;
        int chartWidth=600;
        int chartHeight=500;
        HttpSession session=request.getSession();
        OutputStream out = response.getOutputStream();
        Double largestValue=((Double)session.getAttribute("largestValue")).doubleValue();
        List chartValueList=(List)session.getAttribute("chartValueList");
        String chartTitle=(String)session.getAttribute("chartTitle");
        String selectedYear=(String)session.getAttribute("selectedChartYear");
        String verticalLabel=(String)session.getAttribute("verticalLabel");
        boolean labelVisible=(Boolean)session.getAttribute("labelVisible");
        //System.err.println("chartType is "+chartType);
        if(chartType !=null)
        {
            NewChartGenerator cgen=new NewChartGenerator();
            if(chartType.equalsIgnoreCase("barchart"))
            {
               chart=cgen.createNewBarChart(chartValueList, largestValue, chartTitle,selectedYear,labelVisible,verticalLabel);
            }
            else if(chartType.equalsIgnoreCase("piechart"))
            {
               chart=cgen.createPieChart(chartValueList,chartTitle,selectedYear,labelVisible);
            }
            else if(chartType.equalsIgnoreCase("linegraph"))
            {
                //chartTitle="Test chartTitle";
                chart=cgen.createLineChart(chartValueList,chartTitle,selectedYear,labelVisible);
            }
        }
        response.setContentType("image/png");
        ChartUtilities.writeChartAsPNG(out, chart, chartWidth, chartHeight);
        return null;
    }
}
