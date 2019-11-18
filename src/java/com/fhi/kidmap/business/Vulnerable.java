/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.business;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public interface Vulnerable extends Serializable
{
    public String getAddress();
    public void setAddress(String address);
    public String getAge();
    public void setAge(String age);
    public String getAgeUnit();
    public void setAgeUnit(String ageUnit);
}
