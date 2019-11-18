/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ImportExport;
import com.fhi.kidmap.business.CareAndSupportChecklist;
import com.fhi.kidmap.dao.CaregiverDao;
import com.fhi.kidmap.dao.CaregiverDaoImpl;
import com.fhi.kidmap.dao.ChildStatusIndexDao;
import com.fhi.kidmap.dao.ChildStatusIndexDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.DeletedRecordDao;
import com.fhi.kidmap.dao.DeletedRecordDaoImpl;
import com.fhi.kidmap.dao.FollowupDao;
import com.fhi.kidmap.dao.FollowupDaoImpl;
import com.fhi.kidmap.dao.HivStatusUpdateDao;
import com.fhi.kidmap.dao.HivStatusUpdateDaoImpl;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdFollowupAssessmentDao;
import com.fhi.kidmap.dao.HouseholdFollowupAssessmentDaoImpl;
import com.fhi.kidmap.dao.HouseholdServiceDao;
import com.fhi.kidmap.dao.HouseholdServiceDaoImpl;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDao;
import com.fhi.kidmap.dao.HouseholdVulnerabilityAssessmentDaoImpl;
import com.fhi.kidmap.dao.OrganizationRecordsDao;
import com.fhi.kidmap.dao.OrganizationRecordsDaoImpl;
import com.fhi.kidmap.dao.OrganizationalCapacityAssessmentDao;
import com.fhi.kidmap.dao.OrganizationalCapacityAssessmentDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcReferralDao;
import com.fhi.kidmap.dao.OvcReferralDaoImpl;
import com.fhi.kidmap.dao.OvcSchoolDao;
import com.fhi.kidmap.dao.OvcSchoolDaoImpl;
import com.fhi.kidmap.dao.OvcServiceDao;
import com.fhi.kidmap.dao.OvcServiceDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.kidmap.dao.WardDao;
import com.fhi.kidmap.dao.WardDaoImpl;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.CaregiverExpenditureAndSchoolAttendance;
import com.fhi.kidmap.business.CaregiverTBHIVScreeningChecklist;
import com.fhi.kidmap.business.CareplanAchievement;
import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.kidmap.business.DeletedRecord;
import com.fhi.nomis.nomisutils.DataEncryption;
import com.fhi.kidmap.business.FollowupSurvey;
import com.fhi.kidmap.business.GraduationCheckList;
import com.fhi.kidmap.business.HivRiskAssessmentChecklist;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.HouseholdFollowupAssessment;
import com.fhi.kidmap.business.HouseholdService;
import com.fhi.kidmap.business.HouseholdVulnerabilityAssessment;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.OrganizationalCapacityAssessment;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcReferral;
import com.fhi.kidmap.business.OvcSchool;
import com.fhi.kidmap.business.OvcService;
import com.fhi.kidmap.business.OvcTBHIVScreeningChecklist;
import com.fhi.kidmap.business.OvcWithdrawal;
import com.fhi.kidmap.business.ReferralDirectory;
import com.fhi.kidmap.business.Wards;
import com.fhi.kidmap.business.ZipHandler;
import com.fhi.kidmap.dao.CareAndSupportDao;
import com.fhi.kidmap.dao.CareAndSupportDaoImpl;
import com.fhi.kidmap.dao.CaregiverExpenditureAndSchoolAttendanceDao;
import com.fhi.kidmap.dao.CaregiverExpenditureAndSchoolAttendanceDaoImpl;
import com.fhi.kidmap.dao.CaregiverTBHIVScreeningChecklistDao;
import com.fhi.kidmap.dao.CaregiverTBHIVScreeningChecklistDaoImpl;
import com.fhi.kidmap.dao.CareplanAchievementDao;
import com.fhi.kidmap.dao.CareplanAchievementDaoImpl;
import com.fhi.kidmap.dao.DatabaseImportTrackerDao;
import com.fhi.kidmap.dao.DatabaseImportTrackerDaoImpl;
import com.fhi.kidmap.dao.GraduationCheckListDao;
import com.fhi.kidmap.dao.GraduationCheckListDaoImpl;
import com.fhi.kidmap.dao.HivRiskAssessmentChecklistDao;
import com.fhi.kidmap.dao.HivRiskAssessmentChecklistDaoImpl;
import com.fhi.kidmap.dao.OvcTBHIVScreeningChecklistDao;
import com.fhi.kidmap.dao.OvcTBHIVScreeningChecklistDaoImpl;
import com.fhi.kidmap.dao.ReferralDirectoryDao;
import com.fhi.kidmap.dao.ReferralDirectoryDaoImpl;
import com.fhi.nomis.logs.NomisLogManager;
import com.fhi.nomis.nomisutils.DatabaseImportTracker;
import com.fhi.nomis.nomisutils.DateManager;
import com.fhi.nomis.nomisutils.MoveFile;
import com.fhi.nomis.nomisutils.NomisConstant;
import com.fhi.nomis.nomisutils.TaskManager;
import java.io.Serializable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author smomoh
 */
