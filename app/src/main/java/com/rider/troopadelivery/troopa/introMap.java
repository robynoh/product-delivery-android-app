package com.rider.troopadelivery.troopa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;
import com.rider.troopadelivery.troopa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class introMap extends AppCompatActivity implements OnMapReadyCallback {
    RequestQueue queue;
    RequestQueue queue2;


    String client_name;
    String client_id;
    String payment_type;
    String payment_modex;


    DrawerLayout drawerLayout;
   // NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private GoogleMap mMap;

    Button declineredbtn,view_order_now_multiple,declinebtn2,movetoorder2,movetoorder3;

    SupportMapFragment mapFragment;

    private RadioGroup radioGroup;
    RadioButton far, fuel,gobreak,breakdown;

    TextView ridernamevb;
    TextView ridernameob;
    TextView machine_name;
    TextView license_plate,string_location,clientmg3,multiplemsg,clientdropoff,clientpickup,newclientname,paymenttype2,paymode2,durationdistance2,packagetype2;

    ImageView paymodeimg2,paymodeimg3;
    String order_id="";


    TextView clientname,durationdistance3,packagetype3,clientpickup2,clientdropoff3,payment_type3,paymode3,newclientname2;
    TextView clienttime,paymode,todayearning;

    LinearLayout gooff;
    LinearLayout goon;

    LinearLayout onlinetxt1,earningbtn;
    LinearLayout offlinetxt1;
    LinearLayout no_network;
    LinearLayout ongoingtrip;

    LinearLayout call_pick_up;
    LinearLayout call_drop_off;

    ArrayList<all_order_list> arrayList;
    ListView lv;

    String riderid;
    String amount;
    int status=0;

    LinearLayout current_position,orderalert,declinebtn,greetingboard,call_contacts,msglayout,multiple_board;

    boolean rider_online_status=false;


    LinearLayout golivebtn;
    LinearLayout goofflinebtn;

    private DatabaseReference databaseReference;
    private final int FIVE_SECONDS = 5000;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 5000;
    private final long MIN_DIST = 5;

    private double  lng=7.491302;
    private double lat=9.072264;

    String mapTitle="";
    String goOnlineStatus="stop";
    String  phonenumber="";

    Button view_order_now,movetoorder;

    //ImageButton ongoingcall;
    Intent troopaServiceIntent;

    private BottomSheetDialog bottomSheetDialog;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity(); // or finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_intro_map);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Troopa");







        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            //here do what you want when the GPS service is enabled

           // Toast.makeText(introMap.this, "is enable", Toast.LENGTH_SHORT).show();

        } else {

            MaterialAlertDialogBuilder locationDialog = new MaterialAlertDialogBuilder(introMap.this);
            locationDialog.setTitle("Attention");
            locationDialog.setMessage("Location settings must be enabled from the settings to use the application");
            locationDialog.setCancelable(false);
            locationDialog.setPositiveButton("Open settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            locationDialog.create().show();
        }
       // writeNewRider(riderid,mapTitle, lng, lat,phonenumber,"online","yes","kogi","lokoja","lokongoma");


       // DateFormat.getTimeInstance();

        //Toast.makeText(introMap.this, stringDate, Toast.LENGTH_LONG).show();



       // EventBus.getDefault().register(this);

//        EventBus.getDefault().register(this);





        gooff = findViewById(R.id.gooffline);
        goon = findViewById(R.id.goonline);
        current_position=findViewById(R.id.current_position);
        greetingboard=findViewById(R.id.greetingboard);
        orderalert=findViewById(R.id.orderalert);
        view_order_now=findViewById(R.id.view_order_now);
        view_order_now_multiple=findViewById(R.id.view_order_now_multiple);
        string_location=findViewById(R.id.string_location);
        no_network=findViewById(R.id.no_network);
        ongoingtrip=findViewById(R.id.ongoingtrip);
//        view_location_map2=findViewById(R.id.view_location_map2);
//        ongoingcall=findViewById(R.id.ongoingcall);
        declinebtn2=findViewById(R.id.declinebtn2);
        movetoorder2=findViewById(R.id.movetoorder2);

        msglayout=findViewById(R.id.msglayout);

        multiple_board=findViewById(R.id.multiple_board);

        paymenttype2 = findViewById(R.id.payment_type2);

        paymodeimg2= findViewById(R.id.paymodeimg2);

        durationdistance2= findViewById(R.id.durationdistance2);

        packagetype2= findViewById(R.id.packagetype2);


        multiplemsg=findViewById(R.id.multiplemsgx);

        newclientname=findViewById(R.id.newclientname);

        clientdropoff=findViewById(R.id.clientdropoff);

        clientpickup=findViewById(R.id.clientpickup);

        paymode2=findViewById(R.id.paymode2);



        clientname=findViewById(R.id.clientname);
        clienttime=findViewById(R.id.clienttime);


        movetoorder3=findViewById(R.id.movetoorder3);
        packagetype3=findViewById(R.id.packagetype3);
        clientpickup2=findViewById(R.id.clientpickup2);
        clientdropoff3=findViewById(R.id.clientdropoff3);
        payment_type3=findViewById(R.id.payment_type3);
        paymodeimg3=findViewById(R.id.paymodeimg3);
        paymode3=findViewById(R.id.paymode3);
        newclientname2=findViewById(R.id.newclientname2);
        durationdistance3=findViewById(R.id.durationdistance3);

       // call_contacts=findViewById(R.id.call_contacts);

        arrayList = new ArrayList<>();





        //gooff.setVisibility(View.INVISIBLE);

        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);

        String phone = sharedPreferences.getString("phone", "");
        String email = sharedPreferences.getString("email", "");
        String ridername = sharedPreferences.getString("name", "");
        String picture = sharedPreferences.getString("picture", "");
        int online_status = sharedPreferences.getInt("online_status", 0);
        int verification_status = sharedPreferences.getInt("verification_status", 0);
        String machine_manufacture = sharedPreferences.getString("machine_manufacture", "");
        String license = sharedPreferences.getString("license", "");
        status = sharedPreferences.getInt("riderstatus", 0);
        riderid = sharedPreferences.getString("pilotID", "");

