/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nomis.business;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class FingerPrint implements Serializable
{
    private String id;
    private long uid;
    private String ovcId;
    private byte[] image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getOvcId() {
        return ovcId;
    }

    public void setOvcId(String ovcId) {
        this.ovcId = ovcId;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

}