public class MergeDatabase implements Serializable
{
List deletedWithdrawalStatusList=new ArrayList();
List uniqueDeletedList=new ArrayList();
ZipHandler zipHandler;
DataEncryption encryptor;
AppUtility appUtil;
DaoUtil util=new DaoUtil();
boolean compatibilityFlag=false;
boolean hheCompatibilityFlag=false;
boolean hvaCompatibilityFlag=false;
boolean cgiverCompatibilityFlag=false;
String currentUserName=null;
List hivList=new ArrayList();
List hivOnTreatmentList=new ArrayList();
DeletedRecordDao drdao=new DeletedRecordDaoImpl();


public MergeDatabase()
{
    appUtil=new AppUtility();
    appUtil.createExportImportDirectories();
}
public MergeDatabase(String alternateFilePath,String fileName)
{
    appUtil=new AppUtility();
    AppUtility.getResourceLocation();
    AppUtility.setCurrentImportRecordName("Extracting data from zip files");
    //appUtil.setDbRootDirectory(appUtil.getUserHomeDirectory());
    //appUtil.deleteFiles(appUtil.getExportFilePath());
    String destinationDirectory=appUtil.getExportFilePath();
    appUtil.createExportImportDirectories();
    zipHandler=new ZipHandler();
    zipHandler.unZipFile(appUtil.getImportFilePath()+appUtil.getFileSeperator()+fileName,destinationDirectory);
    //System.err.println("appUtil.getImportFilePath()+\\+fileName is "+appUtil.getImportFilePath()+appUtil.getFileSeperator()+fileName);
}
public DataExportSummary importDataExportSummary()
{
    //List list=new ArrayList();
    String fileSeperator=appUtil.getFileSeperator();
    String exportFileName="DataExportSummary";
    AppUtility.setCurrentImportRecordName("Data export summary");
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    newRecordsList.add("Number od Data export summary saved");
    duplicateRecordsList.add("Number od Data export summary saved as updates");
    int numberSaved=0;
    int numberUpdated=0;
    DataExportSummary des=new DataExportSummary();
    String filePath=appUtil.getExportFilePath()+fileSeperator+exportFileName+fileSeperator;
    try
	{
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+exportFileName+".xml";
                List files=getFiles(filePath);
                if(files !=null)
                {
                    
                    //DataExportSummary des=null;
                    
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("elementName");

                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            //des=new DataExportSummary();
                            Node firstNode = listOfObjects.item(s);
                             if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                //int recordId=Integer.parseInt(getNodeValue("recordId",s,listOfObjects));
                                des.setExportId(getNodeValue("exportId",s,listOfObjects));
                                des.setSystemTime(getNodeValue("systemTime",s,listOfObjects));
                                
                                des.setLevel2OuName(getNodeValue("state",s,listOfObjects));
                                des.setLevel3OuName(getNodeValue("lga",s,listOfObjects));
                                des.setCboName(getNodeValue("cboName",s,listOfObjects));
                                des.setLevel4OuName(getNodeValue("ward",s,listOfObjects));
                                des.setExportPeriod(getNodeValue("exportPeriod",s,listOfObjects));
                                
                                des.setHhEverEnrolledInDatabase(Integer.parseInt(getNodeValue("hheenrolledIndb",s,listOfObjects)));
                                des.setHhCurrentlyEnrolledInDatabase(Integer.parseInt(getNodeValue("hhcenrolledIndb",s,listOfObjects)));
                                des.setVcEverEnrolledInDatabase(Integer.parseInt(getNodeValue("vceenrolledIndb",s,listOfObjects)));
                                des.setVcCurrentlyEnrolledInDatabase(Integer.parseInt(getNodeValue("vccenrolledInd",s,listOfObjects)));
                                des.setNoOfHHEnrollmentRecordsExported(Integer.parseInt(getNodeValue("hhenrolledexported",s,listOfObjects)));
                                des.setNoOfVCEnrollmentRecordsExported(Integer.parseInt(getNodeValue("vceenrolledexported",s,listOfObjects)));
                                des.setNoOfCaregiverEnrollmentRecordsExported(Integer.parseInt(getNodeValue("cgiverexported",s,listOfObjects)));
                                des.setNoOfHHFollowupAssessmentRecordsExported(Integer.parseInt(getNodeValue("hhfollowupexported",s,listOfObjects)));
                                
                                des.setNoOfVCFollowupAssessmentRecordsExported(Integer.parseInt(getNodeValue("vcfollowupexported",s,listOfObjects)));
                                des.setNoOfHHServiceRecordsExported(Integer.parseInt(getNodeValue("hhserviceexported",s,listOfObjects)));
                                des.setNoOfVCServiceRecordsExported(Integer.parseInt(getNodeValue("vcserviceexported",s,listOfObjects)));
                                des.setNoOfCSIRecordsExported(Integer.parseInt(getNodeValue("csiexported",s,listOfObjects)));
                                des.setCareAndSupportRecordsExported(Integer.parseInt(getNodeValue("careandsupportexported",s,listOfObjects)));
                                des.setCaregiverExpenditureRecordsExported(Integer.parseInt(getNodeValue("cgiverExpenditureexported",s,listOfObjects)));
                                des.setCareplanAchievementRecordsExported(Integer.parseInt(getNodeValue("careplanexported",s,listOfObjects)));
                                des.setNoOfCgiverTBHIVRecordsExported(Integer.parseInt(getNodeValue("cgivertbhivexported",s,listOfObjects)));
                                des.setNoOfVCTBHIVRecordsExported(Integer.parseInt(getNodeValue("vctbhivexported",s,listOfObjects)));
                                
                                des.setNoOfHIVRiskAssessmentRecordsExported(Integer.parseInt(getNodeValue("hivriskassessexported",s,listOfObjects)));
                                des.setNoOfReferralRecordsExported(Integer.parseInt(getNodeValue("hhreferralexported",s,listOfObjects)));
                                des.setNoOfSchoolRecordsExported(Integer.parseInt(getNodeValue("schoolexported",s,listOfObjects)));
                                des.setNoOfTrainingRecordsExported(Integer.parseInt(getNodeValue("trainingexported",s,listOfObjects)));
                                des.setNoOfWithdrawalRecordsExported(Integer.parseInt(getNodeValue("withdrawalexported",s,listOfObjects)));
                                
                                
                                
                             }
                        }
                    }
	        }
            // newRecordsList.add(numberSaved);
            //duplicateRecordsList.add(numberUpdated);       
	}

	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    //list.add(newRecordsList);
    //list.add(duplicateRecordsList);
    return des;
}
/*public List importCaregiverExpenditureAndSchoolAttendance()
{
    List list=new ArrayList();
    String fileSeperator=appUtil.getFileSeperator();
    String exportFileName="CaregiverExpenditureAndSchoolAttendance";
    AppUtility.setCurrentImportRecordName("Caregiver Expenditure and School Attendance");
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    newRecordsList.add("Number of new CaregiverExpenditureAndSchoolAttendance records saved");
    duplicateRecordsList.add("Number of CaregiverExpenditureAndSchoolAttendance records saved as updates");
    int numberSaved=0;
    int numberUpdated=0;
    int count=0;
    String filePath=appUtil.getExportFilePath()+fileSeperator+exportFileName+fileSeperator;
    try
	{
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+exportFileName+".xml";
                List files=getFiles(filePath);
                if(files !=null)
                {
                    CaregiverExpenditureAndSchoolAttendanceDao cscdao=new CaregiverExpenditureAndSchoolAttendanceDaoImpl();
                    CaregiverExpenditureAndSchoolAttendance ceasa=null;
                    CaregiverExpenditureAndSchoolAttendance existingCeasa=null;
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("elementName");

                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            ceasa=new CaregiverExpenditureAndSchoolAttendance();
                            Node firstNode = listOfObjects.item(s);
                             if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                int recordId=Integer.parseInt(getNodeValue("recordId",s,listOfObjects));
                                String caregiverId=getNodeValue("caregiverId",s,listOfObjects);
                                ceasa.setRecordId(recordId);
                                ceasa.setCaregiverId(caregiverId);
                                ceasa.setDateOfAssessment(getNodeValue("dateOfAssessment",s,listOfObjects));
                                ceasa.setUnexpectedExpenditure(getNodeValue("unexpectedExpenditure",s,listOfObjects));
                                ceasa.setAccessMoney(getNodeValue("accessMoney",s,listOfObjects));
                                ceasa.setSourceOfMoney(getNodeValue("sourceOfMoney",s,listOfObjects));
                                ceasa.setUrgentHhNeeds(getNodeValue("urgentHhNeeds",s,listOfObjects));
                                ceasa.setSchAttendance(getNodeValue("schAttendance",s,listOfObjects));
                                ceasa.setOvcId(getNodeValue("ovcId",s,listOfObjects));
                                ceasa.setReasonsForMissingSch(getNodeValue("reasonsForMissingSch",s,listOfObjects));
                                ceasa.setVolunteerName(getNodeValue("volunteerName",s,listOfObjects));
                                ceasa.setVolunteerPhone(getNodeValue("volunteerPhone",s,listOfObjects));
                                ceasa.setRecordedBy(getNodeValue("recordedBy",s,listOfObjects));
                                ceasa.setLastModifiedDate(getNodeValue("lastModifiedDate",s,listOfObjects));
                                 
                                existingCeasa=cscdao.getCaregiverExpenditureAndSchoolAttendance(caregiverId, ceasa.getDateOfAssessment());
                                if(existingCeasa==null)
                                {
                                    count++;
                                    cscdao.saveCaregiverExpenditureAndSchoolAttendance(ceasa);
                                    numberSaved++;
                                    AppUtility.setCurrentImportRecordName("Caregiver Expenditure and School Attendance: "+count+" saved");
                                }
                                else
                                {
                                    count++;
                                    ceasa.setRecordId(existingCeasa.getRecordId());
                                    cscdao.updateCaregiverExpenditureAndSchoolAttendance(ceasa);
                                    numberUpdated++;
                                    AppUtility.setCurrentImportRecordName("Caregiver Expenditure and School Attendance: "+count+" saved");
                                }
                                
                             }
                        }
                    }
	        }
             newRecordsList.add(numberSaved);
            duplicateRecordsList.add(numberUpdated);       
	}

	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    list.add(newRecordsList);
    list.add(duplicateRecordsList);
    return list;
}*/
public List importCareAndSupportChecklist()
{
    List list=new ArrayList();
    String fileSeperator=appUtil.getFileSeperator();
    String exportFileName="CareAndSupportChecklist";
    AppUtility.setCurrentImportRecordName("Care and Support checklist");
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    newRecordsList.add("Number of new CareAndSupportChecklist records saved");
    duplicateRecordsList.add("Number of CareAndSupportChecklist records saved as updates");
    int numberSaved=0;
    int numberUpdated=0;
    String filePath=appUtil.getExportFilePath()+fileSeperator+exportFileName+fileSeperator;
    try
	{
                //String uniqueId=null;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+exportFileName+".xml";
                List files=getFiles(filePath);
                if(files !=null)
                {
                    CareAndSupportDao cscdao=new CareAndSupportDaoImpl();
                    CareAndSupportChecklist csc=null;
                    CareAndSupportChecklist existingcsc=null;
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("elementName");

                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            csc=new CareAndSupportChecklist();
                            Node firstNode = listOfObjects.item(s);
                             if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                int recordId=Integer.parseInt(getNodeValue("recordId",s,listOfObjects));
                                String uniqueId=getNodeValue("uniqueId",s,listOfObjects);
                                csc.setRecordId(recordId);
                                csc.setClientId(uniqueId);
                                csc.setDateOfAssessment(getNodeValue("dateOfAssessment",s,listOfObjects));
                                csc.setDateOfHivTest(getNodeValue("dateOfHivTest",s,listOfObjects));
                                csc.setLastModifiedDate(getNodeValue("lastModifiedDate",s,listOfObjects));
                                csc.setReceivedTestResult(getNodeValue("receivedTestResult",s,listOfObjects));
                                csc.setHivStatus(getNodeValue("hivStatus",s,listOfObjects));
                                csc.setEnrolledOnTreatment(getNodeValue("enrolledOnTreatment",s,listOfObjects));
                                csc.setDateEnrolledOnART(getNodeValue("dateEnrolledOnART",s,listOfObjects));
                                csc.setTreatmentFacility(getNodeValue("treatmentFacility",s,listOfObjects));
                                csc.setViralLoadTestDone(getNodeValue("viralLoadTestDone",s,listOfObjects));
                                csc.setDateOfViralLoadTest(getNodeValue("dateOfViralLoadTest",s,listOfObjects));
                                
                                csc.setMedicationPickedUp(getNodeValue("medicationPickedUp",s,listOfObjects));
                                csc.setSkippedARV(getNodeValue("skippedARV",s,listOfObjects));
                                csc.setReasonsPeopleSkipARV(getNodeValue("reasonsPeopleSkipARV",s,listOfObjects));
                                csc.setTransportationSupport(getNodeValue("transportationSupport",s,listOfObjects));
                                csc.setLastModifiedDate(getNodeValue("lastModifiedDate",s,listOfObjects));
                                csc.setExperiencedSoresOrRash(getNodeValue("experiencedSoresOrRash",s,listOfObjects));
                                csc.setVolunteerName(getNodeValue("volunteerName",s,listOfObjects));
                                csc.setDesignation(getNodeValue("designation",s,listOfObjects));
                                csc.setApprovalLevel(Integer.parseInt(getNodeValue("approvalLevel",s,listOfObjects)));
                                csc.setRecordDeleted(Integer.parseInt(getNodeValue("recordDeleted",s,listOfObjects)));
                                String defaultDate="1900-01-01";
                                if(csc.getDateEnrolledOnART()==null || csc.getDateEnrolledOnART().indexOf("-")==-1)
                                csc.setDateEnrolledOnART(defaultDate);
                                if(csc.getDateOfHivTest()==null || csc.getDateOfHivTest().indexOf("-")==-1)
                                csc.setDateOfHivTest(defaultDate);
                                if(csc.getDateOfViralLoadTest()==null || csc.getDateOfViralLoadTest().indexOf("-")==-1)
                                csc.setDateOfViralLoadTest(defaultDate);
                                existingcsc=cscdao.getCareAndSupportCheclist(uniqueId, csc.getDateOfAssessment());
                                if(existingcsc==null)
                                {
                                    cscdao.saveCareAndSupportCheclist(csc);
                                    numberSaved++;
                                }
                                else
                                {
                                    csc.setRecordId(existingcsc.getRecordId());
                                    cscdao.updateCareAndSupportCheclist(csc);
                                    numberUpdated++;
                                }
                                
                             }
                        }
                    }
	        }
             newRecordsList.add(numberSaved);
            duplicateRecordsList.add(numberUpdated);       
	}

	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    list.add(newRecordsList);
    list.add(duplicateRecordsList);
    return list;
}
public List importCareplanAchievementChecklist()
{
    List list=new ArrayList();
    String fileSeperator=appUtil.getFileSeperator();
    String exportFileName="CareplanAchievement";
    AppUtility.setCurrentImportRecordName("Care plan achievement chechlist");
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    newRecordsList.add("Number of new Careplan Achievement records saved");
    duplicateRecordsList.add("Number of Careplan Achievement records saved as updates");
    int numberSaved=0;
    int numberUpdated=0;
    int count=0;
    String filePath=appUtil.getExportFilePath()+fileSeperator+exportFileName+fileSeperator;
    try
	{
                //String uniqueId=null;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+exportFileName+".xml";
                List files=getFiles(filePath);
                if(files !=null)
                {
                    CareplanAchievementDao cpadao=new CareplanAchievementDaoImpl();
                    CareplanAchievement cpa=null;
                    CareplanAchievement existingcpa=null;
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("patient");

                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            cpa=new CareplanAchievement();
                            Node firstNode = listOfObjects.item(s);
                             if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                String clientId=getNodeValue("clientId",s,listOfObjects);
                                cpa.setClientId(clientId);
                                cpa.setDateOfAssessment(getNodeValue("dateOfAssessment",s,listOfObjects));
                                cpa.setDesignation(getNodeValue("designation",s,listOfObjects));
                                cpa.setLastModifiedDate(getNodeValue("lastModifiedDate",s,listOfObjects));
                                cpa.setVolunteerId(getNodeValue("volunteerId",s,listOfObjects));
                                cpa.setGraduated(getNodeValue("graduated",s,listOfObjects));
                                cpa.setRecordedBy(getNodeValue("recordedBy",s,listOfObjects));
                                cpa.setAssessmentNo(Integer.parseInt(getNodeValue("assessmentNo",s,listOfObjects)));
                                cpa.setScore(Integer.parseInt(getNodeValue("score",s,listOfObjects)));
                                
                                cpa.setHth_hivknowledge(getNodeValue("hth_hivknowledge",s,listOfObjects));
                                cpa.setHth_hivknowledgeComment(getNodeValue("hth_hivknowledgeComment",s,listOfObjects));
                                cpa.setHth_vchivrisk(getNodeValue("hth_vchivrisk",s,listOfObjects));
                                cpa.setHth_vchivriskComment(getNodeValue("hth_vchivriskComment",s,listOfObjects));
                                cpa.setHth_vcreftested(getNodeValue("hth_vcreftested",s,listOfObjects));
                                cpa.setHth_vcreftestedComment(getNodeValue("hth_vcreftestedComment",s,listOfObjects));
                                cpa.setHth_hivOnArt(getNodeValue("hth_hivOnArt",s,listOfObjects));
                                cpa.setHth_hivOnArtComment(getNodeValue("hth_hivOnArtComment",s,listOfObjects));
                                cpa.setHth_hivdisclosed(getNodeValue("hth_hivdisclosed",s,listOfObjects));
                                cpa.setHth_hivdisclosedComment(getNodeValue("hth_hivdisclosedComment",s,listOfObjects));
                                cpa.setHth_vcInneedOfHthServices(getNodeValue("hth_vcInneedOfHthServices",s,listOfObjects));
                                cpa.setHth_vcInneedOfHthServicesComment(getNodeValue("hth_vcInneedOfHthServicesComment",s,listOfObjects));
                                
                                cpa.setStb_hungryNoFood(getNodeValue("stb_hungryNoFood",s,listOfObjects));
                                cpa.setStb_hungryNoFoodComment(getNodeValue("stb_hungryNoFoodComment",s,listOfObjects));
                                cpa.setStb_resiliency(getNodeValue("stb_resiliency",s,listOfObjects));
                                cpa.setStb_resiliencyComment(getNodeValue("stb_resiliencyComment",s,listOfObjects));
                                cpa.setStb_cgPartEconServ(getNodeValue("stb_cgPartEconServ",s,listOfObjects));
                                cpa.setStb_cgPartEconServComment(getNodeValue("stb_cgPartEconServComment",s,listOfObjects));
                                cpa.setStb_socEmotSupport(getNodeValue("stb_socEmotSupport",s,listOfObjects));
                                cpa.setStb_socEmotSupportComment(getNodeValue("stb_socEmotSupportComment",s,listOfObjects));
                                
                                cpa.setSft_vcsad(getNodeValue("sft_vcsad",s,listOfObjects));
                                cpa.setSft_vcsadComment(getNodeValue("sft_vcsadComment",s,listOfObjects));
                                cpa.setSft_vcreferredForCps(getNodeValue("sft_vcreferredForCps",s,listOfObjects));
                                cpa.setSft_vcreferredForCpsComment(getNodeValue("sft_vcreferredForCpsComment",s,listOfObjects));
                                cpa.setSft_vcSafeFromAbuse(getNodeValue("sft_vcSafeFromAbuse",s,listOfObjects));
                                cpa.setSft_vcSafeFromAbuseComment(getNodeValue("sft_vcSafeFromAbuseComment",s,listOfObjects));
                                cpa.setSft_birthCert(getNodeValue("sft_birthCert",s,listOfObjects));
                                cpa.setSft_birthCertComment(getNodeValue("sft_birthCertComment",s,listOfObjects));
                                cpa.setSft_cgcompletedTwoModules(getNodeValue("sft_cgcompletedTwoModules",s,listOfObjects));
                                cpa.setSft_cgcompletedTwoModulesComment(getNodeValue("sft_cgcompletedTwoModulesComment",s,listOfObjects));
                                cpa.setSft_childHeadedLinkedToServices(getNodeValue("sft_childHeadedLinkedToServices",s,listOfObjects));
                                cpa.setSft_childHeadedLinkedToServicesComment(getNodeValue("sft_childHeadedLinkedToServicesComment",s,listOfObjects));
                                
                                cpa.setSch_schAttendance(getNodeValue("sch_schAttendance",s,listOfObjects));
                                cpa.setSch_schAttendanceComment(getNodeValue("sch_schAttendanceComment",s,listOfObjects));
                                cpa.setSch_vcEnrolledInSecondarySch(getNodeValue("sch_vcEnrolledInSecondarySch",s,listOfObjects));
                                cpa.setSch_vcEnrolledInSecondarySchComment(getNodeValue("sch_vcEnrolledInSecondarySchComment",s,listOfObjects));
                                cpa.setSch_adolInVoctraining(getNodeValue("sch_adolInVoctraining",s,listOfObjects));
                                cpa.setSch_adolInVoctrainingComment(getNodeValue("sch_adolInVoctrainingComment",s,listOfObjects));
                                cpa.setSch_earlyChildCare(getNodeValue("sch_earlyChildCare",s,listOfObjects));
                                cpa.setSch_earlyChildCareComment(getNodeValue("sch_earlyChildCareComment",s,listOfObjects));
                                
                                existingcpa=cpadao.getCareplanAchievement(clientId, cpa.getDateOfAssessment());
                                if(existingcpa==null)
                                {
                                    cpadao.saveCareplanAchievement(cpa);
                                    numberSaved++;
                                    count++;
                                    AppUtility.setCurrentImportRecordName("Care plan achievement chechlist: "+count+" saved");
                                }
                                else
                                {
                                    cpa.setId(existingcpa.getId());
                                    cpadao.updateCareplanAchievement(cpa);
                                    numberUpdated++;
                                    count++;
                                    AppUtility.setCurrentImportRecordName("Care plan achievement chechlist: "+count+" saved");
                                }
                                
                             }
                        }
                    }
	        }
             newRecordsList.add(numberSaved);
            duplicateRecordsList.add(numberUpdated);       
	}

	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    
    list.add(newRecordsList);
    list.add(duplicateRecordsList);
    return list;
}
public List importHivRiskAssessmentChecklist()
{
    List list=new ArrayList();
    String fileSeperator=appUtil.getFileSeperator();
    String exportFileName="HivRiskAssessmentChecklist";
    AppUtility.setCurrentImportRecordName("Hiv Risk Assessment records");
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    newRecordsList.add("Number of new HivRiskAssessmentChecklist records saved");
    duplicateRecordsList.add("Number of HivRiskAssessmentChecklist records saved as updates");
    int numberSaved=0;
    int numberUpdated=0;
    int count=0;
    String filePath=appUtil.getExportFilePath()+fileSeperator+exportFileName+fileSeperator;
    try
	{
                //String uniqueId=null;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+exportFileName+".xml";
                List files=getFiles(filePath);
                if(files !=null)
                {
                    HivRiskAssessmentChecklistDao hracdao=new HivRiskAssessmentChecklistDaoImpl();
                    HivRiskAssessmentChecklist hrac=null;
                    HivRiskAssessmentChecklist existinghrac=null;
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("elementName");

                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            hrac=new HivRiskAssessmentChecklist();
                            Node firstNode = listOfObjects.item(s);
                             if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                String ovcId=getNodeValue("ovcId",s,listOfObjects);
                                hrac.setOvcId(ovcId);
                                hrac.setChildAgeComment(getNodeValue("childAgeComment",s,listOfObjects));
                                hrac.setChildAgeQuestion(getNodeValue("childAgeQuestion",s,listOfObjects));
                                hrac.setChildAtRiskQuestion(getNodeValue("childAtRiskQuestion",s,listOfObjects));
                                hrac.setChildSickComment(getNodeValue("childSickComment",s,listOfObjects));
                                hrac.setChildSickQuestion(getNodeValue("childSickQuestion",s,listOfObjects));
                                hrac.setChildTestedComment(getNodeValue("childTestedComment",s,listOfObjects));
                                hrac.setChildTestedQuestion(getNodeValue("childTestedQuestion",s,listOfObjects));
                                hrac.setChronicallyIllComment(getNodeValue("chronicallyIllComment",s,listOfObjects));
                                hrac.setChronicallyIllQuestion(getNodeValue("chronicallyIllQuestion",s,listOfObjects));
                                hrac.setDateOfAssessment(getNodeValue("dateOfAssessment",s,listOfObjects));
                                hrac.setDesignation(getNodeValue("designation",s,listOfObjects));
                                hrac.setGenitalDischargeComment(getNodeValue("genitalDischargeComment",s,listOfObjects));
                                hrac.setGenitalDischargeQuestion(getNodeValue("genitalDischargeQuestion",s,listOfObjects));
                                hrac.setHivParentComment(getNodeValue("hivParentComment",s,listOfObjects));
                                hrac.setHivParentQuestion(getNodeValue("hivParentQuestion",s,listOfObjects));
                                hrac.setLastModifiedDate(getNodeValue("lastModifiedDate",s,listOfObjects));
                                hrac.setParentDeceasedComment(getNodeValue("parentDeceasedComment",s,listOfObjects));
                                hrac.setParentDeceasedQuestion(getNodeValue("parentDeceasedQuestion",s,listOfObjects));
                                hrac.setSchoolGradeComment(getNodeValue("schoolGradeComment",s,listOfObjects));
                                hrac.setSchoolGradeQuestion(getNodeValue("schoolGradeQuestion",s,listOfObjects));
                                hrac.setServiceProviderName(getNodeValue("serviceProviderName",s,listOfObjects));
                                hrac.setServiceProviderPhone(getNodeValue("serviceProviderPhone",s,listOfObjects));
                                hrac.setSexualAbuseComment(getNodeValue("sexualAbuseComment",s,listOfObjects));
                                hrac.setSexualAbuseQuestion(getNodeValue("sexualAbuseQuestion",s,listOfObjects));
                                hrac.setSexuallyActiveComment(getNodeValue("sexuallyActiveComment",s,listOfObjects));
                                hrac.setSexuallyActiveQuestion(getNodeValue("sexuallyActiveQuestion",s,listOfObjects));
                                hrac.setSkinProblemComment(getNodeValue("skinProblemComment",s,listOfObjects));
                                hrac.setSkinProblemQuestion(getNodeValue("skinProblemQuestion",s,listOfObjects));
                                if(getNodeName("bloodTransfusionQuestion",s,listOfObjects) !=null)
                                {
                                    hrac.setBloodTransfusionQuestion(getNodeValue("bloodTransfusionQuestion",s,listOfObjects));
                                    hrac.setBloodTransfusionComment(getNodeValue("bloodTransfusionComment",s,listOfObjects));
                                    hrac.setMuacbmiQuestion(getNodeValue("muacbmiQuestion",s,listOfObjects));
                                    hrac.setMuacbmiComment(getNodeValue("muacbmiComment",s,listOfObjects));
                                    hrac.setHivStatusQuestion(getNodeValue("hivStatusQuestion",s,listOfObjects));
                                    hrac.setHivStatus(getNodeValue("hivStatus",s,listOfObjects));
                                }
                                else
                                {
                                    hrac.setBloodTransfusionQuestion("No");
                                    hrac.setBloodTransfusionComment(null);
                                    hrac.setMuacbmiQuestion("N/A");
                                    hrac.setMuacbmiComment(null);
                                    hrac.setHivStatusQuestion("N/A");
                                    hrac.setHivStatus("N/A");
                                }
                                existinghrac=hracdao.getHivRiskAssessmentChecklist(ovcId, hrac.getDateOfAssessment());
                                if(existinghrac==null)
                                {
                                    hracdao.saveHivRiskAssessmentChecklist(hrac);
                                    numberSaved++;
                                    count++;
                                    AppUtility.setCurrentImportRecordName("Hiv Risk Assessment records: "+count+" saved");
                                }
                                else
                                {
                                    hrac.setRecordId(existinghrac.getRecordId());
                                    hracdao.updateHivRiskAssessmentChecklist(hrac);
                                    numberUpdated++;
                                    count++;
                                    AppUtility.setCurrentImportRecordName("Hiv Risk Assessment records: "+count+" saved");
                                }
                                
                             }
                        }
                    }
	        }
             newRecordsList.add(numberSaved);
            duplicateRecordsList.add(numberUpdated);       
	}

	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    
    list.add(newRecordsList);
    list.add(duplicateRecordsList);
    return list;
}
public List importReferralDirectory()
{
    List list=new ArrayList();
    String fileSeperator=appUtil.getFileSeperator();
    String exportFileName="ReferralDirectory";
    AppUtility.setCurrentImportRecordName("Health Facility records");
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    newRecordsList.add("Number of new ReferralDirectory records saved");
    duplicateRecordsList.add("Number of ReferralDirectory records saved as updates");
    int numberSaved=0;
    int numberUpdated=0;
    String filePath=appUtil.getExportFilePath()+fileSeperator+exportFileName+fileSeperator;
    try
	{
                //String uniqueId=null;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+exportFileName+".xml";
                List files=getFiles(filePath);
                if(files !=null)
                {
                    ReferralDirectoryDao rddao=new ReferralDirectoryDaoImpl();
                    ReferralDirectory rd=null;
                    ReferralDirectory existingrd=null;
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("elementName");

                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            rd=new ReferralDirectory();
                            Node firstNode = listOfObjects.item(s);
                             if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                String facilityId=getNodeValue("facilityId",s,listOfObjects);
                                rd.setFacilityId(facilityId);
                                rd.setAddress(getNodeValue("address",s,listOfObjects));
                                rd.setCommunity(getNodeValue("community",s,listOfObjects));
                                rd.setContactEmail(getNodeValue("contactEmail",s,listOfObjects));
                                rd.setContactNumber(getNodeValue("contactNumber",s,listOfObjects));
                                rd.setDateModified(getNodeValue("dateModified",s,listOfObjects));
                                rd.setFacilityName(getNodeValue("facilityName",s,listOfObjects));
                                rd.setLatitude(getNodeValue("latitude",s,listOfObjects));
                                rd.setLongitude(getNodeValue("longitude",s,listOfObjects));
                                rd.setNameOfContact(getNodeValue("nameOfContact",s,listOfObjects));
                                rd.setTypeOfOrganization(getNodeValue("typeOfOrganization",s,listOfObjects));
                                
                                
                                existingrd=rddao.getReferralDirectory(facilityId);
                                if(existingrd==null)
                                {
                                    rddao.saveReferralDirectory(rd);
                                    numberSaved++;
                                }
                                else
                                {
                                    rd.setFacilityId(existingrd.getFacilityId());
                                    rddao.updateReferralDirectory(rd);
                                    numberUpdated++;
                                }
                                
                             }
                        }
                    }
	        }
             newRecordsList.add(numberSaved);
            duplicateRecordsList.add(numberUpdated);       
	}

	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    
    list.add(newRecordsList);
    list.add(duplicateRecordsList);
    return list;
}
public List importGraduationCheckList()
{
    List list=new ArrayList();
    String fileSeperator=appUtil.getFileSeperator();
    String exportFileName="GraduationCheckList";
    AppUtility.setCurrentImportRecordName("Graduation checklist");
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    newRecordsList.add("Number of new GraduationCheckList records saved");
    duplicateRecordsList.add("Number of GraduationCheckList records saved as updates");
    int numberSaved=0;
    int numberUpdated=0;
    String filePath=appUtil.getExportFilePath()+fileSeperator+exportFileName+fileSeperator;
    try
	{
                //String uniqueId=null;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+exportFileName+".xml";
                List files=getFiles(filePath);
                if(files !=null)
                {
                    GraduationCheckListDao gcldao=new GraduationCheckListDaoImpl();
                    GraduationCheckList gcl=null;
                    GraduationCheckList existinggcl=null;
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("patient");

                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            gcl=new GraduationCheckList();
                            Node firstNode = listOfObjects.item(s);
                             if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                String clientId=getNodeValue("clientId",s,listOfObjects);
                                gcl.setId(Integer.parseInt(getNodeValue("id",s,listOfObjects)));
                                gcl.setClientId(clientId);
                                gcl.setClientType(getNodeValue("clientType",s,listOfObjects));
                                gcl.setDateOfAssessment(getNodeValue("dateOfAssessment",s,listOfObjects));
                                gcl.setDateModified(getNodeValue("dateModified",s,listOfObjects));
                                gcl.setHealth(getNodeValue("health",s,listOfObjects));
                                gcl.setSafety(getNodeValue("safety",s,listOfObjects));
                                gcl.setSchool(getNodeValue("school",s,listOfObjects));
                                gcl.setStability(getNodeValue("stability",s,listOfObjects));
                                gcl.setVulnerability(getNodeValue("vulnerability",s,listOfObjects));
                                gcl.setGclscore(Integer.parseInt(getNodeValue("gclscore",s,listOfObjects)));
                                gcl.setVolunteerId(getNodeValue("volunteerId",s,listOfObjects));
                                gcl.setGraduated(getNodeValue("graduated",s,listOfObjects));
                                gcl.setRecordedby(getNodeValue("recordedby",s,listOfObjects));
                                
                                existinggcl=gcldao.getGraduationCheckList(clientId);
                                if(existinggcl==null)
                                {
                                    gcldao.saveGraduationCheckListForImport(gcl);
                                    numberSaved++;
                                }
                                else
                                {
                                    gcl.setId(existinggcl.getId());
                                    gcldao.updateGraduationCheckListForImport(gcl);
                                    numberUpdated++;
                                }
                                
                             }
                        }
                    }
	        }
             newRecordsList.add(numberSaved);
            duplicateRecordsList.add(numberUpdated);       
	}

	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    
    list.add(newRecordsList);
    list.add(duplicateRecordsList);
    return list;
}
public List processDeletedRecords()
{
    List list=new ArrayList();
    
    String fileSeperator=appUtil.getFileSeperator();
    String exportFileName="DeletedRecord";
    AppUtility.setCurrentImportRecordName("Deleted records");
    String filePath=appUtil.getExportFilePath()+fileSeperator+exportFileName+fileSeperator;
    try
	{
                //String uniqueId=null;
            boolean update=false;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+exportFileName+".xml";
                List files=getFiles(filePath);
                if(files !=null)
                {
                    DeletedRecord dr=null;
                    DeletedRecordDao drdao=new DeletedRecordDaoImpl();
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("patient");

                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            //hhe=new HouseholdEnrollment();
                            Node firstNode = listOfObjects.item(s);
                             if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                 
                                //columnNames={"id","recordId","typeOfRecord","dateRecordCreated","dateRecordDeleted"};
                                String recordId=getNodeValue("recordId",s,listOfObjects);
                                String typeOfRecord=getNodeValue("typeOfRecord",s,listOfObjects);
                                String dateRecordCreated=getNodeValue("dateRecordCreated",s,listOfObjects);
                                String dateRecordDeleted=getNodeValue("dateRecordDeleted",s,listOfObjects);
                                if(typeOfRecord !=null && typeOfRecord.equalsIgnoreCase("ovcWithdrawal"))
                                {
                                    dr=new DeletedRecord();
                                    dr.setDateRecordCreated(dateRecordCreated);
                                    dr.setRecordId(recordId);
                                    dr.setTypeOfRecord(typeOfRecord);
                                    dr.setDateRecordDeleted(dateRecordDeleted);
                                    uniqueDeletedList.add(recordId);
                                    deletedWithdrawalStatusList.add(dr);
                                    continue;
                                }
                                drdao.removeDeletedRecord(recordId, dateRecordCreated, typeOfRecord,update);
                             }
                        }
                    }
	        }     
	}

	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return list;
}
public List readHouseholdVulnebilityIndexFromXml(HttpSession session)
{
    List list=new ArrayList();
    List hvaList=new ArrayList();
    List cgiverList=new ArrayList();
    HouseholdEnrollment hhe=null;
    HouseholdEnrollment duplicateHhe=null;
    HouseholdVulnerabilityAssessment hva=null;
    HouseholdVulnerabilityAssessment duplicateHva=null;
    Caregiver cgiver=null;
    Caregiver duplicateCgiver=null;
    HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
    CaregiverDao cgiverDao=new CaregiverDaoImpl();
    HouseholdVulnerabilityAssessmentDao hvaDao=new HouseholdVulnerabilityAssessmentDaoImpl();
    String caregiverFirstname=null;
    String caregiverSurname=null;
    int count=0;
    //if(compatibilityFlag || hheCompatibilityFlag)
    //return list;
    //System.err.println("inside readHouseholdVulnebilityIndexFromXml");
    String exportFileName="HouseholdVulnerabilityIndex";
    AppUtility.setCurrentImportRecordName("Old Household vulnearability assessment records");
    String filePath=appUtil.getExportFilePath()+appUtil.getFileSeperator()+exportFileName+appUtil.getFileSeperator();
       try
	{
                String uniqueId=null;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+exportFileName+".xml";
                List files=getFiles(filePath);
                if(files !=null)
                {
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("patient");

                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            hhe=new HouseholdEnrollment();
                            Node firstNode = listOfObjects.item(s);
                             if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                uniqueId=getNodeValue("hhUniqueId",s,listOfObjects);
                                int hhAge=Integer.parseInt(getNodeValue("caregiverAge",s,listOfObjects));
                                hhe.setHhUniqueId(uniqueId);
                                hhe.setHhAge(hhAge);
                                hhe.setHhSurname(getAppropriateStringLength(getNodeValue("hhSurname",s,listOfObjects),25));
                                hhe.setHhFirstname(getAppropriateStringLength(getNodeValue("hhFirstname",s,listOfObjects),25));
                                hhe.setAddress(getNodeValue("address",s,listOfObjects));
                                hhe.setPhone(getAppropriateStringLength(getNodeValue("phone",s,listOfObjects),25));
                                hhe.setNoOfChildrenInhh(Integer.parseInt(getNodeValue("noOfChildrenInhh",s,listOfObjects)));
                                hhe.setNoOfPeopleInhh(Integer.parseInt(getNodeValue("noOfPeopleInhh",s,listOfObjects)));
                                hhe.setDateOfAssessment(getNodeValue("dateOfAssessment",s,listOfObjects));
                                hhe.setHhGender("none");
                                hhe.setMaritalStatus(getNodeValue("maritalStatus",s,listOfObjects));
                                hhe.setOccupation(getNodeValue("occupation",s,listOfObjects));
                                hhe.setStateCode(getNodeValue("stateCode",s,listOfObjects));
                                hhe.setLgaCode(getNodeValue("lgaCode",s,listOfObjects));
                                hhe.setOrgCode(getAppropriateStringLength(getNodeValue("orgCode",s,listOfObjects),25));
                                hhe.setCommunityCode(getAppropriateStringLength(getNodeValue("communityCode",s,listOfObjects),25));
                                hhe.setPartnerCode(getAppropriateStringLength(getNodeValue("partnerCode",s,listOfObjects),25));
                                hhe.setEligibleForEnrollment(getAppropriateStringLength(getNodeValue("eligibleForEnrollment",s,listOfObjects),25));
                                hhe.setRecordedBy(getNodeValue("recordedBy",s,listOfObjects));
                                hhe.setDateOfEntry(getNodeValue("dateOfEntry",s,listOfObjects));
                                hhe.setCurrentAge(util.getCurrentAge(hhe.getDateOfAssessment(), hhAge,"Year"));
                                if(getNodeName("latitude",s,listOfObjects) !=null)
                                hhe.setLatitude(Double.parseDouble(getNodeValue("latitude",s,listOfObjects)));
                                if(getNodeName("longitude",s,listOfObjects) !=null)
                                hhe.setLatitude(Double.parseDouble(getNodeValue("longitude",s,listOfObjects)));
                                
                                
                                duplicateHhe=hhedao.getHouseholdEnrollment(hhe.getHhUniqueId());
                                if(duplicateHhe ==null)
                                hhedao.saveHouseholdEnrollment(hhe);
                                else
                                hhedao.updateHouseholdEnrollment(hhe);
                                if(getNodeName("hhHeadship",s,listOfObjects) !=null)
                                {
                                   // System.err.println("hhHeadship is not null");
                                    hva=new HouseholdVulnerabilityAssessment();
                                    hva.setHhUniqueId(uniqueId);
                                    hva.setHhHeadship(Integer.parseInt(getNodeValue("hhHeadship",s,listOfObjects)));
                                    hva.setHealth(Integer.parseInt(getNodeValue("health",s,listOfObjects)));
                                    hva.setEducationLevel(Integer.parseInt(getNodeValue("educationLevel",s,listOfObjects)));
                                    hva.setShelterAndHousing(Integer.parseInt(getNodeValue("shelterAndHousing",s,listOfObjects)));
                                    hva.setFoodSecurityAndNutrition(Integer.parseInt(getNodeValue("foodSecurityAndNutrition",s,listOfObjects)));
                                    hva.setMeansOfLivelihood(Integer.parseInt(getNodeValue("meansOfLivelihood",s,listOfObjects)));
                                    hva.setHhIncome(Integer.parseInt(getNodeValue("hhIncome",s,listOfObjects)));
                                    hva.setNameOfAssessor(getNodeValue("nameOfAssessor",s,listOfObjects));
                                    hva.setDesignation(getNodeValue("designation",s,listOfObjects));
                                    hva.setRecordedBy(getNodeValue("recordedBy",s,listOfObjects));
                                    hva.setDateOfAssessment(getNodeValue("dateOfAssessment",s,listOfObjects));
                                    hva.setDateOfEntry(getNodeValue("dateOfEntry",s,listOfObjects));
                                    hva.setAssessmentNo(1);
                                    hva.setVulnerabilityScore(hvaDao.getHouseholdVulnerabilityScore(hva));
                                    
                                    
                                    hhe.setHhHeadship(Integer.parseInt(getNodeValue("hhHeadship",s,listOfObjects)));
                                    hhe.setHealth(Integer.parseInt(getNodeValue("health",s,listOfObjects)));
                                    hhe.setEducationLevel(Integer.parseInt(getNodeValue("educationLevel",s,listOfObjects)));
                                    hhe.setShelterAndHousing(Integer.parseInt(getNodeValue("shelterAndHousing",s,listOfObjects)));
                                    hhe.setFoodSecurityAndNutrition(Integer.parseInt(getNodeValue("foodSecurityAndNutrition",s,listOfObjects)));
                                    hhe.setMeansOfLivelihood(Integer.parseInt(getNodeValue("meansOfLivelihood",s,listOfObjects)));
                                    hhe.setHhIncome(Integer.parseInt(getNodeValue("hhIncome",s,listOfObjects)));
                                    //hhe.setHhvaId(Integer.parseInt(getNodeValue("hhvaId",s,listOfObjects)));
                                
                                    duplicateHva=hvaDao.getHouseholdVulnerabilityAssessment(hva.getHhUniqueId(), hva.getDateOfAssessment());
                                                                    
                                    if(duplicateHva ==null)
                                    {
                                        count++;
                                        hvaDao.saveHouseholdVulnerabilityAssessment(hva);
                                        AppUtility.setCurrentImportRecordName("Old Household vulnearability assessment records: "+count);
                                    }
                                    else
                                    {
                                        count++;
                                        hva.setId(duplicateHva.getId());
                                        hvaDao.updateHouseholdVulnerabilityAssessmentByuniqueIdAndDate(hva);
                                        AppUtility.setCurrentImportRecordName("Old Household vulnearability assessment records: "+count);
                                    }
                                    hvaList.add(hva);
                                }
                                if(getNodeName("caregiverFirstname",s,listOfObjects) !=null)
                                {
                                    //System.err.println("caregiverFirstname is not null");
                                    cgiver=new Caregiver();
                                    cgiver.setHhUniqueId(uniqueId);
                                    caregiverSurname=getAppropriateStringLength(getNodeValue("caregiverSurname",s,listOfObjects),25);
                                    caregiverFirstname=getAppropriateStringLength(getNodeValue("caregiverFirstname",s,listOfObjects),25);
                                    duplicateCgiver=cgiverDao.getCaregiverByName(cgiver.getHhUniqueId(), caregiverFirstname, caregiverSurname);
                                    if(duplicateCgiver==null)
                                    cgiver.setCaregiverId(cgiverDao.generateCaregiverId(uniqueId));
                                    else
                                    cgiver.setCaregiverId(duplicateCgiver.getCaregiverId());
                                    cgiver.setCaregiverAddress(getNodeValue("address",s,listOfObjects));
                                    cgiver.setCaregiverGender(getNodeValue("caregiverGender",s,listOfObjects));
                                    cgiver.setCaregiverAge(Integer.parseInt(getNodeValue("caregiverAge",s,listOfObjects)));
                                    cgiver.setCaregiverLastName(getAppropriateStringLength(getNodeValue("caregiverSurname",s,listOfObjects),25));
                                    cgiver.setCaregiverFirstname(getAppropriateStringLength(getNodeValue("caregiverFirstname",s,listOfObjects),25));
                                    cgiver.setCaregiverOccupation(getAppropriateStringLength(getNodeValue("occupation",s,listOfObjects),25));
                                    cgiver.setCaregiverMaritalStatus(getNodeValue("maritalStatus",s,listOfObjects));
                                    cgiver.setCaregiverPhone(getAppropriateStringLength(getNodeValue("phone",s,listOfObjects),25));
                                    cgiver.setDateOfEnrollment(getNodeValue("dateOfAssessment",s,listOfObjects));
                                    cgiver.setDateModified(getNodeValue("dateOfEntry",s,listOfObjects));
                                    duplicateCgiver=cgiverDao.getCaregiverByCaregiverId(cgiver.getCaregiverId());
                                    if(duplicateCgiver==null)
                                    cgiverDao.saveCaregiver(cgiver);
                                    else
                                    cgiverDao.updateCaregiver(cgiver);
                                    cgiverList.add(cgiver);
                                }

                                list.add(hhe);
                                
                             }
                        }
                    }
	        }
                    if(list !=null && list.size()>0)
                    {
                        createSavableHheList(session,list);
                        //hheCompatibilityFlag=true;
                    }
                    if(hvaList !=null && hvaList.size()>0)
                    {
                        createSavableHvaList(session,hvaList);
                        //hvaCompatibilityFlag=true;
                    }
                    if(cgiverList !=null && cgiverList.size()>0)
                    {
                        createSavableCaregiverList(session,cgiverList);
                        //cgiverCompatibilityFlag=true;
                    }

                    list.clear();
                    hvaList.clear();
                    cgiverList.clear();

	}

	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return list;
}
public List readHouseholdEnrollmentFromXml(HttpSession session,String partnerCode)
{
    //System.err.println("inside readHouseholdEnrollmentFromXml(HttpSession session)");
    List list=new ArrayList();
    
    String exportFileName="HouseholdEnrollment";
    AppUtility.setCurrentImportRecordName("Household enrollment records");
    String filePath=appUtil.getExportFilePath()+appUtil.getFileSeperator()+exportFileName+appUtil.getFileSeperator();
       try
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+exportFileName+".xml";
                List files=getFiles(filePath);
                if(files !=null)
                {
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("patient");
                        HouseholdEnrollment hhe=null;
                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            hhe=new HouseholdEnrollment();
                            Node firstNode = listOfObjects.item(s);
                             if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                hhe.setHhUniqueId(getNodeValue("hhUniqueId",s,listOfObjects));
                                hhe.setHhSurname(getAppropriateStringLength(getNodeValue("hhSurname",s,listOfObjects),25));
                                hhe.setHhFirstname(getAppropriateStringLength(getNodeValue("hhFirstname",s,listOfObjects),25));
                                hhe.setAddress(getNodeValue("address",s,listOfObjects));
                                int hhAge=Integer.parseInt(getNodeValue("hhAge",s,listOfObjects));
                                hhe.setHhAge(hhAge);
                                hhe.setCurrentAge(util.getCurrentAge(getNodeValue("dateOfAssessment",s,listOfObjects), hhAge,"Year"));
                                hhe.setPhone(getAppropriateStringLength(getNodeValue("phone",s,listOfObjects),25));
                                hhe.setNoOfChildrenInhh(Integer.parseInt(getNodeValue("noOfChildrenInhh",s,listOfObjects)));
                                hhe.setNoOfPeopleInhh(Integer.parseInt(getNodeValue("noOfPeopleInhh",s,listOfObjects)));
                                hhe.setDateOfAssessment(getNodeValue("dateOfAssessment",s,listOfObjects));
                                hhe.setMaritalStatus(getNodeValue("maritalStatus",s,listOfObjects));

                                if(getNodeName("hhGender",s,listOfObjects) !=null)
                                hhe.setHhGender(getNodeValue("hhGender",s,listOfObjects));
                                hhe.setOccupation(getNodeValue("occupation",s,listOfObjects));
                                hhe.setEligibleForEnrollment(getAppropriateStringLength(getNodeValue("eligibleForEnrollment",s,listOfObjects),25));
                                hhe.setStateCode(getNodeValue("stateCode",s,listOfObjects));
                                hhe.setLgaCode(getNodeValue("lgaCode",s,listOfObjects));
                                hhe.setOrgCode(getAppropriateStringLength(getNodeValue("orgCode",s,listOfObjects),25));
                                hhe.setCommunityCode(getAppropriateStringLength(getNodeValue("communityCode",s,listOfObjects),25));
                                hhe.setPartnerCode(partnerCode);
                                //hhe.setPartnerCode(getAppropriateStringLength(getNodeValue("partnerCode",s,listOfObjects),25));
                                hhe.setRecordedBy(getNodeValue("recordedBy",s,listOfObjects));
                                hhe.setDateOfEntry(getNodeValue("dateOfEntry",s,listOfObjects));
                                
                                if(getNodeName("hhHeadship",s,listOfObjects) !=null)
                                {
                                    hhe.setHhHeadship(Integer.parseInt(getNodeValue("hhHeadship",s,listOfObjects)));
                                    hhe.setHealth(Integer.parseInt(getNodeValue("health",s,listOfObjects)));
                                    hhe.setEducationLevel(Integer.parseInt(getNodeValue("educationLevel",s,listOfObjects)));
                                    hhe.setShelterAndHousing(Integer.parseInt(getNodeValue("shelterAndHousing",s,listOfObjects)));
                                    hhe.setFoodSecurityAndNutrition(Integer.parseInt(getNodeValue("foodSecurityAndNutrition",s,listOfObjects)));
                                    hhe.setMeansOfLivelihood(Integer.parseInt(getNodeValue("meansOfLivelihood",s,listOfObjects)));
                                    hhe.setHhIncome(Integer.parseInt(getNodeValue("hhIncome",s,listOfObjects)));
                                    hhe.setHhvaId(Integer.parseInt(getNodeValue("hhvaId",s,listOfObjects)));
                                }
                                
                                hhe.setWithdrawnFromProgram(util.getWithdrawalStatus(hhe.getHhUniqueId()));
                                list.add(hhe);
                                removeDeletedWithdrawalRecord(hhe.getHhUniqueId());
                             }
                        }
                    }
	        }
                    //System.err.println("list size in Household enrollment is "+list.size());
                    //hheCompatibilityFlag=true;
                    createSavableHheList(session,list);
                    //list.clear();
	}
	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return list;
}
public List readHouseholdVulnebilityAssessmentFromXml(HttpSession session)
{
    List list=new ArrayList();
    /*if(compatibilityFlag || hvaCompatibilityFlag)
    {
        System.err.println("compatibilityFlag in Household assessment is "+compatibilityFlag+" and hvaCompatibilityFlag "+hvaCompatibilityFlag);
        return list;
    }*/
    String exportFileName="HouseholdVulnerabilityAssessment";
    AppUtility.setCurrentImportRecordName("Household vulnearability assessment records");
    String filePath=appUtil.getExportFilePath()+appUtil.getFileSeperator()+exportFileName+appUtil.getFileSeperator();
    //System.err.println("filePath in readHouseholdVulnebilityAssessmentFromXml is "+filePath);
       try
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+exportFileName+".xml";

                List files=getFiles(filePath);
                if(files !=null)
                {
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("patient");
                        HouseholdVulnerabilityAssessment hva=null;
                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            hva=new HouseholdVulnerabilityAssessment();
                            Node firstNode = listOfObjects.item(s);
                             if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                hva.setHhUniqueId(getNodeValue("hhUniqueId",s,listOfObjects));
                                hva.setDateOfAssessment(getNodeValue("dateOfAssessment",s,listOfObjects));
                                hva.setHhHeadship(Integer.parseInt(getNodeValue("hhHeadship",s,listOfObjects)));
                                hva.setHealth(Integer.parseInt(getNodeValue("health",s,listOfObjects)));
                                hva.setEducationLevel(Integer.parseInt(getNodeValue("educationLevel",s,listOfObjects)));
                                hva.setShelterAndHousing(Integer.parseInt(getNodeValue("shelterAndHousing",s,listOfObjects)));
                                hva.setFoodSecurityAndNutrition(Integer.parseInt(getNodeValue("foodSecurityAndNutrition",s,listOfObjects)));
                                hva.setMeansOfLivelihood(Integer.parseInt(getNodeValue("meansOfLivelihood",s,listOfObjects)));
                                hva.setHhIncome(Integer.parseInt(getNodeValue("hhIncome",s,listOfObjects)));
                                hva.setNameOfAssessor(getNodeValue("nameOfAssessor",s,listOfObjects));
                                hva.setDesignation(getNodeValue("designation",s,listOfObjects));
                                hva.setRecordedBy(getNodeValue("recordedBy",s,listOfObjects));
                                hva.setDateOfEntry(getNodeValue("dateOfEntry",s,listOfObjects));
                                hva.setAssessmentNo(Integer.parseInt(getNodeValue("assessmentNo",s,listOfObjects)));
                                hva.setVulnerabilityScore(Integer.parseInt(getNodeValue("vulnerabilityScore",s,listOfObjects)));
                                list.add(hva);
                             }
                        }
                    }
	        }
                    //System.err.println("list size in Household assessment is "+list.size());
                    createSavableHvaList(session,list);
                    hvaCompatibilityFlag=true;
                    list.clear();
	}
	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return list;
}
public List readCaregiverFromXml(HttpSession session)
{
    List list=new ArrayList();
    List subList=new ArrayList();
    List resultList=new ArrayList();
    int updateCount=0;
    int savedCount=0;
    Caregiver duplicateCgiver=null;
    CaregiverDao cgiverdao=new CaregiverDaoImpl();
    /*if(compatibilityFlag || cgiverCompatibilityFlag)
    {
        System.err.println("compatibilityFlag in Caregiver is "+compatibilityFlag+" and cgiverCompatibilityFlag is "+cgiverCompatibilityFlag);
        return list;
    }*/
    
    String exportFileName="Caregiver";
    AppUtility.setCurrentImportRecordName("Caregiver records");
    String filePath=appUtil.getExportFilePath()+appUtil.getFileSeperator()+exportFileName+appUtil.getFileSeperator();
       try
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                int count=0;
                Document doc = null;
		String fileName=filePath+exportFileName+".xml";
                List files=getFiles(filePath);
                if(files !=null)
                {
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("patient");
                        Caregiver cgiver=null;
                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            cgiver=new Caregiver();
                            Node firstNode = listOfObjects.item(s);
                             if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                cgiver.setHhUniqueId(getNodeValue("hhUniqueId",s,listOfObjects));
                                cgiver.setCaregiverLastName(getAppropriateStringLength(getNodeValue("caregiverLastName",s,listOfObjects),25));
                                cgiver.setCaregiverFirstname(getAppropriateStringLength(getNodeValue("caregiverFirstname",s,listOfObjects),25));
                                cgiver.setCaregiverAddress(getNodeValue("caregiverAddress",s,listOfObjects));
                                cgiver.setCaregiverGender(getNodeValue("caregiverGender",s,listOfObjects));
                                cgiver.setCaregiverAge(Integer.parseInt(getNodeValue("caregiverAge",s,listOfObjects)));
                                cgiver.setCaregiverId(getNodeValue("caregiverId",s,listOfObjects));
                                cgiver.setCaregiverOccupation(getNodeValue("caregiverOccupation",s,listOfObjects));
                                String nodeName=getNodeName("caregiverMaritalStatus",s,listOfObjects);
                                if(nodeName !=null)
                                cgiver.setCaregiverMaritalStatus(getNodeValue("caregiverMaritalStatus",s,listOfObjects));
                                cgiver.setCaregiverPhone(getNodeValue("caregiverPhone",s,listOfObjects));
                                String dateOfEnrollment=getNodeValue("dateOfEnrollment",s,listOfObjects);
                                String dateModified=getNodeValue("dateModified",s,listOfObjects);
                                if(dateOfEnrollment==null || dateOfEnrollment.indexOf("-") ==-1)
                                dateOfEnrollment=dateModified;
                                cgiver.setDateOfEnrollment(dateOfEnrollment);
                                cgiver.setDateModified(dateModified);
                                
                                cgiver.setWithdrawnFromProgram(util.getWithdrawalStatus(cgiver.getCaregiverId()));
                                nodeName=getNodeName("cgiverHivStatus",s,listOfObjects);
                                removeDeletedWithdrawalRecord(cgiver.getCaregiverId());
                                if(nodeName !=null)
                                cgiver.setCgiverHivStatus(getNodeValue("cgiverHivStatus",s,listOfObjects));
                                duplicateCgiver=cgiverdao.getCaregiverByCaregiverId(cgiver.getCaregiverId());
                                if(duplicateCgiver==null)
                                {
                                    cgiverdao.saveCaregiver(cgiver);
                                    savedCount++;
                                    count++;
                                    AppUtility.setCurrentImportRecordName("Caregiver records: "+count+" saved");
                                }
                                else
                                {
                                    cgiverdao.updateCaregiver(cgiver);
                                    updateCount++;
                                    count++;
                                    AppUtility.setCurrentImportRecordName("Caregiver records: "+count+" saved");
                                }
                                //String cgiverHivStatus=
                                //list.add(cgiver);
                             }
                        }
                    }
	        }
                subList.add("Number of new caregiver records");
                subList.add(savedCount);
                resultList.add(subList);
                subList=new ArrayList();
                subList.add("Number of updates caregiver records ");
                subList.add(updateCount);
                    //System.err.println("list size in Caregiver is "+list.size());
                    createSavableCaregiverList(session,list);
                    cgiverCompatibilityFlag=true;
                    list.clear();
	}

	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return resultList;
}
private String getAppropriateStringLength(String str,int length)
{
    if(str !=null)
    {
        str=str.trim();
        if(str.length()>length-1)
        str=str.substring(0, length-2);
    }
    return str;
}
public List readHouseholdServiceFromXml(HttpSession session)
{
    List list=new ArrayList();
    String directoryName="HouseholdService";
    AppUtility.setCurrentImportRecordName("Household service records");
    String filePath=appUtil.getExportFilePath()+appUtil.getFileSeperator()+directoryName+appUtil.getFileSeperator();
       try
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+directoryName+".xml";
                String serviceNodeName=null;
                List files=getFiles(filePath);
                if(files !=null)
                {
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("patient");
                        HouseholdService hhs=null;
                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            hhs=new HouseholdService();
                            Node firstPersonNode = listOfObjects.item(s);
                             if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                if(getNodeName("caregiverId",s,listOfObjects) !=null)
                                hhs.setCaregiverId(getNodeValue("caregiverId",s,listOfObjects));
                                hhs.setId(Integer.parseInt(getNodeValue("id",s,listOfObjects)));
                                hhs.setHhUniqueId(getNodeValue("hhUniqueId",s,listOfObjects));
                                hhs.setServiceDate(getNodeValue("serviceDate",s,listOfObjects));
                                hhs.setEconomicStrengtheningServices(getNodeValue("economicStrengtheningServices",s,listOfObjects));
                                hhs.setEducationalServices(getNodeValue("educationalServices",s,listOfObjects));
                                hhs.setHealthServices(getNodeValue("healthServices",s,listOfObjects));
                                hhs.setNutritionalServices(getNodeValue("nutritionalServices",s,listOfObjects));
                                hhs.setProtectionServices(getNodeValue("protectionServices",s,listOfObjects));
                                hhs.setPsychosocialSupportServices(getNodeValue("psychosocialSupportServices",s,listOfObjects));
                                hhs.setShelterAndCareServices(getNodeValue("shelterAndCareServices",s,listOfObjects));
                                hhs.setVolunteerDesignation(getNodeValue("volunteerDesignation",s,listOfObjects));
                                hhs.setVolunteerName(getNodeValue("volunteerName",s,listOfObjects));
                                hhs.setRecordedBy(getNodeValue("recordedBy",s,listOfObjects));
                                hhs.setDateOfEntry(getNodeValue("dateOfEntry",s,listOfObjects));
                                hhs.setServiceNo(Integer.parseInt(getNodeValue("serviceNo",s,listOfObjects)));
                                serviceNodeName=getNodeName("numberOfServices",s,listOfObjects);
                                if(serviceNodeName !=null)
                                hhs.setNumberOfServices(Integer.parseInt(getNodeValue("numberOfServices",s,listOfObjects)));
                                serviceNodeName=getNodeName("serviceRecipientType",s,listOfObjects);
                                if(serviceNodeName !=null)
                                hhs.setServiceRecipientType(getNodeValue("serviceRecipientType",s,listOfObjects));
                                list.add(hhs);
                             }
                        }
                    }
	        }
                createSavableHhServiceList(session,list);
                list.clear();
	}

	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return list;
}
public List readHouseholdFollowupAssessmentFromXml()
{
    List list=new ArrayList();
    String directoryName="HouseholdFollowupAssessment";
    AppUtility.setCurrentImportRecordName("Household Followup Assessment records");
    String filePath=appUtil.getExportFilePath()+appUtil.getFileSeperator()+directoryName+appUtil.getFileSeperator();
       try
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+directoryName+".xml";
                List files=getFiles(filePath);
                List newRecordsList=new ArrayList();
                List duplicateRecordsList=new ArrayList();
                newRecordsList.add("Number of new Household follow up records saved");
                duplicateRecordsList.add("Number of Household follow up records saved as updates");
                int newRecordsCount=0;
                int duplicateRecordsCount=0;
                int count=0;
                if(files !=null)
                {
                    HouseholdFollowupAssessmentDao hhfadao=new HouseholdFollowupAssessmentDaoImpl();
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("patient");
                        HouseholdFollowupAssessment hhfa=null;
                        HouseholdFollowupAssessment dupHhfa=null;
                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            hhfa=new HouseholdFollowupAssessment();
                            Node firstPersonNode = listOfObjects.item(s);
                             if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                hhfa.setRecordId(getNodeValue("recordId",s,listOfObjects));
                                hhfa.setHhUniqueId(getNodeValue("hhUniqueId",s,listOfObjects));
                                hhfa.setDateOfAssessment(getNodeValue("dateOfAssessment",s,listOfObjects));
                                hhfa.setDateModified(getNodeValue("dateModified",s,listOfObjects));
                                hhfa.setDesignation(getNodeValue("designation",s,listOfObjects));
                                hhfa.setUpdatedAddress(getNodeValue("updatedAddress",s,listOfObjects));
                                hhfa.setPhone(getNodeValue("phone",s,listOfObjects));
                                hhfa.setNoOfChildrenInhh(Integer.parseInt(getNodeValue("noOfChildrenInhh",s,listOfObjects)));
                                hhfa.setNoOfPeopleInhh(Integer.parseInt(getNodeValue("noOfPeopleInhh",s,listOfObjects)));
                                hhfa.setOccupation(getNodeValue("occupation",s,listOfObjects));
                                hhfa.setReasonWithdrawal(getNodeValue("reasonWithdrawal",s,listOfObjects));
                                hhfa.setNameOfAssessor(getNodeValue("nameOfAssessor",s,listOfObjects));
                                hhfa.setRecordedBy(getNodeValue("recordedBy",s,listOfObjects));
                                
                                if(getNodeName("hhHeadship",s,listOfObjects) !=null) 
                                {
                                    hhfa.setHhHeadship(Integer.parseInt(getNodeValue("hhHeadship",s,listOfObjects)));
                                    hhfa.setHealth(Integer.parseInt(getNodeValue("health",s,listOfObjects)));
                                    hhfa.setEducationLevel(Integer.parseInt(getNodeValue("educationLevel",s,listOfObjects)));
                                    hhfa.setShelterAndHousing(Integer.parseInt(getNodeValue("shelterAndHousing",s,listOfObjects)));
                                    hhfa.setFoodSecurityAndNutrition(Integer.parseInt(getNodeValue("foodSecurityAndNutrition",s,listOfObjects)));
                                    hhfa.setMeansOfLivelihood(Integer.parseInt(getNodeValue("meansOfLivelihood",s,listOfObjects)));
                                    hhfa.setHhIncome(Integer.parseInt(getNodeValue("hhIncome",s,listOfObjects)));
                                    hhfa.setHhvaId(Integer.parseInt(getNodeValue("hhvaId",s,listOfObjects)));
                                    hhfa.setVulnerabilityScore(hhfadao.getFollowupVulnerabilityScore(hhfa));
                                }
                                dupHhfa=hhfadao.getHouseholdFollowupAssessmentByIdAndDate(hhfa.getHhUniqueId(), hhfa.getDateOfAssessment());
                                if(dupHhfa==null)
                                {
                                    hhfadao.saveHouseholdFollowupAssessment(hhfa);
                                    newRecordsCount++;
                                    count++;
                                    AppUtility.setCurrentImportRecordName("Household Followup Assessment records: "+count+" saved");
                                }
                                else
                                {
                                    hhfa.setRecordId(dupHhfa.getRecordId());
                                    if(hhfadao.getFollowupVulnerabilityScore(hhfa)<7 && hhfadao.getFollowupVulnerabilityScore(dupHhfa)>6)
                                    {
                                        hhfa.setHealth(dupHhfa.getHealth());
                                        hhfa.setHhHeadship(dupHhfa.getHhHeadship());
                                        hhfa.setHhIncome(dupHhfa.getHhIncome());
                                        hhfa.setEducationLevel(dupHhfa.getEducationLevel());
                                        hhfa.setFoodSecurityAndNutrition(dupHhfa.getFoodSecurityAndNutrition());
                                        hhfa.setMeansOfLivelihood(dupHhfa.getMeansOfLivelihood());
                                        hhfa.setShelterAndHousing(dupHhfa.getShelterAndHousing());
                                        hhfa.setHhvaId(dupHhfa.getHhvaId());
                                        hhfa.setVulnerabilityScore(hhfadao.getFollowupVulnerabilityScore(hhfa));
                                    }
                                    hhfadao.updateHouseholdFollowupAssessment(hhfa);
                                    duplicateRecordsCount++;
                                    count++;
                                    AppUtility.setCurrentImportRecordName("Household Followup Assessment records: "+count+" saved");
                                }
                                //list.add(hhfa);
                             }
                        }
                    }
	        }
                newRecordsList.add(newRecordsCount);
                duplicateRecordsList.add(duplicateRecordsCount);
                
                list.add(newRecordsList);
                list.add(duplicateRecordsList);
	}

	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return list;
}

