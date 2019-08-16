package com.chinuthor.picatune;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    private AppCompatEditText signupFirstNameEditText;
    private AppCompatEditText signupLastNameEditText;
    private AppCompatEditText signupUsernameEditText;
    private AppCompatEditText signupPasswordEditText;
    private AppCompatEditText signupConfirmPasswordEditText;
    private AppCompatButton signupSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupFirstNameEditText = findViewById(R.id.signup_first_name_edit_text);
        signupLastNameEditText = findViewById(R.id.signup_last_name_edit_text);
        signupUsernameEditText = findViewById(R.id.signup_username_edit_text);
        signupPasswordEditText = findViewById(R.id.signup_password_edit_text);
        signupConfirmPasswordEditText = findViewById(R.id.signup_confirm_password_edit_text);
        signupSignupButton = findViewById(R.id.signup_signup_button);

        signupSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupFirstNameEditText.setError(null);
                signupLastNameEditText.setError(null);
                signupUsernameEditText.setError(null);
                signupPasswordEditText.setError(null);
                signupConfirmPasswordEditText.setError(null);

                String firstName = Objects.requireNonNull(signupFirstNameEditText.getText()).toString();
                String lastName = Objects.requireNonNull(signupLastNameEditText.getText()).toString();
                String username = Objects.requireNonNull(signupUsernameEditText.getText()).toString();
                String password = Objects.requireNonNull(signupPasswordEditText.getText()).toString();
                String confirmPassword = Objects.requireNonNull(signupConfirmPasswordEditText.getText()).toString();
                boolean cancel = false;

                if (TextUtils.isEmpty(firstName)) {
                    signupFirstNameEditText.setError("this field is required");
                    signupFirstNameEditText.requestFocus();
                    cancel = true;
                }

                if (TextUtils.isEmpty(lastName)) {
                    signupLastNameEditText.setError("this field is required");
                    signupLastNameEditText.requestFocus();
                    cancel = true;
                }

                if (TextUtils.isEmpty(username)) {
                    signupUsernameEditText.setError("this field is required");
                    signupUsernameEditText.requestFocus();
                    cancel = true;
                }

                if (TextUtils.isEmpty(password)) {
                    signupPasswordEditText.setError("this field is required");
                    signupPasswordEditText.requestFocus();
                    cancel = true;
                }

                if (TextUtils.isEmpty(confirmPassword)) {
                    signupConfirmPasswordEditText.setError("this field is required");
                    signupConfirmPasswordEditText.requestFocus();
                    cancel = true;
                }

                if (!Objects.equals(password, confirmPassword)) {
                    signupConfirmPasswordEditText.setError("this field is required");
                    signupConfirmPasswordEditText.requestFocus();
                    cancel = true;
                }

                if (!cancel) {
                    DatabaseHelper dbHelper = new DatabaseHelper(SignupActivity.this);
                    User user = dbHelper.addUser(new User(firstName, lastName, username, password));
                    if (user != null) {
                        // create an instance of session manager and create a login session
                        SessionManager manager = new SessionManager(SignupActivity.this);
                        manager.createLoginSession(username);

                        // create an instance of intent to go to WelcomeActivity
                        Intent intent = new Intent(SignupActivity.this, Main2Activity.class);
                        // setting couple of flags to disable back button to come back to RegisterActivity or LoginActivity
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // start intent and finish current activity
                        intent.putExtra("user", user);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }
}
