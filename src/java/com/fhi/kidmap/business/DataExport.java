/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.business;
import ImportExport.DatabaseExport;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import java.io.*;
import java.util.List;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
/**
 *
 * @author smomoh
 */

public class DataExport implements Serializable
{
	public DataExport(String reqParam)
	{
            //DatabaseExport dbe=new DatabaseExport();
		try
		{
                    new DatabaseExport();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

public void createXmlTree(String reqParam) throws Exception
{
    String[] params=reqParam.split(":");
    int count=Integer.parseInt(params[0]);
    int startSize=Integer.parseInt(params[1]);
    int loopSize=Integer.parseInt(params[2]);
    OvcDao dao=new OvcDaoImpl();
    List list=dao.getOvcList();
	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
                //creating a new instance of a DOM to build a DOM tree.
        Document doc = docBuilder.newDocument();
        //This method creates an element node
      String[] columnNames={"ovcId","state","lga","completedbyCbo","ward","dateEnrollment","lastName","firstName","age","ageUnit","address","phone","hivStatus","eligibility","gender","birthCertificate","schoolStatus","schoolType","schoolName","currentClass","orphanage","orphanageName","weight","height","caregiverName","caregiverGender","caregiverAge","caregiveraddress","caregiverPhone","caregiverOccupation","caregiverRelationship","completedByName","completedByDesignation","verifiedBy","serviceEnrollment"};

      Element root = doc.createElement("Enrollment");
      doc.appendChild(root);
	Element patient=null;
	Element child=null;
	Text text=null;

        int ovcCount=list.size();
        //int fileCount=(int)Math.ceil((ovcCount/2000));
        //fileCount=3;
	
      //adding a node after the last child node of the specified node.
//for(int k=0; k<fileCount; k++)
//{
    int loopCount=loopSize;
    /*if(ovcCount >2000)
    {
        loopCount=2000;
        ovcCount-=2000;
    }
    else
    {
        loopCount=ovcCount;
    }*/
       for(int i=startSize; i<loopCount; i++)
       {
           patient = doc.createElement("patient");
           root.appendChild(patient);
          Ovc ovc=(Ovc)list.get(i);

          String ovcId=ovc.getOvcId();
          if(ovcId==null)
          ovcId=" ";
          String state=ovc.getState();
          if(state==null)
          state=" ";
          String lga=ovc.getLga();
          if(lga==null)
          lga=" ";
          String completedbyCbo=ovc.getCompletedbyCbo();
          if(completedbyCbo==null)
          completedbyCbo=" ";
          String ward=ovc.getWard();
          if(ward==null)
          ward=" ";
          String dateEnrollment=ovc.getDateEnrollment();
          if(dateEnrollment==null)
          dateEnrollment=" ";
          String firstName=ovc.getFirstName();
          if(firstName==null)
          firstName=" ";
          String lastName=ovc.getLastName();
          if(lastName==null)
          lastName=" ";
          String age=new Integer(ovc.getAge()).toString();
          if(age==null)
          age=" ";
          String ageUnit=ovc.getAgeUnit();
          if(ageUnit==null)
          ageUnit=" ";
          String address=ovc.getAddress();
          if(address==null)
          address=" ";
          String phone=ovc.getPhone();
          if(phone==null)
          phone=" ";
          String hivStatus=ovc.getHivStatus();
          if(hivStatus==null)
          hivStatus=" ";
          String eligibility=ovc.getEligibility();
          if(eligibility==null)
          eligibility=" ";
          String gender=ovc.getGender();
          if(gender==null)
          gender=" ";
          String birthCertificate=ovc.getBirthCertificate();
          if(birthCertificate==null)
          birthCertificate=" ";
          String schoolStatus=ovc.getSchoolStatus();
          if(schoolStatus==null)
          schoolStatus=" ";
          String schoolType=ovc.getSchoolType();
          if(schoolType==null)
          schoolType=" ";
          String schoolName=ovc.getSchoolName();
          if(schoolName==null)
          schoolName=" ";
          String currentClass=ovc.getCurrentClass();
          if(currentClass==null)
          currentClass=" ";
          String orphanage=ovc.getOrphanage();
          if(orphanage==null)
          orphanage=" ";
          String orphanageName=ovc.getOrphanageName();
          if(orphanageName==null)
          orphanageName=" ";
          String weight=new Double(ovc.getWeight()).toString();
          if(weight==null)
          weight=" ";
          String height=new Double(ovc.getHeight()).toString();
          if(height==null)
          height=" ";
          String caregiverName=ovc.getCaregiverName();
          if(caregiverName==null)
          caregiverName=" ";
          String caregiverGender=ovc.getCaregiverGender();
          if(caregiverGender==null)
          caregiverGender=" ";
          String caregiverAge=new Integer(ovc.getCaregiverAge()).toString();
          if(caregiverAge==null)
          caregiverAge=" ";
          String caregiveraddress=ovc.getCaregiverAddress();
          if(caregiveraddress==null)
          caregiveraddress=" ";
          String caregiverPhone=ovc.getCaregiverPhone();
          if(caregiverPhone==null)
          caregiverPhone=" ";
          String caregiverOccupation=ovc.getCaregiverOccupation();
          if(caregiverOccupation==null)
          caregiverOccupation=" ";
          String caregiverRelationship=ovc.getCaregiverRelationships();
          if(caregiverRelationship==null)
          caregiverRelationship=" ";
          String completedByName=ovc.getCompletedbyName();
          if(completedByName==null)
          completedByName=" ";
          String completedByDesignation=ovc.getCompletedbyDesignation();
          if(completedByDesignation==null)
          completedByDesignation=" ";
          String verifiedBy=ovc.getVerifiedBy();
          if(verifiedBy==null)
          verifiedBy=" ";
          String serviceEnrollment=ovc.getServiceEnrollment();
          if(serviceEnrollment==null)
          serviceEnrollment=" ";

          String[] fieldValues={ovcId,state,lga,completedbyCbo,ward,dateEnrollment,lastName,firstName,age,ageUnit,address,phone,hivStatus,eligibility,gender,birthCertificate,schoolStatus,schoolType,schoolName,currentClass,orphanage,orphanageName,weight,height,caregiverName,caregiverGender,caregiverAge,caregiveraddress,caregiverPhone,caregiverOccupation,caregiverRelationship,completedByName,completedByDesignation,verifiedBy,serviceEnrollment};
	

          for(int j=0; j<columnNames.length; j++)
          {
            child = doc.createElement(columnNames[j]);
            patient.appendChild(child);
            text = doc.createTextNode(fieldValues[j]);
            child.appendChild(text);
          }
        fieldValues=null;
      }
        //TransformerFactory instance is used to create Transformer objects.
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        // create string from xml tree
        StringWriter sw = new StringWriter();
	StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);
        String xmlString = sw.toString();
        File file = new File("c:/DatabaseExport/enrollment"+count+".xml");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        bw.write(xmlString);
        bw.flush();
        bw.close();
        //transformer=null;
        //file=null;
        //source=null;
        
        /*Runtime r=Runtime.getRuntime();
        r.gc();
        System.gc();*/
  
    //}
}
}
