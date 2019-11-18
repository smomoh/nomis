/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.kidmap.dao;

import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DqaDao
{
    public List getDqaCount(String[] params) throws Exception;
    public List getDqaOvcList(String[] params,int index) throws Exception;
}
