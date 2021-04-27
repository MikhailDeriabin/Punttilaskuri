package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.punttilaskuri.fileHandlers.MovesInfoFileHandler;
import com.example.punttilaskuri.fileHandlers.TrainingsInfoFileHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class TrainingActivity extends AppCompatActivity {

    private EditText trainingNameInput;
    private Button addTrainingMoveButton, saveTrainingButton;
    public Training training;
    private ListView trainingMovesListView, movesNamesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        Bundle b = getIntent().getExtras();
        String trainingName = b.getString("trainingName", "");
        boolean isNewTraining = b.getBoolean("isNewTraining", false);

        trainingNameInput = findViewById(R.id.trainingNameInput);
        trainingMovesListView = findViewById(R.id.trainingMovesListView);
        addTrainingMoveButton = findViewById(R.id.addTrainingMoveButton);
        saveTrainingButton = findViewById(R.id.saveTrainingButton);
        movesNamesListView = findViewById(R.id.movesNamesListView);

        TrainingsInfoFileHandler trainingsInfoFileHandler = new TrainingsInfoFileHandler(this);
        trainingNameInput.setText(trainingName);

        training = trainingsInfoFileHandler.getTrainingInfoAsTrainingObj(trainingName);

        if(!isNewTraining){
            String[] movesNames = training.getUserReadableMovesNames().toArray(new String[0]);

            if(movesNames != null && movesNames.length > 0){
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_view_training_added_moves, R.id.addedMoveTV, movesNames);
                trainingMovesListView.setAdapter(arrayAdapter);

            /*trainingMovesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent nextActivity = new Intent(CreatedTrainingsActivity.this, TrainingActivity.class);
                    nextActivity.putExtra("isNewTraining", false);
                    nextActivity.putExtra("trainingName", movesNames[i]);
                    startActivity(nextActivity);
                }
            });*/
            }
        }


        // Predefined moves
        MovesInfoFileHandler movesInfoFileHandler = new MovesInfoFileHandler(this);
        String[] moves = movesInfoFileHandler.getMovesNamesAsArray();

        if(moves.length > 0){
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_view_moves_names, R.id.moveNameTV, moves);
            movesNamesListView.setAdapter(arrayAdapter);

            /*movesNamesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent nextActivity = new Intent(CreatedTrainingsActivity.this, TrainingActivity.class);
                    nextActivity.putExtra("isNewTraining", false);
                    nextActivity.putExtra("trainingName", movesNames[i]);
                    startActivity(nextActivity);
                }
            });*/
        }


        addTrainingMoveButton.setOnClickListener( v -> {
            //send data to fragment
            AddNewMoveFragment fragment = new AddNewMoveFragment(training);
            Bundle bundle = new Bundle();
            bundle.putBoolean("isNewTraining", isNewTraining);
            fragment.setArguments(bundle);

            //get all fragments
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            //change fragment
            fragmentTransaction.replace(R.id.moveNamesFrame, fragment);
            fragmentTransaction.commit();
        });

        saveTrainingButton.setOnClickListener(v -> {
            String newTrainingName = trainingNameInput.getText().toString();
            try {
                if(!isNewTraining){
                    trainingsInfoFileHandler.removeTraining(trainingName);
                }
                training.setTrainingName(newTrainingName);
                trainingsInfoFileHandler.rewriteTrainingByTrainingObject(training, isNewTraining);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent nextActivity = new Intent(this, CreatedTrainingsActivity.class);
            startActivity(nextActivity);
        });
    }
}