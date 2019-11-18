/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nomis.webservice;

import com.fhi.nomis.nomisutils.Message;
import com.fhi.nomis.nomisutils.AccessManager;
import com.google.gson.Gson;
import com.nomis.webservice.pmis.PMISOperation;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author smomoh
 */

@Path("pmisservice/{username}/{pwd}/{metadataRequired}/{startDate}/{endDate}")
public class PmisServiceResource
{
    @Context
    private UriInfo context;

    /** Creates a new instance of PmisServiceResource */
    public PmisServiceResource() 
    {
        
    }

    /**
     * Retrieves representation of an instance of com.nomis.webservice.PmisServiceResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@PathParam("username") String username,@PathParam("pwd") String pwd,@PathParam("metadataRequired") String metadataRequired,@PathParam("startDate") String startDate,@PathParam("endDate") String endDate)
    {
        AccessManager acm=new AccessManager();
        List mainList=new ArrayList();
        
        try
        {
            List loginList=new ArrayList();
            Message nmsg=new Message();
            nmsg.setMessageType("Login");
            nmsg.setMessage("Error: wrong username or password");
            loginList.add(nmsg);
            if(acm.isUserExist(username, pwd))
            {
                PMISOperation pmiso=new PMISOperation();
                mainList=pmiso.getPMISServiceData(metadataRequired,startDate,endDate);
            }
            else
            {
                mainList.add(new ArrayList());
                mainList.add(new ArrayList());
                mainList.add(new ArrayList());
                mainList.add(new ArrayList());
                mainList.add(new ArrayList());
                mainList.add(new ArrayList());               
                mainList.add(loginList);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw new UnsupportedOperationException();
        }
        return new Gson().toJson(mainList,ArrayList.class);
        //TODO return proper representation object
        
    }

    /**
     * PUT method for updating or creating an instance of PmisServiceResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
