package com.rider.troopadelivery.troopa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class platform_earning extends AppCompatActivity {

    ArrayList<platform_earning_list> arrayList;
    ListView lv;
    RequestQueue queue;
    LinearLayout emptyrecord;
    platform_earning_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform_earning);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Platform Earning");

        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String riderid = sharedPreferences.getString("pilotID", "");

        pull_platform_earning(riderid);

        arrayList = new ArrayList<>();
        lv =findViewById(R.id.listViewPlatformEarning);
        emptyrecord=findViewById(R.id.emptyrecord);
    }

    private void pull_platform_earning(String riderid) {



        // Building the url to the web service
        String URL = "https://www.troopa.org/api/all-platform-earnings/"+riderid;



        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject obj = null;

                try {
                    obj = new JSONObject(response);


                    if (obj.getString("count").equals("0")) {
                        emptyrecord.setVisibility(View.VISIBLE);
                    }else {
                        JSONArray jsonArray = obj.getJSONArray("requests");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject productObject = jsonArray.getJSONObject(i);


                            arrayList.add(new platform_earning_list(

                                    productObject.getString("packageid"),
                                    productObject.getString("clientname"),
                                    productObject.getString("amount"),
                                    productObject.getString("type"),
                                    productObject.getString("riderearning"),
                                    productObject.getString("platform_earning"),
                                    productObject.getString("date")



                            ));
                        }


                        adapter = new platform_earning_adapter(platform_earning.this, R.layout.all_platform_earning_list, arrayList);
                        lv.setAdapter(adapter);


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