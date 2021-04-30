package com.example.punttilaskuri.fileHandlers;

import android.content.Context;

/**
 * Child of DaysInfoFileHandler,
 * works specially with training names
 * @see DaysInfoFileHandler
 * @author Mikhail Deriabin
 * @version 19.04.2021
 */

public class TrainingsNamesHandler extends DaysInfoFileHandler{

    public TrainingsNamesHandler(Context context){
        super(context, "trainings");
    }
}
