/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author smomoh
 */
package ImportExport;
import com.fhi.kidmap.business.CareAndSupportChecklist;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.DeletedRecordDao;
import com.fhi.kidmap.dao.DeletedRecordDaoImpl;
import com.fhi.kidmap.dao.HivStatusUpdateDao;
import com.fhi.kidmap.dao.HivStatusUpdateDaoImpl;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.OvcWithdrawalDao;
import com.fhi.kidmap.dao.OvcWithdrawalDaoImpl;
import com.fhi.kidmap.report.SummaryStatisticsBean;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Caregiver;
import com.fhi.kidmap.business.CaregiverExpenditureAndSchoolAttendance;
import com.fhi.kidmap.business.CaregiverTBHIVScreeningChecklist;
import com.fhi.kidmap.business.CareplanAchievement;
import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.nomis.nomisutils.DataEncryption;
import com.fhi.kidmap.business.DeletedRecord;
import com.fhi.kidmap.business.FollowupSurvey;
import com.fhi.kidmap.business.GraduationCheckList;
import com.fhi.kidmap.business.HivRiskAssessmentChecklist;
import com.fhi.kidmap.business.HivStatusUpdate;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.HouseholdFollowupAssessment;
import com.fhi.kidmap.business.HouseholdService;
import com.fhi.kidmap.business.HouseholdVulnerabilityAssessment;
import com.fhi.kidmap.business.NutritionAssessment;
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
import com.fhi.kidmap.dao.GraduationCheckListDao;
import com.fhi.kidmap.dao.GraduationCheckListDaoImpl;
import com.fhi.kidmap.dao.HivRiskAssessmentChecklistDao;
import com.fhi.kidmap.dao.HivRiskAssessmentChecklistDaoImpl;
import com.fhi.kidmap.dao.OrganizationRecordsDao;
import com.fhi.kidmap.dao.OrganizationRecordsDaoImpl;
import com.fhi.kidmap.dao.OvcTBHIVScreeningChecklistDao;
import com.fhi.kidmap.dao.OvcTBHIVScreeningChecklistDaoImpl;
import com.fhi.kidmap.dao.ReferralDirectoryDao;
import com.fhi.kidmap.dao.ReferralDirectoryDaoImpl;
import com.fhi.kidmap.dao.WardDao;
import com.fhi.kidmap.dao.WardDaoImpl;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.xml.sax.helpers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.sax.*;


