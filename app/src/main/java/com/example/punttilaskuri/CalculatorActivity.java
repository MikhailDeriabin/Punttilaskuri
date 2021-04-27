package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class CalculatorActivity extends AppCompatActivity {

    private Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        backButton = findViewById(R.id.calculatorBackButton);

        backButton.setOnClickListener( v -> {
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
        });
    }

    public void calculatorButtons(View view){
        if(view == findViewById(R.id.bmr_enter)){
            Intent caloryCalc = new Intent(this, CalorieCalculator.class);
            startActivity(caloryCalc);
        }
    }
}