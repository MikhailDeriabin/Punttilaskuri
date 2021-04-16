package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;

public class CalendarActivity extends AppCompatActivity {

    // UI elements
    private CalendarView calendarView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //Defining UI elements
        calendarView = findViewById(R.id.calendarView);
        backButton = findViewById(R.id.calendarBackButton);

        //Events
        calendarView.setOnDateChangeListener( (v, choseYear, choseMonth, choseDay) -> {
            Intent dayActivity = new Intent(this, DayActivity.class);
            dayActivity.putExtra("choseYear", choseYear);
            dayActivity.putExtra("choseMonth", choseMonth);
            dayActivity.putExtra("choseDay", choseDay);
            startActivity(dayActivity);
        });

        backButton.setOnClickListener( v -> {
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
        });
    }
}