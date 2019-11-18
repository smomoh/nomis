/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.NutritionAssessment;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.NutritionAssessmentDao;
import com.fhi.kidmap.dao.NutritionAssessmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
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
public class NutritionAssessmentAction extends org.apache.struts.action.Action {
    
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
        HttpSession session=request.getSession();
        NutritionAssessmentForm naf=(NutritionAssessmentForm)form;
        NutritionAssessment na=new NutritionAssessment();
        NutritionAssessmentDao nadao=new NutritionAssessmentDaoImpl();
        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
        Ovc ovc=new Ovc();
        OvcDao ovcDao=new OvcDaoImpl();
        DaoUtil util=new DaoUtil();
        AppUtility appUtil=new AppUtility();
        LoadUpInfo loadup=new LoadUpInfo();
        String hhSerialNo=naf.getHhSerialNo();
        String hhUniqueId=naf.getHhUniqueId();
        String sex=naf.getOvcGender();
        int age=naf.getOvcAge();
        String ageUnit=naf.getAgeUnit();
        String ovcId=naf.getOvcId();
        double height=naf.getHeight();
        double heightInMetres=height/100;
        double weight=naf.getWeight();
        double bmi=0;
        if(heightInMetres >0)
        bmi=(weight/(heightInMetres*heightInMetres));
        bmi=Math.round(bmi);
        System.err.println("double bmi= is "+bmi);
        String enrollmentDate=naf.getDateOfEnrollment();
        String assessmentDate=naf.getDateOfAssessment();
        String dateOfLastWeight=naf.getDateOfLastWeight();
        if(dateOfLastWeight==null || dateOfLastWeight.indexOf("/")==-1)
        {
            dateOfLastWeight=assessmentDate;
        }
        na.setBmi(String.valueOf(bmi));
        na.setBodyMassIndex(bmi);
        na.setChangeInWeight(naf.getChangeInWeight());
        String communityCode=naf.getCommunityCode();
        na.setDateModified(appUtil.getCurrentDate());
        String mySqlDateOfAssessment=appUtil.processMthDayYearToMysqlFormat(assessmentDate);
        String mySqlDateOfLastWeight=appUtil.processMthDayYearToMysqlFormat(dateOfLastWeight);
        na.setAssessmentNo(naf.getAssessmentNo());
        na.setDateOfAssessment(mySqlDateOfAssessment);
        na.setDateOfLastWeight(mySqlDateOfLastWeight);
        na.setFoodSecurityAndDietQ1(naf.getFoodSecurityAndDietQ1());
        na.setFoodSecurityAndDietQ2(naf.getFoodSecurityAndDietQ2());
        na.setFoodSecurityAndDietQ3(naf.getFoodSecurityAndDietQ3());
        na.setFoodSecurityAndDietQ4(naf.getFoodSecurityAndDietQ4());
        na.setFoodSecurityAndDietQ5(naf.getFoodSecurityAndDietQ5());
        na.setFoodSecurityAndDietQ6(naf.getFoodSecurityAndDietQ6());
        na.setFoodSecurityAndDietQ7(naf.getFoodSecurityAndDietQ7());
        na.setFoodSecurityAndDietQ8(naf.getFoodSecurityAndDietQ8());
        na.setFoodSecurityAndDietQ9(naf.getFoodSecurityAndDietQ9());

