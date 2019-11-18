/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.nomis.nomisutils;

import com.fhi.kidmap.business.CboSetup;
import com.fhi.kidmap.business.Cbos;
import com.fhi.kidmap.business.ComponentPropertyBean;
import com.fhi.kidmap.business.EligibilityCriteria;
import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcSchool;
import com.fhi.kidmap.business.Partners;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.business.Wards;
import com.fhi.kidmap.dao.CboDao;
import com.fhi.kidmap.dao.CboDaoImpl;
import com.fhi.kidmap.dao.CboSetupDao;
import com.fhi.kidmap.dao.CboSetupDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.DomainServicesDao;
import com.fhi.kidmap.dao.DomainServicesDaoImpl;
import com.fhi.kidmap.dao.EligibilityCriteriaDao;
import com.fhi.kidmap.dao.EligibilityCriteriaDaoImpl;
import com.fhi.kidmap.dao.LgaDao;
import com.fhi.kidmap.dao.LgaDaoImpl;
import com.fhi.kidmap.dao.OrganizationRecordsDao;
import com.fhi.kidmap.dao.OrganizationRecordsDaoImpl;
import com.fhi.kidmap.dao.OvcSchoolDao;
import com.fhi.kidmap.dao.OvcSchoolDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.dao.StatesDao;
import com.fhi.kidmap.dao.StatesDaoImpl;
import com.fhi.kidmap.dao.UserDao;
import com.fhi.kidmap.dao.UserDaoImpl;
import com.fhi.kidmap.dao.WardDao;
import com.fhi.kidmap.dao.WardDaoImpl;
import com.fhi.kidmap.report.CsiMapping;
import com.fhi.kidmap.report.ReportUtility;
import com.fhi.nomis.OperationsManagement.PartnerManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author COMPAQ USER
 */
