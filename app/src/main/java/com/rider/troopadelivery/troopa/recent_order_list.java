package com.rider.troopadelivery.troopa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rider.troopadelivery.troopa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class recent_order_list extends AppCompatActivity {
    ArrayList<all_order_list> arrayList;
    ListView lv;
    RequestQueue queue;
    String riderid;
    order_list_adapter adapter;
    LinearLayout emptyrecord;


    LinearLayout declinerequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_order_list);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Available orders");



        arrayList = new ArrayList<>();



        Intent intent = getIntent();
        riderid = getIntent().getStringExtra("rider_id");
        pull_recent_order(riderid);
        lv =findViewById(R.id.listView);
        emptyrecord=findViewById(R.id.emptyrecord);








    }


    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void pull_recent_order(String riderid) {



        // Building the url to the web service
        String URL = "https://www.troopa.org/api/all-recent-request/"+riderid;



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


                            arrayList.add(new all_order_list(
                                    riderid,
                                    productObject.getString("id"),
                                    productObject.getString("picture"),
                                    productObject.getString("fname") + " " + productObject.getString("lname"),
                                    productObject.getString("created_at"),
                                    productObject.getString("pickup_location"),
                                    productObject.getString("dropoff_location"),
                                    productObject.getString("package_type"),
                                    productObject.getString("distance"),
                                    productObject.getString("duration"),
                                    productObject.getString("pickup_contact"),
                                    productObject.getString("dropoff_contact"),
                                    productObject.getString("pickup_contact_name"),
                                    productObject.getString("dropoff_contact_name"),
                                    productObject.getString("payment_type"),
                                    productObject.getString("payment_mode"),
                                    productObject.getString("duration") + "-" + productObject.getString("distance"),
                                    productObject.getString("amount"),
                                    productObject.getString("delivery_status")

                            ));
                        }


                        adapter = new order_list_adapter(recent_order_list.this, R.layout.alert_order_list, arrayList);
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

}