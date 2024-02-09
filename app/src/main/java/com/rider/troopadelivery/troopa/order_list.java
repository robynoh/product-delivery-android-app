package com.rider.troopadelivery.troopa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rider.troopadelivery.troopa.R;

public class order_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Order");
    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}