/////////////////////////// pull today rarning
          todayearningvalue(riderid);

       // Toast.makeText(introMap.this, picture, Toast.LENGTH_LONG).show();
        mapTitle=ridername;
        phonenumber=phone;

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        View header = navigationView.getHeaderView(0);
        TextView fullname = header.findViewById(R.id.fullname);
        TextView userid = header.findViewById(R.id.userid);
        LinearLayout profilefilter = header.findViewById(R.id.profilefilter);
        ImageView image = (ImageView) header.findViewById(R.id.userimg);

        Picasso.with(introMap.this).load(picture).into(image);
        //Picasso.with(introMap.this).setLoggingEnabled(true);
        fullname.setText(ridername);
        userid.setText(riderid);


        // Intent intent = new Intent(introMap.this, troopaRiderBackgroundService.class);
//        intent.putExtra("riderid",riderid);
//        intent.putExtra("phone", phonenumber);
//        startActivity(intent);





        declinebtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(introMap.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.decline_dialogue, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();

                declineredbtn = dialogView.findViewById(R.id.declineredbtn);
                declineredbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        fuel = dialogView.findViewById(R.id.fuel);
                        far = dialogView.findViewById(R.id.far);
                        gobreak = dialogView.findViewById(R.id.gobreak);
                        breakdown = dialogView.findViewById(R.id.motobreakdown);

                        if (!fuel.isChecked() && !far.isChecked() && !gobreak.isChecked() && !breakdown.isChecked()) {

                            fuel.setError("Please thick a reason for declining");

                        } else {

                            String reasonForDecline = "";
                            if (fuel.isChecked()) {

                                reasonForDecline = "I am out of fuel";

                            } else if (far.isChecked()) {

                                reasonForDecline = "I am far from the pickup location";

                            } else if (gobreak.isChecked()) {

                                reasonForDecline = "I am going for break";

                            } else if (breakdown.isChecked()) {

                                reasonForDecline = "My motor cycle break down";

                            }

                            // Toast.makeText(introMap.this, reasonForDecline, Toast.LENGTH_LONG).show();
                            decline_rider_request(reasonForDecline,Integer.parseInt(order_id));
                            alertDialog.hide();
                            bottomSheetDialog.hide();
                        }

                        // Toast.makeText(introMap.this, "yes o", Toast.LENGTH_LONG).show();

                    }
                });


                alertDialog.show();
            }
        });





        profilefilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(introMap.this, riderProfile.class);
                startActivity(i);
            }
        });


        // Initialize fragment
        //Fragment fragment=new MapFragment();

        // Open fragment
        // getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        goon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                bottomSheetDialog = new BottomSheetDialog(introMap.this, R.style.BottomSheetTheme);
                View sheetview = LayoutInflater.from(getApplicationContext()).inflate(R.layout.onboard_bottom_sheet, null);
                bottomSheetDialog.setContentView(sheetview);

                ridernameob = sheetview.findViewById(R.id.name21);
                machine_name = sheetview.findViewById(R.id.machinename);
                license_plate = sheetview.findViewById(R.id.licenseplate);

                golivebtn = sheetview.findViewById(R.id.golivebtn);
                onlinetxt1 = sheetview.findViewById(R.id.onlinetxt1);



                ridernameob.setText(ridername);
                machine_name.setText(machine_manufacture);
                license_plate.setText(license);

                golivebtn.setVisibility(View.VISIBLE);
                onlinetxt1.setVisibility(View.VISIBLE);



                golivebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        updateRiderOnlineStatus(riderid,"online");
                        //startService(intent);
                       // start troopa rider backdround service here//
                       // startForegroundService()

                        //////////////////////////////////////////////

                        ActivityCompat.requestPermissions(introMap.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);


                        SharedPreferences sharedPrefer = getSharedPreferences("goOnlineKey", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPrefer.edit();

                        editor.putString("onlineActivate", "start");
                        // to save our data with key and value.
                        editor.commit();

                        // writeNewRider(ridername,lng,lat);

                        Intent i = new Intent(introMap.this,introMap.class);
                        startActivity(i);

                        // Toast.makeText(introMap.this, "i am live", Toast.LENGTH_LONG).show();

                    }
                });

                bottomSheetDialog.show();

                drawerLayout.closeDrawer(GravityCompat.START);

            }
        });




        gooff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateRiderOnlineStatus(riderid,"offline");
                bottomSheetDialog = new BottomSheetDialog(introMap.this, R.style.BottomSheetTheme);
                View sheetview = LayoutInflater.from(getApplicationContext()).inflate(R.layout.onboard_bottom_sheet, null);
                bottomSheetDialog.setContentView(sheetview);

                ridernameob = sheetview.findViewById(R.id.name2);
                machine_name = sheetview.findViewById(R.id.machinename);
                license_plate = sheetview.findViewById(R.id.licenseplate);
                offlinetxt1 = sheetview.findViewById(R.id.offlinetxt1);
                goofflinebtn = sheetview.findViewById(R.id.goofflinebtn);


                todayearning = sheetview.findViewById(R.id.todayearning);
                earningbtn = sheetview.findViewById(R.id.earningbtn);

                earningbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(introMap.this, rider_earning.class);
                        startActivity(i);
                    }
                });


                offlinetxt1.setVisibility(View.VISIBLE);
                goofflinebtn.setVisibility(View.VISIBLE);

                ridernameob.setText(ridername);
                machine_name.setText(machine_manufacture);
                license_plate.setText(license);


                goofflinebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        updateRiderOnlineStatus(riderid,"offline");
                       // stopService(intent);

                        // calling method to edit values in shared prefs.
                        SharedPreferences sharedPreferences = getSharedPreferences("goOnlineKey", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.remove("onlineActivate");
                        editor.commit();

                       // writeNewRider(riderid, mapTitle, Double.parseDouble("0.00"), Double.parseDouble("0.00"),phonenumber, "offline","no","","","");
                        removeRider(phonenumber);
                        goOnlineStatus="stop";

                        Intent i = new Intent(introMap.this,introMap.class);
                        startActivity(i);
                        // SharedPreferences sharedPrefer = getSharedPreferences("goOnlineKey", MODE_PRIVATE);
                        // SharedPreferences.Editor editor = sharedPrefer.edit();

                        // editor.putString("onlineActivate", "start");
                        // to save our data with key and value.
                        //editor.apply();

                        // writeNewRider(ridername,lng,lat);

                        //Toast.makeText(introMap.this, "go ofline", Toast.LENGTH_LONG).show();

                    }
                });

                drawerLayout.closeDrawer(GravityCompat.START);
                bottomSheetDialog.show();


            }
        });




                SharedPreferences sharedPreferencesonline = getSharedPreferences("goOnlineKey", MODE_PRIVATE);
                String goOnlineStatus = sharedPreferencesonline.getString("onlineActivate", "");


             // Toast.makeText(introMap.this, goOnlineStatus, Toast.LENGTH_LONG).show();


                if ((status != 1) && (goOnlineStatus.equals(""))) {


                    bottomSheetDialog = new BottomSheetDialog(introMap.this, R.style.BottomSheetTheme);
                    View sheetview = LayoutInflater.from(getApplicationContext()).inflate(R.layout.verify_bottom_sheet, null);
                    bottomSheetDialog.setContentView(sheetview);
                    ridernamevb = sheetview.findViewById(R.id.name);
                    ridernamevb.setText(ridername);
                    bottomSheetDialog.show();
                } else {




                    ActivityCompat.requestPermissions(introMap.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

                    //Toast.makeText(introMap.this, " i am ready to go online", Toast.LENGTH_LONG).show();





                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference("RiderLocation");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@android.support.annotation.NonNull @NonNull DataSnapshot dataSnapshot) {

                            try {

                               //Toast.makeText(introMap.this, riderLocation.longitude+"-"+riderLocation.latitude, Toast.LENGTH_LONG).show();



                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onCancelled(@android.support.annotation.NonNull @NonNull DatabaseError databaseError) {

                        }
                    });




                    if ((status == 1) && (goOnlineStatus.equals(""))) {

                        goon.setVisibility(View.VISIBLE);
                        gooff.setVisibility(View.INVISIBLE);

                        bottomSheetDialog = new BottomSheetDialog(introMap.this, R.style.BottomSheetTheme);
                        View sheetview = LayoutInflater.from(getApplicationContext()).inflate(R.layout.onboard_bottom_sheet, null);
                        bottomSheetDialog.setContentView(sheetview);

                        ridernameob = sheetview.findViewById(R.id.name21);
                        machine_name = sheetview.findViewById(R.id.machinename);
                        license_plate = sheetview.findViewById(R.id.licenseplate);
                        golivebtn = sheetview.findViewById(R.id.golivebtn);
                        onlinetxt1 = sheetview.findViewById(R.id.onlinetxt1);
                        todayearning = sheetview.findViewById(R.id.todayearning);
                        earningbtn = sheetview.findViewById(R.id.earningbtn);

                        earningbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(introMap.this, rider_earning.class);
                                startActivity(i);
                            }
                        });




                        golivebtn.setVisibility(View.VISIBLE);
                        onlinetxt1.setVisibility(View.VISIBLE);
                        ridernameob.setText(ridername);
                        machine_name.setText(machine_manufacture);
                        license_plate.setText(license);
                        Context context = golivebtn.getContext();









                        golivebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                              //  startService(intent);
                                updateRiderOnlineStatus(riderid,"online");
                               // ActivityCompat.requestPermissions(introMap.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);


                                SharedPreferences sharedPrefer = getSharedPreferences("goOnlineKey", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPrefer.edit();

                                editor.putString("onlineActivate", "start");
                                // to save our data with key and value.
                                editor.apply();

                                // writeNewRider(ridername,lng,lat);

                               Intent i = new Intent(introMap.this,introMap.class);
                               startActivity(i);

                               // Toast.makeText(introMap.this, "i am live", Toast.LENGTH_LONG).show();

                            }
                        });


                        bottomSheetDialog.show();




                    }
                    if ((status == 1) && (goOnlineStatus.equals("start"))) {

                        goon.setVisibility(View.INVISIBLE);
                        gooff.setVisibility(View.VISIBLE);

                        greetingboard.setVisibility(View.INVISIBLE);


                        bottomSheetDialog = new BottomSheetDialog(introMap.this, R.style.BottomSheetTheme);
                        View sheetview = LayoutInflater.from(getApplicationContext()).inflate(R.layout.onboard_bottom_sheet, null);
                        bottomSheetDialog.setContentView(sheetview);


                        todayearning = sheetview.findViewById(R.id.todayearning);
                        earningbtn = sheetview.findViewById(R.id.earningbtn);

                        earningbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(introMap.this, rider_earning.class);
                                startActivity(i);
                            }
                        });


                        checkifOrder(phone);




                    }


                }





                drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.close_menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();





        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.orders_nav:
