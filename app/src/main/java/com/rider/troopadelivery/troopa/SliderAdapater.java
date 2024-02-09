package com.rider.troopadelivery.troopa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.rider.troopadelivery.troopa.R;

class SliderAdapter extends PagerAdapter {


    Context context ;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;

    }

    int images[] = {R.drawable.slide1,R.drawable.slide2,R.drawable.slide3};
    String textTitle[] = {"Get easy request from any location","Deliver to multiple customers with ease","Earn big as you deliver"};
    String textDescription[] = {"Get easy request from any location you find yourself","Troopa makes you deliver to multiple customers with ease","Troopa makes it possible for you to earn big as you deliver to your customers"};


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(LinearLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.slides_layout,container,false);
        ImageView imageView=view.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);

        TextView textTitle1=view.findViewById(R.id.textTitle);
        textTitle1.setText(textTitle[position]);

        TextView textDescription1=view.findViewById(R.id.textDescription);
        textDescription1.setText(textDescription[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
