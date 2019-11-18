/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ImportExport;

import com.fhi.kidmap.report.IndicatorDictionary;
import com.fhi.kidmap.report.SummaryStatisticsReportGenerator;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.VersionManager;
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
import java.util.List;

/**
 *
 * @author smomoh
 */
public class DataTransferSummaryGenerator 
{
    public String generateDataExportSummaryInPDF(DataExportSummary des)
    {
        String reportDestination=null;
        
        return reportDestination;
    }
    public static void writeDataExportSummaryToPDF(DataExportSummary des)
    {
        AppUtility appUtil=new AppUtility();
        try
        {
            appUtil.createReportDirectory();
            Paragraph p =new Paragraph(" ");
            Font f=FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);

            float[] colsWidth = {0.5f};
            float[] colsWidth1 = {0.5f,0.5f};
            float[] colsWidth2 = {0.5f,0.5f,0.5f,0.5f,0.5f};
            PdfPTable table = new PdfPTable(colsWidth1);
            PdfPTable table2 = new PdfPTable(colsWidth2);
            PdfPTable table3 = new PdfPTable(colsWidth);
            
            table.setWidthPercentage(100f);
            table2.setWidthPercentage(100f);
            table3.setWidthPercentage(100f);
            
            Document document = new Document();
            table = new PdfPTable(colsWidth1);

            table2 = new PdfPTable(colsWidth1);
            table3 = new PdfPTable(colsWidth);

            String directory=appUtil.getDbRootDirectory()+"/Reports/DataTransfer/";
            appUtil.createDirectories(directory);
            String lgaName=des.getLevel3OuName();
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
            String wardLabelName=des.getLevel4OuName();
            if(wardLabelName !=null)
            {
                wardLabelName=wardLabelName.replace("\\","_");
                wardLabelName=wardLabelName.replace("/","_");
            }
            String stateName="State: "+des.getLevel2OuName();
            String lga="Lga: "+des.getLevel3OuName();
            String wardName="Ward/Community: "+des.getLevel4OuName();
            String organization="Organization: "+des.getCboName();
            String title=" Data export summary";
            String reportPeriod="Reporting Period: All ";
            String versionNumber=VersionManager.getVersionName();
            
            String documentId=des.getExportId();
            if(documentId !=null)
            documentId=documentId.toUpperCase();
            PdfWriter.getInstance(document, new FileOutputStream(directory+"/"+des.getLevel2OuName()+"-"+lgaName+"-"+wardLabelName+"-"+des.getExportPeriod()+"-"+documentId+".pdf"));

            

            reportPeriod="Reporting Period: "+des.getExportPeriod();// util.getMonthAsString(Integer.parseInt(datimForm.getStartMth()))+" "+ datimForm.getStartYr()+" to   end of "+util.getMonthAsString(Integer.parseInt(datimForm.getEndMth()))+" "+datimForm.getEndYr()+"       Partner: "+datimForm.getPartnerName();
            //document.add(title);
            String organizationUnit=stateName+"               "+lga+"            "+organization+"          "+wardName;
            //String reportPeriod="Reporting Period: 01 "+ util.getMonthAsString(Integer.parseInt(merReportParam[3]))+" "+ merReportParam[4]+" to   end of "+util.getMonthAsString(Integer.parseInt(merReportParam[5]))+" "+merReportParam[6]+"       Partner: "+merReportParam[13];
            Paragraph titleParag=new Paragraph(new Phrase(title,f));
            Paragraph periodParag=new Paragraph(new Phrase(reportPeriod,f));
            Paragraph organizationUnitParag=new Paragraph(new Phrase(organizationUnit,f));
            Paragraph versionParag=new Paragraph(new Phrase(versionNumber,f));
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

            table.addCell(getPDFCell(f,"General information", 2,0,Element.ALIGN_CENTER));
            table.addCell(getPDFCell(f,"Document Id", 0,0,Element.ALIGN_LEFT));
            table.addCell(getPDFCell(f,des.getExportId()+"", 0,0,Element.ALIGN_LEFT));
            table.addCell(getPDFCell(f,"Source system time", 0,0,Element.ALIGN_LEFT));
            table.addCell(getPDFCell(f,des.getSystemTime()+"", 0,0,Element.ALIGN_LEFT));
            
            table.addCell(getPDFCell(f,"Number of households ever enrolled in database", 0,0,Element.ALIGN_LEFT));
            table.addCell(getPDFCell(f,des.getHhEverEnrolledInDatabase()+"", 0,0,Element.ALIGN_LEFT));
            table.addCell(getPDFCell(f,"Number of households currently enrolled in database", 0,0,Element.ALIGN_LEFT));
            table.addCell(getPDFCell(f,des.getHhCurrentlyEnrolledInDatabase()+"", 0,0,Element.ALIGN_LEFT));
            
            table.addCell(getPDFCell(f,"Number of OVC ever enrolled in database", 0,0,Element.ALIGN_LEFT));
            table.addCell(getPDFCell(f,des.getVcEverEnrolledInDatabase()+"", 0,0,Element.ALIGN_LEFT));
            table.addCell(getPDFCell(f,"Number of OVC currently enrolled in database", 0,0,Element.ALIGN_LEFT));
            table.addCell(getPDFCell(f,des.getVcCurrentlyEnrolledInDatabase()+"", 0,0,Element.ALIGN_LEFT));
            
            
            table2.addCell(getPDFCell(f,"Export specific information", 2,0,Element.ALIGN_CENTER));
            table2.addCell(getPDFCell(f,"Number of Household enrollment records exported", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,des.getNoOfHHEnrollmentRecordsExported()+"", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,"Number of OVC enrollment records exported", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,des.getNoOfVCEnrollmentRecordsExported()+"", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,"Number of OVC caregiver records exported", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,des.getNoOfCaregiverEnrollmentRecordsExported()+"", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,"Number of Household followup records exported", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,des.getNoOfHHFollowupAssessmentRecordsExported()+"", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,"Number of OVC followup records exported", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,des.getNoOfVCFollowupAssessmentRecordsExported()+"", 0,0,Element.ALIGN_LEFT));
            
            table2.addCell(getPDFCell(f,"Number of OVC service records exported", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,des.getNoOfVCServiceRecordsExported()+"", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,"Number of Household service records exported", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,des.getNoOfHHServiceRecordsExported()+"", 0,0,Element.ALIGN_LEFT));
            
            table2.addCell(getPDFCell(f,"Number of CSI records exported", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,des.getNoOfCSIRecordsExported()+"", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,"Number of Care and support records exported", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,des.getCareAndSupportRecordsExported()+"", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,"Number of Caregiver expenditure records exported", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,des.getCaregiverExpenditureRecordsExported()+"", 0,0,Element.ALIGN_LEFT));
            
            table2.addCell(getPDFCell(f,"Number of Careplan achievement checklist records exported", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,des.getCareplanAchievementRecordsExported()+"", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,"Number of Caregiver TB/HIV records exported", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,des.getNoOfCgiverTBHIVRecordsExported()+"", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,"Number of OVC TB/HIV records exported", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,des.getNoOfVCTBHIVRecordsExported()+"", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,"Number of HIV Risk assessment records exported", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,des.getNoOfHIVRiskAssessmentRecordsExported()+"", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,"Number of Household Referral records exported", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,des.getNoOfReferralRecordsExported()+"", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,"Number of School records exported", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,des.getNoOfSchoolRecordsExported()+"", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,"Number of Training records exported", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,des.getNoOfTrainingRecordsExported()+"", 0,0,Element.ALIGN_LEFT));
            
            table2.addCell(getPDFCell(f,"Number of withdrawal records exported", 0,0,Element.ALIGN_LEFT));
            table2.addCell(getPDFCell(f,des.getNoOfWithdrawalRecordsExported()+"", 0,0,Element.ALIGN_LEFT));
            
            

            document.add(table);
            document.add(p);
            document.add(p);
            document.add(table2);
            document.add(versionParag);
            document.close();
                                    
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static PdfPCell getPDFCell(Font f,String phraseElement, int colspan,int rowspan,int alignment)
    {
        PdfPCell cell=new PdfPCell();
        cell= new PdfPCell(new Phrase(phraseElement,f));
        if(colspan > 1)
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(alignment);
        //cell.setBackgroundColor(bgcolor);//cell.getBackgroundColor()
        return cell;
    }
    public static DataExportSummary getDataExportSummaryWithGeneralInformation(String[] params)
    {
        DataExportSummary des=new DataExportSummary();
        try
        {  
            SummaryStatisticsReportGenerator ssrg=new SummaryStatisticsReportGenerator();
            IndicatorDictionary ind=new IndicatorDictionary();
            String vcCurrentlyEnrolledId=ind.getIndicatorForNumberOfOvcCurrentlyEnrolled().getIndicatorId();
            String vcEverEnrolledId=ind.getIndicatorForNumberOfOvcEverEnrolled().getIndicatorId();
            String hhEverEnrolledId=ind.getIndicatorForNumberOfHouseholdsEverEnrolled().getIndicatorId();
            String hhCurrentlyEnrolledId=ind.getIndicatorForNumberOfHouseholdsCurrentlyEnrolled().getIndicatorId();
            String[] ovcIndicators={vcEverEnrolledId,vcCurrentlyEnrolledId};
            String[] hhIndicators={hhEverEnrolledId,hhCurrentlyEnrolledId};
            List ovcList=ssrg.getOvcEnrolledSummStatistics(null, params, ovcIndicators);
            List hhList=ssrg.getSummaryStaticsCountWithoutGenderDisaggregation(params,hhIndicators);
            if(ovcList !=null)
            {
                int value=0;
                for(int i=0; i<ovcList.size(); i++)
                {
                    List subList=(List)ovcList.get(i);
                    
                    System.err.println("value is "+value);
                    if(i==0)
                    {
                        value=Integer.parseInt(subList.get(3).toString());
                        des.setVcEverEnrolledInDatabase(value);
                    }
                    else if(i==1)
                    {
                        value=Integer.parseInt(subList.get(3).toString());
                        des.setVcCurrentlyEnrolledInDatabase(value);
                    }
                }
            }
            if(hhList !=null)
            {
                int value=0;
                
                for(int i=0; i<hhList.size(); i++)
                {
                    List subList=(List)hhList.get(i);
                    
                    System.err.println("value is "+value);
                    if(i==0)
                    {
                        value=Integer.parseInt(subList.get(1).toString());
                        des.setHhEverEnrolledInDatabase(value);
                    }
                    else if(i==1)
                    {
                        value=Integer.parseInt(subList.get(1).toString());
                        des.setHhCurrentlyEnrolledInDatabase(value);
                    }
                }
            }
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return des;
}
}
