package com.rider.troopadelivery.troopa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.rider.troopadelivery.troopa.R;

public class rider_account extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_account);

        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Account");


        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);

        String account_name = sharedPreferences.getString("account_name", "");
        String bank_name = sharedPreferences.getString("bank_name", "");
        String account_number = sharedPreferences.getString("account_number", "");


        TextView bankname= findViewById(R.id.bank_name);


        EditText bankName= findViewById(R.id.bankName);
        EditText accountName= findViewById(R.id.accountName);
        EditText accountNumber= findViewById(R.id.accountNumber);

        bankName.setText(bank_name);
        accountName.setText(account_name);
        accountNumber.setText(account_number);

        bankname.setText(bank_name);

    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}