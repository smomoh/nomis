/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DataEncryption;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.OvcSchoolDao;
import com.fhi.kidmap.dao.OvcSchoolDaoImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author COMPAQ USER
 */
public class SchoolRecords implements Serializable{

    DataEncryption de=new DataEncryption();
    DaoUtil util=new DaoUtil();
    AppUtility appUtil;

    public SchoolRecords()
    {

    }
    public void getSchoolAttendaceCount(HttpSession session,List paramList)
    {
        List list=new ArrayList();
        String stateCode=(String)paramList.get(0);
        String lgaCode=(String)paramList.get(1);
        String school_type=(String)paramList.get(2);
        String partnerCode=(String)paramList.get(3);
        String cboCode=(String)paramList.get(4);
        String wardCode=(String)paramList.get(5);
        String stateName=util.getStateName(stateCode);
        String lgaName=util.getLgaName(lgaCode);
        String cboName=util.getOrganizationName(cboCode);
        String wardName=util.getWardName(wardCode);
        String partnerName=util.getPartnerName(partnerCode);
        String param[]={stateCode,lgaCode,cboCode,wardCode,school_type,null,null,null,"17","0",partnerCode,stateName,lgaName,partnerName,null,cboName,wardName};
        OvcSchoolDao dao=new OvcSchoolDaoImpl();
        try
        {
            dao.cleanSchoolNamesInSchoolRecords();
            dao.cleanSchoolNamesInOvcRecords();
            list=dao.getOvcCountPerSchool(param);
            //list=dao.getOvcCountPerSchool(param[0],param[1],school_type);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("schAttenparams", param);
        session.setAttribute("schoolAttendanceCount", list);
    }
    public void getSchoolAttendaceList(HttpSession session)
    {
        appUtil=new AppUtility();
        String userRole=appUtil.getUserRole(session);
        List list=new ArrayList();
        List schoolList=new ArrayList();
        Ovc ovc=new Ovc();
        String param[]=(String[])session.getAttribute("schAttenparams");
        String schoolName=(String)session.getAttribute("school_name");
        String stateName=param[0];
        OvcSchoolDao dao=new OvcSchoolDaoImpl();
        try
        {
            list=dao.getSchoolAttendanceList(stateName, schoolName,param);
            System.err.println("school list size in schoolRecords is "+list.size());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        if(userRole !=null)
        {
            List encryptedOvcList=new ArrayList();
            encryptedOvcList=de.encryptOvcInfo(list,session);
            for (Object s: encryptedOvcList)
            {
                ovc=(Ovc)s;
                ovc.setWard(util.getWardName(ovc.getWard()));
                schoolList.add(ovc);
            }
        }
        session.setAttribute("schoolAttendaceList", schoolList);
    }
}
