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

/**
 * This class works with trainingsInfo.json file, which contains information about each training
 * <p>This file structure:</p>
 * <p>{</p>
 * <p>   "myTraining": {</p>
 * <p>       "move1" : [10, 3], //times, loops</p>
 * <p>  }</p>
 * <p>}</p>
 * It reads and writes information from this file
 * @see FileHandler
 * @author Mikhail Deriabin
 * @version 27.04.2021
 */

public class TrainingsInfoFileHandler extends FileHandler{
    private final String fileName = "trainingsInfo.json";

    public TrainingsInfoFileHandler(Context context){
        super(context);
    }

    /**
     * adds new move to training
     * @param trainingName name of the training
     * @param moveName name of move to be added (will be added to the end)
     * @param moveInformation information to be added
     * @throws JSONException if there is problems with converting String to JSON format
     */
    public void addMove(String trainingName, String moveName, JSONArray moveInformation) throws JSONException {
        JSONObject rootObject = getJsonFileRootObject(fileName);
        //If training does not exist, create training object (see file structure above)
        if(rootObject.isNull(trainingName))
            addTrainingObject(trainingName, rootObject);

        JSONObject trainingMoves = rootObject.getJSONObject(trainingName).getJSONObject("moves");
        //delete all spaces from move name
        moveName = moveName.replaceAll("\\s", "");
        //if move name(=key in training map) exists, change it to another(add count of same move names to the end)
        if(!trainingMoves.isNull(moveName))
            moveName += Integer.toString(getSameStartElemCount(moveName, trainingMoves));
        trainingMoves.put(moveName, moveInformation);
        saveDataToFile(fileName, rootObject.toString());
    }

    /**
     * rewrites training information, using Training class's object
     * @see Training
     * @param trainingObj Training class's object, with whole information about training
     * @throws JSONException if there is problems with converting String to JSON format
     */
    public void rewriteTrainingByTrainingObject(Training trainingObj) throws JSONException {
        String trainingName = trainingObj.getTrainingName();
        //contains moves and their information(times and loops)
        HashMap<String, ArrayList<String>> trainingInformation = trainingObj.getTrainingInformation();
        //move name(=only key in training map, not displayed in the app)
        Set<String> movesNames = trainingObj.getMovesNames();

        //training information
        JSONObject rootObject = getJsonFileRootObject(fileName);
        //if training already exists rename it(add count of same trainings names to the end)
        if(!rootObject.isNull(trainingName)){
            int keyCount = getSameStartElemCount(trainingName, rootObject);
            trainingName += Integer.toString(keyCount);
        }

        //If training does not exist, create training object(see file structure above, "myTraining")
        if(rootObject.isNull(trainingName))
            addTrainingObject(trainingName, rootObject);
        //If training has moves rewrite they
        if(movesNames != null){
            rootObject.getJSONObject(trainingName).remove("moves");
            rootObject.getJSONObject(trainingName).put("moves", new JSONObject("{}"));
            JSONObject trainingMoves =  rootObject.getJSONObject(trainingName).getJSONObject("moves");

                for(String moveName : movesNames){
                    trainingMoves.put(moveName, new JSONArray(trainingInformation.get(moveName)));
                }
        }
        saveDataToFile(fileName, rootObject.toString());
    }

    /**
     * removes training from file
     * @param trainingName name of the training to be removed
     */
    public void removeTraining(String trainingName){
        JSONObject rootObject = getJsonFileRootObject(fileName);
        rootObject.remove(trainingName);
        saveDataToFile(fileName, rootObject.toString());
    }

    /**
     * Get information about training(moves names, times, loops)
     * <p>may produce JSONException if there is problems with converting String to JSON format</p>
     * @param trainingName name of the training
     * @return training information as LinkedMap(for saving elements order)
     */
    public LinkedHashMap<String, ArrayList<String>> getTrainingInformationAsHashMap(String trainingName){
        LinkedHashMap<String, ArrayList<String>> trainingInformation = new LinkedHashMap<>();

        try {
            JSONObject jsonObj = getJsonFileRootObject(fileName);
            //If training does not exist, create training object(see file structure above, "myTraining")
            if(jsonObj.isNull(trainingName))
                addTrainingObject(trainingName, jsonObj);

            JSONObject allTrainingInfo = jsonObj.getJSONObject(trainingName).getJSONObject("moves");
            Iterator<String> moves = allTrainingInfo.keys();
            //Convert information from JSONObject to LinkedHashMap
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

    /**
     * Get information about training(moves names, times, loops)
     * @param trainingName name of the training
     * @return training information as Training class's object
     */
    public Training getTrainingInfoAsTrainingObj(String trainingName){
        LinkedHashMap<String, ArrayList<String>> trainingInformation = getTrainingInformationAsHashMap(trainingName);
        return new Training(trainingName, trainingInformation);
    }

    /**
     * Get trainings' names
     * @return trainings' names as String[]
     */
    public String[] getTrainingNames(){
        JSONObject allTrainingsInformation = getJsonFileRootObject(fileName);
        Iterator<String> trainingNamesIterator = allTrainingsInformation.keys();
        int trainingCount = allTrainingsInformation.length();
        //method result
        String[] trainingNames = new String[trainingCount];
        int i = 0;
        //put all names to array
        while(trainingNamesIterator.hasNext()){
            String trainingName = trainingNamesIterator.next();
            trainingNames[i] = trainingName;
            i++;
        }

        return trainingNames;
    }

    //Technical methods for this class

    /**
     * add "training object", see file structure above
     * @param trainingName name of the training
     * @param rootElem JSONObject, where "training object" will be created
     * @throws JSONException if there is a problems with creating JSONObject
     */
    private void addTrainingObject(String trainingName, JSONObject rootElem) throws JSONException {
        rootElem.put(trainingName, new JSONObject());
        JSONObject trainingObject = rootElem.getJSONObject(trainingName);
        trainingObject.put("moves", new JSONObject());
    }

    /**
     * search in JSONObject for all keys with same start
     * <p>used to avoid same keys in Map</p>
     * <p>Example: Map has keys: move, move1, move2</p>
     * <p>keyStart == "move"</p>
     * <p>method return 3</p>
     * @param keyStart start of the key
     * @param jsonObject object to be inspected
     * @return count of keys with same start
     */
    private int getSameStartElemCount(String keyStart, JSONObject jsonObject){
        int elemCount = 1;
        Iterator<String> iterator = jsonObject.keys();
        while(iterator.hasNext()){
            String key = iterator.next();
            int keyStartLength = keyStart.length();
            if(!(key.length() > keyStartLength))
                break;
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
