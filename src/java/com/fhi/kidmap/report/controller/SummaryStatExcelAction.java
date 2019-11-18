/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.nomis.nomisutils.AppUtility;
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
public class SummaryStatExcelAction extends org.apache.struts.action.Action {
    
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
        HttpSession session=request.getSession();
        //session.setAttribute("summaryStatparams", param);
        AppUtility appUtil=new AppUtility();
        List countList=(List)session.getAttribute("summaryStatistics");
        List summStatCountList=new ArrayList();
        String summStatExcelData="Indicator name"+"\t"+"Male"+"\t"+"Female"+"\t"+"Total"+"\r\n";
        if(countList !=null && !countList.isEmpty())
        {
                for(Object s:countList)
                {
                    String listElement="";
                    summStatCountList=(List)s;
                    int total=0;
                    
                    for(int i=1; i<summStatCountList.size(); i++)
                    {
                        listElement=summStatCountList.get(i).toString();
                        summStatExcelData+=listElement+"\t";
                        if(!appUtil.isString(listElement))
                        {
                            if(i>0)
                            total+=Integer.parseInt(listElement);
                        }
                    }
                    summStatExcelData+=total+"\t"+"\r\n";
                }
               session.setAttribute("attributename","Summary Statistics");
               session.setAttribute("Summary Statistics",summStatExcelData);
        }

        return mapping.findForward(SUCCESS);
    }
}
