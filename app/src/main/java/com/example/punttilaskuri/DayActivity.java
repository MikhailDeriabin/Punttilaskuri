package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DayActivity extends AppCompatActivity {

    // UI elements
    TextView dateTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        Bundle b = getIntent().getExtras();
        int choseYear = b.getInt("choseYear", 2021);
        int choseMonth = b.getInt("choseMonth", 1);
        int choseDay = b.getInt("choseDay", 1);

        dateTV = findViewById(R.id.dateTV);

        String choseDate = choseDay + "." + choseMonth + "." + choseYear;
        dateTV.setText(choseDate);
    }
}