/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.resourcemanagement;

/**
 *
 * @author smomoh
 */
public class UserPrivilege 
{
    private int privilegeCode;
    private String privilegeName;

    public int getPrivilegeCode() {
        return privilegeCode;
    }

    public void setPrivilegeCode(int privilegeCode) {
        this.privilegeCode = privilegeCode;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }
    
}
