package com.chinuthor.picatune;

public class DatabaseUtil {
    //INITIALIZE IMPORTANT VARIABLES FOR DATABASE
    public static final String DB_NAME = "PicaTune";
    public static final int DB_VERSION = 1;

    //USERS TABLE NAME AND COLUMNS
    public static final String USERS_TABLE_NAME = "Users";
    public static final String USERS_COLUMN_ID = "id";
    public static final String USERS_COLUMN_FIRST_NAME = "firstName";
    public static final String USERS_COLUMN_LAST_NAME = "lastName";
    public static final String USERS_COLUMN_APP_RATING = "rating";
    public static final String USERS_COLUMN_USERNAME = "username";
    public static final String USERS_COLUMN_PASSWORD = "password";
    public static final String USERS_COLUMN_IS_ADMIN = "isAdmin";
    public static final String USERS_COLUMN_RATING_PROMPTED = "ratingPrompted";
    public static final String USERS_COLUMN_LOGIN_COUNT = "loginCount";

    //DATABSE QUERY TO CREATE DATABASE TABLE IF REQUIRED
    public static final String SQL_CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + USERS_TABLE_NAME + " (\n" +
            "   " + USERS_COLUMN_ID + " INTEGER NOT NULL CONSTRAINT users_pk PRIMARY KEY AUTOINCREMENT,\n" +
            "   " + USERS_COLUMN_FIRST_NAME + " varchar(200) NOT NULL, \n" +
            "   " + USERS_COLUMN_LAST_NAME + " varchar(200) NOT NULL, \n" +
            "   " + USERS_COLUMN_USERNAME + " varchar(200) NOT NULL, \n" +
            "   " + USERS_COLUMN_PASSWORD + " varchar(200) NOT NULL, \n" +
            "   " + USERS_COLUMN_IS_ADMIN + " BOOLEAN NOT NULL, \n" +
            "   " + USERS_COLUMN_RATING_PROMPTED + " BOOLEAN NOT NULL, \n" +
            "   " + USERS_COLUMN_LOGIN_COUNT + " INTEGER NOT NULL, \n" +
            "   " + USERS_COLUMN_APP_RATING + " INTEGER \n" +
            ");";


    //LOGIN HISTORY TABLE NAME AND COLUMNS
    public static final String LOGIN_HISTORY_TABLE_NAME = "LoginHistory";
    public static final String LOGIN_HISTORY_COLUMN_ID = "id";
    public static final String LOGIN_HISTORY_COLUMN_USER_ID = "userId";
    public static final String LOGIN_HISTORY_COLUMN_LOGIN_DATE_TIME = "loginDateTime";
    public static final String LOGIN_HISTORY_COLUMN_LOGOUT_DATE_TIME = "logoutDateTime";
    public static final String LOGIN_HISTORY_COLUMN_SONGS_PLAYED_COUNT = "songsPlayedCount";

    //DATABSE QUERY TO CREATE DATABASE TABLE IF REQUIRED
    public static final String SQL_CREATE_LOGIN_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS " + LOGIN_HISTORY_TABLE_NAME + " (\n" +
            "   " + LOGIN_HISTORY_COLUMN_ID + " INTEGER NOT NULL CONSTRAINT loginHistory_pk PRIMARY KEY AUTOINCREMENT,\n" +
            "   " + LOGIN_HISTORY_COLUMN_USER_ID + " INTEGER NOT NULL, \n" +
            "   " + LOGIN_HISTORY_COLUMN_LOGIN_DATE_TIME + " DOUBLE NOT NULL, \n" +
            "   " + LOGIN_HISTORY_COLUMN_LOGOUT_DATE_TIME + " DOUBLE, \n" +
            "   " + LOGIN_HISTORY_COLUMN_SONGS_PLAYED_COUNT + " INTEGER, \n" +
            ");";


    //PLAYLIST TABLE NAME AND COLUMNS
    public static final String PLAYLIST_TABLE_NAME = "Playlist";
    public static final String PLAYLIST_COLUMN_ID = "id";
    public static final String PLAYLIST_COLUMN_USER_ID = "userId";
    public static final String PLAYLIST_COLUMN_PLAYLIST_NAME = "playlistName";
    public static final String PLAYLIST_COLUMN_SONG_COUNT = "songCount";
    public static final String PLAYLIST_COLUMN_DURATION = "duration";

    //DATABSE QUERY TO CREATE DATABASE TABLE IF REQUIRED
    public static final String SQL_CREATE_PLAYLIST_TABLE = "CREATE TABLE IF NOT EXISTS " + PLAYLIST_TABLE_NAME + " (\n" +
            "   " + PLAYLIST_COLUMN_ID + " INTEGER NOT NULL CONSTRAINT playlist_pk PRIMARY KEY AUTOINCREMENT,\n" +
            "   " + PLAYLIST_COLUMN_USER_ID + "INTEGER NOT NULL, \n" +
            "   " + PLAYLIST_COLUMN_PLAYLIST_NAME + " varchar(200) NOT NULL, \n" +
            "   " + PLAYLIST_COLUMN_SONG_COUNT + " INTEGER NOT NULL, \n" +
            "   " + PLAYLIST_COLUMN_DURATION + " DOUBLE, \n" +
            ");";


    //USER SONGS TABLE NAME AND COLUMNS
    public static final String USER_SONGS_TABLE_NAME = "UsersSongs";
    public static final String USER_SONGS_COLUMN_ID = "id";
    public static final String USER_SONGS_COLUMN_USER_ID = "userId";
    public static final String USER_SONGS_COLUMN_ON_PLAYLIST = "onPlaylist";

    //DATABSE QUERY TO CREATE DATABASE TABLE IF REQUIRED
    public static final String SQL_CREATE_USER_SONGS_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_SONGS_TABLE_NAME + " (\n" +
            "   " + USER_SONGS_COLUMN_ID + " INTEGER NOT NULL CONSTRAINT usersongs_pk PRIMARY KEY AUTOINCREMENT,\n" +
            "   " + USER_SONGS_COLUMN_USER_ID + " INTEGER NOT NULL, \n" +
            "   " + USER_SONGS_COLUMN_ON_PLAYLIST + " BOOLEAN NOT NULL, \n" +
            ");";


   /* //PLAYLIST SONGS TABLE NAME AND COLUMNS
    public static final String PLAYLIST_SONGS_TABLE_NAME = "PlaylistSongs";
    public static final String PLAYLIST_SONGS_COLUMN_ID = "id";
    public static final String PLAYLIST_SONGS_COLUMN_PLAYLIST_ID = "playlistId";

    //DATABSE QUERY TO CREATE DATABASE TABLE IF REQUIRED
    public static final String SQL_CREATE_PLAYLIST_SONGS_TABLE = "CREATE TABLE IF NOT EXISTS " + PLAYLIST_SONGS_TABLE_NAME + " (\n" +
            "   " + PLAYLIST_SONGS_COLUMN_ID + " INTEGER NOT NULL CONSTRAINT playlistsongs_pk PRIMARY KEY AUTOINCREMENT,\n" +
            "   " + PLAYLIST_SONGS_COLUMN_PLAYLIST_ID + " INTEGER NOT NULL, \n" +
            ");";*/
}
