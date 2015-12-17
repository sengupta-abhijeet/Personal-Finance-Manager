package com.abhijeetonline.titi.titi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asengu02 on 12/10/2015.
 */
public class XpenserVoucher {

    private static XpenserVoucher xpenserVchInstance;

    public static void initXpenserVoucher(){
        xpenserVchInstance = new XpenserVoucher();
        xpenserVchInstance.mDestinationAccount = new ArrayList<>();

    }

    public static XpenserVoucher getInstance(){
        return xpenserVchInstance;
    }

    public static void appendNewEntry(String destinationGNUCashCode, Double amount, String description){
        DestinationXpenserAccount tempDestinationXpenserAccount = new DestinationXpenserAccount();
        tempDestinationXpenserAccount.mDestinationGNUCashCode = destinationGNUCashCode;
        tempDestinationXpenserAccount.mAmount = amount;
        tempDestinationXpenserAccount.mDescription = description;
        xpenserVchInstance.mDestinationAccount.add(tempDestinationXpenserAccount);
        xpenserVchInstance.mTotalVchAmount = xpenserVchInstance.mTotalVchAmount+amount;
    }

    private XpenserVoucher()
    {
        //hidden due to singleton
        mTotalVchAmount=0.0;
    }

    private static class DestinationXpenserAccount{
        private String mDestinationGNUCashCode;
        private Double mAmount;
        private String mDescription;
    }

    public static Double getTotalVchAmount(){
        Double temp=0.0;
        if(xpenserVchInstance.mTotalVchAmount!=null) {
            temp = xpenserVchInstance.mTotalVchAmount;
        }
        return temp;
    }

    public static List<String> getVchEntryFor(int i){
        List<String> temp = new ArrayList<>();
        temp.add(xpenserVchInstance.mDestinationAccount.get(i).mDestinationGNUCashCode);
        temp.add(String.valueOf(xpenserVchInstance.mDestinationAccount.get(i).mAmount));
        temp.add(xpenserVchInstance.mDestinationAccount.get(i).mDescription);
        return temp;
    }


    public static int getVchEnteriesCount(){
        return xpenserVchInstance.mDestinationAccount.size();
    }

    private boolean mSplitTransaction;

    public static void setSourceGNUCashCode(String mSourceGNUCashCode) {
        xpenserVchInstance.mSourceGNUCashCode = mSourceGNUCashCode;
    }

    public static void setVchDate(String mVchDate) {
        xpenserVchInstance.mVchDate = mVchDate;
    }

    public static String getSourceGNUCashCode() {
        return xpenserVchInstance.mSourceGNUCashCode;
    }

    public static String getVchDate() {
        return xpenserVchInstance.mVchDate;
    }

    private String  mSourceGNUCashCode;
    private String  mVchDate;
    private Double  mTotalVchAmount;
    private List<DestinationXpenserAccount> mDestinationAccount;

}
