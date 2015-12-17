package com.abhijeetonline.titi.titi;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;


public class NetworkSignalStrengthSettings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_signal_strength_settings);

        SharedPreferences settings = getSharedPreferences("TiTiPreferences", 0);
        int threshold = settings.getInt("NetworkSignalStrengthThreshold", -90);

        final TextView txv = (TextView)findViewById(R.id.textView_NetworkSignalStrength);
        txv.setText("Set poor network Signal Strength threshold(Default is -90, in this value we may get call drops..). Note:" +
                "more the negative value cell reception is poor." );

        final EditText nwStrenthVal = (EditText)findViewById(R.id.editText_nwSigStrength);
        nwStrenthVal.setText(String.valueOf(threshold));

        Button okieBtn = (Button)findViewById(R.id.button_setNwSgnlStrenth);
        okieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = getSharedPreferences("TiTiPreferences", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("NetworkSignalStrengthThreshold",Integer.parseInt(nwStrenthVal.getText().toString()));
                // Commit the edits!
                editor.commit();
                finish();
            }
        });
    }

}