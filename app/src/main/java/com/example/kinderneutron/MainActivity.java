package com.example.kinderneutron;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginButton;
    Button registerButton;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String enteredUsername = username.getText().toString();
        String enteredPassword = password.getText().toString();

        // Retrieve saved credentials
//        String savedUsername = sharedPreferences.getString("username", "");
//        String savedPassword = sharedPreferences.getString("password", "");
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient();

            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            String url = "http://django-env2.eba-pmmersjp.us-west-2.elasticbeanstalk.com/userapi/";


            String json = "{"
                    + "\"type\": \"" + "login" + "\","
                    + "\"username\": \"" + enteredUsername + "\","
                    + "\"password\": \"" + enteredPassword + "\""
                    + "}";
            RequestBody body = RequestBody.create(json, MEDIA_TYPE);

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                Log.d("Response", "Response Code: " + response.code() + " Response Body: " + response.body().string());
                runOnUiThread(() -> {
                    if (response.code() == 200) {
                        Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, landingpage1.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
//        if (enteredUsername.equals("Suhas") && enteredPassword.equals("123456")) {
//            // Login successful, navigate to landingpage1
//            Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, landingpage1.class);
//            startActivity(intent);
//        } else {
//            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
//        }
    }
}
