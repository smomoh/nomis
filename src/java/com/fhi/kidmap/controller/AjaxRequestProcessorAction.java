/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Siaka
 */
public class AjaxRequestProcessorAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
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
        AppUtility appUtil=new AppUtility();
        String requiredAction=request.getParameter("qparam");
        String servletContext=getServlet().getServletContext().getRealPath("/Resources/dbs");
        String userdir=appUtil.getUserHomeDirectory();
        String result=servletContext+" -- "+userdir;
        if(servletContext !=null && servletContext.indexOf("nomis") !=-1)
        {
            String seperator="\\";
            if(servletContext.indexOf("/") !=-1)
            seperator="/";
            servletContext=servletContext.substring(0, servletContext.indexOf(seperator+"nomis"));
            servletContext=servletContext.substring(0, servletContext.lastIndexOf(seperator));
            File file=new File(servletContext);
            if(file.exists() && file.isDirectory())
            {
                String[] contents=file.list();
                for(int i=0; i<contents.length; i++)
                {
                    servletContext=servletContext+"=="+contents[i];
                }
                result=servletContext;
            }
        }
        
        //System.err.println(result);
        PrintWriter out = response.getWriter();
        out.print(result);
        return null;
    }
}
