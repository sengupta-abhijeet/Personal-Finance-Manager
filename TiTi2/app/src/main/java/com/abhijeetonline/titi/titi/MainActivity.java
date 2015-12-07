package com.abhijeetonline.titi.titi;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ActivityNotFoundException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.os.ParcelUuid;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;


public class MainActivity extends Activity implements View.OnClickListener{
    public static Handler mUiHandler = null;

    TextToSpeech mT1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mT1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    mT1.setLanguage(Locale.US);
                }
            }
        });

        LinearLayout layout = (LinearLayout) findViewById(R.id.verticalLayout_MainLayoutButtonHolder);

        Button speakButton = new Button(this);
        speakButton.setText("Speak");
        speakButton.setBackgroundColor(Color.GREEN);
        speakButton.setHeight(200);
        speakButton.setId(10);
        speakButton.setOnClickListener(this);
        layout.addView(speakButton);

        Button CarBTConnectButton = new Button(this);
        CarBTConnectButton.setText("CarBT Connect");
        CarBTConnectButton.setBackgroundColor(Color.CYAN);
        CarBTConnectButton.setHeight(50);
        CarBTConnectButton.setId(20);
        CarBTConnectButton.setOnClickListener(this);
        layout.addView(CarBTConnectButton);

        mUiHandler = new Handler() // Receive messages from service class
        {
            public void handleMessage(Message msg)
            {
                switch(msg.what)
                {
                    case 0:
                        // add the status which came from service and show on GUI
                        Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
                        break;

                    default:
                        break;
                }
            }
        };
    }

    //start the service
    public void onClickStartServie(View V)
    {
        //start the service from here //MyService is your service class name
        startService(new Intent(this, MainBackgroundService.class));
    }
    //Stop the started service
    public void onClickStopService(View V)
    {
        //Stop the running service from here//MyService is your service class name
        //Service will only stop if it is already running.
        stopService(new Intent(this, MainBackgroundService.class));
    }
    //send message to service
    public void onClickGeoMap(View v){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public void onClickSendMessage (View v)
    {
        //only we need a handler to send message to any component.
        //here we will get the handler from the service first, then
        //we will send a message to the service.
        new GcmRegistrationAsyncTask(this).execute();

        if(null != MainBackgroundService.mMyServiceHandler)
        {
            //first build the message and send.
            //put a integer value here and get it from the service handler
            //For Example: lets use 0 (msg.what = 0;) for getting service running status from the service
            Message msg = new Message();
            msg.what = 0;
            msg.obj  = "Add your Extra Meaage Here"; // you can put extra message here
            MainBackgroundService.mMyServiceHandler.sendMessage(msg);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case 10:{

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak, TiTi Is Listening");

                try {
                    startActivityForResult(intent, 100);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),"Speech Not Supported",
                            Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case 20: {
                BluetoothAdapter btAdapter;
                btAdapter = BluetoothAdapter.getDefaultAdapter();
                if (!btAdapter.isEnabled())
                        btAdapter.enable();
                //BluetoothDevice device = btAdapter.getRemoteDevice("50:32:75:C9:08:1E");
                BluetoothDevice device = btAdapter.getRemoteDevice("24:A8:7D:02:CA:3D");
                BluetoothSocket tmp = null;

                BluetoothSocket mmSocket2 = null;
                BluetoothSocket mmSocket3 = null;
                BluetoothSocket mmSocket4 = null;


                try {
                    UUID uid = UUID.fromString("0000111f-0000-1000-8000-00805f9b34fb");
                    tmp = device.createRfcommSocketToServiceRecord(uid);
                } catch (IOException e) { }
                mmSocket2 = tmp;
                btAdapter.cancelDiscovery();
                try {
                    // Connect the device through the socket. This will block
                    // until it succeeds or throws an exception
                    mmSocket2.connect();
                } catch (IOException connectException) {
                    // Unable to connect; close the socket and get out
                    try {
                        mmSocket2.close();
                    } catch (IOException closeException) { }
                    //return;
                }

                try {
                    UUID uid = UUID.fromString("0000110a-0000-1000-8000-00805f9b34fb");
                    tmp = device.createRfcommSocketToServiceRecord(uid);
                } catch (IOException e) { }
                mmSocket3 = tmp;
                btAdapter.cancelDiscovery();
                try {
                    // Connect the device through the socket. This will block
                    // until it succeeds or throws an exception
                    mmSocket3.connect();
                } catch (IOException connectException) {
                    // Unable to connect; close the socket and get out
                    try {
                        mmSocket3.close();
                    } catch (IOException closeException) { }
                    //return;
                }

                try {
                    UUID uid = UUID.fromString("0000110b-0000-1000-8000-00805f9b34fb");
                    tmp = device.createRfcommSocketToServiceRecord(uid);
                } catch (IOException e) { }
                mmSocket4 = tmp;
                btAdapter.cancelDiscovery();
                try {
                    // Connect the device through the socket. This will block
                    // until it succeeds or throws an exception
                    mmSocket4.connect();
                } catch (IOException connectException) {
                    // Unable to connect; close the socket and get out
                    try {
                        mmSocket4.close();
                    } catch (IOException closeException) { }
                    //return;
                }


            }
            break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Toast.makeText(getApplicationContext(),result.get(0),
                            Toast.LENGTH_SHORT).show();
                    String text = result.get(0);
                    executeMatchingVoiceCommand(text);
                }
                break;
            }

        }
    }

    void executeMatchingVoiceCommand(String command){
        if(command.compareTo("switch on Bluetooth")==0 ||
           command.compareTo("start Bluetooth")==0)
        {
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (!mBluetoothAdapter.isEnabled()){
                mBluetoothAdapter.enable();
                mT1.speak("Ok Switching On Bluetooth", TextToSpeech.QUEUE_FLUSH, null);
            }else{
                mT1.speak("Bluetooth is already enabled", TextToSpeech.QUEUE_FLUSH, null);
            }

        }

        if(command.compareTo("switch off Bluetooth")==0 ||
                command.compareTo("stop Bluetooth")==0)
        {
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter.isEnabled()){
                mBluetoothAdapter.disable();
                mT1.speak("Ok Switching Off Bluetooth", TextToSpeech.QUEUE_FLUSH, null);
            }else{
                mT1.speak("Bluetooth is not enabled", TextToSpeech.QUEUE_FLUSH, null);
            }

        }

        if(command.compareTo("start Bluetooth Discovery")==0)
        {
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter.isEnabled()){
                mBluetoothAdapter.startDiscovery();
                mT1.speak("Ok starting discovery", TextToSpeech.QUEUE_FLUSH, null);
            }else{
                mT1.speak("Bluetooth is not enabled", TextToSpeech.QUEUE_FLUSH, null);
            }
        }

        if(command.toLowerCase().compareTo("call tutu")==0)
        {
            Intent in=new Intent(Intent.ACTION_CALL, Uri.parse("tel:9731818245"));
            mT1.speak("calling tutu", TextToSpeech.QUEUE_FLUSH, null);
            try{
                startActivity(in);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
            }catch (android.content.ActivityNotFoundException ex){
                ;
            }


        }

        if(command.toLowerCase().compareTo("traffic")==0) {
            mT1.speak("showing traffic condition", TextToSpeech.QUEUE_FLUSH, null);
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }

    }
}