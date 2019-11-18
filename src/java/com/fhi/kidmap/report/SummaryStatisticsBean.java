/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class SummaryStatisticsBean implements Serializable {
    private String stateCode;
    private String lgaCode;
    private String cboCode;
    private String state;
    private String lga;
    private String cbo;
    private String indicatorId;
    private String indicatorName;
    private String categoryOptionCombo;
    private String gender;
    private int totNoOfOvc;
    private int noOfOvc;
    private int maleValue;
    private int femaleValue;
    private int value3;
    private int value4;
    private int value5;
    private int value6;
    private int value7;
    private int value8;
    private String month;
    private String year;
    private String date;
    private String period;
    private String dataset;

    public String getCbo() {
        return cbo;
    }

    public void setCbo(String cbo) {
        this.cbo = cbo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getCategoryOptionCombo() {
        return categoryOptionCombo;
    }

    public void setCategoryOptionCombo(String categoryOptionCombo) {
        this.categoryOptionCombo = categoryOptionCombo;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public int getFemaleValue() {
        return femaleValue;
    }

    public void setFemaleValue(int femaleValue) {
        this.femaleValue = femaleValue;
    }

    public int getMaleValue() {
        return maleValue;
    }

    public void setMaleValue(int maleValue) {
        this.maleValue = maleValue;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getNoOfOvc() {
        return noOfOvc;
    }

    public int getValue3() {
        return value3;
    }

    public void setValue3(int value3) {
        this.value3 = value3;
    }

    public int getValue4() {
        return value4;
    }

    public void setValue4(int value4) {
        this.value4 = value4;
    }

    public int getValue5() {
        return value5;
    }

    public void setValue5(int value5) {
        this.value5 = value5;
    }

    public int getValue6() {
        return value6;
    }

    public void setValue6(int value6) {
        this.value6 = value6;
    }

    public int getValue7() {
        return value7;
    }

    public void setValue7(int value7) {
        this.value7 = value7;
    }

    public int getValue8() {
        return value8;
    }

    public void setValue8(int value8) {
        this.value8 = value8;
    }

    public void setNoOfOvc(int noOfOvc) {
        this.noOfOvc = noOfOvc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getLgaCode() {
        return lgaCode;
    }

    public void setLgaCode(String lgaCode) {
        this.lgaCode = lgaCode;
    }

    public String getCboCode() {
        return cboCode;
    }

    public void setCboCode(String cboCode) {
        this.cboCode = cboCode;
    }

    public String getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(String indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public int getTotNoOfOvc() {
        return totNoOfOvc;
    }

    public void setTotNoOfOvc(int totNoOfOvc) {
        this.totNoOfOvc = totNoOfOvc;
    }
}
