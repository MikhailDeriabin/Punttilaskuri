package com.example.punttilaskuri.fileHandlers;

import android.content.Context;


/**
 * Child of DaysInfoFileHandler,
 * works specially with notes
 * @see DaysInfoFileHandler
 * @author Mikhail Deriabin
 * @version 19.04.2021
 */
public class NotesHandler extends DaysInfoFileHandler{

    public NotesHandler(Context context){
        super(context, "notes");
    }

}
