package com.abhijeetonline.titi.titi;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.widget.Toast;

public class MainBackgroundRepeatedService extends Service {
    public MainBackgroundRepeatedService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), "MainBackgroundRepeatedTask.onCreate()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        //Toast.makeText(getApplicationContext(), "MainBackgroundRepeatedTask.onStart()", Toast.LENGTH_SHORT).show();
        //new NotificationHelper(getApplicationContext()).createNotification("TiTi@", "Alarm Manager test",
               // 2325,new Intent(getApplicationContext(),MainActivity2.class),false,false);
        //DO Repeated Task on Specified Interval
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        TelephonyManager telephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        GsmCellLocation location = (GsmCellLocation) telephonyMgr.getCellLocation();

        GsmCellLocation gcLoc = (GsmCellLocation) location;
        String cellid = String.valueOf(gcLoc.getCid()); //Cell ID
        String lac = String.valueOf(gcLoc.getLac()); //Location Area Code
        //TextView txtCid = (TextView)findViewById(R.id.cellIdTextView);
        String cellId = cellid+":"+lac;
        String locationName = db.getLocation(cellId);
        GlobalValuesNStatus.getInstance().currentCellId=cellId;
        GlobalValuesNStatus.getInstance().currentCellLocation=locationName;

        Intent i = new Intent("Background Service Values Updated").putExtra("some_msg", "I will be sent!");
        this.sendBroadcast(i);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "MainBackgroundRepeatedTask.onDestroy()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Toast.makeText(getApplicationContext(), "MainBackgroundRepeatedTask.onBind()", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(getApplicationContext(), "MainBackgroundRepeatedTask.onUnbiind()", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }
}
