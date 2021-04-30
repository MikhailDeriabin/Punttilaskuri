package com.example.punttilaskuri;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * This class manipulate with information about training (moves, times, loops),
 * used as interface between app's activities and TrainingsInfoFileHandler class
 * @see com.example.punttilaskuri.fileHandlers.TrainingsInfoFileHandler
 * @author Mikhail Deriabin
 * @version 28.04.2021
 */
public class Training {
    private String trainingName;
    private LinkedHashMap<String, ArrayList<String>> movesInformation;
    private boolean isTrainingEmpty;

    public Training(String trainingName, LinkedHashMap<String, ArrayList<String>> movesInformation){
        this.trainingName = trainingName;
        this.movesInformation = movesInformation;
        isTrainingEmpty = defineIsTrainingEmpty();
    }

    /**
     * adds new move to training, move will added to the end of the moves ArrayList
     * @param moveName name of the move
     * @param times times per loop
     * @param loops loops count
     */
    public void addMove(String moveName, String times, String loops){
        //Move information, which will be added
        ArrayList<String> moveInformation = new ArrayList<>();
        ArrayList<String> moveNames = getUserReadableMovesNames();
        String key = moveName;

        //if move name(=key in training map) exists, change it to another(add count of same move names to the end)
        if(moveNames.contains(key)){
            key += Integer.toString(getSameStartElemCount(key, moveNames));
        }

        //add move information: {moveName, times, loops}
        moveInformation.add(moveName);
        moveInformation.add(times);
        moveInformation.add(loops);
        movesInformation.put(key, moveInformation);
        if(isTrainingEmpty)
            isTrainingEmpty = false;
    }

    /**
     * change move information, without changing order in ArrayList
     * @param moveName name of the move (=key in Map)
     * @param newName name of the move
     * @param times times per loop
     * @param loops loops count
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void changeMove(String moveName, String newName, String times, String loops){
        if(!isTrainingEmpty){
            ArrayList<String> moveInformationArrayList = new ArrayList<>();
            moveInformationArrayList.add(newName);
            moveInformationArrayList.add(times);
            moveInformationArrayList.add(loops);
            this.movesInformation.replace(moveName, moveInformationArrayList);
        }
    }

    /**
     * remove move information, without changing order in ArrayList
     * @param moveName name of the move (=key in Map)
     */
    public void removeMove(String moveName){
        movesInformation.remove(moveName);
    }

    /**
     * trainingName getter
     * @return trainingName
     */
    public String getTrainingName() {
        return trainingName;
    }
    /**
     * trainingName setter
     */
    public void setTrainingName(String newName){
        trainingName = newName;
    }

    /**
     * trainingInformation getter
     * @return trainingInformation, including moves, times, loops
     */
    public LinkedHashMap<String, ArrayList<String>> getTrainingInformation(){
        return movesInformation;
    }

    /**
     * moves names getter, in user readable format
     * <p>Not: "Chest fly1", "Chest fly2", But: "Chest fly", "Chest fly" </p>
     * @return moves names as ArrayList
     */
    public ArrayList<String> getUserReadableMovesNames(){
        ArrayList<String> result = new ArrayList<>();
        Set<String> moveNames = getMovesNames();
        if(!isTrainingEmpty){
            for(String move : moveNames){
                result.add(movesInformation.get(move).get(0));
            }
        }
        return result;
    }

    /**
     * moves names getter, in user readable format
     * <p>Not: "Chest fly1", "Chest fly2", But: "Chest fly", "Chest fly" </p>
     * @return moves names as Array
     */
    public String[] getUserReadableMovesNamesAsArray(){
        if(!isTrainingEmpty)
            return getUserReadableMovesNames().toArray(new String[0]);
        return new String[0];
    }

    /**
     * moves names(=keys in Map) getter
     * <p>Not: "Chest fly", "Chest fly", But: "Chest fly1", "Chest fly2" </p>
     * @return moves names as Set
     */
    public Set<String> getMovesNames(){
        if(!isTrainingEmpty)
            return movesInformation.keySet();
        return null;
    }

    /**
     * moves names(=keys in Map) getter
     * <p>Not: "Chest fly", "Chest fly", But: "Chest fly1", "Chest fly2" </p>
     * @return moves names as Array
     */
    public String[] getMovesNamesAsArray(){
        if(!isTrainingEmpty)
            return getMovesNames().toArray(new String[0]);
        return new String[0];
    }

    //Technical methods
    /**
     * search in ArrayList for all keys with same start
     * <p>used to avoid same keys in Map</p>
     * <p>Example: Map has keys: move, move1, move2</p>
     * <p>keyStart == "move"</p>
     * <p>method return 3</p>
     * @param keyStart start of the key
     * @param arrayList arrayList to be inspected
     * @return count of keys with same start
     */
    private int getSameStartElemCount(String keyStart, ArrayList<String> arrayList){
        int elemCount = 0;
        if(!isTrainingEmpty){
            for(String elem : arrayList){
                int keyLength = keyStart.length();
                if(keyLength <= elem.length()){
                    String subStr = elem.substring(0, keyLength);
                    if(subStr.equals(keyStart)){
                        elemCount++;
                    }
                }
            }
        }
        return  elemCount;
    }

    /**
     * define is Training object contents any move information
     * @return true if empty, false if contains moves
     */
    private boolean defineIsTrainingEmpty(){
        boolean result;
        try{
            result = movesInformation.isEmpty();
        }catch (Exception e){
            result = true;
            e.printStackTrace();
        }
        return result;
    }
}
