package com.abhijeetonline.titi.titi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by asengu02 on 12/14/2015.
 */
public class BroadcastReceiverOnBoot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, CellLocationChangeBackgroundService.class));
    }
}
