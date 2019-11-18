/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.dataexchange.pojo;

import com.fhi.nomis.nomisutils.AppUtility;
import com.nomis.dataexchange.business.DataExchangeOrganizationUnit;
import com.nomis.dataexchange.business.DxCategoryCombination;
import com.nomis.dataexchange.business.DxDataElement;
import com.nomis.dataexchange.dao.DxCategoryCombinationDao;
import com.nomis.dataexchange.dao.DxCategoryCombinationDaoImpl;
import com.nomis.dataexchange.dao.DxDataElementDao;
import com.nomis.dataexchange.dao.DxDataElementDaoImpl;
import com.nomis.dataexchange.dao.DxOrganizationUnitDao;
import com.nomis.dataexchange.dao.DxOrganizationUnitDaoImpl;
import java.util.List;
/**
 *
 * @author smomoh
 */
public class XmlExtractor 
{
 
  public void readOrgUnitFromXml(String xmlFilePath,String dhisInstance) 
  {
      AppUtility appUtil=new AppUtility();
      List list=AppUtility.readFiles(xmlFilePath);
      if(list !=null)
      {
          String orgunitName=null;
          String orgunitId=null;
          String parentId=null;
          int level=0;
          DxOrganizationUnitDao oudao=new DxOrganizationUnitDaoImpl();
          DataExchangeOrganizationUnit ou=new DataExchangeOrganizationUnit();
          ou.setInstanceName(dhisInstance);
          //System.out.println("Number of organization unit is "+list.size());
          String str=null;
          try
          {
              for(int i=0; i<list.size(); i++)
              {
                  str=list.get(i).toString();
                  if(str !=null && str.indexOf("<organisationUnit") !=-1)
                  {
                      orgunitName=null;
                      orgunitId=null;
                      parentId=null;
                      level=0;
                      //System.out.println("str at "+i+" is "+str);
                      String[] strArray=str.split("<organisationUnit");
                      for(int j=0; j<strArray.length; j++)
                      {
                          System.out.println("strArray at "+j+" is "+strArray[j]);
                          String[] substrArray=strArray[j].split(">");
                          if(substrArray !=null)
                          {
                              for(int k=0; k<substrArray.length; k++)
                              {
                                  if(substrArray[k] !=null)
                                  {
                                      System.out.println("substrArray at "+k+" is "+substrArray[k]);
                                      //this contains the orgunit name, level and id
                                      if(substrArray[k].indexOf("name=") !=-1 && substrArray[k].indexOf("<parent") ==-1)
                                      {
                                          String nameAndCreated=substrArray[k].substring(substrArray[k].indexOf("name=")+6, substrArray[k].indexOf("created"));
                                          if(nameAndCreated !=null)
                                          {
                                            orgunitName=nameAndCreated.substring(0, nameAndCreated.length()-2);
                                            ou.setOrgunitName(orgunitName);
                                            //System.out.println("orgunitName at "+k+" is "+orgunitName);
                                          }
                                          if(substrArray[k].indexOf("id=") !=-1)
                                          {
                                            String strid=substrArray[k].substring(substrArray[k].indexOf("id=")+4);
                                            //System.out.println("strid at "+k+" is "+strid);
                                            if(strid !=null)
                                            {
                                                orgunitId=strid.substring(0, strid.indexOf("\""));
                                                ou.setOrgunitId(orgunitId);
                                                //System.out.println("orgunitId at "+k+" is "+orgunitId);
                                            }
                                          }
                                          if(substrArray[k].indexOf("level=") !=-1)
                                          {
                                            String strlevel=substrArray[k].substring(substrArray[k].indexOf("level=")+7);
                                            //System.out.println("strlevel at "+k+" is "+strlevel);
                                            if(strlevel !=null)
                                            {
                                                strlevel=strlevel.trim();
                                                String numlevel=strlevel.substring(0, strlevel.indexOf("\""));
                                                level=Integer.parseInt(numlevel);
                                                ou.setLevel(level);
                                                //System.out.println("numlevel at "+k+" is "+numlevel);
                                            }
                                          }
                                      }
                                      else if(substrArray[k].indexOf("<parent") !=-1)
                                      {
                                          //System.out.println("substrArray[k] for parent at "+k+" is "+substrArray[k]);
                                          if(substrArray[k].indexOf("id=") !=-1)
                                          {
                                            String strid=substrArray[k].substring(substrArray[k].indexOf("id=")+4);
                                            //System.out.println("parentstrid at "+k+" is "+strid);
                                            if(strid !=null)
                                            {
                                                parentId=strid.substring(0, strid.indexOf("\""));
                                                ou.setParentId(parentId);
                                                //System.out.println("parentId at "+k+" is "+parentId);
                                            }
                                          }
                                      }
                                  }
                                 ou.setLastModifiedDate(appUtil.getCurrentDate()); 
                              }
                          }
                          if(ou.getOrgunitId() !=null && ou.getOrgunitName() !=null && ou.getInstanceName() !=null && ou.getLastModifiedDate() !=null)
                          {
                              oudao.saveOrganizationUnit(ou);
                          }
                      }
                  }
              }
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
      }
  }
  public void readDataElementsFromXml(String xmlFilePath,String dhisInstance) 
  {
      AppUtility appUtil=new AppUtility();
      List list=AppUtility.readFiles(xmlFilePath);
      if(list !=null)
      {
          String dataElementName=null;
          String dataElementId=null;
          String lastModifiedDate=appUtil.getCurrentDate();
          DxDataElementDao dedao=new DxDataElementDaoImpl();
          DxDataElement de=new DxDataElement();
          de.setDhisInstance(dhisInstance);
          de.setLastModifiedDate(lastModifiedDate);
          //System.out.println("Number of organization unit is "+list.size());
          String str=null;
          try
          {
              for(int i=0; i<list.size(); i++)
              {
                  str=list.get(i).toString();
                  if(str !=null && str.indexOf("<dataElement") !=-1)
                  {
                      dataElementName=null;
                      dataElementId=null;
                      
                      String[] strArray=str.split("<dataElement");
                      for(int j=0; j<strArray.length; j++)
                      {
                          de=new DxDataElement();
                          de.setDhisInstance(dhisInstance);
                          de.setLastModifiedDate(lastModifiedDate);
                          System.out.println("strArray at "+j+" is "+strArray[j]);
                          if(strArray[j] !=null)
                          {
                              try
                              {
                                  //this contains the data element name and id
                                  if(strArray[j].indexOf("name=") !=-1 && strArray[j].indexOf("created=") !=-1)
                                  {
                                      String nameAndCreated=strArray[j].substring(strArray[j].indexOf("name=")+6, strArray[j].indexOf("created=")-2);
                                      dataElementName=nameAndCreated.replace("\"", "");
                                      dataElementId=strArray[j].substring(strArray[j].indexOf("id=")+4, strArray[j].indexOf("\"><"));
                                      de.setDataElementId(dataElementId);
                                      de.setDataElementName(dataElementName);
                                      dedao.saveOrUpdateDataElement(de);

                                      System.out.println("Data element name at "+j+" is "+de.getDataElementName());
                                      System.out.println("Data element id at "+j+" is "+de.getDataElementId());

                                  }
                              }
                              catch(Exception ex)
                              {
                                  System.err.println(ex.getMessage());
                                  continue;
                              }

                          }
                                 
                      }
                  }
              }
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
      }
}
public void readCategoryCombinationsFromXml(String xmlFilePath,String dhisInstance) 
  {
      AppUtility appUtil=new AppUtility();
      List list=AppUtility.readFiles(xmlFilePath);
      if(list !=null)
      {
          String catComboName=null;
          String catComboId=null;
          String lastModifiedDate=appUtil.getCurrentDate();
          
          DxCategoryCombinationDao ccdao=new DxCategoryCombinationDaoImpl();
          DxCategoryCombination ccombo=new DxCategoryCombination();
          ccombo.setDhisInstance(dhisInstance);
          ccombo.setLastModifiedDate(lastModifiedDate);
          //System.out.println("Number of organization unit is "+list.size());
          String str=null;
          try
          {
              for(int i=0; i<list.size(); i++)
              {
                  str=list.get(i).toString();
                  if(str !=null && str.indexOf("<categoryOptionCombo") !=-1)
                  {
                      catComboName=null;
                      catComboId=null;
                      
                      String[] strArray=str.split("<categoryOptionCombo");
                      for(int j=0; j<strArray.length; j++)
                      {
                          ccombo=new DxCategoryCombination();
                          ccombo.setDhisInstance(dhisInstance);
                          ccombo.setLastModifiedDate(lastModifiedDate);
                          System.out.println("strArray at "+j+" is "+strArray[j]);
                          if(strArray[j] !=null)
                          {
                              try
                              {
                                  //this contains the data element name and id
                                  if(strArray[j].indexOf("name=") !=-1 && strArray[j].indexOf("lastUpdated=") !=-1)
                                  {
                                      catComboName=strArray[j].substring(strArray[j].indexOf("name=")+6, strArray[j].indexOf("lastUpdated=")-2);
                                      catComboId=strArray[j].substring(strArray[j].indexOf("id=")+4, strArray[j].indexOf("created=")-2);
                                      
                                      catComboName=catComboName.trim();
                                      catComboId=catComboId.trim();
                                      ccombo.setCatComboName(catComboName);
                                      ccombo.setCatComboId(catComboId);
                                      ccdao.saveOrUpdateCategoryCombination(ccombo);

                                      System.out.println("catComboName at "+j+" is "+ccombo.getCatComboName());
                                      System.out.println("Data element id at "+j+" is "+ccombo.getCatComboId());

                                  }
                              }
                              catch(Exception ex)
                              {
                                  System.err.println(ex.getMessage());
                                  continue;
                              }

                          }
                                 
                      }
                  }
              }
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
      }
}
    /*try {
        //xmlFilePath="C:\\test\\metaData.xml";
	File fXmlFile = new File(xmlFilePath);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);

	//optional, but recommended
	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	doc.getDocumentElement().normalize();

	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

	NodeList nList = doc.getElementsByTagName("organisationUnit");

	System.out.println("----------------------------");

	for (int temp = 0; temp < nList.getLength(); temp++) {

		Node nNode = nList.item(temp);
                NodeList childList=nNode.getChildNodes();
		System.out.println("\nCurrent Element :" + nNode.getNodeName());

		if (nNode.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element eElement = (Element) nNode;

			System.out.println("Name : " + eElement.getAttribute("name"));
			System.out.println("Created : " + eElement.getAttribute("created"));
                        System.out.println("Level : " + eElement.getAttribute("level"));
                        System.out.println("Id : " + eElement.getAttribute("id"));
                        
                        //NodeList pList = doc.getElementsByTagName("parent");
                        for(int i=0; i<childList.getLength(); i++)
                        {
                           Node pNode = childList.item(i); 
                           if (pNode.getNodeType() == Node.ELEMENT_NODE) 
                            {
                                if(pNode.getNodeName() !=null && pNode.getNodeName().equalsIgnoreCase("parent"))
                                {
                                    Element pElement = (Element) pNode;
                                    System.out.println("Parent Name : " + pElement.getAttribute("name"));
                                    System.out.println("Parent Created : " + pElement.getAttribute("created"));                                  
                                    System.out.println("Parent Id : " + pElement.getAttribute("id"));
                                }
                            }
                        }
			
		}
	}
    } catch (Exception e) {
	e.printStackTrace();
    }
  
public void readDataElementFromXml(String xmlFilePath,String dhisInstance) {

    try {
        File fXmlFile = new File(xmlFilePath);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);

	
	doc.getDocumentElement().normalize();

	
	NodeList nList = doc.getElementsByTagName("dataElement");
        int count=0;
	System.out.println("----------------------------");

	for (int temp = 0; temp < nList.getLength(); temp++) 
        {
		Node nNode = nList.item(temp);
                
		System.out.println("\nCurrent Element :" + nNode.getNodeName());

		if (nNode.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element eElement = (Element) nNode;
			System.out.println("Name : " + eElement.getAttribute("name"));
			System.out.println("Created : " + eElement.getAttribute("created"));
                        System.out.println("Id : " + eElement.getAttribute("id"));	
		}
                count++;
	}
        System.out.println(count+" Data elements imported");
    } 
    catch (Exception e) 
    {
	e.printStackTrace();
    }
  }
public void readCategoryComboFromXml(String xmlFilePath,String dhisInstance) {

    try {
        File fXmlFile = new File(xmlFilePath);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        //File file = new File("c:\\file.xml");
        InputStream inputStream= new FileInputStream(fXmlFile);
        //dBuilder.s
	Document doc = dBuilder.parse(inputStream);
        
	
	doc.getDocumentElement().normalize();

	
	NodeList nList = doc.getElementsByTagName("categoryOptionCombo");
        int count=0;
	System.out.println("----------------------------");

	for (int temp = 0; temp < nList.getLength(); temp++) 
        {
		Node nNode = nList.item(temp);
                
		System.out.println("\nCurrent Element :" + nNode.getNodeName());

		if (nNode.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element eElement = (Element) nNode;
			System.out.println("Name : " + eElement.getAttribute("name"));
			System.out.println("lastUpdated : " + eElement.getAttribute("lastUpdated"));
                        System.out.println("Id : " + eElement.getAttribute("id"));	
		}
                count++;
	}
        System.out.println(count+" categoryOptionCombos imported");
    } 
    catch (Exception e) 
    {
	e.printStackTrace();
    }
  }*/
}
