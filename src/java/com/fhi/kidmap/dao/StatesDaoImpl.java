/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;
import java.util.List;
import java.util.ArrayList;
import com.fhi.kidmap.business.States;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

/**
 *
 * @author COMPAQ USER
 */
public class StatesDaoImpl implements StatesDao
{
    private Session session=null;
    Transaction tx;
    DaoUtil util=new DaoUtil();
    public StatesDaoImpl()
    {

    }
    public States getStateByStateCode(String state_code) throws Exception
    {
        List list=new ArrayList();
        States state=null;
        try
        {
           session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            list=session.createQuery("from States state where state.state_code=:stateCode").setString("stateCode",state_code).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException hbe)
        {
            session.close();
            hbe.printStackTrace();
        }
        if(list !=null && list.size()>0)
        {
            state=(States)list.get(0);
        }
        return state;
    }
    public List getState(String state_code)
    {
        List<String> list=new ArrayList<String>();
        try
        {
           session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            list=session.createQuery("from States state where state.state_code=:stateCode").setString("stateCode",state_code).list();
            tx.commit();
            session.close();
        }
        catch (HibernateException hbe)
        {
            session.close();
            hbe.printStackTrace();
        }
        return list;
    }
    public List getStates() throws Exception
    {
        List<String> list=new ArrayList<String>();
        try
        {
             session=HibernateUtil.getSession();
        tx=session.beginTransaction();
        list=session.createQuery("from States state order by state.name").list();
        tx.commit();
        session.close();
        }
        catch (HibernateException hbe)
        {
            session.close();
            hbe.printStackTrace();
        }
        return list;
    }
    public List getStatesFromEnrollment() throws Exception
    {
        List list=new ArrayList();
        try
        {
            session=HibernateUtil.getSession();
            tx=session.beginTransaction();
            list=session.createQuery("select distinct hhe.stateCode from HouseholdEnrollment hhe order by hhe.stateCode").list();
            tx.commit();
            session.close();
        }
        catch (HibernateException hbe)
        {
            session.close();
            hbe.printStackTrace();
        }
       return list;
    }
    public List getStatesAsString() throws Exception
    {
        String stateCode=null;
        States state=null;
        List stateCodeList=getStatesFromEnrollment();
        List stateList=new ArrayList();
        try
        {
            if(stateCodeList !=null)
            {
                for(int i=0; i<stateCodeList.size(); i++)
                {
                    stateCode=(String)stateCodeList.get(i);
                    state=getStateByStateCode(stateCode);
                    if(state !=null)
                    stateList.add(state);
                }
                /*session=HibernateUtil.getSession();
                tx=session.beginTransaction();
                list=session.createQuery("from States state").list();
                //list=session.createQuery(util.getHouseholdQueryPart()+"States state where hhe.stateCode=state.state_code").list();
                tx.commit();
                session.close();*/
            }
        }
        catch (HibernateException hbe)
        {
            session.close();
            hbe.printStackTrace();
        }
        return stateList;
    }
    public void saveState(States state) throws Exception
    {
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.save(state);
            tx.commit();
            session.close();
        }
        catch (HibernateException hbe)
        {
            session.close();
            throw new Exception(hbe);
        }
    }
    public void updateState(States state) throws Exception
    {
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.update(state);
            tx.commit();
            session.close();
        }
        catch (HibernateException hbe)
        {
            session.close();
            hbe.printStackTrace();
        }
    }
    public void deleteState(States state) throws Exception
    {
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            session.delete(state);
            tx.commit();
            session.close();
        }
        catch (HibernateException hbe)
        {
            session.close();
            hbe.printStackTrace();
        }
    }

}
