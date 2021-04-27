package com.example.punttilaskuri.fileHandlers;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

//This is a parent class for all file handlers
//It saves and reads information from file

public class FileHandler {
    private final Context context;

    public FileHandler(Context context){
        this.context = context;
    }

    //clears .json files: replace whole information with {} (empty JSON object)
    public void clearWholeJsonFile(String fileName){
        saveDataToFile(fileName, "{}");
    }

    //saves data to file
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

    //reads data from file
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

    public boolean isFileExist(String path){
        return new File(path).exists();
    }
}
