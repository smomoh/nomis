/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ImportExport;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Training;
import com.fhi.kidmap.business.TrainingCategory;
import com.fhi.kidmap.business.TrainingDesignation;
import com.fhi.kidmap.business.TrainingInformationSetup;
import com.fhi.kidmap.business.TrainingParticipant;
import com.fhi.kidmap.business.TrainingStatusSetup;
import com.fhi.kidmap.dao.TrainingCategoryDao;
import com.fhi.kidmap.dao.TrainingCategoryDaoImpl;
import com.fhi.kidmap.dao.TrainingDao;
import com.fhi.kidmap.dao.TrainingDaoImpl;
import com.fhi.kidmap.dao.TrainingDesignationDao;
import com.fhi.kidmap.dao.TrainingDesignationDaoImpl;
import com.fhi.kidmap.dao.TrainingInformationSetupDao;
import com.fhi.kidmap.dao.TrainingInformationSetupDaoImpl;
import com.fhi.kidmap.dao.TrainingParticipantDao;
import com.fhi.kidmap.dao.TrainingParticipantDaoImpl;
import com.fhi.kidmap.dao.TrainingStatusSetupDao;
import com.fhi.kidmap.dao.TrainingStatusSetupDaoImpl;
import com.nomis.databasemanagement.DataMgtUtility;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.AttributesImpl;

/**
 *
 * @author Siaka
 */
