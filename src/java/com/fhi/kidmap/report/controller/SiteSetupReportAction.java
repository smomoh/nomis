/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.CboSetup;
import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.Partners;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.dao.CboSetupDao;
import com.fhi.kidmap.dao.CboSetupDaoImpl;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.LgaDao;
import com.fhi.kidmap.dao.LgaDaoImpl;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.kidmap.dao.StatesDao;
import com.fhi.kidmap.dao.StatesDaoImpl;
import com.fhi.nomis.OperationsManagement.PartnerManager;
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
public class SiteSetupReportAction extends org.apache.struts.action.Action {
    
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
        SiteSetupReportForm setupForm=(SiteSetupReportForm)form;
        CboSetupDao dao=new CboSetupDaoImpl();
        StatesDao sDao=new StatesDaoImpl();
        LgaDao lDao=new LgaDaoImpl();
        DaoUtil util=new DaoUtil();

        String requiredAction=setupForm.getActionName();
        String stateCode=setupForm.getState();
        String lgaCode=setupForm.getLga();
        String userName=setupForm.getUserName();
        HttpSession session=request.getSession();
        List stateCodeList=dao.getStateListFromCboSetup();

        //String removeParam=request.getParameter("actionName");
        
        
        List stateList=new ArrayList();
        
        for(int i=0; i<stateCodeList.size(); i++)
        {
                States state=(States)sDao.getStateByStateCode(stateCodeList.get(i).toString());
                if(state !=null)
                stateList.add(state);
        }
        if(requiredAction ==null)
        {
            session.setAttribute("siteSetupStateList",stateList);
            session.setAttribute("siteSetupLgaList", new ArrayList());
            return mapping.findForward(SUCCESS);
        }

        if(requiredAction !=null && requiredAction.equalsIgnoreCase("lga"))
        {
            List lgaList=new ArrayList();

            List list=dao.getLgaListFromCboSetup(stateCode);
            
            for(Object obj:list)
            {
                Lgas lga=lDao.getLgaByCode(obj.toString());
                if(lga !=null)
                lgaList.add(lga);
            }
            session.setAttribute("siteSetupLgaList", lgaList);
        }
        else if(requiredAction !=null && requiredAction.equalsIgnoreCase("setupList"))
        {
            List siteSetupList=new ArrayList();
            PartnersDao partnerdao=new PartnersDaoImpl();
            Partners partner=new Partners();
            List list=dao.getListOfSiteSetup(stateCode, lgaCode);
            for(Object obj:list)
            {
                CboSetup siteSetup=(CboSetup)obj;
                siteSetup.setState_code(util.getStateName(siteSetup.getState_code()));
                siteSetup.setLga_code(util.getLgaName(siteSetup.getLga_code()));
                partner=PartnerManager.getPartner(siteSetup.getPartner());
                /*List partnerList=partnerdao.getPartner(siteSetup.getPartner());
                if(!partnerList.isEmpty())
                partner=(Partners)partnerList.get(0);*/
                siteSetup.setPartner(partner.getPartnerName());
                siteSetupList.add(siteSetup);
            }
            request.setAttribute("siteSetupList", siteSetupList);
            return mapping.findForward("siteSetupReportPage");
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            System.err.println("Action name is "+requiredAction);
            CboSetup cbosetup=new CboSetup();
            cbosetup.setUserName(userName);
            dao.deleteCboSetupInfo(cbosetup);
            return mapping.findForward(SUCCESS);
        }
        return mapping.findForward(SUCCESS);
        
    }
}
