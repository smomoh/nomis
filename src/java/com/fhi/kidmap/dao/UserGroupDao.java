/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.UserGroup;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface UserGroupDao 
{
    public void saveUserGroup(UserGroup usg) throws Exception;
    public void updateUserGroup(UserGroup usg) throws Exception;
    public void deleteUserGroup(UserGroup usg) throws Exception;
    public UserGroup getUserGroup(String groupId) throws Exception;
    public List getAllUserGroups() throws Exception;
    public UserGroup getUserGroupByGroupName(String groupName) throws Exception;
}
