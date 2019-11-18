
package xhrstruts;


import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.kidmap.business.Ovc;
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




public class GetEnrollmentListAction extends Action {




    private Ovc ovc = null;
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
     String enrollmentListCbo = (String)request.getParameter("enrollmentListCbo");
    if (enrollmentListCbo == null) {
      enrollmentListCbo = "";
    }

    String enrollmentListMonth1 = (String)request.getParameter("enrollmentListMonth1");
    if (enrollmentListMonth1 == null) {
      enrollmentListMonth1 = "";
    }

    String enrollmentListYear1 = (String)request.getParameter("enrollmentListYear1");
    if (enrollmentListYear1 == null) {
      enrollmentListYear1 = "";
    }

    String enrollmentListMonth2 = (String)request.getParameter("enrollmentListMonth2");
    if (enrollmentListMonth2 == null) {
      enrollmentListMonth2 = "";
    }

    String enrollmentListYear2 = (String)request.getParameter("enrollmentListYear2");
    if (enrollmentListYear2 == null) {
      enrollmentListYear2 = "";
    }

    String resetEnrollList = (String)request.getParameter("resetEnrollList");
    if (resetEnrollList == null) {
      resetEnrollList = "";
    }

    String submitEnrollList = (String)request.getParameter("submitEnrollList");
    if (submitEnrollList == null) {
      submitEnrollList = "";
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



    if(resetEnrollList.equals("true")) {
        int size = ((String[])httpSession.getAttribute("enrollmentParameters")).length;

        for(int i = 0; i < size; i++) {

            ((String[])httpSession.getAttribute("enrollmentParameters"))[i] = null;

        }

        msg1 = "Array reset";

        //return null;

    }

    if(!enrollmentListCbo.equals("")) {
        ((String[])httpSession.getAttribute("enrollmentParameters"))[0] = enrollmentListCbo;
        msg1 = "set";

    }

   if(!enrollmentListMonth1.equals("")) {
        ((String[])httpSession.getAttribute("enrollmentParameters"))[1] = enrollmentListMonth1;
        msg1 = "set";

    }

    if(!enrollmentListYear1.equals("")) {
        ((String[])httpSession.getAttribute("enrollmentParameters"))[2] = enrollmentListYear1;
        msg1 = "set";

    }


    if(!enrollmentListMonth2.equals("")) {
        ((String[])httpSession.getAttribute("enrollmentParameters"))[3] = enrollmentListMonth2;
        msg1 = "set";


    }

    if(!enrollmentListYear2.equals("")) {
        ((String[])httpSession.getAttribute("enrollmentParameters"))[4] = enrollmentListYear2;
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



    int contentCount1 = 0;

    for(Object s: ((String[])httpSession.getAttribute("enrollmentParameters"))) {

        if(s == null) continue;
        contentCount1++;
    }



  /*
  if(contentCount1 == ((String[])httpSession.getAttribute("charactersList1")).length) {



        if(((String[])httpSession.getAttribute("charactersList1"))[1].equalsIgnoreCase("Jerry")) {

        msg2 = "msg2:error:" + ((String[])httpSession.getAttribute("charactersList1"))[0] + ", " + ((String[])httpSession.getAttribute("charactersList1"))[1] + ", "
                + ((String[])httpSession.getAttribute("charactersList1"))[2] + ", " + ((String[])httpSession.getAttribute("charactersList1"))[3] + ((String[])httpSession.getAttribute("charactersList1"))[4] + ":";
        }

    }*/




   if(contentCount1 == ((String[])httpSession.getAttribute("enrollmentParameters")).length && submitEnrollList.equals("true")) {

       //msg1 = "done";

       String dateFrom = ((String[])httpSession.getAttribute("enrollmentParameters"))[2] + "-"
                       + ((String[])httpSession.getAttribute("enrollmentParameters"))[1] + "-"
                       + "01";

       String dateTo = ((String[])httpSession.getAttribute("enrollmentParameters"))[4] + "-"
                     + ((String[])httpSession.getAttribute("enrollmentParameters"))[3] + "-"
                     + "31";

       String cbo = ((String[])httpSession.getAttribute("enrollmentParameters"))[0];




       System.out.println("This is dateTo: " + dateTo);
       System.out.println("This is cbo: " + cbo);




       String target = "success";

       List ovcMatchesEnrollList = new ArrayList();

       try {

       ovcMatchesEnrollList = getOvcs(cbo, dateFrom, dateTo);


       System.out.println("This is size ovcMatches: " + ovcMatchesEnrollList.size());

       }catch(Exception e) {
           target = "error";

       }

 request.setAttribute("ovcMatchesEnrollList", ovcMatchesEnrollList);

 request.getSession().setAttribute("ovcMatchesEnrollList", ovcMatchesEnrollList);


 msg1 = "success:done:";


 OvcActionMapping ovcMapping =
(OvcActionMapping)mapping;



// Forward to the appropriate View
//return (mapping.findForward(target));
//return (ovcMapping.findForward(target));

 //return null;

   }





   //}





    /*
    int contentCount2 = 0;

    for(Object s: ((String[])httpSession.getAttribute("charactersList2"))) {

        if(s == null) continue;
        contentCount2++;
    }
    */



    /*
    if(contentCount2 == ((String[])httpSession.getAttribute("charactersList2")).length || contentCount2 == ((String[])httpSession.getAttribute("charactersList2")).length-1) {

        if(((String[])httpSession.getAttribute("charactersList2"))[4].equalsIgnoreCase("Gibber")) {

        msg3 = "msg3;error;" + ((String[])httpSession.getAttribute("charactersList2"))[0] + ", " + ((String[])httpSession.getAttribute("charactersList2"))[1] + ", "
                + ((String[])httpSession.getAttribute("charactersList2"))[2] + ", " + ((String[])httpSession.getAttribute("charactersList2"))[3] + ", " + ((String[])httpSession.getAttribute("charactersList2"))[4] + ";";
        }


    }*/




    /*
      if(contentCount2 == ((String[])httpSession.getAttribute("charactersList2")).length || contentCount2 == ((String[])httpSession.getAttribute("charactersList2")).length-1) {
     //if(contentCount2 == ((String[])httpSession.getAttribute("charactersList2")).length) {


         try{
       List list = searchOvcByCaregiverName((String[])httpSession.getAttribute("charactersList2"));

        if(!list.isEmpty()) {
       //return value = "error-Ovc already exits-";



            for(Object s: list) {

                ovc = (Ovc)s;
                matchStr += ovc.getOvcId() + "_";

            }


            //msg3 = "msg3;error;" + "OVC with similar records exists;";
            msg3 = "msg3;error;" + "OVC with similar records exists" + matchStr + ";";

       }
     } catch(Exception e){

           //msg3 = "msg3;error;" + "Exception" + " " + e.getMessage() + ";";

     }
     }
    */










    /*

    // And yes, I know creating HTML in an Action is generally very bad form,
    // but I wanted to keep this exampel simple.
    String html = "<select name=\"CharactersSelect\">";
    int i = 0;
    for (Iterator it = characters.iterator(); it.hasNext();) {
      String name = (String)it.next();
      i++;
      html += "<option value=\"" + i + "\">" + name + "</option>";
    }
    html += "</select>";

    */




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


  // This method returns a list of characters for a given TV show.  If no TV
  // show is selected, i.e., initial page view, am empty ArrayList is returned.






/*
private String getMessage1(String ovcId, String[] selectionArray) {


      List errorList = new ArrayList();

      String value = " ";




if(ovcId.equals("")) {
     //return value = "error-Serial No. is required-" + "Nas/PLA/" + ovcId + "-";
     //return value = "error-Serial No. is required-" + selectionArray[2] + "/" + selectionArray[0] + "/" + selectionArray[1] + "/" + ovcId + "-";
     return value = "error-Serial No. is required-" + selectionArray[2] + "/" + selectionArray[0] + "/" + selectionArray[1] + "/" + ovcId + "-";

}


if(!ovcId.equals("")){
int size = ovcId.length();
for(int i = 0; i < size; i++) {
    try{
    Integer.parseInt(Character.toString(ovcId.charAt(i)));
    }
    catch(Exception e){
        //errors.add("roleid",
        //new ActionMessage("errors.roleid.required"));

        errorList.add(ovcId.charAt(i));


    }

}

if(!errorList.isEmpty()) //return value = "error-Serial No. must be numeric-" + "Nas/PLA/" + ovcId + "-";
    return value = "error-Serial No. must be numeric-" + selectionArray[2] + "/" + selectionArray[0] + "/" + selectionArray[1] + "/" + ovcId + "-";
}


if(ovcId.length() > 5) {
    //return value = "error-Serial No. must not be more than 5 digits-" + "Nas/PLA/" + ovcId + "-";
    return value = "error-Serial No. must not be more than 5 digits-" + selectionArray[2] + "/" + selectionArray[0] + "/" + selectionArray[1] + "/" + ovcId + "-";

}

else if(ovcId.length() <= 5) {

    if(ovcId.length() == 1) {
        if(ovcId.equals("0")) {
            //return value = "error-Serial No. cannot be zero-" + "Nas/PLA/" + ovcId + "-";
            return value = "error-Serial No. cannot be zero-" + selectionArray[2] + "/" + selectionArray[0] + "/" + selectionArray[1] + "/" + ovcId + "-";
        }
        else
        ovcId = "0000" + ovcId;
       }

    else if(ovcId.length() == 2) {
        if(ovcId.equals("00")) {
            //return value = "error-Serial No. cannot be zero-" + "Nas/PLA/" + ovcId + "-";
            return value = "error-Serial No. cannot be zero-" + selectionArray[2] + "/" + selectionArray[0] + "/" + selectionArray[1] + "/" + ovcId + "-";
        }
        else
        ovcId = "000" + ovcId;
    }

    else if(ovcId.length() == 3) {
        if(ovcId.equals("000")) {
            //return value = "error-Serial No. cannot be zero-" + "Nas/PLA/" + ovcId + "-";
            return value = "error-Serial No. cannot be zero-" + selectionArray[2] + "/" + selectionArray[0] + "/" + selectionArray[1] + "/" + ovcId + "-";
        }
        else
        ovcId = "00" + ovcId;
    }

    else if(ovcId.length() == 4) {
        if(ovcId.equals("0000")) {
            //return value = "error-Serial No. cannot be zero-" + "Nas/PLA/" + ovcId + "-";
            return value = "error-Serial No. cannot be zero-" + selectionArray[2] + "/" + selectionArray[0] + "/" + selectionArray[1] + "/" + ovcId + "-";
        }
        else
        ovcId = "0" + ovcId;
    }

    else if(ovcId.length() == 5) {
        if(ovcId.equals("00000")) {
            //return value = "error-Serial No. cannot be zero-" + "Nas/PLA/" + ovcId + "-";
            return value = "error-Serial No. cannot be zero-" + selectionArray[2] + "/" + selectionArray[0] + "/" + selectionArray[1] + "/" + ovcId + "-";
        }
    }




}
      */






/*
      //Ovc ovc = null;
      List list = new ArrayList();


 try{
    OvcDao dao = new OvcDaoImpl();
    //list = dao.searchOvc(ovcId);
    list = dao.searchOvc(selectionArray[2] + "/" + selectionArray[0] + "/" + selectionArray[1] + "/" + ovcId);



    }catch(Exception e) {
          return value = "error-There was an error retrieving records. Try again-";

    }

    if(!list.isEmpty()) {

        //Now get all values from OVC for ovc update!

        ovc = (Ovc)list.get(0);


        String ovcDetails = "::" + "ovcdetails" + "::" + ovc.getDateEnrollment() + "::"
                          + ovc.getLastName() + "::";

        if(ovc.getFirstName().contains(",")) {
               ovcDetails += ovc.getFirstName().substring(0, ovc.getFirstName().indexOf(",")) + "::"
                     + ovc.getFirstName().substring(ovc.getFirstName().indexOf(" ") + 1)+ "::";
            }
            else ovcDetails += ovc.getFirstName() + "::" + " " + "::";


            ovcDetails += ovc.getGender() + "::"
                        + ovc.getAge() + "::"
                        + ovc.getAgeUnit() + "::"
                        + ovc.getWeight() + "::"
                        + ovc.getAddress() + "::"
                        + ovc.getPhone() + "::"
                        + ovc.getHeight() + "::"
                        + ovc.getEligibility() + "::"
                        + ovc.getHivStatus() + "::"
                        + ovc.getBirthCertificate() + "::"
                        + ovc.getSchoolStatus() + "::"
                        + ovc.getSchoolType() + "::"
                        + ovc.getSchoolName() + "::"
                        + ovc.getCurrentClass() + "::"
                        + ovc.getOrphanage() + "::"
                        + ovc.getOrphanageName() + "::"
                        + ovc.getCaregiverName() + "::"
                        + ovc.getCaregiverGender() + "::"
                        + ovc.getCaregiverAge() + "::"
                        + ovc.getCaregiverAddress() + "::"
                        + ovc.getCaregiverPhone() + "::"
                        + ovc.getCaregiverOccupation() + "::"
                        + ovc.getCaregiverRelationships() + "::";




    try{
    ChildStatusIndexDao dao = new ChildStatusIndexDaoImpl();

    childStatusIndex = dao.getChildStatusIndex(selectionArray[2] + "/" + selectionArray[0] + "/" + selectionArray[1] + "/" + ovcId, ovc.getDateEnrollment());



    }catch(Exception e) {
          return value = "error-There was an error retrieving records. Try again-";

    }

            ovcDetails += childStatusIndex.getCsiFactor1() + "::"
                        + childStatusIndex.getCsiFactor2() + "::"
                        + childStatusIndex.getCsiFactor3() + "::"
                        + childStatusIndex.getCsiFactor4() + "::"
                        + childStatusIndex.getCsiFactor5() + "::"
                        + childStatusIndex.getCsiFactor6() + "::"
                        + childStatusIndex.getCsiFactor7() + "::"
                        + childStatusIndex.getCsiFactor8() + "::"
                        + childStatusIndex.getCsiFactor9() + "::"
                        + childStatusIndex.getCsiFactor10() + "::"
                        + childStatusIndex.getCsiFactor11() + "::"
                        + childStatusIndex.getCsiFactor12() + "::";



            ovcDetails += ovc.getServiceEnrollment() + "::"
                        + ovc.getCompletedbyName() + "::"
                        + ovc.getCompletedbyDesignation() + "::"
                        + ovc.getVerifiedBy() + "::";













        //return value = "error-OVC already exits-" + selectionArray[2] + "/" + selectionArray[0] + "/" + selectionArray[1] + "/" + ovcId + "-" + ovcDetails;
        return value = "error- -" + selectionArray[2] + "/" + selectionArray[0] + "/" + selectionArray[1] + "/" + ovcId + "-" + ovcDetails;


    }











      //return value = "Nas/PLA/" + ovcId + "-";
      return value = "success-" + selectionArray[2] + "/" + selectionArray[0] + "/" + selectionArray[1] + "/" + ovcId + "-";

}




  private List searchOvcByAge(String[] charactersList) throws Exception {
        List list = new ArrayList();



        String firstAndMiddleNames = "";


        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();



            if(charactersList[2] == null) {

                firstAndMiddleNames = charactersList[1];
            }
            else if(charactersList[2].equals("")) {

                firstAndMiddleNames = charactersList[1];
            }
            else {//if(!charactersList[2].equals("")){

                firstAndMiddleNames = charactersList[1] + ", " + charactersList[2];
            }




        list = session.createQuery("from Ovc as ovc where ovc.lastName = :ln and ovc.firstName = :fn and ovc.age = :ag and ovc.ageUnit = :au")
                           .setString("ln", charactersList[0])
                           .setString("fn", firstAndMiddleNames)
                           .setString("ag", charactersList[3])
                           .setString("au", charactersList[4])
                           .list();



        tx.commit();
        session.close();
        } catch (HibernateException he) {
            throw new Exception(he);
        }



        return list;
    }




  private List searchOvcByCaregiverName(String[] charactersList) throws Exception {
        List list = new ArrayList();



        String firstAndMiddleNames = "";

        String caregiverName = "";


        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();



            if(charactersList[2] == null) {

                firstAndMiddleNames = charactersList[1];
            }
            else if(charactersList[2].equals("")) {

                firstAndMiddleNames = charactersList[1];
            }
            else {//if(!charactersList[2].equals("")){

                firstAndMiddleNames = charactersList[1] + ", " + charactersList[2];
            }






            if(charactersList[3] == null || charactersList[4] == null) {

                if(charactersList[3] == null) {
                    caregiverName = charactersList[4];
                }
                else caregiverName = charactersList[3];
            }
            else caregiverName = charactersList[3] + ", " + charactersList[4];






        list = session.createQuery("from Ovc as ovc where ovc.lastName = :ln and ovc.firstName = :fn and ovc.caregiverName = :cn")
                           .setString("ln", charactersList[0])
                           .setString("fn", firstAndMiddleNames)
                           .setString("cn", caregiverName)
                           .list();



        tx.commit();
        session.close();
        } catch (HibernateException he) {
            throw new Exception(he);
        }



        return list;
    }
*/






protected List tokenizeStr(String str) {

List tokenList = new ArrayList();

    StringTokenizer st = new StringTokenizer(str, "-");

    while(st.hasMoreTokens()) {

        tokenList.add(st.nextToken());

    }


 return tokenList;
}







protected List getOvcs(String cbo, String dateFrom, String dateTo) throws Exception {
Ovc ovcObj = null;
List ovcs = new ArrayList();

try {


 List list = new ArrayList();


            /*
            OvcDao dao = new OvcDaoImpl();

            for(Object s: idTokens) {
                try {
                   ovc = dao.getOvc((String)s);
                   list.add(ovc);
                }catch(Exception e) {

                }
            }
            */






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

    System.out.println("This is upper list size: " + list.size()); 

     for (Object s: list) {

                Ovc ovc = (Ovc)s;



ovcObj = new Ovc();
ovcObj.setOvcId(ovc.getOvcId());
ovcObj.setDateEnrollment(formatDate(ovc.getDateEnrollment()));
ovcObj.setState(ovc.getState());
ovcObj.setLga(ovc.getLga());
ovcObj.setWard(ovc.getWard());
ovcObj.setLastName(ovc.getLastName().toUpperCase());
//ovcObj.setFirstName(ovc.getFirstName().substring(0, ovc.getFirstName().indexOf(",")) + " " + ovc.getFirstName().substring(ovc.getFirstName().indexOf(" ") + 1) );

try{

    ovcObj.setFirstName(ovc.getFirstName().substring(0, ovc.getFirstName().indexOf(",")) + " " + ovc.getFirstName().substring(ovc.getFirstName().indexOf(" ") + 1) );
}catch(Exception e) {

    ovcObj.setFirstName(ovc.getFirstName());
}



if(ovc.getGender().equals("Male")) {
    ovcObj.setGender("M");
}
else ovcObj.setGender("F");

ovcObj.setAge(ovc.getAge());
ovcObj.setAgeUnit(ovc.getAgeUnit());
ovcObj.setAddress(ovc.getAddress());
ovcObj.setWard(ovc.getWard());



//ovcObj.setCaregiverName((ovc.getCaregiverName().substring(0, ovc.getCaregiverName().indexOf(","))).toUpperCase() + ", " + ovc.getCaregiverName().substring(ovc.getCaregiverName().indexOf(" ") + 1) );

if(ovc.getCaregiverName() != null) {

    try {

    ovcObj.setCaregiverName((ovc.getCaregiverName().substring(0, ovc.getCaregiverName().indexOf(","))).toUpperCase() + " " + ovc.getCaregiverName().substring(ovc.getCaregiverName().indexOf(" ") + 1) );
    }catch(Exception e) {

    ovcObj.setCaregiverName(ovc.getCaregiverName());
    }

}
else ovcObj.setCaregiverName("");

//ovcObj.setCaregiverName("Now Null");

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




private String formatDate(String dateString) {

    List tokens = tokenizeStr(dateString);
    return tokens.get(2) + "/" + tokens.get(1) + "/" + tokens.get(0);


}









}//End class



