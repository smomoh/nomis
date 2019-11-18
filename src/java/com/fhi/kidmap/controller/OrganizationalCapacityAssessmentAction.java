/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.OrganizationalCapacityAssessment;
import com.fhi.kidmap.dao.OrganizationRecordsDao;
import com.fhi.kidmap.dao.OrganizationRecordsDaoImpl;
import com.fhi.kidmap.dao.OrganizationalCapacityAssessmentDao;
import com.fhi.kidmap.dao.OrganizationalCapacityAssessmentDaoImpl;
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
public class OrganizationalCapacityAssessmentAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String FAILURE="failure";
    
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
        OrganizationalCapacityAssessment oca=new OrganizationalCapacityAssessment();
        OrganizationalCapacityAssessmentDao ocaDao=new OrganizationalCapacityAssessmentDaoImpl();
        OrganizationalCapacityAssessmentForm ocaForm=(OrganizationalCapacityAssessmentForm)form;
        OrganizationRecordsDao orDao=new OrganizationRecordsDaoImpl();
        AppUtility appUtil=new AppUtility();

        String dateOfCapacityAssessment=ocaForm.getDateofcapacityassessment();
        String orgCode=ocaForm.getOrgCode();
        String typeOfOrganization=ocaForm.getTypeoforganization();
        String subgrantee=ocaForm.getIsorganizationasubgrantee();
        int noOfAssessment=ocaForm.getNoofassessment();
        String dateOfFirstAssessment=ocaForm.getDateoffirstcapassessment();
        //if(dateOfFirstAssessment !=null)
        //dateOfFirstAssessment=appUtil.getDisplayDate(dateOfFirstAssessment);


        String day=null;
        String month=null;
        String year=null;
        String requiredAction=ocaForm.getActionName();
        
        HttpSession session=request.getSession();

        LoadUpInfo loadup=new LoadUpInfo();
        loadup.setRequestParameters(request);
        loadup.getOrganizationList(session);
        if(requiredAction ==null)
        {
            ocaForm.reset(mapping,request);
            return mapping.findForward(SUCCESS);
        }
            try
            {
                String[] dateArray=dateOfCapacityAssessment.split("/");
                if(dateArray !=null && dateArray.length==3)
                {
                    day=dateArray[1];
                    month=dateArray[0];
                    year=dateArray[2];
                }
            }
            catch(ArrayIndexOutOfBoundsException aiobe)
            {
                aiobe.printStackTrace();
            }
        
        //String orgName=null;
        if(ocaForm.getOrgCode() !=null)
        orgCode=ocaForm.getOrgCode().trim();
        String dateoffirstcapassessment=ocaForm.getDateoffirstcapassessment();
        //String displayDateoffirstcapassessment=null;
        if(requiredAction.equals("noOfAssessmentAndDateOfFirstAssessment"))
        {
            ocaForm.setNoofassessment(ocaDao.getNoOfCapacityAssessment(orgCode));
            dateoffirstcapassessment=ocaDao.getDateOfFirstCapacityAssessment(orgCode);
            if(dateoffirstcapassessment !=null)
            dateoffirstcapassessment=appUtil.monthDayYear(dateoffirstcapassessment);
            ocaForm.setDateoffirstcapassessment(dateoffirstcapassessment);
            ocaForm.setTypeoforganization(orDao.getTypeOfOrganization(orgCode));
            int noOfAssessment2=ocaDao.getNoOfCapacityAssessment(orgCode);
            getOrganizationAssessedBefore(noOfAssessment);
            
            ocaForm.setAssessedbefore(getOrganizationAssessedBefore(noOfAssessment2));
            
            request.setAttribute("dateOfFirstAssessment", dateoffirstcapassessment);
            request.setAttribute("noofassessment", ocaDao.getNoOfCapacityAssessment(orgCode));
            return mapping.findForward(SUCCESS);
        }
        
        if(requiredAction.equals("assessmentDetails"))
        {
             oca=(OrganizationalCapacityAssessment)ocaDao.getOrganizationalCapacityAssessment(orgCode, appUtil.getMySqlDate(day, month, year));
             request.setAttribute("dateOfFirstAssessment", dateOfFirstAssessment);
             request.setAttribute("noofassessment", noOfAssessment);

             if(oca !=null)
             {
                 noOfAssessment=oca.getNoofassessment();

                 ocaForm.setActionplanning(oca.getActionplanning());
                 ocaForm.setAdvocacy(oca.getAdvocacy());
                 ocaForm.setAssessedbefore(getOrganizationAssessedBefore(noOfAssessment));
                 
                 ocaForm.setBanking(oca.getBanking());
                 ocaForm.setBookkeeping(oca.getBookkeeping());
                 ocaForm.setBudgeting(oca.getBudgeting());
                 ocaForm.setTypeoforganization(orDao.getTypeOfOrganization(orgCode));
                 ocaForm.setDateofcapacityassessment(dateOfCapacityAssessment);
                 ocaForm.setDateoffirstcapassessment(dateOfFirstAssessment);
                 ocaForm.setGoalsandobjectives(oca.getGoalsandobjectives());
                 ocaForm.setInternalrules(oca.getInternalrules());
                 ocaForm.setIsorganizationasubgrantee(oca.getIsorganizationasubgrantee());
                 ocaForm.setLeadassessorname(oca.getLeadassessorname());
                 ocaForm.setLeadership(oca.getLeadership());
                 ocaForm.setLocalresourcemobilization(oca.getLocalresourcemobilization());
                 ocaForm.setMeetings(oca.getMeetings());
                 ocaForm.setMonitoring(oca.getMonitoring());
                 ocaForm.setNetworking(oca.getNetworking());
                 //ocaForm.setNoofassessment(noOfAssessment);
                 ocaForm.setNoofassessors(oca.getNoofassessors());
                 ocaForm.setOrgCode(oca.getOrgCode());
                 ocaForm.setProposalwriting(oca.getProposalwriting());
                 ocaForm.setRecordId(oca.getRecordId());
                 ocaForm.setReportingandrecordkeeping(oca.getReportingandrecordkeeping());
                 ocaForm.setRoleandresponsibilities(oca.getRoleandresponsibilities());
                 ocaForm.setTeambuilding(oca.getTeambuilding());
                 //ocaForm.setTypeoforganization(oca.getTypeoforganization());
                 ocaForm.setVisionandmission(oca.getVisionandmission());

                 
                 request.setAttribute("enableOrgCapSave", "true");
                 request.setAttribute("enableOrgCapModify", "false");

             }
             else
             {
                 ocaForm.reset(mapping,request);
                 ocaForm.setOrgCode(orgCode);
                 ocaForm.setTypeoforganization(orDao.getTypeOfOrganization(orgCode));
                 ocaForm.setDateofcapacityassessment(dateOfCapacityAssessment);
                 ocaForm.setDateoffirstcapassessment(dateOfFirstAssessment);
                 ocaForm.setNoofassessment(noOfAssessment);
                 //ocaForm.setIsorganizationasubgrantee(subgrantee);
                 ocaForm.setAssessedbefore(getOrganizationAssessedBefore(noOfAssessment));
             }
             ocaForm.setActionName(null);
             return mapping.findForward(SUCCESS);
        }
        noOfAssessment=ocaForm.getNoofassessment();
        if(noOfAssessment==0)
        noOfAssessment++;
        oca.setActionplanning(ocaForm.getActionplanning());
        oca.setAdvocacy(ocaForm.getAdvocacy());
        //oca.setAssessedbefore(ocaForm.getAssessedbefore());
        oca.setBanking(ocaForm.getBanking());
        oca.setBookkeeping(ocaForm.getBookkeeping());
        oca.setBudgeting(ocaForm.getBudgeting());
        oca.setDateofcapacityassessment(appUtil.getMySqlDate(day, month, year));
        oca.setGoalsandobjectives(ocaForm.getGoalsandobjectives());
        oca.setInternalrules(ocaForm.getInternalrules());
        oca.setIsorganizationasubgrantee(ocaForm.getIsorganizationasubgrantee());
        oca.setLeadassessorname(ocaForm.getLeadassessorname());
        oca.setLeadership(ocaForm.getLeadership());
        oca.setLocalresourcemobilization(ocaForm.getLocalresourcemobilization());
        oca.setMeetings(ocaForm.getMeetings());
        oca.setMonitoring(ocaForm.getMonitoring());
        oca.setNetworking(ocaForm.getNetworking());
        oca.setNoofassessment(noOfAssessment);
        oca.setNoofassessors(ocaForm.getNoofassessors());
        oca.setOrgCode(orgCode);
        oca.setProposalwriting(ocaForm.getProposalwriting());
        oca.setRecordId(ocaForm.getRecordId());

        oca.setReportingandrecordkeeping(ocaForm.getReportingandrecordkeeping());
        oca.setRoleandresponsibilities(ocaForm.getRoleandresponsibilities());
        oca.setTeambuilding(ocaForm.getTeambuilding());
        //oca.setTypeoforganization(ocaForm.getTypeoforganization());
        oca.setVisionandmission(ocaForm.getVisionandmission());
        
        try
        {
            if(requiredAction.equals("save"))
            {
                ocaDao.saveOrganizationalCapacityAssessment(oca);
            }
            else if(requiredAction.equals("modify"))
            {
                ocaDao.updateOrganizationalCapacityAssessment(oca);
            }
            else if(requiredAction.equals("delete"))
            {
                ocaDao.deleteOrganizationalCapacityAssessment(oca);
            }
            ocaForm.reset(mapping,request);
        }
        catch(Exception ex)
        {
           return mapping.findForward(FAILURE);
        }
        return mapping.findForward(SUCCESS);
    }
    public String getOrganizationAssessedBefore(int noOfAssessment)
    {
            if(noOfAssessment>0)
            return "Yes";
            else
            return "No";
    }
}
