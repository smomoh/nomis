/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xhrstruts;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class AjaxActionForm extends org.apache.struts.action.ActionForm {
    
    private String name;
    private String completedbyCbo;
    private String qparam;
    String error;
    HttpServletRequest request;

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getQparam() {
        return qparam;
    }

    public void setQparam() {
        this.qparam =getRequest().getParameter("qparam");
    }
    

    public String getCompletedbyCbo()
    {
        return completedbyCbo;
    }

    public void setCompletedbyCbo(String completedbyCbo) {
        this.completedbyCbo = completedbyCbo;
    }

    /**
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param string
     */
    public void setName(String string)
    {
        name = string;
    }

    public AjaxActionForm()
    {
        super();
    }

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public void setError()
    {
        this.error="<span style='color:red'>Please select correct parameter</span>";
    }
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();
        if(getCompletedbyCbo()==null || getCompletedbyCbo().length()<1)
        errors.add("name", new ActionMessage("error.completedbyCbo.required"));
            // TODO: add 'error.name.required' key to your resources
        return errors;
    }
}
