/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.chart;

import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class ChartValueGenerator implements Serializable
{
    public double getProportionOfOvcCurrentlyEnrolledByHivStatus(String indicatorCode,String indicatorName,String additionalQueryCriteria,String enrollmentEndDateQuery,String hivStatus,boolean percentage)
    {
        OvcDao dao=new OvcDaoImpl();
        double value=0;
        try
        {
            int numberOfHivPosOvc=dao.getNumberOfOvcCurrentlyEnrolledByHivStatus(additionalQueryCriteria, hivStatus);
            int numberOfOvcCurrentlyEnrolled=dao.getNumberOfOvcCurrentlyEnrolled(additionalQueryCriteria);;
            if(numberOfOvcCurrentlyEnrolled>0 && numberOfHivPosOvc>0)
            value=(numberOfHivPosOvc/(double)numberOfOvcCurrentlyEnrolled);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        /*if(percentage)
        {
           value=value*100;
           value=Math.ceil(value);
        }*/               
        return value;
    }
}
