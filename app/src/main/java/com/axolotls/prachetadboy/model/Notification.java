package com.axolotls.prachetadboy.model;

import java.io.Serializable;

public class Notification implements Serializable {

    private String id;
    private String title;
    private String message;
    private String type;
    private String order_id;
    private String date_created;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getDate_created() {
        return date_created;
    }
}
