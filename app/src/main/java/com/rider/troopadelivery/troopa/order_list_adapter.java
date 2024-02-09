package com.rider.troopadelivery.troopa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rider.troopadelivery.troopa.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class order_list_adapter extends ArrayAdapter<all_order_list> {
    RequestQueue queue;
    RequestQueue queue2;
    ArrayList<all_order_list> products;
    Context context;
    int resource;
    private RadioGroup radioGroup;
    private order_list_adapter adapter;
    String selectedcompanyoption="";

    public order_list_adapter(Context context, int resource, ArrayList<all_order_list> products) {
        super(context, resource, products);
        this.products = products;
        this.context = context;
        this.resource = resource;
        this.adapter = this;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.alert_order_list, null, true);

        }
        all_order_list product = getItem(position);

//        ImageView image = (ImageView) convertView.findViewById(R.id.newclientimg);
//        Picasso.with(getContext()).load(product.getImage()).into(image);


        TextView clientname = (TextView) convertView.findViewById(R.id.newclientname);
        clientname.setText(product.clientname());

        TextView time = (TextView) convertView.findViewById(R.id.time);
        time.setText(product.time());

        TextView clientpickup = (TextView) convertView.findViewById(R.id.clientpickup);
        clientpickup.setText(product.pickup_location());

        TextView clientdropoff = (TextView) convertView.findViewById(R.id.clientdropoff);
        clientdropoff.setText(product.dropoff_location());

        TextView packagetype = (TextView) convertView.findViewById(R.id.package_type);
        packagetype.setText(product.package_type());

        TextView durationdistance = (TextView) convertView.findViewById(R.id.durationdistance);
        durationdistance.setText(product.durationdistance());

        TextView paymenttype = (TextView) convertView.findViewById(R.id.payment_type);
        paymenttype.setText(product.payment_type());

        TextView paymode = (TextView) convertView.findViewById(R.id.paymode);
        paymode.setText(product.payment_mode());

        LinearLayout declinerequest=(LinearLayout)convertView.findViewById(R.id.declinerequest);
        LinearLayout  acceptrequest=(LinearLayout)convertView.findViewById(R.id. acceptrequest);

        LinearLayout  acceptrequest2=(LinearLayout)convertView.findViewById(R.id. acceptrequest2);


        LinearLayout  declinerequestbg=(LinearLayout)convertView.findViewById(R.id.declinerequestbg);

        LinearLayout  deliveredbg=(LinearLayout)convertView.findViewById(R.id.deliveredbg);

        ImageView  paymodeimg = (ImageView) convertView.findViewById(R.id. paymodeimg);

        if(product.payment_mode().equals("card")) {
            paymodeimg.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cardpay2));

        }
        else if(product.payment_mode().equals("cash")) {
            paymodeimg.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cashpay));

        }

        if(product.delivery_status().equals("accepted")) {

            acceptrequest.setVisibility(View.GONE);
            acceptrequest2.setVisibility(View.VISIBLE);

        }
        if(product.delivery_status().equals("delivered")) {

            declinerequestbg.setVisibility(View.GONE);
            deliveredbg.setVisibility(View.VISIBLE);

        }



        acceptrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                update_order(product.getid());

                Intent i = new Intent(getContext(), client_detail.class);
                i.putExtra("pickup_location", product.pickup_location());
                i.putExtra("dropoff_location", product.dropoff_location());
                i.putExtra("duration", product.duration());
                i.putExtra("distance", product.distance());
                i.putExtra("pickup_contact", product.pickup_contact());
                i.putExtra("dropoff_contact", product.dropoff_contact());
                i.putExtra("client_name", product.clientname());
                i.putExtra("picture", product.getImage());
                i.putExtra("dropoff_contact_name", product.dropoff_contact_name());
                i.putExtra("pickup_contact_name", product.pickup_contact_name());
                i.putExtra("payment_type", product.payment_type());
                i.putExtra("payment_mode", product.payment_mode());
                i.putExtra("amount", product.amount());
                i.putExtra("order_id", product.getid());
                i.putExtra("riderid",product.getriderid());
                //startActivity(i);
                view.getContext().startActivity(i);


            }
        });


        acceptrequest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // update_order(product.getid());

                Intent i = new Intent(getContext(), client_detail.class);
                i.putExtra("pickup_location", product.pickup_location());
                i.putExtra("dropoff_location", product.dropoff_location());
                i.putExtra("duration", product.duration());
                i.putExtra("distance", product.distance());
                i.putExtra("pickup_contact", product.pickup_contact());
                i.putExtra("dropoff_contact", product.dropoff_contact());
                i.putExtra("client_name", product.clientname());
                i.putExtra("picture", product.getImage());
                i.putExtra("dropoff_contact_name", product.dropoff_contact_name());
                i.putExtra("pickup_contact_name", product.pickup_contact_name());
                i.putExtra("payment_type", product.payment_type());
                i.putExtra("payment_mode", product.payment_mode());
                i.putExtra("amount", product.amount());
                i.putExtra("order_id", product.getid());
                i.putExtra("riderid",product.getriderid());
                //startActivity(i);
                view.getContext().startActivity(i);


            }
        });

        declinerequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Toast.makeText(getContext(),product.getid(), Toast.LENGTH_LONG).show();


                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                ViewGroup viewGroup = view.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.decline_dialogue_multiple, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();

                Button declineredbtn = dialogView.findViewById(R.id.declineredbtn);
                declineredbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        RadioButton fuel = dialogView.findViewById(R.id.fuel);
                        RadioButton far = dialogView.findViewById(R.id.far);
                        RadioButton gobreak = dialogView.findViewById(R.id.gobreak);
                        RadioButton breakdown = dialogView.findViewById(R.id.motobreakdown);

                        RadioButton yes_button = dialogView.findViewById(R.id.yescheckbox);
                        RadioButton no_button = dialogView.findViewById(R.id.nocheckbox);


                        if (!fuel.isChecked() && !far.isChecked() && !gobreak.isChecked() && !breakdown.isChecked()) {

                            fuel.setError("Please thick a reason for declining");

                        }
                       else if ( !yes_button.isChecked() && !no_button.isChecked()) {

                            yes_button.setError("Are you declining all the request from this client?");
                            //return false;
                        }
                        else {

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

                           // Toast.makeText(getContext(), reasonForDecline, Toast.LENGTH_LONG).show();


                            if (yes_button.isChecked()) {

                               selectedcompanyoption = yes_button.getText().toString();


                            }

                            if (no_button.isChecked()) {

                                selectedcompanyoption = no_button.getText().toString();


                            }



                            decline_rider_multiple_request(reasonForDecline,Integer. parseInt(product.getid()),product.getriderid(),selectedcompanyoption);
                           //adapter.notifyDataSetChanged();


                            alertDialog.hide();

                        }




                    }
                });
//




                try {

                    alertDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


        return convertView;
    }


    public void decline_rider_multiple_request(String reason,int order_id,String riderid,String option){


        String URL = "https://www.troopa.org/api/declinemultipleorder/"+order_id+"/"+reason+"/"+option;

        queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    if (obj.getString("error").equals("false")) {

                        // Toast.makeText(introMap.this,response, Toast.LENGTH_LONG).show();

//                        Intent myIntent = new Intent(context,recent_order_list.class);
//                        context.startActivity(myIntent);
                        ((Activity)context).finish();

                        Intent myIntent = new Intent(context,recent_order_list.class);
                        myIntent.putExtra("rider_id",riderid);
                        context.startActivity(myIntent);







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

    public void update_order(String order_id){


        String URL = "https://www.troopa.org/api/updateorder/"+order_id;

        queue2 = Volley.newRequestQueue(getContext());
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




}