//                        Intent i = new Intent(introMap.this,real_order_list.class);
//                        startActivity(i);
                        Intent i = new Intent(introMap.this, recent_order_list.class);
                        i.putExtra("rider_id",riderid);
                        startActivity(i);
                       // finish();



                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.earnings_nav:


                        Intent a = new Intent(introMap.this,earning.class);
                        startActivity(a);
                        //finish();


                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;



                 //   case R.id.logout_nav:
                        // calling method to edit values in shared prefs.
                      //  SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
                       // SharedPreferences.Editor editor = sharedPreferences.edit();

                        // below line will clear
                        // the data in shared prefs.
                      //  editor.clear();

                        // below line will apply empty
                        // data to shared prefs.
                      //  editor.apply();

                       // writeNewRider(mapTitle, Double.parseDouble("0.00"), Double.parseDouble("0.00"),phonenumber);

                      //  goOnlineStatus="stop";

                        // starting mainactivity after
                        // clearing values in shared preferences.
                      //  Intent a = new Intent(introMap.this, login_activity.class);
                      //  startActivity(a);
                       // finish();

                      //  drawerLayout.closeDrawer(GravityCompat.START);
                      //  break;
                }


                return true;
            }
        });
    }






//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(LocationService event){
//
//        SharedPreferences sharedPreferencesonline = getSharedPreferences("goOnlineKey", MODE_PRIVATE);
//        String goOnlineStatus = sharedPreferencesonline.getString("onlineActivate", "");
//
//       // Toast.makeText(introMap.this, goOnlineStatus, Toast.LENGTH_LONG).show();
//
//        if (goOnlineStatus.equals("start")) {
//
//             writeNewRider(riderid,mapTitle, lng, lat,phonenumber,"online");
//
//
//
//            Double lat=event.getLatitude();
//            Double lng =event.getLongitude();
//
//
//            // to remove old markers
//            mMap.clear();
//
//            LatLng latLng = new LatLng(event.getLatitude(),event.getLongitude());
//            mMap.addMarker(new MarkerOptions().position(latLng).title(" you are currently here").icon(BitmapDescriptorFactory.fromResource(R.drawable.gmarker)));
//            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//
//
//
//            string_location.setText(getCompleteAddressString(event.getLatitude(),event.getLongitude()));
//
//            //call your method that actually updates the marker position
//            //this could simply be animating the camera to the new position
//            //like I said, clear the map of the old marker if you want
//        }
//    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // googleMap.addMarker(new MarkerOptions()
         //     .position(sydney)
         //   .title(mapTitle));
       // googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //LatLng sydney = new LatLng(lat,lng);
       // googleMap.addMarker(new MarkerOptions()
              //  .position(sydney)
                //.title(mapTitle));
       // googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        LatLng latLng = new LatLng(9.072264,7.491302);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));







    }
    public void writeNewRider(String riderid, String mapTitle, Double longitude, Double latitude, String phone, String online, String availability, String state,String city, String address) {


        RiderLocation riderLocation = new RiderLocation(riderid,mapTitle,longitude, latitude,phone,online,availability,state,city,address);
        databaseReference.child(phone).setValue(riderLocation);





    }

    public void writeNewRider2(String riderid, String mapTitle, Double longitude, Double latitude, String phone, String online, String availability,String state,String city,String address) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("RiderLocation");
        RiderLocation riderLocation = new RiderLocation(riderid,mapTitle,longitude, latitude,phone,online,availability,state,city,address);

        databaseReference.child(phone).setValue(riderLocation);





    }



    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
               // Log.w("My Current loction address", strReturnedAddress.toString());
            } else {
               // Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
           // Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }



    public void checkifOrder(String phone) {

        Handler handlerordercheck = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run(){
                // accessing data from database or creating network call



                handlerordercheck.postDelayed(new Runnable() {
                    public void run() {


                        ConnectivityManager connMgr = (ConnectivityManager) introMap.this
                                .getSystemService(Context.CONNECTIVITY_SERVICE);

                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                        if (networkInfo != null && networkInfo.isConnected()) {

                            String URL = "https://www.troopa.org/api/pickuprequest/"+riderid;

                            queue = Volley.newRequestQueue(introMap.this);
                            StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject obj = new JSONObject(response);

                                        //Toast.makeText(phone_login_activity.this,obj.getString("error"), Toast.LENGTH_LONG).show();


                                        if (obj.getString("error").equals("false")) {

                                            //Toast.makeText(introMap.this,obj.getInt("count"), Toast.LENGTH_LONG).show();


                                            if (obj.getInt("count")>0 && obj.getInt("count")<2) {



                                                String duradist = obj.getString("duration") + "-" + obj.getString("distance");
                                                client_name = obj.getString("client_name");
                                                String pickup_location = obj.getString("pickup_location");
                                                String dropoff_location = obj.getString("dropoff_location");
                                                String package_type = obj.getString("package_type");
                                                payment_type = obj.getString("payment_type");
                                                payment_modex = obj.getString("payment_mode");
                                                String picture = obj.getString("picture");
                                                String duration = obj.getString("duration");
                                                String distance = obj.getString("distance");
                                                String pickup_contact = obj.getString("pickup_contact");
                                                String dropoff_contact = obj.getString("dropoff_contact");
                                                client_id = obj.getString("client_id");
                                                String dropoff_contact_name = obj.getString("dropoff_contact_name");
                                                String pickup_contact_name = obj.getString("pickup_contact_name");
                                                order_id = obj.getString("order_id");
                                                 amount = obj.getString("amount");


                                               // Toast.makeText(introMap.this, "amount:"+amount+riderid, Toast.LENGTH_LONG).show();

                                                if (obj.getString("status").equals("new")) {

//                                            ImageView clientimg = (ImageView) findViewById(R.id.clientimg);
//                                            Picasso.with(introMap.this).load(obj.getString("picture")).into(clientimg);
                                                    clientname.setText(obj.getString("client_name"));
                                                    clienttime.setText(obj.getString("time"));

                                                    clientdropoff.setText(obj.getString("dropoff_location"));

                                                    clientpickup.setText(obj.getString("pickup_location"));

                                                    newclientname.setText(obj.getString("client_name"));



                                                    paymenttype2.setText(obj.getString("payment_type"));



                                                    paymode2.setText(obj.getString("payment_mode"));



                                                    if(obj.getString("payment_mode").equals("card")) {
                                                        paymodeimg2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.cardpay2));

                                                    }
                                                    else if(obj.getString("payment_mode").equals("cash")) {
                                                        paymodeimg2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.cashpay));

                                                    }




                                                    durationdistance2.setText(obj.getString("duration") + "-" + obj.getString("distance"));



                                                    packagetype2.setText(obj.getString("package_type"));


//                                                    if (mapFragment.getView() != null) {
//
//                                                        ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
//                                                        params.height = 200;
//                                                        mapFragment.getView().setLayoutParams(params);
//
//
//
//                                                    }





                                                    movetoorder2.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {

                                                            update_order();


                                                            Intent i = new Intent(introMap.this, client_detail.class);
                                                            i.putExtra("pickup_location", pickup_location);
                                                            i.putExtra("dropoff_location", dropoff_location);
                                                            i.putExtra("duration", duration);
                                                            i.putExtra("distance", distance);
                                                            i.putExtra("pickup_contact", pickup_contact);
                                                            i.putExtra("dropoff_contact", dropoff_contact);
                                                            i.putExtra("client_name", client_name);
                                                            i.putExtra("picture", picture);
                                                            i.putExtra("dropoff_contact_name", dropoff_contact_name);
                                                            i.putExtra("pickup_contact_name", pickup_contact_name);
                                                            i.putExtra("order_id", order_id);
                                                            i.putExtra("riderid",riderid);
                                                            i.putExtra("payment_type",payment_type);
                                                            i.putExtra("payment_mode",payment_modex);
                                                            i.putExtra("latitude",lat);
                                                            i.putExtra("longitude",lng);
                                                            i.putExtra("client_id",client_id);
                                                            i.putExtra("amount",amount);

                                                            startActivity(i);


                                                        }
                                                    });




                                                    //Toast.makeText(introMap.this, payment_modex, Toast.LENGTH_LONG).show();

                                                    view_order_now.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {

                                                            bottomSheetDialog = new BottomSheetDialog(introMap.this, R.style.BottomSheetTheme);
                                                            View sheetviewxa = LayoutInflater.from(getApplicationContext()).inflate(R.layout.current_location_sheet, null);
                                                            bottomSheetDialog.setContentView(sheetviewxa);
                                                            movetoorder = sheetviewxa.findViewById(R.id.movetoorder);
                                                            declinebtn = sheetviewxa.findViewById(R.id.declinebtn);
                                                            newclientname = sheetviewxa.findViewById(R.id.newclientname);


                                                            // ImageView newclientimg = sheetviewx.findViewById(R.id.newclientimg);
                                                            //newclientname = sheetviewy.findViewById(R.id.newclientname);
                                                            TextView clientpickup = sheetviewxa.findViewById(R.id.clientpickup);
                                                            TextView clientdropoff = sheetviewxa.findViewById(R.id.clientdropoff);
                                                            TextView paymenttype = sheetviewxa.findViewById(R.id.payment_type);
                                                            TextView packagetype = sheetviewxa.findViewById(R.id.packagetype);
                                                            TextView durationdistance = sheetviewxa.findViewById(R.id.durationdistance);
                                                             ImageView paymodeimg=sheetviewxa.findViewById(R.id.paymodeimg);
                                                            TextView paymode = sheetviewxa.findViewById(R.id.paymode);
                                                            // Picasso.with(introMap.this).load(picture).into(newclientimg);
                                                            newclientname.setText(client_name);
                                                            clientpickup.setText(pickup_location);
                                                            clientdropoff.setText(dropoff_location);
                                                            paymenttype.setText(payment_type);
                                                            packagetype.setText(package_type);
                                                            paymode.setText(payment_modex);

                                                            durationdistance.setText(duradist);

                                                            if(payment_modex.equals("card")) {
                                                                paymodeimg.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.cardpay2));

                                                            }
                                                            else if(payment_modex.equals("cash")) {
                                                                paymodeimg.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.cashpay));

                                                            }


                                                            declinebtn.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View view) {
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(introMap.this);
                                                                    ViewGroup viewGroup = findViewById(android.R.id.content);
                                                                    View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.decline_dialogue, viewGroup, false);
                                                                    builder.setView(dialogView);
                                                                    AlertDialog alertDialog = builder.create();

                                                                    declineredbtn = dialogView.findViewById(R.id.declineredbtn);
                                                                    declineredbtn.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View view) {

                                                                            fuel = dialogView.findViewById(R.id.fuel);
                                                                            far = dialogView.findViewById(R.id.far);
                                                                            gobreak = dialogView.findViewById(R.id.gobreak);
                                                                            breakdown = dialogView.findViewById(R.id.motobreakdown);

                                                                            if (!fuel.isChecked() && !far.isChecked() && !gobreak.isChecked() && !breakdown.isChecked()) {

                                                                                fuel.setError("Please thick a reason for declining");

                                                                            } else {

                                                                                String reasonForDecline = "";
                                                                                if (fuel.isChecked()) {

                                                                                    reasonForDecline = "I am out of fuel";

                                                                                } else if (far.isChecked()) {

                                                                                    reasonForDecline = "I am far from the pickup location";

                                                                                } else if (gobreak.isChecked()) {

                                                                                    reasonForDecline = "I am going for break";

                                                                                } else if (breakdown.isChecked()) {

                                                                                    reasonForDecline = "My motor cycle break down";

                                                                                }

                                                                                // Toast.makeText(introMap.this, reasonForDecline, Toast.LENGTH_LONG).show();
                                                                                decline_rider_request(reasonForDecline,Integer.parseInt(order_id));
                                                                                alertDialog.hide();
                                                                                bottomSheetDialog.hide();
                                                                            }

                                                                            // Toast.makeText(introMap.this, "yes o", Toast.LENGTH_LONG).show();

                                                                        }
                                                                    });


                                                                    alertDialog.show();
                                                                }
                                                            });


                                                            movetoorder.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View view) {

                                                                    update_order();


                                                                    Intent i = new Intent(introMap.this, client_detail.class);
                                                                    i.putExtra("pickup_location", pickup_location);
                                                                    i.putExtra("dropoff_location", dropoff_location);
                                                                    i.putExtra("duration", duration);
                                                                    i.putExtra("distance", distance);
                                                                    i.putExtra("pickup_contact", pickup_contact);
                                                                    i.putExtra("dropoff_contact", dropoff_contact);
                                                                    i.putExtra("client_name", client_name);
                                                                    i.putExtra("picture", picture);
                                                                    i.putExtra("dropoff_contact_name", dropoff_contact_name);
                                                                    i.putExtra("pickup_contact_name", pickup_contact_name);
                                                                    i.putExtra("order_id", order_id);
                                                                    i.putExtra("riderid",riderid);
                                                                    i.putExtra("payment_type",payment_type);
                                                                    i.putExtra("payment_mode",payment_modex);
                                                                    i.putExtra("latitude",lat);
                                                                    i.putExtra("longitude",lng);
                                                                    i.putExtra("client_id",client_id);
                                                                    startActivity(i);
                                                                    finish();

                                                                }
                                                            });


                                                            bottomSheetDialog.show();

                                                        }
                                                    });


                                                    ongoingtrip.setVisibility(View.GONE);
                                                    current_position.setVisibility(View.GONE);
                                                    orderalert.setVisibility(View.VISIBLE);
                                                    msglayout.setVisibility(View.VISIBLE);

                                                    no_network.setVisibility(View.GONE);
                                                    multiple_board.setVisibility(View.GONE);


