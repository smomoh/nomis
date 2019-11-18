/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.DhisDataExport;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.DhisExportDao;
import com.fhi.kidmap.dao.DhisExportDaoImpl;
import com.fhi.kidmap.dao.IndicatorsDao;
import com.fhi.kidmap.dao.IndicatorsDaoImpl;
import com.fhi.kidmap.dao.StatesDao;
import com.fhi.kidmap.dao.StatesDaoImpl;
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
public class ExportToDhisAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static String target = "success";

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
            throws Exception {
        DaoUtil util=new DaoUtil();
        IndicatorsDao idao=new IndicatorsDaoImpl();
        List stateList=new ArrayList();
        List lgaList=new ArrayList();
        List cboList=new ArrayList();
        List summStatBeanList=new ArrayList();
        DhisExportForm dhisForm=(DhisExportForm)form;
        StatesDao dao = new StatesDaoImpl();
        String requiredAction=dhisForm.getActionName();
        String stateCode=dhisForm.getState();
        String lgaCode=dhisForm.getLga();
        String cboName=dhisForm.getCbo();
        String month=dhisForm.getMonth();
        String year=dhisForm.getYear();
        String month2=dhisForm.getMonth2();
        String year2=dhisForm.getYear2();
        int orgUnitLevel=dhisForm.getOrgUnitLevel();
        String dhisInstanceId=dhisForm.getDhisInstanceId();
        stateList = dao.getStatesFromEnrollment();
        HttpSession session=request.getSession();
        List dhisIntanceList=idao.getDhisInstances();
        session.setAttribute("dhisIntanceList", dhisIntanceList);
        String requestParam=request.getParameter("q");
        if(requestParam !=null)
            requiredAction=requestParam;
        if(requiredAction==null)
        {
            LoadUpInfo loadup=new LoadUpInfo();
            loadup.getAllStates(session);
            loadup.setParamAttributes(request);
            target="paramPage";
        }
        else if(requiredAction.equalsIgnoreCase("excelReport"))
        {
            LoadUpInfo loadup=new LoadUpInfo();
            loadup.getAllStates(session);
            loadup.setParamAttributes(request);
            target="excelReportParamPage";
        }
        else if(requiredAction.equals("lga"))
        {
            lgaList =util.getUserAssignedLgas(stateCode,session);
            //lgaList =util.getOrganizationalUnits(requiredAction,stateCode,lgaCode,cboName,null);
            session.setAttribute("dhislgaList", lgaList);
            request.removeAttribute("dhiscboList");
            target="paramPage";
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =util.getUserAssignedCBOs(stateCode,lgaCode,session);
            /*OrganizationRecords orgRecords=null;
            List orgList=new ArrayList();
            cboList =util.getOrganizationalUnits(requiredAction,stateCode,lgaCode,cboName,null);
            for(Object org:cboList)
            {
                orgRecords=util.getOrganizationRecords(org.toString());
                orgList.add(orgRecords);
            }*/
            request.setAttribute("dhiscboList", orgList);
            target="paramPage";
        }
        else if(requiredAction.equals("dhisexport"))
        {
            DhisExportDao dhisDao=new DhisExportDaoImpl();
            String[] params={stateCode,lgaCode,cboName,month,year,month2,year2};
            dhisDao.saveOvcDataInDde(params, dhisInstanceId);
            dhisDao.createDhisExportFile(dhisInstanceId);           
            target="success";
        }
        else if(requiredAction.equals("generateReportInExcel"))
        {
            String fileName="OVC data "+month+year+"-"+month2+year2;
            summStatBeanList.addAll(getDhisDataExportList(stateCode,lgaCode,cboName,month,year,month2,year2,dhisInstanceId));
            String summStatExcelData="State"+"\t"+"Lga"+"\t"+"facility"+"\t"+"indicator"+"\t"+"startdate"+"\t"+"value"+"facility Id"+"\t"+"indicator Id"+"category Id"+"\r\n";
            DhisDataExport dde=null;
                for(Object s:summStatBeanList)
                {
                    dde=(DhisDataExport)s;
                    summStatExcelData+=dde.getState()+"\t";
                    summStatExcelData+=dde.getLga()+"\t";
                    summStatExcelData+=dde.getOrgUnitName()+"\t";
                    summStatExcelData+=dde.getDataElementName()+"\t";
                    summStatExcelData+=dde.getStartDate()+"\t";
                    summStatExcelData+=dde.getValue()+"\t";
                    summStatExcelData+=dde.getOrgUnitId()+"\t";
                    summStatExcelData+=dde.getDataElementId()+"\t";
                    summStatExcelData+=dde.getCategoryOptionComboId()+"\t"+"\r\n";
                }
               session.setAttribute("attributename",fileName);
               session.setAttribute(fileName,summStatExcelData);
            target="exporttoexcel";
        }
        return mapping.findForward(target);
    }

    /*public List getSummStatBeanList(String stateCode,String lgaCode,String cboName,String month,String year,String month2,String year2,String dhisInstanceId,int orgUnitLevel)
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
    }*/
    public List getDhisDataExportList(String stateCode,String lgaCode,String cboName,String month,String year,String month2,String year2,String dhisInstanceId)
    {
        String[] params={stateCode,lgaCode,cboName,month,year,month2,year2};
        List ddeList=new ArrayList();
        DhisExportDao dhisDao=new DhisExportDaoImpl();
        try
        {
            dhisDao.saveOvcDataInDde(params, dhisInstanceId);
            ddeList=dhisDao.getOvcDataInDde();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return ddeList;
    }
}
