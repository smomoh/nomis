/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.controller;

/**
 *
 * @author HP USER
 */

import org.apache.struts.action.ActionMapping;

public class OvcActionMapping extends ActionMapping {
protected boolean loginRequired = true;
public OvcActionMapping() {
super();
}
public void setLoginRequired(boolean loginRequired) {
this.loginRequired = loginRequired;
}
public boolean isLoginRequired() {
return loginRequired;
}
}