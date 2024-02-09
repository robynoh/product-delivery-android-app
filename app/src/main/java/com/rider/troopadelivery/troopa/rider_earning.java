package com.rider.troopadelivery.troopa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.rider.troopadelivery.troopa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class rider_earning extends AppCompatActivity {
    RequestQueue queue;
    RequestQueue queue2;
    TextView all_trip;
    TextView current_balance;
    TextView all_rider_earning;
    TextView rider_bonus,timeonline;
    TextView   todayearning;
    LinearLayout statement;


    Button openwithdraw;

    // variable for our bar chart
    BarChart mBarChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_earning);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Rider Earning");



        // initializing variable for bar chart.
        mBarChart = findViewById(R.id.idBarChart);

        timeonline= findViewById(R.id.timeonline);

        todayearning= findViewById(R.id.todayearning);

        statement= findViewById(R.id.statement);

        // calling method to get bar entries.


        statement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(rider_earning.this,statement.class);
                startActivity(i);
            }
        });

        openwithdraw = findViewById(R.id.openwithraw);



        current_balance=findViewById(R.id.current_balance);
        all_trip=findViewById(R.id.all_trip);
        all_rider_earning=findViewById(R.id.all_rider_earning);
        rider_bonus=findViewById(R.id.rider_bonus);

        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String riderid = sharedPreferences.getString("pilotID", "");

        try {
            showMonthsBars(riderid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            all_stat(riderid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            todayearningvalue(riderid);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public boolean onSupportNavigateUp(){


        onBackPressed();
        return true;
    }

    public void all_stat(String riderid){

        final String[] balance = {""};
        final String[] balancetext = {""};

        String URL = "https://www.troopa.org/api/rider-stat/"+riderid;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (obj.getString("status").equals("ok")) {

                        JSONArray jsonArray = obj.getJSONArray("stat");
                        for(int i =0;i<jsonArray.length(); i++) {
                            JSONObject productObject = jsonArray.getJSONObject(i);

                            //cash_earning.setText(productObject.getString("riderCashEarning"));
                            timeonline.setText(productObject.getString("totalTimeOnine"));
                            current_balance.setText(productObject.getString("currentBalance"));
                            all_trip.setText(productObject.getString("totalTrips"));
                            all_rider_earning.setText(productObject.getString("riderEarnings"));
                            rider_bonus.setText(productObject.getString("riderBonus"));
                            balance[0] =productObject.getString("currentBalanceValue");
                            balancetext[0] =productObject.getString("currentBalance");
                            // ongoingtrip.setVisibility(View.VISIBLE);

                            openwithdraw.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(rider_earning.this,withdraw.class);
                                    i.putExtra("balanceValue", balance[0]);
                                    i.putExtra("balanceText",balancetext[0]);
                                    startActivity(i);
                                }
                            });


                        }  // Toast.makeText(riderProfile.this,obj.getString("count"), Toast.LENGTH_LONG).show();


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

    private void showMonthsBars(String riderid){




        String URL = "https://www.troopa.org/api/weekly-earning/"+riderid;

        queue2 = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (obj.getString("error").equals("false")) {

                        //input Y data (Months Data - 12 Values)
                        ArrayList<Double> valuesList = new ArrayList<Double>();
                        JSONArray jsonArray = obj.getJSONArray("d1");
                        for(int i =0;i<jsonArray.length(); i++) {
                            JSONObject productObject = jsonArray.getJSONObject(i);
                            valuesList.add((double) productObject.getDouble("t1_amount"));
                        }

                        JSONArray jsonArray2 = obj.getJSONArray("d2");
                        for(int i =0;i<jsonArray2.length(); i++) {
                            JSONObject productObject2 = jsonArray2.getJSONObject(i);
                            valuesList.add((double) productObject2.getDouble("t2_amount"));
                        }

                        JSONArray jsonArray3 = obj.getJSONArray("d3");
                        for(int i =0;i<jsonArray3.length(); i++) {
                            JSONObject productObject3 = jsonArray3.getJSONObject(i);
                            valuesList.add((double) productObject3.getDouble("t3_amount"));
                        }

                        JSONArray jsonArray4 = obj.getJSONArray("d4");
                        for(int i =0;i<jsonArray4.length(); i++) {
                            JSONObject productObject4 = jsonArray4.getJSONObject(i);
                            valuesList.add((double) productObject4.getDouble("t4_amount"));
                        }


                        JSONArray jsonArray5 = obj.getJSONArray("d5");
                        for(int i =0;i<jsonArray5.length(); i++) {
                            JSONObject productObject5 = jsonArray5.getJSONObject(i);
                            valuesList.add((double) productObject5.getDouble("t5_amount"));
                        }

                        JSONArray jsonArray6 = obj.getJSONArray("d6");
                        for(int i =0;i<jsonArray6.length(); i++) {
                            JSONObject productObject6 = jsonArray6.getJSONObject(i);
                            valuesList.add((double) productObject6.getDouble("t6_amount"));
                        }

                        JSONArray jsonArray7 = obj.getJSONArray("d7");
                        for(int i =0;i<jsonArray7.length(); i++) {
                            JSONObject productObject7 = jsonArray7.getJSONObject(i);
                            valuesList.add((double) productObject7.getDouble("t7_amount"));
                        }
//                        valuesList.add((double)1600);
//                        valuesList.add((double)500);
//                        valuesList.add((double)1500);
//                        valuesList.add((double)900);
//                        valuesList.add((double)1700);
//                        valuesList.add((double)400);
//                        valuesList.add((double)2000);
//                        valuesList.add((double)2500);
//                        valuesList.add((double)3500);
//                        valuesList.add((double)3000);
//                        valuesList.add((double)3000);
//                        valuesList.add((double)1800);

                        //prepare Bar Entries
                        ArrayList<BarEntry> entries = new ArrayList<>();
                        for (int i = 0; i < valuesList.size(); i++) {
                            BarEntry barEntry = new BarEntry(i+1, valuesList.get(i).floatValue()); //start always from x=1 for the first bar
                            entries.add(barEntry);
                        }

                        //initialize x Axis Labels (labels for 13 vertical grid lines)
                        final ArrayList<String> xAxisLabel = new ArrayList<>();


                        JSONArray jsonArraya = obj.getJSONArray("d1");
                        for(int i =0;i<jsonArraya.length(); i++) {
                            JSONObject productObjecta = jsonArraya.getJSONObject(i);
                            xAxisLabel.add(productObjecta.getString("t1_format"));
                        }

                        JSONArray jsonArraya2 = obj.getJSONArray("d2");
                        for(int i =0;i<jsonArraya2.length(); i++) {
                            JSONObject productObjecta2 = jsonArraya2.getJSONObject(i);
                            xAxisLabel.add(productObjecta2.getString("t2_format"));
                        }

                        JSONArray jsonArraya3 = obj.getJSONArray("d3");
                        for(int i =0;i<jsonArraya3.length(); i++) {
                            JSONObject productObjecta3 = jsonArraya3.getJSONObject(i);
                            xAxisLabel.add(productObjecta3.getString("t3_format"));
                        }

                        JSONArray jsonArraya4 = obj.getJSONArray("d4");
                        for(int i =0;i<jsonArraya4.length(); i++) {
                            JSONObject productObjecta4 = jsonArraya4.getJSONObject(i);
                            xAxisLabel.add(productObjecta4.getString("t4_format"));
                        }


                        JSONArray jsonArraya5 = obj.getJSONArray("d5");
                        for(int i =0;i<jsonArraya5.length(); i++) {
                            JSONObject productObjecta5 = jsonArraya5.getJSONObject(i);
                            xAxisLabel.add(productObjecta5.getString("t5_format"));
                        }

                        JSONArray jsonArraya6 = obj.getJSONArray("d6");
                        for(int i =0;i<jsonArraya6.length(); i++) {
                            JSONObject productObjecta6 = jsonArraya6.getJSONObject(i);
                            xAxisLabel.add(productObjecta6.getString("t6_format"));
                        }

                        JSONArray jsonArraya7 = obj.getJSONArray("d7");
                        for(int i =0;i<jsonArraya7.length(); i++) {
                            JSONObject productObjecta7 = jsonArraya7.getJSONObject(i);
                            xAxisLabel.add(productObjecta7.getString("t7_format"));
                        }




//                        xAxisLabel.add("J"); //this label will be mapped to the 1st index of the valuesList
//                        xAxisLabel.add("F");
//                        xAxisLabel.add("M");
//                        xAxisLabel.add("A");
//                        xAxisLabel.add("M");
//                        xAxisLabel.add("J");
//                        xAxisLabel.add("J");
//                        xAxisLabel.add("A");
//                        xAxisLabel.add("S");
//                        xAxisLabel.add("O");
//                        xAxisLabel.add("N");
//                        xAxisLabel.add("D");
                        xAxisLabel.add(""); //empty label for the last vertical grid line on Y-Right Axis

                        //initialize xAxis
                        XAxis xAxis = mBarChart.getXAxis();
                        xAxis.enableGridDashedLine(10f, 10f, 0f);
                        xAxis.setTextColor(Color.BLACK);
                        xAxis.setTextSize(5);
                        xAxis.setDrawAxisLine(true);
                        xAxis.setAxisLineColor(Color.BLACK);
                        xAxis.setDrawGridLines(true);
                        xAxis.setGranularity(1f);
                        xAxis.setGranularityEnabled(true);
                        xAxis.setAxisMinimum(0 + 0.5f); //to center the bars inside the vertical grid lines we need + 0.5 step
                        xAxis.setAxisMaximum(entries.size() + 0.5f); //to center the bars inside the vertical grid lines we need + 0.5 step
                        xAxis.setLabelCount(xAxisLabel.size(), true); //draw x labels for 13 vertical grid lines
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setXOffset(0f); //labels x offset in dps
                        xAxis.setYOffset(0f); //labels y offset in dps
                        xAxis.setCenterAxisLabels(true);

                        xAxis.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getFormattedValue(float value) {
                                return xAxisLabel.get((int) value);
                            }
                        });

                        //initialize Y-Right-Axis
                        YAxis rightAxis = mBarChart.getAxisRight();
                        rightAxis.setTextColor(Color.BLACK);
                        rightAxis.setTextSize(14);
                        rightAxis.setDrawAxisLine(true);
                        rightAxis.setAxisLineColor(Color.BLACK);
                        rightAxis.setDrawGridLines(true);
                        rightAxis.setGranularity(1f);
                        rightAxis.setGranularityEnabled(true);
                        rightAxis.setAxisMinimum(0);
                        rightAxis.setAxisMaximum(6000f);
                        rightAxis.setLabelCount(4, true); //draw y labels (Y-Values) for 4 horizontal grid lines starting from 0 to 6000f (step=2000)
                        rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

                        //initialize Y-Left-Axis
                        YAxis leftAxis = mBarChart.getAxisLeft();
                        leftAxis.setAxisMinimum(0);
                        leftAxis.setDrawAxisLine(true);
                        leftAxis.setLabelCount(0, true);
                        leftAxis.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getFormattedValue(float value) {
                                return "";
                            }
                        });

                        //set the BarDataSet
                        BarDataSet barDataSet = new BarDataSet(entries, "Months");
                        barDataSet.setColor(Color.parseColor("#D9FFC9"));
                        barDataSet.setFormSize(15f);
                        barDataSet.setDrawValues(false);
                        barDataSet.setValueTextSize(12f);

                        //set the BarData to chart
                        BarData data = new BarData(barDataSet);
                        mBarChart.setData(data);
                        mBarChart.setScaleEnabled(false);
                        mBarChart.getLegend().setEnabled(false);
                        mBarChart.setDrawBarShadow(false);
                        mBarChart.getDescription().setEnabled(false);
                        mBarChart.setPinchZoom(false);
                        mBarChart.setDrawGridBackground(true);
                        mBarChart.invalidate();


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
        queue2.add(request);






    }

    public void todayearningvalue(String riderid) {


        String URL = "https://www.troopa.org/api/weekly-earning/"+riderid;

        queue2 = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);

                    // Toast.makeText(introMap.this,obj.getString("error"), Toast.LENGTH_LONG).show();
                    if (obj.getString("error").equals("false")) {

                        JSONArray jsonArray = obj.getJSONArray("d1");
                        for(int i =0;i<jsonArray.length(); i++) {
                            JSONObject productObject = jsonArray.getJSONObject(i);
                            todayearning.setText(productObject.getString("t1_amount_format"));
                            // valuesList.add((double) productObject.getDouble("t1_amount"));
                        }

                        //Toast.makeText(introMap.this,obj.getString("updated"), Toast.LENGTH_LONG).show();


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
        queue2.add(request);


    }

}