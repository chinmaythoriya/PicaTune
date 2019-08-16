package com.chinuthor.picatune;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class SessionManager {
    // static final strings representing Keys for sharedPreference file
    public static final String KEY_USERNAME = "username";
    private static final String PREF_NAME = "SessionManager";
    private static final String IS_LOGIN = "IsLoggedIn";
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    /**
     * Constructor that initializes new instance of SessionManager and also new instances of sharedPreferences and editor
     *
     * @param context context from where the instance of SessionManager is created
     */
    SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * Creates a login session. saves username and loginStatus as true
     *
     * @param name the name of the user who just loggedIn
     */
    void createLoginSession(String name) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, name);
        editor.apply();
    }

    /**
     * gets the saved information of the user from sharedPreference
     *
     * @param key          the key of the sharedPreference value
     * @param defaultValue the default Value that would be returned in case of no value found with the given key
     * @return the detail asked for
     */
    String getUserDetail(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    /**
     * checks whether user has previous login session
     */
    void checkLogin() {
        if (!this.isLoggedIn()) {
            // user has no login session redirect to login activity
            Intent i = new Intent(context, LoginActivity.class);
            //close all the activities.
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //start new activity.
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ((Activity) context).finish();
            context.startActivity(i);
            ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            // user has previous login session redirect to welcome activity
            Intent i = new Intent(context, Main2Activity.class);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ((Activity) context).finish();
            context.startActivity(i);
            ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    /**
     * logouts the user.. clears the username saved in the sharedPreference
     */
    void logoutUser() {
        editor.clear(); //clear all data from preferences
        editor.apply();

        Intent intent = new Intent(context, LoginActivity.class);

        //close all the activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //start new activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
        ((AppCompatActivity) context).finish();

    }

    /**
     * checks whether user has a login session or not by checking the value of the key IS_LOGIN in sharedPreferences
     *
     * @return true if user is logged in, false otherwise
     */
    private boolean isLoggedIn() {
        return preferences.getBoolean(IS_LOGIN, false);
    }
}
