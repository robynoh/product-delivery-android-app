package com.rider.troopadelivery.troopa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rider.troopadelivery.troopa.R;

import org.json.JSONException;
import org.json.JSONObject;

public class help_center extends AppCompatActivity {
    RequestQueue queue;
    LinearLayout rescue_form,technical_form,rescue_container,pickup_form;

    ImageView show_technical_form,hide_technical_form;
    LinearLayout technical_container,delivery_container,delivery_form,pickup_container;
    ImageView show_rescue_form;
    ImageView hide_rescue_form;
    ImageView hide_delivery_form;
    ImageView show_delivery_form;
    ImageView show_pickup_form;
    ImageView  hide_pickup_form;

    Button submit_rescue;
    Button submit_tech_issues;
    Button submit_del_issues;
    Button submit_pick_issues;
    Button delivered;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);

        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Help Center");

        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String riderid = sharedPreferences.getString("pilotID", "");

        show_rescue_form=findViewById(R.id.show_rescue_form);
        hide_rescue_form=findViewById(R.id.hide_rescue_form);
        technical_container=findViewById(R.id.technical_container);
        technical_form=findViewById(R.id.technical_form);
        show_technical_form=findViewById(R.id.show_technical_form);
        hide_technical_form=findViewById(R.id.hide_technical_form);
        loadingPB = findViewById(R.id.idLoadingPB);

        submit_rescue=findViewById(R.id.submit_rescue);
        submit_tech_issues=findViewById(R.id.submit_tech_issues);
        submit_del_issues=findViewById(R.id.submit_del_issues);
        submit_pick_issues=findViewById(R.id.submit_pick_issues);

        delivery_form=findViewById(R.id.delivery_form);
        show_delivery_form=findViewById(R.id.show_delivery_form);
        hide_delivery_form=findViewById(R.id.hide_delivery_form);
        delivery_container=findViewById(R.id.delivery_container);
        rescue_container=findViewById(R.id.rescue_container);

        pickup_form=findViewById(R.id.pickup_form);
        pickup_container=findViewById(R.id.pickup_container);
        hide_pickup_form=findViewById(R.id.hide_pickup_form);
        show_pickup_form=findViewById(R.id.show_pickup_form);



        // Gets linearlayout
        rescue_form=findViewById(R.id.rescue_form);


        show_pickup_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewGroup.LayoutParams params =  pickup_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                params.height = params.WRAP_CONTENT;
                pickup_container.setLayoutParams(params);

                pickup_form.setVisibility(View.VISIBLE);
                show_pickup_form.setVisibility(View.INVISIBLE);
                hide_pickup_form.setVisibility(View.VISIBLE);

                technical_form.setVisibility(View.INVISIBLE);
                rescue_form.setVisibility(View.INVISIBLE);
                delivery_form.setVisibility(View.INVISIBLE);

                submit_pick_issues.setVisibility(View.VISIBLE);
                submit_rescue.setVisibility(View.GONE);
                submit_tech_issues.setVisibility(View.GONE);
                submit_del_issues.setVisibility(View.GONE);




                submit_pick_issues.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        EditText pick_title = findViewById(R.id.pick_title);
                        String pick_title_value=pick_title.getText().toString();

                        EditText pick_desc = findViewById(R.id.pick_desc);
                        String pick_desc_value=pick_desc.getText().toString();

                        if ( pick_title.length() == 0) {

                            TextView errormsg=findViewById(R.id.errormsg);
                            errormsg.setText("Enter title of pickup issue");


                        }

                        else if ( pick_desc.length() == 0) {

                            TextView errormsg=findViewById(R.id.errormsg);
                            errormsg.setText("Enter description of pickup issue");


                        }else{

                            ConnectivityManager connMgr = (ConnectivityManager) help_center.this
                                    .getSystemService(Context.CONNECTIVITY_SERVICE);

                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                            if (networkInfo != null && networkInfo.isConnected()) {

                                submitissues(riderid, pick_title_value, pick_desc_value, "pickup");
                            }
                            else{
                                Toast.makeText(help_center.this,"No network", Toast.LENGTH_LONG).show();


                            }
                        }


                     }
                });


                ViewGroup.LayoutParams paramstech=  technical_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                paramstech.height = 300;
                technical_container.setLayoutParams(paramstech);


                ViewGroup.LayoutParams paramsdel =  delivery_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                paramsdel.height = 300;
                delivery_container.setLayoutParams(paramsdel);

                ViewGroup.LayoutParams paramsrescue =  rescue_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                paramsrescue.height = 300;
                rescue_container.setLayoutParams(paramsrescue);


            }
        });


        hide_pickup_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewGroup.LayoutParams params =  pickup_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                params.height = 300;
                pickup_container.setLayoutParams(params);

                hide_pickup_form.setVisibility(View.INVISIBLE);
                show_pickup_form.setVisibility(View.VISIBLE);
                pickup_form.setVisibility(View.INVISIBLE);

                submit_pick_issues.setVisibility(View.GONE);
                submit_rescue.setVisibility(View.GONE);
                submit_tech_issues.setVisibility(View.GONE);
                submit_del_issues.setVisibility(View.GONE);
            }
        });




        show_delivery_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewGroup.LayoutParams params =  delivery_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                params.height = params.WRAP_CONTENT;
                delivery_container.setLayoutParams(params);

                delivery_form.setVisibility(View.VISIBLE);
                show_delivery_form.setVisibility(View.INVISIBLE);
                hide_delivery_form.setVisibility(View.VISIBLE);
                technical_form.setVisibility(View.INVISIBLE);
                rescue_form.setVisibility(View.INVISIBLE);
                pickup_form.setVisibility(View.INVISIBLE);

                submit_pick_issues.setVisibility(View.GONE);
                submit_rescue.setVisibility(View.GONE);
                submit_tech_issues.setVisibility(View.GONE);
                submit_del_issues.setVisibility(View.VISIBLE);


                submit_del_issues.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        EditText del_title = findViewById(R.id.del_title);
                        String del_title_value=del_title.getText().toString();

                        EditText del_desc = findViewById(R.id.del_desc);
                        String del_desc_value=del_desc.getText().toString();

                        if ( del_title.length() == 0) {

                            TextView errormsg=findViewById(R.id.errormsg);
                            errormsg.setText("Enter title of delivery issue");


                        }

                        else if (del_desc.length() == 0) {

                            TextView errormsg=findViewById(R.id.errormsg);
                            errormsg.setText("Enter description of delivery issue");


                        }else{




                            ConnectivityManager connMgr = (ConnectivityManager) help_center.this
                                    .getSystemService(Context.CONNECTIVITY_SERVICE);

                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                            if (networkInfo != null && networkInfo.isConnected()) {

                                submitissues(riderid,del_title_value,del_desc_value,"delivery");
                            }
                            else{
                                Toast.makeText(help_center.this,"No network", Toast.LENGTH_LONG).show();


                            }



                        }


                    }
                });



                ViewGroup.LayoutParams paramstech=  technical_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                paramstech.height = 300;
                technical_container.setLayoutParams(paramstech);


                ViewGroup.LayoutParams paramsrescue =  rescue_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                paramsrescue.height = 300;
                rescue_container.setLayoutParams(paramsrescue);

                ViewGroup.LayoutParams paramspick =  pickup_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                paramspick.height = 300;
                pickup_container.setLayoutParams(paramspick);

            }
        });

        hide_delivery_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewGroup.LayoutParams params =  delivery_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                params.height = 300;
                delivery_container.setLayoutParams(params);

                hide_delivery_form.setVisibility(View.INVISIBLE);
                show_delivery_form.setVisibility(View.VISIBLE);
                delivery_form.setVisibility(View.INVISIBLE);

                submit_pick_issues.setVisibility(View.GONE);
                submit_rescue.setVisibility(View.GONE);
                submit_tech_issues.setVisibility(View.GONE);
                submit_del_issues.setVisibility(View.GONE);
            }
        });


        show_technical_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ViewGroup.LayoutParams params =  technical_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                params.height = params.WRAP_CONTENT;
                technical_container.setLayoutParams(params);

                technical_form.setVisibility(View.VISIBLE);
                show_technical_form.setVisibility(View.INVISIBLE);
                hide_technical_form.setVisibility(View.VISIBLE);
                rescue_form.setVisibility(View.INVISIBLE);
                delivery_form.setVisibility(View.INVISIBLE);
                pickup_form.setVisibility(View.INVISIBLE);

                submit_pick_issues.setVisibility(View.GONE);
                submit_rescue.setVisibility(View.GONE);
                submit_tech_issues.setVisibility(View.VISIBLE);
                submit_del_issues.setVisibility(View.GONE);

                submit_tech_issues.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        EditText tech_title = findViewById(R.id.tech_title);
                        String tech_title_value=tech_title.getText().toString();

                        EditText tech_desc = findViewById(R.id.tech_desc);
                        String tech_desc_value=tech_desc.getText().toString();

                        if ( tech_title.length() == 0) {

                            TextView errormsg=findViewById(R.id.errormsg);
                            errormsg.setText("Enter title of technical issue");


                        }

                        else if ( tech_desc.length() == 0) {

                            TextView errormsg=findViewById(R.id.errormsg);
                            errormsg.setText("Enter description of technical issue");


                        }else{


                            ConnectivityManager connMgr = (ConnectivityManager) help_center.this
                                    .getSystemService(Context.CONNECTIVITY_SERVICE);

                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                            if (networkInfo != null && networkInfo.isConnected()) {

                                submitissues(riderid,tech_title_value,tech_desc_value,"technical");
                            }
                            else{
                                Toast.makeText(help_center.this,"No network", Toast.LENGTH_LONG).show();


                            }



                        }


                    }
                });



                ViewGroup.LayoutParams paramsrescue=  rescue_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                paramsrescue.height = 300;
                rescue_container.setLayoutParams(paramsrescue);


                ViewGroup.LayoutParams paramsdel =  delivery_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                paramsdel.height = 300;
                delivery_container.setLayoutParams(paramsdel);

                ViewGroup.LayoutParams paramspick =  pickup_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                paramspick.height = 300;
                pickup_container.setLayoutParams(paramspick);

            }
        });

        hide_technical_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewGroup.LayoutParams params =  technical_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                params.height = 300;
                technical_container.setLayoutParams(params);

                hide_technical_form.setVisibility(View.INVISIBLE);
                show_technical_form.setVisibility(View.VISIBLE);
                technical_form.setVisibility(View.INVISIBLE);

                submit_pick_issues.setVisibility(View.GONE);
                submit_rescue.setVisibility(View.GONE);
                submit_tech_issues.setVisibility(View.GONE);
                submit_del_issues.setVisibility(View.GONE);


            }
        });

