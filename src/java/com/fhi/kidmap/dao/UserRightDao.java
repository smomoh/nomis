/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.UserRight;
import java.util.List;

/**
 *
 * @author Siaka
 */
public interface UserRightDao 
{
    public void saveUserRights(UserRight ur) throws Exception;
    public void updateUserRights(UserRight ur) throws Exception;
    public void deleteUserRights(UserRight ur) throws Exception;
    public List getAllUserRights() throws Exception;
}
