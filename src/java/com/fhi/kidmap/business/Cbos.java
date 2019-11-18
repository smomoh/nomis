/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.business;

import java.io.Serializable;

/**
 *
 * @author COMPAQ USER
 */
public class Cbos implements Serializable
{
    //private String state_code;
    private String lga_code;
    private String cbo_code;
    private String cbo_name;
    private String address;
    private String phone;
    private String email;
    private String website;
    private String fax;

    public Cbos()
    {
        
    }

    public String getLga_code() {
        return lga_code;
    }

    public void setLga_code(String lga_code) {
        this.lga_code = lga_code;
    }

    public String getCbo_code() {
        return cbo_code;
    }

    public void setCbo_code(String cbo_code) {
        this.cbo_code = cbo_code;
    }

    public String getCbo_name() {
        return cbo_name;
    }

    public void setCbo_name(String cbo_name) {
        this.cbo_name = cbo_name;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    /*public String getLga_code() {
        return lga_code;
    }

    public void setLga_code(String lga_code) {
        this.lga_code = lga_code;
    }*/
    /*public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }*/

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
