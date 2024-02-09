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

public class statment_list_adapter extends ArrayAdapter<statement_list> {

    RequestQueue queue;
    ArrayList<statement_list> products;
    Context context;
    int resource;
    private RadioGroup radioGroup;



    public statment_list_adapter(Context context, int resource, ArrayList<statement_list> products) {
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
            convertView = layoutInflater.inflate(R.layout.all_withdrawer_list, null, true);

        }
        statement_list product = getItem(position);

//        ImageView image = (ImageView) convertView.findViewById(R.id.newclientimg);
//        Picasso.with(getContext()).load(product.getImage()).into(image);


        TextView time = (TextView) convertView.findViewById(R.id.time);
        time.setText(product.gettime());

        TextView amount = (TextView) convertView.findViewById(R.id.amount);

        amount.setText("NGN"+ NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(product.getamount())));

        TextView balance = (TextView) convertView.findViewById(R.id.balance);
        balance.setText("NGN"+ NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(product.getbalance())));


        return convertView;
    }

}
