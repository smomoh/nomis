/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.nomis.chart.Dashboard;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DashboardDao 
{
    public void saveDashboard(Dashboard dboard) throws Exception;
    public void updateDashboard(Dashboard dboard) throws Exception;
    public void deleteDashboard(Dashboard dboard) throws Exception;
    public List getAllDashboards() throws Exception;
    public Dashboard getDashboard(String dashboardId) throws Exception;
}
