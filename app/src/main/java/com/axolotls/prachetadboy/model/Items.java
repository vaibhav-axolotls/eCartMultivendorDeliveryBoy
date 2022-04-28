package com.axolotls.prachetadboy.model;

import java.io.Serializable;

public class Items implements Serializable {
    String id;
    String quantity;
    String price;
    String discounted_price;
    String tax_amount;
    String tax_percentage;
    String sub_total;
    String active_status;
    String name;
    String image;
    String measurement;
    String unit;



    public String getId() {
        return id;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscounted_price() {
        return discounted_price;
    }

    public String getTax_amount() {
        return tax_amount;
    }

    public String getTax_percentage() {
        return tax_percentage;
    }

    public String getSub_total() {
        return sub_total;
    }

    public String getActive_status() {
        return active_status;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getMeasurement() {
        return measurement;
    }

    public String getUnit() {
        return unit;
    }
}
