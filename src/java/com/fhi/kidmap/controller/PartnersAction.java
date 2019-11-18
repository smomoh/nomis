/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.Partners;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;
import com.fhi.nomis.OperationsManagement.PartnerManager;
import com.fhi.nomis.nomisutils.AccessManager;
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
public class PartnersAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    
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

        PartnersForm pform=(PartnersForm)form;
        Partners partner=new Partners();
        String partnerCode=pform.getPartnerCode();//partner.setPartnerCode(pform.getPartnerCode());//.toUpperCase().trim());
        if(partnerCode !=null)
        partnerCode=partnerCode.trim().toUpperCase();
        partner.setPartnerCode(partnerCode);
        partner.setPartnerName(pform.getPartnerName());
        PartnersDao pdao=new PartnersDaoImpl();
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        AccessManager acm=new AccessManager();
        boolean userInRole=acm.isUserInRole("008",appUtil.getCurrentUser(session));
        if(!userInRole)
        {
            request.setAttribute("errorMsg", appUtil.getAccessDeniedMessage());
            return mapping.findForward("accessDenied");
        }
        LoadUpInfo loadup=new LoadUpInfo();

        String requiredAction=pform.getActionName();
        
        //if(!appUtil.hasPriviledgeToAccessPage(session))
        //return mapping.findForward("Nouserpriviledge");
        try
        {
            if(requiredAction==null)
            {
                form.reset(mapping, request);
                
                loadup.setParamAttributes(request);
                loadup.getPartners(session);
                List list=pdao.getPartnerList(pform.getPartnerList());
                request.setAttribute("modifyDisabled","true");
                request.setAttribute("saveDisabled","false");
                
                session.setAttribute("partnerList", list);
                return mapping.findForward(SUCCESS);
            }
            else if(requiredAction.equalsIgnoreCase("save"))
            {
                pdao.savePartners(partner);
            }
            else if(requiredAction.equalsIgnoreCase("modify"))
            {
                partner.setPartnerCode(pform.getPartnerCode());
                pdao.updatePartners(partner);
            }
            else if(requiredAction.equalsIgnoreCase("delete"))
            {
                partner.setPartnerCode(pform.getPartnerCode());
                pdao.deletePartners(partner);
            }
            else if(requiredAction.equalsIgnoreCase("partnerDetails"))
            {
                partner=PartnerManager.getPartner(pform.getPartnerList());
                pform.setPartnerCode(partner.getPartnerCode());
                pform.setPartnerName(partner.getPartnerName());
                   /*List list=pdao.getPartner(pform.getPartnerList());
                    if(list !=null && !list.isEmpty())
                    {
                        partner=(Partners)list.get(0);
                        pform.setPartnerCode(partner.getPartnerCode());
                        pform.setPartnerName(partner.getPartnerName());
                    }*/
                    request.setAttribute("modifyDisabled","false");
                    request.setAttribute("saveDisabled","true");
                    return mapping.findForward(SUCCESS);
           }
            loadup.getPartners(session);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return mapping.findForward(FAILURE);
        }
        form.reset(mapping, request);
        return mapping.findForward(SUCCESS);
    }
}
