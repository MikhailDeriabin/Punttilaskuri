package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

/**
 * This is a hub class to let the user enter all the other Classes that the Calculators has to offer.
 * @author Henri Johansson
 */
public class CalculatorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
    }

    /**
     * Opens an activity if the right view gets sent to this function.
     * @param view Buttons use this to send their view here
     */
    public void calculatorButtons(View view){
        if(view == findViewById(R.id.bmrEnter)){
            Intent calorieCalc = new Intent(this, CalorieCalculator.class);
            startActivity(calorieCalc);
        } else if(view == view.findViewById(R.id.metEnter)){
            Intent exerciseCalorieCalc = new Intent(this, ExerciseCalories.class);
            startActivity(exerciseCalorieCalc);
        } else if(view == view.findViewById(R.id.infoEnter)){
            Intent infoAboutCalculators = new Intent(this, CalculatorInfoActivity.class);
            startActivity(infoAboutCalculators);
        }
    }
}