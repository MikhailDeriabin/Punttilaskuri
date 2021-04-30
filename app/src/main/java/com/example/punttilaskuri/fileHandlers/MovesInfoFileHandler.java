package com.example.punttilaskuri.fileHandlers;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class MovesInfoFileHandler{
    private final Context context;

    public MovesInfoFileHandler(Context context) {
        this.context = context;
    }

    public String[] getMovesNamesAsArray(){
        String moveNamesString;
        String[] movesNamesArray = new String[0];
        InputStream is;
        try {
            AssetManager manager = context.getAssets();
            is = manager.open("movesPref.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            moveNamesString = new String(buffer, "UTF-8");

            if (moveNamesString.equals(""))
                moveNamesString = "{}";
            JSONObject rootObj = new JSONObject(moveNamesString);
            JSONArray moveNames = rootObj.getJSONArray("movesNames");
            int movesCount = moveNames.length();
            movesNamesArray = new String[movesCount];

            for(int i = 0; i < movesCount; i++){
                movesNamesArray[i] = moveNames.get(i).toString();
            }
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }
        return movesNamesArray;

    }

    /*public String[] getMovesNamesAsArray(){
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
    }*/
}