public class LoadUpInfo implements Serializable
{
    AppUtility appUtil=new AppUtility();
    public LoadUpInfo()
    {

    }
    public void getOrganizationList(HttpSession session)
    {
        List list=new ArrayList();
        States state=(States)session.getAttribute("state");
        try
        {
            OrganizationRecordsDao orgdao=new OrganizationRecordsDaoImpl();
            list=orgdao.getStateOrganizationList(state.getState_code());
            //list=orgdao.getOrganizationList();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("organizationList", list);
    }
    public void getServiceList(HttpSession session)
    {
        List list=new ArrayList();
        try
        {
            OrganizationRecordsDao orgdao=new OrganizationRecordsDaoImpl();
            list=orgdao.getServiceList();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("serviveList", list);
    }
    public void getServiceDomainList(HttpServletRequest request)
    {
        List list=new ArrayList();
        try
        {
            DomainServicesDao dsdao=new DomainServicesDaoImpl();
            list=dsdao.getDomainServices();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        request.setAttribute("listofservicedomains", list);
    }
    public void getPartners(HttpSession session)
    {
        List list=new ArrayList();
        try
        {
            PartnersDao pdao=new PartnersDaoImpl();
            list=pdao.getAllPartners();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("listofpartners", list);
    }
    public void setNewLoginInfo(HttpSession session,String user,String userRole,String userRealNames,String usergrpId)
    {
        session.setAttribute("USER", user);
        session.setAttribute("userrole", userRole);
        session.setAttribute("userRealNames", userRealNames);
        session.setAttribute("userStates", usergrpId);
        if(userRole.equalsIgnoreCase("Administrator"))
        session.setAttribute("adminSaveDisabled", "false");
        else
        session.setAttribute("adminSaveDisabled", "true");
    }
    public void setLoginInfo(HttpSession session,String user,String userRole,String userRealNames,String userStates)
    {
        session.setAttribute("USER", user);
        session.setAttribute("userrole", userRole);
        session.setAttribute("userRealNames", userRealNames);
        session.setAttribute("userStates", userStates);
        if(userRole.equalsIgnoreCase("superuser"))
        session.setAttribute("adminSaveDisabled", "false");
        else
        session.setAttribute("adminSaveDisabled", "true");
    }
    public void getAllEligibilityCriteria(HttpSession session)
    {
        List list=new ArrayList();
        try
        {
            EligibilityCriteriaDao edao=new EligibilityCriteriaDaoImpl();
            list=edao.getEligibilityCriteria();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("listofeligibilitycriteria", list);
    }
    public void displayEligibilityCriteria(HttpSession session)
    {
        List list=new ArrayList();
        List finalList=new ArrayList();
        DaoUtil util=new DaoUtil();
        String tableHtml="";
        String styleId="";
        String styleId2="";
        try
        {
            EligibilityCriteriaDao edao=new EligibilityCriteriaDaoImpl();
            list=edao.getEligibilityCriteria();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        int count=list.size();
        int j=0;
        if(count%2 !=0)
        list.add(null);
        String eligibility=" ";
        String eligibility2=" ";
        ComponentPropertyBean propertyBean=new ComponentPropertyBean();
        for(int i=0; i<list.size(); i+=2)
        {
            eligibility=" ";
            eligibility2=" ";
            List propertyList=new ArrayList();
            EligibilityCriteria ec=(EligibilityCriteria)list.get(i);
            if(ec !=null)
            {
                j=i+1;
                propertyBean=new ComponentPropertyBean();
                eligibility=ec.getEligibilityName();
                styleId=util.tokenize(eligibility).trim();
                propertyBean.setName(eligibility);
                propertyBean.setValue(eligibility);
                propertyBean.setId(styleId);
                propertyList.add(propertyBean);
            }

            ec=(EligibilityCriteria)list.get(i+1);
            if(ec !=null)
            {
                j++;
                propertyBean=new ComponentPropertyBean();
                eligibility2=ec.getEligibilityName();
                styleId2=util.tokenize(eligibility2).trim();
                propertyBean.setName(eligibility2);
                propertyBean.setValue(eligibility2);
                propertyBean.setId(styleId2);
                propertyList.add(propertyBean);
            }
            finalList.add(propertyList);
        }
        session.setAttribute("finalList", finalList);
        session.setAttribute("eligibilityCriteria", tableHtml);
        
    }
    public void getAllStates(HttpSession session)
    {
           List list=new ArrayList();
           DaoUtil util=new DaoUtil();
           try
           {
               list=util.getUserAssignedStates(session);
                /*StatesDao dao = new StatesDaoImpl();
                list = dao.getStatesAsString();
                if(list.size() >1)
                {
                    States state=new States();
                    state.setState_code("All");
                    state.setName("All");
                    list.add(0, state);
                }*/
           }
           catch(Exception e)
           {
                e.printStackTrace();
           }
           session.setAttribute("states", list);
    }
    public void getAllStatesForExport(HttpSession session)
    {
           List list=new ArrayList();
           
           try
           {
                //StatesDao dao = new StatesDaoImpl();
                //list = dao.getStatesAsString(); 
                DaoUtil util=new DaoUtil();
                list=util.getUserAssignedStates(session);
                States state=new States();
                state.setState_code("All");
                state.setName("All");
                list.add(0, state);
           }
           catch(Exception e)
           {
                e.printStackTrace();
           }
           session.setAttribute("states", list);
    }
    public void loadEnrollmentDates(HttpServletRequest request,int month, int startYear)
    {
        //appUtil.generatDate(startYear)
        DateManager dm=new DateManager();
        request.setAttribute("generatedStartDates", DateManager.generateYears());
        request.setAttribute("generatedMonths", dm.generateMonths(month));
    }
    public void setRequestParameters(HttpServletRequest request)
    {
        List emptyList=new ArrayList();
        if(request.getAttribute("heightDisabled")==null)
         request.setAttribute("heightDisabled", "true");
        if(request.getAttribute("weightDisabled")==null)
         request.setAttribute("weightDisabled", "true");

        if(request.getAttribute("enableOrgCapSave")==null)
         request.setAttribute("enableOrgCapSave", "false");
        if(request.getAttribute("enableOrgCapModify")==null)
         request.setAttribute("enableOrgCapModify", "true");
        
        if(request.getAttribute("noofassessment")==null)
         request.setAttribute("noofassessment", 0);
        if(request.getAttribute("dateOfFirstAssessment")==null)
         request.setAttribute("dateOfFirstAssessment", null);

        if(request.getAttribute("currentAge")==null)
        request.setAttribute("currentAge", "underaged;1");

        
    }
    public void setParamAttributes(HttpServletRequest request)
    {
        HttpSession session=request.getSession();
        Ovc ovc=new Ovc();
        List emptyList=new ArrayList();
        List cboList=new ArrayList();
        List wardList=new ArrayList();
        List list=new ArrayList();
        String ovcWithdrawn=" ";
        String modifyDisabled="true";
        String saveDisabled="false";
        String[] stateDetails={};
        CsiMapping csiM=new CsiMapping();

        if(session.getAttribute("statesWithRights")==null)
        session.setAttribute("statesWithRights", emptyList);

        if(session.getAttribute("siteSetupLgaList")==null)
        session.setAttribute("siteSetupLgaList", emptyList);

        if(session.getAttribute("caregiverlgaList")==null)
        session.setAttribute("caregiverlgaList", emptyList);
        if(session.getAttribute("caregivercboList")==null)
        session.setAttribute("caregivercboList", cboList);

        if(session.getAttribute("summstatlgaList")==null)
        session.setAttribute("summstatlgaList", emptyList);
        if(session.getAttribute("summstatcboList")==null)
        session.setAttribute("summstatcboList", emptyList);

        if(session.getAttribute("lgaList")==null)
        session.setAttribute("lgaList", emptyList);
        if(session.getAttribute("cboList")==null)
        session.setAttribute("cboList", cboList);
        if(session.getAttribute("orgList")==null)
        session.setAttribute("orgList", cboList);
        if(session.getAttribute("MthlySummOrgList")==null)
        session.setAttribute("MthlySummOrgList", cboList);
        
        if(session.getAttribute("wardList")==null)
        session.setAttribute("wardList", wardList);          
        if(session.getAttribute("baselineInfo")==null)
        session.setAttribute("baselineInfo", ovc);
        
        if(session.getAttribute("ovcWithdrawn")==null)
        session.setAttribute("ovcWithdrawn", ovcWithdrawn);
        if(session.getAttribute("currentAge")==null)
        session.setAttribute("currentAge", "underaged;1");
        if(session.getAttribute("partnerList")==null)
        session.setAttribute("partnerList", list);
        if(session.getAttribute("userdetails")==null)
        session.setAttribute("userdetails", emptyList);

        if(session.getAttribute("stateDetails")==null)
        session.setAttribute("stateDetails", stateDetails);
        
        if(session.getAttribute("lgalistperstate")==null)
        session.setAttribute("lgalistperstate", emptyList);
        if(session.getAttribute("cbolistperlga")==null)
        session.setAttribute("cbolistperlga", emptyList);
        if(session.getAttribute("cboDetails")==null)
        session.setAttribute("cboDetails", emptyList);

        if(session.getAttribute("wardlistpercbo")==null)
        session.setAttribute("wardlistpercbo", emptyList);
        if(session.getAttribute("wardDetails")==null)
        session.setAttribute("wardDetails", emptyList);

        if(session.getAttribute("dhislgaList")==null)
        session.setAttribute("dhislgaList", emptyList);
        if(session.getAttribute("dhiscboList")==null)
        session.setAttribute("dhiscboList", emptyList);

        if(request.getAttribute("saveDiabled")==null)
        request.setAttribute("saveDiabled", "false");
        if(request.getAttribute("saveDiabled")==null)
        request.setAttribute("saveDiabled", "false");
       
        if(session.getAttribute("cboModifyDisabled")==null)
        session.setAttribute("cboModifyDisabled", modifyDisabled);
        if(session.getAttribute("wardModifyDisabled")==null)
        session.setAttribute("wardModifyDisabled", modifyDisabled);
        
        if(session.getAttribute("stateModifyDisabled")==null)
        session.setAttribute("stateModifyDisabled", modifyDisabled);
        if(session.getAttribute("userAccountModifyDisabled")==null)
        session.setAttribute("userAccountModifyDisabled", modifyDisabled);
        if(session.getAttribute("modifyDisabled")==null)
        session.setAttribute("modifyDisabled", modifyDisabled);
        
            if(session.getAttribute("statesavedisabled")==null)
            session.setAttribute("statesavedisabled", "false");
            if(session.getAttribute("saveDisabled")==null)
            session.setAttribute("saveDisabled", saveDisabled);
            /*if(session.getAttribute("lgasavedisabled")==null)
            session.setAttribute("lgasavedisabled", "false");*/
            if(session.getAttribute("wardsavedisabled")==null)
            session.setAttribute("wardsavedisabled", "false");
            if(session.getAttribute("cbosavedisabled")==null)
            session.setAttribute("cbosavedisabled", "false");
            if(session.getAttribute("userAccountSaveDisabled")==null)
            session.setAttribute("userAccountSaveDisabled", "false");
            /*if(session.getAttribute("eligibilitysavedisabled")==null)
            session.setAttribute("eligibilitysavedisabled", "false");*/
            if(session.getAttribute("followupSaveDisabled")==null)
            session.setAttribute("followupSaveDisabled", "false");
            if(session.getAttribute("followupModifyDisabled")==null)
            session.setAttribute("followupModifyDisabled", "true");
        if((String)session.getAttribute("userrole") !=null )
        {
            if(!((String)session.getAttribute("userrole")).equalsIgnoreCase("Administrator"))
            {
                session.setAttribute("statesavedisabled", "true");
                request.setAttribute("saveDisabled", "true");
                session.setAttribute("lgasavedisabled", "true");
                session.setAttribute("wardsavedisabled", "true");
                session.setAttribute("cbosavedisabled", "true");
                
                /*session.setAttribute("eligibilitysavedisabled", "true");
                session.setAttribute("userAccountSaveDisabled", "true");*/

                request.setAttribute("modifyDisabled", "true");
                session.setAttribute("cboModifyDisabled", "true");
                session.setAttribute("wardModifyDisabled", "true");
                //session.setAttribute("eligibilityModifyDisabled", "true");
                //session.setAttribute("lgaModifyDisabled", "true");
                session.setAttribute("stateModifyDisabled", "true");
                //session.setAttribute("eligibilityModifyDisabled", "true");
                session.setAttribute("userAccountModifyDisabled", "true");
            }
        }
        else
        {
                session.setAttribute("statesavedisabled", "true");
                request.setAttribute("saveDisabled", "true");
                //session.setAttribute("lgasavedisabled", "true");
                session.setAttribute("wardsavedisabled", "true");
                session.setAttribute("cbosavedisabled", "true");
                //session.setAttribute("eligibilitysavedisabled", "true");
                session.setAttribute("userAccountSaveDisabled", "true");

                request.setAttribute("modifyDisabled", "true");
                session.setAttribute("cboModifyDisabled", "true");
                session.setAttribute("wardModifyDisabled", "true");
                //session.setAttribute("eligibilityModifyDisabled", "true");
                //session.setAttribute("lgaModifyDisabled", "true");
                session.setAttribute("stateModifyDisabled", "true");
                //session.setAttribute("eligibilityModifyDisabled", "true");
                session.setAttribute("userAccountModifyDisabled", "true");
        }
    }
    public void setEnrollmentAttributes(HttpSession session)
    {
        List lastwardlist=new ArrayList();
        if(session.getAttribute("lastwardlist")==null)
        session.setAttribute("lastwardlist", lastwardlist);
        if(session.getAttribute("enableModify")==null)
        session.setAttribute("enableModify", "true");
        
            if(session.getAttribute("enableSave")==null)
            {
                if(appUtil.isUserInRole(session, "Administrator") || appUtil.isUserInRole(session, "DataEntry"))
                session.setAttribute("enableSave", "false");
                else
                session.setAttribute("enableSave", "true");
            }
    }
    /*public void getAllStatesAsObjects(HttpSession session)
    {
           List list=new ArrayList();
           try
           {
                //DaoUtil util=new DaoUtil();
                //list=util.getUserAssignedStates(session);
                StatesDao dao = new StatesDaoImpl();
                list=dao.getStates();
           }
           catch(Exception e)
           {
                e.printStackTrace();
           }
           session.setAttribute("states", list);
    }*/
    public void getAllUsers(HttpSession session)
    {
        List list=new ArrayList();
        try
        {
            UserDao dao=new UserDaoImpl();
            list=dao.getAllUsers();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("userLists", list);
    }
    public String getStateCodeFromSetup(HttpSession session)
    {
        String user=appUtil.getCurrentUser(session);
        CboSetup setup=getSetupInfo(user);
        String stateCode=null;
        if(setup!=null)
        stateCode =setup.getState_code();
        return stateCode;
    }
    
    public String processCboSetup(HttpSession session)
    {
        String user=appUtil.getCurrentUser(session);
        List list=new ArrayList();
        List cboList=new ArrayList();
        CboSetup setup=null;
            String lgaCode = "";
            String stateCode ="";
            String cboCode ="";
            String partnerCode ="";
            String param=null;
            setup=getSetupInfo(user);
            if(setup !=null)
            {
                lgaCode = setup.getLga_code();
                stateCode = setup.getState_code();
                cboCode = setup.getUserName();
                partnerCode=setup.getPartner();
                param+=stateCode+";"+lgaCode+";"+cboCode+";"+partnerCode+";";
            }
            try
            {
                StatesDao dao = new StatesDaoImpl();
                list = dao.getState(stateCode);
                States state=new States();
                for (Object s: list)
                {
                    state = (States)s;
                }
                session.setAttribute("state", state);
                LgaDao ldao=new LgaDaoImpl();
                list = ldao.getLgaByLgaCode(lgaCode);
                Lgas lga=new Lgas();
                for (Object s: list)
                {
                    lga = (Lgas)s;
                }
                session.setAttribute("lga", lga);
                OrganizationRecords or=new OrganizationRecords();
                OrganizationRecordsDao orDao=new OrganizationRecordsDaoImpl();
                list=orDao.getOrganizationList(lgaCode);
                
                for (Object s: list)
                {
                    or=(OrganizationRecords)s;
                    cboList.add(or);
                }
                session.setAttribute("cbolist", cboList);
                Partners partner=PartnerManager.getPartner(partnerCode);
                /*PartnersDao pdao=new PartnersDaoImpl();
                list=pdao.getPartner(partnerCode);
                if(!list.isEmpty())
                partner = (Partners)list.get(0);*/
                session.setAttribute("partnername", partner.getPartnerName());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            List lgaList=getLgasPerStateFromOrgRecords(stateCode);
            session.setAttribute("lgaListInOrgRecords", lgaList);
        return param;
    }
    public void getStartupInfoFromData(HttpSession session)
    {
        ReportUtility rutil=new ReportUtility();
        String stateCode =getStateCodeFromSetup(session);
        List lgaList=new ArrayList();
        States state=new States();
        DaoUtil util=new DaoUtil();
        try
        {
            StatesDao dao = new StatesDaoImpl();
            state = dao.getStateByStateCode(stateCode);
            if(state==null)
            state=new States();
            else
            lgaList=rutil.generateLgaListForReports(stateCode);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("state", state);

        List cboList=new ArrayList();
        List wardList=new ArrayList();
        session.setAttribute("lgaListInOrgRecords", lgaList);
        if(lgaList !=null && !lgaList.isEmpty())
        {
            String lgaCode=null;
            Lgas lga=(Lgas)lgaList.get(0);
            if(lga !=null)
             lgaCode=lga.getLga_code();
            cboList=getCbosPerLgaFromSetup(session,lgaCode);
        }
        String partnerCode=util.getPartnerCode(appUtil.getCurrentUser(session));
        String partnerName=util.getPartnerName(partnerCode);
        session.setAttribute("partnerName", partnerName);
        session.setAttribute("cboListInOrgRecords", cboList);
        session.setAttribute("wardListPerOrganization", wardList);
    }
    /*public void getStartupInfo(HttpSession session)
    {
        
        String stateCode =getStateCodeFromSetup(session);
        System.err.println("stateCode in getStartupInfo(HttpSession session) is "+stateCode);
        List lgaList=new ArrayList();
        States state=new States();
        DaoUtil util=new DaoUtil();
        try
        {
            StatesDao dao = new StatesDaoImpl();
            state = dao.getStateByStateCode(stateCode);
            if(state==null)
            state=new States();
            else
            lgaList=getLgasPerStateFromOrgRecords(stateCode);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("state", state);

        List cboList=new ArrayList();
        List wardList=new ArrayList();
        session.setAttribute("lgaListInOrgRecords", lgaList);
        if(lgaList !=null && !lgaList.isEmpty())
        {
            String lgaCode=null;
            Lgas lga=(Lgas)lgaList.get(0);
            if(lga !=null)
             lgaCode=lga.getLga_code();
            cboList=getCbosPerLgaFromSetup(session,lgaCode);
        }
        String partnerCode=util.getPartnerCode(appUtil.getCurrentUser(session));
        String partnerName=util.getPartnerName(partnerCode);
        session.setAttribute("partnerName", partnerName);
        session.setAttribute("cboListInOrgRecords", cboList);
        session.setAttribute("wardListPerOrganization", wardList);
    }*/
    public void getStartupInfo(HttpSession session)
    {
        AccessManager acm=new AccessManager();
        String stateCode =getStateCodeFromSetup(session);
        System.err.println("stateCode in getStartupInfo(HttpSession session) is "+stateCode);
        List lgaList=new ArrayList();
        List lgaCodeList=new ArrayList();
        States state=new States();
        DaoUtil util=new DaoUtil();
        try
        {
            StatesDao dao = new StatesDaoImpl();
            state = dao.getStateByStateCode(stateCode);
            if(state==null)
            state=new States();
            else
            {
                User user=appUtil.getUser(session);
                lgaList=getUserAssignedLgas(user,stateCode);
                /*if(user !=null)
                {
                    if(user.getUserroleId().equalsIgnoreCase(NomisConstant.SUPERUSER_ROLEID) || user.getUserGroupId().equalsIgnoreCase(NomisConstant.DEFAULTGRPID))
                    lgaList=getLgasPerStateFromOrgRecords(stateCode);
                    else
                    {
                        lgaCodeList=acm.getLgaCodesPerUser(user.getUsername(), stateCode);  
                        if(lgaCodeList !=null && !lgaCodeList.isEmpty())
                        {
                            for(Object obj:lgaCodeList)
                            {
                                if(obj !=null)
                                {
                                    Lgas lga=util.getLgaByLgaCode(obj.toString());
                                    if(lga !=null)
                                    lgaList.add(lga);
                                }
                            }
                        }
                        
                    }
                }*/
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        session.setAttribute("state", state);

        List cboList=new ArrayList();
        List wardList=new ArrayList();
        session.setAttribute("lgaListInOrgRecords", lgaList);
        if(lgaList !=null && !lgaList.isEmpty())
        {
            String lgaCode=null;
            Lgas lga=(Lgas)lgaList.get(0);
            if(lga !=null)
             lgaCode=lga.getLga_code();
            cboList=getCbosPerLgaFromSetup(session,lgaCode);
        }
        
        String partnerCode=util.getPartnerCode(appUtil.getCurrentUser(session));
        String partnerName=util.getPartnerName(partnerCode);
        session.setAttribute("partnerName", partnerName);
        session.setAttribute("cboListInOrgRecords", cboList);
        session.setAttribute("wardListPerOrganization", wardList);
    }
    public List getUserAssignedLgas(User user,String stateCode)
    {
        AccessManager acm=new AccessManager();
        DaoUtil util=new DaoUtil();
        List lgaList=new ArrayList();
        if(user !=null)
        {
            if(user.getUserroleId().equalsIgnoreCase(NomisConstant.SUPERUSER_ROLEID) || user.getUserGroupId().equalsIgnoreCase(NomisConstant.DEFAULTGRPID))
            lgaList=getLgasPerStateFromOrgRecords(stateCode);
            else
            {
                List lgaCodeList=acm.getLgaCodesPerUser(user.getUsername(), stateCode);  
                if(lgaCodeList !=null && !lgaCodeList.isEmpty())
                {
                    for(Object obj:lgaCodeList)
                    {
                        if(obj !=null)
                        {
                            Lgas lga=util.getLgaByLgaCode(obj.toString());
                            if(lga !=null)
                            lgaList.add(lga);
                        }
                    }
                }

            }
        }
        return lgaList;
    }
    public List getLgasPerStateFromOrgRecords(String stateCode)
    {
        OrganizationRecordsDao orgDao=new OrganizationRecordsDaoImpl();
        List lgaCodeList=new ArrayList();
        List lgaCodeList2=new ArrayList();
        List lgaList=new ArrayList();
        try
        {
            lgaCodeList=orgDao.getOrganizationRecordsLgaList(stateCode);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        if(lgaCodeList !=null)
        {
            String lgaCodes=null;
            for(int i=0; i<lgaCodeList.size(); i++)
            {
                lgaCodes=(String)lgaCodeList.get(i);
                //System.err.println("lgaCodes is "+lgaCodes);
                if(lgaCodes !=null)
                {
                    if( lgaCodes.indexOf(",") !=-1)
                    {
                        String[] lgaCodeArray=lgaCodes.split(",");
                        for(int j=0; j<lgaCodeArray.length; j++)
                        {
                            if(!lgaCodeList2.contains(lgaCodeArray[j]))
                            {
                                lgaCodeList2.add(lgaCodeArray[j]);
                                //System.err.println("lgaCodeArray[j] is "+lgaCodeArray[j]);
                            }
                        }
                    }
                    else if(!lgaCodeList2.contains(lgaCodes))
                    lgaCodeList2.add(lgaCodes);
                }
            }
        }
        if(lgaCodeList2 !=null && !lgaCodeList2.isEmpty())
        {
            LgaDao ldao=new LgaDaoImpl();
            for(Object lgaCode:lgaCodeList2)
            {
                String code=(String)lgaCode;
                try
                {
                    //System.err.println("code is "+code);
                    Lgas lga=(Lgas)ldao.getLgaByCode(code);
                    if(lga==null)
                    {
                        lga=new Lgas();
                        lga.setLga_code(code);
                        lga.setLga_name(code);
                        lga.setState_code(stateCode);
                    }
                    //System.err.println("lga attributes are "+lga.getLga_code()+" "+lga.getLga_name());
                    if(!appUtil.isString(lga.getLga_name()))
                    lga.setLga_name(code);
                    lgaList.add(lga);
                    
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        return lgaList;
    }
    public void getLgasPerState(HttpSession session)
    {
        String user=appUtil.getCurrentUser(session);//(String)session.getAttribute("USER");
        List list=new ArrayList();
        List lgaList=new ArrayList();
           String stateCode ="";
            try
            {
                CboSetup setup=getSetupInfo(user);
                if(setup==null)
                setup=new CboSetup();
                stateCode=setup.getState_code();
                LgaDao dao = new LgaDaoImpl();
                list = dao.getLgaList(stateCode);
                for (Object s: list)
                {
                    Lgas lga = (Lgas)s;
                    lgaList.add(lga);
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
           session.setAttribute("lgas", lgaList);
           session.setAttribute("wardListPerOrganization", new ArrayList()); 
    }
    public List getCbosPerLgaFromSetup(HttpSession session,String lgaCode)
    {
        OrganizationRecords or=new OrganizationRecords();
            OrganizationRecordsDao orDao=new OrganizationRecordsDaoImpl();
            System.err.println("lgaCode in loadup.getCbosPerLgaFromSetup is "+lgaCode);
            List cboList=new ArrayList();
            if(lgaCode==null || lgaCode.equalsIgnoreCase("") || lgaCode.equalsIgnoreCase(" "))
            return cboList;
            try
            {
                List list=orDao.getOrganizationList(lgaCode);
                
                for (Object s: list)
                {
                    or=(OrganizationRecords)s;
                    cboList.add(or);
                }
             }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            session.setAttribute("cboListInOrgRecords", cboList);
            session.setAttribute("wardListPerOrganization", new ArrayList()); 
            return cboList;
    }
    public void getCbosPerState(HttpSession session)
    {
        List list=new ArrayList();
        List cboList=new ArrayList();
           String stateCode ="";
           try
           {
                CboSetupDao dao = new CboSetupDaoImpl();
                list = dao.getCboSetupInfo();
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                CboSetup setup = (CboSetup)s;
                stateCode = setup.getState_code();
            }
            try
            {
                StatesDao dao = new StatesDaoImpl();
                list = dao.getState(stateCode);
                States state=null;
                for (Object s: list)
                {
                    state = (States)s;
                }
                session.setAttribute("state", state);
                CboDao cdao=new CboDaoImpl();
                list = cdao.getCboByState(stateCode);
                for (Object s: list)
                {
                    Cbos cbo = (Cbos)s;
                    cboList.add(cbo);
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            session.setAttribute("cbo", cboList);
            
    }
    public List getWardsPerCbo(HttpSession session,String lgaCode,String cboCode)
    {
        WardDao wdao = new WardDaoImpl();
        LgaDao lgaDao=new LgaDaoImpl();
        List wardList=new ArrayList();
        try
        {
                Lgas lga=(Lgas)lgaDao.getLgaByCode(lgaCode);
                String stateAndLgaCodes=lga.getState_code()+"/"+lgaCode;
                wardList=wdao.getWardByLgaCodeAndCboId(stateAndLgaCodes, cboCode);//.getWardByCboId(cboCode);
                
        }
        catch(Exception ex)
        {
            System.err.println("Error retrieving ward list");
        }
           session.setAttribute("wardListPerOrganization", wardList); 
            return wardList;
    }
    public void getAllWards(HttpSession session)
    {
        List list=new ArrayList();
        List wardList=new ArrayList();
           try
           {
                WardDao dao = new WardDaoImpl();
                list = dao.getAllWards();
            }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                Wards ward = (Wards)s;
                wardList.add(ward);
            }
        session.setAttribute("ward", wardList);
    }
    public void getSchoolsPerState(HttpSession session)
    {
        List list=new ArrayList();
        //String user=appUtil.getCurrentUser(session);//(String)session.getAttribute("USER");
        List schoolList=new ArrayList();
           String stateCode ="";
           try
           {
               stateCode=getCurrentState(session);
                OvcSchoolDao dao = new OvcSchoolDaoImpl();
                list = dao.getSchoolList(stateCode);
           }
           catch(Exception e)
           {
                e.printStackTrace();
           }
            for (Object s: list)
            {
                OvcSchool school = (OvcSchool)s;
                schoolList.add(school);
            }
            session.setAttribute("schools", schoolList);
    }
    public String getCurrentState(HttpSession session)
    {
       CboSetup setup=getSetupInfo(appUtil.getCurrentUser(session));
       if(setup==null)
       setup=new CboSetup();
       String stateCode=setup.getState_code();
       return stateCode;
    }
    public CboSetup getSetupInfo(String user)
    {
        CboSetup setup=null;
           try
           {
                CboSetupDao dao = new CboSetupDaoImpl();
                setup= dao.getCboSetup(user);
           }
           catch(Exception e)
           {
                e.printStackTrace();
           }
           return setup;
    }
}


