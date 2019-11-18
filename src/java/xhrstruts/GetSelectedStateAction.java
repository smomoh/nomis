package xhrstruts;

import com.fhi.kidmap.business.Lga;
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


public class GetSelectedStateAction extends Action {


    private Lga lga = null;
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


   //((String[])httpSession.getAttribute("selectionArray"))[2] = stateCode;

   ((String[])httpSession.getAttribute("selectionArray"))[2] = stateCode;


    ArrayList characters = new ArrayList();

     try{
       characters = getLgas(stateCode);
     } catch(Exception e){



     }



    String html = "<select name=\"LgasSelect\" id=\"LgasSelect\" Class=\"smallfieldcellinput\" style=\"width:273px;\" onChange=\"retrieveURL4('getSelectedLga.do?lgaCode=' + this.value);\">";
    int i = 0;

    html += "<option value=\"" + " " + "\">" + " " + "</option>";
    html += "<option value=\"" + "All" + "\">" + "All" + "</option>";


    for (Iterator it = characters.iterator(); it.hasNext();) {

      lga = (Lga)it.next();

      String name = lga.getLgaName();
      String code = lga.getLgaCode();

      i++;

      html += "<option value=\"" + code + "\">" + name + "</option>";

    }
    html += "</select>";


    // Write the HTML to response
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println(html);
    out.flush();

    return null; // Not forwarding to anywhere, response is fully-cooked



  } // End execute()


  private ArrayList getLgas(String stateCode) throws Exception {

    ArrayList al = new ArrayList();


    if (stateCode.equalsIgnoreCase(" ")) {
      al.add("");
      return al;

    }



    List list = new ArrayList();


        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();


        list = session.createQuery("from Lga lga where lga.stateCode = :se")
                           .setString("se", stateCode)
                           .list();



        tx.commit();
        session.close();
        } catch (HibernateException he) {
            throw new Exception(he);
        }

    return al = (ArrayList)list;


  } // End getLgas()


} // End class
