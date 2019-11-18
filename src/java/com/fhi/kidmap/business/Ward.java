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
public class Ward implements Serializable {

    public Ward() {

    }


    private String wardName;
    private String lgaCode;
    private String stateCode;
    private String wardCode;
    private String cboCode;


public String getWardName() {
return wardName;
}

public void setWardName(String wardName) {
this.wardName = wardName;
}

public void setLgaCode(String lgaCode) {
this.lgaCode = lgaCode;
}

public String getLgaCode() {
return lgaCode;
}

public void setStateCode(String stateCode) {
this.stateCode = stateCode;
}

public String getStateCode() {
return stateCode;
}

public String getWardCode() {
return wardCode;
}
public void setWardCode(String wardCode) {
this.wardCode = wardCode;
}

public String getCboCode() {
return cboCode;
}
public void setCboCode(String cboCode) {
this.cboCode = cboCode;
}


}
