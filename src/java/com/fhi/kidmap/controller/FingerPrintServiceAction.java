/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.dao.FingerPrintDao;
import com.fhi.kidmap.dao.FingerPrintDaoImpl;
import com.fhi.kidmap.dao.FingerPrintServiceDao;
import com.fhi.kidmap.dao.FingerPrintServiceDaoImpl;
import com.nomis.business.FingerPrint;
import com.nomis.business.FingerPrintService;
//import com.fhi.nomis.nomisutils.;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 *
 * @author smomoh
 */
public class FingerPrintServiceAction extends org.apache.struts.action.Action {
    
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
        List list=new ArrayList();
        AppUtility appUtils=new AppUtility();
        String serviceString=appUtils.readFiles("C:\\FingerPrint\\Services\\service.txt", ":");
        System.err.println("serviceString is "+serviceString);
        FingerPrintDao fpdao=new FingerPrintDaoImpl();
        for(int i=0; i<list.size(); i++)
        {
            FingerPrintService fps=new FingerPrintService();
            String serviceRecord=(String)list.get(i);
            String[] serviceRecordArray=serviceRecord.split(" ");
            String machineId=serviceRecordArray[0];
            String serviceDate=serviceRecordArray[1].substring(0, 3)+"-"+serviceRecordArray[1].substring(4,5)+"-"+serviceRecordArray[1].substring(6);
            FingerPrint fp=fpdao.getFingerPrint(machineId);
            String ovcId=fp.getOvcId();
            fps.setId(i);
            fps.setMachineId(machineId);
            fps.setOvcId(ovcId);
            fps.setServicedate(serviceDate);
            FingerPrintServiceDao fpsdao=new FingerPrintServiceDaoImpl();
            FingerPrintService duplicatefps=fpsdao.getFingerPrintService(machineId,serviceDate);
            if(duplicatefps ==null)
            fpsdao.saveFingerPrintService(fps);
        }
        return mapping.findForward(SUCCESS);
    }
}
