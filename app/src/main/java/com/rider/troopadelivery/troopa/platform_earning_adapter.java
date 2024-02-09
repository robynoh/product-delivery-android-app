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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class platform_earning_adapter extends ArrayAdapter<platform_earning_list> {

    RequestQueue queue;
    ArrayList<platform_earning_list> products;
    Context context;
    int resource;
    private RadioGroup radioGroup;



    public platform_earning_adapter(Context context, int resource, ArrayList<platform_earning_list> products) {
        super(context, resource, products);
        this.products = products;
        this.context = context;
        this.resource = resource;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.all_platform_earning_list, null, true);

        }
        platform_earning_list product = getItem(position);

//        ImageView image = (ImageView) convertView.findViewById(R.id.newclientimg);
//        Picasso.with(getContext()).load(product.getImage()).into(image);


        TextView clientname = (TextView) convertView.findViewById(R.id.clientname);
        clientname.setText(product.getClientname());

        TextView time = (TextView) convertView.findViewById(R.id.time);

        time.setText(product.getTime());

        TextView type = (TextView) convertView.findViewById(R.id.type);

        type.setText(product.getType());

        TextView packageid = (TextView) convertView.findViewById(R.id.packageid);

        packageid.setText(product.getOrder_serial());

        TextView delivery_fee = (TextView) convertView.findViewById(R.id.delivery_fee);
        delivery_fee.setText("NGN"+ NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(product.getAmount())));

        TextView riderearning = (TextView) convertView.findViewById(R.id.rider_earning);
        delivery_fee.setText("NGN"+ NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(product.getRiderearning())));

        TextView charge = (TextView) convertView.findViewById(R.id.charge);
        charge.setText("NGN"+ NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(product.getPlatform_earning())));


        return convertView;
    }

}

