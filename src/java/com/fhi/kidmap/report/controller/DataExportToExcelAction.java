/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.DhisExportDao;
import com.fhi.kidmap.dao.DhisExportDaoImpl;
import com.fhi.kidmap.dao.IndicatorsDao;
import com.fhi.kidmap.dao.IndicatorsDaoImpl;
import com.fhi.kidmap.dao.StatesDao;
import com.fhi.kidmap.dao.StatesDaoImpl;
import com.fhi.kidmap.report.SummaryStatisticsBean;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class DataExportToExcelAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        DaoUtil util=new DaoUtil();
        String target="success";
        IndicatorsDao idao=new IndicatorsDaoImpl();
        List stateList=new ArrayList();
        List lgaList=new ArrayList();
        List cboList=new ArrayList();
        List summStatBeanList=new ArrayList();
        DataExportToExcelForm dhisForm=(DataExportToExcelForm)form;
        StatesDao dao = new StatesDaoImpl();
        String requiredAction=dhisForm.getActionName();
        String stateCode=dhisForm.getState();
        String lgaCode=dhisForm.getLga();
        String cboName=dhisForm.getCbo();
        String month=dhisForm.getMonth();
        String year=dhisForm.getYear();
        String month2=dhisForm.getMonth2();
        String year2=dhisForm.getYear2();
        stateList = dao.getStatesFromEnrollment();
        HttpSession session=request.getSession();
                
        String requestParam=request.getParameter("q");
        if(requestParam !=null)
            requiredAction=requestParam;
        if(requiredAction==null)
        {
            LoadUpInfo loadup=new LoadUpInfo();
            loadup.getAllStates(session);
            loadup.setParamAttributes(request);
            target="excelReportParamPage";
        }
        else if(requiredAction.equals("lga"))
        {
            lgaList =util.getOrganizationalUnits(requiredAction,stateCode,lgaCode,cboName,null);
            session.setAttribute("dhislgaList", lgaList);
            request.removeAttribute("dhiscboList");
            target="excelReportParamPage";
        }
        else if(requiredAction.equals("cbo"))
        {
            OrganizationRecords orgRecords=null;
            List orgList=new ArrayList();
            cboList =util.getOrganizationalUnits(requiredAction,stateCode,lgaCode,cboName,null);
            for(Object org:cboList)
            {
                orgRecords=util.getOrganizationRecords(org.toString());
                orgList.add(orgRecords);
            }
            request.setAttribute("dhiscboList", orgList);
            target="excelReportParamPage";
        }
        else if(requiredAction.equals("generateReportInExcel"))
        {
            String fileName="OVC data "+month+year+"-"+month2+year2;
            if(stateCode !=null && !stateCode.equalsIgnoreCase("All"))
            {
                String stateName=util.getStateName(stateCode);
                fileName="OVC data_"+stateName+"_"+month+year+"-"+month2+year2;
            }
            
            summStatBeanList.addAll(getSummStatBeanList(stateCode,lgaCode,cboName,month,year,month2,year2));
            String summStatExcelData="State"+"\t"+"Lga"+"\t"+"facility"+"\t"+"indicator"+"\t"+"startdate"+"\t"+"value"+"\r\n";
            SummaryStatisticsBean stb=null;
                for(Object s:summStatBeanList)
                {
                    stb=(SummaryStatisticsBean)s;
                    summStatExcelData+=stb.getState()+"\t";
                    summStatExcelData+=stb.getLga()+"\t";
                    summStatExcelData+=stb.getCbo()+"\t";
                    summStatExcelData+=stb.getIndicatorName()+"\t";
                    summStatExcelData+=stb.getMonth()+"/01"+"/"+stb.getYear()+"\t";
                    summStatExcelData+=stb.getNoOfOvc()+"\t"+"\r\n";
                }
               session.setAttribute("attributename",fileName);
               session.setAttribute(fileName,summStatExcelData);
            target="exporttoexcel";
        }
        return mapping.findForward(target);
    }
    public List getSummStatBeanList(String stateCode,String lgaCode,String cboName,String month,String year,String month2,String year2,String dhisInstanceId,int orgUnitLevel)
    {
        String[] params={stateCode,lgaCode,cboName,month,year,month2,year2,dhisInstanceId,orgUnitLevel+""};
        List summStatBeanList=new ArrayList();
        DhisExportDao dhisDao=new DhisExportDaoImpl();
        try
        {
            summStatBeanList=dhisDao.exportToDhis(params);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return summStatBeanList;
    }
    public List getSummStatBeanList(String stateCode,String lgaCode,String cboName,String month,String year,String month2,String year2)
    {
        String[] params={stateCode,lgaCode,cboName,month,year,month2,year2};
        List summStatBeanList=new ArrayList();
        DhisExportDao dhisDao=new DhisExportDaoImpl();
        try
        {
            summStatBeanList=dhisDao.exportToExcel(params);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return summStatBeanList;
    }
}
