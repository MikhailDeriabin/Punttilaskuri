package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.punttilaskuri.fileHandlers.NotesHandler;
import com.example.punttilaskuri.fileHandlers.TrainingsNamesHandler;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author Sami Miettinen
 */
public class DayActivity extends AppCompatActivity {

    // UI elements
    private TextView dateTV;
    private EditText noteInputField;
    private ListView noteView;
    private Button addNoteButton, clearAllNotesButton, addTrainingToDayButton;
    private ListView dayTrainingsListView;
    private ArrayList<String> notes;
    // ListView Elements
    private NoteAdapter noteAdapter;
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
        noteInputField = findViewById(R.id.noteInputField);
        addNoteButton = findViewById(R.id.addNoteButton);
        clearAllNotesButton = findViewById(R.id.clearAllNotesButton);

        dayTrainingsListView = findViewById(R.id.dayTrainingsListView);
        addTrainingToDayButton = findViewById(R.id.addTrainingToDayButton);

        String choseDate = choseDay + "." + choseMonth + "." + choseYear;
        dateTV.setText(choseDate);

        TrainingsNamesHandler trainingsNamesHandler = new TrainingsNamesHandler(this);
        String[] trainingNames = trainingsNamesHandler.getDayInformationAsArray(choseDate);

        if(trainingNames != null){
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.list_view_item_created_training, R.id.trainingNameTV, trainingNames);
            dayTrainingsListView.setAdapter(arrayAdapter);

            dayTrainingsListView.setOnItemClickListener((adapterView, view, i, l) -> {
                Intent nextActivity = new Intent(DayActivity.this, TrainingMoves.class);
                nextActivity.putExtra("trainingName", trainingNames[i]);
                startActivity(nextActivity);
            });
        }

        NotesHandler notesHandler = new NotesHandler(this);

        // NoteListView stuff
        noteView = (ListView) findViewById(R.id.noteView);
        notes = new ArrayList<>();
        String allNotes = notesHandler.getDayInformationAsString(choseDate);
        updateNoteList(allNotes);

        //Events
        addNoteButton.setOnClickListener( v -> {
            String userInput = noteInputField.getText().toString();
            try {
                notesHandler.addInformation(choseDate, userInput);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String tempNotes = notesHandler.getDayInformationAsString(choseDate);
            updateNoteList(tempNotes);

        });

        addTrainingToDayButton.setOnClickListener( v -> {
            Intent nextActivity = new Intent(this, CreatedTrainingsActivity.class);
            nextActivity.putExtra("isTrainingChoice", true);
            nextActivity.putExtra("date", choseDate);

            nextActivity.putExtra("choseYear", choseYear);
            nextActivity.putExtra("choseMonth", choseMonth);
            nextActivity.putExtra("choseDay", choseDay);
            startActivity(nextActivity);
        });

        clearAllNotesButton.setOnClickListener( v -> {
            notesHandler.clearWholeJsonFile("daysInfo.json");
            notes.clear();
            noteAdapter = new NoteAdapter(this, android.R.layout.simple_list_item_1, notes);
            noteView.setAdapter(noteAdapter);
        });

        //Listview onClickListener
        noteView.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(getApplicationContext(),
                "Click ListItem Number " + position, Toast.LENGTH_LONG)
                .show());
    }
    private class NoteAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> noteMap = new HashMap<>();

        public NoteAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                noteMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return noteMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
    public void updateNoteList(String allNotes){
        String[] lines = allNotes.split("\n");
        notes.clear();
        notes.addAll(Arrays.asList(lines));
        noteAdapter = new NoteAdapter(this, android.R.layout.simple_list_item_1, notes);
        noteView.setAdapter(noteAdapter);
    }
}

