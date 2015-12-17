package com.abhijeetonline.titi.titi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Random;


public class XpenserCategories extends Activity implements View.OnClickListener {

    String categoryName[] = {"Food Items","Grocery","Transportation","Personal","Household Purchase","Others"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xpenser_categories);


        LinearLayout layout = (LinearLayout) findViewById(R.id.verticalLayout_XpenserCategoriesButtonHolder);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                120
                );
        layoutParams.setMargins(0, 10, 0, 10);


        Button btn[] = new Button[categoryName.length];

        for (int i=0;i<categoryName.length;i++) { //disable others *****
            btn[i] = new Button(this);
            btn[i].setTransformationMethod(null);
            btn[i].setBackground(null);
            btn[i].setText(categoryName[i]);
            if(i==0)
                btn[i].setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_xpenser_food_item, 0, 0, 0);
            if(i==1)
                btn[i].setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_xpenser_groceries, 0, 0, 0);
            if(i==2)
                btn[i].setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_xpenser_transportaion, 0, 0, 0);
            if(i==3)
                btn[i].setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_xpenser_personal, 0, 0, 0);
            if(i==4)
                btn[i].setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_xpenser_household_purchase, 0, 0, 0);
            if(i==5)
                btn[i].setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_xpenser_other_tempentries, 0, 0, 0);
            btn[i].setGravity(Gravity.CENTER | Gravity.LEFT);
           // Random color = new Random();
           // btn[i].setBackgroundColor(Color.argb(255, color.nextInt(255), color.nextInt(255), color.nextInt(255)));
            btn[i].setId(i);
            btn[i].setOnClickListener(this);
            layout.addView( btn[i],layoutParams);
        }

    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,XpenserSubCategories.class);
        intent.putExtra("category", categoryName[view.getId()]);
        startActivity(intent);
        finish();
    }
}
