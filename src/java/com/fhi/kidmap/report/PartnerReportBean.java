/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.report;

import com.fhi.kidmap.business.Lgas;
import com.fhi.kidmap.business.OrganizationRecords;
import com.fhi.kidmap.business.Partners;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.business.Wards;

/**
 *
 * @author smomoh
 */
public class PartnerReportBean 
{
    private Partners partner;
    private States state;
    private Lgas lga;
    private OrganizationRecords organization;
    private Wards community;
    public Lgas getLga() {
        return lga;
    }

    public void setLga(Lgas lga) {
        this.lga = lga;
    }

    public OrganizationRecords getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationRecords organization) {
        this.organization = organization;
    }

    public Partners getPartner() {
        return partner;
    }

    public void setPartner(Partners partner) {
        this.partner = partner;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

    public Wards getCommunity() 
    {
        return community;
    }

    public void setCommunity(Wards community) {
        this.community = community;
    }
        
}