//                                                    if (mapFragment.getView() != null) {
//
//                                                        ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
//                                                        params.height = 850;
//                                                        mapFragment.getView().setLayoutParams(params);
//
//
//
//                                                    }


                                                } else if (obj.getString("status").equals("accepted")) {


//                                                    String client_namex = obj.getString("client_name");
//                                                    String picture = obj.getString("picture");
//
//                                                    String pickup_contact = obj.getString("pickup_contact");
//                                                    String dropoff_contact = obj.getString("dropoff_contact");
//                                                    String dropoff_contact_name = obj.getString("dropoff_contact_name");
//                                                    String pickup_contact_name = obj.getString("pickup_contact_name");
//                                                    String pickup_location = obj.getString("pickup_location");
//                                                    String dropoff_location = obj.getString("dropoff_location");
//                                                    String distance = obj.getString("distance");
//                                                    String duration = obj.getString("duration");




                                                    movetoorder3.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {

                                                            String URL ="https://www.troopa.org/api/pickuprequest/"+riderid;
                                                            queue2 = Volley.newRequestQueue(introMap.this);
                                                            StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                                                                @Override
                                                                public void onResponse(String response) {

                                                                    JSONObject obj = null;
                                                                    try {
                                                                        obj = new JSONObject(response);
                                                                        if (obj.getString("error").equals("false")) {

                                                                            Intent i = new Intent(introMap.this, client_detail.class);
                                                                            i.putExtra("pickup_location", pickup_location);
                                                                            i.putExtra("dropoff_location", dropoff_location);
                                                                            i.putExtra("duration", duration);
                                                                            i.putExtra("distance", distance);
                                                                            i.putExtra("pickup_contact", pickup_contact);
                                                                            i.putExtra("dropoff_contact", dropoff_contact);
                                                                            i.putExtra("client_name", client_name);
                                                                            i.putExtra("picture", picture);
                                                                            i.putExtra("dropoff_contact_name", dropoff_contact_name);
                                                                            i.putExtra("pickup_contact_name", pickup_contact_name);
                                                                            i.putExtra("order_id", order_id);
                                                                            i.putExtra("riderid",riderid);
                                                                            i.putExtra("payment_type",payment_type);
                                                                            i.putExtra("payment_mode",payment_modex);
                                                                            i.putExtra("latitude",lat);
                                                                            i.putExtra("longitude",lng);
                                                                            i.putExtra("client_id",client_id);
                                                                            i.putExtra("amount",amount);
                                                                            startActivity(i);



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
                                                    });







//                                                    ongoingcall.setOnClickListener(new View.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(View view) {
//
//                                                            AlertDialog.Builder builder = new AlertDialog.Builder(introMap.this);
//                                                            ViewGroup viewGroup = findViewById(android.R.id.content);
//                                                            View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.contact_dialogue, viewGroup, false);
//                                                            TextView pickupname= dialogView.findViewById(R.id.pickupname);
//
//                                                            call_pick_up=dialogView.findViewById(R.id.call_pick_up);
//                                                            call_drop_off=dialogView.findViewById(R.id.call_drop_off);
//
//                                                            pickupname.setText(pickup_contact_name);
//
//                                                            TextView dropoffname= dialogView.findViewById(R.id.dropoffname);
//
//                                                            dropoffname.setText(dropoff_contact_name);
//
//                                                            builder.setView(dialogView);
//                                                            AlertDialog alertDialog = builder.create();
//
//
//                                                            call_pick_up.setOnClickListener(new View.OnClickListener() {
//                                                                @Override
//                                                                public void onClick(View view) {
//
//                                                                    String number=pickup_contact;
//                                                                    Uri call = Uri.parse("tel:"+number);
//                                                                    Intent surf = new Intent(Intent.ACTION_DIAL,call);
//                                                                    startActivity(surf);
//
//                                                                }
//                                                            });
//
//                                                            call_drop_off.setOnClickListener(new View.OnClickListener() {
//                                                                @Override
//                                                                public void onClick(View view) {
//
//                                                                    String number=dropoff_contact;
//                                                                    Uri call = Uri.parse("tel:"+number);
//                                                                    Intent surf = new Intent(Intent.ACTION_DIAL,call);
//                                                                    startActivity(surf);
//
//                                                                }
//                                                            });
//
//                                                            alertDialog.show();
//
//
//                                                        }
//                                                    });










                                                    // ImageView clientimgongoing = (ImageView) findViewById(R.id.clientimgongoing);
                                                    // Picasso.with(introMap.this).load(picture).into(clientimgongoing);

//                                                    TextView clientname3=findViewById(R.id.clientname3);
//                                                    clientname3.setText(client_namex);
//
//                                                    TextView pickup_text=findViewById(R.id.pickup_text);
//                                                    pickup_text.setText(pickup_location);
//
//                                                    TextView dropoff_text=findViewById(R.id.dropoff_text);
//                                                    dropoff_text.setText(dropoff_location);


                                                    durationdistance3.setText(obj.getString("duration") + "-" + obj.getString("distance"));
                                                    packagetype3.setText(obj.getString("package_type"));
                                                    clientpickup2.setText(obj.getString("pickup_location"));
                                                    clientdropoff3.setText(obj.getString("dropoff_location"));
                                                    payment_type3.setText(obj.getString("payment_type"));
                                                    paymode3.setText(obj.getString("payment_mode"));
                                                    newclientname2.setText(obj.getString("client_name"));

                                                    if(obj.getString("payment_mode").equals("card")) {
                                                        paymodeimg3.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.cardpay2));

                                                    }
                                                    else if(obj.getString("payment_mode").equals("cash")) {
                                                        paymodeimg3.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.cashpay));

                                                    }

