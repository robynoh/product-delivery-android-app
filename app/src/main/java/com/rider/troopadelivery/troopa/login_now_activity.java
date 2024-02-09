package com.rider.troopadelivery.troopa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

public class login_now_activity extends AppCompatActivity {

TextView otpnumber;
TextView resend;
EditText value1;
EditText value2;
EditText value3;
EditText value4;
Button verify;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    RequestQueue queue;
    private ProgressBar loadingPB;

    String output="";
    Button delivered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.otp_activity);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        String phone_number = getIntent().getStringExtra("phonevalue");
        loadingPB = findViewById(R.id.idLoadingPB);
        otpnumber=findViewById(R.id.otp_number);
        otpnumber.setText(phone_number);

        value1=(EditText) findViewById(R.id.value1);
        value2=(EditText)findViewById(R.id.value2);
        value3=(EditText)findViewById(R.id.value3);
        value4=(EditText)findViewById(R.id.value4);


        verify=findViewById(R.id.verifyme);
        resend=findViewById(R.id.resend);










        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp =value1.getText().toString()+value2.getText().toString()+value3.getText().toString()+value4.getText().toString();
                //Toast.makeText(login_now_activity.this,otp, Toast.LENGTH_LONG).show();
                loadingPB.setVisibility(View.VISIBLE);
                otp_confirm(phone_number,otp);

            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_otp(phone_number);
            }
        });

    }

    public void otp_confirm(String fullnumbervariable,String otp ){

        String URL = "https://www.troopa.org/api/otppairing/"+fullnumbervariable+"/"+otp;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);

                    //Toast.makeText(phone_login_activity.this,obj.getString("error"), Toast.LENGTH_LONG).show();


                    if (obj.getString("error").equals("true")) {
                        loadingPB.setVisibility(View.GONE);
                        Toast.makeText(login_now_activity.this, "Sorry confirmation code not matched", Toast.LENGTH_LONG).show();


                    }else {

                        loadingPB.setVisibility(View.GONE);
//                        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPref.edit();
//
//                        editor.putString("phone", fullnumbervariable);
//                        editor.putString("email", obj.getString("email"));
//                        editor.putString("name",  obj.getString("name"));
//                        editor.putString("picture",  obj.getString("picture"));
//                        editor.putInt("riderstatus", Integer.parseInt(String.valueOf(obj.getInt("status"))));
//                        editor.putString("date",  obj.getString("date"));
//                        editor.putInt("online_status",  Integer.parseInt(String.valueOf(obj.getInt("online_status"))));
//                        editor.putInt("verification_status",  Integer.parseInt(String.valueOf(obj.getInt("verification_status"))));
//                        editor.putString("license",  obj.getString("license_plate"));
//                        editor.putString("machine_manufacture", obj.getString("machine_manufacture"));
//                        editor.putString("pilotID", obj.getString("riderid"));
//                        editor.putString("address", obj.getString("address"));
//                        editor.putString("engine_size", obj.getString("engine_size"));
//                        editor.putString("bike_color", obj.getString("bike_color"));
//                        editor.putString("bank_name", obj.getString("bank_name"));
//                        editor.putString("account_name", obj.getString("account_name"));
//                        editor.putString("account_number", obj.getString("account_number"));
//
//
//                        // to save our data with key and value.
//                        editor.apply();

                        // starting new activity.
                        //Toast.makeText(login_now_activity.this,obj.getString("machine_manufacture"), Toast.LENGTH_LONG).show();

//                        Intent i = new Intent(login_now_activity.this,easy_login.class);
//                        startActivity(i);
//                        finish();
                        //Toast.makeText(login_now_activity.this, "welcome", Toast.LENGTH_LONG).show();


                        AlertDialog.Builder builder = new AlertDialog.Builder(login_now_activity.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.phone_verify_dialogue, viewGroup, false);
                        builder.setView(dialogView);

                        delivered = dialogView.findViewById(R.id.delivered);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);

                        delivered.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(login_now_activity.this,easy_login.class);
                                startActivity(i);
                                finish();
                            }
                        });
                        alertDialog.show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Toast.makeText(phone_login_activity.this,response.toString().length(), Toast.LENGTH_LONG).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
            }
        });
        queue.add(request);





    }

    public void send_otp(String fullnumbervariable ){

        String URL = "https://www.troopa.org/api/sendotp/"+fullnumbervariable;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (obj.getString("error").equals("false")) {

                        Toast.makeText(login_now_activity.this, "Verification code has been sent to this number "+fullnumbervariable, Toast.LENGTH_LONG).show();


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



}