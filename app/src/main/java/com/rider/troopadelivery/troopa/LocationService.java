package com.rider.troopadelivery.troopa;

public class LocationService {
    private final Double latitude;
    private final Double longitude;

    public LocationService(Double latitude,Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }




}
