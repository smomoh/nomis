/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fhi.nomis.nomisutils.AppUtility;
import ImportExport.DatabaseExport;
import com.fhi.kidmap.business.ZipHandler;
import com.fhi.kidmap.dao.DaoUtil;
import java.util.List;
import javax.servlet.http.HttpSession;


/**
 *
 * @author smomoh
 */
public class DataExportAction extends org.apache.struts.action.Action {

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
            throws Exception {
        HttpSession session=request.getSession();
        DatabaseExport dbe=new DatabaseExport();
        ZipHandler zipHandler=new ZipHandler();
        String reqParam=request.getParameter("qparam");
        String[] exportPeriod=(String[])request.getSession().getAttribute("exportPeriod");
        //System.err.println("reqParam is "+reqParam);
        if(reqParam !=null && !reqParam.equalsIgnoreCase("zip"))
        {
            String[] params=reqParam.split(":");
            int count=Integer.parseInt(params[0]);
            int tableIndex=Integer.parseInt(params[1]);
            dbe.exportData(session,count,tableIndex);
        }
        //CboSetup setup=null;
        String state="State";
        String lga="Lga";
        String cbo="All";
        String community="All";
        
        DaoUtil util=new DaoUtil();
        
        if(exportPeriod !=null)
        {
            if(exportPeriod[4] !=null && !exportPeriod[4].equalsIgnoreCase("All"))
            {
                state=util.getStateName(exportPeriod[4]);
                if(exportPeriod[5] !=null && !exportPeriod[5].equalsIgnoreCase("All"))
                lga=util.getLgaName(exportPeriod[5]);
                if(exportPeriod[6] !=null && !exportPeriod[6].equalsIgnoreCase("All"))
                cbo=util.getOrganizationName(exportPeriod[6]);
                if(exportPeriod.length>9 && exportPeriod[9] !=null && !exportPeriod[9].equalsIgnoreCase("All"))
                community=util.getWardName(exportPeriod[9]);
            }

            if(state !=null)
            {
                state=state.replace("/", "_");
                state=state.replace("\\", "_");
            }
            if(lga !=null)
            {
                lga=lga.replace("/", "_");
                lga=lga.replace("\\", "_");
            }
            if(cbo !=null)
            {
                cbo=cbo.replace("/", "_");
                cbo=cbo.replace("\\", "_");
            }
            if(community !=null)
            {
                community=community.replace("/", "_");
                community=community.replace("\\", "_");
            }
            String startMonth="All";
            String endMonth="All";
            if(exportPeriod[0] !=null && !exportPeriod[0].equalsIgnoreCase("All"))
            {
                startMonth=util.getMonthAsString(Integer.parseInt(exportPeriod[0]));
                endMonth=util.getMonthAsString(Integer.parseInt(exportPeriod[2]));
            }
            if(startMonth !=null && endMonth !=null)
            {
                String period=startMonth+" "+exportPeriod[1]+" - "+endMonth+" "+exportPeriod[3];
                if(startMonth.equalsIgnoreCase("All"))
                period="Period-All";
                String zipFileName=state+"-"+lga+"-"+cbo+"-"+community+"-"+period;
                AppUtility appUtil=new AppUtility();
                List list=appUtil.copyFilePathsIntoList(appUtil.getExportFilePath());
                zipHandler.zipFiles(list, appUtil.getDbExportZipDirectory(), zipFileName);
                request.getSession().setAttribute("zipFileLocation", appUtil.getDbExportZipDirectory());
                //zipHandler.zipFile(zipFileName);
            }
        }
        //dbe.zipOvcRecords();
        return mapping.findForward(SUCCESS);
                
    }
}
