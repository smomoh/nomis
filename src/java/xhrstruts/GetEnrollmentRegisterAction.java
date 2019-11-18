
package xhrstruts;


import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.business.OvcRegisterObject;
import com.fhi.kidmap.dao.ChildStatusIndexDao;
import com.fhi.kidmap.dao.ChildStatusIndexDaoImpl;
import com.fhi.kidmap.dao.HibernateUtil;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fhi.kidmap.controller.OvcActionMapping;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;




public class GetEnrollmentRegisterAction extends Action {




    private Ovc ovc = null;
    //private OvcRegisterObject ovcObj = null;
    private ChildStatusIndex childStatusIndex = null;
    Session session;
    Transaction tx;
    String[] selectionArray;


    @Override
  public ActionForward execute(ActionMapping mapping, ActionForm inForm, HttpServletRequest request, HttpServletResponse response) throws Exception {


        HttpSession httpSession = request.getSession();
        if(httpSession.getAttribute("enrollmentParameters") == null) {

            String[] enrollmentParameters = new String[5];

            httpSession.setAttribute("enrollmentParameters", enrollmentParameters);

        }






    // Get a list of characters associated with the select TV show
     String enrollmentRegisterCbo = (String)request.getParameter("enrollmentRegisterCbo");
    if (enrollmentRegisterCbo == null) {
      enrollmentRegisterCbo = "";
    }

    String enrollmentRegisterMonth1 = (String)request.getParameter("enrollmentRegisterMonth1");
    if (enrollmentRegisterMonth1 == null) {
      enrollmentRegisterMonth1 = "";
    }

    String enrollmentRegisterYear1 = (String)request.getParameter("enrollmentRegisterYear1");
    if (enrollmentRegisterYear1 == null) {
      enrollmentRegisterYear1 = "";
    }

    String enrollmentRegisterMonth2 = (String)request.getParameter("enrollmentRegisterMonth2");
    if (enrollmentRegisterMonth2 == null) {
      enrollmentRegisterMonth2 = "";
    }

    String enrollmentRegisterYear2 = (String)request.getParameter("enrollmentRegisterYear2");
    if (enrollmentRegisterYear2 == null) {
      enrollmentRegisterYear2 = "";
    }

    String resetEnrollReg = (String)request.getParameter("resetEnrollReg");
    if (resetEnrollReg == null) {
      resetEnrollReg = "";
    }

    String submitEnrollReg = (String)request.getParameter("submitEnrollReg");
    if (submitEnrollReg == null) {
      submitEnrollReg = "";
    }


    /*
    String age = (String)request.getParameter("age");
    if (age == null) {
      age = "";
    }

    String ageUnit = (String)request.getParameter("ageUnit");
    if (ageUnit == null) {
      ageUnit = "";
    }

    String caregiverName1 = (String)request.getParameter("caregiverName1");
    if (caregiverName1 == null) {
      caregiverName1 = "";
    }

    String caregiverName2 = (String)request.getParameter("caregiverName2");
    if (caregiverName2 == null) {
      caregiverName2 = "";
    }
    */





    /*
    String reset = (String)request.getParameter("reset");
    if (reset == null) {
      reset = "";
    }

    if(reset.equals("true")) {



    }
    */





    /*
    String idArray = (String)request.getParameter("idArray");
    if (idArray == null) {
      idArray = "";
    }

    List idTokens = new ArrayList();

    if(!idArray.equals("")) {

        String target = "success";

       idTokens = tokenizeStr(idArray);

       List ovcMatches = new ArrayList();

       try {

       ovcMatches = getOvcs(idTokens);

       }catch(Exception e) {
           target = "error";

       }

 request.setAttribute("ovcMatches", ovcMatches);

 request.getSession().setAttribute("ovcMatches", ovcMatches);


 OvcActionMapping ovcMapping =
(OvcActionMapping)mapping;



// Forward to the appropriate View
//return (mapping.findForward(target));
//return (ovcMapping.findForward(target));

 return null;

   }
*/




    //ArrayList characters = getCharacters(tvShow);
    //String msg1 = getMessage1(ovcId, selectionArray);
    String msg1 = "";

    String msg2 = "";

    String msg3 = "";



    if(resetEnrollReg.equals("true")) {
        int size = ((String[])httpSession.getAttribute("enrollmentParameters")).length;

        for(int i = 0; i < size; i++) {

            ((String[])httpSession.getAttribute("enrollmentParameters"))[i] = null;

        }

        msg1 = "Array reset";

        //return null;

    }

    if(!enrollmentRegisterCbo.equals("")) {
        ((String[])httpSession.getAttribute("enrollmentParameters"))[0] = enrollmentRegisterCbo;
        msg1 = "set";

    }

   if(!enrollmentRegisterMonth1.equals("")) {
        ((String[])httpSession.getAttribute("enrollmentParameters"))[1] = enrollmentRegisterMonth1;
        msg1 = "set";

    }

    if(!enrollmentRegisterYear1.equals("")) {
        ((String[])httpSession.getAttribute("enrollmentParameters"))[2] = enrollmentRegisterYear1;
        msg1 = "set";

    }


    if(!enrollmentRegisterMonth2.equals("")) {
        ((String[])httpSession.getAttribute("enrollmentParameters"))[3] = enrollmentRegisterMonth2;
        msg1 = "set";


    }

    if(!enrollmentRegisterYear2.equals("")) {
        ((String[])httpSession.getAttribute("enrollmentParameters"))[4] = enrollmentRegisterYear2;
        msg1 = "set";


    }


    /*
    if(!caregiverName1.equals("")) {

      ((String[])httpSession.getAttribute("charactersList2"))[3] = caregiverName1;
      //((List)httpSession.getAttribute("charactersList2")).add(caregiverName);
    }

   if(!caregiverName2.equals("")) {

      ((String[])httpSession.getAttribute("charactersList2"))[4] = caregiverName2;
      //((List)httpSession.getAttribute("charactersList2")).add(caregiverName);
   }
    */




    /*
    int contentCount1 = 0;

    for(Object s: ((String[])httpSession.getAttribute("enrollmentParameters"))) {

        if(s == null) continue;
        contentCount1++;
    }
    */



  /*
  if(contentCount1 == ((String[])httpSession.getAttribute("charactersList1")).length) {



        if(((String[])httpSession.getAttribute("charactersList1"))[1].equalsIgnoreCase("Jerry")) {

        msg2 = "msg2:error:" + ((String[])httpSession.getAttribute("charactersList1"))[0] + ", " + ((String[])httpSession.getAttribute("charactersList1"))[1] + ", "
                + ((String[])httpSession.getAttribute("charactersList1"))[2] + ", " + ((String[])httpSession.getAttribute("charactersList1"))[3] + ((String[])httpSession.getAttribute("charactersList1"))[4] + ":";
        }

    }*/




   //if(contentCount1 == ((String[])httpSession.getAttribute("enrollmentParameters")).length && submitEnrollReg.equals("true")) {

       //msg1 = "done";

       /*String dateFrom = ((String[])httpSession.getAttribute("enrollmentParameters"))[2] + "-"
                       + ((String[])httpSession.getAttribute("enrollmentParameters"))[1] + "-"
                       + "01";

       String dateTo = ((String[])httpSession.getAttribute("enrollmentParameters"))[4] + "-"
                     + ((String[])httpSession.getAttribute("enrollmentParameters"))[3] + "-"
                     + "31";

       String cbo = ((String[])httpSession.getAttribute("enrollmentParameters"))[0];*/




     String dateFrom = enrollmentRegisterYear1 + "-"
                       + enrollmentRegisterMonth1 + "-"
                       + "01";

       String dateTo = enrollmentRegisterYear2 + "-"
                     + enrollmentRegisterMonth2 + "-"
                     + "31";

       String cbo = enrollmentRegisterCbo;

       String target = "success";

       List ovcMatchesEnrollReg = new ArrayList();

       try {

       ovcMatchesEnrollReg = getOvcs(cbo, dateFrom, dateTo);

       }catch(Exception e) {
           target = "error";

       }

 request.setAttribute("ovcMatchesEnrollReg", ovcMatchesEnrollReg);

 request.getSession().setAttribute("ovcMatchesEnrollReg", ovcMatchesEnrollReg);


 msg1 = "success:done:";


 OvcActionMapping ovcMapping =
(OvcActionMapping)mapping;



    // Write the HTML to response
    //response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    //out.println(html);
    out.println(msg1);
    //out.println(msg2);
    //out.println(msg3);
    out.flush();

    return null; // Not forwarding to anywhere, response is fully-cooked

  } // End execute()


  






protected List tokenizeStr(String str) {

List tokenList = new ArrayList();

    StringTokenizer st = new StringTokenizer(str, "-");

    while(st.hasMoreTokens()) {

        tokenList.add(st.nextToken());

    }


 return tokenList;
}







protected List getOvcs(String cbo, String dateFrom, String dateTo) throws Exception {
OvcRegisterObject ovcObj = null;
List ovcs = new ArrayList();

try {


 List list = new ArrayList();


            
 try {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();

                if(!cbo.equals("All")) {

                   list = session.createQuery("from Ovc as ovc where ovc.completedbyCbo = :cbo and ovc.dateEnrollment >= :dateFrom and ovc.dateEnrollment <= :dateTo").setString("cbo", cbo).setString("dateFrom", dateFrom).setString("dateTo", dateTo).list();
                }
                else {

                    list = session.createQuery("from Ovc as ovc where ovc.dateEnrollment >= :dateFrom and ovc.dateEnrollment <= :dateTo").setString("dateFrom", dateFrom).setString("dateTo", dateTo).list();
                }




                tx.commit();
                session.close();
            } catch (HibernateException he) {
                throw new Exception(he);
            }
            






if(list != null) {


    //System.out.println("This is list size: " + list.size());

     for (Object s: list) {

                //Ovc ovc = (Ovc)s;
                ovc = (Ovc)s;



ovcObj = new OvcRegisterObject();



ovcObj.setOvcId(ovc.getOvcId());


if(ovc.getDateEnrollment() != null) {
ovcObj.setDateEnrollment(formatDate(ovc.getDateEnrollment()));
}
else ovcObj.setDateEnrollment(" ");

if(ovc.getState() != null) {
ovcObj.setState(ovc.getState());
}
else ovcObj.setState(" ");

if(ovc.getLga() != null) {
ovcObj.setLga(ovc.getLga());
}
else ovcObj.setLga(" ");

if(ovc.getWard() != null) {
ovcObj.setWard(ovc.getWard());
}
else ovcObj.setWard(" ");

if(ovc.getLastName() != null) {
ovcObj.setLastName(ovc.getLastName().toUpperCase());
//ovcObj.setFirstName(ovc.getFirstName().substring(0, ovc.getFirstName().indexOf(",")) + " " + ovc.getFirstName().substring(ovc.getFirstName().indexOf(" ") + 1) );
}
else ovcObj.setLastName(" ");

if(ovc.getFirstName() != null) {

try{

    ovcObj.setFirstName(ovc.getFirstName().substring(0, ovc.getFirstName().indexOf(",")) + " " + ovc.getFirstName().substring(ovc.getFirstName().indexOf(" ") + 1) );
}catch(Exception e) {

    ovcObj.setFirstName(ovc.getFirstName());
}
}
else ovcObj.setFirstName(" ");


if(ovc.getGender() != null) {
if(ovc.getGender().equals("Male")) {
    ovcObj.setGender("M");
}
else ovcObj.setGender("F");
}
else ovcObj.setGender(" ");



ovcObj.setAge(ovc.getAge());

if(ovc.getAgeUnit() != null) {
ovcObj.setAgeUnit(ovc.getAgeUnit());
}
else ovcObj.setAgeUnit(" ");

if(ovc.getAddress() != null) {
ovcObj.setAddress(ovc.getAddress());
}
else ovcObj.setAddress(" ");

if(ovc.getWard() != null) {
ovcObj.setWard(ovc.getWard());
}
else ovcObj.setWard(" ");



//ovcObj.setCaregiverName((ovc.getCaregiverName().substring(0, ovc.getCaregiverName().indexOf(","))).toUpperCase() + ", " + ovc.getCaregiverName().substring(ovc.getCaregiverName().indexOf(" ") + 1) );

if(ovc.getCaregiverName() != null) {

    try {

    ovcObj.setCaregiverName((ovc.getCaregiverName().substring(0, ovc.getCaregiverName().indexOf(","))).toUpperCase() + " " + ovc.getCaregiverName().substring(ovc.getCaregiverName().indexOf(" ") + 1) );
    }catch(Exception e) {

    ovcObj.setCaregiverName(ovc.getCaregiverName());
    }

}
else ovcObj.setCaregiverName(" ");


if(ovc.getEligibility() != null) {
    ovcObj.setEligibility(ovc.getEligibility());
}
else ovcObj.setEligibility(" ");

if(ovc.getHivStatus() != null) {
    ovcObj.setHivStatus(ovc.getHivStatus());
}
else ovcObj.setHivStatus(" ");

if(ovc.getBirthCertificate() != null) {
    ovcObj.setBirthCertificate(ovc.getBirthCertificate());
}
else ovcObj.setBirthCertificate(" ");

if(ovc.getSchoolStatus() != null) {
    ovcObj.setSchoolStatus(ovc.getSchoolStatus());
}
else ovcObj.setSchoolStatus(" ");

if(ovc.getSchoolName() != null) {
    ovcObj.setSchoolName(ovc.getSchoolName());
}
else ovcObj.setSchoolName(" ");

if(ovc.getCurrentClass() != null) {
    ovcObj.setCurrentClass(ovc.getCurrentClass());
}
else ovcObj.setCurrentClass(" ");

if(ovc.getCaregiverAddress() != null) {
    ovcObj.setCaregiverAddress(ovc.getCaregiverAddress());
}
else ovcObj.setCaregiverAddress(" ");

if(ovc.getCaregiverPhone() != null) {
    ovcObj.setCaregiverPhone(ovc.getCaregiverPhone());
}
else ovcObj.setCaregiverPhone(" ");



try{
    ChildStatusIndexDao dao = new ChildStatusIndexDaoImpl();

    childStatusIndex = dao.getChildStatusIndex(ovc.getOvcId(), ovc.getDateEnrollment());

    }catch(Exception e) {
      System.err.println(e.getMessage());
    }



if(childStatusIndex.getCsiFactor3() != 0) {
    ovcObj.setCsiFactor3(childStatusIndex.getCsiFactor3());
}

if(childStatusIndex.getCsiFactor4() != 0) {
    ovcObj.setCsiFactor4(childStatusIndex.getCsiFactor4());
}

if(childStatusIndex.getCsiFactor11() != 0) {
    ovcObj.setCsiFactor11(childStatusIndex.getCsiFactor11());
}

if(childStatusIndex.getCsiFactor12() != 0) {
    ovcObj.setCsiFactor12(childStatusIndex.getCsiFactor12());
}

if(childStatusIndex.getCsiFactor5() != 0) {
    ovcObj.setCsiFactor5(childStatusIndex.getCsiFactor5());
}

if(childStatusIndex.getCsiFactor6() != 0) {
    ovcObj.setCsiFactor6(childStatusIndex.getCsiFactor6());
}

if(childStatusIndex.getCsiFactor1() != 0) {
    ovcObj.setCsiFactor1(childStatusIndex.getCsiFactor1());
}

if(childStatusIndex.getCsiFactor2() != 0) {
    ovcObj.setCsiFactor2(childStatusIndex.getCsiFactor2());
}

if(childStatusIndex.getCsiFactor9() != 0) {
    ovcObj.setCsiFactor9(childStatusIndex.getCsiFactor9());
}

if(childStatusIndex.getCsiFactor10() != 0) {
    ovcObj.setCsiFactor10(childStatusIndex.getCsiFactor10());
}

if(childStatusIndex.getCsiFactor7() != 0) {
    ovcObj.setCsiFactor7(childStatusIndex.getCsiFactor7());
}

if(childStatusIndex.getCsiFactor8() != 0) {
    ovcObj.setCsiFactor8(childStatusIndex.getCsiFactor8());
}




if(ovc.getServiceEnrollment() != null) {
    ovcObj.setServiceEnrollment(ovc.getServiceEnrollment());
}
else ovcObj.setServiceEnrollment(" ");



//ovcObj.setCaregiverName("Now Null");

ovcs.add(ovcObj);
//System.err.println("Name : "
//+ ovcObj.getLastName() + " " + ovcObj.getFirstName());


}
}




}catch(Exception e) {
System.err.println(e.getMessage());
}

System.err.println("Total Count : " + ovcs.size());

return ovcs;
}




private String formatDate(String dateString) {

    List tokens = tokenizeStr(dateString);
    return tokens.get(2) + "/" + tokens.get(1) + "/" + tokens.get(0);


}









}//End class



