/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.ReferralDirectory;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.ReferralDirectoryDao;
import com.fhi.kidmap.dao.ReferralDirectoryDaoImpl;
import com.fhi.nomis.nomisutils.AccessManager;
import com.fhi.nomis.nomisutils.AppUtility;
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
public class ReferralDirectoryAction extends org.apache.struts.action.Action {

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
            throws Exception 
    {
        ReferralDirectoryForm rdf=(ReferralDirectoryForm)form;
        ReferralDirectoryDao rddao=new ReferralDirectoryDaoImpl();
        HttpSession session=request.getSession();
        AppUtility appUtil=new AppUtility();
        User user=appUtil.getUser(session);
        String[] exceptions={"."};
        //DaoUtil util=new DaoUtil();
        
        String stateCode=rdf.getState();
        String lgaCode=rdf.getLga();
        String requiredAction=rdf.getActionName();
        String param=request.getParameter("p");
        if(param !=null)
        requiredAction=param;
        if(session.getAttribute("stateListInDirectory")==null)
        loadStartupParameters(user,session);
        
        rdf.setState(stateCode);
        rdf.setLga(lgaCode);
        ReferralDirectory rd=new ReferralDirectory();
        rd.setAddress(rdf.getAddress());
        rd.setCommunity(rdf.getCommunity());
        rd.setContactEmail(rdf.getContactEmail());
        rd.setContactNumber(rdf.getContactNumber());
        rd.setDateModified(appUtil.getCurrentDate());
        rd.setFacilityId(rdf.getFacilityId());
        rd.setFacilityName(rdf.getFacilityName());
        rd.setLatitude(appUtil.removeCharactersFromNumbers(rdf.getLatitude(),exceptions));
        rd.setLongitude(appUtil.removeCharactersFromNumbers(rdf.getLongitude(),exceptions)); 
        rd.setNameOfContact(rdf.getNameOfContact());
        rd.setTypeOfOrganization(rdf.getTypeOfOrganization());
        loadfacility(request);
        
        session.setAttribute("rdSaveBtnDisabled", "false");
        session.setAttribute("rdModifyBtnDisabled", "true");
        if(requiredAction==null)
        {
            //loadStartupParameters(session);
            rdf.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("lga"))
        {
            loadLga(user,stateCode,session);
        }
        else if(requiredAction.equalsIgnoreCase("ward"))
        {
            loadWard(stateCode,lgaCode,session);
        }
        else if(requiredAction.equalsIgnoreCase("ed"))
        {
            String facilityId=request.getParameter("id");
            rd=rddao.getReferralDirectory(facilityId);
            rdf.reset(mapping, request);
            rdf.setState(stateCode);
            rdf.setLga(lgaCode);
            if(rd !=null)
            {
                rdf.setAddress(rd.getAddress());
                rdf.setCommunity(rd.getCommunity());
                rdf.setContactEmail(rd.getContactEmail());
                rdf.setContactNumber(rd.getContactNumber());
                rdf.setFacilityName(rd.getFacilityName());
                rdf.setFacilityId(rd.getFacilityId());
                rdf.setLatitude(rd.getLatitude());
                rdf.setLongitude(rd.getLongitude());
                rdf.setNameOfContact(rd.getNameOfContact());
                rdf.setTypeOfOrganization(rd.getTypeOfOrganization());
                rdf.setLga(lgaCode);
                rdf.setState(stateCode);
                session.setAttribute("rdSaveBtnDisabled", "true");
                session.setAttribute("rdModifyBtnDisabled", "false");
                return mapping.findForward("paramPage");
            }
        }
        else if(requiredAction.equalsIgnoreCase("save"))
        {
            rddao.saveReferralDirectory(rd);
            loadfacility(request);
            rdf.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("modify"))
        {
            rddao.updateReferralDirectory(rd);
            loadfacility(request);
            session.setAttribute("rdSaveBtnDisabled", "false");
            session.setAttribute("rdModifyBtnDisabled", "true");
            rdf.reset(mapping, request);
        }
        else if(requiredAction.equalsIgnoreCase("delete"))
        {
            rddao.deleteReferralDirectory(rd);
            loadfacility(request);
            session.setAttribute("rdSaveBtnDisabled", "false");
            session.setAttribute("rdModifyBtnDisabled", "true");
            rdf.reset(mapping, request);
        }
        rdf.reset(mapping, request);
        return mapping.findForward("paramPage");
    }
    private void loadStartupParameters(User user,HttpSession session)
    {
        DaoUtil util=new DaoUtil();
        List stateList=AccessManager.getInstance().getStateListForReports(session);//getStateListFromUser(appUtil.getCurrentUser(session));
        session.setAttribute("stateListInDirectory", stateList);
        if(stateList !=null && !stateList.isEmpty())
        {
            States state=(States)stateList.get(0);
            loadLga(user,state.getState_code(),session);
        }
    }
    private void loadLga(User user,String stateCode,HttpSession session)
    {
        DaoUtil util=new DaoUtil();
        List lgaList=AccessManager.getInstance().getListOfLgasAssignedToUser(user,stateCode);//util.getLgaListByStateCode(stateCode);
        if(lgaList !=null && !lgaList.isEmpty())
        {
            Lgas lga=(Lgas)lgaList.get(0);
            loadWard(stateCode,lga.getLga_code(),session);
        }
        session.setAttribute("lgaListInDirectory", lgaList);
    }
    private void loadWard(String stateCode,String lgaCode,HttpSession session)
    {
        DaoUtil util=new DaoUtil();
        List wardList=util.getWardListByStateAndLgaCodes(stateCode,lgaCode);
        session.setAttribute("wardListInDirectory", wardList);
    }
    private void loadfacility(HttpServletRequest request)
    {
        ReferralDirectoryDao rddao=new ReferralDirectoryDaoImpl();
        try
        {
            List facilityList=rddao.getAllReferralDirectories();
            if(facilityList !=null && !facilityList.isEmpty())
            request.setAttribute("facilityList", facilityList);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
