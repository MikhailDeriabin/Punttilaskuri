package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.punttilaskuri.fileHandlers.TrainingsInfoFileHandler;
import com.example.punttilaskuri.fileHandlers.TrainingsNamesHandler;

import org.json.JSONException;

/**
 * @author Mikhail Deriabin
 */
public class CreatedTrainingsActivity extends AppCompatActivity {

    private Button addTrainingButton;
    private ListView trainingsListView;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_trainings);

        Bundle b = getIntent().getExtras();
        boolean isTrainingChoice = b.getBoolean("isTrainingChoice", false);

        TrainingsInfoFileHandler trainingsInfoFileHandler = new TrainingsInfoFileHandler(this);
        String[] trainingNames = trainingsInfoFileHandler.getTrainingNames();

        addTrainingButton = findViewById(R.id.addTrainingButton);
        trainingsListView = findViewById(R.id.trainingsListView);

        if(isTrainingChoice){
            addTrainingButton.setEnabled(false);
        }

        context = this;

        if(trainingNames != null){
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_view_item_created_training, R.id.trainingNameTV, trainingNames);
            trainingsListView.setAdapter(arrayAdapter);

            trainingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(!isTrainingChoice){
                        Intent nextActivity = new Intent(CreatedTrainingsActivity.this, TrainingActivity.class);
                        nextActivity.putExtra("isNewTraining", false);
                        nextActivity.putExtra("trainingName", trainingNames[i]);
                        startActivity(nextActivity);
                    } else{
                        TrainingsNamesHandler trainingsNamesHandler = new TrainingsNamesHandler(context);
                        String choseTrainingName = trainingNames[i];
                        try {
                            String date = b.getString("date");
                            trainingsNamesHandler.addInformation(date, choseTrainingName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent nextActivity = new Intent(CreatedTrainingsActivity.this, DayActivity.class);
                        int choseYear = b.getInt("choseYear", 2021);
                        int choseMonth = b.getInt("choseMonth", 1);
                        int choseDay = b.getInt("choseDay", 1);
                        String date = b.getString("date");

                        nextActivity.putExtra("choseYear", choseYear);
                        nextActivity.putExtra("choseMonth", choseMonth);
                        nextActivity.putExtra("choseDay", choseDay);
                        startActivity(nextActivity);
                    }
                }
            });
        }

        addTrainingButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrainingActivity.class);
            intent.putExtra("trainingName", "New training");
            intent.putExtra("isNewTraining", true);
            startActivity(intent);
        });
    }
}