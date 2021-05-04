package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.punttilaskuri.fileHandlers.TrainingsInfoFileHandler;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author Mikhail Deriabin
 */
public class TrainingActivity extends AppCompatActivity {

    private EditText trainingNameInput;
    private Button addTrainingMoveButton, chooseMoveButton;
    private ImageButton saveTrainingButton, deleteTrainingButton;
    public Training training;
    private ListView trainingMovesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        Bundle b = getIntent().getExtras();
        String trainingName = b.getString("trainingName", "");
        boolean isNewTraining = b.getBoolean("isNewTraining", false);

        trainingNameInput = findViewById(R.id.trainingNameInput);
        trainingMovesListView = findViewById(R.id.trainingMovesListView);
        addTrainingMoveButton = findViewById(R.id.addTrainingMoveButton);
        saveTrainingButton = findViewById(R.id.saveTrainingButton);
        chooseMoveButton = findViewById(R.id.chooseMoveButton);
        deleteTrainingButton = findViewById(R.id.deleteTrainingButton);

        TrainingsInfoFileHandler trainingsInfoFileHandler = new TrainingsInfoFileHandler(this);
        trainingNameInput.setText(trainingName);

        if(!isNewTraining){
            training = trainingsInfoFileHandler.getTrainingInfoAsTrainingObj(trainingName);
            //displayed items
            HashMap<String, ArrayList<String>> moves = training.getTrainingInformation();


            if(moves != null && moves.size() > 0){
                TrainingMovesAdapter trainingMovesAdapter = new TrainingMovesAdapter(this, training);
                trainingMovesListView.setAdapter(trainingMovesAdapter);

                trainingMovesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String[] movesNamesArray = training.getMovesNamesAsArray();
                        String choseMoveName = movesNamesArray[i];
                        AddNewMoveFragment fragment = new AddNewMoveFragment(training, trainingMovesListView);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isChanging", true);
                        bundle.putString("choseMoveName", choseMoveName);
                        fragment.setArguments(bundle);

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.moveNamesFrame, fragment);
                        fragmentTransaction.commit();
                    }
                });
            }
        } else{
            deleteTrainingButton.setEnabled(false);
            trainingNameInput.setSelectAllOnFocus(true);
            training = new Training(trainingName, new LinkedHashMap<String, ArrayList<String>>());
        }


        addTrainingMoveButton.setOnClickListener( v -> {
            //send data to fragment
            AddNewMoveFragment fragment = new AddNewMoveFragment(training, trainingMovesListView);

            //get all fragments
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            //change fragment
            fragmentTransaction.replace(R.id.moveNamesFrame, fragment);
            fragmentTransaction.commit();
        });

        chooseMoveButton.setOnClickListener( v -> {
            //send data to fragment
            ChooseMoveFragment fragment = new ChooseMoveFragment(training, trainingMovesListView);

            //get all fragments
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            //change fragment
            fragmentTransaction.replace(R.id.moveNamesFrame, fragment);
            fragmentTransaction.commit();
        });

        saveTrainingButton.setOnClickListener(v -> {
            String newTrainingName = trainingNameInput.getText().toString();
            try {
                if(!isNewTraining){
                    trainingsInfoFileHandler.removeTraining(trainingName);
                }
                UserInputChecker userInputChecker = new UserInputChecker();
                newTrainingName = userInputChecker.removeSpecialCharacters(newTrainingName);
                if(newTrainingName.equals("")){
                    newTrainingName = "New training";
                }
                training.setTrainingName(newTrainingName);
                trainingsInfoFileHandler.rewriteTrainingByTrainingObject(training);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent nextActivity = new Intent(this, CreatedTrainingsActivity.class);
            nextActivity.putExtra("isTrainingChoice", false);
            startActivity(nextActivity);
        });

        deleteTrainingButton.setOnClickListener(v -> {
            trainingsInfoFileHandler.removeTraining(trainingName);
            Intent nextActivity = new Intent(this, CreatedTrainingsActivity.class);
            nextActivity.putExtra("isTrainingChoice", false);
            startActivity(nextActivity);
        });
    }
}