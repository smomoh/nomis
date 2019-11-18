/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xhrstruts;

import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.HibernateUtil;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
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
public class GetChartAction extends Action {

    private Ovc ovc = null;
    Session session;
    Transaction tx;
    DaoUtil util=new DaoUtil();
    //private List list;
    private List datasetList;
    private Integer lgaCount;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm inForm, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String value = "done";
        DaoUtil util=new DaoUtil();

        String type = (String) request.getParameter("type");
        if (type == null) {
            type = "";
        }


        String count = (String) request.getParameter("count");
        if (count == null) {
            count = "";
        }

        String month = (String) request.getParameter("month");
        if (month == null) {
            month = "";
        }

        String year = (String) request.getParameter("year");
        if (year == null) {
            year = "";
        }

        String month1 = (String) request.getParameter("month1");
        if (month1 == null) {
            month1 = "";
        }

        String year1 = (String) request.getParameter("year1");
        if (year1 == null) {
            year1 = "";
        }

        String month2 = (String) request.getParameter("month2");
        if (month2 == null) {
            month2 = "";
        }

        String year2 = (String) request.getParameter("year2");
        if (year2 == null) {
            year2 = "";
        }



        if (type.equals("chart1")) {
            //createChart1(count, month, year);
            createChart1(month1, year1, month2, year2);
        }
        else if (type.equals("chart2")) {
            createChart2(month1, year1, month2, year2);
        }
        else if (type.equals("chart3")) {
            createChart3(month1, year1, month2, year2);
        }
        else if (type.equals("chart4")) {
            createChart4(month1, year1, month2, year2);
        }
        else if (type.equals("chart5")) {
            createChart5(month1, year1, month2, year2);
        }
        else if (type.equals("chart6")) {
            createChart6(month1, year1, month2, year2);
        }
        else if (type.equals("chart7")) {
            createChart7(month1, year1, month2, year2);
        }
        else if (type.equals("chart8")) {
            createChart8(month1, year1, month2, year2);
        }
        else if (type.equals("chart9")) {
            createChart9(month1, year1, month2, year2);
        }
        else if (type.equals("chart10")) {
            createChart10(month1, year1, month2, year2);
        }
        else if (type.equals("chart11")) {
            createChart11(month1, year1, month2, year2);
        }
        else if (type.equals("chart12")) {
            createChart12(month1, year1, month2, year2);
        }






        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("datasetList", datasetList);
        httpSession.setAttribute("lgaCount", lgaCount);

        // Write the HTML to response
        //response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(value);
        out.flush();

