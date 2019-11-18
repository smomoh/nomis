/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.User;
import java.util.List;

/**
 *
 * @author HP USER
 */
public interface UserDao {
    public void saveUser(User user) throws Exception;
    public User getUser(String username) throws Exception;
    public User getUser(String username,String pwd) throws Exception;
    public List getAllUsers() throws Exception;
    public List getOldUsers() throws Exception ;
    public List getUserInfo(String username) throws Exception;
    public void updateUser(User user) throws Exception;
    public void deleteUser(User user) throws Exception;
    public List getUserByUserRole(String userRole) throws Exception;
    public User getUserByUserName(String username) throws Exception;
    public List getUsersByUserGroupId(String userGrpId) throws Exception;
    public void updateUserGroupId() throws Exception;
}
