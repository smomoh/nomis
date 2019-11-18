/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.chart.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.ChildStatusIndex;
import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.EnrollmentReportDao;
import com.fhi.kidmap.dao.EnrollmentReportDaoImpl;
import com.fhi.kidmap.dao.HouseholdEnrollmentDao;
import com.fhi.kidmap.dao.HouseholdEnrollmentDaoImpl;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author smomoh
 */
public class ChartAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private List datasetList;
    private Integer lgaCount;
    private Ovc ovc = null;
    HouseholdEnrollmentDao hheDao=new HouseholdEnrollmentDaoImpl();
    EnrollmentReportDao edao=new EnrollmentReportDaoImpl();
    AppUtility appUtil=new AppUtility();
    Session session;
    Transaction tx;
    DaoUtil util=new DaoUtil();
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ChartForm chartForm=(ChartForm)form;


        String count = (String) request.getParameter("count");
        if (count == null) {
            count = "";
        }

        String chartId = (String) chartForm.getChartType();
        String chartTitle =chartForm.getChartTitle();
        String type="Chart"+chartId;
        String month1 = chartForm.getMonth1();
        String year1 = chartForm.getYear1();
        String month2 = (String) chartForm.getMonth2();
        String year2 = chartForm.getYear2();

        if (type.equalsIgnoreCase("chart1")) {
            createChart1(month1, year1, month2, year2);
        }
        else if (type.equalsIgnoreCase("chart2")) {
            createChart2(month1, year1, month2, year2);
        }
        else if (type.equalsIgnoreCase("chart3")) {
            createChart3(month1, year1, month2, year2);
        }
        else if (type.equalsIgnoreCase("chart4")) {
            createChart4(month1, year1, month2, year2);
        }
        else if (type.equalsIgnoreCase("chart5")) {
            createChart5(month1, year1, month2, year2);
        }
        else if (type.equalsIgnoreCase("chart6")) {
            createChart6(month1, year1, month2, year2);
        }
        else if (type.equalsIgnoreCase("chart7")) {
            createChart7(month1, year1, month2, year2);
        }
        else if (type.equalsIgnoreCase("chart8")) {
            createChart8(month1, year1, month2, year2);
        }
        else if (type.equalsIgnoreCase("chart9")) {
            createChart9(month1, year1, month2, year2);
        }
        else if (type.equalsIgnoreCase("chart10")) {
            createChart10(month1, year1, month2, year2);
        }
        else if (type.equalsIgnoreCase("chart11")) {
            createChart11(month1, year1, month2, year2);
        }
        else if (type.equalsIgnoreCase("chart12")) {
            createChart12(month1, year1, month2, year2);
        }

        HttpSession session = request.getSession();
        session.setAttribute("datasetList", datasetList);
        session.setAttribute("lgaCount", lgaCount);
        session.setAttribute("charttype", type);
        session.setAttribute("charttitle", chartTitle);
        session.setAttribute("param", "ChartGenerator");
   
        return mapping.findForward(SUCCESS);
    }

    private void createChart1( String month1, String year1, String month2, String year2) {

        datasetList = new ArrayList();
        String[] dateParams={month1,year1,month2,year2};
         String dateFrom =util.getStartDate(dateParams);
         String dateTo = util.getEndDate(dateParams);

        String endDate = null;
        try {
            //endDate = getMaxDate();
            endDate = dateTo;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.err.println("This is maxDate: " + endDate);

        List endDateParts = appUtil.tokenizeStr(endDate, "-");

        Calendar cStart = Calendar.getInstance();
        Calendar cEnd = Calendar.getInstance();
        //cStart.set(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt("01"));
        cStart.set(Integer.parseInt(year1), Integer.parseInt(month1)-1, Integer.parseInt("01"));
        cEnd.set(Integer.parseInt((String) endDateParts.get(0)), Integer.parseInt((String) endDateParts.get(1))-1, Integer.parseInt((String) endDateParts.get(2)));

        System.err.println("This startTime: " + cStart.getTime().toString());

        long numberOfMonths = appUtil.monthsBetween(cStart, cEnd);

        System.err.println("This is monthDiff: " + numberOfMonths);


        for (int i = 0; i < numberOfMonths; i++) {

            cStart.add(Calendar.MONTH, 1);

            String monthStr = String.valueOf(cStart.get(cStart.MONTH));

            System.err.println("This is year: " + cStart.get(cStart.YEAR));
            System.err.println("This is month: " + cStart.get(cStart.MONTH));
            System.err.println("This is day: " + cStart.get(cStart.DATE));

            String yearStr = String.valueOf(cStart.get(cStart.YEAR));

            String datasetStr = monthStr + "," + yearStr + ",";

            List cumNumberOfOvcs = null;
            try {
                cumNumberOfOvcs = edao.getOvcCount(monthStr, yearStr);
            } catch (Exception e) {
                System.err.println("Cummulative no of ovc error"+e.getMessage());
            }

            if (!cumNumberOfOvcs.isEmpty()) {
                if (!cumNumberOfOvcs.contains(null))
                {
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
         String[] dateParams={month1,year1,month2,year2};
         String dateFrom =util.getStartDate(dateParams);
         String dateTo = util.getEndDate(dateParams);

         List lgas = null;
        try {
            lgas = edao.getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (lgas !=null && !lgas.isEmpty()) {
                //if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = edao.fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(ovcCount !=null && !ovcCount.isEmpty()) {
                           if(!ovcCount.contains(null)) {

                                String datasetStr = String.valueOf(ovcCount.size()) + "," + "Number of OVC enrolled per LGA" + "," + lgaName;
                                datasetList.add(datasetStr);

                                System.err.println("This is datasetStr: " + datasetStr);
                           }
                       }


                   }

                //}

            }


     }



     private void createChart3(String month1, String year1, String month2, String year2)
     {

         datasetList = new ArrayList();
         String[] dateParams={month1,year1,month2,year2};
         String dateFrom =util.getStartDate(dateParams);
         String dateTo = util.getEndDate(dateParams);

         List lgas = null;
        try {
            lgas = edao.getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (lgas !=null && !lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas)
                   {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = edao.fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(ovcCount !=null && !ovcCount.isEmpty()) {
                           if(!ovcCount.contains(null)) {


                               System.err.println("This is ovc/lga: " + ovcCount.size());
                               int ovcWithThreeServices = 0;


                               for(Object s2:ovcCount) {
                                   ovc = (Ovc)s2;
                                   if(ovc.getServiceEnrollment() != null) {

                                   if(appUtil.tokenizeStr(ovc.getServiceEnrollment(), ",").size() == 3) {
                                      ovcWithThreeServices++;
                                   }

                                   }
                               }

                                System.err.println("This is ovcWithThreeServices:" + ovcWithThreeServices);
                                System.err.println("This is ovcCount:" + ovcCount.size());
                                
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
         String[] dateParams={month1,year1,month2,year2};
         String dateFrom =util.getStartDate(dateParams);
         String dateTo = util.getEndDate(dateParams);

         List lgas = null;
        try {
            lgas = edao.getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (lgas !=null && !lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = edao.fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(ovcCount !=null && !ovcCount.isEmpty()) {
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
         String[] dateParams={month1,year1,month2,year2};
         String dateFrom =util.getStartDate(dateParams);
         String dateTo = util.getEndDate(dateParams);

         List lgas = null;
        try {
            lgas = edao.getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (lgas !=null && !lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = edao.fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(ovcCount !=null && !ovcCount.isEmpty()) {
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
         String[] dateParams={month1,year1,month2,year2};
         String dateFrom =util.getStartDate(dateParams);
         String dateTo = util.getEndDate(dateParams);

         List lgas = null;
        try {
            lgas = edao.getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (lgas !=null && !lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = edao.fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(ovcCount !=null && !ovcCount.isEmpty()) {
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
         String[] dateParams={month1,year1,month2,year2};
         String dateFrom =util.getStartDate(dateParams);
         String dateTo = util.getEndDate(dateParams);

         List lgas = null;
        try {
            lgas = edao.getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (lgas !=null && !lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = edao.fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(ovcCount !=null && !ovcCount.isEmpty()) {
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
                                   csi = edao.getCsiAtEnrolment(ovc.getOvcId());
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
         String[] dateParams={month1,year1,month2,year2};
         String dateFrom =util.getStartDate(dateParams);
         String dateTo = util.getEndDate(dateParams);

         List lgas = null;
        try {
            lgas = edao.getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (lgas !=null && !lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = edao.fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(ovcCount !=null && !ovcCount.isEmpty()) {
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
                                   csi = edao.getCsiAtEnrolment(ovc.getOvcId());
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
         String[] dateParams={month1,year1,month2,year2};
         String dateFrom =util.getStartDate(dateParams);
         String dateTo = util.getEndDate(dateParams);

         List lgas = null;
        try {
            lgas = edao.getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (lgas !=null && !lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = edao.fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(ovcCount !=null && !ovcCount.isEmpty()) {
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
                                   csi = edao.getCsiAtEnrolment(ovc.getOvcId());
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
         String[] dateParams={month1,year1,month2,year2};
         String dateFrom =util.getStartDate(dateParams);
         String dateTo = util.getEndDate(dateParams);

         List lgas = null;
        try {
            lgas = edao.getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (lgas !=null && !lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = edao.fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(ovcCount !=null && !ovcCount.isEmpty()) {
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
                                   csi = edao.getCsiAtEnrolment(ovc.getOvcId());
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
         String[] dateParams={month1,year1,month2,year2};
         String dateFrom =util.getStartDate(dateParams);
         String dateTo = util.getEndDate(dateParams);

         List lgas = null;
        try {
            lgas = edao.getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (lgas !=null && !lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = edao.fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(ovcCount !=null && !ovcCount.isEmpty()) {
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
                                   csi = edao.getCsiAtEnrolment(ovc.getOvcId());
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
         String[] dateParams={month1,year1,month2,year2};
         String dateFrom =util.getStartDate(dateParams);
         String dateTo = util.getEndDate(dateParams);

         List lgas = null;
        try {
            lgas = edao.getLgas(dateFrom, dateTo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (lgas !=null && !lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   lgaCount = lgas.size();

                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;
                    try {
                        ovcCount = edao.fetchOvcCount(lgaName, dateFrom, dateTo);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(ovcCount !=null && !ovcCount.isEmpty()) {
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
                                   csi = edao.getCsiAtEnrolment(ovc.getOvcId());
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
}
