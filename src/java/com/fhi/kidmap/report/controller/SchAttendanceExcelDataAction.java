/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

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
public class SchAttendanceExcelDataAction extends org.apache.struts.action.Action {
    
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
        List countList=(List)session.getAttribute("schoolAttendanceCount");
        List schoolAttendanceCount=new ArrayList();
        String schoolAttendanceExcelData="School name"+"\t"+"Number of OVC"+"\r\n";

                for(Object s:countList)
                {
                    String listElement="";
                    schoolAttendanceCount=(List)s;
                    int total=0;
                    int j=schoolAttendanceCount.size();
                    for(int i=0; i<j; i++)
                    {
                        listElement=schoolAttendanceCount.get(i).toString();
                        schoolAttendanceExcelData+=listElement+"\t";

                    }

                    schoolAttendanceExcelData+="\r\n";
                }
               session.setAttribute("attributename","School Attendance Summary");
               session.setAttribute("School Attendance Summary",schoolAttendanceExcelData);
        
        return mapping.findForward(SUCCESS);
    }
}
