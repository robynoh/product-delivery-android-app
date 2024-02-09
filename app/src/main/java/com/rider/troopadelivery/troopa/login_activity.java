package com.rider.troopadelivery.troopa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.rider.troopadelivery.troopa.R;

public class login_activity extends AppCompatActivity {

    Button signin;
    LinearLayout register;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity(); // or finish();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }



        signin = (Button) findViewById(R.id.signin_action);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login_activity.this,easy_login.class);
                startActivity(i);
            }
        });
        register = findViewById(R.id.register_new_account);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login_activity.this,contact_activity.class);
                startActivity(i);
            }
        });

    }

    protected void onStart() {

        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String phone = sharedPreferences.getString("phone","");
       //63 Toast.makeText(login_activity.this, phone, Toast.LENGTH_LONG).show();

        if (!phone.equals("")) {
            Intent i = new Intent(login_activity.this, introMap.class);
           startActivity(i);
       }
        super.onStart();
    }
}