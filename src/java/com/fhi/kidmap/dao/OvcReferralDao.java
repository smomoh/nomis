/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.OvcReferral;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface OvcReferralDao
{
    public void saveOvcReferral(OvcReferral ovcRef) throws Exception;
    public void updateOvcReferral(OvcReferral ovcRef) throws Exception;
    public void markedForDelete(OvcReferral ovcRef) throws Exception;
    public void deleteOvcReferral(OvcReferral ovcRef) throws Exception;
    public OvcReferral getOvcReferral(String ovcId,String refDate) throws Exception;
    public List getOvcReferralList(String startDate, String endDate,String additionalOrgQuery) throws Exception;
    public List getOvcReferralListByOvcId(String ovcId) throws Exception;
    public List getOvcIdsFromReferral() throws Exception;
    public void deleteOvcReferrals(String ovcId) throws Exception;
    public String getReferralReportCriteria(String[] params) throws Exception;
    public List getVulnerableHouseholdReferralList(String[] params) throws Exception;
    public void changeOvcId(String oldId,String newId) throws Exception;
    public List getVulnerableHouseholdReferralListForHh(String[] params) throws Exception;
    public List getOvcReferralList(String[] params) throws Exception;
    public OvcReferral getHTCReferralServiceRecordById(String ovcId,String startDate) throws Exception;
    public List getHTCReferralServiceRecordById(String ovcId) throws Exception;
}
