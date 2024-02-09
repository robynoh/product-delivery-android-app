package com.rider.troopadelivery.troopa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rider.troopadelivery.troopa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class easy_login extends AppCompatActivity {
    Button login;
    EditText phone_numbers;
    EditText password;
    boolean isAllFieldsChecked = false;
    String all_phone;
    String riderid;

    TextView errormsg,verify_phone,forgot_password;
    RequestQueue queue;
    private ProgressBar loadingPB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_easy_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        login=findViewById(R.id.login);
        loadingPB = findViewById(R.id.idLoadingPB);
        phone_numbers=findViewById(R.id.phone_numbers);
        password=findViewById(R.id.password);
        errormsg=findViewById(R.id.errormsg);
        verify_phone=findViewById(R.id.verify_phone);

        forgot_password=findViewById(R.id.forgot_password);


        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent a = new Intent(easy_login.this, forgot_password_p1.class);
                startActivity(a);
                finish();

            }
        });


        verify_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(easy_login.this, phone_login_activity.class);
                startActivity(a);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // loadingPB.setVisibility(View.VISIBLE);

                isAllFieldsChecked= CheckAllFields();
                if (isAllFieldsChecked) {

                 String   phone=phone_numbers.getText().toString();
                    String   pass=password.getText().toString();




                    all_phone = "+234"+phone_numbers.getText().toString();

                    if (!phone_numbers.getText().toString().isEmpty() && phone_numbers.getText().length()>9) {

                        if(phone_numbers.getText().charAt(0)=='0'){


                            errormsg.setText("Please enter phone number in +2348012345 format");

                            //Toast.makeText(easy_login.this, " ", Toast.LENGTH_LONG).show();


                        }
                        else {

                            loadingPB.setVisibility(View.VISIBLE);
                           // check_if_exist(all_phone);

                            String URL = "https://www.troopa.org/api/easy-login/"+all_phone+"/"+pass;

                            queue = Volley.newRequestQueue(getApplicationContext());
                            StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    JSONObject obj = null;
                                    try {
                                        obj = new JSONObject(response);
                                        if (obj.getString("error").equals("false")) {
                                            riderid=obj.getString("riderid");

                                            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                                                @Override
                                                public void onComplete(@NonNull Task<String> task) {

                                                    String token = task.getResult();

                                                    Logfcmid(riderid,token);

                                                    //Toast.makeText(easy_login.this,token, Toast.LENGTH_LONG).show();

//                                                            if (!task.isSuccessful()) {
//                                                                Log.w(e, "Fetching FCM registration token failed", task.getException());
//                                                                return;
//                                                            }

                                                    // Get new FCM registration token
                                                    //   String token = task.getResult();

                                                    // Log and toast
                                                    //  String msg = getString(R.string.msg_token_fmt, token);
                                                    //  Log.d(TAG, msg);
                                                    //  Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                                }
                                            });





                                            //   Toast.makeText(introMap.this,response, Toast.LENGTH_LONG).show();


                                            loadingPB.setVisibility(View.GONE);
                                            SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPref.edit();

                                            editor.putString("phone", all_phone);
                                            editor.putString("email", obj.getString("email"));
                                            editor.putString("name",  obj.getString("name"));
                                            editor.putString("picture",  obj.getString("picture"));
                                            editor.putInt("riderstatus", Integer.parseInt(String.valueOf(obj.getInt("status"))));
                                            editor.putString("date",  obj.getString("date"));
                                            editor.putInt("online_status",  Integer.parseInt(String.valueOf(obj.getInt("online_status"))));
                                            editor.putInt("verification_status",  Integer.parseInt(String.valueOf(obj.getInt("verification_status"))));
                                            editor.putString("license",  obj.getString("license_plate"));
                                            editor.putString("machine_manufacture", obj.getString("machine_manufacture"));
                                            editor.putString("pilotID", obj.getString("riderid"));
                                            editor.putString("address", obj.getString("address"));
                                            editor.putString("engine_size", obj.getString("engine_size"));
                                            editor.putString("bike_color", obj.getString("bike_color"));
                                            editor.putString("bank_name", obj.getString("bank_name"));
                                            editor.putString("account_name", obj.getString("account_name"));
                                            editor.putString("account_number", obj.getString("account_number"));
                                            editor.putString("third_delivery", obj.getString("third_delivery"));

                                            // to save our data with key and value.
                                            editor.apply();

                                            // starting new activity.
                                            //Toast.makeText(login_now_activity.this,obj.getString("machine_manufacture"), Toast.LENGTH_LONG).show();

                                            Intent i = new Intent(easy_login.this,introMap.class);
                                            startActivity(i);
                                            finish();
                                            //Toast.makeText(login_now_activity.this, "welcome", Toast.LENGTH_LONG).show();


                                        }else if(obj.getString("error").equals("true")){

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

                    } else {
                        errormsg.setText("Please enter a complete mobile number");

                       // Toast.makeText(easy_login.this, " ", Toast.LENGTH_LONG).show();
                    }










                }

            }
        });

    }


    private boolean CheckAllFields() {



        if ( phone_numbers.length() == 0) {
            phone_numbers.setError("enter your phone number");
            return false;
        }

        if (password.length() == 0) {
            password.setError("Enter password");
            return false;
        }


        // after all validation return true.
        return true;
    }

    public void Logfcmid(String riderid,String msgid){


        String URL = "https://www.troopa.org/api/log-rider-msg-id/"+riderid+"/"+msgid;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);


                    if (obj.getString("status").equals("ok")) {





                    }else {


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