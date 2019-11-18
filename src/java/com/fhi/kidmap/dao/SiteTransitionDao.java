/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.SiteTransition;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface SiteTransitionDao 
{
    public void saveSiteTransition(SiteTransition st) throws Exception;
    public void updateSiteTransition(SiteTransition st) throws Exception;
    public void deleteSiteTransition(SiteTransition st) throws Exception;
    public SiteTransition getSiteTransition(String siteCode) throws Exception;
    public List getAllTransitionedSites() throws Exception;
}
