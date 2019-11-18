/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.nomis.nomisutils.DatabaseUtilities;
import com.fhi.kidmap.business.FollowupSurvey;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcReferral;
import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.business.Service1;
import com.fhi.kidmap.dao.ChildStatusIndexDao;
import com.fhi.kidmap.dao.ChildStatusIndexDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.FollowupDao;
import com.fhi.kidmap.dao.FollowupDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcReferralDao;
import com.fhi.kidmap.dao.OvcReferralDaoImpl;
import com.fhi.kidmap.dao.OvcServiceDao;
import com.fhi.kidmap.dao.OvcServiceDaoImpl;
import com.fhi.kidmap.dao.ReportTableDao;
import com.fhi.kidmap.dao.ReportTableDaoImpl;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Siaka
 */
public class ReportTableGenerator implements Serializable
{
    DatabaseUtilities dbUtils=new DatabaseUtilities();
    ReportTableDao rtdao=new ReportTableDaoImpl();
    OvcDao ovcDao=new OvcDaoImpl();
    DaoUtil util=new DaoUtil();
     public void cleanUpFollowupSurveyTable()
    {
        try
        {
            FollowupSurvey fs=null;
            FollowupDao fudao=new FollowupDaoImpl();
            List list=fudao.getDistinctFollowupIds(" ");

            for(int i=0; i<list.size(); i++)
            {
                List fsList=fudao.getFollowupRecordsByOvcId((String)list.get(i));
                if(fsList !=null && fsList.size()>1)
                {
                    for(int j=1; j<fsList.size(); j++)
                    {
                        fs=(FollowupSurvey)fsList.get(j);
                        fudao.deleteFollowup(fs);
                        System.err.println("FollowupSurvey with id "+fs.getOvcId()+" deleted.");
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void populateServiceReportTable()
    {
        OvcServiceDao serviceDao=new OvcServiceDaoImpl();
        ServiceReport serviceReport=null;
        OvcService service=new OvcService();
        try
        {
            //rtdao.dropReportTable("SERVICEREPORT");
            dbUtils.createServiceReportTable(); 
            List ovcIdFromServiceList=serviceDao.getOvcIdsFromServices();
            List serviceRecordList=null;
            String ovcId=null;
            
            for(int i=0; i<ovcIdFromServiceList.size(); i++)
            {
                try
                {
                ovcId=ovcIdFromServiceList.get(i).toString();
                serviceRecordList=serviceDao.getOvcServices(ovcId);
                Ovc ovc=ovcDao.getOvc(ovcId);
                if(ovc==null)
                    continue;
                for(int j=0; j<serviceRecordList.size(); j++)
                {
                    if(j>0)
                        break;
                    service=(OvcService)serviceRecordList.get(j);
                    if(service==null)
                        continue;
                    serviceReport=new ServiceReport();
                    
                    serviceReport.setServiceAccessed1(service.getServiceAccessed1());
                    serviceReport.setServiceAccessed2(service.getServiceAccessed2());
                    serviceReport.setServiceAccessed3(service.getServiceAccessed3());
                    serviceReport.setServiceAccessed4(service.getServiceAccessed4());
                    serviceReport.setServiceAccessed5(service.getServiceAccessed5());
                    serviceReport.setServiceAccessed6(service.getServiceAccessed6());
                    serviceReport.setServiceAccessed7(service.getServiceAccessed7());
                    serviceReport.setServicedate(service.getServicedate());
                    serviceReport.setServicesReferred(service.getServicesReferred());
                    serviceReport.setSurveyNumber(service.getSurveyNumber());
                         serviceReport.setWeight(service.getWeight());
                    rtdao.saveServiceReport(serviceReport);
                }
                }
                catch(Exception ex)
                {
                    continue;
                }
            }   
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    
}
