package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.punttilaskuri.fileHandlers.TrainingsInfoFileHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class TrainingActivity extends AppCompatActivity {

    private EditText trainingNameInput;
    private Button addTrainingMoveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        Bundle b = getIntent().getExtras();
        String trainingName = b.getString("trainingName", "");
        boolean isNewTraining = b.getBoolean("isNewTraining", false);

        trainingNameInput = findViewById(R.id.trainingNameInput);
        addTrainingMoveButton = findViewById(R.id.addTrainingMoveButton);

        TrainingsInfoFileHandler trainingsInfoFileHandler = new TrainingsInfoFileHandler(this);
        HashMap<String, ArrayList<String>> trainingInfo = trainingsInfoFileHandler.getTrainingInformationAsHashMap(trainingName);

        trainingNameInput.setText(trainingName);

        addTrainingMoveButton.setOnClickListener( v -> {
            String newTrainingName = trainingNameInput.getText().toString();

            //send data to fragment
            AddNewMoveFragment fragment = new AddNewMoveFragment();
            Bundle bundle = new Bundle();
            bundle.putString("trainingName", newTrainingName);
            fragment.setArguments(bundle);

            //get all fragments
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            //change fragment
            fragmentTransaction.replace(R.id.moveNamesFrame, fragment);
            fragmentTransaction.commit();
        });
    }
}