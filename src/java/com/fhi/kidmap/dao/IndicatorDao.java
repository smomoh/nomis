/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.nomis.business.Indicator;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface IndicatorDao
{
    public void saveIndicator(Indicator indicator) throws Exception;
    public void updateIndicator(Indicator indicator) throws Exception;
    public void deleteIndicator(Indicator indicator) throws Exception;
    public List getAllIndicators() throws Exception;
    public Indicator getIndicator(String indicatorId) throws Exception;
    public Indicator getIndicatorByName(String indicatorName) throws Exception;
}
