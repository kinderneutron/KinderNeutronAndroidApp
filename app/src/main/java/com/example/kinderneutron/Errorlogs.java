package com.example.kinderneutron;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class Errorlogs extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_errorlogs);
        OkHttpClient client = new OkHttpClient();

        // Define the URL for your GET request
        String url = "http://django-env2.eba-pmmersjp.us-west-2.elasticbeanstalk.com/errorlogapi"; // Replace with your actual API URL

        // Create a request object with the URL
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure
                e.printStackTrace();
            }

        // Define data for 8 rows
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                // Get the JSON response body as a string
                String responseBody = response.body().string();

                try {
                    // Parse the JSON string
                    JSONArray jsonArray = new JSONArray(responseBody);

                    // Initialize rowData array with the same size as jsonArray
                    String[][] rowData = new String[jsonArray.length()][6];

                    // Fill the rowData array with JSON data
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        rowData[i][0] = jsonObject.getString("id");
                        rowData[i][1] = jsonObject.getString("user_id");
                        rowData[i][2] = jsonObject.getString("error_type");
                        rowData[i][3] = jsonObject.getString("message");
                        rowData[i][4] = jsonObject.getString("created_at");
                        rowData[i][5] = jsonObject.getString("updated_at");
                    }

                    // Update UI on the main thread
                    runOnUiThread(() -> updateUI(rowData));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Handle unsuccessful response
            }
        }
        });
    }

    // Update UI with the rowData array
    private void updateUI(String[][] rowData) {
        TextView[] textViews = new TextView[] {
                findViewById(R.id.textview7), findViewById(R.id.textview8), findViewById(R.id.textview9),
                findViewById(R.id.textview10), findViewById(R.id.textview11), findViewById(R.id.textview12),
                findViewById(R.id.textview13), findViewById(R.id.textview14), findViewById(R.id.textview15),
                findViewById(R.id.textview16), findViewById(R.id.textview17), findViewById(R.id.textview18),
                findViewById(R.id.textview19), findViewById(R.id.textview20), findViewById(R.id.textview21),
                findViewById(R.id.textview22), findViewById(R.id.textview23), findViewById(R.id.textview24),
                findViewById(R.id.textview25), findViewById(R.id.textview26), findViewById(R.id.textview27),
                findViewById(R.id.textview28), findViewById(R.id.textview29), findViewById(R.id.textview30)
        };

        // Fill the TextViews with data
        for (int i = 0; i < rowData.length; i++) {
            String[] row = rowData[i];
            int baseIndex = i * 6;  // Each row has 6 TextViews
            for (int j = 0; j < row.length; j++) {
                if (baseIndex + j < textViews.length) {
                    textViews[baseIndex + j].setText(row[j]);
                }
            }
        }
    }
}