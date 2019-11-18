/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.dataexchange.business;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class DhisInstance implements Serializable
{
    
    private String id;
    private String instanceName;
    private String lastModified;

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }
    /*@Override
    public String toString() {
        return getDhisName();
    }
    @Override
    public boolean equals(Object obj)
    {
        if(this==obj)
        return true;
        if((obj==null) || (obj.getClass() !=this.getClass()))
        return false;
        DhisInstance dhis=(DhisInstance)obj;
        return num==dhis.num && (data==dhis.data || (data !=null && data.equals(dhis.data)));
    }
    @Override
    public int hashCode()
    {
        int hash=7;
        hash=31*hash+num;
        hash=31*hash +(null==data?0:data.hashCode());
        return hash;
    }*/
}
