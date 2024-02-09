package com.rider.troopadelivery.troopa;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.Calendar;

public class card_payments extends AppCompatActivity {
LinearLayout  card_payment_detail;
    ArrayList<all_card_payments> arrayList;
    ListView lv;
    RequestQueue queue;
    LinearLayout cash_detail;
    String riderid;
    TextView card_total;
    card_payment_list_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_payments);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Card Payments");


        Spinner spinner=findViewById(R.id.spinner);
        ArrayList<String>  arrayListspinner= new ArrayList<>();
        arrayListspinner.add("Card Earning");
        arrayListspinner.add("Day");
        arrayListspinner.add("Week");
        arrayListspinner.add("Month");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(card_payments.this,android.R.layout.simple_spinner_item,arrayListspinner);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);;
                // Toast.makeText(parent.getContext(), "Selected: " + tutorialsName,          Toast.LENGTH_LONG).show();

                if(tutorialsName.equals("Day")){

                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker dialog
                    DatePickerDialog datePickerDialog = new DatePickerDialog(card_payments.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
//                                    expirydate.setText(dayOfMonth + "/"
//                                            + (monthOfYear + 1) + "/" + year);
                                    Intent a = new Intent(card_payments.this,daily_card_payment.class);
                                    a.putExtra("day", String.valueOf(dayOfMonth));
                                    a.putExtra("month", String.valueOf(monthOfYear));
                                    a.putExtra("year", String.valueOf(year));
                                    startActivity(a);
                                    //finish();


                                    // pull_all_cash_payment_daily(riderid, dayOfMonth,monthOfYear,year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();

                }

                if(tutorialsName.equals("Month")){

                    Intent b = new Intent(card_payments.this,card_monthly_payment.class);
                    startActivity(b);
                    // finish();

                    // pull_all_cash_payment_monthly(riderid);

                }

                if(tutorialsName.equals("Week")){

                    Intent c = new Intent(card_payments.this,card_week_payment.class);
                    startActivity(c);
                    //finish();

                }




            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });




        arrayList = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        riderid = sharedPreferences.getString("pilotID", "");
        pull_all_card_payment(riderid);
        lv =findViewById(R.id.listView4);

        card_total =findViewById(R.id.card_total);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
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

                //Toast.makeText(card_payments.this,status, Toast.LENGTH_LONG).show();



                Intent intent = new Intent(getApplicationContext(),card_payment_detail.class);
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





//        card_payment_detail=findViewById(R.id.card_payment_detail);
//        card_payment_detail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(card_payments.this,card_payment_detail.class);
//                startActivity(i);
//            }
//        });
    }


    private void pull_all_card_payment(String riderid) {



        // Building the url to the web service
        String URL = "https://www.troopa.org/api/rider-card-payment/"+riderid;



        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject obj = null;

                try {
                    obj = new JSONObject(response);

                    card_total.setText(obj.getString("totalCardEarning"));

                    JSONArray jsonArray = obj.getJSONArray("requests");
                    for(int i =0;i<jsonArray.length(); i++){
                        JSONObject productObject = jsonArray.getJSONObject(i);
                        arrayList.add(new all_card_payments(
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


                    adapter = new   card_payment_list_adapter(card_payments.this,R.layout.card_payment_list, arrayList);
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