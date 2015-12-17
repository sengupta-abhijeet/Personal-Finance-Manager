package com.abhijeetonline.titi.titi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;


public class XpenserVoucherSubmitConfirmation extends Activity implements View.OnClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xpenser_voucher_submit_confirmation);

        LinearLayout layout = (LinearLayout) findViewById(R.id.vertical_layout_vchConfirmButtonHolder);

        TextView savedTransactionSourceTextView = new TextView(this);
        savedTransactionSourceTextView.setTextColor(Color.RED);
        savedTransactionSourceTextView.setText("Deduct From " + XpenserVoucher.getInstance().getSourceGNUCashCode());
        layout.addView(savedTransactionSourceTextView);
        TextView savedTransactionVchDateTextView = new TextView(this);
        savedTransactionVchDateTextView.setTextColor(Color.RED);
        savedTransactionVchDateTextView.setText("On Date " + XpenserVoucher.getInstance().getVchDate() + " For Transactions: ");
        layout.addView(savedTransactionVchDateTextView);

        //Show All the entries for confirmation
        //iterate throw all the voucher entries and show here
        int entries = XpenserVoucher.getInstance().getVchEnteriesCount();
        for(int i=0; i< entries; i++){

            final TextView savedTransactionTextView = new TextView(this);
            savedTransactionTextView.setTextColor(Color.RED);
            String temp = XpenserVoucher.getInstance().getVchEntryFor(i).get(0)+ " INR: "+
                    XpenserVoucher.getInstance().getVchEntryFor(i).get(1);
            savedTransactionTextView.setText(temp);
            layout.addView(savedTransactionTextView);
        }


        Button confirmTransactionButton = new Button(this);
        confirmTransactionButton.setText("Confirm Transaction");
        confirmTransactionButton.setBackgroundColor(Color.GREEN);
        confirmTransactionButton.setHeight(200);
        confirmTransactionButton.setId(10);
        confirmTransactionButton.setOnClickListener(this);
        layout.addView(confirmTransactionButton);

        Button cancelTransactionButton = new Button(this);
        cancelTransactionButton.setText("Cancel Transaction");
        cancelTransactionButton.setId(20);
        cancelTransactionButton.setBackgroundColor(Color.RED);
        cancelTransactionButton.setHeight(200);
        cancelTransactionButton.setOnClickListener(this);
        layout.addView(cancelTransactionButton);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case 10: { //confirm
                FileWriter f;
                try {
                    String path = "/TiTi";
                    File file = new File(Environment.getExternalStorageDirectory(), path);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    f = new FileWriter(file+"/Transaction.txt",true);

                    //Record the transaction
                    f.write("!Account\n");
                    f.write("N");
                    f.write(XpenserVoucher.getInstance().getSourceGNUCashCode());
                    f.write("\n");
                    f.write("^\n");
                    f.write("!Type:Cash\n");
                    f.write("D");
                    f.write(XpenserVoucher.getInstance().getVchDate());
                    f.write("\n");
                    f.write("MMobile APP Entries\n");
                    //f.write("EToyTrain\n");

                    int entries = XpenserVoucher.getInstance().getVchEnteriesCount();
                    for(int i=0; i< entries; i++){

                        f.write("S");
                        f.write(XpenserVoucher.getInstance().getVchEntryFor(i).get(0));
                        f.write("\n");
                        f.write("$-");
                        f.write(XpenserVoucher.getInstance().getVchEntryFor(i).get(1));
                        f.write("\n");
                        f.write("E");
                        f.write(XpenserVoucher.getInstance().getVchEntryFor(i).get(2));
                        f.write("\n");
                    }
                    f.write("^\n");
                    f.write("\n");
                    f.write("\n");
                    f.flush();
                    f.close();
                } catch (Exception e) {
                    String el = e.getMessage();
                    int i =0;
                    i++;

                }
                break;
            }

            case 20: { //Cancel

            }
            break;
        }

        finish();
        Intent intent = new Intent(getApplicationContext(), XpenserMain.class);
        startActivity(intent);

    }
}
