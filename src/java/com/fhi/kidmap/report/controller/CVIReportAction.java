/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.dao.ChildVulnerabilityIndexDao;
import com.fhi.kidmap.dao.ChildVulnerabilityIndexDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.LgaDao;
import com.fhi.kidmap.dao.LgaDaoImpl;
import com.fhi.kidmap.dao.StatesDao;
import com.fhi.kidmap.dao.StatesDaoImpl;
import com.fhi.kidmap.report.OvcRecords;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class CVIReportAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        CVIReportForm cviForm=(CVIReportForm)form;

        String requiredAction=cviForm.getActionName();
        String stateCode=cviForm.getState();
        String lgaCode=cviForm.getLga();
        String orgCode=cviForm.getOrganization();

        DaoUtil util=new DaoUtil();
        HttpSession session=request.getSession();

        ChildVulnerabilityIndexDao cviDao=new ChildVulnerabilityIndexDaoImpl();
        StatesDao sDao=new StatesDaoImpl();
        LgaDao lDao=new LgaDaoImpl();
        
        List lgaList=new ArrayList();
        List cboList=new ArrayList();
        List paramList=new ArrayList();
        List list=new ArrayList();
        List stateList=new ArrayList();

        if(requiredAction==null)
        {
            list=cviDao.getCVIStateList();
            for(Object obj:list)
            {
                States state=sDao.getStateByStateCode(obj.toString());
                if(state !=null)
                stateList.add(state);
            }
            
            session.setAttribute("cviStateList",stateList);
            session.setAttribute("cviLgaList",lgaList);
            session.setAttribute("cviOrgList", cboList);
            return mapping.findForward("paramPage");
        }

        else if(requiredAction.equals("lga"))
        {
            list=cviDao.getCVILgaList(stateCode);
            for(Object obj:list)
            {
                Lgas lga=lDao.getLgaByCode(obj.toString());
                if(lga !=null)
                lgaList.add(lga);
            }
            session.setAttribute("cviLgaList", lgaList);
            session.setAttribute("cviOrgList", cboList);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("cbo"))
        {
            OrganizationRecords orgRecords=null;
            if(lgaCode !=null)
            lgaCode=lgaCode.trim();
            List orgList=new ArrayList();
            cboList =util.getOrganizationalUnits(requiredAction,stateCode,lgaCode,orgCode,null);
            for(Object org:cboList)
            {
                orgRecords=util.getOrganizationRecords(org.toString());
                orgList.add(orgRecords);
            }
            session.setAttribute("cviOrgList", orgList);
            return mapping.findForward("paramPage");
        }
        else if(requiredAction.equals("cviReportList"))
        {
            String stateName=util.getStateName(stateCode);
            String lgaName=util.getLgaName(lgaCode);
            String cboName=util.getOrganizationName(orgCode);
            String param[]={stateCode,lgaCode,orgCode,stateName,lgaName,cboName};
            OvcRecords records=new OvcRecords();
            //records.getCVIRecords(request,param);
        }
        
        return mapping.findForward(SUCCESS);
    }
}
