/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import java.io.Serializable;
import java.text.DecimalFormat;
import javax.servlet.http.HttpSession;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.general.DefaultPieDataset;
/**
 *
 * @author smomoh
 */
public class NewChartGenerator implements Serializable
{
    public JFreeChart createNewBarChart(List mainValueList,double largestValue,String chartTitle,String selectedYear,boolean labelVisible,String verticalLabel)
    {
        List valueList=null;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        double value=5.0;
        String series="series";
        String filter="filter";
        String period="mth";
        double maxValue=Math.ceil(largestValue);
        if(mainValueList !=null)
        {
            for(int j=0; j<mainValueList.size(); j++)
            {
                valueList=(List)mainValueList.get(j);
                if(valueList !=null && valueList.size()>2)
                {
                    for(int i=0; i<valueList.size(); i+=4)
                    {
                        value=Double.parseDouble(valueList.get(i).toString());
                        value=Math.round(value*100);
                        series=(String)valueList.get(i+1);
                        period=(String)valueList.get(i+2);
                        filter=(String)valueList.get(i+3);
                        //System.err.println("Series: "+series+" Month: "+period+"Value: "+value);
                        dataset.addValue(value, series,period);
                        
                    }
                }
            }
        }
        
    JFreeChart chart = ChartFactory.createBarChart3D(
    chartTitle,
    null,
    verticalLabel,
    dataset,
    PlotOrientation.VERTICAL,
    true,
    true,
    false
    );

        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis caxis=plot.getDomainAxis();
        caxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
        plot.setNoDataMessage("No data to display");
        caxis.setMaximumCategoryLabelWidthRatio(0);
        //caxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI/3.0));
        //caxis.setCategoryMargin(0.0);
        //plot.getDomainAxis().setCategoryMargin(0.0);
        /*Font titleFont=new Font("Arial",Font.BOLD,25);
        TextTitle title=new TextTitle(chartTitle);
        title.setFont(titleFont);*/

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setUpperMargin(0.15);

        //rangeAxis.setNumberFormatOverride(NumberFormat.getNumberInstance());
        rangeAxis.setNumberFormatOverride(NumberFormat.getNumberInstance());
        //rangeAxis.setRange(0, 100);
        //rangeAxis.setRange(0, maxValue);
        //System.err.println("maxValue is "+maxValue);
        BarRenderer3D renderer = new BarRenderer3D();
        //CategoryItemRenderer renderer = plot.getRenderer();
        //renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("0%")));
        if(labelVisible)
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", DecimalFormat.getNumberInstance()));
        //renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setItemLabelFont(new Font("Arial", Font.PLAIN, 12));
        renderer.setBaseItemLabelsVisible(false);
        renderer.setBaseLegendTextFont(new Font("Arial", Font.PLAIN, 13));

        //renderer.setItemLabelAnchorOffset(10.0);
        renderer.setItemLabelAnchorOffset(0.0);
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
        renderer.setMaximumBarWidth(0.10);

        //renderer.setSeriesPaint(0, Color.decode("#6699FF"));
        renderer.setSeriesPaint(0, Color.decode("#FFA500"));
        //renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0.0);
        plot.setRenderer(renderer);
        renderer.setBaseItemLabelsVisible(true);
        chartTitle=filter+" ("+selectedYear+")";
        Font titleFont=new Font("Arial",Font.BOLD,15);
        TextTitle title=new TextTitle(chartTitle);
        title.setFont(titleFont);
        chart.setTitle(title);
        return chart;
}
public JFreeChart createPieChart(List mainValueList,String chartTitle,String selectedYear,boolean labelVisible)
{
    // create a dataset...
    DefaultPieDataset data = new DefaultPieDataset();
    double value=0;
    String indicatorName=null;
    String series=null;
    String filter=null;
    String period=null;
    List valueList=null;
    if(mainValueList !=null)
        {
            for(int j=0; j<mainValueList.size(); j++)
            {
                valueList=(List)mainValueList.get(j);
                if(valueList !=null && valueList.size()>2)
                {
                    for(int i=0; i<valueList.size(); i+=4)
                    {
                        value=Double.parseDouble(valueList.get(i).toString());
                        series=(String)valueList.get(i+1);
                        period=(String)valueList.get(i+2);
                        filter=(String)valueList.get(i+3);
                        data.setValue(series, new Double(value));
                        System.err.println(series+":- filter: "+filter+"-"+value);
                    }
                }
            }
        }

    JFreeChart chart = ChartFactory.createPieChart(
    chartTitle, data, true, false, false
    );
    PiePlot plot = (PiePlot) chart.getPlot();

    PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(
            "{2}", new DecimalFormat("0"), new DecimalFormat("0.0%"));

    plot.setLabelGenerator(generator);
    if(!labelVisible)
    plot.setLabelGenerator(null);
    //plot.setSectionOutlinesVisible(labelVisible);
    //plot.setBaseSectionOutlineStroke(plot.getBaseSectionOutlineStroke());
    chartTitle=filter+" "+" ("+period+" "+selectedYear+")";
    Font titleFont=new Font("Arial",Font.BOLD,15);
    TextTitle title=new TextTitle(chartTitle);
    title.setFont(titleFont);
   //(new StandardPieSectionLabelGenerator()).
    //plot.setSimpleLabels(true);
    //plot.setLabelGenerator(null);
    //plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})",NumberFormat.getNumberInstance(),NumberFormat.getPercentInstance()));
        //plot.setMaximumLabelWidth(0.20);

        //plot.setLabelGenerator(new CustomLabelGenerator(data_type));
    chart.setTitle(title);
    return chart;
}
public JFreeChart createLineChart(List mainValueList,String chartTitle,String selectedYear,boolean labelVisible)
{
    List valueList=null;
        
        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        double value=5.0;
        String period="Jan";
        String series="series";
        String filter="filter";
        int numberOfSeries=0;
        if(mainValueList !=null)
        {
            numberOfSeries=mainValueList.size();
            for(int j=0; j<mainValueList.size(); j++)
            {
                valueList=(List)mainValueList.get(j);
                if(valueList !=null && valueList.size()>3)
                {
                    for(int i=0; i<valueList.size(); i+=4)
                    {
                        value=Double.parseDouble(valueList.get(i).toString());
                        series=(String)valueList.get(i+1);
                        period=(String)valueList.get(i+2);
                        filter=(String)valueList.get(i+3);
                        dataset.addValue(value, series,period);
                        System.err.println(series+":- Month: "+period+"-"+value);
                    }
                }
            }
        }


        final JFreeChart chart = ChartFactory.createLineChart(
            chartTitle,      // chart title
            "Month",                   // domain axis label
            "Value",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        numberOfSeries=numberOfSeries+1;
        for(int i=1; i<numberOfSeries; i++)
        {
            renderer.setSeriesStroke(i, new BasicStroke(4.0f));
        }
        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        renderer.setSeriesPaint(0, Color.decode("#FFA500"));
        rangeAxis.setNumberFormatOverride(NumberFormat.getPercentInstance());
        chartTitle=filter+" ("+selectedYear+")";
        Font titleFont=new Font("Arial",Font.BOLD,15);
        TextTitle title=new TextTitle(chartTitle);
        
        title.setFont(titleFont);
        chart.setTitle(title);
        renderer.setBaseItemLabelsVisible(labelVisible);
        //plot.getRenderer().setSeriesStroke(i, new BasicStroke(2));
        //renderer.setSeriesPaint(1, Color.decode("#FFA500"));
        //plot.setBackgroundPaint(Color.decode("#FFCCFF"));
        plot.setRenderer(renderer);
        //rangeAxis.setUpperMargin(0.10);
        //rangeAxis.setNumberFormatOverride(NumberFormat.getNumberInstance());
        //rangeAxis.setRange(0, maxValue);
        return chart;

    }

    public JFreeChart getChart(Chart chartObject,HttpSession session)
    {
        String chartType=chartObject.getChartType();
        //System.err.println("chartId:"+chartId);
        Double largestValue=((Double)session.getAttribute("largestValue")).doubleValue();
        List chartValueList=chartObject.getChartValueList();//List)session.getAttribute("chartValueList");
        //System.err.println("chartValueList:"+chartValueList);
        String chartTitle=(String)session.getAttribute("chartTitle");
        String selectedYear=(String)session.getAttribute("selectedChartYear");
        String verticalLabel=(String)session.getAttribute("verticalLabel");
        boolean labelVisible=true;//(Boolean)session.getAttribute("labelVisible");
        //System.err.println("largestValue:"+largestValue+" selectedYear:"+selectedYear);
        //String chartType="barchart";
        JFreeChart chart=null;
        try
        {
            NewChartGenerator cgen=new NewChartGenerator();
            if(chartType==null || chartType.equalsIgnoreCase("barchart"))
            {
               chart=cgen.createNewBarChart(chartValueList, largestValue, chartTitle,selectedYear,labelVisible,verticalLabel);
            }
            else if(chartType.equalsIgnoreCase("piechart"))
            {
               chart=cgen.createPieChart(chartValueList,chartTitle,selectedYear,labelVisible);
            }
            else if(chartType.equalsIgnoreCase("linegraph"))
            {
                chart=cgen.createLineChart(chartValueList,chartTitle,selectedYear,labelVisible);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return chart;
    }
    public JFreeChart getChart(List chartValueList, double largestValue, String chartTitle,String selectedYear,boolean labelVisible,String verticalLabel)
    {
        String chartType=null;
        JFreeChart chart=null;
        try
        {
            NewChartGenerator cgen=new NewChartGenerator();
            if(chartType==null || chartType.equalsIgnoreCase("barchart"))
            {
               chart=cgen.createNewBarChart(chartValueList, largestValue, chartTitle,selectedYear,labelVisible,verticalLabel);
            }
            else if(chartType.equalsIgnoreCase("piechart"))
            {
               chart=cgen.createPieChart(chartValueList,chartTitle,selectedYear,labelVisible);
            }
            else if(chartType.equalsIgnoreCase("linegraph"))
            {
                chart=cgen.createLineChart(chartValueList,chartTitle,selectedYear,labelVisible);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return chart;
    }
}
