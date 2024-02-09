package com.rider.troopadelivery.troopa;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.android.volley.RequestQueue;
import com.rider.troopadelivery.troopa.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class card_payment_list_adapter extends ArrayAdapter<all_card_payments> {
    RequestQueue queue;
    ArrayList<all_card_payments> products;
    Context context;
    int resource;
    private RadioGroup radioGroup;
    private card_payment_list_adapter adapter;


    public card_payment_list_adapter(Context context, int resource, ArrayList<all_card_payments> products) {
        super(context, resource, products);
        this.products = products;
        this.context = context;
        this.resource = resource;
        this.adapter = this;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.card_payment_list, null, true);

        }
        all_card_payments product = getItem(position);

//        ImageView image = (ImageView) convertView.findViewById(R.id.newclientimg);
//        Picasso.with(getContext()).load(product.getImage()).into(image);


        TextView order_serial = (TextView) convertView.findViewById(R.id.order_serial);
        order_serial.setText(product.getserial());

        TextView order_time = (TextView) convertView.findViewById(R.id.order_time);

        order_time.setText(product.request_time());

        TextView amount = (TextView) convertView.findViewById(R.id.order_amount);
        amount.setText("N"+ NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(product.amount())));

//        TextView durationvalue = (TextView) convertView.findViewById(R.id.durationvalue);
//        durationvalue.setText(product.durationdistance());
//
//        TextView dropoffvaluex = (TextView) convertView.findViewById(R.id.dropoffvalue);
//        dropoffvaluex.setText(product.dropoff_location());
//
//        TextView packagevalue = (TextView) convertView.findViewById(R.id.packagevalue);
//        packagevalue.setText(product.package_type());
//
//
//
//
//        TextView deliverystatus = (TextView) convertView.findViewById(R.id.deliverstatus);
//        deliverystatus.setText(product.deliverystatus());


//        LinearLayout tripcall=(LinearLayout)convertView.findViewById(R.id.tripcall);
//
//        LinearLayout view_location_map= convertView.findViewById(R.id.view_location_map);
//
//        view_location_map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(getContext(), client_detail.class);
//                i.putExtra("pickup_location", product.pickup_location());
//                i.putExtra("dropoff_location", product.dropoff_location());
//                i.putExtra("duration", product.duration());
//                i.putExtra("distance", product.distance());
//                i.putExtra("pickup_contact", product.pickup_contact());
//                i.putExtra("dropoff_contact", product.dropoff_contact());
//                i.putExtra("client_name", product.clientname());
//                i.putExtra("picture", product.getImage());
//                i.putExtra("dropoff_contact_name", product.dropoff_contact_name());
//                i.putExtra("pickup_contact_name", product.pickup_contact_name());
//                i.putExtra("order_id", product.getid());
//                i.putExtra("riderid",product.getriderid());
//                //startActivity(i);
//                view.getContext().startActivity(i);
//
//            }
//        });
//
//
//
//
//        tripcall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                ViewGroup viewGroup = view.findViewById(android.R.id.content);
//                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.contact_dialogue, viewGroup, false);
//                builder.setView(dialogView);
//                AlertDialog alertDialog = builder.create();
//
//                TextView pickupname= dialogView.findViewById(R.id.pickupname);
//                TextView dropoffname= dialogView.findViewById(R.id.dropoffname);
//
//                pickupname.setText(product.pickup_contact_name());
//                dropoffname.setText(product.dropoff_contact_name());
//
//                LinearLayout callpickup = dialogView.findViewById(R.id.call_pick_up);
//
//                callpickup.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        String number=product.pickup_contact();
//                        Uri call = Uri.parse("tel:"+number);
//                        Intent surf = new Intent(Intent.ACTION_DIAL,call);
//                        view.getContext().startActivity(surf);
//                    }
//                });
//
//                LinearLayout call_drop_off = dialogView.findViewById(R.id.call_drop_off);
//
//                call_drop_off.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        String number=product.dropoff_contact();
//                        Uri call = Uri.parse("tel:"+number);
//                        Intent surf = new Intent(Intent.ACTION_DIAL,call);
//                        view.getContext().startActivity(surf);
//                    }
//                });
//
//
//
//                alertDialog.show();
//
//
//
//            }
//        });




//        acceptrequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(getContext(), client_detail.class);
//                i.putExtra("pickup_location", product.pickup_location());
//                i.putExtra("dropoff_location", product.dropoff_location());
//                i.putExtra("duration", product.duration());
//                i.putExtra("distance", product.distance());
//                i.putExtra("pickup_contact", product.pickup_contact());
//                i.putExtra("dropoff_contact", product.dropoff_contact());
//                i.putExtra("client_name", product.clientname());
//                i.putExtra("picture", product.getImage());
//                i.putExtra("dropoff_contact_name", product.dropoff_contact_name());
//                i.putExtra("pickup_contact_name", product.pickup_contact_name());
//                i.putExtra("order_id", product.getid());
//                i.putExtra("riderid",product.getriderid());
//                //startActivity(i);
//                view.getContext().startActivity(i);
//
//
//            }
//        });




        return convertView;
    }



}
