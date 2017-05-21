package com.rumaan.academapp.model;

import android.net.Uri;

/**
 * Created by Rumaan on 21-May-17.
 */

public class ForumPost {
    private String title;
    private String description;
    private int usersJoined;
    private String postPersonName;
    private String postPersonType;
    private Uri postPersonImageUri;

    public ForumPost(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUsersJoined() {
        return usersJoined;
    }

    public void setUsersJoined(int usersJoined) {
        this.usersJoined = usersJoined;
    }

    public String getPostPersonName() {
        return postPersonName;
    }

    public void setPostPersonName(String postPersonName) {
        this.postPersonName = postPersonName;
    }

    public String getPostPersonType() {
        return postPersonType;
    }

    public void setPostPersonType(String postPersonType) {
        this.postPersonType = postPersonType;
    }

    public Uri getPostPersonImageUri() {
        return postPersonImageUri;
    }

    public void setPostPersonImageUri(Uri postPersonImageUri) {
        this.postPersonImageUri = postPersonImageUri;
    }
}
