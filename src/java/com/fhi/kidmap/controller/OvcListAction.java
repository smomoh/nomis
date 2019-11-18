/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

/**
 *
 * @author HP USER
 */
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import java.util.ArrayList;
import java.util.List;
public class OvcListAction extends Action {
protected List getOvcs() {
Ovc ovcObj = null;
List ovcs = new ArrayList();

try {

 List list = new ArrayList();


            OvcDao dao = new OvcDaoImpl();
            list = dao.getOvcs();

if(list != null) {
     for (Object s: list) {

                Ovc ovc = (Ovc)s;



ovcObj = new Ovc();
ovcObj.setOvcId(ovc.getOvcId());
ovcObj.setDateEnrollment(ovc.getDateEnrollment());
ovcObj.setState(ovc.getState());
ovcObj.setLga(ovc.getLga());
ovcObj.setWard(ovc.getWard());
ovcObj.setLastName(ovc.getLastName());
ovcObj.setFirstName(ovc.getFirstName());
ovcObj.setGender(ovc.getGender());
ovcObj.setAge(ovc.getAge());
ovcObj.setAgeUnit(ovc.getAgeUnit());
ovcs.add(ovcObj);
System.err.println("Name : "
+ ovcObj.getLastName() + "" + ovcObj.getFirstName());

}
}

}catch(Exception e) {
System.err.println(e.getMessage());
}

return ovcs;
}
    @Override
public ActionForward execute(ActionMapping mapping,
ActionForm form,
HttpServletRequest request,
HttpServletResponse response)
throws IOException, ServletException {


String target = null;

target = "success";

 OvcActionMapping ovcMapping =
(OvcActionMapping)mapping;


 
// Does this action require the user to login
if ( ovcMapping.isLoginRequired() ) {
HttpSession session = request.getSession();
if ( session.getAttribute("USER") == null ) {
// The user is not logged in
target = new String("login");
ActionErrors errors = new ActionErrors();
errors.add(ActionErrors.GLOBAL_MESSAGE,
new ActionMessage("errors.login.required"));
// Report any errors we have discovered back to
// the original form
if (!errors.isEmpty()) {
saveErrors(request, errors);

}
}
}



 List ovcs = getOvcs();

 request.setAttribute("ovcs", ovcs);

 request.getSession().setAttribute("ovcs", ovcs);

 
// Forward to the appropriate View
//return (mapping.findForward(target));
return (ovcMapping.findForward(target));
}
}

