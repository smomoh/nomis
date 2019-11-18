/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.report.ServiceReport;
import java.util.List;

/**
 *
 * @author Siaka
 */
public interface ServiceReportDao 
{
    public void saveServiceReport(ServiceReport sr) throws Exception;
    public ServiceReport getServiceReport(String ovcId) throws Exception;
    public List getOvcServicesByOvcIdAndAdditionalServiceQueryByNumberOfServices(String ovcId,String additionalServiceQuery) throws Exception;
    public int getNumberOfServicesPerServiceRecord(ServiceReport service) throws Exception;
    public ServiceReport getHivServiceReport(String ovc_Id) throws Exception;
    public ServiceReport getServiceReportByIdAndPeriod(String ovc_Id,String startDate,String endDate) throws Exception;
}