//                                                    movetoorder3.setOnClickListener(new View.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(View view) {
//
//
//
//
//                                                            Intent i = new Intent(introMap.this, client_detail.class);
//                                                            i.putExtra("pickup_location", pickup_location);
//                                                            i.putExtra("dropoff_location", dropoff_location);
//                                                            i.putExtra("duration", duration);
//                                                            i.putExtra("distance", distance);
//                                                            i.putExtra("pickup_contact", pickup_contact);
//                                                            i.putExtra("dropoff_contact", dropoff_contact);
//                                                            i.putExtra("client_name", client_name);
//                                                            i.putExtra("picture", picture);
//                                                            i.putExtra("dropoff_contact_name", dropoff_contact_name);
//                                                            i.putExtra("pickup_contact_name", pickup_contact_name);
//                                                            i.putExtra("order_id", order_id);
//                                                            i.putExtra("riderid",riderid);
//                                                            i.putExtra("payment_type",payment_type);
//                                                            i.putExtra("payment_mode",payment_modex);
//                                                            i.putExtra("latitude",lat);
//                                                            i.putExtra("longitude",lng);
//                                                            i.putExtra("client_id",client_id);
//                                                            startActivity(i);
//                                                           // finish();
//
//                                                        }
//                                                    });








                                                    ongoingtrip.setVisibility(View.VISIBLE);
                                                    current_position.setVisibility(View.GONE);
                                                    orderalert.setVisibility(View.GONE);
                                                    msglayout.setVisibility(View.GONE);

                                                    no_network.setVisibility(View.GONE);
                                                    multiple_board.setVisibility(View.GONE);













                                                } else if (obj.getString("status").equals("decline") || obj.getString("status").equals("cancelled")) {


                                                    ongoingtrip.setVisibility(View.GONE);
                                                    current_position.setVisibility(View.VISIBLE);
                                                    orderalert.setVisibility(View.GONE);
                                                    no_network.setVisibility(View.GONE);
                                                    msglayout.setVisibility(View.GONE);

                                                    multiple_board.setVisibility(View.GONE);

//                                                    if (mapFragment.getView() != null) {
//
//                                                        ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
//                                                        params.height = 850;
//                                                        mapFragment.getView().setLayoutParams(params);
//
//
//
//
//                                                    }


                                                } else if (obj.getString("status").equals("delivered")) {

                                                    ongoingtrip.setVisibility(View.GONE);
                                                    current_position.setVisibility(View.VISIBLE);
                                                    orderalert.setVisibility(View.GONE);
                                                    no_network.setVisibility(View.GONE);
                                                    msglayout.setVisibility(View.GONE);
                                                    multiple_board.setVisibility(View.GONE);

//                                                    if (mapFragment.getView() != null) {
//
//                                                        ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
//                                                        params.height = 850;
//                                                        mapFragment.getView().setLayoutParams(params);
//
//
//                                                    }


                                                }


