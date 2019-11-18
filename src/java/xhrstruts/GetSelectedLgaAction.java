package xhrstruts;


import com.fhi.kidmap.business.Cbo;
import com.fhi.kidmap.dao.HibernateUtil;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
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


public class GetSelectedLgaAction extends Action {

    private Cbo cbo = null;
    Session session;
    Transaction tx;


    @Override
  public ActionForward execute(ActionMapping mapping, ActionForm inForm, HttpServletRequest request, HttpServletResponse response) throws Exception {

         HttpSession httpSession = request.getSession();


    String lgaCode = (String)request.getParameter("lgaCode");
    if (lgaCode == null) {
      lgaCode = "";
    }



     ((String[])httpSession.getAttribute("selectionArray"))[0] = lgaCode;



     ArrayList characters = new ArrayList();

     try{
        characters = getCbos(lgaCode);
     } catch(Exception e) {

     }


    String html = "<select name=\"CbosSelect\" id=\"CbosSelect\" Class=\"smallfieldcellinput\" style=\"width:250px;\" onChange=\"retrieveURL5('getSelectedCbo.do?cboCode=' + this.value);\">";
    int i = 0;

    html += "<option value=\"" + " " + "\">" + " " + "</option>";
     html += "<option value=\"" + "All" + "\">" + "All" + "</option>";

    for (Iterator it = characters.iterator(); it.hasNext();) {
      cbo = (Cbo)it.next();
      String name = cbo.getCboName();
      String code = cbo.getCboCode();

      i++;

      //html += "<option value=\"" + code + "\">" + name + "</option>";
      html += "<option value=\"" + name + "\">" + name + "</option>";
    }
    html += "</select>";

    // Write the HTML to response
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println(html);
    out.flush();

    return null; // Not forwarding to anywhere, response is fully-cooked


  } // End execute()



  private ArrayList getCbos(String lgaCode) throws Exception {

   ArrayList al = new ArrayList();


    if (lgaCode.equalsIgnoreCase(" ")) {
      al.add("");
      return al;

    }



    List list = new ArrayList();


        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();


        list = session.createQuery("from Cbo cbo where cbo.lgaCode = :lc")
                           .setString("lc", lgaCode)
                           .list();



        tx.commit();
        session.close();
        } catch (HibernateException he) {
            throw new Exception(he);
        }

    return al = (ArrayList)list;


  } // End getCbos()


} // End class
