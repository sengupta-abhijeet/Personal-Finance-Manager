package com.abhijeetonline.titi.titi;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class XpenserVoucherAmountEntry extends Activity implements View.OnClickListener  {

    private Calendar calendar;
    private DatePicker datePicker;
    private TextView dateView;
    private int year, month, day;

    String currentTransactionGnuCashCode="None";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xpenser_voucher_amount_entry);

        dateView = (TextView) findViewById(R.id.textView_setDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        Bundle bundle = getIntent().getExtras();
        currentTransactionGnuCashCode = bundle.getString("gnuCashCode");

        final LinearLayout layout = (LinearLayout) findViewById(R.id.verticalLayout_UnCommitedTransactionsHolder);

        //show the current transaction
        final EditText currentAmountTextBox = (EditText)findViewById(R.id.editText_amount);
        final TextView currentTransactionTextView = new TextView(this);
        currentTransactionTextView.setTextColor(Color.RED);
        final TextView tempTextView1 = new TextView(this);
        final TextView tempTextView2 = new TextView(this);
        tempTextView2.setTextColor(Color.RED);
        currentAmountTextBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void afterTextChanged(Editable editable) {

                String tempAmt = (currentAmountTextBox.getText().toString().compareTo("")==0) ? "0.0"
                        : currentAmountTextBox.getText().toString();
                currentTransactionTextView.setText(currentTransactionGnuCashCode+" INR: "+
                        Double.parseDouble(tempAmt));

                //update Total vch Amount

                Double TotalSavedVchAmountPlusCurrent = XpenserVoucher.getInstance().getTotalVchAmount() +
                        Double.parseDouble(tempAmt);

                tempTextView1.setText("--------------------------------------------------------");
                tempTextView2.setText("Total INR: "+String.valueOf(TotalSavedVchAmountPlusCurrent));

            }
        });
        layout.addView(currentTransactionTextView);

        //Show Total vch Amount
        String tempAmt = (currentAmountTextBox.getText().toString().compareTo("")==0) ? "0.0"
                : currentAmountTextBox.getText().toString();
        Double TotalSavedVchAmountPlusCurrent = XpenserVoucher.getInstance().getTotalVchAmount() +
                Double.parseDouble(tempAmt);

        tempTextView1.setText("--------------------------------------------------------");
        tempTextView2.setText("Total INR: "+String.valueOf(TotalSavedVchAmountPlusCurrent));


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

        layout.addView(tempTextView1);
        layout.addView(tempTextView2);



        Button moreButton = new Button(this);
        moreButton.setText("Add More Transaction");
        moreButton.setBackgroundColor(Color.YELLOW);
        moreButton.setHeight(20);
        moreButton.setId(10);
        moreButton.setOnClickListener(this);
        layout.addView(moreButton);


        Button doneButton = new Button(this);
        doneButton.setText("Done");
        doneButton.setId(20);
        doneButton.setBackgroundColor(Color.GREEN);
        doneButton.setHeight(20);
        doneButton.setOnClickListener(this);
        layout.addView(doneButton);


    }


    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
                .show();
    }

    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


    @Override
    public void onClick(View view) {

        EditText amountTextBox = (EditText)findViewById(R.id.editText_amount);
        EditText currTransDescTextView = (EditText)findViewById(R.id.editText_memoDEscription);

        if(amountTextBox.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(), "Amount can't be blank", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        XpenserVoucher.getInstance().appendNewEntry(
                currentTransactionGnuCashCode,
                Double.parseDouble(amountTextBox.getText().toString()),
                currTransDescTextView.getText().toString()
        );

        //Add vch date

        XpenserVoucher.getInstance().setVchDate(dateView.getText().toString());

        switch (view.getId()) {

            case 10: { //Add More
              //Get the instance of voucher and add the current entries.
                Intent intent = new Intent(this, XpenserCategories.class);
                startActivity(intent);
                finish();
            }
            break;

            case 20: { //Done, select source account
                Intent intent = new Intent(this, XpenserSelectSourceAccount.class);
                startActivity(intent);
                finish();
            }
            break;
        }

    }
}
