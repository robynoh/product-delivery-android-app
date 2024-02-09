package com.rider.troopadelivery.troopa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class withdraw extends AppCompatActivity {
    Button withdrawnow;
    RequestQueue queue;
    EditText balancetxt;
    String balancetxt_value;
    TextView errormsg;
    TextView todayearning;


    private ProgressBar loadingPB;
    Button delivered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Cash withdrawal");

        withdrawnow = findViewById(R.id.withdrawnow);
        loadingPB = findViewById(R.id.idLoadingPB);

        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String riderid = sharedPreferences.getString("pilotID", "");

        Intent intent = getIntent();
        String balancex = getIntent().getStringExtra("balanceValue");
        String balancextext = getIntent().getStringExtra("balanceText");

        balancetxt = findViewById(R.id.withdrawal);
        balancetxt_value=balancetxt.getText().toString();
        todayearning=findViewById(R.id.todayearning);
        errormsg=findViewById(R.id.errormsg);

        todayearning.setText(balancextext);

        withdrawnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                balancetxt_value=balancetxt.getText().toString();

                if (balancetxt.length() == 0) {


                    errormsg.setText("Please enter amount");


                }
                else if (Integer.parseInt(balancetxt_value) >Integer.parseInt(balancex)){

                    TextView errormsg=findViewById(R.id.errormsg);
                    errormsg.setText("Insufficient Fund");


                }else{

                   withdraw_ticket(riderid,Integer.parseInt(balancetxt_value),Integer.parseInt(balancex));

                }

            }
        });



    }

    public void  withdraw_ticket(String riderid,int withdraw,int balance){


        String URL = "https://www.troopa.org/api/rider-withdraw/"+riderid+"/"+withdraw+"/"+balance;
        loadingPB.setVisibility(View.VISIBLE);
        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (obj.getString("status").equals("ok")) {

                        loadingPB.setVisibility(View.GONE);

                        AlertDialog.Builder builder = new AlertDialog.Builder(withdraw.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.withdraw_ticket_dialogue, viewGroup, false);
                        builder.setView(dialogView);

                        delivered = dialogView.findViewById(R.id.delivered);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);

                        delivered.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(withdraw.this, rider_earning.class);
                                startActivity(i);
                                finish();
                            }
                        });
                        alertDialog.show();


                    }else {
                        errormsg.setText("Something went wrong. Please try again");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
            }
        });
        queue.add(request);


    }


    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}