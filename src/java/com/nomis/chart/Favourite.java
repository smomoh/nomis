/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.chart;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class Favourite implements Serializable
{
    private String favouriteCode;
    private String favouriteType;
    private String series;
    private String filter;
    private String periodType;
    private String startDate;
    private String endDate;
}