public class TrainingExportImportManager implements Serializable
{
    AppUtility appUtil=new AppUtility();
    String directoryPathName="Training";
    public File exportTrainingDataInXml() throws Exception
    {
        String directoryName=directoryPathName;
        String fileName="Training.xml";
        String[] columnNames={"recordId","trainingId","participantId","category","designation","startDate","endDate","dateModified","trainingStatus","partnerCode"};
        Training trn=null;
        TrainingDao tcdao=new TrainingDaoImpl();
        List list=tcdao.getTrainings();
        File file=null;
        if(list==null)
        return file;

      try
      {
        appUtil.createDirectories(appUtil.getExportFilePath()+"/"+directoryName);
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
        String recordId=null;
        String trainingId=null;
        String participantId=null;
        String category=null;
        String designation=null;
        String startDate=null;
        String endDate=null;
        String dateModified=null;
        String trainingStatus=null;
        String partnerCode=null;

	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        hd.startElement("","","datavaluset",atts);
        int count=0;
       for(int j=0; j<list.size(); j++)
	{
           
           trn=(Training)list.get(j);
           recordId=DataMgtUtility.getPropertyValue(trn.getRecordId());
           trainingId=DataMgtUtility.getPropertyValue(trn.getTrainingId());
           participantId=DataMgtUtility.getPropertyValue(trn.getParticipantId());
           category=DataMgtUtility.getPropertyValue(trn.getCategory());
           designation=DataMgtUtility.getPropertyValue(trn.getDesignation());
           startDate=DataMgtUtility.getPropertyValue(trn.getStartDate());
           endDate=DataMgtUtility.getPropertyValue(trn.getEndDate());
           dateModified=DataMgtUtility.getPropertyValue(trn.getDateModified());
           trainingStatus=DataMgtUtility.getPropertyValue(trn.getTrainingStatus());
           partnerCode=DataMgtUtility.getPropertyValue(trn.getPartnerCode());
           
          String[] fieldValues={recordId,trainingId,participantId,category,designation,startDate,endDate,dateModified,trainingStatus,partnerCode};
          hd.startElement("","","datavalue",atts);
          for (int i=0;i<columnNames.length;i++)
          {
              hd.startElement("","",columnNames[i],atts);
              hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
              hd.endElement("","",columnNames[i]);
          }
          hd.endElement("","","datavalue");
	}
	hd.endElement("","","datavaluset");
	hd.endDocument();
	String xmlString = sw.toString();
        file = new File(appUtil.getExportFilePath()+"/"+directoryName+"/"+fileName);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
        return file;
    }
    public File exportTrainingParticipantDataInXml() throws Exception
    {
        String directoryName=directoryPathName;
        String fileName="TrainingParticipant.xml";
        String[] columnNames={"participantId","traineeId","age","dateModified","designation","email","gender","organizationCode","phone","participantName"};
        TrainingParticipant tp=null;
        TrainingParticipantDao tpdao=new TrainingParticipantDaoImpl();
        List list=tpdao.getAllTrainingParticipants();
        File file=null;
        if(list==null)
        return file;

      try
      {
        appUtil.createDirectories(appUtil.getExportFilePath()+"/"+directoryName);
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
        String participantId=null;
        String traineeId=null;
        String age=null;
        String dateModified=null;
        String designation=null;
        String email=null;
        String gender=null;
        String organizationCode=null;
        String phone=null;
        
        String participantName=null;

	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        hd.startElement("","","datavaluset",atts);
        int count=0;
       for(int j=0; j<list.size(); j++)
	{

           tp=(TrainingParticipant)list.get(j);
           participantId=DataMgtUtility.getPropertyValue(tp.getParticipantId());
           age=DataMgtUtility.getIntegerPropertyValue(tp.getAge()+"");
           dateModified=DataMgtUtility.getPropertyValue(tp.getDateModified());
           designation=DataMgtUtility.getPropertyValue(tp.getDesignation());
           email=DataMgtUtility.getPropertyValue(tp.getEmail());
           gender=DataMgtUtility.getPropertyValue(tp.getGender());
           organizationCode=DataMgtUtility.getPropertyValue(tp.getOrganizationCode());
           phone=DataMgtUtility.getPropertyValue(tp.getPhone());
           traineeId=DataMgtUtility.getPropertyValue(tp.getTraineeId());
           participantName=DataMgtUtility.getPropertyValue(tp.getParticipantName());

          String[] fieldValues={participantId,traineeId,age,dateModified,designation,email,gender,organizationCode,phone,participantName};
          hd.startElement("","","datavalue",atts);
          for (int i=0;i<columnNames.length;i++)
          {
              hd.startElement("","",columnNames[i],atts);
              hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
              hd.endElement("","",columnNames[i]);
          }
          hd.endElement("","","datavalue");
	}
	hd.endElement("","","datavaluset");
	hd.endDocument();
	String xmlString = sw.toString();
        file = new File(appUtil.getExportFilePath()+"/"+directoryName+"/"+fileName);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
        return file;
    }
    public File exportTrainingCategoryInXml() throws Exception
    {
        String directoryName=directoryPathName+appUtil.getFileSeperator()+"TrainingCategory";
        String fileName="TrainingCategory.xml";
        String[] columnNames={"categoryId","categoryName"};
        TrainingCategory tc=null; 
        TrainingCategoryDao tcdao=new TrainingCategoryDaoImpl();
        List list=tcdao.getAllTrainingCategories();
        File file=null;
        if(list==null)
        return file;

      try
      {
        appUtil.createDirectories(appUtil.getExportFilePath()+appUtil.getFileSeperator()+directoryName);
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
        hd.startElement("","","datavaluset",atts);
       for(int j=0; j<list.size(); j++)
	{
            tc=(TrainingCategory)list.get(j);
          String categoryId=tc.getCategoryId();
          categoryId=DataMgtUtility.getIntegerPropertyValue(categoryId);
          String categoryName=tc.getCategoryName();
          categoryName=DataMgtUtility.getIntegerPropertyValue(categoryName);
          
          String[] fieldValues={categoryId,categoryName};
          hd.startElement("","","datavalue",atts);
		for (int i=0;i<columnNames.length;i++)
		{
                    hd.startElement("","",columnNames[i],atts);
                    hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                    hd.endElement("","",columnNames[i]);
		}
          hd.endElement("","","datavalue");
	}
	hd.endElement("","","datavaluset");
	hd.endDocument();
	String xmlString = sw.toString();
        file = new File(appUtil.getExportFilePath()+"/"+directoryName+"/"+fileName);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
        return file;
     }
    public File exportTrainingDesignationInXml() throws Exception
    {
        String directoryName=directoryPathName+appUtil.getFileSeperator()+"TrainingDesignation";
        String fileName="TrainingDesignation.xml";
        String[] columnNames={"categoryId","designationId","designationName"};
        TrainingDesignation td=null;
        TrainingDesignationDao tddao=new TrainingDesignationDaoImpl();
        List list=tddao.getAllTrainingDesignations();
      File file=null;
      if(list==null)
        return file;

      try
      {
        appUtil.createDirectories(appUtil.getExportFilePath()+appUtil.getFileSeperator()+directoryName);
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
        hd.startElement("","","datavaluset",atts);

       for(int j=0; j<list.size(); j++)
	{
          td=(TrainingDesignation)list.get(j);
          String categoryId=td.getCategoryId();
          categoryId=DataMgtUtility.getIntegerPropertyValue(categoryId);
          String designationId=td.getDesignationId();
          designationId=DataMgtUtility.getIntegerPropertyValue(designationId);
          String designationName=td.getDesignationName();
          designationName=DataMgtUtility.getIntegerPropertyValue(designationName);
          
          String[] fieldValues={categoryId,designationId,designationName};
          hd.startElement("","","datavalue",atts);
            for (int i=0;i<columnNames.length;i++)
            {
                hd.startElement("","",columnNames[i],atts);
                hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                hd.endElement("","",columnNames[i]);
            }
          hd.endElement("","","datavalue");
	}
	hd.endElement("","","datavaluset");
	hd.endDocument();
	String xmlString = sw.toString();
        file = new File(appUtil.getExportFilePath()+appUtil.getFileSeperator()+directoryName+appUtil.getFileSeperator()+fileName);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
      return file;
     }
    public File exportTrainingStatusSetupInXml() throws Exception
    {
        String directoryName=directoryPathName+appUtil.getFileSeperator()+"TrainingStatusSetup";
        String fileName="TrainingStatusSetup.xml";
        String[] columnNames={"statusId","trainingStatusName"};
        TrainingStatusSetup tss=null; 
        TrainingStatusSetupDao tsdao=new TrainingStatusSetupDaoImpl();
        List list=tsdao.getAllTrainingStatusSetups();
        File file=null;
      if(list==null)
        return file;

      try
      {
        appUtil.createDirectories(appUtil.getExportFilePath()+appUtil.getFileSeperator()+directoryName);
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
        hd.startElement("","","datavaluset",atts);

       for(int j=0; j<list.size(); j++)
	{
          tss=(TrainingStatusSetup)list.get(j);
          String statusId=DataMgtUtility.getIntegerPropertyValue(tss.getStatusId());
          String trainingStatusName=DataMgtUtility.getIntegerPropertyValue(tss.getTrainingStatusName());
          String[] fieldValues={statusId,trainingStatusName};
          hd.startElement("","","datavalue",atts);
            for (int i=0;i<columnNames.length;i++)
            {
                hd.startElement("","",columnNames[i],atts);
                hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                hd.endElement("","",columnNames[i]);
            }
          hd.endElement("","","datavalue");
	}
	hd.endElement("","","datavaluset");
	hd.endDocument();
	String xmlString = sw.toString();
        file = new File(appUtil.getExportFilePath()+"/"+directoryName+"/"+fileName);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
        return file;
     }
    public File exportTrainingInformationSetupInXml() throws Exception
    {
        String directoryName=directoryPathName+appUtil.getFileSeperator()+"TrainingInformationSetup";
        String fileName="TrainingInformationSetup.xml";
        String[] columnNames={"recordId","trainingName"};
        TrainingInformationSetup tis=null;
        TrainingInformationSetupDao tisdao=new TrainingInformationSetupDaoImpl();
        List list=tisdao.getAllTrainingInformationSetups();
        File file=null;
      if(list==null)
        return file;

      try
      {
        appUtil.createDirectories(appUtil.getExportFilePath()+appUtil.getFileSeperator()+directoryName);
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
        hd.startElement("","","datavaluset",atts);

       for(int j=0; j<list.size(); j++)
	{
          tis=(TrainingInformationSetup)list.get(j);
          String recordId=DataMgtUtility.getIntegerPropertyValue(tis.getRecordId());
          String trainingName=DataMgtUtility.getPropertyValue(tis.getTrainingName());
          String[] fieldValues={recordId,trainingName};
          hd.startElement("","","datavalue",atts);
            for (int i=0;i<columnNames.length;i++)
            {
                hd.startElement("","",columnNames[i],atts);
                hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                hd.endElement("","",columnNames[i]);
            }
          hd.endElement("","","datavalue");
	}
	hd.endElement("","","datavaluset");
	hd.endDocument();
	String xmlString = sw.toString();
        file = new File(appUtil.getExportFilePath()+"/"+directoryName+"/"+fileName);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
        return file;
   }
    public String getTrainingMetadataXmlFolderPathFile()
    {
       return appUtil.getTrainingMetadataXmlFolderPathFile();
        
    }
    public String getTrainingDataXmlFolderPathFile()
    {
        return appUtil.getTrainingDataXmlFolderPathFile();
    }
    public List importTrainingData(int dataUploadId, String parentFolderPath)
    {
        List list=new ArrayList();
        List resultList=new ArrayList();
        List subList=new ArrayList();
        String filePath=getTrainingDataXmlFolderPathFile();
        appUtil.createDirectories(filePath);
        try
        {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            File file=null;
            Document doc = null;
            String fileName=filePath+"Training.xml";
            //System.err.println("fileName is "+fileName);
                file=new File(fileName);
                //System.err.println("fileName in importTrainingData() is "+fileName);
                if(!file.exists())
                {
                    return resultList;
                }
                //System.err.println("fileName 2 in importTrainingData() is "+fileName);
                TrainingDao trndao=new TrainingDaoImpl();
                doc = docBuilder.parse (file);
                // normalize text representation
                doc.getDocumentElement().normalize();
                NodeList listOfObjects = doc.getElementsByTagName("datavalue");
                String recordId=null;
                for(int s=0; s<listOfObjects.getLength() ; s++)
                {

                    Training trn=new Training();
                    Node firstPersonNode = listOfObjects.item(s);
                    //System.err.println("firstPersonNode.getNodeType() is "+firstPersonNode.getNodeType()+" Node.ELEMENT_NODE = "+Node.ELEMENT_NODE);
                    if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                     {
                        recordId=XmlProcessor.getNodeValue("recordId",s,listOfObjects);
                        trn.setCategory(XmlProcessor.getNodeValue("category",s,listOfObjects));
                        trn.setDateModified(XmlProcessor.getNodeValue("dateModified",s,listOfObjects));
                        trn.setDesignation(XmlProcessor.getNodeValue("designation",s,listOfObjects));
                        trn.setStartDate(XmlProcessor.getNodeValue("startDate",s,listOfObjects));
                        trn.setEndDate(XmlProcessor.getNodeValue("endDate",s,listOfObjects));
                        trn.setParticipantId(XmlProcessor.getNodeValue("participantId",s,listOfObjects));
                        trn.setPartnerCode(XmlProcessor.getNodeValue("partnerCode",s,listOfObjects));
                        trn.setRecordId(recordId);
                        trn.setTrainingId(XmlProcessor.getNodeValue("trainingId",s,listOfObjects));
                        trn.setTrainingStatus(XmlProcessor.getNodeValue("trainingStatus",s,listOfObjects));
                        if(recordId !=null && !recordId.equalsIgnoreCase("none"))
                        {
                            //System.err.println("trn.getRecordId() is "+trn.getRecordId());
                            if(trndao.getTraining(recordId) !=null)
                            trndao.updateTraining(trn);
                            else
                            trndao.saveTraining(trn);
                        }
                        list.add(trn);
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
        subList.add("Number of Training records");
        subList.add(list.size());
        resultList.add(subList);
    return resultList;
}
    public List importTrainingParticipant(int dataUploadId, String parentFolderPath)
    {
        List list=new ArrayList();
        List resultList=new ArrayList();
        List subList=new ArrayList();
        String filePath=getTrainingDataXmlFolderPathFile();
        appUtil.createDirectories(filePath);
        try
        {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            File file=null;
            Document doc = null;
            String fileName=filePath+"TrainingParticipant.xml";
            //System.err.println("fileName2 in importTrainingParticipant() is "+fileName);
                file=new File(fileName);
                if(!file.exists())
                {
                    return resultList;
                }
                //System.err.println("fileName2 in importTrainingParticipant() is "+fileName);
                TrainingParticipantDao tpdao=new TrainingParticipantDaoImpl();
                doc = docBuilder.parse (file);
                // normalize text representation
                doc.getDocumentElement().normalize();
                NodeList listOfObjects = doc.getElementsByTagName("datavalue");
                String participantId=null;
                for(int s=0; s<listOfObjects.getLength() ; s++)
                {

                    TrainingParticipant tp=new TrainingParticipant();
                    Node firstPersonNode = listOfObjects.item(s);
                    //System.err.println("firstPersonNode.getNodeType() is "+firstPersonNode.getNodeType()+" Node.ELEMENT_NODE = "+Node.ELEMENT_NODE);
                    if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                     {
                        participantId=XmlProcessor.getNodeValue("participantId",s,listOfObjects);
                        tp.setAge(new Integer(XmlProcessor.getNodeValue("age",s,listOfObjects)));
                        tp.setDateModified(XmlProcessor.getNodeValue("dateModified",s,listOfObjects));
                        tp.setDesignation(XmlProcessor.getNodeValue("designation",s,listOfObjects));
                        tp.setEmail(XmlProcessor.getNodeValue("email",s,listOfObjects));
                        tp.setGender(XmlProcessor.getNodeValue("gender",s,listOfObjects));
                        tp.setOrganizationCode(XmlProcessor.getNodeValue("organizationCode",s,listOfObjects));
                        tp.setParticipantId(XmlProcessor.getNodeValue("participantId",s,listOfObjects));
                        tp.setParticipantName(XmlProcessor.getNodeValue("participantName",s,listOfObjects));
                        tp.setPhone(XmlProcessor.getNodeValue("phone",s,listOfObjects));
                        tp.setTraineeId(XmlProcessor.getNodeValue("traineeId",s,listOfObjects));
                        
                        if(participantId !=null && !participantId.equalsIgnoreCase("none"))
                        {
                            System.err.println("tp.getTraineeId() is "+tp.getTraineeId());
                            if(tpdao.getTrainingParticipant(participantId) !=null)
                            tpdao.updateTrainingParticipant(tp);
                            else
                            tpdao.saveTrainingParticipant(tp);
                        }
                        list.add(tp);
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
        
    return resultList;
}
    public List importTrainingData()
    {
        List list=new ArrayList();
        List resultList=new ArrayList();
        List subList=new ArrayList();
        String filePath=getTrainingDataXmlFolderPathFile();
        appUtil.createDirectories(filePath);
        try
        {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            File file=null;
            Document doc = null;
            String fileName=filePath+"Training.xml";
            //System.err.println("fileName is "+fileName);
                file=new File(fileName);
                //System.err.println("fileName in importTrainingData() is "+fileName);
                if(!file.exists())
                {
                    return resultList;
                }
                //System.err.println("fileName 2 in importTrainingData() is "+fileName);
                TrainingDao trndao=new TrainingDaoImpl();
                doc = docBuilder.parse (file);
                // normalize text representation
                doc.getDocumentElement().normalize();
                NodeList listOfObjects = doc.getElementsByTagName("datavalue");
                String recordId=null;
                for(int s=0; s<listOfObjects.getLength() ; s++)
                {

                    Training trn=new Training();
                    Node firstPersonNode = listOfObjects.item(s);
                    //System.err.println("firstPersonNode.getNodeType() is "+firstPersonNode.getNodeType()+" Node.ELEMENT_NODE = "+Node.ELEMENT_NODE);
                    if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                     {
                        recordId=XmlProcessor.getNodeValue("recordId",s,listOfObjects);
                        trn.setCategory(XmlProcessor.getNodeValue("category",s,listOfObjects));
                        trn.setDateModified(XmlProcessor.getNodeValue("dateModified",s,listOfObjects));
                        trn.setDesignation(XmlProcessor.getNodeValue("designation",s,listOfObjects));
                        trn.setStartDate(XmlProcessor.getNodeValue("startDate",s,listOfObjects));
                        trn.setEndDate(XmlProcessor.getNodeValue("endDate",s,listOfObjects));
                        trn.setParticipantId(XmlProcessor.getNodeValue("participantId",s,listOfObjects));
                        trn.setPartnerCode(XmlProcessor.getNodeValue("partnerCode",s,listOfObjects));
                        trn.setRecordId(recordId);
                        trn.setTrainingId(XmlProcessor.getNodeValue("trainingId",s,listOfObjects));
                        trn.setTrainingStatus(XmlProcessor.getNodeValue("trainingStatus",s,listOfObjects));
                        if(recordId !=null && !recordId.equalsIgnoreCase("none"))
                        {
                            //System.err.println("trn.getRecordId() is "+trn.getRecordId());
                            if(trndao.getTraining(recordId) !=null)
                            trndao.updateTraining(trn);
                            else
                            trndao.saveTraining(trn);
                        }
                        list.add(trn);
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
        subList.add("Number of Training records");
        subList.add(list.size());
        resultList.add(subList);
    return resultList;
}
    public List importTrainingParticipant()
    {
        List list=new ArrayList();
        List resultList=new ArrayList();
        List subList=new ArrayList();
        String filePath=getTrainingDataXmlFolderPathFile();
        appUtil.createDirectories(filePath);
        try
        {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            File file=null;
            Document doc = null;
            String fileName=filePath+"TrainingParticipant.xml";
            //System.err.println("fileName2 in importTrainingParticipant() is "+fileName);
                file=new File(fileName);
                if(!file.exists())
                {
                    return resultList;
                }
                //System.err.println("fileName2 in importTrainingParticipant() is "+fileName);
                TrainingParticipantDao tpdao=new TrainingParticipantDaoImpl();
                doc = docBuilder.parse (file);
                // normalize text representation
                doc.getDocumentElement().normalize();
                NodeList listOfObjects = doc.getElementsByTagName("datavalue");
                String participantId=null;
                for(int s=0; s<listOfObjects.getLength() ; s++)
                {

                    TrainingParticipant tp=new TrainingParticipant();
                    Node firstPersonNode = listOfObjects.item(s);
                    //System.err.println("firstPersonNode.getNodeType() is "+firstPersonNode.getNodeType()+" Node.ELEMENT_NODE = "+Node.ELEMENT_NODE);
                    if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                     {
                        participantId=XmlProcessor.getNodeValue("participantId",s,listOfObjects);
                        tp.setAge(new Integer(XmlProcessor.getNodeValue("age",s,listOfObjects)));
                        tp.setDateModified(XmlProcessor.getNodeValue("dateModified",s,listOfObjects));
                        tp.setDesignation(XmlProcessor.getNodeValue("designation",s,listOfObjects));
                        tp.setEmail(XmlProcessor.getNodeValue("email",s,listOfObjects));
                        tp.setGender(XmlProcessor.getNodeValue("gender",s,listOfObjects));
                        tp.setOrganizationCode(XmlProcessor.getNodeValue("organizationCode",s,listOfObjects));
                        tp.setParticipantId(XmlProcessor.getNodeValue("participantId",s,listOfObjects));
                        tp.setParticipantName(XmlProcessor.getNodeValue("participantName",s,listOfObjects));
                        tp.setPhone(XmlProcessor.getNodeValue("phone",s,listOfObjects));
                        tp.setTraineeId(XmlProcessor.getNodeValue("traineeId",s,listOfObjects));
                        
                        if(participantId !=null && !participantId.equalsIgnoreCase("none"))
                        {
                            System.err.println("tp.getTraineeId() is "+tp.getTraineeId());
                            if(tpdao.getTrainingParticipant(participantId) !=null)
                            tpdao.updateTrainingParticipant(tp);
                            else
                            tpdao.saveTrainingParticipant(tp);
                        }
                        list.add(tp);
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
        
    return resultList;
}
    public List importTrainingCategory()
    {
        List resultList=new ArrayList();
        List subList=new ArrayList();
        List<TrainingCategory> list=new ArrayList<TrainingCategory>();
        String filePath=getTrainingMetadataXmlFolderPathFile()+"TrainingCategory"+appUtil.getFileSeperator();
        //appUtil.createDirectories(filePath);
        try
        {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            File file=null;
            Document doc = null;
            String fileName=filePath+"TrainingCategory.xml";
            //System.err.println("fileName is "+fileName);
                file=new File(fileName);
                if(!file.exists())
                {
                    return resultList;
                }
                TrainingCategoryDao tcdao=new TrainingCategoryDaoImpl();
                doc = docBuilder.parse (file);
                // normalize text representation
                doc.getDocumentElement().normalize();
                NodeList listOfObjects = doc.getElementsByTagName("datavalue");
                for(int s=0; s<listOfObjects.getLength() ; s++)
                {

                    TrainingCategory tc=new TrainingCategory();
                    Node firstPersonNode = listOfObjects.item(s);
                    //System.err.println("firstPersonNode.getNodeType() is "+firstPersonNode.getNodeType()+" Node.ELEMENT_NODE = "+Node.ELEMENT_NODE);
                    if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                     {
                        //System.err.println("firstPersonNode.getNodeType()2 is "+firstPersonNode.getNodeType()+" Node.ELEMENT_NODE2 = "+Node.ELEMENT_NODE);
                        tc.setCategoryId(XmlProcessor.getNodeValue("categoryId",s,listOfObjects));
                        tc.setCategoryName(XmlProcessor.getNodeValue("categoryName",s,listOfObjects));
                        if(tc.getCategoryId() !=null && !tc.getCategoryId().equalsIgnoreCase("none") && tc.getCategoryName() !=null)
                        { 
                            //System.err.println("tc.getCategoryId() is "+tc.getCategoryId());
                            if(tcdao.getTrainingCategory(tc.getCategoryId()) !=null)
                            tcdao.updateTrainingCategory(tc);
                            else
                            tcdao.saveTrainingCategory(tc);
                        }
                        list.add(tc);
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
    return resultList;
}
public List importTrainingDesignation()
    {
        List<TrainingDesignation> list=new ArrayList<TrainingDesignation>();
        String filePath=getTrainingMetadataXmlFolderPathFile()+"TrainingDesignation"+appUtil.getFileSeperator();
        List resultList=new ArrayList();
        //appUtil.createDirectories(filePath);
        try
        {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            File file=null;
            Document doc = null;
            String fileName=filePath+"TrainingDesignation.xml";
            //System.err.println("fileName is "+fileName);
                file=new File(fileName);
                if(!file.exists())
                {
                    return resultList;
                }
                TrainingDesignationDao tddao=new TrainingDesignationDaoImpl();
                doc = docBuilder.parse (file);
                // normalize text representation
                doc.getDocumentElement().normalize();
                NodeList listOfObjects = doc.getElementsByTagName("datavalue");
                String id=null;
                for(int s=0; s<listOfObjects.getLength() ; s++)
                {
                    TrainingDesignation td=new TrainingDesignation();
                    Node firstPersonNode = listOfObjects.item(s);
                    if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                     {
                        
                        td.setCategoryId(XmlProcessor.getNodeValue("categoryId",s,listOfObjects));
                        td.setDesignationId(XmlProcessor.getNodeValue("designationId",s,listOfObjects));
                        td.setDesignationName(XmlProcessor.getNodeValue("designationName",s,listOfObjects));
                        id=td.getDesignationId();
                        if(id !=null && !id.equalsIgnoreCase("none") && td.getDesignationName() !=null)
                        {
                            //System.err.println("td.setDesignationId is "+id+" "+td.getDesignationName());
                            if(tddao.getTrainingDesignation(id) !=null)
                            tddao.updateTrainingDesignation(td);
                            else
                            tddao.saveTrainingDesignation(td);
                        }
                        list.add(td);
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
    return resultList;
}
public List importTrainingStatusSetup()
    {
        List<TrainingStatusSetup> list=new ArrayList<TrainingStatusSetup>();
        String filePath=getTrainingMetadataXmlFolderPathFile()+"TrainingStatusSetup/";
        List resultList=new ArrayList();
        try
        {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            File file=null;
            Document doc = null;
            String fileName=filePath+"TrainingStatusSetup.xml";
            //System.err.println("fileName is "+fileName);
                file=new File(fileName);
                if(!file.exists())
                {
                    return resultList;
                }
                TrainingStatusSetupDao tcdao=new TrainingStatusSetupDaoImpl();
                doc = docBuilder.parse (file);
                // normalize text representation
                String id=null;
                doc.getDocumentElement().normalize();
                NodeList listOfObjects = doc.getElementsByTagName("datavalue");
                for(int s=0; s<listOfObjects.getLength() ; s++)
                {
                    TrainingStatusSetup tc=new TrainingStatusSetup();
                    Node firstPersonNode = listOfObjects.item(s);
                    //System.err.println("firstPersonNode.getNodeType() in importTrainingStatusSetup() is "+firstPersonNode.getNodeType()+" == "+Node.ELEMENT_NODE);
                    if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                     {
                        tc.setStatusId(XmlProcessor.getNodeValue("statusId",s,listOfObjects));
                        tc.setTrainingStatusName(XmlProcessor.getNodeValue("trainingStatusName",s,listOfObjects));
                        id=tc.getStatusId();
                        //System.err.println("id is "+id);
                        if(id !=null && !id.equalsIgnoreCase("none") && tc.getTrainingStatusName()!=null)
                        {
                            //System.err.println("tc.getTrainingStatusName() is "+tc.getTrainingStatusName());
                            if(tcdao.getTrainingStatusSetup(id) !=null)
                            tcdao.updateTrainingStatusSetup(tc);
                            else
                            tcdao.saveTrainingStatusSetup(tc);
                        }
                        list.add(tc);
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
    return resultList;
}
public List importTrainingInformationSetup()
    {
        List<TrainingInformationSetup> list=new ArrayList<TrainingInformationSetup>();
        String filePath=getTrainingMetadataXmlFolderPathFile()+"TrainingInformationSetup/";
        List resultList=new ArrayList();
        try
        {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            File file=null;
            Document doc = null;
            String fileName=filePath+"TrainingInformationSetup.xml";
            //System.err.println("fileName is "+fileName);
                file=new File(fileName);
                if(!file.exists())
                {
                    return null;
                }
                TrainingInformationSetupDao tisdao=new TrainingInformationSetupDaoImpl();
                doc = docBuilder.parse (file);
                String id=null;
                // normalize text representation
                doc.getDocumentElement().normalize();
                NodeList listOfObjects = doc.getElementsByTagName("datavalue");
                for(int s=0; s<listOfObjects.getLength() ; s++)
                {
                    TrainingInformationSetup tis=new TrainingInformationSetup();
                    Node firstPersonNode = listOfObjects.item(s);
                    if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                     {
                        tis.setRecordId(XmlProcessor.getNodeValue("recordId",s,listOfObjects));
                        tis.setTrainingName(XmlProcessor.getNodeValue("trainingName",s,listOfObjects));
                        id=tis.getRecordId();
                        //System.err.println("id is "+id);
                        if(id !=null && !id.equalsIgnoreCase("none") && tis.getTrainingName() !=null)
                        { 
                            //System.err.println("tc.getTrainingName() is "+tis.getTrainingName());
                            if(tisdao.getTrainingInformationSetup(tis.getRecordId()) !=null)
                            tisdao.updateTrainingInformationSetup(tis);
                            else
                            tisdao.saveTrainingInformationSetup(tis);
                        }
                        list.add(tis);
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
    return resultList;
}
}
