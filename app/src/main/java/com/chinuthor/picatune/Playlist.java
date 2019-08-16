package com.chinuthor.picatune;

public class Playlist {
    private int id;
    private String playlistName;
    private int songCount;
    private double duration;

    public Playlist(int id, String playlistName) {
        this.id = id;
        this.playlistName = playlistName;
    }
}
