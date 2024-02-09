package com.rider.troopadelivery.troopa;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rider.troopadelivery.troopa.R;

public class MapFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view=inflater.inflate(R.layout.fragment_map, container, false);

         // Initialize map fragment
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);


  //////////////////////////////////////////////////////////////////////////////////////////////////////////
       Toast.makeText(getActivity(), "working", Toast.LENGTH_LONG).show();

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {


            @Override
            public void onMapReady(GoogleMap googleMap) {

                // When map is loaded
                LatLng latLng= new LatLng(9.072264,7.491302);
                MarkerOptions markerOptions=new MarkerOptions();
                // Set position of marker
                 markerOptions.position(latLng);
                // Set title of marker
               markerOptions.title(latLng.latitude+" : "+latLng.longitude);
                // Remove all marker
               googleMap.clear();
                // Animating to zoom the marker
               googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                // Add marker on map
                googleMap.addMarker(markerOptions);



            }
        });
        // Return view
        return view;
    }




}