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
public class TaskManager implements Serializable
{
    private static boolean dataMartAvailable=true;
    private static boolean dataUploadAvailable=true;
    private static boolean databaseLocked=false;
    private static boolean hivUpdateInProgress=false;
    private static boolean withdrawalStatusUpdateActive=false;
    private String statusMessage="System busy. Kindly try again later";
    public static void setDataUploadAvailable(boolean available)
    {
        dataUploadAvailable=available;
    }
    public static boolean isDataUploadAvailable()
    {
        return dataUploadAvailable;
    }
    public static void setDataMartAvailable(boolean available)
    {
        dataMartAvailable=available;
    }
    public static boolean isDataMartAvailable()
    {
        return dataMartAvailable;
    }

    public static boolean isDatabaseLocked() {
        return databaseLocked;
    }

    public static void setDatabaseLocked(boolean databaseLocked) {
        TaskManager.databaseLocked = databaseLocked;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public static boolean isWithdrawalStatusUpdateActive() {
        return withdrawalStatusUpdateActive;
    }

    public static void setWithdrawalStatusUpdateActive(boolean withdrawalStatusUpdateActive) {
        TaskManager.withdrawalStatusUpdateActive = withdrawalStatusUpdateActive;
    }

    public static boolean isHivUpdateInProgress() {
        return hivUpdateInProgress;
    }

    public static void setHivUpdateInProgress(boolean hivUpdateInProgress) {
        TaskManager.hivUpdateInProgress = hivUpdateInProgress;
    }
    
}
