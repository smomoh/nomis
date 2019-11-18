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
public class SchAttendancePdfReportAction extends org.apache.struts.action.Action {
    
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
        String[] params=(String[])session.getAttribute("schAttenparams");
         String orgUnitTitle="State: "+params[11]+"\t"+"                        LGA: "+params[12]+"\t"+"                    Type: "+params[4];
         Paragraph titleParag=new Paragraph(new Phrase("School Attendance Summary",f));
        Paragraph orgTitleParag=new Paragraph(new Phrase(orgUnitTitle,f));
        orgTitleParag.setAlignment(Paragraph.ALIGN_CENTER);
        titleParag.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(titleParag);
        document.add(p);
        document.add(orgTitleParag);
        
        document.add(p);
        float[] colsWidth = {0.6f,2.0f,0.5f};
        PdfPTable table = new PdfPTable(colsWidth);
        table.setWidthPercentage(100f);

        PdfPCell c2 = new PdfPCell(new Phrase("SNo.",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c2);
                c2 = new PdfPCell(new Phrase("School name",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c2);
                c2 = new PdfPCell(new Phrase("Number of OVC",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
                table.addCell(c2);
        List schoolList=(List)session.getAttribute("schoolAttendanceCount");
        int i=1;

            List list=null;
                for(Object s:schoolList)
                {
                    list=(List)s;
                    Phrase phr=new Phrase(i+"",f);
                    table.addCell(phr);
                    phr=new Phrase((String)list.get(0),f);
                    table.addCell(phr);
                    int num=new Integer(list.get(1).toString());
                    phr=new Phrase(num+"",f);
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
