package com.rider.troopadelivery.troopa;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

public class daily_earning extends AppCompatActivity {

    ArrayList<all_cash_payments> arrayList;
    ListView lv;

    ListView lvmonth;
    ListView lvday;
    RequestQueue queue;
    LinearLayout cash_detail;
    String riderid;

    TextView total_day_cash_earning;
    cash_payment_list_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_earning);

        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Daily Cash Payment");


        lvday =findViewById(R.id.listViewday);
        lvday.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){

                // ListView Clicked item index

                String clientname = arrayList.get(position).clientname();
                String pickup_location = arrayList.get(position).pickup_location();
                String dropoff_location= arrayList.get(position).dropoff_location();
                String status = arrayList.get(position).payment_status();
                String payment_mode = arrayList.get(position).payment_mode();
                String day= arrayList.get(position).request_date();
                String time= arrayList.get(position).request_time();
                String trip_serial= arrayList.get(position).getserial();
                String trip_cost = arrayList.get(position).amount();

                //  Toast.makeText(cash_payment.this, status, Toast.LENGTH_LONG).show();



                Intent intent = new Intent(getApplicationContext(),cash_payments_details.class);
                intent.putExtra("clientname", clientname);
                intent.putExtra("trip_cost", trip_cost);
                intent.putExtra("request_serial",  trip_serial);
                intent.putExtra("pickup_location", pickup_location);
                intent.putExtra("dropoff_location", dropoff_location);
                intent.putExtra("payment_status", status);
                intent.putExtra("payment_mode",payment_mode);
                intent.putExtra("day", day);
                intent.putExtra("time", time);
                startActivity(intent);



            }
        });

        total_day_cash_earning=findViewById(R.id.total_day_cash_earning);
        arrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        riderid = sharedPreferences.getString("pilotID", "");

        String day = getIntent().getStringExtra("day");
        String month = getIntent().getStringExtra("month");
        String year = getIntent().getStringExtra("year");



        //Toast.makeText(daily_earning.this,day+""+month+""+year, Toast.LENGTH_LONG).show();
        pull_all_cash_payment_daily(riderid,day,month,year);
    }

    private void pull_all_cash_payment_daily(String riderid, String day,String month, String year) {



        // Building the url to the web service
        String URL = "https://www.troopa.org/api/pull-cash-payment-daily/"+riderid+"/"+day+"/"+month+"/"+year;



        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject obj = null;

                try {
                    obj = new JSONObject(response);

                    total_day_cash_earning.setText(obj.getString("dailyCash"));


                    JSONArray jsonArray = obj.getJSONArray("requests");
                    for(int i =0;i<jsonArray.length(); i++){
                        JSONObject productObject = jsonArray.getJSONObject(i);
                        arrayList.add(new all_cash_payments(
                                riderid,
                                productObject.getString("order_serial"),
                                productObject.getString("fname"),
                                productObject.getString("created_at"),
                                productObject.getString("pickup_location_format"),
                                productObject.getString("dropoff_location_format"),
                                productObject.getString("package_type"),
                                productObject.getString("payment_type"),
                                productObject.getString("payment_mode"),
                                productObject.getString("distance"),
                                productObject.getString("duration"),
                                productObject.getString("pickup_contact"),
                                productObject.getString("dropoff_contact"),
                                productObject.getString("pickup_contact_name"),
                                productObject.getString("dropoff_contact_name"),
                                productObject.getString("duration")+"-"+productObject.getString("distance"),
                                productObject.getString("status"),
                                productObject.getString("amount"),
                                productObject.getString("request_date"),
                                productObject.getString("request_time")

                        ));
                    }


                    adapter = new   cash_payment_list_adapter(daily_earning.this,R.layout.cash_payment_list, arrayList);
                    lvday.setAdapter(adapter);









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