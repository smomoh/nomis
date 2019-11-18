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
public interface DhisExportDao
{
    public List exportToDhis(String[] params) throws Exception;
    public void createDhisExportFile(String dhisInstance) throws Exception;
    public List exportToExcel(String[] params) throws Exception;
    public void saveOvcDataInDde(String[] params,String dhisInstanceId) throws Exception;
    public List getOvcDataInDde() throws Exception;
}
