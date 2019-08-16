package com.chinuthor.picatune;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable {
    private int Id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isAdmin;
    private boolean ratingPrompted;
    private int loginCount;
    private int appRating;
    private Playlist playlist;
    private ArrayList<Song> songList;


    public User() {
        this.songList = new ArrayList<>();
        this.playlist = new Playlist();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    protected User(Parcel in) {
        Id = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        username = in.readString();
        password = in.readString();
        isAdmin = in.readByte() != 0;
        ratingPrompted = in.readByte() != 0;
        loginCount = in.readInt();
        appRating = in.readInt();
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public ArrayList<Song> getSongList() {
        return songList;
    }



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
        this.playlist = new Playlist();
    }

    public void setSongList(ArrayList<Song> songList) {
        this.songList = songList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeByte((byte) (isAdmin ? 1 : 0));
        dest.writeByte((byte) (ratingPrompted ? 1 : 0));
        dest.writeInt(loginCount);
        dest.writeInt(appRating);
    }
}
