/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class NutritionAssessmentForm extends org.apache.struts.action.ActionForm {
    
    private String actionName;
    private String stateCode;
    private String lgaCode;
    private String orgCode;
    private String communityCode;
    private String  dateOfAssessment;
    private String  dateOfEnrollment;
    private int id;
    private String hhSerialNo;
    private String hhUniqueId;
    private String ovcId;
    private String ovcGender;
    private int ovcAge;
    private int assessmentNo;
    private String ageUnit;
    private double weight;
    private double lastWeight;
    private double height;
    private String  dateOfLastWeight;
    private String changeInWeight;
    private String oedema;
    private String muac;
    private double bmi;
    private String nutritionalStatus;
    private String foodSecurityAndDietQ1;
    private String foodSecurityAndDietQ2;
    private String foodSecurityAndDietQ3;
    private String foodSecurityAndDietQ4;
    private String foodSecurityAndDietQ5;
    private String foodSecurityAndDietQ6;
    private String foodSecurityAndDietQ7;
    private String foodSecurityAndDietQ8;
    private String foodSecurityAndDietQ9;
    private String hygieneQ1;
    private String hygieneQ2;
    private String hygieneQ3;
    private String hygieneQ4;
    private String muacFacility;
    private String[] yellowMuacServices;
    private List referralDirectoryList=new ArrayList();
    /*private String medicalEvaluationHIV;
    private String medicalEvaluationDiarrhea;
    private String medicalEvaluationNausea;
    private String medicalEvaluationPainfulChewing;
    private String medicalEvaluationVomiting;
    private String medicalEvaluationPoorApetite;
    private String medicalEvaluationMouthSoars;*/
    

    /**
     *
     */
    
    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getAssessmentNo() {
        return assessmentNo;
    }

    public void setAssessmentNo(int assessmentNo) {
        this.assessmentNo = assessmentNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHhSerialNo() {
        return hhSerialNo;
    }

    public void setHhSerialNo(String hhSerialNo) {
        this.hhSerialNo = hhSerialNo;
    }

    public String getHhUniqueId() {
        return hhUniqueId;
    }

    public void setHhUniqueId(String hhUniqueId) {
        this.hhUniqueId = hhUniqueId;
    }
    
    public NutritionAssessmentForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }
    
    public String getChangeInWeight() {
        return changeInWeight;
    }

    public void setChangeInWeight(String changeInWeight) {
        this.changeInWeight = changeInWeight;
    }

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }

    public String getDateOfAssessment() {
        return dateOfAssessment;
    }

    public void setDateOfAssessment(String dateOfAssessment) {
        this.dateOfAssessment = dateOfAssessment;
    }

    public String getDateOfEnrollment() {
        return dateOfEnrollment;
    }

    public void setDateOfEnrollment(String dateOfEnrollment) {
        this.dateOfEnrollment = dateOfEnrollment;
    }

    public String getDateOfLastWeight() {
        return dateOfLastWeight;
    }

    public void setDateOfLastWeight(String dateOfLastWeight) {
        this.dateOfLastWeight = dateOfLastWeight;
    }

    public String getFoodSecurityAndDietQ1() {
        return foodSecurityAndDietQ1;
    }

    public void setFoodSecurityAndDietQ1(String foodSecurityAndDietQ1) {
        this.foodSecurityAndDietQ1 = foodSecurityAndDietQ1;
    }

    public String getFoodSecurityAndDietQ2() {
        return foodSecurityAndDietQ2;
    }

    public void setFoodSecurityAndDietQ2(String foodSecurityAndDietQ2) {
        this.foodSecurityAndDietQ2 = foodSecurityAndDietQ2;
    }

    public String getFoodSecurityAndDietQ3() {
        return foodSecurityAndDietQ3;
    }

    public void setFoodSecurityAndDietQ3(String foodSecurityAndDietQ3) {
        this.foodSecurityAndDietQ3 = foodSecurityAndDietQ3;
    }

    public String getFoodSecurityAndDietQ4() {
        return foodSecurityAndDietQ4;
    }

    public void setFoodSecurityAndDietQ4(String foodSecurityAndDietQ4) {
        this.foodSecurityAndDietQ4 = foodSecurityAndDietQ4;
    }

    public String getFoodSecurityAndDietQ5() {
        return foodSecurityAndDietQ5;
    }

    public void setFoodSecurityAndDietQ5(String foodSecurityAndDietQ5) {
        this.foodSecurityAndDietQ5 = foodSecurityAndDietQ5;
    }

    public String getFoodSecurityAndDietQ6() {
        return foodSecurityAndDietQ6;
    }

    public void setFoodSecurityAndDietQ6(String foodSecurityAndDietQ6) {
        this.foodSecurityAndDietQ6 = foodSecurityAndDietQ6;
    }

    public String getFoodSecurityAndDietQ7() {
        return foodSecurityAndDietQ7;
    }

    public void setFoodSecurityAndDietQ7(String foodSecurityAndDietQ7) {
        this.foodSecurityAndDietQ7 = foodSecurityAndDietQ7;
    }

    public String getFoodSecurityAndDietQ8() {
        return foodSecurityAndDietQ8;
    }

    public void setFoodSecurityAndDietQ8(String foodSecurityAndDietQ8) {
        this.foodSecurityAndDietQ8 = foodSecurityAndDietQ8;
    }

    public String getFoodSecurityAndDietQ9() {
        return foodSecurityAndDietQ9;
    }

    public void setFoodSecurityAndDietQ9(String foodSecurityAndDietQ9) {
        this.foodSecurityAndDietQ9 = foodSecurityAndDietQ9;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getHygieneQ1() {
        return hygieneQ1;
    }

    public void setHygieneQ1(String hygieneQ1) {
        this.hygieneQ1 = hygieneQ1;
    }

    public String getHygieneQ2() {
        return hygieneQ2;
    }

    public void setHygieneQ2(String hygieneQ2) {
        this.hygieneQ2 = hygieneQ2;
    }

    public String getHygieneQ3() {
        return hygieneQ3;
    }

    public void setHygieneQ3(String hygieneQ3) {
        this.hygieneQ3 = hygieneQ3;
    }

    public String getHygieneQ4() {
        return hygieneQ4;
    }

    public void setHygieneQ4(String hygieneQ4) {
        this.hygieneQ4 = hygieneQ4;
    }

    public double getLastWeight() {
        return lastWeight;
    }

    public void setLastWeight(double lastWeight) {
        this.lastWeight = lastWeight;
    }

    public String getLgaCode() {
        return lgaCode;
    }

    public void setLgaCode(String lgaCode) {
        this.lgaCode = lgaCode;
    }

    public String getMuac() {
        return muac;
    }

    public void setMuac(String muac) {
        this.muac = muac;
    }

    public String getNutritionalStatus() {
        return nutritionalStatus;
    }

    public void setNutritionalStatus(String nutritionalStatus) {
        this.nutritionalStatus = nutritionalStatus;
    }

    public String getOedema() {
        return oedema;
    }

    public void setOedema(String oedema) {
        this.oedema = oedema;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public int getOvcAge() {
        return ovcAge;
    }

    public void setOvcAge(int ovcAge) {
        this.ovcAge = ovcAge;
    }

    public String getAgeUnit() {
        return ageUnit;
    }

    public void setAgeUnit(String ageUnit) {
        this.ageUnit = ageUnit;
    }

    public String getOvcGender() {
        return ovcGender;
    }

    public void setOvcGender(String ovcGender) {
        this.ovcGender = ovcGender;
    }

    public String getOvcId() {
        return ovcId;
    }

    public void setOvcId(String ovcId) {
        this.ovcId = ovcId;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getMuacFacility() {
        return muacFacility;
    }

    public void setMuacFacility(String muacFacility) {
        this.muacFacility = muacFacility;
    }

    public String[] getYellowMuacServices() {
        return yellowMuacServices;
    }

    public void setYellowMuacServices(String[] yellowMuacServices) {
        this.yellowMuacServices = yellowMuacServices;
    }

    public List getReferralDirectoryList() {
        return referralDirectoryList;
    }

    public void setReferralDirectoryList(List referralDirectoryList) {
        this.referralDirectoryList = referralDirectoryList;
    }
    
    public boolean ovcAssessed()
    {
        if((getBmi()==0.0 && getChangeInWeight()==null)&&
        (getFoodSecurityAndDietQ1()==null && getFoodSecurityAndDietQ2()==null)&& 
        (getFoodSecurityAndDietQ3()==null && getFoodSecurityAndDietQ4()==null)&& 
        (getFoodSecurityAndDietQ5()==null && getFoodSecurityAndDietQ6()==null)&& 
        (getFoodSecurityAndDietQ7()==null && getFoodSecurityAndDietQ8()==null)&&
        (getFoodSecurityAndDietQ9()==null && getHeight()==0.0) &&
        (getHygieneQ1()==null && getHygieneQ2()==null) &&
        (getHygieneQ3()==null && getHygieneQ4()==null && this.getMuac()==null) &&
        (getNutritionalStatus()==null && getOedema()==null && getWeight()==0.0))
        
        return false;
        return true;
    }
    @Override
public void reset(ActionMapping mapping, HttpServletRequest request)
{
    //stateCode=null;
    //lgaCode=null;
    //orgCode=null;
    //communityCode=null;
    //hhSerialNo=null;
    //hhUniqueId=null;
    actionName=null;
    dateOfAssessment=null;
    dateOfEnrollment=null;
    id=0;
    ovcId=null;
    ovcGender=null;
    ovcAge=0;
    ageUnit=null;
    weight=0;
    lastWeight=0;
    height=0;
    assessmentNo=0;
    dateOfLastWeight=null;
    changeInWeight=null;
    oedema=null;
    muac=null;
    bmi=0.0;
    nutritionalStatus=null;
    foodSecurityAndDietQ1=null;
    foodSecurityAndDietQ2=null;
    foodSecurityAndDietQ3=null;
    foodSecurityAndDietQ4=null;
    foodSecurityAndDietQ5=null;
    foodSecurityAndDietQ6=null;
    foodSecurityAndDietQ7=null;
    foodSecurityAndDietQ8=null;
    foodSecurityAndDietQ9=null;
    hygieneQ1=null;
    hygieneQ2=null;
    hygieneQ3=null;
    hygieneQ4=null;
    muacFacility=null;
    yellowMuacServices=null;
    /*medicalEvaluationHIV=null;
    medicalEvaluationDiarrhea=null;
    medicalEvaluationNausea=null;
    medicalEvaluationPainfulChewing=null;
    medicalEvaluationVomiting=null;
    medicalEvaluationPoorApetite=null;
    medicalEvaluationMouthSoars=null;*/
}
    
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(getActionName()==null || getActionName().equalsIgnoreCase("cboList")|| getActionName().equalsIgnoreCase("cbo")|| getActionName().equalsIgnoreCase("hhDetails") || getActionName().equalsIgnoreCase("baselineDetails") || getActionName().equalsIgnoreCase("assessmentDetails")|| getActionName().equalsIgnoreCase("delete"))
        return errors; 
        else if(getActionName().equalsIgnoreCase("save") || getActionName().equalsIgnoreCase("modify"))
        {
            if(getOvcId()==null || getOvcId().length() < 1)
            errors.add("ovcId", new ActionMessage("errors.ovcId.null"));
            else if(getDateOfAssessment()==null || getDateOfAssessment().length() < 1 )
            errors.add("dateOfAssessment", new ActionMessage("errors.dateOfAssessment.required"));
            else if(getDateOfAssessment().indexOf("/") == -1 && getDateOfAssessment().indexOf("-") ==-1 )
            errors.add("dateOfAssessment", new ActionMessage("errors.dateOfAssessment.required"));
            else if(!ovcAssessed())
            errors.add("ovcAssessment", new ActionMessage("errors.ovcNotAssessed"));
        }
        return errors;
    }
}
