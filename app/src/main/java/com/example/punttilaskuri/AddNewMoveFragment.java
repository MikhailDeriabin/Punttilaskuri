package com.example.punttilaskuri;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class AddNewMoveFragment extends Fragment {

    private EditText newMoveNameInput, newMoveTimesInput, newMoveLoopsInput;
    private Button saveMoveButton;
    private ImageButton deleteMoveButton;
    private Training training;
    private ListView trainingMovesListView;

    public AddNewMoveFragment(Training training, ListView trainingMovesListView){
        this.training = training;
        this.trainingMovesListView = trainingMovesListView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
        deleteMoveButton = view.findViewById(R.id.deleteMoveButton);

        boolean isChanging = false;
        String choseMoveName = "";
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            isChanging = bundle.getBoolean("isChanging", false);
            choseMoveName = bundle.getString("choseMoveName");
            ArrayList<String> movesInformation = training.getTrainingInformation().get(choseMoveName);
            if(movesInformation != null){
                newMoveNameInput.setText(movesInformation.get(0));
                newMoveTimesInput.setText(movesInformation.get(1));
                newMoveLoopsInput.setText(movesInformation.get(2));
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

            if(!finalIsChanging){
                training.addMove(moveName, timesCount, loopsCount);
            } else {
                training.changeMove(finalChoseMoveName, moveName, timesCount, loopsCount);
            }

            updateListView();
            //Close fragment if it was move changing
            if(finalIsChanging){
                getFragmentManager().beginTransaction().remove(this).commit();
            }

        });

        deleteMoveButton.setOnClickListener(v -> {
            String moveName = newMoveNameInput.getText().toString();
            try{
                training.removeMove(moveName);
                updateListView();
            } catch(Exception e){
                v.setEnabled(false);
                e.printStackTrace();
            }
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

                FragmentManager fragmentManager = getFragmentManager();
                if(fragmentManager != null){
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.moveNamesFrame, fragment);
                    fragmentTransaction.commit();

                } else {
                    System.out.println("------------------");
                    System.out.println("fragment manager == null");
                    System.out.println("------------------");
                }
            }
        });
    }
}
