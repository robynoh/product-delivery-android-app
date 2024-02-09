package com.rider.troopadelivery.troopa;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import org.greenrobot.eventbus.EventBus;

public class troopaRiderBackgroundService extends Service {

    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 2;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



                LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        try {


                            double lat = Double.parseDouble(Double.toString(location.getLatitude()));
                            double lng = Double.parseDouble(Double.toString(location.getLongitude()));


//                           String  phone=intent.getStringExtra("phonenumber");
//                            String riderid = intent.getStringExtra("riderid");
//                            String maptitle = intent.getStringExtra("maptitle");


                            EventBus.getDefault().post(new LocationService(lat,lng));

                            Log.e("Service", String.valueOf(lat));




                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                };


                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return flags;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);






        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

}
