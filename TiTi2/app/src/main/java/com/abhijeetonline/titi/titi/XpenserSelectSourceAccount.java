package com.abhijeetonline.titi.titi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class XpenserSelectSourceAccount extends Activity implements View.OnClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xpenser_select_source_account);

        Double  amtDeductable = XpenserVoucher.getInstance().getTotalVchAmount();

        EditText amountDeductableEditTextView = (EditText)findViewById(R.id.editText_DedutableAmmount);
        amountDeductableEditTextView.setText(String.valueOf(amtDeductable));
        amountDeductableEditTextView.setTextSize(20);
        amountDeductableEditTextView.setEnabled(false);

        LinearLayout layout = (LinearLayout) findViewById(R.id.vertical_layout_xpenserSelectSourceButtonHolder);

        //iterate throw all the voucher entries and show here
        int entries = XpenserVoucher.getInstance().getVchEnteriesCount();
        for(int i=0; i< entries; i++){

            XpenserVoucher.getInstance().getVchEntryFor(i).get(0);
            final TextView savedTransactionTextView = new TextView(this);
            String temp = XpenserVoucher.getInstance().getVchEntryFor(i).get(0)+ " INR: "+
                    XpenserVoucher.getInstance().getVchEntryFor(i).get(1);
            savedTransactionTextView.setText(temp);
            layout.addView(savedTransactionTextView);
        }


        Button tutuCashButton = new Button(this);
        tutuCashButton.setText("TuTu Cash");
        tutuCashButton.setBackgroundColor(Color.GREEN);
        tutuCashButton.setHeight(200);
        tutuCashButton.setId(10);
        tutuCashButton.setOnClickListener(this);
        layout.addView(tutuCashButton);

        Button tutaCashButton = new Button(this);
        tutaCashButton.setText("TuTa Cash");
        tutaCashButton.setId(20);
        tutaCashButton.setBackgroundColor(Color.CYAN);
        tutaCashButton.setHeight(200);
        tutaCashButton.setOnClickListener(this);
        layout.addView(tutaCashButton);

        Button tutuSodexoButton = new Button(this);
        tutuSodexoButton.setText("TuTu Sodexo");
        tutuSodexoButton.setId(30);
        tutuSodexoButton.setBackgroundColor(Color.MAGENTA);
        tutuSodexoButton.setHeight(200);
        tutuSodexoButton.setOnClickListener(this);
        layout.addView(tutuSodexoButton);



    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case 10: { //tutu Cash
                XpenserVoucher.getInstance().setSourceGNUCashCode("Assets:Current Assets:TuTu Cash");
            }
            break;

            case 20: { //tuta cash
                XpenserVoucher.getInstance().setSourceGNUCashCode("Assets:Current Assets:TuTa Cash");
            }
            break;

            case 30: { //tutu sodexo
                XpenserVoucher.getInstance().setSourceGNUCashCode("Assets:Current Assets:TuTu Sodexo");
            }
            break;
        }

        Intent intent =new Intent(this,XpenserVoucherSubmitConfirmation.class);
        startActivity(intent);
        finish();
    }
}
