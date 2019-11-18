/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.OvcWithdrawal;
import java.util.List;

/**
 *
 * @author HP USER
 */
public interface OvcWithdrawalDao
{
    public void saveOvcWithdrawal(OvcWithdrawal ovcWithdrawal) throws Exception;
    public OvcWithdrawal getOvcWithdrawal(String ovcId) throws Exception;
    public void saveOrUpdateOvcWithdrawal(OvcWithdrawal ovcWithdrawal) throws Exception;
    public void updateOvcWithdrawal(OvcWithdrawal ovcWithdrawal) throws Exception;
    public void markedForDelete(OvcWithdrawal ovcWithdrawal) throws Exception;
    public void deleteOvcWithdrawal(OvcWithdrawal ovcWithdrawal) throws Exception;
    public List getWithdrawnOvc(String ovcId) throws Exception;
    public void withdrawClient(String uniqueId,String dateOfWithdrawal, String reasonForWithdrawal,String type,int surveyNo);
    public List getWithdrawalList(List paramList) throws Exception;
    public void changeOvcId(String oldId,String newId) throws Exception;
    public List getListOfOvcWithdrawn(String additionalQueryCriteria) throws Exception;
    public List getListOfOvcWithdrawnForExport(String additionalQueryCriteria) throws Exception;
    public int getNumberOfOvcWithdrawnPerCohort(String additionalQueryCriteria,String reasonWithrawn) throws Exception;
    public int getNumberOfCaregiversWithdrawnPerCohort(String additionalQueryCriteria,String reasonWithrawn) throws Exception;
    public int getNumberOfHouseholdsWithdrawnPerCohort(String additionalQueryCriteria,String reasonWithrawn) throws Exception;
    public List getListOfWithrawalWithWrongType() throws Exception;
    public List getDistinctWithdrawalTypes() throws Exception;
    public List getListOfHouseholdsWithdrawnPerCohort(String additionalQueryCriteria,String reasonWithrawn) throws Exception;
    public List getListOfOvcWithdrawnPerCohort(String additionalQueryCriteria,String reasonWithrawn) throws Exception;
    public List getListOfHouseholdsWithdrawnForExport(String additionalQueryCriteria) throws Exception;
    public List getListOfCaregiversWithdrawnPerCohort(String additionalQueryCriteria,String reasonWithrawn) throws Exception;
    public List getListOfCaregiversWithdrawnForExport(String additionalQueryCriteria) throws Exception;
    public void deleteOvcWithdrawal(String clientId) throws Exception ;
    public void withdrawHousehold(String hhUniqueId,String dateOfWithdrawal,String reasonForWithdrawal,String type,int surveyNo) throws Exception;
    public int resetGraduationValue() throws Exception;
    public int resetAgeAbove18Value() throws Exception;
    public void withdrawHouseholdMembers() throws Exception;
    public List getWithdrawalListByTypeOfWithdrawal(String typeOfWithdrawal) throws Exception;
    public int resetAgeAbove18ToGraduation() throws Exception;
    public List getListOfIdsNotWithdrawn(List listOfIds) throws Exception;
    public List getDistinctReasonForWithdrwal() throws Exception;
    public List getListOfBeneficiariesByTypeAndReasonWithdrawn(String reasonWithdrawal,String type) throws Exception;
    public List getListOfBeneficiariesByReasonWithdrawn(String reasonWithdrawal) throws Exception;
    public void updateHouseholdsWithIncorrectType() throws Exception;
    public OvcWithdrawal getOvcWithdrawalWithCorrectWithrawalType(OvcWithdrawal ovcWithdrawal);
    public void withdrawHouseholdMembers(OvcWithdrawal withdrawal) throws Exception;
    public int resetHouseholdAgeAbove17ToGraduation() throws Exception;
    public OvcWithdrawal getWithdrawal(String uniqueId) throws Exception;
}
