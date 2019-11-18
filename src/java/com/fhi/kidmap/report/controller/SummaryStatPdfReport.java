/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.report.controller;

import com.fhi.nomis.nomisutils.AppUtility;
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
public class SummaryStatPdfReport extends org.apache.struts.action.Action {
    
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

        Paragraph p =new Paragraph(" ");

        Font f=FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);
        String[] params=(String[])session.getAttribute("summaryStatparams");
        String period=(String)session.getAttribute("period");
        String agerange=(String)session.getAttribute("agegroup");
         String orgUnitTitle="State: "+params[5]+"\t"+"                        LGA: "+params[6]+"\t"+"                    CBO: "+params[17];
         String periodAndAgeTitle=period+"\t"+"            "+agerange;
         Paragraph titleParag=new Paragraph(new Phrase("OVC Summary Statistics",f));
        Paragraph periodAndAgeTitleParag=new Paragraph(new Phrase(periodAndAgeTitle,f));
        Paragraph orgTitleParag=new Paragraph(new Phrase(orgUnitTitle,f));
        Paragraph versionParag=new Paragraph(new Phrase(VersionManager.getVersionName(),f));
        orgTitleParag.setAlignment(Paragraph.ALIGN_CENTER);
        titleParag.setAlignment(Paragraph.ALIGN_CENTER);
        periodAndAgeTitleParag.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(titleParag);
        document.add(p);
        document.add(orgTitleParag);
        document.add(periodAndAgeTitleParag);

        document.add(p);
        float[] colsWidth = {0.5f,2.0f,0.5f,0.5f,0.5f};
        PdfPTable table = new PdfPTable(colsWidth);
        table.setWidthPercentage(100f);

        PdfPCell c2 = new PdfPCell(new Phrase("SNo.",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c2);
                c2 = new PdfPCell(new Phrase("Indicator name",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c2);
                c2 = new PdfPCell(new Phrase("Male",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c2);
                c2 = new PdfPCell(new Phrase("Female",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
                table.addCell(c2);
                c2 = new PdfPCell(new Phrase("Total",f));
                c2.setBackgroundColor(BaseColor.YELLOW);
                table.addCell(c2);
        List dataList=(List)session.getAttribute("summaryStatistics");
        List hhdataList=(List)session.getAttribute("orgAssessmentList");
        int i=1;
        AppUtility appUtil=new AppUtility();

        if(dataList !=null)
        {
            List list=null;
                for(Object s:dataList)
                {
                    list=(List)s;
                    Phrase phr=new Phrase(i+"",f);
                    table.addCell(phr);
                    phr=new Phrase(appUtil.removeTags((String)list.get(0)),f);
                    table.addCell(phr);
                    int maleCount=new Integer(list.get(1).toString());
                    phr=new Phrase(maleCount+"",f);
                    table.addCell(phr);
                    int femaleCount=new Integer(list.get(2).toString());
                    phr=new Phrase(femaleCount+"",f);
                    table.addCell(phr);
                    int total=maleCount+femaleCount;
                    phr=new Phrase(total+"",f);
                    table.addCell(phr);
                    i++;
                }
         }
        if(hhdataList !=null)
        {
            List list=null;
            System.err.println("hhdataList in SummaryStatPdfReport is "+hhdataList.size());
                for(Object s:hhdataList)
                {
                    list=(List)s;
                    Phrase phr=new Phrase(i+"",f);
                    table.addCell(phr);
                    phr=new Phrase(appUtil.removeTags((String)list.get(0)),f);
                    PdfPCell cell=new PdfPCell();
                    cell.setColspan(3);
                    cell.addElement(phr);
                    table.addCell(cell);
                    int total=new Integer(list.get(1).toString());
                    phr=new Phrase(total+"",f);
                    table.addCell(phr);
                    
                    
                    i++;
                }
         }
                document.add(table);
                document.add(versionParag);

    }catch(Exception e){
        e.printStackTrace();
    }
    document.close();
    return null;
    }
}
