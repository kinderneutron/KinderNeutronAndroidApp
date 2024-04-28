package com.example.kinderneutron;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class Errorlogs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_errorlogs);

        // Define data for 8 rows
        String[][] rowData = {
                {"1", "Type 1", "Message 1", "2024-04-20", "2024-04-20", "User 1"}, {"2", "Type 2", "Message 2", "2024-04-21", "2024-04-21", "User 2"}, {"3", "Type 3", "Message 3", "2024-04-22", "2024-04-22", "User 3"},
                {"1", "Type 1", "Message 1", "2024-04-20", "2024-04-20", "User 1"}
                // Add data for remaining rows similarly
        };
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
