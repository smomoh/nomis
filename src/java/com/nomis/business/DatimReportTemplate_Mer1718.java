/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.business;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author smomoh
 */
public class DatimReportTemplate_Mer1718 implements Serializable
{
    private int recordId;
    private String state;
    private String lga;
    private String cbo;
    private String ward;
    private String partnerCode;
    private String mainDataElementName;
    private String subDataElementName;
    private int hiv_statNumerator;
    private int totalPositive;
    private int enrolledOnART;
    private int notEnrolledOnART;
    private int totalNegative;
    private int totalUnknown;
    private int testNotIndicated;
    private int otherReasons;
    private int ovc_servNumerator;
    private int ovc_servActive;
    private int ovc_servGraduated;
    private int ovc_servTransfered;
    private int ovc_servExitedWithoutGraduation;
    private int ovc_servLessThan1;
    private int ovc_serv1To4;
    private int ovc_serv5To9;
    private int ovc_serv1To9;
    private int ovc_servMale10To14;
    private int ovc_servFemale10To14;
    private int ovc_servMale15To17;
    private int ovc_servFemale15To17;
    private int ovc_servMale18To24;
    private int ovc_servFemale18To24;
    private int ovc_servMale25AndAbove;
    private int ovc_servFemale25AndAbove;
    
    private String startMth;
    private String startYr;
    private String endMth;
    private String endYr;
    private String period;
    private String partnerName;
    private Date dateCreated;
    private Date lastModifiedDate;
    private String recordedBy;

    public String getCbo() {
        return cbo;
    }

    public void setCbo(String cbo) {
        this.cbo = cbo;
    }

    public int getEnrolledOnART() {
        return enrolledOnART;
    }

    public void setEnrolledOnART(int enrolledOnART) {
        this.enrolledOnART = enrolledOnART;
    }

    public int getHiv_statNumerator() {
        return hiv_statNumerator;
    }

