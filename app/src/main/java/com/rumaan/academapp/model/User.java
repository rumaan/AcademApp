package com.rumaan.academapp.model;

/**
 * Created by Rumaan on 21-May-17.
 */

public class User {
    private static User instance;
    private String type;
    private String name;
    private String usn;

    private User() {
        // singleton class
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

}
