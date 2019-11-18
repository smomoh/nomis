


package xhrstruts;


import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.kidmap.business.EligibilityCriteria;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.dao.ChildStatusIndexDao;
import com.fhi.kidmap.dao.ChildStatusIndexDaoImpl;
import com.fhi.kidmap.dao.HibernateUtil;
import com.fhi.kidmap.dao.OvcDao;
import com.fhi.kidmap.dao.OvcDaoImpl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fhi.kidmap.controller.OvcActionMapping;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.EligibilityCriteriaDao;
import com.fhi.kidmap.dao.EligibilityCriteriaDaoImpl;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class GetCharactersAction extends Action {


    private Ovc ovc = null;
    private ChildStatusIndex childStatusIndex = null;
    Session session;
    Transaction tx;
    String[] selectionArray;


    @Override
  public ActionForward execute(ActionMapping mapping, ActionForm inForm, HttpServletRequest request, HttpServletResponse response) throws Exception {


        HttpSession httpSession = request.getSession();
        if(httpSession.getAttribute("charactersList1") == null && httpSession.getAttribute("charactersList2") == null) {

            String[] charactersList1 = new String[5];
            String[] charactersList2 = new String[5];

           // List charactersList1 = new ArrayList();
           // List charactersList2 = new ArrayList();
            httpSession.setAttribute("charactersList1", charactersList1);
            httpSession.setAttribute("charactersList2", charactersList2);
        }

         if(httpSession.getAttribute("selectionArray") == null) {

            selectionArray = new String[3];

            httpSession.setAttribute("selectionArray", selectionArray);

        }

        //selectionArray = (String[])httpSession.getAttribute("selectionArray");




    // Get a list of characters associated with the select TV show
    String ovcId = (String)request.getParameter("ovcId");
    if (ovcId == null) {
      ovcId = "";
    }

    String surname = (String)request.getParameter("surname");
    if (surname == null) {
      surname = "";
    }

    String firstName = (String)request.getParameter("firstName");
    if (firstName == null) {
      firstName = "";
    }

    String middleName = (String)request.getParameter("middleName");
    if (middleName == null) {
      middleName = "";
    }

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





    /*
    String reset = (String)request.getParameter("reset");
    if (reset == null) {
      reset = "";
    }

    if(reset.equals("true")) {



    }
    */





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





    //ArrayList characters = getCharacters(tvShow);
    String msg1 = getMessage1(ovcId, selectionArray);

    String msg2 = "";

    String msg3 = "";



    if(!surname.equals("")) {
        ((String[])httpSession.getAttribute("charactersList1"))[0] = surname;
        ((String[])httpSession.getAttribute("charactersList2"))[0] = surname;
        //List)httpSession.getAttribute("charactersList1")).add(surname);
        //((List)httpSession.getAttribute("charactersList2")).add(surname);
    }

   if(!firstName.equals("")) {
        ((String[])httpSession.getAttribute("charactersList1"))[1] = firstName;
        ((String[])httpSession.getAttribute("charactersList2"))[1] = firstName;
        //((List)httpSession.getAttribute("charactersList1")).add(firstName);
        //((List)httpSession.getAttribute("charactersList2")).add(firstName);
    }

    if(!middleName.equals("")) {
        ((String[])httpSession.getAttribute("charactersList1"))[2] = middleName;
        ((String[])httpSession.getAttribute("charactersList2"))[2] = middleName;
        //((List)httpSession.getAttribute("charactersList1")).add(firstName);
        //((List)httpSession.getAttribute("charactersList2")).add(firstName);
    } /*else {

        ((String[])httpSession.getAttribute("charactersList1"))[2] = "empty";
        ((String[])httpSession.getAttribute("charactersList2"))[2] = "empty";
    }*/


    /*
     if(middleName.equals("")) {
        ((String[])httpSession.getAttribute("charactersList1"))[2] = middleName;
        ((String[])httpSession.getAttribute("charactersList2"))[2] = middleName;
        //((List)httpSession.getAttribute("charactersList1")).add(firstName);
        //((List)httpSession.getAttribute("charactersList2")).add(firstName);
    }*/


    if(!age.equals("")) {
        ((String[])httpSession.getAttribute("charactersList1"))[3] = age;
        //((List)httpSession.getAttribute("charactersList1")).add(age);

    }

    if(!ageUnit.equals("")) {
        ((String[])httpSession.getAttribute("charactersList1"))[4] = ageUnit;
        //((List)httpSession.getAttribute("charactersList1")).add(ageUnit);

    }

    if(!caregiverName1.equals("")) {

      ((String[])httpSession.getAttribute("charactersList2"))[3] = caregiverName1;
      //((List)httpSession.getAttribute("charactersList2")).add(caregiverName);
    }

   if(!caregiverName2.equals("")) {

      ((String[])httpSession.getAttribute("charactersList2"))[4] = caregiverName2;
      //((List)httpSession.getAttribute("charactersList2")).add(caregiverName);
   }



    int contentCount1 = 0;

    for(Object s: ((String[])httpSession.getAttribute("charactersList1"))) {

        if(s == null) continue;
        contentCount1++;
    }



    String matchStr = "_";

   if(contentCount1 == ((String[])httpSession.getAttribute("charactersList1")).length || contentCount1 == ((String[])httpSession.getAttribute("charactersList1")).length-1) {
   //if(contentCount1 == ((String[])httpSession.getAttribute("charactersList1")).length) {

     try{
       List list = searchOvcByAge((String[])httpSession.getAttribute("charactersList1"));

        if(!list.isEmpty()) {
       //return value = "error-Ovc already exits-";



            for(Object s: list) {

                ovc = (Ovc)s;
                matchStr += ovc.getOvcId() + "_";

            }
            msg2 = "msg2:error:" + "OVC with similar records exists" + matchStr + ":";
       }
     } catch(Exception e){
     }

   }



    int contentCount2 = 0;

    for(Object s: ((String[])httpSession.getAttribute("charactersList2"))) {

        if(s == null) continue;
        contentCount2++;
    }

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










    // Write the HTML to response
    //response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    //out.println(html);
    out.println(msg1);
    out.println(msg2);
    out.println(msg3);
    out.flush();

    return null; // Not forwarding to anywhere, response is fully-cooked

  } // End execute()


  // This method returns a list of characters for a given TV show.  If no TV
  // show is selected, i.e., initial page view, am empty ArrayList is returned.






 private String getMessage1(String ovcId, String[] selectionArray) {


      List errorList = new ArrayList();

      String value = " ";


     //Ovc ovc = null;
      List list = new ArrayList();


 try{
    OvcDao dao = new OvcDaoImpl();
    list = dao.searchOvc(ovcId);
    

    }catch(Exception e) {
          return value = "error-There was an error retrieving records. Try again-";

    }

    if(!list.isEmpty())
    {
        List ovcList=new ArrayList();
        DaoUtil util=new DaoUtil();
        //ovcList=util.getOvcWithCaregiverInfo(list);
        //Now get all values from OVC for ovc update!

        ovc = (Ovc)list.get(0);
        String eligibility="";
        String eligibilityId="";
        String[] eligibilityArray=ovc.getEligibility().split(", ");
        EligibilityCriteriaDao eldao=new EligibilityCriteriaDaoImpl();
        List elList=new ArrayList();
        try
        {
            elList=eldao.getEligibilityCriteria();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        int j=0;
        for(Object el:elList)
        {

            EligibilityCriteria elc=(EligibilityCriteria)el;
            for(int i=0; i<eligibilityArray.length; i++)
            { 
                if(elc.getEligibilityName().equals(eligibilityArray[i].trim()))
                {
                    if(i<eligibilityArray.length-1)
                    eligibility+=eligibilityArray[i]+", ";
                    else
                    eligibility+=eligibilityArray[i];
                }
            }
            if(j<elList.size()-1)
            eligibilityId+=elc.getEligibilityName()+", ";
            else
            eligibilityId+=elc.getEligibilityName();
            j++;
        }
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
                        +eligibility+ "::"
                        //+ ovc.getEligibility() + "::"
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

    childStatusIndex = dao.getChildStatusIndex(ovcId, ovc.getDateEnrollment());
    if(childStatusIndex==null)
    childStatusIndex=new ChildStatusIndex();


    }catch(Exception e) {
          return value = "error-There was an error retrieving records. Try again-";

    }
            //DaoUtil util=new DaoUtil();
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
                        + childStatusIndex.getCsiFactor12() + "::"
                        + childStatusIndex.getId() + "::";

            ovcDetails += ovc.getServiceEnrollment() + "::"
                        + ovc.getCompletedbyName() + "::"
                        + ovc.getCompletedbyDesignation() + "::"
                        + ovc.getVerifiedBy() + "::"
                        + ovc.getCompletedbyCbo() + "::"
                        //+ util.getCboCode(ovc.getCompletedbyCbo()) + "::"
                        + ovc.getWard() + "::"
                        + ovc.getOvcId() + "::"
                        + ovc.getHhFirstname() + "::"
                        + ovc.getHhSurname() + "::"
                        + ovc.getHhGender() + "::"
                        + ovc.getHhAge() + "::"
                        + ovc.getNumOfChildrenInHh() + "::"
                        + ovc.getNumOfOVCInHh() + "::"
                        + ovc.getSourceOfInfo() + "::";
            
            int currentAge=util.getCurrentAge(ovc);
            if(currentAge > 17)
            ovcDetails+="overaged;"+currentAge+"::";
            else
            ovcDetails+="underaged;"+currentAge+"::";
            ovcDetails+=eligibilityId+"::";
            
            

        //return value = "error-OVC already exits-" + selectionArray[2] + "/" + selectionArray[0] + "/" + selectionArray[1] + "/" + ovcId + "-" + ovcDetails;
        //return value = "error- -" + selectionArray[2] + "/" + selectionArray[0] + "/" + selectionArray[1] + "/" + ovcId + "-" + ovcDetails;
         return value = "error- -" + ovcId + "-" + ovcDetails;
    }

    DaoUtil util=new DaoUtil();

    String eligibilityId=util.getEligibilityCriteriaId();

    //System.err.println("eligibilityId is "+eligibilityId);





      //return value = "Nas/PLA/" + ovcId + "-";
      //return value = "success-" + selectionArray[2] + "/" + selectionArray[0] + "/" + selectionArray[1] + "/" + ovcId + "-";
      return value = "success-" + ovcId + "-"+eligibilityId;

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







protected List tokenizeStr(String str) {

List tokenList = new ArrayList();

    StringTokenizer st = new StringTokenizer(str, "_");

    while(st.hasMoreTokens()) {

        tokenList.add(st.nextToken());

    }

 //s += tokenList.size() + ": ";
 //for(Object so: tokenList) {

     //String v = (String)so;
    // s += v + " ";
// }



 return tokenList;
}






protected List getOvcs(List idTokens) throws Exception {
Ovc ovcObj = null;
List ovcs = new ArrayList();

try {


 List list = new ArrayList();


            OvcDao dao = new OvcDaoImpl();

            for(Object s: idTokens) {
                try {
                   ovc = dao.getOvc((String)s);
                   list.add(ovc);
                }catch(Exception e) {

                }
            }






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







}//End class



