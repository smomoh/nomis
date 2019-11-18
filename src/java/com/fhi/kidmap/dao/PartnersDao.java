/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Partners;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface PartnersDao {
 public void savePartners(Partners p) throws Exception;
 public void updatePartners(Partners p) throws Exception;
 public void deletePartners(Partners p) throws Exception;
 public List getAllPartners() throws Exception;
 public List getPartnerList(String partnercode) throws Exception;
 public Partners getPartner(String partnercode) throws Exception;
}
