/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nomis.test;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;

/**
 *
 * @author smomoh
 */
public class RTest 
{
    public void testR()
    {
        String[] args={"--vanilla"};
        Rengine re=new Rengine(args,false, new TextConsole());
        if (!re.waitForR()) 
         {
             System.out.println("Cannot load R");
             //System.exit(1);
         }
        else
        {
         REXP result = re.eval("mean(1:6)");
         System.out.println("rexp: " + result.asDouble());
        }
    }
}
