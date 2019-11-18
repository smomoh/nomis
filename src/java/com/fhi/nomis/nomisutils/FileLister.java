/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhi.nomis.nomisutils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class FileLister implements Serializable
{
    List fileList=new ArrayList();
    public List getFiles(String fileRoot)
    {
        String[] files=null;
        File file=new File(fileRoot);
        File file2;
        files=file.list();
        for(int i=0; i<files.length; i++)
        {
           file2 =new File(fileRoot+"\\"+files[i]);
           if(file2.isDirectory())
           {
               getFiles(file2.getAbsolutePath());
           }
           else
           {
               fileList.add(file2);
           }
        }
        return fileList;
    }
}
