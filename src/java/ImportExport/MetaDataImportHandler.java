/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ImportExport;
import com.fhi.kidmap.business.EligibilityCriteria;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.OvcSchool;
import com.fhi.kidmap.business.Partners;
import com.fhi.kidmap.business.ReferralDirectory;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.business.Wards;
import com.fhi.nomis.nomisutils.DataEncryption;
import com.fhi.kidmap.business.ZipHandler;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.EligibilityCriteriaDao;
import com.fhi.kidmap.dao.LgaDao;
import com.fhi.kidmap.dao.OrganizationRecordsDao;
import com.fhi.kidmap.dao.OrganizationRecordsDaoImpl;
import com.fhi.kidmap.dao.OvcSchoolDao;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.ReferralDirectoryDao;
import com.fhi.kidmap.dao.ReferralDirectoryDaoImpl;
import com.fhi.kidmap.dao.StatesDao;
import com.fhi.kidmap.dao.WardDao;
import com.fhi.kidmap.dao.WardDaoImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
/**
 *
 * @author smomoh
 */
public class MetaDataImportHandler 
{
    ZipHandler zipHandler;
    DataEncryption encryptor;
    AppUtility appUtil=new AppUtility();
    DaoUtil util=new DaoUtil();
public List importStateRecordsFromXml()
{
    List list=new ArrayList();
    String fileSeperator=appUtil.getFileSeperator();
    String exportFileName="State";
    AppUtility.setCurrentImportRecordName("State");
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    newRecordsList.add("Number of new State records saved");
    duplicateRecordsList.add("Number of State records saved as updates");
    int numberSaved=0;
    int numberUpdated=0;
    String filePath=appUtil.getMetadataDirectoryPath()+fileSeperator+exportFileName+fileSeperator;
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
                    StatesDao sdao=util.getStateDaoInstance();
                    States state=null;
                    States existingState=null;
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
                            state=new States();
                            Node firstNode = listOfObjects.item(s);
                             if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                             {                              
                                state.setState_code(getNodeValue("state_code",s,listOfObjects));
                                state.setName(getNodeValue("name",s,listOfObjects));
                                                                                               
                                existingState=sdao.getStateByStateCode(state.getState_code());
                                if(existingState==null)
                                {
                                    sdao.saveState(state);
                                    numberSaved++;
                                }
                                else
                                {
                                    sdao.updateState(state);
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
public List importLgaRecordsFromXml()
{
    List list=new ArrayList();
    String fileSeperator=appUtil.getFileSeperator();
    String exportFileName="Lga";
    AppUtility.setCurrentImportRecordName("LGA");
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    newRecordsList.add("Number of new LGA records saved");
    duplicateRecordsList.add("Number of LGA records saved as updates");
    int numberSaved=0;
    int numberUpdated=0;
    String filePath=appUtil.getMetadataDirectoryPath()+fileSeperator+exportFileName+fileSeperator;
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
                    LgaDao lgadao=util.getLgaDaoInstance();
                    Lgas lga=null;
                    Lgas existingLga=null;
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
                            lga=new Lgas();
                            Node firstNode = listOfObjects.item(s);
                             if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                             {                              
                                lga.setAutogenerated(getNodeValue("autogeneratedId",s,listOfObjects));
                                lga.setLga_code(getNodeValue("lgaCode",s,listOfObjects));
                                lga.setLga_name(getNodeValue("lgaName",s,listOfObjects));
                                lga.setState_code(getNodeValue("stateCode",s,listOfObjects));
                                                               
                                existingLga=lgadao.getLgaByCode(lga.getLga_code());
                                if(existingLga==null)
                                {
                                    lgadao.saveLga(lga);
                                    numberSaved++;
                                }
                                else
                                {
                                    lgadao.updateLga(lga);
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
public List importCBORecordsFromXml()
{
    List list=new ArrayList();
    String fileSeperator=appUtil.getFileSeperator();
    String exportFileName="OrganizationRecords";
    //String filePath=appUtil.getExportFilePath()+"\\OrganizationRecords\\";
    String filePath=appUtil.getMetadataDirectoryPath()+fileSeperator+exportFileName+fileSeperator;
    AppUtility.setCurrentImportRecordName("CBO Setup records");
    System.err.println("filePath in importCBORecordsFromXml() is "+filePath);
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
		String fileName=filePath+exportFileName+".xml";
                List files=getFiles(filePath);
                String tagName="elementName";
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
                                    System.err.println("orgRecords with name "+orgRecords.getOrgName()+" saved");
                                }
                                else
                                {
                                    ordao.updateOrgRecords(orgRecords);
                                    numberUpdated++;
                                    System.err.println("orgRecords with name "+orgRecords.getOrgName()+" updated");
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
public List importParnerRecordsFromXml()
{
    List list=new ArrayList();
    String fileSeperator=appUtil.getFileSeperator();
    String exportFileName="Partner";
    AppUtility.setCurrentImportRecordName(exportFileName);
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    newRecordsList.add("Number of new partner records saved");
    duplicateRecordsList.add("Number of partner records saved as updates");
    int numberSaved=0;
    int numberUpdated=0;
    String filePath=appUtil.getMetadataDirectoryPath()+fileSeperator+exportFileName+fileSeperator;
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
                PartnersDao pdao=util.getPartnerDaoInstance();;
                Partners partner=null;
                Partners existingPartner=null;
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
                        partner=new Partners();
                        Node firstNode = listOfObjects.item(s);
                         if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                         {                              
                            partner.setPartnerCode(getNodeValue("partnerCode",s,listOfObjects));
                            partner.setPartnerName(getNodeValue("partnerName",s,listOfObjects));
                            partner.setDonorCode(getNodeValue("donorCode",s,listOfObjects));

                            existingPartner=pdao.getPartner(partner.getPartnerCode());
                            if(existingPartner==null)
                            {
                                pdao.savePartners(partner);
                                numberSaved++;
                            }
                            else
                            {
                                pdao.updatePartners(partner);
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
public List importSchoolRecordsFromXml()
{
    List list=new ArrayList();
    String fileSeperator=appUtil.getFileSeperator();
    String exportFileName="School";
    AppUtility.setCurrentImportRecordName(exportFileName);
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    newRecordsList.add("Number of new school records saved");
    duplicateRecordsList.add("Number of school records saved as updates");
    int numberSaved=0;
    int numberUpdated=0;
    String filePath=appUtil.getMetadataDirectoryPath()+fileSeperator+exportFileName+fileSeperator;
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
                OvcSchoolDao sdao=util.getOvcSchoolDaoInstance();
                OvcSchool school=null;
                OvcSchool existingSchool=null;
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
                        school=new OvcSchool();
                        Node firstNode = listOfObjects.item(s);
                         if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                         {   
                            school.setId(Integer.parseInt(getNodeValue("id",s,listOfObjects)));
                            school.setCode(getNodeValue("code",s,listOfObjects));
                            school.setState(getNodeValue("state",s,listOfObjects));
                            school.setLga(getNodeValue("lga",s,listOfObjects));
                            school.setType(getNodeValue("type",s,listOfObjects));
                            school.setName(getNodeValue("name",s,listOfObjects));
                            school.setAddress(getNodeValue("address",s,listOfObjects));
                            existingSchool=sdao.getSchool(school.getName());
                            if(existingSchool==null)
                            {
                                sdao.saveOvcSchool(school);
                                numberSaved++;
                            }
                            else
                            {
                                school.setId(existingSchool.getId());
                                sdao.updateOvcSchool(school);
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
public List importEligibilityCriteriaRecordsFromXml()
{
    List list=new ArrayList();
    String fileSeperator=appUtil.getFileSeperator();
    String exportFileName="EligibilityCriteria";
    AppUtility.setCurrentImportRecordName(exportFileName);
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    newRecordsList.add("Number of new Vulnerability status records saved");
    duplicateRecordsList.add("Number of Vulnerability status records saved as updates");
    int numberSaved=0;
    int numberUpdated=0;
    String filePath=appUtil.getMetadataDirectoryPath()+fileSeperator+exportFileName+fileSeperator;
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
                EligibilityCriteriaDao eldao=util.getEligibilityCriteriaDaoInstance();
                EligibilityCriteria criteria=null;
                EligibilityCriteria existingCriteria=null;
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
                        criteria=new EligibilityCriteria();
                        Node firstNode = listOfObjects.item(s);
                         if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                         {   
                            criteria.setId(Integer.parseInt(getNodeValue("id",s,listOfObjects)));
                            criteria.setCode(getNodeValue("code",s,listOfObjects));
                            criteria.setEligibilityName(getNodeValue("eligibilityName",s,listOfObjects));
                            existingCriteria=eldao.getEligibilityCriteria(criteria.getEligibilityName());
                            if(existingCriteria==null)
                            {
                                eldao.saveEligibilityCriteria(criteria);
                                numberSaved++;
                            }
                            else
                            {
                                criteria.setId(existingCriteria.getId());
                                eldao.updateEligibilityCriteria(criteria);
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
public List importWardRecordsFromXml()
{
    List list=new ArrayList();
    String fileSeperator=appUtil.getFileSeperator();
    String exportFileName="Wards";
    AppUtility.setCurrentImportRecordName(exportFileName);
    List newRecordsList=new ArrayList();
    List duplicateRecordsList=new ArrayList();
    newRecordsList.add("Number of new ward records saved");
    duplicateRecordsList.add("Number of ward records saved as updates");
    int numberSaved=0;
    int numberUpdated=0;
    String filePath=appUtil.getMetadataDirectoryPath()+fileSeperator+exportFileName+fileSeperator;
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
            WardDao wdao=new WardDaoImpl();
            Wards ward=null;
            Wards existingWard=null;
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
                NodeList listOfObjects = doc.getElementsByTagName("elementName");

                for(int s=0; s<listOfObjects.getLength() ; s++)
                {
                    ward=new Wards();
                    Node firstNode = listOfObjects.item(s);
                     if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                     {   
                        String wardCode=getNodeValue("ward_code",s,listOfObjects);
                        ward.setWard_code(wardCode);
                        ward.setWard_name(getNodeValue("ward_name",s,listOfObjects));
                        ward.setCbo_code(getNodeValue("cbo_code",s,listOfObjects));
                        existingWard=wdao.getWards(wardCode);
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
public List importReferralDirectoryRecordsFromXml()
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
    String filePath=appUtil.getMetadataDirectoryPath()+fileSeperator+exportFileName+fileSeperator;
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
            ReferralDirectoryDao rddao=new ReferralDirectoryDaoImpl();
            ReferralDirectory rd=null;
            ReferralDirectory existingrd=null;
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