//                                    if (obj.getInt("count") > 0) {
//
//                                        current_position.setVisibility(View.INVISIBLE);
//                                        orderalert.setVisibility(View.VISIBLE);
//
//                                        if (mapFragment.getView() != null) {
//
//                                            ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
//                                            params.height = 850;
//                                            mapFragment.getView().setLayoutParams(params);
//                                        }
//
//                                    } else {
//
//                                        if (mapFragment.getView() != null) {
//
//                                            ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
//                                            params.height = 850;
//                                            mapFragment.getView().setLayoutParams(params);
//                                        }
//
//                                        current_position.setVisibility(View.VISIBLE);
//
//                                    }

                                            }

                                            else if(obj.getInt("count")>1) {



                                               // ImageView clientimg = (ImageView) findViewById(R.id.clientimg);
                                               // Picasso.with(introMap.this).load(obj.getString("picture")).into(clientimg);
                                                clientname.setText(obj.getString("client_name"));
                                                clienttime.setText(obj.getString("time"));

                                                ongoingtrip.setVisibility(View.GONE);
                                                current_position.setVisibility(View.GONE);
                                                orderalert.setVisibility(View.VISIBLE);
                                                view_order_now.setVisibility(View.GONE);
                                                no_network.setVisibility(View.GONE);
                                                msglayout.setVisibility(View.GONE);

                                                multiple_board.setVisibility(View.VISIBLE);
                                                view_order_now_multiple.setVisibility(View.VISIBLE);
                                                multiple_board.setVisibility(View.VISIBLE);
                                                multiplemsg.setVisibility(View.VISIBLE);
                                                multiplemsg.setText("You have "+obj.getInt("count")+" delivery request ");

                                                view_order_now_multiple. setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {



                                                        Intent i = new Intent(introMap.this, recent_order_list.class);
                                                        i.putExtra("rider_id",riderid);
                                                        startActivity(i);






                                                    }
                                                });


