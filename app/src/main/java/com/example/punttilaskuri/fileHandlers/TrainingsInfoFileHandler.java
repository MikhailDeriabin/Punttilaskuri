package com.example.punttilaskuri.fileHandlers;

import android.content.Context;

import com.example.punttilaskuri.Training;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

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

    public void rewriteTrainingByTrainingObject(Training trainingObj, boolean isNewTraining) throws JSONException{
        String trainingName = trainingObj.getTrainingName();
        HashMap<String, ArrayList<String>> trainingInformation = trainingObj.getTrainingInformation();
        Set<String> movesNames = trainingObj.getMovesNames();

        String trainingContent = readData(fileName);
        if(trainingContent.equals(""))
            trainingContent = "{}";

        JSONObject rootObject = new JSONObject(trainingContent);
        if(isNewTraining && !rootObject.isNull(trainingName)){
            int keyCount = getSameStartElemCount(trainingName, rootObject);
            trainingName += Integer.toString(keyCount);
        }

        //If for this day is not any notes or trainings, create object for this day(see file structure above)
        if(rootObject.isNull(trainingName))
            addTrainingObject(trainingName, rootObject);

        rootObject.getJSONObject(trainingName).remove("moves");
        rootObject.getJSONObject(trainingName).put("moves", new JSONObject("{}"));
        JSONObject trainingMoves =  rootObject.getJSONObject(trainingName).getJSONObject("moves");

        for(String moveName : movesNames){
            trainingMoves.put(moveName, new JSONArray(trainingInformation.get(moveName)));
        }

        saveDataToFile(fileName, rootObject.toString());
    }

    public void removeTraining(String trainingName) throws JSONException{
        String trainingContent = readData(fileName);
        JSONObject rootObject = new JSONObject(trainingContent);
        rootObject.remove(trainingName);
        saveDataToFile(fileName, rootObject.toString());
    }

    //Get information (only about chose type notes or trainings) for chose day in ArrayList<String>
    public LinkedHashMap<String, ArrayList<String>> getTrainingInformationAsHashMap(String trainingName){
        LinkedHashMap<String, ArrayList<String>> trainingInformation = new LinkedHashMap<>();

        try {
            String trainingContent = readData(fileName);
            if(trainingContent.equals(""))
                trainingContent = "{}";
            JSONObject jsonObj = new JSONObject(trainingContent);

            if(jsonObj.isNull(trainingName))
                addTrainingObject(trainingName, jsonObj);

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
        }

        return trainingInformation;
    }

    public Training getTrainingInfoAsTrainingObj(String trainingName){
        LinkedHashMap<String, ArrayList<String>> trainingInformation = getTrainingInformationAsHashMap(trainingName);
        return new Training(trainingName, trainingInformation);
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
        int elemCount = 1;
        Iterator<String> iterator = jsonObject.keys();
        while(iterator.hasNext()){
            String key = iterator.next();
            int keyStartLength = keyStart.length();
            String subStr = key.substring(0, keyStartLength);
            if(subStr.equals(keyStart)){
                if(key.length() > keyStartLength){
                   if(Character.isDigit(key.charAt(keyStartLength))){
                        elemCount++;
                    }
                }
            }
        }
        return  elemCount;
    }

}
