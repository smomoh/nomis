
package xhrstruts;


import com.fhi.kidmap.business.Ward;
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


public class GetSelectedCboAction extends Action {

    private Ward ward = null;
    Session session;
    Transaction tx;


    @Override
  public ActionForward execute(ActionMapping mapping, ActionForm inForm, HttpServletRequest request, HttpServletResponse response) throws Exception {

         HttpSession httpSession = request.getSession();

    String cboCode = (String)request.getParameter("cboCode");
    if (cboCode == null) {
      cboCode = "";
    }




    if (!cboCode.equals(" ") || !cboCode.equals("")) {

    ((String[])httpSession.getAttribute("selectionArray"))[1] = cboCode;
    }



     ArrayList characters = new ArrayList();

     try{
        characters = getWards(cboCode);
     } catch(Exception e) {

     }


    String html = "<select name=\"WardsSelect\" Class=\"smallfieldcellinput\" style=\"width:130px;\" onChange=\"enableControl(this.value);\">";
    int i = 0;

    html += "<option value=\"" + " " + "\">" + " " + "</option>";

    for (Iterator it = characters.iterator(); it.hasNext();) {
      ward = (Ward)it.next();
      String name = ward.getWardName();
      String code = ward.getWardCode();
      i++;

      html += "<option value=\"" + code + "\">" + name + "</option>";
      //html += "<option value=\"" + name + "\">" + name + "</option>";
    }
    html += "</select>";

    // Write the HTML to response
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println(html);
    out.flush();

    return null; // Not forwarding to anywhere, response is fully-cooked


  } // End execute()



  private ArrayList getWards(String cboCode) throws Exception {

    ArrayList al = new ArrayList();


    if (cboCode.equalsIgnoreCase(" ")) {
      al.add("");
      return al;

    }



    List list = new ArrayList();


        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();


        list = session.createQuery("from Ward ward where ward.cboCode = :cc")
                           .setString("cc", cboCode)
                           .list();



        tx.commit();
        session.close();
        } catch (HibernateException he) {
            throw new Exception(he);
        }

    return al = (ArrayList)list;


  } // End getWards()


} // End class
