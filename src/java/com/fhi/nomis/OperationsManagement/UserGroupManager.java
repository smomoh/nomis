/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.OperationsManagement;

import com.fhi.kidmap.business.UserGroup;
import com.fhi.kidmap.dao.UserGroupDao;
import com.fhi.kidmap.dao.UserGroupDaoImpl;

/**
 *
 * @author smomoh
 */
public class UserGroupManager 
{
    public static boolean isDonor(UserGroup usg)
    {
        if(usg !=null && usg.getGroupType() !=null && usg.getGroupType().equalsIgnoreCase("FB"))
        return true;
        return false;
    }
    public static boolean isIP(UserGroup usg)
    {
        if(usg !=null && usg.getGroupType() !=null && usg.getGroupType().equalsIgnoreCase("IP"))
        return true;
        return false;
    }
    public static boolean isCBO(UserGroup usg)
    {
        if(usg !=null && usg.getGroupType() !=null && usg.getGroupType().equalsIgnoreCase("CBO"))
        return true;
        return false;
    }
    public static UserGroupDao getUserGroupDaoInstance()
    {
        return new UserGroupDaoImpl();
    }
    public UserGroupDao getUserGroup(String userGroupId)
    {
        return new UserGroupDaoImpl();
    }
}
