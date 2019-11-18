/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.Indicators;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface IndicatorsDao {
    public List getIndicators() throws Exception;
    public List getIndicatorsByDhisInstance(String dhisInstance) throws Exception;
    public List getIndicators(String indicatorId) throws Exception;
    public int getCountOfIndicators() throws Exception;
    public Indicators getIndicator(String dhisId,String dhisInstance) throws Exception;
    public Indicators getIndicatorByName(String indicatorName,String dhisInstance) throws Exception;
    public Indicators getIndicator(int uid) throws Exception;
    public void saveIndicators(Indicators indicator) throws Exception;
    public void updateIndicators(Indicators indicator) throws Exception;
    public void deleteIndicators(Indicators indicator) throws Exception;
    public List getDhisInstances() throws Exception;
}
