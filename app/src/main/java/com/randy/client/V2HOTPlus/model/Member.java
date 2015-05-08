package com.randy.client.V2HOTPlus.model;

/**
 * Created by randy on 15/5/7.
 */
public class Member {
    private int id;
    private String username;
    private String avatar_mini;
    private String avatar_normal;
    private String avatar_large;

    public String getAvatar_large() {
        return avatar_large.charAt(0) == '/' ? "https:" + avatar_large : avatar_large;
    }

    public void setAvatar_large(String avatar_large) {
        this.avatar_large = avatar_large;
    }

    public String getAvatar_mini() {
        return avatar_mini.charAt(0) == '/' ? "https:" + avatar_mini : avatar_mini;
    }

    public void setAvatar_mini(String avatar_mini) {
        this.avatar_mini = avatar_mini;
    }

    public String getAvatar_normal() {
        return avatar_normal.charAt(0) == '/' ? "https:" + avatar_normal : avatar_normal;
    }

    public void setAvatar_normal(String avatar_normal) {
        this.avatar_normal = avatar_normal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
