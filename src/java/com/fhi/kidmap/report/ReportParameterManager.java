/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import com.fhi.kidmap.dao.DaoUtil;
import com.nomis.business.ReportHeaderTemplate;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class ReportParameterManager 
{
    public ReportParameterTemplate getPopulatedReportParameterTemplate(List paramList)
    {
        ReportParameterTemplate rpt=new ReportParameterTemplate();
        if(paramList !=null)
        {
            rpt.setStateCode(paramList.get(0).toString());
            rpt.setLgaCode(paramList.get(1).toString());
            rpt.setCboCode(paramList.get(2).toString());
            rpt.setWardCode(paramList.get(3).toString());
            rpt.setStartMonth(Integer.parseInt(paramList.get(4).toString()));
            rpt.setStartYear(Integer.parseInt(paramList.get(5).toString()));
            rpt.setEndMonth(Integer.parseInt(paramList.get(6).toString()));
            rpt.setEndYear(Integer.parseInt(paramList.get(7).toString()));
            if(paramList.size()>7)
            rpt.setPartnerCode(paramList.get(8).toString());
        }
        return rpt;
    }
    public ReportHeaderTemplate getReportHeaderTemplate(List paramList)
    {
        ReportHeaderTemplate rht=new ReportHeaderTemplate();
        DaoUtil util=new DaoUtil();
        String stateName=util.getStateName(paramList.get(0).toString());
        String lgaName=util.getLgaByStateAndLgaCode(paramList.get(0).toString(),paramList.get(1).toString()).getLga_name();
        String cboName=util.getOrganizationName(paramList.get(2).toString());
        String startMth=paramList.get(4).toString();
        String startYr=paramList.get(5).toString();
        String endMth=paramList.get(6).toString();
        String endYr=paramList.get(7).toString();
        String partnerName=util.getPartnerName(paramList.get(8).toString());
        
        String[] dateParam={paramList.get(4).toString(),paramList.get(5).toString(),paramList.get(6).toString(),paramList.get(7).toString()};
        String period=null;            
        if(util.validateDateParamenters(dateParam))
        period="01 "+util.getMonthAsString(Integer.parseInt(paramList.get(4).toString()))+" "+ paramList.get(5).toString()+" to   end of "+util.getMonthAsString(Integer.parseInt(paramList.get(6).toString()))+" "+paramList.get(7).toString();
        
        rht.setCbo(cboName);
        rht.setEndMth(endMth);
        rht.setEndYr(endYr);
        rht.setLga(lgaName);
        rht.setPartnerName(partnerName);
        rht.setStartMth(startMth);
        rht.setStartYr(startYr);
        rht.setState(stateName);
        rht.setPeriod(period);
        
        return rht;
    }
}
