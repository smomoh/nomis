/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.business;

import java.io.Serializable;

/**
 *
 * @author HP USER
 */
public class Cbo implements Serializable {

    public Cbo() {

    }


    private String cboCode;
    private String cboName;
    private String address;
    private String stateCode;
    private String lgaCode;
    private String contactMobile;
    private String fax;
    private String email;
    private String website;



public String getCboName() {
return cboName;
}
public void setCboName(String cboName) {
this.cboName = cboName;
}

public String getAddress() {
return address;
}
public void setAddress(String address) {
this.address = address;
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

public String getContactMobile() {
return contactMobile;
}
public void setContactMobile(String contactMobile) {
this.contactMobile = contactMobile;
}

public String getFax() {
return fax;
}
public void setFax(String fax) {
this.fax = fax;
}

public String getEmail() {
return email;
}
public void setEmail(String email) {
this.email = email;
}

public String getWebsite() {
return website;
}
public void setWebsite(String website) {
this.website = website;
}


}
