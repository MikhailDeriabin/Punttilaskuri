package com.example.punttilaskuri.fileHandlers;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TrainingsInfoFileHandler extends FileHandler{
    private final String fileName = "trainingsInfo.json";

    public TrainingsInfoFileHandler(Context context){
        super(context);
    }

    //add one "item" of defined content type, like some note to notes array (see file structure above)
    public void addMove(String trainingName, String moveName, JSONArray moveInformation) throws JSONException {
        //get all content of the file in string
        String trainingContent = readData(fileName);
        //if file doesn't content anything or exists, create new one with empty JSON object
        if(trainingContent.equals(""))
            trainingContent = "{}";

        JSONObject rootObject = new JSONObject(trainingContent);
        trainingName = trainingName.replaceAll("\\s", "");
        //If for this day is not any notes or trainings, create object for this day(see file structure above)
        if(rootObject.isNull(trainingName))
            addTrainingObject(trainingName, rootObject);

        //Add new "item" to the end of the array
        JSONObject trainingMoves = rootObject.getJSONObject(trainingName).getJSONObject("moves");
        //delete all spaces
        moveName = moveName.replaceAll("\\s", "");
        if(!trainingMoves.isNull(moveName))
            moveName += Integer.toString(getSameStartElemCount(moveName, trainingMoves));
        trainingMoves.put(moveName, moveInformation);
        saveDataToFile(fileName, rootObject.toString());
    }

    //Get information (only about chose type notes or trainings) for chose day in ArrayList<String>
    public HashMap<String, ArrayList<String>> getTrainingInformationAsHashMap(String trainingName){
        HashMap<String, ArrayList<String>> trainingInformation = new HashMap<>();

        try {
            JSONObject jsonObj = new JSONObject(readData(fileName));
            JSONObject allTrainingInfo = jsonObj.getJSONObject(trainingName).getJSONObject("moves");
            Iterator<String> moves = allTrainingInfo.keys();
            //Convert information to ArrayList
            while(moves.hasNext()){
                String moveName = moves.next();
                JSONArray moveInformationJson = allTrainingInfo.getJSONArray(moveName);
                ArrayList<String> moveInformation = new ArrayList<>();
                for (int i = 0; i < moveInformationJson.length(); i++){
                    moveInformation.add(moveInformationJson.getString(i));
                }
                trainingInformation.put(moveName, moveInformation);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return trainingInformation;
    }

    public String[] getTrainingNames(){
        String[] trainingNames = null;
        try {
            JSONObject allTrainingsInformation = new JSONObject(readData(fileName));
            Iterator<String> trainingNamesIterator = allTrainingsInformation.keys();
            int trainingCount = allTrainingsInformation.length();
            trainingNames = new String[trainingCount];
            int i = 0;
            while(trainingNamesIterator.hasNext()){
                String trainingName = trainingNamesIterator.next();
                trainingNames[i] = trainingName;
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trainingNames;
    }

    //Technical methods for this class

    //add "training" object, see file structure above
    private void addTrainingObject(String trainingName, JSONObject rootElem) throws JSONException {
        rootElem.put(trainingName, new JSONObject());
        JSONObject trainingObject = rootElem.getJSONObject(trainingName);
        trainingObject.put("moves", new JSONObject());
    }

    private int getSameStartElemCount(String keyStart, JSONObject jsonObject){
        int elemCount = 0;
        Iterator<String> iterator = jsonObject.keys();
        while(iterator.hasNext()){
            String key = iterator.next();

            int keyLength = keyStart.length();
            String subStr = key.substring(0, keyLength);
            if(subStr.equals(keyStart)){
                elemCount++;
            }
        }
        return  elemCount;
    }

}
