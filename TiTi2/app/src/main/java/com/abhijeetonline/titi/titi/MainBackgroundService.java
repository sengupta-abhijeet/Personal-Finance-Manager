package com.abhijeetonline.titi.titi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainBackgroundService extends Service {
    public MainBackgroundService() {
    }

    private static final String TAG = "MainBackgroundService";

    //used for getting the handler from other class for sending messages
    public static Handler mMyServiceHandler 			= null;
    //used for keep track on Android running status
    public static Boolean 		mIsServiceRunning 			= false;

    DatabaseHandler db;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {

        Toast.makeText(this, "MyService Created", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        MyThread myThread = new MyThread();
        myThread.start();

        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        mIsServiceRunning = true; // set service running status = true

        Toast.makeText(this, "Congrats! My Service Started", Toast.LENGTH_LONG).show();
        // We need to return if we want to handle this service explicitly.

        //notification("TiTi","TiTi@Work",2323,new Intent(getApplicationContext(),MainActivity.class));

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy");

        mIsServiceRunning = false; // make it false, as the service is already destroyed.

        String ns = getApplicationContext().NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
        //nMgr.cancel(2323);
        nMgr.cancel(2324);

    }



    class MyThread extends Thread
    {
        private static final String INNER_TAG = "MyThread";

        class TeleListener extends PhoneStateListener {


            @Override
            public void onCellLocationChanged(CellLocation location){
                super.onCellLocationChanged(location);
                if (location instanceof GsmCellLocation){
                    db = new DatabaseHandler(getApplicationContext());
                    GsmCellLocation gcLoc = (GsmCellLocation) location;
                    String cellid = String.valueOf(gcLoc.getCid()); //Cell ID
                    String lac = String.valueOf(gcLoc.getLac()); //Location Area Code
                    //TextView txtCid = (TextView)findViewById(R.id.cellIdTextView);
                    String cellId = cellid+":"+lac;
                    String locationName = db.getLocation(cellId);
                    //final TiTiGlobal globalVariable = (TiTiGlobal)(getApplicationContext());
                    //globalVariable.setCurrentCellId(cellId);
                    NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
                    if(locationName.isEmpty()) {
                        Intent intent2 = new Intent(getApplicationContext(),LearnLocations.class);
                        intent2.putExtra("cellId", cellId);
                        notificationHelper.createNotification("TiTi@" + "Unknown Location", cellId, 2324,intent2,true,true);
                    }else {
                        notificationHelper.createNotification("TiTi@" + locationName, cellId, 2324,new Intent(getApplicationContext(),MainActivity.class),false,true);
                    }

                }
            }

        }

        public void run()
        {
            this.setName(INNER_TAG);

            // Prepare the looper before creating the handler.
            Looper.prepare();

            TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            TelephonyMgr.listen(new TeleListener(), PhoneStateListener.LISTEN_CELL_LOCATION);




            mMyServiceHandler = new Handler()
            {
                //here we will receive messages from activity(using sendMessage() from activity)
                public void handleMessage(Message msg)
                {
                    Log.i("BackgroundThread","handleMessage(Message msg)" );
                    switch(msg.what)
                    {
                        case 0: // we sent message with what value =0 from the activity. here it is
                            //Reply to the activity from here using same process handle.sendMessage()
                            //So first get the Activity handler then send the message
                            if(null != MainActivity.mUiHandler)
                            {
                                //first build the message and send.
                                //put a integer value here and get it from the Activity handler
                                //For Example: lets use 0 (msg.what = 0;)
                                //for receiving service running status in the activity
                                Message msgToActivity = new Message();
                                msgToActivity.what = 0;
                                if(true ==mIsServiceRunning)
                                    msgToActivity.obj  = "Request Received. Service is Running"; // you can put extra message here
                                else
                                    msgToActivity.obj  = "Request Received. Service is not Running"; // you can put extra message here

                                MainActivity.mUiHandler.sendMessage(msgToActivity);
                            }

                            break;

                        default:
                            break;
                    }
                }
            };
            Looper.loop();
        }
    }
}

