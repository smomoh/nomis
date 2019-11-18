/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.HouseholdReferral;
import java.util.List;

/**
 *
 * @author Siaka
 */
public interface HhReferralDao 
{
    public void saveHhReferral(HouseholdReferral hhr) throws Exception;
    public void updateHhReferral(HouseholdReferral hhr) throws Exception;
    public void markedForDelete(HouseholdReferral hhr) throws Exception;
    public void deleteHhReferral(HouseholdReferral hhr) throws Exception;
    //public List getHouseholdReferralList(String startDate, String endDate,String additionalOrgQuery) throws Exception;
    public HouseholdReferral getHouseholdReferral(int id) throws Exception;
    public HouseholdReferral getHouseholdReferral(String uniqueId,String refDate) throws Exception;
    public List getHouseholdReferralList(String[] params) throws Exception;
}
