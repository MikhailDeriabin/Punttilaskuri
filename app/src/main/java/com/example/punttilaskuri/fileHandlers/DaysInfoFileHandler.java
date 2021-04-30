package com.example.punttilaskuri.fileHandlers;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This class is parent for NotesHandler and TrainingsNamesHandler classes,
 * works with daysInfo.json file, which contains information about each day: notes and planned trainings names
 * <p>This file structure:</p>
 * <p>{</p>
 * <p>   1.2.2021: {</p>
 * <p>       notes: [This is my first note, Second note],</p>
 * <p>      trainings: [myTraining]</p>
 * <p>  }</p>
 * <p>}</p>
 * @see FileHandler
 * @author Mikhail Deriabin
 * @version 18.04.2021
 */
public class DaysInfoFileHandler extends FileHandler {
    private final String fileName = "daysInfo.json";
    //content type is type of information you store, can be notes or trainings
    private final String contentType;

    public DaysInfoFileHandler(Context context, String contentType) {
        super(context);
        this.contentType = contentType;
    }

    /**
     * adds new information about the day, as notes or training names
     * @param date date of needed day, <p>example: 1.2.2021</p>
     * @param newInformation information to be added
     * @throws JSONException if there is problems with newInformation parameter converting to JSON format
     */
    public void addInformation(String date, String newInformation) throws JSONException{
        JSONObject jsonObject = getJsonFileRootObject(fileName);

        //If for this day is not any notes or trainings, create object for this day(see file structure above)
        if(jsonObject.isNull(date))
            addDayObject(date, jsonObject);

        //Add new "item" to the end of the array
        jsonObject.getJSONObject(date).getJSONArray(contentType).put(newInformation);
        saveDataToFile(fileName, jsonObject.toString());
    }

    /**
     * Get information (only about chose type: notes or training names) for chose day
     * <p>may produce JSONException if there is problems with converting String to JSON format</p>
     * @param date date of needed day, <p>Example: 1.2.2021</p>
     * @return dayInformation as String
     */
    public String getDayInformationAsString(String date){
        StringBuilder dayInformation = new StringBuilder();
        try {
            JSONObject jsonObj = getJsonFileRootObject(fileName);
            JSONArray allContentTypeInfo = jsonObj.getJSONObject(date).getJSONArray(contentType);
            int notesCount = allContentTypeInfo.length();
            //Convert information to String, where each item starts with new line
            for(int i = 0; i < notesCount; i++){
                dayInformation.append(allContentTypeInfo.getString(i)).append("\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dayInformation.toString();
    }

    /**
     * Get information (only about chose type: notes or training names) for chose day
     * <p>may produce JSONException if there is problems with converting String to JSON format</p>
     * @param date date of needed day, <p>Example: 1.2.2021</p>
     * @return dayInformation as Array
     */
    public String[] getDayInformationAsArray(String date){
        String[] dayInformation = null;
        try {
            JSONObject jsonObj = getJsonFileRootObject(fileName);
            JSONArray allContentTypeInfo = jsonObj.getJSONObject(date).getJSONArray(contentType);
            int allContentItemCount = allContentTypeInfo.length();
            dayInformation = new String[allContentItemCount];
            //Convert information to array
            for(int i = 0; i < allContentItemCount; i++){
                dayInformation[i] = allContentTypeInfo.getString(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dayInformation;
    }

    /**
     * Get information (only about chose type: notes or training names) for chose day
     * <p>may produce JSONException if there is problems with converting String to JSON format</p>
     * @param date date of needed day, <p>Example: 1.2.2021</p>
     * @return dayInformation as ArrayList
     */
    public ArrayList<String> getDayInformationAsArrayList(String date){
        ArrayList<String> dayInformation = new ArrayList<>();

        try {
            JSONObject jsonObj = getJsonFileRootObject(fileName);
            JSONArray allContentTypeInfo = jsonObj.getJSONObject(date).getJSONArray(contentType);
            int allContentItemCount = allContentTypeInfo.length();
            //Convert information to ArrayList
            for(int i = 0; i < allContentItemCount; i++){
                dayInformation.add(allContentTypeInfo.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dayInformation;
    }

    /**
     * Remove only one item of defined content type(note or training name)
     * @param date date of chose day, <p>Example: 1.2.2021</p>
     * @param itemIndex index of note or training name
     */
    public void removeOneItem(String date, int itemIndex){
        ArrayList<String> dayInformation = getDayInformationAsArrayList(date);

        if(dayInformation.size() > 0)
            dayInformation.remove(itemIndex);

        rewriteDayInformation(date, dayInformation);
    }

    /**
     * Change only one item of defined content type(note or training name)
     * @param date date of chose day, <p>Example: 1.2.2021</p>
     * @param itemIndex index of note or training name
     */
    public void changeOneItem(String date, int itemIndex, String newInformation){
        ArrayList<String> dayInformation = getDayInformationAsArrayList(date);

        if(dayInformation.size() > 0)
            dayInformation.set(itemIndex, newInformation);

        rewriteDayInformation(date, dayInformation);
    }

    //Technical methods only for this class

    /**
     * Rewrite defined type information(notes or training names)
     * <p>may produce JSONException if there is problems with newInformation parameter converting to JSON format</p>
     * @param date date of chose day, <p>Example: 1.2.2021</p>
     * @param newInformation index of note or training name
     */
    private void rewriteDayInformation(String date, ArrayList<String> newInformation){
        try {
            JSONObject jsonObject = getJsonFileRootObject(fileName);
            JSONObject dayInfo = jsonObject.getJSONObject(date);
            dayInfo.remove(contentType);
            dayInfo.put(contentType, new JSONArray());
            JSONArray dayInformationArray = dayInfo.getJSONArray(contentType);
            for (String item : newInformation){
                dayInformationArray.put(item);
            }
            saveDataToFile(fileName, jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * add "day object", see file structure above
     * @param date date of chose day, <p>Example: 1.2.2021</p>
     * @param jsonObject object, where "day object" will be created
     * @throws JSONException if there is problems with creating JSONObject
     */
    private void addDayObject(String date, JSONObject jsonObject) throws JSONException {
        jsonObject.put(date, new JSONObject());
        jsonObject.getJSONObject(date).put("notes", new JSONArray());
        jsonObject.getJSONObject(date).put("trainings", new JSONArray());
    }
}

