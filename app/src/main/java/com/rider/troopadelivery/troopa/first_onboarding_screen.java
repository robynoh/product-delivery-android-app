package com.rider.troopadelivery.troopa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

//import com.codewithgolap.imageslider.HomeActivity;
//import com.codewithgolap.imageslider.R;
import com.google.android.material.button.MaterialButton;
import com.rider.troopadelivery.troopa.R;

import java.util.ArrayList;
import java.util.List;

public class first_onboarding_screen extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicator;
    private MaterialButton buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_onboarding_screen);

        layoutOnboardingIndicator = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnBoardingAction);

        setOnboardingItem();

        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setOnboadingIndicator();
        setCurrentOnboardingIndicators(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicators(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                } else {
                    startActivity(new Intent(getApplicationContext(), introMap.class));
                    finish();
                }
            }
        });
    }

    private void setOnboadingIndicator() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(), R.drawable.gray_delivery_btn
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicator.addView(indicators[i]);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentOnboardingIndicators(int index) {
        int childCount = layoutOnboardingIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutOnboardingIndicator.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.round_button));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gray_delivery_btn));
            }
        }
        if (index == onboardingAdapter.getItemCount()-1){
            buttonOnboardingAction.setText("Start");
        }else {
            buttonOnboardingAction.setText("Next");
        }
    }

    private void setOnboardingItem() {
        List<onBoardingItem> onBoardingItems = new ArrayList<>();

        onBoardingItem itemFastFood = new onBoardingItem();
        itemFastFood.setTitle("Fast Delivery to your home");
        itemFastFood.setDescription("Our delivery partner is on the way to your home!");
        itemFastFood.setImage(R.drawable.slide1);

        onBoardingItem itemPayOnline = new onBoardingItem();
        itemPayOnline.setTitle("E-Pay your bill online");
        itemPayOnline.setDescription("Electric bill payment is a feature of online, mobile and net banking!");
        itemPayOnline.setImage(R.drawable.slide2);

        onBoardingItem itemEatTogether = new onBoardingItem();
        itemEatTogether.setTitle("Eat together");
        itemEatTogether.setDescription("Enjoy your meal and have a great day. Don’t forget to rate us!");
        itemEatTogether.setImage(R.drawable.slide3);

        onBoardingItems.add(itemFastFood);
        onBoardingItems.add(itemPayOnline);
        onBoardingItems.add(itemEatTogether);

        onboardingAdapter = new OnboardingAdapter(onBoardingItems);

    }

}