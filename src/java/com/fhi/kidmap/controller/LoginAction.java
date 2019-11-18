/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

/**
 *
 * @author HP USER
 */
import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.nomis.nomisutils.DatabaseUtilities;
import com.fhi.nomis.nomisutils.LoadUpInfo;
import com.fhi.kidmap.business.User;
import com.fhi.kidmap.dao.DaoUtil;
import com.fhi.kidmap.dao.UserDao;
import com.fhi.kidmap.dao.UserDaoImpl;
import com.fhi.nomis.nomisutils.AccessManager;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;


public class LoginAction extends Action {

 String s = "";
UserDao dao = new UserDaoImpl();
protected String getUser(String username, String password)
{
User user = null;

       try
       {
            
            user = dao.getUser(username);
            if(user !=null)
            {
                if( user.getUserroleId() !=null && user.getUserroleId().equalsIgnoreCase("Administrator"))
                {
                    user.setUserroleId("superuser");
                    user.setChangePwd("yes");
                    user.setViewClientDetails("yes");
                    dao.updateUser(user);
                }
            }
        }
         catch(Exception e)
        {
            s += e.getMessage();
        }

        if(user != null)
        {
         s += "user exists ";

           if(user.getPassword().equals(password))
           {

               s += "yes password";

               return user.getUsername()+":"+user.getUserroleId()+":"+user.getFirstName()+":"+user.getLastName()+":"+user.getAssignedGroupId();
           }
           else
           {


           }

        }

return null;
}

    @Override
public ActionForward execute(ActionMapping mapping,
ActionForm form,
HttpServletRequest request,
HttpServletResponse response)
throws IOException, ServletException {
String user = null;
AppUtility appUtil=new AppUtility();
// Default target to success
String target = "success";
ActionErrors errors = new ActionErrors();
// Use the LoginForm to get the request parameters
String username = ((LoginForm)form).getUsername();
String password = ((LoginForm)form).getPassword();
if(username !=null)
username=username.trim();

DatabaseUtilities dbUtils=new DatabaseUtilities();
//dbUtils.createNomisUserTable();
//dbUtils.addPartnerCodeToNomisUserTable();
//AppUtility appUtil=new AppUtility();
String dbsource=getServlet().getServletContext().getRealPath("/Resources/dbs");
String confSourceDirectory=getServlet().getServletContext().getRealPath("/Resources/conf");
String seperator="/";
if(dbsource !=null && dbsource.indexOf("\\") !=-1)
seperator="\\";
appUtil.setFileSeperator(seperator);
//set default database directory
String defaultDbLocationPath="C:";

File defaultDbLocation=new File(defaultDbLocationPath);
//check if it is not a Windows computer
if(!defaultDbLocation.exists())
{
    //get the root directory for the context path
    defaultDbLocationPath=dbsource.substring(0,dbsource.indexOf(seperator));
}
File rootFolder=new File(defaultDbLocationPath);
if(!rootFolder.canWrite())
{
    if(appUtil.getUserHomeDirectory() !=null)
    defaultDbLocationPath=appUtil.getUserHomeDirectory();
}
System.err.println("defaultDbLocationPath is "+defaultDbLocationPath);
//set the default location for the Nomis folder
appUtil.setResourceDirectory(defaultDbLocationPath);
//check for the hibernate config file
//AppUtility.getResourceLocation()+seperator+
File hibernateFile=new File(AppUtility.getHibernateFile());
String destination=appUtil.getDatabaseDirectory();
String confDestinationDirectory=AppUtility.getConfigurationDirectory();
if(!hibernateFile.exists())
{
    appUtil.createDatabase(dbsource, destination);
    if(!appUtil.loginConfigFileExists())
    appUtil.copyAndPasteDbFiles(confSourceDirectory, confDestinationDirectory);
    
    int created=appUtil.createHibernateFile();
    if(created==2)
    {
        List list=AppUtility.getHibernateConnectionDetails();
        DatabaseUtilities.setConnectionParameters(list);
        dbUtils.runDatabaseUpdateForVersion21();
        System.err.println("Hibernate file created");
    }
    if(created==1)
    {
        System.err.println("Hibernate file not created but directory created");
    }
    if(created==0)
    {
        System.err.println("Hibernate directory not created");
    }
}
else
{
    List list=AppUtility.getHibernateConnectionDetails();
    DatabaseUtilities.setConnectionParameters(list);
    dbUtils.runDatabaseUpdateForVersion21();
}



AppUtility.request=request;
appUtil.createExportImportDirectories();
appUtil.createLogDirectory();

String userParam = getUser(username, password);

String userRole=null;
String userRealNames=null;
String userStates=null;
if(userParam !=null)
{
    String[] userParams=userParam.split(":");
    user=userParams[0];
    userRole=userParams[1].trim();
    if(userRole.equalsIgnoreCase("default"))
    { 
        if(appUtil.isDefaultAccountEnabled())
        {
            HttpSession session=request.getSession();
            LoadUpInfo loadup=new LoadUpInfo();
            loadup.getAllUsers(session);
            loadup.setParamAttributes(request);
            return (mapping.findForward("createaccount"));
        }
        else
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE,
            new ActionMessage("errors.login.unknown",username));
            saveErrors(request, errors);
            return (mapping.findForward("login"));
        }
    }
    userRealNames=userParams[2]+" "+userParams[3];
    userStates=userParams[4];
}

HttpSession session = request.getSession();
//Enable users to setup metadata
AccessManager.enableMetaDataAccess(session);

// Set the target to failure
if ( user == null )
{
    
target = "login";

errors.add(ActionErrors.GLOBAL_MESSAGE,
new ActionMessage("errors.login.unknown",
username));

// Report any errors we have discovered back to the
// original form
    if (!errors.isEmpty())
    {
        saveErrors(request, errors);
    }
}
else
{
    LoadUpInfo loadup=new LoadUpInfo();
    loadup.setLoginInfo(session,user,userRole,userRealNames,userStates);
    loadup.setParamAttributes(request);
    DaoUtil util=new DaoUtil();
    if(!util.isCurrentAgeUnitCorrectlySet())
    {
        util.setCurrentAgeUnit();
    }
}

request.setAttribute("ADDRESS", s);
// Forward to the appropriate View
return (mapping.findForward(target));
}
}

