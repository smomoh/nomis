/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.kidmap.business.Ovc;
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
 * @author smomoh
 */
public class EnrollmentReportPdfAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    
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
        HttpSession session=request.getSession();
        Document document = new Document();
    try{
        response.setContentType("application/pdf");
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.left(0.5f);
        document.right(0.5f);
        
        Font f=FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);
        String title=(String)session.getAttribute("ovcrecordstitle");
        String ageTitle=(String)session.getAttribute("enrollagetitle");
        String[] params=(String[])session.getAttribute("orgparams");
        String orgUnitTitle="State: "+params[10]+"\t"+"        LGA: "+params[11]+"\t"+"         CBO: "+params[12]+"         Ward: "+params[13];

        Paragraph titleParag=new Paragraph(new Phrase("List of VC enrolled "+title,f));
        Paragraph ageTitleParag=new Paragraph(new Phrase(ageTitle,f));
        Paragraph orgTitleParag=new Paragraph(new Phrase(orgUnitTitle,f));
        titleParag.setAlignment(Paragraph.ALIGN_CENTER);
        ageTitleParag.setAlignment(Paragraph.ALIGN_CENTER);
        orgTitleParag.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(titleParag);
        document.add(ageTitleParag);
        document.add(orgTitleParag);

        document.addTitle("Pdf example");

        Paragraph p =new Paragraph(" ");
        document.add(p);

        float[] colsWidth = {0.6f,1.5f,1.5f,1.5f,1.5f, 0.6f,0.7f,0.9f,1f,1.2f,2f, 1.2f};
        PdfPTable table = new PdfPTable(colsWidth);
        table.setWidthPercentage(100f);
       
        PdfPCell c2 = new PdfPCell(new Phrase("SNo.",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c2);
                c2 = new PdfPCell(new Phrase("OVC Unique ID No.",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c2);
                c2 = new PdfPCell(new Phrase("Date of Enrollment",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c2);
                c2 = new PdfPCell(new Phrase("Surname",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c2);
                c2 = new PdfPCell(new Phrase("Other Names",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c2);
                c2 = new PdfPCell(new Phrase("Age",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c2);
                c2 = new PdfPCell(new Phrase("Age Unit",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c2);
                c2 = new PdfPCell(new Phrase("Gender",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c2);
                c2 = new PdfPCell(new Phrase("Phone",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c2);
                c2 = new PdfPCell(new Phrase("Caregiver name",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c2);
                c2 = new PdfPCell(new Phrase("Caregiver address",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c2);
                c2 = new PdfPCell(new Phrase("Caregiver phone",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c2);

        List ovcList=(List)session.getAttribute("ovcRecords");
        int i=1;
        

                for(Object s:ovcList)
                {
                    Ovc ovc=(Ovc)s;
                    Phrase phr=new Phrase(i+"",f);
                    table.addCell(phr);
                    phr=new Phrase(ovc.getOvcId(),f);
                    table.addCell(phr);
                    phr=new Phrase(ovc.getDateEnrollment(),f);
                    table.addCell(phr);
                    phr=new Phrase(ovc.getLastName(),f);
                    table.addCell(phr);
                    phr=new Phrase(ovc.getFirstName(),f);
                    table.addCell(phr);
                    phr=new Phrase(ovc.getAge()+"",f);
                    table.addCell(phr);
                    phr=new Phrase(ovc.getAgeUnit(),f);
                    table.addCell(phr);
                    phr=new Phrase(ovc.getGender(),f);
                    table.addCell(phr);
                    phr=new Phrase(ovc.getPhone(),f);
                    table.addCell(phr);
                    phr=new Phrase(ovc.getCaregiverName(),f);
                    table.addCell(phr);
                    phr=new Phrase(ovc.getCaregiverAddress(),f);
                    table.addCell(phr);
                    phr=new Phrase(ovc.getCaregiverPhone(),f);
                    table.addCell(phr);
                    i++;
                }
                document.add(table);

    }catch(Exception e){
        e.printStackTrace();
    }
    document.close();
    return null;
    }
}
