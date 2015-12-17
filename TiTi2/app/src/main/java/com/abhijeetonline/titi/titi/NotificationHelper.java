package com.abhijeetonline.titi.titi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

/**
 * Created by asengu02 on 12/3/2015.
 */
public class NotificationHelper {

    private Context mContext;

    public NotificationHelper(Context context)
    {
        mContext = context;
    }

    public void createNotification(String title,String content, int id,Intent intent,boolean makeSound,boolean sticky,String soundName){
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext.getApplicationContext(),
                id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext.getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                //.addAction (R.drawable.ic_,getString(R.string.dismiss), piDismiss)
               // .addAction (R.drawable.ic_stat_snooze,getString(R.string.snooze), piSnooze)
                ;
        Notification n;

        n = builder.build();

        int musicResource= R.raw.s2;

        if(soundName.compareTo("serious")==0)
            musicResource = R.raw.serious;

        if(makeSound) {
            n.sound = Uri.parse("android.resource://"
                    + mContext.getApplicationContext().getPackageName() + "/" + musicResource);
        }
        if(sticky) {
            n.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        }

        NotificationManager notificationManager;
        notificationManager =
                (NotificationManager) mContext.getSystemService(mContext.getApplicationContext().NOTIFICATION_SERVICE);


        notificationManager.notify(id, n);
    }

}
