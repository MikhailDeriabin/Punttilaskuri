package com.example.punttilaskuri;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class AddNewMoveFragment extends Fragment {

    private EditText newMoveNameInput, newMoveTimesInput, newMoveLoopsInput;
    private ImageButton deleteMoveButton, saveMoveButton;
    private Training training;
    private ListView trainingMovesListView;
    private FragmentActivity myContext;

    public AddNewMoveFragment(Training training, ListView trainingMovesListView){
        this.training = training;
        this.trainingMovesListView = trainingMovesListView;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //get view element for getting fragment's UI elements
        View view = inflater.inflate(R.layout.fragment_new_move_adding, container, false);

        //UI elements
        newMoveNameInput = view.findViewById(R.id.newMoveNameInput);
        newMoveTimesInput = view.findViewById(R.id.newMoveTimesInput);
        newMoveLoopsInput = view.findViewById(R.id.newMoveLoopsInput);

        saveMoveButton = view.findViewById(R.id.saveMoveButton);
        deleteMoveButton = view.findViewById(R.id.deleteMoveButton);

        boolean isChanging = false;
        String choseMoveName = "";
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            isChanging = bundle.getBoolean("isChanging", false);
            choseMoveName = bundle.getString("choseMoveName");
            ArrayList<String> movesInformation = training.getTrainingInformation().get(choseMoveName);
            if(movesInformation != null){
                String moveName = movesInformation.get(0);
                String timesCount = movesInformation.get(1);
                String loopsCount = movesInformation.get(2);

                //User input checking, if something wrong put default values or remove wrong symbols
                UserInputChecker userInputChecker = new UserInputChecker();

                moveName = userInputChecker.removeSpecialCharacters(moveName);
                timesCount = userInputChecker.removeSpecialCharacters(timesCount);
                loopsCount = userInputChecker.removeSpecialCharacters(loopsCount);

                timesCount = userInputChecker.removeAllLetters(timesCount);
                loopsCount = userInputChecker.removeAllLetters(loopsCount);

                newMoveNameInput.setText(moveName);
                newMoveTimesInput.setText(timesCount);
                newMoveLoopsInput.setText(loopsCount);
            }
        }

        if(!isChanging)
            deleteMoveButton.setEnabled(false);

        //Events
        boolean finalIsChanging = isChanging;
        String finalChoseMoveName = choseMoveName;
        saveMoveButton.setOnClickListener(v -> {
            String moveName = newMoveNameInput.getText().toString();
            String timesCount = newMoveTimesInput.getText().toString();
            String loopsCount = newMoveLoopsInput.getText().toString();

            newMoveNameInput.setText("");
            newMoveTimesInput.setText("");
            newMoveLoopsInput.setText("");

            //User input checking, if something wrong put default values or remove wrong symbols
            UserInputChecker userInputChecker = new UserInputChecker();

            moveName = userInputChecker.removeSpecialCharacters(moveName);
            timesCount = userInputChecker.removeSpecialCharacters(timesCount);
            loopsCount = userInputChecker.removeSpecialCharacters(loopsCount);

            timesCount = userInputChecker.removeAllLetters(timesCount);
            loopsCount = userInputChecker.removeAllLetters(loopsCount);

            if(moveName.equals("")){
                moveName = "new move";
            }
            if(timesCount.equals("")){
                timesCount = "10";
            }
            if(loopsCount.equals("")){
                loopsCount = "3";
            }

            if(!finalIsChanging){
                training.addMove(moveName, timesCount, loopsCount);
            } else {
                training.changeMove(finalChoseMoveName, moveName, timesCount, loopsCount);
            }

            updateListView();
            //Close fragment if it was move changing
            if(finalIsChanging){
                myContext.getSupportFragmentManager().beginTransaction().remove(this).commit();
            }

        });

        deleteMoveButton.setOnClickListener(v -> {
            try{
                training.removeMove(finalChoseMoveName);
                updateListView();
            } catch(Exception e){
                deleteMoveButton.setEnabled(false);
                e.printStackTrace();
            }
            FragmentManager fragmentManager = myContext.getSupportFragmentManager();
            fragmentManager.beginTransaction().remove(this).commit();
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void updateListView(){
        TrainingMovesAdapter trainingMovesAdapter = new TrainingMovesAdapter(getContext(), training);
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

                FragmentManager fragmentManager = myContext.getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.moveNamesFrame, fragment);
                fragmentTransaction.commit();


            }
        });
    }
}
