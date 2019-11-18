/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.ReferralDirectory;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface ReferralDirectoryDao 
{
    public String saveOrUpdateReferralDirectory(ReferralDirectory rd) throws Exception;
    public void saveReferralDirectory(ReferralDirectory rd) throws Exception;
    public void updateReferralDirectory(ReferralDirectory rd) throws Exception;
    public void deleteReferralDirectory(ReferralDirectory rd) throws Exception;
    public ReferralDirectory getReferralDirectory(String facilityId) throws Exception;
    public List getAllReferralDirectories() throws Exception;
    public ReferralDirectory getReferralDirectoryByFacilityName(String facilityName) throws Exception;
    public List getReferralDirectories(String communityCode) throws Exception;
    public String generateFacilityId() throws Exception;
}
