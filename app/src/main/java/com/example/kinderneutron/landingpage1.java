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
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


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

// Enable zoom controls and set initial scale to fit the entire page
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false); // Hide the zoom controls
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        // Replace "YOUR_SERVER_URL" with the actual URL of your Django server
        String serverUrl = "http://192.168.29.173:8001/videostreamapi/";

        webView.loadUrl(serverUrl);

        // Enable video playback in the WebView
        webView.setWebChromeClient(new WebChromeClient());

        TextViewPerson = findViewById(R.id.personTextView);
        TextViewLight = findViewById(R.id.lightTextView);
        startDataFetchingTimer();
//        // Mock API response
//        String apiPersonStatus = "person detected"; // Replace this with actual API response
//        String apiLightStatus = "light ON"; // Replace this with actual API response
//
//        // Update TextViews based on API response
//        if (apiPersonStatus.equals("person detected")) {
//           TextViewPerson.setText("Person Detected");
//        } else if (apiPersonStatus.equals("No person detected")) {
//            TextViewPerson.setText("Person Not Detected");
//        }
//
//        if (apiLightStatus.equals("light ON")) {
//            TextViewLight.setText("Light is ON");
//        } else if (apiLightStatus.equals("light OFF")) {
//            TextViewLight.setText("Light is OFF");
//        }
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
    private Timer dataFetchTimer;

    private void startDataFetchingTimer() {
        final Handler handler = new Handler();
        dataFetchTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        new FetchDataAsyncTask().execute();
                    }
                });
            }
        };
        // Schedule the timer to run every 5 seconds (5000 milliseconds)
        dataFetchTimer.schedule(timerTask, 0, 1000);
    }
    public class FetchDataAsyncTask extends AsyncTask<Void, Void, String> {

        private static final String TAG = "FetchDataAsyncTask";
        private String endpointUrl = "http://192.168.29.173:8000/landingpage/ajax/update_data/";

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonData = null;

            try {
                URL url = new URL(endpointUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();

                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }
                jsonData = buffer.toString();
            } catch (IOException e) {
                Log.e(TAG, "Error ", e);
                // If the code didn't successfully get the data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }

            return jsonData;
        }

        @Override
        protected void onPostExecute(String jsonData) {
            super.onPostExecute(jsonData);
            if (jsonData != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonData);
                    String apiPersonStatus = jsonObject.optString("person_detected");

                    // Update TextViews based on API response
                    if ("yes".equals(apiPersonStatus)) {
                        TextViewPerson.setText("Person Detected");
                    } else if ("no".equals(apiPersonStatus)) {
                        TextViewPerson.setText("Person Not Detected");
                    }

                    if ("yes".equals(apiPersonStatus)) {
                        TextViewLight.setText("Light is ON");
                    } else if ("no".equals(apiPersonStatus)) {
                        TextViewLight.setText("Light is OFF");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing JSON", e);
                }
            } else {
                Log.e(TAG, "No data received from the server");
            }
        }

    }

    }


