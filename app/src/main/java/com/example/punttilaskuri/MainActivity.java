package com.example.punttilaskuri;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import com.example.punttilaskuri.fileHandlers.FileHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calendarButton = findViewById(R.id.calendarButton);
        Button calculatorButton = findViewById(R.id.calculatorButton);
        Button practicesButton = findViewById(R.id.practiceButton);

        calendarButton.setOnClickListener(v -> {
            Intent intent = new Intent("com.example.punttilaskuri.CalendarActivity");
            startActivity(intent);
        });

        calculatorButton.setOnClickListener(v -> {
            Intent intent = new Intent("com.example.punttilaskuri.CalculatorActivity");
            startActivity(intent);
        });

        practicesButton.setOnClickListener(v ->{
            Intent intent = new Intent("com.example.punttilaskuri.CreatedTrainingsActivity");
            intent.putExtra("isTrainingsChoice", false);
            startActivity(intent);
        });
    }
}