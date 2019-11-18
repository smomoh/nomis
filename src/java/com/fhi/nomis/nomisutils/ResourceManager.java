/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.nomisutils;

import com.fhi.kidmap.dao.OptionsManagerDao;
import com.fhi.kidmap.dao.OptionsManagerDaoImpl;

/**
 *
 * @author smomoh
 */
public class ResourceManager 
{
    private static OptionsManagerDao opmdao=new OptionsManagerDaoImpl();
    public static boolean isRevisedHheForm()
    {
        boolean revisedHheForm=false;
        try
        {
            OptionsManager opm=opmdao.getOptionsManager("hhvaversion");
            if(opm ==null || (opm.getValue() ==null || opm.getValue().equalsIgnoreCase("2")))
            revisedHheForm=true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return revisedHheForm;
    }
    public static boolean isRevisedHivRiskAssessmentForm()
    {
        boolean revisedHracForm=false;
        try
        {
            OptionsManager opm=opmdao.getOptionsManager(OptionsAttributeManager.HRACID);
            if(opm ==null || (opm.getValue() ==null || opm.getValue().equalsIgnoreCase("2")))
            revisedHracForm=true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return revisedHracForm;
    }
    public static boolean xmlDataImportEnabled()
    {
        boolean xmlDataImportEnabled=false;
        try
        {
            OptionsManager opm=opmdao.getOptionsManager(OptionsAttributeManager.DBIMPORTACCESSID);
            if(opm ==null || (opm.getValue() ==null || opm.getValue().equalsIgnoreCase("1")))
            xmlDataImportEnabled=true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return xmlDataImportEnabled;
    }
    public static boolean displayBeneficiaryRecordsInHTML()
    {
        boolean displayRecordInHTML=false;
        try
        {
            OptionsManager opm=opmdao.getOptionsManager(OptionsAttributeManager.DISPLAYCLIENTRECORDINHTMLID);
            if(opm ==null || (opm.getValue() ==null || opm.getValue().equalsIgnoreCase("1")))
            displayRecordInHTML=true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return displayRecordInHTML;
    }
    public static boolean downloadBeneficiaryRecordsInExcel()
    {
        boolean displayRecordInExcel=false;
        try
        {
            OptionsManager opm=opmdao.getOptionsManager(OptionsAttributeManager.DOWNLOADCLIENTRECORDINEXCELID);
            if(opm ==null || (opm.getValue() ==null || opm.getValue().equalsIgnoreCase("1")))
            displayRecordInExcel=true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return displayRecordInExcel;
    }
}
