package com.example.punttilaskuri;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class JsonFileHandler {
    String fileName;
    Context context;
    public JsonFileHandler(String fileName, Context context){
        this.fileName = fileName;
        this.context = context;
    }

    public JSONObject getJsonObject() throws IOException, JSONException {
        JSONObject jsonObject;
        String jsonString;
        InputStream is = context.getAssets().open(fileName);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        jsonString = new String(buffer, "UTF_8");
        jsonObject = new JSONObject(jsonString);

        return jsonObject;
    }
}
