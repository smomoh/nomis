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
public class Lgas implements Serializable
{
    private String state_code;
    private String lga_code;
    private String lga_name;
    private String autogenerated;
    public Lgas()
    {

    }
    public String getLga_code() {
        return lga_code;
    }

    public void setLga_code(String lga_code) {
        this.lga_code = lga_code;
    }

    public String getLga_name() {
        return lga_name;
    }

    public void setLga_name(String lga_name) {
        this.lga_name = lga_name;
    }

   public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getAutogenerated() {
        return autogenerated;
    }

    public void setAutogenerated(String autogenerated) {
        this.autogenerated = autogenerated;
    }
    
}
