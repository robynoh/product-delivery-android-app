package com.rider.troopadelivery.troopa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rider.troopadelivery.troopa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class start_delivery extends AppCompatActivity implements OnMapReadyCallback,TaskLoadedCallback {

    LinearLayout opendetail;
    LinearLayout closedetail,pickdesti,tripcontact,start_delivery_board,paid_pad,openinfo,openinfo2;
    private BottomSheetDialog bottomSheetDialog;

    TextView dashduration;

    private DatabaseReference databaseReference;
    private final int FIVE_SECONDS = 1000;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    RequestQueue queue;
    RequestQueue queue2;
    private GoogleMap mMap;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    private double  lng=7.491302;
    private double lat=9.072264;
    String pickup_location="";
    String riderphone="";
    SupportMapFragment mapFragment;
    private Polyline currentPolyline;
    String mapTitle="";
    String order_id="";
    LinearLayout delivery_start;
    LinearLayout delivery_start_gray,pay_board;
    LinearLayout  arivaltxt;
    String riderid;
    Button arrived_at_delivery,deliveredforcode,recieved_monry, arrived_at_pickup;
    String pickup_contact_name;
    String client_name;


    String dropoff_contact_name;
    LinearLayout call_pick_up;
    LinearLayout call_drop_off;

    TextView duration_map,cash;
    TextView arrtxt;
    ImageView pin1a,pin1,pin2a,pin2,pin3a,pin3;

    String dropoff_location="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_delivery);

        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Delivery Location");

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        opendetail=findViewById(R.id.opendetail);
        pickdesti=findViewById(R.id.pickdesti);
        dashduration= findViewById(R.id.dashduration);


        Intent intent = getIntent();
        client_name = getIntent().getStringExtra("client_name");
        String client_id = getIntent().getStringExtra("client_id");
        pickup_location = getIntent().getStringExtra("pickup_location");
        dropoff_location = getIntent().getStringExtra("dropoff_location");
        String duration = getIntent().getStringExtra("duration");
        String distance = getIntent().getStringExtra("distance");
        String pickup_contact = getIntent().getStringExtra("pickup_contact");
        String dropoff_contact = getIntent().getStringExtra("dropoff_contact");
        String picture = getIntent().getStringExtra("picture");
        dropoff_contact_name = getIntent().getStringExtra("dropoff_contact_name");
        String amount = getIntent().getStringExtra("amount");
        String payment_type = getIntent().getStringExtra("payment_type");
        String payment_mode = getIntent().getStringExtra("payment_mode");
        pickup_contact_name = getIntent().getStringExtra("pickup_contact_name");
        order_id = getIntent().getStringExtra("order_id");
        riderid = getIntent().getStringExtra("rider_id");

       // Toast.makeText(start_delivery.this, payment_mode, Toast.LENGTH_LONG).show();



        openinfo= findViewById(R.id.openinfo);

        openinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(start_delivery.this, help_center.class);
                startActivity(i);
            }
        });




        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);

        riderphone = sharedPreferences.getString("phone", "");
        mapTitle=sharedPreferences.getString("name", "");


        final Handler handler = new Handler();
        Handler handlerordercheck = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run(){
                // accessing data from database or creating network call

                handler.post(new Runnable() {
                    @Override
                    public void run() {



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
                                            LatLng latLng2 = getLocationFromAddress(start_delivery.this, dropoff_location);
                                            LatLng latLng = new LatLng(rider.latitude, rider.longitude);
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
                                                mMap.addMarker(new MarkerOptions().position(latLng2).title(dropoff_location).icon(BitmapDescriptorFactory.fromResource(R.drawable.packagpoint)));
                                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
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
                                                new FetchURL(start_delivery.this).execute(getUrl(latLng, latLng2), "driving");

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













//                        locationListener = new LocationListener() {
//                            @Override
//                            public void onLocationChanged(Location location) {
//                                try {
//
//                                    SharedPreferences sharedPreferencesonline = getSharedPreferences("goOnlineKey", MODE_PRIVATE);
//                                    String goOnlineStatus = sharedPreferencesonline.getString("onlineActivate", "");
//
//                                    lat = Double.parseDouble(Double.toString(location.getLatitude()));
//                                    lng = Double.parseDouble(Double.toString(location.getLongitude()));
//
//                                    mMap.clear();
//                                    if(goOnlineStatus.equals("start")){
//
//                                        LatLng latLng = new LatLng(lat,lng);
//            LatLng latLng2 = getLocationFromAddress(client_detail.this,pickup_location);
//            mMap.addPolyline((new PolylineOptions()).add(latLng, latLng2)
//                                                    // below line is use to specify the width of poly line.
//                                                    .width(12)
//                                                    // below line is use to add color to our poly line.
//                                                    .color(Color.RED)
//                                            // below line is to make our poly line geodesic.
//                                            .geodesic(true)
//                                    );
//
//
//
//
//            mMap.addMarker(new MarkerOptions().position(latLng).title(" you are currently here").icon(BitmapDescriptorFactory.fromResource(R.drawable.gmarker)));
//            mMap.addMarker(new MarkerOptions().position(latLng2).title(pickup_location).icon(BitmapDescriptorFactory.fromResource(R.drawable.packagpoint)));
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//
//
//            getDirectionsUrl(latLng,latLng2);
//          //  gotToDirection(latLng,latLng2,order_id);
//            checkarrival(order_id);
//            checkpayatdelivery(order_id);
//          //  string_location.setText(getCompleteAddressString(event.getLatitude(),event.getLongitude()));
//
//
//
//
//
//
//
//
//                                    }
//
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//
//                            @Override
//                            public void onStatusChanged(String s, int i, Bundle bundle) {
//
//                            }
//
//                            @Override
//                            public void onProviderEnabled(String s) {
//
//                            }
//
//                            @Override
//                            public void onProviderDisabled(String s) {
//
//                            }
//                        };

//                        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//                        if (ActivityCompat.checkSelfPermission(client_detail.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(client_detail.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                            // TODO: Consider calling
//                            //    ActivityCompat#requestPermissions
//                            // here to request the missing permissions, and then overriding
//                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                            //                                          int[] grantResults)
//                            // to handle the case where the user grants the permission. See the documentation
//                            // for ActivityCompat#requestPermissions for more details.
//                            return;
//                        }
//                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
//                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);

                    }
                });
            }
        }).start();



        bottomSheetDialog = new BottomSheetDialog(start_delivery.this, R.style.BottomSheetTheme);
        View sheetviewx = LayoutInflater.from(getApplicationContext()).inflate(R.layout.delivery_route_detail, null);
        bottomSheetDialog.setContentView(sheetviewx);
        arrived_at_delivery=sheetviewx.findViewById(R.id.arrived_at_delivery);
        duration_map= sheetviewx.findViewById(R.id.duration_map);
        recieved_monry=sheetviewx.findViewById(R.id.recieved_monry);
         delivery_start=sheetviewx.findViewById(R.id.delivery_start);
        //delivery_start_gray=sheetviewx.findViewById(R.id.confirm_delivery_gray);
        pay_board=sheetviewx.findViewById(R.id.pay_board);

        paid_pad=sheetviewx.findViewById(R.id.paid_pad);

        arivaltxt=sheetviewx.findViewById(R.id.arivaltxt);

        arrived_at_pickup=sheetviewx.findViewById(R.id.arrived_at_pickup);

        start_delivery_board=sheetviewx.findViewById(R.id.start_delivery_board);

        pay_board=sheetviewx.findViewById(R.id.pay_board);

        TextView clientname =sheetviewx.findViewById(R.id.clientname);


        openinfo2= sheetviewx.findViewById(R.id.openinfo2);

        openinfo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(start_delivery.this, help_center.class);
                startActivity(i);
            }
        });



        //durationtext.setText(duration);
        clientname.setText(client_name);



        if(payment_type.equals("dropoff location")){

            start_delivery_board.setVisibility(View.GONE);
            pay_board.setVisibility(View.VISIBLE);

        }


        cash=sheetviewx.findViewById(R.id.cash);

        recieved_monry.setText("Recieved"+ amount +" from customer");

        cash.setText(amount);

        arrtxt=sheetviewx.findViewById(R.id.arrtxt);

        pin2=sheetviewx.findViewById(R.id.pin2);
        pin2a=sheetviewx.findViewById(R.id.pin2a);

        pin3=sheetviewx.findViewById(R.id.pin3);
        pin3a=sheetviewx.findViewById(R.id.pin3a);

        pin1a=sheetviewx.findViewById(R.id.pin1a);
        pin1=sheetviewx.findViewById(R.id.pin1);

        closedetail=sheetviewx.findViewById(R.id.closedetail);

        opendetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                 deliveredforcode=sheetviewx.findViewById(R.id.deliveredforcode);

                closedetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.hide();
                    }
                });

                deliveredforcode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        gotToDirection(order_id);
                        Intent i = new Intent(start_delivery.this,delivery_code.class);
                        i.putExtra("client_id",client_id);
                        i.putExtra("rider_id",riderid);
                        i.putExtra("amount",amount);
                        i.putExtra("order_id",order_id);
                        i.putExtra("dropoff_contact_name",dropoff_contact_name);
                        i.putExtra("dropoff_contact",dropoff_contact);
                        i.putExtra("payment_mode",payment_mode);
                        startActivity(i);
                        finish();
                    }
                });


                delivery_start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        gotToDirection(order_id);
                        Intent i = new Intent(start_delivery.this,delivery_code.class);
                        i.putExtra("client_id",client_id);
                        i.putExtra("rider_id",riderid);
                        i.putExtra("amount",amount);
                        i.putExtra("order_id",order_id);
                        i.putExtra("dropoff_contact_name",dropoff_contact_name);
                        i.putExtra("dropoff_contact",dropoff_contact);
                        i.putExtra("payment_mode",payment_mode);
                        startActivity(i);
                        finish();
                    }
                });




                tripcontact=sheetviewx.findViewById(R.id.tripcontact);
                tripcontact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(start_delivery.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.contact_dialogue, viewGroup, false);
                        builder.setView(dialogView);
                        AlertDialog alertDialog = builder.create();


                        call_pick_up=dialogView.findViewById(R.id.call_pick_up);
                        call_drop_off=dialogView.findViewById(R.id.call_drop_off);
                        TextView  pickupname= dialogView.findViewById(R.id.pickupname);
                        pickupname.setText(pickup_contact_name);

                        TextView dropoffname= dialogView.findViewById(R.id.dropoffname);

                        dropoffname.setText(dropoff_contact_name);





                        call_pick_up.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String number=pickup_contact;
                                Uri call = Uri.parse("tel:"+number);
                                Intent surf = new Intent(Intent.ACTION_DIAL,call);
                                startActivity(surf);

                            }
                        });

                        call_drop_off.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String number=dropoff_contact;
                                Uri call = Uri.parse("tel:"+number);
                                Intent surf = new Intent(Intent.ACTION_DIAL,call);
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



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // to remove old markers
        LatLng latLng = new LatLng(9.072264,7.491302);
        mMap.addMarker(new MarkerOptions().position(latLng).title(" you are currently here").icon(BitmapDescriptorFactory.fromResource(R.drawable.gmarker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));


//
//        final Handler handler = new Handler();
//        new Thread(new Runnable() {
//            @Override
//            public void run(){
//                // accessing data from database or creating network call
//
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        locationListener = new LocationListener() {
//                            @Override
//                            public void onLocationChanged(Location location) {
//                                try {
//
//                                    SharedPreferences sharedPreferencesonline = getSharedPreferences("goOnlineKey", MODE_PRIVATE);
//                                    String goOnlineStatus = sharedPreferencesonline.getString("onlineActivate", "");
//
//                                    lat = Double.parseDouble(Double.toString(location.getLatitude()));
//                                    lng = Double.parseDouble(Double.toString(location.getLongitude()));
//
//                                    mMap.clear();
//                                    if(goOnlineStatus.equals("start")){
//
//                                        LatLng latLng = new LatLng(lat,lng);
//                                        LatLng latLng2 = getLocationFromAddress(start_delivery.this,pickup_location);
//                                        mMap.addPolyline((new PolylineOptions()).add(latLng, latLng2)
//                                                // below line is use to specify the width of poly line.
//                                                .width(12)
//                                                // below line is use to add color to our poly line.
//                                                .color(Color.RED)
//                                                // below line is to make our poly line geodesic.
//                                                .geodesic(true)
//                                        );
//
//
//
//
//                                        mMap.addMarker(new MarkerOptions().position(latLng).title(" you are currently here").icon(BitmapDescriptorFactory.fromResource(R.drawable.gmarker)));
//                                        mMap.addMarker(new MarkerOptions().position(latLng2).title(dropoff_location).icon(BitmapDescriptorFactory.fromResource(R.drawable.packagpoint)));
////
//                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//
//
//                                        getDirectionsUrl(latLng, latLng2);
//                                        //gotToDirection(latLng, latLng2, order_id);
//                                        checkdeliveryarrival(order_id);
//
//                                        checkpayatdelivery(order_id);
//                                        //  string_location.setText(getCompleteAddressString(event.getLatitude(),event.getLongitude()));
//
//
//
//
//
//
//
//
//                                    }
//
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//
//                            @Override
//                            public void onStatusChanged(String s, int i, Bundle bundle) {
//
//                            }
//
//                            @Override
//                            public void onProviderEnabled(String s) {
//
//                            }
//
//                            @Override
//                            public void onProviderDisabled(String s) {
//
//                            }
//                        };
//
//                        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//                        if (ActivityCompat.checkSelfPermission(start_delivery.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(start_delivery.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                            // TODO: Consider calling
//                            //    ActivityCompat#requestPermissions
//                            // here to request the missing permissions, and then overriding
//                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                            //                                          int[] grantResults)
//                            // to handle the case where the user grants the permission. See the documentation
//                            // for ActivityCompat#requestPermissions for more details.
//                            return;
//                        }
//                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
//                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
//
//                    }
//                });
//            }
//        }).start();








    }

    public boolean onSupportNavigateUp(){

        SharedPreferences sharedPrefer = getSharedPreferences("goOnlineKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefer.edit();

        editor.putString("onlineActivate", "start");
        // to save our data with key and value.
        editor.commit();

        onBackPressed();
        return true;
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng LatLan= null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            LatLan= new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return LatLan;
    }

    private void getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        String key ="key=AIzaSyCGPDz4LFZeTDm_nSFZVpqVmRvaNP6UpF8";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode+ "&"+key;

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
                    for(int i=0;i<legs.length();i++){

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
                Log.d("error",error.toString());
            }
        });
        queue.add(request);
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }



    private String getUrl(LatLng origin, LatLng dest) {


        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        String key ="key=AIzaSyCGPDz4LFZeTDm_nSFZVpqVmRvaNP6UpF8";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode+ "&"+key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String URL = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return URL;
    }



    public void gotToDirection(String order){


            String URL = "https://www.troopa.org/api/updatedeliveryprocesscheck/"+order;

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
                    Log.d("error",error.toString());
                }
            });
            queue.add(request);






    }

    public void checkdeliveryarrival(String order){



        String URL = "https://www.troopa.org/api/deliverycheck/"+order;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (obj.getString("error").equals("false")) {

                        if(Integer.parseInt(obj.getString("count"))>0){

                            arrived_at_delivery.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.round_button));

                           // delivery_start_gray.setVisibility(View.GONE);
                            pin2a.setVisibility(View.VISIBLE);
                            pin2.setVisibility(View.GONE);

                            pay_board.setVisibility(View.VISIBLE);

                            //arivaltxt.setVisibility(View.VISIBLE);
                           // arrtxt.setText(client_name+" have been notified of your arrival");

                            // Toast.makeText(start_delivery.this,"work now", Toast.LENGTH_LONG).show();


                        }



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


    public void checkpayatdelivery(String order,String riderid){



        String URL = "https://www.troopa.org/api/payatdeliverycheck/"+order+"/"+riderid;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (obj.getString("error").equals("false")) {

                        if(Integer.parseInt(obj.getString("count"))>0){

                            recieved_monry.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.round_button));

                           // delivery_start_gray.setVisibility(View.GONE);
                            pin3a.setVisibility(View.VISIBLE);
                            pin3.setVisibility(View.GONE);

                            paid_pad.setVisibility(View.VISIBLE);

                            //arivaltxt.setVisibility(View.VISIBLE);
                            // arrtxt.setText(client_name+" have been notified of your arrival");

                            // Toast.makeText(start_delivery.this,"work now", Toast.LENGTH_LONG).show();


                        }



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

    public void checkarrival(String order){



        String URL = "https://www.troopa.org/api/arrivalcheck/"+order;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (obj.getString("error").equals("false")) {

                        if(Integer.parseInt(obj.getString("count"))>0){
                            arrived_at_pickup.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.round_button));

                            // delivery_start_gray.setVisibility(View.GONE);
                            pin1a.setVisibility(View.VISIBLE);
                            pin1.setVisibility(View.GONE);

                            //delivery_start.setVisibility(View.VISIBLE);

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
                Log.d("error",error.toString());
            }
        });
        queue.add(request);






    }
}