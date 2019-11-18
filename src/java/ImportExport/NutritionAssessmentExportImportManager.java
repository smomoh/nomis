/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ImportExport;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.NutritionAssessment;
import com.fhi.kidmap.dao.NutritionAssessmentDao;
import com.fhi.kidmap.dao.NutritionAssessmentDaoImpl;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class NutritionAssessmentExportImportManager implements Serializable
{
    static AppUtility appUtil=new AppUtility();
    public NutritionAssessmentExportImportManager()
    {
        
    }
    public static List readNutritionAssessmentFromXml(int dataUploadId, String parentFolderPath)
    {
        List list=new ArrayList();
        List resultList=new ArrayList();
        List subList=new ArrayList();
        String exportFileName="NutritionAssessment";
        String filePath=parentFolderPath;
        if(parentFolderPath==null)
        filePath=appUtil.getExportFilePath()+appUtil.getFileSeperator()+exportFileName+appUtil.getFileSeperator();
        //String filePath=appUtil.getExportFilePath()+appUtil.getFileSeperator()+exportFileName+appUtil.getFileSeperator();
           try
            {
                    int count=0;
                    int totalCount=0;
                    NutritionAssessmentDao dao=new NutritionAssessmentDaoImpl();
                    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                    File file=null;
                    Document doc = null;
                    String fileName=filePath+exportFileName+".xml";
                    int t=0;
                    int i=1;
                    list.clear();
                    while(i>0)
                    {
                        if(t>0)
                        fileName=filePath+exportFileName+t+".xml";
                        file=new File(fileName);
                        if(!file.exists())
                        {
                            i=0;
                            break;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize(); //doc.g
                        NodeList listOfObjects = doc.getElementsByTagName("patient");
                        NutritionAssessment na=null;
                    for(int s=0; s<listOfObjects.getLength() ; s++)
                    {
                        na=new NutritionAssessment();
                        Node firstNode = listOfObjects.item(s);
                         if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                         {
                            na.setOvcId(getNodeValue("ovcId",s,listOfObjects));
                            na.setAssessmentNo(Integer.parseInt(getNodeValue("assessmentNo",s,listOfObjects)));
                            
                            double bmi=getBmiAsDouble(getNodeValue("bmi",s,listOfObjects));
                            na.setBmi(bmi+"");
                            if(getNodeName("bodyMassIndex",s,listOfObjects) !=null)
                            na.setBodyMassIndex(Double.parseDouble(getNodeValue("bodyMassIndex",s,listOfObjects)));
                            else
                            na.setBodyMassIndex(bmi);
                            na.setChangeInWeight(getNodeValue("changeInWeight",s,listOfObjects));
                            na.setDateModified(getNodeValue("dateModified",s,listOfObjects));
                            na.setDateOfAssessment(getNodeValue("dateOfAssessment",s,listOfObjects));
                            na.setDateOfLastWeight(getNodeValue("dateOfLastWeight",s,listOfObjects));
                            na.setFoodSecurityAndDietQ1(getNodeValue("foodSecurityAndDietQ1",s,listOfObjects));
                            na.setFoodSecurityAndDietQ2(getNodeValue("foodSecurityAndDietQ2",s,listOfObjects));
                            na.setFoodSecurityAndDietQ3(getNodeValue("foodSecurityAndDietQ3",s,listOfObjects));
                            na.setFoodSecurityAndDietQ4(getNodeValue("foodSecurityAndDietQ4",s,listOfObjects));
                            na.setFoodSecurityAndDietQ5(getNodeValue("foodSecurityAndDietQ5",s,listOfObjects));
                            na.setFoodSecurityAndDietQ6(getNodeValue("foodSecurityAndDietQ6",s,listOfObjects));
                            na.setFoodSecurityAndDietQ7(getNodeValue("foodSecurityAndDietQ7",s,listOfObjects));
                            na.setFoodSecurityAndDietQ8(getNodeValue("foodSecurityAndDietQ8",s,listOfObjects));
                            na.setFoodSecurityAndDietQ9(getNodeValue("foodSecurityAndDietQ9",s,listOfObjects));
                            na.setHeight(Double.parseDouble(getNodeValue("height",s,listOfObjects)));
                            na.setHygieneQ1(getNodeValue("hygieneQ1",s,listOfObjects));
                            na.setHygieneQ2(getNodeValue("hygieneQ2",s,listOfObjects));
                            na.setHygieneQ3(getNodeValue("hygieneQ3",s,listOfObjects));
                            na.setHygieneQ4(getNodeValue("hygieneQ4",s,listOfObjects));
                            na.setLastWeight(Double.parseDouble(getNodeValue("lastWeight",s,listOfObjects)));
                            na.setMedicalEvaluationDiarrhea(getNodeValue("medicalEvaluationDiarrhea",s,listOfObjects));
                            na.setMedicalEvaluationHIV(getNodeValue("medicalEvaluationHIV",s,listOfObjects));
                            na.setMedicalEvaluationMouthSoars(getNodeValue("medicalEvaluationMouthSoars",s,listOfObjects));
                            na.setMedicalEvaluationNausea(getNodeValue("medicalEvaluationNausea",s,listOfObjects));
                            na.setMedicalEvaluationPainfulChewing(getNodeValue("medicalEvaluationPainfulChewing",s,listOfObjects));
                            na.setMedicalEvaluationPoorApetite(getNodeValue("medicalEvaluationPoorApetite",s,listOfObjects));
                            na.setMedicalEvaluationVomiting(getNodeValue("medicalEvaluationVomiting",s,listOfObjects));
                            na.setMuac(getNodeValue("muac",s,listOfObjects));
                            na.setNutritionalStatus(getNodeValue("nutritionalStatus",s,listOfObjects));
                            na.setOedema(getNodeValue("oedema",s,listOfObjects));
                            na.setRecordedBy(getNodeValue("recordedBy",s,listOfObjects));
                            na.setWeight(Double.parseDouble(getNodeValue("weight",s,listOfObjects)));
                            if(getNodeName("muacFacility",s,listOfObjects) !=null)
                            na.setMuacFacility(getNodeValue("muacFacility",s,listOfObjects));
                            if(getNodeName("yellowMuacServices",s,listOfObjects) !=null)
                            na.setYellowMuacServices(getNodeValue("yellowMuacServices",s,listOfObjects));
                            //if(na.getDateOfAssessment()==null || na.getDateOfLastWeight()==null || na.getDateModified()==null)
                            //continue;
                            NutritionAssessment na2=dao.getNutritionAssessment(na.getOvcId(), na.getDateOfAssessment());
                            if(na2==null)
                            {
                                dao.saveNutritionAssessment(na);
                                count++;
                                AppUtility.setCurrentImportRecordName("Household Referral record: "+count+" of "+totalCount+" saved");
                            }
                            else
                            {
                                na.setId(na2.getId());
                                dao.updateNutritionAssessment(na);
                                count++;
                                AppUtility.setCurrentImportRecordName("Household Referral record: "+count+" of "+totalCount+" saved");
                            }
                            //list.add(na);
                         }
                    }
                        t++;
                    }
                    subList.add("Number of Nutritional assessment records");
                    subList.add(totalCount);
                    resultList.add(subList);
                    //createSavableNutritionAssessmentList(session,list);
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
    public static List readNutritionAssessmentFromXml(HttpSession session)
    {
        List list=new ArrayList();
        String exportFileName="NutritionAssessment";
        String filePath=appUtil.getExportFilePath()+appUtil.getFileSeperator()+exportFileName+appUtil.getFileSeperator();
           try
            {
                    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                    File file=null;
                    Document doc = null;
                    String fileName=filePath+exportFileName+".xml";
                    int t=0;
                    int i=1;
                    list.clear();
                    while(i>0)
                    {
                        if(t>0)
                        fileName=filePath+exportFileName+t+".xml";
                        file=new File(fileName);
                        if(!file.exists())
                        {
                            i=0;
                            break;
                        }
                        doc = docBuilder.parse (file);
                        // normalize text representation
                        doc.getDocumentElement().normalize(); //doc.g
                        NodeList listOfObjects = doc.getElementsByTagName("patient");
                        NutritionAssessment na=null;
                    for(int s=0; s<listOfObjects.getLength() ; s++)
                    {
                        na=new NutritionAssessment();
                        Node firstNode = listOfObjects.item(s);
                         if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                         {
                            na.setOvcId(getNodeValue("ovcId",s,listOfObjects));
                            na.setAssessmentNo(Integer.parseInt(getNodeValue("assessmentNo",s,listOfObjects)));
                            
                            double bmi=getBmiAsDouble(getNodeValue("bmi",s,listOfObjects));
                            na.setBmi(bmi+"");
                            if(getNodeName("bodyMassIndex",s,listOfObjects) !=null)
                            na.setBodyMassIndex(Double.parseDouble(getNodeValue("bodyMassIndex",s,listOfObjects)));
                            else
                            na.setBodyMassIndex(bmi);
                            na.setChangeInWeight(getNodeValue("changeInWeight",s,listOfObjects));
                            na.setDateModified(getNodeValue("dateModified",s,listOfObjects));
                            na.setDateOfAssessment(getNodeValue("dateOfAssessment",s,listOfObjects));
                            na.setDateOfLastWeight(getNodeValue("dateOfLastWeight",s,listOfObjects));
                            na.setFoodSecurityAndDietQ1(getNodeValue("foodSecurityAndDietQ1",s,listOfObjects));
                            na.setFoodSecurityAndDietQ2(getNodeValue("foodSecurityAndDietQ2",s,listOfObjects));
                            na.setFoodSecurityAndDietQ3(getNodeValue("foodSecurityAndDietQ3",s,listOfObjects));
                            na.setFoodSecurityAndDietQ4(getNodeValue("foodSecurityAndDietQ4",s,listOfObjects));
                            na.setFoodSecurityAndDietQ5(getNodeValue("foodSecurityAndDietQ5",s,listOfObjects));
                            na.setFoodSecurityAndDietQ6(getNodeValue("foodSecurityAndDietQ6",s,listOfObjects));
                            na.setFoodSecurityAndDietQ7(getNodeValue("foodSecurityAndDietQ7",s,listOfObjects));
                            na.setFoodSecurityAndDietQ8(getNodeValue("foodSecurityAndDietQ8",s,listOfObjects));
                            na.setFoodSecurityAndDietQ9(getNodeValue("foodSecurityAndDietQ9",s,listOfObjects));
                            na.setHeight(Double.parseDouble(getNodeValue("height",s,listOfObjects)));
                            na.setHygieneQ1(getNodeValue("hygieneQ1",s,listOfObjects));
                            na.setHygieneQ2(getNodeValue("hygieneQ2",s,listOfObjects));
                            na.setHygieneQ3(getNodeValue("hygieneQ3",s,listOfObjects));
                            na.setHygieneQ4(getNodeValue("hygieneQ4",s,listOfObjects));
                            na.setLastWeight(Double.parseDouble(getNodeValue("lastWeight",s,listOfObjects)));
                            na.setMedicalEvaluationDiarrhea(getNodeValue("medicalEvaluationDiarrhea",s,listOfObjects));
                            na.setMedicalEvaluationHIV(getNodeValue("medicalEvaluationHIV",s,listOfObjects));
                            na.setMedicalEvaluationMouthSoars(getNodeValue("medicalEvaluationMouthSoars",s,listOfObjects));
                            na.setMedicalEvaluationNausea(getNodeValue("medicalEvaluationNausea",s,listOfObjects));
                            na.setMedicalEvaluationPainfulChewing(getNodeValue("medicalEvaluationPainfulChewing",s,listOfObjects));
                            na.setMedicalEvaluationPoorApetite(getNodeValue("medicalEvaluationPoorApetite",s,listOfObjects));
                            na.setMedicalEvaluationVomiting(getNodeValue("medicalEvaluationVomiting",s,listOfObjects));
                            na.setMuac(getNodeValue("muac",s,listOfObjects));
                            na.setNutritionalStatus(getNodeValue("nutritionalStatus",s,listOfObjects));
                            na.setOedema(getNodeValue("oedema",s,listOfObjects));
                            na.setRecordedBy(getNodeValue("recordedBy",s,listOfObjects));
                            na.setWeight(Double.parseDouble(getNodeValue("weight",s,listOfObjects)));
                            if(getNodeName("muacFacility",s,listOfObjects) !=null)
                            na.setMuacFacility(getNodeValue("muacFacility",s,listOfObjects));
                            if(getNodeName("yellowMuacServices",s,listOfObjects) !=null)
                            na.setYellowMuacServices(getNodeValue("yellowMuacServices",s,listOfObjects));
                            //if(na.getDateOfAssessment()==null || na.getDateOfLastWeight()==null || na.getDateModified()==null)
                            //continue;
                            list.add(na);
                         }
                    }
                        t++;
                    }
                    createSavableNutritionAssessmentList(session,list);
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
    private static String getNodeName(String value,int s,NodeList listOfObjects)
    {
        String elementName=null;
        Node firstPersonNode = listOfObjects.item(s);
        Element firstPersonElement = (Element)firstPersonNode;
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
        return elementName;
    }
    public static double getBmiAsDouble(String bmiStringValue)
    {
        double bmi=0.0;
        try
        {
            int index=0;
            //String character=bmiStringValue;
            System.err.println("character is "+bmiStringValue);
            if(bmiStringValue ==null || bmiStringValue.equalsIgnoreCase("") || bmiStringValue.equalsIgnoreCase(" ") || bmiStringValue.equalsIgnoreCase("  ") || bmiStringValue.contains(" ") || bmiStringValue.trim().equalsIgnoreCase(","))
            {
                bmi=0;
            }
            else
            {
                if(bmiStringValue.length()>1 && bmiStringValue.contains(","))
                {
                    bmiStringValue=bmiStringValue.replace(",", ".");
                }
                for(int i=0; i<bmiStringValue.length(); i++)
                {
                    //System.err.println("character at "+i+" is "+bmiStringValue.charAt(i));
                    if(Character.isLetter(bmiStringValue.charAt(i)))
                    {
                        index++;
                    }
                }
                if(index>0)
                {
                    //System.err.println("index  is "+index);
                    bmi=0;
                }
                else
                {
                    bmi=Double.parseDouble(bmiStringValue);
                }
            }
        }
        catch(NumberFormatException nfe)
        {
            bmi=0;
        }
        catch(Exception ex)
        {
            bmi=0;
        }
        return bmi;
    }
    private static void createSavableNutritionAssessmentList(HttpSession session,List list)
    {
        NutritionAssessmentDao dao=new NutritionAssessmentDaoImpl();
        List naCountList=new ArrayList();
        List nonDuplicateList=new ArrayList();
        List duplicateList=new ArrayList();
        List savableList=new ArrayList();
        int duplicateCount=0;

        for(Object obj: list)
        {
            NutritionAssessment na=(NutritionAssessment)obj;
            try
            {
                if(na.getDateOfAssessment() ==null || na.getDateOfAssessment().equalsIgnoreCase(""))
                continue;
                NutritionAssessment na2=dao.getNutritionAssessment(na.getOvcId(), na.getDateOfAssessment());
                if(na2==null)
                {
                    nonDuplicateList.add(na);
                }
                else
                {
                    na.setId(na2.getId());
                    duplicateList.add(na);
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
        naCountList.add(list.size());
        naCountList.add(duplicateCount);
        session.setAttribute("savableNutritionAssessmentList", savableList);
        session.setAttribute("nutritionAssessmentBackUpCountList", naCountList);
        duplicateCount=0;
    }
    public static List saveNutritionAssessment(List savableNutritionAssessmentList)
    {
        List resultList=new ArrayList();
        List subList=new ArrayList();
        NutritionAssessmentDao dao=new NutritionAssessmentDaoImpl();
           try
           {
                List list=(List)savableNutritionAssessmentList;
                if(list !=null && !list.isEmpty())
                {
                    List nonDuplicateList=(List)list.get(0);
                    List duplicateList=(List)list.get(1);
                    int count=0;
                    int totalCount=duplicateList.size()+nonDuplicateList.size();
                    subList.add("Number of nutritional assessment records");
                    subList.add(totalCount);
                    resultList.add(subList);
                    for(Object obj: nonDuplicateList)
                    {
                        NutritionAssessment na=(NutritionAssessment)obj;
                        dao.saveNutritionAssessment(na);
                        count++;
                        AppUtility.setCurrentImportRecordName("Household Followup Assessment records: "+count+" of "+totalCount+" saved");
                    }
                    for(Object obj: duplicateList)
                    {
                        NutritionAssessment na2=(NutritionAssessment)obj;
                        dao.updateNutritionAssessment(na2);
                        count++;
                        AppUtility.setCurrentImportRecordName("Household Followup Assessment records: "+count+" of "+totalCount+" saved");
                    }
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        return resultList;
    }
    private static String getNodeValue(String value,int s,NodeList listOfObjects)
    {
        return XmlProcessor.getNodeValue(value,s,listOfObjects);
    }
    private String getAppropriateStringLength(String str,int length)
    {
        XmlProcessor xmlProcessor=new XmlProcessor();
        return xmlProcessor.getAppropriateStringLength(str,length);
    }
}
