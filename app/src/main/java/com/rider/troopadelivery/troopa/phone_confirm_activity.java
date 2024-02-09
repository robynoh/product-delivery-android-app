package com.rider.troopadelivery.troopa;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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


public class phone_confirm_activity extends AppCompatActivity {

    TextView phone_no;
    Button send;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    RequestQueue queue;
    private ProgressBar loadingPB;
    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_phone_confirm);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        phone_no= findViewById(R.id.phone_digit_number);
        loadingPB = findViewById(R.id.idLoadingPB);

        String phone_number = getIntent().getStringExtra("phonevalue");
        phone_no.setText(phone_number);
        String fullnumbervariable=phone_number;
        send=findViewById(R.id.senddigit);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingPB.setVisibility(View.VISIBLE);
                //Toast.makeText(phone_confirm_activity.this, "bla bla ", Toast.LENGTH_LONG).show();
                send_otp(fullnumbervariable);

            }
        });


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

                       loadingPB.setVisibility(View.GONE);
                       Intent i = new Intent(phone_confirm_activity.this,login_now_activity.class);
                       i.putExtra("phonevalue",fullnumbervariable);
                       startActivity(i);


                   }else {

                       Toast.makeText(phone_confirm_activity.this, "Something went wrong", Toast.LENGTH_LONG).show();

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