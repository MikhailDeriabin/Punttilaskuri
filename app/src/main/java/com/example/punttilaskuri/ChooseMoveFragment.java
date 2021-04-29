package com.example.punttilaskuri;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.punttilaskuri.fileHandlers.MovesInfoFileHandler;

import java.io.UnsupportedEncodingException;

public class ChooseMoveFragment extends Fragment {
    private Training training;
    private ListView movesNamesListView, trainingMovesListView;
    private FragmentActivity myContext;

    public ChooseMoveFragment(Training training, ListView trainingMovesListView){
        this.training = training;
        this.trainingMovesListView = trainingMovesListView;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //get view element for getting fragment's UI elements
        View view = inflater.inflate(R.layout.fragment_choose_move, container, false);

        movesNamesListView = view.findViewById(R.id.movesNamesListView);

        // Predefined moves
        MovesInfoFileHandler movesInfoFileHandler = null;
        try {
            movesInfoFileHandler = new MovesInfoFileHandler(getContext());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] moves = movesInfoFileHandler.getMovesNamesAsArray();

        if(moves.length > 0){
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(myContext, R.layout.list_view_moves_names, R.id.moveNameTV, moves);
            movesNamesListView.setAdapter(arrayAdapter);

            movesNamesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String moveName = moves[i];
                    UserInputChecker userInputChecker = new UserInputChecker();
                    moveName = userInputChecker.removeSpecialCharacters(moveName);
                    String timesCount = "10";
                    String loopsCount = "3";

                    training.addMove(moveName, timesCount, loopsCount);

                    updateListView();
                }
            });
        }
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
