package com.rider.troopadelivery.troopa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

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

public class real_order_list extends AppCompatActivity {

    ArrayList<all_real_order_list> arrayList;
    ListView lv;
    RequestQueue queue;
    String riderid;
    real_order_list_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_order_list);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("All ride request");

        arrayList = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        riderid = sharedPreferences.getString("pilotID", "");





        pull_real_order(riderid);
        lv =findViewById(R.id.listView2);
    }

    private void pull_real_order(String riderid) {



        // Building the url to the web service
        String URL = "https://www.troopa.org/api/clientorder/"+riderid;



        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject obj = null;

                try {
                    obj = new JSONObject(response);


                    JSONArray jsonArray = obj.getJSONArray("requests");
                    for(int i =0;i<jsonArray.length(); i++){
                        JSONObject productObject = jsonArray.getJSONObject(i);
                        arrayList.add(new all_real_order_list(
                                riderid,
                                productObject.getString("id"),
                                productObject.getString("picture"),
                                productObject.getString("fname")+" "+productObject.getString("lname"),
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
                                productObject.getString("duration")+"-"+productObject.getString("distance"),
                                productObject.getString("delivery_status")

                        ));
                    }


                    adapter = new   real_order_list_adapter(real_order_list.this,R.layout.real_orders, arrayList);
                    lv.setAdapter(adapter);







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