    public void setHiv_statNumerator(int hiv_statNumerator) {
        this.hiv_statNumerator = hiv_statNumerator;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public String getMainDataElementName() {
        return mainDataElementName;
    }

    public void setMainDataElementName(String mainDataElementName) {
        this.mainDataElementName = mainDataElementName;
    }

    public int getNotEnrolledOnART() {
        return notEnrolledOnART;
    }

    public void setNotEnrolledOnART(int notEnrolledOnART) {
        this.notEnrolledOnART = notEnrolledOnART;
    }

    public int getOtherReasons() {
        return otherReasons;
    }

    public void setOtherReasons(int otherReasons) {
        this.otherReasons = otherReasons;
    }

    public int getOvc_serv1To9() {
        return ovc_serv1To9;
    }

    public void setOvc_serv1To9(int ovc_serv1To9) {
        this.ovc_serv1To9 = ovc_serv1To9;
    }

    public int getOvc_servActive() {
        return ovc_servActive;
    }

    public void setOvc_servActive(int ovc_servActive) {
        this.ovc_servActive = ovc_servActive;
    }

    public int getOvc_servExitedWithoutGraduation() {
        return ovc_servExitedWithoutGraduation;
    }

    public void setOvc_servExitedWithoutGraduation(int ovc_servExitedWithoutGraduation) {
        this.ovc_servExitedWithoutGraduation = ovc_servExitedWithoutGraduation;
    }

    public int getOvc_servFemale10To14() {
        return ovc_servFemale10To14;
    }

    public void setOvc_servFemale10To14(int ovc_servFemale10To14) {
        this.ovc_servFemale10To14 = ovc_servFemale10To14;
    }

    public int getOvc_servFemale15To17() {
        return ovc_servFemale15To17;
    }

    public void setOvc_servFemale15To17(int ovc_servFemale15To17) {
        this.ovc_servFemale15To17 = ovc_servFemale15To17;
    }

    public int getOvc_servFemale18To24() {
        return ovc_servFemale18To24;
    }

    public void setOvc_servFemale18To24(int ovc_servFemale18To24) {
        this.ovc_servFemale18To24 = ovc_servFemale18To24;
    }

    public int getOvc_servFemale25AndAbove() {
        return ovc_servFemale25AndAbove;
    }

    public void setOvc_servFemale25AndAbove(int ovc_servFemale25AndAbove) {
        this.ovc_servFemale25AndAbove = ovc_servFemale25AndAbove;
    }

    public int getOvc_servGraduated() {
        return ovc_servGraduated;
    }

    public void setOvc_servGraduated(int ovc_servGraduated) {
        this.ovc_servGraduated = ovc_servGraduated;
    }

    public int getOvc_servLessThan1() {
        return ovc_servLessThan1;
    }

    public void setOvc_servLessThan1(int ovc_servLessThan1) {
        this.ovc_servLessThan1 = ovc_servLessThan1;
    }

    public int getOvc_servMale10To14() {
        return ovc_servMale10To14;
    }

    public void setOvc_servMale10To14(int ovc_servMale10To14) {
        this.ovc_servMale10To14 = ovc_servMale10To14;
    }

    public int getOvc_servMale15To17() {
        return ovc_servMale15To17;
    }

    public void setOvc_servMale15To17(int ovc_servMale15To17) {
        this.ovc_servMale15To17 = ovc_servMale15To17;
    }

    public int getOvc_servMale18To24() {
        return ovc_servMale18To24;
    }

    public void setOvc_servMale18To24(int ovc_servMale18To24) {
        this.ovc_servMale18To24 = ovc_servMale18To24;
    }

    public int getOvc_servMale25AndAbove() {
        return ovc_servMale25AndAbove;
    }

    public void setOvc_servMale25AndAbove(int ovc_servMale25AndAbove) {
        this.ovc_servMale25AndAbove = ovc_servMale25AndAbove;
    }

    
    public int getOvc_servNumerator() {
        return ovc_servNumerator;
    }

    public void setOvc_servNumerator(int ovc_servNumerator) {
        this.ovc_servNumerator = ovc_servNumerator;
    }

    public int getOvc_servTransfered() {
        return ovc_servTransfered;
    }

    public void setOvc_servTransfered(int ovc_servTransfered) {
        this.ovc_servTransfered = ovc_servTransfered;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSubDataElementName() {
        return subDataElementName;
    }

    public void setSubDataElementName(String subDataElementName) {
        this.subDataElementName = subDataElementName;
    }

    public int getTestNotIndicated() {
        return testNotIndicated;
    }

    public void setTestNotIndicated(int testNotIndicated) {
        this.testNotIndicated = testNotIndicated;
    }

    public int getTotalNegative() {
        return totalNegative;
    }

    public void setTotalNegative(int totalNegative) {
        this.totalNegative = totalNegative;
    }

    public int getTotalPositive() {
        return totalPositive;
    }

    public void setTotalPositive(int totalPositive) {
        this.totalPositive = totalPositive;
    }

    public int getTotalUnknown() {
        return totalUnknown;
    }

    public void setTotalUnknown(int totalUnknown) {
        this.totalUnknown = totalUnknown;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getEndMth() {
        return endMth;
    }

    public void setEndMth(String endMth) {
        this.endMth = endMth;
    }

    public String getEndYr() {
        return endYr;
    }

    public void setEndYr(String endYr) {
        this.endYr = endYr;
    }

    public String getStartMth() {
        return startMth;
    }

    public void setStartMth(String startMth) {
        this.startMth = startMth;
    }

    public String getStartYr() {
        return startYr;
    }

    public void setStartYr(String startYr) {
        this.startYr = startYr;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public int getOvc_serv1To4() {
        return ovc_serv1To4;
    }

    public void setOvc_serv1To4(int ovc_serv1To4) {
        this.ovc_serv1To4 = ovc_serv1To4;
    }

    public int getOvc_serv5To9() {
        return ovc_serv5To9;
    }

    public void setOvc_serv5To9(int ovc_serv5To9) {
        this.ovc_serv5To9 = ovc_serv5To9;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }
    
}
