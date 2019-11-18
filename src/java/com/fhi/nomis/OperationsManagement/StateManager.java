/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.OperationsManagement;

import com.fhi.kidmap.dao.StatesDao;
import com.fhi.kidmap.dao.StatesDaoImpl;

/**
 *
 * @author smomoh
 */
public class StateManager 
{
    public static StatesDao getStateDaoInstance()
    {
        return new StatesDaoImpl();
    }
}
