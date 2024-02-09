package com.rider.troopadelivery.troopa;


public class fragment1_all_confirmed {

    private String riderid;
    private String order_serial;
    private String clientname;
    private String time;
    private String pickup_location;
    private String dropoff_location;
    private String package_type;
    private String payment_type;
    private String payment_mode;
    private String distance;
    private String duration;
    private String pickup_contact_name;
    private String dropoff_contact_name;
    private String pickup_contact;
    private String dropoff_contact;
    private String durationdistance;
    private String amount;
    private String request_date;
    private String request_time;


    // private String distance;


    public fragment1_all_confirmed(String riderid,String order_serial, String clientname,String time,String pickup_location,String dropoff_location,String package_type,String payment_type,String payment_mode,String distance,String duration,String pickup_contact,String dropoff_contact,String pickup_contact_name,String dropoff_contact_name,String durationdistance,String amount,String request_date,String request_time) {
        this.riderid = riderid;
        this.order_serial = order_serial;
        this.clientname = clientname;
        this.time = time;
        this.pickup_location = pickup_location;
        this.dropoff_location = dropoff_location;
        this.package_type = package_type;
        this.payment_type = payment_type;
        this.payment_mode = payment_mode;
        this.distance = distance;
        this.duration = duration;
        this.pickup_contact = pickup_contact;
        this.dropoff_contact = dropoff_contact;
        this.pickup_contact_name = pickup_contact_name;
        this.dropoff_contact_name = dropoff_contact_name;
        this.durationdistance= durationdistance;
        this.amount = amount;
        this.request_date = request_date;
        this.request_time = request_time;
    }

    public String getriderid() {
        return riderid;
    }
    public String getserial() {
        return order_serial;
    }
    public String clientname() {
        return clientname;
    }
    public String time() {
        return time;
    }
    public String pickup_location() {
        return pickup_location;
    }
    public String dropoff_location() {
        return dropoff_location;
    }

    public String  package_type() {
        return  package_type;
    }
    public String  payment_type() {
        return  payment_type;
    }
    public String  payment_mode() {
        return  payment_mode;
    }

    public String  distance() {
        return  distance;
    }

    public String  duration() {
        return  duration;
    }

    public String  pickup_contact_name() {
        return  pickup_contact_name;
    }

    public String  dropoff_contact_name() {
        return  dropoff_contact_name;
    }

    public String  dropoff_contact() {
        return  dropoff_contact;
    }

    public String  pickup_contact() {
        return  pickup_contact;
    }
    public String  durationdistance() {
        return  durationdistance;
    }

    public String  amount() {
        return  amount;
    }

    public String  request_date() {
        return  request_date;
    }

    public String  request_time() {
        return  request_time;
    }



}