// Gets the layout params that will allow you to resize the layout



        show_rescue_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewGroup.LayoutParams params =  rescue_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                params.height = params.WRAP_CONTENT;
                rescue_container.setLayoutParams(params);

                rescue_form.setVisibility(View.VISIBLE);
                hide_rescue_form.setVisibility(View.VISIBLE);

                show_rescue_form.setVisibility(View.INVISIBLE);
                technical_form.setVisibility(View.INVISIBLE);
                delivery_form.setVisibility(View.INVISIBLE);
                pickup_form.setVisibility(View.INVISIBLE);

                submit_pick_issues.setVisibility(View.GONE);
                submit_rescue.setVisibility(View.VISIBLE);
                submit_tech_issues.setVisibility(View.GONE);
                submit_del_issues.setVisibility(View.GONE);

                submit_rescue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        EditText rescue_title = findViewById(R.id.rescue_title);
                        String rescue_title_value=rescue_title.getText().toString();

                        EditText rescue_desc = findViewById(R.id.rescue_desc);
                        String recue_desc_value=rescue_desc.getText().toString();

                        if ( rescue_title.length() == 0) {

                            TextView errormsg=findViewById(R.id.errormsg);
                            errormsg.setText("Enter title of rescue issue");


                        }

                        else if ( rescue_desc.length() == 0) {

                            TextView errormsg=findViewById(R.id.errormsg);
                            errormsg.setText("Enter description of rescue issue");


                        }else{


                            ConnectivityManager connMgr = (ConnectivityManager) help_center.this
                                    .getSystemService(Context.CONNECTIVITY_SERVICE);

                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                            if (networkInfo != null && networkInfo.isConnected()) {

                                submitissues(riderid,rescue_title_value,recue_desc_value,"rescue");
                            }
                            else{
                                Toast.makeText(help_center.this,"No network", Toast.LENGTH_LONG).show();


                            }




                        }


                    }
                });


                ViewGroup.LayoutParams paramstech =  technical_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                paramstech.height = 300;
                technical_container.setLayoutParams(paramstech);


                ViewGroup.LayoutParams paramsdel =  delivery_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                paramsdel.height = 300;
                delivery_container.setLayoutParams(paramsdel);

                ViewGroup.LayoutParams paramspick =  pickup_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                paramspick.height = 300;
                pickup_container.setLayoutParams(paramspick);

              //  ViewGroup.LayoutParams params = rescue_form.getLayoutParams();
