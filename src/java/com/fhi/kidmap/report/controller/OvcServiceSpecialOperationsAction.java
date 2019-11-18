/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.dao.OvcServiceDao;
import com.fhi.kidmap.dao.OvcServiceDaoImpl;
import com.fhi.nomis.nomisutils.DateManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class OvcServiceSpecialOperationsAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String SPECIALREPORT="specialReport";

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
        OVCServiceSpecialOperationsForm reportForm=(OVCServiceSpecialOperationsForm)form;
        String requiredAction=reportForm.getActionName();
        String newServiceDate=reportForm.getServiceDate();
        System.err.println("requiredAction "+requiredAction+" "+newServiceDate+" saved");
        if(requiredAction==null)
        {
            
            return mapping.findForward(SPECIALREPORT);
        }
        else if(requiredAction.equals("save"))
        {
            String[] servicesToChange=reportForm.getServicesToChange();
            //System.err.println("servicesToChange "+servicesToChange);
            if(servicesToChange !=null &&(newServiceDate !=null && newServiceDate.indexOf("/") !=-1))
            {
                String idAndDate=null;
                String ovcId=null;
                String serviceDate=null;
                String mysqlServiceDate=null;
                OvcServiceDao sdao=new OvcServiceDaoImpl();
                String[] newServiceDateArray=newServiceDate.split("/");
                String mth=newServiceDateArray[0];
                for(int i=0; i<servicesToChange.length; i++)
                {
                    
                    idAndDate=servicesToChange[i];
                    if(idAndDate !=null && idAndDate.indexOf(":") !=-1)
                    {
                        String[] idAndDateArray=idAndDate.split(":");
                        ovcId=idAndDateArray[0];
                        serviceDate=idAndDateArray[1];
                        serviceDate=DateManager.processMthDayYearToMysqlFormat(serviceDate);
                        String[] serviceDateArray=serviceDate.split("-");
                        OvcService service=sdao.getOvcServiceForTheMth(ovcId, serviceDate);
                        if(service !=null)
                        {
                            mysqlServiceDate=serviceDateArray[0]+"-"+mth+"-"+serviceDateArray[2];
                            //mysqlServiceDate=DateManager.processMthDayYearToMysqlFormat(newServiceDate);
                            
                            if(sdao.getOvcServiceForTheMth(ovcId, mysqlServiceDate)==null)
                            {
                                sdao.deleteService(service);
                                System.err.println("service with "+service.getOvcId()+" "+service.getServicedate()+" deleted");
                                service.setServicedate(mysqlServiceDate);
                                sdao.saveOvcService(service, false, false);
                                System.err.println("service with "+service.getOvcId()+" "+service.getServicedate()+" saved");
                            }
                        }
                    }
                }
            }
            return mapping.findForward("paramPage");
        }
        return mapping.findForward(SPECIALREPORT);
    }
}
