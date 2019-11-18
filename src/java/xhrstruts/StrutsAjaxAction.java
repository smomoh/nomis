/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xhrstruts;
import ImportExport.MergeDatabase;
import com.fhi.kidmap.dao.DatabaseImportTrackerDao;
import com.fhi.kidmap.dao.DatabaseImportTrackerDaoImpl;
import com.fhi.nomis.OperationsManagement.BackgroundProcessManager;
import com.fhi.nomis.OperationsManagement.SchoolAttendanceManager;
import com.fhi.nomis.nomisutils.AjaxProcessor;
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.NomisConstant;
import com.fhi.nomis.nomisutils.ResourceManager;
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
public class StrutsAjaxAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String FAILURE="failure";

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
        AjaxActionForm ajaxForm=(AjaxActionForm)form;
        ajaxForm.setRequest(request);
        ajaxForm.setQparam();
        String reqParam=ajaxForm.getQparam();
        String values="";
        
        HttpSession session=request.getSession();
        if(reqParam==null)
        {
            ajaxForm.setError();
            return mapping.findForward(FAILURE);
        }
        String[] reqParamArr=reqParam.split(";");
        AjaxProcessor aproc=new AjaxProcessor();
        
        if(reqParamArr[0].equals("contextPath"))
        {
            String msg=" ";
            
            values="contextPath is "+getServlet().getServletContext().getRealPath("/Resources/dbs");
            System.err.println("contextPath is "+values);
        }
        else if(reqParamArr[0].equals("hsuTrackingDate"))
        {
            String msg=" ";
            //HivRecordsManager hsum=new HivRecordsManager();
            //hsum.updateLastHivTrackingDate();
            //System.err.println("contextPath is "+values);
        }
        else if(reqParamArr[0].equals("checkImportStatus"))
        {
            String msg=" ";
            //values="-";
            if(AppUtility.getCurrentImportFileName()!=null)
            values="Processing "+AppUtility.getCurrentImportFileName()+": "+AppUtility.getCurrentImportRecordName();
        }
        else if(reqParamArr[0].equals("checkProcessedImportFiles"))
        {
            //System.err.println("Inside reqParamArr[0].equals(checkProcessedImportFiles)");
            AppUtility appUtil=new AppUtility();
            String userName=appUtil.getCurrentUser(session);
            //System.err.println("Inside reqParamArr[0].equals(checkProcessedImportFiles) userName is "+userName);
            if(userName !=null)
            {
                String fileStatement="file";
                DatabaseImportTrackerDao ditdao=new DatabaseImportTrackerDaoImpl();
                List list=ditdao.getDatabaseImportTracker(userName, false);
                if(list !=null && !list.isEmpty())
                {
                    int numberOfProcessedFiles=list.size();
                    if(numberOfProcessedFiles >1)
                    fileStatement="files";
                    values="You have "+numberOfProcessedFiles+" import "+fileStatement+" processed";
                    System.err.println("Inside reqParamArr[0].equals(checkProcessedImportFiles) values is "+values);
                }
                BackgroundProcessManager bpm=new BackgroundProcessManager();
                //bpm.executeBackgroundProcesses();
            }
        }
        else if(reqParamArr[0].equals("processImportFiles"))
        {
            String tagName="ceasaSaved";
                    int tagValue=1;
            BackgroundProcessManager bpm=new BackgroundProcessManager();
            if(!bpm.backgroundProcessExecuted(tagName,tagValue))
            {
               SchoolAttendanceManager.moveSchoolAttendanceTrackerRecordsFromCaregiverExpenditureRecord();
            }
            if(ResourceManager.xmlDataImportEnabled())
            {
                System.err.println("XML Data import enabled");
                String sync_DeletedRecords="off";
                if(session.getAttribute("syncRecords") !=null)
                sync_DeletedRecords=(String)session.getAttribute("syncRecords");
                //DatabaseCleanup dbc=new DatabaseCleanup();
                //String msg=dbc.cleanCommunityCodes();
                //System.err.println(msg);
                MergeDatabase mdb=new MergeDatabase();
                mdb.queueAndProcessImportFiles(request, sync_DeletedRecords, NomisConstant.HIV_BIRTHREGUPDATE,AppUtility.importHivOnly);
            }
            else
            System.err.println("XML data import disabled-----");
            
        }
        else if(reqParamArr[0].equals("withdrawalStatus"))
        {
            /*DatabaseCleanup dbc=new DatabaseCleanup();
            String msg=dbc.cleanCommunityCodes();
            System.err.println(msg);*/
            //NomisUpgrade nu=new NomisUpgrade();
            //values=nu.updateWithdrawalStatus(true);
            //DatabaseUtilities dbUtils=new DatabaseUtilities();
            //dbUtils.createBackup();
            //nu.updateHouseholdWithdrawal();
            
            //dbcleanup.setAppropriateWithdrawalType();
            //nu.updateBirthRegistrationStatus(false);
            //nu.updateHivStatus(false);
        }
        
        else if(reqParamArr[0].equals("partofnamestring"))
        {
            values=aproc.getOvcByPartOfNames(reqParamArr[1]);
        }
        else if(reqParamArr[0].equals("searchCaregiverInHousehold"))
        {
            values=aproc.getCaregiversByPartOfNames(reqParamArr[1]);
        }
        else if(reqParamArr[0].equals("searchCaregiverById"))
        {
            values=aproc.getCaregiverInfoById(reqParamArr[1]);
        }
        if(reqParamArr[0].equals("All"))
        {
            if(reqParamArr[1].equals("ward"))
            values=aproc.processAllWards();
            else if(reqParamArr[1].equals("cbo"))
            values=aproc.processAllCbos();
            else if(reqParamArr[1].equals("lga"))
            values=aproc.processAllLgas();
            else if (reqParamArr[1].equals("cbosetup"))
            {
                values = aproc.processCboSetup();
            }
        }
        else if(reqParamArr[0].equals("Reports"))
        {
            String[] params=reqParamArr[1].split(":");
            int len=params.length-1;
            if(params[len].equals("lgaForReports") || params[len].equals("csi_lga") || params[len].equals("sumstat_lga") || params[len].equals("sumstatPerMth_lga"))
            {
                /*Fetch all lgas for the report based on selected state*/
                values = aproc.getLgaFromEnrollment(params[0]);
            }
            else if (params[len].equals("cboForReports") || params[len].equals("sumstat_cbo") || params[len].equals("sumstatPerMth_cbo") || params[len].equals("csi_cbo"))
            {
                /*Fetch all cbos for the report based on selected state*/
                values = aproc.getCboFromEnrollment(params[0], params[1]);
            }
            else if(params[len].equals("wardForReports"))
            {
                /*Fetch all wards for the report based on selected cbo*/
                values = aproc.getWardsFromEnrollment(params[0], params[1]);
            }
            else if(params[len].equals("schools"))
            {
                /*Fetch all lgas for the report based on selected state*/
                values = aproc.getSchoolListFromEnrollment(params[0]);
            }
            else if(params[len].equals("schoolAttendanceList"))
            {
                String[] schAttendaceparams=reqParamArr[1].split(":");
                session.setAttribute("schAttenparams", schAttendaceparams);
            }
            else if(params[len].equals("dqaReport"))
            {
                String[] dqaparams=reqParamArr[1].split(":");
                session.setAttribute("dqaparams", dqaparams);
            }
            else if(params[len].equals("summaryStatistics"))
            {
                String[] summStatisticsparams=reqParamArr[1].split(":");
                session.setAttribute("summaryStatparams", summStatisticsparams);
            }
        }
        else if(reqParamArr[0].equals("EnrollmentReports"))
        {
            String[] params=reqParamArr[1].split(":");
            session.setAttribute("orgparams", params);
        }
        else if(reqParamArr[0].equals("csiReports"))
        {
            String[] params=reqParamArr[1].split(":");
            session.setAttribute("params", params);
        }
        else if(reqParamArr[0].equals("duplicateCheck"))
        {
            if(reqParamArr[2].equals("nameAndAge"))
            {
                //System.err.println("reqParamArr[1] in strutsAjaxAction in duplicate check is "+reqParamArr[1]);
                values = aproc.processOvcByNameAndAge(reqParamArr[1],request);
            }
            else if (reqParamArr[2].equals("nameAndCaregiver"))
            {
                values = aproc.processOvcByNameAndCaregiver(reqParamArr[1],request);
            }
        }
        else
        {
            //System.err.println("reqParamArr[1] "+reqParamArr[1]);
            if (reqParamArr[1].equals("ward"))
            {
                values = aproc.processWard(session,reqParamArr[0]);
            }
            else if (reqParamArr[1].equals("wardDetails"))
            {
                values = aproc.getWardDetails(reqParamArr[0]);
            }
            else if (reqParamArr[1].equals("lga"))
            {
                values = aproc.processLga(reqParamArr[0]);
            }
            else if (reqParamArr[1].equals("lgaDetails"))
            {
                values = aproc.getLgaDetails(reqParamArr[0]);
            }
            else if (reqParamArr[1].equals("cbo"))
            {
                values = aproc.processCbo(reqParamArr[0]);
            }
            else if (reqParamArr[1].equals("cboDetails"))
            {
                values = aproc.getCboDetails(reqParamArr[0]);
            }
            else if (reqParamArr[1].equals("state"))
            {
                values = aproc.processAllStates();
            }
            /*else if (reqParamArr[1].equals("stateDetails"))
            {
                values = aproc.getStateDetails(reqParamArr[0]);
            }*/
            else if (reqParamArr[1].equals("cbosetup"))
            {
                values = aproc.processCboSetup();
            }
            else if (reqParamArr[1].equals("schoolList"))
            {
                values = aproc.processSchools(reqParamArr[0],reqParamArr[2]);
            }
            else if (reqParamArr[1].equals("schoolDetails"))
            {
                values = aproc.getSchoolDetails(reqParamArr[0]);
            }
            
        }
        request.setAttribute("wname", values);
     return mapping.findForward(SUCCESS);
    }
}
