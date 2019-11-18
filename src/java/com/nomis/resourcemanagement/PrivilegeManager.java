/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.resourcemanagement;

/**
 *
 * @author smomoh
 */
public class PrivilegeManager 
{
    public UserPrivilege getOvcEnrollmentPrivilege()
    {
        UserPrivilege privilege=new UserPrivilege();
        privilege.setPrivilegeCode(12345);
        privilege.setPrivilegeName("OVC Enrollment form");
        
        return privilege;
    }
}
