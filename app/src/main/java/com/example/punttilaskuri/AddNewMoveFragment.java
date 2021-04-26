package com.example.punttilaskuri;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.punttilaskuri.fileHandlers.TrainingsInfoFileHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class AddNewMoveFragment extends Fragment {

    private EditText newMoveNameInput, newMoveTimesInput, newMoveLoopsInput;
    private Button saveMoveButton;
    private Training training;

    public AddNewMoveFragment(Training training){
        this.training = training;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //get view element for getting fragment's UI elements
        View activityView = inflater.inflate(R.layout.activity_training, container, false);
        View view = inflater.inflate(R.layout.fragment_new_move_adding, container, false);

        //UI elements
        newMoveNameInput = view.findViewById(R.id.newMoveNameInput);
        newMoveTimesInput = view.findViewById(R.id.newMoveTimesInput);
        newMoveLoopsInput = view.findViewById(R.id.newMoveLoopsInput);

        saveMoveButton = view.findViewById(R.id.saveMoveButton);

        //Events
        saveMoveButton.setOnClickListener(v -> {
            String moveName = newMoveNameInput.getText().toString();
            String timesCount = newMoveTimesInput.getText().toString();
            String loopsCount = newMoveLoopsInput.getText().toString();

            training.addMove(moveName, timesCount, loopsCount);
        });
        // Inflate the layout for this fragment
        return view;
    }
}
