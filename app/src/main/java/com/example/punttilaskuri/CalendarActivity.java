package com.example.punttilaskuri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

public class CalendarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // UI elements
    private CalendarView calendarView;
    private Button backButton;
    private DrawerLayout drawer;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.calendarActivity_drawer_open, R.string.calendarActivity_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        drawer.setOnClickListener(v -> {});

        NavigationView navView = findViewById(R.id.navView);
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.showTrainingsButton:
                Intent nextActivity = new Intent(this, CreatedTrainingsActivity.class);
                startActivity(nextActivity);
                break;
            case R.id.toBMRCalculator:
                Intent BMRCalculator = new Intent(this, CalorieCalculator.class);
                startActivity(BMRCalculator);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}