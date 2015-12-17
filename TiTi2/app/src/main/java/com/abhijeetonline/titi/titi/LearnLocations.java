package com.abhijeetonline.titi.titi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Locale;


public class LearnLocations extends Activity implements View.OnClickListener {

    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_locations);

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });

        LinearLayout layout = (LinearLayout) findViewById(R.id.verticalLayout_LocationButtonHolder);

        Button homeButton = new Button(this);
        homeButton.setText("Home");
        homeButton.setBackgroundColor(Color.GREEN);
        homeButton.setHeight(200);
        homeButton.setId(10);
        homeButton.setOnClickListener(this);
        layout.addView(homeButton);

        Button workButton = new Button(this);
        workButton.setText("Work");
        workButton.setId(20);
        workButton.setBackgroundColor(Color.BLUE);
        workButton.setHeight(200);
        workButton.setOnClickListener(this);
        layout.addView(workButton);
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        DatabaseHandler db;
        Intent intent = getIntent();
        String cellId = intent.getStringExtra("cellId");
        Toast toast;
        String toSpeak = "Started Learning New Location.";
        // t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
       // toast = Toast.makeText(this, "Started Learning New Location!!! "+cellId, Toast.LENGTH_LONG);
       // toast.show();
        db = new DatabaseHandler(getApplicationContext());
        String locationName="Unknown Location";
        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
        switch (v.getId()) {

            case 10:
                locationName = "HOME";
                break;
            case 20:
                locationName = "WORK";
                break;

            default:
                break;
        }
        db.addLocation(cellId,locationName);
        notificationHelper.createNotification("TiTi@" + locationName, cellId, 2324,new Intent(getApplicationContext(),MainActivity2.class),true,true,"s2");

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_learn_locations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
