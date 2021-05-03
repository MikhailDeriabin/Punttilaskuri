package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.punttilaskuri.fileHandlers.TrainingsInfoFileHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class TrainingMoves extends AppCompatActivity {

    private ListView movesNamesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainings_moves);

        Bundle b = getIntent().getExtras();
        String trainingName = b.getString("trainingName", "");

        movesNamesListView = findViewById(R.id.movesNamesListView);

        TrainingsInfoFileHandler trainingsInfoFileHandler = new TrainingsInfoFileHandler(this);

        Training training = trainingsInfoFileHandler.getTrainingInfoAsTrainingObj(trainingName);

        //displayed items
        HashMap<String, ArrayList<String>> moves = training.getTrainingInformation();

        if(moves != null && moves.size() > 0) {
            TrainingMovesAdapter trainingMovesAdapter = new TrainingMovesAdapter(this, training);
            movesNamesListView.setAdapter(trainingMovesAdapter);
        }
    }
}