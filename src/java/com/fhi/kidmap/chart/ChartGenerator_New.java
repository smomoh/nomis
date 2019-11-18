/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.chart;
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
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class ChartGenerator_New
{
    public JFreeChart createNewBarChart(int value,String indicatorName,String orgUnitName)
    {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List datasetList=new ArrayList();
        dataset.addValue(value, indicatorName, orgUnitName);
    /*for(Object s:datasetList)
    {
    String argsStr = (String)s;
    List args =new ArrayList();

    double count = Double.parseDouble((String)args.get(0));
    String seriesName = (String)args.get(1);
    String lgaName = (String)args.get(2);

    System.err.println(count + "," + seriesName + "," + lgaName);

    dataset.addValue(count, seriesName, lgaName);

    }*/
    JFreeChart chart = ChartFactory.createBarChart3D(
    indicatorName,
    null,
    "Percentage",
    dataset,
    PlotOrientation.VERTICAL,
    true,
    true,
    false
    );


    Font titleFont=new Font("Arial",Font.BOLD,15);


        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis caxis=plot.getDomainAxis();
        caxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI/3.0));

        TextTitle title=new TextTitle("Proportion of OVC in school at enrolment per LGA");
        title.setFont(titleFont);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setUpperMargin(0.15);

        rangeAxis.setNumberFormatOverride(NumberFormat.getPercentInstance());
        rangeAxis.setRange(0, 1);

        BarRenderer3D renderer = new BarRenderer3D();
        //CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setBaseItemLabelGenerator(
                new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("0%")));
        renderer.setItemLabelFont(new Font("Arial", Font.PLAIN, 12));
        renderer.setBaseItemLabelsVisible(false);
        //renderer.setItemLabelAnchorOffset(10.0);
        renderer.setItemLabelAnchorOffset(5.0);
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
        plot.setRenderer(renderer);

        //renderer.setBaseItemLabelsVisible(true);
        renderer.setMaximumBarWidth(0.05);

        renderer.setSeriesPaint(0, Color.decode("#6699FF"));
        //renderer.setSeriesPaint(0, Color.decode("#52A1CC"));
        renderer.setDrawBarOutline(false);
        chart.setTitle(title);
        return chart;
    }
}