        return null; // Not forwarding to anywhere, response is fully-cooked



    } // End execute()

    /*private void createChart1(String count, String month, String year) {

        datasetList = new ArrayList();
        String startDate = year + "-" + month + "-" + "01";

        System.err.println("This is startDate: " + startDate);

        String endDate = null;
        try {
            endDate = getMaxDate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.err.println("This is maxDate: " + endDate);

        List endDateParts = tokenizeStr(endDate, "-");


        Calendar cStart = Calendar.getInstance();
        Calendar cEnd = Calendar.getInstance();
        cStart.set(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt("01"));
        cEnd.set(Integer.parseInt((String) endDateParts.get(0)), Integer.parseInt((String) endDateParts.get(1))-1, Integer.parseInt((String) endDateParts.get(2)));

        System.err.println("This startTime: " + cStart.getTime().toString());

        long numberOfMonths = monthsBetween(cStart, cEnd);

        System.err.println("This is monthDiff: " + numberOfMonths);


        for (int i = 0; i < numberOfMonths; i++) {

            cStart.add(Calendar.MONTH, 1);
            Date date = cStart.getTime();
            DateFormat df1 = DateFormat.getDateInstance(DateFormat.SHORT);
            DateFormat df2 = DateFormat.getDateInstance(DateFormat.MEDIUM);
            String shortFormat = df1.format(date);
            String mediumFormat = df2.format(date);

            System.err.println("after getTime()");

            System.err.println(shortFormat);
            System.err.println(mediumFormat);

            List dateParts = tokenizeStr(shortFormat, "/");

            //String datasetStr = (String) dateParts.get(0) + ",";

            String monthStr = (String) dateParts.get(1);

            System.err.println((String)dateParts.get(1));

            dateParts = tokenizeStr(mediumFormat, "-");

            //datasetStr += (String) dateParts.get(2) + ",";

            String yearStr = (String) dateParts.get(2);

            System.err.println((String)dateParts.get(2));

            String datasetStr = monthStr + "," + yearStr + ",";

            List cumNumberOfOvcs = null;
            try {
                cumNumberOfOvcs = getOvcCount(monthStr, yearStr);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            if (!cumNumberOfOvcs.isEmpty()) {
                if (!cumNumberOfOvcs.contains(null)) {
                    datasetStr += String.valueOf(cumNumberOfOvcs.size() + Integer.parseInt(count));
                    datasetList.add(datasetStr);
                }
                System.err.println(datasetStr);
            }
            

        }

        System.err.println("This is size of datasetList: " + datasetList.size());


    }*/




    private void createChart1( String month1, String year1, String month2, String year2) {

        datasetList = new ArrayList();
        //String startDate = year + "-" + month + "-" + "01";



        String dateFrom = year1 + "-" + month1 + "-" + "01";
        String dateTo = year2 + "-" + month2 + "-" + "31";



        //System.err.println("This is startDate: " + startDate);
        System.err.println("This is startDate: " + dateFrom);

        String endDate = null;
        try {
            //endDate = getMaxDate();
            endDate = dateTo;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.err.println("This is maxDate: " + endDate);

        List endDateParts = tokenizeStr(endDate, "-");


        Calendar cStart = Calendar.getInstance();
        Calendar cEnd = Calendar.getInstance();
        //cStart.set(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt("01"));
        cStart.set(Integer.parseInt(year1), Integer.parseInt(month1)-1, Integer.parseInt("01"));
        cEnd.set(Integer.parseInt((String) endDateParts.get(0)), Integer.parseInt((String) endDateParts.get(1))-1, Integer.parseInt((String) endDateParts.get(2)));

        System.err.println("This startTime: " + cStart.getTime().toString());

        long numberOfMonths = monthsBetween(cStart, cEnd);

        System.err.println("This is monthDiff: " + numberOfMonths);


        for (int i = 0; i < numberOfMonths; i++) {

            cStart.add(Calendar.MONTH, 1);

            /*
            Date date = cStart.getTime();
            DateFormat df1 = DateFormat.getDateInstance(DateFormat.SHORT);
            DateFormat df2 = DateFormat.getDateInstance(DateFormat.MEDIUM);
            String shortFormat = df1.format(date);
            String mediumFormat = df2.format(date);

            System.err.println("after getTime()");

            System.err.println(shortFormat);
            System.err.println(mediumFormat);
            */

            //List dateParts = tokenizeStr(shortFormat, "/");


            //String datasetStr = (String) dateParts.get(0) + ",";

            //String monthStr = (String) dateParts.get(1);

            String monthStr = String.valueOf(cStart.get(cStart.MONTH));

         
            //dateParts = tokenizeStr(mediumFormat, "-");

            System.err.println("This is year: " + cStart.get(cStart.YEAR));
            System.err.println("This is month: " + cStart.get(cStart.MONTH));
            System.err.println("This is day: " + cStart.get(cStart.DATE));

            //datasetStr += (String) dateParts.get(2) + ",";

            //String yearStr = (String) dateParts.get(2);
            String yearStr = String.valueOf(cStart.get(cStart.YEAR));

            //System.err.println((String)dateParts.get(2));

            String datasetStr = monthStr + "," + yearStr + ",";

            List cumNumberOfOvcs = null;
            try {
                cumNumberOfOvcs = getOvcCount(monthStr, yearStr);
            } catch (Exception e) {
                System.err.println("Cummulative no of ovc error"+e.getMessage());
            }

            if (!cumNumberOfOvcs.isEmpty()) {
                if (!cumNumberOfOvcs.contains(null)) {
                    //datasetStr += String.valueOf(cumNumberOfOvcs.size() + Integer.parseInt(count));
                    datasetStr += String.valueOf(cumNumberOfOvcs.size());
                    datasetList.add(datasetStr);
                }
                System.err.println("datasetlist: "+datasetStr);
            }


        }

        System.err.println("This is size of datasetList: " + datasetList.size());


    }




     private void createChart2(String month1, String year1, String month2, String year2) {

         datasetList = new ArrayList();
         String dateFrom = year1 + "-" + month1 + "-" + "01";
         String dateTo = year2 + "-" + month2 + "-" + "31";

         List lgas = null;
        try {
            lgas = getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (!lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(!ovcCount.isEmpty()) {
                           if(!ovcCount.contains(null)) {

                                String datasetStr = String.valueOf(ovcCount.size()) + "," + "Number of OVC enrolled per LGA" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                           }
                       }


                   }

                }

            }


     }



     private void createChart3(String month1, String year1, String month2, String year2) {

         datasetList = new ArrayList();
         String dateFrom = year1 + "-" + month1 + "-" + "01";
         String dateTo = year2 + "-" + month2 + "-" + "31";

         List lgas = null;
        try {
            lgas = getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (!lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {
                        //String lgaCode=(String)s;
                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(!ovcCount.isEmpty()) {
                           if(!ovcCount.contains(null)) {


                               System.err.println("This is ovc/lga: " + ovcCount.size());
                               int ovcWithThreeServices = 0;


                               for(Object s2:ovcCount) {
                                   ovc = (Ovc)s2;
                                   if(ovc.getServiceEnrollment() != null) {

                                   //System.err.println("This is services list size: " + tokenizeStr(ovc.getServiceEnrollment(), ",").size());
                                   //ovcServices = tokenizeStr(ovc.getServiceEnrollment(), ",");
                                   if(tokenizeStr(ovc.getServiceEnrollment(), ",").size() == 3) {
                                      ovcWithThreeServices++;
                                     
                                   }

                                   }
                               }

                                System.err.println("This is ovcWithThreeServices:" + ovcWithThreeServices);
                                System.err.println("This is ovcCount:" + ovcCount.size());
                                //double percentage = ((double)ovcWithThreeServices/ovcCount.size());
                                //System.err.println("This is percentage:" + percentage);

                                //String datasetStr = String.valueOf(ovcWithThreeServices / ovcCount.size()) + "," + "Proportion of OVC that received 3 services at enrolment per LGA" + "," + lgaName;

                                if(ovcWithThreeServices != 0) {
                                String datasetStr = String.valueOf((double)ovcWithThreeServices / ovcCount.size()) + "," + "Proportion of OVC that received 3 services at enrolment per LGA" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                    String datasetStr = String.valueOf(0.0) + "," + "Proportion of OVC that received 3 services at enrolment per LGA" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }

                           }
                       }


                   }

                }

            }

    }
     
     
     
    private void createChart4(String month1, String year1, String month2, String year2) {
        
        datasetList = new ArrayList();
         String dateFrom = year1 + "-" + month1 + "-" + "01";
         String dateTo = year2 + "-" + month2 + "-" + "31";

         List lgas = null;
        try {
            lgas = getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (!lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(!ovcCount.isEmpty()) {
                           if(!ovcCount.contains(null)) {


                               System.err.println("This is ovc/lga: " + ovcCount.size());
                               int ovcWithPositiveStatus = 0;
                               int ovcWithNegativeStatus = 0;
                               int ovcWithUnknownStatus = 0;


                               for(Object s2:ovcCount) {
                                   ovc = (Ovc)s2;
                                   if(ovc.getHivStatus() != null) {

                                   if(ovc.getHivStatus().equals("HIV status unknown")) {
                                       
                                       ovcWithUnknownStatus++;
                                   }
                                   else if(ovc.getHivStatus().equals("HIV negative")) {
                                       
                                       ovcWithNegativeStatus++;
                                   }
                                   else if(ovc.getHivStatus().equals("HIV positive")) {
                                       
                                       ovcWithPositiveStatus++;
                                   }

                                   }
                               }

                                System.err.println("This is ovcWithUnknownStatus:" + ovcWithUnknownStatus);
                                System.err.println("This is ovcWithNegativeStatus:" + ovcWithNegativeStatus);
                                System.err.println("This is ovcWithPositiveStatus:" + ovcWithPositiveStatus);
                                System.err.println("This is ovcCount:" + ovcCount.size());
                                //double percentage = ((double)ovcWithThreeServices/ovcCount.size());
                                //System.err.println("This is percentage:" + percentage);

                                //String datasetStr = String.valueOf(ovcWithThreeServices / ovcCount.size()) + "," + "Proportion of OVC that received 3 services at enrolment per LGA" + "," + lgaName;
                                
                                
                                if(ovcWithPositiveStatus != 0) {
                                String datasetStr = String.valueOf((double)ovcWithPositiveStatus / ovcCount.size()) + "," + "Positive" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {
                                    
                                String datasetStr = String.valueOf(0.0) + "," + "Positive" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                if(ovcWithNegativeStatus != 0) {
                                String datasetStr = String.valueOf((double)ovcWithNegativeStatus / ovcCount.size()) + "," + "Negative" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Negative" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                 if(ovcWithUnknownStatus != 0) {
                                String datasetStr = String.valueOf((double)ovcWithUnknownStatus / ovcCount.size()) + "," + "Unknown" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Unknown" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }

                                
                           }
                       }


                   }

                }

            }

    }




    private void createChart5(String month1, String year1, String month2, String year2) {

   datasetList = new ArrayList();
         String dateFrom = year1 + "-" + month1 + "-" + "01";
         String dateTo = year2 + "-" + month2 + "-" + "31";

         List lgas = null;
        try {
            lgas = getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (!lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(!ovcCount.isEmpty()) {
                           if(!ovcCount.contains(null)) {


                               System.err.println("This is ovc/lga: " + ovcCount.size());

                               int ovcWithoutBirthCert = 0;


                               for(Object s2:ovcCount) {
                                   ovc = (Ovc)s2;
                                   if(ovc.getBirthCertificate() != null) {

                                   if(ovc.getBirthCertificate().equals("No")) {

                                       ovcWithoutBirthCert++;
                                   }


                                   }
                               }

                                System.err.println("This is ovcWithoutBirthCert:" + ovcWithoutBirthCert);

                                System.err.println("This is ovcCount:" + ovcCount.size());
                                //double percentage = ((double)ovcWithThreeServices/ovcCount.size());
                                //System.err.println("This is percentage:" + percentage);

                                //String datasetStr = String.valueOf(ovcWithThreeServices / ovcCount.size()) + "," + "Proportion of OVC that received 3 services at enrolment per LGA" + "," + lgaName;


                                if(ovcWithoutBirthCert != 0) {
                                String datasetStr = String.valueOf((double)ovcWithoutBirthCert / ovcCount.size()) + "," + "Proportion of OVC without birth certificate at enrolment per LGA" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Proportion of OVC without birth certificate at enrolment per LGA" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }


                           }
                       }


                   }

                }

            }

    }




    private void createChart6(String month1, String year1, String month2, String year2) {

   datasetList = new ArrayList();
         String dateFrom = year1 + "-" + month1 + "-" + "01";
         String dateTo = year2 + "-" + month2 + "-" + "31";

         List lgas = null;
        try {
            lgas = getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (!lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(!ovcCount.isEmpty()) {
                           if(!ovcCount.contains(null)) {


                               System.err.println("This is ovc/lga: " + ovcCount.size());

                               int ovcInSchool = 0;


                               for(Object s2:ovcCount) {
                                   ovc = (Ovc)s2;
                                   if(ovc.getSchoolStatus() != null) {

                                   if(ovc.getSchoolStatus().equals("Yes")) {

                                       ovcInSchool++;
                                   }


                                   }
                               }

                                System.err.println("This is ovcInSchool:" + ovcInSchool);

                                System.err.println("This is ovcCount:" + ovcCount.size());
                                //double percentage = ((double)ovcWithThreeServices/ovcCount.size());
                                //System.err.println("This is percentage:" + percentage);

                                //String datasetStr = String.valueOf(ovcWithThreeServices / ovcCount.size()) + "," + "Proportion of OVC that received 3 services at enrolment per LGA" + "," + lgaName;


                                if(ovcInSchool != 0) {
                                String datasetStr = String.valueOf((double)ovcInSchool / ovcCount.size()) + "," + "Proportion of OVC in school at enrolment per LGA" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Proportion of OVC in school at enrolment per LGA" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }


                           }
                       }


                   }

                }

            }

    }




    private void createChart7(String month1, String year1, String month2, String year2) {

        datasetList = new ArrayList();
         String dateFrom = year1 + "-" + month1 + "-" + "01";
         String dateTo = year2 + "-" + month2 + "-" + "31";

         List lgas = null;
        try {
            lgas = getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (!lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(!ovcCount.isEmpty()) {
                           if(!ovcCount.contains(null)) {


                               System.err.println("This is ovc/lga: " + ovcCount.size());
                               int good = 0;
                               int fair = 0;
                               int bad = 0;
                               int veryBad = 0;
                               List csi = null;
                               ChildStatusIndex childStatusIndex = null;


                               for(Object s2:ovcCount) {
                                   ovc = (Ovc)s2;

                                   try {
                                   csi = getCsiAtEnrolment(ovc.getOvcId());
                                   } catch(Exception e) {
                                       System.err.println(e.getMessage());
                                   }

                                   if(!csi.isEmpty()) {
                                       if(!csi.contains(null)) {
                                           childStatusIndex = (ChildStatusIndex)csi.get(0);
                                       }
                                   }

                                   if(childStatusIndex != null) {

                                   if(childStatusIndex.getCsiFactor1() != 0) {

                                   if(childStatusIndex.getCsiFactor1() == 4) {

                                       good++;
                                   }
                                   else if(childStatusIndex.getCsiFactor1() == 3) {

                                       fair++;
                                   }
                                   else if(childStatusIndex.getCsiFactor1() == 2) {

                                       bad++;
                                   }
                                   else if(childStatusIndex.getCsiFactor1() == 1) {

                                       veryBad++;
                                   }

                                   }


                                   }
                               }

                                System.err.println("This is good:" + good);
                                System.err.println("This is fair:" + fair);
                                System.err.println("This is bad:" + bad);
                                System.err.println("This is veryBad:" + veryBad);
                                System.err.println("This is ovcCount:" + ovcCount.size());
                                //double percentage = ((double)ovcWithThreeServices/ovcCount.size());
                                //System.err.println("This is percentage:" + percentage);

                                //String datasetStr = String.valueOf(ovcWithThreeServices / ovcCount.size()) + "," + "Proportion of OVC that received 3 services at enrolment per LGA" + "," + lgaName;


                                if(good != 0) {
                                String datasetStr = String.valueOf((double)good / ovcCount.size()) + "," + "Good" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Good" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                if(fair != 0) {
                                String datasetStr = String.valueOf((double)fair / ovcCount.size()) + "," + "Fair" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Fair" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                 if(bad != 0) {
                                String datasetStr = String.valueOf((double)bad / ovcCount.size()) + "," + "Bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                if(veryBad != 0) {
                                String datasetStr = String.valueOf((double)veryBad / ovcCount.size()) + "," + "Very bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Very bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }


                           }
                       }


                   }

                }

            }

    }




    private void createChart8(String month1, String year1, String month2, String year2) {

        datasetList = new ArrayList();
         String dateFrom = year1 + "-" + month1 + "-" + "01";
         String dateTo = year2 + "-" + month2 + "-" + "31";

         List lgas = null;
        try {
            lgas = getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (!lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(!ovcCount.isEmpty()) {
                           if(!ovcCount.contains(null)) {


                               System.err.println("This is ovc/lga: " + ovcCount.size());
                               int good = 0;
                               int fair = 0;
                               int bad = 0;
                               int veryBad = 0;
                               List csi = null;
                               ChildStatusIndex childStatusIndex = null;


                               for(Object s2:ovcCount) {
                                   ovc = (Ovc)s2;

                                   try {
                                   csi = getCsiAtEnrolment(ovc.getOvcId());
                                   } catch(Exception e) {
                                       System.err.println(e.getMessage());
                                   }

                                   if(!csi.isEmpty()) {
                                       if(!csi.contains(null)) {
                                           childStatusIndex = (ChildStatusIndex)csi.get(0);
                                       }
                                   }

                                   if(childStatusIndex != null) {

                                   if(childStatusIndex.getCsiFactor3() != 0) {

                                   if(childStatusIndex.getCsiFactor3() == 4) {

                                       good++;
                                   }
                                   else if(childStatusIndex.getCsiFactor3() == 3) {

                                       fair++;
                                   }
                                   else if(childStatusIndex.getCsiFactor3() == 2) {

                                       bad++;
                                   }
                                   else if(childStatusIndex.getCsiFactor3() == 1) {

                                       veryBad++;
                                   }

                                   }


                                   }
                               }

                                System.err.println("This is good:" + good);
                                System.err.println("This is fair:" + fair);
                                System.err.println("This is bad:" + bad);
                                System.err.println("This is veryBad:" + veryBad);
                                System.err.println("This is ovcCount:" + ovcCount.size());
                                //double percentage = ((double)ovcWithThreeServices/ovcCount.size());
                                //System.err.println("This is percentage:" + percentage);

                                //String datasetStr = String.valueOf(ovcWithThreeServices / ovcCount.size()) + "," + "Proportion of OVC that received 3 services at enrolment per LGA" + "," + lgaName;


                                if(good != 0) {
                                String datasetStr = String.valueOf((double)good / ovcCount.size()) + "," + "Good" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Good" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                if(fair != 0) {
                                String datasetStr = String.valueOf((double)fair / ovcCount.size()) + "," + "Fair" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Fair" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                 if(bad != 0) {
                                String datasetStr = String.valueOf((double)bad / ovcCount.size()) + "," + "Bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                if(veryBad != 0) {
                                String datasetStr = String.valueOf((double)veryBad / ovcCount.size()) + "," + "Very bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Very bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }


                           }
                       }


                   }

                }

            }

    }



    private void createChart9(String month1, String year1, String month2, String year2) {

        datasetList = new ArrayList();
         String dateFrom = year1 + "-" + month1 + "-" + "01";
         String dateTo = year2 + "-" + month2 + "-" + "31";

         List lgas = null;
        try {
            lgas = getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (!lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(!ovcCount.isEmpty()) {
                           if(!ovcCount.contains(null)) {


                               System.err.println("This is ovc/lga: " + ovcCount.size());
                               int good = 0;
                               int fair = 0;
                               int bad = 0;
                               int veryBad = 0;
                               List csi = null;
                               ChildStatusIndex childStatusIndex = null;


                               for(Object s2:ovcCount) {
                                   ovc = (Ovc)s2;

                                   try {
                                   csi = getCsiAtEnrolment(ovc.getOvcId());
                                   } catch(Exception e) {
                                       System.err.println(e.getMessage());
                                   }

                                   if(!csi.isEmpty()) {
                                       if(!csi.contains(null)) {
                                           childStatusIndex = (ChildStatusIndex)csi.get(0);
                                       }
                                   }

                                   if(childStatusIndex != null) {

                                   if(childStatusIndex.getCsiFactor6() != 0) {

                                   if(childStatusIndex.getCsiFactor6() == 4) {

                                       good++;
                                   }
                                   else if(childStatusIndex.getCsiFactor6() == 3) {

                                       fair++;
                                   }
                                   else if(childStatusIndex.getCsiFactor6() == 2) {

                                       bad++;
                                   }
                                   else if(childStatusIndex.getCsiFactor6() == 1) {

                                       veryBad++;
                                   }

                                   }


                                   }
                               }

                                System.err.println("This is good:" + good);
                                System.err.println("This is fair:" + fair);
                                System.err.println("This is bad:" + bad);
                                System.err.println("This is veryBad:" + veryBad);
                                System.err.println("This is ovcCount:" + ovcCount.size());
                                //double percentage = ((double)ovcWithThreeServices/ovcCount.size());
                                //System.err.println("This is percentage:" + percentage);

                                //String datasetStr = String.valueOf(ovcWithThreeServices / ovcCount.size()) + "," + "Proportion of OVC that received 3 services at enrolment per LGA" + "," + lgaName;


                                if(good != 0) {
                                String datasetStr = String.valueOf((double)good / ovcCount.size()) + "," + "Good" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Good" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                if(fair != 0) {
                                String datasetStr = String.valueOf((double)fair / ovcCount.size()) + "," + "Fair" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Fair" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                 if(bad != 0) {
                                String datasetStr = String.valueOf((double)bad / ovcCount.size()) + "," + "Bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                if(veryBad != 0) {
                                String datasetStr = String.valueOf((double)veryBad / ovcCount.size()) + "," + "Very bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Very bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }


                           }
                       }


                   }

                }

            }

    }




    private void createChart10(String month1, String year1, String month2, String year2) {

        datasetList = new ArrayList();
         String dateFrom = year1 + "-" + month1 + "-" + "01";
         String dateTo = year2 + "-" + month2 + "-" + "31";

         List lgas = null;
        try {
            lgas = getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (!lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(!ovcCount.isEmpty()) {
                           if(!ovcCount.contains(null)) {


                               System.err.println("This is ovc/lga: " + ovcCount.size());
                               int good = 0;
                               int fair = 0;
                               int bad = 0;
                               int veryBad = 0;
                               List csi = null;
                               ChildStatusIndex childStatusIndex = null;


                               for(Object s2:ovcCount) {
                                   ovc = (Ovc)s2;

                                   try {
                                   csi = getCsiAtEnrolment(ovc.getOvcId());
                                   } catch(Exception e) {
                                       System.err.println(e.getMessage());
                                   }

                                   if(!csi.isEmpty()) {
                                       if(!csi.contains(null)) {
                                           childStatusIndex = (ChildStatusIndex)csi.get(0);
                                       }
                                   }

                                   if(childStatusIndex != null) {

                                   if(childStatusIndex.getCsiFactor8() != 0) {

                                   if(childStatusIndex.getCsiFactor8() == 4) {

                                       good++;
                                   }
                                   else if(childStatusIndex.getCsiFactor8() == 3) {

                                       fair++;
                                   }
                                   else if(childStatusIndex.getCsiFactor8() == 2) {

                                       bad++;
                                   }
                                   else if(childStatusIndex.getCsiFactor8() == 1) {

                                       veryBad++;
                                   }

                                   }


                                   }
                               }

                                System.err.println("This is good:" + good);
                                System.err.println("This is fair:" + fair);
                                System.err.println("This is bad:" + bad);
                                System.err.println("This is veryBad:" + veryBad);
                                System.err.println("This is ovcCount:" + ovcCount.size());
                                //double percentage = ((double)ovcWithThreeServices/ovcCount.size());
                                //System.err.println("This is percentage:" + percentage);

                                //String datasetStr = String.valueOf(ovcWithThreeServices / ovcCount.size()) + "," + "Proportion of OVC that received 3 services at enrolment per LGA" + "," + lgaName;


                                if(good != 0) {
                                String datasetStr = String.valueOf((double)good / ovcCount.size()) + "," + "Good" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Good" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                if(fair != 0) {
                                String datasetStr = String.valueOf((double)fair / ovcCount.size()) + "," + "Fair" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Fair" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                 if(bad != 0) {
                                String datasetStr = String.valueOf((double)bad / ovcCount.size()) + "," + "Bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                if(veryBad != 0) {
                                String datasetStr = String.valueOf((double)veryBad / ovcCount.size()) + "," + "Very bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Very bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }


                           }
                       }


                   }

                }

            }

    }





     private void createChart11(String month1, String year1, String month2, String year2) {

        datasetList = new ArrayList();
         String dateFrom = year1 + "-" + month1 + "-" + "01";
         String dateTo = year2 + "-" + month2 + "-" + "31";

         List lgas = null;
        try {
            lgas = getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (!lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(!ovcCount.isEmpty()) {
                           if(!ovcCount.contains(null)) {


                               System.err.println("This is ovc/lga: " + ovcCount.size());
                               int good = 0;
                               int fair = 0;
                               int bad = 0;
                               int veryBad = 0;
                               List csi = null;
                               ChildStatusIndex childStatusIndex = null;


                               for(Object s2:ovcCount) {
                                   ovc = (Ovc)s2;

                                   try {
                                   csi = getCsiAtEnrolment(ovc.getOvcId());
                                   } catch(Exception e) {
                                       System.err.println(e.getMessage());
                                   }

                                   if(!csi.isEmpty()) {
                                       if(!csi.contains(null)) {
                                           childStatusIndex = (ChildStatusIndex)csi.get(0);
                                       }
                                   }

                                   if(childStatusIndex != null) {

                                   if(childStatusIndex.getCsiFactor9() != 0) {

                                   if(childStatusIndex.getCsiFactor9() == 4) {

                                       good++;
                                   }
                                   else if(childStatusIndex.getCsiFactor9() == 3) {

                                       fair++;
                                   }
                                   else if(childStatusIndex.getCsiFactor9() == 2) {

                                       bad++;
                                   }
                                   else if(childStatusIndex.getCsiFactor9() == 1) {

                                       veryBad++;
                                   }

                                   }


                                   }
                               }

                                System.err.println("This is good:" + good);
                                System.err.println("This is fair:" + fair);
                                System.err.println("This is bad:" + bad);
                                System.err.println("This is veryBad:" + veryBad);
                                System.err.println("This is ovcCount:" + ovcCount.size());
                                //double percentage = ((double)ovcWithThreeServices/ovcCount.size());
                                //System.err.println("This is percentage:" + percentage);

                                //String datasetStr = String.valueOf(ovcWithThreeServices / ovcCount.size()) + "," + "Proportion of OVC that received 3 services at enrolment per LGA" + "," + lgaName;


                                if(good != 0) {
                                String datasetStr = String.valueOf((double)good / ovcCount.size()) + "," + "Good" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Good" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                if(fair != 0) {
                                String datasetStr = String.valueOf((double)fair / ovcCount.size()) + "," + "Fair" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Fair" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                 if(bad != 0) {
                                String datasetStr = String.valueOf((double)bad / ovcCount.size()) + "," + "Bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                if(veryBad != 0) {
                                String datasetStr = String.valueOf((double)veryBad / ovcCount.size()) + "," + "Very bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Very bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }


                           }
                       }


                   }

                }

            }

    }





     private void createChart12(String month1, String year1, String month2, String year2) {

        datasetList = new ArrayList();
         String dateFrom = year1 + "-" + month1 + "-" + "01";
         String dateTo = year2 + "-" + month2 + "-" + "31";

         List lgas = null;
        try {
            lgas = getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (!lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(!ovcCount.isEmpty()) {
                           if(!ovcCount.contains(null)) {


                               System.err.println("This is ovc/lga: " + ovcCount.size());
                               int good = 0;
                               int fair = 0;
                               int bad = 0;
                               int veryBad = 0;
                               List csi = null;
                               ChildStatusIndex childStatusIndex = null;


                               for(Object s2:ovcCount) {
                                   ovc = (Ovc)s2;

                                   try {
                                   csi = getCsiAtEnrolment(ovc.getOvcId());
                                   } catch(Exception e) {
                                       System.err.println(e.getMessage());
                                   }

                                   if(!csi.isEmpty()) {
                                       if(!csi.contains(null)) {
                                           childStatusIndex = (ChildStatusIndex)csi.get(0);
                                       }
                                   }

                                   if(childStatusIndex != null) {

                                   if(childStatusIndex.getCsiFactor11() != 0) {

                                   if(childStatusIndex.getCsiFactor11() == 4) {

                                       good++;
                                   }
                                   else if(childStatusIndex.getCsiFactor11() == 3) {

                                       fair++;
                                   }
                                   else if(childStatusIndex.getCsiFactor11() == 2) {

                                       bad++;
                                   }
                                   else if(childStatusIndex.getCsiFactor11() == 1) {

                                       veryBad++;
                                   }

                                   }


                                   }
                               }

                                System.err.println("This is good:" + good);
                                System.err.println("This is fair:" + fair);
                                System.err.println("This is bad:" + bad);
                                System.err.println("This is veryBad:" + veryBad);
                                System.err.println("This is ovcCount:" + ovcCount.size());
                                //double percentage = ((double)ovcWithThreeServices/ovcCount.size());
                                //System.err.println("This is percentage:" + percentage);

                                //String datasetStr = String.valueOf(ovcWithThreeServices / ovcCount.size()) + "," + "Proportion of OVC that received 3 services at enrolment per LGA" + "," + lgaName;


                                if(good != 0) {
                                String datasetStr = String.valueOf((double)good / ovcCount.size()) + "," + "Good" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Good" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                if(fair != 0) {
                                String datasetStr = String.valueOf((double)fair / ovcCount.size()) + "," + "Fair" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Fair" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                 if(bad != 0) {
                                String datasetStr = String.valueOf((double)bad / ovcCount.size()) + "," + "Bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }



                                if(veryBad != 0) {
                                String datasetStr = String.valueOf((double)veryBad / ovcCount.size()) + "," + "Very bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }
                                else {

                                String datasetStr = String.valueOf(0.0) + "," + "Very bad" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                                }


                           }
                       }


                   }

                }

            }

    }









    private long monthsBetween(Calendar startDate, Calendar endDate) {
        Calendar date = (Calendar) startDate.clone();
        long monthsBetween = 0;
        while (date.before(endDate)) {
            date.add(Calendar.MONTH, 1);
            monthsBetween++;
        }
        return monthsBetween;
    }

    private String getMaxDate() throws Exception {

        List list = new ArrayList();


        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();


            list = session.createQuery("SELECT MAX(o.dateEnrollment) FROM Ovc o").list();



            tx.commit();
            session.close();
        } catch (HibernateException he) {
            throw new Exception(he);
        }

        //return list;

        if (!list.isEmpty()) {
            if (!list.contains(null)) {
                String date = (String) list.get(0);
                return date;
            } else {
                return null;
            }
        }
        return null;


    }

    protected List tokenizeStr(String str, String delim) {

        List tokenList = new ArrayList();

        StringTokenizer st = new StringTokenizer(str, delim);

        while (st.hasMoreTokens()) {

            tokenList.add(st.nextToken());

        }



        return tokenList;
    }

    private List getOvcCount(String month, String year) throws Exception {

        String date = year + "-" + month + "-" + "01";
        List list = new ArrayList();


        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();


            System.err.println("This is date in query: " + date);

            list = session.createQuery("from Ovc o where o.dateEnrollment < :dt").setString("dt", date).list();



            tx.commit();
            session.close();
        } catch (HibernateException he) {
           System.err.println("This is getOvcCount error: " + he.getMessage());
            throw new Exception(he);
        }

        return list;
    }



    private List getLgas(String dateFrom, String dateTo) throws Exception {


        List list = new ArrayList();


        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();


            //System.err.println("This is date in query: " + date);

            list = session.createQuery("SELECT DISTINCT(o.lga) FROM Ovc o where o.dateEnrollment >= :df and o.dateEnrollment <= :dt)")
                    .setString("df", dateFrom)
                    .setString("dt", dateTo)
                    .list();



            tx.commit();
            session.close();
        } catch (HibernateException he) {
            throw new Exception(he);
        }

        return list;
    }

    private List fetchOvcCount(String lgaName, String dateFrom, String dateTo) throws Exception {

         List list = new ArrayList();


        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();


            //System.err.println("This is date in query: " + date);

            list = session.createQuery("from Ovc o where o.lga = :lga and o.dateEnrollment >= :df and o.dateEnrollment <= :dt)")
                    .setString("lga", lgaName)
                    .setString("df", dateFrom)
                    .setString("dt", dateTo)
                    .list();



            tx.commit();
            session.close();
        } catch (HibernateException he) {
            throw new Exception(he);
            //System.err.println(he.getMessage());
        }




        return list;
    }

    private List getCsiAtEnrolment(String ovcId) throws Exception {

        List list = new ArrayList();


        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();


            //System.err.println("This is date in query: " + date);

            list = session.createQuery("from ChildStatusIndex csi where csi.ovcId = :ovcId and csi.surveyNumber = 0)")
                    .setString("ovcId", ovcId)
                    .list();



            tx.commit();
            session.close();
        } catch (HibernateException he) {
            throw new Exception(he);
            //System.err.println(he.getMessage());
        }




        return list;
    }







    





}
