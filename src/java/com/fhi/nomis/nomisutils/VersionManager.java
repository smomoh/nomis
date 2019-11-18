/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.nomisutils;

/**
 *
 * @author smomoh
 */
public class VersionManager 
{
    public static Double getVersionNumber()
    {
      return new Double("2.51");   
    }
    public static String getVersionName()
    {
      return "version "+getVersionNumber().doubleValue();   
    }
}

