package com.example.punttilaskuri.fileHandlers;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

public class MovesInfoFileHandler extends FileHandler{
    private final String fileName = "movesInfo.json";
    String movesNames;
    public MovesInfoFileHandler(Context context){
        super(context);
        movesNames = readData(fileName);
    }

    public String[] getMovesNamesAsArray(){
        String[] movesNamesArray = new String[0];
        try {
            String moves = movesNames;
            if (moves.equals(""))
                moves = "{}";
            JSONObject rootObj = new JSONObject(moves);
            JSONArray moveNames = rootObj.getJSONArray("movesNames");
            int movesCount = moveNames.length();
            movesNamesArray = new String[movesCount];

            for(int i = 0; i < movesCount; i++){
                movesNamesArray[i] = moveNames.get(i).toString();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return movesNamesArray;
    }
}
