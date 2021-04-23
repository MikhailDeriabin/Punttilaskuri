package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.punttilaskuri.fileHandlers.TrainingsInfoFileHandler;

import org.json.JSONArray;
import org.json.JSONException;

public class TrainingActivity extends AppCompatActivity {

    private EditText trainingNameInput;
    private Button addTrainingMoveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        trainingNameInput = findViewById(R.id.trainingNameInput);
        addTrainingMoveButton = findViewById(R.id.addTrainingMoveButton);

        addTrainingMoveButton.setOnClickListener(v -> {
            TrainingsInfoFileHandler trainingsInfoFileHandler = new TrainingsInfoFileHandler(this);
            String trainingName = trainingNameInput.getText().toString();

            JSONArray moveInfo = new JSONArray().put("my Move");
            moveInfo.put("10");
            moveInfo.put("3");
            try {
                trainingsInfoFileHandler.addMove(trainingName, "my Move", moveInfo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}