public List readOvcFromXml(HttpSession session,boolean hivUpdate,boolean birthRegUpdate)
{
    List list=new ArrayList();
    List hheList=new ArrayList();
    List resultList=new ArrayList();
    List subList=new ArrayList();
    
    int saveCount=0;
    int updateCount=0;
    
    CaregiverDao cgiverDao=new CaregiverDaoImpl();
    OvcDao ovcdao=new OvcDaoImpl();
    String directoryName="Enrollment";
    AppUtility.setCurrentImportRecordName("OVC enrollment records");
    String ovcId=null;
    
    String filePath=appUtil.getExportFilePath()+appUtil.getFileSeperator()+directoryName+appUtil.getFileSeperator();
       try
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+directoryName+".xml";
                List files=getFiles(filePath);
                Ovc existingOvc=null;
                int count=0;
                int totalCount=0;
                if(files !=null)
                {
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println("Filepath in readOvcFromXml is "+filePath+files.get(i).toString()+" ");
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        //System.err.println("Filepath in readOvcFromXml after file.exist() test is "+filePath+files.get(i).toString()+" ");
                        ovcId=null;
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("patient");
                        ovcId=getNodeValue("ovcId",0,listOfObjects);
                        String hhUniqueIdNodeName=getNodeName("hhUniqueId",0,listOfObjects);
                        //check if this export is from v2.2         
                        if(hhUniqueIdNodeName !=null)
                        {          
                            for(int s=0; s<listOfObjects.getLength() ; s++)
                            {
                                //System.err.println("Filepath in readOvcFromXml after hhUniqueIdNodeName at "+s+" is "+filePath+files.get(i).toString()+" ");
                                Ovc ovc=new Ovc();
                                Node firstNode = listOfObjects.item(s);
                                 if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                                 {
                                    ovc.setOvcId(getNodeValue("ovcId",s,listOfObjects));
                                    ovc.setHhUniqueId(getNodeValue("hhUniqueId",s,listOfObjects));
                                    ovc.setCaregiverId(getNodeValue("caregiverId",s,listOfObjects));

                                    //System.err.println("Filepath in readOvcFromXml after Node.ELEMENT_NODE at "+s+" is "+filePath+files.get(i).toString()+" ");
                                    
                                    ovc.setDateEnrollment(getNodeValue("dateEnrollment",s,listOfObjects));
                                    ovc.setAge(Integer.parseInt(getNodeValue("age",s,listOfObjects)));
                                    ovc.setAgeUnit(getNodeValue("ageUnit",s,listOfObjects));
                                    ovc.setFirstName(getNodeValue("firstName",s,listOfObjects));
                                    ovc.setLastName(getNodeValue("lastName",s,listOfObjects));
                                    ovc.setAddress(getNodeValue("address",s,listOfObjects));
                                    ovc.setPhone(getNodeValue("phone",s,listOfObjects));
                                    ovc.setHivStatus(getNodeValue("hivStatus",s,listOfObjects));
                                    ovc.setEligibility(getNodeValue("eligibility",s,listOfObjects));
                                    ovc.setGender(getNodeValue("gender",s,listOfObjects));
                                    ovc.setBirthCertificate(getNodeValue("birthCertificate",s,listOfObjects));
                                    ovc.setSchoolStatus(getNodeValue("schoolStatus",s,listOfObjects));
                                    ovc.setSchoolType(getNodeValue("schoolType",s,listOfObjects));
                                    ovc.setSchoolName(getNodeValue("schoolName",s,listOfObjects));
                                    ovc.setCurrentClass(getNodeValue("currentClass",s,listOfObjects));
                                    ovc.setWeight(new Double(getNodeValue("weight",s,listOfObjects)));
                                    ovc.setHeight(new Double(getNodeValue("height",s,listOfObjects)));
                                    ovc.setCaregiverRelationships(getNodeValue("caregiverRelationship",s,listOfObjects));
                                    ovc.setCompletedbyName(getNodeValue("completedByName",s,listOfObjects));
                                    ovc.setCompletedbyDesignation(getNodeValue("completedByDesignation",s,listOfObjects));
                                    ovc.setVerifiedBy(getNodeValue("verifiedBy",s,listOfObjects));
                                    ovc.setDateOfEntry(getNodeValue("dateOfEntry",s,listOfObjects));
                                    ovc.setRecordedBy(getNodeValue("recordedBy",s,listOfObjects));
                                    ovc.setPartner(getNodeValue("partner",s,listOfObjects));
                                    ovc.setWithdrawnFromProgram(util.getWithdrawalStatus(ovc.getOvcId()));

                                    String sourceOfInfo=getNodeName("sourceOfInfo",s,listOfObjects);
                                    if(sourceOfInfo !=null)
                                    ovc.setSourceOfInfo(getNodeValue("sourceOfInfo",s,listOfObjects));
                                    ovc=util.getOvcWithCurrentAge(ovc);
                                    removeDeletedWithdrawalRecord(ovc.getOvcId());
                                    //ovc.setCurrentAge(util.getCurrentAge(ovc));
                                    
                                    if(ovc.getCaregiverId()==null)
                                    {
                                        //System.err.println("Filepath in readOvcFromXml inside ovc.getCaregiverId() at "+s+" is "+filePath+files.get(i).toString()+" ");
                                        List cgiverListPerHh=cgiverDao.getListOfCaregiversFromSameHousehold(ovc.getHhUniqueId());
                                        if(cgiverListPerHh !=null && !cgiverListPerHh.isEmpty())
                                        {
                                            Caregiver existingCgiver=(Caregiver)cgiverListPerHh.get(0);
                                            ovc.setCaregiverId(existingCgiver.getCaregiverId());
                                            //System.err.println("existingCgiver.getCaregiverId() is "+existingCgiver.getCaregiverId());
                                        }
                                    }
                                    existingOvc=ovcdao.getOvc(ovc.getOvcId());
                                    //System.err.println("Filepath in readOvcFromXml after existingOvc is "+filePath+files.get(i).toString()+" ");
                                    if(existingOvc ==null)
                                    {
                                        //System.err.println("Filepath in readOvcFromXml inside existingOvc==null is "+filePath+files.get(i).toString()+" ");
                                        ovcdao.saveOvc(ovc,hivUpdate,birthRegUpdate);
                                        saveCount++;
                                        count++;
                                        AppUtility.setCurrentImportRecordName("OVC enrollment records: "+count);
                                        
                                    }
                                    else
                                    {
                                        count++;
                                        //System.err.println("Filepath in readOvcFromXml inside existingOvc not null at "+s+" is "+filePath+files.get(i).toString()+" ");
                                        ovcdao.updateOvc(ovc,hivUpdate,birthRegUpdate);
                                        updateCount++;
                                        AppUtility.setCurrentImportRecordName("OVC enrollment records: "+count);
                                        
                                    }
                                    //list.add(ovc);
                                 }
                            }
                        }   
                        else //this is from a version 2.0 or 2.1
                        {
                            if(ovcId.indexOf("/") !=-1)
                            {
                                String[] ovcIdArray=ovcId.split("/");
                                if(ovcIdArray.length>4)
                                {//this is from v2.1
                                    readOvcDataFromv21Xml(hivUpdate,birthRegUpdate);
                                    break;
                                }
                                else
                                {//this is from v2.0
                                    readOvcDataFromv20Xml(hivUpdate,birthRegUpdate);
                                    break;
                                }
                            }
                        }        
                  }
	        }
                subList.add("Number of new OVC records save ");
                subList.add(saveCount);
                resultList.add(subList);
                subList=new ArrayList();
                subList.add("Number of OVC records saved as updates");
                subList.add(updateCount);
                resultList.add(subList);
	}
	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		//((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return resultList;
}

