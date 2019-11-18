/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.dao.DaoUtil;
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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Siaka
 */
public class USGDataEntryFormInPdf extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
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
        HttpSession session=request.getSession();
        Document document = new Document();
        DaoUtil util=new DaoUtil();
    try{
        response.setContentType("application/pdf");
        //PdfWriter.getInstance(document, new FileOutputStream("/Users/smomoh/Desktop/Temp/PDFTest.pdf"));
        
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.left(0.5f);
        document.right(0.5f);

        Paragraph p =new Paragraph(" ");
        Font f=FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);
        
        String[] merReportParam=(String[])session.getAttribute("merReportParam");
        
        //String reportParam=(String)session.getAttribute("reportParam");
        String state="State: "+util.getStateName(merReportParam[0]);
        String organization="Organization: "+util.getOrganizationName(merReportParam[2]);
        String title=" MER Report FORM";
        //document.add(title);
        String organizationUnit=state+"               "+organization;
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
        float[] colsWidth = {0.5f,1.0f,1.0f,0.5f};
        float[] colsWidth2 = {0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f};
        PdfPTable table = new PdfPTable(colsWidth);
        PdfPTable table2 = new PdfPTable(colsWidth2);
        PdfPTable table3 = new PdfPTable(colsWidth);
        PdfPTable table4 = new PdfPTable(colsWidth);
        PdfPTable table5 = new PdfPTable(colsWidth);
        PdfPTable table6 = new PdfPTable(colsWidth);
        table.setWidthPercentage(100f);
        table2.setWidthPercentage(100f);
        table3.setWidthPercentage(100f);
        table4.setWidthPercentage(100f);
        table5.setWidthPercentage(100f);
        table6.setWidthPercentage(100f);

        PdfPCell c2 = new PdfPCell(new Phrase("OVC MER Reporting form",f));
        c2.setColspan(4);
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c2);
                
        List reportList=(List)session.getAttribute("merReportList");
        List list1=new ArrayList();
        List list2=new ArrayList();
        List list3=new ArrayList();
        List list4=new ArrayList();
        List caregiverList=new ArrayList();
        List hivBeneficiaryList=new ArrayList();
        if(reportList !=null)
        {
            list1=(List)reportList.get(0);
            list4=(List)reportList.get(1);
            list2=(List)reportList.get(2);
            list3=(List)reportList.get(3);
            caregiverList=(List)reportList.get(4);
            hivBeneficiaryList=(List)reportList.get(5);
        }
        int k=1;
        //AppUtility appUtil=new AppUtility();
        String[] ageSegregation={"<1","1-4","5-9","10-14","15-17","18+"};
        String[] beneficiaryAgeSegregation={"<1","1-4","5-9","10-14","15-17","18-24","25+"};
        String[] caregiverAgeSegregation={"0-17","18-24","25+"};
            //List list=null;
            List subList=null;
            Phrase phr=null;
            //int l=0;
            //String[] indicators={"Number of OVC newly enrolled","Number of eligible adults and children who received food and/or other nutrition services","Number of HIV positive OVC that received clinical services (including those on ART)","Number of active beneficiaries served by PEPFAR OVC programs for children and families affected by HIV/AIDS","Number of OVC receiving primary direct support (≥ 3 services)","Number of OVC receiving supplemental direct support (< 3 services)","Number of active beneficiaries accompanied otherwise supported for transport to HIV testing, care and/or treatment services at least once every three months (subset of OVC served)","Number of OVC who graduated","Number of care givers trained to improve their ability in caring for OVC"};
            String indicator="Indicator name";
                for(Object s:list1)
                {
                    subList=(List)s;
                    
                        indicator=(String)subList.get(0);
                        phr=new Phrase(" "+indicator,f);
                        c2 = new PdfPCell(phr);
                        /*add an empty row to create space before the next set of records*/
                        c2.setColspan(4);
                        c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        
                        table.addCell(c2);
                        
                        c2 = new PdfPCell(new Phrase("Age range",f));
                        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c2); 
                        c2 = new PdfPCell(new Phrase("Male",f));
                        table.addCell(c2);
                        c2 = new PdfPCell(new Phrase("Female",f));
                        table.addCell(c2);
                        c2 = new PdfPCell(new Phrase("Total",f));
                        table.addCell(c2);
                        //reset k to 1 for a fresh loop
                        k=1;
                        for(int j=0; j<ageSegregation.length; j++)
                        {
                            if(subList.size() <=(k+7))
                            continue;
                            phr=new Phrase(ageSegregation[j],f);
                            table.addCell(phr);
                            int maleCount=new Integer(subList.get(k).toString());
                            phr=new Phrase(maleCount+"",f);
                            table.addCell(phr);
                            int femaleCount=new Integer(subList.get(k+7).toString());
                            phr=new Phrase(femaleCount+"",f);
                            table.addCell(phr);
                            int total=maleCount+femaleCount;
                            phr=new Phrase(total+"",f);
                            table.addCell(phr);
                            k++;
                        }
                        
                    //}
                }
                document.add(table);
                for(Object s:list4)
                {
                    subList=(List)s;
                    
                        indicator=(String)subList.get(0);
                        phr=new Phrase(" "+indicator,f);
                        c2 = new PdfPCell(phr);
                        /*add an empty row to create space before the next set of records*/
                        c2.setColspan(4);
                        c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        
                        table5.addCell(c2);
                        
                        c2 = new PdfPCell(new Phrase("Age range",f));
                        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table5.addCell(c2); 
                        c2 = new PdfPCell(new Phrase("Male",f));
                        table5.addCell(c2);
                        c2 = new PdfPCell(new Phrase("Female",f));
                        table5.addCell(c2);
                        c2 = new PdfPCell(new Phrase("Total",f));
                        table5.addCell(c2);
                        //reset k to 1 for a fresh loop
                        k=1;
                        for(int j=0; j<beneficiaryAgeSegregation.length; j++)
                        {
                            if(subList.size() <=(k+7))
                            continue;
                            phr=new Phrase(beneficiaryAgeSegregation[j],f);
                            table5.addCell(phr);
                            int maleCount=new Integer(subList.get(k).toString());
                            phr=new Phrase(maleCount+"",f);
                            table5.addCell(phr);
                            int femaleCount=new Integer(subList.get(k+7).toString());
                            phr=new Phrase(femaleCount+"",f);
                            table5.addCell(phr);
                            int total=maleCount+femaleCount;
                            phr=new Phrase(total+"",f);
                            table5.addCell(phr);
                            k++;
                        }
                        
                    //}
                }
                document.add(table5);
                if(list2 !=null && !list2.isEmpty())
                {
                    List eduList=(List)list2.get(0);
                    List shelterList=(List)list2.get(1);
                    List nutritionList=(List)list2.get(2);
                    List psychoList=(List)list2.get(3);
                    List protectionList=(List)list2.get(4);
                    List ovcEconomicStrenghteningList=(List)list2.get(5);
                    /*add an empty row to create space before the next set of records*/
                    c2 = new PdfPCell(new Phrase(" ",f));
                    c2.setColspan(7);
                    table2.addCell(c2); 
                    c2 = new PdfPCell(new Phrase("Age range",f));
                    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table2.addCell(c2); 
                    c2 = new PdfPCell(new Phrase((String)eduList.get(0),f));
                    c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table2.addCell(c2);
                    c2 = new PdfPCell(new Phrase((String)shelterList.get(0),f));
                    c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table2.addCell(c2);
                    c2 = new PdfPCell(new Phrase((String)nutritionList.get(0),f));
                    c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table2.addCell(c2);
                    c2 = new PdfPCell(new Phrase((String)psychoList.get(0),f));
                    c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table2.addCell(c2);
                    c2 = new PdfPCell(new Phrase((String)protectionList.get(0),f));
                    c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table2.addCell(c2);

                    c2 = new PdfPCell(new Phrase((String)ovcEconomicStrenghteningList.get(0),f));
                    c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table2.addCell(c2);

                    int maleCount=0;
                    int femaleCount=0;

                    int eduTotal=0;
                    int shelterTotal=0;
                    int nutritionTotal=0;
                    int psychoTotal=0;
                    int protectionTotal=0;
                    int economicStrenghteningTotal=0;
                    k=1;
                    for(int j=0; j<ageSegregation.length; j++)
                    {
                        if(subList.size() <=(k+7))
                        continue;
                        phr=new Phrase(ageSegregation[j],f);
                        table2.addCell(phr);
                        maleCount=new Integer(eduList.get(k).toString());
                        femaleCount=new Integer(eduList.get(k+7).toString());
                        phr=new Phrase((maleCount+femaleCount)+"",f);
                        table2.addCell(phr);
                        eduTotal+=maleCount+femaleCount;
                        maleCount=new Integer(shelterList.get(k).toString());
                        femaleCount=new Integer(shelterList.get(k+7).toString());
                        phr=new Phrase((maleCount+femaleCount)+"",f);
                        table2.addCell(phr);
                        shelterTotal+=maleCount+femaleCount;
                        maleCount=new Integer(nutritionList.get(k).toString());
                        femaleCount=new Integer(nutritionList.get(k+7).toString());
                        phr=new Phrase((maleCount+femaleCount)+"",f);
                        table2.addCell(phr);

                        nutritionTotal+=maleCount+femaleCount;
                        maleCount=new Integer(psychoList.get(k).toString());
                        femaleCount=new Integer(psychoList.get(k+7).toString());
                        phr=new Phrase((maleCount+femaleCount)+"",f);
                        table2.addCell(phr);

                        psychoTotal+=maleCount+femaleCount;
                        maleCount=new Integer(protectionList.get(k).toString());
                        femaleCount=new Integer(protectionList.get(k+7).toString());
                        phr=new Phrase((maleCount+femaleCount)+"",f);
                        table2.addCell(phr);
                        protectionTotal+=maleCount+femaleCount;

                        maleCount=new Integer(ovcEconomicStrenghteningList.get(k).toString());
                        femaleCount=new Integer(ovcEconomicStrenghteningList.get(k+7).toString());
                        phr=new Phrase((maleCount+femaleCount)+"",f);
                        table2.addCell(phr);
                        economicStrenghteningTotal+=maleCount+femaleCount;
                        k++;
                    }
                    c2 = new PdfPCell(new Phrase("Total",f));
                    table2.addCell(c2);
                    c2 = new PdfPCell(new Phrase(eduTotal+"",f));
                    table2.addCell(c2);
                    c2 = new PdfPCell(new Phrase(shelterTotal+"",f));
                    table2.addCell(c2);
                    c2 = new PdfPCell(new Phrase(nutritionTotal+"",f));
                    table2.addCell(c2);
                    c2 = new PdfPCell(new Phrase(psychoTotal+"",f));
                    table2.addCell(c2);
                    c2 = new PdfPCell(new Phrase(protectionTotal+"",f));
                    table2.addCell(c2);
                    c2 = new PdfPCell(new Phrase(economicStrenghteningTotal+"",f));
                    table2.addCell(c2);
                    document.add(table2);
                }
                for(Object s:list3)
                {
                subList=(List)s;

                    indicator=(String)subList.get(0);
                    phr=new Phrase(" "+indicator,f);
                    c2 = new PdfPCell(phr);
                    c2.setColspan(4);
                    c2.setBackgroundColor(BaseColor.LIGHT_GRAY);

                    table3.addCell(c2);

                    c2 = new PdfPCell(new Phrase("Age range",f));
                    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table3.addCell(c2); //c2.setColspan(4);
                    c2 = new PdfPCell(new Phrase("Male",f));
                    table3.addCell(c2);
                    c2 = new PdfPCell(new Phrase("Female",f));
                    table3.addCell(c2);
                    c2 = new PdfPCell(new Phrase("Total",f));
                    table3.addCell(c2);
                    //reset k to 1 for a fresh loop
                    k=1;
                    for(int j=0; j<ageSegregation.length; j++)
                    {
                        if(subList.size() <=(k+7))
                        continue;
                        phr=new Phrase(ageSegregation[j],f);
                        table3.addCell(phr);
                        int maleCount=new Integer(subList.get(k).toString());
                        phr=new Phrase(maleCount+"",f);
                        table3.addCell(phr);
                        int femaleCount=new Integer(subList.get(k+7).toString());
                        phr=new Phrase(femaleCount+"",f);
                        table3.addCell(phr);
                        int total=maleCount+femaleCount;
                        phr=new Phrase(total+"",f);
                        table3.addCell(phr);
                        k++;
                    }

                //}
            }
            document.add(table3);
            for(Object s:caregiverList)
            {
                subList=(List)s;

                indicator=(String)subList.get(0);
                phr=new Phrase(" "+indicator,f);
                c2 = new PdfPCell(phr);
                c2.setColspan(4);
                c2.setBackgroundColor(BaseColor.LIGHT_GRAY);

                table4.addCell(c2);

                c2 = new PdfPCell(new Phrase("Age range",f));
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table4.addCell(c2); //c2.setColspan(4);
                c2 = new PdfPCell(new Phrase("Male",f));
                table4.addCell(c2);
                c2 = new PdfPCell(new Phrase("Female",f));
                table4.addCell(c2);
                c2 = new PdfPCell(new Phrase("Total",f));
                table4.addCell(c2);
                //reset k to 1 for a fresh loop
                k=1;
                for(int j=0; j<caregiverAgeSegregation.length; j++)
                {
                    if(subList.size() <=(k+4))
                    continue;
                    phr=new Phrase(caregiverAgeSegregation[j],f);
                    table4.addCell(phr);
                    int maleCount=new Integer(subList.get(k).toString());
                    phr=new Phrase(maleCount+"",f);
                    table4.addCell(phr);
                    int femaleCount=new Integer(subList.get(k+4).toString());
                    phr=new Phrase(femaleCount+"",f);
                    table4.addCell(phr);
                    int total=maleCount+femaleCount;
                    phr=new Phrase(total+"",f);
                    table4.addCell(phr);
                    k++;
                }
        }
        document.add(table4);
        for(Object s:hivBeneficiaryList)
        {
            subList=(List)s;

                indicator=(String)subList.get(0);
                phr=new Phrase(" "+indicator,f);
                c2 = new PdfPCell(phr);
                /*add an empty row to create space before the next set of records*/
                c2.setColspan(4);
                c2.setBackgroundColor(BaseColor.LIGHT_GRAY);

                table6.addCell(c2);

                c2 = new PdfPCell(new Phrase("Age range",f));
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table6.addCell(c2);
                c2 = new PdfPCell(new Phrase("Male",f));
                table6.addCell(c2);
                c2 = new PdfPCell(new Phrase("Female",f));
                table6.addCell(c2);
                c2 = new PdfPCell(new Phrase("Total",f));
                table6.addCell(c2);
                //reset k to 1 for a fresh loop
                k=1;
                for(int j=0; j<beneficiaryAgeSegregation.length; j++)
                {
                    if(subList.size() <=(k+7))
                    continue;
                    phr=new Phrase(beneficiaryAgeSegregation[j],f);
                    table6.addCell(phr);
                    int maleCount=new Integer(subList.get(k).toString());
                    phr=new Phrase(maleCount+"",f);
                    table6.addCell(phr);
                    int femaleCount=new Integer(subList.get(k+8).toString());
                    phr=new Phrase(femaleCount+"",f);
                    table6.addCell(phr);
                    int total=maleCount+femaleCount;
                    phr=new Phrase(total+"",f);
                    table6.addCell(phr);
                    k++;
                }

        }
        document.add(table6);
            document.close();
    }catch(Exception e){
        e.printStackTrace();
    }
    
    return null;
    }
}
