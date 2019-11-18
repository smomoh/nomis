/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.chart;

import com.fhi.kidmap.dao.DashboardItemDao;
import com.fhi.kidmap.dao.DashboardItemDaoImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author smomoh
 */
public class DashboardManager implements Serializable
{
    public void loadDashboardItems(HttpSession session)
    {
        DashboardItemDao dbidao=new DashboardItemDaoImpl();
        List mainObjectIdList=new ArrayList();
        try
        {
            List objectIdList=dbidao.getDistinctObjectId(null);
            if(objectIdList ==null)
            {
                objectIdList=new ArrayList();
            }
                int numberOfCharts=objectIdList.size();
                if(numberOfCharts >0 && (numberOfCharts%2) !=0)
                objectIdList.add("emptyId");
                //System.err.println("dashboardItemList.size() is "+objectIdList.size());
            for(int i=0; i<objectIdList.size(); i+=2)
            {
                List list=new ArrayList();
                list.add(objectIdList.get(i));
                list.add(objectIdList.get(i+1));
                mainObjectIdList.add(list);
                //System.err.println("objectIdList.get(i) is "+objectIdList.get(i).toString()+" objectIdList.get(i+1) is "+objectIdList.get(i+1));
            }
            session.setAttribute("mainObjectIdList", mainObjectIdList);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
