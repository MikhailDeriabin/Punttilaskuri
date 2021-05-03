package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.punttilaskuri.fileHandlers.NotesHandler;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

public class DayActivity extends AppCompatActivity {

    // UI elements
    private TextView dateTV, jsonFileTV;
    private EditText noteInputField;
    private Button addNoteButton, clearAllNotesButton;
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
        clearAllNotesButton = findViewById(R.id.clearAllNotesButton);

        String choseDate = choseDay + "." + choseMonth + "." + choseYear;
        dateTV.setText(choseDate);

        NotesHandler notesHandler = new NotesHandler(this);
        String userNotes = null;
        userNotes = notesHandler.getDayInformationAsString(choseDate);
        jsonFileTV.setText(userNotes);

        //Events
        addNoteButton.setOnClickListener( v -> {
            String userInput = noteInputField.getText().toString();
            try {
                notesHandler.addInformation(choseDate, userInput);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String allNotes = null;
            allNotes = notesHandler.getDayInformationAsString(choseDate);
            jsonFileTV.setText(allNotes);
        });

        jsonFileTV.setOnClickListener( v -> {
            notesHandler.removeOneItem(choseDate, 0);
            String allNotes = null;
            allNotes = notesHandler.getDayInformationAsString(choseDate);
            jsonFileTV.setText(allNotes);
        });

        clearAllNotesButton.setOnClickListener( v -> {
            notesHandler.clearWholeJsonFile("daysInfo.json");
        });
    }
}