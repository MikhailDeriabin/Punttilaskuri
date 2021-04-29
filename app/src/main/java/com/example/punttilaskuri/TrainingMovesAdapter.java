package com.example.punttilaskuri;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TrainingMovesAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Training training;
    private LinkedHashMap<String, ArrayList<String>> movesInformation;
    private String[] movesNames;

    public TrainingMovesAdapter(Context context, Training training) {
        layoutInflater = LayoutInflater.from(context);
        this.training = training;
        movesInformation = training.getTrainingInformation();
        movesNames = training.getMovesNamesAsArray();
    }

    @Override
    public int getCount() {
        return movesInformation.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.list_view_training_added_moves, null);
        TextView addedMoveNameTV = convertView.findViewById(R.id.addedMoveNameTV);
        TextView addedMoveTimesTV = convertView.findViewById(R.id.addedMoveTimesTV);
        TextView addedMoveLoopsTV = convertView.findViewById(R.id.addedMoveLoopsTV);

        String moveName = movesNames[position];

        addedMoveNameTV.setText(movesInformation.get(moveName).get(0));
        addedMoveTimesTV.setText(movesInformation.get(moveName).get(1));
        addedMoveLoopsTV.setText(movesInformation.get(moveName).get(2));
        return convertView;
    }
}
