package com.rider.troopadelivery.troopa;

public class statement_list {


    private String time;
    private String amount;
    private String balance;

    // private String distance;


    public statement_list(String time,String amount,String balance){
        this.time = time;
        this.amount = amount;
        this.balance = balance;

    }

    public String gettime() {
        return time;
    }
    public String getamount() {
        return amount;
    }

    public String getbalance() {
        return balance;
    }




}
