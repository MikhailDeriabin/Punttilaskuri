package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button calendarButton, calculatorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarButton = findViewById(R.id.calendarButton);
        calculatorButton = findViewById(R.id.calculatorButton);

        calendarButton.setOnClickListener( v -> {
            Intent intent = new Intent("com.example.punttilaskuri.CalendarActivity");
            startActivity(intent);
        });

        calculatorButton.setOnClickListener( v -> {
            Intent intent = new Intent("com.example.punttilaskuri.CalculatorActivity");
            startActivity(intent);
        });
    }
}