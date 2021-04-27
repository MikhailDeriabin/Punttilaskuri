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

    private Button calendarButton, calculatorButton;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileHandler fileHandler = new FileHandler(this);

        if(!fileHandler.isFileExist("movesInfo.json")){
            String moveNames = null;
            InputStream is = null;
            try {
                AssetManager manager = getAssets();
                is = manager.open("movesPref.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                moveNames = new String(buffer, StandardCharsets.UTF_8);
                fileHandler.saveDataToFile("movesInfo.json", moveNames);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

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