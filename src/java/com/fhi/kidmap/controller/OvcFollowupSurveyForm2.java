/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author HP USER
 */
public class OvcFollowupSurveyForm2 extends ActionForm {

    public OvcFollowupSurveyForm2() {

    }



private String state;
private String lga;
private String ward;
private String dateEnrollment;
private String ovcId;
private String surname;
private String otherNames1;
private String otherNames2;
private String gender;
private int age;
private String ageUnit;
private String address;
private int csiFactor1;
private int csiFactor2;
private int csiFactor3;
private int csiFactor4;
private int csiFactor5;
private int csiFactor6;
private int csiFactor7;
private int csiFactor8;
private int csiFactor9;
private int csiFactor10;
private int csiFactor11;
private int csiFactor12;
private String dateInfoUpdated;
private String addressUpdate;
private int ageUpdate;
private String ageUnitUpdate;
private String hivStatus;
private String birthCertificate;
private String schoolStatus;
private String caregiverName1;
private String caregiverName2;
private String caregiverGender;
private int caregiverAge;
private String caregiverAddress;
private String caregiverPhone;
private String caregiverOccupation;
private String caregiverRelationships;
private String caregiverRelationships2;
private String reasonWithdrawal;



private String serviceAccessed11;
private String serviceAccessed12;
private String serviceAccessed13;
private String serviceAccessed14;



private String serviceAccessed21;
private String serviceAccessed22;
private String serviceAccessed23;
private String serviceAccessed24;



private String serviceAccessed31;
private String serviceAccessed32;
private String serviceAccessed33;
private String serviceAccessed34;
private String serviceAccessed35;
private String serviceAccessed36;
private String serviceAccessed37;



private String serviceAccessed41;
private String serviceAccessed42;
private String serviceAccessed43;
private String serviceAccessed44;
private String serviceAccessed45;
private String serviceAccessed46;



private String serviceAccessed51;
private String serviceAccessed52;
private String serviceAccessed53;
private String serviceAccessed54;



private String serviceAccessed61;
private String serviceAccessed62;
private String serviceAccessed63;
private String serviceAccessed64;
private String serviceAccessed65;



private String serviceAccessed71;
private String serviceAccessed72;
private String serviceAccessed73;
private String serviceAccessed74;
private String serviceAccessed75;



private String dateOfSurvey;
private String completedbyName1;
private String completedbyName2;
private String completedbyDesignation;
private String completedbyCbo;



private int followupId;
private int childStatusIndexId;
private int ovcServiceId;
private int ovcWithdrawalId;
private int childUpdateId;






public String getState() {
return state;
}
public void setState(String state) {
this.state = state;
}

public String getLga() {
return lga;
}
public void setLga(String lga) {
this.lga = lga;
}

public String getWard() {
return ward;
}
public void setWard(String ward) {
this.ward = ward;
}

public String getDateEnrollment() {
return dateEnrollment;
}
public void setDateEnrollment(String dateEnrollment) {
this.dateEnrollment = dateEnrollment;
}

public String getOvcId() {
return ovcId;
}
public void setOvcId(String ovcId) {
this.ovcId = ovcId;
}

public String getSurname() {
return surname;
}
public void setSurname(String surname) {
this.surname = surname;
}

public String getOtherNames1() {
return otherNames1;
}
public void setOtherNames1(String otherNames1) {
this.otherNames1 = otherNames1;
}

public String getOtherNames2() {
return otherNames2;
}
public void setOtherNames2(String otherNames2) {
this.otherNames2 = otherNames2;
}

public String getGender() {
return gender;
}
public void setGender(String gender) {
this.gender = gender;
}

public int getAge() {
return age;
}
public void setAge(int age) {
this.age = age;
}

public String getAgeUnit() {
return ageUnit;
}
public void setAgeUnit(String ageUnit) {
this.ageUnit = ageUnit;
}

public String getAddress() {
return address;
}
public void setAddress(String address) {
this.address = address;
}

public int getCsiFactor1() {
return csiFactor1;
}
public void setCsiFactor1(int csiFactor1) {
this.csiFactor1 = csiFactor1;
}

public int getCsiFactor2() {
return csiFactor2;
}
public void setCsiFactor2(int csiFactor2) {
this.csiFactor2 = csiFactor2;
}

public int getCsiFactor3() {
return csiFactor3;
}
public void setCsiFactor3(int csiFactor3) {
this.csiFactor3 = csiFactor3;
}

public int getCsiFactor4() {
return csiFactor4;
}
public void setCsiFactor4(int csiFactor4) {
this.csiFactor4 = csiFactor4;
}

public int getCsiFactor5() {
return csiFactor5;
}
public void setCsiFactor5(int csiFactor5) {
this.csiFactor5 = csiFactor5;
}

public int getCsiFactor6() {
return csiFactor6;
}
public void setCsiFactor6(int csiFactor6) {
this.csiFactor6 = csiFactor6;
}

public int getCsiFactor7() {
return csiFactor7;
}
public void setCsiFactor7(int csiFactor7) {
this.csiFactor7 = csiFactor7;
}

public int getCsiFactor8() {
return csiFactor8;
}
public void setCsiFactor8(int csiFactor8) {
this.csiFactor8 = csiFactor8;
}

public int getCsiFactor9() {
return csiFactor9;
}
public void setCsiFactor9(int csiFactor9) {
this.csiFactor9 = csiFactor9;
}

public int getCsiFactor10() {
return csiFactor10;
}
public void setCsiFactor10(int csiFactor10) {
this.csiFactor10 = csiFactor10;
}

public int getCsiFactor11() {
return csiFactor11;
}
public void setCsiFactor11(int csiFactor11) {
this.csiFactor11 = csiFactor11;
}

public int getCsiFactor12() {
return csiFactor12;
}
public void setCsiFactor12(int csiFactor12) {
this.csiFactor12 = csiFactor12;
}

public String getDateInfoUpdated() {
return dateInfoUpdated;
}
public void setDateInfoUpdated(String dateInfoUpdated) {
this.dateInfoUpdated = dateInfoUpdated;
}

public String getAddressUpdate() {
return addressUpdate;
}
public void setAddressUpdate(String addressUpdate) {
this.addressUpdate = addressUpdate;
}

public int getAgeUpdate() {
return ageUpdate;
}
public void setAgeUpdate(int ageUpdate) {
this.ageUpdate = ageUpdate;
}

public String getAgeUnitUpdate() {
return ageUnitUpdate;
}
public void setAgeUnitUpdate(String ageUnitUpdate) {
this.ageUnitUpdate = ageUnitUpdate;
}

public String getHivStatus() {
return hivStatus;
}
public void setHivStatus(String hivStatus) {
this.hivStatus = hivStatus;
}

public String getBirthCertificate() {
return birthCertificate;
}
public void setBirthCertificate(String birthCertificate) {
this.birthCertificate = birthCertificate;
}

public String getSchoolStatus() {
return schoolStatus;
}
public void setSchoolStatus(String schoolStatus) {
this.schoolStatus = schoolStatus;
}

public String getCaregiverName1() {
return caregiverName1;
}
public void setCaregiverName1(String caregiverName1) {
this.caregiverName1 = caregiverName1;
}

public String getCaregiverName2() {
return caregiverName2;
}
public void setCaregiverName2(String caregiverName2) {
this.caregiverName2 = caregiverName2;
}

public String getCaregiverGender() {
return caregiverGender;
}
public void setCaregiverGender(String caregiverGender) {
this.caregiverGender = caregiverGender;
}

public int getCaregiverAge() {
return caregiverAge;
}
public void setCaregiverAge(int caregiverAge) {
this.caregiverAge = caregiverAge;
}

public String getCaregiverAddress() {
return caregiverAddress;
}
public void setCaregiverAddress(String caregiverAddress) {
this.caregiverAddress = caregiverAddress;
}

public String getCaregiverPhone() {
return caregiverPhone;
}
public void setCaregiverPhone(String caregiverPhone) {
this.caregiverPhone = caregiverPhone;
}

public String getCaregiverOccupation() {
return caregiverOccupation;
}
public void setCaregiverOccupation(String caregiverOccupation) {
this.caregiverOccupation = caregiverOccupation;
}

public String getCaregiverRelationships() {
return caregiverRelationships;
}
public void setCaregiverRelationships(String caregiverRelationships) {
this.caregiverRelationships = caregiverRelationships;
}

public String getCaregiverRelationships2() {
return caregiverRelationships2;
}
public void setcaregiverRelationships2(String caregiverRelationships2) {
this.caregiverRelationships2 = caregiverRelationships2;
}

public String getReasonWithdrawal() {
return reasonWithdrawal;
}
public void setReasonWithdrawal(String reasonWithdrawal) {
this.reasonWithdrawal = reasonWithdrawal;
}





public String getServiceAccessed11() {
return serviceAccessed11;
}
public void setServiceAccessed11(String serviceAccessed11) {
this.serviceAccessed11 = serviceAccessed11;
}

public String getServiceAccessed12() {
return serviceAccessed12;
}
public void setServiceAccessed12(String serviceAccessed12) {
this.serviceAccessed12 = serviceAccessed12;
}

public String getServiceAccessed13() {
return serviceAccessed13;
}
public void setServiceAccessed13(String serviceAccessed13) {
this.serviceAccessed13 = serviceAccessed13;
}

public String getServiceAccessed14() {
return serviceAccessed14;
}
public void setServiceAccessed14(String serviceAccessed14) {
this.serviceAccessed14 = serviceAccessed14;
}




public String getServiceAccessed21() {
return serviceAccessed21;
}
public void setServiceAccessed21(String serviceAccessed21) {
this.serviceAccessed21 = serviceAccessed21;
}

public String getServiceAccessed22() {
return serviceAccessed22;
}
public void setServiceAccessed22(String serviceAccessed22) {
this.serviceAccessed22 = serviceAccessed22;
}

public String getServiceAccessed23() {
return serviceAccessed23;
}
public void setServiceAccessed23(String serviceAccessed23) {
this.serviceAccessed23 = serviceAccessed23;
}

public String getServiceAccessed24() {
return serviceAccessed24;
}
public void setServiceAccessed24(String serviceAccessed24) {
this.serviceAccessed24 = serviceAccessed24;
}




public String getServiceAccessed31() {
return serviceAccessed31;
}
public void setServiceAccessed31(String serviceAccessed31) {
this.serviceAccessed31 = serviceAccessed31;
}

public String getServiceAccessed32() {
return serviceAccessed32;
}
public void setServiceAccessed32(String serviceAccessed32) {
this.serviceAccessed32 = serviceAccessed32;
}

public String getServiceAccessed33() {
return serviceAccessed33;
}
public void setServiceAccessed33(String serviceAccessed33) {
this.serviceAccessed33 = serviceAccessed33;
}

public String getServiceAccessed34() {
return serviceAccessed34;
}
public void setServiceAccessed34(String serviceAccessed34) {
this.serviceAccessed34 = serviceAccessed34;
}

public String getServiceAccessed35() {
return serviceAccessed35;
}
public void setServiceAccessed35(String serviceAccessed35) {
this.serviceAccessed35 = serviceAccessed35;
}

public String getServiceAccessed36() {
return serviceAccessed36;
}
public void setServiceAccessed36(String serviceAccessed36) {
this.serviceAccessed36 = serviceAccessed36;
}

public String getServiceAccessed37() {
return serviceAccessed37;
}
public void setServiceAccessed37(String serviceAccessed37) {
this.serviceAccessed37 = serviceAccessed37;
}




public String getServiceAccessed41() {
return serviceAccessed41;
}
public void setServiceAccessed41(String serviceAccessed41) {
this.serviceAccessed41 = serviceAccessed41;
}

public String getServiceAccessed42() {
return serviceAccessed42;
}
public void setServiceAccessed42(String serviceAccessed42) {
this.serviceAccessed42 = serviceAccessed42;
}

public String getServiceAccessed43() {
return serviceAccessed43;
}
public void setServiceAccessed43(String serviceAccessed43) {
this.serviceAccessed43 = serviceAccessed43;
}

public String getServiceAccessed44() {
return serviceAccessed44;
}
public void setServiceAccessed44(String serviceAccessed44) {
this.serviceAccessed44 = serviceAccessed44;
}

public String getServiceAccessed45() {
return serviceAccessed45;
}
public void setServiceAccessed45(String serviceAccessed45) {
this.serviceAccessed45 = serviceAccessed45;
}

public String getServiceAccessed46() {
return serviceAccessed46;
}
public void setServiceAccessed46(String serviceAccessed46) {
this.serviceAccessed46 = serviceAccessed46;
}




public String getServiceAccessed51() {
return serviceAccessed51;
}
public void setServiceAccessed51(String serviceAccessed51) {
this.serviceAccessed51 = serviceAccessed51;
}

public String getServiceAccessed52() {
return serviceAccessed52;
}
public void setServiceAccessed52(String serviceAccessed52) {
this.serviceAccessed52 = serviceAccessed52;
}

public String getServiceAccessed53() {
return serviceAccessed53;
}
public void setServiceAccessed53(String serviceAccessed53) {
this.serviceAccessed53 = serviceAccessed53;
}

public String getServiceAccessed54() {
return serviceAccessed54;
}
public void setServiceAccessed54(String serviceAccessed54) {
this.serviceAccessed54 = serviceAccessed54;
}




public String getServiceAccessed61() {
return serviceAccessed61;
}
public void setServiceAccessed61(String serviceAccessed61) {
this.serviceAccessed61 = serviceAccessed61;
}

public String getServiceAccessed62() {
return serviceAccessed62;
}
public void setServiceAccessed62(String serviceAccessed62) {
this.serviceAccessed62 = serviceAccessed62;
}

public String getServiceAccessed63() {
return serviceAccessed63;
}
public void setServiceAccessed63(String serviceAccessed63) {
this.serviceAccessed63 = serviceAccessed63;
}

public String getServiceAccessed64() {
return serviceAccessed64;
}
public void setServiceAccessed64(String serviceAccessed64) {
this.serviceAccessed64 = serviceAccessed64;
}

public String getServiceAccessed65() {
return serviceAccessed65;
}
public void setServiceAccessed65(String serviceAccessed65) {
this.serviceAccessed65 = serviceAccessed65;
}




public String getServiceAccessed71() {
return serviceAccessed71;
}
public void setServiceAccessed71(String serviceAccessed71) {
this.serviceAccessed71 = serviceAccessed71;
}

public String getServiceAccessed72() {
return serviceAccessed72;
}
public void setServiceAccessed72(String serviceAccessed72) {
this.serviceAccessed72 = serviceAccessed72;
}

public String getServiceAccessed73() {
return serviceAccessed73;
}
public void setServiceAccessed73(String serviceAccessed73) {
this.serviceAccessed73 = serviceAccessed73;
}

public String getServiceAccessed74() {
return serviceAccessed74;
}
public void setServiceAccessed74(String serviceAccessed74) {
this.serviceAccessed74 = serviceAccessed74;
}

public String getServiceAccessed75() {
return serviceAccessed75;
}
public void setServiceAccessed75(String serviceAccessed75) {
this.serviceAccessed75 = serviceAccessed75;
}




public String getDateOfSurvey() {
return dateOfSurvey;
}
public void setDateOfSurvey(String dateOfSurvey) {
this.dateOfSurvey = dateOfSurvey;
}

public String getCompletedbyName1() {
return completedbyName1;
}
public void setCompletedbyName1(String completedbyName1) {
this.completedbyName1 = completedbyName1;
}

public String getCompletedbyName2() {
return completedbyName2;
}
public void setCompletedbyName2(String completedbyName2) {
this.completedbyName2 = completedbyName2;
}

public String getCompletedbyDesignation() {
return completedbyDesignation;
}
public void setCompletedbyDesignation(String completedbyDesignation) {
this.completedbyDesignation = completedbyDesignation;
}

public String getCompletedbyCbo() {
return completedbyCbo;
}
public void setCompletedbyCbo(String completedbyCbo) {
this.completedbyCbo = completedbyCbo;
}






public int getFollowupId() {
return followupId;
}
public void setFollowupId(int followupId) {
this.followupId = followupId;
}

public int getChildStatusIndexId() {
return childStatusIndexId;
}
public void setChildStatusIndexId(int childStatusIndexId) {
this.childStatusIndexId = childStatusIndexId;
}

public int getOvcServiceId() {
return ovcServiceId;
}
public void setOvcServiceId(int ovcServiceId) {
this.ovcServiceId = ovcServiceId;
}

public int getOvcWithdrawalId() {
return ovcWithdrawalId;
}
public void setOvcWithdrawalId(int ovcWithdrawalId) {
this.ovcWithdrawalId = ovcWithdrawalId;
}

public int getChildUpdateId() {
return childUpdateId;
}
public void setChildUpdateId(int childUpdateId) {
this.childUpdateId = childUpdateId;
}







