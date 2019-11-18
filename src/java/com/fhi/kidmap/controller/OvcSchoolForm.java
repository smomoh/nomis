/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author HP USER
 */
public class OvcSchoolForm extends ActionForm{

public OvcSchoolForm() {

}


private int id;
private String name;
private String type;
private String address;
private String lga;
private String state;
private int schoolList;
private String actionName;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }



public int getId() {
return id;
}
public void setId(int id) {
this.id = id;
}

public String getName() {
return name;
}
public void setName(String name) {
this.name = name;
}

public String getType() {
return type;
}
public void setType(String type) {
this.type = type;
}

public String getAddress() {
return address;
}
public void setAddress(String address) {
this.address = address;
}

public String getLga() {
return lga;
}
public void setLga(String lga) {
this.lga = lga;
}

public String getState() {
return state;
}
public void setState(String state) {
this.state = state;
}

    public int getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(int schoolList) {
        this.schoolList = schoolList;
    }



    @Override
public void reset(ActionMapping mapping,
HttpServletRequest request) {

this.id = 0;
this.name =null;
this.type = null;
this.address =null;
this.lga =null;
this.state =null;
schoolList=0;
actionName=null;
}



    @Override
public ActionErrors validate(ActionMapping mapping,
HttpServletRequest request) {
ActionErrors errors = new ActionErrors();
/*OvcActionMapping ovcMapping =
(OvcActionMapping)mapping;
// Does this action require the user to login
if ( ovcMapping.isLoginRequired() ) {
HttpSession session = request.getSession();
if ( session.getAttribute("USER") == null )
{
// return null to force action to handle login
// error
return null;
}*///
if(getActionName()==null || getActionName().equalsIgnoreCase("delete") || getActionName().equalsIgnoreCase("schoolDetails"))
return errors;
else if(this.getState()==null || getState().length()<1)
errors.add("state", new ActionMessage("error.state_code.required"));
else if(getLga()==null || getLga().length()<1)
errors.add("lga",new ActionMessage("error.lga_name.required"));
else if(this.getType()==null || getType().length()<1)
errors.add("schoolType", new ActionMessage("error.schoolType.required"));
else if(this.getName()==null || getName().trim().length()<1)
errors.add("name", new ActionMessage("error.school_name.required"));
else if(this.getName()==null || getName().trim().length()>100)
errors.add("nameLength", new ActionMessage("error.school_name.length"));

//}


return errors;

}

}