// Changes the height and width to the specified *pixels*
              //  params.height = params.WRAP_CONTENT;
              //  rescue_form.setLayoutParams(params);
            }
        });


        hide_rescue_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewGroup.LayoutParams params =  rescue_container.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                params.height = 300;
                rescue_container.setLayoutParams(params);


                rescue_form.setLayoutParams(params);
                hide_rescue_form.setVisibility(View.INVISIBLE);
                show_rescue_form.setVisibility(View.VISIBLE);
                rescue_form.setVisibility(View.INVISIBLE);

                submit_pick_issues.setVisibility(View.GONE);
                submit_rescue.setVisibility(View.GONE);
                submit_tech_issues.setVisibility(View.GONE);
                submit_del_issues.setVisibility(View.GONE);
            }
        });
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }


    public void submitissues(String riderid,String title,String description,String type){



        String URL = "https://www.troopa.org/api/support-issue/"+riderid+"/"+title+"/"+description+"/"+type;

        queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (obj.getString("status").equals("ok")) {



                            loadingPB.setVisibility(View.GONE);

                            AlertDialog.Builder builder = new AlertDialog.Builder(help_center.this);
                            ViewGroup viewGroup = findViewById(android.R.id.content);
                            View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.submit_issue_ticket, viewGroup, false);
                            builder.setView(dialogView);

                            delivered = dialogView.findViewById(R.id.delivered);
                            AlertDialog alertDialog = builder.create();
                            alertDialog.setCanceledOnTouchOutside(false);

                            delivered.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(help_center.this, riderProfile.class);
                                    startActivity(i);
                                    finish();
                                }
                            });
                            alertDialog.show();




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