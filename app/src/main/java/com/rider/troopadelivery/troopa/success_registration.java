package com.rider.troopadelivery.troopa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rider.troopadelivery.troopa.R;

public class success_registration extends AppCompatActivity {
    Button open_first_map;



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //finishAffinity(); // or finish();

        Intent i = new Intent(success_registration.this,phone_login_activity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_registration);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

      open_first_map=findViewById(R.id.open_first_map);
       open_first_map.setOnClickListener(new View.OnClickListener() {
           @Override
          public void onClick(View view) {
             Intent i = new Intent(success_registration.this,phone_login_activity.class);
            startActivity(i);
         }});
      // new Handler().postDelayed(new Runnable() {
       //     @Override
        //   public void run() {
        //       Intent i = new Intent(success_registration.this,first_map.class);
         //             startActivity(i);

        // }
      //  },2000);
    }
}