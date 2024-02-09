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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rider.troopadelivery.troopa.R;

import org.json.JSONException;
import org.json.JSONObject;

public class forgot_password extends AppCompatActivity {

    Button change_password;
    EditText phone_numbers;
    EditText new_password;
    EditText confirm_password,otp;
    boolean isAllFieldsChecked = false;
    TextView errormsg;
    RequestQueue queue;
    Button delivered;
    private ProgressBar loadingPB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        change_password=findViewById(R.id.change_password);
        new_password=findViewById(R.id.new_password);
        confirm_password=findViewById(R.id.confirm_password);
        otp=findViewById(R.id.otp);
        errormsg=findViewById(R.id.errormsg);

        loadingPB = findViewById(R.id.idLoadingPB);

       // Toast.makeText(forgot_password.this, getIntent().getStringExtra("passid"), Toast.LENGTH_LONG).show();


        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                isAllFieldsChecked= CheckAllFields();
                if (isAllFieldsChecked){


                    String   otp1=otp.getText().toString();
                    String   newpass=new_password.getText().toString();
                    String   passid=getIntent().getStringExtra("passid");

                    loadingPB.setVisibility(View.VISIBLE);

                    String URL = "https://www.troopa.org/api/otppass2/"+otp1+"/"+newpass+"/"+passid;

                    queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONObject obj = null;
                            try {
                                obj = new JSONObject(response);
                                if (obj.getString("status").equals("OK")) {

                                    //   Toast.makeText(introMap.this,response, Toast.LENGTH_LONG).show();


                                    loadingPB.setVisibility(View.GONE);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(forgot_password.this);
                                    ViewGroup viewGroup = findViewById(android.R.id.content);
                                    View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.change_pass_dialogue, viewGroup, false);
                                    builder.setView(dialogView);

                                    delivered = dialogView.findViewById(R.id.delivered);
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.setCanceledOnTouchOutside(false);

                                    delivered.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(forgot_password.this,easy_login.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    });
                                    alertDialog.show();
                                    // starting new activity.
                                    //Toast.makeText(login_now_activity.this,obj.getString("machine_manufacture"), Toast.LENGTH_LONG).show();


                                    //Toast.makeText(login_now_activity.this, "welcome", Toast.LENGTH_LONG).show();


                                }else if(obj.getString("status").equals("FAILED")){

                                    loadingPB.setVisibility(View.GONE);
                                    errormsg.setText(obj.getString("message"));

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



        });

    }


    private boolean CheckAllFields() {



        if ( otp.length() == 0) {
            otp.setError("enter OTP");
            return false;
        }

        if (new_password.length() == 0) {
            new_password.setError("Enter a new password");
            return false;
        }

        if (confirm_password.length() == 0) {
            confirm_password.setError("Please confirm your password");
            return false;
        }
        if(!new_password.getText().toString().equals(confirm_password.getText().toString())) {

            confirm_password.setError("Both passwords does not match");
            return false;
        }


        // after all validation return true.
        return true;
    }
}