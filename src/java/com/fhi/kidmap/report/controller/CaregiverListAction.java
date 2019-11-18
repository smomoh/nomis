/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.Caregiver;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.report.OvcRecords;
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DataEncryption;
import com.fhi.nomis.nomisutils.DateManager;
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
public class CaregiverListAction extends org.apache.struts.action.Action {
    
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

        CaregiverListForm reportForm=(CaregiverListForm)form;
        //OvcRecords records=new OvcRecords();
        DaoUtil util=new DaoUtil();
        AppUtility appUtil=new AppUtility();
        HttpSession session=request.getSession();
        User user=appUtil.getUser(session);
        AccessManager acm=new AccessManager();
        acm.setStateListForReports(session);
        PartnersDao pdao=new PartnersDaoImpl();
        
        List wardList=new ArrayList();
        String stateCode=reportForm.getState();
        String orgUnitGroupId=reportForm.getOrgUnitGroupId();
        String lgaCode=reportForm.getLga();
        String cboCode=reportForm.getCbo();
        String wardCode=reportForm.getWard();
        String partnerCode=reportForm.getPartnerCode();
        String enroll_month=reportForm.getEnroll_month();
        String enroll_year=reportForm.getEnroll_year();
        String enroll_month2=reportForm.getEnroll_month2();
        String enroll_year2=reportForm.getEnroll_year2();

       String requiredAction=reportForm.getActionName();
       String requestAction=request.getParameter("name");
       if(requestAction !=null)
       requiredAction=requestAction;
       //System.err.println("requestAction is "+requestAction);
       session.removeAttribute("caregiverlist");
       List partnerList=pdao.getAllPartners();
       session.setAttribute("partnerList", partnerList);
       session.setAttribute("caregiverwardList", new ArrayList());
       List yearList=DateManager.generateYears();
        session.setAttribute("yearList", yearList);
       List orgUnitGroupList=new ArrayList();
        if(lgaCode ==null || lgaCode.equalsIgnoreCase("all"))
        {
            orgUnitGroupList=ReportUtility.getOrgUnitGroup(stateCode);
        }
        session.setAttribute("ssorgUnitGroupList", orgUnitGroupList);
        LoadUpInfo loadup=new LoadUpInfo();
        loadup.getAllStates(session);
        loadup.setParamAttributes(request);
        //String dbsource=getServlet().getServletContext().getRealPath("/Resources/dbs");
        //request.setAttribute("dbsource", dbsource);
        //System.err.println("dbsource is "+dbsource);
        if(requiredAction==null)
        {
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equals("allLga"))
        {
            session.setAttribute("caregiverlgaList", new ArrayList());
            session.setAttribute("orgList", new ArrayList());
            if(orgUnitGroupId==null || orgUnitGroupId.equalsIgnoreCase("All"))
            generateLgaList(stateCode,request);
        }
        else if(requiredAction.equals("lga"))
        {
            generateLgaList(stateCode,request);
            
        }
        else if(requiredAction.equals("cbo"))
        {
            List orgList =new ArrayList();
            if(user !=null)
            orgList =acm.getListOfCBOsAssignedToUser(user,stateCode,lgaCode);
            session.setAttribute("orgList", orgList);
        }
        else if(requiredAction.equals("ward"))
        {
            if(user !=null)
            wardList =acm.getListOfCommunitiesAssignedToUserByCBO(user,stateCode,lgaCode,cboCode);
            session.setAttribute("caregiverwardList", wardList);
        }
        else if(requiredAction.equals("caregiverList"))
        {
            if(orgUnitGroupId !=null && !orgUnitGroupId.equalsIgnoreCase("all"))
            {
                lgaCode="orgUnitGroup";
                cboCode=orgUnitGroupId;
            }
            String stateName=util.getStateName(stateCode);
            String lgaName=util.getLgaName(lgaCode);
            String cboName=util.getOrganizationName(cboCode);
            String wardName=util.getWardName(wardCode);
            String partnerName=util.getPartnerName(partnerCode);
            String[] periodArray={enroll_month,enroll_year,enroll_month2,enroll_year2};
            String param[]={stateCode,lgaCode,cboCode,wardCode,null,null,null,null,"0","30",partnerCode,lgaName,cboName,partnerName,stateName,wardName};
            String hviParam[]={stateCode,lgaCode,cboCode,wardCode,null,null,null,null,null,null,partnerCode,wardCode};
            CaregiverDao cgiverdao=new CaregiverDaoImpl();
            String periodQuery=util.getCaregiverReportCriteria(periodArray);
            String additionalQuery=util.getHVIReportCriteria(hviParam);
            List hviList=cgiverdao.getListOfCaregivers(additionalQuery+periodQuery);
            DataEncryption de=new DataEncryption();
            List encryptedList=de.encryptCaregiverInfo(hviList, session);
            OvcDao dao=new OvcDaoImpl();
            Caregiver cgiver=null;
            List list=null;
            List mainList=new ArrayList();
            int SNo=0;
            for(Object obj: encryptedList)
            {
                SNo++;
                list=new ArrayList();
                cgiver=(Caregiver)obj;
                list.add(SNo);
                list.add(cgiver.getCaregiverId());
                list.add(cgiver.getCaregiverFirstname()+" "+cgiver.getCaregiverLastName());
                list.add(cgiver.getCaregiverAddress());
                list.add(cgiver.getCgiverHivStatus());
                list.add(cgiver.getActiveHivStatus().getHivStatus());
                list.add(cgiver.getActiveHivStatus().getDateOfCurrentStatus());
                list.add(cgiver.getActiveHivStatus().getPointOfUpdate());
                list.add(cgiver.getActiveHivStatus().getClientEnrolledInCare());
                list.add(cgiver.getActiveHivStatus().getEnrolledOnART());
                list.add(dao.getNoOfOvcsPerCaregiver(additionalQuery, cgiver.getCaregiverId()));
                mainList.add(list);
            }
            session.setAttribute("caregiverlist", mainList);
            session.setAttribute("caregiverlistparam", param);
            reportForm.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("ovc"))
        {
            String caregiverId=request.getParameter("id");
            CaregiverDao cgiverdao=new CaregiverDaoImpl();
            Caregiver cgiver=cgiverdao.getCaregiverByCaregiverId(caregiverId);
            OvcRecords records=new OvcRecords();
            records.getOvcsPerCaregiver(session, caregiverId);
            request.setAttribute("caregivername", cgiver.getCaregiverFirstname()+" "+cgiver.getCaregiverLastName());
            return mapping.findForward("listOfOvcPerCaregiver");
        }
        return mapping.findForward(SUCCESS);
    }
    private void generateLgaList(String stateCode,HttpServletRequest request)
    {
        AppUtility appUtil=new AppUtility();
        User user=appUtil.getUser(request.getSession());
        AccessManager acm=new AccessManager();
        HttpSession session=request.getSession();
        List lgaList=new ArrayList();
        if(user !=null)
        lgaList =acm.getListOfLgasAssignedToUser(user,stateCode);
        session.setAttribute("caregiverlgaList", lgaList);
        session.setAttribute("orgList", new ArrayList());
    }
}
