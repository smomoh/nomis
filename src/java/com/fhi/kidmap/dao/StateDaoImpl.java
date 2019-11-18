/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import com.fhi.kidmap.business.State;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


/**
 *
 * @author HP USER
 */
public class StateDaoImpl implements StateDao {

    private State state = null;
    Session session;
    Transaction tx;
    SessionFactory sessions;

    

    public List getStates() throws Exception {
        List list = new ArrayList();

        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();


        list = session.createQuery("from State").list();

        tx.commit();
        session.close();
        } catch (HibernateException he) {
            throw new Exception(he);
        }
        return list;
    }

    public State getState(String name) throws Exception {
        
        return state;
    }
    public void saveState(String name) throws Exception {

    }
    public void deleteState(String name) throws Exception {

    }

}
