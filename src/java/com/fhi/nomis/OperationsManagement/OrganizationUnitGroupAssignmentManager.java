/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.OperationsManagement;

import com.fhi.kidmap.business.OrganizationUnitGroupAssignment;
import com.fhi.kidmap.dao.OrganizationUnitGroupAssignmentDao;
import com.fhi.kidmap.dao.OrganizationUnitGroupAssignmentDaoImpl;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class OrganizationUnitGroupAssignmentManager 
{
    public static OrganizationUnitGroupAssignmentDao getOrganizationUnitGroupAssignmentDaoInstance()
    {
        return new OrganizationUnitGroupAssignmentDaoImpl();
    }
    public static OrganizationUnitGroupAssignment getOrganizationUnitGroupAssignment(int recordId)
    {
        OrganizationUnitGroupAssignment ouga=null;
        try
        {
            ouga=getOrganizationUnitGroupAssignmentDaoInstance().getOrganizationUnitGroupAssignmentByRecordId(recordId);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return ouga;
    }
    public static List getOrganizationUnitGroupAssignments(String ougaId)
    {
        List list=null;
        try
        {
            list=getOrganizationUnitGroupAssignmentDaoInstance().getOrganizationUnitGroupAssignmentByGroupId(ougaId);
            if(list==null)
            {
                list=new ArrayList();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
}
