/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

/**
 *
 * @author smomoh
 */
public class SpecificServiceReport 
{
    private int id;
    private String ovcId;
    private String servicedate;
    private String dateOfEntry;
    private String serviceAccessed1;
    private String serviceAccessed2;
    private String serviceAccessed3;
    private String serviceAccessed4;
    private String serviceAccessed5;
    private String serviceAccessed6;
    private String serviceAccessed7;
    private String servicesReferred;
    private double weight;
    private double height;
    private int surveyNumber;
    private int numberOfServices;
    private String providerName;

    public String getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(String dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfServices() {
        return numberOfServices;
    }

    public void setNumberOfServices(int numberOfServices) {
        this.numberOfServices = numberOfServices;
    }

    public String getOvcId() {
        return ovcId;
    }

    public void setOvcId(String ovcId) {
        this.ovcId = ovcId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getServiceAccessed1() {
        return serviceAccessed1;
    }

    public void setServiceAccessed1(String serviceAccessed1) {
        this.serviceAccessed1 = serviceAccessed1;
    }

    public String getServiceAccessed2() {
        return serviceAccessed2;
    }

    public void setServiceAccessed2(String serviceAccessed2) {
        this.serviceAccessed2 = serviceAccessed2;
    }

    public String getServiceAccessed3() {
        return serviceAccessed3;
    }

    public void setServiceAccessed3(String serviceAccessed3) {
        this.serviceAccessed3 = serviceAccessed3;
    }

    public String getServiceAccessed4() {
        return serviceAccessed4;
    }

    public void setServiceAccessed4(String serviceAccessed4) {
        this.serviceAccessed4 = serviceAccessed4;
    }

    public String getServiceAccessed5() {
        return serviceAccessed5;
    }

    public void setServiceAccessed5(String serviceAccessed5) {
        this.serviceAccessed5 = serviceAccessed5;
    }

    public String getServiceAccessed6() {
        return serviceAccessed6;
    }

    public void setServiceAccessed6(String serviceAccessed6) {
        this.serviceAccessed6 = serviceAccessed6;
    }

    public String getServiceAccessed7() {
        return serviceAccessed7;
    }

    public void setServiceAccessed7(String serviceAccessed7) {
        this.serviceAccessed7 = serviceAccessed7;
    }

    public String getServicedate() {
        return servicedate;
    }

    public void setServicedate(String servicedate) {
        this.servicedate = servicedate;
    }

    public String getServicesReferred() {
        return servicesReferred;
    }

    public void setServicesReferred(String servicesReferred) {
        this.servicesReferred = servicesReferred;
    }

    public int getSurveyNumber() {
        return surveyNumber;
    }

    public void setSurveyNumber(int surveyNumber) {
        this.surveyNumber = surveyNumber;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