public List readOvcDataFromv21Xml(boolean hivUpdate,boolean birthRegUpdate)
{
    List resultList=new ArrayList();
    List subList=new ArrayList();
    int saveCount=0;
    int updateCount=0;
    Caregiver cgiver=null;
    
    
    CaregiverDao cgiverDao=new CaregiverDaoImpl();
    
    OvcDao ovcdao=new OvcDaoImpl();
    String directoryName="Enrollment";
    AppUtility.setCurrentImportRecordName("OVC Enrollment records (v2.1)");
    String ovcId=null;
    String hhUniqueId=null;
    
    String filePath=appUtil.getExportFilePath()+appUtil.getFileSeperator()+directoryName+appUtil.getFileSeperator();
       try
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+directoryName+".xml";
                List files=getFiles(filePath);
                Ovc existingOvc=null;
                if(files !=null)
                {
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        ovcId=null;
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("patient");

                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            Ovc ovc=new Ovc();
                            Node firstNode = listOfObjects.item(s);
                             if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                ovcId=getNodeValue("ovcId",s,listOfObjects);
                                String hhUniqueIdNodeName=getNodeName("hhUniqueId",s,listOfObjects);
                                //check if this export is from v2.2
                                if(hhUniqueIdNodeName !=null)
                                hhUniqueId=getNodeValue("hhUniqueId",s,listOfObjects);
                                else //this is from a version 2.0 or 2.1
                                {
                                    if(ovcId.indexOf("/") !=-1)
                                    {
                                        hhUniqueId=ovcId.substring(0, ovcId.lastIndexOf("/"));                                         
                                    }
                                }
                                ovc.setHhUniqueId(hhUniqueId);
                                ovc.setOvcId(ovcId);
                                ovc.setDateEnrollment(getNodeValue("dateEnrollment",s,listOfObjects));
                                ovc.setAge(Integer.parseInt(getNodeValue("age",s,listOfObjects)));
                                ovc.setAgeUnit(getNodeValue("ageUnit",s,listOfObjects));
                                ovc.setFirstName(getNodeValue("firstName",s,listOfObjects));
                                ovc.setLastName(getNodeValue("lastName",s,listOfObjects));
                                ovc.setAddress(getNodeValue("address",s,listOfObjects));
                                ovc.setPhone(getNodeValue("phone",s,listOfObjects));
                                ovc.setHivStatus(getNodeValue("hivStatus",s,listOfObjects));
                                ovc.setEligibility(getNodeValue("eligibility",s,listOfObjects));
                                ovc.setGender(getNodeValue("gender",s,listOfObjects));
                                ovc.setBirthCertificate(getNodeValue("birthCertificate",s,listOfObjects));
                                ovc.setSchoolStatus(getNodeValue("schoolStatus",s,listOfObjects));
                                ovc.setSchoolType(getNodeValue("schoolType",s,listOfObjects));
                                ovc.setSchoolName(getNodeValue("schoolName",s,listOfObjects));
                                ovc.setCurrentClass(getNodeValue("currentClass",s,listOfObjects));
                                ovc.setWeight(new Double(getNodeValue("weight",s,listOfObjects)));
                                ovc.setHeight(new Double(getNodeValue("height",s,listOfObjects)));
                                ovc.setCaregiverRelationships(getNodeValue("caregiverRelationship",s,listOfObjects));
                                ovc.setCompletedbyName(getNodeValue("completedByName",s,listOfObjects));
                                ovc.setCompletedbyDesignation(getNodeValue("completedByDesignation",s,listOfObjects));
                                ovc.setVerifiedBy(getNodeValue("verifiedBy",s,listOfObjects));
                                ovc.setDateOfEntry(getNodeValue("dateOfEntry",s,listOfObjects));
                                ovc.setRecordedBy(getNodeValue("recordedBy",s,listOfObjects));
                                ovc.setPartner(getNodeValue("partner",s,listOfObjects));
                                
                                ovc.setWithdrawnFromProgram(util.getWithdrawalStatus(ovc.getOvcId()));
                                String sourceOfInfo=getNodeName("sourceOfInfo",s,listOfObjects);
                                if(sourceOfInfo !=null)
                                ovc.setSourceOfInfo(getNodeValue("sourceOfInfo",s,listOfObjects));
                                ovc=util.getOvcWithCurrentAge(ovc);
                                //ovc.setCurrentAge(util.getCurrentAge(ovc));
                                String caregiverId=null;
                                
                                List cgiverList=cgiverDao.getListOfCaregiversFromSameHousehold(hhUniqueId);                               
                                if(cgiverList !=null && !cgiverList.isEmpty())
                                {
                                    cgiver=(Caregiver)cgiverList.get(0);
                                    caregiverId=cgiver.getCaregiverId();
                                }
                                ovc.setCaregiverId(caregiverId);                              
                                existingOvc=ovcdao.getOvc(ovc.getOvcId());
                                
                                if(existingOvc ==null)
                                {
                                    ovcdao.saveOvc(ovc,hivUpdate,birthRegUpdate);
                                    saveCount++;
                                }
                                else
                                {
                                    ovcdao.updateOvc(ovc,hivUpdate,birthRegUpdate);
                                    updateCount++;
                                }
                             }
                        }
                    }
	        }
                subList.add("Number of new OVC records save ");
                subList.add(saveCount);
                resultList.add(subList);
                subList=new ArrayList();
                subList.add("Number of OVC records saved as updates");
                subList.add(updateCount);
                resultList.add(subList);
	}

	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return resultList;
}
public List readOvcDataFromv20Xml(boolean hivUpdate,boolean birthRegUpdate)
{
    List resultList=new ArrayList();
    List list=new ArrayList();
    List subList=new ArrayList();
    int saveCount=0;
    int updateCount=0;
    HouseholdEnrollment hhe=null;
    Caregiver cgiver=null;
    HouseholdVulnerabilityAssessment hva=null;
    HouseholdVulnerabilityAssessment existinghva=null;
    CaregiverDao cgiverDao=new CaregiverDaoImpl();
    HouseholdEnrollmentDao hhedao=new HouseholdEnrollmentDaoImpl();
    HouseholdVulnerabilityAssessmentDao hvaDao=new HouseholdVulnerabilityAssessmentDaoImpl();
    OvcDao ovcdao=new OvcDaoImpl();
    String directoryName="Enrollment";
    AppUtility.setCurrentImportRecordName("OVC Enrollment records (v 2.0)");
    String ovcId=null;
    String hhUniqueId=null;
    String caregiverNames=null;
    String caregiverFirstName="Unknown";
    String caregiverSurnameName="Unknown";
    String[] caregiverNameArray=null;
    String stateCode=null;
    String lgaCode=null;
    String orgCode=null;
    String wardCode=null;
    String hhFirstName="Unknown";
    String hhSurname="Unknown";
    Integer caregiverAge=null;
    String filePath=appUtil.getExportFilePath()+appUtil.getFileSeperator()+directoryName+appUtil.getFileSeperator();
   try
    {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        File file=null;
        Document doc = null;
        String fileName=filePath+directoryName+".xml";
        List files=getFiles(filePath);
        Ovc existingOvc=null;
        if(files !=null)
        {
            for(int i=0; i<files.size(); i++)
            {
                //System.err.println(filePath+files.get(i).toString());
                fileName=filePath+files.get(i).toString();
                 file=new File(fileName);
                if(!file.exists())
                {
                    continue;
                }
                ovcId=null;
                doc = docBuilder.parse (file);
                // normalize text representation
                doc.getDocumentElement().normalize();
                NodeList listOfObjects = doc.getElementsByTagName("patient");

                for(int s=0; s<listOfObjects.getLength() ; s++)
                {
                    Ovc ovc=new Ovc();
                    Node firstNode = listOfObjects.item(s);
                     if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                     {
                        ovcId=getNodeValue("ovcId",s,listOfObjects);
                        stateCode=getNodeValue("state",s,listOfObjects);
                        lgaCode=getNodeValue("lga",s,listOfObjects);
                        orgCode=getNodeValue("completedbyCbo",s,listOfObjects);
                        wardCode=getNodeValue("ward",s,listOfObjects);

                        if(ovcId.indexOf("/") ==-1)
                        continue;
                        hhUniqueId=ovcId;
                        ovc.setHhUniqueId(hhUniqueId);

                        String[] ovcIdParts=ovcId.split("/");
                        ovcId=ovcId+"/"+ovcIdParts[3];

                        ovc.setOvcId(ovcId);
                        ovc.setDateEnrollment(getNodeValue("dateEnrollment",s,listOfObjects));
                        ovc.setAge(Integer.parseInt(getNodeValue("age",s,listOfObjects)));
                        ovc.setAgeUnit(getNodeValue("ageUnit",s,listOfObjects));
                        ovc.setFirstName(getNodeValue("firstName",s,listOfObjects));
                        ovc.setLastName(getNodeValue("lastName",s,listOfObjects));
                        ovc.setAddress(getNodeValue("address",s,listOfObjects));
                        ovc.setPhone(getNodeValue("phone",s,listOfObjects));
                        ovc.setHivStatus(getNodeValue("hivStatus",s,listOfObjects));
                        ovc.setEligibility(getNodeValue("eligibility",s,listOfObjects));
                        ovc.setGender(getNodeValue("gender",s,listOfObjects));
                        ovc.setBirthCertificate(getNodeValue("birthCertificate",s,listOfObjects));
                        ovc.setSchoolStatus(getNodeValue("schoolStatus",s,listOfObjects));
                        ovc.setSchoolType(getNodeValue("schoolType",s,listOfObjects));
                        ovc.setSchoolName(getNodeValue("schoolName",s,listOfObjects));
                        ovc.setCurrentClass(getNodeValue("currentClass",s,listOfObjects));
                        ovc.setWeight(new Double(getNodeValue("weight",s,listOfObjects)));
                        ovc.setHeight(new Double(getNodeValue("height",s,listOfObjects)));
                        ovc.setCaregiverRelationships(getNodeValue("caregiverRelationship",s,listOfObjects));
                        ovc.setCompletedbyName(getNodeValue("completedByName",s,listOfObjects));
                        ovc.setCompletedbyDesignation(getNodeValue("completedByDesignation",s,listOfObjects));
                        ovc.setVerifiedBy(getNodeValue("verifiedBy",s,listOfObjects));
                        ovc.setDateOfEntry(getNodeValue("dateOfEntry",s,listOfObjects));
                        ovc.setRecordedBy(getNodeValue("recordedBy",s,listOfObjects));
                        ovc.setPartner(getNodeValue("partner",s,listOfObjects));
                        
                        ovc.setWithdrawnFromProgram(util.getWithdrawalStatus(ovc.getOvcId()));
                        String caregiverId=null;

                        String sourceOfInfo=getNodeName("sourceOfInfo",s,listOfObjects);
                        if(sourceOfInfo !=null)
                        ovc.setSourceOfInfo(getNodeValue("sourceOfInfo",s,listOfObjects));
                        ovc=util.getOvcWithCurrentAge(ovc);
                        //ovc.setCurrentAge(util.getCurrentAge(ovc));

                        String nodeName=getNodeName("caregiverName",s,listOfObjects);
                        if(nodeName !=null)
                        {
                            hhe=new HouseholdEnrollment();
                            hva=new HouseholdVulnerabilityAssessment();
                            cgiver=new Caregiver();
                            if(getNodeValue("hhFirstname",s,listOfObjects) !=null && !getNodeValue("hhFirstname",s,listOfObjects).equalsIgnoreCase(""))
                            hhFirstName=getNodeValue("hhFirstname",s,listOfObjects);
                            else
                            hhFirstName="Unknown";
                            if(getNodeValue("hhSurname",s,listOfObjects) !=null && !getNodeValue("hhSurname",s,listOfObjects).equalsIgnoreCase(""))
                            hhSurname=getNodeValue("hhSurname",s,listOfObjects);
                            else
                            hhSurname="Unknown";
                            caregiverNames=getNodeValue("caregiverName",s,listOfObjects);
                            if(caregiverNames !=null)
                            {
                                if(caregiverNames.indexOf(",") !=-1)
                                {
                                    caregiverNameArray=caregiverNames.split(",");
                                    if(caregiverNameArray.length> 0)
                                    caregiverSurnameName=caregiverNameArray[0];
                                    if(caregiverNameArray.length> 1)
                                    caregiverFirstName=caregiverNameArray[1];

                                }
                                else if(caregiverNames.indexOf(" ") !=-1)
                                {
                                    caregiverNameArray=caregiverNames.split(" ");
                                    if(caregiverNameArray.length> 0)
                                    caregiverSurnameName=caregiverNameArray[0];
                                    if(caregiverNameArray.length > 1)
                                    caregiverFirstName=caregiverNameArray[1];
                                }
                                else
                                {
                                    caregiverFirstName=caregiverNames;
                                    caregiverSurnameName=caregiverNames;
                                }
                            }
                            else
                            {
                                caregiverFirstName=hhFirstName;
                                caregiverSurnameName=hhSurname;
                            }

                            hhe.setStateCode(stateCode);
                            hhe.setLgaCode(lgaCode);
                            hhe.setOrgCode(getAppropriateStringLength(orgCode,25));
                            hhe.setCommunityCode(getAppropriateStringLength(wardCode,25));
                            hhe.setHhUniqueId(hhUniqueId);
                            hhe.setHhFirstname(getAppropriateStringLength(hhFirstName,25));
                            hhe.setHhSurname(getAppropriateStringLength(hhSurname,25));
                            hhe.setAddress(getNodeValue("caregiveraddress",s,listOfObjects));
                            hhe.setOccupation(getNodeValue("caregiverOccupation",s,listOfObjects));
                            hhe.setPhone(getAppropriateStringLength(getNodeValue("caregiverPhone",s,listOfObjects),25));
                            hhe.setDateOfAssessment(ovc.getDateEnrollment());
                            hhe.setDateOfEntry(getNodeValue("dateOfEntry",s,listOfObjects));
                            hhe.setRecordedBy(getNodeValue("recordedBy",s,listOfObjects));
                            hhe.setPartnerCode(getNodeValue("partner",s,listOfObjects));
                            caregiverAge=Integer.parseInt(getNodeValue("caregiverAge",s,listOfObjects));
                            hhe.setHhAge(caregiverAge);
                            hhe.setCurrentAge(util.getCurrentAge(ovc.getDateEnrollment(), caregiverAge,"Year"));
                            hhe.setEligibleForEnrollment("Yes");
                            hhe.setNoOfPeopleInhh(Integer.parseInt(getNodeValue("numOfChildrenInHh",s,listOfObjects)));
                            hhe.setNoOfChildrenInhh(Integer.parseInt(getNodeValue("numOfOVCInHh",s,listOfObjects)));

                            hva.setAssessmentNo(1);
                            hva.setHhUniqueId(hhUniqueId);
                            hva.setDateOfAssessment(ovc.getDateEnrollment());
                            hva.setDateOfEntry(ovc.getDateOfEntry());
                            hva.setDesignation(ovc.getCompletedbyDesignation());
                            hva.setRecordedBy(ovc.getCompletedbyName());
                            hva.setNameOfAssessor(ovc.getCompletedbyName());

                            if(caregiverAge ==null)
                            caregiverAge=0;
                            else if(caregiverAge >199)
                            caregiverAge=200;
                            caregiverId=cgiverDao.generateCaregiverId(hhUniqueId);
                            cgiver.setCaregiverId(caregiverId);
                            cgiver.setHhUniqueId(hhUniqueId);
                            cgiver.setCaregiverAge(caregiverAge);
                            caregiverFirstName=getAppropriateStringLength(caregiverFirstName,25);
                            caregiverSurnameName=getAppropriateStringLength(caregiverSurnameName,25);
                            cgiver.setCaregiverFirstname(caregiverFirstName);
                            cgiver.setCaregiverLastName(caregiverSurnameName);
                            cgiver.setCaregiverAddress(getNodeValue("caregiveraddress",s,listOfObjects));
                            cgiver.setCaregiverPhone(getAppropriateStringLength(getNodeValue("caregiverPhone",s,listOfObjects),25));
                            cgiver.setCaregiverGender(getNodeValue("caregiverGender",s,listOfObjects));
                            cgiver.setCaregiverOccupation(getNodeValue("caregiverOccupation",s,listOfObjects));
                            cgiver.setDateOfEnrollment(getNodeValue("dateEnrollment",s,listOfObjects));
                            cgiver.setDateModified(getNodeValue("dateOfEntry",s,listOfObjects));
                            if(hhedao.getHouseholdEnrollment(hhe.getHhUniqueId())==null)
                            hhedao.saveHouseholdEnrollment(hhe);
                            else 
                            hhedao.updateHouseholdEnrollment(hhe);
                            existinghva=hvaDao.getHouseholdVulnerabilityAssessment(hva.getHhUniqueId(), hva.getDateOfAssessment());
                            if(existinghva==null)
                            hvaDao.saveHouseholdVulnerabilityAssessment(hva);
                            else
                            {
                                hva.setId(existinghva.getId());
                                hvaDao.updateHouseholdVulnerabilityAssessmentByuniqueIdAndDate(hva);
                            }
                            Caregiver existingCgiver=cgiverDao.getCaregiverByName(ovc.getHhUniqueId(), caregiverFirstName, caregiverSurnameName);
                            if(existingCgiver==null)
                            cgiverDao.saveCaregiver(cgiver);
                            else
                            {
                                caregiverId=existingCgiver.getCaregiverId();
                                cgiver.setCaregiverId(caregiverId);
                                cgiverDao.updateCaregiver(cgiver);
                            }
                            ovc.setCaregiverId(caregiverId); 
                        }
                        existingOvc=ovcdao.getOvc(ovcId);
                        if(existingOvc ==null)
                        {
                            ovcdao.saveOvc(ovc,hivUpdate,birthRegUpdate);
                            saveCount++;
                        }
                        else
                        {
                            ovcdao.updateOvc(ovc,hivUpdate,birthRegUpdate);
                            updateCount++;
                        }
                     }
                }
            }
        }
        subList.add("Number of new OVC records save ");
        subList.add(saveCount);
        resultList.add(subList);
        subList=new ArrayList();
        subList.add("Number of OVC records saved as updates");
        subList.add(updateCount);
        resultList.add(subList);

        list.clear();
    }
    catch (SAXParseException err)
    {
        err.printStackTrace();
    }
    catch (SAXException e)
    {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();
    }
    catch (Exception ex)
    {
        ex.printStackTrace ();
    }
    return resultList;
}
public List readCsiScoreFromXml()
{
    //List list=new ArrayList();
    
    List resultList=new ArrayList();
    List subList=new ArrayList();
    int saveCount=0;
    int updateCount=0;
    int count=0;
    
    String directoryName="CsiScore";
    AppUtility.setCurrentImportRecordName("About to extract CSI records");
    String filePath=appUtil.getExportFilePath()+appUtil.getFileSeperator()+directoryName+appUtil.getFileSeperator();
    //String filePath=appUtil.getExportFilePath()+"\\CsiScore\\";
       try
	{
            ChildStatusIndexDao csidao=new ChildStatusIndexDaoImpl();
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+directoryName+".xml";
                List files=getFiles(filePath);
                if(files !=null)
                {
                    //System.err.println("The are "+files.size()+" readCsiFromXml()");
                    String ovcId=null;
                    for(int i=0; i<files.size(); i++)
                    {
                        try
                        {
                            //System.err.println(filePath+files.get(i).toString());
                            fileName=filePath+files.get(i).toString();
                             file=new File(fileName);
                            if(!file.exists())
                            {
                                continue;
                            }
                            doc = docBuilder.parse (file);
                            // normalize text representation
                            doc.getDocumentElement().normalize();
                            NodeList listOfObjects = doc.getElementsByTagName("patient");
                            for(int s=0; s<listOfObjects.getLength() ; s++)
                            {
                                ChildStatusIndex csi=new ChildStatusIndex();
                                Node firstPersonNode = listOfObjects.item(s);
                                try
                                {
                                 if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                                 {
                                     ovcId=getNodeValue("ovcId",s,listOfObjects);
                                     if(ovcId.indexOf("/")==-1)
                                     continue;
                                    ovcId=getApproriateOvcIdPerVersion(ovcId);
                                    csi.setOvcId(ovcId);
                                    csi.setCsiDate(getNodeValue("csiDate",s,listOfObjects));
                                    csi.setCsiFactor1(Integer.parseInt(getNodeValue("csiFactor1",s,listOfObjects)));
                                    csi.setCsiFactor2(Integer.parseInt(getNodeValue("csiFactor2",s,listOfObjects)));
                                    csi.setCsiFactor3(Integer.parseInt(getNodeValue("csiFactor3",s,listOfObjects)));
                                    csi.setCsiFactor4(Integer.parseInt(getNodeValue("csiFactor4",s,listOfObjects)));
                                    csi.setCsiFactor5(Integer.parseInt(getNodeValue("csiFactor5",s,listOfObjects)));
                                    csi.setCsiFactor6(Integer.parseInt(getNodeValue("csiFactor6",s,listOfObjects)));
                                    csi.setCsiFactor7(Integer.parseInt(getNodeValue("csiFactor7",s,listOfObjects)));
                                    csi.setCsiFactor8(Integer.parseInt(getNodeValue("csiFactor8",s,listOfObjects)));
                                    csi.setCsiFactor9(Integer.parseInt(getNodeValue("csiFactor9",s,listOfObjects)));
                                    csi.setCsiFactor10(Integer.parseInt(getNodeValue("csiFactor10",s,listOfObjects)));
                                    csi.setCsiFactor11(Integer.parseInt(getNodeValue("csiFactor11",s,listOfObjects)));
                                    csi.setCsiFactor12(Integer.parseInt(getNodeValue("csiFactor12",s,listOfObjects)));
                                    csi.setSurveyNumber(Integer.parseInt(getNodeValue("surveyNumber",s,listOfObjects)));
                                    csi.setDateOfEntry(getNodeValue("dateOfEntry",s,listOfObjects));
                                    //ChildStatusIndex dupCsi=csidao.getChildStatusIndex(csi.getOvcId(), csi.getCsiDate());
                                    int num=csidao.saveOrUpdateChildStatusIndex(csi);
                                    if(num==1)
                                    {
                                        count++;
                                        saveCount++;
                                        AppUtility.setCurrentImportRecordName("CSI records: "+count);
                                    }
                                    else if(num==2)
                                    {
                                        count++;
                                        updateCount++;
                                        AppUtility.setCurrentImportRecordName("CSI records: "+count);
                                    }
                                    /*if(dupCsi !=null)
                                    {
                                        csi.setId(dupCsi.getId());
                                        csidao.updateChildStatusIndex(csi);
                                        updateCount++;

                                    }
                                    else
                                    {
                                        //csidao.saveChildStatusIndex(csi);
                                        saveCount++;
                                    }*/
                                    //System.err.println("csi file "+i+" and record "+s+" processed");
                                    //System.err.println("csi record "+s+" processed");
                                 }
                                }
                                catch (Exception ex)
                                {
                                    //System.err.println("An error occured while running csi file "+i);
                                    //continue;
                                   ex.printStackTrace();
                                }
                            }
                            System.err.println("About to open csi file "+i);
                        }
                        catch (Exception ex)
                        {
                           ex.printStackTrace();
                        }
                    }// 
	        }
                subList.add("Number of new CSI records saved ");
                subList.add(saveCount);
                resultList.add(subList);
                subList=new ArrayList();
                subList.add("Number of CSI records saved as updates");
                subList.add(updateCount);
                resultList.add(subList);
                //createSavableCsiList(session,list);
                //list.clear();
	}
	catch (Exception ex)
	{
		ex.printStackTrace();
	}
    return resultList;
}
public List readServiceRecordsFromXml(HttpSession session)
{
    List list=new ArrayList();
    String filePath=appUtil.getExportFilePath()+"\\OvcService\\";
    AppUtility.setCurrentImportRecordName("OVC Service records");
    OvcServiceDao serviceDao=new OvcServiceDaoImpl();
    
    try
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+"OvcService.xml";
                List files=getFiles(filePath);
                if(files !=null)
                {
                    String ovcId=null;
                    for(int i=0; i<files.size(); i++)
                    {
                        System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        AppUtility.setCurrentImportRecordName("OVC Service records: "+fileName);
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("patient");
                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            OvcService service=new OvcService();
                            Node firstPersonNode = listOfObjects.item(s);
                             if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                ovcId=getNodeValue("ovcId",s,listOfObjects);
                                 if(ovcId.indexOf("/")==-1)
                                 continue;
                                ovcId=getApproriateOvcIdPerVersion(ovcId);
                                service.setId(Integer.parseInt(getNodeValue("id",s,listOfObjects)));
                                service.setOvcId(ovcId);
                                service.setServicedate(getNodeValue("servicedate",s,listOfObjects));
                                service.setDateOfEntry(getNodeValue("dateOfEntry",s,listOfObjects));
                                service.setserviceAccessed1(getNodeValue("serviceAccessed1",s,listOfObjects));
                                service.setserviceAccessed2(getNodeValue("serviceAccessed2",s,listOfObjects));
                                service.setserviceAccessed3(getNodeValue("serviceAccessed3",s,listOfObjects));
                                service.setserviceAccessed4(getNodeValue("serviceAccessed4",s,listOfObjects));
                                service.setserviceAccessed5(getNodeValue("serviceAccessed5",s,listOfObjects));
                                service.setserviceAccessed6(getNodeValue("serviceAccessed6",s,listOfObjects));
                                service.setserviceAccessed7(getNodeValue("serviceAccessed7",s,listOfObjects));
                                service.setServicesReferred(getNodeValue("servicesReferred",s,listOfObjects));
                                service.setHeight(Double.parseDouble(getNodeValue("height",s,listOfObjects)));
                                service.setWeight(Double.parseDouble(getNodeValue("weight",s,listOfObjects)));
                                service.setSurveyNumber(Integer.parseInt(getNodeValue("surveyNumber",s,listOfObjects)));
                                service.setProviderName(getNodeValue("providerName",s,listOfObjects));
                                service.setNumberOfServices(serviceDao.getNumberOfServicesPerServiceRecord(service));
                                if(getNodeName("childAbused",s,listOfObjects) !=null)
                                service.setChildAbused(Integer.parseInt(getNodeValue("childAbused",s,listOfObjects)));
                                if(getNodeName("childLinkedToGovt",s,listOfObjects) !=null)
                                service.setChildLinkedToGovt(Integer.parseInt(getNodeValue("childLinkedToGovt",s,listOfObjects)));
                                if(getNodeName("childMissedSchool",s,listOfObjects) !=null)
                                service.setChildMissedSchool(getNodeValue("childMissedSchool",s,listOfObjects));
                                if(getNodeName("recordedBy",s,listOfObjects) !=null)
                                service.setRecordedBy(getNodeValue("recordedBy",s,listOfObjects));
                                                                
                                list.add(service);
                             }
                        }
                    }
                     
	        }
                createSavableServiceList(session,list);
                list.clear();
	}
	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return list;
}
public String getApproriateOvcIdPerVersion(String ovcId)
{
    if(ovcId !=null)
    {
        ovcId=ovcId.trim();
        String[] ovcIdArray=ovcId.split("/");
        if(ovcIdArray.length<5)
        ovcId=ovcId+"/"+ovcIdArray[3].trim();
    }
     return ovcId;
}
public List readOrganizationRecordsFromXml(String alternatePath,HttpSession session)
{
    List list=new ArrayList();
    String filePath=appUtil.getExportFilePath()+"\\OrganizationRecords\\";
    AppUtility.setCurrentImportRecordName("CBO Setup records");
    //System.err.println("filePath in readOrganizationRecordsFromXml is "+filePath);
    if(alternatePath !=null)
    filePath=alternatePath;
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    newRecordsList.add("Number of new CBO records saved");
    duplicateRecordsList.add("Number of CBO records saved as updates");
    int numberSaved=0;
    int numberUpdated=0;
    try
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+"OrganizationRecords.xml";
                List files=getFiles(filePath);
                String tagName="patient";
                if(files !=null)
                {
                    OrganizationRecordsDao ordao=new OrganizationRecordsDaoImpl();
                    for(int i=0; i<files.size(); i++)
                    {
                        
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        
                        NodeList listOfObjects = doc.getElementsByTagName(tagName);
                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            OrganizationRecords orgRecords=new OrganizationRecords();
                            Node firstPersonNode = listOfObjects.item(s);
                             if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                 String orgCode=getNodeValue("orgCode",s,listOfObjects);
                                orgRecords.setOrgCode(orgCode);
                                orgRecords.setState(getNodeValue("state",s,listOfObjects));
                                orgRecords.setLga(getNodeValue("lga",s,listOfObjects));
                                orgRecords.setOrgName(getNodeValue("orgName",s,listOfObjects));
                                orgRecords.setOrgType(getNodeValue("orgType",s,listOfObjects));
                                orgRecords.setOrgSubtype(getNodeValue("orgSubtype",s,listOfObjects));

                                orgRecords.setAddress(getNodeValue("address",s,listOfObjects));
                                orgRecords.setContactName1(getNodeValue("contactName1",s,listOfObjects));
                                orgRecords.setContactPhone1(getNodeValue("contactPhone1",s,listOfObjects));
                                orgRecords.setContactEmail1(getNodeValue("contactEmail1",s,listOfObjects));
                                orgRecords.setContactName2(getNodeValue("contactName2",s,listOfObjects));
                                orgRecords.setContactPhone2(getNodeValue("contactPhone2",s,listOfObjects));
                                orgRecords.setContactEmail2(getNodeValue("contactEmail2",s,listOfObjects));
                                orgRecords.setContactName3(getNodeValue("contactName3",s,listOfObjects));
                                orgRecords.setContactPhone3(getNodeValue("contactPhone3",s,listOfObjects));
                                orgRecords.setContactEmail3(getNodeValue("contactEmail3",s,listOfObjects));
                                orgRecords.setServices(getNodeValue("services",s,listOfObjects));
                                OrganizationRecords existingrd=ordao.getOrganizationRecord(orgCode);
                                if(existingrd==null)
                                {
                                    ordao.saveOrgRecords(orgRecords);
                                    numberSaved++;
                                    //System.err.println("orgRecords with name "+orgRecords.getOrgName()+" saved");
                                }
                                else
                                {
                                    ordao.updateOrgRecords(orgRecords);
                                    numberUpdated++;
                                    //System.err.println("orgRecords with name "+orgRecords.getOrgName()+" updated");
                                }
                                //list.add(orgRecords);
                             }
                        }
                    }
	        }
                newRecordsList.add(numberSaved);
                duplicateRecordsList.add(numberUpdated);   
	}
	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    list.add(newRecordsList);
    list.add(duplicateRecordsList);
    return list;
}
public List readFollowupSurveyRecordsFromXml(HttpSession session,boolean hivUpdate,boolean birthRegUpdate)
{
    List list=new ArrayList();
    List subList=new ArrayList();
    List resultList=new ArrayList();
    int saveCount=0;
    int updateCount=0;
    OvcDao ovcdao=new OvcDaoImpl();
    FollowupDao fdao=new FollowupDaoImpl();
    FollowupSurvey dulicateFs=null;
    String filePath=appUtil.getExportFilePath()+"\\FollowupSurveyRecords\\";
    AppUtility.setCurrentImportRecordName("OVC Follow-up records");
       try
	{
                     
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+"FollowupSurveyRecords.xml";
                List files=getFiles(filePath);
                String caregiverId=null;
                String caregiverNodeName=null;
                int count=0;
                
                if(files !=null)
                {
                    String ovcId=null;
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                    doc = docBuilder.parse (file);
                    // normalize text representation
                    doc.getDocumentElement().normalize();
                    NodeList listOfObjects = doc.getElementsByTagName("patient");
                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            FollowupSurvey followupSurvey=new FollowupSurvey();
                            Node firstPersonNode = listOfObjects.item(s);
                             if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                 ovcId=getNodeValue("ovcId",s,listOfObjects);
                                 if(ovcId.indexOf("/")==-1)
                                 continue;
                                 ovcId=getApproriateOvcIdPerVersion(ovcId);
                                 followupSurvey.setOvcId(ovcId);
                                 
                                caregiverNodeName=getNodeName("caregiverId",s,listOfObjects);
                                if(caregiverNodeName !=null)
                                caregiverId=getNodeValue("caregiverId",s,listOfObjects);
                                else
                                {
                                  Ovc ovc=ovcdao.getOvc(ovcId);
                                  if(ovc !=null)
                                  caregiverId=ovc.getCaregiverId();
                                  
                                }
                                
                                followupSurvey.setCaregiverId(caregiverId);
                                followupSurvey.setDateOfSurvey(getNodeValue("dateOfSurvey",s,listOfObjects));
                                followupSurvey.setUpdatedAddress(getNodeValue("updatedAddress",s,listOfObjects));
                                followupSurvey.setUpdatedAge(Integer.parseInt(getNodeValue("updatedAge",s,listOfObjects)));
                                followupSurvey.setUpdatedAgeUnit(getNodeValue("updatedAgeUnit",s,listOfObjects));
                                followupSurvey.setUpdatedHivStatus(getNodeValue("updatedHivStatus",s,listOfObjects));
                                followupSurvey.setUpdatedBirthCertStatus(getNodeValue("updatedBirthCertStatus",s,listOfObjects));
                                followupSurvey.setUpdatedSchoolStatus(getNodeValue("updatedSchoolStatus",s,listOfObjects));
                                followupSurvey.setUpdatedSchoolName(getNodeValue("updatedSchoolName",s,listOfObjects));
                                //followupSurvey.setUpdatedCaregiverName(getNodeValue("updatedCaregiverName",s,listOfObjects));
                                //followupSurvey.setUpdatedCaregiverGender(getNodeValue("updatedCaregiverGender",s,listOfObjects));
                                //followupSurvey.setUpdatedCaregiverAge(Integer.parseInt(getNodeValue("updatedCaregiverAge",s,listOfObjects)));
                                //followupSurvey.setUpdatedCaregiverAddress(getNodeValue("updatedCaregiverAddress",s,listOfObjects));
                                //followupSurvey.setUpdatedCaregiverPhone(getNodeValue("updatedCaregiverPhone",s,listOfObjects));
                                //followupSurvey.setUpdatedCaregiverOccupation(getNodeValue("updatedCaregiverOccupation",s,listOfObjects));
                                //followupSurvey.setUpdatedCaregiverRelationship(getNodeValue("updatedCaregiverRelationship",s,listOfObjects));
                                followupSurvey.setCompletedbyName(getNodeValue("completedbyName",s,listOfObjects));
                                followupSurvey.setCompletedbyDesignation(getNodeValue("completedbyDesignation",s,listOfObjects));
                                followupSurvey.setDateOfEntry(getNodeValue("dateOfEntry",s,listOfObjects));
                                followupSurvey.setRecordedBy(getNodeValue("recordedBy",s,listOfObjects));
                                dulicateFs=fdao.getFollowup(followupSurvey.getOvcId(), followupSurvey.getDateOfSurvey());
                                if(dulicateFs==null)
                                {
                                    count++;
                                    fdao.saveFollowup(followupSurvey,hivUpdate,birthRegUpdate);
                                    saveCount++;
                                    AppUtility.setCurrentImportRecordName("OVC Follow-up records: "+count);
                                    //System.err.println("FollowupSurvey record "+saveCount+" saved ");
                                }
                                else
                                {
                                    count++;
                                    followupSurvey.setId(dulicateFs.getId());
                                    fdao.updateFollowup(followupSurvey,hivUpdate,birthRegUpdate);
                                    updateCount++;
                                    AppUtility.setCurrentImportRecordName("OVC Follow-up records: "+count);
                                    //System.err.println("FollowupSurvey record "+updateCount+" updated");
                                }
                                
                             }
                        }
                    }
	        }
                subList.add("Number of new follow up assessment records");
                subList.add(saveCount);
                resultList.add(subList);
                subList=new ArrayList();
                subList.add("Number of follow up assessment records saved as updates");
                subList.add(updateCount);
                resultList.add(subList);
                createFollowupRecordsList(session,list);
                list.clear();
	}
	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return resultList;
}
public List readReferralRecordsFromXml(HttpSession session)
{
    List list=new ArrayList();
    String filePath=appUtil.getExportFilePath()+"\\OvcReferral\\";
       try
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+"OvcReferral.xml";
                AppUtility.setCurrentImportRecordName("Referral records");
                List files=getFiles(filePath);
                if(files !=null)
                {
                    String ovcId=null;
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();

                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("patient");
                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            OvcReferral referral=new OvcReferral();
                            Node firstPersonNode = listOfObjects.item(s);
                             if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                ovcId=getNodeValue("ovcId",s,listOfObjects);
                                 if(ovcId.indexOf("/")==-1)
                                 continue;
                                ovcId=getApproriateOvcIdPerVersion(ovcId);
                                referral.setRecordId(Integer.parseInt(getNodeValue("recordId",s,listOfObjects)));
                                referral.setOvcId(ovcId);
                                referral.setDateOfReferral(getNodeValue("dateOfReferral",s,listOfObjects));
                                referral.setReferringOrganization(getNodeValue("referringOrganization",s,listOfObjects));
                                referral.setPsychoServices(getNodeValue("psychoServices",s,listOfObjects));
                                referral.setNutritionalServices(getNodeValue("nutritionalServices",s,listOfObjects));
                                referral.setHealthServices(getNodeValue("healthServices",s,listOfObjects));
                                referral.setEducationalServices(getNodeValue("educationalServices",s,listOfObjects));
                                referral.setProtectionServices(getNodeValue("protectionServices",s,listOfObjects));
                                referral.setShelterServices(getNodeValue("shelterServices",s,listOfObjects));
                                referral.setEconomicServices(getNodeValue("economicServices",s,listOfObjects));
                                referral.setPsychosocialOther(getNodeValue("psychosocialOther",s,listOfObjects));
                                referral.setNutritionOther(getNodeValue("nutritionOther",s,listOfObjects));
                                referral.setHealthOther(getNodeValue("healthOther",s,listOfObjects));
                                referral.setEducationOther(getNodeValue("educationOther",s,listOfObjects));
                                referral.setProtectionOther(getNodeValue("protectionOther",s,listOfObjects));
                                referral.setShelterOther(getNodeValue("shelterOther",s,listOfObjects));
                                referral.setEconomicOther(getNodeValue("economicOther",s,listOfObjects));
                                referral.setNameOfOrganizationChildIsreferred(getNodeValue("nameOfOrganizationChildIsreferred",s,listOfObjects));
                                referral.setReferralCompleted(getNodeValue("referralCompleted",s,listOfObjects));
                                referral.setNameOfPersonReferring(getNodeValue("nameOfPersonReferring",s,listOfObjects));
                                referral.setDesignationOfReferrer(getNodeValue("designationOfReferrer",s,listOfObjects));
                                referral.setReferrerPhoneNo(getNodeValue("referrerPhoneNo",s,listOfObjects));
                                referral.setReferrerEmail(getNodeValue("referrerEmail",s,listOfObjects));
                                String nodeName=getNodeName("type",s,listOfObjects);
                                if(nodeName !=null)
                                referral.setType(getNodeValue("type",s,listOfObjects));
                                else
                                {
                                    if(ovcId.length()>20)
                                    referral.setType("ovc");
                                    else
                                    referral.setType("household");
                                }
                                list.add(referral);
                             }
                        }
                    }
	        }
                createReferralRecordsList(session,list);
                list.clear();
	}
	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return list;
}
public List readWardsFromXml(String alternateFilePath, HttpSession session)
{
    List list=new ArrayList();
    String directoryName="Wards";
    String filePath=appUtil.getExportFilePath()+appUtil.getFileSeperator()+directoryName+appUtil.getFileSeperator();
    //String filePath=appUtil.getExportFilePath()+"\\Wards\\";
    AppUtility.setCurrentImportRecordName("Ward/Community records");
    //if(alternateFilePath !=null)
    //filePath=alternateFilePath;
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    newRecordsList.add("Number of new Ward records saved");
    duplicateRecordsList.add("Number of Ward records saved as updates");
    int numberSaved=0;
    int numberUpdated=0;
    try
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+directoryName+".xml";
                List files=getFiles(filePath);
                if(files !=null)
                {
                    WardDao wdao=new WardDaoImpl();
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();

                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("patient");
                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            Wards ward=new Wards();
                            Node firstPersonNode = listOfObjects.item(s);
                            if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                String wardCode=getNodeValue("ward_code",s,listOfObjects);
                                ward.setWard_code(wardCode);
                                ward.setWard_name(getNodeValue("ward_name",s,listOfObjects));
                                ward.setCbo_code(getNodeValue("cbo_code",s,listOfObjects));
                                Wards existingWard=wdao.getWards(wardCode);
                                if(existingWard==null)
                                {
                                    wdao.saveWard(ward);
                                    numberSaved++;
                                    //System.err.println("ward with name "+ward.getWard_name()+" saved");
                                }
                                else 
                                {
                                    wdao.updateWard(ward);
                                    numberUpdated++;
                                    //System.err.println("ward with name "+ward.getWard_name()+" updated");
                                }
                                //list.add(ward);
                             }
                        }
                    }
                    
	        }
                createWardList(session,list);
                //list.clear();
	}
	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    list.add(newRecordsList);
    list.add(duplicateRecordsList);
    return list;
}
public List readOvcWithdrawalFromXml()
{
    List list=new ArrayList();
    String parentName="OvcWithdrawal";
    AppUtility.setCurrentImportRecordName("Withdrawal records");
    String filePath=appUtil.getExportFilePath()+appUtil.getFileSeperator()+parentName+appUtil.getFileSeperator();
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    int count=0;
    int newRecordsCount=0;
    int duplicateRecordsCount=0;
    newRecordsList.add("Number of new OVC withdrawal records saved");
    duplicateRecordsList.add("Number of OVC withdrawal records saved as updates");
    try
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+parentName+".xml";
                String reasonWithdrawal=null;
                List files=getFiles(filePath);
                if(files !=null)
                {
                    String ovcId=null;
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                        file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        OvcWithdrawal withdrawal=null;
                        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("patient");
                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            withdrawal=new OvcWithdrawal();
                            Node firstPersonNode = listOfObjects.item(s);
                            if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                 ovcId=getNodeValue("ovcId",s,listOfObjects);
                                 if(ovcId.indexOf("/")==-1)
                                 continue;
                                String withdrawalType=getNodeValue("type",s,listOfObjects);
                                
                                if(withdrawalType !=null && withdrawalType.equalsIgnoreCase("ovc"))
                                ovcId=getApproriateOvcIdPerVersion(ovcId);
                                withdrawal.setDateOfWithdrawal(getNodeValue("dateOfWithdrawal",s,listOfObjects));
                                withdrawal.setOvcId(ovcId);
                                reasonWithdrawal=getNodeValue("reasonWithdrawal",s,listOfObjects);
                                withdrawal.setReasonWithdrawal(reasonWithdrawal);
                                withdrawal.setSurveyNumber(Integer.parseInt(getNodeValue("surveyNumber",s,listOfObjects)));
                                withdrawal.setType(withdrawalType);
                                //Set correct withdrawal values for old records
                                withdrawal=util.getWithdrawalWithCleanedValues(withdrawal);
                                if(wdao.getOvcWithdrawal(withdrawal.getOvcId())==null)
                                {
                                    count++;
                                    wdao.saveOvcWithdrawal(withdrawal);
                                    newRecordsCount++;
                                    AppUtility.setCurrentImportRecordName("Withdrawal records: "+count+" saved");
                                }
                                else
                                {
                                    count++;
                                    duplicateRecordsCount++;
                                    wdao.saveOrUpdateOvcWithdrawal(withdrawal);
                                    AppUtility.setCurrentImportRecordName("Withdrawal records: "+count+" saved");
                                }
                                //list.add(ward);
                             }
                        }
                    }

	        }
                newRecordsList.add(newRecordsCount);
                duplicateRecordsList.add(duplicateRecordsCount);
                list.add(newRecordsList);
                list.add(duplicateRecordsList);
	}
	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return list;
}
public List readOvcTBHIVScreeningChecklistFromXml()
{
    List list=new ArrayList();
    String parentFolderName="OvcTBHIVScreeningChecklist";
    AppUtility.setCurrentImportRecordName("OVC TB/HIV screening records");
    String parentFolder=appUtil.getExportFilePath()+appUtil.getFileSeperator()+parentFolderName;
    appUtil.createDirectories(parentFolder);
    String filePath=parentFolder+appUtil.getFileSeperator();
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    int newRecordsCount=0;
    int duplicateRecordsCount=0;
    newRecordsList.add("Number of new Child TB/HIV records saved");
    duplicateRecordsList.add("Number of Child TB/HIV records saved as updates");
    try
	{
            int count=0;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+parentFolderName+".xml";
                List files=getFiles(filePath);
                if(files !=null)
                {
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                        file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        OvcTBHIVScreeningChecklist tbhiv=null;
                        OvcTBHIVScreeningChecklistDao tbhivdao=new OvcTBHIVScreeningChecklistDaoImpl();
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("dataValue");
                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            tbhiv=new OvcTBHIVScreeningChecklist();
                            Node firstPersonNode = listOfObjects.item(s);
                            if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                tbhiv.setOvcId(getNodeValue("ovcId",s,listOfObjects));
                                tbhiv.setDateOfAssessment(getNodeValue("dateOfAssessment",s,listOfObjects));
                                tbhiv.setAssessmentNo(Integer.parseInt(getNodeValue("assessmentNo",s,listOfObjects)));
                                tbhiv.setChildCoughing(getNodeValue("childCoughing",s,listOfObjects));
                                tbhiv.setChildLossindWeight(getNodeValue("childLossindWeight",s,listOfObjects));
                                tbhiv.setChildHavingFever(getNodeValue("childHavingFever",s,listOfObjects));
                                tbhiv.setChildHavingNightSweat(getNodeValue("childHavingNightSweat",s,listOfObjects));
                                tbhiv.setFamilyMemberHadTB(getNodeValue("familyMemberHadTB",s,listOfObjects));
                                tbhiv.setDosesOfARVMissedByChild(getNodeValue("dosesOfARVMissedByChild",s,listOfObjects));
                                tbhiv.setCaregiverEnrolledInPMTCT(getNodeValue("caregiverEnrolledInPMTCT",s,listOfObjects));
                                tbhiv.setEidTestingDoneForChild(getNodeValue("eidTestingDoneForChild",s,listOfObjects));
                                tbhiv.setChildOnSeptrin(getNodeValue("childOnSeptrin",s,listOfObjects));
                                tbhiv.setHivStatusDisclosedToPartner(getNodeValue("hivStatusDisclosedToPartner",s,listOfObjects));
                                tbhiv.setHivStatusOfPartnerKnown(getNodeValue("hivStatusOfPartnerKnown",s,listOfObjects));
                                tbhiv.setDosesOfARVMissedByChild(getNodeValue("dosesOfSeptrinMissedByChild",s,listOfObjects));
                                tbhiv.setChildAboveSixWksAndStartedOnSeptrin(getNodeValue("childAboveSixWksAndStartedOnSeptrin",s,listOfObjects));
                                tbhiv.setChildSleepInTreatedNet(getNodeValue("childSleepInTreatedNet",s,listOfObjects));
                                tbhiv.setBeneficiariesHasSoresOrBleeding(getNodeValue("beneficiariesHasSoresOrBleeding",s,listOfObjects));
                                tbhiv.setNumberOfClubsAttndedInThreeMths(getNodeValue("numberOfClubsAttndedInThreeMths",s,listOfObjects));
                                tbhiv.setChildSwellingOnNeck(getNodeValue("childSwellingOnNeck",s,listOfObjects));
                                tbhiv.setVolunteerName(getNodeValue("volunteerName",s,listOfObjects));
                                tbhiv.setDesignation(getNodeValue("designation",s,listOfObjects));
                                tbhiv.setDateModified(getNodeValue("dateModified",s,listOfObjects));
                                if(tbhivdao.getOvcTBHIVScreeningChecklist(tbhiv.getOvcId(), tbhiv.getDateOfAssessment()) ==null)
                                {
                                    tbhivdao.saveOvcTBHIVScreeningChecklist(tbhiv);
                                    newRecordsCount++;
                                    count++;
                                    AppUtility.setCurrentImportRecordName("OVC TB/HIV screening records: "+count+" saved");
                                }
                                else
                                {
                                    duplicateRecordsCount++;
                                    tbhivdao.updateOvcTBHIVScreeningChecklist(tbhiv);
                                    count++;
                                    AppUtility.setCurrentImportRecordName("OVC TB/HIV screening records: "+count+" saved");
                                }
                             }
                        }
                    }

	        }
                newRecordsList.add(newRecordsCount);
                duplicateRecordsList.add(duplicateRecordsCount);
                list.add(newRecordsList);
                list.add(duplicateRecordsList);
	}
	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return list;
}
public List readCaregiverTBHIVScreeningChecklistFromXml()
{
    List list=new ArrayList();
    String parentFolderName="CaregiverTBHIVScreeningChecklist";
    AppUtility.setCurrentImportRecordName("Caregiver TB/HIV screening records");
    String parentFolder=appUtil.getExportFilePath()+appUtil.getFileSeperator()+parentFolderName;
    appUtil.createDirectories(parentFolder);
    String filePath=parentFolder+appUtil.getFileSeperator();
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    int newRecordsCount=0;
    int duplicateRecordsCount=0;
    newRecordsList.add("Number of new Caregiver TB/HIV records saved");
    duplicateRecordsList.add("Number of Caregiver TB/HIV records saved as updates");
    try
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+parentFolderName+".xml";
                List files=getFiles(filePath);
                if(files !=null)
                {
                    for(int i=0; i<files.size(); i++)
                    {
                        //System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                        file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        CaregiverTBHIVScreeningChecklist tbhiv=null;
                        CaregiverTBHIVScreeningChecklistDao tbhivdao=new CaregiverTBHIVScreeningChecklistDaoImpl();
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("dataValue");
                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            tbhiv=new CaregiverTBHIVScreeningChecklist();
                            Node firstPersonNode = listOfObjects.item(s);
                            if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                tbhiv.setCaregiverId(getNodeValue("caregiverId",s,listOfObjects));
                                tbhiv.setDateOfAssessment(getNodeValue("dateOfAssessment",s,listOfObjects));
                                tbhiv.setAssessmentNo(Integer.parseInt(getNodeValue("assessmentNo",s,listOfObjects)));
                                tbhiv.setCaregiverCoughing(getNodeValue("caregiverCoughing",s,listOfObjects));
                                tbhiv.setCaregiverLossindWeight(getNodeValue("caregiverLossindWeight",s,listOfObjects));
                                tbhiv.setCaregiverHavingFever(getNodeValue("caregiverHavingFever",s,listOfObjects));
                                tbhiv.setCaregiverHavingNightSweat(getNodeValue("caregiverHavingNightSweat",s,listOfObjects));
                                tbhiv.setFamilyMemberHadTB(getNodeValue("familyMemberHadTB",s,listOfObjects));
                                tbhiv.setDosesOfARVMissedByCaregiver(getNodeValue("dosesOfARVMissedByCaregiver",s,listOfObjects));
                                tbhiv.setCaregiverEnrolledInPMTCT(getNodeValue("caregiverEnrolledInPMTCT",s,listOfObjects));
                                tbhiv.setEidTestingDoneForChild(getNodeValue("eidTestingDoneForChild",s,listOfObjects));
                                tbhiv.setCaregiverOnSeptrin(getNodeValue("caregiverOnSeptrin",s,listOfObjects));
                                tbhiv.setHivStatusDisclosedToPartner(getNodeValue("hivStatusDisclosedToPartner",s,listOfObjects));
                                tbhiv.setHivStatusOfPartnerKnown(getNodeValue("hivStatusOfPartnerKnown",s,listOfObjects));
                                tbhiv.setDosesOfARVMissedByCaregiver(getNodeValue("dosesOfSeptrinMissedByCaregiver",s,listOfObjects));
                                tbhiv.setChildAboveSixWksAndStartedOnSeptrin(getNodeValue("childAboveSixWksAndStartedOnSeptrin",s,listOfObjects));
                                tbhiv.setCaregiverSleepInTreatedNet(getNodeValue("caregiverSleepInTreatedNet",s,listOfObjects));
                                tbhiv.setBeneficiariesHasSoresOrBleeding(getNodeValue("beneficiariesHasSoresOrBleeding",s,listOfObjects));
                                tbhiv.setNumberOfClubsAttndedInThreeMths(getNodeValue("numberOfClubsAttndedInThreeMths",s,listOfObjects));
                                tbhiv.setCaregiverSwellingOnNeck(getNodeValue("caregiverSwellingOnNeck",s,listOfObjects));
                                tbhiv.setVolunteerName(getNodeValue("volunteerName",s,listOfObjects));
                                tbhiv.setDesignation(getNodeValue("designation",s,listOfObjects));
                                tbhiv.setDateModified(getNodeValue("dateModified",s,listOfObjects));
                                if(tbhivdao.getCaregiverTBHIVScreeningChecklist(tbhiv.getCaregiverId(), tbhiv.getDateOfAssessment()) ==null)
                                {
                                    tbhivdao.saveCaregiverTBHIVScreeningChecklist(tbhiv);
                                    newRecordsCount++;
                                }
                                else
                                {
                                    duplicateRecordsCount++;
                                    tbhivdao.updateCaregiverTBHIVScreeningChecklist(tbhiv);
                                }
                             }
                        }
                    }

	        }
                newRecordsList.add(newRecordsCount);
                duplicateRecordsList.add(duplicateRecordsCount);
                list.add(newRecordsList);
                list.add(duplicateRecordsList);
	}
	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return list;
}
public List readHivStatusUpdateFromXml()
{
    List list=new ArrayList();
    String parentFolderName="HivStatusUpdate";
    AppUtility.setCurrentImportRecordName("HIV Status records");
    String parentFolder=appUtil.getExportFilePath()+appUtil.getFileSeperator()+parentFolderName;
    appUtil.createDirectories(parentFolder);
    String filePath=parentFolder+appUtil.getFileSeperator();
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    int newRecordsCount=0;
    int duplicateRecordsCount=0;
    newRecordsList.add("Number of new HIV records saved");
    duplicateRecordsList.add("Number of HIV records saved as updates");
    try
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+parentFolderName+".xml";
                List files=getFiles(filePath);
                int hivPosActiveCount=0;
                int hivPosOnTreatmentCount=0;
                if(files !=null)
                {
                    int count=0;
                    for(int i=0; i<files.size(); i++)
                    {
                        System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                        file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        HivStatusUpdate hsu=null;
                        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("patient");
                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            hsu=new HivStatusUpdate();
                            Node firstPersonNode = listOfObjects.item(s);
                            if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                hsu.setClientEnrolledInCare(getNodeValue("clientEnrolledInCare",s,listOfObjects));
                                if(getNodeValue("enrolledOnART",s,listOfObjects) !=null)
                                hsu.setEnrolledOnART(getNodeValue("enrolledOnART",s,listOfObjects));
                                hsu.setClientId(getNodeValue("clientId",s,listOfObjects));
                                hsu.setClientType(getNodeValue("clientType",s,listOfObjects));
                                hsu.setDateModified(getNodeValue("dateModified",s,listOfObjects));
                                hsu.setDateOfCurrentStatus(getNodeValue("dateOfCurrentStatus",s,listOfObjects));
                                
                                hsu.setHivStatus(getNodeValue("hivStatus",s,listOfObjects));
                                hsu.setOrganizationClientIsReferred(getNodeValue("organizationClientIsReferred",s,listOfObjects));
                                hsu.setPointOfUpdate(getNodeValue("pointOfUpdate",s,listOfObjects));
                                hsu.setRecordId(getNodeValue("recordId",s,listOfObjects));
                                hsu.setRecordStatus(getNodeValue("recordStatus",s,listOfObjects));
                                if(hsu.getRecordStatus() !=null && (hsu.getRecordStatus().equalsIgnoreCase("active") || hsu.getPointOfUpdate().equalsIgnoreCase(NomisConstant.ENROLLMENT_POINTOFUPDATE)))
                                {
                                    HivStatusUpdate existingHsu=hsudao.getHivStatusUpdatesByClientIdAndDateOfStatus(hsu.getClientId(), hsu.getDateOfCurrentStatus());
                                    if(existingHsu ==null)
                                    {
                                        hsudao.saveHivStatusUpdate(hsu);
                                        newRecordsCount++;
                                    }
                                    else
                                    {
                                        hsu.setRecordId(existingHsu.getRecordId());
                                        duplicateRecordsCount++;
                                        hsudao.updateHivStatusUpdate(hsu);
                                    }
                                    count++;
                                    AppUtility.setCurrentImportRecordName("HIV Status records: "+count+" saved");
                                    if(!hivList.contains(hsu.getClientId()))
                                    hivList.add(hsu.getClientId());
                                    if(hsu.getHivStatus().equalsIgnoreCase(NomisConstant.HIV_POSITIVE) && hsu.getRecordStatus().equalsIgnoreCase("active"))
                                    {
                                        hivPosActiveCount++;
                                        if((hsu.getClientEnrolledInCare() !=null && hsu.getClientEnrolledInCare().equalsIgnoreCase("Yes")) || (hsu.getEnrolledOnART() !=null && hsu.getEnrolledOnART().equalsIgnoreCase("Yes")))
                                        {
                                            hivOnTreatmentList.add(hsu);
                                            hivPosOnTreatmentCount++;
                                        }
                                        else
                                        {
                                            System.err.println("Enrolled in Care is "+hsu.getClientEnrolledInCare()+" Enrolled in Care is "+hsu.getEnrolledOnART());
                                        }
                                        System.err.println("Number of Active positive records imported is "+hivPosActiveCount);
                                        System.err.println("Number of Active positive on treatment is "+hivPosOnTreatmentCount);
                                    }
                                 }
                             }
                        }
                    }

	        }
                newRecordsList.add(newRecordsCount);
                duplicateRecordsList.add(duplicateRecordsCount);
                list.add(newRecordsList);
                list.add(duplicateRecordsList);
	}
	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return list;
}
/*public List readSchoolRecordsFromXml(HttpSession session)
{
    List list=new ArrayList();
    List resultList=new ArrayList();
    List subList=new ArrayList();
    String filePath=appUtil.getExportFilePath()+"\\OvcSchool\\";
    AppUtility.setCurrentImportRecordName("Extracting School records");
    subList.add("Number of school records");
    try
	{
                OvcSchoolDao schoolDao=new OvcSchoolDaoImpl();
                int count=0;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+"OvcSchool.xml";
                List files=getFiles(filePath);
                if(files !=null)
                {
                    for(int i=0; i<files.size(); i++)
                    {
                        System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();
                        AppUtility.setCurrentImportRecordName("Extracting "+files.get(i).toString());
                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize();
                        NodeList listOfObjects = doc.getElementsByTagName("patient");
                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            OvcSchool school=new OvcSchool();
                            Node firstPersonNode = listOfObjects.item(s);
                            if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                school.setState(getNodeValue("state",s,listOfObjects));
                                school.setLga(getNodeValue("lga",s,listOfObjects));
                                school.setType(getNodeValue("type",s,listOfObjects));
                                school.setName(getNodeValue("name",s,listOfObjects));
                                school.setAddress(getNodeValue("address",s,listOfObjects));
                                OvcSchool school2=schoolDao.getOvcSchool(school.getState(),school.getLga(),school.getName());
                                if(school2==null)
                                {
                                    schoolDao.saveOvcSchool(school);
                                    count++;
                                    AppUtility.setCurrentImportRecordName("School records "+count+" saved");
                                }
                                else
                                {
                                    school.setId(school2.getId());
                                    schoolDao.updateOvcSchool(school);
                                    count++;
                                    AppUtility.setCurrentImportRecordName("School records "+count+" saved");
                                }
                                //list.add(school);
                             }
                        }
                    }
	        }
                subList.add(count);
                resultList.add(subList);
                //createSchoolList(session,list);
                list.clear();
	}
	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return resultList;
}*/
public List readOrganizationalAssessmentFromXml(HttpSession session)
{
    List list=new ArrayList();
    String filePath=appUtil.getExportFilePath()+"\\OrganizationalAssessment\\";
   AppUtility.setCurrentImportRecordName("Organization assessment records");
    try
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                File file=null;
                Document doc = null;
		String fileName=filePath+"OrganizationalAssessment.xml";
                List files=getFiles(filePath);
                if(files !=null)
                {
                    for(int i=0; i<files.size(); i++)
                    {
                        System.err.println(filePath+files.get(i).toString());
                        fileName=filePath+files.get(i).toString();

                         file=new File(fileName);
                        if(!file.exists())
                        {
                            continue;
                        }
                    doc = docBuilder.parse (file);
                    // normalize text representation
                    doc.getDocumentElement().normalize();
                    NodeList listOfObjects = doc.getElementsByTagName("patient");
                        for(int s=0; s<listOfObjects.getLength() ; s++)
                        {
                            OrganizationalCapacityAssessment orgCapAssessment=new OrganizationalCapacityAssessment();
                            Node firstPersonNode = listOfObjects.item(s);
                             if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                             {
                                orgCapAssessment.setOrgCode(getNodeValue("orgCode",s,listOfObjects));
                                orgCapAssessment.setIsorganizationasubgrantee(getNodeValue("isorganizationasubgrantee",s,listOfObjects));
                                orgCapAssessment.setDateofcapacityassessment(getNodeValue("dateofcapacityassessment",s,listOfObjects));
                                orgCapAssessment.setLeadassessorname(getNodeValue("leadassessorname",s,listOfObjects));
                                orgCapAssessment.setNoofassessment(Integer.parseInt(getNodeValue("noofassessment",s,listOfObjects)));
                                orgCapAssessment.setNoofassessors(Integer.parseInt(getNodeValue("noofassessors",s,listOfObjects)));
                                orgCapAssessment.setVisionandmission(Integer.parseInt(getNodeValue("visionandmission",s,listOfObjects)));
                                orgCapAssessment.setGoalsandobjectives(Integer.parseInt(getNodeValue("goalsandobjectives",s,listOfObjects)));
                                orgCapAssessment.setActionplanning(Integer.parseInt(getNodeValue("actionplanning",s,listOfObjects)));
                                orgCapAssessment.setRoleandresponsibilities(Integer.parseInt(getNodeValue("roleandresponsibilities",s,listOfObjects)));
                                orgCapAssessment.setInternalrules(Integer.parseInt(getNodeValue("internalrules",s,listOfObjects)));
                                orgCapAssessment.setMeetings(Integer.parseInt(getNodeValue("meetings",s,listOfObjects)));
                                orgCapAssessment.setLeadership(Integer.parseInt(getNodeValue("leadership",s,listOfObjects)));
                                orgCapAssessment.setTeambuilding(Integer.parseInt(getNodeValue("teambuilding",s,listOfObjects)));
                                orgCapAssessment.setMonitoring(Integer.parseInt(getNodeValue("monitoring",s,listOfObjects)));
                                orgCapAssessment.setReportingandrecordkeeping(Integer.parseInt(getNodeValue("reportingandrecordkeeping",s,listOfObjects)));
                                orgCapAssessment.setBudgeting(Integer.parseInt(getNodeValue("budgeting",s,listOfObjects)));
                                orgCapAssessment.setBookkeeping(Integer.parseInt(getNodeValue("bookkeeping",s,listOfObjects)));
                                orgCapAssessment.setBanking(Integer.parseInt(getNodeValue("banking",s,listOfObjects)));
                                orgCapAssessment.setLocalresourcemobilization(Integer.parseInt(getNodeValue("localresourcemobilization",s,listOfObjects)));
                                orgCapAssessment.setProposalwriting(Integer.parseInt(getNodeValue("proposalwriting",s,listOfObjects)));
                                orgCapAssessment.setNetworking(Integer.parseInt(getNodeValue("networking",s,listOfObjects)));
                                orgCapAssessment.setAdvocacy(Integer.parseInt(getNodeValue("advocacy",s,listOfObjects)));
                                list.add(orgCapAssessment);
                             }
                        }
                    }
	        }
                createOrganizationalCapacityAssessmentList(session,list);
                list.clear();
	}
	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return list;
}
private void createSavableHheList(HttpSession session,List list)
{
    HouseholdEnrollmentDao dao=new HouseholdEnrollmentDaoImpl();
    List hheCountList=new ArrayList();
    List nonDuplicateList=new ArrayList();
    List duplicateList=new ArrayList();
    List savableList=new ArrayList();
    int duplicateCount=0;
    HouseholdEnrollment hhe=null;
    HouseholdEnrollment hhe2=null;
    //int index=0;

    for(Object obj: list)
    {
        hhe=(HouseholdEnrollment)obj;
        
        try
        {
            //removeDeletedWithdrawalRecord(hhe.getHhUniqueId());
            hhe2=dao.getHouseholdEnrollment(hhe.getHhUniqueId());
            if(hhe2==null)
            {
                nonDuplicateList.add(hhe);
            }
            else
            {
                if(dao.getHouseholdVulnerabilityScore(hhe)<7 && dao.getHouseholdVulnerabilityScore(hhe2)>6)
                {
                    hhe.setHealth(hhe2.getHealth());
                    hhe.setHhHeadship(hhe2.getHhHeadship());
                    hhe.setHhIncome(hhe2.getHhIncome());
                    hhe.setEducationLevel(hhe2.getEducationLevel());
                    hhe.setFoodSecurityAndNutrition(hhe2.getFoodSecurityAndNutrition());
                    hhe.setMeansOfLivelihood(hhe2.getMeansOfLivelihood());
                    hhe.setShelterAndHousing(hhe2.getShelterAndHousing());
                    hhe.setHhvaId(hhe2.getHhvaId());
                    hhe.setBaselineAssessmentScore(dao.getHouseholdVulnerabilityScore(hhe2));
                }
                duplicateList.add(hhe);
                duplicateCount++;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    System.err.println("nonDuplicateList size in createSavableHheList(HttpSession session,List list) is "+nonDuplicateList.size());
    System.err.println("duplicateList size in createSavableHheList(HttpSession session,List list) is "+duplicateList.size());
    savableList.add(nonDuplicateList);
    savableList.add(duplicateList);
    hheCountList.add(list.size());
    hheCountList.add(duplicateCount);
    session.setAttribute("savableHheList", savableList);
    session.setAttribute("hheBackUpCountList", hheCountList);
    duplicateCount=0;
}
public void removeDeletedWithdrawalRecord(String uniqueId)
{
    try
    {
        if(uniqueId !=null && uniqueDeletedList.contains(uniqueId))
        {
            int index=uniqueDeletedList.indexOf(uniqueId);
            DeletedRecord delr=(DeletedRecord)deletedWithdrawalStatusList.get(index);
            drdao.removeDeletedRecord(delr.getRecordId(), delr.getDateRecordCreated(), delr.getTypeOfRecord(), true);
            uniqueDeletedList.remove(index);
            deletedWithdrawalStatusList.remove(index);
        }
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
}
private void createSavableHvaList(HttpSession session,List list)
{
    HouseholdVulnerabilityAssessmentDao dao=new HouseholdVulnerabilityAssessmentDaoImpl();
    List hvaCountList=new ArrayList();
    List nonDuplicateList=new ArrayList();
    List duplicateList=new ArrayList();
    List savableList=new ArrayList();
    int duplicateCount=0;

    for(Object obj: list)
    {
        HouseholdVulnerabilityAssessment hva=(HouseholdVulnerabilityAssessment)obj;
        try
        {
            HouseholdVulnerabilityAssessment hva2=dao.getHouseholdVulnerabilityAssessment(hva.getHhUniqueId(),hva.getDateOfAssessment());
            if(hva2==null)
            {
                nonDuplicateList.add(hva);
            }
            else
            {
                hva.setId(hva2.getId());
                duplicateList.add(hva);
                duplicateCount++;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    savableList.add(nonDuplicateList);
    savableList.add(duplicateList);
    hvaCountList.add(list.size());
    hvaCountList.add(duplicateCount);
    session.setAttribute("savableHvaList", savableList);
    session.setAttribute("hvaBackUpCountList", hvaCountList);
    duplicateCount=0;
}
private void createSavableCaregiverList(HttpSession session,List list)
{
    CaregiverDao dao=new CaregiverDaoImpl();
    List cgiverCountList=new ArrayList();
    List nonDuplicateList=new ArrayList();
    List duplicateList=new ArrayList();
    List savableList=new ArrayList();
    int duplicateCount=0;

    for(Object obj: list)
    {
        Caregiver cgiver=(Caregiver)obj;
        try
        {
            //removeDeletedWithdrawalRecord(cgiver.getCaregiverId());
            Caregiver cgiver2=dao.getCaregiverByCaregiverId(cgiver.getCaregiverId());
            if(cgiver2==null)
            {
                nonDuplicateList.add(cgiver);
            }
            else
            {
                duplicateList.add(cgiver);
                duplicateCount++;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    savableList.add(nonDuplicateList);
    savableList.add(duplicateList);
    cgiverCountList.add(list.size());
    cgiverCountList.add(duplicateCount);
    session.setAttribute("savablecgiverList", savableList);
    session.setAttribute("cgiverBackUpCountList", cgiverCountList);
    duplicateCount=0;
}
private void createSavableHhServiceList(HttpSession session,List list)
{
    HouseholdServiceDao dao=new HouseholdServiceDaoImpl();
    List hhsCountList=new ArrayList();
    List nonDuplicateList=new ArrayList();
    List duplicateList=new ArrayList();
    List savableList=new ArrayList();
    int duplicateCount=0;
    int i=0;
    System.err.println("list.size() in createSavableHhServiceList is "+list.size());
    for(Object obj: list)
    {
        HouseholdService hhs=(HouseholdService)obj;
        try
        {
            HouseholdService hhs2=dao.getHouseholdService(hhs.getCaregiverId(),hhs.getServiceDate());
            if(hhs2==null)
            {
                i++;
                //System.err.println("hhs.getCaregiverId() is "+hhs.getCaregiverId()+" "+i);
                nonDuplicateList.add(hhs);
            }
            else
            {
                hhs.setId(hhs2.getId());
                duplicateList.add(hhs);
                duplicateCount++;
                //System.err.println("hhs duplicateCount is "+duplicateCount);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    savableList.add(nonDuplicateList);
    savableList.add(duplicateList);
    hhsCountList.add(list.size());
    hhsCountList.add(duplicateCount);
    session.setAttribute("savableHhsList", savableList);
    session.setAttribute("hhsBackUpCountList", hhsCountList);
    duplicateCount=0;
}
private void createSavableOvcList(HttpSession session,List list)
{
    OvcDao dao=new OvcDaoImpl();
    List ovcCountList=new ArrayList();
    List nonDuplicateOvcList=new ArrayList();
    List duplicateOvcList=new ArrayList();
    List savableList=new ArrayList();
    int duplicateCount=0;

    for(Object obj: list)
    {
        Ovc ovc=(Ovc)obj;
        try
        {
            //removeDeletedWithdrawalRecord(ovc.getOvcId());
            List ovcList=dao.searchOvc(ovc.getOvcId());
            if(ovcList.isEmpty())
            {
                nonDuplicateOvcList.add(ovc);
            }
            else
            {
                duplicateOvcList.add(ovc);
                duplicateCount++;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    savableList.add(nonDuplicateOvcList);
    savableList.add(duplicateOvcList);
    ovcCountList.add(list.size());
    ovcCountList.add(duplicateCount);
    session.setAttribute("savableOvcList", savableList);
    session.setAttribute("backUpCountList", ovcCountList);
    duplicateCount=0;

}
private void createSavableCsiList(HttpSession session,List list)
{
    ChildStatusIndexDao dao=new ChildStatusIndexDaoImpl();
    List nonDuplicateCsiList=new ArrayList();
    List duplicateCsiList=new ArrayList();
    List savableList=new ArrayList();
    for(Object obj: list)
    {
        ChildStatusIndex csi=(ChildStatusIndex)obj;
        ChildStatusIndex csi2=null;
        try
        {
            csi2=(ChildStatusIndex)dao.getChildStatusIndex(csi.getOvcId(), csi.getCsiDate());
            //List csiList=dao.getCsiAsList(csi.getOvcId(), csi.getCsiDate());
            if(csi2==null)
            {
                nonDuplicateCsiList.add(csi);
            }
            else
            {
                csi.setId(csi2.getId());
                duplicateCsiList.add(csi);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        savableList.add(nonDuplicateCsiList);
        savableList.add(duplicateCsiList);
        session.setAttribute("savableCsiList", savableList);
    }
}
private void createSavableServiceList(HttpSession session,List list)
{
    OvcServiceDao dao=new OvcServiceDaoImpl();
    List serviceCountList=new ArrayList();
    List nonDuplicateServiceList=new ArrayList();
    List duplicateServiceList=new ArrayList();
    List savableList=new ArrayList();
    int duplicateCount=0;
    int k=0;
    for(Object obj: list)
    {
        OvcService service=(OvcService)obj;
        OvcService ovcService=null;
        try
        {
            ovcService=dao.getOvcServiceForTheMth(service.getOvcId(),service.getServicedate());
            if(ovcService==null)
            {
                k++;
                nonDuplicateServiceList.add(service);
            }
            else
            {
                service.setId(ovcService.getId());
                duplicateServiceList.add(service);
                duplicateCount++;
            }

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    savableList.add(nonDuplicateServiceList);
    savableList.add(duplicateServiceList);
    serviceCountList.add(list.size());
    serviceCountList.add(duplicateCount);
    session.setAttribute("serviceSavableList", savableList);
    session.setAttribute("backUpServiceCountList", serviceCountList);
    duplicateCount=0;

}
private void createOrganizationRecordsList(HttpSession session,List list)
{
    OrganizationRecordsDao orgDao=new OrganizationRecordsDaoImpl();
    List countList=new ArrayList();
    List nonDuplicateList=new ArrayList();
    List duplicateList=new ArrayList();
    List savableList=new ArrayList();
    int duplicateCount=0;
    int k=0;
    for(Object obj: list)
    {
        OrganizationRecords orgRecords=(OrganizationRecords)obj;
        OrganizationRecords orgRecords2=null;
        try
        {
            orgRecords2=orgDao.getOrganizationRecord(orgRecords.getOrgCode());
            if(orgRecords2==null)
            {
                k++;
                nonDuplicateList.add(orgRecords);
            }
            else
            {
                duplicateList.add(orgRecords);
                duplicateCount++;
            }

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    savableList.add(nonDuplicateList);
    savableList.add(duplicateList);
    countList.add(list.size());
    countList.add(duplicateCount);

    session.setAttribute("organizationRecordsSavableList", savableList);
    session.setAttribute("backUpOrganizationRecordsCountList", countList);
    duplicateCount=0;

}
private void createWardList(HttpSession session,List list)
{
    WardDao wardDao=new WardDaoImpl();
    List countList=new ArrayList();
    List nonDuplicateList=new ArrayList();
    List duplicateList=new ArrayList();
    List savableList=new ArrayList();
    int duplicateCount=0;
    int k=0;
    for(Object obj: list)
    {
        Wards ward=(Wards)obj;
        Wards ward2=null;
        try
        {
            ward2=wardDao.getWards(ward.getWard_code());
            if(ward2==null)
            {
                k++;
                nonDuplicateList.add(ward);
            }
            else
            {
                duplicateList.add(ward);
                duplicateCount++;
            }

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    savableList.add(nonDuplicateList);
    savableList.add(duplicateList);
    countList.add(list.size());
    countList.add(duplicateCount);
    session.setAttribute("wardsSavableList", savableList);
    session.setAttribute("backUpWardsCountList", countList);
    duplicateCount=0;

}
private void createSchoolList(HttpSession session,List list)
{
    OvcSchoolDao schoolDao=new OvcSchoolDaoImpl();
    List countList=new ArrayList();
    List nonDuplicateList=new ArrayList();
    List duplicateList=new ArrayList();
    List savableList=new ArrayList();
    int duplicateCount=0;
    int k=0;
    for(Object obj: list)
    {
        OvcSchool school=(OvcSchool)obj;
        OvcSchool school2=null;
        try
        {
            school2=schoolDao.getOvcSchool(school.getState(),school.getLga(),school.getName());
            if(school2==null)
            {
                k++;
                nonDuplicateList.add(school);
            }
            else
            {
                school.setId(school2.getId());
                duplicateList.add(school);
                duplicateCount++;
            }

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    savableList.add(nonDuplicateList);
    savableList.add(duplicateList);
    //countList.add(list.size());
    //countList.add(duplicateCount);
    session.setAttribute("schoolSavableList", savableList);
    session.setAttribute("backUpSchoolCountList", countList);
    duplicateCount=0;

}
private void createOrganizationalCapacityAssessmentList(HttpSession session,List list)
{
    OrganizationalCapacityAssessmentDao orgCapAssessmentDao=new OrganizationalCapacityAssessmentDaoImpl();
    List countList=new ArrayList();
    List nonDuplicateList=new ArrayList();
    List duplicateList=new ArrayList();
    List savableList=new ArrayList();
    int duplicateCount=0;
    int k=0;
    for(Object obj: list)
    {
        OrganizationalCapacityAssessment orgCapAssessment=(OrganizationalCapacityAssessment)obj;
        OrganizationalCapacityAssessment orgCapAssessment2=null;
        try
        {
            orgCapAssessment2=orgCapAssessmentDao.getOrganizationalCapacityAssessment(orgCapAssessment.getOrgCode(),orgCapAssessment.getDateofcapacityassessment());
            if(orgCapAssessment2==null)
            {
                k++;
                nonDuplicateList.add(orgCapAssessment);
            }
            else
            {
                orgCapAssessment.setRecordId(orgCapAssessment2.getRecordId());
                duplicateList.add(orgCapAssessment);
                duplicateCount++;
            }

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    savableList.add(nonDuplicateList);
    savableList.add(duplicateList);
    countList.add(list.size());
    countList.add(duplicateCount);
    session.setAttribute("organizationalCapacityAssessmentSavableList", savableList);
    session.setAttribute("backUpOrganizationalCapAssessmentCountList", countList);
    duplicateCount=0;

}
private void createFollowupRecordsList(HttpSession session,List list)
{
    FollowupDao followupDao=new FollowupDaoImpl();
    List countList=new ArrayList();
    List nonDuplicateList=new ArrayList();
    List duplicateList=new ArrayList();
    List savableList=new ArrayList();
    int duplicateCount=0;
    int k=0;
    for(Object obj: list)
    {
        FollowupSurvey followupSurvey=(FollowupSurvey)obj;
        FollowupSurvey followupSurvey2=null;
        try
        {
            followupSurvey2=followupDao.getFollowup(followupSurvey.getOvcId(),followupSurvey.getDateOfSurvey());
            if(followupSurvey2==null)
            {
                k++;
                nonDuplicateList.add(followupSurvey);
            }
            else
            {
                followupSurvey.setId(followupSurvey2.getId());
                duplicateList.add(followupSurvey);
                duplicateCount++;
            }

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    savableList.add(nonDuplicateList);
    savableList.add(duplicateList);
    countList.add(list.size());
    countList.add(duplicateCount);
    session.setAttribute("followupSurveySavableList", savableList);
    session.setAttribute("backUpFollowupSurveyCountList", countList);
    duplicateCount=0;

}
private void createReferralRecordsList(HttpSession session,List list)
{
    OvcReferralDao referralDao=new OvcReferralDaoImpl();
    List countList=new ArrayList();
    List nonDuplicateList=new ArrayList();
    List duplicateList=new ArrayList();
    List savableList=new ArrayList();
    int duplicateCount=0;
    int k=0;
    for(Object obj: list)
    {
        OvcReferral referral=(OvcReferral)obj;
        OvcReferral referral2=null;
        try
        {
            if(referral !=null && referral.getDateOfReferral() !=null)
            {
                referral2=referralDao.getOvcReferral(referral.getOvcId(),referral.getDateOfReferral());
                if(referral2==null)
                {
                    k++;
                    nonDuplicateList.add(referral);
                }
                else
                {
                    referral.setRecordId(referral2.getRecordId());
                    duplicateList.add(referral);
                    duplicateCount++;
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    savableList.add(nonDuplicateList);
    savableList.add(duplicateList);
    //countList.add(list.size());
    //countList.add(duplicateCount);
    session.setAttribute("referralRecordsSavableList", savableList);
    session.setAttribute("backUpReferralRecordsCountList", countList);
    duplicateCount=0;

}
public List saveOvc(List savableOvcList, boolean hivUpdate,boolean birthRegUpdate)
{
    List resultList=new ArrayList();
    List subList=new ArrayList();
    OvcDao dao=new OvcDaoImpl();
    try
       {
            List list=(List)savableOvcList;
            List nonDuplicateList=(List)list.get(0);
            List duplicateList=(List)list.get(1);
            subList.add("Number of new OVC enrollment records");
            subList.add(nonDuplicateList.size());
            resultList.add(subList);
            subList=new ArrayList();
            subList.add("Number of updates enrollment records ");
            subList.add(duplicateList.size());
            resultList.add(subList);
            for(Object obj: nonDuplicateList)
            {
                Ovc ovc=(Ovc)obj;
                ovc=getOvcWith(ovc);
                dao.saveOvc(ovc,hivUpdate,birthRegUpdate);
                System.err.println("Ovc hhe name is "+ovc.getHhe().getHhFirstname()+" "+ovc.getHhe().getHhSurname());
            }
            for(Object obj: duplicateList)
            {
                Ovc ovc=(Ovc)obj;
                ovc=getOvcWith(ovc);
                dao.updateOvc(ovc,hivUpdate,birthRegUpdate);
                System.err.println("Ovc hhe name is "+ovc.getHhe().getHhFirstname()+" "+ovc.getHhe().getHhSurname());
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    return resultList;
}
private Ovc getOvcWith(Ovc ovc)
{
    boolean bvalue=false;
    ovc.setActiveHivStatusRequired(bvalue);
    ovc.setAddressRequired(bvalue);
    ovc.setBirthRegStatusRequired(bvalue);
    ovc.setCaregiverInfoRequired(bvalue);
    ovc.setHouseholdInfoRequired(bvalue);
    ovc.setVulnearabiltyStatusInfoRequired(bvalue);
    ovc.setSchoolStatusRequired(bvalue);
    return ovc;
}
public List saveCsi(List savableCsiList)
{
    List resultList=new ArrayList();
    List subList=new ArrayList();
    ChildStatusIndexDao dao=new ChildStatusIndexDaoImpl();
       try
       {
           System.err.println("Inside saveCsi");
            List list=(List)savableCsiList;
            if(list !=null)
            {
                List nonDuplicateList=(List)list.get(0);
                List duplicateList=(List)list.get(1);
                for(Object obj: nonDuplicateList)
                {
                    ChildStatusIndex csi=(ChildStatusIndex)obj;
                    dao.saveChildStatusIndex(csi);
                }
                for(Object obj: duplicateList)
                {
                    ChildStatusIndex csi2=(ChildStatusIndex)obj;
                    dao.updateChildStatusIndex(csi2);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    return resultList;
}
public List saveServiceRecords(List savableServiceList,boolean hivUpdate,boolean birthRegUpdate)
{
    List resultList=new ArrayList();
    List subList=new ArrayList();
    OvcServiceDao dao=new OvcServiceDaoImpl();
    int count=0;
    try
        {
            List list=(List)savableServiceList;
            List nonDuplicateList=(List)list.get(0);
            List duplicateList=(List)list.get(1);
            int totalCount=duplicateList.size()+nonDuplicateList.size();
            subList.add("Number of OVC Service records");
            AppUtility.setCurrentImportRecordName("OVC Service records");
            subList.add(totalCount);
            resultList.add(subList);
            
            for(Object obj: nonDuplicateList)
            {
                count++;
                OvcService service=(OvcService)obj;
                dao.saveOvcService(service,hivUpdate,birthRegUpdate);
                AppUtility.setCurrentImportRecordName("OVC Service records: "+count+" of "+totalCount+" saved");
                //System.err.println("Service with ovcId "+service.getOvcId()+" saved ");
            }
            for(Object obj: duplicateList)
            {
                count++;
                OvcService service2=(OvcService)obj;
                dao.updateOvcService(service2,hivUpdate,birthRegUpdate);
                AppUtility.setCurrentImportRecordName("OVC Service records: "+count+" of "+totalCount+" saved");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    return resultList;
}
public List saveOrganizationRecords(List savableOrganizationRecordsList)
{
    List resultList=new ArrayList();
    List subList=new ArrayList();
    OrganizationRecordsDao dao=new OrganizationRecordsDaoImpl();
        try
        {
            List list=(List)savableOrganizationRecordsList;
            System.err.println("list size is "+list.size());
            AppUtility.setCurrentImportRecordName("CBO records");
            List nonDuplicateList=(List)list.get(0);
            List duplicateList=(List)list.get(1);
            for(Object obj: nonDuplicateList)
            {
                OrganizationRecords orgRecords=(OrganizationRecords)obj;
                dao.saveOrgRecords(orgRecords);
            }
            for(Object obj: duplicateList)
            {
                OrganizationRecords orgRecords2=(OrganizationRecords)obj;
                dao.updateOrgRecords(orgRecords2);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return resultList;
}
public List saveOrganizationalCapacityAssessment(List savableOrganizationalCapacityAssessmentList)
{
    List resultList=new ArrayList();
    List subList=new ArrayList();
    OrganizationalCapacityAssessmentDao dao=new OrganizationalCapacityAssessmentDaoImpl();
        try
        {
            List list=(List)savableOrganizationalCapacityAssessmentList;
            List nonDuplicateList=(List)list.get(0);
            List duplicateList=(List)list.get(1);
            int count=duplicateList.size()+nonDuplicateList.size();
            subList.add("Number of Organization capacity assessment records");
            AppUtility.setCurrentImportRecordName("Organizational capacity assessment");
            subList.add(count);
            resultList.add(subList);
            for(Object obj: nonDuplicateList)
            {
                OrganizationalCapacityAssessment orgCapAssessment=(OrganizationalCapacityAssessment)obj;
                dao.saveOrganizationalCapacityAssessment(orgCapAssessment);
            }
            for(Object obj: duplicateList)
            {
                OrganizationalCapacityAssessment orgCapAssessment2=(OrganizationalCapacityAssessment)obj;
                dao.updateOrganizationalCapacityAssessment(orgCapAssessment2);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return resultList;
}
public List saveOvcSchool(List savableSchoolList)
{
    List resultList=new ArrayList();
    List subList=new ArrayList();
    OvcSchoolDao dao=new OvcSchoolDaoImpl();
        try
        {
            List list=(List)savableSchoolList;
            List nonDuplicateList=(List)list.get(0);
            List duplicateList=(List)list.get(1);
            int count=0;
            int totalCount=duplicateList.size()+nonDuplicateList.size();
            subList.add("Number of school records");
            AppUtility.setCurrentImportRecordName("School records");
            subList.add(count);
            resultList.add(subList);
            for(Object obj: nonDuplicateList)
            {
                OvcSchool school=(OvcSchool)obj;
                dao.saveOvcSchool(school);
                count++;
                AppUtility.setCurrentImportRecordName("School records: "+count+" of "+totalCount+" saved");
            }
            for(Object obj: duplicateList)
            {
                OvcSchool school2=(OvcSchool)obj;
                dao.updateOvcSchool(school2);
                count++;
                AppUtility.setCurrentImportRecordName("School records: "+count+" of "+totalCount+" saved");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    return resultList;
}
public List saveHhe(List savableHheList)
{
    List resultList=new ArrayList();
    List subList=new ArrayList();
    HouseholdEnrollmentDao dao=new HouseholdEnrollmentDaoImpl();
        try
        {
            List list=(List)savableHheList;
            if(list !=null && !list.isEmpty())
            {
                List nonDuplicateList=(List)list.get(0);
                List duplicateList=(List)list.get(1);
                subList.add("Number of new household enrollment records");
                AppUtility.setCurrentImportRecordName("Household enrollment records");
                subList.add(nonDuplicateList.size());
                resultList.add(subList);
                subList=new ArrayList();
                subList.add("Number of household enrollment records saved as updates");
                subList.add(duplicateList.size());
                resultList.add(subList);
                int count=0;
                int totalCount=nonDuplicateList.size()+duplicateList.size();
                HouseholdEnrollment hhe=null;
                System.err.println("nonDuplicateList size in saveHhe is "+nonDuplicateList.size());
                for(Object obj: nonDuplicateList)
                {
                    count++;
                    hhe=(HouseholdEnrollment)obj;
                    dao.saveHouseholdEnrollment(hhe);
                    AppUtility.setCurrentImportRecordName("Household enrollment records: "+count+" of "+totalCount+" saved");
                }
                for(Object obj: duplicateList)
                {
                    count++;
                    hhe=(HouseholdEnrollment)obj;
                    dao.updateHouseholdEnrollment(hhe);
                    AppUtility.setCurrentImportRecordName("Household enrollment records: "+count+" of "+totalCount+" saved");
                }
            }
            else
            System.err.println("Household enrollment record list is empty");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    return resultList;
}
public List saveHva(List savableHvaList)
{
    List resultList=new ArrayList();
    List subList=new ArrayList();
    HouseholdVulnerabilityAssessmentDao dao=new HouseholdVulnerabilityAssessmentDaoImpl();
        try
        {
            List list=(List)savableHvaList;
            if(list !=null && !list.isEmpty())
            {
                List nonDuplicateList=(List)list.get(0);
                List duplicateList=(List)list.get(1);
                HouseholdVulnerabilityAssessment hva=null;
                System.err.println("nonDuplicateList size in saveHva is "+nonDuplicateList.size());
                for(Object obj: nonDuplicateList)
                {
                    hva=(HouseholdVulnerabilityAssessment)obj;
                    dao.saveHouseholdVulnerabilityAssessment(hva);
                }
                for(Object obj: duplicateList)
                {
                    hva=(HouseholdVulnerabilityAssessment)obj;
                    dao.updateHouseholdVulnerabilityAssessmentByuniqueIdAndDate(hva);
                }
            }
            else
            System.err.println("Household vulnearbility assessment record list is empty");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    return resultList;
}
public List saveCaregiver(List savableCgiverList)
{
    List resultList=new ArrayList();
    List subList=new ArrayList();
    CaregiverDao dao=new CaregiverDaoImpl();
    AppUtility.setCurrentImportRecordName("Caregiver records");
        try
        {
            List list=(List)savableCgiverList;
            if(list !=null && !list.isEmpty())
            {
                List nonDuplicateList=(List)list.get(0);
                List duplicateList=(List)list.get(1);
                subList.add("Number of new caregiver records");
                subList.add(nonDuplicateList.size());
                resultList.add(subList);
                subList=new ArrayList();
                subList.add("Number of updates caregiver records ");
                subList.add(duplicateList.size());
                resultList.add(subList);
                Caregiver cgiver=null;
                int count=0;
                int totalCount=nonDuplicateList.size()+duplicateList.size();
                //System.err.println("nonDuplicateList size in saveCaregiver is "+nonDuplicateList.size());
                for(Object obj: nonDuplicateList)
                {
                    count++;
                    cgiver=(Caregiver)obj;
                    dao.saveCaregiver(cgiver);
                    AppUtility.setCurrentImportRecordName("Caregiver records: "+count+" of "+totalCount+" saved");
                }
                for(Object obj: duplicateList)
                {
                    count++;
                    cgiver=(Caregiver)obj;
                    dao.updateCaregiver(cgiver);
                    AppUtility.setCurrentImportRecordName("Caregiver records: "+count+" of "+totalCount+" saved");
                }
            }
            else
            System.err.println("Caregiver record list is empty");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    return resultList;
}
public List saveHhs(List savableHhsList)
{
    List resultList=new ArrayList();
    List subList=new ArrayList();
    HouseholdServiceDao dao=new HouseholdServiceDaoImpl();
    AppUtility.setCurrentImportRecordName("Household service");
        try
        {
            List list=(List)savableHhsList;
            List nonDuplicateList=(List)list.get(0);
            List duplicateList=(List)list.get(1);
            int count=0;
            int totalCount=duplicateList.size()+nonDuplicateList.size();
            subList.add("Number of household Service records");
            subList.add(totalCount);
            resultList.add(subList);
            HouseholdService hhs=null;
            for(Object obj: nonDuplicateList)
            {
                count++;
                hhs=(HouseholdService)obj;
                dao.saveHouseholdService(hhs);
                AppUtility.setCurrentImportRecordName("Household service: "+count+" of "+totalCount+" saved");
            }
            for(Object obj: duplicateList)
            {
                count++;
                hhs=(HouseholdService)obj;
                dao.updateHouseholdService(hhs);
                AppUtility.setCurrentImportRecordName("Household service: "+count+" of "+totalCount+" saved");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    return resultList;
}
public List saveWards(List savableWardList)
{
    List resultList=new ArrayList();
    AppUtility.setCurrentImportRecordName("Ward records");
    WardDao dao=new WardDaoImpl();
        try
        {
            List list=(List)savableWardList;
            List nonDuplicateList=(List)list.get(0);
            List duplicateList=(List)list.get(1);
            for(Object obj: nonDuplicateList)
            {
                Wards ward=(Wards)obj;
                dao.saveWard(ward);
            }
            for(Object obj: duplicateList)
            {
                Wards ward2=(Wards)obj;
                dao.updateWard(ward2);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    return resultList;
}
public List saveFollowupSurvey(List savableFollowupSurveyList,boolean hivUpdate,boolean birthRegUpdate)
{
    List resultList=new ArrayList();
    List subList=new ArrayList();
    FollowupDao dao=new FollowupDaoImpl();
        try
        {
            int count=0;
            //AppUtility.setCurrentImportRecordName("OVC Followup records");
            List list=(List)savableFollowupSurveyList;
            List nonDuplicateList=(List)list.get(0);
            List duplicateList=(List)list.get(1);
            int totalCount=duplicateList.size()+nonDuplicateList.size();
            subList.add("Number of follow up assessment records");
            subList.add(totalCount);
            resultList.add(subList);
            for(Object obj: nonDuplicateList)
            {
                count++;
                FollowupSurvey followupSurvey=(FollowupSurvey)obj;
                dao.saveFollowup(followupSurvey,hivUpdate,birthRegUpdate);
                AppUtility.setCurrentImportRecordName("OVC Followup records: "+count+" of "+totalCount+" saved");
            }
            for(Object obj: duplicateList)
            {
                count++;
                FollowupSurvey followupSurvey2=(FollowupSurvey)obj;
                dao.updateFollowup(followupSurvey2,hivUpdate,birthRegUpdate);
                AppUtility.setCurrentImportRecordName("OVC Followup records: "+count+" of "+totalCount+" saved");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    return resultList;
}
public List saveOvcReferral(List savableReferralRecordsList)
{
    List resultList=new ArrayList();
    List subList=new ArrayList();
    OvcReferralDao dao=new OvcReferralDaoImpl();
    int count=0;
    AppUtility.setCurrentImportRecordName("OVC Referral records");
        try
        {
            List list=(List)savableReferralRecordsList;
            List nonDuplicateList=(List)list.get(0);
            List duplicateList=(List)list.get(1);
            int totalCount=duplicateList.size()+nonDuplicateList.size();
            subList.add("Number of referral Service records");
            subList.add(count);
            resultList.add(subList);
            for(Object obj: nonDuplicateList)
            {
                count++;
                OvcReferral referral=(OvcReferral)obj;
                if(referral !=null && referral.getDateOfReferral() !=null)
            
                dao.saveOvcReferral(referral);
                AppUtility.setCurrentImportRecordName("OVC Referral records: "+count+" of "+totalCount+" saved");
            }
            for(Object obj: duplicateList)
            {
                count++;
                OvcReferral referral2=(OvcReferral)obj;
                if(referral2 !=null && referral2.getDateOfReferral() !=null)
                dao.updateOvcReferral(referral2);
                AppUtility.setCurrentImportRecordName("OVC Referral records: "+count+" of "+totalCount+" saved");
            }
        }
        catch(Exception ex)
        {
            NomisLogManager.logStackTrace(ex);
            ex.printStackTrace();
        }
        return resultList;
}

public List saveImportedRecords(HttpSession session,String syncRecords,boolean hivUpdate,boolean birthRegUpdate,DataImportFileUploadManager difum) throws Exception
{
    List resultList=new ArrayList();
    //if(!TaskManager.isDatabaseLocked())
    //{
        TaskManager.setDatabaseLocked(true);
        List savableOvcList=(List)session.getAttribute("savableOvcList");
        List savableCsiList=(List)session.getAttribute("savableCsiList");
        List savableServiceList=(List)session.getAttribute("serviceSavableList");
        List savableOrganizationRecordsList=(List)session.getAttribute("organizationRecordsSavableList");
        List savableWardsList=(List)session.getAttribute("wardsSavableList");
        List savableOrganizationalCapacityAssessmentList=(List)session.getAttribute("organizationalCapacityAssessmentSavableList");
        List savableFollowupSurveyList=(List)session.getAttribute("followupSurveySavableList");
        List savableReferralList=(List)session.getAttribute("referralRecordsSavableList");
        List savableSchoolList=(List)session.getAttribute("schoolSavableList");
        List savableHheList=(List)session.getAttribute("savableHheList");
        List savableHvaList=(List)session.getAttribute("savableHvaList");
        //List savableCaregiverList=(List)session.getAttribute("savablecgiverList");
        List savableHhsList=(List)session.getAttribute("savableHhsList");
        List savableNutritionAssessmentList=(List)session.getAttribute("savableNutritionAssessmentList");

        //resultList.addAll(saveOvc(savableOvcList));
        if(syncRecords !=null && syncRecords.equalsIgnoreCase("on"))
        {
          processDeletedRecords();  
        }
        
        TrainingExportImportManager teim=new TrainingExportImportManager();
        
        if(difum !=null && (difum.getSelectedTableCodes() !=null && difum.getSelectedTableCodes().trim().length()>0))
        {
            String selectedTableCodes=difum.getSelectedTableCodes();
            String[] selectedTableCodesArray=null;
            if(selectedTableCodes !=null)
            selectedTableCodesArray=selectedTableCodes.split(",");
            if(selectedTableCodesArray !=null)
            {
                String tableCode=null;
                for(int i=difum.getImportStatus(); i<selectedTableCodesArray.length; i++)
                {
                    tableCode=selectedTableCodesArray[i];
                    if(tableCode.equalsIgnoreCase("wdr"))
                    resultList.addAll(readOvcWithdrawalFromXml());
                    else if(tableCode.equalsIgnoreCase("vce"))
                    resultList.addAll(readOvcFromXml(session,hivUpdate,birthRegUpdate));
                    else if(tableCode.equalsIgnoreCase("vcs"))
                    resultList.addAll(saveServiceRecords(savableServiceList,hivUpdate,birthRegUpdate));
                    else if(tableCode.equalsIgnoreCase("csi"))
                    resultList.addAll(readCsiScoreFromXml());
                    else if(tableCode.equalsIgnoreCase("hhe"))
                    resultList.addAll(saveHhe(savableHheList));
                    else if(tableCode.equalsIgnoreCase("hhs"))
                    resultList.addAll(saveHhs(savableHhsList));
                    else if(tableCode.equalsIgnoreCase("vcfu"))
                    resultList.addAll(readFollowupSurveyRecordsFromXml(session,hivUpdate,birthRegUpdate));
                    else if(tableCode.equalsIgnoreCase("hhfu"))
                    resultList.addAll(readHouseholdFollowupAssessmentFromXml());
                    else if(tableCode.equalsIgnoreCase("hva"))
                    resultList.addAll(saveHva(savableHvaList));
                    else if(tableCode.equalsIgnoreCase("cgi"))
                    resultList.addAll(readCaregiverFromXml(session));
                    else if(tableCode.equalsIgnoreCase("hsu"))
                    resultList.addAll(readHivStatusUpdateFromXml());
                    else if(tableCode.equalsIgnoreCase("dlr"))
                    processDeletedRecords();
                    
                    else if(tableCode.equalsIgnoreCase("hrac"))
                    resultList.addAll(importHivRiskAssessmentChecklist());
                    
                    else if(tableCode.equalsIgnoreCase("cpa"))
                    resultList.addAll(importCareplanAchievementChecklist());
                    //else if(tableCode.equalsIgnoreCase("cef"))
                    //importCaregiverExpenditureAndSchoolAttendance();
                    else if(tableCode.equalsIgnoreCase("ref"))
                    resultList.addAll(saveOvcReferral(savableReferralList));
                    else if(tableCode.equalsIgnoreCase("trn") || tableCode.equalsIgnoreCase("trnp"))
                    {
                        resultList.addAll(teim.importTrainingData());
                        resultList.addAll(teim.importTrainingParticipant());
                    }
                    else if(tableCode.equalsIgnoreCase("nass"))
                    resultList.addAll(NutritionAssessmentExportImportManager.saveNutritionAssessment(savableNutritionAssessmentList));
                    //else if(tableCode.equalsIgnoreCase("sch"))
                    //resultList.addAll(saveOvcSchool(savableSchoolList));
                    else if(tableCode.equalsIgnoreCase("cgtbhiv"))
                    resultList.addAll(readCaregiverTBHIVScreeningChecklistFromXml());
                    else if(tableCode.equalsIgnoreCase("ovctbhiv"))
                    resultList.addAll(readOvcTBHIVScreeningChecklistFromXml());
                    else if(tableCode.equalsIgnoreCase("grdchk"))
                    resultList.addAll(importGraduationCheckList());
                    else if(tableCode.equalsIgnoreCase("refdir"))
                    resultList.addAll(importReferralDirectory());
                    else if(tableCode.equalsIgnoreCase("casc"))
                    importCareAndSupportChecklist();
                    else
                    {
                        resultList.addAll(saveOrganizationalCapacityAssessment(savableOrganizationalCapacityAssessmentList));
                    }                    
                }
            }
        }
        else
        {
            resultList.addAll(readOvcWithdrawalFromXml());
            resultList.addAll(readOvcFromXml(session,hivUpdate,birthRegUpdate));
            resultList.addAll(readCsiScoreFromXml());
            //resultList.addAll(saveCsi(savableCsiList));
            resultList.addAll(saveServiceRecords(savableServiceList,hivUpdate,birthRegUpdate));
            resultList.addAll(readFollowupSurveyRecordsFromXml(session,hivUpdate,birthRegUpdate));

            //resultList.addAll(saveOrganizationRecords(savableOrganizationRecordsList));

            resultList.addAll(saveOrganizationalCapacityAssessment(savableOrganizationalCapacityAssessmentList));
            //resultList.addAll(saveWards(savableWardsList));
            //resultList.addAll(saveFollowupSurvey(savableFollowupSurveyList));

            //resultList.addAll(saveOvcSchool(savableSchoolList));
            resultList.addAll(saveHva(savableHvaList));
            resultList.addAll(saveHhe(savableHheList));

            resultList.addAll(readCaregiverFromXml(session));
            //resultList.addAll(saveCaregiver(savableCaregiverList));
            resultList.addAll(saveHhs(savableHhsList));
            resultList.addAll(readHouseholdFollowupAssessmentFromXml());
            //resultList.addAll(readOvcWithdrawalFromXml());
            resultList.addAll(saveOvcReferral(savableReferralList));
            NutritionAssessmentExportImportManager.readNutritionAssessmentFromXml(session);
            resultList.addAll(NutritionAssessmentExportImportManager.saveNutritionAssessment(savableNutritionAssessmentList));

            //TrainingExportImportManager teim=new TrainingExportImportManager();
            resultList.addAll(teim.importTrainingData());
            resultList.addAll(teim.importTrainingParticipant());

            resultList.addAll(readHivStatusUpdateFromXml());
            resultList.addAll(readCaregiverTBHIVScreeningChecklistFromXml());
            resultList.addAll(readOvcTBHIVScreeningChecklistFromXml());
            resultList.addAll(importGraduationCheckList());
            resultList.addAll(importReferralDirectory());
            resultList.addAll(importHivRiskAssessmentChecklist());
            resultList.addAll(importCareplanAchievementChecklist());
            importCareAndSupportChecklist();
            //importCaregiverExpenditureAndSchoolAttendance();
        }
    //}
    //TaskManager.setDatabaseLocked(false);
    //NomisUpgrade nu=new NomisUpgrade();
    //nu.updateHouseholdWithdrawal();
    //String values=nu.updateWithdrawalStatus(true);
    //System.err.println("updateWithdrawalStatus values in mergedatabase is "+values);
    return resultList;
}
public List queueAndProcessImportFiles(HttpServletRequest request,String syncRecords, String hivBirthRegUpdate, boolean hivUpdateOnly)
{
    List list=new ArrayList();
    if(!TaskManager.isDatabaseLocked())
    {
        DataImportManager dim=new DataImportManager();
        dim.processImportFiles(request, syncRecords, hivBirthRegUpdate, hivUpdateOnly);
        //list=processImportFiles(request,syncRecords, hivBirthRegUpdate, hivUpdateOnly);
    }
    return list;
}
/*public List processMultipleImportFiles(HttpServletRequest request,String syncRecords, String hivBirthRegUpdate, boolean hivUpdateOnly)
{
    System.err.println("inside processImportFiles-----");
    String fileName=null;
    String userFilePath=null;
    List resultList=new ArrayList();
    if(currentUserName==null)
    currentUserName=appUtil.getCurrentUser(request.getSession());
    boolean directoriesOnly=false;
    boolean validFile=true;
        try
        {
            List list=util.getDataImportUploadManagerDaoInstance().getAllDataImportFileUploadManager(0);
            
            String[] uploadedFileList=appUtil.listFiles(appUtil.getImportFilePath());
            if(list !=null && !list.isEmpty())
            {
                //if files has been manually moved from the import folder, return empty list
                if(uploadedFileList==null || uploadedFileList.length==0)
                return new ArrayList();
                TaskManager.setDatabaseLocked(true);
                String exportDirectory=appUtil.getExportFilePath();
                String destinationDirectory=exportDirectory;

                String filePath=null;
                File fileToBeImported=null;
                DataImportFileUploadManager difum=null;
                String selectedTableCodes=null;
                if(uploadedFileList !=null && uploadedFileList.length>0)
                {
                    for(int i=0; i<list.size(); i++)
                    {
                        if(i>uploadedFileList.length-1)
                        {
                            AppUtility.setCurrentImportFileName(null);
                            break;
                        }
                        //Delete all files in the export directory and recreate the directory if it does not exist
                        zipHandler=new ZipHandler();
                        difum=(DataImportFileUploadManager)list.get(i);
                        
                        String partnerCode=difum.getPartnerCode();
                        filePath=difum.getImportFilePath()+appUtil.getFileSeperator()+difum.getImportFileName();
                        System.err.println("filePath is "+filePath);
                        AppUtility.setCurrentImportFileName(difum.getImportFileName());
                        fileToBeImported=new File(filePath);
                        if(!fileToBeImported.exists())
                        {
                            System.err.println("file "+filePath+" does not exist");
                            //Status of 2 means file was prematurely moved
                            difum.setImportStatus(2);
                            util.getDataImportUploadManagerDaoInstance().updateDataImportFileUploadManager(difum);
                            continue;
                        }
                        userFilePath=appUtil.getExportFilePath()+appUtil.getFileSeperator()+difum.getImportFileName();
                        appUtil.deleteFiles(userFilePath);
                        appUtil.createExportImportDirectories();
                        appUtil.createDirectories(userFilePath);
                        //Move the file if it is not a valid export file or it is a directory
                        if(fileToBeImported.isDirectory() || !filePath.endsWith(".zip"))
                        {
                            System.err.println("file "+filePath+" has been moved");
                            MoveFile.moveFileToAnotherDirectory(filePath,appUtil.getImportDoneDirectoryPath());
                            validFile=false;
                            directoriesOnly=true;
                            //Status of 2 means file was prematurely moved
                            difum.setImportStatus(2);
                            util.getDataImportUploadManagerDaoInstance().updateDataImportFileUploadManager(difum);
                            continue;
                        }
                        directoriesOnly=false;
                        validFile=true;
                        destinationDirectory=destinationDirectory+appUtil.getFileSeperator()+difum.getImportFileName();
                        zipHandler.unZipFile(filePath,destinationDirectory);
                        HttpSession session=request.getSession();
                        boolean hivUpdate=false;
                        boolean birthRegUpdate=true;
                        if(hivUpdateOnly)
                        {
                            zipHandler=null;
                            resultList.addAll(readHivStatusUpdateFromXml());
                        }
                        else
                        {
                            if(hivBirthRegUpdate !=null && hivBirthRegUpdate.equalsIgnoreCase("on"))
                            {
                                hivUpdate=false;
                                birthRegUpdate=false;
                            }
                            if(syncRecords !=null && syncRecords.equalsIgnoreCase("on"))
                            {
                              processDeletedRecords(); 
                            }
                            readHouseholdVulnebilityIndexFromXml(session);
                            zipHandler=null;
                            readHouseholdEnrollmentFromXml(session,partnerCode);
                            readHouseholdVulnebilityAssessmentFromXml(session);
                            readServiceRecordsFromXml(session);
                            readOrganizationRecordsFromXml(null,session);
                            readWardsFromXml(null,session);
                            readOrganizationalAssessmentFromXml(session);
                            readSchoolRecordsFromXml(session);
                            readReferralRecordsFromXml(session);
                            readHouseholdServiceFromXml(session);
                            resultList=saveImportedRecords(session,syncRecords,hivUpdate,birthRegUpdate,difum);
                            request.setAttribute("dbImportMsg","Data import summary");
                        }
                        MoveFile.moveFileToAnotherDirectory(filePath,appUtil.getImportDoneDirectoryPath());
                        fileName=fileToBeImported.getName();
                        generateImportSummary(resultList,fileName);
                        //Status of 1 means file was imported successfully
                        difum.setImportStatus(1);
                        util.getDataImportUploadManagerDaoInstance().updateDataImportFileUploadManager(difum);
                        System.err.println("file "+filePath+" has been imported");
                    }
                }
                //AppUtility.setCurrentImportFileName(null);
                TaskManager.setDatabaseLocked(false);
                if(!directoriesOnly)
                processImportFiles(request, NomisConstant.SYNC_DELETEDFILES, NomisConstant.HIV_BIRTHREGUPDATE,hivUpdateOnly);
                if(!hivList.isEmpty())
                {
                   AppUtility.setCurrentImportRecordName("Updating HIV records");
                   util.getHivStatusUpdateDaoInstance().setActiveHivStatus(hivList);
                   util.getHivStatusUpdateDaoInstance().updateHivTreatmentStatus(hivOnTreatmentList);
                   System.err.println("hivOnTreatmentList.size() is "+hivOnTreatmentList.size());
                   hivList.clear();
                }
                AppUtility.setCurrentImportFileName(null);
                AppUtility.setCurrentImportRecordName("All data imported");
            }
        }
        catch(Exception ex)
        {
            AppUtility.setCurrentImportFileName("An error occured: "+ex.getMessage());
            NomisLogManager.logStackTrace(ex);
            ex.printStackTrace();
        }
        return resultList;
}*/
public List processImportFiles(HttpServletRequest request,String syncRecords, String hivBirthRegUpdate, boolean hivUpdateOnly)
{
    System.err.println("inside processImportFiles-----");
    String fileName=null;
    List resultList=new ArrayList();
    if(currentUserName==null)
    currentUserName=appUtil.getCurrentUser(request.getSession());
    boolean directoriesOnly=false;
    boolean validFile=true;
    if(!TaskManager.isDatabaseLocked())
    {  
        try
        {
            List list=util.getDataImportUploadManagerDaoInstance().getAllDataImportFileUploadManager(0);
            
            String[] uploadedFileList=appUtil.listFiles(appUtil.getImportFilePath());
            if(list !=null && !list.isEmpty())
            {
                //if files has been manually moved from the import folder, return empty list
                if(uploadedFileList==null || uploadedFileList.length==0)
                return new ArrayList();
                TaskManager.setDatabaseLocked(true);
                String destinationDirectory=appUtil.getExportFilePath();

                String filePath=null;
                File fileToBeImported=null;
                DataImportFileUploadManager difum=null;
                String selectedTableCodes=null;
                if(uploadedFileList !=null && uploadedFileList.length>0)
                {
                    for(int i=0; i<list.size(); i++)
                    {
                        if(i>uploadedFileList.length-1)
                        {
                            AppUtility.setCurrentImportFileName(null);
                            break;
                        }
                        //Delete all files in the export directory and recreate the directory if it does not exist
                        
                        //AppUtility.setCurrentImportFileName(uploadedFileList[i]);
                        zipHandler=new ZipHandler();
                        difum=(DataImportFileUploadManager)list.get(i);
                        
                        String partnerCode=difum.getPartnerCode();
                        filePath=difum.getImportFilePath()+appUtil.getFileSeperator()+difum.getImportFileName();
                        System.err.println("filePath is "+filePath);
                        AppUtility.setCurrentImportFileName(difum.getImportFileName());
                        fileToBeImported=new File(filePath);
                        if(!fileToBeImported.exists())
                        {
                            System.err.println("file "+filePath+" does not exist");
                            //Status of 2 means file was prematurely moved
                            difum.setImportStatus(2);
                            util.getDataImportUploadManagerDaoInstance().updateDataImportFileUploadManager(difum);
                            continue;
                        }
                        appUtil.deleteFiles(appUtil.getExportFilePath());
                        appUtil.createExportImportDirectories();
                        //Move the file if it is not a valid export file or it is a directory
                        if(fileToBeImported.isDirectory() || !filePath.endsWith(".zip"))
                        {
                            System.err.println("file "+filePath+" has been moved");
                            MoveFile.moveFileToAnotherDirectory(filePath,appUtil.getImportDoneDirectoryPath());
                            validFile=false;
                            directoriesOnly=true;
                            //Status of 2 means file was prematurely moved
                            difum.setImportStatus(2);
                            util.getDataImportUploadManagerDaoInstance().updateDataImportFileUploadManager(difum);
                            continue;
                        }
                        directoriesOnly=false;
                        validFile=true;
                        zipHandler.unZipFile(filePath,destinationDirectory);
                        HttpSession session=request.getSession();
                        boolean hivUpdate=false;
                        boolean birthRegUpdate=true;
                        if(hivUpdateOnly)
                        {
                            zipHandler=null;
                            resultList.addAll(readHivStatusUpdateFromXml());
                        }
                        else
                        {
                            if(hivBirthRegUpdate !=null && hivBirthRegUpdate.equalsIgnoreCase("on"))
                            {
                                hivUpdate=false;
                                birthRegUpdate=false;
                            }
                            if(syncRecords !=null && syncRecords.equalsIgnoreCase("on"))
                            {
                              processDeletedRecords(); 
                            }
                            readHouseholdVulnebilityIndexFromXml(session);
                            zipHandler=null;
                            //readSchoolRecordsFromXml(session);
                            readHouseholdEnrollmentFromXml(session,partnerCode);
                            readHouseholdVulnebilityAssessmentFromXml(session);
                            readServiceRecordsFromXml(session);
                            readOrganizationRecordsFromXml(null,session);
                            readWardsFromXml(null,session);
                            readOrganizationalAssessmentFromXml(session);
                            
                            readReferralRecordsFromXml(session);
                            readHouseholdServiceFromXml(session);
                            //readSchoolRecordsFromXml(session);
                            //NutritionAssessmentExportImportManager.readNutritionAssessmentFromXml(session);
                            //importCareAndSupportChecklist();
                            //importCaregiverExpenditureAndSchoolAttendance();
                            resultList=saveImportedRecords(session,syncRecords,hivUpdate,birthRegUpdate,difum);
                            request.setAttribute("dbImportMsg","Data import summary");
                            
                        }
                        //lastFileName=fileToBeImported.getName();
                        MoveFile.moveFileToAnotherDirectory(filePath,appUtil.getImportDoneDirectoryPath());
                        fileName=fileToBeImported.getName();
                        generateImportSummary(resultList,fileName);
                        //Status of 1 means file was imported successfully
                        difum.setImportStatus(1);
                        util.getDataImportUploadManagerDaoInstance().updateDataImportFileUploadManager(difum);
                        System.err.println("file "+filePath+" has been imported");
                    }
                }
                //AppUtility.setCurrentImportFileName(null);
                TaskManager.setDatabaseLocked(false);
                if(!directoriesOnly)
                processImportFiles(request, NomisConstant.SYNC_DELETEDFILES, NomisConstant.HIV_BIRTHREGUPDATE,hivUpdateOnly);
                if(!hivList.isEmpty())
                {
                   AppUtility.setCurrentImportRecordName("Updating HIV records");
                   util.getHivStatusUpdateDaoInstance().setActiveHivStatus(hivList);
                   util.getHivStatusUpdateDaoInstance().updateHivTreatmentStatus(hivOnTreatmentList);
                   System.err.println("hivOnTreatmentList.size() is "+hivOnTreatmentList.size());
                   hivList.clear();
                }
                //TaskManager.setDatabaseLocked(false);
                //AppUtility.setCurrentImportRecordName("Cleaning database");
                //DatabaseCleanup dbc=new DatabaseCleanup();
                //String msg=dbc.cleanUpDatabase();
                AppUtility.setCurrentImportFileName(null);
                AppUtility.setCurrentImportRecordName("All data imported");

                //System.err.println(msg);
            }
        }
        catch(Exception ex)
        {
            AppUtility.setCurrentImportFileName("An error occured: "+ex.getMessage());
            NomisLogManager.logStackTrace(ex);
            ex.printStackTrace();
        }
    } 
    return resultList;
}
/*public List processImportFiles(HttpServletRequest request,String syncRecords, String hivBirthRegUpdate, boolean hivUpdateOnly)
{
    System.err.println("inside processImportFiles-----");
    String fileName=null;
    List resultList=new ArrayList();
    if(currentUserName==null)
    currentUserName=appUtil.getCurrentUser(request.getSession());
    boolean directoriesOnly=false;
    boolean validFile=true;
    if(!TaskManager.isDatabaseLocked())
    {  
        try
        {
            List list=util.getDataImportUploadManagerDaoInstance().getAllDataImportFileUploadManager(0);
            String[] uploadedFileList=appUtil.listFiles(appUtil.getImportFilePath());
            if(uploadedFileList !=null && uploadedFileList.length>0)
            {
                TaskManager.setDatabaseLocked(true);
                String destinationDirectory=appUtil.getExportFilePath();

                String filePath=null;
                File fileToBeImported=null;
                for(int i=0; i<uploadedFileList.length; i++)
                {
                    //Delete all files in the export directory and recreate the directory if it does not exist
                    appUtil.deleteFiles(appUtil.getExportFilePath());
                    appUtil.createExportImportDirectories();
                    AppUtility.setCurrentImportFileName(uploadedFileList[i]);
                    zipHandler=new ZipHandler();
                    
                    filePath=appUtil.getImportFilePath()+appUtil.getFileSeperator()+uploadedFileList[i];
                    fileToBeImported=new File(filePath);
                    if(!fileToBeImported.exists())
                    {
                        continue;
                    }
                                        
                    //Move the file if it is not a valid export file or it is a directory
                    if(fileToBeImported.isDirectory() || !filePath.endsWith(".zip"))
                    {
                        MoveFile.moveFileToAnotherDirectory(filePath,appUtil.getImportDoneDirectoryPath());
                        validFile=false;
                        directoriesOnly=true;
                        continue;
                    }
                    directoriesOnly=false;
                    validFile=true;
                    zipHandler.unZipFile(filePath,destinationDirectory);
                    HttpSession session=request.getSession();
                    boolean hivUpdate=false;
                    boolean birthRegUpdate=true;
                    if(hivUpdateOnly)
                    {
                        zipHandler=null;
                        resultList.addAll(readHivStatusUpdateFromXml());
                    }
                    else
                    {
                        if(hivBirthRegUpdate !=null && hivBirthRegUpdate.equalsIgnoreCase("on"))
                        {
                            hivUpdate=false;
                            birthRegUpdate=false;
                        }
                        if(syncRecords !=null && syncRecords.equalsIgnoreCase("on"))
                        {
                          processDeletedRecords(); 
                        }
                        readHouseholdVulnebilityIndexFromXml(session);
                        zipHandler=null;
                        readHouseholdEnrollmentFromXml(session);
                        readHouseholdVulnebilityAssessmentFromXml(session);
                        readServiceRecordsFromXml(session);
                        readOrganizationRecordsFromXml(null,session);
                        readWardsFromXml(null,session);
                        readOrganizationalAssessmentFromXml(session);
                        readSchoolRecordsFromXml(session);
                        readReferralRecordsFromXml(session);
                        readHouseholdServiceFromXml(session);
                        NutritionAssessmentExportImportManager.readNutritionAssessmentFromXml(session);
                        importCareAndSupportChecklist();
                        importCaregiverExpenditureAndSchoolAttendance();
                        resultList=saveImportedRecords(session,syncRecords,hivUpdate,birthRegUpdate);
                        request.setAttribute("dbImportMsg","Data import summary");
                    }
                    //lastFileName=fileToBeImported.getName();
                    MoveFile.moveFileToAnotherDirectory(filePath,appUtil.getImportDoneDirectoryPath());
                    fileName=fileToBeImported.getName();
                    generateImportSummary(resultList,fileName);
                }
                //AppUtility.setCurrentImportFileName(null);
                TaskManager.setDatabaseLocked(false);
                if(!directoriesOnly)
                processImportFiles(request, NomisConstant.SYNC_DELETEDFILES, NomisConstant.HIV_BIRTHREGUPDATE,hivUpdateOnly);
                if(!hivList.isEmpty())
                {
                   AppUtility.setCurrentImportRecordName("Updating HIV records");
                   util.getHivStatusUpdateDaoInstance().setActiveHivStatus(hivList);
                   util.getHivStatusUpdateDaoInstance().updateHivTreatmentStatus(hivOnTreatmentList);
                   System.err.println("hivOnTreatmentList.size() is "+hivOnTreatmentList.size());
                   hivList.clear();
                }
                //TaskManager.setDatabaseLocked(false);
                AppUtility.setCurrentImportRecordName("Cleaning database");
                DatabaseCleanup dbc=new DatabaseCleanup();
                String msg=dbc.cleanUpDatabase();
                AppUtility.setCurrentImportFileName(null);
                //AppUtility.setCurrentImportRecordName("Done");

                System.err.println(msg);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    } 
    return resultList;
}*/
private void saveDatabaseImportTracker(String summary,String fileName)
{
    //appUtil.getCurrentDate();
    String userName=null;
    if(fileName !=null)
    {
        if(fileName.indexOf("_") !=-1)
        {
            String[] fileNameArray=fileName.split("_");
            if(fileNameArray !=null && fileNameArray.length >2)
            {
                //This file may have been uploaded by another user, extract the persons userName
                userName=fileNameArray[fileNameArray.length-2];
            }
            else //Use the current userName
            userName=currentUserName;
        }
        if(userName==null)
        userName="unknown";
        DatabaseImportTracker dit=new DatabaseImportTracker();
        dit.setDateOfImport(appUtil.getCurrentDate());
        dit.setSummary(summary);
        dit.setUserName(userName);
        dit.setFileName(fileName);
        dit.setResponseSent("No");
        dit.setDateAndTime(DateManager.getDefaultCurrentDateAndTime() );
        DatabaseImportTrackerDao ditDao=new DatabaseImportTrackerDaoImpl();
        try
        {
            ditDao.saveDatabaseImportTracker(dit);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();;
        }
    }
}
private void generateImportSummary(List resultList,String fileName)
{
    List subList=null;
    String summary="";
    for(int i=0; i<resultList.size(); i++)
    {
        subList=(List)resultList.get(i);
        if(subList.size()>1)
        {
            summary=summary.concat(subList.get(0).toString());
            summary=summary.concat("=");
            summary=summary.concat(subList.get(1).toString());
            if(i<resultList.size()-1)
            summary=summary.concat(";");
        }
    }
    saveDatabaseImportTracker(summary,fileName);
    //request.setAttribute("resultList", resultList);
}
private static String getNodeName(String value,int s,NodeList listOfObjects)
{
    String elementName=null;
    Node firstPersonNode = listOfObjects.item(s);
    Element firstPersonElement = (Element)firstPersonNode;
    if(firstPersonElement !=null)
    {
        NodeList firstNameList = firstPersonElement.getElementsByTagName(value);
        Element firstNameElement = (Element)firstNameList.item(0);

        try
        {
            if(firstNameElement !=null)
            elementName=firstNameElement.getNodeName();
        }
        catch(NullPointerException npe)
        {
            elementName=null;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    return elementName;
}
private static String getNodeValue(String value,int s,NodeList listOfObjects)
{
    String elementValue=" ";
    Node firstPersonNode = listOfObjects.item(s);
    Element firstPersonElement = (Element)firstPersonNode;
    if(firstPersonElement !=null)
    {
        NodeList firstNameList = firstPersonElement.getElementsByTagName(value);
        Element firstNameElement = (Element)firstNameList.item(0);

        NodeList textFNList =null;
        if(firstNameElement !=null)
        {
            textFNList =firstNameElement.getChildNodes();
        }
        try
        {
            if((Node)textFNList==null)
            elementValue="";
            else if((Node)textFNList.item(0)==null)
            elementValue="";
            else if(((Node)textFNList.item(0)).getNodeValue()==null || (((Node)textFNList.item(0)).getNodeValue()).equals("") || (((Node)textFNList.item(0)).getNodeValue()).equals(" ") || (((Node)textFNList.item(0)).getNodeValue()).equals("none"))
            elementValue="";
            else
            elementValue=((Node)textFNList.item(0)).getNodeValue();
        }
        catch(NullPointerException npe)
        {
            elementValue="";
        }
    }
    return elementValue;
}
private List getFiles(String filePath)
{
    String[] files=appUtil.listFiles(filePath);
    List fileList=new ArrayList();
    if(files !=null)
    {
        for(int i=0; i<files.length; i++)
        {
            if(files[i].indexOf(".xml") ==-1)
            continue;
            fileList.add(files[i]);
        }
    }
    return fileList;
}
}
