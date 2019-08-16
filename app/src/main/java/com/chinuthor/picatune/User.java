package com.chinuthor.picatune;

import java.util.ArrayList;

public class User {
    private int Id;
    private String firstName;
    private String lastName;
    private String username;
    private boolean isAdmin;
    private boolean ratingPrompted;
    private int loginCount;
    private int appRating;
    private Playlist playlist;
    private ArrayList<Song> songList;


    public User(int id, String firstName, String lastName, String username, boolean isAdmin, boolean ratingPrompted, int loginCount, int appRating) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.isAdmin = isAdmin;
        this.ratingPrompted = ratingPrompted;
        this.loginCount = loginCount;
        this.appRating = appRating;
        this.songList = new ArrayList<>();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isRatingPrompted() {
        return ratingPrompted;
    }

    public void setRatingPrompted(boolean ratingPrompted) {
        this.ratingPrompted = ratingPrompted;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public int getAppRating() {
        return appRating;
    }

    public void setAppRating(int appRating) {
        this.appRating = appRating;
    }
}
