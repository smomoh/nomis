/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.nomisutils;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class Service implements Serializable
{
    private String beneciaryType;
    private String domainName;
    private String serviceCode;
    private String serviceName;
    private String serviceValue;

    public String getBeneciaryType() {
        return beneciaryType;
    }

    public void setBeneciaryType(String beneciaryType) {
        this.beneciaryType = beneciaryType;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceValue() {
        return serviceValue;
    }

    public void setServiceValue(String serviceValue) {
        this.serviceValue = serviceValue;
    }
    
}
