package com.chinuthor.picatune;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText loginUsernameEditText;
    private AppCompatEditText loginPasswordEditText;
    private AppCompatButton loginLoginButton;
    private AppCompatButton loginSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsernameEditText = findViewById(R.id.login_username_edit_text);
        loginPasswordEditText = findViewById(R.id.login_password_edit_text);
        loginLoginButton = findViewById(R.id.login_login_button);
        loginSignupButton = findViewById(R.id.login_register_button);

        loginLoginButton.setOnClickListener(this);
        loginSignupButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login_button:
                loginUsernameEditText.setError(null);
                loginPasswordEditText.setError(null);
                String username = Objects.requireNonNull(loginUsernameEditText.getText()).toString();
                String password = Objects.requireNonNull(loginPasswordEditText.getText()).toString();

                boolean cancel = false;

                if (TextUtils.isEmpty(username)) {
                    loginUsernameEditText.setError("Please enter username");
                    loginUsernameEditText.requestFocus();
                    cancel = true;
                }

                if (TextUtils.isEmpty(password)) {
                    loginPasswordEditText.setError("Please enter password");
                    loginPasswordEditText.requestFocus();
                    cancel = true;
                }

                if (!cancel) {
                    DatabaseHelper dbHelper = new DatabaseHelper(this);
                    User user = dbHelper.login(username, password);
                    if (user != null) {
                        // user credentials are correct. login
                        SessionManager manager = new SessionManager(LoginActivity.this);
                        // create login session and save email in sharedPreference
                        manager.createLoginSession(username);

                        Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        finish();
                    }
                }
                break;

            case R.id.login_register_button:
                startActivity(new Intent(this, SignupActivity.class));
                break;

            default:

                break;
        }
    }
}
