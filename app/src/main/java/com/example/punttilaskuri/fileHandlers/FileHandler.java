package com.example.punttilaskuri.fileHandlers;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This is a parent class for all file handlers,
 * it saves and reads information from file, clear JSON files, checks for file existing
 * @author Mikhail Deriabin
 * @version 18.04.2021
 */
public class FileHandler {
    private final Context context;

    public FileHandler(Context context){
        this.context = context;
    }

    /**
     * clears .json files: replace whole information with {} (empty JSON object)
     * @param fileName name of file on device: data/data/app_folder/files/fileName, example myFile.txt
     */
    public void clearWholeJsonFile(String fileName){
        saveDataToFile(fileName, "{}");
    }

    /**
     * saves data to file
     * <p>may produce FileNotFoundException if file doesn't exist or has wrong path, in these case creates new one</p>
     * <p>may produce IOException if there is a failure writing file</p>
     * @param fileName name of file on device: data/data/app_folder/files/fileName, example myFile.txt
     * @param data information to be saved
     */
    public void saveDataToFile(String fileName, String data){
        FileOutputStream fos;
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

    /**
     * reads data from file
     * <p>may produce FileNotFoundException if file doesn't exist or has wrong path</p>
     * <p>may produce IOException if there is a failure reading file</p>
     * @param fileName name of file on device: data/data/app_folder/files/fileName, example myFile.txt
     * @return String text data of the file
     */
    public String readData(String fileName) {
        StringBuilder text = new StringBuilder();
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(fileName);
            int count;
            while ((count = fis.read()) !=-1) {
                text.append((char) count);
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

        return text.toString();
    }

    /**
     * checks file existing
     * @param path name of file on device: data/data/app_folder/files/fileName, example myFile.txt
     * @return true if file exists, false if not
     */
    public boolean isFileExist(String path){
        return new File(path).exists();
    }

    protected JSONObject getJsonFileRootObject(String fileName){
        //get all content of the file in string
        String fileContent = readData(fileName);
        //if file doesn't content anything or exists, create new one with empty JSON object
        if(fileContent.equals(""))
            fileContent = "{}";

        try {
            return new JSONObject(fileContent);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }
}
