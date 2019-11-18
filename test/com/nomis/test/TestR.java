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
public class TestR 
{
  
 public static void main(String args[]) {
 if (!Rengine.versionCheck()) {
 System.err.println("Java version mismatch.");
 System.exit(1);
 }
 String my[] = { "--vanilla" };
 Rengine re=new Rengine(my,false,new TextConsole());
 if (!re.waitForR()) {
 System.out.println("Cannot load R");
 System.exit(1);
 }
 REXP result = re.eval("mean(1:6)");
 System.out.println("rexp: " + result.asDouble());
 }
}
