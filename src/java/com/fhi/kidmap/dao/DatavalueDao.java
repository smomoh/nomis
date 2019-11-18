/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.nomis.business.Datavalue;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DatavalueDao
{
    public void saveDatavalue(Datavalue dv) throws Exception;
    public void updateDatavalue(Datavalue dv) throws Exception;
    public void deleteDatavalue(Datavalue dv) throws Exception;
    public List getDatavalueList() throws Exception;
    public List getDatavalues(String startDate, String endDate) throws Exception;
    public Datavalue getDatavalue(String orgUnitId, String indicatorId,String categoryCombinationId,String startDate) throws Exception;
}
