package com.example.punttilaskuri;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInputHandler {
    private final Context context;

    public UserInputHandler(Context context){
        this.context = context;
    }

    public void addNote(String note, String date) throws JSONException {
        String daysInfoContent = readData("daysInfo.json");
        System.out.println("----------------");
        System.out.println(daysInfoContent);
        System.out.println("----------------");
        JSONObject jsonObject = new JSONObject(daysInfoContent);

        if(jsonObject.isNull(date))
            addDayObject(date, jsonObject);

        jsonObject.getJSONObject(date).getJSONArray("notes").put(note);
        saveDataToFile("daysInfo.json", jsonObject.toString());
    }

    public String getAllNotes(String date){
        String result = "";
        try {
            JSONObject jsonObj = new JSONObject(readData("daysInfo.json"));
            JSONArray allNotes = jsonObj.getJSONObject(date).getJSONArray("notes");
            int notesCount = allNotes.length();
            for(int i = 0; i < notesCount; i++){
                result += allNotes.getString(i) + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public void saveDataToFile(String fileName, String data){
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readData(String fileName){
        String text = "";
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            int count;
            while ((count = fis.read()) !=-1) {
                text += Character.toString((char)count);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return text;
    }

    public void addDayObject(String date, JSONObject jsonObject) throws JSONException {
        jsonObject.put(date, new JSONObject());
        jsonObject.getJSONObject(date).put("notes", new JSONArray());
        jsonObject.getJSONObject(date).put("trainings", new JSONArray());
    }
}
