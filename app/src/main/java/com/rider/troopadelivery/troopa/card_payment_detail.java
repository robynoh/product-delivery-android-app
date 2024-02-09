package com.rider.troopadelivery.troopa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.rider.troopadelivery.troopa.R;

import java.text.NumberFormat;
import java.util.Locale;

public class card_payment_detail extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_payment_detail);

        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Cash Payment Detail");

        String trip_cost = getIntent().getStringExtra("trip_cost");
        String trip_serial = getIntent().getStringExtra("request_serial");
        String clientname = getIntent().getStringExtra("clientname");
         String payment_mode = getIntent().getStringExtra("payment_mode");
         String pickup_location = getIntent().getStringExtra("pickup_location");
         String dropoff_location = getIntent().getStringExtra("dropoff_location");
        String status = getIntent().getStringExtra("payment_status");
        String date_time= getIntent().getStringExtra("day")+" "+getIntent().getStringExtra("time");

        TextView trip_serialx = (TextView) findViewById(R.id.trip_serial);
        TextView trip_costx = (TextView) findViewById(R.id.trip_cost);
        TextView  clientnamex = ( TextView ) findViewById(R.id.clientname);
        TextView pickup_locationx = (TextView) findViewById(R.id.pickup_location);
        TextView dropoff_locationx = (TextView) findViewById(R.id.dropoff_location);
        TextView date_timex = (TextView) findViewById(R.id.date_time);
        TextView payment_modex = (TextView) findViewById(R.id.payment_mode);
        TextView trip_status = (TextView) findViewById(R.id.trip_status);

        trip_serialx.setText(trip_serial);
        trip_costx.setText("N"+ NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(trip_cost)));
        clientnamex.setText(clientname);
        pickup_locationx.setText(pickup_location);
        dropoff_locationx.setText(dropoff_location);
       // dropoff_locationx.setText(date_time);
        payment_modex.setText(payment_mode);
        date_timex.setText(date_time);

        if(status.equals("ok")) {
            trip_status.setText("Successful");
        }


    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}