package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.File;

public class DayActivity extends AppCompatActivity {

    // UI elements
    private TextView dateTV, jsonFileTV;
    private Button loadJsonButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        Bundle b = getIntent().getExtras();
        int choseYear = b.getInt("choseYear", 2021);
        int choseMonth = b.getInt("choseMonth", 1);
        int choseDay = b.getInt("choseDay", 1);

        dateTV = findViewById(R.id.dateTV);
        jsonFileTV = findViewById(R.id.jsonFileTV);
        loadJsonButton = findViewById(R.id.loadJsonButton);

        String choseDate = choseDay + "." + choseMonth + "." + choseYear;
        dateTV.setText(choseDate);

        loadJsonButton.setOnClickListener( v -> {
            File daysInfoFile = new File("");
            //JSONObject allDayInformation =
        });
    }
}