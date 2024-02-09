package com.rider.troopadelivery.troopa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "GeofenceBroadcastReceiv";

    String orderID,type;

    Context ctx;
    RequestQueue queue;
    private RequestQueue mRequestQueue;


    @Override
    public void onReceive(Context context, Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.e(TAG, "GeofencingEvent error: " + geofencingEvent.getErrorCode());
            return;
        }

        mRequestQueue = Volley.newRequestQueue(context);
        orderID = intent.getStringExtra("orderId");
        type = intent.getStringExtra("type");

        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            // User entered a geofence
            String requestId = geofencingEvent.getTriggeringGeofences().get(0).getRequestId();
           // Toast.makeText(context,type, Toast.LENGTH_SHORT).show();


            sendDeliveryAproachAlert(orderID,type);

        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
            // User exited a geofence
            String requestId = geofencingEvent.getTriggeringGeofences().get(0).getRequestId();
           // Toast.makeText(context,type, Toast.LENGTH_SHORT).show();

           sendDeliveryAproachAlert(orderID,type);
        }
    }

    public void sendDeliveryAproachAlert(String orderid,String type) {





        String url = "https://www.troopa.org/api/delivery-approach-alert/"+orderid+"/"+type;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(response);


                            if (obj.getString("status").equals("ok")) {




                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
            }
        });

// Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);

//        queue = Volley.newRequestQueue(ctx);
//        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                JSONObject obj = null;
//                try {
//                    obj = new JSONObject(response);
//
//
//                    if (obj.getString("status").equals("ok")) {
//
//
//
//
//                    } else {
//
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("error", error.toString());
//            }
//        });
//        queue.add(request);
//

    }
}
