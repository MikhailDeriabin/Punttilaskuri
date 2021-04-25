package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.punttilaskuri.fileHandlers.TrainingsInfoFileHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class CreatedTrainingsActivity extends AppCompatActivity {

    private Button addTrainingButton;
    private ListView trainingsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_trainings);

        TrainingsInfoFileHandler trainingsInfoFileHandler = new TrainingsInfoFileHandler(this);
        String[] trainingNames = trainingsInfoFileHandler.getTrainingNames();

        addTrainingButton = findViewById(R.id.addTrainingButton);
        trainingsListView = findViewById(R.id.trainingsListView);

        if(trainingNames != null){
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_view_item_created_training, R.id.trainingNameTV, trainingNames);
            trainingsListView.setAdapter(arrayAdapter);

            trainingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent nextActivity = new Intent(CreatedTrainingsActivity.this, TrainingActivity.class);
                    nextActivity.putExtra("isNewTraining", false);
                    nextActivity.putExtra("trainingName", trainingNames[i]);
                    startActivity(nextActivity);
                }
            });
        }


        addTrainingButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrainingActivity.class);
            intent.putExtra("trainingName", "New training");
            intent.putExtra("isNewTraining", true);
            startActivity(intent);
        });
    }
}