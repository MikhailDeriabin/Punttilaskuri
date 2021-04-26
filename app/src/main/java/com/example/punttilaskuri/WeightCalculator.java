package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * This class is attached to the activity that counts BMR meaning calories for
 * the user when the user has chosen all the parameters he/she desires.
 * @author Henri Johansson
 */
public class WeightCalculator extends AppCompatActivity {

    SeekBar seekBarHeight;
    SeekBar seekBarWeight;
    SeekBar seekBarAge;
    BmrCalculator bmrCalc;
    TextView age;
    TextView weight;
    TextView height;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_calculator);
        seekBarAge = (SeekBar) findViewById(R.id.seekBarAge);
        seekBarWeight = (SeekBar) findViewById(R.id.seekBarWeight);
        seekBarHeight = (SeekBar) findViewById(R.id.seekBarHeight);
        height = (TextView) findViewById(R.id.heightNow);
        weight = (TextView) findViewById(R.id.weightNow);
        age = (TextView) findViewById(R.id.ageNow);
        output = (TextView) findViewById(R.id.output);

        bmrCalc = new BmrCalculator(25,100, 10, true);
        setSeekBarListener(seekBarHeight, seekBarType.HEIGHT);
        setSeekBarListener(seekBarWeight, seekBarType.WEIGHT);
        setSeekBarListener(seekBarAge, seekBarType.AGE);
    }

    private enum seekBarType{
        HEIGHT,
        WEIGHT,
        AGE
    }

    public void setSeekBarListener(SeekBar seekBar, seekBarType typeofBar){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (typeofBar){
                    case HEIGHT:
                        bmrCalc.setHeightCm(progress);
                        height.setText(progress + " cm");
                        break;
                    case WEIGHT:
                        bmrCalc.setWeightKg(progress);
                        weight.setText(progress + " Kg");
                        break;
                    case AGE:
                        bmrCalc.setAge(progress);
                        age.setText(progress + " years");
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void onClickCalories(View view){
        output.setText("You need " + String.format("%.2f",bmrCalc.calculateBmr()) + " Calories/day.");
    }
    public void onClickMale(View view){
        bmrCalc.setMale(true);
    }
    public void onClickFemale(View view){
        bmrCalc.setMale(false);
    }
}