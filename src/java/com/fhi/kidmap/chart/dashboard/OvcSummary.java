/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.chart.dashboard;

import com.fhi.kidmap.business.Ovc;
import com.fhi.kidmap.chart.DashboardChartGenerator;
import com.fhi.kidmap.dao.DaoUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author HP USER
 */
public class OvcSummary {

    private Ovc ovc = null;

    public void setSummaryList(HttpServletRequest request) {

        List dashboardDatasetList = new ArrayList();
        setDataForOvcByGender(dashboardDatasetList);
        request.setAttribute("dashboardDatasetList", dashboardDatasetList);
        //request.getSession().setAttribute("dashboardDatasetList", datasetList);

    }


    private void setDataForOvcByGender(List dashboardDatasetList) {


    DashboardChartGenerator generator = new DashboardChartGenerator();
    OvcSummaryBean ovcSummaryBean = null;

    int grandTotalMale = 0;
    int grandTotalFemale = 0;
    int grandTotal = 0;
    DaoUtil util=new DaoUtil();



         List lgas = null;
        try {
            lgas = generator.getLgas();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

         if (!lgas.isEmpty()) {
                if (!lgas.contains(null)) {
                   System.err.println("This is no of Lgas: " + lgas.size());


                   for(Object s:lgas) {

                       String lgaName = (String)s;
                       List ovcCount = null;

                       ovcSummaryBean = new OvcSummaryBean();


                       int male = 0;
                       int female = 0;
                       int total = 0;


                    try {

                        ovcCount = generator.fetchOvcCount(lgaName);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    lgaName=util.getLgaName(lgaName);
                       if(!ovcCount.isEmpty()) {
                           if(!ovcCount.contains(null)) {


                               System.err.println("This is ovc/lga: " + ovcCount.size());

                               for(Object s2:ovcCount) {
                                   ovc = (Ovc)s2;
                                   if(ovc.getGender() != null) {

                                   if(ovc.getGender().equals("Male")) {

                                       male++;
                                   }
                                   else if(ovc.getGender().equals("Female")) {

                                       female++;
                                   }


                                   }
                               }


                               ovcSummaryBean.setLga(lgaName);
                               ovcSummaryBean.setMaleCount(male);
                               ovcSummaryBean.setFemaleCount(female);
                               total = male + female;
                               ovcSummaryBean.setTotal(total);

                               dashboardDatasetList.add(ovcSummaryBean);


                               grandTotalMale += male;
                               grandTotalFemale += female;
                               grandTotal += total;


                                System.err.println("This is maleCount:" + male);
                                System.err.println("This is femaleCount:" + female);
                                System.err.println("This is total:" + total);

                                System.err.println("This is ovcCount:" + ovcCount.size());

                           }
                       }


                   }



                    ovcSummaryBean = new OvcSummaryBean();
                    ovcSummaryBean.setLga("Total");
                    ovcSummaryBean.setMaleCount(grandTotalMale);
                    ovcSummaryBean.setFemaleCount(grandTotalFemale);
                    ovcSummaryBean.setTotal(grandTotal);

                    dashboardDatasetList.add(ovcSummaryBean);


                    System.err.println("This is grandTotalMale:" + grandTotalMale);
                    System.err.println("This is grandTotalFemale:" + grandTotalFemale);
                    System.err.println("This is grandTotal:" + grandTotal);


                }

            }

    }


    public String getDateAsString() {

        DashboardChartGenerator generator = new DashboardChartGenerator();
        List dateParts = null;
        String dateAsString = null;



        try {
            System.err.println("This is period ending: " + generator.getMaxDate());
            dateParts = generator.tokenizeStr(generator.getMaxDate(), "-");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        if(!dateParts.isEmpty()) {
            if(!dateParts.contains(null)) {
                Calendar cal = Calendar.getInstance();

                cal.set(Integer.parseInt((String)dateParts.get(0)), Integer.parseInt((String)dateParts.get(1)) - 1, Integer.parseInt((String)dateParts.get(2)));
                dateAsString = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
                dateAsString += " " + (String)dateParts.get(0);
            }
        }
        return dateAsString;

    }
}
