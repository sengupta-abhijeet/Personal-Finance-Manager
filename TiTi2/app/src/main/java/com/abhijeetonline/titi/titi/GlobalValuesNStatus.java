package com.abhijeetonline.titi.titi;

/**
 * Created by asengu02 on 12/14/2015.
 */
public class GlobalValuesNStatus {

    private static GlobalValuesNStatus mInstance= null;

    public String currentCellLocation;
    public String currentCellId;
    public int    muteSignalStrengthAlertForNTimes=0; //it will alert then count for 5 time event changes. Then reset.

    protected GlobalValuesNStatus(){}

    public static synchronized GlobalValuesNStatus getInstance(){
        if(null == mInstance){
            mInstance = new GlobalValuesNStatus();
        }
        return mInstance;
    }
}

