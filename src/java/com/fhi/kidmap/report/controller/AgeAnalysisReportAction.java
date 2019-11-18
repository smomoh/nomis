/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.nomis.nomisutils.ExcelWriter;
import com.fhi.kidmap.business.HouseholdEnrollment;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import com.fhi.kidmap.dao.StatesDao;
import com.fhi.kidmap.dao.StatesDaoImpl;
import com.fhi.kidmap.report.SummaryStatisticsBean;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.write.WritableWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class AgeAnalysisReportAction extends org.apache.struts.action.Action {
    
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
        AgeAnalysisReportForm aar=(AgeAnalysisReportForm)form;
        OvcDao dao=new OvcDaoImpl();
        String requiredAction=aar.getActionName();
        int currentAge=aar.getCurrentAge();
        String logic=aar.getLogic();
        String download=aar.getDownload();
        if(download !=null)
        requiredAction=download;
        String name=request.getParameter("name");
        HttpSession session=request.getSession();
        if(name !=null)
        {
            requiredAction=name;
        }
        if(requiredAction==null)
        {
           List list=dao.getCurrentAgeFromEnrollment();
           session.setAttribute("currentAgeList", list);
           return mapping.findForward("paramPage");
        }
        else if(requiredAction.equalsIgnoreCase("list"))
        {
            String param=request.getParameter("id");
            if(param !=null && param.indexOf("-") !=-1)
            {
                String[] paramArray=param.split("-");
                String stateCode=paramArray[0];
                logic=paramArray[1];
                currentAge=Integer.parseInt(paramArray[2]);
                try
                {
                List list=dao.getListOfOvcsByCurrentAge(stateCode,logic, currentAge);
                if(list !=null)
                {
                    Ovc ovc=null;
                    HouseholdEnrollment hhe=null;
                    States state=null;
                    StatesDao sdao=new StatesDaoImpl();
                    List ovcList=new ArrayList();
                    for(int i=0; i<list.size(); i++)
                    {
                        Object[] obj=(Object[])list.get(i);
                        hhe=(HouseholdEnrollment)obj[0];
                        ovc=(Ovc)obj[1];
                        state=sdao.getStateByStateCode(hhe.getStateCode());
                        ovc.setState(state.getName());
                        ovc.setSerialNo(i+1);
                        ovcList.add(ovc);
                    }
                    session.setAttribute("ovcAgeAnalysisList", ovcList);
                    return mapping.findForward("list");
                }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            System.err.println("param is "+param);
        }
        else if(requiredAction.equalsIgnoreCase("report"))
        {
            List reportList=getAgeAnalysisReportList(logic,currentAge);
            session.setAttribute("ssbreportList", reportList);
            /*StatesDao sdao=new StatesDaoImpl();
            States state=null;
            List reportList=new ArrayList();
            List list=dao.getCountOfOvcsByCurrentAge(logic,currentAge);
            SummaryStatisticsBean ssb=null;
            if(list !=null)
            {
                String stateCode=null;
                int numberOfOvc=0;
                for(int i=0; i<list.size(); i++)
                {
                    ssb=new SummaryStatisticsBean();

                    Object[] obj=(Object[])list.get(i);
                    stateCode=(String)obj[0];
                    ssb.setStateCode(stateCode+"-"+logic+"-"+currentAge);
                    numberOfOvc=Integer.parseInt(obj[1].toString());
                    state=sdao.getStateByStateCode(stateCode);
                    ssb.setState(state.getName());
                    ssb.setNoOfOvc(numberOfOvc);
                    reportList.add(ssb);
                    //System.err.println("state "+state.getName()+" count="+numberOfOvc);
                }
                session.setAttribute("ssbreportList", reportList);
                return mapping.findForward(SUCCESS);
            }*/
            return mapping.findForward(SUCCESS);
        }
        else if(requiredAction.equalsIgnoreCase("download"))
        {
            String fileName="Age_Analysis";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
            OutputStream os=response.getOutputStream();
            ExcelWriter ew=new ExcelWriter();
            String ageCriteriaLabel=logic+currentAge;
            List reportList=getAgeAnalysisReportList(logic,currentAge);
            WritableWorkbook wworkbook=ew.writeAgeAnalysisValuesToExcel(os, reportList,ageCriteriaLabel);
            if(wworkbook !=null)
            {
                wworkbook.write();
                wworkbook.close();
            }
            os.close();
            aar.reset(mapping, request);
        }
        aar.reset(mapping, request);
        return mapping.findForward("paramPage");
    }
    private List getAgeAnalysisReportList(String logic,int currentAge)
    {
        List reportList=new ArrayList();
        States state=null;
        String lgaName=null;
        String cboName=null;
        DaoUtil util=new DaoUtil();
        OvcDao dao=new OvcDaoImpl();
        int totalNoOfOvc=0;
        try
        {
        List list=dao.getCountOfOvcsByCurrentAge(logic,currentAge);
            SummaryStatisticsBean ssb=null;
            if(list !=null)
            {
                String stateCode=null;
                String lgaCode=null;
                String cboCode=null;
                int numberOfOvc=0;
                for(int i=0; i<list.size(); i++)
                {
                    ssb=new SummaryStatisticsBean();

                    Object[] obj=(Object[])list.get(i);
                    stateCode=(String)obj[0];
                    lgaCode=(String)obj[1];
                    cboCode=(String)obj[2];
                    lgaName=util.getLgaName(lgaCode);
                    if(lgaName==null || lgaName.equalsIgnoreCase("") || lgaName.equalsIgnoreCase(" ") || lgaName.equalsIgnoreCase("  "))
                    lgaName=lgaCode;
                    cboName=util.getOrganizationName(cboCode);
                    ssb.setStateCode(stateCode+"-"+logic+"-"+currentAge);
                    numberOfOvc=Integer.parseInt(obj[3].toString());
                    totalNoOfOvc=dao.getNumberOfOvcEnrolledPerCBO(stateCode,lgaCode,cboCode);
                    ssb.setState(util.getStateName(stateCode));
                    ssb.setLga(lgaName);
                    ssb.setCbo(cboName);
                    ssb.setTotNoOfOvc(totalNoOfOvc);
                    ssb.setNoOfOvc(numberOfOvc);
                    ssb.setValue3(dao.getNumberOfOvcOutOfSchoolPerCBO(stateCode, lgaCode, cboCode));
                    ssb.setValue4(dao.getNumberOfOvcOutOfSchoolByAgeGroupAndPerCBO(stateCode, lgaCode, cboCode,logic,currentAge));
                    reportList.add(ssb);
                    //System.err.println("state "+state.getName()+" count="+numberOfOvc);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return reportList;
    }
        
}
