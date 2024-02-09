package com.rider.troopadelivery.troopa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class client_detail extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private static final String GEOFENCE_ID = "GEOFENCE_ID";
    private static final double GEOFENCE_RADIUS = 100; // in meters
    private static final long GEOFENCE_EXPIRATION = 24 * 60 * 60 * 1000; // in milliseconds

    private GeofencingClient geofencingClient;
    private GeofenceHelper geofenceHelper;


    Button firstbtn, arrivedbtn, startdelbtn, arrivedelbtn1, arrivedelbtn2, confirmdelbtn;

    Button firstbtnb, arrivedbtnb, startdelbtnb, arrivedelbtn1b, arrivedelbtn2b, confirmdelbtnb;

    ImageButton a1, a2, b1, b2, c1, c2, d1, d2, helpboxb, helpboxb2;
    TextView allLocation, location_type,ratingHeading,ratingHeading2;

    LatLng latLng2;

    LatLng latLng;
    String payment_type;

    String dropoffDistance;

    ImageButton opendetail, openinfo, tripcontact,hideprogressBoard;
    LinearLayout cancelled_trip, closedetail, pickdesti, openinfo2, pickup_paya, pickup_paya1, pay_boardnew, pickup_payab, pickup_paya1b, pay_boardnewb;
    private BottomSheetDialog bottomSheetDialog;
    private Polyline currentPolyline;
    TextView dashduration;

    String dropoff_location;

    private DatabaseReference databaseReference;
    private final int FIVE_SECONDS = 5000;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    RequestQueue queue;
    RequestQueue queue2;
    private GoogleMap mMap;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 5000;
    private final long MIN_DIST = 5;
    private double lng = 7.491302;
    private double lat = 9.072264;
    String pickup_location = "";
    String riderphone = "";
    SupportMapFragment mapFragment;

    String amount;

    String mapTitle = "";
    String order_id = "";
    LinearLayout delivery_start;
    LinearLayout delivery_start_gray;
    LinearLayout arivaltxt;
    LinearLayout start_delivery_board, pay_board, paid_pad, paid_pad2;
    String riderid;
    Button arrived_at_pickup, recieved_monry, deliveredforcode;
    String pickup_contact_name;
    String dropoff_contact_name;
    LinearLayout call_pick_up;
    LinearLayout call_drop_off;

    TextView duration_map, cash, cash2;
    TextView arrtxt;
    ImageView pin1a, pin1, pin3, pin3a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_detail);
        // EventBus.getDefault().register(this);


        // Get the SupportMapFragment and request notification when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        geofencingClient = LocationServices.getGeofencingClient(this);
        geofenceHelper = new GeofenceHelper(this);


//        MapsInitializer.initialize(this);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Trip Detail");


        getSupportActionBar().show();


        Intent intent = getIntent();
        String client_name = getIntent().getStringExtra("client_name");
        String client_id = getIntent().getStringExtra("client_id");
        pickup_location = getIntent().getStringExtra("pickup_location");
        dropoff_location = getIntent().getStringExtra("dropoff_location");
        String duration = getIntent().getStringExtra("duration");
        String distance = getIntent().getStringExtra("distance");
        String pickup_contact = getIntent().getStringExtra("pickup_contact");
        String dropoff_contact = getIntent().getStringExtra("dropoff_contact");
        String picture = getIntent().getStringExtra("picture");
        dropoff_contact_name = getIntent().getStringExtra("dropoff_contact_name");
        amount = getIntent().getStringExtra("amount");
        payment_type = getIntent().getStringExtra("payment_type");
        String payment_mode = getIntent().getStringExtra("payment_mode");
        pickup_contact_name = getIntent().getStringExtra("pickup_contact_name");
        order_id = getIntent().getStringExtra("order_id");
        riderid = getIntent().getStringExtra("riderid");

      //  Toast.makeText(client_detail.this, "amount:"+getIntent().getStringExtra("amount")+"riderid:"+getIntent().getStringExtra("riderid"), Toast.LENGTH_LONG).show();


        // Toast.makeText(client_detail.this, payment_mode, Toast.LENGTH_LONG).show();

//        lat= Double.parseDouble(getIntent().getStringExtra("longitude"));
//        lng= Double.parseDouble(getIntent().getStringExtra("latitude"));


        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);

        riderphone = sharedPreferences.getString("phone", "");
        mapTitle = sharedPreferences.getString("name", "");


        /////////////////////////////////////////////////////// up date this pickup request as accepted ////////


        opendetail = findViewById(R.id.opendetail);
        pickdesti = findViewById(R.id.pickdesti);

        bottomSheetDialog = new BottomSheetDialog(client_detail.this, R.style.BottomSheetTheme);
        View sheetviewx = LayoutInflater.from(getApplicationContext()).inflate(R.layout.client_route_detail, null);
        bottomSheetDialog.setContentView(sheetviewx);
        duration_map = sheetviewx.findViewById(R.id.duration_map);

        arrived_at_pickup = sheetviewx.findViewById(R.id.arrived_at_pickup);

        deliveredforcode = sheetviewx.findViewById(R.id.deliveredforcode);

        cancelled_trip = sheetviewx.findViewById(R.id.cancelledTrip);
        ratingHeading = sheetviewx.findViewById(R.id.ratingHeading);

        ratingHeading2 = findViewById(R.id.ratingHeading2);
        hideprogressBoard=sheetviewx.findViewById(R.id.hideprogressBoard);
        //delivery_start_gray=sheetviewx.findViewById(R.id.delivery_start_gray);

        delivery_start = sheetviewx.findViewById(R.id.delivery_start);

        helpboxb = sheetviewx.findViewById(R.id.helpboxb);
        helpboxb2 = sheetviewx.findViewById(R.id.helpboxb2);

        hideprogressBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.hide();
            }
        });

        helpboxb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(client_detail.this, help_center.class);
                startActivity(i);
            }
        });

        helpboxb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(client_detail.this, help_center.class);
                startActivity(i);
            }
        });


