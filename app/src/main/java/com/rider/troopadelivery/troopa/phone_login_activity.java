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

public class phone_login_activity extends AppCompatActivity {

    Button login_btn;
    private ProgressBar loadingPB;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    RequestQueue queue;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //finishAffinity(); // or finish();

        Intent i = new Intent(phone_login_activity.this,login_activity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_phone_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        EditText phone =(EditText)findViewById(R.id.phone_numbers);

        login_btn = findViewById(R.id.login);
        loadingPB = findViewById(R.id.idLoadingPB);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String all_phone = "+234"+phone.getText().toString();

                if (!phone.getText().toString().isEmpty() && phone.getText().length()>9) {

                    if(phone.getText().charAt(0)=='0'){

                        Toast.makeText(phone_login_activity.this, "Please enter phone number in +2348012345 format ", Toast.LENGTH_LONG).show();


                    }
                    else {

                        loadingPB.setVisibility(View.VISIBLE);
                       check_if_exist(all_phone);



                    }

                } else {

                    Toast.makeText(phone_login_activity.this, "Please enter a complete mobile number ", Toast.LENGTH_LONG).show();
                }



            }
        });

    }

    public void check_if_exist(String fullnumbervariable ){

        String URL = "https://www.troopa.org/api/checkifexist/"+fullnumbervariable;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);

                    //Toast.makeText(phone_login_activity.this,obj.getString("error"), Toast.LENGTH_LONG).show();


                   if (obj.getString("error").equals("true")) {
                       Toast.makeText(phone_login_activity.this, "This Mobile number is not in our record", Toast.LENGTH_LONG).show();


                   }else {
                       loadingPB.setVisibility(View.GONE);
                       Intent i = new Intent(phone_login_activity.this,phone_confirm_activity.class);
                      i.putExtra("phonevalue",fullnumbervariable);
                       startActivity(i);
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
}