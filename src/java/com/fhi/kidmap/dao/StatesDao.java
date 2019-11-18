/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;
import java.util.*;
import com.fhi.kidmap.business.States;

/**
 *
 * @author COMPAQ USER
 */
public interface StatesDao
{
    public List getState(String state_code) throws Exception;
    public List getStates() throws Exception;
    public List getStatesAsString() throws Exception;
    public void saveState(States state) throws Exception;
    public void updateState(States state) throws Exception;
    public void deleteState(States state) throws Exception;
    public List getStatesFromEnrollment() throws Exception;
    public States getStateByStateCode(String state_code) throws Exception;
}
