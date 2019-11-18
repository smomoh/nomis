/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DatabaseUtilities;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author HP USER
 */
public class HibernateUtil
{

private static SessionFactory sessionFactory;

static {
try
{
    AppUtility appUtil=new AppUtility();
    
    Configuration cfg = new Configuration();
    cfg.configure();
    //String hibernateConnectionPath=AppUtility.getEnvironmentFilePath();
    String dbURL=appUtil.getDatabaseURL();
    
    cfg.setProperty("hibernate.connection.url", dbURL);
    cfg=getPreparedConfiguration(cfg);
    sessionFactory = cfg.buildSessionFactory();
    
}
//catch (Throwable ex)
catch (Exception ex)
{
    ex.getMessage();
    //ex.printStackTrace(System.out);
    //throw new ExceptionInInitializerError(ex);
}

}
public static Session getSession() throws HibernateException
{
    return sessionFactory.openSession();
}
private static Configuration getPreparedConfiguration(Configuration cfg)
{
    System.err.println("Inside getPreparedConfiguration(cfg) ");
    List list=DatabaseUtilities.getConnectionParameters();
    if(list !=null && !list.isEmpty())
    {
        //System.err.println("list size in getPreparedConfiguration(cfg) is "+list.size());
        String property=null;
        String value=null;
        for(int i=0; i<list.size(); i++)
        {
            String str=list.get(i).toString();
            if(str !=null && str.indexOf("=") !=-1)
            {
                String[] strArray=str.split("=");
                if(strArray !=null && strArray.length>1)
                {
                    property=strArray[0];
                    value=strArray[1];
                    if(property !=null && value !=null)
                    {
                        property=property.trim();
                        value=value.trim();
                        System.err.println("property: "+property+" value: "+value);
                        if(value.length()>0)
                        {
                            if(property.equalsIgnoreCase("hibernate.connection.driver_class"))
                            {
                                cfg.setProperty(property, value);
                            }
                            else if(property.equalsIgnoreCase("hibernate.dialect"))
                            {
                               cfg.setProperty(property, value); 
                            }
                            else if(property.equalsIgnoreCase("hibernate.connection.url"))
                            {
                                cfg.setProperty(property, value);
                            }
                            else if(property.equalsIgnoreCase("hibernate.connection.username"))
                            {
                                cfg.setProperty(property, value);
                            }
                            else if(property.equalsIgnoreCase("hibernate.connection.password"))
                            {
                               cfg.setProperty(property, value);
                            }
                        }
                    }
                }
                
            }
        }
    }
    return cfg;
}
}