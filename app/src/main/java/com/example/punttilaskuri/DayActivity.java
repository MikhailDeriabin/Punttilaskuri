package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class DayActivity extends AppCompatActivity {

    // UI elements
    private TextView dateTV, jsonFileTV;
    private EditText noteInputField;
    private Button addNoteButton;
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
        noteInputField = findViewById(R.id.noteInputField);
        addNoteButton = findViewById(R.id.addNoteButton);

        String choseDate = choseDay + "." + choseMonth + "." + choseYear;
        dateTV.setText(choseDate);

        UserInputHandler ih = new UserInputHandler(this);
        String userNotes = ih.getAllNotes(choseDate);
        jsonFileTV.setText(userNotes);

        //Events
        addNoteButton.setOnClickListener( v -> {
            String userInput = noteInputField.getText().toString();
            try {
                ih.addNote(userInput, choseDate);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String allNotes = ih.getAllNotes(choseDate);
            jsonFileTV.setText(allNotes);
        });
    }
}