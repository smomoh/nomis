/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ImportExport;
import com.fhi.kidmap.business.EligibilityCriteria;
import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.OvcSchool;
import com.fhi.kidmap.business.Partners;
import com.fhi.kidmap.business.ReferralDirectory;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.business.Wards;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DataEncryption;
import com.fhi.kidmap.business.ZipHandler;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.helpers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.sax.*;
/**
 *
 * @author smomoh
 */
public class MetaDataExportHandler 
{
    ZipHandler zipHandler;
    DataEncryption encryptor;
    
    
    AppUtility appUtil=new AppUtility();
      String fileSeperator=appUtil.getFileSeperator();
      
public int createStateExportInXml(String parentFolderPath, List list) throws Exception
{
        appUtil.createExportImportDirectories();
        String fileName="";
        String parentFolder="State";

        //String fileSeperator=appUtil.getFileSeperator();
        int noOfRecords=0;
          try
          {
              int startSize=0;
              int count=0;
        
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
                        String[] columnNames={"state_code","name"};
                        atts.clear();
                            hd.startDocument();
                            hd.startElement("","",parentFolder,atts);
                            startSize=count;
                            System.err.println("startSize is "+startSize);
                            for(int j=startSize; j<list.size(); j++)
                            {
                                if(j>((k+1)*4999))
                                break;
                                States state=(States)list.get(j);
                                String state_code=getPropertyValue(state.getState_code());
                                String name=getPropertyValue(state.getName());
                                String[] fieldValues={state_code,name};
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
public int createLgaExportInXml(String parentFolderPath, List list) throws Exception
{
        appUtil.createExportImportDirectories();
        String fileName="";
        String parentFolder="Lga";

        String fileSeperator=appUtil.getFileSeperator();
        int noOfRecords=0;
          try
          {
              int startSize=0;
              int count=0;
        
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
                        String[] columnNames={"lgaCode","lgaName","stateCode","autogeneratedId"};
                        atts.clear();
                            hd.startDocument();
                            hd.startElement("","",parentFolder,atts);
                            startSize=count;
                            System.err.println("startSize is "+startSize);
                            for(int j=startSize; j<list.size(); j++)
                            {
                                if(j>((k+1)*4999))
                                break;
                                Lgas lga=(Lgas)list.get(j);
                                  String autogeneratedId=getIntegerPropertyValue(lga.getAutogenerated()+"");
                                  String lgaCode=getPropertyValue(lga.getLga_code());
                                  String lgaName=getPropertyValue(lga.getLga_name());
                                  String stateCode=getPropertyValue(lga.getState_code());

                                  String[] fieldValues={lgaCode,lgaName,stateCode,autogeneratedId};
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
public int createCBOExportInXml(String parentFolderPath, List list) throws Exception
{
    int noOfRecords=0;
    //appUtil.createExportImportDirectories();  
    String fileName="OrganizationRecords";
    String parentFolder="OrganizationRecords";
    String fileSeperator=appUtil.getFileSeperator();
      try
      {
          int startSize=0;
        int count=0;
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
       return noOfRecords;
}
public int createWardExportInXml(String parentFolderPath, List list) throws Exception
{
    int noOfRecords=0;
    String parentFolder="Wards";
        //appUtil.createExportImportDirectories();
        String fileName="";
        String[] columnNames={"cbo_code","ward_code","ward_name"};
        int startSize=0;
        int count=0;
      try
      {
        StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	// SAX2.0 ContentHandler.
	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        hd.startElement("","",parentFolder,atts);

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
          hd.startElement("","","elementName",atts);
            for (int i=0;i<columnNames.length;i++)
            {
                hd.startElement("","",columnNames[i],atts);
                hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                hd.endElement("","",columnNames[i]);
            }
          hd.endElement("","","elementName");
	}
	hd.endElement("","",parentFolder);
	hd.endDocument();

	String xmlString = sw.toString();
        fileName="Wards.xml";
        if(count>0)
        fileName="Wards"+count+".xml";
        //File file = new File(appUtil.getExportFilePath()+"/Wards/"+fileName);
        String exportDestination=appUtil.getExportFilePath()+fileSeperator+parentFolder+fileSeperator+fileName;
        if(parentFolderPath !=null)//parentFolderPath
        exportDestination=parentFolderPath+fileName;
        File file = new File(exportDestination);

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
          return noOfRecords;
}
public int createPartnerExportInXml(String parentFolderPath, List list) throws Exception
{
        appUtil.createExportImportDirectories();
        String fileName="";
        String parentFolder="Partner";

        String fileSeperator=appUtil.getFileSeperator();
        int noOfRecords=0;
          try
          {
              int startSize=0;
              int count=0;
        
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
                        String[] columnNames={"partnerCode","partnerName","donorCode"};
                        atts.clear();
                            hd.startDocument();
                            hd.startElement("","",parentFolder,atts);
                            startSize=count;
                            System.err.println("startSize is "+startSize);
                            for(int j=startSize; j<list.size(); j++)
                            {
                                if(j>((k+1)*4999))
                                break;
                                Partners partner=(Partners)list.get(j);
                                  String partnerCode=getPropertyValue(partner.getPartnerCode());
                                  String partnerName=getPropertyValue(partner.getPartnerName());
                                  String donorCode=getPropertyValue(partner.getDonorCode());

                                  String[] fieldValues={partnerCode,partnerName,donorCode};
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
public int createEligibilityCriteriaExportInXml(String parentFolderPath, List list) throws Exception
{
        appUtil.createExportImportDirectories();
        String fileName="";
        String parentFolder="EligibilityCriteria";

        String fileSeperator=appUtil.getFileSeperator();
        int noOfRecords=0;
          try
          {
              int startSize=0;
              int count=0;
        
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
                        String[] columnNames={"id","code","eligibilityName"};
                        atts.clear();
                            hd.startDocument();
                            hd.startElement("","",parentFolder,atts);
                            startSize=count;
                            System.err.println("startSize is "+startSize);
                            for(int j=startSize; j<list.size(); j++)
                            {
                                if(j>((k+1)*4999))
                                break;
                                EligibilityCriteria criteria=(EligibilityCriteria)list.get(j);
                                  String code=getPropertyValue(criteria.getCode());
                                  String criteriaName=getPropertyValue(criteria.getEligibilityName());
                                  String id=getIntegerPropertyValue(criteria.getId()+"");

                                  String[] fieldValues={id,code,criteriaName};
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
public int createSchoolExportInXml(String parentFolderPath, List list) throws Exception
{
        appUtil.createExportImportDirectories();
        String fileName="";
        String parentFolder="School";

        String fileSeperator=appUtil.getFileSeperator();
        int noOfRecords=0;
          try
          {
              int startSize=0;
              int count=0;
        
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
                        String[] columnNames={"id","code","state","lga","type","name","address"};
                        atts.clear();
                            hd.startDocument();
                            hd.startElement("","",parentFolder,atts);
                            startSize=count;
                            System.err.println("startSize is "+startSize);
                            for(int j=startSize; j<list.size(); j++)
                            {
                                if(j>((k+1)*4999))
                                break;
                                OvcSchool school=(OvcSchool)list.get(j);
                                String id=getIntegerPropertyValue(school.getId()+"");
                                String code=getPropertyValue(school.getCode());
                                String state=getPropertyValue(school.getState());
                                String lga=getPropertyValue(school.getLga());
                                String type=getPropertyValue(school.getType());
                                String name=getPropertyValue(school.getName());
                                String address=getPropertyValue(school.getAddress());
                                
                                  String[] fieldValues={id,code,state,lga,type,name,address};
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
public int createReferralDirectoryExportInXml(String parentFolderPath, List list) throws Exception
{
        appUtil.createExportImportDirectories();
        int noOfRecords=0;
        String fileName="";
    String parentFolder="ReferralDirectory";
    //appUtil.createExportImportDirectories();
    String fileSeperator=appUtil.getFileSeperator();
      try
      {
          int startSize=0;
        int count=0;
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
       return noOfRecords;
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
}