        na.setHeight(height);
        na.setHygieneQ1(naf.getHygieneQ1());
        na.setHygieneQ2(naf.getHygieneQ2());
        na.setHygieneQ3(naf.getHygieneQ3());
        na.setHygieneQ4(naf.getHygieneQ4());
        na.setLastWeight(naf.getLastWeight());
        na.setMuacFacility(naf.getMuacFacility());
        na.setYellowMuacServices(appUtil.concatStr(naf.getYellowMuacServices(), null));
        /*na.setMedicalEvaluationDiarrhea(naf.getMedicalEvaluationDiarrhea());
        na.setMedicalEvaluationHIV(naf.getMedicalEvaluationHIV());
        na.setMedicalEvaluationMouthSoars(naf.getMedicalEvaluationMouthSoars());
        na.setMedicalEvaluationNausea(naf.getMedicalEvaluationNausea());
        na.setMedicalEvaluationPainfulChewing(naf.getMedicalEvaluationPainfulChewing());
        na.setMedicalEvaluationPoorApetite(naf.getMedicalEvaluationPoorApetite());
        na.setMedicalEvaluationVomiting(naf.getMedicalEvaluationVomiting());*/
        na.setMuac(naf.getMuac());
        na.setNutritionalStatus(naf.getNutritionalStatus());
        na.setOedema(naf.getOedema());
        na.setOvcId(naf.getOvcId());
        na.setRecordedBy(appUtil.getCurrentUser(session));
        na.setWeight(weight);
        String requiredAction=naf.getActionName();
        String lgaCode=naf.getLgaCode();
        String selectedCbo=naf.getOrgCode();
        List ovcList=new ArrayList();
        String hhUniqueIdMsg=" ";
        String ovcWithdrawn=" ";
        request.setAttribute("ovcWithdrawn", ovcWithdrawn);
        ovc=ovcDao.getOvc(ovcId);
        if(ovc !=null)
        setControlStatus(ovc, request);
        if(requiredAction==null)
        {
            form.reset(mapping, request);
            loadup.getStartupInfo(session);
            naf.setLgaCode(null);
            naf.setHhSerialNo(null);
            naf.setCommunityCode(null);
            session.setAttribute("ovcListInNutritionAssessment", ovcList);
            session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
            session.setAttribute("vcUniqueId", "");
            setBtnState(session, "false", "true");
            return mapping.findForward("success");
        }
        else if(requiredAction.equalsIgnoreCase("cboList"))
        {
            naf.reset(mapping, request);
            loadup.getCbosPerLgaFromSetup(session,lgaCode);
            naf.setLgaCode(lgaCode);
            return mapping.findForward("success");
        }
        else if(requiredAction.equalsIgnoreCase("cbo"))
        {
            form.reset(mapping, request);
            naf.setOrgCode(selectedCbo);
            naf.setLgaCode(lgaCode);
            setBtnState(session,"false","true");
            return mapping.findForward("success");
        }
        else if(requiredAction.equalsIgnoreCase("hhDetails"))
        {
            HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
            naf.reset(mapping, request);
              try
              {
                  //System.err.println("hhUniqueId is "+hhUniqueId);
                        HouseholdEnrollment hhe=hhedao.getHouseholdEnrollment(hhUniqueId);
                        naf.setLgaCode(lgaCode);
                        naf.setOrgCode(selectedCbo);
                        naf.setHhSerialNo(hhSerialNo);

                        if(hhe !=null)
                        {
                            hhUniqueIdMsg=hhUniqueId;
                            naf.setHhUniqueId(hhUniqueId);
                            naf.setCommunityCode(util.getWardName(hhe.getCommunityCode()));
                            ovcList=ovcDao.getOvcPerHousehold(hhUniqueId);
                            session.setAttribute("ovcListInNutritionAssessment", ovcList);
                        }
              }
              catch(Exception ex)
              {
                  ex.printStackTrace();
              }
              session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
              return mapping.findForward("success");
        }
        else if(requiredAction.equalsIgnoreCase("baselineDetails"))
        {
            ovcWithdrawn=" ";
            naf.setHhSerialNo(hhSerialNo);
            hhUniqueIdMsg=naf.getHhUniqueId();
            setBtnState(session, "false", "true");
            session.setAttribute("vcHhUniqueId", hhUniqueIdMsg);
            List list=wdao.getWithdrawnOvc(ovcId);

            if(!list.isEmpty())
            {
                ovcWithdrawn="This VC has been withdrawn";
                request.setAttribute("ovcWithdrawn", ovcWithdrawn);
                return mapping.findForward("success");
            }
            List ovcDetailsList=ovcDao.getOVC(ovcId);
            if(!ovcDetailsList.isEmpty())
            {
                ovc=(Ovc)ovcDetailsList.get(0);
                form.reset(mapping, request);
                setControlStatus(ovc, request);               
                setBaselineDetails(ovc, naf,communityCode,hhSerialNo);
                int currentAge=util.getCurrentAge(ovc);
                if(currentAge > 17)
                request.setAttribute("currentAge", "overaged;"+currentAge);
                else
                request.setAttribute("currentAge", "underaged;"+currentAge);
                naf.setActionName(null);
                naf.setLgaCode(lgaCode);
                naf.setOrgCode(selectedCbo);
                session.setAttribute("vcUniqueId", ovc.getOvcId());
                return mapping.findForward(SUCCESS);
            }
            naf.setLgaCode(lgaCode);
            naf.setOrgCode(selectedCbo);
            naf.setCommunityCode(communityCode);            
            //setBtnState(session,"false","true");
            session.setAttribute("ovcWithdrawn", "This VC does not exist in the enrolment record");
            return mapping.findForward("success");
        }
        else if(requiredAction.equalsIgnoreCase("assessmentDetails"))
        {
            //ovc.setAge(age);
            //ovc.setAgeUnit(ageUnit);
            setControlStatus(ovc, request);
            na=nadao.getNutritionAssessment(ovcId, mySqlDateOfAssessment);
            form.reset(mapping, request);
            naf.setOrgCode(selectedCbo);
            naf.setCommunityCode(communityCode);
            naf.setOvcAge(age);
            naf.setOvcGender(sex);
            naf.setAgeUnit(ageUnit);
            naf.setOvcId(ovcId);
            naf.setHhSerialNo(hhSerialNo);
            naf.setDateOfAssessment(assessmentDate);
            naf.setDateOfEnrollment(enrollmentDate);
            setBtnState(session, "false", "true");
            if(na !=null)
            {
                naf.setAssessmentNo(na.getAssessmentNo());
                naf.setBmi(na.getBodyMassIndex());
                naf.setChangeInWeight(na.getChangeInWeight());
                naf.setDateOfLastWeight(appUtil.convertMysqlDateTomonthDayYear(na.getDateOfLastWeight()));
                naf.setFoodSecurityAndDietQ1(na.getFoodSecurityAndDietQ1());
                naf.setFoodSecurityAndDietQ2(na.getFoodSecurityAndDietQ2());
                naf.setFoodSecurityAndDietQ3(na.getFoodSecurityAndDietQ3());
                naf.setFoodSecurityAndDietQ4(na.getFoodSecurityAndDietQ4());
                naf.setFoodSecurityAndDietQ5(na.getFoodSecurityAndDietQ5());
                naf.setFoodSecurityAndDietQ6(na.getFoodSecurityAndDietQ6());
                naf.setFoodSecurityAndDietQ7(na.getFoodSecurityAndDietQ7());
                naf.setFoodSecurityAndDietQ8(na.getFoodSecurityAndDietQ8());
                naf.setFoodSecurityAndDietQ9(na.getFoodSecurityAndDietQ9());
                naf.setHeight(na.getHeight());
                naf.setHygieneQ1(na.getHygieneQ1());
                naf.setHygieneQ2(na.getHygieneQ2());
                naf.setHygieneQ3(na.getHygieneQ3());
                naf.setHygieneQ4(na.getHygieneQ4());
                naf.setId(na.getId());
                naf.setLastWeight(na.getLastWeight());
                /*naf.setMedicalEvaluationDiarrhea(na.getMedicalEvaluationDiarrhea());
                naf.setMedicalEvaluationHIV(na.getMedicalEvaluationHIV());
                naf.setMedicalEvaluationMouthSoars(na.getMedicalEvaluationMouthSoars());
                naf.setMedicalEvaluationNausea(na.getMedicalEvaluationNausea());
                naf.setMedicalEvaluationPainfulChewing(na.getMedicalEvaluationPainfulChewing());
                naf.setMedicalEvaluationPoorApetite(na.getMedicalEvaluationPoorApetite());
                naf.setMedicalEvaluationVomiting(na.getMedicalEvaluationVomiting());*/
                naf.setMuac(na.getMuac());
                naf.setNutritionalStatus(na.getNutritionalStatus());
                naf.setOedema(na.getOedema());
                naf.setOvcId(na.getOvcId());
                naf.setWeight(na.getWeight()); 
                naf.setMuacFacility(na.getMuacFacility());
                naf.setYellowMuacServices(appUtil.splitString(na.getYellowMuacServices(), ","));
                setBtnState(session,"true","false");
            }
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            nadao.saveNutritionAssessment(na);
            form.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            na.setId(naf.getId());
            nadao.updateNutritionAssessment(na);
            nadao.setNutritionAssessmentsWithDateOfLastWeight(na);
            form.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            na.setId(naf.getId());
            nadao.deleteNutritionAssessment(na);
            form.reset(mapping, request);
        }
        return mapping.findForward(SUCCESS);
    }
    private void setBtnState(HttpSession session, String saveState, String modifyState)
    {
        session.setAttribute("nutritionSaveDisabled", saveState);
        session.setAttribute("nutritionModifyDisabled", modifyState);
    }
    private void setControlStatus(Ovc ovc, HttpServletRequest request)
    {
        String disableBMIAndNutritionalStatus="false";
        String disableOedemaAndMUAC="false";
        String disableFoodSecurityAndDietQ4ToQ7="false";
        String disableFoodSecurityAndDietQ8AndQ9="false";

        if(ovc !=null && ovc.getCurrentAgeUnit() !=null)
        {
            DaoUtil util=new DaoUtil();
            int ovcAge=util.getCurrentAge(ovc);
            String ovcCurrentAgeUnit=ovc.getCurrentAgeUnit();
            if(ovcCurrentAgeUnit==null)
            disableOedemaAndMUAC="false";
            else
            {
                if(ovcAge >4 && (ovcCurrentAgeUnit.equalsIgnoreCase("year") ))
                disableBMIAndNutritionalStatus="false";
                else
                disableBMIAndNutritionalStatus="true";
            
                if((ovcAge >5 && ovcCurrentAgeUnit.equalsIgnoreCase("month")) || (ovcAge <5 && ovcCurrentAgeUnit.equalsIgnoreCase("year")))
                disableOedemaAndMUAC="false";
                else
                disableOedemaAndMUAC="true";
                if((ovcAge > 5 && ovcCurrentAgeUnit.equalsIgnoreCase("month")) || ovcCurrentAgeUnit.equalsIgnoreCase("year"))
                disableFoodSecurityAndDietQ4ToQ7="false";
                else
                disableFoodSecurityAndDietQ4ToQ7="true";
                if(ovcAge <6 && ovcCurrentAgeUnit.equalsIgnoreCase("month"))
                disableFoodSecurityAndDietQ8AndQ9="false";
                else
                disableFoodSecurityAndDietQ8AndQ9="true";
            }
        }
        request.setAttribute("disableBMIAndNutritionalStatus", disableBMIAndNutritionalStatus);
        request.setAttribute("disableOedemaAndMUAC", disableOedemaAndMUAC);
        request.setAttribute("disableFoodSecurityAndDietQ4ToQ7", disableFoodSecurityAndDietQ4ToQ7);
        request.setAttribute("disableFoodSecurityAndDietQ8AndQ9", disableFoodSecurityAndDietQ8AndQ9);
    }
    private void setBaselineDetails(Ovc ovc,NutritionAssessmentForm naf,String communityCode, String hhSerialNo)
    {
        if(ovc !=null)
        {
            DaoUtil util=new DaoUtil();
            AppUtility appUtil=new AppUtility();
            naf.setOvcId(ovc.getOvcId());
            naf.setDateOfEnrollment(appUtil.getDisplayDate(ovc.getDateEnrollment()));
            naf.setOvcAge(util.getCurrentAge(ovc));
            naf.setAgeUnit(ovc.getCurrentAgeUnit());
            naf.setOvcGender(ovc.getGender());
            naf.setWeight(ovc.getWeight());
            naf.setHeight(ovc.getHeight());
            naf.setCommunityCode(communityCode);
            naf.setHhSerialNo(hhSerialNo);
        }
    }
}