//        recieved_monry=sheetviewx.findViewById(R.id.recieved_monry);

        paid_pad = sheetviewx.findViewById(R.id.paid_pad);
        paid_pad2 = sheetviewx.findViewById(R.id.paid_pad2);

        arivaltxt = sheetviewx.findViewById(R.id.arivaltxt);

        cash = sheetviewx.findViewById(R.id.cash);

        cash2 = sheetviewx.findViewById(R.id.cash2);

        arrtxt = sheetviewx.findViewById(R.id.arrtxt);

//        recieved_monry.setText("Recieved N"+ amount +" from customer");

        cash.setText("N" + amount);

        cash2.setText("N" + amount);

        bottomSheetDialog.show();
        TextView clientnamexxx = sheetviewx.findViewById(R.id.clientname);


        //durationtext.setText(duration);
        clientnamexxx.setText(client_name);

        pin1 = sheetviewx.findViewById(R.id.pin1);
        pin1a = sheetviewx.findViewById(R.id.pin1a);
        pin3 = sheetviewx.findViewById(R.id.pin3);
        pin3a = sheetviewx.findViewById(R.id.pin3a);


        try {
            trackProcess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // ifCancelledTrip();
try {
    initiateMap();
} catch (Exception e) {
    e.printStackTrace();
}

        //Toast.makeText(client_detail.this, dropoffDistance, Toast.LENGTH_LONG).show();
        openinfo2 = sheetviewx.findViewById(R.id.openinfo2);

        openinfo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(client_detail.this, help_center.class);
                startActivity(i);
            }
        });

        start_delivery_board = sheetviewx.findViewById(R.id.start_delivery_board);
        pay_board = sheetviewx.findViewById(R.id.pay_board);


        allLocation = sheetviewx.findViewById(R.id.allLocation);

        firstbtn = sheetviewx.findViewById(R.id.firstbtn);
        arrivedbtn = sheetviewx.findViewById(R.id.arrivedbtn);

        startdelbtn = sheetviewx.findViewById(R.id.startdelbtn);
        arrivedelbtn2 = sheetviewx.findViewById(R.id.arrivedelbtn2);

        arrivedelbtn1 = sheetviewx.findViewById(R.id.arrivedelbtn1);
        confirmdelbtn = sheetviewx.findViewById(R.id.confirmdelbtn);

        pickup_paya = sheetviewx.findViewById(R.id.pickup_paya);

        pickup_paya1 = sheetviewx.findViewById(R.id.pickup_paya1);

        pay_boardnew = sheetviewx.findViewById(R.id.pay_boardnew);

        location_type = sheetviewx.findViewById(R.id.location_type);


        firstbtnb = sheetviewx.findViewById(R.id.firstbtnb);
        arrivedbtnb = sheetviewx.findViewById(R.id.arrivedbtnb);

        startdelbtnb = sheetviewx.findViewById(R.id.startdelbtnb);
        arrivedelbtn2b = sheetviewx.findViewById(R.id.arrivedelbtn2b);

        arrivedelbtn1b = sheetviewx.findViewById(R.id.arrivedelbtn1b);
        confirmdelbtnb = sheetviewx.findViewById(R.id.confirmdelbtnb);

        pickup_payab = sheetviewx.findViewById(R.id.pickup_payab);

        pickup_paya1b = sheetviewx.findViewById(R.id.pickup_paya1b);

        pay_boardnewb = sheetviewx.findViewById(R.id.pay_boardnewb);

        a1 = sheetviewx.findViewById(R.id.a1);
        a2 = sheetviewx.findViewById(R.id.a2);

        b1 = sheetviewx.findViewById(R.id.b1);
        b2 = sheetviewx.findViewById(R.id.b2);

        c1 = sheetviewx.findViewById(R.id.c1);
        c2 = sheetviewx.findViewById(R.id.c2);

        d1 = sheetviewx.findViewById(R.id.d1);
        d2 = sheetviewx.findViewById(R.id.d2);

        dashduration = findViewById(R.id.dashduration);

        openinfo = findViewById(R.id.openinfo);

        openinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(client_detail.this, help_center.class);
                startActivity(i);
            }
        });


        arrivedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendArriveAlert(order_id);
            }
        });

        arrivedbtnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendArriveAlertb(order_id);
            }
        });


        startdelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startDeliveryAlert(order_id);

            }
        });

        startdelbtnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startDeliveryAlertb(order_id);

            }
        });


        arrivedelbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                arrivedDeliveryAlert(order_id);

            }
        });


        arrivedelbtn2b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                arrivedDeliveryAlertb(order_id);

            }
        });

        confirmdelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(client_detail.this, delivery_code.class);
                i.putExtra("client_id", client_id);
                i.putExtra("rider_id", riderid);
                i.putExtra("amount", amount);
                i.putExtra("order_id", order_id);
                i.putExtra("dropoff_contact_name", dropoff_contact_name);
                i.putExtra("dropoff_contact", dropoff_contact);
                i.putExtra("payment_mode", payment_mode);
                startActivity(i);
                finish();
            }
        });

        confirmdelbtnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(client_detail.this, delivery_code.class);
                i.putExtra("client_id", client_id);
                i.putExtra("rider_id", riderid);
                i.putExtra("amount", amount);
                i.putExtra("order_id", order_id);
                i.putExtra("dropoff_contact_name", dropoff_contact_name);
                i.putExtra("dropoff_contact", dropoff_contact);
                i.putExtra("payment_mode", payment_mode);
                startActivity(i);

            }
        });


        // Toast.makeText(client_detail.this, payment_type, Toast.LENGTH_LONG).show();

        if (payment_type.equals("pickup location")) {

            pickup_payab.setVisibility(View.GONE);
            pickup_paya.setVisibility(View.VISIBLE);

        }
        if (payment_type.equals("dropoff location")) {

            pickup_paya.setVisibility(View.GONE);
            pickup_payab.setVisibility(View.VISIBLE);

        }

        opendetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                closedetail = sheetviewx.findViewById(R.id.closedetail);

                //TextView durationtext =sheetviewx.findViewById(R.id.duration);
                TextView clientname = sheetviewx.findViewById(R.id.clientname);


                //durationtext.setText(duration);
                clientname.setText(client_name);


                closedetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.hide();
                    }
                });

                tripcontact = sheetviewx.findViewById(R.id.tripcontact);
                tripcontact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(client_detail.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.contact_dialogue, viewGroup, false);
                        builder.setView(dialogView);
                        AlertDialog alertDialog = builder.create();

                        call_pick_up = dialogView.findViewById(R.id.call_pick_up);
                        call_drop_off = dialogView.findViewById(R.id.call_drop_off);
                        TextView pickupname = dialogView.findViewById(R.id.pickupname);
                        pickupname.setText(pickup_contact_name);

                        TextView dropoffname = dialogView.findViewById(R.id.dropoffname);

                        dropoffname.setText(dropoff_contact_name);


                        call_pick_up.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String number = pickup_contact;
                                Uri call = Uri.parse("tel:" + number);
                                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                                startActivity(surf);

                            }
                        });

                        call_drop_off.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String number = dropoff_contact;
                                Uri call = Uri.parse("tel:" + number);
                                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                                startActivity(surf);

                            }
                        });


                        alertDialog.show();
                    }
                });

                try {
                    bottomSheetDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }


