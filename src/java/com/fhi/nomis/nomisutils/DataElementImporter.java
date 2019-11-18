/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.nomis.nomisutils;

import com.fhi.kidmap.business.Indicators;
import com.fhi.kidmap.dao.IndicatorsDao;
import com.fhi.kidmap.dao.IndicatorsDaoImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class DataElementImporter implements Serializable
{
    public boolean importDhisDataElements(String fileName)
    {
        List list=new ArrayList();
        AppUtility appUtil=new AppUtility();
        System.err.println("File name is "+fileName);

        try
        {
        List fileContentList=appUtil.readFiles(fileName);
        if(fileContentList !=null)
        {
            List nameAndIdList=null;
            Indicators indicator;
                nameAndIdList=getDataElementNameAndIdFromXML(fileContentList);
                if(nameAndIdList !=null)
                {
                    for(int i=0; i<nameAndIdList.size(); i+=2)
                    {
                        indicator=new Indicators();
                        indicator.setIndicatorName((String)nameAndIdList.get(i));
                        indicator.setIndicatorId((String)nameAndIdList.get(i+1));
                        list.add(indicator);
                    }
                }
            boolean indicatorsSaved=saveIndicators(list);
            if(indicatorsSaved)
            return true;
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return false;
    }
public boolean saveIndicators(List listOfIndicators)
{
    System.err.println("Total number of indicators is "+listOfIndicators.size());
    IndicatorsDao idao=new IndicatorsDaoImpl();
    for(int i=0; i<listOfIndicators.size(); i++)
    {
        Indicators indicator=(Indicators)listOfIndicators.get(i);
        try
        {
            System.err.println("data element "+i+" before save is name="+indicator.getIndicatorName()+" id="+indicator.getIndicatorId());
            /*Indicators duplicateIndicator=idao.getIndicator(indicator.getIndicatorId());
            if(duplicateIndicator==null)
            {*/
                idao.saveIndicators(indicator);
               // System.err.println("data element "+i+" after save is name="+indicator.getIndicatorName()+" id="+indicator.getIndicatorId());
            //}
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    return true;
}
public List getDataElementNameAndIdFromXML(List strList)
{
    List list=new ArrayList();
    if(strList !=null)
    {
        String[] firstArray=null;
        String[] secondArray=null;
        String[] thirdArray=null;
        String name=null, id=null;
        String xmlTag;
        for(int i=0; i<strList.size(); i++)
        {
            xmlTag=(String)strList.get(i);
            if(xmlTag !=null && xmlTag.startsWith("<") && xmlTag.endsWith(">"))
            {
                firstArray=xmlTag.split("name=");
                if(firstArray !=null && firstArray.length>1)
                {
                    secondArray=firstArray[1].split("id=");
                    if(secondArray !=null && secondArray.length>1)
                    {
                        thirdArray=secondArray[0].split("\"");
                        name=thirdArray[1];
                        id=secondArray[1].replace("\"", "").replace("/>", "");
                        if(name !=null && id !=null)
                        {
                            list.add(name);
                            list.add(id);
                        }
                    }
                }
            }
        }
    }
    return list;

}
}
