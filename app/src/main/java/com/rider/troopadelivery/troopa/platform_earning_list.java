package com.rider.troopadelivery.troopa;

public class platform_earning_list {



    private String  order_serial;
    private String clientname;
    private String amount;
    private String type;
    private String riderearning;
    private String platform_earning;
    private String sharetime;

    // private String distance;


    public platform_earning_list(String order_serial,String clientname,String amount,String type,String riderearning,String platform_earning,String sharetime){
        this.order_serial = order_serial;
        this.clientname = clientname;
        this.amount = amount;
        this.type = type;
        this.riderearning = riderearning;
        this.platform_earning = platform_earning;
        this.sharetime = sharetime;

    }

    public String getOrder_serial() {
        return order_serial;
    }
    public String getClientname() {
        return clientname;
    }

    public String getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }
    public String getRiderearning() {
        return riderearning;
    }

    public String getPlatform_earning() {
        return platform_earning;
    }

    public String getTime() {
        return sharetime;
    }




}
