/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nomis.business;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class Indicator  implements Serializable
{
    private String indicatorId;
    private String indicatorName;
    private String query;
    private String description;
    private String programArea;
    private String type;

    public String getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(String indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

   
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProgramArea() {
        return programArea;
    }

    public void setProgramArea(String programArea) {
        this.programArea = programArea;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
