package com.example.kinderneutron;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.security.SecureRandom;

public class SignUp extends AppCompatActivity {
    EditText usernameEditText, passwordEditText, emailEditText, authTokenEditText;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Initialize UI components
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password1);
        emailEditText = findViewById(R.id.email);
        authTokenEditText = findViewById(R.id.auth);
        signUpButton = findViewById(R.id.loginButton1);

        // Set onClickListener for the sign-up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get username, password, email, and auth token from EditText fields
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString().trim();
                String authToken = authTokenEditText.getText().toString();

                // Validate input
                if (TextUtils.isEmpty(username)) {
                    usernameEditText.setError("Username is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError("Password is required");
                    return;
                }
                if (!isValidEmail(email)) {
                    emailEditText.setError("Enter a valid email address");
                    return;
                }
                if (TextUtils.isEmpty(authToken)) {
                    authTokenEditText.setError("Auth token is required");
                    return;
                }
                if (!isAlphaNumeric(authToken)) {
                    authTokenEditText.setError("Auth token must be alphanumeric");
                    return;
                }

                // Store the user's credentials
                saveCredentials(username, password, email, authToken);

                // Display a message
                Toast.makeText(SignUp.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                // Finish SignUp activity
                finish();
            }
        });
    }

    // Method to check if a string is alphanumeric
    private boolean isAlphaNumeric(String str) {
        return str != null && str.matches("^[a-zA-Z0-9]*$");
    }

    // Method to check if the email address is valid
    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    // Method to save user credentials
    private void saveCredentials(String username, String password, String email, String authToken) {
        // Get SharedPreferences editor
//        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
//
//        // Store the username, password, email, and auth token
//        editor.putString("username", username);
//        editor.putString("password", password);
//        editor.putString("email", email);
//        editor.putString("authToken", authToken);
//
//        // Apply changes
//        editor.apply();
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient();

            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            String url = "http://django-env2.eba-pmmersjp.us-west-2.elasticbeanstalk.com/userapi/";


            String json = "{"
                    + "\"username\": \"" + username + "\","
                    + "\"email\": \"" + email + "\","
                    + "\"auth_token\": \"" + authToken + "\","
                    + "\"password\": \"" + password + "\""
                    + "}";
            RequestBody body = RequestBody.create(json, MEDIA_TYPE);

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                Log.d("Response", "Response Code: " + response.code() + " Response Body: " + response.body().string());
                Toast.makeText(SignUp.this, response.code(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
}


