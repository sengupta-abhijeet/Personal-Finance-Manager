package com.abhijeetonline.titi.titi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;


public class XpenserMain extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xpenser_main);

        LinearLayout layout = (LinearLayout) findViewById(R.id.verticalLayout_XpenserMainButtonHolder);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                120
        );
        layoutParams.setMargins(0, 10, 0, 10);

        Button AddTransactionButton = new Button(this);
        AddTransactionButton.setTransformationMethod(null);
        AddTransactionButton.setBackground(null);
        AddTransactionButton.setText("Add Transaction");
        AddTransactionButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_xpenser_new_transaction, 0, 0, 0);
        AddTransactionButton.setGravity(Gravity.CENTER | Gravity.LEFT);
        //XpenserButton.setBackgroundColor(Color.WHITE);
        //XpenserButton.setHeight(250);
        AddTransactionButton.setId(10);
        AddTransactionButton.setOnClickListener(this);
        layout.addView(AddTransactionButton);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        String tempText = "Upload Transaction";
        Button UploadTransactionButton = new Button(this);
        if (connectivityManager.getActiveNetworkInfo()!=null) {
            if (!connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()) {
                tempText = tempText + "(Offline No Network)";
                UploadTransactionButton.setEnabled(false);
            }
        }else {
            tempText = tempText + "(Offline No Network)";
            UploadTransactionButton.setText(tempText);
            UploadTransactionButton.setEnabled(false);
        }
        //Check if file is there for upload
        String path = "/TiTi/Transaction.txt";
        File file = new File(Environment.getExternalStorageDirectory(), path);
        if(!file.exists()){
            tempText = tempText + "(No Saved Transactions)";
            UploadTransactionButton.setText(tempText);
            UploadTransactionButton.setEnabled(false);
        }else {
            Format formatter = new SimpleDateFormat("dd-MMMM yyyy");
            tempText = tempText + "(Last Entry: " + formatter.format(file.lastModified()) + ")";
            UploadTransactionButton.setText(tempText);
        }
        UploadTransactionButton.setTransformationMethod(null);
        UploadTransactionButton.setBackground(null);

        UploadTransactionButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_xpenser_upload_transactions, 0, 0, 0);
        UploadTransactionButton.setGravity(Gravity.CENTER | Gravity.LEFT);
        UploadTransactionButton.setId(20);
        UploadTransactionButton.setOnClickListener(this);
        layout.addView(UploadTransactionButton);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case 10: {
                Intent intent = new Intent(this, XpenserCategories.class);
                //start new instance of xpenserVoucher
                XpenserVoucher.initXpenserVoucher();
                startActivity(intent);
            }
            break;

            case 20: {
                Intent intent = new Intent(this, XpenserUploadToCloud.class);
                //start new instance of xpenserVoucher
                XpenserVoucher.initXpenserVoucher();
                startActivity(intent);
            }
            break;
        }
        finish();
    }
}
