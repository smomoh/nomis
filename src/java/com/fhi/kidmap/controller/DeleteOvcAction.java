/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

/**
 *
 * @author HP USER
 */
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;


public class DeleteOvcAction extends Action {

protected void deleteOvc(String ovcId)
throws Exception {

String user = null;

//Connection conn = null;
//Statement stmt = null;
//ResultSet rs = null;
//ServletContext context = servlet.getServletContext();
//DataSource dataSource = (DataSource)
//context.getAttribute(Action.DATA_SOURCE_KEY);


try{
            OvcDao dao = new OvcDaoImpl();
            dao.deleteOvc(ovcId);

        }catch(Exception e) {
            //target = new String("failure");
          // s+= "6 update ovc " + e.getMessage();
           throw new Exception(e);
        }

}
    @Override
public ActionForward execute(ActionMapping mapping,
ActionForm form,
HttpServletRequest request,
HttpServletResponse response)
throws IOException, ServletException {
// Default target to success
String target = "success";
OvcActionMapping ovcMapping =
(OvcActionMapping)mapping;
// Does this action require the user to login
if ( ovcMapping.isLoginRequired() ) {
HttpSession session = request.getSession();
if ( session.getAttribute("USER") == null ) {
// The user is not logged in
target = "login";
ActionErrors errors = new ActionErrors();
errors.add(ActionErrors.GLOBAL_MESSAGE,
new ActionMessage("errors.login.required"));
// Report any errors we have discovered
// back to the original form
if (!errors.isEmpty()) {
saveErrors(request, errors);
}
return (ovcMapping.findForward(target));
}
}
try {

if(form != null) {

    deleteOvc(((OvcForm)form).getOvcId());
    ((OvcForm)form).reset(mapping, request);
}
else
deleteOvc(request.getParameter("ovcId"));
}
catch (Exception e) {
System.err.println("Setting target to error");
target = "error";
ActionErrors errors = new ActionErrors();
errors.add(ActionErrors.GLOBAL_MESSAGE,
new ActionMessage("errors.database.error",
e.getMessage()));
// Report any errors
if (!errors.isEmpty()) {
saveErrors(request, errors);
}
}
// Forward to the appropriate View
return (ovcMapping.findForward(target));
}
}
