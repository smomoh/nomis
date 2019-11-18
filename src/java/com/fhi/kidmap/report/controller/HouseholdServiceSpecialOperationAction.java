/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.HouseholdService;
import com.fhi.kidmap.dao.HouseholdServiceDao;
import com.fhi.kidmap.dao.HouseholdServiceDaoImpl;
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
public class HouseholdServiceSpecialOperationAction extends org.apache.struts.action.Action {

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
            throws Exception {
        
        HouseholdServiceSpecialOperationForm reportForm=(HouseholdServiceSpecialOperationForm)form;
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
                String caregiverId=null;
                String serviceDate=null;
                String mysqlServiceDate=null;
                HouseholdServiceDao sdao=new HouseholdServiceDaoImpl();
                String[] newServiceDateArray=newServiceDate.split("/");
                String mth=newServiceDateArray[0];
                for(int i=0; i<servicesToChange.length; i++)
                {
                    
                    idAndDate=servicesToChange[i];
                    if(idAndDate !=null && idAndDate.indexOf(":") !=-1)
                    {
                        String[] idAndDateArray=idAndDate.split(":");
                        caregiverId=idAndDateArray[0];
                        serviceDate=idAndDateArray[1];
                        serviceDate=DateManager.processMthDayYearToMysqlFormat(serviceDate);
                        String[] serviceDateArray=serviceDate.split("-");
                        HouseholdService service=sdao.getHouseholdService(caregiverId, serviceDate);
                        if(service !=null)
                        {
                            mysqlServiceDate=serviceDateArray[0]+"-"+mth+"-"+serviceDateArray[2];
                            //mysqlServiceDate=DateManager.processMthDayYearToMysqlFormat(newServiceDate);
                            
                            if(sdao.getHouseholdService(caregiverId, mysqlServiceDate)==null)
                            {
                                sdao.deleteHouseholdService(service);
                                System.err.println("service with "+service.getCaregiverId()+" "+service.getServiceDate()+" deleted");
                                service.setServiceDate(mysqlServiceDate);
                                sdao.saveHouseholdService(service);
                                System.err.println("service with "+service.getCaregiverId()+" "+service.getServiceDate()+" saved");
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