public class DatabaseExport implements Serializable
{
    ZipHandler zipHandler;
    DataEncryption encryptor;
    AppUtility appUtil;
    String fileSeperator="\\";
    DaoUtil util;
    List mainList=null;
   public DatabaseExport()
   {
      appUtil=new AppUtility();
      fileSeperator=appUtil.getFileSeperator();
      appUtil.createExportImportDirectories();
   }
private String getPropertyValue(String propertyValue)
{
    if(propertyValue==null || propertyValue.equals(" ") || propertyValue.equals(""))
     propertyValue="none";
     propertyValue=propertyValue.trim();
    return propertyValue;
}
private String getIntegerPropertyValue(String propertyValue)
{
    if(propertyValue==null || propertyValue.equals(" ") || propertyValue.equals(""))
     propertyValue="0";
     propertyValue=propertyValue.trim();
    return propertyValue;
}
public int createDataExportSummaryInXml(String parentFolderPath,DataExportSummary des) throws Exception
{
    String fileName="";
    String parentFolder="DataExportSummary";
    
    String fileSeperator=appUtil.getFileSeperator();
    int noOfRecords=0;
      try
      {
          int startSize=0;
        int count=0;
        
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");

	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	AttributesImpl atts = new AttributesImpl();
                
        String[] columnNames={"exportId","systemTime","state","lga","cboName","ward","exportPeriod","hheenrolledIndb","hhcenrolledIndb","vceenrolledIndb","vccenrolledIndb","hhenrolledexported","vceenrolledexported","cgiverexported","hhfollowupexported","vcfollowupexported","hhserviceexported","vcserviceexported","csiexported","careandsupportexported","cgiverExpenditureexported","careplanexported","cgivertbhivexported","vctbhivexported","hivriskassessexported","hhreferralexported","schoolexported","trainingexported","withdrawalexported"};
        atts.clear();
        
            hd.startDocument();
            hd.startElement("","",parentFolder,atts);
            startSize=count;
            System.err.println("startSize is "+startSize);
            
              String exportId=getPropertyValue(des.getExportId());
              String systemTime=getPropertyValue(des.getSystemTime());
              String state=getPropertyValue(des.getLevel2OuName());
              String lga=getPropertyValue(des.getLevel3OuName());
              String cboName=getPropertyValue(des.getCboName());
              String ward=getPropertyValue(des.getLevel4OuName());
              
              String exportPeriod=getPropertyValue(des.getExportPeriod());
              String hheenrolledIndb=getIntegerPropertyValue(des.getHhEverEnrolledInDatabase()+"");
              String hhcenrolledIndb=getIntegerPropertyValue(des.getHhCurrentlyEnrolledInDatabase()+"");
              String vceenrolledIndb=getIntegerPropertyValue(des.getVcEverEnrolledInDatabase()+"");
              String vccenrolledIndb=getIntegerPropertyValue(des.getVcCurrentlyEnrolledInDatabase()+"");
              
              String hhenrolledexported=getIntegerPropertyValue(des.getNoOfHHEnrollmentRecordsExported()+"");
              String vceenrolledexported=getIntegerPropertyValue(des.getNoOfVCEnrollmentRecordsExported()+"");
              String cgiverexported=getIntegerPropertyValue(des.getNoOfCaregiverEnrollmentRecordsExported()+"");
              String hhfollowupexported=getIntegerPropertyValue(des.getNoOfHHFollowupAssessmentRecordsExported()+"");
              
              String vcfollowupexported=getIntegerPropertyValue(des.getNoOfVCFollowupAssessmentRecordsExported()+"");
              String hhserviceexported=getIntegerPropertyValue(des.getNoOfHHServiceRecordsExported()+"");
              String vcserviceexported=getIntegerPropertyValue(des.getNoOfVCServiceRecordsExported()+"");
              String csiexported=getIntegerPropertyValue(des.getNoOfCSIRecordsExported()+"");
              String careandsupportexported=getIntegerPropertyValue(des.getCareAndSupportRecordsExported()+"");
              
              String cgiverExpenditureexported=getIntegerPropertyValue(des.getCaregiverExpenditureRecordsExported()+"");
              String careplanexported=getIntegerPropertyValue(des.getCareplanAchievementRecordsExported()+"");
              String vctbhivexported=getIntegerPropertyValue(des.getNoOfVCTBHIVRecordsExported()+"");
              String hivriskassessexported=getIntegerPropertyValue(des.getNoOfHIVRiskAssessmentRecordsExported()+"");
              String cgivertbhivexported=getIntegerPropertyValue(des.getNoOfCgiverTBHIVRecordsExported()+"");
              String hhreferralexported=getIntegerPropertyValue(des.getNoOfReferralRecordsExported()+"");
              String schoolexported=getIntegerPropertyValue(des.getNoOfSchoolRecordsExported()+"");
              String trainingexported=getIntegerPropertyValue(des.getNoOfTrainingRecordsExported()+"");
              String withdrawalexported=getIntegerPropertyValue(des.getNoOfWithdrawalRecordsExported()+"");
              
              String[] fieldValues={exportId,systemTime,state,lga,cboName,ward,exportPeriod,hheenrolledIndb,hhcenrolledIndb,vceenrolledIndb,vccenrolledIndb,hhenrolledexported,vceenrolledexported,cgiverexported,hhfollowupexported,vcfollowupexported,hhserviceexported,vcserviceexported,csiexported,careandsupportexported,cgiverExpenditureexported,careplanexported,cgivertbhivexported,vctbhivexported,hivriskassessexported,hhreferralexported,schoolexported,trainingexported,withdrawalexported};
              hd.startElement("","","elementName",atts);
                for (int i=0;i<columnNames.length;i++)
                {
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
                }
              hd.endElement("","","elementName");
              count++;
            
            hd.endElement("","",parentFolder);
            hd.endDocument();
            String xmlString = sw.toString();
            fileName=parentFolder+".xml";
            
            String exportDestination=appUtil.getExportFilePath()+fileSeperator+parentFolder+fileSeperator+fileName;
            if(parentFolderPath !=null)//parentFolderPath
            exportDestination=parentFolderPath+fileName;
            File file = new File(exportDestination);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(xmlString);
            bw.flush();
            bw.close();
        }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
      return noOfRecords;
}
public int exportCaregiverExpenditureAndSchoolAttendanceInXml(String parentFolderPath,String additionalQueryCriteria) throws Exception
{
    String fileName="";
    String parentFolder="CaregiverExpenditureAndSchoolAttendance";
    //appUtil.createExportImportDirectories();
    String fileSeperator=appUtil.getFileSeperator();
    int noOfRecords=0;
      try
      {
          int startSize=0;
        int count=0;
        CaregiverExpenditureAndSchoolAttendanceDao candcdao=new CaregiverExpenditureAndSchoolAttendanceDaoImpl();
        List list=candcdao.getCaregiverExpenditureAndSchoolAttendanceList(additionalQueryCriteria);
        if(list !=null)
        {
            noOfRecords=list.size();
        
        double loopCount=Math.ceil((list.size()/5000d));
          for(int k=0; k<loopCount; k++)
        {
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");

	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	
	AttributesImpl atts = new AttributesImpl();
        
        
        if(list==null)
        list=new ArrayList();
        String[] columnNames={"recordId","caregiverId","dateOfAssessment","unexpectedExpenditure","accessMoney","sourceOfMoney","urgentHhNeeds","schAttendance","ovcId","reasonsForMissingSch","volunteerName","volunteerPhone","recordedBy","lastModifiedDate"};
        
	
        atts.clear();
        
        
            hd.startDocument();
            hd.startElement("","",parentFolder,atts);
            startSize=count;
            System.err.println("startSize is "+startSize);
            for(int j=startSize; j<list.size(); j++)
            {
                if(j>((k+1)*4999))
                break;
                CaregiverExpenditureAndSchoolAttendance ceasa=(CaregiverExpenditureAndSchoolAttendance)list.get(j);
              String recordId=getIntegerPropertyValue(ceasa.getRecordId()+"");
              String caregiverId=getPropertyValue(ceasa.getCaregiverId());
              String dateOfAssessment=getPropertyValue(ceasa.getDateOfAssessment());
              String unexpectedExpenditure=getPropertyValue(ceasa.getUnexpectedExpenditure());
              String accessMoney=getPropertyValue(ceasa.getAccessMoney());
              String sourceOfMoney=getPropertyValue(ceasa.getSourceOfMoney());
              String urgentHhNeeds=getPropertyValue(ceasa.getUrgentHhNeeds());
              String lastModifiedDate=getPropertyValue(ceasa.getLastModifiedDate());
              String schAttendance=getPropertyValue(ceasa.getSchAttendance());
              String ovcId=getPropertyValue(ceasa.getOvcId());
              String reasonsForMissingSch=getPropertyValue(ceasa.getReasonsForMissingSch());
              String volunteerName=getPropertyValue(ceasa.getVolunteerName());
              String volunteerPhone=getPropertyValue(ceasa.getVolunteerPhone());
              String recordedBy=getPropertyValue(ceasa.getRecordedBy());
              
              
              String[] fieldValues={recordId,caregiverId,dateOfAssessment,unexpectedExpenditure,accessMoney,sourceOfMoney,urgentHhNeeds,schAttendance,ovcId,reasonsForMissingSch,volunteerName,volunteerPhone,recordedBy,lastModifiedDate};
              hd.startElement("","","elementName",atts);
                    for (int i=0;i<columnNames.length;i++)
                    {
                        hd.startElement("","",columnNames[i],atts);
                        hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                        hd.endElement("","",columnNames[i]);
                    }
              hd.endElement("","","elementName");
              count++;
            }
        
            hd.endElement("","",parentFolder);
            hd.endDocument();
            String xmlString = sw.toString();
            fileName=parentFolder+".xml";
            if(k>0)
            fileName=parentFolder+k+".xml";
            String exportDestination=appUtil.getExportFilePath()+fileSeperator+parentFolder+fileSeperator+fileName;
            if(parentFolderPath !=null)//parentFolderPath
            exportDestination=parentFolderPath+fileName;
            File file = new File(exportDestination);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(xmlString);
            bw.flush();
            bw.close();
        }
       }
      }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
      return noOfRecords;
}
public int exportCareAndSupportChecklistInXml(String parentFolderPath,String additionalQueryCriteria) throws Exception
{
    String fileName="";
    String parentFolder="CareAndSupportChecklist";
    //appUtil.createExportImportDirectories();
    String fileSeperator=appUtil.getFileSeperator();
    int noOfRecords=0;
      try
      {
          int startSize=0;
        int count=0;
        CareAndSupportDao candcdao=new CareAndSupportDaoImpl();
        List list=candcdao.getCareAndSupportForExport(additionalQueryCriteria, "All", "All", "asc");
        if(list !=null)
        {
            noOfRecords=list.size();
        
        double loopCount=Math.ceil((list.size()/5000d));
          for(int k=0; k<loopCount; k++)
        {
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");

	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	
	AttributesImpl atts = new AttributesImpl();
        
        
        if(list==null)
        list=new ArrayList();
        String[] columnNames={"recordId","uniqueId","dateOfAssessment","dateOfHivTest","lastModifiedDate","receivedTestResult","hivStatus","enrolledOnTreatment","dateEnrolledOnART","treatmentFacility","viralLoadTestDone","dateOfViralLoadTest","medicationPickedUp","skippedARV","reasonsPeopleSkipARV"
                ,""+"transportationSupport","experiencedSoresOrRash","volunteerName","designation","approvalLevel","recordDeleted"};
        
	
        atts.clear();
        
        
            hd.startDocument();
            hd.startElement("","",parentFolder,atts);
            startSize=count;
            System.err.println("startSize is "+startSize);
            for(int j=startSize; j<list.size(); j++)
            {
                if(j>((k+1)*4999))
                break;
                CareAndSupportChecklist csc=(CareAndSupportChecklist)list.get(j);
              String recordId=getIntegerPropertyValue(csc.getRecordId()+"");
              String uniqueId=getPropertyValue(csc.getClientId());
              String dateOfAssessment=getPropertyValue(csc.getDateOfAssessment());
              String dateOfHivTest=getPropertyValue(csc.getDateOfHivTest());
              String receivedTestResult=getPropertyValue(csc.getReceivedTestResult());
              String hivStatus=getPropertyValue(csc.getHivStatus());
              String enrolledOnTreatment=getPropertyValue(csc.getEnrolledOnTreatment());
              String lastModifiedDate=getPropertyValue(csc.getLastModifiedDate());
              String dateEnrolledOnART=getPropertyValue(csc.getDateEnrolledOnART());
              String treatmentFacility=getPropertyValue(csc.getTreatmentFacility());
              String viralLoadTestDone=getPropertyValue(csc.getViralLoadTestDone());
              String dateOfViralLoadTest=getPropertyValue(csc.getDateOfViralLoadTest());
              String medicationPickedUp=getPropertyValue(csc.getMedicationPickedUp());
              String skippedARV=getPropertyValue(csc.getSkippedARV());
              String reasonsPeopleSkipARV=getPropertyValue(csc.getReasonsPeopleSkipARV());
              String transportationSupport=getPropertyValue(csc.getTransportationSupport());
              String experiencedSoresOrRash=getPropertyValue(csc.getExperiencedSoresOrRash());
              String volunteerName=getPropertyValue(csc.getVolunteerName());
              String designation=getPropertyValue(csc.getDesignation());
              String approvalLevel=getIntegerPropertyValue(csc.getApprovalLevel()+"");
              String recordDeleted=getIntegerPropertyValue(csc.getRecordDeleted()+"");
              
              String[] fieldValues={recordId,uniqueId,dateOfAssessment,dateOfHivTest,lastModifiedDate,receivedTestResult,hivStatus,enrolledOnTreatment,dateEnrolledOnART,treatmentFacility,viralLoadTestDone,dateOfViralLoadTest,medicationPickedUp,skippedARV,reasonsPeopleSkipARV, transportationSupport,experiencedSoresOrRash,volunteerName,designation,approvalLevel,recordDeleted};
              hd.startElement("","","elementName",atts);
                    for (int i=0;i<columnNames.length;i++)
                    {
                        hd.startElement("","",columnNames[i],atts);
                        hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                        hd.endElement("","",columnNames[i]);
                    }
              hd.endElement("","","elementName");
              count++;
            }
        
            hd.endElement("","",parentFolder);
            hd.endDocument();
            String xmlString = sw.toString();
            fileName=parentFolder+".xml";
            if(k>0)
            fileName=parentFolder+k+".xml";
            String exportDestination=appUtil.getExportFilePath()+fileSeperator+parentFolder+fileSeperator+fileName;
            if(parentFolderPath !=null)//parentFolderPath
            exportDestination=parentFolderPath+fileName;
            File file = new File(exportDestination);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(xmlString);
            bw.flush();
            bw.close();
        }
       }
      }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
      return noOfRecords;
}
public int exportHivRiskAssessmentChecklistInXml(String parentFolderPath,String additionalQueryCriteria) throws Exception
{
    int noOfRecords=0;
    String fileName="";
    String parentFolder="HivRiskAssessmentChecklist";
    //appUtil.createExportImportDirectories();
    String fileSeperator=appUtil.getFileSeperator();
      try
      {
          int startSize=0;
        int count=0;
        HivRiskAssessmentChecklistDao hracdao=new HivRiskAssessmentChecklistDaoImpl();
        List list=hracdao.getHivRiskAssessmentChecklist(additionalQueryCriteria);
        if(list !=null)
        {
            noOfRecords=list.size();
        }
        double loopCount=Math.ceil((list.size()/5000d));
          for(int k=0; k<loopCount; k++)
        {
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");

	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	
	AttributesImpl atts = new AttributesImpl();
        
        
        if(list==null)
        list=new ArrayList();
        String[] columnNames={"ovcId","dateOfAssessment","lastModifiedDate","childAgeQuestion","childAgeComment","childTestedQuestion","childTestedComment","hivParentQuestion","hivParentComment","childSickQuestion","childSickComment","skinProblemQuestion",""
        +"skinProblemComment","parentDeceasedQuestion","parentDeceasedComment","chronicallyIllQuestion","chronicallyIllComment","schoolGradeQuestion","schoolGradeComment","sexualAbuseQuestion","sexualAbuseComment",""
        +"sexuallyActiveQuestion","sexuallyActiveComment","genitalDischargeQuestion","genitalDischargeComment","childAtRiskQuestion","bloodTransfusionQuestion","bloodTransfusionComment","muacbmiQuestion","muacbmiComment","hivStatusQuestion","hivStatus","serviceProviderName","designation","serviceProviderPhone"};
        //String[] columnNames={"id","clientId","clientType","dateOfAssessment","dateModified","health","safety","school","stability","vulnerability","graduated","gclscore","volunteerId","recordedby"};
	
        atts.clear();
        
        
            hd.startDocument();
            hd.startElement("","",parentFolder,atts);
            startSize=count;
            System.err.println("startSize is "+startSize);
            for(int j=startSize; j<list.size(); j++)
            {
                if(j>((k+1)*4999))
                break;
                HivRiskAssessmentChecklist hrac=(HivRiskAssessmentChecklist)list.get(j);

              String ovcId=getPropertyValue(hrac.getOvcId());
              String dateOfAssessment=getPropertyValue(hrac.getDateOfAssessment());
              String childAgeQuestion=getPropertyValue(hrac.getChildAgeQuestion());
              String childAgeComment=getPropertyValue(hrac.getChildAgeComment());
              String childTestedQuestion=getPropertyValue(hrac.getChildTestedQuestion());
              String childTestedComment=getPropertyValue(hrac.getChildTestedComment());
              String lastModifiedDate=hrac.getLastModifiedDate();
              String hivParentQuestion=getPropertyValue(hrac.getHivParentQuestion());
              String hivParentComment=getPropertyValue(hrac.getHivParentComment());
              String childSickQuestion=getPropertyValue(hrac.getChildSickQuestion());
              String childSickComment=getPropertyValue(hrac.getChildSickComment());
              String skinProblemQuestion=getPropertyValue(hrac.getSkinProblemQuestion());
              String skinProblemComment=getPropertyValue(hrac.getSkinProblemComment());
              
              String parentDeceasedQuestion=hrac.getParentDeceasedQuestion();
              String parentDeceasedComment=getPropertyValue(hrac.getParentDeceasedComment());
              String chronicallyIllQuestion=getPropertyValue(hrac.getChronicallyIllQuestion());
              String chronicallyIllComment=getPropertyValue(hrac.getChronicallyIllComment());
              String schoolGradeQuestion=getPropertyValue(hrac.getSchoolGradeQuestion());
              String schoolGradeComment=getPropertyValue(hrac.getSchoolGradeComment());
              String sexualAbuseQuestion=getPropertyValue(hrac.getSexualAbuseQuestion());
              
              String sexualAbuseComment=getPropertyValue(hrac.getSexualAbuseComment());
              String sexuallyActiveQuestion=getPropertyValue(hrac.getSexuallyActiveQuestion());
              String sexuallyActiveComment=getPropertyValue(hrac.getSexuallyActiveComment());
              String genitalDischargeQuestion=getPropertyValue(hrac.getGenitalDischargeQuestion());
              String genitalDischargeComment=getPropertyValue(hrac.getGenitalDischargeComment());
              String childAtRiskQuestion=getPropertyValue(hrac.getChildAtRiskQuestion());
              String bloodTransfusionQuestion=getPropertyValue(hrac.getBloodTransfusionQuestion());
              String bloodTransfusionComment=getPropertyValue(hrac.getBloodTransfusionComment());
              String muacbmiQuestion=getPropertyValue(hrac.getMuacbmiQuestion());
              String muacbmiComment=getPropertyValue(hrac.getMuacbmiComment());
              String hivStatusQuestion=getPropertyValue(hrac.getHivStatusQuestion());
              String hivStatus=getPropertyValue(hrac.getHivStatus());
              String serviceProviderName=getPropertyValue(hrac.getServiceProviderName());
              String designation=getPropertyValue(hrac.getDesignation());
              String serviceProviderPhone=getPropertyValue(hrac.getServiceProviderPhone());
              
              
              String[] fieldValues={ovcId,dateOfAssessment,lastModifiedDate,childAgeQuestion,childAgeComment,childTestedQuestion,childTestedComment,hivParentQuestion,hivParentComment,childSickQuestion,childSickComment,skinProblemQuestion,
        skinProblemComment,parentDeceasedQuestion,parentDeceasedComment,chronicallyIllQuestion,chronicallyIllComment,schoolGradeQuestion,schoolGradeComment,sexualAbuseQuestion,sexualAbuseComment,
        sexuallyActiveQuestion,sexuallyActiveComment,genitalDischargeQuestion,genitalDischargeComment,childAtRiskQuestion,bloodTransfusionQuestion,bloodTransfusionComment,muacbmiQuestion,muacbmiComment,hivStatusQuestion,hivStatus,serviceProviderName,designation,serviceProviderPhone};
              hd.startElement("","","elementName",atts);
                    for (int i=0;i<columnNames.length;i++)
                    {
                        hd.startElement("","",columnNames[i],atts);
                        hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                        hd.endElement("","",columnNames[i]);
                    }
              hd.endElement("","","elementName");
              count++;
            }
        
            hd.endElement("","",parentFolder);
            hd.endDocument();
            String xmlString = sw.toString();
            fileName=parentFolder+".xml";
            if(k>0)
            fileName=parentFolder+k+".xml";
            String exportDestination=appUtil.getExportFilePath()+fileSeperator+parentFolder+fileSeperator+fileName;
            if(parentFolderPath !=null)//parentFolderPath
            exportDestination=parentFolderPath+fileName;
            File file = new File(exportDestination);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(xmlString);
            bw.flush();
            bw.close();
        }
       }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
      return noOfRecords;
}
public void exportWardInXml(String parentFolderPath,String additionalQueryCriteria) throws Exception
{
    
    String fileName=" ";
    String parentFolder="Wards";
    //appUtil.createExportImportDirectories();
    String fileSeperator=appUtil.getFileSeperator();
    
      try
      {
          int startSize=0;
        int count=0;
        WardDao wdao=new WardDaoImpl();
        List list=wdao.getWardListByOrgUnits(additionalQueryCriteria);;
        double loopCount=Math.ceil((list.size()/5000d));
          for(int k=0; k<loopCount; k++)
        {
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");

	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	
	AttributesImpl atts = new AttributesImpl();
        
        
        if(list==null)
        list=new ArrayList();
        String[] columnNames={"cbo_code","ward_code","ward_name"};
        	
        atts.clear();
        
        
            hd.startDocument();
            hd.startElement("","",parentFolder,atts);
            startSize=count;
            System.err.println("startSize is "+startSize);
            for(int j=startSize; j<list.size(); j++)
            {
                if(j>((k+1)*4999))
                break;
                Wards ward=(Wards)list.get(j);

              String cbo_code=getPropertyValue(ward.getCbo_code());
              String ward_code=getPropertyValue(ward.getWard_code());
              String ward_name=getPropertyValue(ward.getWard_name());
       
              String[] fieldValues={cbo_code,ward_code,ward_name};
              hd.startElement("","","patient",atts);
                for (int i=0;i<columnNames.length;i++)
                {
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
                }
              hd.endElement("","","patient");
              count++;
            }
        
            hd.endElement("","",parentFolder);
            hd.endDocument();
            String xmlString = sw.toString();
            fileName=parentFolder+".xml";
            if(k>0)
            fileName=parentFolder+k+".xml";

            String exportDestination=appUtil.getExportFilePath()+fileSeperator+parentFolder+fileSeperator+fileName;
            if(parentFolderPath !=null)
            exportDestination=parentFolderPath+fileName;
            System.err.println("exportDestination is "+exportDestination);
            File file = new File(exportDestination);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(xmlString);
            bw.flush();
            bw.close();
        }
       }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
}
public void exportCBOInXml(String parentFolderPath,String additionalQueryCriteria) throws Exception
{
    String fileName="OrganizationRecords";
    String parentFolder="OrganizationRecords";
    //appUtil.createExportImportDirectories();
    String fileSeperator=appUtil.getFileSeperator();
      try
      {
          int startSize=0;
        int count=0;
        OrganizationRecordsDao ordao=new OrganizationRecordsDaoImpl();
        List list=ordao.getOrganizationListForExport(additionalQueryCriteria);;
        double loopCount=Math.ceil((list.size()/5000d));
          for(int k=0; k<loopCount; k++)
        {
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");

	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	
	AttributesImpl atts = new AttributesImpl();
        
        
        if(list==null)
        list=new ArrayList();
        String[] columnNames={"orgCode","state","lga","orgName","orgType","orgSubtype","address","contactName1","contactPhone1","contactEmail1","contactName2","contactPhone2","contactEmail2","contactName3","contactPhone3","contactEmail3","services"};
        	
        atts.clear();
        
        
            hd.startDocument();
            hd.startElement("","",parentFolder,atts);
            startSize=count;
            System.err.println("startSize is "+startSize);
            for(int j=startSize; j<list.size(); j++)
            {
                if(j>((k+1)*4999))
                break;
                OrganizationRecords or=(OrganizationRecords)list.get(j);

              String orgCode=getPropertyValue(or.getOrgCode());
              String lga=getPropertyValue(or.getLga());
              String state=getPropertyValue(or.getState());
              String orgName=getPropertyValue(or.getOrgName());
              String orgType=getPropertyValue(or.getOrgType());
              String orgSubtype=getPropertyValue(or.getOrgSubtype());
              String address=getPropertyValue(or.getAddress());
              String contactName1=getPropertyValue(or.getContactName1());
              String contactPhone1=getPropertyValue(or.getContactPhone1());
              String contactEmail1=getPropertyValue(or.getContactEmail1());
              String contactName2=getPropertyValue(or.getContactName2());
              String contactPhone2=getPropertyValue(or.getContactPhone2());
              String contactEmail2=getPropertyValue(or.getContactEmail2());
              String contactName3=getPropertyValue(or.getContactName3());
              String contactPhone3=getPropertyValue(or.getContactPhone3());
              String contactEmail3=getPropertyValue(or.getContactEmail3());
              String services=getPropertyValue(or.getServices());
              
              String[] fieldValues={orgCode,state,lga,orgName,orgType,orgSubtype,address,contactName1,contactPhone1,contactEmail1,contactName2,contactPhone2,contactEmail2,contactName3,contactPhone3,contactEmail3,services};
              hd.startElement("","","patient",atts);
                for (int i=0;i<columnNames.length;i++)
                {
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
                }
              hd.endElement("","","patient");
              count++;
            }
        
            hd.endElement("","",parentFolder);
            hd.endDocument();
            String xmlString = sw.toString();
            fileName=parentFolder+".xml";
            if(k>0)
            fileName=parentFolder+k+".xml";

            String exportDestination=appUtil.getExportFilePath()+fileSeperator+parentFolder+fileSeperator+fileName;
            if(parentFolderPath !=null)
            exportDestination=parentFolderPath+fileName;
            File file = new File(exportDestination);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(xmlString);
            bw.flush();
            bw.close();
        }
       }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
}
public void exportReferralDirectoryInXml(String parentFolderPath,String additionalQueryCriteria) throws Exception
{
    String fileName="";
    String parentFolder="ReferralDirectory";
    //appUtil.createExportImportDirectories();
    String fileSeperator=appUtil.getFileSeperator();
      try
      {
          int startSize=0;
        int count=0;
        ReferralDirectoryDao rddao=new ReferralDirectoryDaoImpl();
        List list=rddao.getAllReferralDirectories();
        double loopCount=Math.ceil((list.size()/5000d));
          for(int k=0; k<loopCount; k++)
        {
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	AttributesImpl atts = new AttributesImpl();
                
        if(list==null)
        list=new ArrayList();
        String[] columnNames={"facilityId","facilityName","address","community","contactEmail","contactNumber","dateModified","latitude","longitude","nameOfContact","typeOfOrganization"};
        
        atts.clear();
               
            hd.startDocument();
            hd.startElement("","",parentFolder,atts);
            startSize=count;
            System.err.println("startSize is "+startSize);
            for(int j=startSize; j<list.size(); j++)
            {
                if(j>((k+1)*4999))
                break;
                ReferralDirectory rd=(ReferralDirectory)list.get(j);

              String facilityId=getPropertyValue(rd.getFacilityId());
              String address=getPropertyValue(rd.getAddress());
              String community=getPropertyValue(rd.getCommunity());
              String contactEmail=getPropertyValue(rd.getContactEmail());
              String contactNumber=getPropertyValue(rd.getContactNumber());
              String dateModified=rd.getDateModified();
              String facilityName=getPropertyValue(rd.getFacilityName());
              String latitude=getPropertyValue(rd.getLatitude());
              String longitude=getPropertyValue(rd.getLongitude());
              String nameOfContact=getPropertyValue(rd.getNameOfContact());
              String typeOfOrganization=getPropertyValue(rd.getTypeOfOrganization());
              
              String[] fieldValues={facilityId,facilityName,address,community,contactEmail,contactNumber,dateModified,latitude,longitude,nameOfContact,typeOfOrganization};
              hd.startElement("","","elementName",atts);
                    for (int i=0;i<columnNames.length;i++)
                    {
                        hd.startElement("","",columnNames[i],atts);
                        hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                        hd.endElement("","",columnNames[i]);
                    }
              hd.endElement("","","elementName");
              count++;
            }
        
            hd.endElement("","",parentFolder);
            hd.endDocument();
            String xmlString = sw.toString();
            fileName=parentFolder+".xml";
            if(k>0)
            fileName=parentFolder+k+".xml";
            String exportDestination=appUtil.getExportFilePath()+fileSeperator+parentFolder+fileSeperator+fileName;
            if(parentFolderPath !=null)//parentFolderPath
            exportDestination=parentFolderPath+fileName;
            File file = new File(exportDestination);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(xmlString);
            bw.flush();
            bw.close();
        }
       }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
}
public int exportCareplanAchievementInXml(String additionalQueryCriteria) throws Exception
{
    String fileName="";
    String parentFolder="CareplanAchievement";
    //appUtil.createExportImportDirectories();
    String fileSeperator=appUtil.getFileSeperator();
    int noOfRecords=0;
      try
      {
          int startSize=0;
        int count=0;
        CareplanAchievementDao cpadao=new CareplanAchievementDaoImpl();
        List list=cpadao.getCareplanAchievements(additionalQueryCriteria);
        if(list !=null)
        {
        double loopCount=Math.ceil((list.size()/5000d));
        for(int k=0; k<loopCount; k++)
        {
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");

	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	
	AttributesImpl atts = new AttributesImpl();
        
        
        if(list==null)
        list=new ArrayList();
        String[] columnNames={"id","clientId","dateOfAssessment","lastModifiedDate","hth_hivknowledge","hth_vchivrisk","hth_vcreftested","hth_hivOnArt","hth_hivdisclosed","hth_vcInneedOfHthServices","stb_hungryNoFood","stb_resiliency","stb_cgPartEconServ","stb_socEmotSupport","sft_vcsad","sft_vcreferredForCps","sft_vcSafeFromAbuse","sft_birthCert","sft_cgcompletedTwoModules","sft_childHeadedLinkedToServices","sch_schAttendance","sch_vcEnrolledInSecondarySch","sch_adolInVoctraining","sch_earlyChildCare","hth_hivknowledgeComment","hth_vchivriskComment","hth_vcreftestedComment","hth_hivOnArtComment","hth_hivdisclosedComment","hth_vcInneedOfHthServicesComment","stb_hungryNoFoodComment","stb_resiliencyComment","sft_vcsadComment","stb_cgPartEconServComment","stb_socEmotSupportComment","sft_vcreferredForCpsComment","sft_vcSafeFromAbuseComment","sft_birthCertComment","sft_cgcompletedTwoModulesComment","sft_childHeadedLinkedToServicesComment","sch_schAttendanceComment","sch_vcEnrolledInSecondarySchComment","sch_adolInVoctrainingComment","sch_earlyChildCareComment","graduated","score","volunteerId","designation","assessmentNo","recordedBy"};
	
        atts.clear();
        
        
            hd.startDocument();
            hd.startElement("","",parentFolder,atts);
            startSize=count;
            System.err.println("startSize is "+startSize);
            for(int j=startSize; j<list.size(); j++)
            {
                if(j>((k+1)*4999))
                break;
                CareplanAchievement cpa=(CareplanAchievement)list.get(j);

              String id=getIntegerPropertyValue(cpa.getId()+"");
              String clientId=getPropertyValue(cpa.getClientId());
              //String clientType=getPropertyValue(cpa.getClientType());
              String dateOfAssessment=getPropertyValue(cpa.getDateOfAssessment());
              String lastModifiedDate=getPropertyValue(cpa.getLastModifiedDate());
              
                
                String designation=getPropertyValue(cpa.getDesignation());
                String assessmentNo=getIntegerPropertyValue(cpa.getAssessmentNo()+"");

                String hth_hivknowledge=getPropertyValue(cpa.getHth_hivknowledge());
                String hth_vchivrisk=getPropertyValue(cpa.getHth_vchivrisk());
                String hth_vcreftested=getPropertyValue(cpa.getHth_vcreftested());
                String hth_hivOnArt=getPropertyValue(cpa.getHth_hivOnArt());
                String hth_hivdisclosed=getPropertyValue(cpa.getHth_hivdisclosed());
                String hth_vcInneedOfHthServices=getPropertyValue(cpa.getHth_vcInneedOfHthServices());
                String stb_hungryNoFood=getPropertyValue(cpa.getStb_hungryNoFood());
                String stb_resiliency=getPropertyValue(cpa.getStb_resiliency());
                
                String stb_cgPartEconServ=getPropertyValue(cpa.getStb_cgPartEconServ());
                String stb_socEmotSupport=getPropertyValue(cpa.getStb_socEmotSupport());
                String sft_vcsad=getPropertyValue(cpa.getSft_vcsad());
                String sft_vcreferredForCps=getPropertyValue(cpa.getSft_vcreferredForCps());
                String sft_vcSafeFromAbuse=getPropertyValue(cpa.getSft_vcSafeFromAbuse());
                String sft_birthCert=getPropertyValue(cpa.getSft_birthCert());
                String sft_cgcompletedTwoModules=getPropertyValue(cpa.getSft_cgcompletedTwoModules());
                String sft_childHeadedLinkedToServices=getPropertyValue(cpa.getSft_childHeadedLinkedToServices());
                String sch_schAttendance=getPropertyValue(cpa.getSch_schAttendance());
                String sch_vcEnrolledInSecondarySch=getPropertyValue(cpa.getSch_vcEnrolledInSecondarySch());
                String sch_adolInVoctraining=getPropertyValue(cpa.getSch_adolInVoctraining());
                String sch_earlyChildCare=getPropertyValue(cpa.getSch_earlyChildCare());

                String hth_hivknowledgeComment=getPropertyValue(cpa.getHth_hivknowledgeComment());
                String hth_vchivriskComment=getPropertyValue(cpa.getHth_vchivriskComment());
                String hth_vcreftestedComment=getPropertyValue(cpa.getHth_vcreftestedComment());
                String hth_hivOnArtComment=getPropertyValue(cpa.getHth_hivOnArtComment());
                String hth_hivdisclosedComment=getPropertyValue(cpa.getHth_hivdisclosedComment());
                String hth_vcInneedOfHthServicesComment=getPropertyValue(cpa.getHth_vcInneedOfHthServicesComment());
                String stb_hungryNoFoodComment=getPropertyValue(cpa.getStb_hungryNoFoodComment());
                String stb_resiliencyComment=getPropertyValue(cpa.getStb_resiliencyComment());
                String sft_vcsadComment=getPropertyValue(cpa.getSft_vcsadComment());
                String stb_cgPartEconServComment=getPropertyValue(cpa.getStb_cgPartEconServComment());
                String stb_socEmotSupportComment=getPropertyValue(cpa.getStb_socEmotSupportComment());
                String sft_vcreferredForCpsComment=getPropertyValue(cpa.getSft_vcreferredForCpsComment());
                String sft_vcSafeFromAbuseComment=getPropertyValue(cpa.getSft_vcSafeFromAbuseComment());
                String sft_birthCertComment=getPropertyValue(cpa.getSft_birthCertComment());
                String sft_cgcompletedTwoModulesComment=getPropertyValue(cpa.getSft_cgcompletedTwoModulesComment());
                String sft_childHeadedLinkedToServicesComment=(cpa.getSft_childHeadedLinkedToServicesComment());
                String sch_schAttendanceComment=getPropertyValue(cpa.getSch_schAttendanceComment());
                String sch_vcEnrolledInSecondarySchComment=getPropertyValue(cpa.getSch_vcEnrolledInSecondarySchComment());
                String sch_adolInVoctrainingComment=getPropertyValue(cpa.getSch_adolInVoctrainingComment());
                String sch_earlyChildCareComment=getPropertyValue(cpa.getSch_earlyChildCareComment());
                String graduated=getPropertyValue(cpa.getGraduated());
                String score=getIntegerPropertyValue(cpa.getScore()+"");
                String volunteerId=getPropertyValue(cpa.getVolunteerId());
                String recordedBy=getPropertyValue(cpa.getRecordedBy());
              
              String[] fieldValues={id,clientId,dateOfAssessment,lastModifiedDate,hth_hivknowledge,hth_vchivrisk,hth_vcreftested,hth_hivOnArt,hth_hivdisclosed,hth_vcInneedOfHthServices,stb_hungryNoFood,stb_resiliency,stb_cgPartEconServ,stb_socEmotSupport,sft_vcsad,sft_vcreferredForCps,sft_vcSafeFromAbuse,sft_birthCert,sft_cgcompletedTwoModules,sft_childHeadedLinkedToServices,sch_schAttendance,sch_vcEnrolledInSecondarySch,sch_adolInVoctraining,sch_earlyChildCare,hth_hivknowledgeComment,hth_vchivriskComment,hth_vcreftestedComment,hth_hivOnArtComment,hth_hivdisclosedComment,hth_vcInneedOfHthServicesComment,stb_hungryNoFoodComment,stb_resiliencyComment,sft_vcsadComment,stb_cgPartEconServComment,stb_socEmotSupportComment,sft_vcreferredForCpsComment,sft_vcSafeFromAbuseComment,sft_birthCertComment,sft_cgcompletedTwoModulesComment,sft_childHeadedLinkedToServicesComment,sch_schAttendanceComment,sch_vcEnrolledInSecondarySchComment,sch_adolInVoctrainingComment,sch_earlyChildCareComment,graduated,score,volunteerId,designation,assessmentNo,recordedBy};
              hd.startElement("","","patient",atts);
                    for (int i=0;i<columnNames.length;i++)
                    {
                        System.err.println(columnNames[i]+": "+fieldValues[i]);
                        hd.startElement("","",columnNames[i],atts);
                        if(fieldValues[i]==null)
                        fieldValues[i]="none";
                        hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                        hd.endElement("","",columnNames[i]);
                    }
              hd.endElement("","","patient");
              count++;
            }
        
            hd.endElement("","",parentFolder);
            hd.endDocument();
            String xmlString = sw.toString();
            fileName=parentFolder+".xml";
            if(k>0)
            fileName=parentFolder+k+".xml";

            File file = new File(appUtil.getExportFilePath()+fileSeperator+parentFolder+fileSeperator+fileName);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(xmlString);
            bw.flush();
            bw.close();
        }
       }
      }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
      return noOfRecords;
}
public void exportGraduationCheckListInXml(String additionalQueryCriteria) throws Exception
{
    String fileName="";
    String parentFolder="GraduationCheckList";
    //appUtil.createExportImportDirectories();
    String fileSeperator=appUtil.getFileSeperator();
      try
      {
          int startSize=0;
        int count=0;
        GraduationCheckListDao gcldao=new GraduationCheckListDaoImpl();
        List list=gcldao.getGraduationCheckLists(additionalQueryCriteria);
        double loopCount=Math.ceil((list.size()/5000d));
          for(int k=0; k<loopCount; k++)
        {
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");

	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	
	AttributesImpl atts = new AttributesImpl();
        
        
        if(list==null)
        list=new ArrayList();
        String[] columnNames={"id","clientId","clientType","dateOfAssessment","dateModified","health","safety","school","stability","vulnerability","graduated","gclscore","volunteerId","recordedby"};
	
        atts.clear();
        
        
            hd.startDocument();
            hd.startElement("","",parentFolder,atts);
            startSize=count;
            System.err.println("startSize is "+startSize);
            for(int j=startSize; j<list.size(); j++)
            {
                if(j>((k+1)*4999))
                break;
                GraduationCheckList gcl=(GraduationCheckList)list.get(j);

              String id=getIntegerPropertyValue(gcl.getId()+"");
              String clientId=getPropertyValue(gcl.getClientId());
              String clientType=getPropertyValue(gcl.getClientType());
              String dateOfAssessment=gcl.getDateOfAssessment();
              String dateModified=gcl.getDateModified();
              String health=getPropertyValue(gcl.getHealth());
              String safety=getPropertyValue(gcl.getSafety());
              String school=getPropertyValue(gcl.getSchool());
              String stability=getPropertyValue(gcl.getStability());
              String vulnerability=getPropertyValue(gcl.getVulnerability());
              String graduated=getPropertyValue(gcl.getGraduated());
              String gclscore=getIntegerPropertyValue(gcl.getGclscore()+"");
              String volunteerId=getPropertyValue(gcl.getVolunteerId());
              String recordedby=getPropertyValue(gcl.getRecordedby());
              
              String[] fieldValues={id,clientId,clientType,dateOfAssessment,dateModified,health,safety,school,stability,vulnerability,graduated,gclscore,volunteerId,recordedby};
              hd.startElement("","","patient",atts);
                    for (int i=0;i<columnNames.length;i++)
                    {
                        hd.startElement("","",columnNames[i],atts);
                        hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                        hd.endElement("","",columnNames[i]);
                    }
              hd.endElement("","","patient");
              count++;
            }
        
            hd.endElement("","",parentFolder);
            hd.endDocument();
            String xmlString = sw.toString();
            fileName=parentFolder+".xml";
            if(k>0)
            fileName=parentFolder+k+".xml";

            File file = new File(appUtil.getExportFilePath()+fileSeperator+parentFolder+fileSeperator+fileName);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(xmlString);
            bw.flush();
            bw.close();
        }
       }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
}
public void exportDeletedRecordsInXml(String startDate,String endDate) throws Exception
{
    String fileName="";
    String parentFolder="DeletedRecord";
    String fileSeperator=appUtil.getFileSeperator();
      try
      {
          int startSize=0;
        int count=0;
        DeletedRecordDao drdao=new DeletedRecordDaoImpl();
        List list=drdao.getDeletedRecordByDateDeleted(startDate, endDate);
        double loopCount=Math.ceil((list.size()/5000d));
        System.err.println("list.size() in exportDeletedRecordsInXml "+list.size()+" and loopCount is "+loopCount);
        for(int k=0; k<loopCount; k++)
        {
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");

	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	
	AttributesImpl atts = new AttributesImpl();
        
        
        if(list==null)
        list=new ArrayList();
        String[] columnNames={"id","recordId","typeOfRecord","dateRecordCreated","dateRecordDeleted"};
	
        atts.clear();
        
        
            hd.startDocument();
            hd.startElement("","",parentFolder,atts);
            startSize=count;
            System.err.println("startSize is "+startSize);
            for(int j=startSize; j<list.size(); j++)
            {
                if(j>((k+1)*4999))
                break;
                DeletedRecord dr=(DeletedRecord)list.get(j);

              String id=getIntegerPropertyValue(dr.getId()+"");
              String recordId=getPropertyValue(dr.getRecordId());
              String typeOfRecord=getPropertyValue(dr.getTypeOfRecord());
              String dateRecordCreated=getPropertyValue(dr.getDateRecordCreated());
              String dateRecordDeleted=getPropertyValue(dr.getDateRecordDeleted());

              String[] fieldValues={id,recordId,typeOfRecord,dateRecordCreated,dateRecordDeleted};
              hd.startElement("","","patient",atts);
              for (int i=0;i<columnNames.length;i++)
              {
                  hd.startElement("","",columnNames[i],atts);
                  hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                  hd.endElement("","",columnNames[i]);
              }
              hd.endElement("","","patient");
              count++;
            }
        
            hd.endElement("","",parentFolder);
            hd.endDocument();
            String xmlString = sw.toString();
            fileName=parentFolder+".xml";
            if(k>0)
            fileName=parentFolder+k+".xml";

            File file = new File(appUtil.getExportFilePath()+fileSeperator+parentFolder+fileSeperator+fileName);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(xmlString);
            bw.flush();
            bw.close();
        }
       }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
}
public void getEnrollmentInXml(int count,int startSize,List list) throws Exception
{
     String fileName="";
      try
      {
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	// SAX2.0 ContentHandler.
	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
	//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"Enrollment.dtd");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        String[] columnNames={"ovcId","caregiverId","hhUniqueId","dateEnrollment","lastName","firstName","age","ageUnit","address","phone","hivStatus","eligibility","gender","birthCertificate","schoolStatus","schoolType","schoolName","currentClass","weight","height","caregiverRelationship","completedByName","completedByDesignation","verifiedBy","dateOfEntry","recordedBy","partner","sourceOfInfo"};
	List ovcList=null;
        List ovcMainList=null;
        hd.startElement("","","Enrollment",atts);
        atts.clear();

	for(int j=startSize; j<list.size(); j++)
	{
            if(j>(startSize+1999))
            break;
            ovcList=new ArrayList();
            Ovc ovc=(Ovc)list.get(j);

          String ovcId=ovc.getOvcId();
          if(ovcId==null || ovcId.equals(" ") || ovcId.equals(""))
          ovcId="none";
          ovcId=ovcId.trim();
          String caregiverId=getPropertyValue(ovc.getCaregiverId());
          String hhUniqueId=getPropertyValue(ovc.getHhUniqueId());

          String dateEnrollment=ovc.getDateEnrollment();
          if(dateEnrollment==null || dateEnrollment.equals(" ") || dateEnrollment.equals(""))
          dateEnrollment="none";
          String firstName=ovc.getFirstName();
          if(firstName==null || firstName.equals(" ") || firstName.equals(""))
          firstName="none";
          //firstName=encryptor.encryptData1(firstName);
          String lastName=ovc.getLastName();
          if(lastName==null || lastName.equals(" ") || lastName.equals(""))
          lastName="none";
          //lastName=encryptor.encryptData1(lastName);
          String age=new Integer(ovc.getAge()).toString();
          if(age==null || age.equals(" ") || age.equals(""))
          age="0";
          String ageUnit=ovc.getAgeUnit();
          if(ageUnit==null || ageUnit.equals(" ") || ageUnit.equals(""))
          ageUnit="none";
          String address=ovc.getAddress();
          if(address==null || address.equals(" ") || address.equals(""))
          address="none";
          //address=encryptor.encryptData1(address);
          String phone=ovc.getPhone();
          if(phone==null || phone.equals(" ") || phone.equals(""))
          phone="none";
          //phone=encryptor.encryptData1(phone);
          String hivStatus=ovc.getHivStatus();
          if(hivStatus==null || hivStatus.equals(" ") || hivStatus.equals(""))
          hivStatus="none";
          String eligibility=ovc.getEligibility();
          if(eligibility==null || eligibility.equals(" ") || eligibility.equals(""))
          eligibility="none";
          String gender=ovc.getGender();
          if(gender==null || gender.equals(" ") || gender.equals(""))
          gender="none";
          String birthCertificate=ovc.getBirthCertificate();
          if(birthCertificate==null || birthCertificate.equals(" ") || birthCertificate.equals(""))
          birthCertificate="none";
          String schoolStatus=ovc.getSchoolStatus();
          if(schoolStatus==null || schoolStatus.equals(" ") || schoolStatus.equals(""))
          schoolStatus="none";
          String schoolType=ovc.getSchoolType();
          if(schoolType==null || schoolType.equals(" ") || schoolType.equals(""))
          schoolType="none";
          String schoolName=ovc.getSchoolName();
          if(schoolName==null || schoolName.equals(" ") || schoolName.equals(""))
          schoolName="none";
          String currentClass=ovc.getCurrentClass();
          if(currentClass==null || currentClass.equals(" ") || currentClass.equals(""))
          currentClass="none";
          String weight=new Double(ovc.getWeight()).toString();
          if(weight==null || weight.equals(" ") || weight.equals(""))
          weight="0.0";
          String height=new Double(ovc.getHeight()).toString();
          if(height==null || height.equals(" ") || height.equals(""))
          height="none";
          String caregiverRelationship=ovc.getCaregiverRelationships();
          if(caregiverRelationship==null || caregiverRelationship.equals(" ") || caregiverRelationship.equals(""))
          caregiverRelationship="none";
          String completedByName=ovc.getCompletedbyName();
          if(completedByName==null || completedByName.equals(" ") || completedByName.equals(""))
          completedByName="none";
          String completedByDesignation=ovc.getCompletedbyDesignation();
          if(completedByDesignation==null || completedByDesignation.equals(" ") || completedByDesignation.equals(""))
          completedByDesignation="none";
          String verifiedBy=ovc.getVerifiedBy();
          if(verifiedBy==null || verifiedBy.equals(" ") || verifiedBy.equals(""))
          verifiedBy="none";

          String dateOfEntry=ovc.getDateOfEntry();
          if(dateOfEntry==null || dateOfEntry.equals(" ") || dateOfEntry.equals(""))
          dateOfEntry="none";

          String recordedBy=ovc.getRecordedBy();
          if(recordedBy==null || recordedBy.equals(" ") || recordedBy.equals(""))
          recordedBy="none";

          String partner=ovc.getPartner();
          if(partner==null || partner.equals(" ") || partner.equals("") || partner.trim().length()<1)
          partner="none";
          String sourceOfInfo=ovc.getSourceOfInfo();
          if(sourceOfInfo==null || sourceOfInfo.equals(" ") || sourceOfInfo.equals("") || sourceOfInfo.trim().length()<1)
          sourceOfInfo="none";

          ovcList.add(ovcId);
          ovcList.add(lastName);
          ovcList.add(firstName);
          ovcList.add(address);
          ovcList.add(phone);

          ovcMainList=new ArrayList();
          ovcMainList.add(ovcList);

          String[] fieldValues={ovcId,caregiverId,hhUniqueId,dateEnrollment,lastName,firstName,age,ageUnit,address,phone,hivStatus,eligibility,gender,birthCertificate,schoolStatus,schoolType,schoolName,currentClass,weight,height,caregiverRelationship,completedByName,completedByDesignation,verifiedBy,dateOfEntry,recordedBy,partner,sourceOfInfo};
          hd.startElement("","","patient",atts);
		for (int i=0;i<columnNames.length;i++)
		{
                    if(fieldValues[i]==null)
                    {
                        fieldValues[i]="0";
                    }
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
		}
          hd.endElement("","","patient");
	}

	hd.endElement("","","Enrollment");
	hd.endDocument();
	String xmlString = sw.toString();
        fileName="Enrollment.xml";
        if(count>0)
        fileName="Enrollment"+count+".xml";
        File file = new File(appUtil.getExportFilePath()+fileSeperator+"Enrollment"+fileSeperator+fileName);

        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
}
public void exportHouseholdEnrollmentInXml(int count,int startSize,List list) throws Exception
{
    String fileName="";

      try
      {
        HouseholdEnrollment hhe=null;
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
        String exportFileName="HouseholdEnrollment";

	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        String[] columnNames={"hhUniqueId","hhSurname","hhFirstname","address","phone","noOfChildrenInhh","noOfPeopleInhh","dateOfAssessment","hhGender","maritalStatus","hhAge","occupation","eligibleForEnrollment","stateCode","lgaCode","orgCode","communityCode","partnerCode","recordedBy","dateOfEntry","latitude","longitude","hhHeadship","health","educationLevel","shelterAndHousing","foodSecurityAndNutrition","meansOfLivelihood","hhIncome","hhvaId"};
	hd.startElement("","",exportFileName,atts);
        atts.clear();
        System.err.println("list.size() in exportHouseholdEnrollmentInXml is "+list.size());
	for(int j=startSize; j<list.size(); j++)
	{
            if(j>(startSize+1999))
            break;
             hhe=(HouseholdEnrollment)list.get(j);

          String hhUniqueId=getPropertyValue(hhe.getHhUniqueId());
          String hhSurname=getPropertyValue(hhe.getHhSurname());
          String hhFirstname=getPropertyValue(hhe.getHhFirstname());
          String address=getPropertyValue(hhe.getAddress());
          String phone=getPropertyValue(hhe.getPhone());
          String noOfChildrenInhh=getIntegerPropertyValue(new Integer(hhe.getNoOfChildrenInhh()).toString());
          String noOfPeopleInhh=getIntegerPropertyValue(new Integer(hhe.getNoOfPeopleInhh()).toString());
          String dateOfAssessment=getPropertyValue(hhe.getDateOfAssessment());
          String hhGender=getPropertyValue(hhe.getHhGender());
          String maritalStatus=getPropertyValue(hhe.getMaritalStatus());
          String hhAge=getIntegerPropertyValue(new Integer(hhe.getHhAge()).toString());
          String occupation=getPropertyValue(hhe.getOccupation());
          String eligibleForEnrollment=getPropertyValue(hhe.getEligibleForEnrollment());
          String stateCode=getPropertyValue(hhe.getStateCode());
          String lgaCode=getPropertyValue(hhe.getLgaCode());
          String orgCode=getPropertyValue(hhe.getOrgCode());
          String communityCode=getPropertyValue(hhe.getCommunityCode());
          String partnerCode=getPropertyValue(hhe.getPartnerCode());
          String recordedBy=getPropertyValue(hhe.getRecordedBy());
          String dateOfEntry=getPropertyValue(hhe.getDateOfEntry());
          String latitude=getPropertyValue((new Double(hhe.getLatitude())).toString());
          String longitude=getPropertyValue((new Double(hhe.getLongitude())).toString());
          
          String hhHeadship=getIntegerPropertyValue(new Integer(hhe.getHhHeadship()).toString());
          String health=getIntegerPropertyValue(new Integer(hhe.getHealth()).toString());
          String educationLevel=getIntegerPropertyValue(new Integer(hhe.getEducationLevel()).toString());
          String shelterAndHousing=getIntegerPropertyValue(new Integer(hhe.getShelterAndHousing()).toString());
          String foodSecurityAndNutrition=getIntegerPropertyValue(new Integer(hhe.getFoodSecurityAndNutrition()).toString());
          String meansOfLivelihood=getIntegerPropertyValue(new Integer(hhe.getMeansOfLivelihood()).toString());
          String hhIncome=getIntegerPropertyValue(new Integer(hhe.getHhIncome()).toString());
          String hhvaId=getIntegerPropertyValue(new Integer(hhe.getHhvaId()).toString());

          String[] fieldValues={hhUniqueId,hhSurname,hhFirstname,address,phone,noOfChildrenInhh,noOfPeopleInhh,dateOfAssessment,hhGender,maritalStatus,hhAge,occupation,eligibleForEnrollment,stateCode,lgaCode,orgCode,communityCode,partnerCode,recordedBy,dateOfEntry,latitude,longitude,hhHeadship,health,educationLevel,shelterAndHousing,foodSecurityAndNutrition,meansOfLivelihood,hhIncome,hhvaId};
          hd.startElement("","","patient",atts);
		for (int i=0;i<columnNames.length;i++)
		{
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
		}
          hd.endElement("","","patient");
	}
	hd.endElement("","",exportFileName);
	hd.endDocument();
	String xmlString = sw.toString();
        fileName=exportFileName+".xml";
        if(count>0)
        fileName=exportFileName+count+".xml";

        File file = new File(appUtil.getExportFilePath()+"/"+exportFileName+"/"+fileName);

        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
}
public void exportHouseholdFollowupAssessmentInXml(int count,int startSize,List list) throws Exception
{
    String fileName="";
      try
      {
        HouseholdFollowupAssessment hhfa=null;
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
        String exportFileName="HouseholdFollowupAssessment";

	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        String[] columnNames={"recordId","hhUniqueId","updatedAddress","phone","noOfChildrenInhh","noOfPeopleInhh","dateOfAssessment","occupation","reasonWithdrawal","nameOfAssessor","designation","recordedBy","dateModified","hhHeadship","health","educationLevel","shelterAndHousing","foodSecurityAndNutrition","meansOfLivelihood","hhIncome","hhvaId"};
	hd.startElement("","",exportFileName,atts);
        atts.clear();
        System.err.println("list.size() in exportHouseholdFollowupAssessmentInXml is "+list.size());
	for(int j=startSize; j<list.size(); j++)
	{
            if(j>(startSize+1999))
            break;
             hhfa=(HouseholdFollowupAssessment)list.get(j);
          String recordId=getPropertyValue(hhfa.getRecordId());
          String hhUniqueId=getPropertyValue(hhfa.getHhUniqueId());
          String updatedAddress=getPropertyValue(hhfa.getUpdatedAddress());
          String phone=getPropertyValue(hhfa.getPhone());
          String noOfChildrenInhh=getIntegerPropertyValue(new Integer(hhfa.getNoOfChildrenInhh()).toString());
          String noOfPeopleInhh=getIntegerPropertyValue(new Integer(hhfa.getNoOfPeopleInhh()).toString());
          String dateOfAssessment=getPropertyValue(hhfa.getDateOfAssessment());
          String occupation=getPropertyValue(hhfa.getOccupation());
          String reasonWithdrawal=getPropertyValue(hhfa.getReasonWithdrawal());
          String nameOfAssessor=getPropertyValue(hhfa.getNameOfAssessor());
          String designation=getPropertyValue(hhfa.getDesignation());
          String recordedBy=getPropertyValue(hhfa.getRecordedBy());
          String dateModified=getPropertyValue(hhfa.getDateModified());
          
          String hhHeadship=getIntegerPropertyValue(new Integer(hhfa.getHhHeadship()).toString());
          String health=getIntegerPropertyValue(new Integer(hhfa.getHealth()).toString());
          String educationLevel=getIntegerPropertyValue(new Integer(hhfa.getEducationLevel()).toString());
          String shelterAndHousing=getIntegerPropertyValue(new Integer(hhfa.getShelterAndHousing()).toString());
          String foodSecurityAndNutrition=getIntegerPropertyValue(new Integer(hhfa.getFoodSecurityAndNutrition()).toString());
          String meansOfLivelihood=getIntegerPropertyValue(new Integer(hhfa.getMeansOfLivelihood()).toString());
          String hhIncome=getIntegerPropertyValue(new Integer(hhfa.getHhIncome()).toString());
          String hhvaId=getIntegerPropertyValue(new Integer(hhfa.getHhvaId()).toString());
          
          String[] fieldValues={recordId,hhUniqueId,updatedAddress,phone,noOfChildrenInhh,noOfPeopleInhh,dateOfAssessment,occupation,reasonWithdrawal,nameOfAssessor,designation,recordedBy,dateModified,hhHeadship,health,educationLevel,shelterAndHousing,foodSecurityAndNutrition,meansOfLivelihood,hhIncome,hhvaId};
          hd.startElement("","","patient",atts);
		for (int i=0;i<columnNames.length;i++)
		{
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
		}
          hd.endElement("","","patient");
	}
	hd.endElement("","",exportFileName);
	hd.endDocument();
	String xmlString = sw.toString();
        fileName=exportFileName+".xml";
        if(count>0)
        fileName=exportFileName+count+".xml";

        File file = new File(appUtil.getExportFilePath()+fileSeperator+exportFileName+fileSeperator+fileName);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
       catch(Exception ex)
       {
            ex.printStackTrace();
       }
}
public void exportHouseholdVulnerabilityAssessmentInXml(int count,int startSize,List list) throws Exception
{
    String fileName="";

      try
      {
          HouseholdVulnerabilityAssessment hva=null;
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
        String exportFileName="HouseholdVulnerabilityAssessment";

	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        String[] columnNames={"hhUniqueId","dateOfAssessment","hhHeadship","health","educationLevel","shelterAndHousing","foodSecurityAndNutrition","meansOfLivelihood","hhIncome","nameOfAssessor","designation","recordedBy","dateOfEntry","assessmentNo","vulnerabilityScore"};
	hd.startElement("","",exportFileName,atts);
        atts.clear();

	for(int j=startSize; j<list.size(); j++)
	{
            if(j>(startSize+1999))
            break;
             hva=(HouseholdVulnerabilityAssessment)list.get(j);

          String hhUniqueId=getPropertyValue(hva.getHhUniqueId());
          String dateOfAssessment=getPropertyValue(hva.getDateOfAssessment());
          String hhHeadship=getIntegerPropertyValue(new Integer(hva.getHhHeadship()).toString());
          String health=getIntegerPropertyValue(new Integer(hva.getHealth()).toString());

          String educationLevel=getIntegerPropertyValue(new Integer(hva.getEducationLevel()).toString());
          String shelterAndHousing=getIntegerPropertyValue(new Integer(hva.getShelterAndHousing()).toString());
          String foodSecurityAndNutrition=getIntegerPropertyValue(new Integer(hva.getFoodSecurityAndNutrition()).toString());
          String meansOfLivelihood=getIntegerPropertyValue(new Integer(hva.getMeansOfLivelihood()).toString());
          String hhIncome=getIntegerPropertyValue(new Integer(hva.getHhIncome()).toString());
          String assessmentNo=getIntegerPropertyValue(new Integer(hva.getAssessmentNo()).toString());

          String nameOfAssessor=getPropertyValue(hva.getNameOfAssessor());
          String designation=getPropertyValue(hva.getDesignation());
          String recordedBy=getPropertyValue(hva.getRecordedBy());
          String dateOfEntry=getPropertyValue(hva.getDateOfEntry());
          String vulnerabilityScore=getPropertyValue(hva.getVulnerabilityScore()+"");

          String[] fieldValues={hhUniqueId,dateOfAssessment,hhHeadship,health,educationLevel,shelterAndHousing,foodSecurityAndNutrition,meansOfLivelihood,hhIncome,nameOfAssessor,designation,recordedBy,dateOfEntry,assessmentNo,vulnerabilityScore};
          hd.startElement("","","patient",atts);
		for (int i=0;i<columnNames.length;i++)
		{
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
		}
          hd.endElement("","","patient");
	}
	hd.endElement("","",exportFileName);
	hd.endDocument();
	String xmlString = sw.toString();
        fileName=exportFileName+".xml";
        if(count>0)
        fileName=exportFileName+count+".xml";

        File file = new File(appUtil.getExportFilePath()+"/"+exportFileName+"/"+fileName);

        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
}
public void exportCaregiverInXml(int count,int startSize,List list) throws Exception
{
    String fileName="";

      try
      {
          Caregiver cgiver=null;
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
        String exportFileName="Caregiver";

	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        String[] columnNames={"hhUniqueId","caregiverId","caregiverLastName","caregiverFirstname","caregiverAddress","caregiverPhone","caregiverGender","caregiverAge","caregiverOccupation","caregiverMaritalStatus","dateOfEnrollment","dateModified","cgiverHivStatus"};
	hd.startElement("","",exportFileName,atts);
        atts.clear();

	for(int j=startSize; j<list.size(); j++)
	{
            if(j>(startSize+1999))
            break;
             cgiver=(Caregiver)list.get(j);

          String hhUniqueId=getPropertyValue(cgiver.getHhUniqueId());
          String caregiverId=getPropertyValue(cgiver.getCaregiverId());
          String caregiverLastName=getPropertyValue(cgiver.getCaregiverLastName());
          String caregiverFirstname=getPropertyValue(cgiver.getCaregiverFirstname());
          String caregiverAddress=getPropertyValue(cgiver.getCaregiverAddress());
          String caregiverPhone=getPropertyValue(cgiver.getCaregiverPhone());
          String caregiverGender=getPropertyValue(cgiver.getCaregiverGender());
          String caregiverAge=getPropertyValue(new Integer(cgiver.getCaregiverAge()).toString());
          String caregiverOccupation=getPropertyValue(cgiver.getCaregiverOccupation());
          String caregiverMaritalStatus=getPropertyValue(cgiver.getCaregiverMaritalStatus());
          String dateOfEnrollment=getPropertyValue(cgiver.getDateOfEnrollment());
          String dateModified=getPropertyValue(cgiver.getDateModified());
          String cgiverHivStatus=getPropertyValue(cgiver.getCgiverHivStatus());
          String[] fieldValues={hhUniqueId,caregiverId,caregiverLastName,caregiverFirstname,caregiverAddress,caregiverPhone,caregiverGender,caregiverAge,caregiverOccupation,caregiverMaritalStatus,dateOfEnrollment,dateModified,cgiverHivStatus};
          hd.startElement("","","patient",atts);
		for (int i=0;i<columnNames.length;i++)
		{
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
		}
          hd.endElement("","","patient");
	}
	hd.endElement("","",exportFileName);
	hd.endDocument();
	String xmlString = sw.toString();
        fileName=exportFileName+".xml";
        if(count>0)
        fileName=exportFileName+count+".xml";

        File file = new File(appUtil.getExportFilePath()+"/"+exportFileName+"/"+fileName);

        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
}
public void exportHouseholdServiceInXml(int count,int startSize,List list) throws Exception
{
    String fileName="";

      try
      {
          HouseholdService hhs=null;
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");

	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        String[] columnNames={"id","hhUniqueId","caregiverId","serviceDate","economicStrengtheningServices","educationalServices","healthServices","nutritionalServices","protectionServices","psychosocialSupportServices","shelterAndCareServices","volunteerDesignation","volunteerName","recordedBy","dateOfEntry","serviceNo","numberOfServices","serviceRecipientType"};
	hd.startElement("","","HouseholdService",atts);
        atts.clear();

	for(int j=startSize; j<list.size(); j++)
	{
            if(j>(startSize+1999))
            break;
             hhs=(HouseholdService)list.get(j);
            String id=getIntegerPropertyValue(new Integer(hhs.getId()).toString());
          String hhUniqueId=getPropertyValue(hhs.getHhUniqueId());
          String caregiverId=getPropertyValue(hhs.getCaregiverId());
          String serviceDate=getPropertyValue(hhs.getServiceDate());
          String economicStrengtheningServices=getPropertyValue(hhs.getEconomicStrengtheningServices());
          String educationalServices=getPropertyValue(hhs.getEducationalServices());
          String healthServices=getPropertyValue(hhs.getHealthServices());
          String nutritionalServices=getPropertyValue(hhs.getNutritionalServices());
          String protectionServices=getPropertyValue(hhs.getProtectionServices());
          String psychosocialSupportServices=getPropertyValue(hhs.getPsychosocialSupportServices());
          String shelterAndCareServices=getPropertyValue(hhs.getShelterAndCareServices());
          String volunteerDesignation=getPropertyValue(hhs.getVolunteerDesignation());
          String volunteerName=getPropertyValue(hhs.getVolunteerName());
          String recordedBy=getPropertyValue(hhs.getRecordedBy());
          String serviceNo=getIntegerPropertyValue(new Integer(hhs.getServiceNo()).toString());
          String numberOfServices=getIntegerPropertyValue(new Integer(hhs.getNumberOfServices()).toString());
          String dateOfEntry=getPropertyValue(hhs.getDateOfEntry());
          String serviceRecipientType=getPropertyValue(hhs.getServiceRecipientType());

          String[] fieldValues={id,hhUniqueId,caregiverId,serviceDate,economicStrengtheningServices,educationalServices,healthServices,nutritionalServices,protectionServices,psychosocialSupportServices,shelterAndCareServices,volunteerDesignation,volunteerName,recordedBy,dateOfEntry,serviceNo,numberOfServices,serviceRecipientType};
          hd.startElement("","","patient",atts);
		for (int i=0;i<columnNames.length;i++)
		{
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
		}
          hd.endElement("","","patient");
	}
	hd.endElement("","","HouseholdService");
	hd.endDocument();
	String xmlString = sw.toString();
        fileName="HouseholdService.xml";
        if(count>0)
        fileName="HouseholdService"+count+".xml";

        File file = new File(appUtil.getExportFilePath()+"/HouseholdService/"+fileName);

        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
}
public void exportReferralServicesInXml(int count,int startSize,List list) throws Exception
{
    String fileName="";

      try
      {
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");

	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        String[] columnNames={"recordId","ovcId","referringOrganization","dateOfReferral","psychoServices","nutritionalServices","healthServices","educationalServices","protectionServices","shelterServices","economicServices","psychosocialOther","nutritionOther","healthOther","educationOther","protectionOther","shelterOther","economicOther","nameOfOrganizationChildIsreferred","referralCompleted","nameOfPersonReferring","designationOfReferrer","referrerPhoneNo","referrerEmail","type"};
	hd.startElement("","","OvcReferral",atts);
        atts.clear();

	for(int j=startSize; j<list.size(); j++)
	{
            if(j>(startSize+4999))
            break;
            OvcReferral referralService=(OvcReferral)list.get(j);

          String recordId=new Integer(referralService.getRecordId()).toString();
          if(recordId==null || recordId.equals(" ") || recordId.equals(""))
          recordId="0";

          String ovcId=referralService.getOvcId();
          if(ovcId==null || ovcId.equals(" ") || ovcId.equals(""))
          ovcId="none";
          ovcId=ovcId.trim();
          String dateOfReferral=referralService.getDateOfReferral();
          if(dateOfReferral==null || dateOfReferral.equals(" ") || dateOfReferral.equals(""))
          dateOfReferral="none";
          String referringOrganization=referralService.getReferringOrganization();
          if(referringOrganization==null || referringOrganization.equals(" ") || referringOrganization.equals(""))
          referringOrganization="none";
          String psychoServices=referralService.getPsychoServices();
          if(psychoServices==null || psychoServices.equals(" ") || psychoServices.equals(""))
          psychoServices="none";
          String nutritionalServices=referralService.getNutritionalServices();
          if(nutritionalServices==null || nutritionalServices.equals(" ") || nutritionalServices.equals(""))
          nutritionalServices="none";
          String healthServices=referralService.getHealthServices();
          if(healthServices==null || healthServices.equals(" ") || healthServices.equals(""))
          healthServices="none";
          String educationalServices=referralService.getEducationalServices();
          if(educationalServices==null || educationalServices.equals(" ") || educationalServices.equals(""))
          educationalServices="none";
          String protectionServices=referralService.getProtectionServices();
          if(protectionServices==null || protectionServices.equals(" ") || protectionServices.equals(""))
          protectionServices="none";

          String shelterServices=referralService.getShelterServices();
          if(shelterServices==null || shelterServices.equals(" ") || shelterServices.equals(""))
          shelterServices="none";
          String economicServices=referralService.getEconomicServices();
          if(economicServices==null || economicServices.equals(" ") || economicServices.equals(""))
          economicServices="none";
          String psychosocialOther=referralService.getPsychosocialOther();
          if(psychosocialOther==null || psychosocialOther.equals(" ") || psychosocialOther.equals(""))
          psychosocialOther="none";
          String healthOther=referralService.getHealthOther();
          if(healthOther==null || healthOther.equals(" ") || healthOther.equals(""))
          healthOther="none";

          String nutritionOther=referralService.getNutritionOther();
          if(nutritionOther==null || nutritionOther.equals(" ") || nutritionOther.equals(""))
          nutritionOther="none";

          String educationOther=referralService.getEducationOther();
          if(educationOther==null || educationOther.equals(" ") || educationOther.equals(""))
          educationOther="none";

          String protectionOther=referralService.getProtectionOther();
          if(protectionOther==null || protectionOther.equals(" ") || protectionOther.equals(""))
          protectionOther="none";
          String shelterOther=referralService.getShelterOther();
          if(shelterOther==null || shelterOther.equals(" ") || shelterOther.equals(""))
          shelterOther="none";

          String economicOther=referralService.getEconomicOther();
          if(economicOther==null || economicOther.equals(" ") || economicOther.equals(""))
          economicOther="none";

          String nameOfOrganizationChildIsreferred=referralService.getNameOfOrganizationChildIsreferred();
          if(nameOfOrganizationChildIsreferred==null || nameOfOrganizationChildIsreferred.equals(" ") || nameOfOrganizationChildIsreferred.equals(""))
          nameOfOrganizationChildIsreferred="none";

          String referralCompleted=referralService.getReferralCompleted();
          if(referralCompleted==null || referralCompleted.equals(" ") || referralCompleted.equals(""))
          referralCompleted="none";

          String nameOfPersonReferring=referralService.getNameOfPersonReferring();
          if(nameOfPersonReferring==null || nameOfPersonReferring.equals(" ") || nameOfPersonReferring.equals(""))
          nameOfPersonReferring="none";

          String designationOfReferrer=referralService.getDesignationOfReferrer();
          if(designationOfReferrer==null || designationOfReferrer.equals(" ") || designationOfReferrer.equals(""))
          designationOfReferrer="none";

          String referrerPhoneNo=referralService.getReferrerPhoneNo();
          if(referrerPhoneNo==null || referrerPhoneNo.equals(" ") || referrerPhoneNo.equals(""))
          referrerPhoneNo="none";

          String referrerEmail=referralService.getReferrerEmail();
          if(referrerEmail==null || referrerEmail.equals(" ") || referrerEmail.equals(""))
          referrerEmail="none";

          String referralType=referralService.getType();
          if(referralType==null || referralType.equals(" ") || referralType.equals(""))
          referralType="none";
          String[] fieldValues={recordId,ovcId,referringOrganization,dateOfReferral,psychoServices,nutritionalServices,healthServices,educationalServices,protectionServices,shelterServices,economicServices,psychosocialOther,nutritionOther,healthOther,educationOther,protectionOther,shelterOther,economicOther,nameOfOrganizationChildIsreferred,referralCompleted,nameOfPersonReferring,designationOfReferrer,referrerPhoneNo,referrerEmail,referralType};
          hd.startElement("","","patient",atts);
		for (int i=0;i<columnNames.length;i++)
		{
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
		}
          hd.endElement("","","patient");
	}
	hd.endElement("","","OvcReferral");
	hd.endDocument();
	String xmlString = sw.toString();
        fileName="OvcReferral.xml";
        if(count>0)
        fileName="OvcReferral"+count+".xml";

        File file = new File(appUtil.getExportFilePath()+"/OvcReferral/"+fileName);

        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
}
public void getServicesInXml(int count,int startSize,List list) throws Exception
{
    String fileName="";

      try
      {
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	// SAX2.0 ContentHandler.
	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
	//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"Enrollment.dtd");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        String[] columnNames={"id","ovcId","servicedate","dateOfEntry","serviceAccessed1","serviceAccessed2","serviceAccessed3","serviceAccessed4","serviceAccessed5","serviceAccessed6","serviceAccessed7","servicesReferred","weight","height","surveyNumber","providerName","numberOfServices","childMissedSchool","childAbused","childLinkedToGovt","recordedBy"};
	hd.startElement("","","OvcService",atts);
        atts.clear();

	for(int j=startSize; j<list.size(); j++)
	{
            if(j>(startSize+1999))
            break;
            OvcService service=(OvcService)list.get(j);

          String id=new Integer(service.getId()).toString();
          if(id==null || id.equals(" ") || id.equals(""))
          id="0";

          String ovcId=service.getOvcId();
          if(ovcId==null || ovcId.equals(" ") || ovcId.equals(""))
          ovcId="none";
          ovcId=ovcId.trim();
          String servicedate=service.getServicedate();
          if(servicedate==null || servicedate.equals(" ") || servicedate.equals(""))
          servicedate="none";
          String dateOfEntry=service.getDateOfEntry();
          if(dateOfEntry==null || dateOfEntry.equals(" ") || dateOfEntry.equals(""))
          dateOfEntry="none";
          String serviceAccessed1=service.getServiceAccessed1();
          if(serviceAccessed1==null || serviceAccessed1.equals(" ") || serviceAccessed1.equals(""))
          serviceAccessed1="none";
          String serviceAccessed2=service.getServiceAccessed2();
          if(serviceAccessed2==null || serviceAccessed2.equals(" ") || serviceAccessed2.equals(""))
          serviceAccessed2="none";
          String serviceAccessed3=service.getServiceAccessed3();
          if(serviceAccessed3==null || serviceAccessed3.equals(" ") || serviceAccessed3.equals(""))
          serviceAccessed3="none";
          String serviceAccessed4=service.getServiceAccessed4();
          if(serviceAccessed4==null || serviceAccessed4.equals(" ") || serviceAccessed4.equals(""))
          serviceAccessed4="none";
          String serviceAccessed5=service.getServiceAccessed5();
          if(serviceAccessed5==null || serviceAccessed5.equals(" ") || serviceAccessed5.equals(""))
          serviceAccessed5="none";
          String serviceAccessed6=service.getServiceAccessed6();
          if(serviceAccessed6==null || serviceAccessed6.equals(" ") || serviceAccessed6.equals(""))
          serviceAccessed6="none";
          String serviceAccessed7=service.getServiceAccessed7();
          if(serviceAccessed7==null || serviceAccessed7.equals(" ") || serviceAccessed7.equals(""))
          serviceAccessed7="none";
          String servicesReferred=service.getServicesReferred();
          if(servicesReferred==null || servicesReferred.equals(" ") || servicesReferred.equals(""))
          servicesReferred="none";

          String surveyNumber=(String)new Integer(service.getSurveyNumber()).toString();
          if(surveyNumber==null || surveyNumber.equals(" ") || surveyNumber.equals(""))
          surveyNumber="0";
          String numberOfServices=(String)new Integer(service.getNumberOfServices()).toString();
          String weight=(String)new Double(service.getWeight()).toString();
          if(weight==null || weight.equals(" ") || weight.equals(""))
          weight="0";

          String height=(String)new Double(service.getHeight()).toString();
          if(height==null || height.equals(" ") || height.equals(""))
          height="0";
          String providerName=service.getProviderName();
          if(providerName==null || providerName.equals(" ") || providerName.equals(""))
          providerName="none";
          String recordedBy=service.getRecordedBy();
          if(recordedBy==null || recordedBy.equals(" ") || recordedBy.equals(""))
          recordedBy="none";
          String childMissedSchool=service.getChildMissedSchool();
          if(childMissedSchool==null || childMissedSchool.equals(" ") || childMissedSchool.equals(""))
          childMissedSchool="none";
          
          String childAbused=(new Integer(service.getChildAbused())).toString();
          if(childAbused==null || childAbused.equals(" ") || childAbused.equals(""))
          childAbused="0";
          String childLinkedToGovt=(new Integer(service.getChildLinkedToGovt())).toString();
          if(childLinkedToGovt==null || childLinkedToGovt.equals(" ") || childLinkedToGovt.equals(""))
          childLinkedToGovt="0";
          //"childMissedSchool","childAbused","childLinkedToGovt","recordedBy"};
          String[] fieldValues={id,ovcId,servicedate,dateOfEntry,serviceAccessed1,serviceAccessed2,serviceAccessed3,serviceAccessed4,serviceAccessed5,serviceAccessed6,serviceAccessed7,servicesReferred,weight,height,surveyNumber,providerName,numberOfServices,childMissedSchool,childAbused,childLinkedToGovt,recordedBy};
          hd.startElement("","","patient",atts);
		for (int i=0;i<columnNames.length;i++)
		{
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
		}
          hd.endElement("","","patient");
	}
	hd.endElement("","","OvcService");
	hd.endDocument();
	String xmlString = sw.toString();
        fileName="OvcService.xml";
        if(count>0)
        fileName="OvcService"+count+".xml";

        File file = new File(appUtil.getExportFilePath()+"/OvcService/"+fileName);

        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
}
public void getNutritionAssessmentInXml(int count,int startSize,List list) throws Exception
{
    String fileName="";
    String directoryName="NutritionAssessment";

      try
      {
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	// SAX2.0 ContentHandler.
	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
	//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"Enrollment.dtd");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        String[] columnNames={"id","ovcId","dateOfAssessment","weight","lastWeight","dateOfLastWeight","changeInWeight","height","assessmentNo","oedema","muac","bmi","bodyMassIndex","nutritionalStatus","foodSecurityAndDietQ1","foodSecurityAndDietQ2","foodSecurityAndDietQ3","foodSecurityAndDietQ4","foodSecurityAndDietQ5","foodSecurityAndDietQ6","foodSecurityAndDietQ7","foodSecurityAndDietQ8","foodSecurityAndDietQ9","hygieneQ1","hygieneQ2","hygieneQ3","hygieneQ4","medicalEvaluationHIV","medicalEvaluationDiarrhea","medicalEvaluationNausea","medicalEvaluationPainfulChewing","medicalEvaluationVomiting","medicalEvaluationPoorApetite","medicalEvaluationMouthSoars","muacFacility","yellowMuacServices","dateModified","recordedBy"};
	hd.startElement("","",directoryName,atts);
        atts.clear();

	for(int j=startSize; j<list.size(); j++)
	{
            if(j>(startSize+1999))
            break;
            NutritionAssessment na=(NutritionAssessment)list.get(j);

          String id=new Integer(na.getId()).toString();
          if(id==null || id.equals(" ") || id.equals(""))
          id="0";

          String ovcId=na.getOvcId();
          if(ovcId==null || ovcId.equals(" ") || ovcId.equals(""))
          ovcId="none";
          ovcId=ovcId.trim();
          String dateOfAssessment=getPropertyValue(na.getDateOfAssessment());
          String weight=getIntegerPropertyValue((String)new Double(na.getWeight()).toString());
          String lastWeight=getIntegerPropertyValue((String)new Double(na.getLastWeight()).toString());
          String dateOfLastWeight=getPropertyValue(na.getDateOfLastWeight());
          String changeInWeight=getPropertyValue(na.getChangeInWeight());
          String height=getIntegerPropertyValue((String)new Double(na.getHeight()).toString());
          String assessmentNo=getIntegerPropertyValue(new Integer(na.getAssessmentNo()).toString());
          String oedema=getPropertyValue(na.getOedema());
          String muac=getPropertyValue(na.getMuac());
          String bmi=getPropertyValue(na.getBmi());
          String bodyMassIndex=getPropertyValue(na.getBodyMassIndex()+"");
          String nutritionalStatus=getPropertyValue(na.getNutritionalStatus());
          String foodSecurityAndDietQ1=getPropertyValue(na.getFoodSecurityAndDietQ1());
          String foodSecurityAndDietQ2=getPropertyValue(na.getFoodSecurityAndDietQ2());
          String foodSecurityAndDietQ3=getPropertyValue(na.getFoodSecurityAndDietQ3());
          String foodSecurityAndDietQ4=getPropertyValue(na.getFoodSecurityAndDietQ4());
          String foodSecurityAndDietQ5=getPropertyValue(na.getFoodSecurityAndDietQ5());
          String foodSecurityAndDietQ6=getPropertyValue(na.getFoodSecurityAndDietQ6());
          String foodSecurityAndDietQ7=getPropertyValue(na.getFoodSecurityAndDietQ7());
          String foodSecurityAndDietQ8=getPropertyValue(na.getFoodSecurityAndDietQ8());
          String foodSecurityAndDietQ9=getPropertyValue(na.getFoodSecurityAndDietQ9());
          String hygieneQ1=getPropertyValue(na.getHygieneQ1());
          String hygieneQ2=getPropertyValue(na.getHygieneQ2());
          String hygieneQ3=getPropertyValue(na.getHygieneQ3());
          String hygieneQ4=getPropertyValue(na.getHygieneQ4());
          String medicalEvaluationHIV=getPropertyValue(na.getMedicalEvaluationHIV());
          String medicalEvaluationDiarrhea=getPropertyValue(na.getMedicalEvaluationDiarrhea());
          String medicalEvaluationNausea=getPropertyValue(na.getMedicalEvaluationNausea());
          String medicalEvaluationPainfulChewing=getPropertyValue(na.getMedicalEvaluationPainfulChewing());
          String medicalEvaluationVomiting=getPropertyValue(na.getMedicalEvaluationVomiting());
          String medicalEvaluationPoorApetite=getPropertyValue(na.getMedicalEvaluationPoorApetite());
          String medicalEvaluationMouthSoars=getPropertyValue(na.getMedicalEvaluationMouthSoars());
          String muacFacility=getPropertyValue(na.getMuacFacility());
          String yellowMuacServices=getPropertyValue(na.getYellowMuacServices());
          String dateModified=getPropertyValue(na.getDateModified());
          String recordedBy=getPropertyValue(na.getRecordedBy());

          String[] fieldValues={id,ovcId,dateOfAssessment,weight,lastWeight,dateOfLastWeight,changeInWeight,height,assessmentNo,oedema,muac,bmi,bodyMassIndex,nutritionalStatus,foodSecurityAndDietQ1,foodSecurityAndDietQ2,foodSecurityAndDietQ3,foodSecurityAndDietQ4,foodSecurityAndDietQ5,foodSecurityAndDietQ6,foodSecurityAndDietQ7,foodSecurityAndDietQ8,foodSecurityAndDietQ9,hygieneQ1,hygieneQ2,hygieneQ3,hygieneQ4,medicalEvaluationHIV,medicalEvaluationDiarrhea,medicalEvaluationNausea,medicalEvaluationPainfulChewing,medicalEvaluationVomiting,medicalEvaluationPoorApetite,medicalEvaluationMouthSoars,muacFacility,yellowMuacServices,dateModified,recordedBy};
          hd.startElement("","","patient",atts);
		for (int i=0;i<columnNames.length;i++)
		{
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
		}
          hd.endElement("","","patient");
	}
	hd.endElement("","",directoryName);
	hd.endDocument();
	String xmlString = sw.toString();
        fileName=directoryName+".xml";
        if(count>0)
        fileName=directoryName+count+".xml";

        File file = new File(appUtil.getExportFilePath()+"/"+directoryName+"/"+fileName);

        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
}
    public void exportCsiScoresInXml(int count,int startSize,List list) throws Exception
    {
        String fileName="";
        String[] columnNames={"id","ovcId","csiDate","csiFactor1","csiFactor2","csiFactor3","csiFactor4","csiFactor5","csiFactor6","csiFactor7","csiFactor8","csiFactor9","csiFactor10","csiFactor11","csiFactor12","surveyNumber","dateOfEntry"};
      try
      {
        StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	// SAX2.0 ContentHandler.
	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
	//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"users.dtd");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        hd.startElement("","","Enrollment",atts);

       for(int j=startSize; j<list.size(); j++)
	{
            if(j>(startSize+4999))
            break;

          ChildStatusIndex csi=(ChildStatusIndex)list.get(j);

          String id=getIntegerPropertyValue(new Integer(csi.getId()).toString());
          String ovcId=getPropertyValue(csi.getOvcId());
          String csiDate=csi.getCsiDate();
          if(csiDate==null)
          {
            System.err.println("csiDate is null ");
            continue;
          }
          String csiFactor1=getIntegerPropertyValue(new Integer(csi.getCsiFactor1()).toString());
          String csiFactor2=getIntegerPropertyValue(new Integer(csi.getCsiFactor2()).toString());
          String csiFactor3=getIntegerPropertyValue(new Integer(csi.getCsiFactor3()).toString());
          String csiFactor4=getIntegerPropertyValue(new Integer(csi.getCsiFactor4()).toString());
          String csiFactor5=getIntegerPropertyValue(new Integer(csi.getCsiFactor5()).toString());
          String csiFactor6=getIntegerPropertyValue(new Integer(csi.getCsiFactor6()).toString());
          String csiFactor7=getIntegerPropertyValue(new Integer(csi.getCsiFactor7()).toString());
          String csiFactor8=getIntegerPropertyValue(new Integer(csi.getCsiFactor8()).toString());
          String csiFactor9=getIntegerPropertyValue(new Integer(csi.getCsiFactor9()).toString());
          String csiFactor10=getIntegerPropertyValue(new Integer(csi.getCsiFactor10()).toString());
          String csiFactor11=getIntegerPropertyValue(new Integer(csi.getCsiFactor11()).toString());
          String csiFactor12=getIntegerPropertyValue(new Integer(csi.getCsiFactor12()).toString());
          String surveyNumber=getIntegerPropertyValue(new Integer(csi.getSurveyNumber()).toString());
          String dateOfEntry=csi.getDateOfEntry();
          if(dateOfEntry==null)
          {
            System.err.println("dateOfEntry is null ");
            dateOfEntry=appUtil.getCurrentDate();
          }
          String[] fieldValues={id,ovcId,csiDate,csiFactor1,csiFactor2,csiFactor3,csiFactor4,csiFactor5,csiFactor6,csiFactor7,csiFactor8,csiFactor9,csiFactor10,csiFactor11,csiFactor12,surveyNumber,dateOfEntry};

          hd.startElement("","","patient",atts);
		for (int i=0;i<columnNames.length;i++)
		{
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
		}
          hd.endElement("","","patient");
	}
	hd.endElement("","","Enrollment");
	hd.endDocument();

	String xmlString = sw.toString();
        fileName="CsiScore.xml";
        if(count>0)
        fileName="CsiScore"+count+".xml";
        File file = new File(appUtil.getExportFilePath()+"/CsiScore/"+fileName);

        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
     }
    public void exportFollowupSurveyRecordsInXml(int count,int startSize,List list) throws Exception
    {
        String fileName="";
        String[] columnNames={"id","ovcId","caregiverId","dateOfSurvey","updatedAddress","updatedAge","updatedAgeUnit","updatedHivStatus","updatedBirthCertStatus","updatedSchoolStatus","updatedSchoolName","updatedCaregiverName","updatedCaregiverGender","updatedCaregiverAge","updatedCaregiverAddress","updatedCaregiverPhone","updatedCaregiverOccupation","updatedCaregiverRelationship","completedbyName","completedbyDesignation","dateOfEntry","recordedBy"};
      try
      {
        StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	// SAX2.0 ContentHandler.
	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
	//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"users.dtd");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        hd.startElement("","","Enrollment",atts);
        OvcDao ovcdao=new OvcDaoImpl();
        Ovc ovc=null;
       for(int j=startSize; j<list.size(); j++)
	{
            if(j>(startSize+4999))
            break;

          FollowupSurvey followupSurvey=(FollowupSurvey)list.get(j);

          String id=new Integer(followupSurvey.getId()).toString();

          String ovcId=followupSurvey.getOvcId();
          if(ovcId==null || ovcId.equals(" ") || ovcId.equals(""))
          ovcId="none";
          ovcId=ovcId.toString().trim();
          String caregiverId=followupSurvey.getCaregiverId();
          ovc=null;
          if(caregiverId==null || caregiverId.equals(" ") || caregiverId.equals(""))
          {
            ovc=ovcdao.getOvc(ovcId);
            if(ovc !=null)
            caregiverId=ovc.getCaregiverId(); 
            if(caregiverId==null || caregiverId.equals(" ") || caregiverId.equals(""))
            caregiverId="none";
          }
          String dateOfSurvey=followupSurvey.getDateOfSurvey();
          if(dateOfSurvey==null || dateOfSurvey.equals(" ") || dateOfSurvey.equals(""))
          dateOfSurvey="none";
          dateOfSurvey=dateOfSurvey.toString().trim();
          String updatedAddress=followupSurvey.getUpdatedAddress();
          if(updatedAddress==null || updatedAddress.equals(" ") || updatedAddress.equals(""))
          updatedAddress="none";
          updatedAddress=updatedAddress.toString().trim();
          String updatedAge=new Integer(followupSurvey.getUpdatedAge()).toString();//.toString();

          String updatedAgeUnit=followupSurvey.getUpdatedAgeUnit();
          if(updatedAgeUnit==null || updatedAgeUnit.equals(" ") || updatedAgeUnit.equals(""))
          updatedAgeUnit="none";
          updatedAgeUnit=updatedAgeUnit.toString().trim();
          String updatedHivStatus=followupSurvey.getUpdatedHivStatus();
          if(updatedHivStatus==null || updatedHivStatus.equals(" ") || updatedHivStatus.equals(""))
          updatedHivStatus="none";
          updatedHivStatus=updatedHivStatus.toString().trim();
          String updatedBirthCertStatus=followupSurvey.getUpdatedBirthCertStatus();
          if(updatedBirthCertStatus==null || updatedBirthCertStatus.equals(" ") || updatedBirthCertStatus.equals(""))
          updatedBirthCertStatus="none";
          updatedBirthCertStatus=updatedBirthCertStatus.toString().trim();
          String updatedSchoolStatus=followupSurvey.getUpdatedSchoolStatus();
          if(updatedSchoolStatus==null || updatedSchoolStatus.equals(" ") || updatedSchoolStatus.equals(""))
          updatedSchoolStatus="none";
          updatedSchoolStatus=updatedSchoolStatus.toString().trim();
          String updatedSchoolName=followupSurvey.getUpdatedSchoolName();
          if(updatedSchoolName==null || updatedSchoolName.equals(" ") || updatedSchoolName.equals(""))
          updatedSchoolName="none";
          updatedSchoolName=updatedSchoolName.toString().trim();
          String updatedCaregiverName=followupSurvey.getUpdatedCaregiverName();
          if(updatedCaregiverName==null || updatedCaregiverName.equals(" ") || updatedCaregiverName.equals(""))
          updatedCaregiverName="none";
          updatedCaregiverName=updatedCaregiverName.toString().trim();
          String updatedCaregiverGender=followupSurvey.getUpdatedCaregiverGender();
          if(updatedCaregiverGender==null || updatedCaregiverGender.equals(" ") || updatedCaregiverGender.equals(""))
          updatedCaregiverGender="none";
          updatedCaregiverGender=updatedCaregiverGender.toString().trim();
          String updatedCaregiverAge=new Integer(followupSurvey.getUpdatedCaregiverAge()).toString();

          String updatedCaregiverAddress=followupSurvey.getUpdatedCaregiverAddress();
          if(updatedCaregiverAddress==null || updatedCaregiverAddress.equals(" ") || updatedCaregiverAddress.equals(""))
          updatedCaregiverAddress="none";
          updatedCaregiverAddress=updatedCaregiverAddress.toString().trim();
          String updatedCaregiverPhone=followupSurvey.getUpdatedCaregiverPhone();
          if(updatedCaregiverPhone==null || updatedCaregiverPhone.equals(" ") || updatedCaregiverPhone.equals(""))
          updatedCaregiverPhone="none";
          updatedCaregiverPhone=updatedCaregiverPhone.toString().trim();
          String updatedCaregiverOccupation=followupSurvey.getUpdatedCaregiverOccupation();
          if(updatedCaregiverOccupation==null || updatedCaregiverOccupation.equals(" ") || updatedCaregiverOccupation.equals(""))
          updatedCaregiverOccupation="none";
          updatedCaregiverOccupation=updatedCaregiverOccupation.toString().trim();
          String updatedCaregiverRelationship=followupSurvey.getUpdatedCaregiverRelationship();
          if(updatedCaregiverRelationship==null || updatedCaregiverRelationship.equals(" ") || updatedCaregiverRelationship.equals(""))
          updatedCaregiverRelationship="none";
          updatedCaregiverRelationship=updatedCaregiverRelationship.toString().trim();
          String completedbyName=followupSurvey.getCompletedbyName();
          if(completedbyName==null || completedbyName.equals(" ") || completedbyName.equals(""))
          completedbyName="none";
          completedbyName=completedbyName.toString().trim();
          String completedbyDesignation=followupSurvey.getCompletedbyDesignation();
          if(completedbyDesignation==null || completedbyDesignation.equals(" ") || completedbyDesignation.equals(""))
          completedbyDesignation="none";
          completedbyDesignation=completedbyDesignation.toString().trim();
          String dateOfEntry=followupSurvey.getDateOfEntry();
          if(dateOfEntry==null || dateOfEntry.equals(" ") || dateOfEntry.equals(""))
          dateOfEntry="none";
          dateOfEntry=dateOfEntry.toString().trim();
          String recordedBy=followupSurvey.getRecordedBy();
          if(recordedBy==null || recordedBy.equals(" ") || recordedBy.equals(""))
          recordedBy="none";
          recordedBy=recordedBy.toString().trim();

          String[] fieldValues={id,ovcId,caregiverId,dateOfSurvey,updatedAddress,updatedAge,updatedAgeUnit,updatedHivStatus,updatedBirthCertStatus,updatedSchoolStatus,updatedSchoolName,updatedCaregiverName,updatedCaregiverGender,updatedCaregiverAge,updatedCaregiverAddress,updatedCaregiverPhone,updatedCaregiverOccupation,updatedCaregiverRelationship,completedbyName,completedbyDesignation,dateOfEntry,recordedBy};

          hd.startElement("","","patient",atts);
		for (int i=0;i<columnNames.length;i++)
		{
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
		}
          hd.endElement("","","patient");
	}
	hd.endElement("","","Enrollment");
	hd.endDocument();

	String xmlString = sw.toString();
        fileName="FollowupSurveyRecords.xml";
        if(count>0)
        fileName="FollowupSurveyRecords"+count+".xml";
        File file = new File(appUtil.getExportFilePath()+"/FollowupSurveyRecords/"+fileName);

        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
    }
    public int exportCaregiverTBHIVScreeningChecklistInXml(String additionalQueryCriteria) throws Exception
    {
        int noOfRecords=0;
        String fileName="";
        String parentFolderName="CaregiverTBHIVScreeningChecklist";
        String[] columnNames={"caregiverId","dateOfAssessment","assessmentNo","caregiverCoughing","caregiverLossindWeight","caregiverHavingFever","caregiverHavingNightSweat","familyMemberHadTB","dosesOfARVMissedByCaregiver","caregiverEnrolledInPMTCT","eidTestingDoneForChild","caregiverOnSeptrin","hivStatusDisclosedToPartner","hivStatusOfPartnerKnown","dosesOfSeptrinMissedByCaregiver","childAboveSixWksAndStartedOnSeptrin","caregiverSleepInTreatedNet","beneficiariesHasSoresOrBleeding","numberOfClubsAttndedInThreeMths","caregiverSwellingOnNeck","volunteerName","designation","dateModified"};
        CaregiverTBHIVScreeningChecklistDao cgtbdao=new CaregiverTBHIVScreeningChecklistDaoImpl();
        List list=cgtbdao.getTBHIVScreeningRecords(additionalQueryCriteria);
        if(list !=null)
        {
            noOfRecords=list.size();
            System.err.println("list size in exportCaregiverTBHIVScreeningChecklistInXml is "+list.size());
        }
        List preparedList=prepareDataInAppropriateListSize(2000,list);
        int count=0;
      try
      {
       for(int i=0; i<preparedList.size(); i++)
       {
        StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	// SAX2.0 ContentHandler.
	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
	//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"users.dtd");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	
	AttributesImpl atts = new AttributesImpl();
        List subList=null;
        CaregiverTBHIVScreeningChecklist cgtbhiv=null;
       
           subList=(List)preparedList.get(i);
           System.err.println("subList size is "+subList.size());
           
           hd.startDocument();
           hd.startElement("","","dataValueSet",atts);
           
           for(int j=0; j<subList.size(); j++)
           {
              cgtbhiv=(CaregiverTBHIVScreeningChecklist)subList.get(j);
              String caregiverId=getPropertyValue(cgtbhiv.getCaregiverId());
              String dateOfAssessment=getPropertyValue(cgtbhiv.getDateOfAssessment());
              String assessmentNo=getPropertyValue(cgtbhiv.getAssessmentNo()+"");
              String caregiverCoughing=getPropertyValue(cgtbhiv.getCaregiverCoughing());
              String caregiverLossindWeight=getPropertyValue(cgtbhiv.getCaregiverLossindWeight());
              String caregiverHavingFever=getPropertyValue(cgtbhiv.getCaregiverHavingFever());
              String caregiverHavingNightSweat=getPropertyValue(cgtbhiv.getCaregiverHavingNightSweat());
              String familyMemberHadTB=getPropertyValue(cgtbhiv.getFamilyMemberHadTB());
              String dosesOfARVMissedByCaregiver=getPropertyValue(cgtbhiv.getDosesOfARVMissedByCaregiver());
              String caregiverEnrolledInPMTCT=getPropertyValue(cgtbhiv.getCaregiverEnrolledInPMTCT());
              String eidTestingDoneForChild=getPropertyValue(cgtbhiv.getEidTestingDoneForChild());
              String caregiverOnSeptrin=getPropertyValue(cgtbhiv.getCaregiverOnSeptrin());
              String hivStatusDisclosedToPartner=getPropertyValue(cgtbhiv.getHivStatusDisclosedToPartner());
              String hivStatusOfPartnerKnown=getPropertyValue(cgtbhiv.getHivStatusOfPartnerKnown());
              String dosesOfSeptrinMissedByCaregiver=getPropertyValue(cgtbhiv.getDosesOfSeptrinMissedByCaregiver());
              String childAboveSixWksAndStartedOnSeptrin=getPropertyValue(cgtbhiv.getChildAboveSixWksAndStartedOnSeptrin());
              String caregiverSleepInTreatedNet=getPropertyValue(cgtbhiv.getCaregiverSleepInTreatedNet());
              String beneficiariesHasSoresOrBleeding=getPropertyValue(cgtbhiv.getBeneficiariesHasSoresOrBleeding());
              String numberOfClubsAttndedInThreeMths=getPropertyValue(cgtbhiv.getNumberOfClubsAttndedInThreeMths());
              String caregiverSwellingOnNeck=getPropertyValue(cgtbhiv.getCaregiverSwellingOnNeck());
              String volunteerName=getPropertyValue(cgtbhiv.getVolunteerName());
              String designation=getPropertyValue(cgtbhiv.getDesignation());
              String dateModified=getPropertyValue(cgtbhiv.getDateModified());
              
              String[] fieldValues={caregiverId,dateOfAssessment,assessmentNo,caregiverCoughing,caregiverLossindWeight,caregiverHavingFever,caregiverHavingNightSweat,familyMemberHadTB,dosesOfARVMissedByCaregiver,caregiverEnrolledInPMTCT,eidTestingDoneForChild,caregiverOnSeptrin,hivStatusDisclosedToPartner,hivStatusOfPartnerKnown,dosesOfSeptrinMissedByCaregiver,childAboveSixWksAndStartedOnSeptrin,caregiverSleepInTreatedNet,beneficiariesHasSoresOrBleeding,numberOfClubsAttndedInThreeMths,caregiverSwellingOnNeck,volunteerName,designation,dateModified};
              
              hd.startElement("","","dataValue",atts);
                for (int k=0;k<columnNames.length;k++)
                {
                    hd.startElement("","",columnNames[k],atts);
                    hd.characters(fieldValues[k].toCharArray(),0,fieldValues[k].length());
                    hd.endElement("","",columnNames[k]);
                }
              hd.endElement("","","dataValue");
            }
	hd.endElement("","","dataValueSet");
	hd.endDocument();

	String xmlString = sw.toString();
        fileName=parentFolderName+".xml";
        if(count>0)
        fileName=parentFolderName+count+".xml";
        String parentFolder=appUtil.getExportFilePath()+fileSeperator+parentFolderName;
        appUtil.createDirectories(parentFolder);
        File file = new File(parentFolder+fileSeperator+fileName);

        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
        count++;
       }
      }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
      return noOfRecords;
     }
    public int exportOvcTBHIVScreeningChecklistInXml(String additionalQueryCriteria) throws Exception
    {
        int noOfRecords=0;
        String fileName="";
        String parentFolderName="OvcTBHIVScreeningChecklist";
        String[] columnNames={"ovcId","dateOfAssessment","assessmentNo","childCoughing","childLossindWeight","childHavingFever","childHavingNightSweat","familyMemberHadTB","dosesOfARVMissedByChild","caregiverEnrolledInPMTCT","eidTestingDoneForChild","childOnSeptrin","hivStatusDisclosedToPartner","hivStatusOfPartnerKnown","dosesOfSeptrinMissedByChild","childAboveSixWksAndStartedOnSeptrin","childSleepInTreatedNet","beneficiariesHasSoresOrBleeding","numberOfClubsAttndedInThreeMths","childSwellingOnNeck","volunteerName","designation","dateModified"};
        OvcTBHIVScreeningChecklistDao ovctbdao=new OvcTBHIVScreeningChecklistDaoImpl();
        List list=ovctbdao.getTBHIVScreeningRecords(additionalQueryCriteria);
        if(list !=null)
        {
            noOfRecords=0;
            System.err.println("list size in exportOvcTBHIVScreeningChecklistInXml is "+list.size());
        }
        List preparedList=prepareDataInAppropriateListSize(2000,list);
        int count=0;
      try
      {
       for(int i=0; i<preparedList.size(); i++)
       {
        StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	// SAX2.0 ContentHandler.
	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
	//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"users.dtd");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	
	AttributesImpl atts = new AttributesImpl();
        List subList=null;
        OvcTBHIVScreeningChecklist ovctbhiv=null;
       
           subList=(List)preparedList.get(i);
           System.err.println("subList size is "+subList.size());
           
           hd.startDocument();
           hd.startElement("","","dataValueSet",atts);
           
           for(int j=0; j<subList.size(); j++)
           {
              ovctbhiv=(OvcTBHIVScreeningChecklist)subList.get(j);
              //columnNames={"volunteerName","designation","dateModified"};
              String ovcId=getPropertyValue(ovctbhiv.getOvcId());
              String dateOfAssessment=getPropertyValue(ovctbhiv.getDateOfAssessment());
              String assessmentNo=getPropertyValue(ovctbhiv.getAssessmentNo()+"");
              String childCoughing=getPropertyValue(ovctbhiv.getChildCoughing());
              String childLossindWeight=getPropertyValue(ovctbhiv.getChildLossindWeight());
              String childHavingFever=getPropertyValue(ovctbhiv.getChildHavingFever());
              String childHavingNightSweat=getPropertyValue(ovctbhiv.getChildHavingNightSweat());
              String familyMemberHadTB=getPropertyValue(ovctbhiv.getFamilyMemberHadTB());
              String dosesOfARVMissedByChild=getPropertyValue(ovctbhiv.getDosesOfARVMissedByChild());
              String caregiverEnrolledInPMTCT=getPropertyValue(ovctbhiv.getCaregiverEnrolledInPMTCT());
              String eidTestingDoneForChild=getPropertyValue(ovctbhiv.getEidTestingDoneForChild());
              String childOnSeptrin=getPropertyValue(ovctbhiv.getChildOnSeptrin());
              String hivStatusDisclosedToPartner=getPropertyValue(ovctbhiv.getHivStatusDisclosedToPartner());
              String hivStatusOfPartnerKnown=getPropertyValue(ovctbhiv.getHivStatusOfPartnerKnown());
              String dosesOfSeptrinMissedByChild=getPropertyValue(ovctbhiv.getDosesOfSeptrinMissedByChild());
              String childAboveSixWksAndStartedOnSeptrin=getPropertyValue(ovctbhiv.getChildAboveSixWksAndStartedOnSeptrin());
              String childSleepInTreatedNet=getPropertyValue(ovctbhiv.getChildSleepInTreatedNet());
              String beneficiariesHasSoresOrBleeding=getPropertyValue(ovctbhiv.getBeneficiariesHasSoresOrBleeding());
              String numberOfClubsAttndedInThreeMths=getPropertyValue(ovctbhiv.getNumberOfClubsAttndedInThreeMths());
              String childSwellingOnNeck=getPropertyValue(ovctbhiv.getChildSwellingOnNeck());
              String volunteerName=getPropertyValue(ovctbhiv.getVolunteerName());
              String designation=getPropertyValue(ovctbhiv.getDesignation());
              String dateModified=getPropertyValue(ovctbhiv.getDateModified());
              
              String[] fieldValues={ovcId,dateOfAssessment,assessmentNo,childCoughing,childLossindWeight,childHavingFever,childHavingNightSweat,familyMemberHadTB,dosesOfARVMissedByChild,caregiverEnrolledInPMTCT,eidTestingDoneForChild,childOnSeptrin,hivStatusDisclosedToPartner,hivStatusOfPartnerKnown,dosesOfSeptrinMissedByChild,childAboveSixWksAndStartedOnSeptrin,childSleepInTreatedNet,beneficiariesHasSoresOrBleeding,numberOfClubsAttndedInThreeMths,childSwellingOnNeck,volunteerName,designation,dateModified};
              hd.startElement("","","dataValue",atts);
                for (int k=0;k<columnNames.length;k++)
                {
                    hd.startElement("","",columnNames[k],atts);
                    hd.characters(fieldValues[k].toCharArray(),0,fieldValues[k].length());
                    hd.endElement("","",columnNames[k]);
                }
              hd.endElement("","","dataValue");
            }
	hd.endElement("","","dataValueSet");
	hd.endDocument();

	String xmlString = sw.toString();
        fileName=parentFolderName+".xml";
        if(count>0)
        fileName=parentFolderName+count+".xml";
        String parentFolder=appUtil.getExportFilePath()+fileSeperator+parentFolderName;
        appUtil.createDirectories(parentFolder);
        File file = new File(parentFolder+fileSeperator+fileName);

        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
        count++;
       }
      }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
      return noOfRecords;
     }
    public int exportHivStatusRecordsInXml(String additionalQueryCriteria) throws Exception
    {
        int totalRecords=0;
        String fileName="";
        String parentFolderName="HivStatusUpdate";
        String[] columnNames={"recordId","clientId","hivStatus","recordStatus","dateOfCurrentStatus","dateModified","clientType","clientEnrolledInCare","enrolledOnART","organizationClientIsReferred","pointOfUpdate"};
        HivStatusUpdateDao hsudao=new HivStatusUpdateDaoImpl();
        List hsuList=hsudao.getHivStatusUpdates(additionalQueryCriteria);
        if(hsuList !=null)
        {
            totalRecords=hsuList.size();
            System.err.println("hsuList size is "+hsuList.size());
        }
        List preparedList=prepareDataInAppropriateListSize(5000,hsuList);
        int count=0;
      try
      {
       for(int i=0; i<preparedList.size(); i++)
       {
        StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	// SAX2.0 ContentHandler.
	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
	//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"users.dtd");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	
	AttributesImpl atts = new AttributesImpl();
        List subList=null;
        HivStatusUpdate hsu=null;
       
           subList=(List)preparedList.get(i);
           System.err.println("subList size is "+subList.size());
           
           hd.startDocument();
           hd.startElement("","","Enrollment",atts);
           
           for(int j=0; j<subList.size(); j++)
           {
              hsu=(HivStatusUpdate)subList.get(j);
              String clientId=getPropertyValue(hsu.getClientId());
              String clientEnrolledInCare=getPropertyValue(hsu.getClientEnrolledInCare());
              String clientEnrolledOnART=getPropertyValue(hsu.getEnrolledOnART());
              String clientType=getPropertyValue(hsu.getClientType());
              String dateModified=getPropertyValue(hsu.getDateModified());
              String dateOfCurrentStatus=getPropertyValue(hsu.getDateOfCurrentStatus());
              String hivStatus=getPropertyValue(hsu.getHivStatus());
              String organizationClientIsReferred=getPropertyValue(hsu.getOrganizationClientIsReferred());
              String pointOfUpdate=getPropertyValue(hsu.getPointOfUpdate());
              String recordId=getPropertyValue(hsu.getRecordId());
              String recordStatus=getPropertyValue(hsu.getRecordStatus());
              
              String[] fieldValues={recordId,clientId,hivStatus,recordStatus,dateOfCurrentStatus,dateModified,clientType,clientEnrolledInCare,clientEnrolledOnART,organizationClientIsReferred,pointOfUpdate};
              hd.startElement("","","patient",atts);
                for (int k=0;k<columnNames.length;k++)
                {
                    hd.startElement("","",columnNames[k],atts);
                    hd.characters(fieldValues[k].toCharArray(),0,fieldValues[k].length());
                    hd.endElement("","",columnNames[k]);
                }
              hd.endElement("","","patient");
            }
	hd.endElement("","","Enrollment");
	hd.endDocument();

	String xmlString = sw.toString();
        fileName=parentFolderName+".xml";
        if(count>0)
        fileName=parentFolderName+count+".xml";
        String parentFolder=appUtil.getExportFilePath()+fileSeperator+parentFolderName;
        appUtil.createDirectories(parentFolder);
        File file = new File(parentFolder+fileSeperator+fileName);

        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
        count++;
       }
      }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
      return totalRecords;
     }
    public int exportOvcWithdrwalRecordsInXml(String additionalQueryCriteria) throws Exception
    {
        int totalRecords=0;
        String fileName="";
        String parentFolderName="OvcWithdrawal";
        String[] columnNames={"ovcId","dateOfWithdrawal","reasonWithdrawal","type","surveyNumber"};
        OvcWithdrawalDao wdao=new OvcWithdrawalDaoImpl();
        List withdrawalList=wdao.getListOfOvcWithdrawnForExport(additionalQueryCriteria);
        List hhWithdrawalList=wdao.getListOfHouseholdsWithdrawnForExport(additionalQueryCriteria);
        List cgiverWithdrawalList=wdao.getListOfCaregiversWithdrawnForExport(additionalQueryCriteria);
        if(hhWithdrawalList !=null)
        withdrawalList.addAll(hhWithdrawalList);
        if(cgiverWithdrawalList !=null)
        withdrawalList.addAll(cgiverWithdrawalList);
        if(withdrawalList !=null)
        {
            totalRecords=withdrawalList.size();
            System.err.println("withdrawalList size is "+withdrawalList.size());
        }
        List preparedList=prepareDataInAppropriateListSize(5000,withdrawalList);
        int count=0;
      try
      {
           for(int i=0; i<preparedList.size(); i++)
           {
                StringWriter sw=new StringWriter();
                StreamResult streamResult = new StreamResult(sw);
                SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
                // SAX2.0 ContentHandler.
                TransformerHandler hd = tf.newTransformerHandler();
                Transformer serializer = hd.getTransformer();
                serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
                //serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"users.dtd");
                serializer.setOutputProperty(OutputKeys.INDENT,"yes");
                hd.setResult(streamResult);

                AttributesImpl atts = new AttributesImpl();
                //hd.startElement("","","Enrollment",atts);
                List subList=null;
                OvcWithdrawal withdrawal=null;

                   subList=(List)preparedList.get(i);
                   hd.startDocument();
                   hd.startElement("","","Enrollment",atts);

                   for(int j=0; j<subList.size(); j++)
                   {
                      withdrawal=(OvcWithdrawal)subList.get(j);
                      String ovcId=getPropertyValue(withdrawal.getOvcId());
                      String dateOfWithdrawal=getPropertyValue(withdrawal.getDateOfWithdrawal());
                      String reasonWithdrawal=getPropertyValue(withdrawal.getReasonWithdrawal());
                      String type=getPropertyValue(withdrawal.getType());
                      String surveyNo=getIntegerPropertyValue(new Integer(withdrawal.getSurveyNumber()).toString());
                      String[] fieldValues={ovcId,dateOfWithdrawal,reasonWithdrawal,type,surveyNo};
                      hd.startElement("","","patient",atts);
                        for (int k=0;k<columnNames.length;k++)
                        {
                            hd.startElement("","",columnNames[k],atts);
                            hd.characters(fieldValues[k].toCharArray(),0,fieldValues[k].length());
                            hd.endElement("","",columnNames[k]);
                        }
                      hd.endElement("","","patient");
                    }
                hd.endElement("","","Enrollment");
                hd.endDocument();

                String xmlString = sw.toString();
                fileName=parentFolderName+".xml";
                if(count>0)
                fileName=parentFolderName+count+".xml";
                File file = new File(appUtil.getExportFilePath()+fileSeperator+parentFolderName+fileSeperator+fileName);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                bw.write(xmlString);
                bw.flush();
                bw.close();
                count++;
               }
          }
          catch(Exception ex)
          {
            ex.printStackTrace();
          }
       return totalRecords;
     }
    public void exportOrganizationalAssessmentInXml(int count,int startSize,List list) throws Exception
    {
        String fileName="";
        String[] columnNames={"recordId","orgCode","isorganizationasubgrantee","dateofcapacityassessment","leadassessorname","noofassessment","noofassessors","visionandmission","goalsandobjectives","actionplanning","roleandresponsibilities","internalrules","meetings","leadership","teambuilding","monitoring","reportingandrecordkeeping","budgeting","bookkeeping","banking","localresourcemobilization","proposalwriting","networking","advocacy"};
      try
      {
        StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	// SAX2.0 ContentHandler.
	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
	//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"users.dtd");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        hd.startElement("","","Enrollment",atts);

       for(int j=startSize; j<list.size(); j++)
	{
            if(j>(startSize+4999))
            break;

          OrganizationalCapacityAssessment orgCapAssessment=(OrganizationalCapacityAssessment)list.get(j);

          String recordId=new Integer(orgCapAssessment.getRecordId()).toString();

          String orgCode=orgCapAssessment.getOrgCode();
          if(orgCode==null || orgCode.equals(" ") || orgCode.equals(""))
          orgCode="none";
          orgCode=orgCode.toString().trim();
          String isorganizationasubgrantee=orgCapAssessment.getIsorganizationasubgrantee();
          if(isorganizationasubgrantee==null || isorganizationasubgrantee.equals(" ") || isorganizationasubgrantee.equals(""))
          isorganizationasubgrantee="none";
          isorganizationasubgrantee=isorganizationasubgrantee.toString().trim();
          String dateofcapacityassessment=orgCapAssessment.getDateofcapacityassessment();
          if(dateofcapacityassessment==null || dateofcapacityassessment.equals(" ") || dateofcapacityassessment.equals(""))
          dateofcapacityassessment="none";
          dateofcapacityassessment=dateofcapacityassessment.toString().trim();
          String leadassessorname=orgCapAssessment.getLeadassessorname();//.toString();
          if(leadassessorname==null || leadassessorname.equals(" ") || leadassessorname.equals(""))
          leadassessorname="none";
          leadassessorname=leadassessorname.toString().trim();


          String noofassessment=new Integer(orgCapAssessment.getNoofassessors()).toString();
          String noofassessors=new Integer(orgCapAssessment.getNoofassessment()).toString();
          String visionandmission=new Integer(orgCapAssessment.getVisionandmission()).toString();
          String goalsandobjectives=new Integer(orgCapAssessment.getGoalsandobjectives()).toString();
          String actionplanning=new Integer(orgCapAssessment.getActionplanning()).toString();
          String roleandresponsibilities=new Integer(orgCapAssessment.getRoleandresponsibilities()).toString();
          String internalrules=new Integer(orgCapAssessment.getInternalrules()).toString();
          String meetings=new Integer(orgCapAssessment.getMeetings()).toString();
          String leadership=new Integer(orgCapAssessment.getLeadership()).toString();
          String teambuilding=new Integer(orgCapAssessment.getTeambuilding()).toString();
          String monitoring=new Integer(orgCapAssessment.getMonitoring()).toString();
          String reportingandrecordkeeping=new Integer(orgCapAssessment.getReportingandrecordkeeping()).toString();
          String budgeting=new Integer(orgCapAssessment.getBudgeting()).toString();
          String bookkeeping=new Integer(orgCapAssessment.getBookkeeping()).toString();
          String banking=new Integer(orgCapAssessment.getBanking()).toString();
          String localresourcemobilization=new Integer(orgCapAssessment.getLocalresourcemobilization()).toString();
          String proposalwriting=new Integer(orgCapAssessment.getProposalwriting()).toString();
          String networking=new Integer(orgCapAssessment.getNetworking()).toString();
          String advocacy=new Integer(orgCapAssessment.getAdvocacy()).toString();
          String[] fieldValues={recordId,orgCode,isorganizationasubgrantee,dateofcapacityassessment,leadassessorname,noofassessment,noofassessors,visionandmission,goalsandobjectives,actionplanning,roleandresponsibilities,internalrules,meetings,leadership,teambuilding,monitoring,reportingandrecordkeeping,budgeting,bookkeeping,banking,localresourcemobilization,proposalwriting,networking,advocacy};
          //"monitoring","reportingandrecordkeeping","budgeting","bookkeeping","banking","localresourcemobilization","proposalwriting","networking","advocacy"};

          hd.startElement("","","patient",atts);
		for (int i=0;i<columnNames.length;i++)
		{
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
		}
          hd.endElement("","","patient");
	}
	hd.endElement("","","Enrollment");
	hd.endDocument();

	String xmlString = sw.toString();
        fileName="OrganizationalAssessment.xml";
        if(count>0)
        fileName="OrganizationalAssessment"+count+".xml";
        File file = new File(appUtil.getExportFilePath()+"/OrganizationalAssessment/"+fileName);
        
        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
     }
    public void exportOrganizationRecordsInXml(int count,int startSize,List list) throws Exception
    {
        String fileName="";
        String[] columnNames={"orgCode","state","lga","orgName","orgType","orgSubtype","address","contactName1","contactPhone1","contactEmail1","contactName2","contactPhone2","contactEmail2","contactName3","contactPhone3","contactEmail3","services"};

      try
      {
        StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	// SAX2.0 ContentHandler.
	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
	//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"users.dtd");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        hd.startElement("","","Enrollment",atts);

       for(int j=startSize; j<list.size(); j++)
	{
            if(j>(startSize+4999))
            break;

          OrganizationRecords orgRecords=(OrganizationRecords)list.get(j);

          String orgCode=orgRecords.getOrgCode();
          if(orgCode==null || orgCode.equals(" ") || orgCode.equals(""))
          orgCode="none";
          String state=orgRecords.getState();
          if(state==null || state.equals(" ") || state.equals(""))
          state="none";
          String lga=orgRecords.getLga();
          if(lga==null || lga.equals(" ") || lga.equals(""))
          lga="none";
          String orgName=orgRecords.getOrgName();
          if(orgName==null || orgName.equals(" ") || orgName.equals(""))
          orgName="none";
          String orgType=orgRecords.getOrgType();
          if(orgType==null || orgType.equals(" ") || orgType.equals(""))
          orgType="none";
          String orgSubtype=orgRecords.getOrgSubtype();
          if(orgSubtype==null || orgSubtype.equals(" ") || orgSubtype.equals(""))
          orgSubtype="none";
          String address=orgRecords.getAddress();
          if(address==null || address.equals(" ") || address.equals(""))
          address="none";
          String contactName1=orgRecords.getContactName1();
          if(contactName1==null || contactName1.equals(" ") || contactName1.equals(""))
          contactName1="none";
          String contactPhone1=orgRecords.getContactPhone1();
          if(contactPhone1==null || contactPhone1.equals(" ") || contactPhone1.equals(""))
          contactPhone1="none";
          String contactEmail1=orgRecords.getContactEmail1();
          if(contactEmail1==null || contactEmail1.equals(" ") || contactEmail1.equals(""))
          contactEmail1="none";
          String contactName2=orgRecords.getContactName2();
          if(contactName2==null || contactName2.equals(" ") || contactName2.equals(""))
          contactName2="none";
          String contactPhone2=orgRecords.getContactPhone2();
          if(contactPhone2==null || contactPhone2.equals(" ") || contactPhone2.equals(""))
          contactPhone2="none";
          String contactEmail2=orgRecords.getContactEmail2();
          if(contactEmail2==null || contactEmail2.equals(" ") || contactEmail2.equals(""))
          contactEmail2="none";
          String contactName3=orgRecords.getContactEmail3();
          if(contactName3==null || contactName3.equals(" ") || contactName3.equals(""))
          contactName3="none";
          String contactPhone3=orgRecords.getContactPhone3();
          if(contactPhone3==null || contactPhone3.equals(" ") || contactPhone3.equals(""))
          contactPhone3="none";
          String contactEmail3=orgRecords.getContactEmail3();
          if(contactEmail3==null || contactEmail3.equals(" ") || contactEmail3.equals(""))
          contactEmail3="none";
          String services=orgRecords.getServices();
          if(services==null || services.equals(" ") || services.equals(""))
          services="none";

          String[] fieldValues={orgCode,state,lga,orgName,orgType,orgSubtype,address,contactName1,contactPhone1,contactEmail1,contactName2,contactPhone2,contactEmail2,contactName3,contactPhone3,contactEmail3,services};

          hd.startElement("","","patient",atts);
		for (int i=0;i<columnNames.length;i++)
		{
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
		}
          hd.endElement("","","patient");
	}
	hd.endElement("","","Enrollment");
	hd.endDocument();

	String xmlString = sw.toString();
        fileName="OrganizationRecords.xml";
        if(count>0)
        fileName="OrganizationRecords"+count+".xml";
        File file = new File(appUtil.getExportFilePath()+"/OrganizationRecords/"+fileName);

        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
     }
    public void exportWardRecordsInXml(int count,int startSize,List list) throws Exception
    {
        String fileName="";
        String[] columnNames={"cbo_code","ward_code","ward_name"};

      try
      {
        StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	// SAX2.0 ContentHandler.
	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
	//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"users.dtd");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        hd.startElement("","","Enrollment",atts);

       for(int j=startSize; j<list.size(); j++)
	{
            if(j>(startSize+4999))
            break;

           Wards ward=(Wards)list.get(j);

          String cbo_code=ward.getCbo_code();
          if(cbo_code==null || cbo_code.equals(" ") || cbo_code.equals(""))
          cbo_code="none";
          cbo_code=cbo_code.trim();
          String ward_code=ward.getWard_code();
          if(ward_code==null || ward_code.equals(" ") || ward_code.equals(""))
          ward_code="none";
          ward_code=ward_code.trim();
          String ward_name=ward.getWard_name();
          if(ward_name==null || ward_name.equals(" ") || ward_name.equals(""))
          ward_name="none";
          ward_name=ward_name.trim();

          String[] fieldValues={cbo_code,ward_code,ward_name};
          hd.startElement("","","patient",atts);
            for (int i=0;i<columnNames.length;i++)
            {
                hd.startElement("","",columnNames[i],atts);
                hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                hd.endElement("","",columnNames[i]);
            }
          hd.endElement("","","patient");
	}
	hd.endElement("","","Enrollment");
	hd.endDocument();

	String xmlString = sw.toString();
        fileName="Wards.xml";
        if(count>0)
        fileName="Wards"+count+".xml";
        File file = new File(appUtil.getExportFilePath()+"/Wards/"+fileName);

        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
     }
    /*public void exportWardRecordsInXml(int count,int startSize,List list) throws Exception
    {
        String fileName="";
        String[] columnNames={"cbo_code","ward_code","ward_name"};

      try
      {
        StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	// SAX2.0 ContentHandler.
	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
	//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"users.dtd");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        hd.startElement("","","Enrollment",atts);

       for(int j=startSize; j<list.size(); j++)
	{
            if(j>(startSize+4999))
            break;

           Wards ward=(Wards)list.get(j);

          String cbo_code=ward.getCbo_code();
          if(cbo_code==null || cbo_code.equals(" ") || cbo_code.equals(""))
          cbo_code="none";
          cbo_code=cbo_code.trim();
          String ward_code=ward.getWard_code();
          if(ward_code==null || ward_code.equals(" ") || ward_code.equals(""))
          ward_code="none";
          ward_code=ward_code.trim();
          String ward_name=ward.getWard_name();
          if(ward_name==null || ward_name.equals(" ") || ward_name.equals(""))
          ward_name="none";
          ward_name=ward_name.trim();

          String[] fieldValues={cbo_code,ward_code,ward_name};
          hd.startElement("","","patient",atts);
            for (int i=0;i<columnNames.length;i++)
            {
                hd.startElement("","",columnNames[i],atts);
                hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                hd.endElement("","",columnNames[i]);
            }
          hd.endElement("","","patient");
	}
	hd.endElement("","","Enrollment");
	hd.endDocument();

	String xmlString = sw.toString();
        fileName="Wards.xml";
        if(count>0)
        fileName="Wards"+count+".xml";
        File file = new File(appUtil.getExportFilePath()+"/Wards/"+fileName);

        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
     }*/
    public void exportSchoolRecordsInXml(int count,int startSize,List list) throws Exception
    {
        String fileName="";
        String[] columnNames={"id","state","lga","type","name","address"};

      try
      {
        StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	// SAX2.0 ContentHandler.
	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
	//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"users.dtd");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        hd.startElement("","","Enrollment",atts);

       for(int j=startSize; j<list.size(); j++)
	{
            if(j>(startSize+4999))
            break;

           OvcSchool school=(OvcSchool)list.get(j);

           String id=new Integer(school.getId()).toString();

          String state=school.getState();
          if(state==null || state.equals(" ") || state.equals(""))
          state="none";
          state=state.trim();

          String lga=school.getLga();
          if(lga==null || lga.equals(" ") || lga.equals(""))
          lga="none";
          lga=lga.trim();
          String type=school.getType();
          if(type==null || type.equals(" ") || type.equals(""))
          type="none";
          type=type.trim();

          String name=school.getName();
          if(name==null || name.equals(" ") || name.equals(""))
          name="none";
          name=name.trim();

          String address=school.getAddress();
          if(address==null || address.equals(" ") || address.equals(""))
          address="none";
          address=address.trim();

          String[] fieldValues={id,state,lga,type,name,address};
          hd.startElement("","","patient",atts);
		for (int i=0;i<columnNames.length;i++)
		{
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
		}
          hd.endElement("","","patient");
	}
	hd.endElement("","","Enrollment");
	hd.endDocument();

	String xmlString = sw.toString();
        fileName="OvcSchool.xml";
        if(count>0)
        fileName="OvcSchool"+count+".xml";
        File file = new File(appUtil.getExportFilePath()+"/OvcSchool/"+fileName);

        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
     }
    public void exportToDhisInPlainText(List list) throws Exception
    {
        String fileName="";
        appUtil=new AppUtility();
        String[] columnNames={"strDataField","strOrgUnit","Period","dblEntry","ysnCheck","strComment","intMin","intMax","ysnDisplay","strUser","dtmChanged","/  /","0","0","\"\"","0","0","0","","/  /     :  :"};
        String str="";
        for(int i=0; i<columnNames.length; i++)
        {
            if(i<11)
            str+="\""+columnNames[i]+"\",";
            else if(i<columnNames.length-2)
            str+=columnNames[i]+",";
            else
            str+=columnNames[i];
        }
        str+="\n";
      try
      {
        String period="";
        String fileDateName="";
	for(int j=0; j<list.size(); j++)
	{
          SummaryStatisticsBean stb=(SummaryStatisticsBean)list.get(j);
          String cbo=stb.getCbo();
          String indicatorName=stb.getIndicatorName();
          String noOfOvc=new Integer(stb.getNoOfOvc()).toString();
          fileDateName=stb.getMonth()+" "+stb.getYear();
          period=stb.getYear()+"/"+stb.getMonth()+"/01";
          String lastPeriod=appUtil.getPreviousDate(Integer.parseInt(stb.getYear()), Integer.parseInt(stb.getMonth()), 1, -2);
          String[] fieldValues={indicatorName,cbo,period,noOfOvc,"0","\"\"","0","9999","-1","\"Nomis\"",lastPeriod};
          for(int k=0; k<fieldValues.length; k++)
          {
              if(k<2)
              str+="\""+fieldValues[k]+"\",";
              else if(k<fieldValues.length-1)
              str+=fieldValues[k]+",";
              else
              str+=fieldValues[k];
          }
          str+="\n";
	}
	fileName="dhisSummExport "+fileDateName+".txt";

        File file = new File(appUtil.getDhisExportPath()+"/"+fileName);
        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));
        bw.write(str);
        bw.flush();
        bw.close();
       }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
}
    public void exportToDhisInXml(List list,String user,String fileName) throws Exception
    {
        appUtil=new AppUtility();
        util=new DaoUtil();
        String[] columnNames={"dataValue"};
        List listOfFilesToBeZipped=new ArrayList();
        String destinationDirectory=appUtil.getDhisExportPath();
      try
      {
        StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();

        atts.addAttribute(" ", " ","xmlns", " ", "http://dhis2.org/schema/dxf/2.0");
        hd.startElement("","","dataValueSet",atts);
        atts.clear();

        String period=null,timestamp=null;
        String fileDateName="";
        for(int j=0; j<list.size(); j++)
	{
          SummaryStatisticsBean stb=(SummaryStatisticsBean)list.get(j);
          String cbo=stb.getCbo();
          String indicatorName=stb.getIndicatorName();
          String categoryOption=stb.getCategoryOptionCombo();
          String noOfOvc=new Integer(stb.getNoOfOvc()).toString();
          fileDateName=util.getMonthAsString(Integer.parseInt(stb.getMonth()))+" "+stb.getYear();
          int month=(Integer.parseInt(stb.getMonth()));
          String mth=""+month;
          if(month <10)
          mth="0"+month;
          period=stb.getYear()+mth;
          timestamp=appUtil.getCurrentDate();

            for (int i=0;i<columnNames.length;i++)
            {
                atts.addAttribute(" ", " ","dataElement", " ", indicatorName);
                atts.addAttribute(" ", " ","period", " ", period);
                atts.addAttribute(" ", " ","orgUnit", " ", cbo);
                atts.addAttribute(" ", " ","categoryOptionCombo", " ", categoryOption);
                atts.addAttribute(" ", " ","value", " ", noOfOvc);
                atts.addAttribute(" ", " ","storedBy", " ", user);
                atts.addAttribute(" ", " ","timestamp", " ",timestamp);
                atts.addAttribute(" ", " ","followUp", " ", "false");
                hd.startElement("","",columnNames[i],atts);
                hd.endElement("","",columnNames[i]);
            }
	}
	hd.endElement("","","dataValueSet");
	hd.endDocument();

	String xmlString = sw.toString();
        fileName=fileName.replace("\\", "-");
        fileName=fileName.replace("/", "-");
        String xmlFileName=fileName+".xml";

        File file = new File(destinationDirectory+"/"+xmlFileName);
        listOfFilesToBeZipped.add(file.getAbsolutePath());
        //System.err.println("file.getAbsolutePath() is "+file.getAbsolutePath());
        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));
        bw.write(xmlString);
        bw.flush();
        bw.close();

       }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
      ZipHandler zipHandler=new ZipHandler();
      zipHandler.zipFile(listOfFilesToBeZipped, destinationDirectory, fileName);
}
public void getMthlySummaryReportInXml(int count,int startSize,List list) throws Exception
{
    String fileName="";

      try
      {
	StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	// SAX2.0 ContentHandler.
	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
	//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"Enrollment.dtd");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        String[] columnNames={"id","ovcId","servicedate","dateOfEntry","serviceAccessed1","serviceAccessed2","serviceAccessed3","serviceAccessed4","serviceAccessed5","serviceAccessed6","serviceAccessed7","weight","height","surveyNumber"};
	hd.startElement("","","OvcService",atts);
        atts.clear();

	for(int j=startSize; j<list.size(); j++)
	{
            if(j>(startSize+4999))
            break;
            OvcService service=(OvcService)list.get(j);

          String id=new Integer(service.getId()).toString();
          if(id==null || id.equals(" ") || id.equals(""))
          id="0";

          String ovcId=service.getOvcId();
          if(ovcId==null || ovcId.equals(" ") || ovcId.equals(""))
          ovcId="none";
          String servicedate=service.getServicedate();
          if(servicedate==null || servicedate.equals(" ") || servicedate.equals(""))
          servicedate="none";
          String dateOfEntry=service.getDateOfEntry();
          if(dateOfEntry==null || dateOfEntry.equals(" ") || dateOfEntry.equals(""))
          dateOfEntry="none";
          String serviceAccessed1=service.getServiceAccessed1();
          if(serviceAccessed1==null || serviceAccessed1.equals(" ") || serviceAccessed1.equals(""))
          serviceAccessed1="none";
          String serviceAccessed2=service.getServiceAccessed2();
          if(serviceAccessed2==null || serviceAccessed2.equals(" ") || serviceAccessed2.equals(""))
          serviceAccessed2="none";
          String serviceAccessed3=service.getServiceAccessed3();
          if(serviceAccessed3==null || serviceAccessed3.equals(" ") || serviceAccessed3.equals(""))
          serviceAccessed3="none";
          String serviceAccessed4=service.getServiceAccessed4();
          if(serviceAccessed4==null || serviceAccessed4.equals(" ") || serviceAccessed4.equals(""))
          serviceAccessed4="none";
          String serviceAccessed5=service.getServiceAccessed5();
          if(serviceAccessed5==null || serviceAccessed5.equals(" ") || serviceAccessed5.equals(""))
          serviceAccessed5="none";
          String serviceAccessed6=service.getServiceAccessed6();
          if(serviceAccessed6==null || serviceAccessed6.equals(" ") || serviceAccessed6.equals(""))
          serviceAccessed6="none";
          String serviceAccessed7=service.getServiceAccessed7();
          if(serviceAccessed7==null || serviceAccessed7.equals(" ") || serviceAccessed7.equals(""))
          serviceAccessed7="none";

          String surveyNumber=(String)new Integer(service.getSurveyNumber()).toString();
          if(surveyNumber==null || surveyNumber.equals(" ") || surveyNumber.equals(""))
          surveyNumber="0";

          String weight=(String)new Double(service.getWeight()).toString();
          if(weight==null || weight.equals(" ") || weight.equals(""))
          weight="0";

          String height=(String)new Double(service.getHeight()).toString();
          if(height==null || height.equals(" ") || height.equals(""))
          height="0";

          String[] fieldValues={id,ovcId,servicedate,dateOfEntry,serviceAccessed1,serviceAccessed2,serviceAccessed3,serviceAccessed4,serviceAccessed5,serviceAccessed6,serviceAccessed7,weight,height,surveyNumber};
          hd.startElement("","","patient",atts);
		for (int i=0;i<columnNames.length;i++)
		{
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
		}
          hd.endElement("","","patient");
	}
	hd.endElement("","","OvcService");
	hd.endDocument();
	String xmlString = sw.toString();
        fileName="OvcService.xml";
        if(count>0)
        fileName="OvcService"+count+".xml";

        File file = new File(appUtil.getExportFilePath()+"/OvcService/"+fileName);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
       catch(Exception ex)
       {
		ex.printStackTrace();
       }
}
private String getStringValue(String value)
{
    if(value==null || value.equals(" ") || value.equals(""))
    value="none";
    value=value.trim();
    return value;
}
    public void exportData(HttpSession session,int count,int tableIndex)
    {
        List enrollList=new ArrayList();
        int enrollSize=0;
        int csiSize=0;
        int serviceSize=0;
        int orgRecordsSize=0;
        int wardRecordSize=0;
        int organizationalAssessmentListSize=0;
        int followupSurveyRecordsListSize=0;
        int referralRecordsListSize=0;
        int schoolRecordsListSize=0;
        int hheSize=0;
        int hvaSize=0;
        int caregiverSize=0;
        int hhsSize=0;
        int nutritionAssessmentSize=0;
        int householdFollowupAssessmentSize=0;
        int ovcWithdrawalSize=0;

        enrollList=(List)session.getAttribute("enrollmentRecList");
        List csiList=(List)session.getAttribute("csiRecList");
        List servicesList=(List)session.getAttribute("servicesRecList");
        List organizationRecordsList=(List)session.getAttribute("organizationRecList");
        List wardRecordsList=(List)session.getAttribute("WardRecList");
        List organizationalAssessmentList=(List)session.getAttribute("listOfOrganizationalAssessment");
        List followupSurveyRecordsList=(List)session.getAttribute("listOffollowupSurveyRecords");
        List referralRecordsList=(List)session.getAttribute("referralRecordsList");
        List schoolRecordsList=(List)session.getAttribute("schoolRecList");
        List hheList=(List)session.getAttribute("hheRecList");
        List hvaList=(List)session.getAttribute("hvaRecList");
        List caregiverList=(List)session.getAttribute("caregiverRecList");
        List hhsList=(List)session.getAttribute("hhsRecList");
        List nutritionAssessmentList=(List)session.getAttribute("nutritionAssessmentRecList");
        List householdFollowupAssessmentList=(List)session.getAttribute("householdFollowupAssessmentRecList");
        List ovcWithdrawalList=(List)session.getAttribute("ovcWithdrawalRecList");

        if(enrollList !=null)
        enrollSize=enrollList.size();
        if(csiList !=null)
        csiSize=csiList.size();
        if(servicesList !=null)
        serviceSize=servicesList.size();
        if(organizationRecordsList !=null)
        orgRecordsSize=organizationRecordsList.size();
        if(wardRecordsList !=null)
        wardRecordSize=wardRecordsList.size();
        if(organizationalAssessmentList !=null)
           organizationalAssessmentListSize=organizationalAssessmentList.size();
        if(followupSurveyRecordsList !=null)
           followupSurveyRecordsListSize=followupSurveyRecordsList.size();
        if(referralRecordsList !=null)
           referralRecordsListSize=referralRecordsList.size();
        if(schoolRecordsList !=null)
           schoolRecordsListSize=schoolRecordsList.size();
        if(hheList !=null)
        hheSize=hheList.size();
        if(hvaList !=null)
        hvaSize=hvaList.size();
        if(caregiverList !=null)
        caregiverSize=caregiverList.size();
        if(hhsList !=null)
        hhsSize=hhsList.size();
        if(nutritionAssessmentList !=null)
        nutritionAssessmentSize=nutritionAssessmentList.size();
        if(householdFollowupAssessmentList !=null)
        householdFollowupAssessmentSize=householdFollowupAssessmentList.size();
        if(ovcWithdrawalList !=null)
        ovcWithdrawalSize=ovcWithdrawalList.size();
        
        int dataSize=0;
        int startSize=0;
        int tempSize=0;

        try
        {
            if(tableIndex==0)
            {
                dataSize=enrollSize;
                tempSize=(count+1)*2000;
                if(dataSize >= tempSize && tempSize <=2000)
                {
                    startSize=0;
                }
                else
                {
                    startSize=tempSize-2000;
                }
                getEnrollmentInXml(count,startSize,enrollList);
            }
            else if (tableIndex==1)
            {
                dataSize=csiSize;
                tempSize=(count+1)*2000;
                if(dataSize >= tempSize && tempSize <=2000)
                startSize=0;
                else
                startSize=tempSize-2000;
                exportCsiScoresInXml(count,startSize,csiList);
            }
            else if (tableIndex==2)
            {
                dataSize=serviceSize;
                tempSize=(count+1)*2000;
                if(dataSize >= tempSize && tempSize <= 2000)
                startSize=0;
                else
                startSize=tempSize-2000;

                getServicesInXml(count,startSize,servicesList);
            }
            else if (tableIndex==3)
            {
                dataSize=orgRecordsSize;
                tempSize=(count+1)*2000;
                if(dataSize >= tempSize && tempSize <=2000)
                startSize=0;
                else
                startSize=tempSize-2000;
                exportOrganizationRecordsInXml(count,startSize,organizationRecordsList);
            }
            else if (tableIndex==4)
            {
                dataSize=wardRecordSize;
                tempSize=(count+1)*2000;
                if(dataSize >= tempSize && tempSize <=2000)
                startSize=0;
                else
                startSize=tempSize-2000;
                exportWardRecordsInXml(count,startSize,wardRecordsList);
            }
            else if (tableIndex==5)
            {
                dataSize=organizationalAssessmentListSize;
                tempSize=(count+1)*2000;
                if(dataSize >= tempSize && tempSize <=2000)
                startSize=0;
                else
                startSize=tempSize-2000;
                exportOrganizationalAssessmentInXml(count,startSize,organizationalAssessmentList);
            }
            else if (tableIndex==6)
            {
                dataSize=followupSurveyRecordsListSize;
                tempSize=(count+1)*2000;
                if(dataSize >= tempSize && tempSize <=2000)
                startSize=0;
                else
                startSize=tempSize-2000;
                exportFollowupSurveyRecordsInXml(count,startSize,followupSurveyRecordsList);
            }
            else if (tableIndex==7)
            {
                dataSize=referralRecordsListSize;
                tempSize=(count+1)*2000;
                if(dataSize >= tempSize && tempSize <=2000)
                startSize=0;
                else
                startSize=tempSize-2000;
                exportReferralServicesInXml(count,startSize,referralRecordsList);
            }
            else if (tableIndex==8)
            {
                dataSize=schoolRecordsListSize;
                tempSize=(count+1)*2000;
                if(dataSize >= tempSize && tempSize <=2000)
                startSize=0;
                else
                startSize=tempSize-2000;
                exportSchoolRecordsInXml(count,startSize,schoolRecordsList);
            }
            else if(tableIndex==9)
            {
                dataSize=hheSize;
                tempSize=(count+1)*2000;
                if(dataSize >= tempSize && tempSize <=2000)
                {
                    startSize=0;
                }
                else
                {
                    startSize=tempSize-2000;
                }
                exportHouseholdEnrollmentInXml(count,startSize,hheList);
            }
            else if(tableIndex==10)
            {
                dataSize=hhsSize;
                tempSize=(count+1)*2000;
                if(dataSize >= tempSize && tempSize <=2000)
                {
                    startSize=0;
                }
                else
                {
                    startSize=tempSize-2000;
                }
                exportHouseholdServiceInXml(count,startSize,hhsList);
            }
            else if(tableIndex==11)
            {
                dataSize=hvaSize;
                tempSize=(count+1)*2000;
                if(dataSize >= tempSize && tempSize <=2000)
                {
                    startSize=0;
                }
                else
                {
                    startSize=tempSize-2000;
                }
                exportHouseholdVulnerabilityAssessmentInXml(count,startSize,hvaList);
            }
            else if(tableIndex==12)
            {
                dataSize=caregiverSize;
                tempSize=(count+1)*2000;
                if(dataSize >= tempSize && tempSize <=2000)
                {
                    startSize=0;
                }
                else
                {
                    startSize=tempSize-2000;
                }
                exportCaregiverInXml(count,startSize,caregiverList);
            }
            else if(tableIndex==13)
            {
                dataSize=nutritionAssessmentSize;
                tempSize=(count+1)*2000;
                if(dataSize >= tempSize && tempSize <=2000)
                {
                    startSize=0;
                }
                else
                {
                    startSize=tempSize-2000;
                }
                getNutritionAssessmentInXml(count,startSize,nutritionAssessmentList);
            }
            else if(tableIndex==14)
            {
                dataSize=householdFollowupAssessmentSize;
                tempSize=(count+1)*2000;
                if(dataSize >= tempSize && tempSize <=2000)
                {
                    startSize=0;
                }
                else
                {
                    startSize=tempSize-2000;
                }
                exportHouseholdFollowupAssessmentInXml(count,startSize,householdFollowupAssessmentList);
            }
            else if(tableIndex==15)
            {
                dataSize=ovcWithdrawalSize;
                tempSize=(count+1)*2000;
                if(dataSize >= tempSize && tempSize <=2000)
                {
                    startSize=0;
                }
                else
                {
                    startSize=tempSize-2000;
                }
                //exportOvcWithdrwalRecordsInXml(count,startSize,ovcWithdrawalList);
            }
            TrainingExportImportManager teim=new TrainingExportImportManager();
            teim.exportTrainingDataInXml();
            teim.exportTrainingParticipantDataInXml();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public List prepareDataInAppropriateListSize(int requiredsize,List list)
    {
        List mainList=new ArrayList();
        List subList=new ArrayList();
        if(list !=null)
        {
            for(int i=0; i<list.size(); i++)
            {
                subList.add(list.get(i));
                if(subList.size()==requiredsize)
                {
                    mainList.add(subList);
                    System.err.println("subList size in prepareDataInAppropriateListSize is "+subList.size());
                    subList=new ArrayList();
                }
                else if(i==list.size()-1)
                {
                    mainList.add(subList);
                }
            }
        }
        return mainList;
    }
}
