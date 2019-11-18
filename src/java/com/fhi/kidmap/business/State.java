/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.business;

import java.io.Serializable;

/**
 *
 * @author HP USER
 */
public class State implements Serializable {

    public State() {

    }


    private String name;
    private String description;
    private String symbol;


public String getName() {
return name;
}
public void setName(String name) {
this.name = name;
}

public String getDescription() {
return description;
}
public void setDescription(String description) {
this.description = description;
}

public String getSymbol() {
return symbol;
}
public void setsymbol(String symbol) {
this.symbol = symbol;
}


}
