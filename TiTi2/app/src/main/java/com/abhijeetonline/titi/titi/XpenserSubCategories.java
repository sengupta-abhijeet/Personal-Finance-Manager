package com.abhijeetonline.titi.titi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class XpenserSubCategories extends Activity implements View.OnClickListener {


    List<XpenserSubcategory> listSubCat = null;

    private class XpenserCategory{
        private String mCategory;

        public List<XpenserSubcategory> getSubCategories() {
            return mSubCategory;
        }

        private List<XpenserSubcategory> mSubCategory;

        private XpenserCategory(String mCategory, List<XpenserSubcategory> mSubCategory) {
            this.mCategory = mCategory;
            this.mSubCategory = mSubCategory;
        }
    }

    private class XpenserSubcategory{

        private String mCategory;

        public String getSubCategoryName() {
            return mSubCategory;
        }

        private String mSubCategory;
        private String mGnuCashCode;


        public String getCategoryName() {
            return mCategory;
        }

        public String getGnuCashCode() {
            return mGnuCashCode;
        }

        private XpenserSubcategory(String mCategory, String mSubCategory, String mGnuCashCode) {
            this.mCategory = mCategory;
            this.mSubCategory = mSubCategory;
            this.mGnuCashCode = mGnuCashCode;
        }
    }

    Map< String, XpenserCategory > mCategoryMap = new HashMap< String, XpenserCategory >();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xpenser_sub_categories);

        populateSubcategoryData();



        LinearLayout layout = (LinearLayout) findViewById(R.id.VerticalLayout_SubcategoriesButtonHolder);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                120
        );
        layoutParams.setMargins(0, 10, 0, 10);


        Bundle bundle = getIntent().getExtras();
        String category = bundle.getString("category");
        XpenserCategory xpensercategory = mCategoryMap.get(category);
        listSubCat = xpensercategory.getSubCategories();
        int numberOfEnteries = listSubCat.size();

        Button btn[] = new Button[numberOfEnteries];

        for (int i=0;i<numberOfEnteries;i++) {
            btn[i] = new Button(this);
            btn[i].setTransformationMethod(null);
            btn[i].setBackground(null);
            btn[i].setText(listSubCat.get(i).getSubCategoryName());
            btn[i].setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_xpenser_add_to_cart, 0, 0, 0);
            btn[i].setGravity(Gravity.CENTER | Gravity.LEFT);
            btn[i].setId(i);
            btn[i].setOnClickListener(this);
            layout.addView( btn[i],layoutParams);
        }
    }


    @Override
    public void onClick(View view) {

        Intent intent = new Intent(this,XpenserVoucherAmountEntry.class);
        intent.putExtra("gnuCashCode",listSubCat.get(view.getId()).getGnuCashCode());
        startActivity(intent);
        finish();

    }

    public void populateSubcategoryData() {
        List tempList1 = new ArrayList();
        tempList1.add(new XpenserSubcategory("Food Items","Food Items","Expenses:Food Items"));
        tempList1.add(new XpenserSubcategory("Food Items","cakes","Expenses:Food Items:cakes"));
        tempList1.add(new XpenserSubcategory("Food Items","chocolate","Expenses:Food Items:chocolate"));
        tempList1.add(new XpenserSubcategory("Food Items","coldDrinks","Expenses:Food Items:coldDrinks"));
        tempList1.add(new XpenserSubcategory("Food Items","Icecream","Expenses:Food Items:Icecream"));
        tempList1.add(new XpenserSubcategory("Food Items","Juice","Expenses:Food Items:Juice"));
        tempList1.add(new XpenserSubcategory("Food Items","Light snacks","Expenses:Food Items:Light snacks"));
        tempList1.add(new XpenserSubcategory("Food Items","Sweets","Expenses:Food Items:Sweets"));
        mCategoryMap.put("Food Items", new XpenserCategory("Food Items",tempList1));

        List tempList2 = new ArrayList();
        tempList2.add(new XpenserSubcategory("Grocery","Biscuit","Expenses:Grocery:Biscuit"));
        tempList2.add(new XpenserSubcategory("Grocery","Atta","Expenses:Grocery:Atta"));
        tempList2.add(new XpenserSubcategory("Grocery","Cheese","Expenses:Grocery:Cheese"));
        tempList2.add(new XpenserSubcategory("Grocery","Curd","Expenses:Grocery:Curd"));
        tempList2.add(new XpenserSubcategory("Grocery","Dryfruit","Expenses:Grocery:Dryfruit"));
        tempList2.add(new XpenserSubcategory("Grocery","Ghee","Expenses:Grocery:Ghee"));
        tempList2.add(new XpenserSubcategory("Grocery","Milk","Expenses:Grocery:Milk"));
        tempList2.add(new XpenserSubcategory("Grocery","Nonveg","Expenses:Grocery:Nonveg"));
        tempList2.add(new XpenserSubcategory("Grocery","Oil","Expenses:Grocery:Oil"));
        tempList2.add(new XpenserSubcategory("Grocery","Others","Expenses:Grocery:Others[Grocery"));
        tempList2.add(new XpenserSubcategory("Grocery","Paneer","Expenses:Grocery:Paneer"));
        tempList2.add(new XpenserSubcategory("Grocery","Pulses","Expenses:Grocery:Pulses"));
        tempList2.add(new XpenserSubcategory("Grocery","Rice","Expenses:Grocery:Rice"));
        tempList2.add(new XpenserSubcategory("Grocery","Spices","Expenses:Grocery:Spices"));
        tempList2.add(new XpenserSubcategory("Grocery","Sugar","Expenses:Grocery:Sugar"));
        tempList2.add(new XpenserSubcategory("Grocery","Tea","Expenses:Grocery:Tea"));
        tempList2.add(new XpenserSubcategory("Grocery","Vegetable","Expenses:Grocery:Vegetable"));
        tempList2.add(new XpenserSubcategory("Grocery","Fruits","Expenses:Grocery:Fruits"));
        tempList2.add(new XpenserSubcategory("Grocery", "Bread", "Expenses:Grocery:Bread"));
        mCategoryMap.put("Grocery", new XpenserCategory("Grocery", tempList2));

        List tempList3 = new ArrayList();
        tempList3.add(new XpenserSubcategory("Household Purchase","Household Purchase","Expenses:Household Purchase"));
        tempList3.add(new XpenserSubcategory("Household Purchase","Bath/Washing/Hygiene","Expenses:Household Purchase:Bath/Washing/Hygiene"));
        tempList3.add(new XpenserSubcategory("Household Purchase","DishWash","Expenses:Household Purchase:DishWash"));
        tempList3.add(new XpenserSubcategory("Household Purchase","Purchase[Household]","Expenses:Household Purchase:Purchase[Household]"));
        mCategoryMap.put("Household Purchase", new XpenserCategory("Household Purchase",tempList3));

        List tempList4 = new ArrayList();
        tempList4.add(new XpenserSubcategory("Transportation","Activa Petrol","Expenses:Transportation:Activa:Petrol"));
        tempList4.add(new XpenserSubcategory("Transportation","Celerio Petrol","Expenses:Transportation:Celerio:Petrol"));
        tempList4.add(new XpenserSubcategory("Transportation","Taxi/Auto","Expenses:Transportation:Taxi/Auto"));
        tempList4.add(new XpenserSubcategory("Transportation","TuTu Office Transport","Expenses:Transportation:TuTu Office Transport"));
        mCategoryMap.put("Transportation", new XpenserCategory("Transportation",tempList4));

        List tempList5 = new ArrayList();
        tempList5.add(new XpenserSubcategory("Personal","Tuta Personal","Expenses:TuTa Personal"));
        tempList5.add(new XpenserSubcategory("Personal","Tutu Personal","Expenses:TuTu Personal"));
        mCategoryMap.put("Personal", new XpenserCategory("Personal",tempList5));

        List tempList6 = new ArrayList();
        tempList6.add(new XpenserSubcategory("Others","Others","Expenses:Others"));
        mCategoryMap.put("Others", new XpenserCategory("Others",tempList6));
    }
}
