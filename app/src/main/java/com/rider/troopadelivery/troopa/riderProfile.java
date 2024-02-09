package com.rider.troopadelivery.troopa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.rider.troopadelivery.troopa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class riderProfile extends AppCompatActivity {
    private DatabaseReference databaseReference;
    LinearLayout profileLogout,openhelp,ongoingtrip,ongoing_contact;
    String goOnlineStatus="stop";
    RequestQueue queue2;
    String pick_up_name,drop_off_name,pick_up_contact,dropoff_contact;
    LinearLayout call_pick_up;
    LinearLayout call_drop_off,view_location_map;
    TextView   todayearning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_profile);

        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Profile");


        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);

        String phone = sharedPreferences.getString("phone", "");
        String email = sharedPreferences.getString("email", "");
        String ridername = sharedPreferences.getString("name", "");
        String address = sharedPreferences.getString("address", "");
        String picture = sharedPreferences.getString("picture", "");
        String riderid = sharedPreferences.getString("pilotID", "");
        String license = sharedPreferences.getString("license", "");
        String date = sharedPreferences.getString("date", "");

        TextView rider_name= findViewById(R.id.rider_name);
        TextView license_number= findViewById(R.id.license_number);
        TextView date_created= findViewById(R.id.date_created);

        view_location_map=findViewById(R.id.view_location_map);
        todayearning = findViewById(R.id.todayearning);
        ongoingtrip=findViewById(R.id.ongoingtrip);
        ongoing_contact=findViewById(R.id.ongoing_contact);
        //TextView rider_id= findViewById(R.id.rider_id);
        //TextView rider_phone= findViewById(R.id.rider_phone);
        ImageView profileimg= findViewById(R.id.profile_image);
        Picasso.with(riderProfile.this).load(picture).into(profileimg);


       // EditText riderName= findViewById(R.id.riderName);
        //EditText riderEmail= findViewById(R.id.riderEmail);
        //EditText riderAddress= findViewById(R.id.riderAddress);

        rider_name.setText(ridername);
        //rider_id.setText(riderid);
       // rider_phone.setText(phone);
        license_number.setText(license);


        date_created.setText(date);

        getongoingtrip(riderid);
        all_trips(riderid);

