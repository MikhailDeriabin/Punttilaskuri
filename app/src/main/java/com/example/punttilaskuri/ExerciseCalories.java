package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class ExerciseCalories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_calories);
        AutoCompleteTextView exerciseView = findViewById(R.id.autoCompleteTextView);

        ArrayAdapter<SingleExercise> exerciseAdapter = new ArrayAdapter<SingleExercise>
                (this, android.R.layout.simple_list_item_1, ExerciseInfo.getInstance().getExercises());
        exerciseView.setAdapter(exerciseAdapter);
    }


}