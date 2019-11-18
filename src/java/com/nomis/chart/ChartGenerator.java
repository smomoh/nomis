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
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author smomoh
 */
public class ChartGenerator implements Serializable
{
    public JFreeChart createNewBarChart(List mainValueList,double largestValue,String chartTitle,String selectedYear,boolean labelVisible)
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
                        dataset.addValue(value, series,period);
                        System.err.println(series+":- Month: "+period+"-"+value);
                    }
                }
            }
        }
        
    JFreeChart chart = ChartFactory.createBarChart3D(
    chartTitle,
    null,
    "Percentage of Samples",
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

        /*final String series1 = "First";
        final String series2 = "Second";
        final String series3 = "Third";

        // column keys...
        final String category1 = "Category 1";
        final String category2 = "Category 2";
        final String category3 = "Category 3";
        final String category4 = "Category 4";
        final String category5 = "Category 5";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(1.0, series1, category1);
        dataset.addValue(4.0, series1, category2);
        dataset.addValue(3.0, series1, category3);
        dataset.addValue(5.0, series1, category4);
        dataset.addValue(5.0, series1, category5);

        dataset.addValue(5.0, series2, category1);
        dataset.addValue(7.0, series2, category2);
        dataset.addValue(6.0, series2, category3);
        dataset.addValue(8.0, series2, category4);
        dataset.addValue(4.0, series2, category5);

        dataset.addValue(4.0, series3, category1);
        dataset.addValue(3.0, series3, category2);
        dataset.addValue(2.0, series3, category3);
        dataset.addValue(3.0, series3, category4);
        dataset.addValue(6.0, series3, category5);*/


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
/*public JFreeChart createLineChart()
{
 * /*
 *      // row keys...
        final String series1 = "Jalingo GH";
        final String series2 = "FMC Jalingo";
        final String series3 = "First Referral Jalingo";

        // column keys...
        final String type1 = "Jan 15";
        final String type2 = "Feb 15";
        final String type3 = "Mar 15";
        final String type4 = "Apr 15";
        final String type5 = "May 15";
        final String type6 = "Jun 15";
        final String type7 = "Jul 15";
        final String type8 = "Aug 15";

        dataset.addValue(1.0, series1, type1);
        dataset.addValue(4.0, series1, type2);
        dataset.addValue(3.0, series1, type3);
        dataset.addValue(5.0, series1, type4);
        dataset.addValue(5.0, series1, type5);
        dataset.addValue(7.0, series1, type6);
        dataset.addValue(7.0, series1, type7);
        dataset.addValue(8.0, series1, type8);

        dataset.addValue(5.0, series2, type1);
        dataset.addValue(7.0, series2, type2);
        dataset.addValue(6.0, series2, type3);
        dataset.addValue(8.0, series2, type4);
        dataset.addValue(4.0, series2, type5);
        dataset.addValue(4.0, series2, type6);
        dataset.addValue(2.0, series2, type7);
        dataset.addValue(1.0, series2, type8);

        dataset.addValue(4.0, series3, type1);
        dataset.addValue(3.0, series3, type2);
        dataset.addValue(2.0, series3, type3);
        dataset.addValue(3.0, series3, type4);
        dataset.addValue(6.0, series3, type5);
        dataset.addValue(3.0, series3, type6);
        dataset.addValue(4.0, series3, type7);
        dataset.addValue(3.0, series3, type8);*/
        /*final XYSeries series1 = new XYSeries("First");
        series1.add(1.0, 1.0);
        series1.add(2.0, 4.0);
        series1.add(3.0, 3.0);
        series1.add(4.0, 5.0);
        series1.add(5.0, 5.0);
        series1.add(6.0, 7.0);
        series1.add(7.0, 7.0);
        series1.add(8.0, 8.0);

        final XYSeries series2 = new XYSeries("Second");
        series2.add(1.0, 5.0);
        series2.add(2.0, 7.0);
        series2.add(3.0, 6.0);
        series2.add(4.0, 8.0);
        series2.add(5.0, 4.0);
        series2.add(6.0, 4.0);
        series2.add(7.0, 2.0);
        series2.add(8.0, 1.0);

        final XYSeries series3 = new XYSeries("Third");
        series3.add(3.0, 4.0);
        series3.add(4.0, 3.0);
        series3.add(5.0, 2.0);
        series3.add(6.0, 3.0);
        series3.add(7.0, 6.0);
        series3.add(8.0, 3.0);
        series3.add(9.0, 4.0);
        series3.add(10.0, 3.0);

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Line Chart Demo 6",      // chart title
            "Month",                      // x axis label
            "values",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        return chart;

    }*/
}