//    @Override
//    public void onResume(){
//        super.onResume();
//        // put your code here...
//        ifCancelledTrip();
//
//    }


    public boolean onSupportNavigateUp() {


        onBackPressed();
        return true;
    }




//    private void setUpMap() {
//
//
//        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker").snippet("Snippet"));
//    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(latLng).title(" you are currently here").icon(BitmapDescriptorFactory.fromResource(R.drawable.gmarker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        // to remove old markers


    }



    public void writeNewRider(String riderid, String mapTitle, Double longitude, Double latitude, String phone, String online, String availability, String state, String city, String address) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("RiderLocation");
        RiderLocation riderLocation = new RiderLocation(riderid, mapTitle, longitude, latitude, phone, online, availability, state, city, address);

        databaseReference.child(phone).setValue(riderLocation);


    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng LatLan = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            LatLan = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return LatLan;
    }


    private String getUrl(LatLng origin, LatLng dest) {


        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        String key = "key=AIzaSyCGPDz4LFZeTDm_nSFZVpqVmRvaNP6UpF8";

//                key=AIzaSyARVQnTNKvSr3d9qyGIjRqgqFhrhXlyMPc
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&" + key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String URL = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return URL;
    }


    private void getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        String key = "key=AIzaSyCGPDz4LFZeTDm_nSFZVpqVmRvaNP6UpF8";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&" + key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String URL = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject obj = null;

                try {
                    obj = new JSONObject(response);
                    // JSONArray jRoutes = obj.getJSONArray("routes");
                    JSONObject route = new JSONObject(response);
                    JSONArray legs = route.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");
                    // JSONArray legs =  obj.getJSONArray("legs");
                    for (int i = 0; i < legs.length(); i++) {

                        JSONObject leg = legs.getJSONObject(i);
                        // String distance = leg.getJSONObject("distance").getString("text");
                        String duration = leg.getJSONObject("duration").getString("text");
                        // int distance = leg.getJSONObject("distance").getInt("value");
                        // int duration = leg.getJSONObject("duration").getInt("value");
                        dashduration.setText(duration);
                        duration_map.setText(duration);

                        // Toast.makeText(client_detail.this, duration, Toast.LENGTH_LONG).show();


                    }

                    // for each leg

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);
    }

    public void gotToDirection(String order) {


        String URL = "https://www.troopa.org/api/updatedeliveryprocess/" + order;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (obj.getString("message").equals("performed")) {


                        // Toast.makeText(client_detail.this, "updated", Toast.LENGTH_LONG).show();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);


    }


    public void checkarrival(String order) {


        String URL = "https://www.troopa.org/api/arrivalcheck/" + order;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (obj.getString("error").equals("false")) {

                        if (Integer.parseInt(obj.getString("count")) > 0) {
//                            arrived_at_pickup.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.round_button));

                            // delivery_start_gray.setVisibility(View.GONE);
//                            pin1a.setVisibility(View.VISIBLE);
//                            pin1.setVisibility(View.GONE);

//                            delivery_start.setVisibility(View.VISIBLE);

                            // arivaltxt.setVisibility(View.VISIBLE);
                            // arrtxt.setText(pickup_contact_name+" have been notified of your arrival");

                            // Toast.makeText(client_detail.this,"work now", Toast.LENGTH_LONG).show();


                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);


    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        try {
            currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkpayatdelivery(String order, String riderid) {


        String URL = "https://www.troopa.org/api/payatdeliverycheck/" + order + "/" + riderid;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (obj.getString("error").equals("false")) {

                        // Toast.makeText(client_detail.this, obj.getString("count"), Toast.LENGTH_LONG).show();


                        if (Integer.parseInt(obj.getString("count")) > 0) {

//                            recieved_monry.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.round_button));
//
//                            // delivery_start_gray.setVisibility(View.GONE);
//                            pin3a.setVisibility(View.VISIBLE);
//                            pin3.setVisibility(View.GONE);


                            if (payment_type.equals("pickup location")) {
                                paid_pad.setVisibility(View.VISIBLE);

                            }
                            if (payment_type.equals("dropoff location")) {
                                paid_pad2.setVisibility(View.VISIBLE);
                            }

                            //arivaltxt.setVisibility(View.VISIBLE);
                            // arrtxt.setText(client_name+" have been notified of your arrival");

                            // Toast.makeText(start_delivery.this,Integer.parseInt(obj.getString("count"), Toast.LENGTH_LONG).show();


                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);


    }

    private boolean isLocationWithinGeofence(LatLng location, double Clientlatitude, double Clientlongitude, float radius) {
        // Create the geofence
        Geofence geofence = new Geofence.Builder()
                .setRequestId(GEOFENCE_ID)
                .setCircularRegion(
                        // Geofence center latitude and longitude
                        Clientlatitude,
                        Clientlongitude,
                        // Geofence radius in meters
                        radius)
                .setExpirationDuration(GEOFENCE_EXPIRATION)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build();

        // Check if the location is within the geofence
        float[] distance = new float[1];
        Location.distanceBetween(location.latitude, location.longitude,
                geofence.getLatitude(), geofence.getLongitude(), distance);
        return distance[0] <= geofence.getRadius();
    }


    public void sendDeliveryAproachAlert(String orderid) {


        String URL = "https://www.troopa.org/api/delivery-approach-alert/" + orderid;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);


                    if (obj.getString("status").equals("ok")) {

                        c1.setVisibility(View.GONE);
                        c2.setVisibility(View.VISIBLE);

//                        firstbtn.setVisibility(View.GONE);
//                        arrivedbtn.setVisibility(View.GONE);
//                        startdelbtn.setVisibility(View.GONE);
//                        arrivedelbtn1.setVisibility(View.GONE);
//
//                        arrivedelbtn2.setVisibility(View.VISIBLE);
//                        pay_boardnew.setVisibility(View.VISIBLE);
//
//                        pickup_paya1.setVisibility(View.GONE);
                        // Toast.makeText(introMap.this,"done", Toast.LENGTH_LONG).show();


                        if (payment_type.equals("pickup location")) {
                            firstbtn.setVisibility(View.GONE);
                            arrivedbtn.setVisibility(View.GONE);
                            startdelbtn.setVisibility(View.GONE);
                            arrivedelbtn1.setVisibility(View.GONE);

                            arrivedelbtn2.setVisibility(View.VISIBLE);
                            pay_boardnew.setVisibility(View.VISIBLE);

                            pickup_paya1.setVisibility(View.GONE);
                        }
                        if (payment_type.equals("dropoff location")) {
                            firstbtnb.setVisibility(View.GONE);
                            arrivedbtnb.setVisibility(View.GONE);
                            startdelbtnb.setVisibility(View.GONE);
                            arrivedelbtn1b.setVisibility(View.GONE);

                            arrivedelbtn2b.setVisibility(View.VISIBLE);
                            pay_boardnewb.setVisibility(View.GONE);

                            pickup_paya1b.setVisibility(View.VISIBLE);
                        }


                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);


    }


    public void sendAproachAlert(String orderid) {


        String URL = "https://www.troopa.org/api/approach-alert/" + orderid;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);


                    if (obj.getString("status").equals("ok")) {

                        if (payment_type.equals("pickup location")) {
                            firstbtn.setVisibility(View.GONE);
                            arrivedbtn.setVisibility(View.VISIBLE);

                            pay_boardnew.setVisibility(View.GONE);
                            pickup_paya1.setVisibility(View.VISIBLE);
                        }

                        if (payment_type.equals("dropoff location")) {
                            firstbtnb.setVisibility(View.GONE);
                            arrivedbtnb.setVisibility(View.VISIBLE);

                            pay_boardnewb.setVisibility(View.GONE);
                            pickup_paya1b.setVisibility(View.VISIBLE);
                        }

                        // Toast.makeText(introMap.this,"done", Toast.LENGTH_LONG).show();


                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);


    }

    public void allProcessTracker(String orderid) {


        String URL = "https://www.troopa.org/api/all-process-tracker/" + orderid;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);


                    if (obj.getString("status").equals("ok")) {

                        JSONArray jsonArray = obj.getJSONArray("process");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject productObject = jsonArray.getJSONObject(i);


                            if (productObject.getString("startDelivery").equals("0")) {
                                try {

                                allLocation.setText(pickup_location);
                                location_type.setText("Pickup Address");
                                ratingHeading.setText("To pickup destination");
                                ratingHeading2.setText("To pickup destination");





                                    LatLng latLngPickup = getLocationFromAddress(client_detail.this, pickup_location);

                                   // Toast.makeText(client_detail.this,latLngPickup.latitude+"plus"+latLngPickup.longitude, Toast.LENGTH_LONG).show();


    addGeofencesPickup(latLngPickup.latitude, latLngPickup.longitude, 350);
} catch (Exception e) {
    e.printStackTrace();
}

                            } else if(productObject.getString("startDelivery").equals("1")) {

                                allLocation.setText(dropoff_location);
                                location_type.setText("Dropoff Address");
                                ratingHeading.setText("To drop off destination");
                                ratingHeading2.setText("To drop off destination");

                                try {


                                  //  latLng2 = getLocationFromAddress(client_detail.this, dropoff_location);
                                  //  mMap.addMarker(new MarkerOptions().position(latLng2).title(dropoff_location).icon(BitmapDescriptorFactory.fromResource(R.drawable.packagpoint)));

                                    LatLng latLngDropoff = getLocationFromAddress(client_detail.this, dropoff_location);

                                   addGeofencesDropOff(latLngDropoff.latitude, latLngDropoff.longitude, 400,"1");

                                   addGeofencesDropOff(latLngDropoff.latitude, latLngDropoff.longitude, 250,"2");
                               } catch (Exception e) {
                                   e.printStackTrace();
                               }
                            }


                            if (productObject.getString("arrivePickup").equals("1")) {

                                a1.setVisibility(View.GONE);
                                a2.setVisibility(View.VISIBLE);


                            }

                            if (productObject.getString("startDelivery").equals("1")) {


                                b1.setVisibility(View.GONE);
                                b2.setVisibility(View.VISIBLE);


                            }

                            if (productObject.getString("approachDelivery").equals("1")) {


                                c1.setVisibility(View.GONE);
                                c2.setVisibility(View.VISIBLE);


                            }

                            if (productObject.getString("arriveDelivery").equals("1")) {


                                d1.setVisibility(View.GONE);
                                d2.setVisibility(View.VISIBLE);


                            }

                            if (productObject.getString("approach").equals("1") && productObject.getString("arrivePickup").equals("0")) {
                                if (payment_type.equals("pickup location")) {
                                    firstbtn.setVisibility(View.GONE);
                                    arrivedbtn.setVisibility(View.VISIBLE);

                                    pay_boardnew.setVisibility(View.GONE);
                                    pickup_paya1.setVisibility(View.VISIBLE);
                                }

                                if (payment_type.equals("dropoff location")) {
                                    firstbtnb.setVisibility(View.GONE);
                                    arrivedbtnb.setVisibility(View.VISIBLE);

                                    pay_boardnewb.setVisibility(View.GONE);
                                    pickup_paya1b.setVisibility(View.VISIBLE);
                                }
                            }
                            if (productObject.getString("approach").equals("1") && productObject.getString("arrivePickup").equals("1") && productObject.getString("startDelivery").equals("0")) {

                                if (payment_type.equals("pickup location")) {
                                    firstbtn.setVisibility(View.GONE);
                                    arrivedbtn.setVisibility(View.GONE);
                                    startdelbtn.setVisibility(View.VISIBLE);

                                    pay_boardnew.setVisibility(View.VISIBLE);
                                    pickup_paya1.setVisibility(View.GONE);
                                }
                                if (payment_type.equals("dropoff location")) {
                                    firstbtnb.setVisibility(View.GONE);
                                    arrivedbtnb.setVisibility(View.GONE);
                                    startdelbtnb.setVisibility(View.VISIBLE);

                                    pay_boardnewb.setVisibility(View.GONE);
                                    pickup_paya1b.setVisibility(View.VISIBLE);
                                }
                            }

                            if (productObject.getString("approach").equals("1") && productObject.getString("arrivePickup").equals("1") && productObject.getString("startDelivery").equals("1") && productObject.getString("approachDelivery").equals("0")) {

                                if (payment_type.equals("pickup location")) {
                                    firstbtn.setVisibility(View.GONE);
                                    arrivedbtn.setVisibility(View.GONE);
                                    startdelbtn.setVisibility(View.GONE);

                                    arrivedelbtn1.setVisibility(View.VISIBLE);
                                    pay_boardnew.setVisibility(View.VISIBLE);

                                    pickup_paya1.setVisibility(View.GONE);
                                }
                                if (payment_type.equals("dropoff location")) {
                                    firstbtnb.setVisibility(View.GONE);
                                    arrivedbtnb.setVisibility(View.GONE);
                                    startdelbtnb.setVisibility(View.GONE);

                                    arrivedelbtn1b.setVisibility(View.VISIBLE);
                                    pay_boardnewb.setVisibility(View.GONE);

                                    pickup_paya1b.setVisibility(View.VISIBLE);
                                }

                            }

                            if (productObject.getString("approach").equals("1") && productObject.getString("arrivePickup").equals("1") && productObject.getString("startDelivery").equals("1") && productObject.getString("approachDelivery").equals("1") && productObject.getString("arriveDelivery").equals("0")) {

                                if (payment_type.equals("pickup location")) {
                                    firstbtn.setVisibility(View.GONE);
                                    arrivedbtn.setVisibility(View.GONE);
                                    startdelbtn.setVisibility(View.GONE);
                                    arrivedelbtn1.setVisibility(View.GONE);

                                    arrivedelbtn2.setVisibility(View.VISIBLE);
                                    pay_boardnew.setVisibility(View.VISIBLE);

                                    pickup_paya1.setVisibility(View.GONE);
                                }
                                if (payment_type.equals("dropoff location")) {
                                    firstbtnb.setVisibility(View.GONE);
                                    arrivedbtnb.setVisibility(View.GONE);
                                    startdelbtnb.setVisibility(View.GONE);
                                    arrivedelbtn1b.setVisibility(View.GONE);

                                    arrivedelbtn2b.setVisibility(View.VISIBLE);
                                    pay_boardnewb.setVisibility(View.GONE);

                                    pickup_paya1b.setVisibility(View.VISIBLE);
                                }

                            }

                            if (productObject.getString("approach").equals("1") && productObject.getString("arrivePickup").equals("1") && productObject.getString("startDelivery").equals("1") && productObject.getString("approachDelivery").equals("1") && productObject.getString("arriveDelivery").equals("1")) {

                                if (payment_type.equals("pickup location")) {
                                    firstbtn.setVisibility(View.GONE);
                                    arrivedbtn.setVisibility(View.GONE);
                                    startdelbtn.setVisibility(View.GONE);
                                    arrivedelbtn1.setVisibility(View.GONE);
                                    arrivedelbtn2.setVisibility(View.GONE);
                                    confirmdelbtn.setVisibility(View.VISIBLE);
                                    pay_boardnew.setVisibility(View.VISIBLE);

                                    pickup_paya1.setVisibility(View.GONE);
                                }
                                if (payment_type.equals("dropoff location")) {
                                    firstbtnb.setVisibility(View.GONE);
                                    arrivedbtnb.setVisibility(View.GONE);
                                    startdelbtnb.setVisibility(View.GONE);
                                    arrivedelbtn1b.setVisibility(View.GONE);
                                    arrivedelbtn2b.setVisibility(View.GONE);
                                    confirmdelbtnb.setVisibility(View.VISIBLE);
                                    pay_boardnewb.setVisibility(View.VISIBLE);

                                    pickup_paya1b.setVisibility(View.GONE);
                                }

                            }

                        }


                        // Toast.makeText(introMap.this,"done", Toast.LENGTH_LONG).show();


                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);


    }


    public void sendArriveAlert(String orderid) {


        String URL = "https://www.troopa.org/api/arrive-alert/" + orderid;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);


                    if (obj.getString("status").equals("ok")) {

                        a1.setVisibility(View.GONE);
                        a2.setVisibility(View.VISIBLE);


                        firstbtn.setVisibility(View.GONE);
                        arrivedbtn.setVisibility(View.GONE);
                        startdelbtn.setVisibility(View.VISIBLE);

                        pay_boardnew.setVisibility(View.VISIBLE);
                        pickup_paya1.setVisibility(View.GONE);


                        //startdelbtn.setVisibility(View.VISIBLE);

                        // Toast.makeText(introMap.this,"done", Toast.LENGTH_LONG).show();


                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);


    }


    public void sendArriveAlertb(String orderid) {


        String URL = "https://www.troopa.org/api/arrive-alert/" + orderid;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);


                    if (obj.getString("status").equals("ok")) {

                        a1.setVisibility(View.GONE);
                        a2.setVisibility(View.VISIBLE);


                        firstbtnb.setVisibility(View.GONE);
                        arrivedbtnb.setVisibility(View.GONE);
                        startdelbtnb.setVisibility(View.VISIBLE);

                        // pay_boardnewb.setVisibility(View.VISIBLE);
                        pickup_paya1b.setVisibility(View.VISIBLE);


                        //startdelbtn.setVisibility(View.VISIBLE);

                        // Toast.makeText(introMap.this,"done", Toast.LENGTH_LONG).show();


                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);


    }


    public void startDeliveryAlert(String orderid) {


        String URL = "https://www.troopa.org/api/start-delivery-alert/" + orderid;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);


                    if (obj.getString("status").equals("ok")) {

                        b1.setVisibility(View.GONE);
                        b2.setVisibility(View.VISIBLE);

                        arrivedelbtn1.setVisibility(View.VISIBLE);
                        startdelbtn.setVisibility(View.GONE);

                        allLocation.setText(dropoff_location);
                        location_type.setText("Dropoff Address");

                        // Toast.makeText(introMap.this,"done", Toast.LENGTH_LONG).show();


                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);


    }


    public void startDeliveryAlertb(String orderid) {


        String URL = "https://www.troopa.org/api/start-delivery-alert/" + orderid;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);


                    if (obj.getString("status").equals("ok")) {

                        b1.setVisibility(View.GONE);
                        b2.setVisibility(View.VISIBLE);

                        arrivedelbtn1b.setVisibility(View.VISIBLE);
                        startdelbtnb.setVisibility(View.GONE);
                        // Toast.makeText(introMap.this,"done", Toast.LENGTH_LONG).show();

                        allLocation.setText(dropoff_location);
                        location_type.setText("Dropoff Address");


                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);


    }


    public void arrivedDeliveryAlert(String orderid) {


        String URL = "https://www.troopa.org/api/arrive-delivery-alert/" + orderid;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);


                    if (obj.getString("status").equals("ok")) {

                        d1.setVisibility(View.GONE);
                        d2.setVisibility(View.VISIBLE);


                        firstbtn.setVisibility(View.GONE);
                        arrivedbtn.setVisibility(View.GONE);
                        startdelbtn.setVisibility(View.GONE);
                        arrivedelbtn1.setVisibility(View.GONE);
                        arrivedelbtn2.setVisibility(View.GONE);
                        confirmdelbtn.setVisibility(View.VISIBLE);
                        pay_boardnew.setVisibility(View.VISIBLE);

                        pickup_paya1.setVisibility(View.GONE);

                        // Toast.makeText(introMap.this,"done", Toast.LENGTH_LONG).show();


                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);


    }


    public void arrivedDeliveryAlertb(String orderid) {


        String URL = "https://www.troopa.org/api/arrive-delivery-alert/" + orderid;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);


                    if (obj.getString("status").equals("ok")) {

                        d1.setVisibility(View.GONE);
                        d2.setVisibility(View.VISIBLE);


                        firstbtnb.setVisibility(View.GONE);
                        arrivedbtnb.setVisibility(View.GONE);
                        startdelbtnb.setVisibility(View.GONE);
                        arrivedelbtn1b.setVisibility(View.GONE);
                        arrivedelbtn2b.setVisibility(View.GONE);
                        confirmdelbtnb.setVisibility(View.VISIBLE);
                        pay_boardnewb.setVisibility(View.VISIBLE);

                        pickup_paya1b.setVisibility(View.GONE);

                        // Toast.makeText(introMap.this,"done", Toast.LENGTH_LONG).show();


                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);


    }


    public void initiateMap() {

                        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference ref = database.child("RiderLocation");

                        Query phoneQuery = ref.orderByChild("phone").equalTo(riderphone);


                        phoneQuery.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if(dataSnapshot.exists()) {

                                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                                        RiderLocation rider = singleSnapshot.getValue(RiderLocation.class);
                                        if (String.valueOf(rider.latitude) != null || String.valueOf(rider.longitude) != null) {
                                            // Toast.makeText(.this, rider.longitude+"-"+rider.latitude, Toast.LENGTH_LONG).show();
                                            mMap.clear();


                                            String URL = "https://www.troopa.org/api/all-process-tracker/"+order_id;
                                            queue = Volley.newRequestQueue(client_detail.this);
                                            StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {

                                                    JSONObject obj = null;
                                                    try {
                                                        obj = new JSONObject(response);


                                                        if (obj.getString("status").equals("ok")) {


                                                            JSONArray jsonArray = obj.getJSONArray("process");
                                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                                JSONObject productObject = jsonArray.getJSONObject(i);


                                                                if (productObject.getString("startDelivery").equals("0")) {


                                                                    latLng2 = getLocationFromAddress(client_detail.this, pickup_location);



                                                                } else if(productObject.getString("startDelivery").equals("1")) {


                                                                    latLng2 = getLocationFromAddress(client_detail.this, pickup_location);

                                                                }




                                                            }



                                                        } else {


                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }


                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Log.d("error", error.toString());
                                                }
                                            });
                                            queue.add(request);









                                            LatLng latLng = new LatLng(rider.latitude, rider.longitude);
                                            try {
                                                new FetchURL(client_detail.this).execute(getUrl(latLng, latLng2), "driving");

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

//                                            if (latLng != null) {
//                                                mMap.addPolyline((new PolylineOptions()).add(latLng, latLng2)
//                                                                // below line is use to specify the width of poly line.
//                                                                .width(10)
//                                                                // below line is use to add color to our poly line.
//                                                                .color(Color.RED)
//                                                        // below line is to make our poly line geodesic.
//                                                        // .geodesic(true)
//                                                );
//                                            }

                                            try {
                                                CircleOptions circleOptions = new CircleOptions()
                                                        .center(latLng)   //set center
                                                        .radius(70)   //set radius in meters
                                                        .fillColor(Color.parseColor("#FFB973"))  //default
                                                        .strokeColor(Color.TRANSPARENT)
                                                        .strokeWidth(5);

                                                Circle myCircle = mMap.addCircle(circleOptions);

                                                mMap.addMarker(new MarkerOptions().position(latLng).title(" you are currently here").icon(BitmapDescriptorFactory.fromResource(R.drawable.gmarker)));
                                                mMap.addMarker(new MarkerOptions().position(latLng2).title(pickup_location).icon(BitmapDescriptorFactory.fromResource(R.drawable.packagpoint)));
                                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            try {
                                                getDirectionsUrl(latLng, latLng2);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                            try {
                                                checkarrival(order_id);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            try {
                                                checkpayatdelivery(order_id, riderid);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            try {
                                              //  new FetchURL(client_detail.this).execute(getUrl(latLng, latLng2), "driving");

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            //  gotToDirection(latLng,latLng2,order_id);




                                        }

                                    }
                                }



                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Log.e(TAG, "onCancelled", databaseError.toException());
                            }
                        });



                        // make operation on the UI - for example
                        // on a progress bar.


//
//                        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//                        DatabaseReference ref = database.child("RiderLocation");
//
//                        Query phoneQuery = ref.orderByChild("phone").equalTo(riderphone);
//
//
//                        phoneQuery.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                if (dataSnapshot.exists()) {
//
//                                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//                                        RiderLocation rider = singleSnapshot.getValue(RiderLocation.class);
//                                        latLng = new LatLng(rider.latitude, rider.longitude);
//
//                                        mMap.clear();
//
//                                        CircleOptions circleOptions = new CircleOptions()
//                                                .center(latLng)   //set center
//                                                .radius(170)   //set radius in meters
//                                                .fillColor(Color.parseColor("#FFB973"))  //default
//                                                .strokeColor(Color.TRANSPARENT)
//                                                .strokeWidth(5);
//
//                                        Circle myCircle = mMap.addCircle(circleOptions);
//                                        try {
//                                            mMap.addMarker(new MarkerOptions().position(latLng).title(" you are currently here").icon(BitmapDescriptorFactory.fromResource(R.drawable.gmarker)));
//                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//
//                                            latLng2 = getLocationFromAddress(client_detail.this, pickup_location);
//                                            mMap.addMarker(new MarkerOptions().position(latLng2).title(pickup_location).icon(BitmapDescriptorFactory.fromResource(R.drawable.packagpoint)));
//
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//
//
//
//                                        try {
//                                            checkarrival(order_id);
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//
//                                        try {
//                                            checkpayatdelivery(order_id, riderid);
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//
//
//
//
//
//                                    }
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                                // Log.e(TAG, "onCancelled", databaseError.toException());
//                            }
//                        });
//
//
//
//
//                        try {
//
//                            new FetchURL(client_detail.this).execute(getUrl(latLng, latLng2), "driving");
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                        try {
//                            getDirectionsUrl(latLng, latLng2);
//                            ifCancelledTrips(order_id);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }




    }

    public void trackProcess() {


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                allProcessTracker(order_id);
                handler.postDelayed(this, 5000);
            }
        }, 5000);


    }





    public void ifCancelledTrip() {

        Handler handlerordercheck = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // accessing data from database or creating network call


                handlerordercheck.postDelayed(new Runnable() {
                    public void run() {


                        ifCancelledTrips(order_id);

                    }

                    ///////////////////////////

                }, 5000);


            }
        }).start();


    }


    public void ifCancelledTrips(String orderid) {


        String URL = "https://www.troopa.org/api/check-cancelled-trip/" + orderid;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);


                    if (obj.getString("status").equals("cancelled")) {


                        cancelled_trip.setVisibility(View.VISIBLE);
                        Toast.makeText(client_detail.this, orderid, Toast.LENGTH_LONG).show();


                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);


    }

    private List<Geofence> geofenceList = new ArrayList<>();

    private void createGeofences() {
        geofenceList.add(new Geofence.Builder()
                .setRequestId("Location A")
                .setCircularRegion(37.4219999, -122.0840575, 100) // Latitude, Longitude, Radius (in meters)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());

        geofenceList.add(new Geofence.Builder()
                .setRequestId("Location B")
                .setCircularRegion(37.4219985, -122.0840565, 100) // Latitude, Longitude, Radius (in meters)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());
    }

    private PendingIntent geofencePendingIntent;

    private void createGeofencePendingIntent() {
        if (geofencePendingIntent != null) {
            return;
        }
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }




    


    private void addGeofencesDropOff(double latitude, double longitude, float radius, String type)  {

       // Toast.makeText(client_detail.this,type, Toast.LENGTH_LONG).show();

        createGeofencesDropOff(latitude,longitude,radius,type);
        createGeofencePendingIntentDropOff(type);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        geofencingClient.addGeofences(getGeofencingRequest(), geofencePendingIntent).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });

    }

    private void createGeofencesDropOff(double latitude, double longitude, float radius, String type) {
        geofenceList.add(new Geofence.Builder()
                .setRequestId(order_id+"dl"+type)
                .setCircularRegion(latitude, longitude, radius) // Latitude, Longitude, Radius (in meters)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());


    }

    private void createGeofencePendingIntentDropOff(String type) {
        if (geofencePendingIntent != null) {
            return;
        }
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        intent.putExtra("orderId",order_id);
        intent.putExtra("type",type);
        startService(intent);
        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    //////////////////////////////////////////////add geofence for pickup////////////////////////////////////////
    private void addGeofencesPickup(double latitude, double longitude,float radius) {

        createGeofencesPickup(latitude,longitude,radius);
        createGeofencePendingIntentPickup();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        geofencingClient.addGeofences(getGeofencingRequest(), geofencePendingIntent).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });

    }

    private void createGeofencePendingIntentPickup() {
        if (geofencePendingIntent != null) {
            return;
        }
        Intent intent = new Intent(this,PickupGeofenceBroadcastReceiver.class);
        intent.putExtra("orderId",order_id);
        startService(intent);
        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void createGeofencesPickup(double latitude, double longitude,float radius) {
        geofenceList.add(new Geofence.Builder()
                .setRequestId(order_id+"pk")
                .setCircularRegion(latitude, longitude, radius) // Latitude, Longitude, Radius (in meters)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());


    }


}