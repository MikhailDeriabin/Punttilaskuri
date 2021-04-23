package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class CreatedTrainingsActivity extends AppCompatActivity {

    private Button addTrainingButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_trainings);

        addTrainingButton = findViewById(R.id.addTrainingButton);

        addTrainingButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrainingActivity.class);
            startActivity(intent);
        });
    }
}