/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.State;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP USER
 */
public interface StateDao {

    public List getStates() throws Exception;
    public State getState(String name) throws Exception;
    public void saveState(String name) throws Exception;
    public void deleteState(String name) throws Exception;

}
