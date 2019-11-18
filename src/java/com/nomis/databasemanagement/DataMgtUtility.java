/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.databasemanagement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Siaka
 */
public class DataMgtUtility implements Serializable
{
    public static String getPropertyValue(String propertyValue)
    {
        if(propertyValue==null || propertyValue.equals(" ") || propertyValue.equals(""))
         propertyValue="none";
         propertyValue=propertyValue.trim();
        return propertyValue;
    }
    public static String getIntegerPropertyValue(String propertyValue)
    {
        if(propertyValue==null || propertyValue.equals(" ") || propertyValue.equals(""))
         propertyValue="0";
         propertyValue=propertyValue.trim();
        return propertyValue;
    }
    public static boolean writeXmlFile(String xmlString,File file)
    {
        try
        {
            BufferedWriter bw=null;               
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(xmlString);
            bw.flush();
            bw.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
