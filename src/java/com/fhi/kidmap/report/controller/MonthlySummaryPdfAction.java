/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report.controller;

import com.fhi.nomis.nomisutils.AppUtility;
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
 * @author Siaka
 */
public class MonthlySummaryPdfAction extends org.apache.struts.action.Action {

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
        AppUtility appUtil=new AppUtility();
    try
    {
        response.setContentType("application/pdf");
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.left(0.5f);
        document.right(0.5f);

        Paragraph p =new Paragraph(" ");
        Font f=FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);
        List summaryList=(List)session.getAttribute("mthSummarySessionObj");
        List economicStrenghteningList=(List)session.getAttribute("econStrengthList");
        List orgReportList=(List)session.getAttribute("orgReportList");
        List nationalMthlySummaryList=(List)session.getAttribute("nationalMthlySummaryList");
        List stateMthlySummaryList=(List)session.getAttribute("stateMthlySummaryList");
        List lgaMthlySummaryList=(List)session.getAttribute("lgaMthlySummaryList");
        List cboMthlySummaryList=(List)session.getAttribute("cboMthlySummaryList");
        String[] mthlysummparams=(String[])session.getAttribute("mthlysummparams");
        String[] reportLevel=(String[])session.getAttribute("reportLevel");
        String reportParam=(String)session.getAttribute("reportParam");
        
        String title=reportParam+" VC MONTHLY SUMMARY FORM";
        //document.add(title);
        String reportPeriod="Reporting Period: 01 "+ mthlysummparams[3]+" "+ mthlysummparams[4]+" to   end of "+mthlysummparams[5]+" "+mthlysummparams[6]+"       Partner: "+mthlysummparams[13];
        Paragraph titleParag=new Paragraph(new Phrase(title,f));
        Paragraph periodParag=new Paragraph(new Phrase(reportPeriod,f));
        titleParag.setAlignment(Paragraph.ALIGN_CENTER);
        periodParag.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(titleParag);
        document.add(p);
        document.add(periodParag);
        document.add(p);
        float[] colsWidth = {2.0f,0.3f,0.3f,0.3f,0.4f,0.3f,0.3f,0.3f,0.4f,0.3f};
        PdfPTable table = new PdfPTable(colsWidth);
        table.setWidthPercentage(100f);
        PdfPCell c2 =null;
        List mainList=new ArrayList();
        List subList=null;
        mainList.add(nationalMthlySummaryList);
        mainList.add(stateMthlySummaryList);
        mainList.add(lgaMthlySummaryList);
        mainList.add(cboMthlySummaryList);
        for(int i=0;i<mainList.size(); i++)
        {
            subList=(List)mainList.get(i);
            for(int j=0; j<subList.size(); j++)
            {
                List list=(List)subList.get(j);
                for(int k=0; k<list.size(); k+=2)
                {
                    c2=new PdfPCell(new Phrase(appUtil.removeTags((String)list.get(k)),f));
                    c2.setColspan(9);
                    table.addCell(c2);
                    c2=new PdfPCell(new Phrase(((Long)list.get(k+1)).toString(),f));
                    table.addCell(c2);
                }
            }
        }
        c2=new PdfPCell(new Phrase("Community VC Services Data Elements",f));
        c2.setRowspan(2);
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c2);
        c2=new PdfPCell(new Phrase("Male",f));
        c2.setColspan(4);
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c2);
        c2=new PdfPCell(new Phrase("Female",f));
        c2.setColspan(4);
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c2);
        c2=new PdfPCell(new Phrase("Total",f));
        c2.setRowspan(2);
        table.addCell(c2);
        
        c2=new PdfPCell(new Phrase("0-5",f));
        table.addCell(c2);
        c2=new PdfPCell(new Phrase("6-12",f));
        table.addCell(c2);
        c2=new PdfPCell(new Phrase("13-17",f));
        table.addCell(c2);
        c2=new PdfPCell(new Phrase("Total(M)",f));
        table.addCell(c2);
        c2=new PdfPCell(new Phrase("0-5",f));
        table.addCell(c2);
        c2=new PdfPCell(new Phrase("6-12",f));
        table.addCell(c2);
        c2=new PdfPCell(new Phrase("13-17",f));
        table.addCell(c2);
        c2=new PdfPCell(new Phrase("Total(F)",f));
        table.addCell(c2);
        List dataList=null;
        String cellContent=null;
        for(int l=0; l<summaryList.size(); l++)
        {
            int total=0;
            
            dataList=(List)summaryList.get(l);
            for(int m=0; m<dataList.size(); m++)
            {
                int grandTotal=0;
                List innerList=(List)dataList.get(m);
                while(innerList.size()<10)
                {
                    innerList.add(0);
                }
                for(int q=0; q<innerList.size(); q++)
                {
                    cellContent=innerList.get(q).toString();
                    
                    //System.err.println("innerList size is "+innerList.size());
                    //if(q>0)
                    //total+=(Integer.parseInt(innerList.get(q).toString()));
                    c2=new PdfPCell(new Phrase(appUtil.removeTags(cellContent),f));
                    table.addCell(c2);
                   /* if(q==4 || q==8)
                    {
                        //c2=new PdfPCell(new Phrase(total+"",f));
                        //table.addCell(c2);
                        grandTotal+=total;
                        total=0;
                    }*/
                }
                c2=new PdfPCell(new Phrase(grandTotal+"",f));
                //table.addCell(c2);
            }
            
        }
        List valueList=null;
        for(int r=0; r<economicStrenghteningList.size(); r++)
        {
            valueList=(List)economicStrenghteningList.get(r);
            String cellValue=" ";
            for(int t=0; t<valueList.size(); t++)
            {
                cellValue=appUtil.removeTags(valueList.get(t).toString());
                if(cellValue.indexOf("<label") !=-1)
                    cellValue=" ";
                c2=new PdfPCell(new Phrase(cellValue,f));
                table.addCell(c2);
            }
        }
        document.add(table);
        document.add(p);
        String completedByLine="Completed by:  Name_____________________________ Designation: _________________________Sign/Date: ______________________";
        String phoneLine="Telephone: ________________________________________ Email: ___________________________________________________________";
        Paragraph completedByLineParag=new Paragraph(new Phrase(completedByLine,f));
        Paragraph phoneLineParag=new Paragraph(new Phrase(phoneLine,f));
        completedByLineParag.setAlignment(Paragraph.ALIGN_CENTER);
        phoneLineParag.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(completedByLineParag);
        document.add(p);
        document.add(phoneLineParag);
        document.add(p);
        document.close();
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
        
        return null;
    }
}

