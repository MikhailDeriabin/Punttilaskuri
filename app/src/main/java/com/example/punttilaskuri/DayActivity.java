package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class DayActivity extends AppCompatActivity {

    // UI elements
    private TextView dateTV, jsonFileTV;
    private EditText noteInputField;
    private Button loadJsonButton, addNoteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        Bundle b = getIntent().getExtras();
        int choseYear = b.getInt("choseYear", 2021);
        int choseMonth = b.getInt("choseMonth", 1);
        int choseDay = b.getInt("choseDay", 1);

        //Defining UI elements
        dateTV = findViewById(R.id.dateTV);
        jsonFileTV = findViewById(R.id.jsonFileTV);
        loadJsonButton = findViewById(R.id.loadJsonButton);
        noteInputField = findViewById(R.id.noteInputField);
        addNoteButton = findViewById(R.id.addNoteButton);

        String choseDate = choseDay + "." + choseMonth + "." + choseYear;
        dateTV.setText(choseDate);

        //Events
        loadJsonButton.setOnClickListener( v -> {
            JsonFileHandler jsonFileHandler = new JsonFileHandler("daysInfo.json", this);
            try {
                String result = jsonFileHandler.getJsonObject().getJSONObject("days").getJSONObject("1.2.2021").getJSONArray("notes").getString(0);
                jsonFileTV.setText(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        addNoteButton.setOnClickListener( v -> {

        });
    }
}