package com.axolotls.prachetadboy.model;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderList implements Serializable {
    String id,otp,mobile,order_note,total,delivery_charge,wallet_balance,discount,promo_discount,final_total,payment_method,address,latitude,longitude,delivery_time,date_added,seller_mobile,user_name,seller_name,seller_address,seller_latitude,seller_longitude;
    ArrayList<Items> items;

    public String getSeller_mobile() {
        return seller_mobile;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public String getSeller_address() {
        return seller_address;
    }

    public String getSeller_latitude() {
        return seller_latitude;
    }

    public String getSeller_longitude() {
        return seller_longitude;
    }

    public String getId() {
        return id;
    }

    public String getOtp() {
        return otp;
    }

    public String getMobile() {
        return mobile;
    }

    public String getOrder_note() {
        return order_note;
    }

    public String getTotal() {
        return total;
    }

    public String getDelivery_charge() {
        return delivery_charge;
    }

    public String getWallet_balance() {
        return wallet_balance;
    }

    public String getDiscount() {
        return discount;
    }

    public String getPromo_discount() {
        return promo_discount;
    }

    public String getFinal_total() {
        return final_total;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public String getDate_added() {
        return date_added;
    }

    public String getUser_name() {
        return user_name;
    }

    public ArrayList<Items> getItems() {
        return items;
    }
}