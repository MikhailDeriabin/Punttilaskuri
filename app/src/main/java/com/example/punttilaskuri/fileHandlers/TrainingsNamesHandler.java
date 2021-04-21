package com.example.punttilaskuri.fileHandlers;

import android.content.Context;

//Child of DaysInfoFileHandler
//Used for work with "trainings" in daysInfo.json

public class TrainingsNamesHandler extends DaysInfoFileHandler{

    public TrainingsNamesHandler(Context context){
        super(context, "trainings");
    }
}
