/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.DomainServices;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DomainServicesDao
{
    public void saveCbo(DomainServices ds) throws Exception;
    public List getDomainServices() throws Exception;
}
