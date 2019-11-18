/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

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
//import java.awt.Font;
import java.awt.Color;
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
public class PdfHelloWorldAction extends org.apache.struts.action.Action {
    
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
        //CSI analysis ${subtitle} <br> ${agetitle}
        Font f=FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);
        String title=(String)session.getAttribute("subtitle");
        String ageTitle=(String)session.getAttribute("agetitle");
        String[] params=(String[])session.getAttribute("params");
        String orgUnitTitle="State: "+params[0]+"\t"+"        LGA: "+params[1]+"\t"+"         CBO: "+params[2];
        
        Paragraph titleParag=new Paragraph(new Phrase("CSI analysis "+title,f));
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
        
        float[] colsWidth = {2f,1f,1f,1f,1f, 1f};
        PdfPTable table2 = new PdfPTable(colsWidth);
        PdfPCell c2 = new PdfPCell(new Phrase("Indicator name",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table2.addCell(c2);
                c2 = new PdfPCell(new Phrase("Good",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table2.addCell(c2);
                c2 = new PdfPCell(new Phrase("Fair",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table2.addCell(c2);
                c2 = new PdfPCell(new Phrase("Bad",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table2.addCell(c2);
                c2 = new PdfPCell(new Phrase("Very bad",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table2.addCell(c2);
                c2 = new PdfPCell(new Phrase("Total",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table2.addCell(c2);

        List countList=(List)session.getAttribute("ovcCount");
        
        List csiCountList=new ArrayList();
        
                for(Object s:countList)
                {
                    String listElement="";
                    csiCountList=(List)s;
                    int total=0;
                    int j=csiCountList.size()-1;
                    for(int i=j; i>=0; i--)
                    {
                        listElement=csiCountList.get(i).toString();
                        Phrase phr=new Phrase(listElement,f);
                        //String str=new String("An");
                        table2.addCell(phr);
                        if(i<j)
                        total+=Integer.parseInt(listElement);
                    }

                    table2.addCell(new Phrase(total+"",f));
                }
                document.add(table2);
        
    }catch(Exception e){
        e.printStackTrace();
    }
    document.close();
    return null;

    }
}
