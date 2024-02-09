package com.rider.troopadelivery.troopa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class forgot_password_p1 extends AppCompatActivity {

    Button change_password1;
    EditText phone_numbers;
    RequestQueue queue;
    private ProgressBar loadingPB;
    TextView errormsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_forgot_password_p1);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        change_password1=findViewById(R.id.change_password1);
        phone_numbers=findViewById(R.id.phone_number);
        errormsg=findViewById(R.id.errormsg);
        loadingPB = findViewById(R.id.idLoadingPB);

        change_password1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String all_phone = "+234"+phone_numbers.getText().toString();


                if (!phone_numbers.getText().toString().isEmpty() && phone_numbers.getText().length()>9) {


                    if(phone_numbers.getText().charAt(0)=='0'){


                        errormsg.setText("Please enter phone number in +2348012345 format");

                        //Toast.makeText(easy_login.this, " ", Toast.LENGTH_LONG).show();


                    }
                    else {

                        loadingPB.setVisibility(View.VISIBLE);
                        String URL = "https://www.troopa.org/api/forgotpasscode2/"+all_phone;

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

                                        // starting new activity.
                                        //Toast.makeText(login_now_activity.this,obj.getString("machine_manufacture"), Toast.LENGTH_LONG).show();

                                        Intent i = new Intent(forgot_password_p1.this,forgot_password.class);
                                        i.putExtra("passid",obj.getString("id"));
                                        startActivity(i);
                                        finish();
                                        //Toast.makeText(login_now_activity.this, "welcome", Toast.LENGTH_LONG).show();


                                    }else {

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

                }else{

                    errormsg.setText("Please enter a complete mobile number");
                }




            }
        });
    }
}