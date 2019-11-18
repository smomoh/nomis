/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.nomis.chart.Chart;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface ChartDao 
{
    public void saveChart(Chart chart) throws Exception;
    public void updateChart(Chart chart) throws Exception;
    public void deleteChart(Chart chart) throws Exception;
    public Chart getChart(String chartId) throws Exception;
    public List getAllCharts() throws Exception;
}
