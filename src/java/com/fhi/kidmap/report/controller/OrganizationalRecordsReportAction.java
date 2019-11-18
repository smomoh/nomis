/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.LgaDao;
import com.fhi.kidmap.dao.LgaDaoImpl;
import com.fhi.kidmap.dao.OrganizationRecordsDao;
import com.fhi.kidmap.dao.OrganizationRecordsDaoImpl;
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
public class OrganizationalRecordsReportAction extends org.apache.struts.action.Action {
    
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
        OrganizationalRecordsReportForm orgReportForm=(OrganizationalRecordsReportForm)form;
        OrganizationRecordsDao dao=new OrganizationRecordsDaoImpl();
        StatesDao sDao=new StatesDaoImpl();
        LgaDao lDao=new LgaDaoImpl();
        DaoUtil util=new DaoUtil();

        String requiredAction=orgReportForm.getActionName();
        String stateCode=orgReportForm.getState();
        String lgaCode=orgReportForm.getLga();
        HttpSession session=request.getSession();
        List stateCodeList=dao.getOrganizationRecordsStateList();
        List stateList=new ArrayList();
        if(stateList ==null)
        stateList=new ArrayList();
        for(int i=0; i<stateCodeList.size(); i++)
        {
                States state=(States)sDao.getStateByStateCode(stateCodeList.get(i).toString());
                if(state !=null)
                stateList.add(state);
        }

            session.setAttribute("orgRecordsStateList",stateList);
            session.setAttribute("orgRecordsLgaList", new ArrayList());
        
        if(requiredAction !=null && requiredAction.equalsIgnoreCase("lga"))
        {
            List lgaList=new ArrayList();
            
            List list=dao.getOrganizationRecordsLgaList(stateCode);
            for(Object obj:list)
            {
                Lgas lga=lDao.getLgaByCode((String)obj);
                if(lga !=null)
                lgaList.add(lga);
            }
            session.setAttribute("orgRecordsLgaList", lgaList);
        }
        else if(requiredAction !=null && requiredAction.equalsIgnoreCase("orgList"))
        {
            String orgRecordCriteria=util.getOrganizationRecordsReportCriteria(stateCode,lgaCode,"All");
            List organizationList=new ArrayList();
            /*if(stateCode !=null && !stateCode.equalsIgnoreCase("All") && !stateCode.equalsIgnoreCase("") && !stateCode.equalsIgnoreCase(" "))
            {
                orgRecordCriteria=stateCode;
                if(lgaCode !=null && !lgaCode.equalsIgnoreCase("All") && !lgaCode.equalsIgnoreCase("") && !lgaCode.equalsIgnoreCase(" "))
                orgRecordCriteria=stateCode+"/"+lgaCode;
            }*/
            List list=dao.getOrganizationListForExport(orgRecordCriteria);
            for(Object obj:list)
            {
                OrganizationRecords organization=(OrganizationRecords)obj;
                organization.setState(util.getStateName(organization.getState()));
                //organization.setLga(util.getLgaName(organization.getLga()));
                //organizationList.add(organization);
                String assignedLga=organization.getLga();
                String[] assignedLgaArray=null;
                if(assignedLga !=null)
                assignedLgaArray=assignedLga.split(",");
                if(assignedLgaArray !=null)
                {
                            String strLga=null;
                            String strLgaNames="";
                            for(int i=0; i<assignedLgaArray.length; i++)
                            {
                               strLga=util.getLgaName(assignedLgaArray[i]);
                               if(strLga !=null)
                               {
                                    strLgaNames+=strLga;
                                    if(i<assignedLgaArray.length-1)
                                    strLgaNames+=",";
                               }
                            }
                            organization.setLga(strLgaNames);
                }
                organizationList.add(organization);
            }
            int noOfOrganizations=dao.getNumberOfOrganizations(orgRecordCriteria);
            request.setAttribute("noOfOrganizations", noOfOrganizations);
            request.setAttribute("orgRecordsList", organizationList);
            return mapping.findForward("orgRecordsReportPage");
        }
        return mapping.findForward(SUCCESS);
    }
}
