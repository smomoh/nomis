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
public class Lga implements Serializable {

    public Lga() {

    }


    private String lgaCode;
    private String lgaName;
    private String stateCode;
   


public String getLgaCode() {
return lgaCode;
}
public void setLgaCode(String lgaCode) {
this.lgaCode = lgaCode;
}

public String getStateCode() {
return stateCode;
}
public void setStateCode(String stateCode) {
this.stateCode = stateCode;
}

public String getLgaName() {
return lgaName;
}
public void setLgaName(String lgaName) {
this.lgaName = lgaName;
}


}
