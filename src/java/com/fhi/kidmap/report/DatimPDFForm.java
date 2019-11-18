/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.nomis.nomisutils.VersionManager;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class DatimPDFForm implements Serializable
{
    public void writePDFFormToFile(List reportList,String[] merReportParam,String fileName)
    {
        Document document = new Document();
        DaoUtil util=new DaoUtil();
        AppUtility appUtil=new AppUtility();
        try
        {
            String fileSeperator=appUtil.getFileSeperator();
            String directory=appUtil.getDbRootDirectory()+fileSeperator+"Reports";
            appUtil.createDirectories(directory);
            PdfWriter.getInstance(document, new FileOutputStream(directory+fileSeperator+fileName+"_Datim.pdf"));

            //PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            document.left(0.5f);
            document.right(0.5f);

            Paragraph p =new Paragraph(" ");
            Font f=FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);

            //String[] merReportParam=(String[])session.getAttribute("merReportParam");

            //String reportParam=(String)session.getAttribute("reportParam");
            String state="State: "+util.getStateName(merReportParam[0]);
            String lga="Lga: "+fileName;
            String organization="Organization: "+util.getOrganizationName(merReportParam[2]);
            String title=" Datim ForM";
            //document.add(title);
            String organizationUnit=state+"               "+lga+"            "+organization;
            String reportPeriod="Reporting Period: 01 "+ util.getMonthAsString(Integer.parseInt(merReportParam[3]))+" "+ merReportParam[4]+" to   end of "+util.getMonthAsString(Integer.parseInt(merReportParam[5]))+" "+merReportParam[6]+"       Partner: "+merReportParam[13];
            Paragraph titleParag=new Paragraph(new Phrase(title,f));
            Paragraph periodParag=new Paragraph(new Phrase(reportPeriod,f));
            Paragraph organizationUnitParag=new Paragraph(new Phrase(organizationUnit,f));
            titleParag.setAlignment(Paragraph.ALIGN_CENTER);
            periodParag.setAlignment(Paragraph.ALIGN_CENTER);
            organizationUnitParag.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(titleParag);
            document.add(p);
            document.add(organizationUnitParag);
            document.add(p);
            document.add(periodParag);
            document.add(p);
            float[] colsWidth = {0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f};
            float[] colsWidth2 = {0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f};
            PdfPTable table = new PdfPTable(colsWidth);
            PdfPTable table2 = new PdfPTable(colsWidth2);
            PdfPTable table3 = new PdfPTable(colsWidth);
            PdfPTable table4 = new PdfPTable(colsWidth);
            table.setWidthPercentage(100f);
            table2.setWidthPercentage(100f);
            table3.setWidthPercentage(100f);
            table4.setWidthPercentage(100f);

            PdfPCell c2 = new PdfPCell(new Phrase("OVC Datim Reporting form",f));
            c2.setColspan(8);
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c2);

            //List reportList=(List)session.getAttribute("merReportList");
            List mainList=new ArrayList();
            List excelList=new ArrayList();
            List list2=new ArrayList();
            List list3=new ArrayList();
            List list4=new ArrayList();
            if(reportList !=null)
            {
                mainList.add(reportList.get(0));
                mainList.add(reportList.get(1));
                mainList.add(reportList.get(2));
            }
            int k=1;
            //AppUtility appUtil=new AppUtility();
            String[] ageSegregation={"<1","1-4","5-9","10-14","15-17","18-24","25+"};
                //List list=null;
                List subList=null;
                List recordList=null;
                List maleList=null;
                List femaleList=null;
                List finalList=null;
                List genderList=new ArrayList();
                Phrase phr=null;
                int tracker=0;
                int t=0;
                String disaggregationLabel="Disaggregated by age and sex";
                String indicator="Indicator name";
                    for(Object s:mainList)
                    {
                        recordList=(List)s;
                        tracker++;
                        for(Object obj:recordList)
                        {
                            //subList contains records of both male and female
                            subList=(List)obj;
                            
                            k++;
                            indicator=(String)subList.get(0);
                            phr=new Phrase(" "+indicator,f);
                            c2 = new PdfPCell(phr);
                            /*add an empty row to create space before the next set of records*/
                            c2.setColspan(8);
                            c2.setBackgroundColor(BaseColor.GREEN);

                            //Seperate the contents of subList into male and female lists
                            maleList=new ArrayList();
                            femaleList=new ArrayList();
                            int numerator=0;
                            //if(subList.size()>15)
                            numerator=new Integer(subList.get(subList.size()-1).toString());
                            maleList.add("Male");
                            femaleList.add("Female");
                            t++;
                                for(int l=1; l<subList.size(); l++)
                                {
                                   if(l<8)
                                   {
                                       maleList.add(subList.get(l));
                                   }
                                   else if(l>8 && l<16)
                                   {
                                      femaleList.add(subList.get(l));
                                   }
                                }
                                table.addCell(c2);
                                if(tracker<3)
                                {
                                    c2 = new PdfPCell(new Phrase("Numerator",f));
                                    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell(c2);

                                    c2 = new PdfPCell(new Phrase(numerator+"",f));
                                    c2.setColspan(7);
                                    table.addCell(c2);
                                }
                                if(tracker <3)
                                {
                                    c2 = new PdfPCell(new Phrase(disaggregationLabel+"",f));
                                    c2.setColspan(8);
                                    c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                    table.addCell(c2);
                                }

                            genderList=new ArrayList();
                            genderList.add(femaleList);
                            genderList.add(maleList);
                                               
                            //add female component to the form
                            for(int m=0; m<genderList.size(); m++)
                            {
                                finalList=(List)genderList.get(m);
                                System.err.println("tracker is "+tracker);
                                c2 = new PdfPCell(new Phrase(finalList.get(0).toString(),f));
                                table.addCell(c2);
                                c2.setBackgroundColor(BaseColor.GREEN);
                                for(int i=0; i<ageSegregation.length; i++)
                                {
                                    c2 = new PdfPCell(new Phrase(ageSegregation[i],f));
                                    table.addCell(c2);
                                }
                                phr=new Phrase(" ",f);
                                table.addCell(phr);
                                for(int j=1; j<finalList.size(); j++)
                                {
                                    int value=new Integer(finalList.get(j).toString());
                                    phr=new Phrase(value+"",f);
                                    table.addCell(phr);
                                    System.err.println("value is "+value);
                                }
                            }
                            if(tracker <3)
                            {
                                c2 = new PdfPCell(new Phrase("Sub-total",f));
                                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(c2);

                                c2 = new PdfPCell(new Phrase(numerator+"",f));
                                c2.setColspan(7);
                                table.addCell(c2);
                            }
                            if(tracker==2)
                            {
                                disaggregationLabel="Disaggregated by age, sex and Service Area - Required on for DREAMS countries";
                                c2 = new PdfPCell(new Phrase(disaggregationLabel+"",f));
                                c2.setColspan(8);
                                c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                table.addCell(c2);
                            }
                        }
                    }
                    document.add(table);
                        
                    document.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void writeDatim2017FormToPDF(List reportList)
    {
        
        DaoUtil util=new DaoUtil();
        AppUtility appUtil=new AppUtility();
        try
        {
            
            Paragraph p =new Paragraph(" ");
            Font f=FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);

            float[] colsWidth = {0.5f};
            float[] colsWidth1 = {0.5f,0.5f};
            float[] colsWidth2 = {0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f};
            PdfPTable table = new PdfPTable(colsWidth1);
            PdfPTable table2 = new PdfPTable(colsWidth2);
            PdfPTable table3 = new PdfPTable(colsWidth);
            
            Paragraph versionParag=new Paragraph(new Phrase(VersionManager.getVersionName(),f));
            //PdfPTable table4 = new PdfPTable(colsWidth);
            table.setWidthPercentage(100f);
            table2.setWidthPercentage(100f);
            table3.setWidthPercentage(100f);
            //table4.setWidthPercentage(100f);

            
            
            if(reportList !=null)
            {
                for(int i=0; i<reportList.size(); i++)
                {
                    Document document = new Document();
                    table = new PdfPTable(colsWidth1);
                    
                    table2 = new PdfPTable(colsWidth2);
                    table3 = new PdfPTable(colsWidth);
                    DatimReportTemplate datimForm=(DatimReportTemplate)reportList.get(i);
                    String directory=appUtil.getDbRootDirectory()+"/Reports/Datim2017/"+datimForm.getState();
                    appUtil.createDirectories(directory);
                    String lgaName=datimForm.getLga();
                    if(lgaName !=null)
                    {  
                        if(lgaName.indexOf("\"") !=-1)
                        {
                            String[] lgaNamePart=lgaName.split("\"");
                            if(lgaNamePart !=null && lgaNamePart.length>0)
                            {
                                lgaName=" ";
                                for(int j=0; j<lgaNamePart.length; j++)
                                {
                                    lgaName+=lgaNamePart[j];
                                    if(j<lgaNamePart.length-1)
                                    lgaName+="-";
                                }
                            }
                        }
                        if(lgaName.indexOf("/") !=-1)
                        {
                            String[] lgaNamePart=lgaName.split("/");
                            if(lgaNamePart !=null && lgaNamePart.length>0)
                            {
                                lgaName=" ";
                                for(int j=0; j<lgaNamePart.length; j++)
                                {
                                    lgaName+=lgaNamePart[j];
                                    if(j<lgaNamePart.length-1)
                                    lgaName+="-";
                                }
                            }
                        }
                        
                    }
                    PdfWriter.getInstance(document, new FileOutputStream(directory+"/"+lgaName+"_Datim2017.pdf"));
                    
                    String stateName="State: "+datimForm.getState();
                    String lga="Lga: "+datimForm.getLga();
                    String organization="Organization: "+datimForm.getCbo();
                    String title=" Datim Form";
                    String reportPeriod="Reporting Period: All ";
                    
                    reportPeriod="Reporting Period: "+datimForm.getPeriod();// util.getMonthAsString(Integer.parseInt(datimForm.getStartMth()))+" "+ datimForm.getStartYr()+" to   end of "+util.getMonthAsString(Integer.parseInt(datimForm.getEndMth()))+" "+datimForm.getEndYr()+"       Partner: "+datimForm.getPartnerName();
                    //document.add(title);
                    String organizationUnit=stateName+"               "+lga+"            "+organization;
                    //String reportPeriod="Reporting Period: 01 "+ util.getMonthAsString(Integer.parseInt(merReportParam[3]))+" "+ merReportParam[4]+" to   end of "+util.getMonthAsString(Integer.parseInt(merReportParam[5]))+" "+merReportParam[6]+"       Partner: "+merReportParam[13];
                    Paragraph titleParag=new Paragraph(new Phrase(title,f));
                    Paragraph periodParag=new Paragraph(new Phrase(reportPeriod,f));
                    Paragraph organizationUnitParag=new Paragraph(new Phrase(organizationUnit,f));
                    titleParag.setAlignment(Paragraph.ALIGN_CENTER);
                    periodParag.setAlignment(Paragraph.ALIGN_CENTER);
                    organizationUnitParag.setAlignment(Paragraph.ALIGN_CENTER);
                    
                    document.open();
                    document.left(0.5f);
                    document.right(0.5f);
                    document.add(titleParag);
                    document.add(p);
                    document.add(organizationUnitParag);
                    document.add(periodParag);
                    document.add(p);
                  
                    //System.err.println("datimForm.getOvc_servActive10to14Female() "+datimForm.getOvc_servActive10to14Female()+" datimForm.getOvc_servActive10to14Male() is "+datimForm.getOvc_servActive10to14Male());
                    table.addCell(getPDFCell(f,"OVC HIV_STAT", 2,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,"Number of OVC with HIV status reported to implementing partner (including status not reported)", 2,0,Element.ALIGN_LEFT));
                    
                    table.addCell(getPDFCell(f,"Numerator", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,datimForm.getHiv_statNumerator()+"", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,"Disaggregated by status type", 2,0,Element.ALIGN_LEFT));
                   
                    table.addCell(getPDFCell(f,"Reported HIV positive to IP (includes tested in the reporting period and known positive)", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,datimForm.getTotalPositive()+"", 0,0,Element.ALIGN_LEFT));
                   
                    table.addCell(getPDFCell(f,"Of those positive: Currently receiving ART", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,datimForm.getEnrolledOnART()+"", 0,0,Element.ALIGN_LEFT));
                                    
                    table.addCell(getPDFCell(f,"Of those positive: Not currently receiving ART", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,datimForm.getNotEnrolledOnART()+"", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,"Reported HIV negative to IP", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,datimForm.getTotalNegative()+"", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,"No HIV status reported to Implementing Partner", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,datimForm.getTotalUnknown()+"", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,"Of those not reported: test not indicated", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,datimForm.getTestNotIndicated()+"", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,"Of those not reported: Other reasons", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,datimForm.getOtherReasons()+"", 0,0,Element.ALIGN_LEFT));
                    
                    table.addCell(getPDFCell(f," ", 2,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,"OVC_SERV", 2,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,"Number of beneficiaries served by PEPFAR OVC programs for children and families affected by HIV/AIDS", 2,0,Element.ALIGN_LEFT));
                    
                    table.addCell(getPDFCell(f,"Numerator", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,datimForm.getOvc_servNumerator()+"", 0,0,Element.ALIGN_LEFT));
                    
                    /*table.addCell(getPDFCell(f,"Disaggregated by program status (active and graduated) by age/sex (Fine disaggregation)", 2,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,"Active (Received at least one service in each of the preceding two quarters)", 8,0,Element.ALIGN_LEFT));
                    
                    table.addCell(getPDFCell(f,datimForm.getOvc_servActive()+"", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,"Graduated", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,datimForm.getOvc_servGraduated()+"", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,"Transfered", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,datimForm.getOvc_servTransfered()+"", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,"Exited without graduation", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f,datimForm.getOvc_servExitedWithoutGraduation()+"", 0,0,Element.ALIGN_LEFT));
                    table.addCell(getPDFCell(f," ", 2,0,Element.ALIGN_LEFT));*/
                    
                    table2.addCell(getPDFCell(f,"required", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"Disaggregated by program status (active and graduated) by age/sex (Fine disaggregation)", 7,0,Element.ALIGN_LEFT));
                    
                    table2.addCell(getPDFCell(f,"Active (Received at least one service in each of the preceding two quarters)", 8,0,Element.ALIGN_LEFT));
                    
                    table2.addCell(getPDFCell(f,"Female", 0,2,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"Unknown age", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"<1", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"1-4", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"5-9", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"10-14", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"15-17", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"18+", 0,0,Element.ALIGN_LEFT));
                                        
                    table2.addCell(getPDFCell(f," ", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servActiveLessThan1Female()+" ", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servActive1to4Female()+"", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servActive5to9Female()+"", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servActive10to14Female()+"", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servActive15to17Female()+"", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servActive18AndAboveFemale()+"", 0,0,Element.ALIGN_LEFT));
                    
                    table2.addCell(getPDFCell(f," ", 8,0,Element.ALIGN_LEFT));
                    
                    table2.addCell(getPDFCell(f,"Male", 0,2,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"Unknown age", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"<1", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"1-4", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"5-9", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"10-14", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"15-17", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"18+", 0,0,Element.ALIGN_LEFT));
                                        
                    table2.addCell(getPDFCell(f," ", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servActiveLessThan1Male()+" ", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servActive1to4Male()+"", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servActive5to9Male()+"", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servActive10to14Male()+"", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servActive15to17Male()+"", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servActive18AndAboveMale()+"", 0,0,Element.ALIGN_LEFT));
                    
                    
                    //This is for graduated
                    table2.addCell(getPDFCell(f,"Graduated (Q2: Graduated in the past 6 months. At Q4: Report the number of children and parents/caregivers that exited in the past four quarters and did not return to active status)", 8,0,Element.ALIGN_LEFT));
                    
                    table2.addCell(getPDFCell(f,"Female", 0,2,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"Unknown age", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"<1", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"1-4", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"5-9", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"10-14", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"15-17", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"18+", 0,0,Element.ALIGN_LEFT));
                                        
                    table2.addCell(getPDFCell(f," ", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servGraduatedLessThan1Female()+" ", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servGraduated1to4Female()+"", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servGraduated5to9Female()+"", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servGraduated10to14Female()+"", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servGraduated15to17Female()+"", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servGraduated18AndAboveFemale()+"", 0,0,Element.ALIGN_LEFT));
                    
                    table2.addCell(getPDFCell(f," ", 8,0,Element.ALIGN_LEFT));
                    
                    table2.addCell(getPDFCell(f,"Male", 0,2,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"Unknown age", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"<1", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"1-4", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"5-9", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"10-14", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"15-17", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"18+", 0,0,Element.ALIGN_LEFT));
                                        
                    table2.addCell(getPDFCell(f," ", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servGraduatedLessThan1Male()+" ", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servGraduated1to4Male()+"", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servGraduated5to9Male()+"", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servGraduated10to14Male()+"", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servGraduated15to17Male()+"", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servGraduated18AndAboveMale()+"", 0,0,Element.ALIGN_LEFT));
                    
                    table2.addCell(getPDFCell(f,"required", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"At Q2: Report exits or transfers within the past 6 months. At Q4: Report exits or transfers within the past 12 months", 7,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f," ", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"Transfered to PEPFAR supported programs", 2,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"Transfer to Non-PEPFAR supported programs ", 3,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,"Exited without graduation", 2,0,Element.ALIGN_LEFT));
                    
                    table2.addCell(getPDFCell(f," ", 0,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f," ", 2,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servTransfered()+"", 3,0,Element.ALIGN_LEFT));
                    table2.addCell(getPDFCell(f,datimForm.getOvc_servExitedWithoutGraduation()+"", 2,0,Element.ALIGN_LEFT));
                    //datimForm.getOvc_servTransfered()
                    table3.addCell(table);
                    table3.addCell(table2);
                    document.add(table3);
                    document.add(versionParag);
                    //document.add(table2);
                    document.close();
                }
            }
               
                    
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public PdfPCell getPDFCell(Font f,String phraseElement, int colspan,int rowspan,int alignment)
    {
        PdfPCell cell=new PdfPCell();
        cell= new PdfPCell(new Phrase(phraseElement,f));
        if(colspan > 1)
        cell.setColspan(colspan);
        if(rowspan>1)
        cell.setRowspan(rowspan);
        cell.setHorizontalAlignment(alignment);
        //cell.setBackgroundColor(bgcolor);//cell.getBackgroundColor()
        return cell;
    }
    public List writeToExcel(List mainList, String stateName,String cboName,int mth,int year)
    {
        List mainExcelList=new ArrayList();
        List reportList=new ArrayList();
        if(mainList ==null || mainList.size()<3)
        {
            System.err.println("No records, mainList is empty");
            return mainExcelList;
        }
        if(mainList !=null && mainList.size()>2)
        {
            reportList.add(mainList.get(0));
            reportList.add(mainList.get(1));
            reportList.add(mainList.get(2));
        }
        String[] ageSegregation={"<1","1-4","5-9","10-14","15-17","18-24","25+"};
       
        List maleExcelList=new ArrayList();
        List femaleExcelList=new ArrayList();
        int maleCount=0;
        int femaleCount=0;
        int maleTotal=0;
        int femaleTotal=0;
        String indicator=null;
        String maleTotalIndicator="";
        String femaleTotalIndicator="";
        List subList=null;
        List recordList=null;
        for(Object obj:reportList)
        {
            recordList=(List)obj;
            for(int i=0; i<recordList.size(); i++)
            {
                maleCount=0;
                femaleCount=0;
                maleTotal=0;
                femaleTotal=0;
                //subList contains records of both male and female
                subList=(List)recordList.get(i);
                indicator=(String)subList.get(0);
                //Seperate the contents of subList into male and female lists
                for(int l=1; l<subList.size(); l++)
                {
                   if(l<8)
                   {
                       maleExcelList.add(stateName);
                       maleExcelList.add(cboName);
                       maleExcelList.add(indicator+" (Male,"+ageSegregation[maleCount]+")");
                       maleExcelList.add(mth);
                       maleExcelList.add(year);
                       maleExcelList.add(subList.get(l));
                       /*Get the total values for male categories*/
                       maleTotal+=Integer.parseInt(subList.get(l).toString());
                       maleCount++;
                       System.err.println("Value at "+l+" is "+subList.get(l).toString());
                   }
                   else if(l>8 && l<16)
                   {
                        femaleExcelList.add(stateName);
                        femaleExcelList.add(cboName);
                        femaleExcelList.add(indicator+" (Female,"+ageSegregation[femaleCount]+")");
                        femaleExcelList.add(mth);
                        femaleExcelList.add(year);
                        femaleExcelList.add(subList.get(l));
                        /*Get the total values for female categories*/
                        femaleTotal+=Integer.parseInt(subList.get(l).toString());
                        femaleCount++;
                        System.err.println("Value at "+l+" is "+subList.get(l).toString());
                   }
                }
                    /*Add the total values for male to list*/
                    maleTotalIndicator=indicator+" (Total Male)";
                    maleExcelList.add(stateName);
                    maleExcelList.add(cboName);
                    maleExcelList.add(maleTotalIndicator);
                    maleExcelList.add(mth);
                    maleExcelList.add(year);
                    maleExcelList.add(maleTotal);
                    /*Add the total values for female to list*/
                    femaleTotalIndicator=indicator+" (Total Female)";
                    femaleExcelList.add(stateName);
                    femaleExcelList.add(cboName);
                    femaleExcelList.add(femaleTotalIndicator);
                    femaleExcelList.add(mth);
                    femaleExcelList.add(year);
                    femaleExcelList.add(femaleTotal);
            }
        }
        mainExcelList.addAll(maleExcelList);
        mainExcelList.addAll(femaleExcelList);
        return mainExcelList;
    }
    
}