/////////////////////////// pull today rarning
        todayearningvalue(riderid);
       // riderName.setText(ridername);
       // riderEmail.setText(email);
       // riderAddress.setText(address);


        view_location_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String URL ="https://www.troopa.org/api/pickuprequest/"+riderid;
                queue2 = Volley.newRequestQueue(riderProfile.this);
                StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(response);
                            if (obj.getString("error").equals("false")) {

                                Intent i = new Intent(riderProfile.this, client_detail.class);
                                i.putExtra("pickup_location", obj.getString("pickup_location"));
                                i.putExtra("dropoff_location", obj.getString("dropoff_location"));
                                i.putExtra("duration", obj.getString("duration"));
                                i.putExtra("distance", obj.getString("distance"));
                                i.putExtra("pickup_contact", obj.getString("pickup_contact"));
                                i.putExtra("dropoff_contact", obj.getString("dropoff_contact"));
                                i.putExtra("client_name", obj.getString("client_name"));
                                i.putExtra("picture", obj.getString("picture"));
                                i.putExtra("dropoff_contact_name", obj.getString("dropoff_contact_name"));
                                i.putExtra("pickup_contact_name", obj.getString("pickup_contact_name"));
                                i.putExtra("order_id",obj.getString("order_id"));
                                i.putExtra("payment_type",obj.getString("payment_type"));
                                i.putExtra("payment_mode",obj.getString("payment_mode"));
                                i.putExtra("riderid",riderid);
                                startActivity(i);
                                finish();


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


        ongoing_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(riderProfile.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.contact_dialogue, viewGroup, false);
                TextView pickupname= dialogView.findViewById(R.id.pickupname);

                call_pick_up=dialogView.findViewById(R.id.call_pick_up);
                call_drop_off=dialogView.findViewById(R.id.call_drop_off);

                pickupname.setText(pick_up_name);

                TextView dropoffname= dialogView.findViewById(R.id.dropoffname);

                dropoffname.setText(drop_off_name);

                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();


                call_pick_up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String number=pick_up_contact;
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


        profileLogout= findViewById(R.id.profileLogout);
        openhelp= findViewById(R.id.openhelp);

        openhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(riderProfile.this, help_center.class);
                startActivity(i);
            }
        });

        profileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calling method to edit values in shared prefs.
                SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                SharedPreferences sharedPrefer = getSharedPreferences("goOnlineKey", MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPrefer.edit();


                // below line will clear
                // the data in shared prefs.
                editor.clear();
                editor2.remove("onlineActivate");
                editor2.commit();

                // below line will apply empty
                // data to shared prefs.
                editor.apply();


               // writeNewRider(riderid,ridername, Double.parseDouble("0.00"), Double.parseDouble("0.00"),phone,"offline","no","","","");



                removeRider(phone);

                // starting mainactivity after
                // clearing values in shared preferences.
                Intent a = new Intent(riderProfile.this, login_activity.class);
                startActivity(a);
                finish();

            }
        });

    }

    public void writeNewRider(String riderid, String mapTitle, Double longitude, Double latitude, String phone, String online, String availability,String state,String city,String address) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("RiderLocation");
        RiderLocation riderLocation = new RiderLocation(riderid,mapTitle,longitude, latitude,phone,online,availability,state,city,address);
        databaseReference.child(phone).setValue(riderLocation);





    }

    public void getongoingtrip(String riderid){


        String URL = "https://www.troopa.org/api/pickuprequest/"+riderid;

        queue2 = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);



                    if (obj.getString("error").equals("false")) {

                      //  Toast.makeText(riderProfile.this,"here", Toast.LENGTH_LONG).show();


                        if (obj.getInt("count")>0 && obj.getInt("count")<2) {

                            if (obj.getString("status").equals("accepted")) {



                                String client_namex = obj.getString("client_name");
                                String picture = obj.getString("picture");

                                pick_up_contact = obj.getString("pickup_contact");
                                dropoff_contact = obj.getString("dropoff_contact");
                                drop_off_name = obj.getString("dropoff_contact_name");
                                pick_up_name = obj.getString("pickup_contact_name");
                                String pickup_location = obj.getString("pickup_location");
                                String dropoff_location = obj.getString("dropoff_location");
                                String distance = obj.getString("distance");
                                String date = obj.getString("time");
                                String duration = obj.getString("duration");


                                // ImageView clientimgongoing = (ImageView) findViewById(R.id.clientimgongoing);
                                // Picasso.with(introMap.this).load(picture).into(clientimgongoing);

                                TextView clientname = findViewById(R.id.clientname);
                                clientname.setText(client_namex);

                                TextView ongoing_time = findViewById(R.id.ongoing_time);
                                ongoing_time.setText(date);

                                ongoingtrip.setVisibility(View.VISIBLE);



                            }
                        }
                      //  Toast.makeText(introMap.this,response, Toast.LENGTH_LONG).show();


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


    public void all_trips(String riderid){


        String URL = "https://www.troopa.org/api/clientorder/"+riderid;

        queue2 = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (obj.getString("error").equals("false")) {



                        TextView tripcount = findViewById(R.id.tripcount);
                        tripcount.setText(obj.getString("count"));

                       // ongoingtrip.setVisibility(View.VISIBLE);


                       // Toast.makeText(riderProfile.this,obj.getString("count"), Toast.LENGTH_LONG).show();


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

    public boolean onSupportNavigateUp(){

        SharedPreferences sharedPrefer = getSharedPreferences("goOnlineKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefer.edit();

        editor.putString("onlineActivate", "start");
        // to save our data with key and value.
        editor.commit();

        onBackPressed();
        return true;
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
}