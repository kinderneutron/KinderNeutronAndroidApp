package com.example.kinderneutron;
import android.view.View;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import java.io.BufferedInputStream;
import androidx.appcompat.app.AppCompatActivity;
import android.view.SurfaceHolder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.Handler;
import android.widget.VideoView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.content.Intent;

public class landingpage1 extends AppCompatActivity {

    private VideoView videoView;
    private MediaController mediaController;
    private static final String TAG = "landingpage1";
    private static final String MJPEG_URL = "http://192.168.29.173:8001/videostreamapi/";
    private WebView webView;
    private TextView TextViewPerson;
    private TextView TextViewLight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landingpage1);

        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Replace "YOUR_SERVER_URL" with the actual URL of your Django server
//        String serverUrl = "http://192.168.29.173:8001/videostreamapi/";

//        webView.loadUrl(serverUrl);

        // Enable video playback in the WebView
        webView.setWebChromeClient(new WebChromeClient());

        TextViewPerson = findViewById(R.id.personTextView);
        TextViewLight = findViewById(R.id.lightTextView);

        // Mock API response
        String apiPersonStatus = "person detected"; // Replace this with actual API response
        String apiLightStatus = "light ON"; // Replace this with actual API response

        // Update TextViews based on API response
        if (apiPersonStatus.equals("person detected")) {
           TextViewPerson.setText("Person Detected");
        } else if (apiPersonStatus.equals("No person detected")) {
            TextViewPerson.setText("Person Not Detected");
        }

        if (apiLightStatus.equals("light ON")) {
            TextViewLight.setText("Light is ON");
        } else if (apiLightStatus.equals("light OFF")) {
            TextViewLight.setText("Light is OFF");
        }
        Button next = findViewById(R.id.NextButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Errorlogs activity using explicit package name
                Intent intent = new Intent(landingpage1.this, com.example.kinderneutron.Errorlogs.class);
                startActivity(intent);
            }
        });

    }
    }


