/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.dataexchange.pojo;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class MetadataManager implements Serializable
{
    XmlExtractor xmlExtractor=new XmlExtractor();
    public String processOrgUnit(String xmlFilePath,String dhisInstance)
    {
        xmlExtractor.readOrgUnitFromXml(xmlFilePath,dhisInstance);
        String msg="";
        
        return msg;
    }
    public String processDataElement(String xmlFilePath,String dhisInstance)
    {
        String msg="";
        xmlExtractor.readDataElementsFromXml(xmlFilePath,dhisInstance);
        return msg;
    }
    public String processCatCombo(String xmlFilePath,String dhisInstance)
    {
        String msg="";
        xmlExtractor.readCategoryCombinationsFromXml(xmlFilePath, dhisInstance);
        return msg;
    }
    
}
