/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.test;

/**
 *
 * @author smomoh
 */
public class TestConnection 
{
    //System.err.println("request.getServletPath() is "+request.getServletPath());
            /*String dbsource=getServlet().getServletContext().getRealPath("/Resources/dbs");
            String path=getServlet().getServletContext().getRealPath("/Resources/dbs");
            String confpath=getServlet().getServletContext().getRealPath("/Resources/conf");
            String path1=null;
            String dbDestination=null;
            if(path !=null && path.indexOf("webapps") !=-1)
            {
                String[] partArray=path.split("webapps");
                path1=partArray[0]+"bin\\loginconfig.txt";
                String result=AppUtility.readFiles(path1, "");
                if(result !=null && result.indexOf("=") !=-1)
                {
                    String[] resultArray=result.split("=");
                    dbDestination=resultArray[1];
                    File sourceDir=new File(dbsource);
                    File destinationDir=new File(dbDestination+"\\Nomis\\dbs");
                    
                    File confSourceDir=new File(dbsource);
                    File confDir=new File(dbDestination+"\\Nomis\\conf");
                    if(!destinationDir.exists())
                    destinationDir.mkdirs();
                    if(!confDir.exists())
                    confDir.mkdirs();
                    FileUtils.copyDirectory(sourceDir, destinationDir);
                    FileUtils.copyDirectory(confSourceDir, confDir);
                    //MoveFile.moveFileToAnotherDirectory(dbsource,dbDestination+"\\Nomis");
                    //appUtil.createDatabase(dbsource, dbDestination);
                    //appUtil.copyAndPasteDbFiles(confSourceDirectory, confDestinationDirectory);
                }
            }*/
            
            /*List list=DatabaseUtilities.getConnectionParameters();
            String path="";
            if(list !=null && !list.isEmpty())
                {
                    for(int i=0; i<list.size(); i++)
                    {
                        path=path+";"+list.get(i).toString();
                    }
                }
            request.setAttribute("contmsg", path);*/
            //DatabaseCleanup dbc=new DatabaseCleanup();
            //dbc.correctHouseholdEnrollmentDate();
            //hhedao.deleteHouseholdsByStateLgaCBO("BAU/BAU/WEI");
            
            //System.err.println("request.getRequestURL() is "+request.getRequestURL());
            //System.err.println("request.getRequestURL() is "+request.getRequestDispatcher(request.getRequestURL().toString()).forward(request, response));
            //String startDate="2017-04-01";
            //String endDate="2017-06-30";
           // System.err.println("Date 1 before date 2: "+appUtil.compareDates("2017-12-31", "2017-03-06"));
            
            //util.processExitedWithoutGraduation(startDate,endDate);
            //hhedao.correctLgaCodesInHouseholdEnrollmentRecords();
            //CompatibilityManager cm=new UpgradeManager();
            //cm.populateHouseholdEnrollmentAndAssessment();
}
