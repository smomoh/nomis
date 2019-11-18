/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.report.SpecificServiceReport;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface SpecificServiceReportDao 
{
    public void saveSpecificServiceReport(SpecificServiceReport sr) throws Exception;
    public SpecificServiceReport getSpecificServiceReport(String ovcId) throws Exception;
    public List getOvcServicesByOvcIdAndAdditionalServiceQueryByNumberOfServices(String ovcId,String additionalServiceQuery) throws Exception;
    public int getNumberOfServicesPerServiceRecord(SpecificServiceReport service) throws Exception;
    public SpecificServiceReport getHivServiceReport(String ovc_Id) throws Exception;
    public List getNumberOfOvcSupportedToAccessHivServicesPerMonthByCBO(String indicatorName,String stateCode,String startDate,String endDate) throws Exception;
}
