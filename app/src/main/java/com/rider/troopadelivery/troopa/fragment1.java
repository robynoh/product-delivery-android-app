package com.rider.troopadelivery.troopa;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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


public class fragment1 extends Fragment {
    ArrayList<fragment1_all_confirmed> arrayList;
    ListView lv;
    RequestQueue queue;
    LinearLayout cash_detail;
    String riderid;
    fragment1_adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment1, container, false);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        arrayList = new ArrayList<>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myKey", MODE_PRIVATE);
        riderid = sharedPreferences.getString("pilotID", "");

       // Toast.makeText(getContext(), riderid, Toast.LENGTH_LONG).show();
        pull_all_confirmed_order(riderid);

        lv = view.findViewById(R.id.listView5);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){

                // ListView Clicked item index

                String clientname = arrayList.get(position).clientname();
                String pickup_location = arrayList.get(position).pickup_location();
                String dropoff_location= arrayList.get(position).dropoff_location();
               // String status = arrayList.get(position).payment_status();
                String payment_mode = arrayList.get(position).payment_mode();
                String day= arrayList.get(position).request_date();
                String time= arrayList.get(position).request_time();
                String trip_serial= arrayList.get(position).getserial();
                String trip_cost = arrayList.get(position).amount();

               // Toast.makeText(getContext(),pickup_location, Toast.LENGTH_LONG).show();



                Intent intent = new Intent(getContext(),confirmed_order_request.class);
                intent.putExtra("clientname", clientname);
                intent.putExtra("trip_cost", trip_cost);
                intent.putExtra("request_serial",  trip_serial);
                intent.putExtra("pickup_location", pickup_location);
                intent.putExtra("dropoff_location", dropoff_location);
               // intent.putExtra("payment_status", status);
                intent.putExtra("payment_mode",payment_mode);
                intent.putExtra("day", day);
                intent.putExtra("time", time);
                startActivity(intent);



            }
        });






        // fabs.setOnClickListener(
        //       new View.OnClickListener() {
        //  @Override
        //       public void onClick(View view) {
        //          Intent i = new Intent(getActivity(), today_match.class);
        //         startActivity(i);
        //      }
        //   });






    }

    private void pull_all_confirmed_order(String riderid) {



        // Building the url to the web service
        String URL = "https://www.troopa.org/api/pull-confirmed-trip/"+riderid;



        queue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject obj = null;

                try {
                    obj = new JSONObject(response);



                    JSONArray jsonArray = obj.getJSONArray("requests");
                    for(int i =0;i<jsonArray.length(); i++){
                        JSONObject productObject = jsonArray.getJSONObject(i);
                        arrayList.add(new fragment1_all_confirmed (
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
                                productObject.getString("amount"),
                                productObject.getString("request_date"),
                                productObject.getString("request_time")

                        ));
                    }


                    adapter = new   fragment1_adapter(getContext(),R.layout.activity_confirmed_order_request, arrayList);
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


}