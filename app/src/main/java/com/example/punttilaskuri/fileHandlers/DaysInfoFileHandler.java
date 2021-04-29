package com.example.punttilaskuri.fileHandlers;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

//This class is parent for NotesHandler and TrainingsNamesHandler classes
//Works with daysInfo.json file, which contains information about each day: notes and planned trainings names
/*This file looks like:
*{
    “1.2.2021”: {
        “notes”: [“This is my first note”, “Second note”],
        “trainings”: [“myTraining”]
    }
}
*/

public class DaysInfoFileHandler extends FileHandler {

    private final String fileName = "daysInfo.json";
    //content type is type of information you store, can be notes or trainings
    private final String contentType;

    public DaysInfoFileHandler(Context context, String contentType) {
        super(context);
        this.contentType = contentType;
    }

    //add one "item" of defined content type, like some note to notes array (see file structure above)
    public void addInformation(String date, String newInformation) throws JSONException, UnsupportedEncodingException {
        //get all content of the file in string
        String daysInfoContent = readData(fileName);
        //if file doesn't content anything or exists, create new one with empty JSON object
        if(daysInfoContent.equals(""))
            daysInfoContent = "{}";

        JSONObject jsonObject = new JSONObject(daysInfoContent);

        //If for this day is not any notes or trainings, create object for this day(see file structure above)
        if(jsonObject.isNull(date))
            addDayObject(date, jsonObject);

        //Add new "item" to the end of the array
        jsonObject.getJSONObject(date).getJSONArray(contentType).put(newInformation);
        saveDataToFile(fileName, jsonObject.toString());
    }

    //Get information (only about chose type notes or trainings) for chose day in String
    public String getDayInformationAsString(String date) throws UnsupportedEncodingException {
        String dayInformation = "";
        try {
            JSONObject jsonObj = new JSONObject(readData(fileName));
            JSONArray allContentTypeInfo = jsonObj.getJSONObject(date).getJSONArray(contentType);
            int notesCount = allContentTypeInfo.length();
            //Convert information to String, where each item starts with new line
            for(int i = 0; i < notesCount; i++){
                dayInformation += allContentTypeInfo.getString(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dayInformation;
    }

    //Get information (only about chose type notes or trainings) for chose day in String[]
    public String[] getDayInformationAsArray(String date){
        String[] dayInformation = null;
        try {
            JSONObject jsonObj = new JSONObject(readData(fileName));
            JSONArray allContentTypeInfo = jsonObj.getJSONObject(date).getJSONArray(contentType);
            int allContentItemCount = allContentTypeInfo.length();
            dayInformation = new String[allContentItemCount];
            //Convert information to array
            for(int i = 0; i < allContentItemCount; i++){
                dayInformation[i] = allContentTypeInfo.getString(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dayInformation;
    }

    //Get information (only about chose type notes or trainings) for chose day in ArrayList<String>
    public ArrayList<String> getDayInformationAsArrayList(String date){
        ArrayList<String> dayInformation = new ArrayList<>();

        try {
            JSONObject jsonObj = new JSONObject(readData(fileName));
            JSONArray allContentTypeInfo = jsonObj.getJSONObject(date).getJSONArray(contentType);
            int allContentItemCount = allContentTypeInfo.length();
            //Convert information to ArrayList
            for(int i = 0; i < allContentItemCount; i++){
                dayInformation.add(allContentTypeInfo.getString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dayInformation;
    }

    //Remove only one item of defined content type, like some note to notes array (see file structure above)
    public void removeOneItem(String date, int itemIndex){
        ArrayList<String> dayInformation = getDayInformationAsArrayList(date);

        if(dayInformation.size() > 0)
            dayInformation.remove(itemIndex);

        rewriteDayInformation(date, dayInformation);
    }

    //Change one item of defined content type, like some note to notes array (see file structure above)
    public void changeOneItem(String date, int itemIndex, String newInformation){
        ArrayList<String> dayInformation = getDayInformationAsArrayList(date);

        if(dayInformation.size() > 0)
            dayInformation.set(itemIndex, newInformation);

        rewriteDayInformation(date, dayInformation);
    }

    //Technical methods for this class

    //Rewrite current type information, like all notes for this day
    private void rewriteDayInformation(String date, ArrayList<String> newInformation){
        try {
            JSONObject jsonObject = new JSONObject(readData(fileName));
            JSONObject dayInfo = jsonObject.getJSONObject(date);
            dayInfo.remove(contentType);
            dayInfo.put(contentType, new JSONArray());
            JSONArray dayInformationArray = dayInfo.getJSONArray(contentType);
            for (String item : newInformation){
                dayInformationArray.put(item);
            }
            saveDataToFile(fileName, jsonObject.toString());
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //add "day" object, see file structure above
    private void addDayObject(String date, JSONObject jsonObject) throws JSONException {
        jsonObject.put(date, new JSONObject());
        jsonObject.getJSONObject(date).put("notes", new JSONArray());
        jsonObject.getJSONObject(date).put("trainings", new JSONArray());
    }
}

