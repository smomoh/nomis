/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.OperationsManagement;

import com.fhi.kidmap.business.UserRight;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class UserRightsManager 
{
    
    public static List getUserRights()
    {
        List rightList=new ArrayList();
        rightList.add(getUserRights("bdelete","Bulk delete records","bulkdelete"));
        rightList.add(getUserRights("hhmerge","Merge Households","hhmerge"));
        rightList.add(getUserRights("mergeou","Merge Organization units","mergeou"));
        rightList.add(getUserRights("siteTrans","Transition sites","sitetransfer"));
        rightList.add(getUserRights("chptner","Change Partner","partnerchange"));
        //rightList.add(getUserRights("datimreport","Access Datim report","datimreport"));
        return rightList;
    }
    public static UserRight getUserRights(String rightId,String name,String type)
    {
        UserRight ur=new UserRight();
        ur.setRightId(rightId);
        ur.setName(name);
        ur.setType(type);
        return ur;
    }
}
