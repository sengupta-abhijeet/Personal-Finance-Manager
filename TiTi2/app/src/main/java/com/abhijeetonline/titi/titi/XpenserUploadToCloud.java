package com.abhijeetonline.titi.titi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.ProgressListener;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session;
import com.dropbox.client2.session.TokenPair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class XpenserUploadToCloud extends Activity {

    final static public String ACCESS_KEY = "jwuctvd09muhitn";
    final static public String ACCESS_SECRET = "tjfnua9dqyygs64";
    final static public String DROPBOX_NAME = "TiTiDropbox_prefs";
    private boolean isLoggedIn=false;

    final static public Session.AccessType ACCESS_TYPE = Session.AccessType.DROPBOX;

    private DropboxAPI<AndroidAuthSession> dropbox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xpenser_upload_to_cloud);

        LinearLayout layout = (LinearLayout) findViewById(R.id.verticalLayout_XpenserUploadToCloudButtonHolder);

        AndroidAuthSession session = buildSession();
        dropbox = new DropboxAPI(session);
        if(!isLoggedIn)
            dropbox.getSession().startAuthentication(XpenserUploadToCloud.this);
    }

    private AndroidAuthSession buildSession() {

        AppKeyPair pair = new AppKeyPair(ACCESS_KEY, ACCESS_SECRET);
        AndroidAuthSession session;

        SharedPreferences prefs = getSharedPreferences(DROPBOX_NAME, 0);
        String key = prefs.getString(ACCESS_KEY, null);
        String secret = prefs.getString(ACCESS_SECRET, null);

        if (key != null && secret != null) {
            AccessTokenPair token = new AccessTokenPair(key, secret);
            session = new AndroidAuthSession(pair, ACCESS_TYPE.DROPBOX, token);
            loggedIn(true);
        } else {
            session = new AndroidAuthSession(pair, ACCESS_TYPE.DROPBOX);
        }

        return session;
    }


    protected void onActivityResult() {
        dropbox.getSession().startAuthentication(XpenserUploadToCloud.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AndroidAuthSession session = dropbox.getSession();
        if (session.authenticationSuccessful()) {
            try {
                session.finishAuthentication();

                TokenPair tokens = session.getAccessTokenPair();
                SharedPreferences prefs = getSharedPreferences(DROPBOX_NAME, 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(ACCESS_KEY, tokens.key);
                editor.putString(ACCESS_SECRET, tokens.secret);
                editor.commit();
                loggedIn(true);
            } catch (IllegalStateException e) {
                Toast.makeText(this, "Error during Dropbox authentication",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loggedIn(boolean isLogged) {
        UploadFileToDropbox upload = new UploadFileToDropbox();
        upload.execute();
        isLoggedIn = isLogged;
    }

    private class UploadFileToDropbox extends AsyncTask<String, Integer, Boolean> {

        int progress = 0;


        @Override
        protected void onPreExecute() {
            progress = 0;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Intent intent = new Intent(getApplicationContext(), XpenserMain.class);
            startActivity(intent);
        }

        protected void onProgressUpdate(Integer... values) {
            ((TextView) findViewById(R.id.textView_XpenserUploadToCloud)).setText("Uploading Progress%: " + values[0]);
        }



        @Override
        protected Boolean doInBackground(String... values) {

            String path = "/TiTi/Transaction.txt";
            File file = new File(Environment.getExternalStorageDirectory(), path);
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //DropboxAPI.Entry response = null;
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmm");
            String currentDateandTime = sdf.format(new Date());
            String remoteFileName = "/transaction_"+currentDateandTime+".qif";
            NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
            try {

                dropbox.putFile(remoteFileName, inputStream,
                        file.length(), null, new ProgressListener() {
                            @Override
                            public void onProgress(long arg0, long arg1) {
                                int totalSize=(int) arg1;
                                int uploadedSize=(int) arg0;
                                int tempProgress = (uploadedSize/totalSize)*100;
                                publishProgress(tempProgress);
                            }
                        });
            } catch (DropboxException e) {
                e.printStackTrace();
                notificationHelper.createNotification("TiTi","Upload Expense Transaction Failed "+e.getMessage() ,3826836,new Intent(getApplicationContext(),XpenserMain.class),true,false,"s2");
                return null;
            }
            publishProgress(100);
            //rename transaction file with today's date stamp
            File to = new File(Environment.getExternalStorageDirectory(),"/TiTi/"+remoteFileName);
            file.renameTo(to);
            notificationHelper.createNotification("TiTi","Upload Expense Transaction Successful file: "+ remoteFileName ,3826836,new Intent(getApplicationContext(),XpenserMain.class),true,false,"s2");

            return null;
        }
    }


}
