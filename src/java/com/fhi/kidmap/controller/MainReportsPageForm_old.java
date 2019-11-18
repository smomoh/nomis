/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

import com.fhi.kidmap.business.Cbo;
import com.fhi.kidmap.business.State;
import com.fhi.kidmap.business.States;
import com.fhi.kidmap.dao.CboDao;
import com.fhi.kidmap.dao.CboDaoImpl;
import com.fhi.kidmap.dao.StateDao;
import com.fhi.kidmap.dao.StateDaoImpl;
import com.fhi.kidmap.dao.StatesDao;
import com.fhi.kidmap.dao.StatesDaoImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author HP USER
 */
public class MainReportsPageForm_old extends ActionForm {

    public MainReportsPageForm_old() {

    }

    private String cbo;
    private String state;
    private String enrollmentRegisterMonth1;
    private String enrollmentRegisterMonth2;
    private String enrollmentRegisterYear1;
    private String enrollmentRegisterYear2;









    private Collection cboCategories = null;

    public static class CboCategory implements Serializable{
    private String name;
    private String cboCode;
    public CboCategory(){}
    public CboCategory(String cboCode, String name){
    this.cboCode = cboCode;
    this.name = name;
    }
    public String getName() {return name;}
    public void setName(String string) {name = string;}
    public String getCboCode() {return cboCode;}
    public void setCboCode(String code) {cboCode = code;}
    }
    static Collection categories = new ArrayList();
    /*static {

    

         List list = new ArrayList();
                //list.add("Kaduna");
                //list.add("Kano");
               // list.add("Sokoto");

                try{
                CboDao dao = new CboDaoImpl();
                list = dao.getCbos();
                }
                catch(Exception e) {}



         categories.add(new CboCategory("", ""));
         categories.add(new CboCategory("All", "All"));

         for (Object s: list) {
                    //String state = (String)s;
                    Cbo cbo = (Cbo)s;
                    String name = cbo.getCboName();
                    String cboCode = cbo.getCboCode();
             categories.add(new CboCategory(cboCode, name));

         }




    }*/

    public Collection getCboCategories(){return categories;}


    public void setCboCategories(){ }







    private Collection stateCategories = null;

    public static class StateCategory implements Serializable{
    private String name;
    private String stateCode;
    public StateCategory(){}
    public StateCategory(String stateCode, String name){
    this.stateCode = stateCode;
    this.name = name;
    }
    public String getName() {return name;}
    public void setName(String string) {name = string;}
    public String getStateCode() {return stateCode;}
    public void setStateCode(String code) {stateCode = code;}
    }
    static Collection categories2 = new ArrayList();
    static {

    /*
    categories.add(new CboCategory("1", "VP"));
    categories.add(new CboCategory("2", "CTO"));
    categories.add(new CboCategory("3", "Marketing"));
    categories.add(new CboCategory("4", "Developer"));
    categories.add(new CboCategory("5", "Architect"));
    */


         List list = new ArrayList();
                //list.add("Kaduna");
                //list.add("Kano");
               // list.add("Sokoto");

               // try{
               // StateDao dao = new StateDaoImpl();
                //list = dao.getStates();
               // }
                //catch(Exception e) {msg = "problem with states";}



          try{
                StatesDao dao = new StatesDaoImpl();
                list = dao.getStates();
                }
                catch(Exception e) { }



         categories2.add(new StateCategory("", ""));
         categories2.add(new StateCategory("All", "All"));


         /*for (Object s: list) {
                    //String state = (String)s;
                    State state = (State)s;
                    String name = state.getName();
                    String stateCode = state.getStateCode();
             categories2.add(new StateCategory(stateCode, name));

         }*/



         for (Object s: list) {
                    //String state = (String)s;
                    States state = (States)s;
                    String name = state.getName();
                    String stateCode = state.getState_code();
             categories2.add(new StateCategory(stateCode, name));

         }




    }

    public Collection getStateCategories(){return categories2;}


    public void setStateCategories(){ }












    public String getCbo() {
        return cbo;
    }

    public void setCbo(String cbo) {
        this.cbo = cbo;
    }


     public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



    public String getEnrollmentRegisterMonth1() {
        return enrollmentRegisterMonth1;
    }

    public void setEnrollmentRegisterMonth1(String enrollmentRegisterMonth1) {
        this.enrollmentRegisterMonth1 = enrollmentRegisterMonth1;
    }

    public String getEnrollmentRegisterMonth2() {
        return enrollmentRegisterMonth2;
    }

    public void setEnrollmentRegisterMonth2(String enrollmentRegisterMonth2) {
        this.enrollmentRegisterMonth2 = enrollmentRegisterMonth2;
    }

    public String getEnrollmentRegisterYear1() {
        return enrollmentRegisterYear1;
    }

    public void setEnrollmentRegisterYear1(String enrollmentRegisterYear1) {
        this.enrollmentRegisterYear1 = enrollmentRegisterYear1;
    }

    public String getEnrollmentRegisterYear2() {
        return enrollmentRegisterYear2;
    }

    public void setEnrollmentRegisterYear2(String enrollmentRegisterYear2) {
        this.enrollmentRegisterYear2 = enrollmentRegisterYear2;
    }

}
