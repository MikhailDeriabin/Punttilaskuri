package com.example.punttilaskuri.fileHandlers;

import android.content.Context;

//Child of DaysInfoFileHandler
//Used for work with "notes" in daysInfo.json

public class NotesHandler extends DaysInfoFileHandler{

    public NotesHandler(Context context){
        super(context, "notes");
    }

}
