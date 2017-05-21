package com.rumaan.academapp.model;

import java.util.List;

/**
 * Created by Rumaan on 21-May-17.
 */

public class ForumPost {
    private String post_title;
    private String post_desc;
    private List<String> joined_users;
    private String name;
    private String type;
    private String uid;

    public ForumPost(String post_title, String post_desc, List<String> joined_users, String name, String type, String uid) {
        this.post_title = post_title;
        this.post_desc = post_desc;
        this.joined_users = joined_users;
        this.name = name;
        this.type = type;
        this.uid = uid;
    }

    public ForumPost() {

    }

    public ForumPost(String post_title, String post_desc) {
        this.post_title = post_title;
        this.post_desc = post_desc;
    }

    public String getPost_title() {
        return post_title;
    }

    public ForumPost setPost_title(String post_title) {
        this.post_title = post_title;
        return this;
    }

    public String getPost_desc() {
        return post_desc;
    }

    public ForumPost setPost_desc(String post_desc) {
        this.post_desc = post_desc;
        return this;
    }

    public List<String> getJoined_users() {
        return joined_users;
    }

    public ForumPost setJoined_users(List<String> joined_users) {
        this.joined_users = joined_users;
        return this;
    }

    public String getName() {
        return name;
    }

    public ForumPost setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public ForumPost setType(String type) {
        this.type = type;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public ForumPost setUid(String uid) {
        this.uid = uid;
        return this;
    }


}
