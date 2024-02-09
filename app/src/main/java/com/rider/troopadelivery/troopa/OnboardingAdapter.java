package com.rider.troopadelivery.troopa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rider.troopadelivery.troopa.R;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>{


    public OnboardingAdapter(List<onBoardingItem> onBoardingItems) {
    }

    @NonNull
    @Override
    public OnboardingAdapter.OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_boarding_one, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingAdapter.OnboardingViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class OnboardingViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private TextView textDescription;
        private ImageView imageOnboarding;

        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
            imageOnboarding = itemView.findViewById(R.id.imageOnboarding);
        }

        void setOnBoardingData(onBoardingItem onBoardingItem){
            textTitle.setText(onBoardingItem.getTitle());
            textDescription.setText(onBoardingItem.getDescription());
            imageOnboarding.setImageResource(onBoardingItem.getImage());
        }
    }
}