    @Override
public void reset(ActionMapping mapping,
HttpServletRequest request) {


this.state = null;
this.lga = null;
this.ward = null;
this.dateEnrollment = null;
this.ovcId = null;
this.surname = null;
this.otherNames1 = null;
this.otherNames2 = null;
this.gender = null;
this.age = 0;
this.ageUnit = null;
this.address = null;
this.csiFactor1 = 0;
this.csiFactor2 = 0;
this.csiFactor3 = 0;
this.csiFactor4 = 0;
this.csiFactor5 = 0;
this.csiFactor6 = 0;
this.csiFactor7 = 0;
this.csiFactor8 = 0;
this.csiFactor9 = 0;
this.csiFactor10 = 0;
this.csiFactor11 = 0;
this.csiFactor12 = 0;
this.dateInfoUpdated = null;
this.addressUpdate = null;
this.ageUpdate = 0;
this.ageUnitUpdate = null;
this.hivStatus = null;
this.birthCertificate = null;
this.schoolStatus = null;
this.caregiverName1 = null;
this.caregiverName2 = null;
this.caregiverGender = null;
this.caregiverAge = 0;
this.caregiverAddress = null;
this.caregiverPhone = null;
this.caregiverOccupation = null;
this.caregiverRelationships = null;
this.caregiverRelationships2 = null;
this.reasonWithdrawal = null;



this.serviceAccessed11 = null;
this.serviceAccessed12 = null;
this.serviceAccessed13 = null;
this.serviceAccessed14 = null;



this.serviceAccessed21 = null;
this.serviceAccessed22 = null;
this.serviceAccessed23 = null;
this.serviceAccessed24 = null;



this.serviceAccessed31 = null;
this.serviceAccessed32 = null;
this.serviceAccessed33 = null;
this.serviceAccessed34 = null;
this.serviceAccessed35 = null;
this.serviceAccessed36 = null;
this.serviceAccessed37 = null;



this.serviceAccessed41 = null;
this.serviceAccessed42 = null;
this.serviceAccessed43 = null;
this.serviceAccessed44 = null;
this.serviceAccessed45 = null;
this.serviceAccessed46 = null;



this.serviceAccessed51 = null;
this.serviceAccessed52 = null;
this.serviceAccessed53 = null;
this.serviceAccessed54 = null;



this.serviceAccessed61 = null;
this.serviceAccessed62 = null;
this.serviceAccessed63 = null;
this.serviceAccessed64 = null;
this.serviceAccessed65 = null;



this.serviceAccessed71 = null;
this.serviceAccessed72 = null;
this.serviceAccessed73 = null;
this.serviceAccessed74 = null;
this.serviceAccessed75 = null;



this.dateOfSurvey = null;
this.completedbyName1 = null;
this.completedbyName2 = null;
this.completedbyDesignation = null;
this.completedbyCbo = null;





this.followupId = 0;
this.childStatusIndexId = 0;
this.ovcServiceId = 0;
this.ovcWithdrawalId = 0;
this.childUpdateId = 0;


}




}
