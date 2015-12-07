package com.abhijeetonline.titi.titi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;

/**
 * Created by asengu02 on 12/3/2015.
 */
public class NotificationHelper {

    private Context mContext;

    public NotificationHelper(Context context)
    {
        mContext = context;
    }

    public void createNotification(String title,String content, int id,Intent intent,boolean makeSound,boolean sticky){
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext.getApplicationContext(),
                id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(mContext.getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher))
                ;
        Notification n;

        n = builder.build();

        if(makeSound) {
            n.sound = Uri.parse("android.resource://"
                    + mContext.getApplicationContext().getPackageName() + "/" + R.raw.s2);
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
