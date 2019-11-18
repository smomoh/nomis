/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.nomis.chart.DashboardItem;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DashboardItemDao 
{
    public void saveDashboardItem(DashboardItem dbi) throws Exception;
    public void updateDashboardItem(DashboardItem dbi) throws Exception;
    public void deleteDashboardItem(DashboardItem dbi) throws Exception;
    public List getDashboardItemsByDashboardId(String dashboardId) throws Exception;
    public DashboardItem getDashboardItem(String recordId) throws Exception;
    public List getAllDashboardItems() throws Exception;
    public void saveOrUpdateDashboardItems(String dashboardId,String[] itemArray) throws Exception;
    public void deleteDashboardItemsByDashboard(String dashboardId) throws Exception;
    public List getDistinctObjectId(String dashboardId) throws Exception;
}
