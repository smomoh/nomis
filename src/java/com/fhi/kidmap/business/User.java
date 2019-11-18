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
public class User implements Serializable {

    public User() {

    }
    private String username;
    private String password;
    private String userroleId;
    private String firstName;
    private String lastName;
    private String changePwd;
    private String viewClientDetails;
    private String userGroupId;
    private String orgUnitGroupId;
    private String assignedGroupId;
    private String address;
    private String phone;
    private String email;
    private String dateCreated;
    private String lastModified;
    private String accountStatus;
    private String partnerCodes;
    private boolean superUser;

public String getUsername() {
return username;
}
public void setUsername(String username) {
this.username = username;
}

public String getPassword() {
return password;
}
public void setPassword(String password) {
this.password = password;
}

public String getUserroleId() {
return userroleId;
}
public void setUserroleId(String userroleId) {
this.userroleId = userroleId;
}

public String getFirstName() {
return firstName;
}
public void setFirstName(String firstName) {
this.firstName = firstName;
}

public String getLastName() {
return lastName;
}
public void setLastName(String lastName) {
this.lastName = lastName;
}

public String getChangePwd() {
    return changePwd;
}

public void setChangePwd(String changePwd) {
    this.changePwd = changePwd;
}

public String getViewClientDetails() {
    return viewClientDetails;
}

public void setViewClientDetails(String viewClientDetails) {
    this.viewClientDetails = viewClientDetails;
}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAssignedGroupId() {
        return assignedGroupId;
    }

    public void setAssignedGroupId(String assignedGroupId) {
        this.assignedGroupId = assignedGroupId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getOrgUnitGroupId() {
        return orgUnitGroupId;
    }

    public void setOrgUnitGroupId(String orgUnitGroupId) {
        this.orgUnitGroupId = orgUnitGroupId;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getPartnerCodes() {
        return partnerCodes;
    }

    public void setPartnerCodes(String partnerCodes) {
        this.partnerCodes = partnerCodes;
    }

    public boolean isSuperUser() 
    {
        if(this.getUserroleId() !=null && this.getUserroleId().equalsIgnoreCase("superuser"))
        superUser=true;
        return superUser;
    }

}
