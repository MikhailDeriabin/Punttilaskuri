package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class CalculatorInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_info);
        //Find the Text Views and make the links Clickable.
        TextView linkBmr = findViewById(R.id.bmr_link);
        linkBmr.setMovementMethod(LinkMovementMethod.getInstance());
        TextView linkMet = findViewById(R.id.met_link);
        linkMet.setMovementMethod(LinkMovementMethod.getInstance());
    }
}