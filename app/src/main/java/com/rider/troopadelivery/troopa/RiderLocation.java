package com.rider.troopadelivery.troopa;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class RiderLocation {

    public String riderid;
    public String ridername;
    public Double longitude;
    public Double latitude;
    public String phone;
    public String onlinestatus;
    public String availability;
    public String state;
    public String city;
    public String address;

    public RiderLocation() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public RiderLocation(String riderid,String ridername, Double longitude, Double latitude,String phone,String onlinestatus,String availability,String state,String city,String address) {
        this.riderid = riderid;
        this.ridername = ridername;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phone = phone;
        this.onlinestatus =onlinestatus;
        this.availability =availability;
        this.state =state;
        this.city =city;
        this.address =address;
    }

}
