/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.OperationsManagement;

import com.fhi.kidmap.business.Partners;
import com.fhi.kidmap.dao.PartnersDao;
import com.fhi.kidmap.dao.PartnersDaoImpl;

/**
 *
 * @author smomoh
 */
public class PartnerManager 
{
    public static PartnersDao getPartnersDaoInstance()
    {
        return new PartnersDaoImpl();
    }
    public static Partners getPartner(String partnerCode)
    {
        Partners partner=null;
        try
        {
            partner=getPartnersDaoInstance().getPartner(partnerCode);
            if(partner==null)
            {
                partner=new Partners();
                partner.setPartnerCode(partnerCode);
                partner.setPartnerName(partnerCode);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return partner;
    }
}
