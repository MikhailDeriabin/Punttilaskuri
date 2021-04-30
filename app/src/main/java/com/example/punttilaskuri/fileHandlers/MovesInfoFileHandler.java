package com.example.punttilaskuri.fileHandlers;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is a class for getting predefined training moves
 * @author Mikhail Deriabin
 * @version 30.04.2021
 */
public class MovesInfoFileHandler{
    private final Context context;

    public MovesInfoFileHandler(Context context) {
        this.context = context;
    }

    /**
     * get all predefined training names
     * <p>may produce JSONException and IOException if there is problems with converting String to JSON format or with reading file</p>
     * @return all move names as Array
     */
    public String[] getMovesNamesAsArray(){
        String moveNamesString;
        //result array to return
        String[] movesNamesArray = new String[0];
        InputStream is;
        //try to read file(assets/movesPref.json) and convert this data to JSON format
        try {
            AssetManager manager = context.getAssets();
            is = manager.open("movesPref.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            moveNamesString = new String(buffer, "UTF-8");

            //if file is empty add empty JSONObject
            if (moveNamesString.equals(""))
                moveNamesString = "{}";
            JSONObject rootObj = new JSONObject(moveNamesString);
            JSONArray moveNames = rootObj.getJSONArray("movesNames");
            //Convert JSONArray to String[]
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
}
