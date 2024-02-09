package com.rider.troopadelivery.troopa;

public class all_real_order_list {
    private String riderid;
    private String order_id;
    private String image;
    private String clientname;
    private String time;
    private String pickup_location;
    private String dropoff_location;
    private String package_type;
    private String payment_type;
    private String distance;
    private String duration;
    private String pickup_contact_name;
    private String dropoff_contact_name;
    private String pickup_contact;
    private String dropoff_contact;
    private String durationdistance;
    private String deliverystatus;
    // private String distance;


    public all_real_order_list(String riderid,String order_id,String image, String clientname,String time,String pickup_location,String dropoff_location,String package_type,String distance,String duration,String pickup_contact,String dropoff_contact,String pickup_contact_name,String dropoff_contact_name,String payment_type,String durationdistance,String delivery_status) {
        this.riderid = riderid;
        this.order_id = order_id;
        this.image = image;
        this.clientname = clientname;
        this.time = time;
        this.pickup_location = pickup_location;
        this.dropoff_location = dropoff_location;
        this.package_type = package_type;
        this.distance = distance;
        this.duration = duration;
        this.pickup_contact = pickup_contact;
        this.dropoff_contact = dropoff_contact;
        this.pickup_contact_name = pickup_contact_name;
        this.dropoff_contact_name = dropoff_contact_name;
        this.payment_type = payment_type;
        this.durationdistance= durationdistance;
        this.deliverystatus= delivery_status;
        // this.distance = distance;
    }

    public String getriderid() {
        return riderid;
    }
    public String getid() {
        return order_id;
    }

    public String getImage() {
        return image;
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

    public String  payment_type() {
        return  payment_type;
    }

    public String  durationdistance() {
        return  durationdistance;
    }
    public String  deliverystatus() {
        return  deliverystatus;
    }


}