//                                                if (mapFragment.getView() != null) {
//
//                                                    ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
//                                                    params.height = 850;
//                                                    mapFragment.getView().setLayoutParams(params);
//
//
//
//
//
//                                                }

                                            }


                                            else{



                                                ongoingtrip.setVisibility(View.GONE);
                                                current_position.setVisibility(View.VISIBLE);
                                                orderalert.setVisibility(View.GONE);
                                                multiple_board.setVisibility(View.GONE);
                                                msglayout.setVisibility(View.GONE);





//                                                if (mapFragment.getView() != null) {
//
//                                                    ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
//                                                    params.height = 850;
//                                                    mapFragment.getView().setLayoutParams(params);
//
//
//
//
//
//                                                }

                                            }
                                        }

                                        else {



                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    // Toast.makeText(phone_login_activity.this,response.toString().length(), Toast.LENGTH_LONG).show();


                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("error", error.toString());
                                }
                            });
                            queue.add(request);


                        }else{
                            // Toast.makeText(introMap.this, "No network coverage ", Toast.LENGTH_LONG).show();
                            no_network.setVisibility(View.VISIBLE);
                            ongoingtrip.setVisibility(View.GONE);
                            current_position.setVisibility(View.GONE);
                            orderalert.setVisibility(View.GONE);
                            msglayout.setVisibility(View.GONE);

                            multiple_board.setVisibility(View.GONE);
//                            if (mapFragment.getView() != null) {
//
//                                ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
//                                params.height = 850;
//                                mapFragment.getView().setLayoutParams(params);
//
//
//
//
//
//                            }
                        }





                        // this method will contain your almost-finished HTTP calls
                        handlerordercheck.postDelayed(this, FIVE_SECONDS);
                    }

                    ///////////////////////////

                }, FIVE_SECONDS);




            }
        }).start();



    }

    public void update_order(){


        String URL = "https://www.troopa.org/api/updateorder/"+order_id;

        queue2 = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (obj.getString("error").equals("false")) {

                     //   Toast.makeText(introMap.this,response, Toast.LENGTH_LONG).show();


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


    public void decline_rider_request(String reason,int order_id){


        String URL = "https://www.troopa.org/api/declineorder/"+order_id+"/"+reason;

        queue2 = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (obj.getString("error").equals("false")) {

                       // Toast.makeText(introMap.this,response, Toast.LENGTH_LONG).show();


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

    public static String getAddress(Context context, double LATITUDE, double LONGITUDE) {

        String address="";

//Set Address
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {



               address = addresses.get(0).getAddressLine(0);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }


    public static String getState(Context context, double LATITUDE, double LONGITUDE) {

        String state="";

//Set Address
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {



//                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
  //             String city = addresses.get(0).getLocality();
                state = addresses.get(0).getAdminArea();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return state;
    }


    public static String getCity(Context context, double LATITUDE, double LONGITUDE) {

        String city="";

//Set Address
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {


          city = addresses.get(0).getLocality();



            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return city;
    }


    public void updateRiderOnlineStatus(String riderid ) {

        String URL = "https://www.troopa.org/api/update-pilot-status/"+riderid;

        queue2 = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (obj.getString("updated").equals("true")) {

                        Toast.makeText(introMap.this,obj.getString("updated"), Toast.LENGTH_LONG).show();


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


    public void updateRiderOnlineStatus(String riderid,String status ) {

        String URL = "https://www.troopa.org/api/update-pilot-status/"+riderid+"/"+status;

        queue2 = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (obj.getString("update").equals("true")) {

                        Toast.makeText(introMap.this,obj.getString("update"), Toast.LENGTH_LONG).show();


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
                            if(status != 0) {
                                todayearning.setText(productObject.getString("t1_amount_format"));
                                // valuesList.add((double) productObject.getDouble("t1_amount"));
                            }
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



    public void takeTimeOnline(String riderid,String time) {

        String URL = "https://www.troopa.org/api/update-online-time/"+riderid+"/"+time;

        queue2 = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (obj.getString("status").equals("ok")) {

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




  public void removeRider(String phonenumber){


      DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
      Query applesQuery = ref.child("RiderLocation").orderByChild("phone").equalTo(phonenumber);

      applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                  appleSnapshot.getRef().removeValue();
              }
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {
             // Log.e(TAG, "onCancelled", databaseError.toException());
          }
      });
  }
//
//    public void onNewToken(@NonNull String token) {
//
//
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // FCM registration token to your app server.
//        sendRegistrationToServer(riderid,token);
//    }

    public void sendRegistrationToServer(String riderid,String msgid){


        String URL = "https://www.troopa.org/api/update-rider-msg-id/"+riderid+"/"+msgid;

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);


                    if (obj.getString("status").equals("ok")) {



                       // Toast.makeText(introMap.this,"done", Toast.LENGTH_LONG).show();


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





    private void alertRider(String userToken,String title,String message,String picture) {
        // url to post our data
        String url = "https://fcm.googleapis.com/fcm/send ";


// Creating string request with post method.
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        // Hiding the progress dialog after all task complete.

                        String resultResponse = new String(response.data);

                        //Toast.makeText(contact_activity.this,resultResponse, Toast.LENGTH_LONG).show();
                        // Showing response message coming from server.
                        if(resultResponse.equals("ok")) {

                            // Toast.makeText(contact_activity.this,user_phone_str, Toast.LENGTH_LONG).show();

                            // writeNewRider(user_phone_str,0.0,0.0);


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {



                        // Showing error message if something goes wrong.
                        Toast.makeText(introMap.this,error.getMessage(), Toast.LENGTH_LONG).show();




                    }
                }){

            @RequiresApi(api = Build.VERSION_CODES.O)
            protected Map<String, String> getParams() throws AuthFailureError {

                JSONObject  params = new JSONObject();
                JSONObject jNotification = new JSONObject();
                JSONObject jData = new JSONObject();


                try {


                jNotification.put("title",title);
                jNotification.put("body", message);
                jNotification.put("sound", "default");
                jNotification.put("badge", "1");
                jNotification.put("click_action", "OPEN_ACTIVITY_1");
                jNotification.put("icon", "troopa_rider_new");

                jData.put("picture", " https://troopa.org/dist/images/troopaRiderNew.fw.png ");
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                // Creating Map String Params.
               // Map<String, String> params = new HashMap<String, String>();
try{
                // Adding All values to Params
                params.put("priority","high");
                params.put("notification",jNotification);
                params.put("data",jData);
                params.put("registration_ids",userToken);
                params.put("Authorization","AAAAeqyWOO4:APA91bH3-gSJVdaEgFOw5AzT6dmUeigtF0aZ_jNlt11kHN9_W8r0AJ2WfKbXhWHGDrwRsUgawMeZQi0QxQkLhdUIG3oM8un69ISInUKzVjhvYkfixosbfdkVObVa4mwprypf91MYekUt ");
                params.put("Content-Type", "application/json");

} catch (JSONException e) {
    e.printStackTrace();
}
                return (Map<String, String>) params;


            }


        };



        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue rQueue = Volley.newRequestQueue(introMap.this);
        rQueue.add(volleyMultipartRequest);

    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        initAllMap();

    }



public void initAllMap(){

    Handler handlerordercheck = new Handler();
    new Thread(new Runnable() {
        @Override
        public void run(){
            // accessing data from database or creating network call



            handlerordercheck.postDelayed(new Runnable() {
                public void run() {


    locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            try {





                lat = Double.parseDouble(Double.toString(location.getLatitude()));
                lng = Double.parseDouble(Double.toString(location.getLongitude()));

                Geocoder geocoder = new Geocoder(introMap.this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                String cityName = addresses.get(0).getLocality();
                String stateName = addresses.get(0).getAdminArea();
                // String countryName = addresses.get(0).getAddressLine(2);

                String address =getCompleteAddressString(lat,lng);

                // Toast.makeText(introMap.this,goOnlineStatus, Toast.LENGTH_LONG).show();
                writeNewRider(riderid,mapTitle, lng, lat,phonenumber,"online","yes",stateName,cityName,address);

                // Write a message to the database





                // to remove old markers
                mMap.clear();
                LatLng latLng = new LatLng(lat,lng);
                CircleOptions circleOptions = new CircleOptions()
                        .center(latLng)   //set center
                        .radius(70)   //set radius in meters
                        .fillColor(Color.parseColor("#FFB973"))  //default
                        .strokeColor(Color.TRANSPARENT)
                        .strokeWidth(5);

                Circle myCircle = mMap.addCircle(circleOptions);


                mMap.addMarker(new MarkerOptions().position(latLng).title(mapTitle + " you are currently here").icon(BitmapDescriptorFactory.fromResource(R.drawable.gmarker)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));



                string_location.setText(getCompleteAddressString(lat,lng));

                takeTimeOnline(riderid,"00:00:05");











            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };



    if (ActivityCompat.checkSelfPermission(introMap.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(introMap.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return;
    }
    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);






                }

                ///////////////////////////

            }, 5000);




        }
    }).start();


    ////////////////////////// here  ///////////////////////
}

}