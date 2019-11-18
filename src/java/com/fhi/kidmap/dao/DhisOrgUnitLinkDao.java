/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.DhisOrgUnitLink;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DhisOrgUnitLinkDao
{
    public void saveDhisOrgUnitLink(DhisOrgUnitLink dhisLink) throws Exception;
    public void updateDhisOrgUnitLink(DhisOrgUnitLink dhisLink) throws Exception;
    public void deleteDhisOrgUnitLink(DhisOrgUnitLink dhisLink) throws Exception;
    public DhisOrgUnitLink getDhisOrgUnitLink(String orgCode) throws Exception;
    public List getAllDhisOrgUnitLinks() throws Exception;
}
