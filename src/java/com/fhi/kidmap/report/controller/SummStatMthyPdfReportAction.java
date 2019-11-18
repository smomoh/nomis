/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

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
public class SummStatMthyPdfReportAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    
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

        Paragraph p =new Paragraph(" ");

        Font f=FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);

        String[] params=(String[])session.getAttribute("summaryStatparams");
        List dateList=(List)session.getAttribute("dateLabels");

        String orgUnitTitle="State: "+params[5]+"\t"+"                        LGA: "+params[6]+"\t"+"                    CBO: "+params[17];
        
        Paragraph titleParag=new Paragraph(new Phrase("OVC Summary Statistics ("+dateList.get(0) +" - "+dateList.get(5)+")",f));
        Paragraph orgTitleParag=new Paragraph(new Phrase(orgUnitTitle,f));
        orgTitleParag.setAlignment(Paragraph.ALIGN_CENTER);
        titleParag.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(titleParag);
        document.add(p);
        document.add(orgTitleParag);

        document.add(p);
        float[] colsWidth = {0.6f,2.0f,0.6f,0.9f,0.6f,0.9f,0.6f,0.9f,0.6f,0.9f,0.6f,0.9f,0.6f,0.9f,0.6f,0.9f,1.0f};
        PdfPTable table = new PdfPTable(17);
        table.setWidthPercentage(100f);
        table.setWidths(colsWidth);

        PdfPCell c2 = new PdfPCell(new Phrase("SNo.",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c2);
                c2 = new PdfPCell(new Phrase("Indicator name",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c2);
                for(int i=0; i<dateList.size()-1; i++)
                {
                    c2 = new PdfPCell(new Phrase(dateList.get(i).toString(),f));
                    c2.setBackgroundColor(BaseColor.YELLOW);
                    c2.setColspan(2);
                    table.addCell(c2);
                }
                c2 = new PdfPCell(new Phrase("Total",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
                c2.setColspan(2);
		table.addCell(c2);
                c2 = new PdfPCell(new Phrase("Grand total",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c2);

                    Phrase phr=new Phrase(" ",f);
                    table.addCell(phr);
                    phr=new Phrase(" ",f);
                    table.addCell(phr);
                for(int i=0; i<dateList.size(); i++)
                {
                   phr=new Phrase("Male",f);
                    table.addCell(phr);
                   phr=new Phrase("Female",f);
                    table.addCell(phr);
                }
                phr=new Phrase(" ",f);
                table.addCell(phr);
        List summList=(List)session.getAttribute("summaryStatisticsPerMth");
        int i=1;

            List list=null;
                for(Object s:summList)
                {
                    int total=0;
                    int count=0;
                    int maleTotal=0;
                    int femaleTotal=0;
                    phr=new Phrase(i+"",f);
                    table.addCell(phr);
                    list=(List)s;
                    phr=new Phrase(list.get(0).toString()+"",f);
                    table.addCell(phr);
                    for(int j=1; j<list.size(); j++)
                    {
                        count=new Integer(list.get(j).toString());
                        phr=new Phrase(count+"",f);
                        table.addCell(phr);
                        if(j%2 >0)
                        maleTotal+=count;
                        else
                        femaleTotal+=count;
                    }
                    phr=new Phrase(maleTotal+"",f);
                        table.addCell(phr);
                    phr=new Phrase(femaleTotal+"",f);
                        table.addCell(phr);
                    total=maleTotal+femaleTotal;
                    phr=new Phrase(total+"",f);
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
