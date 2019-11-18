/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.dao.OptionsManagerDao;
import com.fhi.kidmap.dao.OptionsManagerDaoImpl;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.OptionsAttributeManager;
import com.fhi.nomis.nomisutils.OptionsManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class OptionAction extends org.apache.struts.action.Action {

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
        OptionForm opform=(OptionForm)form;
        String actionName=opform.getActionName();
        String curagedate=OptionsAttributeManager.CURAGEDATAE;
        String hhvaId=OptionsAttributeManager.HHVAID;
        String startDateId=OptionsAttributeManager.STARTDATEID;
        String endDateId=OptionsAttributeManager.ENDDATEID;
        String hracId=OptionsAttributeManager.HRACID;
        String dbImportAccessId=OptionsAttributeManager.DBIMPORTACCESSID;
        String displayClientRecordInHtml=OptionsAttributeManager.DISPLAYCLIENTRECORDINHTMLID;
        String downloadClientRecordInExcel=OptionsAttributeManager.DOWNLOADCLIENTRECORDINEXCELID;
        
        String optionName=opform.getName();
        String value=opform.getCuragedate();
        
        String hhvaValue=opform.getHhvaversion();
        String hracValue=opform.getHracversion();
        String startDate=opform.getStartDate();
        String endDate=opform.getEndDate();
        String dbImportAccess=opform.getXmlDbImport();
        
        String displayInHtmlValue=opform.getDisplayClientRecordInHtml();
        String downloadInExcelValue=opform.getDownloadClientRecordInExcel();
        if(actionName==null)
        {
            opform=getDefaultForm(opform);
            return mapping.findForward(SUCCESS);
        }
        else if(actionName.equalsIgnoreCase("save"))
        {
            OptionsManagerDao opmdao=new OptionsManagerDaoImpl();
            OptionsManager opm=new OptionsManager();
            opm.setName(optionName);
            opm.setValue(DateManager.processMthDayYearToMysqlFormat(value));
            opm.setOptionId(curagedate);
            opm.setLastModifiedDate(DateManager.getCurrentDate());
            opmdao.saveOptionsManager(opm);
            //hracversion
            opm=new OptionsManager();
            opm.setName(optionName);
            opm.setValue(hhvaValue);
            opm.setOptionId(hhvaId);
            opm.setLastModifiedDate(DateManager.getCurrentDate());
            opmdao.saveOptionsManager(opm);
            
                     
            opm=new OptionsManager();
            opm.setName(optionName);
            opm.setValue(hracValue);
            opm.setOptionId(hracId);
            opm.setLastModifiedDate(DateManager.getCurrentDate());
            opmdao.saveOptionsManager(opm);
            
            opm=new OptionsManager();
            opm.setName(optionName);
            opm.setValue(dbImportAccess);
            opm.setOptionId(dbImportAccessId);
            opm.setLastModifiedDate(DateManager.getCurrentDate());
            opmdao.saveOptionsManager(opm);
            
            opm=new OptionsManager();
            opm.setName(optionName);
            opm.setValue(displayInHtmlValue);
            opm.setOptionId(displayClientRecordInHtml);
            opm.setLastModifiedDate(DateManager.getCurrentDate());
            opmdao.saveOptionsManager(opm);
            
            opm=new OptionsManager();
            opm.setName(optionName);
            opm.setValue(downloadInExcelValue);
            opm.setOptionId(downloadClientRecordInExcel);
            opm.setLastModifiedDate(DateManager.getCurrentDate());
            opmdao.saveOptionsManager(opm);
            
            if(startDate !=null && startDate.indexOf("/") !=-1)
            {
                if(endDate !=null && endDate.indexOf("/") !=-1)
                {
                    opm=new OptionsManager();
                    opm.setName(optionName);
                    opm.setValue(DateManager.processMthDayYearToMysqlFormat(startDate));
                    opm.setOptionId(startDateId);
                    opm.setLastModifiedDate(DateManager.getCurrentDate());
                    opmdao.saveOptionsManager(opm);
                    
                    opm=new OptionsManager();
                    opm.setName(optionName);
                    opm.setValue(DateManager.processMthDayYearToMysqlFormat(endDate));
                    opm.setOptionId(endDateId);
                    opm.setLastModifiedDate(DateManager.getCurrentDate());
                    opmdao.saveOptionsManager(opm);
                }
            }
            
        }
        return mapping.findForward(SUCCESS);
    }
    private OptionForm getDefaultForm(OptionForm opf)
    {
        try
        {
        if(opf !=null)
        {
            OptionsManagerDao opmdao=new OptionsManagerDaoImpl();
            OptionsManager opm=opmdao.getOptionsManager("curagedate");
            if(opm !=null)
            opf.setCuragedate(DateManager.getMthDayYearFromMySqlDate(opm.getValue()));
            opm=opmdao.getOptionsManager(OptionsAttributeManager.HHVAID);
            if(opm !=null)
            opf.setHhvaversion(opm.getValue());
            
            opm=opmdao.getOptionsManager(OptionsAttributeManager.HRACID);
            if(opm !=null)
            opf.setHracversion(opm.getValue());
            
            opm=opmdao.getOptionsManager(OptionsAttributeManager.STARTDATEID);
            if(opm !=null)
            opf.setStartDate(DateManager.getMthDayYearFromMySqlDate(opm.getValue()));
            
            opm=opmdao.getOptionsManager(OptionsAttributeManager.ENDDATEID);
            if(opm !=null)
            opf.setEndDate(DateManager.getMthDayYearFromMySqlDate(opm.getValue()));
            opm=opmdao.getOptionsManager(OptionsAttributeManager.DBIMPORTACCESSID);
            if(opm !=null)
            opf.setXmlDbImport(opm.getValue());
            
            opm=opmdao.getOptionsManager(OptionsAttributeManager.DISPLAYCLIENTRECORDINHTMLID);
            if(opm !=null)
            opf.setDisplayClientRecordInHtml(opm.getValue());
            opm=opmdao.getOptionsManager(OptionsAttributeManager.DOWNLOADCLIENTRECORDINEXCELID);
            if(opm !=null)
            opf.setDownloadClientRecordInExcel(opm.getValue());
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return opf;
    }
    
}
