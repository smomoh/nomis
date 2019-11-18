/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.nomis.upgrade.CompatibilityHvi;
import java.util.List;

/**
 *
 * @author Siaka
 */
public interface CompatibilityHviDao 
{
    public int getNoOfHouseholdsEnrolled() throws Exception;
    public List getHouseholdVulnerabilityIndexList(String additionalQuery) throws Exception;
    public List getDistinctOrgCodes() throws Exception;
    public CompatibilityHvi getCompatibleHouseholdVulnerabilityIndex(String uid) throws Exception;
}
