package com.chinuthor.picatune;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Launcher activity of the application. determines whether user has an active login session or not and redirects to other activity accordingly
 */
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create an instance of SessionManager class and check for login status of the user
        SessionManager manager = new SessionManager(this);
        manager.checkLogin();
    }
}
