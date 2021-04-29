package com.example.punttilaskuri;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

public class Training {
    private String trainingName;
    private LinkedHashMap<String, ArrayList<String>> movesInformation;
    private boolean isTrainingEmpty;

    public Training(String trainingName, LinkedHashMap<String, ArrayList<String>> movesInformation){
        this.trainingName = trainingName;
        this.movesInformation = movesInformation;
        isTrainingEmpty = defineIsTrainingEmpty();
    }

    public void addMove(String moveName, String times, String loops){
        ArrayList<String> moveInformation = new ArrayList<>();
        ArrayList<String> moveNames = getUserReadableMovesNames();
        String key = moveName;

        if(moveNames.contains(key)){
            key += Integer.toString(getSameStartElemCount(key, moveNames));
        }

        moveInformation.add(moveName);
        moveInformation.add(times);
        moveInformation.add(loops);
        movesInformation.put(key, moveInformation);
        if(isTrainingEmpty)
            isTrainingEmpty = false;
    }

    // ! moveName is key value
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

    // ! moveName is key value
    public void removeMove(String moveName){
        movesInformation.remove(moveName);
    }

    public String getTrainingName() {
        return trainingName;
    }
    public void setTrainingName(String newName){
        trainingName = newName;
    }

    public LinkedHashMap<String, ArrayList<String>> getTrainingInformation(){
        return movesInformation;
    }

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

    public String[] getUserReadableMovesNamesAsArray(){
        if(!isTrainingEmpty)
            return getUserReadableMovesNames().toArray(new String[0]);
        return new String[0];
    }

    public Set<String> getMovesNames(){
        if(!isTrainingEmpty)
            return movesInformation.keySet();
        return null;
    }

    public String[] getMovesNamesAsArray(){
        if(!isTrainingEmpty)
            return getMovesNames().toArray(new String[0]);
        return new String[0];
    }

    //Technical methods
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
