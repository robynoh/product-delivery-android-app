package com.rider.troopadelivery.troopa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.rider.troopadelivery.troopa.R;

import java.util.ArrayList;
import java.util.Calendar;

public class completed_orders extends AppCompatActivity {

    // Declare variables
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    ViewPagerFragmentAdapter adapter;

    // array for tab labels
    private String[] labels = new String[]{"Confirmed", "Declined"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_orders);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Completed Orders");


        Spinner spinner=findViewById(R.id.spinner);
        ArrayList<String> arrayListspinner= new ArrayList<>();
        arrayListspinner.add("All Order");
        arrayListspinner.add("Day");
        arrayListspinner.add("Week");
        arrayListspinner.add("Month");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(completed_orders.this,android.R.layout.simple_spinner_item,arrayListspinner);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);;
                // Toast.makeText(parent.getContext(), "Selected: " + tutorialsName,          Toast.LENGTH_LONG).show();

                if(tutorialsName.equals("Day")){

                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker dialog
                    DatePickerDialog datePickerDialog = new DatePickerDialog(completed_orders.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
//                                    expirydate.setText(dayOfMonth + "/"
//                                            + (monthOfYear + 1) + "/" + year);
//                                    Intent a = new Intent(card_payments.this,daily_card_payment.class);
//                                    a.putExtra("day", String.valueOf(dayOfMonth));
//                                    a.putExtra("month", String.valueOf(monthOfYear));
//                                    a.putExtra("year", String.valueOf(year));
//                                    startActivity(a);
                                    //finish();


                                    // pull_all_cash_payment_daily(riderid, dayOfMonth,monthOfYear,year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();

                }

                if(tutorialsName.equals("Month")){

//                    Intent b = new Intent(card_payments.this,card_monthly_payment.class);
//                    startActivity(b);
//                    // finish();

                    // pull_all_cash_payment_monthly(riderid);

                }

                if(tutorialsName.equals("Week")){

//                    Intent c = new Intent(card_payments.this,card_week_payment.class);
//                    startActivity(c);
//                    //finish();

                }




            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });



        // call function to initialize views
        init();

        // bind and set tabLayout to viewPager2 and set labels for every tab
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            tab.setText(labels[position]);
        }).attach();

        // set default position to 1 instead of default 0
        viewPager2.setCurrentItem(1, false);
    }

    private void init() {
        // initialize tabLayout
        tabLayout = findViewById(R.id.tablayout);
        // initialize viewPager2
        viewPager2 = findViewById(R.id.viewpager);
        // create adapter instance
        adapter = new ViewPagerFragmentAdapter(this);
        // set adapter to viewPager2
        viewPager2.setAdapter(adapter);

        // remove default elevation of actionbar
        getSupportActionBar().setElevation(0);
    }

    // create adapter to attach fragments to viewpager2 using FragmentStateAdapter
    private class ViewPagerFragmentAdapter extends FragmentStateAdapter {

        public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        // return fragments at every position
        @NonNull
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new fragment1(); // calls fragment
                case 1:
                    return new fragment2(); // chats fragment

            }
            return new fragment1(); //chats fragment
        }

        // return total number of tabs in our case we have 3
        @Override
        public int getItemCount() {
            return labels.length;
        }
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}