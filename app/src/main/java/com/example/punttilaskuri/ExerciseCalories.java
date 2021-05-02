package com.example.punttilaskuri;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.os.IResultReceiver;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

/**
 * The purpose of this class is to manage and display the calories used when exercising and put them
 * inside a listView where they can be destroyed with one easy click and the sum total gets calculated here.
 *
 * This class refers to activity_exercise_calories.xml and all the components mentioned here are located
 * there.
 * @author Henri Johansson
 */

public class ExerciseCalories extends AppCompatActivity {

    private int minWeight;
    private int minTime;
    private int maxWeight;
    private int maxTime;
    private AutoCompleteTextView exerciseView;
    ArrayAdapter<String> stringArrayAdapter;

    TextView sumTotal;

    ArrayList<String> resultList;
    ArrayList<Double> calorieList;
    double totalCalories;
    private MetCalculator metCalculator;
    private static final String TAG = SingleExercise.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_calories);

        TextView weight = findViewById(R.id.weightMinExercise);
        TextView time = findViewById(R.id.timeMinExercise);
        TextView weightMax = findViewById(R.id.weightMaxExercise);
        TextView timeMax = findViewById(R.id.timeMaxExercise);
        ListView outputView = findViewById(R.id.resultView);
        sumTotal = findViewById(R.id.sumTextView);
        totalCalories = 0;

        SeekBar seekWeight = findViewById(R.id.seekBarWeightExercise);
        SeekBar seekTime = findViewById(R.id.seekBarTimeExercise);

        resultList = new ArrayList<>();
        calorieList = new ArrayList<>();
        exerciseView = findViewById(R.id.autoCompleteTextView);
        stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1 , resultList);
        outputView.setAdapter(stringArrayAdapter);
        defineRangeValues();

        //Adapter for the dropDownMenu
        ArrayAdapter<SingleExercise> exerciseAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, ExerciseInfo.getInstance().getExercises());
        exerciseView.setAdapter(exerciseAdapter);
        metCalculator = new MetCalculator(minWeight, minTime, 1.0);
        //Setting unit texts
        weight.setText(getString(R.string.weight_unit, minWeight));
        time.setText(getString(R.string.time_unit, minTime));
        weightMax.setText(getString(R.string.weight_unit, maxWeight));
        timeMax.setText(getString(R.string.time_unit, maxTime));

        //SeekBar listeners to listen changes on the Seekbar.
        seekWeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Set parameters for the calculator
                seekBar.setMax( maxWeight - minWeight);
                metCalculator.setWeight(progress + minWeight);
                weight.setText(getString(R.string.weight_unit, progress + minWeight));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Must be included, but is not used currently
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Must be included, but is not used currently
            }
        });
        seekTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setMax(maxTime - minTime);
                metCalculator.setTime(progress + minTime);
                time.setText(getString(R.string.time_unit, progress + minTime));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Must be included, but is not used currently
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Must be included, but is not used currently
            }
        });
        //Items on the listView can be removed by clicking them.
        outputView.setOnItemClickListener((parent, view, position, id) -> {

            totalCalories -= calorieList.get(position);
            calorieList.remove(position);
            resultList.remove(position);
            if(calorieList.isEmpty()){
                totalCalories = 0;
            }
            stringArrayAdapter.notifyDataSetChanged();
            sumTotal.setText(getString(R.string.total_calories, String.format(Locale.ENGLISH,"%.2f", totalCalories)));
        });
        //Empty the text field when it gets clicked.
        exerciseView.setOnClickListener(v -> exerciseView.setText(""));
        //On clickListener to remove SoftInput after choice has been made.
        exerciseView.setOnItemClickListener((parent, view, position, id) -> {
            InputMethodManager inputMethod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethod.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);
            exerciseView.clearFocus();
        });

    }

    //Populates the View with new results that get added to the Array and the adapter updates.
    private void populateView(SingleExercise object, double calories, double time){
        resultList.add(object.toString() + " "+ time + "min " + calories + "cal");
        calorieList.add(calories);
        totalCalories += calories;
        stringArrayAdapter.notifyDataSetChanged();
    }
    //defines range locally that the activity will use for minimums and maximums.
    private void defineRangeValues(){
        minWeight = 40;
        maxWeight = 210 + minWeight;
        minTime = 1;
        maxTime = 179 + minTime;
    }

    /**
     * This function calls the exercise based of users input and if user inputs something
     * that doesn't make sense the if doesn't allow the process to continue.
     * @param view Button normally sends this param to the function
     */
    public void calculateCalories(View view){
        Log.d(TAG, exerciseView.getText().toString());
        //Compare string on autocomplete field with every SingleExercise inside the ExerciseInfo Singelton
        SingleExercise exercise = ExerciseInfo.getInstance().getExerciseWithString(exerciseView.getText().toString());
        Log.d(TAG, "MET amount " + exercise.getMET());
        double met = exercise.getMET();
        if(met > 0.0){
            metCalculator.setExerciseMet(met);
            populateView(exercise, metCalculator.calculateCalMet(), metCalculator.getTime());
            Log.d(TAG, getString(R.string.total_calories, Double.toString(totalCalories)));
            sumTotal.setText(getString(R.string.total_calories, String.format(Locale.ENGLISH,"%.2f", totalCalories)));
        }
    }
}