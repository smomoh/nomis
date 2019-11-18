/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.nomis.nomisutils;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class ButtonStateBean implements Serializable
{
    private boolean saveBtnDisabled;
    private boolean modifyBtnDisabled;

    public boolean isModifyBtnDisabled() {
        return modifyBtnDisabled;
    }

    public void setModifyBtnDisabled(boolean modifyBtnDisabled) {
        this.modifyBtnDisabled = modifyBtnDisabled;
    }

    public boolean isSaveBtnDisabled() {
        return saveBtnDisabled;
    }

    public void setSaveBtnDisabled(boolean saveBtnDisabled) {
        this.saveBtnDisabled = saveBtnDisabled;
    }

}
