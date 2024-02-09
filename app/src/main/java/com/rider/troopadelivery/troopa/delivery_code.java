package com.rider.troopadelivery.troopa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rider.troopadelivery.troopa.R;

import org.json.JSONException;
import org.json.JSONObject;

public class delivery_code extends AppCompatActivity {

    Button deliveredbtn;
    RequestQueue queue;
    private ProgressBar loadingPB;
    ImageView paymodeimg;
    Button delivered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_code);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Package Delivery");

        TextView clientname=findViewById(R.id.clientname);
        TextView client_contact=findViewById(R.id.client_contact);
        TextView paymode=findViewById(R.id.paymode);
        paymodeimg=findViewById(R.id.paymodeimg);

        TextView amountbold=findViewById(R.id.amount);





        loadingPB = findViewById(R.id.idLoadingPB);


        Intent intent = getIntent();

        String client_id = getIntent().getStringExtra("client_id");
        String rider_id = getIntent().getStringExtra("rider_id");
        String amount = getIntent().getStringExtra("amount");
        String order_id = getIntent().getStringExtra("order_id");
        String dropoff_contact_name = getIntent().getStringExtra("dropoff_contact_name");
        String dropoff_contact = getIntent().getStringExtra("dropoff_contact");
        String payment_mode = getIntent().getStringExtra("payment_mode");

      // Toast.makeText(delivery_code.this, "amount:"+amount+"riderid:"+rider_id, Toast.LENGTH_LONG).show();


        clientname.setText(dropoff_contact_name);
        client_contact.setText(dropoff_contact);
        client_contact.setText(dropoff_contact);
        paymode.setText(payment_mode);
        amountbold.setText("NGN"+amount);

        if(payment_mode.equals("card")) {
            paymodeimg.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.cardpay2));

        }
        else if(payment_mode.equals("cash")) {
            paymodeimg.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.cashpay));

        }

        deliveredbtn=findViewById(R.id.deliveredbtn);
        deliveredbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText pickup_code = findViewById(R.id.pickup_code);
                String pickup_code_value=pickup_code.getText().toString();

                if ( pickup_code.length() == 0) {

                    TextView errormsg=findViewById(R.id.errormsg);
                    errormsg.setText("Please enter the delivery code");


                }else {


                    String URL = "https://www.troopa.org/api/confirm-delivery/" + client_id + "/" + rider_id + "/" + amount + "/" + order_id + "/" + pickup_code_value;
                    loadingPB.setVisibility(View.VISIBLE);
                    queue = Volley.newRequestQueue(delivery_code.this);
                    StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONObject obj = null;
                            try {
                                obj = new JSONObject(response);
                                if (obj.getString("error").equals("false")) {


                                    loadingPB.setVisibility(View.GONE);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(delivery_code.this);
                                    ViewGroup viewGroup = findViewById(android.R.id.content);
                                    View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.paid_delivered_dialogue, viewGroup, false);
                                    builder.setView(dialogView);

                                    delivered = dialogView.findViewById(R.id.delivered);
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.setCanceledOnTouchOutside(false);

                                    delivered.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(delivery_code.this, introMap.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    });
                                    alertDialog.show();


                                } else if (obj.getString("error").equals("true")) {


                                    loadingPB.setVisibility(View.GONE);


                                    TextView errormsg = findViewById(R.id.errormsg);
                                    errormsg.setText(obj.getString("message"));


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", error.toString());
                        }
                    });
                    queue.add(request);


                }

            }
        });
    }
    public boolean onSupportNavigateUp(){


        SharedPreferences sharedPrefer = getSharedPreferences("goOnlineKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefer.edit();

        editor.putString("onlineActivate", "start");
        // to save our data with key and value.
        editor.commit();

        onBackPressed();
        return true;
    }
}