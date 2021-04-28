package com.example.punttilaskuri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.punttilaskuri.fileHandlers.MovesInfoFileHandler;

public class ChooseMoveFragment extends Fragment {
    private Training training;
    private ListView movesNamesListView, trainingMovesListView;

    public ChooseMoveFragment(Training training, ListView trainingMovesListView){
        this.training = training;
        this.trainingMovesListView = trainingMovesListView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //get view element for getting fragment's UI elements
        View activityView = inflater.inflate(R.layout.activity_training, container, false);
        View view = inflater.inflate(R.layout.fragment_choose_move, container, false);

        movesNamesListView = view.findViewById(R.id.movesNamesListView);

        // Predefined moves
        MovesInfoFileHandler movesInfoFileHandler = new MovesInfoFileHandler(getContext());
        String[] moves = movesInfoFileHandler.getMovesNamesAsArray();

        if(moves.length > 0){
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.list_view_moves_names, R.id.moveNameTV, moves);
            movesNamesListView.setAdapter(arrayAdapter);

            movesNamesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String moveName = moves[i];
                    String timesCount = "10";
                    String loopsCount = "3";

                    training.addMove(moveName, timesCount, loopsCount);
                    TrainingMovesAdapter trainingMovesAdapter = new TrainingMovesAdapter(getContext(), training);
                    trainingMovesListView.setAdapter(trainingMovesAdapter);
                }
            });
        }

       /* //Events
        saveMoveButton.setOnClickListener(v -> {
            String moveName = newMoveNameInput.getText().toString();
            String timesCount = newMoveTimesInput.getText().toString();
            String loopsCount = newMoveLoopsInput.getText().toString();

            training.addMove(moveName, timesCount, loopsCount);
        });*/
        // Inflate the layout for this fragment
        return view;
    }
}
