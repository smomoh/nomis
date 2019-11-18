
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xhrstruts;

import com.fhi.kidmap.business.CboSetup;
import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.State;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.dao.StatesDao;
import com.fhi.kidmap.dao.StatesDaoImpl;
import com.fhi.kidmap.dao.CboSetupDao;
import com.fhi.kidmap.dao.CboSetupDaoImpl;
import com.fhi.kidmap.dao.HibernateUtil;
import com.fhi.kidmap.dao.LgaDao;
import com.fhi.kidmap.dao.LgaDaoImpl;
import com.fhi.kidmap.dao.StateDao;
import com.fhi.kidmap.dao.StateDaoImpl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author HP USER
 */
public class GetSetUpInfoAction extends Action {

    private Lgas lga = null;
    private States state = null;
    Session session;
    Transaction tx;



    @Override
     public ActionForward execute(ActionMapping mapping, ActionForm inForm, HttpServletRequest request, HttpServletResponse response) throws Exception {



         HttpSession httpSession = request.getSession();
        if(httpSession.getAttribute("selectionArray") == null) {

            String[] selectionArray = new String[3];

            httpSession.setAttribute("selectionArray", selectionArray);

        }



    String stateCode = (String)request.getParameter("stateCode");
    if (stateCode == null) {
      stateCode = "";
    }

     String lgaCode = (String)request.getParameter("lgaCode");
    if (lgaCode == null) {
      lgaCode = "";
    }



     //((String[])httpSession.getAttribute("selectionArray"))[0] = lgaCode;

   //((String[])httpSession.getAttribute("selectionArray"))[2] = stateCode;

   //((String[])httpSession.getAttribute("selectionArray"))[2] = stateCode;




    List list = new ArrayList();


        try{

            CboSetupDao dao = new CboSetupDaoImpl();
            list = dao.getCboSetupInfo();
        }catch (Exception e) {

        }


    String msg = " ";

    if(!list.isEmpty())
    {
        System.err.println("Before cbosetup. List is not empty ");
       CboSetup cboSetup = (CboSetup)list.get(0);
        try{
       StatesDao dao = new StatesDaoImpl();

       list = dao.getState(cboSetup.getState_code());

       if(!list.isEmpty()) {

           state = (States)list.get(0);
       }



       }catch(Exception e) {
           System.err.println("state exception "+e.getMessage());
       }


       
        try
        {
            List lgalist=new ArrayList();
            LgaDao dao = new LgaDaoImpl();
            lgalist = dao.getLgaByLgaCode(cboSetup.getLga_code());
            //lga = dao.getLga(cboSetup.getLga_code());
            if(!lgalist.isEmpty())
            {
                lga=(Lgas)lgalist.get(0);
            }



       ((String[])httpSession.getAttribute("selectionArray"))[2] = state.getState_code();
       ((String[])httpSession.getAttribute("selectionArray"))[0] = lga.getLga_code();
      


       msg = "msg@setupinfo@" + state.getName() + "@" + lga.getLga_name() + "@" + state.getState_code() + "@" + lga.getLga_code() + "@";


       }catch(Exception e) {

           msg = "lga" + e.getMessage();

       }


       }
    
    //response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println(msg);
    out.flush();

    return null; // Not forwarding to anywhere, response is fully-cooked


     }


}
