package com.rumaan.academapp.Model;

/**
 * Created by Rumaan on 19-Mar-17.
 */

public class Profile {
    private String name;
    private String usn;
    private String course;

    private String email;
    // avatar object
    private Avatar avatar;

    public Profile(String name, String usn, String course, Avatar avatar, String email) {
        this.name = name;
        this.usn = usn;
        this.course = course;
        this.avatar = avatar;
        this.email = email;
    }

    public Profile() {
        // default constructor
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
